package com.webapp.ims.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "PW_Investment_Details", schema = "loc")
public class PhaseWiseInvestmentDetails implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	@Column(name = "PW_ID")
	private String pwId;

	@Column(name = "PW_Prop_Prod_Date")
	@Temporal(TemporalType.DATE)
	private Date pwPropProductDate;
	@Column(name = "PW_Product_Name")
	private String pwProductName;
	@Column(name = "PW_Capacity")
	private Long pwCapacity;
	@Column(name = "PW_Unit")
	private String pwUnit;
	@Column(name = "PW_Fixed_Capital_Investment")
	private Long pwFci;
	@Column(name = "PW_Cutoff_Com_Prod_Amt")
	private Long pwCutoffProdAmt;
	@Column(name = "PW_Created_By")
	private String pwCreatedBy;
	@Column(name = "PW_Modified_By")
	private String pwModifiedBy;
	@Column(name = "PW_Status")
	private String pwStatus;

	@Column(name = "PW_INV_ID")
	private String pwInvId;
	@Column(name = "PW_APC_ID")
	private String pwApcId;
	@Column(name = "PW_Modify_Date")
	@Temporal(TemporalType.TIMESTAMP)
	private Date pwModifiedDate;
	@Column(name = "PW_Apply")
	private String pwApply;
	@Column(name = "PW_Phase_Number")
	private Integer pwPhaseNo;

	@Column(name = "Act_Date_Comm_Prod")
	private String actDtCommProd;
	
	@Transient
	private String dateInString;

	public Integer getPwPhaseNo() {
		return pwPhaseNo;
	}

	public void setPwPhaseNo(Integer pwPhaseNo) {
		this.pwPhaseNo = pwPhaseNo;
	}

	public String getPwApply() {
		return pwApply;
	}

	public void setPwApply(String pwApply) {
		this.pwApply = pwApply;
	}

	public String getPwInvId() {
		return pwInvId;
	}

	public Date getPwModifiedDate() {
		return pwModifiedDate;
	}

	public void setPwModifiedDate(Date pwModifiedDate) {
		this.pwModifiedDate = pwModifiedDate;
	}

	public void setPwInvId(String pwInvId) {
		this.pwInvId = pwInvId;
	}

	public String getPwApcId() {
		return pwApcId;
	}

	public void setPwApcId(String pwApcId) {
		this.pwApcId = pwApcId;
	}

	public PhaseWiseInvestmentDetails() {
		super();
	}

	public String getPwId() {
		return pwId;
	}

	public void setPwId(String pwId) {
		this.pwId = pwId;
	}

	public String getPwProductName() {
		return pwProductName;
	}

	public void setPwProductName(String pwProductName) {
		this.pwProductName = pwProductName;
	}

	public Long getPwCapacity() {
		return pwCapacity;
	}

	public void setPwCapacity(Long pwCapacity) {
		this.pwCapacity = pwCapacity;
	}

	public String getPwUnit() {
		return pwUnit;
	}

	public void setPwUnit(String pwUnit) {
		this.pwUnit = pwUnit;
	}

	public Long getPwFci() {
		return pwFci;
	}

	public void setPwFci(Long pwFci) {
		this.pwFci = pwFci;
	}

	public Date getPwPropProductDate() {
		return pwPropProductDate;
	}

	public String getPwCreatedBy() {
		return pwCreatedBy;
	}

	public void setPwCreatedBy(String pwCreatedBy) {
		this.pwCreatedBy = pwCreatedBy;
	}

	public String getPwModifiedBy() {
		return pwModifiedBy;
	}

	public void setPwModifiedBy(String pwModifiedBy) {
		this.pwModifiedBy = pwModifiedBy;
	}

	public String getPwStatus() {
		return pwStatus;
	}

	public void setPwStatus(String pwStatus) {
		this.pwStatus = pwStatus;
	}

	public void setPwPropProductDate(Date pwPropProductDate) {
		this.pwPropProductDate = pwPropProductDate;
	}

	public Long getPwCutoffProdAmt() {
		return pwCutoffProdAmt;
	}

	public void setPwCutoffProdAmt(Long pwCutoffProdAmt) {
		this.pwCutoffProdAmt = pwCutoffProdAmt;
	}

	public String getActDtCommProd() {
		return actDtCommProd;
	}

	public void setActDtCommProd(String actDtCommProd) {
		this.actDtCommProd = actDtCommProd;
	}

	@Column(name = "INV_land_Cost")
	@NotNull
	private Long invLandCost;

	public Long getInvLandCost() {
		return invLandCost;
	}

	public void setInvLandCost(Long invLandCost) {
		this.invLandCost = invLandCost;
	}

	public Long getInvBuildingCost() {
		return invBuildingCost;
	}

	public void setInvBuildingCost(Long invBuildingCost) {
		this.invBuildingCost = invBuildingCost;
	}

	public Long getInvPlantAndMachCost() {
		return invPlantAndMachCost;
	}

	public void setInvPlantAndMachCost(Long invPlantAndMachCost) {
		this.invPlantAndMachCost = invPlantAndMachCost;
	}

	public Long getInvFci() {
		return invFci;
	}

	public void setInvFci(Long invFci) {
		this.invFci = invFci;
	}

	public Long getInvOtherCost() {
		return invOtherCost;
	}

	public void setInvOtherCost(Long invOtherCost) {
		this.invOtherCost = invOtherCost;
	}

	@Column(name = "INV_Bldg_Cost")
	private Long invBuildingCost;

	@Column(name = "INV_Plantmac_Cost")
	private Long invPlantAndMachCost;

	@Column(name = "INV_Proposed_Fix_Cap_Inv")
	private Long invFci;

	@Column(name = "INV_Misc_Fix_Asset")
	private Long invOtherCost;

	public String getDateInString() {
		return dateInString;
	}

	public void setDateInString(String dateInString) {
		this.dateInString = dateInString;
	}
	
	
	

}
