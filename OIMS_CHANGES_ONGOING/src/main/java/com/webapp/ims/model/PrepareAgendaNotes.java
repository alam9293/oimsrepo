package com.webapp.ims.model;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "DPT_Agenda_Notes", schema = "loc")
public class PrepareAgendaNotes {

	@Id
	@Column(name = "DPT_ID")
	private String id;

	@Column(name = "DPT _APC_ID")
	private String appliId;

	@Column(name = "DPT _Company_Name")
	private String companyName;

	@Column(name = "DPT _Investment")
	private String investment;

	@Column(name = "DPT _Notes")
	private String notes;

	@Column(name = "DPT _User_Id")
	private String userId;

	@Column(name = "Status")
	private String status;

	@Column(name = "Created_By")
	private String createdBY;

	@Column(name = "Loc_Number")
	private String locNumber;

	@Temporal(value = TemporalType.DATE)
	@Column(name = "DPT_Submission_Date")
	private Date submissionDate;

	@Temporal(value = TemporalType.DATE)
	@Column(name = "DPT_Approval_Date")
	private Date approvalDate;

	@Temporal(value = TemporalType.DATE)
	@Column(name = "DPT_Approvalacs_Date")
	private Date acsapprovalDate;

	@Column(name = "Modify_Date")
	private Timestamp modifyDate;

	transient List<RoleMasterEntity> roleMasterList = new ArrayList<>();

	public Date getAcsapprovalDate() {
		return acsapprovalDate;
	}

	public void setAcsapprovalDate(Date acsapprovalDate) {
		this.acsapprovalDate = acsapprovalDate;
	}

	public Date getApprovalDate() {
		return approvalDate;
	}

	public void setApprovalDate(Date approvalDate) {
		this.approvalDate = approvalDate;
	}

	private transient String createDate;
	private transient String[] prepareAgendaNote;

	@Column(name = "DPT_Category_Type")
	private String categoryIndsType;

	@Column(name = " DPT_District")
	private String district;

	@Column(name = "DPT_Region ")
	private String region;

	@Column(name = "DPT_MDPComments")
	private String comments;

	@Column(name = "DPT_JMDComments")
	private String jmdcomments;

	@Column(name = "DPT_Pickup_Scrutiny_Details")
	private String pkupScrutinyDetail;

	@Column(name = "DPT_JMD_Scrutiny_Details")
	private String jmdScrutinyDetail;

	@Column(name = "DPT_MD_Scrutiny_Details")
	private String mdScrutinyDetail;

	@Column(name = "DPT_ACS_Scrutiny_Details")
	private String acsScrutinyDetail;

	@Column(name = "DPT_Pickup_Scrutiny_Filename")
	private String pkupFilename;

	@Column(name = "DPT_Pickup_Scrutiny_Filedata")
	private byte[] pkupFiledata;

	@Column(name = "Applicant_EmailID")
	private String appliacntEmailId;

	@Column(name = "DPT_ACSComments")
	private String acsmdcomments;

	transient List<ScrutinyDocument> scrutinyDocumentList = new ArrayList<>();
	transient String[] multiDept;

	public String getPkupFilename() {
		return pkupFilename;
	}

	public void setPkupFilename(String pkupFilename) {
		this.pkupFilename = pkupFilename;
	}

	public byte[] getPkupFiledata() {
		return pkupFiledata;
	}

	public void setPkupFiledata(byte[] pkupFiledata) {
		this.pkupFiledata = pkupFiledata;
	}

	public String getPkupScrutinyDetail() {
		return pkupScrutinyDetail;
	}

	public void setPkupScrutinyDetail(String pkupScrutinyDetail) {
		this.pkupScrutinyDetail = pkupScrutinyDetail;
	}

	public String getJmdScrutinyDetail() {
		return jmdScrutinyDetail;
	}

	public void setJmdScrutinyDetail(String jmdScrutinyDetail) {
		this.jmdScrutinyDetail = jmdScrutinyDetail;
	}

	public String getMdScrutinyDetail() {
		return mdScrutinyDetail;
	}

	public void setMdScrutinyDetail(String mdScrutinyDetail) {
		this.mdScrutinyDetail = mdScrutinyDetail;
	}

	public String getAcsScrutinyDetail() {
		return acsScrutinyDetail;
	}

	public void setAcsScrutinyDetail(String acsScrutinyDetail) {
		this.acsScrutinyDetail = acsScrutinyDetail;
	}

	public String getJmdcomments() {
		return jmdcomments;
	}

	public void setJmdcomments(String jmdcomments) {
		this.jmdcomments = jmdcomments;
	}

	public String getAcsmdcomments() {
		return acsmdcomments;
	}

	public void setAcsmdcomments(String acsmdcomments) {
		this.acsmdcomments = acsmdcomments;
	}

	@Column(name = "DPT_Subject")
	private String subjectName;

	private transient String panbase64File;

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

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
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

	public String[] getPrepareAgendaNote() {
		return prepareAgendaNote;
	}

	public void setPrepareAgendaNote(String[] prepareAgendaNote) {
		this.prepareAgendaNote = prepareAgendaNote;
	}

	public String getCreateDate() {
		return createDate;
	}

	public void setCreateDate(String createDate) {
		this.createDate = createDate;
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

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public String getSubjectName() {
		return subjectName;
	}

	public void setSubjectName(String subjectName) {
		this.subjectName = subjectName;
	}

	public Date getSubmissionDate() {
		return submissionDate;
	}

	public void setSubmissionDate(Date submissionDate) {
		this.submissionDate = submissionDate;
	}

	public String getPanbase64File() {
		return panbase64File;
	}

	public void setPanbase64File(String panbase64File) {
		this.panbase64File = panbase64File;
	}

	public String getLocNumber() {
		return locNumber;
	}

	public void setLocNumber(String locNumber) {
		this.locNumber = locNumber;
	}

	public List<ScrutinyDocument> getScrutinyDocumentList() {
		return scrutinyDocumentList;
	}

	public void setScrutinyDocumentList(List<ScrutinyDocument> scrutinyDocumentList) {
		this.scrutinyDocumentList = scrutinyDocumentList;
	}

	public List<RoleMasterEntity> getRoleMasterList() {
		return roleMasterList;
	}

	public void setRoleMasterList(List<RoleMasterEntity> roleMasterList) {
		this.roleMasterList = roleMasterList;
	}

	public String[] getMultiDept() {
		return multiDept;
	}

	public void setMultiDept(String[] multiDept) {
		this.multiDept = multiDept;
	}

	public Timestamp getModifyDate() {
		return modifyDate;
	}

	public void setModifyDate(Timestamp modifyDate) {
		this.modifyDate = modifyDate;
	}

	public String getAppliacntEmailId() {
		return appliacntEmailId;
	}

	public void setAppliacntEmailId(String appliacntEmailId) {
		this.appliacntEmailId = appliacntEmailId;
	}

}
