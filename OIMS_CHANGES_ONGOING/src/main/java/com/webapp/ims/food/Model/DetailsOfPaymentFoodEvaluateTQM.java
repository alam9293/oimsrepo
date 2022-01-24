package com.webapp.ims.food.Model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="detailsofpaymenttqm")
public class DetailsOfPaymentFoodEvaluateTQM implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Column(name="app_id")
	private String app_id;
	
	@Id
	@Column(name="unit_id")
	private String unitId;
	
	@Column(name="control_id")
	private String control_id;
	
	


	public String getApp_id() {
		return app_id;
	}


	public void setApp_id(String app_id) {
		this.app_id = app_id;
	}


	public String getUnitId() {
		return unitId;
	}


	public void setUnitId(String unitId) {
		this.unitId = unitId;
	}


	public String getControl_id() {
		return control_id;
	}


	public void setControl_id(String control_id) {
		this.control_id = control_id;
	}


	

}