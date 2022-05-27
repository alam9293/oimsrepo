package com.cdgtaxi.ibs.common.model.forms;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;

import com.cdgtaxi.ibs.common.model.AmtbAccount;

public class PaymentInfo {
	private AmtbAccount account;
	private AmtbAccount division;
	private AmtbAccount department;
	private String paymentMode;
	private Date paymentDate;
	private Timestamp receiptDt;
	private String txnRefNo;
	private BigDecimal paymentAmount;
	private BigDecimal excessAmount;
	private Integer bankNo;
	private Integer branchNo;
	private Date chequeDate;
	private String chequeNo;
	private boolean quickChequeDeposit;
	private String remarks;
	private Integer bankInNo;
	
	public PaymentInfo(){
	}
	
	public AmtbAccount getAccount() {
		return account;
	}
	public void setAccount(AmtbAccount account) {
		this.account = account;
	}
	public String getPaymentMode() {
		return paymentMode;
	}
	public void setPaymentMode(String paymentMode) {
		this.paymentMode = paymentMode;
	}
	public Date getPaymentDate() {
		return paymentDate;
	}
	public void setPaymentDate(Date paymentDate) {
		this.paymentDate = paymentDate;
	}
	public String getTxnRefNo() {
		return txnRefNo;
	}
	public void setTxnRefNo(String txnRefNo) {
		this.txnRefNo = txnRefNo;
	}
	public BigDecimal getPaymentAmount() {
		return paymentAmount;
	}
	public void setPaymentAmount(BigDecimal paymentAmount) {
		this.paymentAmount = paymentAmount;
	}
	public Integer getBankNo() {
		return bankNo;
	}
	public void setBankNo(Integer bankNo) {
		this.bankNo = bankNo;
	}
	public Integer getBranchNo() {
		return branchNo;
	}
	public void setBranchNo(Integer branchNo) {
		this.branchNo = branchNo;
	}
	public Date getChequeDate() {
		return chequeDate;
	}
	public void setChequeDate(Date chequeDate) {
		this.chequeDate = chequeDate;
	}
	public String getChequeNo() {
		return chequeNo;
	}
	public void setChequeNo(String chequeNo) {
		this.chequeNo = chequeNo;
	}
	public boolean isQuickChequeDeposit() {
		return quickChequeDeposit;
	}
	public void setQuickChequeDeposit(boolean quickChequeDeposit) {
		this.quickChequeDeposit = quickChequeDeposit;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public Integer getBankInNo() {
		return bankInNo;
	}
	public void setBankInNo(Integer bankInNo) {
		this.bankInNo = bankInNo;
	}
	public AmtbAccount getDivision() {
		return division;
	}
	public void setDivision(AmtbAccount division) {
		this.division = division;
	}
	public AmtbAccount getDepartment() {
		return department;
	}
	public void setDepartment(AmtbAccount department) {
		this.department = department;
	}
	public BigDecimal getExcessAmount() {
		return excessAmount;
	}
	public void setExcessAmount(BigDecimal excessAmount) {
		this.excessAmount = excessAmount;
	}
	public Timestamp getReceiptDt() {
		return receiptDt;
	}
	public void setReceiptDt(Timestamp receiptDt) {
		this.receiptDt = receiptDt;
	}
}
