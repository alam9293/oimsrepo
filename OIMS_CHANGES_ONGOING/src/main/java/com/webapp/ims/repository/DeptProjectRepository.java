package com.webapp.ims.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.webapp.ims.model.DeptProjectDetails;
import com.webapp.ims.model.ProjectDetails;

public interface DeptProjectRepository extends JpaRepository<DeptProjectDetails, String>  {
	ProjectDetails getProjectByapplicantDetailId(String appId);
	public ProjectDetails getProjDetById(String projId);
	
	public DeptProjectDetails findByApplicantDetailId(String applicantId);
	

}
