package com.cdgtaxi.ibs.common.dao;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.cdgtaxi.ibs.common.model.AmtbAccount;
import com.cdgtaxi.ibs.common.model.BmtbDraftInvHeader;
import com.cdgtaxi.ibs.common.model.BmtbInvoiceHeader;
import com.cdgtaxi.ibs.common.model.forms.SearchInvoiceForm;

public interface InvoiceDao extends GenericDao {
	public boolean checkGLDepositTxnCodeAvailable(Integer entityNo);

	public BmtbInvoiceHeader generateDepositInvoice(BigDecimal depositAmt, Date invoiceDate,
			AmtbAccount acct, String username);

	public BigDecimal getTotalNegativeDetailAmount(BmtbInvoiceHeader invoiceHeader);

	// These are one set of methods
	public List<BmtbInvoiceHeader> getOutstandingInvoiceByAccount(AmtbAccount account);

	public List<BmtbInvoiceHeader> getOutstandingInvoiceByParentAccount(AmtbAccount parentAccount);

	public List<BmtbInvoiceHeader> getOutstandingInvoiceByGrandParentAccount(AmtbAccount grandParentAccount);

	// These are one set of methods
	public List<BmtbInvoiceHeader> getNegativeInvoiceByAccount(AmtbAccount account);

	public List<BmtbInvoiceHeader> getNegativeInvoiceByParentAccount(AmtbAccount parentAccount);

	public List<BmtbInvoiceHeader> getNegativeInvoiceByGrandParentAccount(AmtbAccount grandParentAccount);

	/**
	 * To retrieve total outstanding amount under a corporate or personal account.
	 * Outstanding amount refers to invoices outstanding amount and only counted in
	 * when invoices are overdue.
	 * 
	 * @param accountNo
	 *            the invoices that belongs to this top level account
	 * @param paidInvoiceDueDate
	 * @return total outstanding amount otherwise null
	 */
	public BigDecimal getTotalOutstandingAmount(Integer accountNo, java.sql.Date paidInvoiceDueDate);

	/**
	 * To retrieve total outstanding amount under a corporate or personal account
	 * regardless of invoice due date.
	 * 
	 * @param accountNo
	 *            the invoices that belongs to this top level account
	 * @return total outstanding amount otherwise null
	 */
	public BigDecimal getTotalOutstandingAmount(Integer accountNo);

	/**
	 * To get all invoices entitled for early payment
	 * 
	 * @param accountNo
	 */
	public List<Long> getInvoicesToRewardEarlyPayment(Integer accountNo);

	/**
	 * To retrieve invoices that are awarded early payment and remove that award.
	 * One of the reasons for doing so is cancellation of payment receipt.
	 * 
	 * @param accountNo
	 * @param cancelInvoiceDueDate
	 * @return
	 */
	public List<Long> getNotBilledEarlyPaymentInvoices(Integer accountNo, java.sql.Date cancelInvoiceDueDate);

	/**
	 * to update all invoice that is tied to the account to debt to the next charge to parent account
	 * 
	 * @param accountNo
	 * @param userId
	 */
	public void debtToBilliableParent(Integer accountNo, String userId);

	/**
	 * to check whether an invoice exist with given account no and bill gen request no
	 * 
	 * @param accountNo
	 * @param billGenRequestNo
	 * @return
	 */
	public boolean checkInvoiceExist(Integer accountNo, Integer billGenRequestNo);

	public List<BmtbInvoiceHeader> searchInvoice(SearchInvoiceForm form);

	public List<BmtbDraftInvHeader> searchDraftInvoice(SearchInvoiceForm form);

	public BmtbInvoiceHeader getInvoice(String invoiceNo);

	public BmtbDraftInvHeader getDraftInvoice(String invoiceNo);

	public byte[] getInvoiceFile(String invoiceNo);

	public byte[] getDraftInvoiceFile(String invoiceNo);

	public BigDecimal getLess30DaysTotalOutstandingAmount(AmtbAccount debtToAccount, Date invoiceDate);

	public BigDecimal getLess60DaysTotalOutstandingAmount(AmtbAccount debtToAccount, Date invoiceDate);

	public BigDecimal getLess90DaysTotalOutstandingAmount(AmtbAccount debtToAccount, Date invoiceDate);

	public BigDecimal getMore90DaysTotalOutstandingAmount(AmtbAccount debtToAccount, Date invoiceDate);

	public BigDecimal getTransactedAmountByType(String invoiceNo, String txnType);

	public List<BmtbInvoiceHeader> searchNoteIssuableInvoice(SearchInvoiceForm form);

	public boolean isInvoiceStillOutstanding(Long invoiceNo);

	public List<BmtbInvoiceHeader> getInvoicesForPrinting(Integer billGenRequestNo, Integer custNoFrom,
			Integer custNoTo, Long invoiceNoFrom, Long invoiceNoTo, boolean printNoActivity);

	public BmtbInvoiceHeader getInvoiceByInvoiceNo(Long invoiceNo);
} 