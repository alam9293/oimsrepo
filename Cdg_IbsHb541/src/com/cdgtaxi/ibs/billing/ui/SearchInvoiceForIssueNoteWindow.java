package com.cdgtaxi.ibs.billing.ui;

import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Messagebox;

import com.cdgtaxi.ibs.Uri;
import com.cdgtaxi.ibs.common.ConfigurableConstants;
import com.cdgtaxi.ibs.common.NonConfigurableConstants;
import com.cdgtaxi.ibs.common.model.BmtbInvoiceHeader;
import com.cdgtaxi.ibs.common.model.forms.SearchInvoiceForm;
import com.cdgtaxi.ibs.util.ComponentUtil;
import com.cdgtaxi.ibs.util.DateUtil;
import com.cdgtaxi.ibs.util.StringUtil;

@SuppressWarnings("serial")
public class SearchInvoiceForIssueNoteWindow extends SearchInvoiceWindow {
	private static final Logger logger = Logger.getLogger(SearchInvoiceForIssueNoteWindow.class);

	//	private Listbox resultLB;

	@Override
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
			List<BmtbInvoiceHeader> invoiceHeader = businessHelper.getInvoiceBusiness().searchNoteIssuableInvoice(form);

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
				item.appendChild(newListcell(ih.getInvoiceNo(), StringUtil.GLOBAL_STRING_FORMAT));
				item.appendChild(newListcell(ih.getNewTxn(), StringUtil.GLOBAL_DECIMAL_FORMAT));
				item.appendChild(newListcell(ih.getInvoiceDate(), DateUtil.GLOBAL_DATE_FORMAT));
				item.appendChild(newListcell(NonConfigurableConstants.INVOICE_STATUS.get(ih.getInvoiceStatus().toString())));
				item.appendChild(newListcell(ih.getAmtbAccountByDebtTo().getAccountName()));

				resultLB.appendChild(item);
			}
			
			if(invoiceHeader.size()>ConfigurableConstants.getMaxQueryResult())
				resultLB.removeItemAt(ConfigurableConstants.getMaxQueryResult());
		}
		catch (Exception ex) {
			logger.error(ex.getMessage(), ex);
			Messagebox.show(NonConfigurableConstants.COMMON_ERROR_MESSAGE,
					"Error", Messagebox.OK, Messagebox.ERROR);
		}
	}

	public void issueNote() throws InterruptedException{
		try {
			Listitem selectedItem = resultLB.getSelectedItem();
			BmtbInvoiceHeader ih = (BmtbInvoiceHeader)selectedItem.getValue();

			HashMap<String, String> map = new HashMap<String, String>();
			map.put("invoiceNo", ih.getInvoiceNo().toString());
			forward(Uri.ISSUE_NOTE, map);
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
		resultLB.getItems().clear();
	}
}
