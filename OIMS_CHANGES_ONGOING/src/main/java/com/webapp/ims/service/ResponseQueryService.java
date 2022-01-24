package com.webapp.ims.service;

import java.util.List;

import com.webapp.ims.model.RaiseQuery;
import com.webapp.ims.model.ResponseQuery;

public interface ResponseQueryService {

	public List<ResponseQuery> getResponseQueryList();

	public ResponseQuery saveResponseQuery(ResponseQuery respQuery);

	public ResponseQuery findResponseQueryByApplId(String applicationId);

	public ResponseQuery getResponseQueryById(String id);

	public List<String> getApplicationIdList(String userId);

	public ResponseQuery findResponseQueryById(String responseId);

	public List<ResponseQuery> getResponseQueryListByUserId(String userId);

	public void deleteResponseQueryById(ResponseQuery rq);

	public List<RaiseQuery> findRaisedQueryListByQueryId(String respRqid);
	
	

}
