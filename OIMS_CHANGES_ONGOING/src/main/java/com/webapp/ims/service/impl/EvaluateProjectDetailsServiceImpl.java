package com.webapp.ims.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.webapp.ims.model.EvaluateProjectDetails;
import com.webapp.ims.repository.EvaluateProjectDetailsRepository;
import com.webapp.ims.service.EvaluateProjectDetailsService;

@Service
@Transactional
public class EvaluateProjectDetailsServiceImpl implements EvaluateProjectDetailsService {

	@Autowired
	EvaluateProjectDetailsRepository evaluateProjectDetailsRepository;
	
	public EvaluateProjectDetails getEvalProjDetByapplicantDetailId(String appId) {
		
		return evaluateProjectDetailsRepository.getEvalProjDetByapplicantDetailId(appId);
	}

	
	@Override
	@Query(" from ProjectDetails projId = :projId ")
	public EvaluateProjectDetails getProjectDetailsById(String projId) {
		
		return evaluateProjectDetailsRepository.getProjectDetailsById(projId);
	}


	public EvaluateProjectDetails saveEvaluationProjectDetails(EvaluateProjectDetails evaluateProjectDetails) {
		
		return evaluateProjectDetailsRepository.save(evaluateProjectDetails);
	}

}
