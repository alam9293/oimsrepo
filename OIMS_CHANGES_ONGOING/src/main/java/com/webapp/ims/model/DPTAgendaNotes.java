package com.webapp.ims.model;

//gopal
import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "DPT_Agenda_Notes", schema = "loc")
public class DPTAgendaNotes implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	@Column(name = "DPT_ID")
	private String dptId;

	@Column(name = "DPT _APC_ID")
	private String dptApcId;

	@Column(name = "DPT _Company_Name")
	private String dptCompanyName;

	@Column(name = "DPT _Investment")
	private String dptInvestment;

	@Column(name = "DPT _Notes")
	private String dptNotes;

	@Column(name = "DPT _User_Id")
	private String dptUserIds;

	@Column(name = "Created_Date")
	@Temporal(TemporalType.TIMESTAMP)
	private Date createdDate;

	@Column(name = "Modify_Date")
	@Temporal(TemporalType.TIMESTAMP)
	private Date modifydate;

	@Column(name = "Status")
	private String status;

	@Column(name = "DPT_Category_Type")
	private String dPTCategoryType;

	@Column(name = "DPT_District")
	private String dPtDistrict;

	@Column(name = "DPT_Region")
	private String dPTRegion;

	@Column(name = "DPT_MDPComments")
	private String dPtComments;

	@Column(name = "DPT_Subject")
	private String dPTSubject;

	@Column(name = "DPT_Reason")
	private String dPTReason;

	@Column(name = "Created_By")
	private String createdBy;
	@Column(name = "Modify_By")
	private String modifyBy;

	public String getDptId() {
		return dptId;
	}

	public void setDptId(String dptId) {
		this.dptId = dptId;
	}

	public String getDptApcId() {
		return dptApcId;
	}

	public void setDptApcId(String dptApcId) {
		this.dptApcId = dptApcId;
	}

	public String getDptCompanyName() {
		return dptCompanyName;
	}

	public void setDptCompanyName(String dptCompanyName) {
		this.dptCompanyName = dptCompanyName;
	}

	public String getDptInvestment() {
		return dptInvestment;
	}

	public void setDptInvestment(String dptInvestment) {
		this.dptInvestment = dptInvestment;
	}

	public String getDptNotes() {
		return dptNotes;
	}

	public void setDptNotes(String dptNotes) {
		this.dptNotes = dptNotes;
	}

	public String getDptUserIds() {
		return dptUserIds;
	}

	public void setDptUserIds(String dptUserIds) {
		this.dptUserIds = dptUserIds;
	}

	public Date getModifydate() {
		return modifydate;
	}

	public void setModifydate(Date modifydate) {
		this.modifydate = modifydate;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getdPTCategoryType() {
		return dPTCategoryType;
	}

	public void setdPTCategoryType(String dPTCategoryType) {
		this.dPTCategoryType = dPTCategoryType;
	}

	public String getdPtDistrict() {
		return dPtDistrict;
	}

	public void setdPtDistrict(String dPtDistrict) {
		this.dPtDistrict = dPtDistrict;
	}

	public String getdPTRegion() {
		return dPTRegion;
	}

	public void setdPTRegion(String dPTRegion) {
		this.dPTRegion = dPTRegion;
	}

	public String getdPtComments() {
		return dPtComments;
	}

	public void setdPtComments(String dPtComments) {
		this.dPtComments = dPtComments;
	}

	public String getdPTSubject() {
		return dPTSubject;
	}

	public void setdPTSubject(String dPTSubject) {
		this.dPTSubject = dPTSubject;
	}

	public String getdPTReason() {
		return dPTReason;
	}

	public void setdPTReason(String dPTReason) {
		this.dPTReason = dPTReason;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public String getModifyBy() {
		return modifyBy;
	}

	public void setModifyBy(String modifyBy) {
		this.modifyBy = modifyBy;
	}

}
