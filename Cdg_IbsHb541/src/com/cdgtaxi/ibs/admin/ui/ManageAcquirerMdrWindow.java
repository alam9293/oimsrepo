package com.cdgtaxi.ibs.admin.ui;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.orm.hibernate5.HibernateOptimisticLockingFailureException;
import org.zkoss.zk.ui.Components;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.SuspendNotAllowedException;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zk.ui.ext.AfterCompose;
import org.zkoss.zk.ui.metainfo.EventHandler;
import org.zkoss.zk.ui.metainfo.ZScript;
import org.zkoss.zk.ui.util.ConditionImpl;
import org.zkoss.zul.Button;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Decimalbox;
import org.zkoss.zul.Image;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Messagebox;

import com.cdgtaxi.ibs.Uri;
import com.cdgtaxi.ibs.common.ConfigurableConstants;
import com.cdgtaxi.ibs.common.NonConfigurableConstants;
import com.cdgtaxi.ibs.common.model.forms.SearchAcquirerMdrForm;
import com.cdgtaxi.ibs.common.ui.CommonWindow;
import com.cdgtaxi.ibs.master.model.MstbAcquirer;
import com.cdgtaxi.ibs.master.model.MstbAcquirerMdr;
import com.cdgtaxi.ibs.util.ComponentUtil;
import com.cdgtaxi.ibs.util.DateUtil;
import com.cdgtaxi.ibs.web.component.Constants;

@SuppressWarnings("serial")
public class ManageAcquirerMdrWindow extends CommonWindow implements AfterCompose {
	
	private static final Logger logger = Logger.getLogger(ManageAcquirerWindow.class);
	private Listbox acquirerTypeListBox, resultList;
	private Decimalbox rateDecimalBox;
	private Datebox effDateFromDateBox, effDateToDateBox;
	private Button createBtn;
	
	public void afterCompose() {
		//wire variables
		Components.wireVariables(this, this);
		
		Listitem emptyItem = new Listitem("-", null);
		
		acquirerTypeListBox.appendChild((Listitem)emptyItem.clone());
		List<MstbAcquirer> acquirerTypes = this.businessHelper.getAdminBusiness().getAcquirer();
		for(MstbAcquirer acquirerType : acquirerTypes){
			Listitem item = new Listitem();
			item.setValue(acquirerType);
			item.setLabel(acquirerType.getName());
			acquirerTypeListBox.appendChild(item);
		}
		acquirerTypeListBox.setSelectedIndex(0);
		
		if(!this.checkUriAccess(Uri.CREATE_ACQUIRER_MDR)){
			createBtn.setDisabled(true);
		}
		
	}
	public void search() throws SuspendNotAllowedException, InterruptedException{
		this.displayProcessing();
		resultList.getItems().clear();
		
		try{			
			SearchAcquirerMdrForm form = this.buildSearchForm();
			if(effDateFromDateBox.getValue()!=null && effDateToDateBox.getValue()!=null)
				if(effDateFromDateBox.getValue().compareTo(effDateToDateBox.getValue()) == 1)
					throw new WrongValueException(effDateFromDateBox, "Effective Date From cannot be later than Effective Date To.");
			
			if(!form.isAtLeastOneCriteriaSelected){
				Messagebox.show("Please enter the name of the search criteria", 
						"Error", Messagebox.OK, Messagebox.ERROR);
				return;
			}

			List<Object[]> acquirersMdrs = this.businessHelper.getAdminBusiness().searchAcquirerMdr(form);
			
			if(acquirersMdrs.size()>0){
				
				if(acquirersMdrs.size()>ConfigurableConstants.getMaxQueryResult())
					Messagebox.show(NonConfigurableConstants.getExceedMaxResultMessage(), "Alert", Messagebox.OK, Messagebox.INFORMATION);
				
				for(Object[] objectArray : acquirersMdrs){
					
					MstbAcquirerMdr acquirerMdr = (MstbAcquirerMdr)objectArray[0];
					MstbAcquirer acquirer = (MstbAcquirer)objectArray[1];
					
					Listitem item = new Listitem();
					
					item.setValue(acquirerMdr.getMdrNo());
					item.appendChild(newListcell(acquirer.getName()));
					if(acquirerMdr.getEffectiveDate()!=null)
						item.appendChild(newListcell(acquirerMdr.getEffectiveDate()));
					else
						item.appendChild(newEmptyListcell(DateUtil.getSqlDateForNullComparison(), "-"));
					item.appendChild(newListcell(acquirerMdr.getRate()));
					
					if(DateUtil.getCurrentDate().compareTo(acquirerMdr.getEffectiveDate()) <=0){
						Image deleteImage = new Image("/images/delete.png");
						deleteImage.setStyle("cursor:pointer");
						ZScript showInfo = ZScript.parseContent("manageAcquirerMdrWindow.delete(self.getParent().getParent())");
						showInfo.setLanguage("java");
						EventHandler event = new EventHandler(showInfo, ConditionImpl.getInstance(null, null));
						deleteImage.addEventHandler("onClick", event);
						Listcell imageCell = new Listcell();
						imageCell.appendChild(deleteImage);
						item.appendChild(imageCell);
					}else{
						item.appendChild(new Listcell());
					}
					
					resultList.appendChild(item);
				}
				
				if(acquirersMdrs.size()>ConfigurableConstants.getMaxQueryResult())
					resultList.removeItemAt(ConfigurableConstants.getMaxQueryResult());
				if(resultList.getListfoot()!=null)
					resultList.removeChild(resultList.getListfoot());
			}
			else{
				if(resultList.getListfoot()==null){
					resultList.appendChild(ComponentUtil.createNoRecordsFoundListFoot(4));
				}
			}

			resultList.setMold(Constants.LISTBOX_MOLD_PAGING);
			resultList.setPageSize(10);

		}
		catch(WrongValueException wve){
			throw wve;
		}
		catch(Exception e){
			Messagebox.show(com.cdgtaxi.ibs.common.NonConfigurableConstants.COMMON_ERROR_MESSAGE, 
					"Error", Messagebox.OK, Messagebox.ERROR);
			e.printStackTrace();
		}
	}
	private SearchAcquirerMdrForm buildSearchForm(){
		SearchAcquirerMdrForm form = new SearchAcquirerMdrForm();

		if(acquirerTypeListBox.getSelectedItem().getValue()!=null)
			form.acquirer = (MstbAcquirer)acquirerTypeListBox.getSelectedItem().getValue();
		if(form.acquirer!=null) form.isAtLeastOneCriteriaSelected = true;
		form.rate = rateDecimalBox.getValue();
		if(form.rate!=null) form.isAtLeastOneCriteriaSelected = true;
		form.effDateFromDate = effDateFromDateBox.getValue();
		if(form.effDateFromDate!=null) form.isAtLeastOneCriteriaSelected = true;
		form.effDateToDate = effDateToDateBox.getValue();
		if(form.effDateToDate!=null) form.isAtLeastOneCriteriaSelected = true;
		
		if(effDateFromDateBox.getValue()!=null && effDateToDateBox.getValue()==null){
			effDateToDateBox.setValue(effDateFromDateBox.getValue());
			form.effDateToDate = effDateFromDateBox.getValue();
		}else if(effDateFromDateBox.getValue()==null && effDateToDateBox.getValue()!=null){
			effDateFromDateBox.setValue(effDateToDateBox.getValue());
			form.effDateFromDate = effDateToDateBox.getValue();
		}		
		return form;
	}
	public void delete(Listitem item) throws InterruptedException {
		if(!this.checkUriAccess(Uri.DELETE_ACQUIRER_MDR)){
			Messagebox.show(com.cdgtaxi.ibs.common.NonConfigurableConstants.ACCESS_DENIED,
					"Access Denied", Messagebox.OK, Messagebox.EXCLAMATION);
			return;
		}
		
		if (Messagebox.show("Are you sure you wish to delete?", "Confirmation",
				Messagebox.OK | Messagebox.NO, Messagebox.QUESTION) != Messagebox.OK) {
			return;
		}

		try {
			MstbAcquirerMdr acquirerMdr = (MstbAcquirerMdr)this.businessHelper.getGenericBusiness().get(MstbAcquirerMdr.class, (Integer)item.getValue());
			this.businessHelper.getGenericBusiness().delete(acquirerMdr);
			
			item.detach();
			Messagebox.show("Acquirer MDR has been successfully deleted", "Delete Acquirer MDR",
					Messagebox.OK, Messagebox.INFORMATION);
		}
		catch(HibernateOptimisticLockingFailureException holfe){
			Messagebox.show(com.cdgtaxi.ibs.common.NonConfigurableConstants.HIBERNATE_ERROR_MESSAGE,
					"Error", Messagebox.OK, Messagebox.ERROR);

			Executions.getCurrent().sendRedirect(""); //TO DO A REFRESH

			holfe.printStackTrace();
		}
		catch (Exception ex) {
			logger.error(ex.getMessage(), ex);
			Messagebox.show(NonConfigurableConstants.COMMON_ERROR_MESSAGE,
					"Error", Messagebox.OK, Messagebox.ERROR);
		}
	}
	@SuppressWarnings("unchecked")
	public void edit() throws InterruptedException{
		try{
			Integer mdrNo = (Integer)resultList.getSelectedItem().getValue();
			Map map = new HashMap();
			map.put("mdrNo", mdrNo);
			
			MstbAcquirerMdr acquirerMdr = (MstbAcquirerMdr)this.businessHelper.getGenericBusiness().get(MstbAcquirerMdr.class, mdrNo);
			
			Timestamp t1 = DateUtil.convertTimestampTo0000Hours(DateUtil.convertDateToTimestamp(acquirerMdr.getEffectiveDate()));
			Timestamp t2 = DateUtil.convertTimestampTo0000Hours(DateUtil.getCurrentTimestamp());
			if(t1.compareTo(t2) >= 0)
				if(this.checkUriAccess(Uri.EDIT_ACQUIRER_MDR))
					forward(Uri.EDIT_ACQUIRER_MDR, map);
				else if(this.checkUriAccess(Uri.VIEW_ACQUIRER_MDR))
					forward(Uri.VIEW_ACQUIRER_MDR, map);
				else{
					Messagebox.show(com.cdgtaxi.ibs.common.NonConfigurableConstants.ACCESS_DENIED,
							"Access Denied", Messagebox.OK, Messagebox.EXCLAMATION);
					return;
				}
			else
				if(this.checkUriAccess(Uri.VIEW_ACQUIRER_MDR))
					forward(Uri.VIEW_ACQUIRER_MDR, map);
				else{
					Messagebox.show(com.cdgtaxi.ibs.common.NonConfigurableConstants.ACCESS_DENIED,
							"Access Denied", Messagebox.OK, Messagebox.EXCLAMATION);
					return;
				}
		}
		catch(WrongValueException wve){
			throw wve;
		}
		catch(Exception e){
			Messagebox.show(com.cdgtaxi.ibs.common.NonConfigurableConstants.COMMON_ERROR_MESSAGE, 
					"Error", Messagebox.OK, Messagebox.ERROR);
			e.printStackTrace();
		}
	}
	public void create() throws InterruptedException{
		this.forward(Uri.CREATE_ACQUIRER_MDR, null);
	}
	public void reset(){		
		acquirerTypeListBox.setSelectedIndex(0);
		rateDecimalBox.setText("");
		effDateFromDateBox.setText("");
		effDateToDateBox.setText("");		
		resultList.getItems().clear();
		if(resultList.getListfoot()==null) resultList.appendChild(ComponentUtil.createNoRecordsFoundListFoot(5));
	}
	@Override
	public void refresh() throws InterruptedException {
		// TODO Auto-generated method stub
		if(this.buildSearchForm().isAtLeastOneCriteriaSelected){
			this.search();
		}
	}
}
