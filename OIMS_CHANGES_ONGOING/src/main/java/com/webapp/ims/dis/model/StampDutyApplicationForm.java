package com.webapp.ims.dis.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="Stamp_Duty_Application_Form", schema="loc")
public class StampDutyApplicationForm implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "Stamp_ID")
	private String stampId;
	
	@Column(name = "Stamp_APC_ID")
	private String stampApcId;
	
	@Column(name = "Stamp_Duty_Fin_Yr")
	private String stampDutyFinYr;
	
	@Column(name = "Duration_From")
	private String durationFrom;
	
	@Column(name = "Duration_To")
	private String durationTo;
	
	@Column(name = "Claim_Reim_Amt")
	private String claimReimAmt;
	
	

	public String getStampApcId() {
		return stampApcId;
	}

	public void setStampApcId(String stampApcId) {
		this.stampApcId = stampApcId;
	}

	public String getStampDutyFinYr() {
		return stampDutyFinYr;
	}

	public void setStampDutyFinYr(String stampDutyFinYr) {
		this.stampDutyFinYr = stampDutyFinYr;
	}

	public String getDurationFrom() {
		return durationFrom;
	}

	public void setDurationFrom(String durationFrom) {
		this.durationFrom = durationFrom;
	}

	public String getDurationTo() {
		return durationTo;
	}

	public void setDurationTo(String durationTo) {
		this.durationTo = durationTo;
	}

	public String getClaimReimAmt() {
		return claimReimAmt;
	}

	public void setClaimReimAmt(String claimReimAmt) {
		this.claimReimAmt = claimReimAmt;
	}
	
	public String getStampId() {
		return stampId;
	}

	public void setStampId(String stampId) {
		this.stampId = stampId;
	}

}
