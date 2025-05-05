package com.cdgtaxi.ibs.common;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.cdgtaxi.ibs.common.exception.NoDetailLevelException;
import com.cdgtaxi.ibs.common.exception.NoMasterLevelException;
import com.cdgtaxi.ibs.common.exception.NoTierLevelException;
import com.cdgtaxi.ibs.master.dao.SetupDao;
import com.cdgtaxi.ibs.master.model.MstbSalesperson;

public class SalespersonMasterManager extends GenericMasterManager implements MasterManager {
	private static final Map<Integer, MstbSalesperson> SALESPERSONS = Collections.synchronizedMap(new LinkedHashMap<Integer, MstbSalesperson>());
	public static final String DETAIL_SUBSCRIPTION_FEE = "SF";
	public static final String DETAIL_EFFECTIVE_DATE = "ED";
	private SetupDao setupDao;
	public Map<Integer, Map<String, String>> getAllDetails(Integer masterNo) throws NoDetailLevelException {
		throw new NoDetailLevelException("No Details found for Salesperson Master");
	}
	public Map<Integer, Map<String, String>> getCurrentDetail(Integer masterNo) throws NoDetailLevelException {
		throw new NoDetailLevelException("No Details found for Salesperson Master");
	}
	public Object getDetail(Integer detailNo) throws NoDetailLevelException{
		throw new NoDetailLevelException("No Details found for Salesperson Master");
	}
	public Map<Integer, String> getAllMasters() throws NoMasterLevelException {
		Map<Integer, String> returnMap = new LinkedHashMap<Integer, String>();
		for(Integer salespersonNo : SALESPERSONS.keySet()){
			returnMap.put(salespersonNo, SALESPERSONS.get(salespersonNo).getName());
		}
		return sortMasters(returnMap);
	}
	public Object getMaster(Integer masterNo) throws NoMasterLevelException{
		for(Integer salespersonNo : SALESPERSONS.keySet()){
			if(salespersonNo.equals(masterNo)){
				return SALESPERSONS.get(salespersonNo);
			}
		}
		return null;
	}
	public Map<Integer, Map<String, String>> getAllTiers(Integer detailNo) throws NoTierLevelException {
		throw new NoTierLevelException("No Tiers found for Salesperson Master");
	}
	public void init() {
		// init salesperson
		List<MstbSalesperson> salespersons = this.setupDao.getAllSalespersons();
		for(MstbSalesperson salesperson : salespersons){
			SALESPERSONS.put(salesperson.getSalespersonNo(), salesperson);
		}
	}
	public void refresh() {
		clear();
		init();
	}
	private void clear(){
		SALESPERSONS.clear();
	}
	public SetupDao getSetupDao() {
		return setupDao;
	}
	public void setSetupDao(SetupDao setupDao) {
		this.setupDao = setupDao;
	}
}