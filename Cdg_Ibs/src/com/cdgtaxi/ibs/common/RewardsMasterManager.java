package com.cdgtaxi.ibs.common;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;

import com.cdgtaxi.ibs.common.exception.NoDetailLevelException;
import com.cdgtaxi.ibs.common.exception.NoMasterLevelException;
import com.cdgtaxi.ibs.common.exception.NoTierLevelException;
import com.cdgtaxi.ibs.master.dao.SetupDao;
import com.cdgtaxi.ibs.master.model.LrtbRewardDetail;
import com.cdgtaxi.ibs.master.model.LrtbRewardMaster;
import com.cdgtaxi.ibs.master.model.LrtbRewardTier;
import com.cdgtaxi.ibs.util.DateUtil;

public class RewardsMasterManager extends GenericMasterManager implements MasterManager {
	public static final String DETAIL_REWARDS_TYPE = "AF";
	public static final String DETAIL_EFFECTIVE_FROM = "EF";
	public static final String DETAIL_EFFECTIVE_TO = "ET";
	public static final String TIER_START_RANGE = "SR";
	public static final String TIER_END_RANGE = "ER";
	public static final String TIER_POINTS_PER_VALUE = "PV";
	private static final Map<Integer, LrtbRewardMaster> REWARDS_PLANS = Collections.synchronizedMap(new LinkedHashMap<Integer, LrtbRewardMaster>());
	private SetupDao setupDao;
	public Map<Integer, Map<String, String>> getAllDetails(Integer masterNo) {
		LrtbRewardMaster rpm = REWARDS_PLANS.get(masterNo);
		if(rpm!=null){
			Map<Integer, Map<String, String>> returnMap = new LinkedHashMap<Integer, Map<String,String>>();
			for(LrtbRewardDetail rpd : rpm.getLrtbRewardDetails()){
				Map<String, String> detailMap = new LinkedHashMap<String, String>();
				detailMap.put(DETAIL_REWARDS_TYPE, rpd.getRewardType());
				detailMap.put(DETAIL_EFFECTIVE_FROM, DateUtil.convertTimestampToStr(rpd.getEffectiveDtFrom(), DateUtil.GLOBAL_DATE_FORMAT));
				detailMap.put(DETAIL_EFFECTIVE_TO, DateUtil.convertTimestampToStr(rpd.getEffectiveDtTo(), DateUtil.GLOBAL_DATE_FORMAT));
				returnMap.put(rpd.getRewardPlanDetailNo(), detailMap);
			}
			return sortDetails(returnMap);
		}else{
			return null;
		}
	}
	@SuppressWarnings("unchecked")
	public Map<Integer, Map<String, String>> getCurrentDetail(Integer masterNo) throws NoDetailLevelException {
		LrtbRewardDetail rpd = getCurrentRewardsDetail(masterNo);
		if(rpd!=null){
			Map<Integer, Map<String, String>> returnMap = new LinkedHashMap<Integer, Map<String,String>>();
			Map<String, String> detailMap = new LinkedHashMap<String, String>();
			detailMap.put(DETAIL_REWARDS_TYPE, rpd.getRewardType());
			detailMap.put(DETAIL_EFFECTIVE_FROM, DateUtil.convertTimestampToStr(rpd.getEffectiveDtFrom(), DateUtil.GLOBAL_DATE_FORMAT));
			detailMap.put(DETAIL_EFFECTIVE_TO, DateUtil.convertTimestampToStr(rpd.getEffectiveDtTo(), DateUtil.GLOBAL_DATE_FORMAT));
			returnMap.put(rpd.getRewardPlanDetailNo(), detailMap);
			return returnMap;
		}else{
			return new HashMap();
		}
	}
	public Integer getCurrentRewards(Integer masterNo, BigDecimal rangeInput){
		LrtbRewardDetail currentRewardsDetail = getCurrentRewardsDetail(masterNo);
		if(currentRewardsDetail!=null){
			// now getting the tiers
			for(LrtbRewardTier rpt : currentRewardsDetail.getLrtbRewardTiers()){
				// if start range is equals to or lesser than the input
				// if end range is equals to or greater than the input
				if(rpt.getStartRange().compareTo(rangeInput)>=0 && rpt.getEndRange().compareTo(rangeInput)<=0){
					return rpt.getPtsPerValue();
				}
			}
			return null;
		}else{
			return null;
		}
	}
	private LrtbRewardDetail getCurrentRewardsDetail(Integer masterNo){
		LrtbRewardMaster rpm = REWARDS_PLANS.get(masterNo);
		if(rpm!=null){
			TreeSet<LrtbRewardDetail> details = new TreeSet<LrtbRewardDetail>(new Comparator<LrtbRewardDetail>(){
				public int compare(LrtbRewardDetail r1, LrtbRewardDetail r2) {
					return r1.getEffectiveDtTo().compareTo(r2.getEffectiveDtTo());
				}				
			});
			for(LrtbRewardDetail rpd : rpm.getLrtbRewardDetails()){
				// if effective date from is before today and effective date to is after today.
				// means is still in effective
				if(rpd.getEffectiveDtFrom().before(Calendar.getInstance().getTime()) && rpd.getEffectiveDtTo().after(Calendar.getInstance().getTime())){
					details.add(rpd);
				}
			}
			return details.size()==0 ? null : details.last();
		}else{
			return null;
		}
	}
	public Object getDetail(Integer detailNo){
		for(Integer rewardsNo : REWARDS_PLANS.keySet()){
			LrtbRewardMaster rpm = REWARDS_PLANS.get(rewardsNo);
			for(LrtbRewardDetail rpd : rpm.getLrtbRewardDetails()){
				if(rpd.getRewardPlanDetailNo().equals(detailNo)){
					return rpd;
				}
			}
		}
		return null;
	}
	public Map<Integer, String> getAllMasters() {
		Map<Integer, String> returnMap = new LinkedHashMap<Integer, String>();
		for(Integer masterNo : REWARDS_PLANS.keySet()){
			returnMap.put(masterNo, REWARDS_PLANS.get(masterNo).getRewardPlanName());
		}
		return sortMasters(returnMap);
	}
	public Object getMaster(Integer masterNo) throws NoMasterLevelException{
		for(Integer rewardsNo : REWARDS_PLANS.keySet()){
			if(rewardsNo.equals(masterNo)){
				return REWARDS_PLANS.get(rewardsNo);
			}
		}
		return null;
	}
	public Map<Integer, Map<String, String>> getAllTiers(Integer detailNo) throws NoTierLevelException {
		// looping thru all master plan
		Map<Integer, Map<String, String>> returnMap = null;
		boolean found = false;
		for(Integer masterNo : REWARDS_PLANS.keySet()){
			LrtbRewardMaster rpm = REWARDS_PLANS.get(masterNo);
			// looping thru all details
			for(LrtbRewardDetail rpd : rpm.getLrtbRewardDetails()){
				if(rpd.getRewardPlanDetailNo().equals(detailNo)){
					returnMap = new LinkedHashMap<Integer, Map<String, String>>();
					// now looping thru all tiers
					for(LrtbRewardTier rpt : rpd.getLrtbRewardTiers()){
						Map<String, String> tierMap = new LinkedHashMap<String, String>();
						tierMap.put(TIER_START_RANGE, ""+rpt.getStartRange());
						tierMap.put(TIER_END_RANGE, ""+rpt.getEndRange());
						tierMap.put(TIER_POINTS_PER_VALUE, rpt.getPtsPerValue()+"");
						returnMap.put(rpt.getRewardPlanTierNo(), tierMap);
					}
					returnMap = sortTiers(returnMap);
					found = true;
					break;
				}
			}
			if(found){
				break;
			}
		}
		return returnMap;
	}
	public void init() {
		// init rewards plans
		List<LrtbRewardMaster> rewardsPlans = this.setupDao.getAllRewardsPlans();
		for(LrtbRewardMaster rewardsPlan : rewardsPlans){
			REWARDS_PLANS.put(rewardsPlan.getRewardPlanNo(), rewardsPlan);
		}
	}
	public void refresh() {
		clear();
		init();
	}
	private void clear(){
		REWARDS_PLANS.clear();
	}
	public SetupDao getSetupDao() {
		return setupDao;
	}
	public void setSetupDao(SetupDao setupDao) {
		this.setupDao = setupDao;
	}
}