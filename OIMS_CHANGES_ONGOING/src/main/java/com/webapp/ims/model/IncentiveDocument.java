package com.webapp.ims.model;

import java.io.Serializable;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "Ind_Incentive_Doc")
public class IncentiveDocument extends MongoDBDocument implements Serializable {
	private static final long serialVersionUID = 1L;

	public IncentiveDocument() {
	}

	public IncentiveDocument(String fileName, String fileType, byte[] data) {
		super(fileName, fileType, data);

	}

}
