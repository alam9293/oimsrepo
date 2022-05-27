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
import com.cdgtaxi.ibs.master.model.MstbProdDiscDetail;
import com.cdgtaxi.ibs.master.model.MstbProdDiscMaster;
import com.cdgtaxi.ibs.util.DateUtil;

public class ProductDiscountMasterManager extends GenericMasterManager implements MasterManager {
	public static final String DETAIL_PRODUCT_DISCOUNT = "PD";
	public static final String DETAIL_EFFECTIVE_DATE = "ED";
	private static final Map<Integer, MstbProdDiscMaster> PRODUCT_DISCOUNT_PLANS	= Collections.synchronizedMap(new LinkedHashMap<Integer, MstbProdDiscMaster>());
	private SetupDao setupDao;
	public Map<Integer, Map<String, String>> getAllDetails(Integer masterNo) {
		MstbProdDiscMaster pdpm = PRODUCT_DISCOUNT_PLANS.get(masterNo);
		if(pdpm!=null){
			Map<Integer, Map<String, String>> returnMap = new LinkedHashMap<Integer, Map<String,String>>();
			for(MstbProdDiscDetail pdpd : pdpm.getMstbProdDiscDetails()){
				Map<String, String> detailMap = new LinkedHashMap<String, String>();
				detailMap.put(DETAIL_PRODUCT_DISCOUNT, pdpd.getProductDiscount()+"%");
				detailMap.put(DETAIL_EFFECTIVE_DATE, DateUtil.convertTimestampToStr(pdpd.getEffectiveDt(), DateUtil.GLOBAL_DATE_FORMAT));
				returnMap.put(pdpd.getProdDiscountPlanDetailNo(), detailMap);
			}
			return sortDetails(returnMap);
		}else{
			return null;
		}
	}
	public Map<Integer, Map<String, String>> getCurrentDetail(Integer masterNo) throws NoDetailLevelException {
		MstbProdDiscDetail pdpd = getCurrentProductDiscountDetail(masterNo);
		if(pdpd!=null){
			Map<Integer, Map<String, String>> returnMap = new LinkedHashMap<Integer, Map<String,String>>();
			Map<String, String> detailMap = new LinkedHashMap<String, String>();
			detailMap.put(DETAIL_PRODUCT_DISCOUNT, pdpd.getProductDiscount()+"%");
			detailMap.put(DETAIL_EFFECTIVE_DATE, DateUtil.convertTimestampToStr(pdpd.getEffectiveDt(), DateUtil.GLOBAL_DATE_FORMAT));
			returnMap.put(pdpd.getProdDiscountPlanDetailNo(), detailMap);
			return returnMap;
		}else{
			return null;
		}
	}
	public BigDecimal getCurrentProductDiscount(Integer masterNo){
		MstbProdDiscDetail currentProductDiscountDetail = getCurrentProductDiscountDetail(masterNo);
		return currentProductDiscountDetail == null ? null : currentProductDiscountDetail.getProductDiscount();
	}
	private MstbProdDiscDetail getCurrentProductDiscountDetail(Integer masterNo){
		MstbProdDiscMaster pdpm = PRODUCT_DISCOUNT_PLANS.get(masterNo);
		if(pdpm!=null){
			TreeSet<MstbProdDiscDetail> details = new TreeSet<MstbProdDiscDetail>(new Comparator<MstbProdDiscDetail>(){
				public int compare(MstbProdDiscDetail pd1, MstbProdDiscDetail pd2) {
					return pd1.getEffectiveDt().compareTo(pd2.getEffectiveDt());
				}
			});
			for(MstbProdDiscDetail pdpd : pdpm.getMstbProdDiscDetails()){
				if(pdpd.getEffectiveDt().before(Calendar.getInstance().getTime())){
					details.add(pdpd);
				}
			}
			return details.size()==0 ? null : details.last();
		}else{
			return null;
		}
	}
	public Object getDetail(Integer detailNo){
		for(Integer productDiscountNo : PRODUCT_DISCOUNT_PLANS.keySet()){
			MstbProdDiscMaster pdpm = PRODUCT_DISCOUNT_PLANS.get(productDiscountNo);
			for(MstbProdDiscDetail pdpd : pdpm.getMstbProdDiscDetails()){
				if(pdpd.getProdDiscountPlanDetailNo().equals(detailNo)){
					return pdpd;
				}
			}
		}
		return null;
	}
	public Map<Integer, String> getAllMasters() {
		Map<Integer, String> returnMap = new LinkedHashMap<Integer, String>();
		for(Integer productDiscountPlanNo : PRODUCT_DISCOUNT_PLANS.keySet()){
			returnMap.put(productDiscountPlanNo, PRODUCT_DISCOUNT_PLANS.get(productDiscountPlanNo).getProductDiscountPlanName());
		}
		return sortMasters(returnMap);
	}
	public Object getMaster(Integer masterNo) throws NoMasterLevelException{
		for(Integer productDiscountNo : PRODUCT_DISCOUNT_PLANS.keySet()){
			if(productDiscountNo.equals(masterNo)){
				return PRODUCT_DISCOUNT_PLANS.get(productDiscountNo);
			}
		}
		return null;
	}
	public Map<Integer, Map<String, String>> getAllTiers(Integer detailNo) throws NoTierLevelException {
		throw new NoTierLevelException("No Tiers found for Product Discount Master");
	}
	public void init() {
		// init product discount
		List<MstbProdDiscMaster> productDiscountPlans = this.setupDao.getAllProductDiscountPlans();
		for(MstbProdDiscMaster productDiscountPlan : productDiscountPlans){
			PRODUCT_DISCOUNT_PLANS.put(productDiscountPlan.getProductDiscountPlanNo(), productDiscountPlan);
		}
	}
	public void refresh() {
		clear();
		init();
	}
	private void clear(){
		PRODUCT_DISCOUNT_PLANS.clear();
	}
	public SetupDao getSetupDao() {
		return setupDao;
	}

	public void setSetupDao(SetupDao setupDao) {
		this.setupDao = setupDao;
	}
}
