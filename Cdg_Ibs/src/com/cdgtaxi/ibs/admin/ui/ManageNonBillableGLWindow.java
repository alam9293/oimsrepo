package com.cdgtaxi.ibs.admin.ui;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.log4j.Logger;
import org.springframework.orm.hibernate3.HibernateOptimisticLockingFailureException;
import org.zkoss.zk.ui.Components;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.SuspendNotAllowedException;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zk.ui.ext.AfterCompose;
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
import com.cdgtaxi.ibs.common.model.FmtbNonBillableMaster;
import com.cdgtaxi.ibs.common.model.forms.SearchNonBillableForm;
import com.cdgtaxi.ibs.common.ui.CommonWindow;
import com.cdgtaxi.ibs.util.ComponentUtil;
import com.cdgtaxi.ibs.web.component.Constants;

@SuppressWarnings({"serial", "unchecked"})
public class ManageNonBillableGLWindow extends CommonWindow implements AfterCompose {
	private Listbox resultList, serviceProviderListBox, cardTypeListBox;
	private static final Logger logger = Logger.getLogger(ManageNonBillableGLWindow.class);
	private Button createBtn;
	
	public void afterCompose() {
		//wire variables
		Components.wireVariables(this, this);
		
		Listitem emptyItem = new Listitem("-", null);
		serviceProviderListBox.appendChild((Listitem)emptyItem.clone());
		List<Entry<String, String>> splist = new LinkedList(ConfigurableConstants.getAllMasters(ConfigurableConstants.SERVICE_PROVIDER).entrySet());
		for (Map.Entry<String, String> me : splist) {
			Listitem item = new Listitem();
			item.setValue(me.getKey());
			item.setLabel(me.getValue());
			serviceProviderListBox.appendChild(item);
		}
		serviceProviderListBox.setSelectedIndex(0);
		
		cardTypeListBox.appendChild((Listitem)emptyItem.clone());
		List<Entry<String, String>> cplist = new LinkedList(ConfigurableConstants.getAllMasters(ConfigurableConstants.ACQUIRER_PYMT_TYPE).entrySet());
		for (Map.Entry<String, String> me : cplist) {
			Listitem item = new Listitem();
			item.setValue(me.getKey());
			item.setLabel(me.getValue());
			cardTypeListBox.appendChild(item);
		}
		cardTypeListBox.setSelectedIndex(0);
		
		if(!this.checkUriAccess(Uri.CREATE_NONBILLABLE_GL)){
			createBtn.setDisabled(true);
		}
	}
	
	public void search() throws SuspendNotAllowedException, InterruptedException{
		this.displayProcessing();
		resultList.getItems().clear();
		try {
			SearchNonBillableForm form = this.buildSearchForm();
			if(!form.isAtLeastOneCriteriaSelected){
					Messagebox.show("Please provide search criteria","Search Criteria", Messagebox.OK, Messagebox.INFORMATION);
			}else{
				List<FmtbNonBillableMaster> masters = this.businessHelper.getAdminBusiness().searchNonBillable(form);
				if(masters.size()>0){
					if(masters.size()>ConfigurableConstants.getMaxQueryResult())
						Messagebox.show(NonConfigurableConstants.getExceedMaxResultMessage(), "Alert", Messagebox.OK, Messagebox.INFORMATION);
					
					for(FmtbNonBillableMaster master : masters){
						Listitem item = new Listitem();

						item.setValue(master.getMasterNo());
						item.appendChild(newListcell(master.getMstbMasterTableByServiceProvider().getMasterValue()));
						item.appendChild(newListcell(master.getMstbMasterTableByPymtTypeMasterNo().getMasterValue()));
						
						if(master.getFmtbNonBillableDetails().size()<=0){
							Image deleteImage = new Image("/images/delete.png");
							deleteImage.setStyle("cursor:pointer");
							ZScript showInfo = ZScript.parseContent("manageNonBillableGLWindow.delete(self.getParent().getParent())");
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
					
					if(masters.size()>ConfigurableConstants.getMaxQueryResult())
						resultList.removeItemAt(ConfigurableConstants.getMaxQueryResult());
					
					if(resultList.getListfoot()!=null)
						resultList.removeChild(resultList.getListfoot());
				}else{
					if(resultList.getListfoot()==null){
						resultList.appendChild(ComponentUtil.createNoRecordsFoundListFoot(3));
					}
				}
				resultList.setMold(Constants.LISTBOX_MOLD_PAGING);
				resultList.setPageSize(10);
			}
		} catch(WrongValueException wve){
			throw wve;
		}
		catch(Exception e){
			Messagebox.show(com.cdgtaxi.ibs.common.NonConfigurableConstants.COMMON_ERROR_MESSAGE, 
					"Error", Messagebox.OK, Messagebox.ERROR);
			e.printStackTrace();
		}
	}
	
	public void delete(Listitem item) throws InterruptedException {
		if(!this.checkUriAccess(Uri.DELETE_NONBILLABLE_GL)){
			Messagebox.show(com.cdgtaxi.ibs.common.NonConfigurableConstants.ACCESS_DENIED,
					"Access Denied", Messagebox.OK, Messagebox.EXCLAMATION);
			return;
		}
		
		if (Messagebox.show("Are you sure you wish to delete?", "Confirmation",
				Messagebox.OK | Messagebox.NO, Messagebox.QUESTION) != Messagebox.OK) {
			return;
		}

		try {
			Integer masterNo = (Integer)item.getValue();
			FmtbNonBillableMaster master = this.businessHelper.getAdminBusiness().getNonBillableMaster(masterNo);
			this.businessHelper.getGenericBusiness().delete(master);

			item.detach();
			this.search();
			Messagebox.show("The Non Billable GL has been successfully deleted", "Delete Non Billable GL",
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
	
	public void edit() throws InterruptedException{
		try{
			Integer masterNo = (Integer)resultList.getSelectedItem().getValue();
			Map map = new HashMap();
			map.put("masterNo", masterNo);
			
			if(this.checkUriAccess(Uri.EDIT_NONBILLABLE_GL))
				forward(Uri.EDIT_NONBILLABLE_GL, map);
			else if(this.checkUriAccess(Uri.VIEW_NONBILLABLE_GL))
				forward(Uri.VIEW_NONBILLABLE_GL, map);
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
	
	private SearchNonBillableForm buildSearchForm(){
		SearchNonBillableForm form = new SearchNonBillableForm();
		
		if(serviceProviderListBox.getSelectedItem().getValue()!=null)
			form.service_provider = (String)serviceProviderListBox.getSelectedItem().getValue();
		if(form.service_provider!=null) form.isAtLeastOneCriteriaSelected = true;
		if(cardTypeListBox.getSelectedItem().getValue()!=null)
			form.card_type = (String)cardTypeListBox.getSelectedItem().getValue();
		if(form.card_type!=null) form.isAtLeastOneCriteriaSelected = true;
				
		return form;
	}
	
	public void reset(){
		serviceProviderListBox.setSelectedIndex(0);
		cardTypeListBox.setSelectedIndex(0);
		resultList.getItems().clear();
		if(resultList.getListfoot()==null) resultList.appendChild(ComponentUtil.createNoRecordsFoundListFoot(3));
	}
	
	public void create() throws InterruptedException{
		this.forward(Uri.CREATE_NONBILLABLE_GL, null);
	}
	
	@Override
	public void refresh() throws InterruptedException {
		if(this.buildSearchForm().isAtLeastOneCriteriaSelected){
			this.search();
		}
	}
}
