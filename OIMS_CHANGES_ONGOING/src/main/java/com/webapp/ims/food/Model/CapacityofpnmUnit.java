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
@Table(name = "capacityofinstalled_plantamp_manufacturing_unit")
///@IdClass(Identifier.class)
public class CapacityofpnmUnit {

	@Column(name = "unit_id")
	private String unit_id;

	private String control_id;

	@Id
	private String id;

	@Column(name = "capacitymetricton")
    private String capacitymetricton;
    @Column(name = "perdaymetricton")
    private String perdaymetricton;
    @Column(name = "peryearmetricton")
    private String peryearmetricton;
    @Column(name = "capacitymetriclitre")
    private String capacitymetriclitre;
    @Column(name = "perdaymetriclitre")
    private String perdaymetriclitre;
    @Column(name = "peryearmetriclitre")
    private String peryearmetriclitre;
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
	public String getCapacitymetricton() {
		return capacitymetricton;
	}
	public void setCapacitymetricton(String capacitymetricton) {
		this.capacitymetricton = capacitymetricton;
	}
	public String getPerdaymetricton() {
		return perdaymetricton;
	}
	public void setPerdaymetricton(String perdaymetricton) {
		this.perdaymetricton = perdaymetricton;
	}
	public String getPeryearmetricton() {
		return peryearmetricton;
	}
	public void setPeryearmetricton(String peryearmetricton) {
		this.peryearmetricton = peryearmetricton;
	}
	public String getCapacitymetriclitre() {
		return capacitymetriclitre;
	}
	public void setCapacitymetriclitre(String capacitymetriclitre) {
		this.capacitymetriclitre = capacitymetriclitre;
	}
	public String getPerdaymetriclitre() {
		return perdaymetriclitre;
	}
	public void setPerdaymetriclitre(String perdaymetriclitre) {
		this.perdaymetriclitre = perdaymetriclitre;
	}
	public String getPeryearmetriclitre() {
		return peryearmetriclitre;
	}
	public void setPeryearmetriclitre(String peryearmetriclitre) {
		this.peryearmetriclitre = peryearmetriclitre;
	}
    
}
