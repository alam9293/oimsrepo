package com.cdgtaxi.ibs.common.model;

import java.math.BigDecimal;
import java.sql.Timestamp;


@SuppressWarnings("serial")
public class BmtbTopUpInvoiceTxn  implements java.io.Serializable{

    private BigDecimal txnNo;
    private BigDecimal amount;
    private BigDecimal discount;
    private BigDecimal gst;
    private String txnType;
    private PmtbTopUpReqCard pmtbTopUpReqCard;
    private BmtbInvoiceSummary bmtbInvoiceSummary;
    private Timestamp createdDt;
    private Integer version;
    private String description;
	
	public BigDecimal getTxnNo() {
		return txnNo;
	}
	public BigDecimal getAmount() {
		return amount;
	}
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
	public String getTxnType() {
		return txnType;
	}

	public Timestamp getCreatedDt() {
		return createdDt;
	}
	public Integer getVersion() {
		return version;
	}
	public void setTxnNo(BigDecimal txnNo) {
		this.txnNo = txnNo;
	}
	public void setTxnType(String txnType) {
		this.txnType = txnType;
	}
	
	public void setCreatedDt(Timestamp createdDt) {
		this.createdDt = createdDt;
	}
	public void setVersion(Integer version) {
		this.version = version;
	}

	public PmtbTopUpReqCard getPmtbTopUpReqCard() {
		return pmtbTopUpReqCard;
	}
	public void setPmtbTopUpReqCard(PmtbTopUpReqCard pmtbTopUpReqCard) {
		this.pmtbTopUpReqCard = pmtbTopUpReqCard;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	public BigDecimal getDiscount() {
		return discount;
	}
	public BigDecimal getGst() {
		return gst;
	}
	public void setDiscount(BigDecimal discount) {
		this.discount = discount;
	}
	public void setGst(BigDecimal gst) {
		this.gst = gst;
	}
	public BmtbInvoiceSummary getBmtbInvoiceSummary() {
		return bmtbInvoiceSummary;
	}
	public void setBmtbInvoiceSummary(BmtbInvoiceSummary bmtbInvoiceSummary) {
		this.bmtbInvoiceSummary = bmtbInvoiceSummary;
	}
	


	
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((txnNo == null) ? 0 : txnNo.hashCode());
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
		BmtbTopUpInvoiceTxn other = (BmtbTopUpInvoiceTxn) obj;
		if (txnNo == null) {
			if (other.txnNo != null)
				return false;
		} else if (!txnNo.equals(other.txnNo))
			return false;
		return true;
	}
	public static BmtbTopUpInvoiceTxn buildTxn(PmtbTopUpReqCard pmtbTopUpReqCard, String txnType, BigDecimal amount, BigDecimal discount, BigDecimal gst){
		BmtbTopUpInvoiceTxn txn = new BmtbTopUpInvoiceTxn();
		txn.setTxnType(txnType);
		txn.setAmount(amount);
		txn.setPmtbTopUpReqCard(pmtbTopUpReqCard);
		
		if(discount==null){
			discount = BigDecimal.valueOf(0);
		}
		if(gst==null){
			gst = BigDecimal.valueOf(0);
		}
		txn.setDiscount(discount);
		txn.setGst(gst);
		
		return txn;
	}
	
}
