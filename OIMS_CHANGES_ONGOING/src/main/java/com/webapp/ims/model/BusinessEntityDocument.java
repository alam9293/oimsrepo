package com.webapp.ims.model;

import java.io.Serializable;

import org.springframework.data.mongodb.core.mapping.Document;


@Document(collection = "Ind_Business_Entity_Doc")
public class BusinessEntityDocument extends MongoDBDocument implements Serializable {

	private static final long serialVersionUID = 1L;

	private String businessEntityId;

	public BusinessEntityDocument() {
	}

	public BusinessEntityDocument(String fileName, String fileType, byte[] data) {
		super(fileName, fileType, data);
	}

	public String getBusinessEntityId() {
		return businessEntityId;
	}

	public void setBusinessEntityId(String businessEntityId) {
		this.businessEntityId = businessEntityId;
	}

}
