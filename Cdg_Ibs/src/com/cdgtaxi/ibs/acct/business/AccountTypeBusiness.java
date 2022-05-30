package com.cdgtaxi.ibs.acct.business;

import java.util.List;

import com.cdgtaxi.ibs.common.business.GenericBusiness;
import com.cdgtaxi.ibs.common.model.AmtbAcctType;
import com.cdgtaxi.ibs.common.model.PmtbProductType;

public interface AccountTypeBusiness extends GenericBusiness {
	public List<AmtbAcctType> getAccountTypes(String category);
	public void saveAccountTypes(List<AmtbAcctType> accountTypes);
	public List<PmtbProductType> getAllProductTypes();
	public boolean createAccountType(String accountCategory, String accountType);
	public boolean hasAccountType(String accountTemplate, String accountType);
}
