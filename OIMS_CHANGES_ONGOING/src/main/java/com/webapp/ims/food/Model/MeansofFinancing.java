package com.webapp.ims.food.Model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "meansof_financing")
public class MeansofFinancing implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private String unit_id;
	    private String control_id;
	    @Id
	    private String id;
	    @Column(name = "itemspromotersequity")
	    private String itemspromotersequity;
	    @Column(name = "existinginvestmentpromotersequity")
	    private String existinginvestmentpromotersequity;
	    @Column(name = "proposedinvestmentpromotersequity")
	    private String proposedinvestmentpromotersequity;
	    @Column(name = "appraisedcostbybankpromotersequity")
	    private String appraisedcostbybankpromotersequity;
	    @Column(name = "itemstermloan")
	    private String itemstermloan;
	    @Column(name = "existinginvestmenttermloan")
	    private String existinginvestmenttermloan;
	    @Column(name = "proposedinvestmenttermloan")
	    private String proposedinvestmenttermloan;
	    @Column(name = "appraisedcostbybanktermloan")
	    private String appraisedcostbybanktermloan;
	    @Column(name = "itemsmarginmoneyforworkingcapital")
	    private String itemsmarginmoneyforworkingcapital;
	    @Column(name = "existinginvestmentmarginmoneyforworkingcapital")
	    private String existinginvestmentmarginmoneyforworkingcapital;
	    @Column(name = "proposedinvestmentmarginmoneyforworkingcapital")
	    private String proposedinvestmentmarginmoneyforworkingcapital;
	    @Column(name = "appraisedcostbybankmarginmoneyforworkingcapital")
	    private String appraisedcostbybankmarginmoneyforworkingcapital;
	    @Column(name = "itemsothers")
	    private String itemsothers;
	    @Column(name = "existinginvestmentothers")
	    private String existinginvestmentothers;
	    @Column(name = "proposedinvestmentothers")
	    private String proposedinvestmentothers;
	    @Column(name = "appraisedcostbybankothers")
	    private String appraisedcostbybankothers;
	    @Column(name = "itemstotal")
	    private String itemstotal;
	    @Column(name = "existinginvestmenttotal")
	    private String existinginvestmenttotal;
	    @Column(name = "proposedinvestmenttotal")
	    private String proposedinvestmenttotal;
	    @Column(name = "appraisedcostbybanktotal")
	    private String appraisedcostbybanktotal;
	    
	   
	  
	    
	    public String getId() {
	        return this.id;
	    }
	    
	    public void setId(String id) {
	        id = id;
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

		public String getItemspromotersequity() {
			return itemspromotersequity;
		}

		public void setItemspromotersequity(String itemspromotersequity) {
			this.itemspromotersequity = itemspromotersequity;
		}

		public String getExistinginvestmentpromotersequity() {
			return existinginvestmentpromotersequity;
		}

		public void setExistinginvestmentpromotersequity(String existinginvestmentpromotersequity) {
			this.existinginvestmentpromotersequity = existinginvestmentpromotersequity;
		}

		public String getProposedinvestmentpromotersequity() {
			return proposedinvestmentpromotersequity;
		}

		public void setProposedinvestmentpromotersequity(String proposedinvestmentpromotersequity) {
			this.proposedinvestmentpromotersequity = proposedinvestmentpromotersequity;
		}

		public String getAppraisedcostbybankpromotersequity() {
			return appraisedcostbybankpromotersequity;
		}

		public void setAppraisedcostbybankpromotersequity(String appraisedcostbybankpromotersequity) {
			this.appraisedcostbybankpromotersequity = appraisedcostbybankpromotersequity;
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

		public String getAppraisedcostbybanktermloan() {
			return appraisedcostbybanktermloan;
		}

		public void setAppraisedcostbybanktermloan(String appraisedcostbybanktermloan) {
			this.appraisedcostbybanktermloan = appraisedcostbybanktermloan;
		}

		public String getItemsmarginmoneyforworkingcapital() {
			return itemsmarginmoneyforworkingcapital;
		}

		public void setItemsmarginmoneyforworkingcapital(String itemsmarginmoneyforworkingcapital) {
			this.itemsmarginmoneyforworkingcapital = itemsmarginmoneyforworkingcapital;
		}

		public String getExistinginvestmentmarginmoneyforworkingcapital() {
			return existinginvestmentmarginmoneyforworkingcapital;
		}

		public void setExistinginvestmentmarginmoneyforworkingcapital(String existinginvestmentmarginmoneyforworkingcapital) {
			this.existinginvestmentmarginmoneyforworkingcapital = existinginvestmentmarginmoneyforworkingcapital;
		}

		public String getProposedinvestmentmarginmoneyforworkingcapital() {
			return proposedinvestmentmarginmoneyforworkingcapital;
		}

		public void setProposedinvestmentmarginmoneyforworkingcapital(String proposedinvestmentmarginmoneyforworkingcapital) {
			this.proposedinvestmentmarginmoneyforworkingcapital = proposedinvestmentmarginmoneyforworkingcapital;
		}

		public String getAppraisedcostbybankmarginmoneyforworkingcapital() {
			return appraisedcostbybankmarginmoneyforworkingcapital;
		}

		public void setAppraisedcostbybankmarginmoneyforworkingcapital(String appraisedcostbybankmarginmoneyforworkingcapital) {
			this.appraisedcostbybankmarginmoneyforworkingcapital = appraisedcostbybankmarginmoneyforworkingcapital;
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

		public String getAppraisedcostbybankothers() {
			return appraisedcostbybankothers;
		}

		public void setAppraisedcostbybankothers(String appraisedcostbybankothers) {
			this.appraisedcostbybankothers = appraisedcostbybankothers;
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

		public String getAppraisedcostbybanktotal() {
			return appraisedcostbybanktotal;
		}

		public void setAppraisedcostbybanktotal(String appraisedcostbybanktotal) {
			this.appraisedcostbybanktotal = appraisedcostbybanktotal;
		}

		public String getitemspromotersequity() {
	        return this.itemspromotersequity;
	    }
	    
	    public void setitemspromotersequity(final String itemspromotersequity) {
	        this.itemspromotersequity = itemspromotersequity;
	    }
	    
	    public String getexistinginvestmentpromotersequity() {
	        return this.existinginvestmentpromotersequity;
	    }
	    
	    public void setexistinginvestmentpromotersequity(final String existinginvestmentpromotersequity) {
	        this.existinginvestmentpromotersequity = existinginvestmentpromotersequity;
	    }
	    
	    public String getproposedinvestmentpromotersequity() {
	        return this.proposedinvestmentpromotersequity;
	    }
	    
	    public void setproposedinvestmentpromotersequity(final String proposedinvestmentpromotersequity) {
	        this.proposedinvestmentpromotersequity = proposedinvestmentpromotersequity;
	    }
	    
	    public String getappraisedcostbybankpromotersequity() {
	        return this.appraisedcostbybankpromotersequity;
	    }
	    
	    public void setappraisedcostbybankpromotersequity(final String appraisedcostbybankpromotersequity) {
	        this.appraisedcostbybankpromotersequity = appraisedcostbybankpromotersequity;
	    }
	    
	    public String getitemstermloan() {
	        return this.itemstermloan;
	    }
	    
	    public void setitemstermloan(final String itemstermloan) {
	        this.itemstermloan = itemstermloan;
	    }
	    
	    public String getexistinginvestmenttermloan() {
	        return this.existinginvestmenttermloan;
	    }
	    
	    public void setexistinginvestmenttermloan(final String existinginvestmenttermloan) {
	        this.existinginvestmenttermloan = existinginvestmenttermloan;
	    }
	    
	    public String getproposedinvestmenttermloan() {
	        return this.proposedinvestmenttermloan;
	    }
	    
	    public void setproposedinvestmenttermloan(final String proposedinvestmenttermloan) {
	        this.proposedinvestmenttermloan = proposedinvestmenttermloan;
	    }
	    
	    public String getappraisedcostbybanktermloan() {
	        return this.appraisedcostbybanktermloan;
	    }
	    
	    public void setappraisedcostbybanktermloan(final String appraisedcostbybanktermloan) {
	        this.appraisedcostbybanktermloan = appraisedcostbybanktermloan;
	    }
	    
	    public String getitemsmarginmoneyforworkingcapital() {
	        return this.itemsmarginmoneyforworkingcapital;
	    }
	    
	    public void setitemsmarginmoneyforworkingcapital(final String itemsmarginmoneyforworkingcapital) {
	        this.itemsmarginmoneyforworkingcapital = itemsmarginmoneyforworkingcapital;
	    }
	    
	    public String getexistinginvestmentmarginmoneyforworkingcapital() {
	        return this.existinginvestmentmarginmoneyforworkingcapital;
	    }
	    
	    public void setexistinginvestmentmarginmoneyforworkingcapital(final String existinginvestmentmarginmoneyforworkingcapital) {
	        this.existinginvestmentmarginmoneyforworkingcapital = existinginvestmentmarginmoneyforworkingcapital;
	    }
	    
	    public String getproposedinvestmentmarginmoneyforworkingcapital() {
	        return this.proposedinvestmentmarginmoneyforworkingcapital;
	    }
	    
	    public void setproposedinvestmentmarginmoneyforworkingcapital(final String proposedinvestmentmarginmoneyforworkingcapital) {
	        this.proposedinvestmentmarginmoneyforworkingcapital = proposedinvestmentmarginmoneyforworkingcapital;
	    }
	    
	    public String getappraisedcostbybankmarginmoneyforworkingcapital() {
	        return this.appraisedcostbybankmarginmoneyforworkingcapital;
	    }
	    
	    public void setappraisedcostbybankmarginmoneyforworkingcapital(final String appraisedcostbybankmarginmoneyforworkingcapital) {
	        this.appraisedcostbybankmarginmoneyforworkingcapital = appraisedcostbybankmarginmoneyforworkingcapital;
	    }
	    
	    public String getitemsothers() {
	        return this.itemsothers;
	    }
	    
	    public void setitemsothers(final String itemsothers) {
	        this.itemsothers = itemsothers;
	    }
	    
	    public String getexistinginvestmentothers() {
	        return this.existinginvestmentothers;
	    }
	    
	    public void setexistinginvestmentothers(final String existinginvestmentothers) {
	        this.existinginvestmentothers = existinginvestmentothers;
	    }
	    
	    public String getproposedinvestmentothers() {
	        return this.proposedinvestmentothers;
	    }
	    
	    public void setproposedinvestmentothers(final String proposedinvestmentothers) {
	        this.proposedinvestmentothers = proposedinvestmentothers;
	    }
	    
	    public String getappraisedcostbybankothers() {
	        return this.appraisedcostbybankothers;
	    }
	    
	    public void setappraisedcostbybankothers(final String appraisedcostbybankothers) {
	        this.appraisedcostbybankothers = appraisedcostbybankothers;
	    }
	    
	    public String getitemstotal() {
	        return this.itemstotal;
	    }
	    
	    public void setitemstotal(final String itemstotal) {
	        this.itemstotal = itemstotal;
	    }
	    
	    public String getexistinginvestmenttotal() {
	        return this.existinginvestmenttotal;
	    }
	    
	    public void setexistinginvestmenttotal(final String existinginvestmenttotal) {
	        this.existinginvestmenttotal = existinginvestmenttotal;
	    }
	    
	    public String getproposedinvestmenttotal() {
	        return this.proposedinvestmenttotal;
	    }
	    
	    public void setproposedinvestmenttotal(final String proposedinvestmenttotal) {
	        this.proposedinvestmenttotal = proposedinvestmenttotal;
	    }
	    
	    public String getappraisedcostbybanktotal() {
	        return this.appraisedcostbybanktotal;
	    }
	    
	    public void setappraisedcostbybanktotal(final String appraisedcostbybanktotal) {
	        this.appraisedcostbybanktotal = appraisedcostbybanktotal;
	    }
	}


