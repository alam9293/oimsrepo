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
@Table(name = "INV_Installed_Capacities", schema = "loc")
public class INVInstalledCapacities implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	@Column(name = "ID")
	private String pwId;

	public Integer getPhaseNo() {
		return phaseNo;
	}

	public void setPhaseNo(Integer phaseNo) {
		this.phaseNo = phaseNo;
	}

	public String getInvId() {
		return invId;
	}

	public void setInvId(String invId) {
		this.invId = invId;
	}

	public String getApcId() {
		return apcId;
	}

	public void setApcId(String apcId) {
		this.apcId = apcId;
	}

	public Long getCapacity() {
		return capacity;
	}

	public void setCapacity(Long capacity) {
		this.capacity = capacity;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public Date getPropProductDate() {
		return propProductDate;
	}

	public void setPropProductDate(Date propProductDate) {
		this.propProductDate = propProductDate;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

//	public String getCreatedBy() {
//		return createdBy;
//	}
//
//	public void setCreatedBy(String createdBy) {
//		this.createdBy = createdBy;
//	}
//
//	public String getModifiedBy() {
//		return modifiedBy;
//	}
//
//	public void setModifiedBy(String modifiedBy) {
//		this.modifiedBy = modifiedBy;
//	}
//
//	public String getStatus() {
//		return status;
//	}
//
//	public void setStatus(String status) {
//		this.status = status;
//	}
//
//	public Date getModifiedDate() {
//		return modifiedDate;
//	}
//
//	public void setModifiedDate(Date modifiedDate) {
//		this.modifiedDate = modifiedDate;
//	}

	@Column(name = "Phase_Number")
	private Integer phaseNo;

	@Column(name = "INV_ID")
	private String invId;
	@Column(name = "APC_ID")
	private String apcId;

	@Column(name = "INV_Proposed_Fix_Cap_Inv")
	private Long invFci;

	@Column(name = "Capacity")
	private Long capacity;

	@Column(name = "Product_Name")
	private String productName;

	@Column(name = "Prop_Prod_Date")
	@Temporal(TemporalType.DATE)
	private Date propProductDate;

	@Column(name = "Unit")
	private String unit;

//	@Column(name = "Created_By")
//	private String createdBy;
//	@Column(name = "Modified_By")
//	private String modifiedBy;
//	@Column(name = "Status")
//	private String status;
//
//	@Column(name = "Modify_Date")
//	@Temporal(TemporalType.TIMESTAMP)
//	private Date modifiedDate;

	public INVInstalledCapacities() {
		super();
	}

	public String getPwId() {
		return pwId;
	}

	public void setPwId(String pwId) {
		this.pwId = pwId;
	}

	public Long getInvFci() {
		return invFci;
	}

	public void setInvFci(Long invFci) {
		this.invFci = invFci;
	}
}
