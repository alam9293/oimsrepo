package com.webapp.ims.uam.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "Tbl_Role", schema = "loc")
public class TblRole {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	@Column(name = "Role_Id")
	private String roleId;

	@Column(name = "Role")
	private String role;

	@Column(name = "Role_Description")
	private String roleDescription;

	@Column(name = "Created_By")
	private String creator;

	@Column(name = "Approved_By")
	private String approver;

	@Column(name = "Creator_Date")
	@Temporal(TemporalType.TIMESTAMP)
	private Date creator_Date;
	
	@Column(name = "Approved_Date")
	@Temporal(TemporalType.TIMESTAMP)
	private Date approverDate;
	
	@Column(name = "Allowed_Forms")
	private String allowed_Forms;
	
	@Column(name = "Creator_Ip_Address")
	private String creator_Ip_Address;
	
	@Column(name = "Approver_Ip_Address")
	private String approverIpAddress;
	
	@Column(name = "Status")
	private String status;

	

	public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getRoleDescription() {
		return roleDescription;
	}

	public void setRoleDescription(String roleDescription) {
		this.roleDescription = roleDescription;
	}

	public String getCreator() {
		return creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	public String getApprover() {
		return approver;
	}

	public void setApprover(String approver) {
		this.approver = approver;
	}

	public Date getCreator_Date() {
		return creator_Date;
	}

	public void setCreator_Date(Date creator_Date) {
		this.creator_Date = creator_Date;
	}

	public Date getApproverDate() {
		return approverDate;
	}

	public void setApproverDate(Date approverDate) {
		this.approverDate = approverDate;
	}

	public String getAllowed_Forms() {
		return allowed_Forms;
	}

	public void setAllowed_Forms(String allowed_Forms) {
		this.allowed_Forms = allowed_Forms;
	}

	public String getCreator_Ip_Address() {
		return creator_Ip_Address;
	}

	public void setCreator_Ip_Address(String creator_Ip_Address) {
		this.creator_Ip_Address = creator_Ip_Address;
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
