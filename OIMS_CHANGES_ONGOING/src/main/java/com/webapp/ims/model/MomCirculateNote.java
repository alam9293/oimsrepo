package com.webapp.ims.model;

import java.util.Date;

public class MomCirculateNote {

	private String id;
	private String gosAppID;
	private String minutesOfMeetingOrGos;
	private String committeeDepartments;
	private Date dateOfMeeting;
	private String committeeName;
	private String gosName;
	private String deptName;
	private String gosNo;
	public String getGosNo() {
		return gosNo;
	}
	public void setGosNo(String gosNo) {
		this.gosNo = gosNo;
	}
	private String noteReportStatus;
	
	private transient String uploadMomFile;
	private transient String uploadGosFile;
	
	
	
	public String getUploadMomFile() {
		return uploadMomFile;
	}
	public void setUploadMomFile(String uploadMomFile) {
		this.uploadMomFile = uploadMomFile;
	}
	public String getUploadGosFile() {
		return uploadGosFile;
	}
	public void setUploadGosFile(String uploadGosFile) {
		this.uploadGosFile = uploadGosFile;
	}
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
	public String getMinutesOfMeetingOrGos() {
		return minutesOfMeetingOrGos;
	}
	public void setMinutesOfMeetingOrGos(String minutesOfMeetingOrGos) {
		this.minutesOfMeetingOrGos = minutesOfMeetingOrGos;
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
	
	public String getGosName() {
		return gosName;
	}
	public void setGosName(String gosName) {
		this.gosName = gosName;
	}
	public String getDeptName() {
		return deptName;
	}
	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}
	public String getNoteReportStatus() {
		return noteReportStatus;
	}
	public void setNoteReportStatus(String noteReportStatus) {
		this.noteReportStatus = noteReportStatus;
	}


}
