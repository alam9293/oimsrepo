package com.webapp.ims.service;

import com.webapp.ims.model.EvaluateInvestmentDetails;

public interface EvaluateInvestMentDetailsService {

	public EvaluateInvestmentDetails getEvalInvestDetByapplId(String appId);
	
	public EvaluateInvestmentDetails saveEvaluateInvestMentDetails(EvaluateInvestmentDetails evaluateInvestMentDetails);
}
