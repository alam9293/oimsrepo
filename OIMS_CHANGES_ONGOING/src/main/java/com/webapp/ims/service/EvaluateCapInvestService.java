package com.webapp.ims.service;

import java.util.List;

import com.webapp.ims.model.EvaluateCapitalInvestment;

public interface EvaluateCapInvestService {

	public List<EvaluateCapitalInvestment> saveEvalCapitalInvList(List<EvaluateCapitalInvestment> evalCapInvList);

	public List<EvaluateCapitalInvestment> getEvalCapitalInvListByApplId(String applId);

	public EvaluateCapitalInvestment saveEvaluateCapInvest(EvaluateCapitalInvestment evalCapInv);

	public void deleteEvalCapInvById(String id);
}
