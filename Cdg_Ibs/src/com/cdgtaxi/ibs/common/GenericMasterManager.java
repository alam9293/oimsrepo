package com.cdgtaxi.ibs.common;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.TreeSet;

public abstract class GenericMasterManager {
	protected Map<Integer, String> sortMasters(Map<Integer, String> masters){
		// sorting details. Can't do it at hibernate level
		TreeMap<Integer, String> sortedMap = new TreeMap<Integer, String>(new Comparator<Integer>(){
			public int compare(Integer masterNo1, Integer masterNo2){
				return masterNo1.compareTo(masterNo2);
			}
		});
		sortedMap.putAll(masters);
		return sortedMap;
	}
	protected Map<Integer, Map<String, String>> sortDetails(Map<Integer, Map<String, String>> details){
		// sorting details. Can't do it at hibernate level
		TreeMap<Integer, Map<String, String>> sortedMap = new TreeMap<Integer, Map<String, String>>(new Comparator<Integer>(){
			public int compare(Integer detailNo1, Integer detailNo2){
				return detailNo1.compareTo(detailNo2);
			}
		});
		sortedMap.putAll(details);
		return sortedMap;
	}
	protected Map<Integer, Map<String, String>> sortTiers(Map<Integer, Map<String, String>> tiers){
		// sorting details. Can't do it at hibernate level
		TreeMap<Integer, Map<String, String>> sortedMap = new TreeMap<Integer, Map<String, String>>(new Comparator<Integer>(){
			public int compare(Integer tierNo1, Integer tierNo2){
				return tierNo1.compareTo(tierNo2);
			}
		});
		sortedMap.putAll(tiers);
		return sortedMap;
	}
	public Map<Integer, String> sortMastersByValue(Map<Integer, String> map){
		// to hold the sorted result
		HashMap sortedMap = new LinkedHashMap();

		List mapKeys = new ArrayList(map.keySet());
		List mapValues = new ArrayList(map.values());
		TreeSet sortedSet = new TreeSet(mapValues);
		Object[] sortedArray = sortedSet.toArray();
		int size = sortedArray.length;

		for (int i=0; i<size; i++) {
			sortedMap.put
		      (mapKeys.get(mapValues.indexOf(sortedArray[i])),
		       sortedArray[i]);
		}
		
		return sortedMap;
	}
}
