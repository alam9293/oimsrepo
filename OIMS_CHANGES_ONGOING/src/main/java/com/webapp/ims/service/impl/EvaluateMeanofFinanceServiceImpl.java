package com.webapp.ims.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.webapp.ims.model.EvaluateMeanOfFinance;
import com.webapp.ims.repository.EvaluateMeanofFinanceRepository;
import com.webapp.ims.service.EvaluateMeanofFinanceService;

@Service
@Transactional
public class EvaluateMeanofFinanceServiceImpl implements EvaluateMeanofFinanceService {

	@Autowired
	EvaluateMeanofFinanceRepository evalMOFRepository;

	@Override
	public List<EvaluateMeanOfFinance> saveEvalMeanofFinanceInvList(List<EvaluateMeanOfFinance> evalmofList) {
		return evalMOFRepository.saveAll(evalmofList);
	}

	@Override
	public EvaluateMeanOfFinance saveEvaluateMeanofFinance(EvaluateMeanOfFinance evalMof) {
		return evalMOFRepository.save(evalMof);
	}

	@Override
	public void deleteEvalMofById(String id) {
		evalMOFRepository.deleteById(id);
	}

	@Override
	public List<EvaluateMeanOfFinance> getEvalMeanofFinanceListByApplId(String applId) {
		return evalMOFRepository.findByemfApplcId(applId);
	}

}
