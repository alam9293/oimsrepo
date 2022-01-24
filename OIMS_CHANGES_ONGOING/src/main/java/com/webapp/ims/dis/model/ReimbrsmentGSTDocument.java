package com.webapp.ims.dis.model;

import java.io.Serializable;

import org.springframework.data.mongodb.core.mapping.Document;

import com.webapp.ims.model.MongoDBDocument;

@Document(collection = "Ind_ReimbrsDepsitGST_Doc")
public class ReimbrsmentGSTDocument extends MongoDBDocument implements Serializable {

	private static final long serialVersionUID = 1L;

	private String reimbrsGSTId;

	public ReimbrsmentGSTDocument() {
	}

	public ReimbrsmentGSTDocument(String fileName, String fileType, byte[] data) {
		super(fileName, fileType, data);
	}

	public String getReimbrsGSTId() {
		return reimbrsGSTId;
	}

	public void setReimbrsGSTId(String reimbrsGSTId) {
		this.reimbrsGSTId = reimbrsGSTId;
	}

}
