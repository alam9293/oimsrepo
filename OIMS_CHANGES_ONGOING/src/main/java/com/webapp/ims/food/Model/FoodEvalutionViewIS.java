/**
 * Author:: Pankaj Sahu
* Created date::06/05O/2021
 */

package com.webapp.ims.food.Model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "foodisevalution")
public class FoodEvalutionViewIS implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "app_id")
	private String app_id;

	@Column(name = "unit_id")
	private String unitid;

	@Column(name = "control_id")
	private String control_id;

	@Column(name = "created_by")
	private String createdBy;

	@Column(name = "modified_by")
	private String modifiedBy;

	@Column(name = "status")
	private String status;

	@Column(name = "name_of_organization")
	private String nameOfOrganization;

	@Column(name = "registered_address")
	private String registeredAddress;

	@Column(name = "project_name")
	private String projectName;

	@Column(name = "proposed_site_address")
	private String proposedSiteAddress;

	@Column(name = "capacity")
	private String capacity;

	// PROJECT PROFILE

	@Column(name = "project_profile_app_id")
	private String projectProfileAppId;

	@Column(name = "project_profile_app_id_observ")
	private String projectProfileAppIdObserv;

	@Column(name = "proposal_submission_date")
	private String proposalSubmissionDate;

	@Column(name = "proposal_submission_date_observ")
	private String proposalSubmissionDateObserv;

	@Column(name = "name_of_promoter")
	private String nameOfPromoter;

	@Column(name = "name_of_promoter_observ")
	private String nameOfPromoterObserv;

	@Column(name = "type_of_organization")
	private String typeOfOrganization;

	@Column(name = "type_of_organization_observ")
	private String typeOfOrganizationObserv;

	@Column(name = "date_of_incorporation")
	private String dateOfIncorporation;

	@Column(name = "date_of_incorporation_observ")
	private String dateOfIncorporationObserv;

	@Column(name = "avail_of_land_and_building")
	private String availOfLandAndBuilding;

	@Column(name = "avail_of_land_and_building_observ")
	private String availOfLandAndBuildingObserv;

	@Column(name = "pan_no")
	private String panNo;

	@Column(name = "pan_no_observ")
	private String panNoObserv;

	@Column(name = "gst_no")
	private String gstNo;

	@Column(name = "gst_no_observ")
	private String gstNoObserv;

	@Column(name = "udyog_aadhar_no")
	private String udyogAadharNo;

	@Column(name = "udyog_aadhar_no_observ")
	private String udyogAadharNoObservation;

	@Column(name = "location_of_firm")
	private String locationOfFirm;

	@Column(name = "location_of_firm_observ")
	private String locationOfFirmObservation;

	@Column(name = "sector")
	private String sector;

	@Column(name = "sector_observ")
	private String sectorObservation;

	@Column(name = "sub_sector")
	private String subSector;

	@Column(name = "sub_sector_observ")
	private String subSectorObservation;

	// Proposed Products by Products

	@Column(name = "proposed_capacity_buy_products")
	private String proposedCapacityBuyProducts;

	@Column(name = "proposed_capacity_buy_products_observ")
	private String proposedCapacityBuyProductsObserv;

	@Column(name = "exist_capacity_buy_products")
	private String existingCapacityBuyProducts;

	@Column(name = "exist_capacity_buy_products_observ")
	private String existingCapacityBuyProductsObserv;

	@Column(name = "capacity_utilize_buy_products")
	private String capacityUtilizationBuyProducts;

	@Column(name = "capacity_utilize_buy_products_observ")
	private String capacityUtilizationBuyProductsObserv;

	@Column(name = "name_of_appraising_bank_buy_products")
	private String nameOfAppraisingBankBuyProducts;

	@Column(name = "name_of_appraising_bank_buy_products_observ")
	private String nameOfAppraisingBankBuyProductsObserv;

	@Column(name = "amt_of_termloan_sanctioned_buy_products")
	private String amtOfTermloanSanctionedBuyProducts;

	@Column(name = "amt_of_termloan_sanctioned_buy_products_observ")
	private String amtOfTermloanSanctionedBuyProductsObservs;

	@Column(name = "date_of_term_loan_sanctioned_buy_products")
	private String dateOfTermLoanSanctionedBuyProducts;

	@Column(name = "date_of_term_loan_sanctioned_buy_products_observ")
	private String dateOfTermLoanSanctionedBuyProductsObserv;

	@Column(name = "project_summary_observ")
	private String projectSummaryObserv;

	// COST OF PROJECT (IN LACS)

	@Column(name = "land_exist_cost")
	private String landExistCost;

	@Column(name = "land_proposed_cost")
	private String landProposedCost;

	@Column(name = "land_appraised_cost")
	private String landAppraisedCost;

	@Column(name = "land_remarks")
	private String landRemarks;

	@Column(name = "factory_build_exist_cost")
	private String factoryBuildExistCost;

	@Column(name = "factory_build_proposed_cost")
	private String factoryBuildProposedCost;

	@Column(name = "factory_build_apraised_cost")
	private String factoryBuildAppraisedCost;

	@Column(name = "factory_build_remarks")
	private String factoryBuildRemarks;

	@Column(name = "plant_mach_exist_cost")
	private String plantMachExistCost;

	@Column(name = "plant_mach_proposed_cost")
	private String plantMachProposedCost;

	@Column(name = "plant_mach_appraised_cost")
	private String plantMachAppraisedCost;

	@Column(name = "plant_mach_build_remarks")
	private String plantMachRemarks;

	@Column(name = "misc_fixed_assets_exist_cost")
	private String miscFixedAssetsExistCost;

	@Column(name = "misc_fixed_assets_proposed_cost")
	private String miscFixedAssetsProposedCost;

	@Column(name = "misc_fixed_assets_appraised_cost")
	private String miscFixedAssetsAppraisedCost;

	@Column(name = "misc_fixed_assets_build_remarks")
	private String miscFixedAssetsRemarks;

	@Column(name = "contingences_exist_cost")
	private String contingencesExistCost;

	@Column(name = "contingences_proposed_cost")
	private String contingencesProposedCost;

	@Column(name = "contingences_appraised_cost")
	private String contingencesAppraisedCost;

	@Column(name = "contingences_remarks")
	private String contingencesRemarks;

	@Column(name = "pre_operative_expenses_exist_cost")
	private String preOperativeExpensesExistCost;

	@Column(name = "pre_operative_expenses_proposed_cost")
	private String preOperativeExpensesProposedCost;

	@Column(name = "pre_perative_expenses_appraised_cost")
	private String preOperativeExpensesAppraisedCost;

	@Column(name = "pre_operative_expenses_remarks")
	private String preOperativeExpensesRemarks;

	@Column(name = "int_during_construct_exist_cost")
	private String intDuringConstructExistCost;

	@Column(name = "int_during_construct_proposed_cost")
	private String intDuringConstructProposedCost;

	@Column(name = "int_during_construct_appraised_cost")
	private String intDuringConstructAppraisedCost;

	@Column(name = "int_during_construct_remarks")
	private String intDuringConstructRemarks;

	@Column(name = "margin_money_exist_cost")
	private String marginMoneyExistCost;

	@Column(name = "margin_money_proposed_cost")
	private String marginMoneyProposedCost;

	@Column(name = "margin_money_appraised_cost")
	private String marginMoneyAppraisedCost;

	@Column(name = "margin_money_remarks")
	private String marginMoneyRemarks;

	@Column(name = "total_exist_cost")
	private String totalExistCost;

	@Column(name = "total_proposed_cost")
	private String totalProposedCost;

	@Column(name = "total_appraised_cost")
	private String totalAppraisedCost;

	@Column(name = "total_remarks")
	private String totalRemarks;

	// MEANS OF FINANCE (IN LACS)

	@Column(name = "equity_exist_cost")
	private String equityExistCost;

	@Column(name = "equity_proposed_cost")
	private String equityProposedCost;

	@Column(name = "equity_appraised_cost")
	private String equityAppraisedCost;

	@Column(name = "equity_remarks")
	private String equityRemarks;

	@Column(name = "grant_exist_cost")
	private String grantExistCost;

	@Column(name = "grant_proposed_cost")
	private String grantProposedCost;

	@Column(name = "grant_appraised_cost")
	private String grantAppraisedCost;

	@Column(name = "grant_remarks")
	private String grantRemarks;

	@Column(name = "term_loan_exist_cost")
	private String termLoanExistCost;

	@Column(name = "term_loan_proposed_cost")
	private String termLoanProposedCost;

	@Column(name = "term_loan_appraised_cost")
	private String termLoanAppraisedCost;

	@Column(name = "term_loan_remarks")
	private String termLoanRemarks;

	@Column(name = "working_capital_exist_cost")
	private String workingCapitalExistCost;

	@Column(name = "working_capital_proposed_cost")
	private String workingCapitalProposedCost;

	@Column(name = "working_capital_appraised_cost")
	private String workingCapitalAppraisedCost;

	@Column(name = "working_capital_remarks")
	private String workingCapitalRemarks;
	
	@Column(name = "mof_other_exit_cost")
	private String mofOtherExitCost;
	
	@Column(name = "mof_other_proposed_cost")
	private String mofOtherProposedCost;
	
	@Column(name = "mof_other_appraised_cost")
	private String mofOtherAppraisedCost;
	
	@Column(name = "mof_other_remarks")
	private String mofOtherRemarks;

	@Column(name = "total_mof_exist_cost")
	private String totalMofExistCost;

	@Column(name = "total_mof_proposed_cost")
	private String totalMofProposedCost;

	@Column(name = "total_mof_appraised_cost")
	private String totalMofAppraisedCost;

	@Column(name = "total_mof_remarks")
	private String totalMofRemarks;

	// IMPLEMENTATION SCHEDULE:

	@Column(name = "acquisition_land_date")
	private String acquisitionLandDate;

	@Column(name = "acquisition_land_remark")
	private String acquisitionLandRemark;

	@Column(name = "start_construction_of_build_date")
	private String startConstructionOfBuildDate;

	@Column(name = "start_construction_of_build_remark")
	private String startOfConstructionOfBuildRemark;

	@Column(name = "completion_of_build_date")
	private String completionOfBuildDate;

	@Column(name = "completion_of_build_remark")
	private String completionOfBuildRemark;

	@Column(name = "placing_order_plant_mach_date")
	private String placingOrderPlantMachDate;

	@Column(name = "placing_order_plant_mach_remark")
	private String placingOrderPlantMachRemark;

	@Column(name = "installation_date")
	private String installationDate;

	@Column(name = "installation_remark")
	private String installationRemark;

	@Column(name = "trial_production_date")
	private String trialProductionDate;

	@Column(name = "trial_production_remark")
	private String trialProductionRemark;

	@Column(name = "commercial_production_date")
	private String commercialProductionDate;

	@Column(name = "commercial_production_remark")
	private String commercialProductionRemark;

	// STATUS OF DOCUMENTS

	@Column(name = "app_on_prescribed_format_desc")
	private String appOnPrescribedFormatDesc;
	@Column(name = "app_on_prescribed_format_remark")
	private String appOnPrescribedFormatRemark;

	@Column(name = "detailed_project_report_desc")
	private String detailedProjectReportDesc;
	@Column(name = "detailed_project_report_remark")
	private String detailedProjectReportRemark;

	@Column(name = "sanction_letter_term_loan_desc")
	private String sanctionLetterTermLoanDesc;
	@Column(name = "sanction_letter_term_loan_remark")
	private String sanctionLetterTermLoanRemark;

	@Column(name = "bank_appraisal_report_desc")
	private String bankAppraisalReportDesc;
	@Column(name = "bank_appraisal_report_remark")
	private String bankAppraisalReportRemark;

	@Column(name = "ce_civil_certification_for_a7_desc")
	private String ceCivilCertificationForA7Desc;
	@Column(name = "ce_civil_certification_for_a7_remark")
	private String ceCivilCertificationForA7Remark;

	@Column(name = "ce_mechanical_certification_for_a8_desc")
	private String ceMechanicalCertificationForA8Desc;
	@Column(name = "ce_mechanical_certification_for_a8_remark")
	private String ceMechanicalCertificationForA8Remark;

	@Column(name = "building_plan_of_factory_desc")
	private String buildingPlanOfFactoryDesc;
	@Column(name = "building_plan_of_factory_remark")
	private String buildingPlanOfFactoryRemark;

	@Column(name = "land_document_desc")
	private String landDocumentDesc;
	@Column(name = "land_document_remark")
	private String landDocumentRemark;

	@Column(name = "affidavit_prescribed_format_desc")
	private String affidavitPrescribedFormatDesc;
	@Column(name = "affidavit_prescribed_format_remark")
	private String affidavitPrescribedFormatRemark;
	
	@Column(name="incorporation_certificate_of_firm_desc")
	private String incorporationCertificateOfFirmDesc;
	@Column(name="incorporation_certificate_of_firm_remark")
	private String incorporationCertificateOfFirmRemark;
	
	@Column(name="partnership_deed_desc")
	private String partnershipDeedDesc;
	@Column(name="partnership_deed_remark")
	private String partnershipDeedRemark;
	
	@Column(name = "a13_bank_certi_for_int_liability_desc")
	private String a13BankCertiForIntLiabilityDesc;
	@Column(name = "a13_bank_certi_for_int_liability_remark")
	private String a13BankCertiForIntLiabilityRemark;
	
	
	@Column(name="a18_certi_for_incre_in_gross_block_value_desc")
	private String a18certiForIncreInGrossBlockValueDesc;
	@Column(name="a18_certi_for_incre_in_gross_block_value_remark")
	private String a18certiForIncreaseInGrossBlockValueRemark;

	
	
	
	

	// ELIGIBLE COST OF PLANT MACHINERY-
	// as transient
	@Column(name = "Eligible_Cost_Name_Of_PlantMach")
	private transient String eligibleCostNameOfPlantMach;

	@Column(name = "Eligible_Cost_Name_Of_Supplier_Company")
	private transient String eligibleCostNameOfSupplierCompany;

	@Column(name = "eligible_cost_basic_price")
	private transient String eligibleCostBasicPrice;

	// as transient
	@Column(name = "total_eligible_cost_in_lacs")
	private String totaleEligibleCostInLacs;

	@Column(name = "eligible_cost_remarks")
	private String eligibleCostRemarks;

	// ELIGIBLE TECHNICAL CIVIL WORK-
//as transiant
	@Column(name = "Eligible_Tech_Civil_Work_Particular")
	private transient String eligibleTechCivilWorkParticular;

	@Column(name = "Eligible_Tech_Civil_Work_Area")
	private transient String eligibleTechCivilWorkArea;

	@Column(name = "Eligible_Tech_Civil_Work_Rate")
	private transient String eligibleTechCivilWorkRate;

	@Column(name = "Eligible_Tech_Civil_Work_Amount")
	private transient String eligibleTechCivilWorkAmount;

	@Column(name = "Eligible_Tech_Civil_Work_Eligible_Cost")
	private transient String[] eligibleTechCivilWorkEligibleCost;
//as transiant
	@Column(name = "ttl_eligible_tech_civil_work_eligible_cost")
	private String ttlEligibleTechCivilWorkEligibleCost;

	@Column(name = "eligible_tech_civil_work_remarks")
	private String eligibleTechCivilWorkRemarks;

	// ELIGIBLE GRANT AMOUNT:

	@Column(name = "grant_amt_eligiblle_cost_of_plant_mach")
	private String grantAmtEligiblleCostOfPlantMach;

	@Column(name = "grant_amt_eligible_cost_of_technical_civil_work")
	private String grantAmtEligibleCostOfTechnicalCivilWork;

	@Column(name = "grant_amt_eligible_project_cost")
	private String grantAmtEligibleProjectCost;

	@Column(name = "grant_amt_eligible_pm")
	private String grantAmtEligiblePM;

	@Column(name = "grant_amt_max_eligible_grant")
	private String grantAmtMaxEligibleGrant;

	@Column(name = "grant_amt_remarks")
	private String grantAmtRemarks;

	@Column(name = "interest_subsidy")
	private String interestSubsidy;

	@Column(name = "recommendation_comments")
	private String recommendationComments;

	public String getUnitid() {
		return unitid;
	}

	public void setUnitid(String unitid) {
		this.unitid = unitid;
	}

	public String getControl_id() {
		return control_id;
	}

	public void setControl_id(String control_id) {
		this.control_id = control_id;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public String getModifiedBy() {
		return modifiedBy;
	}

	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getNameOfOrganization() {
		return nameOfOrganization;
	}

	public void setNameOfOrganization(String nameOfOrganization) {
		this.nameOfOrganization = nameOfOrganization;
	}

	public String getRegisteredAddress() {
		return registeredAddress;
	}

	public void setRegisteredAddress(String registeredAddress) {
		this.registeredAddress = registeredAddress;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public String getProposedSiteAddress() {
		return proposedSiteAddress;
	}

	public void setProposedSiteAddress(String proposedSiteAddress) {
		this.proposedSiteAddress = proposedSiteAddress;
	}

	public String getCapacity() {
		return capacity;
	}

	public void setCapacity(String capacity) {
		this.capacity = capacity;
	}

	public String getProjectProfileAppId() {
		return projectProfileAppId;
	}

	public void setProjectProfileAppId(String projectProfileAppId) {
		this.projectProfileAppId = projectProfileAppId;
	}

	public String getProjectProfileAppIdObserv() {
		return projectProfileAppIdObserv;
	}

	public void setProjectProfileAppIdObserv(String projectProfileAppIdObserv) {
		this.projectProfileAppIdObserv = projectProfileAppIdObserv;
	}

	public String getApp_id() {
		return app_id;
	}

	public void setApp_id(String app_id) {
		this.app_id = app_id;
	}

	public String getProposalSubmissionDateObserv() {
		return proposalSubmissionDateObserv;
	}

	public void setProposalSubmissionDateObserv(String proposalSubmissionDateObserv) {
		this.proposalSubmissionDateObserv = proposalSubmissionDateObserv;
	}

	public String getNameOfPromoter() {
		return nameOfPromoter;
	}

	public void setNameOfPromoter(String nameOfPromoter) {
		this.nameOfPromoter = nameOfPromoter;
	}

	public String getNameOfPromoterObserv() {
		return nameOfPromoterObserv;
	}

	public void setNameOfPromoterObserv(String nameOfPromoterObserv) {
		this.nameOfPromoterObserv = nameOfPromoterObserv;
	}

	public String getTypeOfOrganization() {
		return typeOfOrganization;
	}

	public void setTypeOfOrganization(String typeOfOrganization) {
		this.typeOfOrganization = typeOfOrganization;
	}

	public String getTypeOfOrganizationObserv() {
		return typeOfOrganizationObserv;
	}

	public void setTypeOfOrganizationObserv(String typeOfOrganizationObserv) {
		this.typeOfOrganizationObserv = typeOfOrganizationObserv;
	}

	public String getDateOfIncorporation() {
		return dateOfIncorporation;
	}

	public void setDateOfIncorporation(String dateOfIncorporation) {
		this.dateOfIncorporation = dateOfIncorporation;
	}

	public String getDateOfIncorporationObserv() {
		return dateOfIncorporationObserv;
	}

	public void setDateOfIncorporationObserv(String dateOfIncorporationObserv) {
		this.dateOfIncorporationObserv = dateOfIncorporationObserv;
	}

	public String getAvailOfLandAndBuildingObserv() {
		return availOfLandAndBuildingObserv;
	}

	public void setAvailOfLandAndBuildingObserv(String availOfLandAndBuildingObserv) {
		this.availOfLandAndBuildingObserv = availOfLandAndBuildingObserv;
	}

	public String getPanNo() {
		return panNo;
	}

	public void setPanNo(String panNo) {
		this.panNo = panNo;
	}

	public String getPanNoObserv() {
		return panNoObserv;
	}

	public void setPanNoObserv(String panNoObserv) {
		this.panNoObserv = panNoObserv;
	}

	public String getGstNo() {
		return gstNo;
	}

	public void setGstNo(String gstNo) {
		this.gstNo = gstNo;
	}

	public String getGstNoObserv() {
		return gstNoObserv;
	}

	public void setGstNoObserv(String gstNoObserv) {
		this.gstNoObserv = gstNoObserv;
	}

	public String getUdyogAadharNo() {
		return udyogAadharNo;
	}

	public void setUdyogAadharNo(String udyogAadharNo) {
		this.udyogAadharNo = udyogAadharNo;
	}

	public String getUdyogAadharNoObservation() {
		return udyogAadharNoObservation;
	}

	public void setUdyogAadharNoObservation(String udyogAadharNoObservation) {
		this.udyogAadharNoObservation = udyogAadharNoObservation;
	}

	public String getLocationOfFirm() {
		return locationOfFirm;
	}

	public void setLocationOfFirm(String locationOfFirm) {
		this.locationOfFirm = locationOfFirm;
	}

	public String getLocationOfFirmObservation() {
		return locationOfFirmObservation;
	}

	public void setLocationOfFirmObservation(String locationOfFirmObservation) {
		this.locationOfFirmObservation = locationOfFirmObservation;
	}

	public String getSector() {
		return sector;
	}

	public void setSector(String sector) {
		this.sector = sector;
	}

	public String getSectorObservation() {
		return sectorObservation;
	}

	public void setSectorObservation(String sectorObservation) {
		this.sectorObservation = sectorObservation;
	}

	public String getSubSector() {
		return subSector;
	}

	public void setSubSector(String subSector) {
		this.subSector = subSector;
	}

	public String getSubSectorObservation() {
		return subSectorObservation;
	}

	public void setSubSectorObservation(String subSectorObservation) {
		this.subSectorObservation = subSectorObservation;
	}

	public String getProposedCapacityBuyProducts() {
		return proposedCapacityBuyProducts;
	}

	public void setProposedCapacityBuyProducts(String proposedCapacityBuyProducts) {
		this.proposedCapacityBuyProducts = proposedCapacityBuyProducts;
	}

	public String getProposedCapacityBuyProductsObserv() {
		return proposedCapacityBuyProductsObserv;
	}

	public void setProposedCapacityBuyProductsObserv(String proposedCapacityBuyProductsObserv) {
		this.proposedCapacityBuyProductsObserv = proposedCapacityBuyProductsObserv;
	}

	public String getExistingCapacityBuyProducts() {
		return existingCapacityBuyProducts;
	}

	public void setExistingCapacityBuyProducts(String existingCapacityBuyProducts) {
		this.existingCapacityBuyProducts = existingCapacityBuyProducts;
	}

	public String getExistingCapacityBuyProductsObserv() {
		return existingCapacityBuyProductsObserv;
	}

	public void setExistingCapacityBuyProductsObserv(String existingCapacityBuyProductsObserv) {
		this.existingCapacityBuyProductsObserv = existingCapacityBuyProductsObserv;
	}

	public String getCapacityUtilizationBuyProductsObserv() {
		return capacityUtilizationBuyProductsObserv;
	}

	public void setCapacityUtilizationBuyProductsObserv(String capacityUtilizationBuyProductsObserv) {
		this.capacityUtilizationBuyProductsObserv = capacityUtilizationBuyProductsObserv;
	}

	public String getNameOfAppraisingBankBuyProducts() {
		return nameOfAppraisingBankBuyProducts;
	}

	public void setNameOfAppraisingBankBuyProducts(String nameOfAppraisingBankBuyProducts) {
		this.nameOfAppraisingBankBuyProducts = nameOfAppraisingBankBuyProducts;
	}

	public String getNameOfAppraisingBankBuyProductsObserv() {
		return nameOfAppraisingBankBuyProductsObserv;
	}

	public void setNameOfAppraisingBankBuyProductsObserv(String nameOfAppraisingBankBuyProductsObserv) {
		this.nameOfAppraisingBankBuyProductsObserv = nameOfAppraisingBankBuyProductsObserv;
	}

	public String getMiscFixedAssetsAppraisedCost() {
		return miscFixedAssetsAppraisedCost;
	}

	public void setMiscFixedAssetsAppraisedCost(String miscFixedAssetsAppraisedCost) {
		this.miscFixedAssetsAppraisedCost = miscFixedAssetsAppraisedCost;
	}

	public String getDateOfTermLoanSanctionedBuyProducts() {
		return dateOfTermLoanSanctionedBuyProducts;
	}

	public void setDateOfTermLoanSanctionedBuyProducts(String dateOfTermLoanSanctionedBuyProducts) {
		this.dateOfTermLoanSanctionedBuyProducts = dateOfTermLoanSanctionedBuyProducts;
	}

	public String getDateOfTermLoanSanctionedBuyProductsObserv() {
		return dateOfTermLoanSanctionedBuyProductsObserv;
	}

	public void setDateOfTermLoanSanctionedBuyProductsObserv(String dateOfTermLoanSanctionedBuyProductsObserv) {
		this.dateOfTermLoanSanctionedBuyProductsObserv = dateOfTermLoanSanctionedBuyProductsObserv;
	}

	public String getProjectSummaryObserv() {
		return projectSummaryObserv;
	}

	public void setProjectSummaryObserv(String projectSummaryObserv) {
		this.projectSummaryObserv = projectSummaryObserv;
	}

	public String getLandExistCost() {
		return landExistCost;
	}

	public void setLandExistCost(String landExistCost) {
		this.landExistCost = landExistCost;
	}

	public String getLandProposedCost() {
		return landProposedCost;
	}

	public void setLandProposedCost(String landProposedCost) {
		this.landProposedCost = landProposedCost;
	}

	public String getLandAppraisedCost() {
		return landAppraisedCost;
	}

	public void setLandAppraisedCost(String landAppraisedCost) {
		this.landAppraisedCost = landAppraisedCost;
	}

	public String getLandRemarks() {
		return landRemarks;
	}

	public void setLandRemarks(String landRemarks) {
		this.landRemarks = landRemarks;
	}

	public String getFactoryBuildExistCost() {
		return factoryBuildExistCost;
	}

	public void setFactoryBuildExistCost(String factoryBuildExistCost) {
		this.factoryBuildExistCost = factoryBuildExistCost;
	}

	public String getFactoryBuildProposedCost() {
		return factoryBuildProposedCost;
	}

	public void setFactoryBuildProposedCost(String factoryBuildProposedCost) {
		this.factoryBuildProposedCost = factoryBuildProposedCost;
	}

	public String getFactoryBuildAppraisedCost() {
		return factoryBuildAppraisedCost;
	}

	public void setFactoryBuildAppraisedCost(String factoryBuildAppraisedCost) {
		this.factoryBuildAppraisedCost = factoryBuildAppraisedCost;
	}

	public String getFactoryBuildRemarks() {
		return factoryBuildRemarks;
	}

	public void setFactoryBuildRemarks(String factoryBuildRemarks) {
		this.factoryBuildRemarks = factoryBuildRemarks;
	}

	public String getPlantMachExistCost() {
		return plantMachExistCost;
	}

	public void setPlantMachExistCost(String plantMachExistCost) {
		this.plantMachExistCost = plantMachExistCost;
	}

	public String getPlantMachProposedCost() {
		return plantMachProposedCost;
	}

	public void setPlantMachProposedCost(String plantMachProposedCost) {
		this.plantMachProposedCost = plantMachProposedCost;
	}

	public String getPlantMachAppraisedCost() {
		return plantMachAppraisedCost;
	}

	public void setPlantMachAppraisedCost(String plantMachAppraisedCost) {
		this.plantMachAppraisedCost = plantMachAppraisedCost;
	}

	public String getMiscFixedAssetsExistCost() {
		return miscFixedAssetsExistCost;
	}

	public void setMiscFixedAssetsExistCost(String miscFixedAssetsExistCost) {
		this.miscFixedAssetsExistCost = miscFixedAssetsExistCost;
	}

	public String getMiscFixedAssetsProposedCost() {
		return miscFixedAssetsProposedCost;
	}

	public void setMiscFixedAssetsProposedCost(String miscFixedAssetsProposedCost) {
		this.miscFixedAssetsProposedCost = miscFixedAssetsProposedCost;
	}

	public String getContingencesExistCost() {
		return contingencesExistCost;
	}

	public void setContingencesExistCost(String contingencesExistCost) {
		this.contingencesExistCost = contingencesExistCost;
	}

	public String getContingencesProposedCost() {
		return contingencesProposedCost;
	}

	public void setContingencesProposedCost(String contingencesProposedCost) {
		this.contingencesProposedCost = contingencesProposedCost;
	}

	public String getContingencesAppraisedCost() {
		return contingencesAppraisedCost;
	}

	public void setContingencesAppraisedCost(String contingencesAppraisedCost) {
		this.contingencesAppraisedCost = contingencesAppraisedCost;
	}

	public String getContingencesRemarks() {
		return contingencesRemarks;
	}

	public void setContingencesRemarks(String contingencesRemarks) {
		this.contingencesRemarks = contingencesRemarks;
	}

	public String getPreOperativeExpensesExistCost() {
		return preOperativeExpensesExistCost;
	}

	public void setPreOperativeExpensesExistCost(String preOperativeExpensesExistCost) {
		this.preOperativeExpensesExistCost = preOperativeExpensesExistCost;
	}

	public String getPreOperativeExpensesProposedCost() {
		return preOperativeExpensesProposedCost;
	}

	public void setPreOperativeExpensesProposedCost(String preOperativeExpensesProposedCost) {
		this.preOperativeExpensesProposedCost = preOperativeExpensesProposedCost;
	}

	public String getPreOperativeExpensesAppraisedCost() {
		return preOperativeExpensesAppraisedCost;
	}

	public void setPreOperativeExpensesAppraisedCost(String preOperativeExpensesAppraisedCost) {
		this.preOperativeExpensesAppraisedCost = preOperativeExpensesAppraisedCost;
	}

	public String getPreOperativeExpensesRemarks() {
		return preOperativeExpensesRemarks;
	}

	public void setPreOperativeExpensesRemarks(String preOperativeExpensesRemarks) {
		this.preOperativeExpensesRemarks = preOperativeExpensesRemarks;
	}

	public String getIntDuringConstructExistCost() {
		return intDuringConstructExistCost;
	}

	public void setIntDuringConstructExistCost(String intDuringConstructExistCost) {
		this.intDuringConstructExistCost = intDuringConstructExistCost;
	}

	public String getIntDuringConstructProposedCost() {
		return intDuringConstructProposedCost;
	}

	public void setIntDuringConstructProposedCost(String intDuringConstructProposedCost) {
		this.intDuringConstructProposedCost = intDuringConstructProposedCost;
	}

	public String getIntDuringConstructAppraisedCost() {
		return intDuringConstructAppraisedCost;
	}

	public void setIntDuringConstructAppraisedCost(String intDuringConstructAppraisedCost) {
		this.intDuringConstructAppraisedCost = intDuringConstructAppraisedCost;
	}

	public String getIntDuringConstructRemarks() {
		return intDuringConstructRemarks;
	}

	public void setIntDuringConstructRemarks(String intDuringConstructRemarks) {
		this.intDuringConstructRemarks = intDuringConstructRemarks;
	}

	public String getMarginMoneyExistCost() {
		return marginMoneyExistCost;
	}

	public void setMarginMoneyExistCost(String marginMoneyExistCost) {
		this.marginMoneyExistCost = marginMoneyExistCost;
	}

	public String getMarginMoneyProposedCost() {
		return marginMoneyProposedCost;
	}

	public void setMarginMoneyProposedCost(String marginMoneyProposedCost) {
		this.marginMoneyProposedCost = marginMoneyProposedCost;
	}

	public String getMarginMoneyAppraisedCost() {
		return marginMoneyAppraisedCost;
	}

	public void setMarginMoneyAppraisedCost(String marginMoneyAppraisedCost) {
		this.marginMoneyAppraisedCost = marginMoneyAppraisedCost;
	}

	public String getMarginMoneyRemarks() {
		return marginMoneyRemarks;
	}

	public void setMarginMoneyRemarks(String marginMoneyRemarks) {
		this.marginMoneyRemarks = marginMoneyRemarks;
	}

	public String getTotalExistCost() {
		return totalExistCost;
	}

	public void setTotalExistCost(String totalExistCost) {
		this.totalExistCost = totalExistCost;
	}

	public String getTotalProposedCost() {
		return totalProposedCost;
	}

	public void setTotalProposedCost(String totalProposedCost) {
		this.totalProposedCost = totalProposedCost;
	}

	public String getTotalAppraisedCost() {
		return totalAppraisedCost;
	}

	public void setTotalAppraisedCost(String totalAppraisedCost) {
		this.totalAppraisedCost = totalAppraisedCost;
	}

	public String getEquityExistCost() {
		return equityExistCost;
	}

	public void setEquityExistCost(String equityExistCost) {
		this.equityExistCost = equityExistCost;
	}

	public String getEquityProposedCost() {
		return equityProposedCost;
	}

	public void setEquityProposedCost(String equityProposedCost) {
		this.equityProposedCost = equityProposedCost;
	}

	public String getEquityAppraisedCost() {
		return equityAppraisedCost;
	}

	public void setEquityAppraisedCost(String equityAppraisedCost) {
		this.equityAppraisedCost = equityAppraisedCost;
	}

	public String getEquityRemarks() {
		return equityRemarks;
	}

	public void setEquityRemarks(String equityRemarks) {
		this.equityRemarks = equityRemarks;
	}

	public String getGrantExistCost() {
		return grantExistCost;
	}

	public void setGrantExistCost(String grantExistCost) {
		this.grantExistCost = grantExistCost;
	}

	public String getGrantProposedCost() {
		return grantProposedCost;
	}

	public void setGrantProposedCost(String grantProposedCost) {
		this.grantProposedCost = grantProposedCost;
	}

	public String getGrantAppraisedCost() {
		return grantAppraisedCost;
	}

	public void setGrantAppraisedCost(String grantAppraisedCost) {
		this.grantAppraisedCost = grantAppraisedCost;
	}

	public String getGrantRemarks() {
		return grantRemarks;
	}

	public void setGrantRemarks(String grantRemarks) {
		this.grantRemarks = grantRemarks;
	}

	public String getTermLoanExistCost() {
		return termLoanExistCost;
	}

	public void setTermLoanExistCost(String termLoanExistCost) {
		this.termLoanExistCost = termLoanExistCost;
	}

	public String getTermLoanProposedCost() {
		return termLoanProposedCost;
	}

	public void setTermLoanProposedCost(String termLoanProposedCost) {
		this.termLoanProposedCost = termLoanProposedCost;
	}

	public String getTermLoanAppraisedCost() {
		return termLoanAppraisedCost;
	}

	public void setTermLoanAppraisedCost(String termLoanAppraisedCost) {
		this.termLoanAppraisedCost = termLoanAppraisedCost;
	}

	public String getTermLoanRemarks() {
		return termLoanRemarks;
	}

	public void setTermLoanRemarks(String termLoanRemarks) {
		this.termLoanRemarks = termLoanRemarks;
	}

	public String getWorkingCapitalExistCost() {
		return workingCapitalExistCost;
	}

	public void setWorkingCapitalExistCost(String workingCapitalExistCost) {
		this.workingCapitalExistCost = workingCapitalExistCost;
	}

	public String getWorkingCapitalProposedCost() {
		return workingCapitalProposedCost;
	}

	public void setWorkingCapitalProposedCost(String workingCapitalProposedCost) {
		this.workingCapitalProposedCost = workingCapitalProposedCost;
	}

	public String getWorkingCapitalAppraisedCost() {
		return workingCapitalAppraisedCost;
	}

	public void setWorkingCapitalAppraisedCost(String workingCapitalAppraisedCost) {
		this.workingCapitalAppraisedCost = workingCapitalAppraisedCost;
	}

	public String getWorkingCapitalRemarks() {
		return workingCapitalRemarks;
	}

	public void setWorkingCapitalRemarks(String workingCapitalRemarks) {
		this.workingCapitalRemarks = workingCapitalRemarks;
	}

	public String getTotalMofExistCost() {
		return totalMofExistCost;
	}

	public void setTotalMofExistCost(String totalMofExistCost) {
		this.totalMofExistCost = totalMofExistCost;
	}

	public String getTotalMofProposedCost() {
		return totalMofProposedCost;
	}

	public void setTotalMofProposedCost(String totalMofProposedCost) {
		this.totalMofProposedCost = totalMofProposedCost;
	}

	public String getTotalMofAppraisedCost() {
		return totalMofAppraisedCost;
	}

	public void setTotalMofAppraisedCost(String totalMofAppraisedCost) {
		this.totalMofAppraisedCost = totalMofAppraisedCost;
	}

	public String getTotalMofRemarks() {
		return totalMofRemarks;
	}

	public void setTotalMofRemarks(String totalMofRemarks) {
		this.totalMofRemarks = totalMofRemarks;
	}

	public String getStartConstructionOfBuildDate() {
		return startConstructionOfBuildDate;
	}

	public void setStartConstructionOfBuildDate(String startConstructionOfBuildDate) {
		this.startConstructionOfBuildDate = startConstructionOfBuildDate;
	}

	public String getStartOfConstructionOfBuildRemark() {
		return startOfConstructionOfBuildRemark;
	}

	public void setStartOfConstructionOfBuildRemark(String startOfConstructionOfBuildRemark) {
		this.startOfConstructionOfBuildRemark = startOfConstructionOfBuildRemark;
	}

	public String getCompletionOfBuildDate() {
		return completionOfBuildDate;
	}

	public void setCompletionOfBuildDate(String completionOfBuildDate) {
		this.completionOfBuildDate = completionOfBuildDate;
	}

	public String getCompletionOfBuildRemark() {
		return completionOfBuildRemark;
	}

	public void setCompletionOfBuildRemark(String completionOfBuildRemark) {
		this.completionOfBuildRemark = completionOfBuildRemark;
	}

	public String getPlacingOrderPlantMachDate() {
		return placingOrderPlantMachDate;
	}

	public void setPlacingOrderPlantMachDate(String placingOrderPlantMachDate) {
		this.placingOrderPlantMachDate = placingOrderPlantMachDate;
	}

	public String getPlacingOrderPlantMachRemark() {
		return placingOrderPlantMachRemark;
	}

	public void setPlacingOrderPlantMachRemark(String placingOrderPlantMachRemark) {
		this.placingOrderPlantMachRemark = placingOrderPlantMachRemark;
	}

	public String getInstallationDate() {
		return installationDate;
	}

	public void setInstallationDate(String installationDate) {
		this.installationDate = installationDate;
	}

	public String getInstallationRemark() {
		return installationRemark;
	}

	public void setInstallationRemark(String installationRemark) {
		this.installationRemark = installationRemark;
	}

	public String getTrialProductionDate() {
		return trialProductionDate;
	}

	public void setTrialProductionDate(String trialProductionDate) {
		this.trialProductionDate = trialProductionDate;
	}

	public String getTrialProductionRemark() {
		return trialProductionRemark;
	}

	public void setTrialProductionRemark(String trialProductionRemark) {
		this.trialProductionRemark = trialProductionRemark;
	}

	public String getCommercialProductionDate() {
		return commercialProductionDate;
	}

	public void setCommercialProductionDate(String commercialProductionDate) {
		this.commercialProductionDate = commercialProductionDate;
	}

	public String getCommercialProductionRemark() {
		return commercialProductionRemark;
	}

	public void setCommercialProductionRemark(String commercialProductionRemark) {
		this.commercialProductionRemark = commercialProductionRemark;
	}

	public String getDetailedProjectReportDesc() {
		return detailedProjectReportDesc;
	}

	public void setDetailedProjectReportDesc(String detailedProjectReportDesc) {
		this.detailedProjectReportDesc = detailedProjectReportDesc;
	}

	public String getDetailedProjectReportRemark() {
		return detailedProjectReportRemark;
	}

	public void setDetailedProjectReportRemark(String detailedProjectReportRemark) {
		this.detailedProjectReportRemark = detailedProjectReportRemark;
	}

	public String getSanctionLetterTermLoanDesc() {
		return sanctionLetterTermLoanDesc;
	}

	public void setSanctionLetterTermLoanDesc(String sanctionLetterTermLoanDesc) {
		this.sanctionLetterTermLoanDesc = sanctionLetterTermLoanDesc;
	}

	public String getSanctionLetterTermLoanRemark() {
		return sanctionLetterTermLoanRemark;
	}

	public void setSanctionLetterTermLoanRemark(String sanctionLetterTermLoanRemark) {
		this.sanctionLetterTermLoanRemark = sanctionLetterTermLoanRemark;
	}

	public String getCeCivilCertificationForA7Desc() {
		return ceCivilCertificationForA7Desc;
	}

	public void setCeCivilCertificationForA7Desc(String ceCivilCertificationForA7Desc) {
		this.ceCivilCertificationForA7Desc = ceCivilCertificationForA7Desc;
	}

	public String getCeCivilCertificationForA7Remark() {
		return ceCivilCertificationForA7Remark;
	}

	public void setCeCivilCertificationForA7Remark(String ceCivilCertificationForA7Remark) {
		this.ceCivilCertificationForA7Remark = ceCivilCertificationForA7Remark;
	}

	public String getCeMechanicalCertificationForA8Desc() {
		return ceMechanicalCertificationForA8Desc;
	}

	public void setCeMechanicalCertificationForA8Desc(String ceMechanicalCertificationForA8Desc) {
		this.ceMechanicalCertificationForA8Desc = ceMechanicalCertificationForA8Desc;
	}

	public String getCeMechanicalCertificationForA8Remark() {
		return ceMechanicalCertificationForA8Remark;
	}

	public void setCeMechanicalCertificationForA8Remark(String ceMechanicalCertificationForA8Remark) {
		this.ceMechanicalCertificationForA8Remark = ceMechanicalCertificationForA8Remark;
	}

	public String getLandDocumentDesc() {
		return landDocumentDesc;
	}

	public void setLandDocumentDesc(String landDocumentDesc) {
		this.landDocumentDesc = landDocumentDesc;
	}

	public String getLandDocumentRemark() {
		return landDocumentRemark;
	}

	public void setLandDocumentRemark(String landDocumentRemark) {
		this.landDocumentRemark = landDocumentRemark;
	}

	public String getTotaleEligibleCostInLacs() {
		return totaleEligibleCostInLacs;
	}

	public void setTotaleEligibleCostInLacs(String totaleEligibleCostInLacs) {
		this.totaleEligibleCostInLacs = totaleEligibleCostInLacs;
	}

	public String getEligibleCostRemarks() {
		return eligibleCostRemarks;
	}

	public void setEligibleCostRemarks(String eligibleCostRemarks) {
		this.eligibleCostRemarks = eligibleCostRemarks;
	}

	public String getTtlEligibleTechCivilWorkEligibleCost() {
		return ttlEligibleTechCivilWorkEligibleCost;
	}

	public void setTtlEligibleTechCivilWorkEligibleCost(String ttlEligibleTechCivilWorkEligibleCost) {
		this.ttlEligibleTechCivilWorkEligibleCost = ttlEligibleTechCivilWorkEligibleCost;
	}

	public String getEligibleTechCivilWorkRemarks() {
		return eligibleTechCivilWorkRemarks;
	}

	public void setEligibleTechCivilWorkRemarks(String eligibleTechCivilWorkRemarks) {
		this.eligibleTechCivilWorkRemarks = eligibleTechCivilWorkRemarks;
	}

	public String getGrantAmtEligiblleCostOfPlantMach() {
		return grantAmtEligiblleCostOfPlantMach;
	}

	public void setGrantAmtEligiblleCostOfPlantMach(String grantAmtEligiblleCostOfPlantMach) {
		this.grantAmtEligiblleCostOfPlantMach = grantAmtEligiblleCostOfPlantMach;
	}

	public String getGrantAmtEligibleCostOfTechnicalCivilWork() {
		return grantAmtEligibleCostOfTechnicalCivilWork;
	}

	public void setGrantAmtEligibleCostOfTechnicalCivilWork(String grantAmtEligibleCostOfTechnicalCivilWork) {
		this.grantAmtEligibleCostOfTechnicalCivilWork = grantAmtEligibleCostOfTechnicalCivilWork;
	}

	public String getGrantAmtEligibleProjectCost() {
		return grantAmtEligibleProjectCost;
	}

	public void setGrantAmtEligibleProjectCost(String grantAmtEligibleProjectCost) {
		this.grantAmtEligibleProjectCost = grantAmtEligibleProjectCost;
	}

	public String getGrantAmtEligiblePM() {
		return grantAmtEligiblePM;
	}

	public void setGrantAmtEligiblePM(String grantAmtEligiblePM) {
		this.grantAmtEligiblePM = grantAmtEligiblePM;
	}

	public String getGrantAmtMaxEligibleGrant() {
		return grantAmtMaxEligibleGrant;
	}

	public void setGrantAmtMaxEligibleGrant(String grantAmtMaxEligibleGrant) {
		this.grantAmtMaxEligibleGrant = grantAmtMaxEligibleGrant;
	}

	public String getGrantAmtRemarks() {
		return grantAmtRemarks;
	}

	public void setGrantAmtRemarks(String grantAmtRemarks) {
		this.grantAmtRemarks = grantAmtRemarks;
	}

	public String getInterestSubsidy() {
		return interestSubsidy;
	}

	public void setInterestSubsidy(String interestSubsidy) {
		this.interestSubsidy = interestSubsidy;
	}

	public String getRecommendationComments() {
		return recommendationComments;
	}

	public void setRecommendationComments(String recommendationComments) {
		this.recommendationComments = recommendationComments;
	}

	public String getProposalSubmissionDate() {
		return proposalSubmissionDate;
	}

	public void setProposalSubmissionDate(String proposalSubmissionDate) {
		this.proposalSubmissionDate = proposalSubmissionDate;
	}

	public String getAvailOfLandAndBuilding() {
		return availOfLandAndBuilding;
	}

	public void setAvailOfLandAndBuilding(String availOfLandAndBuilding) {
		this.availOfLandAndBuilding = availOfLandAndBuilding;
	}

	public String getCapacityUtilizationBuyProducts() {
		return capacityUtilizationBuyProducts;
	}

	public void setCapacityUtilizationBuyProducts(String capacityUtilizationBuyProducts) {
		this.capacityUtilizationBuyProducts = capacityUtilizationBuyProducts;
	}

	public String getAmtOfTermloanSanctionedBuyProducts() {
		return amtOfTermloanSanctionedBuyProducts;
	}

	public void setAmtOfTermloanSanctionedBuyProducts(String amtOfTermloanSanctionedBuyProducts) {
		this.amtOfTermloanSanctionedBuyProducts = amtOfTermloanSanctionedBuyProducts;
	}

	public String getAmtOfTermloanSanctionedBuyProductsObservs() {
		return amtOfTermloanSanctionedBuyProductsObservs;
	}

	public void setAmtOfTermloanSanctionedBuyProductsObservs(String amtOfTermloanSanctionedBuyProductsObservs) {
		this.amtOfTermloanSanctionedBuyProductsObservs = amtOfTermloanSanctionedBuyProductsObservs;
	}

	public String getPlantMachRemarks() {
		return plantMachRemarks;
	}

	public void setPlantMachRemarks(String plantMachRemarks) {
		this.plantMachRemarks = plantMachRemarks;
	}

	public String getMiscFixedAssetsRemarks() {
		return miscFixedAssetsRemarks;
	}

	public void setMiscFixedAssetsRemarks(String miscFixedAssetsRemarks) {
		this.miscFixedAssetsRemarks = miscFixedAssetsRemarks;
	}

	public String getTotalRemarks() {
		return totalRemarks;
	}

	public void setTotalRemarks(String totalRemarks) {
		this.totalRemarks = totalRemarks;
	}

	public String getAcquisitionLandDate() {
		return acquisitionLandDate;
	}

	public void setAcquisitionLandDate(String acquisitionLandDate) {
		this.acquisitionLandDate = acquisitionLandDate;
	}

	public String getAcquisitionLandRemark() {
		return acquisitionLandRemark;
	}

	public void setAcquisitionLandRemark(String acquisitionLandRemark) {
		this.acquisitionLandRemark = acquisitionLandRemark;
	}

	public String getAppOnPrescribedFormatDesc() {
		return appOnPrescribedFormatDesc;
	}

	public String getEligibleCostNameOfPlantMach() {
		return eligibleCostNameOfPlantMach;
	}

	public void setEligibleCostNameOfPlantMach(String eligibleCostNameOfPlantMach) {
		this.eligibleCostNameOfPlantMach = eligibleCostNameOfPlantMach;
	}

	public String getEligibleCostNameOfSupplierCompany() {
		return eligibleCostNameOfSupplierCompany;
	}

	public void setEligibleCostNameOfSupplierCompany(String eligibleCostNameOfSupplierCompany) {
		this.eligibleCostNameOfSupplierCompany = eligibleCostNameOfSupplierCompany;
	}

	public String getEligibleCostBasicPrice() {
		return eligibleCostBasicPrice;
	}

	public void setEligibleCostBasicPrice(String eligibleCostBasicPrice) {
		this.eligibleCostBasicPrice = eligibleCostBasicPrice;
	}

	public String getEligibleTechCivilWorkParticular() {
		return eligibleTechCivilWorkParticular;
	}

	public void setEligibleTechCivilWorkParticular(String eligibleTechCivilWorkParticular) {
		this.eligibleTechCivilWorkParticular = eligibleTechCivilWorkParticular;
	}

	public String getEligibleTechCivilWorkArea() {
		return eligibleTechCivilWorkArea;
	}

	public void setEligibleTechCivilWorkArea(String eligibleTechCivilWorkArea) {
		this.eligibleTechCivilWorkArea = eligibleTechCivilWorkArea;
	}

	public String getEligibleTechCivilWorkRate() {
		return eligibleTechCivilWorkRate;
	}

	public void setEligibleTechCivilWorkRate(String eligibleTechCivilWorkRate) {
		this.eligibleTechCivilWorkRate = eligibleTechCivilWorkRate;
	}

	public String getEligibleTechCivilWorkAmount() {
		return eligibleTechCivilWorkAmount;
	}

	public void setEligibleTechCivilWorkAmount(String eligibleTechCivilWorkAmount) {
		this.eligibleTechCivilWorkAmount = eligibleTechCivilWorkAmount;
	}

	public String[] getEligibleTechCivilWorkEligibleCost() {
		return eligibleTechCivilWorkEligibleCost;
	}

	public void setEligibleTechCivilWorkEligibleCost(String[] eligibleTechCivilWorkEligibleCost) {
		this.eligibleTechCivilWorkEligibleCost = eligibleTechCivilWorkEligibleCost;
	}

	public void setAppOnPrescribedFormatDesc(String appOnPrescribedFormatDesc) {
		this.appOnPrescribedFormatDesc = appOnPrescribedFormatDesc;
	}

	public String getAppOnPrescribedFormatRemark() {
		return appOnPrescribedFormatRemark;
	}

	public void setAppOnPrescribedFormatRemark(String appOnPrescribedFormatRemark) {
		this.appOnPrescribedFormatRemark = appOnPrescribedFormatRemark;
	}

	public String getBankAppraisalReportDesc() {
		return bankAppraisalReportDesc;
	}

	public void setBankAppraisalReportDesc(String bankAppraisalReportDesc) {
		this.bankAppraisalReportDesc = bankAppraisalReportDesc;
	}

	public String getBankAppraisalReportRemark() {
		return bankAppraisalReportRemark;
	}

	public void setBankAppraisalReportRemark(String bankAppraisalReportRemark) {
		this.bankAppraisalReportRemark = bankAppraisalReportRemark;
	}

	public String getBuildingPlanOfFactoryDesc() {
		return buildingPlanOfFactoryDesc;
	}

	public void setBuildingPlanOfFactoryDesc(String buildingPlanOfFactoryDesc) {
		this.buildingPlanOfFactoryDesc = buildingPlanOfFactoryDesc;
	}

	public String getBuildingPlanOfFactoryRemark() {
		return buildingPlanOfFactoryRemark;
	}

	public void setBuildingPlanOfFactoryRemark(String buildingPlanOfFactoryRemark) {
		this.buildingPlanOfFactoryRemark = buildingPlanOfFactoryRemark;
	}

	public String getAffidavitPrescribedFormatDesc() {
		return affidavitPrescribedFormatDesc;
	}

	public void setAffidavitPrescribedFormatDesc(String affidavitPrescribedFormatDesc) {
		this.affidavitPrescribedFormatDesc = affidavitPrescribedFormatDesc;
	}

	public String getAffidavitPrescribedFormatRemark() {
		return affidavitPrescribedFormatRemark;
	}

	public void setAffidavitPrescribedFormatRemark(String affidavitPrescribedFormatRemark) {
		this.affidavitPrescribedFormatRemark = affidavitPrescribedFormatRemark;
	}

	@Column(name = "sno")
	private transient String[] sno;

	public String[] getSno() {
		return sno;
	}

	public void setSno(String[] sno) {
		this.sno = sno;
	}

	@Column(name = "totalplandmachamt")
	private transient String[] totalplandmachamt;

	@Column(name = "eligiblecostinlacs")
	private transient String[] eligiblecostinlacs;

	public String[] getTotalplandmachamt() {
		return totalplandmachamt;
	}

	public void setTotalplandmachamt(String[] totalplandmachamt) {
		this.totalplandmachamt = totalplandmachamt;
	}

	public String[] getEligiblecostinlacs() {
		return eligiblecostinlacs;
	}

	public void setEligiblecostinlacs(String[] eligiblecostinlacs) {
		this.eligiblecostinlacs = eligiblecostinlacs;
	}

	public String getMofOtherExitCost() {
		return mofOtherExitCost;
	}

	public void setMofOtherExitCost(String mofOtherExitCost) {
		this.mofOtherExitCost = mofOtherExitCost;
	}

	public String getMofOtherProposedCost() {
		return mofOtherProposedCost;
	}

	public void setMofOtherProposedCost(String mofOtherProposedCost) {
		this.mofOtherProposedCost = mofOtherProposedCost;
	}

	public String getMofOtherAppraisedCost() {
		return mofOtherAppraisedCost;
	}

	public void setMofOtherAppraisedCost(String mofOtherAppraisedCost) {
		this.mofOtherAppraisedCost = mofOtherAppraisedCost;
	}

	public String getMofOtherRemarks() {
		return mofOtherRemarks;
	}

	public void setMofOtherRemarks(String mofOtherRemarks) {
		this.mofOtherRemarks = mofOtherRemarks;
	}

	public String getIncorporationCertificateOfFirmDesc() {
		return incorporationCertificateOfFirmDesc;
	}

	public void setIncorporationCertificateOfFirmDesc(String incorporationCertificateOfFirmDesc) {
		this.incorporationCertificateOfFirmDesc = incorporationCertificateOfFirmDesc;
	}

	public String getIncorporationCertificateOfFirmRemark() {
		return incorporationCertificateOfFirmRemark;
	}

	public void setIncorporationCertificateOfFirmRemark(String incorporationCertificateOfFirmRemark) {
		this.incorporationCertificateOfFirmRemark = incorporationCertificateOfFirmRemark;
	}

	public String getPartnershipDeedDesc() {
		return partnershipDeedDesc;
	}

	public void setPartnershipDeedDesc(String partnershipDeedDesc) {
		this.partnershipDeedDesc = partnershipDeedDesc;
	}

	public String getPartnershipDeedRemark() {
		return partnershipDeedRemark;
	}

	public void setPartnershipDeedRemark(String partnershipDeedRemark) {
		this.partnershipDeedRemark = partnershipDeedRemark;
	}

	public String getA13BankCertiForIntLiabilityDesc() {
		return a13BankCertiForIntLiabilityDesc;
	}

	public void setA13BankCertiForIntLiabilityDesc(String a13BankCertiForIntLiabilityDesc) {
		this.a13BankCertiForIntLiabilityDesc = a13BankCertiForIntLiabilityDesc;
	}

	public String getA13BankCertiForIntLiabilityRemark() {
		return a13BankCertiForIntLiabilityRemark;
	}

	public void setA13BankCertiForIntLiabilityRemark(String a13BankCertiForIntLiabilityRemark) {
		this.a13BankCertiForIntLiabilityRemark = a13BankCertiForIntLiabilityRemark;
	}

	public String getA18certiForIncreInGrossBlockValueDesc() {
		return a18certiForIncreInGrossBlockValueDesc;
	}

	public void setA18certiForIncreInGrossBlockValueDesc(String a18certiForIncreInGrossBlockValueDesc) {
		this.a18certiForIncreInGrossBlockValueDesc = a18certiForIncreInGrossBlockValueDesc;
	}

	public String getA18certiForIncreaseInGrossBlockValueRemark() {
		return a18certiForIncreaseInGrossBlockValueRemark;
	}

	public void setA18certiForIncreaseInGrossBlockValueRemark(String a18certiForIncreaseInGrossBlockValueRemark) {
		this.a18certiForIncreaseInGrossBlockValueRemark = a18certiForIncreaseInGrossBlockValueRemark;
	}

	
	
	/*
	 * public String[] getTotalplandmachamt() { return totalplandmachamt; }
	 * 
	 * public void setTotalplandmachamt(String[] totalplandmachamt) {
	 * this.totalplandmachamt = totalplandmachamt; }
	 * 
	 * public String[] getEligiblecostinlacs() { return eligiblecostinlacs; }
	 * 
	 * public void setEligiblecostinlacs(String[] eligiblecostinlacs) {
	 * this.eligiblecostinlacs = eligiblecostinlacs; }
	 */
}
