package com.cdgtaxi.ibs.common.model.forms;

import java.util.Date;

public class SearchPrepaidProductForm {
	

    private Integer accountNo;
    private String nameOnCard;
	private String cardNoStart;
	private String cardNoEnd;
    private Date cardExpiryFrom;
    private Date cardExpiryTo;
    private Date balanceExpiryFrom;
    private Date balanceExpiryTo;
    private String status;
	public Integer getAccountNo() {
		return accountNo;
	}
	public String getNameOnCard() {
		return nameOnCard;
	}
	public String getCardNoStart() {
		return cardNoStart;
	}
	public String getCardNoEnd() {
		return cardNoEnd;
	}
	public Date getCardExpiryFrom() {
		return cardExpiryFrom;
	}
	public Date getCardExpiryTo() {
		return cardExpiryTo;
	}
	public String getStatus() {
		return status;
	}
	public void setAccountNo(Integer accountNo) {
		this.accountNo = accountNo;
	}
	public void setNameOnCard(String nameOnCard) {
		this.nameOnCard = nameOnCard;
	}
	public void setCardNoStart(String cardNoStart) {
		this.cardNoStart = cardNoStart;
	}
	public void setCardNoEnd(String cardNoEnd) {
		this.cardNoEnd = cardNoEnd;
	}
	public void setCardExpiryFrom(Date cardExpiryFrom) {
		this.cardExpiryFrom = cardExpiryFrom;
	}
	public void setCardExpiryTo(Date cardExpiryTo) {
		this.cardExpiryTo = cardExpiryTo;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Date getBalanceExpiryFrom() {
		return balanceExpiryFrom;
	}
	public Date getBalanceExpiryTo() {
		return balanceExpiryTo;
	}
	public void setBalanceExpiryFrom(Date balanceExpiryFrom) {
		this.balanceExpiryFrom = balanceExpiryFrom;
	}
	public void setBalanceExpiryTo(Date balanceExpiryTo) {
		this.balanceExpiryTo = balanceExpiryTo;
	}
    

	
}
