package com.webapp.ims.model;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "Dept_Business_Entity_Details", schema = "loc")
public class DeptBusinessEntityDetails implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "BE_ID")
	private String id;

	@Column(name = "BE_Name")
	private String businessEntityName;

	@Column(name = "BE_Org_Type")
	private String businessEntityType;

	@Column(name = "BE_Auth_Sign_Name")
	private String authorisedSignatoryName;

	@Column(name = "BE_Designation")
	private String businessDesignation;

	@Column(name = "BE_Email_Id")
	private String emailId;

	@Column(name = "BE_Mobile_No", columnDefinition = "numeric(10) default 0 ")
	private Long mobileNumber;

	@Column(name = "BE_FAX")
	private String fax;

	@Column(name = "BE_Address")
	private String businessAddress;

	@Column(name = "BE_Country")
	private String businessCountryName;

	@Column(name = "BE_State")
	private String businessStateName;

	@Column(name = "BE_District")
	private String businessDistrictName;

	@Column(name = "BE_Pin", columnDefinition = "numeric(6) default 0 ")
	private Long PinCode;

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Temporal(TemporalType.DATE)
	@Column(name = "BE_Estb_Yr")
	private Date yearEstablishment;

	@Column(name = "BE_Comp_PAN_No")
	private String companyPanNo = null;

	@Column(name = "BE_APC_ID")
	private String applicantDetailId;

	@Column(name = "BE_Created_By")
	private String created_By;

	@Column(name = "BE_Modified_By")
	private String modified_By;

	@Column(name = "BE_Modify_Date")
	@UpdateTimestamp
	private Timestamp modify_Date;

	@Column(name = "BE_Status")
	private String status;

	@Column(name = "BE_GSTIN")
	private String gstin = null;

	@Column(name = "BE_CIN")
	private String cin = null;

	@Column(name = "BE_MOA_Prtsp_Attmnt_FName")
	private String moaDocFname = null;

	@Column(name = "BE_Cert_Incp_Registration_FName")
	private String regisAttacDocFname = null;

	@Column(name = "BE_BOD_FName")
	private String bodDocFname = null;

	@Column(name = "BE_Indus_Affidavite_Fname")
	private String indusAffidaDocFname = null;

	@Column(name = "BE_MOA_Prtsp_Attmnt")
	private byte[] moaDoc = null;

	@Column(name = "BE_Cert_Incp_Registration")
	private byte[] registrationAttachedDoc = null;

	@Column(name = "BE_BOD_Doc")
	private byte[] bodDoc = null;

	@Column(name = "BE_Indus_Affidavite")
	private byte[] indusAffidaviteDoc = null;

	@Column(name = "BE_Ann_Format_Fname")
	private String annexureiaformat = null;

	@Column(name = "BE_ Ann_Format_Fdata")
	private byte[] annexiaformFdata = null;

	@Column(name = "BE_Phone_No")
	private Long phoneNo = 0l;
	@Column(name = "BE_IsPrescribed_Format")
	private String presFormat;
	@Column(name = "BE_IsDocument_Authorized")
	private String docAuthorized;

	@Column(name = "BE_DirDetails_Observ")
	private String dirDetailsObserv;

	@Column(name = "BE_supprtdoc_Observ")
	private String supprtdocObserv;

	@Column(name = "BE_Gstin_Observ")
	private String gstinObserv;

	@Column(name = "BE_PAN_Observ")
	private String panObserv;

	public String getSupprtdocObserv() {
		return supprtdocObserv;
	}

	public void setSupprtdocObserv(String supprtdocObserv) {
		this.supprtdocObserv = supprtdocObserv;
	}

	public String getGstinObserv() {
		return gstinObserv;
	}

	public void setGstinObserv(String gstinObserv) {
		this.gstinObserv = gstinObserv;
	}

	public String getPanObserv() {
		return panObserv;
	}

	public void setPanObserv(String panObserv) {
		this.panObserv = panObserv;
	}

	private transient String moaDocbase64File;
	private transient String regisAttacbase64File;
	private transient String bodDocbase64File;
	private transient String indusAffidaDocbase64File;

	public String getDirDetailsObserv() {
		return dirDetailsObserv;
	}

	public void setDirDetailsObserv(String dirDetailsObserv) {
		this.dirDetailsObserv = dirDetailsObserv;
	}

	public String getMoaDocbase64File() {
		return moaDocbase64File;
	}

	public void setMoaDocbase64File(String moaDocbase64File) {
		this.moaDocbase64File = moaDocbase64File;
	}

	public String getRegisAttacbase64File() {
		return regisAttacbase64File;
	}

	public void setRegisAttacbase64File(String regisAttacbase64File) {
		this.regisAttacbase64File = regisAttacbase64File;
	}

	public String getBodDocbase64File() {
		return bodDocbase64File;
	}

	public void setBodDocbase64File(String bodDocbase64File) {
		this.bodDocbase64File = bodDocbase64File;
	}

	public String getIndusAffidaDocbase64File() {
		return indusAffidaDocbase64File;
	}

	public void setIndusAffidaDocbase64File(String indusAffidaDocbase64File) {
		this.indusAffidaDocbase64File = indusAffidaDocbase64File;
	}

	public String getAnnexureiaformat() {
		return annexureiaformat;
	}

	public void setAnnexureiaformat(String annexureiaformat) {
		this.annexureiaformat = annexureiaformat;
	}

	public byte[] getAnnexiaformFdata() {
		return annexiaformFdata;
	}

	public void setAnnexiaformFdata(byte[] annexiaformFdata) {
		this.annexiaformFdata = annexiaformFdata;
	}

	public String getMoaDocFname() {
		return moaDocFname;
	}

	public void setMoaDocFname(String moaDocFname) {
		this.moaDocFname = moaDocFname;
	}

	public String getRegisAttacDocFname() {
		return regisAttacDocFname;
	}

	public void setRegisAttacDocFname(String regisAttacDocFname) {
		this.regisAttacDocFname = regisAttacDocFname;
	}

	public String getBodDocFname() {
		return bodDocFname;
	}

	public void setBodDocFname(String bodDocFname) {
		this.bodDocFname = bodDocFname;
	}

	public String getIndusAffidaDocFname() {
		return indusAffidaDocFname;
	}

	public void setIndusAffidaDocFname(String indusAffidaDocFname) {
		this.indusAffidaDocFname = indusAffidaDocFname;
	}

	public String getGstin() {
		return gstin;
	}

	public void setGstin(String gstin) {
		this.gstin = gstin;
	}

	public String getCin() {
		return cin;
	}

	public void setCin(String cin) {
		this.cin = cin;
	}

	public byte[] getBodDoc() {
		return bodDoc;
	}

	public void setBodDoc(byte[] bodDoc) {
		this.bodDoc = bodDoc;
	}

	public byte[] getIndusAffidaviteDoc() {
		return indusAffidaviteDoc;
	}

	public void setIndusAffidaviteDoc(byte[] indusAffidaviteDoc) {
		this.indusAffidaviteDoc = indusAffidaviteDoc;
	}

	public byte[] getMoaDoc() {
		return moaDoc;
	}

	public void setMoaDoc(byte[] moaDoc) {
		this.moaDoc = moaDoc;
	}

	public byte[] getRegistrationAttachedDoc() {
		return registrationAttachedDoc;
	}

	public void setRegistrationAttachedDoc(byte[] registrationAttachedDoc) {
		this.registrationAttachedDoc = registrationAttachedDoc;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getBusinessEntityName() {
		return businessEntityName;
	}

	public void setBusinessEntityName(String businessEntityName) {
		this.businessEntityName = businessEntityName;
	}

	public String getBusinessEntityType() {
		return businessEntityType;
	}

	public void setBusinessEntityType(String businessEntityType) {
		this.businessEntityType = businessEntityType;
	}

	public String getAuthorisedSignatoryName() {
		return authorisedSignatoryName;
	}

	public void setAuthorisedSignatoryName(String authorisedSignatoryName) {
		this.authorisedSignatoryName = authorisedSignatoryName;
	}

	public String getBusinessDesignation() {
		return businessDesignation;
	}

	public void setBusinessDesignation(String businessDesignation) {
		this.businessDesignation = businessDesignation;
	}

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	public Long getMobileNumber() {
		return mobileNumber;
	}

	public void setMobileNumber(Long mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	public String getFax() {
		return fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	public String getBusinessAddress() {
		return businessAddress;
	}

	public void setBusinessAddress(String businessAddress) {
		this.businessAddress = businessAddress;
	}

	public String getBusinessCountryName() {
		return businessCountryName;
	}

	public void setBusinessCountryName(String businessCountryName) {
		this.businessCountryName = businessCountryName;
	}

	public String getBusinessStateName() {
		return businessStateName;
	}

	public void setBusinessStateName(String businessStateName) {
		this.businessStateName = businessStateName;
	}

	public String getBusinessDistrictName() {
		return businessDistrictName;
	}

	public void setBusinessDistrictName(String businessDistrictName) {
		this.businessDistrictName = businessDistrictName;
	}

	public Long getPinCode() {
		return PinCode;
	}

	public void setPinCode(Long pinCode) {
		PinCode = pinCode;
	}

	public Date getYearEstablishment() {
		return yearEstablishment;
	}

	public void setYearEstablishment(Date yearEstablishment) {
		this.yearEstablishment = yearEstablishment;
	}

	public String getCompanyPanNo() {
		return companyPanNo;
	}

	public void setCompanyPanNo(String companyPanNo) {
		this.companyPanNo = companyPanNo;
	}

	public String getApplicantDetailId() {
		return applicantDetailId;
	}

	public void setApplicantDetailId(String applicantDetailId) {
		this.applicantDetailId = applicantDetailId;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getCreated_By() {
		return created_By;
	}

	public void setCreated_By(String created_By) {
		this.created_By = created_By;
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

	public Long getPhoneNo() {
		return phoneNo;
	}

	public void setPhoneNo(Long phoneNo) {
		this.phoneNo = phoneNo;
	}

	public DeptBusinessEntityDetails() {
		super();
	}

	public String getPresFormat() {
		return presFormat;
	}

	public void setPresFormat(String presFormat) {
		this.presFormat = presFormat;
	}

	public String getDocAuthorized() {
		return docAuthorized;
	}

	public void setDocAuthorized(String docAuthorized) {
		this.docAuthorized = docAuthorized;
	}

}