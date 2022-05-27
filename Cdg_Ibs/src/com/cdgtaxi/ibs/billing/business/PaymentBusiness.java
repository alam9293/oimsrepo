package com.cdgtaxi.ibs.billing.business;

import java.util.List;
import java.util.Set;

import com.cdgtaxi.ibs.common.business.GenericBusiness;
import com.cdgtaxi.ibs.common.model.AmtbAccount;
import com.cdgtaxi.ibs.common.model.BmtbInvoiceDetail;
import com.cdgtaxi.ibs.common.model.BmtbInvoiceHeader;
import com.cdgtaxi.ibs.common.model.BmtbPaymentReceipt;
import com.cdgtaxi.ibs.common.model.FmtbBankCode;
import com.cdgtaxi.ibs.common.model.forms.InvoicePaymentDetail;
import com.cdgtaxi.ibs.common.model.forms.PaymentInfo;
import com.cdgtaxi.ibs.common.model.forms.SearchPaymentReceiptForm;

public interface PaymentBusiness extends GenericBusiness {
	public List<AmtbAccount> searchBillableAccount(String customerNo, String name);

	public List<AmtbAccount> searchBillableAccountByParentAccount(AmtbAccount parentAccount);

	public List<AmtbAccount> searchBillableAccountByGrandParentAccount(AmtbAccount grandParentAccount);

	public List<InvoicePaymentDetail> searchOutstandingInvoice(AmtbAccount account);

	public List<InvoicePaymentDetail> searchNegativeInvoice(AmtbAccount account);

	public Long createPaymentReceipt(PaymentInfo paymentInfo,
			List<InvoicePaymentDetail> invoicePaymentDetails, String createdBy) throws Exception;

	public void rewardEarlyPayment(AmtbAccount account, String updatedBy,
			List<InvoicePaymentDetail> invoicePaymentDetails);

	public Long createContraReceipt(PaymentInfo paymentInfo,
			List<InvoicePaymentDetail> invoicePaymentDetails, String createdBy) throws Exception;

	public BmtbPaymentReceipt searchPaymentReceipt(Long receiptNo);

	public void applyPaymentReceipt(BmtbPaymentReceipt paymentReceipt,
			List<InvoicePaymentDetail> invoicePaymentDetails, String updatedBy) throws Exception;

	public List<BmtbPaymentReceipt> searchPaymentReceipt(SearchPaymentReceiptForm form);

	public void cancelPaymentReceipt(BmtbPaymentReceipt paymentReceipt, String updatedBy) throws Exception;

	public List<BmtbPaymentReceipt> searchPaymentReceiptsByInvoice(Long invoiceNo);

	public Set<FmtbBankCode> searchBankInBanks(AmtbAccount account);

	public String getCustomerNo(Integer accountNo);

	public Long generateMemoRefundForExcessAmount(BmtbPaymentReceipt paymentReceipt, String userId);

	// added 12/12/2011
	public BmtbInvoiceHeader getInvoiceByInvoiceNo(Long invoiceNo);
	
	public void generateRecurringReceipt(BmtbInvoiceHeader ih, BmtbInvoiceDetail invoiceDetail, String amount, AmtbAccount account);
} 
