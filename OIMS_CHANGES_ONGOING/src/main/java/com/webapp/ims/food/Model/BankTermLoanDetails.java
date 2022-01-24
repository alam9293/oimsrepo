package com.webapp.ims.food.Model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "bank_term_loan_details")
public class BankTermLoanDetails implements Serializable{

	
	private static final long serialVersionUID = 1L;
	private String unit_id;
	    private String control_id;
	    @Id
	    private String id;
	    @Column(name = "itemifsccode")
	    private String itemifsccode;
	    @Column(name = "detailsifsccode")
	    private String detailsifsccode;
	    @Column(name = "itemnameofthebankfi")
	    private String itemnameofthebankfi;
	    @Column(name = "detailsnameofthebankfi")
	    private String detailsnameofthebankfi;
	    @Column(name = "itembranchaddress")
	    private String itembranchaddress;
	    @Column(name = "detailsbranchaddress")
	    private String detailsbranchaddress;
	    @Column(name = "itembankemail")
	    private String itembankemail;
	    @Column(name = "detailsbankemail")
	    private String detailsbankemail;
	    @Column(name = "itembanktelephone")
	    private String itembanktelephone;
	    @Column(name = "detailsbanktelephone")
	    private String detailsbanktelephone;
	    @Column(name = "itemaccountno")
	    private String itemaccountno;
	    @Column(name = "detailsaccountno")
	    private String detailsaccountno;
	    @Column(name = "itemrateofinterest")
	    private String itemrateofinterest;
	    @Column(name = "detailsrateofinterest")
	    private String detailsrateofinterest;
	    @Column(name = "itemdateofsanctionoftermloan")
	    private String itemdateofsanctionoftermloan;
	    @Column(name = "detailsdateofsanctionoftermloan")
	    private String detailsdateofsanctionoftermloan;
	    @Column(name = "itemdateoffirstdisbursement")
	    private String itemdateoffirstdisbursement;
	    @Column(name = "detailsdateoffirstdisbursement")
	    private String detailsdateoffirstdisbursement;
	    @Column(name = "itemdateoflastdisbursement")
	    private String itemdateoflastdisbursement;
	    @Column(name = "detailsdateoflastdisbursement")
	    private String detailsdateoflastdisbursement;
	    @Column(name = "itemtotalamountoftermloansanctioned")
	    private String itemtotalamountoftermloansanctioned;
	    @Column(name = "detailstotalamountoftermloansanctioned")
	    private String detailstotalamountoftermloansanctioned;
	    @Column(name = "itemtotalamountoftermloansanctionedfornewplantmachinerysparestec")
	    private String itemtotalamountoftermloansanctionedfornewplantmachinerysparestec;
	    @Column(name = "detailstotalamountoftermloansanctionedfornewplantmachineryspares")
	    private String detailstotalamountoftermloansanctionedfornewplantmachineryspares;
	    @Column(name = "itemtotalamountoftermloandisbursed")
	    private String itemtotalamountoftermloandisbursed;
	    @Column(name = "detailstotalamountoftermloandisbursed")
	    private String detailstotalamountoftermloandisbursed;
	    @Column(name = "itemtotalamountoffirstdisbursement")
	    private String itemtotalamountoffirstdisbursement;
	    @Column(name = "detailstotalamountoffirstdisbursement")
	    private String detailstotalamountoffirstdisbursement;
	    @Column(name = "itemtotalamountoflastdisbursement")
	    private String itemtotalamountoflastdisbursement;
	    @Column(name = "detailstotalamountoflastdisbursement")
	    private String detailstotalamountoflastdisbursement;
	  
	    
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

		public String getItemifsccode() {
			return itemifsccode;
		}

		public void setItemifsccode(String itemifsccode) {
			this.itemifsccode = itemifsccode;
		}

		public String getDetailsifsccode() {
			return detailsifsccode;
		}

		public void setDetailsifsccode(String detailsifsccode) {
			this.detailsifsccode = detailsifsccode;
		}

		public String getItemnameofthebankfi() {
			return itemnameofthebankfi;
		}

		public void setItemnameofthebankfi(String itemnameofthebankfi) {
			this.itemnameofthebankfi = itemnameofthebankfi;
		}

		public String getDetailsnameofthebankfi() {
			return detailsnameofthebankfi;
		}

		public void setDetailsnameofthebankfi(String detailsnameofthebankfi) {
			this.detailsnameofthebankfi = detailsnameofthebankfi;
		}

		public String getItembranchaddress() {
			return itembranchaddress;
		}

		public void setItembranchaddress(String itembranchaddress) {
			this.itembranchaddress = itembranchaddress;
		}

		public String getDetailsbranchaddress() {
			return detailsbranchaddress;
		}

		public void setDetailsbranchaddress(String detailsbranchaddress) {
			this.detailsbranchaddress = detailsbranchaddress;
		}

		public String getItembankemail() {
			return itembankemail;
		}

		public void setItembankemail(String itembankemail) {
			this.itembankemail = itembankemail;
		}

		public String getDetailsbankemail() {
			return detailsbankemail;
		}

		public void setDetailsbankemail(String detailsbankemail) {
			this.detailsbankemail = detailsbankemail;
		}

		public String getItembanktelephone() {
			return itembanktelephone;
		}

		public void setItembanktelephone(String itembanktelephone) {
			this.itembanktelephone = itembanktelephone;
		}

		public String getDetailsbanktelephone() {
			return detailsbanktelephone;
		}

		public void setDetailsbanktelephone(String detailsbanktelephone) {
			this.detailsbanktelephone = detailsbanktelephone;
		}

		public String getItemaccountno() {
			return itemaccountno;
		}

		public void setItemaccountno(String itemaccountno) {
			this.itemaccountno = itemaccountno;
		}

		public String getDetailsaccountno() {
			return detailsaccountno;
		}

		public void setDetailsaccountno(String detailsaccountno) {
			this.detailsaccountno = detailsaccountno;
		}

		public String getItemrateofinterest() {
			return itemrateofinterest;
		}

		public void setItemrateofinterest(String itemrateofinterest) {
			this.itemrateofinterest = itemrateofinterest;
		}

		public String getDetailsrateofinterest() {
			return detailsrateofinterest;
		}

		public void setDetailsrateofinterest(String detailsrateofinterest) {
			this.detailsrateofinterest = detailsrateofinterest;
		}

		public String getItemdateofsanctionoftermloan() {
			return itemdateofsanctionoftermloan;
		}

		public void setItemdateofsanctionoftermloan(String itemdateofsanctionoftermloan) {
			this.itemdateofsanctionoftermloan = itemdateofsanctionoftermloan;
		}

		public String getDetailsdateofsanctionoftermloan() {
			return detailsdateofsanctionoftermloan;
		}

		public void setDetailsdateofsanctionoftermloan(String detailsdateofsanctionoftermloan) {
			this.detailsdateofsanctionoftermloan = detailsdateofsanctionoftermloan;
		}

		public String getItemdateoffirstdisbursement() {
			return itemdateoffirstdisbursement;
		}

		public void setItemdateoffirstdisbursement(String itemdateoffirstdisbursement) {
			this.itemdateoffirstdisbursement = itemdateoffirstdisbursement;
		}

		public String getDetailsdateoffirstdisbursement() {
			return detailsdateoffirstdisbursement;
		}

		public void setDetailsdateoffirstdisbursement(String detailsdateoffirstdisbursement) {
			this.detailsdateoffirstdisbursement = detailsdateoffirstdisbursement;
		}

		public String getItemdateoflastdisbursement() {
			return itemdateoflastdisbursement;
		}

		public void setItemdateoflastdisbursement(String itemdateoflastdisbursement) {
			this.itemdateoflastdisbursement = itemdateoflastdisbursement;
		}

		public String getDetailsdateoflastdisbursement() {
			return detailsdateoflastdisbursement;
		}

		public void setDetailsdateoflastdisbursement(String detailsdateoflastdisbursement) {
			this.detailsdateoflastdisbursement = detailsdateoflastdisbursement;
		}

		public String getItemtotalamountoftermloansanctioned() {
			return itemtotalamountoftermloansanctioned;
		}

		public void setItemtotalamountoftermloansanctioned(String itemtotalamountoftermloansanctioned) {
			this.itemtotalamountoftermloansanctioned = itemtotalamountoftermloansanctioned;
		}

		public String getDetailstotalamountoftermloansanctioned() {
			return detailstotalamountoftermloansanctioned;
		}

		public void setDetailstotalamountoftermloansanctioned(String detailstotalamountoftermloansanctioned) {
			this.detailstotalamountoftermloansanctioned = detailstotalamountoftermloansanctioned;
		}

		public String getItemtotalamountoftermloansanctionedfornewplantmachinerysparestec() {
			return itemtotalamountoftermloansanctionedfornewplantmachinerysparestec;
		}

		public void setItemtotalamountoftermloansanctionedfornewplantmachinerysparestec(
				String itemtotalamountoftermloansanctionedfornewplantmachinerysparestec) {
			this.itemtotalamountoftermloansanctionedfornewplantmachinerysparestec = itemtotalamountoftermloansanctionedfornewplantmachinerysparestec;
		}

		public String getDetailstotalamountoftermloansanctionedfornewplantmachineryspares() {
			return detailstotalamountoftermloansanctionedfornewplantmachineryspares;
		}

		public void setDetailstotalamountoftermloansanctionedfornewplantmachineryspares(
				String detailstotalamountoftermloansanctionedfornewplantmachineryspares) {
			this.detailstotalamountoftermloansanctionedfornewplantmachineryspares = detailstotalamountoftermloansanctionedfornewplantmachineryspares;
		}

		public String getItemtotalamountoftermloandisbursed() {
			return itemtotalamountoftermloandisbursed;
		}

		public void setItemtotalamountoftermloandisbursed(String itemtotalamountoftermloandisbursed) {
			this.itemtotalamountoftermloandisbursed = itemtotalamountoftermloandisbursed;
		}

		public String getDetailstotalamountoftermloandisbursed() {
			return detailstotalamountoftermloandisbursed;
		}

		public void setDetailstotalamountoftermloandisbursed(String detailstotalamountoftermloandisbursed) {
			this.detailstotalamountoftermloandisbursed = detailstotalamountoftermloandisbursed;
		}

		public String getItemtotalamountoffirstdisbursement() {
			return itemtotalamountoffirstdisbursement;
		}

		public void setItemtotalamountoffirstdisbursement(String itemtotalamountoffirstdisbursement) {
			this.itemtotalamountoffirstdisbursement = itemtotalamountoffirstdisbursement;
		}

		public String getDetailstotalamountoffirstdisbursement() {
			return detailstotalamountoffirstdisbursement;
		}

		public void setDetailstotalamountoffirstdisbursement(String detailstotalamountoffirstdisbursement) {
			this.detailstotalamountoffirstdisbursement = detailstotalamountoffirstdisbursement;
		}

		public String getItemtotalamountoflastdisbursement() {
			return itemtotalamountoflastdisbursement;
		}

		public void setItemtotalamountoflastdisbursement(String itemtotalamountoflastdisbursement) {
			this.itemtotalamountoflastdisbursement = itemtotalamountoflastdisbursement;
		}

		public String getDetailstotalamountoflastdisbursement() {
			return detailstotalamountoflastdisbursement;
		}

		public void setDetailstotalamountoflastdisbursement(String detailstotalamountoflastdisbursement) {
			this.detailstotalamountoflastdisbursement = detailstotalamountoflastdisbursement;
		}

		public String getId() {
	        return this.id;
	    }
	    
	    public void setId(String id) {
	        id = id;
	    }
	    
	    public String getitemifsccode() {
	        return this.itemifsccode;
	    }
	    
	    public void setitemifsccode(final String itemifsccode) {
	        this.itemifsccode = itemifsccode;
	    }
	    
	    public String getdetailsifsccode() {
	        return this.detailsifsccode;
	    }
	    
	    public void setdetailsifsccode(final String detailsifsccode) {
	        this.detailsifsccode = detailsifsccode;
	    }
	    
	    public String getitemnameofthebankfi() {
	        return this.itemnameofthebankfi;
	    }
	    
	    public void setitemnameofthebankfi(final String itemnameofthebankfi) {
	        this.itemnameofthebankfi = itemnameofthebankfi;
	    }
	    
	    public String getdetailsnameofthebankfi() {
	        return this.detailsnameofthebankfi;
	    }
	    
	    public void setdetailsnameofthebankfi(final String detailsnameofthebankfi) {
	        this.detailsnameofthebankfi = detailsnameofthebankfi;
	    }
	    
	    public String getitembranchaddress() {
	        return this.itembranchaddress;
	    }
	    
	    public void setitembranchaddress(final String itembranchaddress) {
	        this.itembranchaddress = itembranchaddress;
	    }
	    
	    public String getdetailsbranchaddress() {
	        return this.detailsbranchaddress;
	    }
	    
	    public void setdetailsbranchaddress(final String detailsbranchaddress) {
	        this.detailsbranchaddress = detailsbranchaddress;
	    }
	    
	    public String getitembankemail() {
	        return this.itembankemail;
	    }
	    
	    public void setitembankemail(final String itembankemail) {
	        this.itembankemail = itembankemail;
	    }
	    
	    public String getdetailsbankemail() {
	        return this.detailsbankemail;
	    }
	    
	    public void setdetailsbankemail(final String detailsbankemail) {
	        this.detailsbankemail = detailsbankemail;
	    }
	    
	    public String getitembanktelephone() {
	        return this.itembanktelephone;
	    }
	    
	    public void setitembanktelephone(final String itembanktelephone) {
	        this.itembanktelephone = itembanktelephone;
	    }
	    
	    public String getdetailsbanktelephone() {
	        return this.detailsbanktelephone;
	    }
	    
	    public void setdetailsbanktelephone(final String detailsbanktelephone) {
	        this.detailsbanktelephone = detailsbanktelephone;
	    }
	    
	    public String getitemaccountno() {
	        return this.itemaccountno;
	    }
	    
	    public void setitemaccountno(final String itemaccountno) {
	        this.itemaccountno = itemaccountno;
	    }
	    
	    public String getdetailsaccountno() {
	        return this.detailsaccountno;
	    }
	    
	    public void setdetailsaccountno(final String detailsaccountno) {
	        this.detailsaccountno = detailsaccountno;
	    }
	    
	    public String getitemrateofinterest() {
	        return this.itemrateofinterest;
	    }
	    
	    public void setitemrateofinterest(final String itemrateofinterest) {
	        this.itemrateofinterest = itemrateofinterest;
	    }
	    
	    public String getdetailsrateofinterest() {
	        return this.detailsrateofinterest;
	    }
	    
	    public void setdetailsrateofinterest(final String detailsrateofinterest) {
	        this.detailsrateofinterest = detailsrateofinterest;
	    }
	    
	    public String getitemdateofsanctionoftermloan() {
	        return this.itemdateofsanctionoftermloan;
	    }
	    
	    public void setitemdateofsanctionoftermloan(final String itemdateofsanctionoftermloan) {
	        this.itemdateofsanctionoftermloan = itemdateofsanctionoftermloan;
	    }
	    
	    public String getdetailsdateofsanctionoftermloan() {
	        return this.detailsdateofsanctionoftermloan;
	    }
	    
	    public void setdetailsdateofsanctionoftermloan(final String detailsdateofsanctionoftermloan) {
	        this.detailsdateofsanctionoftermloan = detailsdateofsanctionoftermloan;
	    }
	    
	    public String getitemdateoffirstdisbursement() {
	        return this.itemdateoffirstdisbursement;
	    }
	    
	    public void setitemdateoffirstdisbursement(final String itemdateoffirstdisbursement) {
	        this.itemdateoffirstdisbursement = itemdateoffirstdisbursement;
	    }
	    
	    public String getdetailsdateoffirstdisbursement() {
	        return this.detailsdateoffirstdisbursement;
	    }
	    
	    public void setdetailsdateoffirstdisbursement(final String detailsdateoffirstdisbursement) {
	        this.detailsdateoffirstdisbursement = detailsdateoffirstdisbursement;
	    }
	    
	    public String getitemdateoflastdisbursement() {
	        return this.itemdateoflastdisbursement;
	    }
	    
	    public void setitemdateoflastdisbursement(final String itemdateoflastdisbursement) {
	        this.itemdateoflastdisbursement = itemdateoflastdisbursement;
	    }
	    
	    public String getdetailsdateoflastdisbursement() {
	        return this.detailsdateoflastdisbursement;
	    }
	    
	    public void setdetailsdateoflastdisbursement(final String detailsdateoflastdisbursement) {
	        this.detailsdateoflastdisbursement = detailsdateoflastdisbursement;
	    }
	    
	    public String getitemtotalamountoftermloansanctioned() {
	        return this.itemtotalamountoftermloansanctioned;
	    }
	    
	    public void setitemtotalamountoftermloansanctioned(final String itemtotalamountoftermloansanctioned) {
	        this.itemtotalamountoftermloansanctioned = itemtotalamountoftermloansanctioned;
	    }
	    
	    public String getdetailstotalamountoftermloansanctioned() {
	        return this.detailstotalamountoftermloansanctioned;
	    }
	    
	    public void setdetailstotalamountoftermloansanctioned(final String detailstotalamountoftermloansanctioned) {
	        this.detailstotalamountoftermloansanctioned = detailstotalamountoftermloansanctioned;
	    }
	    
	    public String getitemtotalamountoftermloansanctionedfornewplantmachinerysparestec() {
	        return this.itemtotalamountoftermloansanctionedfornewplantmachinerysparestec;
	    }
	    
	    public void setitemtotalamountoftermloansanctionedfornewplantmachinerysparestec(final String itemtotalamountoftermloansanctionedfornewplantmachinerysparestec) {
	        this.itemtotalamountoftermloansanctionedfornewplantmachinerysparestec = itemtotalamountoftermloansanctionedfornewplantmachinerysparestec;
	    }
	    
	    public String getdetailstotalamountoftermloansanctionedfornewplantmachineryspares() {
	        return this.detailstotalamountoftermloansanctionedfornewplantmachineryspares;
	    }
	    
	    public void setdetailstotalamountoftermloansanctionedfornewplantmachineryspares(final String detailstotalamountoftermloansanctionedfornewplantmachineryspares) {
	        this.detailstotalamountoftermloansanctionedfornewplantmachineryspares = detailstotalamountoftermloansanctionedfornewplantmachineryspares;
	    }
	    
	    public String getitemtotalamountoftermloandisbursed() {
	        return this.itemtotalamountoftermloandisbursed;
	    }
	    
	    public void setitemtotalamountoftermloandisbursed(final String itemtotalamountoftermloandisbursed) {
	        this.itemtotalamountoftermloandisbursed = itemtotalamountoftermloandisbursed;
	    }
	    
	    public String getdetailstotalamountoftermloandisbursed() {
	        return this.detailstotalamountoftermloandisbursed;
	    }
	    
	    public void setdetailstotalamountoftermloandisbursed(final String detailstotalamountoftermloandisbursed) {
	        this.detailstotalamountoftermloandisbursed = detailstotalamountoftermloandisbursed;
	    }
	    
	    public String getitemtotalamountoffirstdisbursement() {
	        return this.itemtotalamountoffirstdisbursement;
	    }
	    
	    public void setitemtotalamountoffirstdisbursement(final String itemtotalamountoffirstdisbursement) {
	        this.itemtotalamountoffirstdisbursement = itemtotalamountoffirstdisbursement;
	    }
	    
	    public String getdetailstotalamountoffirstdisbursement() {
	        return this.detailstotalamountoffirstdisbursement;
	    }
	    
	    public void setdetailstotalamountoffirstdisbursement(final String detailstotalamountoffirstdisbursement) {
	        this.detailstotalamountoffirstdisbursement = detailstotalamountoffirstdisbursement;
	    }
	    
	    public String getitemtotalamountoflastdisbursement() {
	        return this.itemtotalamountoflastdisbursement;
	    }
	    
	    public void setitemtotalamountoflastdisbursement(final String itemtotalamountoflastdisbursement) {
	        this.itemtotalamountoflastdisbursement = itemtotalamountoflastdisbursement;
	    }
	    
	    public String getdetailstotalamountoflastdisbursement() {
	        return this.detailstotalamountoflastdisbursement;
	    }
	    
	    public void setdetailstotalamountoflastdisbursement(final String detailstotalamountoflastdisbursement) {
	        this.detailstotalamountoflastdisbursement = detailstotalamountoflastdisbursement;
	    }
}
