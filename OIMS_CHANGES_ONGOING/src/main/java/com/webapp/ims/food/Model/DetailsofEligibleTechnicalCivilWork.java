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
@Table(name = "detailsof_eligible_technical_civil_work")
///@IdClass(Identifier.class)
public class DetailsofEligibleTechnicalCivilWork {

	private String unit_id;
    private String control_id;

	@Id
	private String id;
	@Column(name = "sno")
    private String sno;
    @Column(name = "particulars")
    private String particulars;
    @Column(name = "areasqft")
    private String areasqft;
    @Column(name = "rate")
    private String rate;
    @Column(name = "amount")
    private String amount;
    @Column(name = "eligiblecost")
    private String eligiblecost;
	
    
    
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
	public String getSno() {
		return sno;
	}
	public void setSno(String sno) {
		this.sno = sno;
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
	public String getEligiblecost() {
		return eligiblecost;
	}
	public void setEligiblecost(String eligiblecost) {
		this.eligiblecost = eligiblecost;
	}
    
	    
}