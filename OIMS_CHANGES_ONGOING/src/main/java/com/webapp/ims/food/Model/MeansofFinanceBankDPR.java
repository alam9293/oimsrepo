package com.webapp.ims.food.Model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "meansof_finance")
public class MeansofFinanceBankDPR implements Serializable {

	private static final long serialVersionUID = 1L;
	private String unit_id;
	private String control_id;
	@Id
	private String id;

	@Column(name = "itemsequity")
	private String itemsequity;

	@Column(name = "existinginvestmentequity")
	private String existinginvestmentequity;

	@Column(name = "proposedinvestmentequity")
	private String proposedinvestmentequity;

	@Column(name = "asperappraisalofthebankequity")
	private String asperappraisalofthebankequity;

	@Column(name = "itemstermloan")
	private String itemstermloan;

	@Column(name = "existinginvestmenttermloan")
	private String existinginvestmenttermloan;

	@Column(name = "proposedinvestmenttermloan")
	private String proposedinvestmenttermloan;

	@Column(name = "asperappraisalofthebanktermloan")
	private String asperappraisalofthebanktermloan;

	@Column(name = "itemsworkingcapital")
	private String itemsworkingcapital;

	@Column(name = "existinginvestmentworkingcapital")
	private String existinginvestmentworkingcapital;

	@Column(name = "proposedinvestmentworkingcapital")
	private String proposedinvestmentworkingcapital;

	@Column(name = "asperappraisalofthebankworkingcapital")
	private String asperappraisalofthebankworkingcapital;

	@Column(name = "itemsothers")
	private String itemsothers;

	@Column(name = "existinginvestmentothers")
	private String existinginvestmentothers;

	@Column(name = "proposedinvestmentothers")
	private String proposedinvestmentothers;

	@Column(name = "asperappraisalofthebankothers")
	private String asperappraisalofthebankothers;

	@Column(name = "itemsconsultancyfeescharge")
	private String itemsconsultancyfeescharge;

	@Column(name = "existinginvestmentconsultancyfeescharge")
	private String existinginvestmentconsultancyfeescharge;

	@Column(name = "proposedinvestmentconsultancyfeescharge")
	private String proposedinvestmentconsultancyfeescharge;

	@Column(name = "asperappraisalofthebankconsultancyfeescharge")
	private String asperappraisalofthebankconsultancyfeescharge;

	@Column(name = "itemstotal")
	private String itemstotal;

	@Column(name = "existinginvestmenttotal")
	private String existinginvestmenttotal;

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

	public String getItemsequity() {
		return itemsequity;
	}

	public void setItemsequity(String itemsequity) {
		this.itemsequity = itemsequity;
	}

	public String getExistinginvestmentequity() {
		return existinginvestmentequity;
	}

	public void setExistinginvestmentequity(String existinginvestmentequity) {
		this.existinginvestmentequity = existinginvestmentequity;
	}

	public String getProposedinvestmentequity() {
		return proposedinvestmentequity;
	}

	public void setProposedinvestmentequity(String proposedinvestmentequity) {
		this.proposedinvestmentequity = proposedinvestmentequity;
	}

	public String getAsperappraisalofthebankequity() {
		return asperappraisalofthebankequity;
	}

	public void setAsperappraisalofthebankequity(String asperappraisalofthebankequity) {
		this.asperappraisalofthebankequity = asperappraisalofthebankequity;
	}

	public String getItemstermloan() {
		return itemstermloan;
	}

	public void setItemstermloan(String itemstermloan) {
		this.itemstermloan = itemstermloan;
	}

	public String getExistinginvestmenttermloan() {
		return existinginvestmenttermloan;
	}

	public void setExistinginvestmenttermloan(String existinginvestmenttermloan) {
		this.existinginvestmenttermloan = existinginvestmenttermloan;
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

	public String getItemsworkingcapital() {
		return itemsworkingcapital;
	}

	public void setItemsworkingcapital(String itemsworkingcapital) {
		this.itemsworkingcapital = itemsworkingcapital;
	}

	public String getExistinginvestmentworkingcapital() {
		return existinginvestmentworkingcapital;
	}

	public void setExistinginvestmentworkingcapital(String existinginvestmentworkingcapital) {
		this.existinginvestmentworkingcapital = existinginvestmentworkingcapital;
	}

	public String getProposedinvestmentworkingcapital() {
		return proposedinvestmentworkingcapital;
	}

	public void setProposedinvestmentworkingcapital(String proposedinvestmentworkingcapital) {
		this.proposedinvestmentworkingcapital = proposedinvestmentworkingcapital;
	}

	public String getAsperappraisalofthebankworkingcapital() {
		return asperappraisalofthebankworkingcapital;
	}

	public void setAsperappraisalofthebankworkingcapital(String asperappraisalofthebankworkingcapital) {
		this.asperappraisalofthebankworkingcapital = asperappraisalofthebankworkingcapital;
	}

	public String getItemsothers() {
		return itemsothers;
	}

	public void setItemsothers(String itemsothers) {
		this.itemsothers = itemsothers;
	}

	public String getExistinginvestmentothers() {
		return existinginvestmentothers;
	}

	public void setExistinginvestmentothers(String existinginvestmentothers) {
		this.existinginvestmentothers = existinginvestmentothers;
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

	public String getItemsconsultancyfeescharge() {
		return itemsconsultancyfeescharge;
	}

	public void setItemsconsultancyfeescharge(String itemsconsultancyfeescharge) {
		this.itemsconsultancyfeescharge = itemsconsultancyfeescharge;
	}

	public String getExistinginvestmentconsultancyfeescharge() {
		return existinginvestmentconsultancyfeescharge;
	}

	public void setExistinginvestmentconsultancyfeescharge(String existinginvestmentconsultancyfeescharge) {
		this.existinginvestmentconsultancyfeescharge = existinginvestmentconsultancyfeescharge;
	}

	public String getProposedinvestmentconsultancyfeescharge() {
		return proposedinvestmentconsultancyfeescharge;
	}

	public void setProposedinvestmentconsultancyfeescharge(String proposedinvestmentconsultancyfeescharge) {
		this.proposedinvestmentconsultancyfeescharge = proposedinvestmentconsultancyfeescharge;
	}

	public String getAsperappraisalofthebankconsultancyfeescharge() {
		return asperappraisalofthebankconsultancyfeescharge;
	}

	public void setAsperappraisalofthebankconsultancyfeescharge(String asperappraisalofthebankconsultancyfeescharge) {
		this.asperappraisalofthebankconsultancyfeescharge = asperappraisalofthebankconsultancyfeescharge;
	}

	public String getItemstotal() {
		return itemstotal;
	}

	public void setItemstotal(String itemstotal) {
		this.itemstotal = itemstotal;
	}

	public String getExistinginvestmenttotal() {
		return existinginvestmenttotal;
	}

	public void setExistinginvestmenttotal(String existinginvestmenttotal) {
		this.existinginvestmenttotal = existinginvestmenttotal;
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

}
