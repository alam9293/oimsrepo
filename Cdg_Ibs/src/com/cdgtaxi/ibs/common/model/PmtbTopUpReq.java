package com.cdgtaxi.ibs.common.model;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

import com.cdgtaxi.ibs.master.model.MstbCreditTermMaster;




@SuppressWarnings("serial")
public class PmtbTopUpReq extends PmtbPrepaidReq implements java.io.Serializable {


	//this field only serve as a display purpose in the view details
	private BigDecimal totalAmount;
	private MstbCreditTermMaster mstbCreditTermMaster;
	private Set<PmtbTopUpReqCard> pmtbTopUpReqCards;
	private transient List<PmtbTopUpReqCard> holderCardList;

	public BigDecimal getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(BigDecimal totalAmount) {
		this.totalAmount = totalAmount;
	}

	public MstbCreditTermMaster getMstbCreditTermMaster() {
		return mstbCreditTermMaster;
	}
	public void setMstbCreditTermMaster(MstbCreditTermMaster mstbCreditTermMaster) {
		this.mstbCreditTermMaster = mstbCreditTermMaster;
	}
	public Set<PmtbTopUpReqCard> getPmtbTopUpReqCards() {
		return pmtbTopUpReqCards;
	}
	public void setPmtbTopUpReqCards(Set<PmtbTopUpReqCard> pmtbTopUpReqCards) {
		this.pmtbTopUpReqCards = pmtbTopUpReqCards;
	}

	public List<PmtbTopUpReqCard> getHolderCardList() {
		return holderCardList;
	}

	public void setHolderCardList(List<PmtbTopUpReqCard> holderCardList) {
		this.holderCardList = holderCardList;
	}


	
	
	
}
