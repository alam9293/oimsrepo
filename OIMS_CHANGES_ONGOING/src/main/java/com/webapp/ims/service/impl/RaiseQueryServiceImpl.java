package com.webapp.ims.service.impl;

import java.util.List;

import javax.mail.Multipart;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.webapp.ims.model.RaiseQuery;
import com.webapp.ims.repository.RaiseQueryRepository;
import com.webapp.ims.service.RaiseQueryService;

@Service
@Transactional
public class RaiseQueryServiceImpl implements RaiseQueryService {

	@Autowired
	RaiseQueryRepository rqRepository;

	@Override
	public List<RaiseQuery> getRaiseQueryList() {
		String status = "active";
		return rqRepository.findRaiseQueryActive(status);
	}

	@Override
	public RaiseQuery saveRaiseQuery(RaiseQuery raiseQuery) {
		return rqRepository.saveAndFlush(raiseQuery);
	}

	@Override
	public RaiseQuery getRaiseQueryById(String id) {
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
	public RaiseQuery findRaiseQueryByApplId(String applicationId) {
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
	public List<RaiseQuery> getRaiseQueryListByUserId(String userId) {
		return rqRepository.getRaiseQueryListByUserId(userId);
	}

	@Override
	public String findDepartmentByUserId(String userid) {
		return rqRepository.findDepartmentByUserId(userid);
	}

	@Override
	public void saveQuery(MultipartFile[] file, RaiseQuery raiseQuery) {
		// TODO Auto-generated method stub

	}

	@Override
	public List<RaiseQuery> searchByApplicantId(String applId) {
		String status="active";
		List<RaiseQuery> raiseQueryList =	rqRepository.searchByApplicantId(applId,status);
		return raiseQueryList;
		

	}
}
