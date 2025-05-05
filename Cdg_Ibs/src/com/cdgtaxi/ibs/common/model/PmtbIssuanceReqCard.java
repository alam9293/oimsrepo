package com.cdgtaxi.ibs.common.model;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.Set;

import com.cdgtaxi.ibs.common.model.forms.IssueProductForm;
import com.cdgtaxi.ibs.master.model.MstbPromotionCashPlus;



@SuppressWarnings("serial")
public class PmtbIssuanceReqCard implements java.io.Serializable {

	private BigDecimal reqCardNo;
	private PmtbIssuanceReq pmtbIssuanceReq;
    private AmtbAccount amtbAccount;
    private PmtbProductType pmtbProductType;
    private PmtbProduct pmtbProduct;
   
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
    private String smsExpiryFlag;
	private String smsTopupFlag;
    private Timestamp expiryTime;
    private Integer offlineCount;
    private BigDecimal offlineAmount;
    private BigDecimal offlineTxnAmount;
    private String embossNameOnCard;
    private String waiveIssuanceFeeFlag;
    private java.util.Date balanceExpiryDate;
    private BigDecimal cashplus;
    private BigDecimal cardValue;

    private BigDecimal issuanceFee;
	private Set <MstbPromotionCashPlus> mstbPromotionCashPluses;
	private Set <BmtbIssuanceInvoiceTxn> bmtbIssuanceInvoiceTxns;
	
	private String employeeId;
	
	private transient PmtbProduct transientProduct;
	
	private transient boolean isSelected;
	
	public AmtbAccount getAmtbAccount() {
		return amtbAccount;
	}
	public void setAmtbAccount(AmtbAccount amtbAccount) {
		this.amtbAccount = amtbAccount;
	}
	

	public PmtbIssuanceReq getPmtbIssuanceReq() {
		return pmtbIssuanceReq;
	}
	public PmtbProductType getPmtbProductType() {
		return pmtbProductType;
	}
	public void setPmtbIssuanceReq(PmtbIssuanceReq pmtbIssuanceReq) {
		this.pmtbIssuanceReq = pmtbIssuanceReq;
	}
	public void setPmtbProductType(PmtbProductType pmtbProductType) {
		this.pmtbProductType = pmtbProductType;
	}
	
	public Set<MstbPromotionCashPlus> getMstbPromotionCashPluses() {
		return mstbPromotionCashPluses;
	}
	public void setMstbPromotionCashPluses(Set<MstbPromotionCashPlus> mstbPromotionCashPluses) {
		this.mstbPromotionCashPluses = mstbPromotionCashPluses;
	}
	public BigDecimal getReqCardNo() {
		return reqCardNo;
	}
	public void setReqCardNo(BigDecimal reqCardNo) {
		this.reqCardNo = reqCardNo;
	}
	public Set<BmtbIssuanceInvoiceTxn> getBmtbIssuanceInvoiceTxns() {
		return bmtbIssuanceInvoiceTxns;
	}
	public void setBmtbIssuanceInvoiceTxns(Set<BmtbIssuanceInvoiceTxn> bmtbIssuanceInvoiceTxns) {
		this.bmtbIssuanceInvoiceTxns = bmtbIssuanceInvoiceTxns;
	}
	public PmtbProduct getPmtbProduct() {
		return pmtbProduct;
	}
	public void setPmtbProduct(PmtbProduct pmtbProduct) {
		this.pmtbProduct = pmtbProduct;
	}
	public BigDecimal getIssuanceFee() {
		return issuanceFee;
	}
	public void setIssuanceFee(BigDecimal issuanceFee) {
		this.issuanceFee = issuanceFee;
	}
	
	
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
	public java.util.Date getBalanceExpiryDate() {
		return balanceExpiryDate;
	}
	public void setBalanceExpiryDate(java.util.Date balanceExpiryDate) {
		this.balanceExpiryDate = balanceExpiryDate;
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
	public PmtbProduct getTransientProduct() {
		return transientProduct;
	}
	public void setTransientProduct(PmtbProduct transientProduct) {
		this.transientProduct = transientProduct;
	}
	
	public String getEmployeeId() {
		return employeeId;
	}
	public void setEmployeeId(String employeeId) {
		this.employeeId = employeeId;
	}
	
	public static void buildReqCard(PmtbIssuanceReqCard card, IssueProductForm form) {

		card.setAmtbAccount(form.getAmtbAccount());
		card.setPmtbProductType(form.getPmtbProductType());
		card.setExpiryDate(form.getExpiryDate());
		card.setCreditLimit(form.getCreditLimit());
		card.setFixedValue(form.getFixedValue());
		card.setExpiryTime(form.getExpiryTime());
		card.setNameOnProduct(form.getNameOnProduct());
		card.setCardHolderMobile(form.getCardHolderMobile());
		card.setCardHolderEmail(form.getCardHolderEmail());
		card.setCardHolderTel(form.getCardHolderTel());
		card.setCardHolderName(form.getCardHolderName());
		card.setCardHolderTitle(form.getCardHolderTitle());
		card.setCardHolderSalutation(form.getCardHolderSalutation());
		card.setSmsExpiryFlag(form.getSmsExpiryFlag());
		card.setSmsTopupFlag(form.getSmsTopupFlag());
		card.setOfflineCount(form.getOfflineCount());
		card.setOfflineAmount(form.getOfflineAmount());
		card.setOfflineTxnAmount(form.getOfflineTxnAmount());
		card.setEmbossNameOnCard(form.getEmbossNameOnCard());
		card.setWaiveIssuanceFeeFlag(form.getWaiveIssuanceFeeFlag());

		card.setWaiveSubscFeeFlag(form.getWaiveSubscFeeFlag());
		card.setIsIndividualCard(form.getIsIndividualCard());
		card.setCardValue(form.getCardValue());
		card.setCashplus(form.getCashplus());
		
		card.setBalanceExpiryDate(form.getBalanceExpiryDate());
		card.setEmployeeId(form.getEmployeeId());

	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((reqCardNo == null) ? 0 : reqCardNo.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PmtbIssuanceReqCard other = (PmtbIssuanceReqCard) obj;
		if (reqCardNo == null) {
			if (other.reqCardNo != null)
				return false;
		} else if (!reqCardNo.equals(other.reqCardNo))
			return false;
		return true;
	}
	public boolean isSelected() {
		return isSelected;
	}
	public void setSelected(boolean isSelected) {
		this.isSelected = isSelected;
	}
	
	
	
	
	
	
	
}
