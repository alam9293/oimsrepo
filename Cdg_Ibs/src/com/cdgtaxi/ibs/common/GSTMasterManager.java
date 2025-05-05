package com.cdgtaxi.ibs.common;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;

import com.cdgtaxi.ibs.common.exception.NoDetailLevelException;
import com.cdgtaxi.ibs.common.exception.NoMasterLevelException;
import com.cdgtaxi.ibs.common.exception.NoTierLevelException;
import com.cdgtaxi.ibs.master.dao.SetupDao;
import com.cdgtaxi.ibs.master.model.MstbGstDetail;
import com.cdgtaxi.ibs.util.DateUtil;

public class GSTMasterManager extends GenericMasterManager implements MasterManager {
	public static final String GST = "GST";
	public static final String EFFECTIVE_DATE = "ED";
	private static final Map<Integer, MstbGstDetail> GST_DETAILS = Collections.synchronizedMap(new LinkedHashMap<Integer, MstbGstDetail>());
	private SetupDao setupDao;
	public Map<Integer, Map<String, String>> getAllDetails(Integer masterNo){
		Map<Integer, Map<String, String>> returnMap = new LinkedHashMap<Integer, Map<String,String>>();
		for(Integer gstNo : GST_DETAILS.keySet()){
			Map<String, String> detailMap = new LinkedHashMap<String, String>();
			detailMap.put(GST, GST_DETAILS.get(gstNo).getGst()+"%");
			detailMap.put(EFFECTIVE_DATE, DateUtil.convertTimestampToStr(GST_DETAILS.get(gstNo).getEffectiveDt(), DateUtil.GLOBAL_DATE_FORMAT));
			returnMap.put(gstNo, detailMap);
		}
		return sortDetails(returnMap);
	}
	public Map<Integer, Map<String, String>> getCurrentDetail(Integer masterNo) throws NoDetailLevelException {
		MstbGstDetail currentGSTDetail = getCurrentGSTDetail(new Date());
		if(currentGSTDetail!=null){
			Map<Integer, Map<String, String>> returnMap = new LinkedHashMap<Integer, Map<String,String>>();
			Map<String, String> detailMap = new LinkedHashMap<String, String>();
			detailMap.put(GST, currentGSTDetail.getGst()+"%");
			detailMap.put(EFFECTIVE_DATE, DateUtil.convertTimestampToStr(currentGSTDetail.getEffectiveDt(), DateUtil.GLOBAL_DATE_FORMAT));
			returnMap.put(currentGSTDetail.getGstDetailNo(), detailMap);
			return returnMap;
		}else{
			return null;
		}
	}
	public BigDecimal getCurrentGST(){
		MstbGstDetail currentGSTDetail = getCurrentGSTDetail(new Date());
		return currentGSTDetail == null ? null : currentGSTDetail.getGst();
	}
	public BigDecimal getEffectiveGST(Date invoiceDate) {
		MstbGstDetail currentGSTDetail = getCurrentGSTDetail(invoiceDate);
		return currentGSTDetail == null ? null : currentGSTDetail.getGst();
	}
	private MstbGstDetail getCurrentGSTDetail(Date date){
		TreeSet<MstbGstDetail> gsts = new TreeSet<MstbGstDetail>(new Comparator<MstbGstDetail>(){
			public int compare(MstbGstDetail gst1, MstbGstDetail gst2) {
				return gst1.getEffectiveDt().compareTo(gst2.getEffectiveDt());
			}
		});
		for(Integer gstNo : GST_DETAILS.keySet()){
			if(GST_DETAILS.get(gstNo).getEffectiveDt().before(date)){
				gsts.add(GST_DETAILS.get(gstNo));
			}
		}
		return gsts.size()==0 ? null : gsts.last();
	}
	public Object getDetail(Integer detailNo){
		for(Integer gstNo : GST_DETAILS.keySet()){
			if(GST_DETAILS.get(gstNo).getGstDetailNo().equals(detailNo)){
				return GST_DETAILS.get(gstNo);
			}
		}
		return null;
	}
	public Map<Integer, String> getAllMasters() throws NoMasterLevelException{
		throw new NoMasterLevelException("No Masters found for GST");
	}
	public Object getMaster(Integer masterNo) throws NoMasterLevelException{
		throw new NoMasterLevelException("No Masters found for GST");
	}
	public Map<Integer, Map<String, String>> getAllTiers(Integer detailNo) throws NoTierLevelException {
		throw new NoTierLevelException("No Tiers found for GST");
	}
	public void init() {
		// init GST
		List<MstbGstDetail> gsts = this.setupDao.getAllGst();
		for(MstbGstDetail gst : gsts){
			GST_DETAILS.put(gst.getGstDetailNo(), gst);
		}
	}
	private void clear(){
		GST_DETAILS.clear();
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