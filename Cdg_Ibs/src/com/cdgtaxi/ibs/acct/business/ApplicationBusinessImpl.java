package com.cdgtaxi.ibs.acct.business;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

import org.apache.log4j.Logger;
import org.springframework.dao.ConcurrencyFailureException;

import com.cdgtaxi.ibs.Uri;
import com.cdgtaxi.ibs.acl.model.SatbUser;
import com.cdgtaxi.ibs.common.ConfigurableConstants;
import com.cdgtaxi.ibs.common.MasterSetup;
import com.cdgtaxi.ibs.common.NonConfigurableConstants;
import com.cdgtaxi.ibs.common.business.GenericBusinessImpl;
import com.cdgtaxi.ibs.common.dao.Sequence;
import com.cdgtaxi.ibs.common.model.AmtbAccount;
import com.cdgtaxi.ibs.common.model.AmtbAcctAdminFee;
import com.cdgtaxi.ibs.common.model.AmtbAcctCredLimit;
import com.cdgtaxi.ibs.common.model.AmtbAcctEarlyPymt;
import com.cdgtaxi.ibs.common.model.AmtbAcctLatePymt;
import com.cdgtaxi.ibs.common.model.AmtbAcctMainContact;
import com.cdgtaxi.ibs.common.model.AmtbAcctMainContactPK;
import com.cdgtaxi.ibs.common.model.AmtbAcctSalesperson;
import com.cdgtaxi.ibs.common.model.AmtbAcctStatus;
import com.cdgtaxi.ibs.common.model.AmtbAcctType;
import com.cdgtaxi.ibs.common.model.AmtbAcctVolDisc;
import com.cdgtaxi.ibs.common.model.AmtbApplication;
import com.cdgtaxi.ibs.common.model.AmtbApplicationFlow;
import com.cdgtaxi.ibs.common.model.AmtbApplicationProduct;
import com.cdgtaxi.ibs.common.model.AmtbContactPerson;
import com.cdgtaxi.ibs.common.model.AmtbCorporateDetail;
import com.cdgtaxi.ibs.common.model.AmtbPersonalDetail;
import com.cdgtaxi.ibs.common.model.AmtbSubscTo;
import com.cdgtaxi.ibs.common.model.AmtbSubscToPK;
import com.cdgtaxi.ibs.common.model.FmtbArContCodeMaster;
import com.cdgtaxi.ibs.common.model.PmtbProductType;
import com.cdgtaxi.ibs.common.ui.CommonWindow;
import com.cdgtaxi.ibs.master.model.LrtbRewardMaster;
import com.cdgtaxi.ibs.master.model.MstbAdminFeeMaster;
import com.cdgtaxi.ibs.master.model.MstbEarlyPaymentMaster;
import com.cdgtaxi.ibs.master.model.MstbIssuanceFeeMaster;
import com.cdgtaxi.ibs.master.model.MstbLatePaymentMaster;
import com.cdgtaxi.ibs.master.model.MstbProdDiscMaster;
import com.cdgtaxi.ibs.master.model.MstbSalesperson;
import com.cdgtaxi.ibs.master.model.MstbSubscFeeMaster;
import com.cdgtaxi.ibs.master.model.MstbVolDiscMaster;
import com.cdgtaxi.ibs.util.DateUtil;
import com.cdgtaxi.ibs.util.EmailUtil;
import com.google.common.collect.Iterables;

public class ApplicationBusinessImpl extends GenericBusinessImpl implements ApplicationBusiness {
	private static final Logger logger = Logger.getLogger(ApplicationBusinessImpl.class);
	public Map<Integer, String> getAccountTypes(String template){
		logger.info("getAccountTypes(String template)");
		Map<Integer, String> acctTypes = new TreeMap<Integer, String>(new Comparator<Integer>(){
			public int compare(Integer acctTypeNo1, Integer acctTypeNo2){
				return acctTypeNo1.compareTo(acctTypeNo2);
			}
		});
		List<AmtbAcctType> accountTypes = this.daoHelper.getAccountTypeDao().getAccountTypes(template);
		for(AmtbAcctType accountType : accountTypes){
			acctTypes.put(accountType.getAcctTypeNo(), accountType.getAcctType());
		}
		return acctTypes;
	}
	/**
	 * method to get all product types that is subscribeable by the account type
	 * @param accountTypeNo - the account type id
	 * @return the set containing all product types. Null if not account type found
	 */
	public Map<String, String> getProductTypes(Integer accountTypeNo){
		logger.info("getProductTypes(Integer accountTypeNo)");
		Map<String, String> prodTypes = new TreeMap<String, String>(new Comparator<String>(){
			public int compare(String prodTypeId1, String prodTypeId2){
				return prodTypeId1.compareTo(prodTypeId2);
			}
		});
		List<AmtbAcctType> accountType = this.daoHelper.getAccountTypeDao().getAccountType(accountTypeNo);
		if(accountType.size()!=0){
			Set<PmtbProductType> productTypes = accountType.get(0).getPmtbProductTypes();
			for(PmtbProductType productType : productTypes){
				prodTypes.put(productType.getProductTypeId(), productType.getName());
			}
		}
		return prodTypes;
	}
	/**
	 * method to create new application.
	 */
	public String createCorpApplication(Map<String, Object> corpDetails){
		logger.info("createApplication(Map<String, Object> applicationDetails)");
		AmtbApplication newCorpApp = createCorpAppObject(corpDetails);
		if(this.daoHelper.getApplicationDao().createApplication(newCorpApp)){
			return newCorpApp.getApplicationNo();
		}else{
			return null;
		}
	}
	/**
	 * method to create new application.
	 */
	public String createPersApplication(Map<String, Object> persDetails){
		logger.info("createApplication(Map<String, Object> applicationDetails)");
		AmtbApplication newPersApp = createPersAppObject(persDetails);
		if(this.daoHelper.getApplicationDao().createApplication(newPersApp)){
			return newPersApp.getApplicationNo();
		}else{
			return null;
		}
	}
	public Map<AmtbApplication, Map<String, String>> searchApplications(String appNo, String appName, String appStatus){
		logger.info("searchApplication(Integer appNo, String appName, String appStatus)");
		List<AmtbApplication> applications = this.daoHelper.getApplicationDao().searchApplication(appNo, appName, appStatus);
		Map<AmtbApplication, Map<String, String>> returnMap = new TreeMap<AmtbApplication, Map<String, String>>(new Comparator<AmtbApplication>(){
			public int compare(AmtbApplication app1, AmtbApplication app2) {
				String o1 = app1.getApplicationNo();
				String o2 = app2.getApplicationNo();
				if(o1.matches("([0-9]*)") && o2.matches("([0-9]*)")){
					return new Integer(Integer.parseInt(o1)).compareTo(Integer.parseInt(o2));
				}else{
					return 0;
				}
			}
		});
		for(AmtbApplication application : applications){
			Map<String, String> applicationDetails = new LinkedHashMap<String, String>();
			applicationDetails.put("appName", application.getApplicantName());
			applicationDetails.put("acctTemplate", application.getAmtbAcctType().getAcctTemplate());
			Timestamp appDate = application.getApplicationDt();
			applicationDetails.put("appDate", appDate==null ? "-" : DateUtil.convertTimestampToStr(appDate, DateUtil.GLOBAL_DATE_FORMAT));
			Set<AmtbApplicationFlow> applicationFlows = application.getAmtbApplicationFlows();
			AmtbApplicationFlow latestFlow = null;
			for(AmtbApplicationFlow applicationFlow : applicationFlows){
				if(latestFlow==null){
					latestFlow = applicationFlow;
				}else{
					if(latestFlow.getFlowDt().before(applicationFlow.getFlowDt())){
						latestFlow = applicationFlow;
					}
				}
			}
			applicationDetails.put("appStatus", latestFlow.getToStatus());
			returnMap.put(application, applicationDetails);
		}
		return returnMap;
	}
	public Map<AmtbApplication, Map<String, String>> searchApplications(List<String> appStatuses){
		logger.info("searchApplications(List<String> appStatuses)");
		
		List<Object[]> applicationsObjects = this.daoHelper.getApplicationDao().searchApplicationObject(appStatuses);
		List<String> applicationNoList = new ArrayList<String>();
		List<AmtbApplication> applications = new ArrayList<AmtbApplication>();
		
		if(applicationsObjects.size() != 0 )
		{
				for(Object[] appObj : applicationsObjects)
					applicationNoList.add(appObj[0].toString());
				
				Iterable<List<String>> applicationNoListChunks = Iterables.partition(applicationNoList, 1000);
				for(List<String> chunk: applicationNoListChunks){
					List<AmtbApplication> applicationsChunk = this.daoHelper.getApplicationDao().searchApplicationChunk(chunk);
					
					applications.addAll(applicationsChunk);
				}
		}
		
//		 getting applications of the status in the list from dao layer.
//		List<AmtbApplication> applications = this.daoHelper.getApplicationDao().searchApplication(appStatuses);
//		// creating new map to return to view layer
		Map<AmtbApplication, Map<String, String>> returnMap = new TreeMap<AmtbApplication, Map<String, String>>(new Comparator<AmtbApplication>(){
			public int compare(AmtbApplication app1, AmtbApplication app2) {
				String o1 = app1.getApplicationNo();
				String o2 = app2.getApplicationNo();
				if(o1.matches("([0-9]*)") && o2.matches("([0-9]*)")){
					return new Integer(Integer.parseInt(o1)).compareTo(Integer.parseInt(o2));
				}else{
					return 0;
				}
			}
		});
		// for each result
		for(AmtbApplication application : applications){
			// creating new map to hold each detail of the application
			Map<String, String> applicationDetails = new LinkedHashMap<String, String>();
			// adding applicant name into map
			applicationDetails.put("appName", application.getApplicantName());
			// adding account template to map
			applicationDetails.put("acctTemplate", application.getAmtbAcctType().getAcctTemplate());
			// adding application date into map
			Timestamp appDate = application.getApplicationDt();
			applicationDetails.put("appDate", appDate==null ? "-" : DateUtil.convertTimestampToStr(appDate, DateUtil.GLOBAL_DATE_FORMAT));
			//now adding application last status into the map
			Set<AmtbApplicationFlow> applicationFlows = application.getAmtbApplicationFlows();
			AmtbApplicationFlow latestFlow = null;
			for(AmtbApplicationFlow applicationFlow : applicationFlows){
				if(latestFlow==null){
					latestFlow = applicationFlow;
				}else{
					if(latestFlow.getFlowDt().before(applicationFlow.getFlowDt())){
						latestFlow = applicationFlow;
					}
				}
			}
			applicationDetails.put("appStatus", latestFlow.getToStatus());
			// adding the detail map into the return map
			returnMap.put(application, applicationDetails);
		}
		return returnMap;
	}
	public Map<String, Object> getCorpApplication(String appNo){
		logger.info("getCorpApplication(String appNo)");
		AmtbApplication application = this.daoHelper.getApplicationDao().getApplication(appNo);
		if(application != null){
			Map<String, Object> returnMap = new LinkedHashMap<String, Object>();
			// get common details
			getCommonDetails(returnMap, application);
			// get specific details
			returnMap.put("rcbNo", application.getRcbNo());
			returnMap.put("rcbDate", application.getRcbDt()== null ? null : application.getRcbDt());
			returnMap.put("capital", application.getCapital());
			returnMap.put("fax", application.getFax());
			returnMap.put("authSalCode", application.getMstbMasterTableByAuthSal()==null ? null : application.getMstbMasterTableByAuthSal().getMasterCode());
			returnMap.put("authPerson", application.getAuthPerson());
			returnMap.put("authTitle", application.getAuthTitle());
			returnMap.put("projectCode", application.getProjectCode());

			if(application.getInvoiceFormat() != null)
				returnMap.put("invoiceFormat", application.getInvoiceFormat());
			if(application.getInvoiceSorting() != null)
				returnMap.put("invoiceSorting", application.getInvoiceSorting());
			if(application.getGovtEInvoiceFlag() != null)
				returnMap.put("gvtEInvoiceFlag", application.getGovtEInvoiceFlag());
			if(application.getBusinessUnitMasterNo() != null)
				returnMap.put("businessUnit", Integer.toString(application.getBusinessUnitMasterNo()));
			if(application.getPubbsFlag() != null)
				returnMap.put("pubbsFlag", application.getPubbsFlag());
			if(application.getFiFlag() != null)
				returnMap.put("fiFlag", application.getFiFlag());
			returnMap.put("aceIndicator", application.getAceIndicator());
			returnMap.put("coupaIndicator", application.getCoupaIndicator());
			
			return returnMap;
		}else{
			return null;
		}
	}
	public Map<String, Object> getPersApplication(String appNo){
		logger.info("getPersApplication(String appNo)");
		AmtbApplication application = this.daoHelper.getApplicationDao().getApplication(appNo);
		if(application != null){
			Map<String, Object> returnMap = new LinkedHashMap<String, Object>();
			// get common details
			getCommonDetails(returnMap, application);
			// get specific details
			returnMap.put("salutationCode", application.getMstbMasterTableByAuthSal()==null ? null : application.getMstbMasterTableByAuthSal().getMasterCode());
			returnMap.put("nric", application.getNric());
			returnMap.put("birthdate", application.getRcbDt()== null ? null : application.getRcbDt());
			returnMap.put("email", application.getEmail());
			returnMap.put("mobile", application.getMobile());
			returnMap.put("office", application.getOffice());
			returnMap.put("mainContactRace", application.getMstbMasterTableByMainContactRace() == null ? null : application.getMstbMasterTableByMainContactRace().getMasterCode());
			returnMap.put("mainContactRaceValue", application.getMstbMasterTableByMainContactRace() == null ? null : application.getMstbMasterTableByMainContactRace().getMasterValue());
			// billing address
			returnMap.put("billAdd", application.getBillingSame());
			returnMap.put("billBlkNo", application.getBillingBlock());
			returnMap.put("billUnitNo", application.getBillingUnit());
			returnMap.put("billStreet", application.getBillingStreet());
			returnMap.put("billBuilding", application.getBillingBuilding());
			returnMap.put("billArea", application.getBillingArea());
			returnMap.put("billCountryListCode", application.getMstbMasterTableByBillingCountry() == null ? null : application.getMstbMasterTableByBillingCountry().getMasterCode());
			returnMap.put("billCity", application.getBillingCity());
			returnMap.put("billState", application.getBillingState());
			returnMap.put("billPostal", application.getBillingPostal());
			// shipping address
			returnMap.put("shipAdd", application.getShippingSame());
			returnMap.put("shipBlkNo", application.getShippingBlock());
			returnMap.put("shipUnitNo", application.getShippingUnit());
			returnMap.put("shipStreet", application.getShippingStreet());
			returnMap.put("shipBuilding", application.getShippingBuilding());
			returnMap.put("shipArea", application.getShippingArea());
			returnMap.put("shipCountryListCode", application.getMstbMasterTableByShippingCountry()==null ? null : application.getMstbMasterTableByShippingCountry().getMasterCode());
			returnMap.put("shipCity", application.getShippingCity());
			returnMap.put("shipState", application.getShippingState());
			returnMap.put("shipPostal", application.getShippingPostal());
			//employment details
			returnMap.put("jobStatusListCode", application.getMstbMasterTableByJobStatus()==null ? null : application.getMstbMasterTableByJobStatus().getMasterCode());
			returnMap.put("occupation", application.getOccupation());
			returnMap.put("employerName", application.getEmployerName());
			// employee address
			returnMap.put("empBlkNo", application.getEmployerBlock());
			returnMap.put("empUnitNo", application.getEmployerUnit());
			returnMap.put("empStreet", application.getEmployerStreet());
			returnMap.put("empBuilding", application.getEmployerBuilding());
			returnMap.put("empArea", application.getEmployerArea());
			returnMap.put("empCountryListCode", application.getMstbMasterTableByEmployerCountry()==null ? null : application.getMstbMasterTableByEmployerCountry().getMasterCode());
			returnMap.put("empCity", application.getEmployerCity());
			returnMap.put("empState", application.getEmployerState());
			returnMap.put("empPostal", application.getEmployerPostal());
			returnMap.put("monthlyIncome", application.getMonthlyIncome());
			returnMap.put("empLength", application.getEmployYears());
			
			return returnMap;
		}else{
			return null;
		}
	}
	private void getCommonDetails(Map<String, Object> appDetails, AmtbApplication application){
		appDetails.put("appNo", application.getApplicationNo());
		appDetails.put("appDate", application.getApplicationDt()==null ? null : application.getApplicationDt());
		TreeSet<AmtbApplicationFlow> sortedStatuses = new TreeSet<AmtbApplicationFlow>(new Comparator<AmtbApplicationFlow>(){
			public int compare(AmtbApplicationFlow af1, AmtbApplicationFlow af2) {
				return af1.getFlowDt().compareTo(af2.getFlowDt());
			}
		});
		sortedStatuses.addAll(application.getAmtbApplicationFlows());
		appDetails.put("appStatus", sortedStatuses.last().getToStatus());
		for(AmtbApplicationFlow flow : sortedStatuses){
			// from new to draft = application remarks
			if(flow.getFromStatus().equals(NonConfigurableConstants.APPLICATION_STATUS_NEW)){
				appDetails.put("remarks", flow.getRemarks());
			// from draft to any = application remarks
			}else if(flow.getFromStatus().equals(NonConfigurableConstants.APPLICATION_STATUS_DRAFT)){
				appDetails.put("remarks", flow.getRemarks());
				appDetails.put("requester", flow.getSatbUser().getName());
				appDetails.put("reqDate", DateUtil.convertTimestampToStr(flow.getFlowDt(), DateUtil.GLOBAL_DATE_FORMAT));
				appDetails.put("reqTime", DateUtil.convertTimestampToStr(flow.getFlowDt(), DateUtil.GLOBAL_TIME_FORMAT));
			// from 1st level to any status = 1st level approver remarks
			}else if(flow.getFromStatus().equals(NonConfigurableConstants.APPLICATION_STATUS_PENDING_1ST_LEVEL_APPROVAL)){
				appDetails.put("approver1Remarks", flow.getRemarks());
				appDetails.put("approver1", flow.getSatbUser().getName());
				appDetails.put("hasLvl1", NonConfigurableConstants.BOOLEAN_YES);
			// from 2nd level to any statys = 2nd level approver remarks
			}else if(flow.getFromStatus().equals(NonConfigurableConstants.APPLICATION_STATUS_PENDING_2ND_LEVEL_APPROVAL)){
				appDetails.put("approver2Remarks", flow.getRemarks());
				appDetails.put("approver2", flow.getSatbUser().getName());
				appDetails.put("hasLvl2", NonConfigurableConstants.BOOLEAN_YES);
			}
		}
		appDetails.put("custNo", application.getAmtbAccount()==null ? "-" : application.getAmtbAccount().getCustNo());
		appDetails.put("acctTypeListNo", application.getAmtbAcctType()==null ? null : application.getAmtbAcctType().getAcctTypeNo());
		appDetails.put("acctName", application.getApplicantName());
		appDetails.put("nameOnCard", application.getNameOnCard());
		appDetails.put("blkNo", application.getAddressBlock());
		appDetails.put("unitNo", application.getAddressUnit());
		appDetails.put("street", application.getAddressStreet());
		appDetails.put("building", application.getAddressBuilding());
		appDetails.put("area", application.getAddressArea());
		appDetails.put("countryListCode", application.getMstbMasterTableByAddressCountry()==null ? null : application.getMstbMasterTableByAddressCountry().getMasterCode());
		appDetails.put("city", application.getAddressCity());
		appDetails.put("state", application.getAddressState());
		appDetails.put("postal", application.getAddressPostal());
		appDetails.put("tel", application.getTel());
		appDetails.put("industryListCode", application.getMstbMasterTableByIndustryNo()==null ? null : application.getMstbMasterTableByIndustryNo().getMasterCode());
		appDetails.put("infoSourceCode", application.getMstbMasterTableByInformationSource()==null ? null : application.getMstbMasterTableByInformationSource().getMasterCode());
		appDetails.put("eInvoice", application.getEinvoiceFlag());
		appDetails.put("invoicePrinting", application.getInvoicePrinting());
		appDetails.put("sms", application.getSmsFlag());
		appDetails.put("smsExpiry", application.getSmsExpiryFlag());
		appDetails.put("smsTopUp", application.getSmsTopupFlag());
		appDetails.put("outsourcePrinting", application.getOutsourcePrintingFlag());
		appDetails.put("printTaxInvOnly", application.getPrintTaxInvoiceOnly());
		appDetails.put("recurring", application.getRecurringFlag());
		getAssessmentDetails(appDetails, application);
		getProductSubscriptions(appDetails, application);
	}
	private void getAssessmentDetails(Map<String, Object> appDetails, AmtbApplication application){
		// assessment
		appDetails.put("arControlCodeNo", application.getFmtbArContCodeMaster().getArControlCodeNo());
		appDetails.put("creditLimit", application.getCreditLimit());
		appDetails.put("deposit", application.getDeposit());
		if(application.getMstbSalesperson()!=null){
			appDetails.put("salesPersonNo", application.getMstbSalesperson().getSalespersonNo());
		}
		appDetails.put("volumeDiscountNo", application.getMstbVolDiscMaster()==null ? null : application.getMstbVolDiscMaster().getVolumeDiscountPlanNo());
		appDetails.put("adminFeeNo", application.getMstbAdminFeeMaster().getAdminFeePlanNo());
		appDetails.put("earlyPaymentNo", application.getMstbEarlyPaymentMaster()==null ? null : application.getMstbEarlyPaymentMaster().getEarlyPaymentPlanNo());
		appDetails.put("latePaymentNo", application.getMstbLatePaymentMaster().getLatePaymentPlanNo());
	}
	private void getProductSubscriptions(Map<String, Object> appDetails, AmtbApplication application){
		// now parsing product type
		Set<AmtbApplicationProduct> subscriptions = application.getAmtbApplicationProducts();
		Set<Map<String, Object>> prodSubscriptions = new TreeSet<Map<String, Object>>(new Comparator<Map<String, Object>>(){
			public int compare(Map<String, Object> map1, Map<String, Object> map2) {
				return ((Integer)map1.get("applicationProductNo")).compareTo((Integer)map2.get("applicationProductNo"));
			}
		});
		for(AmtbApplicationProduct subscription : subscriptions){
			Map<String, Object> prodSubscription = new LinkedHashMap<String, Object>();
			prodSubscription.put("productTypeId", subscription.getPmtbProductType().getProductTypeId());
			prodSubscription.put("productDiscountPlanMasterNo", subscription.getMstbProdDiscMaster()==null ? null : subscription.getMstbProdDiscMaster().getProductDiscountPlanNo());
			prodSubscription.put("rewardsPlanMasterNo", subscription.getLrtbRewardMaster()==null ? null : subscription.getLrtbRewardMaster().getRewardPlanNo());
			prodSubscription.put("subscriptionFeeMasterNo", subscription.getMstbSubscFeeMaster()==null ? null : subscription.getMstbSubscFeeMaster().getSubscriptionFeeNo());
			prodSubscription.put("issuanceFeeMasterNo", subscription.getMstbIssuanceFeeMaster()==null ? null : subscription.getMstbIssuanceFeeMaster().getIssuanceFeeNo());
			prodSubscription.put("applicationProductNo", subscription.getApplicationProductNo());
			prodSubscriptions.add(prodSubscription);
		}
		appDetails.put("prodSubscriptions", prodSubscriptions);
	}
	public void saveCorpApplication(Map<String, Object> corpDetails){
		logger.info("saveCorpApplication(Map<String, Object> corpDetails)");
		this.daoHelper.getApplicationDao().saveApplication(createCorpAppObject(corpDetails));
	}
	@SuppressWarnings("unchecked")
	private AmtbApplication createCorpAppObject(Map<String, Object> corpDetails){
		logger.info("createCorpAppObject(Map<String, Object> corpDetails)");
		AmtbApplication app;
		if(corpDetails.get("appNo")!=null){
			app = this.daoHelper.getApplicationDao().getApplication((String)corpDetails.get("appNo"));
		}else{
			app = new AmtbApplication();
		}
		app.setAmtbAcctType(this.daoHelper.getAccountTypeDao().getAccountType((Integer)corpDetails.get("accountTypeNo")).get(0));
		app.setApplicantName((String)corpDetails.get("acctName"));
		app.setNameOnCard((String)corpDetails.get("nameOnCard"));
		app.setRcbNo((String)corpDetails.get("rcbNo"));
		app.setMstbMasterTableByIndustryNo(ConfigurableConstants.getMasterTable(ConfigurableConstants.INDUSTRY_MASTER_CODE, (String)corpDetails.get("industryListCode")));
		app.setRcbDt(DateUtil.convertDateToTimestamp((Date)corpDetails.get("rcbDate")));
		app.setCapital((BigDecimal)corpDetails.get("capital"));
		app.setTel((String)corpDetails.get("tel"));
		app.setFax((String)corpDetails.get("fax"));
		app.setAddressBlock((String)corpDetails.get("blkNo"));
		app.setAddressUnit((String)corpDetails.get("unitNo"));
		app.setAddressStreet((String)corpDetails.get("street"));
		app.setAddressBuilding((String)corpDetails.get("building"));
		app.setAddressArea((String)corpDetails.get("area"));
		app.setMstbMasterTableByAddressCountry(ConfigurableConstants.getMasterTable(ConfigurableConstants.COUNTRY_MASTER_CODE, (String)corpDetails.get("countryListCode")));
		app.setAddressCity((String)corpDetails.get("city"));
		app.setAddressState((String)corpDetails.get("state"));
		app.setAddressPostal((String)corpDetails.get("postal"));
		app.setMstbMasterTableByAuthSal(ConfigurableConstants.getMasterTable(ConfigurableConstants.SALUTATION_MASTER_CODE, (String)corpDetails.get("salutationCode")));
		app.setAuthPerson((String)corpDetails.get("authPerson"));
		app.setAuthTitle((String)corpDetails.get("authTitle"));
		app.setMstbMasterTableByInformationSource(ConfigurableConstants.getMasterTable(ConfigurableConstants.INFORMATION_SOURCE_MASTER_CODE, (String)corpDetails.get("infoSourceCode")));
		app.setFmtbArContCodeMaster((FmtbArContCodeMaster) MasterSetup.getEntityManager().getDetail((Integer)corpDetails.get("arControlCodeNo")));
		app.setCreditLimit((BigDecimal)corpDetails.get("creditLimit"));
		app.setDeposit((BigDecimal)corpDetails.get("deposit"));
		app.setProjectCode((String)corpDetails.get("projectCode"));
		app.setEinvoiceFlag((String)corpDetails.get("eInvoice"));
		app.setInvoicePrinting((String)corpDetails.get("invoicePrinting"));
		app.setOutsourcePrintingFlag((String)corpDetails.get("outsourcePrinting"));
		app.setSmsFlag((String)corpDetails.get("sms"));
		app.setSmsExpiryFlag((String)corpDetails.get("smsExpiry"));
		app.setSmsTopupFlag((String)corpDetails.get("smsTopUp"));
		app.setMstbSalesperson((MstbSalesperson)MasterSetup.getSalespersonManager().getMaster((Integer)corpDetails.get("salespersonNo")));
		app.setMstbVolDiscMaster((MstbVolDiscMaster)MasterSetup.getVolumeDiscountManager().getMaster((Integer)corpDetails.get("volumeDiscountNo")));
		app.setMstbAdminFeeMaster((MstbAdminFeeMaster)MasterSetup.getAdminFeeManager().getMaster((Integer)corpDetails.get("adminFeesNo")));
		app.setMstbEarlyPaymentMaster((MstbEarlyPaymentMaster)MasterSetup.getEarlyPaymentManager().getMaster((Integer)corpDetails.get("earlyDiscountNo")));
		app.setMstbLatePaymentMaster((MstbLatePaymentMaster)MasterSetup.getLatePaymentManager().getMaster((Integer)corpDetails.get("lateInterestNo")));

		app.setInvoiceFormat((String)corpDetails.get("invoiceFormat"));
		app.setInvoiceSorting((String)corpDetails.get("invoiceSorting"));
		app.setGovtEInvoiceFlag((String)corpDetails.get("govtEInvFlag"));
		app.setPrintTaxInvoiceOnly((String)corpDetails.get("printTaxInvoiceOnly"));
		app.setAceIndicator((String)corpDetails.get("aceIndicator"));
		app.setCoupaIndicator((String)corpDetails.get("coupaIndicator"));
		app.setPubbsFlag((String)corpDetails.get("pubbsFlag"));
		app.setFiFlag((String)corpDetails.get("fiFlag"));
		app.setRecurringFlag((String)corpDetails.get("recurring"));
		if((String) corpDetails.get("businessUnit") != null && (String) corpDetails.get("businessUnit") != "")
			app.setBusinessUnitMasterNo((ConfigurableConstants.getMasterTable(ConfigurableConstants.GOVT_EINVOICE_BUSINESS_UNIT_MASTER_TYPE, (String)corpDetails.get("businessUnit"))).getMasterNo());
		
		AmtbApplicationFlow appFlow = new AmtbApplicationFlow();
		appFlow.setAmtbApplication(app);
		appFlow.setSatbUser(this.daoHelper.getUserDao().getUserWithRoles(CommonWindow.getUserId()));
		appFlow.setRemarks((String)corpDetails.get("remarks"));
		appFlow.setToStatus("D");
		appFlow.setFlowDt(DateUtil.getCurrentTimestamp());
		if(corpDetails.get("appNo")!=null){
			appFlow.setFromStatus("D");
			this.save(appFlow);
		}else{
			appFlow.setFromStatus("N");
			app.getAmtbApplicationFlows().add(appFlow);
		}
		// clearing product subscriptions
		for(AmtbApplicationProduct product : app.getAmtbApplicationProducts()){
			this.delete(product);
		}
		app.getAmtbApplicationProducts().clear();
		// setting product subscriptions
		List<Map<String, Object>> prodSubscriptions = (List<Map<String, Object>>)corpDetails.get("prodSubscriptions");
		for(Map<String, Object> prodSubscription : prodSubscriptions){
			AmtbApplicationProduct subscription = new AmtbApplicationProduct();
			subscription.setAmtbApplication(app);
			// setting the product type
			subscription.setPmtbProductType(this.daoHelper.getProductTypeDao().getProductType((String)prodSubscription.get("prodTypeId")));
			// setting the product discount
			subscription.setMstbProdDiscMaster((MstbProdDiscMaster)MasterSetup.getProductDiscountManager().getMaster((Integer)prodSubscription.get("prodDiscountNo")));
			// setting the rewards
			subscription.setLrtbRewardMaster((LrtbRewardMaster)MasterSetup.getRewardsManager().getMaster((Integer)prodSubscription.get("rewardsNo")));
			// setting the subscription fee
			subscription.setMstbSubscFeeMaster((MstbSubscFeeMaster) MasterSetup.getSubscriptionManager().getMaster((Integer)prodSubscription.get("subscriptionNo")));
			// setting the issuance fee
			subscription.setMstbIssuanceFeeMaster((MstbIssuanceFeeMaster) MasterSetup.getIssuanceManager().getMaster((Integer)prodSubscription.get("issuanceNo")));
			if(corpDetails.get("appNo")!=null){
				this.save(subscription);
			}else{
				app.getAmtbApplicationProducts().add(subscription);
			}
		}
		return app;
	}
	@SuppressWarnings("unchecked")
	private AmtbApplication createPersAppObject(Map<String, Object> persDetails){
		logger.info("createCorpAppObject(Map<String, Object> persDetails)");
		AmtbApplication app;
		if(persDetails.get("appNo")!=null){
			app = this.daoHelper.getApplicationDao().getApplication((String)persDetails.get("appNo"));
		}else{
			app = new AmtbApplication();
		}
		// common inputs
		app.setAmtbAcctType(this.daoHelper.getAccountTypeDao().getAccountType((Integer)persDetails.get("accountTypeNo")).get(0));
		app.setApplicantName((String)persDetails.get("acctName"));
		app.setNameOnCard((String)persDetails.get("nameOnCard"));
		app.setMstbMasterTableByIndustryNo(ConfigurableConstants.getMasterTable(ConfigurableConstants.INDUSTRY_MASTER_CODE, (String)persDetails.get("industryListCode")));
		app.setTel((String)persDetails.get("tel"));
		app.setAddressBlock((String)persDetails.get("blkNo"));
		app.setAddressUnit((String)persDetails.get("unitNo"));
		app.setAddressStreet((String)persDetails.get("street"));
		app.setAddressBuilding((String)persDetails.get("building"));
		app.setAddressArea((String)persDetails.get("area"));
		app.setMstbMasterTableByAddressCountry(ConfigurableConstants.getMasterTable(ConfigurableConstants.COUNTRY_MASTER_CODE, (String)persDetails.get("countryListCode")));
		app.setAddressCity((String)persDetails.get("city"));
		app.setAddressState((String)persDetails.get("state"));
		app.setAddressPostal((String)persDetails.get("postal"));
		app.setMstbMasterTableByJobStatus(ConfigurableConstants.getMasterTable(ConfigurableConstants.JOB_STATUS_MASTER_CODE, (String)persDetails.get("jobStatusCode")));
		app.setMstbMasterTableByAuthSal(ConfigurableConstants.getMasterTable(ConfigurableConstants.SALUTATION_MASTER_CODE, (String)persDetails.get("salutationCode")));
		app.setMstbMasterTableByInformationSource(ConfigurableConstants.getMasterTable(ConfigurableConstants.INFORMATION_SOURCE_MASTER_CODE, (String)persDetails.get("infoSourceCode")));
		app.setFmtbArContCodeMaster((FmtbArContCodeMaster) MasterSetup.getEntityManager().getDetail((Integer)persDetails.get("arControlCodeNo")));
		app.setEinvoiceFlag((String)persDetails.get("eInvoice"));
		app.setInvoicePrinting((String)persDetails.get("invoicePrinting"));
		app.setOutsourcePrintingFlag((String)persDetails.get("outsourcePrinting"));
		app.setPrintTaxInvoiceOnly((String)persDetails.get("printTaxInvoiceOnly"));
		app.setSmsFlag((String)persDetails.get("sms"));
		app.setSmsExpiryFlag((String)persDetails.get("smsExpiry"));
		app.setSmsTopupFlag((String)persDetails.get("smsTopUp"));
		app.setAceIndicator("N");
		app.setRecurringFlag((String)persDetails.get("recurring"));
		// clearing product subscriptions
		for(AmtbApplicationProduct product : app.getAmtbApplicationProducts()){
			this.delete(product);
		}
		app.getAmtbApplicationProducts().clear();
		// setting product subscriptions
		List<Map<String, Object>> prodSubscriptions = (List<Map<String, Object>>)persDetails.get("prodSubscriptions");
		for(Map<String, Object> prodSubscription : prodSubscriptions){
			AmtbApplicationProduct subscription = new AmtbApplicationProduct();
			subscription.setAmtbApplication(app);
			// setting the product type
			subscription.setPmtbProductType(this.daoHelper.getProductTypeDao().getProductType((String)prodSubscription.get("prodTypeId")));
			// setting the product discount
			subscription.setMstbProdDiscMaster((MstbProdDiscMaster)MasterSetup.getProductDiscountManager().getMaster((Integer)prodSubscription.get("prodDiscountNo")));
			// setting the rewards
			subscription.setLrtbRewardMaster((LrtbRewardMaster)MasterSetup.getRewardsManager().getMaster((Integer)prodSubscription.get("rewardsNo")));
			// setting the subscription fee
			subscription.setMstbSubscFeeMaster((MstbSubscFeeMaster) MasterSetup.getSubscriptionManager().getMaster((Integer)prodSubscription.get("subscriptionNo")));
			// setting the issuance fee
			subscription.setMstbIssuanceFeeMaster((MstbIssuanceFeeMaster) MasterSetup.getIssuanceManager().getMaster((Integer)prodSubscription.get("issuanceNo")));
			if(persDetails.get("appNo")!=null){
				this.save(subscription);
			}else{
				app.getAmtbApplicationProducts().add(subscription);
			}
		}
		// assessment
		app.setCreditLimit((BigDecimal)persDetails.get("creditLimit"));
		app.setDeposit((BigDecimal)persDetails.get("deposit"));
		app.setMstbSalesperson((MstbSalesperson)MasterSetup.getSalespersonManager().getMaster((Integer)persDetails.get("salespersonNo")));
		app.setMstbVolDiscMaster((MstbVolDiscMaster)MasterSetup.getVolumeDiscountManager().getMaster((Integer)persDetails.get("volumeDiscountNo")));
		app.setMstbAdminFeeMaster((MstbAdminFeeMaster)MasterSetup.getAdminFeeManager().getMaster((Integer)persDetails.get("adminFeesNo")));
		app.setMstbEarlyPaymentMaster((MstbEarlyPaymentMaster)MasterSetup.getEarlyPaymentManager().getMaster((Integer)persDetails.get("earlyDiscountNo")));
		app.setMstbLatePaymentMaster((MstbLatePaymentMaster)MasterSetup.getLatePaymentManager().getMaster((Integer)persDetails.get("lateInterestNo")));
		// specific inputs
		app.setNric((String)persDetails.get("nric"));
		app.setRcbDt(DateUtil.convertDateToTimestamp((Date)persDetails.get("birthdate")));
		app.setEmail((String)persDetails.get("email"));
		app.setMobile((String)persDetails.get("mobile"));
		app.setOffice((String)persDetails.get("office"));
		if((String)persDetails.get("mainContactRace")!=null) {
			app.setMstbMasterTableByMainContactRace(ConfigurableConstants.getMasterTable(ConfigurableConstants.RACE_MASTER_CODE, (String)persDetails.get("mainContactRace")));
		}
		app.setBillingSame((String)persDetails.get("billAdd"));
		app.setBillingBlock((String)persDetails.get("billBlkNo"));
		app.setBillingUnit((String)persDetails.get("billUnitNo"));
		app.setBillingStreet((String)persDetails.get("billStreet"));
		app.setBillingBuilding((String)persDetails.get("billBuilding"));
		app.setBillingArea((String)persDetails.get("billArea"));
		if((String)persDetails.get("billCountryListCode")!=null){
			app.setMstbMasterTableByBillingCountry(ConfigurableConstants.getMasterTable(ConfigurableConstants.COUNTRY_MASTER_CODE, (String)persDetails.get("billCountryListCode")));
		}
		app.setBillingCity((String)persDetails.get("billCity"));
		app.setBillingState((String)persDetails.get("billState"));
		app.setBillingPostal((String)persDetails.get("billPostal"));
		app.setShippingSame((String)persDetails.get("shipAdd"));
		app.setShippingBlock((String)persDetails.get("shipBlkNo"));
		app.setShippingUnit((String)persDetails.get("shipUnitNo"));
		app.setShippingStreet((String)persDetails.get("shipStreet"));
		app.setShippingBuilding((String)persDetails.get("shipBuilding"));
		app.setShippingArea((String)persDetails.get("shipArea"));
		if((String)persDetails.get("shipCountryListCode")!=null){
			app.setMstbMasterTableByShippingCountry(ConfigurableConstants.getMasterTable(ConfigurableConstants.COUNTRY_MASTER_CODE, (String)persDetails.get("shipCountryListCode")));
		}
		app.setShippingCity((String)persDetails.get("shipCity"));
		app.setShippingState((String)persDetails.get("shipState"));
		app.setShippingPostal((String)persDetails.get("shipPostal"));
		if(persDetails.get("jobStatusNo")!=null){
			app.setMstbMasterTableByJobStatus(ConfigurableConstants.getMasterTable(ConfigurableConstants.JOB_STATUS_MASTER_CODE, (String)persDetails.get("jobStatusCode")));
		}
		app.setOccupation((String)persDetails.get("occupation"));
		app.setEmployerName((String)persDetails.get("employerName"));
		app.setEmployerBlock((String)persDetails.get("empBlkNo"));
		app.setEmployerUnit((String)persDetails.get("empUnitNo"));
		app.setEmployerStreet((String)persDetails.get("empStreet"));
		app.setEmployerBuilding((String)persDetails.get("empBuilding"));
		app.setEmployerArea((String)persDetails.get("empArea"));
		if((String)persDetails.get("empCountryListCode")!=null){
			app.setMstbMasterTableByEmployerCountry(ConfigurableConstants.getMasterTable(ConfigurableConstants.COUNTRY_MASTER_CODE, (String)persDetails.get("empCountryListCode")));
		}
		app.setEmployerCity((String)persDetails.get("empCity"));
		app.setEmployerState((String)persDetails.get("empState"));
		app.setEmployerPostal((String)persDetails.get("empPostal"));
		app.setMonthlyIncome((BigDecimal)persDetails.get("monthlyIncome"));
		if((BigDecimal)persDetails.get("empLength")!=null){
			app.setEmployYears(((BigDecimal)persDetails.get("empLength")).intValue());
		}
		AmtbApplicationFlow appFlow = new AmtbApplicationFlow();
		appFlow.setAmtbApplication(app);
		appFlow.setSatbUser(this.daoHelper.getUserDao().getUserWithRoles(CommonWindow.getUserId()));
		if(persDetails.get("appNo")!=null){
			appFlow.setFromStatus(NonConfigurableConstants.APPLICATION_STATUS_DRAFT);
		}else{
			appFlow.setFromStatus(NonConfigurableConstants.APPLICATION_STATUS_NEW);
		}
		appFlow.setRemarks((String)persDetails.get("remarks"));
		appFlow.setToStatus(NonConfigurableConstants.APPLICATION_STATUS_DRAFT);
		appFlow.setFlowDt(DateUtil.getCurrentTimestamp());
		app.getAmtbApplicationFlows().add(appFlow);
		return app;
	}
	public boolean submitApplication(String appNo, String remarks, String loginId){
		AmtbApplication app = this.daoHelper.getApplicationDao().getApplication(appNo);
		List<String> toEmails = new ArrayList<String>();
		List<String> ccEmails = new ArrayList<String>();
		SatbUser user = this.daoHelper.getUserDao().getUserWithRoles(CommonWindow.getUserId());
		//ccEmails.add(user.getEmail());
		String salespersonEmail = this.daoHelper.getApplicationDao().getApplicationSalesperson(appNo).getEmail();
		ccEmails.add(salespersonEmail);
		String uri = null;
		if(app.getAmtbAcctType().getAcctTemplate().equals(NonConfigurableConstants.ACCOUNT_TEMPLATES_CORPORATE)){
			uri = Uri.APPROVE_CORP_APP;
		}else{
			uri = Uri.APPROVE_PERS_APP;
		}
		StringBuffer approverNames = new StringBuffer();
		List<SatbUser> approvers = this.daoHelper.getUserDao().searchUser(uri);
		for(SatbUser approver : approvers){
			toEmails.add(approver.getEmail());
			approverNames.append(approver.getName() + "/");
		}
		approverNames.delete(approverNames.length()-1, approverNames.length()-0);
		EmailUtil.sendEmail(toEmails.toArray(new String[]{}), ccEmails.toArray(new String[]{}),
				ConfigurableConstants.getEmailText(ConfigurableConstants.EMAIL_TYPE_APP_REQUEST_SUBMIT, ConfigurableConstants.EMAIL_SUBJECT),
				ConfigurableConstants.getEmailText(ConfigurableConstants.EMAIL_TYPE_APP_REQUEST_SUBMIT, ConfigurableConstants.EMAIL_CONTENT)
					.replaceAll("#appNo#", appNo)
					.replaceAll("#appName#", app.getApplicantName())
					.replaceAll("#submiter#", user.getName())
					.replaceAll("#userName#", approverNames.toString()));
		Timestamp now = DateUtil.getCurrentTimestamp();
		app.setApplicationDt(now);
		AmtbApplicationFlow appFlow = new AmtbApplicationFlow();
		appFlow.setAmtbApplication(app);
		appFlow.setRemarks(remarks);
		appFlow.setSatbUser(user);
		appFlow.setFromStatus(NonConfigurableConstants.APPLICATION_STATUS_DRAFT);
		appFlow.setToStatus(NonConfigurableConstants.APPLICATION_STATUS_PENDING_1ST_LEVEL_APPROVAL);
		appFlow.setFlowDt(now);
		this.daoHelper.getApplicationDao().update(app);
		return this.daoHelper.getApplicationDao().save(appFlow)!=null;
	}
	public boolean recommandApplication(AmtbApplication app, String remarks, String loginId)throws ConcurrencyFailureException{
		//AmtbApplication app = this.daoHelper.getApplicationDao().getApplication(appNo);
		String appNo = app.getApplicationNo();
		
		Timestamp now = DateUtil.getCurrentTimestamp();
		app.setApplicationDt(now);
		AmtbApplicationFlow appFlow = new AmtbApplicationFlow();
		appFlow.setAmtbApplication(app);
		appFlow.setRemarks(remarks);
		appFlow.setFromStatus(NonConfigurableConstants.APPLICATION_STATUS_PENDING_1ST_LEVEL_APPROVAL);
		appFlow.setToStatus(NonConfigurableConstants.APPLICATION_STATUS_PENDING_2ND_LEVEL_APPROVAL);
		appFlow.setSatbUser(this.daoHelper.getUserDao().getUserWithRoles(CommonWindow.getUserId()));
		appFlow.setFlowDt(now);
		this.daoHelper.getApplicationDao().update(app);
		if(this.daoHelper.getApplicationDao().save(appFlow)!=null){
			
			// save successful, create an email and send to the 2nd level approver + requester + sales
			/*if(app.getAmtbAcctType().getAcctTemplate().equals(NonConfigurableConstants.ACCOUNT_TEMPLATES_CORPORATE)){
				approvers = this.daoHelper.getUserDao().searchUser(Uri.APPROVE_CORP_APP_2);
			}else{
				approvers = this.daoHelper.getUserDao().searchUser(Uri.APPROVE_PERS_APP_2);
			}
			List<String> to = new ArrayList<String>();
			for(SatbUser approver : approvers){
				to.add(approver.getEmail());
			}*/
			List<String> toEmails = new ArrayList<String>();
			List<String> ccEmails = new ArrayList<String>();
			SatbUser recommender = this.daoHelper.getUserDao().getUserWithRoles(CommonWindow.getUserId());
			ccEmails.add(recommender.getEmail());
			String salespersonEmail = this.daoHelper.getApplicationDao().getApplicationSalesperson(appNo).getEmail();
			ccEmails.add(salespersonEmail);
			String uri = null;
			if(app.getAmtbAcctType().getAcctTemplate().equals(NonConfigurableConstants.ACCOUNT_TEMPLATES_CORPORATE)){
				uri = Uri.APPROVE_CORP_APP_2;
			}else{
				uri = Uri.APPROVE_PERS_APP_2;
			}
			List<SatbUser> approvers = this.daoHelper.getUserDao().searchUser(uri);
			StringBuffer approverNames = new StringBuffer();
			for(SatbUser approver : approvers){
				toEmails.add(approver.getEmail());
				approverNames.append(approver.getName() + "/");
			}
			approverNames.delete(approverNames.length()-1, approverNames.length());
			EmailUtil.sendEmail(toEmails.toArray(new String[]{}), ccEmails.toArray(new String[]{}),
					ConfigurableConstants.getEmailText(ConfigurableConstants.EMAIL_TYPE_APP_REQUEST_RECOMMAND, ConfigurableConstants.EMAIL_SUBJECT),
					ConfigurableConstants.getEmailText(ConfigurableConstants.EMAIL_TYPE_APP_REQUEST_RECOMMAND, ConfigurableConstants.EMAIL_CONTENT)
						.replaceAll("#appNo#", appNo)
						.replaceAll("#appName#", app.getApplicantName())
						.replaceAll("#submiter#", getRequestingStatus(app.getAmtbApplicationFlows()).getSatbUser().getName())
						.replaceAll("#userName#", approverNames.toString()));
			
			
			return true;
		}else{
			return false;
		}
	}
	public String approveApplication(AmtbApplication app, String remarks, String loginId)throws ConcurrencyFailureException{
		//AmtbApplication app = this.daoHelper.getApplicationDao().getApplication(appNo);
		String appNo = app.getApplicationNo();
		Timestamp now = DateUtil.getCurrentTimestamp();
		app.setApplicationDt(now);
		AmtbApplicationFlow appFlow = new AmtbApplicationFlow();
		appFlow.setAmtbApplication(app);
		Set<AmtbApplicationFlow> applicationFlows = app.getAmtbApplicationFlows();
		AmtbApplicationFlow latestStatus = null;
		for(AmtbApplicationFlow applicationStatus : applicationFlows){
			if(latestStatus==null){
				latestStatus = applicationStatus;
			}else{
				if(latestStatus.getFlowDt().before(applicationStatus.getFlowDt())){
					latestStatus = applicationStatus;
				}
			}
		}
		appFlow.setFromStatus(latestStatus.getToStatus());
		appFlow.setToStatus(NonConfigurableConstants.APPLICATION_STATUS_APPROVED);
		appFlow.setSatbUser(this.daoHelper.getUserDao().getUserWithRoles(CommonWindow.getUserId()));
		appFlow.setRemarks(remarks);
		appFlow.setFlowDt(now);
		if(this.daoHelper.getApplicationDao().save(appFlow)!=null){
			app.getAmtbApplicationFlows().add(appFlow);
			//create account from here
			AmtbAccount newAcct = createAcct(app);		
			this.daoHelper.getAccountDao().save(newAcct, loginId);
			if(app.getAmtbAcctType().getAcctTemplate().equals(NonConfigurableConstants.ACCOUNT_TEMPLATES_PERSONAL)){
				createContactPersons(newAcct, app);
			}
			app.setAmtbAccount(newAcct);
			this.daoHelper.getApplicationDao().update(app);
			// save successful, create an email and send to the 2nd level approver + requester + sales
			List<String> toEmails = new ArrayList<String>();
			List<String> ccEmails = new ArrayList<String>();
			String salespersonEmail = this.daoHelper.getApplicationDao().getApplicationSalesperson(appNo).getEmail();
			ccEmails.add(salespersonEmail);
			SatbUser requester = getRequestingStatus(app.getAmtbApplicationFlows()).getSatbUser();
			toEmails.add(requester.getEmail());
			EmailUtil.sendEmail(toEmails.toArray(new String[]{}), ccEmails.toArray(new String[]{}),
					ConfigurableConstants.getEmailText(ConfigurableConstants.EMAIL_TYPE_APP_REQUEST_APPROVED, ConfigurableConstants.EMAIL_SUBJECT),
					ConfigurableConstants.getEmailText(ConfigurableConstants.EMAIL_TYPE_APP_REQUEST_APPROVED, ConfigurableConstants.EMAIL_CONTENT)
						.replaceAll("#appNo#", appNo)
						.replaceAll("#appName#", app.getApplicantName())
						.replaceAll("#userName#", requester.getName())
						.replaceAll("#custNo#", newAcct.getCustNo()));
			return newAcct.getCustNo();
		}else{
			return null;
		}
	}
	public boolean rejectApplication(AmtbApplication app, String remarks, String loginId)throws ConcurrencyFailureException{
		//AmtbApplication app = this.daoHelper.getApplicationDao().getApplication(appNo);
		String appNo = app.getApplicationNo();
		Timestamp now = DateUtil.getCurrentTimestamp();
		app.setApplicationDt(now);
		AmtbApplicationFlow appFlow = new AmtbApplicationFlow();
		appFlow.setAmtbApplication(app);
		Set<AmtbApplicationFlow> applicationFlows = app.getAmtbApplicationFlows();
		AmtbApplicationFlow latestFlow = null;
		for(AmtbApplicationFlow applicationStatus : applicationFlows){
			if(latestFlow==null){
				latestFlow = applicationStatus;
			}else{
				if(latestFlow.getFlowDt().before(applicationStatus.getFlowDt())){
					latestFlow = applicationStatus;
				}
			}
		}
		appFlow.setFromStatus(latestFlow.getToStatus());
		appFlow.setToStatus(NonConfigurableConstants.APPLICATION_STATUS_REJECTED);
		appFlow.setSatbUser(this.daoHelper.getUserDao().getUserWithRoles(CommonWindow.getUserId()));
		appFlow.setRemarks(remarks);
		appFlow.setFlowDt(now);
		this.daoHelper.getApplicationDao().update(app);
		Serializable result = this.daoHelper.getApplicationDao().save(appFlow);
		
		List<String> toEmails = new ArrayList<String>();
		List<String> ccEmails = new ArrayList<String>();
		String salespersonEmail = this.daoHelper.getApplicationDao().getApplicationSalesperson(appNo).getEmail();
		ccEmails.add(salespersonEmail);
		SatbUser requester = getRequestingStatus(app.getAmtbApplicationFlows()).getSatbUser();
		toEmails.add(requester.getEmail());
		EmailUtil.sendEmail(toEmails.toArray(new String[]{}), ccEmails.toArray(new String[]{}),
				ConfigurableConstants.getEmailText(ConfigurableConstants.EMAIL_TYPE_APP_REQUEST_REJECTED, ConfigurableConstants.EMAIL_SUBJECT),
				ConfigurableConstants.getEmailText(ConfigurableConstants.EMAIL_TYPE_APP_REQUEST_REJECTED, ConfigurableConstants.EMAIL_CONTENT)
					.replaceAll("#appNo#", appNo)
					.replaceAll("#appName#", app.getApplicantName())
					.replaceAll("#userName#", requester.getName()));
		
		return result!=null;
	}
	public void savePersApplication(Map<String, Object> persDetails) {
		logger.info("saveCorpApplication(Map<String, Object> corpDetails)");
		this.daoHelper.getApplicationDao().saveApplication(createPersAppObject(persDetails));
	}
	public Map<String, String> getAllProductTypes(){
		List<PmtbProductType> productTypes = this.daoHelper.getProductTypeDao().getAllProductType();
		Map<String, String> returnMap = new LinkedHashMap<String, String>();
		for(PmtbProductType productType : productTypes){
			returnMap.put(productType.getProductTypeId(), productType.getName());
		}
		return returnMap;
	}
	/**
	 * method to get the approver
	 * @param level level 1 or level 2
	 * @return the names of the approver e.g. Approver1 / Approver 2
	 */
	public String getApplicationApprovers(String acctTemplate, Integer level){
		logger.debug("getApplicationApprovers(String acctTemplate, Integer level)");
		// getting the approver for approving applications
		String uri = null;
		if(acctTemplate.equals(NonConfigurableConstants.ACCOUNT_TEMPLATES_CORPORATE)){
			if(level == 1){
				uri = Uri.APPROVE_CORP_APP;
			}else if(level==2){
				uri = Uri.APPROVE_CORP_APP_2;
			}else{
				throw new IllegalArgumentException("There can only be level 1 or 2 approvers. Input level = " + level);
			}
		}else if(acctTemplate.equals(NonConfigurableConstants.ACCOUNT_TEMPLATES_PERSONAL)){
			if(level==1){
				uri = Uri.APPROVE_PERS_APP;
			}else if(level==2){
				uri = Uri.APPROVE_PERS_APP_2;
			}else{
				throw new IllegalArgumentException("There can only be level 1 or 2 approvers. Input level = " + level);
			}
		}else{
			throw new IllegalArgumentException("There can only be PERSONAL or CORPORATE template. Input Template = " + acctTemplate);
		}
		List<SatbUser> approvers = this.daoHelper.getUserDao().searchUser(uri);
		Set<String> apprNames = new TreeSet<String>(new Comparator<String>(){
			public int compare(String name1, String name2) {
				return name1.compareTo(name2);
			}			
		});
		for(SatbUser approver : approvers){
			apprNames.add(approver.getName());
		}
		StringBuffer returnString = new StringBuffer();
		for(String apprName : apprNames){
			returnString.append(apprName);
			returnString.append(" / ");
		}
		returnString.delete(returnString.length()-3, returnString.length()-1);
		return returnString.toString();
	}
	
	private AmtbAccount createAcct(AmtbApplication app){
		// creating new account
		AmtbAccount newAcct = new AmtbAccount();
		// setting the common fields
		setCommonFields(newAcct, app);
		// setting specific fields
		if(app.getAmtbAcctType().getAcctTemplate().equals(NonConfigurableConstants.ACCOUNT_TEMPLATES_CORPORATE)){
			createCorpAcct(newAcct, app);
		}else{
			createPersAcct(newAcct, app);
		}
		// now adding product subscriptions to it
		Set<AmtbApplicationProduct> prodSubs = app.getAmtbApplicationProducts();
		for(AmtbApplicationProduct prodSub : prodSubs){
			AmtbSubscTo acctSubscribe = new AmtbSubscTo();
			acctSubscribe.setComp_id(new AmtbSubscToPK(prodSub.getPmtbProductType(), newAcct));
			if(prodSub.getLrtbRewardMaster()!=null){
				acctSubscribe.setLrtbRewardMaster(prodSub.getLrtbRewardMaster());
			}
			if(prodSub.getMstbProdDiscMaster()!=null){
				acctSubscribe.setMstbProdDiscMaster(prodSub.getMstbProdDiscMaster());
			}
			if(prodSub.getMstbSubscFeeMaster()!=null){
				acctSubscribe.setMstbSubscFeeMaster(prodSub.getMstbSubscFeeMaster());
			}
			if(prodSub.getMstbIssuanceFeeMaster()!=null){
				acctSubscribe.setMstbIssuanceFeeMaster(prodSub.getMstbIssuanceFeeMaster());
			}
		}
		return newAcct;
	}
	private void createCorpAcct(AmtbAccount acct, AmtbApplication app){
		acct.setAccountCategory(NonConfigurableConstants.ACCOUNT_CATEGORY_CORPORATE);
		AmtbCorporateDetail corpDetail = new AmtbCorporateDetail();
		// setting telephone
		corpDetail.setTel(app.getTel());
		// setting the fax
		corpDetail.setFax(app.getFax());
		corpDetail.setRcbNo(app.getRcbNo());
		corpDetail.setRcbDt(app.getRcbDt());
		corpDetail.setCapital(app.getCapital());
		corpDetail.setAddressBlock(app.getAddressBlock());
		corpDetail.setAddressUnit(app.getAddressUnit());
		corpDetail.setAddressBuilding(app.getAddressBuilding());
		corpDetail.setAddressStreet(app.getAddressStreet());
		corpDetail.setAddressArea(app.getAddressArea());
		corpDetail.setMstbMasterTableByAddressCountry(app.getMstbMasterTableByAddressCountry());
		corpDetail.setAddressCity(app.getAddressCity());
		corpDetail.setAddressState(app.getAddressState());
		corpDetail.setAddressPostal(app.getAddressPostal());
		corpDetail.setAuthPersonName(app.getAuthPerson());
		corpDetail.setAuthPersonTitle(app.getAuthTitle());
		corpDetail.setMstbMasterTableByAuthPersonSal(app.getMstbMasterTableByAuthSal());
		corpDetail.setMstbMasterTableByIndustry(app.getMstbMasterTableByIndustryNo());
		corpDetail.setProjectCode(app.getProjectCode());
		corpDetail.setAmtbAccount(acct);
		acct.getAmtbCorporateDetails().add(corpDetail);
		
		if(app.getInvoiceSorting() != null)
			acct.setInvoiceSorting(app.getInvoiceSorting());
		else
			acct.setInvoiceSorting(NonConfigurableConstants.INVOICE_SORTING_CARD);
		if(app.getInvoiceFormat() != null)
			acct.setInvoiceFormat(app.getInvoiceFormat());
		else	
			acct.setInvoiceFormat(NonConfigurableConstants.INVOICE_FORMAT_ACCOUNT);
		
		if(app.getGovtEInvoiceFlag() != null)
			acct.setGovtEInvoiceFlag(app.getGovtEInvoiceFlag());
		
		if(app.getBusinessUnitMasterNo() != null)
			acct.setMstbMasterTableByBusinessUnit(ConfigurableConstants.getMasterTableByMasterNo(ConfigurableConstants.GOVT_EINVOICE_BUSINESS_UNIT_MASTER_TYPE, app.getBusinessUnitMasterNo()));

		if(app.getPrintTaxInvoiceOnly() != null)
			acct.setPrintTaxInvoiceOnly(app.getPrintTaxInvoiceOnly());
		
		acct.setAceIndicator(app.getAceIndicator());
		
		if(app.getPubbsFlag() != null)
			acct.setPubbsFlag(app.getPubbsFlag());
		if(app.getFiFlag() != null)
			acct.setFiFlag(app.getFiFlag());
	}
	private void createPersAcct(AmtbAccount acct, AmtbApplication app){
		acct.setAccountCategory(NonConfigurableConstants.ACCOUNT_CATEGORY_APPLICANT);
		AmtbPersonalDetail persDetail = new AmtbPersonalDetail();
		persDetail.setBirthDt(app.getRcbDt());
		persDetail.setTel(app.getTel());
		persDetail.setNric(app.getNric());
		persDetail.setAddressBlock(app.getAddressBlock());
		persDetail.setAddressUnit(app.getAddressUnit());
		persDetail.setAddressStreet(app.getAddressStreet());
		persDetail.setAddressBuilding(app.getAddressBuilding());
		persDetail.setAddressArea(app.getAddressArea());
		persDetail.setMstbMasterTableByAddressCountry(app.getMstbMasterTableByAddressCountry());
		persDetail.setAddressCity(app.getAddressCity());
		persDetail.setAddressState(app.getAddressState());
		persDetail.setAddressPostal(app.getAddressPostal());
		persDetail.setEmployerName(app.getEmployerName());
		persDetail.setEmployerBlock(app.getEmployerBlock());
		persDetail.setEmployerUnit(app.getEmployerUnit());
		persDetail.setEmployerStreet(app.getEmployerStreet());
		persDetail.setEmployerBuilding(app.getEmployerBuilding());
		persDetail.setEmployerArea(app.getEmployerArea());
		persDetail.setMstbMasterTableByEmployerCountry(app.getMstbMasterTableByEmployerCountry());
		persDetail.setEmployerCity(app.getEmployerCity());
		persDetail.setEmployerState(app.getEmployerState());
		persDetail.setEmployerPostal(app.getEmployerPostal());
		persDetail.setMonthlyIncome(app.getMonthlyIncome());
		if(app.getEmployYears()!=null){
			persDetail.setEmployLengthYear(app.getEmployYears());
		}
		persDetail.setMstbMasterTableByIndustry(app.getMstbMasterTableByIndustryNo());
		persDetail.setMstbMasterTableByJobStatus(app.getMstbMasterTableByJobStatus());
		persDetail.setOccupation(app.getOccupation());
		
		if(app.getMstbMasterTableByMainContactRace() != null)
			persDetail.setMstbMasterTableByMainContactRace(app.getMstbMasterTableByMainContactRace());
		
		persDetail.setAmtbAccount(acct);
		acct.getAmtbPersonalDetails().add(persDetail);
		acct.setInvoiceFormat(NonConfigurableConstants.INVOICE_FORMAT_PERSONAL);
		acct.setInvoiceSorting(NonConfigurableConstants.INVOICE_SORTING_CARD);
		acct.setAceIndicator("N");
		
		if(app.getPrintTaxInvoiceOnly() != null)
			acct.setPrintTaxInvoiceOnly(app.getPrintTaxInvoiceOnly());
		logger.info("here");
	}
	private void setCommonFields(AmtbAccount acct, AmtbApplication app){
		// getting customer no
		BigDecimal nextValue = this.daoHelper.getApplicationDao().getNextSequenceNo(Sequence.CUST_NO_SEQUENCE); 
		acct.setCustNo(nextValue.toString());
		// setting account name
		acct.setAccountName(app.getApplicantName());
		// setting name on card
		acct.setNameOnCard(app.getNameOnCard());
		// setting account type
		acct.setAmtbAcctType(app.getAmtbAcctType());
		// setting information source
		acct.setMstbMasterTableByInformationSource(app.getMstbMasterTableByInformationSource());
		// setting ar control code
		acct.setFmtbArContCodeMaster(app.getFmtbArContCodeMaster());
		// setting eInvoice
		acct.setEinvoiceFlag(app.getEinvoiceFlag());
		// setting invoice printing
		acct.setInvoicePrinting(app.getInvoicePrinting());
		// setting outsource printing flag
		acct.setOutsourcePrintingFlag(app.getOutsourcePrintingFlag());
		// setting sms
		acct.setSmsFlag(app.getSmsFlag());
		// setting sms expiry		
		acct.setSmsExpiryFlag(app.getSmsExpiryFlag());
		// setting sms top up
		acct.setSmsTopupFlag(app.getSmsTopupFlag());
		// setting govt einvoice
		acct.setGovtEInvoiceFlag(NonConfigurableConstants.GOVT_EINV_FLAG_NO);
		//set Overdue Reminder
		acct.setOverdueReminder("Y");
		acct.setPrintTaxInvoiceOnly(app.getPrintTaxInvoiceOnly());
		acct.setRecurringFlag(app.getRecurringFlag());
		setProductSubscriptions(acct, app);
		setAssessmentFields(acct, app);
	}
	private void setProductSubscriptions(AmtbAccount acct, AmtbApplication app){
		Set<AmtbApplicationProduct> subscribedProds = app.getAmtbApplicationProducts();
		for(Iterator<AmtbApplicationProduct> iter = subscribedProds.iterator(); iter.hasNext();){
			AmtbApplicationProduct subscribedProd = iter.next();
			AmtbSubscTo acctSubscribeTo = new AmtbSubscTo();
			acctSubscribeTo.setComp_id(new AmtbSubscToPK(subscribedProd.getPmtbProductType(), acct));
			if(subscribedProd.getLrtbRewardMaster()!=null){
				acctSubscribeTo.setLrtbRewardMaster(subscribedProd.getLrtbRewardMaster());
			}
			if(subscribedProd.getMstbProdDiscMaster()!=null){
				acctSubscribeTo.setMstbProdDiscMaster(subscribedProd.getMstbProdDiscMaster());
			}
			if(subscribedProd.getMstbSubscFeeMaster()!=null){
				acctSubscribeTo.setMstbSubscFeeMaster(subscribedProd.getMstbSubscFeeMaster());
			}
			if(subscribedProd.getMstbIssuanceFeeMaster()!=null){
				acctSubscribeTo.setMstbIssuanceFeeMaster(subscribedProd.getMstbIssuanceFeeMaster());
			}
			acctSubscribeTo.setEffectiveDt(DateUtil.getCurrentTimestamp());
			acct.getAmtbSubscTos().add(acctSubscribeTo);
		}
	}
	private void setAssessmentFields(AmtbAccount acct, AmtbApplication app){
		// setting credit limit
		AmtbAcctCredLimit creditLimit = new AmtbAcctCredLimit();
		creditLimit.setAmtbAccount(acct);
		creditLimit.setCreditLimitType(NonConfigurableConstants.CREDIT_LIMIT_PERMANENT);
		creditLimit.setEffectiveDtFrom(DateUtil.getCurrentTimestamp());
		creditLimit.setNewCreditLimit(app.getCreditLimit());
		TreeSet<AmtbApplicationFlow> flows = new TreeSet<AmtbApplicationFlow>(new Comparator<AmtbApplicationFlow>(){
			public int compare(AmtbApplicationFlow af1, AmtbApplicationFlow af2) {
				return af1.getFlowDt().compareTo(af2.getFlowDt());
			}
		});
		flows.addAll(app.getAmtbApplicationFlows());
		StringBuffer remarksBuffer = new StringBuffer();
		String tempUserId = null;
		for(AmtbApplicationFlow flow : flows){
			if(flow.getFromStatus().equals(NonConfigurableConstants.APPLICATION_STATUS_PENDING_1ST_LEVEL_APPROVAL)){
				if(flow.getRemarks()!=null && flow.getRemarks().length()!=0){
					remarksBuffer.append(flow.getRemarks());
				}else{
					remarksBuffer.append("INITIAL CREDIT LIMIT");
				}
				tempUserId = flow.getSatbUser().getLoginId();
			}else if(flow.getFromStatus().equals(NonConfigurableConstants.APPLICATION_STATUS_PENDING_2ND_LEVEL_APPROVAL)){
				remarksBuffer.insert(0, tempUserId + " : ");
				remarksBuffer.append(System.getProperty("line.separator"));
				remarksBuffer.append(flow.getSatbUser().getLoginId() + " : ");
				if(flow.getRemarks()!=null && flow.getRemarks().length()!=0){
					remarksBuffer.append(flow.getRemarks());
				}else{
					remarksBuffer.append("INITIAL CREDIT LIMIT");
				}
			}
		}
		creditLimit.setRemarks(remarksBuffer.toString());
		creditLimit.setSatbUser(flows.last().getSatbUser());
		acct.getAmtbAcctCredLimits().add(creditLimit);
		acct.setDeposit(app.getDeposit());
		acct.setCreditLimit(app.getCreditLimit());
		acct.setCreditBalance(app.getCreditLimit());
		acct.setAccountBalance(new BigDecimal(0));
		AmtbAcctSalesperson salesperson = new AmtbAcctSalesperson();
		salesperson.setAmtbAccount(acct);
		salesperson.setMstbSalesperson(app.getMstbSalesperson());
		salesperson.setEffectiveDtFrom(DateUtil.getCurrentTimestamp());
		acct.getAmtbAcctSalespersons().add(salesperson);
		// adding volume discount
		if(app.getMstbVolDiscMaster()!=null){
			AmtbAcctVolDisc newVolumeDiscount = new AmtbAcctVolDisc();
			newVolumeDiscount.setMstbVolDiscMaster(app.getMstbVolDiscMaster());
			newVolumeDiscount.setEffectiveDate(DateUtil.getCurrentDate());
			newVolumeDiscount.setAmtbAccount(acct);
			acct.getAmtbAcctVolDiscs().add(newVolumeDiscount);
		}
		// adding admin fee
		AmtbAcctAdminFee newAdminFee = new AmtbAcctAdminFee();
		newAdminFee.setMstbAdminFeeMaster(app.getMstbAdminFeeMaster());
		newAdminFee.setEffectiveDate(DateUtil.getCurrentDate());
		newAdminFee.setAmtbAccount(acct);
		acct.getAmtbAcctAdminFees().add(newAdminFee);
		// adding early payment
		if(app.getMstbEarlyPaymentMaster()!=null){
			AmtbAcctEarlyPymt newEarlyPymt = new AmtbAcctEarlyPymt();
			newEarlyPymt.setMstbEarlyPaymentMaster(app.getMstbEarlyPaymentMaster());
			newEarlyPymt.setEffectiveDate(DateUtil.getCurrentDate());
			newEarlyPymt.setAmtbAccount(acct);
			acct.getAmtbAcctEarlyPymts().add(newEarlyPymt);
		}
		// adding late payment
		AmtbAcctLatePymt newLatePymt = new AmtbAcctLatePymt();
		newLatePymt.setMstbLatePaymentMaster(app.getMstbLatePaymentMaster());
		newLatePymt.setEffectiveDate(DateUtil.getCurrentDate());
		newLatePymt.setAmtbAccount(acct);
		acct.getAmtbAcctLatePymts().add(newLatePymt);
		// now adding the status into it.
		AmtbAcctStatus newStatus = new AmtbAcctStatus();
		newStatus.setEffectiveDt(DateUtil.getCurrentTimestamp());
		newStatus.setAcctStatus(NonConfigurableConstants.ACCOUNT_STATUS_PENDING_ACTIVATION);
		newStatus.setAmtbAccount(acct);
		newStatus.setSatbUser(flows.last().getSatbUser());
		acct.getAmtbAcctStatuses().add(newStatus);
	}
	private void createContactPersons(AmtbAccount acct, AmtbApplication app){
		// billing contact person
		AmtbContactPerson billingContact = new AmtbContactPerson();
		if(app.getBillingSame().equals(NonConfigurableConstants.BOOLEAN_YES)){
			billingContact.setAddressArea(app.getAddressArea());
			billingContact.setAddressBlock(app.getAddressBlock());
			billingContact.setAddressBuilding(app.getAddressBuilding());
			billingContact.setAddressCity(app.getAddressCity());
			billingContact.setAddressPostal(app.getAddressPostal());
			billingContact.setAddressState(app.getAddressState());
			billingContact.setAddressStreet(app.getAddressStreet());
			billingContact.setAddressUnit(app.getAddressUnit());
			billingContact.setMstbMasterTableByAddressCountry(app.getMstbMasterTableByAddressCountry());
		}else{
			billingContact.setAddressArea(app.getBillingArea());
			billingContact.setAddressBlock(app.getBillingBlock());
			billingContact.setAddressBuilding(app.getBillingBuilding());
			billingContact.setAddressCity(app.getBillingCity());
			billingContact.setAddressPostal(app.getBillingPostal());
			billingContact.setAddressState(app.getBillingState());
			billingContact.setAddressStreet(app.getBillingStreet());
			billingContact.setAddressUnit(app.getBillingUnit());
			billingContact.setMstbMasterTableByAddressCountry(app.getMstbMasterTableByBillingCountry());
		}
		billingContact.setMainContactTitle("-");
		billingContact.setMainContactEmail(app.getEmail());
		billingContact.setMainContactMobile(app.getMobile());
		billingContact.setMainContactName(app.getApplicantName());
		billingContact.setMainContactTel(app.getOffice());
		billingContact.setMstbMasterTableByMainContactSal(app.getMstbMasterTableByAuthSal());
		billingContact.setMstbMasterTableByMainContactRace(app.getMstbMasterTableByMainContactRace());
		billingContact.setAmtbAccount(acct);
		// saving the billing contact
		this.daoHelper.getAccountDao().save(billingContact);
		AmtbAcctMainContact mainBilling = new AmtbAcctMainContact();
		mainBilling.setComp_id(new AmtbAcctMainContactPK(NonConfigurableConstants.MAIN_CONTACT_TYPE_BILLING, acct));
		mainBilling.setAmtbContactPerson(billingContact);
		this.daoHelper.getAccountDao().save(mainBilling);
		// shipping contact person
		AmtbContactPerson shippingContact = new AmtbContactPerson();
		if(app.getShippingSame().equals(NonConfigurableConstants.BOOLEAN_YES)){
			shippingContact.setAddressArea(app.getAddressArea());
			shippingContact.setAddressBlock(app.getAddressBlock());
			shippingContact.setAddressBuilding(app.getAddressBuilding());
			shippingContact.setAddressCity(app.getAddressCity());
			shippingContact.setAddressPostal(app.getAddressPostal());
			shippingContact.setAddressState(app.getAddressState());
			shippingContact.setAddressStreet(app.getAddressStreet());
			shippingContact.setAddressUnit(app.getAddressUnit());
			shippingContact.setMstbMasterTableByAddressCountry(app.getMstbMasterTableByAddressCountry());
		}else{
			shippingContact.setAddressArea(app.getShippingArea());
			shippingContact.setAddressBlock(app.getShippingBlock());
			shippingContact.setAddressBuilding(app.getShippingBuilding());
			shippingContact.setAddressCity(app.getShippingCity());
			shippingContact.setAddressPostal(app.getShippingPostal());
			shippingContact.setAddressState(app.getShippingState());
			shippingContact.setAddressStreet(app.getShippingStreet());
			shippingContact.setAddressUnit(app.getShippingUnit());
			shippingContact.setMstbMasterTableByAddressCountry(app.getMstbMasterTableByShippingCountry());
		}
		shippingContact.setMainContactTitle("-");
		shippingContact.setMainContactEmail(app.getEmail());
		shippingContact.setMainContactMobile(app.getMobile());
		shippingContact.setMainContactName(app.getApplicantName());
		shippingContact.setMainContactTel(app.getOffice());
		shippingContact.setMstbMasterTableByMainContactSal(app.getMstbMasterTableByAuthSal());
		shippingContact.setAmtbAccount(acct);
		// saving the shipping contact
		this.daoHelper.getAccountDao().save(shippingContact);
		AmtbAcctMainContact mainShipping = new AmtbAcctMainContact();
		mainShipping.setComp_id(new AmtbAcctMainContactPK(NonConfigurableConstants.MAIN_CONTACT_TYPE_SHIPPING, acct));
		mainShipping.setAmtbContactPerson(shippingContact);
		this.daoHelper.getAccountDao().save(mainShipping);
	}
	public boolean checkRCBNoExist(String rcbNo){
		return this.daoHelper.getAccountDao().checkRCBNo(rcbNo) || this.daoHelper.getApplicationDao().checkRCBNo(rcbNo);
	}
	public boolean checkCorporateNameExist(String name){
		return this.daoHelper.getAccountDao().checkCorporateName(name) || this.daoHelper.getApplicationDao().checkCorporateName(name);	
	}
	private AmtbApplicationFlow getRequestingStatus(Collection<AmtbApplicationFlow> flows){
		for(AmtbApplicationFlow flow : flows){
			if(flow.getFromStatus().equals(NonConfigurableConstants.APPLICATION_STATUS_DRAFT) && flow.getToStatus().equals(NonConfigurableConstants.APPLICATION_STATUS_PENDING_1ST_LEVEL_APPROVAL)){
				return flow;
			}
		}
		return null;
	}
	public boolean hasExternalCardSubscription(Set<String> productTypeIds){
		logger.info("hasExternalCardSubscription(productTypeId = "+productTypeIds+")");
		List<PmtbProductType> productTypes = this.daoHelper.getProductTypeDao().getAllProductType();
		for(PmtbProductType productType : productTypes){
			if(productType.getExternalCard().equals(NonConfigurableConstants.BOOLEAN_YN_YES) && productTypeIds.contains(productType.getProductTypeId())){
				List<AmtbAccount> accts = this.daoHelper.getAccountDao().getAccountSubscribedToExternalCard(productType.getProductTypeId());
				for(AmtbAccount acct : accts){
					AmtbAcctStatus currentStatus = null;
					for(AmtbAcctStatus status : acct.getAmtbAcctStatuses()){
						if(status.getEffectiveDt().before(new Date())){
							if(currentStatus == null || currentStatus.getEffectiveDt().getTime() < status.getEffectiveDt().getTime()){
								currentStatus = status;
							}
						}
					}
					if(currentStatus!=null){
						if(!currentStatus.getAcctStatus().equals(NonConfigurableConstants.ACCOUNT_STATUS_CLOSED)){
							return true;
						}
					}
				}
			}
		}
		return false;
	}
//	private AmtbApplicationFlow getRecommandingStatus(Collection<AmtbApplicationFlow> flows){
//		for(AmtbApplicationFlow flow : flows){
//			if(flow.getFromStatus().equals(NonConfigurableConstants.APPLICATION_STATUS_PENDING_1ST_LEVEL_APPROVAL) && flow.getToStatus().equals(NonConfigurableConstants.APPLICATION_STATUS_PENDING_2ND_LEVEL_APPROVAL)){
//				return flow;
//			}
//		}
//		return null;
//	}
	
	public String getInvoiceFullString(String invoice, String type)
	{
		String toReturn = "";
		
		if(type.equalsIgnoreCase("invoiceFormat"))
		{
			for(Entry<String, String> entry : NonConfigurableConstants.INVOICE_FORMAT.entrySet()){
				if(invoice.equalsIgnoreCase(entry.getKey()))
				{
					toReturn = entry.getValue();
					break;
				}
			}
		}
		else if(type.equalsIgnoreCase("invoiceSorting"))
		{
			for(Entry<String, String> entry : NonConfigurableConstants.INVOICE_SORTING.entrySet()){
				if(invoice.equalsIgnoreCase(entry.getKey()))
				{
					toReturn = entry.getValue();
					break;
				}
			}
		}
		else if(type.equalsIgnoreCase("govtEInvoice"))
		{
			for(Entry<String, String> entry : NonConfigurableConstants.GOVT_EINV_FLAGS.entrySet()){
				if(invoice.equalsIgnoreCase(entry.getKey()))
				{
					toReturn = entry.getValue();
					break;
				}
			}
		}
		else if(type.equalsIgnoreCase("businessUnit"))
		{
			for(Entry<String, String> entry : ConfigurableConstants.getBusinessUnits().entrySet()){
				
				if(invoice.equalsIgnoreCase(Integer.toString(ConfigurableConstants.getMasterTable(ConfigurableConstants.GOVT_EINVOICE_BUSINESS_UNIT_MASTER_TYPE, entry.getKey()).getMasterNo())))
				{
					toReturn = entry.getKey() + " - " +entry.getValue();
					break;
				}
			}
		}
		return toReturn;
	}
}