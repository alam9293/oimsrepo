package com.webapp.ims.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.webapp.ims.model.ProjectDetails;

@Repository
@Transactional
public interface ProjectRepository extends JpaRepository<ProjectDetails, String> 
{

	ProjectDetails getProjectByapplicantDetailId(String appId);
	public ProjectDetails getProjDetById(String projId);
	
	@Query(" from ProjectDetails where applicantDetailId = :applicantDetailId ")
	public List<ProjectDetails> getProjByApplicantDetailId(String applicantDetailId);
	

}
