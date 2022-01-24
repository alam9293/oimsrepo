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
public class FoodDocumentPMKSY implements Serializable {

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

	@Column(name = "detailedprojectreportsubmittedtomofpi_data")
	private byte[] detailedprojectreportsubmittedtomofpi_data;
	@Column(name = "detailedprojectreportsubmittedtomofpi")
	private String detailedprojectreportsubmittedtomofpi;

	@Column(name = "termloansanctionletter_data")
	private byte[] termloansanctionletterData;
	@Column(name = "termloansanctionletter")
	private String termloansanctionletter;

	@Column(name = "bankappraisalreport_data")
	private byte[] bankappraisalreportData;
	@Column(name = "bankappraisalreport")
	private String bankappraisalreport;

	@Column(name = "iemudyogaadharudyamregister_data")
	private byte[] iemudyogaadharudyamregisterData;
	@Column(name = "iemudyogaadharudyamregister")
	private String iemudyogaadharudyamregister;

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

	@Column(name = "repaymentscheduleoftermloan_data")
	private byte[] repaymentscheduleoftermloanData;
	@Column(name = "repaymentscheduleoftermloan")
	private String repaymentscheduleoftermloan;

	@Column(name = "annexurea6data")
	private byte[] annexurea6Data;
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

	@Column(name = "sanctionletterissuedbymofpigovernmentofindia_data")
	private byte[] sanctionletterissuedbymofpigovernmentofindiaData;
	@Column(name = "sanctionletterissuedbymofpigovernmentofindia")
	private String sanctionletterissuedbymofpigovernmentofindia;

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

	

	public byte[] getDetailedprojectreportsubmittedtomofpi_data() {
		return detailedprojectreportsubmittedtomofpi_data;
	}

	public void setDetailedprojectreportsubmittedtomofpi_data(byte[] detailedprojectreportsubmittedtomofpi_data) {
		this.detailedprojectreportsubmittedtomofpi_data = detailedprojectreportsubmittedtomofpi_data;
	}

	public String getDetailedprojectreportsubmittedtomofpi() {
		return detailedprojectreportsubmittedtomofpi;
	}

	public void setDetailedprojectreportsubmittedtomofpi(String detailedprojectreportsubmittedtomofpi) {
		this.detailedprojectreportsubmittedtomofpi = detailedprojectreportsubmittedtomofpi;
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

	public byte[] getIemudyogaadharudyamregisterData() {
		return iemudyogaadharudyamregisterData;
	}

	public void setIemudyogaadharudyamregisterData(byte[] iemudyogaadharudyamregisterData) {
		this.iemudyogaadharudyamregisterData = iemudyogaadharudyamregisterData;
	}

	public String getIemudyogaadharudyamregister() {
		return iemudyogaadharudyamregister;
	}

	public void setIemudyogaadharudyamregister(String iemudyogaadharudyamregister) {
		this.iemudyogaadharudyamregister = iemudyogaadharudyamregister;
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

	public byte[] getAnnexurea6Data() {
		return annexurea6Data;
	}

	public void setAnnexurea6Data(byte[] annexurea6Data) {
		this.annexurea6Data = annexurea6Data;
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

	public byte[] getSanctionletterissuedbymofpigovernmentofindiaData() {
		return sanctionletterissuedbymofpigovernmentofindiaData;
	}

	public void setSanctionletterissuedbymofpigovernmentofindiaData(
			byte[] sanctionletterissuedbymofpigovernmentofindiaData) {
		this.sanctionletterissuedbymofpigovernmentofindiaData = sanctionletterissuedbymofpigovernmentofindiaData;
	}

	public String getSanctionletterissuedbymofpigovernmentofindia() {
		return sanctionletterissuedbymofpigovernmentofindia;
	}

	public void setSanctionletterissuedbymofpigovernmentofindia(String sanctionletterissuedbymofpigovernmentofindia) {
		this.sanctionletterissuedbymofpigovernmentofindia = sanctionletterissuedbymofpigovernmentofindia;
	}

}
