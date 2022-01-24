package com.webapp.ims.dis.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "Dis_Other_Incentive", schema = "loc")
public class OtherIncentive implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "OtherInc_Id")
	private String id;

	@Column(name = "OtherInc_ApcId")
	private String othApcid;

	@Column(name = "Reim_DissAllowedAvail_Amt")
	private Long reimDissAllowedAvailAmt;

	@Column(name = "Exe_CapitivePower_Amt")
	private Long exCapitivePowerAmt;

	@Column(name = "Exe_PowerDrawn_Amt")
	private Long exePowerDrawnAmt;

	@Column(name = "Exe_MandiFreeAmt_Amt")
	private Long exeMandiFreeAmt;

	@Column(name = "IndustrialUnit_Amt")
	private Long industrialUnitAmt;

	@Column(name = "Created_By")
	private String createdBy;

	@Column(name = "Modified_By")
	private String modifiedtedBy;

	@Column(name = "OtherInc_Status")
	private String status;

	@Column(name = "DIS_OtherInc_Create_By")
	private String createBy;

	@Column(name = "DIS_Other_Create_Date")
	@Temporal(TemporalType.TIMESTAMP)
	private Date createDate;

	@Column(name = "DIS_OtherInc_Modified_By")
	private String modifiedBy;

	@Column(name = "DIS_OtherInc_Modify_Date")
	@Temporal(TemporalType.TIMESTAMP)
	private Date modifiyDate;
	
	
	private transient String electricityDutyExeFinYr;


	private transient String electricityDateFrom;


	private transient String electricityDateTo;

	private transient String electricityAmtClaim;


	private transient String electricityEligAmt;
	
	private transient String PowerType;
	

	private transient String electricityDutyExeFinYr2;


	private transient String electricityDateFrom2;


	private transient String electricityDateTo2;

	private transient String electricityAmtClaim2;


	private transient String electricityEligAmt2;
	
	private transient String PowerType2;

	private transient String mandiFeeExeFinYr1;
	
    
	private transient String mandiFeeDateFrom1;
    
	
	private transient String mandiFeeDateTo1;
	
	
	private transient String claimMandiFeeExe1;

	
	private transient String AvailMandiFeeExe1;
	
	
	private transient String disallowedFinYr;
	
	
	private transient String disallowedDateFr;
	
	
	private transient String disallowedDateTo;
	
	
	private transient String disallowedClaimAmt;


	private transient String disallowedEligAmt;
	
	

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

	public String getPowerType() {
		return PowerType;
	}

	public void setPowerType(String powerType) {
		PowerType = powerType;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getOthApcid() {
		return othApcid;
	}

	public void setOthApcid(String othApcid) {
		this.othApcid = othApcid;
	}

	public Long getReimDissAllowedAvailAmt() {
		return reimDissAllowedAvailAmt;
	}

	public void setReimDissAllowedAvailAmt(Long reimDissAllowedAvailAmt) {
		this.reimDissAllowedAvailAmt = reimDissAllowedAvailAmt;
	}

	public Long getExCapitivePowerAmt() {
		return exCapitivePowerAmt;
	}

	public void setExCapitivePowerAmt(Long exCapitivePowerAmt) {
		this.exCapitivePowerAmt = exCapitivePowerAmt;
	}

	public Long getExePowerDrawnAmt() {
		return exePowerDrawnAmt;
	}

	public void setExePowerDrawnAmt(Long exePowerDrawnAmt) {
		this.exePowerDrawnAmt = exePowerDrawnAmt;
	}

	public Long getExeMandiFreeAmt() {
		return exeMandiFreeAmt;
	}

	public void setExeMandiFreeAmt(Long exeMandiFreeAmt) {
		this.exeMandiFreeAmt = exeMandiFreeAmt;
	}

	public Long getIndustrialUnitAmt() {
		return industrialUnitAmt;
	}

	public void setIndustrialUnitAmt(Long industrialUnitAmt) {
		this.industrialUnitAmt = industrialUnitAmt;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public String getModifiedtedBy() {
		return modifiedtedBy;
	}

	public void setModifiedtedBy(String modifiedtedBy) {
		this.modifiedtedBy = modifiedtedBy;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getCreateBy() {
		return createBy;
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getModifiedBy() {
		return modifiedBy;
	}

	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	public Date getModifiyDate() {
		return modifiyDate;
	}

	public void setModifiyDate(Date modifiyDate) {
		this.modifiyDate = modifiyDate;
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

	public String getElectricityDutyExeFinYr2() {
		return electricityDutyExeFinYr2;
	}

	public void setElectricityDutyExeFinYr2(String electricityDutyExeFinYr2) {
		this.electricityDutyExeFinYr2 = electricityDutyExeFinYr2;
	}

	public String getElectricityDateFrom2() {
		return electricityDateFrom2;
	}

	public void setElectricityDateFrom2(String electricityDateFrom2) {
		this.electricityDateFrom2 = electricityDateFrom2;
	}

	public String getElectricityDateTo2() {
		return electricityDateTo2;
	}

	public void setElectricityDateTo2(String electricityDateTo2) {
		this.electricityDateTo2 = electricityDateTo2;
	}

	public String getElectricityAmtClaim2() {
		return electricityAmtClaim2;
	}

	public void setElectricityAmtClaim2(String electricityAmtClaim2) {
		this.electricityAmtClaim2 = electricityAmtClaim2;
	}

	public String getElectricityEligAmt2() {
		return electricityEligAmt2;
	}

	public void setElectricityEligAmt2(String electricityEligAmt2) {
		this.electricityEligAmt2 = electricityEligAmt2;
	}

	public String getPowerType2() {
		return PowerType2;
	}

	public void setPowerType2(String powerType2) {
		PowerType2 = powerType2;
	}

	public String getMandiFeeExeFinYr1() {
		return mandiFeeExeFinYr1;
	}

	public void setMandiFeeExeFinYr1(String mandiFeeExeFinYr1) {
		this.mandiFeeExeFinYr1 = mandiFeeExeFinYr1;
	}

	public String getMandiFeeDateFrom1() {
		return mandiFeeDateFrom1;
	}

	public void setMandiFeeDateFrom1(String mandiFeeDateFrom1) {
		this.mandiFeeDateFrom1 = mandiFeeDateFrom1;
	}

	public String getMandiFeeDateTo1() {
		return mandiFeeDateTo1;
	}

	public void setMandiFeeDateTo1(String mandiFeeDateTo1) {
		this.mandiFeeDateTo1 = mandiFeeDateTo1;
	}

	public String getClaimMandiFeeExe1() {
		return claimMandiFeeExe1;
	}

	public void setClaimMandiFeeExe1(String claimMandiFeeExe1) {
		this.claimMandiFeeExe1 = claimMandiFeeExe1;
	}

	public String getAvailMandiFeeExe1() {
		return AvailMandiFeeExe1;
	}

	public void setAvailMandiFeeExe1(String availMandiFeeExe1) {
		AvailMandiFeeExe1 = availMandiFeeExe1;
	}
	
	

}
