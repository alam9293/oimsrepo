package com.webapp.ims.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "Dept_Project_Details", schema = "loc")
public class DeptProjectDetails implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "PD_ID")
	private String id;

	/*
	 * @Column(name="PD_Proj_Name") private String projectUnitName;
	 */

	@Column(name = "PD_Name_Contact_Person")
	private String contactPersonName;

	@Column(name = "PD_Designation")
	private String designation;

	@Column(name = "PD_Proj_Dsc")
	private String projectDescription;

	@Column(name = "PD_Proj_Mobile_No")
	private Long mobileNo;

	@Column(name = "PD_Website")
	private String webSiteName;

	@Column(name = "PD_Proj_Full_Addr")
	private String fullAddress;

	@Column(name = "PD_District_Name")
	private String districtName;

	@Column(name = "PD_Mandal_Name")
	private String mandalName;

	@Column(name = "PD_Region_Name")
	private String resionName;

	@Column(name = "PD_Pin_Code")
	private Integer pinCode;

	@Column(name = "PD_Regi_Licence")
	private String regiOrLicense;

	/*
	 * @Column(name="PD_Det_Exits_Prop_Prod") private String detOfExisitOrPropProd;
	 */

	@Column(name = "PD_Reg_Licen_Fname")
	private String regiOrLicenseFileName;

	@Column(name = "PD_Ind_Licence_Upload")

	private byte[] regiOrLicenseFileData;

	@Column(name = "PD_Encl_Det_Rep_Fname")
	private String enclDetProRepFileName;

	@Column(name = "PD_Project_report_upload")

	private byte[] enclDetProRepFileData;

	@Column(name = "PD_CA_Certi_Fname")
	private String caCertificateFileName;

	@Column(name = "PD_CA_Certificate_Upd")

	private byte[] caCertificateFileData;

	@Column(name = "PD_Chart_Eng_Fname")
	private String charatEngFileName;

	@Column(name = "PD_Chartd_Eng_Doc")
	private byte[] charatEngFileData;

	@Column(name = "PD_APC_ID")
	private String applicantDetailId;

	@Column(name = "PD_Created_By")
	private String createdBy;

	@Column(name = "PD_Status")
	private String status;

	@Column(name = "PD_New_Project")
	private String newProject;

	@Column(name = "PD_Expansion")
	private String expansion;

	@Column(name = "PD_Diversification")
	private String diversification;

	// @Column(name="PD_Exis_Prod")
	private transient String ExistingProducts;

	// @Column(name="PD_Exis_Ins_Cap")
	private transient Long existingInstalledCapacity;

	// @Column(name="PD_Prop_Prod")
	private transient String proposedProducts;

	// @Column(name="PD_Prop_Ins_Cap")
	private transient Long proposedInstalledCapacity;

	// @Column(name="PD_Exis_Gr_Block")
	private transient Long existingGrossBlock;

	// @Column(name="PD_Prop_Gr_Block")
	private transient Long proposedGrossBlock;
	private transient String eicUnit;
	private transient String picUnit;
	private transient String pdfFileLicense;
	private transient String pdfFileDprReport;
	private transient String pdfFileCaReport;
	private transient String pdfFileCharReport;

	private transient String projReportbase64File;
	private transient String caReportbase64File;
	private transient String charEngbase64File;

	@Column(name = "PD_Nature_Of_Project")
	private String natureOfProject;

	public DeptProjectDetails() {
		super();
	}

	public String getProjReportbase64File() {
		return projReportbase64File;
	}

	public void setProjReportbase64File(String projReportbase64File) {
		this.projReportbase64File = projReportbase64File;
	}

	public String getCaReportbase64File() {
		return caReportbase64File;
	}

	public void setCaReportbase64File(String caReportbase64File) {
		this.caReportbase64File = caReportbase64File;
	}

	public String getCharEngbase64File() {
		return charEngbase64File;
	}

	public void setCharEngbase64File(String charEngbase64File) {
		this.charEngbase64File = charEngbase64File;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getEicUnit() {
		return eicUnit;
	}

	public void setEicUnit(String eicUnit) {
		this.eicUnit = eicUnit;
	}

	public String getPicUnit() {
		return picUnit;
	}

	public void setPicUnit(String picUnit) {
		this.picUnit = picUnit;
	}

	public String getNatureOfProject() {
		return natureOfProject;
	}

	public void setNatureOfProject(String natureOfProject) {
		this.natureOfProject = natureOfProject;
	}

	public String getContactPersonName() {
		return contactPersonName;
	}

	public void setContactPersonName(String contactPersonName) {
		this.contactPersonName = contactPersonName;
	}

	public String getDesignation() {
		return designation;
	}

	public void setDesignation(String designation) {
		this.designation = designation;
	}

	public String getProjectDescription() {
		return projectDescription;
	}

	public void setProjectDescription(String projectDescription) {
		this.projectDescription = projectDescription;
	}

	public String getWebSiteName() {
		return webSiteName;
	}

	public void setWebSiteName(String webSiteName) {
		this.webSiteName = webSiteName;
	}

	public String getFullAddress() {
		return fullAddress;
	}

	public void setFullAddress(String fullAddress) {
		this.fullAddress = fullAddress;
	}

	public String getDistrictName() {
		return districtName;
	}

	public void setDistrictName(String districtName) {
		this.districtName = districtName;
	}

	public String getMandalName() {
		return mandalName;
	}

	public void setMandalName(String mandalName) {
		this.mandalName = mandalName;
	}

	public String getResionName() {
		return resionName;
	}

	public void setResionName(String resionName) {
		this.resionName = resionName;
	}

	public Integer getPinCode() {
		return pinCode;
	}

	public void setPinCode(Integer pinCode) {
		this.pinCode = pinCode;
	}

	public String getRegiOrLicense() {
		return regiOrLicense;
	}

	public void setRegiOrLicense(String regiOrLicense) {
		this.regiOrLicense = regiOrLicense;
	}

	public String getExistingProducts() {
		return ExistingProducts;
	}

	public void setExistingProducts(String existingProducts) {
		ExistingProducts = existingProducts;
	}

	public String getProposedProducts() {
		return proposedProducts;
	}

	public void setProposedProducts(String proposedProducts) {
		this.proposedProducts = proposedProducts;
	}

	public Long getExistingInstalledCapacity() {
		return existingInstalledCapacity;
	}

	public void setExistingInstalledCapacity(Long existingInstalledCapacity) {
		this.existingInstalledCapacity = existingInstalledCapacity;
	}

	public Long getProposedInstalledCapacity() {
		return proposedInstalledCapacity;
	}

	public void setProposedInstalledCapacity(Long proposedInstalledCapacity) {
		this.proposedInstalledCapacity = proposedInstalledCapacity;
	}

	public Long getExistingGrossBlock() {
		return existingGrossBlock;
	}

	public void setExistingGrossBlock(Long existingGrossBlock) {
		this.existingGrossBlock = existingGrossBlock;
	}

	public Long getProposedGrossBlock() {
		return proposedGrossBlock;
	}

	public void setProposedGrossBlock(Long proposedGrossBlock) {
		this.proposedGrossBlock = proposedGrossBlock;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getApplicantDetailId() {
		return applicantDetailId;
	}

	public void setApplicantDetailId(String applicantDetailId) {
		this.applicantDetailId = applicantDetailId;
	}

	public Long getMobileNo() {
		return mobileNo;
	}

	public void setMobileNo(Long mobileNo) {
		this.mobileNo = mobileNo;
	}

	public String getEnclDetProRepFileName() {
		return enclDetProRepFileName;
	}

	public void setEnclDetProRepFileName(String enclDetProRepFileName) {
		this.enclDetProRepFileName = enclDetProRepFileName;
	}

	public byte[] getEnclDetProRepFileData() {
		return enclDetProRepFileData;
	}

	public void setEnclDetProRepFileData(byte[] enclDetProRepFileData) {
		this.enclDetProRepFileData = enclDetProRepFileData;
	}

	public String getCaCertificateFileName() {
		return caCertificateFileName;
	}

	public void setCaCertificateFileName(String caCertificateFileName) {
		this.caCertificateFileName = caCertificateFileName;
	}

	public byte[] getCaCertificateFileData() {
		return caCertificateFileData;
	}

	public void setCaCertificateFileData(byte[] caCertificateFileData) {
		this.caCertificateFileData = caCertificateFileData;
	}

	public String getCharatEngFileName() {
		return charatEngFileName;
	}

	public void setCharatEngFileName(String charatEngFileName) {
		this.charatEngFileName = charatEngFileName;
	}

	public byte[] getCharatEngFileData() {
		return charatEngFileData;
	}

	public void setCharatEngFileData(byte[] charatEngFileData) {
		this.charatEngFileData = charatEngFileData;
	}

	public String getRegiOrLicenseFileName() {
		return regiOrLicenseFileName;
	}

	public void setRegiOrLicenseFileName(String regiOrLicenseFileName) {
		this.regiOrLicenseFileName = regiOrLicenseFileName;
	}

	public byte[] getRegiOrLicenseFileData() {
		return regiOrLicenseFileData;
	}

	public void setRegiOrLicenseFileData(byte[] regiOrLicenseFileData) {
		this.regiOrLicenseFileData = regiOrLicenseFileData;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getNewProject() {
		return newProject;
	}

	public void setNewProject(String newProject) {
		this.newProject = newProject;
	}

	public String getExpansion() {
		return expansion;
	}

	public void setExpansion(String expansion) {
		this.expansion = expansion;
	}

	public String getDiversification() {
		return diversification;
	}

	public void setDiversification(String diversification) {
		this.diversification = diversification;
	}

	public String getPdfFileLicense() {
		return pdfFileLicense;
	}

	public void setPdfFileLicense(String pdfFileLicense) {
		this.pdfFileLicense = pdfFileLicense;
	}

	public String getPdfFileDprReport() {
		return pdfFileDprReport;
	}

	public void setPdfFileDprReport(String pdfFileDprReport) {
		this.pdfFileDprReport = pdfFileDprReport;
	}

	public String getPdfFileCaReport() {
		return pdfFileCaReport;
	}

	public void setPdfFileCaReport(String pdfFileCaReport) {
		this.pdfFileCaReport = pdfFileCaReport;
	}

	public String getPdfFileCharReport() {
		return pdfFileCharReport;
	}

	public void setPdfFileCharReport(String pdfFileCharReport) {
		this.pdfFileCharReport = pdfFileCharReport;
	}

}
