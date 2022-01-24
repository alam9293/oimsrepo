/*
 *     Copyright 2020 - IT Solution powered by National Informatics Centre Uttar Pradesh State Unit
 *
 */
package com.webapp.ims.food.Model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author nic
 *
 */
@Entity
@Table(name = "investment_details")
//@IdClass(Identifier.class)
public class InvestmentDetailsFood implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	private String unit_id;

	private String control_id;

	
	private String id;

	@Column(name = "unittype")
	private String unittype;

	@Column(name = "totalamountoftermloansanctioned")
	private String totalamountoftermloansanctioned;

	public String getTotalamountoftermloansanctioned() {
		return totalamountoftermloansanctioned;
	}

	public void setTotalamountoftermloansanctioned(String totalamountoftermloansanctioned) {
		this.totalamountoftermloansanctioned = totalamountoftermloansanctioned;
	}

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

	public String getUnittype() {
		return unittype;
	}

	public void setUnittype(String unittype) {
		this.unittype = unittype;
	}

}
