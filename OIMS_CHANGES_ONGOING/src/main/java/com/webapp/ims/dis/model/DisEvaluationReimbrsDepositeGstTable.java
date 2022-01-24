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
@Table(name = "Dis_Evaluation_ReimOfDepositGST_Table", schema = "loc")
public class DisEvaluationReimbrsDepositeGstTable implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "DIS_ID")
	private String disId;

	@Column(name = "DIS_APC_ID")
	private String disAppId;

	@Column(name = "Financial_Year")
	private String financialYear;

	@Column(name = "Amt_NetSgst_QYr")
	private Long amtNetSgstQYr;

	@Column(name = "DIS_AmtNetSgst")
	private String amtNetSgst;

	@Column(name = "Ttl_Sgst_Reim")
	private Long ttlSgstReim;

	@Column(name = "DIS_AmtAdmtTaxDeptGst")
	private String amtAdmtTaxDeptGst;

	@Column(name = "DIS_EligbAmtDepo")
	private String eligbAmtDepo;

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

	@Column(name = "Duration")
	private String durationPeriodDtFr;

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

	@Column(name = "DurationT")
	private String durationPeriodDtTo;

	private transient Long amtnetconvert;

	public String getDisId() {
		return disId;
	}

	public void setDisId(String disId) {
		this.disId = disId;
	}

	public String getDisAppId() {
		return disAppId;
	}

	public void setDisAppId(String disAppId) {
		this.disAppId = disAppId;
	}

	public String getFinancialYear() {
		return financialYear;
	}

	public void setFinancialYear(String financialYear) {
		this.financialYear = financialYear;
	}

	public Long getAmtNetSgstQYr() {
		return amtNetSgstQYr;
	}

	public void setAmtNetSgstQYr(Long amtNetSgstQYr) {
		this.amtNetSgstQYr = amtNetSgstQYr;
	}

	public String getAmtNetSgst() {
		return amtNetSgst;
	}

	public void setAmtNetSgst(String amtNetSgst) {
		this.amtNetSgst = amtNetSgst;
	}

	public Long getTtlSgstReim() {
		return ttlSgstReim;
	}

	public void setTtlSgstReim(Long ttlSgstReim) {
		this.ttlSgstReim = ttlSgstReim;
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

	public Long getAmtnetconvert() {
		return amtnetconvert;
	}

	public void setAmtnetconvert(Long amtnetconvert) {
		this.amtnetconvert = amtnetconvert;
	}

}
