package com.cdgtaxi.ibs.common.model.forms;

import java.math.BigDecimal;

public class TransferableAmtInfo {

	private BigDecimal cardValue;
	private BigDecimal cashplus;
	private BigDecimal creditBalance;

	public BigDecimal getCardValue() {
		return cardValue;
	}

	public void setCardValue(BigDecimal cardValue) {
		this.cardValue = cardValue;
	}

	public BigDecimal getCashplus() {
		return cashplus;
	}

	public void setCashplus(BigDecimal cashplus) {
		this.cashplus = cashplus;
	}

	public BigDecimal getCreditBalance() {
		return creditBalance;
	}

	public void setCreditBalance(BigDecimal creditBalance) {
		this.creditBalance = creditBalance;
	}

	@Override
	public String toString() {
		return "TransferableAmtInfo [cardValue=" + cardValue + ", cashplus=" + cashplus + ", creditBalance=" + creditBalance + "]";
	}
	
	public ApplyAmtInfo toApplyAmtInfo(){
		
		ApplyAmtInfo info = new ApplyAmtInfo();
		info.setApplyCardValue(cardValue);
		info.setApplyCashplus(cashplus);
		info.setApplyCreditBalance(creditBalance);
		
		return info;
	}

}
