package com.cdgtaxi.ibs.common.model;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;

public class TmtbNonBillableTxnCrca extends BaseEntity{  //extends BaseEntity {

    private long crcaId;

    //ayden
    private String pspRefNo;
    private String recordType;
    private String submissionMerchantId;
    private String batchCode;
    private String paymentMethod;
    private BigDecimal grossAmount;
    private BigDecimal grossDebit;
    private BigDecimal grossCredit;
    private BigDecimal netDebit;
    private BigDecimal netCredit;
    private BigDecimal commission;
    private BigDecimal markup;
    private BigDecimal schemeFee;
    private BigDecimal interchange;
    //amex
    private Date paymentDate;
    private String paymentCurrency;
    private BigDecimal transactionAmount;
    private Date transactionDate;
    private String feeCode;
    private BigDecimal feeAmount;
    private String discountRate;
    private BigDecimal discountAmount;
    private String chargebackNo;
    private String chargebackReasonCode;
    private String chargebackReasonDescription;
    private BigDecimal serviceFeeAmount;
    private BigDecimal taxAmount;
    private BigDecimal netAmount;
    private String serviceFeeRate;
    private String adjustmentNo;
    private String adjustmentReasonCode;
    private String adjustmentReasonDescription;
    private String fileName;
    private Date uploadDate;
    //Lazada
    private String details;
    private String transactionNo;
    private String paymentReference;
    private String status;
//    private TmtbNonBillableTxn tmtbNonBillableTxn1;
//    private TmtbNonBillableTxn tmtbNonBillableTxn2;


    private String source;

    private Timestamp createdDt;
    private Timestamp modifiedDt;

    public TmtbNonBillableTxnCrca(String pspRefNo) {
        this.pspRefNo = pspRefNo;
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

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public Timestamp getCreatedDt() {
        return createdDt;
    }

    public Timestamp getModifiedDt() {
        return modifiedDt;
    }

    public void setModifiedDt(Timestamp modifiedDt) {
        this.modifiedDt = modifiedDt;
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


    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public TmtbNonBillableTxnCrca(){

    }

    public TmtbNonBillableTxnCrca(String pspRefNo, String recordType, String submissionMerchantId, String batchCode, String paymentMethod, BigDecimal grossAmount, BigDecimal grossDebit, BigDecimal grossCredit, BigDecimal netDebit, BigDecimal netCredit, BigDecimal commission, BigDecimal markup, BigDecimal schemeFee, BigDecimal interchange, Date paymentDate, String paymentCurrency, BigDecimal transactionAmount, Date transactionDate, String feeCode, BigDecimal feeAmount, String discountRate, BigDecimal discountAmount, String chargebackNo, String chargebackReasonCode, String chargebackReasonDescription, BigDecimal serviceFeeAmount, BigDecimal taxAmount, BigDecimal netAmount, String serviceFeeRate, String adjustmentNo, String adjustmentReasonCode, String adjustmentReasonDescription, String fileName, Date uploadDate, String detail, String transactionNo, String paymentReference, String status, String source) {
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
        this.fileName = fileName;
        this.uploadDate = uploadDate;
        this.details = detail;
        this.transactionNo = transactionNo;
        this.paymentReference = paymentReference;
        this.status = status;
        this.source = source;
    }

    @Override
    public String toString() {
        return "TmtbNonBillableTxnCrca{" +
                "pspRefNo='" + pspRefNo + '\'' +
                ", recordType='" + recordType + '\'' +
                ", submissionMerchantId='" + submissionMerchantId + '\'' +
                ", batchCode='" + batchCode + '\'' +
                ", paymentMethod='" + paymentMethod + '\'' +
                ", grossAmount=" + grossAmount +
                ", grossDebit=" + grossDebit +
                ", grossCredit=" + grossCredit +
                ", netDebit=" + netDebit +
                ", netCredit=" + netCredit +
                ", commission=" + commission +
                ", markup=" + markup +
                ", schemeFee=" + schemeFee +
                ", interchange=" + interchange +
                ", paymentDate=" + paymentDate +
                ", paymentCurrency='" + paymentCurrency + '\'' +
                ", transactionAmount=" + transactionAmount +
                ", transactionDate=" + transactionDate +
                ", feeCode='" + feeCode + '\'' +
                ", feeAmount=" + feeAmount +
                ", discountRate='" + discountRate + '\'' +
                ", discountAmount=" + discountAmount +
                ", chargebackNo='" + chargebackNo + '\'' +
                ", chargebackReasonCode='" + chargebackReasonCode + '\'' +
                ", chargebackReasonDescription='" + chargebackReasonDescription + '\'' +
                ", serviceFeeAmount=" + serviceFeeAmount +
                ", taxAmount=" + taxAmount +
                ", netAmount=" + netAmount +
                ", serviceFeeRate='" + serviceFeeRate + '\'' +
                ", adjustmentNo='" + adjustmentNo + '\'' +
                ", adjustmentReasonCode='" + adjustmentReasonCode + '\'' +
                ", adjustmentReasonDescription='" + adjustmentReasonDescription + '\'' +
                ", fileName='" + fileName + '\'' +
                ", uploadDate=" + uploadDate +
                ", detail='" + details + '\'' +
                ", transactionNo='" + transactionNo + '\'' +
                ", paymentReference='" + paymentReference + '\'' +
                ", paidStatus='" + status + '\'' +
                ", source='" + source + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TmtbNonBillableTxnCrca that = (TmtbNonBillableTxnCrca) o;

        if (crcaId != that.crcaId) return false;
        if (pspRefNo != null ? !pspRefNo.equals(that.pspRefNo) : that.pspRefNo != null) return false;
        if (recordType != null ? !recordType.equals(that.recordType) : that.recordType != null) return false;
        if (submissionMerchantId != null ? !submissionMerchantId.equals(that.submissionMerchantId) : that.submissionMerchantId != null)
            return false;
        if (batchCode != null ? !batchCode.equals(that.batchCode) : that.batchCode != null) return false;
        if (paymentMethod != null ? !paymentMethod.equals(that.paymentMethod) : that.paymentMethod != null)
            return false;
        if (grossAmount != null ? !grossAmount.equals(that.grossAmount) : that.grossAmount != null) return false;
        if (grossDebit != null ? !grossDebit.equals(that.grossDebit) : that.grossDebit != null) return false;
        if (grossCredit != null ? !grossCredit.equals(that.grossCredit) : that.grossCredit != null) return false;
        if (netDebit != null ? !netDebit.equals(that.netDebit) : that.netDebit != null) return false;
        if (netCredit != null ? !netCredit.equals(that.netCredit) : that.netCredit != null) return false;
        if (commission != null ? !commission.equals(that.commission) : that.commission != null) return false;
        if (markup != null ? !markup.equals(that.markup) : that.markup != null) return false;
        if (schemeFee != null ? !schemeFee.equals(that.schemeFee) : that.schemeFee != null) return false;
        if (interchange != null ? !interchange.equals(that.interchange) : that.interchange != null) return false;
        if (paymentDate != null ? !paymentDate.equals(that.paymentDate) : that.paymentDate != null) return false;
        if (paymentCurrency != null ? !paymentCurrency.equals(that.paymentCurrency) : that.paymentCurrency != null)
            return false;
        if (transactionAmount != null ? !transactionAmount.equals(that.transactionAmount) : that.transactionAmount != null)
            return false;
        if (transactionDate != null ? !transactionDate.equals(that.transactionDate) : that.transactionDate != null)
            return false;
        if (feeCode != null ? !feeCode.equals(that.feeCode) : that.feeCode != null) return false;
        if (feeAmount != null ? !feeAmount.equals(that.feeAmount) : that.feeAmount != null) return false;
        if (discountRate != null ? !discountRate.equals(that.discountRate) : that.discountRate != null) return false;
        if (discountAmount != null ? !discountAmount.equals(that.discountAmount) : that.discountAmount != null)
            return false;
        if (chargebackNo != null ? !chargebackNo.equals(that.chargebackNo) : that.chargebackNo != null) return false;
        if (chargebackReasonCode != null ? !chargebackReasonCode.equals(that.chargebackReasonCode) : that.chargebackReasonCode != null)
            return false;
        if (chargebackReasonDescription != null ? !chargebackReasonDescription.equals(that.chargebackReasonDescription) : that.chargebackReasonDescription != null)
            return false;
        if (serviceFeeAmount != null ? !serviceFeeAmount.equals(that.serviceFeeAmount) : that.serviceFeeAmount != null)
            return false;
        if (taxAmount != null ? !taxAmount.equals(that.taxAmount) : that.taxAmount != null) return false;
        if (netAmount != null ? !netAmount.equals(that.netAmount) : that.netAmount != null) return false;
        if (serviceFeeRate != null ? !serviceFeeRate.equals(that.serviceFeeRate) : that.serviceFeeRate != null)
            return false;
        if (adjustmentNo != null ? !adjustmentNo.equals(that.adjustmentNo) : that.adjustmentNo != null) return false;
        if (adjustmentReasonCode != null ? !adjustmentReasonCode.equals(that.adjustmentReasonCode) : that.adjustmentReasonCode != null)
            return false;
        if (adjustmentReasonDescription != null ? !adjustmentReasonDescription.equals(that.adjustmentReasonDescription) : that.adjustmentReasonDescription != null)
            return false;
        if (source != null ? !source.equals(that.source) : that.source != null) return false;
        if (createdDt != null ? !createdDt.equals(that.createdDt) : that.createdDt != null) return false;
        if (fileName != null ? !fileName.equals(that.fileName) : that.fileName != null) return false;
        if (details != null ? !details.equals(that.details) : that.details != null) return false;
        if (transactionNo != null ? !transactionNo.equals(that.transactionNo) : that.transactionNo != null) return false;
        if (status != null ? !status.equals(that.status) : that.status != null) return false;
        if (paymentReference != null ? !paymentReference.equals(that.paymentReference) : that.paymentReference != null) return false;
        if (uploadDate != null ? !uploadDate.equals(that.uploadDate) : that.uploadDate != null) return false;
        if (modifiedDt != null ? modifiedDt.equals(that.modifiedDt) : that.modifiedDt == null) return false;

        return true;
    }

    public int hashCode() {
        int result = 17;

        result = 37 * result + (getPspRefNo() == null ? 0 : this.getPspRefNo().hashCode());
        result = 37 * result + (getRecordType() == null ? 0 : this.getRecordType().hashCode());
        result = 37 * result + (getSubmissionMerchantId() == null ? 0 : this.getSubmissionMerchantId().hashCode());
        result = 37 * result + (getBatchCode() == null ? 0 : this.getBatchCode().hashCode());
        result = 37 * result + (getPaymentMethod() == null ? 0 : this.getPaymentMethod().hashCode());
        result = 37 * result + (getGrossAmount() == null ? 0 : this.getGrossAmount().hashCode());
        result = 37 * result + (getGrossDebit() == null ? 0 : this.getGrossDebit().hashCode());
        result = 37 * result + (getGrossCredit() == null ? 0 : this.getGrossCredit().hashCode());
        result = 37 * result + (getNetDebit() == null ? 0 : this.getNetDebit().hashCode());
        result = 37 * result + (getNetCredit() == null ? 0 : this.getNetCredit().hashCode());
        result = 37 * result + (getCommission() == null ? 0 : this.getCommission().hashCode());
        result = 37 * result + (getMarkup() == null ? 0 : this.getMarkup().hashCode());
        result = 37 * result + (getSchemeFee() == null ? 0 : this.getSchemeFee().hashCode());
        result = 37 * result + (getInterchange() == null ? 0 : this.getInterchange().hashCode());
        result = 37 * result + (getSource() == null ? 0 : this.getSource().hashCode());
        result = 37 * result + (getCreatedDt() == null ? 0 : this.getCreatedDt().hashCode());
        result = 37 * result + (getModifiedDt() == null ? 0 : this.getModifiedDt().hashCode());
        result = 37 * result + ((getFileName()) == null ? 0 : this.getFileName().hashCode());
        result = 37 * result + (getUploadDate() == null ? 0 : this.getUploadDate().hashCode());
        result = 37 * result + ((getDetails()) == null ? 0 : this.getDetails().hashCode());
        result = 37 * result + (getTransactionNo() == null ? 0 : this.getTransactionNo().hashCode());
        result = 37 * result + (getStatus() == null ? 0 : this.getStatus().hashCode());
        result = 37 * result + (getPaymentReference() == null ? 0 : this.getPaymentReference().hashCode());
        return result;
    }

}
