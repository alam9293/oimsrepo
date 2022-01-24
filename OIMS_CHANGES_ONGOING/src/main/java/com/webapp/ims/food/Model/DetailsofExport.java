package com.webapp.ims.food.Model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "detailsof_export")
public class DetailsofExport implements Serializable {

	private String unit_id;

	private String control_id;

	@Id
	private String id;

	@Column(name = "dateofexport")
	private String dateofexport;

	@Column(name = "namecommodity")
	private String namecommodity;

	@Column(name = "weightofexportedproducts")
	private String weightofexportedproducts;

	@Column(name = "nameofthecountries")
	private String nameofthecountries;

	@Column(name = "modeofexport")
	private String modeofexport;

	@Column(name = "slnoanddatesofshippingairwaybills")
	private String slnoanddatesofshippingairwaybills;

	@Column(name = "amountofthebills")
	private String amountofthebills;

	@Column(name = "action")
	private String action;

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

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDateofexport() {
		return dateofexport;
	}

	public void setDateofexport(String dateofexport) {
		this.dateofexport = dateofexport;
	}

	public String getNamecommodity() {
		return namecommodity;
	}

	public void setNamecommodity(String namecommodity) {
		this.namecommodity = namecommodity;
	}

	public String getWeightofexportedproducts() {
		return weightofexportedproducts;
	}

	public void setWeightofexportedproducts(String weightofexportedproducts) {
		this.weightofexportedproducts = weightofexportedproducts;
	}

	public String getNameofthecountries() {
		return nameofthecountries;
	}

	public void setNameofthecountries(String nameofthecountries) {
		this.nameofthecountries = nameofthecountries;
	}

	public String getModeofexport() {
		return modeofexport;
	}

	public void setModeofexport(String modeofexport) {
		this.modeofexport = modeofexport;
	}

	public String getSlnoanddatesofshippingairwaybills() {
		return slnoanddatesofshippingairwaybills;
	}

	public void setSlnoanddatesofshippingairwaybills(String slnoanddatesofshippingairwaybills) {
		this.slnoanddatesofshippingairwaybills = slnoanddatesofshippingairwaybills;
	}

	public String getAmountofthebills() {
		return amountofthebills;
	}

	public void setAmountofthebills(String amountofthebills) {
		this.amountofthebills = amountofthebills;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

}
