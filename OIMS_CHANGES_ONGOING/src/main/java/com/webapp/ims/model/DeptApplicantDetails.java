package com.webapp.ims.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Entity
@Table(name = "Dept_Applicant_Detail_Master", schema = "loc")
public class DeptApplicantDetails implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "APC_ID")
	private String appId;
	@Column(name = "APC_Modify_Date")
	@Temporal(TemporalType.TIMESTAMP)
	private Date apcModifiyDate;
	@Column(name = "APC_First_Name")
	@Size(max = 30)
	@NotEmpty
	@Pattern(regexp = "^[aA-zZ]\\w{1,29}$")
	private String appFirstName;

	@Column(name = "APC_Middle_Name")
	private String appMiddleName;

	@Column(name = "APC_Last_Name")
	@Size(max = 30)
	@NotEmpty
	@Pattern(regexp = "^[aA-zZ]\\w{1,29}$")
	private String appLastName;

	@Column(name = "APC_Gender")
	private String gender;

	@Column(name = "APC_Designation")
	@Size(max = 60)
	private String appDesignation;

	@Column(name = "APC_Email_Id")
	@Email
	@Size(max = 60)
	@NotEmpty
	@Pattern(regexp = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$")
	private String appEmailId;
	@Column(name = "APC_Phone_No")
	private String appPhoneNo;
	@Column(name = "APC_Mobile_No")
	@NotNull
	private Long appMobileNo;
	@Column(name = "APC_Pancard_No")
	@Size(min = 10, max = 10)
	@NotEmpty
	@Pattern(regexp = "[A-Z]{5}[0-9]{4}[A-Z]{1}")
	private String appPancardNo;
	@Column(name = "APC_Aadhar_No")
	@NotNull
	private Long appAadharNo;
	@Column(name = "APC_Add_Line1")
	@Size(max = 250)
	@NotEmpty
	private String appAddressLine1;
	@Column(name = "APC_Add_Line2")
	private String appAddressLine2;
	@Column(name = "APC_Country")
	@NotEmpty
	private String appCountry;
	@Column(name = "APC_State")
	@NotEmpty
	private String appState;
	@Column(name = "APC_District")
	@NotEmpty
	private String appDistrict;
	@Column(name = "APC_Pin_Code")
	@NotNull
	private Long appPinCode;
	@Column(name = "APC_Created_By")
	private String appCreatedBy;
	@Column(name = "APC_Modified_By")
	private String appModifiedBy;
	@Column(name = "APC_Photo")
	private byte[] appPhoto;
	@Column(name = "APC_Sign")
	private transient byte[] appSign;
	@Column(name = "APC_Status")
	private String status;

	private transient String fileName;

	private transient String base64imageFile = null;

	public DeptApplicantDetails() {
		super();
	}

	public Date getApcModifiyDate() {
		return apcModifiyDate;
	}

	public void setApcModifiyDate(Date apcModifiyDate) {
		this.apcModifiyDate = apcModifiyDate;
	}

	public String getBase64imageFile() {
		return base64imageFile;
	}

	public void setBase64imageFile(String base64imageFile) {
		this.base64imageFile = base64imageFile;
	}

	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	public String getAppFirstName() {
		return appFirstName;
	}

	public void setAppFirstName(String appFirstName) {
		this.appFirstName = appFirstName;
	}

	public String getAppMiddleName() {
		return appMiddleName;
	}

	public void setAppMiddleName(String appMiddleName) {
		this.appMiddleName = appMiddleName;
	}

	public String getAppLastName() {
		return appLastName;
	}

	public void setAppLastName(String appLastName) {
		this.appLastName = appLastName;
	}

	public String getAppEmailId() {
		return appEmailId;
	}

	public void setAppEmailId(String appEmailId) {
		this.appEmailId = appEmailId;
	}

	public String getAppDesignation() {
		return appDesignation;
	}

	public void setAppDesignation(String appDesignation) {
		this.appDesignation = appDesignation;
	}

	public String getAppAddressLine1() {
		return appAddressLine1;
	}

	public void setAppAddressLine1(String appAddressLine1) {
		this.appAddressLine1 = appAddressLine1;
	}

	public String getAppAddressLine2() {
		return appAddressLine2;
	}

	public void setAppAddressLine2(String appAddressLine2) {
		this.appAddressLine2 = appAddressLine2;
	}

	public String getAppCountry() {
		return appCountry;
	}

	public void setAppCountry(String appCountry) {
		this.appCountry = appCountry;
	}

	public String getAppState() {
		return appState;
	}

	public void setAppState(String appState) {
		this.appState = appState;
	}

	public String getAppDistrict() {
		return appDistrict;
	}

	public void setAppDistrict(String appDistrict) {
		this.appDistrict = appDistrict;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getAppCreatedBy() {
		return appCreatedBy;
	}

	public void setAppCreatedBy(String appCreatedBy) {
		this.appCreatedBy = appCreatedBy;
	}

	public String getAppModifiedBy() {
		return appModifiedBy;
	}

	public void setAppModifiedBy(String appModifiedBy) {
		this.appModifiedBy = appModifiedBy;
	}

	public byte[] getAppPhoto() {
		return appPhoto;
	}

	public void setAppPhoto(byte[] appPhoto) {
		this.appPhoto = appPhoto;
	}

	public byte[] getAppSign() {
		return appSign;
	}

	public void setAppSign(byte[] appSign) {
		this.appSign = appSign;
	}

	public String getAppPhoneNo() {
		return appPhoneNo;
	}

	public void setAppPhoneNo(String appPhoneNo) {
		this.appPhoneNo = appPhoneNo;
	}

	public Long getAppMobileNo() {
		return appMobileNo;
	}

	public void setAppMobileNo(Long appMobileNo) {
		this.appMobileNo = appMobileNo;
	}

	public String getAppPancardNo() {
		return appPancardNo;
	}

	public void setAppPancardNo(String appPancardNo) {
		this.appPancardNo = appPancardNo;
	}

	public Long getAppAadharNo() {
		return appAadharNo;
	}

	public void setAppAadharNo(Long appAadharNo) {
		this.appAadharNo = appAadharNo;
	}

	public Long getAppPinCode() {
		return appPinCode;
	}

	public void setAppPinCode(Long appPinCode) {
		this.appPinCode = appPinCode;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((appAadharNo == null) ? 0 : appAadharNo.hashCode());
		result = prime * result + ((appId == null) ? 0 : appId.hashCode());
		result = prime * result + ((appPancardNo == null) ? 0 : appPancardNo.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DeptApplicantDetails other = (DeptApplicantDetails) obj;
		if (appAadharNo == null) {
			if (other.appAadharNo != null)
				return false;
		} else if (!appAadharNo.equals(other.appAadharNo))
			return false;
		if (appId == null) {
			if (other.appId != null)
				return false;
		} else if (!appId.equals(other.appId))
			return false;
		if (appPancardNo == null) {
			if (other.appPancardNo != null)
				return false;
		} else if (!appPancardNo.equals(other.appPancardNo))
			return false;
		return true;
	}

}
