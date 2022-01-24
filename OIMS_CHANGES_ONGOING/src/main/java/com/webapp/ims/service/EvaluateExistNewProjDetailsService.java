package com.webapp.ims.service;

import java.util.List;

import com.webapp.ims.model.EvaluateExistNewProjDetails;

public interface EvaluateExistNewProjDetailsService {
	
	public List<EvaluateExistNewProjDetails> getEvalExistProjListByepdProjDtlId(String id);

}
