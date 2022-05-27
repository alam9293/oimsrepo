package com.cdgtaxi.ibs.common.model.forms;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;

import com.cdgtaxi.ibs.common.model.AmtbAccount;
import com.cdgtaxi.ibs.common.model.PmtbIssuanceReqCard;
import com.cdgtaxi.ibs.common.model.PmtbProductType;

public class IssueProductForm {

	public enum IssueType {COUNT, RANGE};
	
	private String nameOnProduct;
	private BigDecimal creditLimit;
	private Date expiryDate;
	private BigDecimal fixedValue;
	private String cardHolderName;
	private String cardHolderTitle;
	private String cardHolderTel;
	private String cardHolderSalutation;
	private String cardHolderMobile;
	private String cardHolderEmail;
	private String waiveSubscFeeFlag;
	private String isIndividualCard;
	private String embossFlag;
	private String smsExpirySent;
	private String smsExpiryFlag;
	private String smsTopupFlag;
	private Timestamp expiryTime;
	private Integer offlineCount;
	private BigDecimal offlineAmount;
	private BigDecimal offlineTxnAmount;
	private String embossNameOnCard;
	private String waiveIssuanceFeeFlag;
	private PmtbProductType pmtbProductType;
	private AmtbAccount amtbAccount;
	private BigDecimal cashplus;
	private BigDecimal cardValue;
	private java.util.Date balanceExpiryDate;
	private String employeeId;
	
	
	private IssueType issueType;
	private Integer noOfCards;
	private Integer cardNoStart;
	private Integer cardNoEnd;
	
	
	public String getNameOnProduct() {
		return nameOnProduct;
	}
	public void setNameOnProduct(String nameOnProduct) {
		this.nameOnProduct = nameOnProduct;
	}
	public BigDecimal getCreditLimit() {
		return creditLimit;
	}
	public void setCreditLimit(BigDecimal creditLimit) {
		this.creditLimit = creditLimit;
	}
	public Date getExpiryDate() {
		return expiryDate;
	}
	public void setExpiryDate(Date expiryDate) {
		this.expiryDate = expiryDate;
	}
	public BigDecimal getFixedValue() {
		return fixedValue;
	}
	public void setFixedValue(BigDecimal fixedValue) {
		this.fixedValue = fixedValue;
	}
	public String getCardHolderName() {
		return cardHolderName;
	}
	public void setCardHolderName(String cardHolderName) {
		this.cardHolderName = cardHolderName;
	}
	public String getCardHolderTitle() {
		return cardHolderTitle;
	}
	public void setCardHolderTitle(String cardHolderTitle) {
		this.cardHolderTitle = cardHolderTitle;
	}
	public String getCardHolderTel() {
		return cardHolderTel;
	}
	public void setCardHolderTel(String cardHolderTel) {
		this.cardHolderTel = cardHolderTel;
	}
	public String getCardHolderSalutation() {
		return cardHolderSalutation;
	}
	public void setCardHolderSalutation(String cardHolderSalutation) {
		this.cardHolderSalutation = cardHolderSalutation;
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
	public String getWaiveSubscFeeFlag() {
		return waiveSubscFeeFlag;
	}
	public void setWaiveSubscFeeFlag(String waiveSubscFeeFlag) {
		this.waiveSubscFeeFlag = waiveSubscFeeFlag;
	}
	public String getIsIndividualCard() {
		return isIndividualCard;
	}
	public void setIsIndividualCard(String isIndividualCard) {
		this.isIndividualCard = isIndividualCard;
	}
	public String getEmbossFlag() {
		return embossFlag;
	}
	public void setEmbossFlag(String embossFlag) {
		this.embossFlag = embossFlag;
	}
	public String getSmsExpirySent() {
		return smsExpirySent;
	}
	public void setSmsExpirySent(String smsExpirySent) {
		this.smsExpirySent = smsExpirySent;
	}
	public String getSmsExpiryFlag() {
		return smsExpiryFlag;
	}
	public void setSmsExpiryFlag(String smsExpiryFlag) {
		this.smsExpiryFlag = smsExpiryFlag;
	}
	public String getSmsTopupFlag() {
		return smsTopupFlag;
	}
	public void setSmsTopupFlag(String smsTopupFlag) {
		this.smsTopupFlag = smsTopupFlag;
	}
	public Timestamp getExpiryTime() {
		return expiryTime;
	}
	public void setExpiryTime(Timestamp expiryTime) {
		this.expiryTime = expiryTime;
	}
	public Integer getOfflineCount() {
		return offlineCount;
	}
	public void setOfflineCount(Integer offlineCount) {
		this.offlineCount = offlineCount;
	}
	public BigDecimal getOfflineAmount() {
		return offlineAmount;
	}
	public void setOfflineAmount(BigDecimal offlineAmount) {
		this.offlineAmount = offlineAmount;
	}
	public BigDecimal getOfflineTxnAmount() {
		return offlineTxnAmount;
	}
	public void setOfflineTxnAmount(BigDecimal offlineTxnAmount) {
		this.offlineTxnAmount = offlineTxnAmount;
	}
	public String getEmbossNameOnCard() {
		return embossNameOnCard;
	}
	public void setEmbossNameOnCard(String embossNameOnCard) {
		this.embossNameOnCard = embossNameOnCard;
	}
	public String getWaiveIssuanceFeeFlag() {
		return waiveIssuanceFeeFlag;
	}
	public void setWaiveIssuanceFeeFlag(String waiveIssuanceFeeFlag) {
		this.waiveIssuanceFeeFlag = waiveIssuanceFeeFlag;
	}
	public PmtbProductType getPmtbProductType() {
		return pmtbProductType;
	}
	public void setPmtbProductType(PmtbProductType pmtbProductType) {
		this.pmtbProductType = pmtbProductType;
	}
	public AmtbAccount getAmtbAccount() {
		return amtbAccount;
	}
	public void setAmtbAccount(AmtbAccount amtbAccount) {
		this.amtbAccount = amtbAccount;
	}
	public BigDecimal getCashplus() {
		return cashplus;
	}
	public void setCashplus(BigDecimal cashplus) {
		this.cashplus = cashplus;
	}
	public BigDecimal getCardValue() {
		return cardValue;
	}
	public void setCardValue(BigDecimal cardValue) {
		this.cardValue = cardValue;
	}
	public IssueType getIssueType() {
		return issueType;
	}
	public void setIssueType(IssueType issueType) {
		this.issueType = issueType;
	}
	public Integer getNoOfCards() {
		return noOfCards;
	}
	public void setNoOfCards(Integer noOfCards) {
		this.noOfCards = noOfCards;
	}
	public Integer getCardNoStart() {
		return cardNoStart;
	}
	public void setCardNoStart(Integer cardNoStart) {
		this.cardNoStart = cardNoStart;
	}
	public Integer getCardNoEnd() {
		return cardNoEnd;
	}
	public void setCardNoEnd(Integer cardNoEnd) {
		this.cardNoEnd = cardNoEnd;
	}
	
	
	public java.util.Date getBalanceExpiryDate() {
		return balanceExpiryDate;
	}
	public void setBalanceExpiryDate(java.util.Date balanceExpiryDate) {
		this.balanceExpiryDate = balanceExpiryDate;
	}
	
	public String getEmployeeId() {
		return employeeId;
	}
	public void setEmployeeId(String employeeId) {
		this.employeeId = employeeId;
	}
	public static IssueProductForm buildIssueProductForm(PmtbIssuanceReqCard card){
		
	   IssueProductForm form = new IssueProductForm();
	   form.setAmtbAccount(card.getAmtbAccount());
	   form.setPmtbProductType(card.getPmtbProductType());
	   form.setExpiryDate(card.getExpiryDate());
	   form.setCreditLimit(card.getCreditLimit());
	   form.setFixedValue(card.getFixedValue());
	   form.setExpiryDate(card.getExpiryDate());
	   form.setExpiryTime(card.getExpiryTime());
	   form.setNameOnProduct(card.getNameOnProduct());
	   form.setCardHolderMobile(card.getCardHolderMobile());
	   form.setCardHolderEmail(card.getCardHolderEmail());
	   form.setCardHolderTel(card.getCardHolderTel());
	   form.setCardHolderName(card.getCardHolderName());
	   form.setCardHolderTitle(card.getCardHolderTitle());
	   form.setCardHolderSalutation(card.getCardHolderSalutation());
	   form.setSmsExpiryFlag(card.getSmsExpiryFlag());
	   form.setSmsTopupFlag(card.getSmsTopupFlag());
	 
	   form.setOfflineCount(card.getOfflineCount());
	   form.setOfflineAmount(card.getOfflineAmount());
	   form.setOfflineTxnAmount(card.getOfflineTxnAmount());
	   form.setEmbossNameOnCard(card.getEmbossNameOnCard());
	   form.setWaiveIssuanceFeeFlag(card.getWaiveIssuanceFeeFlag());

	   form.setWaiveSubscFeeFlag(card.getWaiveSubscFeeFlag());
	   form.setIsIndividualCard(card.getIsIndividualCard());
	   form.setCardValue(card.getCardValue());
	   form.setCashplus(card.getCashplus());
	   
	   form.setBalanceExpiryDate(card.getBalanceExpiryDate());
	   form.setEmployeeId(card.getEmployeeId());

	   return form;
	}

}
