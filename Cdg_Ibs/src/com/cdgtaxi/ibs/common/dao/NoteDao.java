package com.cdgtaxi.ibs.common.dao;

import java.util.List;

import com.cdgtaxi.ibs.common.model.AmtbAccount;
import com.cdgtaxi.ibs.common.model.BmtbNote;
import com.cdgtaxi.ibs.common.model.forms.SearchNoteForm;

public interface NoteDao  extends GenericDao{
	public String getGst();

	public List<AmtbAccount> getAccountNoAndName (String accNo,String name);

	public List searchNote(String accountNo, String noteType);

	public List searchNote(Long noteNo, String accountNo);

	public List<BmtbNote> getNotes(Long invoiceHeaderNo);

	public List<BmtbNote> searchNote(SearchNoteForm form);

	public BmtbNote getNote(Long noteNo);
	
	public BmtbNote getNoteByAcquireTxn(int tmtbAcquireTxnNo);
	
	public List<BmtbNote> getPendingNotes();
}
