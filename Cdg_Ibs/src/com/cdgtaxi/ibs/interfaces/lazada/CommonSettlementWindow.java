package com.cdgtaxi.ibs.interfaces.lazada;

import com.cdgtaxi.ibs.common.ConfigurableConstants;
import com.cdgtaxi.ibs.common.NonConfigurableConstants;
import com.cdgtaxi.ibs.common.model.TmtbNonBillableBatch;
import com.cdgtaxi.ibs.common.model.TmtbNonBillableTxn;
import com.cdgtaxi.ibs.common.model.TmtbNonBillableTxnCrca;
import com.cdgtaxi.ibs.common.model.forms.SearchNonBillableBatchForm;
import com.cdgtaxi.ibs.common.model.forms.SearchNonBillableTxnsForm;
import com.cdgtaxi.ibs.common.ui.CommonWindow;
import com.cdgtaxi.ibs.report.dto.NonBillableBatchDto;
import com.cdgtaxi.ibs.util.DateUtil;
import com.cdgtaxi.ibs.util.EmailUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.springframework.beans.BeanUtils;
import org.zkoss.zkplus.spring.SpringUtil;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

public abstract class CommonSettlementWindow extends CommonWindow {
    private static final long serialVersionUID = 1L;
    private static Logger logger = Logger.getLogger(CommonSettlementWindow.class);
    private BigDecimal chargebackAmount = BigDecimal.ZERO;
    private BigDecimal markup = BigDecimal.ZERO;
    private BigDecimal commission = BigDecimal.ZERO;
    private BigDecimal schemeFee = BigDecimal.ZERO;
    private BigDecimal interchange = BigDecimal.ZERO;
    private BigDecimal chargebackReverseAmount = BigDecimal.ZERO;
    private BigDecimal refund = BigDecimal.ZERO;
    private BigDecimal refundReverse = BigDecimal.ZERO;
    private BigDecimal otherCredit = BigDecimal.ZERO;
    private BigDecimal otherDebit = BigDecimal.ZERO;
    private BigDecimal grossAmount = BigDecimal.ZERO;
    private BigDecimal errorAmount = BigDecimal.ZERO;
    private BigDecimal matchedAmount = BigDecimal.ZERO;
    private BigDecimal pendingAmount = BigDecimal.ZERO;
    private String validIPs = null;
    Map<Long, Long> beforeErrorTripCountMap = new HashMap();


    @Override
    public void refresh() throws InterruptedException {
        // TODO Auto-generated method stub
    }

    public void processChargebackRefunded(List<TmtbNonBillableTxn> chargebackRefundedTxns, Map<String, Object[]> emailDetailMap) {


    }


    public List<TmtbNonBillableBatch> transferToNewBatch(List<TmtbNonBillableTxn> matchedErrorTxns, List<TmtbNonBillableTxn> pendingNotFoundInCrcaTxns,Map<String, Object[]> emailDetailMap){
        logger.info("Start Transfer to New Batch");
        List<TmtbNonBillableTxn> newTxns = new ArrayList<TmtbNonBillableTxn>();
        List<TmtbNonBillableTxn> toBeUpdatedTxns = new ArrayList<TmtbNonBillableTxn>();
//        List<Long> toBeUpdatedTxns2 = new ArrayList<Long>();
        List<TmtbNonBillableTxn> toBeUpdatedTxns2 = new ArrayList<TmtbNonBillableTxn>();
        List<TmtbNonBillableBatch> toBeUpdatedBatches = new ArrayList<TmtbNonBillableBatch>();
        List<TmtbNonBillableBatch> newBatchList = new ArrayList<TmtbNonBillableBatch>();
        long noOfErrorTrip = 0L;
        Map<Long, Long> pendingTripCountMap = new HashMap();
        Map<Long, Long> errorTripCountMap = new HashMap();
        Map<Long, BigDecimal> pendingTripAmountMap = new HashMap();
        Map<Long, BigDecimal> errorTripAmountMap = new HashMap();
        Map<Long, TmtbNonBillableBatch> newBatchMapping = new HashMap();
        Map<Long, BigDecimal> errorCommissionMap = new HashMap<Long, BigDecimal>();
        Map<Long, BigDecimal> errorInterchangeMap = new HashMap<Long, BigDecimal>();
        Map<Long, BigDecimal> errorSchemeFeeMap = new HashMap<Long, BigDecimal>();
        Map<Long, BigDecimal> errorMarkupMap = new HashMap<Long, BigDecimal>();
        BigDecimal errorTripAmount = BigDecimal.ZERO;
        BigDecimal pendingTripAmount = BigDecimal.ZERO;
        TmtbNonBillableTxn prevTxn = null;
        boolean hasBatchCreated = false;

        //count number of txns based on batch id in pendingError
        for (TmtbNonBillableTxn txn : matchedErrorTxns) {

            if (prevTxn != null && !txn.getTmtbNonBillableBatch().equals(prevTxn.getTmtbNonBillableBatch())) {

                newBatchMapping.put(prevTxn.getTmtbNonBillableBatch().getBatchId(), new TmtbNonBillableBatch());

                if (noOfErrorTrip != 0L) {
                    errorTripCountMap.put(prevTxn.getTmtbNonBillableBatch().getBatchId(), noOfErrorTrip);
                    errorTripAmountMap.put(prevTxn.getTmtbNonBillableBatch().getBatchId(), errorTripAmount);
                    errorCommissionMap.put(prevTxn.getTmtbNonBillableBatch().getBatchId(), commission);
                    errorInterchangeMap.put(prevTxn.getTmtbNonBillableBatch().getBatchId(), interchange);
                    errorSchemeFeeMap.put(prevTxn.getTmtbNonBillableBatch().getBatchId(), schemeFee);
                    errorMarkupMap.put(prevTxn.getTmtbNonBillableBatch().getBatchId(), markup);
                }

                noOfErrorTrip = 0;
                errorTripAmount = BigDecimal.ZERO;
                resetQRST();

            }

            if (txn.getMatchingStatus().equals(NonConfigurableConstants.AYDEN_MATCHING_STATUS_ERROR)) {
                noOfErrorTrip++;
                errorTripAmount = txn.getTotal().add(errorTripAmount);
                sumQRST(txn);
            }

            prevTxn = txn;
        }


        if (matchedErrorTxns.size() > 0) {

            newBatchMapping.put(matchedErrorTxns.get(matchedErrorTxns.size() - 1).getTmtbNonBillableBatch().getBatchId(), new TmtbNonBillableBatch());

            if (noOfErrorTrip != 0L) {
                errorTripCountMap.put(matchedErrorTxns.get(matchedErrorTxns.size() - 1).getTmtbNonBillableBatch().getBatchId(), noOfErrorTrip);
                errorTripAmountMap.put(matchedErrorTxns.get(matchedErrorTxns.size() - 1).getTmtbNonBillableBatch().getBatchId(), errorTripAmount);
                errorCommissionMap.put(matchedErrorTxns.get(matchedErrorTxns.size() - 1).getTmtbNonBillableBatch().getBatchId(), commission);
                errorInterchangeMap.put(matchedErrorTxns.get(matchedErrorTxns.size() - 1).getTmtbNonBillableBatch().getBatchId(), interchange);
                errorSchemeFeeMap.put(matchedErrorTxns.get(matchedErrorTxns.size() - 1).getTmtbNonBillableBatch().getBatchId(), schemeFee);
                errorMarkupMap.put(matchedErrorTxns.get(matchedErrorTxns.size() - 1).getTmtbNonBillableBatch().getBatchId(), markup);
            }
        }

        for (Map.Entry<Long, TmtbNonBillableBatch> entry : newBatchMapping.entrySet()) {
            logger.info("entry: " + entry.getKey());
        }

        for (TmtbNonBillableTxn txn : pendingNotFoundInCrcaTxns) {
            if (newBatchMapping.get(txn.getTmtbNonBillableBatch().getBatchId()) != null) {
                pendingTripAmount = txn.getTotal();
                if (pendingTripAmountMap.get(txn.getTmtbNonBillableBatch().getBatchId()) != null) {
                    pendingTripAmountMap.put(txn.getTmtbNonBillableBatch().getBatchId(), pendingTripAmountMap.get(txn.getTmtbNonBillableBatch().getBatchId()).add(pendingTripAmount));
                } else {
                    pendingTripAmountMap.put(txn.getTmtbNonBillableBatch().getBatchId(), pendingTripAmount);
                }
                if (pendingTripCountMap.get(txn.getTmtbNonBillableBatch().getBatchId()) != null) {
                    pendingTripCountMap.put(txn.getTmtbNonBillableBatch().getBatchId(), pendingTripCountMap.get(txn.getTmtbNonBillableBatch().getBatchId()) + 1L);
                } else {
                    pendingTripCountMap.put(txn.getTmtbNonBillableBatch().getBatchId(), 1L);
                }
            }

        }

        prevTxn = null;

        //to create new batch
        for (TmtbNonBillableTxn txn : matchedErrorTxns) {
            if (prevTxn != null && !prevTxn.getTmtbNonBillableBatch().getBatchNo().equals(txn.getTmtbNonBillableBatch().getBatchNo())) {
                hasBatchCreated = false;
            }

            //Should  create batch when
            //there is E matching status and there is unprocessed records that was not processed by performMatching
//            logger.info("batch id: " + txn.getTmtbNonBillableBatch().getBatchId());
//            logger.info("errorTripCountMap.size(): " + errorTripCountMap.size());
//            logger.info("content of errorTripCountMap: " + errorTripCountMap.get(txn.getTmtbNonBillableBatch().getBatchId()));
//            logger.info("pendingTripCountMap.size(): " + pendingTripCountMap.size());
//            logger.info("content of pendingTripCountMap: " + pendingTripCountMap.get(txn.getTmtbNonBillableBatch().getBatchId()));

            // 2) if E increase for batchid, rollover
            // 3) if got P for batchid, rollover
            if ((pendingTripCountMap != null && pendingTripCountMap.get(txn.getTmtbNonBillableBatch().getBatchId()) != null && pendingTripCountMap.size() != txn.getTmtbNonBillableBatch().getTxnCount())
                    || ((errorTripCountMap.get(txn.getTmtbNonBillableBatch().getBatchId()) != null)
                    && ((beforeErrorTripCountMap.get(txn.getTmtbNonBillableBatch().getBatchId()) == null && errorTripCountMap.get(txn.getTmtbNonBillableBatch().getBatchId()) > 0)
                    || (beforeErrorTripCountMap.get(txn.getTmtbNonBillableBatch().getBatchId()) != null && errorTripCountMap.get(txn.getTmtbNonBillableBatch().getBatchId()) > beforeErrorTripCountMap.get(txn.getTmtbNonBillableBatch().getBatchId()))))) {
                hasBatchCreated = performTransferToNewBatch(txn,
                        pendingTripCountMap,
                        errorTripCountMap,
                        errorTripAmountMap,
                        errorCommissionMap,
                        errorInterchangeMap,
                        errorMarkupMap,
                        errorSchemeFeeMap,
                        pendingTripAmountMap,
                        newTxns,
                        toBeUpdatedTxns,
                        toBeUpdatedBatches,
                        newBatchList,
                        newBatchMapping,
                        hasBatchCreated,
                        emailDetailMap);
            }

            prevTxn = txn;
        }

        for (TmtbNonBillableTxn txn : pendingNotFoundInCrcaTxns) {
            if (pendingTripCountMap != null && pendingTripCountMap.get(txn.getTmtbNonBillableBatch().getBatchId()) != null && pendingTripCountMap.size() != txn.getTmtbNonBillableBatch().getTxnCount()) {
                performTransferToNewBatch2(newTxns, toBeUpdatedTxns2, txn, newBatchMapping);
            }
        }


        resetQRST();

        logger.info("Creating " + newTxns.size() + " New Record(s)");

        for (TmtbNonBillableTxn txn : newTxns) {
//            logger.info("Saving " + txn.getTmtbNonBillableBatch().getBatchNo() + " | " + txn.getTmtbNonBillableBatch().getBatchId() + " | " + txn.getMatchingStatus());
            this.businessHelper.getNonBillableBusiness().save(txn, "Common Settlement");
        }

        logger.info("Transfering " + toBeUpdatedTxns.size() + " Record(s)");

        for (TmtbNonBillableTxn txn : toBeUpdatedTxns) {
            this.businessHelper.getNonBillableBusiness().update(txn, "Common Settlement");
        }

        logger.info("Updating " + toBeUpdatedTxns2.size() + " Record(s)");

        for (TmtbNonBillableTxn txn : toBeUpdatedTxns2) {
            this.businessHelper.getNonBillableBusiness().update(txn, "Common Settlement");
        }

        logger.info("Updating " + newBatchMapping.size() + " Batches");

        for (TmtbNonBillableBatch batch : toBeUpdatedBatches) {
            this.businessHelper.getNonBillableBusiness().update(batch, "Common Settlement");

        }

        logger.info("END of Transfer to New Batch");
        if (newBatchList.size() > 0) {
            return newBatchList;
        } else {
            return null;
        }

    }

    public void resetQRST() {

        commission = BigDecimal.ZERO;
        interchange = BigDecimal.ZERO;
        schemeFee = BigDecimal.ZERO;
        markup = BigDecimal.ZERO;
    }

    public void performTransferToNewBatch2(List<TmtbNonBillableTxn> newTxns, List<TmtbNonBillableTxn> toBeUpdatedTxns2, TmtbNonBillableTxn txn, Map<Long, TmtbNonBillableBatch> newBatchMapping) {


        TmtbNonBillableTxn newTxn = new TmtbNonBillableTxn();

        BeanUtils.copyProperties(txn, newTxn);

        newTxn.setTxnId(null);
        newTxn.setTxnNo(null);
        newTxn.setTmtbNonBillableBatch(newBatchMapping.get(txn.getTmtbNonBillableBatch().getBatchId()));
        newTxn.setTmtbNonBillableTxnCrca1(null); //so that wont update this
        newTxn.setTmtbNonBillableTxnCrca2(null); //so that wont update this
        newTxns.add(newTxn);
        txn.setMatchingStatus(NonConfigurableConstants.AYDEN_MATCHING_STATUS_TRANSFERED);

        toBeUpdatedTxns2.add(txn);
    }

    private boolean performTransferToNewBatch(TmtbNonBillableTxn txn,
                                              Map<Long, Long> pendingTripCountMap,
                                              Map<Long, Long> errorTripCountMap,
                                              Map<Long, BigDecimal> errorTripAmountMap,
                                              Map<Long, BigDecimal> errorCommissionMap,
                                              Map<Long, BigDecimal> errorInterchangeMap,
                                              Map<Long, BigDecimal> errorMarkupMap,
                                              Map<Long, BigDecimal> errorSchemeFeeMap,
                                              Map<Long, BigDecimal> pendingTripAmountMap,
                                              List<TmtbNonBillableTxn> newTxns,
                                              List<TmtbNonBillableTxn> toBeUpdatedTxns,
                                              List<TmtbNonBillableBatch> toBeUpdatedBatches,
                                              List<TmtbNonBillableBatch> newBatchList,
                                              Map<Long, TmtbNonBillableBatch> newBatchMapping,
                                              boolean hasBatchCreated,
                                              Map<String, Object[]> emailDetailMap) {

        TmtbNonBillableTxn newTxn = new TmtbNonBillableTxn();
        TmtbNonBillableBatch toBeUpdatedBatch = txn.getTmtbNonBillableBatch();
        Long id;
        Object[] emailDetail;
        Object[] temp = new Object[3];

        //emailDetailMap
        //key: batchId@settlement date
        //count 0: batchNo
        //count 1: processedRecords
        //count 2: matchedRecords
        //count 3: errorRecords
        //count 4: recon_count
        //count 5: ayden_settlement_count
        //count 6: acquirer_no
        //count 7: total amount that processed
        //count 8: total amount that is matched
        //count 9: total amount that is error
        //count 10: pendingRecords
        //count 11: total amount that is pending
        //count 12: file name
        //count 13: Excess Records(record type,payment type,count,gross amount,file name)
        //count 14: Transferred Records(transfer count,transferred amt, transferred batch no)

        if (!hasBatchCreated) {

            TmtbNonBillableBatch newBatch = new TmtbNonBillableBatch();

            BeanUtils.copyProperties(txn.getTmtbNonBillableBatch(), newBatch);

            newBatch.setTxnCount(0);
            newBatch.setTxnAmt(BigDecimal.ZERO);


            if (pendingTripCountMap.get(toBeUpdatedBatch.getBatchId()) != null) {
                toBeUpdatedBatch.setTxnCount(toBeUpdatedBatch.getTxnCount() - pendingTripCountMap.get(toBeUpdatedBatch.getBatchId()));
                newBatch.setTxnCount(pendingTripCountMap.get(newBatch.getBatchId()));
            }

            if (pendingTripAmountMap.get(toBeUpdatedBatch.getBatchId()) != null) {
                toBeUpdatedBatch.setTxnAmt(toBeUpdatedBatch.getTxnAmt().subtract(pendingTripAmountMap.get(toBeUpdatedBatch.getBatchId())));
                newBatch.setTxnAmt(pendingTripAmountMap.get(newBatch.getBatchId()));
            }

            if (errorTripCountMap.get(toBeUpdatedBatch.getBatchId()) != null) {
                toBeUpdatedBatch.setTxnCount(toBeUpdatedBatch.getTxnCount() - errorTripCountMap.get(toBeUpdatedBatch.getBatchId()));
                newBatch.setTxnCount(newBatch.getTxnCount() + errorTripCountMap.get(newBatch.getBatchId()));
                if (errorMarkupMap.get(toBeUpdatedBatch.getBatchId()) != null) {
                    toBeUpdatedBatch.setMarkup(toBeUpdatedBatch.getMarkup().subtract(errorMarkupMap.get(toBeUpdatedBatch.getBatchId())));
                }
                if (errorCommissionMap.get(toBeUpdatedBatch.getBatchId()) != null) {
                    toBeUpdatedBatch.setCommission(toBeUpdatedBatch.getCommission().subtract(errorCommissionMap.get(toBeUpdatedBatch.getBatchId())));
                }
                if (errorInterchangeMap.get(toBeUpdatedBatch.getBatchId()) != null) {
                    toBeUpdatedBatch.setInterchange(toBeUpdatedBatch.getInterchange().subtract(errorInterchangeMap.get(toBeUpdatedBatch.getBatchId())));
                }
                if (errorSchemeFeeMap.get(toBeUpdatedBatch.getBatchId()) != null) {
                    toBeUpdatedBatch.setSchemeFee(toBeUpdatedBatch.getSchemeFee().subtract(errorSchemeFeeMap.get(toBeUpdatedBatch.getBatchId())));
                }
            }
            if (errorTripAmountMap.get(toBeUpdatedBatch.getBatchId()) != null) {
                toBeUpdatedBatch.setTxnAmt(toBeUpdatedBatch.getTxnAmt().subtract(errorTripAmountMap.get(toBeUpdatedBatch.getBatchId())));
                newBatch.setTxnAmt(newBatch.getTxnAmt().add(errorTripAmountMap.get(newBatch.getBatchId())));
            }

            toBeUpdatedBatch.setTmtbNonBillableTxns(null);
            toBeUpdatedBatch.setCompleteStatus(NonConfigurableConstants.AYDEN_STATUS_COMPLETED);
            newBatch.setBmtbBankPaymentDetails(null);
            newBatch.setCreditCount(null);
            newBatch.setCreditAmt(null);
            newBatch.setCompleteStatus(NonConfigurableConstants.AYDEN_STATUS_INCOMPLETE);
            newBatch.setRefundAmt(null);
            newBatch.setRefundReverseAmt(null);
            newBatch.setChargebackAmt(null);
            newBatch.setChargebackReverseAmt(null);
            newBatch.setMarkup(errorMarkupMap.get(toBeUpdatedBatch.getBatchId()));
            newBatch.setCommission(errorCommissionMap.get(toBeUpdatedBatch.getBatchId()));
            newBatch.setSchemeFee(errorSchemeFeeMap.get(toBeUpdatedBatch.getBatchId()));
            newBatch.setInterchange(errorInterchangeMap.get(toBeUpdatedBatch.getBatchId()));
            newBatch.setOtherCreditAmt(null);
            newBatch.setOtherDebitAmt(null);
            newBatch.setUploadDtString(null);
            newBatch.setFileName(null);

            newBatch.setBatchId(null);
            if (newBatch.getBatchNo().split("-").length > 1) {
                newBatch.setBatchNo(newBatch.getBatchNo().split("-")[0] + "-" + (Long.parseLong(newBatch.getBatchNo().split("-")[1]) + 1));
            } else {
                newBatch.setBatchNo(newBatch.getBatchNo().split("-")[0] + "-1");
            }

            logger.info("newBatch created: " + newBatch.getBatchId() + " | " + newBatch.getBatchNo() + " | " + newBatch.getTxnCount());



            temp[0] = newBatch.getTxnCount();
            temp[1] = newBatch.getTxnAmt();
            temp[2] = newBatch.getBatchNo();
            emailDetail = emailDetailMap.get(toBeUpdatedBatch.getBatchId() + "@" + DateUtil.convertDateToStr(txn.getTmtbNonBillableBatch().getSettlementDate(), DateUtil.GLOBAL_DATE_FORMAT));
            emailDetail[14] = temp;
            emailDetailMap.put(toBeUpdatedBatch.getBatchId() + "@" + DateUtil.convertDateToStr(txn.getTmtbNonBillableBatch().getSettlementDate(), DateUtil.GLOBAL_DATE_FORMAT),emailDetail);

            id = (Long) this.businessHelper.getNonBillableBusiness().save(newBatch, "Adyen Settlement");
            newBatch.setBatchId(id);
            newBatchList.add(newBatch);

            newBatchMapping.put(toBeUpdatedBatch.getBatchId(), newBatch);
            toBeUpdatedBatches.add(toBeUpdatedBatch);
            hasBatchCreated = true;
        }
        if (!txn.getMatchingStatus().equals(NonConfigurableConstants.AYDEN_MATCHING_STATUS_MATCHED)) {

            BeanUtils.copyProperties(txn, newTxn);

            newTxn.setTmtbNonBillableBatch(newBatchMapping.get(txn.getTmtbNonBillableBatch().getBatchId()));

            newTxn.setTxnId(null);
            newTxn.setTxnNo(null);
            newTxn.setTmtbNonBillableTxnCrca1(null);
            newTxn.setTmtbNonBillableTxnCrca2(null);
            txn.setMatchingStatus(NonConfigurableConstants.AYDEN_MATCHING_STATUS_TRANSFERED);

            newTxns.add(newTxn);
            toBeUpdatedTxns.add(txn);
        }
        return hasBatchCreated;
    }


    public abstract SearchNonBillableTxnsForm buildNonBillableMatchErrorTxnsSearchForm(List<TmtbNonBillableBatch> batchList);

    public SearchNonBillableBatchForm buildNonBillableBatchSearchForm() {
        SearchNonBillableBatchForm form = new SearchNonBillableBatchForm();
//        form.uploadDateFrom = settlementDate;
//        form.uploadDateTo = settlementDate;
        List<String> result = new ArrayList<String>();
        result.add(NonConfigurableConstants.AYDEN_MATCHING_STATUS_PENDING);
        result.add(NonConfigurableConstants.AYDEN_MATCHING_STATUS_ERROR);

        form.matchingStatus = result;
        return form;
    }

    public abstract SearchNonBillableTxnsForm buildNonBillablePendingTxnsSearchForm(List<NonBillableBatchDto> batchList);

//	public SearchNonBillableTxnsForm buildNonBillableExcessRecordsSearchForm() {
//        SearchNonBillableTxnsForm form = new SearchNonBillableTxnsForm();
//        List<String> result = new ArrayList<String>();
//
//        result.add(NonConfigurableConstants.AYDEN_MATCHING_STATUS_PENDING);
//        result.add(NonConfigurableConstants.AYDEN_MATCHING_STATUS_ERROR);
//        result.add(NonConfigurableConstants.AYDEN_MATCHING_STATUS_MATCHED);
//
//        form.matchingStatuses = result;
//
//        result = new ArrayList<String>();
//        result.add(NonConfigurableConstants.AMEX_RECORD_STATUS_TXNPRICING); //to exclude this transaction
//
//        form.recordType = result;
//
//        return form;
//    }

    public abstract SearchNonBillableTxnsForm buildNonBillableTxnsSearchForm(List<NonBillableBatchDto> batchList);

    public abstract SearchNonBillableTxnsForm buildNonBillableChargebackRefundedTxnsSearchForm(List<NonBillableBatchDto> batchList);

    public abstract SearchNonBillableTxnsForm buildNonBillableMatchedTxnsSearchForm(List<NonBillableBatchDto> batchList);

    //	public SearchNonBillableTxnsForm buildNonBillableAllTxnsSearchForm2(List<TmtbNonBillableBatch> batchList) {
    public abstract SearchNonBillableTxnsForm buildNonBillableMatchedTxnsSearchForm2(List<TmtbNonBillableBatch> batchList);

    public List<TmtbNonBillableBatch> processMatching(List<TmtbNonBillableTxn> txns, Map<String, Object[]> emailDetailMap) {
        boolean[] hasDecrepencies = new boolean[2];
        TmtbNonBillableTxn prevTxn = null;
        Long processedRecords = 0L;
        Long matchedRecords = 0L;
        Long errorRecords = 0L;
        List<TmtbNonBillableBatch> processedBatch = new ArrayList<TmtbNonBillableBatch>();
        Map<Long, String> errorTxns = new HashMap<Long, String>();
        String fileName = null;
        String uploadDateString = null;

        //emailDetailMap
        //key: batchId@settlement date
        //count 0: batchNo
        //count 1: processedRecords
        //count 2: matchedRecords
        //count 3: errorRecords
        //count 4: recon_count
        //count 5: ayden_settlement_count
        //count 6: acquirer_no
        //count 7: total amount that processed
        //count 8: total amount that is matched
        //count 9: total amount that is error
        //count 10: pendingRecords
        //count 11: total amount that is pending
        //count 12: file name
        //count 13: Excess Records(record type,payment type,count,gross amount,file name)
        //count 14: Transferred Records(transfer count,transferred amt, transferred batch no)

        logger.info("Performing Matching::: txn.size: " + txns.size());
        logger.info("Updating Matching Status ");

        //count number of error records that begin with
        for (TmtbNonBillableTxn txn : txns) {

            if (txn.getMatchingStatus().equals(NonConfigurableConstants.AYDEN_MATCHING_STATUS_ERROR)) {
                errorTxns.put(txn.getTxnId(), txn.getMatchingStatus());
                errorRecords++;
            }

            if (prevTxn != null && !txn.getTmtbNonBillableBatch().equals(prevTxn.getTmtbNonBillableBatch())) {
                if (prevTxn.getMatchingStatus().equals(NonConfigurableConstants.AYDEN_MATCHING_STATUS_ERROR)){
                    beforeErrorTripCountMap.put(prevTxn.getTmtbNonBillableBatch().getBatchId(),errorRecords);
                    errorRecords = 0L;
                }
            }
            prevTxn = txn;
        }

        if (txns.size() > 0) {

            if (txns.get(txns.size() - 1).getMatchingStatus().equals(NonConfigurableConstants.AYDEN_MATCHING_STATUS_ERROR)) {
                errorRecords++;
                beforeErrorTripCountMap.put(prevTxn.getTmtbNonBillableBatch().getBatchId(),errorRecords);
            }

        }

        //process matching status and setup email content
        for (TmtbNonBillableTxn txn : txns) {

            if (txn.getMatchingStatus().equals(NonConfigurableConstants.AYDEN_MATCHING_STATUS_ERROR))
                errorTxns.put(txn.getTxnId(), txn.getMatchingStatus());

            //sum up all the grossDebit and groerrorAmountssAmount
            BigDecimal totalGrossAmount1 = new BigDecimal(0.00);
            BigDecimal totalGrossAmount2 = new BigDecimal(0.00);

            String source1 = null;
            String source2 = null;

            hasDecrepencies[0] = false; // Lazada

            Object[] temp = new Object[15];
            temp[0] = txn.getTmtbNonBillableBatch().getBatchNo();
            temp[1] = Long.valueOf(0);
            temp[2] = Long.valueOf(0);
            temp[3] = Long.valueOf(0);
            temp[4] = Long.valueOf(0);
            temp[5] = Long.valueOf(0);
            //temp[6] = Acquirer
            temp[7] = BigDecimal.ZERO;
            temp[8] = BigDecimal.ZERO;
            temp[9] = BigDecimal.ZERO;
            temp[10] = Long.valueOf(0);
            temp[11] = BigDecimal.ZERO;
            temp[13] = new ArrayList<Object[]>();
            temp[14] = new Object[3];

            //lazada crca1 records for gross amount
            if (txn.getTmtbNonBillableTxnCrca1() != null && txn.getTmtbNonBillableTxnCrca1().size() > 0) {
                for (TmtbNonBillableTxnCrca crca1 : txn.getTmtbNonBillableTxnCrca1()) {
                    if (crca1 != null && crca1.getGrossAmount() != null) {
                        totalGrossAmount1 = totalGrossAmount1.add(crca1.getGrossAmount());
                    }
                }

                if (txn.getTmtbNonBillableTxnCrca1().iterator().next() != null) {
                    source1 = txn.getTmtbNonBillableTxnCrca1().iterator().next().getSource();
                }
            }

            //lazada crca1 records for txn status
            if (txn.getTmtbNonBillableTxnCrca1() != null && txn.getTmtbNonBillableTxnCrca1().size() > 0) {
                for (TmtbNonBillableTxnCrca crca1 : txn.getTmtbNonBillableTxnCrca1()) {
                    if (crca1 != null) {
                        if (checkTxnStatus(crca1.getRecordType())) {
                            txn = setTxnStatus(crca1, txn);
                            break;
                        }
                    }
                }
            }


            //lazada crca2 records for gross amount
            if (txn.getTmtbNonBillableTxnCrca2() != null && txn.getTmtbNonBillableTxnCrca2().size() > 0) {
                for (TmtbNonBillableTxnCrca crca2 : txn.getTmtbNonBillableTxnCrca2()) {
                    if (crca2 != null && crca2.getGrossAmount() != null) {
                        totalGrossAmount2 = totalGrossAmount2.add(crca2.getGrossAmount());
                    }
                }


                //kiv
                if (txn.getTmtbNonBillableTxnCrca2().iterator().next() != null) {
                    source2 = txn.getTmtbNonBillableTxnCrca2().iterator().next().getSource();
                }
            }

            //lazada crca2 records for txn status
            if (txn.getTmtbNonBillableTxnCrca2() != null && txn.getTmtbNonBillableTxnCrca2().size() > 0) {
                for (TmtbNonBillableTxnCrca crca2 : txn.getTmtbNonBillableTxnCrca2()) {
                    if (crca2 != null) {
                        if (checkTxnStatus(crca2.getRecordType())) {
                            txn = setTxnStatus(crca2, txn);
                            break;
                        }
                    }
                }
            }
            if (txn.getTotal().doubleValue() != Math.abs((totalGrossAmount1.add(totalGrossAmount2)).doubleValue())) { //Todo totalGrossAmount to be confirmed
                txn.setMatchingStatus(NonConfigurableConstants.AYDEN_MATCHING_STATUS_ERROR);
                if ((source1 != null && source1.equals(NonConfigurableConstants.FROM_LAZADA)) || (source2 != null && source2.equals(NonConfigurableConstants.FROM_LAZADA))) {
                    hasDecrepencies[0] = true;
                }
            }


            if (!hasDecrepencies[0]) {
                txn.setMatchingStatus(NonConfigurableConstants.AYDEN_MATCHING_STATUS_MATCHED);
            }


            temp[0] = txn.getTmtbNonBillableBatch().getBatchNo();
            emailDetailMap.put(
                    txn.getTmtbNonBillableBatch().getBatchId() + "@" +
                            DateUtil.convertDateToStr(txn.getTmtbNonBillableBatch().getSettlementDate(), DateUtil.GLOBAL_DATE_FORMAT)
                    , temp);


            this.businessHelper.getNonBillableBusiness().update(txn);
        }

        logger.info("End of Updating Matching Status ");

        //perform sum of all transaction
        prevTxn = null;
        errorRecords = 0L;


        chargebackAmount.setScale(NonConfigurableConstants.NO_OF_DECIMAL,
                BigDecimal.ROUND_HALF_UP);
        markup.setScale(NonConfigurableConstants.NO_OF_DECIMAL,
                BigDecimal.ROUND_HALF_UP);
        commission.setScale(NonConfigurableConstants.NO_OF_DECIMAL,
                BigDecimal.ROUND_HALF_UP);
        schemeFee.setScale(NonConfigurableConstants.NO_OF_DECIMAL,
                BigDecimal.ROUND_HALF_UP);
        interchange.setScale(NonConfigurableConstants.NO_OF_DECIMAL,
                BigDecimal.ROUND_HALF_UP);
        chargebackReverseAmount.setScale(NonConfigurableConstants.NO_OF_DECIMAL,
                BigDecimal.ROUND_HALF_UP);
        refund.setScale(NonConfigurableConstants.NO_OF_DECIMAL,
                BigDecimal.ROUND_HALF_UP);
        refundReverse.setScale(NonConfigurableConstants.NO_OF_DECIMAL,
                BigDecimal.ROUND_HALF_UP);
        otherCredit.setScale(NonConfigurableConstants.NO_OF_DECIMAL,
                BigDecimal.ROUND_HALF_UP);
        otherDebit.setScale(NonConfigurableConstants.NO_OF_DECIMAL,
                BigDecimal.ROUND_HALF_UP);

        Object[] temp = null;

        logger.info("Updating Q,R,S,T "); //Q R S T = interchange,comission,scheme fee, markup. Not in any particular order

        for (TmtbNonBillableTxn txn : txns) {
            if (prevTxn != null && !txn.getTmtbNonBillableBatch().equals(prevTxn.getTmtbNonBillableBatch())) {

                //to troubleshoot
//                if (prevTxn.getTmtbNonBillableBatch().getChargebackAmt() != null)
//                    prevTxn.getTmtbNonBillableBatch().setChargebackAmt(chargebackAmount.add(prevTxn.getTmtbNonBillableBatch().getChargebackAmt()));
//                else
//                    prevTxn.getTmtbNonBillableBatch().setChargebackAmt(chargebackAmount);
//                if (prevTxn.getTmtbNonBillableBatch().getChargebackReverseAmt() != null)
//                    prevTxn.getTmtbNonBillableBatch().setChargebackReverseAmt(chargebackReverseAmount.add(prevTxn.getTmtbNonBillableBatch().getChargebackReverseAmt()));
//                else
//                    prevTxn.getTmtbNonBillableBatch().setChargebackReverseAmt(chargebackReverseAmount);
//                if (prevTxn.getTmtbNonBillableBatch().getRefundAmt() != null)
//                    prevTxn.getTmtbNonBillableBatch().setRefundAmt(refund.add(prevTxn.getTmtbNonBillableBatch().getRefundAmt()));
//                else
//                    prevTxn.getTmtbNonBillableBatch().setRefundAmt(refund);
//                if (prevTxn.getTmtbNonBillableBatch().getRefundReverseAmt() != null)
//                    prevTxn.getTmtbNonBillableBatch().setRefundReverseAmt(refundReverse.add(prevTxn.getTmtbNonBillableBatch().getRefundReverseAmt()));
//                else
//                    prevTxn.getTmtbNonBillableBatch().setRefundReverseAmt(refundReverse);
                if (prevTxn.getTmtbNonBillableBatch().getMarkup() != null)
                    prevTxn.getTmtbNonBillableBatch().setMarkup(markup.add(prevTxn.getTmtbNonBillableBatch().getMarkup()));
                else
                    prevTxn.getTmtbNonBillableBatch().setMarkup(markup);
                if (prevTxn.getTmtbNonBillableBatch().getCommission() != null)
                    prevTxn.getTmtbNonBillableBatch().setCommission(commission.add(prevTxn.getTmtbNonBillableBatch().getCommission()));
                else
                    prevTxn.getTmtbNonBillableBatch().setCommission(commission);
                if (prevTxn.getTmtbNonBillableBatch().getInterchange() != null)
                    prevTxn.getTmtbNonBillableBatch().setInterchange(interchange.add(prevTxn.getTmtbNonBillableBatch().getInterchange()));
                else
                    prevTxn.getTmtbNonBillableBatch().setInterchange(interchange);
                if (prevTxn.getTmtbNonBillableBatch().getSchemeFee() != null)
                    prevTxn.getTmtbNonBillableBatch().setSchemeFee(schemeFee.add(prevTxn.getTmtbNonBillableBatch().getSchemeFee()));
                else
                    prevTxn.getTmtbNonBillableBatch().setSchemeFee(schemeFee);
                if (prevTxn.getTmtbNonBillableBatch().getOtherCreditAmt() != null)
                    prevTxn.getTmtbNonBillableBatch().setOtherCreditAmt(otherCredit.add(prevTxn.getTmtbNonBillableBatch().getOtherCreditAmt()));
                else
                    prevTxn.getTmtbNonBillableBatch().setOtherCreditAmt(otherCredit);
                if (prevTxn.getTmtbNonBillableBatch().getOtherDebitAmt() != null)
                    prevTxn.getTmtbNonBillableBatch().setOtherDebitAmt(otherDebit.add(prevTxn.getTmtbNonBillableBatch().getOtherDebitAmt()));
                else
                    prevTxn.getTmtbNonBillableBatch().setOtherDebitAmt(otherDebit);

                prevTxn.getTmtbNonBillableBatch().setFileName(fileName);
                prevTxn.getTmtbNonBillableBatch().setUploadDtString(uploadDateString);


                temp = emailDetailMap.get(
                        prevTxn.getTmtbNonBillableBatch().getBatchId() + "@" +
                                DateUtil.convertDateToStr(prevTxn.getTmtbNonBillableBatch().getSettlementDate(), DateUtil.GLOBAL_DATE_FORMAT));
                //count 1: processedRecords
                //count 2: matchedRecords
                //count 3: errorRecords
                //count 7: total amount that processed
                //count 8: total amount that is matched
                //count 9: total amount that is error

                temp[1] = processedRecords;
                temp[2] = matchedRecords;
                temp[3] = errorRecords;
                temp[6] = prevTxn.getTmtbNonBillableBatch().getMstbAcquirer().getName();
                temp[7] = grossAmount;
                temp[8] = matchedAmount;
                temp[9] = errorAmount;
                temp[12] = fileName;

                emailDetailMap.put(
                        prevTxn.getTmtbNonBillableBatch().getBatchId() + "@" +
                                DateUtil.convertDateToStr(prevTxn.getTmtbNonBillableBatch().getSettlementDate(), DateUtil.GLOBAL_DATE_FORMAT), temp);


                resetQRST();
                matchedRecords = 0L;
                processedRecords = 0L;
                errorRecords = 0L;
                grossAmount = BigDecimal.ZERO;
                matchedAmount = BigDecimal.ZERO;
                errorAmount = BigDecimal.ZERO;

                fileName = null;
                uploadDateString = null;
            }
            //counting the records

            processedRecords++;

            if (txn.getMatchingStatus().equals(NonConfigurableConstants.AYDEN_MATCHING_STATUS_MATCHED)) {
                matchedRecords++;
            }

            if (txn.getMatchingStatus().equals(NonConfigurableConstants.AYDEN_MATCHING_STATUS_ERROR)) {
                errorRecords++;
            }

            if (txn.getTotal() != null) {
                grossAmount = grossAmount.add(txn.getTotal());
            }
            if (txn.getTotal() != null && txn.getMatchingStatus().equals(NonConfigurableConstants.AYDEN_MATCHING_STATUS_ERROR)) {
                errorAmount = errorAmount.add(txn.getTotal());
            }
            if (txn.getTotal() != null && txn.getMatchingStatus().equals(NonConfigurableConstants.AYDEN_MATCHING_STATUS_MATCHED)) {
                matchedAmount = matchedAmount.add(txn.getTotal());
            }

            //setup respective record type

            if (errorTxns.get(txn.getTxnId()) == null) {
                sumQRST(txn);
            }

            if (txn.getTmtbNonBillableTxnCrca1() != null && StringUtils.isBlank(fileName)) {
                String dateStr = DateUtil.convertDateToStr(txn.getTmtbNonBillableTxnCrca1().iterator().next().getUploadDate(), "dd/MM/yyyy");
                uploadDateString = dateStr;
            } else if (txn.getTmtbNonBillableTxnCrca1() != null) {
                String dateStr = DateUtil.convertDateToStr(txn.getTmtbNonBillableTxnCrca1().iterator().next().getUploadDate(), "dd/MM/yyyy");
                if (!fileName.contains(txn.getTmtbNonBillableTxnCrca1().iterator().next().getFileName())) {
                    uploadDateString = uploadDateString + "," + dateStr;
                }
            }

            if (txn.getTmtbNonBillableTxnCrca1() != null && StringUtils.isBlank(fileName)) {
                fileName = txn.getTmtbNonBillableTxnCrca1().iterator().next().getFileName();
            } else if (txn.getTmtbNonBillableTxnCrca1() != null && !fileName.contains(txn.getTmtbNonBillableTxnCrca1().iterator().next().getFileName())) {
                fileName = fileName + "," + txn.getTmtbNonBillableTxnCrca1().iterator().next().getFileName();
            }

            if (txn.getTmtbNonBillableTxnCrca2() != null && StringUtils.isBlank(fileName)) {
                String dateStr = DateUtil.convertDateToStr(txn.getTmtbNonBillableTxnCrca2().iterator().next().getUploadDate(), "dd/MM/yyyy");
                uploadDateString = dateStr;
            } else if (txn.getTmtbNonBillableTxnCrca2() != null) {
                String dateStr = DateUtil.convertDateToStr(txn.getTmtbNonBillableTxnCrca2().iterator().next().getUploadDate(), "dd/MM/yyyy");
                if (!fileName.contains(txn.getTmtbNonBillableTxnCrca2().iterator().next().getFileName())) {
                    uploadDateString = uploadDateString + "," + dateStr;
                }
            }


            if (txn.getTmtbNonBillableTxnCrca2() != null && StringUtils.isBlank(fileName)) {
                fileName = txn.getTmtbNonBillableTxnCrca2().iterator().next().getFileName();
            } else if (txn.getTmtbNonBillableTxnCrca2() != null && !fileName.contains(txn.getTmtbNonBillableTxnCrca2().iterator().next().getFileName())) {
                fileName = fileName + "," + txn.getTmtbNonBillableTxnCrca2().iterator().next().getFileName();
            }


            prevTxn = txn;
        }

        if (txns.size() > 0) {
            //final results
//            if (txns.get(txns.size() - 1).getTmtbNonBillableBatch().getChargebackAmt() != null)
//                txns.get(txns.size() - 1).getTmtbNonBillableBatch().setChargebackAmt(chargebackAmount.add(txns.get(txns.size() - 1).getTmtbNonBillableBatch().getChargebackAmt()));
//            else
//                txns.get(txns.size() - 1).getTmtbNonBillableBatch().setChargebackAmt(chargebackAmount);
//            if (txns.get(txns.size() - 1).getTmtbNonBillableBatch().getChargebackReverseAmt() != null)
//                txns.get(txns.size() - 1).getTmtbNonBillableBatch().setChargebackReverseAmt(chargebackReverseAmount.add(txns.get(txns.size() - 1).getTmtbNonBillableBatch().getChargebackReverseAmt()));
//            else
//                txns.get(txns.size() - 1).getTmtbNonBillableBatch().setChargebackReverseAmt(chargebackReverseAmount);
//            if (txns.get(txns.size() - 1).getTmtbNonBillableBatch().getRefundAmt() != null)
//                txns.get(txns.size() - 1).getTmtbNonBillableBatch().setRefundAmt(refund.add(txns.get(txns.size() - 1).getTmtbNonBillableBatch().getRefundAmt()));
//            else
//                txns.get(txns.size() - 1).getTmtbNonBillableBatch().setRefundAmt(refund);
//            if (txns.get(txns.size() - 1).getTmtbNonBillableBatch().getRefundReverseAmt() != null)
//                txns.get(txns.size() - 1).getTmtbNonBillableBatch().setRefundReverseAmt(refundReverse.add(txns.get(txns.size() - 1).getTmtbNonBillableBatch().getRefundReverseAmt()));
//            else
//                txns.get(txns.size() - 1).getTmtbNonBillableBatch().setRefundReverseAmt(refundReverse);
            if (txns.get(txns.size() - 1).getTmtbNonBillableBatch().getMarkup() != null)
                txns.get(txns.size() - 1).getTmtbNonBillableBatch().setMarkup(markup.add(txns.get(txns.size() - 1).getTmtbNonBillableBatch().getMarkup()));
            else
                txns.get(txns.size() - 1).getTmtbNonBillableBatch().setMarkup(markup);
            if (txns.get(txns.size() - 1).getTmtbNonBillableBatch().getCommission() != null)
                txns.get(txns.size() - 1).getTmtbNonBillableBatch().setCommission(commission.add(txns.get(txns.size() - 1).getTmtbNonBillableBatch().getCommission()));
            else
                txns.get(txns.size() - 1).getTmtbNonBillableBatch().setCommission(commission);
            if (txns.get(txns.size() - 1).getTmtbNonBillableBatch().getInterchange() != null)
                txns.get(txns.size() - 1).getTmtbNonBillableBatch().setInterchange(interchange.add(txns.get(txns.size() - 1).getTmtbNonBillableBatch().getInterchange()));
            else
                txns.get(txns.size() - 1).getTmtbNonBillableBatch().setInterchange(interchange);
            if (txns.get(txns.size() - 1).getTmtbNonBillableBatch().getSchemeFee() != null)
                txns.get(txns.size() - 1).getTmtbNonBillableBatch().setSchemeFee(schemeFee.add(txns.get(txns.size() - 1).getTmtbNonBillableBatch().getSchemeFee()));
            else
                txns.get(txns.size() - 1).getTmtbNonBillableBatch().setSchemeFee(schemeFee);
            if (txns.get(txns.size() - 1).getTmtbNonBillableBatch().getOtherCreditAmt() != null)
                txns.get(txns.size() - 1).getTmtbNonBillableBatch().setOtherCreditAmt(otherCredit.add(txns.get(txns.size() - 1).getTmtbNonBillableBatch().getOtherCreditAmt()));
            else
                txns.get(txns.size() - 1).getTmtbNonBillableBatch().setOtherCreditAmt(otherCredit);
            if (txns.get(txns.size() - 1).getTmtbNonBillableBatch().getOtherDebitAmt() != null)
                txns.get(txns.size() - 1).getTmtbNonBillableBatch().setOtherDebitAmt(otherDebit.add(txns.get(txns.size() - 1).getTmtbNonBillableBatch().getOtherDebitAmt()));
            else
                txns.get(txns.size() - 1).getTmtbNonBillableBatch().setOtherDebitAmt(otherDebit);

            txns.get(txns.size() - 1).getTmtbNonBillableBatch().setFileName(fileName);
            txns.get(txns.size() - 1).getTmtbNonBillableBatch().setUploadDtString(uploadDateString);

            temp = emailDetailMap.get(
                    txns.get(txns.size() - 1).getTmtbNonBillableBatch().getBatchId() + "@" +
                            DateUtil.convertDateToStr(txns.get(txns.size() - 1).getTmtbNonBillableBatch().getSettlementDate(), DateUtil.GLOBAL_DATE_FORMAT));

            temp[1] = processedRecords;
            temp[2] = matchedRecords;
            temp[3] = errorRecords;
            temp[6] = txns.get(txns.size() - 1).getTmtbNonBillableBatch().getMstbAcquirer().getName();
            temp[7] = grossAmount;
            temp[8] = matchedAmount;
            temp[9] = errorAmount;
            temp[12] = txns.get(txns.size() - 1).getTmtbNonBillableBatch().getFileName();

            emailDetailMap.put(
                    txns.get(txns.size() - 1).getTmtbNonBillableBatch().getBatchId() + "@" +
                            DateUtil.convertDateToStr(txns.get(txns.size() - 1).getTmtbNonBillableBatch().getSettlementDate(), DateUtil.GLOBAL_DATE_FORMAT), temp);

        }


        resetQRST();
        int j = 0;
        int i = 1;

        for (i = 1; i < txns.size() - 1; i++) {
            if (i % NonConfigurableConstants.HIBERNATE_MINIMUM_SIZE == 0) {
                updateBatch(txns.subList(j, i + 1), processedBatch);
                j = i + 1;
            }
        }

        if (txns.size() > 0 && j != i) {
            updateBatch(txns.subList(j, txns.size()), processedBatch);
        }

        logger.info("End of Updating Q,R,S,T ");
        logger.info("End of Perform Matching::: txn.size: " + txns.size());

        return processedBatch;
    }

    public TmtbNonBillableTxn sumQRST(TmtbNonBillableTxn txn) {

        if (txn.getTmtbNonBillableTxnCrca1() != null && txn.getTmtbNonBillableTxnCrca1().size() > 0) {
            for (TmtbNonBillableTxnCrca crca1 : txn.getTmtbNonBillableTxnCrca1()) {
                if (crca1 != null) {
//                    if (crca1.getRecordType().equals(NonConfigurableConstants.AMEX_RECORD_STATUS_CHARGEBACK))
//                        chargebackAmount = chargebackAmount.add(crca1.getGrossAmount());
//                    else if (crca1.getRecordType().equals(NonConfigurableConstants.AMEX_STATUS_ADJUSTMENT)) //amex
//                        refund = refund.add(crca1.getGrossAmount());
//                    else if (crca1.getRecordType().equals(NonConfigurableConstants.AYDEN_RECORD_STATUS_REFUNDED)) //ayden
//                        refund = refund.add(crca1.getGrossAmount());
//                    else if (crca1.getRecordType().equals(NonConfigurableConstants.AYDEN_RECORD_STATUS_REFUNDED_REVERSE)) //ayden
//                        refundReverse = refundReverse.add(crca1.getGrossAmount());
//                    else if (crca1.getRecordType().equals(NonConfigurableConstants.AYDEN_RECORD_STATUS_SECOND_CHARGEBACK) || crca1.getRecordType().equals(NonConfigurableConstants.AYDEN_RECORD_STATUS_CHARGEBACK))
//                        chargebackAmount = chargebackAmount.add(crca1.getGrossAmount());
//                    else if (crca1.getRecordType().equals(NonConfigurableConstants.AYDEN_RECORD_STATUS_CHARGEBACK_REVERSE))
//                        chargebackReverseAmount = chargebackReverseAmount.add(crca1.getGrossAmount());
//                    else {
                    if (crca1.getRecordType() != null &&
                            !crca1.getRecordType().equals(NonConfigurableConstants.AYDEN_RECORD_STATUS_SETTLED) &&
                            !crca1.getRecordType().equals(NonConfigurableConstants.AMEX_RECORD_STATUS_TRANSACTION)) {
                        if (crca1.getGrossCredit() != null)
                            otherCredit = otherCredit.add(crca1.getGrossCredit());
                        if (crca1.getGrossDebit() != null)
                            otherDebit = otherDebit.add(crca1.getGrossDebit());
//                	}
                    }
                    if (crca1.getMarkup() != null) {
                        markup = markup.add(crca1.getMarkup());
                    }
                    if (crca1.getCommission() != null) {
                        commission = commission.add(crca1.getCommission());

                    }
                    if (crca1.getSchemeFee() != null) {
                        schemeFee = schemeFee.add(crca1.getSchemeFee());

                    }
                    if (crca1.getInterchange() != null) {
                        interchange = interchange.add(crca1.getInterchange());

                    }
                }
            }

        }
        if (txn.getTmtbNonBillableTxnCrca2() != null && txn.getTmtbNonBillableTxnCrca2().size() > 0) {
            for (TmtbNonBillableTxnCrca crca2 : txn.getTmtbNonBillableTxnCrca2()) {
                if (crca2 != null) {
//                    if (crca2.getRecordType().equals(NonConfigurableConstants.AMEX_RECORD_STATUS_CHARGEBACK))
//                        chargebackAmount = chargebackAmount.add(crca2.getGrossAmount());
//                    		else if (crca2.getRecordType().equals(NonConfigurableConstants.AMEX_STATUS_ADJUSTMENT)) //amex
//                        		refund = refund.add(crca2.getGrossAmount());
//                    else if (crca2.getRecordType().equals(NonConfigurableConstants.AYDEN_RECORD_STATUS_REFUNDED)) //ayden
//                        refund = refund.add(crca2.getGrossAmount());
//                    else if (crca2.getRecordType().equals(NonConfigurableConstants.AYDEN_RECORD_STATUS_REFUNDED_REVERSE)) //ayden
//                        refundReverse = refundReverse.add(crca2.getGrossAmount());
//                    else if (crca2.getRecordType().equals(NonConfigurableConstants.AYDEN_RECORD_STATUS_SECOND_CHARGEBACK) || crca2.getRecordType().equals(NonConfigurableConstants.AYDEN_RECORD_STATUS_CHARGEBACK))
//                        chargebackAmount = chargebackAmount.add(crca2.getGrossAmount());
//                    else if (crca2.getRecordType().equals(NonConfigurableConstants.AYDEN_RECORD_STATUS_CHARGEBACK_REVERSE))
//                        chargebackReverseAmount = chargebackReverseAmount.add(crca2.getGrossAmount());
//                    else {
                    if (crca2.getRecordType() != null &&
                            !crca2.getRecordType().equals(NonConfigurableConstants.AYDEN_RECORD_STATUS_SETTLED) &&
                            !crca2.getRecordType().equals(NonConfigurableConstants.AMEX_RECORD_STATUS_TRANSACTION)) {
                        if (crca2.getGrossCredit() != null)
                            otherCredit = otherCredit.add(crca2.getGrossCredit());
                        if (crca2.getGrossDebit() != null)
                            otherDebit = otherDebit.add(crca2.getGrossDebit());
//                        }
                    }

                    if (crca2.getMarkup() != null) {
                        markup = markup.add(crca2.getMarkup());
                    }
                    if (crca2.getCommission() != null) {
                        commission = commission.add(crca2.getCommission());

                    }
                    if (crca2.getSchemeFee() != null) {
                        schemeFee = schemeFee.add(crca2.getSchemeFee());

                    }
                    if (crca2.getInterchange() != null) {
                        interchange = interchange.add(crca2.getInterchange());

                    }
                }
            }
        }

        return txn;
    }

    public boolean processAllPendingError(List<TmtbNonBillableTxn> txns, Map<String, Object[]> emailDetailMap) {

        TmtbNonBillableTxn prevTxn = null;
        Long pendingRecords = 0L;
        Object[] temp;

        //emailDetailMap
        //key: batchId@settlement date
        //count 0: batchNo
        //count 1: processedRecords
        //count 2: matchedRecords
        //count 3: errorRecords
        //count 4: recon_count
        //count 5: ayden_settlement_count
        //count 6: acquirer_no
        //count 7: total amount that processed
        //count 8: total amount that is matched
        //count 9: total amount that is error
        //count 10: pendingRecords
        //count 11: total amount that is pending
        //count 12: file name
        //count 13: Excess Records(record type,payment type,count,gross amount,file name)
        //count 14: Transferred Records(transfer count,transferred amt, transferred batch no)

        logger.info("Processing processAllPendingError::::::::::::::::::::::::::::::::");

        for (TmtbNonBillableTxn txn : txns) {
            if (prevTxn == null || txn.getTmtbNonBillableBatch().equals(prevTxn.getTmtbNonBillableBatch())) {

                //counting the records
                pendingRecords++;

                //setup respective record type
                pendingAmount = pendingAmount.add(txn.getTotal());
                grossAmount = grossAmount.add(txn.getTotal());

            } else if (!txn.getTmtbNonBillableBatch().equals(prevTxn.getTmtbNonBillableBatch())) {
                //final results

                temp = emailDetailMap.get(
                        prevTxn.getTmtbNonBillableBatch().getBatchId() + "@" +
                                DateUtil.convertDateToStr(prevTxn.getTmtbNonBillableBatch().getSettlementDate(), DateUtil.GLOBAL_DATE_FORMAT));

                if (temp == null) {
                    temp = new Object[15];
                    temp[0] = prevTxn.getTmtbNonBillableBatch().getBatchNo();
                    temp[1] = Long.valueOf(0);
                    temp[2] = Long.valueOf(0);
                    temp[3] = Long.valueOf(0);
                    temp[4] = Long.valueOf(0);
                    temp[5] = Long.valueOf(0);
                    temp[6] = prevTxn.getTmtbNonBillableBatch().getMstbAcquirer().getName();
                    temp[7] = BigDecimal.ZERO;
                    temp[8] = BigDecimal.ZERO;
                    temp[9] = BigDecimal.ZERO;
                    temp[10] = Long.valueOf(0);
                    temp[11] = BigDecimal.ZERO;
                    temp[12] = txn.getTmtbNonBillableBatch().getFileName();
                    temp[13] = new ArrayList<Object[]>();
                    temp[14] = new Object[3];
                }

                temp[10] = pendingRecords;
                temp[11] = pendingAmount;
                temp[1] = ((Long) temp[1]) + (pendingRecords);
                temp[7] = ((BigDecimal) temp[7]).add(pendingAmount);


                emailDetailMap.put(
                        prevTxn.getTmtbNonBillableBatch().getBatchId() + "@" +
                                DateUtil.convertDateToStr(prevTxn.getTmtbNonBillableBatch().getSettlementDate(), DateUtil.GLOBAL_DATE_FORMAT), temp);


                pendingAmount = BigDecimal.ZERO;
                pendingRecords = 1L;
                pendingAmount = pendingAmount.add(txn.getTotal());

            }

            prevTxn = txn;
        }
        if (txns.size() > 0) {
            //final results

            temp = emailDetailMap.get(
                    txns.get(txns.size() - 1).getTmtbNonBillableBatch().getBatchId() + "@" +
                            DateUtil.convertDateToStr(txns.get(txns.size() - 1).getTmtbNonBillableBatch().getSettlementDate(), DateUtil.GLOBAL_DATE_FORMAT));

            if (temp == null) {
                temp = new Object[15];
                temp[0] = txns.get(txns.size() - 1).getTmtbNonBillableBatch().getBatchNo();
                temp[1] = Long.valueOf(0);
                temp[2] = Long.valueOf(0);
                temp[3] = Long.valueOf(0);
                temp[4] = Long.valueOf(0);
                temp[5] = Long.valueOf(0);
                temp[6] = txns.get(txns.size() - 1).getTmtbNonBillableBatch().getMstbAcquirer().getName();
                temp[7] = BigDecimal.ZERO;
                temp[8] = BigDecimal.ZERO;
                temp[9] = BigDecimal.ZERO;
                temp[10] = Long.valueOf(0);
                temp[11] = BigDecimal.ZERO;
                temp[12] = txns.get(txns.size() - 1).getTmtbNonBillableBatch().getFileName();
                temp[13] = new ArrayList<Object[]>();
                temp[14] = new Object[3];
            }

            temp[10] = pendingRecords;
            temp[11] = pendingAmount;
            temp[1] = ((Long) temp[1]) + (pendingRecords);
            temp[7] = ((BigDecimal) temp[7]).add(pendingAmount);

            emailDetailMap.put(
                    txns.get(txns.size() - 1).getTmtbNonBillableBatch().getBatchId() + "@" +
                            DateUtil.convertDateToStr(txns.get(txns.size() - 1).getTmtbNonBillableBatch().getSettlementDate(), DateUtil.GLOBAL_DATE_FORMAT), temp);

        }

        logger.info("End of Processing processAllPendingError:::::::::::::::::::::::: " + pendingRecords);

        if (pendingRecords > 0) {
            return true;
        } else {
            return false;
        }
    }

    public boolean processExcessAmount(List<Object[]> excessObjs, Map<String, Object[]> emailDetailMap) {

        String compositeKey;
        Object count[];

        //emailDetailMap
        //key: batchId@settlement date
        //count 0: batchNo
        //count 1: processedRecords
        //count 2: matchedRecords
        //count 3: errorRecords
        //count 4: recon_count
        //count 5: ayden_settlement_count
        //count 6: acquirer_no
        //count 7: total amount that processed
        //count 8: total amount that is matched
        //count 9: total amount that is error
        //count 10: pendingRecords
        //count 11: total amount that is pending
        //count 12: file name
        //count 13: Excess Records(record type,payment type,count,gross amount,file name)
        //count 14: Transferred Records(transfer count,transferred amt, transferred batch no)

        for (Map.Entry<String, Object[]> entry : emailDetailMap.entrySet()) {
            compositeKey = entry.getKey();
            count = entry.getValue();
            List<Object[]> temp = new ArrayList<Object[]>();
            if(count[12] == null) {
                if(excessObjs.size()>0) {
                    temp.add(excessObjs.get(0));
                }
            }else{
                String[] fileNameSplitted = count[12].toString().split(",");

                for (Object[] excessObj : excessObjs) {
                    for (int i = 0; i < fileNameSplitted.length; i++) {
                        if (excessObj[4].equals(fileNameSplitted[i])) {
                            temp.add(excessObj);
                        }
                    }
                }
            }

            count[13] = temp;
            emailDetailMap.put(compositeKey, count);
        }

        return true;
    }

    public void updateBatch(List<TmtbNonBillableTxn> txns, List<TmtbNonBillableBatch> processedBatch) {

        for (TmtbNonBillableTxn txn : txns) {
            boolean check = false;

            for (TmtbNonBillableBatch batch : processedBatch) {
                if (batch.getBatchId() == txn.getTmtbNonBillableBatch().getBatchId())
                    check = true;
            }

            if (!check) {
                this.businessHelper.getNonBillableBusiness().update(txn.getTmtbNonBillableBatch());
                processedBatch.add(txn.getTmtbNonBillableBatch());
            }

        }
    }

    public void sendMatchingNotification(Map<String, Object[]> emailDetailMap2, StringBuilder warningMessage) {


        //emailDetailMap
        //key: batchId@settlement date
        //count 0: batchNo
        //count 1: processedRecords
        //count 2: matchedRecords
        //count 3: errorRecords
        //count 4: recon_count
        //count 5: ayden_settlement_count
        //count 6: acquirer_no
        //count 7: total amount that processed
        //count 8: total amount that is matched
        //count 9: total amount that is error
        //count 10: pendingRecords
        //count 11: total amount that is pending
        //count 12: file name
        //count 13: Excess Records(record type,payment type,count,gross amount,file name)
        //count 14: Transferred Records(transfer count,transferred amt, transferred batch no)


        logger.info("::::::::::::Performing email notification::::::::::: ");
        String emails = ConfigurableConstants.getEmailText(ConfigurableConstants.EMAIL_TYPE_LAZADA_SETTLEMENT, "EMAIL");
        Map properties = (Map) SpringUtil.getBean("aydenSettlementProperties");
        String from = (String) properties.get("aydenSettlementFrom");
        String emailType;
        String compositeKey = null;
        String subject = null;
        String prevExcessRecord;
        String temp[];
        Object[] transferRecords;

        //key: batchId|settlement date
        //create a new sortTreeMap that will sort map stuff
        Map<String, Object[]> emailDetailMap = new LinkedHashMap();
        Map<Date, String> sortTreeMap = new TreeMap<Date, String>(new Comparator<Date>() {
            public int compare(Date o1, Date o2) {
                //change to o2.compareTo(o1) if want to sort date other way.
                return o1.compareTo(o2);
            }
        });

        //put records in the tree map that will sort automatically. --Start

        if (emailDetailMap2 != null) {
            for (Map.Entry<String, Object[]> entry : emailDetailMap2.entrySet()) {

                String compositeKey2 = entry.getKey();
                String batchIdx2 = compositeKey2.split("@")[0];
                Date settlementDatex2 = DateUtil.convertStrToDate(compositeKey2.split("@")[1], DateUtil.GLOBAL_DATE_FORMAT);

                sortTreeMap.put(settlementDatex2, batchIdx2);
            }
            //put in the tree map that will sort. --End

            //loop thru the tree map and pull each sort record out and put nicely into emailDetailMap --start
            for (Map.Entry<Date, String> tempTreeMap : sortTreeMap.entrySet()) {
                String batchIdx3 = tempTreeMap.getValue();
                String settlementDatex3 = DateUtil.convertDateToStr(tempTreeMap.getKey(), DateUtil.GLOBAL_DATE_FORMAT);

                Object[] entry2 = emailDetailMap2.get(batchIdx3 + "@" + settlementDatex3);

                emailDetailMap.put(batchIdx3 + "@" + settlementDatex3, entry2);
            }
        }
        //loop thru the tree map and pull each sort record out and put everything nicely into emailDetailmap --end


        if (emailDetailMap != null && emailDetailMap.size() > 0) {
            Object count[];

            emailType = ConfigurableConstants.EMAIL_TYPE_LAZADA_SETTLEMENT_COMPLETED;


            //prepare data to send

            for (Map.Entry<String, Object[]> entry : emailDetailMap.entrySet()) {

                compositeKey = entry.getKey();
                count = entry.getValue();
                String content = ConfigurableConstants.getEmailText(emailType, ConfigurableConstants.EMAIL_CONTENT);
                subject = ConfigurableConstants.getEmailText(emailType, ConfigurableConstants.EMAIL_SUBJECT);
                prevExcessRecord = null;

                if ((Long) count[3] == 0) {
                    if (count[2].equals(count[1]))
                        content = content.replace("#title", "With All Matched");
                    else
                        content = content.replace("#title", "");
                } else {
                    content = content.replace("#title", "With Error (Txn Amount)");
                }


                content = content + "<br/><strong>[Settlement date: #settlementDate]</strong><br/>" +
                        "<br/>Batch number processed: #batchNo<br/>" +
                        "<br/>Batch Acquirer: #acquirer<br/><br/>" +
                        "<br/>(A) + (B) + (C) Total records processed: #processedRecords<br/>" +
                        "<br/>Total amount processed: $#totalAmount<br/><br/>" +
                        "<br/>(A) Total records matched: #matchedRecords<br/>" +
                        "<br/>Total amount matched: $#amountMatched<br/><br/>" +
                        "<br/>(B) Total records with error(txn amount): #errorRecords<br/>" +
                        "<br/>Total amount with error(txn amount): $#errorAmount<br/><br/>" +
                        "<br/>(C) Total pending records: #pendingRecords<br/>" +
                        "<br/>Total pending amount: $#pendingAmount<br/><br/>";

                if (count[14] != null && ((Object[])count[14])[2] != null) {
                    content = content + "<hr/><br/>Batch No: #transferredBatch<br/>";
                    content = content + "(B) + (C) Total transferred records: #transferredRecords <br/>";
                    content = content + "Total transferred amount: $#transferredAmount<br/><br/><hr/>";
                }

                if (((List<Object[]>) count[13]).size() > 0) {
                    content = content + "<br/><br/><strong>[Excess Records]</strong><br/>";
                }
                for (Object obj[] : (List<Object[]>) count[13]) {
//                    obj[0] = recordType
//                    obj[1] = paymentType
//                    obj[2] = count
//                    obj[3] = grossAmount
//                    obj[4] = fileName
                    if (prevExcessRecord == null) {
                        content = content + "<p style=\"text-align: left;\">Record Type: " + obj[0] + " ( File Name: " + obj[4] + " )" + "</p>" + "<table style=\"border-collapse: collapse; width: 25%;\" border=\"1\">";
                    } else if (prevExcessRecord != null && !prevExcessRecord.equals(obj[4])) {
                        content = content + "</table>";
                        content = content + "</br>";
                        content = content + "<p style=\"text-align: left;\">Record Type: " + obj[0] + " ( File Name: " + obj[4] + " )" + "</p>" + "<table style=\"border-collapse: collapse; width: 25%;\" border=\"1\">";
                    }

                    content = content + "<tr>" + "<td>Payment Type:</td>";
                    if (obj[1].equals(NonConfigurableConstants.AYDEN_RECORD_TYPE_MC)) {
                        content = content + "<td>" + NonConfigurableConstants.AYDEN_RECORD_TYPE_MASTERCARD + "</td></tr>";
                    } else {
                        content = content + "<td>" + obj[1] + "</td></tr>";
                    }
                    content = content + "<tr>" + "<td>Number of records:</td>";
                    content = content + "<td>" + obj[2] + "</td></tr>";
                    content = content + "<tr>" + "<td>Amount:</td>";
                    content = content + "<td>$" + obj[3] + "</td></tr>";
                    content = content + "<tr><td></td>";
                    content = content + "<td></td></tr>";

                    prevExcessRecord = obj[4].toString();

                }

                if (((List<Object[]>) count[13]).size() > 0) {
                    content = content + "</table>";
//                    content = content + "<br/><hr/>";
                }


                if (warningMessage.length() > 0) {
                    content = content + "<br/><br/><strong>[[Warning]]</strong>";
                    content = content + "<br/>" + warningMessage;
                }


                content = content.replace("#settlementDate", compositeKey.split("@")[1]);
                content = content.replace("#batchNo", (String) count[0]);
                content = content.replace("#processedRecords", ((Long) count[1]).toString());
                content = content.replace("#matchedRecords", ((Long) count[2]).toString());
                content = content.replace("#errorRecords", ((Long) count[3]).toString());
                content = content.replace("#acquirer", ((String) count[6]));
                content = content.replace("#totalAmount", ((BigDecimal) count[7]).abs().toString());
                content = content.replace("#amountMatched", ((BigDecimal) count[8]).abs().toString());
                content = content.replace("#errorAmount", ((BigDecimal) count[9]).abs().toString());
                content = content.replace("#pendingRecords", ((Long) count[10]).toString());
                content = content.replace("#pendingAmount", ((BigDecimal) count[11]).abs().toString());

                if(count[14] != null && ((Object[])count[14])[2] != null) {
                    transferRecords = (Object[])count[14];
                    content = content.replace("#transferredBatch", (String)transferRecords[2]);
                    content = content.replace("#transferredRecords", ((Long)transferRecords[0]).toString());
                    content = content.replace("#transferredAmount", ((BigDecimal)transferRecords[1]).toString());
                }

                subject = subject.replace("#settlementDate", compositeKey.split("@")[1]);
                subject = subject.replace("#acq", (String) count[6]);
                subject = subject.replace("#status", "SUCCESS: ");


                logger.info("content: " + content);
                logger.info("subject: " + subject);

//                EmailUtil.sendEmail(from, emails.split(";")
//                        , subject, content);
                try {
                    EmailUtil.sendEmail(emails.split(";")
                            , new String[]{}, subject, content);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            //send email to those that have count

        } else {
            emailType = ConfigurableConstants.EMAIL_TYPE_LAZADA_SETTLEMENT_ERROR;
            String content = ConfigurableConstants.getEmailText(emailType, ConfigurableConstants.EMAIL_CONTENT);
            content = content + "<br/>An Exception has occurred. Please contact IBS. No Matching Has Been Done";

            subject = ConfigurableConstants.getEmailText(emailType, ConfigurableConstants.EMAIL_SUBJECT);
            subject = subject.replace("#settlementDate", "N.A");
            subject = subject.replace("#acq", "N.A");
            EmailUtil.sendEmail(emails.split(";"), new String[]{},
                    subject, content.replace("#settlementDate", "N.A"));

        }

        logger.info("::::::::::::END OF Performing email notification:::::::::::");
    }

    public TmtbNonBillableTxn setTxnStatus(TmtbNonBillableTxnCrca crca, TmtbNonBillableTxn txn) {

        //Todo: check data variable of amex
        if (crca.getRecordType().equals(NonConfigurableConstants.AMEX_RECORD_STATUS_CHARGEBACK)) {
            if (txn.getChargebackRefundDate() == null) {
                txn.setChargebackRefundDate(DateUtil.convertUtilDateToSqlDate(DateUtil.convertStrToDate(DateUtil.getStrCurrentDate("dd-MMM-yy"), "dd-MMM-yy")));
            }
            txn.setChargebackRefundFareAmt(crca.getGrossAmount());
            txn.setChargebackRefundAdminFee(new BigDecimal(0));
            txn.setChargebackRefundGst(new BigDecimal(0));
            txn.setStatus(NonConfigurableConstants.NON_BILLABLE_TXN_CHARGEBACK);
        } else if (crca.getRecordType().equals(NonConfigurableConstants.AMEX_STATUS_ADJUSTMENT)) { //amex
            if (txn.getChargebackRefundDate() == null) {
                txn.setChargebackRefundDate(DateUtil.convertUtilDateToSqlDate(DateUtil.convertStrToDate(DateUtil.getStrCurrentDate("dd-MMM-yy"), "dd-MMM-yy")));
            }
            txn.setChargebackRefundFareAmt(crca.getGrossAmount());
            txn.setChargebackRefundAdminFee(new BigDecimal(0));
            txn.setChargebackRefundGst(new BigDecimal(0));
            txn.setStatus(NonConfigurableConstants.NON_BILLABLE_TXN_REFUNDED);
        } else if (crca.getRecordType().equals(NonConfigurableConstants.AYDEN_RECORD_STATUS_REFUNDED)) {
            if (txn.getChargebackRefundDate() == null) {
                txn.setChargebackRefundDate(DateUtil.convertUtilDateToSqlDate(DateUtil.convertStrToDate(DateUtil.getStrCurrentDate("dd-MMM-yy"), "dd-MMM-yy")));
            }
            txn.setChargebackRefundFareAmt(crca.getGrossAmount());
            txn.setChargebackRefundAdminFee(new BigDecimal(0));
            txn.setChargebackRefundGst(new BigDecimal(0));
            txn.setStatus(NonConfigurableConstants.NON_BILLABLE_TXN_REFUNDED);
        } else if (crca.getRecordType().equals(NonConfigurableConstants.AYDEN_RECORD_STATUS_CHARGEBACK)) { //ayden
            if (txn.getChargebackRefundDate() == null) {
                txn.setChargebackRefundDate(DateUtil.convertUtilDateToSqlDate(DateUtil.convertStrToDate(DateUtil.getStrCurrentDate("dd-MMM-yy"), "dd-MMM-yy")));
            }
            txn.setChargebackRefundFareAmt(crca.getGrossAmount());
            txn.setChargebackRefundAdminFee(new BigDecimal(0));
            txn.setChargebackRefundGst(new BigDecimal(0));
            txn.setStatus(NonConfigurableConstants.NON_BILLABLE_TXN_CHARGEBACK);
        }

        return txn;
    }

    public boolean checkTxnStatus(String recordType) {

        //Todo: check data variable of amex
        if(recordType == null){
            return false;
        }
        if (recordType.equals(NonConfigurableConstants.AMEX_RECORD_STATUS_CHARGEBACK))//amex
            return true;
        else if (recordType.equals(NonConfigurableConstants.AMEX_STATUS_ADJUSTMENT)) //amex
            return true;
        else if (recordType.equals(NonConfigurableConstants.AYDEN_RECORD_STATUS_REFUNDED)) //ayden
            return true;
        else if (recordType.equals(NonConfigurableConstants.AYDEN_RECORD_STATUS_CHARGEBACK)) //ayden
            return true;
        return false;
    }

    //could be txns from other batch job too. check batch no
    public boolean processCompletenessCheck
    (List<TmtbNonBillableTxn> txns, List<NonBillableBatchDto> batches, Map<String, Object[]> emailDetailMap) {

        //emailDetailMap
        //key: batchId@settlement date
        //count 0: batchNo
        //count 1: processedRecords
        //count 2: matchedRecords
        //count 3: errorRecords
        //count 4: recon_count
        //count 5: ayden_settlement_count
        //count 6: acquirer_no
        //count 7: total amount that processed
        //count 8: total amount that is matched
        //count 9: total amount that is error
        //count 10: pendingRecords
        //count 11: total amount that is pending
        //count 12: file name
        //count 13: Excess Records(record type,payment type,count,gross amount,file name)
        //count 14: Transferred Records(transfer count,transferred amt, transferred batch no)

        logger.info("Performing Completeness Check::::::::::::::::");

        for (TmtbNonBillableTxn txn : txns) {
            txn.getTmtbNonBillableBatch().setCompleteStatus(null); //reset the status to null everytime before processing
        }


        List<Object[]> results = this.businessHelper.getNonBillableBusiness().processAydenCompletenessCheck(batches);

        for (Object[] result : results) {
            //results 0: batchId
            //results 1: recon_count
            //results 2: ayden_settlement_count
            //results 3: settlementDate

            Object[] count = emailDetailMap.get(result[0].toString() + "@" + result[3]);

            if (count == null) {
                continue;
            }
            count[4] = result[1];
            count[5] = result[2];

            emailDetailMap.put(result[0].toString() + "@" + result[3], count);


            //for testing
            count = emailDetailMap.get(result[0].toString() + "@" + result[3]);


            for (TmtbNonBillableTxn txn : txns) {
//                txn.getTmtbNonBillableBatch().setCompleteStatus(null); //reset the status to null everytime before processing
                if (txn.getTmtbNonBillableBatch().getBatchId().equals(result[0])) {
                    if (!count[4].equals(count[5]) || txn.getMatchingStatus().equals(NonConfigurableConstants.AYDEN_MATCHING_STATUS_ERROR)) {
                        txn.getTmtbNonBillableBatch().setCompleteStatus(NonConfigurableConstants.AYDEN_STATUS_INCOMPLETE);
                    }
                }
            }


        }

        int j = 0;
        int i = 1;
        Set<Long> processedBatch = new HashSet<Long>();

        for (i = 1; i < txns.size() - 1; i++) {
            if (i % NonConfigurableConstants.HIBERNATE_MINIMUM_SIZE == 0) {
                updateBatchForComplete(txns.subList(j, i + 1), processedBatch);
                j = i + 1;
            }
        }

        if (j != i) {
            updateBatchForComplete(txns.subList(j, txns.size()), processedBatch);
        }


        logger.info("End of Performing Completeness Check::::::::::::::::");

        if (results.size() > 0) {
            return true;
        } else {
            return false;
        }


    }

    public void updateBatchForComplete(List<TmtbNonBillableTxn> txns, Set<Long> processedBatch) {

        for (TmtbNonBillableTxn txn : txns) {
            boolean check = false;
            if (txn.getTmtbNonBillableBatch().getCompleteStatus() == null || !txn.getTmtbNonBillableBatch().getCompleteStatus().equals(NonConfigurableConstants.AYDEN_STATUS_INCOMPLETE)) {
                txn.getTmtbNonBillableBatch().setCompleteStatus(NonConfigurableConstants.AYDEN_STATUS_COMPLETED);
            }
            for (Long batchId : processedBatch) {
                if (batchId.equals(txn.getTmtbNonBillableBatch().getBatchId())) {
                    check = true;
                    break;
                }
            }

            if (!check) {
                logger.info("Updating Batch ID: " + txn.getTmtbNonBillableBatch().getBatchId() + " | txn.getTmtbNonBillableBatch().getCompleteStatus(): " + txn.getTmtbNonBillableBatch().getCompleteStatus());

                this.businessHelper.getNonBillableBusiness().update(txn.getTmtbNonBillableBatch(), "Lazada Settlement");
            }

            processedBatch.add(txn.getTmtbNonBillableBatch().getBatchId());

        }
    }

}
