/*
 *     Copyright 2020 - IT Solution powered by National Informatics Centre Uttar Pradesh State Unit
 *
 */
package com.webapp.ims.food.Model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author nic
 *
 */

@Entity
@Table(name = "foodiseligibletechnicalcivilwork")
///@IdClass(Identifier.class)
public class ISEligibleTechnicalCivilWork {

	@Column(name = "unit_id")
	private String unitId;

	@Column(name = "control_id")
	private String control_id;

	@Id
	@Column(name = "id")
	private String id;

	@Column(name = "sno")
	private String sno;

	@Column(name = "particulars")
	private String particulars;

	@Column(name = "rate")
	private String rate;

	@Column(name = "amount")
	private String amount;

	@Column(name = "eligiblecost")
	private String eligiblecost;

	@Column(name = "areasqft")
	private String areasqft;

	@Column(name = "eligiblecostinlacs")
	private String eligiblecostinlacs;

	public String getUnitId() {
		return unitId;
	}

	public void setUnitId(String unitId) {
		this.unitId = unitId;
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

	public String getSno() {
		return sno;
	}

	public void setSno(String sno) {
		this.sno = sno;
	}

	public String getRate() {
		return rate;
	}

	public void setRate(String rate) {
		this.rate = rate;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getEligiblecostinlacs() {
		return eligiblecostinlacs;
	}

	public void setEligiblecostinlacs(String eligiblecostinlacs) {
		this.eligiblecostinlacs = eligiblecostinlacs;
	}

	public String getEligiblecost() {
		return eligiblecost;
	}

	public void setEligiblecost(String eligiblecost) {
		this.eligiblecost = eligiblecost;
	}

	public String getParticulars() {
		return particulars;
	}

	public void setParticulars(String particulars) {
		this.particulars = particulars;
	}

	public String getAreasqft() {
		return areasqft;
	}

	public void setAreasqft(String areasqft) {
		this.areasqft = areasqft;
	}

}