package com.cdgtaxi.ibs.acct.business;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.cdgtaxi.ibs.common.business.GenericBusiness;
import com.cdgtaxi.ibs.common.model.AmtbApplication;

public interface ApplicationBusiness extends GenericBusiness {
	public Map<Integer, String> getAccountTypes(String template);
	public Map<String, String> getProductTypes(Integer accountTypeNo);
	public String createCorpApplication(Map<String, Object> corpDetails);
	public String createPersApplication(Map<String, Object> persDetails);
	public Map<AmtbApplication, Map<String, String>> searchApplications(String appNo, String appName, String appStatus);
	public Map<AmtbApplication, Map<String, String>> searchApplications(List<String> appStatuses);
	public Map<String, Object> getCorpApplication(String appNo);
	public Map<String, Object> getPersApplication(String appNo);
	public void saveCorpApplication(Map<String, Object> corpDetails);
	public void savePersApplication(Map<String, Object> persDetails);
	public boolean submitApplication(String appNo, String remarks, String loginId);
	public boolean recommandApplication(AmtbApplication app, String remarks, String loginId);
	public String approveApplication(AmtbApplication app, String remarks, String loginId);
	public boolean rejectApplication(AmtbApplication app, String remarks, String loginId);
	public Map<String, String> getAllProductTypes();
	public String getApplicationApprovers(String acctTemplate, Integer level);
	public boolean checkRCBNoExist(String rcbNo);
	public boolean checkCorporateNameExist(String name);
	public boolean hasExternalCardSubscription(Set<String> productTypeIds);
	public String getInvoiceFullString(String invoice, String type );
}