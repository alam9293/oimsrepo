package com.cdgtaxi.ibs.common.model;

import java.math.BigDecimal;




@SuppressWarnings("serial")
public class PmtbAdjustmentReq extends PmtbPrepaidReq implements java.io.Serializable {

	private BigDecimal oriValueAmount;
	private BigDecimal oriCashplusAmount;
	private BigDecimal adjustValueAmount;
	private BigDecimal adjustCashplusAmount;
	private PmtbProduct pmtbProduct;
	private FmtbTransactionCode adjustValueTxnCode;
	private FmtbTransactionCode adjustCashplusTxnCode;
	
	private BigDecimal adjustValueGst;
	private BigDecimal adjustCashplusGst;
	
	public BigDecimal getAdjustValueAmount() {
		return adjustValueAmount;
	}
	public BigDecimal getAdjustCashplusAmount() {
		return adjustCashplusAmount;
	}

	public PmtbProduct getPmtbProduct() {
		return pmtbProduct;
	}
	public void setAdjustValueAmount(BigDecimal adjustValueAmount) {
		this.adjustValueAmount = adjustValueAmount;
	}
	public void setAdjustCashplusAmount(BigDecimal adjustCashplusAmount) {
		this.adjustCashplusAmount = adjustCashplusAmount;
	}
	
	public void setPmtbProduct(PmtbProduct pmtbProduct) {
		this.pmtbProduct = pmtbProduct;
	}
	public BigDecimal getOriValueAmount() {
		return oriValueAmount;
	}
	public BigDecimal getOriCashplusAmount() {
		return oriCashplusAmount;
	}
	public void setOriValueAmount(BigDecimal oriValueAmount) {
		this.oriValueAmount = oriValueAmount;
	}
	public void setOriCashplusAmount(BigDecimal oriCashplusAmount) {
		this.oriCashplusAmount = oriCashplusAmount;
	}
	public BigDecimal getAdjustValueGst() {
		return adjustValueGst;
	}
	public void setAdjustValueGst(BigDecimal adjustValueGst) {
		this.adjustValueGst = adjustValueGst;
	}
	public BigDecimal getAdjustCashplusGst() {
		return adjustCashplusGst;
	}
	public void setAdjustCashplusGst(BigDecimal adjustCashplusGst) {
		this.adjustCashplusGst = adjustCashplusGst;
	}
	public FmtbTransactionCode getAdjustValueTxnCode() {
		return adjustValueTxnCode;
	}
	public void setAdjustValueTxnCode(FmtbTransactionCode adjustValueTxnCode) {
		this.adjustValueTxnCode = adjustValueTxnCode;
	}
	public FmtbTransactionCode getAdjustCashplusTxnCode() {
		return adjustCashplusTxnCode;
	}
	public void setAdjustCashplusTxnCode(FmtbTransactionCode adjustCashplusTxnCode) {
		this.adjustCashplusTxnCode = adjustCashplusTxnCode;
	}
	
	

	
	
	
}
