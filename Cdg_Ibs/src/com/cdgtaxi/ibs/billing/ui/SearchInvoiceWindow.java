package com.cdgtaxi.ibs.billing.ui;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.zkoss.util.media.AMedia;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.CreateEvent;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zkplus.spring.SpringUtil;
import org.zkoss.zul.Button;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Filedownload;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Longbox;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import com.cdgtaxi.ibs.Uri;
import com.cdgtaxi.ibs.common.ConfigurableConstants;
import com.cdgtaxi.ibs.common.NonConfigurableConstants;
import com.cdgtaxi.ibs.common.model.AmtbAccount;
import com.cdgtaxi.ibs.common.model.BmtbInvoiceHeader;
import com.cdgtaxi.ibs.common.model.BmtbNote;
import com.cdgtaxi.ibs.common.model.forms.SearchInvoiceForm;
import com.cdgtaxi.ibs.exporter.PdfExportItem;
import com.cdgtaxi.ibs.exporter.ZkPdfExporter;
import com.cdgtaxi.ibs.util.ComponentUtil;
import com.cdgtaxi.ibs.util.DateUtil;
import com.cdgtaxi.ibs.util.StringUtil;
import com.cdgtaxi.ibs.web.component.Constants;
import com.lowagie.text.PageSize;

public class SearchInvoiceWindow extends SearchByAccountWindow{
	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(SearchInvoiceWindow.class);

	protected Longbox invoiceNoFromLB;
	protected Longbox invoiceNoToLB;
	protected Datebox invoiceDateFromDB;
	protected Datebox invoiceDateToDB;
	protected Listbox invoiceStatusLB;
	protected Listbox resultLB;

	@Override
	public void onCreate(CreateEvent ce) throws Exception{
		super.onCreate(ce);

		invoiceNoFromLB = (Longbox) getFellow("invoiceNoFromLB");
		invoiceNoToLB = (Longbox) getFellow("invoiceNoToLB");
		invoiceDateFromDB = (Datebox) getFellow("invoiceDateFromDB");
		invoiceDateToDB = (Datebox) getFellow("invoiceDateToDB");
		invoiceStatusLB = (Listbox) getFellow("invoiceStatusDDL");

		List<Listitem> invoiceStatuses = ComponentUtil.convertToListitems(NonConfigurableConstants.INVOICE_STATUS, true);
		invoiceStatusLB.appendChild(ComponentUtil.createNotRequiredListItem());
		for(Listitem listItem : invoiceStatuses){
			if(listItem.getLabel().equals(NonConfigurableConstants.PAYMENT_MODE_MEMO)) {
				continue;
			}
			invoiceStatusLB.appendChild(listItem);
		}

		resultLB = (Listbox) getFellow("resultList");
		resultLB.setMold(Constants.LISTBOX_MOLD_PAGING);
		resultLB.setPageSize(10);
	}

	public void searchInvoice() throws InterruptedException{
		try {
			SearchInvoiceForm form = buildSearchForm();
			if (form == null) {
				return;
			}
			if(!form.isAtLeastOneCriteriaSelected()){
				Messagebox.show("Please enter one of the selection criteria",
						"Error", Messagebox.OK, Messagebox.ERROR);
				return;
			}

			displayProcessing();
			List<BmtbInvoiceHeader> invoiceHeader =
				businessHelper.getInvoiceBusiness().searchInvoice(form);

			resultLB.getItems().clear();
			if (resultLB.getListfoot() != null) {
				resultLB.removeChild(resultLB.getListfoot());
			}

			if (invoiceHeader.isEmpty()) {
				resultLB.appendChild(ComponentUtil.createNoRecordsFoundListFoot(
						resultLB.getListhead().getChildren().size()));
				return;
			}
			
			if(invoiceHeader.size()>ConfigurableConstants.getMaxQueryResult())
				Messagebox.show(NonConfigurableConstants.getExceedMaxResultMessage(), "Alert", Messagebox.OK, Messagebox.INFORMATION);

			for (final BmtbInvoiceHeader ih : invoiceHeader) {
				Listitem item = new Listitem();
				item.setValue(ih);

				final AmtbAccount debtToAccount = ih.getAmtbAccountByDebtTo();
				AmtbAccount topLevelAccount =
					businessHelper.getAccountBusiness().getTopLevelAccount(debtToAccount);
				item.appendChild(newListcell(new Integer(topLevelAccount.getCustNo()), StringUtil.GLOBAL_STRING_FORMAT));
				item.appendChild(newListcell(topLevelAccount.getAccountName()));
				
				String invoiceNo = ih.getInvoiceNo().toString();
				if(ih.getInvoiceFormat().equals(NonConfigurableConstants.INVOICE_FORMAT_DEPOSIT))
					invoiceNo += " (D)";
				else if(ih.getInvoiceFormat().equals(NonConfigurableConstants.INVOICE_FORMAT_MISC))
					invoiceNo += " (M)";
				
				Listcell invoiceNoCell = new Listcell();
				invoiceNoCell.setLabel(invoiceNo);
				invoiceNoCell.setValue(ih.getInvoiceNo());
				item.appendChild(invoiceNoCell);
				
				item.appendChild(newListcell(ih.getNewTxn(), StringUtil.GLOBAL_DECIMAL_FORMAT));

				BigDecimal appliedAmount = ih.getNewTxn().subtract(ih.getOutstandingAmount());
				item.appendChild(newListcell(appliedAmount, StringUtil.GLOBAL_DECIMAL_FORMAT));
				item.appendChild(newListcell(ih.getOutstandingAmount(), StringUtil.GLOBAL_DECIMAL_FORMAT));

				BigDecimal debitCreditAmount = new BigDecimal("0.00");
				for (BmtbNote note : ih.getBmtbNotes()) {
					BigDecimal noteAmount = new BigDecimal(0);
					if (NonConfigurableConstants.NOTE_TXN_TYPE_TRIP_BASED.equals(note.getNoteTxnType()))
					{
						// Trips based
						noteAmount = note.getNoteAmount().subtract(note.getPromoDis()).add(note.getGst()).add(note.getAdminFee()).subtract(note.getProdDis());
					}
					else
					{
						// Non-trips based
						noteAmount = note.getNoteAmount().add(note.getGst()).subtract(note.getDiscount());
					}
					// Do not allow cancelled notes to be added
					if (!NonConfigurableConstants.NOTE_STATUS_CANCELLED.equals(note.getStatus()) && !NonConfigurableConstants.NOTE_STATUS_REJECTED.equals(note.getStatus()))
					{
						if (note.getNoteType().equals(NonConfigurableConstants.NOTE_TYPE_CREDIT)) {
							debitCreditAmount = debitCreditAmount.subtract(noteAmount);
						} else {
							debitCreditAmount = debitCreditAmount.add(noteAmount);
						}
					}
					logger.info("DebitCreditAmount " + debitCreditAmount);
				}
				item.appendChild(newListcell(debitCreditAmount, StringUtil.GLOBAL_DECIMAL_FORMAT));
				item.appendChild(newListcell(ih.getInvoiceDate(), DateUtil.GLOBAL_DATE_FORMAT));
				item.appendChild(newListcell(NonConfigurableConstants.INVOICE_STATUS.get(ih.getInvoiceStatus().toString())));
				item.appendChild(newListcell(debtToAccount.getAccountName()));
				
				if(topLevelAccount.getPrintTaxInvoiceOnly() != null)
				{
					if(topLevelAccount.getPrintTaxInvoiceOnly().equalsIgnoreCase("Y"))
						item.appendChild(newListcell("YES"));
					else if(topLevelAccount.getPrintTaxInvoiceOnly().equalsIgnoreCase("N"))
						item.appendChild(newListcell("NO"));
				}
				else
					item.appendChild(newListcell("NO"));
				
				final Button downloadButton = new Button("pdf");
				downloadButton.addEventListener("onClick", new EventListener() {
					public void onEvent(Event event) throws Exception {
						download(ih.getInvoiceNo().toString());
					}
				});
				final Button reprintButton = new Button("reprint");
				reprintButton.addEventListener("onClick", new EventListener() {
					public void onEvent(Event event) throws Exception {
						reprint(debtToAccount.getAccountNo(), ih.getInvoiceNo(), ih.getInvoiceHeaderNo(), ih.getInvoiceFormat(), NonConfigurableConstants.getBoolean(ih.getPrepaidFlag()));
					}
				});
				Listcell cell = new Listcell();
				cell.appendChild(downloadButton);
				cell.appendChild(reprintButton);
				item.appendChild(cell);

				resultLB.appendChild(item);
			}
			
			if(invoiceHeader.size()>ConfigurableConstants.getMaxQueryResult())
				resultLB.removeItemAt(ConfigurableConstants.getMaxQueryResult());
			
			Grid exportButtonGrid = (Grid) getFellow("gridButton");
			exportButtonGrid.setVisible(true);
		} catch (Exception ex) {
			logger.error(ex.getMessage(), ex);
			Messagebox.show(NonConfigurableConstants.COMMON_ERROR_MESSAGE,
					"Error", Messagebox.OK, Messagebox.ERROR);
		}
	}

	protected SearchInvoiceForm buildSearchForm() throws InterruptedException {
		SearchInvoiceForm form = new SearchInvoiceForm();
		populateAccountForm(form);

		if (invoiceNoFromLB.getValue() != null
				&& invoiceNoToLB.getValue() != null
				&& invoiceNoFromLB.getValue() > invoiceNoToLB.getValue()) {
			Messagebox.show("Invoice No. To shouldn't be less than Invoice No. From",
					"View Invoice", Messagebox.OK, Messagebox.INFORMATION);
			return null;
		}

		if (invoiceDateFromDB.getValue() != null
				&& invoiceDateToDB.getValue() != null
				&& invoiceDateFromDB.getValue().after(invoiceDateToDB.getValue())) {
			Messagebox.show("Invoice Date To shouldn't be earlier than Invoice Date From",
					"View Invoice", Messagebox.OK, Messagebox.INFORMATION);
			return null;
		}

		form.setInvoiceNoFrom(invoiceNoFromLB.getValue());
		form.setInvoiceNoTo(invoiceNoToLB.getValue());
		form.setInvoiceDateFrom(invoiceDateFromDB.getValue());
		form.setInvoiceDateTo(invoiceDateToDB.getValue());

		String invoiceStatus = "";
		if (invoiceStatusLB.getSelectedItem() != null) {
			invoiceStatus = (String) invoiceStatusLB.getSelectedItem().getValue();
		}
		form.setInvoiceStatus(invoiceStatus);

		return form;
	}

	public void updateInvoiceNoTo() {
		if (invoiceNoToLB.getValue() == null) {
			invoiceNoToLB.setValue(invoiceNoFromLB.getValue());
		}
	}

	public void updateInvoiceDateTo() {
		if (invoiceDateToDB.getValue() == null) {
			invoiceDateToDB.setValue(invoiceDateFromDB.getValue());
		}
	}

	private void download(String invoiceNo) {
		byte[] file = businessHelper.getInvoiceBusiness().getInvoiceFile(invoiceNo);
		if (file != null) {
			Filedownload.save(file, com.cdgtaxi.ibs.report.Constants.FORMAT_PDF,
					"invoice_" + invoiceNo + ".pdf");
		} else {
			try {
				Messagebox.show("The PDF for Invoice " + invoiceNo + " is not available.");
			} catch (InterruptedException e) {}
		}
	}

	private void reprint(Integer accountNo, Long invoiceNo, Long invoiceHeaderNo, String invoiceFormat, boolean isPrepaidInvoice) throws InterruptedException{
		logger.info("");

		try{
			AmtbAccount amtbAcct = this.businessHelper.getAccountBusiness().getAccount(accountNo.toString());
			
			if(invoiceFormat.equals(NonConfigurableConstants.INVOICE_FORMAT_MISC)){
				if(isPrepaidInvoice){
					
					BmtbInvoiceHeader header = this.businessHelper.getPrepaidBusiness().getPrepaidInvoiceHeader(invoiceHeaderNo);
					byte[] bytes = this.businessHelper.getReportBusiness().generatePrepaidInvoice(header.getAmtbAccountByAccountNo(), header.getInvoiceHeaderNo().toString(), false);
					this.businessHelper.getInvoiceBusiness().updateInvoiceHeaderFile(bytes, header);
				
					Filedownload.save(bytes, "application/pdf", "invoice_" + invoiceNo + "_reprint.pdf");
					
				} else {
					
					Map<String, Object> args = new HashMap<String, Object>();
					args.put("invoiceHeaderNo", invoiceHeaderNo);
					args.put("accountNo", accountNo);
					final Window win = (Window) Executions.createComponents(Uri.REPRINT_INVOICE, null, args);
					win.setMaximizable(false);
					win.doModal();
				}
			}
			else{
				Properties reportParamsProperties = new Properties();
				String reportName = "";
				if(invoiceFormat.equals(NonConfigurableConstants.INVOICE_FORMAT_ACCOUNT)) {
					reportName = NonConfigurableConstants.REPORT_NAME_INV_ACCT_FORMAT;
					reportParamsProperties.put("invoiceOnly", amtbAcct.getPrintTaxInvoiceOnly());
				} else if(invoiceFormat.equals(NonConfigurableConstants.INVOICE_FORMAT_PERSONAL)) {
					reportName = NonConfigurableConstants.REPORT_NAME_INV_PERS_FORMAT;
					reportParamsProperties.put("invoiceOnly", amtbAcct.getPrintTaxInvoiceOnly());
				} else if(invoiceFormat.equals(NonConfigurableConstants.INVOICE_FORMAT_SUBACCOUNT)) {
					reportName = NonConfigurableConstants.REPORT_NAME_INV_SUB_ACCT_FORMAT;
					reportParamsProperties.put("invoiceOnly", amtbAcct.getPrintTaxInvoiceOnly());
				} else if(invoiceFormat.equals(NonConfigurableConstants.INVOICE_FORMAT_DEPOSIT)) {
					reportName = NonConfigurableConstants.REPORT_NAME_INV_DEPOSIT;
				}
				
				if(!invoiceFormat.equals(NonConfigurableConstants.INVOICE_FORMAT_DEPOSIT)){
					reportParamsProperties.put("invoiceHeaderTbl", "bmtb_invoice_header");
					reportParamsProperties.put("invoiceSummaryTbl", "bmtb_invoice_summary");
					reportParamsProperties.put("invoiceDetailTbl", "bmtb_invoice_detail");
					reportParamsProperties.put("invoiceTxnTbl", "bmtb_invoice_txn");
					reportParamsProperties.put("invoicePaymentHeaderTbl", "bmtb_invoice_payment_header");
					reportParamsProperties.put("invoicePaymentDetailTbl", "bmtb_invoice_payment_detail");
					
					Map pdfGenProperties = (Map)SpringUtil.getBean("pdfGenProperties");
					Integer.parseInt((String)pdfGenProperties.get("pdfgen.buffer.duedate"));
					reportParamsProperties.put("bufferDays", pdfGenProperties.get("pdfgen.buffer.duedate"));
				}
				
				reportParamsProperties.put("invoiceHeaderNo", ""+invoiceHeaderNo);
				this.displayProcessing();
				logger.info("Reprint Invoice ["+reportName+"] in PDF - Invoice Header No "+invoiceHeaderNo);
				byte[] pdfByteArray = this.businessHelper.getReportBusiness().generate(reportName, null, com.cdgtaxi.ibs.report.Constants.FORMAT_PDF, reportParamsProperties);
				Filedownload.save(pdfByteArray, "application/pdf", "invoice_" + invoiceNo + "_reprint.pdf");
			}
		}
		catch(Exception e){
			Messagebox.show(com.cdgtaxi.ibs.common.NonConfigurableConstants.COMMON_ERROR_MESSAGE,
					"Error", Messagebox.OK, Messagebox.ERROR);
			e.printStackTrace();
		}
	}

	public void displayInvoiceDetails()  throws InterruptedException{
		try {
			Listitem selectedItem = resultLB.getSelectedItem();
			BmtbInvoiceHeader ih = (BmtbInvoiceHeader)selectedItem.getValue();

			HashMap<String, String> map = new HashMap<String, String>();
			map.put("invoiceNo", ih.getInvoiceNo().toString());
			forward(Uri.VIEW_INVOICE, map);
		} catch (Exception ex) {
			ex.printStackTrace();
			Messagebox.show(ex.toString());
		} finally {
			resultLB.clearSelection();
		}
	}

	@Override
	public void reset() throws InterruptedException {
		super.reset();

		invoiceNoFromLB.setValue(null);
		invoiceNoToLB.setValue(null);
		invoiceDateFromDB.setValue(null);
		invoiceDateToDB.setValue(null);
		invoiceStatusLB.setSelectedIndex(0);
		resultLB.getItems().clear();
	}
	
	public void exportResult() throws InterruptedException, IOException {
		logger.info("exportResult");
		List<PdfExportItem> items = new ArrayList<PdfExportItem>();
		items.add(new PdfExportItem((Grid)this.getFellow("searchFieldGrid"), "Search Invoice", null));
		items.add(new PdfExportItem((Listbox)this.getFellow("resultList"), "Invoice Search Result", new int[]{10}));
		
	    ByteArrayOutputStream out = new ByteArrayOutputStream();
	     
		ZkPdfExporter exp = new ZkPdfExporter(PageSize.A4.rotate());
		try{
		exp.export(items, out, accountNameComboBox.getValue());
	     
	    AMedia amedia = new AMedia("Search_Invoice_Result.pdf", "pdf", "application/pdf", out.toByteArray());
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
