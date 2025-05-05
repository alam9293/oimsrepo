package com.cdgtaxi.ibs.product.ui;

import java.util.Date;

public class ProductSearchCriteria {
	
	private String accNo="";
	private String custNo="";
	private String accName="";
	private String cardNoStart="";
	private String cardNoEnd="";
	private String productType="";
	private String productStatus="";
	private Date IssueDateFrom=null;
	private Date IssueDateTo=null;
	private Date ExpiryDateFrom=null;
	private Date ExpiryDateTo=null;
	private Date SuspensionDateFrom=null;
	private Date SuspensionDateTo=null;
	private String sortBy="";
	private String nameOnCard="";
	private String employeeId = "";
	private String cardHolderMobile = "";
	private String cardHolderEmail = "";
	private String tokenId = "";
	
	private Date balanceExpiryDateFrom=null;
	private Date balanceExpiryDateTo=null;
	private String tokenActive = null;

	private Date ccExpiryDateFrom=null;
	private Date ccExpiryDateTo=null;
	
	public String getTokenActive() {
		return tokenActive;
	}
	public void setTokenActive(String tokenActive) {
		this.tokenActive = tokenActive;
	}
	public String getTokenId() {
		return tokenId;
	}
	public void setTokenId(String tokenId) {
		this.tokenId = tokenId;
	}
	
	public String getAccNo() {
		return accNo;
	}
	public void setAccNo(String accNo) {
		this.accNo = accNo;
	}
	public String getCustNo() {
		return custNo;
	}
	public void setCustNo(String custNo) {
		this.custNo = custNo;
	}
	public String getAccName() {
		return accName;
	}
	public void setAccName(String accName) {
		this.accName = accName;
	}
	public String getCardNoStart() {
		return cardNoStart;
	}
	public void setCardNoStart(String cardNoStart) {
		this.cardNoStart = cardNoStart;
	}
	public String getCardNoEnd() {
		return cardNoEnd;
	}
	public void setCardNoEnd(String cardNoEnd) {
		this.cardNoEnd = cardNoEnd;
	}
	public String getProductType() {
		return productType;
	}
	public void setProductType(String productType) {
		this.productType = productType;
	}
	public String getProductStatus() {
		return productStatus;
	}
	public void setProductStatus(String productStatus) {
		this.productStatus = productStatus;
	}
	public Date getIssueDateFrom() {
		return IssueDateFrom;
	}
	public void setIssueDateFrom(Date issueDateFrom) {
		IssueDateFrom = issueDateFrom;
	}
	public Date getIssueDateTo() {
		return IssueDateTo;
	}
	public void setIssueDateTo(Date issueDateTo) {
		IssueDateTo = issueDateTo;
	}
	public Date getExpiryDateFrom() {
		return ExpiryDateFrom;
	}
	public void setExpiryDateFrom(Date expiryDateFrom) {
		ExpiryDateFrom = expiryDateFrom;
	}
	public Date getExpiryDateTo() {
		return ExpiryDateTo;
	}
	public void setExpiryDateTo(Date expiryDateTo) {
		ExpiryDateTo = expiryDateTo;
	}
	public Date getSuspensionDateFrom() {
		return SuspensionDateFrom;
	}
	public void setSuspensionDateFrom(Date suspensionDateFrom) {
		SuspensionDateFrom = suspensionDateFrom;
	}
	public Date getSuspensionDateTo() {
		return SuspensionDateTo;
	}
	public void setSuspensionDateTo(Date suspensionDateTo) {
		SuspensionDateTo = suspensionDateTo;
	}
	public String getSortBy() {
		return sortBy;
	}
	public void setSortBy(String sortBy) {
		this.sortBy = sortBy;
	}
	public void setNameOnCard(String nameOnCard) {
		this.nameOnCard = nameOnCard;
	}
	public String getNameOnCard() {
		return nameOnCard;
	}
	public Date getBalanceExpiryDateFrom() {
		return balanceExpiryDateFrom;
	}
	public void setBalanceExpiryDateFrom(Date balanceExpiryDateFrom) {
		this.balanceExpiryDateFrom = balanceExpiryDateFrom;
	}
	public Date getBalanceExpiryDateTo() {
		return balanceExpiryDateTo;
	}
	public void setBalanceExpiryDateTo(Date balanceExpiryDateTo) {
		this.balanceExpiryDateTo = balanceExpiryDateTo;
	}
	public String getEmployeeId() {
		return employeeId;
	}
	public void setEmployeeId(String employeeId) {
		this.employeeId = employeeId;
	}
	public String getCardHolderMobile() {
		return cardHolderMobile;
	}
	public void setCardHolderMobile(String cardHolderMobile) {
		this.cardHolderMobile = cardHolderMobile;
	}
	public String getCardHolderEmail() {
		return cardHolderEmail;
	}
	public void setCardHolderEmail(String cardHolderEmail) {
		this.cardHolderEmail = cardHolderEmail;
	}
	public Date getCcExpiryDateFrom() {
		return ccExpiryDateFrom;
	}
	public void setCcExpiryDateFrom(Date ccExpiryDateFrom) {
		this.ccExpiryDateFrom = ccExpiryDateFrom;
	}
	public Date getCcExpiryDateTo() {
		return ccExpiryDateTo;
	}
	public void setCcExpiryDateTo(Date ccExpiryDateTo) {
		this.ccExpiryDateTo = ccExpiryDateTo;
	}
}