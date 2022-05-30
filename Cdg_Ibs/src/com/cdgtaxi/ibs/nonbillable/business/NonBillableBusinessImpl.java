package com.cdgtaxi.ibs.nonbillable.business;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

import com.cdgtaxi.ibs.common.model.*;
import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;

import com.cdgtaxi.ibs.common.NonConfigurableConstants;
import com.cdgtaxi.ibs.common.business.GenericBusinessImpl;
import com.cdgtaxi.ibs.common.model.forms.BankPaymentInfo;
import com.cdgtaxi.ibs.common.model.forms.BankPaymentInfo.BatchInfo;
import com.cdgtaxi.ibs.common.model.forms.SearchBankPaymentAdviseForm;
import com.cdgtaxi.ibs.common.model.forms.SearchChargebackForm;
import com.cdgtaxi.ibs.common.model.forms.SearchNonBillableBatchForm;
import com.cdgtaxi.ibs.common.model.forms.SearchNonBillableTxnForm;
import com.cdgtaxi.ibs.common.model.forms.SearchNonBillableTxnsForm;
import com.cdgtaxi.ibs.interfaces.ayden.AydenSettlementWindow;
import com.cdgtaxi.ibs.master.model.MstbAcquirer;
import com.cdgtaxi.ibs.master.model.MstbAcquirerMdr;
import com.cdgtaxi.ibs.master.model.MstbMasterTable;
import com.cdgtaxi.ibs.report.dto.NonBillableBatchDto;
import com.cdgtaxi.ibs.util.DateUtil;

public class NonBillableBusinessImpl extends GenericBusinessImpl implements NonBillableBusiness {

	private static Logger logger = Logger.getLogger(AydenSettlementWindow.class);

	public List<MstbAcquirer> getAllAcquirers(){
		return this.daoHelper.getNonBillableDao().getAllAcquirers();
	}

	public List<Object[]> searchNonBillableBatch(SearchNonBillableBatchForm form){
		return this.daoHelper.getNonBillableDao().searchNonBillableBatch(form);
	}

	public List<Object[]> searchNonBillableBatch2(SearchNonBillableBatchForm form){
		return this.daoHelper.getNonBillableDao().searchNonBillableBatch2(form);
	}

	public List<TmtbNonBillableTxn> searchNonBillableTxn(SearchNonBillableTxnForm form){
		return this.daoHelper.getNonBillableDao().searchNonBillableTxn(form);
	}

	public List<TmtbNonBillableTxn> searchNonBillableTxns (SearchNonBillableTxnsForm form){
		return this.daoHelper.getNonBillableDao().searchNonBillableTxns(form);
	}

	public TmtbNonBillableBatch getNonbillableBatch(Long batchId){
		return this.daoHelper.getNonBillableDao().getNonbillableBatch(batchId);
	}

	@Override
	public TmtbNonBillableBatch getNonbillableBatch(String batchNo, Date settlementDate) {
		return this.daoHelper.getNonBillableDao().getNonbillableBatch(batchNo,settlementDate);
	}

	public List<TmtbNonBillableBatch> getNonbillableBatch(List<Long> batchIds){
		return this.daoHelper.getNonBillableDao().getNonbillableBatch(batchIds);
	}

	public MstbAcquirerMdr getMDR(MstbAcquirer acquirer){
		return this.daoHelper.getNonBillableDao().getMDR(acquirer);
	}

	public List<FmtbBankCode> getBankInBanksForNonBillable(List<Long> batchIds){
		return this.daoHelper.getNonBillableDao().getBankInBanksForNonBillable(batchIds);
	}

	public Long createBankPaymentAdvise(BankPaymentInfo bankPaymentInfo, String userLoginId) {
		//1. Create Bank Payment
		BmtbBankPayment bankPayment = new BmtbBankPayment();
		bankPayment.setTxnRefNo(bankPaymentInfo.txnRefNo);
		bankPayment.setCreditDate(bankPaymentInfo.creditDate);
		bankPayment.setCollectionAmount(bankPaymentInfo.collectionAmount);
		bankPayment.setTotalTxnAmount(bankPaymentInfo.totalTripsAmount);
		bankPayment.setRejectedAmount(bankPaymentInfo.rejectedAmount);
		bankPayment.setMdrPercentage(bankPaymentInfo.mdrPercentage);
		bankPayment.setMdrValue(bankPaymentInfo.mdrAmount);
		bankPayment.setMdrAdjustment(bankPaymentInfo.mdrAdjustment);
		bankPayment.setChargebackAmount(bankPaymentInfo.chargebackAmount);
		bankPayment.setChargebackReverseAmt(bankPaymentInfo.chargebackReverse);
		bankPayment.setRefundAmt(bankPaymentInfo.refund);
		bankPayment.setRefundReverseAmt(bankPaymentInfo.refundReverse);
		bankPayment.setMarkup(bankPaymentInfo.markup);
		bankPayment.setCommission(bankPaymentInfo.commission);
		bankPayment.setInterchange(bankPaymentInfo.interchange);
		bankPayment.setSchemeFee(bankPaymentInfo.schemeFee);
		bankPayment.setOtherCredit(bankPaymentInfo.otherCredit);
		bankPayment.setOtherDebit(bankPaymentInfo.otherDebit);
		bankPayment.setRemarks(bankPaymentInfo.remarks);
		bankPayment.setFmtbBankCode((FmtbBankCode) this.get(FmtbBankCode.class, bankPaymentInfo.bankInCode));

		Long receiptNo = (Long) this.save(bankPayment, userLoginId);

		//2. Create Bank Payment Detail
		for(BatchInfo batchInfo : bankPaymentInfo.batchList){
			BmtbBankPaymentDetail bankPaymentDetail = new BmtbBankPaymentDetail();
			bankPaymentDetail.setBmtbBankPayment(bankPayment);
			bankPaymentDetail.setTmtbNonBillableBatch(batchInfo.batch);
			this.save(bankPaymentDetail, userLoginId);

			//3. Update Batch Status
			TmtbNonBillableBatch batch = batchInfo.batch;
			batch.setCreditCount(batch.getTxnCount() - batchInfo.rejectedCount);
			batch.setCreditAmt(batch.getTxnAmt().subtract(batchInfo.rejectedAmount));
			batch.setStatus(NonConfigurableConstants.NON_BILLABLE_BATCH_STATUS_CLOSED);
			this.update(batch, userLoginId);

			//4. Batch Update All Txn to Close Status
			//To make things simpler, I never exclude those rejected ones.
			//Instead a double update to override as rejected
			this.daoHelper.getNonBillableDao().closeAllNonBillableTxn(batch.getBatchId(), userLoginId);

			//5. Update trip status
			if(batchInfo.rejectedTrips.size()>0){
				List<TmtbNonBillableTxn> txns = this.daoHelper.getNonBillableDao().getNonBillableTxn(batchInfo.rejectedTrips.keySet());
				for(TmtbNonBillableTxn txn : txns){
					txn.setStatus(NonConfigurableConstants.NON_BILLABLE_TXN_REJECTED);
					txn.setRemarks((String)batchInfo.rejectedTrips.get(txn.getTxnId())[1]);
					this.update(txn, userLoginId);
				}
			}
		}

		return receiptNo;
	}

	public List<BmtbBankPayment> searchBankPaymentAdvise(SearchBankPaymentAdviseForm form){
		return this.daoHelper.getNonBillableDao().searchBankPaymentAdvise(form);
	}

	public Object[] getRejectedTripsCountAndAmount(Long batchId){
		return this.daoHelper.getNonBillableDao().getRejectedTripsCountAndAmount(batchId);
	}

	public List<TmtbNonBillableTxn> searchChargeback(SearchChargebackForm form){
		return this.daoHelper.getNonBillableDao().searchChargeback(form);
	}

	public void chargeback(TmtbNonBillableTxn txn, String userLoginId) throws Exception{

		txn.setStatus(NonConfigurableConstants.NON_BILLABLE_TXN_CHARGEBACK);
		this.update(txn, userLoginId);

		// Send to FMS
		if (NonConfigurableConstants.BOOLEAN_YES.equals(txn.getFmsFlag()))
			this.updateFMSReq(txn);
	}

	public void refund(TmtbNonBillableTxn txn, String userLoginId) throws Exception{

		txn.setStatus(NonConfigurableConstants.TRANSACTION_REQUEST_STATUS_PENDING_REFUND);
		this.update(txn, userLoginId);

		// Send to FMS
		if (NonConfigurableConstants.BOOLEAN_YES.equals(txn.getFmsFlag()))
			this.updateFMSReq(txn);
	}

	private void updateFMSReq(TmtbNonBillableTxn txn) throws Exception
	{
		try
		{
			MstbMasterTable aptMaster = txn.getMstbAcquirerPymtType().getMstbMasterTable();
			// To set the uniqueness to 4 fields instead of only job no.
			List<IttbFmsDrvrRfndColReq> fmsDrvrRfndColReqList =
					this.daoHelper.getTxnDao().getPendingFMSReq(txn.getJobNo(), txn.getNric(), txn.getTaxiNo(), aptMaster.getInterfaceMappingValue());

			if (fmsDrvrRfndColReqList == null)
				// no such request. create new request
				this.save(this.createNewFMSRequest(txn));
			else
			{
				// Iterate the request to add
				// then delete all the pending requests
				Iterator<IttbFmsDrvrRfndColReq> iter = fmsDrvrRfndColReqList.iterator();

				BigDecimal totalFmsAmount = BigDecimal.ZERO;
				BigDecimal totalIncentiveAmount = BigDecimal.ZERO;
				BigDecimal totalPromoAmt = BigDecimal.ZERO;
				BigDecimal totalCabRewardsAmt = BigDecimal.ZERO;

				if (NonConfigurableConstants.FMS_COLLECT.equals(txn.getUpdateFms())){
					totalFmsAmount = txn.getFmsAmt().negate();
					if (txn.getIncentiveAmt() != null) {
						totalIncentiveAmount = txn.getIncentiveAmt().negate();
					}
					if(txn.getPromoAmt() != null) {
						totalPromoAmt = txn.getPromoAmt().negate();
					}
					if(txn.getCabRewardsAmt() != null) {
						totalCabRewardsAmt = txn.getCabRewardsAmt().negate();
					}
				}
				else {
					totalFmsAmount = txn.getFmsAmt();
					if (txn.getIncentiveAmt() != null) {
						totalIncentiveAmount = txn.getIncentiveAmt();
					}
					if(txn.getPromoAmt() != null) {
						totalPromoAmt = txn.getPromoAmt();
					}
					if(txn.getCabRewardsAmt() != null) {
						totalCabRewardsAmt = txn.getCabRewardsAmt();
					}
				}
				BigDecimal totalLevyAmount = BigDecimal.ZERO;
				totalLevyAmount = txn.getLevy();

				while (iter.hasNext())
				{
					IttbFmsDrvrRfndColReq temp = iter.next();
					totalLevyAmount = totalLevyAmount.add(temp.getLevy());

					totalFmsAmount = totalFmsAmount.add(temp.getRefundAmt());
					if (temp.getIncentiveAmt() != null) {
						totalIncentiveAmount = totalIncentiveAmount.add(temp.getIncentiveAmt());
					}
					if(temp.getPromoAmt() != null ) {
						totalPromoAmt = totalPromoAmt.add(temp.getPromoAmt());
					}
					if(temp.getCabRewardsAmt() != null ) {
						totalCabRewardsAmt = totalCabRewardsAmt.add(temp.getCabRewardsAmt());
					}
					// Delete the records
					this.delete(temp);
				}

				//After merging the values, create single new request
				IttbFmsDrvrRfndColReq newRequest = this.createNewFMSRequest(txn);
				newRequest.setRefundAmt(totalFmsAmount);
				newRequest.setIncentiveAmt(totalIncentiveAmount);
				newRequest.setPromoAmt(totalPromoAmt);
				newRequest.setCabRewardsAmt(totalCabRewardsAmt);
				newRequest.setLevy(totalLevyAmount);

				this.save(newRequest);
			}

		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw new Exception("Update FMS Exception");
		}
	}

	private IttbFmsDrvrRfndColReq createNewFMSRequest(TmtbNonBillableTxn txn){
		IttbFmsDrvrRfndColReq fmsDrvrRfndColReq = new IttbFmsDrvrRfndColReq();

		fmsDrvrRfndColReq.setAccountName(null);
		if (txn.getDestination() != null && !"".equals(txn.getDestination()))
			fmsDrvrRfndColReq.setDestination(txn.getDestination());
		else
			fmsDrvrRfndColReq.setDestination(NonConfigurableConstants.NA_FLAG);
		fmsDrvrRfndColReq.setEntityCode(txn.getMstbMasterTableByServiceProvider().getInterfaceMappingValue());
		fmsDrvrRfndColReq.setJobNo(txn.getJobNo());
		fmsDrvrRfndColReq.setLevy(txn.getLevy().setScale(NonConfigurableConstants.NO_OF_DECIMAL, BigDecimal.ROUND_HALF_UP));
		fmsDrvrRfndColReq.setNric(txn.getNric());
		if (NonConfigurableConstants.FMS_COLLECT.equals(txn.getUpdateFms())) {
			fmsDrvrRfndColReq.setRefundAmt(txn.getFmsAmt().setScale(NonConfigurableConstants.NO_OF_DECIMAL, BigDecimal.ROUND_HALF_UP).negate());
			if (txn.getIncentiveAmt() != null) {
				fmsDrvrRfndColReq.setIncentiveAmt(txn.getIncentiveAmt().setScale(NonConfigurableConstants.NO_OF_DECIMAL, BigDecimal.ROUND_HALF_UP).negate());
			}
			else {
				fmsDrvrRfndColReq.setIncentiveAmt(BigDecimal.ZERO);
			}
			if (txn.getPromoAmt() != null) {
				fmsDrvrRfndColReq.setPromoAmt(txn.getPromoAmt().setScale(NonConfigurableConstants.NO_OF_DECIMAL, BigDecimal.ROUND_HALF_UP).negate());
			}
			else {
				fmsDrvrRfndColReq.setPromoAmt(BigDecimal.ZERO);
			}
			if (txn.getCabRewardsAmt() != null) {
				fmsDrvrRfndColReq.setCabRewardsAmt(txn.getCabRewardsAmt().setScale(NonConfigurableConstants.NO_OF_DECIMAL, BigDecimal.ROUND_HALF_UP).negate());
			}
			else {
				fmsDrvrRfndColReq.setCabRewardsAmt(BigDecimal.ZERO);
			}
		}
		else {
			fmsDrvrRfndColReq.setRefundAmt(txn.getFmsAmt().setScale(NonConfigurableConstants.NO_OF_DECIMAL, BigDecimal.ROUND_HALF_UP));
			if (txn.getIncentiveAmt() != null) {
				fmsDrvrRfndColReq.setIncentiveAmt(txn.getIncentiveAmt().setScale(NonConfigurableConstants.NO_OF_DECIMAL, BigDecimal.ROUND_HALF_UP));
			}
			else {
				fmsDrvrRfndColReq.setIncentiveAmt(BigDecimal.ZERO);
			}
			if (txn.getPromoAmt() != null) {
				fmsDrvrRfndColReq.setPromoAmt(txn.getPromoAmt().setScale(NonConfigurableConstants.NO_OF_DECIMAL, BigDecimal.ROUND_HALF_UP));
			}
			else {
				fmsDrvrRfndColReq.setPromoAmt(BigDecimal.ZERO);
			}
			if (txn.getCabRewardsAmt() != null) {
				fmsDrvrRfndColReq.setCabRewardsAmt(txn.getCabRewardsAmt().setScale(NonConfigurableConstants.NO_OF_DECIMAL, BigDecimal.ROUND_HALF_UP));
			}
			else {
				fmsDrvrRfndColReq.setCabRewardsAmt(BigDecimal.ZERO);
			}
		}
		fmsDrvrRfndColReq.setPickupAddress(txn.getPickupAddress()==null?NonConfigurableConstants.NA_FLAG:txn.getPickupAddress());
		fmsDrvrRfndColReq.setReqDate(DateUtil.getCurrentTimestamp());
		fmsDrvrRfndColReq.setReqStatus(NonConfigurableConstants.STATUS_PENDING);
		fmsDrvrRfndColReq.setServiceType(NonConfigurableConstants.FMS_SERVICE_NORMAL);
		fmsDrvrRfndColReq.setFareAmt(txn.getFareAmt());

		MstbMasterTable aptMaster = txn.getMstbAcquirerPymtType().getMstbMasterTable();
		fmsDrvrRfndColReq.setPaymentMode(aptMaster.getInterfaceMappingValue());

		fmsDrvrRfndColReq.setTaxiNo(txn.getTaxiNo());
		fmsDrvrRfndColReq.setTripStartDt(txn.getTripStartDt());
		if(txn.getTripEndDt()!=null)fmsDrvrRfndColReq.setTripEndDt(txn.getTripEndDt());
		else fmsDrvrRfndColReq.setTripEndDt(txn.getTripStartDt());
		fmsDrvrRfndColReq.setJobType(txn.getMstbMasterTableByJobType().getMasterValue());
		//fmsDrvrRfndColReq.setUpdatedBy(NonConfigurableConstants.INTERFACE_USER);
		if (txn.getUpdatedBy() != null && !"".equals(txn.getUpdatedBy()))
			fmsDrvrRfndColReq.setUpdatedBy(txn.getUpdatedBy());
		else
			fmsDrvrRfndColReq.setUpdatedBy(txn.getCreatedBy());
		fmsDrvrRfndColReq.setUpdatedDt(DateUtil.getCurrentTimestamp());

		return fmsDrvrRfndColReq;
	}

	public List<MstbMasterTable> getPymtType(MstbAcquirer acquirer){
		return this.daoHelper.getNonBillableDao().getPymtType(acquirer);
	}

	public boolean isChargebackTxnGLed(TmtbNonBillableTxn txn){
		return this.daoHelper.getNonBillableDao().isChargebackTxnGLed(txn);
	}

	public List<VwBankAdviseAyden> retrieveAydenSettlementDetailReport() {
		return this.daoHelper.getNonBillableDao().retrieveAydenSettlementDetailReport();
	}

	public List<VwBankAdviseAmex> retrieveAmexSettlementDetailReport() {
		return this.daoHelper.getNonBillableDao().retrieveAmexSettlementDetailReport();
	}
	
	/* WanTing - 07/01/2021 - CR/1220/028 Start*/
	public List<VwBankAdviceIBS> retrieveCommonSettlementDetailReport() {
		return this.daoHelper.getNonBillableDao().retrieveCommonSettlementDetailReport();
	}
	/* WanTing - 07/01/2021 - CR/1220/028 End*/

	public TmtbNonBillableTxnCrcaReq  searchTmtbCrcaRequest(){
		return this.daoHelper.getNonBillableDao().searchTmtbCrcaRequest();
	}

	@Deprecated
	public List<IttbSetlReportingAyden> processAydenSettlement(List<VwBankAdviseAyden> aydenSettlementDetailList,List<IttbSetlReportingAyden> ittbSetlReportingAydenList ){

		List <TmtbNonBillableTxnCrca> crcaList  = new ArrayList<TmtbNonBillableTxnCrca>();
		List <IttbSetlReportingAyden> settlList = new ArrayList<IttbSetlReportingAyden>();
		TmtbNonBillableTxnCrca crca = new TmtbNonBillableTxnCrca();


		if(aydenSettlementDetailList == null || aydenSettlementDetailList.size() == 0){
			return null;
		}

		for (VwBankAdviseAyden ayden : aydenSettlementDetailList) {

			IttbSetlReportingAyden settl = new IttbSetlReportingAyden();

			BeanUtils.copyProperties(ayden, settl);
			ayden.setRetrievedFlag("Y");
			ayden.setRetrievedDate(new Date(System.currentTimeMillis()));

			crca = createNewCrca(ayden);

			crcaList.add(crca);
			settlList.add(settl);
		}

		logger.info("processAdyenSettlement - crcaList size " + crcaList.size());
		logger.info("processAdyenSettlement - settlList size " + settlList.size());

		int j = 0;
		int i = 1;

		for(i = 1; i<crcaList.size()-1; i++) {
			if(i % NonConfigurableConstants.HIBERNATE_MINIMUM_SIZE == 0) {
				this.daoHelper.getNonBillableDao().processAydenSettlement(crcaList.subList(j, i+1),settlList.subList(j, i+1),aydenSettlementDetailList.subList(j, i+1));
				j = i+1;
			}
		}

		if(j!=i){
			this.daoHelper.getNonBillableDao().processAydenSettlement(crcaList.subList(j, crcaList.size()),settlList.subList(j, settlList.size()),aydenSettlementDetailList.subList(j, aydenSettlementDetailList.size()));
		}


		return settlList;
	}

	@Deprecated
	public List<IttbSetlReportingAmex> processAmexSettlement(
			List<VwBankAdviseAmex> amexSettlementDetailList,
			List<IttbSetlReportingAmex> ittbSetlReportingAmexList) {

		List<TmtbNonBillableTxnCrca> crcaList = new ArrayList<TmtbNonBillableTxnCrca>();
		List<IttbSetlReportingAmex> settlList = new ArrayList<IttbSetlReportingAmex>();
		TmtbNonBillableTxnCrca crca = new TmtbNonBillableTxnCrca();
		IttbSetlReportingAmex settl = new IttbSetlReportingAmex();

		if (amexSettlementDetailList == null || amexSettlementDetailList.size() == 0) {
			return null;
		}

		logger.info("processAmexSettlement - amexSettlementDetailList size " + amexSettlementDetailList.size());

		for (VwBankAdviseAmex amex : amexSettlementDetailList) {

			settl = new IttbSetlReportingAmex();

			BeanUtils.copyProperties(amex, settl);
			amex.setRetrievedFlag("Y");
			amex.setRetrievedDate(new Date(System.currentTimeMillis()));

			crca = createNewCrca(amex);

			crcaList.add(crca);
			settlList.add(settl);
		}

		logger.info("processAmexSettlement - crcaList size " + crcaList.size());
		logger.info("processAmexSettlement - settlList size " + settlList.size());

		int j = 0;
		int i = 1;

		for(i = 1; i<crcaList.size()-1; i++) {
			if(i % NonConfigurableConstants.HIBERNATE_MINIMUM_SIZE == 0) {
				this.daoHelper.getNonBillableDao().processAmexSettlement(crcaList.subList(j, i+1), settlList.subList(j, i+1), amexSettlementDetailList.subList(j, i+1));
				j = i+1;
			}
		}

		if(j!=i){
			this.daoHelper.getNonBillableDao().processAmexSettlement(crcaList.subList(j,crcaList.size()), settlList.subList(j,settlList.size()), amexSettlementDetailList.subList(j,amexSettlementDetailList.size()));
		}

		return settlList;
	}

	public <T> List<T> processCommonSettlement(List<VwBankAdvice> vwBankAdviceList,Class<T> ittbSetlReportingClass) throws IllegalAccessException, InstantiationException {

		List <TmtbNonBillableTxnCrca> crcaList  = new ArrayList<TmtbNonBillableTxnCrca>();
		List <T> settlList = new ArrayList<T>();
		TmtbNonBillableTxnCrca crca = null;

		if(vwBankAdviceList == null || vwBankAdviceList.size() == 0){
			return null;
		}

		for (VwBankAdvice bankAdvice : vwBankAdviceList){
			bankAdvice.setRetrievedFlag("Y");
			bankAdvice.setRetrievedDate(new Date(System.currentTimeMillis()));

			if(bankAdvice instanceof VwBankAdviseAyden) {
				crca = createNewCrca((VwBankAdviseAyden) bankAdvice);
			}else if (bankAdvice instanceof VwBankAdviseAmex){
				crca = createNewCrca((VwBankAdviseAmex) bankAdvice);
			}else if (bankAdvice instanceof VwBankAdviceIBS){
				crca = createNewCrca((VwBankAdviceIBS) bankAdvice);
			}

			crcaList.add(crca);

			if(ittbSetlReportingClass != null) {
				T settl = ittbSetlReportingClass.newInstance();
				BeanUtils.copyProperties(bankAdvice, settl);
				settlList.add(settl);
			}

		}

		logger.info("processCommonSettlement - crcaList size " + crcaList.size());
		logger.info("processCommonSettlement - settlList size " + settlList.size());

		int j = 0;
		int i = 1;

		for(i = 1; i<crcaList.size()-1; i++) {
			if(i % NonConfigurableConstants.HIBERNATE_MINIMUM_SIZE == 0) {
				this.daoHelper.getNonBillableDao().processCommonSettlement(crcaList.subList(j, i+1), settlList.subList(j, i+1), vwBankAdviceList.subList(j, i+1));
				j = i+1;
			}
		}

		if(j!=i){
			this.daoHelper.getNonBillableDao().processCommonSettlement(crcaList.subList(j,crcaList.size()), settlList.subList(j,settlList.size()), vwBankAdviceList.subList(j,vwBankAdviceList.size()));
		}

		return settlList;
	}
	
	/* WanTing - 04/01/2021 - CR/1220/028 Start*/
//	public List<Object> processCommonSettlement(
//					List<Object> commonSettlementDetailList,List<Object> ittbSetlReportingCommonList, List<Object> retrievedList){
//
//		//commonSettlementDetailList => combined markup/scheme fee/interchange
//		//retrievedList => separated markup/schemefee/interchange
//
//				List <TmtbNonBillableTxnCrca> crcaList  = new ArrayList<TmtbNonBillableTxnCrca>();
//				List <Object> settlList = new ArrayList<Object>();
//				TmtbNonBillableTxnCrca crca = new TmtbNonBillableTxnCrca();
//
//				//to set retrievedFlag and retrievedDate for all the retrieved records
//				for (VwBankAdviceIBS retrievedItem : (List<VwBankAdviceIBS>)(Object) retrievedList) {
//					retrievedItem.setRetrievedFlag("Y");
//					retrievedItem.setRetrievedDate(new Date(System.currentTimeMillis()));
//				}
//
//				if(commonSettlementDetailList == null || commonSettlementDetailList.size() == 0){
//					return null;
//				}
//
//				if (commonSettlementDetailList.get(0).getClass().equals(VwBankAdviseAyden.class)) {
//					for (VwBankAdviseAyden ayden : (List<VwBankAdviseAyden>)(Object)commonSettlementDetailList) {
//						Object settl = (Object) new IttbSetlReportingAyden();
//
//						BeanUtils.copyProperties(ayden, settl);
//						ayden.setRetrievedFlag("Y");
//						ayden.setRetrievedDate(new Date(System.currentTimeMillis()));
//
//						crca = createNewCrca(ayden);
//
//						crcaList.add(crca);
//						settlList.add(settl);
//					}
//
//				} else if (commonSettlementDetailList.get(0).getClass().equals(VwBankAdviseAmex.class))  {
//					for (VwBankAdviseAmex amex : (List<VwBankAdviseAmex>)(Object)commonSettlementDetailList) {
//						Object settl = (Object) new IttbSetlReportingAmex();
//
//						BeanUtils.copyProperties(amex, settl);
//						amex.setRetrievedFlag("Y");
//						amex.setRetrievedDate(new Date(System.currentTimeMillis()));
//
//						crca = createNewCrca(amex);
//
//						crcaList.add(crca);
//						settlList.add(settl);
//					}
//
//				}  else if (commonSettlementDetailList.get(0).getClass().equals(VwBankAdviceIBS.class))  {
//					for (VwBankAdviceIBS lazada : (List<VwBankAdviceIBS>)(Object)commonSettlementDetailList) {
////						Object settl = (Object) new IttbSetlReportingAmex();
//
////						BeanUtils.copyProperties(amex, settl);
////						lazada.setRetrievedFlag("Y");
////						lazada.setRetrievedDate(new Date(System.currentTimeMillis()));
//
//						crca = createNewCrca(lazada);
//
//						crcaList.add(crca);
////						settlList.add(null);
//					}
//
//				}
//
//				logger.info("processCommonSettlement - crcaList size " + crcaList.size());
//				logger.info("processCommonSettlement - settlList size " + settlList.size());
//
//				int j = 0;
//				int i = 1;
//
//				for(i = 1; i<crcaList.size()-1; i++) {
//					if(i % NonConfigurableConstants.HIBERNATE_MINIMUM_SIZE == 0) {
//						this.daoHelper.getNonBillableDao().processCommonSettlement(crcaList.subList(j, i+1),null,retrievedList.subList(j, i+1));
//						j = i+1;
//					}
//				}
//
//				if(j!=i){
//					this.daoHelper.getNonBillableDao().processCommonSettlement(crcaList.subList(j, crcaList.size()),null,retrievedList.subList(j, retrievedList.size()));
//				}
//
//				return settlList;
//
//	}
	/* WanTing - 04/01/2021 - CR/1220/028 End */

	public List<TmtbNonBillableTxn> retrieveCrca(List<TmtbNonBillableTxn> txns) {

		List<TmtbNonBillableTxn> result = new ArrayList<TmtbNonBillableTxn>();

		for(TmtbNonBillableTxn txn: txns){
			List<TmtbNonBillableTxnCrca> crca1;
			List<TmtbNonBillableTxnCrca> crca2;

			if(txn.getPspRefNo1() != null) {
				crca1 = this.daoHelper.getNonBillableDao().retrieveCrca(txn.getPspRefNo1());
				txn.setTmtbNonBillableTxnCrca1(new HashSet<TmtbNonBillableTxnCrca>((crca1)));
			}else{
				txn.setTmtbNonBillableTxnCrca1(null);
			}
			if(txn.getPspRefNo2() != null) {
				crca2 = this.daoHelper.getNonBillableDao().retrieveCrca(txn.getPspRefNo2());
				txn.setTmtbNonBillableTxnCrca2(new HashSet<TmtbNonBillableTxnCrca>((crca2)));
			}else{
				txn.setTmtbNonBillableTxnCrca2(null);
			}

			result.add(txn);
		}

		return result;
	}
	public List<Object[]> processAydenCompletenessCheck(List<NonBillableBatchDto> batches){
		return this.daoHelper.getNonBillableDao().processAydenCompletenessCheck(batches);
	}

	//Todo put in new NonBillableUtil
	private TmtbNonBillableTxnCrca createNewCrca(VwBankAdviseAyden ayden) {

		TmtbNonBillableTxnCrca tmtbNonBillableTxnCrca = new TmtbNonBillableTxnCrca();

		tmtbNonBillableTxnCrca.setPspRefNo(ayden.getPspRefNo());
		tmtbNonBillableTxnCrca.setBatchCode(ayden.getBatchNo());
		tmtbNonBillableTxnCrca.setGrossCredit(ayden.getGrossDebit());//flip because credit from ayden is debit for us
		tmtbNonBillableTxnCrca.setGrossDebit(ayden.getGrossCredit());//flip because debit from ayden is credit for us
		tmtbNonBillableTxnCrca.setCommission(ayden.getCommission());
		tmtbNonBillableTxnCrca.setNetCredit(ayden.getNetDebit());//flip because credit from ayden is debit for us
		tmtbNonBillableTxnCrca.setNetDebit(ayden.getNetCredit());//flip because debit from ayden is credit for us
		tmtbNonBillableTxnCrca.setInterchange(ayden.getInterchange());
		tmtbNonBillableTxnCrca.setPaymentMethod(ayden.getPaymentMethod());
		tmtbNonBillableTxnCrca.setMarkup(ayden.getMarkup());
		tmtbNonBillableTxnCrca.setSchemeFee(ayden.getSchemeFees());
		tmtbNonBillableTxnCrca.setRecordType(ayden.getType());
		tmtbNonBillableTxnCrca.setSubmissionMerchantId(ayden.getMerchantRefNo()); //to be confirmed
		tmtbNonBillableTxnCrca.setFileName(ayden.getFileName());
		tmtbNonBillableTxnCrca.setUploadDate(ayden.getUploadDate());
		tmtbNonBillableTxnCrca.setSource(NonConfigurableConstants.FROM_AYDEN);

		//debit and credit will flip when reach to IBS
		if(ayden.getGrossDebit() != null && ayden.getGrossCredit() != null) {
			tmtbNonBillableTxnCrca.setGrossAmount(ayden.getGrossDebit().subtract(ayden.getGrossCredit()));//to be confirmed
		}else if(ayden.getGrossCredit() != null){
			tmtbNonBillableTxnCrca.setGrossAmount(BigDecimal.ZERO.subtract(ayden.getGrossCredit()));//to be confirmed
		}else if(ayden.getGrossDebit() != null){
			tmtbNonBillableTxnCrca.setGrossAmount(ayden.getGrossDebit());//to be confirmed
		}
		if(ayden.getNetDebit() != null && ayden.getNetCredit() != null) {
			tmtbNonBillableTxnCrca.setNetAmount(ayden.getNetDebit().subtract(ayden.getNetCredit()));//to be confirmed
		}else if(ayden.getNetCredit() != null){
			tmtbNonBillableTxnCrca.setNetAmount(BigDecimal.ZERO.subtract(ayden.getNetCredit()));//to be confirmed
		}else if(ayden.getNetDebit() != null){
			tmtbNonBillableTxnCrca.setNetAmount(ayden.getNetDebit());//to be confirmed
		}

		tmtbNonBillableTxnCrca.setCreatedDt(new Timestamp(System.currentTimeMillis()));
		tmtbNonBillableTxnCrca.setModifiedDt(new Timestamp(System.currentTimeMillis()));

		return tmtbNonBillableTxnCrca;
	}

	private TmtbNonBillableTxnCrca createNewCrca (VwBankAdviseAmex amex) {

		TmtbNonBillableTxnCrca tmtbNonBillableTxnCrca = new TmtbNonBillableTxnCrca();

		tmtbNonBillableTxnCrca.setPspRefNo(amex.getPspRefNo());
		tmtbNonBillableTxnCrca.setBatchCode(amex.getBatchCode());
		tmtbNonBillableTxnCrca.setNetAmount(amex.getNetAmount());
		tmtbNonBillableTxnCrca.setRecordType(amex.getRecordType());
		tmtbNonBillableTxnCrca.setChargebackNo(amex.getChargebackNo());
		tmtbNonBillableTxnCrca.setChargebackReasonCode(amex.getChargebackReasonCode());
		tmtbNonBillableTxnCrca.setChargebackReasonDescription(amex.getChargebackReasonDescription());
		tmtbNonBillableTxnCrca.setDiscountAmount(amex.getDiscountAmount());
		tmtbNonBillableTxnCrca.setDiscountRate(amex.getDiscountRate());
		tmtbNonBillableTxnCrca.setPaymentMethod("amex"); //to be confirmed
		tmtbNonBillableTxnCrca.setTaxAmount(amex.getTaxAmount());
		tmtbNonBillableTxnCrca.setTransactionAmount(amex.getTransactionAmount());
		tmtbNonBillableTxnCrca.setTransactionDate(amex.getTransactionDate());
		tmtbNonBillableTxnCrca.setPaymentCurrency(amex.getPaymentCurrency());
		tmtbNonBillableTxnCrca.setPaymentDate(amex.getPaymentDate());
		tmtbNonBillableTxnCrca.setSubmissionMerchantId(amex.getPayeeMerchantId());
		tmtbNonBillableTxnCrca.setServiceFeeAmount(amex.getServiceFeeAmount());
		tmtbNonBillableTxnCrca.setServiceFeeRate(amex.getServiceFeeRate());
		tmtbNonBillableTxnCrca.setFeeAmount(amex.getFeeAmount());
		tmtbNonBillableTxnCrca.setFeeCode(amex.getFeeCode());
		tmtbNonBillableTxnCrca.setSource(NonConfigurableConstants.FROM_AMEX);
		tmtbNonBillableTxnCrca.setAdjustmentReasonDescription(amex.getAdjustmentReasonDescription());
		tmtbNonBillableTxnCrca.setAdjustmentReasonCode(amex.getAdjustmentReasonCode());
		tmtbNonBillableTxnCrca.setAdjustmentNo(amex.getAdjustmentNo());
		tmtbNonBillableTxnCrca.setFileName(amex.getFileName());
		tmtbNonBillableTxnCrca.setUploadDate(amex.getUploadDate());
		tmtbNonBillableTxnCrca.setCreatedDt(new Timestamp(System.currentTimeMillis()));
		tmtbNonBillableTxnCrca.setModifiedDt(new Timestamp(System.currentTimeMillis()));

		if(amex.getGrossAmount() != null)
			tmtbNonBillableTxnCrca.setGrossAmount(amex.getGrossAmount());//to be confirmed
		else if(amex.getSubmissionGrossAmount() != null)
			tmtbNonBillableTxnCrca.setGrossAmount(amex.getSubmissionGrossAmount());
		else{
			tmtbNonBillableTxnCrca.setGrossAmount(amex.getTransactionAmount());
		}

		return tmtbNonBillableTxnCrca;
	}
	
	/* WanTing - 04/01/2021 - CR/1220/028 Start*/
	private TmtbNonBillableTxnCrca createNewCrca(VwBankAdviceIBS lazada) {
		TmtbNonBillableTxnCrca tmtbNonBillableTxnCrca = new TmtbNonBillableTxnCrca();

		tmtbNonBillableTxnCrca.setPspRefNo(lazada.getPspRefNo());
		tmtbNonBillableTxnCrca.setBatchCode(null); //to confirm for Lazada
		tmtbNonBillableTxnCrca.setGrossCredit(lazada.getGrossDebit());//flip because credit from lazada is debit for us
		tmtbNonBillableTxnCrca.setGrossDebit(lazada.getGrossCredit());//flip because debit from lazada is credit for us
		tmtbNonBillableTxnCrca.setCommission(lazada.getCommission());
		tmtbNonBillableTxnCrca.setNetCredit(lazada.getNetDebit());//flip because credit from lazada is debit for us
		tmtbNonBillableTxnCrca.setNetDebit(lazada.getNetCredit());//flip because debit from lazada is credit for us
		tmtbNonBillableTxnCrca.setInterchange(lazada.getInterchange());
		tmtbNonBillableTxnCrca.setPaymentMethod(lazada.getPaymentMethod());
		tmtbNonBillableTxnCrca.setMarkup(lazada.getMarkup());
//		tmtbNonBillableTxnCrca.setSchemeFee(lazada.getSchemeFees()); //to confirm for Lazada
		tmtbNonBillableTxnCrca.setRecordType(lazada.getRecordType());
//		tmtbNonBillableTxnCrca.setSubmissionMerchantId(lazada.getMerchantRefNo()); //to confirm for Lazada
		tmtbNonBillableTxnCrca.setFileName(lazada.getFileName()); //to confirm for Lazada
		tmtbNonBillableTxnCrca.setUploadDate(lazada.getUploadDate()); //to confirm for Lazada
		tmtbNonBillableTxnCrca.setSource(NonConfigurableConstants.FROM_LAZADA);
		tmtbNonBillableTxnCrca.setRecordType(lazada.getRecordType());
		tmtbNonBillableTxnCrca.setSubmissionMerchantId(lazada.getSubmissionMerchantId());
		tmtbNonBillableTxnCrca.setStatus(lazada.getStatus());
		tmtbNonBillableTxnCrca.setPaymentReference(lazada.getPaymentReference());
		tmtbNonBillableTxnCrca.setDetails(lazada.getDetails());
		tmtbNonBillableTxnCrca.setTransactionNo(lazada.getTransactionNo());
		tmtbNonBillableTxnCrca.setGrossAmount(lazada.getGrossAmount().multiply(BigDecimal.valueOf(-1)));//it is negative because adyen/amex is set to be negative
		tmtbNonBillableTxnCrca.setCreatedDt(new Timestamp(System.currentTimeMillis()));
		tmtbNonBillableTxnCrca.setModifiedDt(new Timestamp(System.currentTimeMillis()));

		return tmtbNonBillableTxnCrca;
	}
	/* WanTing - 04/01/2021 - CR/1220/028 End*/

	public void processTmtbCrcaRequest(TmtbNonBillableTxnCrcaReq req){
		if(req.getReqNo() != null){
			req.setUpdatedDt(new Timestamp(System.currentTimeMillis()));
			this.daoHelper.getGenericDao().update(req,"Ayden Settlement");
		}else {
			req.setCreatedDt(new Timestamp(System.currentTimeMillis()));
			this.daoHelper.getGenericDao().save(req, "Ayden Settlement");
		}
	}
	
	/* WanTing - 04/01/2021 - CR/1220/028 Start*/
	public void processTmtbCrcaRequest(TmtbNonBillableTxnCrcaReq req, String crtUpdNm){
		if(req.getReqNo() != null){
			req.setUpdatedDt(new Timestamp(System.currentTimeMillis()));
			this.daoHelper.getGenericDao().update(req, crtUpdNm);
		}else {
			req.setCreatedDt(new Timestamp(System.currentTimeMillis()));
			this.daoHelper.getGenericDao().save(req, crtUpdNm);
		}
	}
	/* WanTing - 04/01/2021 - CR/1220/028 End*/

	@Override
	public List<TmtbNonBillableTxn> searchNonBillableTxnsWithMatchingStatues(SearchNonBillableTxnsForm form) {
		return this.daoHelper.getNonBillableDao().searchNonBillableTxnsWithMatchingStatues(form);
	}


	public List<Object[]> searchExcessCrca(SearchNonBillableTxnsForm form){
		return this.daoHelper.getNonBillableDao().searchExcessCrca(form);
	}

	@Override
	public List<TmtbNonBillableTxn> searchNonBillablePendingTxnsNotFoundInCrca(SearchNonBillableTxnsForm form) {
		return this.daoHelper.getNonBillableDao().searchNonBillablePendingTxnsNotFoundInCrca(form);
	}

	@Override
	public List<TmtbNonBillableTxn> searchNonBillableChargebackRefundedTxns(SearchNonBillableTxnsForm txnsForm) {
		return this.daoHelper.getNonBillableDao().searchNonBillableChargebackRefundedTxns(txnsForm);
	}

	@Override
	public int updateNonBillableTxnMatchingStatusByHql(String matchingStatus, List<Long> txnIdList) {
		return this.daoHelper.getNonBillableDao().updateNonBillableTxnMatchingStatusByHql(matchingStatus,txnIdList);
	}

}
