package com.cdgtaxi.ibs.billing.ui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.zkoss.zk.ui.Components;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zk.ui.ext.AfterCompose;
import org.zkoss.zul.Button;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;

import com.cdgtaxi.ibs.Uri;
import com.cdgtaxi.ibs.common.NonConfigurableConstants;
import com.cdgtaxi.ibs.common.model.AmtbAccount;
import com.cdgtaxi.ibs.common.model.BmtbNote;
import com.cdgtaxi.ibs.common.ui.CommonWindow;
import com.cdgtaxi.ibs.util.StringUtil;

public class SearchNoteReqWindow extends CommonWindow implements AfterCompose {

	private static Logger logger = Logger.getLogger(SearchNoteReqWindow.class);
	private static final long serialVersionUID = 1L;

	// Autowire components
	private Listbox pendingApprovalNoteLB;
	private Checkbox checkAll;
	private Textbox remarksTB;
	private Button approveBtn, rejectBtn;

	public void afterCompose() {
		Components.wireVariables(this, this);
	}
	
	public void init(){
		// List all pending notes
		try {
			pendingApprovalNoteLB.getItems().clear();
			remarksTB.setRawValue("");
			
			List<BmtbNote> pendingNotes = this.businessHelper.getNoteBusiness().getPendingNotes();
			if (pendingNotes.isEmpty()) {
				Listitem item = new Listitem();
				pendingApprovalNoteLB.appendChild(item);
				Listcell emptyRowCell = new Listcell("No pending notes for approval...");
				emptyRowCell.setSpan(9);
				item.appendChild(emptyRowCell);
			} else {
				for (BmtbNote note : pendingNotes) {
					// creating a new row and append it to rows
					Listitem item = new Listitem();
					pendingApprovalNoteLB.appendChild(item);

					item.setValue(note);

					item.appendChild(newListcell(note.getNoteNo(), StringUtil.GLOBAL_STRING_FORMAT));
					item.appendChild(newListcell(NonConfigurableConstants.NOTE_TYPE.get(note
							.getNoteType())));
					item.appendChild(newListcell(note.getNoteAmount()));

					AmtbAccount account = note.getAmtbAccount();
					while (account.getAmtbAccount() != null)
						account = account.getAmtbAccount();
					
					item.appendChild(newListcell(account.getCustNo(),
							StringUtil.GLOBAL_STRING_FORMAT));

					item.appendChild(newListcell(account.getAccountName()));
					item.appendChild(newListcell(note.getBmtbInvoiceHeader().getInvoiceNo(),
							StringUtil.GLOBAL_STRING_FORMAT));
					item.appendChild(newListcell(NonConfigurableConstants.NOTE_STATUS.get(note
							.getStatus())));
					
					if(note.getStatus().equals(NonConfigurableConstants.NOTE_STATUS_PENDING))
						item.appendChild(newListcell(note.getRemarks()));
					else if(note.getStatus().equals(NonConfigurableConstants.NOTE_STATUS_PENDING_CANCELLATION))
						item.appendChild(newListcell(note.getMstbMasterTableByCancellationReason().getMasterValue()));
					
					Listcell lastCell=new Listcell();
					lastCell.appendChild(new Checkbox());
					item.appendChild(lastCell);
				}
			}
		} catch (Exception e) {
			try {
				Messagebox.show(
						com.cdgtaxi.ibs.common.NonConfigurableConstants.COMMON_ERROR_MESSAGE,
						"Error", Messagebox.OK, Messagebox.ERROR);
			} catch (InterruptedException e1) {
				logger.error(e.getMessage(), e);
			}
			e.printStackTrace();
		}
	}

	public void select() throws InterruptedException {
		logger.info("select()");
		
		Listitem selectedItem = pendingApprovalNoteLB.getSelectedItem();
		if (selectedItem != null && selectedItem.getValue() != null) {
			
			Map<String, String> params = new HashMap<String, String>();
			BmtbNote pendingNote = (BmtbNote) selectedItem.getValue();
			params.put("noteNo", pendingNote.getNoteNo().toString());

			// Forward to view page
			this.forward(Uri.VIEW_NOTE, params);
		}
	}

	private List<BmtbNote> validate() throws InterruptedException{
		//Retrieve
		List<BmtbNote> selectedNotes = new ArrayList<BmtbNote>();
		for (Object pendingNoteItem : pendingApprovalNoteLB.getItems()) {
			Checkbox checkbox = (Checkbox) ((Listitem) pendingNoteItem).getLastChild()
					.getFirstChild();
			if (checkbox!= null && checkbox.isChecked())
				selectedNotes.add(((BmtbNote) ((Listitem) pendingNoteItem).getValue()));
		}
		
		if (selectedNotes.isEmpty()) {
			Messagebox.show("No pending notes are selected!", "Credit/Debit Note Approval",
					Messagebox.OK, Messagebox.EXCLAMATION);
			return new ArrayList<BmtbNote>();
		}
		
		return selectedNotes;
	}
	
	public void approve() throws InterruptedException {
		logger.info("approve()");

		try {
			List<BmtbNote> selectedNotes = this.validate();
			if (!selectedNotes.isEmpty()) {
				String remarks = remarksTB.getValue();

				if (Messagebox.show("Are you sure you want to proceed to approve?",
						"Credit/Debit Note Approval", Messagebox.OK | Messagebox.CANCEL,
						Messagebox.QUESTION) == Messagebox.OK) {

					this.businessHelper.getNoteBusiness().approveNotes(selectedNotes, remarks);

					Messagebox.show("Credit/Debit notes approved!", "Credit/Debit Note Approval",
							Messagebox.OK, Messagebox.INFORMATION);
					// Refresh the listing
					this.init();
				}
			}
		} catch (WrongValueException wve) {
			throw wve;
		} catch (Exception e) {
			Messagebox.show(com.cdgtaxi.ibs.common.NonConfigurableConstants.COMMON_ERROR_MESSAGE,
					"Error", Messagebox.OK, Messagebox.ERROR);
			e.printStackTrace();
		}
	}

	public void reject() throws InterruptedException {
		logger.info("reject()");
		
		try {
			List<BmtbNote> selectedNotes = this.validate();
			if (!selectedNotes.isEmpty()) {
				String remarks = remarksTB.getValue();

				if (Messagebox.show("Are you sure you want to proceed to reject?",
						"Credit/Debit Note Approval", Messagebox.OK | Messagebox.CANCEL,
						Messagebox.QUESTION) == Messagebox.OK) {

					this.businessHelper.getNoteBusiness().rejectNotes(selectedNotes, remarks);

					Messagebox.show("Credit/Debit notes rejected!", "Credit/Debit Note Approval",
							Messagebox.OK, Messagebox.INFORMATION);
					// Refresh the listing
					this.init();
				}
			}
		} catch (WrongValueException wve) {
			throw wve;
		} catch (Exception e) {
			Messagebox.show(com.cdgtaxi.ibs.common.NonConfigurableConstants.COMMON_ERROR_MESSAGE,
					"Error", Messagebox.OK, Messagebox.ERROR);
			e.printStackTrace();
		}
	}

	@Override
	public void refresh() throws InterruptedException {
		init();
	}

	public void checkAll() throws InterruptedException {
		logger.info("");

		try {
			for (Object pendingNote : pendingApprovalNoteLB.getItems()) {
				if (checkAll.isChecked()) {
					Checkbox checkbox = (Checkbox) ((Listitem) pendingNote).getLastChild()
							.getFirstChild();
					if (checkbox != null) {
						if (!checkbox.isChecked()) {
							checkbox.setChecked(true);
						}
					}
				} else {
					Checkbox checkbox = (Checkbox) ((Listitem) pendingNote).getLastChild()
							.getFirstChild();
					if (checkbox != null) {
						if (checkbox.isChecked()) {
							checkbox.setChecked(false);
						}
					}
				}
			}
		} catch (Exception e) {
			Messagebox.show(com.cdgtaxi.ibs.common.NonConfigurableConstants.COMMON_ERROR_MESSAGE,
					"Error", Messagebox.OK, Messagebox.ERROR);
			logger.error(e.getMessage(), e);
		}
	}
}
