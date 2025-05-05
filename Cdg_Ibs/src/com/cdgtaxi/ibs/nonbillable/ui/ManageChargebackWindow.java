package com.cdgtaxi.ibs.nonbillable.ui;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
import org.zkoss.zul.Messagebox;

import com.cdgtaxi.ibs.Uri;
import com.cdgtaxi.ibs.common.ConfigurableConstants;
import com.cdgtaxi.ibs.common.NonConfigurableConstants;
import com.cdgtaxi.ibs.common.model.TmtbNonBillableTxn;
import com.cdgtaxi.ibs.common.model.forms.SearchChargebackForm;
import com.cdgtaxi.ibs.common.ui.CommonWindow;
import com.cdgtaxi.ibs.exporter.PdfExportItem;
import com.cdgtaxi.ibs.exporter.ZkPdfExporter;
import com.cdgtaxi.ibs.master.model.MstbAcquirer;
import com.cdgtaxi.ibs.master.model.MstbMasterTable;
import com.cdgtaxi.ibs.util.ComponentUtil;
import com.cdgtaxi.ibs.web.component.CapsTextbox;
import com.cdgtaxi.ibs.web.component.Constants;
import com.lowagie.text.PageSize;

@SuppressWarnings("serial")
public class ManageChargebackWindow extends CommonWindow implements AfterCompose {
	
	private Listbox acquirerLB, paymentTypeLB, resultList, offlineLB, txnStatusLB;
	private Datebox tripDateFromDB, tripDateToDB;
	private CapsTextbox taxiNoTB, jobNoTB, driverIdTB;
	private Decimalbox totalAmtDMB;
	
	public void afterCompose() {
		//wire variables
		Components.wireVariables(this, this);
		
		List<MstbAcquirer> acquirers = this.businessHelper.getNonBillableBusiness().getAllAcquirers();
		for(MstbAcquirer acquirer : acquirers){
			Listitem item = new Listitem();
			item.setValue(acquirer);
			item.setLabel(acquirer.getName());
			acquirerLB.appendChild(item);
		}
		acquirerLB.setSelectedIndex(0);
		this.populatePaymentType((MstbAcquirer)acquirerLB.getSelectedItem().getValue());
		
		offlineLB.appendChild(ComponentUtil.createNotRequiredListItem());
		Set<String> ynKeys = NonConfigurableConstants.BOOLEAN_YN.keySet();
		for(String key : ynKeys){
			Listitem item = new Listitem();
			item.setValue(key);
			item.setLabel(NonConfigurableConstants.BOOLEAN_YN.get(key));
			offlineLB.appendChild(item);
		}
		offlineLB.setSelectedIndex(0);

		txnStatusLB.appendChild(ComponentUtil.createNotRequiredListItem());
		txnStatusLB.setSelectedIndex(0);
		txnStatusLB.appendChild(new Listitem("Refunded", NonConfigurableConstants.NON_BILLABLE_TXN_REFUNDED));
		txnStatusLB.appendChild(new Listitem("Chargeback",
				NonConfigurableConstants.NON_BILLABLE_TXN_CHARGEBACK));
	}
	
	public void populatePaymentType(MstbAcquirer acquirer){
		paymentTypeLB.getChildren().clear();
		
		Listitem allItem = new Listitem("All", null);
		paymentTypeLB.appendChild(allItem);
		
		List<MstbMasterTable> masters = this.businessHelper.getNonBillableBusiness().getPymtType(acquirer);
		for(MstbMasterTable master : masters){
			Listitem item = new Listitem();
			
			item.setValue(master.getMasterCode());
			item.setLabel(master.getMasterValue());
			
			paymentTypeLB.appendChild(item);
		}
		paymentTypeLB.setSelectedIndex(0);
	}
	
	public void reset(){
		acquirerLB.setSelectedIndex(0);
		offlineLB.setSelectedIndex(0);
		this.populatePaymentType((MstbAcquirer)acquirerLB.getSelectedItem().getValue());
		taxiNoTB.setValue("");
		jobNoTB.setValue("");
		driverIdTB.setValue("");
		tripDateFromDB.setText("");
		tripDateToDB.setText("");
		totalAmtDMB.setText("");
		
		resultList.getItems().clear();
		if(resultList.getListfoot()==null) resultList.appendChild(ComponentUtil.createNoRecordsFoundListFoot(12));
	}
	
	public void search() throws SuspendNotAllowedException, InterruptedException{
		this.displayProcessing();
		resultList.getItems().clear();
		
		try{
			if(tripDateFromDB.getValue()!=null && tripDateToDB.getValue()==null)
				tripDateToDB.setValue(tripDateFromDB.getValue());
			else if(tripDateFromDB.getValue()==null && tripDateToDB.getValue()!=null)
				tripDateFromDB.setValue(tripDateToDB.getValue());
			
			if(tripDateFromDB.getValue()!=null && tripDateToDB.getValue()!=null)
				if(tripDateFromDB.getValue().compareTo(tripDateToDB.getValue()) == 1)
					throw new WrongValueException(tripDateFromDB, "Trip Date From cannot be later than Trip Date To.");
			
			SearchChargebackForm form = this.buildSearchForm();
			
			if(form.tripDateFrom==null && form.tripDateTo==null && 
					form.totalAmount==null &&
					(form.taxiNo==null || form.taxiNo.length()==0) &&
					(form.jobNo==null || form.jobNo.length()==0) &&
					(form.driverID==null || form.driverID.length()==0)){
				Messagebox.show("At least one of these criteria (Trip Date, Total Amount, Taxi No, Job No, Driver ID) must have input", "Alert", Messagebox.OK, Messagebox.INFORMATION);
				return;
			}
			
			List<TmtbNonBillableTxn> txns = this.businessHelper.getNonBillableBusiness().searchChargeback(form);
			if(txns.size()>0){
				if(txns.size()>ConfigurableConstants.getMaxQueryResult())
					Messagebox.show(NonConfigurableConstants.getExceedMaxResultMessage(), "Alert", Messagebox.OK, Messagebox.INFORMATION);
				
				for(TmtbNonBillableTxn txn : txns){
					Listitem item = new Listitem();
					
					item.setValue(txn);
					
					item.appendChild(newListcell(txn.getCardNo()));
					item.appendChild(newListcell(txn.getMstbAcquirerPymtType().getMstbMasterTable().getMasterValue()));
					item.appendChild(newListcell(txn.getTripStartDt()));
					item.appendChild(newListcell(txn.getMstbMasterTableByServiceProvider().getMasterValue()));
					item.appendChild(newListcell(txn.getTaxiNo()));
					item.appendChild(newListcell(txn.getNric()));
					item.appendChild(newListcell(txn.getFareAmt()));
					item.appendChild(newListcell(txn.getAdminFee()));
					item.appendChild(newListcell(txn.getGst()));
					item.appendChild(newListcell(txn.getTotal()));
					item.appendChild(newListcell(NonConfigurableConstants.BOOLEAN_YN.get(txn.getOfflineFlag())));
					item.appendChild(newListcell(NonConfigurableConstants.NON_BILLABLE_TXN_STATUS.get(txn.getStatus())));
					
					resultList.appendChild(item);
				}
				
				if(txns.size()>ConfigurableConstants.getMaxQueryResult())
					resultList.removeItemAt(ConfigurableConstants.getMaxQueryResult());
				
				if(resultList.getListfoot()!=null)
					resultList.removeChild(resultList.getListfoot());
				
				
				((Grid)this.getFellow("gridButton")).setVisible(true);
			}
			else{
				if(resultList.getListfoot()==null){
					resultList.appendChild(ComponentUtil.createNoRecordsFoundListFoot(12));
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
		TmtbNonBillableTxn txn = (TmtbNonBillableTxn) resultList.getSelectedItem().getValue();
		String mode = null;

		if(txn.getStatus().equals(NonConfigurableConstants.NON_BILLABLE_TXN_REFUNDED))
			mode = ViewNonBillableTxnWindow.MODE_REFUNDED;
		else
			mode = ViewNonBillableTxnWindow.MODE_CHARGEBACK;
		
		if(txn.getStatus().equals(NonConfigurableConstants.NON_BILLABLE_TXN_CHARGEBACK)){
			if(this.businessHelper.getNonBillableBusiness().isChargebackTxnGLed(txn))
				mode = ViewNonBillableTxnWindow.MODE_VIEW;
		}
		
		Map map = new HashMap();
		map.put("txn", txn);
		map.put("batch", txn.getTmtbNonBillableBatch());
		map.put("mode", mode);
		this.forward(Uri.VIEW_NON_BILLABLE_TXN, map);
	}
	
	@Override
	public void refresh() throws InterruptedException {
		resultList.clearSelection();
	}
	
	private SearchChargebackForm buildSearchForm(){
		SearchChargebackForm form = new SearchChargebackForm();
		
		form.acquirer = (MstbAcquirer)acquirerLB.getSelectedItem().getValue();
		form.paymentType = (String)paymentTypeLB.getSelectedItem().getValue();
		form.tripDateFrom = tripDateFromDB.getValue();
		form.tripDateTo = tripDateToDB.getValue();
		form.taxiNo = taxiNoTB.getValue();
		form.driverID = driverIdTB.getValue();
		form.jobNo = jobNoTB.getValue();
		form.totalAmount = totalAmtDMB.getValue();
		form.offline = (String)offlineLB.getSelectedItem().getValue();
		form.txnStatus = (String)txnStatusLB.getSelectedItem().getValue();
		
		return form;
	}
	
	public void exportResult() throws InterruptedException, IOException {
		List<PdfExportItem> items = new ArrayList<PdfExportItem>();
		items.add(new PdfExportItem((Grid)this.getFellow("searchFieldGrid"), "Manage Chargeback", null));
		items.add(new PdfExportItem((Listbox)this.getFellow("resultList"), "Chargeback Listing", null));
		
	    ByteArrayOutputStream out = new ByteArrayOutputStream();
	    
	    //try a3? might all 1 line
		ZkPdfExporter exp = new ZkPdfExporter(PageSize.A4.rotate());
		try {
		exp.export(items, out);
	     
	    AMedia amedia = new AMedia("Chargeback_Listing.pdf", "pdf", "application/pdf", out.toByteArray());
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
