package com.webapp.ims.dis.service;

import java.util.List;

import com.webapp.ims.dis.model.DisEvaluationReimbrsDepositeGstTable;

public interface DisEvaluationReimbrsGStTableService {

	public List<DisEvaluationReimbrsDepositeGstTable> saveReimbrsGSTTableList(
			List<DisEvaluationReimbrsDepositeGstTable> reimbrsGStTableList);

	public List<DisEvaluationReimbrsDepositeGstTable> getListBydisAppId(String applId);

}
