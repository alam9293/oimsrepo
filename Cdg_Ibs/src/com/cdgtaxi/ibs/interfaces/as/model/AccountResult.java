package com.cdgtaxi.ibs.interfaces.as.model;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class AccountResult {

	private static final long serialVersionUID = 1L;
	private String acctId;
    private String acctType;
    private BigDecimal creditLimit;
    private BigDecimal totalTxnAmt;
    private Timestamp createDate;
    private String createBy;
    private Timestamp updateDate;
    private String updateBy;
    private AccountResult account; //Parent Account, Null if there is no parent

    public AccountResult(String acctId, String acctType, BigDecimal creditLimit, BigDecimal totalTxnAmt, Timestamp createDate, String createBy, Timestamp updateDate, String updateBy, AccountResult account) {
    	this.acctId = acctId;
        this.acctType = acctType;
        this.creditLimit = creditLimit;
        this.totalTxnAmt = totalTxnAmt;
        this.createDate = createDate;
        this.createBy = createBy;
        this.updateDate = updateDate;
        this.updateBy = updateBy;
        this.account = account;
    }

    public String getAcctId() {
        return this.acctId;
    }

    public void setAcctId(String acctId) {
        this.acctId = acctId;
    }

    public String getAcctType() {
        return this.acctType;
    }

    public void setAcctType(String acctType) {
        this.acctType = acctType;
    }

    public BigDecimal getCreditLimit() {
        return this.creditLimit;
    }

    public void setCreditLimit(BigDecimal creditLimit) {
        this.creditLimit = creditLimit;
    }

    public BigDecimal getTotalTxnAmt() {
        return this.totalTxnAmt;
    }

    public void setTotalTxnAmt(BigDecimal totalTxnAmt) {
        this.totalTxnAmt = totalTxnAmt;
    }

    public Timestamp getCreateDate() {
        return this.createDate;
    }

    public void setCreateDate(Timestamp createDate) {
        this.createDate = createDate;
    }

    public String getCreateBy() {
        return this.createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public Timestamp getUpdateDate() {
        return this.updateDate;
    }

    public void setUpdateDate(Timestamp updateDate) {
        this.updateDate = updateDate;
    }

    public String getUpdateBy() {
        return this.updateBy;
    }

    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
    }

    public AccountResult getAccount() {
        return this.account;
    }

    public void setAccount(AccountResult account) {
        this.account = account;
    }
}
