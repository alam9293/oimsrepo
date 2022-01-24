package com.webapp.ims.food.Model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="foodevaluationviewaggropark")
public class FoodEvaluationViewAggroPark implements Serializable

{
	
	private static final long serialVersionUID = 1L;

	@Column(name="app_id")
	private String appId;
	
	@Id
	@Column(name="unit_id")
	private String unitId;
	
	@Column(name="control_id")
	private String controlId;
	
	@Column(name="created_by")
	private String createdBy;
	
	@Column(name="modified_by")
	private String modifiedBy;
	
	@Column(name="status")
	private String status;
	
	@Column(name="name_of_organization")
	private String nameOfOrganization;
	
	@Column(name="registered_address")
	private String registeredAddress;
	
	@Column(name="project_name")
    private String projectName;
	
	@Column(name="proposed_site_address")
    private String proposedSiteAddress;
	
	@Column(name="project_profile_app_id")
	private String projectProfileAppId;
	
	@Column(name="project_profile_app_id_observ")
	private String projectProfileAppIdObserv;
	
	@Column(name="proposal_submission_date")
	private String proposalSubmissionDate;
	
	@Column(name="proposal_submission_date_observ")
	private String proposalSubmissionDateObserv;
	
	
	@Column(name="name_of_promoter")
	private String nameOfPromoter;

	@Column(name="name_of_promoter_observ")
	private String nameOfPromoterObserv;
	
	
	@Column(name="type_of_organization")
	private String typeOfOrganization;

	@Column(name="type_of_organization_observ")
	private String typeOfOrganizationObserv;
	

	@Column(name="date_of_incorporation")
	private String dateOfIncorporation;

	@Column(name="date_of_incorporation_obsev")
	private String dateOfIncorporationObsev;
	
	@Column(name="availability_of_land")
	private String availabilityOfLand;

	@Column(name="availability_of_land_observ")
	private String availabilityOfLandObserv;

	
	@Column(name="pan_no")
	private String panNo;

	@Column(name="pan_no_observ")
	private String panNoObserv;
	
	@Column(name="gst_no")
	private String GSTNo;
	
	@Column(name="gst_no_observ")
	private String GSTNoObserv;
	
	@Column(name="udyog_aadhar_no")
	private String udyogAadharNo;
	
	@Column(name="udyog_aadhar_no_observ")
	private String udyogAadharNoObserv;
	
	@Column(name="name_of_spv")
	private String nameOfSPV;
	
	@Column(name="name_of_spv_observ")
	private String nameOfSPVObserv;
	
	@Column(name="name_and_address_of_pma")
	private String nameAndAddressOfPMA;
	
	@Column(name="name_and_address_of_pma_observ")
	private String nameAndAddressOfPMAObserv;
	

	@Column(name="annual_turn_over_of_company")
	private String annualTurnOverOfCompany;
	
	@Column(name="annual_turn_over_of_company_observ")
	private String annualTurnOverOfCompanyObserv;
	
	@Column(name="amount_of_term_loan_sanctioned")
	private String amountOfTermLoanSanctioned;
	
	@Column(name="amount_of_term_loan_sanctioned_observ")
	private String amountOfTermLoanSanctionedObserv;
	
	
	@Column(name="date_of_sanction")
	private String dateOfSanction;
	
	@Column(name="date_of_sanction_observ")
	private String dateOfSanctionObserv;
	
	@Column(name="project_summary")
	private String projectSummary;
	
	//CAPITAL INVESTMENT (IN LACS)
	
	@Column(name="land_proposed_cost")
	private String landProposedCost;
	@Column(name="land_appraised_cost")
	private String landAppraisedCost;
	@Column(name="land_cost_remarks")
	private String landCostRemarks;
	
	@Column(name="central_process_center_proposed_cost")
	private String centralProcessCenterProposedCost;
	@Column(name="central_process_center_appraised_cost")
	private String centralProcessCenterAppraisedCost;
	@Column(name="central_process_center_remarks")
	private String centralProcessCenterRemarks;
	
	@Column(name="primary_process_center_proposed_cost")
	private String primaryProcessCenterProposedCost;
	@Column(name="primary_process_center_appraised_cost")
	private String primaryProcessCenterAppraisedCost;
	@Column(name="primary_process_center_remarks")
	private String primaryProcessCenterRemarks;
	
	@Column(name="basic_enabling_infra_cpc_proposed_cost")
	private String basicEnablingInfraCPCProposedCost;
	@Column(name="basic_enabling_infra_cpc_appraised_cost")
	private String basicEnablingInfraCPCAppraisedCost;
	@Column(name="basic_enabling_infra_cpc_remarks")
	private String basicEnablingInfraCPCRemarks;
	
	@Column(name="basic_enabling_infra_ppc_proposed_cost")
	private String basicEnablingInfraPPCProposedCost;
	@Column(name="basic_enabling_infra_ppc_appraised_cost")
	private String basicEnablingInfraPPCAppraisedCost;
	@Column(name="basic_enabling_infra_ppc_remarks")
	private String basicEnablingInfraPPCRemarks;
	
	@Column(name="core_infra_cpc_proposed_cost")
	private String coreInfraCPCProposedCost;
	@Column(name="core_infra_cpc_appraised_cost")
	private String coreInfraCPCAppraisedCost;
	@Column(name="core_infra_cpc_remarks")
	private String coreInfraCPCRemarks;
	
	@Column(name="core_infra_ppc_proposed_cost")
	private String coreInfraPPCProposedCost;
	@Column(name="core_infra_ppc_appraised_cost")
	private String coreInfraPPCAppraisedCost;
	@Column(name="core_infra_ppc_remarks")
	private String coreInfraPPCRemarks;
	
	@Column(name="non_co_infra_cpc_proposed_cost")
	private String nonCoInfraCPCProposedCost;
	@Column(name="non_co_infra_cpc_appraised_cost")
	private String nonCoInfraCPCAppraisedCost;
	@Column(name="non_co_infra_cpc_remarks")
	private String nonCoInfraCPCRemarks;
	
	@Column(name="non_co_infra_ppc_proposed_cost")
	private String nonCoInfraPPCProposedCost;
	@Column(name="non_co_infra_ppc_appraised_cost")
	private String nonCoInfraPPCAppraisedCost;
	@Column(name="non_co_infra_ppc_remarks")
	private String nonCoInfraPPCRemarks;
	
	
	@Column(name="interest_during_construct_proposed_cost")
	private String interestDuringConstructProposedCost;
	@Column(name="interest_during_construct_appraised_cost")
	private String interestDuringConstructAppraisedCost;
	@Column(name="interest_during_construct_remarks")
	private String interestDuringConstructRemarks;
	
	@Column(name="pmc_consultancy_fee_proposed_cost")
	private String pmcConsultancyFeeProposedCost;
	@Column(name="pmc_consultancy_fee_appraised_cost")
	private String pmcConsultancyFeeAppraisedCost;
	@Column(name="pmc_consultancy_fee_remarks")
	private String pmcConsultancyFeeRemarks;
	
	@Column(name="preliminary_and_pre_operative_proposed_cost")
	private String preliminaryAndPreOperativeProposedCost;
	@Column(name="preliminary_and_pre_operative_appraised_cost")
	private String preliminaryAndPreOperativeAppraisedCost;
	@Column(name="preliminary_and_pre_operative_remarks")
	private String preliminaryAndPreOperativeRemarks;
	
	@Column(name="margin_money_for_working_capital_proposed_cost")
	private String marginMoneyForWorkingCapitalProposedCost;
	@Column(name="margin_money_for_working_capital_appraised_cost")
	private String marginMoneyForWorkingCapitalAppraisedCost;
	@Column(name="margin_money_for_working_capital_remarks")
	private String marginMoneyForWorkingCapitalRemarks;
	
	@Column(name="contingencies_proposed_cost")
	private String contingenciesProposedCost;
	@Column(name="contingencies_appraised_cost")
	private String contingenciesAppraisedCost;
	@Column(name="contingencies_remarks")
	private String contingenciesRemarks;
	
	@Column(name="total_proposed_cost")
	private String totalProposedCost;
	@Column(name="total_appraised_cost")
	private String totalAppraisedCost;
	@Column(name="total_remarks")
	private String totalRemarks;
	
	//MEANS OF FINANCE (IN LACS)
	
	@Column(name="mof_equity_proposed_cost")
	private String mofEquityProposedCost;
	@Column(name="mof_equity_appraised_cost")
	private String mofEquityAppraisedCost;
	@Column(name="mof_equity_remarks")
	private String mofEquityRemarks;
	
	
	@Column(name="mof_term_loan_proposed_cost")
	private String mofTermLoanProposedCost;
	@Column(name="mof_term_loan_appraised_cost")
	private String mofTermLoanAppraisedCost;
	@Column(name="mof_term_loan_remark")
	private String mofTermLoanRemark;
	
	@Column(name="mof_assis_under_upfpip_proposed_cost")
	private String mofAssisUnderUPFPIPProposedCost ;
	@Column(name="mof_assis_under_upfpip_appraised_cost")
	private String mofAssisUnderUPFPIPAppraisedCost;
	@Column(name="mof_assis_under_upfpip_remarks")
	private String mofAssisUnderUPFPIPRemark;
	
	@Column(name="grant_in_aid_from_mofpi_proposed_cost")
	private String grantInAidFromMOFPIProposedCost ;
	@Column(name="grant_in_aid_from_mofpi_appraised_cost")
	private String grantInAidFromMOFPIPAppraisedCost;
	@Column(name="grant_in_aid_from_mofpi_remarks")
	private String grantInAidFromMOFPIRemark;
	
	
	@Column(name="mof_total_proposed_cost")
	private String mofTotalProposedCost ;
	@Column(name="mof_total_appraised_cost")
	private String mofTotalAppraisedCost;
	@Column(name="mof_total_remarks")
	private String mofTotalRemarks;
	
	@Column(name="mof_others_proposed_cost")
	private String mofOthersProposedCost ;
	@Column(name="mof_others_appraised_cost")
	private String mofOthersAppraisedCost;
	@Column(name="mof_others_remarks")
	private String mofOthersRemarks;
	
	//STATUS OF DOCUMENT ://
	

	@Column(name="incorporation_certificate_of_spv_desc")
	private String incorporationCertificateOfSPVDesc;
	@Column(name="incorporation_certificate_of_spv_remarks")
	private String incorporationCertificateOfSPVRemarks;
	
	@Column(name="memorandum_and_article_desc")
	private String memorandumAndArticleDesc;
	@Column(name="memorandum_and_article_remarks")
	private String memorandumAndArticleRemarks;
	
	@Column(name="registered_land_deed_desc")
	private String registeredLandDeedDesc ;
	@Column(name="registered_land_deed_remarks")
	private String registeredLandDeedRemarks;
	
	@Column(name="certificate_of_partnership_deed_desc")
	private String certificateOfPartnershipDeedDesc;
	@Column(name="certificate_of_partnership_deed_remarks")
	private String certificateOfPartnershipDeedRemarks;
	
	@Column(name="rent_agreement_deed_desc")
	private String rentAgreementDeedDesc;
	@Column(name="rent_agreement_deed_remarks")
	private String rentAgreementDeedRemark;
	
	@Column(name="khasra_khatauni_desc")
	private String khasraKhatauniDesc;
	@Column(name="khasra_khatauni_remarks")
	private String khasraKhatauniRemarks;
	
	@Column(name="land_use_certificate_desc")
	private String landUseCertificateDesc;
	@Column(name="land_use_certificate_remark")
	private String landUseCertificateRemark;
	
	@Column(name="detailed_project_report_desc")
	private String detailedProjectReportDesc;
	@Column(name="detailed_project_report_remarks")
	private String detailedProjectReportRemark;
	
	@Column(name="term_loan_sanction_letter_desc")
	private String termLoanSanctionLetterDesc;
	@Column(name="term_loan_sanction_letter_remarks")
	private String termLoanSanctionLetterRemark;
	
	@Column(name="bank_appraisal_report_desc")
	private String bankAppraisalReportDesc;
	@Column(name="bank_appraisal_report_remark")
	private String bankAppraisalReportRemark;

	@Column(name="site_plan_of_food_park_desc")
	private String sitePlanOfFoodParkDesc;
	@Column(name="site_plan_of_food_park_remark")
	private String sitePlanOfFoodParkRemark;

	@Column(name="ca_certificate_for_project_cost_desc")
	private String caCertificateForProjectCostDesc;
	@Column(name="ca_certificate_for_project_cost_remarks")
	private String caCertificateForProjectCostRemark;
	
	@Column(name="affidavit_from_promoter_desc")
	private String affidavitFromPromoterDesc;
	@Column(name="affidavit_from_promoter_remarks")
	private String affidavitFromPromoterDescRemark;
	
	@Column(name="bank_certificate_noc_desc")
	private String bankCertificateNOCDesc;
	@Column(name="bank_certificate_noc_remarks")
	private String bankCertificateNOCDescRemark;
	
	@Column(name="sanction_letter_issued_by_ministry_desc")
	private String sanctionLetterIssuedByMinistryDesc;
	@Column(name="sanction_letter_issued_by_ministry_remark")
	private String sanctionLetterIssuedByMinistryRemark;

	
	
	//APPLICATION AMOUNT FOR CAPITAL SUBSIDY CAPITAL SUBSIDY :
	
	@Column(name="amount_sanctioned_by_mofpi")
	private String amountSanctionedByMOFPI;
	
	@Column(name="eligible_add_capital_subsidy")
	private String eligibleAddCapitalSubsidy;
	
	@Column(name="max_grant")
	private String maxGrant;
	

	
	@Column(name="app_amt_capital_subsidy_remarks")
	private String appAmtCapitalSubSidyRemarks;



	


	public String getUnitId() {
		return unitId;
	}



	public void setUnitId(String unitId) {
		this.unitId = unitId;
	}



	



	public String getAppId() {
		return appId;
	}



	public void setAppId(String appId) {
		this.appId = appId;
	}



	public String getControlId() {
		return controlId;
	}



	public void setControlId(String controlId) {
		this.controlId = controlId;
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



	public String getProposalSubmissionDate() {
		return proposalSubmissionDate;
	}



	public void setProposalSubmissionDate(String proposalSubmissionDate) {
		this.proposalSubmissionDate = proposalSubmissionDate;
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



	public String getDateOfIncorporationObsev() {
		return dateOfIncorporationObsev;
	}



	public void setDateOfIncorporationObsev(String dateOfIncorporationObsev) {
		this.dateOfIncorporationObsev = dateOfIncorporationObsev;
	}



	public String getAvailabilityOfLand() {
		return availabilityOfLand;
	}



	public void setAvailabilityOfLand(String availabilityOfLand) {
		this.availabilityOfLand = availabilityOfLand;
	}



	public String getAvailabilityOfLandObserv() {
		return availabilityOfLandObserv;
	}



	public void setAvailabilityOfLandObserv(String availabilityOfLandObserv) {
		this.availabilityOfLandObserv = availabilityOfLandObserv;
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



	public String getGSTNo() {
		return GSTNo;
	}



	public void setGSTNo(String gSTNo) {
		GSTNo = gSTNo;
	}



	public String getGSTNoObserv() {
		return GSTNoObserv;
	}



	public void setGSTNoObserv(String gSTNoObserv) {
		GSTNoObserv = gSTNoObserv;
	}



	public String getUdyogAadharNo() {
		return udyogAadharNo;
	}



	public void setUdyogAadharNo(String udyogAadharNo) {
		this.udyogAadharNo = udyogAadharNo;
	}



	public String getUdyogAadharNoObserv() {
		return udyogAadharNoObserv;
	}



	public void setUdyogAadharNoObserv(String udyogAadharNoObserv) {
		this.udyogAadharNoObserv = udyogAadharNoObserv;
	}



	public String getNameOfSPV() {
		return nameOfSPV;
	}



	public void setNameOfSPV(String nameOfSPV) {
		this.nameOfSPV = nameOfSPV;
	}



	public String getNameOfSPVObserv() {
		return nameOfSPVObserv;
	}



	public void setNameOfSPVObserv(String nameOfSPVObserv) {
		this.nameOfSPVObserv = nameOfSPVObserv;
	}



	public String getNameAndAddressOfPMA() {
		return nameAndAddressOfPMA;
	}



	public void setNameAndAddressOfPMA(String nameAndAddressOfPMA) {
		this.nameAndAddressOfPMA = nameAndAddressOfPMA;
	}



	public String getNameAndAddressOfPMAObserv() {
		return nameAndAddressOfPMAObserv;
	}



	public void setNameAndAddressOfPMAObserv(String nameAndAddressOfPMAObserv) {
		this.nameAndAddressOfPMAObserv = nameAndAddressOfPMAObserv;
	}



	public String getAnnualTurnOverOfCompany() {
		return annualTurnOverOfCompany;
	}



	public void setAnnualTurnOverOfCompany(String annualTurnOverOfCompany) {
		this.annualTurnOverOfCompany = annualTurnOverOfCompany;
	}



	public String getAnnualTurnOverOfCompanyObserv() {
		return annualTurnOverOfCompanyObserv;
	}



	public void setAnnualTurnOverOfCompanyObserv(String annualTurnOverOfCompanyObserv) {
		this.annualTurnOverOfCompanyObserv = annualTurnOverOfCompanyObserv;
	}



	public String getAmountOfTermLoanSanctioned() {
		return amountOfTermLoanSanctioned;
	}



	public void setAmountOfTermLoanSanctioned(String amountOfTermLoanSanctioned) {
		this.amountOfTermLoanSanctioned = amountOfTermLoanSanctioned;
	}



	public String getAmountOfTermLoanSanctionedObserv() {
		return amountOfTermLoanSanctionedObserv;
	}



	public void setAmountOfTermLoanSanctionedObserv(String amountOfTermLoanSanctionedObserv) {
		this.amountOfTermLoanSanctionedObserv = amountOfTermLoanSanctionedObserv;
	}



	public String getDateOfSanction() {
		return dateOfSanction;
	}



	public void setDateOfSanction(String dateOfSanction) {
		this.dateOfSanction = dateOfSanction;
	}



	public String getDateOfSanctionObserv() {
		return dateOfSanctionObserv;
	}



	public void setDateOfSanctionObserv(String dateOfSanctionObserv) {
		this.dateOfSanctionObserv = dateOfSanctionObserv;
	}



	public String getProjectSummary() {
		return projectSummary;
	}



	public void setProjectSummary(String projectSummary) {
		this.projectSummary = projectSummary;
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



	public String getLandCostRemarks() {
		return landCostRemarks;
	}



	public void setLandCostRemarks(String landCostRemarks) {
		this.landCostRemarks = landCostRemarks;
	}



	public String getCentralProcessCenterProposedCost() {
		return centralProcessCenterProposedCost;
	}



	public void setCentralProcessCenterProposedCost(String centralProcessCenterProposedCost) {
		this.centralProcessCenterProposedCost = centralProcessCenterProposedCost;
	}



	public String getCentralProcessCenterAppraisedCost() {
		return centralProcessCenterAppraisedCost;
	}



	public void setCentralProcessCenterAppraisedCost(String centralProcessCenterAppraisedCost) {
		this.centralProcessCenterAppraisedCost = centralProcessCenterAppraisedCost;
	}



	public String getCentralProcessCenterRemarks() {
		return centralProcessCenterRemarks;
	}



	public void setCentralProcessCenterRemarks(String centralProcessCenterRemarks) {
		this.centralProcessCenterRemarks = centralProcessCenterRemarks;
	}



	public String getPrimaryProcessCenterProposedCost() {
		return primaryProcessCenterProposedCost;
	}



	public void setPrimaryProcessCenterProposedCost(String primaryProcessCenterProposedCost) {
		this.primaryProcessCenterProposedCost = primaryProcessCenterProposedCost;
	}



	public String getPrimaryProcessCenterAppraisedCost() {
		return primaryProcessCenterAppraisedCost;
	}



	public void setPrimaryProcessCenterAppraisedCost(String primaryProcessCenterAppraisedCost) {
		this.primaryProcessCenterAppraisedCost = primaryProcessCenterAppraisedCost;
	}



	public String getPrimaryProcessCenterRemarks() {
		return primaryProcessCenterRemarks;
	}



	public void setPrimaryProcessCenterRemarks(String primaryProcessCenterRemarks) {
		this.primaryProcessCenterRemarks = primaryProcessCenterRemarks;
	}



	public String getBasicEnablingInfraCPCProposedCost() {
		return basicEnablingInfraCPCProposedCost;
	}



	public void setBasicEnablingInfraCPCProposedCost(String basicEnablingInfraCPCProposedCost) {
		this.basicEnablingInfraCPCProposedCost = basicEnablingInfraCPCProposedCost;
	}



	public String getBasicEnablingInfraCPCAppraisedCost() {
		return basicEnablingInfraCPCAppraisedCost;
	}



	public void setBasicEnablingInfraCPCAppraisedCost(String basicEnablingInfraCPCAppraisedCost) {
		this.basicEnablingInfraCPCAppraisedCost = basicEnablingInfraCPCAppraisedCost;
	}



	public String getBasicEnablingInfraCPCRemarks() {
		return basicEnablingInfraCPCRemarks;
	}



	public void setBasicEnablingInfraCPCRemarks(String basicEnablingInfraCPCRemarks) {
		this.basicEnablingInfraCPCRemarks = basicEnablingInfraCPCRemarks;
	}



	public String getBasicEnablingInfraPPCProposedCost() {
		return basicEnablingInfraPPCProposedCost;
	}



	public void setBasicEnablingInfraPPCProposedCost(String basicEnablingInfraPPCProposedCost) {
		this.basicEnablingInfraPPCProposedCost = basicEnablingInfraPPCProposedCost;
	}



	public String getBasicEnablingInfraPPCAppraisedCost() {
		return basicEnablingInfraPPCAppraisedCost;
	}



	public void setBasicEnablingInfraPPCAppraisedCost(String basicEnablingInfraPPCAppraisedCost) {
		this.basicEnablingInfraPPCAppraisedCost = basicEnablingInfraPPCAppraisedCost;
	}



	public String getBasicEnablingInfraPPCRemarks() {
		return basicEnablingInfraPPCRemarks;
	}



	public void setBasicEnablingInfraPPCRemarks(String basicEnablingInfraPPCRemarks) {
		this.basicEnablingInfraPPCRemarks = basicEnablingInfraPPCRemarks;
	}



	public String getCoreInfraCPCProposedCost() {
		return coreInfraCPCProposedCost;
	}



	public void setCoreInfraCPCProposedCost(String coreInfraCPCProposedCost) {
		this.coreInfraCPCProposedCost = coreInfraCPCProposedCost;
	}



	public String getCoreInfraCPCAppraisedCost() {
		return coreInfraCPCAppraisedCost;
	}



	public void setCoreInfraCPCAppraisedCost(String coreInfraCPCAppraisedCost) {
		this.coreInfraCPCAppraisedCost = coreInfraCPCAppraisedCost;
	}



	public String getCoreInfraCPCRemarks() {
		return coreInfraCPCRemarks;
	}



	public void setCoreInfraCPCRemarks(String coreInfraCPCRemarks) {
		this.coreInfraCPCRemarks = coreInfraCPCRemarks;
	}



	public String getCoreInfraPPCProposedCost() {
		return coreInfraPPCProposedCost;
	}



	public void setCoreInfraPPCProposedCost(String coreInfraPPCProposedCost) {
		this.coreInfraPPCProposedCost = coreInfraPPCProposedCost;
	}



	public String getCoreInfraPPCAppraisedCost() {
		return coreInfraPPCAppraisedCost;
	}



	public void setCoreInfraPPCAppraisedCost(String coreInfraPPCAppraisedCost) {
		this.coreInfraPPCAppraisedCost = coreInfraPPCAppraisedCost;
	}



	public String getCoreInfraPPCRemarks() {
		return coreInfraPPCRemarks;
	}



	public void setCoreInfraPPCRemarks(String coreInfraPPCRemarks) {
		this.coreInfraPPCRemarks = coreInfraPPCRemarks;
	}



	public String getNonCoInfraCPCProposedCost() {
		return nonCoInfraCPCProposedCost;
	}



	public void setNonCoInfraCPCProposedCost(String nonCoInfraCPCProposedCost) {
		this.nonCoInfraCPCProposedCost = nonCoInfraCPCProposedCost;
	}



	public String getNonCoInfraCPCAppraisedCost() {
		return nonCoInfraCPCAppraisedCost;
	}



	public void setNonCoInfraCPCAppraisedCost(String nonCoInfraCPCAppraisedCost) {
		this.nonCoInfraCPCAppraisedCost = nonCoInfraCPCAppraisedCost;
	}



	public String getNonCoInfraCPCRemarks() {
		return nonCoInfraCPCRemarks;
	}



	public void setNonCoInfraCPCRemarks(String nonCoInfraCPCRemarks) {
		this.nonCoInfraCPCRemarks = nonCoInfraCPCRemarks;
	}



	public String getNonCoInfraPPCProposedCost() {
		return nonCoInfraPPCProposedCost;
	}



	public void setNonCoInfraPPCProposedCost(String nonCoInfraPPCProposedCost) {
		this.nonCoInfraPPCProposedCost = nonCoInfraPPCProposedCost;
	}



	public String getNonCoInfraPPCAppraisedCost() {
		return nonCoInfraPPCAppraisedCost;
	}



	public void setNonCoInfraPPCAppraisedCost(String nonCoInfraPPCAppraisedCost) {
		this.nonCoInfraPPCAppraisedCost = nonCoInfraPPCAppraisedCost;
	}



	public String getNonCoInfraPPCRemarks() {
		return nonCoInfraPPCRemarks;
	}



	public void setNonCoInfraPPCRemarks(String nonCoInfraPPCRemarks) {
		this.nonCoInfraPPCRemarks = nonCoInfraPPCRemarks;
	}



	public String getInterestDuringConstructProposedCost() {
		return interestDuringConstructProposedCost;
	}



	public void setInterestDuringConstructProposedCost(String interestDuringConstructProposedCost) {
		this.interestDuringConstructProposedCost = interestDuringConstructProposedCost;
	}



	public String getInterestDuringConstructAppraisedCost() {
		return interestDuringConstructAppraisedCost;
	}



	public void setInterestDuringConstructAppraisedCost(String interestDuringConstructAppraisedCost) {
		this.interestDuringConstructAppraisedCost = interestDuringConstructAppraisedCost;
	}



	public String getInterestDuringConstructRemarks() {
		return interestDuringConstructRemarks;
	}



	public void setInterestDuringConstructRemarks(String interestDuringConstructRemarks) {
		this.interestDuringConstructRemarks = interestDuringConstructRemarks;
	}



	public String getPmcConsultancyFeeProposedCost() {
		return pmcConsultancyFeeProposedCost;
	}



	public void setPmcConsultancyFeeProposedCost(String pmcConsultancyFeeProposedCost) {
		this.pmcConsultancyFeeProposedCost = pmcConsultancyFeeProposedCost;
	}



	public String getPmcConsultancyFeeAppraisedCost() {
		return pmcConsultancyFeeAppraisedCost;
	}



	public void setPmcConsultancyFeeAppraisedCost(String pmcConsultancyFeeAppraisedCost) {
		this.pmcConsultancyFeeAppraisedCost = pmcConsultancyFeeAppraisedCost;
	}



	public String getPmcConsultancyFeeRemarks() {
		return pmcConsultancyFeeRemarks;
	}



	public void setPmcConsultancyFeeRemarks(String pmcConsultancyFeeRemarks) {
		this.pmcConsultancyFeeRemarks = pmcConsultancyFeeRemarks;
	}



	public String getPreliminaryAndPreOperativeProposedCost() {
		return preliminaryAndPreOperativeProposedCost;
	}



	public void setPreliminaryAndPreOperativeProposedCost(String preliminaryAndPreOperativeProposedCost) {
		this.preliminaryAndPreOperativeProposedCost = preliminaryAndPreOperativeProposedCost;
	}



	public String getPreliminaryAndPreOperativeAppraisedCost() {
		return preliminaryAndPreOperativeAppraisedCost;
	}



	public void setPreliminaryAndPreOperativeAppraisedCost(String preliminaryAndPreOperativeAppraisedCost) {
		this.preliminaryAndPreOperativeAppraisedCost = preliminaryAndPreOperativeAppraisedCost;
	}



	public String getPreliminaryAndPreOperativeRemarks() {
		return preliminaryAndPreOperativeRemarks;
	}



	public void setPreliminaryAndPreOperativeRemarks(String preliminaryAndPreOperativeRemarks) {
		this.preliminaryAndPreOperativeRemarks = preliminaryAndPreOperativeRemarks;
	}



	public String getMarginMoneyForWorkingCapitalProposedCost() {
		return marginMoneyForWorkingCapitalProposedCost;
	}



	public void setMarginMoneyForWorkingCapitalProposedCost(String marginMoneyForWorkingCapitalProposedCost) {
		this.marginMoneyForWorkingCapitalProposedCost = marginMoneyForWorkingCapitalProposedCost;
	}



	public String getMarginMoneyForWorkingCapitalAppraisedCost() {
		return marginMoneyForWorkingCapitalAppraisedCost;
	}



	public void setMarginMoneyForWorkingCapitalAppraisedCost(String marginMoneyForWorkingCapitalAppraisedCost) {
		this.marginMoneyForWorkingCapitalAppraisedCost = marginMoneyForWorkingCapitalAppraisedCost;
	}



	public String getMarginMoneyForWorkingCapitalRemarks() {
		return marginMoneyForWorkingCapitalRemarks;
	}



	public void setMarginMoneyForWorkingCapitalRemarks(String marginMoneyForWorkingCapitalRemarks) {
		this.marginMoneyForWorkingCapitalRemarks = marginMoneyForWorkingCapitalRemarks;
	}



	public String getContingenciesProposedCost() {
		return contingenciesProposedCost;
	}



	public void setContingenciesProposedCost(String contingenciesProposedCost) {
		this.contingenciesProposedCost = contingenciesProposedCost;
	}



	public String getContingenciesAppraisedCost() {
		return contingenciesAppraisedCost;
	}



	public void setContingenciesAppraisedCost(String contingenciesAppraisedCost) {
		this.contingenciesAppraisedCost = contingenciesAppraisedCost;
	}



	public String getContingenciesRemarks() {
		return contingenciesRemarks;
	}



	public void setContingenciesRemarks(String contingenciesRemarks) {
		this.contingenciesRemarks = contingenciesRemarks;
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



	public String getTotalRemarks() {
		return totalRemarks;
	}



	public void setTotalRemarks(String totalRemarks) {
		this.totalRemarks = totalRemarks;
	}



	public String getMofEquityProposedCost() {
		return mofEquityProposedCost;
	}



	public void setMofEquityProposedCost(String mofEquityProposedCost) {
		this.mofEquityProposedCost = mofEquityProposedCost;
	}



	public String getMofEquityAppraisedCost() {
		return mofEquityAppraisedCost;
	}



	public void setMofEquityAppraisedCost(String mofEquityAppraisedCost) {
		this.mofEquityAppraisedCost = mofEquityAppraisedCost;
	}



	public String getMofEquityRemarks() {
		return mofEquityRemarks;
	}



	public void setMofEquityRemarks(String mofEquityRemarks) {
		this.mofEquityRemarks = mofEquityRemarks;
	}



	public String getMofTermLoanProposedCost() {
		return mofTermLoanProposedCost;
	}



	public void setMofTermLoanProposedCost(String mofTermLoanProposedCost) {
		this.mofTermLoanProposedCost = mofTermLoanProposedCost;
	}



	public String getMofTermLoanAppraisedCost() {
		return mofTermLoanAppraisedCost;
	}



	public void setMofTermLoanAppraisedCost(String mofTermLoanAppraisedCost) {
		this.mofTermLoanAppraisedCost = mofTermLoanAppraisedCost;
	}



	public String getMofTermLoanRemark() {
		return mofTermLoanRemark;
	}



	public void setMofTermLoanRemark(String mofTermLoanRemark) {
		this.mofTermLoanRemark = mofTermLoanRemark;
	}



	public String getMofAssisUnderUPFPIPProposedCost() {
		return mofAssisUnderUPFPIPProposedCost;
	}



	public void setMofAssisUnderUPFPIPProposedCost(String mofAssisUnderUPFPIPProposedCost) {
		this.mofAssisUnderUPFPIPProposedCost = mofAssisUnderUPFPIPProposedCost;
	}



	public String getMofAssisUnderUPFPIPAppraisedCost() {
		return mofAssisUnderUPFPIPAppraisedCost;
	}



	public void setMofAssisUnderUPFPIPAppraisedCost(String mofAssisUnderUPFPIPAppraisedCost) {
		this.mofAssisUnderUPFPIPAppraisedCost = mofAssisUnderUPFPIPAppraisedCost;
	}



	public String getMofAssisUnderUPFPIPRemark() {
		return mofAssisUnderUPFPIPRemark;
	}



	public void setMofAssisUnderUPFPIPRemark(String mofAssisUnderUPFPIPRemark) {
		this.mofAssisUnderUPFPIPRemark = mofAssisUnderUPFPIPRemark;
	}



	public String getGrantInAidFromMOFPIProposedCost() {
		return grantInAidFromMOFPIProposedCost;
	}



	public void setGrantInAidFromMOFPIProposedCost(String grantInAidFromMOFPIProposedCost) {
		this.grantInAidFromMOFPIProposedCost = grantInAidFromMOFPIProposedCost;
	}



	public String getGrantInAidFromMOFPIPAppraisedCost() {
		return grantInAidFromMOFPIPAppraisedCost;
	}



	public void setGrantInAidFromMOFPIPAppraisedCost(String grantInAidFromMOFPIPAppraisedCost) {
		this.grantInAidFromMOFPIPAppraisedCost = grantInAidFromMOFPIPAppraisedCost;
	}



	public String getGrantInAidFromMOFPIRemark() {
		return grantInAidFromMOFPIRemark;
	}



	public void setGrantInAidFromMOFPIRemark(String grantInAidFromMOFPIRemark) {
		this.grantInAidFromMOFPIRemark = grantInAidFromMOFPIRemark;
	}



	public String getMofTotalProposedCost() {
		return mofTotalProposedCost;
	}



	public void setMofTotalProposedCost(String mofTotalProposedCost) {
		this.mofTotalProposedCost = mofTotalProposedCost;
	}



	public String getMofTotalAppraisedCost() {
		return mofTotalAppraisedCost;
	}



	public void setMofTotalAppraisedCost(String mofTotalAppraisedCost) {
		this.mofTotalAppraisedCost = mofTotalAppraisedCost;
	}



	public String getMofTotalRemarks() {
		return mofTotalRemarks;
	}



	public void setMofTotalRemarks(String mofTotalRemarks) {
		this.mofTotalRemarks = mofTotalRemarks;
	}



	public String getMofOthersProposedCost() {
		return mofOthersProposedCost;
	}



	public void setMofOthersProposedCost(String mofOthersProposedCost) {
		this.mofOthersProposedCost = mofOthersProposedCost;
	}



	public String getMofOthersAppraisedCost() {
		return mofOthersAppraisedCost;
	}



	public void setMofOthersAppraisedCost(String mofOthersAppraisedCost) {
		this.mofOthersAppraisedCost = mofOthersAppraisedCost;
	}



	public String getMofOthersRemarks() {
		return mofOthersRemarks;
	}



	public void setMofOthersRemarks(String mofOthersRemarks) {
		this.mofOthersRemarks = mofOthersRemarks;
	}



	public String getIncorporationCertificateOfSPVDesc() {
		return incorporationCertificateOfSPVDesc;
	}



	public void setIncorporationCertificateOfSPVDesc(String incorporationCertificateOfSPVDesc) {
		this.incorporationCertificateOfSPVDesc = incorporationCertificateOfSPVDesc;
	}



	public String getIncorporationCertificateOfSPVRemarks() {
		return incorporationCertificateOfSPVRemarks;
	}



	public void setIncorporationCertificateOfSPVRemarks(String incorporationCertificateOfSPVRemarks) {
		this.incorporationCertificateOfSPVRemarks = incorporationCertificateOfSPVRemarks;
	}



	public String getMemorandumAndArticleDesc() {
		return memorandumAndArticleDesc;
	}



	public void setMemorandumAndArticleDesc(String memorandumAndArticleDesc) {
		this.memorandumAndArticleDesc = memorandumAndArticleDesc;
	}



	public String getMemorandumAndArticleRemarks() {
		return memorandumAndArticleRemarks;
	}



	public void setMemorandumAndArticleRemarks(String memorandumAndArticleRemarks) {
		this.memorandumAndArticleRemarks = memorandumAndArticleRemarks;
	}



	public String getRegisteredLandDeedDesc() {
		return registeredLandDeedDesc;
	}



	public void setRegisteredLandDeedDesc(String registeredLandDeedDesc) {
		this.registeredLandDeedDesc = registeredLandDeedDesc;
	}



	public String getRegisteredLandDeedRemarks() {
		return registeredLandDeedRemarks;
	}



	public void setRegisteredLandDeedRemarks(String registeredLandDeedRemarks) {
		this.registeredLandDeedRemarks = registeredLandDeedRemarks;
	}



	public String getCertificateOfPartnershipDeedDesc() {
		return certificateOfPartnershipDeedDesc;
	}



	public void setCertificateOfPartnershipDeedDesc(String certificateOfPartnershipDeedDesc) {
		this.certificateOfPartnershipDeedDesc = certificateOfPartnershipDeedDesc;
	}



	public String getCertificateOfPartnershipDeedRemarks() {
		return certificateOfPartnershipDeedRemarks;
	}



	public void setCertificateOfPartnershipDeedRemarks(String certificateOfPartnershipDeedRemarks) {
		this.certificateOfPartnershipDeedRemarks = certificateOfPartnershipDeedRemarks;
	}



	public String getRentAgreementDeedDesc() {
		return rentAgreementDeedDesc;
	}



	public void setRentAgreementDeedDesc(String rentAgreementDeedDesc) {
		this.rentAgreementDeedDesc = rentAgreementDeedDesc;
	}



	public String getRentAgreementDeedRemark() {
		return rentAgreementDeedRemark;
	}



	public void setRentAgreementDeedRemark(String rentAgreementDeedRemark) {
		this.rentAgreementDeedRemark = rentAgreementDeedRemark;
	}



	public String getKhasraKhatauniDesc() {
		return khasraKhatauniDesc;
	}



	public void setKhasraKhatauniDesc(String khasraKhatauniDesc) {
		this.khasraKhatauniDesc = khasraKhatauniDesc;
	}



	public String getKhasraKhatauniRemarks() {
		return khasraKhatauniRemarks;
	}



	public void setKhasraKhatauniRemarks(String khasraKhatauniRemarks) {
		this.khasraKhatauniRemarks = khasraKhatauniRemarks;
	}



	public String getLandUseCertificateDesc() {
		return landUseCertificateDesc;
	}



	public void setLandUseCertificateDesc(String landUseCertificateDesc) {
		this.landUseCertificateDesc = landUseCertificateDesc;
	}



	public String getLandUseCertificateRemark() {
		return landUseCertificateRemark;
	}



	public void setLandUseCertificateRemark(String landUseCertificateRemark) {
		this.landUseCertificateRemark = landUseCertificateRemark;
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



	public String getTermLoanSanctionLetterDesc() {
		return termLoanSanctionLetterDesc;
	}



	public void setTermLoanSanctionLetterDesc(String termLoanSanctionLetterDesc) {
		this.termLoanSanctionLetterDesc = termLoanSanctionLetterDesc;
	}



	public String getTermLoanSanctionLetterRemark() {
		return termLoanSanctionLetterRemark;
	}



	public void setTermLoanSanctionLetterRemark(String termLoanSanctionLetterRemark) {
		this.termLoanSanctionLetterRemark = termLoanSanctionLetterRemark;
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



	public String getSitePlanOfFoodParkDesc() {
		return sitePlanOfFoodParkDesc;
	}



	public void setSitePlanOfFoodParkDesc(String sitePlanOfFoodParkDesc) {
		this.sitePlanOfFoodParkDesc = sitePlanOfFoodParkDesc;
	}



	public String getSitePlanOfFoodParkRemark() {
		return sitePlanOfFoodParkRemark;
	}



	public void setSitePlanOfFoodParkRemark(String sitePlanOfFoodParkRemark) {
		this.sitePlanOfFoodParkRemark = sitePlanOfFoodParkRemark;
	}



	public String getCaCertificateForProjectCostDesc() {
		return caCertificateForProjectCostDesc;
	}



	public void setCaCertificateForProjectCostDesc(String caCertificateForProjectCostDesc) {
		this.caCertificateForProjectCostDesc = caCertificateForProjectCostDesc;
	}



	public String getCaCertificateForProjectCostRemark() {
		return caCertificateForProjectCostRemark;
	}



	public void setCaCertificateForProjectCostRemark(String caCertificateForProjectCostRemark) {
		this.caCertificateForProjectCostRemark = caCertificateForProjectCostRemark;
	}



	public String getAffidavitFromPromoterDesc() {
		return affidavitFromPromoterDesc;
	}



	public void setAffidavitFromPromoterDesc(String affidavitFromPromoterDesc) {
		this.affidavitFromPromoterDesc = affidavitFromPromoterDesc;
	}



	public String getAffidavitFromPromoterDescRemark() {
		return affidavitFromPromoterDescRemark;
	}



	public void setAffidavitFromPromoterDescRemark(String affidavitFromPromoterDescRemark) {
		this.affidavitFromPromoterDescRemark = affidavitFromPromoterDescRemark;
	}



	public String getBankCertificateNOCDesc() {
		return bankCertificateNOCDesc;
	}



	public void setBankCertificateNOCDesc(String bankCertificateNOCDesc) {
		this.bankCertificateNOCDesc = bankCertificateNOCDesc;
	}



	public String getBankCertificateNOCDescRemark() {
		return bankCertificateNOCDescRemark;
	}



	public void setBankCertificateNOCDescRemark(String bankCertificateNOCDescRemark) {
		this.bankCertificateNOCDescRemark = bankCertificateNOCDescRemark;
	}



	public String getSanctionLetterIssuedByMinistryDesc() {
		return sanctionLetterIssuedByMinistryDesc;
	}



	public void setSanctionLetterIssuedByMinistryDesc(String sanctionLetterIssuedByMinistryDesc) {
		this.sanctionLetterIssuedByMinistryDesc = sanctionLetterIssuedByMinistryDesc;
	}



	public String getSanctionLetterIssuedByMinistryRemark() {
		return sanctionLetterIssuedByMinistryRemark;
	}



	public void setSanctionLetterIssuedByMinistryRemark(String sanctionLetterIssuedByMinistryRemark) {
		this.sanctionLetterIssuedByMinistryRemark = sanctionLetterIssuedByMinistryRemark;
	}



	public String getAmountSanctionedByMOFPI() {
		return amountSanctionedByMOFPI;
	}



	public void setAmountSanctionedByMOFPI(String amountSanctionedByMOFPI) {
		this.amountSanctionedByMOFPI = amountSanctionedByMOFPI;
	}



	public String getEligibleAddCapitalSubsidy() {
		return eligibleAddCapitalSubsidy;
	}



	public void setEligibleAddCapitalSubsidy(String eligibleAddCapitalSubsidy) {
		this.eligibleAddCapitalSubsidy = eligibleAddCapitalSubsidy;
	}



	public String getMaxGrant() {
		return maxGrant;
	}



	public void setMaxGrant(String maxGrant) {
		this.maxGrant = maxGrant;
	}



	public String getAppAmtCapitalSubSidyRemarks() {
		return appAmtCapitalSubSidyRemarks;
	}



	public void setAppAmtCapitalSubSidyRemarks(String appAmtCapitalSubSidyRemarks) {
		this.appAmtCapitalSubSidyRemarks = appAmtCapitalSubSidyRemarks;
	}
	
	
}
