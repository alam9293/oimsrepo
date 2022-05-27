package com.cdgtaxi.ibs.common.model;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Set;

import com.cdgtaxi.ibs.common.NonConfigurableConstants;
import com.cdgtaxi.ibs.util.DateUtil;


@SuppressWarnings("serial")
public class PmtbPrepaidCardTxn  implements java.io.Serializable{

    private BigDecimal txnNo;
    // the amount is amount without GST
    private BigDecimal amount;
    private BigDecimal gst;
    private String txnType;
    private PmtbProduct pmtbProduct;
    private PmtbPrepaidReq pmtbPrepaidReq;
    private TmtbAcquireTxn tmtbAcquireTxn;
    private String remarks;
    private Date txnDate;
    private Integer version;
    private String glableFlag;
    private FmtbTransactionCode fmtbTransactionCode;
    private Set<FmtbGlLogDetail> fmtbGlLogDetails;
    
    // the apply amount is the amount that will deduct from card value
    private BigDecimal applyCardValue;
    // the apply amount is the amount that will deduct from cash plus
    private BigDecimal applyCashplus;
    
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

	public Date getTxnDate() {
		return txnDate;
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
	
	public void setTxnDate(Date txnDate) {
		this.txnDate = txnDate;
	}
	public void setVersion(Integer version) {
		this.version = version;
	}
	public void setPmtbProduct(PmtbProduct pmtbProduct) {
		this.pmtbProduct = pmtbProduct;
	}
	public PmtbProduct getPmtbProduct() {
		return pmtbProduct;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public BigDecimal getGst() {
		return gst;
	}
	public void setGst(BigDecimal gst) {
		this.gst = gst;
	}
	
	public String getGlableFlag() {
		return glableFlag;
	}
	
	public void setGlableFlag(String glableFlag) {
		this.glableFlag = glableFlag;
	}
	
	
	
    
	public static PmtbPrepaidCardTxn buildTripTxn(TmtbAcquireTxn tmtbAcquireTxn, String txnType, BigDecimal amount, BigDecimal applyCardValue, BigDecimal applyCashplus, boolean glable){
		
		PmtbProduct product = tmtbAcquireTxn.getPmtbProduct();
		PmtbPrepaidCardTxn txn = buildTxn(null, product, txnType, amount, null, applyCardValue, applyCashplus, glable);
		txn.setTmtbAcquireTxn(tmtbAcquireTxn);
		
		
		return txn;
	}
	
	public static PmtbPrepaidCardTxn buildTxn(PmtbPrepaidReq req, PmtbProduct product, String txnType, BigDecimal amount, BigDecimal gst, BigDecimal applyCardValue, BigDecimal applyCashplus, boolean glable){
		PmtbPrepaidCardTxn txn = new PmtbPrepaidCardTxn();
		
		if(gst==null){
			gst = BigDecimal.ZERO;
		}
		if(applyCardValue==null){
			applyCardValue = BigDecimal.ZERO;
		}
		if(applyCashplus==null){
			applyCashplus = BigDecimal.ZERO;
		}
		txn.setTxnType(txnType);
		txn.setAmount(amount);
		txn.setGst(gst);
		txn.setPmtbProduct(product);
		txn.setPmtbPrepaidReq(req);
		txn.setGlableFlag(NonConfigurableConstants.getBooleanFlag(glable));
		txn.setApplyCardValue(applyCardValue);
		txn.setApplyCashplus(applyCashplus);
		txn.setTxnDate(DateUtil.getCurrentTimestamp());
		
		return txn;
	}
	public PmtbPrepaidReq getPmtbPrepaidReq() {
		return pmtbPrepaidReq;
	}
	public void setPmtbPrepaidReq(PmtbPrepaidReq pmtbPrepaidReq) {
		this.pmtbPrepaidReq = pmtbPrepaidReq;
	}
	public Set<FmtbGlLogDetail> getFmtbGlLogDetails() {
		return fmtbGlLogDetails;
	}
	public void setFmtbGlLogDetails(Set<FmtbGlLogDetail> fmtbGlLogDetails) {
		this.fmtbGlLogDetails = fmtbGlLogDetails;
	}
	public TmtbAcquireTxn getTmtbAcquireTxn() {
		return tmtbAcquireTxn;
	}
	public void setTmtbAcquireTxn(TmtbAcquireTxn tmtbAcquireTxn) {
		this.tmtbAcquireTxn = tmtbAcquireTxn;
	}
	public FmtbTransactionCode getFmtbTransactionCode() {
		return fmtbTransactionCode;
	}
	public void setFmtbTransactionCode(FmtbTransactionCode fmtbTransactionCode) {
		this.fmtbTransactionCode = fmtbTransactionCode;
	}
	public BigDecimal getApplyCardValue() {
		return applyCardValue;
	}
	public void setApplyCardValue(BigDecimal applyCardValue) {
		this.applyCardValue = applyCardValue;
	}
	public BigDecimal getApplyCashplus() {
		return applyCashplus;
	}
	public void setApplyCashplus(BigDecimal applyCashplus) {
		this.applyCashplus = applyCashplus;
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
		PmtbPrepaidCardTxn other = (PmtbPrepaidCardTxn) obj;
		if (txnNo == null) {
			if (other.txnNo != null)
				return false;
		} else if (!txnNo.equals(other.txnNo))
			return false;
		return true;
	}
	

	
	

   

}
