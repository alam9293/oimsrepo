package com.webapp.ims.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.webapp.ims.model.EvaluateExistNewProjDetails;
import com.webapp.ims.repository.EvaluateExistNewProjDetailsRepository;
import com.webapp.ims.service.EvaluateExistNewProjDetailsService;

@Service
@Transactional
public class EvaluateExistNewProjDetailsServiceImpl implements EvaluateExistNewProjDetailsService{

	@Autowired
	EvaluateExistNewProjDetailsRepository evaluateExistNewProjDetailsRepository;
	@Override
	public List<EvaluateExistNewProjDetails> getEvalExistProjListByepdProjDtlId(String id) {
		
		return evaluateExistNewProjDetailsRepository.getEvalExistProjListByepdProjDtlId(id);
	}

}
