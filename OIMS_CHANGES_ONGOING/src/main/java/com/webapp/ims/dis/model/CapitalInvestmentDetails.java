package com.webapp.ims.dis.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "Capital_Investment_Details", schema = "loc")
public class CapitalInvestmentDetails implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "Cap_Inv_Id")
	private String capInvId;
	@Column(name = "Cap_Inv_Apc_Id")
	private String capInvApcId;

	// Item Land cost
	@Column(name = "Cap_Inv_DPR_LC")
	private Long capInvDPRLC;
	@Column(name = "Cap_Inv_Appraisal_LC")
	private Long capInvAppraisalLC;
	@Column(name = "Cap_Inv_CutoffDate_LC")
	private Long capInvCutoffDateLC;
	@Column(name = "Cap_Inv_CommProd_LC")
	private Long capInvCommProdLC;
	@Column(name = "Cap_Inv_ActualInv_LC")
	private Long capInvActualInvLC;
	@Column(name = "Cap_Inv_AddlInv_LC")
	private Long capInvAddlInvLC;
	@Column(name = "Cap_Inv_Total_LC")
	private Long capInvTotalLC;

	// Item Building cost
	@Column(name = "Cap_Inv_DPR_BC")
	private Long capInvDPRBC;
	@Column(name = "Cap_Inv_Appraisal_BC")
	private Long capInvAppraisalBC;
	@Column(name = "Cap_Inv_CutoffDate_BC")
	private Long capInvCutoffDateBC;
	@Column(name = "Cap_Inv_CommProd_BC")
	private Long capInvCommProdBC;
	@Column(name = "Cap_Inv_ActualInv_BC")
	private Long capInvActualInvBC;
	@Column(name = "Cap_Inv_AddlInv_BC")
	private Long capInvAddlInvBC;
	@Column(name = "Cap_Inv_Total_BC")
	private Long capInvTotalBC;

	@Column(name = "Annual_TurnOver")
	private Long annualTurnOver;

	// Item Plant& Machinery cost
	@Column(name = "Cap_Inv_DPR_PMC")
	private Long capInvDPRPMC;
	@Column(name = "Cap_Inv_Appraisal_PMC")
	private Long capInvAppraisalPMC;
	@Column(name = "Cap_Inv_CutoffDate_PMC")
	private Long capInvCutoffDatePMC;
	@Column(name = "Cap_Inv_Commprod_PMC")
	private Long capInvCommProdPMC;
	@Column(name = "Cap_Inv_ActualInv_PMC")
	private Long capInvActualInvPMC;
	@Column(name = "Cap_Inv_Addlinv_PMC")
	private Long capInvAddlInvPMC;
	@Column(name = "Cap_Inv_Total_PMC")
	private Long capInvTotalPMC;

	// Miscellaneous Fixed Asset
	@Column(name = "Cap_Inv_Dpr_MFA")
	private Long capInvDPRMFA;
	@Column(name = "Cap_Inv_Appraisal_MFA")
	private Long capInvAppraisalMFA;
	@Column(name = "Cap_Inv_Cutoffdate_MFA")
	private Long capInvCutoffDateMFA;
	@Column(name = "Cap_Inv_Commprod_MFA")
	private Long capInvCommProdMFA;
	@Column(name = "Cap_Inv_ActualInv_MFA")
	private Long capInvActualInvMFA;
	@Column(name = "Cap_Inv_Addlinv_MFA")
	private Long capInvAddlInvMFA;
	@Column(name = "Cap_Inv_Total_MFA")
	private Long capInvTotalMFA;

	// Technical Knowhow Fee
	@Column(name = "Cap_Inv_Dpr_TKF")
	private Long capInvDPRTKF;
	@Column(name = "Cap_Inv_Appraisal_TKF")
	private Long capInvAppraisalTKF;
	@Column(name = "Cap_Inv_CutoffDate_TKF")
	private Long capInvCutoffDateTKF;
	@Column(name = "Cap_Inv_Commprod_TKF")
	private Long capInvCommProdTKF;
	@Column(name = "Cap_Inv_ActualInv_TKF")
	private Long capInvActualInvTKF;
	@Column(name = "Cap_Inv_Addlinv_TKF")
	private Long capInvAddlInvTKF;
	@Column(name = "Cap_Inv_Total_TKF")
	private Long capInvTotalTKF;

	// Interest During Construction Period
	@Column(name = "Cap_Inv_Dpr_ICP")
	private Long capInvDPRICP;
	@Column(name = "Cap_Inv_Appraisal_ICP")
	private Long capInvAppraisalICP;
	@Column(name = "Cap_Inv_CutoffDate_ICP")
	private Long capInvCutoffDateICP;
	@Column(name = "Cap_Inv_Commprod_ICP")
	private Long capInvCommProdICP;
	@Column(name = "Cap_Inv_ActualInv_ICP")
	private Long capInvActualInvICP;
	@Column(name = "Cap_Inv_AddlInv_ICP")
	private Long capInvAddlInvICP;
	@Column(name = "Cap_Inv_Total_ICP")
	private Long capInvTotalICP;

	// Prel. And Preoperative Expenses
	@Column(name = "Cap_Inv_Dpr_PPE")
	private Long capInvDPRPPE;
	@Column(name = "Cap_Inv_Appraisal_PPE")
	private Long capInvAppraisalPPE;
	@Column(name = "Cap_Inv_CutoffDate_PPE")
	private Long capInvCutoffDatePPE;
	@Column(name = "Cap_Inv_Commprod_PPE")
	private Long capInvCommProdPPE;
	@Column(name = "Cap_Inv_ActualInv_PPE")
	private Long capInvActualInvPPE;
	@Column(name = "Cap_Inv_Addlinv_PPE")
	private Long capInvAddlInvPPE;
	@Column(name = "Cap_Inv_Total_PPE")
	private Long capInvTotalPPE;

	// Margin Money for Working Capital
	@Column(name = "Cap_Inv_Dpr_MMC")
	private Long capInvDPRMMC;
	@Column(name = "Cap_Inv_Appraisal_MMC")
	private Long capInvAppraisalMMC;
	@Column(name = "Cap_Inv_CutoffDate_MMC")
	private Long capInvCutoffDateMMC;
	@Column(name = "Cap_Inv_Commprod_MMC")
	private Long capInvCommProdMMC;
	@Column(name = "Cap_Inv_ActualInv_MMC")
	private Long capInvActualInvMMC;
	@Column(name = "Cap_Inv_Addlinv_MMC")
	private Long capInvAddlInvMMC;
	@Column(name = "Cap_Inv_Total_MMC")
	private Long capInvTotalMMC;

	@Column(name = "Cap_Inv_Created_By")
	private String capInvCreatedBy;
	@Column(name = "Cap_Inv_Modify_Date")
	@Temporal(TemporalType.DATE)
	private Date capInvModifiyDate;
	@Column(name = "Cap_Inv_Created_Date")
	@Temporal(TemporalType.DATE)
	private Date capInvCreatedDate;

	@Column(name = "Ttl_Cost_Dpr")
	private Long ttlCostDpr;
	@Column(name = "Ttlcost_ProApp_FrBankFI")
	private Long ttlcostProAppFrBank;
	@Column(name = "Ttl_Before_CutOff")
	private Long ttlBeforeCutOff;
	@Column(name = "TtlCutOff_Comm_Production")
	private Long ttlCutOffCommProduction;
	@Column(name = "Ttl_Actual_Investment")
	private Long ttlActualInvestment;

	@Column(name = "Ttl_AddiActual_Investment")
	private Long ttlAddiActualInvestment;
	@Column(name = "Dis_TotalOfTotal")
	private Long disValueTotalOfTotal;

	@Column(name = "Land_Purchase_Fr_UPSIDC")
	private String landPurchaseFrUPSIDC;

	public CapitalInvestmentDetails() {
		super();
	}

	public String getCapInvId() {
		return capInvId;
	}

	public void setCapInvId(String capInvId) {
		this.capInvId = capInvId;
	}

	public String getCapInvApcId() {
		return capInvApcId;
	}

	public void setCapInvApcId(String capInvApcId) {
		this.capInvApcId = capInvApcId;
	}

	public Long getTtlCostDpr() {
		return ttlCostDpr;
	}

	public void setTtlCostDpr(Long ttlCostDpr) {
		this.ttlCostDpr = ttlCostDpr;
	}

	public Long getTtlcostProAppFrBank() {
		return ttlcostProAppFrBank;
	}

	public void setTtlcostProAppFrBank(Long ttlcostProAppFrBank) {
		this.ttlcostProAppFrBank = ttlcostProAppFrBank;
	}

	public Long getTtlBeforeCutOff() {
		return ttlBeforeCutOff;
	}

	public void setTtlBeforeCutOff(Long ttlBeforeCutOff) {
		this.ttlBeforeCutOff = ttlBeforeCutOff;
	}

	public Long getTtlCutOffCommProduction() {
		return ttlCutOffCommProduction;
	}

	public void setTtlCutOffCommProduction(Long ttlCutOffCommProduction) {
		this.ttlCutOffCommProduction = ttlCutOffCommProduction;
	}

	public Long getTtlActualInvestment() {
		return ttlActualInvestment;
	}

	public void setTtlActualInvestment(Long ttlActualInvestment) {
		this.ttlActualInvestment = ttlActualInvestment;
	}

	public Long getTtlAddiActualInvestment() {
		return ttlAddiActualInvestment;
	}

	public void setTtlAddiActualInvestment(Long ttlAddiActualInvestment) {
		this.ttlAddiActualInvestment = ttlAddiActualInvestment;
	}

	public Long getDisValueTotalOfTotal() {
		return disValueTotalOfTotal;
	}

	public void setDisValueTotalOfTotal(Long disValueTotalOfTotal) {
		this.disValueTotalOfTotal = disValueTotalOfTotal;
	}

	public Long getCapInvDPRLC() {
		return capInvDPRLC;
	}

	public void setCapInvDPRLC(Long capInvDPRLC) {
		this.capInvDPRLC = capInvDPRLC;
	}

	public Long getCapInvAppraisalLC() {
		return capInvAppraisalLC;
	}

	public void setCapInvAppraisalLC(Long capInvAppraisalLC) {
		this.capInvAppraisalLC = capInvAppraisalLC;
	}

	public Long getCapInvCutoffDateLC() {
		return capInvCutoffDateLC;
	}

	public void setCapInvCutoffDateLC(Long capInvCutoffDateLC) {
		this.capInvCutoffDateLC = capInvCutoffDateLC;
	}

	public Long getCapInvCommProdLC() {
		return capInvCommProdLC;
	}

	public void setCapInvCommProdLC(Long capInvCommProdLC) {
		this.capInvCommProdLC = capInvCommProdLC;
	}

	public Long getCapInvActualInvLC() {
		return capInvActualInvLC;
	}

	public void setCapInvActualInvLC(Long capInvActualInvLC) {
		this.capInvActualInvLC = capInvActualInvLC;
	}

	public Long getCapInvAddlInvLC() {
		return capInvAddlInvLC;
	}

	public void setCapInvAddlInvLC(Long capInvAddlInvLC) {
		this.capInvAddlInvLC = capInvAddlInvLC;
	}

	public Long getCapInvTotalLC() {
		return capInvTotalLC;
	}

	public void setCapInvTotalLC(Long capInvTotalLC) {
		this.capInvTotalLC = capInvTotalLC;
	}

	public String getCapInvCreatedBy() {
		return capInvCreatedBy;
	}

	public void setCapInvCreatedBy(String capInvCreatedBy) {
		this.capInvCreatedBy = capInvCreatedBy;
	}

	public Date getCapInvModifiyDate() {
		return capInvModifiyDate;
	}

	public void setCapInvModifiyDate(Date capInvModifiyDate) {
		this.capInvModifiyDate = capInvModifiyDate;
	}

	public Date getCapInvCreatedDate() {
		return capInvCreatedDate;
	}

	public void setCapInvCreatedDate(Date capInvCreatedDate) {
		this.capInvCreatedDate = capInvCreatedDate;
	}

	public Long getCapInvDPRBC() {
		return capInvDPRBC;
	}

	public void setCapInvDPRBC(Long capInvDPRBC) {
		this.capInvDPRBC = capInvDPRBC;
	}

	public Long getCapInvAppraisalBC() {
		return capInvAppraisalBC;
	}

	public void setCapInvAppraisalBC(Long capInvAppraisalBC) {
		this.capInvAppraisalBC = capInvAppraisalBC;
	}

	public Long getCapInvCutoffDateBC() {
		return capInvCutoffDateBC;
	}

	public void setCapInvCutoffDateBC(Long capInvCutoffDateBC) {
		this.capInvCutoffDateBC = capInvCutoffDateBC;
	}

	public Long getCapInvCommProdBC() {
		return capInvCommProdBC;
	}

	public void setCapInvCommProdBC(Long capInvCommProdBC) {
		this.capInvCommProdBC = capInvCommProdBC;
	}

	public Long getCapInvActualInvBC() {
		return capInvActualInvBC;
	}

	public void setCapInvActualInvBC(Long capInvActualInvBC) {
		this.capInvActualInvBC = capInvActualInvBC;
	}

	public Long getAnnualTurnOver() {
		return annualTurnOver;
	}

	public void setAnnualTurnOver(Long annualTurnOver) {
		this.annualTurnOver = annualTurnOver;
	}

	public Long getCapInvAddlInvBC() {
		return capInvAddlInvBC;
	}

	public void setCapInvAddlInvBC(Long capInvAddlInvBC) {
		this.capInvAddlInvBC = capInvAddlInvBC;
	}

	public Long getCapInvTotalBC() {
		return capInvTotalBC;
	}

	public void setCapInvTotalBC(Long capInvTotalBC) {
		this.capInvTotalBC = capInvTotalBC;
	}

	public Long getCapInvDPRPMC() {
		return capInvDPRPMC;
	}

	public void setCapInvDPRPMC(Long capInvDPRPMC) {
		this.capInvDPRPMC = capInvDPRPMC;
	}

	public Long getCapInvAppraisalPMC() {
		return capInvAppraisalPMC;
	}

	public void setCapInvAppraisalPMC(Long capInvAppraisalPMC) {
		this.capInvAppraisalPMC = capInvAppraisalPMC;
	}

	public Long getCapInvCutoffDatePMC() {
		return capInvCutoffDatePMC;
	}

	public void setCapInvCutoffDatePMC(Long capInvCutoffDatePMC) {
		this.capInvCutoffDatePMC = capInvCutoffDatePMC;
	}

	public Long getCapInvCommProdPMC() {
		return capInvCommProdPMC;
	}

	public void setCapInvCommProdPMC(Long capInvCommProdPMC) {
		this.capInvCommProdPMC = capInvCommProdPMC;
	}

	public Long getCapInvActualInvPMC() {
		return capInvActualInvPMC;
	}

	public void setCapInvActualInvPMC(Long capInvActualInvPMC) {
		this.capInvActualInvPMC = capInvActualInvPMC;
	}

	public Long getCapInvAddlInvPMC() {
		return capInvAddlInvPMC;
	}

	public void setCapInvAddlInvPMC(Long capInvAddlInvPMC) {
		this.capInvAddlInvPMC = capInvAddlInvPMC;
	}

	public Long getCapInvTotalPMC() {
		return capInvTotalPMC;
	}

	public void setCapInvTotalPMC(Long capInvTotalPMC) {
		this.capInvTotalPMC = capInvTotalPMC;
	}

	public Long getCapInvDPRMFA() {
		return capInvDPRMFA;
	}

	public void setCapInvDPRMFA(Long capInvDPRMFA) {
		this.capInvDPRMFA = capInvDPRMFA;
	}

	public Long getCapInvAppraisalMFA() {
		return capInvAppraisalMFA;
	}

	public void setCapInvAppraisalMFA(Long capInvAppraisalMFA) {
		this.capInvAppraisalMFA = capInvAppraisalMFA;
	}

	public Long getCapInvCutoffDateMFA() {
		return capInvCutoffDateMFA;
	}

	public void setCapInvCutoffDateMFA(Long capInvCutoffDateMFA) {
		this.capInvCutoffDateMFA = capInvCutoffDateMFA;
	}

	public Long getCapInvCommProdMFA() {
		return capInvCommProdMFA;
	}

	public void setCapInvCommProdMFA(Long capInvCommProdMFA) {
		this.capInvCommProdMFA = capInvCommProdMFA;
	}

	public Long getCapInvActualInvMFA() {
		return capInvActualInvMFA;
	}

	public void setCapInvActualInvMFA(Long capInvActualInvMFA) {
		this.capInvActualInvMFA = capInvActualInvMFA;
	}

	public Long getCapInvAddlInvMFA() {
		return capInvAddlInvMFA;
	}

	public void setCapInvAddlInvMFA(Long capInvAddlInvMFA) {
		this.capInvAddlInvMFA = capInvAddlInvMFA;
	}

	public Long getCapInvTotalMFA() {
		return capInvTotalMFA;
	}

	public void setCapInvTotalMFA(Long capInvTotalMFA) {
		this.capInvTotalMFA = capInvTotalMFA;
	}

	public Long getCapInvDPRTKF() {
		return capInvDPRTKF;
	}

	public void setCapInvDPRTKF(Long capInvDPRTKF) {
		this.capInvDPRTKF = capInvDPRTKF;
	}

	public Long getCapInvAppraisalTKF() {
		return capInvAppraisalTKF;
	}

	public void setCapInvAppraisalTKF(Long capInvAppraisalTKF) {
		this.capInvAppraisalTKF = capInvAppraisalTKF;
	}

	public Long getCapInvCutoffDateTKF() {
		return capInvCutoffDateTKF;
	}

	public void setCapInvCutoffDateTKF(Long capInvCutoffDateTKF) {
		this.capInvCutoffDateTKF = capInvCutoffDateTKF;
	}

	public Long getCapInvCommProdTKF() {
		return capInvCommProdTKF;
	}

	public void setCapInvCommProdTKF(Long capInvCommProdTKF) {
		this.capInvCommProdTKF = capInvCommProdTKF;
	}

	public Long getCapInvActualInvTKF() {
		return capInvActualInvTKF;
	}

	public void setCapInvActualInvTKF(Long capInvActualInvTKF) {
		this.capInvActualInvTKF = capInvActualInvTKF;
	}

	public Long getCapInvAddlInvTKF() {
		return capInvAddlInvTKF;
	}

	public void setCapInvAddlInvTKF(Long capInvAddlInvTKF) {
		this.capInvAddlInvTKF = capInvAddlInvTKF;
	}

	public Long getCapInvTotalTKF() {
		return capInvTotalTKF;
	}

	public void setCapInvTotalTKF(Long capInvTotalTKF) {
		this.capInvTotalTKF = capInvTotalTKF;
	}

	public Long getCapInvDPRICP() {
		return capInvDPRICP;
	}

	public void setCapInvDPRICP(Long capInvDPRICP) {
		this.capInvDPRICP = capInvDPRICP;
	}

	public Long getCapInvAppraisalICP() {
		return capInvAppraisalICP;
	}

	public void setCapInvAppraisalICP(Long capInvAppraisalICP) {
		this.capInvAppraisalICP = capInvAppraisalICP;
	}

	public Long getCapInvCutoffDateICP() {
		return capInvCutoffDateICP;
	}

	public void setCapInvCutoffDateICP(Long capInvCutoffDateICP) {
		this.capInvCutoffDateICP = capInvCutoffDateICP;
	}

	public Long getCapInvCommProdICP() {
		return capInvCommProdICP;
	}

	public void setCapInvCommProdICP(Long capInvCommProdICP) {
		this.capInvCommProdICP = capInvCommProdICP;
	}

	public Long getCapInvActualInvICP() {
		return capInvActualInvICP;
	}

	public void setCapInvActualInvICP(Long capInvActualInvICP) {
		this.capInvActualInvICP = capInvActualInvICP;
	}

	public Long getCapInvAddlInvICP() {
		return capInvAddlInvICP;
	}

	public void setCapInvAddlInvICP(Long capInvAddlInvICP) {
		this.capInvAddlInvICP = capInvAddlInvICP;
	}

	public Long getCapInvTotalICP() {
		return capInvTotalICP;
	}

	public void setCapInvTotalICP(Long capInvTotalICP) {
		this.capInvTotalICP = capInvTotalICP;
	}

	public Long getCapInvDPRPPE() {
		return capInvDPRPPE;
	}

	public void setCapInvDPRPPE(Long capInvDPRPPE) {
		this.capInvDPRPPE = capInvDPRPPE;
	}

	public Long getCapInvAppraisalPPE() {
		return capInvAppraisalPPE;
	}

	public void setCapInvAppraisalPPE(Long capInvAppraisalPPE) {
		this.capInvAppraisalPPE = capInvAppraisalPPE;
	}

	public Long getCapInvCutoffDatePPE() {
		return capInvCutoffDatePPE;
	}

	public void setCapInvCutoffDatePPE(Long capInvCutoffDatePPE) {
		this.capInvCutoffDatePPE = capInvCutoffDatePPE;
	}

	public Long getCapInvCommProdPPE() {
		return capInvCommProdPPE;
	}

	public void setCapInvCommProdPPE(Long capInvCommProdPPE) {
		this.capInvCommProdPPE = capInvCommProdPPE;
	}

	public Long getCapInvActualInvPPE() {
		return capInvActualInvPPE;
	}

	public void setCapInvActualInvPPE(Long capInvActualInvPPE) {
		this.capInvActualInvPPE = capInvActualInvPPE;
	}

	public Long getCapInvAddlInvPPE() {
		return capInvAddlInvPPE;
	}

	public void setCapInvAddlInvPPE(Long capInvAddlInvPPE) {
		this.capInvAddlInvPPE = capInvAddlInvPPE;
	}

	public Long getCapInvTotalPPE() {
		return capInvTotalPPE;
	}

	public void setCapInvTotalPPE(Long capInvTotalPPE) {
		this.capInvTotalPPE = capInvTotalPPE;
	}

	public Long getCapInvDPRMMC() {
		return capInvDPRMMC;
	}

	public void setCapInvDPRMMC(Long capInvDPRMMC) {
		this.capInvDPRMMC = capInvDPRMMC;
	}

	public Long getCapInvAppraisalMMC() {
		return capInvAppraisalMMC;
	}

	public void setCapInvAppraisalMMC(Long capInvAppraisalMMC) {
		this.capInvAppraisalMMC = capInvAppraisalMMC;
	}

	public Long getCapInvCutoffDateMMC() {
		return capInvCutoffDateMMC;
	}

	public void setCapInvCutoffDateMMC(Long capInvCutoffDateMMC) {
		this.capInvCutoffDateMMC = capInvCutoffDateMMC;
	}

	public Long getCapInvCommProdMMC() {
		return capInvCommProdMMC;
	}

	public void setCapInvCommProdMMC(Long capInvCommProdMMC) {
		this.capInvCommProdMMC = capInvCommProdMMC;
	}

	public Long getCapInvActualInvMMC() {
		return capInvActualInvMMC;
	}

	public void setCapInvActualInvMMC(Long capInvActualInvMMC) {
		this.capInvActualInvMMC = capInvActualInvMMC;
	}

	public Long getCapInvAddlInvMMC() {
		return capInvAddlInvMMC;
	}

	public void setCapInvAddlInvMMC(Long capInvAddlInvMMC) {
		this.capInvAddlInvMMC = capInvAddlInvMMC;
	}

	public Long getCapInvTotalMMC() {
		return capInvTotalMMC;
	}

	public void setCapInvTotalMMC(Long capInvTotalMMC) {
		this.capInvTotalMMC = capInvTotalMMC;
	}

	public String getLandPurchaseFrUPSIDC() {
		return landPurchaseFrUPSIDC;
	}

	public void setLandPurchaseFrUPSIDC(String landPurchaseFrUPSIDC) {
		this.landPurchaseFrUPSIDC = landPurchaseFrUPSIDC;
	}

}
