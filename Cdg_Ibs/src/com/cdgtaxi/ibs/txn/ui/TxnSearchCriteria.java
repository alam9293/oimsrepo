package com.cdgtaxi.ibs.txn.ui;

import com.cdgtaxi.ibs.common.model.AmtbAccount;

public class TxnSearchCriteria {
	
	private AmtbAccount amtbAccount=null;
	private String cardNoStart=null;
	private String cardNoEnd=null;
	private String productType=null;
	private String txnStatus=null;
	private String taxiNo = null;
	private String tripStartDate=null;
	private String tripEndDate=null;
	private String jobNo=null;
	private String nric=null;
	private String fareAmt = null;
	private String tripStartTime = null;
	private String tripEndTime = null;
	private String salesDraftNo = null;
	private String pickup = null;
	private String destination = null;
	
	public String getSalesDraftNo() {
		return salesDraftNo;
	}
	public void setSalesDraftNo(String salesDraftNo) {
		this.salesDraftNo = salesDraftNo;
	}
	public String getPickup() {
		return pickup;
	}
	public void setPickup(String pickup) {
		this.pickup = pickup;
	}
	public String getDestination() {
		return destination;
	}
	public void setDestination(String destination) {
		this.destination = destination;
	}
	public String getCardNoStart() {
		return cardNoStart;
	}
	public void setCardNoStart(String cardNoStart) {
		this.cardNoStart = cardNoStart;
	}
	public String getCardNoEnd() {
		return cardNoEnd;
	}
	public void setCardNoEnd(String cardNoEnd) {
		this.cardNoEnd = cardNoEnd;
	}
	public String getProductType() {
		return productType;
	}
	public void setProductType(String productType) {
		this.productType = productType;
	}
	public void setAmtbAccount(AmtbAccount amtbAccount) {
		this.amtbAccount = amtbAccount;
	}
	public AmtbAccount getAmtbAccount() {
		return amtbAccount;
	}
	public String getTxnStatus() {
		return txnStatus;
	}
	public void setTxnStatus(String txnStatus) {
		this.txnStatus = txnStatus;
	}
	public String getTripStartDate() {
		return tripStartDate;
	}
	public void setTripStartDate(String tripStartDate) {
		this.tripStartDate = tripStartDate;
	}
	public String getTripEndDate() {
		return tripEndDate;
	}
	public void setTripEndDate(String tripEndDate) {
		this.tripEndDate = tripEndDate;
	}
	public String getJobNo() {
		return jobNo;
	}
	public void setJobNo(String jobNo) {
		this.jobNo = jobNo;
	}
	public String getNric() {
		return nric;
	}
	public void setNric(String nric) {
		this.nric = nric;
	}
	public String getFareAmt() {
		return fareAmt;
	}
	public void setFareAmt(String fareAmt) {
		this.fareAmt = fareAmt;
	}
	public String getTripStartTime() {
		return tripStartTime;
	}
	public void setTripStartTime(String tripStartTime) {
		this.tripStartTime = tripStartTime;
	}
	public String getTripEndTime() {
		return tripEndTime;
	}
	public void setTripEndTime(String tripEndTime) {
		this.tripEndTime = tripEndTime;
	}
	public void setTaxiNo(String taxiNo) {
		this.taxiNo = taxiNo;
	}
	public String getTaxiNo() {
		return taxiNo;
	}
	
}
