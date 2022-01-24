package com.webapp.ims.service;

import java.util.List;

import com.webapp.ims.model.NewProjectDetails;

public interface NewProjectDetailsService {
	
	public List<NewProjectDetails> getNewProjList();

	public void saveNewProject(NewProjectDetails newProjDetails);

	public List<NewProjectDetails> saveNewProjList(List<NewProjectDetails> newProjDetailsList);

	public List<NewProjectDetails> getNewProjListById(String id);

	public void deleteNewProjById(String id);

	public void deleteNewProject(NewProjectDetails newProjDetails);


}
