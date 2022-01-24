package com.webapp.ims.model;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class AgendaReportBean {

	public String applicationId;
	public String newProject;
	public String diversification;
	public String resionName;
	public String districtName;
	public String natureOfProject;
	public String epdExisProducts;
	public String epdExisInstallCapacity;
	public String epdPropProducts;
	public String epdExisGrossBlock;
	public String epdPropoGrossBlock;
	public String invTotalProjCost;
	public String propCommProdDate;
	public String invPlantAndMachCost;
	public String invOtherCost;
	public String landcostFci;
	public String buildingFci;
	public String plantAndMachFci;
	public String fixedAssetFci;
	public String invLandCost;
	public String invBuildingCost;
	public String landcostIIEPP;
	public String buildingIIEPP;
	public String fixedAssetIIEPP;
	public String plantAndMachIIEPP;
	public String landcostRemarks;
	public String buildingRemarks;
	public String plantAndMachRemarks;
	public String fixedAssetRemarks;
	public String invGovtEquity;
	public String invIemNumber;
	public String pwApply;
	public String capInvAmt;
	public String pwPhaseNo;
	public String pwProductName;
	public String pwCapacity;
	public String pwUnit;
	public String pwFci;
	public String pwPropProductDate;
	public String authorisedSignatoryName;
	public String gstin;
	public String companyPanNo;
	public String createDate;
	public long ISFReimSCST;
	public String ISFReimFW;
	public String ISFReimBPLW;
	public String ISFTtlSGSTReim;
	public String ISFStampDutyEX;
	public String ISFEpfReim_UW;
	public String sgstRemark;
	public String scstRemark;
	public String stampDutyExemptRemark;
	public String stampDutyReimRemark;
	public String isfSgstComment;
	public String isfScstComment;
	public String isffwComment;
	public String isfBplComment;
	public String isfElecdutyComment;
	public String businessEntityName;
	public String businessAddress;
	public String invFci;
	public String invIndType;
	public int itemNumber;
	public String catIndusUndtObserv;
	public String iemRegObserv;
	public Date invCommenceDate;
	public String optcutofdateobserv;
	public String propsPlntMcnryCostObserv;
	public String dateofComProdObserv;
	public String appformatObserv;
	public String authSignatoryObserv;
	public String detailProjReportObserv;
	public String listAssetsObserv;
	public String listAssets;
	public String anexurUndertkObserv;
	public String eligblInvPerdMegaObserv;
	public String projPhasesObserv;
	public String totalDetailObserv;
	public String supprtdocObserv;
	public String docAuthorized;
	public Long numberofMaleEmployees;
	public String ISF_Claim_Reim;
	public Long ISFAmtStampDutyReim;
	public Long ISF_Cis;
	public String capIntSubRemark;
	public Long numberOfFemaleEmployees;
	public Long ISF_Infra_Int_Subsidy;
	public String infraIntSubRemark;
	public Long ISF_Loan_Subsidy;
	public String inputTaxRemark;
	public Long ISF_Tax_Credit_Reim;
	public Long ISF_Indus_Payroll_Asst;
	public String diffAbleWorkRemark;
	public String solarCaptivePower;
	public String solarCaptivePowerObserv;
	private Long ISF_Epf_Reim_UW;
	private String expansion;
	private String modify_Date;
	private String proposal;
	
	private String isfcapisComment;
	private String isfinfComment;
	private String isfloanComment;
	private String isfdisComment;
	private String isfdifferabilComment;
	

	public String getNewProject() {
		return newProject;
	}

	public void setNewProject(String newProject) {
		this.newProject = newProject;
	}

	public String getDiversification() {
		return diversification;
	}

	public void setDiversification(String diversification) {
		this.diversification = diversification;
	}

	public String getResionName() {
		return resionName;
	}

	public void setResionName(String resionName) {
		this.resionName = resionName;
	}

	public String getDistrictName() {
		return districtName;
	}

	public void setDistrictName(String districtName) {
		this.districtName = districtName;
	}

	public String getNatureOfProject() {
		return natureOfProject;
	}

	public void setNatureOfProject(String natureOfProject) {
		this.natureOfProject = natureOfProject;
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

	public String getInvTotalProjCost() {
		return invTotalProjCost;
	}

	public void setInvTotalProjCost(String invTotalProjCost) {
		this.invTotalProjCost = invTotalProjCost;
	}

	public String getPropCommProdDate() {
		return propCommProdDate;
	}

	public void setPropCommProdDate(String propCommProdDate) {
		this.propCommProdDate = propCommProdDate;
	}

	public String getInvPlantAndMachCost() {
		return invPlantAndMachCost;
	}

	public void setInvPlantAndMachCost(String invPlantAndMachCost) {
		this.invPlantAndMachCost = invPlantAndMachCost;
	}

	public String getInvOtherCost() {
		return invOtherCost;
	}

	public void setInvOtherCost(String invOtherCost) {
		this.invOtherCost = invOtherCost;
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

	public String getInvLandCost() {
		return invLandCost;
	}

	public void setInvLandCost(String invLandCost) {
		this.invLandCost = invLandCost;
	}

	public String getInvBuildingCost() {
		return invBuildingCost;
	}

	public void setInvBuildingCost(String invBuildingCost) {
		this.invBuildingCost = invBuildingCost;
	}

	public String getLandcostIIEPP() {
		return landcostIIEPP;
	}

	public void setLandcostIIEPP(String landcostIIEPP) {
		this.landcostIIEPP = landcostIIEPP;
	}

	public String getBuildingIIEPP() {
		return buildingIIEPP;
	}

	public void setBuildingIIEPP(String buildingIIEPP) {
		this.buildingIIEPP = buildingIIEPP;
	}

	public String getFixedAssetIIEPP() {
		return fixedAssetIIEPP;
	}

	public void setFixedAssetIIEPP(String fixedAssetIIEPP) {
		this.fixedAssetIIEPP = fixedAssetIIEPP;
	}

	public String getPlantAndMachIIEPP() {
		return plantAndMachIIEPP;
	}

	public void setPlantAndMachIIEPP(String plantAndMachIIEPP) {
		this.plantAndMachIIEPP = plantAndMachIIEPP;
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

	public String getInvGovtEquity() {
		return invGovtEquity;
	}

	public void setInvGovtEquity(String invGovtEquity) {
		this.invGovtEquity = invGovtEquity;
	}

	public String getInvIemNumber() {
		return invIemNumber;
	}

	public void setInvIemNumber(String invIemNumber) {
		this.invIemNumber = invIemNumber;
	}

	public String getPwApply() {
		return pwApply;
	}

	public void setPwApply(String pwApply) {
		this.pwApply = pwApply;
	}

	public String getCapInvAmt() {
		return capInvAmt;
	}

	public void setCapInvAmt(String capInvAmt) {
		this.capInvAmt = capInvAmt;
	}

	public String getPwPhaseNo() {
		return pwPhaseNo;
	}

	public void setPwPhaseNo(String pwPhaseNo) {
		this.pwPhaseNo = pwPhaseNo;
	}

	public String getPwProductName() {
		return pwProductName;
	}

	public void setPwProductName(String pwProductName) {
		this.pwProductName = pwProductName;
	}

	public String getPwCapacity() {
		return pwCapacity;
	}

	public void setPwCapacity(String pwCapacity) {
		this.pwCapacity = pwCapacity;
	}

	public String getPwUnit() {
		return pwUnit;
	}

	public void setPwUnit(String pwUnit) {
		this.pwUnit = pwUnit;
	}

	public String getPwFci() {
		return pwFci;
	}

	public void setPwFci(String pwFci) {
		this.pwFci = pwFci;
	}

	public String getPwPropProductDate() {
		return pwPropProductDate;
	}

	public void setPwPropProductDate(String pwPropProductDate) {
		this.pwPropProductDate = pwPropProductDate;
	}

	public String getAuthorisedSignatoryName() {
		return authorisedSignatoryName;
	}

	public void setAuthorisedSignatoryName(String authorisedSignatoryName) {
		this.authorisedSignatoryName = authorisedSignatoryName;
	}

	public String getGstin() {
		return gstin;
	}

	public void setGstin(String gstin) {
		this.gstin = gstin;
	}

	public String getCompanyPanNo() {
		return companyPanNo;
	}

	public void setCompanyPanNo(String companyPanNo) {
		this.companyPanNo = companyPanNo;
	}

	public String getCreateDate() {
		return createDate;
	}

	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}

	public Long getISFReimSCST() {
		return ISFReimSCST;
	}

	public void setISFReimSCST(long iSFReimSCST) {
		ISFReimSCST = iSFReimSCST;
	}

	public String getISFReimFW() {
		return ISFReimFW;
	}

	public void setISFReimFW(String iSFReimFW) {
		ISFReimFW = iSFReimFW;
	}

	public String getISFReimBPLW() {
		return ISFReimBPLW;
	}

	public void setISFReimBPLW(String iSFReimBPLW) {
		ISFReimBPLW = iSFReimBPLW;
	}

	public String getISFTtlSGSTReim() {
		return ISFTtlSGSTReim;
	}

	public void setISFTtlSGSTReim(String iSFTtlSGSTReim) {
		ISFTtlSGSTReim = iSFTtlSGSTReim;
	}

	public String getISFStampDutyEX() {
		return ISFStampDutyEX;
	}

	public void setISFStampDutyEX(String iSFStampDutyEX) {
		ISFStampDutyEX = iSFStampDutyEX;
	}

	public String getISFEpfReim_UW() {
		return ISFEpfReim_UW;
	}

	public void setISFEpfReim_UW(String iSFEpfReim_UW) {
		ISFEpfReim_UW = iSFEpfReim_UW;
	}

	public String getSgstRemark() {
		return sgstRemark;
	}

	public void setSgstRemark(String sgstRemark) {
		this.sgstRemark = sgstRemark;
	}

	public String getScstRemark() {
		return scstRemark;
	}

	public void setScstRemark(String scstRemark) {
		this.scstRemark = scstRemark;
	}

	public String getStampDutyExemptRemark() {
		return stampDutyExemptRemark;
	}

	public void setStampDutyExemptRemark(String stampDutyExemptRemark) {
		this.stampDutyExemptRemark = stampDutyExemptRemark;
	}

	public String getStampDutyReimRemark() {
		return stampDutyReimRemark;
	}

	public void setStampDutyReimRemark(String stampDutyReimRemark) {
		this.stampDutyReimRemark = stampDutyReimRemark;
	}

	public String getIsfSgstComment() {
		return isfSgstComment;
	}

	public void setIsfSgstComment(String isfSgstComment) {
		this.isfSgstComment = isfSgstComment;
	}

	public String getIsfScstComment() {
		return isfScstComment;
	}

	public void setIsfScstComment(String isfScstComment) {
		this.isfScstComment = isfScstComment;
	}

	public String getIsffwComment() {
		return isffwComment;
	}

	public void setIsffwComment(String isffwComment) {
		this.isffwComment = isffwComment;
	}

	public String getIsfBplComment() {
		return isfBplComment;
	}

	public void setIsfBplComment(String isfBplComment) {
		this.isfBplComment = isfBplComment;
	}

	public String getIsfElecdutyComment() {
		return isfElecdutyComment;
	}

	public void setIsfElecdutyComment(String isfElecdutyComment) {
		this.isfElecdutyComment = isfElecdutyComment;
	}

	public String getBusinessEntityName() {
		return businessEntityName;
	}

	public void setBusinessEntityName(String businessEntityName) {
		this.businessEntityName = businessEntityName;
	}

	public String getBusinessAddress() {
		return businessAddress;
	}

	public void setBusinessAddress(String businessAddress) {
		this.businessAddress = businessAddress;
	}

	public String getInvFci() {
		return invFci;
	}

	public void setInvFci(String invFci) {
		this.invFci = invFci;
	}

	public String getInvIndType() {
		return invIndType;
	}

	public void setInvIndType(String invIndType) {
		this.invIndType = invIndType;
	}

	public int getItemNumber() {
		return itemNumber;
	}

	public void setItemNumber(int itemNumber) {
		this.itemNumber = itemNumber;
	}

	public String getApplicationId() {
		return applicationId;
	}

	public void setApplicationId(String applicationId) {
		this.applicationId = applicationId;
	}

	public String getCatIndusUndtObserv() {
		return catIndusUndtObserv;
	}

	public void setCatIndusUndtObserv(String catIndusUndtObserv) {
		this.catIndusUndtObserv = catIndusUndtObserv;
	}

	public String getIemRegObserv() {
		return iemRegObserv;
	}

	public void setIemRegObserv(String iemRegObserv) {
		this.iemRegObserv = iemRegObserv;
	}

	public Date getInvCommenceDate() {
		return invCommenceDate;
	}

	public void setInvCommenceDate(Date invCommenceDate) {
		this.invCommenceDate = invCommenceDate;
	}

	public String getOptcutofdateobserv() {
		return optcutofdateobserv;
	}

	public void setOptcutofdateobserv(String optcutofdateobserv) {
		this.optcutofdateobserv = optcutofdateobserv;
	}

	public String getPropsPlntMcnryCostObserv() {
		return propsPlntMcnryCostObserv;
	}

	public void setPropsPlntMcnryCostObserv(String propsPlntMcnryCostObserv) {
		this.propsPlntMcnryCostObserv = propsPlntMcnryCostObserv;
	}

	public String getDateofComProdObserv() {
		return dateofComProdObserv;
	}

	public void setDateofComProdObserv(String dateofComProdObserv) {
		this.dateofComProdObserv = dateofComProdObserv;
	}

	public String getAppformatObserv() {
		return appformatObserv;
	}

	public void setAppformatObserv(String appformatObserv) {
		this.appformatObserv = appformatObserv;
	}

	public String getAuthSignatoryObserv() {
		return authSignatoryObserv;
	}

	public void setAuthSignatoryObserv(String authSignatoryObserv) {
		this.authSignatoryObserv = authSignatoryObserv;
	}

	public String getDetailProjReportObserv() {
		return detailProjReportObserv;
	}

	public void setDetailProjReportObserv(String detailProjReportObserv) {
		this.detailProjReportObserv = detailProjReportObserv;
	}

	public String getListAssetsObserv() {
		return listAssetsObserv;
	}

	public void setListAssetsObserv(String listAssetsObserv) {
		this.listAssetsObserv = listAssetsObserv;
	}

	public String getListAssets() {
		return listAssets;
	}

	public void setListAssets(String listAssets) {
		this.listAssets = listAssets;
	}

	public String getAnexurUndertkObserv() {
		return anexurUndertkObserv;
	}

	public void setAnexurUndertkObserv(String anexurUndertkObserv) {
		this.anexurUndertkObserv = anexurUndertkObserv;
	}

	public String getEligblInvPerdMegaObserv() {
		return eligblInvPerdMegaObserv;
	}

	public void setEligblInvPerdMegaObserv(String eligblInvPerdMegaObserv) {
		this.eligblInvPerdMegaObserv = eligblInvPerdMegaObserv;
	}

	public String getProjPhasesObserv() {
		return projPhasesObserv;
	}

	public void setProjPhasesObserv(String projPhasesObserv) {
		this.projPhasesObserv = projPhasesObserv;
	}

	public String getTotalDetailObserv() {
		return totalDetailObserv;
	}

	public void setTotalDetailObserv(String totalDetailObserv) {
		this.totalDetailObserv = totalDetailObserv;
	}

	public Long getNumberofMaleEmployees() {
		return numberofMaleEmployees;
	}

	public void setNumberofMaleEmployees(Long numberofMaleEmployees) {
		this.numberofMaleEmployees = numberofMaleEmployees;
	}

	public Long getNumberOfFemaleEmployees() {
		return numberOfFemaleEmployees;
	}

	public void setNumberOfFemaleEmployees(Long numberOfFemaleEmployees) {
		this.numberOfFemaleEmployees = numberOfFemaleEmployees;
	}

	public String getSupprtdocObserv() {
		return supprtdocObserv;
	}

	public void setSupprtdocObserv(String supprtdocObserv) {
		this.supprtdocObserv = supprtdocObserv;
	}

	public String getDocAuthorized() {
		return docAuthorized;
	}

	public void setDocAuthorized(String docAuthorized) {
		this.docAuthorized = docAuthorized;
	}

	public String getISF_Claim_Reim() {
		return ISF_Claim_Reim;
	}

	public void setISF_Claim_Reim(String iSF_Claim_Reim) {
		ISF_Claim_Reim = iSF_Claim_Reim;
	}

	public Long getISFAmtStampDutyReim() {
		return ISFAmtStampDutyReim;
	}

	public void setISFAmtStampDutyReim(Long iSFAmtStampDutyReim) {
		ISFAmtStampDutyReim = iSFAmtStampDutyReim;
	}

	public Long getISF_Cis() {
		return ISF_Cis;
	}

	public void setISF_Cis(Long iSF_Cis) {
		ISF_Cis = iSF_Cis;
	}

	public String getCapIntSubRemark() {
		return capIntSubRemark;
	}

	public void setCapIntSubRemark(String capIntSubRemark) {
		this.capIntSubRemark = capIntSubRemark;
	}

	public Long getISF_Infra_Int_Subsidy() {
		return ISF_Infra_Int_Subsidy;
	}

	public void setISF_Infra_Int_Subsidy(Long iSF_Infra_Int_Subsidy) {
		ISF_Infra_Int_Subsidy = iSF_Infra_Int_Subsidy;
	}

	public String getInfraIntSubRemark() {
		return infraIntSubRemark;
	}

	public void setInfraIntSubRemark(String infraIntSubRemark) {
		this.infraIntSubRemark = infraIntSubRemark;
	}

	public Long getISF_Loan_Subsidy() {
		return ISF_Loan_Subsidy;
	}

	public void setISF_Loan_Subsidy(Long iSF_Loan_Subsidy) {
		ISF_Loan_Subsidy = iSF_Loan_Subsidy;
	}

	public String getInputTaxRemark() {
		return inputTaxRemark;
	}

	public void setInputTaxRemark(String inputTaxRemark) {
		this.inputTaxRemark = inputTaxRemark;
	}

	public Long getISF_Tax_Credit_Reim() {
		return ISF_Tax_Credit_Reim;
	}

	public void setISF_Tax_Credit_Reim(Long iSF_Tax_Credit_Reim) {
		ISF_Tax_Credit_Reim = iSF_Tax_Credit_Reim;
	}

	public Long getISF_Indus_Payroll_Asst() {
		return ISF_Indus_Payroll_Asst;
	}

	public void setISF_Indus_Payroll_Asst(Long iSF_Indus_Payroll_Asst) {
		ISF_Indus_Payroll_Asst = iSF_Indus_Payroll_Asst;
	}

	public String getDiffAbleWorkRemark() {
		return diffAbleWorkRemark;
	}

	public void setDiffAbleWorkRemark(String diffAbleWorkRemark) {
		this.diffAbleWorkRemark = diffAbleWorkRemark;
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

	public Long getISF_Epf_Reim_UW() {
		return ISF_Epf_Reim_UW;
	}

	public void setISF_Epf_Reim_UW(Long iSF_Epf_Reim_UW) {
		ISF_Epf_Reim_UW = iSF_Epf_Reim_UW;
	}

	public String getExpansion() {
		return expansion;
	}

	public void setExpansion(String expansion) {
		this.expansion = expansion;
	}

	public String getModify_Date() {
		return modify_Date;
	}

	public void setModify_Date(String modify_Date) {
		this.modify_Date = modify_Date;
	}
	
	public String getProposal() {
		return proposal;
	}

	public void setProposal(String Proposal) {
		this.proposal = Proposal;
	}
	
	
	//Modified by Mohd Alam
	public String getIsfcapisComment() {
		return isfcapisComment;
	}

	public void setIsfcapisComment(String isfcapisComment) {
		this.isfcapisComment = isfcapisComment;
	}
	
	public String getisfinfComment() {
		return isfinfComment;
	}

	public void setisfinfComment(String isfinfComment) {
		this.isfinfComment = isfinfComment;
	}
	
	public String getIsfloanComment() {
		return isfloanComment;
	}

	public void setIsfloanComment(String isfloanComment) {
		this.isfloanComment = isfloanComment;
	}
	
	public String getIsfdisComment() {
		return isfdisComment;
	}

	public void setIsfdisComment(String isfdisComment) {
		this.isfdisComment = isfdisComment;
	}
	
	public String getIsfdifferabilComment() {
		return isfdifferabilComment;
	}

	public void setIsfdifferabilComment(String isfdifferabilComment) {
		this.isfdifferabilComment = isfdifferabilComment;
	}

}
