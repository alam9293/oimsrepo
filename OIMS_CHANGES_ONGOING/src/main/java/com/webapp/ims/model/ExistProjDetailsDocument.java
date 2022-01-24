package com.webapp.ims.model;

import java.io.Serializable;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "Ind_Exist_Proj_Details_Doc")
public class ExistProjDetailsDocument extends MongoDBDocument implements Serializable {
	private static final long serialVersionUID = 1L;

	private String projectId;

	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}

	public String getProjectId() {
		return projectId;
	}

	public ExistProjDetailsDocument() {
	}

	public ExistProjDetailsDocument(String fileName, String fileType, byte[] data) {
		super(fileName, fileType, data);
	}

}
