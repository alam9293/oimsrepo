package com.webapp.ims.model;

import java.io.Serializable;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "Ind_New_Proj_Details_Doc")
public class NewProjDetailsDocument extends MongoDBDocument implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private String projectId;
	
	public String getProjectId() {
		return projectId;
	}

	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}

	public NewProjDetailsDocument() {
	}

	public NewProjDetailsDocument(String fileName, String fileType, byte[] data) {
		super(fileName, fileType, data);
	}

}
