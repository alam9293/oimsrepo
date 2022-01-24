package com.webapp.ims.food.Model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "meansof_finance")
public class MeansofFinancingTQM implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private String unit_id;
	    private String control_id;
	    @Id
	    private String id;
	    
	    @Column(name = "itemspromotersequity")
	    private String itemspromotersequity;
	    @Column(name = "costpromotersequity")
	    private String costpromotersequity;
	    @Column(name = "itemstermloan")
	    private String itemstermloan;
	    @Column(name = "costtermloan")
	    private String costtermloan;
	    @Column(name = "itemsworkingcapital")
	    private String itemsworkingcapital;
	    @Column(name = "costworkingcapital")
	    private String costworkingcapital;
	    @Column(name = "itemsothers")
	    private String itemsothers;
	    @Column(name = "costothers")
	    private String costothers;
	    @Column(name = "itemstotal")
	    private String itemstotal;
	    @Column(name = "costtotal")
	    private String costtotal;
		
	    
	    
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
		public String getItemspromotersequity() {
			return itemspromotersequity;
		}
		public void setItemspromotersequity(String itemspromotersequity) {
			this.itemspromotersequity = itemspromotersequity;
		}
		public String getCostpromotersequity() {
			return costpromotersequity;
		}
		public void setCostpromotersequity(String costpromotersequity) {
			this.costpromotersequity = costpromotersequity;
		}
		public String getItemstermloan() {
			return itemstermloan;
		}
		public void setItemstermloan(String itemstermloan) {
			this.itemstermloan = itemstermloan;
		}
		public String getCosttermloan() {
			return costtermloan;
		}
		public void setCosttermloan(String costtermloan) {
			this.costtermloan = costtermloan;
		}
		public String getItemsworkingcapital() {
			return itemsworkingcapital;
		}
		public void setItemsworkingcapital(String itemsworkingcapital) {
			this.itemsworkingcapital = itemsworkingcapital;
		}
		public String getCostworkingcapital() {
			return costworkingcapital;
		}
		public void setCostworkingcapital(String costworkingcapital) {
			this.costworkingcapital = costworkingcapital;
		}
		public String getItemsothers() {
			return itemsothers;
		}
		public void setItemsothers(String itemsothers) {
			this.itemsothers = itemsothers;
		}
		public String getCostothers() {
			return costothers;
		}
		public void setCostothers(String costothers) {
			this.costothers = costothers;
		}
		public String getItemstotal() {
			return itemstotal;
		}
		public void setItemstotal(String itemstotal) {
			this.itemstotal = itemstotal;
		}
		public String getCosttotal() {
			return costtotal;
		}
		public void setCosttotal(String costtotal) {
			this.costtotal = costtotal;
		}
	    
}


