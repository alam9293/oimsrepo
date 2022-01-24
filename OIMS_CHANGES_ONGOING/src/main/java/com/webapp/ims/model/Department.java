package com.webapp.ims.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.data.annotation.LastModifiedDate;

@Entity
@Table(name = "Department_Master", schema = "loc")
public class Department implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "Dept_ID")
	private long deptId;
	@Column(name = "Dept_Name")
	private String newDeptName;
	@Column(name = "Dept_Email")
	private String deptEmail;
	@Column(name = "Dept_Created_By")
	private String deptCreatedBy;
	@Column(name = "Dept_Modified_By")
	private String deptModifiedBy;
	@Column(name = "Dept_Status")
	private String deptStatus;
	@Column(name = "Dept_Modified_Date")
	@Temporal(TemporalType.TIMESTAMP)
	@LastModifiedDate
	private Date deptModifiyDate;

	// private transient String[] deptName;

	public Department() {
		super();
	}

	public long getDeptId() {
		return deptId;
	}

	public void setDeptId(long deptId) {
		this.deptId = deptId;
	}

	public String getNewDeptName() {
		return newDeptName;
	}

	public void setNewDeptName(String newDeptName) {
		this.newDeptName = newDeptName;
	}

	public String getDeptEmail() {
		return deptEmail;
	}

	public void setDeptEmail(String deptEmail) {
		this.deptEmail = deptEmail;
	}

	public String getDeptCreatedBy() {
		return deptCreatedBy;
	}

	public void setDeptCreatedBy(String deptCreatedBy) {
		this.deptCreatedBy = deptCreatedBy;
	}

	public String getDeptModifiedBy() {
		return deptModifiedBy;
	}

	public void setDeptModifiedBy(String deptModifiedBy) {
		this.deptModifiedBy = deptModifiedBy;
	}

	public String getDeptStatus() {
		return deptStatus;
	}

	public void setDeptStatus(String deptStatus) {
		this.deptStatus = deptStatus;
	}

	public Date getDeptModifiyDate() {
		return deptModifiyDate;
	}

	public void setDeptModifiyDate(Date deptModifiyDate) {
		this.deptModifiyDate = deptModifiyDate;
	}

}
