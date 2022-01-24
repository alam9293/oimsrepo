package com.webapp.ims.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "Dept_Prop_Emp_Skilled_Unskilled_Details", schema = "loc")

public class DeptSkilledUnSkilledEmployemnt {

	@Id
	@Column(name = "PE_ID")
	private String id;

	/*
	 * @Column(name = "PE_Finc_Yr") private String financialYear;
	 */

	@Column(name = "PE_M_Emp")
	private Long numberofMaleEmployees;

	@Column(name = "PE_F_Emp")
	private Long numberOfFemaleEmployees;

	@Column(name = "PE_Emp_Type")
	private String skilledUnskilled;

	@Column(name = "PE_General")
	private Long numberOfGeneral;

	@Column(name = "PE_BPL")
	private Long numberOfBpl;

	@Column(name = "PE_SC")
	private Long numberOfSc;

	@Column(name = "PE_ST")
	private Long numberOfSt;

	@Column(name = "PE_OBC")
	private Long numberOfObc;

	@Column(name = "PE_Divyang")
	private Long numberOfDivyang;

	@Column(name = "Created_By")
	private String createdBy;

	@Column(name = "Modified_By")
	private String modifiedBy;

	@Column(name = "Status")
	private String status;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "PE_Detail_ID")
	private ProposedEmploymentDetails proposedEmploymentDetails;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	/*
	 * public String getFinancialYear() { return financialYear; }
	 * 
	 * public void setFinancialYear(String financialYear) { this.financialYear =
	 * financialYear; }
	 */

	public Long getNumberofMaleEmployees() {
		return numberofMaleEmployees;
	}

	public Long getNumberOfDivyang() {
		return numberOfDivyang;
	}

	public void setNumberOfDivyang(Long numberOfDivyang) {
		this.numberOfDivyang = numberOfDivyang;
	}

	public void setNumberofMaleEmployees(Long numberofMaleEmployees) {
		this.numberofMaleEmployees = numberofMaleEmployees;
	}

	public Long getNumberOfFemaleEmployees() {
		return numberOfFemaleEmployees;
	}

	public void setNumberOfFemaleEmployees(Long numberOfFemaleEmployees) {
		this.numberOfFemaleEmployees = numberOfFemaleEmployees;
	}

	public String getSkilledUnskilled() {
		return skilledUnskilled;
	}

	public void setSkilledUnskilled(String skilledUnskilled) {
		this.skilledUnskilled = skilledUnskilled;
	}

	public Long getNumberOfGeneral() {
		return numberOfGeneral;
	}

	public void setNumberOfGeneral(Long numberOfGeneral) {
		this.numberOfGeneral = numberOfGeneral;
	}

	public Long getNumberOfBpl() {
		return numberOfBpl;
	}

	public void setNumberOfBpl(Long numberOfBpl) {
		this.numberOfBpl = numberOfBpl;
	}

	public Long getNumberOfSc() {
		return numberOfSc;
	}

	public void setNumberOfSc(Long numberOfSc) {
		this.numberOfSc = numberOfSc;
	}

	public Long getNumberOfSt() {
		return numberOfSt;
	}

	public void setNumberOfSt(Long numberOfSt) {
		this.numberOfSt = numberOfSt;
	}

	public Long getNumberOfObc() {
		return numberOfObc;
	}

	public void setNumberOfObc(Long numberOfObc) {
		this.numberOfObc = numberOfObc;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public String getModifiedBy() {
		return modifiedBy;
	}

	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public ProposedEmploymentDetails getProposedEmploymentDetails() {
		return proposedEmploymentDetails;
	}

	public void setProposedEmploymentDetails(ProposedEmploymentDetails proposedEmploymentDetails) {
		this.proposedEmploymentDetails = proposedEmploymentDetails;
	}

}
