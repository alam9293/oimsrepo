package com.webapp.ims.food.Model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "detailsof_paymentforpreparationof_bankable_project")
public class DetailsofPaymentforpreparationofBankableProjectIACVIHUBIL implements Serializable{

	 private String unit_id;
	    private String control_id;
	    @Id
	    private String id;
	    @Column(name = "voucherno")
	    private String voucherno;
	    @Column(name = "voucherdate")
	    private String voucherdate;
	    @Column(name = "amount")
	    private String amount;
	    @Column(name = "tds")
	    private String tds;
	    @Column(name = "totalpaidamount")
	    private String totalpaidamount;
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
		public String getVoucherno() {
			return voucherno;
		}
		public void setVoucherno(String voucherno) {
			this.voucherno = voucherno;
		}
		public String getVoucherdate() {
			return voucherdate;
		}
		public void setVoucherdate(String voucherdate) {
			this.voucherdate = voucherdate;
		}
		public String getAmount() {
			return amount;
		}
		public void setAmount(String amount) {
			this.amount = amount;
		}
		public String getTds() {
			return tds;
		}
		public void setTds(String tds) {
			this.tds = tds;
		}
		public String getTotalpaidamount() {
			return totalpaidamount;
		}
		public void setTotalpaidamount(String totalpaidamount) {
			this.totalpaidamount = totalpaidamount;
		}
		public String getAction() {
			return action;
		}
		public void setAction(String action) {
			this.action = action;
		}
	    
	    
}
