package com.webapp.ims.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.webapp.ims.model.EvaluateCapitalInvestment;
import com.webapp.ims.repository.EvaluateCapInvestRepository;
import com.webapp.ims.service.EvaluateCapInvestService;

@Service
@Transactional
public class EvaluateCapInvestServiceImpl implements EvaluateCapInvestService {

	@Autowired
	EvaluateCapInvestRepository evalCapInvRepository;

	@Override
	public List<EvaluateCapitalInvestment> saveEvalCapitalInvList(List<EvaluateCapitalInvestment> evalCapInvList) {
		return evalCapInvRepository.saveAll(evalCapInvList);
	}

	@Override
	public List<EvaluateCapitalInvestment> getEvalCapitalInvListByApplId(String applId) {
		return evalCapInvRepository.findByEciApplcId(applId);
	}

	@Override
	public EvaluateCapitalInvestment saveEvaluateCapInvest(EvaluateCapitalInvestment evalCapInv) {
		return evalCapInvRepository.save(evalCapInv);
	}

	@Override
	public void deleteEvalCapInvById(String id) {
		evalCapInvRepository.deleteById(id);		
	}
	
	
}
