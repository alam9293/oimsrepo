package com.webapp.ims.dis.model;

import java.io.Serializable;

import org.springframework.data.mongodb.core.mapping.Document;

import com.webapp.ims.model.MongoDBDocument;

@Document(collection = "Ind_DIS_IIS_Doc")
public class IISDocument extends MongoDBDocument implements Serializable {

	private static final long serialVersionUID = 1L;

	private String disIISId;

	public IISDocument() {
	}

	public IISDocument(String fileName, String fileType, byte[] data) {
		super(fileName, fileType, data);
	}

	public String getDisIISId() {
		return disIISId;
	}

	public void setDisIISId(String disIISId) {
		this.disIISId = disIISId;
	}

}
