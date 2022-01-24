package com.webapp.ims.dis.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.webapp.ims.dis.model.DisRaiseQuery;
import com.webapp.ims.dis.repository.DisRaiseQueryRepository;
import com.webapp.ims.dis.service.DisRaiseQueryService;

@Service
@Transactional
public class DisRaiseQueryServiceImpl implements DisRaiseQueryService {

	@Autowired
	DisRaiseQueryRepository rqRepository;

	@Override
	public List<DisRaiseQuery> getRaiseQueryList() {
		return rqRepository.findAll();
	}

	@Override
	public DisRaiseQuery saveRaiseQuery(DisRaiseQuery raiseQuery) {
		return rqRepository.saveAndFlush(raiseQuery);
	}

	@Override
	public DisRaiseQuery getRaiseQueryById(String id) {
		return rqRepository.findRaiseQueryById(id);
	}

	@Override
	public void deleteRaiseQueryById(String id) {
		rqRepository.deleteById(id);

	}

	@Override
	public List<String> getApplicationIdList(String userId) {
		return rqRepository.getApplicationIdList(userId);
	}

	@Override
	public DisRaiseQuery findRaiseQueryByApplId(String applicationId) {
		return rqRepository.findRaiseQueryByApplicationId(applicationId);
	}

	@Override
	public String findQueryIdByApplId(String applicationId) {
		return rqRepository.findQueryIdByApplId(applicationId);
	}

	@Override
	public List<String> getQueryIdListByApplId(String applicationId) {
		return rqRepository.getQueryIdListByApplId(applicationId);
	}

	@Override
	public List<DisRaiseQuery> getRaiseQueryListByUserId(String userId) {
		return rqRepository.getRaiseQueryListByUserId(userId);
	}

	@Override
	public String findDepartmentByUserId(String userid) {
		return rqRepository.findDepartmentByUserId(userid);
	}

}
