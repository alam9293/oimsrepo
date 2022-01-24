package com.webapp.ims.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.webapp.ims.model.RaiseQuery;

public interface RaiseQueryService {

	public List<RaiseQuery> getRaiseQueryList();

	public List<RaiseQuery> getRaiseQueryListByUserId(String userId);

	public RaiseQuery saveRaiseQuery(RaiseQuery raiseQuery);

	public List<String> getApplicationIdList(String userId);

	public RaiseQuery findRaiseQueryByApplId(String applicationId);

	public RaiseQuery getRaiseQueryById(String id);

	public String findQueryIdByApplId(String applicationId);

	public void deleteRaiseQueryById(String id);

	public List<String> getQueryIdListByApplId(String applicationId);

	public String findDepartmentByUserId(String userid);

	public void saveQuery(MultipartFile[] file, RaiseQuery raiseQuery);

	public List<RaiseQuery> searchByApplicantId(String applId);

}
