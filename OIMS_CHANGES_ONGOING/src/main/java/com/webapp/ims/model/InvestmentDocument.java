package com.webapp.ims.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.data.mongodb.core.mapping.Document;

@Entity
@Document(collection = "Ind_Investment_Details_Doc")
public class InvestmentDocument implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	private String _id;
	private String docId;
	private String fileName;
	private String fileType;
	private byte[] data;
	private String docCreatedBy;
	private String appModifiedBy;
	private String investId;
	@Temporal(TemporalType.DATE)
	private Date docUpdateDate;
	@Temporal(TemporalType.DATE)
	private Date docCreatedDate;

	public InvestmentDocument() {
	}

	public InvestmentDocument(String fileName, String fileType, byte[] data) {
		this.fileName = fileName;
		this.fileType = fileType;
		this.data = data;
	}

	public String get_id() {
		return _id;
	}

	public void set_id(String _id) {
		this._id = _id;
	}

	public Date getDocCreatedDate() {
		return docCreatedDate;
	}

	public void setDocCreatedDate(Date docCreatedDate) {
		this.docCreatedDate = docCreatedDate;
	}

	public Date getDocUpdateDate() {
		return docUpdateDate;
	}

	public void setDocUpdateDate(Date docUpdateDate) {
		this.docUpdateDate = docUpdateDate;
	}

	public String getFileName() {
		return fileName;
	}

	public String getFileType() {
		return fileType;
	}

	public byte[] getData() {
		return data;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public void setFileType(String fileType) {
		this.fileType = fileType;
	}

	public void setData(byte[] data) {
		this.data = data;
	}

	public String getDocId() {
		return docId;
	}

	public void setDocId(String docId) {
		this.docId = docId;
	}

	public String getDocCreatedBy() {
		return docCreatedBy;
	}

	public void setDocCreatedBy(String docCreatedBy) {
		this.docCreatedBy = docCreatedBy;
	}

	public String getAppModifiedBy() {
		return appModifiedBy;
	}

	public void setAppModifiedBy(String appModifiedBy) {
		this.appModifiedBy = appModifiedBy;
	}

	public String getInvestId() {
		return investId;
	}

	public void setInvestId(String investId) {
		this.investId = investId;
	}

}
