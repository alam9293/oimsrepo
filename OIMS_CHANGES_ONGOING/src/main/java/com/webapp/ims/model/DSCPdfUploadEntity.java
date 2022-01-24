package com.webapp.ims.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "DSC_PdfUpload", schema = "loc")
public class DSCPdfUploadEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	@Column(name = "id")
	private int id;
	@Column(name = "file")
	private byte[] file;

	@Column(name = "filename")
	private String fileName;

	@Column(name = "filesize")
	private String fileSize;
	@Column(name = "sendername")
	private String senderName;

	@Column(name = "createddate")
	private String createdDate;
	@Column(name = "agendacreatestatus")
	private boolean agendaCreateStatus;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public byte[] getFile() {
		return file;
	}

	public void setFile(byte[] file) {
		this.file = file;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getFileSize() {
		return fileSize;
	}

	public void setFileSize(String fileSize) {
		this.fileSize = fileSize;
	}

	public String getSenderName() {
		return senderName;
	}

	public void setSenderName(String senderName) {
		this.senderName = senderName;
	}

	public String getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}

	public boolean isAgendaCreateStatus() {
		return agendaCreateStatus;
	}

	public void setAgendaCreateStatus(boolean agendaCreateStatus) {
		this.agendaCreateStatus = agendaCreateStatus;
	}

}
