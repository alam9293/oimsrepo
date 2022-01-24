/*
 *     Copyright 2020 - IT Solution powered by National Informatics Centre Uttar Pradesh State Unit
 *
 */
package com.webapp.ims.food.Model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author nic
 *
 */

@Entity
@Table(name = "detailsof_proposed_plant_and_machinerytobe_installed", schema = "public")
///@IdClass(Identifier.class)
public class DetailsofProposedPlantAndMachinerytobeInstalled {

	private String unit_id;
	private String control_id;

	@Id
	private String id;
	@Column(name = "sno")
	private String sno;
	@Column(name = "machinename")
	private String machinename;
	@Column(name = "suppliername")
	private String suppliername;
	@Column(name = "invoicetype")
	private String invoicetype;
	@Column(name = "invoiceno")
	private String invoiceno;
	@Column(name = "invoicedate")
	private String invoicedate;
	@Column(name = "quantity")
	private String quantity;
	@Column(name = "basicprice")
	private String basicprice;
	@Column(name = "eligiblecost")
	private String eligiblecost;
	@Column(name = "gstamount")
	private String gstamount;
	@Column(name = "installationerectiontransporationcharges")
	private String installationerectiontransporationcharges;
	@Column(name = "totalamount")
	private String totalamount;

	@Column(name = "totalplandmachamt")
	private transient String totalplandmachamt;

	@Column(name = "eligiblecostinlacs")
	private transient String eligiblecostinlacs;

	public String getTotalplandmachamt() {
		return totalplandmachamt;
	}

	public void setTotalplandmachamt(String totalplandmachamt) {
		this.totalplandmachamt = totalplandmachamt;
	}

	public String getEligiblecostinlacs() {
		return eligiblecostinlacs;
	}

	public void setEligiblecostinlacs(String eligiblecostinlacs) {
		this.eligiblecostinlacs = eligiblecostinlacs;
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

	public String getSno() {
		return sno;
	}

	public void setSno(String sno) {
		this.sno = sno;
	}

	public String getMachinename() {
		return machinename;
	}

	public void setMachinename(String machinename) {
		this.machinename = machinename;
	}

	public String getSuppliername() {
		return suppliername;
	}

	public void setSuppliername(String suppliername) {
		this.suppliername = suppliername;
	}

	public String getInvoicetype() {
		return invoicetype;
	}

	public void setInvoicetype(String invoicetype) {
		this.invoicetype = invoicetype;
	}

	public String getInvoiceno() {
		return invoiceno;
	}

	public void setInvoiceno(String invoiceno) {
		this.invoiceno = invoiceno;
	}

	public String getInvoicedate() {
		return invoicedate;
	}

	public void setInvoicedate(String invoicedate) {
		this.invoicedate = invoicedate;
	}

	public String getQuantity() {
		return quantity;
	}

	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}

	public String getBasicprice() {
		return basicprice;
	}

	public void setBasicprice(String basicprice) {
		this.basicprice = basicprice;
	}

	public String getEligiblecost() {
		return eligiblecost;
	}

	public void setEligiblecost(String eligiblecost) {
		this.eligiblecost = eligiblecost;
	}

	public String getGstamount() {
		return gstamount;
	}

	public void setGstamount(String gstamount) {
		this.gstamount = gstamount;
	}

	public String getInstallationerectiontransporationcharges() {
		return installationerectiontransporationcharges;
	}

	public void setInstallationerectiontransporationcharges(String installationerectiontransporationcharges) {
		this.installationerectiontransporationcharges = installationerectiontransporationcharges;
	}

	public String getTotalamount() {
		return totalamount;
	}

	public void setTotalamount(String totalamount) {
		this.totalamount = totalamount;
	}

}