package com.cdgtaxi.ibs.common.model;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;

public class VwBankAdviceIBS extends VwBankAdvice{
    long crcaId;

    //ayden
    String pspRefNo;
    String recordType;
    String submissionMerchantId;
    String batchCode;
    String paymentMethod;
    BigDecimal grossAmount;
    BigDecimal grossDebit;
    BigDecimal grossCredit;
    BigDecimal netDebit;
    BigDecimal netCredit;
    BigDecimal commission;
    BigDecimal markup;
    BigDecimal schemeFee;
    BigDecimal interchange;
    //amex
    Date paymentDate;
    String paymentCurrency;
    BigDecimal transactionAmount;
    Date transactionDate;
    String feeCode;
    BigDecimal feeAmount;
    String discountRate;
    BigDecimal discountAmount;
    String chargebackNo;
    String chargebackReasonCode;
    String chargebackReasonDescription;
    BigDecimal serviceFeeAmount;
    BigDecimal taxAmount;
    BigDecimal netAmount;
    String serviceFeeRate;
    String adjustmentNo;
    String adjustmentReasonCode;
    String adjustmentReasonDescription;
    String status;
    String source;
	String details;
	String transactionNo;
	String paymentReference;
	String fileName;
	Date uploadDate;
    Timestamp createdDt;
    String createdBy;
    Timestamp modifiedDt;
    Timestamp updatedDt;
    String updatedBy;
    
    public VwBankAdviceIBS() {
    }
    
    public VwBankAdviceIBS(long crcaId, String pspRefNo, String recordType, String submissionMerchantId,
			String batchCode, String paymentMethod, BigDecimal grossAmount, BigDecimal grossDebit,
			BigDecimal grossCredit, BigDecimal netDebit, BigDecimal netCredit, BigDecimal commission, BigDecimal markup,
			BigDecimal schemeFee, BigDecimal interchange, Date paymentDate, String paymentCurrency,
			BigDecimal transactionAmount, Date transactionDate, String feeCode, BigDecimal feeAmount,
			String discountRate, BigDecimal discountAmount, String chargebackNo, String chargebackReasonCode,
			String chargebackReasonDescription, BigDecimal serviceFeeAmount, BigDecimal taxAmount, BigDecimal netAmount,
			String serviceFeeRate, String adjustmentNo, String adjustmentReasonCode, String adjustmentReasonDescription,
			String status, String fileName, Date uploadDate, String source, String details, String transactionNo, String paymentReference,
		   Timestamp createdDt, Timestamp modifiedDt, Timestamp updatedDt, String updatedBy, String retrievedFlag, Date retrievedDate) {

    	this.crcaId = crcaId;
		this.pspRefNo = pspRefNo;
		this.recordType = recordType;
		this.submissionMerchantId = submissionMerchantId;
		this.batchCode = batchCode;
		this.paymentMethod = paymentMethod;
		this.grossAmount = grossAmount;
		this.grossDebit = grossDebit;
		this.grossCredit = grossCredit;
		this.netDebit = netDebit;
		this.netCredit = netCredit;
		this.commission = commission;
		this.markup = markup;
		this.schemeFee = schemeFee;
		this.interchange = interchange;
		this.paymentDate = paymentDate;
		this.paymentCurrency = paymentCurrency;
		this.transactionAmount = transactionAmount;
		this.transactionDate = transactionDate;
		this.feeCode = feeCode;
		this.feeAmount = feeAmount;
		this.discountRate = discountRate;
		this.discountAmount = discountAmount;
		this.chargebackNo = chargebackNo;
		this.chargebackReasonCode = chargebackReasonCode;
		this.chargebackReasonDescription = chargebackReasonDescription;
		this.serviceFeeAmount = serviceFeeAmount;
		this.taxAmount = taxAmount;
		this.netAmount = netAmount;
		this.serviceFeeRate = serviceFeeRate;
		this.adjustmentNo = adjustmentNo;
		this.adjustmentReasonCode = adjustmentReasonCode;
		this.adjustmentReasonDescription = adjustmentReasonDescription;
		this.status = status;
		this.fileName = fileName;
		this.uploadDate = uploadDate;
		this.source = source;
		this.details = details;
		this.transactionNo = transactionNo;
		this.paymentReference = paymentReference;
		this.createdDt = createdDt;
		this.createdBy = createdBy;
		this.modifiedDt = modifiedDt;
		this.updatedDt = updatedDt;
		this.updatedBy = updatedBy;
        this.retrievedFlag = retrievedFlag;
        this.retrievedDate = retrievedDate;
	}

	public long getCrcaId() {
		return crcaId;
	}

	public void setCrcaId(long crcaId) {
		this.crcaId = crcaId;
	}

	public String getPspRefNo() {
		return pspRefNo;
	}

	public void setPspRefNo(String pspRefNo) {
		this.pspRefNo = pspRefNo;
	}

	public String getRecordType() {
		return recordType;
	}

	public void setRecordType(String recordType) {
		this.recordType = recordType;
	}

	public String getSubmissionMerchantId() {
		return submissionMerchantId;
	}

	public void setSubmissionMerchantId(String submissionMerchantId) {
		this.submissionMerchantId = submissionMerchantId;
	}

	public String getBatchCode() {
		return batchCode;
	}

	public void setBatchCode(String batchCode) {
		this.batchCode = batchCode;
	}

	public String getPaymentMethod() {
		return paymentMethod;
	}

	public void setPaymentMethod(String paymentMethod) {
		this.paymentMethod = paymentMethod;
	}

	public BigDecimal getGrossAmount() {
		return grossAmount;
	}

	public void setGrossAmount(BigDecimal grossAmount) {
		this.grossAmount = grossAmount;
	}

	public BigDecimal getGrossDebit() {
		return grossDebit;
	}

	public void setGrossDebit(BigDecimal grossDebit) {
		this.grossDebit = grossDebit;
	}

	public BigDecimal getGrossCredit() {
		return grossCredit;
	}

	public void setGrossCredit(BigDecimal grossCredit) {
		this.grossCredit = grossCredit;
	}

	public BigDecimal getNetDebit() {
		return netDebit;
	}

	public void setNetDebit(BigDecimal netDebit) {
		this.netDebit = netDebit;
	}

	public BigDecimal getNetCredit() {
		return netCredit;
	}

	public void setNetCredit(BigDecimal netCredit) {
		this.netCredit = netCredit;
	}

	public BigDecimal getCommission() {
		return commission;
	}

	public void setCommission(BigDecimal commission) {
		this.commission = commission;
	}

	public BigDecimal getMarkup() {
		return markup;
	}

	public void setMarkup(BigDecimal markup) {
		this.markup = markup;
	}

	public BigDecimal getSchemeFee() {
		return schemeFee;
	}

	public void setSchemeFee(BigDecimal schemeFee) {
		this.schemeFee = schemeFee;
	}

	public BigDecimal getInterchange() {
		return interchange;
	}

	public void setInterchange(BigDecimal interchange) {
		this.interchange = interchange;
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

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public Timestamp getCreatedDt() {
		return createdDt;
	}

	public void setCreatedDt(Timestamp createdDt) {
		this.createdDt = createdDt;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public Timestamp getModifiedDt() {
		return modifiedDt;
	}

	public void setModifiedDt(Timestamp modifiedDt) {
		this.modifiedDt = modifiedDt;
	}

    public Timestamp getUpdatedDt() {
		return updatedDt;
	}

	public void setUpdatedDt(Timestamp updatedDt) {
		this.updatedDt = updatedDt;
	}

	public String getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
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

	public String getDetails() {
		return details;
	}

	public void setDetails(String details) {
		this.details = details;
	}

	public String getTransactionNo() {
		return transactionNo;
	}

	public void setTransactionNo(String transactionNo) {
		this.transactionNo = transactionNo;
	}

	public String getPaymentReference() {
		return paymentReference;
	}

	public void setPaymentReference(String paymentReference) {
		this.paymentReference = paymentReference;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public Date getUploadDate() {
		return uploadDate;
	}

	public void setUploadDate(Date uploadDate) {
		this.uploadDate = uploadDate;
	}
}
