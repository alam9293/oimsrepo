package com.cdgtaxi.ibs.common;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.cdgtaxi.ibs.common.exception.NoDetailLevelException;
import com.cdgtaxi.ibs.common.exception.NoMasterLevelException;
import com.cdgtaxi.ibs.common.exception.NoTierLevelException;
import com.cdgtaxi.ibs.master.dao.SetupDao;
import com.cdgtaxi.ibs.master.model.MstbPromoDetail;
import com.cdgtaxi.ibs.master.model.MstbPromotion;
import com.cdgtaxi.ibs.util.DateUtil;
import com.cdgtaxi.ibs.util.StringUtil;

public class PromotionMasterManager extends GenericMasterManager implements MasterManager {
	private static Logger logger = Logger.getLogger(PromotionMasterManager.class);
	public static final String MASTER_NAME			= "NAME";
	public static final String MASTER_TYPE			= "TYPE";
	public static final String MASTER_PRODUCT_TYPE	= "PROD_TYPE";
	public static final String MASTER_PROMO_TYPE	= "PROMO_TYPE";
	public static final String MASTER_PROMO_VALUE	= "PROMO_VALUE";
	public static final String MASTER_EFF_DATE_FROM	= "EFF_FROM";
	public static final String MASTER_EFF_DATE_TO	= "EFF_TO";
	public static final String MASTER_JOB			= "JOB";
	public static final String MASTER_MODEL			= "MODEL";
	private static final Map<Integer, MstbPromotion> PROMOTION_PLANS = Collections.synchronizedMap(new LinkedHashMap<Integer, MstbPromotion>());
	private SetupDao setupDao;
	public Map<Integer, Map<String, String>> getAllDetails(Integer masterNo) throws NoDetailLevelException{
		// no details for promotions
		throw new NoDetailLevelException("No detail level for promotion.");
	}
	public Map<Integer, Map<String, String>> getCurrentDetail(Integer masterNo) throws NoDetailLevelException{
		throw new NoDetailLevelException("No detail level for promotion.");
	}

	public Map<Integer, Map<String, String>> getCurrentDetails() throws NoDetailLevelException {
		throw new NoDetailLevelException("No detail level for promotion.");
	}
	public Object getDetail(Integer detailNo){
		throw new NoDetailLevelException("No detail level for promotion.");
	}
	public Map<Integer, String> getAllEffectiveAccountPromotions(){
		Map<Integer, String> returnMap = new LinkedHashMap<Integer, String>();
		for(Integer promotionNo : PROMOTION_PLANS.keySet()){
			MstbPromotion promo = PROMOTION_PLANS.get(promotionNo);
			MstbPromoDetail promoDetail = promo.getCurrentPromoDetail();		
			if(promoDetail.getType().equals(NonConfigurableConstants.PROMO_ACCT_TYPE_ACCOUNT)){
				if(promoDetail.getEffectiveDtFrom().before(DateUtil.getCurrentUtilDate()) && (promoDetail.getEffectiveDtTo() == null || promoDetail.getEffectiveDtTo().after(DateUtil.getCurrentDate()))){
					returnMap.put(promotionNo, promoDetail.getName());
				}
			}
		}
		return sortMasters(returnMap);
	}
	public Map<Integer, Map<String, String>> getAllAccountPromotions(){
		Map<Integer, Map<String, String>> returnMap = new LinkedHashMap<Integer, Map<String, String>>();
		for(Integer promotionNo : PROMOTION_PLANS.keySet()){
			MstbPromotion promo = PROMOTION_PLANS.get(promotionNo);
			MstbPromoDetail promotion = promo.getCurrentPromoDetail();
			if(promotion.getType().equals(NonConfigurableConstants.PROMO_ACCT_TYPE_ACCOUNT)){
				Map<String, String> returnPromoMap = new HashMap<String, String>();
				returnPromoMap.put(MASTER_NAME, promotion.getName());
				returnPromoMap.put(MASTER_TYPE, NonConfigurableConstants.PROMO_ACCT_TYPE.get(promotion.getType()));
				returnPromoMap.put(MASTER_PRODUCT_TYPE, promotion.getPmtbProductType()!=null ? promotion.getPmtbProductType().getName() : "ALL");
				returnPromoMap.put(MASTER_PROMO_TYPE, NonConfigurableConstants.PROMO_TYPE.get(promotion.getPromoType()));
				returnPromoMap.put(MASTER_PROMO_VALUE, StringUtil.bigDecimalToString(promotion.getPromoValue(), StringUtil.GLOBAL_DECIMAL_FORMAT));
				returnPromoMap.put(MASTER_EFF_DATE_FROM, DateUtil.convertTimestampToStr(promotion.getEffectiveDtFrom(), DateUtil.GLOBAL_DATE_FORMAT));
				returnPromoMap.put(MASTER_EFF_DATE_TO, promotion.getEffectiveDtTo()!=null ? DateUtil.convertTimestampToStr(promotion.getEffectiveDtTo(), DateUtil.GLOBAL_DATE_FORMAT) : "-");
				returnPromoMap.put(MASTER_JOB, promotion.getMstbMasterTableByJobType()!=null ? promotion.getMstbMasterTableByJobType().getMasterValue() : "ALL");
				returnPromoMap.put(MASTER_MODEL, promotion.getMstbMasterTableByVehicleModel()!=null ? promotion.getMstbMasterTableByVehicleModel().getMasterValue() : "ALL");
				returnMap.put(promotionNo, returnPromoMap);
			}
		}
		return sortDetails(returnMap);
	}
	public Map<Integer, String> getAllMasters(){
		Map<Integer, String> returnMap = new LinkedHashMap<Integer, String>();
		for(Integer promotionNo : PROMOTION_PLANS.keySet()){
			MstbPromotion promo = PROMOTION_PLANS.get(promotionNo);
			MstbPromoDetail promoDetail = promo.getCurrentPromoDetail();
			returnMap.put(promotionNo, promoDetail.getName());
		}
		return sortMasters(returnMap);
	}
	public Object getMaster(Integer masterNo) throws NoMasterLevelException{
		for(Integer promotionNo : PROMOTION_PLANS.keySet()){
			if(promotionNo.equals(masterNo)){
				return PROMOTION_PLANS.get(promotionNo);
			}
		}
		return null;
	}
	public Map<Integer, Map<String, String>> getAllTiers(Integer detailNo) throws NoTierLevelException {
		throw new NoTierLevelException("No Tiers found for Promotion Master");
	}
	private void clear() {
		PROMOTION_PLANS.clear();
	}
	public void init() {
		// init credit term
		List<MstbPromotion> promotions = this.setupDao.getAllPromotions();
		for(MstbPromotion promotion : promotions){
			PROMOTION_PLANS.put(promotion.getPromoNo(), promotion);
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