package com.cdgtaxi.ibs.common.dao;

import java.util.List;

import com.cdgtaxi.ibs.common.model.AmtbAcctType;

public interface AccountTypeDao extends GenericDao {
	public List<AmtbAcctType> getAccountTypes(String template);
	public List<AmtbAcctType> getAccountType(Integer accountTypeNo);
	public List<AmtbAcctType> getAllAccountTypes();
}