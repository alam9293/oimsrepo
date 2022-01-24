package com.webapp.ims.model;

import java.util.Date;

import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * @author Pankaj Sahu . It is a super class of all document related classes.
 */
public abstract class MongoDBDocument {
	@Id
	private String _id;
	private String docId;
	private String fieldName;
	private String fileName;
	private String fileType;
	private byte[] data;
	private String docCreatedBy;
	private String docModifiedBy;
	private String docAppId;
	@Temporal(TemporalType.DATE)
	private Date docUpdateDate;
	@Temporal(TemporalType.DATE)
	private Date docCreatedDate;

	public MongoDBDocument() {
		super();
	}

	public MongoDBDocument(String fileName, String fileType, byte[] data) {
		super();
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

	public String getDocId() {
		return docId;
	}

	public void setDocId(String docId) {
		this.docId = docId;
	}

	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
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

	public byte[] getData() {
		return data;
	}

	public void setData(byte[] data) {
		this.data = data;
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

	public String getDocAppId() {
		return docAppId;
	}

	public void setDocAppId(String docAppId) {
		this.docAppId = docAppId;
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

}
