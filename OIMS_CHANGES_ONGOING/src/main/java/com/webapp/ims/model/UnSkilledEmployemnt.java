/*
 * package com.webapp.ims.model;
 * 
 * import javax.persistence.CascadeType; import javax.persistence.Column; import
 * javax.persistence.Entity; import javax.persistence.GeneratedValue; import
 * javax.persistence.GenerationType; import javax.persistence.Id; import
 * javax.persistence.JoinColumn; import javax.persistence.ManyToOne; import
 * javax.persistence.Table;
 * 
 * @Entity
 * 
 * @Table(name = "UNSKILLED_EMPLOYMENT") public class UnSkilledEmployemnt {
 * 
 * @Id
 * 
 * @GeneratedValue(strategy = GenerationType.AUTO)
 * 
 * @Column(name = "USE_Id") private int id;
 * 
 * @Column(name = "Un_Financial_year") private String unFinancialYear;
 * 
 * @Column(name = "UN_Number_Of_Male_Employment") private String
 * unNumberofMaleEmployees;
 * 
 * 
 * 
 * @Column(name = "UN_Number_Of_Female_Employment") private String
 * unNumberOfFemaleEmployees;
 * 
 * @Column(name = "UN_Number_Of_General") private String unNumberOfGeneral;
 * 
 * @Column(name = "UN_Number_Of_Sc") private String unNumberOfSc;
 * 
 * @Column(name = "UN_Number_Of_St") private String unNumberOfSt;
 * 
 * 
 * @Column(name = "UN_Number_Of_Obc") private String unNumberOfObc;
 * 
 * @Column(name = "UN_Number_Of_Divyang") private String unNumberOfDivyang;
 * 
 * 
 * @ManyToOne(cascade = CascadeType.ALL)
 * 
 * @JoinColumn(name = "PE_Detail_Id") private ProposedEmploymentDetails
 * unProposedEmploymentDetails; public UnSkilledEmployemnt() {
 * 
 * }
 * 
 * public int getId() { return id; }
 * 
 * 
 * public void setId(int id) { this.id = id; }
 * 
 * 
 * public String getUnFinancialYear() { return unFinancialYear; }
 * 
 * 
 * public void setUnFinancialYear(String unFinancialYear) { this.unFinancialYear
 * = unFinancialYear; }
 * 
 * 
 * public String getUnNumberofMaleEmployees() { return unNumberofMaleEmployees;
 * }
 * 
 * 
 * public void setUnNumberofMaleEmployees(String unNumberofMaleEmployees) {
 * this.unNumberofMaleEmployees = unNumberofMaleEmployees; }
 * 
 * 
 * public String getUnNumberOfFemaleEmployees() { return
 * unNumberOfFemaleEmployees; }
 * 
 * 
 * public void setUnNumberOfFemaleEmployees(String unNumberOfFemaleEmployees) {
 * this.unNumberOfFemaleEmployees = unNumberOfFemaleEmployees; }
 * 
 * 
 * public String getUnNumberOfGeneral() { return unNumberOfGeneral; }
 * 
 * 
 * public void setUnNumberOfGeneral(String unNumberOfGeneral) {
 * this.unNumberOfGeneral = unNumberOfGeneral; }
 * 
 * 
 * public String getUnNumberOfSc() { return unNumberOfSc; }
 * 
 * 
 * public void setUnNumberOfSc(String unNumberOfSc) { this.unNumberOfSc =
 * unNumberOfSc; }
 * 
 * 
 * public String getUnNumberOfSt() { return unNumberOfSt; }
 * 
 * 
 * public void setUnNumberOfSt(String unNumberOfSt) { this.unNumberOfSt =
 * unNumberOfSt; }
 * 
 * 
 * public String getUnNumberOfObc() { return unNumberOfObc; }
 * 
 * 
 * public void setUnNumberOfObc(String unNumberOfObc) { this.unNumberOfObc =
 * unNumberOfObc; }
 * 
 * 
 * public String getUnNumberOfDivyang() { return unNumberOfDivyang; }
 * 
 * 
 * public void setUnNumberOfDivyang(String unNumberOfDivyang) {
 * this.unNumberOfDivyang = unNumberOfDivyang; }
 * 
 * 
 * public ProposedEmploymentDetails getUnProposedEmploymentDetails() { return
 * unProposedEmploymentDetails; }
 * 
 * 
 * public void setUnProposedEmploymentDetails(ProposedEmploymentDetails
 * unProposedEmploymentDetails) { this.unProposedEmploymentDetails =
 * unProposedEmploymentDetails; }
 * 
 * }
 */