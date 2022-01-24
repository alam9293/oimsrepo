package com.webapp.ims.service;

import java.util.List;

import com.webapp.ims.model.ExistingProjectDetails;

public interface ExistingProjectDetailsService {
	public List<ExistingProjectDetails> getExistProjList();

	public void saveExistProject(ExistingProjectDetails existProjDetails);

	public List<ExistingProjectDetails> saveExistProjList(List<ExistingProjectDetails> existProjDetailsList);

	public List<ExistingProjectDetails> getExistProjListById(String id);

	public void deleteExistProjById(String id);

	public void deleteExistProject(ExistingProjectDetails existProjDetails);

}
