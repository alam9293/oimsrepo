package com.webapp.ims.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "Dept_Prop_Emp_Details", schema = "loc")
public class DeptProposedEmploymentDetails implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "PE_Detail_ID")
	private String id;

	@Column(name = "PE_APC_ID")
	private String appId;

	@Column(name = "Created_By")
	private String createdBy;

	@Column(name = "Modified_By")
	private String modifiedtedBy;

	@Column(name = "Status")
	private String status;

	@Column(name = "PE_Totaldetail_Observ")
	private String totalDetailObserv;

	public String getTotalDetailObserv() {
		return totalDetailObserv;
	}

	public void setTotalDetailObserv(String totalDetailObserv) {
		this.totalDetailObserv = totalDetailObserv;
	}

	@OneToMany(mappedBy = "proposedEmploymentDetails", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<DeptSkilledUnSkilledEmployemnt> skilledUnSkilledEmployemnt;

	public DeptProposedEmploymentDetails() {

	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public String getModifiedtedBy() {
		return modifiedtedBy;
	}

	public void setModifiedtedBy(String modifiedtedBy) {
		this.modifiedtedBy = modifiedtedBy;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public List<DeptSkilledUnSkilledEmployemnt> getSkilledUnSkilledEmployemnt() {
		return skilledUnSkilledEmployemnt;
	}

	public void setSkilledUnSkilledEmployemnt(List<DeptSkilledUnSkilledEmployemnt> skilledUnSkilledEmployemnt) {
		this.skilledUnSkilledEmployemnt = skilledUnSkilledEmployemnt;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
