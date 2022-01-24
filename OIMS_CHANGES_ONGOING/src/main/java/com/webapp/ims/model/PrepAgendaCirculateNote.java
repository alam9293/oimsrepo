package com.webapp.ims.model;

import java.util.Date;

public class PrepAgendaCirculateNote {

	private String appliId;

	private String status;

	private Date submissionDate;
	private Date approvalDate;
	private Date acsapprovalDate;

	private String deptName;
	private String noteReportStatus;
	private int fileId;

	public String getAppliId() {
		return appliId;
	}

	public void setAppliId(String appliId) {
		this.appliId = appliId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Date getSubmissionDate() {
		return submissionDate;
	}

	public Date getAcsapprovalDate() {
		return acsapprovalDate;
	}

	public void setAcsapprovalDate(Date acsapprovalDate) {
		this.acsapprovalDate = acsapprovalDate;
	}

	public void setSubmissionDate(Date submissionDate) {
		this.submissionDate = submissionDate;
	}

	public Date getApprovalDate() {
		return approvalDate;
	}

	public void setApprovalDate(Date approvalDate) {
		this.approvalDate = approvalDate;
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

	public int getFileId() {
		return fileId;
	}

	public void setFileId(int fileId) {
		this.fileId = fileId;
	}

}
