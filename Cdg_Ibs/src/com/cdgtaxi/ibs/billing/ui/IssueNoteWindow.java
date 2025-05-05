package com.cdgtaxi.ibs.billing.ui;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Comboitem;
import org.zkoss.zul.Decimalbox;
import org.zkoss.zul.Label;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;

import com.cdgtaxi.ibs.common.ConfigurableConstants;
import com.cdgtaxi.ibs.common.NonConfigurableConstants;
import com.cdgtaxi.ibs.common.exception.ExcessiveNoteAmountException;
import com.cdgtaxi.ibs.common.model.AmtbAccount;
import com.cdgtaxi.ibs.common.model.BmtbInvoiceHeader;
import com.cdgtaxi.ibs.common.model.BmtbNote;
import com.cdgtaxi.ibs.common.model.FmtbTaxCode;
import com.cdgtaxi.ibs.common.model.FmtbTransactionCode;
import com.cdgtaxi.ibs.util.ComponentUtil;
import com.cdgtaxi.ibs.util.DateUtil;
import com.cdgtaxi.ibs.util.StringUtil;
import com.cdgtaxi.ibs.web.constraint.RequiredConstraint;

public class IssueNoteWindow extends ViewInvoiceWindow {
	private static BigDecimal ZERO = new BigDecimal("0.00");
	private static BigDecimal DIVISOR = new BigDecimal("100");
	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(IssueNoteWindow.class);
	private static final String DISCOUNT_GREATER_THAN_NOTE_AMOUNT = "* Discount must be less than or equal to the Note Amount";
	
	private int entityNo;

	private final BmtbInvoiceHeader invoiceHeader;

	@SuppressWarnings("unchecked")
	public IssueNoteWindow() {
		Map params = Executions.getCurrent().getArg();
		String invoiceNo =  (String)params.get("invoiceNo");
		invoiceHeader = businessHelper.getInvoiceBusiness().getInvoice(invoiceNo);
	}

	@Override
	public void onCreate() {
		Label accountLabel;
		Decimalbox noteAmountDB;
		Decimalbox discountDB;
		Decimalbox gstAmountDB;
		Combobox noteTypeCB;
		Combobox txnTypeCB;
		Combobox noteReasonCB;
		Textbox remarksTB;
		
		Label accountNoLabel = (Label) getFellow("accountNoLabel");
		Label accountNameLabel = (Label) getFellow("nameLabel");
		Label invoiceNoLabel = (Label) getFellow("invoiceLabel");
		Label invoiceDateLabel = (Label) getFellow("invoiceDateLabel");
		Label invoiceAmtLabel = (Label) getFellow("invoiceAmountLabel");
		Label outstandingAmtLabel = (Label) getFellow("outstandingAmountLabel");
		Label invoiceStatusLabel = (Label) getFellow("invoiceStatusLabel");
		Label billedToLabel = (Label) getFellow("billedToLabel");

		AmtbAccount debtToAccount = invoiceHeader.getAmtbAccountByDebtTo();
		AmtbAccount topLevelAccount =
			businessHelper.getAccountBusiness().getTopLevelAccount(debtToAccount);

		// set entity no
		AmtbAccount topLevelAccountWithEntity = this.businessHelper.getInvoiceBusiness().getAccountWithEntity(topLevelAccount);
		entityNo = topLevelAccountWithEntity.getFmtbArContCodeMaster().getFmtbEntityMaster().getEntityNo();
		
		accountNoLabel.setValue(topLevelAccount.getCustNo());
		accountNameLabel.setValue(topLevelAccount.getAccountName());
		invoiceNoLabel.setValue(invoiceHeader.getInvoiceNo().toString());
		invoiceDateLabel.setValue(DateUtil.convertDateToStr(invoiceHeader.getInvoiceDate(), DateUtil.GLOBAL_DATE_FORMAT));
		invoiceAmtLabel.setValue(StringUtil.bigDecimalToString(invoiceHeader.getNewTxn(), StringUtil.GLOBAL_DECIMAL_FORMAT));
		invoiceStatusLabel.setValue(NonConfigurableConstants.INVOICE_STATUS.get(invoiceHeader.getInvoiceStatus()));
		outstandingAmtLabel.setValue(StringUtil.bigDecimalToString(invoiceHeader.getOutstandingAmount(), StringUtil.GLOBAL_DECIMAL_FORMAT));
		billedToLabel.setValue(debtToAccount.getAccountName());

		accountLabel = (Label) getFellow("accountLabel");
		accountLabel.setValue(debtToAccount.getAccountName());

		noteAmountDB = (Decimalbox) getFellow("noteAmountDB");

		discountDB = (Decimalbox) getFellow("discountDB");
		discountDB.setValue(ZERO);

		gstAmountDB = (Decimalbox) getFellow("gstAmountDB");
		gstAmountDB.setValue(ZERO);

		noteTypeCB = (Combobox) getFellow("noteTypeCB");
		List<Comboitem> items = ComponentUtil.convertToComboitems(NonConfigurableConstants.NOTE_TYPE, true);
		for (Comboitem item : items) {
			noteTypeCB.appendChild(item);
		}

		txnTypeCB = (Combobox) getFellow("txnTypeCB");
		// TODO: have to pass in userid to filter by entity
		List<FmtbTransactionCode> codes =
			businessHelper.getFinanceBusiness().getEffectiveManualTxnCodes(entityNo);
		for (FmtbTransactionCode code : codes) {
			Comboitem item = new Comboitem(code.getDescription());
			logger.info("Code : " + code.getTxnCode());
			item.setValue(code);
			txnTypeCB.appendChild(item);
		}

		noteReasonCB = (Combobox) getFellow("noteReasonCB");
		items = ComponentUtil.convertToComboitems(ConfigurableConstants.getNoteReasons(), true);
		for (Comboitem item : items) {
			noteReasonCB.appendChild(item);
		}

		remarksTB = (Textbox) getFellow("remarksTB");
	}

	public void onGlTxnChange() {
		//txnTypeCB.getValue(); // just for firing the validation
		Combobox txnTypeCB = (Combobox) getFellow("txnTypeCB");
		Decimalbox discountDB = (Decimalbox) getFellow("discountDB");
		Decimalbox gstAmountDB = (Decimalbox) getFellow("gstAmountDB");
		
		//trigger event for mandatory field
		txnTypeCB.getText();
		
		FmtbTransactionCode value = (FmtbTransactionCode) txnTypeCB.getSelectedItem().getValue();
		if (value != null) {
			FmtbTransactionCode txnCode = (FmtbTransactionCode) value;

			// Disable Discount field if Txn Code is not discountable
			discountDB.setDisabled(NonConfigurableConstants.BOOLEAN_NO.equals(txnCode.getDiscountable()));

			// Disable GST field if Txn Code is not taxable
			Integer taxType = txnCode.getMstbMasterTable().getMasterNo();
			FmtbTaxCode taxCode = businessHelper.getFinanceBusiness().getEffectiveTaxCode(entityNo, taxType);
			gstAmountDB.setDisabled(taxCode.getTaxRate().signum() == 0);
			checkValues();
		} else {
			discountDB.setDisabled(false);
			gstAmountDB.setDisabled(false);
		}

	}

	public void checkValues() {
		
		Decimalbox discountDB = (Decimalbox) getFellow("discountDB");
		
		BigDecimal gstAmount = ZERO;
		BigDecimal discount = discountDB.getValue();
		
		Combobox txnTypeCB = (Combobox) getFellow("txnTypeCB");
		Decimalbox gstAmountDB = (Decimalbox) getFellow("gstAmountDB");
		Decimalbox noteAmountDB = (Decimalbox) getFellow("noteAmountDB");
		Combobox noteTypeCB = (Combobox) getFellow("noteTypeCB");

		// To trigger event for fmtbTransactionCode and note Type

		txnTypeCB.getText();
		FmtbTransactionCode value = (FmtbTransactionCode) txnTypeCB.getSelectedItem().getValue();
		if (value != null) {
			FmtbTransactionCode txnCode = (FmtbTransactionCode) value;

			if (noteAmountDB.isValid()) {
				BigDecimal noteAmount = noteAmountDB.getValue();
				if (noteAmountDB.isValid() && discount.compareTo(noteAmount) > 0) {
					throw new WrongValueException(discountDB, DISCOUNT_GREATER_THAN_NOTE_AMOUNT);
				} else {
					discountDB.clearErrorMessage();
				}
	
				if (noteAmountDB.isValid() && !gstAmountDB.isDisabled()) {
					BigDecimal gst = BigDecimal.ZERO;
					noteTypeCB.getText();
					String noteType = (String) noteTypeCB.getSelectedItem().getValue();
					if (NonConfigurableConstants.NOTE_TYPE_CREDIT.equalsIgnoreCase(noteType))
					{
						gst = this.businessHelper.getFinanceBusiness().getLatestGST(entityNo, DateUtil.convertDateToTimestamp(invoiceHeader.getInvoiceDate()), txnCode.getTxnCode());
					}
					else
					{
						gst = this.businessHelper.getFinanceBusiness().getLatestGST(entityNo, DateUtil.getCurrentTimestamp(), txnCode.getTxnCode());
					}
					gst = gst.divide(new BigDecimal(100), 4, BigDecimal.ROUND_HALF_UP);
					gstAmount = noteAmountDB.getValue().subtract(discount).multiply(gst);
				}
			}
			gstAmountDB.setValue(gstAmount);
		}
	}

	public void issueNote() throws InterruptedException{
		// just for firing the validation for combo boxes
		Combobox txnTypeCB = (Combobox) getFellow("txnTypeCB");
		Combobox noteTypeCB = (Combobox) getFellow("noteTypeCB");
		Combobox noteReasonCB = (Combobox) getFellow("noteReasonCB");
		Textbox remarksTB = (Textbox) getFellow("remarksTB");
		Decimalbox gstAmountDB = (Decimalbox) getFellow("gstAmountDB");
		Decimalbox noteAmountDB = (Decimalbox) getFellow("noteAmountDB");

		Decimalbox discountDB = (Decimalbox) getFellow("discountDB");

		RequiredConstraint.validate(txnTypeCB);
		FmtbTransactionCode txnCode = (FmtbTransactionCode) txnTypeCB.getSelectedItem().getValue();
		RequiredConstraint.validate(noteReasonCB);
		String noteReason = (String) noteReasonCB.getSelectedItem().getValue();
		RequiredConstraint.validate(noteTypeCB);
		String noteType = (String) noteTypeCB.getSelectedItem().getValue();

		BmtbNote note = new BmtbNote();
		note.setAmtbAccount(invoiceHeader.getAmtbAccountByDebtTo());
		note.setRemarks(remarksTB.getValue());
		note.setGst(gstAmountDB.getValue());
		note.setDiscount(discountDB.getValue());
		note.setBmtbInvoiceHeader(invoiceHeader);
		note.setNoteAmount(noteAmountDB.getValue());
		note.setNoteType(noteType);
		note.setFmtbTransactionCode(txnCode);
		note.setMstbMasterTableByReason(ConfigurableConstants.getMasterTable(ConfigurableConstants.NOTE_REASON, noteReason));
		note.setCreatedBy(getUserLoginIdAndDomain());

		try {
			Long noteNo = businessHelper.getNoteBusiness().issueNote(note);
			Messagebox.show("Note has been successfully submitted for approval. The Note No. is "
					+ noteNo + ".", "Issue Note", Messagebox.OK, Messagebox.INFORMATION);
			this.back();
		} catch (ExcessiveNoteAmountException e) {
			Messagebox.show(e.getMessage(), "Note Issuance Error", Messagebox.OK, Messagebox.ERROR);
		} catch (Exception ex) {
			ex.printStackTrace();
			Messagebox.show(com.cdgtaxi.ibs.common.NonConfigurableConstants.COMMON_ERROR_MESSAGE,
					"Error", Messagebox.OK, Messagebox.ERROR);
		}
	}
}
