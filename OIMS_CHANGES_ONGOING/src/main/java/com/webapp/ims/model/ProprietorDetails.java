package com.webapp.ims.model;

import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.UpdateTimestamp;

@Entity
@Table(name = "Propriter_Details", schema = "loc")
public class ProprietorDetails {
	@Id
	@Column(name = "PR_ID")
	private String propId;

	@Column(name = "PR_Director_Name")
	private String directorName;

	@Column(name = "PR_Designation")
	private String designation;

	@Column(name = "PR_Percent_Equity")
	private Float equity;

	@Column(name = "Mobile_No")
	private Long mobileNo;

	@Column(name = "PR_Address")
	private String proprietorDetailsaddress;

	@Column(name = "PR_Email")
	private String email;

	@Column(name = "PR_Gender")
	private String gender;

	@Column(name = "PR_Category")
	private String category;

	@Column(name = "PR_Div_Status")
	private String div_Status;

	@Column(name = "PR_PAN_No")
	private String panCardNo;

	@Column(name = "Created_By")
	private String created_By;

	@Column(name = "Created_Date")
	private Date created_Date;

	@Column(name = "Modified_By")
	private String modified_By;

	@Column(name = "Modify_Date")
	@UpdateTimestamp
	private Timestamp modify_Date;

	@Column(name = "Status")
	private String status;

	@Column(name = "PR_BE_ID")
	private String businessEntityDetails;

	@Column(name = "PR_DIN")
	private String din;

	public String getDin() {
		return din;
	}

	public void setDin(String din) {
		this.din = din;
	}

	public ProprietorDetails() {

	}

	public String getDiv_Status() {
		return div_Status;
	}

	public void setDiv_Status(String div_Status) {
		this.div_Status = div_Status;
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

	public Timestamp getModify_Date() {
		return modify_Date;
	}

	public void setModify_Date(Timestamp modify_Date) {
		this.modify_Date = modify_Date;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getPropId() {
		return propId;
	}

	public void setPropId(String propId) {
		this.propId = propId;
	}

	public String getDirectorName() {
		return directorName;
	}

	public void setDirectorName(String directorName) {
		this.directorName = directorName;
	}

	public String getDesignation() {
		return designation;
	}

	public void setDesignation(String designation) {
		this.designation = designation;
	}

	public Float getEquity() {
		return equity;
	}

	public void setEquity(Float equity) {
		this.equity = equity;
	}

	public Long getMobileNo() {
		return mobileNo;
	}

	public void setMobileNo(Long mobileNo) {
		this.mobileNo = mobileNo;
	}

	public String getProprietorDetailsaddress() {
		return proprietorDetailsaddress;
	}

	public void setProprietorDetailsaddress(String proprietorDetailsaddress) {
		this.proprietorDetailsaddress = proprietorDetailsaddress;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getPanCardNo() {
		return panCardNo;
	}

	public void setPanCardNo(String panCardNo) {
		this.panCardNo = panCardNo;
	}

	public String getBusinessEntityDetails() {
		return businessEntityDetails;
	}

	public void setBusinessEntityDetails(String businessEntityDetails) {
		this.businessEntityDetails = businessEntityDetails;
	}

}
