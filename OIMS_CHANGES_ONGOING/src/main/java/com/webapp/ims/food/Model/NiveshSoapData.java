package com.webapp.ims.food.Model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "nivesh_soap_data")
public class NiveshSoapData implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Column(name = "unit_id")
	private String unit_id;

	private String control_id;

	@Id
	@Column(name = "id")
	private String id;

	public String getOccupier_State_ID() {
		return Occupier_State_ID;
	}

	public void setOccupier_State_ID(String occupier_State_ID) {
		Occupier_State_ID = occupier_State_ID;
	}

	public String getIndustry_District_Id() {
		return Industry_District_Id;
	}

	public void setIndustry_District_Id(String industry_District_Id) {
		Industry_District_Id = industry_District_Id;
	}

	public String getOrganization_Type_ID() {
		return Organization_Type_ID;
	}

	public void setOrganization_Type_ID(String organization_Type_ID) {
		Organization_Type_ID = organization_Type_ID;
	}

	public String getOccupier_Country_Id() {
		return Occupier_Country_Id;
	}

	public void setOccupier_Country_Id(String occupier_Country_Id) {
		Occupier_Country_Id = occupier_Country_Id;
	}

	public String getOccupier_District_ID() {
		return Occupier_District_ID;
	}

	public void setOccupier_District_ID(String occupier_District_ID) {
		Occupier_District_ID = occupier_District_ID;
	}

	public String getIndustry_Type_ID() {
		return Industry_Type_ID;
	}

	public void setIndustry_Type_ID(String industry_Type_ID) {
		Industry_Type_ID = industry_Type_ID;
	}

	private String Industry_District_Id;

	public String getCompany_name() {
		return company_name;
	}

	public void setCompany_name(String company_name) {
		this.company_name = company_name;
	}

	public String getAppid() {
		return appid;
	}

	public void setAppid(String appid) {
		this.appid = appid;
	}

	public String getOccupier_district_name() {
		return occupier_district_name;
	}

	public void setOccupier_district_name(String occupier_district_name) {
		this.occupier_district_name = occupier_district_name;
	}

	public String getUnit_Category() {
		return Unit_Category;
	}

	public void setUnit_Category(String unit_Category) {
		Unit_Category = unit_Category;
	}

	private String Organization_Type_ID;
	private String Occupier_Country_Id;

	private String Occupier_District_ID;
	private String Industry_Type_ID;

	@Column(name = "company_name")
	private String company_name;

	private String Occupier_State_ID;

	@Column(name = "appid")
	private String appid;

	@Column(name = "occupier_district_name")
	private String occupier_district_name;

	private String Unit_Category;
	private String status;
	private String Service_ID;
	private String divison;

	public String getDivison() {
		return divison;
	}

	public void setDivison(String divison) {
		this.divison = divison;
	}

	public String getService_ID() {
		return Service_ID;
	}

	public void setService_ID(String service_ID) {
		Service_ID = service_ID;
	}

	@Column(name = "policy_id")
	private String policy_id;

	@Column(name = "policy_name")
	private String policy_name;

	public String getPolicy_id() {
		return policy_id;
	}

	public void setPolicy_id(String policy_id) {
		this.policy_id = policy_id;
	}

	public String getPolicy_name() {
		return policy_name;
	}

	public void setPolicy_name(String policy_name) {
		this.policy_name = policy_name;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
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

}
