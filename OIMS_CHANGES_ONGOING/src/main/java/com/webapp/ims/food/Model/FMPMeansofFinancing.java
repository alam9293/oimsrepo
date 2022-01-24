package com.webapp.ims.food.Model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "meansof_finance")
public class FMPMeansofFinancing implements Serializable {

	private static final long serialVersionUID = 1L;
	private String unit_id;
	private String control_id;
	@Id
	private String id;

	@Column(name = "itemstermloan")
	private String itemstermloan;

	@Column(name = "proposedinvestmenttermloan")
	private String proposedinvestmenttermloan;

	@Column(name = "asperappraisalofthebanktermloan")
	private String asperappraisalofthebanktermloan;

	@Column(name = "itemsassistanceunderupfpip")
	private String itemsassistanceunderupfpip;

	@Column(name = "proposedinvestmentassistanceunderupfpip")
	private String proposedinvestmentassistanceunderupfpip;

	@Column(name = "asperappraisalofthebankassistanceunderupfpip")
	private String asperappraisalofthebankassistanceunderupfpip;

	@Column(name = "itemsgrantinaidfrommofpi")
	private String itemsgrantinaidfrommofpi;

	@Column(name = "proposedinvestmentgrantinaidfrommofpi")
	private String proposedinvestmentgrantinaidfrommofpi;

	@Column(name = "asperappraisalofthebankgrantinaidfrommofpi")
	private String asperappraisalofthebankgrantinaidfrommofpi;

	@Column(name = "itemsothers")
	private String itemsothers;

	@Column(name = "proposedinvestmentothers")
	private String proposedinvestmentothers;

	@Column(name = "asperappraisalofthebankothers")
	private String asperappraisalofthebankothers;

	@Column(name = "itemstotal")
	private String itemstotal;

	@Column(name = "proposedinvestmenttotal")
	private String proposedinvestmenttotal;

	@Column(name = "asperappraisalofthebanktotal")
	private String asperappraisalofthebanktotal;

	

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

	public String getItemstermloan() {
		return itemstermloan;
	}

	public void setItemstermloan(String itemstermloan) {
		this.itemstermloan = itemstermloan;
	}

	public String getProposedinvestmenttermloan() {
		return proposedinvestmenttermloan;
	}

	public void setProposedinvestmenttermloan(String proposedinvestmenttermloan) {
		this.proposedinvestmenttermloan = proposedinvestmenttermloan;
	}

	public String getAsperappraisalofthebanktermloan() {
		return asperappraisalofthebanktermloan;
	}

	public void setAsperappraisalofthebanktermloan(String asperappraisalofthebanktermloan) {
		this.asperappraisalofthebanktermloan = asperappraisalofthebanktermloan;
	}

	public String getItemsassistanceunderupfpip() {
		return itemsassistanceunderupfpip;
	}

	public void setItemsassistanceunderupfpip(String itemsassistanceunderupfpip) {
		this.itemsassistanceunderupfpip = itemsassistanceunderupfpip;
	}

	public String getProposedinvestmentassistanceunderupfpip() {
		return proposedinvestmentassistanceunderupfpip;
	}

	public void setProposedinvestmentassistanceunderupfpip(String proposedinvestmentassistanceunderupfpip) {
		this.proposedinvestmentassistanceunderupfpip = proposedinvestmentassistanceunderupfpip;
	}

	public String getAsperappraisalofthebankassistanceunderupfpip() {
		return asperappraisalofthebankassistanceunderupfpip;
	}

	public void setAsperappraisalofthebankassistanceunderupfpip(String asperappraisalofthebankassistanceunderupfpip) {
		this.asperappraisalofthebankassistanceunderupfpip = asperappraisalofthebankassistanceunderupfpip;
	}

	public String getItemsgrantinaidfrommofpi() {
		return itemsgrantinaidfrommofpi;
	}

	public void setItemsgrantinaidfrommofpi(String itemsgrantinaidfrommofpi) {
		this.itemsgrantinaidfrommofpi = itemsgrantinaidfrommofpi;
	}

	public String getProposedinvestmentgrantinaidfrommofpi() {
		return proposedinvestmentgrantinaidfrommofpi;
	}

	public void setProposedinvestmentgrantinaidfrommofpi(String proposedinvestmentgrantinaidfrommofpi) {
		this.proposedinvestmentgrantinaidfrommofpi = proposedinvestmentgrantinaidfrommofpi;
	}

	public String getAsperappraisalofthebankgrantinaidfrommofpi() {
		return asperappraisalofthebankgrantinaidfrommofpi;
	}

	public void setAsperappraisalofthebankgrantinaidfrommofpi(String asperappraisalofthebankgrantinaidfrommofpi) {
		this.asperappraisalofthebankgrantinaidfrommofpi = asperappraisalofthebankgrantinaidfrommofpi;
	}

	public String getItemsothers() {
		return itemsothers;
	}

	public void setItemsothers(String itemsothers) {
		this.itemsothers = itemsothers;
	}

	public String getProposedinvestmentothers() {
		return proposedinvestmentothers;
	}

	public void setProposedinvestmentothers(String proposedinvestmentothers) {
		this.proposedinvestmentothers = proposedinvestmentothers;
	}

	public String getAsperappraisalofthebankothers() {
		return asperappraisalofthebankothers;
	}

	public void setAsperappraisalofthebankothers(String asperappraisalofthebankothers) {
		this.asperappraisalofthebankothers = asperappraisalofthebankothers;
	}

	public String getItemstotal() {
		return itemstotal;
	}

	public void setItemstotal(String itemstotal) {
		this.itemstotal = itemstotal;
	}

	public String getProposedinvestmenttotal() {
		return proposedinvestmenttotal;
	}

	public void setProposedinvestmenttotal(String proposedinvestmenttotal) {
		this.proposedinvestmenttotal = proposedinvestmenttotal;
	}

	public String getAsperappraisalofthebanktotal() {
		return asperappraisalofthebanktotal;
	}

	public void setAsperappraisalofthebanktotal(String asperappraisalofthebanktotal) {
		this.asperappraisalofthebanktotal = asperappraisalofthebanktotal;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
