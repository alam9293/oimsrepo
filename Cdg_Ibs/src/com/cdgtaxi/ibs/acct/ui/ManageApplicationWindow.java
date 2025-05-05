package com.cdgtaxi.ibs.acct.ui;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listheader;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;

import com.cdgtaxi.ibs.Uri;
import com.cdgtaxi.ibs.common.ConfigurableConstants;
import com.cdgtaxi.ibs.common.NonConfigurableConstants;
import com.cdgtaxi.ibs.common.model.AmtbApplication;
import com.cdgtaxi.ibs.common.ui.CommonWindow;
import com.cdgtaxi.ibs.util.ComponentUtil;
import com.cdgtaxi.ibs.util.DateUtil;
import com.cdgtaxi.ibs.util.StringUtil;

public class ManageApplicationWindow extends CommonWindow {
	private static final long serialVersionUID = 1L;
	private static Logger logger = Logger.getLogger(ManageApplicationWindow.class);
	private List<Listitem> applicationStatus = new ArrayList<Listitem>();
	
	public ManageApplicationWindow(){
		// adding all application status
		applicationStatus.add((Listitem)ComponentUtil.createNotRequiredListItem());
		for(String statusCode : NonConfigurableConstants.APPLICATION_STATUS.keySet()){
			applicationStatus.add(new Listitem(NonConfigurableConstants.APPLICATION_STATUS.get(statusCode), statusCode));
		}
		// now removing NEW status. Hidden from user
		applicationStatus.remove(1);
	}
	/**
	 * Search for all application
	 */
	public void search() throws InterruptedException{
		logger.info("search()");
		// getting appNo string from textbox
		String appNoString = ((Intbox)this.getFellow("appNo")).getText();
		logger.info("app no = " + appNoString);
		try{
			// if data exist
			if(appNoString!=null && appNoString.length()!=0){
				// try parsing it to integer
				int appNo = Integer.parseInt(appNoString);
				// if application number is lesser than 0 = invalid. setting string to null
				if(appNo < 0){
					appNoString = null;
				}
			}
		}catch(NumberFormatException nfe){
			// Shouldn't happen
			Messagebox.show("Invalid format for application no. Continuing without application no", "Manage Applications", Messagebox.OK, Messagebox.ERROR);
			appNoString = null;
		}
		// getting app name
		String appName = ((Textbox)this.getFellow("appName")).getText();
		// getting app status
		String appStatus = (String)((Listbox)this.getFellow("appStatusList")).getSelectedItem().getValue();
		// now checking for min 3 characters
		if(appNoString==null || appNoString.length()==0){
			if(appStatus==null || appStatus.length()==0){
				if(appName==null || appName.length()<3){
					Messagebox.show("Please input application no/name(min 3 chars)/status", "Manage Applications", Messagebox.OK, Messagebox.ERROR);
					return;
				}
			}
		}
		// getting rows to add results
		Listbox applications = (Listbox)this.getFellow("applications");
		// clearing any previous search
		applications.getItems().clear();
		// getting results from business layer
		Map<AmtbApplication, Map<String, String>> results = this.businessHelper.getApplicationBusiness().searchApplications(appNoString, appName, appStatus);
		// for each result
		if(results.size()!=0){
			
			if(results.size()>ConfigurableConstants.getMaxQueryResult())
				Messagebox.show(NonConfigurableConstants.getExceedMaxResultMessage(), "Alert", Messagebox.OK, Messagebox.INFORMATION);
			
			for(AmtbApplication amtbApplication : results.keySet()){
				
				String applicationNo = amtbApplication.getApplicationNo();
				// creating a new row and append it to rows
				Listitem application = new Listitem(); applications.appendChild(application);
				// getting the details
				Map<String, String> applicationDetails = results.get(amtbApplication);
				// creating a list to hold required information of the application but hide from user
				Map<String, Object> applicationList = new LinkedHashMap<String, Object>();
				// putting application number into list
				//applicationList.put("appNo", applicationNo);
				applicationList.put("amtbApplication", amtbApplication);
				// putting account template into list
				applicationList.put("acctTemplate", applicationDetails.get("acctTemplate"));
				// setting the list as the value
				application.setValue(applicationList);
				// creating listcell into listitem for applicant name
				application.appendChild(newListcell(applicationDetails.get("appName")));
				// creating listcell into listitem for application status
				application.appendChild(newListcell(NonConfigurableConstants.APPLICATION_STATUS.get(applicationDetails.get("appStatus"))));
				// creating listcell into listitem for application no
				application.appendChild(newListcell(new Integer(applicationNo), StringUtil.GLOBAL_STRING_FORMAT));
				// creating listcell into listitem for application date
				Date applicationDate = DateUtil.convertStrToDate(applicationDetails.get("appDate"), DateUtil.GLOBAL_DATE_FORMAT);
				if(applicationDate==null)
					application.appendChild(newEmptyListcell(DateUtil.convertStrToDate("01/01/1980", DateUtil.GLOBAL_DATE_FORMAT), "-"));
				else
					application.appendChild(newListcell(applicationDate, DateUtil.GLOBAL_DATE_FORMAT));
			}
			
			if(results.size()>ConfigurableConstants.getMaxQueryResult())
				applications.removeItemAt(ConfigurableConstants.getMaxQueryResult());
			
			applications.setVisible(true);
		}else{
			Messagebox.show("No matching application.", "Manage Applications", Messagebox.OK, Messagebox.INFORMATION);
		}
	}
	/**
	 * Search for all outstanding (Draft, pending 1st/2nd level) applications
	 * on load
	 */
	public void searchOutstandingApps() throws InterruptedException{
		logger.info("searchOutstandingApps()");
		// getting rows to add results
		Listbox applications = (Listbox)this.getFellow("applications");
		// clearing any previous search
		applications.getItems().clear();
		// creating a new list to hold all status for query
		List<String> statuses = new ArrayList<String>();
		// adding draft status
		statuses.add(NonConfigurableConstants.APPLICATION_STATUS_DRAFT);
		// adding pending 1st level approval
		statuses.add(NonConfigurableConstants.APPLICATION_STATUS_PENDING_1ST_LEVEL_APPROVAL);
		// adding pending 2nd level approval
		statuses.add(NonConfigurableConstants.APPLICATION_STATUS_PENDING_2ND_LEVEL_APPROVAL);
		// now extracting results from business layer
		Map<AmtbApplication, Map<String, String>> results = this.businessHelper.getApplicationBusiness().searchApplications(statuses);
		// for each result
		if(results.size()!=0){
			for(AmtbApplication amtbApplication : results.keySet()){
				
				String applicationNo = amtbApplication.getApplicationNo();
				// creating a new row and append it to rows
				Listitem application = new Listitem(); applications.appendChild(application);
				// getting the details
				Map<String, String> applicationDetails = results.get(amtbApplication);
				// creating a list to hold required information of the application but hide from user
				Map<String, Object> applicationList = new LinkedHashMap<String, Object>();
				// putting application number into list
				//applicationList.put("appNo", applicationNo);
				// putting amtbApplication object into list
				applicationList.put("amtbApplication", amtbApplication);
				// putting account template into list
				applicationList.put("acctTemplate", applicationDetails.get("acctTemplate"));
				// setting the list as the value
				application.setValue(applicationList);
				// creating listcell into listitem for applicant name
				application.appendChild(newListcell(applicationDetails.get("appName")));
				// creating listcell into listitem for application status
				application.appendChild(newListcell(NonConfigurableConstants.APPLICATION_STATUS.get(applicationDetails.get("appStatus"))));
				// creating listcell into listitem for application no
				application.appendChild(newListcell(new Integer(applicationNo), StringUtil.GLOBAL_STRING_FORMAT));
				// creating listcell into listitem for application date
				Date applicationDate = DateUtil.convertStrToDate(applicationDetails.get("appDate"), DateUtil.GLOBAL_DATE_FORMAT);
				if(applicationDate==null)
					application.appendChild(newEmptyListcell(DateUtil.convertStrToDate("01/01/1980", DateUtil.GLOBAL_DATE_FORMAT), "-"));
				else
					application.appendChild(newListcell(applicationDate, DateUtil.GLOBAL_DATE_FORMAT));
			}
			applications.setVisible(true);
		}
		// now adding the default searchs
		applications.getPagingChild().setMold("os");
		applications.setPageSize(ConfigurableConstants.getSortPagingSize());
		Listheader appNoHeader = (Listheader)this.getFellow("appNoHeader");
		appNoHeader.setSortAscending(new Comparator<Listitem>(){
			public int compare(Listitem o1, Listitem o2) {
				String appNo1 = (String)((Listcell)o1.getChildren().get(2)).getLabel();
				String appNo2 = (String)((Listcell)o2.getChildren().get(2)).getLabel();
				return new Integer(appNo1).compareTo(Integer.parseInt(appNo2));
			}
		});
		appNoHeader.setSortDescending(new Comparator<Listitem>(){
			public int compare(Listitem o1, Listitem o2) {
				String appNo1 = (String)((Listcell)o1.getChildren().get(2)).getLabel();
				String appNo2 = (String)((Listcell)o2.getChildren().get(2)).getLabel();
				return new Integer(appNo2).compareTo(Integer.parseInt(appNo1));
			}
		});
	}
	/**
	 * method trigger when user clicks an application
	 * @throws InterruptedException 
	 */
	@SuppressWarnings("unchecked")
	public void selectApplication() throws InterruptedException{
		logger.info("selectApplication()");
		// getting the list box
		Listbox applications = (Listbox)this.getFellow("applications");
		// getting the selected item
		Listitem selectedApplication = applications.getSelectedItem();
		
		// getting the selected application details
		Map<String, Object> applicationDetails = (Map<String, Object>)selectedApplication.getValue();
		AmtbApplication amtbApplication = (AmtbApplication)applicationDetails.get("amtbApplication");
		String acctTemplate = (String)applicationDetails.get("acctTemplate");
		
		// creating view params
		Map<String, String> params = new LinkedHashMap<String, String>();
		// putting application no into the params for viewing
		params.put("appNo", amtbApplication.getApplicationNo());
		
		// creating approve params
		Map<String, Object> approveParams = new LinkedHashMap<String, Object>();
		// putting application no into the params for approving
		approveParams.put("amtbApplication", amtbApplication);
		
		// if draft
		String status = ((Listcell)selectedApplication.getChildren().get(1)).getLabel();
		if(status.equals(NonConfigurableConstants.APPLICATION_STATUS.get(NonConfigurableConstants.APPLICATION_STATUS_DRAFT))){
			if(acctTemplate.equals(NonConfigurableConstants.ACCOUNT_TEMPLATES_CORPORATE)){
				if(this.checkUriAccess(Uri.CREATE_CORP_APP)){
					this.forward(Uri.CREATE_CORP_APP, params);
				}else{
					this.forward(Uri.VIEW_CORP_APP, params);
				}
			}else{
				if(this.checkUriAccess(Uri.CREATE_PERS_APP)){
					this.forward(Uri.CREATE_PERS_APP, params);
				}else{
					this.forward(Uri.VIEW_PERS_APP, params);
				}
			}
		}else if(status.equals(NonConfigurableConstants.APPLICATION_STATUS.get(NonConfigurableConstants.APPLICATION_STATUS_PENDING_1ST_LEVEL_APPROVAL))){
			if(acctTemplate.equals(NonConfigurableConstants.ACCOUNT_TEMPLATES_CORPORATE)){
				if(this.checkUriAccess(Uri.APPROVE_CORP_APP)){
					this.forward(Uri.APPROVE_CORP_APP, approveParams);
				}else{
					this.forward(Uri.VIEW_CORP_APP, params);
				}
			}else{
				if(this.checkUriAccess(Uri.APPROVE_PERS_APP)){
					this.forward(Uri.APPROVE_PERS_APP, approveParams);
				}else{
					this.forward(Uri.VIEW_PERS_APP, params);
				}
			}
		}else if(status.equals(NonConfigurableConstants.APPLICATION_STATUS.get(NonConfigurableConstants.APPLICATION_STATUS_PENDING_2ND_LEVEL_APPROVAL))){
			if(acctTemplate.equals(NonConfigurableConstants.ACCOUNT_TEMPLATES_CORPORATE)){
				if(this.checkUriAccess(Uri.APPROVE_CORP_APP_2)){
					this.forward(Uri.APPROVE_CORP_APP_2, approveParams);
				}else{
					this.forward(Uri.VIEW_CORP_APP, params);
				}
			}else{
				if(this.checkUriAccess(Uri.APPROVE_PERS_APP_2)){
					this.forward(Uri.APPROVE_PERS_APP_2, approveParams);
				}else{
					this.forward(Uri.VIEW_PERS_APP, params);
				}
			}
		}else{
			//boolean approver = this.getUserLoginId();
			if(acctTemplate.equals(NonConfigurableConstants.ACCOUNT_TEMPLATES_CORPORATE)){
				this.forward(Uri.VIEW_CORP_APP, params);
			}else{
				this.forward(Uri.VIEW_PERS_APP, params);
			}
		}
	}
	/**
	 * Overriden method from window. Things to do when refresh or back is issued
	 * to this window.
	 */
	@Override
	public void refresh() throws InterruptedException {
		logger.info("refresh()");
		// clearing all results and reload the outstanding apps
		Listbox apps = (Listbox)this.getFellow("applications");
		apps.getItems().clear();
		this.searchOutstandingApps();
		((Intbox)this.getFellow("appNo")).setText("");
		((Textbox)this.getFellow("appName")).setText("");
		((Listbox)this.getFellowIfAny("appStatusList")).setSelectedIndex(0);
	}
	public List<Listitem> getApplicationStatus(){
		return this.applicationStatus;
	}
}