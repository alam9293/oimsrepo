package com.cdgtaxi.ibs.common.dao;

import java.math.BigDecimal;
import java.util.List;

import com.cdgtaxi.ibs.common.model.BmtbInvoiceHeader;
import com.cdgtaxi.ibs.common.model.BmtbInvoiceTxn;
import com.cdgtaxi.ibs.common.model.IttbCpGuestProduct;
import com.cdgtaxi.ibs.common.model.IttbFmsDrvrRfndColReq;
import com.cdgtaxi.ibs.common.model.IttbTripsTxn;
import com.cdgtaxi.ibs.common.model.IttbTripsTxnReq;
import com.cdgtaxi.ibs.common.model.TmtbAcquireTxn;
import com.cdgtaxi.ibs.common.model.TmtbTxnReviewReq;
import com.cdgtaxi.ibs.common.model.TmtbTxnReviewReqFlow;
import com.cdgtaxi.ibs.common.model.VwIntfSetlForIb;
import com.cdgtaxi.ibs.common.model.VwIntfTripsForIb;
import com.cdgtaxi.ibs.txn.ui.TxnSearchCriteria;

public interface TxnDao extends GenericDao {
	public boolean createTxn(TmtbAcquireTxn newTxn, String user);
	public BigDecimal getGstByTripDate(java.util.Date tripDt);
	public BmtbInvoiceHeader getInvoiceNo(int txnID);
	public BmtbInvoiceTxn getInvoiceTxn(int txnID);
	public List<TmtbAcquireTxn> getTxns(TxnSearchCriteria txnSearchCriteria);
	public TmtbAcquireTxn getTxn(String txnID);
	public boolean updateTxn(TmtbAcquireTxn newTxn, String user);
	public boolean updateTxn(TmtbAcquireTxn newTxn);
	public List<TmtbTxnReviewReq> getTxnReqs();
	public List<TmtbTxnReviewReq> getTxnReqs(String txnID);
	public List<TmtbTxnReviewReq> getTxnApprovedReqs(String txnID);
	public TmtbTxnReviewReq getTxnReq(String txnReqID);
	public List<IttbFmsDrvrRfndColReq> getPendingFMSReq(String jobNo, String nric, String taxiNo, String paymentMode);
	public TmtbAcquireTxn getTxnByDraftNo(String salesDraftNo);
	public boolean hasTxn(String jobNo);
	public TmtbAcquireTxn getLatestTxnByJobNo(String jobNo);
	public String getMappedCustNo(String oldCustNo);
	public List<VwIntfTripsForIb> getTRIPSview(int records);
	public List<IttbTripsTxn> getTxnView(int records);
	public List<IttbTripsTxnReq> getTripsReqs();
	public void updateFailed(int[] failedList, String[] errorCodes) throws Exception;
	public void updateSucceeded(int[] succeededList) throws Exception;
	public void started(int retrievalPK) throws Exception;
	public void ended(int retrievalPK) throws Exception;
	public IttbCpGuestProduct getIttbCpGuestProduct(Long productNo);
	public List<VwIntfSetlForIb> getSETLSview(int records);
	public void updateSucceededForSETL(BigDecimal[] succeededList) throws Exception;
	public boolean hasActiveOrBilledTripByJobNo(String jobNo);
	public List<TmtbTxnReviewReqFlow> getTxnReqFlows(String txnReqID);
	public List<TmtbTxnReviewReq> getRemark(String jobNo, Integer txnNo, String txnType);
	public List<Object[]> getPreviousApproval(String jobNo);
	public boolean checkJobNo(String jobNo);
}