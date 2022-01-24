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
@Table(name = "Prop_Emp_Details", schema = "loc")
public class ProposedEmploymentDetails implements Serializable {

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

	@OneToMany(mappedBy = "proposedEmploymentDetails", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<SkilledUnSkilledEmployemnt> skilledUnSkilledEmployemnt;

	public ProposedEmploymentDetails() {

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

	public List<SkilledUnSkilledEmployemnt> getSkilledUnSkilledEmployemnt() {
		return skilledUnSkilledEmployemnt;
	}

	public void setSkilledUnSkilledEmployemnt(List<SkilledUnSkilledEmployemnt> skilledUnSkilledEmployemnt) {
		this.skilledUnSkilledEmployemnt = skilledUnSkilledEmployemnt;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
