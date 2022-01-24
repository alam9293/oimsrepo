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
@Table(name = "Tbl_Group", schema = "loc")
public class TblGroup {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	@Column(name = "Group_Id")
	private String groupId;
	
	

	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	@Column(name = "Group")
	private String group;

	@Column(name = "Group_Desc")
	private String group_Description;

	@Column(name = "Created_By")
	private String Creator;

	@Column(name = "Approved_By")
	private String Approver;

	@Column(name = "Creator_Date")
	@Temporal(TemporalType.TIMESTAMP)
	private Date Creator_Date;
	
	@Column(name = "Approved_Date")
	@Temporal(TemporalType.TIMESTAMP)
	private Date Approver_Date;
	
	@Column(name = "Creator_Ip_Address")
	private String Creator_Ip_Address;
	
	@Column(name = "Approver_Ip_Address")
	private String Approver_Ip_Address;
	
	@Column(name = "Status")
	private String status;


	public String getGroup() {
		return group;
	}

	public void setGroup(String group) {
		this.group = group;
	}

	public String getGroup_Description() {
		return group_Description;
	}

	public void setGroup_Description(String group_Description) {
		this.group_Description = group_Description;
	}

	public String getCreator() {
		return Creator;
	}

	public void setCreator(String creator) {
		Creator = creator;
	}

	public String getApprover() {
		return Approver;
	}

	public void setApprover(String approver) {
		Approver = approver;
	}

	public Date getCreator_Date() {
		return Creator_Date;
	}

	public void setCreator_Date(Date creator_Date) {
		Creator_Date = creator_Date;
	}

	public Date getApprover_Date() {
		return Approver_Date;
	}

	public void setApprover_Date(Date approver_Date) {
		Approver_Date = approver_Date;
	}

	public String getCreator_Ip_Address() {
		return Creator_Ip_Address;
	}

	public void setCreator_Ip_Address(String creator_Ip_Address) {
		Creator_Ip_Address = creator_Ip_Address;
	}

	public String getApprover_Ip_Address() {
		return Approver_Ip_Address;
	}

	public void setApprover_Ip_Address(String approver_Ip_Address) {
		Approver_Ip_Address = approver_Ip_Address;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	
}
