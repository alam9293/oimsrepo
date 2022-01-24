package com.webapp.ims.dis.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;



@Entity
@Table(name="SGST_Reim_Expansion_Diversification",  schema = "loc")
public class SGSTReimExpanDivers implements Serializable{
	
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "SGST_APC_ID")
	private String sgstExpApcId;
	
	@Column(name = "Duration_Fin_Yr")
	private String durationFinYr;
	
	@Column(name = "Turnover_Of_Production")
	private String turnoverOfProduction;
	
	@Column(name = "Financial_Yr_Period")
	private String financialYrPeriod;
	
	@Column(name = "TurnOver_Part_Full_Yr_Divers")
	private String turnOverPartFullYrDivers;
	

	@Column(name = "Incre_Turnover")
	private String increTurnover;
	

	@Column(name = "Ttl_Net_SGST_Paid_FinYr")
	private String ttlNetSGSTPaidFinYr;
	
	@Column(name = "Ttl_Net_SGST_Paid_Incre_TurnOver")
	private String ttlNetSGSTPaidIncreTurnOver;

	@Column(name = "Amt_Net_SGST_Reim_Cliam")
	private String amtNetSGSTReimCliam;


	@Column(name = "Amt_Of_Net_SGST_Reim_Eligibility")
	private String amtOfNetSGSTReimEligibility;



	public String getSgstExpApcId() {
		return sgstExpApcId;
	}


	public void setSgstExpApcId(String sgstExpApcId) {
		this.sgstExpApcId = sgstExpApcId;
	}


	public String getDurationFinYr() {
		return durationFinYr;
	}


	public void setDurationFinYr(String durationFinYr) {
		this.durationFinYr = durationFinYr;
	}


	public String getTurnoverOfProduction() {
		return turnoverOfProduction;
	}


	public void setTurnoverOfProduction(String turnoverOfProduction) {
		this.turnoverOfProduction = turnoverOfProduction;
	}


	public String getFinancialYrPeriod() {
		return financialYrPeriod;
	}


	public void setFinancialYrPeriod(String financialYrPeriod) {
		this.financialYrPeriod = financialYrPeriod;
	}


	public String getTurnOverPartFullYrDivers() {
		return turnOverPartFullYrDivers;
	}


	public void setTurnOverPartFullYrDivers(String turnOverPartFullYrDivers) {
		this.turnOverPartFullYrDivers = turnOverPartFullYrDivers;
	}


	public String getIncreTurnover() {
		return increTurnover;
	}


	public void setIncreTurnover(String increTurnover) {
		this.increTurnover = increTurnover;
	}


	public String getTtlNetSGSTPaidFinYr() {
		return ttlNetSGSTPaidFinYr;
	}


	public void setTtlNetSGSTPaidFinYr(String ttlNetSGSTPaidFinYr) {
		this.ttlNetSGSTPaidFinYr = ttlNetSGSTPaidFinYr;
	}


	public String getTtlNetSGSTPaidIncreTurnOver() {
		return ttlNetSGSTPaidIncreTurnOver;
	}


	public void setTtlNetSGSTPaidIncreTurnOver(String ttlNetSGSTPaidIncreTurnOver) {
		this.ttlNetSGSTPaidIncreTurnOver = ttlNetSGSTPaidIncreTurnOver;
	}


	public String getAmtNetSGSTReimCliam() {
		return amtNetSGSTReimCliam;
	}


	public void setAmtNetSGSTReimCliam(String amtNetSGSTReimCliam) {
		this.amtNetSGSTReimCliam = amtNetSGSTReimCliam;
	}


	public String getAmtOfNetSGSTReimEligibility() {
		return amtOfNetSGSTReimEligibility;
	}


	public void setAmtOfNetSGSTReimEligibility(String amtOfNetSGSTReimEligibility) {
		this.amtOfNetSGSTReimEligibility = amtOfNetSGSTReimEligibility;
	}


	

}
