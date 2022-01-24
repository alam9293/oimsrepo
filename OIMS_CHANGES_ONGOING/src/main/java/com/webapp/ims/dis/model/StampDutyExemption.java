package com.webapp.ims.dis.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="Stamp_Duty_Exemption", schema="loc")
public class StampDutyExemption implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name = "Stamp_ID")
	private String stampId;
	
	@Column(name = "Apc_Id")
	private String apcId;

	

	public String getStampId() {
		return stampId;
	}

	public void setStampId(String stampId) {
		this.stampId = stampId;
	}

	@Column(name = "Computation_Fin_Yr")
	private String computationFinYr;
	
	@Column(name = "Stamp_Duty_Date_From")
	private String stampDutyDateFrom;
	
	@Column(name = "Stamp_Duty_Date_To")
	private String stampDutyDateTo;
	

	

	@Column(name = "Claim_Stamp_Duty_Reim_Amt")
	private String claimStampDutyReimAmt;

	@Column(name = "Stamp_Duty_Reim_Elig")
	private String stampDutyReimElig;

	


	



	public String getApcId() {
		return apcId;
	}

	public void setApcId(String apcId) {
		this.apcId = apcId;
	}
	

	
	public String getComputationFinYr() {
		return computationFinYr;
	}

	public void setComputationFinYr(String computationFinYr) {
		this.computationFinYr = computationFinYr;
	}

	public String getStampDutyDateFrom() {
		return stampDutyDateFrom;
	}

	public void setStampDutyDateFrom(String stampDutyDateFrom) {
		this.stampDutyDateFrom = stampDutyDateFrom;
	}

	public String getStampDutyDateTo() {
		return stampDutyDateTo;
	}

	public void setStampDutyDateTo(String stampDutyDateTo) {
		this.stampDutyDateTo = stampDutyDateTo;
	}

	
	public String getClaimStampDutyReimAmt() {
		return claimStampDutyReimAmt;
	}

	public void setClaimStampDutyReimAmt(String claimStampDutyReimAmt) {
		this.claimStampDutyReimAmt = claimStampDutyReimAmt;
	}

	public String getStampDutyReimElig() {
		return stampDutyReimElig;
	}

	public void setStampDutyReimElig(String stampDutyReimElig) {
		this.stampDutyReimElig = stampDutyReimElig;
	}

	

	
	
	
	
	
}
