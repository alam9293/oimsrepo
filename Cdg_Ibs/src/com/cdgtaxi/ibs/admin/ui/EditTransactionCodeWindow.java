package com.cdgtaxi.ibs.admin.ui;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.log4j.Logger;
import org.springframework.orm.hibernate3.HibernateOptimisticLockingFailureException;
import org.zkoss.zk.ui.Components;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zk.ui.ext.AfterCompose;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Comboitem;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.Label;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Row;
import org.zkoss.zul.Textbox;

import com.cdgtaxi.ibs.common.ConfigurableConstants;
import com.cdgtaxi.ibs.common.NonConfigurableConstants;
import com.cdgtaxi.ibs.common.exception.DuplicateEffectiveDateError;
import com.cdgtaxi.ibs.common.model.FmtbEntityMaster;
import com.cdgtaxi.ibs.common.model.FmtbTransactionCode;
import com.cdgtaxi.ibs.common.model.PmtbProductType;
import com.cdgtaxi.ibs.common.ui.CommonWindow;
import com.cdgtaxi.ibs.util.DateUtil;

@SuppressWarnings("serial")
public class EditTransactionCodeWindow extends CommonWindow implements AfterCompose{
	private static final Logger logger = Logger.getLogger(EditTransactionCodeWindow.class);

	private final FmtbTransactionCode transactionCode;
	private Combobox entityCB;
	private Combobox productTypeCB;
	private Textbox txnCodeTB;
	private Textbox descriptionTB;
	private Combobox txnTypeCB;
	private Intbox glCodeTB;
	private Textbox costCentreTB;
	private Checkbox discountableCB;
	private Row discountRow;
	private Intbox discountGlCodeTB;
	private Textbox discountCostCentreTB;
	private Combobox taxTypeCB;
	private Checkbox isManualCB;
	private Datebox effectiveDateDB;
	
	private Label createdByLabel, createdDateLabel, createdTimeLabel, 
				  lastUpdatedByLabel, lastUpdatedDateLabel, lastUpdatedTimeLabel;

	@Override
	public void refresh() throws InterruptedException {
	}

	@SuppressWarnings("unchecked")
	public EditTransactionCodeWindow() {
		Map params = Executions.getCurrent().getArg();
		Integer transactionCodeNo =  (Integer) params.get("transactionCodeNo");
		logger.info("Transaction Code No = " + transactionCodeNo);
		transactionCode = businessHelper.getAdminBusiness().getTransactionCode(transactionCodeNo);
	}

	public void afterCompose() {
		Components.wireVariables(this, this);
		txnCodeTB.setValue(transactionCode.getTxnCode());
		descriptionTB.setValue(transactionCode.getDescription());
		glCodeTB.setValue(new Integer(transactionCode.getGlCode()));
		costCentreTB.setValue(transactionCode.getCostCentre());
		discountableCB.setChecked(transactionCode.getDiscountable().equals(NonConfigurableConstants.BOOLEAN_YES));
		if (discountableCB.isChecked()) {
			discountGlCodeTB.setRawValue(transactionCode.getDiscountGlCode());
			discountCostCentreTB.setRawValue(transactionCode.getDiscountCostCentre());
			discountRow.setVisible(true);
		}
		isManualCB.setChecked(transactionCode.getIsManual().equals(NonConfigurableConstants.BOOLEAN_YES));
		effectiveDateDB.setRawValue(transactionCode.getEffectiveDate());

		// populate Entity list
		List<FmtbEntityMaster> entities = businessHelper.getAdminBusiness().getActiveEntities();
		for (FmtbEntityMaster entity : entities) {
			Comboitem item = newComboitem(entity, entity.getEntityName());
			entityCB.appendChild(item);
			if (transactionCode.getFmtbEntityMaster().getEntityNo().equals(entity.getEntityNo())) {
				entityCB.setSelectedItem(item);
			}
		}

		// populate Product Type list
		List<PmtbProductType> productTypes = businessHelper.getAdminBusiness().getProductTypes();
		Comboitem selectedItem = newComboitem(null, "-");
		productTypeCB.appendChild(selectedItem);
		for (PmtbProductType productType : productTypes) {
			Comboitem item = newComboitem(productType, productType.getName());
			productTypeCB.appendChild(item);
			if (transactionCode.getPmtbProductType() != null
					&& transactionCode.getPmtbProductType().getProductTypeId().equals(productType.getProductTypeId())) {
				selectedItem = item;
			}
		}
		productTypeCB.setSelectedItem(selectedItem);

		// populate Txn Type list
		Map<String, String> txnTypes = NonConfigurableConstants.TRANSACTION_TYPE;
		for (Entry<String, String> entry : txnTypes.entrySet()) {
			Comboitem item = newComboitem(entry.getKey(), entry.getValue());
			txnTypeCB.appendChild(item);
			if (transactionCode.getTxnType().equals(entry.getKey())) {
				txnTypeCB.setSelectedItem(item);
			}
		}

		// populate Tax Type list
		Map<String, String> masters = ConfigurableConstants.getMasters(ConfigurableConstants.TAX_TYPE);
		for (Entry<String, String> entry : masters.entrySet()) {
			Comboitem item = newComboitem(entry.getKey(), entry.getValue());
			taxTypeCB.appendChild(item);
			if (transactionCode.getMstbMasterTable() != null
					&& transactionCode.getMstbMasterTable().getMasterCode().equals(entry.getKey())) {
				taxTypeCB.setSelectedItem(item);
			}
		}

		entityCB.focus();
		
		if(transactionCode.getCreatedDt()!=null)createdByLabel.setValue(transactionCode.getCreatedBy());
		else createdByLabel.setValue("-");
		if(transactionCode.getCreatedDt()!=null)createdDateLabel.setValue(DateUtil.convertDateToStr(transactionCode.getCreatedDt(), DateUtil.GLOBAL_DATE_FORMAT));
		else createdDateLabel.setValue("-");
		if(transactionCode.getCreatedDt()!=null)createdTimeLabel.setValue(DateUtil.convertDateToStr(transactionCode.getCreatedDt(), DateUtil.GLOBAL_TIME_FORMAT));
		else createdTimeLabel.setValue("-");
		if(transactionCode.getUpdatedBy()!=null) lastUpdatedByLabel.setValue(transactionCode.getUpdatedBy());
		else lastUpdatedByLabel.setValue("-");
		if(transactionCode.getUpdatedDt()!=null) lastUpdatedDateLabel.setValue(DateUtil.convertDateToStr(transactionCode.getUpdatedDt(), DateUtil.GLOBAL_DATE_FORMAT));
		else lastUpdatedDateLabel.setValue("-");
		if(transactionCode.getUpdatedDt()!=null) lastUpdatedTimeLabel.setValue(DateUtil.convertDateToStr(transactionCode.getUpdatedDt(), DateUtil.GLOBAL_TIME_FORMAT));
		else lastUpdatedTimeLabel.setValue("-");
	}

	public void update() throws InterruptedException {
		entityCB.getValue();
		//		productTypeCB.getValue();
		txnTypeCB.getValue();
		taxTypeCB.getValue();

		transactionCode.setFmtbEntityMaster((FmtbEntityMaster) entityCB.getSelectedItem().getValue());
		transactionCode.setPmtbProductType((PmtbProductType) productTypeCB.getSelectedItem().getValue());
		transactionCode.setTxnType((String) txnTypeCB.getSelectedItem().getValue());
		transactionCode.setTxnCode(txnCodeTB.getValue());
		transactionCode.setDescription(descriptionTB.getValue());
		transactionCode.setGlCode(glCodeTB.getValue()+"");
		transactionCode.setCostCentre(costCentreTB.getValue());
		String discountable = discountableCB.isChecked() ? NonConfigurableConstants.BOOLEAN_YES : NonConfigurableConstants.BOOLEAN_NO;
		transactionCode.setDiscountable(discountable);
		if (discountableCB.isChecked()) {
			transactionCode.setDiscountGlCode(discountGlCodeTB.getValue()+"");
			transactionCode.setDiscountCostCentre(discountCostCentreTB.getValue());
		}
		else{
			transactionCode.setDiscountGlCode(null);
			transactionCode.setDiscountCostCentre(null);
		}
		transactionCode.setMstbMasterTable(
				ConfigurableConstants.getMasterTable(ConfigurableConstants.TAX_TYPE,
						(String) taxTypeCB.getSelectedItem().getValue()));
		String isManual = isManualCB.isChecked() ? NonConfigurableConstants.BOOLEAN_YES : NonConfigurableConstants.BOOLEAN_NO;
		transactionCode.setIsManual(isManual);
		transactionCode.setEffectiveDate(DateUtil.convertUtilDateToSqlDate(effectiveDateDB.getValue()));

		if(transactionCode.getPmtbProductType()!=null &&
				!(transactionCode.getTxnType().equals(NonConfigurableConstants.TRANSACTION_TYPE_ADMIN_FEE) ||
				transactionCode.getTxnType().equals(NonConfigurableConstants.TRANSACTION_TYPE_FARE))){
			
			Messagebox.show("Transaction code for product type is only applicable for transaction type of FARE or ADMIN FEE. " +
					"Do you still want to proceed to update this transaction code?", "Edit Transaction Code",
					Messagebox.OK, Messagebox.ERROR);
			return;
		}
		
		try {
			businessHelper.getAdminBusiness().updateTransactionCode(transactionCode, getUserLoginIdAndDomain());
			Messagebox.show(
					"Transaction Code has been successfully saved.", "Save Transaction Code",
					Messagebox.OK, Messagebox.INFORMATION);
			back();
		} 
		catch(WrongValueException wve){
			throw wve;
		}
		catch (DuplicateEffectiveDateError e) {
			Messagebox.show(e.getMessage(), "Error", Messagebox.OK, Messagebox.ERROR);
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

	public void onDiscountable() {
		discountRow.setVisible(discountableCB.isChecked());
		discountGlCodeTB.setDisabled(!discountableCB.isChecked());
		discountCostCentreTB.setDisabled(!discountableCB.isChecked());
	}
}
