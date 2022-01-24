package com.webapp.ims.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "Dept_Existing_Project_Details", schema = "loc")
public class EvaluateExistNewProjDetails implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "Epd_ID")
	private String epdId;

	@Column(name = "Epd_Exis_Prod")
	private String epdExisProducts;

	@Column(name = "Epd_Exis_Ins_Cap")
	private String epdExisInstallCapacity;

	@Column(name = "Epd_Prop_Prod")
	private String epdPropProducts;

	@Column(name = "Epd_Prop_Ins_Cap")
	private String epdPropInstallCapacity;

	@Column(name = "Epd_Exis_Gross_Block")
	private Long epdExisGrossBlock;

	@Column(name = "Epd_Prop_Gross_Block")
	private Long epdPropoGrossBlock;

	@Column(name = "Epd_PD_ID")
	private String epdProjDtlId;

	@Column(name = "Epd_Created_By")
	private String epdCreatedBy;

	@Column(name = "Epd_Status")
	private String epdStatus;

	@Column(name = "Epd_Modified_By")
	private String epdModifiedBy;

	@Column(name = "Epd_Modify_Date")
	@Temporal(TemporalType.TIMESTAMP)
	private Date epdModifiyDate;

	@Column(name = "expeligblecapinv")
	private String expEligbleCapInv;

	@Column(name = "exppercovergrsblck")
	private String expPercOverGrsBlck;

	public String getExpEligbleCapInv() {
		return expEligbleCapInv;
	}

	public void setExpEligbleCapInv(String expEligbleCapInv) {
		this.expEligbleCapInv = expEligbleCapInv;
	}

	public String getExpPercOverGrsBlck() {
		return expPercOverGrsBlck;
	}

	public void setExpPercOverGrsBlck(String expPercOverGrsBlck) {
		this.expPercOverGrsBlck = expPercOverGrsBlck;
	}

	public EvaluateExistNewProjDetails() {
		super();
	}

	public String getEpdId() {
		return epdId;
	}

	public void setEpdId(String epdId) {
		this.epdId = epdId;
	}

	public String getEpdExisProducts() {
		return epdExisProducts;
	}

	public void setEpdExisProducts(String epdExisProducts) {
		this.epdExisProducts = epdExisProducts;
	}

	public String getEpdPropProducts() {
		return epdPropProducts;
	}

	public void setEpdPropProducts(String epdPropProducts) {
		this.epdPropProducts = epdPropProducts;
	}

	public String getEpdExisInstallCapacity() {
		return epdExisInstallCapacity;
	}

	public void setEpdExisInstallCapacity(String epdExisInstallCapacity) {
		this.epdExisInstallCapacity = epdExisInstallCapacity;
	}

	public String getEpdPropInstallCapacity() {
		return epdPropInstallCapacity;
	}

	public void setEpdPropInstallCapacity(String epdPropInstallCapacity) {
		this.epdPropInstallCapacity = epdPropInstallCapacity;
	}

	public String getEpdModifiedBy() {
		return epdModifiedBy;
	}

	public void setEpdModifiedBy(String epdModifiedBy) {
		this.epdModifiedBy = epdModifiedBy;
	}

	public Long getEpdExisGrossBlock() {
		return epdExisGrossBlock;
	}

	public void setEpdExisGrossBlock(Long epdExisGrossBlock) {
		this.epdExisGrossBlock = epdExisGrossBlock;
	}

	public Long getEpdPropoGrossBlock() {
		return epdPropoGrossBlock;
	}

	public void setEpdPropoGrossBlock(Long epdPropoGrossBlock) {
		this.epdPropoGrossBlock = epdPropoGrossBlock;
	}

	public String getEpdProjDtlId() {
		return epdProjDtlId;
	}

	public void setEpdProjDtlId(String epdProjDtlId) {
		this.epdProjDtlId = epdProjDtlId;
	}

	public String getEpdCreatedBy() {
		return epdCreatedBy;
	}

	public void setEpdCreatedBy(String epdCreatedBy) {
		this.epdCreatedBy = epdCreatedBy;
	}

	public String getEpdStatus() {
		return epdStatus;
	}

	public void setEpdStatus(String epdStatus) {
		this.epdStatus = epdStatus;
	}

	public Date getEpdModifiyDate() {
		return epdModifiyDate;
	}

	public void setEpdModifiyDate(Date epdModifiyDate) {
		this.epdModifiyDate = epdModifiyDate;
	}
}
