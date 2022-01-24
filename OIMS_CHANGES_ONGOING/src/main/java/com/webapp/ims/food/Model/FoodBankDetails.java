/*
 *     Copyright 2020 - IT Solution powered by National Informatics Centre Uttar Pradesh State Unit
 *
 */
package com.webapp.ims.food.Model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author nic
 *
 */
@Entity
@Table(name = "bank_details")
public class FoodBankDetails implements Serializable {

	private static final long serialVersionUID = 1L;
	private String unit_id;
	private String control_id;
	@Id
	private String id;

	@Column(name = "itemsnameofthebank")
	private String itemsnameofthebank;

	@Column(name = "detailsnameofthebank")
	private String detailsnameofthebank;

	@Column(name = "itemsbranchaddress")
	private String itemsbranchaddress;

	@Column(name = "detailsbranchaddress")
	private String detailsbranchaddress;

	@Column(name = "itemsbankemail")
	private String itemsbankemail;

	@Column(name = "detailsbankemail")
	private String detailsbankemail;

	@Column(name = "itemsifsccode")
	private String itemsifsccode;

	@Column(name = "detailsifsccode")
	private String detailsifsccode;

	@Column(name = "itemsaccountno")
	private String itemsaccountno;

	@Column(name = "detailsaccountno")
	private String detailsaccountno;


	
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

	public String getItemsnameofthebank() {
		return itemsnameofthebank;
	}

	public void setItemsnameofthebank(String itemsnameofthebank) {
		this.itemsnameofthebank = itemsnameofthebank;
	}

	public String getDetailsnameofthebank() {
		return detailsnameofthebank;
	}

	public void setDetailsnameofthebank(String detailsnameofthebank) {
		this.detailsnameofthebank = detailsnameofthebank;
	}

	public String getItemsbranchaddress() {
		return itemsbranchaddress;
	}

	public void setItemsbranchaddress(String itemsbranchaddress) {
		this.itemsbranchaddress = itemsbranchaddress;
	}

	public String getDetailsbranchaddress() {
		return detailsbranchaddress;
	}

	public void setDetailsbranchaddress(String detailsbranchaddress) {
		this.detailsbranchaddress = detailsbranchaddress;
	}

	public String getItemsbankemail() {
		return itemsbankemail;
	}

	public void setItemsbankemail(String itemsbankemail) {
		this.itemsbankemail = itemsbankemail;
	}

	public String getDetailsbankemail() {
		return detailsbankemail;
	}

	public void setDetailsbankemail(String detailsbankemail) {
		this.detailsbankemail = detailsbankemail;
	}

	public String getItemsifsccode() {
		return itemsifsccode;
	}

	public void setItemsifsccode(String itemsifsccode) {
		this.itemsifsccode = itemsifsccode;
	}

	public String getDetailsifsccode() {
		return detailsifsccode;
	}

	public void setDetailsifsccode(String detailsifsccode) {
		this.detailsifsccode = detailsifsccode;
	}

	public String getItemsaccountno() {
		return itemsaccountno;
	}

	public void setItemsaccountno(String itemsaccountno) {
		this.itemsaccountno = itemsaccountno;
	}

	public String getDetailsaccountno() {
		return detailsaccountno;
	}

	public void setDetailsaccountno(String detailsaccountno) {
		this.detailsaccountno = detailsaccountno;
	}

}
