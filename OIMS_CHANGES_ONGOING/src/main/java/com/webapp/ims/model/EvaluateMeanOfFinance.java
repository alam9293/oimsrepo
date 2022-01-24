package com.webapp.ims.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "Evaluate_MeanOfFinance", schema = "loc")
public class EvaluateMeanOfFinance implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "EMF_ID")
	private String emfId;

	@Column(name = "EMF_Applicant_ID")
	private String emfApplcId;

	@Column(name = "EMF_PhaseNo")
	private String emfphaseNo;

	@Column(name = "EMF_PhsItemName")
	private String emfphsItemName;

	@Column(name = "EMF_PhsInvestAmt")
	private Long emfphsInvestAmt;

	@Column(name = "EMF_Created_By")
	private String emfCreatedBy;

	@Column(name = "EMF_Created_Date")
	private LocalDate emfCreatedDate;

	@Column(name = "EMF_Modified_By")
	private String emfModifiedBy;

	@Column(name = "EMF_Modify_Date")
	@Temporal(TemporalType.TIMESTAMP)
	private Date emfModifiyDate;

	public EvaluateMeanOfFinance() {
		super();

	}

	public String getEmfId() {
		return emfId;
	}

	public void setEmfId(String emfId) {
		this.emfId = emfId;
	}

	public String getEmfApplcId() {
		return emfApplcId;
	}

	public void setEmfApplcId(String emfApplcId) {
		this.emfApplcId = emfApplcId;
	}

	public String getEmfphaseNo() {
		return emfphaseNo;
	}

	public void setEmfphaseNo(String emfphaseNo) {
		this.emfphaseNo = emfphaseNo;
	}

	public String getEmfphsItemName() {
		return emfphsItemName;
	}

	public void setEmfphsItemName(String emfphsItemName) {
		this.emfphsItemName = emfphsItemName;
	}

	public Long getEmfphsInvestAmt() {
		return emfphsInvestAmt;
	}

	public void setEmfphsInvestAmt(Long emfphsInvestAmt) {
		this.emfphsInvestAmt = emfphsInvestAmt;
	}

	public String getEmfCreatedBy() {
		return emfCreatedBy;
	}

	public void setEmfCreatedBy(String emfCreatedBy) {
		this.emfCreatedBy = emfCreatedBy;
	}

	public LocalDate getEmfCreatedDate() {
		return emfCreatedDate;
	}

	public void setEmfCreatedDate(LocalDate emfCreatedDate) {
		this.emfCreatedDate = emfCreatedDate;
	}

	public String getEmfModifiedBy() {
		return emfModifiedBy;
	}

	public void setEmfModifiedBy(String emfModifiedBy) {
		this.emfModifiedBy = emfModifiedBy;
	}

	public Date getEmfModifiyDate() {
		return emfModifiyDate;
	}

	public void setEmfModifiyDate(Date emfModifiyDate) {
		this.emfModifiyDate = emfModifiyDate;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
