package com.cdgtaxi.ibs.common.dao;

import com.cdgtaxi.ibs.common.model.*;
import com.cdgtaxi.ibs.common.model.forms.*;
import com.cdgtaxi.ibs.master.model.MstbAcquirer;
import com.cdgtaxi.ibs.master.model.MstbAcquirerMdr;
import com.cdgtaxi.ibs.master.model.MstbMasterTable;
import com.cdgtaxi.ibs.report.dto.NonBillableBatchDto;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public interface NonBillableDao extends GenericDao {
	public List<MstbAcquirer> getAllAcquirers();
	public List<Object[]> searchNonBillableBatch(SearchNonBillableBatchForm form);
	public List<Object[]> searchNonBillableBatch2(SearchNonBillableBatchForm form);
	public List<TmtbNonBillableTxn> searchNonBillableTxn(SearchNonBillableTxnForm form);
	public List<TmtbNonBillableTxn> searchNonBillableTxns(SearchNonBillableTxnsForm form);
	public List<TmtbNonBillableTxn> searchNonBillableTxnsWithoutCRCA(SearchNonBillableTxnsForm form);
	public TmtbNonBillableBatch getNonbillableBatch(Long batchId);
	public List<TmtbNonBillableBatch> getNonbillableBatch(List<Long> batchIds);
	public TmtbNonBillableBatch getNonbillableBatch(String batchNo, Date settlementDate);
	public MstbAcquirerMdr getMDR(MstbAcquirer acquirer);
	public List<FmtbBankCode> getBankInBanksForNonBillable(List<Long> batchIds);
	public List<TmtbNonBillableTxn> getNonBillableTxn(Collection<Long> txnNos);
	public List<BmtbBankPayment> searchBankPaymentAdvise(SearchBankPaymentAdviseForm form);
	public void closeAllNonBillableTxn(Long batchId, String userLoginId);
	public Object[] getRejectedTripsCountAndAmount(Long batchId);
	public List<TmtbNonBillableTxn> searchChargeback(SearchChargebackForm form);
	public List<MstbMasterTable> getPymtType(MstbAcquirer acquirer);
	public boolean isChargebackTxnGLed(TmtbNonBillableTxn txn);
	public List<VwBankAdviseAyden> retrieveAydenSettlementDetailReport();
	public List<VwBankAdviseAmex> retrieveAmexSettlementDetailReport();
	public void processAydenSettlement(List<TmtbNonBillableTxnCrca> crcaList, List<IttbSetlReportingAyden> ittbSetlReportingAydenList, List<VwBankAdviseAyden> aydenSettlementDetailList);
	public void processAmexSettlement(List<TmtbNonBillableTxnCrca> crcaList, List<IttbSetlReportingAmex> settlList, List<VwBankAdviseAmex> amexSettlementDetailList);
	public List<TmtbNonBillableTxnCrca> retrieveCrca(String pspRefNo);
	public List<Object[]> processAydenCompletenessCheck(List<NonBillableBatchDto> batches);
	public TmtbNonBillableTxnCrcaReq searchTmtbCrcaRequest();
	public List<TmtbNonBillableTxn> searchNonBillableTxnsWithMatchingStatues(SearchNonBillableTxnsForm form);
	public List<Object[]> searchExcessCrca(SearchNonBillableTxnsForm form);
	public List<TmtbNonBillableTxn> searchNonBillablePendingTxnsNotFoundInCrca(SearchNonBillableTxnsForm form);
	public List<TmtbNonBillableTxn> searchNonBillableChargebackRefundedTxns(SearchNonBillableTxnsForm txnsForm);
	public int updateNonBillableTxnMatchingStatusByHql(String matchingStatus, List<Long> txnNoList);
	
	/* WanTing - 24/12/2020 - CR/1220/028 */
	public List<VwBankAdviceIBS> retrieveCommonSettlementDetailReport();
	public <T> void processCommonSettlement(List<TmtbNonBillableTxnCrca> subList, List<T> object, List<VwBankAdvice> subList2);
		
}
