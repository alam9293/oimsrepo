package com.webapp.ims.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "DSC_User_Profile", schema = "loc")
public class DscUserProfileEntity {

	private static final long serialVersionUID = 1L;

	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	@Id
	@Column(name = "id")
	private int id;

	@Column(name = "dsc_name")
	private String DSC_name;

	@Column(name = "dsc_serialno")
	private String serialNo;

	@Column(name = "designation")
	private String designation;

	@Column(name = "priority")
	private int priority;

	public int getId() {
		return id;
	}

	public String getDSC_name() {
		return DSC_name;
	}

	public void setDSC_name(String dSC_name) {
		DSC_name = dSC_name;
	}

	public String getSerialNo() {
		return serialNo;
	}

	public void setSerialNo(String serialNo) {
		this.serialNo = serialNo;
	}

	public String getDesignation() {
		return designation;
	}

	public void setDesignation(String designation) {
		this.designation = designation;
	}

	public int getPriority() {
		return priority;
	}

	public void setPriority(int priority) {
		this.priority = priority;
	}

	public void setId(int id) {
		this.id = id;
	}

}
