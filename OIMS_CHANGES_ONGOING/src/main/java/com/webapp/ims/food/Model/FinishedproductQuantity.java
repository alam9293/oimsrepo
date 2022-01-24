package com.webapp.ims.food.Model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "finishedproduct_quantity")
public class FinishedproductQuantity implements Serializable {

	private String unit_id;

	private String control_id;

	@Id
	private String id;

	@Column(name = "quantityexported")
	private String quantityexported;

	@Column(name = "nameofcountries")
	private String nameofcountries;

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

	public String getQuantityexported() {
		return quantityexported;
	}

	public void setQuantityexported(String quantityexported) {
		this.quantityexported = quantityexported;
	}

	public String getNameofcountries() {
		return nameofcountries;
	}

	public void setNameofcountries(String nameofcountries) {
		this.nameofcountries = nameofcountries;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

}
