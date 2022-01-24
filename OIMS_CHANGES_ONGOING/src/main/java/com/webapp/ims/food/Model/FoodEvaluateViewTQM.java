package com.webapp.ims.food.Model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="foodevaluateviewtqm")
public class FoodEvaluateViewTQM implements Serializable{


	private static final long serialVersionUID = 1L;

	@Column(name="app_id")
	private String app_id;
	
	@Id
	@Column(name="unit_id")
	private String unitId;
	
	@Column(name="control_id")
	private String control_id;
	
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
	
	
	@Column(name="project_profile_app_id")
	private String projectProfileAppId;
	
	@Column(name="project_profile_app_id_observ")
	private String projectProfileAppIdObserv;
	
	@Column(name="proposal_submission_date")
	private String proposalSubmissionDate;
	
	@Column(name="proposal_submission_date_observ")
	private String proposalSubmissionDateObserv;
	
	@Column(name="name_of_the_firm")
	private String nameOfTheFirm;

	@Column(name="name_of_the_firm_obsev")
	private String nameOfTheFirmObsev;
	
	@Column(name="name_of_promoter")
	private String nameOfPromoter;

	@Column(name="name_of_promoter_observ")
	private String nameOfPromoterObserv;
	
	@Column(name="location_of_manufact_unit")
	private String locationOfManufactUnit;

	@Column(name="location_of_manufact_unit_observ")
	private String locationOfManufactUnitObserv;
	
	@Column(name="type_of_organization")
	private String typeOfOrganization;

	@Column(name="type_of_organization_observ")
	private String typeOfOrganizationObserv;
	
	@Column(name="pan_no")
	private String panNo;

	@Column(name="pan_no_observ")
	private String panNoObserv;
	
	@Column(name="registered_office_of_the_firm")
	private String registeredOfficeOfTheFirm;
	
	@Column(name="registered_office_of_the_firm_observ")
	private String registeredOfficeOfTheFirmObserv;
	
	@Column(name="registered_product_details")
	private String registeredProductDetails;
	
	@Column(name="registered_product_details_observ")
	private String registeredProductDetailsObserv;
	
	@Column(name="location_the_firm")
	private String locationTheFirm;
	
	@Column(name="location_the_firm_observ")
	private String locationTheFirmObserv;
	
	@Column(name="capacity_of_installed_plant_manufact_unit")
	private String capacityOfInstalledPlantManufactUnit;
	
	@Column(name="capacity_of_installed_plant_manufact_unit_observ")
	private String capacityOfInstalledPlantManufactUnitObserv;
	

	@Column(name="name_of_consult_agency")
	private String nameOfConsultAgency;
	
	@Column(name="name_of_consult_agency_observ")
	private String nameOfConsultAgencyObserv;
	
	@Column(name="fee_charged_by_certify_agency")
	private String feeChargedByCertifyAgency;
	
	@Column(name="fee_charged_by_certify_agency_observ")
	private String feeChargedByCertifyAgencyObserv;
	
	
	@Column(name="details_and_exp_of_consult")
	private String detailsAndExpOfConsult;
	
	@Column(name="details_and_exp_of_consult_observ")
	private String detailsAndExpOfConsultObserv;
	
	@Column(name="details_and_exp_of_certify_body")
	private String detailsAndExpOfCertifyBody;
	
	@Column(name="details_and_exp_of_certify_body_observ")
	private String detailsAndExpOfCertifyBodyObserv;
	
	@Column(name="details_of_accreditation_body")
	private String detailsOfAccreditationBody;
	
	@Column(name="details_of_accreditation_body_observ")
	private String detailsOfAccreditationBodyObserv;
	
	@Column(name="project_summary")
	private String projectSummary;
	
	//COST OF PROJECT AS PER BANK APPRAISAL (IN LACS)
	
	@Column(name="consultancy_fee_cost")
	private String consultancyFeeCost;
	@Column(name="consultancy_fee_cost_remarks")
	private String consultancyFeeRemarks;
	
	@Column(name="fee_charged_by_certifying_agency_cost")
	private String feeChargedBycertifyAgencyCost;
	@Column(name="fee_charged_by_certifying_agency_remarks")
	private String feeChargedBycertifyAgencyRemarks;
	
	@Column(name="plant_and_mach_with_reference_gap_cost")
	private String plantAndMachWithReferenceGAPCost;
	@Column(name="plant_and_mach_with_reference_gap_remarks")
	private String plantAndMachWithReferenceGAPRemarks;
	
	@Column(name="any_other_expenses_as_per_req_of_gmp_cost")
	private String anyOtherExpensesAsPerReqOfGMPCost;
	@Column(name="any_other_expenses_as_per_req_of_gmp_remarks")
	private String anyOtherExpensesAsPerReqOfGMPRemarks;
	
	@Column(name="patent_regis_fee_cost")
	private String patentRegisFeeCost;
	@Column(name="patent_regis_fee_remarks")
	private String patentRegisFeeRemarks;
	
	@Column(name="technical_civil_works_cost")
	private String technicalCivilWorksCost;
	
	//DETAILS OF PAYMENT FOR THE PROJECT-
	
	@Column(name="fee_and_testing_chargs_amt_in_lacs")
	private String feeAndTestingChargsAmtInLacs;
	@Column(name="fee_and_testing_chargs_tds")
	private String feeAndTestingChargsTDS;
	@Column(name="fee_and_testing_chargs_ttl_paid_amt")
	private String feeAndTestingChargsTtlPaidAmt;
	@Column(name="fee_and_testing_chargs_eligible_cost")
	private String feeAndTestingChargsEligibleCost;
	
	@Column(name="cunsultancy_fee_amt_in_lacs")
	private String cunsultancyFeeAmtInLacs;
	@Column(name="cunsultancy_fee_tds")
	private String cunsultancyFeeTDS;
	@Column(name="cunsultancy_fee_ttl_paid_amt")
	private String cunsultancyFeeTtlPaidAmt;
	@Column(name="cunsultancy_fee_eligible_cost")
	private String cunsultancyFeeEligibleCost;
	
	@Column(name="fee_charged_by_certi_agency_amt_in_lacs")
	private String 	feeChargedByCertiAgencyAmtInLacs;
	@Column(name="fee_charged_by_certi_agency_tds")
	private String feeChargedByCertiAgencyTDS;
	@Column(name="fee_charged_by_certi_agency_ttl_paid_amt")
	private String feeChargedByCertiAgencyTtlPaidAmt;
	@Column(name="fee_charged_by_certi_agency_eligible_cost")
	private String feeChargedByCertiAgencyEligibleCost;
	
	@Column(name="cost_of_plant_mach_amt_in_lacs")
	private String 	costOfPlantMachAmtInLacs;
	@Column(name="cost_of_plant_mach_tds")
	private String costOfPlantMachTDS;
	@Column(name="cost_of_plant_mach_ttl_paid_amt")
	private String costOfPlantMachTtlPaidAmt;
	@Column(name="cost_of_plant_mach_eligible_cost")
	private String costOfPlantMachEligibleCost;
	
	@Column(name="any_other_expences_amt_in_lacs")
	private String 	anyOtherExpencesAmtInLacs;
	@Column(name="any_other_expences_tds")
	private String anyOtherExpencesTDS;
	@Column(name="any_other_expences_ttl_paid_amt")
	private String anyOtherExpencesTtlPaidAmt;
	@Column(name="any_other_expences_eligible_cost")
	private String anyOtherExpencesEligibleCost;
	
	
	public String getFeeAndTestingChargsAmtInLacs() {
		return feeAndTestingChargsAmtInLacs;
	}

	public void setFeeAndTestingChargsAmtInLacs(String feeAndTestingChargsAmtInLacs) {
		this.feeAndTestingChargsAmtInLacs = feeAndTestingChargsAmtInLacs;
	}

	public String getFeeAndTestingChargsTDS() {
		return feeAndTestingChargsTDS;
	}

	public void setFeeAndTestingChargsTDS(String feeAndTestingChargsTDS) {
		this.feeAndTestingChargsTDS = feeAndTestingChargsTDS;
	}

	public String getFeeAndTestingChargsTtlPaidAmt() {
		return feeAndTestingChargsTtlPaidAmt;
	}

	public void setFeeAndTestingChargsTtlPaidAmt(String feeAndTestingChargsTtlPaidAmt) {
		this.feeAndTestingChargsTtlPaidAmt = feeAndTestingChargsTtlPaidAmt;
	}

	public String getFeeAndTestingChargsEligibleCost() {
		return feeAndTestingChargsEligibleCost;
	}

	public void setFeeAndTestingChargsEligibleCost(String feeAndTestingChargsEligibleCost) {
		this.feeAndTestingChargsEligibleCost = feeAndTestingChargsEligibleCost;
	}

	public String getCunsultancyFeeAmtInLacs() {
		return cunsultancyFeeAmtInLacs;
	}

	public void setCunsultancyFeeAmtInLacs(String cunsultancyFeeAmtInLacs) {
		this.cunsultancyFeeAmtInLacs = cunsultancyFeeAmtInLacs;
	}

	public String getCunsultancyFeeTDS() {
		return cunsultancyFeeTDS;
	}

	public void setCunsultancyFeeTDS(String cunsultancyFeeTDS) {
		this.cunsultancyFeeTDS = cunsultancyFeeTDS;
	}

	public String getCunsultancyFeeTtlPaidAmt() {
		return cunsultancyFeeTtlPaidAmt;
	}

	public void setCunsultancyFeeTtlPaidAmt(String cunsultancyFeeTtlPaidAmt) {
		this.cunsultancyFeeTtlPaidAmt = cunsultancyFeeTtlPaidAmt;
	}

	public String getCunsultancyFeeEligibleCost() {
		return cunsultancyFeeEligibleCost;
	}

	public void setCunsultancyFeeEligibleCost(String cunsultancyFeeEligibleCost) {
		this.cunsultancyFeeEligibleCost = cunsultancyFeeEligibleCost;
	}

	public String getFeeChargedByCertiAgencyAmtInLacs() {
		return feeChargedByCertiAgencyAmtInLacs;
	}

	public void setFeeChargedByCertiAgencyAmtInLacs(String feeChargedByCertiAgencyAmtInLacs) {
		this.feeChargedByCertiAgencyAmtInLacs = feeChargedByCertiAgencyAmtInLacs;
	}

	public String getFeeChargedByCertiAgencyTDS() {
		return feeChargedByCertiAgencyTDS;
	}

	public void setFeeChargedByCertiAgencyTDS(String feeChargedByCertiAgencyTDS) {
		this.feeChargedByCertiAgencyTDS = feeChargedByCertiAgencyTDS;
	}

	public String getFeeChargedByCertiAgencyTtlPaidAmt() {
		return feeChargedByCertiAgencyTtlPaidAmt;
	}

	public void setFeeChargedByCertiAgencyTtlPaidAmt(String feeChargedByCertiAgencyTtlPaidAmt) {
		this.feeChargedByCertiAgencyTtlPaidAmt = feeChargedByCertiAgencyTtlPaidAmt;
	}

	public String getFeeChargedByCertiAgencyEligibleCost() {
		return feeChargedByCertiAgencyEligibleCost;
	}

	public void setFeeChargedByCertiAgencyEligibleCost(String feeChargedByCertiAgencyEligibleCost) {
		this.feeChargedByCertiAgencyEligibleCost = feeChargedByCertiAgencyEligibleCost;
	}

	public String getCostOfPlantMachAmtInLacs() {
		return costOfPlantMachAmtInLacs;
	}

	public void setCostOfPlantMachAmtInLacs(String costOfPlantMachAmtInLacs) {
		this.costOfPlantMachAmtInLacs = costOfPlantMachAmtInLacs;
	}

	public String getCostOfPlantMachTDS() {
		return costOfPlantMachTDS;
	}

	public void setCostOfPlantMachTDS(String costOfPlantMachTDS) {
		this.costOfPlantMachTDS = costOfPlantMachTDS;
	}

	public String getCostOfPlantMachTtlPaidAmt() {
		return costOfPlantMachTtlPaidAmt;
	}

	public void setCostOfPlantMachTtlPaidAmt(String costOfPlantMachTtlPaidAmt) {
		this.costOfPlantMachTtlPaidAmt = costOfPlantMachTtlPaidAmt;
	}

	public String getCostOfPlantMachEligibleCost() {
		return costOfPlantMachEligibleCost;
	}

	public void setCostOfPlantMachEligibleCost(String costOfPlantMachEligibleCost) {
		this.costOfPlantMachEligibleCost = costOfPlantMachEligibleCost;
	}

	public String getAnyOtherExpencesAmtInLacs() {
		return anyOtherExpencesAmtInLacs;
	}

	public void setAnyOtherExpencesAmtInLacs(String anyOtherExpencesAmtInLacs) {
		this.anyOtherExpencesAmtInLacs = anyOtherExpencesAmtInLacs;
	}

	public String getAnyOtherExpencesTDS() {
		return anyOtherExpencesTDS;
	}

	public void setAnyOtherExpencesTDS(String anyOtherExpencesTDS) {
		this.anyOtherExpencesTDS = anyOtherExpencesTDS;
	}

	public String getAnyOtherExpencesTtlPaidAmt() {
		return anyOtherExpencesTtlPaidAmt;
	}

	public void setAnyOtherExpencesTtlPaidAmt(String anyOtherExpencesTtlPaidAmt) {
		this.anyOtherExpencesTtlPaidAmt = anyOtherExpencesTtlPaidAmt;
	}

	public String getAnyOtherExpencesEligibleCost() {
		return anyOtherExpencesEligibleCost;
	}

	public void setAnyOtherExpencesEligibleCost(String anyOtherExpencesEligibleCost) {
		this.anyOtherExpencesEligibleCost = anyOtherExpencesEligibleCost;
	}

	public String getTechnicalCivilWorksCost() {
		return technicalCivilWorksCost;
	}

	public void setTechnicalCivilWorksCost(String technicalCivilWorksCost) {
		this.technicalCivilWorksCost = technicalCivilWorksCost;
	}

	public String getTechnicalCivilWorksRemarks() {
		return technicalCivilWorksRemarks;
	}

	public void setTechnicalCivilWorksRemarks(String technicalCivilWorksRemarks) {
		this.technicalCivilWorksRemarks = technicalCivilWorksRemarks;
	}

	public String getOthersCost() {
		return othersCost;
	}

	public void setOthersCost(String othersCost) {
		this.othersCost = othersCost;
	}

	public String getOthersRemarks() {
		return othersRemarks;
	}

	public void setOthersRemarks(String othersRemarks) {
		this.othersRemarks = othersRemarks;
	}

	@Column(name="technical_civil_works_remarks")
	private String technicalCivilWorksRemarks;
	
	@Column(name="others_cost")
	private String othersCost;
	@Column(name="others_remarks")
	private String othersRemarks;
	
	@Column(name="total_cost")
	private String totalCost;
	@Column(name="total_remarks")
	private String totalRemarks;
	
	//MEANS OF FINANCE AS PER BANK APPRAISAL
	
	@Column(name="mof_equity_cost")
	private String mofEquityCost;
	@Column(name="mof_equity_remarks")
	private String mofEquityRemarks;
	
	
	@Column(name="mof_term_loan_cost")
	private String mofTermLoanCost;
	@Column(name="mof_term_loan_remark")
	private String mofTermLoanRemark;
	
	@Column(name="mof_working_capital_cost")
	private String mofWorkingCapitalCost ;
	@Column(name="mof_working_capital_remarks")
	private String mofWorkingCapitalRemark;
	
	@Column(name="mof_others_cost")
	private String mofOthersCost;
	@Column(name="mof_others_cost_remarks")
	private String mofOthersRemarks;
	
	
	@Column(name="mof_total_cost")
	private String mofTotalCost ;
	@Column(name="mof_total_remarks")
	private String mofTotalRemarks;
	
	//STATUS OF DOCUMENT :
	

	@Column(name="app_on_prescribed_format_cost")
	private String appOnPrescribedFormatCost;
	@Column(name="app_on_prescribed_format_remarks")
	private String appOnPrescribedFormatRemarks;
	
	@Column(name="detailed_project_report_cost")
	private String detailedProjectReportCost;
	@Column(name="detailed_project_report_remarks")
	private String detailedProjectReportRemarks;
	
	@Column(name="udyog_aadhar_cost")
	private String udyogAadharCost;
	@Column(name="udyog_aadhar_cost_remarks")
	private String udyogAadharCostRemarks;
	
	@Column(name="fssai_license_cost")
	private String fSSAILicenseCost;
	@Column(name="fssai_license_remarks")
	private String fSSAILicenseRemarks;
	
	@Column(name="bio_data_cost")
	private String bioDataCost;
	@Column(name="bio_data_remarks")
	private String bioDataRemark;
	
	@Column(name="quotation_by_consult_ost")
	private String quotationByConsultCost;
	@Column(name="quotation_by_consult_remarks")
	private String quotationByConsultRemarks;
	
	@Column(name="consult_regis_details_cost")
	private String consultRegisDetailsCost;
	@Column(name="consult_regis_details_remark")
	private String consultRegisDetailsRemark;
	
	@Column(name="quotation_fr_certi_body_cost")
	private String quotationFrCertiBodyCost;
	@Column(name="quotation_fr_certi_body_remarks")
	private String quotationFrCertiBodyRemark;
	
	@Column(name="certification_agency_cost")
	private String certificationAgencyCost;
	@Column(name="certification_agency_remarks")
	private String certificationAgencyRemark;
	
	@Column(name="details_of_plant_mach_as_per_gap_study_cost")
	private String detailsOfPlantMachineryAsPerGAPStudyCost;
	@Column(name="details_of_plant_mach_as_per_gap_study_remark")
	private String detailsOfPlantMachineryAsPerGAPStudyRemark;

	@Column(name="details_of_plant_mach_aquot_duly_cost")
	private String detailsOfPlantMachineryAquotDulyCost;
	@Column(name="details_of_plant_mach_aquot_duly_remark")
	private String detailsOfPlantMachineryAquotDulyRemark;

	@Column(name="details_of_tech_civil_works_cost")
	private String detailsOfTechCivilWorksCost;
	@Column(name="details_of_tech_civil_works_remarks")
	private String detailsOfTechCivilWorksRemark;
	
	@Column(name="gap_study_report_as_per_annux19_cost")
	private String gapStudyReportCostAsPerAnnux19Cost;
	@Column(name="gap_study_report_as_per_annux19_remarks")
	private String gapStudyReportAsPerAnnux19Remark;
	
	@Column(name="affidevit_as_per_annux20_cost")
	private String affidevitAsPerAnnux20Cost;
	@Column(name="affidevit_as_per_annux20_remarks")
	private String affidevitAsPerAnnux20CostRemark;
	
	@Column(name="imp_chedule_of_haccp_cost")
	private String impScheduleOfHACCPCost;
	@Column(name="imp_chedule_of_haccp_remark")
	private String impScheduleOfHACCPRemark;

	@Column(name="the_applicant_org_is_req_cost")
	private String theApplicantOrgIsReqCost;
	@Column(name="the_applicant_org_is_req_remarks")
	private String theApplicantOrgIsReqRemark;
	
	@Column(name="list_of_exist_plant_mach_cost")
	private String listOfExistPlantMachCost;
	@Column(name="list_of_exist_plant_mach_remarks")
	private String listOfExistPlantMachRemark;
	
	@Column(name="proces_of_manufact_cost")
	private String procesOfManufactCost;
	@Column(name="proces_of_manufact_remarks")
	private String procesOfManufactRemark;
	
	@Column(name="patent_certi_issued_by_competent_auth_cost")
	private String patentCertiIssuedByCompetentAuthCost;
	@Column(name="patent_certi_issued_by_competent_auth_remarks")
	private String patentCertiIssuedByCompetentAuthRemark;
	
	@Column(name="declaration_upload_cost")
	private String declarationUploadCost;
	@Column(name="declaration_upload_cost_remarks")
	private String declarationUploadRemark;
	
	//DETAILS OF PAYMENT FOR THE PROJECT-
	
	@Column(name="details_of_payment_sno")
	private transient String detailsOfPaymentSno;
	
	@Column(name="details_of_payment_details")
	private transient String detailsOfPaymentDetails;
	
	@Column(name="details_of_payment_amount")
	private transient String detailsOfPaymentamount;
	
	@Column(name="details_of_payment_tds")
	private transient String detailsOfPaymentTDS;
	
	
	@Column(name="details_of_payment_elig_cost")
	private transient String detailsOfPaymentEligCost;
	
	
	@Column(name="ttl_details_of_payment_elig_cost")
	private String ttlDetailsOfPaymentEligCost;
	
	@Column(name="details_of_payment_remarks")
	private String detailsOfPaymentRemarks;
	
	//ELIGIBLE GRANT AMOUNT:
	
	@Column(name="eligible_cost")
	private String eligibleCost;
	
	@Column(name="eligibility_of_grant_50_percent")
	private String eligibilityOfGrant50Percent;
	
	@Column(name="max_eligible_grant")
	private String maxEligibleGrant;
	
	@Column(name="eligible_grant_amount_remarks")
	private String eligibleGrantAmountRemarks;

	public String getApp_id() {
		return app_id;
	}

	public void setApp_id(String app_id) {
		this.app_id = app_id;
	}

	public String getUnitId() {
		return unitId;
	}

	public void setUnitId(String unitId) {
		this.unitId = unitId;
	}

	public String getControl_id() {
		return control_id;
	}

	public void setControl_id(String control_id) {
		this.control_id = control_id;
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

	public String getNameOfTheFirm() {
		return nameOfTheFirm;
	}

	public void setNameOfTheFirm(String nameOfTheFirm) {
		this.nameOfTheFirm = nameOfTheFirm;
	}

	public String getNameOfTheFirmObsev() {
		return nameOfTheFirmObsev;
	}

	public void setNameOfTheFirmObsev(String nameOfTheFirmObsev) {
		this.nameOfTheFirmObsev = nameOfTheFirmObsev;
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

	public String getLocationOfManufactUnit() {
		return locationOfManufactUnit;
	}

	public void setLocationOfManufactUnit(String locationOfManufactUnit) {
		this.locationOfManufactUnit = locationOfManufactUnit;
	}

	public String getLocationOfManufactUnitObserv() {
		return locationOfManufactUnitObserv;
	}

	public void setLocationOfManufactUnitObserv(String locationOfManufactUnitObserv) {
		this.locationOfManufactUnitObserv = locationOfManufactUnitObserv;
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

	public String getRegisteredOfficeOfTheFirm() {
		return registeredOfficeOfTheFirm;
	}

	public void setRegisteredOfficeOfTheFirm(String registeredOfficeOfTheFirm) {
		this.registeredOfficeOfTheFirm = registeredOfficeOfTheFirm;
	}

	public String getRegisteredOfficeOfTheFirmObserv() {
		return registeredOfficeOfTheFirmObserv;
	}

	public void setRegisteredOfficeOfTheFirmObserv(String registeredOfficeOfTheFirmObserv) {
		this.registeredOfficeOfTheFirmObserv = registeredOfficeOfTheFirmObserv;
	}

	public String getRegisteredProductDetails() {
		return registeredProductDetails;
	}

	public void setRegisteredProductDetails(String registeredProductDetails) {
		this.registeredProductDetails = registeredProductDetails;
	}

	public String getRegisteredProductDetailsObserv() {
		return registeredProductDetailsObserv;
	}

	public void setRegisteredProductDetailsObserv(String registeredProductDetailsObserv) {
		this.registeredProductDetailsObserv = registeredProductDetailsObserv;
	}

	public String getLocationTheFirm() {
		return locationTheFirm;
	}

	public void setLocationTheFirm(String locationTheFirm) {
		this.locationTheFirm = locationTheFirm;
	}

	public String getLocationTheFirmObserv() {
		return locationTheFirmObserv;
	}

	public void setLocationTheFirmObserv(String locationTheFirmObserv) {
		this.locationTheFirmObserv = locationTheFirmObserv;
	}

	public String getCapacityOfInstalledPlantManufactUnit() {
		return capacityOfInstalledPlantManufactUnit;
	}

	public void setCapacityOfInstalledPlantManufactUnit(String capacityOfInstalledPlantManufactUnit) {
		this.capacityOfInstalledPlantManufactUnit = capacityOfInstalledPlantManufactUnit;
	}

	public String getCapacityOfInstalledPlantManufactUnitObserv() {
		return capacityOfInstalledPlantManufactUnitObserv;
	}

	public void setCapacityOfInstalledPlantManufactUnitObserv(String capacityOfInstalledPlantManufactUnitObserv) {
		this.capacityOfInstalledPlantManufactUnitObserv = capacityOfInstalledPlantManufactUnitObserv;
	}

	public String getNameOfConsultAgency() {
		return nameOfConsultAgency;
	}

	public void setNameOfConsultAgency(String nameOfConsultAgency) {
		this.nameOfConsultAgency = nameOfConsultAgency;
	}

	public String getNameOfConsultAgencyObserv() {
		return nameOfConsultAgencyObserv;
	}

	public void setNameOfConsultAgencyObserv(String nameOfConsultAgencyObserv) {
		this.nameOfConsultAgencyObserv = nameOfConsultAgencyObserv;
	}

	public String getFeeChargedByCertifyAgency() {
		return feeChargedByCertifyAgency;
	}

	public void setFeeChargedByCertifyAgency(String feeChargedByCertifyAgency) {
		this.feeChargedByCertifyAgency = feeChargedByCertifyAgency;
	}

	public String getFeeChargedByCertifyAgencyObserv() {
		return feeChargedByCertifyAgencyObserv;
	}

	public void setFeeChargedByCertifyAgencyObserv(String feeChargedByCertifyAgencyObserv) {
		this.feeChargedByCertifyAgencyObserv = feeChargedByCertifyAgencyObserv;
	}

	public String getDetailsAndExpOfConsult() {
		return detailsAndExpOfConsult;
	}

	public void setDetailsAndExpOfConsult(String detailsAndExpOfConsult) {
		this.detailsAndExpOfConsult = detailsAndExpOfConsult;
	}

	public String getDetailsAndExpOfConsultObserv() {
		return detailsAndExpOfConsultObserv;
	}

	public void setDetailsAndExpOfConsultObserv(String detailsAndExpOfConsultObserv) {
		this.detailsAndExpOfConsultObserv = detailsAndExpOfConsultObserv;
	}

	public String getDetailsAndExpOfCertifyBody() {
		return detailsAndExpOfCertifyBody;
	}

	public void setDetailsAndExpOfCertifyBody(String detailsAndExpOfCertifyBody) {
		this.detailsAndExpOfCertifyBody = detailsAndExpOfCertifyBody;
	}

	public String getDetailsAndExpOfCertifyBodyObserv() {
		return detailsAndExpOfCertifyBodyObserv;
	}

	public void setDetailsAndExpOfCertifyBodyObserv(String detailsAndExpOfCertifyBodyObserv) {
		this.detailsAndExpOfCertifyBodyObserv = detailsAndExpOfCertifyBodyObserv;
	}

	public String getDetailsOfAccreditationBody() {
		return detailsOfAccreditationBody;
	}

	public void setDetailsOfAccreditationBody(String detailsOfAccreditationBody) {
		this.detailsOfAccreditationBody = detailsOfAccreditationBody;
	}

	public String getDetailsOfAccreditationBodyObserv() {
		return detailsOfAccreditationBodyObserv;
	}

	public void setDetailsOfAccreditationBodyObserv(String detailsOfAccreditationBodyObserv) {
		this.detailsOfAccreditationBodyObserv = detailsOfAccreditationBodyObserv;
	}

	public String getProjectSummary() {
		return projectSummary;
	}

	public void setProjectSummary(String projectSummary) {
		this.projectSummary = projectSummary;
	}

	public String getConsultancyFeeCost() {
		return consultancyFeeCost;
	}

	public void setConsultancyFeeCost(String consultancyFeeCost) {
		this.consultancyFeeCost = consultancyFeeCost;
	}

	public String getConsultancyFeeRemarks() {
		return consultancyFeeRemarks;
	}

	public void setConsultancyFeeRemarks(String consultancyFeeRemarks) {
		this.consultancyFeeRemarks = consultancyFeeRemarks;
	}

	public String getFeeChargedBycertifyAgencyCost() {
		return feeChargedBycertifyAgencyCost;
	}

	public void setFeeChargedBycertifyAgencyCost(String feeChargedBycertifyAgencyCost) {
		this.feeChargedBycertifyAgencyCost = feeChargedBycertifyAgencyCost;
	}

	public String getFeeChargedBycertifyAgencyRemarks() {
		return feeChargedBycertifyAgencyRemarks;
	}

	public void setFeeChargedBycertifyAgencyRemarks(String feeChargedBycertifyAgencyRemarks) {
		this.feeChargedBycertifyAgencyRemarks = feeChargedBycertifyAgencyRemarks;
	}

	public String getPlantAndMachWithReferenceGAPCost() {
		return plantAndMachWithReferenceGAPCost;
	}

	public void setPlantAndMachWithReferenceGAPCost(String plantAndMachWithReferenceGAPCost) {
		this.plantAndMachWithReferenceGAPCost = plantAndMachWithReferenceGAPCost;
	}

	public String getPlantAndMachWithReferenceGAPRemarks() {
		return plantAndMachWithReferenceGAPRemarks;
	}

	public void setPlantAndMachWithReferenceGAPRemarks(String plantAndMachWithReferenceGAPRemarks) {
		this.plantAndMachWithReferenceGAPRemarks = plantAndMachWithReferenceGAPRemarks;
	}

	public String getAnyOtherExpensesAsPerReqOfGMPCost() {
		return anyOtherExpensesAsPerReqOfGMPCost;
	}

	public void setAnyOtherExpensesAsPerReqOfGMPCost(String anyOtherExpensesAsPerReqOfGMPCost) {
		this.anyOtherExpensesAsPerReqOfGMPCost = anyOtherExpensesAsPerReqOfGMPCost;
	}

	public String getAnyOtherExpensesAsPerReqOfGMPRemarks() {
		return anyOtherExpensesAsPerReqOfGMPRemarks;
	}

	public void setAnyOtherExpensesAsPerReqOfGMPRemarks(String anyOtherExpensesAsPerReqOfGMPRemarks) {
		this.anyOtherExpensesAsPerReqOfGMPRemarks = anyOtherExpensesAsPerReqOfGMPRemarks;
	}

	public String getPatentRegisFeeCost() {
		return patentRegisFeeCost;
	}

	public void setPatentRegisFeeCost(String patentRegisFeeCost) {
		this.patentRegisFeeCost = patentRegisFeeCost;
	}

	public String getPatentRegisFeeRemarks() {
		return patentRegisFeeRemarks;
	}

	public void setPatentRegisFeeRemarks(String patentRegisFeeRemarks) {
		this.patentRegisFeeRemarks = patentRegisFeeRemarks;
	}

	public String getTotalCost() {
		return totalCost;
	}

	public void setTotalCost(String totalCost) {
		this.totalCost = totalCost;
	}

	public String getTotalRemarks() {
		return totalRemarks;
	}

	public void setTotalRemarks(String totalRemarks) {
		this.totalRemarks = totalRemarks;
	}

	public String getMofEquityCost() {
		return mofEquityCost;
	}

	public void setMofEquityCost(String mofEquityCost) {
		this.mofEquityCost = mofEquityCost;
	}

	public String getMofEquityRemarks() {
		return mofEquityRemarks;
	}

	public void setMofEquityRemarks(String mofEquityRemarks) {
		this.mofEquityRemarks = mofEquityRemarks;
	}

	public String getMofTermLoanCost() {
		return mofTermLoanCost;
	}

	public void setMofTermLoanCost(String mofTermLoanCost) {
		this.mofTermLoanCost = mofTermLoanCost;
	}

	public String getMofTermLoanRemark() {
		return mofTermLoanRemark;
	}

	public void setMofTermLoanRemark(String mofTermLoanRemark) {
		this.mofTermLoanRemark = mofTermLoanRemark;
	}

	public String getMofWorkingCapitalCost() {
		return mofWorkingCapitalCost;
	}

	public void setMofWorkingCapitalCost(String mofWorkingCapitalCost) {
		this.mofWorkingCapitalCost = mofWorkingCapitalCost;
	}

	public String getMofWorkingCapitalRemark() {
		return mofWorkingCapitalRemark;
	}

	public void setMofWorkingCapitalRemark(String mofWorkingCapitalRemark) {
		this.mofWorkingCapitalRemark = mofWorkingCapitalRemark;
	}

	public String getMofOthersCost() {
		return mofOthersCost;
	}

	public void setMofOthersCost(String mofOthersCost) {
		this.mofOthersCost = mofOthersCost;
	}

	public String getMofOthersRemarks() {
		return mofOthersRemarks;
	}

	public void setMofOthersRemarks(String mofOthersRemarks) {
		this.mofOthersRemarks = mofOthersRemarks;
	}

	public String getMofTotalCost() {
		return mofTotalCost;
	}

	public void setMofTotalCost(String mofTotalCost) {
		this.mofTotalCost = mofTotalCost;
	}

	public String getMofTotalRemarks() {
		return mofTotalRemarks;
	}

	public void setMofTotalRemarks(String mofTotalRemarks) {
		this.mofTotalRemarks = mofTotalRemarks;
	}

	public String getAppOnPrescribedFormatCost() {
		return appOnPrescribedFormatCost;
	}

	public void setAppOnPrescribedFormatCost(String appOnPrescribedFormatCost) {
		this.appOnPrescribedFormatCost = appOnPrescribedFormatCost;
	}

	public String getAppOnPrescribedFormatRemarks() {
		return appOnPrescribedFormatRemarks;
	}

	public void setAppOnPrescribedFormatRemarks(String appOnPrescribedFormatRemarks) {
		this.appOnPrescribedFormatRemarks = appOnPrescribedFormatRemarks;
	}

	public String getDetailedProjectReportCost() {
		return detailedProjectReportCost;
	}

	public void setDetailedProjectReportCost(String detailedProjectReportCost) {
		this.detailedProjectReportCost = detailedProjectReportCost;
	}

	public String getDetailedProjectReportRemarks() {
		return detailedProjectReportRemarks;
	}

	public void setDetailedProjectReportRemarks(String detailedProjectReportRemarks) {
		this.detailedProjectReportRemarks = detailedProjectReportRemarks;
	}

	public String getUdyogAadharCost() {
		return udyogAadharCost;
	}

	public void setUdyogAadharCost(String udyogAadharCost) {
		this.udyogAadharCost = udyogAadharCost;
	}

	public String getUdyogAadharCostRemarks() {
		return udyogAadharCostRemarks;
	}

	public void setUdyogAadharCostRemarks(String udyogAadharCostRemarks) {
		this.udyogAadharCostRemarks = udyogAadharCostRemarks;
	}

	public String getfSSAILicenseCost() {
		return fSSAILicenseCost;
	}

	public void setfSSAILicenseCost(String fSSAILicenseCost) {
		this.fSSAILicenseCost = fSSAILicenseCost;
	}

	public String getfSSAILicenseRemarks() {
		return fSSAILicenseRemarks;
	}

	public void setfSSAILicenseRemarks(String fSSAILicenseRemarks) {
		this.fSSAILicenseRemarks = fSSAILicenseRemarks;
	}

	
	public String getBioDataCost() {
		return bioDataCost;
	}

	public void setBioDataCost(String bioDataCost) {
		this.bioDataCost = bioDataCost;
	}

	public String getBioDataRemark() {
		return bioDataRemark;
	}

	public void setBioDataRemark(String bioDataRemark) {
		this.bioDataRemark = bioDataRemark;
	}

	public String getQuotationByConsultCost() {
		return quotationByConsultCost;
	}

	public void setQuotationByConsultCost(String quotationByConsultCost) {
		this.quotationByConsultCost = quotationByConsultCost;
	}

	public String getQuotationByConsultRemarks() {
		return quotationByConsultRemarks;
	}

	public void setQuotationByConsultRemarks(String quotationByConsultRemarks) {
		this.quotationByConsultRemarks = quotationByConsultRemarks;
	}

	public String getConsultRegisDetailsCost() {
		return consultRegisDetailsCost;
	}

	public void setConsultRegisDetailsCost(String consultRegisDetailsCost) {
		this.consultRegisDetailsCost = consultRegisDetailsCost;
	}

	public String getConsultRegisDetailsRemark() {
		return consultRegisDetailsRemark;
	}

	public void setConsultRegisDetailsRemark(String consultRegisDetailsRemark) {
		this.consultRegisDetailsRemark = consultRegisDetailsRemark;
	}

	public String getQuotationFrCertiBodyCost() {
		return quotationFrCertiBodyCost;
	}

	public void setQuotationFrCertiBodyCost(String quotationFrCertiBodyCost) {
		this.quotationFrCertiBodyCost = quotationFrCertiBodyCost;
	}

	public String getQuotationFrCertiBodyRemark() {
		return quotationFrCertiBodyRemark;
	}

	public void setQuotationFrCertiBodyRemark(String quotationFrCertiBodyRemark) {
		this.quotationFrCertiBodyRemark = quotationFrCertiBodyRemark;
	}

	public String getCertificationAgencyCost() {
		return certificationAgencyCost;
	}

	public void setCertificationAgencyCost(String certificationAgencyCost) {
		this.certificationAgencyCost = certificationAgencyCost;
	}

	public String getCertificationAgencyRemark() {
		return certificationAgencyRemark;
	}

	public void setCertificationAgencyRemark(String certificationAgencyRemark) {
		this.certificationAgencyRemark = certificationAgencyRemark;
	}

	public String getDetailsOfPlantMachineryAsPerGAPStudyCost() {
		return detailsOfPlantMachineryAsPerGAPStudyCost;
	}

	public void setDetailsOfPlantMachineryAsPerGAPStudyCost(String detailsOfPlantMachineryAsPerGAPStudyCost) {
		this.detailsOfPlantMachineryAsPerGAPStudyCost = detailsOfPlantMachineryAsPerGAPStudyCost;
	}

	

	public String getDetailsOfPlantMachineryAsPerGAPStudyRemark() {
		return detailsOfPlantMachineryAsPerGAPStudyRemark;
	}

	public void setDetailsOfPlantMachineryAsPerGAPStudyRemark(String detailsOfPlantMachineryAsPerGAPStudyRemark) {
		this.detailsOfPlantMachineryAsPerGAPStudyRemark = detailsOfPlantMachineryAsPerGAPStudyRemark;
	}

	public String getDetailsOfPlantMachineryAquotDulyCost() {
		return detailsOfPlantMachineryAquotDulyCost;
	}

	public void setDetailsOfPlantMachineryAquotDulyCost(String detailsOfPlantMachineryAquotDulyCost) {
		this.detailsOfPlantMachineryAquotDulyCost = detailsOfPlantMachineryAquotDulyCost;
	}

	public String getDetailsOfPlantMachineryAquotDulyRemark() {
		return detailsOfPlantMachineryAquotDulyRemark;
	}

	public void setDetailsOfPlantMachineryAquotDulyRemark(String detailsOfPlantMachineryAquotDulyRemark) {
		this.detailsOfPlantMachineryAquotDulyRemark = detailsOfPlantMachineryAquotDulyRemark;
	}

	public String getDetailsOfTechCivilWorksCost() {
		return detailsOfTechCivilWorksCost;
	}

	public void setDetailsOfTechCivilWorksCost(String detailsOfTechCivilWorksCost) {
		this.detailsOfTechCivilWorksCost = detailsOfTechCivilWorksCost;
	}

	public String getDetailsOfTechCivilWorksRemark() {
		return detailsOfTechCivilWorksRemark;
	}

	public void setDetailsOfTechCivilWorksRemark(String detailsOfTechCivilWorksRemark) {
		this.detailsOfTechCivilWorksRemark = detailsOfTechCivilWorksRemark;
	}

	public String getGapStudyReportCostAsPerAnnux19Cost() {
		return gapStudyReportCostAsPerAnnux19Cost;
	}

	public void setGapStudyReportCostAsPerAnnux19Cost(String gapStudyReportCostAsPerAnnux19Cost) {
		this.gapStudyReportCostAsPerAnnux19Cost = gapStudyReportCostAsPerAnnux19Cost;
	}

	public String getGapStudyReportAsPerAnnux19Remark() {
		return gapStudyReportAsPerAnnux19Remark;
	}

	public void setGapStudyReportAsPerAnnux19Remark(String gapStudyReportAsPerAnnux19Remark) {
		this.gapStudyReportAsPerAnnux19Remark = gapStudyReportAsPerAnnux19Remark;
	}

	public String getImpScheduleOfHACCPCost() {
		return impScheduleOfHACCPCost;
	}

	public void setImpScheduleOfHACCPCost(String impScheduleOfHACCPCost) {
		this.impScheduleOfHACCPCost = impScheduleOfHACCPCost;
	}

	public String getImpScheduleOfHACCPRemark() {
		return impScheduleOfHACCPRemark;
	}

	public void setImpScheduleOfHACCPRemark(String impScheduleOfHACCPRemark) {
		this.impScheduleOfHACCPRemark = impScheduleOfHACCPRemark;
	}

	public String getTheApplicantOrgIsReqCost() {
		return theApplicantOrgIsReqCost;
	}

	public void setTheApplicantOrgIsReqCost(String theApplicantOrgIsReqCost) {
		this.theApplicantOrgIsReqCost = theApplicantOrgIsReqCost;
	}

	public String getTheApplicantOrgIsReqRemark() {
		return theApplicantOrgIsReqRemark;
	}

	public void setTheApplicantOrgIsReqRemark(String theApplicantOrgIsReqRemark) {
		this.theApplicantOrgIsReqRemark = theApplicantOrgIsReqRemark;
	}

	public String getListOfExistPlantMachCost() {
		return listOfExistPlantMachCost;
	}

	public void setListOfExistPlantMachCost(String listOfExistPlantMachCost) {
		this.listOfExistPlantMachCost = listOfExistPlantMachCost;
	}

	public String getListOfExistPlantMachRemark() {
		return listOfExistPlantMachRemark;
	}

	public void setListOfExistPlantMachRemark(String listOfExistPlantMachRemark) {
		this.listOfExistPlantMachRemark = listOfExistPlantMachRemark;
	}

	public String getProcesOfManufactCost() {
		return procesOfManufactCost;
	}

	public void setProcesOfManufactCost(String procesOfManufactCost) {
		this.procesOfManufactCost = procesOfManufactCost;
	}

	public String getProcesOfManufactRemark() {
		return procesOfManufactRemark;
	}

	public void setProcesOfManufactRemark(String procesOfManufactRemark) {
		this.procesOfManufactRemark = procesOfManufactRemark;
	}

	public String getPatentCertiIssuedByCompetentAuthCost() {
		return patentCertiIssuedByCompetentAuthCost;
	}

	public void setPatentCertiIssuedByCompetentAuthCost(String patentCertiIssuedByCompetentAuthCost) {
		this.patentCertiIssuedByCompetentAuthCost = patentCertiIssuedByCompetentAuthCost;
	}

	public String getPatentCertiIssuedByCompetentAuthRemark() {
		return patentCertiIssuedByCompetentAuthRemark;
	}

	public void setPatentCertiIssuedByCompetentAuthRemark(String patentCertiIssuedByCompetentAuthRemark) {
		this.patentCertiIssuedByCompetentAuthRemark = patentCertiIssuedByCompetentAuthRemark;
	}

	public String getDeclarationUploadCost() {
		return declarationUploadCost;
	}

	public void setDeclarationUploadCost(String declarationUploadCost) {
		this.declarationUploadCost = declarationUploadCost;
	}

	public String getDeclarationUploadRemark() {
		return declarationUploadRemark;
	}

	public void setDeclarationUploadRemark(String declarationUploadRemark) {
		this.declarationUploadRemark = declarationUploadRemark;
	}

	

	public String getDetailsOfPaymentDetails() {
		return detailsOfPaymentDetails;
	}

	public void setDetailsOfPaymentDetails(String detailsOfPaymentDetails) {
		this.detailsOfPaymentDetails = detailsOfPaymentDetails;
	}

	public String getDetailsOfPaymentamount() {
		return detailsOfPaymentamount;
	}

	public void setDetailsOfPaymentamount(String detailsOfPaymentamount) {
		this.detailsOfPaymentamount = detailsOfPaymentamount;
	}

	public String getDetailsOfPaymentTDS() {
		return detailsOfPaymentTDS;
	}

	public void setDetailsOfPaymentTDS(String detailsOfPaymentTDS) {
		this.detailsOfPaymentTDS = detailsOfPaymentTDS;
	}

	public String getDetailsOfPaymentEligCost() {
		return detailsOfPaymentEligCost;
	}

	public void setDetailsOfPaymentEligCost(String detailsOfPaymentEligCost) {
		this.detailsOfPaymentEligCost = detailsOfPaymentEligCost;
	}

	

	public String getDetailsOfPaymentSno() {
		return detailsOfPaymentSno;
	}

	public void setDetailsOfPaymentSno(String detailsOfPaymentSno) {
		this.detailsOfPaymentSno = detailsOfPaymentSno;
	}

	public String getTtlDetailsOfPaymentEligCost() {
		return ttlDetailsOfPaymentEligCost;
	}

	public void setTtlDetailsOfPaymentEligCost(String ttlDetailsOfPaymentEligCost) {
		this.ttlDetailsOfPaymentEligCost = ttlDetailsOfPaymentEligCost;
	}

	public String getDetailsOfPaymentRemarks() {
		return detailsOfPaymentRemarks;
	}

	public void setDetailsOfPaymentRemarks(String detailsOfPaymentRemarks) {
		this.detailsOfPaymentRemarks = detailsOfPaymentRemarks;
	}

	public String getEligibleCost() {
		return eligibleCost;
	}

	public void setEligibleCost(String eligibleCost) {
		this.eligibleCost = eligibleCost;
	}

	public String getEligibilityOfGrant50Percent() {
		return eligibilityOfGrant50Percent;
	}

	public void setEligibilityOfGrant50Percent(String eligibilityOfGrant50Percent) {
		this.eligibilityOfGrant50Percent = eligibilityOfGrant50Percent;
	}

	public String getMaxEligibleGrant() {
		return maxEligibleGrant;
	}

	public void setMaxEligibleGrant(String maxEligibleGrant) {
		this.maxEligibleGrant = maxEligibleGrant;
	}

	public String getEligibleGrantAmountRemarks() {
		return eligibleGrantAmountRemarks;
	}

	public void setEligibleGrantAmountRemarks(String eligibleGrantAmountRemarks) {
		this.eligibleGrantAmountRemarks = eligibleGrantAmountRemarks;
	}

	public String getAffidevitAsPerAnnux20Cost() {
		return affidevitAsPerAnnux20Cost;
	}

	public void setAffidevitAsPerAnnux20Cost(String affidevitAsPerAnnux20Cost) {
		this.affidevitAsPerAnnux20Cost = affidevitAsPerAnnux20Cost;
	}

	public String getAffidevitAsPerAnnux20CostRemark() {
		return affidevitAsPerAnnux20CostRemark;
	}

	public void setAffidevitAsPerAnnux20CostRemark(String affidevitAsPerAnnux20CostRemark) {
		this.affidevitAsPerAnnux20CostRemark = affidevitAsPerAnnux20CostRemark;
	}
	
	
	
}
