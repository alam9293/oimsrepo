package com.cdgtaxi.ibs.interfaces.lazada;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.cdgtaxi.ibs.common.model.VwBankAdvice;
import org.apache.log4j.Logger;
import org.zkoss.zkplus.spring.SpringUtil;

import com.cdgtaxi.ibs.common.ConfigurableConstants;
import com.cdgtaxi.ibs.common.NonConfigurableConstants;
import com.cdgtaxi.ibs.common.model.TmtbNonBillableBatch;
import com.cdgtaxi.ibs.common.model.TmtbNonBillableTxn;
import com.cdgtaxi.ibs.common.model.TmtbNonBillableTxnCrcaReq;
import com.cdgtaxi.ibs.common.model.VwBankAdviceIBS;
import com.cdgtaxi.ibs.common.model.forms.SearchNonBillableBatchForm;
import com.cdgtaxi.ibs.common.model.forms.SearchNonBillableTxnsForm;
import com.cdgtaxi.ibs.master.model.MstbMasterTable;
import com.cdgtaxi.ibs.report.dto.NonBillableBatchDto;

public class LazadaSettlementWindow extends CommonSettlementWindow {

    private static final long serialVersionUID = 1L;
    private static Logger logger = Logger.getLogger(LazadaSettlementWindow.class);
//    BigDecimal chargebackAmount = BigDecimal.ZERO;
//    BigDecimal markup = BigDecimal.ZERO;
//    BigDecimal commission = BigDecimal.ZERO;
//    BigDecimal schemeFee = BigDecimal.ZERO;
//    BigDecimal interchange = BigDecimal.ZERO;
//    BigDecimal chargebackReverseAmount = BigDecimal.ZERO;
//    BigDecimal refund = BigDecimal.ZERO;
//    BigDecimal refundReverse = BigDecimal.ZERO;
//    BigDecimal otherCredit = BigDecimal.ZERO;
//    BigDecimal otherDebit = BigDecimal.ZERO;
//    BigDecimal grossAmount = BigDecimal.ZERO;
//    BigDecimal errorAmount = BigDecimal.ZERO;
//    BigDecimal matchedAmount = BigDecimal.ZERO;
//    BigDecimal pendingAmount = BigDecimal.ZERO;
    private String validIPs = null;
    private String retrieveFromTempTable = null;
    private String acquirerLazada = null;


    public LazadaSettlementWindow() {
        Map lazadaSettlementProperties = (Map) SpringUtil.getBean("aydenSettlementProperties");
        //retrieve properties value
        validIPs = (String) lazadaSettlementProperties.get("ayden.validhost");
        retrieveFromTempTable = (String) lazadaSettlementProperties.get("retrieveFromTempTable");
        acquirerLazada = (String) lazadaSettlementProperties.get("acquirerLazada");

    }

    public void refresh() throws InterruptedException {
        logger.debug("Lazada Setllement Auto Window refresh()");
    }

    public void init() throws Exception {
        boolean sentEmail = false;
        TmtbNonBillableTxnCrcaReq req = null;

        try {
            String remoteIP = this.getHttpServletRequest().getRemoteAddr();
            //Todo
            String errorMsg;
            List<NonBillableBatchDto> batchList = new ArrayList();
            List<TmtbNonBillableBatch> processedBatchList = new ArrayList<TmtbNonBillableBatch>();
            List<VwBankAdviceIBS> commonSettlementDetailList;
            Map<String, Object[]> emailDetailMap = new LinkedHashMap();
            StringBuilder warningMsg = new StringBuilder(1000);


            logger.info("IP Address (Process Txn Window) " + remoteIP);
            if (validIPs.indexOf(remoteIP) == -1) {
                errorMsg = "Remote IP Address not found in the list of valid IP addresses";
                logger.error(errorMsg);
                return;
            }

            //here implement CRCA_REQ
            req = this.businessHelper.getNonBillableBusiness().searchTmtbCrcaRequest();
            //END

            if (req != null) {
                logger.error("runLazadaSettlementWindow is still running");
                return;
            } else {
                req = new TmtbNonBillableTxnCrcaReq("P");
                this.businessHelper.getNonBillableBusiness().processTmtbCrcaRequest(req, "Lazada Settlement");
                req = this.businessHelper.getNonBillableBusiness().searchTmtbCrcaRequest();
            }

            /*
             * [0] = consolidateList
             * [1] = retrievedList
             */
//            List<VwBankAdviceIBS> retrievedList = null;
            logger.info("retrieveFromTempTable value: " + retrieveFromTempTable);
            if(retrieveFromTempTable.equals(NonConfigurableConstants.BOOLEAN_YES)){
                logger.info("PERFORMANCE CHECK: Before retrieveCommonSettlementDetailReport");
//            List<List<VwBankAdviceIBS>> listOfLists = this.businessHelper.getNonBillableBusiness().retrieveCommonSettlementDetailReport();
//            commonSettlementDetailList = listOfLists.get(0);
//            retrievedList = listOfLists.get(1);

                commonSettlementDetailList = this.businessHelper.getNonBillableBusiness().retrieveCommonSettlementDetailReport();
                logger.info("PERFORMANCE CHECK: After retrieveCommonSettlementDetailReport");

                if (commonSettlementDetailList != null) {
                    logger.info("Begin processing::::::::::::::::");
                    logger.info("commonSettlementDetailList.size(): " + commonSettlementDetailList.size());
                } else {
                    logger.error("no settlement found for lazada");
                    req.setStatus("E");
                    this.businessHelper.getNonBillableBusiness().processTmtbCrcaRequest(req, "Lazada Settlement");
                    this.sendMatchingNotification(null, null);
                    return;
                }

                logger.info("Saving Records - Lazada Settlement");

                this.businessHelper.getNonBillableBusiness().processCommonSettlement(((List<VwBankAdvice>) (List<?>) commonSettlementDetailList), null);
                logger.info("End of Saving Records - Lazada Settlement");

            }
            //retrieve existing nonbillable batch
            SearchNonBillableBatchForm batchForm = this.buildNonBillableBatchSearchForm();
            
            //add payment's interfaceMappingValue to batchForm to process only Lazada records       
            batchForm.interfaceMappingValue = NonConfigurableConstants.PAYMENT_MODE_LAZADA;
            
            List<Object[]> batches = this.businessHelper.getNonBillableBusiness().searchNonBillableBatch2(batchForm);
                        
            if (batches.size() == 0) {
                logger.info("No batch found");
                req.setStatus("E");
                this.businessHelper.getNonBillableBusiness().processTmtbCrcaRequest(req, "Lazada Settlement");
                this.sendMatchingNotification(null, null);
                return;
            }

            for (Object[] batch : batches) {
                NonBillableBatchDto dto = new NonBillableBatchDto(batch);
                batchList.add(dto);
            }

            SearchNonBillableTxnsForm txnsForm = this.buildNonBillableTxnsSearchForm(batchList);
            List<TmtbNonBillableTxn> matchErrorTxns = this.businessHelper.getNonBillableBusiness().searchNonBillableTxns(txnsForm);

            //process matching now
            logger.info("PERFORMANCE CHECK: Before Process Matching");
            processedBatchList = processMatching(matchErrorTxns, emailDetailMap);
            logger.info("PERFORMANCE CHECK: After Process Matching");

            //for excess
            if(processedBatchList.size() > 0) {
                txnsForm = this.buildNonBillableMatchedTxnsSearchForm2(processedBatchList);
                List<Object[]> excessObjs = this.businessHelper.getNonBillableBusiness().searchExcessCrca(txnsForm);
                logger.info("PERFORMANCE CHECK: Before Excess Amount");
                processExcessAmount(excessObjs, emailDetailMap);
                logger.info("PERFORMANCE CHECK: After Excess Amount");
            }
            
            //for pending
            txnsForm = this.buildNonBillablePendingTxnsSearchForm(batchList);
            List<TmtbNonBillableTxn> pendingTxns = this.businessHelper.getNonBillableBusiness().searchNonBillableTxnsWithMatchingStatues(txnsForm); //this txn doesn't have crca
            logger.info("PERFORMANCE CHECK: Before Pending Error");
            processAllPendingError(pendingTxns, emailDetailMap); //This pendingTxns includes txn that isn't pull by the pendingErrorTxns
            logger.info("PERFORMANCE CHECK: After Pending Error");

            //for completeness check
            txnsForm = this.buildNonBillableMatchedTxnsSearchForm(batchList);
            List<TmtbNonBillableTxn> allTxns = this.businessHelper.getNonBillableBusiness().searchNonBillableTxnsWithMatchingStatues(txnsForm); //this txn doesn't have crca
            logger.info("PERFORMANCE CHECK: Before Completeness Check");
            processCompletenessCheck(allTxns, batchList, emailDetailMap);
            logger.info("PERFORMANCE CHECK: After Completeness Check");

            //to transfer to new batch after matching is done
            if(processedBatchList.size() > 0) {
                txnsForm = this.buildNonBillablePendingTxnsSearchForm(batchList);
                List<TmtbNonBillableTxn> pendingNotFoundInCrcaTxns = this.businessHelper.getNonBillableBusiness().searchNonBillablePendingTxnsNotFoundInCrca(txnsForm);
                txnsForm = this.buildNonBillableMatchErrorTxnsSearchForm(processedBatchList);
                matchErrorTxns = this.businessHelper.getNonBillableBusiness().searchNonBillableTxns(txnsForm);
                logger.info("PERFORMANCE CHECK: Before Transfer to New Batch");
                transferToNewBatch(matchErrorTxns, pendingNotFoundInCrcaTxns,emailDetailMap);
                logger.info("PERFORMANCE CHECK: After Transfer to New Batch");
            }

            //process chargeback/refund txn
            txnsForm = this.buildNonBillableChargebackRefundedTxnsSearchForm(batchList);
            List<TmtbNonBillableTxn> chargebackRefundedTxns = this.businessHelper.getNonBillableBusiness().searchNonBillableChargebackRefundedTxns(txnsForm);
            processChargebackRefunded(chargebackRefundedTxns,emailDetailMap);
            
            this.sendMatchingNotification(emailDetailMap, warningMsg);
            sentEmail = true;

            req.setStatus("C");
            this.businessHelper.getNonBillableBusiness().processTmtbCrcaRequest(req, "Lazada Settlement");
        } catch (Exception e) {
            logger.error("Catched Error: " + e.getMessage());
            logger.error("Catched Error: " + e.getCause());
            e.printStackTrace();

            req.setStatus("E");
            this.businessHelper.getNonBillableBusiness().processTmtbCrcaRequest(req, "Lazada Settlement");
            if (!sentEmail) {
            	this.sendMatchingNotification(null, null);
            }
        }
        //chargeback check the update button. no need as long as the status is chargeback
        //TODO: 1) Report
        //        2) Amex Adjustment and Chargeback


    }

    public SearchNonBillableTxnsForm buildNonBillableTxnsSearchForm(List<NonBillableBatchDto> batchList) {
        SearchNonBillableTxnsForm form = new SearchNonBillableTxnsForm();
        List<String> result = new ArrayList<String>();
        result.add(NonConfigurableConstants.AYDEN_MATCHING_STATUS_PENDING);
        result.add(NonConfigurableConstants.AYDEN_MATCHING_STATUS_ERROR);

        form.matchingStatuses = result;

        result = new ArrayList<String>();
        result.add(NonConfigurableConstants.AMEX_RECORD_STATUS_TXNPRICING); //to exclude this transaction
        result.add(NonConfigurableConstants.AMEX_RECORD_STATUS_CHARGEBACK); //to exclude this transaction
        result.add(NonConfigurableConstants.AYDEN_RECORD_STATUS_CHARGEBACK);
        result.add(NonConfigurableConstants.AYDEN_RECORD_STATUS_SECOND_CHARGEBACK);
        result.add(NonConfigurableConstants.AYDEN_RECORD_STATUS_CHARGEBACK_REVERSE);
        result.add(NonConfigurableConstants.AYDEN_RECORD_STATUS_REFUNDED);
        result.add(NonConfigurableConstants.AYDEN_RECORD_STATUS_REFUNDED_REVERSE);
        result.add(NonConfigurableConstants.AYDEN_RECORD_STATUS_MERCHANT_PAYOUT);


        form.recordType = result;

        result = new ArrayList<String>();
        result.add(NonConfigurableConstants.FROM_LAZADA);

        form.sourceList = result;

        form.addTmtbNonBillableBatches(batchList);

        return form;
    }

    public SearchNonBillableTxnsForm buildNonBillableChargebackRefundedTxnsSearchForm(List<NonBillableBatchDto> batchList) {
        SearchNonBillableTxnsForm form = new SearchNonBillableTxnsForm();
        List<String> result = new ArrayList<String>();
        result.add(NonConfigurableConstants.AYDEN_MATCHING_STATUS_MATCHED);
        result.add(NonConfigurableConstants.AYDEN_MATCHING_STATUS_ERROR);


        form.matchingStatuses = result;

        result = new ArrayList<String>();
        result.add(NonConfigurableConstants.AMEX_RECORD_STATUS_CHARGEBACK); //to include this transaction
        result.add(NonConfigurableConstants.AYDEN_RECORD_STATUS_REFUNDED_REVERSE); //to include this transaction
        result.add(NonConfigurableConstants.AYDEN_RECORD_STATUS_REFUNDED); //to include this transaction
        result.add(NonConfigurableConstants.AYDEN_RECORD_STATUS_SECOND_CHARGEBACK); //to include this transaction
        result.add(NonConfigurableConstants.AYDEN_RECORD_STATUS_CHARGEBACK_REVERSE); //to include this transaction


        form.recordType = result;

        result = new ArrayList<String>();
        result.add(NonConfigurableConstants.FROM_LAZADA);

        form.sourceList = result;

        form.addTmtbNonBillableBatches(batchList);

        return form;

    }

    public SearchNonBillableTxnsForm buildNonBillableMatchErrorTxnsSearchForm(List<TmtbNonBillableBatch> batchList) {
        SearchNonBillableTxnsForm form = new SearchNonBillableTxnsForm();
        List<String> result = new ArrayList<String>();
        result.add(NonConfigurableConstants.AYDEN_MATCHING_STATUS_MATCHED);
        result.add(NonConfigurableConstants.AYDEN_MATCHING_STATUS_ERROR);

        form.matchingStatuses = result;

        //required to put FROM_LAZADA because it is used at the hibernate level
        result = new ArrayList<String>();
        result.add(NonConfigurableConstants.FROM_LAZADA);

        form.sourceList = result;

        form.setTxnBatches(batchList);
        form.isReport = false;

        return form;
    }

    public SearchNonBillableTxnsForm buildNonBillablePendingTxnsSearchForm(List<NonBillableBatchDto> batchList) {
        SearchNonBillableTxnsForm form = new SearchNonBillableTxnsForm();
        List<String> result = new ArrayList<String>();
        result.add(NonConfigurableConstants.AYDEN_MATCHING_STATUS_PENDING);

        form.matchingStatuses = result;

        result = new ArrayList<String>();
        result.add(NonConfigurableConstants.AMEX_RECORD_STATUS_TXNPRICING); //to exclude this transaction
        result.add(NonConfigurableConstants.AYDEN_RECORD_STATUS_MERCHANT_PAYOUT);

        form.recordType = result;

        result = new ArrayList<String>();
        result.add(acquirerLazada); //to include this acquirer

        form.acquirerList = result;

        form.addTmtbNonBillableBatches(batchList);
        form.isReport = false;

        return form;
    }

    public SearchNonBillableTxnsForm buildNonBillableMatchedTxnsSearchForm(List<NonBillableBatchDto> batchList) { //toDo merge with Lazada
        SearchNonBillableTxnsForm form = new SearchNonBillableTxnsForm();
        List<String> result = new ArrayList<String>();
        result.add(NonConfigurableConstants.AYDEN_MATCHING_STATUS_ERROR);
        result.add(NonConfigurableConstants.AYDEN_MATCHING_STATUS_MATCHED);

        form.matchingStatuses = result;

        result = new ArrayList<String>();
        result.add(NonConfigurableConstants.AMEX_RECORD_STATUS_TXNPRICING); //to exclude this transaction
        result.add(NonConfigurableConstants.AYDEN_RECORD_STATUS_MERCHANT_PAYOUT); //to exclude this transaction

        form.recordType = result;

        result = new ArrayList<String>();
        result.add(acquirerLazada); //to include this acquirer

        form.acquirerList = result;

        form.addTmtbNonBillableBatches(batchList);
        form.isReport = false;

        return form;

    }

    public SearchNonBillableTxnsForm buildNonBillableMatchedTxnsSearchForm2(List<TmtbNonBillableBatch> batchList) {
        SearchNonBillableTxnsForm form = new SearchNonBillableTxnsForm();
        List<String> result = new ArrayList<String>();
        result.add(NonConfigurableConstants.AYDEN_MATCHING_STATUS_ERROR);
        result.add(NonConfigurableConstants.AYDEN_MATCHING_STATUS_MATCHED);

        form.matchingStatuses = result;

        result = new ArrayList<String>();
        result.add(NonConfigurableConstants.AMEX_RECORD_STATUS_TXNPRICING); //to exclude this transaction
        result.add(NonConfigurableConstants.AYDEN_RECORD_STATUS_MERCHANT_PAYOUT); //to exclude this transaction

        form.recordType = result;

        result = new ArrayList<String>();
        result.add(NonConfigurableConstants.FROM_LAZADA);

        form.sourceList = result;

        form.setTxnBatches(batchList);
        form.isReport = false;

        return form;
    }

}