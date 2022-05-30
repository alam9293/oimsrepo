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
import com.cdgtaxi.ibs.master.model.MstbLatePaymentDetail;
import com.cdgtaxi.ibs.master.model.MstbLatePaymentMaster;
import com.cdgtaxi.ibs.util.DateUtil;

public class LatePaymentMasterManager extends GenericMasterManager implements MasterManager {
	public static final String LATE_PAYMENT = "LP";
	public static final String EFFECTIVE_DATE = "ED";
	private static final Map<Integer, MstbLatePaymentMaster> LATE_PAYMENT_PLANS	= Collections.synchronizedMap(new LinkedHashMap<Integer, MstbLatePaymentMaster>());
	private SetupDao setupDao;
	public Map<Integer, Map<String, String>> getAllDetails(Integer masterNo){
		MstbLatePaymentMaster lppm = LATE_PAYMENT_PLANS.get(masterNo);
		if(lppm!=null){
			Map<Integer, Map<String, String>> returnMap = new LinkedHashMap<Integer, Map<String,String>>();
			for(MstbLatePaymentDetail lppd : lppm.getMstbLatePaymentDetails()){
				Map<String, String> detailMap = new LinkedHashMap<String, String>();
				detailMap.put(LATE_PAYMENT, lppd.getLatePayment()+"%");
				detailMap.put(EFFECTIVE_DATE, DateUtil.convertTimestampToStr(lppd.getEffectiveDt(), DateUtil.GLOBAL_DATE_FORMAT));
				returnMap.put(lppd.getLatePaymentPlanDetailNo(), detailMap);
			}
			return sortDetails(returnMap);
		}else{
			return null;
		}
	}
	public Map<Integer, Map<String, String>> getCurrentDetail(Integer masterNo) throws NoDetailLevelException {
		MstbLatePaymentDetail lppd = getCurrentLatePaymentDetail(masterNo);
		if(lppd!=null){
			Map<Integer, Map<String, String>> returnMap = new LinkedHashMap<Integer, Map<String,String>>();
			Map<String, String> detailMap = new LinkedHashMap<String, String>();
			detailMap.put(LATE_PAYMENT, lppd.getLatePayment()+"%");
			detailMap.put(EFFECTIVE_DATE, DateUtil.convertTimestampToStr(lppd.getEffectiveDt(), DateUtil.GLOBAL_DATE_FORMAT));
			returnMap.put(lppd.getLatePaymentPlanDetailNo(), detailMap);
			return returnMap;
		}else{
			return null;
		}
	}
	public BigDecimal getCurrentLatePayment(Integer masterNo){
		MstbLatePaymentDetail currentLatePaymentDetail = getCurrentLatePaymentDetail(masterNo);
		return currentLatePaymentDetail == null ? null : currentLatePaymentDetail.getLatePayment();
	}
	private MstbLatePaymentDetail getCurrentLatePaymentDetail(Integer masterNo){
		MstbLatePaymentMaster lppm = LATE_PAYMENT_PLANS.get(masterNo);
		if(lppm!=null){
			TreeSet<MstbLatePaymentDetail> details = new TreeSet<MstbLatePaymentDetail>(new Comparator<MstbLatePaymentDetail>(){
				public int compare(MstbLatePaymentDetail lp1, MstbLatePaymentDetail lp2) {
					return lp1.getEffectiveDt().compareTo(lp2.getEffectiveDt());
				}				
			});
			for(MstbLatePaymentDetail lppd : lppm.getMstbLatePaymentDetails()){
				if(lppd.getEffectiveDt().before(Calendar.getInstance().getTime())){
					details.add(lppd);
				}
			}
			return details.size()==0 ? null : details.last();
		}else{
			return null;
		}
	}
	public Object getDetail(Integer detailNo){
		for(Integer latePaymentNo : LATE_PAYMENT_PLANS.keySet()){
			MstbLatePaymentMaster lppm = LATE_PAYMENT_PLANS.get(latePaymentNo);
			for(MstbLatePaymentDetail lppd : lppm.getMstbLatePaymentDetails()){
				if(lppd.getLatePaymentPlanDetailNo().equals(detailNo)){
					return lppd;
				}
			}
		}
		return null;
	}
	public Map<Integer, String> getAllMasters() {
		Map<Integer, String> returnMap = new LinkedHashMap<Integer, String>();
		for(Integer latePaymentPlanNo : LATE_PAYMENT_PLANS.keySet()){
			returnMap.put(latePaymentPlanNo, LATE_PAYMENT_PLANS.get(latePaymentPlanNo).getLatePaymentPlanName());
		}
		return sortMasters(returnMap);
	}
	public Object getMaster(Integer masterNo) throws NoMasterLevelException{
		for(Integer latePaymentNo : LATE_PAYMENT_PLANS.keySet()){
			if(latePaymentNo.equals(masterNo)){
				return LATE_PAYMENT_PLANS.get(latePaymentNo);
			}
		}
		return null;
	}
	public Map<Integer, Map<String, String>> getAllTiers(Integer detailNo) throws NoTierLevelException {
		throw new NoTierLevelException("No Tiers found for Late Payment Master");
	}
	public void init() {
		// init late payment
		List<MstbLatePaymentMaster> latePaymentPlans = this.setupDao.getAllLatePaymentPlans();
		for(MstbLatePaymentMaster latePaymentPlan : latePaymentPlans){
			LATE_PAYMENT_PLANS.put(latePaymentPlan.getLatePaymentPlanNo(), latePaymentPlan);
		}
	}
	public void refresh() {
		clear();
		init();
	}
	private void clear(){
		LATE_PAYMENT_PLANS.clear();
	}
	public SetupDao getSetupDao() {
		return setupDao;
	}
	public void setSetupDao(SetupDao setupDao) {
		this.setupDao = setupDao;
	}
}
