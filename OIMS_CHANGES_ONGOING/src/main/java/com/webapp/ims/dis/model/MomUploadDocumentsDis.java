package com.webapp.ims.dis.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "Dis_Mom_Upload_Doc")
public class MomUploadDocumentsDis implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	private String _id;
	private String documentId;
	private String fileName;
	private String fileType;
	private byte[] fileData;
	private String docCreatedBy;
	private String docModifiedBy;
	private String momId;
	@Temporal(TemporalType.DATE)
	private Date docUpdateDate;
	@Temporal(TemporalType.DATE)
	private Date docCreatedDate;

	public String get_id() {
		return _id;
	}

	public void set_id(String _id) {
		this._id = _id;
	}

	public String getDocumentId() {
		return documentId;
	}

	public void setDocumentId(String documentId) {
		this.documentId = documentId;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getFileType() {
		return fileType;
	}

	public void setFileType(String fileType) {
		this.fileType = fileType;
	}

	public byte[] getFileData() {
		return fileData;
	}

	public void setFileData(byte[] fileData) {
		this.fileData = fileData;
	}

	public String getDocCreatedBy() {
		return docCreatedBy;
	}

	public void setDocCreatedBy(String docCreatedBy) {
		this.docCreatedBy = docCreatedBy;
	}

	public String getDocModifiedBy() {
		return docModifiedBy;
	}

	public void setDocModifiedBy(String docModifiedBy) {
		this.docModifiedBy = docModifiedBy;
	}

	public String getMomId() {
		return momId;
	}

	public void setMomId(String momId) {
		this.momId = momId;
	}

	public Date getDocUpdateDate() {
		return docUpdateDate;
	}

	public void setDocUpdateDate(Date docUpdateDate) {
		this.docUpdateDate = docUpdateDate;
	}

	public Date getDocCreatedDate() {
		return docCreatedDate;
	}

	public void setDocCreatedDate(Date docCreatedDate) {
		this.docCreatedDate = docCreatedDate;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
