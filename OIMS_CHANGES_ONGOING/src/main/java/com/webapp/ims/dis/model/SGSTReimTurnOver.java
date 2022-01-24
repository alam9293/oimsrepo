package com.webapp.ims.dis.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name="SGST_Reim_Claim_TurnOver", schema = "loc")
public class SGSTReimTurnOver implements Serializable{
	
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "SGST_ID")
	private String sgstId;

	@Column(name = "SGST_APC_ID")
	private String sgstApcId;
	
	
	@Column(name = "SGST_Fin_Yr")
	private String sgstFinYr;
	
	@Column(name = "SGST_TurnOver_Sales_Items")
	private String sgstTurnOverSalesItems;
	
	
	@Column(name = "SGST_TurnOver_Base_Production")
	private String sgstTurnOverBaseProduction;


	public String getSgstId() {
		return sgstId;
	}


	public void setSgstId(String sgstId) {
		this.sgstId = sgstId;
	}


	public String getSgstApcId() {
		return sgstApcId;
	}


	public void setSgstApcId(String sgstApcId) {
		this.sgstApcId = sgstApcId;
	}


	public String getSgstFinYr() {
		return sgstFinYr;
	}


	public void setSgstFinYr(String sgstFinYr) {
		this.sgstFinYr = sgstFinYr;
	}


	public String getSgstTurnOverSalesItems() {
		return sgstTurnOverSalesItems;
	}


	public void setSgstTurnOverSalesItems(String sgstTurnOverSalesItems) {
		this.sgstTurnOverSalesItems = sgstTurnOverSalesItems;
	}


	public String getSgstTurnOverBaseProduction() {
		return sgstTurnOverBaseProduction;
	}


	public void setSgstTurnOverBaseProduction(String sgstTurnOverBaseProduction) {
		this.sgstTurnOverBaseProduction = sgstTurnOverBaseProduction;
	}
	
	
	


}
