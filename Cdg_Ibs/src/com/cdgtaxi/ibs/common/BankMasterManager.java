package com.cdgtaxi.ibs.common;

import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.cdgtaxi.ibs.common.exception.NoDetailLevelException;
import com.cdgtaxi.ibs.common.exception.NoMasterLevelException;
import com.cdgtaxi.ibs.common.exception.NoTierLevelException;
import com.cdgtaxi.ibs.master.dao.SetupDao;
import com.cdgtaxi.ibs.master.model.MstbBankMaster;
import com.cdgtaxi.ibs.master.model.MstbBranchMaster;

public class BankMasterManager extends GenericMasterManager implements MasterManager {
	public static final String MASTER_BANK_NAME = "MBN";
	public static final String MASTER_BANK_CODE = "MBC";
	public static final String DETAIL_BRANCH_NAME = "DBN";
	public static final String DETAIL_BRANCH_CODE = "DBC";
	private static final Map<Integer, MstbBankMaster> BANKS = Collections.synchronizedMap(new LinkedHashMap<Integer, MstbBankMaster>());
	private SetupDao setupDao;
	public Map<Integer, Map<String, String>> getAllDetails(Integer masterNo){
		MstbBankMaster bank = BANKS.get(masterNo);
		if(bank!=null){
			Map<Integer, Map<String, String>> returnMap = new LinkedHashMap<Integer, Map<String,String>>();
			for(MstbBranchMaster branch : bank.getMstbBranchMasters()){
				Map<String, String> detailMap = new LinkedHashMap<String, String>();
				detailMap.put(DETAIL_BRANCH_NAME, branch.getBranchName());
				detailMap.put(DETAIL_BRANCH_CODE, branch.getBranchCode());
				returnMap.put(branch.getBranchMasterNo(), detailMap);
			}
			return sortByBranchCode(returnMap);
		}else{
			return null;
		}
	}
	public Map<Integer, Map<String, String>> getCurrentDetail(Integer masterNo) throws NoDetailLevelException{
		return getAllDetails(masterNo);
	}
	public Object getDetail(Integer detailNo){
		for(Integer bankNo : BANKS.keySet()){
			MstbBankMaster bank = BANKS.get(bankNo);
			for(MstbBranchMaster branch : bank.getMstbBranchMasters()){
				if(branch.getBranchMasterNo().equals(detailNo)){
					return branch;
				}
			}
		}
		return null;
	}
	public Map<Integer, String> getAllMasters(){
		Map<Integer, String> returnMap = new LinkedHashMap<Integer, String>();
		for(Integer bankNo : BANKS.keySet()){
			returnMap.put(bankNo, BANKS.get(bankNo).getBankName());
		}
		return sortMasters(returnMap);
	}
	public Object getMaster(Integer masterNo) throws NoMasterLevelException{
		for(Integer bankNo : BANKS.keySet()){
			if(bankNo.equals(masterNo)){
				return BANKS.get(bankNo);
			}
		}
		return null;
	}
	public void init(){
		// init admin fee
		List<MstbBankMaster> banks = this.setupDao.getAllBanks();
		for(MstbBankMaster bank : banks){
			BANKS.put(bank.getBankMasterNo(), bank);
		}
	}
	private void clear() {
		BANKS.clear();
	}
	public void refresh() {
		clear();
		init();
	}
	public Map<Integer, Map<String, String>> getAllTiers(Integer detailNo) throws NoTierLevelException{
		throw new NoTierLevelException("No Tiers found for Bank Master");
	}
	// getter and setter
	public SetupDao getSetupDao() {
		return setupDao;
	}

	public void setSetupDao(SetupDao setupDao) {
		this.setupDao = setupDao;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Map<Integer, Map<String, String>> sortByBranchCode(Map<Integer, Map<String, String>> details){
		List list = new LinkedList(details.entrySet());
	     Collections.sort(list, new Comparator() {
	          public int compare(Object o1, Object o2) {
	        	  
	        	  String branchCode1 = (String) ((Map)((Map.Entry) (o1)).getValue()).get(DETAIL_BRANCH_CODE);
	        	  String branchCode2 = (String) ((Map)((Map.Entry) (o2)).getValue()).get(DETAIL_BRANCH_CODE);
	        	  
	               return branchCode1.compareTo(branchCode2);
	          }
	     });

	    Map result = new LinkedHashMap();
	    for (Iterator it = list.iterator(); it.hasNext();) {
	        Map.Entry entry = (Map.Entry)it.next();
	        result.put(entry.getKey(), entry.getValue());
	    }
	    return result;
	}
}