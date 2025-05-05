package com.cdgtaxi.ibs.billing.ui;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;
import org.zkoss.zk.ui.event.CreateEvent;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Longbox;
import org.zkoss.zul.Messagebox;

import com.cdgtaxi.ibs.Uri;
import com.cdgtaxi.ibs.common.ConfigurableConstants;
import com.cdgtaxi.ibs.common.NonConfigurableConstants;
import com.cdgtaxi.ibs.common.model.AmtbAccount;
import com.cdgtaxi.ibs.common.model.BmtbNote;
import com.cdgtaxi.ibs.common.model.forms.SearchNoteForm;
import com.cdgtaxi.ibs.util.ComponentUtil;
import com.cdgtaxi.ibs.util.DateUtil;
import com.cdgtaxi.ibs.util.StringUtil;

public class SearchNoteWindow extends SearchByAccountWindow {
	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(SearchNoteWindow.class);

	private Longbox noteNoLB;
	private Longbox issuingInvoiceNoLB;
	private Datebox noteDateFromDB;
	private Datebox noteDateToDB;
	private Listbox noteTypeLB;
	private Listbox noteStatusLB;
	private Listbox resultLB;

	@Override
	public void onCreate(CreateEvent ce) throws Exception{
		super.onCreate(ce);

		noteNoLB = (Longbox) getFellow("noteNoLB");
		issuingInvoiceNoLB = (Longbox) getFellow("issuingInvoiceNoLB");
		noteDateFromDB = (Datebox) getFellow("noteDateFromDB");
		noteDateToDB = (Datebox) getFellow("noteDateToDB");
		noteStatusLB = (Listbox) getFellow("noteStatusLB");
		noteTypeLB = (Listbox) getFellow("noteTypeLB");
		resultLB = (Listbox) getFellow("resultList");
		resultLB.setPageSize(10);

		List<Listitem> noteTypes = ComponentUtil.convertToListitems(NonConfigurableConstants.NOTE_TYPE, true);
		noteTypeLB.appendChild(ComponentUtil.createNotRequiredListItem());
		for(Listitem listItem : noteTypes){
			noteTypeLB.appendChild(listItem);
		}

		List<Listitem> noteStatuses = ComponentUtil.convertToListitems(NonConfigurableConstants.NOTE_STATUS, true);
		noteStatusLB.appendChild(ComponentUtil.createNotRequiredListItem());
		for(Listitem listItem : noteStatuses){
			noteStatusLB.appendChild(listItem);
		}
	}

	public void updateNoteDateTo() {
		if (noteDateToDB.getValue() == null) {
			noteDateToDB.setValue(noteDateFromDB.getValue());
		}
	}

	public void searchNote() throws InterruptedException{
		try {
			SearchNoteForm form = buildSearchForm();
			if (form == null) {
				return;
			}
			if(!form.isAtLeastOneCriteriaSelected()){
				Messagebox.show("Please enter one of the selection criteria",
						"Error", Messagebox.OK, Messagebox.ERROR);
				return;
			}

			displayProcessing();
			List<BmtbNote> notes = businessHelper.getNoteBusiness().searchNote(form);

			resultLB.getItems().clear();
			if (resultLB.getListfoot() != null) {
				resultLB.removeChild(resultLB.getListfoot());
			}

			if (notes.isEmpty()) {
				resultLB.appendChild(ComponentUtil.createNoRecordsFoundListFoot(10));
				return;
			}
			
			if(notes.size()>ConfigurableConstants.getMaxQueryResult())
				Messagebox.show(NonConfigurableConstants.getExceedMaxResultMessage(), "Alert", Messagebox.OK, Messagebox.INFORMATION);
			
			for (final BmtbNote note : notes) {
				Listitem item = new Listitem();
				item.setValue(note);
				item.appendChild(newListcell(note.getNoteNo(), StringUtil.GLOBAL_STRING_FORMAT));
				item.appendChild(newListcell(NonConfigurableConstants.NOTE_TYPE.get(note.getNoteType())));
				// To calculate the note amount
				// Net Note Amount = note amount - discount + GST + admin fee (for trip-based)
				BigDecimal noteAmount = new BigDecimal(0);
				if (NonConfigurableConstants.NOTE_TXN_TYPE_TRIP_BASED.equals(note.getNoteTxnType()))
				{
					// Trips based
					noteAmount = note.getNoteAmount().add(note.getGst()).add(note.getAdminFee()).subtract(note.getProdDis()).subtract(note.getPromoDis());
				}
				else
				{
					// Non-trips based
					noteAmount = note.getNoteAmount().add(note.getGst()).subtract(note.getProdDis());
				}
				item.appendChild(newListcell(noteAmount, StringUtil.GLOBAL_DECIMAL_FORMAT));
				AmtbAccount topLevelAccount =
					businessHelper.getAccountBusiness().getTopLevelAccount(note.getAmtbAccount());
				item.appendChild(newListcell(new Integer(topLevelAccount.getCustNo()), StringUtil.GLOBAL_STRING_FORMAT));
				item.appendChild(newListcell(topLevelAccount.getAccountName()));
				item.appendChild(newListcell(note.getBmtbInvoiceHeader().getInvoiceNo(), StringUtil.GLOBAL_STRING_FORMAT));
				Listcell appliedInvoiceNoListCell = new Listcell();
				appliedInvoiceNoListCell.setLabel("-");
				appliedInvoiceNoListCell.setValue(new Long(0));
				if (note.getBmtbInvoiceTxnByBilledInvoiceTxnNo() != null) {
					Long invoiceNo = note.getBmtbInvoiceTxnByBilledInvoiceTxnNo().getBmtbInvoiceDetail().getBmtbInvoiceSummary().getBmtbInvoiceHeader().getInvoiceNo();
					if(invoiceNo!=null){
						appliedInvoiceNoListCell.setLabel(invoiceNo.toString());
						appliedInvoiceNoListCell.setValue(invoiceNo);
					}
					else {
						appliedInvoiceNoListCell.setLabel("-");
						appliedInvoiceNoListCell.setValue(new Long(0));
					}
				}
				item.appendChild(appliedInvoiceNoListCell);
				item.appendChild(newListcell(note.getCreatedDt(), DateUtil.LAST_UPDATED_DATE_FORMAT));
				item.appendChild(newListcell(NonConfigurableConstants.NOTE_STATUS.get(note.getStatus())));
				resultLB.appendChild(item);
			}
			
			if(notes.size()>ConfigurableConstants.getMaxQueryResult())
				resultLB.removeItemAt(ConfigurableConstants.getMaxQueryResult());
		}
		catch (Exception ex) {
			logger.error(ex.getMessage(), ex);
			Messagebox.show(NonConfigurableConstants.COMMON_ERROR_MESSAGE,
					"Error", Messagebox.OK, Messagebox.ERROR);
		}
	}

	protected SearchNoteForm buildSearchForm() throws InterruptedException {
		SearchNoteForm form = new SearchNoteForm();
		populateAccountForm(form);

		if (noteDateFromDB.getValue() != null
				&& noteDateToDB.getValue() != null
				&& noteDateFromDB.getValue().after(noteDateToDB.getValue())) {
			Messagebox.show("Note Date To shouldn't be earlier than Note Date From",
					"View Credit/Debit Note", Messagebox.OK, Messagebox.INFORMATION);
			return null;
		}

		String noteType = (String) noteTypeLB.getSelectedItem().getValue();
		String noteStatus = (String) noteStatusLB.getSelectedItem().getValue();

		form.setNoteNo(noteNoLB.getValue());
		form.setIssuingInvoiceNo(issuingInvoiceNoLB.getValue());
		form.setNoteDateFrom(noteDateFromDB.getValue());
		form.setNoteDateTo(noteDateToDB.getValue());
		form.setNoteType(noteType);
		form.setNoteStatus(noteStatus);

		return form;
	}

	public void displayNoteDetails()  throws InterruptedException{
		try {
			Listitem selectedItem = resultLB.getSelectedItem();
			BmtbNote note = (BmtbNote) selectedItem.getValue();

			HashMap<String, String> map = new HashMap<String, String>();
			map.put("noteNo", note.getNoteNo().toString());
			forward(Uri.VIEW_NOTE, map);
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

		noteNoLB.setValue(null);
		issuingInvoiceNoLB.setValue(null);
		noteDateFromDB.setValue(null);
		noteDateToDB.setValue(null);
		noteStatusLB.setSelectedIndex(0);
		noteTypeLB.setSelectedIndex(0);
		resultLB.getItems().clear();
	}
}
