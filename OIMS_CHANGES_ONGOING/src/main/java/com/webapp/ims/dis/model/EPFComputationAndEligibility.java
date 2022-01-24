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
@Table(name = "EPF_Computation_Eligibility", schema = "loc")
public class EPFComputationAndEligibility implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "EPF_Compute_Id")
	private String epfComputeId;

	@Column(name = "Apc_Id")
	private String epfApcId;

	@Column(name = "EPF_Comput_Fin_Yr")
	private String epfComputFinYr;
	
	@Column(name = "Date_From")
	private String dateFrom;
	
	@Column(name = "Date_To")
	private String dateTo;
	

	@Column(name = "Employer_Contribution_EPF")
	private String employerContributionEPF;

	@Column(name = "Reim_Eligibility")
	private String reimEligibility;

	

	public String getEpfComputeId() {
		return epfComputeId;
	}

	public void setEpfComputeId(String epfComputeId) {
		this.epfComputeId = epfComputeId;
	}

	public String getEpfApcId() {
		return epfApcId;
	}

	public void setEpfApcId(String epfApcId) {
		this.epfApcId = epfApcId;
	}

	

	public String getDateFrom() {
		return dateFrom;
	}

	public void setDateFrom(String dateFrom) {
		this.dateFrom = dateFrom;
	}

	public String getDateTo() {
		return dateTo;
	}

	public void setDateTo(String dateTo) {
		this.dateTo = dateTo;
	}

	public String getEpfComputFinYr() {
		return epfComputFinYr;
	}

	public void setEpfComputFinYr(String epfComputFinYr) {
		this.epfComputFinYr = epfComputFinYr;
	}

	public String getEmployerContributionEPF() {
		return employerContributionEPF;
	}

	public void setEmployerContributionEPF(String employerContributionEPF) {
		this.employerContributionEPF = employerContributionEPF;
	}

	public String getReimEligibility() {
		return reimEligibility;
	}

	public void setReimEligibility(String reimEligibility) {
		this.reimEligibility = reimEligibility;
	}

	
	
}
