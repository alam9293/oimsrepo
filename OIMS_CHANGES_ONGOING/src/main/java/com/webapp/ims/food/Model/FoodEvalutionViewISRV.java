package com.webapp.ims.food.Model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name="food_evalution_reefer_vehicles")
public class FoodEvalutionViewISRV implements Serializable{

	private static final long serialVersionUID = 1L;

	
	
		@Id
		@Column(name="unit_id")
		private String unitId;
		
		@Column(name="app_id")
		private String appId;
		
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
		
		@Column(name="capacity")
		private String capacity;
		
		//PROJECT PROFILE
		
			@Column(name="project_profile_appid")
			private String projectProfileAppId;
			
			@Column(name="project_profile_appid_observ")
			private String projectProfileAppIdObserv;
			
			@Column(name="proposal_submission_date")
			private String ProposalSubmissionDate;
			
			@Column(name="proposal_submission_date_observ")
			private String proposalSubmissionDateObserv;
			
			@Column(name="name_of_firm")
			private String nameOfFirm;
			
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
			
			@Column(name="regis_office_firm")
			private String regisOfficefirm;
			
			@Column(name="regis_office_firm_observ")
			private String regisOfficefirmobser;
			
			@Column(name="perpose_vehicle")
			private String perposevehicle;
			
			@Column(name="perpose_vehicle_observ")
			private String perposevehicleObserv;
			
			@Column(name="num_cap_ref_vehi")
			private String numcaprefvehi;
			
			@Column(name="num_cap_ref_vehi_observ")
			private String numcaprefvehiObservation;
			
			@Column(name="model_rv")
			private String modelRV;
			
			@Column(name="model_rv_observ")
			private String modelrvObservation;
			
			@Column(name="chassis")
			private String chassis;
			
			@Column(name="chassis_observ")
			private String chassisObservation;
			
			@Column(name="engine")
			private String engine;
			
			@Column(name="engine_observ")
			private String engineObservation;
			
			@Column(name ="appraising_bank")
			private String appraisingBank;
			
			@Column(name="appraise_bank_observ")
			private String appraiseBankObservation;
			
			@Column(name ="sanction_loan_amt")
			private String sanctionloan;
			
			@Column(name="sanction_loan_amt_observ")
			private String sanctionloanObservation;
			
			@Column(name ="roi")
			private String roi;
			@Column(name="roi_observation")
			private String roiObservation;
			
			@Column(name ="date_sanc_loan")
			private String datesancloan;
			@Column(name="date_sanc_loan_observ")
			private String datesancloanObservation;
			
			@Column(name ="date_disb_loan")
			private String datedisbloan;
			@Column(name="date_disb_loan_oservation")
			private String datedisbloanObservation;
			
			@Column(name="project_summary")
			private String projectSummary;
			
			//COST OF PROJECT (IN LACS)
		
			@Column(name="reefer_vehicles_cost")
			private String reeferVehiclesCost;
			
			@Column(name="total_reefer_vehicles_cost")
			private String totalreeferVehiclesCost;
			
			@Column(name="cop_nodal_officer")
			private String copNodalOfficer;
			
			//MEANS OF FINANCE (IN LACS)
			
			@Column(name="equity_cost")
			private String equityCost;
			
			@Column(name="term_loan_cost")
			private String termLoanCost;
			
			@Column(name="total_mof_cost")
			private String totalMOFCost;
			
			@Column(name="total_mof_remarks")
			private String totalMofRemarks;
		
			//STATUS OF DOCUMENTS
			
			@Column(name="app_on_prescribed_format_desc")
			private String appOnPrescribedFormatDesc;
			@Column(name="app_on_prescribed_format_remark")
			private String appOnPrescribedFormatRemark;
			
			@Column(name="detailed_project_report_desc")
			private String detailedProjectReportDesc;
			@Column(name="detailed_project_report_remark")
			private String detailedProjectReportRemark;
			
			@Column(name="sanction_letter_term_loan_desc")
			private String sanctionLetterTermLoanDesc;
			@Column(name="sanction_letter_term_loan_remark")
			private String sanctionLetterTermLoanRemark;
			
			@Column(name="bank_appraisal_report_desc")
			private String bankAppraisalReportDesc;
			@Column(name="bank_appraisal_report_remark")
			private String bankAppraisalReportRemark;
			
			@Column(name="quotation_reefer_vehicle_certified_ce_desc")
			private String quotationReeferVehicleCertifiedCEDesc;
			@Column(name="quotation_reefer_vehicle_certified_ce_remark")
			private String quotationReeferVehicleCertifiedCEDescRemark;
			
			@Column(name="invoice_reefer_vehicle_certified_ce_mec_decs")
			private String invoiceReeferVehicleCertifiedCEMecDecs;
			@Column(name="invoice_reefer_vehicle_certified_ce_mec_remark")
			private String invoiceReeferVehicleCertifiedCEMecDecsRemark;
			
			@Column(name="certificate_of_incorporation_desc")
			private String certificateOfIncorporationDesc;
			@Column(name="certificate_of_incorporation_remark")
			private String certificateOfIncorporationRemark;
			
			@Column(name="promoters_biodata_desc")
			private String promotersBioDataDesc;
			@Column(name="promoters_biodata_desc_remark")
			private String promotersBioDataDescRemark;
			
			@Column(name="delivery_challan_desc")
			private String deliveryChallanDesc;
			@Column(name="delivery_challan_remark")
			private String deliveryChallanRemark;
			
			@Column(name="repayment_schedule_of_term_loan_desc")
			private String repaymentScheduleOfTermLoanDesc;
			@Column(name="repayment_schedule_of_term_loan_remark")
			private String repaymentScheduleOfTermLoanRemark;
			
			@Column(name="certificate_of_regis_for_reefer_vehicle_desc")
			private String certificateOfRegisForReeferVehicleDesc;
			@Column(name="certificate_of_regis_for_reefer_vehicle_remarks")
			private String certificateOfRegisForReeferVehicleRemarks;
			
			@Column(name="certificate_of_fitness_for_vehicle_desc")
			private String certificateOfFitnessForVehicleDesc;
			@Column(name="certificate_of_fitness_for_vehicle_remarks")
			private String certificateOfFitnessForVehiclesRemarks;
			
			@Column(name="undertaking_for_job_work_desc")
			private String undertakingForJobWorkDesc;
			@Column(name="undertaking_for_job_work_remarks")
			private String undertakingForJobWorkRemarks;
			
			@Column(name="affidavit_on_prescribed_format_desc")
			private String affidavitOnPrescribedFormatDesc;
			@Column(name="affidavit_on_prescribed_format_remarks")
			private String affidavitOnPrescribedFormatRemarks;
			
			@Column(name="bank_claim_certificate_desc")
			private String bankClaimCertificateDesc;
			@Column(name="bank_claim_certificate_remarks")
			private String bankClaimCertificateRemarks;
			
			@Column(name="interest_subsudy")
			private String interestSubsudy;
			
			@Column(name="recommendation_comments")
			private String recommendationComments;

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
			
			

			public String getTotalMOFCost() {
				return totalMOFCost;
			}

			public void setTotalMOFCost(String totalMOFCost) {
				this.totalMOFCost = totalMOFCost;
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
				return ProposalSubmissionDate;
			}

			public void setProposalSubmissionDate(String proposalSubmissionDate) {
				ProposalSubmissionDate = proposalSubmissionDate;
			}

			public String getProposalSubmissionDateObserv() {
				return proposalSubmissionDateObserv;
			}

			public void setProposalSubmissionDateObserv(String proposalSubmissionDateObserv) {
				this.proposalSubmissionDateObserv = proposalSubmissionDateObserv;
			}

			public String getNameOfFirm() {
				return nameOfFirm;
			}

			public void setNameOfFirm(String nameOfFirm) {
				this.nameOfFirm = nameOfFirm;
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

			public String getRegisOfficefirm() {
				return regisOfficefirm;
			}

			public void setRegisOfficefirm(String regisOfficefirm) {
				this.regisOfficefirm = regisOfficefirm;
			}

			public String getRegisOfficefirmobser() {
				return regisOfficefirmobser;
			}

			public void setRegisOfficefirmobser(String regisOfficefirmobser) {
				this.regisOfficefirmobser = regisOfficefirmobser;
			}

			public String getPerposevehicle() {
				return perposevehicle;
			}

			public void setPerposevehicle(String perposevehicle) {
				this.perposevehicle = perposevehicle;
			}

			public String getPerposevehicleObserv() {
				return perposevehicleObserv;
			}

			public void setPerposevehicleObserv(String perposevehicleObserv) {
				this.perposevehicleObserv = perposevehicleObserv;
			}

			public String getNumcaprefvehi() {
				return numcaprefvehi;
			}

			public void setNumcaprefvehi(String numcaprefvehi) {
				this.numcaprefvehi = numcaprefvehi;
			}

			public String getNumcaprefvehiObservation() {
				return numcaprefvehiObservation;
			}

			public void setNumcaprefvehiObservation(String numcaprefvehiObservation) {
				this.numcaprefvehiObservation = numcaprefvehiObservation;
			}

			
			public String getModelRV() {
				return modelRV;
			}

			public void setModelRV(String modelRV) {
				this.modelRV = modelRV;
			}

			public String getModelrvObservation() {
				return modelrvObservation;
			}

			public void setModelrvObservation(String modelrvObservation) {
				this.modelrvObservation = modelrvObservation;
			}

			public String getChassis() {
				return chassis;
			}

			public void setChassis(String chassis) {
				this.chassis = chassis;
			}

			public String getChassisObservation() {
				return chassisObservation;
			}

			public void setChassisObservation(String chassisObservation) {
				this.chassisObservation = chassisObservation;
			}

			public String getEngine() {
				return engine;
			}

			public void setEngine(String engine) {
				this.engine = engine;
			}

			public String getEngineObservation() {
				return engineObservation;
			}

			public void setEngineObservation(String engineObservation) {
				this.engineObservation = engineObservation;
			}

			public String getAppraisingBank() {
				return appraisingBank;
			}

			public void setAppraisingBank(String appraisingBank) {
				this.appraisingBank = appraisingBank;
			}

			public String getAppraiseBankObservation() {
				return appraiseBankObservation;
			}

			public void setAppraiseBankObservation(String appraiseBankObservation) {
				this.appraiseBankObservation = appraiseBankObservation;
			}

			public String getSanctionloan() {
				return sanctionloan;
			}

			public void setSanctionloan(String sanctionloan) {
				this.sanctionloan = sanctionloan;
			}

			public String getSanctionloanObservation() {
				return sanctionloanObservation;
			}

			public void setSanctionloanObservation(String sanctionloanObservation) {
				this.sanctionloanObservation = sanctionloanObservation;
			}

			public String getRoi() {
				return roi;
			}

			public void setRoi(String roi) {
				this.roi = roi;
			}

			public String getRoiObservation() {
				return roiObservation;
			}

			public void setRoiObservation(String roiObservation) {
				this.roiObservation = roiObservation;
			}

			public String getDatesancloan() {
				return datesancloan;
			}

			public void setDatesancloan(String datesancloan) {
				this.datesancloan = datesancloan;
			}

			public String getDatesancloanObservation() {
				return datesancloanObservation;
			}

			public void setDatesancloanObservation(String datesancloanObservation) {
				this.datesancloanObservation = datesancloanObservation;
			}

			public String getDatedisbloan() {
				return datedisbloan;
			}

			public void setDatedisbloan(String datedisbloan) {
				this.datedisbloan = datedisbloan;
			}

			public String getDatedisbloanObservation() {
				return datedisbloanObservation;
			}

			public void setDatedisbloanObservation(String datedisbloanObservation) {
				this.datedisbloanObservation = datedisbloanObservation;
			}

			public String getProjectSummary() {
				return projectSummary;
			}

			public void setProjectSummary(String projectSummary) {
				this.projectSummary = projectSummary;
			}

			public String getReeferVehiclesCost() {
				return reeferVehiclesCost;
			}

			public void setReeferVehiclesCost(String reeferVehiclesCost) {
				this.reeferVehiclesCost = reeferVehiclesCost;
			}

			public String getTotalreeferVehiclesCost() {
				return totalreeferVehiclesCost;
			}

			public void setTotalreeferVehiclesCost(String totalreeferVehiclesCost) {
				this.totalreeferVehiclesCost = totalreeferVehiclesCost;
			}

			public String getCopNodalOfficer() {
				return copNodalOfficer;
			}

			public void setCopNodalOfficer(String copNodalOfficer) {
				this.copNodalOfficer = copNodalOfficer;
			}

			public String getEquityCost() {
				return equityCost;
			}

			public void setEquityCost(String equityCost) {
				this.equityCost = equityCost;
			}

			public String getTermLoanCost() {
				return termLoanCost;
			}

			public void setTermLoanCost(String termLoanCost) {
				this.termLoanCost = termLoanCost;
			}

			public String getTotalMofRemarks() {
				return totalMofRemarks;
			}

			public void setTotalMofRemarks(String totalMofRemarks) {
				this.totalMofRemarks = totalMofRemarks;
			}

			public String getAppOnPrescribedFormatDesc() {
				return appOnPrescribedFormatDesc;
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

			public String getQuotationReeferVehicleCertifiedCEDesc() {
				return quotationReeferVehicleCertifiedCEDesc;
			}

			public void setQuotationReeferVehicleCertifiedCEDesc(String quotationReeferVehicleCertifiedCEDesc) {
				this.quotationReeferVehicleCertifiedCEDesc = quotationReeferVehicleCertifiedCEDesc;
			}

			public String getQuotationReeferVehicleCertifiedCEDescRemark() {
				return quotationReeferVehicleCertifiedCEDescRemark;
			}

			public void setQuotationReeferVehicleCertifiedCEDescRemark(String quotationReeferVehicleCertifiedCEDescRemark) {
				this.quotationReeferVehicleCertifiedCEDescRemark = quotationReeferVehicleCertifiedCEDescRemark;
			}

			public String getInvoiceReeferVehicleCertifiedCEMecDecs() {
				return invoiceReeferVehicleCertifiedCEMecDecs;
			}

			public void setInvoiceReeferVehicleCertifiedCEMecDecs(String invoiceReeferVehicleCertifiedCEMecDecs) {
				this.invoiceReeferVehicleCertifiedCEMecDecs = invoiceReeferVehicleCertifiedCEMecDecs;
			}

			public String getInvoiceReeferVehicleCertifiedCEMecDecsRemark() {
				return invoiceReeferVehicleCertifiedCEMecDecsRemark;
			}

			public void setInvoiceReeferVehicleCertifiedCEMecDecsRemark(String invoiceReeferVehicleCertifiedCEMecDecsRemark) {
				this.invoiceReeferVehicleCertifiedCEMecDecsRemark = invoiceReeferVehicleCertifiedCEMecDecsRemark;
			}

			public String getCertificateOfIncorporationDesc() {
				return certificateOfIncorporationDesc;
			}

			public void setCertificateOfIncorporationDesc(String certificateOfIncorporationDesc) {
				this.certificateOfIncorporationDesc = certificateOfIncorporationDesc;
			}

			public String getCertificateOfIncorporationRemark() {
				return certificateOfIncorporationRemark;
			}

			public void setCertificateOfIncorporationRemark(String certificateOfIncorporationRemark) {
				this.certificateOfIncorporationRemark = certificateOfIncorporationRemark;
			}

			public String getPromotersBioDataDesc() {
				return promotersBioDataDesc;
			}

			public void setPromotersBioDataDesc(String promotersBioDataDesc) {
				this.promotersBioDataDesc = promotersBioDataDesc;
			}

			public String getPromotersBioDataDescRemark() {
				return promotersBioDataDescRemark;
			}

			public void setPromotersBioDataDescRemark(String promotersBioDataDescRemark) {
				this.promotersBioDataDescRemark = promotersBioDataDescRemark;
			}

			public String getDeliveryChallanDesc() {
				return deliveryChallanDesc;
			}

			public void setDeliveryChallanDesc(String deliveryChallanDesc) {
				this.deliveryChallanDesc = deliveryChallanDesc;
			}

			public String getDeliveryChallanRemark() {
				return deliveryChallanRemark;
			}

			public void setDeliveryChallanRemark(String deliveryChallanRemark) {
				this.deliveryChallanRemark = deliveryChallanRemark;
			}

			public String getRepaymentScheduleOfTermLoanDesc() {
				return repaymentScheduleOfTermLoanDesc;
			}

			public void setRepaymentScheduleOfTermLoanDesc(String repaymentScheduleOfTermLoanDesc) {
				this.repaymentScheduleOfTermLoanDesc = repaymentScheduleOfTermLoanDesc;
			}

			public String getRepaymentScheduleOfTermLoanRemark() {
				return repaymentScheduleOfTermLoanRemark;
			}

			public void setRepaymentScheduleOfTermLoanRemark(String repaymentScheduleOfTermLoanRemark) {
				this.repaymentScheduleOfTermLoanRemark = repaymentScheduleOfTermLoanRemark;
			}

			public String getCertificateOfRegisForReeferVehicleDesc() {
				return certificateOfRegisForReeferVehicleDesc;
			}

			public void setCertificateOfRegisForReeferVehicleDesc(String certificateOfRegisForReeferVehicleDesc) {
				this.certificateOfRegisForReeferVehicleDesc = certificateOfRegisForReeferVehicleDesc;
			}

			public String getCertificateOfRegisForReeferVehicleRemarks() {
				return certificateOfRegisForReeferVehicleRemarks;
			}

			public void setCertificateOfRegisForReeferVehicleRemarks(String certificateOfRegisForReeferVehicleRemarks) {
				this.certificateOfRegisForReeferVehicleRemarks = certificateOfRegisForReeferVehicleRemarks;
			}

			public String getCertificateOfFitnessForVehicleDesc() {
				return certificateOfFitnessForVehicleDesc;
			}

			public void setCertificateOfFitnessForVehicleDesc(String certificateOfFitnessForVehicleDesc) {
				this.certificateOfFitnessForVehicleDesc = certificateOfFitnessForVehicleDesc;
			}

			public String getCertificateOfFitnessForVehiclesRemarks() {
				return certificateOfFitnessForVehiclesRemarks;
			}

			public void setCertificateOfFitnessForVehiclesRemarks(String certificateOfFitnessForVehiclesRemarks) {
				this.certificateOfFitnessForVehiclesRemarks = certificateOfFitnessForVehiclesRemarks;
			}

			public String getUndertakingForJobWorkDesc() {
				return undertakingForJobWorkDesc;
			}

			public void setUndertakingForJobWorkDesc(String undertakingForJobWorkDesc) {
				this.undertakingForJobWorkDesc = undertakingForJobWorkDesc;
			}

			public String getUndertakingForJobWorkRemarks() {
				return undertakingForJobWorkRemarks;
			}

			public void setUndertakingForJobWorkRemarks(String undertakingForJobWorkRemarks) {
				this.undertakingForJobWorkRemarks = undertakingForJobWorkRemarks;
			}

			public String getAffidavitOnPrescribedFormatDesc() {
				return affidavitOnPrescribedFormatDesc;
			}

			public void setAffidavitOnPrescribedFormatDesc(String affidavitOnPrescribedFormatDesc) {
				this.affidavitOnPrescribedFormatDesc = affidavitOnPrescribedFormatDesc;
			}

			public String getAffidavitOnPrescribedFormatRemarks() {
				return affidavitOnPrescribedFormatRemarks;
			}

			public void setAffidavitOnPrescribedFormatRemarks(String affidavitOnPrescribedFormatRemarks) {
				this.affidavitOnPrescribedFormatRemarks = affidavitOnPrescribedFormatRemarks;
			}

			public String getBankClaimCertificateDesc() {
				return bankClaimCertificateDesc;
			}

			public void setBankClaimCertificateDesc(String bankClaimCertificateDesc) {
				this.bankClaimCertificateDesc = bankClaimCertificateDesc;
			}

			public String getBankClaimCertificateRemarks() {
				return bankClaimCertificateRemarks;
			}

			public void setBankClaimCertificateRemarks(String bankClaimCertificateRemarks) {
				this.bankClaimCertificateRemarks = bankClaimCertificateRemarks;
			}

			public String getInterestSubsudy() {
				return interestSubsudy;
			}

			public void setInterestSubsudy(String interestSubsudy) {
				this.interestSubsudy = interestSubsudy;
			}

			public String getRecommendationComments() {
				return recommendationComments;
			}

			public void setRecommendationComments(String recommendationComments) {
				this.recommendationComments = recommendationComments;
			}
			
			
			
}
			