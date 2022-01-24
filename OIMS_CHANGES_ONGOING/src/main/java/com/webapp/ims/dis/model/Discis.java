/**
 * Author:: Sachin
* Created on:: 
 */
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
@Table(name = "Dis_Cis_Detail", schema = "loc")
public class Discis implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "DIS_CIS_ID")
	private String discisId;

	@Column(name = "DIS_APC_ID")
	private String disAppId;

	@Column(name = "DIS_BANK_NAME")
	private String bankname;

	@Column(name = "DIS_BANK_ADD")
	private String bankadd;

	public float getRoi() {
		return roi;
	}

	public void setRoi(float roi) {
		this.roi = roi;
	}

	@Column(name = "DIS_TOTAL")
	private long total;

	@Column(name = "DIS_BANKCERTLETTER")
	private String bankcertletter;

	@Column(name = "DIS_BANKCERT")
	private String bankcert;

	@Column(name = "DIS_SECTIONLETTER")
	private String sectionletter;

	@Column(name = "DIS_PNMLAON")
	private long pnmloan;

	@Column(name = "AmtInv_Plant_Machin")
	private Long amtInvPlantMachin;

	@Column(name = "DIS_Total_INT")
	private long totalint;

	@Column(name = "DIS_ROI")
	private float roi;

	@Column(name = "DIS_SANCTIONDATE")
	private String sanctiondate;

	@Column(name = "DIS_DISLOAN")
	private String disbursedloan;

	@Column(name = "DIS_CERTIFYLOAN")
	private String certifyingLoan;

	@Column(name = "DIS_NODEFAULT")
	private String auditedAccounts;

	@Column(name = "DIS_APC_Create_By")
	private String createBy;

	@Column(name = "DIS_CIS_Create_Date")
	@Temporal(TemporalType.TIMESTAMP)
	private Date createDate;

	@Column(name = "DIS_CIS_Modified_By")
	private String modifiedBy;

	@Column(name = "DIS_CIS_Modify_Date")
	@Temporal(TemporalType.TIMESTAMP)
	private Date modifiyDate;

	@Column(name = "STATUS")
	private String status;

	private transient String fiBankCertificate;

	private transient String sectionletterDocbase64File;
	private transient String certifyingLoanDocbase64File;
	private transient String auditedAccountsDocbase64File;
	private transient String fiBankCertificateDocbase64File;

//	List Column

	@Column(name = "DIS_CIS_YearI")
	private String yearI;

	@Column(name = "DIS_CIS_firstYTI")
	private String firstYTI;

	@Column(name = "DIS_CIS_firstYP")
	private String firstYP;

	@Column(name = "DIS_CIS_firstYI")
	private long firstYI;

	@Column(name = "DIS_CIS_firstYAmtIntSubsidy")
	private String firstYAmtIntSubsidy;

	@Column(name = "DIS_CIS_yearII")
	private String yearII;

	@Column(name = "DIS_CIS_secondYTI")
	private String secondYTI;

	@Column(name = "DIS_CIS_secondYP")
	private String secondYP;

	@Column(name = "DIS_CIS_secondYI")
	private long secondYI;

	@Column(name = "DIS_CIS_secondYAmtIntSubsidy")
	private String secondYAmtIntSubsidy;

	@Column(name = "DIS_CIS_yearIII")
	private String yearIII;

	@Column(name = "DIS_CIS_thirdYTI")
	private String thirdYTI;

	@Column(name = "DIS_CIS_thirdYP")
	private String thirdYP;

	@Column(name = "DIS_CIS_thirdYI")
	private long thirdYI;

	@Column(name = "DIS_CIS_thirdYAmtIntSubsidy")
	private String thirdYAmtIntSubsidy;

	@Column(name = "DIS_CIS_yearIV")
	private String yearIV;

	@Column(name = "DIS_CIS_fourthYTI")
	private String fourthYTI;

	@Column(name = "DIS_CIS_fourthYP")
	private String fourthYP;

	@Column(name = "DIS_CIS_fourthYI")
	private long fourthYI;

	@Column(name = "DIS_CIS_fourthYAmtIntSubsidy")
	private String fourthYAmtIntSubsidy;

	@Column(name = "DIS_CIS_yearV")
	private String yearV;

	@Column(name = "DIS_CIS_fifthYTI")
	private String fifthYTI;

	@Column(name = "DIS_CIS_fifthYP")
	private String fifthYP;

	@Column(name = "DIS_CIS_fifthYI")
	private long fifthYI;

	@Column(name = "DIS_CIS_fifthYAmtIntSubsidy")
	private String fifthYAmtIntSubsidy;

	// Duration/Period
	@Column(name = "Duration_Period_Fr1")
	private String durationPeriodDtFr1;
	@Column(name = "Duration_Period_To1")
	private String durationPeriodDtTo1;

	@Column(name = "Duration_Period_Fr2")
	private String durationPeriodDtFr2;
	@Column(name = "Duration_Period_To2")
	private String durationPeriodDtTo2;

	@Column(name = "Duration_Period_Fr3")
	private String durationPeriodDtFr3;
	@Column(name = "Duration_Period_To3")
	private String durationPeriodDtTo3;

	@Column(name = "Duration_Period_Fr4")
	private String durationPeriodDtFr4;
	@Column(name = "Duration_Period_To4")
	private String durationPeriodDtTo4;

	@Column(name = "Duration_Period_Fr5")
	private String durationPeriodDtFr5;
	@Column(name = "Duration_Period_To5")
	private String durationPeriodDtTo5;

	public Long getAmtInvPlantMachin() {
		return amtInvPlantMachin;
	}

	public void setAmtInvPlantMachin(Long amtInvPlantMachin) {
		this.amtInvPlantMachin = amtInvPlantMachin;
	}

	public String getYearI() {
		return yearI;
	}

	public void setYearI(String yearI) {
		this.yearI = yearI;
	}

	public String getFirstYTI() {
		return firstYTI;
	}

	public void setFirstYTI(String firstYTI) {
		this.firstYTI = firstYTI;
	}

	public String getFirstYP() {
		return firstYP;
	}

	public void setFirstYP(String firstYP) {
		this.firstYP = firstYP;
	}

	public long getFirstYI() {
		return firstYI;
	}

	public void setFirstYI(long firstYI) {
		this.firstYI = firstYI;
	}

	public String getFirstYAmtIntSubsidy() {
		return firstYAmtIntSubsidy;
	}

	public void setFirstYAmtIntSubsidy(String firstYAmtIntSubsidy) {
		this.firstYAmtIntSubsidy = firstYAmtIntSubsidy;
	}

	public String getYearII() {
		return yearII;
	}

	public void setYearII(String yearII) {
		this.yearII = yearII;
	}

	public String getSecondYTI() {
		return secondYTI;
	}

	public void setSecondYTI(String secondYTI) {
		this.secondYTI = secondYTI;
	}

	public String getSecondYP() {
		return secondYP;
	}

	public void setSecondYP(String secondYP) {
		this.secondYP = secondYP;
	}

	public long getSecondYI() {
		return secondYI;
	}

	public void setSecondYI(long secondYI) {
		this.secondYI = secondYI;
	}

	public String getSecondYAmtIntSubsidy() {
		return secondYAmtIntSubsidy;
	}

	public void setSecondYAmtIntSubsidy(String secondYAmtIntSubsidy) {
		this.secondYAmtIntSubsidy = secondYAmtIntSubsidy;
	}

	public String getYearIII() {
		return yearIII;
	}

	public void setYearIII(String yearIII) {
		this.yearIII = yearIII;
	}

	public String getThirdYTI() {
		return thirdYTI;
	}

	public void setThirdYTI(String thirdYTI) {
		this.thirdYTI = thirdYTI;
	}

	public String getThirdYP() {
		return thirdYP;
	}

	public void setThirdYP(String thirdYP) {
		this.thirdYP = thirdYP;
	}

	public long getThirdYI() {
		return thirdYI;
	}

	public void setThirdYI(long thirdYI) {
		this.thirdYI = thirdYI;
	}

	public String getThirdYAmtIntSubsidy() {
		return thirdYAmtIntSubsidy;
	}

	public void setThirdYAmtIntSubsidy(String thirdYAmtIntSubsidy) {
		this.thirdYAmtIntSubsidy = thirdYAmtIntSubsidy;
	}

	public String getYearIV() {
		return yearIV;
	}

	public void setYearIV(String yearIV) {
		this.yearIV = yearIV;
	}

	public String getFourthYTI() {
		return fourthYTI;
	}

	public void setFourthYTI(String fourthYTI) {
		this.fourthYTI = fourthYTI;
	}

	public String getFourthYP() {
		return fourthYP;
	}

	public void setFourthYP(String fourthYP) {
		this.fourthYP = fourthYP;
	}

	public long getFourthYI() {
		return fourthYI;
	}

	public void setFourthYI(long fourthYI) {
		this.fourthYI = fourthYI;
	}

	public String getFourthYAmtIntSubsidy() {
		return fourthYAmtIntSubsidy;
	}

	public void setFourthYAmtIntSubsidy(String fourthYAmtIntSubsidy) {
		this.fourthYAmtIntSubsidy = fourthYAmtIntSubsidy;
	}

	public String getYearV() {
		return yearV;
	}

	public void setYearV(String yearV) {
		this.yearV = yearV;
	}

	public String getFifthYTI() {
		return fifthYTI;
	}

	public void setFifthYTI(String fifthYTI) {
		this.fifthYTI = fifthYTI;
	}

	public String getFifthYP() {
		return fifthYP;
	}

	public void setFifthYP(String fifthYP) {
		this.fifthYP = fifthYP;
	}

	public long getFifthYI() {
		return fifthYI;
	}

	public void setFifthYI(long fifthYI) {
		this.fifthYI = fifthYI;
	}

	public String getFifthYAmtIntSubsidy() {
		return fifthYAmtIntSubsidy;
	}

	public void setFifthYAmtIntSubsidy(String fifthYAmtIntSubsidy) {
		this.fifthYAmtIntSubsidy = fifthYAmtIntSubsidy;
	}

	public String getFiBankCertificate() {
		return fiBankCertificate;
	}

	public void setFiBankCertificate(String fiBankCertificate) {
		this.fiBankCertificate = fiBankCertificate;
	}

	public String getSectionletterDocbase64File() {
		return sectionletterDocbase64File;
	}

	public void setSectionletterDocbase64File(String sectionletterDocbase64File) {
		this.sectionletterDocbase64File = sectionletterDocbase64File;
	}

	public String getCertifyingLoanDocbase64File() {
		return certifyingLoanDocbase64File;
	}

	public void setCertifyingLoanDocbase64File(String certifyingLoanDocbase64File) {
		this.certifyingLoanDocbase64File = certifyingLoanDocbase64File;
	}

	public String getAuditedAccountsDocbase64File() {
		return auditedAccountsDocbase64File;
	}

	public void setAuditedAccountsDocbase64File(String auditedAccountsDocbase64File) {
		this.auditedAccountsDocbase64File = auditedAccountsDocbase64File;
	}

	public String getFiBankCertificateDocbase64File() {
		return fiBankCertificateDocbase64File;
	}

	public void setFiBankCertificateDocbase64File(String fiBankCertificateDocbase64File) {
		this.fiBankCertificateDocbase64File = fiBankCertificateDocbase64File;
	}

	public String getDiscisId() {
		return discisId;
	}

	public void setDiscisId(String discisId) {
		this.discisId = discisId;
	}

	public String getDisAppId() {
		return disAppId;
	}

	public void setDisAppId(String disAppId) {
		this.disAppId = disAppId;
	}

	public String getBankname() {
		return bankname;
	}

	public void setBankname(String bankname) {
		this.bankname = bankname;
	}

	public String getBankadd() {
		return bankadd;
	}

	public void setBankadd(String bankadd) {
		this.bankadd = bankadd;
	}

	public long getTotal() {
		return total;
	}

	public void setTotal(long total) {
		this.total = total;
	}

	public String getSectionletter() {
		return sectionletter;
	}

	public void setSectionletter(String sectionletter) {
		this.sectionletter = sectionletter;
	}

	public String getBankcert() {
		return bankcert;
	}

	public void setBankcert(String bankcert) {
		this.bankcert = bankcert;
	}

	public String getBankcertletter() {
		return bankcertletter;
	}

	public void setBankcertletter(String bankcertletter) {
		this.bankcertletter = bankcertletter;
	}

	public long getPnmloan() {
		return pnmloan;
	}

	public void setPnmloan(long pnmloan) {
		this.pnmloan = pnmloan;
	}

	public long getTotalint() {
		return totalint;
	}

	public void setTotalint(long totalint) {
		this.totalint = totalint;
	}

	public void setRoi(Long roi) {
		this.roi = roi;
	}

	public String getSanctiondate() {
		return sanctiondate;
	}

	public void setSanctiondate(String sanctiondate) {
		this.sanctiondate = sanctiondate;
	}

	public String getDisbursedloan() {
		return disbursedloan;
	}

	public void setDisbursedloan(String disbursedloan) {
		this.disbursedloan = disbursedloan;
	}

	public String getCertifyingLoan() {
		return certifyingLoan;
	}

	public void setCertifyingLoan(String certifyingLoan) {
		this.certifyingLoan = certifyingLoan;
	}

	public String getAuditedAccounts() {
		return auditedAccounts;
	}

	public void setAuditedAccounts(String auditedAccounts) {
		this.auditedAccounts = auditedAccounts;
	}

	public String getCreateBy() {
		return createBy;
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getModifiedBy() {
		return modifiedBy;
	}

	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	public Date getModifiyDate() {
		return modifiyDate;
	}

	public void setModifiyDate(Date modifiyDate) {
		this.modifiyDate = modifiyDate;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getDurationPeriodDtFr1() {
		return durationPeriodDtFr1;
	}

	public void setDurationPeriodDtFr1(String durationPeriodDtFr1) {
		this.durationPeriodDtFr1 = durationPeriodDtFr1;
	}

	public String getDurationPeriodDtTo1() {
		return durationPeriodDtTo1;
	}

	public void setDurationPeriodDtTo1(String durationPeriodDtTo1) {
		this.durationPeriodDtTo1 = durationPeriodDtTo1;
	}

	public String getDurationPeriodDtFr2() {
		return durationPeriodDtFr2;
	}

	public void setDurationPeriodDtFr2(String durationPeriodDtFr2) {
		this.durationPeriodDtFr2 = durationPeriodDtFr2;
	}

	public String getDurationPeriodDtTo2() {
		return durationPeriodDtTo2;
	}

	public void setDurationPeriodDtTo2(String durationPeriodDtTo2) {
		this.durationPeriodDtTo2 = durationPeriodDtTo2;
	}

	public String getDurationPeriodDtFr3() {
		return durationPeriodDtFr3;
	}

	public void setDurationPeriodDtFr3(String durationPeriodDtFr3) {
		this.durationPeriodDtFr3 = durationPeriodDtFr3;
	}

	public String getDurationPeriodDtTo3() {
		return durationPeriodDtTo3;
	}

	public void setDurationPeriodDtTo3(String durationPeriodDtTo3) {
		this.durationPeriodDtTo3 = durationPeriodDtTo3;
	}

	public String getDurationPeriodDtFr4() {
		return durationPeriodDtFr4;
	}

	public void setDurationPeriodDtFr4(String durationPeriodDtFr4) {
		this.durationPeriodDtFr4 = durationPeriodDtFr4;
	}

	public String getDurationPeriodDtTo4() {
		return durationPeriodDtTo4;
	}

	public void setDurationPeriodDtTo4(String durationPeriodDtTo4) {
		this.durationPeriodDtTo4 = durationPeriodDtTo4;
	}

	public String getDurationPeriodDtFr5() {
		return durationPeriodDtFr5;
	}

	public void setDurationPeriodDtFr5(String durationPeriodDtFr5) {
		this.durationPeriodDtFr5 = durationPeriodDtFr5;
	}

	public String getDurationPeriodDtTo5() {
		return durationPeriodDtTo5;
	}

	public void setDurationPeriodDtTo5(String durationPeriodDtTo5) {
		this.durationPeriodDtTo5 = durationPeriodDtTo5;
	}

}
