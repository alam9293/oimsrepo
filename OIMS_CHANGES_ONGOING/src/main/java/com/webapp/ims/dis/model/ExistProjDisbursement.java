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
@Table(name = "Exist_Proj_Disbursement", schema = "loc")
public class ExistProjDisbursement implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	@Column(name = "Proj_Dis_Id")
	private String projDisId;
	@Column(name = "Proj_Dis_Apc_Id")
	private String projApcId;

	@Column(name = "Proj_Dis_Land_Cost")
	private Long projDisLandCost;

	@Column(name = "Proj_Dis_Bldg_Cost")
	private Long projDisBldgCost;

	@Column(name = "Proj_Dis_Plantmac_Cost")
	private Long projDisPlantMachCost;

	@Column(name = "Proj_Dis_Construct")
	private Long projDisConstruct;

	@Column(name = "Proj_Dis_Infra")
	private Long projDisInfra;

	@Column(name = "Proj_Dis_ExpandOrDiverse")
	private String projDisExpandOrDiverse;

	@Column(name = "Total_Amount_OfInvest")
	private Long totalAmountOfInvest;

	@Column(name = "Total_Increment")
	private Long totalIncrement;

	@Column(name = "Ttl_Amt_ExitProj")
	private Long ttlAmtExitProj;

	@Column(name = "Ttl_Loan")
	private Long totalLoan;

	@Column(name = "Ttl_Interrest")
	private Long totalInterest;

	@Column(name = "Proj_Land_Inc")
	private Long projLandIncrement;
	@Column(name = "Proj_Building_Inc")
	private Long projBuildIncrement;
	@Column(name = "Proj_Construct_Inc")
	private Long projConstructIncrement;
	@Column(name = "Proj_Plant_Inc")
	private Long projPlantIncrement;

	@Column(name = "ExProj_Misc_Fixed_Asset")
	private Long exProjMiscFixedAsset;

	@Column(name = "ExMisc_Exp_Div")
	private Long exMiscExpDiv;

	@Column(name = "ExMisc_Increment")
	private Long exMiscIncrement;

	@Column(name = "Proj_Infra_Inc")
	private Long projInfraIncrement;
	@Column(name = "Proj_Dis_Created_By")
	private String projDisCreatedBy;
	@Column(name = "Proj_Dis_Modify_Date")
	@Temporal(TemporalType.DATE)
	private Date projDisModifiyDate;
	@Column(name = "Proj_Dis_Created_Date")
	@Temporal(TemporalType.DATE)
	private Date projDisCreatedDate;

	private transient Long annualTurnOver;
	private transient Long ttlCostDpr;
	private transient Long ttlcostProAppFrBank;
	private transient Long ttlBeforeCutOff;
	private transient Long ttlCutOffCommProduction;
	private transient Long ttlActualInvestment;
	private transient Long ttlAddiActualInvestment;
	private transient Long disValueTotalOfTotal;

	private transient Long newprojLandCost;
	private transient Long newprojBldgCost;
	private transient Long newprojPlantMachCost;
	private transient Long newprojInfra;
	private transient Long newprojMiscFixedAsset;
	private transient Long total;

	private transient Long capInvDPRLC;
	private transient Long capInvAppraisalLC;
	private transient Long capInvCutoffDateLC;
	private transient Long capInvCommProdLC;
	private transient Long capInvActualInvLC;
	private transient Long capInvAddlInvLC;
	private transient Long capInvTotalLC;

	private transient Long capInvDPRBC;
	private transient Long capInvAppraisalBC;
	private transient Long capInvCutoffDateBC;
	private transient Long capInvCommProdBC;
	private transient Long capInvActualInvBC;
	private transient Long capInvAddlInvBC;
	private transient Long capInvTotalBC;

	private transient Long capInvDPRPMC;
	private transient Long capInvAppraisalPMC;
	private transient Long capInvCutoffDatePMC;
	private transient Long capInvCommProdPMC;
	private transient Long capInvActualInvPMC;
	private transient Long capInvAddlInvPMC;
	private transient Long capInvTotalPMC;

	private transient Long capInvDPRMFA;
	private transient Long capInvAppraisalMFA;
	private transient Long capInvCutoffDateMFA;
	private transient Long capInvCommProdMFA;
	private transient Long capInvActualInvMFA;
	private transient Long capInvAddlInvMFA;
	private transient Long capInvTotalMFA;

	private transient Long capInvDPRTKF;
	private transient Long capInvAppraisalTKF;
	private transient Long capInvCutoffDateTKF;
	private transient Long capInvCommProdTKF;
	private transient Long capInvActualInvTKF;
	private transient Long capInvAddlInvTKF;
	private transient Long capInvTotalTKF;

	private transient Long capInvDPRICP;
	private transient Long capInvAppraisalICP;
	private transient Long capInvCutoffDateICP;
	private transient Long capInvCommProdICP;
	private transient Long capInvActualInvICP;
	private transient Long capInvAddlInvICP;
	private transient Long capInvTotalICP;

	private transient Long capInvDPRPPE;
	private transient Long capInvAppraisalPPE;
	private transient Long capInvCutoffDatePPE;
	private transient Long capInvCommProdPPE;
	private transient Long capInvActualInvPPE;
	private transient Long capInvAddlInvPPE;
	private transient Long capInvTotalPPE;

	private transient Long capInvDPRMMC;
	private transient Long capInvAppraisalMMC;
	private transient Long capInvCutoffDateMMC;
	private transient Long capInvCommProdMMC;
	private transient Long capInvActualInvMMC;
	private transient Long capInvAddlInvMMC;
	private transient Long capInvTotalMMC;

	private transient String statutoryAuditorDoc;
	private transient String purchasePriceDoc;
	private transient String stampDutyDoc;
	private transient String registrationFeeDoc;
	private transient String banksAuctionDoc;
	private transient String civilWorksDoc;
	private transient String machineryMiscDoc;
	private transient String encCertificateFName;

	private transient String statutoryAuditorDocbase64File;
	private transient String purchasePriceDocbase64File;
	private transient String stampDutyDocbase64File;
	private transient String registrationFeeDocbase64File;
	private transient String banksAuctionDocbase64File;
	private transient String civilWorksDocbase64File;
	private transient String machineryMiscDocbase64File;
	private transient String encCertificateFNamebase64File;
	private transient String landPurchaseFrUPSIDC;

	public String getLandPurchaseFrUPSIDC() {
		return landPurchaseFrUPSIDC;
	}

	public void setLandPurchaseFrUPSIDC(String landPurchaseFrUPSIDC) {
		this.landPurchaseFrUPSIDC = landPurchaseFrUPSIDC;
	}

	public Long getTtlCostDpr() {
		return ttlCostDpr;
	}

	public void setTtlCostDpr(Long ttlCostDpr) {
		this.ttlCostDpr = ttlCostDpr;
	}

	public Long getTotal() {
		return total;
	}

	public void setTotal(Long total) {
		this.total = total;
	}

	public Long getAnnualTurnOver() {
		return annualTurnOver;
	}

	public void setAnnualTurnOver(Long annualTurnOver) {
		this.annualTurnOver = annualTurnOver;
	}

	public Long getTotalAmountOfInvest() {
		return totalAmountOfInvest;
	}

	public void setTotalAmountOfInvest(Long totalAmountOfInvest) {
		this.totalAmountOfInvest = totalAmountOfInvest;
	}

	public Long getTotalIncrement() {
		return totalIncrement;
	}

	public void setTotalIncrement(Long totalIncrement) {
		this.totalIncrement = totalIncrement;
	}

	public Long getTtlcostProAppFrBank() {
		return ttlcostProAppFrBank;
	}

	public void setTtlcostProAppFrBank(Long ttlcostProAppFrBank) {
		this.ttlcostProAppFrBank = ttlcostProAppFrBank;
	}

	public Long getTtlAmtExitProj() {
		return ttlAmtExitProj;
	}

	public void setTtlAmtExitProj(Long ttlAmtExitProj) {
		this.ttlAmtExitProj = ttlAmtExitProj;
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

	public String getStatutoryAuditorDocbase64File() {
		return statutoryAuditorDocbase64File;
	}

	public void setStatutoryAuditorDocbase64File(String statutoryAuditorDocbase64File) {
		this.statutoryAuditorDocbase64File = statutoryAuditorDocbase64File;
	}

	public String getPurchasePriceDocbase64File() {
		return purchasePriceDocbase64File;
	}

	public void setPurchasePriceDocbase64File(String purchasePriceDocbase64File) {
		this.purchasePriceDocbase64File = purchasePriceDocbase64File;
	}

	public String getStampDutyDocbase64File() {
		return stampDutyDocbase64File;
	}

	public void setStampDutyDocbase64File(String stampDutyDocbase64File) {
		this.stampDutyDocbase64File = stampDutyDocbase64File;
	}

	public String getRegistrationFeeDocbase64File() {
		return registrationFeeDocbase64File;
	}

	public void setRegistrationFeeDocbase64File(String registrationFeeDocbase64File) {
		this.registrationFeeDocbase64File = registrationFeeDocbase64File;
	}

	public String getBanksAuctionDocbase64File() {
		return banksAuctionDocbase64File;
	}

	public void setBanksAuctionDocbase64File(String banksAuctionDocbase64File) {
		this.banksAuctionDocbase64File = banksAuctionDocbase64File;
	}

	public String getCivilWorksDocbase64File() {
		return civilWorksDocbase64File;
	}

	public void setCivilWorksDocbase64File(String civilWorksDocbase64File) {
		this.civilWorksDocbase64File = civilWorksDocbase64File;
	}

	public String getMachineryMiscDocbase64File() {
		return machineryMiscDocbase64File;
	}

	public void setMachineryMiscDocbase64File(String machineryMiscDocbase64File) {
		this.machineryMiscDocbase64File = machineryMiscDocbase64File;
	}

	public String getStatutoryAuditorDoc() {
		return statutoryAuditorDoc;
	}

	public void setStatutoryAuditorDoc(String statutoryAuditorDoc) {
		this.statutoryAuditorDoc = statutoryAuditorDoc;
	}

	public String getPurchasePriceDoc() {
		return purchasePriceDoc;
	}

	public void setPurchasePriceDoc(String purchasePriceDoc) {
		this.purchasePriceDoc = purchasePriceDoc;
	}

	public String getStampDutyDoc() {
		return stampDutyDoc;
	}

	public void setStampDutyDoc(String stampDutyDoc) {
		this.stampDutyDoc = stampDutyDoc;
	}

	public String getRegistrationFeeDoc() {
		return registrationFeeDoc;
	}

	public void setRegistrationFeeDoc(String registrationFeeDoc) {
		this.registrationFeeDoc = registrationFeeDoc;
	}

	public String getBanksAuctionDoc() {
		return banksAuctionDoc;
	}

	public void setBanksAuctionDoc(String banksAuctionDoc) {
		this.banksAuctionDoc = banksAuctionDoc;
	}

	public String getCivilWorksDoc() {
		return civilWorksDoc;
	}

	public void setCivilWorksDoc(String civilWorksDoc) {
		this.civilWorksDoc = civilWorksDoc;
	}

	public String getMachineryMiscDoc() {
		return machineryMiscDoc;
	}

	public void setMachineryMiscDoc(String machineryMiscDoc) {
		this.machineryMiscDoc = machineryMiscDoc;
	}

	public ExistProjDisbursement() {
		super();
	}

	public String getProjDisId() {
		return projDisId;
	}

	public void setProjDisId(String projDisId) {
		this.projDisId = projDisId;
	}

	public Long getProjDisLandCost() {
		return projDisLandCost;
	}

	public void setProjDisLandCost(Long projDisLandCost) {
		this.projDisLandCost = projDisLandCost;
	}

	public Long getProjDisBldgCost() {
		return projDisBldgCost;
	}

	public void setProjDisBldgCost(Long projDisBldgCost) {
		this.projDisBldgCost = projDisBldgCost;
	}

	public Long getProjDisPlantMachCost() {
		return projDisPlantMachCost;
	}

	public void setProjDisPlantMachCost(Long projDisPlantMachCost) {
		this.projDisPlantMachCost = projDisPlantMachCost;
	}

	public Long getProjDisConstruct() {
		return projDisConstruct;
	}

	public void setProjDisConstruct(Long projDisConstruct) {
		this.projDisConstruct = projDisConstruct;
	}

	public Long getProjDisInfra() {
		return projDisInfra;
	}

	public void setProjDisInfra(Long projDisInfra) {
		this.projDisInfra = projDisInfra;
	}

	public String getProjApcId() {
		return projApcId;
	}

	public void setProjApcId(String projApcId) {
		this.projApcId = projApcId;
	}

	public Long getProjLandIncrement() {
		return projLandIncrement;
	}

	public void setProjLandIncrement(Long projLandIncrement) {
		this.projLandIncrement = projLandIncrement;
	}

	public Long getProjBuildIncrement() {
		return projBuildIncrement;
	}

	public void setProjBuildIncrement(Long projBuildIncrement) {
		this.projBuildIncrement = projBuildIncrement;
	}

	public Long getProjConstructIncrement() {
		return projConstructIncrement;
	}

	public void setProjConstructIncrement(Long projConstructIncrement) {
		this.projConstructIncrement = projConstructIncrement;
	}

	public Long getProjPlantIncrement() {
		return projPlantIncrement;
	}

	public void setProjPlantIncrement(Long projPlantIncrement) {
		this.projPlantIncrement = projPlantIncrement;
	}

	public Long getProjInfraIncrement() {
		return projInfraIncrement;
	}

	public void setProjInfraIncrement(Long projInfraIncrement) {
		this.projInfraIncrement = projInfraIncrement;
	}

	public String getProjDisExpandOrDiverse() {
		return projDisExpandOrDiverse;
	}

	public void setProjDisExpandOrDiverse(String projDisExpandOrDiverse) {
		this.projDisExpandOrDiverse = projDisExpandOrDiverse;
	}

	public String getProjDisCreatedBy() {
		return projDisCreatedBy;
	}

	public void setProjDisCreatedBy(String projDisCreatedBy) {
		this.projDisCreatedBy = projDisCreatedBy;
	}

	public Date getProjDisModifiyDate() {
		return projDisModifiyDate;
	}

	public void setProjDisModifiyDate(Date projDisModifiyDate) {
		this.projDisModifiyDate = projDisModifiyDate;
	}

	public Date getProjDisCreatedDate() {
		return projDisCreatedDate;
	}

	public void setProjDisCreatedDate(Date projDisCreatedDate) {
		this.projDisCreatedDate = projDisCreatedDate;
	}

	public Long getNewprojLandCost() {
		return newprojLandCost;
	}

	public void setNewprojLandCost(Long newprojLandCost) {
		this.newprojLandCost = newprojLandCost;
	}

	public Long getNewprojBldgCost() {
		return newprojBldgCost;
	}

	public void setNewprojBldgCost(Long newprojBldgCost) {
		this.newprojBldgCost = newprojBldgCost;
	}

	public Long getNewprojPlantMachCost() {
		return newprojPlantMachCost;
	}

	public void setNewprojPlantMachCost(Long newprojPlantMachCost) {
		this.newprojPlantMachCost = newprojPlantMachCost;
	}

	public Long getNewprojInfra() {
		return newprojInfra;
	}

	public void setNewprojInfra(Long newprojInfra) {
		this.newprojInfra = newprojInfra;
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

	public String getEncCertificateFName() {
		return encCertificateFName;
	}

	public void setEncCertificateFName(String encCertificateFName) {
		this.encCertificateFName = encCertificateFName;
	}

	public String getEncCertificateFNamebase64File() {
		return encCertificateFNamebase64File;
	}

	public void setEncCertificateFNamebase64File(String encCertificateFNamebase64File) {
		this.encCertificateFNamebase64File = encCertificateFNamebase64File;
	}

	public Long getTotalLoan() {
		return totalLoan;
	}

	public void setTotalLoan(Long totalLoan) {
		this.totalLoan = totalLoan;
	}

	public Long getTotalInterest() {
		return totalInterest;
	}

	public void setTotalInterest(Long totalInterest) {
		this.totalInterest = totalInterest;
	}

	public Long getNewprojMiscFixedAsset() {
		return newprojMiscFixedAsset;
	}

	public void setNewprojMiscFixedAsset(Long newprojMiscFixedAsset) {
		this.newprojMiscFixedAsset = newprojMiscFixedAsset;
	}

	public Long getExProjMiscFixedAsset() {
		return exProjMiscFixedAsset;
	}

	public void setExProjMiscFixedAsset(Long exProjMiscFixedAsset) {
		this.exProjMiscFixedAsset = exProjMiscFixedAsset;
	}

	public Long getExMiscExpDiv() {
		return exMiscExpDiv;
	}

	public void setExMiscExpDiv(Long exMiscExpDiv) {
		this.exMiscExpDiv = exMiscExpDiv;
	}

	public Long getExMiscIncrement() {
		return exMiscIncrement;
	}

	public void setExMiscIncrement(Long exMiscIncrement) {
		this.exMiscIncrement = exMiscIncrement;
	}

}
