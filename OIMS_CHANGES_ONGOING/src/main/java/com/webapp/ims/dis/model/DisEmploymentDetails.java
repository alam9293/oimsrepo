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
@Table(name = "Dis_Employment_Detail", schema = "loc")
public class DisEmploymentDetails implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "DIS_ID")
	private String disId;

	@Column(name = "DIS_APC_ID")
	private String disAppId;

	@Column(name = "DIS_NoBPLEmpl")
	private String noBPLEmpl;

	@Column(name = "DIS_NoSCEmpl")
	private String noSCEmpl;

	@Column(name = "DIS_NoSTEmpl")
	private String noSTEmpl;

	@Column(name = "DIS_NoFemaleEmpl")
	private String noFemaleEmpl;

	@Column(name = "DIS_Create_By")
	private String createBy;

	@Column(name = "DIS_Create_Date")
	@Temporal(TemporalType.TIMESTAMP)
	private Date createDate;

	@Column(name = "DIS_Modified_By")
	private String modifiedBy;

	@Column(name = "DIS_Modify_Date")
	@Temporal(TemporalType.TIMESTAMP)
	private Date modifiyDate;

	private transient String noBPLEmplDoc;
	private transient String noSCEmplDoc;
	private transient String noSTEmplDoc;
	private transient String noFemaleEmplDoc;

	private transient String noBPLEmplDocbase64File;
	private transient String noSCEmplDocbase64File;
	private transient String noSTEmplDocbase64File;
	private transient String noFemaleEmplDocbase64File;

	public String getNoBPLEmplDoc() {
		return noBPLEmplDoc;
	}

	public void setNoBPLEmplDoc(String noBPLEmplDoc) {
		this.noBPLEmplDoc = noBPLEmplDoc;
	}

	public String getNoSCEmplDoc() {
		return noSCEmplDoc;
	}

	public void setNoSCEmplDoc(String noSCEmplDoc) {
		this.noSCEmplDoc = noSCEmplDoc;
	}

	public String getNoSTEmplDoc() {
		return noSTEmplDoc;
	}

	public void setNoSTEmplDoc(String noSTEmplDoc) {
		this.noSTEmplDoc = noSTEmplDoc;
	}

	public String getNoFemaleEmplDoc() {
		return noFemaleEmplDoc;
	}

	public void setNoFemaleEmplDoc(String noFemaleEmplDoc) {
		this.noFemaleEmplDoc = noFemaleEmplDoc;
	}

	public String getNoBPLEmplDocbase64File() {
		return noBPLEmplDocbase64File;
	}

	public void setNoBPLEmplDocbase64File(String noBPLEmplDocbase64File) {
		this.noBPLEmplDocbase64File = noBPLEmplDocbase64File;
	}

	public String getNoSCEmplDocbase64File() {
		return noSCEmplDocbase64File;
	}

	public void setNoSCEmplDocbase64File(String noSCEmplDocbase64File) {
		this.noSCEmplDocbase64File = noSCEmplDocbase64File;
	}

	public String getNoSTEmplDocbase64File() {
		return noSTEmplDocbase64File;
	}

	public void setNoSTEmplDocbase64File(String noSTEmplDocbase64File) {
		this.noSTEmplDocbase64File = noSTEmplDocbase64File;
	}

	public String getNoFemaleEmplDocbase64File() {
		return noFemaleEmplDocbase64File;
	}

	public void setNoFemaleEmplDocbase64File(String noFemaleEmplDocbase64File) {
		this.noFemaleEmplDocbase64File = noFemaleEmplDocbase64File;
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

	public String getNoBPLEmpl() {
		return noBPLEmpl;
	}

	public void setNoBPLEmpl(String noBPLEmpl) {
		this.noBPLEmpl = noBPLEmpl;
	}

	public String getNoSCEmpl() {
		return noSCEmpl;
	}

	public void setNoSCEmpl(String noSCEmpl) {
		this.noSCEmpl = noSCEmpl;
	}

	public String getNoSTEmpl() {
		return noSTEmpl;
	}

	public void setNoSTEmpl(String noSTEmpl) {
		this.noSTEmpl = noSTEmpl;
	}

	public String getNoFemaleEmpl() {
		return noFemaleEmpl;
	}

	public void setNoFemaleEmpl(String noFemaleEmpl) {
		this.noFemaleEmpl = noFemaleEmpl;
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

}
