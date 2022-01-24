package com.webapp.ims.dis.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.format.annotation.DateTimeFormat;

public class AgendaReportDisBean {

	private String evaluateId;

	private String apcId;

	// Top Table

	private String companyName;

	private String product;

	private String region;

	private String district;

	private Long investment;

	private String category;

	private String addPromotersDetails;

	// The Proposal- Company’s Request for Disbursement of
	// Incentives-----------------

	private String addOfRegisCompl;

	private String addOfRegisObserv;

	private String addOfFactoryCompl;

	private String addOfFactoryObserv;

	private String constiOfCompl;

	private String constiOfObserv;

	private String dateOfStartCompl;

	private String dateOfStartObserv;

	private String newUnitExpanCompl;

	private String newUnitExpanObserv;

	private String productWiseCompl;

	private String productWiseObserv;

	private String gstnNoOfCompl;

	private String gstnNoOfObserv;

	private String panNoOfCompl;

	private String panNoOfObserv;

	// Break-up of Cost of Project- Investment Details--------------------------

	// Land

	private Long landAmtInv;

	private Long landPerDpr;

	private Long landBankApprai;

	private Long landPerCerti;

	private Long landCapInvCA;

	private Long landafterinv;

	private Long landCapInvValuer;

	private Long landStatutoryAudit;

	// Building and Civil Works

	private Long buildAmtInv;

	private Long buildPerDpr;

	private Long buildBankApprai;

	private Long buildPerCerti;

	private Long buildCapInvCA;

	private Long buildafterinv;

	private Long buildCapInvValuer;

	private Long buildStatutoryAudit;

	// Plant & Machinery

	private Long plantAmtInv;

	private Long plantPerDpr;

	private Long plantBankApprai;

	private Long plantPerCertificate;

	private Long plantCapInvCA;

	private Long plantCapInvValuer;

	private Long plantStatutoryAudit;

	private Long plantdafterinv;

	// Misc. Fixed Assets

	private Long miscAmtInve;

	private Long miscPerDpr;

	private Long miscBankApprai;

	private Long miscPerCertificate;

	private Long miscCapInvCA;

	private Long miscCapInvValuer;

	private Long miscStatutoryAuditor;

	private Long miscafterinv;

	// SubTotal (A) (1+2+3+4)

	private Long subTtlAAmtInv;

	private Long subTtlAPerDpr;

	private Long subTtlABankApprai;

	private Long subTtlAPerCerti;

	private Long subTtlACapInvCA;

	private Long subTtlACapInvValuer;

	private Long subTtlAStatutoryAudit;

	private Long subTtlAafterinv;

	// Provision for Cost Escalation & Contingencies

	private Long provisionAmtInve;

	private Long provisionPerDpr;

	private Long provisionBankApprai;

	private Long provisionPerCerti;

	private Long provisionCapInvCA;

	private Long provisionCapInvValuer;

	private Long provisionStatutoryAudit;

	private Long provisionafterinv;

	// Preliminary & Preoperative Expenses

	private Long prelimAmtInve;

	private Long prelimPerDpr;

	private Long prelimBankApprai;

	private Long prelimPerCerti;

	private Long prelimCapInvCA;

	private Long prelimCapInvValuer;

	private Long prelimStatutoryAudit;

	private Long prelimafterinv;

	// Interest During Construction Period

	private Long interestAmtInve;

	private Long interestPerDpr;

	private Long interestBankApprai;

	private Long interestPerCerti;

	private Long interestCapInvCA;

	private Long interestCapInvValuer;

	private Long interestStatutoryAudit;

	private Long interestafterinv;

	// Margin Money for Working Capital

	private Long marginAmtInve;

	private Long marginPerDpr;

	private Long marginBankApprai;

	private Long marginPerCerti;

	private Long marginCapInvCA;

	private Long marginCapInvValuer;

	private Long marginStatutoryAudit;

	private Long marginafterinv;

	// Other, If any

	private Long otherAmtInve;

	private Long otherPerDpr;

	private Long otherBankApprai;

	private Long otherPerCerti;

	private Long otherCapInvCA;

	private Long otherCapInvValuer;

	private Long otherStatutoryAudit;

	private Long otherafterinv;

	// SubTotal (B) (5+6+7+8+9)

	private Long subTtlBAmtInv;

	private Long subTtlBPerDpr;

	private Long subTtlBBankApprai;

	private Long subTtlBPerCerti;

	private Long subTtlBCapInvCA;

	private Long subTtlBCapInvValuer;

	private Long subTtlBStatutoryAudit;

	private Long subTtlBCafterinv;

	// Total (A+B)

	private Long ttlAmtInve;

	private Long ttlPerDpr;

	private Long TtlBankApprai;

	private Long ttlPerCerti;

	private Long ttlCapInvCA;

	private Long ttlCapInvValuer;

	private Long ttlStatutoryAudit;

	private String breakUpCostObserve;

	private String ttlafterinv;

//Means Of Financing-------------------------------------------------------

	// Equity Share Capital

	private Long equityCapPerDpr;

	private Long equityCapBankApprai;

	private Long equityCapPerCerti;

	private Long equityCapCapInvCA;

	private Long equAftinvdate;

	private Long equAftinvproddate;

	private Long equityCapStatutoryAudit;

	// Internal Cash Accruals

	private Long intCashPerDpr;

	private Long intCashBankApprai;

	private Long intCashPerCerti;

	private Long intCashCapInvCA;

	private Long intCashStatutoryAudit;

	private Long intCashAftinvdate;

	private Long intCashAftinvproddate;

	// Interest Free Unsecured Loans & Promoter’s contribution

	private Long intFreePerDpr;

	private Long intFreeBankApprai;

	private Long intFreePerCerti;

	private Long intFreeCapInvCA;

	private Long intFreeStatutoryAudit;

	private Long intFreeAftinvdate;

	private Long intFreeAftinvproddate;

	// Other

	private Long finOthPerDpr;

	private Long finOthBankApprai;

	private Long finOthPerCerti;

	private Long finOthCapInvCA;

	private Long finOthStatutoryAudit;

	private Long finOtheAftinvdate;

	private Long finOthAftinvproddate;

	// Security Deposit

	private Long SecPerDpr;

	private Long SecBankApprai;

	private Long SecPerCerti;

	private Long SecCapInvCA;

	private Long SecStatutoryAudit;

	private Long SeceAftinvdate;

	private Long SecAftinvproddate;

	// Advances from Dealers

	private Long dealPerDpr;

	private Long dealBankApprai;

	private Long dealPerCerti;

	private Long dealCapInvCA;

	private Long dealStatutoryAudit;

	private Long dealeAftinvdate;

	private Long dealAftinvproddate;

	// From FI’s

	private Long FromFiPerDpr;

	private Long FromFiBankApprai;

	private Long FromFiPerCerti;

	private Long FromFiCapInvCA;

	private Long FromFiStatutoryAudit;

	private Long FromFieAftinvdate;

	private Long FromFiAftinvproddate;

	// From Bank

	private Long FrBankPerDpr;

	private Long FrBankBankApprai;

	private Long FrBankPerCerti;

	private Long FrBankCapInvCA;

	private Long FrBankStatutoryAudit;

	private Long frBankeAftinvdate;

	private Long frBankAftinvproddate;

	/*
	 * // Other Loan
	 * 
	 * @Column(name = " FinOthL_Per_Dpr") private Long finOthLPerDpr;
	 * 
	 * @Column(name = "FinOthL_Bank_Apprai") private Long finOthLBankApprai;
	 * 
	 * @Column(name = "FinOthL_Per_Certi") private Long finOthLPerCerti;
	 * 
	 * @Column(name = "FinOthL_CapInv_CA") private Long finOthLCapInvCA;
	 * 
	 * @Column(name = "FinOthL_Statutory_Audit") private Long finOthLStatutoryAudit;
	 * 
	 * @Column(name = "FinOthL_inv_date") private Long finOthLeAftinvdate;
	 * 
	 * @Column(name = "FinOthL_inv_proddate") private Long finOthLAftinvproddate;
	 */

	// For Plant & Machinery

	private Long PlantMachPerDpr;

	private Long PlantMachBankApprai;

	private Long Plant_MachPerCerti;

	private Long PlantMachCapInvCA;

	private Long PlantMachStatutoryAudit;

	private Long Plant_MacheAftinvdate;

	private Long Plant_MachAftinvproddate;

	// Total

	private Long finTttlPerDpr;

	private Long finTttlBankApprai;

	private Long finTttlPerCerti;

	private Long finTttlCapInvCA;

	private Long finTttlStatutoryAudit;

	private String financingObserve;

	private Long finTttldate;

	private Long finTttlproddate;

	// Details of Incentive Claimed (As per
	// LoC)-----------------------------------------------

	private Long ISF_Claim_Reim;

	private String sgstRemark;

	private Long ISF_Reim_SCST;

	private String scstRemark;

	private Long ISF_Reim_FW;

	private String fwRemark;

	private Long ISF_Reim_BPLW;

	private String bplRemark;

	private Long ttlIncAmt;

	// New Incentive

	private Long ISF_Stamp_Duty_EX;

	private String stampDutyExemptRemark;

	private Long ISF_Amt_Stamp_Duty_Reim;

	private String stampDutyReimRemark;

	private Long ISF_Additonal_Stamp_Duty_EX;

	private String divyangSCSTRemark;

	private Long ISF_Epf_Reim_UW;

	private String epfUnsklRemark;

	private Long ISF_Add_Epf_Reim_SkUkW;

	private String epfSklUnsklRemark;

	private Long ISF_Add_Epf_Reim_DIVSCSTF;

	private String epfDvngSCSTRemark;

	private Long ISF_Cis;

	private String capIntSubRemark;

	private Long ISF_ACI_Subsidy_Indus;

	private String aciSubsidyRemark;

	private Long ISF_Infra_Int_Subsidy;

	private String infraIntSubRemark;

	private Long ISF_AII_Subsidy_DIVSCSTF;

	private String aiiSubsidyRemark;

	private Long ISF_Loan_Subsidy;

	private String loanIntSubRemark;

	private Long ISF_Tax_Credit_Reim;

	private String inputTaxRemark;

	private Long ISF_EX_E_Duty;

	private String elecDutyCapRemark;

	private Long ISF_EX_E_Duty_PC;

	private String elecDutyDrawnRemark;

	private Long ISF_EX_Mandee_Fee;

	private String mandiFeeRemark;

	private Long ISF_Indus_Payroll_Asst;

	private String diffAbleWorkRemark;

	// Turnover of Base
	// Production-------------------------------------------------------
	// 1

	private String finYr1;

	private Long turnoverOfSales1;

	private Long turnoverProduction1;
	// 2

	private String finYr2;

	private Long turnoverOfSales2;

	private Long turnoverProduction2;

	// 3

	private String finYr3;

	private Long turnoverOfSales3;

	private Long turnoverProduction3;

	// 4

	private String finYr4;

	private Long turnoverOfSales4;

	private Long turnoverProduction4;

//5

	private String finYr5;

	private Long turnoverOfSales5;

	private Long turnoverProduction5;

	private String turnOverObserve;

	// SGST- Amount of new

	private String durationFinYr1New;

	private Long amtOfNetSGST1New;

	private double amtOfNetSGSTReim1New;

	private String durationFinYr2New;

	private Long amtOfNetSGST2New;

	private double amtOfNetSGSTReim2New;

	private String durationFinYr3New;

	private Long amtOfNetSGST3New;

	private double amtOfNetSGSTReim3New;

	private String durationFinYr4New;

	private Long amtOfNetSGST4New;

	private double amtOfNetSGSTReim4New;

	// SGST- Amount of
	// Reimbursement----------------------------------------------------------------
	// 1

	private String durationFinYr1;

	private Long turnoverOfProduction1;

	private Long ttlAmtCommTax1;

	private Long amtOfNetSGST1;

	private Long increTurnover1;

	private Long increNetSGST1;

	private Long amtOfNetSGSTReim1;
	// 2

	private String durationFinYr2;

	private Long turnoverOfProduction2;

	private Long ttlAmtCommTax2;

	private Long amtOfNetSGST2;

	private Long increTurnover2;

	private Long increNetSGST2;

	private Long amtOfNetSGSTReim2;
	// 3

	private String durationFinYr3;

	private Long turnoverOfProduction3;

	private Long ttlAmtCommTax3;

	private Long amtOfNetSGST3;

	private Long increTurnover3;

	private Long increNetSGST3;

	private Long amtOfNetSGSTReim3;
	// 4

	private String durationFinYr4;

	private Long turnoverOfProduction4;

	private Long ttlAmtCommTax4;

	private Long amtOfNetSGST4;

	private Long increTurnover4;

	private Long increNetSGST4;

	private Long amtOfNetSGSTReim4;

	private String durationFinYr5;

	private Long turnoverOfProduction5;

	private Long ttlAmtCommTax5;

	private Long amtOfNetSGST5;

	private Long increTurnover5;

	private Long increNetSGST5;

	private Long amtOfNetSGSTReim5;

	private String sgstAmtReimObserve;

	private String admissibleBenefits;

	// CIS Table Observ

	private String cisTblObserve;

	// Eligibility of
	// Benefits-------------------------------------------------------------------
	// STATUS OF COMPLIANCE OF ELIGIBILITY CRITERIA FOR DISBURSEMENT

	// 1

	private String iemStatusCompl;

	private String iemStatusObserve;

	// 2

	private String newExpDivCompl;

	private String newExpDivObserve;

	// 3

	private String proTtlInvCompl;

	private String proTtlInvObserve;

	// 4

	private String CutOffDateCompl;

	private String cutOffDateObserve;

	// 5

	private String dateCommProCompl;

	private String dateCommProObserve;

	// 6

	private String invPeriodCompl;

	private String invPeriodObserve;

	// 7

	private String projPhasesCompl;

	private String projPhasesObserve;

	// 8

	private String dirIndirWorkersCompl;

	private String dirIndirWorkersObserve;

	// 9

	private String dprCompl;

	private String dprObserve;

	// 10

	private String listOfAssetsCompl;

	private String listOfAssetsObserve;

	// 11

	private String undertakingCompl;

	private String undertakingObserve;

	// 12

	private String authSignCompl;

	private String authSignObserve;

	// 13

	private String applFormatCompl;

	private String applFormatObserve;

	// 14

	private String suppDocDirCompl;

	private String suppDocDirObserve;

	// 16

	private String bankApprCompl;

	private String bankApprObserve;

	private String eligOfBenefitsNote;

	private String eligOfBenefitsComments;

// Quantum of Benefits-------------------------------------------------------------

	private Long ttlEligAmt;

	private Long propDisbAmt;

	private Long eligBenefitsAmt;

	private Long balancePeriodAmt;

// Disbursement effected till date-------------------------------------------------------		

	private String disbEffDate;

	private String disbEffComments;

// Proposal for Consideration------------------------------------------------------------------			

	private String ttlEligIncentives;

	private String dateAdmissibilityInc;

// Compliance of Conditions---------------------------------------

	private String complCondiComments;

//04-02-2021	create New Column

	private String wheProdSetupPhs;

	private String wheProdSetupPhsObserv;

	private Date optCutofDate;

	// CIS- Computation Methodology & Amount Cost of Project

	private Date doFirstLoanCIS;

	private Date doLastLoanCIS;

	private Date doFirstDisCIS;

	private Date doLastDisCIS;
	private Date doPaymentCIS;

	///// IIS Comp...

	private Date doFirstLoanIIS;

	private Date doLastLoanIIS;

	private Date doFirstDisIIS;

	private Date doLastDisIIS;

	private Date doPaymentIIS;

	private Long cisCostOfProjectAmt;

	private Long cisPlantMachAmt;

	private Long cisEntireProjectAmt;

	private Double cisTermPlantMachAmt;

	private Long cisApplROIAmt;

	private Long cisIntPaidAmt;

	private String cisRoiPMAmt;

	private Double cisIntPM5Amt;

	private String cisObserve;

	private String confProvbyCTD;

	private String confProvbyBank;

	private String externalERD;

	// transiant value for mongo
	private transient String confProvbyCTDDoc;
	private transient String confProvbyBankDoc;
	private transient String externalERDDoc;

	private transient String confProvbyCTDDocBase64;
	private transient String confProvbyBankDocBase64;
	private transient String externalERDDocBase64;

	/// IIS Model Start
	private String clamSactniisObserv;
	
	private String eligibleAmtId;


	private String fYI;


	private String intPMI;


	private String dateofDISI;


	private String actAmtIPI;


	private String dateofPI;


	private String propIntRoiI;


	private String propIntPAI;

	// II Row

	private String fYII;


	private String intPMII;


	private String dateofDISII;


	private String actAmtIPII;


	private String dateofPII;


	private String propIntRoiII;


	private String propIntPAII;

	// III Row

	private String fYIII;


	private String intPMIII;


	private String dateofDISIII;


	private String actAmtIPIII;


	private String dateofPIII;


	private String propIntRoiIII;


	private String propIntPAIII;

	// IV Row

	private String fYIV;


	private String intPMIV;


	private String dateofDISIV;


	private String actAmtIPIV;


	private String dateofPIV;


	private String propIntRoiIV;


	private String propIntPAIV;

	// V Row

	private String fYV;


	private String intPMV;


	private String dateofDISV;


	private String actAmtIPV;


	private String dateofPV;


	private String propIntRoiV;


	private String propIntPAV;


	private String ElgAmtcisObserv;

 
	private String eligibleAmtIISId;

 
	private String apcIdIIS;

	// I Row
 
	private String iisFinYr1;
 
	private String iisTtlLoanAmt1;


	private String iisDateOfDisb1;


	private String iisActAmtIP1;


	private String dateOfPay1;


	private String propIntInfra1;


	private String propIntInfraPA1;


	private String eligibleIIS1;

	// II Row

	private String iisFinYr2;


	private String iisTtlLoanAmt2;


	private String iisDateOfDisb2;


	private String iisActAmtIP2;


	private String dateOfPay2;


	private String propIntInfra2;


	private String propIntInfraPA2;


	private String eligibleIIS2;



	private String iisFinYr3;


	private String iisTtlLoanAmt3;


	private String iisDateOfDisb3;


	private String iisActAmtIP3;


	private String dateOfPay3;


	private String propIntInfra3;


	private String propIntInfraPA3;


	private String eligibleIIS3;

	// IV Row
	
	private String iisFinYr4;

	
	private String iisTtlLoanAmt4;

	
	private String iisDateOfDisb4;

	
	private String iisActAmtIP4;

	
	private String dateOfPay4;

	
	private String propIntInfra4;

	
	private String propIntInfraPA4;

	
	private String eligibleIIS4;

	// V Row
	
	private String iisFinYr5;

	
	private String iisTtlLoanAmt5;

	
	private String iisDateOfDisb5;

	
	private String iisActAmtIP5;

	
	private String dateOfPay5;

	
	private String propIntInfra5;

	
	private String propIntInfraPA5;

	
	private String eligibleIIS5;

	
	private String eligAmtIisObserv;

	
	
	public String getEligibleAmtIISId() {
		return eligibleAmtIISId;
	}

	public void setEligibleAmtIISId(String eligibleAmtIISId) {
		this.eligibleAmtIISId = eligibleAmtIISId;
	}

	public String getApcIdIIS() {
		return apcIdIIS;
	}

	public void setApcIdIIS(String apcIdIIS) {
		this.apcIdIIS = apcIdIIS;
	}

	public String getIisFinYr1() {
		return iisFinYr1;
	}

	public void setIisFinYr1(String iisFinYr1) {
		this.iisFinYr1 = iisFinYr1;
	}

	public String getIisTtlLoanAmt1() {
		return iisTtlLoanAmt1;
	}

	public void setIisTtlLoanAmt1(String iisTtlLoanAmt1) {
		this.iisTtlLoanAmt1 = iisTtlLoanAmt1;
	}

	public String getIisDateOfDisb1() {
		return iisDateOfDisb1;
	}

	public void setIisDateOfDisb1(String iisDateOfDisb1) {
		this.iisDateOfDisb1 = iisDateOfDisb1;
	}

	public String getIisActAmtIP1() {
		return iisActAmtIP1;
	}

	public void setIisActAmtIP1(String iisActAmtIP1) {
		this.iisActAmtIP1 = iisActAmtIP1;
	}

	public String getDateOfPay1() {
		return dateOfPay1;
	}

	public void setDateOfPay1(String dateOfPay1) {
		this.dateOfPay1 = dateOfPay1;
	}

	public String getPropIntInfra1() {
		return propIntInfra1;
	}

	public void setPropIntInfra1(String propIntInfra1) {
		this.propIntInfra1 = propIntInfra1;
	}

	public String getPropIntInfraPA1() {
		return propIntInfraPA1;
	}

	public void setPropIntInfraPA1(String propIntInfraPA1) {
		this.propIntInfraPA1 = propIntInfraPA1;
	}

	public String getEligibleIIS1() {
		return eligibleIIS1;
	}

	public void setEligibleIIS1(String eligibleIIS1) {
		this.eligibleIIS1 = eligibleIIS1;
	}

	public String getIisFinYr2() {
		return iisFinYr2;
	}

	public void setIisFinYr2(String iisFinYr2) {
		this.iisFinYr2 = iisFinYr2;
	}

	public String getIisTtlLoanAmt2() {
		return iisTtlLoanAmt2;
	}

	public void setIisTtlLoanAmt2(String iisTtlLoanAmt2) {
		this.iisTtlLoanAmt2 = iisTtlLoanAmt2;
	}

	public String getIisDateOfDisb2() {
		return iisDateOfDisb2;
	}

	public void setIisDateOfDisb2(String iisDateOfDisb2) {
		this.iisDateOfDisb2 = iisDateOfDisb2;
	}

	public String getIisActAmtIP2() {
		return iisActAmtIP2;
	}

	public void setIisActAmtIP2(String iisActAmtIP2) {
		this.iisActAmtIP2 = iisActAmtIP2;
	}

	public String getDateOfPay2() {
		return dateOfPay2;
	}

	public void setDateOfPay2(String dateOfPay2) {
		this.dateOfPay2 = dateOfPay2;
	}

	public String getPropIntInfra2() {
		return propIntInfra2;
	}

	public void setPropIntInfra2(String propIntInfra2) {
		this.propIntInfra2 = propIntInfra2;
	}

	public String getPropIntInfraPA2() {
		return propIntInfraPA2;
	}

	public void setPropIntInfraPA2(String propIntInfraPA2) {
		this.propIntInfraPA2 = propIntInfraPA2;
	}

	public String getEligibleIIS2() {
		return eligibleIIS2;
	}

	public void setEligibleIIS2(String eligibleIIS2) {
		this.eligibleIIS2 = eligibleIIS2;
	}

	public String getIisFinYr3() {
		return iisFinYr3;
	}

	public void setIisFinYr3(String iisFinYr3) {
		this.iisFinYr3 = iisFinYr3;
	}

	public String getIisTtlLoanAmt3() {
		return iisTtlLoanAmt3;
	}

	public void setIisTtlLoanAmt3(String iisTtlLoanAmt3) {
		this.iisTtlLoanAmt3 = iisTtlLoanAmt3;
	}

	public String getIisDateOfDisb3() {
		return iisDateOfDisb3;
	}

	public void setIisDateOfDisb3(String iisDateOfDisb3) {
		this.iisDateOfDisb3 = iisDateOfDisb3;
	}

	public String getIisActAmtIP3() {
		return iisActAmtIP3;
	}

	public void setIisActAmtIP3(String iisActAmtIP3) {
		this.iisActAmtIP3 = iisActAmtIP3;
	}

	public String getDateOfPay3() {
		return dateOfPay3;
	}

	public void setDateOfPay3(String dateOfPay3) {
		this.dateOfPay3 = dateOfPay3;
	}

	public String getPropIntInfra3() {
		return propIntInfra3;
	}

	public void setPropIntInfra3(String propIntInfra3) {
		this.propIntInfra3 = propIntInfra3;
	}

	public String getPropIntInfraPA3() {
		return propIntInfraPA3;
	}

	public void setPropIntInfraPA3(String propIntInfraPA3) {
		this.propIntInfraPA3 = propIntInfraPA3;
	}

	public String getEligibleIIS3() {
		return eligibleIIS3;
	}

	public void setEligibleIIS3(String eligibleIIS3) {
		this.eligibleIIS3 = eligibleIIS3;
	}

	public String getIisFinYr4() {
		return iisFinYr4;
	}

	public void setIisFinYr4(String iisFinYr4) {
		this.iisFinYr4 = iisFinYr4;
	}

	public String getIisTtlLoanAmt4() {
		return iisTtlLoanAmt4;
	}

	public void setIisTtlLoanAmt4(String iisTtlLoanAmt4) {
		this.iisTtlLoanAmt4 = iisTtlLoanAmt4;
	}

	public String getIisDateOfDisb4() {
		return iisDateOfDisb4;
	}

	public void setIisDateOfDisb4(String iisDateOfDisb4) {
		this.iisDateOfDisb4 = iisDateOfDisb4;
	}

	public String getIisActAmtIP4() {
		return iisActAmtIP4;
	}

	public void setIisActAmtIP4(String iisActAmtIP4) {
		this.iisActAmtIP4 = iisActAmtIP4;
	}

	public String getDateOfPay4() {
		return dateOfPay4;
	}

	public void setDateOfPay4(String dateOfPay4) {
		this.dateOfPay4 = dateOfPay4;
	}

	public String getPropIntInfra4() {
		return propIntInfra4;
	}

	public void setPropIntInfra4(String propIntInfra4) {
		this.propIntInfra4 = propIntInfra4;
	}

	public String getPropIntInfraPA4() {
		return propIntInfraPA4;
	}

	public void setPropIntInfraPA4(String propIntInfraPA4) {
		this.propIntInfraPA4 = propIntInfraPA4;
	}

	public String getEligibleIIS4() {
		return eligibleIIS4;
	}

	public void setEligibleIIS4(String eligibleIIS4) {
		this.eligibleIIS4 = eligibleIIS4;
	}

	public String getIisFinYr5() {
		return iisFinYr5;
	}

	public void setIisFinYr5(String iisFinYr5) {
		this.iisFinYr5 = iisFinYr5;
	}

	public String getIisTtlLoanAmt5() {
		return iisTtlLoanAmt5;
	}

	public void setIisTtlLoanAmt5(String iisTtlLoanAmt5) {
		this.iisTtlLoanAmt5 = iisTtlLoanAmt5;
	}

	public String getIisDateOfDisb5() {
		return iisDateOfDisb5;
	}

	public void setIisDateOfDisb5(String iisDateOfDisb5) {
		this.iisDateOfDisb5 = iisDateOfDisb5;
	}

	public String getIisActAmtIP5() {
		return iisActAmtIP5;
	}

	public void setIisActAmtIP5(String iisActAmtIP5) {
		this.iisActAmtIP5 = iisActAmtIP5;
	}

	public String getDateOfPay5() {
		return dateOfPay5;
	}

	public void setDateOfPay5(String dateOfPay5) {
		this.dateOfPay5 = dateOfPay5;
	}

	public String getPropIntInfra5() {
		return propIntInfra5;
	}

	public void setPropIntInfra5(String propIntInfra5) {
		this.propIntInfra5 = propIntInfra5;
	}

	public String getPropIntInfraPA5() {
		return propIntInfraPA5;
	}

	public void setPropIntInfraPA5(String propIntInfraPA5) {
		this.propIntInfraPA5 = propIntInfraPA5;
	}

	public String getEligibleIIS5() {
		return eligibleIIS5;
	}

	public void setEligibleIIS5(String eligibleIIS5) {
		this.eligibleIIS5 = eligibleIIS5;
	}

	public String getEligAmtIisObserv() {
		return eligAmtIisObserv;
	}

	public void setEligAmtIisObserv(String eligAmtIisObserv) {
		this.eligAmtIisObserv = eligAmtIisObserv;
	}

	public String getEligibleAmtId() {
		return eligibleAmtId;
	}

	public void setEligibleAmtId(String eligibleAmtId) {
		this.eligibleAmtId = eligibleAmtId;
	}

	public String getfYI() {
		return fYI;
	}

	public void setfYI(String fYI) {
		this.fYI = fYI;
	}

	public String getIntPMI() {
		return intPMI;
	}

	public void setIntPMI(String intPMI) {
		this.intPMI = intPMI;
	}

	public String getDateofDISI() {
		return dateofDISI;
	}

	public void setDateofDISI(String dateofDISI) {
		this.dateofDISI = dateofDISI;
	}

	public String getActAmtIPI() {
		return actAmtIPI;
	}

	public void setActAmtIPI(String actAmtIPI) {
		this.actAmtIPI = actAmtIPI;
	}

	public String getDateofPI() {
		return dateofPI;
	}

	public void setDateofPI(String dateofPI) {
		this.dateofPI = dateofPI;
	}

	public String getPropIntRoiI() {
		return propIntRoiI;
	}

	public void setPropIntRoiI(String propIntRoiI) {
		this.propIntRoiI = propIntRoiI;
	}

	public String getPropIntPAI() {
		return propIntPAI;
	}

	public void setPropIntPAI(String propIntPAI) {
		this.propIntPAI = propIntPAI;
	}

	public String getfYII() {
		return fYII;
	}

	public void setfYII(String fYII) {
		this.fYII = fYII;
	}

	public String getIntPMII() {
		return intPMII;
	}

	public void setIntPMII(String intPMII) {
		this.intPMII = intPMII;
	}

	public String getDateofDISII() {
		return dateofDISII;
	}

	public void setDateofDISII(String dateofDISII) {
		this.dateofDISII = dateofDISII;
	}

	public String getActAmtIPII() {
		return actAmtIPII;
	}

	public void setActAmtIPII(String actAmtIPII) {
		this.actAmtIPII = actAmtIPII;
	}

	public String getDateofPII() {
		return dateofPII;
	}

	public void setDateofPII(String dateofPII) {
		this.dateofPII = dateofPII;
	}

	public String getPropIntRoiII() {
		return propIntRoiII;
	}

	public void setPropIntRoiII(String propIntRoiII) {
		this.propIntRoiII = propIntRoiII;
	}

	public String getPropIntPAII() {
		return propIntPAII;
	}

	public void setPropIntPAII(String propIntPAII) {
		this.propIntPAII = propIntPAII;
	}

	public String getfYIII() {
		return fYIII;
	}

	public void setfYIII(String fYIII) {
		this.fYIII = fYIII;
	}

	public String getIntPMIII() {
		return intPMIII;
	}

	public void setIntPMIII(String intPMIII) {
		this.intPMIII = intPMIII;
	}

	public String getDateofDISIII() {
		return dateofDISIII;
	}

	public void setDateofDISIII(String dateofDISIII) {
		this.dateofDISIII = dateofDISIII;
	}

	public String getActAmtIPIII() {
		return actAmtIPIII;
	}

	public void setActAmtIPIII(String actAmtIPIII) {
		this.actAmtIPIII = actAmtIPIII;
	}

	public String getDateofPIII() {
		return dateofPIII;
	}

	public void setDateofPIII(String dateofPIII) {
		this.dateofPIII = dateofPIII;
	}

	public String getPropIntRoiIII() {
		return propIntRoiIII;
	}

	public void setPropIntRoiIII(String propIntRoiIII) {
		this.propIntRoiIII = propIntRoiIII;
	}

	public String getPropIntPAIII() {
		return propIntPAIII;
	}

	public void setPropIntPAIII(String propIntPAIII) {
		this.propIntPAIII = propIntPAIII;
	}

	public String getfYIV() {
		return fYIV;
	}

	public void setfYIV(String fYIV) {
		this.fYIV = fYIV;
	}

	public String getIntPMIV() {
		return intPMIV;
	}

	public void setIntPMIV(String intPMIV) {
		this.intPMIV = intPMIV;
	}

	public String getDateofDISIV() {
		return dateofDISIV;
	}

	public void setDateofDISIV(String dateofDISIV) {
		this.dateofDISIV = dateofDISIV;
	}

	public String getActAmtIPIV() {
		return actAmtIPIV;
	}

	public void setActAmtIPIV(String actAmtIPIV) {
		this.actAmtIPIV = actAmtIPIV;
	}

	public String getDateofPIV() {
		return dateofPIV;
	}

	public void setDateofPIV(String dateofPIV) {
		this.dateofPIV = dateofPIV;
	}

	public String getPropIntRoiIV() {
		return propIntRoiIV;
	}

	public void setPropIntRoiIV(String propIntRoiIV) {
		this.propIntRoiIV = propIntRoiIV;
	}

	public String getPropIntPAIV() {
		return propIntPAIV;
	}

	public void setPropIntPAIV(String propIntPAIV) {
		this.propIntPAIV = propIntPAIV;
	}

	public String getfYV() {
		return fYV;
	}

	public void setfYV(String fYV) {
		this.fYV = fYV;
	}

	public String getIntPMV() {
		return intPMV;
	}

	public void setIntPMV(String intPMV) {
		this.intPMV = intPMV;
	}

	public String getDateofDISV() {
		return dateofDISV;
	}

	public void setDateofDISV(String dateofDISV) {
		this.dateofDISV = dateofDISV;
	}

	public String getActAmtIPV() {
		return actAmtIPV;
	}

	public void setActAmtIPV(String actAmtIPV) {
		this.actAmtIPV = actAmtIPV;
	}

	public String getDateofPV() {
		return dateofPV;
	}

	public void setDateofPV(String dateofPV) {
		this.dateofPV = dateofPV;
	}

	public String getPropIntRoiV() {
		return propIntRoiV;
	}

	public void setPropIntRoiV(String propIntRoiV) {
		this.propIntRoiV = propIntRoiV;
	}

	public String getPropIntPAV() {
		return propIntPAV;
	}

	public void setPropIntPAV(String propIntPAV) {
		this.propIntPAV = propIntPAV;
	}

	public String getElgAmtcisObserv() {
		return ElgAmtcisObserv;
	}

	public void setElgAmtcisObserv(String elgAmtcisObserv) {
		ElgAmtcisObserv = elgAmtcisObserv;
	}

	public String getEvaluateId() {
		return evaluateId;
	}

	public void setEvaluateId(String evaluateId) {
		this.evaluateId = evaluateId;
	}

	public String getApcId() {
		return apcId;
	}

	public void setApcId(String apcId) {
		this.apcId = apcId;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getProduct() {
		return product;
	}

	public void setProduct(String product) {
		this.product = product;
	}

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	public String getDistrict() {
		return district;
	}

	public void setDistrict(String district) {
		this.district = district;
	}

	public Long getInvestment() {
		return investment;
	}

	public void setInvestment(Long investment) {
		this.investment = investment;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getAddPromotersDetails() {
		return addPromotersDetails;
	}

	public void setAddPromotersDetails(String addPromotersDetails) {
		this.addPromotersDetails = addPromotersDetails;
	}

	public String getAddOfRegisCompl() {
		return addOfRegisCompl;
	}

	public void setAddOfRegisCompl(String addOfRegisCompl) {
		this.addOfRegisCompl = addOfRegisCompl;
	}

	public String getAddOfRegisObserv() {
		return addOfRegisObserv;
	}

	public void setAddOfRegisObserv(String addOfRegisObserv) {
		this.addOfRegisObserv = addOfRegisObserv;
	}

	public String getAddOfFactoryCompl() {
		return addOfFactoryCompl;
	}

	public void setAddOfFactoryCompl(String addOfFactoryCompl) {
		this.addOfFactoryCompl = addOfFactoryCompl;
	}

	public String getAddOfFactoryObserv() {
		return addOfFactoryObserv;
	}

	public void setAddOfFactoryObserv(String addOfFactoryObserv) {
		this.addOfFactoryObserv = addOfFactoryObserv;
	}

	public String getConstiOfCompl() {
		return constiOfCompl;
	}

	public void setConstiOfCompl(String constiOfCompl) {
		this.constiOfCompl = constiOfCompl;
	}

	public String getConstiOfObserv() {
		return constiOfObserv;
	}

	public void setConstiOfObserv(String constiOfObserv) {
		this.constiOfObserv = constiOfObserv;
	}

	public String getDateOfStartCompl() {
		return dateOfStartCompl;
	}

	public void setDateOfStartCompl(String dateOfStartCompl) {
		this.dateOfStartCompl = dateOfStartCompl;
	}

	public String getDateOfStartObserv() {
		return dateOfStartObserv;
	}

	public void setDateOfStartObserv(String dateOfStartObserv) {
		this.dateOfStartObserv = dateOfStartObserv;
	}

	public String getNewUnitExpanCompl() {
		return newUnitExpanCompl;
	}

	public void setNewUnitExpanCompl(String newUnitExpanCompl) {
		this.newUnitExpanCompl = newUnitExpanCompl;
	}

	public String getNewUnitExpanObserv() {
		return newUnitExpanObserv;
	}

	public void setNewUnitExpanObserv(String newUnitExpanObserv) {
		this.newUnitExpanObserv = newUnitExpanObserv;
	}

	public String getProductWiseCompl() {
		return productWiseCompl;
	}

	public void setProductWiseCompl(String productWiseCompl) {
		this.productWiseCompl = productWiseCompl;
	}

	public String getProductWiseObserv() {
		return productWiseObserv;
	}

	public void setProductWiseObserv(String productWiseObserv) {
		this.productWiseObserv = productWiseObserv;
	}

	public String getGstnNoOfCompl() {
		return gstnNoOfCompl;
	}

	public void setGstnNoOfCompl(String gstnNoOfCompl) {
		this.gstnNoOfCompl = gstnNoOfCompl;
	}

	public String getGstnNoOfObserv() {
		return gstnNoOfObserv;
	}

	public void setGstnNoOfObserv(String gstnNoOfObserv) {
		this.gstnNoOfObserv = gstnNoOfObserv;
	}

	public String getPanNoOfCompl() {
		return panNoOfCompl;
	}

	public void setPanNoOfCompl(String panNoOfCompl) {
		this.panNoOfCompl = panNoOfCompl;
	}

	public String getPanNoOfObserv() {
		return panNoOfObserv;
	}

	public void setPanNoOfObserv(String panNoOfObserv) {
		this.panNoOfObserv = panNoOfObserv;
	}

	public Long getLandAmtInv() {
		return landAmtInv;
	}

	public void setLandAmtInv(Long landAmtInv) {
		this.landAmtInv = landAmtInv;
	}

	public Long getLandPerDpr() {
		return landPerDpr;
	}

	public void setLandPerDpr(Long landPerDpr) {
		this.landPerDpr = landPerDpr;
	}

	public Long getLandBankApprai() {
		return landBankApprai;
	}

	public void setLandBankApprai(Long landBankApprai) {
		this.landBankApprai = landBankApprai;
	}

	public Long getLandPerCerti() {
		return landPerCerti;
	}

	public void setLandPerCerti(Long landPerCerti) {
		this.landPerCerti = landPerCerti;
	}

	public Long getLandCapInvCA() {
		return landCapInvCA;
	}

	public void setLandCapInvCA(Long landCapInvCA) {
		this.landCapInvCA = landCapInvCA;
	}

	public Long getLandafterinv() {
		return landafterinv;
	}

	public void setLandafterinv(Long landafterinv) {
		this.landafterinv = landafterinv;
	}

	public Long getLandCapInvValuer() {
		return landCapInvValuer;
	}

	public void setLandCapInvValuer(Long landCapInvValuer) {
		this.landCapInvValuer = landCapInvValuer;
	}

	public Long getLandStatutoryAudit() {
		return landStatutoryAudit;
	}

	public void setLandStatutoryAudit(Long landStatutoryAudit) {
		this.landStatutoryAudit = landStatutoryAudit;
	}

	public Long getBuildAmtInv() {
		return buildAmtInv;
	}

	public void setBuildAmtInv(Long buildAmtInv) {
		this.buildAmtInv = buildAmtInv;
	}

	public Long getBuildPerDpr() {
		return buildPerDpr;
	}

	public void setBuildPerDpr(Long buildPerDpr) {
		this.buildPerDpr = buildPerDpr;
	}

	public Long getBuildBankApprai() {
		return buildBankApprai;
	}

	public void setBuildBankApprai(Long buildBankApprai) {
		this.buildBankApprai = buildBankApprai;
	}

	public Long getBuildPerCerti() {
		return buildPerCerti;
	}

	public void setBuildPerCerti(Long buildPerCerti) {
		this.buildPerCerti = buildPerCerti;
	}

	public Long getBuildCapInvCA() {
		return buildCapInvCA;
	}

	public void setBuildCapInvCA(Long buildCapInvCA) {
		this.buildCapInvCA = buildCapInvCA;
	}

	public Long getBuildafterinv() {
		return buildafterinv;
	}

	public void setBuildafterinv(Long buildafterinv) {
		this.buildafterinv = buildafterinv;
	}

	public Long getBuildCapInvValuer() {
		return buildCapInvValuer;
	}

	public void setBuildCapInvValuer(Long buildCapInvValuer) {
		this.buildCapInvValuer = buildCapInvValuer;
	}

	public Long getBuildStatutoryAudit() {
		return buildStatutoryAudit;
	}

	public void setBuildStatutoryAudit(Long buildStatutoryAudit) {
		this.buildStatutoryAudit = buildStatutoryAudit;
	}

	public Long getPlantAmtInv() {
		return plantAmtInv;
	}

	public void setPlantAmtInv(Long plantAmtInv) {
		this.plantAmtInv = plantAmtInv;
	}

	public Long getPlantPerDpr() {
		return plantPerDpr;
	}

	public void setPlantPerDpr(Long plantPerDpr) {
		this.plantPerDpr = plantPerDpr;
	}

	public Long getPlantBankApprai() {
		return plantBankApprai;
	}

	public void setPlantBankApprai(Long plantBankApprai) {
		this.plantBankApprai = plantBankApprai;
	}

	public Long getPlantPerCertificate() {
		return plantPerCertificate;
	}

	public void setPlantPerCertificate(Long plantPerCertificate) {
		this.plantPerCertificate = plantPerCertificate;
	}

	public Long getPlantCapInvCA() {
		return plantCapInvCA;
	}

	public void setPlantCapInvCA(Long plantCapInvCA) {
		this.plantCapInvCA = plantCapInvCA;
	}

	public Long getPlantCapInvValuer() {
		return plantCapInvValuer;
	}

	public void setPlantCapInvValuer(Long plantCapInvValuer) {
		this.plantCapInvValuer = plantCapInvValuer;
	}

	public Long getPlantStatutoryAudit() {
		return plantStatutoryAudit;
	}

	public void setPlantStatutoryAudit(Long plantStatutoryAudit) {
		this.plantStatutoryAudit = plantStatutoryAudit;
	}

	public Long getPlantdafterinv() {
		return plantdafterinv;
	}

	public void setPlantdafterinv(Long plantdafterinv) {
		this.plantdafterinv = plantdafterinv;
	}

	public Long getMiscAmtInve() {
		return miscAmtInve;
	}

	public void setMiscAmtInve(Long miscAmtInve) {
		this.miscAmtInve = miscAmtInve;
	}

	public Long getMiscPerDpr() {
		return miscPerDpr;
	}

	public void setMiscPerDpr(Long miscPerDpr) {
		this.miscPerDpr = miscPerDpr;
	}

	public Long getMiscBankApprai() {
		return miscBankApprai;
	}

	public void setMiscBankApprai(Long miscBankApprai) {
		this.miscBankApprai = miscBankApprai;
	}

	public Long getMiscPerCertificate() {
		return miscPerCertificate;
	}

	public void setMiscPerCertificate(Long miscPerCertificate) {
		this.miscPerCertificate = miscPerCertificate;
	}

	public Long getMiscCapInvCA() {
		return miscCapInvCA;
	}

	public void setMiscCapInvCA(Long miscCapInvCA) {
		this.miscCapInvCA = miscCapInvCA;
	}

	public Long getMiscCapInvValuer() {
		return miscCapInvValuer;
	}

	public void setMiscCapInvValuer(Long miscCapInvValuer) {
		this.miscCapInvValuer = miscCapInvValuer;
	}

	public Long getMiscStatutoryAuditor() {
		return miscStatutoryAuditor;
	}

	public void setMiscStatutoryAuditor(Long miscStatutoryAuditor) {
		this.miscStatutoryAuditor = miscStatutoryAuditor;
	}

	public Long getMiscafterinv() {
		return miscafterinv;
	}

	public void setMiscafterinv(Long miscafterinv) {
		this.miscafterinv = miscafterinv;
	}

	public Long getSubTtlAAmtInv() {
		return subTtlAAmtInv;
	}

	public void setSubTtlAAmtInv(Long subTtlAAmtInv) {
		this.subTtlAAmtInv = subTtlAAmtInv;
	}

	public Long getSubTtlAPerDpr() {
		return subTtlAPerDpr;
	}

	public void setSubTtlAPerDpr(Long subTtlAPerDpr) {
		this.subTtlAPerDpr = subTtlAPerDpr;
	}

	public Long getSubTtlABankApprai() {
		return subTtlABankApprai;
	}

	public void setSubTtlABankApprai(Long subTtlABankApprai) {
		this.subTtlABankApprai = subTtlABankApprai;
	}

	public Long getSubTtlAPerCerti() {
		return subTtlAPerCerti;
	}

	public void setSubTtlAPerCerti(Long subTtlAPerCerti) {
		this.subTtlAPerCerti = subTtlAPerCerti;
	}

	public Long getSubTtlACapInvCA() {
		return subTtlACapInvCA;
	}

	public void setSubTtlACapInvCA(Long subTtlACapInvCA) {
		this.subTtlACapInvCA = subTtlACapInvCA;
	}

	public Long getSubTtlACapInvValuer() {
		return subTtlACapInvValuer;
	}

	public void setSubTtlACapInvValuer(Long subTtlACapInvValuer) {
		this.subTtlACapInvValuer = subTtlACapInvValuer;
	}

	public Long getSubTtlAStatutoryAudit() {
		return subTtlAStatutoryAudit;
	}

	public void setSubTtlAStatutoryAudit(Long subTtlAStatutoryAudit) {
		this.subTtlAStatutoryAudit = subTtlAStatutoryAudit;
	}

	public Long getSubTtlAafterinv() {
		return subTtlAafterinv;
	}

	public void setSubTtlAafterinv(Long subTtlAafterinv) {
		this.subTtlAafterinv = subTtlAafterinv;
	}

	public Long getProvisionAmtInve() {
		return provisionAmtInve;
	}

	public void setProvisionAmtInve(Long provisionAmtInve) {
		this.provisionAmtInve = provisionAmtInve;
	}

	public Long getProvisionPerDpr() {
		return provisionPerDpr;
	}

	public void setProvisionPerDpr(Long provisionPerDpr) {
		this.provisionPerDpr = provisionPerDpr;
	}

	public Long getProvisionBankApprai() {
		return provisionBankApprai;
	}

	public void setProvisionBankApprai(Long provisionBankApprai) {
		this.provisionBankApprai = provisionBankApprai;
	}

	public Long getProvisionPerCerti() {
		return provisionPerCerti;
	}

	public void setProvisionPerCerti(Long provisionPerCerti) {
		this.provisionPerCerti = provisionPerCerti;
	}

	public Long getProvisionCapInvCA() {
		return provisionCapInvCA;
	}

	public void setProvisionCapInvCA(Long provisionCapInvCA) {
		this.provisionCapInvCA = provisionCapInvCA;
	}

	public Long getProvisionCapInvValuer() {
		return provisionCapInvValuer;
	}

	public void setProvisionCapInvValuer(Long provisionCapInvValuer) {
		this.provisionCapInvValuer = provisionCapInvValuer;
	}

	public Long getProvisionStatutoryAudit() {
		return provisionStatutoryAudit;
	}

	public void setProvisionStatutoryAudit(Long provisionStatutoryAudit) {
		this.provisionStatutoryAudit = provisionStatutoryAudit;
	}

	public Long getProvisionafterinv() {
		return provisionafterinv;
	}

	public void setProvisionafterinv(Long provisionafterinv) {
		this.provisionafterinv = provisionafterinv;
	}

	public Long getPrelimAmtInve() {
		return prelimAmtInve;
	}

	public void setPrelimAmtInve(Long prelimAmtInve) {
		this.prelimAmtInve = prelimAmtInve;
	}

	public Long getPrelimPerDpr() {
		return prelimPerDpr;
	}

	public void setPrelimPerDpr(Long prelimPerDpr) {
		this.prelimPerDpr = prelimPerDpr;
	}

	public Long getPrelimBankApprai() {
		return prelimBankApprai;
	}

	public void setPrelimBankApprai(Long prelimBankApprai) {
		this.prelimBankApprai = prelimBankApprai;
	}

	public Long getPrelimPerCerti() {
		return prelimPerCerti;
	}

	public void setPrelimPerCerti(Long prelimPerCerti) {
		this.prelimPerCerti = prelimPerCerti;
	}

	public Long getPrelimCapInvCA() {
		return prelimCapInvCA;
	}

	public void setPrelimCapInvCA(Long prelimCapInvCA) {
		this.prelimCapInvCA = prelimCapInvCA;
	}

	public Long getPrelimCapInvValuer() {
		return prelimCapInvValuer;
	}

	public void setPrelimCapInvValuer(Long prelimCapInvValuer) {
		this.prelimCapInvValuer = prelimCapInvValuer;
	}

	public Long getPrelimStatutoryAudit() {
		return prelimStatutoryAudit;
	}

	public void setPrelimStatutoryAudit(Long prelimStatutoryAudit) {
		this.prelimStatutoryAudit = prelimStatutoryAudit;
	}

	public Long getPrelimafterinv() {
		return prelimafterinv;
	}

	public void setPrelimafterinv(Long prelimafterinv) {
		this.prelimafterinv = prelimafterinv;
	}

	public Long getInterestAmtInve() {
		return interestAmtInve;
	}

	public void setInterestAmtInve(Long interestAmtInve) {
		this.interestAmtInve = interestAmtInve;
	}

	public Long getInterestPerDpr() {
		return interestPerDpr;
	}

	public void setInterestPerDpr(Long interestPerDpr) {
		this.interestPerDpr = interestPerDpr;
	}

	public Long getInterestBankApprai() {
		return interestBankApprai;
	}

	public void setInterestBankApprai(Long interestBankApprai) {
		this.interestBankApprai = interestBankApprai;
	}

	public Long getInterestPerCerti() {
		return interestPerCerti;
	}

	public void setInterestPerCerti(Long interestPerCerti) {
		this.interestPerCerti = interestPerCerti;
	}

	public Long getInterestCapInvCA() {
		return interestCapInvCA;
	}

	public void setInterestCapInvCA(Long interestCapInvCA) {
		this.interestCapInvCA = interestCapInvCA;
	}

	public Long getInterestCapInvValuer() {
		return interestCapInvValuer;
	}

	public void setInterestCapInvValuer(Long interestCapInvValuer) {
		this.interestCapInvValuer = interestCapInvValuer;
	}

	public Long getInterestStatutoryAudit() {
		return interestStatutoryAudit;
	}

	public void setInterestStatutoryAudit(Long interestStatutoryAudit) {
		this.interestStatutoryAudit = interestStatutoryAudit;
	}

	public Long getInterestafterinv() {
		return interestafterinv;
	}

	public void setInterestafterinv(Long interestafterinv) {
		this.interestafterinv = interestafterinv;
	}

	public Long getMarginAmtInve() {
		return marginAmtInve;
	}

	public void setMarginAmtInve(Long marginAmtInve) {
		this.marginAmtInve = marginAmtInve;
	}

	public Long getMarginPerDpr() {
		return marginPerDpr;
	}

	public void setMarginPerDpr(Long marginPerDpr) {
		this.marginPerDpr = marginPerDpr;
	}

	public Long getMarginBankApprai() {
		return marginBankApprai;
	}

	public void setMarginBankApprai(Long marginBankApprai) {
		this.marginBankApprai = marginBankApprai;
	}

	public Long getMarginPerCerti() {
		return marginPerCerti;
	}

	public void setMarginPerCerti(Long marginPerCerti) {
		this.marginPerCerti = marginPerCerti;
	}

	public Long getMarginCapInvCA() {
		return marginCapInvCA;
	}

	public void setMarginCapInvCA(Long marginCapInvCA) {
		this.marginCapInvCA = marginCapInvCA;
	}

	public Long getMarginCapInvValuer() {
		return marginCapInvValuer;
	}

	public void setMarginCapInvValuer(Long marginCapInvValuer) {
		this.marginCapInvValuer = marginCapInvValuer;
	}

	public Long getMarginStatutoryAudit() {
		return marginStatutoryAudit;
	}

	public void setMarginStatutoryAudit(Long marginStatutoryAudit) {
		this.marginStatutoryAudit = marginStatutoryAudit;
	}

	public Long getMarginafterinv() {
		return marginafterinv;
	}

	public void setMarginafterinv(Long marginafterinv) {
		this.marginafterinv = marginafterinv;
	}

	public Long getOtherAmtInve() {
		return otherAmtInve;
	}

	public void setOtherAmtInve(Long otherAmtInve) {
		this.otherAmtInve = otherAmtInve;
	}

	public Long getOtherPerDpr() {
		return otherPerDpr;
	}

	public void setOtherPerDpr(Long otherPerDpr) {
		this.otherPerDpr = otherPerDpr;
	}

	public Long getOtherBankApprai() {
		return otherBankApprai;
	}

	public void setOtherBankApprai(Long otherBankApprai) {
		this.otherBankApprai = otherBankApprai;
	}

	public Long getOtherPerCerti() {
		return otherPerCerti;
	}

	public void setOtherPerCerti(Long otherPerCerti) {
		this.otherPerCerti = otherPerCerti;
	}

	public Long getOtherCapInvCA() {
		return otherCapInvCA;
	}

	public void setOtherCapInvCA(Long otherCapInvCA) {
		this.otherCapInvCA = otherCapInvCA;
	}

	public Long getOtherCapInvValuer() {
		return otherCapInvValuer;
	}

	public void setOtherCapInvValuer(Long otherCapInvValuer) {
		this.otherCapInvValuer = otherCapInvValuer;
	}

	public Long getOtherStatutoryAudit() {
		return otherStatutoryAudit;
	}

	public void setOtherStatutoryAudit(Long otherStatutoryAudit) {
		this.otherStatutoryAudit = otherStatutoryAudit;
	}

	public Long getOtherafterinv() {
		return otherafterinv;
	}

	public void setOtherafterinv(Long otherafterinv) {
		this.otherafterinv = otherafterinv;
	}

	public Long getSubTtlBAmtInv() {
		return subTtlBAmtInv;
	}

	public void setSubTtlBAmtInv(Long subTtlBAmtInv) {
		this.subTtlBAmtInv = subTtlBAmtInv;
	}

	public Long getSubTtlBPerDpr() {
		return subTtlBPerDpr;
	}

	public void setSubTtlBPerDpr(Long subTtlBPerDpr) {
		this.subTtlBPerDpr = subTtlBPerDpr;
	}

	public Long getSubTtlBBankApprai() {
		return subTtlBBankApprai;
	}

	public void setSubTtlBBankApprai(Long subTtlBBankApprai) {
		this.subTtlBBankApprai = subTtlBBankApprai;
	}

	public Long getSubTtlBPerCerti() {
		return subTtlBPerCerti;
	}

	public void setSubTtlBPerCerti(Long subTtlBPerCerti) {
		this.subTtlBPerCerti = subTtlBPerCerti;
	}

	public Long getSubTtlBCapInvCA() {
		return subTtlBCapInvCA;
	}

	public void setSubTtlBCapInvCA(Long subTtlBCapInvCA) {
		this.subTtlBCapInvCA = subTtlBCapInvCA;
	}

	public Long getSubTtlBCapInvValuer() {
		return subTtlBCapInvValuer;
	}

	public void setSubTtlBCapInvValuer(Long subTtlBCapInvValuer) {
		this.subTtlBCapInvValuer = subTtlBCapInvValuer;
	}

	public Long getSubTtlBStatutoryAudit() {
		return subTtlBStatutoryAudit;
	}

	public void setSubTtlBStatutoryAudit(Long subTtlBStatutoryAudit) {
		this.subTtlBStatutoryAudit = subTtlBStatutoryAudit;
	}

	public Long getSubTtlBCafterinv() {
		return subTtlBCafterinv;
	}

	public void setSubTtlBCafterinv(Long subTtlBCafterinv) {
		this.subTtlBCafterinv = subTtlBCafterinv;
	}

	public Long getTtlAmtInve() {
		return ttlAmtInve;
	}

	public void setTtlAmtInve(Long ttlAmtInve) {
		this.ttlAmtInve = ttlAmtInve;
	}

	public Long getTtlPerDpr() {
		return ttlPerDpr;
	}

	public void setTtlPerDpr(Long ttlPerDpr) {
		this.ttlPerDpr = ttlPerDpr;
	}

	public Long getTtlBankApprai() {
		return TtlBankApprai;
	}

	public void setTtlBankApprai(Long ttlBankApprai) {
		TtlBankApprai = ttlBankApprai;
	}

	public Long getTtlPerCerti() {
		return ttlPerCerti;
	}

	public void setTtlPerCerti(Long ttlPerCerti) {
		this.ttlPerCerti = ttlPerCerti;
	}

	public Long getTtlCapInvCA() {
		return ttlCapInvCA;
	}

	public void setTtlCapInvCA(Long ttlCapInvCA) {
		this.ttlCapInvCA = ttlCapInvCA;
	}

	public Long getTtlCapInvValuer() {
		return ttlCapInvValuer;
	}

	public void setTtlCapInvValuer(Long ttlCapInvValuer) {
		this.ttlCapInvValuer = ttlCapInvValuer;
	}

	public Long getTtlStatutoryAudit() {
		return ttlStatutoryAudit;
	}

	public void setTtlStatutoryAudit(Long ttlStatutoryAudit) {
		this.ttlStatutoryAudit = ttlStatutoryAudit;
	}

	public String getBreakUpCostObserve() {
		return breakUpCostObserve;
	}

	public void setBreakUpCostObserve(String breakUpCostObserve) {
		this.breakUpCostObserve = breakUpCostObserve;
	}

	public String getTtlafterinv() {
		return ttlafterinv;
	}

	public void setTtlafterinv(String ttlafterinv) {
		this.ttlafterinv = ttlafterinv;
	}

	public Long getEquityCapPerDpr() {
		return equityCapPerDpr;
	}

	public void setEquityCapPerDpr(Long equityCapPerDpr) {
		this.equityCapPerDpr = equityCapPerDpr;
	}

	public Long getEquityCapBankApprai() {
		return equityCapBankApprai;
	}

	public void setEquityCapBankApprai(Long equityCapBankApprai) {
		this.equityCapBankApprai = equityCapBankApprai;
	}

	public Long getEquityCapPerCerti() {
		return equityCapPerCerti;
	}

	public void setEquityCapPerCerti(Long equityCapPerCerti) {
		this.equityCapPerCerti = equityCapPerCerti;
	}

	public Long getEquityCapCapInvCA() {
		return equityCapCapInvCA;
	}

	public void setEquityCapCapInvCA(Long equityCapCapInvCA) {
		this.equityCapCapInvCA = equityCapCapInvCA;
	}

	public Long getEquAftinvdate() {
		return equAftinvdate;
	}

	public void setEquAftinvdate(Long equAftinvdate) {
		this.equAftinvdate = equAftinvdate;
	}

	public Long getEquAftinvproddate() {
		return equAftinvproddate;
	}

	public void setEquAftinvproddate(Long equAftinvproddate) {
		this.equAftinvproddate = equAftinvproddate;
	}

	public Long getEquityCapStatutoryAudit() {
		return equityCapStatutoryAudit;
	}

	public void setEquityCapStatutoryAudit(Long equityCapStatutoryAudit) {
		this.equityCapStatutoryAudit = equityCapStatutoryAudit;
	}

	public Long getIntCashPerDpr() {
		return intCashPerDpr;
	}

	public void setIntCashPerDpr(Long intCashPerDpr) {
		this.intCashPerDpr = intCashPerDpr;
	}

	public Long getIntCashBankApprai() {
		return intCashBankApprai;
	}

	public void setIntCashBankApprai(Long intCashBankApprai) {
		this.intCashBankApprai = intCashBankApprai;
	}

	public Long getIntCashPerCerti() {
		return intCashPerCerti;
	}

	public void setIntCashPerCerti(Long intCashPerCerti) {
		this.intCashPerCerti = intCashPerCerti;
	}

	public Long getIntCashCapInvCA() {
		return intCashCapInvCA;
	}

	public void setIntCashCapInvCA(Long intCashCapInvCA) {
		this.intCashCapInvCA = intCashCapInvCA;
	}

	public Long getIntCashStatutoryAudit() {
		return intCashStatutoryAudit;
	}

	public void setIntCashStatutoryAudit(Long intCashStatutoryAudit) {
		this.intCashStatutoryAudit = intCashStatutoryAudit;
	}

	public Long getIntCashAftinvdate() {
		return intCashAftinvdate;
	}

	public void setIntCashAftinvdate(Long intCashAftinvdate) {
		this.intCashAftinvdate = intCashAftinvdate;
	}

	public Long getIntCashAftinvproddate() {
		return intCashAftinvproddate;
	}

	public void setIntCashAftinvproddate(Long intCashAftinvproddate) {
		this.intCashAftinvproddate = intCashAftinvproddate;
	}

	public Long getIntFreePerDpr() {
		return intFreePerDpr;
	}

	public void setIntFreePerDpr(Long intFreePerDpr) {
		this.intFreePerDpr = intFreePerDpr;
	}

	public Long getIntFreeBankApprai() {
		return intFreeBankApprai;
	}

	public void setIntFreeBankApprai(Long intFreeBankApprai) {
		this.intFreeBankApprai = intFreeBankApprai;
	}

	public Long getIntFreePerCerti() {
		return intFreePerCerti;
	}

	public void setIntFreePerCerti(Long intFreePerCerti) {
		this.intFreePerCerti = intFreePerCerti;
	}

	public Long getIntFreeCapInvCA() {
		return intFreeCapInvCA;
	}

	public void setIntFreeCapInvCA(Long intFreeCapInvCA) {
		this.intFreeCapInvCA = intFreeCapInvCA;
	}

	public Long getIntFreeStatutoryAudit() {
		return intFreeStatutoryAudit;
	}

	public void setIntFreeStatutoryAudit(Long intFreeStatutoryAudit) {
		this.intFreeStatutoryAudit = intFreeStatutoryAudit;
	}

	public Long getIntFreeAftinvdate() {
		return intFreeAftinvdate;
	}

	public void setIntFreeAftinvdate(Long intFreeAftinvdate) {
		this.intFreeAftinvdate = intFreeAftinvdate;
	}

	public Long getIntFreeAftinvproddate() {
		return intFreeAftinvproddate;
	}

	public void setIntFreeAftinvproddate(Long intFreeAftinvproddate) {
		this.intFreeAftinvproddate = intFreeAftinvproddate;
	}

	public Long getFinOthPerDpr() {
		return finOthPerDpr;
	}

	public void setFinOthPerDpr(Long finOthPerDpr) {
		this.finOthPerDpr = finOthPerDpr;
	}

	public Long getFinOthBankApprai() {
		return finOthBankApprai;
	}

	public void setFinOthBankApprai(Long finOthBankApprai) {
		this.finOthBankApprai = finOthBankApprai;
	}

	public Long getFinOthPerCerti() {
		return finOthPerCerti;
	}

	public void setFinOthPerCerti(Long finOthPerCerti) {
		this.finOthPerCerti = finOthPerCerti;
	}

	public Long getFinOthCapInvCA() {
		return finOthCapInvCA;
	}

	public void setFinOthCapInvCA(Long finOthCapInvCA) {
		this.finOthCapInvCA = finOthCapInvCA;
	}

	public Long getFinOthStatutoryAudit() {
		return finOthStatutoryAudit;
	}

	public void setFinOthStatutoryAudit(Long finOthStatutoryAudit) {
		this.finOthStatutoryAudit = finOthStatutoryAudit;
	}

	public Long getFinOtheAftinvdate() {
		return finOtheAftinvdate;
	}

	public void setFinOtheAftinvdate(Long finOtheAftinvdate) {
		this.finOtheAftinvdate = finOtheAftinvdate;
	}

	public Long getFinOthAftinvproddate() {
		return finOthAftinvproddate;
	}

	public void setFinOthAftinvproddate(Long finOthAftinvproddate) {
		this.finOthAftinvproddate = finOthAftinvproddate;
	}

	public Long getSecPerDpr() {
		return SecPerDpr;
	}

	public void setSecPerDpr(Long secPerDpr) {
		SecPerDpr = secPerDpr;
	}

	public Long getSecBankApprai() {
		return SecBankApprai;
	}

	public void setSecBankApprai(Long secBankApprai) {
		SecBankApprai = secBankApprai;
	}

	public Long getSecPerCerti() {
		return SecPerCerti;
	}

	public void setSecPerCerti(Long secPerCerti) {
		SecPerCerti = secPerCerti;
	}

	public Long getSecCapInvCA() {
		return SecCapInvCA;
	}

	public void setSecCapInvCA(Long secCapInvCA) {
		SecCapInvCA = secCapInvCA;
	}

	public Long getSecStatutoryAudit() {
		return SecStatutoryAudit;
	}

	public void setSecStatutoryAudit(Long secStatutoryAudit) {
		SecStatutoryAudit = secStatutoryAudit;
	}

	public Long getSeceAftinvdate() {
		return SeceAftinvdate;
	}

	public void setSeceAftinvdate(Long seceAftinvdate) {
		SeceAftinvdate = seceAftinvdate;
	}

	public Long getSecAftinvproddate() {
		return SecAftinvproddate;
	}

	public void setSecAftinvproddate(Long secAftinvproddate) {
		SecAftinvproddate = secAftinvproddate;
	}

	public Long getDealPerDpr() {
		return dealPerDpr;
	}

	public void setDealPerDpr(Long dealPerDpr) {
		this.dealPerDpr = dealPerDpr;
	}

	public Long getDealBankApprai() {
		return dealBankApprai;
	}

	public void setDealBankApprai(Long dealBankApprai) {
		this.dealBankApprai = dealBankApprai;
	}

	public Long getDealPerCerti() {
		return dealPerCerti;
	}

	public void setDealPerCerti(Long dealPerCerti) {
		this.dealPerCerti = dealPerCerti;
	}

	public Long getDealCapInvCA() {
		return dealCapInvCA;
	}

	public void setDealCapInvCA(Long dealCapInvCA) {
		this.dealCapInvCA = dealCapInvCA;
	}

	public Long getDealStatutoryAudit() {
		return dealStatutoryAudit;
	}

	public void setDealStatutoryAudit(Long dealStatutoryAudit) {
		this.dealStatutoryAudit = dealStatutoryAudit;
	}

	public Long getDealeAftinvdate() {
		return dealeAftinvdate;
	}

	public void setDealeAftinvdate(Long dealeAftinvdate) {
		this.dealeAftinvdate = dealeAftinvdate;
	}

	public Long getDealAftinvproddate() {
		return dealAftinvproddate;
	}

	public void setDealAftinvproddate(Long dealAftinvproddate) {
		this.dealAftinvproddate = dealAftinvproddate;
	}

	public Long getFromFiPerDpr() {
		return FromFiPerDpr;
	}

	public void setFromFiPerDpr(Long fromFiPerDpr) {
		FromFiPerDpr = fromFiPerDpr;
	}

	public Long getFromFiBankApprai() {
		return FromFiBankApprai;
	}

	public void setFromFiBankApprai(Long fromFiBankApprai) {
		FromFiBankApprai = fromFiBankApprai;
	}

	public Long getFromFiPerCerti() {
		return FromFiPerCerti;
	}

	public void setFromFiPerCerti(Long fromFiPerCerti) {
		FromFiPerCerti = fromFiPerCerti;
	}

	public Long getFromFiCapInvCA() {
		return FromFiCapInvCA;
	}

	public void setFromFiCapInvCA(Long fromFiCapInvCA) {
		FromFiCapInvCA = fromFiCapInvCA;
	}

	public Long getFromFiStatutoryAudit() {
		return FromFiStatutoryAudit;
	}

	public void setFromFiStatutoryAudit(Long fromFiStatutoryAudit) {
		FromFiStatutoryAudit = fromFiStatutoryAudit;
	}

	public Long getFromFieAftinvdate() {
		return FromFieAftinvdate;
	}

	public void setFromFieAftinvdate(Long fromFieAftinvdate) {
		FromFieAftinvdate = fromFieAftinvdate;
	}

	public Long getFromFiAftinvproddate() {
		return FromFiAftinvproddate;
	}

	public void setFromFiAftinvproddate(Long fromFiAftinvproddate) {
		FromFiAftinvproddate = fromFiAftinvproddate;
	}

	public Long getFrBankPerDpr() {
		return FrBankPerDpr;
	}

	public void setFrBankPerDpr(Long frBankPerDpr) {
		FrBankPerDpr = frBankPerDpr;
	}

	public Long getFrBankBankApprai() {
		return FrBankBankApprai;
	}

	public void setFrBankBankApprai(Long frBankBankApprai) {
		FrBankBankApprai = frBankBankApprai;
	}

	public Long getFrBankPerCerti() {
		return FrBankPerCerti;
	}

	public void setFrBankPerCerti(Long frBankPerCerti) {
		FrBankPerCerti = frBankPerCerti;
	}

	public Long getFrBankCapInvCA() {
		return FrBankCapInvCA;
	}

	public void setFrBankCapInvCA(Long frBankCapInvCA) {
		FrBankCapInvCA = frBankCapInvCA;
	}

	public Long getFrBankStatutoryAudit() {
		return FrBankStatutoryAudit;
	}

	public void setFrBankStatutoryAudit(Long frBankStatutoryAudit) {
		FrBankStatutoryAudit = frBankStatutoryAudit;
	}

	public Long getFrBankeAftinvdate() {
		return frBankeAftinvdate;
	}

	public void setFrBankeAftinvdate(Long frBankeAftinvdate) {
		this.frBankeAftinvdate = frBankeAftinvdate;
	}

	public Long getFrBankAftinvproddate() {
		return frBankAftinvproddate;
	}

	public void setFrBankAftinvproddate(Long frBankAftinvproddate) {
		this.frBankAftinvproddate = frBankAftinvproddate;
	}

	public Long getPlantMachPerDpr() {
		return PlantMachPerDpr;
	}

	public void setPlantMachPerDpr(Long plantMachPerDpr) {
		PlantMachPerDpr = plantMachPerDpr;
	}

	public Long getPlantMachBankApprai() {
		return PlantMachBankApprai;
	}

	public void setPlantMachBankApprai(Long plantMachBankApprai) {
		PlantMachBankApprai = plantMachBankApprai;
	}

	public Long getPlant_MachPerCerti() {
		return Plant_MachPerCerti;
	}

	public void setPlant_MachPerCerti(Long plant_MachPerCerti) {
		Plant_MachPerCerti = plant_MachPerCerti;
	}

	public Long getPlantMachCapInvCA() {
		return PlantMachCapInvCA;
	}

	public void setPlantMachCapInvCA(Long plantMachCapInvCA) {
		PlantMachCapInvCA = plantMachCapInvCA;
	}

	public Long getPlantMachStatutoryAudit() {
		return PlantMachStatutoryAudit;
	}

	public void setPlantMachStatutoryAudit(Long plantMachStatutoryAudit) {
		PlantMachStatutoryAudit = plantMachStatutoryAudit;
	}

	public Long getPlant_MacheAftinvdate() {
		return Plant_MacheAftinvdate;
	}

	public void setPlant_MacheAftinvdate(Long plant_MacheAftinvdate) {
		Plant_MacheAftinvdate = plant_MacheAftinvdate;
	}

	public Long getPlant_MachAftinvproddate() {
		return Plant_MachAftinvproddate;
	}

	public void setPlant_MachAftinvproddate(Long plant_MachAftinvproddate) {
		Plant_MachAftinvproddate = plant_MachAftinvproddate;
	}

	public Long getFinTttlPerDpr() {
		return finTttlPerDpr;
	}

	public void setFinTttlPerDpr(Long finTttlPerDpr) {
		this.finTttlPerDpr = finTttlPerDpr;
	}

	public Long getFinTttlBankApprai() {
		return finTttlBankApprai;
	}

	public void setFinTttlBankApprai(Long finTttlBankApprai) {
		this.finTttlBankApprai = finTttlBankApprai;
	}

	public Long getFinTttlPerCerti() {
		return finTttlPerCerti;
	}

	public void setFinTttlPerCerti(Long finTttlPerCerti) {
		this.finTttlPerCerti = finTttlPerCerti;
	}

	public Long getFinTttlCapInvCA() {
		return finTttlCapInvCA;
	}

	public void setFinTttlCapInvCA(Long finTttlCapInvCA) {
		this.finTttlCapInvCA = finTttlCapInvCA;
	}

	public Long getFinTttlStatutoryAudit() {
		return finTttlStatutoryAudit;
	}

	public void setFinTttlStatutoryAudit(Long finTttlStatutoryAudit) {
		this.finTttlStatutoryAudit = finTttlStatutoryAudit;
	}

	public String getFinancingObserve() {
		return financingObserve;
	}

	public void setFinancingObserve(String financingObserve) {
		this.financingObserve = financingObserve;
	}

	public Long getFinTttldate() {
		return finTttldate;
	}

	public void setFinTttldate(Long finTttldate) {
		this.finTttldate = finTttldate;
	}

	public Long getFinTttlproddate() {
		return finTttlproddate;
	}

	public void setFinTttlproddate(Long finTttlproddate) {
		this.finTttlproddate = finTttlproddate;
	}

	public Long getISF_Claim_Reim() {
		return ISF_Claim_Reim;
	}

	public void setISF_Claim_Reim(Long iSF_Claim_Reim) {
		ISF_Claim_Reim = iSF_Claim_Reim;
	}

	public String getSgstRemark() {
		return sgstRemark;
	}

	public void setSgstRemark(String sgstRemark) {
		this.sgstRemark = sgstRemark;
	}

	public Long getISF_Reim_SCST() {
		return ISF_Reim_SCST;
	}

	public void setISF_Reim_SCST(Long iSF_Reim_SCST) {
		ISF_Reim_SCST = iSF_Reim_SCST;
	}

	public String getScstRemark() {
		return scstRemark;
	}

	public void setScstRemark(String scstRemark) {
		this.scstRemark = scstRemark;
	}

	public Long getISF_Reim_FW() {
		return ISF_Reim_FW;
	}

	public void setISF_Reim_FW(Long iSF_Reim_FW) {
		ISF_Reim_FW = iSF_Reim_FW;
	}

	public String getFwRemark() {
		return fwRemark;
	}

	public void setFwRemark(String fwRemark) {
		this.fwRemark = fwRemark;
	}

	public Long getISF_Reim_BPLW() {
		return ISF_Reim_BPLW;
	}

	public void setISF_Reim_BPLW(Long iSF_Reim_BPLW) {
		ISF_Reim_BPLW = iSF_Reim_BPLW;
	}

	public String getBplRemark() {
		return bplRemark;
	}

	public void setBplRemark(String bplRemark) {
		this.bplRemark = bplRemark;
	}

	public Long getTtlIncAmt() {
		return ttlIncAmt;
	}

	public void setTtlIncAmt(Long ttlIncAmt) {
		this.ttlIncAmt = ttlIncAmt;
	}

	public Long getISF_Stamp_Duty_EX() {
		return ISF_Stamp_Duty_EX;
	}

	public void setISF_Stamp_Duty_EX(Long iSF_Stamp_Duty_EX) {
		ISF_Stamp_Duty_EX = iSF_Stamp_Duty_EX;
	}

	public String getStampDutyExemptRemark() {
		return stampDutyExemptRemark;
	}

	public void setStampDutyExemptRemark(String stampDutyExemptRemark) {
		this.stampDutyExemptRemark = stampDutyExemptRemark;
	}

	public Long getISF_Amt_Stamp_Duty_Reim() {
		return ISF_Amt_Stamp_Duty_Reim;
	}

	public void setISF_Amt_Stamp_Duty_Reim(Long iSF_Amt_Stamp_Duty_Reim) {
		ISF_Amt_Stamp_Duty_Reim = iSF_Amt_Stamp_Duty_Reim;
	}

	public String getStampDutyReimRemark() {
		return stampDutyReimRemark;
	}

	public void setStampDutyReimRemark(String stampDutyReimRemark) {
		this.stampDutyReimRemark = stampDutyReimRemark;
	}

	public Long getISF_Additonal_Stamp_Duty_EX() {
		return ISF_Additonal_Stamp_Duty_EX;
	}

	public void setISF_Additonal_Stamp_Duty_EX(Long iSF_Additonal_Stamp_Duty_EX) {
		ISF_Additonal_Stamp_Duty_EX = iSF_Additonal_Stamp_Duty_EX;
	}

	public String getDivyangSCSTRemark() {
		return divyangSCSTRemark;
	}

	public void setDivyangSCSTRemark(String divyangSCSTRemark) {
		this.divyangSCSTRemark = divyangSCSTRemark;
	}

	public Long getISF_Epf_Reim_UW() {
		return ISF_Epf_Reim_UW;
	}

	public void setISF_Epf_Reim_UW(Long iSF_Epf_Reim_UW) {
		ISF_Epf_Reim_UW = iSF_Epf_Reim_UW;
	}

	public String getEpfUnsklRemark() {
		return epfUnsklRemark;
	}

	public void setEpfUnsklRemark(String epfUnsklRemark) {
		this.epfUnsklRemark = epfUnsklRemark;
	}

	public Long getISF_Add_Epf_Reim_SkUkW() {
		return ISF_Add_Epf_Reim_SkUkW;
	}

	public void setISF_Add_Epf_Reim_SkUkW(Long iSF_Add_Epf_Reim_SkUkW) {
		ISF_Add_Epf_Reim_SkUkW = iSF_Add_Epf_Reim_SkUkW;
	}

	public String getEpfSklUnsklRemark() {
		return epfSklUnsklRemark;
	}

	public void setEpfSklUnsklRemark(String epfSklUnsklRemark) {
		this.epfSklUnsklRemark = epfSklUnsklRemark;
	}

	public Long getISF_Add_Epf_Reim_DIVSCSTF() {
		return ISF_Add_Epf_Reim_DIVSCSTF;
	}

	public void setISF_Add_Epf_Reim_DIVSCSTF(Long iSF_Add_Epf_Reim_DIVSCSTF) {
		ISF_Add_Epf_Reim_DIVSCSTF = iSF_Add_Epf_Reim_DIVSCSTF;
	}

	public String getEpfDvngSCSTRemark() {
		return epfDvngSCSTRemark;
	}

	public void setEpfDvngSCSTRemark(String epfDvngSCSTRemark) {
		this.epfDvngSCSTRemark = epfDvngSCSTRemark;
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

	public Long getISF_ACI_Subsidy_Indus() {
		return ISF_ACI_Subsidy_Indus;
	}

	public void setISF_ACI_Subsidy_Indus(Long iSF_ACI_Subsidy_Indus) {
		ISF_ACI_Subsidy_Indus = iSF_ACI_Subsidy_Indus;
	}

	public String getAciSubsidyRemark() {
		return aciSubsidyRemark;
	}

	public void setAciSubsidyRemark(String aciSubsidyRemark) {
		this.aciSubsidyRemark = aciSubsidyRemark;
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

	public Long getISF_AII_Subsidy_DIVSCSTF() {
		return ISF_AII_Subsidy_DIVSCSTF;
	}

	public void setISF_AII_Subsidy_DIVSCSTF(Long iSF_AII_Subsidy_DIVSCSTF) {
		ISF_AII_Subsidy_DIVSCSTF = iSF_AII_Subsidy_DIVSCSTF;
	}

	public String getAiiSubsidyRemark() {
		return aiiSubsidyRemark;
	}

	public void setAiiSubsidyRemark(String aiiSubsidyRemark) {
		this.aiiSubsidyRemark = aiiSubsidyRemark;
	}

	public Long getISF_Loan_Subsidy() {
		return ISF_Loan_Subsidy;
	}

	public void setISF_Loan_Subsidy(Long iSF_Loan_Subsidy) {
		ISF_Loan_Subsidy = iSF_Loan_Subsidy;
	}

	public String getLoanIntSubRemark() {
		return loanIntSubRemark;
	}

	public void setLoanIntSubRemark(String loanIntSubRemark) {
		this.loanIntSubRemark = loanIntSubRemark;
	}

	public Long getISF_Tax_Credit_Reim() {
		return ISF_Tax_Credit_Reim;
	}

	public void setISF_Tax_Credit_Reim(Long iSF_Tax_Credit_Reim) {
		ISF_Tax_Credit_Reim = iSF_Tax_Credit_Reim;
	}

	public String getInputTaxRemark() {
		return inputTaxRemark;
	}

	public void setInputTaxRemark(String inputTaxRemark) {
		this.inputTaxRemark = inputTaxRemark;
	}

	public Long getISF_EX_E_Duty() {
		return ISF_EX_E_Duty;
	}

	public void setISF_EX_E_Duty(Long iSF_EX_E_Duty) {
		ISF_EX_E_Duty = iSF_EX_E_Duty;
	}

	public String getElecDutyCapRemark() {
		return elecDutyCapRemark;
	}

	public void setElecDutyCapRemark(String elecDutyCapRemark) {
		this.elecDutyCapRemark = elecDutyCapRemark;
	}

	public Long getISF_EX_E_Duty_PC() {
		return ISF_EX_E_Duty_PC;
	}

	public void setISF_EX_E_Duty_PC(Long iSF_EX_E_Duty_PC) {
		ISF_EX_E_Duty_PC = iSF_EX_E_Duty_PC;
	}

	public String getElecDutyDrawnRemark() {
		return elecDutyDrawnRemark;
	}

	public void setElecDutyDrawnRemark(String elecDutyDrawnRemark) {
		this.elecDutyDrawnRemark = elecDutyDrawnRemark;
	}

	public Long getISF_EX_Mandee_Fee() {
		return ISF_EX_Mandee_Fee;
	}

	public void setISF_EX_Mandee_Fee(Long iSF_EX_Mandee_Fee) {
		ISF_EX_Mandee_Fee = iSF_EX_Mandee_Fee;
	}

	public String getMandiFeeRemark() {
		return mandiFeeRemark;
	}

	public void setMandiFeeRemark(String mandiFeeRemark) {
		this.mandiFeeRemark = mandiFeeRemark;
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

	public String getFinYr1() {
		return finYr1;
	}

	public void setFinYr1(String finYr1) {
		this.finYr1 = finYr1;
	}

	public Long getTurnoverOfSales1() {
		return turnoverOfSales1;
	}

	public void setTurnoverOfSales1(Long turnoverOfSales1) {
		this.turnoverOfSales1 = turnoverOfSales1;
	}

	public Long getTurnoverProduction1() {
		return turnoverProduction1;
	}

	public void setTurnoverProduction1(Long turnoverProduction1) {
		this.turnoverProduction1 = turnoverProduction1;
	}

	public String getFinYr2() {
		return finYr2;
	}

	public void setFinYr2(String finYr2) {
		this.finYr2 = finYr2;
	}

	public Long getTurnoverOfSales2() {
		return turnoverOfSales2;
	}

	public void setTurnoverOfSales2(Long turnoverOfSales2) {
		this.turnoverOfSales2 = turnoverOfSales2;
	}

	public Long getTurnoverProduction2() {
		return turnoverProduction2;
	}

	public void setTurnoverProduction2(Long turnoverProduction2) {
		this.turnoverProduction2 = turnoverProduction2;
	}

	public String getFinYr3() {
		return finYr3;
	}

	public void setFinYr3(String finYr3) {
		this.finYr3 = finYr3;
	}

	public Long getTurnoverOfSales3() {
		return turnoverOfSales3;
	}

	public void setTurnoverOfSales3(Long turnoverOfSales3) {
		this.turnoverOfSales3 = turnoverOfSales3;
	}

	public Long getTurnoverProduction3() {
		return turnoverProduction3;
	}

	public void setTurnoverProduction3(Long turnoverProduction3) {
		this.turnoverProduction3 = turnoverProduction3;
	}

	public String getFinYr4() {
		return finYr4;
	}

	public void setFinYr4(String finYr4) {
		this.finYr4 = finYr4;
	}

	public Long getTurnoverOfSales4() {
		return turnoverOfSales4;
	}

	public void setTurnoverOfSales4(Long turnoverOfSales4) {
		this.turnoverOfSales4 = turnoverOfSales4;
	}

	public Long getTurnoverProduction4() {
		return turnoverProduction4;
	}

	public void setTurnoverProduction4(Long turnoverProduction4) {
		this.turnoverProduction4 = turnoverProduction4;
	}

	public String getFinYr5() {
		return finYr5;
	}

	public void setFinYr5(String finYr5) {
		this.finYr5 = finYr5;
	}

	public Long getTurnoverOfSales5() {
		return turnoverOfSales5;
	}

	public void setTurnoverOfSales5(Long turnoverOfSales5) {
		this.turnoverOfSales5 = turnoverOfSales5;
	}

	public Long getTurnoverProduction5() {
		return turnoverProduction5;
	}

	public void setTurnoverProduction5(Long turnoverProduction5) {
		this.turnoverProduction5 = turnoverProduction5;
	}

	public String getTurnOverObserve() {
		return turnOverObserve;
	}

	public void setTurnOverObserve(String turnOverObserve) {
		this.turnOverObserve = turnOverObserve;
	}

	public String getDurationFinYr1New() {
		return durationFinYr1New;
	}

	public void setDurationFinYr1New(String durationFinYr1New) {
		this.durationFinYr1New = durationFinYr1New;
	}

	public Long getAmtOfNetSGST1New() {
		return amtOfNetSGST1New;
	}

	public void setAmtOfNetSGST1New(Long amtOfNetSGST1New) {
		this.amtOfNetSGST1New = amtOfNetSGST1New;
	}

	public double getAmtOfNetSGSTReim1New() {
		return amtOfNetSGSTReim1New;
	}

	public void setAmtOfNetSGSTReim1New(double amtOfNetSGSTReim1New) {
		this.amtOfNetSGSTReim1New = amtOfNetSGSTReim1New;
	}

	public String getDurationFinYr2New() {
		return durationFinYr2New;
	}

	public void setDurationFinYr2New(String durationFinYr2New) {
		this.durationFinYr2New = durationFinYr2New;
	}

	public Long getAmtOfNetSGST2New() {
		return amtOfNetSGST2New;
	}

	public void setAmtOfNetSGST2New(Long amtOfNetSGST2New) {
		this.amtOfNetSGST2New = amtOfNetSGST2New;
	}

	public double getAmtOfNetSGSTReim2New() {
		return amtOfNetSGSTReim2New;
	}

	public void setAmtOfNetSGSTReim2New(double amtOfNetSGSTReim2New) {
		this.amtOfNetSGSTReim2New = amtOfNetSGSTReim2New;
	}

	public String getDurationFinYr3New() {
		return durationFinYr3New;
	}

	public void setDurationFinYr3New(String durationFinYr3New) {
		this.durationFinYr3New = durationFinYr3New;
	}

	public Long getAmtOfNetSGST3New() {
		return amtOfNetSGST3New;
	}

	public void setAmtOfNetSGST3New(Long amtOfNetSGST3New) {
		this.amtOfNetSGST3New = amtOfNetSGST3New;
	}

	public double getAmtOfNetSGSTReim3New() {
		return amtOfNetSGSTReim3New;
	}

	public void setAmtOfNetSGSTReim3New(double amtOfNetSGSTReim3New) {
		this.amtOfNetSGSTReim3New = amtOfNetSGSTReim3New;
	}

	public String getDurationFinYr4New() {
		return durationFinYr4New;
	}

	public void setDurationFinYr4New(String durationFinYr4New) {
		this.durationFinYr4New = durationFinYr4New;
	}

	public Long getAmtOfNetSGST4New() {
		return amtOfNetSGST4New;
	}

	public void setAmtOfNetSGST4New(Long amtOfNetSGST4New) {
		this.amtOfNetSGST4New = amtOfNetSGST4New;
	}

	public double getAmtOfNetSGSTReim4New() {
		return amtOfNetSGSTReim4New;
	}

	public void setAmtOfNetSGSTReim4New(double amtOfNetSGSTReim4New) {
		this.amtOfNetSGSTReim4New = amtOfNetSGSTReim4New;
	}

	public String getDurationFinYr1() {
		return durationFinYr1;
	}

	public void setDurationFinYr1(String durationFinYr1) {
		this.durationFinYr1 = durationFinYr1;
	}

	public Long getTurnoverOfProduction1() {
		return turnoverOfProduction1;
	}

	public void setTurnoverOfProduction1(Long turnoverOfProduction1) {
		this.turnoverOfProduction1 = turnoverOfProduction1;
	}

	public Long getTtlAmtCommTax1() {
		return ttlAmtCommTax1;
	}

	public void setTtlAmtCommTax1(Long ttlAmtCommTax1) {
		this.ttlAmtCommTax1 = ttlAmtCommTax1;
	}

	public Long getAmtOfNetSGST1() {
		return amtOfNetSGST1;
	}

	public void setAmtOfNetSGST1(Long amtOfNetSGST1) {
		this.amtOfNetSGST1 = amtOfNetSGST1;
	}

	public Long getIncreTurnover1() {
		return increTurnover1;
	}

	public void setIncreTurnover1(Long increTurnover1) {
		this.increTurnover1 = increTurnover1;
	}

	public Long getIncreNetSGST1() {
		return increNetSGST1;
	}

	public void setIncreNetSGST1(Long increNetSGST1) {
		this.increNetSGST1 = increNetSGST1;
	}

	public Long getAmtOfNetSGSTReim1() {
		return amtOfNetSGSTReim1;
	}

	public void setAmtOfNetSGSTReim1(Long amtOfNetSGSTReim1) {
		this.amtOfNetSGSTReim1 = amtOfNetSGSTReim1;
	}

	public String getDurationFinYr2() {
		return durationFinYr2;
	}

	public void setDurationFinYr2(String durationFinYr2) {
		this.durationFinYr2 = durationFinYr2;
	}

	public Long getTurnoverOfProduction2() {
		return turnoverOfProduction2;
	}

	public void setTurnoverOfProduction2(Long turnoverOfProduction2) {
		this.turnoverOfProduction2 = turnoverOfProduction2;
	}

	public Long getTtlAmtCommTax2() {
		return ttlAmtCommTax2;
	}

	public void setTtlAmtCommTax2(Long ttlAmtCommTax2) {
		this.ttlAmtCommTax2 = ttlAmtCommTax2;
	}

	public Long getAmtOfNetSGST2() {
		return amtOfNetSGST2;
	}

	public void setAmtOfNetSGST2(Long amtOfNetSGST2) {
		this.amtOfNetSGST2 = amtOfNetSGST2;
	}

	public Long getIncreTurnover2() {
		return increTurnover2;
	}

	public void setIncreTurnover2(Long increTurnover2) {
		this.increTurnover2 = increTurnover2;
	}

	public Long getIncreNetSGST2() {
		return increNetSGST2;
	}

	public void setIncreNetSGST2(Long increNetSGST2) {
		this.increNetSGST2 = increNetSGST2;
	}

	public Long getAmtOfNetSGSTReim2() {
		return amtOfNetSGSTReim2;
	}

	public void setAmtOfNetSGSTReim2(Long amtOfNetSGSTReim2) {
		this.amtOfNetSGSTReim2 = amtOfNetSGSTReim2;
	}

	public String getDurationFinYr3() {
		return durationFinYr3;
	}

	public void setDurationFinYr3(String durationFinYr3) {
		this.durationFinYr3 = durationFinYr3;
	}

	public Long getTurnoverOfProduction3() {
		return turnoverOfProduction3;
	}

	public void setTurnoverOfProduction3(Long turnoverOfProduction3) {
		this.turnoverOfProduction3 = turnoverOfProduction3;
	}

	public Long getTtlAmtCommTax3() {
		return ttlAmtCommTax3;
	}

	public void setTtlAmtCommTax3(Long ttlAmtCommTax3) {
		this.ttlAmtCommTax3 = ttlAmtCommTax3;
	}

	public Long getAmtOfNetSGST3() {
		return amtOfNetSGST3;
	}

	public void setAmtOfNetSGST3(Long amtOfNetSGST3) {
		this.amtOfNetSGST3 = amtOfNetSGST3;
	}

	public Long getIncreTurnover3() {
		return increTurnover3;
	}

	public void setIncreTurnover3(Long increTurnover3) {
		this.increTurnover3 = increTurnover3;
	}

	public Long getIncreNetSGST3() {
		return increNetSGST3;
	}

	public void setIncreNetSGST3(Long increNetSGST3) {
		this.increNetSGST3 = increNetSGST3;
	}

	public Long getAmtOfNetSGSTReim3() {
		return amtOfNetSGSTReim3;
	}

	public void setAmtOfNetSGSTReim3(Long amtOfNetSGSTReim3) {
		this.amtOfNetSGSTReim3 = amtOfNetSGSTReim3;
	}

	public String getDurationFinYr4() {
		return durationFinYr4;
	}

	public void setDurationFinYr4(String durationFinYr4) {
		this.durationFinYr4 = durationFinYr4;
	}

	public Long getTurnoverOfProduction4() {
		return turnoverOfProduction4;
	}

	public void setTurnoverOfProduction4(Long turnoverOfProduction4) {
		this.turnoverOfProduction4 = turnoverOfProduction4;
	}

	public Long getTtlAmtCommTax4() {
		return ttlAmtCommTax4;
	}

	public void setTtlAmtCommTax4(Long ttlAmtCommTax4) {
		this.ttlAmtCommTax4 = ttlAmtCommTax4;
	}

	public Long getAmtOfNetSGST4() {
		return amtOfNetSGST4;
	}

	public void setAmtOfNetSGST4(Long amtOfNetSGST4) {
		this.amtOfNetSGST4 = amtOfNetSGST4;
	}

	public Long getIncreTurnover4() {
		return increTurnover4;
	}

	public void setIncreTurnover4(Long increTurnover4) {
		this.increTurnover4 = increTurnover4;
	}

	public Long getIncreNetSGST4() {
		return increNetSGST4;
	}

	public void setIncreNetSGST4(Long increNetSGST4) {
		this.increNetSGST4 = increNetSGST4;
	}

	public Long getAmtOfNetSGSTReim4() {
		return amtOfNetSGSTReim4;
	}

	public void setAmtOfNetSGSTReim4(Long amtOfNetSGSTReim4) {
		this.amtOfNetSGSTReim4 = amtOfNetSGSTReim4;
	}

	public String getDurationFinYr5() {
		return durationFinYr5;
	}

	public void setDurationFinYr5(String durationFinYr5) {
		this.durationFinYr5 = durationFinYr5;
	}

	public Long getTurnoverOfProduction5() {
		return turnoverOfProduction5;
	}

	public void setTurnoverOfProduction5(Long turnoverOfProduction5) {
		this.turnoverOfProduction5 = turnoverOfProduction5;
	}

	public Long getTtlAmtCommTax5() {
		return ttlAmtCommTax5;
	}

	public void setTtlAmtCommTax5(Long ttlAmtCommTax5) {
		this.ttlAmtCommTax5 = ttlAmtCommTax5;
	}

	public Long getAmtOfNetSGST5() {
		return amtOfNetSGST5;
	}

	public void setAmtOfNetSGST5(Long amtOfNetSGST5) {
		this.amtOfNetSGST5 = amtOfNetSGST5;
	}

	public Long getIncreTurnover5() {
		return increTurnover5;
	}

	public void setIncreTurnover5(Long increTurnover5) {
		this.increTurnover5 = increTurnover5;
	}

	public Long getIncreNetSGST5() {
		return increNetSGST5;
	}

	public void setIncreNetSGST5(Long increNetSGST5) {
		this.increNetSGST5 = increNetSGST5;
	}

	public Long getAmtOfNetSGSTReim5() {
		return amtOfNetSGSTReim5;
	}

	public void setAmtOfNetSGSTReim5(Long amtOfNetSGSTReim5) {
		this.amtOfNetSGSTReim5 = amtOfNetSGSTReim5;
	}

	public String getSgstAmtReimObserve() {
		return sgstAmtReimObserve;
	}

	public void setSgstAmtReimObserve(String sgstAmtReimObserve) {
		this.sgstAmtReimObserve = sgstAmtReimObserve;
	}

	public String getAdmissibleBenefits() {
		return admissibleBenefits;
	}

	public void setAdmissibleBenefits(String admissibleBenefits) {
		this.admissibleBenefits = admissibleBenefits;
	}

	public String getCisTblObserve() {
		return cisTblObserve;
	}

	public void setCisTblObserve(String cisTblObserve) {
		this.cisTblObserve = cisTblObserve;
	}

	public String getIemStatusCompl() {
		return iemStatusCompl;
	}

	public void setIemStatusCompl(String iemStatusCompl) {
		this.iemStatusCompl = iemStatusCompl;
	}

	public String getIemStatusObserve() {
		return iemStatusObserve;
	}

	public void setIemStatusObserve(String iemStatusObserve) {
		this.iemStatusObserve = iemStatusObserve;
	}

	public String getNewExpDivCompl() {
		return newExpDivCompl;
	}

	public void setNewExpDivCompl(String newExpDivCompl) {
		this.newExpDivCompl = newExpDivCompl;
	}

	public String getNewExpDivObserve() {
		return newExpDivObserve;
	}

	public void setNewExpDivObserve(String newExpDivObserve) {
		this.newExpDivObserve = newExpDivObserve;
	}

	public String getProTtlInvCompl() {
		return proTtlInvCompl;
	}

	public void setProTtlInvCompl(String proTtlInvCompl) {
		this.proTtlInvCompl = proTtlInvCompl;
	}

	public String getProTtlInvObserve() {
		return proTtlInvObserve;
	}

	public void setProTtlInvObserve(String proTtlInvObserve) {
		this.proTtlInvObserve = proTtlInvObserve;
	}

	public String getCutOffDateCompl() {
		return CutOffDateCompl;
	}

	public void setCutOffDateCompl(String cutOffDateCompl) {
		CutOffDateCompl = cutOffDateCompl;
	}

	public String getCutOffDateObserve() {
		return cutOffDateObserve;
	}

	public void setCutOffDateObserve(String cutOffDateObserve) {
		this.cutOffDateObserve = cutOffDateObserve;
	}

	public String getDateCommProCompl() {
		return dateCommProCompl;
	}

	public void setDateCommProCompl(String dateCommProCompl) {
		this.dateCommProCompl = dateCommProCompl;
	}

	public String getDateCommProObserve() {
		return dateCommProObserve;
	}

	public void setDateCommProObserve(String dateCommProObserve) {
		this.dateCommProObserve = dateCommProObserve;
	}

	public String getInvPeriodCompl() {
		return invPeriodCompl;
	}

	public void setInvPeriodCompl(String invPeriodCompl) {
		this.invPeriodCompl = invPeriodCompl;
	}

	public String getInvPeriodObserve() {
		return invPeriodObserve;
	}

	public void setInvPeriodObserve(String invPeriodObserve) {
		this.invPeriodObserve = invPeriodObserve;
	}

	public String getProjPhasesCompl() {
		return projPhasesCompl;
	}

	public void setProjPhasesCompl(String projPhasesCompl) {
		this.projPhasesCompl = projPhasesCompl;
	}

	public String getProjPhasesObserve() {
		return projPhasesObserve;
	}

	public void setProjPhasesObserve(String projPhasesObserve) {
		this.projPhasesObserve = projPhasesObserve;
	}

	public String getDirIndirWorkersCompl() {
		return dirIndirWorkersCompl;
	}

	public void setDirIndirWorkersCompl(String dirIndirWorkersCompl) {
		this.dirIndirWorkersCompl = dirIndirWorkersCompl;
	}

	public String getDirIndirWorkersObserve() {
		return dirIndirWorkersObserve;
	}

	public void setDirIndirWorkersObserve(String dirIndirWorkersObserve) {
		this.dirIndirWorkersObserve = dirIndirWorkersObserve;
	}

	public String getDprCompl() {
		return dprCompl;
	}

	public void setDprCompl(String dprCompl) {
		this.dprCompl = dprCompl;
	}

	public String getDprObserve() {
		return dprObserve;
	}

	public void setDprObserve(String dprObserve) {
		this.dprObserve = dprObserve;
	}

	public String getListOfAssetsCompl() {
		return listOfAssetsCompl;
	}

	public void setListOfAssetsCompl(String listOfAssetsCompl) {
		this.listOfAssetsCompl = listOfAssetsCompl;
	}

	public String getListOfAssetsObserve() {
		return listOfAssetsObserve;
	}

	public void setListOfAssetsObserve(String listOfAssetsObserve) {
		this.listOfAssetsObserve = listOfAssetsObserve;
	}

	public String getUndertakingCompl() {
		return undertakingCompl;
	}

	public void setUndertakingCompl(String undertakingCompl) {
		this.undertakingCompl = undertakingCompl;
	}

	public String getUndertakingObserve() {
		return undertakingObserve;
	}

	public void setUndertakingObserve(String undertakingObserve) {
		this.undertakingObserve = undertakingObserve;
	}

	public String getAuthSignCompl() {
		return authSignCompl;
	}

	public void setAuthSignCompl(String authSignCompl) {
		this.authSignCompl = authSignCompl;
	}

	public String getAuthSignObserve() {
		return authSignObserve;
	}

	public void setAuthSignObserve(String authSignObserve) {
		this.authSignObserve = authSignObserve;
	}

	public String getApplFormatCompl() {
		return applFormatCompl;
	}

	public void setApplFormatCompl(String applFormatCompl) {
		this.applFormatCompl = applFormatCompl;
	}

	public String getApplFormatObserve() {
		return applFormatObserve;
	}

	public void setApplFormatObserve(String applFormatObserve) {
		this.applFormatObserve = applFormatObserve;
	}

	public String getSuppDocDirCompl() {
		return suppDocDirCompl;
	}

	public void setSuppDocDirCompl(String suppDocDirCompl) {
		this.suppDocDirCompl = suppDocDirCompl;
	}

	public String getSuppDocDirObserve() {
		return suppDocDirObserve;
	}

	public void setSuppDocDirObserve(String suppDocDirObserve) {
		this.suppDocDirObserve = suppDocDirObserve;
	}

	public String getBankApprCompl() {
		return bankApprCompl;
	}

	public void setBankApprCompl(String bankApprCompl) {
		this.bankApprCompl = bankApprCompl;
	}

	public String getBankApprObserve() {
		return bankApprObserve;
	}

	public void setBankApprObserve(String bankApprObserve) {
		this.bankApprObserve = bankApprObserve;
	}

	public String getEligOfBenefitsNote() {
		return eligOfBenefitsNote;
	}

	public void setEligOfBenefitsNote(String eligOfBenefitsNote) {
		this.eligOfBenefitsNote = eligOfBenefitsNote;
	}

	public String getEligOfBenefitsComments() {
		return eligOfBenefitsComments;
	}

	public void setEligOfBenefitsComments(String eligOfBenefitsComments) {
		this.eligOfBenefitsComments = eligOfBenefitsComments;
	}

	public Long getTtlEligAmt() {
		return ttlEligAmt;
	}

	public void setTtlEligAmt(Long ttlEligAmt) {
		this.ttlEligAmt = ttlEligAmt;
	}

	public Long getPropDisbAmt() {
		return propDisbAmt;
	}

	public void setPropDisbAmt(Long propDisbAmt) {
		this.propDisbAmt = propDisbAmt;
	}

	public Long getEligBenefitsAmt() {
		return eligBenefitsAmt;
	}

	public void setEligBenefitsAmt(Long eligBenefitsAmt) {
		this.eligBenefitsAmt = eligBenefitsAmt;
	}

	public Long getBalancePeriodAmt() {
		return balancePeriodAmt;
	}

	public void setBalancePeriodAmt(Long balancePeriodAmt) {
		this.balancePeriodAmt = balancePeriodAmt;
	}

	public String getDisbEffDate() {
		return disbEffDate;
	}

	public void setDisbEffDate(String disbEffDate) {
		this.disbEffDate = disbEffDate;
	}

	public String getDisbEffComments() {
		return disbEffComments;
	}

	public void setDisbEffComments(String disbEffComments) {
		this.disbEffComments = disbEffComments;
	}

	public String getTtlEligIncentives() {
		return ttlEligIncentives;
	}

	public void setTtlEligIncentives(String ttlEligIncentives) {
		this.ttlEligIncentives = ttlEligIncentives;
	}

	public String getDateAdmissibilityInc() {
		return dateAdmissibilityInc;
	}

	public void setDateAdmissibilityInc(String dateAdmissibilityInc) {
		this.dateAdmissibilityInc = dateAdmissibilityInc;
	}

	public String getComplCondiComments() {
		return complCondiComments;
	}

	public void setComplCondiComments(String complCondiComments) {
		this.complCondiComments = complCondiComments;
	}

	public String getWheProdSetupPhs() {
		return wheProdSetupPhs;
	}

	public void setWheProdSetupPhs(String wheProdSetupPhs) {
		this.wheProdSetupPhs = wheProdSetupPhs;
	}

	public String getWheProdSetupPhsObserv() {
		return wheProdSetupPhsObserv;
	}

	public void setWheProdSetupPhsObserv(String wheProdSetupPhsObserv) {
		this.wheProdSetupPhsObserv = wheProdSetupPhsObserv;
	}

	public Date getOptCutofDate() {
		return optCutofDate;
	}

	public void setOptCutofDate(Date optCutofDate) {
		this.optCutofDate = optCutofDate;
	}

	public Date getDoFirstLoanCIS() {
		return doFirstLoanCIS;
	}

	public void setDoFirstLoanCIS(Date doFirstLoanCIS) {
		this.doFirstLoanCIS = doFirstLoanCIS;
	}

	public Date getDoLastLoanCIS() {
		return doLastLoanCIS;
	}

	public void setDoLastLoanCIS(Date doLastLoanCIS) {
		this.doLastLoanCIS = doLastLoanCIS;
	}

	public Date getDoFirstDisCIS() {
		return doFirstDisCIS;
	}

	public void setDoFirstDisCIS(Date doFirstDisCIS) {
		this.doFirstDisCIS = doFirstDisCIS;
	}

	public Date getDoLastDisCIS() {
		return doLastDisCIS;
	}

	public void setDoLastDisCIS(Date doLastDisCIS) {
		this.doLastDisCIS = doLastDisCIS;
	}

	public Date getDoPaymentCIS() {
		return doPaymentCIS;
	}

	public void setDoPaymentCIS(Date doPaymentCIS) {
		this.doPaymentCIS = doPaymentCIS;
	}

	public Date getDoFirstLoanIIS() {
		return doFirstLoanIIS;
	}

	public void setDoFirstLoanIIS(Date doFirstLoanIIS) {
		this.doFirstLoanIIS = doFirstLoanIIS;
	}

	public Date getDoLastLoanIIS() {
		return doLastLoanIIS;
	}

	public void setDoLastLoanIIS(Date doLastLoanIIS) {
		this.doLastLoanIIS = doLastLoanIIS;
	}

	public Date getDoFirstDisIIS() {
		return doFirstDisIIS;
	}

	public void setDoFirstDisIIS(Date doFirstDisIIS) {
		this.doFirstDisIIS = doFirstDisIIS;
	}

	public Date getDoLastDisIIS() {
		return doLastDisIIS;
	}

	public void setDoLastDisIIS(Date doLastDisIIS) {
		this.doLastDisIIS = doLastDisIIS;
	}

	public Date getDoPaymentIIS() {
		return doPaymentIIS;
	}

	public void setDoPaymentIIS(Date doPaymentIIS) {
		this.doPaymentIIS = doPaymentIIS;
	}

	public Long getCisCostOfProjectAmt() {
		return cisCostOfProjectAmt;
	}

	public void setCisCostOfProjectAmt(Long cisCostOfProjectAmt) {
		this.cisCostOfProjectAmt = cisCostOfProjectAmt;
	}

	public Long getCisPlantMachAmt() {
		return cisPlantMachAmt;
	}

	public void setCisPlantMachAmt(Long cisPlantMachAmt) {
		this.cisPlantMachAmt = cisPlantMachAmt;
	}

	public Long getCisEntireProjectAmt() {
		return cisEntireProjectAmt;
	}

	public void setCisEntireProjectAmt(Long cisEntireProjectAmt) {
		this.cisEntireProjectAmt = cisEntireProjectAmt;
	}

	public Double getCisTermPlantMachAmt() {
		return cisTermPlantMachAmt;
	}

	public void setCisTermPlantMachAmt(Double cisTermPlantMachAmt) {
		this.cisTermPlantMachAmt = cisTermPlantMachAmt;
	}

	public Long getCisApplROIAmt() {
		return cisApplROIAmt;
	}

	public void setCisApplROIAmt(Long cisApplROIAmt) {
		this.cisApplROIAmt = cisApplROIAmt;
	}

	public Long getCisIntPaidAmt() {
		return cisIntPaidAmt;
	}

	public void setCisIntPaidAmt(Long cisIntPaidAmt) {
		this.cisIntPaidAmt = cisIntPaidAmt;
	}

	public String getCisRoiPMAmt() {
		return cisRoiPMAmt;
	}

	public void setCisRoiPMAmt(String cisRoiPMAmt) {
		this.cisRoiPMAmt = cisRoiPMAmt;
	}

	public Double getCisIntPM5Amt() {
		return cisIntPM5Amt;
	}

	public void setCisIntPM5Amt(Double cisIntPM5Amt) {
		this.cisIntPM5Amt = cisIntPM5Amt;
	}

	public String getCisObserve() {
		return cisObserve;
	}

	public void setCisObserve(String cisObserve) {
		this.cisObserve = cisObserve;
	}

	public String getConfProvbyCTD() {
		return confProvbyCTD;
	}

	public void setConfProvbyCTD(String confProvbyCTD) {
		this.confProvbyCTD = confProvbyCTD;
	}

	public String getConfProvbyBank() {
		return confProvbyBank;
	}

	public void setConfProvbyBank(String confProvbyBank) {
		this.confProvbyBank = confProvbyBank;
	}

	public String getExternalERD() {
		return externalERD;
	}

	public void setExternalERD(String externalERD) {
		this.externalERD = externalERD;
	}

	public String getConfProvbyCTDDoc() {
		return confProvbyCTDDoc;
	}

	public void setConfProvbyCTDDoc(String confProvbyCTDDoc) {
		this.confProvbyCTDDoc = confProvbyCTDDoc;
	}

	public String getConfProvbyBankDoc() {
		return confProvbyBankDoc;
	}

	public void setConfProvbyBankDoc(String confProvbyBankDoc) {
		this.confProvbyBankDoc = confProvbyBankDoc;
	}

	public String getExternalERDDoc() {
		return externalERDDoc;
	}

	public void setExternalERDDoc(String externalERDDoc) {
		this.externalERDDoc = externalERDDoc;
	}

	public String getConfProvbyCTDDocBase64() {
		return confProvbyCTDDocBase64;
	}

	public void setConfProvbyCTDDocBase64(String confProvbyCTDDocBase64) {
		this.confProvbyCTDDocBase64 = confProvbyCTDDocBase64;
	}

	public String getConfProvbyBankDocBase64() {
		return confProvbyBankDocBase64;
	}

	public void setConfProvbyBankDocBase64(String confProvbyBankDocBase64) {
		this.confProvbyBankDocBase64 = confProvbyBankDocBase64;
	}

	public String getExternalERDDocBase64() {
		return externalERDDocBase64;
	}

	public void setExternalERDDocBase64(String externalERDDocBase64) {
		this.externalERDDocBase64 = externalERDDocBase64;
	}

	public String getClamSactniisObserv() {
		return clamSactniisObserv;
	}

	public void setClamSactniisObserv(String clamSactniisObserv) {
		this.clamSactniisObserv = clamSactniisObserv;
	}

	
	
}
