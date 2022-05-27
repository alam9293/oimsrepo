package com.cdgtaxi.ibs.common.model;

import com.cdgtaxi.ibs.acl.security.Auditable;

import java.sql.Date;
import java.sql.Timestamp;

public class TmtbNonBillableTxnCrcaReq implements java.io.Serializable, Auditable, Creatable, Updatable {

    private Integer reqNo;
    /**
     * P - Pending <Br>
     * G - Progress <Br>
     * E - Error <Br>
     * C - Completed <Br>
     */
    private String status;
    private Integer version;
    private String createdBy;
    private Timestamp createdDt;
    private String updatedBy;
    private Timestamp updatedDt;
    private Date settlementDate;

    public TmtbNonBillableTxnCrcaReq() {
    }

    public TmtbNonBillableTxnCrcaReq(String status) {
        this.status = status;
    }

    public Integer getReqNo() {
        return reqNo;
    }

    public void setReqNo(Integer reqNo) {
        this.reqNo = reqNo;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    @Override
    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Timestamp getCreatedDt() {
        return createdDt;
    }

    @Override
    public void setCreatedDt(Timestamp createdDt) {
        this.createdDt = createdDt;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    @Override
    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Timestamp getUpdatedDt() {
        return updatedDt;
    }

    @Override
    public void setUpdatedDt(Timestamp updatedDt) {
        this.updatedDt = updatedDt;
    }

    public Date getSettlementDate() {
        return settlementDate;
    }

    public void setSettlementDate(Date settlementDate) {
        this.settlementDate = settlementDate;
    }
}
