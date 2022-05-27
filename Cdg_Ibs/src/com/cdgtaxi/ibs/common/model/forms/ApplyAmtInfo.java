package com.cdgtaxi.ibs.common.model.forms;

import java.math.BigDecimal;

public class ApplyAmtInfo {

	private BigDecimal applyCardValue;
	private BigDecimal applyCashplus;
	private BigDecimal applyCreditBalance;
	
	public BigDecimal getApplyCardValue() {
		return applyCardValue;
	}
	public void setApplyCardValue(BigDecimal applyCardValue) {
		this.applyCardValue = applyCardValue;
	}
	public BigDecimal getApplyCashplus() {
		return applyCashplus;
	}
	public void setApplyCashplus(BigDecimal applyCashPlus) {
		this.applyCashplus = applyCashPlus;
	}
	public BigDecimal getApplyCreditBalance() {
		return applyCreditBalance;
	}
	public void setApplyCreditBalance(BigDecimal applyCreditBalance) {
		this.applyCreditBalance = applyCreditBalance;
	}
	
	@Override
	public String toString() {
		return "ApplyAmtInfo [applyCardValue=" + applyCardValue + ", applyCashplus=" + applyCashplus + ", applyCreditBalance=" + applyCreditBalance + "]";
	}
	
}
