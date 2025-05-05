package com.cdgtaxi.ibs.common.model;

import java.sql.Date;


public class VwBankAdvice {

    String retrievedFlag;
    Date retrievedDate;

    public VwBankAdvice(){

    }

    public VwBankAdvice(String retrievedFlag, Date retrievedDate) {
        this.retrievedFlag = retrievedFlag;
        this.retrievedDate = retrievedDate;
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
