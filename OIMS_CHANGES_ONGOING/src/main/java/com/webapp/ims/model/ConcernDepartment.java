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
@Table(name = "Forward_Inline_Department", schema = "loc")
public class ConcernDepartment implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	@Column(name = "Fild_ID")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long conDeptId;
	@Column(name = "Fild_Created_By")
	private String conDeptCreatedBy;
	@Column(name = "Fild_Modified_By")
	private String conDeptModifiedBy;
	@Column(name = "Fild_Status")
	private String conDeptStatus;
	@Column(name = "Fild_Modified_Date")
	@Temporal(TemporalType.TIMESTAMP)
	@LastModifiedDate
	private Date conDeptModifiyDate;
	@Column(name = "Fild_User_ID")
	private String conDeptUserId;
	@Column(name = "Fild_APC_ID")
	private String conDeptApplId;
	@Column(name = "Fild_Dept_ID")
	private String deptId;
	// gopal
	private String[] deptName;

	public String[] getDeptName() {
		return deptName;
	}

	public void setDeptName(String[] deptName) {
		this.deptName = deptName;
	}

	public ConcernDepartment() {
		super();
	}

	public long getConDeptId() {
		return conDeptId;
	}

	public void setConDeptId(long conDeptId) {
		this.conDeptId = conDeptId;
	}

	public String getConDeptCreatedBy() {
		return conDeptCreatedBy;
	}

	public void setConDeptCreatedBy(String conDeptCreatedBy) {
		this.conDeptCreatedBy = conDeptCreatedBy;
	}

	public String getConDeptModifiedBy() {
		return conDeptModifiedBy;
	}

	public void setConDeptModifiedBy(String conDeptModifiedBy) {
		this.conDeptModifiedBy = conDeptModifiedBy;
	}

	public String getConDeptStatus() {
		return conDeptStatus;
	}

	public void setConDeptStatus(String conDeptStatus) {
		this.conDeptStatus = conDeptStatus;
	}

	public Date getConDeptModifiyDate() {
		return conDeptModifiyDate;
	}

	public void setConDeptModifiyDate(Date conDeptModifiyDate) {
		this.conDeptModifiyDate = conDeptModifiyDate;
	}

	public String getConDeptUserId() {
		return conDeptUserId;
	}

	public void setConDeptUserId(String conDeptUserId) {
		this.conDeptUserId = conDeptUserId;
	}

	public String getConDeptApplId() {
		return conDeptApplId;
	}

	public void setConDeptApplId(String conDeptApplId) {
		this.conDeptApplId = conDeptApplId;
	}

	public String getDeptId() {
		return deptId;
	}

	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}

}
