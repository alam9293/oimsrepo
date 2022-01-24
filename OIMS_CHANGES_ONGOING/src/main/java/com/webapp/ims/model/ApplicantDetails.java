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
@Table(name = "Applicant_Detail_Master", schema = "loc")
public class ApplicantDetails implements Serializable {

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

	@Column(name = "controlid")
	private String controlId;

	@Column(name = "unitid")
	private String unitId;

	@Column(name = "serviceid")
	private String serviceId;

	@Column(name = "processindustryid")
	private String processIndustryId;

	@Column(name = "applicationid")
	private String applicationId;

	@Column(name = "statuscode")
	private String statusCode;

	@Column(name = "remarks")
	private String remarks;

	@Column(name = "pendancylevel")
	private String pendancyLevel;

	@Column(name = "feeamount")
	private String feeAmount;

	@Column(name = "feestatus")
	private String feeStatus;

	@Column(name = "transactionid")
	private String transactionId;

	@Column(name = "transactiondate")
	private String transactionDate;

	@Column(name = "transactiondatetime")
	private String transactionDateTime;

	@Column(name = "noccertificatenumber")
	private String nOCCertificateNumber;

	@Column(name = "nocurl")
	private String nOCURL;

	@Column(name = "isnocurlactiveyesno")
	private String iSNOCURLActiveYesNO;

	@Column(name = "passsalt")
	private String passsalt;

	@Column(name = "requestid")
	private String requestId;

	@Column(name = "objectionrejectioncode")
	private String objectionRejectionCode;

	@Column(name = "iscertificatevalidlifetime")
	private String isCertificateValidLifeTime;

	@Column(name = "certificateexpdateddmmyyyy")
	private String certificateEXPDateDDMMYYYY;

	@Column(name = "d1")
	private String d1;

	@Column(name = "d2")
	private String d2;

	@Column(name = "d3")
	private String d3;

	@Column(name = "d4")
	private String d4;

	@Column(name = "d5")
	private String d5;

	@Column(name = "d6")
	private String d6;

	@Column(name = "d7")
	private String d7;

	@Column(name = "submit_status")
	private boolean submitStatus;

	public ApplicantDetails() {
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

	public String getControlId() {
		return controlId;
	}

	public void setControlId(String controlId) {
		this.controlId = controlId;
	}

	public String getUnitId() {
		return unitId;
	}

	public void setUnitId(String unitId) {
		this.unitId = unitId;
	}

	public String getServiceId() {
		return serviceId;
	}

	public void setServiceId(String serviceId) {
		this.serviceId = serviceId;
	}

	public String getProcessIndustryId() {
		return processIndustryId;
	}

	public void setProcessIndustryId(String processIndustryId) {
		this.processIndustryId = processIndustryId;
	}

	public String getApplicationId() {
		return applicationId;
	}

	public void setApplicationId(String applicationId) {
		this.applicationId = applicationId;
	}

	public String getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(String statusCode) {
		this.statusCode = statusCode;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getPendancyLevel() {
		return pendancyLevel;
	}

	public void setPendancyLevel(String pendancyLevel) {
		this.pendancyLevel = pendancyLevel;
	}

	public String getFeeAmount() {
		return feeAmount;
	}

	public void setFeeAmount(String feeAmount) {
		this.feeAmount = feeAmount;
	}

	public String getFeeStatus() {
		return feeStatus;
	}

	public void setFeeStatus(String feeStatus) {
		this.feeStatus = feeStatus;
	}

	public String getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}

	public String getTransactionDate() {
		return transactionDate;
	}

	public void setTransactionDate(String transactionDate) {
		this.transactionDate = transactionDate;
	}

	public String getTransactionDateTime() {
		return transactionDateTime;
	}

	public void setTransactionDateTime(String transactionDateTime) {
		this.transactionDateTime = transactionDateTime;
	}

	public String getnOCCertificateNumber() {
		return nOCCertificateNumber;
	}

	public void setnOCCertificateNumber(String nOCCertificateNumber) {
		this.nOCCertificateNumber = nOCCertificateNumber;
	}

	public String getnOCURL() {
		return nOCURL;
	}

	public void setnOCURL(String nOCURL) {
		this.nOCURL = nOCURL;
	}

	public String getiSNOCURLActiveYesNO() {
		return iSNOCURLActiveYesNO;
	}

	public void setiSNOCURLActiveYesNO(String iSNOCURLActiveYesNO) {
		this.iSNOCURLActiveYesNO = iSNOCURLActiveYesNO;
	}

	public String getPasssalt() {
		return passsalt;
	}

	public void setPasssalt(String passsalt) {
		this.passsalt = passsalt;
	}

	public String getRequestId() {
		return requestId;
	}

	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}

	public String getObjectionRejectionCode() {
		return objectionRejectionCode;
	}

	public void setObjectionRejectionCode(String objectionRejectionCode) {
		this.objectionRejectionCode = objectionRejectionCode;
	}

	public String getIsCertificateValidLifeTime() {
		return isCertificateValidLifeTime;
	}

	public void setIsCertificateValidLifeTime(String isCertificateValidLifeTime) {
		this.isCertificateValidLifeTime = isCertificateValidLifeTime;
	}

	public String getCertificateEXPDateDDMMYYYY() {
		return certificateEXPDateDDMMYYYY;
	}

	public void setCertificateEXPDateDDMMYYYY(String certificateEXPDateDDMMYYYY) {
		this.certificateEXPDateDDMMYYYY = certificateEXPDateDDMMYYYY;
	}

	public String getD1() {
		return d1;
	}

	public void setD1(String d1) {
		this.d1 = d1;
	}

	public String getD2() {
		return d2;
	}

	public void setD2(String d2) {
		this.d2 = d2;
	}

	public String getD3() {
		return d3;
	}

	public void setD3(String d3) {
		this.d3 = d3;
	}

	public String getD4() {
		return d4;
	}

	public void setD4(String d4) {
		this.d4 = d4;
	}

	public String getD5() {
		return d5;
	}

	public void setD5(String d5) {
		this.d5 = d5;
	}

	public String getD6() {
		return d6;
	}

	public void setD6(String d6) {
		this.d6 = d6;
	}

	public String getD7() {
		return d7;
	}

	public void setD7(String d7) {
		this.d7 = d7;
	}

	public boolean isSubmitStatus() {
		return submitStatus;
	}

	public void setSubmitStatus(boolean submitStatus) {
		this.submitStatus = submitStatus;
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
		ApplicantDetails other = (ApplicantDetails) obj;
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
