package com.webapp.ims.dis.model;

import java.io.Serializable;

import org.springframework.data.mongodb.core.mapping.Document;

import com.webapp.ims.model.MongoDBDocument;

@Document(collection = "Ind_DIS_EPF_Doc")
public class EPFDocument extends MongoDBDocument implements Serializable {

	private static final long serialVersionUID = 1L;

	private String disEpfId;

	public EPFDocument() {
	}

	public EPFDocument(String fileName, String fileType, byte[] data) {
		super(fileName, fileType, data);
	}

	public String getDisEpfId() {
		return disEpfId;
	}

	public void setDisEpfId(String disEpfId) {
		this.disEpfId = disEpfId;
	}

}
