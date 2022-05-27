package com.cdgtaxi.ibs.common.model;

import com.cdgtaxi.ibs.acl.security.Auditable;

import java.sql.Timestamp;

public class BaseEntity implements  java.io.Serializable {
        //, Auditable, Creatable, Updatable{

    //all this belongs to hibernate requirements
    private Integer version;
    private String createdBy;
    private String updatedBy;
    private Timestamp updatedDt;
    private Timestamp createdDt;

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Timestamp getUpdatedDt() {
        return updatedDt;
    }

    public void setUpdatedDt(Timestamp updatedDt) {
        this.updatedDt = updatedDt;
    }

    public Timestamp getCreatedDt() {
        return createdDt;
    }

    public void setCreatedDt(Timestamp createdDt) {
        this.createdDt = createdDt;
    }
}
