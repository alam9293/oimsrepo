package com.webapp.ims.food.Model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="foodeligiblecostofplantmach")
public class FoodElegibleCostOfPlantMach implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "id")
	private String id;

	
	@Column(name="unit_id")
	private String unitId;
	
	@Column(name="control_id")
	private String control_id;
	
	@Column(name="name_of_plant_mach")
	private String nameOfPlantMach;
	
	@Column(name="name_of_supplier_company")
	private String nameOfSupplierCompany;
	
	@Column(name="basic_price")
	private String basicPrice;
	
	@Column(name="total_plant_mach")
	private String totalPlantMach;
	
	@Column(name="eligible_cost_in_lacs")
	private String eligibleCostInLacsPM;

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

	public String getNameOfPlantMach() {
		return nameOfPlantMach;
	}

	public void setNameOfPlantMach(String nameOfPlantMach) {
		this.nameOfPlantMach = nameOfPlantMach;
	}

	public String getNameOfSupplierCompany() {
		return nameOfSupplierCompany;
	}

	public void setNameOfSupplierCompany(String nameOfSupplierCompany) {
		this.nameOfSupplierCompany = nameOfSupplierCompany;
	}

	public String getBasicPrice() {
		return basicPrice;
	}

	public void setBasicPrice(String basicPrice) {
		this.basicPrice = basicPrice;
	}

	public String getTotalPlantMach() {
		return totalPlantMach;
	}

	public void setTotalPlantMach(String totalPlantMach) {
		this.totalPlantMach = totalPlantMach;
	}


	public String getEligibleCostInLacsPM() {
		return eligibleCostInLacsPM;
	}

	public void setEligibleCostInLacsPM(String eligibleCostInLacsPM) {
		this.eligibleCostInLacsPM = eligibleCostInLacsPM;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	


	
	
	
	
	
}
