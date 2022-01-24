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
public class FoodDocumentsMDBP implements Serializable {

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

	@Column(name = "affiliationletterfromauthorizedsignatoryofapplicationoforgani_d")
	private byte[] affiliationletterfromauthorizedsignatoryofapplicationoforganiData;
	@Column(name = "affiliationletterfromauthorizedsignatoryofapplicationoforgani")
	private String affiliationletterfromauthorizedsignatoryofapplicationoforgani;

	@Column(name = "selfattestedpromotersbiodataoforganization_data")
	private byte[] selfattestedpromotersbiodataoforganizationData;
	@Column(name = "selfattestedpromotersbiodataoforganization")
	private String selfattestedpromotersbiodataoforganization;

	@Column(name = "forcompletionofexportresponsibilityshipmentairwaybillampexpor_data")
	private byte[] forcompletionofexportresponsibilityshipmentairwaybillampexporData;
	@Column(name = "forcompletionofexportresponsibilityshipmentairwaybillampexpor")
	private String forcompletionofexportresponsibilityshipmentairwaybillampexpor;

	@Column(name = "detailofquantityofagricultureampexportedproduce_data")
	private byte[] detailofquantityofagricultureampexportedproduceData;
	@Column(name = "detailofquantityofagricultureampexportedproduce")
	private String detailofquantityofagricultureampexportedproduce;

	@Column(name = "importerexportercodecertificatefromdgftoffice_data")
	private byte[] importerexportercodecertificatefromdgftofficeData;
	@Column(name = "importerexportercodecertificatefromdgftoffice")
	private String importerexportercodecertificatefromdgftoffice;

	@Column(name = "rcmcregistrationcertificatefromapeeda_data")
	private byte[] rcmcregistrationcertificatefromapeedaData;
	@Column(name = "rcmcregistrationcertificatefromapeeda")
	private String rcmcregistrationcertificatefromapeeda;

	@Column(name = "billsofshipmentairforexportedmaterialquantity_data")
	private byte[] billsofshipmentairforexportedmaterialquantityData;
	@Column(name = "billsofshipmentairforexportedmaterialquantity")
	private String billsofshipmentairforexportedmaterialquantity;

	@Column(name = "exportgeneralmanifestcertificatefromcustomdepartment_data")
	private byte[] exportgeneralmanifestcertificatefromcustomdepartmentData;
	@Column(name = "exportgeneralmanifestcertificatefromcustomdepartment")
	private String exportgeneralmanifestcertificatefromcustomdepartment;

	@Column(name = "detailsofrawmaterialnbsp_data")
	private byte[] detailsofrawmaterialnbspData;
	@Column(name = "detailsofrawmaterialnbsp")
	private String detailsofrawmaterialnbsp;

	@Column(name = "freightbillsissuingauthoritydetails_data")
	private byte[] freightbillsissuingauthoritydetailsData;
	@Column(name = "freightbillsissuingauthoritydetails")
	private String freightbillsissuingauthoritydetails;

	@Column(name = "buyerpurchaseorderfromimportingcountry_data")
	private byte[] buyerpurchaseorderfromimportingcountryData;
	@Column(name = "buyerpurchaseorderfromimportingcountry")
	private String buyerpurchaseorderfromimportingcountry;

	@Column(name = "phytosanitarycertificate_data")
	private byte[] phytosanitarycertificateData;
	@Column(name = "phytosanitarycertificate")
	private String phytosanitarycertificate;

	@Column(name = "exportinvoicecumpackinglistissuingauthoritydetails_data")
	private byte[] exportinvoicecumpackinglistissuingauthoritydetailsData;
	@Column(name = "exportinvoicecumpackinglistissuingauthoritydetails")
	private String exportinvoicecumpackinglistissuingauthoritydetails;

	@Column(name = "nameofbankrealizationcertificate_data")
	private byte[] nameofbankrealizationcertificateData;
	@Column(name = "nameofbankrealizationcertificate")
	private String nameofbankrealizationcertificate;

	@Column(name = "shippingbillsforexports_data")
	private byte[] shippingbillsforexportsData;
	@Column(name = "shippingbillsforexports")
	private String shippingbillsforexports;

	@Column(name = "declarationupload_data")
	private byte[] declarationuploadData;
	@Column(name = "declarationupload")
	private String declarationupload;

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

	public byte[] getAffiliationletterfromauthorizedsignatoryofapplicationoforganiData() {
		return affiliationletterfromauthorizedsignatoryofapplicationoforganiData;
	}

	public void setAffiliationletterfromauthorizedsignatoryofapplicationoforganiData(
			byte[] affiliationletterfromauthorizedsignatoryofapplicationoforganiData) {
		this.affiliationletterfromauthorizedsignatoryofapplicationoforganiData = affiliationletterfromauthorizedsignatoryofapplicationoforganiData;
	}

	public String getAffiliationletterfromauthorizedsignatoryofapplicationoforgani() {
		return affiliationletterfromauthorizedsignatoryofapplicationoforgani;
	}

	public void setAffiliationletterfromauthorizedsignatoryofapplicationoforgani(
			String affiliationletterfromauthorizedsignatoryofapplicationoforgani) {
		this.affiliationletterfromauthorizedsignatoryofapplicationoforgani = affiliationletterfromauthorizedsignatoryofapplicationoforgani;
	}

	public byte[] getSelfattestedpromotersbiodataoforganizationData() {
		return selfattestedpromotersbiodataoforganizationData;
	}

	public void setSelfattestedpromotersbiodataoforganizationData(
			byte[] selfattestedpromotersbiodataoforganizationData) {
		this.selfattestedpromotersbiodataoforganizationData = selfattestedpromotersbiodataoforganizationData;
	}

	public String getSelfattestedpromotersbiodataoforganization() {
		return selfattestedpromotersbiodataoforganization;
	}

	public void setSelfattestedpromotersbiodataoforganization(String selfattestedpromotersbiodataoforganization) {
		this.selfattestedpromotersbiodataoforganization = selfattestedpromotersbiodataoforganization;
	}

	public byte[] getForcompletionofexportresponsibilityshipmentairwaybillampexporData() {
		return forcompletionofexportresponsibilityshipmentairwaybillampexporData;
	}

	public void setForcompletionofexportresponsibilityshipmentairwaybillampexporData(
			byte[] forcompletionofexportresponsibilityshipmentairwaybillampexporData) {
		this.forcompletionofexportresponsibilityshipmentairwaybillampexporData = forcompletionofexportresponsibilityshipmentairwaybillampexporData;
	}

	public String getForcompletionofexportresponsibilityshipmentairwaybillampexpor() {
		return forcompletionofexportresponsibilityshipmentairwaybillampexpor;
	}

	public void setForcompletionofexportresponsibilityshipmentairwaybillampexpor(
			String forcompletionofexportresponsibilityshipmentairwaybillampexpor) {
		this.forcompletionofexportresponsibilityshipmentairwaybillampexpor = forcompletionofexportresponsibilityshipmentairwaybillampexpor;
	}

	public byte[] getDetailofquantityofagricultureampexportedproduceData() {
		return detailofquantityofagricultureampexportedproduceData;
	}

	public void setDetailofquantityofagricultureampexportedproduceData(
			byte[] detailofquantityofagricultureampexportedproduceData) {
		this.detailofquantityofagricultureampexportedproduceData = detailofquantityofagricultureampexportedproduceData;
	}

	public String getDetailofquantityofagricultureampexportedproduce() {
		return detailofquantityofagricultureampexportedproduce;
	}

	public void setDetailofquantityofagricultureampexportedproduce(
			String detailofquantityofagricultureampexportedproduce) {
		this.detailofquantityofagricultureampexportedproduce = detailofquantityofagricultureampexportedproduce;
	}

	public byte[] getImporterexportercodecertificatefromdgftofficeData() {
		return importerexportercodecertificatefromdgftofficeData;
	}

	public void setImporterexportercodecertificatefromdgftofficeData(
			byte[] importerexportercodecertificatefromdgftofficeData) {
		this.importerexportercodecertificatefromdgftofficeData = importerexportercodecertificatefromdgftofficeData;
	}

	public String getImporterexportercodecertificatefromdgftoffice() {
		return importerexportercodecertificatefromdgftoffice;
	}

	public void setImporterexportercodecertificatefromdgftoffice(String importerexportercodecertificatefromdgftoffice) {
		this.importerexportercodecertificatefromdgftoffice = importerexportercodecertificatefromdgftoffice;
	}

	public byte[] getRcmcregistrationcertificatefromapeedaData() {
		return rcmcregistrationcertificatefromapeedaData;
	}

	public void setRcmcregistrationcertificatefromapeedaData(byte[] rcmcregistrationcertificatefromapeedaData) {
		this.rcmcregistrationcertificatefromapeedaData = rcmcregistrationcertificatefromapeedaData;
	}

	public String getRcmcregistrationcertificatefromapeeda() {
		return rcmcregistrationcertificatefromapeeda;
	}

	public void setRcmcregistrationcertificatefromapeeda(String rcmcregistrationcertificatefromapeeda) {
		this.rcmcregistrationcertificatefromapeeda = rcmcregistrationcertificatefromapeeda;
	}

	public byte[] getBillsofshipmentairforexportedmaterialquantityData() {
		return billsofshipmentairforexportedmaterialquantityData;
	}

	public void setBillsofshipmentairforexportedmaterialquantityData(
			byte[] billsofshipmentairforexportedmaterialquantityData) {
		this.billsofshipmentairforexportedmaterialquantityData = billsofshipmentairforexportedmaterialquantityData;
	}

	public String getBillsofshipmentairforexportedmaterialquantity() {
		return billsofshipmentairforexportedmaterialquantity;
	}

	public void setBillsofshipmentairforexportedmaterialquantity(String billsofshipmentairforexportedmaterialquantity) {
		this.billsofshipmentairforexportedmaterialquantity = billsofshipmentairforexportedmaterialquantity;
	}

	public byte[] getExportgeneralmanifestcertificatefromcustomdepartmentData() {
		return exportgeneralmanifestcertificatefromcustomdepartmentData;
	}

	public void setExportgeneralmanifestcertificatefromcustomdepartmentData(
			byte[] exportgeneralmanifestcertificatefromcustomdepartmentData) {
		this.exportgeneralmanifestcertificatefromcustomdepartmentData = exportgeneralmanifestcertificatefromcustomdepartmentData;
	}

	public String getExportgeneralmanifestcertificatefromcustomdepartment() {
		return exportgeneralmanifestcertificatefromcustomdepartment;
	}

	public void setExportgeneralmanifestcertificatefromcustomdepartment(
			String exportgeneralmanifestcertificatefromcustomdepartment) {
		this.exportgeneralmanifestcertificatefromcustomdepartment = exportgeneralmanifestcertificatefromcustomdepartment;
	}

	public byte[] getDetailsofrawmaterialnbspData() {
		return detailsofrawmaterialnbspData;
	}

	public void setDetailsofrawmaterialnbspData(byte[] detailsofrawmaterialnbspData) {
		this.detailsofrawmaterialnbspData = detailsofrawmaterialnbspData;
	}

	public String getDetailsofrawmaterialnbsp() {
		return detailsofrawmaterialnbsp;
	}

	public void setDetailsofrawmaterialnbsp(String detailsofrawmaterialnbsp) {
		this.detailsofrawmaterialnbsp = detailsofrawmaterialnbsp;
	}

	public byte[] getFreightbillsissuingauthoritydetailsData() {
		return freightbillsissuingauthoritydetailsData;
	}

	public void setFreightbillsissuingauthoritydetailsData(byte[] freightbillsissuingauthoritydetailsData) {
		this.freightbillsissuingauthoritydetailsData = freightbillsissuingauthoritydetailsData;
	}

	public String getFreightbillsissuingauthoritydetails() {
		return freightbillsissuingauthoritydetails;
	}

	public void setFreightbillsissuingauthoritydetails(String freightbillsissuingauthoritydetails) {
		this.freightbillsissuingauthoritydetails = freightbillsissuingauthoritydetails;
	}

	public byte[] getBuyerpurchaseorderfromimportingcountryData() {
		return buyerpurchaseorderfromimportingcountryData;
	}

	public void setBuyerpurchaseorderfromimportingcountryData(byte[] buyerpurchaseorderfromimportingcountryData) {
		this.buyerpurchaseorderfromimportingcountryData = buyerpurchaseorderfromimportingcountryData;
	}

	public String getBuyerpurchaseorderfromimportingcountry() {
		return buyerpurchaseorderfromimportingcountry;
	}

	public void setBuyerpurchaseorderfromimportingcountry(String buyerpurchaseorderfromimportingcountry) {
		this.buyerpurchaseorderfromimportingcountry = buyerpurchaseorderfromimportingcountry;
	}

	public byte[] getPhytosanitarycertificateData() {
		return phytosanitarycertificateData;
	}

	public void setPhytosanitarycertificateData(byte[] phytosanitarycertificateData) {
		this.phytosanitarycertificateData = phytosanitarycertificateData;
	}

	public String getPhytosanitarycertificate() {
		return phytosanitarycertificate;
	}

	public void setPhytosanitarycertificate(String phytosanitarycertificate) {
		this.phytosanitarycertificate = phytosanitarycertificate;
	}

	public byte[] getExportinvoicecumpackinglistissuingauthoritydetailsData() {
		return exportinvoicecumpackinglistissuingauthoritydetailsData;
	}

	public void setExportinvoicecumpackinglistissuingauthoritydetailsData(
			byte[] exportinvoicecumpackinglistissuingauthoritydetailsData) {
		this.exportinvoicecumpackinglistissuingauthoritydetailsData = exportinvoicecumpackinglistissuingauthoritydetailsData;
	}

	public String getExportinvoicecumpackinglistissuingauthoritydetails() {
		return exportinvoicecumpackinglistissuingauthoritydetails;
	}

	public void setExportinvoicecumpackinglistissuingauthoritydetails(
			String exportinvoicecumpackinglistissuingauthoritydetails) {
		this.exportinvoicecumpackinglistissuingauthoritydetails = exportinvoicecumpackinglistissuingauthoritydetails;
	}

	public byte[] getNameofbankrealizationcertificateData() {
		return nameofbankrealizationcertificateData;
	}

	public void setNameofbankrealizationcertificateData(byte[] nameofbankrealizationcertificateData) {
		this.nameofbankrealizationcertificateData = nameofbankrealizationcertificateData;
	}

	public String getNameofbankrealizationcertificate() {
		return nameofbankrealizationcertificate;
	}

	public void setNameofbankrealizationcertificate(String nameofbankrealizationcertificate) {
		this.nameofbankrealizationcertificate = nameofbankrealizationcertificate;
	}

	public byte[] getShippingbillsforexportsData() {
		return shippingbillsforexportsData;
	}

	public void setShippingbillsforexportsData(byte[] shippingbillsforexportsData) {
		this.shippingbillsforexportsData = shippingbillsforexportsData;
	}

	public String getShippingbillsforexports() {
		return shippingbillsforexports;
	}

	public void setShippingbillsforexports(String shippingbillsforexports) {
		this.shippingbillsforexports = shippingbillsforexports;
	}

	public byte[] getDeclarationuploadData() {
		return declarationuploadData;
	}

	public void setDeclarationuploadData(byte[] declarationuploadData) {
		this.declarationuploadData = declarationuploadData;
	}

	public String getDeclarationupload() {
		return declarationupload;
	}

	public void setDeclarationupload(String declarationupload) {
		this.declarationupload = declarationupload;
	}

}
