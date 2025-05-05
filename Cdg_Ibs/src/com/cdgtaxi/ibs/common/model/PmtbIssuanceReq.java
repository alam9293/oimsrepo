package com.cdgtaxi.ibs.common.model;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

import com.cdgtaxi.ibs.master.model.MstbCreditTermMaster;



@SuppressWarnings("serial")
public class PmtbIssuanceReq extends PmtbPrepaidReq implements java.io.Serializable {

	private BigDecimal discount;
	private BigDecimal deliveryCharge;
	//this field only serve as a display purpose in the view details
	private BigDecimal totalAmount;
	private MstbCreditTermMaster mstbCreditTermMaster;
	private Set<PmtbIssuanceReqCard> pmtbIssuanceReqCards;
	
	private transient List<PmtbIssuanceReqCard> holderCardList;
	private FmtbTransactionCode deliveryChargeTxnCode;
	
	public BigDecimal getDiscount() {
		return discount;
	}
	public BigDecimal getDeliveryCharge() {
		return deliveryCharge;
	}
	
	public void setDiscount(BigDecimal discount) {
		this.discount = discount;
	}
	public void setDeliveryCharge(BigDecimal deliveryCharge) {
		this.deliveryCharge = deliveryCharge;
	}
	
	public BigDecimal getTotalAmount() {
		return totalAmount;
	}
	public void setTotalAmount(BigDecimal totalAmount) {
		this.totalAmount = totalAmount;
	}
	public Set<PmtbIssuanceReqCard> getPmtbIssuanceReqCards() {
		return pmtbIssuanceReqCards;
	}
	public void setPmtbIssuanceReqCards(Set<PmtbIssuanceReqCard> pmtbIssuanceReqCards) {
		this.pmtbIssuanceReqCards = pmtbIssuanceReqCards;
	}
	public MstbCreditTermMaster getMstbCreditTermMaster() {
		return mstbCreditTermMaster;
	}
	public void setMstbCreditTermMaster(MstbCreditTermMaster mstbCreditTermMaster) {
		this.mstbCreditTermMaster = mstbCreditTermMaster;
	}
	public List<PmtbIssuanceReqCard> getHolderCardList() {
		return holderCardList;
	}
	public void setHolderCardList(List<PmtbIssuanceReqCard> holderCardList) {
		this.holderCardList = holderCardList;
	}
	public FmtbTransactionCode getDeliveryChargeTxnCode() {
		return deliveryChargeTxnCode;
	}
	public void setDeliveryChargeTxnCode(FmtbTransactionCode deliveryChargeTxnCode) {
		this.deliveryChargeTxnCode = deliveryChargeTxnCode;
	}
	
}
	
