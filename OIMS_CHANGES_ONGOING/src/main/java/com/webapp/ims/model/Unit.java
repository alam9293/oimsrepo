package com.webapp.ims.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "Unit_Master", schema = "loc")
public class Unit implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "Unit_ID")
	private String unitId;

	@Column(name = "Unit_Name")
	private String unitName;

	public String getUnitId() {
		return unitId;
	}

	public void setUnitId(String unitId) {
		this.unitId = unitId;
	}

	public String getUnitName() {
		return unitName;
	}

	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}

}
