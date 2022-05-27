package com.cdgtaxi.ibs.admin.ui;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.orm.hibernate3.HibernateOptimisticLockingFailureException;
import org.zkoss.zk.ui.Components;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.SuspendNotAllowedException;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zk.ui.metainfo.EventHandler;
import org.zkoss.zk.ui.metainfo.ZScript;
import org.zkoss.zk.ui.util.ConditionImpl;
import org.zkoss.zul.Button;
import org.zkoss.zul.Image;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Messagebox;

import com.cdgtaxi.ibs.Uri;
import com.cdgtaxi.ibs.common.ConfigurableConstants;
import com.cdgtaxi.ibs.common.NonConfigurableConstants;
import com.cdgtaxi.ibs.common.model.forms.SearchAcquirerForm;
import com.cdgtaxi.ibs.common.ui.CommonWindow;
import com.cdgtaxi.ibs.master.model.MstbAcquirer;
import com.cdgtaxi.ibs.util.ComponentUtil;
import com.cdgtaxi.ibs.web.component.CapsTextbox;
import com.cdgtaxi.ibs.web.component.Constants;

@SuppressWarnings("serial")
public class ManageAcquirerWindow extends CommonWindow {
	private static final Logger logger = Logger.getLogger(ManageAcquirerWindow.class);
	private CapsTextbox nameTextBox;
	private Listbox resultList;
	private Button createBtn;

	public void onCreate() throws InterruptedException {
		try{
			//wire variables
			Components.wireVariables(this, this);
			this.displayAcquirers();
			
			if(!this.checkUriAccess(Uri.CREATE_ACQUIRER)){
				createBtn.setDisabled(true);
			}
		}catch(WrongValueException wve){
			throw wve;
		}
	}
	
	public void displayAcquirers() throws InterruptedException{
		try{
			resultList.getItems().clear();
			List<MstbAcquirer> acquirers = this.businessHelper.getAdminBusiness().getAcquirerByExample();
			if(acquirers.size()>0){

				if(acquirers.size()>ConfigurableConstants.getMaxQueryResult())
					Messagebox.show(NonConfigurableConstants.getExceedMaxResultMessage(), "Alert", Messagebox.OK, Messagebox.INFORMATION);

				for(MstbAcquirer acquirer : acquirers){
					Listitem item = new Listitem();

					item.setValue(acquirer.getAcquirerNo());
					item.appendChild(newListcell(acquirer.getName()));

					if(this.businessHelper.getAdminBusiness().isAcquirerDeletable(acquirer.getAcquirerNo())){
						Image deleteImage = new Image("/images/delete.png");
						deleteImage.setStyle("cursor:pointer");
						ZScript showInfo = ZScript.parseContent("manageAcquirerWindow.delete(self.getParent().getParent())");
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

				if(acquirers.size()>ConfigurableConstants.getMaxQueryResult())
					resultList.removeItemAt(ConfigurableConstants.getMaxQueryResult());

				if(resultList.getListfoot()!=null)
					resultList.removeChild(resultList.getListfoot());
			}
			else{
				if(resultList.getListfoot()==null){
					resultList.appendChild(ComponentUtil.createNoRecordsFoundListFoot(2));
				}
			}

			resultList.setMold(Constants.LISTBOX_MOLD_PAGING);
			resultList.setPageSize(10);
		}catch(WrongValueException wve){
			throw wve;
		}
		catch(HibernateOptimisticLockingFailureException holfe){
			Messagebox.show(com.cdgtaxi.ibs.common.NonConfigurableConstants.HIBERNATE_ERROR_MESSAGE,
					"Error", Messagebox.OK, Messagebox.ERROR);

			Executions.getCurrent().sendRedirect(""); //TO DO A REFRESH

			holfe.printStackTrace();
		}
		catch(Exception e){
			Messagebox.show(com.cdgtaxi.ibs.common.NonConfigurableConstants.COMMON_ERROR_MESSAGE,
					"Error", Messagebox.OK, Messagebox.ERROR);
			e.printStackTrace();
		}
	}
	
	public void search() throws SuspendNotAllowedException, InterruptedException{
		this.displayProcessing();
		resultList.getItems().clear();
		
		try{			
			SearchAcquirerForm form = this.buildSearchForm();
			
			if(!form.isAtLeastOneCriteriaSelected){
				Messagebox.show("Please enter the name of the search criteria", 
						"Error", Messagebox.OK, Messagebox.ERROR);
				return;
			}
			
			List<MstbAcquirer> acquirers = this.businessHelper.getAdminBusiness().searchAcquirer(form);
			if(acquirers.size()>0){
				
				if(acquirers.size()>ConfigurableConstants.getMaxQueryResult())
					Messagebox.show(NonConfigurableConstants.getExceedMaxResultMessage(), "Alert", Messagebox.OK, Messagebox.INFORMATION);
				
				for(MstbAcquirer acquirer : acquirers){
					Listitem item = new Listitem();
					
					item.setValue(acquirer.getAcquirerNo());
					item.appendChild(newListcell(acquirer.getName()));

					if(this.businessHelper.getAdminBusiness().isAcquirerDeletable(acquirer.getAcquirerNo())){
						Image deleteImage = new Image("/images/delete.png");
						deleteImage.setStyle("cursor:pointer");
						ZScript showInfo = ZScript.parseContent("manageAcquirerWindow.delete(self.getParent().getParent())");
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
				
				if(acquirers.size()>ConfigurableConstants.getMaxQueryResult())
					resultList.removeItemAt(ConfigurableConstants.getMaxQueryResult());
				
				if(resultList.getListfoot()!=null)
					resultList.removeChild(resultList.getListfoot());
			}
			else{
				if(resultList.getListfoot()==null){
					resultList.appendChild(ComponentUtil.createNoRecordsFoundListFoot(2));
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
	
	@SuppressWarnings("unchecked")
	public void edit() throws InterruptedException{
		try{
			Integer acquirerNo = (Integer)resultList.getSelectedItem().getValue();
			Map map = new HashMap();
			map.put("acquirerNo", acquirerNo);
			
			if(this.businessHelper.getAdminBusiness().isAcquirerDeletable(acquirerNo)){
				if(this.checkUriAccess(Uri.EDIT_ACQUIRER))
					forward(Uri.EDIT_ACQUIRER, map);
				else if(this.checkUriAccess(Uri.VIEW_ACQUIRER))
					forward(Uri.VIEW_ACQUIRER, map);
				else{
					Messagebox.show(com.cdgtaxi.ibs.common.NonConfigurableConstants.ACCESS_DENIED,
							"Access Denied", Messagebox.OK, Messagebox.EXCLAMATION);
					return;
				}
			}else{
				if(this.checkUriAccess(Uri.VIEW_ACQUIRER))
					forward(Uri.VIEW_ACQUIRER, map);
				else{
					Messagebox.show(com.cdgtaxi.ibs.common.NonConfigurableConstants.ACCESS_DENIED,
							"Access Denied", Messagebox.OK, Messagebox.EXCLAMATION);
					return;
				}
			}
		}
		catch(WrongValueException wve){
			throw wve;
		}
		catch(Exception e){
			Messagebox.show(com.cdgtaxi.ibs.common.NonConfigurableConstants.COMMON_ERROR_MESSAGE, 
					"Error", Messagebox.OK, Messagebox.ERROR);
			e.printStackTrace();
		}finally {
			resultList.clearSelection();
		}
	}
	
	public void create() throws InterruptedException{
		this.forward(Uri.CREATE_ACQUIRER, null);
	}
	
	public void delete(Listitem item) throws InterruptedException {
		if(!this.checkUriAccess(Uri.DELETE_ACQUIRER)){
			Messagebox.show(com.cdgtaxi.ibs.common.NonConfigurableConstants.ACCESS_DENIED,
					"Access Denied", Messagebox.OK, Messagebox.EXCLAMATION);
			return;
		}
		
		if (Messagebox.show("Are you sure you wish to delete?", "Confirmation",
				Messagebox.OK | Messagebox.NO, Messagebox.QUESTION) != Messagebox.OK) {
			return;
		}

		try {
			MstbAcquirer acquirer = (MstbAcquirer)this.businessHelper.getGenericBusiness().get(MstbAcquirer.class, (Integer)item.getValue());
			this.businessHelper.getGenericBusiness().delete(acquirer);
			
			item.detach();
			Messagebox.show("Acquirer has been successfully deleted", "Delete Acquirer",
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
	
	private SearchAcquirerForm buildSearchForm(){
		SearchAcquirerForm form = new SearchAcquirerForm();
		form.name = nameTextBox.getValue();
		if(form.name!=null && form.name.length()>0) form.isAtLeastOneCriteriaSelected = true;
	
		return form;
	}
	
	public void reset() throws InterruptedException{
		nameTextBox.setValue("");
		resultList.getItems().clear();
		this.displayAcquirers();
	}

	@Override
	public void refresh() throws InterruptedException {
		// TODO Auto-generated method stub
		if(buildSearchForm().isAtLeastOneCriteriaSelected){
			this.search();
		}else{
			this.displayAcquirers();
		}
	}
	
}
