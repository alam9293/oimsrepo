package com.cdgtaxi.ibs.nonbillable.ui;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zkoss.util.media.AMedia;
import org.zkoss.zk.ui.Components;
import org.zkoss.zk.ui.SuspendNotAllowedException;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zk.ui.ext.AfterCompose;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Decimalbox;
import org.zkoss.zul.Filedownload;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Longbox;
import org.zkoss.zul.Messagebox;

import com.cdgtaxi.ibs.Uri;
import com.cdgtaxi.ibs.common.ConfigurableConstants;
import com.cdgtaxi.ibs.common.NonConfigurableConstants;
import com.cdgtaxi.ibs.common.model.BmtbBankPayment;
import com.cdgtaxi.ibs.common.model.forms.SearchBankPaymentAdviseForm;
import com.cdgtaxi.ibs.common.ui.CommonWindow;
import com.cdgtaxi.ibs.exporter.PdfExportItem;
import com.cdgtaxi.ibs.exporter.ZkPdfExporter;
import com.cdgtaxi.ibs.master.model.MstbAcquirer;
import com.cdgtaxi.ibs.util.ComponentUtil;
import com.cdgtaxi.ibs.web.component.CapsTextbox;
import com.cdgtaxi.ibs.web.component.Constants;
import com.lowagie.text.PageSize;

@SuppressWarnings("serial")
public class ManageBankPaymentAdviseWindow extends CommonWindow implements AfterCompose {
	
	private CapsTextbox txnRefNoTB;
	private Datebox creditDateFromDB, creditDateToDB;
	private Listbox acquirerLB, resultList;
	private Decimalbox collectionAmountDMB;
	private Longbox paymentNoTB;

	public void afterCompose() {
		//wire variables
		Components.wireVariables(this, this);
		
		acquirerLB.appendChild(ComponentUtil.createNotRequiredAllListItem());
		List<MstbAcquirer> acquirers = this.businessHelper.getNonBillableBusiness().getAllAcquirers();
		for(MstbAcquirer acquirer : acquirers){
			Listitem item = new Listitem();
			item.setValue(acquirer);
			item.setLabel(acquirer.getName());
			acquirerLB.appendChild(item);
		}
		acquirerLB.setSelectedIndex(0);
	}
	
	public void reset(){
		txnRefNoTB.setValue("");
		acquirerLB.setSelectedIndex(0);
		collectionAmountDMB.setText("");
		creditDateFromDB.setText("");
		creditDateToDB.setText("");
		paymentNoTB.setText("");
		
		resultList.getItems().clear();
		if(resultList.getListfoot()==null) resultList.appendChild(ComponentUtil.createNoRecordsFoundListFoot(11));
	}
	
	public void search() throws SuspendNotAllowedException, InterruptedException{
		this.displayProcessing();
		resultList.getItems().clear();
		
		try{
			if(creditDateFromDB.getValue()!=null && creditDateToDB.getValue()==null)
				creditDateToDB.setValue(creditDateFromDB.getValue());
			else if(creditDateFromDB.getValue()==null && creditDateToDB.getValue()!=null)
				creditDateFromDB.setValue(creditDateToDB.getValue());
			
			if(creditDateFromDB.getValue()!=null && creditDateToDB.getValue()!=null)
				if(creditDateFromDB.getValue().compareTo(creditDateToDB.getValue()) == 1)
					throw new WrongValueException(creditDateFromDB, "Credit Date From cannot be later than Credit Date To.");
			
			SearchBankPaymentAdviseForm form = this.buildSearchForm();
			
			if(form.creditDateFrom==null && form.creditDateTo==null && 
					form.collectionAmount==null && form.paymentNo==null &&
					(form.txnRefNo==null || form.txnRefNo.length()==0)){
				Messagebox.show("At least one of these criteria (Payment No, Txn Reference No, Bank Collection Amount, Credit Date) must have input", "Alert", Messagebox.OK, Messagebox.INFORMATION);
				return;
			}
			
			List<BmtbBankPayment> payments = this.businessHelper.getNonBillableBusiness().searchBankPaymentAdvise(form);
			if(payments.size()>0){
				
				if(payments.size()>ConfigurableConstants.getMaxQueryResult())
					Messagebox.show(NonConfigurableConstants.getExceedMaxResultMessage(), "Alert", Messagebox.OK, Messagebox.INFORMATION);
				
				for(BmtbBankPayment payment : payments){
					Listitem item = new Listitem();
					
					item.setValue(payment);
					
					item.appendChild(newListcell(payment.getPaymentNo()));
					if(payment.getTxnRefNo()!=null) item.appendChild(newListcell(payment.getTxnRefNo()));
					else item.appendChild(newEmptyListcell("", "-"));
					item.appendChild(newListcell(payment.getCreditDate()));
					item.appendChild(newListcell(payment.getCollectionAmount()));
					item.appendChild(newListcell(payment.getTotalTxnAmount()));
					item.appendChild(newListcell(payment.getRejectedAmount()));
					item.appendChild(newListcell(payment.getMdrValue().add(payment.getMdrAdjustment())));
					item.appendChild(newListcell(payment.getMarkup()));
					item.appendChild(newListcell(payment.getCommission()));
					item.appendChild(newListcell(payment.getInterchange()));
					item.appendChild(newListcell(payment.getSchemeFee()));
					item.appendChild(newListcell(payment.getChargebackAmount()));
					item.appendChild(newListcell(payment.getChargebackReverseAmt()));
					item.appendChild(newListcell(payment.getRefundAmt()));
					item.appendChild(newListcell(payment.getRefundReverseAmt()));
					item.appendChild(newListcell(payment.getOtherCredit()));
					item.appendChild(newListcell(payment.getOtherDebit()));


					resultList.appendChild(item);
				}
				
				if(payments.size()>ConfigurableConstants.getMaxQueryResult())
					resultList.removeItemAt(ConfigurableConstants.getMaxQueryResult());
				
				if(resultList.getListfoot()!=null)
					resultList.removeChild(resultList.getListfoot());
				
				((Grid)this.getFellow("gridButton")).setVisible(true);
			}
			else{
				if(resultList.getListfoot()==null){
					resultList.appendChild(ComponentUtil.createNoRecordsFoundListFoot(8));
				}
				
				((Grid)this.getFellow("gridButton")).setVisible(false);
			}
			
			resultList.setMold(Constants.LISTBOX_MOLD_PAGING);
			resultList.setPageSize(10);

		}
		catch(WrongValueException wve){
			throw wve;
		}
		catch(Exception e){
			Messagebox.show(com.cdgtaxi.ibs.common.NonConfigurableConstants.COMMON_ERROR_MESSAGE, 
					"Error", Messagebox.OK, Messagebox.ERROR);
			e.printStackTrace();
		}
	}
	
	@SuppressWarnings("unchecked")
	public void view() throws InterruptedException{
		Map map = new HashMap();
		map.put("bankPayment", resultList.getSelectedItem().getValue());
		this.forward(Uri.VIEW_BANK_PAYMENT_ADVISE, map);
	}
	
	@Override
	public void refresh() throws InterruptedException {
		resultList.clearSelection();
	}
	
	private SearchBankPaymentAdviseForm buildSearchForm(){
		SearchBankPaymentAdviseForm form = new SearchBankPaymentAdviseForm();
		
		form.creditDateFrom = creditDateFromDB.getValue();
		form.creditDateTo = creditDateToDB.getValue();
		form.acquirer = (MstbAcquirer)acquirerLB.getSelectedItem().getValue();
		form.txnRefNo = txnRefNoTB.getValue();
		form.collectionAmount = collectionAmountDMB.getValue();
		form.paymentNo = paymentNoTB.getValue();
		
		return form;
	}
	
	public void exportResult() throws InterruptedException, IOException {
		List<PdfExportItem> items = new ArrayList<PdfExportItem>();
		items.add(new PdfExportItem((Grid)this.getFellow("searchFieldGrid"), "Search Bank Payment Advise", null));
		items.add(new PdfExportItem((Listbox)this.getFellow("resultList"), "Bank Payment Advice Search Result", null));
		
	    ByteArrayOutputStream out = new ByteArrayOutputStream();
	     
		ZkPdfExporter exp = new ZkPdfExporter(PageSize.A4.rotate());
		
		try{
			exp.export(items, out);
		     
		    AMedia amedia = new AMedia("Bank_Payment_Advice_Result.pdf", "pdf", "application/pdf", out.toByteArray());
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
