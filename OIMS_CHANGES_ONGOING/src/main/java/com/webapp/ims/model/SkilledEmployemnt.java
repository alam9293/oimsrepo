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
 * @Table(name = "SKILLED_EMPLOYMENT") public class SkilledEmployemnt {
 * 
 * @Id
 * 
 * @GeneratedValue(strategy = GenerationType.AUTO)
 * 
 * @Column(name = "SE_Id") private int id;
 * 
 * @Column(name = "Financial_year") private String financialYear;
 * 
 * @Column(name = "Number_Of_Male_Employment") private String
 * numberofMaleEmployees;
 * 
 * 
 * 
 * @Column(name = "Number_Of_Female_Employment") private String
 * numberOfFemaleEmployees;
 * 
 * @Column(name = "Number_Of_General") private String numberOfGeneral;
 * 
 * @Column(name = "Number_Of_Sc") private String numberOfSc;
 * 
 * @Column(name = "Number_Of_St") private String numberOfSt;
 * 
 * 
 * @Column(name = "Number_Of_Obc") private String numberOfObc;
 * 
 * @Column(name = "Number_Of_Divyang") private String numberOfDivyang;
 * 
 * 
 * @ManyToOne(cascade = CascadeType.ALL)
 * 
 * @JoinColumn(name = "PE_Detail_Id") private ProposedEmploymentDetails
 * proposedEmploymentDetails;
 * 
 * private String unFinancialYear; private String unNumberofMaleEmployees;
 * private String unNumberOfFemaleEmployees; private String unNumberOfGeneral;
 * private String unNumberOfSc; private String unNumberOfSt; private String
 * unNumberOfObc; private String unNumberOfDivyang;
 * 
 * 
 * public SkilledEmployemnt() {
 * 
 * }
 * 
 * public int getId() { return id; }
 * 
 * public void setId(int id) { this.id = id; }
 * 
 * public String getFinancialYear() { return financialYear; }
 * 
 * public void setFinancialYear(String financialYear) { this.financialYear =
 * financialYear; }
 * 
 * public String getNumberofMaleEmployees() { return numberofMaleEmployees; }
 * 
 * public void setNumberofMaleEmployees(String numberofMaleEmployees) {
 * this.numberofMaleEmployees = numberofMaleEmployees; }
 * 
 * public ProposedEmploymentDetails getProposedEmploymentDetails() { return
 * proposedEmploymentDetails; }
 * 
 * public void setProposedEmploymentDetails(ProposedEmploymentDetails
 * proposedEmploymentDetails) { this.proposedEmploymentDetails =
 * proposedEmploymentDetails; }
 * 
 * public String getNumberOfFemaleEmployees() { return numberOfFemaleEmployees;
 * }
 * 
 * public void setNumberOfFemaleEmployees(String numberOfFemaleEmployees) {
 * this.numberOfFemaleEmployees = numberOfFemaleEmployees; }
 * 
 * public String getNumberOfGeneral() { return numberOfGeneral; }
 * 
 * public void setNumberOfGeneral(String numberOfGeneral) { this.numberOfGeneral
 * = numberOfGeneral; }
 * 
 * public String getNumberOfSc() { return numberOfSc; }
 * 
 * public void setNumberOfSc(String numberOfSc) { this.numberOfSc = numberOfSc;
 * }
 * 
 * public String getNumberOfSt() { return numberOfSt; }
 * 
 * public void setNumberOfSt(String numberOfSt) { this.numberOfSt = numberOfSt;
 * }
 * 
 * public String getNumberOfObc() { return numberOfObc; }
 * 
 * public void setNumberOfObc(String numberOfObc) { this.numberOfObc =
 * numberOfObc; }
 * 
 * public String getNumberOfDivyang() { return numberOfDivyang; }
 * 
 * public void setNumberOfDivyang(String numberOfDivyang) { this.numberOfDivyang
 * = numberOfDivyang; }
 * 
 * public String getUnFinancialYear() { return unFinancialYear; }
 * 
 * public void setUnFinancialYear(String unFinancialYear) { this.unFinancialYear
 * = unFinancialYear; } public String getUnNumberofMaleEmployees() { return
 * unNumberofMaleEmployees; }
 * 
 * public void setUnNumberofMaleEmployees(String unNumberofMaleEmployees) {
 * this.unNumberofMaleEmployees = unNumberofMaleEmployees; }
 * 
 * public String getUnNumberOfFemaleEmployees() { return
 * unNumberOfFemaleEmployees; }
 * 
 * public void setUnNumberOfFemaleEmployees(String unNumberOfFemaleEmployees) {
 * this.unNumberOfFemaleEmployees = unNumberOfFemaleEmployees; }
 * 
 * public String getUnNumberOfGeneral() { return unNumberOfGeneral; }
 * 
 * public void setUnNumberOfGeneral(String unNumberOfGeneral) {
 * this.unNumberOfGeneral = unNumberOfGeneral; }
 * 
 * public String getUnNumberOfSc() { return unNumberOfSc; }
 * 
 * public void setUnNumberOfSc(String unNumberOfSc) { this.unNumberOfSc =
 * unNumberOfSc; }
 * 
 * public String getUnNumberOfSt() { return unNumberOfSt; }
 * 
 * public void setUnNumberOfSt(String unNumberOfSt) { this.unNumberOfSt =
 * unNumberOfSt; }
 * 
 * public String getUnNumberOfObc() { return unNumberOfObc; }
 * 
 * public void setUnNumberOfObc(String unNumberOfObc) { this.unNumberOfObc =
 * unNumberOfObc; }
 * 
 * public String getUnNumberOfDivyang() { return unNumberOfDivyang; }
 * 
 * public void setUnNumberOfDivyang(String unNumberOfDivyang) {
 * this.unNumberOfDivyang = unNumberOfDivyang; }
 * 
 * }
 */