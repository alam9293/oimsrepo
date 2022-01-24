package com.webapp.ims.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import com.webapp.ims.model.EvaluateProjectDetails;

@Repository
@Transactional
public interface EvaluateProjectDetailsRepository extends JpaRepository<EvaluateProjectDetails, String> {

	EvaluateProjectDetails getEvalProjDetByapplicantDetailId(String appId);
	
	public EvaluateProjectDetails getProjectDetailsById (String projId);
}
