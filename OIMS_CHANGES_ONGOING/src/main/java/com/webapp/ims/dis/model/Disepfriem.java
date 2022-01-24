/**
 * Author:: Sachin
* Created on:: 
 */
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
@Table(name = "Disepfriem", schema = "loc")
public class Disepfriem implements Serializable{


	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "Epf_Reim_ID")
	private String id;

	@Column(name = "Epf_APC_ID")
	private String appId;

	@Column(name = "Skilled_Male_Employees")
	private Integer skillmale;

	@Column(name = "Skilled_Female_Employees")
	private Integer skillfemale;

	@Column(name = "Skilled_Employees")
	private Integer skillemp;

	@Column(name = "UnSkilled_Male_Employees")
	private Integer unskillmale;

	@Column(name = "UnSkilled_Female_Employees")
	private Integer unskillfemale;

	@Column(name = "UnSkilled_Employees")
	private Integer unskillemp;

	@Column(name = "Total_Male_Employees")
	private Integer totalmale;

	@Column(name = "Total_Female_Employees")
	private Integer totalfemale;

	@Column(name = "Epf_Reim")
	private Integer epfReim;

	@Column(name = "Epf_Reim_Worker")
	private Integer epfReimSkUnWorker;

	@Column(name = "Epf_Eqyity")
	private Integer epfEquity;

	@Column(name = "Epf_Total")
	private Integer EpfTotal;

	@Column(name = "Created_By")
	private String createdBy;

	@Column(name = "Modified_By")
	private String modifiedtedBy;

	@Column(name = "Status")
	private String status;

	@Column(name = "DIS_EPF_Create_By")
	private String createBy;

	@Column(name = "DIS_EPF_Create_Date")
	@Temporal(TemporalType.TIMESTAMP)
	private Date createDate;

	@Column(name = "DIS_EPF_Modified_By")
	private String modifiedBy;

	@Column(name = "DIS_EPF_Modify_Date")
	@Temporal(TemporalType.TIMESTAMP)
	private Date modifiyDate;
	

	private transient String epfComputFinYr;
	
	
	private transient String dateFrom;
	
	private transient String dateTo;
	

	private transient String employerContributionEPF;

	private transient String affidavitDoc;
	private transient String copyFormDoc;
	private transient String monthwiseDoc;

	private transient String affidavitDocBase64File;
	private transient String copyFormDocBase64File;
	private transient String monthwiseDocBase64File;

	public String getAffidavitDoc() {
		return affidavitDoc;
	}

	public void setAffidavitDoc(String affidavitDoc) {
		this.affidavitDoc = affidavitDoc;
	}

	public String getCopyFormDoc() {
		return copyFormDoc;
	}

	public void setCopyFormDoc(String copyFormDoc) {
		this.copyFormDoc = copyFormDoc;
	}

	public String getMonthwiseDoc() {
		return monthwiseDoc;
	}

	public void setMonthwiseDoc(String monthwiseDoc) {
		this.monthwiseDoc = monthwiseDoc;
	}

	public String getAffidavitDocBase64File() {
		return affidavitDocBase64File;
	}

	public void setAffidavitDocBase64File(String affidavitDocBase64File) {
		this.affidavitDocBase64File = affidavitDocBase64File;
	}

	public String getCopyFormDocBase64File() {
		return copyFormDocBase64File;
	}

	public void setCopyFormDocBase64File(String copyFormDocBase64File) {
		this.copyFormDocBase64File = copyFormDocBase64File;
	}
	
	

	public String getMonthwiseDocBase64File() {
		return monthwiseDocBase64File;
	}

	public void setMonthwiseDocBase64File(String monthwiseDocBase64File) {
		this.monthwiseDocBase64File = monthwiseDocBase64File;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	public Integer getSkillmale() {
		return skillmale;
	}

	public void setSkillmale(Integer skillmale) {
		this.skillmale = skillmale;
	}

	public Integer getSkillfemale() {
		return skillfemale;
	}

	public void setSkillfemale(Integer skillfemale) {
		this.skillfemale = skillfemale;
	}

	public Integer getSkillemp() {
		return skillemp;
	}

	public void setSkillemp(Integer skillemp) {
		this.skillemp = skillemp;
	}

	public Integer getUnskillmale() {
		return unskillmale;
	}

	public void setUnskillmale(Integer unskillmale) {
		this.unskillmale = unskillmale;
	}

	public Integer getUnskillfemale() {
		return unskillfemale;
	}

	public void setUnskillfemale(Integer unskillfemale) {
		this.unskillfemale = unskillfemale;
	}

	public Integer getUnskillemp() {
		return unskillemp;
	}

	public void setUnskillemp(Integer unskillemp) {
		this.unskillemp = unskillemp;
	}

	public Integer getTotalmale() {
		return totalmale;
	}

	public void setTotalmale(Integer totalmale) {
		this.totalmale = totalmale;
	}

	public Integer getTotalfemale() {
		return totalfemale;
	}

	public void setTotalfemale(Integer totalfemale) {
		this.totalfemale = totalfemale;
	}

	public Integer getEpfReim() {
		return epfReim;
	}

	public void setEpfReim(Integer epfReim) {
		this.epfReim = epfReim;
	}

	public Integer getEpfReimSkUnWorker() {
		return epfReimSkUnWorker;
	}

	public void setEpfReimSkUnWorker(Integer epfReimSkUnWorker) {
		this.epfReimSkUnWorker = epfReimSkUnWorker;
	}

	public Integer getEpfEquity() {
		return epfEquity;
	}

	public void setEpfEquity(Integer epfEquity) {
		this.epfEquity = epfEquity;
	}

	public Integer getEpfTotal() {
		return EpfTotal;
	}

	public void setEpfTotal(Integer epfTotal) {
		EpfTotal = epfTotal;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public String getModifiedtedBy() {
		return modifiedtedBy;
	}

	public void setModifiedtedBy(String modifiedtedBy) {
		this.modifiedtedBy = modifiedtedBy;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
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

	public String getEpfComputFinYr() {
		return epfComputFinYr;
	}

	public void setEpfComputFinYr(String epfComputFinYr) {
		this.epfComputFinYr = epfComputFinYr;
	}

	public String getDateFrom() {
		return dateFrom;
	}

	public void setDateFrom(String dateFrom) {
		this.dateFrom = dateFrom;
	}

	public String getDateTo() {
		return dateTo;
	}

	public void setDateTo(String dateTo) {
		this.dateTo = dateTo;
	}

	public String getEmployerContributionEPF() {
		return employerContributionEPF;
	}

	public void setEmployerContributionEPF(String employerContributionEPF) {
		this.employerContributionEPF = employerContributionEPF;
	}

	
}
