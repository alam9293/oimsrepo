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
public class FoodDocumentFMP_PMKSY implements Serializable {

	@Id
	@Column(name = "unit_id")
	private String unit_id;

	@Id
	@Column(name = "control_id")
	private String control_id;

	@Id
	@Column(name = "id")
	private Identifier id;

	@Column(name = "incorporationcertificateofspvpea_data")
	private byte[] incorporationcertificateofspvpeaData;
	@Column(name = "incorporationcertificateofspvpea")
	private String incorporationcertificateofspvpea;

	@Column(name = "memorandumamparticleofassociationandbyelawsofthespvpea_data")
	private byte[] memorandumamparticleofassociationandbyelawsofthespvpeaData;
	@Column(name = "memorandumamparticleofassociationandbyelawsofthespvpea")
	private String memorandumamparticleofassociationandbyelawsofthespvpea;

	@Column(name = "registeredlanddeed_data")
	private byte[] registeredlanddeedData;
	@Column(name = "registeredlanddeed")
	private String registeredlanddeed;

	@Column(name = "rentagreementregisterednbsp_data")
	private byte[] rentagreementregisterednbspData;
	@Column(name = "rentagreementregisterednbsp")
	private String rentagreementregisterednbsp;

	@Column(name = "khasrakhatauni_data")
	private byte[] khasrakhatauniData;
	@Column(name = "khasrakhatauni")
	private String khasrakhatauni;

	@Column(name = "landusecertificate_data")
	private byte[] landusecertificateData;
	@Column(name = "landusecertificate")
	private String landusecertificate;

	@Column(name = "detailedprojectreportsubmittedtoministryoffoodprocessingindus_data")
	private byte[] detailedprojectreportsubmittedtoministryoffoodprocessingindusData;
	@Column(name = "detailedprojectreportsubmittedtoministryoffoodprocessingindus")
	private String detailedprojectreportsubmittedtoministryoffoodprocessingindus;

	@Column(name = "termloansanctionletter_data")
	private byte[] termloansanctionletterData;
	@Column(name = "termloansanctionletter")
	private String termloansanctionletter;

	@Column(name = "bankappraisalreport_data")
	private byte[] bankappraisalreportData;
	@Column(name = "bankappraisalreport")
	private String bankappraisalreport;

	@Column(name = "layoutplanformegafoodparkagroprocessingcluster_data")
	private byte[] layoutplanformegafoodparkagroprocessingclusterData;
	@Column(name = "layoutplanformegafoodparkagroprocessingcluster")
	private String layoutplanformegafoodparkagroprocessingcluster;

	@Column(name = "annexurea6data")
	private byte[] annexurea6Data;
	@Column(name = "annexurea6")
	private String annexurea6;

	@Column(name = "annexurea4data")
	private byte[] annexurea4Data;
	@Column(name = "annexurea4")
	private String annexurea4;

	@Column(name = "annexurea10data")
	private byte[] annexurea10Data;
	@Column(name = "annexurea10")
	private String annexurea10;

	@Column(name = "annexurea11data")
	private byte[] annexurea11Data;
	@Column(name = "annexurea11")
	private String annexurea11;

	@Column(name = "sanctionedletterissuedbyministryoffoodprocessingindustriesgov_data")
	private byte[] sanctionedletterissuedbyministryoffoodprocessingindustriesgovData;
	@Column(name = "sanctionedletterissuedbyministryoffoodprocessingindustriesgov")
	private String sanctionedletterissuedbyministryoffoodprocessingindustriesgov;

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

	public byte[] getIncorporationcertificateofspvpeaData() {
		return incorporationcertificateofspvpeaData;
	}

	public void setIncorporationcertificateofspvpeaData(byte[] incorporationcertificateofspvpeaData) {
		this.incorporationcertificateofspvpeaData = incorporationcertificateofspvpeaData;
	}

	public String getIncorporationcertificateofspvpea() {
		return incorporationcertificateofspvpea;
	}

	public void setIncorporationcertificateofspvpea(String incorporationcertificateofspvpea) {
		this.incorporationcertificateofspvpea = incorporationcertificateofspvpea;
	}

	public byte[] getMemorandumamparticleofassociationandbyelawsofthespvpeaData() {
		return memorandumamparticleofassociationandbyelawsofthespvpeaData;
	}

	public void setMemorandumamparticleofassociationandbyelawsofthespvpeaData(
			byte[] memorandumamparticleofassociationandbyelawsofthespvpeaData) {
		this.memorandumamparticleofassociationandbyelawsofthespvpeaData = memorandumamparticleofassociationandbyelawsofthespvpeaData;
	}

	public String getMemorandumamparticleofassociationandbyelawsofthespvpea() {
		return memorandumamparticleofassociationandbyelawsofthespvpea;
	}

	public void setMemorandumamparticleofassociationandbyelawsofthespvpea(
			String memorandumamparticleofassociationandbyelawsofthespvpea) {
		this.memorandumamparticleofassociationandbyelawsofthespvpea = memorandumamparticleofassociationandbyelawsofthespvpea;
	}

	public byte[] getRegisteredlanddeedData() {
		return registeredlanddeedData;
	}

	public void setRegisteredlanddeedData(byte[] registeredlanddeedData) {
		this.registeredlanddeedData = registeredlanddeedData;
	}

	public String getRegisteredlanddeed() {
		return registeredlanddeed;
	}

	public void setRegisteredlanddeed(String registeredlanddeed) {
		this.registeredlanddeed = registeredlanddeed;
	}

	public byte[] getRentagreementregisterednbspData() {
		return rentagreementregisterednbspData;
	}

	public void setRentagreementregisterednbspData(byte[] rentagreementregisterednbspData) {
		this.rentagreementregisterednbspData = rentagreementregisterednbspData;
	}

	public String getRentagreementregisterednbsp() {
		return rentagreementregisterednbsp;
	}

	public void setRentagreementregisterednbsp(String rentagreementregisterednbsp) {
		this.rentagreementregisterednbsp = rentagreementregisterednbsp;
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

	public byte[] getLandusecertificateData() {
		return landusecertificateData;
	}

	public void setLandusecertificateData(byte[] landusecertificateData) {
		this.landusecertificateData = landusecertificateData;
	}

	public String getLandusecertificate() {
		return landusecertificate;
	}

	public void setLandusecertificate(String landusecertificate) {
		this.landusecertificate = landusecertificate;
	}

	public byte[] getDetailedprojectreportsubmittedtoministryoffoodprocessingindusData() {
		return detailedprojectreportsubmittedtoministryoffoodprocessingindusData;
	}

	public void setDetailedprojectreportsubmittedtoministryoffoodprocessingindusData(
			byte[] detailedprojectreportsubmittedtoministryoffoodprocessingindusData) {
		this.detailedprojectreportsubmittedtoministryoffoodprocessingindusData = detailedprojectreportsubmittedtoministryoffoodprocessingindusData;
	}

	public String getDetailedprojectreportsubmittedtoministryoffoodprocessingindus() {
		return detailedprojectreportsubmittedtoministryoffoodprocessingindus;
	}

	public void setDetailedprojectreportsubmittedtoministryoffoodprocessingindus(
			String detailedprojectreportsubmittedtoministryoffoodprocessingindus) {
		this.detailedprojectreportsubmittedtoministryoffoodprocessingindus = detailedprojectreportsubmittedtoministryoffoodprocessingindus;
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

	public byte[] getLayoutplanformegafoodparkagroprocessingclusterData() {
		return layoutplanformegafoodparkagroprocessingclusterData;
	}

	public void setLayoutplanformegafoodparkagroprocessingclusterData(
			byte[] layoutplanformegafoodparkagroprocessingclusterData) {
		this.layoutplanformegafoodparkagroprocessingclusterData = layoutplanformegafoodparkagroprocessingclusterData;
	}

	public String getLayoutplanformegafoodparkagroprocessingcluster() {
		return layoutplanformegafoodparkagroprocessingcluster;
	}

	public void setLayoutplanformegafoodparkagroprocessingcluster(
			String layoutplanformegafoodparkagroprocessingcluster) {
		this.layoutplanformegafoodparkagroprocessingcluster = layoutplanformegafoodparkagroprocessingcluster;
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

	public byte[] getAnnexurea4Data() {
		return annexurea4Data;
	}

	public void setAnnexurea4Data(byte[] annexurea4Data) {
		this.annexurea4Data = annexurea4Data;
	}

	public String getAnnexurea4() {
		return annexurea4;
	}

	public void setAnnexurea4(String annexurea4) {
		this.annexurea4 = annexurea4;
	}

	public byte[] getAnnexurea10Data() {
		return annexurea10Data;
	}

	public void setAnnexurea10Data(byte[] annexurea10Data) {
		this.annexurea10Data = annexurea10Data;
	}

	public String getAnnexurea10() {
		return annexurea10;
	}

	public void setAnnexurea10(String annexurea10) {
		this.annexurea10 = annexurea10;
	}

	public byte[] getAnnexurea11Data() {
		return annexurea11Data;
	}

	public void setAnnexurea11Data(byte[] annexurea11Data) {
		this.annexurea11Data = annexurea11Data;
	}

	public String getAnnexurea11() {
		return annexurea11;
	}

	public void setAnnexurea11(String annexurea11) {
		this.annexurea11 = annexurea11;
	}

	public byte[] getSanctionedletterissuedbyministryoffoodprocessingindustriesgovData() {
		return sanctionedletterissuedbyministryoffoodprocessingindustriesgovData;
	}

	public void setSanctionedletterissuedbyministryoffoodprocessingindustriesgovData(
			byte[] sanctionedletterissuedbyministryoffoodprocessingindustriesgovData) {
		this.sanctionedletterissuedbyministryoffoodprocessingindustriesgovData = sanctionedletterissuedbyministryoffoodprocessingindustriesgovData;
	}

	public String getSanctionedletterissuedbyministryoffoodprocessingindustriesgov() {
		return sanctionedletterissuedbyministryoffoodprocessingindustriesgov;
	}

	public void setSanctionedletterissuedbyministryoffoodprocessingindustriesgov(
			String sanctionedletterissuedbyministryoffoodprocessingindustriesgov) {
		this.sanctionedletterissuedbyministryoffoodprocessingindustriesgov = sanctionedletterissuedbyministryoffoodprocessingindustriesgov;
	}

}
