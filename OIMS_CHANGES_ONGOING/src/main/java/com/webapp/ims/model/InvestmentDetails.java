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
@Table(name = "Investment_Details", schema = "loc")
public class InvestmentDetails implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	@Column(name = "INV_ID")
	private String invId;

	@Column(name = "INV_land_Cost")
	//@NotNull
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
	@NotEmpty
	private String invIndType;
	@Column(name = "INV_Pinv_Dt")
	//@NotNull
	@Temporal(TemporalType.DATE)
	// @FutureOrPresent
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
	// @FutureOrPresent
	//@NotNull
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

	@Temporal(TemporalType.DATE)
	@FutureOrPresent
	private transient Date pwPropProductDate;
	private transient Long pwFci;
	private transient Integer pwPhaseNo;
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
	private transient String particulars;
	private transient String particularmom;
	private transient Integer phasenoic;
	private transient Long invFciIc;
	private transient String otid;
	private transient String pwic;
	private transient String pwmom;

	public String getPwmom() {
		return pwmom;
	}

	public void setPwmom(String pwmom) {
		this.pwmom = pwmom;
	}

	public String getPwic() {
		return pwic;
	}

	public void setPwic(String pwic) {
		this.pwic = pwic;
	}

	public String getOtid() {
		return otid;
	}

	public void setOtid(String otid) {
		this.otid = otid;
	}

	public Long getInvFciIc() {
		return invFciIc;
	}

	public void setInvFciIc(Long invFciIc) {
		this.invFciIc = invFciIc;
	}

	public Integer getPhasenoic() {
		return phasenoic;
	}

	public void setPhasenoic(Integer phasenoic) {
		this.phasenoic = phasenoic;
	}

	public String getParticularmom() {
		return particularmom;
	}

	public void setParticularmom(String particularmom) {
		this.particularmom = particularmom;
	}

	private transient Integer pwPhaseNoOT;

	public Integer getPwPhaseNoOT() {
		return pwPhaseNoOT;
	}

	public void setPwPhaseNoOT(Integer pwPhaseNoOT) {
		this.pwPhaseNoOT = pwPhaseNoOT;
	}

	public Integer getPwPhaseNoMOM() {
		return pwPhaseNoMOM;
	}

	public void setPwPhaseNoMOM(Integer pwPhaseNoMOM) {
		this.pwPhaseNoMOM = pwPhaseNoMOM;
	}

	private transient Integer pwPhaseNoMOM;

	public String getParticulars() {
		return particulars;
	}

	public void setParticulars(String particulars) {
		this.particulars = particulars;
	}

	private transient Long proposedInvestmentInProject;
	private transient Long proposedInvestmentInProjectmom;

	public Long getProposedInvestmentInProjectmom() {
		return proposedInvestmentInProjectmom;
	}

	public void setProposedInvestmentInProjectmom(Long proposedInvestmentInProjectmom) {
		this.proposedInvestmentInProjectmom = proposedInvestmentInProjectmom;
	}

	public Long getProposedInvestmentInProject() {
		return proposedInvestmentInProject;
	}

	public void setProposedInvestmentInProject(Long proposedInvestmentInProject) {
		this.proposedInvestmentInProject = proposedInvestmentInProject;
	}

	public Integer getPwPhaseNo() {
		return pwPhaseNo;
	}

	public void setPwPhaseNo(Integer pwPhaseNo) {
		this.pwPhaseNo = pwPhaseNo;
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

	public String getInvregorlicbase64File() {
		return invregorlicbase64File;
	}

	public void setInvregorlicbase64File(String invregorlicbase64File) {
		this.invregorlicbase64File = invregorlicbase64File;
	}

	public InvestmentDetails() {
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
