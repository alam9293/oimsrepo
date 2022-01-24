/*
 *     Copyright 2020 - IT Solution powered by National Informatics Centre Uttar Pradesh State Unit
 *
 */
package com.webapp.ims.dis.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author nic
 *
 */
@Entity
@Table(name = "Dis_Evaluation_Bank_Details", schema = "loc")
public class DisEvaluationBankDetails implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	private String id;

	@Column(name = "apcId")
	private String apcId;

	@Column(name = "loanBankName")
	private String loanBankName;

	@Column(name = "loanBankDate")
	private String loanBankDate;

	@Column(name = "loanBankAmt")
	private String loanBankAmt;

	@Column(name = "loanBankROI")
	private String loanBankROI;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getApcId() {
		return apcId;
	}

	public void setApcId(String apcId) {
		this.apcId = apcId;
	}

	public String getLoanBankName() {
		return loanBankName;
	}

	public void setLoanBankName(String loanBankName) {
		this.loanBankName = loanBankName;
	}

	public String getLoanBankDate() {
		return loanBankDate;
	}

	public void setLoanBankDate(String loanBankDate) {
		this.loanBankDate = loanBankDate;
	}

	public String getLoanBankAmt() {
		return loanBankAmt;
	}

	public void setLoanBankAmt(String loanBankAmt) {
		this.loanBankAmt = loanBankAmt;
	}

	public String getLoanBankROI() {
		return loanBankROI;
	}

	public void setLoanBankROI(String loanBankROI) {
		this.loanBankROI = loanBankROI;
	}

}
