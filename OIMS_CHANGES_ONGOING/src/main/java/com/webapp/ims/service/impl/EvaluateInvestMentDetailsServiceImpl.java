package com.webapp.ims.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.webapp.ims.model.EvaluateInvestmentDetails;
import com.webapp.ims.repository.EvaluateInvestMentDetailsRepository;
import com.webapp.ims.service.EvaluateInvestMentDetailsService;

@Service
@Transactional
public class EvaluateInvestMentDetailsServiceImpl implements EvaluateInvestMentDetailsService{
	
	@Autowired
	EvaluateInvestMentDetailsRepository evaluateInvestMentDetailsRepository;
	@Override
	public EvaluateInvestmentDetails getEvalInvestDetByapplId(String appId) {
		
		return evaluateInvestMentDetailsRepository.getEvalInvestDetByapplId(appId);
	}
	@Override
	public EvaluateInvestmentDetails saveEvaluateInvestMentDetails(EvaluateInvestmentDetails evaluateInvestMentDetails) 
	{		
		return evaluateInvestMentDetailsRepository.save(evaluateInvestMentDetails);
	}

}
