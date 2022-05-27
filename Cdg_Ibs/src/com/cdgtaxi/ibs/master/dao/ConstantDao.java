package com.cdgtaxi.ibs.master.dao;

import java.util.List;

import com.cdgtaxi.ibs.common.dao.GenericDao;
import com.cdgtaxi.ibs.master.model.MstbMasterTable;

public interface ConstantDao extends GenericDao {
	public List<MstbMasterTable> getAllMasterTable();
	public List<MstbMasterTable> getAllActiveMasterTable();
}