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
@Table(name = "financial_status")
///@IdClass(Identifier.class)
public class FinancialStatus {

	@Column(name = "unit_id")
	private String unit_id;

	private String control_id;

	@Id
	private String id;

	@Column(name = "financialyear")
	private String financialyear;

	@Column(name = "turnover")
	private String turnover;

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

	public String getFinancialyear() {
		return financialyear;
	}

	public void setFinancialyear(String financialyear) {
		this.financialyear = financialyear;
	}

	public String getTurnover() {
		return turnover;
	}

	public void setTurnover(String turnover) {
		this.turnover = turnover;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	@Override
	public String toString() {
		return "FinancialStatus [unit_id=" + unit_id + ", control_id=" + control_id + ", id=" + id + ", financialyear="
				+ financialyear + ", turnover=" + turnover + ", action=" + action + "]";
	}

}
