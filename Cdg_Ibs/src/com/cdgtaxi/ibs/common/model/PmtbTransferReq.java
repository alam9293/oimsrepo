package com.cdgtaxi.ibs.common.model;

import java.math.BigDecimal;

import com.cdgtaxi.ibs.common.NonConfigurableConstants;




@SuppressWarnings("serial")
public class PmtbTransferReq extends PmtbPrepaidReq implements java.io.Serializable {



	private PmtbProduct fromPmtbProduct;
	private PmtbProduct toPmtbProduct;
	private String waiveTransferFeeFlag = NonConfigurableConstants.BOOLEAN_NO;
	private BigDecimal transferFee;
	private BigDecimal txferableCardValue;
	private BigDecimal txferableCashplus;
	private BigDecimal transferCashplus;
	private BigDecimal transferCardValue;
	private String transferAllFlag;
	
	private BigDecimal fromCardValue;
	private BigDecimal fromCashplus;
	private BigDecimal toCardValue;
	private BigDecimal toCashplus;
	
	public PmtbProduct getFromPmtbProduct() {
		return fromPmtbProduct;
	}
	public PmtbProduct getToPmtbProduct() {
		return toPmtbProduct;
	}
	public String getWaiveTransferFeeFlag() {
		return waiveTransferFeeFlag;
	}
	public BigDecimal getTransferFee() {
		return transferFee;
	}

	public void setFromPmtbProduct(PmtbProduct fromPmtbProduct) {
		this.fromPmtbProduct = fromPmtbProduct;
	}
	public void setToPmtbProduct(PmtbProduct toPmtbProduct) {
		this.toPmtbProduct = toPmtbProduct;
	}
	public void setWaiveTransferFeeFlag(String waiveTransferFeeFlag) {
		this.waiveTransferFeeFlag = waiveTransferFeeFlag;
	}
	public void setTransferFee(BigDecimal transferFee) {
		this.transferFee = transferFee;
	}

	public BigDecimal getFromCardValue() {
		return fromCardValue;
	}
	public BigDecimal getFromCashplus() {
		return fromCashplus;
	}
	public BigDecimal getToCardValue() {
		return toCardValue;
	}
	public BigDecimal getToCashplus() {
		return toCashplus;
	}
	public void setFromCardValue(BigDecimal fromCardValue) {
		this.fromCardValue = fromCardValue;
	}
	public void setFromCashplus(BigDecimal fromCashplus) {
		this.fromCashplus = fromCashplus;
	}
	public void setToCardValue(BigDecimal toCardValue) {
		this.toCardValue = toCardValue;
	}
	public void setToCashplus(BigDecimal toCashplus) {
		this.toCashplus = toCashplus;
	}

	public BigDecimal getTxferableCardValue() {
		return txferableCardValue;
	}
	public BigDecimal getTxferableCashplus() {
		return txferableCashplus;
	}
	public void setTxferableCardValue(BigDecimal txferableCardValue) {
		this.txferableCardValue = txferableCardValue;
	}
	public void setTxferableCashplus(BigDecimal txferableCashplus) {
		this.txferableCashplus = txferableCashplus;
	}
	public BigDecimal getTransferCashplus() {
		return transferCashplus;
	}
	public void setTransferCashplus(BigDecimal transferCashplus) {
		this.transferCashplus = transferCashplus;
	}
	public BigDecimal getTransferCardValue() {
		return transferCardValue;
	}
	public void setTransferCardValue(BigDecimal transferCardValue) {
		this.transferCardValue = transferCardValue;
	}
	public String getTransferAllFlag() {
		return transferAllFlag;
	}
	public void setTransferAllFlag(String transferAllFlag) {
		this.transferAllFlag = transferAllFlag;
	}
	
	
	
	
	
}
