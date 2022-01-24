package com.webapp.ims.uam.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "Tbl_Department", schema = "loc")
public class TblDepartment implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name = "Dept_Id")
	public String deptId;
	
	@Column(name = "Dept_Name")
	public String deptName;
	
	
	@Column(name = "Created_By")
	private String createdBy;
	
	@Column(name = "Dept_Desc")
	private String deptDesc;
	
	@Column(name = "Created_Date")
	@Temporal(TemporalType.TIMESTAMP)
	private Date CreatedDate;

	@Column(name = "Creator_Ip_Address")
	private String CreatorIpAddress;
	
	@Column(name = "Approved_By")
	private String approvedBy;
	
	@Column(name = "Approved_Date")
	@Temporal(TemporalType.TIMESTAMP)
	private Date approvedDate;

	@Column(name = "Approver_Ip_Address")
	private String approverIpAddress;
	
	@Column(name = "Status")
	private String status;

	public String getDeptId() {
		return deptId;
	}

	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}

	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public String getDeptDesc() {
		return deptDesc;
	}

	public void setDeptDesc(String deptDesc) {
		this.deptDesc = deptDesc;
	}

	public Date getCreatedDate() {
		return CreatedDate;
	}

	public void setCreatedDate(Date createdDate) {
		CreatedDate = createdDate;
	}

	public String getCreatorIpAddress() {
		return CreatorIpAddress;
	}

	public void setCreatorIpAddress(String creatorIpAddress) {
		CreatorIpAddress = creatorIpAddress;
	}

	public String getApprovedBy() {
		return approvedBy;
	}

	public void setApprovedBy(String approvedBy) {
		this.approvedBy = approvedBy;
	}

	public Date getApprovedDate() {
		return approvedDate;
	}

	public void setApprovedDate(Date approvedDate) {
		this.approvedDate = approvedDate;
	}

	public String getApproverIpAddress() {
		return approverIpAddress;
	}

	public void setApproverIpAddress(String approverIpAddress) {
		this.approverIpAddress = approverIpAddress;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
}
