package com.cdgtaxi.ibs.admin.ui;

import java.util.List;
import java.util.Map;

import org.springframework.orm.hibernate3.HibernateOptimisticLockingFailureException;
import org.zkoss.zk.ui.Components;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zk.ui.ext.AfterCompose;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Label;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Messagebox;

import com.cdgtaxi.ibs.common.NonConfigurableConstants;
import com.cdgtaxi.ibs.common.exception.DataValidationError;
import com.cdgtaxi.ibs.common.model.FmtbArContCodeMaster;
import com.cdgtaxi.ibs.common.model.FmtbEntityMaster;
import com.cdgtaxi.ibs.common.model.FmtbNonBillableDetail;
import com.cdgtaxi.ibs.common.model.FmtbNonBillableMaster;
import com.cdgtaxi.ibs.common.model.FmtbTransactionCode;
import com.cdgtaxi.ibs.common.ui.CommonWindow;
import com.cdgtaxi.ibs.util.DateUtil;

@SuppressWarnings("serial")
public class CreateNonBillableDetailGLWindow extends CommonWindow implements AfterCompose {

	private Integer masterNo;
	private FmtbNonBillableMaster master;
	private Label serviceProviderLabel, cardTypeLabel;
	private Listbox entityListBox, arControlCodeListBox, fAmountTxnCodeListBox, aFeeTxnCodeListBox, premiumAmountTxnCodeListBox;
	private Datebox effDateDateBox;
	
	@SuppressWarnings("unchecked")
	public void afterCompose() {
		//wire variables
		Components.wireVariables(this, this);
		
		Listitem emptyItem = new Listitem("-", null);
		Map map = Executions.getCurrent().getArg();
		masterNo = (Integer)map.get("masterNo");
		master = this.businessHelper.getAdminBusiness().getNonBillableMaster(masterNo);

		serviceProviderLabel.setValue(master.getMstbMasterTableByServiceProvider().getMasterValue());
		cardTypeLabel.setValue(master.getMstbMasterTableByPymtTypeMasterNo().getMasterValue());
		
		entityListBox.appendChild((Listitem)emptyItem.clone());
		List<FmtbEntityMaster> entities = businessHelper.getAdminBusiness().getActiveEntities();
		for (FmtbEntityMaster entity : entities) {
			Listitem item = new Listitem();
			item.setValue(entity.getEntityNo());
			item.setLabel(entity.getEntityName());
			entityListBox.appendChild(item);
		}
		entityListBox.setSelectedIndex(0);

		arControlCodeListBox.appendChild((Listitem)emptyItem.clone());
		arControlCodeListBox.setSelectedIndex(0);
		
		fAmountTxnCodeListBox.appendChild((Listitem)emptyItem.clone());
		fAmountTxnCodeListBox.setSelectedIndex(0);

		aFeeTxnCodeListBox.appendChild((Listitem)emptyItem.clone());
		aFeeTxnCodeListBox.setSelectedIndex(0);
		
		premiumAmountTxnCodeListBox.appendChild((Listitem)emptyItem.clone());
		premiumAmountTxnCodeListBox.setSelectedIndex(0);
	}
	
	public void populateList(){
		this.populateARControlCode();
		this.populateFareAmountTxnCode();
		this.populateAdminAmountTxnCode();
		this.populatePremiumAmountTxnCode();
	}
	
	private void populateARControlCode(){
		Integer key = (Integer)entityListBox.getSelectedItem().getValue();
		Listitem emptyItem = new Listitem("-", null);
		arControlCodeListBox.getChildren().clear();
		arControlCodeListBox.appendChild((Listitem)emptyItem.clone());
		
		
		if(key!=null){
			List<FmtbArContCodeMaster> controlCodes = this.businessHelper.getAdminBusiness().getARControlCode(key);

			if(controlCodes.size()>0){
				for(FmtbArContCodeMaster controlCode : controlCodes){
					Listitem item = new Listitem();
					item.setValue(controlCode);
					item.setLabel(controlCode.getArControlCode());
					arControlCodeListBox.appendChild(item);
				}
			}
		}
		arControlCodeListBox.setSelectedIndex(0);
	}
	
	private void populateFareAmountTxnCode(){
		Integer key = (Integer)entityListBox.getSelectedItem().getValue();
		Listitem emptyItem = new Listitem("-", null);
		fAmountTxnCodeListBox.getChildren().clear();
		fAmountTxnCodeListBox.appendChild((Listitem)emptyItem.clone());
		fAmountTxnCodeListBox.setSelectedIndex(0);
		
		if(key!=null){
			List<String> nonBillableTxnCodes = this.businessHelper.getAdminBusiness().getTransactionCodeType(key, NonConfigurableConstants.TRANSACTION_TYPE_NON_BILLABLE_FARE_AMOUNT);
			for(String transactionCode : nonBillableTxnCodes){
				Listitem item = new Listitem();
				item.setValue(transactionCode);
				item.setLabel(transactionCode);

				fAmountTxnCodeListBox.appendChild(item);
			}
		}
	}
	
	private void populateAdminAmountTxnCode(){
		Integer key = (Integer)entityListBox.getSelectedItem().getValue();
		Listitem emptyItem = new Listitem("-", null);
		aFeeTxnCodeListBox.getChildren().clear();
		aFeeTxnCodeListBox.appendChild((Listitem)emptyItem.clone());
		aFeeTxnCodeListBox.setSelectedIndex(0);
		
		if(key!=null){
			List<String> adminTxnCodes = this.businessHelper.getAdminBusiness().getTransactionCodeType(key, NonConfigurableConstants.TRANSACTION_TYPE_NON_BILLABLE_ADMIN_AMOUNT);
			for(String adminTxnCode : adminTxnCodes){
				Listitem item = new Listitem();
				item.setValue(adminTxnCode);
				item.setLabel(adminTxnCode);

				aFeeTxnCodeListBox.appendChild(item);
			}
		}
	}
	
	private void populatePremiumAmountTxnCode(){
		Integer key = (Integer)entityListBox.getSelectedItem().getValue();
		Listitem emptyItem = new Listitem("-", null);
		premiumAmountTxnCodeListBox.getChildren().clear();
		premiumAmountTxnCodeListBox.appendChild((Listitem)emptyItem.clone());
		premiumAmountTxnCodeListBox.setSelectedIndex(0);
		
		if(key!=null){
			List<String> nonBillableTxnCodes = this.businessHelper.getAdminBusiness().getTransactionCodeType(key, NonConfigurableConstants.TRANSACTION_TYPE_NON_BILLABLE_PREMIUM_AMOUNT);
			for(String transactionCode : nonBillableTxnCodes){
				Listitem item = new Listitem();
				item.setValue(transactionCode);
				item.setLabel(transactionCode);

				premiumAmountTxnCodeListBox.appendChild(item);
			}
		}
	}
	
	public void create() throws InterruptedException{
		try{
			FmtbArContCodeMaster controlCode = (FmtbArContCodeMaster)arControlCodeListBox.getSelectedItem().getValue();
			String fareAmountTxnCode = (String)fAmountTxnCodeListBox.getSelectedItem().getValue();
			String adminFeeTxnCode = (String)aFeeTxnCodeListBox.getSelectedItem().getValue();
			String premiumAmountTxnCode = (String)premiumAmountTxnCodeListBox.getSelectedItem().getValue();
			
			if(controlCode!=null && fareAmountTxnCode!=null && adminFeeTxnCode!=null && effDateDateBox.getValue()!=null){
				FmtbNonBillableDetail detail = new FmtbNonBillableDetail();
				detail.setFmtbArContCodeMaster(controlCode);
				detail.setFareAmountTxnCode(fareAmountTxnCode);
				detail.setAdminFeeTxnCode(adminFeeTxnCode);
				detail.setPremiumAmountTxnCode(premiumAmountTxnCode);
				detail.setEffectiveDate(DateUtil.convertUtilDateToSqlDate(effDateDateBox.getValue()));
				detail.setFmtbNonBillableMaster(master);
				if(this.businessHelper.getAdminBusiness().hasDuplicateRecord(master,detail)){
					Messagebox.show("Duplicate record found.","Error", Messagebox.OK, Messagebox.ERROR);
				}else{
					this.checkTxnCodeEffectedBeforeNewDetail(detail);
					this.businessHelper.getGenericBusiness().save(detail, getUserLoginIdAndDomain());
					Messagebox.show("Non Billable GL Created.", "Create Non Billable GL", Messagebox.OK, Messagebox.INFORMATION);
					this.back();
				}
			}else{
				Messagebox.show("All fields are mandatory.", "Edit Non Billable Detail GL", Messagebox.OK, Messagebox.INFORMATION);
			}
		}
		catch(WrongValueException wve){
			throw wve;
		}
		catch(DataValidationError dve){
			Messagebox.show(dve.getMessage(), "Create Non Billable GL", Messagebox.OK, Messagebox.INFORMATION);
			dve.printStackTrace();
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
	
	private void checkTxnCodeEffectedBeforeNewDetail(FmtbNonBillableDetail detail) throws DataValidationError{
		FmtbTransactionCode fareTxnCode = this.businessHelper.getAdminBusiness().getEarliestEffectedTxnCode(
				NonConfigurableConstants.TRANSACTION_TYPE_NON_BILLABLE_FARE_AMOUNT, detail.getFareAmountTxnCode());
		
		if(fareTxnCode.getEffectiveDate().after(detail.getEffectiveDate()))
			throw new DataValidationError("Selected Fare Txn Code has not yet taken effect based on detail's effective date!");
		
		FmtbTransactionCode adminTxnCode = this.businessHelper.getAdminBusiness().getEarliestEffectedTxnCode(
				NonConfigurableConstants.TRANSACTION_TYPE_NON_BILLABLE_ADMIN_AMOUNT, detail.getAdminFeeTxnCode());
		
		if(adminTxnCode.getEffectiveDate().after(detail.getEffectiveDate()))
			throw new DataValidationError("Selected Admin Fee Txn Code has not yet taken effect based on detail's effective date!");
		
		FmtbTransactionCode premiumAmountTxnCode = this.businessHelper.getAdminBusiness().getEarliestEffectedTxnCode(
				NonConfigurableConstants.TRANSACTION_TYPE_NON_BILLABLE_PREMIUM_AMOUNT, detail.getPremiumAmountTxnCode());
		
		if(premiumAmountTxnCode.getEffectiveDate().after(detail.getEffectiveDate()))
			throw new DataValidationError("Selected Premium Amount Txn Code has not yet taken effect based on detail's effective date!");
	}
	
	@Override
	public void refresh() throws InterruptedException {
		
	}
}
