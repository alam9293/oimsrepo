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
public class FoodDocument implements Serializable {

	@Id
	@Column(name = "unit_id")
	private String unit_id;

	@Id
	@Column(name = "control_id")
	private String control_id;

	@Id
	@Column(name = "id")
	private Identifier id;

	@Column(name = "incorporationcertificateoffirm_data")
	private byte[] incorporationcertificateoffirmData;
	@Column(name = "incorporationcertificateoffirm")
	private String incorporationcertificateoffirm;

	@Column(name = "partnershipdeedbyelawsofthefirm_data")
	private byte[] partnershipdeedbyelawsofthefirmData;
	@Column(name = "partnershipdeedbyelawsofthefirm")
	private String partnershipdeedbyelawsofthefirm;

	@Column(name = "registeredlanddeedrentagreement_data")
	private byte[] registeredlanddeedrentagreementData;
	@Column(name = "registeredlanddeedrentagreement")
	private String registeredlanddeedrentagreement;

	@Column(name = "khasrakhatauni_data")
	private byte[] khasrakhatauniData;
	@Column(name = "khasrakhatauni")
	private String khasrakhatauni;

	@Column(name = "detailedprojectreportasperannexurea16data")
	private byte[] detailedprojectreportasperannexurea16Data;
	@Column(name = "detailedprojectreportasperannexurea16")
	private String detailedprojectreportasperannexurea16;

	@Column(name = "termloansanctionletter_data")
	private byte[] termloansanctionletterData;
	@Column(name = "termloansanctionletter")
	private String termloansanctionletter;

	@Column(name = "bankappraisalreport_data")
	private byte[] bankappraisalreportData;
	@Column(name = "bankappraisalreport")
	private String bankappraisalreport;

	@Column(name = "buildingplanoffactoryapprovedbycompetentauthority_data")
	private byte[] buildingplanoffactoryapprovedbycompetentauthorityData;
	@Column(name = "buildingplanoffactoryapprovedbycompetentauthority")
	private String buildingplanoffactoryapprovedbycompetentauthority;

	@Column(name = "udyogaadharudyamregistration_data")
	private byte[] udyogaadharudyamregistrationData;
	@Column(name = "udyogaadharudyamregistration")
	private String udyogaadharudyamregistration;

	@Column(name = "nocforpollution_data")
	private byte[] nocforpollutionData;
	@Column(name = "nocforpollution")
	private String nocforpollution;

	@Column(name = "nocforfiresafety_data")
	private byte[] nocforfiresafetyData;
	@Column(name = "nocforfiresafety")
	private String nocforfiresafety;

	@Column(name = "powerloadsanctionletterformuppcl_data")
	private byte[] powerloadsanctionletterformuppclData;
	@Column(name = "powerloadsanctionletterformuppcl")
	private String powerloadsanctionletterformuppcl;

	@Column(name = "fssailicensecertificate_data")
	private byte[] fssailicensecertificateData;
	@Column(name = "fssailicensecertificate")
	private String fssailicensecertificate;

	@Column(name = "repaymentscheduleoftermloan_data")
	private byte[] repaymentscheduleoftermloanData;
	@Column(name = "repaymentscheduleoftermloan")
	private String repaymentscheduleoftermloan;

	@Column(name = "annexurea6data")
	private byte[] annexurea6data;
	@Column(name = "annexurea6")
	private String annexurea6;

	@Column(name = "annexurea7charteredengineercertificateforcivilwork_data")
	private byte[] annexurea7charteredengineercertificateforcivilworkData;
	@Column(name = "annexurea7charteredengineercertificateforcivilwork")
	private String annexurea7charteredengineercertificateforcivilwork;

	@Column(name = "annexurea8cecertificateforplantampmachinery_data")
	private byte[] annexurea8cecertificateforplantampmachineryData;
	@Column(name = "annexurea8cecertificateforplantampmachinery")
	private String annexurea8cecertificateforplantampmachinery;

	@Column(name = "annexurea18cacertificateforincreaseingrossblockvalue_data")
	private byte[] annexurea18cacertificateforincreaseingrossblockvalueData;
	@Column(name = "annexurea18cacertificateforincreaseingrossblockvalue")
	private String annexurea18cacertificateforincreaseingrossblockvalue;
	
	
	
	    @Column(name = "annexurea10data")
	    private byte[] annexurea10Data;
	    @Column(name = "annexurea10")
	    private String annexurea10;
	    @Column(name = "annexurea13data")
	    private byte[] annexurea13data;
	    @Column(name = "annexurea13")
	    private String annexurea13;
	    @Column(name = "annexurea14data")
	    private byte[] annexurea14data;
	    @Column(name = "annexurea14")
	    private String annexurea14;
	   
	

	public String getAnnexurea10() {
			return annexurea10;
		}

		public void setAnnexurea10(String annexurea10) {
			this.annexurea10 = annexurea10;
		}

		public String getAnnexurea13() {
			return annexurea13;
		}

		public void setAnnexurea13(String annexurea13) {
			this.annexurea13 = annexurea13;
		}

		public String getAnnexurea14() {
			return annexurea14;
		}

		public void setAnnexurea14(String annexurea14) {
			this.annexurea14 = annexurea14;
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
		return this.id;
	}

	public void setId(final Identifier id) {
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

	public byte[] getRegisteredlanddeedrentagreementData() {
		return registeredlanddeedrentagreementData;
	}

	public void setRegisteredlanddeedrentagreementData(byte[] registeredlanddeedrentagreementData) {
		this.registeredlanddeedrentagreementData = registeredlanddeedrentagreementData;
	}

	public String getRegisteredlanddeedrentagreement() {
		return registeredlanddeedrentagreement;
	}

	public void setRegisteredlanddeedrentagreement(String registeredlanddeedrentagreement) {
		this.registeredlanddeedrentagreement = registeredlanddeedrentagreement;
	}

	public byte[] getKhasrakhatauniData() {
		return khasrakhatauniData;
	}

	public void setKhasrakhatauniData(byte[] khasrakhatauniData) {
		this.khasrakhatauniData = khasrakhatauniData;
	}

	public String getKhasrakhatauni() {
		return khasrakhatauni;
	}

	public void setKhasrakhatauni(String khasrakhatauni) {
		this.khasrakhatauni = khasrakhatauni;
	}

	public byte[] getDetailedprojectreportasperannexurea16Data() {
		return detailedprojectreportasperannexurea16Data;
	}

	public void setDetailedprojectreportasperannexurea16Data(byte[] detailedprojectreportasperannexurea16Data) {
		this.detailedprojectreportasperannexurea16Data = detailedprojectreportasperannexurea16Data;
	}

	public String getDetailedprojectreportasperannexurea16() {
		return detailedprojectreportasperannexurea16;
	}

	public void setDetailedprojectreportasperannexurea16(String detailedprojectreportasperannexurea16) {
		this.detailedprojectreportasperannexurea16 = detailedprojectreportasperannexurea16;
	}

	public byte[] getTermloansanctionletterData() {
		return termloansanctionletterData;
	}

	public void setTermloansanctionletterData(byte[] termloansanctionletterData) {
		this.termloansanctionletterData = termloansanctionletterData;
	}

	public String getTermloansanctionletter() {
		return termloansanctionletter;
	}

	public void setTermloansanctionletter(String termloansanctionletter) {
		this.termloansanctionletter = termloansanctionletter;
	}

	public byte[] getBankappraisalreportData() {
		return bankappraisalreportData;
	}

	public void setBankappraisalreportData(byte[] bankappraisalreportData) {
		this.bankappraisalreportData = bankappraisalreportData;
	}

	public String getBankappraisalreport() {
		return bankappraisalreport;
	}

	public void setBankappraisalreport(String bankappraisalreport) {
		this.bankappraisalreport = bankappraisalreport;
	}

	public byte[] getBuildingplanoffactoryapprovedbycompetentauthorityData() {
		return buildingplanoffactoryapprovedbycompetentauthorityData;
	}

	public void setBuildingplanoffactoryapprovedbycompetentauthorityData(
			byte[] buildingplanoffactoryapprovedbycompetentauthorityData) {
		this.buildingplanoffactoryapprovedbycompetentauthorityData = buildingplanoffactoryapprovedbycompetentauthorityData;
	}

	public String getBuildingplanoffactoryapprovedbycompetentauthority() {
		return buildingplanoffactoryapprovedbycompetentauthority;
	}

	public void setBuildingplanoffactoryapprovedbycompetentauthority(
			String buildingplanoffactoryapprovedbycompetentauthority) {
		this.buildingplanoffactoryapprovedbycompetentauthority = buildingplanoffactoryapprovedbycompetentauthority;
	}

	public byte[] getUdyogaadharudyamregistrationData() {
		return udyogaadharudyamregistrationData;
	}

	public void setUdyogaadharudyamregistrationData(byte[] udyogaadharudyamregistrationData) {
		this.udyogaadharudyamregistrationData = udyogaadharudyamregistrationData;
	}

	public String getUdyogaadharudyamregistration() {
		return udyogaadharudyamregistration;
	}

	public void setUdyogaadharudyamregistration(String udyogaadharudyamregistration) {
		this.udyogaadharudyamregistration = udyogaadharudyamregistration;
	}

	public byte[] getNocforpollutionData() {
		return nocforpollutionData;
	}

	public void setNocforpollutionData(byte[] nocforpollutionData) {
		this.nocforpollutionData = nocforpollutionData;
	}

	public String getNocforpollution() {
		return nocforpollution;
	}

	public void setNocforpollution(String nocforpollution) {
		this.nocforpollution = nocforpollution;
	}

	public byte[] getNocforfiresafetyData() {
		return nocforfiresafetyData;
	}

	public void setNocforfiresafetyData(byte[] nocforfiresafetyData) {
		this.nocforfiresafetyData = nocforfiresafetyData;
	}

	public String getNocforfiresafety() {
		return nocforfiresafety;
	}

	public void setNocforfiresafety(String nocforfiresafety) {
		this.nocforfiresafety = nocforfiresafety;
	}

	public byte[] getPowerloadsanctionletterformuppclData() {
		return powerloadsanctionletterformuppclData;
	}

	public void setPowerloadsanctionletterformuppclData(byte[] powerloadsanctionletterformuppclData) {
		this.powerloadsanctionletterformuppclData = powerloadsanctionletterformuppclData;
	}

	public String getPowerloadsanctionletterformuppcl() {
		return powerloadsanctionletterformuppcl;
	}

	public void setPowerloadsanctionletterformuppcl(String powerloadsanctionletterformuppcl) {
		this.powerloadsanctionletterformuppcl = powerloadsanctionletterformuppcl;
	}

	public byte[] getFssailicensecertificateData() {
		return fssailicensecertificateData;
	}

	public void setFssailicensecertificateData(byte[] fssailicensecertificateData) {
		this.fssailicensecertificateData = fssailicensecertificateData;
	}

	public String getFssailicensecertificate() {
		return fssailicensecertificate;
	}

	public void setFssailicensecertificate(String fssailicensecertificate) {
		this.fssailicensecertificate = fssailicensecertificate;
	}

	public byte[] getRepaymentscheduleoftermloanData() {
		return repaymentscheduleoftermloanData;
	}

	public void setRepaymentscheduleoftermloanData(byte[] repaymentscheduleoftermloanData) {
		this.repaymentscheduleoftermloanData = repaymentscheduleoftermloanData;
	}

	public String getRepaymentscheduleoftermloan() {
		return repaymentscheduleoftermloan;
	}

	public void setRepaymentscheduleoftermloan(String repaymentscheduleoftermloan) {
		this.repaymentscheduleoftermloan = repaymentscheduleoftermloan;
	}

	

	public byte[] getAnnexurea6data() {
		return annexurea6data;
	}

	public void setAnnexurea6data(byte[] annexurea6data) {
		this.annexurea6data = annexurea6data;
	}

	public byte[] getAnnexurea10Data() {
		return annexurea10Data;
	}

	public void setAnnexurea10Data(byte[] annexurea10Data) {
		this.annexurea10Data = annexurea10Data;
	}

	public byte[] getAnnexurea13data() {
		return annexurea13data;
	}

	public void setAnnexurea13data(byte[] annexurea13data) {
		this.annexurea13data = annexurea13data;
	}

	public byte[] getAnnexurea14data() {
		return annexurea14data;
	}

	public void setAnnexurea14data(byte[] annexurea14data) {
		this.annexurea14data = annexurea14data;
	}

	public String getAnnexurea6() {
		return annexurea6;
	}

	public void setAnnexurea6(String annexurea6) {
		this.annexurea6 = annexurea6;
	}

	public byte[] getAnnexurea7charteredengineercertificateforcivilworkData() {
		return annexurea7charteredengineercertificateforcivilworkData;
	}

	public void setAnnexurea7charteredengineercertificateforcivilworkData(
			byte[] annexurea7charteredengineercertificateforcivilworkData) {
		this.annexurea7charteredengineercertificateforcivilworkData = annexurea7charteredengineercertificateforcivilworkData;
	}

	public String getAnnexurea7charteredengineercertificateforcivilwork() {
		return annexurea7charteredengineercertificateforcivilwork;
	}

	public void setAnnexurea7charteredengineercertificateforcivilwork(
			String annexurea7charteredengineercertificateforcivilwork) {
		this.annexurea7charteredengineercertificateforcivilwork = annexurea7charteredengineercertificateforcivilwork;
	}

	public byte[] getAnnexurea8cecertificateforplantampmachineryData() {
		return annexurea8cecertificateforplantampmachineryData;
	}

	public void setAnnexurea8cecertificateforplantampmachineryData(
			byte[] annexurea8cecertificateforplantampmachineryData) {
		this.annexurea8cecertificateforplantampmachineryData = annexurea8cecertificateforplantampmachineryData;
	}

	public String getAnnexurea8cecertificateforplantampmachinery() {
		return annexurea8cecertificateforplantampmachinery;
	}

	public void setAnnexurea8cecertificateforplantampmachinery(String annexurea8cecertificateforplantampmachinery) {
		this.annexurea8cecertificateforplantampmachinery = annexurea8cecertificateforplantampmachinery;
	}

	public byte[] getAnnexurea18cacertificateforincreaseingrossblockvalueData() {
		return annexurea18cacertificateforincreaseingrossblockvalueData;
	}

	public void setAnnexurea18cacertificateforincreaseingrossblockvalueData(
			byte[] annexurea18cacertificateforincreaseingrossblockvalueData) {
		this.annexurea18cacertificateforincreaseingrossblockvalueData = annexurea18cacertificateforincreaseingrossblockvalueData;
	}

	public String getAnnexurea18cacertificateforincreaseingrossblockvalue() {
		return annexurea18cacertificateforincreaseingrossblockvalue;
	}

	public void setAnnexurea18cacertificateforincreaseingrossblockvalue(
			String annexurea18cacertificateforincreaseingrossblockvalue) {
		this.annexurea18cacertificateforincreaseingrossblockvalue = annexurea18cacertificateforincreaseingrossblockvalue;
	}

}
