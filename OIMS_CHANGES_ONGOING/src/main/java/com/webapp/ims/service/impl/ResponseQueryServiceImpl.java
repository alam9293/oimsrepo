package com.webapp.ims.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.webapp.ims.model.RaiseQuery;
import com.webapp.ims.model.ResponseQuery;
import com.webapp.ims.repository.ResponseQueryRepository;
import com.webapp.ims.service.ResponseQueryService;

@Service
@Transactional
public class ResponseQueryServiceImpl implements ResponseQueryService {

	@Autowired
	ResponseQueryRepository respRepository;

	@Override
	public List<ResponseQuery> getResponseQueryList() {
		return respRepository.findAll();
	}

	@Override
	public ResponseQuery saveResponseQuery(ResponseQuery respQuery) {
		return respRepository.saveAndFlush(respQuery);
	}

	@Override
	public ResponseQuery findResponseQueryByApplId(String applicationId) {
		return respRepository.findResponseQueryByApplicationId(applicationId);
	}

	@Override
	public ResponseQuery getResponseQueryById(String id) {
		return respRepository.findResponseQueryById(id);
	}

	@Override
	public void deleteResponseQueryById(ResponseQuery rq) {
		respRepository.delete(rq);
	}

	@Override
	public List<String> getApplicationIdList(String userId) {
		return respRepository.getApplicanttionIdList(userId);
	}

	@Override
	public ResponseQuery findResponseQueryById(String responseId) {
		return respRepository.findResponseQueryById(responseId);
	}

	@Override
	public List<ResponseQuery> getResponseQueryListByUserId(String userId) {
		return respRepository.getResponseQueryListByUserId(userId);
	}

	@Override
	public List<RaiseQuery> findRaisedQueryListByQueryId(String respRqid) {
		return respRepository.findRaisedQueryListByQueryId(respRqid);
	}

	
}
