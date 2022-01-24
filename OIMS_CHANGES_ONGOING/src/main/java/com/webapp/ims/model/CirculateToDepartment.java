/**
 * Author:: Sachin
* Created on:: 
 */
package com.webapp.ims.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author dell
 *
 */
@Entity
@Table(name = "Circulate_To_Department", schema = "loc")
public class CirculateToDepartment implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "Id")
	private int id;

	@Column(name = "Circulate_Id")
	private String circulateId;

	@Column(name = "Applicant_Id")
	private String AppId;
	@Column(name = "Department_Email")
	private String deptEmail;
	@Column(name = "Department_Name")
	private String deptName;
	@Column(name = "Note_Report_Status")
	private String noteReportStatus;

	@Column(name = "Department_Id")
	private String deptId;

	@Column(name = "File_Id")
	private int fileId;

	public String getNoteReportStatus() {
		return noteReportStatus;
	}

	public void setNoteReportStatus(String noteReportStatus) {
		this.noteReportStatus = noteReportStatus;
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

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCirculateId() {
		return circulateId;
	}

	public void setCirculateId(String circulateId) {
		this.circulateId = circulateId;
	}

	public int getFileId() {
		return fileId;
	}

	public void setFileId(int fileId) {
		this.fileId = fileId;
	}

	public String getDeptId() {
		return deptId;
	}

	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}
}
