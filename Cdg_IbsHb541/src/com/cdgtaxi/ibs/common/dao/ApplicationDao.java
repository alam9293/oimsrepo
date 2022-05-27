package com.cdgtaxi.ibs.common.dao;

import java.util.List;

import com.cdgtaxi.ibs.common.model.AmtbApplication;
import com.cdgtaxi.ibs.master.model.MstbSalesperson;

public interface ApplicationDao extends GenericDao {
	public List<AmtbApplication> searchApplication(String appNo, String appName, String appStatus);
	public List<AmtbApplication> searchApplication(List<String> appStatuses);
	public List<AmtbApplication> searchApplicationChunk(List<String> appNo);
	public List<Object[]> searchApplicationObject(List<String> appStatuses);
	public boolean createApplication(AmtbApplication newApplication);
	public void saveApplication(AmtbApplication newApplication);
	public AmtbApplication getApplication(String appNo);
	public MstbSalesperson getApplicationSalesperson(String appNo);
	public boolean checkRCBNo(String rcbNo);
	public boolean checkCorporateName(String name);
}