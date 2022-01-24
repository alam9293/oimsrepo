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
@Table(name = "Dept_PW_Investment_Details", schema = "loc")
public class Dept_PhaseWiseInvestmentDetails implements Serializable {

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

	@Column(name = "phwActualdateEval")
	// @Temporal(TemporalType.DATE)
	private String phwActualdateEval;

	public String getPhwActualdateEval() {
		return phwActualdateEval;
	}

	public void setPhwActualdateEval(String phwActualdateEval) {
		this.phwActualdateEval = phwActualdateEval;
	}

	public Dept_PhaseWiseInvestmentDetails() {
		super();
	}

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
		this.pwCutoffProdAmt = 100L;
	}

}
