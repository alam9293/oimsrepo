/*
 *     Copyright 2020 - IT Solution powered by National Informatics Centre Uttar Pradesh State Unit
 *
 */
package com.webapp.ims.food.Model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.format.annotation.DateTimeFormat;

/**
 * @author nic
 *
 */
@Entity
@Table(name = "business_entity_details", schema = "public")
@IdClass(Identifier.class)
public class BusinessEntityDetailsFood implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "unit_id")
	private String unit_id;

	@Id
	@Column(name = "control_id")
	private String control_id;

	@Id
	@Column(name = "id")
	private Identifier id;

	@Column(name = "nameofpromoter")
	private String nameofpromoter;

	@Column(name = "nameofcompany")
	private String nameofcompany;

	@Column(name = "addressofcompany")
	private String addressofcompany;

	@Column(name = "mobileno")
	private String mobileno;

	@Column(name = "email")
	private String email;

	@Column(name = "district")
	private String district;

	@Column(name = "division")
	private String division;

	@Column(name = "pancardoffirmproprietor")
	private String pancardoffirmproprietor;

	@Column(name = "gstin")
	private String gstin;

	@Column(name = "typeoforganization")
	private String typeoforganization;

	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	@Column(name = "dateofincorporation")
	private Date dateofincorporation;

	@Column(name = "photographofpromoter_data")
	private byte[] photographofpromoterData;

	@Column(name = "photographofpromoter")
	private String photographofpromoter;

	@Column(name = "address")
	private String address;

	@Column(name = "companystate")
	private String companystate;

	@Column(name = "companydistrict")
	private String companydistrict;

	@Column(name = "panno")
	private String panno;

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getCompanystate() {
		return companystate;
	}

	public void setCompanystate(String companystate) {
		this.companystate = companystate;
	}

	public String getCompanydistrict() {
		return companydistrict;
	}

	public void setCompanydistrict(String companydistrict) {
		this.companydistrict = companydistrict;
	}

	public String getPanno() {
		return panno;
	}

	public void setPanno(String panno) {
		this.panno = panno;
	}

	private transient String base64imageFile = null;

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

	public Identifier getId() {
		return id;
	}

	public void setId(Identifier id) {
		this.id = id;
	}

	public String getNameofpromoter() {
		return nameofpromoter;
	}

	public void setNameofpromoter(String nameofpromoter) {
		this.nameofpromoter = nameofpromoter;
	}

	public String getNameofcompany() {
		return nameofcompany;
	}

	public void setNameofcompany(String nameofcompany) {
		this.nameofcompany = nameofcompany;
	}

	public String getAddressofcompany() {
		return addressofcompany;
	}

	public void setAddressofcompany(String addressofcompany) {
		this.addressofcompany = addressofcompany;
	}

	public String getMobileno() {
		return mobileno;
	}

	public void setMobileno(String mobileno) {
		this.mobileno = mobileno;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getDistrict() {
		return district;
	}

	public void setDistrict(String district) {
		this.district = district;
	}

	public String getDivision() {
		return division;
	}

	public void setDivision(String division) {
		this.division = division;
	}

	public String getPancardoffirmproprietor() {
		return pancardoffirmproprietor;
	}

	public void setPancardoffirmproprietor(String pancardoffirmproprietor) {
		this.pancardoffirmproprietor = pancardoffirmproprietor;
	}

	public String getGstin() {
		return gstin;
	}

	public void setGstin(String gstin) {
		this.gstin = gstin;
	}

	public String getTypeoforganization() {
		return typeoforganization;
	}

	public void setTypeoforganization(String typeoforganization) {
		this.typeoforganization = typeoforganization;
	}

	public Date getDateofincorporation() {
		return dateofincorporation;
	}

	public void setDateofincorporation(Date dateofincorporation) {
		this.dateofincorporation = dateofincorporation;
	}

	public byte[] getPhotographofpromoterData() {
		return photographofpromoterData;
	}

	public void setPhotographofpromoterData(byte[] photographofpromoterData) {
		this.photographofpromoterData = photographofpromoterData;
	}

	public String getPhotographofpromoter() {
		return photographofpromoter;
	}

	public void setPhotographofpromoter(String photographofpromoter) {
		this.photographofpromoter = photographofpromoter;
	}

	public String getBase64imageFile() {
		return base64imageFile;
	}

	public void setBase64imageFile(String base64imageFile) {
		this.base64imageFile = base64imageFile;
	}

}
