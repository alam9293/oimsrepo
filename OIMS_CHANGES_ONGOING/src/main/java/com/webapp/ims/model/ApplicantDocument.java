package com.webapp.ims.model;

import java.io.Serializable;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "Ind_Auth_Signatory_Doc")
public class ApplicantDocument extends MongoDBDocument implements Serializable {

	private static final long serialVersionUID = 1L;

	public ApplicantDocument() {
	}

	public ApplicantDocument(String fileName, String fileType, byte[] data) {
		super(fileName, fileType, data);
	}

}
