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
import com.cdgtaxi.ibs.master.model.MstbVolDiscDetail;
import com.cdgtaxi.ibs.master.model.MstbVolDiscMaster;
import com.cdgtaxi.ibs.master.model.MstbVolDiscTier;
import com.cdgtaxi.ibs.util.DateUtil;

public class VolumeDiscountMasterManager extends GenericMasterManager implements MasterManager {
	public static final String DETAIL_DISCOUNT_TYPE = "DT";
	public static final String DETAIL_EFFECTIVE_DATE = "ED";
	public static final String TIER_START_RANGE = "SR";
	public static final String TIER_END_RANGE = "ER";
	public static final String TIER_VOLUME_DISCOUNT = "VD";
	private static final Map<Integer, MstbVolDiscMaster>	VOLUME_DISCOUNT_PLANS = Collections.synchronizedMap(new LinkedHashMap<Integer, MstbVolDiscMaster>());
	private SetupDao setupDao;
	public Map<Integer, Map<String, String>> getAllDetails(Integer masterNo) {
		// getting the master admin fee plan
		MstbVolDiscMaster vdpm = VOLUME_DISCOUNT_PLANS.get(masterNo);
		if(vdpm!=null){
			Map<Integer, Map<String, String>> returnMap = new LinkedHashMap<Integer, Map<String,String>>();
			for(MstbVolDiscDetail vdpd : vdpm.getMstbVolDiscDetails()){
				Map<String, String> detailMap = new LinkedHashMap<String, String>();
				detailMap.put(DETAIL_DISCOUNT_TYPE, vdpd.getVolumeDiscountType());
				detailMap.put(DETAIL_EFFECTIVE_DATE, DateUtil.convertTimestampToStr(vdpd.getEffectiveDt(), DateUtil.GLOBAL_DATE_FORMAT));
				returnMap.put(vdpd.getVolumeDiscountPlanDetailNo(), detailMap);
			}
			return sortDetails(returnMap);
		}else{
			return null;
		}
	}
	public Map<Integer, Map<String, String>> getCurrentDetail(Integer masterNo) throws NoDetailLevelException {
		MstbVolDiscDetail vdpd = getCurrentVolumeDiscountDetail(masterNo);
		if(vdpd!=null){
			Map<Integer, Map<String, String>> returnMap = new LinkedHashMap<Integer, Map<String,String>>();
			Map<String, String> detailMap = new LinkedHashMap<String, String>();
			detailMap.put(DETAIL_DISCOUNT_TYPE, vdpd.getVolumeDiscountType());
			detailMap.put(DETAIL_EFFECTIVE_DATE, DateUtil.convertTimestampToStr(vdpd.getEffectiveDt(), DateUtil.GLOBAL_DATE_FORMAT));
			returnMap.put(vdpd.getVolumeDiscountPlanDetailNo(), detailMap);
			return returnMap;
		}else{
			return null;
		}
	}
	public BigDecimal getCurrentVolumeDiscount(Integer masterNo, BigDecimal rangeInput){
		MstbVolDiscDetail currentVolumeDiscountDetail = getCurrentVolumeDiscountDetail(masterNo);
		if(currentVolumeDiscountDetail!=null){
			for(MstbVolDiscTier vdpt : currentVolumeDiscountDetail.getMstbVolDiscTiers()){
				// if it is within the range
				if(vdpt.getStartRange().compareTo(rangeInput)>=0 && vdpt.getEndRange().compareTo(rangeInput)<=0){
					return vdpt.getVolumeDiscount();
				}
			}
			return null;
		}else{
			return null;
		}
	}
	public MstbVolDiscDetail getCurrentVolumeDiscountDetail(Integer masterNo){
		MstbVolDiscMaster vdpm = VOLUME_DISCOUNT_PLANS.get(masterNo);
		if(vdpm!=null){
			TreeSet<MstbVolDiscDetail> details = new TreeSet<MstbVolDiscDetail>(new Comparator<MstbVolDiscDetail>(){
				public int compare(MstbVolDiscDetail vd1, MstbVolDiscDetail vd2) {
					return vd1.getEffectiveDt().compareTo(vd2.getEffectiveDt());
				}
			});
			for(MstbVolDiscDetail vdpd : vdpm.getMstbVolDiscDetails()){
				if(vdpd.getEffectiveDt().before(Calendar.getInstance().getTime())){
					details.add(vdpd);
				}
			}
			return details.size()==0 ? null : details.last();
		}else{
			return null;
		}
	}
	public Object getDetail(Integer detailNo){
		for(Integer volumeDiscountNo : VOLUME_DISCOUNT_PLANS.keySet()){
			MstbVolDiscMaster vdpm = VOLUME_DISCOUNT_PLANS.get(volumeDiscountNo);
			for(MstbVolDiscDetail vdpd : vdpm.getMstbVolDiscDetails()){
				if(vdpd.getVolumeDiscountPlanDetailNo().equals(detailNo)){
					return vdpd;
				}
			}
		}
		return null;
	}
	public Map<Integer, String> getAllMasters() {
		Map<Integer, String> returnMap = new LinkedHashMap<Integer, String>();
		for(Integer masterNo : VOLUME_DISCOUNT_PLANS.keySet()){
			returnMap.put(masterNo, VOLUME_DISCOUNT_PLANS.get(masterNo).getVolumeDiscountPlanName());
		}
		return sortMasters(returnMap);
	}
	public Object getMaster(Integer masterNo) throws NoMasterLevelException{
		for(Integer volumeDiscountNo : VOLUME_DISCOUNT_PLANS.keySet()){
			if(volumeDiscountNo.equals(masterNo)){
				return VOLUME_DISCOUNT_PLANS.get(volumeDiscountNo);
			}
		}
		return null;
	}
	public Map<Integer, Map<String, String>> getAllTiers(Integer detailNo) throws NoTierLevelException {
		// looping thru all master plan
		Map<Integer, Map<String, String>> returnMap = null;
		boolean found = false;
		for(Integer masterNo : VOLUME_DISCOUNT_PLANS.keySet()){
			MstbVolDiscMaster pdpm = VOLUME_DISCOUNT_PLANS.get(masterNo);
			// looping thru all details
			for(MstbVolDiscDetail pdpd : pdpm.getMstbVolDiscDetails()){
				if(pdpd.getVolumeDiscountPlanDetailNo().equals(detailNo)){
					returnMap = new LinkedHashMap<Integer, Map<String, String>>();
					// now looping thru all tiers
					for(MstbVolDiscTier pdpt : pdpd.getMstbVolDiscTiers()){
						Map<String, String> tierMap = new LinkedHashMap<String, String>();
						tierMap.put(TIER_START_RANGE, ""+pdpt.getStartRange());
						tierMap.put(TIER_END_RANGE, ""+pdpt.getEndRange());
						tierMap.put(TIER_VOLUME_DISCOUNT, pdpt.getVolumeDiscount()+"%");
						returnMap.put(pdpt.getVolumeDiscountPlanTierNo(), tierMap);
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
		// init volume discount
		List<MstbVolDiscMaster> volumeDiscountPlans = this.setupDao.getAllVolumeDiscountPlans();
		for(MstbVolDiscMaster volumeDiscountPlan : volumeDiscountPlans){
			VOLUME_DISCOUNT_PLANS.put(volumeDiscountPlan.getVolumeDiscountPlanNo(), volumeDiscountPlan);
		}
	}
	private void clear(){
		VOLUME_DISCOUNT_PLANS.clear();
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
