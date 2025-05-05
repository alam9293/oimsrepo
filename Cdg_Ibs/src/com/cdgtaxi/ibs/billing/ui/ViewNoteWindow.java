package com.cdgtaxi.ibs.billing.ui;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.httpclient.HttpException;
import org.apache.log4j.Logger;
import org.zkoss.util.media.AMedia;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Button;
import org.zkoss.zul.Filedownload;
import org.zkoss.zul.Label;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Row;
import org.zkoss.zul.Window;

import com.cdgtaxi.ibs.Uri;
import com.cdgtaxi.ibs.common.NonConfigurableConstants;
import com.cdgtaxi.ibs.common.model.AmtbAccount;
import com.cdgtaxi.ibs.common.model.BmtbInvoiceHeader;
import com.cdgtaxi.ibs.common.model.BmtbNote;
import com.cdgtaxi.ibs.common.ui.CommonWindow;
import com.cdgtaxi.ibs.report.Constants;
import com.cdgtaxi.ibs.util.DateUtil;
import com.cdgtaxi.ibs.util.StringUtil;
import com.elixirtech.net.NetException;


public class ViewNoteWindow extends CommonWindow {
	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(ViewNoteWindow.class);
	private final BmtbNote note;
	private Long noteNo;

	@Override
	public void refresh() throws InterruptedException {
		//
	}

	public ViewNoteWindow() {
		Map params = Executions.getCurrent().getArg();

		// retrieve note details
		noteNo =  Long.parseLong((String) params.get("noteNo"));
		note = businessHelper.getNoteBusiness().getNote(noteNo);
	}
	public void printReceipt() throws InterruptedException{
		try{
			AMedia media = this.generate(noteNo.toString());
			Filedownload.save(media);
		}catch(NetException ne){
			Messagebox.show("Unable to generate receipt in pdf. Please inform the administrator", "Credit Debit Note", Messagebox.OK, Messagebox.ERROR);
		}catch(IOException ioe){
			Messagebox.show("Unable to generate receipt in pdf. Please inform the administrator", "Credit Debit Note", Messagebox.OK, Messagebox.ERROR);
		}
	}
	private AMedia generate(String noteNo) throws HttpException, IOException, InterruptedException, NetException {
		Properties params = new Properties();
		params.put("noteNo", noteNo);
		
		String outputFormat = Constants.FORMAT_PDF;
		String reportName = NonConfigurableConstants.REPORT_NAME_NOTE_CREDIT_DEBIT_RECEIPT;
		String reportCategory = NonConfigurableConstants.REPORT_CATEGORY_NOTE;
		byte[] bytes =
			businessHelper.getReportBusiness().generate(reportName, reportCategory, outputFormat, params);

		return new AMedia(reportName.replaceAll(" ", "_")+ ".pdf", "pdf", outputFormat, bytes);
	}
	public void onCreate() {
		Label accountNoLabel = (Label) getFellow("accountNoLabel");
		Label accountNameLabel = (Label) getFellow("nameLabel");
		Label invoiceNoLabel = (Label) getFellow("invoiceLabel");
		Label invoiceDateLabel = (Label) getFellow("invoiceDateLabel");
		Label invoiceAmtLabel = (Label) getFellow("invoiceAmountLabel");
		Label outstandingAmtLabel = (Label) getFellow("outstandingAmountLabel");
		Label invoiceStatusLabel = (Label) getFellow("invoiceStatusLabel");
		Label billedToLabel = (Label) getFellow("billedToLabel");

		BmtbInvoiceHeader invoiceHeader = note.getBmtbInvoiceHeader();
		AmtbAccount debtToAccount = invoiceHeader.getAmtbAccountByDebtTo();
		AmtbAccount topLevelAccount =
			businessHelper.getAccountBusiness().getTopLevelAccount(debtToAccount);

		accountNoLabel.setValue(topLevelAccount.getCustNo());
		accountNameLabel.setValue(topLevelAccount.getAccountName());
		invoiceNoLabel.setValue(invoiceHeader.getInvoiceNo().toString());
		invoiceDateLabel.setValue(DateUtil.convertDateToStr(invoiceHeader.getInvoiceDate(), DateUtil.GLOBAL_DATE_FORMAT));
		invoiceAmtLabel.setValue(StringUtil.bigDecimalToString(invoiceHeader.getNewTxn(), StringUtil.GLOBAL_DECIMAL_FORMAT));
		invoiceStatusLabel.setValue(NonConfigurableConstants.INVOICE_STATUS.get(invoiceHeader.getInvoiceStatus()));
		outstandingAmtLabel.setValue(StringUtil.bigDecimalToString(invoiceHeader.getOutstandingAmount(), StringUtil.GLOBAL_DECIMAL_FORMAT));
		billedToLabel.setValue(debtToAccount.getAccountName());

		Label noteNoLabel = (Label) getFellow("noteNoLabel");
		Label statusLabel = (Label) getFellow("statusLabel");
		Label accountLabel = (Label) getFellow("accountLabel");
		Label noteTypeLabel = (Label) getFellow("noteTypeLabel");
		Label noteAmountLabel = (Label) getFellow("noteAmountLabel");
		Label discountLabel = (Label) getFellow("discountLabel");
		Label gstAmountLabel = (Label) getFellow("gstAmountLabel");
		Label glTxnLabel = (Label) getFellow("glTxnLabel");
		Label noteReasonLabel = (Label) getFellow("noteReasonLabel");
		Label appliedInvoiceLabel = (Label) getFellow("appliedInvoiceLabel");
		Label remarksLabel = (Label) getFellow("remarksLabel");
		Label adminFeeLabel = (Label) getFellow("adminFeeLabel");
		Label updatedByLabel = (Label) getFellow("lastUpdatedBy");
		Label updatedDateLabel = (Label) getFellow("lastUpdatedDate");
		Label promoDiscountLabel = (Label) getFellow("promoDiscountLabel");

		noteNoLabel.setValue(note.getNoteNo().toString());
		statusLabel.setValue(NonConfigurableConstants.NOTE_STATUS.get(note.getStatus()));
		accountLabel.setValue(note.getAmtbAccount().getAccountName());
		noteTypeLabel.setValue(NonConfigurableConstants.NOTE_TYPE.get(note.getNoteType()));
		noteAmountLabel.setValue(StringUtil.bigDecimalToString(note.getNoteAmount().subtract(note.getPromoDis()), StringUtil.GLOBAL_DECIMAL_FORMAT));

		gstAmountLabel.setValue(StringUtil.bigDecimalToString(note.getGst(), StringUtil.GLOBAL_DECIMAL_FORMAT));
		if (NonConfigurableConstants.NOTE_TXN_TYPE_TRIP_BASED.equals(note.getNoteTxnType())) {
			adminFeeLabel.setValue(StringUtil.bigDecimalToString(note.getAdminFee(), StringUtil.GLOBAL_DECIMAL_FORMAT));
			discountLabel.setValue(StringUtil.bigDecimalToString(note.getProdDis(), StringUtil.GLOBAL_DECIMAL_FORMAT));
		}
		else
			discountLabel.setValue(StringUtil.bigDecimalToString(note.getDiscount(), StringUtil.GLOBAL_DECIMAL_FORMAT));
		
		promoDiscountLabel.setValue(StringUtil.bigDecimalToString(note.getPromoDis(), StringUtil.GLOBAL_DECIMAL_FORMAT));
		
		if(note.getFmtbTransactionCode()!=null){
			glTxnLabel.setValue(note.getFmtbTransactionCode().getDescription());
		} else {
			glTxnLabel.setValue("-");
		}
		
		
		if (note.getMstbMasterTableByReason() != null) {
			noteReasonLabel.setValue(note.getMstbMasterTableByReason().getMasterValue());
		} else {
			noteReasonLabel.setValue("-");
		}
		String appliedInvoiceNo = "-";
		if (note.getBmtbInvoiceTxnByBilledInvoiceTxnNo() != null) {
			appliedInvoiceNo = note.getBmtbInvoiceTxnByBilledInvoiceTxnNo().getBmtbInvoiceDetail().getBmtbInvoiceSummary().getBmtbInvoiceHeader().getInvoiceNo().toString();
		}
		appliedInvoiceLabel.setValue(appliedInvoiceNo);
		remarksLabel.setValue(note.getRemarks());

		if (note.getStatus().equals(NonConfigurableConstants.NOTE_STATUS_ACTIVE)) {
			Button cancelNoteButton = (Button) getFellow("cancelNoteButton");
			cancelNoteButton.setDisabled(false);
		}
		
		if (note.getStatus().equals(NonConfigurableConstants.NOTE_STATUS_ACTIVE) || 
				note.getStatus().equals(NonConfigurableConstants.NOTE_STATUS_BILLED)) {
			Button printReceiptButton = (Button) getFellow("printReceipt");
			printReceiptButton.setDisabled(false);
		}

		if (note.getStatus().equals(NonConfigurableConstants.NOTE_STATUS_CANCELLED)
				|| note.getStatus().equals(
						NonConfigurableConstants.NOTE_STATUS_PENDING_CANCELLATION)) {
			Label cancellationDateTimeLabel = (Label) getFellow("cancellationDateTimeLabel");
			Label cancellationReasonLabel = (Label) getFellow("cancellationReasonLabel");

			cancellationDateTimeLabel.setValue(
					DateUtil.convertTimestampToStr(
							note.getCancelDt(), DateUtil.LAST_UPDATED_DATE_FORMAT));
			cancellationReasonLabel.setValue(
					note.getMstbMasterTableByCancellationReason().getMasterValue());

			Row cancellationRow = (Row) getFellow("cancellationRow");
			cancellationRow.setVisible(true);
		}

		// Set updated by/update date/time
		updatedByLabel.setValue(note.getUpdatedBy());
		if (note.getUpdatedDt() != null)
		{
			updatedDateLabel.setValue(DateUtil.convertTimestampToStr(note.getUpdatedDt(), DateUtil.LAST_UPDATED_DATE_FORMAT));
		}
	}

	public void cancelNote() throws InterruptedException {
		Map<String, Object> args = new HashMap<String, Object>();
		args.put("note", note);
		args.put("owner", this);

		//		try{
		final Window win = (Window) Executions.createComponents(Uri.CANCEL_NOTE, null, args);
		win.setMaximizable(false);
		win.doModal();
		//		} catch(Exception e){
		//			Messagebox.show(NonConfigurableConstants.COMMON_ERROR_MESSAGE,
		//					"Error", Messagebox.OK, Messagebox.ERROR);
		//			e.printStackTrace();
		//		}
	}
}
