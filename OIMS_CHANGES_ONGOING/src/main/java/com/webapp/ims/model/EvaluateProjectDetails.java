package com.webapp.ims.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "Dept_Project_Details", schema = "loc")

public class EvaluateProjectDetails implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "PD_ID")
	private String id;

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

	@Column(name = "PD_Proj_Registered_Addr")
	private String registeredAddr;

	@Column(name = "PD_District_Name")
	private String districtName;

	@Column(name = "PD_Mandal_Name")
	private String mandalName;

	@Column(name = "PD_Region_Name")
	private String resionName;

	@Column(name = "PD_Pin_Code")
	private Integer pinCode;

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

	@Column(name = "PD_Product_Details")
	private String productDetails;

	@Column(name = "PD_Diversification")
	private String diversification;

	@Column(name = "PD_Nature_Of_Project")
	private String natureOfProject;

	// Observation Column Start
	@Column(name = "PD_propsProdtDetail_Observ")
	private String propsProdtDetailObserv;

	@Column(name = "PD_Project_Observ")
	private String projectObserv;

	@Column(name = "PD_ProdDetail_Obserb")
	private String prodDetailObserv;

	@Column(name = "PD_Location_Observ")
	private String locationObserv;

	@Column(name = "PD_listAssets")
	private String listAssets;

	@Column(name = "PD_listAssets_Observ")
	private String listAssetsObserv;

	@Column(name = "PD_AnexurUndertk")
	private String anexurUndertk;

	@Column(name = "PD_AnexurUndertk_Observ")
	private String anexurUndertkObserv;

	@Column(name = "PD_ExpDivfObserv_Observ")
	private String expDivfObserv;

	@Column(name = "solar_captive_power")
	private String solarCaptivePower;

	@Column(name = "solarcaptivepowerobserv")
	private String solarCaptivePowerObserv;

	// Observation Column End

	public String getExpDivfObserv() {
		return expDivfObserv;
	}

	public void setExpDivfObserv(String expDivfObserv) {
		this.expDivfObserv = expDivfObserv;
	}

	public String getProjectObserv() {
		return projectObserv;
	}

	public String getAnexurUndertk() {
		return anexurUndertk;
	}

	public void setAnexurUndertk(String anexurUndertk) {
		this.anexurUndertk = anexurUndertk;
	}

	public String getAnexurUndertkObserv() {
		return anexurUndertkObserv;
	}

	public void setAnexurUndertkObserv(String anexurUndertkObserv) {
		this.anexurUndertkObserv = anexurUndertkObserv;
	}

	public String getListAssets() {
		return listAssets;
	}

	public void setListAssets(String listAssets) {
		this.listAssets = listAssets;
	}

	public String getListAssetsObserv() {
		return listAssetsObserv;
	}

	public void setListAssetsObserv(String listAssetsObserv) {
		this.listAssetsObserv = listAssetsObserv;
	}

	public String getPropsProdtDetailObserv() {
		return propsProdtDetailObserv;
	}

	public void setPropsProdtDetailObserv(String propsProdtDetailObserv) {
		this.propsProdtDetailObserv = propsProdtDetailObserv;
	}

	public void setProjectObserv(String projectObserv) {
		this.projectObserv = projectObserv;
	}

	public String getProdDetailObserv() {
		return prodDetailObserv;
	}

	public void setProdDetailObserv(String prodDetailObserv) {
		this.prodDetailObserv = prodDetailObserv;
	}

	public String getLocationObserv() {
		return locationObserv;
	}

	public void setLocationObserv(String locationObserv) {
		this.locationObserv = locationObserv;
	}

	// @Column(name="PD_Exis_Prod")
	private transient String epdExisProducts;

	// @Column(name="PD_Exis_Ins_Cap")
	private transient String epdExisInstallCapacity;

	// @Column(name="PD_Prop_Prod")
	private transient String epdPropProducts;

	// @Column(name="PD_Prop_Ins_Cap")
	private transient String epdPropInstallCapacity;

	// @Column(name="PD_Exis_Gr_Block")
	private transient String epdExisGrossBlock;

	// @Column(name="PD_Prop_Gr_Block")
	private transient String epdPropoGrossBlock;
	private transient String eicUnit;
	private transient String picUnit;
	private transient String pdfFileLicense;
	private transient String pdfFileDprReport;
	private transient String pdfFileCaReport;
	private transient String pdfFileCharReport;
	private transient String epdId;
	private transient Long invFci;
	private transient Long invTotalProjCost;
	private transient String invIndType;
	private transient Date propCommProdDate;
	private transient Date invCommenceDate;

	// Evaluate Application Related Columns
	private transient String landcostRemarks;
	private transient String buildingRemarks;
	private transient String plantAndMachRemarks;
	private transient String fixedAssetRemarks;

	private transient String iemNumber;
	private transient String govtEquity;
	private transient String eligcapInvest;
	private transient Long cAStatutoryAmt;
	private transient String cAStatutoryDate;

	private transient String landcostFci;
	private transient String buildingFci;
	private transient String plantAndMachFci;
	private transient String fixedAssetFci;
	private transient Long landcostIIEPP;
	private transient Long buildingIIEPP;
	private transient Long plantAndMachIIEPP;
	private transient Long fixedAssetIIEPP;

	private transient String presFormat;
	private transient String docAuthorized;
	private transient String authorisedSignatoryName;
	private transient String companyPanNo;
	private transient String gstin;
	private transient Date CreatedDate;

	private transient String eciItem;
	private transient String eciIsFci;
	private transient Long eciDPRInvest;
	private transient Long eciIIEPPInvest;
	private transient String eciPICUP_Remarks;

	// Mean of Finance Start

	private transient String emfphaseNo;
	private transient String emfphsItemName;
	private transient Long emfphsInvestAmt;

	public String getEmfphaseNo() {
		return emfphaseNo;
	}

	public void setEmfphaseNo(String emfphaseNo) {
		this.emfphaseNo = emfphaseNo;
	}

	public String getEmfphsItemName() {
		return emfphsItemName;
	}

	public void setEmfphsItemName(String emfphsItemName) {
		this.emfphsItemName = emfphsItemName;
	}

	public Long getEmfphsInvestAmt() {
		return emfphsInvestAmt;
	}

	public void setEmfphsInvestAmt(Long emfphsInvestAmt) {
		this.emfphsInvestAmt = emfphsInvestAmt;
	}

	// End Mean of Finance End

	// Observation transient Variable Start
	private transient String dirDetailsObserv;

	private transient String totalDetailObserv;
	private transient String catIndusUndtObserv;
	private transient String propsPlntMcnryCostObserv;

	public String getPropsPlntMcnryCostObserv() {
		return propsPlntMcnryCostObserv;
	}

	public void setPropsPlntMcnryCostObserv(String propsPlntMcnryCostObserv) {
		this.propsPlntMcnryCostObserv = propsPlntMcnryCostObserv;
	}

	private transient String optcutofdateobserv;
	private transient String dateofComProdObserv;
	private transient String detailProjReportObserv;
	private transient String propCapInvObserv;
	private transient String totlCostProjObserv;

	private transient String mofObserv;
	private transient String IemRegObserv;
	private transient String indusUntkObserv;

	private transient String eligblInvPerdLargeObserv;
	private transient String eligblInvPerdMegaObserv;
	private transient String eligblInvPerdSupermegaObserv;
	private transient String eligblCapInvObserv;
	private transient String projPhasesObserv;

	private transient String caStatutoryObserv;
	private transient String authSignatoryObserv;
	private transient String appformatObserv;

	private transient String supprtdocObserv;
	private transient String gstinObserv;
	private transient String panObserv;

	private transient String subDateAppObserv;

	public String getEligblInvPerdLargeObserv() {
		return eligblInvPerdLargeObserv;
	}

	public void setEligblInvPerdLargeObserv(String eligblInvPerdLargeObserv) {
		this.eligblInvPerdLargeObserv = eligblInvPerdLargeObserv;
	}

	public String getEligblInvPerdSupermegaObserv() {
		return eligblInvPerdSupermegaObserv;
	}

	public void setEligblInvPerdSupermegaObserv(String eligblInvPerdSupermegaObserv) {
		this.eligblInvPerdSupermegaObserv = eligblInvPerdSupermegaObserv;
	}

	public String getEligblInvPerdMegaObserv() {
		return eligblInvPerdMegaObserv;
	}

	public void setEligblInvPerdMegaObserv(String eligblInvPerdMegaObserv) {
		this.eligblInvPerdMegaObserv = eligblInvPerdMegaObserv;
	}

	public String getSubDateAppObserv() {
		return subDateAppObserv;
	}

	public void setSubDateAppObserv(String subDateAppObserv) {
		this.subDateAppObserv = subDateAppObserv;
	}

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

	public String getCaStatutoryObserv() {
		return caStatutoryObserv;
	}

	public void setCaStatutoryObserv(String caStatutoryObserv) {
		this.caStatutoryObserv = caStatutoryObserv;
	}

	public String getAuthSignatoryObserv() {
		return authSignatoryObserv;
	}

	public void setAuthSignatoryObserv(String authSignatoryObserv) {
		this.authSignatoryObserv = authSignatoryObserv;
	}

	public String getAppformatObserv() {
		return appformatObserv;
	}

	public void setAppformatObserv(String appformatObserv) {
		this.appformatObserv = appformatObserv;
	}

	public String getEligblCapInvObserv() {
		return eligblCapInvObserv;
	}

	public void setEligblCapInvObserv(String eligblCapInvObserv) {
		this.eligblCapInvObserv = eligblCapInvObserv;
	}

	public String getProjPhasesObserv() {
		return projPhasesObserv;
	}

	public void setProjPhasesObserv(String projPhasesObserv) {
		this.projPhasesObserv = projPhasesObserv;
	}

	public String getMofObserv() {
		return mofObserv;
	}

	public void setMofObserv(String mofObserv) {
		this.mofObserv = mofObserv;
	}

	public String getIemRegObserv() {
		return IemRegObserv;
	}

	public void setIemRegObserv(String iemRegObserv) {
		IemRegObserv = iemRegObserv;
	}

	public String getIndusUntkObserv() {
		return indusUntkObserv;
	}

	public void setIndusUntkObserv(String indusUntkObserv) {
		this.indusUntkObserv = indusUntkObserv;
	}

	public String getPropCapInvObserv() {
		return propCapInvObserv;
	}

	public void setPropCapInvObserv(String propCapInvObserv) {
		this.propCapInvObserv = propCapInvObserv;
	}

	public String getTotlCostProjObserv() {
		return totlCostProjObserv;
	}

	public void setTotlCostProjObserv(String totlCostProjObserv) {
		this.totlCostProjObserv = totlCostProjObserv;
	}

	public String getOptcutofdateobserv() {
		return optcutofdateobserv;
	}

	public void setOptcutofdateobserv(String optcutofdateobserv) {
		this.optcutofdateobserv = optcutofdateobserv;
	}

	public String getDateofComProdObserv() {
		return dateofComProdObserv;
	}

	public void setDateofComProdObserv(String dateofComProdObserv) {
		this.dateofComProdObserv = dateofComProdObserv;
	}

	public String getDetailProjReportObserv() {
		return detailProjReportObserv;
	}

	public void setDetailProjReportObserv(String detailProjReportObserv) {
		this.detailProjReportObserv = detailProjReportObserv;
	}

	public String getCatIndusUndtObserv() {
		return catIndusUndtObserv;
	}

	public void setCatIndusUndtObserv(String catIndusUndtObserv) {
		this.catIndusUndtObserv = catIndusUndtObserv;
	}

	public String getTotalDetailObserv() {
		return totalDetailObserv;
	}

	public void setTotalDetailObserv(String totalDetailObserv) {
		this.totalDetailObserv = totalDetailObserv;
	}

	public String getDirDetailsObserv() {
		return dirDetailsObserv;
	}

	public void setDirDetailsObserv(String dirDetailsObserv) {
		this.dirDetailsObserv = dirDetailsObserv;
	}

	public EvaluateProjectDetails() {
		super();
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

	public String getEpdExisProducts() {
		return epdExisProducts;
	}

	public void setEpdExisProducts(String epdExisProducts) {
		this.epdExisProducts = epdExisProducts;
	}

	public String getEpdExisInstallCapacity() {
		return epdExisInstallCapacity;
	}

	public void setEpdExisInstallCapacity(String epdExisInstallCapacity) {
		this.epdExisInstallCapacity = epdExisInstallCapacity;
	}

	public String getEpdPropProducts() {
		return epdPropProducts;
	}

	public void setEpdPropProducts(String epdPropProducts) {
		this.epdPropProducts = epdPropProducts;
	}

	public String getEpdPropInstallCapacity() {
		return epdPropInstallCapacity;
	}

	public void setEpdPropInstallCapacity(String epdPropInstallCapacity) {
		this.epdPropInstallCapacity = epdPropInstallCapacity;
	}

	public String getEpdExisGrossBlock() {
		return epdExisGrossBlock;
	}

	public void setEpdExisGrossBlock(String epdExisGrossBlock) {
		this.epdExisGrossBlock = epdExisGrossBlock;
	}

	public String getEpdPropoGrossBlock() {
		return epdPropoGrossBlock;
	}

	public void setEpdPropoGrossBlock(String epdPropoGrossBlock) {
		this.epdPropoGrossBlock = epdPropoGrossBlock;
	}

	public String getEpdId() {
		return epdId;
	}

	public void setEpdId(String epdId) {
		this.epdId = epdId;
	}

	public Long getInvFci() {
		return invFci;
	}

	public void setInvFci(Long invFci) {
		this.invFci = invFci;
	}

	public Long getInvTotalProjCost() {
		return invTotalProjCost;
	}

	public void setInvTotalProjCost(Long invTotalProjCost) {
		this.invTotalProjCost = invTotalProjCost;
	}

	public String getInvIndType() {
		return invIndType;
	}

	public void setInvIndType(String invIndType) {
		this.invIndType = invIndType;
	}

	public String getProductDetails() {
		return productDetails;
	}

	public void setProductDetails(String productDetails) {
		this.productDetails = productDetails;
	}

	public String getLandcostRemarks() {
		return landcostRemarks;
	}

	public void setLandcostRemarks(String landcostRemarks) {
		this.landcostRemarks = landcostRemarks;
	}

	public String getBuildingRemarks() {
		return buildingRemarks;
	}

	public void setBuildingRemarks(String buildingRemarks) {
		this.buildingRemarks = buildingRemarks;
	}

	public String getPlantAndMachRemarks() {
		return plantAndMachRemarks;
	}

	public void setPlantAndMachRemarks(String plantAndMachRemarks) {
		this.plantAndMachRemarks = plantAndMachRemarks;
	}

	public String getFixedAssetRemarks() {
		return fixedAssetRemarks;
	}

	public void setFixedAssetRemarks(String fixedAssetRemarks) {
		this.fixedAssetRemarks = fixedAssetRemarks;
	}

	public String getIemNumber() {
		return iemNumber;
	}

	public void setIemNumber(String iemNumber) {
		this.iemNumber = iemNumber;
	}

	public String getGovtEquity() {
		return govtEquity;
	}

	public void setGovtEquity(String govtEquity) {
		this.govtEquity = govtEquity;
	}

	public String getEligcapInvest() {
		return eligcapInvest;
	}

	public void setEligcapInvest(String eligcapInvest) {
		this.eligcapInvest = eligcapInvest;
	}

	public Long getcAStatutoryAmt() {
		return cAStatutoryAmt;
	}

	public void setcAStatutoryAmt(Long cAStatutoryAmt) {
		this.cAStatutoryAmt = cAStatutoryAmt;
	}

	public String getcAStatutoryDate() {
		return cAStatutoryDate;
	}

	public void setcAStatutoryDate(String cAStatutoryDate) {
		this.cAStatutoryDate = cAStatutoryDate;
	}

	public String getLandcostFci() {
		return landcostFci;
	}

	public void setLandcostFci(String landcostFci) {
		this.landcostFci = landcostFci;
	}

	public String getBuildingFci() {
		return buildingFci;
	}

	public void setBuildingFci(String buildingFci) {
		this.buildingFci = buildingFci;
	}

	public String getPlantAndMachFci() {
		return plantAndMachFci;
	}

	public void setPlantAndMachFci(String plantAndMachFci) {
		this.plantAndMachFci = plantAndMachFci;
	}

	public String getFixedAssetFci() {
		return fixedAssetFci;
	}

	public void setFixedAssetFci(String fixedAssetFci) {
		this.fixedAssetFci = fixedAssetFci;
	}

	public Long getLandcostIIEPP() {
		return landcostIIEPP;
	}

	public void setLandcostIIEPP(Long landcostIIEPP) {
		this.landcostIIEPP = landcostIIEPP;
	}

	public Long getBuildingIIEPP() {
		return buildingIIEPP;
	}

	public void setBuildingIIEPP(Long buildingIIEPP) {
		this.buildingIIEPP = buildingIIEPP;
	}

	public Long getPlantAndMachIIEPP() {
		return plantAndMachIIEPP;
	}

	public void setPlantAndMachIIEPP(Long plantAndMachIIEPP) {
		this.plantAndMachIIEPP = plantAndMachIIEPP;
	}

	public Long getFixedAssetIIEPP() {
		return fixedAssetIIEPP;
	}

	public void setFixedAssetIIEPP(Long fixedAssetIIEPP) {
		this.fixedAssetIIEPP = fixedAssetIIEPP;
	}

	public Date getPropCommProdDate() {
		return propCommProdDate;
	}

	public void setPropCommProdDate(Date propCommProdDate) {
		this.propCommProdDate = propCommProdDate;
	}

	public Date getInvCommenceDate() {
		return invCommenceDate;
	}

	public void setInvCommenceDate(Date invCommenceDate) {
		this.invCommenceDate = invCommenceDate;
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

	public String getAuthorisedSignatoryName() {
		return authorisedSignatoryName;
	}

	public void setAuthorisedSignatoryName(String authorisedSignatoryName) {
		this.authorisedSignatoryName = authorisedSignatoryName;
	}

	public String getCompanyPanNo() {
		return companyPanNo;
	}

	public void setCompanyPanNo(String companyPanNo) {
		this.companyPanNo = companyPanNo;
	}

	public String getGstin() {
		return gstin;
	}

	public void setGstin(String gstin) {
		this.gstin = gstin;
	}

	public Date getCreatedDate() {
		return CreatedDate;
	}

	public void setCreatedDate(Date createdDate) {
		CreatedDate = createdDate;
	}

	public String getEciItem() {
		return eciItem;
	}

	public void setEciItem(String eciItem) {
		this.eciItem = eciItem;
	}

	public String getEciIsFci() {
		return eciIsFci;
	}

	public void setEciIsFci(String eciIsFci) {
		this.eciIsFci = eciIsFci;
	}

	public Long getEciDPRInvest() {
		return eciDPRInvest;
	}

	public void setEciDPRInvest(Long eciDPRInvest) {
		this.eciDPRInvest = eciDPRInvest;
	}

	public Long getEciIIEPPInvest() {
		return eciIIEPPInvest;
	}

	public void setEciIIEPPInvest(Long eciIIEPPInvest) {
		this.eciIIEPPInvest = eciIIEPPInvest;
	}

	public String getEciPICUP_Remarks() {
		return eciPICUP_Remarks;
	}

	public void setEciPICUP_Remarks(String eciPICUP_Remarks) {
		this.eciPICUP_Remarks = eciPICUP_Remarks;
	}

	public String getRegisteredAddr() {
		return registeredAddr;
	}

	public void setRegisteredAddr(String registeredAddr) {
		this.registeredAddr = registeredAddr;
	}

	public String getSolarCaptivePower() {
		return solarCaptivePower;
	}

	public void setSolarCaptivePower(String solarCaptivePower) {
		this.solarCaptivePower = solarCaptivePower;
	}

	public String getSolarCaptivePowerObserv() {
		return solarCaptivePowerObserv;
	}

	public void setSolarCaptivePowerObserv(String solarCaptivePowerObserv) {
		this.solarCaptivePowerObserv = solarCaptivePowerObserv;
	}

}
