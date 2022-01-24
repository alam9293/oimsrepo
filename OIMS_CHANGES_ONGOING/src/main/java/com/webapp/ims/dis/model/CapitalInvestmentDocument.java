package com.webapp.ims.dis.model;

import java.io.Serializable;

import org.springframework.data.mongodb.core.mapping.Document;

import com.webapp.ims.model.MongoDBDocument;

@Document(collection = "Ind_CapitalInvestment_Doc")
public class CapitalInvestmentDocument extends MongoDBDocument implements Serializable {

	private static final long serialVersionUID = 1L;

	private String capInvId;

	public CapitalInvestmentDocument() {
	}

	public CapitalInvestmentDocument(String fileName, String fileType, byte[] data) {
		super(fileName, fileType, data);
	}

	public String getCapInvId() {
		return capInvId;
	}

	public void setCapInvId(String capInvId) {
		this.capInvId = capInvId;
	}

}
