package com.webapp.ims.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "ISF_Cstm_Incentive", schema = "loc")
public class AvailCustomisedDetails {
	@Id
	@Column(name = "Cstm_Inc_ID")
	private String acdid;

	public String getAcdid() {
		return acdid;
	}

	public void setAcdid(String acdid) {
		this.acdid = acdid;
	}

	@Column(name = "ISF_Form_Dtls_ISF_ID")
	private String incentiveDetails;

	@Column(name = "Add_Inc_Type")
	private String addIncentiveType;

	@Column(name = "Add_Amt")
	private Long addAmt;

	@Column(name = "Type_Dtl_Cus_Inc")
	private String typeDtlCusIncentives;

	@Column(name = "Oth_Add_Request")
	private String othAddRequest;

	@Column(name = "Created_By")
	private String created_By;

	@Column(name = "Created_Date")
	private Date created_Date;

	@Column(name = "Modified_By")
	private String modified_By;

	@Column(name = "Modify_Date")
	private Date modify_Date;

	@Column(name = "Status")
	private String status;

	public AvailCustomisedDetails() {

	}

	public String getAddIncentiveType() {
		return addIncentiveType;
	}

	public void setAddIncentiveType(String addIncentiveType) {
		this.addIncentiveType = addIncentiveType;
	}

	public Long getAddAmt() {
		return addAmt;
	}

	public void setAddAmt(Long addAmt) {
		this.addAmt = addAmt;
	}

	public String getTypeDtlCusIncentives() {
		return typeDtlCusIncentives;
	}

	public void setTypeDtlCusIncentives(String typeDtlCusIncentives) {
		this.typeDtlCusIncentives = typeDtlCusIncentives;
	}

	public String getOthAddRequest() {
		return othAddRequest;
	}

	public void setOthAddRequest(String othAddRequest) {
		this.othAddRequest = othAddRequest;
	}

	public String getCreated_By() {
		return created_By;
	}

	public void setCreated_By(String created_By) {
		this.created_By = created_By;
	}

	public Date getCreated_Date() {
		return created_Date;
	}

	public void setCreated_Date(Date created_Date) {
		this.created_Date = created_Date;
	}

	public String getModified_By() {
		return modified_By;
	}

	public void setModified_By(String modified_By) {
		this.modified_By = modified_By;
	}

	public Date getModify_Date() {
		return modify_Date;
	}

	public void setModify_Date(Date modify_Date) {
		this.modify_Date = modify_Date;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getIncentiveDetails() {
		return incentiveDetails;
	}

	public void setIncentiveDetails(String incentiveDetails) {
		this.incentiveDetails = incentiveDetails;
	}

}
