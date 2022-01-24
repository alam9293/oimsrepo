package com.webapp.ims.dis.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "Dis_Applicant_Detail", schema = "loc")
public class DissbursmentApplicantDetails implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "DIS_ID")
	private String disId;

	@Column(name = "DIS_APC_ID")
	private String disAppId;

	@Column(name = "DIS_APC_AuthSignName")
	private String bussAuthSigName;

	@Column(name = "DIS_APC_Designation")
	private String bussDesignation;

	@Column(name = "DIS_APC_BussName")
	private String bussNameDis;

	@Column(name = "DIS_APC_BussAddrs")
	private String bussAddressDis;

	@Column(name = "DIS_APC_BussCountry")
	private String bussCountryDis;

	@Column(name = "DIS_APC_BussState")
	private String bussStateDis;

	@Column(name = "DIS_APC_BussDistrict")
	private String bussDistrictDis;

	@Column(name = "DIS_APC_BussPinCode")
	private Long bussPinCodeDis;

	@Column(name = "DIS_APC_ProjAddrs")
	private String projAddressDis;

	@Column(name = "DIS_APC_ProjDistrict")
	private String projDistrictDis;

	@Column(name = "DIS_APC_ProjMandal")
	private String projMandalDis;

	@Column(name = "DIS_APC_ProjRegion")
	private String projRegionDis;

	@Column(name = "DIS_APC_ProjPinCode")
	private Long projPinCodeDis;

	@Column(name = "DIS_APC_Create_By")
	private String createBy;

	@Column(name = "DIS_APC_Create_Date")
	@Temporal(TemporalType.TIMESTAMP)
	private Date createDate;

	@Column(name = "DIS_APC_Modified_By")
	private String modifiedBy;

	@Column(name = "DIS_APC_Modify_Date")
	@Temporal(TemporalType.TIMESTAMP)
	private Date modifiyDate;

	@Column(name = "DIS_APC_LocNumber")
	private String locNumberDis;

	public String getLocNumberDis() {
		return locNumberDis;
	}

	public void setLocNumberDis(String locNumberDis) {
		this.locNumberDis = locNumberDis;
	}

	public String getDisId() {
		return disId;
	}

	public void setDisId(String disId) {
		this.disId = disId;
	}

	public String getDisAppId() {
		return disAppId;
	}

	public void setDisAppId(String disAppId) {
		this.disAppId = disAppId;
	}

	public String getBussNameDis() {
		return bussNameDis;
	}

	public void setBussNameDis(String bussNameDis) {
		this.bussNameDis = bussNameDis;
	}

	public String getBussAddressDis() {
		return bussAddressDis;
	}

	public void setBussAddressDis(String bussAddressDis) {
		this.bussAddressDis = bussAddressDis;
	}

	public String getBussCountryDis() {
		return bussCountryDis;
	}

	public void setBussCountryDis(String bussCountryDis) {
		this.bussCountryDis = bussCountryDis;
	}

	public String getBussStateDis() {
		return bussStateDis;
	}

	public void setBussStateDis(String bussStateDis) {
		this.bussStateDis = bussStateDis;
	}

	public String getBussDistrictDis() {
		return bussDistrictDis;
	}

	public void setBussDistrictDis(String bussDistrictDis) {
		this.bussDistrictDis = bussDistrictDis;
	}

	public Long getBussPinCodeDis() {
		return bussPinCodeDis;
	}

	public void setBussPinCodeDis(Long bussPinCodeDis) {
		this.bussPinCodeDis = bussPinCodeDis;
	}

	public String getProjAddressDis() {
		return projAddressDis;
	}

	public void setProjAddressDis(String projAddressDis) {
		this.projAddressDis = projAddressDis;
	}

	public String getProjDistrictDis() {
		return projDistrictDis;
	}

	public void setProjDistrictDis(String projDistrictDis) {
		this.projDistrictDis = projDistrictDis;
	}

	public String getProjMandalDis() {
		return projMandalDis;
	}

	public void setProjMandalDis(String projMandalDis) {
		this.projMandalDis = projMandalDis;
	}

	public String getProjRegionDis() {
		return projRegionDis;
	}

	public void setProjRegionDis(String projRegionDis) {
		this.projRegionDis = projRegionDis;
	}

	public Long getProjPinCodeDis() {
		return projPinCodeDis;
	}

	public void setProjPinCodeDis(Long projPinCodeDis) {
		this.projPinCodeDis = projPinCodeDis;
	}

	public String getCreateBy() {
		return createBy;
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getModifiedBy() {
		return modifiedBy;
	}

	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	public Date getModifiyDate() {
		return modifiyDate;
	}

	public void setModifiyDate(Date modifiyDate) {
		this.modifiyDate = modifiyDate;
	}

	public String getBussAuthSigName() {
		return bussAuthSigName;
	}

	public void setBussAuthSigName(String bussAuthSigName) {
		this.bussAuthSigName = bussAuthSigName;
	}

	public String getBussDesignation() {
		return bussDesignation;
	}

	public void setBussDesignation(String bussDesignation) {
		this.bussDesignation = bussDesignation;
	}

}
