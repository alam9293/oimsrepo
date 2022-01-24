package com.webapp.ims.food.Model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

@Entity
@Table(name = "business_entity_details")
@IdClass(Identifier.class)
public class BEDReeferVehicles implements Serializable {
	
	 @Id
	    private String  unit_id;
	    @Id
	    private String control_id;
	    @Id
	    private Identifier id;
	    @Column(name = "nameofpromoter")
	    private String nameofpromoter;
	    @Column(name = "nameoffirm")
	    private String nameoffirm;
	    @Column(name = "addressoffirm")
	    private String addressoffirm;
	    @Column(name = "mobileno")
	    private String mobileno;
	    @Column(name = "email")
	    private String email;
	    @Column(name = "companystate")
	    private String companystate;
	    @Column(name = "companydistrict")
	    private String companydistrict;
	    @Column(name = "pancardoffirmproprietor")
	    private String pancardoffirmproprietor;
	    @Column(name = "gstin")
	    private String gstin;
	    @Column(name = "typeoforganization")
	    private String typeoforganization;
	    @Column(name = "dateofincorporation")
	    private Date dateofincorporation;
		/*
		 * @Column(name = "photographofpromoterData") private byte[]
		 * photographofpromoterData;
		 * 
		 * @Column(name = "photographofpromoter") private String photographofpromoter;
		 */
	    
	    private transient String base64imageFile = null;
	    
	    
	    
	    
	
		/*
		 * public byte[] getPhotographofpromoterData() { return
		 * photographofpromoterData; } public void setPhotographofpromoterData(byte[]
		 * photographofpromoterData) { this.photographofpromoterData =
		 * photographofpromoterData; } public String getPhotographofpromoter() { return
		 * photographofpromoter; } public void setPhotographofpromoter(String
		 * photographofpromoter) { this.photographofpromoter = photographofpromoter; }
		 */
		public String getBase64imageFile() {
			return base64imageFile;
		}
		public void setBase64imageFile(String base64imageFile) {
			this.base64imageFile = base64imageFile;
		}
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
		public String getNameoffirm() {
			return nameoffirm;
		}
		public void setNameoffirm(String nameoffirm) {
			this.nameoffirm = nameoffirm;
		}
		public String getAddressoffirm() {
			return addressoffirm;
		}
		public void setAddressoffirm(String addressoffirm) {
			this.addressoffirm = addressoffirm;
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
		
		
	    

}
