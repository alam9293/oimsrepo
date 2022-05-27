package com.cdgtaxi.ibs.common.dao;

import java.util.List;

import com.cdgtaxi.ibs.common.model.BmtbPaymentReceipt;
import com.cdgtaxi.ibs.common.model.forms.SearchPaymentReceiptForm;

public interface PaymentReceiptDao extends GenericDao {
	public BmtbPaymentReceipt getPaymentReceipt(Long receiptNo);
	public List<BmtbPaymentReceipt> getPaymentReceipt(SearchPaymentReceiptForm form);
	/**
	 * updates the receipt to charge parent account
	 * @param accountNo
	 * @param userId
	 */
	public void updateReceiptToParent(Integer accountNo, String userId);

	public List<BmtbPaymentReceipt> getPaymentReceiptsByInvoice(Long invoiceNo);
}
