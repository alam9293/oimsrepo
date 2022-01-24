package com.webapp.ims.food.Model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="foodevaluationbankabledpr")
public class FoodBankableDPR implements Serializable{
	

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
	
	@Column(name="details_of_institution")
	private String detailsOfInstitution;
	
	@Column(name="details_of_institution_observ")
	private String detailsOfInstitutionObserv;
	
	@Column(name="details_of_export_import")
	private String detailsOfExportImport;
	
	

	@Column(name="details_of_export_import_observ")
	private String detailsOfExportImportObserv;
	
	@Column(name="project_summary")
	private String projectSummary;
	
	//COST OF PROJECT AS PER BANK APPRAISAL (IN LACS)
	
	@Column(name="consultancy_fee_cost")
	private String consultancyFeeCost;
	@Column(name="consultancy_fee_cost_remarks")
	private String consultancyFeeRemarks;
	
	@Column(name="fee_charged_by_institution_cost")
	private String feeChargedByInstitutionCost;
	@Column(name="fee_charged_by_institution_remarks")
	private String feeChargedByInstitutionRemarks;
	
	@Column(name="plant_mach_required_cost")
	private String plantMachineryRequiredCost;
	@Column(name="plant_mach_required_cost_remark")
	private String plantMachineryRequiredRemarks;
	
	@Column(name="technical_civil_work_cost")
	private String technicalCivilWorkCost;
	@Column(name="technical_civil_work_remarks")
	private String technicalCivilWorkRemarks;
	
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
	
	@Column(name="mof_assistance_from_other_sources_cost")
	private String mofAssistanceFromOtherSourcesCost ;
	@Column(name="mof_assistance_from_other_sources_remarks")
	private String mofAssistanceFromOtherSourcesRemark;
	
	@Column(name="mof_others_cost")
	private String mofOthersCost;
	@Column(name="mof_others_cost_remarks")
	private String mofOthersCostRemarks;
	
	
	@Column(name="mof_total_cost")
	private String mofTotalCost ;
	@Column(name="mof_total_remarks")
	private String mofTotalRemarks;
	
	//STATUS OF DOCUMENT :
	

	@Column(name="app_on_prescribed_format_desc")
	private String appOnPrescribedFormatDesc;
	@Column(name="app_on_prescribed_format_remarks")
	private String appOnPrescribedFormatRemarks;
	
	@Column(name="detailed_project_report_desc")
	private String detailedProjectReportDesc;
	@Column(name="detailed_project_report_remarks")
	private String detailedProjectReportRemarks;
	
	@Column(name="certificate_of_incorporation_desc")
	private String certificateOfIncorporationDesc;
	@Column(name="certificate_of_incorporation_remarks")
	private String certificateOfIncorporationRemarks;
	
	@Column(name="certificate_of_partnership_deed_desc")
	private String certificateOfPartnershipDeedDesc;
	@Column(name="certificate_of_partnership_deed_remarks")
	private String certificateOfPartnershipDeedRemarks;
	
	@Column(name="promoters_bio_data_desc")
	private String promotersBioDataDesc;
	@Column(name="promoters_bio_data_remarks")
	private String promotersBioDataRemark;
	
	@Column(name="financial_statements_for_last_3yrs_desc")
	private String financialStatementsForLast3YrsDesc;
	@Column(name="financial_statements_for_last_3yrs_remarks")
	private String financialStatementsForLast3YrsRemarks;
	
	@Column(name="receipt_of_payment_cost")
	private String receiptOfPaymentCostDesc;
	@Column(name="receipt_of_payment_remark")
	private String receiptOfPaymentRemark;
	
	@Column(name="layout_of_plant_desc")
	private String layoutOfPlantDesc;
	@Column(name="layout_of_plant_remarks")
	private String layoutOfPlantRemark;
	
	@Column(name="details_of_plant_mach_ce_mech_desc")
	private String detailsOfPlantMachineryCEMechDesc;
	@Column(name="details_of_plant_mach_ce_mech_remarks")
	private String detailsOfPlantMachineryCEMechRemark;
	
	@Column(name="details_of_tech_civil_desc")
	private String detailsOfTechnicalCivilDesc;
	@Column(name="details_of_tech_civil_remark")
	private String detailsOfTechnicalCivilRemark;
	
	//DETAILS OF PAYMENT FOR PREPARATION OF BANKABLE PROJECT-
	
	@Column(name="details_of_payment_bankable_boucher_no")
	private transient String detailsOfPaymentBankableBoucherNo;
	
	@Column(name="details_of_payment_bankable_boucher_date")
	private transient String detailsOfPaymentBankableBoucherDate;
	
	@Column(name="details_of_payment_bankable_project_amount")
	private transient String detailsOfPaymentBankableProjectAmount;
	

	@Column(name="details_of_payment_bankable_project_tds")
	private transient  String detailsOfPaymentBankableProjectTDS;
	
	@Column(name="details_of_payment_bankable_total_amount_paid")
	private transient String detailsOfPaymentBankableTotalAmountPaid;
	
	@Column(name="details_of_payment_bankable_project_elig_cost")
	private transient String[] detailsOfPaymentBankableProjectEligCost;
	
	
	@Column(name="ttl_details_of_payment_bankable_project")
	private String ttlDetailsOfPaymentBankableProject;
	
	@Column(name="details_of_payment_bankable_project_remarks")
	private String detailsOfPaymentBankableProjectRemarks;
	
	//ELIGIBLE GRANT AMOUNT:
	
	@Column(name="eligible_cost_of_dpr")
	private String eligibleCostOfDPR;
	
	@Column(name="eligibility_of_grant_50_percent")
	private String eligibilityOfGrant50Percent;
	
	@Column(name="max_eligible_grant")
	private String maxEligibleGrant;
	
	@Column(name="eligible_grant_amount_remarks")
	private String eligibleGrantAmountRemarks;

	

	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	public String getUnitId() {
		return unitId;
	}

	public void setUnitId(String unitId) {
		this.unitId = unitId;
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

	public String getDetailsOfInstitution() {
		return detailsOfInstitution;
	}

	public void setDetailsOfInstitution(String detailsOfInstitution) {
		this.detailsOfInstitution = detailsOfInstitution;
	}

	public String getDetailsOfInstitutionObserv() {
		return detailsOfInstitutionObserv;
	}

	public void setDetailsOfInstitutionObserv(String detailsOfInstitutionObserv) {
		this.detailsOfInstitutionObserv = detailsOfInstitutionObserv;
	}

	public String getDetailsOfExportImport() {
		return detailsOfExportImport;
	}

	public void setDetailsOfExportImport(String detailsOfExportImport) {
		this.detailsOfExportImport = detailsOfExportImport;
	}

	public String getDetailsOfExportImportObserv() {
		return detailsOfExportImportObserv;
	}

	public void setDetailsOfExportImportObserv(String detailsOfExportImportObserv) {
		this.detailsOfExportImportObserv = detailsOfExportImportObserv;
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

	public String getFeeChargedByInstitutionCost() {
		return feeChargedByInstitutionCost;
	}

	public void setFeeChargedByInstitutionCost(String feeChargedByInstitutionCost) {
		this.feeChargedByInstitutionCost = feeChargedByInstitutionCost;
	}

	public String getFeeChargedByInstitutionRemarks() {
		return feeChargedByInstitutionRemarks;
	}

	public void setFeeChargedByInstitutionRemarks(String feeChargedByInstitutionRemarks) {
		this.feeChargedByInstitutionRemarks = feeChargedByInstitutionRemarks;
	}

	public String getPlantMachineryRequiredCost() {
		return plantMachineryRequiredCost;
	}

	public void setPlantMachineryRequiredCost(String plantMachineryRequiredCost) {
		this.plantMachineryRequiredCost = plantMachineryRequiredCost;
	}

	public String getPlantMachineryRequiredRemarks() {
		return plantMachineryRequiredRemarks;
	}

	public void setPlantMachineryRequiredRemarks(String plantMachineryRequiredRemarks) {
		this.plantMachineryRequiredRemarks = plantMachineryRequiredRemarks;
	}

	public String getTechnicalCivilWorkCost() {
		return technicalCivilWorkCost;
	}

	public void setTechnicalCivilWorkCost(String technicalCivilWorkCost) {
		this.technicalCivilWorkCost = technicalCivilWorkCost;
	}

	public String getTechnicalCivilWorkRemarks() {
		return technicalCivilWorkRemarks;
	}

	public void setTechnicalCivilWorkRemarks(String technicalCivilWorkRemarks) {
		this.technicalCivilWorkRemarks = technicalCivilWorkRemarks;
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

	public String getMofAssistanceFromOtherSourcesCost() {
		return mofAssistanceFromOtherSourcesCost;
	}

	public void setMofAssistanceFromOtherSourcesCost(String mofAssistanceFromOtherSourcesCost) {
		this.mofAssistanceFromOtherSourcesCost = mofAssistanceFromOtherSourcesCost;
	}

	public String getMofAssistanceFromOtherSourcesRemark() {
		return mofAssistanceFromOtherSourcesRemark;
	}

	public void setMofAssistanceFromOtherSourcesRemark(String mofAssistanceFromOtherSourcesRemark) {
		this.mofAssistanceFromOtherSourcesRemark = mofAssistanceFromOtherSourcesRemark;
	}

	public String getMofOthersCost() {
		return mofOthersCost;
	}

	public void setMofOthersCost(String mofOthersCost) {
		this.mofOthersCost = mofOthersCost;
	}

	public String getMofOthersCostRemarks() {
		return mofOthersCostRemarks;
	}

	public void setMofOthersCostRemarks(String mofOthersCostRemarks) {
		this.mofOthersCostRemarks = mofOthersCostRemarks;
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

	public String getAppOnPrescribedFormatDesc() {
		return appOnPrescribedFormatDesc;
	}

	public void setAppOnPrescribedFormatDesc(String appOnPrescribedFormatDesc) {
		this.appOnPrescribedFormatDesc = appOnPrescribedFormatDesc;
	}

	public String getAppOnPrescribedFormatRemarks() {
		return appOnPrescribedFormatRemarks;
	}

	public void setAppOnPrescribedFormatRemarks(String appOnPrescribedFormatRemarks) {
		this.appOnPrescribedFormatRemarks = appOnPrescribedFormatRemarks;
	}

	public String getDetailedProjectReportDesc() {
		return detailedProjectReportDesc;
	}

	public void setDetailedProjectReportDesc(String detailedProjectReportDesc) {
		this.detailedProjectReportDesc = detailedProjectReportDesc;
	}

	public String getDetailedProjectReportRemarks() {
		return detailedProjectReportRemarks;
	}

	public void setDetailedProjectReportRemarks(String detailedProjectReportRemarks) {
		this.detailedProjectReportRemarks = detailedProjectReportRemarks;
	}

	public String getCertificateOfIncorporationDesc() {
		return certificateOfIncorporationDesc;
	}

	public void setCertificateOfIncorporationDesc(String certificateOfIncorporationDesc) {
		this.certificateOfIncorporationDesc = certificateOfIncorporationDesc;
	}

	public String getCertificateOfIncorporationRemarks() {
		return certificateOfIncorporationRemarks;
	}

	public void setCertificateOfIncorporationRemarks(String certificateOfIncorporationRemarks) {
		this.certificateOfIncorporationRemarks = certificateOfIncorporationRemarks;
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

	public String getPromotersBioDataDesc() {
		return promotersBioDataDesc;
	}

	public void setPromotersBioDataDesc(String promotersBioDataDesc) {
		this.promotersBioDataDesc = promotersBioDataDesc;
	}

	public String getPromotersBioDataRemark() {
		return promotersBioDataRemark;
	}

	public void setPromotersBioDataRemark(String promotersBioDataRemark) {
		this.promotersBioDataRemark = promotersBioDataRemark;
	}

	public String getFinancialStatementsForLast3YrsDesc() {
		return financialStatementsForLast3YrsDesc;
	}

	public void setFinancialStatementsForLast3YrsDesc(String financialStatementsForLast3YrsDesc) {
		this.financialStatementsForLast3YrsDesc = financialStatementsForLast3YrsDesc;
	}

	public String getFinancialStatementsForLast3YrsRemarks() {
		return financialStatementsForLast3YrsRemarks;
	}

	public void setFinancialStatementsForLast3YrsRemarks(String financialStatementsForLast3YrsRemarks) {
		this.financialStatementsForLast3YrsRemarks = financialStatementsForLast3YrsRemarks;
	}

	public String getReceiptOfPaymentCostDesc() {
		return receiptOfPaymentCostDesc;
	}

	public void setReceiptOfPaymentCostDesc(String receiptOfPaymentCostDesc) {
		this.receiptOfPaymentCostDesc = receiptOfPaymentCostDesc;
	}

	public String getReceiptOfPaymentRemark() {
		return receiptOfPaymentRemark;
	}

	public void setReceiptOfPaymentRemark(String receiptOfPaymentRemark) {
		this.receiptOfPaymentRemark = receiptOfPaymentRemark;
	}

	public String getLayoutOfPlantDesc() {
		return layoutOfPlantDesc;
	}

	public void setLayoutOfPlantDesc(String layoutOfPlantDesc) {
		this.layoutOfPlantDesc = layoutOfPlantDesc;
	}

	public String getLayoutOfPlantRemark() {
		return layoutOfPlantRemark;
	}

	public void setLayoutOfPlantRemark(String layoutOfPlantRemark) {
		this.layoutOfPlantRemark = layoutOfPlantRemark;
	}

	public String getDetailsOfPlantMachineryCEMechDesc() {
		return detailsOfPlantMachineryCEMechDesc;
	}

	public void setDetailsOfPlantMachineryCEMechDesc(String detailsOfPlantMachineryCEMechDesc) {
		this.detailsOfPlantMachineryCEMechDesc = detailsOfPlantMachineryCEMechDesc;
	}

	public String getDetailsOfPlantMachineryCEMechRemark() {
		return detailsOfPlantMachineryCEMechRemark;
	}

	public void setDetailsOfPlantMachineryCEMechRemark(String detailsOfPlantMachineryCEMechRemark) {
		this.detailsOfPlantMachineryCEMechRemark = detailsOfPlantMachineryCEMechRemark;
	}

	public String getDetailsOfTechnicalCivilDesc() {
		return detailsOfTechnicalCivilDesc;
	}

	public void setDetailsOfTechnicalCivilDesc(String detailsOfTechnicalCivilDesc) {
		this.detailsOfTechnicalCivilDesc = detailsOfTechnicalCivilDesc;
	}

	public String getDetailsOfTechnicalCivilRemark() {
		return detailsOfTechnicalCivilRemark;
	}

	public void setDetailsOfTechnicalCivilRemark(String detailsOfTechnicalCivilRemark) {
		this.detailsOfTechnicalCivilRemark = detailsOfTechnicalCivilRemark;
	}

	
	
	
	public String getDetailsOfPaymentBankableBoucherNo() {
		return detailsOfPaymentBankableBoucherNo;
	}

	public void setDetailsOfPaymentBankableBoucherNo(String detailsOfPaymentBankableBoucherNo) {
		this.detailsOfPaymentBankableBoucherNo = detailsOfPaymentBankableBoucherNo;
	}

	public String getDetailsOfPaymentBankableBoucherDate() {
		return detailsOfPaymentBankableBoucherDate;
	}

	public void setDetailsOfPaymentBankableBoucherDate(String detailsOfPaymentBankableBoucherDate) {
		this.detailsOfPaymentBankableBoucherDate = detailsOfPaymentBankableBoucherDate;
	}

	public String getDetailsOfPaymentBankableProjectAmount() {
		return detailsOfPaymentBankableProjectAmount;
	}

	public void setDetailsOfPaymentBankableProjectAmount(String detailsOfPaymentBankableProjectAmount) {
		this.detailsOfPaymentBankableProjectAmount = detailsOfPaymentBankableProjectAmount;
	}

	public String getDetailsOfPaymentBankableTotalAmountPaid() {
		return detailsOfPaymentBankableTotalAmountPaid;
	}

	public void setDetailsOfPaymentBankableTotalAmountPaid(String detailsOfPaymentBankableTotalAmountPaid) {
		this.detailsOfPaymentBankableTotalAmountPaid = detailsOfPaymentBankableTotalAmountPaid;
	}

	public String getDetailsOfPaymentBankableProjectTDS() {
		return detailsOfPaymentBankableProjectTDS;
	}

	public void setDetailsOfPaymentBankableProjectTDS(String detailsOfPaymentBankableProjectTDS) {
		this.detailsOfPaymentBankableProjectTDS = detailsOfPaymentBankableProjectTDS;
	}

	
	
	

	public String[] getDetailsOfPaymentBankableProjectEligCost() {
		return detailsOfPaymentBankableProjectEligCost;
	}

	public void setDetailsOfPaymentBankableProjectEligCost(String[] detailsOfPaymentBankableProjectEligCost) {
		this.detailsOfPaymentBankableProjectEligCost = detailsOfPaymentBankableProjectEligCost;
	}

	public String getTtlDetailsOfPaymentBankableProject() {
		return ttlDetailsOfPaymentBankableProject;
	}

	public void setTtlDetailsOfPaymentBankableProject(String ttlDetailsOfPaymentBankableProject) {
		this.ttlDetailsOfPaymentBankableProject = ttlDetailsOfPaymentBankableProject;
	}

	public String getDetailsOfPaymentBankableProjectRemarks() {
		return detailsOfPaymentBankableProjectRemarks;
	}

	public void setDetailsOfPaymentBankableProjectRemarks(String detailsOfPaymentBankableProjectRemarks) {
		this.detailsOfPaymentBankableProjectRemarks = detailsOfPaymentBankableProjectRemarks;
	}

	public String getEligibleCostOfDPR() {
		return eligibleCostOfDPR;
	}

	public void setEligibleCostOfDPR(String eligibleCostOfDPR) {
		this.eligibleCostOfDPR = eligibleCostOfDPR;
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
	
	

}
