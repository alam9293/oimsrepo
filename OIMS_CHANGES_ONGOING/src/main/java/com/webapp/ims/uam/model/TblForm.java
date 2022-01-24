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
@Table(name = "Tbl_Form", schema = "loc")
public class TblForm {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	@Column(name = "Id")
	private String id;
	
	@Column(name = "Action_Name")
	private String actionName;

	@Column(name = "Display_Name")
	private String displayName;

	@Column(name = "Created_By")
	private String createdBy;

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

	@Column(name = "Display_Order")
	private int Display_Order;
	
	@Column(name = "Dept_Code")
	private String deptCode;

	public String getDeptCode() {
		return deptCode;
	}

	public void setDeptCode(String deptCode) {
		this.deptCode = deptCode;
	}

	public String getactionName() {
		return actionName;
	}

	public void setactionName(String action_Name) {
		actionName = action_Name;
	}

	public String getdisplayName() {
		return displayName;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setDisplay_Name(String display_Name) {
		displayName = display_Name;
	}

	public int getDisplay_Order() {
		return Display_Order;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
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

	public void setDisplay_Order(int display_Order) {
		Display_Order = display_Order;
	}
	
}
