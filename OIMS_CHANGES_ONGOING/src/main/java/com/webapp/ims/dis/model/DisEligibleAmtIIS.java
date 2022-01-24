/**
 * Author:: Gyan
* Created on:: 15/02/2021 
 */

package com.webapp.ims.dis.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "Dis_EligibleAmt_IIS", schema = "loc")
public class DisEligibleAmtIIS implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "IIS_Elig_Amt_Id")
	private String eligibleAmtIISId;

	@Column(name = "IIS_Apc_Id")
	private String apcIdIIS;

	// I Row
	@Column(name = "IIS_Year1")
	private String iisFinYr1;
	@Column(name = "IIS_Ttl_Loan_Amt1")
	private String iisTtlLoanAmt1;

	@Column(name = "IIS_Date_Of_Disb1")
	private String iisDateOfDisb1;

	@Column(name = "IIS_Act_Amt_IP1")
	private String iisActAmtIP1;

	@Column(name = "Date_Of_Pay1")
	private String dateOfPay1;

	@Column(name = "Prop_Int_Infra1")
	private String propIntInfra1;

	@Column(name = "Prop_Int_Infra_PA1")
	private String propIntInfraPA1;

	@Column(name = "Eligible_IIS1")
	private String eligibleIIS1;

	// II Row
	@Column(name = "IIS_Year2")
	private String iisFinYr2;

	@Column(name = "IIS_Ttl_Loan_Amt2")
	private String iisTtlLoanAmt2;

	@Column(name = "IIS_Date_Of_Disb2")
	private String iisDateOfDisb2;

	@Column(name = "IIS_Act_Amt_IP2")
	private String iisActAmtIP2;

	@Column(name = "Date_Of_Pay2")
	private String dateOfPay2;

	@Column(name = "Prop_Int_Infra2")
	private String propIntInfra2;

	@Column(name = "Prop_Int_Infra_PA2")
	private String propIntInfraPA2;

	@Column(name = "Eligible_IIS2")
	private String eligibleIIS2;

	// III Row
	@Column(name = "IIS_Year3")
	private String iisFinYr3;

	@Column(name = "IIS_Ttl_Loan_Amt3")
	private String iisTtlLoanAmt3;

	@Column(name = "IIS_Date_Of_Disb3")
	private String iisDateOfDisb3;

	@Column(name = "IIS_Act_Amt_IP3")
	private String iisActAmtIP3;

	@Column(name = "Date_Of_Pay3")
	private String dateOfPay3;

	@Column(name = "Prop_Int_Infra3")
	private String propIntInfra3;

	@Column(name = "Prop_Int_Infra_PA3")
	private String propIntInfraPA3;

	@Column(name = "Eligible_IIS3")
	private String eligibleIIS3;

	// IV Row
	@Column(name = "IIS_Year4")
	private String iisFinYr4;

	@Column(name = "IIS_Ttl_Loan_Amt4")
	private String iisTtlLoanAmt4;

	@Column(name = "IIS_Date_Of_Disb4")
	private String iisDateOfDisb4;

	@Column(name = "IIS_Act_Amt_IP4")
	private String iisActAmtIP4;

	@Column(name = "Date_Of_Pay4")
	private String dateOfPay4;

	@Column(name = "Prop_Int_Infra4")
	private String propIntInfra4;

	@Column(name = "Prop_Int_Infra_PA4")
	private String propIntInfraPA4;

	@Column(name = "Eligible_IIS4")
	private String eligibleIIS4;

	// V Row
	@Column(name = "IIS_Year5")
	private String iisFinYr5;

	@Column(name = "IIS_Ttl_Loan_Amt5")
	private String iisTtlLoanAmt5;

	@Column(name = "IIS_Date_Of_Disb5")
	private String iisDateOfDisb5;

	@Column(name = "IIS_Act_Amt_IP5")
	private String iisActAmtIP5;

	@Column(name = "Date_Of_Pay5")
	private String dateOfPay5;

	@Column(name = "Prop_Int_Infra5")
	private String propIntInfra5;

	@Column(name = "Prop_Int_Infra_PA5")
	private String propIntInfraPA5;

	@Column(name = "Eligible_IIS5")
	private String eligibleIIS5;

	@Column(name = "Elig_Amt_IIS_Observ")
	private String eligAmtIisObserv;

	public String getEligibleAmtIISId() {
		return eligibleAmtIISId;
	}

	public void setEligibleAmtIISId(String eligibleAmtIISId) {
		this.eligibleAmtIISId = eligibleAmtIISId;
	}

	public String getApcIdIIS() {
		return apcIdIIS;
	}

	public void setApcIdIIS(String apcIdIIS) {
		this.apcIdIIS = apcIdIIS;
	}

	public String getIisFinYr1() {
		return iisFinYr1;
	}

	public void setIisFinYr1(String iisFinYr1) {
		this.iisFinYr1 = iisFinYr1;
	}

	public String getIisDateOfDisb1() {
		return iisDateOfDisb1;
	}

	public void setIisDateOfDisb1(String iisDateOfDisb1) {
		this.iisDateOfDisb1 = iisDateOfDisb1;
	}

	public String getIisActAmtIP1() {
		return iisActAmtIP1;
	}

	public void setIisActAmtIP1(String iisActAmtIP1) {
		this.iisActAmtIP1 = iisActAmtIP1;
	}

	public String getDateOfPay1() {
		return dateOfPay1;
	}

	public void setDateOfPay1(String dateOfPay1) {
		this.dateOfPay1 = dateOfPay1;
	}

	public String getPropIntInfra1() {
		return propIntInfra1;
	}

	public void setPropIntInfra1(String propIntInfra1) {
		this.propIntInfra1 = propIntInfra1;
	}

	public String getPropIntInfraPA1() {
		return propIntInfraPA1;
	}

	public void setPropIntInfraPA1(String propIntInfraPA1) {
		this.propIntInfraPA1 = propIntInfraPA1;
	}

	public String getEligibleIIS1() {
		return eligibleIIS1;
	}

	public void setEligibleIIS1(String eligibleIIS1) {
		this.eligibleIIS1 = eligibleIIS1;
	}

	public String getIisFinYr2() {
		return iisFinYr2;
	}

	public void setIisFinYr2(String iisFinYr2) {
		this.iisFinYr2 = iisFinYr2;
	}

	public String getIisDateOfDisb2() {
		return iisDateOfDisb2;
	}

	public void setIisDateOfDisb2(String iisDateOfDisb2) {
		this.iisDateOfDisb2 = iisDateOfDisb2;
	}

	public String getIisActAmtIP2() {
		return iisActAmtIP2;
	}

	public void setIisActAmtIP2(String iisActAmtIP2) {
		this.iisActAmtIP2 = iisActAmtIP2;
	}

	public String getDateOfPay2() {
		return dateOfPay2;
	}

	public void setDateOfPay2(String dateOfPay2) {
		this.dateOfPay2 = dateOfPay2;
	}

	public String getPropIntInfra2() {
		return propIntInfra2;
	}

	public void setPropIntInfra2(String propIntInfra2) {
		this.propIntInfra2 = propIntInfra2;
	}

	public String getPropIntInfraPA2() {
		return propIntInfraPA2;
	}

	public void setPropIntInfraPA2(String propIntInfraPA2) {
		this.propIntInfraPA2 = propIntInfraPA2;
	}

	public String getEligibleIIS2() {
		return eligibleIIS2;
	}

	public void setEligibleIIS2(String eligibleIIS2) {
		this.eligibleIIS2 = eligibleIIS2;
	}

	public String getIisFinYr3() {
		return iisFinYr3;
	}

	public void setIisFinYr3(String iisFinYr3) {
		this.iisFinYr3 = iisFinYr3;
	}

	public String getIisDateOfDisb3() {
		return iisDateOfDisb3;
	}

	public void setIisDateOfDisb3(String iisDateOfDisb3) {
		this.iisDateOfDisb3 = iisDateOfDisb3;
	}

	public String getIisActAmtIP3() {
		return iisActAmtIP3;
	}

	public void setIisActAmtIP3(String iisActAmtIP3) {
		this.iisActAmtIP3 = iisActAmtIP3;
	}

	public String getDateOfPay3() {
		return dateOfPay3;
	}

	public void setDateOfPay3(String dateOfPay3) {
		this.dateOfPay3 = dateOfPay3;
	}

	public String getPropIntInfra3() {
		return propIntInfra3;
	}

	public void setPropIntInfra3(String propIntInfra3) {
		this.propIntInfra3 = propIntInfra3;
	}

	public String getPropIntInfraPA3() {
		return propIntInfraPA3;
	}

	public void setPropIntInfraPA3(String propIntInfraPA3) {
		this.propIntInfraPA3 = propIntInfraPA3;
	}

	public String getEligibleIIS3() {
		return eligibleIIS3;
	}

	public void setEligibleIIS3(String eligibleIIS3) {
		this.eligibleIIS3 = eligibleIIS3;
	}

	public String getIisFinYr4() {
		return iisFinYr4;
	}

	public void setIisFinYr4(String iisFinYr4) {
		this.iisFinYr4 = iisFinYr4;
	}

	public String getIisDateOfDisb4() {
		return iisDateOfDisb4;
	}

	public void setIisDateOfDisb4(String iisDateOfDisb4) {
		this.iisDateOfDisb4 = iisDateOfDisb4;
	}

	public String getIisActAmtIP4() {
		return iisActAmtIP4;
	}

	public void setIisActAmtIP4(String iisActAmtIP4) {
		this.iisActAmtIP4 = iisActAmtIP4;
	}

	public String getDateOfPay4() {
		return dateOfPay4;
	}

	public void setDateOfPay4(String dateOfPay4) {
		this.dateOfPay4 = dateOfPay4;
	}

	public String getPropIntInfra4() {
		return propIntInfra4;
	}

	public void setPropIntInfra4(String propIntInfra4) {
		this.propIntInfra4 = propIntInfra4;
	}

	public String getPropIntInfraPA4() {
		return propIntInfraPA4;
	}

	public void setPropIntInfraPA4(String propIntInfraPA4) {
		this.propIntInfraPA4 = propIntInfraPA4;
	}

	public String getEligibleIIS4() {
		return eligibleIIS4;
	}

	public void setEligibleIIS4(String eligibleIIS4) {
		this.eligibleIIS4 = eligibleIIS4;
	}

	public String getIisFinYr5() {
		return iisFinYr5;
	}

	public void setIisFinYr5(String iisFinYr5) {
		this.iisFinYr5 = iisFinYr5;
	}

	public String getIisTtlLoanAmt1() {
		return iisTtlLoanAmt1;
	}

	public void setIisTtlLoanAmt1(String iisTtlLoanAmt1) {
		this.iisTtlLoanAmt1 = iisTtlLoanAmt1;
	}

	public String getIisTtlLoanAmt2() {
		return iisTtlLoanAmt2;
	}

	public void setIisTtlLoanAmt2(String iisTtlLoanAmt2) {
		this.iisTtlLoanAmt2 = iisTtlLoanAmt2;
	}

	public String getIisTtlLoanAmt3() {
		return iisTtlLoanAmt3;
	}

	public void setIisTtlLoanAmt3(String iisTtlLoanAmt3) {
		this.iisTtlLoanAmt3 = iisTtlLoanAmt3;
	}

	public String getIisTtlLoanAmt4() {
		return iisTtlLoanAmt4;
	}

	public void setIisTtlLoanAmt4(String iisTtlLoanAmt4) {
		this.iisTtlLoanAmt4 = iisTtlLoanAmt4;
	}

	public String getIisTtlLoanAmt5() {
		return iisTtlLoanAmt5;
	}

	public void setIisTtlLoanAmt5(String iisTtlLoanAmt5) {
		this.iisTtlLoanAmt5 = iisTtlLoanAmt5;
	}

	public String getIisDateOfDisb5() {
		return iisDateOfDisb5;
	}

	public void setIisDateOfDisb5(String iisDateOfDisb5) {
		this.iisDateOfDisb5 = iisDateOfDisb5;
	}

	public String getIisActAmtIP5() {
		return iisActAmtIP5;
	}

	public void setIisActAmtIP5(String iisActAmtIP5) {
		this.iisActAmtIP5 = iisActAmtIP5;
	}

	public String getDateOfPay5() {
		return dateOfPay5;
	}

	public void setDateOfPay5(String dateOfPay5) {
		this.dateOfPay5 = dateOfPay5;
	}

	public String getPropIntInfra5() {
		return propIntInfra5;
	}

	public void setPropIntInfra5(String propIntInfra5) {
		this.propIntInfra5 = propIntInfra5;
	}

	public String getPropIntInfraPA5() {
		return propIntInfraPA5;
	}

	public void setPropIntInfraPA5(String propIntInfraPA5) {
		this.propIntInfraPA5 = propIntInfraPA5;
	}

	public String getEligibleIIS5() {
		return eligibleIIS5;
	}

	public void setEligibleIIS5(String eligibleIIS5) {
		this.eligibleIIS5 = eligibleIIS5;
	}

	public String getEligAmtIisObserv() {
		return eligAmtIisObserv;
	}

	public void setEligAmtIisObserv(String eligAmtIisObserv) {
		this.eligAmtIisObserv = eligAmtIisObserv;
	}

}
