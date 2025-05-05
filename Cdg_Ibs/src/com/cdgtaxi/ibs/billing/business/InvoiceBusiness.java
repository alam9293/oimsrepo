package com.cdgtaxi.ibs.billing.business;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.zkoss.zul.Listitem;

import com.cdgtaxi.ibs.common.business.GenericBusiness;
import com.cdgtaxi.ibs.common.exception.FieldException;
import com.cdgtaxi.ibs.common.model.AmtbAccount;
import com.cdgtaxi.ibs.common.model.BmtbDraftInvHeader;
import com.cdgtaxi.ibs.common.model.BmtbInvoiceHeader;
import com.cdgtaxi.ibs.common.model.IttbRecurringReq;
import com.cdgtaxi.ibs.common.model.forms.SearchInvoiceForm;

public interface InvoiceBusiness extends GenericBusiness {
	public List<BmtbInvoiceHeader> searchInvoice(SearchInvoiceForm form);
	public List<BmtbDraftInvHeader> searchDraftInvoice(SearchInvoiceForm form);
	
	public BmtbInvoiceHeader getInvoice(String invoiceNo);
	
	public byte[] getInvoiceFile(String string);
	public byte[] getDraftInvoiceFile(String invoiceNo);

	public List<BmtbInvoiceHeader> searchNoteIssuableInvoice(
			SearchInvoiceForm form);
	public AmtbAccount getAccountWithEntity(AmtbAccount amtbAccount);
	
	public boolean print(Integer billGenRequestNo, Integer custNoFrom, Integer custNoTo,
			Long invoiceNoFrom, Long invoiceNoTo, boolean printNoActivity, Set<Listitem> printers, 
			String printedBy) throws IOException;
	
	public void updateInvoiceHeaderFile( byte[] bytes, BmtbInvoiceHeader header);
	
	public void updateInvoiceHeaderFile(byte[] bytes, BmtbDraftInvHeader header);
	
	public String processRecurringDtl (IttbRecurringReq ittbRecurringReq, int[] stats);
	
	public String downloadAndProcessRecurringDtl (int[] stats);
	
	public void uploadAndProcessRecurringReturnFile(IttbRecurringReq request, Map<String, List<String>> listOfUploadedFiles) throws FieldException, Exception;
}
