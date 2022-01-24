package com.webapp.ims.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.CreationTimestamp;

@Entity
@Table(name = "Dept_Investment_Details", schema = "loc")
public class EvaluateInvestmentDetails implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	@Column(name = "INV_ID")
	private String invId;
	@Column(name = "INV_land_Cost")
	//@NotNull
//	@NotNull
	private Long invLandCost;
	@Column(name = "INV_Bldg_Cost")
	//@NotNull
	private Long invBuildingCost;
	@Column(name = "INV_Plantmac_Cost")
	//@NotNull
	private Long invPlantAndMachCost;
	@Column(name = "INV_Proposed_Fix_Cap_Inv")
	//@NotNull
	private Long invFci;
	@Column(name = "INV_Misc_Fix_Asset")
	//@NotNull
	private Long invOtherCost;
	@Column(name = "INV_Ttlproj_Cost")
	//@NotNull
	private Long invTotalProjCost;
	@Column(name = "INV_Ind_Type")
	//@NotEmpty
	private String invIndType;
	@Column(name = "INV_Pinv_Dt")
	//@NotNull
	@Temporal(TemporalType.DATE)
	@CreationTimestamp
	private Date propCommProdDate;

	@Column(name = "INV_Pcomm_Prod_Dt")
	//@NotNull
	@Temporal(TemporalType.DATE)
	private Date invCommenceDate;

	@Column(name = "INV_APC_ID")
	private String applId;
	@Column(name = "INV_Created_By")
	private String invCreatedBy;
	@Column(name = "INV_Modified_By")
	private String invModifiedBy;
	@Column(name = "INV_Status")
	private String invStatus;
	@Column(name = "PW_Apply")
	private String pwApply;
	@Column(name = "INV_Costesccontg")
	private Long invcostEscContg;
	@Column(name = "INV_Prelpreopexp")
	private Long invprelPreopExp;
	@Column(name = "INV_Intconsperiod")
	private Long invIntCons;
	@Column(name = "INV_Workcapital")
	private Long invmmwCap;
	@Column(name = "INV_Firstdateinvest")
	@Temporal(TemporalType.DATE)
	private Date invfdInvest;
	@Column(name = "INV_Priorcutamt")
	private Long invPriorCutAmt;
	@Column(name = "INV_Cutprodamt")
	private Long invCutoffProdAmt;
	@Column(name = "INV_Comprodamt")
	private Long invComProdAmt;
	@Column(name = "INV_Priorfirstdateamt")
	private Long invPriorFdiAmt;
	@Column(name = "INV_Eqshcapital")
	private Long invEqShCapital;
	@Column(name = "INV_Eqintcashaccrl")
	private Long invEqIntCashAccrl;
	@Column(name = "INV_Eqintfreeunsecloan")
	private Long invEqIntFreeUnsecLoan;
	@Column(name = "INV_Eqsecuritydeposit")
	private Long invEqSecDept;
	@Column(name = "INV_Eqadvdealer")
	private Long invEqAdvDealer;
	@Column(name = "INV_Debtfininst")
	private Long invDebtFi;
	@Column(name = "INV_Debtbank")
	private Long invDebtBank;
	@Column(name = "INV_Fintotal")
	private Long invFinTotal;
	@Column(name = "Inv_Filename")
	private String invFilename;
	@Column(name = "Inv_Filedata")
	private byte[] invFiledata;

	@Column(name = "INV_Modify_Date")
	@Temporal(TemporalType.TIMESTAMP)
	private Date invModifiyDate;

	@Column(name = "INV_Capinvamt")
	private Long capInvAmt;

	@Column(name = "INV_Regi_Licence")
	private String regiOrLicense;

	@Column(name = "INV_Reg_Licen_Fname")
	private String regiOrLicenseFileName;

	@Column(name = "INV_Ind_Licence_Upload")
	private byte[] regiOrLicenseFileData;

	// Evaluate Application Form Related columns

	@Column(name = "INV_Landcost_Remarks")
	private String landcostRemarks;
	@Column(name = "INV_Building_Remarks")
	private String buildingRemarks;
	@Column(name = "INV_Plant_Remarks")
	private String plantAndMachRemarks;
	@Column(name = "INV_Fixedasset_Remarks")
	private String fixedAssetRemarks;

	@Column(name = "INV_IEM_Number")
	private String invIemNumber;
	@Column(name = "INV_Govt_Equity")
	private String invGovtEquity;
	@Column(name = "INV_Eligcap_Invest")
	private String invEligcapInvest;
	@Column(name = "INV_CAStatutory_Amount")
	private Long invCAStatutoryAmt;
	@Column(name = "INV_CAStatutory_Date")

	/*
	 * @Temporal(TemporalType.DATE)
	 * 
	 * @DateTimeFormat(pattern = "dd/MM/yyyy")
	 */

	private String invCAStatutoryDate;

	@Column(name = "INV_Landcost_FCI")
	private String landcostFci;
	@Column(name = "INV_Building_FCI")
	private String buildingFci;
	@Column(name = "INV_Plant_FCI")
	private String plantAndMachFci;
	@Column(name = "INV_Fixedasset_FCI")
	private String fixedAssetFci;

	@Column(name = "INV_Landcost_IIEPP")
	private Long landcostIIEPP;
	@Column(name = "INV_Building_IIEPP")
	private Long buildingIIEPP;
	@Column(name = "INV_Plant_IIEPP")
	private Long plantAndMachIIEPP;
	@Column(name = "INV_Fixedasset_IIEPP")
	private Long fixedAssetIIEPP;

	// Observation Column
	@Column(name = "INV_CatIndusUndtObserv")
	private String catIndusUndtObserv;

	@Column(name = "INV_PropsPlntMcnryCost_Observ")
	private String propsPlntMcnryCostObserv;

	public String getPropsPlntMcnryCostObserv() {
		return propsPlntMcnryCostObserv;
	}

	public void setPropsPlntMcnryCostObserv(String propsPlntMcnryCostObserv) {
		this.propsPlntMcnryCostObserv = propsPlntMcnryCostObserv;
	}

	@Column(name = "INV_Cutofdate_Observ")
	private String optcutofdateobserv;

	@Column(name = "INV_dateofCumProd_Observ")
	private String dateofComProdObserv;

	@Column(name = "INV_detailProjReport_Observ")
	private String detailProjReportObserv;

	@Column(name = "INV_PropCapInv_Observ")
	private String propCapInvObserv;

	@Column(name = "INV_TotlCostProj_Observ")
	private String totlCostProjObserv;

	@Column(name = "INV_Mof_Observ")
	private String mofObserv;

	@Column(name = "INV_IemReg_Observ")
	private String IemRegObserv;

	@Column(name = "INV_Indusundrtk_Observ")
	private String indusUntkObserv;

	@Column(name = "INV_EligblInvPerdLarge_Observ")
	private String eligblInvPerdLargeObserv;

	@Column(name = "INV_EligblInvPerdMega_Observ")
	private String eligblInvPerdMegaObserv;

	@Column(name = "INV_EligblInvPerdSupermega_Observ")
	private String eligblInvPerdSupermegaObserv;

	@Column(name = "INV_EligblCapInv_Observ")
	private String eligblCapInvObserv;

	@Column(name = "INV_ProjPhases_Observ")
	private String projPhasesObserv;

	@Column(name = "INV_CaStatutory_Observ")
	private String caStatutoryObserv;

	@Column(name = "INV_AuthSignatory_Observ")
	private String authSignatoryObserv;

	@Column(name = "INV_AppFormat_Observ")
	private String appformatObserv;

	public String getEligblInvPerdLargeObserv() {
		return eligblInvPerdLargeObserv;
	}

	public void setEligblInvPerdLargeObserv(String eligblInvPerdLargeObserv) {
		this.eligblInvPerdLargeObserv = eligblInvPerdLargeObserv;
	}

	public String getEligblInvPerdSupermegaObserv() {
		return eligblInvPerdSupermegaObserv;
	}

	public void setEligblInvPerdSupermegaObserv(String eligblInvPerdSupermegaObserv) {
		this.eligblInvPerdSupermegaObserv = eligblInvPerdSupermegaObserv;
	}

	public String getCaStatutoryObserv() {
		return caStatutoryObserv;
	}

	public void setCaStatutoryObserv(String caStatutoryObserv) {
		this.caStatutoryObserv = caStatutoryObserv;
	}

	public String getAuthSignatoryObserv() {
		return authSignatoryObserv;
	}

	public void setAuthSignatoryObserv(String authSignatoryObserv) {
		this.authSignatoryObserv = authSignatoryObserv;
	}

	public String getAppformatObserv() {
		return appformatObserv;
	}

	public void setAppformatObserv(String appformatObserv) {
		this.appformatObserv = appformatObserv;
	}

	public String getEligblInvPerdMegaObserv() {
		return eligblInvPerdMegaObserv;
	}

	public void setEligblInvPerdMegaObserv(String eligblInvPerdMegaObserv) {
		this.eligblInvPerdMegaObserv = eligblInvPerdMegaObserv;
	}

	public String getEligblCapInvObserv() {
		return eligblCapInvObserv;
	}

	public void setEligblCapInvObserv(String eligblCapInvObserv) {
		this.eligblCapInvObserv = eligblCapInvObserv;
	}

	public String getProjPhasesObserv() {
		return projPhasesObserv;
	}

	public void setProjPhasesObserv(String projPhasesObserv) {
		this.projPhasesObserv = projPhasesObserv;
	}

	public String getMofObserv() {
		return mofObserv;
	}

	public void setMofObserv(String mofObserv) {
		this.mofObserv = mofObserv;
	}

	public String getIemRegObserv() {
		return IemRegObserv;
	}

	public void setIemRegObserv(String iemRegObserv) {
		IemRegObserv = iemRegObserv;
	}

	public String getIndusUntkObserv() {
		return indusUntkObserv;
	}

	public void setIndusUntkObserv(String indusUntkObserv) {
		this.indusUntkObserv = indusUntkObserv;
	}

	public String getPropCapInvObserv() {
		return propCapInvObserv;
	}

	public void setPropCapInvObserv(String propCapInvObserv) {
		this.propCapInvObserv = propCapInvObserv;
	}

	public String getTotlCostProjObserv() {
		return totlCostProjObserv;
	}

	public void setTotlCostProjObserv(String totlCostProjObserv) {
		this.totlCostProjObserv = totlCostProjObserv;
	}

	public String getOptcutofdateobserv() {
		return optcutofdateobserv;
	}

	public void setOptcutofdateobserv(String optcutofdateobserv) {
		this.optcutofdateobserv = optcutofdateobserv;
	}

	public String getDateofComProdObserv() {
		return dateofComProdObserv;
	}

	public void setDateofComProdObserv(String dateofComProdObserv) {
		this.dateofComProdObserv = dateofComProdObserv;
	}

	public String getDetailProjReportObserv() {
		return detailProjReportObserv;
	}

	public void setDetailProjReportObserv(String detailProjReportObserv) {
		this.detailProjReportObserv = detailProjReportObserv;
	}

	public String getCatIndusUndtObserv() {
		return catIndusUndtObserv;
	}

	public void setCatIndusUndtObserv(String catIndusUndtObserv) {
		this.catIndusUndtObserv = catIndusUndtObserv;
	}

	public String getLandcostFci() {
		return landcostFci;
	}

	public void setLandcostFci(String landcostFci) {
		this.landcostFci = landcostFci;
	}

	public String getBuildingFci() {
		return buildingFci;
	}

	public void setBuildingFci(String buildingFci) {
		this.buildingFci = buildingFci;
	}

	public String getPlantAndMachFci() {
		return plantAndMachFci;
	}

	public void setPlantAndMachFci(String plantAndMachFci) {
		this.plantAndMachFci = plantAndMachFci;
	}

	public String getFixedAssetFci() {
		return fixedAssetFci;
	}

	public void setFixedAssetFci(String fixedAssetFci) {
		this.fixedAssetFci = fixedAssetFci;
	}

	public Long getLandcostIIEPP() {
		return landcostIIEPP;
	}

	public void setLandcostIIEPP(Long landcostIIEPP) {
		this.landcostIIEPP = landcostIIEPP;
	}

	public Long getBuildingIIEPP() {
		return buildingIIEPP;
	}

	public void setBuildingIIEPP(Long buildingIIEPP) {
		this.buildingIIEPP = buildingIIEPP;
	}

	public Long getPlantAndMachIIEPP() {
		return plantAndMachIIEPP;
	}

	public void setPlantAndMachIIEPP(Long plantAndMachIIEPP) {
		this.plantAndMachIIEPP = plantAndMachIIEPP;
	}

	public Long getFixedAssetIIEPP() {
		return fixedAssetIIEPP;
	}

	public void setFixedAssetIIEPP(Long fixedAssetIIEPP) {
		this.fixedAssetIIEPP = fixedAssetIIEPP;
	}

	public String getInvGovtEquity() {
		return invGovtEquity;
	}

	public void setInvGovtEquity(String invGovtEquity) {
		this.invGovtEquity = invGovtEquity;
	}

	public String getInvEligcapInvest() {
		return invEligcapInvest;
	}

	public void setInvEligcapInvest(String invEligcapInvest) {
		this.invEligcapInvest = invEligcapInvest;
	}

	public Long getInvCAStatutoryAmt() {
		return invCAStatutoryAmt;
	}

	public void setInvCAStatutoryAmt(Long invCAStatutoryAmt) {
		this.invCAStatutoryAmt = invCAStatutoryAmt;
	}

	public String getInvCAStatutoryDate() {
		return invCAStatutoryDate;
	}

	public void setInvCAStatutoryDate(String invCAStatutoryDate) {
		this.invCAStatutoryDate = invCAStatutoryDate;
	}

	public String getInvIemNumber() {
		return invIemNumber;
	}

	public void setInvIemNumber(String invIemNumber) {
		this.invIemNumber = invIemNumber;
	}

	public String getLandcostRemarks() {
		return landcostRemarks;
	}

	public void setLandcostRemarks(String landcostRemarks) {
		this.landcostRemarks = landcostRemarks;
	}

	public String getBuildingRemarks() {
		return buildingRemarks;
	}

	public void setBuildingRemarks(String buildingRemarks) {
		this.buildingRemarks = buildingRemarks;
	}

	public String getPlantAndMachRemarks() {
		return plantAndMachRemarks;
	}

	public void setPlantAndMachRemarks(String plantAndMachRemarks) {
		this.plantAndMachRemarks = plantAndMachRemarks;
	}

	public String getFixedAssetRemarks() {
		return fixedAssetRemarks;
	}

	public void setFixedAssetRemarks(String fixedAssetRemarks) {
		this.fixedAssetRemarks = fixedAssetRemarks;
	}

	public byte[] getRegiOrLicenseFileData() {
		return regiOrLicenseFileData;
	}

	public void setRegiOrLicenseFileData(byte[] regiOrLicenseFileData) {
		this.regiOrLicenseFileData = regiOrLicenseFileData;
	}

	public String getRegiOrLicense() {
		return regiOrLicense;
	}

	public void setRegiOrLicense(String regiOrLicense) {
		this.regiOrLicense = regiOrLicense;
	}

	public String getRegiOrLicenseFileName() {
		return regiOrLicenseFileName;
	}

	public void setRegiOrLicenseFileName(String regiOrLicenseFileName) {
		this.regiOrLicenseFileName = regiOrLicenseFileName;
	}

	@Temporal(TemporalType.DATE)
	@FutureOrPresent
	private transient Date pwPropProductDate;
	private transient Long pwFci;
	private transient Long pwCutoffProdAmt;
	private transient String brkupParameter;
	private transient Long brkupAmount;
	private transient String mofParameter;
	private transient Long mofAmount;
	private transient String invbase64File;
	private transient String invregorlicbase64File;
	private transient String pwProductName;
	private transient Long pwCapacity;
	private transient String pwUnit;

	public String getInvregorlicbase64File() {
		return invregorlicbase64File;
	}

	public void setInvregorlicbase64File(String invregorlicbase64File) {
		this.invregorlicbase64File = invregorlicbase64File;
	}

	public EvaluateInvestmentDetails() {
		super();
		this.invfdInvest = new Date();
	}

	public String getPwProductName() {
		return pwProductName;
	}

	public void setPwProductName(String pwProductName) {
		this.pwProductName = pwProductName;
	}

	public Long getPwCapacity() {
		return pwCapacity;
	}

	public void setPwCapacity(Long pwCapacity) {
		this.pwCapacity = pwCapacity;
	}

	public String getPwUnit() {
		return pwUnit;
	}

	public void setPwUnit(String pwUnit) {
		this.pwUnit = pwUnit;
	}

	public Long getCapInvAmt() {
		return capInvAmt;
	}

	public void setCapInvAmt(Long capInvAmt) {
		this.capInvAmt = capInvAmt;
	}

	public String getInvFilename() {
		return invFilename;
	}

	public void setInvFilename(String invFilename) {
		this.invFilename = invFilename;
	}

	public byte[] getInvFiledata() {
		return invFiledata;
	}

	public void setInvFiledata(byte[] invFiledata) {
		this.invFiledata = invFiledata;
	}

	public String getInvbase64File() {
		return invbase64File;
	}

	public void setInvbase64File(String invbase64File) {
		this.invbase64File = invbase64File;
	}

	public Date getInvModifiyDate() {
		return invModifiyDate;
	}

	public void setInvModifiyDate(Date invModifiyDate) {
		this.invModifiyDate = invModifiyDate;
	}

	public Long getInvCutoffProdAmt() {
		return invCutoffProdAmt;
	}

	public void setInvCutoffProdAmt(Long invCutoffProdAmt) {
		this.invCutoffProdAmt = invCutoffProdAmt;
	}

	public String getMofParameter() {
		return mofParameter;
	}

	public void setMofParameter(String mofParameter) {
		this.mofParameter = mofParameter;
	}

	public Long getMofAmount() {
		return mofAmount;
	}

	public void setMofAmount(Long mofAmount) {
		this.mofAmount = mofAmount;
	}

	public String getBrkupParameter() {
		return brkupParameter;
	}

	public void setBrkupParameter(String brkupParameter) {
		this.brkupParameter = brkupParameter;
	}

	public Long getBrkupAmount() {
		return brkupAmount;
	}

	public void setBrkupAmount(Long brkupAmount) {
		this.brkupAmount = brkupAmount;
	}

	public Long getInvFinTotal() {
		return invFinTotal;
	}

	public void setInvFinTotal(Long invFinTotal) {
		this.invFinTotal = invFinTotal;
	}

	public Long getInvcostEscContg() {
		return invcostEscContg;
	}

	public void setInvcostEscContg(Long invcostEscContg) {
		this.invcostEscContg = invcostEscContg;
	}

	public Long getInvprelPreopExp() {
		return invprelPreopExp;
	}

	public void setInvprelPreopExp(Long invprelPreopExp) {
		this.invprelPreopExp = invprelPreopExp;
	}

	public Long getInvIntCons() {
		return invIntCons;
	}

	public void setInvIntCons(Long invIntCons) {
		this.invIntCons = invIntCons;
	}

	public Long getInvmmwCap() {
		return invmmwCap;
	}

	public void setInvmmwCap(Long invmmwCap) {
		this.invmmwCap = invmmwCap;
	}

	public Date getInvfdInvest() {
		return invfdInvest;
	}

	public void setInvfdInvest(Date invfdInvest) {
		this.invfdInvest = invfdInvest;
		this.invfdInvest = new Date();
	}

	public Long getInvPriorCutAmt() {
		return invPriorCutAmt;
	}

	public void setInvPriorCutAmt(Long invPriorCutAmt) {
		this.invPriorCutAmt = invPriorCutAmt;
	}

	public Long getPwCutoffProdAmt() {
		return pwCutoffProdAmt;
	}

	public void setPwCutoffProdAmt(Long pwCutoffProdAmt) {
		this.pwCutoffProdAmt = pwCutoffProdAmt;
	}

	public Long getInvComProdAmt() {
		return invComProdAmt;
	}

	public void setInvComProdAmt(Long invComProdAmt) {
		this.invComProdAmt = invComProdAmt;
	}

	public Long getInvPriorFdiAmt() {
		return invPriorFdiAmt;
	}

	public void setInvPriorFdiAmt(Long invPriorFdiAmt) {
		this.invPriorFdiAmt = invPriorFdiAmt;
	}

	public Long getInvEqShCapital() {
		return invEqShCapital;
	}

	public void setInvEqShCapital(Long invEqShCapital) {
		this.invEqShCapital = invEqShCapital;
	}

	public Long getInvEqIntCashAccrl() {
		return invEqIntCashAccrl;
	}

	public void setInvEqIntCashAccrl(Long invEqIntCashAccrl) {
		this.invEqIntCashAccrl = invEqIntCashAccrl;
	}

	public Long getInvEqIntFreeUnsecLoan() {
		return invEqIntFreeUnsecLoan;
	}

	public void setInvEqIntFreeUnsecLoan(Long invEqIntFreeUnsecLoan) {
		this.invEqIntFreeUnsecLoan = invEqIntFreeUnsecLoan;
	}

	public Long getInvEqSecDept() {
		return invEqSecDept;
	}

	public void setInvEqSecDept(Long invEqSecDept) {
		this.invEqSecDept = invEqSecDept;
	}

	public Long getInvEqAdvDealer() {
		return invEqAdvDealer;
	}

	public void setInvEqAdvDealer(Long invEqAdvDealer) {
		this.invEqAdvDealer = invEqAdvDealer;
	}

	public Long getInvDebtFi() {
		return invDebtFi;
	}

	public void setInvDebtFi(Long invDebtFi) {
		this.invDebtFi = invDebtFi;
	}

	public Long getInvDebtBank() {
		return invDebtBank;
	}

	public void setInvDebtBank(Long invDebtBank) {
		this.invDebtBank = invDebtBank;
	}

	public String getPwApply() {
		return pwApply;
	}

	public void setPwApply(String pwApply) {
		this.pwApply = pwApply;
	}

	public String getInvId() {
		return invId;
	}

	public void setInvId(String invId) {
		this.invId = invId;
	}

	public String getInvIndType() {
		return invIndType;
	}

	public void setInvIndType(String invIndType) {
		this.invIndType = invIndType;
	}

	public Date getPropCommProdDate() {
		return propCommProdDate;
	}

	public void setPropCommProdDate(Date propCommProdDate) {
		this.propCommProdDate = propCommProdDate;
	}

	public Date getInvCommenceDate() {
		return invCommenceDate;
	}

	public void setInvCommenceDate(Date invCommenceDate) {
		this.invCommenceDate = invCommenceDate;
	}

	public Date getPwPropProductDate() {
		return pwPropProductDate;
	}

	public void setPwPropProductDate(Date pwPropProductDate) {
		this.pwPropProductDate = pwPropProductDate;
	}

	public String getInvCreatedBy() {
		return invCreatedBy;
	}

	public void setInvCreatedBy(String invCreatedBy) {
		this.invCreatedBy = invCreatedBy;
	}

	public String getInvModifiedBy() {
		return invModifiedBy;
	}

	public void setInvModifiedBy(String invModifiedBy) {
		this.invModifiedBy = invModifiedBy;
	}

	public String getInvStatus() {
		return invStatus;
	}

	public void setInvStatus(String invStatus) {
		this.invStatus = invStatus;
	}

	public Long getInvLandCost() {
		return invLandCost;
	}

	public void setInvLandCost(Long invLandCost) {
		this.invLandCost = invLandCost;
	}

	public Long getInvBuildingCost() {
		return invBuildingCost;
	}

	public void setInvBuildingCost(Long invBuildingCost) {
		this.invBuildingCost = invBuildingCost;
	}

	public Long getInvPlantAndMachCost() {
		return invPlantAndMachCost;
	}

	public void setInvPlantAndMachCost(Long invPlantAndMachCost) {
		this.invPlantAndMachCost = invPlantAndMachCost;
	}

	public Long getInvFci() {
		return invFci;
	}

	public void setInvFci(Long invFci) {
		this.invFci = invFci;
	}

	public Long getInvOtherCost() {
		return invOtherCost;
	}

	public void setInvOtherCost(Long invOtherCost) {
		this.invOtherCost = invOtherCost;
	}

	public Long getPwFci() {
		return pwFci;
	}

	public void setPwFci(Long pwFci) {
		this.pwFci = pwFci;
	}

	public String getApplId() {
		return applId;
	}

	public void setApplId(String applId) {
		this.applId = applId;
	}

	public Long getInvTotalProjCost() {
		return invTotalProjCost;
	}

	public void setInvTotalProjCost(Long invTotalProjCost) {
		this.invTotalProjCost = invTotalProjCost;
	}

}