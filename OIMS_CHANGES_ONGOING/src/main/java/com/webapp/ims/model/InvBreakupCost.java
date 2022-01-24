package com.webapp.ims.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "INV_Breakupcost", schema = "loc")
public class InvBreakupCost implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "Brkupco_ID")
	private String brkupId;
	@Column(name = "Brkupco_Parameter")
	private String brkupParameter;
	@Column(name = "Brkupco_Amount")
	private Long brkupAmount;
	@Column(name = "Brkupco_Inv_Id")
	private String brkupInvId;
	@Column(name = "Brkupco_Created_By")
	private String brkupCreatedBy;
	@Column(name = "Brkupco_Modified_By")
	private String brkupModifiedBy;
	@Column(name = "Brkupco_Modify_Date")
	@Temporal(TemporalType.DATE)
	private Date brkupModifyDate;
	@Column(name = "Brkupco_Status")
	private String brkupStatus;

	public InvBreakupCost() {
		super();
	}

	public String getBrkupStatus() {
		return brkupStatus;
	}

	public void setBrkupStatus(String brkupStatus) {
		this.brkupStatus = brkupStatus;
	}

	public String getBrkupId() {
		return brkupId;
	}

	public void setBrkupId(String brkupId) {
		this.brkupId = brkupId;
	}

	public String getBrkupParameter() {
		return brkupParameter;
	}

	public void setBrkupParameter(String brkupParameter) {
		this.brkupParameter = brkupParameter;
	}

	public Long getBrkupAmount() {
		return brkupAmount;
	}

	public void setBrkupAmount(Long brkupAmount) {
		this.brkupAmount = brkupAmount;
	}

	public String getBrkupInvId() {
		return brkupInvId;
	}

	public void setBrkupInvId(String brkupInvId) {
		this.brkupInvId = brkupInvId;
	}

	public String getBrkupCreatedBy() {
		return brkupCreatedBy;
	}

	public void setBrkupCreatedBy(String brkupCreatedBy) {
		this.brkupCreatedBy = brkupCreatedBy;
	}

	public String getBrkupModifiedBy() {
		return brkupModifiedBy;
	}

	public void setBrkupModifiedBy(String brkupModifiedBy) {
		this.brkupModifiedBy = brkupModifiedBy;
	}

	public Date getBrkupModifyDate() {
		return brkupModifyDate;
	}

	public void setBrkupModifyDate(Date brkupModifyDate) {
		this.brkupModifyDate = brkupModifyDate;
	}

}
