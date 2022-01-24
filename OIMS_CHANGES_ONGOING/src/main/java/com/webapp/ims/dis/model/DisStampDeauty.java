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
@Table(name = "Dis_Stamp_Duty", schema = "loc")
public class DisStampDeauty implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "Stamp_ID")
	private String stampId;

	@Column(name = "Stamp_APC_ID")
	private String stampApcId;
	
	//private transient List<StampDutyApplicationForm> stampDutyAppForm;

	@Column(name = "Ex_Claim_Amount")
	private Long exemptionClaimAmount;

	@Column(name = "Ex_Avail_Amount")
	private Long exemptionAvailAmount;

	@Column(name = "Reim_Claim_Amount")
	private Long reimbursementClaimAmount;

	@Column(name = "Reim_Avail_Amount")
	private Long reimbursementAvailAmount;

	@Column(name = "Add_Claim_Amount")
	private Long additionalStampClaimAmount;

	@Column(name = "Add_Avail_Amount")
	private Long additionalStampAvailAmount;

	@Column(name = "Ttl_Claim_Amt")
	private Long totalClaimAmount;

	@Column(name = "Ttl_Avail_Amt")
	private Long totalAvailAmount;

	@Column(name = "Created_By")
	private String createdBy;

	@Column(name = "Modified_By")
	private String modifiedtedBy;

	@Column(name = "Stamp_Status")
	private String status;

	@Column(name = "DIS_Stamp_Create_By")
	private String createBy;

	@Column(name = "DIS_Stamp_Create_Date")
	@Temporal(TemporalType.TIMESTAMP)
	private Date createDate;

	@Column(name = "DIS_Stamp_Modified_By")
	private String modifiedBy;

	@Column(name = "DIS_Stamp_Modify_Date")
	@Temporal(TemporalType.TIMESTAMP)
	private Date modifiyDate;
	
	//Gyan
	private transient String stampDutyFinYr;
	private transient String durationFrom;
	private transient String durationTo;
	private transient String claimReimAmt;
	
	/*
	 * private transient String[] stampDutyFinYr; private transient String[]
	 * durationFrom; private transient String[] durationTo; private transient
	 * String[] claimReimAmt;
	 */
	

	public String getStampApcId() {
		return stampApcId;
	}

	public void setStampApcId(String stampApcId) {
		this.stampApcId = stampApcId;
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

	public String getStampId() {
		return stampId;
	}

	public void setStampId(String stampId) {
		this.stampId = stampId;
	}

	public Long getExemptionClaimAmount() {
		return exemptionClaimAmount;
	}

	public void setExemptionClaimAmount(Long exemptionClaimAmount) {
		this.exemptionClaimAmount = exemptionClaimAmount;
	}

	public Long getExemptionAvailAmount() {
		return exemptionAvailAmount;
	}

	public void setExemptionAvailAmount(Long exemptionAvailAmount) {
		this.exemptionAvailAmount = exemptionAvailAmount;
	}

	public Long getReimbursementClaimAmount() {
		return reimbursementClaimAmount;
	}

	public void setReimbursementClaimAmount(Long reimbursementClaimAmount) {
		this.reimbursementClaimAmount = reimbursementClaimAmount;
	}

	public Long getReimbursementAvailAmount() {
		return reimbursementAvailAmount;
	}

	public void setReimbursementAvailAmount(Long reimbursementAvailAmount) {
		this.reimbursementAvailAmount = reimbursementAvailAmount;
	}

	public Long getAdditionalStampClaimAmount() {
		return additionalStampClaimAmount;
	}

	public void setAdditionalStampClaimAmount(Long additionalStampClaimAmount) {
		this.additionalStampClaimAmount = additionalStampClaimAmount;
	}

	public Long getAdditionalStampAvailAmount() {
		return additionalStampAvailAmount;
	}

	public void setAdditionalStampAvailAmount(Long additionalStampAvailAmount) {
		this.additionalStampAvailAmount = additionalStampAvailAmount;
	}

	public Long getTotalClaimAmount() {
		return totalClaimAmount;
	}

	public void setTotalClaimAmount(Long totalClaimAmount) {
		this.totalClaimAmount = totalClaimAmount;
	}

	public Long getTotalAvailAmount() {
		return totalAvailAmount;
	}

	public void setTotalAvailAmount(Long totalAvailAmount) {
		this.totalAvailAmount = totalAvailAmount;
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

	
	/*
	 * public List<StampDutyApplicationForm> getStampDutyAppForm() { return
	 * stampDutyAppForm; }
	 * 
	 * public void setStampDutyAppForm(List<StampDutyApplicationForm>
	 * stampDutyAppForm) { this.stampDutyAppForm = stampDutyAppForm; }
	 */
	
	
}
