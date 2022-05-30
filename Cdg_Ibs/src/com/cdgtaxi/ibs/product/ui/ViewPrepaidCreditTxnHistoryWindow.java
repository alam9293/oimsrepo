package com.cdgtaxi.ibs.product.ui;

import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;
import org.zkoss.zk.ui.event.CreateEvent;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Longbox;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;

import com.cdgtaxi.ibs.Uri;
import com.cdgtaxi.ibs.billing.ui.SearchByAccountWindow;
import com.cdgtaxi.ibs.common.ConfigurableConstants;
import com.cdgtaxi.ibs.common.NonConfigurableConstants;
import com.cdgtaxi.ibs.common.model.BmtbInvoiceHeader;
import com.cdgtaxi.ibs.common.model.forms.SearchPrepaidRequestForm;
import com.cdgtaxi.ibs.util.ComponentUtil;
import com.cdgtaxi.ibs.web.component.Constants;
	
	public class ViewPrepaidCreditTxnHistoryWindow extends SearchByAccountWindow {

	private static final long serialVersionUID = 5482551235361783789L;
	private static Logger logger = Logger.getLogger(ViewProductHistoryWindow.class);
	private String productNo="";
	private Listbox resultList;
	protected Longbox invoiceNo;
	protected Textbox cardNo;
	protected Listbox resultLB;
	protected Datebox requestDateFromField;
	protected Datebox requestDateToField;
	@SuppressWarnings("unchecked")
	public ViewPrepaidCreditTxnHistoryWindow(){
		//
		
	}
	
	@Override
	public void onCreate(CreateEvent ce) throws Exception{

		//super.onCreate(ce);

		invoiceNo = (Longbox) getFellow("invoiceNo");
		cardNo = (Textbox) getFellow("cardNo");
		requestDateFromField = (Datebox) getFellow("requestDateFromField");
		requestDateToField = (Datebox) getFellow("requestDateToField");

		resultLB = (Listbox) getFellow("resultList");
		resultLB.setMold(Constants.LISTBOX_MOLD_PAGING);
		resultLB.setPageSize(10);
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
	
	protected SearchPrepaidRequestForm buildSearchForm() throws InterruptedException {
		SearchPrepaidRequestForm form = new SearchPrepaidRequestForm();
	//	populateAccountForm(form);
	
		
		if (invoiceNo.getValue() == null
				&& cardNo.getValue().length() == 0  && (requestDateFromField.getValue() == null && requestDateToField.getValue() == null)) {
			Messagebox.show("Please fill in at least 1 box"
				);
			return null;
		}

		if((requestDateFromField.getValue() == null && requestDateToField.getValue() != null) 
				|| (requestDateFromField.getValue() != null && requestDateToField.getValue() == null)) {
			Messagebox.show("Please fill in at both From and To Date"
					);
			return null;
		}

		form.setCardNo(cardNo.getValue());
		form.setInvoiceNoFrom(invoiceNo.getValue());
		form.setInvoiceNoTo(invoiceNo.getValue());
		form.setRequestDateFrom(requestDateFromField.getValue());
		form.setRequestDateTo(requestDateToField.getValue());

		return form;
	}

	
	public void search() throws InterruptedException{
		try {
			SearchPrepaidRequestForm form = buildSearchForm();
			if (form == null) {
				return;
			}
			
			displayProcessing();

			this.displayProcessing();
			List<Object[]> requestList = businessHelper.getPrepaidBusiness().searchPrepaidCreditInvoiceRequest(form);
			resultLB.getItems().clear();
			
			if (resultLB.getListfoot() != null) {
				resultLB.removeChild(resultLB.getListfoot());
			}

			if (requestList.isEmpty()) {
				resultLB.appendChild(ComponentUtil.createNoRecordsFoundListFoot(
						resultLB.getListhead().getChildren().size()));
				return;
			}

			for(Object[] batchInfo : requestList){
				Listitem item = new Listitem();
				BmtbInvoiceHeader ih = businessHelper.getInvoiceBusiness().getInvoice(batchInfo[3].toString());
				item.setValue(ih);

				String invoiceNo = "";
				if(ih.getInvoiceFormat().equals(NonConfigurableConstants.INVOICE_FORMAT_DEPOSIT))
					invoiceNo += " (D)";
				else if(ih.getInvoiceFormat().equals(NonConfigurableConstants.INVOICE_FORMAT_MISC))
					invoiceNo += " (M)";
				item.appendChild(newListcell(batchInfo[2]));
				

				item.appendChild(newListcell(batchInfo[3]+invoiceNo));
				
				item.appendChild(newListcell(batchInfo[1]));
				item.appendChild(newListcell(batchInfo[0]));
								
	

				resultLB.appendChild(item);
			}
			
			if(requestList.size()>ConfigurableConstants.getMaxQueryResult())
				resultLB.removeItemAt(ConfigurableConstants.getMaxQueryResult());
			

		} catch (Exception ex) {
			logger.error(ex.getMessage(), ex);
			Messagebox.show(NonConfigurableConstants.COMMON_ERROR_MESSAGE,
					"Error", Messagebox.OK, Messagebox.ERROR);
		}
	}
	
	
	@Override
	public void refresh() throws InterruptedException {
		// TODO Auto-generated method stub
		
	}
		

	
}


