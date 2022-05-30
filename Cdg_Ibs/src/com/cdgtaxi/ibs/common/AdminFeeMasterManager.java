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
import com.cdgtaxi.ibs.master.model.MstbAdminFeeDetail;
import com.cdgtaxi.ibs.master.model.MstbAdminFeeMaster;
import com.cdgtaxi.ibs.util.DateUtil;

public class AdminFeeMasterManager extends GenericMasterManager implements MasterManager {
	public static final String DETAIL_ADMIN_FEE = "AF";
	public static final String DETAIL_EFFECTIVE_DATE = "ED";
	private static final Map<Integer, MstbAdminFeeMaster> ADMIN_FEE_PLANS = Collections.synchronizedMap(new LinkedHashMap<Integer, MstbAdminFeeMaster>());
	private SetupDao setupDao;
	public Map<Integer, Map<String, String>> getAllDetails(Integer masterNo){
		MstbAdminFeeMaster afpm = ADMIN_FEE_PLANS.get(masterNo);
		if(afpm!=null){
			Map<Integer, Map<String, String>> returnMap = new LinkedHashMap<Integer, Map<String,String>>();
			for(MstbAdminFeeDetail afpd : afpm.getMstbAdminFeeDetails()){
				Map<String, String> detailMap = new LinkedHashMap<String, String>();
				detailMap.put(DETAIL_ADMIN_FEE, afpd.getAdminFee()+"%");
				detailMap.put(DETAIL_EFFECTIVE_DATE, DateUtil.convertTimestampToStr(afpd.getEffectiveDt(), DateUtil.GLOBAL_DATE_FORMAT));
				returnMap.put(afpd.getAdminFeePlanDetailNo(), detailMap);
			}
			return sortDetails(returnMap);
		}else{
			return null;
		}
	}
	public Map<Integer, Map<String, String>> getCurrentDetail(Integer masterNo) throws NoDetailLevelException{
		MstbAdminFeeDetail afpd = getCurrentAdminFeeDetail(masterNo);
		if(afpd!=null){
			Map<Integer, Map<String, String>> returnMap = new LinkedHashMap<Integer, Map<String,String>>();
			Map<String, String> detailMap = new LinkedHashMap<String, String>();
			detailMap.put(DETAIL_ADMIN_FEE, afpd.getAdminFee()+"%");
			detailMap.put(DETAIL_EFFECTIVE_DATE, DateUtil.convertTimestampToStr(afpd.getEffectiveDt(), DateUtil.GLOBAL_DATE_FORMAT));
			returnMap.put(afpd.getAdminFeePlanDetailNo(), detailMap);
			return returnMap;
		}else{
			return null;
		}
	}
	public BigDecimal getCurrentAdminFee(Integer masterNo){
		MstbAdminFeeDetail currentAdminFeeDetail = getCurrentAdminFeeDetail(masterNo);
		return currentAdminFeeDetail == null ? null : currentAdminFeeDetail.getAdminFee();
	}
	private MstbAdminFeeDetail getCurrentAdminFeeDetail(Integer masterNo){
		MstbAdminFeeMaster afpm = ADMIN_FEE_PLANS.get(masterNo);
		if(afpm!=null){
			TreeSet<MstbAdminFeeDetail> details = new TreeSet<MstbAdminFeeDetail>(new Comparator<MstbAdminFeeDetail>(){
				public int compare(MstbAdminFeeDetail af1, MstbAdminFeeDetail af2) {
					return af1.getEffectiveDt().compareTo(af2.getEffectiveDt());
				}
			});
			for(MstbAdminFeeDetail afpd : afpm.getMstbAdminFeeDetails()){
				if(afpd.getEffectiveDt().before(Calendar.getInstance().getTime())){
					details.add(afpd);
				}
			}
			return details.size()==0 ? null : details.last();
		}else{
			return null;
		}
	}
	public Object getDetail(Integer detailNo){
		for(Integer adminFeeNo : ADMIN_FEE_PLANS.keySet()){
			MstbAdminFeeMaster afpm = ADMIN_FEE_PLANS.get(adminFeeNo);
			for(MstbAdminFeeDetail afpd : afpm.getMstbAdminFeeDetails()){
				if(afpd.getAdminFeePlanDetailNo().equals(detailNo)){
					return afpd;
				}
			}
		}
		return null;
	}
	public Map<Integer, String> getAllMasters(){
		Map<Integer, String> returnMap = new LinkedHashMap<Integer, String>();
		for(Integer adminFeePlanNo : ADMIN_FEE_PLANS.keySet()){
			returnMap.put(adminFeePlanNo, ADMIN_FEE_PLANS.get(adminFeePlanNo).getAdminFeePlanName());
		}
		return sortMasters(returnMap);
	}
	public Object getMaster(Integer masterNo) throws NoMasterLevelException{
		for(Integer adminFeeNo : ADMIN_FEE_PLANS.keySet()){
			if(adminFeeNo.equals(masterNo)){
				return ADMIN_FEE_PLANS.get(adminFeeNo);
			}
		}
		return null;
	}
	public void init(){
		// init admin fee
		List<MstbAdminFeeMaster> adminFeePlans = this.setupDao.getAllAdminFeePlans();
		for(MstbAdminFeeMaster adminFeePlan : adminFeePlans){
			ADMIN_FEE_PLANS.put(adminFeePlan.getAdminFeePlanNo(), adminFeePlan);
		}
	}
	private void clear() {
		ADMIN_FEE_PLANS.clear();
	}
	public void refresh() {
		clear();
		init();
	}
	public Map<Integer, Map<String, String>> getAllTiers(Integer detailNo) throws NoTierLevelException{
		throw new NoTierLevelException("No Tiers found for Admin Fee Master");
	}
	// getter and setter
	public SetupDao getSetupDao() {
		return setupDao;
	}

	public void setSetupDao(SetupDao setupDao) {
		this.setupDao = setupDao;
	}
	
}