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
import com.cdgtaxi.ibs.master.model.MstbSubscFeeDetail;
import com.cdgtaxi.ibs.master.model.MstbSubscFeeMaster;
import com.cdgtaxi.ibs.util.DateUtil;

public class SubscriptionMasterManager extends GenericMasterManager implements MasterManager {
	private static final Map<Integer, MstbSubscFeeMaster> SUBSCRIPTION_PLANS	= Collections.synchronizedMap(new LinkedHashMap<Integer, MstbSubscFeeMaster>());
	public static final String DETAIL_SUBSCRIPTION_FEE = "SF";
	public static final String DETAIL_EFFECTIVE_DATE = "ED";
	public static final String DETAIL_RECURRING_PERIOD = "RP";
	private SetupDao setupDao;
	public Map<Integer, Map<String, String>> getAllDetails(Integer masterNo) {
		// getting the master subscription fee plan
		MstbSubscFeeMaster sfpm = SUBSCRIPTION_PLANS.get(masterNo);
		if(sfpm!=null){
			Map<Integer, Map<String, String>> returnMap = new LinkedHashMap<Integer, Map<String,String>>();
			for(MstbSubscFeeDetail sfpd : sfpm.getMstbSubscFeeDetails()){
				Map<String, String> detailMap = new LinkedHashMap<String, String>();
				detailMap.put(DETAIL_SUBSCRIPTION_FEE, ""+sfpd.getSubscriptionFee());
				detailMap.put(DETAIL_EFFECTIVE_DATE, DateUtil.convertTimestampToStr(sfpd.getEffectiveDt(), DateUtil.GLOBAL_DATE_FORMAT));
				detailMap.put(DETAIL_RECURRING_PERIOD, ""+sfpd.getRecurringPeriod());
				returnMap.put(sfpd.getSubscriptionFeeDetailNo(), detailMap);
			}
			return sortDetails(returnMap);
		}else{
			return null;
		}
	}
	public Map<Integer, Map<String, String>> getCurrentDetail(Integer masterNo) throws NoDetailLevelException {
		MstbSubscFeeDetail sfpd = getCurrentSubscriptionFeeDetail(masterNo);
		if(sfpd!=null){
			Map<Integer, Map<String, String>> returnMap = new LinkedHashMap<Integer, Map<String,String>>();
			Map<String, String> detailMap = new LinkedHashMap<String, String>();
			detailMap.put(DETAIL_SUBSCRIPTION_FEE, ""+sfpd.getSubscriptionFee());
			detailMap.put(DETAIL_EFFECTIVE_DATE, DateUtil.convertTimestampToStr(sfpd.getEffectiveDt(), DateUtil.GLOBAL_DATE_FORMAT));
			returnMap.put(sfpd.getSubscriptionFeeDetailNo(), detailMap);
			return returnMap;
		}else{
			return null;
		}
	}
	public BigDecimal getCurrentSubscriptionFee(Integer masterNo){
		MstbSubscFeeDetail currentSubscriptionFeeDetail = getCurrentSubscriptionFeeDetail(masterNo);
		return currentSubscriptionFeeDetail == null ? null : currentSubscriptionFeeDetail.getSubscriptionFee();
	}
	private MstbSubscFeeDetail getCurrentSubscriptionFeeDetail(Integer masterNo){
		MstbSubscFeeMaster sfpm = SUBSCRIPTION_PLANS.get(masterNo);
		if(sfpm!=null){
			TreeSet<MstbSubscFeeDetail> details = new TreeSet<MstbSubscFeeDetail>(new Comparator<MstbSubscFeeDetail>(){
				public int compare(MstbSubscFeeDetail sf1, MstbSubscFeeDetail sf2) {
					return sf1.getEffectiveDt().compareTo(sf2.getEffectiveDt());
				}
			});
			for(MstbSubscFeeDetail sfpd : sfpm.getMstbSubscFeeDetails()){
				if(sfpd.getEffectiveDt().before(Calendar.getInstance().getTime())){
					details.add(sfpd);
				}
			}
			return details.size()==0 ? null : details.last();
		}else{
			return null;
		}
	}
	public Object getDetail(Integer detailNo){
		for(Integer subscriptionNo : SUBSCRIPTION_PLANS.keySet()){
			MstbSubscFeeMaster sfm = SUBSCRIPTION_PLANS.get(subscriptionNo);
			for(MstbSubscFeeDetail sfd : sfm.getMstbSubscFeeDetails()){
				if(sfd.getSubscriptionFeeDetailNo().equals(detailNo)){
					return sfd;
				}
			}
		}
		return null;
	}
	public Map<Integer, String> getAllMasters() {
		Map<Integer, String> returnMap = new LinkedHashMap<Integer, String>();
		for(Integer subscriptionFeePlanNo : SUBSCRIPTION_PLANS.keySet()){
			returnMap.put(subscriptionFeePlanNo, SUBSCRIPTION_PLANS.get(subscriptionFeePlanNo).getSubscriptionFeeName());
		}
		return sortMasters(returnMap);
	}
	public Object getMaster(Integer masterNo) throws NoMasterLevelException{
		for(Integer subscriptionNo : SUBSCRIPTION_PLANS.keySet()){
			if(subscriptionNo.equals(masterNo)){
				return SUBSCRIPTION_PLANS.get(subscriptionNo);
			}
		}
		return null;
	}
	public Map<Integer, Map<String, String>> getAllTiers(Integer detailNo) throws NoTierLevelException {
		throw new NoTierLevelException("No Tiers found for Subscription Fee Master");
	}
	public void init() {
		// init subscription fee
		List<MstbSubscFeeMaster> subscriptionFees = this.setupDao.getAllSubscriptionFees();
		for(MstbSubscFeeMaster subscriptionFee : subscriptionFees){
			SUBSCRIPTION_PLANS.put(subscriptionFee.getSubscriptionFeeNo(), subscriptionFee);
		}
	}
	public void refresh() {
		clear();
		init();
	}
	private void clear(){
		SUBSCRIPTION_PLANS.clear();
	}
	public SetupDao getSetupDao() {
		return setupDao;
	}
	public void setSetupDao(SetupDao setupDao) {
		this.setupDao = setupDao;
	}
}