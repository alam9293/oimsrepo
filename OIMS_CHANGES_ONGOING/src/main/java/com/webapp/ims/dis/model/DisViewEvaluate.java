package com.webapp.ims.dis.model;

import java.io.Serializable;
import java.util.Date;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.format.annotation.DateTimeFormat;


@Entity
@Table(name = "Dis_View_Evaluate", schema = "loc")
public class DisViewEvaluate
{

	@Id
	@Column(name = "Evaluate_Id")
	private String evaluateId;

	@Column(name = "Apc_Id")
	private String apcId;
	// QIS observation
	@Column(name = "qisobservation")
	private String qisobservation;

	//Table-11
	 
	
	private transient String Sancdldrbank;
	

	public String getSancdldrbank() {
		return Sancdldrbank;
	}

	public void setSancdldrbank(String sancdldrbank) {
		Sancdldrbank = sancdldrbank;
	}

	public java.util.Date getSancdDate() {
		return SancdDate;
	}

	public void setSancdDate(java.util.Date sancdDate) {
		SancdDate = sancdDate;
	}

	public int getSancdAmt() {
		return SancdAmt;
	}

	public void setSancdAmt(int sancdAmt) {
		SancdAmt = sancdAmt;
	}

	public Double getSancdInstRate() {
		return SancdInstRate;
	}

	public void setSancdInstRate(Double sancdInstRate) {
		SancdInstRate = sancdInstRate;
	}

	@Temporal(TemporalType.DATE)
	private  transient java.util.Date SancdDate;


	private transient int SancdAmt;

	
	private transient Double SancdInstRate;

	// table 12

	@Column(name = "firstbybank")
	private Long firstbybank;

	@Column(name = "firstbycompany")
	private Long firstbycompany;

	@Column(name = "firstannualpermissible")
	private Long firstannualpermissible;

	@Column(name = "firstadmissibleamount")
	private Long firstadmissibleamount;

	@Column(name = "secondbybank")
	private Long secondbybank;

	@Column(name = "secondbycompany")
	private Long secondbycompany;

	@Column(name = "secondannualpermissible")
	private Long secondannualpermissible;

	@Column(name = "secondadmissibleamount")
	private Long secondadmissibleamount;

	@Column(name = "thirdbybank")
	private Long thirdbybank;

	@Column(name = "thirdbycompany")
	private Long thirdbycompany;

	@Column(name = "thirdannualpermissible")
	private Long thirdannualpermissible;

	@Column(name = "thirdadmissibleamount")
	private Long thirdadmissibleamount;

	@Column(name = "fourthbybank")
	private Long fourthbybank;

	@Column(name = "fourthbycompany")
	private Long fourthbycompany;

	@Column(name = "fourthannualpermissible")
	private Long fourthannualpermissible;

	@Column(name = "fourthadmissibleamount")
	private Long fourthadmissibleamount;

	@Column(name = "fifthbybank")
	private Long fifthbybank;

	@Column(name = "fifthbycompany")
	private Long fifthbycompany;

	@Column(name = "fifthannualpermissible")
	private Long fifthannualpermissible;

	@Column(name = "fifthadmissibleamount")
	private Long fifthadmissibleamount;

	@Column(name = "totalbybank")
	private Long totalbybank;

	@Column(name = "totalbycompany")
	private Long totalbycompany;

	@Column(name = "totalannualpermissible")
	private Long totalannualpermissible;

	@Column(name = "totaladmissibleamount")
	private Long totaladmissibleamount;

	// table 15

	@Column(name = "firstbybankiis")
	private Long firstbybankiis;

	@Column(name = "firstbycompanyiis")
	private Long firstbycompanyiis;

	@Column(name = "firstannualpermissibleiis")
	private Long firstannualpermissibleiis;

	@Column(name = "firstadmissibleamountiis")
	private Long firstadmissibleamountiis;

	@Column(name = "secondbybankiis")
	private Long secondbybankiis;

	@Column(name = "secondbycompanyiis")
	private Long secondbycompanyiis;

	@Column(name = "secondannualpermissibleiis")
	private Long secondannualpermissibleiis;

	@Column(name = "secondadmissibleamountiis")
	private Long secondadmissibleamountiis;

	@Column(name = "thirdbybankiis")
	private Long thirdbybankiis;

	@Column(name = "thirdbycompanyiis")
	private Long thirdbycompanyiis;

	@Column(name = "thirdannualpermissibleiis")
	private Long thirdannualpermissibleiis;

	@Column(name = "thirdadmissibleamountiis")
	private Long thirdadmissibleamountiis;

	@Column(name = "fourthbybankiis")
	private Long fourthbybankiis;

	@Column(name = "fourthbycompanyiis")
	private Long fourthbycompanyiis;

	@Column(name = "fourthannualpermissibleiis")
	private Long fourthannualpermissibleiis;

	@Column(name = "fourthadmissibleamountiis")
	private Long fourthadmissibleamountiis;

	@Column(name = "fifthbybankiis")
	private Long fifthbybankiis;

	@Column(name = "fifthbycompanyiis")
	private Long fifthbycompanyiis;

	@Column(name = "fifthannualpermissibleiis")
	private Long fifthannualpermissibleiis;

	@Column(name = "fifthadmissibleamountiis")
	private Long fifthadmissibleamountiis;

	@Column(name = "totalbybankiis")
	private Long totalbybankiis;

	@Column(name = "totalbycompanyiis")
	private Long totalbycompanyiis;

	@Column(name = "totalannualpermissibleiis")
	private Long totalannualpermissibleiis;

	@Column(name = "totaladmissibleamountiis")
	private Long totaladmissibleamountiis;

	// table 15

	@Column(name = "firstbybankqis")
	private Long firstbybankqis;

	@Column(name = "firstbycompanyqis")
	private Long firstbycompanyqis;

	@Column(name = "firstannualpermissibleqis")
	private Long firstannualpermissibleqis;

	@Column(name = "firstadmissibleamountqis")
	private Long firstadmissibleamountqis;

	@Column(name = "secondbybankqis")
	private Long secondbybankqis;

	@Column(name = "secondbycompanyqis")
	private Long secondbycompanyqis;

	@Column(name = "secondannualpermissibleqis")
	private Long secondannualpermissibleqis;

	@Column(name = "secondadmissibleamountqis")
	private Long secondadmissibleamountqis;

	@Column(name = "thirdbybankqis")
	private Long thirdbybankqis;

	@Column(name = "thirdbycompanyqis")
	private Long thirdbycompanyqis;

	@Column(name = "thirdannualpermissibleqis")
	private Long thirdannualpermissibleqis;

	@Column(name = "thirdadmissibleamountqis")
	private Long thirdadmissibleamountqis;

	@Column(name = "fourthbybankqis")
	private Long fourthbybankqis;

	@Column(name = "fourthbycompanyqis")
	private Long fourthbycompanyqis;

	@Column(name = "fourthannualpermissibleqis")
	private Long fourthannualpermissibleqis;

	@Column(name = "fourthadmissibleamountqis")
	private Long fourthadmissibleamountqis;

	@Column(name = "fifthbybankqis")
	private Long fifthbybankqis;

	@Column(name = "fifthbycompanyqis")
	private Long fifthbycompanyqis;

	@Column(name = "fifthannualpermissibleqis")
	private Long fifthannualpermissibleqis;

	@Column(name = "fifthadmissibleamountqis")
	private Long fifthadmissibleamountqis;

	@Column(name = "totalbybankqis")
	private Long totalbybankqis;

	@Column(name = "totalbycompanyqis")
	private Long totalbycompanyqis;

	@Column(name = "totalannualpermissibleqis")
	private Long totalannualpermissibleqis;

	@Column(name = "totaladmissibleamountqis")
	private Long totaladmissibleamountqis;

	// Top Table
	@Column(name = "Company_Name")
	private String companyName;

	@Column(name = "Product_Name")
	private String product;

	@Column(name = "Region_Name")
	private String region;

	@Column(name = "District_Name")
	private String district;

	@Column(name = "Investment_Amt")
	private Long investment;

	@Column(name = "Category_Type")
	private String category;

	@Column(name = "Add_Promoters_Details")
	private String addPromotersDetails;

	// table1
	@Column(name = "table1remark1")
	private String table1remark1;

	@Column(name = "table1remark2")
	private String table1remark2;

	@Column(name = "table1remark3")
	private String table1remark3;

	@Column(name = "table1remark4")
	private String table1remark4;

	@Column(name = "table1remark5")
	private String table1remark5;

	@Column(name = "table1remark6")
	private String table1remark6;

	// The Proposal- Company’s Request for Disbursement of
	// Incentives-----------------

	@Column(name = "Add_Of_Regis_Compl")
	private String addOfRegisCompl;

	@Column(name = "Add_Of_Regis_Observ")
	private String addOfRegisObserv;
//address
	@Column(name = "Add_Of_Factory_Compl")
	private String addOfFactoryCompl;

	@Column(name = "addOfOfficeCompl")
	private String addOfOfficeCompl;

	@Column(name = "Add_Of_Factory_Observ")
	private String addOfFactoryObserv;

	@Column(name = "Consti_Of_Compl")
	private String constiOfCompl;

	@Column(name = "Consti_Of_Observ")
	private String constiOfObserv;

	@Column(name = "Date_Of_Start_Compl")
	private String dateOfStartCompl;

	@Column(name = "Date_Of_Start_Observ")
	private String dateOfStartObserv;

	@Column(name = "New_UnitExpan_Compl")
	private String newUnitExpanCompl;

	@Column(name = "New_UnitExpan_Observ")
	private String newUnitExpanObserv;

	@Column(name = "Product_Wise_Compl")
	private String productWiseCompl;

	@Column(name = "Product_Wise_Observ")
	private String productWiseObserv;

	@Column(name = "GstnNo_Of_Compl")
	private String gstnNoOfCompl;

	@Column(name = "GstnNo_Of_Observ")
	private String gstnNoOfObserv;

	@Column(name = "PanNo_Of_Compl")
	private String panNoOfCompl;

	@Column(name = "PanNo_Of_Observ")
	private String panNoOfObserv;

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	// @NotNull
	@Column(name = "actualdatecomminv")
	@Temporal(TemporalType.DATE)
	private Date actualdatecomminv;

	@Column(name = "actualdatecomminvrem")
	private String actualdatecomminvrem;

	@Column(name = "applicantchangreq")
	private String applicantchangreq;

	@Column(name = "applicantchangreqrem")
	private String applicantchangreqrem;

	@Column(name = "nodalobservcutdate")
	private String nodalobservcutdate;

	@Column(name = "nodalobservcutdateremark")
	private String nodalobservcutdateremark;

	// Break-up of Cost of Project- Investment Details--------------------------

	// before opt
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	// @NotNull
	@Column(name = "beforeopt")
	@Temporal(TemporalType.DATE)
	private Date beforeopt;

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	// @NotNull
	@Column(name = "comproddate")
	@Temporal(TemporalType.DATE)
	private Date comprodDate;

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	// @NotNull
	@Column(name = "eligibileldate2")
	@Temporal(TemporalType.DATE)
	private Date EligibilelDate;

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	// @NotNull
	@Column(name = "byondcommercialdate")
	@Temporal(TemporalType.DATE)
	private Date byondcommercialdate;

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	// @NotNull
	@Column(name = "commercialdate")
	@Temporal(TemporalType.DATE)
	private Date CommercialDate;

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	// @NotNull
	@Column(name = "eleligibiledate")
	@Temporal(TemporalType.DATE)
	private Date eleligibiledate;

	// Land
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	// @NotNull
	@Column(name = "Land_Amt_Inv")
	@Temporal(TemporalType.DATE)
	private Date landAmtInv;

	@Column(name = "Land_Per_Dpr")
	private Long landPerDpr;

	
	@Column(name = "Land_Bank_Apprai")
	private Long landBankApprai;

	@Column(name = "Land_Per_Certi")
	private Long landPerCerti;

	@Column(name = "Land_CapInv_CA")
	private Long landCapInvCA;

	@Column(name = "landafterinv")
	private Long landafterinv;

	@Column(name = "Land_CapInv_Valuer")
	private Long landCapInvValuer;

	@Column(name = "Land_Statutory_Audit")
	private Long landStatutoryAudit;

	@Column(name = "landbanknodalca")
	private Long landBanknodalCA;

	@Column(name = "landnodal")
	private Long landnodal;

	// Building and Civil Works
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Column(name = "Build_Amt_Inv")
	@Temporal(TemporalType.DATE)
	private Date buildAmtInv;

	@Column(name = "Build_Per_Dpr")
	private Long buildPerDpr;

	@Column(name = "Build_Bank_Apprai")
	private Long buildBankApprai;

	@Column(name = "Build_Per_Certi")
	private Long buildPerCerti;

	@Column(name = "Build_CapInv_CA")
	private Long buildCapInvCA;

	@Column(name = "buildafterinv")
	private Long buildafterinv;

	@Column(name = "Build_CapInv_Valuer")
	private Long buildCapInvValuer;

	@Column(name = "Build_Statutory_Audit")
	private Long buildStatutoryAudit;

	@Column(name = "buildbanknodalca")
	private Long buildBanknodalCA;

	@Column(name = "buildnodal")
	private Long buildnodal;

	// Other Construction
	@Column(name = "othcperdpr")
	private Long othcPerDpr;

	@Column(name = "othcbankapprai")
	private Long othcBankApprai;

	@Column(name = "othcbanknodalca")
	private Long othcBanknodalCA;

	@Column(name = "othcpercerti")
	private Long othcPerCerti;

	@Column(name = "othccapinvca")
	private Long othcCapInvCA;

	@Column(name = "othccapinvvaluer")
	private Long othcCapInvValuer;

	@Column(name = "othcafterinv")
	private Long othcafterinv;

	@Column(name = "othcnodal")
	private Long othcnodal;

	// Plant & Machinery
	@Column(name = "Plant_Amt_Inv")
	private Long plantAmtInv;

	@Column(name = "Plant_Per_Dpr")
	private Long plantPerDpr;

	@Column(name = "Plant_Bank_Apprai")
	private Long plantBankApprai;

	@Column(name = "Plant_Per_Certificate")
	private Long plantPerCertificate;

	@Column(name = "Plant_CapInv_CA")
	private Long plantCapInvCA;

	@Column(name = "Plant_CapInv_Valuer")
	private Long plantCapInvValuer;

	@Column(name = "Plant_Statutory_Audit")
	private Long plantStatutoryAudit;

	@Column(name = "plantdafterinv")
	private Long plantdafterinv;

	@Column(name = "plantbanknodalca")
	private Long plantBanknodalCA;

	@Column(name = "plantnodal")
	private Long plantnodal;

	// Infrastructure facilities
	@Column(name = "Misc_Amt_Inv")
	private Long miscAmtInve;

	@Column(name = "Misc_Per_Dpr")
	private Long miscPerDpr;

	@Column(name = "Misc_Bank_Apprai")
	private Long miscBankApprai;

	@Column(name = "Misc_Per_Certi")
	private Long miscPerCertificate;

	@Column(name = "Misc_CapInv_CA")
	private Long miscCapInvCA;

	@Column(name = "Misc_CapInv_Valuer")
	private Long miscCapInvValuer;

	@Column(name = "Misc_Statutory_Audit")
	private Long miscStatutoryAuditor;

	@Column(name = "miscafterinv")
	private Long miscafterinv;

	@Column(name = "miscbanknodalca")
	private Long miscBanknodalCA;

	@Column(name = "miscnodal")
	private Long miscnodal;

	// SubTotal (A) (1+2+3+4)
	@Column(name = "SubTtl_A_Amt_Inv")
	private Long subTtlAAmtInv;

	@Column(name = "SubTtl_A_Per_Dpr")
	private Long subTtlAPerDpr;

	@Column(name = "SubTtl_A_Bank_Apprai")
	private Long subTtlABankApprai;

	@Column(name = "SubTtl_A_Per_Certi")
	private Long subTtlAPerCerti;

	@Column(name = "SubTtl_A_CapInv_CA")
	private Long subTtlACapInvCA;

	@Column(name = "SubTtl_A_CapInv_Valuer")
	private Long subTtlACapInvValuer;

	@Column(name = "SubTtl_A_Statutory_Audit")
	private Long subTtlAStatutoryAudit;

	@Column(name = "subttlaafterinv")
	private Long subTtlAafterinv;

	@Column(name = "subttlabanknodalca")
	private Long subttlabanknodalca;

	@Column(name = "subttlanodal")
	private Long subttlanodal;

	// Provision for Cost Escalation & Contingencies
	@Column(name = "Provision_Amt_Inv")
	private Long provisionAmtInve;

	@Column(name = "Provision_Per_Dpr")
	private Long provisionPerDpr;

	@Column(name = "Provision_Bank_Apprai")
	private Long provisionBankApprai;

	@Column(name = "Provision_Per_Certi")
	private Long provisionPerCerti;

	@Column(name = "Provision_CapInv_CA")
	private Long provisionCapInvCA;

	@Column(name = "Provision_CapInv_Valuer")
	private Long provisionCapInvValuer;

	@Column(name = "Provision_Statutory_Audit")
	private Long provisionStatutoryAudit;

	@Column(name = "provisionafterinv")
	private Long provisionafterinv;

	@Column(name = "prelimbanknodalca")
	private Long prelimBanknodalCA;

	@Column(name = "prelimnodal")
	private Long prelimnodal;

	// Preliminary & Preoperative Expenses
	@Column(name = "Prelim_Amt_Inv")
	private Long prelimAmtInve;

	@Column(name = "Prelim_Per_Dpr")
	private Long prelimPerDpr;

	@Column(name = "Prelim_Bank_Apprai")
	private Long prelimBankApprai;

	@Column(name = "Prelim_Per_Certi")
	private Long prelimPerCerti;

	@Column(name = "Prelim_CapInv_CA")
	private Long prelimCapInvCA;

	@Column(name = "Prelim_CapInv_Valuer")
	private Long prelimCapInvValuer;

	@Column(name = "Prelim_Statutory_Audit")
	private Long prelimStatutoryAudit;

	@Column(name = "prelimafterinv")
	private Long prelimafterinv;

	@Column(name = "ppebanknodalca")
	private Long ppeBanknodalCA;

	@Column(name = "ppenodal")
	private Long ppenodal;

	// Interest During Construction Period
	@Column(name = "Interest_Amt_Inv")
	private Long interestAmtInve;

	@Column(name = "Interest_Per_Dpr")
	private Long interestPerDpr;

	@Column(name = "Interest_Bank_Apprai")
	private Long interestBankApprai;

	@Column(name = "Interest_Per_Certi")
	private Long interestPerCerti;

	@Column(name = "Interest_CapInv_CA")
	private Long interestCapInvCA;

	@Column(name = "Interest_CapInv_Valuer")
	private Long interestCapInvValuer;

	@Column(name = "Interest_Statutory_Audit")
	private Long interestStatutoryAudit;

	@Column(name = "interestafterinv")
	private Long interestafterinv;

	@Column(name = "interestbanknodalca")
	private Long interestBanknodalca;

	@Column(name = "interestnodal")
	private Long interestnodal;

	// Margin Money for Working Capital
	@Column(name = "Margin_Amt_Inv")
	private Long marginAmtInve;

	@Column(name = "Margin_Per_Dpr")
	private Long marginPerDpr;

	@Column(name = "Margin_Bank_Apprai")
	private Long marginBankApprai;

	@Column(name = "Margin_Per_Certi")
	private Long marginPerCerti;

	@Column(name = "Margin_CapInv_CA")
	private Long marginCapInvCA;

	@Column(name = "Margin_CapInv_Valuer")
	private Long marginCapInvValuer;

	@Column(name = "Margin_Statutory_Audit")
	private Long marginStatutoryAudit;

	@Column(name = "marginafterinv")
	private Long marginafterinv;

	@Column(name = "marginbanknodalca")
	private Long marginBanknodalca;

	@Column(name = "marginnodal")
	private Long marginnodal;

	// Other, If any
	@Column(name = "Other_Amt_Inv")
	private Long otherAmtInve;

	@Column(name = "Other_Per_Dpr")
	private Long othPerDpr;

	@Column(name = "Other_Bank_Apprai")
	private Long othBankApprai;

	@Column(name = "Other_Per_Certi")
	private Long othPerCerti;

	@Column(name = "Other_CapInv_CA")
	private Long othCapInvCA;

	@Column(name = "Other_CapInv_Valuer")
	private Long otherCapInvValuer;

	@Column(name = "Other_Statutory_Audit")
	private Long otherStatutoryAudit;

	@Column(name = "otherafterinv")
	private Long othafterinv;

	@Column(name = "othbanknodalca")
	private Long othBanknodalca;

	@Column(name = "othnodal")
	private Long othnodal;

	// SubTotal (B) (5+6+7+8+9)
	@Column(name = "SubTtl_B_Amt_Inv")
	private Long subTtlBAmtInv;

	@Column(name = "SubTtl_B_Per_Dpr")
	private Long subTtlBPerDpr;

	@Column(name = "SubTtl_B_Bank_Apprai")
	private Long subTtlBBankApprai;

	@Column(name = "SubTtl_B_Per_Certi")
	private Long subTtlBPerCerti;

	@Column(name = "SubTtl_B_CapInv_CA")
	private Long subTtlBCapInvCA;

	@Column(name = "SubTtl_B_CapInv_Valuer")
	private Long subTtlBCapInvValuer;

	@Column(name = "SubTtl_B_Statutory_Audit")
	private Long subTtlBStatutoryAudit;

	@Column(name = "subttlbcafterinv")
	private Long subTtlBCafterinv;

	@Column(name = "subttlbcapinvca")
	private Long subTtlBCapinvca;

	@Column(name = "subttlbnodal")
	private Long subTtlBnodal;

	// Total (A+B)
	@Column(name = "Ttl_Amt_Inv")
	private Long ttlAmtInve;

	@Column(name = "Ttl_Per_Dpr")
	private Long ttlPerDpr;

	@Column(name = "Ttl_Bank_Apprai")
	private Long TtlBankApprai;

	@Column(name = "Ttl_Per_Certi")
	private Long ttlPerCerti;

	@Column(name = "Ttl_CapInv_CA")
	private Long ttlCapInvCA;

	@Column(name = "Ttl_CapInv_Valuer")
	private Long ttlCapInvValuer;

	@Column(name = "Ttl_Statutory_Audit")
	private Long ttlStatutoryAudit;

	@Column(name = "BreakUp_Cost_Observe")
	private String breakUpCostObserve;

	@Column(name = "ttlafterinv")
	private String ttlafterinv;

	@Column(name = "ttlcapinvca")
	private Long ttlcapinvca;

	@Column(name = "ttlnodal")
	private Long ttlnodal;

//Means Of Financing-------------------------------------------------------

	// Equity Share Capital
	@Column(name = "Equity_Cap_Per_Dpr")
	private Long equityCapPerDpr;

	@Column(name = "Equity_Cap_Bank_Apprai")
	private Long equityCapBankApprai;

	@Column(name = "Equity_Cap_Per_Certi")
	private Long equityCapPerCerti;

	@Column(name = "Equity_Cap_CapInv_CA")
	// @Temporal(TemporalType.DATE)
	private String equityCapCapInvCA;

	@Column(name = "Equ_aft_inv_date")
	private Long equAftinvdate;

	@Column(name = "Equ_aft_inv_proddate")
	private Long equAftinvproddate;

	@Column(name = "Equ_Cap_Statutory_Audit")
	private Long equityCapStatutoryAudit;

	// Internal Cash Accruals
	@Column(name = "Int_Cash_Per_Dpr")
	private Long intCashPerDpr;

	@Column(name = "Int_Cash_Bank_Apprai")
	private Long intCashBankApprai;

	@Column(name = "Int_Cash_Per_Certi")
	private Long intCashPerCerti;

	@Column(name = "Int_Cash_CapInv_CA")
	private Long intCashCapInvCA;

	@Column(name = "Int_Cash_Statutory_Audit")
	private Long intCashStatutoryAudit;

	@Column(name = "Int_Cash_inv_date")
	private Long intCashAftinvdate;

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Column(name = "Int_Cash_inv_proddate")
	@Temporal(TemporalType.DATE)
	private Date intCashAftinvproddate;

	// Interest Free Unsecured Loans & Promoter’s contribution
	@Column(name = "Int_Free_Per_Dpr")
	private Long intFreePerDpr;

	@Column(name = "Int_Free_Bank_Apprai")
	private Long intFreeBankApprai;

	@Column(name = "Int_Free_Per_Certi")
	private Long intFreePerCerti;

	@Column(name = "Int_Free_CapInv_CA")
	private Long intFreeCapInvCA;

	@Column(name = "Int_Free_Statutory_Audit")
	private Long intFreeStatutoryAudit;

	@Column(name = "Int_Free_inv_date")
	private Long intFreeAftinvdate;

	@Column(name = "Int_Free_inv_proddate")
	private Long intFreeAftinvproddate;

	// Other
	@Column(name = " FinOth_Per_Dpr")
	private Long finOthPerDpr;

	@Column(name = "FinOth_Bank_Apprai")
	private Long finOthBankApprai;

	@Column(name = "FinOth_Per_Certi")
	private Long finOthPerCerti;

	@Column(name = "FinOth_CapInv_CA")
	private Long finOthCapInvCA;

	@Column(name = "FinOth_Statutory_Audit")
	private Long finOthStatutoryAudit;

	@Column(name = "FinOth_inv_date")
	private Long finOtheAftinvdate;

	@Column(name = "FinOth_inv_proddate")
	private Long finOthAftinvproddate;

	// Detailed Project Report
	@Column(name = " Sec_Per_Dpr")
	private Long SecPerDpr;

	// Appraisal Note
	@Column(name = "Sec_Bank_Apprai")
	private String SecBankApprai;

	// Verification of Capital Investment
	@Column(name = "Sec_Per_Certi")
	private String SecPerCerti;

	@Column(name = "Sec_CapInv_CA")
	private Long SecCapInvCA;

	@Column(name = "Sec_Statutory_Audit")
	private Long SecStatutoryAudit;

	@Column(name = "Sec_inv_date")
	private Long SeceAftinvdate;

	@Column(name = "Sec_inv_proddate")
	private Long SecAftinvproddate;

	// Advances from Dealers
	@Column(name = " Deal_Per_Dpr")
	private String dealPerDpr;

	@Column(name = "Deal_Bank_Apprai")
	private Long dealBankApprai;

	@Column(name = "Deal_Per_Certi")
	private Long dealPerCerti;

	@Column(name = "Deal_CapInv_CA")
	private Long dealCapInvCA;

	@Column(name = "Deal_Statutory_Audit")
	private Long dealStatutoryAudit;

	@Column(name = "Deal_inv_date")
	private Long dealeAftinvdate;

	@Column(name = "Deal_inv_proddate")
	private Long dealAftinvproddate;

	// From FI’s
	@Column(name = "FromFi_Per_Dpr")
	private Long FromFiPerDpr;

	@Column(name = "FromFi_Bank_Apprai")
	private Long FromFiBankApprai;

	@Column(name = "FromFi_Per_Certi")
	private Long FromFiPerCerti;

	@Column(name = "FromFi_CapInv_CA")
	private Long FromFiCapInvCA;

	@Column(name = "FromFi_Statutory_Audit")
	private Long FromFiStatutoryAudit;

	@Column(name = "FromFi_inv_date")
	private Long FromFieAftinvdate;

	@Column(name = "FromFi_inv_proddate")
	private Long FromFiAftinvproddate;

	// From Bank
	@Column(name = "FrBank_Per_Dpr")
	private Long FrBankPerDpr;

	@Column(name = "FrBank_Bank_Apprai")
	private Long FrBankBankApprai;

	@Column(name = "FromFi_Bank_Per_Certi")
	private Long FrBankPerCerti;

	@Column(name = "FrBank_CapInv_CA")
	private Long FrBankCapInvCA;

	@Column(name = "FrBank_Statutory_Audit")
	private Long FrBankStatutoryAudit;

	@Column(name = "FrBank_inv_date")
	private Long frBankeAftinvdate;

	@Column(name = "FrBank_inv_proddate")
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
	@Column(name = "Plant_Mach_Per_Dpr")
	private Long PlantMachPerDpr;

	@Column(name = "Plant_Mach_Bank_Apprai")
	private Long PlantMachBankApprai;

	@Column(name = "Plant_Mach_Per_Certi")
	private Long Plant_MachPerCerti;

	@Column(name = "Plant_Mach_CapInv_CA")
	private Long PlantMachCapInvCA;

	@Column(name = "Plant_Mach_Statutory_Audit")
	private Long PlantMachStatutoryAudit;

	@Column(name = "Plant_Mach_inv_date")
	private Long Plant_MacheAftinvdate;

	@Column(name = "Plant_Machl_inv_proddate")
	private Long Plant_MachAftinvproddate;

	// Total
	@Column(name = "FinTttl_Mach_Per_Dpr")
	private Long finTttlPerDpr;

	@Column(name = "FinTttl_Bank_Apprai")
	private Long finTttlBankApprai;

	@Column(name = "FinTttl_Per_Certi")
	private Long finTttlPerCerti;

	@Column(name = "FinTttl_CapInv_CA")
	private Long finTttlCapInvCA;

	@Column(name = "FinTttl_Statutory_Audit")
	private Long finTttlStatutoryAudit;

	@Column(name = "Financing_Observe")
	private String financingObserve;

	@Column(name = "FinTttl_date")
	private Long finTttldate;

	@Column(name = "FinTttl_proddate")
	private Long finTttlproddate;

	// Details of Incentive Claimed (As per
	// LoC)-----------------------------------------------

	@Column(name = "Sgst_Reim_Inc_Amt")
	private Long ISF_Claim_Reim;

	@Column(name = "Sgst_Reim_Picup_Remark")
	private String sgstRemark;

	@Column(name = "Add_SCST_Inc_Amt")
	private Long ISF_Reim_SCST;

	@Column(name = "Add_SCST_Picup_Remark")
	private String scstRemark;

	@Column(name = "Add_Female_Inc_Amt")
	private Long ISF_Reim_FW;

	@Column(name = "Add_Female_Picup_Remark")
	private String fwRemark;

	@Column(name = "Add_BPL_Inc_Amt")
	private Long ISF_Reim_BPLW;

	@Column(name = "Add_BPL_Picup_Remark")
	private String bplRemark;

	@Column(name = "Ttl_Inc_Amt")
	private Long ttlIncAmt;

	// New Incentive
	@Column(name = "StampDuty_Exe_Inc_Amt_Ex")
	private Long ISF_Stamp_Duty_EX;

	@Column(name = "StampDuty_Exe_Remark")
	private String stampDutyExemptRemark;

	@Column(name = "StampDuty_Reim_Inc_Amt")
	private Long ISF_Amt_Stamp_Duty_Reim;

	@Column(name = "StampDuty_Reim_Remark")
	private String stampDutyReimRemark;

	@Column(name = "AddSCST_Prmtr_Inc_Amt")
	private Long ISF_Additonal_Stamp_Duty_EX;

	@Column(name = "AddSCST_Prmtr_Remark")
	private String divyangSCSTRemark;

	@Column(name = "Epf_Reim_Inc_Amt")
	private Long ISF_Epf_Reim_UW;

	@Column(name = "Epf_Reim_Remark")
	private String epfUnsklRemark;

	@Column(name = "AddEpf_Reim_Inc_Amt")
	private Long ISF_Add_Epf_Reim_SkUkW;

	@Column(name = "AddEpf_Reim_Remark")
	private String epfSklUnsklRemark;

	@Column(name = "Add10_Promoter_Inc_Amt")
	private Long ISF_Add_Epf_Reim_DIVSCSTF;

	@Column(name = "Add10_Promoter_Remark")
	private String epfDvngSCSTRemark;

	@Column(name = "CapInt_Subs_Inc_Amt")
	private Long ISF_Cis;

	@Column(name = "Cap_Int_Subs_Remark")
	private String capIntSubRemark;

	@Column(name = "AddCap_Int_Inc_Amt")
	private Long ISF_ACI_Subsidy_Indus;

	@Column(name = "Add_Cap_Int_Remark")
	private String aciSubsidyRemark;

	@Column(name = "Infra_Int_Subs_Inc_Amt")
	private Long ISF_Infra_Int_Subsidy;

	@Column(name = "Infra_Int_Subs_Remark")
	private String infraIntSubRemark;

	@Column(name = "AddInfra_Int_Inc_Amt")
	private Long ISF_AII_Subsidy_DIVSCSTF;

	@Column(name = "Add_Infra_Inte_Remark")
	private String aiiSubsidyRemark;

	@Column(name = "ISF_Loan_Subsidy")
	private Long ISF_Loan_Subsidy;

	@Column(name = "Add_Loan_Remark")
	private String loanIntSubRemark;

	@Column(name = "ISF_TaxCredit_Reim")
	private Long ISF_Tax_Credit_Reim;

	@Column(name = "input_Tax_Remark")
	private String inputTaxRemark;

	@Column(name = "Isf_EX_E_Duty")
	private Long ISF_EX_E_Duty;

	@Column(name = "elec_Duty_Cap_Remark")
	private String elecDutyCapRemark;

	@Column(name = "Isf_EX_E_Duty_PC")
	private Long ISF_EX_E_Duty_PC;

	@Column(name = "elec_Duty_Drawn_Remark")
	private String elecDutyDrawnRemark;

	@Column(name = "Isf_EX_Mandee_Duty")
	private Long ISF_EX_Mandee_Fee;

	@Column(name = "mandi_Fee_Remark")
	private String mandiFeeRemark;

	@Column(name = "Isf_IndusPayroll_Asst")
	private Long ISF_Indus_Payroll_Asst;

	@Column(name = "diffAble_Work_Remark")
	private String diffAbleWorkRemark;

	// Turnover of Base
	// Production-------------------------------------------------------
	// 1
	
	

	private transient String[] sgstFinYr;
	

	private transient String[] sgstTurnOverSalesItems;
	
	

	private transient String[] sgstTurnOverBaseProduction;
	
	
	
	@Column(name = "Fin_Yr1")
	private String finYr1;

	@Column(name = "Turnover_Of_Sales1")
	private Long turnoverOfSales1;

	@Column(name = "Turnover_Production1")
	private Long turnoverProduction1;
	// 2
	@Column(name = "Fin_Yr2")
	private String finYr2;

	@Column(name = "Turnover_Of_Sales2")
	private Long turnoverOfSales2;

	@Column(name = "Turnover_Production2")
	private Long turnoverProduction2;

	// 3
	@Column(name = "Fin_Yr3")
	private String finYr3;

	@Column(name = "Turnover_Of_Sales3")
	private Long turnoverOfSales3;

	@Column(name = "Turnover_Production3")
	private Long turnoverProduction3;

	// 4
	@Column(name = "Fin_Yr4")
	private String finYr4;

	@Column(name = "Turnover_Of_Sales4")
	private Long turnoverOfSales4;

	@Column(name = "Turnover_Production4")
	private Long turnoverProduction4;

//5
	@Column(name = "Fin_Yr5")
	private String finYr5;

	@Column(name = "Turnover_Of_Sales5")
	private Long turnoverOfSales5;

	@Column(name = "Turnover_Production5")
	private Long turnoverProduction5;

	@Column(name = "TurnOver_Observe")
	private String turnOverObserve;
	
	@Column(name = "TurnOver_Base_Prod_Observe")
	private String turnOverBaseProdObserve;

	// SGST- Amount of new
	@Column(name = "Duration_Fin_Yr1_New")
	private String durationFinYr1New;
	
	@Column(name = "Duration_From1_New")
	private String durationFrom1New;

	@Column(name = "Duration_To1_New")
	private String durationTo1New;
	
	@Column(name = "Amt_Of_Net_SGST1_New")
	private String amtOfNetSGST1New;

	@Column(name = "AmtOf_Net_SGST_Reim1_New")
	private  transient String[] amtOfNetSGSTReim1New;
	
	@Column(name = "Payable_Amt_Of_SGST_new")
	private transient String[] payableAmountOfSGSTNew;
	
	
	@Column(name = "Ttl_Deposit_SGST_new")
	private String ttlDepositSGST;
	
	@Column(name = "Ttl_Claim_SGST_new")
	private String ttlClaimSGST;
	
	@Column(name = "Ttl_Payable_SGST_new")
	private String ttlPayableSGST;
	
	

	@Column(name = "Duration_Fin_Yr2_New")
	private String durationFinYr2New;

	@Column(name = "Amt_Of_Net_SGST2_New")
	private Long amtOfNetSGST2New;

	@Column(name = "AmtOf_Net_SGST_Reim2_New")
	private double amtOfNetSGSTReim2New;

	@Column(name = "Duration_Fin_Yr3_New")
	private String durationFinYr3New;

	@Column(name = "Amt_Of_Net_SGST3_New")
	private Long amtOfNetSGST3New;

	@Column(name = "AmtOf_Net_SGST_Reim3_New")
	private double amtOfNetSGSTReim3New;

	@Column(name = "Duration_Fin_Yr4_New")
	private String durationFinYr4New;

	@Column(name = "Amt_Of_Net_SGST4_New")
	private Long amtOfNetSGST4New;

	@Column(name = "AmtOf_Net_SGST_Reim4_New")
	private double amtOfNetSGSTReim4New;

	// SGST- Amount of
	// Reimbursement----------------------------------------------------------------
	// 1
	
	private transient String durationFinYr;

	private transient String turnoverOfProduction;
	
	private transient String financialYrPeriod;

	private transient String turnOverPartFullYrDivers;
	
	private transient String increTurnover;
	
	private transient String ttlNetSGSTPaidFinYr;
	
	private transient String ttlNetSGSTPaidIncreTurnOver;
	
	private transient String amtNetSGSTReimCliam;

	private transient String amtOfNetSGSTReimEligibility;

	
	
	@Column(name = "Ttl_Turn_Over_Part_Divers")
	private Long ttlTurnOverPartDivers;
	
	@Column(name = "Ttl_Incre_Turnover1")
	private Long ttlIncreTurnover1;
	

	@Column(name = "Ttl_Amt_Comm_Tax1_Ttl")
	private Long ttlAmtCommTax1Ttl;
	
	@Column(name = "Ttl_Incre_Net_SGST1")
	private Long ttlIncreNetSGST1;
	
	@Column(name = "Ttl_Amt_Of_Net_SGST1")
	private Long ttlAmtOfNetSGST1;
	
	@Column(name = "Ttl_AmtOf_Net_SGST_Reim1")
	private Long ttlAmtOfNetSGSTReim1;
	
	

	
	


	// 2
	@Column(name = "Duration_Fin_Yr2")
	private String durationFinYr2;

	@Column(name = "Turnover_Of_Production2")
	private Long turnoverOfProduction2;

	@Column(name = "Ttl_Amt_Comm_Tax2")
	private Long ttlAmtCommTax2;

	@Column(name = "Amt_Of_Net_SGST2")
	private Long amtOfNetSGST2;

	@Column(name = "Incre_Turnover2")
	private Long increTurnover2;

	@Column(name = "Incre_Net_SGST2")
	private Long increNetSGST2;

	@Column(name = "AmtOf_Net_SGST_Reim2")
	private Long amtOfNetSGSTReim2;
	// 3
	@Column(name = "Duration_Fin_Yr3")
	private String durationFinYr3;

	@Column(name = "Turnover_Of_Production3")
	private Long turnoverOfProduction3;

	@Column(name = "Ttl_Amt_Comm_Tax3")
	private Long ttlAmtCommTax3;

	@Column(name = "Amt_Of_Net_SGST3")
	private Long amtOfNetSGST3;

	@Column(name = "Incre_Turnover3")
	private Long increTurnover3;

	@Column(name = "Incre_Net_SGST3")
	private Long increNetSGST3;

	@Column(name = "AmtOf_Net_SGST_Reim3")
	private Long amtOfNetSGSTReim3;
	// 4
	@Column(name = "Duration_Fin_Yr4")
	private String durationFinYr4;

	@Column(name = "Turnover_Of_Production4")
	private Long turnoverOfProduction4;

	@Column(name = "Ttl_Amt_Comm_Tax4")
	private Long ttlAmtCommTax4;

	@Column(name = "Amt_Of_Net_SGST4")
	private Long amtOfNetSGST4;

	@Column(name = "Incre_Turnover4")
	private Long increTurnover4;

	@Column(name = "Incre_Net_SGST4")
	private Long increNetSGST4;

	@Column(name = "AmtOf_Net_SGST_Reim4")
	private Long amtOfNetSGSTReim4;
	// 5
	@Column(name = "Duration_Fin_Yr5")
	private String durationFinYr5;

	@Column(name = "Turnover_Of_Production5")
	private Long turnoverOfProduction5;

	@Column(name = "Ttl_Amt_Comm_Tax5")
	private Long ttlAmtCommTax5;

	@Column(name = "Amt_Of_Net_SGST5")
	private Long amtOfNetSGST5;

	@Column(name = "Incre_Turnover5")
	private Long increTurnover5;

	@Column(name = "Incre_Net_SGST5")
	private Long increNetSGST5;

	@Column(name = "AmtOf_Net_SGST_Reim5")
	private Long amtOfNetSGSTReim5;

	@Column(name = "Sgst_Amt_Reim_Observe")
	private String sgstAmtReimObserve;

	@Column(name = "Admissible_Benefits")
	private String admissibleBenefits;

	// CIS Table Observ
	@Column(name = "CIS_Table_Observe")
	private String cisTblObserve;

	// Eligibility of
	// Benefits-------------------------------------------------------------------
	// STATUS OF COMPLIANCE OF ELIGIBILITY CRITERIA FOR DISBURSEMENT

	// 1
	@Column(name = "Iem_Status_Compl")
	private String iemStatusCompl;

	@Column(name = "Iem_Status_Observe")
	private String iemStatusObserve;

	// 2
	@Column(name = "New_Exp_Div_Compl")
	private String newExpDivCompl;

	@Column(name = "New_Exp_Div_Observe")
	private String newExpDivObserve;

	// 3
	@Column(name = "Pro_Ttl_Inv_Compl")
	private String proTtlInvCompl;

	@Column(name = "Pro_Ttl_Inv_Observe")
	private String proTtlInvObserve;

	// 4
	@Column(name = "CutOff_Date_Compl")
	private String CutOffDateCompl;

	

	@Column(name = "CutOff_Date_Observe")
	private String cutOffDateObserve;

	// 5
	@Column(name = "Date_Comm_Pro_Compl")
	private String dateCommProCompl;
	@Column(name = "Date_Comm_Pro_Observe")
	private String dateCommProObserve;

	// 6
	@Column(name = "Inv_Period_Compl")
	private String invPeriodCompl;
	@Column(name = "Inv_Period_Observe")
	private String invPeriodObserve;

	// 7
	@Column(name = "Proj_Phases_Compl")
	private String projPhasesCompl;
	@Column(name = "Proj_Phases_Observe")
	private String projPhasesObserve;

	// 8
	@Column(name = "Dir_Indir_Workers_Compl")
	private String dirIndirWorkersCompl;
	@Column(name = "Dir_Indir_Workers_Observe")
	private String dirIndirWorkersObserve;

	// 9
	@Column(name = "Dpr_Compl")
	private String dprCompl;
	@Column(name = "Dpr_Observe")
	private String dprObserve;

	// 10
	@Column(name = "List_Of_Assets_Compl")
	private String listOfAssetsCompl;
	@Column(name = "List_Of_Assets_Observe")
	private String listOfAssetsObserve;

	// 11
	@Column(name = "Undertaking_Compl")
	private String undertakingCompl;
	@Column(name = "Undertaking_Observe")
	private String undertakingObserve;

	// 12
	@Column(name = "Auth_Sign_Compl")
	private String authSignCompl;
	@Column(name = "Auth_Sign_Observe")
	private String authSignObserve;

	// 13
	@Column(name = "Appl_Format_Compl")
	private String applFormatCompl;
	@Column(name = "Appl_Format_Observe")
	private String applFormatObserve;

	// 14
	@Column(name = "Supp_Doc_Dir_Compl")
	private String suppDocDirCompl;
	@Column(name = "Supp_Doc_Dir_Observe")
	private String suppDocDirObserve;

	// 16
	@Column(name = "Bank_Appr_Compl")
	private String bankApprCompl;
	@Column(name = "Bank_Appr_Observe")
	private String bankApprObserve;

	@Column(name = "Elig_Of_Benefits_Note")
	private String eligOfBenefitsNote;

	@Column(name = "Eelig_Of_Benefits_Comments")
	private String eligOfBenefitsComments;

// Quantum of Benefits-------------------------------------------------------------

	@Column(name = "Ttl_Elig_Amt")
	private Long ttlEligAmt;

	@Column(name = "Prop_Disb_Amt")
	private Long propDisbAmt;

	@Column(name = "Elig_Benefits_Amt")
	private Long eligBenefitsAmt;

	@Column(name = "Balance_Period_Amt")
	private Long balancePeriodAmt;

// Disbursement effected till date-------------------------------------------------------		

	@Column(name = "Disb_Eff_Date")
	private String disbEffDate;

	@Column(name = "Disb_Eff_Comments")
	private String disbEffComments;

// Proposal for Consideration------------------------------------------------------------------			

	@Column(name = "Ttl_Elig_Incentives")
	private String ttlEligIncentives;

	@Column(name = "DateAdmissibilityInc")
	private String dateAdmissibilityInc;

// Compliance of Conditions---------------------------------------

	@Column(name = "Compl_Condi_Comments")
	private String complCondiComments;

//04-02-2021	create New Column

	@Column(name = "Whether_ProdSetupPhs")
	private String wheProdSetupPhs;

	@Column(name = "Whether_ProdSetupPhsObserv")
	private String wheProdSetupPhsObserv;

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Column(name = "Opted_CutofDate")
	@Temporal(TemporalType.DATE)
	private Date optCutofDate;

	// CIS- Computation Methodology & Amount Cost of Project
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Temporal(TemporalType.DATE)
	@Column(name = "Dateof_FirstLoanCIS")
	private Date doFirstLoanCIS;

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Temporal(TemporalType.DATE)
	@Column(name = "Dateof_LastLoanCIS")
	private Date doLastLoanCIS;

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Temporal(TemporalType.DATE)
	@Column(name = "Dateof_FirstDisCIS")
	private Date doFirstDisCIS;

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Temporal(TemporalType.DATE)
	@Column(name = "Dateof_LastDisCIS")
	private Date doLastDisCIS;

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Temporal(TemporalType.DATE)
	@Column(name = "Dateof_PaymentCIS")
	private Date doPaymentCIS;

	///// IIS Comp...

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Temporal(TemporalType.DATE)
	@Column(name = "Dateof_FirstLoanIIS")
	private Date doFirstLoanIIS;

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Temporal(TemporalType.DATE)
	@Column(name = "Dateof_LastLoanIIS")
	private Date doLastLoanIIS;

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Temporal(TemporalType.DATE)
	@Column(name = "Dateof_FirstDisIIS")
	private Date doFirstDisIIS;

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Temporal(TemporalType.DATE)
	@Column(name = "Dateof_LastDisIIS")
	private Date doLastDisIIS;

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Temporal(TemporalType.DATE)
	@Column(name = "Dateof_PaymentIIS")
	private Date doPaymentIIS;
/////

	@Column(name = "Cis_CostOf_Project_Amt")
	private Long cisCostOfProjectAmt;

	@Column(name = "Cis_Plant_Mach_Amt")
	private Long cisPlantMachAmt;

	@Column(name = "Cis_Entire_Project_Amt")
	private Long cisEntireProjectAmt;

	@Column(name = "Cis_Term_Plant_Mach_Amt")
	private Double cisTermPlantMachAmt;

	@Column(name = "Cis_Appl_ROI_Amt")
	private float cisApplROIAmt;

	@Column(name = "Cis_Int_Paid_Amt")
	private Long cisIntPaidAmt;

	@Column(name = "Cis_ROI_PM_Amt")
	private String cisRoiPMAmt;

	@Column(name = "Cis_Int_PM5_Amt")
	private Double cisIntPM5Amt;

	@Column(name = "Cis_Observe")
	private String cisObserve;

// 05/05/2021

	@Column(name = "Confrm_Provby_CTD")
	private String confProvbyCTD;

	@Column(name = "Confrm_Provby_Bank")
	private String confProvbyBank;

	@Column(name = "External_ERD")
	private String externalERD;

	// transiant value for mongo
	private transient String confProvbyCTDDoc;
	private transient String confProvbyBankDoc;
	private transient String externalERDDoc;

	private transient String confProvbyCTDDocBase64;
	private transient String confProvbyBankDocBase64;
	private transient String externalERDDocBase64;

	/// IIS Model Start
	@Column(name = "ClaimSctnTbleIISObserv")
	private String clamSactniisObserv;

	public Long getLandafterinv() {
		return landafterinv;
	}

	public void setLandafterinv(Long landafterinv) {
		this.landafterinv = landafterinv;
	}

	public Long getBuildafterinv() {
		return buildafterinv;
	}

	public void setBuildafterinv(Long buildafterinv) {
		this.buildafterinv = buildafterinv;
	}

	public Long getPlantdafterinv() {
		return plantdafterinv;
	}

	public void setPlantdafterinv(Long plantdafterinv) {
		this.plantdafterinv = plantdafterinv;
	}

	public Long getMiscafterinv() {
		return miscafterinv;
	}

	public void setMiscafterinv(Long miscafterinv) {
		this.miscafterinv = miscafterinv;
	}

	public Long getSubTtlAafterinv() {
		return subTtlAafterinv;
	}

	public void setSubTtlAafterinv(Long subTtlAafterinv) {
		this.subTtlAafterinv = subTtlAafterinv;
	}

	public Long getProvisionafterinv() {
		return provisionafterinv;
	}

	public void setProvisionafterinv(Long provisionafterinv) {
		this.provisionafterinv = provisionafterinv;
	}

	public Long getInterestafterinv() {
		return interestafterinv;
	}

	public void setInterestafterinv(Long interestafterinv) {
		this.interestafterinv = interestafterinv;
	}

	public Long getMarginafterinv() {
		return marginafterinv;
	}

	public void setMarginafterinv(Long marginafterinv) {
		this.marginafterinv = marginafterinv;
	}

	public Long getSubTtlBCafterinv() {
		return subTtlBCafterinv;
	}

	public void setSubTtlBCafterinv(Long subTtlBCafterinv) {
		this.subTtlBCafterinv = subTtlBCafterinv;
	}

	public String getTtlafterinv() {
		return ttlafterinv;
	}

	public void setTtlafterinv(String ttlafterinv) {
		this.ttlafterinv = ttlafterinv;
	}


	@Column(name = "IIS_CMAmtObserv")
	private String iisCMAmtObserv;

	// Stamp Duty Exemption or Reimbursement Computation
	// 15/02/2021

	
	private transient String computationFinYr;
	
	
	private transient String stampDutyDateFrom;
	
	
	private transient String stampDutyDateTo;
	
	private transient String claimStampDutyReimAmt;

	
	private transient String[] stampDutyReimElig;

	
	@Column(name = "Ttl_Claim_Stamp_Duty_Reim_Amt")
	private  String ttlClaimStampDutyReimAmt;

	@Column(name = "Ttl_Stamp_Duty_Reim_Elig")
	private String ttlStampDutyReimElig;

	

	@Column(name = "Stamp_Duty_Exe_Observ")
	private String stampDutyExeObserv;

	// Reimbursement of Disallowed Input Tax // 15/02/2021
	
	private transient String disallowedFinYr;

	private transient String disallowedDateFr;
	
	
	private transient String disallowedDateTo;
	
	
	private transient String disallowedClaimAmt;

	
	private transient String[] disallowedEligAmt;

	
	public String getDisallowedDateFr() {
		return disallowedDateFr;
	}

	public void setDisallowedDateFr(String disallowedDateFr) {
		this.disallowedDateFr = disallowedDateFr;
	}

	public String getDisallowedDateTo() {
		return disallowedDateTo;
	}

	public void setDisallowedDateTo(String disallowedDateTo) {
		this.disallowedDateTo = disallowedDateTo;
	}

	public String getDisallowedClaimAmt() {
		return disallowedClaimAmt;
	}

	public void setDisallowedClaimAmt(String disallowedClaimAmt) {
		this.disallowedClaimAmt = disallowedClaimAmt;
	}

	

	public String[] getDisallowedEligAmt() {
		return disallowedEligAmt;
	}

	public void setDisallowedEligAmt(String[] disallowedEligAmt) {
		this.disallowedEligAmt = disallowedEligAmt;
	}

	@Column(name = "Ttl_Disallowed_Claim_Amt")
	private String ttlDisallowedClaimAmt;

	

	@Column(name = "TtlReim_Disallow_Avail_Amt")
	private String ttlReimDisallowAvailAmt;

	@Column(name = "Reim_Disallow_Observ")
	private String reimDisallowObserv;

	@Column(name = "actaulDate7")
	private String actaulDate7;

	@Column(name = "cutofDate8")
	private String cutofDate8;

	@Column(name = "nodalCutofDate9")
	private String nodalCutofDate9;

	@Column(name = "indusUnit17")
	private String indusUnit17;

	@Column(name = "table2remark1")
	private String table2remark1;

	@Column(name = "table2remark2")
	private String table2remark2;

	@Column(name = "table2remark3")
	private String table2remark3;

	@Column(name = "table2remark4")
	private String table2remark4;

	@Column(name = "table2remark5")
	private String table2remark5;

	@Column(name = "table2remark6")
	private String table2remark6;

	@Column(name = "table2remark7")
	private String table2remark7;

	@Column(name = "table2remark8")
	private String table2remark8;

	@Column(name = "table2remark9")
	private String table2remark9;

	@Column(name = "table2remark10")
	private String table2remark10;

	@Column(name = "table2remark11")
	private String table2remark11;

	@Column(name = "table2remark11A")
	private String table2remark11A;

	@Column(name = "table2remark11B")
	private String table2remark11B;

	@Column(name = "table2remark11C")
	private String table2remark11C;

	@Column(name = "table2remark12")
	private String table2remark12;

	@Column(name = "table2remark13")
	private String table2remark13;

	@Column(name = "table2remark14")
	private String table2remark14;

	@Column(name = "table2remark15")
	private String table2remark15;

	@Column(name = "table2remark16")
	private String table2remark16;

	@Column(name = "table2remark17")
	private String table2remark17;

	@Column(name = "table2remark18")
	private String table2remark18;

	@Column(name = "table2remark19")
	private String table2remark19;

	@Column(name = "table2remark20")
	private String table2remark20;

	@Column(name = "table2remark21")
	private String table2remark21;

	@Column(name = "table2remark22")
	private String table2remark22;

	@Column(name = "eligidisallowavailamt")
	private String eligiDisallowAvailAmt;

	@Column(name = "ttleligidisallowavailamt")
	private String ttleligiDisallowAvailAmt;

	@Column(name = "sactiondate")
	private String sactiondate;

	public String[] getActDtCommProd() {
		return actDtCommProd;
	}

	public void setActDtCommProd(String[] actDtCommProd) {
		this.actDtCommProd = actDtCommProd;
	}

	private transient String[] phwActualdateEval;

	// Bank Loan Details
	private transient String[] loanBankName;
	private transient String[] loanBankDate;
	private transient String[] loanBankAmt;
	private transient String[] loanBankROI;

	private transient String[] pandMBankName;
	private transient String[] pandMBankDate;
	private transient String[] pandMBankAmt;

	public String[] getLoanBankROI() {
		return loanBankROI;
	}

	public void setLoanBankROI(String[] loanBankROI) {
		this.loanBankROI = loanBankROI;
	}

	public String[] getPandMBankName() {
		return pandMBankName;
	}

	public void setPandMBankName(String[] pandMBankName) {
		this.pandMBankName = pandMBankName;
	}

	public String[] getPandMBankDate() {
		return pandMBankDate;
	}

	public void setPandMBankDate(String[] pandMBankDate) {
		this.pandMBankDate = pandMBankDate;
	}

	public String[] getPandMBankAmt() {
		return pandMBankAmt;
	}

	public void setPandMBankAmt(String[] pandMBankAmt) {
		this.pandMBankAmt = pandMBankAmt;
	}

	public String[] getLoanBankName() {
		return loanBankName;
	}

	public void setLoanBankName(String[] loanBankName) {
		this.loanBankName = loanBankName;
	}

	public String[] getLoanBankDate() {
		return loanBankDate;
	}

	public void setLoanBankDate(String[] loanBankDate) {
		this.loanBankDate = loanBankDate;
	}

	public String[] getLoanBankAmt() {
		return loanBankAmt;
	}

	public void setLoanBankAmt(String[] loanBankAmt) {
		this.loanBankAmt = loanBankAmt;
	}

	public String[] getPhwActualdateEval() {
		return phwActualdateEval;
	}

	public void setPhwActualdateEval(String[] phwActualdateEval) {
		this.phwActualdateEval = phwActualdateEval;
	}

	private transient String[] expEligbleCapInv;

	private transient String[] expPercOverGrsBlck;

	public String[] getExpEligbleCapInv() {
		return expEligbleCapInv;
	}

	public void setExpEligbleCapInv(String[] expEligbleCapInv) {
		this.expEligbleCapInv = expEligbleCapInv;
	}

	public String[] getExpPercOverGrsBlck() {
		return expPercOverGrsBlck;
	}

	public void setExpPercOverGrsBlck(String[] expPercOverGrsBlck) {
		this.expPercOverGrsBlck = expPercOverGrsBlck;
	}

	// @Column(name="Act_Date_Comm_Prod")
	private transient String[] actDtCommProd;

	

	public String getTtlReimDisallowAvailAmt() {
		return ttlReimDisallowAvailAmt;
	}

	public void setTtlReimDisallowAvailAmt(String ttlReimDisallowAvailAmt) {
		this.ttlReimDisallowAvailAmt = ttlReimDisallowAvailAmt;
	}

	public String getReimDisallowObserv() {
		return reimDisallowObserv;
	}

	public void setReimDisallowObserv(String reimDisallowObserv) {
		this.reimDisallowObserv = reimDisallowObserv;
	}

	

	

	/*
	 * public String getTtlAdmissibleStampDuty() { return ttlAdmissibleStampDuty; }
	 * 
	 * public void setTtlAdmissibleStampDuty(String ttlAdmissibleStampDuty) {
	 * this.ttlAdmissibleStampDuty = ttlAdmissibleStampDuty; }
	 */

	public String getClaimStampDutyReimAmt() {
		return claimStampDutyReimAmt;
	}

	public String getTtlClaimStampDutyReimAmt() {
		return ttlClaimStampDutyReimAmt;
	}

	public void setTtlClaimStampDutyReimAmt(String ttlClaimStampDutyReimAmt) {
		this.ttlClaimStampDutyReimAmt = ttlClaimStampDutyReimAmt;
	}

	public String getTtlStampDutyReimElig() {
		return ttlStampDutyReimElig;
	}

	public void setTtlStampDutyReimElig(String ttlStampDutyReimElig) {
		this.ttlStampDutyReimElig = ttlStampDutyReimElig;
	}
	/*
	 * public String getTtlStampDutyReimAvail() { return ttlStampDutyReimAvail; }
	 * 
	 * public void setTtlStampDutyReimAvail(String ttlStampDutyReimAvail) {
	 * this.ttlStampDutyReimAvail = ttlStampDutyReimAvail; }
	 */

	public String getStampDutyExeObserv() {
		return stampDutyExeObserv;
	}

	public void setStampDutyExeObserv(String stampDutyExeObserv) {
		this.stampDutyExeObserv = stampDutyExeObserv;
	}

	// transient value for Eligible Amt CIS Table
	private transient String fYI;
	private transient String intPMI;
	private transient String dateofDISI;
	private transient String actAmtIPI;
	private transient String dateofPI;
	private transient String propIntRoiI;
	private transient String propIntPAI;

	// II Row
	private transient String fYII;
	private transient String intPMII;
	private transient String dateofDISII;
	private transient String actAmtIPII;
	private transient String dateofPII;
	private transient String propIntRoiII;
	private transient String propIntPAII;

	// III Row
	private transient String fYIII;
	private transient String intPMIII;
	private transient String dateofDISIII;
	private transient String actAmtIPIII;
	private transient String dateofPIII;
	private transient String propIntRoiIII;
	private transient String propIntPAIII;

	// IV Row
	private transient String fYIV;
	private transient String intPMIV;
	private transient String dateofDISIV;
	private transient String actAmtIPIV;
	private transient String dateofPIV;
	private transient String propIntRoiIV;
	private transient String propIntPAIV;

	// V Row
	private transient String fYV;
	private transient String intPMV;
	private transient String dateofDISV;
	private transient String actAmtIPV;
	private transient String dateofPV;
	private transient String propIntRoiV;
	private transient String propIntPAV;

	private transient String ElgAmtcisObserv;
	// End CIS Eligible Amt Table

	// START Eligible AMT IIS

	// I Row
	private transient String iisFinYr1;
	private transient String iisTtlLoanAmt1;
	private transient String iisDateOfDisb1;
	private transient String iisActAmtIP1;
	private transient String dateOfPay1;
	private transient String propIntInfra1;
	private transient String propIntInfraPA1;
	private transient String eligibleIIS1;

	// II Row
	private transient String iisFinYr2;
	private transient String iisTtlLoanAmt2;
	private transient String iisDateOfDisb2;
	private transient String iisActAmtIP2;
	private transient String dateOfPay2;
	private transient String propIntInfra2;
	private transient String propIntInfraPA2;
	private transient String eligibleIIS2;

	// III Row
	private transient String iisFinYr3;
	private transient String iisTtlLoanAmt3;
	private transient String iisDateOfDisb3;
	private transient String iisActAmtIP3;
	private transient String dateOfPay3;
	private transient String propIntInfra3;
	private transient String propIntInfraPA3;
	private transient String eligibleIIS3;

	// IV Row
	private transient String iisFinYr4;
	private transient String iisTtlLoanAmt4;
	private transient String iisDateOfDisb4;
	private transient String iisActAmtIP4;
	private transient String dateOfPay4;
	private transient String propIntInfra4;
	private transient String propIntInfraPA4;
	private transient String eligibleIIS4;

	// V Row
	private transient String iisFinYr5;
	private transient String iisTtlLoanAmt5;
	private transient String iisDateOfDisb5;
	private transient String iisActAmtIP5;
	private transient String dateOfPay5;
	private transient String propIntInfra5;
	private transient String propIntInfraPA5;
	private transient String eligibleIIS5;

	private transient String eligAmtIisObserv;

	// End Eligible Amt IIS
	
	
	
	private transient String epfComputFinYr;
	
	private transient String dateFrom;

	private transient String dateTo;
	
	private transient String employerContributionEPF;

	private transient String[] reimEligibility;
	
	@Column(name = "Ttl_Emp_Contrib_EPF")
    private String ttlEmployerContributionEPF;
	
    @Column(name = "Ttl_Reim_Elig")
	private String ttlReimEligibility;
    
    @Column(name = "EPF_Observe")
  	private String epfObserve;

	// End EPF Computation and Eligibility

	public String getTtlEmployerContributionEPF() {
		return ttlEmployerContributionEPF;
	}

	public void setTtlEmployerContributionEPF(String ttlEmployerContributionEPF) {
		this.ttlEmployerContributionEPF = ttlEmployerContributionEPF;
	}

	public String getTtlReimEligibility() {
		return ttlReimEligibility;
	}

	public void setTtlReimEligibility(String ttlReimEligibility) {
		this.ttlReimEligibility = ttlReimEligibility;
	}

	// Start Mandi Fee Exe.
	private transient String mandiFeeExeFinYr1;
	private transient String mandiFeeDateFrom1;
	private transient String mandiFeeDateTo1;
	private transient String claimMandiFeeExe1;
	private transient String[] availMandiFeeExe1;

	

	@Column(name="Ttl_Claim_Mandi_Fee_Exe")
	private String ttlClaimMandiFeeExe;
	
	@Column(name="Ttl_Avail_Mandi_Fee_Exe")
	private String ttlAvailMandiFeeExe;
	
	@Column(name="Mandi_Fee_Exe_Observ")
	private String mandiFeeExeObserv;

	// End Mandi Fee Exe.

	// Start Electricity Duty Exemption
	

	private transient String electricityDutyExeFinYr;

	
	private transient String electricityDateFrom;

	
	private transient String electricityDateTo;

	
	private transient String electricityAmtClaim;


	private transient String[] electricityEligAmt;
	
	private transient String[] electricityEligAmt2;
	
	    public String[] getElectricityEligAmt2() {
		return electricityEligAmt2;
	}

	public void setElectricityEligAmt2(String[] electricityEligAmt2) {
		this.electricityEligAmt2 = electricityEligAmt2;
	}

		@Column(name = "Ttl_Electricity_Amt_Claim")
		private String ttlElectricityAmtClaim;
	 
	    @Column(name = "Ttl_Electricity_Elig_Amt")
		private String ttlElectricityEligAmt;
	    
		// electricityObservation
	    
	    @Column(name = "electricity_Observation1")
		private String electricityObservation1;
	    
		public String getElectricityObservation1() {
			return electricityObservation1;
		}

		public void setElectricityObservation1(String electricityObservation1) {
			this.electricityObservation1 = electricityObservation1;
		}

		@Column(name = "electricityobservation")
		private String electricityObservation;

	// End Electricity Duty Exemption
		
		@Column(name = "Proposal_For_EC_Observ")
		private String proposalForECObserv;
		
		@Column(name = "Proposal_For_Date_Of_Admiss")
		private String proposalForDateOfAdmiss;
		
		@Column(name = "Proposal_Of_EC_Ttl_Eligb")
		private String proposalOfECTtlEligb;
		
		@Column(name = "Table19_Observ")
		private String table19Observ;
		
		@Column(name = "Comments_Of_Nodal_Observ")
		private String commentsOfNodalObserv;
		
		@Column(name = "Proposal_For_Consid_Observ")
		private String proposalForConsidObserv;
	
		// table19Observ, commentsOfNodalObserv, proposalForConsidObserv

	public String getTable19Observ() {
			return table19Observ;
		}

		public void setTable19Observ(String table19Observ) {
			this.table19Observ = table19Observ;
		}

		public String getCommentsOfNodalObserv() {
			return commentsOfNodalObserv;
		}

		public void setCommentsOfNodalObserv(String commentsOfNodalObserv) {
			this.commentsOfNodalObserv = commentsOfNodalObserv;
		}

		public String getProposalForConsidObserv() {
			return proposalForConsidObserv;
		}

		public void setProposalForConsidObserv(String proposalForConsidObserv) {
			this.proposalForConsidObserv = proposalForConsidObserv;
		}

	public String getClamSactniisObserv() {
		return clamSactniisObserv;
	}

	public void setClamSactniisObserv(String clamSactniisObserv) {
		this.clamSactniisObserv = clamSactniisObserv;
	}

	public String getIisCMAmtObserv() {
		return iisCMAmtObserv;
	}

	public void setIisCMAmtObserv(String iisCMAmtObserv) {
		this.iisCMAmtObserv = iisCMAmtObserv;
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

	// getter and setter
	public Date getOptCutofDate() {
		return optCutofDate;
	}

	public void setOptCutofDate(Date optCutofDate) {
		this.optCutofDate = optCutofDate;
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

	public Date getLandAmtInv() {
		return landAmtInv;
	}

	public void setLandAmtInv(Date landAmtInv) {
		this.landAmtInv = landAmtInv;
	}

	public Date getBuildAmtInv() {
		return buildAmtInv;
	}

	public void setBuildAmtInv(Date buildAmtInv) {
		this.buildAmtInv = buildAmtInv;
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

	public Long getOtherAmtInve() {
		return otherAmtInve;
	}

	public void setOtherAmtInve(Long otherAmtInve) {
		this.otherAmtInve = otherAmtInve;
	}

	public Long getOthPerCerti() {
		return othPerCerti;
	}

	public void setOthPerCerti(Long othPerCerti) {
		this.othPerCerti = othPerCerti;
	}

	public Long getOthPerDpr() {
		return othPerDpr;
	}

	public void setOthPerDpr(Long othPerDpr) {
		this.othPerDpr = othPerDpr;
	}

	public Long getOthBankApprai() {
		return othBankApprai;
	}

	public void setOthBankApprai(Long othBankApprai) {
		this.othBankApprai = othBankApprai;
	}

	public Long getOthCapInvCA() {
		return othCapInvCA;
	}

	public void setOthCapInvCA(Long othCapInvCA) {
		this.othCapInvCA = othCapInvCA;
	}

	public Long getOthafterinv() {
		return othafterinv;
	}

	public void setOthafterinv(Long othafterinv) {
		this.othafterinv = othafterinv;
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

	public String getBreakUpCostObserve() {
		return breakUpCostObserve;
	}

	public void setBreakUpCostObserve(String breakUpCostObserve) {
		this.breakUpCostObserve = breakUpCostObserve;
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

	public String getCisTblObserve() {
		return cisTblObserve;
	}

	public void setCisTblObserve(String cisTblObserve) {
		this.cisTblObserve = cisTblObserve;
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

	public float getCisApplROIAmt() {
		return cisApplROIAmt;
	}

	public void setCisApplROIAmt(float cisApplROIAmt) {
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

	public String getIisFinYr1() {
		return iisFinYr1;
	}

	public void setIisFinYr1(String iisFinYr1) {
		this.iisFinYr1 = iisFinYr1;
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

	public String getIisTtlLoanAmt1() {
		return iisTtlLoanAmt1;
	}

	public void setIisTtlLoanAmt1(String iisTtlLoanAmt1) {
		this.iisTtlLoanAmt1 = iisTtlLoanAmt1;
	}

	public String getIisTtlLoanAmt2() {
		return iisTtlLoanAmt2;
	}

	public void setIisTtlLoanAmt2(String iisTtlLoanAmt2) {
		this.iisTtlLoanAmt2 = iisTtlLoanAmt2;
	}

	public String getIisTtlLoanAmt3() {
		return iisTtlLoanAmt3;
	}

	public void setIisTtlLoanAmt3(String iisTtlLoanAmt3) {
		this.iisTtlLoanAmt3 = iisTtlLoanAmt3;
	}

	public String getIisTtlLoanAmt4() {
		return iisTtlLoanAmt4;
	}

	public void setIisTtlLoanAmt4(String iisTtlLoanAmt4) {
		this.iisTtlLoanAmt4 = iisTtlLoanAmt4;
	}

	public String getIisTtlLoanAmt5() {
		return iisTtlLoanAmt5;
	}

	public void setIisTtlLoanAmt5(String iisTtlLoanAmt5) {
		this.iisTtlLoanAmt5 = iisTtlLoanAmt5;
	}

	

	public String getMandiFeeExeFinYr1() {
		return mandiFeeExeFinYr1;
	}

	public void setMandiFeeExeFinYr1(String mandiFeeExeFinYr1) {
		this.mandiFeeExeFinYr1 = mandiFeeExeFinYr1;
	}

	public String getTtlAvailMandiFeeExe() {
		return ttlAvailMandiFeeExe;
	}

	public void setTtlAvailMandiFeeExe(String ttlAvailMandiFeeExe) {
		this.ttlAvailMandiFeeExe = ttlAvailMandiFeeExe;
	}

	public String getMandiFeeExeObserv() {
		return mandiFeeExeObserv;
	}

	public void setMandiFeeExeObserv(String mandiFeeExeObserv) {
		this.mandiFeeExeObserv = mandiFeeExeObserv;
	}

	
	

	public String getDateFrom() {
		return dateFrom;
	}

	public void setDateFrom(String dateFrom) {
		this.dateFrom = dateFrom;
	}

	public String getDateTo() {
		return dateTo;
	}

	public void setDateTo(String dateTo) {
		this.dateTo = dateTo;
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

	public Long getIntCashAftinvdate() {
		return intCashAftinvdate;
	}

	public void setIntCashAftinvdate(Long intCashAftinvdate) {
		this.intCashAftinvdate = intCashAftinvdate;
	}

	public String getEquityCapCapInvCA() {
		return equityCapCapInvCA;
	}

	public void setEquityCapCapInvCA(String equityCapCapInvCA) {
		this.equityCapCapInvCA = equityCapCapInvCA;
	}

	public Date getIntCashAftinvproddate() {
		return intCashAftinvproddate;
	}

	public void setIntCashAftinvproddate(Date intCashAftinvproddate) {
		this.intCashAftinvproddate = intCashAftinvproddate;
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

	public String getSecBankApprai() {
		return SecBankApprai;
	}

	public void setSecBankApprai(String secBankApprai) {
		SecBankApprai = secBankApprai;
	}

	public String getSecPerCerti() {
		return SecPerCerti;
	}

	public void setSecPerCerti(String secPerCerti) {
		SecPerCerti = secPerCerti;
	}

	public String getDealPerDpr() {
		return dealPerDpr;
	}

	public void setDealPerDpr(String dealPerDpr) {
		this.dealPerDpr = dealPerDpr;
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

	public String getDurationFinYr1New() {
		return durationFinYr1New;
	}

	public void setDurationFinYr1New(String durationFinYr1New) {
		this.durationFinYr1New = durationFinYr1New;
	}

	

	public String getAmtOfNetSGST1New() {
		return amtOfNetSGST1New;
	}

	public void setAmtOfNetSGST1New(String amtOfNetSGST1New) {
		this.amtOfNetSGST1New = amtOfNetSGST1New;
	}

	

	public String[] getAmtOfNetSGSTReim1New() {
		return amtOfNetSGSTReim1New;
	}

	public void setAmtOfNetSGSTReim1New(String[] amtOfNetSGSTReim1New) {
		this.amtOfNetSGSTReim1New = amtOfNetSGSTReim1New;
	}

	public String[] getPayableAmountOfSGSTNew() {
		return payableAmountOfSGSTNew;
	}

	public void setPayableAmountOfSGSTNew(String[] payableAmountOfSGSTNew) {
		this.payableAmountOfSGSTNew = payableAmountOfSGSTNew;
	}

	public String getTtlDepositSGST() {
		return ttlDepositSGST;
	}

	public void setTtlDepositSGST(String ttlDepositSGST) {
		this.ttlDepositSGST = ttlDepositSGST;
	}

	public String getTtlClaimSGST() {
		return ttlClaimSGST;
	}

	public void setTtlClaimSGST(String ttlClaimSGST) {
		this.ttlClaimSGST = ttlClaimSGST;
	}

	public String getTtlPayableSGST() {
		return ttlPayableSGST;
	}

	public void setTtlPayableSGST(String ttlPayableSGST) {
		this.ttlPayableSGST = ttlPayableSGST;
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

	public String getAddOfOfficeCompl() {
		return addOfOfficeCompl;
	}

	public void setAddOfOfficeCompl(String addOfOfficeCompl) {
		this.addOfOfficeCompl = addOfOfficeCompl;
	}

	public String getTable2remark1() {
		return table2remark1;
	}

	public void setTable2remark1(String table2remark1) {
		this.table2remark1 = table2remark1;
	}

	public String getTable2remark11A() {
		return table2remark11A;
	}

	public void setTable2remark11A(String table2remark11a) {
		table2remark11A = table2remark11a;
	}

	public String getTable2remark11B() {
		return table2remark11B;
	}

	public void setTable2remark11B(String table2remark11b) {
		table2remark11B = table2remark11b;
	}

	public String getTable2remark11C() {
		return table2remark11C;
	}

	public void setTable2remark11C(String table2remark11c) {
		table2remark11C = table2remark11c;
	}

	public String getTable2remark2() {
		return table2remark2;
	}

	public void setTable2remark2(String table2remark2) {
		this.table2remark2 = table2remark2;
	}

	public String getTable2remark3() {
		return table2remark3;
	}

	public void setTable2remark3(String table2remark3) {
		this.table2remark3 = table2remark3;
	}

	public String getTable2remark4() {
		return table2remark4;
	}

	public void setTable2remark4(String table2remark4) {
		this.table2remark4 = table2remark4;
	}

	public String getTable2remark5() {
		return table2remark5;
	}

	public void setTable2remark5(String table2remark5) {
		this.table2remark5 = table2remark5;
	}

	public String getTable2remark6() {
		return table2remark6;
	}

	public void setTable2remark6(String table2remark6) {
		this.table2remark6 = table2remark6;
	}

	public String getTable2remark7() {
		return table2remark7;
	}

	public void setTable2remark7(String table2remark7) {
		this.table2remark7 = table2remark7;
	}

	public String getTable2remark8() {
		return table2remark8;
	}

	public void setTable2remark8(String table2remark8) {
		this.table2remark8 = table2remark8;
	}

	public String getTable2remark9() {
		return table2remark9;
	}

	public void setTable2remark9(String table2remark9) {
		this.table2remark9 = table2remark9;
	}

	public String getTable2remark10() {
		return table2remark10;
	}

	public void setTable2remark10(String table2remark10) {
		this.table2remark10 = table2remark10;
	}

	public String getTable2remark11() {
		return table2remark11;
	}

	public void setTable2remark11(String table2remark11) {
		this.table2remark11 = table2remark11;
	}

	public String getTable2remark12() {
		return table2remark12;
	}

	public void setTable2remark12(String table2remark12) {
		this.table2remark12 = table2remark12;
	}

	public String getTable2remark13() {
		return table2remark13;
	}

	public void setTable2remark13(String table2remark13) {
		this.table2remark13 = table2remark13;
	}

	public String getTable2remark14() {
		return table2remark14;
	}

	public void setTable2remark14(String table2remark14) {
		this.table2remark14 = table2remark14;
	}

	public String getTable2remark15() {
		return table2remark15;
	}

	public void setTable2remark15(String table2remark15) {
		this.table2remark15 = table2remark15;
	}

	public String getTable2remark16() {
		return table2remark16;
	}

	public void setTable2remark16(String table2remark16) {
		this.table2remark16 = table2remark16;
	}

	public String getTable2remark17() {
		return table2remark17;
	}

	public void setTable2remark17(String table2remark17) {
		this.table2remark17 = table2remark17;
	}

	public String getTable2remark18() {
		return table2remark18;
	}

	public void setTable2remark18(String table2remark18) {
		this.table2remark18 = table2remark18;
	}

	public String getTable2remark19() {
		return table2remark19;
	}

	public void setTable2remark19(String table2remark19) {
		this.table2remark19 = table2remark19;
	}

	public String getTable2remark20() {
		return table2remark20;
	}

	public void setTable2remark20(String table2remark20) {
		this.table2remark20 = table2remark20;
	}

	public String getTable2remark21() {
		return table2remark21;
	}

	public void setTable2remark21(String table2remark21) {
		this.table2remark21 = table2remark21;
	}

	public String getTable2remark22() {
		return table2remark22;
	}

	public void setTable2remark22(String table2remark22) {
		this.table2remark22 = table2remark22;
	}

	public String getSactiondate() {
		return sactiondate;
	}

	public void setSactiondate(String sactiondate) {
		this.sactiondate = sactiondate;
	}

	public String getTable1remark1() {
		return table1remark1;
	}

	public void setTable1remark1(String table1remark1) {
		this.table1remark1 = table1remark1;
	}

	public String getTable1remark2() {
		return table1remark2;
	}

	public void setTable1remark2(String table1remark2) {
		this.table1remark2 = table1remark2;
	}

	public String getTable1remark3() {
		return table1remark3;
	}

	public void setTable1remark3(String table1remark3) {
		this.table1remark3 = table1remark3;
	}

	public String getTable1remark4() {
		return table1remark4;
	}

	public void setTable1remark4(String table1remark4) {
		this.table1remark4 = table1remark4;
	}

	public String getTable1remark5() {
		return table1remark5;
	}

	public void setTable1remark5(String table1remark5) {
		this.table1remark5 = table1remark5;
	}

	public String getTable1remark6() {
		return table1remark6;
	}

	public void setTable1remark6(String table1remark6) {
		this.table1remark6 = table1remark6;
	}

	public Long getLandBanknodalCA() {
		return landBanknodalCA;
	}

	public void setLandBanknodalCA(Long landBanknodalCA) {
		this.landBanknodalCA = landBanknodalCA;
	}

	public Long getLandnodal() {
		return landnodal;
	}

	public void setLandnodal(Long landnodal) {
		this.landnodal = landnodal;
	}

	public Long getBuildBanknodalCA() {
		return buildBanknodalCA;
	}

	public void setBuildBanknodalCA(Long buildBanknodalCA) {
		this.buildBanknodalCA = buildBanknodalCA;
	}

	public Long getBuildnodal() {
		return buildnodal;
	}

	public void setBuildnodal(Long buildnodal) {
		this.buildnodal = buildnodal;
	}

	public Long getOthcPerDpr() {
		return othcPerDpr;
	}

	public void setOthcPerDpr(Long othcPerDpr) {
		this.othcPerDpr = othcPerDpr;
	}

	public Long getOthcBankApprai() {
		return othcBankApprai;
	}

	public void setOthcBankApprai(Long othcBankApprai) {
		this.othcBankApprai = othcBankApprai;
	}

	public Long getOthcBanknodalCA() {
		return othcBanknodalCA;
	}

	public void setOthcBanknodalCA(Long othcBanknodalCA) {
		this.othcBanknodalCA = othcBanknodalCA;
	}

	public Long getOthcPerCerti() {
		return othcPerCerti;
	}

	public void setOthcPerCerti(Long othcPerCerti) {
		this.othcPerCerti = othcPerCerti;
	}

	public Long getOthcCapInvCA() {
		return othcCapInvCA;
	}

	public void setOthcCapInvCA(Long othcCapInvCA) {
		this.othcCapInvCA = othcCapInvCA;
	}

	public Long getOthcCapInvValuer() {
		return othcCapInvValuer;
	}

	public void setOthcCapInvValuer(Long othcCapInvValuer) {
		this.othcCapInvValuer = othcCapInvValuer;
	}

	public Long getOthcafterinv() {
		return othcafterinv;
	}

	public void setOthcafterinv(Long othcafterinv) {
		this.othcafterinv = othcafterinv;
	}

	public Long getOthcnodal() {
		return othcnodal;
	}

	public void setOthcnodal(Long othcnodal) {
		this.othcnodal = othcnodal;
	}

	public Long getPlantBanknodalCA() {
		return plantBanknodalCA;
	}

	public void setPlantBanknodalCA(Long plantBanknodalCA) {
		this.plantBanknodalCA = plantBanknodalCA;
	}

	public Long getPlantnodal() {
		return plantnodal;
	}

	public void setPlantnodal(Long plantnodal) {
		this.plantnodal = plantnodal;
	}

	public Long getMiscBanknodalCA() {
		return miscBanknodalCA;
	}

	public void setMiscBanknodalCA(Long miscBanknodalCA) {
		this.miscBanknodalCA = miscBanknodalCA;
	}

	public Long getMiscnodal() {
		return miscnodal;
	}

	public void setMiscnodal(Long miscnodal) {
		this.miscnodal = miscnodal;
	}

	public Long getSubttlabanknodalca() {
		return subttlabanknodalca;
	}

	public void setSubttlabanknodalca(Long subttlabanknodalca) {
		this.subttlabanknodalca = subttlabanknodalca;
	}

	public Long getSubttlanodal() {
		return subttlanodal;
	}

	public void setSubttlanodal(Long subttlanodal) {
		this.subttlanodal = subttlanodal;
	}

	public Long getPrelimBanknodalCA() {
		return prelimBanknodalCA;
	}

	public void setPrelimBanknodalCA(Long prelimBanknodalCA) {
		this.prelimBanknodalCA = prelimBanknodalCA;
	}

	public Long getPrelimnodal() {
		return prelimnodal;
	}

	public void setPrelimnodal(Long prelimnodal) {
		this.prelimnodal = prelimnodal;
	}

	public Long getPpeBanknodalCA() {
		return ppeBanknodalCA;
	}

	public void setPpeBanknodalCA(Long ppeBanknodalCA) {
		this.ppeBanknodalCA = ppeBanknodalCA;
	}

	public Long getPpenodal() {
		return ppenodal;
	}

	public void setPpenodal(Long ppenodal) {
		this.ppenodal = ppenodal;
	}

	public Long getInterestBanknodalca() {
		return interestBanknodalca;
	}

	public void setInterestBanknodalca(Long interestBanknodalca) {
		this.interestBanknodalca = interestBanknodalca;
	}

	public Long getInterestnodal() {
		return interestnodal;
	}

	public void setInterestnodal(Long interestnodal) {
		this.interestnodal = interestnodal;
	}

	public Long getMarginBanknodalca() {
		return marginBanknodalca;
	}

	public void setMarginBanknodalca(Long marginBanknodalca) {
		this.marginBanknodalca = marginBanknodalca;
	}

	public Long getMarginnodal() {
		return marginnodal;
	}

	public void setMarginnodal(Long marginnodal) {
		this.marginnodal = marginnodal;
	}

	public Long getOthBanknodalca() {
		return othBanknodalca;
	}

	public void setOthBanknodalca(Long othBanknodalca) {
		this.othBanknodalca = othBanknodalca;
	}

	public Long getOthnodal() {
		return othnodal;
	}

	public void setOthnodal(Long othnodal) {
		this.othnodal = othnodal;
	}

	public Long getSubTtlBCapinvca() {
		return subTtlBCapinvca;
	}

	public void setSubTtlBCapinvca(Long subTtlBCapinvca) {
		this.subTtlBCapinvca = subTtlBCapinvca;
	}

	public Long getSubTtlBnodal() {
		return subTtlBnodal;
	}

	public void setSubTtlBnodal(Long subTtlBnodal) {
		this.subTtlBnodal = subTtlBnodal;
	}

	public Long getTtlcapinvca() {
		return ttlcapinvca;
	}

	public void setTtlcapinvca(Long ttlcapinvca) {
		this.ttlcapinvca = ttlcapinvca;
	}

	public Long getTtlnodal() {
		return ttlnodal;
	}

	public void setTtlnodal(Long ttlnodal) {
		this.ttlnodal = ttlnodal;
	}

	public Long getPrelimafterinv() {
		return prelimafterinv;
	}

	public void setPrelimafterinv(Long prelimafterinv) {
		this.prelimafterinv = prelimafterinv;
	}

	public Date getBeforeopt() {
		return beforeopt;
	}

	public void setBeforeopt(Date beforeopt) {
		this.beforeopt = beforeopt;
	}

	public Date getComprodDate() {
		return comprodDate;
	}

	public void setComprodDate(Date comprodDate) {
		this.comprodDate = comprodDate;
	}

	public Date getEleligibiledate() {
		return eleligibiledate;
	}

	public void setEleligibiledate(Date eleligibiledate) {
		this.eleligibiledate = eleligibiledate;
	}

	public Date getEligibilelDate() {
		return EligibilelDate;
	}

	public void setEligibilelDate(Date eligibilelDate) {
		EligibilelDate = eligibilelDate;
	}

	public Date getByondcommercialdate() {
		return byondcommercialdate;
	}

	public void setByondcommercialdate(Date byondcommercialdate) {
		this.byondcommercialdate = byondcommercialdate;
	}

	public Date getCommercialDate() {
		return CommercialDate;
	}

	public void setCommercialDate(Date commercialDate) {
		CommercialDate = commercialDate;
	}

	public Long getFirstbybank() {
		return firstbybank;
	}

	public void setFirstbybank(Long firstbybank) {
		this.firstbybank = firstbybank;
	}

	public Long getFirstbycompany() {
		return firstbycompany;
	}

	public void setFirstbycompany(Long firstbycompany) {
		this.firstbycompany = firstbycompany;
	}

	public Long getFirstannualpermissible() {
		return firstannualpermissible;
	}

	public void setFirstannualpermissible(Long firstannualpermissible) {
		this.firstannualpermissible = firstannualpermissible;
	}

	public Long getFirstadmissibleamount() {
		return firstadmissibleamount;
	}

	public void setFirstadmissibleamount(Long firstadmissibleamount) {
		this.firstadmissibleamount = firstadmissibleamount;
	}

	public Long getSecondbybank() {
		return secondbybank;
	}

	public void setSecondbybank(Long secondbybank) {
		this.secondbybank = secondbybank;
	}

	public Long getSecondbycompany() {
		return secondbycompany;
	}

	public void setSecondbycompany(Long secondbycompany) {
		this.secondbycompany = secondbycompany;
	}

	public Long getSecondannualpermissible() {
		return secondannualpermissible;
	}

	public void setSecondannualpermissible(Long secondannualpermissible) {
		this.secondannualpermissible = secondannualpermissible;
	}

	public Long getSecondadmissibleamount() {
		return secondadmissibleamount;
	}

	public void setSecondadmissibleamount(Long secondadmissibleamount) {
		this.secondadmissibleamount = secondadmissibleamount;
	}

	public Long getThirdbybank() {
		return thirdbybank;
	}

	public void setThirdbybank(Long thirdbybank) {
		this.thirdbybank = thirdbybank;
	}

	public Long getThirdbycompany() {
		return thirdbycompany;
	}

	public void setThirdbycompany(Long thirdbycompany) {
		this.thirdbycompany = thirdbycompany;
	}

	public Long getThirdannualpermissible() {
		return thirdannualpermissible;
	}

	public void setThirdannualpermissible(Long thirdannualpermissible) {
		this.thirdannualpermissible = thirdannualpermissible;
	}

	public Long getThirdadmissibleamount() {
		return thirdadmissibleamount;
	}

	public void setThirdadmissibleamount(Long thirdadmissibleamount) {
		this.thirdadmissibleamount = thirdadmissibleamount;
	}

	public Long getFourthbybank() {
		return fourthbybank;
	}

	public void setFourthbybank(Long fourthbybank) {
		this.fourthbybank = fourthbybank;
	}

	public Long getFourthbycompany() {
		return fourthbycompany;
	}

	public void setFourthbycompany(Long fourthbycompany) {
		this.fourthbycompany = fourthbycompany;
	}

	public Long getFourthannualpermissible() {
		return fourthannualpermissible;
	}

	public void setFourthannualpermissible(Long fourthannualpermissible) {
		this.fourthannualpermissible = fourthannualpermissible;
	}

	public Long getFourthadmissibleamount() {
		return fourthadmissibleamount;
	}

	public void setFourthadmissibleamount(Long fourthadmissibleamount) {
		this.fourthadmissibleamount = fourthadmissibleamount;
	}

	public Long getFifthbybank() {
		return fifthbybank;
	}

	public void setFifthbybank(Long fifthbybank) {
		this.fifthbybank = fifthbybank;
	}

	public Long getFifthbycompany() {
		return fifthbycompany;
	}

	public void setFifthbycompany(Long fifthbycompany) {
		this.fifthbycompany = fifthbycompany;
	}

	public Long getFifthannualpermissible() {
		return fifthannualpermissible;
	}

	public void setFifthannualpermissible(Long fifthannualpermissible) {
		this.fifthannualpermissible = fifthannualpermissible;
	}

	public Long getFifthadmissibleamount() {
		return fifthadmissibleamount;
	}

	public void setFifthadmissibleamount(Long fifthadmissibleamount) {
		this.fifthadmissibleamount = fifthadmissibleamount;
	}

	public Long getTotalbybank() {
		return totalbybank;
	}

	public void setTotalbybank(Long totalbybank) {
		this.totalbybank = totalbybank;
	}

	public Long getTotalbycompany() {
		return totalbycompany;
	}

	public void setTotalbycompany(Long totalbycompany) {
		this.totalbycompany = totalbycompany;
	}

	public Long getTotalannualpermissible() {
		return totalannualpermissible;
	}

	public void setTotalannualpermissible(Long totalannualpermissible) {
		this.totalannualpermissible = totalannualpermissible;
	}

	public Long getTotaladmissibleamount() {
		return totaladmissibleamount;
	}

	public void setTotaladmissibleamount(Long totaladmissibleamount) {
		this.totaladmissibleamount = totaladmissibleamount;
	}

	public Long getFirstbybankiis() {
		return firstbybankiis;
	}

	public void setFirstbybankiis(Long firstbybankiis) {
		this.firstbybankiis = firstbybankiis;
	}

	public Long getFirstbycompanyiis() {
		return firstbycompanyiis;
	}

	public void setFirstbycompanyiis(Long firstbycompanyiis) {
		this.firstbycompanyiis = firstbycompanyiis;
	}

	public Long getFirstannualpermissibleiis() {
		return firstannualpermissibleiis;
	}

	public void setFirstannualpermissibleiis(Long firstannualpermissibleiis) {
		this.firstannualpermissibleiis = firstannualpermissibleiis;
	}

	public Long getFirstadmissibleamountiis() {
		return firstadmissibleamountiis;
	}

	public void setFirstadmissibleamountiis(Long firstadmissibleamountiis) {
		this.firstadmissibleamountiis = firstadmissibleamountiis;
	}

	public Long getSecondbybankiis() {
		return secondbybankiis;
	}

	public void setSecondbybankiis(Long secondbybankiis) {
		this.secondbybankiis = secondbybankiis;
	}

	public Long getSecondbycompanyiis() {
		return secondbycompanyiis;
	}

	public void setSecondbycompanyiis(Long secondbycompanyiis) {
		this.secondbycompanyiis = secondbycompanyiis;
	}

	public Long getSecondannualpermissibleiis() {
		return secondannualpermissibleiis;
	}

	public void setSecondannualpermissibleiis(Long secondannualpermissibleiis) {
		this.secondannualpermissibleiis = secondannualpermissibleiis;
	}

	public Long getSecondadmissibleamountiis() {
		return secondadmissibleamountiis;
	}

	public void setSecondadmissibleamountiis(Long secondadmissibleamountiis) {
		this.secondadmissibleamountiis = secondadmissibleamountiis;
	}

	public Long getThirdbybankiis() {
		return thirdbybankiis;
	}

	public void setThirdbybankiis(Long thirdbybankiis) {
		this.thirdbybankiis = thirdbybankiis;
	}

	public Long getThirdbycompanyiis() {
		return thirdbycompanyiis;
	}

	public void setThirdbycompanyiis(Long thirdbycompanyiis) {
		this.thirdbycompanyiis = thirdbycompanyiis;
	}

	public Long getThirdannualpermissibleiis() {
		return thirdannualpermissibleiis;
	}

	public void setThirdannualpermissibleiis(Long thirdannualpermissibleiis) {
		this.thirdannualpermissibleiis = thirdannualpermissibleiis;
	}

	public Long getThirdadmissibleamountiis() {
		return thirdadmissibleamountiis;
	}

	public void setThirdadmissibleamountiis(Long thirdadmissibleamountiis) {
		this.thirdadmissibleamountiis = thirdadmissibleamountiis;
	}

	public Long getFourthbybankiis() {
		return fourthbybankiis;
	}

	public void setFourthbybankiis(Long fourthbybankiis) {
		this.fourthbybankiis = fourthbybankiis;
	}

	public Long getFourthbycompanyiis() {
		return fourthbycompanyiis;
	}

	public void setFourthbycompanyiis(Long fourthbycompanyiis) {
		this.fourthbycompanyiis = fourthbycompanyiis;
	}

	public Long getFourthannualpermissibleiis() {
		return fourthannualpermissibleiis;
	}

	public void setFourthannualpermissibleiis(Long fourthannualpermissibleiis) {
		this.fourthannualpermissibleiis = fourthannualpermissibleiis;
	}

	public Long getFourthadmissibleamountiis() {
		return fourthadmissibleamountiis;
	}

	public void setFourthadmissibleamountiis(Long fourthadmissibleamountiis) {
		this.fourthadmissibleamountiis = fourthadmissibleamountiis;
	}

	public Long getFifthbybankiis() {
		return fifthbybankiis;
	}

	public void setFifthbybankiis(Long fifthbybankiis) {
		this.fifthbybankiis = fifthbybankiis;
	}

	public Long getFifthbycompanyiis() {
		return fifthbycompanyiis;
	}

	public void setFifthbycompanyiis(Long fifthbycompanyiis) {
		this.fifthbycompanyiis = fifthbycompanyiis;
	}

	public Long getFifthannualpermissibleiis() {
		return fifthannualpermissibleiis;
	}

	public void setFifthannualpermissibleiis(Long fifthannualpermissibleiis) {
		this.fifthannualpermissibleiis = fifthannualpermissibleiis;
	}

	public Long getFifthadmissibleamountiis() {
		return fifthadmissibleamountiis;
	}

	public void setFifthadmissibleamountiis(Long fifthadmissibleamountiis) {
		this.fifthadmissibleamountiis = fifthadmissibleamountiis;
	}

	public Long getTotalbybankiis() {
		return totalbybankiis;
	}

	public void setTotalbybankiis(Long totalbybankiis) {
		this.totalbybankiis = totalbybankiis;
	}

	public Long getTotalbycompanyiis() {
		return totalbycompanyiis;
	}

	public void setTotalbycompanyiis(Long totalbycompanyiis) {
		this.totalbycompanyiis = totalbycompanyiis;
	}

	public Long getTotalannualpermissibleiis() {
		return totalannualpermissibleiis;
	}

	public void setTotalannualpermissibleiis(Long totalannualpermissibleiis) {
		this.totalannualpermissibleiis = totalannualpermissibleiis;
	}

	public Long getTotaladmissibleamountiis() {
		return totaladmissibleamountiis;
	}

	public void setTotaladmissibleamountiis(Long totaladmissibleamountiis) {
		this.totaladmissibleamountiis = totaladmissibleamountiis;
	}

	public Long getFirstbybankqis() {
		return firstbybankqis;
	}

	public void setFirstbybankqis(Long firstbybankqis) {
		this.firstbybankqis = firstbybankqis;
	}

	public Long getFirstbycompanyqis() {
		return firstbycompanyqis;
	}

	public void setFirstbycompanyqis(Long firstbycompanyqis) {
		this.firstbycompanyqis = firstbycompanyqis;
	}

	public Long getFirstannualpermissibleqis() {
		return firstannualpermissibleqis;
	}

	public void setFirstannualpermissibleqis(Long firstannualpermissibleqis) {
		this.firstannualpermissibleqis = firstannualpermissibleqis;
	}

	public Long getFirstadmissibleamountqis() {
		return firstadmissibleamountqis;
	}

	public void setFirstadmissibleamountqis(Long firstadmissibleamountqis) {
		this.firstadmissibleamountqis = firstadmissibleamountqis;
	}

	public Long getSecondbybankqis() {
		return secondbybankqis;
	}

	public void setSecondbybankqis(Long secondbybankqis) {
		this.secondbybankqis = secondbybankqis;
	}

	public Long getSecondbycompanyqis() {
		return secondbycompanyqis;
	}

	public void setSecondbycompanyqis(Long secondbycompanyqis) {
		this.secondbycompanyqis = secondbycompanyqis;
	}

	public Long getSecondannualpermissibleqis() {
		return secondannualpermissibleqis;
	}

	public void setSecondannualpermissibleqis(Long secondannualpermissibleqis) {
		this.secondannualpermissibleqis = secondannualpermissibleqis;
	}

	public Long getSecondadmissibleamountqis() {
		return secondadmissibleamountqis;
	}

	public void setSecondadmissibleamountqis(Long secondadmissibleamountqis) {
		this.secondadmissibleamountqis = secondadmissibleamountqis;
	}

	public Long getThirdbybankqis() {
		return thirdbybankqis;
	}

	public void setThirdbybankqis(Long thirdbybankqis) {
		this.thirdbybankqis = thirdbybankqis;
	}

	public Long getThirdbycompanyqis() {
		return thirdbycompanyqis;
	}

	public void setThirdbycompanyqis(Long thirdbycompanyqis) {
		this.thirdbycompanyqis = thirdbycompanyqis;
	}

	public Long getThirdannualpermissibleqis() {
		return thirdannualpermissibleqis;
	}

	public void setThirdannualpermissibleqis(Long thirdannualpermissibleqis) {
		this.thirdannualpermissibleqis = thirdannualpermissibleqis;
	}

	public Long getThirdadmissibleamountqis() {
		return thirdadmissibleamountqis;
	}

	public void setThirdadmissibleamountqis(Long thirdadmissibleamountqis) {
		this.thirdadmissibleamountqis = thirdadmissibleamountqis;
	}

	public Long getFourthbybankqis() {
		return fourthbybankqis;
	}

	public void setFourthbybankqis(Long fourthbybankqis) {
		this.fourthbybankqis = fourthbybankqis;
	}

	public Long getFourthbycompanyqis() {
		return fourthbycompanyqis;
	}

	public void setFourthbycompanyqis(Long fourthbycompanyqis) {
		this.fourthbycompanyqis = fourthbycompanyqis;
	}

	public Long getFourthannualpermissibleqis() {
		return fourthannualpermissibleqis;
	}

	public void setFourthannualpermissibleqis(Long fourthannualpermissibleqis) {
		this.fourthannualpermissibleqis = fourthannualpermissibleqis;
	}

	public Long getFourthadmissibleamountqis() {
		return fourthadmissibleamountqis;
	}

	public void setFourthadmissibleamountqis(Long fourthadmissibleamountqis) {
		this.fourthadmissibleamountqis = fourthadmissibleamountqis;
	}

	public Long getFifthbybankqis() {
		return fifthbybankqis;
	}

	public void setFifthbybankqis(Long fifthbybankqis) {
		this.fifthbybankqis = fifthbybankqis;
	}

	public Long getFifthbycompanyqis() {
		return fifthbycompanyqis;
	}

	public void setFifthbycompanyqis(Long fifthbycompanyqis) {
		this.fifthbycompanyqis = fifthbycompanyqis;
	}

	public Long getFifthannualpermissibleqis() {
		return fifthannualpermissibleqis;
	}

	public void setFifthannualpermissibleqis(Long fifthannualpermissibleqis) {
		this.fifthannualpermissibleqis = fifthannualpermissibleqis;
	}

	public Long getFifthadmissibleamountqis() {
		return fifthadmissibleamountqis;
	}

	public void setFifthadmissibleamountqis(Long fifthadmissibleamountqis) {
		this.fifthadmissibleamountqis = fifthadmissibleamountqis;
	}

	public Long getTotalbybankqis() {
		return totalbybankqis;
	}

	public void setTotalbybankqis(Long totalbybankqis) {
		this.totalbybankqis = totalbybankqis;
	}

	public Long getTotalbycompanyqis() {
		return totalbycompanyqis;
	}

	public void setTotalbycompanyqis(Long totalbycompanyqis) {
		this.totalbycompanyqis = totalbycompanyqis;
	}

	public Long getTotalannualpermissibleqis() {
		return totalannualpermissibleqis;
	}

	public void setTotalannualpermissibleqis(Long totalannualpermissibleqis) {
		this.totalannualpermissibleqis = totalannualpermissibleqis;
	}

	public Long getTotaladmissibleamountqis() {
		return totaladmissibleamountqis;
	}

	public void setTotaladmissibleamountqis(Long totaladmissibleamountqis) {
		this.totaladmissibleamountqis = totaladmissibleamountqis;
	}

	public String getQisobservation() {
		return qisobservation;
	}

	public void setQisobservation(String qisobservation) {
		this.qisobservation = qisobservation;
	}

	public String getElectricityObservation() {
		return electricityObservation;
	}

	public void setElectricityObservation(String electricityObservation) {
		this.electricityObservation = electricityObservation;
	}

	public String getActualdatecomminvrem() {
		return actualdatecomminvrem;
	}

	public void setActualdatecomminvrem(String actualdatecomminvrem) {
		this.actualdatecomminvrem = actualdatecomminvrem;
	}

	public String getApplicantchangreq() {
		return applicantchangreq;
	}

	public void setApplicantchangreq(String applicantchangreq) {
		this.applicantchangreq = applicantchangreq;
	}

	public String getApplicantchangreqrem() {
		return applicantchangreqrem;

	}

	public Date getActualdatecomminv() {
		return actualdatecomminv;
	}

	public void setActualdatecomminv(Date actualdatecomminv) {
		this.actualdatecomminv = actualdatecomminv;
	}

	public void setApplicantchangreqrem(String applicantchangreqrem) {
		this.applicantchangreqrem = applicantchangreqrem;
	}

	public String getNodalobservcutdate() {
		return nodalobservcutdate;
	}

	public void setNodalobservcutdate(String nodalobservcutdate) {
		this.nodalobservcutdate = nodalobservcutdate;
	}

	public String getNodalobservcutdateremark() {
		return nodalobservcutdateremark;
	}

	public void setNodalobservcutdateremark(String nodalobservcutdateremark) {
		this.nodalobservcutdateremark = nodalobservcutdateremark;
	}

	public String getEligiDisallowAvailAmt() {
		return eligiDisallowAvailAmt;
	}

	public void setEligiDisallowAvailAmt(String eligiDisallowAvailAmt) {
		this.eligiDisallowAvailAmt = eligiDisallowAvailAmt;
	}

	public String getTtleligiDisallowAvailAmt() {
		return ttleligiDisallowAvailAmt;
	}

	public void setTtleligiDisallowAvailAmt(String ttleligiDisallowAvailAmt) {
		this.ttleligiDisallowAvailAmt = ttleligiDisallowAvailAmt;
	}

	public String getActaulDate7() {
		return actaulDate7;
	}

	public void setActaulDate7(String actaulDate7) {
		this.actaulDate7 = actaulDate7;
	}

	public String getCutofDate8() {
		return cutofDate8;
	}

	public void setCutofDate8(String cutofDate8) {
		this.cutofDate8 = cutofDate8;
	}

	public String getNodalCutofDate9() {
		return nodalCutofDate9;
	}

	public void setNodalCutofDate9(String nodalCutofDate9) {
		this.nodalCutofDate9 = nodalCutofDate9;
	}

	public String getIndusUnit17() {
		return indusUnit17;
	}

	public void setIndusUnit17(String indusUnit17) {
		this.indusUnit17 = indusUnit17;
	}

	public String getMandiFeeDateFrom1() {
		return mandiFeeDateFrom1;
	}

	public void setMandiFeeDateFrom1(String mandiFeeDateFrom1) {
		this.mandiFeeDateFrom1 = mandiFeeDateFrom1;
	}

	public String getMandiFeeDateTo1() {
		return mandiFeeDateTo1;
	}

	public void setMandiFeeDateTo1(String mandiFeeDateTo1) {
		this.mandiFeeDateTo1 = mandiFeeDateTo1;
	}

	public String getClaimMandiFeeExe1() {
		return claimMandiFeeExe1;
	}

	public void setClaimMandiFeeExe1(String claimMandiFeeExe1) {
		this.claimMandiFeeExe1 = claimMandiFeeExe1;
	}

	/*
	 * public String getMandiFeeDateFrom2() { return mandiFeeDateFrom2; }
	 * 
	 * public void setMandiFeeDateFrom2(String mandiFeeDateFrom2) {
	 * this.mandiFeeDateFrom2 = mandiFeeDateFrom2; }
	 * 
	 * public String getMandiFeeDateTo2() { return mandiFeeDateTo2; }
	 * 
	 * public void setMandiFeeDateTo2(String mandiFeeDateTo2) { this.mandiFeeDateTo2
	 * = mandiFeeDateTo2; }
	 * 
	 * public String getClaimMandiFeeExe2() { return claimMandiFeeExe2; }
	 * 
	 * public void setClaimMandiFeeExe2(String claimMandiFeeExe2) {
	 * this.claimMandiFeeExe2 = claimMandiFeeExe2; }
	 * 
	 * public String getMandiFeeDateFrom3() { return mandiFeeDateFrom3; }
	 * 
	 * public void setMandiFeeDateFrom3(String mandiFeeDateFrom3) {
	 * this.mandiFeeDateFrom3 = mandiFeeDateFrom3; }
	 * 
	 * public String getMandiFeeDateTo3() { return mandiFeeDateTo3; }
	 * 
	 * public void setMandiFeeDateTo3(String mandiFeeDateTo3) { this.mandiFeeDateTo3
	 * = mandiFeeDateTo3; }
	 * 
	 * public String getClaimMandiFeeExe3() { return claimMandiFeeExe3; }
	 * 
	 * public void setClaimMandiFeeExe3(String claimMandiFeeExe3) {
	 * this.claimMandiFeeExe3 = claimMandiFeeExe3; }
	 * 
	 * public String getMandiFeeDateFrom4() { return mandiFeeDateFrom4; }
	 * 
	 * public void setMandiFeeDateFrom4(String mandiFeeDateFrom4) {
	 * this.mandiFeeDateFrom4 = mandiFeeDateFrom4; }
	 * 
	 * public String getMandiFeeDateTo4() { return mandiFeeDateTo4; }
	 * 
	 * public void setMandiFeeDateTo4(String mandiFeeDateTo4) { this.mandiFeeDateTo4
	 * = mandiFeeDateTo4; }
	 * 
	 * public String getClaimMandiFeeExe4() { return claimMandiFeeExe4; }
	 * 
	 * public void setClaimMandiFeeExe4(String claimMandiFeeExe4) {
	 * this.claimMandiFeeExe4 = claimMandiFeeExe4; }
	 * 
	 * public String getMandiFeeDateFrom5() { return mandiFeeDateFrom5; }
	 * 
	 * public void setMandiFeeDateFrom5(String mandiFeeDateFrom5) {
	 * this.mandiFeeDateFrom5 = mandiFeeDateFrom5; }
	 * 
	 * public String getMandiFeeDateTo5() { return mandiFeeDateTo5; }
	 * 
	 * public void setMandiFeeDateTo5(String mandiFeeDateTo5) { this.mandiFeeDateTo5
	 * = mandiFeeDateTo5; }
	 * 
	 * public String getClaimMandiFeeExe5() { return claimMandiFeeExe5; }
	 * 
	 * public void setClaimMandiFeeExe5(String claimMandiFeeExe5) {
	 * this.claimMandiFeeExe5 = claimMandiFeeExe5; }
	 */
	public String getDurationFrom1New() {
		return durationFrom1New;
	}

	public String getEpfObserve() {
		return epfObserve;
	}

	public void setEpfObserve(String epfObserve) {
		this.epfObserve = epfObserve;
	}

	public String getTtlDisallowedClaimAmt() {
		return ttlDisallowedClaimAmt;
	}

	public void setTtlDisallowedClaimAmt(String ttlDisallowedClaimAmt) {
		this.ttlDisallowedClaimAmt = ttlDisallowedClaimAmt;
	}

	public void setDurationFrom1New(String durationFrom1New) {
		this.durationFrom1New = durationFrom1New;
	}

	public String getDurationTo1New() {
		return durationTo1New;
	}

	public void setDurationTo1New(String durationTo1New) {
		this.durationTo1New = durationTo1New;
	}

	public Long getTtlTurnOverPartDivers() {
		return ttlTurnOverPartDivers;
	}

	public void setTtlTurnOverPartDivers(Long ttlTurnOverPartDivers) {
		this.ttlTurnOverPartDivers = ttlTurnOverPartDivers;
	}

	public Long getTtlIncreTurnover1() {
		return ttlIncreTurnover1;
	}

	public void setTtlIncreTurnover1(Long ttlIncreTurnover1) {
		this.ttlIncreTurnover1 = ttlIncreTurnover1;
	}

	public Long getTtlAmtCommTax1Ttl() {
		return ttlAmtCommTax1Ttl;
	}

	public void setTtlAmtCommTax1Ttl(Long ttlAmtCommTax1Ttl) {
		this.ttlAmtCommTax1Ttl = ttlAmtCommTax1Ttl;
	}

	public Long getTtlIncreNetSGST1() {
		return ttlIncreNetSGST1;
	}

	public void setTtlIncreNetSGST1(Long ttlIncreNetSGST1) {
		this.ttlIncreNetSGST1 = ttlIncreNetSGST1;
	}

	public Long getTtlAmtOfNetSGST1() {
		return ttlAmtOfNetSGST1;
	}

	public void setTtlAmtOfNetSGST1(Long ttlAmtOfNetSGST1) {
		this.ttlAmtOfNetSGST1 = ttlAmtOfNetSGST1;
	}

	public Long getTtlAmtOfNetSGSTReim1() {
		return ttlAmtOfNetSGSTReim1;
	}

	public void setTtlAmtOfNetSGSTReim1(Long ttlAmtOfNetSGSTReim1) {
		this.ttlAmtOfNetSGSTReim1 = ttlAmtOfNetSGSTReim1;
	}

	
	
	public String getComputationFinYr() {
		return computationFinYr;
	}

	public void setComputationFinYr(String computationFinYr) {
		this.computationFinYr = computationFinYr;
	}

	public String getStampDutyDateFrom() {
		return stampDutyDateFrom;
	}

	public void setStampDutyDateFrom(String stampDutyDateFrom) {
		this.stampDutyDateFrom = stampDutyDateFrom;
	}

	public String getStampDutyDateTo() {
		return stampDutyDateTo;
	}

	public void setStampDutyDateTo(String stampDutyDateTo) {
		this.stampDutyDateTo = stampDutyDateTo;
	}

	public void setClaimStampDutyReimAmt(String claimStampDutyReimAmt) {
		this.claimStampDutyReimAmt = claimStampDutyReimAmt;
	}

	public String[] getStampDutyReimElig() {
		return stampDutyReimElig;
	}

	public void setStampDutyReimElig(String[] stampDutyReimElig) {
		this.stampDutyReimElig = stampDutyReimElig;
	}

	public String getEpfComputFinYr() {
		return epfComputFinYr;
	}

	public void setEpfComputFinYr(String epfComputFinYr) {
		this.epfComputFinYr = epfComputFinYr;
	}

	public String getEmployerContributionEPF() {
		return employerContributionEPF;
	}

	public void setEmployerContributionEPF(String employerContributionEPF) {
		this.employerContributionEPF = employerContributionEPF;
	}

	public String[] getReimEligibility() {
		return reimEligibility;
	}

	public void setReimEligibility(String[] reimEligibility) {
		this.reimEligibility = reimEligibility;
	}

	public String getElectricityDutyExeFinYr() {
		return electricityDutyExeFinYr;
	}

	public void setElectricityDutyExeFinYr(String electricityDutyExeFinYr) {
		this.electricityDutyExeFinYr = electricityDutyExeFinYr;
	}

	public String getElectricityDateFrom() {
		return electricityDateFrom;
	}

	public void setElectricityDateFrom(String electricityDateFrom) {
		this.electricityDateFrom = electricityDateFrom;
	}

	public String getElectricityDateTo() {
		return electricityDateTo;
	}

	public void setElectricityDateTo(String electricityDateTo) {
		this.electricityDateTo = electricityDateTo;
	}

	public String getElectricityAmtClaim() {
		return electricityAmtClaim;
	}

	public void setElectricityAmtClaim(String electricityAmtClaim) {
		this.electricityAmtClaim = electricityAmtClaim;
	}

	

	public String[] getElectricityEligAmt() {
		return electricityEligAmt;
	}

	public void setElectricityEligAmt(String[] electricityEligAmt) {
		this.electricityEligAmt = electricityEligAmt;
	}

	public String getTtlElectricityAmtClaim() {
		return ttlElectricityAmtClaim;
	}

	public void setTtlElectricityAmtClaim(String ttlElectricityAmtClaim) {
		this.ttlElectricityAmtClaim = ttlElectricityAmtClaim;
	}

	public String getTtlElectricityEligAmt() {
		return ttlElectricityEligAmt;
	}

	public void setTtlElectricityEligAmt(String ttlElectricityEligAmt) {
		this.ttlElectricityEligAmt = ttlElectricityEligAmt;
	}

	public String getTtlClaimMandiFeeExe() {
		return ttlClaimMandiFeeExe;
	}

	public void setTtlClaimMandiFeeExe(String ttlClaimMandiFeeExe) {
		this.ttlClaimMandiFeeExe = ttlClaimMandiFeeExe;
	}

	public String[] getAvailMandiFeeExe1() {
		return availMandiFeeExe1;
	}

	public void setAvailMandiFeeExe1(String[] availMandiFeeExe1) {
		this.availMandiFeeExe1 = availMandiFeeExe1;
	}

	public String getDisallowedFinYr() {
		return disallowedFinYr;
	}

	public void setDisallowedFinYr(String disallowedFinYr) {
		this.disallowedFinYr = disallowedFinYr;
	}

	public String[] getSgstFinYr() {
		return sgstFinYr;
	}

	public void setSgstFinYr(String[] sgstFinYr) {
		this.sgstFinYr = sgstFinYr;
	}

	public String[] getSgstTurnOverSalesItems() {
		return sgstTurnOverSalesItems;
	}

	public void setSgstTurnOverSalesItems(String[] sgstTurnOverSalesItems) {
		this.sgstTurnOverSalesItems = sgstTurnOverSalesItems;
	}

	public String[] getSgstTurnOverBaseProduction() {
		return sgstTurnOverBaseProduction;
	}

	public void setSgstTurnOverBaseProduction(String[] sgstTurnOverBaseProduction) {
		this.sgstTurnOverBaseProduction = sgstTurnOverBaseProduction;
	}

	public String getDurationFinYr() {
		return durationFinYr;
	}

	public void setDurationFinYr(String durationFinYr) {
		this.durationFinYr = durationFinYr;
	}


	public String getFinancialYrPeriod() {
		return financialYrPeriod;
	}

	public void setFinancialYrPeriod(String financialYrPeriod) {
		this.financialYrPeriod = financialYrPeriod;
	}

	public String getTurnoverOfProduction() {
		return turnoverOfProduction;
	}

	public void setTurnoverOfProduction(String turnoverOfProduction) {
		this.turnoverOfProduction = turnoverOfProduction;
	}

	public String getTurnOverPartFullYrDivers() {
		return turnOverPartFullYrDivers;
	}

	public void setTurnOverPartFullYrDivers(String turnOverPartFullYrDivers) {
		this.turnOverPartFullYrDivers = turnOverPartFullYrDivers;
	}

	public String getIncreTurnover() {
		return increTurnover;
	}

	public void setIncreTurnover(String increTurnover) {
		this.increTurnover = increTurnover;
	}

	public String getTtlNetSGSTPaidFinYr() {
		return ttlNetSGSTPaidFinYr;
	}

	public void setTtlNetSGSTPaidFinYr(String ttlNetSGSTPaidFinYr) {
		this.ttlNetSGSTPaidFinYr = ttlNetSGSTPaidFinYr;
	}

	public String getTtlNetSGSTPaidIncreTurnOver() {
		return ttlNetSGSTPaidIncreTurnOver;
	}

	public void setTtlNetSGSTPaidIncreTurnOver(String ttlNetSGSTPaidIncreTurnOver) {
		this.ttlNetSGSTPaidIncreTurnOver = ttlNetSGSTPaidIncreTurnOver;
	}

	public String getAmtNetSGSTReimCliam() {
		return amtNetSGSTReimCliam;
	}

	public void setAmtNetSGSTReimCliam(String amtNetSGSTReimCliam) {
		this.amtNetSGSTReimCliam = amtNetSGSTReimCliam;
	}

	public String getAmtOfNetSGSTReimEligibility() {
		return amtOfNetSGSTReimEligibility;
	}

	public void setAmtOfNetSGSTReimEligibility(String amtOfNetSGSTReimEligibility) {
		this.amtOfNetSGSTReimEligibility = amtOfNetSGSTReimEligibility;
	}

	public String getTurnOverBaseProdObserve() {
		return turnOverBaseProdObserve;
	}

	public void setTurnOverBaseProdObserve(String turnOverBaseProdObserve) {
		this.turnOverBaseProdObserve = turnOverBaseProdObserve;
	}

	public String getProposalForECObserv() {
		return proposalForECObserv;
	}

	public void setProposalForECObserv(String proposalForECObserv) {
		this.proposalForECObserv = proposalForECObserv;
	}

	public String getProposalForDateOfAdmiss() {
		return proposalForDateOfAdmiss;
	}

	public void setProposalForDateOfAdmiss(String proposalForDateOfAdmiss) {
		this.proposalForDateOfAdmiss = proposalForDateOfAdmiss;
	}

	public String getProposalOfECTtlEligb() {
		return proposalOfECTtlEligb;
	}

	public void setProposalOfECTtlEligb(String proposalOfECTtlEligb) {
		this.proposalOfECTtlEligb = proposalOfECTtlEligb;
	}

	
	
	
	

}
