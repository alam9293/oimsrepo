package com.webapp.ims.dis.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "Dis_Circulate_To_Department", schema = "loc")
public class CirculateToDepartmentDis implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "Id")
	private int id;

	@Column(name = "Dis_Circulate_Id")
	private String discirculateId;

	@Column(name = "Applicant_Id")
	private String AppId;
	@Column(name = "Dis_Department_Email")
	private String deptEmail;
	@Column(name = "Dis_Department_Name")
	private String deptName;
	@Column(name = "Dis_Note_Report_Status")
	private String noteReportStatus;

	@Column(name = "Dis_File_Id")
	private int fileId;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getDiscirculateId() {
		return discirculateId;
	}

	public void setDiscirculateId(String discirculateId) {
		this.discirculateId = discirculateId;
	}

	public String getAppId() {
		return AppId;
	}

	public void setAppId(String appId) {
		AppId = appId;
	}

	public String getDeptEmail() {
		return deptEmail;
	}

	public void setDeptEmail(String deptEmail) {
		this.deptEmail = deptEmail;
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
