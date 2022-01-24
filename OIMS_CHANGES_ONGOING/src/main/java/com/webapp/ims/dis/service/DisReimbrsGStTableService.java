package com.webapp.ims.dis.service;

import java.util.List;

import com.webapp.ims.dis.model.DisReimbrsDepositeGstTable;

public interface DisReimbrsGStTableService {

	public List<DisReimbrsDepositeGstTable> saveReimbrsGSTTableList(
			List<DisReimbrsDepositeGstTable> reimbrsGStTableList);

	public List<DisReimbrsDepositeGstTable> getListBydisAppId(String applId);

	public void deletedisId(String id);
}
