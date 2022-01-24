package com.webapp.ims.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.webapp.ims.model.DeptProjectDetails;
import com.webapp.ims.model.ProjectDetails;
import com.webapp.ims.repository.DeptProjectRepository;
import com.webapp.ims.repository.ProjectRepository;
import com.webapp.ims.service.ProjectService;

@Service
@Transactional
public class ProjectServiceImpl implements ProjectService {

	@Autowired
	ProjectRepository projectRepository;
	@Autowired
	DeptProjectRepository deptProjectRepository;

	@Override
	public List<ProjectDetails> getAllProjects() {
		return projectRepository.findAll();
	}

	public ProjectDetails saveProject(ProjectDetails project) {
		return saveProjectExtra(project);
	}
  // vinay Starts
	
	public ProjectDetails saveProjectExtra(ProjectDetails project) {
		DeptProjectDetails deptProjectDetails = new DeptProjectDetails();
		BeanUtils.copyProperties(project, deptProjectDetails);
		deptProjectRepository.save(deptProjectDetails);
		return projectRepository.save(project);
	}
	
	
	
// vinay ends
	@Override
	public Optional<ProjectDetails> getProjectById(String id) {
		return projectRepository.findById(id);
	}

	@Override
	public void deleteProjectById(String id) {
		projectRepository.deleteById(id);
	}

	@Override
	public void deleteProject(ProjectDetails project) {
		projectRepository.delete(project);
	}

	@Override
	public ProjectDetails updateProject(ProjectDetails project) {
		return projectRepository.save(project);
	}
	
	@Override
	@Query("from ProjectDetails where applicantDetailId = :appId ")
	public ProjectDetails getProjectByapplicantDetailId(String appId) {
		return projectRepository.getProjectByapplicantDetailId(appId);
		
	}
	@Override
	@Query(" from ProjectDetails projId = :projId ")
	public ProjectDetails getProjDetById(String projId) {

		return projectRepository.getProjDetById(projId);
	}

	
}
