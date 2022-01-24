package com.webapp.ims.food.Model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

@Entity
@Table(name = "capacityofthe_plant_or_unit")
@IdClass(Identifier.class)
public class CapacityofthePlantOrUnit

{

	@Column(name = "unit_id")
	private String unit_id;

	private String control_id;

	@Id
	private String id;

	@Column(name = "itemscapacity")
	private String itemscapacity;
	@Column(name = "unittypecapacity")
	private String unittypecapacity;
	@Column(name = "existingunitcapacity")
	private String existingunitcapacity;
	@Column(name = "newunitcapacity")
	private String newunitcapacity;
	@Column(name = "totalafterexpansioncapacity")
	private String totalafterexpansioncapacity;

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

	public String getItemscapacity() {
		return itemscapacity;
	}

	public void setItemscapacity(String itemscapacity) {
		this.itemscapacity = itemscapacity;
	}

	public String getUnittypecapacity() {
		return unittypecapacity;
	}

	public void setUnittypecapacity(String unittypecapacity) {
		this.unittypecapacity = unittypecapacity;
	}

	public String getExistingunitcapacity() {
		return existingunitcapacity;
	}

	public void setExistingunitcapacity(String existingunitcapacity) {
		this.existingunitcapacity = existingunitcapacity;
	}

	public String getNewunitcapacity() {
		return newunitcapacity;
	}

	public void setNewunitcapacity(String newunitcapacity) {
		this.newunitcapacity = newunitcapacity;
	}

	public String getTotalafterexpansioncapacity() {
		return totalafterexpansioncapacity;
	}

	public void setTotalafterexpansioncapacity(String totalafterexpansioncapacity) {
		this.totalafterexpansioncapacity = totalafterexpansioncapacity;
	}

}
