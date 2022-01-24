package com.webapp.ims.food.Model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="foodevalutionviewmdbp")
public class FoodEvalutionViewMDBP implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="app_id")
	private String appId;
	
	
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
	
	@Column(name="proposed_site_address")
	private String proposedSiteAddress;
	
	@Column(name="capacity")
	private String capacity;
	
	@Column(name="project_profile_app_id")
	private String projectProfileAppId;
	
	@Column(name="project_profile_app_id_observ")
	private String projectProfileAppIdObserv;
	
	@Column(name="proposal_submission_date")
	private String proposalSubmissionDate;
	
	@Column(name="proposal_submission_date_observ")
	private String proposalSubmissionDateObserv;
	
	//Financial Status (audited balance sheet for last 3 years)
	
	
	private transient String financialYear;
	
	
	private transient String turnOverInLacs;

	@Column(name="financial_status_observ")
	private String financialStatusObserv;
	
	@Column(name="ssi_registration")
	private String ssiRegistration;

	@Column(name="ssi_registration_observ")
	private String ssiRegistrationObserv;
	
	@Column(name="type_of_organization")
	private String typeOfOrganization;

	@Column(name="type_of_organization_observ")
	private String typeOfOrganizationObserv;
	
	@Column(name="promoter_biodata")
	private String promoterBiodata;

	@Column(name="promoter_biodata_observ")
	private String promoterBiodataObserv;
	
	@Column(name="project_summary")
	private String projectSummary;
	
	@Column(name="transportation_paid_cost")
	private String transportationPaidCost;
	
	@Column(name="transportation_paid_contin_corporate")
	private String transportationPaidContinCorporate;
	
	@Column(name="transportation_paid_cost_observ")
	private String transportationPaidObserv;
	
	@Column(name="fob_value_cost")
	private String fobValueCost;
	
	@Column(name="fob_value_contain_corporate")
	private String fobValueContainCorporate;
	
	@Column(name="fob_value_observ")
	private String fobValueObserv;
	
	//STATUS OF DOCUMENTS
	
	@Column(name="app_on_prescribed_format_desc")
	private String appOnPrescribedFormatDesc;
	@Column(name="app_on_prescribed_format_remark")
	private String appOnPrescribedFormatDescRemarks;
	
	@Column(name="detailed_project_report_desc")
	private String detailedProjectReportDesc;
	@Column(name="detailed_project_report_remark")
	private String detailedProjectReportRemark;
	
	@Column(name="certificate_of_export_import_desc")
	private String certificateOfExportImportDesc;
	@Column(name="certificate_of_export_import_remark")
	private String certificateOfExportImportRemark;
	
	@Column(name="ca_certificate_desc")
	private String caCertificateDesc;
	@Column(name="ca_certificate_remark")
	private String caCertificateRemark;
	
	@Column(name="list_of_plant_mach_desc")
	private String listOfPlantMachDesc;
	@Column(name="list_of_plant_mach_remark")
	private String listOfPlantMachDescRemark;
	
	@Column(name="details_of_export_done_desc")
	private String detailsOfExportDoneDesc;
	@Column(name="details_of_export_done_remark")
	private String detailsOfExportDoneRemark;
	
	@Column(name="freight_bills_of_container_desc")
	private String freightBillsOfContainerDesc;
	@Column(name="freight_bills_of_container_remark")
	private String freightBillsOfContainerRemark;
	
	
	@Column(name="buyer_purchase_order_desc")
	private String buyerPurchaseOrderDesc;
	@Column(name="buyer_purchase_order_remark")
	private String buyerPurchaseOrderDescRemark;
	
	@Column(name="phyto_sanitary_certificate_desc")
	private String phytoSanitaryCertificateDesc ;
	@Column(name="phyto_sanitary_certificate_remark")
	private String phytoSanitaryCertificateRemark;
	
	@Column(name="export_invoice_desc")
	private String exportInvoiceDesc;
	@Column(name="export_invoice_remark")
	private String exportInvoiceRemark;
	
	
	@Column(name="bank_realization_certificate_desc")
	private String bankRealizationCertificateDesc ;
	@Column(name="bank_realization_certificate_remark")
	private String bankRealizationCertificateRemark;
	
	@Column(name="shipping_bills_for_export_desc")
	private String shippingBillsForExportDesc;
	@Column(name="shipping_bills_for_export_remark")
	private String shippingBillsForExportRemark;
	

	
	@Column(name="bills_of_landing_desc")
	private String billsOfLandingDesc ;
	@Column(name="bills_of_landing_remark")
	private String billsOfLandingRemark;
	
	//SUBSIDY CALCULATION FOR ROAD TRANSPORTATION/FREIGHT TO THE SEA PORT
	
	@Column(name="road_transport_concor_inv_no")
	private transient String subCalRoadTransportConcorInvNo;
	
	@Column(name="road_transport_concor_date")
	private transient String subCalRoadTransportDate;
	

	@Column(name="road_transport_basic_price")
	private transient String subCalRoadTransportBasicPrice;
	
	
	@Column(name="sub_cal_road_transport_abated_value")
	private transient String subCalRoadTransportAbatedValue;
	

	@Column(name="road_transport_cgst")
	private transient String subCalRoadTransportCGST;
	
	@Column(name="road_transport_sgst")
	private transient String subCalRoadTransportSGST;
	
	@Column(name="road_transport_total_amount")
	private transient String subCalTotalAmount;
	
	@Column(name="road_transport_eligible_cost_in_lacs")
	private transient String subCalRoadTransportEligibleCostInLacs;
	
	
	
	@Column(name="total_road_transport_basic_price")
	private String totalSubCalRoadTransportBasicPrice;
	
	@Column(name="total_road_transport_abated_value")
	private String totalSubCalRoadTransportAbatedValue;
	
	
	@Column(name="total_road_transport_cgst")
	private String totalSubCalRoadTransportCGST;
	
	@Column(name="total_road_transport_sgst")
	private String totalSubCalRoadTransportSGST;
	
	@Column(name="total_road_transport_ttl_amount")
	private String totalSubCalRoadTransportTtlAmount;
	
	@Column(name="total_road_transport_eligible_cost_in_lacs")
	private String totalSubCalRoadTransportEligibleCostInLacs;
	
	
	
	
	@Column(name="road_transport_nodal_officer_remark")
	private String subCalRoadTransportNodalOfficerRemark;
	
	//ELIGIBLE SUBSIDY AMOUNT OF ROAD TRANSPORT
	

	@Column(name="eligible_cost_road_transport")
	private String eligibleCostRoadTransport;
	
	
	@Column(name="eligibility_of_subsidy_road_transport")
	private String eligibilityOfSubsidyRoadTransport;
	
	@Column(name="max_eligible_grant_road_transport")
	private String maxEligibleGrantRoadTransport;
	
	//SUBSIDY CALCULATION FOR FOB VALUE.
	

	@Column(name="sub_cal_for_fob_value_shipping_bill_no")
	private transient String subCalForFobValueShippingBillNo;
	
	
	@Column(name="sub_cal_for_fob_value_date")
	private transient String subCalForFobValueDate;
	
	@Column(name="sub_cal_for_fob_value")
	private transient  String subCalForFobValue;
	
	
	@Column(name="sub_cal_for_fob_value_cgst")
	private transient String subCalForFobValueCGST;
	
	
	@Column(name="sub_cal_for_fob_value_sgst")
	private transient String subCalForFobValueSGST;
	
	@Column(name="sub_cal_for_fob_value_total_amount")
	private transient String subCalForFobValueTotalAmount;
	
	@Column(name="sub_cal_for_fob_value_eligible_cost_in_lacs")
	private transient String subCalForFobValueEligibleCostInLacs;
	
	
	
	@Column(name="total_sub_cal_for_fob_value")
	private String totalSubCalForFobValue;
	
	
	@Column(name="total_sub_cal_for_fob_value_cgst")
	private String totalSubCalForFobValueCGST;
	
	
	@Column(name="total_sub_cal_for_fob_value_sgst")
	private String totalSubCalForFobValueSGST;
	
	@Column(name="total_sub_cal_for_fob_value_total_amount")
	private String totalSubCalForFobValueTotalAmount;
	
	@Column(name="total_sub_cal_for_fob_value_eligible_cost_in_lacs")
	private String totalSubCalForFobValueEligibleCostInLacs;
	
	
	//ELIGIBLE SUBSIDY ON FOB AMOUNT
	
	@Column(name="eligible_cost_fob_value")
	private String eligibleCostFOBValue;
	
	
	@Column(name="eligibility_of_subsidy_fob_value")
	private String eligibilityOfSubsidyFOBValue;
	
	@Column(name="max_eligible_grant_fob_value")
	private String maxEligibleGrantFOBValue;
	
	@Column(name="total_eligible_amount_fob_value")
	private String totalEligibleAmountFOBValue;
	
	@Column(name="nodal_officer_remark_fob_value")
	private String nodalOfficerRemarkFOBValue;
	
	
	@Column(name="sub_on_transport_and_fob_value")
	private String subOnTransportAndFOBValue;
	

	@Column(name="recommendation_comments")
	private String recommendationComments;




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


	


	public String getFinancialYear() {
		return financialYear;
	}


	public void setFinancialYear(String financialYear) {
		this.financialYear = financialYear;
	}


	public String getTurnOverInLacs() {
		return turnOverInLacs;
	}


	public void setTurnOverInLacs(String turnOverInLacs) {
		this.turnOverInLacs = turnOverInLacs;
	}


	public String getFinancialStatusObserv() {
		return financialStatusObserv;
	}


	public void setFinancialStatusObserv(String financialStatusObserv) {
		this.financialStatusObserv = financialStatusObserv;
	}


	public String getSsiRegistration() {
		return ssiRegistration;
	}


	public void setSsiRegistration(String ssiRegistration) {
		this.ssiRegistration = ssiRegistration;
	}


	public String getSsiRegistrationObserv() {
		return ssiRegistrationObserv;
	}


	public void setSsiRegistrationObserv(String ssiRegistrationObserv) {
		this.ssiRegistrationObserv = ssiRegistrationObserv;
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


	public String getPromoterBiodata() {
		return promoterBiodata;
	}


	public void setPromoterBiodata(String promoterBiodata) {
		this.promoterBiodata = promoterBiodata;
	}


	public String getPromoterBiodataObserv() {
		return promoterBiodataObserv;
	}


	public void setPromoterBiodataObserv(String promoterBiodataObserv) {
		this.promoterBiodataObserv = promoterBiodataObserv;
	}


	public String getProjectSummary() {
		return projectSummary;
	}


	public void setProjectSummary(String projectSummary) {
		this.projectSummary = projectSummary;
	}


	public String getTransportationPaidCost() {
		return transportationPaidCost;
	}


	public void setTransportationPaidCost(String transportationPaidCost) {
		this.transportationPaidCost = transportationPaidCost;
	}


	public String getTransportationPaidContinCorporate() {
		return transportationPaidContinCorporate;
	}


	public void setTransportationPaidContinCorporate(String transportationPaidContinCorporate) {
		this.transportationPaidContinCorporate = transportationPaidContinCorporate;
	}


	public String getTransportationPaidObserv() {
		return transportationPaidObserv;
	}


	public void setTransportationPaidObserv(String transportationPaidObserv) {
		this.transportationPaidObserv = transportationPaidObserv;
	}


	public String getFobValueCost() {
		return fobValueCost;
	}


	public void setFobValueCost(String fobValueCost) {
		this.fobValueCost = fobValueCost;
	}


	public String getFobValueContainCorporate() {
		return fobValueContainCorporate;
	}


	public void setFobValueContainCorporate(String fobValueContainCorporate) {
		this.fobValueContainCorporate = fobValueContainCorporate;
	}


	public String getFobValueObserv() {
		return fobValueObserv;
	}


	public void setFobValueObserv(String fobValueObserv) {
		this.fobValueObserv = fobValueObserv;
	}


	public String getAppOnPrescribedFormatDesc() {
		return appOnPrescribedFormatDesc;
	}


	public void setAppOnPrescribedFormatDesc(String appOnPrescribedFormatDesc) {
		this.appOnPrescribedFormatDesc = appOnPrescribedFormatDesc;
	}


	public String getAppOnPrescribedFormatDescRemarks() {
		return appOnPrescribedFormatDescRemarks;
	}


	public void setAppOnPrescribedFormatDescRemarks(String appOnPrescribedFormatDescRemarks) {
		this.appOnPrescribedFormatDescRemarks = appOnPrescribedFormatDescRemarks;
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


	public String getCertificateOfExportImportDesc() {
		return certificateOfExportImportDesc;
	}


	public void setCertificateOfExportImportDesc(String certificateOfExportImportDesc) {
		this.certificateOfExportImportDesc = certificateOfExportImportDesc;
	}


	public String getCertificateOfExportImportRemark() {
		return certificateOfExportImportRemark;
	}


	public void setCertificateOfExportImportRemark(String certificateOfExportImportRemark) {
		this.certificateOfExportImportRemark = certificateOfExportImportRemark;
	}


	public String getCaCertificateDesc() {
		return caCertificateDesc;
	}


	public void setCaCertificateDesc(String caCertificateDesc) {
		this.caCertificateDesc = caCertificateDesc;
	}


	public String getCaCertificateRemark() {
		return caCertificateRemark;
	}


	public void setCaCertificateRemark(String caCertificateRemark) {
		this.caCertificateRemark = caCertificateRemark;
	}


	public String getListOfPlantMachDesc() {
		return listOfPlantMachDesc;
	}


	public void setListOfPlantMachDesc(String listOfPlantMachDesc) {
		this.listOfPlantMachDesc = listOfPlantMachDesc;
	}


	public String getListOfPlantMachDescRemark() {
		return listOfPlantMachDescRemark;
	}


	public void setListOfPlantMachDescRemark(String listOfPlantMachDescRemark) {
		this.listOfPlantMachDescRemark = listOfPlantMachDescRemark;
	}


	public String getDetailsOfExportDoneDesc() {
		return detailsOfExportDoneDesc;
	}


	public void setDetailsOfExportDoneDesc(String detailsOfExportDoneDesc) {
		this.detailsOfExportDoneDesc = detailsOfExportDoneDesc;
	}


	public String getDetailsOfExportDoneRemark() {
		return detailsOfExportDoneRemark;
	}


	public void setDetailsOfExportDoneRemark(String detailsOfExportDoneRemark) {
		this.detailsOfExportDoneRemark = detailsOfExportDoneRemark;
	}


	public String getFreightBillsOfContainerDesc() {
		return freightBillsOfContainerDesc;
	}


	public void setFreightBillsOfContainerDesc(String freightBillsOfContainerDesc) {
		this.freightBillsOfContainerDesc = freightBillsOfContainerDesc;
	}


	public String getFreightBillsOfContainerRemark() {
		return freightBillsOfContainerRemark;
	}


	public void setFreightBillsOfContainerRemark(String freightBillsOfContainerRemark) {
		this.freightBillsOfContainerRemark = freightBillsOfContainerRemark;
	}


	public String getBuyerPurchaseOrderDesc() {
		return buyerPurchaseOrderDesc;
	}


	public void setBuyerPurchaseOrderDesc(String buyerPurchaseOrderDesc) {
		this.buyerPurchaseOrderDesc = buyerPurchaseOrderDesc;
	}


	public String getBuyerPurchaseOrderDescRemark() {
		return buyerPurchaseOrderDescRemark;
	}


	public void setBuyerPurchaseOrderDescRemark(String buyerPurchaseOrderDescRemark) {
		this.buyerPurchaseOrderDescRemark = buyerPurchaseOrderDescRemark;
	}


	public String getPhytoSanitaryCertificateDesc() {
		return phytoSanitaryCertificateDesc;
	}


	public void setPhytoSanitaryCertificateDesc(String phytoSanitaryCertificateDesc) {
		this.phytoSanitaryCertificateDesc = phytoSanitaryCertificateDesc;
	}


	public String getPhytoSanitaryCertificateRemark() {
		return phytoSanitaryCertificateRemark;
	}


	public void setPhytoSanitaryCertificateRemark(String phytoSanitaryCertificateRemark) {
		this.phytoSanitaryCertificateRemark = phytoSanitaryCertificateRemark;
	}


	public String getExportInvoiceDesc() {
		return exportInvoiceDesc;
	}


	public void setExportInvoiceDesc(String exportInvoiceDesc) {
		this.exportInvoiceDesc = exportInvoiceDesc;
	}


	public String getExportInvoiceRemark() {
		return exportInvoiceRemark;
	}


	public void setExportInvoiceRemark(String exportInvoiceRemark) {
		this.exportInvoiceRemark = exportInvoiceRemark;
	}


	public String getBankRealizationCertificateDesc() {
		return bankRealizationCertificateDesc;
	}


	public void setBankRealizationCertificateDesc(String bankRealizationCertificateDesc) {
		this.bankRealizationCertificateDesc = bankRealizationCertificateDesc;
	}


	public String getBankRealizationCertificateRemark() {
		return bankRealizationCertificateRemark;
	}


	public void setBankRealizationCertificateRemark(String bankRealizationCertificateRemark) {
		this.bankRealizationCertificateRemark = bankRealizationCertificateRemark;
	}


	public String getShippingBillsForExportDesc() {
		return shippingBillsForExportDesc;
	}


	public void setShippingBillsForExportDesc(String shippingBillsForExportDesc) {
		this.shippingBillsForExportDesc = shippingBillsForExportDesc;
	}


	public String getShippingBillsForExportRemark() {
		return shippingBillsForExportRemark;
	}


	public void setShippingBillsForExportRemark(String shippingBillsForExportRemark) {
		this.shippingBillsForExportRemark = shippingBillsForExportRemark;
	}


	public String getBillsOfLandingDesc() {
		return billsOfLandingDesc;
	}


	public void setBillsOfLandingDesc(String billsOfLandingDesc) {
		this.billsOfLandingDesc = billsOfLandingDesc;
	}


	public String getBillsOfLandingRemark() {
		return billsOfLandingRemark;
	}


	public void setBillsOfLandingRemark(String billsOfLandingRemark) {
		this.billsOfLandingRemark = billsOfLandingRemark;
	}


	public String getSubCalRoadTransportConcorInvNo() {
		return subCalRoadTransportConcorInvNo;
	}


	public void setSubCalRoadTransportConcorInvNo(String subCalRoadTransportConcorInvNo) {
		this.subCalRoadTransportConcorInvNo = subCalRoadTransportConcorInvNo;
	}


	public String getSubCalRoadTransportDate() {
		return subCalRoadTransportDate;
	}


	public void setSubCalRoadTransportDate(String subCalRoadTransportDate) {
		this.subCalRoadTransportDate = subCalRoadTransportDate;
	}


	public String getSubCalRoadTransportBasicPrice() {
		return subCalRoadTransportBasicPrice;
	}


	public void setSubCalRoadTransportBasicPrice(String subCalRoadTransportBasicPrice) {
		this.subCalRoadTransportBasicPrice = subCalRoadTransportBasicPrice;
	}


	


	public String getSubCalRoadTransportAbatedValue() {
		return subCalRoadTransportAbatedValue;
	}


	public void setSubCalRoadTransportAbatedValue(String subCalRoadTransportAbatedValue) {
		this.subCalRoadTransportAbatedValue = subCalRoadTransportAbatedValue;
	}


	public String getSubCalRoadTransportCGST() {
		return subCalRoadTransportCGST;
	}


	public void setSubCalRoadTransportCGST(String subCalRoadTransportCGST) {
		this.subCalRoadTransportCGST = subCalRoadTransportCGST;
	}


	public String getSubCalRoadTransportSGST() {
		return subCalRoadTransportSGST;
	}


	public void setSubCalRoadTransportSGST(String subCalRoadTransportSGST) {
		this.subCalRoadTransportSGST = subCalRoadTransportSGST;
	}


	public String getSubCalRoadTransportEligibleCostInLacs() {
		return subCalRoadTransportEligibleCostInLacs;
	}


	public void setSubCalRoadTransportEligibleCostInLacs(String subCalRoadTransportEligibleCostInLacs) {
		this.subCalRoadTransportEligibleCostInLacs = subCalRoadTransportEligibleCostInLacs;
	}


	public String getSubCalRoadTransportNodalOfficerRemark() {
		return subCalRoadTransportNodalOfficerRemark;
	}


	public void setSubCalRoadTransportNodalOfficerRemark(String subCalRoadTransportNodalOfficerRemark) {
		this.subCalRoadTransportNodalOfficerRemark = subCalRoadTransportNodalOfficerRemark;
	}


	public String getTotalSubCalRoadTransportBasicPrice() {
		return totalSubCalRoadTransportBasicPrice;
	}


	public void setTotalSubCalRoadTransportBasicPrice(String totalSubCalRoadTransportBasicPrice) {
		this.totalSubCalRoadTransportBasicPrice = totalSubCalRoadTransportBasicPrice;
	}


	public String getTotalSubCalRoadTransportAbatedValue() {
		return totalSubCalRoadTransportAbatedValue;
	}


	public void setTotalSubCalRoadTransportAbatedValue(String totalSubCalRoadTransportAbatedValue) {
		this.totalSubCalRoadTransportAbatedValue = totalSubCalRoadTransportAbatedValue;
	}


	public String getTotalSubCalRoadTransportCGST() {
		return totalSubCalRoadTransportCGST;
	}


	public void setTotalSubCalRoadTransportCGST(String totalSubCalRoadTransportCGST) {
		this.totalSubCalRoadTransportCGST = totalSubCalRoadTransportCGST;
	}


	public String getTotalSubCalRoadTransportSGST() {
		return totalSubCalRoadTransportSGST;
	}


	public void setTotalSubCalRoadTransportSGST(String totalSubCalRoadTransportSGST) {
		this.totalSubCalRoadTransportSGST = totalSubCalRoadTransportSGST;
	}


	public String getTotalSubCalRoadTransportEligibleCostInLacs() {
		return totalSubCalRoadTransportEligibleCostInLacs;
	}


	public void setTotalSubCalRoadTransportEligibleCostInLacs(String totalSubCalRoadTransportEligibleCostInLacs) {
		this.totalSubCalRoadTransportEligibleCostInLacs = totalSubCalRoadTransportEligibleCostInLacs;
	}


	


	public String getSubCalTotalAmount() {
		return subCalTotalAmount;
	}


	public void setSubCalTotalAmount(String subCalTotalAmount) {
		this.subCalTotalAmount = subCalTotalAmount;
	}


	public String getTotalSubCalRoadTransportTtlAmount() {
		return totalSubCalRoadTransportTtlAmount;
	}


	public void setTotalSubCalRoadTransportTtlAmount(String totalSubCalRoadTransportTtlAmount) {
		this.totalSubCalRoadTransportTtlAmount = totalSubCalRoadTransportTtlAmount;
	}


	public String getEligibleCostRoadTransport() {
		return eligibleCostRoadTransport;
	}


	public void setEligibleCostRoadTransport(String eligibleCostRoadTransport) {
		this.eligibleCostRoadTransport = eligibleCostRoadTransport;
	}


	public String getEligibilityOfSubsidyRoadTransport() {
		return eligibilityOfSubsidyRoadTransport;
	}


	public void setEligibilityOfSubsidyRoadTransport(String eligibilityOfSubsidyRoadTransport) {
		this.eligibilityOfSubsidyRoadTransport = eligibilityOfSubsidyRoadTransport;
	}


	public String getMaxEligibleGrantRoadTransport() {
		return maxEligibleGrantRoadTransport;
	}


	public void setMaxEligibleGrantRoadTransport(String maxEligibleGrantRoadTransport) {
		this.maxEligibleGrantRoadTransport = maxEligibleGrantRoadTransport;
	}


	public String getSubCalForFobValueShippingBillNo() {
		return subCalForFobValueShippingBillNo;
	}


	public void setSubCalForFobValueShippingBillNo(String subCalForFobValueShippingBillNo) {
		this.subCalForFobValueShippingBillNo = subCalForFobValueShippingBillNo;
	}


	public String getSubCalForFobValueDate() {
		return subCalForFobValueDate;
	}


	public void setSubCalForFobValueDate(String subCalForFobValueDate) {
		this.subCalForFobValueDate = subCalForFobValueDate;
	}


	public String getSubCalForFobValue() {
		return subCalForFobValue;
	}


	public void setSubCalForFobValue(String subCalForFobValue) {
		this.subCalForFobValue = subCalForFobValue;
	}


	public String getSubCalForFobValueCGST() {
		return subCalForFobValueCGST;
	}


	public void setSubCalForFobValueCGST(String subCalForFobValueCGST) {
		this.subCalForFobValueCGST = subCalForFobValueCGST;
	}


	public String getSubCalForFobValueSGST() {
		return subCalForFobValueSGST;
	}


	public void setSubCalForFobValueSGST(String subCalForFobValueSGST) {
		this.subCalForFobValueSGST = subCalForFobValueSGST;
	}


	public String getSubCalForFobValueTotalAmount() {
		return subCalForFobValueTotalAmount;
	}


	public void setSubCalForFobValueTotalAmount(String subCalForFobValueTotalAmount) {
		this.subCalForFobValueTotalAmount = subCalForFobValueTotalAmount;
	}


	public String getSubCalForFobValueEligibleCostInLacs() {
		return subCalForFobValueEligibleCostInLacs;
	}


	public void setSubCalForFobValueEligibleCostInLacs(String subCalForFobValueEligibleCostInLacs) {
		this.subCalForFobValueEligibleCostInLacs = subCalForFobValueEligibleCostInLacs;
	}


	public String getTotalSubCalForFobValue() {
		return totalSubCalForFobValue;
	}


	public void setTotalSubCalForFobValue(String totalSubCalForFobValue) {
		this.totalSubCalForFobValue = totalSubCalForFobValue;
	}


	public String getTotalSubCalForFobValueCGST() {
		return totalSubCalForFobValueCGST;
	}


	public void setTotalSubCalForFobValueCGST(String totalSubCalForFobValueCGST) {
		this.totalSubCalForFobValueCGST = totalSubCalForFobValueCGST;
	}


	public String getTotalSubCalForFobValueSGST() {
		return totalSubCalForFobValueSGST;
	}


	public void setTotalSubCalForFobValueSGST(String totalSubCalForFobValueSGST) {
		this.totalSubCalForFobValueSGST = totalSubCalForFobValueSGST;
	}


	public String getTotalSubCalForFobValueTotalAmount() {
		return totalSubCalForFobValueTotalAmount;
	}


	public void setTotalSubCalForFobValueTotalAmount(String totalSubCalForFobValueTotalAmount) {
		this.totalSubCalForFobValueTotalAmount = totalSubCalForFobValueTotalAmount;
	}


	public String getTotalSubCalForFobValueEligibleCostInLacs() {
		return totalSubCalForFobValueEligibleCostInLacs;
	}


	public void setTotalSubCalForFobValueEligibleCostInLacs(String totalSubCalForFobValueEligibleCostInLacs) {
		this.totalSubCalForFobValueEligibleCostInLacs = totalSubCalForFobValueEligibleCostInLacs;
	}


	public String getEligibleCostFOBValue() {
		return eligibleCostFOBValue;
	}


	public void setEligibleCostFOBValue(String eligibleCostFOBValue) {
		this.eligibleCostFOBValue = eligibleCostFOBValue;
	}


	public String getEligibilityOfSubsidyFOBValue() {
		return eligibilityOfSubsidyFOBValue;
	}


	public void setEligibilityOfSubsidyFOBValue(String eligibilityOfSubsidyFOBValue) {
		this.eligibilityOfSubsidyFOBValue = eligibilityOfSubsidyFOBValue;
	}


	public String getMaxEligibleGrantFOBValue() {
		return maxEligibleGrantFOBValue;
	}


	public void setMaxEligibleGrantFOBValue(String maxEligibleGrantFOBValue) {
		this.maxEligibleGrantFOBValue = maxEligibleGrantFOBValue;
	}


	public String getTotalEligibleAmountFOBValue() {
		return totalEligibleAmountFOBValue;
	}


	public void setTotalEligibleAmountFOBValue(String totalEligibleAmountFOBValue) {
		this.totalEligibleAmountFOBValue = totalEligibleAmountFOBValue;
	}


	


	public String getNodalOfficerRemarkFOBValue() {
		return nodalOfficerRemarkFOBValue;
	}


	public void setNodalOfficerRemarkFOBValue(String nodalOfficerRemarkFOBValue) {
		this.nodalOfficerRemarkFOBValue = nodalOfficerRemarkFOBValue;
	}


	public String getSubOnTransportAndFOBValue() {
		return subOnTransportAndFOBValue;
	}


	public void setSubOnTransportAndFOBValue(String subOnTransportAndFOBValue) {
		this.subOnTransportAndFOBValue = subOnTransportAndFOBValue;
	}


	public String getRecommendationComments() {
		return recommendationComments;
	}


	public void setRecommendationComments(String recommendationComments) {
		this.recommendationComments = recommendationComments;
	}
	
	
	
}
