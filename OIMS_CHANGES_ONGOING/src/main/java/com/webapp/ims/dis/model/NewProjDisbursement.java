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
@Table(name = "New_Proj_Disbursement", schema = "loc")
public class NewProjDisbursement implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "NewProj_Id")
	private String newprojId;

	@Column(name = "NewProj_Apc_Id")
	private String newprojApcId;

	@Column(name = "NewProj_Land_Cost")
	private Long newprojLandCost;

	@Column(name = "NewProj_Bldg_Cost")
	private Long newprojBldgCost;

	@Column(name = "NewProj_Plantmac_Cost")
	private Long newprojPlantMachCost;

	@Column(name = "NewProj_MiscFixed_Asset")
	private Long newprojMiscFixedAsset;

	@Column(name = "NewProj_Infra")
	private Long newprojInfra;

	@Column(name = "NewProj_Total")
	private Long total;

	public Long getTotal() {
		return total;
	}

	public void setTotal(Long total) {
		this.total = total;
	}

	@Column(name = "NewProj_Created_By")
	private String newprojCreatedBy;
	@Column(name = "NewProj_Modify_Date")
	@Temporal(TemporalType.DATE)
	private Date newprojModifiyDate;
	@Column(name = "NewProj_Created_Date")
	@Temporal(TemporalType.DATE)
	private Date newprojCreatedDate;

	public NewProjDisbursement() {
		super();

	}

	public String getNewprojId() {
		return newprojId;
	}

	public void setNewprojId(String newprojId) {
		this.newprojId = newprojId;
	}

	public String getNewprojApcId() {
		return newprojApcId;
	}

	public void setNewprojApcId(String newprojApcId) {
		this.newprojApcId = newprojApcId;
	}

	public Long getNewprojLandCost() {
		return newprojLandCost;
	}

	public void setNewprojLandCost(Long newprojLandCost) {
		this.newprojLandCost = newprojLandCost;
	}

	public Long getNewprojBldgCost() {
		return newprojBldgCost;
	}

	public void setNewprojBldgCost(Long newprojBldgCost) {
		this.newprojBldgCost = newprojBldgCost;
	}

	public Long getNewprojPlantMachCost() {
		return newprojPlantMachCost;
	}

	public void setNewprojPlantMachCost(Long newprojPlantMachCost) {
		this.newprojPlantMachCost = newprojPlantMachCost;
	}

	public Long getNewprojInfra() {
		return newprojInfra;
	}

	public void setNewprojInfra(Long newprojInfra) {
		this.newprojInfra = newprojInfra;
	}

	public String getNewprojCreatedBy() {
		return newprojCreatedBy;
	}

	public void setNewprojCreatedBy(String newprojCreatedBy) {
		this.newprojCreatedBy = newprojCreatedBy;
	}

	public Date getNewprojModifiyDate() {
		return newprojModifiyDate;
	}

	public void setNewprojModifiyDate(Date newprojModifiyDate) {
		this.newprojModifiyDate = newprojModifiyDate;
	}

	public Date getNewprojCreatedDate() {
		return newprojCreatedDate;
	}

	public void setNewprojCreatedDate(Date newprojCreatedDate) {
		this.newprojCreatedDate = newprojCreatedDate;
	}

	public Long getNewprojMiscFixedAsset() {
		return newprojMiscFixedAsset;
	}

	public void setNewprojMiscFixedAsset(Long newprojMiscFixedAsset) {
		this.newprojMiscFixedAsset = newprojMiscFixedAsset;
	}

}
