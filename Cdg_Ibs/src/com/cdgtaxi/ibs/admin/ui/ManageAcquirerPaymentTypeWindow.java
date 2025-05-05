package com.cdgtaxi.ibs.admin.ui;

import java.sql.Timestamp;
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
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Image;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Messagebox;

import com.cdgtaxi.ibs.Uri;
import com.cdgtaxi.ibs.common.ConfigurableConstants;
import com.cdgtaxi.ibs.common.NonConfigurableConstants;
import com.cdgtaxi.ibs.common.model.forms.SearchAcquirerPaymentTypeForm;
import com.cdgtaxi.ibs.common.ui.CommonWindow;
import com.cdgtaxi.ibs.master.model.MstbAcquirer;
import com.cdgtaxi.ibs.master.model.MstbAcquirerPymtType;
import com.cdgtaxi.ibs.master.model.MstbMasterTable;
import com.cdgtaxi.ibs.util.ComponentUtil;
import com.cdgtaxi.ibs.util.DateUtil;
import com.cdgtaxi.ibs.web.component.Constants;

@SuppressWarnings({"serial", "unchecked"})
public class ManageAcquirerPaymentTypeWindow extends CommonWindow implements AfterCompose {

	private static final Logger logger = Logger.getLogger(ManageAcquirerWindow.class);
	private Datebox effDateFromDateBox, effDateToDateBox;
	private Listbox resultList, acquirerTypeListBox, valueTypeListBox;
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

		valueTypeListBox.appendChild((Listitem)emptyItem.clone());
		List<Entry<String, String>> list = new LinkedList(ConfigurableConstants.getAllMasters(ConfigurableConstants.ACQUIRER_PYMT_TYPE).entrySet());
		for (Map.Entry<String, String> me : list) {
			Listitem item = new Listitem();
			item.setValue(me.getKey());
			item.setLabel(me.getValue());
			valueTypeListBox.appendChild(item);
		}
		valueTypeListBox.setSelectedIndex(0);
		
		if(!this.checkUriAccess(Uri.CREATE_ACQUIRER_PAYMENT_TYPE)){
			createBtn.setDisabled(true);
		}
	}
	
	public void populatePaymentType(){
		MstbAcquirer acquirer = (MstbAcquirer)acquirerTypeListBox.getSelectedItem().getValue();
		Listitem emptyItem = new Listitem("-", null);
		valueTypeListBox.getChildren().clear();
		valueTypeListBox.appendChild((Listitem)emptyItem.clone());
		if(acquirer!=null){
			List<MstbMasterTable> paymentTypes = this.businessHelper.getAdminBusiness().getPaymentType(acquirer);

			for(MstbMasterTable paymentType : paymentTypes){
				Listitem item = new Listitem();
				item.setValue(paymentType.getMasterCode());
				item.setLabel(paymentType.getMasterValue());
				valueTypeListBox.appendChild(item);
			}
		}else{
			List<Entry<String, String>> list = new LinkedList(ConfigurableConstants.getAllMasters(ConfigurableConstants.ACQUIRER_PYMT_TYPE).entrySet());
			for (Map.Entry<String, String> me : list) {
				Listitem item = new Listitem();
				item.setValue(me.getKey());
				item.setLabel(me.getValue());
				valueTypeListBox.appendChild(item);
			}
		}
		valueTypeListBox.setSelectedIndex(0);
	}
	public void search() throws SuspendNotAllowedException, InterruptedException{
		this.displayProcessing();
		resultList.getItems().clear();
		
		try{			
			SearchAcquirerPaymentTypeForm form = this.buildSearchForm();
			if(effDateFromDateBox.getValue()!=null && effDateToDateBox.getValue()!=null)
				if(effDateFromDateBox.getValue().compareTo(effDateToDateBox.getValue()) == 1)
					throw new WrongValueException(effDateFromDateBox, "Effective Date From cannot be later than Effective Date To.");
			
			if(!form.isAtLeastOneCriteriaSelected){
				Messagebox.show("Please enter the name of the search criteria", 
						"Error", Messagebox.OK, Messagebox.ERROR);
				return;
			}
			
			List<MstbAcquirerPymtType> acquirersPymtTypes = this.businessHelper.getAdminBusiness().searchAcquirerPaymentType(form);
			if(acquirersPymtTypes.size()>0){
				
				if(acquirersPymtTypes.size()>ConfigurableConstants.getMaxQueryResult())
					Messagebox.show(NonConfigurableConstants.getExceedMaxResultMessage(), "Alert", Messagebox.OK, Messagebox.INFORMATION);
				
				for(MstbAcquirerPymtType acquirerPymtType : acquirersPymtTypes){
					Listitem item = new Listitem();
					item.setValue(acquirerPymtType.getPymtTypeNo());
					item.appendChild(newListcell(acquirerPymtType.getMstbAcquirer().getName()));
					item.appendChild(newListcell(acquirerPymtType.getMstbMasterTable().getMasterValue()));
					item.appendChild(newListcell(DateUtil.convertTimestampToUtilDate(acquirerPymtType.getEffectiveDtFrom())));
					
					if(acquirerPymtType.getEffectiveDtTo()!=null)
						item.appendChild(newListcell(DateUtil.convertTimestampToUtilDate(acquirerPymtType.getEffectiveDtTo())));
					else
						item.appendChild(newEmptyListcell(DateUtil.getSqlDateForNullComparison(), "-"));
					
					if(DateUtil.getCurrentDate().compareTo(acquirerPymtType.getEffectiveDtFrom()) <= -1){
						Image deleteImage = new Image("/images/delete.png");
						deleteImage.setStyle("cursor:pointer");
						ZScript showInfo = ZScript.parseContent("manageAcquirerPaymentTypeWindow.delete(self.getParent().getParent())");
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
				
				if(acquirersPymtTypes.size()>ConfigurableConstants.getMaxQueryResult())
					resultList.removeItemAt(ConfigurableConstants.getMaxQueryResult());
				
				if(resultList.getListfoot()!=null)
					resultList.removeChild(resultList.getListfoot());
			}
			else{
				if(resultList.getListfoot()==null){
					resultList.appendChild(ComponentUtil.createNoRecordsFoundListFoot(6));
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
	
	public void delete(Listitem item) throws InterruptedException {
		if(!this.checkUriAccess(Uri.DELETE_ACQUIRER_PAYMENT_TYPE)){
			Messagebox.show(com.cdgtaxi.ibs.common.NonConfigurableConstants.ACCESS_DENIED,
					"Access Denied", Messagebox.OK, Messagebox.EXCLAMATION);
			return;
		}
		
		if (Messagebox.show("Are you sure you wish to delete?", "Confirmation",
				Messagebox.OK | Messagebox.NO, Messagebox.QUESTION) != Messagebox.OK) {
			return;
		}

		try {
			MstbAcquirerPymtType acquirerPymtType = (MstbAcquirerPymtType)this.businessHelper.getGenericBusiness().get(MstbAcquirerPymtType.class, (Integer)item.getValue());
			this.businessHelper.getGenericBusiness().delete(acquirerPymtType);
			
			item.detach();
			Messagebox.show("Acquirer Payment Type has been successfully deleted", "Delete Acquirer Payment Type",
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
			Integer pymtTypeNo = (Integer)resultList.getSelectedItem().getValue();
			Map map = new HashMap();
			map.put("pymtTypeNo", pymtTypeNo);
			
			MstbAcquirerPymtType acquirerPymtType = (MstbAcquirerPymtType)this.businessHelper.getGenericBusiness().get(MstbAcquirerPymtType.class, pymtTypeNo);

			Timestamp t1 = DateUtil.convertTimestampTo0000Hours(acquirerPymtType.getEffectiveDtFrom());
			Timestamp t2 = DateUtil.convertTimestampTo0000Hours(DateUtil.getCurrentTimestamp());
			if(t1.compareTo(t2) >= 0)
				if(this.checkUriAccess(Uri.EDIT_ACQUIRER_PAYMENT_TYPE))
					forward(Uri.EDIT_ACQUIRER_PAYMENT_TYPE, map);
				else if(this.checkUriAccess(Uri.VIEW_ACQUIRER_PAYMENT_TYPE))
					forward(Uri.VIEW_ACQUIRER_PAYMENT_TYPE, map);
				else{
					Messagebox.show(com.cdgtaxi.ibs.common.NonConfigurableConstants.ACCESS_DENIED,
							"Access Denied", Messagebox.OK, Messagebox.EXCLAMATION);
					return;
				}
			else
				if(this.checkUriAccess(Uri.VIEW_ACQUIRER_PAYMENT_TYPE))
					forward(Uri.VIEW_ACQUIRER_PAYMENT_TYPE, map);
				else{
					Messagebox.show(com.cdgtaxi.ibs.common.NonConfigurableConstants.ACCESS_DENIED,
							"Access Denied", Messagebox.OK, Messagebox.EXCLAMATION);
					return;
				}
			//User is allowed to edit the values as long as the payment type effective from date has not taken effect.
			/*if(acquirerPymtType.getEffectiveDtFrom().compareTo(DateUtil.getCurrentTimestamp()) <= 0 &&
					acquirerPymtType.getEffectiveDtTo() != null)
				this.forward(Uri.VIEW_ACQUIRER_PAYMENT_TYPE, map);
			else
				this.forward(Uri.EDIT_ACQUIRER_PAYMENT_TYPE, map);*/
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
		this.forward(Uri.CREATE_ACQUIRER_PAYMENT_TYPE, null);
	}
	
	private SearchAcquirerPaymentTypeForm buildSearchForm(){
		SearchAcquirerPaymentTypeForm form = new SearchAcquirerPaymentTypeForm();
		
		if(acquirerTypeListBox.getSelectedItem().getValue()!=null)
			form.acquirerNo = ((MstbAcquirer)acquirerTypeListBox.getSelectedItem().getValue()).getAcquirerNo();
		if(form.acquirerNo!=null) form.isAtLeastOneCriteriaSelected = true;
		if(valueTypeListBox.getSelectedItem().getValue()!=null)
			form.value = (String)valueTypeListBox.getSelectedItem().getValue();
		if(form.value!=null && form.value.length()>0) form.isAtLeastOneCriteriaSelected = true;
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
	public void reset(){
		acquirerTypeListBox.setSelectedIndex(0);
		this.populatePaymentType();
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
