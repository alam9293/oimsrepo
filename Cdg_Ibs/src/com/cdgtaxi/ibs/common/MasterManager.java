package com.cdgtaxi.ibs.common;

import java.util.Map;

import com.cdgtaxi.ibs.common.exception.NoDetailLevelException;
import com.cdgtaxi.ibs.common.exception.NoMasterLevelException;
import com.cdgtaxi.ibs.common.exception.NoTierLevelException;

public interface MasterManager {
	/**
	 * method to initialize the map for each master
	 */
	public void init();
	/**
	 * method to refresh the map within each master
	 */
	public void refresh();
	/**
	 * method to get all plan masters
	 * @return Map<Integer = masterNo, String = master name>
	 * @throws NoMasterLevelException thrown when no master level is found
	 */
	public Map<Integer, String> getAllMasters() throws NoMasterLevelException;
	/**
	 * method to get all master's detail 
	 * @param masterNo - the master number of the plan
	 * @return Map<Integer = detailNo, Map<String = detailAttrName, String = value>>
	 * 			detailAttrName can be found in each MasterManager final string
	 * @throws NoDetailLevelException thrown when no detail level is found
	 */
	public Map<Integer, Map<String, String>> getAllDetails(Integer masterNo) throws NoDetailLevelException;
	/**
	 * method to get the current detail of the master
	 * @param masterNo - the master number of the plan
	 * @return Map<Integer = detailNo, Map<String = detailAttrName, String = value>>
	 * 			detailAttrName can be found in each MasterManager final string
	 * @throws NoDetailLevelException thrown when no detail level is found
	 */
	public Map<Integer, Map<String, String>> getCurrentDetail(Integer masterNo) throws NoDetailLevelException;
	/**
	 * method to get all detail's tiers
	 * @param detailNo - the detail number
	 * @return Map<Integer = tierNo, Map<String = tierAttrName, String = value>>
	 * 			tierAttrName can be found in each MasterManager final String
	 * @throws NoTierLevelException thrown when no tier level is found
	 */
	public Map<Integer, Map<String, String>> getAllTiers(Integer detailNo) throws NoTierLevelException;
	/**
	 * method to get a specific detail. Needs to be cast back to the original class
	 * @param detailNo - the detail primary key
	 * @return - the detail of the matching key or null if not found
	 * @throws NoDetailLevelException - when there is no details
	 */
	public Object getDetail(Integer detailNo) throws NoDetailLevelException;
	/**
	 * method to get a specific master. Need to cast back to the original class
	 * @param masterNo - the master primary key
	 * @return - the master of the matching key or null if not found
	 * @throws NoMasterLevelException - when there is no master
	 */
	public Object getMaster(Integer masterNo) throws NoMasterLevelException;
	/**
	 * method is to sort the given map by its value
	 * @param map The map that is to be sorted
	 * @return sorted map
	 */
	public Map sortMastersByValue(Map<Integer, String> map);
}