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
@Table(name = "Evaluate_Capital_Investment", schema = "loc")
public class EvaluateCapitalInvestment implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "ECI_ID")
	private String eciId;

	@Column(name = "ECI_Applicant_ID")
	private String eciApplcId;

	@Column(name = "ECI_Item")
	private String eciItem;

	@Column(name = "ECI_IsFci")
	private String eciIsFci;

	@Column(name = "ECI_DPR_Invest")
	private Long eciDPRInvest;

	@Column(name = "ECI_IIEPP_Invest")
	private Long eciIIEPPInvest;

	@Column(name = "ECI_PICUP_Remarks")
	private String eciPICUP_Remarks;

	@Column(name = "ECI_Created_By")
	private String eciCreatedBy;
	@Column(name = "ECI_Modified_By")
	private String eciModifiedBy;

	@Column(name = "ECI_Modify_Date")
	@Temporal(TemporalType.TIMESTAMP)
	private Date eciModifiyDate;

	@Column(name = "ECI_Created_Date")
	private LocalDate eciCreatedDate;

	public EvaluateCapitalInvestment() {
		super();

	}

	public String getEciId() {
		return eciId;
	}

	public void setEciId(String eciId) {
		this.eciId = eciId;
	}

	public String getEciItem() {
		return eciItem;
	}

	public void setEciItem(String eciItem) {
		this.eciItem = eciItem;
	}

	public String getEciIsFci() {
		return eciIsFci;
	}

	public void setEciIsFci(String eciIsFci) {
		this.eciIsFci = eciIsFci;
	}

	public Long getEciDPRInvest() {
		return eciDPRInvest;
	}

	public void setEciDPRInvest(Long eciDPRInvest) {
		this.eciDPRInvest = eciDPRInvest;
	}

	public Long getEciIIEPPInvest() {
		return eciIIEPPInvest;
	}

	public void setEciIIEPPInvest(Long eciIIEPPInvest) {
		this.eciIIEPPInvest = eciIIEPPInvest;
	}

	public String getEciCreatedBy() {
		return eciCreatedBy;
	}

	public void setEciCreatedBy(String eciCreatedBy) {
		this.eciCreatedBy = eciCreatedBy;
	}

	public String getEciModifiedBy() {
		return eciModifiedBy;
	}

	public void setEciModifiedBy(String eciModifiedBy) {
		this.eciModifiedBy = eciModifiedBy;
	}

	public Date getEciModifiyDate() {
		return eciModifiyDate;
	}

	public void setEciModifiyDate(Date eciModifiyDate) {
		this.eciModifiyDate = eciModifiyDate;
	}

	public String getEciPICUP_Remarks() {
		return eciPICUP_Remarks;
	}

	public void setEciPICUP_Remarks(String eciPICUP_Remarks) {
		this.eciPICUP_Remarks = eciPICUP_Remarks;
	}

	public String getEciApplcId() {
		return eciApplcId;
	}

	public void setEciApplcId(String eciApplcId) {
		this.eciApplcId = eciApplcId;
	}

	public LocalDate getEciCreatedDate() {
		return eciCreatedDate;
	}

	public void setEciCreatedDate(LocalDate eciCreatedDate) {
		this.eciCreatedDate = eciCreatedDate;
	}

}
