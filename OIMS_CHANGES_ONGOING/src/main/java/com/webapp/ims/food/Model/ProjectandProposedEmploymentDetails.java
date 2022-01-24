package com.webapp.ims.food.Model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

@Entity
@Table(name = "projectand_proposed_employment_details")
@IdClass(Identifier.class)
public class ProjectandProposedEmploymentDetails implements Serializable {
	@Id
	@Column(name = "unit_id")
	private String unit_id;

	@Id
	@Column(name = "control_id")
	private String control_id;

	@Id
	@Column(name = "id")
	private Identifier id;

	@Column(name = "district")
	private String district;
	@Column(name = "division")
	private String division;
	@Column(name = "sector")
	private String sector;
	@Column(name = "selectsubsector")
	private String selectsubsector;
	@Column(name = "nameoftheproject")
	private String nameoftheproject;
	@Column(name = "locationareaoftheproject")
	private String locationareaoftheproject;
	@Column(name = "technology")
	private String technology;

	@Column(name = "detailsoffirmregistration")
	private String detailsoffirmregistration;

	@Column(name = "nameofthenbspagencywhoprepareddpr")
	private String nameofthenbspagencywhoprepareddpr;

	public String getUnit_id() {
		return unit_id;
	}

	public void setUnit_id(String unit_id) {
		this.unit_id = unit_id;
	}

	public String getControl_id() {
		return control_id;
	}

	public void setControl_id(String control_id) {
		this.control_id = control_id;
	}

	public Identifier getId() {
		return id;
	}

	public void setId(Identifier id) {
		this.id = id;
	}

	public String getDistrict() {
		return district;
	}

	public void setDistrict(String district) {
		this.district = district;
	}

	public String getDivision() {
		return division;
	}

	public void setDivision(String division) {
		this.division = division;
	}

	public String getSector() {
		return sector;
	}

	public void setSector(String sector) {
		this.sector = sector;
	}

	public String getSelectsubsector() {
		return selectsubsector;
	}

	public void setSelectsubsector(String selectsubsector) {
		this.selectsubsector = selectsubsector;
	}

	public String getNameoftheproject() {
		return nameoftheproject;
	}

	public void setNameoftheproject(String nameoftheproject) {
		this.nameoftheproject = nameoftheproject;
	}

	public String getLocationareaoftheproject() {
		return locationareaoftheproject;
	}

	public void setLocationareaoftheproject(String locationareaoftheproject) {
		this.locationareaoftheproject = locationareaoftheproject;
	}

	public String getTechnology() {
		return technology;
	}

	public void setTechnology(String technology) {
		this.technology = technology;
	}

	public String getDetailsoffirmregistration() {
		return detailsoffirmregistration;
	}

	public void setDetailsoffirmregistration(String detailsoffirmregistration) {
		this.detailsoffirmregistration = detailsoffirmregistration;
	}

	public String getNameofthenbspagencywhoprepareddpr() {
		return nameofthenbspagencywhoprepareddpr;
	}

	public void setNameofthenbspagencywhoprepareddpr(String nameofthenbspagencywhoprepareddpr) {
		this.nameofthenbspagencywhoprepareddpr = nameofthenbspagencywhoprepareddpr;
	}

}
