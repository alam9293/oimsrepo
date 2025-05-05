package com.cdgtaxi.ibs.nonbillable.business;

import java.sql.Date;
import java.util.List;

import com.cdgtaxi.ibs.common.business.GenericBusiness;
import com.cdgtaxi.ibs.common.model.BmtbBankPayment;
import com.cdgtaxi.ibs.common.model.FmtbBankCode;
import com.cdgtaxi.ibs.common.model.IttbSetlReportingAmex;
import com.cdgtaxi.ibs.common.model.IttbSetlReportingAyden;
import com.cdgtaxi.ibs.common.model.TmtbNonBillableBatch;
import com.cdgtaxi.ibs.common.model.TmtbNonBillableTxn;
import com.cdgtaxi.ibs.common.model.TmtbNonBillableTxnCrca;
import com.cdgtaxi.ibs.common.model.TmtbNonBillableTxnCrcaReq;
import com.cdgtaxi.ibs.common.model.VwBankAdvice;
import com.cdgtaxi.ibs.common.model.VwBankAdviceIBS;
import com.cdgtaxi.ibs.common.model.VwBankAdviseAmex;
import com.cdgtaxi.ibs.common.model.VwBankAdviseAyden;
import com.cdgtaxi.ibs.common.model.forms.BankPaymentInfo;
import com.cdgtaxi.ibs.common.model.forms.SearchBankPaymentAdviseForm;
import com.cdgtaxi.ibs.common.model.forms.SearchChargebackForm;
import com.cdgtaxi.ibs.common.model.forms.SearchNonBillableBatchForm;
import com.cdgtaxi.ibs.common.model.forms.SearchNonBillableTxnForm;
import com.cdgtaxi.ibs.common.model.forms.SearchNonBillableTxnsForm;
import com.cdgtaxi.ibs.master.model.MstbAcquirer;
import com.cdgtaxi.ibs.master.model.MstbAcquirerMdr;
import com.cdgtaxi.ibs.master.model.MstbMasterTable;
import com.cdgtaxi.ibs.report.dto.NonBillableBatchDto;

public interface NonBillableBusiness extends GenericBusiness {
	public List<MstbAcquirer> getAllAcquirers();
	public List<Object[]> searchNonBillableBatch(SearchNonBillableBatchForm form);
	public List<Object[]> searchNonBillableBatch2(SearchNonBillableBatchForm form);
	public List<TmtbNonBillableTxn> searchNonBillableTxn(SearchNonBillableTxnForm form);
	public List<TmtbNonBillableTxn> searchNonBillableTxns(SearchNonBillableTxnsForm form);
	public TmtbNonBillableBatch getNonbillableBatch(Long batchId);
	public TmtbNonBillableBatch getNonbillableBatch(String batchNo, Date settlementDate);
	public List<TmtbNonBillableBatch> getNonbillableBatch(List<Long> batchIds);
	public MstbAcquirerMdr getMDR(MstbAcquirer acquirer);
	public List<FmtbBankCode> getBankInBanksForNonBillable(List<Long> batchIds);
	public Long createBankPaymentAdvise(BankPaymentInfo bankPaymentInfo, String userLoginId);
	public List<BmtbBankPayment> searchBankPaymentAdvise(SearchBankPaymentAdviseForm form);
	public Object[] getRejectedTripsCountAndAmount(Long batchId);
	public List<TmtbNonBillableTxn> searchChargeback(SearchChargebackForm form);
	public void chargeback(TmtbNonBillableTxn txn, String userLoginId) throws Exception;
	public void refund(TmtbNonBillableTxn txn, String userLoginId) throws Exception;
	public List<MstbMasterTable> getPymtType(MstbAcquirer acquirer);
	public boolean isChargebackTxnGLed(TmtbNonBillableTxn txn);
	public List<VwBankAdviseAyden> retrieveAydenSettlementDetailReport();
	public List<VwBankAdviseAmex> retrieveAmexSettlementDetailReport();
	public List<Object[]> processAydenCompletenessCheck(List<NonBillableBatchDto> batches);
	public List<IttbSetlReportingAyden> processAydenSettlement(List<VwBankAdviseAyden> aydenSettlementDetailList,List<IttbSetlReportingAyden> ittbSetlReportingAydenList );
	public List<IttbSetlReportingAmex> processAmexSettlement(List<VwBankAdviseAmex> amexSettlementDetailList, List<IttbSetlReportingAmex> ittbSetlReportingAmexList);
	public List<TmtbNonBillableTxn> retrieveCrca(List<TmtbNonBillableTxn> txns);
	public TmtbNonBillableTxnCrcaReq searchTmtbCrcaRequest();
	public void processTmtbCrcaRequest(TmtbNonBillableTxnCrcaReq req);
	public List<TmtbNonBillableTxn> searchNonBillableTxnsWithMatchingStatues(SearchNonBillableTxnsForm txnsForm);
	public List<Object[]> searchExcessCrca(SearchNonBillableTxnsForm form);
	public List<TmtbNonBillableTxn> searchNonBillablePendingTxnsNotFoundInCrca(SearchNonBillableTxnsForm txnsForm);
	public List<TmtbNonBillableTxn> searchNonBillableChargebackRefundedTxns(SearchNonBillableTxnsForm txnsForm);
	public int updateNonBillableTxnMatchingStatusByHql(String matchingStatus, List<Long> txnIdList);
	
	/* WanTing - 07/01/2021 - CR/1220/028 */
	public List<VwBankAdviceIBS> retrieveCommonSettlementDetailReport();
	public <T> List<T>processCommonSettlement(List<VwBankAdvice> vwBankAdviceList, Class<T> ittbSetlReportingClass) throws IllegalAccessException, InstantiationException;
	public void processTmtbCrcaRequest(TmtbNonBillableTxnCrcaReq req, String crtUpdNm);
	
}
