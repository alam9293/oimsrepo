package com.webapp.ims.food.Model;



import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

@Entity
@Table(name = "project_detail")
@IdClass(Identifier.class)
public class FoodTQMprojectDetails implements Serializable
{
	@Id
	@Column(name = "unit_id")
	private String unit_id;

	@Id
	@Column(name = "control_id")
	private String control_id;

	@Id
	@Column(name = "id")
	private Identifier id;

	@Column(name = "technology")
    private String technology;
    @Column(name = "capacityutilizationinlastcompletedyear")
    private String capacityutilizationinlastcompletedyear;
    @Column(name = "nameoftheproject")
    private String nameoftheproject;
    @Column(name = "locationareaofthemanufacturingunit")
    private String locationareaofthemanufacturingunit;
    @Column(name = "detailsandexperienceoftheconsultant")
    private String detailsandexperienceoftheconsultant;
    @Column(name = "detailsandexperienceofcertifyingbody")
    private String detailsandexperienceofcertifyingbody;
    @Column(name = "detailsofaccreditationbody")
    private String detailsofaccreditationbody;
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
	public Identifier getId() {
		return id;
	}
	public void setId(Identifier id) {
		this.id = id;
	}
	public String getTechnology() {
		return technology;
	}
	public void setTechnology(String technology) {
		this.technology = technology;
	}
	public String getCapacityutilizationinlastcompletedyear() {
		return capacityutilizationinlastcompletedyear;
	}
	public void setCapacityutilizationinlastcompletedyear(String capacityutilizationinlastcompletedyear) {
		this.capacityutilizationinlastcompletedyear = capacityutilizationinlastcompletedyear;
	}
	public String getNameoftheproject() {
		return nameoftheproject;
	}
	public void setNameoftheproject(String nameoftheproject) {
		this.nameoftheproject = nameoftheproject;
	}
	public String getLocationareaofthemanufacturingunit() {
		return locationareaofthemanufacturingunit;
	}
	public void setLocationareaofthemanufacturingunit(String locationareaofthemanufacturingunit) {
		this.locationareaofthemanufacturingunit = locationareaofthemanufacturingunit;
	}
	public String getDetailsandexperienceoftheconsultant() {
		return detailsandexperienceoftheconsultant;
	}
	public void setDetailsandexperienceoftheconsultant(String detailsandexperienceoftheconsultant) {
		this.detailsandexperienceoftheconsultant = detailsandexperienceoftheconsultant;
	}
	public String getDetailsandexperienceofcertifyingbody() {
		return detailsandexperienceofcertifyingbody;
	}
	public void setDetailsandexperienceofcertifyingbody(String detailsandexperienceofcertifyingbody) {
		this.detailsandexperienceofcertifyingbody = detailsandexperienceofcertifyingbody;
	}
	public String getDetailsofaccreditationbody() {
		return detailsofaccreditationbody;
	}
	public void setDetailsofaccreditationbody(String detailsofaccreditationbody) {
		this.detailsofaccreditationbody = detailsofaccreditationbody;
	}
    
}


