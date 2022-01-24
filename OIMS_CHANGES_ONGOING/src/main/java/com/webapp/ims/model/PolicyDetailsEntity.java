package com.webapp.ims.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "Policy_Details", schema = "loc")
public class PolicyDetailsEntity {

	private static final long serialVersionUID = 1L;

	@Id
	private int id;
	private String appdid;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getAppdid() {
		return appdid;
	}

	public void setAppdid(String appdid) {
		this.appdid = appdid;
	}

}
