package com.webapp.ims.dis.model;

import java.io.Serializable;

import org.springframework.data.mongodb.core.mapping.Document;

import com.webapp.ims.model.MongoDBDocument;

@Document(collection = "Ind_DIS_CIS_Doc")
public class CISDocument extends MongoDBDocument implements Serializable {

	private static final long serialVersionUID = 1L;

	private String disCisId;

	public CISDocument() {
	}

	public CISDocument(String fileName, String fileType, byte[] data) {
		super(fileName, fileType, data);
	}

	public String getDisCisId() {
		return disCisId;
	}

	public void setDisCisId(String disCisId) {
		this.disCisId = disCisId;
	}

}
