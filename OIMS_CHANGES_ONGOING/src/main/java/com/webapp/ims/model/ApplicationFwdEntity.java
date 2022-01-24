package com.webapp.ims.model;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "applicationFwd", schema = "loc")
public class ApplicationFwdEntity {

	private static final long serialVersionUID = 1L;

	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	@Id
	@Column(name = "id")
	private int id;

	@Column(name = "appid")
	private String appId;

	@Column(name = "name")
	private String name;

	@Column(name = "rolname")
	private String rolname;

	@Column(name = "department")
	private String department;

	@Column(name = "fwddate")
	private Timestamp fwddate;

	@Column(name = "status")
	private boolean status;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public Timestamp getFwddate() {
		return fwddate;
	}

	public void setFwddate(Timestamp fwddate) {
		this.fwddate = fwddate;
	}

	public String getRolname() {
		return rolname;
	}

	public void setRolname(String rolname) {
		this.rolname = rolname;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

}
