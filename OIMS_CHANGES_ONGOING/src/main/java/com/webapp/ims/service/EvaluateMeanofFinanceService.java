package com.webapp.ims.service;

import java.util.List;

import com.webapp.ims.model.EvaluateMeanOfFinance;

public interface EvaluateMeanofFinanceService {

	public List<EvaluateMeanOfFinance> saveEvalMeanofFinanceInvList(List<EvaluateMeanOfFinance> evalmofList);

	public List<EvaluateMeanOfFinance> getEvalMeanofFinanceListByApplId(String applId);

	public EvaluateMeanOfFinance saveEvaluateMeanofFinance(EvaluateMeanOfFinance evalMof);

	public void deleteEvalMofById(String id);
}
