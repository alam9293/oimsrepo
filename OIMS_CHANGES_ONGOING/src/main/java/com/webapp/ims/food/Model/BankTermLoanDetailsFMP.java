package com.webapp.ims.food.Model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "bank_term_loan_details")
public class BankTermLoanDetailsFMP implements Serializable {

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

	@Column(name = "itemsifsccode")
	private String itemsifsccode;

	@Column(name = "detailsifsccode")
	private String detailsifsccode;

	@Column(name = "itemsaccountno")
	private String itemsaccountno;

	@Column(name = "detailsaccountno")
	private String detailsaccountno;

	@Column(name = "itemsdateofcommercialproduction")
	private String itemsdateofcommercialproduction;

	@Column(name = "detailsdateofcommercialproduction")
	private String detailsdateofcommercialproduction;

	@Column(name = "itemstotalamountoftermloansanctioned")
	private String itemstotalamountoftermloansanctioned;

	@Column(name = "detailstotalamountoftermloansanctioned")
	private String detailstotalamountoftermloansanctioned;

	@Column(name = "itemsdateofsanction")
	private String itemsdateofsanction;

	@Column(name = "detailsdateofsanction")
	private String detailsdateofsanction;

	@Column(name = "itemsrateofinterest")
	private String itemsrateofinterest;

	@Column(name = "detailsrateofinterest")
	private String detailsrateofinterest;

	@Column(name = "itemsamountoftermloandisbursed")
	private String itemsamountoftermloandisbursed;

	@Column(name = "detailsamountoftermloandisbursed")
	private String detailsamountoftermloandisbursed;

	@Column(name = "itemsdateoffirstdisbursement")
	private String itemsdateoffirstdisbursement;

	@Column(name = "detailsdateoffirstdisbursement")
	private String detailsdateoffirstdisbursement;

	
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

	public String getItemsdateofcommercialproduction() {
		return itemsdateofcommercialproduction;
	}

	public void setItemsdateofcommercialproduction(String itemsdateofcommercialproduction) {
		this.itemsdateofcommercialproduction = itemsdateofcommercialproduction;
	}

	public String getDetailsdateofcommercialproduction() {
		return detailsdateofcommercialproduction;
	}

	public void setDetailsdateofcommercialproduction(String detailsdateofcommercialproduction) {
		this.detailsdateofcommercialproduction = detailsdateofcommercialproduction;
	}

	public String getItemstotalamountoftermloansanctioned() {
		return itemstotalamountoftermloansanctioned;
	}

	public void setItemstotalamountoftermloansanctioned(String itemstotalamountoftermloansanctioned) {
		this.itemstotalamountoftermloansanctioned = itemstotalamountoftermloansanctioned;
	}

	public String getDetailstotalamountoftermloansanctioned() {
		return detailstotalamountoftermloansanctioned;
	}

	public void setDetailstotalamountoftermloansanctioned(String detailstotalamountoftermloansanctioned) {
		this.detailstotalamountoftermloansanctioned = detailstotalamountoftermloansanctioned;
	}

	public String getItemsdateofsanction() {
		return itemsdateofsanction;
	}

	public void setItemsdateofsanction(String itemsdateofsanction) {
		this.itemsdateofsanction = itemsdateofsanction;
	}

	public String getDetailsdateofsanction() {
		return detailsdateofsanction;
	}

	public void setDetailsdateofsanction(String detailsdateofsanction) {
		this.detailsdateofsanction = detailsdateofsanction;
	}

	public String getItemsrateofinterest() {
		return itemsrateofinterest;
	}

	public void setItemsrateofinterest(String itemsrateofinterest) {
		this.itemsrateofinterest = itemsrateofinterest;
	}

	public String getDetailsrateofinterest() {
		return detailsrateofinterest;
	}

	public void setDetailsrateofinterest(String detailsrateofinterest) {
		this.detailsrateofinterest = detailsrateofinterest;
	}

	public String getItemsamountoftermloandisbursed() {
		return itemsamountoftermloandisbursed;
	}

	public void setItemsamountoftermloandisbursed(String itemsamountoftermloandisbursed) {
		this.itemsamountoftermloandisbursed = itemsamountoftermloandisbursed;
	}

	public String getDetailsamountoftermloandisbursed() {
		return detailsamountoftermloandisbursed;
	}

	public void setDetailsamountoftermloandisbursed(String detailsamountoftermloandisbursed) {
		this.detailsamountoftermloandisbursed = detailsamountoftermloandisbursed;
	}

	public String getItemsdateoffirstdisbursement() {
		return itemsdateoffirstdisbursement;
	}

	public void setItemsdateoffirstdisbursement(String itemsdateoffirstdisbursement) {
		this.itemsdateoffirstdisbursement = itemsdateoffirstdisbursement;
	}

	public String getDetailsdateoffirstdisbursement() {
		return detailsdateoffirstdisbursement;
	}

	public void setDetailsdateoffirstdisbursement(String detailsdateoffirstdisbursement) {
		this.detailsdateoffirstdisbursement = detailsdateoffirstdisbursement;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
