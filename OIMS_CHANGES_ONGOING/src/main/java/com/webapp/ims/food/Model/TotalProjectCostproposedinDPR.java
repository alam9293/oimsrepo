/*
 *     Copyright 2020 - IT Solution powered by National Informatics Centre Uttar Pradesh State Unit
 *
 */
package com.webapp.ims.food.Model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

/**
 * @author nic
 *
 */

@Entity
@Table(name = "total_project_costproposedindpr")
@IdClass(Identifier.class)
public class TotalProjectCostproposedinDPR {

	private String unit_id;

	private String control_id;

	@Id
	private String id;

	@Column(name = "itemsland")
	private String itemsland;

	@Column(name = "amountland")
	private double amountland;

	@Column(name = "itemsbuilding")
	private String itemsbuilding;

	@Column(name = "amountbuilding")
	private double amountbuilding;

	@Column(name = "itemsmachineryindigenous")
	private String itemsmachineryindigenous;

	@Column(name = "amountmachineryindigenous")
	private double amountmachineryindigenous;

	@Column(name = "itemsmachineryimported")
	private String itemsmachineryimported;

	@Column(name = "amountmachineryimported")
	private double amountmachineryimported;

	@Column(name = "itemsothercost")
	private String itemsothercost;

	@Column(name = "amountothercost")
	private double amountothercost;

	@Column(name = "itemsmarginmoneyforworkingcapital")
	private String itemsmarginmoneyforworkingcapital;

	@Column(name = "amountmarginmoneyforworkingcapital")
	private double amountmarginmoneyforworkingcapital;

	@Column(name = "itemstotal")
	private String itemstotal;

	@Column(name = "amounttotal")
	private double amounttotal;

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

	public String getItemsland() {
		return itemsland;
	}

	public void setItemsland(String itemsland) {
		this.itemsland = itemsland;
	}

	public double getAmountland() {
		return amountland;
	}

	public void setAmountland(double amountland) {
		this.amountland = amountland;
	}

	public String getItemsbuilding() {
		return itemsbuilding;
	}

	public void setItemsbuilding(String itemsbuilding) {
		this.itemsbuilding = itemsbuilding;
	}

	public double getAmountbuilding() {
		return amountbuilding;
	}

	public void setAmountbuilding(double amountbuilding) {
		this.amountbuilding = amountbuilding;
	}

	public String getItemsmachineryindigenous() {
		return itemsmachineryindigenous;
	}

	public void setItemsmachineryindigenous(String itemsmachineryindigenous) {
		this.itemsmachineryindigenous = itemsmachineryindigenous;
	}

	public double getAmountmachineryindigenous() {
		return amountmachineryindigenous;
	}

	public void setAmountmachineryindigenous(double amountmachineryindigenous) {
		this.amountmachineryindigenous = amountmachineryindigenous;
	}

	public String getItemsmachineryimported() {
		return itemsmachineryimported;
	}

	public void setItemsmachineryimported(String itemsmachineryimported) {
		this.itemsmachineryimported = itemsmachineryimported;
	}

	public double getAmountmachineryimported() {
		return amountmachineryimported;
	}

	public void setAmountmachineryimported(double amountmachineryimported) {
		this.amountmachineryimported = amountmachineryimported;
	}

	public String getItemsothercost() {
		return itemsothercost;
	}

	public void setItemsothercost(String itemsothercost) {
		this.itemsothercost = itemsothercost;
	}

	public double getAmountothercost() {
		return amountothercost;
	}

	public void setAmountothercost(double amountothercost) {
		this.amountothercost = amountothercost;
	}

	public String getItemsmarginmoneyforworkingcapital() {
		return itemsmarginmoneyforworkingcapital;
	}

	public void setItemsmarginmoneyforworkingcapital(String itemsmarginmoneyforworkingcapital) {
		this.itemsmarginmoneyforworkingcapital = itemsmarginmoneyforworkingcapital;
	}

	public double getAmountmarginmoneyforworkingcapital() {
		return amountmarginmoneyforworkingcapital;
	}

	public void setAmountmarginmoneyforworkingcapital(double amountmarginmoneyforworkingcapital) {
		this.amountmarginmoneyforworkingcapital = amountmarginmoneyforworkingcapital;
	}

	public String getItemstotal() {
		return itemstotal;
	}

	public void setItemstotal(String itemstotal) {
		this.itemstotal = itemstotal;
	}

	public double getAmounttotal() {
		return amounttotal;
	}

	public void setAmounttotal(double amounttotal) {
		this.amounttotal = amounttotal;
	}

}
