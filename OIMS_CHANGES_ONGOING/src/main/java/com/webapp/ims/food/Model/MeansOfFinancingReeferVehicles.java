package com.webapp.ims.food.Model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "meansof_finance")
public class MeansOfFinancingReeferVehicles implements Serializable{

	
	private static final long serialVersionUID = 1L;
	private String unit_id;
	    private String control_id;
	    @Id
	    private String id;
	    @Column(name = "itemspromoterscontribution")
	    private String itemspromoterscontribution;
	    @Column(name = "proposedinvestmentpromoterscontribution")
	    private String proposedinvestmentpromoterscontribution;
	    @Column(name = "asperappraisalofthebankpromoterscontribution")
	    private String asperappraisalofthebankpromoterscontribution;
	    @Column(name = "itemstermloan")
	    private String itemstermloan;
	    @Column(name = "proposedinvestmenttermloan")
	    private String proposedinvestmenttermloan;
	    @Column(name = "asperappraisalofthebanktermloan")
	    private String asperappraisalofthebanktermloan;
	    @Column(name = "itemsassistancefromothersources")
	    private String itemsassistancefromothersources;
	    @Column(name = "proposedinvestmentassistancefromothersources")
	    private String proposedinvestmentassistancefromothersources;
	    @Column(name = "asperappraisalofthebankassistancefromothersources")
	    private String asperappraisalofthebankassistancefromothersources;
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
		public String getItemspromoterscontribution() {
			return itemspromoterscontribution;
		}
		public void setItemspromoterscontribution(String itemspromoterscontribution) {
			this.itemspromoterscontribution = itemspromoterscontribution;
		}
		public String getProposedinvestmentpromoterscontribution() {
			return proposedinvestmentpromoterscontribution;
		}
		public void setProposedinvestmentpromoterscontribution(String proposedinvestmentpromoterscontribution) {
			this.proposedinvestmentpromoterscontribution = proposedinvestmentpromoterscontribution;
		}
		public String getAsperappraisalofthebankpromoterscontribution() {
			return asperappraisalofthebankpromoterscontribution;
		}
		public void setAsperappraisalofthebankpromoterscontribution(String asperappraisalofthebankpromoterscontribution) {
			this.asperappraisalofthebankpromoterscontribution = asperappraisalofthebankpromoterscontribution;
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
		public String getItemsassistancefromothersources() {
			return itemsassistancefromothersources;
		}
		public void setItemsassistancefromothersources(String itemsassistancefromothersources) {
			this.itemsassistancefromothersources = itemsassistancefromothersources;
		}
		public String getProposedinvestmentassistancefromothersources() {
			return proposedinvestmentassistancefromothersources;
		}
		public void setProposedinvestmentassistancefromothersources(String proposedinvestmentassistancefromothersources) {
			this.proposedinvestmentassistancefromothersources = proposedinvestmentassistancefromothersources;
		}
		public String getAsperappraisalofthebankassistancefromothersources() {
			return asperappraisalofthebankassistancefromothersources;
		}
		public void setAsperappraisalofthebankassistancefromothersources(
				String asperappraisalofthebankassistancefromothersources) {
			this.asperappraisalofthebankassistancefromothersources = asperappraisalofthebankassistancefromothersources;
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
	    
	    
	    
	
}
