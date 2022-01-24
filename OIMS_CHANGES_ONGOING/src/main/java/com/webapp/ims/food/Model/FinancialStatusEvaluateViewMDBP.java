package com.webapp.ims.food.Model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="financialstatusmdbp")
public class FinancialStatusEvaluateViewMDBP implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="id")
	private String id;
	
	
	@Column(name="unit_id")
	private String unitId;
	
	@Column(name="control_id")
	private String controlId;
	
	@Column(name="financial_year")
	private String financialYear;
	
	@Column(name="turn_over_in_lacs")
	private String turnOverInLacs;

	

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

	

	public String getControlId() {
		return controlId;
	}

	public void setControlId(String controlId) {
		this.controlId = controlId;
	}

	public String getFinancialYear() {
		return financialYear;
	}

	public void setFinancialYear(String financialYear) {
		this.financialYear = financialYear;
	}

	public String getTurnOverInLacs() {
		return turnOverInLacs;
	}

	public void setTurnOverInLacs(String turnOverInLacs) {
		this.turnOverInLacs = turnOverInLacs;
	}
	
	
	
}
