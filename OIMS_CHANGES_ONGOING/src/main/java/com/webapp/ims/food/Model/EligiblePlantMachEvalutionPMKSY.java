package com.webapp.ims.food.Model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="eligibleplantmachevalutionpmksy")
public class EligiblePlantMachEvalutionPMKSY implements Serializable {
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	@Id
	@Column(name = "id")
	private String id;

	
	@Column(name="unit_id")
	private String unitId;
	
	@Column(name="control_id")
	private String control_id;
	
	
	@Column(name = "eligible_cost_name_of_plant_mach")
	private String eligibleCostNameOfPlantMach;

	@Column(name = "eligible_cost_name_of_supplier_company")
	private String eligibleCostNameOfSupplierCompany;

	@Column(name = "eligible_cost_basic_price")
	private String eligibleCostBasicPrice;

	@Column(name = "eligible_cost_total")
	private String eligibleCostTotal;

	@Column(name = "eligible_cost_in_lacs")
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

	public String getEligibleCostNameOfPlantMach() {
		return eligibleCostNameOfPlantMach;
	}

	public void setEligibleCostNameOfPlantMach(String eligibleCostNameOfPlantMach) {
		this.eligibleCostNameOfPlantMach = eligibleCostNameOfPlantMach;
	}

	public String getEligibleCostNameOfSupplierCompany() {
		return eligibleCostNameOfSupplierCompany;
	}

	public void setEligibleCostNameOfSupplierCompany(String eligibleCostNameOfSupplierCompany) {
		this.eligibleCostNameOfSupplierCompany = eligibleCostNameOfSupplierCompany;
	}

	public String getEligibleCostBasicPrice() {
		return eligibleCostBasicPrice;
	}

	public void setEligibleCostBasicPrice(String eligibleCostBasicPrice) {
		this.eligibleCostBasicPrice = eligibleCostBasicPrice;
	}

	public String getEligibleCostTotal() {
		return eligibleCostTotal;
	}

	public void setEligibleCostTotal(String eligibleCostTotal) {
		this.eligibleCostTotal = eligibleCostTotal;
	}

	public String getEligibleCostInLacs() {
		return eligibleCostInLacs;
	}

	public void setEligibleCostInLacs(String eligibleCostInLacs) {
		this.eligibleCostInLacs = eligibleCostInLacs;
	}



	
	
	


}
