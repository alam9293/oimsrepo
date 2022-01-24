package com.webapp.ims.food.Model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

@Entity
@Table(name = "project_details")
@IdClass(Identifier.class)
public class ProjectDtailsPreVehicles implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	private String unit_id;
	@Id
	private String control_id;
	@Id
	private Identifier id;
	@Column(name = "nameoftheproject")
	private String nameoftheproject;
	@Column(name = "locationareaoftheproject")
	private String locationareaoftheproject;
	@Column(name = "anticipateddateofpurchaseofreefervanmobileprecoolingvans")
	private Date anticipateddateofpurchaseofreefervanmobileprecoolingvans;

	@Column(name = "nameoftheunit")
	private String nameoftheunit;
	@Column(name = "locationareaoftheunit")
	private String locationareaoftheunit;
	@Column(name = "technology")
	private String technology;
	@Column(name = "yearofestablishingtheunit")
	private String yearofestablishingtheunit;
	@Column(name = "dateofcommercialproduction")
	private Date dateofcommercialproduction;
	@Column(name = "nameoftheagriculturalcommodityused")
	private String nameoftheagriculturalcommodityused;
	@Column(name = "quantityoftotalrawmaterialusedforprocessing")
	private String quantityoftotalrawmaterialusedforprocessing;

	public String getNameoftheunit() {
		return nameoftheunit;
	}

	public void setNameoftheunit(String nameoftheunit) {
		this.nameoftheunit = nameoftheunit;
	}

	public String getLocationareaoftheunit() {
		return locationareaoftheunit;
	}

	public void setLocationareaoftheunit(String locationareaoftheunit) {
		this.locationareaoftheunit = locationareaoftheunit;
	}

	public String getTechnology() {
		return technology;
	}

	public void setTechnology(String technology) {
		this.technology = technology;
	}

	public String getYearofestablishingtheunit() {
		return yearofestablishingtheunit;
	}

	public void setYearofestablishingtheunit(String yearofestablishingtheunit) {
		this.yearofestablishingtheunit = yearofestablishingtheunit;
	}

	public Date getDateofcommercialproduction() {
		return dateofcommercialproduction;
	}

	public void setDateofcommercialproduction(Date dateofcommercialproduction) {
		this.dateofcommercialproduction = dateofcommercialproduction;
	}

	public String getNameoftheagriculturalcommodityused() {
		return nameoftheagriculturalcommodityused;
	}

	public void setNameoftheagriculturalcommodityused(String nameoftheagriculturalcommodityused) {
		this.nameoftheagriculturalcommodityused = nameoftheagriculturalcommodityused;
	}

	public String getQuantityoftotalrawmaterialusedforprocessing() {
		return quantityoftotalrawmaterialusedforprocessing;
	}

	public void setQuantityoftotalrawmaterialusedforprocessing(String quantityoftotalrawmaterialusedforprocessing) {
		this.quantityoftotalrawmaterialusedforprocessing = quantityoftotalrawmaterialusedforprocessing;
	}

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

	public Date getAnticipateddateofpurchaseofreefervanmobileprecoolingvans() {
		return anticipateddateofpurchaseofreefervanmobileprecoolingvans;
	}

	public void setAnticipateddateofpurchaseofreefervanmobileprecoolingvans(
			Date anticipateddateofpurchaseofreefervanmobileprecoolingvans) {
		this.anticipateddateofpurchaseofreefervanmobileprecoolingvans = anticipateddateofpurchaseofreefervanmobileprecoolingvans;
	}

}
