package com.cdgtaxi.ibs.common;

import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeSet;

import com.cdgtaxi.ibs.common.exception.NoDetailLevelException;
import com.cdgtaxi.ibs.common.exception.NoMasterLevelException;
import com.cdgtaxi.ibs.common.exception.NoTierLevelException;
import com.cdgtaxi.ibs.master.dao.SetupDao;
import com.cdgtaxi.ibs.master.model.MstbCreditTermDetail;
import com.cdgtaxi.ibs.master.model.MstbCreditTermMaster;
import com.cdgtaxi.ibs.util.DateUtil;
import com.google.common.collect.Maps;

public class CreditTermMasterManager extends GenericMasterManager implements MasterManager {
	public static final String MASTER_NAME = "MN";
	public static final String DETAIL_CREDIT_TERM = "CT";
	public static final String DETAIL_EFFECTIVE_DATE = "ED";
	private static final Map<Integer, MstbCreditTermMaster> CREDIT_TERM_PLANS = Collections.synchronizedMap(new LinkedHashMap<Integer, MstbCreditTermMaster>());
	private SetupDao setupDao;
	public Map<Integer, Map<String, String>> getAllDetails(Integer masterNo){
		// getting the master early payment plan
		MstbCreditTermMaster ctpm = CREDIT_TERM_PLANS.get(masterNo);
		if(ctpm!=null){
			Map<Integer, Map<String, String>> returnMap = new LinkedHashMap<Integer, Map<String,String>>();
			for(MstbCreditTermDetail ctpd : ctpm.getMstbCreditTermDetails()){
				Map<String, String> detailMap = new LinkedHashMap<String, String>();
				detailMap.put(DETAIL_CREDIT_TERM, ctpd.getCreditTerm()+"");
				detailMap.put(DETAIL_EFFECTIVE_DATE, DateUtil.convertTimestampToStr(ctpd.getEffectiveDt(), DateUtil.GLOBAL_DATE_FORMAT));
				returnMap.put(ctpd.getMstbCreditTermDetailNo(), detailMap);
			}
			return sortDetails(returnMap);
		}else{
			return null;
		}
	}
	public Map<Integer, Map<String, String>> getCurrentDetail(Integer masterNo) throws NoDetailLevelException{
		MstbCreditTermDetail ctpd = getCurrentCreditTermDetail(masterNo);
		if(ctpd!=null){
			Map<Integer, Map<String, String>> returnMap = new LinkedHashMap<Integer, Map<String,String>>();
			Map<String, String> detailMap = new LinkedHashMap<String, String>();
			detailMap.put(DETAIL_CREDIT_TERM, ctpd.getCreditTerm()+"");
			detailMap.put(DETAIL_EFFECTIVE_DATE, DateUtil.convertTimestampToStr(ctpd.getEffectiveDt(), DateUtil.GLOBAL_DATE_FORMAT));
			returnMap.put(ctpd.getMstbCreditTermDetailNo(), detailMap);
			return returnMap;
		}else{
			return null;
		}
	}

	public Map<Integer, Map<String, String>> getCurrentDetails() throws NoDetailLevelException {
		Map<Integer, Map<String, String>> returnMap =
			new LinkedHashMap<Integer, Map<String,String>>();
		for (Integer masterNo : getAllMasters().keySet()) {
			MstbCreditTermDetail ctpd = getCurrentCreditTermDetail(masterNo);
			if(ctpd != null){
				Map<String, String> detailMap = new LinkedHashMap<String, String>();
				detailMap.put(MASTER_NAME, ctpd.getMstbCreditTermMaster().getCreditTermPlanName());
				detailMap.put(DETAIL_CREDIT_TERM, ctpd.getCreditTerm()+"");
				detailMap.put(DETAIL_EFFECTIVE_DATE, DateUtil.convertTimestampToStr(ctpd.getEffectiveDt(), DateUtil.GLOBAL_DATE_FORMAT));
				returnMap.put(ctpd.getMstbCreditTermDetailNo(), detailMap);
			}
		}
		return returnMap;
	}

	public Integer getCurrentCreditTerm(Integer masterNo){
		MstbCreditTermDetail currentCreditTermDetail = getCurrentCreditTermDetail(masterNo);
		return currentCreditTermDetail == null ? null : currentCreditTermDetail.getCreditTerm();
	}
	private MstbCreditTermDetail getCurrentCreditTermDetail(Integer masterNo){
		MstbCreditTermMaster ctpm = CREDIT_TERM_PLANS.get(masterNo);
		if(ctpm!=null){
			TreeSet<MstbCreditTermDetail> details = new TreeSet<MstbCreditTermDetail>(new Comparator<MstbCreditTermDetail>(){
				public int compare(MstbCreditTermDetail ct1,MstbCreditTermDetail ct2) {
					return ct1.getEffectiveDt().compareTo(ct2.getEffectiveDt());
				}
			});
			for(MstbCreditTermDetail ctpd : ctpm.getMstbCreditTermDetails()){
				if(ctpd.getEffectiveDt().before(Calendar.getInstance().getTime())){
					details.add(ctpd);
				}
			}
			return details.size()==0 ? null : details.last();
		}else{
			return null;
		}
	}
	public Object getDetail(Integer detailNo){
		for(Integer earlyPaymentNo : CREDIT_TERM_PLANS.keySet()){
			MstbCreditTermMaster ctpm = CREDIT_TERM_PLANS.get(earlyPaymentNo);
			for(MstbCreditTermDetail ctpd : ctpm.getMstbCreditTermDetails()){
				if(ctpd.getMstbCreditTermDetailNo().equals(detailNo)){
					return ctpd;
				}
			}
		}
		return null;
	}
	public Map<Integer, String> getAllMasters(){
		Map<Integer, String> returnMap = new LinkedHashMap<Integer, String>();
		for(Integer creditTermPlanNo : CREDIT_TERM_PLANS.keySet()){
			returnMap.put(creditTermPlanNo, CREDIT_TERM_PLANS.get(creditTermPlanNo).getCreditTermPlanName());
		}
		return sortMasters(returnMap);
	}
	public Object getMaster(Integer masterNo) throws NoMasterLevelException{
		for(Integer creditTermNo : CREDIT_TERM_PLANS.keySet()){
			if(creditTermNo.equals(masterNo)){
				return CREDIT_TERM_PLANS.get(creditTermNo);
			}
		}
		return null;
	}
	public Map<Integer, Map<String, String>> getAllTiers(Integer detailNo) throws NoTierLevelException {
		throw new NoTierLevelException("No Tiers found for Early Payment Master");
	}
	private void clear() {
		CREDIT_TERM_PLANS.clear();
	}
	public void init() {
		// init credit term
		List<MstbCreditTermMaster> creditTermPlans = this.setupDao.getAllCreditTerms();
		for(MstbCreditTermMaster creditTermPlan : creditTermPlans){
			CREDIT_TERM_PLANS.put(creditTermPlan.getCreditTermPlanNo(), creditTermPlan);
		}
	}
	public void refresh() {
		clear();
		init();
	}
	public SetupDao getSetupDao() {
		return setupDao;
	}
	public void setSetupDao(SetupDao setupDao) {
		this.setupDao = setupDao;
	}
	
	public Map<MstbCreditTermMaster, String> getAllMastersWithCOD(){
		Map<Integer, String> creditTermMasters = MasterSetup.getCreditTermManager().getAllMasters();
		
		Map<MstbCreditTermMaster, String> creditTermMap = Maps.newLinkedHashMap();
		creditTermMap.put(null, NonConfigurableConstants.COD_DESC);
		for(Entry<Integer, String> entry: creditTermMasters.entrySet()){
			creditTermMap.put((MstbCreditTermMaster)MasterSetup.getCreditTermManager().getMaster(entry.getKey()), entry.getValue());
		}
		
		return creditTermMap;
	}
	
	
}