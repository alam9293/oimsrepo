package com.webapp.ims.model;

import java.io.Serializable;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "Ind_Response_Query_Doc")
public class ResponseQueryDocument extends MongoDBDocument implements Serializable {
	private static final long serialVersionUID = 1L;
	private String responseUserId;
	
	public ResponseQueryDocument() {
		super();
	}

	public ResponseQueryDocument(String fileName, String fileType, byte[] data) {
		super(fileName, fileType, data);
	}

	public String getResponseUserId() {
		return responseUserId;
	}

	public void setResponseUserId(String responseUserId) {
		this.responseUserId = responseUserId;
	}

}
