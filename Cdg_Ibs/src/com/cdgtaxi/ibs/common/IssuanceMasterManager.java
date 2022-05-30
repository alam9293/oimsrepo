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
import com.cdgtaxi.ibs.master.model.MstbIssuanceFeeDetail;
import com.cdgtaxi.ibs.master.model.MstbIssuanceFeeMaster;
import com.cdgtaxi.ibs.util.DateUtil;

public class IssuanceMasterManager extends GenericMasterManager implements MasterManager {
	private static final Map<Integer, MstbIssuanceFeeMaster> ISSUANCE_PLANS	= Collections.synchronizedMap(new LinkedHashMap<Integer, MstbIssuanceFeeMaster>());
	public static final String DETAIL_ISSUANCE_FEE = "IF";
	public static final String DETAIL_EFFECTIVE_DATE = "ED";

	private SetupDao setupDao;
	public Map<Integer, Map<String, String>> getAllDetails(Integer masterNo) {
		// getting the master issuance fee plan
		MstbIssuanceFeeMaster sfpm = ISSUANCE_PLANS.get(masterNo);
		if(sfpm!=null){
			Map<Integer, Map<String, String>> returnMap = new LinkedHashMap<Integer, Map<String,String>>();
			for(MstbIssuanceFeeDetail sfpd : sfpm.getMstbIssuanceFeeDetails()){
				Map<String, String> detailMap = new LinkedHashMap<String, String>();
				detailMap.put(DETAIL_ISSUANCE_FEE, ""+sfpd.getIssuanceFee());
				detailMap.put(DETAIL_EFFECTIVE_DATE, DateUtil.convertTimestampToStr(sfpd.getEffectiveDt(), DateUtil.GLOBAL_DATE_FORMAT));
				returnMap.put(sfpd.getIssuanceFeeDetailNo(), detailMap);
			}
			return sortDetails(returnMap);
		}else{
			return null;
		}
	}
	public Map<Integer, Map<String, String>> getCurrentDetail(Integer masterNo) throws NoDetailLevelException {
		MstbIssuanceFeeDetail sfpd = getCurrentIssuanceFeeDetail(masterNo);
		if(sfpd!=null){
			Map<Integer, Map<String, String>> returnMap = new LinkedHashMap<Integer, Map<String,String>>();
			Map<String, String> detailMap = new LinkedHashMap<String, String>();
			detailMap.put(DETAIL_ISSUANCE_FEE, ""+sfpd.getIssuanceFee());
			detailMap.put(DETAIL_EFFECTIVE_DATE, DateUtil.convertTimestampToStr(sfpd.getEffectiveDt(), DateUtil.GLOBAL_DATE_FORMAT));
			returnMap.put(sfpd.getIssuanceFeeDetailNo(), detailMap);
			return returnMap;
		}else{
			return null;
		}
	}
	public BigDecimal getCurrentIssuanceFee(Integer masterNo){
		MstbIssuanceFeeDetail currentIssuanceFeeDetail = getCurrentIssuanceFeeDetail(masterNo);
		return currentIssuanceFeeDetail == null ? null : currentIssuanceFeeDetail.getIssuanceFee();
	}
	private MstbIssuanceFeeDetail getCurrentIssuanceFeeDetail(Integer masterNo){
		MstbIssuanceFeeMaster sfpm = ISSUANCE_PLANS.get(masterNo);
		if(sfpm!=null){
			TreeSet<MstbIssuanceFeeDetail> details = new TreeSet<MstbIssuanceFeeDetail>(new Comparator<MstbIssuanceFeeDetail>(){
				public int compare(MstbIssuanceFeeDetail sf1, MstbIssuanceFeeDetail sf2) {
					return sf1.getEffectiveDt().compareTo(sf2.getEffectiveDt());
				}
			});
			for(MstbIssuanceFeeDetail sfpd : sfpm.getMstbIssuanceFeeDetails()){
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
		for(Integer issuanceNo : ISSUANCE_PLANS.keySet()){
			MstbIssuanceFeeMaster sfm = ISSUANCE_PLANS.get(issuanceNo);
			for(MstbIssuanceFeeDetail sfd : sfm.getMstbIssuanceFeeDetails()){
				if(sfd.getIssuanceFeeDetailNo().equals(detailNo)){
					return sfd;
				}
			}
		}
		return null;
	}
	public Map<Integer, String> getAllMasters() {
		Map<Integer, String> returnMap = new LinkedHashMap<Integer, String>();
		for(Integer issuanceFeePlanNo : ISSUANCE_PLANS.keySet()){
			returnMap.put(issuanceFeePlanNo, ISSUANCE_PLANS.get(issuanceFeePlanNo).getIssuanceFeeName());
		}
		return sortMasters(returnMap);
	}
	public Object getMaster(Integer masterNo) throws NoMasterLevelException{
		for(Integer issuanceNo : ISSUANCE_PLANS.keySet()){
			if(issuanceNo.equals(masterNo)){
				return ISSUANCE_PLANS.get(issuanceNo);
			}
		}
		return null;
	}
	public Map<Integer, Map<String, String>> getAllTiers(Integer detailNo) throws NoTierLevelException {
		throw new NoTierLevelException("No Tiers found for IssuanceNo Fee Master");
	}
	public void init() {
		// init issuance fee
		List<MstbIssuanceFeeMaster> issuanceFees = this.setupDao.getAllIssuanceFees();
		for(MstbIssuanceFeeMaster issuanceFee :  issuanceFees){
			ISSUANCE_PLANS.put(issuanceFee.getIssuanceFeeNo(), issuanceFee);
		}
	}
	public void refresh() {
		clear();
		init();
	}
	private void clear(){
		ISSUANCE_PLANS.clear();
	}
	public SetupDao getSetupDao() {
		return setupDao;
	}
	public void setSetupDao(SetupDao setupDao) {
		this.setupDao = setupDao;
	}
}