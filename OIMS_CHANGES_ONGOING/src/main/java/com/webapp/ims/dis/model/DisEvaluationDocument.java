package com.webapp.ims.dis.model;

import java.io.Serializable;

import org.springframework.data.mongodb.core.mapping.Document;

import com.webapp.ims.model.MongoDBDocument;

@Document(collection = "Ind_DIS_Evaluation_Doc")
public class DisEvaluationDocument extends MongoDBDocument implements Serializable {

	private static final long serialVersionUID = 1L;

	private String disEvalId;

	public DisEvaluationDocument() {
	}

	public DisEvaluationDocument(String fileName, String fileType, byte[] data) {
		super(fileName, fileType, data);
	}

	public String getDisEvalId() {
		return disEvalId;
	}

	public void setDisEvalId(String disEvalId) {
		this.disEvalId = disEvalId;
	}

}
