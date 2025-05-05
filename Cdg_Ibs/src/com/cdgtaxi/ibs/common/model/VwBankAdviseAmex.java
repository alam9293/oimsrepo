package com.cdgtaxi.ibs.common.model;

import java.math.BigDecimal;
import java.sql.Date;

public class VwBankAdviseAmex extends VwBankAdvice{

    BigDecimal id;
    String recordType;
    String payeeMerchantId;
    String accountType;
    String paymentNo;
    String fileName;
    String invoiceNo;
    BigDecimal submissionGrossAmount;
    Date uploadDate;
    Date paymentDate;
    String paymentCurrency;
    String pspRefNo;
    String cardmemberAccountNo;
    BigDecimal transactionAmount;
    Date transactionDate;
    String transactionId;
    String approvalCode;
    String acquirerRefNo;
    String transactionRejectedInd;
    String feeCode;
    BigDecimal feeAmount;
    String discountRate;
    BigDecimal discountAmount;
    Date businessSubmissionDate;
    String submissionInvoiceNo;
    String submissionCurrency;
    String chargebackNo;
    String chargebackReasonCode;
    String chargebackReasonDescription;
    BigDecimal grossAmount;
    BigDecimal serviceFeeAmount;
    BigDecimal taxAmount;
    BigDecimal netAmount;
    String serviceFeeRate;
    String batchCode;
    String billCode;
    String adjustmentNo;
    String adjustmentReasonCode;
    String adjustmentReasonDescription;
    String retrievedFlag;
    Date retrievedDate;

    public VwBankAdviseAmex() {
    }

    public VwBankAdviseAmex(BigDecimal id, String recordType, String payeeMerchantId, String accountType, String paymentNo, String fileName, String invoiceNo, BigDecimal submissionGrossAmount, Date uploadDate, Date paymentDate, String paymentCurrency, String pspRefNo, String cardmemberAccountNo, BigDecimal transactionAmount, Date transactionDate, String transactionId, String approvalCode, String acquirerRefNo, String transactionRejectedInd, String feeCode, BigDecimal feeAmount, String discountRate, BigDecimal discountAmount, Date businessSubmissionDate, String submissionInvoiceNo, String submissionCurrency, String chargebackNo, String chargebackReasonCode, String chargebackReasonDescription, BigDecimal grossAmount, BigDecimal serviceFeeAmount, BigDecimal taxAmount, BigDecimal netAmount, String serviceFeeRate, String batchCode, String billCode, String adjustmentNo, String adjustmentReasonCode, String adjustmentReasonDescription, String retrievedFlag, Date retrievedDate) {
        this.id = id;
        this.recordType = recordType;
        this.payeeMerchantId = payeeMerchantId;
        this.accountType = accountType;
        this.paymentNo = paymentNo;
        this.fileName = fileName;
        this.invoiceNo = invoiceNo;
        this.submissionGrossAmount = submissionGrossAmount;
        this.uploadDate = uploadDate;
        this.paymentDate = paymentDate;
        this.paymentCurrency = paymentCurrency;
        this.pspRefNo = pspRefNo;
        this.cardmemberAccountNo = cardmemberAccountNo;
        this.transactionAmount = transactionAmount;
        this.transactionDate = transactionDate;
        this.transactionId = transactionId;
        this.approvalCode = approvalCode;
        this.acquirerRefNo = acquirerRefNo;
        this.transactionRejectedInd = transactionRejectedInd;
        this.feeCode = feeCode;
        this.feeAmount = feeAmount;
        this.discountRate = discountRate;
        this.discountAmount = discountAmount;
        this.businessSubmissionDate = businessSubmissionDate;
        this.submissionInvoiceNo = submissionInvoiceNo;
        this.submissionCurrency = submissionCurrency;
        this.chargebackNo = chargebackNo;
        this.chargebackReasonCode = chargebackReasonCode;
        this.chargebackReasonDescription = chargebackReasonDescription;
        this.grossAmount = grossAmount;
        this.serviceFeeAmount = serviceFeeAmount;
        this.taxAmount = taxAmount;
        this.netAmount = netAmount;
        this.serviceFeeRate = serviceFeeRate;
        this.batchCode = batchCode;
        this.billCode = billCode;
        this.adjustmentNo = adjustmentNo;
        this.adjustmentReasonCode = adjustmentReasonCode;
        this.adjustmentReasonDescription = adjustmentReasonDescription;
        this.retrievedFlag = retrievedFlag;
        this.retrievedDate = retrievedDate;
    }

    public BigDecimal getId() {
        return id;
    }

    public void setId(BigDecimal id) {
        this.id = id;
    }

    public String getRecordType() {
        return recordType;
    }

    public void setRecordType(String recordType) {
        this.recordType = recordType;
    }

    public String getPayeeMerchantId() {
        return payeeMerchantId;
    }

    public void setPayeeMerchantId(String payeeMerchantId) {
        this.payeeMerchantId = payeeMerchantId;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public String getPaymentNo() {
        return paymentNo;
    }

    public void setPaymentNo(String paymentNo) {
        this.paymentNo = paymentNo;
    }

    public Date getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(Date paymentDate) {
        this.paymentDate = paymentDate;
    }

    public String getPaymentCurrency() {
        return paymentCurrency;
    }

    public void setPaymentCurrency(String paymentCurrency) {
        this.paymentCurrency = paymentCurrency;
    }

    public String getPspRefNo() {
        return pspRefNo;
    }

    public void setPspRefNo(String pspRefNo) {
        this.pspRefNo = pspRefNo;
    }

    public String getCardmemberAccountNo() {
        return cardmemberAccountNo;
    }

    public void setCardmemberAccountNo(String cardmemberAccountNo) {
        this.cardmemberAccountNo = cardmemberAccountNo;
    }

    public BigDecimal getTransactionAmount() {
        return transactionAmount;
    }

    public void setTransactionAmount(BigDecimal transactionAmount) {
        this.transactionAmount = transactionAmount;
    }

    public Date getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(Date transactionDate) {
        this.transactionDate = transactionDate;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getApprovalCode() {
        return approvalCode;
    }

    public void setApprovalCode(String approvalCode) {
        this.approvalCode = approvalCode;
    }

    public String getAcquirerRefNo() {
        return acquirerRefNo;
    }

    public void setAcquirerRefNo(String acquirerRefNo) {
        this.acquirerRefNo = acquirerRefNo;
    }

    public String getTransactionRejectedInd() {
        return transactionRejectedInd;
    }

    public void setTransactionRejectedInd(String transactionRejectedInd) {
        this.transactionRejectedInd = transactionRejectedInd;
    }

    public String getFeeCode() {
        return feeCode;
    }

    public void setFeeCode(String feeCode) {
        this.feeCode = feeCode;
    }

    public BigDecimal getFeeAmount() {
        return feeAmount;
    }

    public void setFeeAmount(BigDecimal feeAmount) {
        this.feeAmount = feeAmount;
    }

    public String getDiscountRate() {
        return discountRate;
    }

    public void setDiscountRate(String discountRate) {
        this.discountRate = discountRate;
    }

    public BigDecimal getDiscountAmount() {
        return discountAmount;
    }

    public void setDiscountAmount(BigDecimal discountAmount) {
        this.discountAmount = discountAmount;
    }

    public Date getBusinessSubmissionDate() {
        return businessSubmissionDate;
    }

    public void setBusinessSubmissionDate(Date businessSubmissionDate) {
        this.businessSubmissionDate = businessSubmissionDate;
    }

    public String getSubmissionInvoiceNo() {
        return submissionInvoiceNo;
    }

    public void setSubmissionInvoiceNo(String submissionInvoiceNo) {
        this.submissionInvoiceNo = submissionInvoiceNo;
    }

    public String getSubmissionCurrency() {
        return submissionCurrency;
    }

    public void setSubmissionCurrency(String submissionCurrency) {
        this.submissionCurrency = submissionCurrency;
    }

    public String getChargebackNo() {
        return chargebackNo;
    }

    public void setChargebackNo(String chargebackNo) {
        this.chargebackNo = chargebackNo;
    }

    public String getChargebackReasonCode() {
        return chargebackReasonCode;
    }

    public void setChargebackReasonCode(String chargebackReasonCode) {
        this.chargebackReasonCode = chargebackReasonCode;
    }

    public String getChargebackReasonDescription() {
        return chargebackReasonDescription;
    }

    public void setChargebackReasonDescription(String chargebackReasonDescription) {
        this.chargebackReasonDescription = chargebackReasonDescription;
    }

    public BigDecimal getGrossAmount() {
        return grossAmount;
    }

    public void setGrossAmount(BigDecimal grossAmount) {
        this.grossAmount = grossAmount;
    }

    public BigDecimal getServiceFeeAmount() {
        return serviceFeeAmount;
    }

    public void setServiceFeeAmount(BigDecimal serviceFeeAmount) {
        this.serviceFeeAmount = serviceFeeAmount;
    }

    public BigDecimal getTaxAmount() {
        return taxAmount;
    }

    public void setTaxAmount(BigDecimal taxAmount) {
        this.taxAmount = taxAmount;
    }

    public BigDecimal getNetAmount() {
        return netAmount;
    }

    public void setNetAmount(BigDecimal netAmount) {
        this.netAmount = netAmount;
    }

    public String getServiceFeeRate() {
        return serviceFeeRate;
    }

    public void setServiceFeeRate(String serviceFeeRate) {
        this.serviceFeeRate = serviceFeeRate;
    }

    public String getBatchCode() {
        return batchCode;
    }

    public void setBatchCode(String batchCode) {
        this.batchCode = batchCode;
    }

    public String getBillCode() {
        return billCode;
    }

    public void setBillCode(String billCode) {
        this.billCode = billCode;
    }

    public String getAdjustmentNo() {
        return adjustmentNo;
    }

    public void setAdjustmentNo(String adjustmentNo) {
        this.adjustmentNo = adjustmentNo;
    }

    public String getAdjustmentReasonCode() {
        return adjustmentReasonCode;
    }

    public void setAdjustmentReasonCode(String adjustmentReasonCode) {
        this.adjustmentReasonCode = adjustmentReasonCode;
    }

    public String getAdjustmentReasonDescription() {
        return adjustmentReasonDescription;
    }

    public void setAdjustmentReasonDescription(String adjustmentReasonDescription) {
        this.adjustmentReasonDescription = adjustmentReasonDescription;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getInvoiceNo() {
        return invoiceNo;
    }

    public void setInvoiceNo(String invoiceNo) {
        this.invoiceNo = invoiceNo;
    }

    public BigDecimal getSubmissionGrossAmount() {
        return submissionGrossAmount;
    }

    public void setSubmissionGrossAmount(BigDecimal submissionGrossAmount) {
        this.submissionGrossAmount = submissionGrossAmount;
    }

    public Date getUploadDate() {
        return uploadDate;
    }

    public void setUploadDate(Date uploadDate) {
        this.uploadDate = uploadDate;
    }

    public String getRetrievedFlag() {
        return retrievedFlag;
    }

    public void setRetrievedFlag(String retrievedFlag) {
        this.retrievedFlag = retrievedFlag;
    }

    public Date getRetrievedDate() {
        return retrievedDate;
    }

    public void setRetrievedDate(Date retrievedDate) {
        this.retrievedDate = retrievedDate;
    }
}
