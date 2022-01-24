package com.webapp.ims.service;

import com.webapp.ims.model.EvaluateProjectDetails;



public interface EvaluateProjectDetailsService {

	EvaluateProjectDetails getEvalProjDetByapplicantDetailId(String appId);
	
	public EvaluateProjectDetails getProjectDetailsById (String projId);
	
	public EvaluateProjectDetails saveEvaluationProjectDetails(EvaluateProjectDetails evaluateProjectDetails);
}
