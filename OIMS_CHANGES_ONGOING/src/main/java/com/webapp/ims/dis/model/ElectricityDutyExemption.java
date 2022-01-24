/**
 * Author:: Gyan
* Created on:: 15/02/2021 
 */

package com.webapp.ims.dis.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "Electricity_Duty_Exemption", schema = "loc")
public class ElectricityDutyExemption implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "Electric_Duty_Exe_Id")
	private String electricDutyExeId;

	@Column(name = "Apc_Id")
	private String apcId;

	@Column(name = "Electricity_Duty_Exe_FinYr")
	private String electricityDutyExeFinYr;

	@Column(name = "Electricity_Date_From")
	private String electricityDateFrom;

	@Column(name = "Electricity_Date_To")
	private String electricityDateTo;

	@Column(name = "Electricity_Amt_Claim")
	private String electricityAmtClaim;

	@Column(name = "Electricity_Elig_Amt")
	private String electricityEligAmt;
	
	@Column(name = "Electricity_Type_Status")
	private String electricityTypeStatus;
	

	public String getElectricDutyExeId() {
		return electricDutyExeId;
	}

	public void setElectricDutyExeId(String electricDutyExeId) {
		this.electricDutyExeId = electricDutyExeId;
	}

	public String getApcId() {
		return apcId;
	}

	public void setApcId(String apcId) {
		this.apcId = apcId;
	}

	public String getElectricityDutyExeFinYr() {
		return electricityDutyExeFinYr;
	}

	public void setElectricityDutyExeFinYr(String electricityDutyExeFinYr) {
		this.electricityDutyExeFinYr = electricityDutyExeFinYr;
	}

	public String getElectricityDateFrom() {
		return electricityDateFrom;
	}

	public void setElectricityDateFrom(String electricityDateFrom) {
		this.electricityDateFrom = electricityDateFrom;
	}

	public String getElectricityDateTo() {
		return electricityDateTo;
	}

	public void setElectricityDateTo(String electricityDateTo) {
		this.electricityDateTo = electricityDateTo;
	}

	public String getElectricityAmtClaim() {
		return electricityAmtClaim;
	}

	public void setElectricityAmtClaim(String electricityAmtClaim) {
		this.electricityAmtClaim = electricityAmtClaim;
	}

	public String getElectricityEligAmt() {
		return electricityEligAmt;
	}

	public void setElectricityEligAmt(String electricityEligAmt) {
		this.electricityEligAmt = electricityEligAmt;
	}

	public String getElectricityTypeStatus() {
		return electricityTypeStatus;
	}

	public void setElectricityTypeStatus(String electricityTypeStatus) {
		this.electricityTypeStatus = electricityTypeStatus;
	}

	
	

	

}
