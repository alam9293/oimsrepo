package com.webapp.ims.dis.model;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "DIS_DPT_Agenda_Notes", schema = "loc")
public class DISPrepareAgendaNotes {

	@Id
	@Column(name = "ID")
	private String id;

	@Column(name = "APC_ID")
	private String appliId;

	@Column(name = "UserId")
	private String userId;

	@Column(name = "Company_Name")
	private String companyName;

	@Column(name = "Investment")
	private String investment;

	@Column(name = "Category_Type")
	private String categoryIndsType;

	@Column(name = "District")
	private String district;

	@Column(name = "Region ")
	private String region;

	@Column(name = "Status")
	private String status;

	@Column(name = "CreatedBy")
	private String createdBY;

	@Column(name = "CreateDate")
	private Timestamp createDate;

	@Column(name = "App_Reject_Reason")
	private String rejectReason;

	@Column(name = "Dis_Note")
	private String note;

	@Column(name = "Nodal_Agency")
	private String nodalAgency;

	@Column(name = "JMD_Comments")
	private String jmdComment;

	@Column(name = "ACS_Comments")
	private String acsComments;

	@Column(name = "Submission_Date")
	private Timestamp submissionDate;

	@Column(name = "MD_Comments")
	private String mdComments;

	@Column(name = "Applicant_EmailID")
	private String appliacntEmailId;

	@Column(name = "Dis_Approval_Date")
	private Timestamp approvalDate;

	@Column(name = "Dis_Approvalacs_Date")
	private Timestamp acsapprovalDate;

	private transient String[] prepareAgendaNote;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getAppliId() {
		return appliId;
	}

	public void setAppliId(String appliId) {
		this.appliId = appliId;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getInvestment() {
		return investment;
	}

	public void setInvestment(String investment) {
		this.investment = investment;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getCategoryIndsType() {
		return categoryIndsType;
	}

	public void setCategoryIndsType(String categoryIndsType) {
		this.categoryIndsType = categoryIndsType;
	}

	public String getDistrict() {
		return district;
	}

	public void setDistrict(String district) {
		this.district = district;
	}

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getCreatedBY() {
		return createdBY;
	}

	public void setCreatedBY(String createdBY) {
		this.createdBY = createdBY;
	}

	public Timestamp getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Timestamp createDate) {
		this.createDate = createDate;
	}

	public String getRejectReason() {
		return rejectReason;
	}

	public void setRejectReason(String rejectReason) {
		this.rejectReason = rejectReason;
	}

	public String[] getPrepareAgendaNote() {
		return prepareAgendaNote;
	}

	public void setPrepareAgendaNote(String[] prepareAgendaNote) {
		this.prepareAgendaNote = prepareAgendaNote;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String getNodalAgency() {
		return nodalAgency;
	}

	public void setNodalAgency(String nodalAgency) {
		this.nodalAgency = nodalAgency;
	}

	public Timestamp getSubmissionDate() {
		return submissionDate;
	}

	public void setSubmissionDate(Timestamp submissionDate) {
		this.submissionDate = submissionDate;
	}

	public String getJmdComment() {
		return jmdComment;
	}

	public void setJmdComment(String jmdComment) {
		this.jmdComment = jmdComment;
	}

	public String getAcsComments() {
		return acsComments;
	}

	public void setAcsComments(String acsComments) {
		this.acsComments = acsComments;
	}

	public String getMdComments() {
		return mdComments;
	}

	public void setMdComments(String mdComments) {
		this.mdComments = mdComments;
	}

	public String getAppliacntEmailId() {
		return appliacntEmailId;
	}

	public void setAppliacntEmailId(String appliacntEmailId) {
		this.appliacntEmailId = appliacntEmailId;
	}

	public Timestamp getApprovalDate() {
		return approvalDate;
	}

	public Timestamp getAcsapprovalDate() {
		return acsapprovalDate;
	}

	public void setAcsapprovalDate(Timestamp acsapprovalDate) {
		this.acsapprovalDate = acsapprovalDate;
	}

	public void setApprovalDate(Timestamp approvalDate) {
		this.approvalDate = approvalDate;
	}

}
