package com.cdgtaxi.ibs.common.model;

import java.math.BigDecimal;
import java.util.Date;

@SuppressWarnings("serial")
public class PmtbBalanceForfeiture implements java.io.Serializable{

	private BigDecimal balanceForfeitureNo;
	private BigDecimal forfeitCardValue;
	private BigDecimal forfeitCashplus;
	private Date forfeitedDate;
	private PmtbProduct pmtbProduct;
	private Integer version;
	
	private BigDecimal forfeitCardValueGstRate;
	private BigDecimal forfeitCashplusGstRate;

	public PmtbBalanceForfeiture() {
	}

	public BigDecimal getBalanceForfeitureNo() {
		return balanceForfeitureNo;
	}

	public void setBalanceForfeitureNo(BigDecimal balanceForfeitureNo) {
		this.balanceForfeitureNo = balanceForfeitureNo;
	}

	public BigDecimal getForfeitCardValue() {
		return forfeitCardValue;
	}

	public void setForfeitCardValue(BigDecimal forfeitCardValue) {
		this.forfeitCardValue = forfeitCardValue;
	}

	public BigDecimal getForfeitCashplus() {
		return forfeitCashplus;
	}

	public void setForfeitCashplus(BigDecimal forfeitCashplus) {
		this.forfeitCashplus = forfeitCashplus;
	}

	public Date getForfeitedDate() {
		return forfeitedDate;
	}

	public void setForfeitedDate(Date forfeitedDate) {
		this.forfeitedDate = forfeitedDate;
	}

	public PmtbProduct getPmtbProduct() {
		return pmtbProduct;
	}

	public void setPmtbProduct(PmtbProduct pmtbProduct) {
		this.pmtbProduct = pmtbProduct;
	}

	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

	public BigDecimal getForfeitCardValueGstRate() {
		return forfeitCardValueGstRate;
	}

	public void setForfeitCardValueGstRate(BigDecimal forfeitCardValueGstRate) {
		this.forfeitCardValueGstRate = forfeitCardValueGstRate;
	}

	public BigDecimal getForfeitCashplusGstRate() {
		return forfeitCashplusGstRate;
	}

	public void setForfeitCashplusGstRate(BigDecimal forfeitCashplusGstRate) {
		this.forfeitCashplusGstRate = forfeitCashplusGstRate;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((balanceForfeitureNo == null) ? 0 : balanceForfeitureNo.hashCode());
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
		PmtbBalanceForfeiture other = (PmtbBalanceForfeiture) obj;
		if (balanceForfeitureNo == null) {
			if (other.balanceForfeitureNo != null)
				return false;
		} else if (!balanceForfeitureNo.equals(other.balanceForfeitureNo))
			return false;
		return true;
	}

	
	

	

}