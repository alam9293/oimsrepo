package com.cdgtaxi.ibs.billing.ui;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.zkoss.util.media.AMedia;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zul.Button;
import org.zkoss.zul.Filedownload;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Label;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Messagebox;

import com.cdgtaxi.ibs.common.NonConfigurableConstants;
import com.cdgtaxi.ibs.common.model.AmtbAccount;
import com.cdgtaxi.ibs.common.model.BmtbInvoiceHeader;
import com.cdgtaxi.ibs.common.model.BmtbNote;
import com.cdgtaxi.ibs.common.model.BmtbPaymentReceipt;
import com.cdgtaxi.ibs.common.model.BmtbPaymentReceiptDetail;
import com.cdgtaxi.ibs.common.model.PmtbPrepaidReq;
import com.cdgtaxi.ibs.common.ui.CommonWindow;
import com.cdgtaxi.ibs.exporter.PdfExportItem;
import com.cdgtaxi.ibs.exporter.ZkPdfExporter;
import com.cdgtaxi.ibs.master.model.MstbMasterTable;
import com.cdgtaxi.ibs.util.ComponentUtil;
import com.cdgtaxi.ibs.util.DateUtil;
import com.cdgtaxi.ibs.util.StringUtil;
import com.lowagie.text.PageSize;

public class ViewInvoiceWindow extends CommonWindow {
	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(ViewInvoiceWindow.class);

	private final BmtbInvoiceHeader invoiceHeader;
	private final Long invoiceNo;
	private final List<BmtbPaymentReceipt> receipts;
	private final List<BmtbNote> notes;

	@SuppressWarnings("unchecked")
	public ViewInvoiceWindow() {
		// retrieve account details
		Map params = Executions.getCurrent().getArg();
		invoiceNo = Long.parseLong((String)params.get("invoiceNo"));

		logger.debug("View Invoice " + invoiceNo);

		// retrieve invoice header
		invoiceHeader = businessHelper.getInvoiceBusiness().getInvoice(invoiceNo.toString());

		// retrieve receipts
		receipts = businessHelper.getPaymentBusiness().searchPaymentReceiptsByInvoice(invoiceNo);
		logger.debug("receipts count = " + receipts.size());

		// retrieve debit / credit note details
		notes = businessHelper.getNoteBusiness().getNotes(invoiceHeader.getInvoiceHeaderNo());
		logger.debug("notes count = " + notes.size());
	}

	public void onCreate() {
		Label accountNoLabel = (Label) getFellow("accountNoLabel");
		Label accountNameLabel = (Label) getFellow("nameLabel");
		Label invoiceNoLabel = (Label) getFellow("invoiceLabel");
		Label invoiceDateLabel = (Label) getFellow("invoiceDateLabel");
		Label invoiceAmtLabel = (Label) getFellow("invoiceAmountLabel");
		Label appliedAmtLabel = (Label) getFellow("invoiceAppliedAmountLabel");
		Label debitCreditAmtLabel = (Label) getFellow("debitCreditAmountLabel");
		Label outstandingAmtLabel = (Label) getFellow("outstandingAmountLabel");
		Label invoiceStatusLabel = (Label) getFellow("invoiceStatusLabel");
		Label billedToLabel = (Label) getFellow("billedToLabel");
		Label remarksLabel = (Label) getFellow("remarksLabel");

		Button cancelButton = (Button) getFellow("cancelButton");
		
		AmtbAccount debtToAccount = invoiceHeader.getAmtbAccountByDebtTo();
		AmtbAccount topLevelAccount =
			businessHelper.getAccountBusiness().getTopLevelAccount(debtToAccount);

		accountNoLabel.setValue(topLevelAccount.getCustNo());
		accountNameLabel.setValue(topLevelAccount.getAccountName());
		invoiceNoLabel.setValue(invoiceHeader.getInvoiceNo().toString());
		invoiceDateLabel.setValue(DateUtil.convertDateToStr(invoiceHeader.getInvoiceDate(), DateUtil.GLOBAL_DATE_FORMAT));
		invoiceAmtLabel.setValue(StringUtil.bigDecimalToString(invoiceHeader.getNewTxn(), StringUtil.GLOBAL_DECIMAL_FORMAT));
		appliedAmtLabel.setValue(getInvoiceAppliedAmount());
		debitCreditAmtLabel.setValue(getDebitCreditAmount());
		invoiceStatusLabel.setValue(NonConfigurableConstants.INVOICE_STATUS.get(invoiceHeader.getInvoiceStatus()));
		outstandingAmtLabel.setValue(StringUtil.bigDecimalToString(invoiceHeader.getOutstandingAmount(), StringUtil.GLOBAL_DECIMAL_FORMAT));
		billedToLabel.setValue(debtToAccount.getAccountName());
		remarksLabel.setValue(invoiceHeader.getRemarks());
		
		boolean showCancelButton = NonConfigurableConstants.getBoolean(invoiceHeader.getPrepaidFlag()) 
				&& invoiceHeader.getCancelDt()==null
				&& !NonConfigurableConstants.INVOICE_STATUS_CLOSED.equals(invoiceHeader.getInvoiceStatus())
				;
					
		cancelButton.setVisible(showCancelButton);
		
	}

	public void fillReceiptDetails() {
		Listbox receiptsList = (Listbox)this.getFellow("receiptsList");
		receiptsList.getItems().clear();
		if (receiptsList.getListfoot() != null) {
			receiptsList.removeChild(receiptsList.getListfoot());
		}

		if (receipts.isEmpty()) {
			receiptsList.appendChild(ComponentUtil.createNoRecordsFoundListFoot(10));
			return;
		}

		for (BmtbPaymentReceipt receipt : receipts) {
			Listitem item = new Listitem();
			item.setValue(receipt);
			item.appendChild(newListcell(receipt.getPaymentReceiptNo(), StringUtil.GLOBAL_STRING_FORMAT));
			item.appendChild(newListcell(receipt.getReceiptDt(), DateUtil.GLOBAL_DATE_FORMAT));
			item.appendChild(newListcell(receipt.getPaymentDate(), DateUtil.GLOBAL_DATE_FORMAT));
			item.appendChild(newListcell(receipt.getMstbMasterTableByPaymentMode().getMasterValue()));

			String chequeNo = (receipt.getChequeNo() != null) ? receipt.getChequeNo() : "-";
			item.appendChild(newListcell(chequeNo));

			java.sql.Date date = receipt.getChequeDate();
			Listcell chequeDateCell = new Listcell();
			chequeDateCell.setLabel((date != null) ? DateUtil.convertDateToStr(date, DateUtil.GLOBAL_DATE_FORMAT) : "-");
			chequeDateCell.setValue((date != null) ? date : DateUtil.getSqlDateForNullComparison());
			item.appendChild(chequeDateCell);

			item.appendChild(newListcell(receipt.getPaymentAmount(), StringUtil.GLOBAL_DECIMAL_FORMAT));

			BigDecimal appliedAmount = new BigDecimal("0.00");
			for (BmtbPaymentReceiptDetail detail : receipt.getBmtbPaymentReceiptDetails()) {
				if (detail.getBmtbInvoiceHeader().getInvoiceNo().equals(invoiceNo)) {
					appliedAmount = appliedAmount.add(detail.getAppliedAmount());
				}
			}
			item.appendChild(newListcell(appliedAmount, StringUtil.GLOBAL_DECIMAL_FORMAT));
			//			item.appendChild(new Listcell(StringUtil.bigDecimalToString(receipt.getExcessAmount(), StringUtil.GLOBAL_DECIMAL_FORMAT)));

			MstbMasterTable masterTable = receipt.getMstbMasterTableByCancellationReason();
			String cancelReason = (masterTable != null) ? masterTable.getMasterValue() : "-";
			item.appendChild(newListcell(cancelReason));

			receiptsList.appendChild(item);
		}
	}

	public void fillNoteDetails() {
		Listbox notesList = (Listbox)this.getFellow("notesList");
		notesList.getItems().clear();
		if (notesList.getListfoot() != null) {
			notesList.removeChild(notesList.getListfoot());
		}

		if (notes.isEmpty()) {
			notesList.appendChild(ComponentUtil.createNoRecordsFoundListFoot(10));
			return;
		}

		for (BmtbNote note : notes) {
			Listitem item = new Listitem();
			item.setValue(note);
			item.appendChild(newListcell(note.getNoteNo(), StringUtil.GLOBAL_STRING_FORMAT));
			item.appendChild(newListcell(note.getCreatedDt(), DateUtil.LAST_UPDATED_DATE_FORMAT));
			item.appendChild(newListcell(NonConfigurableConstants.NOTE_TYPE.get(note.getNoteType())));
			Listcell billedInvoiceCell = new Listcell();
			billedInvoiceCell.setLabel("-");
			billedInvoiceCell.setValue(null);
			if (note.getBmtbInvoiceTxnByBilledInvoiceTxnNo() != null) {
				Long invoiceNo = note.getBmtbInvoiceTxnByBilledInvoiceTxnNo().getBmtbInvoiceDetail().getBmtbInvoiceSummary().getBmtbInvoiceHeader().getInvoiceNo();
				billedInvoiceCell.setLabel(invoiceNo.toString());
				billedInvoiceCell.setValue(invoiceNo);
			}
			
			item.appendChild(billedInvoiceCell);
			// To calculate the note amount
			// Net Note Amount = note amount - discount + GST + admin fee (for trip-based)
			BigDecimal noteAmount = new BigDecimal(0);
			if (NonConfigurableConstants.NOTE_TXN_TYPE_TRIP_BASED.equals(note.getNoteTxnType()))
			{
				// Trips based
				noteAmount = note.getNoteAmount().subtract(note.getPromoDis()).add(note.getGst()).add(note.getAdminFee()).subtract(note.getProdDis());
			}
			else
			{
				// Non-trips based
				noteAmount = note.getNoteAmount().add(note.getGst()).subtract(note.getProdDis());
			}
			if (NonConfigurableConstants.NOTE_STATUS_CANCELLED.equals(note.getStatus())) {
				item.appendChild(newListcell(new BigDecimal(0), StringUtil.GLOBAL_DECIMAL_FORMAT));
			} else {
				item.appendChild(newListcell(noteAmount, StringUtil.GLOBAL_DECIMAL_FORMAT));
			}
			/*item.appendChild(new Listcell(StringUtil.bigDecimalToString(note.getAdminFee(), StringUtil.GLOBAL_DECIMAL_FORMAT)));
			item.appendChild(new Listcell(StringUtil.bigDecimalToString(note.getDiscount(), StringUtil.GLOBAL_DECIMAL_FORMAT)));
			item.appendChild(new Listcell(StringUtil.bigDecimalToString(note.getProdDis(), StringUtil.GLOBAL_DECIMAL_FORMAT)));
			item.appendChild(new Listcell(StringUtil.bigDecimalToString(note.getGst(), StringUtil.GLOBAL_DECIMAL_FORMAT)));*/
			item.appendChild(newListcell(NonConfigurableConstants.NOTE_STATUS.get(note.getStatus())));

			String cancellationReason = (note.getMstbMasterTableByCancellationReason() != null)
			? note.getMstbMasterTableByCancellationReason().getMasterValue()
					: "-";

			item.appendChild(newListcell(cancellationReason));

			notesList.appendChild(item);
		}
	}

	private String getInvoiceAppliedAmount() {
		BigDecimal appliedAmount = new BigDecimal("0.00");
		for (BmtbPaymentReceipt receipt : receipts) {
			for (BmtbPaymentReceiptDetail detail : receipt.getBmtbPaymentReceiptDetails()) {
				if (detail.getBmtbInvoiceHeader().getInvoiceNo().equals(invoiceNo)) {
					appliedAmount = appliedAmount.add(detail.getAppliedAmount());
				}
			}
		}
		return StringUtil.bigDecimalToString(appliedAmount, StringUtil.GLOBAL_DECIMAL_FORMAT);
	}

	//	public String getExcessAmount() {
	//		BigDecimal excessAmount = new BigDecimal("0.00");
	//		for (BmtbPaymentReceipt receipt : receipts) {
	//			excessAmount = excessAmount.add(receipt.getExcessAmount());
	//		}
	//		return StringUtil.bigDecimalToString(excessAmount, StringUtil.GLOBAL_DECIMAL_FORMAT);
	//	}

	private String getDebitCreditAmount() {
		BigDecimal debitCreditAmount = new BigDecimal("0.00");
		for (BmtbNote note : notes) {
			// To calculate the note amount
			// Net Note Amount = note amount - discount + GST + admin fee (for trip-based)
			BigDecimal noteAmount = new BigDecimal(0);
			if (NonConfigurableConstants.NOTE_TXN_TYPE_TRIP_BASED.equals(note.getNoteTxnType()))
			{
				// Trips based
				noteAmount = note.getNoteAmount().add(note.getGst()).add(note.getAdminFee()).subtract(note.getProdDis());
			}
			else
			{
				// Non-trips based
				noteAmount = note.getNoteAmount().add(note.getGst()).subtract(note.getProdDis());
			}
			// Do not allow cancelled notes to be added
			if (!NonConfigurableConstants.NOTE_STATUS_CANCELLED.equals(note.getStatus()) && !NonConfigurableConstants.NOTE_STATUS_REJECTED.equals(note.getStatus()))
			{
				if (NonConfigurableConstants.NOTE_TYPE_CREDIT.equals(note.getNoteType()))
				{
					debitCreditAmount = debitCreditAmount.subtract(noteAmount);
				} else {
					debitCreditAmount = debitCreditAmount.add(noteAmount);
				}
			}
			logger.info("DebitCreditAmount " + debitCreditAmount);
		}
		return StringUtil.bigDecimalToString(debitCreditAmount, StringUtil.GLOBAL_DECIMAL_FORMAT);
	}

	@Override
	public void refresh() throws InterruptedException {
		//
	}

	public void cancel() throws InterruptedException{
		
		if(NonConfigurableConstants.getBoolean(invoiceHeader.getBilledFlag())){
			throw new WrongValueException("Cancel invoice not allow as it has been billed.");
		}
		
		//check whether invoice applicable to be cancelled
		PmtbPrepaidReq prepaidInvoiceHeader = businessHelper.getPrepaidBusiness().getPrepaidRequestWithInvoiceHeader(invoiceHeader.getInvoiceHeaderNo());
		String reqStatus = prepaidInvoiceHeader.getStatus();
		
		if(reqStatus.equals(NonConfigurableConstants.PREPAID_REQUEST_STATUS_COMPLETED_WO_PAYMENT) || reqStatus.equals(NonConfigurableConstants.PREPAID_REQUEST_STATUS_COMPLETED)){
			throw new WrongValueException("Cancel invoice not allow as prepaid product(s) have been created.");
		}
		
		//check is there any active payments tie to the invoice, if there exists not allow to cancel invoice
		if(receipts!=null && !receipts.isEmpty()){
			for(BmtbPaymentReceipt paymentReceipt: receipts){
				
				if(paymentReceipt.getCancelDt()==null){
					throw new WrongValueException("Please cancel payment receipt(s) before proceed to cancel the invoice.");
				}
			}
		}
		
		if (!ComponentUtil.confirmBox("Do you confirm to cancel the invoice?", "Cancel Invoice")) {
			return;
		}
		
		this.businessHelper.getPrepaidBusiness().cancelPrepaidInvoice(invoiceHeader, CommonWindow.getUserLoginIdAndDomain());
		
		Messagebox.show("Invoice was cancelled.", "View Invoice", Messagebox.OK, Messagebox.INFORMATION);
		
		back();
		
	}
	
	public void exportResult() throws InterruptedException, IOException {
		logger.info("exportResult");
		List<PdfExportItem> items = new ArrayList<PdfExportItem>();
		items.add(new PdfExportItem((Grid)this.getFellow("invoiceDetailsGrid"), "Invoice Details", null));
		items.add(new PdfExportItem((Listbox)this.getFellow("receiptsList"), "Applied Receipts", null));
		items.add(new PdfExportItem((Listbox)this.getFellow("notesList"), "Debit / Credit Notes", null));
		
	    ByteArrayOutputStream out = new ByteArrayOutputStream();
	     
		ZkPdfExporter exp = new ZkPdfExporter(PageSize.A4.rotate());
		try{
		exp.export(items, out);
	     
	    AMedia amedia = new AMedia("Invoice_Details.pdf", "pdf", "application/pdf", out.toByteArray());
	    Filedownload.save(amedia);   
		
		out.close();
		}catch (Exception e) {
			try {
				Messagebox.show(com.cdgtaxi.ibs.common.NonConfigurableConstants.COMMON_ERROR_MESSAGE,
						"Error", Messagebox.OK, Messagebox.ERROR);
			} catch (InterruptedException e1) { e.printStackTrace(); }
		}
	}
}
