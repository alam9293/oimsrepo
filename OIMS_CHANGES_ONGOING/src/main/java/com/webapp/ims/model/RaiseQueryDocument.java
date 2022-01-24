package com.webapp.ims.model;

import java.io.Serializable;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection="Ind_Raise_Query_Doc")
public class RaiseQueryDocument extends MongoDBDocument implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private String docUserId;	

	public RaiseQueryDocument() {
	}

	public RaiseQueryDocument(String fileName, String fileType, byte[] data) {
		super(fileName, fileType, data);
	}
	
	public String getDocUserId() {
		return docUserId;
	}

	public void setDocUserId(String docUserId) {
		this.docUserId = docUserId;
	}


}
