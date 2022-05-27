package com.cdgtaxi.ibs.billing.ui;

import java.math.BigDecimal;
import java.util.List;

import org.apache.log4j.Logger;
import org.zkoss.zk.ui.event.CreateEvent;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zul.Button;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Filedownload;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Longbox;
import org.zkoss.zul.Messagebox;

import com.cdgtaxi.ibs.common.ConfigurableConstants;
import com.cdgtaxi.ibs.common.NonConfigurableConstants;
import com.cdgtaxi.ibs.common.model.AmtbAccount;
import com.cdgtaxi.ibs.common.model.BmtbDraftInvHeader;
import com.cdgtaxi.ibs.common.model.forms.SearchInvoiceForm;
import com.cdgtaxi.ibs.util.ComponentUtil;
import com.cdgtaxi.ibs.util.DateUtil;
import com.cdgtaxi.ibs.util.StringUtil;
import com.cdgtaxi.ibs.web.component.Constants;

public class SearchDraftInvoiceWindow extends SearchByAccountWindow{
	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(SearchDraftInvoiceWindow.class);

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

	public void searchDraftInvoice() throws InterruptedException{
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
			List<BmtbDraftInvHeader> invoiceHeader =
				businessHelper.getInvoiceBusiness().searchDraftInvoice(form);

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
			
			for (final BmtbDraftInvHeader ih : invoiceHeader) {
				Listitem item = new Listitem();
				item.setValue(ih);

				AmtbAccount debtToAccount = ih.getAmtbAccountByDebtTo();
				AmtbAccount topLevelAccount =
					businessHelper.getAccountBusiness().getTopLevelAccount(debtToAccount);
				item.appendChild(newListcell(new Integer(topLevelAccount.getCustNo()), StringUtil.GLOBAL_STRING_FORMAT));
				item.appendChild(newListcell(topLevelAccount.getAccountName()));
				item.appendChild(newListcell(ih.getInvoiceNo(), StringUtil.GLOBAL_STRING_FORMAT));
				item.appendChild(newListcell(ih.getNewTxn(), StringUtil.GLOBAL_DECIMAL_FORMAT));

				BigDecimal appliedAmount = ih.getNewTxn().subtract(ih.getOutstandingAmount());
				item.appendChild(newListcell(appliedAmount, StringUtil.GLOBAL_DECIMAL_FORMAT));
				item.appendChild(newListcell(ih.getOutstandingAmount(), StringUtil.GLOBAL_DECIMAL_FORMAT));

				BigDecimal debitCreditAmount = new BigDecimal("0.00");
				item.appendChild(newListcell(debitCreditAmount, StringUtil.GLOBAL_DECIMAL_FORMAT));
				item.appendChild(newListcell(ih.getInvoiceDate(), DateUtil.GLOBAL_DATE_FORMAT));
				item.appendChild(newListcell(NonConfigurableConstants.INVOICE_STATUS.get(ih.getInvoiceStatus().toString())));
				item.appendChild(newListcell(debtToAccount.getAccountName()));

				final Button downloadButton = new Button("pdf");
				downloadButton.addEventListener("onClick", new EventListener() {
					public void onEvent(Event event) throws Exception {
						download(ih.getInvoiceNo().toString());
					}
				});
				Listcell cell = new Listcell();
				cell.appendChild(downloadButton);
				item.appendChild(cell);

				resultLB.appendChild(item);
			}
			
			if(invoiceHeader.size()>ConfigurableConstants.getMaxQueryResult())
				resultLB.removeItemAt(ConfigurableConstants.getMaxQueryResult());
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
		byte[] file = businessHelper.getInvoiceBusiness().getDraftInvoiceFile(invoiceNo);
		if (file != null) {
			Filedownload.save(file, com.cdgtaxi.ibs.report.Constants.FORMAT_PDF,
					"draft_invoice_" + invoiceNo + ".pdf");
		} else {
			try {
				Messagebox.show("The PDF for Invoice " + invoiceNo + " is not available.");
			} catch (InterruptedException e) {}
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
}
