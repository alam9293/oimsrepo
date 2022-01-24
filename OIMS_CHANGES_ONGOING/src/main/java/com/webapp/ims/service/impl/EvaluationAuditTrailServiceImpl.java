package com.webapp.ims.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.webapp.ims.model.EvaluationAuditTrail;
import com.webapp.ims.repository.EvaluateAuditTrialRepository;
import com.webapp.ims.service.EvaluationAuditTrailService;

@Service
@Transactional
public class EvaluationAuditTrailServiceImpl implements EvaluationAuditTrailService{

	@Autowired
	EvaluateAuditTrialRepository evaluateAuditTrialRepository;
	@Override
	public EvaluationAuditTrail saveEvaluationAuditTrail(EvaluationAuditTrail evaluationAuditTrail) {
		return evaluateAuditTrialRepository.save(evaluationAuditTrail);
	}

}
