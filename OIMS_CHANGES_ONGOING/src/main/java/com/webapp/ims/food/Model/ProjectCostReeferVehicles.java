package com.webapp.ims.food.Model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "project_cost")
public class ProjectCostReeferVehicles implements Serializable{

	private static final long serialVersionUID = 1L;
	private String unit_id;
	    private String control_id;
	    @Id
	    private String id;
	    @Column(name = "itemsreefervehiclesmobileprecoolingvan")
	    private String itemsreefervehiclesmobileprecoolingvan;
	    @Column(name = "proposedinvestmentreefervehiclesmobileprecoolingvan")
	    private String proposedinvestmentreefervehiclesmobileprecoolingvan;
	    @Column(name = "appraisedcostbybankreefervehiclesmobileprecoolingvan")
	    private String appraisedcostbybankreefervehiclesmobileprecoolingvan;
	    @Column(name = "itemspreoperativeexpenses")
	    private String itemspreoperativeexpenses;
	    @Column(name = "proposedinvestmentpreoperativeexpenses")
	    private String proposedinvestmentpreoperativeexpenses;
	    @Column(name = "appraisedcostbybankpreoperativeexpenses")
	    private String appraisedcostbybankpreoperativeexpenses;
	    @Column(name = "itemsworkingcapital")
	    private String itemsworkingcapital;
	    @Column(name = "proposedinvestmentworkingcapital")
	    private String proposedinvestmentworkingcapital;
	    @Column(name = "appraisedcostbybankworkingcapital")
	    private String appraisedcostbybankworkingcapital;
	    @Column(name = "itemstotal")
	    private String itemstotal;
	    @Column(name = "proposedinvestmenttotal")
	    private String proposedinvestmenttotal;
	    @Column(name = "appraisedcostbybanktotal")
	    private String appraisedcostbybanktotal;
		
	    
	    
	    
		
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
		public String getItemsreefervehiclesmobileprecoolingvan() {
			return itemsreefervehiclesmobileprecoolingvan;
		}
		public void setItemsreefervehiclesmobileprecoolingvan(String itemsreefervehiclesmobileprecoolingvan) {
			this.itemsreefervehiclesmobileprecoolingvan = itemsreefervehiclesmobileprecoolingvan;
		}
		public String getProposedinvestmentreefervehiclesmobileprecoolingvan() {
			return proposedinvestmentreefervehiclesmobileprecoolingvan;
		}
		public void setProposedinvestmentreefervehiclesmobileprecoolingvan(
				String proposedinvestmentreefervehiclesmobileprecoolingvan) {
			this.proposedinvestmentreefervehiclesmobileprecoolingvan = proposedinvestmentreefervehiclesmobileprecoolingvan;
		}
		public String getAppraisedcostbybankreefervehiclesmobileprecoolingvan() {
			return appraisedcostbybankreefervehiclesmobileprecoolingvan;
		}
		public void setAppraisedcostbybankreefervehiclesmobileprecoolingvan(
				String appraisedcostbybankreefervehiclesmobileprecoolingvan) {
			this.appraisedcostbybankreefervehiclesmobileprecoolingvan = appraisedcostbybankreefervehiclesmobileprecoolingvan;
		}
		public String getItemspreoperativeexpenses() {
			return itemspreoperativeexpenses;
		}
		public void setItemspreoperativeexpenses(String itemspreoperativeexpenses) {
			this.itemspreoperativeexpenses = itemspreoperativeexpenses;
		}
		public String getProposedinvestmentpreoperativeexpenses() {
			return proposedinvestmentpreoperativeexpenses;
		}
		public void setProposedinvestmentpreoperativeexpenses(String proposedinvestmentpreoperativeexpenses) {
			this.proposedinvestmentpreoperativeexpenses = proposedinvestmentpreoperativeexpenses;
		}
		public String getAppraisedcostbybankpreoperativeexpenses() {
			return appraisedcostbybankpreoperativeexpenses;
		}
		public void setAppraisedcostbybankpreoperativeexpenses(String appraisedcostbybankpreoperativeexpenses) {
			this.appraisedcostbybankpreoperativeexpenses = appraisedcostbybankpreoperativeexpenses;
		}
		public String getItemsworkingcapital() {
			return itemsworkingcapital;
		}
		public void setItemsworkingcapital(String itemsworkingcapital) {
			this.itemsworkingcapital = itemsworkingcapital;
		}
		public String getProposedinvestmentworkingcapital() {
			return proposedinvestmentworkingcapital;
		}
		public void setProposedinvestmentworkingcapital(String proposedinvestmentworkingcapital) {
			this.proposedinvestmentworkingcapital = proposedinvestmentworkingcapital;
		}
		public String getAppraisedcostbybankworkingcapital() {
			return appraisedcostbybankworkingcapital;
		}
		public void setAppraisedcostbybankworkingcapital(String appraisedcostbybankworkingcapital) {
			this.appraisedcostbybankworkingcapital = appraisedcostbybankworkingcapital;
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
		public String getAppraisedcostbybanktotal() {
			return appraisedcostbybanktotal;
		}
		public void setAppraisedcostbybanktotal(String appraisedcostbybanktotal) {
			this.appraisedcostbybanktotal = appraisedcostbybanktotal;
		}
	    
	    

}
