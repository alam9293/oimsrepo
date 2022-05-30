package com.cdgtaxi.ibs.common.model.forms;

import java.sql.Date;

import com.cdgtaxi.ibs.common.model.AmtbAccount;
import com.cdgtaxi.ibs.master.model.MstbMasterTable;

public class SearchPaymentReceiptForm {
	private String customerNo;
	private String accountName;
	private AmtbAccount account;
	private AmtbAccount division;
	private AmtbAccount department;
	private Long receiptNo;
	private MstbMasterTable paymentMode;
	private Date receiptDateFrom;
	private Date receiptDateTo;
	private String receiptStatus;
	private String chequeNo;
	private String transactionRefNo;
	private boolean isAtLeastOneCriteriaSelected;
	
	public SearchPaymentReceiptForm(){
		isAtLeastOneCriteriaSelected = false;
	}
	
	public String getCustomerNo() {
		return customerNo;
	}
	public void setCustomerNo(String customerNo) {
		this.customerNo = customerNo;
		if(customerNo!=null && customerNo.length()>0) isAtLeastOneCriteriaSelected = true;
	}
	public AmtbAccount getAccount() {
		return account;
	}
	public void setAccount(AmtbAccount account) {
		this.account = account;
		if(account!=null) isAtLeastOneCriteriaSelected = true;
	}
	public AmtbAccount getDivision() {
		return division;
	}
	public void setDivision(AmtbAccount division) {
		this.division = division;
		if(division!=null) isAtLeastOneCriteriaSelected = true;
	}
	public AmtbAccount getDepartment() {
		return department;
	}
	public void setDepartment(AmtbAccount department) {
		this.department = department;
		if(department!=null) isAtLeastOneCriteriaSelected = true;
	}
	public Long getReceiptNo() {
		return receiptNo;
	}
	public void setReceiptNo(Long receiptNo) {
		this.receiptNo = receiptNo;
		if(receiptNo!=null) isAtLeastOneCriteriaSelected = true;
	}
	public Date getReceiptDateFrom() {
		return receiptDateFrom;
	}
	public void setReceiptDateFrom(Date receiptDateFrom) {
		this.receiptDateFrom = receiptDateFrom;
		if(receiptDateFrom!=null) isAtLeastOneCriteriaSelected = true;
	}
	public Date getReceiptDateTo() {
		return receiptDateTo;
	}
	public void setReceiptDateTo(Date receiptDateTo) {
		this.receiptDateTo = receiptDateTo;
		if(receiptDateTo!=null) isAtLeastOneCriteriaSelected = true;
	}
	public void setPaymentMode(MstbMasterTable paymentMode) {
		this.paymentMode = paymentMode;
		if(paymentMode!=null) isAtLeastOneCriteriaSelected = true;
	}
	public String getAccountName() {
		return accountName;
	}
	public void setAccountName(String accountName) {
		this.accountName = accountName;
		if(accountName!=null && accountName.length()>0) isAtLeastOneCriteriaSelected = true;
	}
	public MstbMasterTable getPaymentMode() {
		return paymentMode;
	}
	public String getReceiptStatus() {
		return receiptStatus;
	}
	public void setReceiptStatus(String receiptStatus) {
		this.receiptStatus = receiptStatus;
		if(receiptStatus!=null && receiptStatus.length()>0) isAtLeastOneCriteriaSelected = true;
	}
	public String getChequeNo() {
		return chequeNo;
	}
	public void setChequeNo(String chequeNo) {
		this.chequeNo = chequeNo;
		if(chequeNo!=null && chequeNo.length()>0) isAtLeastOneCriteriaSelected = true;
	}
	public String getTransactionRefNo() {
		return transactionRefNo;
	}
	public void setTransactionRefNo(String transactionRefNo) {
		this.transactionRefNo = transactionRefNo;
		if(transactionRefNo!=null && transactionRefNo.length()>0) isAtLeastOneCriteriaSelected = true;
	}

	public boolean isAtLeastOneCriteriaSelected() {
		return isAtLeastOneCriteriaSelected;
	}
}
