package com.webapp.ims.dis.model;

import java.io.Serializable;

import org.springframework.data.mongodb.core.mapping.Document;

import com.webapp.ims.model.MongoDBDocument;

@Document(collection = "DIS_Ind_Raise_Query_Doc")
public class DisRaiseQueryDocument extends MongoDBDocument implements Serializable {
	private static final long serialVersionUID = 1L;

	private String docUserId;

	public DisRaiseQueryDocument() {
	}

	public DisRaiseQueryDocument(String fileName, String fileType, byte[] data) {
		super(fileName, fileType, data);
	}

	public String getDocUserId() {
		return docUserId;
	}

	public void setDocUserId(String docUserId) {
		this.docUserId = docUserId;
	}

}
