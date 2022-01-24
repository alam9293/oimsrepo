package com.webapp.ims.food.Model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "capacityofthe_plant")
public class CapacityofthePlantFood implements Serializable {

	private String unit_id;

	private String control_id;

	@Id
	private String id;

	@Column(name = "itemcapacity")
	private String itemcapacity;

	@Column(name = "unitcapacity")
	private String unitcapacity;

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

	public String getItemcapacity() {
		return itemcapacity;
	}

	public void setItemcapacity(String itemcapacity) {
		this.itemcapacity = itemcapacity;
	}

	public String getUnitcapacity() {
		return unitcapacity;
	}

	public void setUnitcapacity(String unitcapacity) {
		this.unitcapacity = unitcapacity;
	}

}
