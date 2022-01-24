package com.webapp.ims.dis.service;

import java.util.List;

import com.webapp.ims.dis.model.DisRaiseQuery;

public interface DisRaiseQueryService {

	public List<DisRaiseQuery> getRaiseQueryList();

	public List<DisRaiseQuery> getRaiseQueryListByUserId(String userId);

	public DisRaiseQuery saveRaiseQuery(DisRaiseQuery raiseQuery);

	public List<String> getApplicationIdList(String userId);

	public DisRaiseQuery findRaiseQueryByApplId(String applicationId);

	public DisRaiseQuery getRaiseQueryById(String id);

	public String findQueryIdByApplId(String applicationId);

	public void deleteRaiseQueryById(String id);

	public List<String> getQueryIdListByApplId(String applicationId);

	public String findDepartmentByUserId(String userid);

}
