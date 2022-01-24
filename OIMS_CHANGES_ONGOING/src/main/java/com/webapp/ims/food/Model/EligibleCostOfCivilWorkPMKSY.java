package com.webapp.ims.food.Model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="eligiblecostofcivilworkpmksy")
public class EligibleCostOfCivilWorkPMKSY implements Serializable {

	@Id
	@Column(name = "id")
	private String id;

	
	@Column(name="unit_id")
	private String unitId;
	
	@Column(name="control_id")
	private String control_id;
	
	@Column(name = "eligible_tech_civil_work_particular")
	private String eligibleTechCivilWorkParticular;

	@Column(name = "eligible_tech_civil_work_area")
	private String eligibleTechCivilWorkArea;

	@Column(name = "eligible_tech_civil_work_rate")
	private String eligibleTechCivilWorkRate;

	@Column(name = "eligible_tech_civil_work_amount")
	private String eligibleTechCivilWorkAmount;

	@Column(name = "eligible_tech_civil_work_eligible_cost")
	private String eligibleTechCivilWorkEligibleCost;

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

	public String getEligibleTechCivilWorkParticular() {
		return eligibleTechCivilWorkParticular;
	}

	public void setEligibleTechCivilWorkParticular(String eligibleTechCivilWorkParticular) {
		this.eligibleTechCivilWorkParticular = eligibleTechCivilWorkParticular;
	}

	public String getEligibleTechCivilWorkArea() {
		return eligibleTechCivilWorkArea;
	}

	public void setEligibleTechCivilWorkArea(String eligibleTechCivilWorkArea) {
		this.eligibleTechCivilWorkArea = eligibleTechCivilWorkArea;
	}

	public String getEligibleTechCivilWorkRate() {
		return eligibleTechCivilWorkRate;
	}

	public void setEligibleTechCivilWorkRate(String eligibleTechCivilWorkRate) {
		this.eligibleTechCivilWorkRate = eligibleTechCivilWorkRate;
	}

	public String getEligibleTechCivilWorkAmount() {
		return eligibleTechCivilWorkAmount;
	}

	public void setEligibleTechCivilWorkAmount(String eligibleTechCivilWorkAmount) {
		this.eligibleTechCivilWorkAmount = eligibleTechCivilWorkAmount;
	}

	public String getEligibleTechCivilWorkEligibleCost() {
		return eligibleTechCivilWorkEligibleCost;
	}

	public void setEligibleTechCivilWorkEligibleCost(String eligibleTechCivilWorkEligibleCost) {
		this.eligibleTechCivilWorkEligibleCost = eligibleTechCivilWorkEligibleCost;
	}

	
	
}
