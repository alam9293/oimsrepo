package com.cdgtaxi.ibs.common.dao;

import java.util.List;

import com.cdgtaxi.ibs.common.model.AmtbAccount;
import com.cdgtaxi.ibs.common.model.FmtbBankCode;
import com.cdgtaxi.ibs.common.model.FmtbEntityMaster;

public interface BankCodeDao extends GenericDao {
	public List<Object[]> getBankInBanks(AmtbAccount account);
	public FmtbBankCode getLatestBankCode(String bankCode, String branchCode, FmtbEntityMaster entityNo);
}