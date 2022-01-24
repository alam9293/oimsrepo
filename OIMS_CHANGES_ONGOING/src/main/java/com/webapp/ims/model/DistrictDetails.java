package com.webapp.ims.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "District_Details", schema = "loc")
public class DistrictDetails implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "Distt_ID")
	private Long id;

	@Column(name = "District_Code")
	private Long districtCode;

	@Column(name = "District_Name")
	private String districtName;

	@Column(name = "Mandal_Name")
	private String mandalName;

	@Column(name = "Region_Name")
	private String regionName;

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

	public String getMandalName() {
		return mandalName;
	}

	public void setMandalName(String mandalName) {
		this.mandalName = mandalName;
	}

	public String getRegionName() {
		return regionName;
	}

	public void setRegionName(String regionName) {
		this.regionName = regionName;
	}

	public Long getDistrictCode() {
		return districtCode;
	}

	public void setDistrictCode(Long districtCode) {
		this.districtCode = districtCode;
	}

}
