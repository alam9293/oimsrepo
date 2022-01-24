package com.webapp.ims.dis.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="Dis_Reim_Disallowed_Input",  schema = "loc")
public class DisReimDisallowedInput implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "Evaluate_Id")
	private String disallowedId;

	@Column(name = "Apc_Id")
	private String apcId;
	
	@Column(name = "Disallowed_Fin_Yr")
	private String disallowedFinYr;
	
	@Column(name = "Disallowed_Date_Fr")
	private String disallowedDateFr;
	
	@Column(name = "Disallowed_Date_To")
	private String disallowedDateTo;
	
	@Column(name = "Disallowed_Claim_Amt")
	private String disallowedClaimAmt;

	@Column(name = "Disallowed_Elig_Amt")
	private String disallowedEligAmt;

	public String getDisallowedId() {
		return disallowedId;
	}

	public void setDisallowedId(String disallowedId) {
		this.disallowedId = disallowedId;
	}

	public String getApcId() {
		return apcId;
	}

	public void setApcId(String apcId) {
		this.apcId = apcId;
	}

	public String getDisallowedFinYr() {
		return disallowedFinYr;
	}

	public void setDisallowedFinYr(String disallowedFinYr) {
		this.disallowedFinYr = disallowedFinYr;
	}

	public String getDisallowedDateFr() {
		return disallowedDateFr;
	}

	public void setDisallowedDateFr(String disallowedDateFr) {
		this.disallowedDateFr = disallowedDateFr;
	}

	public String getDisallowedDateTo() {
		return disallowedDateTo;
	}

	public void setDisallowedDateTo(String disallowedDateTo) {
		this.disallowedDateTo = disallowedDateTo;
	}

	public String getDisallowedClaimAmt() {
		return disallowedClaimAmt;
	}

	public void setDisallowedClaimAmt(String disallowedClaimAmt) {
		this.disallowedClaimAmt = disallowedClaimAmt;
	}

	public String getDisallowedEligAmt() {
		return disallowedEligAmt;
	}

	public void setDisallowedEligAmt(String disallowedEligAmt) {
		this.disallowedEligAmt = disallowedEligAmt;
	}
	
	
}
