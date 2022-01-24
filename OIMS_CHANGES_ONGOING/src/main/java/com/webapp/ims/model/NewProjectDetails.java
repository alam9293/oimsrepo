package com.webapp.ims.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "New_Project_Details", schema = "loc")

public class NewProjectDetails {

	@Id
	@Column(name = "Npd_Id")
	private String npdId;

	public String getNpdId() {
		return npdId;
	}

	public void setNpdId(String npdId) {
		this.npdId = npdId;
	}

	@Column(name = "New_Prop_Prod")
	private String newPropProducts;

	@Column(name = "New_Prop_Ins_Cap")
	private String newPropInstallCapacity;

	@Column(name = "Npd_Status")
	private String status;

	@Column(name = "Npd_Pd_Id")
	private String npdProjDtlId;

	public String getNpdProjDtlId() {
		return npdProjDtlId;
	}

	public void setNpdProjDtlId(String npdProjDtlId) {
		this.npdProjDtlId = npdProjDtlId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getNewPropProducts() {
		return newPropProducts;
	}

	public void setNewPropProducts(String newPropProducts) {
		this.newPropProducts = newPropProducts;
	}

	public String getNewPropInstallCapacity() {
		return newPropInstallCapacity;
	}

	public void setNewPropInstallCapacity(String newPropInstallCapacity) {
		this.newPropInstallCapacity = newPropInstallCapacity;
	}

}
