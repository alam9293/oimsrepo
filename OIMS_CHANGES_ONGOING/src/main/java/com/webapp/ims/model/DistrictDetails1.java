package com.webapp.ims.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "District_Master", schema = "loc")
public class DistrictDetails1 implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "Dist_ID")
	private Long id;

	@Column(name = "Dist_Name")
	private String districtName;

	@Column(name = "State_Code")
	private Long stateCode;

	public Long getStateCode() {
		return stateCode;
	}

	public void setStateCode(Long stateCode) {
		this.stateCode = stateCode;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getDistrictName() {
		return districtName;
	}

	public void setDistrictName(String districtName) {
		this.districtName = districtName;
	}

}
