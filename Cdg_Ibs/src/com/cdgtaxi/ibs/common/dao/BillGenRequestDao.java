package com.cdgtaxi.ibs.common.dao;

import java.sql.Date;
import java.util.List;

import com.cdgtaxi.ibs.common.model.AmtbAccount;
import com.cdgtaxi.ibs.common.model.BmtbBillGenReq;
import com.cdgtaxi.ibs.common.model.BmtbBillGenSetup;
import com.cdgtaxi.ibs.common.model.BmtbInvoiceHeader;

public interface BillGenRequestDao extends GenericDao {
	public boolean checkRequestExist(List<BmtbBillGenSetup> billGenSetups, Date requestDate, Integer entityNo, Integer requestNo);
	public boolean checkRequestExist(AmtbAccount topLevelAccount, Date requestDate) throws Exception;
	public BmtbBillGenReq get(Integer billGenRequestNo);
	public List<BmtbBillGenReq> get(Integer requestNo, String status, Integer setupNo, Date requestDateFrom, Date requestDateTo, Integer entityNo);
	public List<BmtbBillGenReq> get(Integer requestNo, String status, Integer setupNo, Date requestDateFrom, Date requestDateTo, Integer entityNo, String backward);
	
	public List<BmtbInvoiceHeader> getAllBmtbInvoicesByBillGenReqNo(BmtbBillGenReq bmtbBillGenReq);
	public List<BmtbInvoiceHeader> getAllBmtbInvoicesForRecurring(AmtbAccount acct);
	public List<BmtbInvoiceHeader> getAllBmtbInvoicesForRecurringByInvoiceNo(Long invoiceNoFrom, Long invoiceNoTo);
	public List<BmtbInvoiceHeader> getAllBmtbInvoicesForRecurringByCustNo(List<String> acctNo, Date invoiceDate);
//	public List<BmtbInvoiceHeader> getAllBmtbInvoicesForRecurringByAccountNo(List<Integer> acctNo, Date invoiceDateFrom, Date invoiceDateTo);
	public List<BmtbInvoiceHeader> getAllBmtbInvoicesForRecurringByAccountNo(List<Integer> acctNo, Date invoiceDateTo);

}

