package com.cdgtaxi.ibs.admin.ui;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.log4j.Logger;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Comboitem;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Intbox;
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
public class CreateTransactionCodeWindow extends CommonWindow {
	private static final Logger logger = Logger.getLogger(CreateTransactionCodeWindow.class);

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

	public void onCreate() {
		entityCB = (Combobox) getFellow("entityCB");
		productTypeCB = (Combobox) getFellow("productTypeCB");
		txnCodeTB = (Textbox) getFellow("txnCodeTB");
		descriptionTB = (Textbox) getFellow("descriptionTB");
		txnTypeCB = (Combobox) getFellow("txnTypeCB");
		glCodeTB = (Intbox) getFellow("glCodeTB");
		costCentreTB = (Textbox) getFellow("costCentreTB");
		discountableCB = (Checkbox) getFellow("discountableCB");
		discountRow = (Row) getFellow("discountRow");
		discountGlCodeTB = (Intbox) getFellow("discountGlCodeTB");
		discountCostCentreTB = (Textbox) getFellow("discountCostCentreTB");
		taxTypeCB = (Combobox) getFellow("taxTypeCB");
		isManualCB = (Checkbox) getFellow("isManualCB");
		effectiveDateDB = (Datebox) getFellow("effectiveDateDB");

		// populate Entity list
		List<FmtbEntityMaster> entities = businessHelper.getAdminBusiness().getActiveEntities();
		for (FmtbEntityMaster entity : entities) {
			Comboitem item = newComboitem(entity, entity.getEntityName());
			entityCB.appendChild(item);
		}

		// populate Product Type list
		List<PmtbProductType> productTypes = businessHelper.getAdminBusiness().getProductTypes();
		Comboitem selectedItem = newComboitem(null, "-");
		productTypeCB.appendChild(selectedItem);
		for (PmtbProductType productType : productTypes) {
			Comboitem item = newComboitem(productType, productType.getName());
			productTypeCB.appendChild(item);
		}
		productTypeCB.setSelectedItem(selectedItem);

		// populate Txn Type list
		Map<String, String> txnTypes = NonConfigurableConstants.TRANSACTION_TYPE;
		for (Entry<String, String> entry : txnTypes.entrySet()) {
			Comboitem item = newComboitem(entry.getKey(), entry.getValue());
			txnTypeCB.appendChild(item);
		}

		// populate Tax Type list
		Map<String, String> masters = ConfigurableConstants.getMasters(ConfigurableConstants.TAX_TYPE);
		for (Entry<String, String> entry : masters.entrySet()) {
			Comboitem item = newComboitem(entry.getKey(), entry.getValue());
			taxTypeCB.appendChild(item);
		}

		entityCB.focus();
	}

	public void create() throws InterruptedException {
		entityCB.getValue();
		//		productTypeCB.getValue();
		txnTypeCB.getValue();
		taxTypeCB.getValue();

		FmtbTransactionCode transactionCode = new FmtbTransactionCode();
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
		transactionCode.setMstbMasterTable(
				ConfigurableConstants.getMasterTable(ConfigurableConstants.TAX_TYPE,
						(String) taxTypeCB.getSelectedItem().getValue()));
		String isManual = isManualCB.isChecked() ? NonConfigurableConstants.BOOLEAN_YES : NonConfigurableConstants.BOOLEAN_NO;
		transactionCode.setIsManual(isManual);
		transactionCode.setEffectiveDate(DateUtil.convertUtilDateToSqlDate(effectiveDateDB.getValue()));

		if(transactionCode.getPmtbProductType()!=null &&
				!NonConfigurableConstants.getBoolean(transactionCode.getPmtbProductType().getPrepaid()) &&
				!(transactionCode.getTxnType().equals(NonConfigurableConstants.TRANSACTION_TYPE_ADMIN_FEE) ||
				transactionCode.getTxnType().equals(NonConfigurableConstants.TRANSACTION_TYPE_FARE))){
			
			Messagebox.show("Transaction code for product type is only applicable for transaction type of FARE or ADMIN FEE. " +
					"Do you still want to proceed to create this transaction code?", "Create Transaction Code",
					Messagebox.OK, Messagebox.ERROR);
			return;
		}
		
		try {
			businessHelper.getAdminBusiness().createTransactionCode(transactionCode, getUserLoginIdAndDomain());
			Messagebox.show("Transaction Code has been successfully created", "Create Transaction Code",
					Messagebox.OK, Messagebox.INFORMATION);
			Executions.getCurrent().sendRedirect("");
		} catch (DuplicateEffectiveDateError e) {
			Messagebox.show(e.getMessage(), "Error", Messagebox.OK, Messagebox.ERROR);
		} catch (Exception ex) {
			logger.error(ex.getMessage(), ex);
			Messagebox.show(NonConfigurableConstants.COMMON_ERROR_MESSAGE,
					"Error", Messagebox.OK, Messagebox.ERROR);
		}
	}

	public void onDiscountable() {
		discountRow.setVisible(discountableCB.isChecked());
		discountGlCodeTB.setDisabled(!discountableCB.isChecked());
		discountCostCentreTB.setDisabled(!discountableCB.isChecked());
	}

	@Override
	public void refresh() throws InterruptedException {
	}
}
