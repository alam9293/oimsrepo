package com.cdgtaxi.ibs.admin.ui;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.Map.Entry;

import org.apache.log4j.Logger;
import org.springframework.orm.hibernate5.HibernateOptimisticLockingFailureException;
import org.zkoss.zk.ui.Components;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zk.ui.metainfo.EventHandler;
import org.zkoss.zk.ui.metainfo.ZScript;
import org.zkoss.zk.ui.util.ConditionImpl;
import org.zkoss.zul.Button;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Image;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Messagebox;

import com.cdgtaxi.ibs.Uri;
import com.cdgtaxi.ibs.common.ConfigurableConstants;
import com.cdgtaxi.ibs.common.NonConfigurableConstants;
import com.cdgtaxi.ibs.common.model.FmtbEntityMaster;
import com.cdgtaxi.ibs.common.model.FmtbTransactionCode;
import com.cdgtaxi.ibs.common.model.PmtbProductType;
import com.cdgtaxi.ibs.common.model.forms.SearchTransactionCodeForm;
import com.cdgtaxi.ibs.common.ui.CommonWindow;
import com.cdgtaxi.ibs.master.model.MstbMasterTable;
import com.cdgtaxi.ibs.util.ComponentUtil;
import com.cdgtaxi.ibs.util.DateUtil;
import com.cdgtaxi.ibs.web.component.CapsTextbox;

@SuppressWarnings("serial")
public class ManageTransactionCodeWindow extends CommonWindow{
	private static final Logger logger = Logger.getLogger(ManageTransactionCodeWindow.class);

	private Combobox entityCB, taxTypeCB;
	private CapsTextbox txnCodeTB;
	private Listbox resultList, txnTypeLB;
	private Datebox effDateFromDatebox, effDateToDatebox;
	private Button createBtn;

	private List<Object[]> codes;

	public void onCreate() throws InterruptedException{
		//wire variables
		Components.wireVariables(this, this);
		
		List<FmtbEntityMaster> entities = businessHelper.getAdminBusiness().getEntities();
		entityCB.appendChild(newComboitem(null, "-"));
		entityCB.setSelectedIndex(0);
		for (FmtbEntityMaster entity : entities) {
			entityCB.appendChild(newComboitem(entity.getEntityNo(), entity.getEntityName()));
		}

		taxTypeCB.appendChild(newComboitem(null, "-"));
		taxTypeCB.setSelectedIndex(0);
		Set<Entry<String, String>> entries = ConfigurableConstants.getAllMasters(ConfigurableConstants.TAX_TYPE).entrySet();
		for(Entry<String, String> entry : entries){
			taxTypeCB.appendChild(newComboitem(entry.getKey(), entry.getValue()));
		}
		
		txnTypeLB.appendChild(ComponentUtil.createNotRequiredListItem(null));
		txnTypeLB.setSelectedIndex(0);
		Set<Entry<String, String>> txnTypeEntries = NonConfigurableConstants.TRANSACTION_TYPE.entrySet();
		for(Entry<String, String> entry : txnTypeEntries){
			txnTypeLB.appendChild(new Listitem(entry.getValue(), entry.getKey()));
		}
		
		codes = businessHelper.getAdminBusiness().getTransactionCodes(this.buildSearchForm());
		buildResultList();
		
		if(!this.checkUriAccess(Uri.CREATE_TRANSACTION_CODE)){
			createBtn.setDisabled(true);
		}
	}

	public void search() throws InterruptedException {
		this.displayProcessing();
		if(effDateFromDatebox.getValue()!=null && effDateToDatebox.getValue()!=null)
			if(effDateFromDatebox.getValue().compareTo(effDateToDatebox.getValue()) == 1)
				throw new WrongValueException(effDateFromDatebox, "Effective Date From cannot be later than Effective Date To.");
		SearchTransactionCodeForm form = this.buildSearchForm();
		if(!form.isAtLeastOneCriteriaSelected){
			Messagebox.show("Please provide at least one search criteria", 
					"Error", Messagebox.OK, Messagebox.ERROR);
			return;
		}
		codes = businessHelper.getAdminBusiness().getTransactionCodes(form);
		buildResultList();
	}
	
	private SearchTransactionCodeForm buildSearchForm(){
		SearchTransactionCodeForm form = new SearchTransactionCodeForm();
		
		if(entityCB.getSelectedItem().getValue()!=null)
			form.entityNo = (Integer)entityCB.getSelectedItem().getValue();
		if(form.entityNo!=null) form.isAtLeastOneCriteriaSelected = true;
		if(taxTypeCB.getSelectedItem().getValue()!=null)
			form.taxType = (String)taxTypeCB.getSelectedItem().getValue();
		if(form.taxType!=null) form.isAtLeastOneCriteriaSelected = true;
		if(txnCodeTB.getValue()!=null)
			form.txnCode = (String)txnCodeTB.getValue();
		if(form.txnCode!=null) form.isAtLeastOneCriteriaSelected = true;
		if(txnTypeLB.getSelectedItem().getValue()!=null)
			form.txnType = (String)txnTypeLB.getSelectedItem().getValue();
		if(form.txnType!=null) form.isAtLeastOneCriteriaSelected = true;
		
		form.effDateFromDate = DateUtil.convertUtilDateToSqlDate(effDateFromDatebox.getValue());
		if(form.effDateFromDate!=null) form.isAtLeastOneCriteriaSelected = true;
		form.effDateToDate = DateUtil.convertUtilDateToSqlDate(effDateToDatebox.getValue());
		if(form.effDateToDate!=null) form.isAtLeastOneCriteriaSelected = true;
		
		if(effDateFromDatebox.getValue()!=null && effDateToDatebox.getValue()==null){
			effDateToDatebox.setValue(effDateFromDatebox.getValue());
			form.effDateToDate = DateUtil.convertUtilDateToSqlDate(effDateFromDatebox.getValue());
		}else if(effDateFromDatebox.getValue()==null && effDateToDatebox.getValue()!=null){
			effDateFromDatebox.setValue(effDateToDatebox.getValue());
			form.effDateFromDate = DateUtil.convertUtilDateToSqlDate(effDateToDatebox.getValue());
		}	
		return form;
	}

	public void edit() throws InterruptedException {
		try {
			Listitem selectedItem = resultList.getSelectedItem();
			FmtbTransactionCode transactionCode = (FmtbTransactionCode)selectedItem.getValue();

			/*if(transactionCode.getEffectiveDate().compareTo(DateUtil.getCurrentDate()) != 1)
				return;
			*/
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("transactionCodeNo", transactionCode.getTransactionCodeNo());
			
			Timestamp t1 = DateUtil.convertTimestampTo0000Hours(DateUtil.convertDateToTimestamp(transactionCode.getEffectiveDate()));
			Timestamp t2 = DateUtil.convertTimestampTo0000Hours(DateUtil.getCurrentTimestamp());
			if(t1.compareTo(t2) >= 0)
				if(this.checkUriAccess(Uri.EDIT_TRANSACTION_CODE))
					forward(Uri.EDIT_TRANSACTION_CODE, map);
				else if(this.checkUriAccess(Uri.VIEW_TRANSACTION_CODE))
					forward(Uri.VIEW_TRANSACTION_CODE, map);
				else{
					Messagebox.show(com.cdgtaxi.ibs.common.NonConfigurableConstants.ACCESS_DENIED,
							"Access Denied", Messagebox.OK, Messagebox.EXCLAMATION);
					return;
				}
			else
				if(this.checkUriAccess(Uri.VIEW_TRANSACTION_CODE))
					forward(Uri.VIEW_TRANSACTION_CODE, map);
				else{
					Messagebox.show(com.cdgtaxi.ibs.common.NonConfigurableConstants.ACCESS_DENIED,
							"Access Denied", Messagebox.OK, Messagebox.EXCLAMATION);
					return;
				}
		} 
		catch(Exception e){
			Messagebox.show(com.cdgtaxi.ibs.common.NonConfigurableConstants.COMMON_ERROR_MESSAGE,
					"Error", Messagebox.OK, Messagebox.ERROR);
			e.printStackTrace();
		}
		finally {
			resultList.clearSelection();
		}
	}

	public void create() throws InterruptedException {
		try {
			forward(Uri.CREATE_TRANSACTION_CODE, null);
		}
		catch(Exception e){
			Messagebox.show(com.cdgtaxi.ibs.common.NonConfigurableConstants.COMMON_ERROR_MESSAGE,
					"Error", Messagebox.OK, Messagebox.ERROR);
			e.printStackTrace();
		}
	}

	public void delete(Listitem item) throws InterruptedException {
		if(!this.checkUriAccess(Uri.DELETE_TRANSACTION_CODE)){
			Messagebox.show(com.cdgtaxi.ibs.common.NonConfigurableConstants.ACCESS_DENIED,
					"Access Denied", Messagebox.OK, Messagebox.EXCLAMATION);
			return;
		}
		
		if (Messagebox.show("Are you sure you wish to delete this transaction code?", "Confirmation",
				Messagebox.OK | Messagebox.NO, Messagebox.QUESTION) != Messagebox.OK) {
			return;
		}

		try {
			businessHelper.getGenericBusiness().delete(item.getValue());
			item.detach();
			Messagebox.show("Transaction Code has been successfully deleted", "Delete Transaction Code",
					Messagebox.OK, Messagebox.INFORMATION);
		}
		catch(WrongValueException wve){
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

	@Override
	public void refresh() throws InterruptedException {
		search();
	}

	private void buildResultList() throws InterruptedException {
		resultList.getItems().clear();
		if (resultList.getListfoot() != null) {
			resultList.getListfoot().detach();
		}
		try{
		if (codes.size() == 0) {
			resultList.appendChild(ComponentUtil.createNoRecordsFoundListFoot(11));
		} else {
			if(codes.size()>ConfigurableConstants.getMaxQueryResult())
				Messagebox.show(NonConfigurableConstants.getExceedMaxResultMessage(), "Alert", Messagebox.OK, Messagebox.INFORMATION);
			for(Object[] objectArray : codes){
				FmtbTransactionCode code = (FmtbTransactionCode)objectArray[0];
				FmtbEntityMaster entity = (FmtbEntityMaster)objectArray[1];
				PmtbProductType productType = (PmtbProductType)objectArray[2];
				MstbMasterTable master = (MstbMasterTable)objectArray[3];

				Listitem item = new Listitem();
				item.setValue(code);
				
				item.appendChild(newListcell(entity.getEntityName()));
				item.appendChild(newListcell(code.getDescription()));
				if(productType!=null) item.appendChild(newListcell(productType.getName()));
				else item.appendChild(newEmptyListcell("", "-"));
				item.appendChild(newListcell(NonConfigurableConstants.TRANSACTION_TYPE.get(code.getTxnType())));
				item.appendChild(newListcell(code.getTxnCode()));
				item.appendChild(newListcell(code.getDiscountable()));
				item.appendChild(newListcell(master.getMasterValue()));
				item.appendChild(newListcell(code.getGlCode()));
				item.appendChild(newListcell(code.getCostCentre()));
				item.appendChild(newListcell(code.getDiscountGlCode()));
				item.appendChild(newListcell(code.getDiscountCostCentre()));
				item.appendChild(newListcell(code.getEffectiveDate()));
				
				//Not taken effect then will allow delete
				if(code.getEffectiveDate().compareTo(DateUtil.getCurrentDate()) == 1){
					Image deleteImage = new Image("/images/delete.png");
					deleteImage.setStyle("cursor:pointer");
					ZScript showInfo = ZScript.parseContent("manageTransactionCodeWindow.delete(self.getParent().getParent())");
					showInfo.setLanguage("java");
					EventHandler event = new EventHandler(showInfo, ConditionImpl.getInstance(null, null));
					deleteImage.addEventHandler("onClick", event);
					Listcell imageCell = new Listcell();
					imageCell.appendChild(deleteImage);
					item.appendChild(imageCell);
				}
				else
					item.appendChild(new Listcell());
				
				resultList.appendChild(item);
			}
		}
		}catch(WrongValueException wve){
			throw wve;
		}
		catch(Exception e){
			Messagebox.show(com.cdgtaxi.ibs.common.NonConfigurableConstants.COMMON_ERROR_MESSAGE, 
					"Error", Messagebox.OK, Messagebox.ERROR);
			e.printStackTrace();
		}
	}
	
	public void reset(){
		resultList.clearSelection();
		resultList.getItems().clear();
		if (resultList.getListfoot() == null) 
			resultList.appendChild(ComponentUtil.createNoRecordsFoundListFoot(13));
		
		entityCB.setSelectedIndex(0);
		taxTypeCB.setSelectedIndex(0);
		txnTypeLB.setSelectedIndex(0);
		
		txnCodeTB.setText("");
		effDateFromDatebox.setText("");
		effDateToDatebox.setText("");
	}
}
