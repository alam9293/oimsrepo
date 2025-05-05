package com.cdgtaxi.ibs.billing.business;

import java.util.List;

import com.cdgtaxi.ibs.common.business.GenericBusiness;
import com.cdgtaxi.ibs.common.exception.ExcessiveNoteAmountException;
import com.cdgtaxi.ibs.common.model.AmtbAccount;
import com.cdgtaxi.ibs.common.model.BmtbNote;
import com.cdgtaxi.ibs.common.model.forms.SearchNoteForm;

@SuppressWarnings("rawtypes")
public interface NoteBusiness extends GenericBusiness {
	public Long issueNote(BmtbNote note) throws ExcessiveNoteAmountException;

	public List searchNote(String accountNo, String noteType);

	public List searchNote(Long noteNo, String accountNo);

	public void cancelNote(BmtbNote note);

	public AmtbAccount getAccount(String accountNo);

	public List<BmtbNote> getNotes(Long invoiceHeaderNo);

	public List<BmtbNote> searchNote(SearchNoteForm form);

	public BmtbNote getNote(Long noteNo);
	
	public List<BmtbNote> getPendingNotes();

	public void approveNotes(List<BmtbNote> notes, String remarks);

	public void rejectNotes(List<BmtbNote> notes, String remarks);
}
