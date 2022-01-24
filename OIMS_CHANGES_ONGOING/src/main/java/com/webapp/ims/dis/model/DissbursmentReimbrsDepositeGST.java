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
@Table(name = "Dis_ReimOfDepositGST_Detail", schema = "loc")
public class DissbursmentReimbrsDepositeGST implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "DIS_ID")
	private String disId;

	@Column(name = "DIS_APC_ID")
	private String disAppId;

	@Column(name = "DIS_GstComrTaxDept")
	private String gstComrTaxDept;

	private transient String financialYear;
	private transient Long amtNetSgstQYr;
	private transient String amtNetSgst;
	private transient Long ttlSgstReim;
	private transient String amtAdmtTaxDeptGst;
	private transient String eligbAmtDepo;
	private transient String durationPeriodDtFr;
	private transient String durationPeriodDtTo;

	public String getDurationPeriodDtFr() {
		return durationPeriodDtFr;
	}

	public void setDurationPeriodDtFr(String durationPeriodDtFr) {
		this.durationPeriodDtFr = durationPeriodDtFr;
	}

	public String getDurationPeriodDtTo() {
		return durationPeriodDtTo;
	}

	public void setDurationPeriodDtTo(String durationPeriodDtTo) {
		this.durationPeriodDtTo = durationPeriodDtTo;
	}

	@Column(name = "DIS_APC_Create_By")
	private String createBy;

	@Column(name = "DIS_APC_Create_Date")
	@Temporal(TemporalType.TIMESTAMP)
	private Date createDate;

	@Column(name = "DIS_APC_Modified_By")
	private String modifiedBy;

	@Column(name = "DIS_APC_Modify_Date")
	@Temporal(TemporalType.TIMESTAMP)
	private Date modifiyDate;

	private transient String relevantDoc;
	private transient String relevantDocID;

	private transient String auditedAccounts;
	private transient String auditedAccountsId;

	private transient String GSTAudit;
	private transient String GSTAuditId;

	private transient String CACertificate;
	private transient String CACertificateId;

	private transient String relevantDocbase64File;
	private transient String auditedAccountsDocbase64File;
	private transient String GSTAuditDocbase64File;
	private transient String CACertificateDocbase64File;

	public Long getAmtNetSgstQYr() {
		return amtNetSgstQYr;
	}

	public void setAmtNetSgstQYr(Long amtNetSgstQYr) {
		this.amtNetSgstQYr = amtNetSgstQYr;
	}

	public String getFinancialYear() {
		return financialYear;
	}

	public void setFinancialYear(String financialYear) {
		this.financialYear = financialYear;
	}

	public Long getTtlSgstReim() {
		return ttlSgstReim;
	}

	public void setTtlSgstReim(Long ttlSgstReim) {
		this.ttlSgstReim = ttlSgstReim;
	}

	public String getRelevantDocID() {
		return relevantDocID;
	}

	public void setRelevantDocID(String relevantDocID) {
		this.relevantDocID = relevantDocID;
	}

	public String getAuditedAccountsId() {
		return auditedAccountsId;
	}

	public void setAuditedAccountsId(String auditedAccountsId) {
		this.auditedAccountsId = auditedAccountsId;
	}

	public String getGSTAuditId() {
		return GSTAuditId;
	}

	public void setGSTAuditId(String gSTAuditId) {
		GSTAuditId = gSTAuditId;
	}

	public String getCACertificateId() {
		return CACertificateId;
	}

	public void setCACertificateId(String cACertificateId) {
		CACertificateId = cACertificateId;
	}

	public String getRelevantDoc() {
		return relevantDoc;
	}

	public void setRelevantDoc(String relevantDoc) {
		this.relevantDoc = relevantDoc;
	}

	public String getAuditedAccounts() {
		return auditedAccounts;
	}

	public void setAuditedAccounts(String auditedAccounts) {
		this.auditedAccounts = auditedAccounts;
	}

	public String getGSTAudit() {
		return GSTAudit;
	}

	public void setGSTAudit(String gSTAudit) {
		GSTAudit = gSTAudit;
	}

	public String getCACertificate() {
		return CACertificate;
	}

	public void setCACertificate(String cACertificate) {
		CACertificate = cACertificate;
	}

	public String getRelevantDocbase64File() {
		return relevantDocbase64File;
	}

	public void setRelevantDocbase64File(String relevantDocbase64File) {
		this.relevantDocbase64File = relevantDocbase64File;
	}

	public String getAuditedAccountsDocbase64File() {
		return auditedAccountsDocbase64File;
	}

	public void setAuditedAccountsDocbase64File(String auditedAccountsDocbase64File) {
		this.auditedAccountsDocbase64File = auditedAccountsDocbase64File;
	}

	public String getGSTAuditDocbase64File() {
		return GSTAuditDocbase64File;
	}

	public void setGSTAuditDocbase64File(String gSTAuditDocbase64File) {
		GSTAuditDocbase64File = gSTAuditDocbase64File;
	}

	public String getCACertificateDocbase64File() {
		return CACertificateDocbase64File;
	}

	public void setCACertificateDocbase64File(String cACertificateDocbase64File) {
		CACertificateDocbase64File = cACertificateDocbase64File;
	}

	public String getDisId() {
		return disId;
	}

	public void setDisId(String disId) {
		this.disId = disId;
	}

	public String getDisAppId() {
		return disAppId;
	}

	public String getGstComrTaxDept() {
		return gstComrTaxDept;
	}

	public void setGstComrTaxDept(String gstComrTaxDept) {
		this.gstComrTaxDept = gstComrTaxDept;
	}

	public String getAmtNetSgst() {
		return amtNetSgst;
	}

	public void setAmtNetSgst(String amtNetSgst) {
		this.amtNetSgst = amtNetSgst;
	}

	public String getAmtAdmtTaxDeptGst() {
		return amtAdmtTaxDeptGst;
	}

	public void setAmtAdmtTaxDeptGst(String amtAdmtTaxDeptGst) {
		this.amtAdmtTaxDeptGst = amtAdmtTaxDeptGst;
	}

	public String getEligbAmtDepo() {
		return eligbAmtDepo;
	}

	public void setEligbAmtDepo(String eligbAmtDepo) {
		this.eligbAmtDepo = eligbAmtDepo;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public void setDisAppId(String disAppId) {
		this.disAppId = disAppId;
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

}
