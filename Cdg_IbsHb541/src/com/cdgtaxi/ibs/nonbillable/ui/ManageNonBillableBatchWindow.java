package com.cdgtaxi.ibs.nonbillable.ui;

import java.io.ByteArrayOutputStream;
import java.math.BigDecimal;
import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.zkoss.util.media.AMedia;
import org.zkoss.zk.ui.Components;
import org.zkoss.zk.ui.SuspendNotAllowedException;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.ext.AfterCompose;
import org.zkoss.zul.Button;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Filedownload;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Messagebox;

import com.cdgtaxi.ibs.Uri;
import com.cdgtaxi.ibs.common.ConfigurableConstants;
import com.cdgtaxi.ibs.common.NonConfigurableConstants;
import com.cdgtaxi.ibs.common.model.forms.SearchNonBillableBatchForm;
import com.cdgtaxi.ibs.common.ui.CommonWindow;
import com.cdgtaxi.ibs.exporter.PdfExportItem;
import com.cdgtaxi.ibs.exporter.ZkPdfExporter;
import com.cdgtaxi.ibs.master.model.MstbAcquirer;
import com.cdgtaxi.ibs.master.model.MstbMasterTable;
import com.cdgtaxi.ibs.util.ComponentUtil;
import com.cdgtaxi.ibs.util.DateUtil;
import com.cdgtaxi.ibs.web.component.CapsTextbox;
import com.cdgtaxi.ibs.web.component.Constants;
import com.lowagie.text.PageSize;

@SuppressWarnings("serial")
public class ManageNonBillableBatchWindow extends CommonWindow implements AfterCompose {
	private CapsTextbox batchNoTextBox, midTextBox, tidTextBox, jobNoTextBox;
	private Datebox uploadDateFromDateBox, uploadDateToDateBox,
			creditDateFromDateBox, creditDateToDateBox;
	private Listbox acquirerListBox, paymentTypeListBox,
			batchStatusListBox, resultList, completeStatusListBox;
	private Checkbox checkAll;

	public void afterCompose() {
		//wire variables
		Components.wireVariables(this, this);

		List<MstbAcquirer> acquirers = this.businessHelper.getNonBillableBusiness().getAllAcquirers();
		for(MstbAcquirer acquirer : acquirers){
			Listitem item = new Listitem();
			item.setValue(acquirer);
			item.setLabel(acquirer.getName());
			acquirerListBox.appendChild(item);
		}
		acquirerListBox.setSelectedIndex(0);
		this.populatePaymentType((MstbAcquirer)acquirerListBox.getSelectedItem().getValue());

		Set<String> batchStatusKeys = NonConfigurableConstants.NON_BILLABLE_BATCH_STATUS.keySet();
		for(String key : batchStatusKeys){
			Listitem item = new Listitem();
			item.setValue(key);
			item.setLabel(NonConfigurableConstants.NON_BILLABLE_BATCH_STATUS.get(key));
			batchStatusListBox.appendChild(item);
		}
		batchStatusListBox.setSelectedIndex(0);

		completeStatusListBox.appendChild(ComponentUtil.createNotRequiredListItem());
		Set<String> completeStatusKeys = NonConfigurableConstants.AYDEN_COMPLETE_STATUS.keySet();
		for(String key : completeStatusKeys){
			Listitem item = new Listitem();
			item.setValue(key);
			item.setLabel(NonConfigurableConstants.AYDEN_COMPLETE_STATUS.get(key));
			completeStatusListBox.appendChild(item);
		}
		completeStatusListBox.setSelectedIndex(0);

		if(!this.checkUriAccess(Uri.CREATE_BANK_PAYMENT_ADVISE))
			((Button)this.getFellow("createBtn")).setDisabled(true);
	}

	public void populatePaymentType(MstbAcquirer acquirer){
		paymentTypeListBox.getChildren().clear();

		Listitem allItem = new Listitem("All", null);
		paymentTypeListBox.appendChild(allItem);

		List<MstbMasterTable> masters = this.businessHelper.getNonBillableBusiness().getPymtType(acquirer);
		for(MstbMasterTable master : masters){
			Listitem item = new Listitem();

			item.setValue(master.getMasterCode());
			item.setLabel(master.getMasterValue());

			paymentTypeListBox.appendChild(item);
		}
		paymentTypeListBox.setSelectedIndex(0);
	}

	public void reset(){
		batchNoTextBox.setValue("");
		acquirerListBox.setSelectedIndex(0);
		this.populatePaymentType((MstbAcquirer)acquirerListBox.getSelectedItem().getValue());
		batchStatusListBox.setSelectedIndex(0);
		completeStatusListBox.setSelectedIndex(0);
		midTextBox.setValue("");
		tidTextBox.setValue("");

		uploadDateFromDateBox.setText("");
		uploadDateToDateBox.setText("");
		creditDateFromDateBox.setText("");
		creditDateToDateBox.setText("");
		jobNoTextBox.setText("");

		resultList.getItems().clear();
		if(resultList.getListfoot()==null) resultList.appendChild(ComponentUtil.createNoRecordsFoundListFoot(11));
	}

	public void search() throws SuspendNotAllowedException, InterruptedException{
		this.displayProcessing();
		resultList.getItems().clear();

		try{
			if(uploadDateFromDateBox.getValue()!=null && uploadDateToDateBox.getValue()==null)
				uploadDateToDateBox.setValue(uploadDateFromDateBox.getValue());
			else if(uploadDateFromDateBox.getValue()==null && uploadDateToDateBox.getValue()!=null)
				uploadDateFromDateBox.setValue(uploadDateToDateBox.getValue());

			if(creditDateFromDateBox.getValue()!=null && creditDateToDateBox.getValue()==null)
				creditDateToDateBox.setValue(creditDateFromDateBox.getValue());
			else if(creditDateFromDateBox.getValue()==null && creditDateToDateBox.getValue()!=null)
				creditDateFromDateBox.setValue(creditDateToDateBox.getValue());

			if(uploadDateFromDateBox.getValue()!=null && uploadDateToDateBox.getValue()!=null)
				if(uploadDateFromDateBox.getValue().compareTo(uploadDateToDateBox.getValue()) == 1)
					throw new WrongValueException(uploadDateFromDateBox, "Upload Date From cannot be later than Upload Date To.");

			if(creditDateFromDateBox.getValue()!=null && creditDateToDateBox.getValue()!=null)
				if(creditDateFromDateBox.getValue().compareTo(creditDateToDateBox.getValue()) == 1)
					throw new WrongValueException(creditDateFromDateBox, "Credit Date From cannot be later than Credit Date To.");

			SearchNonBillableBatchForm form = this.buildSearchForm();

			if(form.isAtLeastOneCriteriaSelected==false){
				Messagebox.show("Please enter one of the selection criteria",
						"Error", Messagebox.OK, Messagebox.ERROR);
				return;
			}

			List<Object[]> batches = this.businessHelper.getNonBillableBusiness().searchNonBillableBatch(form);
			if(batches.size()>0){

				if(batches.size()>ConfigurableConstants.getMaxQueryResult())
					Messagebox.show(NonConfigurableConstants.getExceedMaxResultMessage(), "Alert", Messagebox.OK, Messagebox.INFORMATION);

				/*
				 * 1. Batch Id
				 * 2. Batch No
				 * 3. Settlement Date
				 * 4. Acquirer Name
				 * 5. TID
				 * 6. TXN COUNT
				 * 7. TXN AMT
				 * 8. CREDIT COUNT
				 * 9. CREDIT AMT
				 * 10.CREDIT DATE
				 * 11.STATUS
				 * 12.COMPLETE STATUS
				 * 13.FILE NAME
				 * 14.UPLOAD DATE
				 */

				for(Object[] batchInfo : batches){
					Listitem item = new Listitem();

					item.setValue(batchInfo[0]);

					Listcell checkBoxCell=new Listcell();
					Checkbox checkBox = new Checkbox();
					if(((String)batchInfo[10]).equals(NonConfigurableConstants.NON_BILLABLE_BATCH_STATUS_CLOSED))
						checkBox.setDisabled(true);
					else{
						checkBox.addEventListener(Events.ON_CHECK, new EventListener() {
							@SuppressWarnings("unused")
							public boolean isAsap() {
								return true;
							}

							public void onEvent(Event event) throws Exception {
								checkAll.setChecked(false);
							}
						});
					}
					checkBoxCell.appendChild(checkBox);
					item.appendChild(checkBoxCell);

					item.appendChild(newListcell((String)batchInfo[1]));
					item.appendChild(newListcell((Date)batchInfo[2]));
					item.appendChild(newListcell((String)batchInfo[3]));
					if((String)batchInfo[4]!=null) item.appendChild(newListcell((String)batchInfo[4]));
					else item.appendChild(newEmptyListcell(null, "-"));
					item.appendChild(newListcell((Long)batchInfo[5]));
					item.appendChild(newListcell((BigDecimal)batchInfo[6]));
					if((Long)batchInfo[7]!=null) item.appendChild(newListcell((Long)batchInfo[7]));
					else item.appendChild(newEmptyListcell(new Long(-1), "-"));
					if((BigDecimal)batchInfo[8]!=null) item.appendChild(newListcell((BigDecimal)batchInfo[8]));
					else item.appendChild(newEmptyListcell(new BigDecimal(-1), "-"));
					if(((Date)batchInfo[9])!=null){
						item.appendChild(newListcell((Date)batchInfo[9]));
					}
					else item.appendChild(newEmptyListcell(DateUtil.getUtilDateForNullComparison(), "-"));
					item.appendChild(newListcell(NonConfigurableConstants.NON_BILLABLE_BATCH_STATUS.get((String)batchInfo[10])));
					item.appendChild(newListcell(NonConfigurableConstants.AYDEN_COMPLETE_STATUS.get((String)batchInfo[11])));
					item.appendChild(newListcell((String)batchInfo[12]));
					item.appendChild(newListcell((String)batchInfo[13]));

					resultList.appendChild(item);
				}

				if(batches.size()>ConfigurableConstants.getMaxQueryResult())
					resultList.removeItemAt(ConfigurableConstants.getMaxQueryResult());

				if(resultList.getListfoot()!=null)
					resultList.removeChild(resultList.getListfoot());
			}
			else{
				if(resultList.getListfoot()==null){
					resultList.appendChild(ComponentUtil.createNoRecordsFoundListFoot(11));
				}
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
		this.displayProcessing();
		if(!this.checkUriAccess(Uri.VIEW_NON_BILLABLE_BATCH)){
			Messagebox.show(com.cdgtaxi.ibs.common.NonConfigurableConstants.ACCESS_DENIED,
					"Access Denied", Messagebox.OK, Messagebox.EXCLAMATION);
			return;
		}

		Map map = new HashMap();
		map.put("batchId", (Long)resultList.getSelectedItem().getValue());
		map.put("mode", ViewNonBillableBatchWindow.MODE_VIEW);
		this.forward(Uri.VIEW_NON_BILLABLE_BATCH, map);
	}

	@SuppressWarnings("unchecked")
	public void createBankPaymentAdvise() throws InterruptedException{
		List<Long> batchIds = new ArrayList<Long>();
		List<Listitem> listItems = resultList.getItems();
		for(Listitem listItem : listItems){
			Checkbox checkbox = (Checkbox)listItem.getFirstChild().getFirstChild();
			if(!checkbox.isDisabled())
				if(checkbox.isChecked()){
					batchIds.add((Long)listItem.getValue());
				}
		}

		if(batchIds.isEmpty()){
			Messagebox.show("At least one batch needs to be selected to proceed.",
					"Error", Messagebox.OK, Messagebox.ERROR);
			return;
		}

		Map map = new HashMap();
		map.put("batchIds", batchIds);
		this.forward(Uri.CREATE_BANK_PAYMENT_ADVISE, map);
	}

	@SuppressWarnings("unchecked")
	public void checkAll() throws InterruptedException{
		try{

			List<Listitem> listItems = resultList.getItems();
			for(Listitem listItem : listItems){

				if(checkAll.isChecked()){
					Checkbox checkbox = (Checkbox)listItem.getFirstChild().getFirstChild();
					if(!checkbox.isDisabled())
						if(!checkbox.isChecked()){
							checkbox.setChecked(true);
						}
				}
				else{
					Checkbox checkbox = (Checkbox)listItem.getFirstChild().getFirstChild();
					if(!checkbox.isDisabled())
						if(checkbox.isChecked())
							checkbox.setChecked(false);
				}
			}
		}
		catch(Exception e){
			Messagebox.show(com.cdgtaxi.ibs.common.NonConfigurableConstants.COMMON_ERROR_MESSAGE,
					"Error", Messagebox.OK, Messagebox.ERROR);
			e.printStackTrace();
		}
	}

	@Override
	public void refresh() throws InterruptedException {
		resultList.clearSelection();
	}

	private SearchNonBillableBatchForm buildSearchForm(){
		SearchNonBillableBatchForm form = new SearchNonBillableBatchForm();

		form.batchNo = batchNoTextBox.getValue();
		if(form.batchNo!=null && form.batchNo.length()>0) form.isAtLeastOneCriteriaSelected = true;
		form.mid = midTextBox.getValue();
		if(form.mid!=null && form.mid.length()>0) form.isAtLeastOneCriteriaSelected = true;
		form.tid = tidTextBox.getValue();
		if(form.tid!=null && form.tid.length()>0) form.isAtLeastOneCriteriaSelected = true;
		form.uploadDateFrom = uploadDateFromDateBox.getValue();
		if(form.uploadDateFrom!=null) form.isAtLeastOneCriteriaSelected = true;
		form.uploadDateTo = uploadDateToDateBox.getValue();
		if(form.uploadDateTo!=null) form.isAtLeastOneCriteriaSelected = true;
		form.creditDateFrom = creditDateFromDateBox.getValue();
		if(form.creditDateFrom!=null) form.isAtLeastOneCriteriaSelected = true;
		form.creditDateTo = creditDateToDateBox.getValue();
		if(form.creditDateTo!=null) form.isAtLeastOneCriteriaSelected = true;
		form.acquirer = (MstbAcquirer)acquirerListBox.getSelectedItem().getValue();
		if(form.acquirer!=null) form.isAtLeastOneCriteriaSelected = true;
		form.paymentType = (String)paymentTypeListBox.getSelectedItem().getValue();
		if(form.paymentType!=null && form.paymentType.length()>0) form.isAtLeastOneCriteriaSelected = true;
		form.batchStatus = (String)batchStatusListBox.getSelectedItem().getValue();
		if(form.batchStatus!=null && form.batchStatus.length()>0) form.isAtLeastOneCriteriaSelected = true;
		form.completeStatus = (String)completeStatusListBox.getSelectedItem().getValue();
		if(form.completeStatus!=null && form.completeStatus.length()>0) form.isAtLeastOneCriteriaSelected = true;
		form.jobNo = jobNoTextBox.getValue();


		return form;
	}

	public void exportResult() {
		List<PdfExportItem> items = new ArrayList<PdfExportItem>();
		items.add(new PdfExportItem((Grid)this.getFellow("searchFieldGrid"), "Search Non Billable Trips", null));
		items.add(new PdfExportItem((Listbox)this.getFellow("resultList"), "Non Billable Trips Listing", new int[]{0}));

		ByteArrayOutputStream out = new ByteArrayOutputStream();

		ZkPdfExporter exp = new ZkPdfExporter(PageSize.A4.rotate());
		try{
			exp.export(items, out);

			AMedia amedia = new AMedia("Non_Billable_Listing.pdf", "pdf", "application/pdf", out.toByteArray());
			Filedownload.save(amedia);

			out.close();
		}catch (Exception e) {
			try {
				Messagebox.show(com.cdgtaxi.ibs.common.NonConfigurableConstants.ZERO_PDF_RESULT_ERROR,
						"Error", Messagebox.OK, Messagebox.ERROR);
			} catch (InterruptedException e1) { e.printStackTrace(); }
		}
	}
}
