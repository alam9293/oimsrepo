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
@Table(name = "INV_Means_Of_Finance", schema = "loc")
public class InvMeansOfFinance implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	@Column(name = "MOF_ID")
	private String mofId;
	@Column(name = "MOF_Parameter")
	private String mofParameter;
	
	@Column(name = "Phase_Number")
	private Integer pwPhaseNoMOM;
	
	@Column(name = "MOF_Amount")
	private Long mofAmount;
	@Column(name = "MOF_INV_ID")
	private String mofInvId;
	@Column(name = "MOF_Created_By")
	private String mofCreatedBy;
	@Column(name = "MOF_Modified_By")
	private String mofModifiedBy;
	@Column(name = "MOF_Created_Date")
	@Temporal(TemporalType.DATE)
	private Date mofModifyDate;
	@Column(name = "MOF_Status")
	private String mofStatus;
	
	/*
	 * @Column(name = "Phase_Number") private int phaseNo;
	 */
	
	@Column(name = "PW_APC_ID")
	private String pwApcId;
	
	
	/*
	 * public int getPhaseNo() { return phaseNo; }
	 * 
	 * public void setPhaseNo(int phaseNo) { this.phaseNo = phaseNo; }
	 */

	public String getPwApcId() {
		return pwApcId;
	}

	public void setPwApcId(String pwApcId) {
		this.pwApcId = pwApcId;
	}

	public InvMeansOfFinance() {
		super();
	}

	public String getMofId() {
		return mofId;
	}

	public void setMofId(String mofId) {
		this.mofId = mofId;
	}

	public String getMofParameter() {
		return mofParameter;
	}

	public void setMofParameter(String mofParameter) {
		this.mofParameter = mofParameter;
	}

	public Long getMofAmount() {
		return mofAmount;
	}

	public void setMofAmount(Long mofAmount) {
		this.mofAmount = mofAmount;
	}

	public String getMofInvId() {
		return mofInvId;
	}

	public void setMofInvId(String mofInvId) {
		this.mofInvId = mofInvId;
	}

	public String getMofCreatedBy() {
		return mofCreatedBy;
	}

	public void setMofCreatedBy(String mofCreatedBy) {
		this.mofCreatedBy = mofCreatedBy;
	}

	public String getMofModifiedBy() {
		return mofModifiedBy;
	}

	public void setMofModifiedBy(String mofModifiedBy) {
		this.mofModifiedBy = mofModifiedBy;
	}

	public Date getMofModifyDate() {
		return mofModifyDate;
	}

	public void setMofModifyDate(Date mofModifyDate) {
		this.mofModifyDate = mofModifyDate;
	}

	public String getMofStatus() {
		return mofStatus;
	}

	public void setMofStatus(String mofStatus) {
		this.mofStatus = mofStatus;
	}

	public Integer getPwPhaseNoMOM() {
		return pwPhaseNoMOM;
	}

	public void setPwPhaseNoMOM(Integer pwPhaseNoMOM) {
		this.pwPhaseNoMOM = pwPhaseNoMOM;
	}

	
	


}
