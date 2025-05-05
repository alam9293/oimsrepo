package com.cdgtaxi.ibs.admin.ui;

import java.util.Date;
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
public class EditNonBillableDetailGLWindow extends CommonWindow implements AfterCompose {

	private FmtbNonBillableDetail detail;
	private FmtbNonBillableMaster master;
	private Label serviceProviderLabel, cardTypeLabel;
	private Label createdByLabel,createdDateLabel,createdTimeLabel,lastUpdatedByLabel,lastUpdatedDateLabel,lastUpdatedTimeLabel;
	private Integer detailNo;
	private Listbox entityListBox, arControlCodeListBox, fAmountTxnCodeListBox, aFeeTxnCodeListBox, premiumAmountTxnCodeListBox;
	private Datebox effDateDateBox;
	
	@SuppressWarnings("unchecked")
	public void afterCompose() {
		//wire variables
		Components.wireVariables(this, this);
		
		Map map = Executions.getCurrent().getArg();
		detailNo = (Integer)map.get("detailNo");	
		
		detail = this.businessHelper.getAdminBusiness().getNonBillableDetail(detailNo);
		master = this.businessHelper.getAdminBusiness().getNonBillableMaster(detail.getFmtbNonBillableMaster().getMasterNo());

		serviceProviderLabel.setValue(master.getMstbMasterTableByServiceProvider().getMasterValue());
		cardTypeLabel.setValue(master.getMstbMasterTableByPymtTypeMasterNo().getMasterValue());
		effDateDateBox.setValue(detail.getEffectiveDate());
		
		List<FmtbEntityMaster> entities = businessHelper.getAdminBusiness().getActiveEntities();
		for (FmtbEntityMaster entity : entities) {
			Listitem item = new Listitem();
			item.setValue(entity.getEntityNo());
			item.setLabel(entity.getEntityName());
			
			if(detail.getFmtbArContCodeMaster()!=null)
				if(detail.getFmtbArContCodeMaster().getFmtbEntityMaster().getEntityNo().equals(entity.getEntityNo()))
					item.setSelected(true);
			entityListBox.appendChild(item);
		}
		if(entityListBox.getSelectedItem()==null) entityListBox.setSelectedIndex(0);

		List<FmtbArContCodeMaster> controlCodes = this.businessHelper.getAdminBusiness().getARControlCode(detail.getFmtbArContCodeMaster().getFmtbEntityMaster().getEntityNo());

		arControlCodeListBox.appendChild(new Listitem("-", null));
		if(controlCodes.size()>0){
			for(FmtbArContCodeMaster controlCode : controlCodes){
				Listitem item = new Listitem();
				item.setValue(controlCode);
				item.setLabel(controlCode.getArControlCode());
				
				if(detail.getFmtbArContCodeMaster()!=null)
					if(detail.getFmtbArContCodeMaster().getArControlCodeNo().equals(controlCode.getArControlCodeNo()))
						item.setSelected(true);
				arControlCodeListBox.appendChild(item);
			}
		}
		if(arControlCodeListBox.getSelectedItem()==null) arControlCodeListBox.setSelectedIndex(0);

		
		fAmountTxnCodeListBox.appendChild(new Listitem("-", null));
		if(entityListBox.getSelectedItem()!=null)
			if(entityListBox.getSelectedItem().getValue()!=null){
				Integer entityNo = (Integer) entityListBox.getSelectedItem().getValue();
				List<String> transactionCodes = this.businessHelper.getAdminBusiness().getTransactionCodeType(entityNo, NonConfigurableConstants.TRANSACTION_TYPE_NON_BILLABLE_FARE_AMOUNT);
				for(String transactionCode : transactionCodes){
					Listitem item = new Listitem();
					item.setValue(transactionCode);
					item.setLabel(transactionCode);
					
					if(detail.getFareAmountTxnCode().equals(transactionCode))
						item.setSelected(true);
					
					fAmountTxnCodeListBox.appendChild(item);
				}
			}
		if(fAmountTxnCodeListBox.getSelectedItem()==null)fAmountTxnCodeListBox.setSelectedIndex(0);

		aFeeTxnCodeListBox.appendChild(new Listitem("-", null));
		if(entityListBox.getSelectedItem()!=null)
			if(entityListBox.getSelectedItem().getValue()!=null){
				Integer entityNo = (Integer) entityListBox.getSelectedItem().getValue();
				List<String> aTransactionCodes = this.businessHelper.getAdminBusiness().getTransactionCodeType(entityNo, NonConfigurableConstants.TRANSACTION_TYPE_NON_BILLABLE_ADMIN_AMOUNT);
				for(String aTransactionCode : aTransactionCodes){
					Listitem item = new Listitem();
					item.setValue(aTransactionCode);
					item.setLabel(aTransactionCode);
					if(detail.getAdminFeeTxnCode().equals(aTransactionCode))
						item.setSelected(true);
					aFeeTxnCodeListBox.appendChild(item);
				}
			}
		if(aFeeTxnCodeListBox.getSelectedItem()==null)aFeeTxnCodeListBox.setSelectedIndex(0);
		
		premiumAmountTxnCodeListBox.appendChild(new Listitem("-", null));
		if(entityListBox.getSelectedItem()!=null)
			if(entityListBox.getSelectedItem().getValue()!=null){
				Integer entityNo = (Integer) entityListBox.getSelectedItem().getValue();
				List<String> transactionCodes = this.businessHelper.getAdminBusiness().getTransactionCodeType(entityNo, NonConfigurableConstants.TRANSACTION_TYPE_NON_BILLABLE_PREMIUM_AMOUNT);
				for(String transactionCode : transactionCodes){
					Listitem item = new Listitem();
					item.setValue(transactionCode);
					item.setLabel(transactionCode);
					
					if(detail.getPremiumAmountTxnCode().equals(transactionCode))
						item.setSelected(true);
					
					premiumAmountTxnCodeListBox.appendChild(item);
				}
			}
		if(premiumAmountTxnCodeListBox.getSelectedItem()==null)premiumAmountTxnCodeListBox.setSelectedIndex(0);
		
		if(detail.getCreatedBy()!=null) createdByLabel.setValue(detail.getCreatedBy());
		else createdByLabel.setValue("-");
		if(detail.getCreatedDt()!=null) createdDateLabel.setValue(DateUtil.convertDateToStr(detail.getCreatedDt(), DateUtil.GLOBAL_DATE_FORMAT));
		else createdDateLabel.setValue("-");
		if(detail.getCreatedDt()!=null) createdTimeLabel.setValue(DateUtil.convertDateToStr(detail.getCreatedDt(), DateUtil.GLOBAL_TIME_FORMAT));
		else createdTimeLabel.setValue("-");
		
		if(detail.getUpdatedBy()!=null) lastUpdatedByLabel.setValue(detail.getUpdatedBy());
		else lastUpdatedByLabel.setValue("-");
		if(detail.getUpdatedDt()!=null) lastUpdatedDateLabel.setValue(DateUtil.convertDateToStr(detail.getUpdatedDt(), DateUtil.GLOBAL_DATE_FORMAT));
		else lastUpdatedDateLabel.setValue("-");
		if(detail.getUpdatedDt()!=null) lastUpdatedTimeLabel.setValue(DateUtil.convertDateToStr(detail.getUpdatedDt(), DateUtil.GLOBAL_TIME_FORMAT));
		else lastUpdatedTimeLabel.setValue("-");
	}
	
	public void save() throws InterruptedException{
		try{
			FmtbArContCodeMaster controlCode = (FmtbArContCodeMaster)arControlCodeListBox.getSelectedItem().getValue();
			String fareAmountTxnCode = (String)fAmountTxnCodeListBox.getSelectedItem().getValue();
			String adminFeeTxnCode = (String)aFeeTxnCodeListBox.getSelectedItem().getValue();
			String premiumAmountTxnCode = (String)premiumAmountTxnCodeListBox.getSelectedItem().getValue();
			Date effectiveDate = effDateDateBox.getValue();
			
			if(controlCode!=null && fareAmountTxnCode!=null && adminFeeTxnCode!=null){
				detail.setFmtbArContCodeMaster(controlCode);
				detail.setFareAmountTxnCode(fareAmountTxnCode);
				detail.setAdminFeeTxnCode(adminFeeTxnCode);
				detail.setPremiumAmountTxnCode(premiumAmountTxnCode);
				detail.setEffectiveDate(DateUtil.convertUtilDateToSqlDate(effectiveDate));
				if(this.businessHelper.getAdminBusiness().hasDuplicateRecord(master,detail)){
					Messagebox.show("Duplicate record found.","Error", Messagebox.OK, Messagebox.ERROR);
				}else{
					this.checkTxnCodeEffectedBeforeNewDetail(detail);
					this.businessHelper.getGenericBusiness().update(detail, getUserLoginIdAndDomain());
					Messagebox.show("Non Billable GL updated.", "Edit Non Billable GL", Messagebox.OK, Messagebox.INFORMATION);
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
			Messagebox.show(dve.getMessage(), "Edit Non Billable GL", Messagebox.OK, Messagebox.INFORMATION);
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
			List<String> transactionCodes = this.businessHelper.getAdminBusiness().getTransactionCodeType(key, NonConfigurableConstants.TRANSACTION_TYPE_NON_BILLABLE_FARE_AMOUNT);
			for(String transactionCode : transactionCodes){
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
			List<String> aTransactionCodes = this.businessHelper.getAdminBusiness().getTransactionCodeType(key, NonConfigurableConstants.TRANSACTION_TYPE_NON_BILLABLE_ADMIN_AMOUNT);
			for(String aTransactionCode : aTransactionCodes){
				Listitem item = new Listitem();
				item.setValue(aTransactionCode);
				item.setLabel(aTransactionCode);

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
			List<String> transactionCodes = this.businessHelper.getAdminBusiness().getTransactionCodeType(key, NonConfigurableConstants.TRANSACTION_TYPE_NON_BILLABLE_PREMIUM_AMOUNT);
			for(String transactionCode : transactionCodes){
				Listitem item = new Listitem();
				item.setValue(transactionCode);
				item.setLabel(transactionCode);

				premiumAmountTxnCodeListBox.appendChild(item);
			}
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

		if(premiumAmountTxnCode == null)
			throw new DataValidationError("Premium Amount Txn Code has not yet been configured!");
		else if(premiumAmountTxnCode.getEffectiveDate().after(detail.getEffectiveDate()))
			throw new DataValidationError("Selected Premium Amount Txn Code has not yet taken effect based on detail's effective date!");
	}
	
	@Override
	public void refresh() throws InterruptedException {
		
	}
}
