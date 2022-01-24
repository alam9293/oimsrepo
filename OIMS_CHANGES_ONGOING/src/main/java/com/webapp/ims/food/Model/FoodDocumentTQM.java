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
public class FoodDocumentTQM implements Serializable {

	@Id
	@Column(name = "unit_id")
	private String unit_id;

	@Id
	@Column(name = "control_id")
	private String control_id;

	@Id
	@Column(name = "id")
	private Identifier id;

	@Column(name = "detailedprojectreport_data")
    private byte[] detailedprojectreportData;
    @Column(name = "detailedprojectreport")
    private String detailedprojectreport;
    
    @Column(name = "incometaxreturnsoftheproprietororganizationforpreviousthreeye_data")
    private byte[] incometaxreturnsoftheproprietororganizationforpreviousthreeyeData;
    @Column(name = "incometaxreturnsoftheproprietororganizationforpreviousthreeye")
    private String incometaxreturnsoftheproprietororganizationforpreviousthreeye;
    
    @Column(name = "udyogaadharudyamregistration_data")
    private byte[] udyogaadharudyamregistration_data;
    @Column(name = "udyogaadharudyamregistration")
    private String udyogaadharudyamregistration;
    
    @Column(name = "fssailicense_data")
    private byte[] fssailicenseData;
    @Column(name = "fssailicense")
    private String fssailicense;
    
    @Column(name = "biodataexperienceofconsultant_data")
    private byte[] biodataexperienceofconsultantData;
    @Column(name = "biodataexperienceofconsultant")
    private String biodataexperienceofconsultant;
    
    @Column(name = "quotationofconsultant_data")
    private byte[] quotationofconsultantData;
    @Column(name = "quotationofconsultant")
    private String quotationofconsultant;
    
    @Column(name = "consultantregistrationdetails_data")
    private byte[] consultantregistrationdetailsData;
    @Column(name = "consultantregistrationdetails")
    private String consultantregistrationdetails;
    
    @Column(name = "quotationfromcertificationbodyalongwiththedetailsofthecertifi_data")
    private byte[] quotationfromcertificationbodyalongwiththedetailsofthecertifiData;
    @Column(name = "quotationfromcertificationbodyalongwiththedetailsofthecertifi")
    private String quotationfromcertificationbodyalongwiththedetailsofthecertifi;
    @Column(name = "divcertificationagencyshouldbenbspaccreditedbytheqciornationa_data")
    private byte[] divcertificationagencyshouldbenbspaccreditedbytheqciornationaData;
    @Column(name = "divcertificationagencyshouldbenbspaccreditedbytheqciornationa")
    private String divcertificationagencyshouldbenbspaccreditedbytheqciornationa;
    @Column(name = "detailsofplantampmachineryaspergapstudyreportcountersignedbyc_data")
    private byte[] detailsofplantampmachineryaspergapstudyreportcountersignedbycData;
    @Column(name = "detailsofplantampmachineryaspergapstudyreportcountersignedbyc")
    private String detailsofplantampmachineryaspergapstudyreportcountersignedbyc;
    @Column(name = "detailsofplantampmachinerynbspquotationsdulycertifiedbyapprov_d")
    private byte[] detailsofplantampmachinerynbspquotationsdulycertifiedbyapprovData;
    @Column(name = "detailsofplantampmachinerynbspquotationsdulycertifiedbyapprov")
    private String detailsofplantampmachinerynbspquotationsdulycertifiedbyapprov;
    @Column(name = "detailsoftechnicalcivilworksaspergapstudyreportapprovedbynbsp_data")
    private byte[] detailsoftechnicalcivilworksaspergapstudyreportapprovedbynbspData;
    @Column(name = "detailsoftechnicalcivilworksaspergapstudyreportapprovedbynbsp")
    private String detailsoftechnicalcivilworksaspergapstudyreportapprovedbynbsp;
    
    @Column(name = "gapstudyreportasper_data")
    private byte[] gapstudyreportasperData;
    @Column(name = "gapstudyreportasper")
    private String gapstudyreportasper;
    @Column(name = "affidavitasper_data")
    private byte[] affidavitasperData;
    @Column(name = "affidavitasper")
    private String affidavitasper;
    @Column(name = "implementationscheduleofthehaccpiso9000iso22000gmpghpsystemim_data")
    private byte[] implementationscheduleofthehaccpiso9000iso22000gmpghpsystemimData;
    @Column(name = "implementationscheduleofthehaccpiso9000iso22000gmpghpsystemim")
    private String implementationscheduleofthehaccpiso9000iso22000gmpghpsystemim;
    @Column(name = "theapplicantorganizationisrequiredtogiveanundertakingthatthet_data")
    private byte[] theapplicantorganizationisrequiredtogiveanundertakingthatthetData;
    @Column(name = "theapplicantorganizationisrequiredtogiveanundertakingthatthet")
    private String theapplicantorganizationisrequiredtogiveanundertakingthatthet;
    
    @Column(name = "processofmanufacture_data")
    private byte[] processofmanufactureData;
    @Column(name = "processofmanufacture")
    private String processofmanufacture;
    @Column(name = "listofexistingplantampmachineryandqualitycontrolfacilitieswit_data")
    private byte[] listofexistingplantampmachineryandqualitycontrolfacilitieswitData;
    @Column(name = "listofexistingplantampmachineryandqualitycontrolfacilitieswit")
    private String listofexistingplantampmachineryandqualitycontrolfacilitieswit;
    @Column(name = "patentcertificateissuedbycompetentauthority_data")
    private byte[] patentcertificateissuedbycompetentauthorityData;
    @Column(name = "patentcertificateissuedbycompetentauthority")
    private String patentcertificateissuedbycompetentauthority;
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
	public Identifier getId() {
		return id;
	}
	public void setId(Identifier id) {
		this.id = id;
	}
	public byte[] getDetailedprojectreportData() {
		return detailedprojectreportData;
	}
	public void setDetailedprojectreportData(byte[] detailedprojectreportData) {
		this.detailedprojectreportData = detailedprojectreportData;
	}
	public String getDetailedprojectreport() {
		return detailedprojectreport;
	}
	public void setDetailedprojectreport(String detailedprojectreport) {
		this.detailedprojectreport = detailedprojectreport;
	}
	public byte[] getIncometaxreturnsoftheproprietororganizationforpreviousthreeyeData() {
		return incometaxreturnsoftheproprietororganizationforpreviousthreeyeData;
	}
	public void setIncometaxreturnsoftheproprietororganizationforpreviousthreeyeData(
			byte[] incometaxreturnsoftheproprietororganizationforpreviousthreeyeData) {
		this.incometaxreturnsoftheproprietororganizationforpreviousthreeyeData = incometaxreturnsoftheproprietororganizationforpreviousthreeyeData;
	}
	public String getIncometaxreturnsoftheproprietororganizationforpreviousthreeye() {
		return incometaxreturnsoftheproprietororganizationforpreviousthreeye;
	}
	public void setIncometaxreturnsoftheproprietororganizationforpreviousthreeye(
			String incometaxreturnsoftheproprietororganizationforpreviousthreeye) {
		this.incometaxreturnsoftheproprietororganizationforpreviousthreeye = incometaxreturnsoftheproprietororganizationforpreviousthreeye;
	}
	
	
	
	public byte[] getUdyogaadharudyamregistration_data() {
		return udyogaadharudyamregistration_data;
	}
	public void setUdyogaadharudyamregistration_data(byte[] udyogaadharudyamregistration_data) {
		this.udyogaadharudyamregistration_data = udyogaadharudyamregistration_data;
	}
	public String getUdyogaadharudyamregistration() {
		return udyogaadharudyamregistration;
	}
	public void setUdyogaadharudyamregistration(String udyogaadharudyamregistration) {
		this.udyogaadharudyamregistration = udyogaadharudyamregistration;
	}
	public byte[] getFssailicenseData() {
		return fssailicenseData;
	}
	public void setFssailicenseData(byte[] fssailicenseData) {
		this.fssailicenseData = fssailicenseData;
	}
	public String getFssailicense() {
		return fssailicense;
	}
	public void setFssailicense(String fssailicense) {
		this.fssailicense = fssailicense;
	}
	public byte[] getBiodataexperienceofconsultantData() {
		return biodataexperienceofconsultantData;
	}
	public void setBiodataexperienceofconsultantData(byte[] biodataexperienceofconsultantData) {
		this.biodataexperienceofconsultantData = biodataexperienceofconsultantData;
	}
	public String getBiodataexperienceofconsultant() {
		return biodataexperienceofconsultant;
	}
	public void setBiodataexperienceofconsultant(String biodataexperienceofconsultant) {
		this.biodataexperienceofconsultant = biodataexperienceofconsultant;
	}
	public byte[] getQuotationofconsultantData() {
		return quotationofconsultantData;
	}
	public void setQuotationofconsultantData(byte[] quotationofconsultantData) {
		this.quotationofconsultantData = quotationofconsultantData;
	}
	public String getQuotationofconsultant() {
		return quotationofconsultant;
	}
	public void setQuotationofconsultant(String quotationofconsultant) {
		this.quotationofconsultant = quotationofconsultant;
	}
	public byte[] getConsultantregistrationdetailsData() {
		return consultantregistrationdetailsData;
	}
	public void setConsultantregistrationdetailsData(byte[] consultantregistrationdetailsData) {
		this.consultantregistrationdetailsData = consultantregistrationdetailsData;
	}
	public String getConsultantregistrationdetails() {
		return consultantregistrationdetails;
	}
	public void setConsultantregistrationdetails(String consultantregistrationdetails) {
		this.consultantregistrationdetails = consultantregistrationdetails;
	}
	public byte[] getQuotationfromcertificationbodyalongwiththedetailsofthecertifiData() {
		return quotationfromcertificationbodyalongwiththedetailsofthecertifiData;
	}
	public void setQuotationfromcertificationbodyalongwiththedetailsofthecertifiData(
			byte[] quotationfromcertificationbodyalongwiththedetailsofthecertifiData) {
		this.quotationfromcertificationbodyalongwiththedetailsofthecertifiData = quotationfromcertificationbodyalongwiththedetailsofthecertifiData;
	}
	public String getQuotationfromcertificationbodyalongwiththedetailsofthecertifi() {
		return quotationfromcertificationbodyalongwiththedetailsofthecertifi;
	}
	public void setQuotationfromcertificationbodyalongwiththedetailsofthecertifi(
			String quotationfromcertificationbodyalongwiththedetailsofthecertifi) {
		this.quotationfromcertificationbodyalongwiththedetailsofthecertifi = quotationfromcertificationbodyalongwiththedetailsofthecertifi;
	}
	public byte[] getDivcertificationagencyshouldbenbspaccreditedbytheqciornationaData() {
		return divcertificationagencyshouldbenbspaccreditedbytheqciornationaData;
	}
	public void setDivcertificationagencyshouldbenbspaccreditedbytheqciornationaData(
			byte[] divcertificationagencyshouldbenbspaccreditedbytheqciornationaData) {
		this.divcertificationagencyshouldbenbspaccreditedbytheqciornationaData = divcertificationagencyshouldbenbspaccreditedbytheqciornationaData;
	}
	public String getDivcertificationagencyshouldbenbspaccreditedbytheqciornationa() {
		return divcertificationagencyshouldbenbspaccreditedbytheqciornationa;
	}
	public void setDivcertificationagencyshouldbenbspaccreditedbytheqciornationa(
			String divcertificationagencyshouldbenbspaccreditedbytheqciornationa) {
		this.divcertificationagencyshouldbenbspaccreditedbytheqciornationa = divcertificationagencyshouldbenbspaccreditedbytheqciornationa;
	}
	public byte[] getDetailsofplantampmachineryaspergapstudyreportcountersignedbycData() {
		return detailsofplantampmachineryaspergapstudyreportcountersignedbycData;
	}
	public void setDetailsofplantampmachineryaspergapstudyreportcountersignedbycData(
			byte[] detailsofplantampmachineryaspergapstudyreportcountersignedbycData) {
		this.detailsofplantampmachineryaspergapstudyreportcountersignedbycData = detailsofplantampmachineryaspergapstudyreportcountersignedbycData;
	}
	public String getDetailsofplantampmachineryaspergapstudyreportcountersignedbyc() {
		return detailsofplantampmachineryaspergapstudyreportcountersignedbyc;
	}
	public void setDetailsofplantampmachineryaspergapstudyreportcountersignedbyc(
			String detailsofplantampmachineryaspergapstudyreportcountersignedbyc) {
		this.detailsofplantampmachineryaspergapstudyreportcountersignedbyc = detailsofplantampmachineryaspergapstudyreportcountersignedbyc;
	}
	public byte[] getDetailsofplantampmachinerynbspquotationsdulycertifiedbyapprovData() {
		return detailsofplantampmachinerynbspquotationsdulycertifiedbyapprovData;
	}
	public void setDetailsofplantampmachinerynbspquotationsdulycertifiedbyapprovData(
			byte[] detailsofplantampmachinerynbspquotationsdulycertifiedbyapprovData) {
		this.detailsofplantampmachinerynbspquotationsdulycertifiedbyapprovData = detailsofplantampmachinerynbspquotationsdulycertifiedbyapprovData;
	}
	public String getDetailsofplantampmachinerynbspquotationsdulycertifiedbyapprov() {
		return detailsofplantampmachinerynbspquotationsdulycertifiedbyapprov;
	}
	public void setDetailsofplantampmachinerynbspquotationsdulycertifiedbyapprov(
			String detailsofplantampmachinerynbspquotationsdulycertifiedbyapprov) {
		this.detailsofplantampmachinerynbspquotationsdulycertifiedbyapprov = detailsofplantampmachinerynbspquotationsdulycertifiedbyapprov;
	}
	public byte[] getDetailsoftechnicalcivilworksaspergapstudyreportapprovedbynbspData() {
		return detailsoftechnicalcivilworksaspergapstudyreportapprovedbynbspData;
	}
	public void setDetailsoftechnicalcivilworksaspergapstudyreportapprovedbynbspData(
			byte[] detailsoftechnicalcivilworksaspergapstudyreportapprovedbynbspData) {
		this.detailsoftechnicalcivilworksaspergapstudyreportapprovedbynbspData = detailsoftechnicalcivilworksaspergapstudyreportapprovedbynbspData;
	}
	public String getDetailsoftechnicalcivilworksaspergapstudyreportapprovedbynbsp() {
		return detailsoftechnicalcivilworksaspergapstudyreportapprovedbynbsp;
	}
	public void setDetailsoftechnicalcivilworksaspergapstudyreportapprovedbynbsp(
			String detailsoftechnicalcivilworksaspergapstudyreportapprovedbynbsp) {
		this.detailsoftechnicalcivilworksaspergapstudyreportapprovedbynbsp = detailsoftechnicalcivilworksaspergapstudyreportapprovedbynbsp;
	}
	public byte[] getGapstudyreportasperData() {
		return gapstudyreportasperData;
	}
	public void setGapstudyreportasperData(byte[] gapstudyreportasperData) {
		this.gapstudyreportasperData = gapstudyreportasperData;
	}
	public String getGapstudyreportasper() {
		return gapstudyreportasper;
	}
	public void setGapstudyreportasper(String gapstudyreportasper) {
		this.gapstudyreportasper = gapstudyreportasper;
	}
	public byte[] getAffidavitasperData() {
		return affidavitasperData;
	}
	public void setAffidavitasperData(byte[] affidavitasperData) {
		this.affidavitasperData = affidavitasperData;
	}
	public String getAffidavitasper() {
		return affidavitasper;
	}
	public void setAffidavitasper(String affidavitasper) {
		this.affidavitasper = affidavitasper;
	}
	public byte[] getImplementationscheduleofthehaccpiso9000iso22000gmpghpsystemimData() {
		return implementationscheduleofthehaccpiso9000iso22000gmpghpsystemimData;
	}
	public void setImplementationscheduleofthehaccpiso9000iso22000gmpghpsystemimData(
			byte[] implementationscheduleofthehaccpiso9000iso22000gmpghpsystemimData) {
		this.implementationscheduleofthehaccpiso9000iso22000gmpghpsystemimData = implementationscheduleofthehaccpiso9000iso22000gmpghpsystemimData;
	}
	public String getImplementationscheduleofthehaccpiso9000iso22000gmpghpsystemim() {
		return implementationscheduleofthehaccpiso9000iso22000gmpghpsystemim;
	}
	public void setImplementationscheduleofthehaccpiso9000iso22000gmpghpsystemim(
			String implementationscheduleofthehaccpiso9000iso22000gmpghpsystemim) {
		this.implementationscheduleofthehaccpiso9000iso22000gmpghpsystemim = implementationscheduleofthehaccpiso9000iso22000gmpghpsystemim;
	}
	public byte[] getTheapplicantorganizationisrequiredtogiveanundertakingthatthetData() {
		return theapplicantorganizationisrequiredtogiveanundertakingthatthetData;
	}
	public void setTheapplicantorganizationisrequiredtogiveanundertakingthatthetData(
			byte[] theapplicantorganizationisrequiredtogiveanundertakingthatthetData) {
		this.theapplicantorganizationisrequiredtogiveanundertakingthatthetData = theapplicantorganizationisrequiredtogiveanundertakingthatthetData;
	}
	public String getTheapplicantorganizationisrequiredtogiveanundertakingthatthet() {
		return theapplicantorganizationisrequiredtogiveanundertakingthatthet;
	}
	public void setTheapplicantorganizationisrequiredtogiveanundertakingthatthet(
			String theapplicantorganizationisrequiredtogiveanundertakingthatthet) {
		this.theapplicantorganizationisrequiredtogiveanundertakingthatthet = theapplicantorganizationisrequiredtogiveanundertakingthatthet;
	}
	public byte[] getProcessofmanufactureData() {
		return processofmanufactureData;
	}
	public void setProcessofmanufactureData(byte[] processofmanufactureData) {
		this.processofmanufactureData = processofmanufactureData;
	}
	public String getProcessofmanufacture() {
		return processofmanufacture;
	}
	public void setProcessofmanufacture(String processofmanufacture) {
		this.processofmanufacture = processofmanufacture;
	}
	public byte[] getListofexistingplantampmachineryandqualitycontrolfacilitieswitData() {
		return listofexistingplantampmachineryandqualitycontrolfacilitieswitData;
	}
	public void setListofexistingplantampmachineryandqualitycontrolfacilitieswitData(
			byte[] listofexistingplantampmachineryandqualitycontrolfacilitieswitData) {
		this.listofexistingplantampmachineryandqualitycontrolfacilitieswitData = listofexistingplantampmachineryandqualitycontrolfacilitieswitData;
	}
	public String getListofexistingplantampmachineryandqualitycontrolfacilitieswit() {
		return listofexistingplantampmachineryandqualitycontrolfacilitieswit;
	}
	public void setListofexistingplantampmachineryandqualitycontrolfacilitieswit(
			String listofexistingplantampmachineryandqualitycontrolfacilitieswit) {
		this.listofexistingplantampmachineryandqualitycontrolfacilitieswit = listofexistingplantampmachineryandqualitycontrolfacilitieswit;
	}
	public byte[] getPatentcertificateissuedbycompetentauthorityData() {
		return patentcertificateissuedbycompetentauthorityData;
	}
	public void setPatentcertificateissuedbycompetentauthorityData(byte[] patentcertificateissuedbycompetentauthorityData) {
		this.patentcertificateissuedbycompetentauthorityData = patentcertificateissuedbycompetentauthorityData;
	}
	public String getPatentcertificateissuedbycompetentauthority() {
		return patentcertificateissuedbycompetentauthority;
	}
	public void setPatentcertificateissuedbycompetentauthority(String patentcertificateissuedbycompetentauthority) {
		this.patentcertificateissuedbycompetentauthority = patentcertificateissuedbycompetentauthority;
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
