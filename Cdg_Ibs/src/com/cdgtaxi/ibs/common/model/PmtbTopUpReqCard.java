package com.cdgtaxi.ibs.common.model;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Set;

import com.cdgtaxi.ibs.common.NonConfigurableConstants;
import com.cdgtaxi.ibs.master.model.MstbPromotionCashPlus;



@SuppressWarnings("serial")
public class PmtbTopUpReqCard implements java.io.Serializable {

	private BigDecimal reqCardNo;
	private PmtbTopUpReq pmtbTopUpReq;
    private PmtbProduct pmtbProduct;
	private BigDecimal topUpFee;
    private String waiveTopUpFeeFlag = NonConfigurableConstants.BOOLEAN_NO;
    private Date newBalanceExpiryDate;
    private BigDecimal topUpValue;
    private BigDecimal topUpCashplus;
    private Set <MstbPromotionCashPlus> mstbPromotionCashPluses;
    private Set <BmtbTopUpInvoiceTxn> bmtbTopUpInvoiceTxns;
    
    private transient boolean isSelected;
    
	public BigDecimal getReqCardNo() {
		return reqCardNo;
	}
	public PmtbProduct getPmtbProduct() {
		return pmtbProduct;
	}
	public String getWaiveTopUpFeeFlag() {
		return waiveTopUpFeeFlag;
	}
	public Date getNewBalanceExpiryDate() {
		return newBalanceExpiryDate;
	}
	public BigDecimal getTopUpValue() {
		return topUpValue;
	}
	public void setReqCardNo(BigDecimal reqCardNo) {
		this.reqCardNo = reqCardNo;
	}
	public void setPmtbProduct(PmtbProduct pmtbProduct) {
		this.pmtbProduct = pmtbProduct;
	}
	public BigDecimal getTopUpFee() {
		return topUpFee;
	}
	public void setTopUpFee(BigDecimal topUpFee) {
		this.topUpFee = topUpFee;
	}
	public void setWaiveTopUpFeeFlag(String waiveTopUpFeeFlag) {
		this.waiveTopUpFeeFlag = waiveTopUpFeeFlag;
	}
	public void setNewBalanceExpiryDate(Date newBalanceExpiryDate) {
		this.newBalanceExpiryDate = newBalanceExpiryDate;
	}
	public void setTopUpValue(BigDecimal topUpValue) {
		this.topUpValue = topUpValue;
	}
	public Set<MstbPromotionCashPlus> getMstbPromotionCashPluses() {
		return mstbPromotionCashPluses;
	}
	public void setMstbPromotionCashPluses(Set<MstbPromotionCashPlus> mstbPromotionCashPluses) {
		this.mstbPromotionCashPluses = mstbPromotionCashPluses;
	}
	public PmtbTopUpReq getPmtbTopUpReq() {
		return pmtbTopUpReq;
	}
	public void setPmtbTopUpReq(PmtbTopUpReq pmtbTopUpReq) {
		this.pmtbTopUpReq = pmtbTopUpReq;
	}
	public Set<BmtbTopUpInvoiceTxn> getBmtbTopUpInvoiceTxns() {
		return bmtbTopUpInvoiceTxns;
	}
	public void setBmtbTopUpInvoiceTxns(Set<BmtbTopUpInvoiceTxn> bmtbTopUpInvoiceTxns) {
		this.bmtbTopUpInvoiceTxns = bmtbTopUpInvoiceTxns;
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
		PmtbTopUpReqCard other = (PmtbTopUpReqCard) obj;
		if (reqCardNo == null) {
			if (other.reqCardNo != null)
				return false;
		} else if (!reqCardNo.equals(other.reqCardNo))
			return false;
		return true;
	}
	public BigDecimal getTopUpCashplus() {
		return topUpCashplus;
	}
	public void setTopUpCashplus(BigDecimal topUpCashplus) {
		this.topUpCashplus = topUpCashplus;
	}
	public boolean isSelected() {
		return isSelected;
	}
	public void setSelected(boolean isSelected) {
		this.isSelected = isSelected;
	}
    
  

	
	
	
	
	
	
	
}
