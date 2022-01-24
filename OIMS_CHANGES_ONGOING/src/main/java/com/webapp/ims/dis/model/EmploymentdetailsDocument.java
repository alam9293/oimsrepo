package com.webapp.ims.dis.model;

import java.io.Serializable;

import org.springframework.data.mongodb.core.mapping.Document;

import com.webapp.ims.model.MongoDBDocument;

@Document(collection = "Ind_EmploymentDeatils_Doc")
public class EmploymentdetailsDocument extends MongoDBDocument implements Serializable {

	private static final long serialVersionUID = 1L;

	private String emplDtlId;

	public EmploymentdetailsDocument() {
	}

	public EmploymentdetailsDocument(String fileName, String fileType, byte[] data) {
		super(fileName, fileType, data);
	}

	public String getEmplDtlId() {
		return emplDtlId;
	}

	public void setEmplDtlId(String emplDtlId) {
		this.emplDtlId = emplDtlId;
	}

}
