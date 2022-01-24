package com.webapp.ims.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "App_Status_Master", schema = "loc")
public class AppStatusMaster implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private int id;

	@Column(name = "statuscode")
	private String statusCode;

	@Column(name = "nm_statusname")
	private String nmStatusName;

	@Column(name = "ims_statusname")
	private String imsStatusName;

	@Column(name = "statusdescription")
	private String statusDescription;

	@Column(name = "backcolor")
	private String backColor;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(String statusCode) {
		this.statusCode = statusCode;
	}

	public String getNmStatusName() {
		return nmStatusName;
	}

	public void setNmStatusName(String nmStatusName) {
		this.nmStatusName = nmStatusName;
	}

	public String getImsStatusName() {
		return imsStatusName;
	}

	public void setImsStatusName(String imsStatusName) {
		this.imsStatusName = imsStatusName;
	}

	public String getStatusDescription() {
		return statusDescription;
	}

	public void setStatusDescription(String statusDescription) {
		this.statusDescription = statusDescription;
	}

	public String getBackColor() {
		return backColor;
	}

	public void setBackColor(String backColor) {
		this.backColor = backColor;
	}

}
