package com.cdgtaxi.ibs.common;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;

import com.cdgtaxi.ibs.common.exception.NoDetailLevelException;
import com.cdgtaxi.ibs.common.exception.NoMasterLevelException;
import com.cdgtaxi.ibs.common.exception.NoTierLevelException;
import com.cdgtaxi.ibs.master.dao.SetupDao;
import com.cdgtaxi.ibs.master.model.MstbEarlyPaymentDetail;
import com.cdgtaxi.ibs.master.model.MstbEarlyPaymentMaster;
import com.cdgtaxi.ibs.util.DateUtil;

public class EarlyPaymentMasterManager extends GenericMasterManager implements MasterManager {
	public static final String EARLY_PAYMENT = "EP";
	public static final String EFFECTIVE_DATE = "ED";
	private static final Map<Integer, MstbEarlyPaymentMaster> EARLY_PAYMENT_PLANS = Collections.synchronizedMap(new LinkedHashMap<Integer, MstbEarlyPaymentMaster>());
	private SetupDao setupDao;
	public Map<Integer, Map<String, String>> getAllDetails(Integer masterNo){
		// getting the master early payment plan
		MstbEarlyPaymentMaster eppm = EARLY_PAYMENT_PLANS.get(masterNo);
		if(eppm!=null){
			Map<Integer, Map<String, String>> returnMap = new LinkedHashMap<Integer, Map<String,String>>();
			for(MstbEarlyPaymentDetail eppd : eppm.getMstbEarlyPaymentDetails()){
				Map<String, String> detailMap = new LinkedHashMap<String, String>();
				detailMap.put(EARLY_PAYMENT, eppd.getEarlyPayment()+"%");
				detailMap.put(EFFECTIVE_DATE, DateUtil.convertTimestampToStr(eppd.getEffectiveDt(), DateUtil.GLOBAL_DATE_FORMAT));
				returnMap.put(eppd.getEarlyPaymentPlanDetailNo(), detailMap);
			}
			return sortDetails(returnMap);
		}else{
			return null;
		}
	}
	public Map<Integer, Map<String, String>> getCurrentDetail(Integer masterNo) throws NoDetailLevelException{
		MstbEarlyPaymentDetail eppd = getCurrentEarlyPaymentDetail(masterNo);
		if(eppd!=null){
			Map<Integer, Map<String, String>> returnMap = new LinkedHashMap<Integer, Map<String,String>>();
			Map<String, String> detailMap = new LinkedHashMap<String, String>();
			detailMap.put(EARLY_PAYMENT, eppd.getEarlyPayment()+"%");
			detailMap.put(EFFECTIVE_DATE, DateUtil.convertTimestampToStr(eppd.getEffectiveDt(), DateUtil.GLOBAL_DATE_FORMAT));
			returnMap.put(eppd.getEarlyPaymentPlanDetailNo(), detailMap);
			return returnMap;
		}else{
			return null;
		}
	}
	public BigDecimal getCurrentEarlyPayment(Integer masterNo){
		MstbEarlyPaymentDetail currentEarlyPaymentDetail = getCurrentEarlyPaymentDetail(masterNo);
		return currentEarlyPaymentDetail == null ? null : currentEarlyPaymentDetail.getEarlyPayment();
	}
	private MstbEarlyPaymentDetail getCurrentEarlyPaymentDetail(Integer masterNo){
		MstbEarlyPaymentMaster eppm = EARLY_PAYMENT_PLANS.get(masterNo);
		if(eppm!=null){
			TreeSet<MstbEarlyPaymentDetail> details = new TreeSet<MstbEarlyPaymentDetail>(new Comparator<MstbEarlyPaymentDetail>(){
				public int compare(MstbEarlyPaymentDetail ep1, MstbEarlyPaymentDetail ep2) {
					return ep1.getEffectiveDt().compareTo(ep2.getEffectiveDt());
				}
			});
			for(MstbEarlyPaymentDetail eppd : eppm.getMstbEarlyPaymentDetails()){
				if(eppd.getEffectiveDt().before(Calendar.getInstance().getTime())){
					details.add(eppd);
				}
			}
			return details.size()==0 ? null : details.last();
		}else{
			return null;
		}
	}
	public Object getDetail(Integer detailNo){
		for(Integer earlyPaymentNo : EARLY_PAYMENT_PLANS.keySet()){
			MstbEarlyPaymentMaster eppm = EARLY_PAYMENT_PLANS.get(earlyPaymentNo);
			for(MstbEarlyPaymentDetail eppd : eppm.getMstbEarlyPaymentDetails()){
				if(eppd.getEarlyPaymentPlanDetailNo().equals(detailNo)){
					return eppd;
				}
			}
		}
		return null;
	}
	public Map<Integer, String> getAllMasters(){
		Map<Integer, String> returnMap = new LinkedHashMap<Integer, String>();
		for(Integer earlyPaymentPlanNo : EARLY_PAYMENT_PLANS.keySet()){
			returnMap.put(earlyPaymentPlanNo, EARLY_PAYMENT_PLANS.get(earlyPaymentPlanNo).getEarlyPaymentPlanName());
		}
		return sortMasters(returnMap);
	}
	public Object getMaster(Integer masterNo) throws NoMasterLevelException{
		for(Integer earlyPaymentNo : EARLY_PAYMENT_PLANS.keySet()){
			if(earlyPaymentNo.equals(masterNo)){
				return EARLY_PAYMENT_PLANS.get(earlyPaymentNo);
			}
		}
		return null;
	}
	public Map<Integer, Map<String, String>> getAllTiers(Integer detailNo) throws NoTierLevelException {
		throw new NoTierLevelException("No Tiers found for Early Payment Master");
	}
	private void clear() {
		EARLY_PAYMENT_PLANS.clear();
	}
	public void init() {
		// init early payment
		List<MstbEarlyPaymentMaster> earlyPaymentPlans = this.setupDao.getAllEarlyPaymentPlans();
		for(MstbEarlyPaymentMaster earlyPaymentPlan : earlyPaymentPlans){
			EARLY_PAYMENT_PLANS.put(earlyPaymentPlan.getEarlyPaymentPlanNo(), earlyPaymentPlan);
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
}