package com.webapp.ims.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;

/* Author Shailendra Kumar Rathour */

@Entity
@Table(name = "Minutes_Of_Meeting", schema = "loc")

public class MinutesOfMeeting implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "MM_ID")
	private String id;

	@Column(name = "Gos_AppID")
	private String gosAppID;

	@Column(name = "Committee_Departments")
	private String committeeDepartments;

	@Column(name = "Minut_Of_Meet_Or_Gos")
	private String minutesOfMeetingOrGos;

	@Column(name = "Committee_Name")
	private String committeeName;

	@Column(name = "Date_Of_Meeting")
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	private Date dateOfMeeting;

	@Column(name = "Gos_No")
	private String gosNo;

	@Column(name = "Gos_Name")
	private String gosName;

	@Column(name = "Gos_Date")
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	private Date gosDate;

	@Column(name = "User_ID")
	private String userId;

	@Column(name = "Mom_Created_By")
	private String createdBy;

	@Column(name = "Mom_Modified_By")
	private String modifiedBy;

	@Column(name = "Mom_Created_Date")
	private Date createDate;

	@Column(name = "Mom_Modified_Date")
	private Date modifyDate;

	@Column(name = "Status")
	private String active;

	private transient String uploadMomFile;
	private transient String uploadGosFile;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getGosAppID() {
		return gosAppID;
	}

	public void setGosAppID(String gosAppID) {
		this.gosAppID = gosAppID;
	}

	public String getCommitteeDepartments() {
		return committeeDepartments;
	}

	public void setCommitteeDepartments(String committeeDepartments) {
		this.committeeDepartments = committeeDepartments;
	}

	public String getCommitteeName() {
		return committeeName;
	}

	public void setCommitteeName(String committeeName) {
		this.committeeName = committeeName;
	}

	public Date getDateOfMeeting() {
		return dateOfMeeting;
	}

	public void setDateOfMeeting(Date dateOfMeeting) {
		this.dateOfMeeting = dateOfMeeting;
	}

	public String getGosNo() {
		return gosNo;
	}

	public void setGosNo(String gosNo) {
		this.gosNo = gosNo;
	}

	public String getGosName() {
		return gosName;
	}

	public void setGosName(String gosName) {
		this.gosName = gosName;
	}

	public Date getGosDate() {
		return gosDate;
	}

	public void setGosDate(Date gosDate) {
		this.gosDate = gosDate;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getMinutesOfMeetingOrGos() {
		return minutesOfMeetingOrGos;
	}

	public void setMinutesOfMeetingOrGos(String minutesOfMeetingOrGos) {
		this.minutesOfMeetingOrGos = minutesOfMeetingOrGos;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public String getModifiedBy() {
		return modifiedBy;
	}

	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Date getModifyDate() {
		return modifyDate;
	}

	public void setModifyDate(Date modifyDate) {
		this.modifyDate = modifyDate;
	}

	public String getActive() {
		return active;
	}

	public void setActive(String active) {
		this.active = active;
	}

	public String getUploadGosFile() {
		return uploadGosFile;
	}

	public void setUploadGosFile(String uploadGosFile) {
		this.uploadGosFile = uploadGosFile;
	}

	public String getUploadMomFile() {
		return uploadMomFile;
	}

	public void setUploadMomFile(String uploadMomFile) {
		this.uploadMomFile = uploadMomFile;
	}

}
