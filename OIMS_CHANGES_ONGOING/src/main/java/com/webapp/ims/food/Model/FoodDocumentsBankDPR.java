/*
 *     Copyright 2020 - IT Solution powered by National Informatics Centre Uttar Pradesh State Unit
 *
 */
package com.webapp.ims.food.Model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

/**
 * @author nic
 *
 */
@Entity
@Table(name = "documents")
@IdClass(Identifier.class)
public class FoodDocumentsBankDPR implements Serializable {

	private static final long serialVersionUID = 1L;
	private String unit_id;
	private String control_id;
	@Id
	private String id;

	@Column(name = "incorporationcertificateoffirm_data")
	private byte[] incorporationcertificateoffirmData;
	@Column(name = "incorporationcertificateoffirm")
	private String incorporationcertificateoffirm;

	@Column(name = "partnershipdeedbyelawsofthefirm_data")
	private byte[] partnershipdeedbyelawsofthefirmData;
	@Column(name = "partnershipdeedbyelawsofthefirm")
	private String partnershipdeedbyelawsofthefirm;

	@Column(name = "chiefpromoterdirectorsbiodata_data")
	private byte[] chiefpromoterdirectorsbiodataData;
	@Column(name = "chiefpromoterdirectorsbiodata")
	private String chiefpromoterdirectorsbiodata;

	@Column(name = "auditedstatementofaccounts_data")
	private byte[] auditedstatementofaccountsData;
	@Column(name = "auditedstatementofaccounts")
	private String auditedstatementofaccounts;

	@Column(name = "copiesofpaidbillvoucheragainstdpr_data")
	private byte[] copiesofpaidbillvoucheragainstdprData;
	@Column(name = "copiesofpaidbillvoucheragainstdpr")
	private String copiesofpaidbillvoucheragainstdpr;

	@Column(name = "bankloansanctionletter_data")
	private byte[] bankloansanctionletterData;
	@Column(name = "bankloansanctionletter")
	private String bankloansanctionletter;

	@Column(name = "loanreleasedocument_data")
	private byte[] loanreleasedocumentData;
	@Column(name = "loanreleasedocument")
	private String loanreleasedocument;

	@Column(name = "chartedengineercertificateforprojectimplementation_data")
	private byte[] chartedengineercertificateforprojectimplementationData;
	@Column(name = "chartedengineercertificateforprojectimplementation")
	private String chartedengineercertificateforprojectimplementation;

	@Column(name = "projectreportdetail_data")
	private byte[] projectreportdetailData;
	@Column(name = "projectreportdetail")
	private String projectreportdetail;

	@Column(name = "copyofdprdulysignedbypreparingagencyinstitution_data")
	private byte[] copyofdprdulysignedbypreparingagencyinstitutionData;
	@Column(name = "copyofdprdulysignedbypreparingagencyinstitution")
	private String copyofdprdulysignedbypreparingagencyinstitution;

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

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public byte[] getIncorporationcertificateoffirmData() {
		return incorporationcertificateoffirmData;
	}

	public void setIncorporationcertificateoffirmData(byte[] incorporationcertificateoffirmData) {
		this.incorporationcertificateoffirmData = incorporationcertificateoffirmData;
	}

	public String getIncorporationcertificateoffirm() {
		return incorporationcertificateoffirm;
	}

	public void setIncorporationcertificateoffirm(String incorporationcertificateoffirm) {
		this.incorporationcertificateoffirm = incorporationcertificateoffirm;
	}

	public byte[] getPartnershipdeedbyelawsofthefirmData() {
		return partnershipdeedbyelawsofthefirmData;
	}

	public void setPartnershipdeedbyelawsofthefirmData(byte[] partnershipdeedbyelawsofthefirmData) {
		this.partnershipdeedbyelawsofthefirmData = partnershipdeedbyelawsofthefirmData;
	}

	public String getPartnershipdeedbyelawsofthefirm() {
		return partnershipdeedbyelawsofthefirm;
	}

	public void setPartnershipdeedbyelawsofthefirm(String partnershipdeedbyelawsofthefirm) {
		this.partnershipdeedbyelawsofthefirm = partnershipdeedbyelawsofthefirm;
	}

	public byte[] getChiefpromoterdirectorsbiodataData() {
		return chiefpromoterdirectorsbiodataData;
	}

	public void setChiefpromoterdirectorsbiodataData(byte[] chiefpromoterdirectorsbiodataData) {
		this.chiefpromoterdirectorsbiodataData = chiefpromoterdirectorsbiodataData;
	}

	public String getChiefpromoterdirectorsbiodata() {
		return chiefpromoterdirectorsbiodata;
	}

	public void setChiefpromoterdirectorsbiodata(String chiefpromoterdirectorsbiodata) {
		this.chiefpromoterdirectorsbiodata = chiefpromoterdirectorsbiodata;
	}

	public byte[] getAuditedstatementofaccountsData() {
		return auditedstatementofaccountsData;
	}

	public void setAuditedstatementofaccountsData(byte[] auditedstatementofaccountsData) {
		this.auditedstatementofaccountsData = auditedstatementofaccountsData;
	}

	public String getAuditedstatementofaccounts() {
		return auditedstatementofaccounts;
	}

	public void setAuditedstatementofaccounts(String auditedstatementofaccounts) {
		this.auditedstatementofaccounts = auditedstatementofaccounts;
	}

	public byte[] getCopiesofpaidbillvoucheragainstdprData() {
		return copiesofpaidbillvoucheragainstdprData;
	}

	public void setCopiesofpaidbillvoucheragainstdprData(byte[] copiesofpaidbillvoucheragainstdprData) {
		this.copiesofpaidbillvoucheragainstdprData = copiesofpaidbillvoucheragainstdprData;
	}

	public String getCopiesofpaidbillvoucheragainstdpr() {
		return copiesofpaidbillvoucheragainstdpr;
	}

	public void setCopiesofpaidbillvoucheragainstdpr(String copiesofpaidbillvoucheragainstdpr) {
		this.copiesofpaidbillvoucheragainstdpr = copiesofpaidbillvoucheragainstdpr;
	}

	public byte[] getBankloansanctionletterData() {
		return bankloansanctionletterData;
	}

	public void setBankloansanctionletterData(byte[] bankloansanctionletterData) {
		this.bankloansanctionletterData = bankloansanctionletterData;
	}

	public String getBankloansanctionletter() {
		return bankloansanctionletter;
	}

	public void setBankloansanctionletter(String bankloansanctionletter) {
		this.bankloansanctionletter = bankloansanctionletter;
	}

	public byte[] getLoanreleasedocumentData() {
		return loanreleasedocumentData;
	}

	public void setLoanreleasedocumentData(byte[] loanreleasedocumentData) {
		this.loanreleasedocumentData = loanreleasedocumentData;
	}

	public String getLoanreleasedocument() {
		return loanreleasedocument;
	}

	public void setLoanreleasedocument(String loanreleasedocument) {
		this.loanreleasedocument = loanreleasedocument;
	}

	public byte[] getChartedengineercertificateforprojectimplementationData() {
		return chartedengineercertificateforprojectimplementationData;
	}

	public void setChartedengineercertificateforprojectimplementationData(
			byte[] chartedengineercertificateforprojectimplementationData) {
		this.chartedengineercertificateforprojectimplementationData = chartedengineercertificateforprojectimplementationData;
	}

	public String getChartedengineercertificateforprojectimplementation() {
		return chartedengineercertificateforprojectimplementation;
	}

	public void setChartedengineercertificateforprojectimplementation(
			String chartedengineercertificateforprojectimplementation) {
		this.chartedengineercertificateforprojectimplementation = chartedengineercertificateforprojectimplementation;
	}

	public byte[] getProjectreportdetailData() {
		return projectreportdetailData;
	}

	public void setProjectreportdetailData(byte[] projectreportdetailData) {
		this.projectreportdetailData = projectreportdetailData;
	}

	public String getProjectreportdetail() {
		return projectreportdetail;
	}

	public void setProjectreportdetail(String projectreportdetail) {
		this.projectreportdetail = projectreportdetail;
	}

	public byte[] getCopyofdprdulysignedbypreparingagencyinstitutionData() {
		return copyofdprdulysignedbypreparingagencyinstitutionData;
	}

	public void setCopyofdprdulysignedbypreparingagencyinstitutionData(
			byte[] copyofdprdulysignedbypreparingagencyinstitutionData) {
		this.copyofdprdulysignedbypreparingagencyinstitutionData = copyofdprdulysignedbypreparingagencyinstitutionData;
	}

	public String getCopyofdprdulysignedbypreparingagencyinstitution() {
		return copyofdprdulysignedbypreparingagencyinstitution;
	}

	public void setCopyofdprdulysignedbypreparingagencyinstitution(
			String copyofdprdulysignedbypreparingagencyinstitution) {
		this.copyofdprdulysignedbypreparingagencyinstitution = copyofdprdulysignedbypreparingagencyinstitution;
	}

}
