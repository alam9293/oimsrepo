package com.webapp.ims.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "INV_Others", schema = "loc")
public class INVOthers implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	@Column(name = "ID")
	private String mofId;

	public String getMofId() {
		return mofId;
	}

	public void setMofId(String mofId) {
		this.mofId = mofId;
	}

	public String getInvid() {
		return invid;
	}

	public void setInvid(String invid) {
		this.invid = invid;
	}

	public String getApcid() {
		return apcid;
	}

	public void setApcid(String apcid) {
		this.apcid = apcid;
	}

	@Column(name = "Phase_Number")
	private int phaseNumber;

	public int getPhaseNumber() {
		return phaseNumber;
	}

	public void setPhaseNumber(int phaseNumber) {
		this.phaseNumber = phaseNumber;
	}

	@Column(name = "Particulars")
	private String particulars;

	public String getParticulars() {
		return particulars;
	}

	public void setParticulars(String particulars) {
		this.particulars = particulars;
	}

	@Column(name = "INV_ID")
	private String invid;

	@Column(name = "APC_ID")
	private String apcid;

	@Column(name = "Proposed_Investment_In_Project")
	private Long proposedInvestmentInProject;

	public Long getProposedInvestmentInProject() {
		return proposedInvestmentInProject;
	}

	public void setProposedInvestmentInProject(Long proposedInvestmentInProject) {
		this.proposedInvestmentInProject = proposedInvestmentInProject;
	}
}
