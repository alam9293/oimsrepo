package com.webapp.ims.food.Model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "project_investment_details")
public class ProjectInvestmentDetails implements Serializable {

	 private String unit_id;
	    private String control_id;
	    @Id
	    private String id;
	    @Column(name = "itemslandcost")
	    private String itemslandcost;
	    @Column(name = "existinginvestmentlandcost")
	    private String existinginvestmentlandcost;
	    @Column(name = "proposedinvestmentlandcost")
	    private String proposedinvestmentlandcost;
	    @Column(name = "appraisedcostbybanklandcost")
	    private String appraisedcostbybanklandcost;
	    @Column(name = "itemscivilwork")
	    private String itemscivilwork;
	    @Column(name = "existinginvestmentcivilwork")
	    private String existinginvestmentcivilwork;
	    @Column(name = "proposedinvestmentcivilwork")
	    private String proposedinvestmentcivilwork;
	    @Column(name = "appraisedcostbybankcivilwork")
	    private String appraisedcostbybankcivilwork;
	    @Column(name = "itemsplantmachinery")
	    private String itemsplantmachinery;
	    @Column(name = "existinginvestmentplantmachinery")
	    private String existinginvestmentplantmachinery;
	    @Column(name = "proposedinvestmentplantmachinery")
	    private String proposedinvestmentplantmachinery;
	    @Column(name = "appraisedcostbybankplantmachinery")
	    private String appraisedcostbybankplantmachinery;
	    @Column(name = "itemspreoperativeexpenses")
	    private String itemspreoperativeexpenses;
	    @Column(name = "existinginvestmentpreoperativeexpenses")
	    private String existinginvestmentpreoperativeexpenses;
	    @Column(name = "proposedinvestmentpreoperativeexpenses")
	    private String proposedinvestmentpreoperativeexpenses;
	    @Column(name = "appraisedcostbybankpreoperativeexpenses")
	    private String appraisedcostbybankpreoperativeexpenses;
	    @Column(name = "itemsworkingcapital")
	    private String itemsworkingcapital;
	    @Column(name = "existinginvestmentmarginmoneyforworkingcapital")
	   private String existinginvestmentmarginmoneyforworkingcapital;
	    @Column(name = "proposedinvestmentmarginmoneyforworkingcapital")
		   private String proposedinvestmentmarginmoneyforworkingcapital;
	    
		   @Column(name = "appraisedcostbybankmarginmoneyforworkingcapital")
		   private String appraisedcostbybankmarginmoneyforworkingcapital;
	    
	    
	    @Column(name = "existinginvestmentworkingcapital")
	    private String existinginvestmentworkingcapital;
	    @Column(name = "proposedinvestmentworkingcapital")
	    private String proposedinvestmentworkingcapital;
	    @Column(name = "appraisedcostbybankworkingcapital")
	    private String appraisedcostbybankworkingcapital;
	    @Column(name = "itemstotal")
	    private String itemstotal;
	    @Column(name = "existinginvestmenttotal")
	    private String existinginvestmenttotal;
	    @Column(name = "proposedinvestmenttotal")
	    private String proposedinvestmenttotal;
	    @Column(name = "appraisedcostbybanktotal")
	    private String appraisedcostbybanktotal;
	    
	    @Column(name = "existinginvestmentbuilding")
	    private String existinginvestmentbuilding;
	    @Column(name = "proposedinvestmentbuilding")
	    private String proposedinvestmentbuilding;
	    @Column(name = "appraisedcostbybankbuilding")
	    private String appraisedcostbybankbuilding;
	    
	   
	    
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
	        return this.id;
	    }
	    
	    public void setId(String id) {
	        id = id;
	    }
	    
	    public String getItemslandcost() {
			return itemslandcost;
		}

		public void setItemslandcost(String itemslandcost) {
			this.itemslandcost = itemslandcost;
		}

		public String getExistinginvestmentlandcost() {
			return existinginvestmentlandcost;
		}

		public void setExistinginvestmentlandcost(String existinginvestmentlandcost) {
			this.existinginvestmentlandcost = existinginvestmentlandcost;
		}

		public String getProposedinvestmentlandcost() {
			return proposedinvestmentlandcost;
		}

		public void setProposedinvestmentlandcost(String proposedinvestmentlandcost) {
			this.proposedinvestmentlandcost = proposedinvestmentlandcost;
		}

		public String getAppraisedcostbybanklandcost() {
			return appraisedcostbybanklandcost;
		}

		public void setAppraisedcostbybanklandcost(String appraisedcostbybanklandcost) {
			this.appraisedcostbybanklandcost = appraisedcostbybanklandcost;
		}

		public String getItemscivilwork() {
			return itemscivilwork;
		}

		public void setItemscivilwork(String itemscivilwork) {
			this.itemscivilwork = itemscivilwork;
		}

		public String getExistinginvestmentcivilwork() {
			return existinginvestmentcivilwork;
		}

		public void setExistinginvestmentcivilwork(String existinginvestmentcivilwork) {
			this.existinginvestmentcivilwork = existinginvestmentcivilwork;
		}

		public String getProposedinvestmentcivilwork() {
			return proposedinvestmentcivilwork;
		}

		public void setProposedinvestmentcivilwork(String proposedinvestmentcivilwork) {
			this.proposedinvestmentcivilwork = proposedinvestmentcivilwork;
		}

		public String getAppraisedcostbybankcivilwork() {
			return appraisedcostbybankcivilwork;
		}

		public void setAppraisedcostbybankcivilwork(String appraisedcostbybankcivilwork) {
			this.appraisedcostbybankcivilwork = appraisedcostbybankcivilwork;
		}

		public String getItemsplantmachinery() {
			return itemsplantmachinery;
		}

		public void setItemsplantmachinery(String itemsplantmachinery) {
			this.itemsplantmachinery = itemsplantmachinery;
		}

		public String getExistinginvestmentplantmachinery() {
			return existinginvestmentplantmachinery;
		}

		public void setExistinginvestmentplantmachinery(String existinginvestmentplantmachinery) {
			this.existinginvestmentplantmachinery = existinginvestmentplantmachinery;
		}

		public String getProposedinvestmentplantmachinery() {
			return proposedinvestmentplantmachinery;
		}

		public void setProposedinvestmentplantmachinery(String proposedinvestmentplantmachinery) {
			this.proposedinvestmentplantmachinery = proposedinvestmentplantmachinery;
		}

		public String getAppraisedcostbybankplantmachinery() {
			return appraisedcostbybankplantmachinery;
		}

		public void setAppraisedcostbybankplantmachinery(String appraisedcostbybankplantmachinery) {
			this.appraisedcostbybankplantmachinery = appraisedcostbybankplantmachinery;
		}

		public String getItemspreoperativeexpenses() {
			return itemspreoperativeexpenses;
		}

		public void setItemspreoperativeexpenses(String itemspreoperativeexpenses) {
			this.itemspreoperativeexpenses = itemspreoperativeexpenses;
		}

		public String getExistinginvestmentpreoperativeexpenses() {
			return existinginvestmentpreoperativeexpenses;
		}

		public void setExistinginvestmentpreoperativeexpenses(String existinginvestmentpreoperativeexpenses) {
			this.existinginvestmentpreoperativeexpenses = existinginvestmentpreoperativeexpenses;
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

		public String getitemslandcost() {
	        return this.itemslandcost;
	    }
	    
	    public void setitemslandcost(final String itemslandcost) {
	        this.itemslandcost = itemslandcost;
	    }
	    
	    public String getexistinginvestmentlandcost() {
	        return this.existinginvestmentlandcost;
	    }
	    
	    public void setexistinginvestmentlandcost(final String existinginvestmentlandcost) {
	        this.existinginvestmentlandcost = existinginvestmentlandcost;
	    }
	    
	    public String getproposedinvestmentlandcost() {
	        return this.proposedinvestmentlandcost;
	    }
	    
	    public void setproposedinvestmentlandcost(final String proposedinvestmentlandcost) {
	        this.proposedinvestmentlandcost = proposedinvestmentlandcost;
	    }
	    
	    public String getappraisedcostbybanklandcost() {
	        return this.appraisedcostbybanklandcost;
	    }
	    
	    public void setappraisedcostbybanklandcost(final String appraisedcostbybanklandcost) {
	        this.appraisedcostbybanklandcost = appraisedcostbybanklandcost;
	    }
	    
	    public String getExistinginvestmentbuilding() {
			return existinginvestmentbuilding;
		}

		public void setExistinginvestmentbuilding(String existinginvestmentbuilding) {
			this.existinginvestmentbuilding = existinginvestmentbuilding;
		}

		public String getProposedinvestmentbuilding() {
			return proposedinvestmentbuilding;
		}

		public void setProposedinvestmentbuilding(String proposedinvestmentbuilding) {
			this.proposedinvestmentbuilding = proposedinvestmentbuilding;
		}

		public String getAppraisedcostbybankbuilding() {
			return appraisedcostbybankbuilding;
		}

		public void setAppraisedcostbybankbuilding(String appraisedcostbybankbuilding) {
			this.appraisedcostbybankbuilding = appraisedcostbybankbuilding;
		}

		public String getitemscivilwork() {
	        return this.itemscivilwork;
	    }
	    
	    public void setitemscivilwork(final String itemscivilwork) {
	        this.itemscivilwork = itemscivilwork;
	    }
	    
	    public String getexistinginvestmentcivilwork() {
	        return this.existinginvestmentcivilwork;
	    }
	    
	    public void setexistinginvestmentcivilwork(final String existinginvestmentcivilwork) {
	        this.existinginvestmentcivilwork = existinginvestmentcivilwork;
	    }
	    
	    public String getproposedinvestmentcivilwork() {
	        return this.proposedinvestmentcivilwork;
	    }
	    
	    public void setproposedinvestmentcivilwork(final String proposedinvestmentcivilwork) {
	        this.proposedinvestmentcivilwork = proposedinvestmentcivilwork;
	    }
	    
	    public String getappraisedcostbybankcivilwork() {
	        return this.appraisedcostbybankcivilwork;
	    }
	    
	    public void setappraisedcostbybankcivilwork(final String appraisedcostbybankcivilwork) {
	        this.appraisedcostbybankcivilwork = appraisedcostbybankcivilwork;
	    }
	    
	    public String getitemsplantmachinery() {
	        return this.itemsplantmachinery;
	    }
	    
	    public void setitemsplantmachinery(final String itemsplantmachinery) {
	        this.itemsplantmachinery = itemsplantmachinery;
	    }
	    
	    public String getexistinginvestmentplantmachinery() {
	        return this.existinginvestmentplantmachinery;
	    }
	    
	    public void setexistinginvestmentplantmachinery(final String existinginvestmentplantmachinery) {
	        this.existinginvestmentplantmachinery = existinginvestmentplantmachinery;
	    }
	    
	    public String getproposedinvestmentplantmachinery() {
	        return this.proposedinvestmentplantmachinery;
	    }
	    
	    public void setproposedinvestmentplantmachinery(final String proposedinvestmentplantmachinery) {
	        this.proposedinvestmentplantmachinery = proposedinvestmentplantmachinery;
	    }
	    
	    public String getappraisedcostbybankplantmachinery() {
	        return this.appraisedcostbybankplantmachinery;
	    }
	    
	    public void setappraisedcostbybankplantmachinery(final String appraisedcostbybankplantmachinery) {
	        this.appraisedcostbybankplantmachinery = appraisedcostbybankplantmachinery;
	    }
	    
	    public String getitemspreoperativeexpenses() {
	        return this.itemspreoperativeexpenses;
	    }
	    
	    public void setitemspreoperativeexpenses(final String itemspreoperativeexpenses) {
	        this.itemspreoperativeexpenses = itemspreoperativeexpenses;
	    }
	    
	    public String getexistinginvestmentpreoperativeexpenses() {
	        return this.existinginvestmentpreoperativeexpenses;
	    }
	    
	    public void setexistinginvestmentpreoperativeexpenses(final String existinginvestmentpreoperativeexpenses) {
	        this.existinginvestmentpreoperativeexpenses = existinginvestmentpreoperativeexpenses;
	    }
	    
	    public String getproposedinvestmentpreoperativeexpenses() {
	        return this.proposedinvestmentpreoperativeexpenses;
	    }
	    
	    public void setproposedinvestmentpreoperativeexpenses(final String proposedinvestmentpreoperativeexpenses) {
	        this.proposedinvestmentpreoperativeexpenses = proposedinvestmentpreoperativeexpenses;
	    }
	    
	    public String getappraisedcostbybankpreoperativeexpenses() {
	        return this.appraisedcostbybankpreoperativeexpenses;
	    }
	    
	    public void setappraisedcostbybankpreoperativeexpenses(final String appraisedcostbybankpreoperativeexpenses) {
	        this.appraisedcostbybankpreoperativeexpenses = appraisedcostbybankpreoperativeexpenses;
	    }
	    
	    public String getitemsworkingcapital() {
	        return this.itemsworkingcapital;
	    }
	    
	    public void setitemsworkingcapital(final String itemsworkingcapital) {
	        this.itemsworkingcapital = itemsworkingcapital;
	    }
	    
	    public String getexistinginvestmentworkingcapital() {
	        return this.existinginvestmentworkingcapital;
	    }
	    
	    public void setexistinginvestmentworkingcapital(final String existinginvestmentworkingcapital) {
	        this.existinginvestmentworkingcapital = existinginvestmentworkingcapital;
	    }
	    
	    public String getproposedinvestmentworkingcapital() {
	        return this.proposedinvestmentworkingcapital;
	    }
	    
	    public void setproposedinvestmentworkingcapital(final String proposedinvestmentworkingcapital) {
	        this.proposedinvestmentworkingcapital = proposedinvestmentworkingcapital;
	    }
	    
	    public String getappraisedcostbybankworkingcapital() {
	        return this.appraisedcostbybankworkingcapital;
	    }
	    
	    public void setappraisedcostbybankworkingcapital(final String appraisedcostbybankworkingcapital) {
	        this.appraisedcostbybankworkingcapital = appraisedcostbybankworkingcapital;
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
