package com.webapp.ims.food.Model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="foodeligibletechnicalcivilwork")
public class EligibleTechnicalCivilWork implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "id")
	private String id;
	
	@Column(name="unit_id")
	private String unitId;
	
	@Column(name="control_id")
	private String control_id;
	
	@Column(name="particular")
	private String particular;
	
	@Column(name="area_sqmt")
	private String areaSqmt;
	
	@Column(name="rate")
	private String rate;
	
	@Column(name="amount")
	private String amount;
	
	@Column(name="eligible_cost_in_lacs")
	private String eligibleCostInLacs;

	

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

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

	public String getParticular() {
		return particular;
	}

	public void setParticular(String particular) {
		this.particular = particular;
	}

	public String getAreaSqmt() {
		return areaSqmt;
	}

	public void setAreaSqmt(String areaSqmt) {
		this.areaSqmt = areaSqmt;
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
	

	public String getEligibleCostInLacs() {
		return eligibleCostInLacs;
	}

	public void setEligibleCostInLacs(String eligibleCostInLacs) {
		this.eligibleCostInLacs = eligibleCostInLacs;
	}
	
	

}
