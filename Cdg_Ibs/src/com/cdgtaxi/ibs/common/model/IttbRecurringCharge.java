package com.cdgtaxi.ibs.common.model;
// Generated Jul 20, 2009 4:09:25 PM by Hibernate Tools 3.1.0.beta4

import java.sql.Timestamp;
import java.util.Date;
import java.util.Set;

import com.cdgtaxi.ibs.acl.security.Auditable;

/**
 * BmtbNote generated by hbm2java
 */

public class IttbRecurringCharge implements java.io.Serializable, Auditable {

	// Fields

	private Integer id;
	private String tokenId;
	private Date tokenExpiry;
	private String creditCardNo;
	private Date creditCardNoExpiry;
	
	private Timestamp createdDt;
	private Timestamp updatedDt;
	private String updatedBy;
	private String createdBy;
	
	// Constructors

	/** default constructor */
	public IttbRecurringCharge() {
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTokenId() {
		return tokenId;
	}

	public void setTokenId(String tokenId) {
		this.tokenId = tokenId;
	}

	public Date getTokenExpiry() {
		return tokenExpiry;
	}

	public void setTokenExpiry(Date tokenExpiry) {
		this.tokenExpiry = tokenExpiry;
	}

	public String getCreditCardNo() {
		return creditCardNo;
	}

	public void setCreditCardNo(String creditCardNo) {
		this.creditCardNo = creditCardNo;
	}

	public Date getCreditCardNoExpiry() {
		return creditCardNoExpiry;
	}

	public void setCreditCardNoExpiry(Date creditCardNoExpiry) {
		this.creditCardNoExpiry = creditCardNoExpiry;
	}

	private Set<IttbRecurringChargeTagAcct> ittbRecurringChargeTagAcct;

	public Set<IttbRecurringChargeTagAcct> getIttbRecurringChargeTagAcct() {
		return ittbRecurringChargeTagAcct;
	}

	public void setIttbRecurringChargeTagAcct(Set<IttbRecurringChargeTagAcct> ittbRecurringChargeTagAcct) {
		this.ittbRecurringChargeTagAcct = ittbRecurringChargeTagAcct;
	}

	private Set<IttbRecurringChargeTagCard> ittbRecurringChargeTagCard;

	public Set<IttbRecurringChargeTagCard> getIttbRecurringChargeTagCard() {
		return ittbRecurringChargeTagCard;
	}

	public void setIttbRecurringChargeTagCard(Set<IttbRecurringChargeTagCard> ittbRecurringChargeTagCard) {
		this.ittbRecurringChargeTagCard = ittbRecurringChargeTagCard;
	}

	public Timestamp getCreatedDt() {
		return createdDt;
	}

	public void setCreatedDt(Timestamp createdDt) {
		this.createdDt = createdDt;
	}

	public Timestamp getUpdatedDt() {
		return updatedDt;
	}

	public void setUpdatedDt(Timestamp updatedDt) {
		this.updatedDt = updatedDt;
	}

	public String getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
}
