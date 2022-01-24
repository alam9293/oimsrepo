package com.webapp.ims.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "State_Master", schema = "loc")
public class StateDetails implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "ID")
	private Long id;

	@Column(name = "State_Code")
	private Long stateCode;

	@Column(name = "State_Name")
	private String stateName;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getStateCode() {
		return stateCode;
	}

	public void setStateCode(Long stateCode) {
		stateCode = stateCode;
	}

	public String getStateName() {
		return stateName;
	}

	public void setStateName(String stateName) {
		this.stateName = stateName;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
