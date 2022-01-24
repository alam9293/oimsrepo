package com.webapp.ims.uam.model;

import java.util.List;

public class LoadFormsNameRequestParam {
	
	private String departmentID;
	private String formdropdownId;
	private List<TblForm>formNamelist;
	public List<TblForm> getFormNamelist() {
		return formNamelist;
	}
	public void setFormNamelist(List<TblForm> formNamelist) {
		this.formNamelist = formNamelist;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	private String status;
	public String getDepartmentID() {
		return departmentID;
	}
	public void setDepartmentID(String departmentID) {
		this.departmentID = departmentID;
	}
	public String getFormdropdownId() {
		return formdropdownId;
	}
	public void setFormdropdownId(String formdropdownId) {
		this.formdropdownId = formdropdownId;
	}
	

}
