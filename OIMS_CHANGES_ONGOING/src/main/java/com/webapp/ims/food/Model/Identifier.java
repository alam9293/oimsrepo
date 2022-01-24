package com.webapp.ims.food.Model;

import java.io.Serializable;

public class Identifier implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -1567462231204627616L;
	private String unit_id;

	public Identifier(String unit_Id, String control_ID) {
		super();
		unit_id = unit_Id;
		control_id = control_ID;
	}

	public Identifier() {
	}

	private String control_id;

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

}
