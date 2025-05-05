/**
 * Not for distribution without written consent by WizVision.com Pte Ltd
 */
package com.cdgtaxi.ibs.common;

import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;

import com.cdgtaxi.ibs.common.exception.NoDetailLevelException;
import com.cdgtaxi.ibs.common.exception.NoMasterLevelException;
import com.cdgtaxi.ibs.common.exception.NoTierLevelException;
import com.cdgtaxi.ibs.common.model.FmtbArContCodeDetail;
import com.cdgtaxi.ibs.common.model.FmtbArContCodeMaster;
import com.cdgtaxi.ibs.common.model.FmtbEntityMaster;
import com.cdgtaxi.ibs.master.dao.SetupDao;
import com.cdgtaxi.ibs.util.DateUtil;

public class EntityMasterManager extends GenericMasterManager implements MasterManager {
	public static final String DETAIL_AR_CODE = "AC";
	public static final String TIER_GL = "GL";
	public static final String TIER_EFFECTIVE_DATE = "ED";
	public static final String TIER_DESCRIPTION = "ED";
	public static final String TIER_COST_CENTRE = "CC";
	public static final String TIER_TAX_TYPE = "TT";
	
	private static final Map<Integer, FmtbEntityMaster>	ENTITIES = Collections.synchronizedMap(new LinkedHashMap<Integer, FmtbEntityMaster>());
	private SetupDao setupDao;
	public Map<Integer, Map<String, String>> getAllDetails(Integer masterNo){
		FmtbEntityMaster em = ENTITIES.get(masterNo);
		if(em!=null){
			Map<Integer, Map<String, String>> returnMap = new LinkedHashMap<Integer, Map<String,String>>();
			for(FmtbArContCodeMaster arcc : em.getFmtbArContCodeMasters()){
				Map<String, String> detailMap = new LinkedHashMap<String, String>();
				detailMap.put(DETAIL_AR_CODE, arcc.getArControlCode());
				returnMap.put(arcc.getArControlCodeNo(), detailMap);
			}
			return sortDetails(returnMap);
		}else{
			return null;
		}
	}
	public Map<Integer, Map<String, String>> getCurrentDetail(Integer masterNo) throws NoDetailLevelException{
		throw new NoDetailLevelException("No effective date at AR control code master");
//		FmtbArContCodeMaster currentArControlCode = getCurrentArControlCodeMaster(masterNo);
//		if(currentArControlCode!=null){
//			Map<Integer, Map<String, String>> returnMap = new LinkedHashMap<Integer, Map<String,String>>();
//			Map<String, String> detailMap = new LinkedHashMap<String, String>();
//			detailMap.put(DETAIL_AR_CODE, currentArControlCode.getArControlCode());
//			returnMap.put(currentArControlCode.getArControlCodeNo(), detailMap);
//			return sortDetails(returnMap);
//		}else{
//			return null;
//		}
	}
	public Map<Integer, Map<String, String>> getCurrentTier(Integer detailNo){
		Map<Integer, Map<String, String>> returnMap = null;
		boolean found = false;
		for(FmtbEntityMaster em : ENTITIES.values()){
			for(FmtbArContCodeMaster accm : em.getFmtbArContCodeMasters()){
				if(accm.getArControlCodeNo().equals(detailNo)){
					returnMap = new LinkedHashMap<Integer, Map<String, String>>();
					TreeSet<FmtbArContCodeDetail> tiers = new TreeSet<FmtbArContCodeDetail>(new Comparator<FmtbArContCodeDetail>(){
						public int compare(FmtbArContCodeDetail o1,	FmtbArContCodeDetail o2) {
							return o1.getEffectiveDt().compareTo(o2.getEffectiveDt());
						}
					});
					for(FmtbArContCodeDetail accd : accm.getFmtbArContCodeDetails()){
						if(accd.getEffectiveDt().before(DateUtil.getCurrentTimestamp())){
							tiers.add(accd);
						}
					}
					if(!tiers.isEmpty()){
						Map<String, String> tierMap = new LinkedHashMap<String, String>();
						tierMap.put(TIER_GL, ""+tiers.last().getGlAccount());
						tierMap.put(TIER_DESCRIPTION, tiers.last().getDescription());
						tierMap.put(TIER_COST_CENTRE, tiers.last().getCostCentre());
						if(tiers.last().getMstbMasterTable()!=null){
							tierMap.put(TIER_TAX_TYPE, tiers.last().getMstbMasterTable().getMasterValue());
						}else{
							tierMap.put(TIER_TAX_TYPE, "-");
						}
						tierMap.put(TIER_EFFECTIVE_DATE, DateUtil.convertTimestampToStr(tiers.last().getEffectiveDt(), DateUtil.GLOBAL_DATE_FORMAT));
						returnMap.put(tiers.last().getArContCodeDetailNo(), tierMap);
					}
					break;
				}
			}
			if(found){
				break;
			}
		}
		return returnMap;
	}
//	private FmtbArContCodeMaster getCurrentArControlCodeMaster(Integer masterNo){
//		throw new NoDetailLevelException("No effective date at ar control code master");
//		FmtbEntityMaster em = ENTITIES.get(masterNo);
//		if(em!=null){
//			TreeSet<FmtbArContCodeMaster> details = new TreeSet<FmtbArContCodeMaster>(new Comparator<FmtbArContCodeMaster>(){
//				public int compare(FmtbArContCodeMaster ar1, FmtbArContCodeMaster ar2) {
//					return ar1.getEffectiveDt().compareTo(ar2.getEffectiveDt());
//				}
//			});
//			for(FmtbArContCodeMaster arControl : em.getFmtbArContCodeMasters()){
//				if(arControl.getEffectiveDt().after(Calendar.getInstance().getTime())){
//					details.add(arControl);
//				}
//			}
//			return em.getFmtbArContCodeMasters().size()==0 ? null : em.getFmtbArContCodeMasters().iterator().next();
//		}else{
//			return null;
//		}
//	}
	public Object getDetail(Integer detailNo){
		for(Integer entityNo : ENTITIES.keySet()){
			FmtbEntityMaster entity = ENTITIES.get(entityNo);
			for(FmtbArContCodeMaster arcc : entity.getFmtbArContCodeMasters()){
				if(arcc.getArControlCodeNo().equals(detailNo)){
					return arcc;
				}
			}
		}
		return null;
	}
	public Map<Integer, String> getAllMasters(){
		Map<Integer, String> returnMap = new LinkedHashMap<Integer, String>();
		for(Integer entityNo : ENTITIES.keySet()){
			returnMap.put(entityNo, ENTITIES.get(entityNo).getEntityName());
		}
		return sortMasters(returnMap);
	}
	public Object getMaster(Integer masterNo) throws NoMasterLevelException{
		for(Integer entityNo : ENTITIES.keySet()){
			if(entityNo.equals(masterNo)){
				return ENTITIES.get(entityNo);
			}
		}
		return null;
	}
	public Map<Integer, Map<String, String>> getAllTiers(Integer detailNo) throws NoTierLevelException {
		// looping thru all master plan
		Map<Integer, Map<String, String>> returnMap = null;
		boolean found = false;
		for(Integer masterNo : ENTITIES.keySet()){
			FmtbEntityMaster em = ENTITIES.get(masterNo);
			// looping thru all details
			for(FmtbArContCodeMaster accm : em.getFmtbArContCodeMasters()){
				if(accm.getArControlCodeNo().equals(detailNo)){
					returnMap = new LinkedHashMap<Integer, Map<String, String>>();
					// now looping thru all tiers
					for(FmtbArContCodeDetail accd : accm.getFmtbArContCodeDetails()){
						Map<String, String> tierMap = new LinkedHashMap<String, String>();
						tierMap.put(TIER_GL, ""+accd.getGlAccount());
						tierMap.put(TIER_DESCRIPTION, accd.getDescription());
						tierMap.put(TIER_COST_CENTRE, accd.getCostCentre());
						if(accd.getMstbMasterTable()!=null){
							tierMap.put(TIER_TAX_TYPE, accd.getMstbMasterTable().getMasterValue());
						}else{
							tierMap.put(TIER_TAX_TYPE, "-");
						}
						tierMap.put(TIER_EFFECTIVE_DATE, DateUtil.convertTimestampToStr(accd.getEffectiveDt(), DateUtil.GLOBAL_DATE_FORMAT));
						returnMap.put(accd.getArContCodeDetailNo(), tierMap);
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
		// init entities
		List<FmtbEntityMaster> entities = this.setupDao.getAllEntities();
		for(FmtbEntityMaster entity : entities){
			ENTITIES.put(entity.getEntityNo(), entity);
		}
	}
	public void refresh() {
		clear();
		init();
	}
	private void clear(){
		ENTITIES.clear();
	}
	public SetupDao getSetupDao() {
		return setupDao;
	}
	public void setSetupDao(SetupDao setupDao) {
		this.setupDao = setupDao;
	}
	
	/**
	 * To check whether entity is still active based on the effective end date.
	 * @param entity
	 * @return Effective End Date is null OR >= current date, return true.
	 */
	public boolean isEntityActive(FmtbEntityMaster entity){
		if(entity.getEffectiveEndDate()!=null &&
				entity.getEffectiveEndDate().compareTo(
						DateUtil.convertUtilDateToSqlDate(DateUtil.convertDateTo0000Hours(DateUtil.getCurrentUtilDate()))) < 0)
			return false;
		else
			return true;
	}
}