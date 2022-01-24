package com.webapp.ims.service;

import java.util.List;
import java.util.Optional;

import com.webapp.ims.model.ExistingProjectDetails;
import com.webapp.ims.model.ProjectDetails;

@SuppressWarnings("unused")
public interface ProjectService {

	public List<ProjectDetails> getAllProjects();

	public ProjectDetails saveProject(ProjectDetails project);

	public Optional<ProjectDetails> getProjectById(String id);
	
	public void deleteProjectById(String id);
	
	public void deleteProject(ProjectDetails project);

	public ProjectDetails updateProject(ProjectDetails project);
	public ProjectDetails getProjDetById (String appId);
	
	public ProjectDetails getProjectByapplicantDetailId(String appId);	
	
}
