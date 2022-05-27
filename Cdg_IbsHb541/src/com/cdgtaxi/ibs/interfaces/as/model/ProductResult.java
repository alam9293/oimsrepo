package com.cdgtaxi.ibs.interfaces.as.model;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class ProductResult {

	private static final long serialVersionUID = 1L;
	private String cardNo;
	private String prodTypeId;
    private BigDecimal fixedValue;
    private BigDecimal creditLimit;
    private BigDecimal totalTxnAmt;
    private String status;
    private String reasonCode;
    private String used;
    private Timestamp createDate;
    private String createBy;
    private Timestamp updateDate;
    private String updateBy;
    private AccountResult account; //Issued Account

    public ProductResult(String cardNo, String prodTypeId, BigDecimal fixedValue, BigDecimal creditLimit, BigDecimal totalTxnAmt, String status, String reasonCode, String used, Timestamp createDate, String createBy, Timestamp updateDate, String updateBy, AccountResult account) {
    	this.cardNo = cardNo;
    	this.prodTypeId = prodTypeId;
        this.fixedValue = fixedValue;
        this.creditLimit = creditLimit;
        this.totalTxnAmt = totalTxnAmt;
        this.status = status;
        this.reasonCode = reasonCode;
        this.used = used;
        this.createDate = createDate;
        this.createBy = createBy;
        this.updateDate = updateDate;
        this.updateBy = updateBy;
        this.account = account;
    }

	public String getCardNo() {
		return cardNo;
	}

	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}

	public String getProdTypeId() {
		return prodTypeId;
	}

	public void setProdTypeId(String prodTypeId) {
		this.prodTypeId = prodTypeId;
	}

	public BigDecimal getFixedValue() {
		return fixedValue;
	}

	public void setFixedValue(BigDecimal fixedValue) {
		this.fixedValue = fixedValue;
	}

	public BigDecimal getCreditLimit() {
		return creditLimit;
	}

	public void setCreditLimit(BigDecimal creditLimit) {
		this.creditLimit = creditLimit;
	}

	public BigDecimal getTotalTxnAmt() {
		return totalTxnAmt;
	}

	public void setTotalTxnAmt(BigDecimal totalTxnAmt) {
		this.totalTxnAmt = totalTxnAmt;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getReasonCode() {
		return reasonCode;
	}

	public void setReasonCode(String reasonCode) {
		this.reasonCode = reasonCode;
	}

	public String getUsed() {
		return used;
	}

	public void setUsed(String used) {
		this.used = used;
	}

	public Timestamp getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Timestamp createDate) {
		this.createDate = createDate;
	}

	public String getCreateBy() {
		return createBy;
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}

	public Timestamp getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Timestamp updateDate) {
		this.updateDate = updateDate;
	}

	public String getUpdateBy() {
		return updateBy;
	}

	public void setUpdateBy(String updateBy) {
		this.updateBy = updateBy;
	}

	public AccountResult getAccount() {
		return account;
	}

	public void setAccount(AccountResult account) {
		this.account = account;
	}
}
