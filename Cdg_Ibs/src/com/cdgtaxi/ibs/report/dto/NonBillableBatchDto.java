package com.cdgtaxi.ibs.report.dto;

import java.math.BigDecimal;
import java.sql.Date;

public class NonBillableBatchDto {

    /*
     * 1. Batch Id
     * 2. Batch No
     * 3. Settlement Date
     * 4. Acquirer Name
     * 5. TID
     * 6. TXN COUNT
     * 7. TXN AMT
     * 8. CREDIT COUNT
     * 9. CREDIT AMT
     * 10.CREDIT DATE
     * 11. STATUS
     */

    private Long batchId;
    private String batchNo;
    private Date settlementDate;
    private String acquirerName;
    private String tid;
    private Long txnCount;
    private BigDecimal txnAmt;
    private Long creditCount;
    private BigDecimal creditAmt;
    private Date creditDate;
    private String status;

    public NonBillableBatchDto(Long batchId, String batchNo, Date settlementDate, String acquirerName, String tid, Long txnCount, BigDecimal txnAmt, Long creditCount, BigDecimal creditAmt, Date creditDate, String status) {
        this.batchId = batchId;
        this.batchNo = batchNo;
        this.settlementDate = settlementDate;
        this.acquirerName = acquirerName;
        this.tid = tid;
        this.txnCount = txnCount;
        this.txnAmt = txnAmt;
        this.creditCount = creditCount;
        this.creditAmt = creditAmt;
        this.creditDate = creditDate;
        this.status = status;
    }

    public NonBillableBatchDto(Object[] obj) {
        this.batchId = (Long)obj[0];
        this.batchNo = (String) obj[1];
        this.settlementDate = (Date) obj[2];
        this.acquirerName = (String) obj[3];
        this.tid = (String) obj[4];
        this.txnCount = (Long) obj[5];
        this.txnAmt = (BigDecimal) obj[6];
        this.creditCount = (Long) obj[7];
        this.creditAmt = (BigDecimal) obj[8];
        this.creditDate = (Date) obj[9];
        this.status = (String) obj[10];
    }

    public Long getBatchId() {
        return batchId;
    }

    public void setBatchId(Long batchId) {
        this.batchId = batchId;
    }

    public String getBatchNo() {
        return batchNo;
    }

    public void setBatchNo(String batchNo) {
        this.batchNo = batchNo;
    }

    public Date getSettlementDate() {
        return settlementDate;
    }

    public void setSettlementDate(Date settlementDate) {
        this.settlementDate = settlementDate;
    }

    public String getAcquirerName() {
        return acquirerName;
    }

    public void setAcquirerName(String acquirerName) {
        this.acquirerName = acquirerName;
    }

    public String getTid() {
        return tid;
    }

    public void setTid(String tid) {
        this.tid = tid;
    }

    public Long getTxnCount() {
        return txnCount;
    }

    public void setTxnCount(Long txnCount) {
        this.txnCount = txnCount;
    }

    public BigDecimal getTxnAmt() {
        return txnAmt;
    }

    public void setTxnAmt(BigDecimal txnAmt) {
        this.txnAmt = txnAmt;
    }

    public Long getCreditCount() {
        return creditCount;
    }

    public void setCreditCount(Long creditCount) {
        this.creditCount = creditCount;
    }

    public BigDecimal getCreditAmt() {
        return creditAmt;
    }

    public void setCreditAmt(BigDecimal creditAmt) {
        this.creditAmt = creditAmt;
    }

    public Date getCreditDate() {
        return creditDate;
    }

    public void setCreditDate(Date creditDate) {
        this.creditDate = creditDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
