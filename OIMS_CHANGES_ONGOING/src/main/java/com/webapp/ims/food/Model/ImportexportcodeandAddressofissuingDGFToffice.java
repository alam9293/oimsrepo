package com.webapp.ims.food.Model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "importexportcodeand_addressofissuingdgftoffice")
public class ImportexportcodeandAddressofissuingDGFToffice implements Serializable {

	private String unit_id;

	private String control_id;

	@Id
	private String id;

	@Column(name = "consignmentcode")
	private String consignmentcode;

	@Column(name = "address")
	private String address;

	@Column(name = "action")
	private String action;

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

	public String getConsignmentcode() {
		return consignmentcode;
	}

	public void setConsignmentcode(String consignmentcode) {
		this.consignmentcode = consignmentcode;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

}
