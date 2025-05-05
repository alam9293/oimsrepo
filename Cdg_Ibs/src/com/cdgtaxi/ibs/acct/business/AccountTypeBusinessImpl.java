package com.cdgtaxi.ibs.acct.business;

import java.util.List;

import org.apache.log4j.Logger;

import com.cdgtaxi.ibs.common.business.GenericBusinessImpl;
import com.cdgtaxi.ibs.common.model.AmtbAcctType;
import com.cdgtaxi.ibs.common.model.PmtbProductType;

public class AccountTypeBusinessImpl extends GenericBusinessImpl implements AccountTypeBusiness{
	private static final Logger logger = Logger.getLogger(AccountTypeBusinessImpl.class);
	public List<AmtbAcctType> getAccountTypes(String template){
		logger.info("getAccountTypes(String template)");
		return this.daoHelper.getAccountTypeDao().getAccountTypes(template);
	}
	public boolean hasAccountType(String accountTemplate, String accountType){
		logger.info("hasAccountType(String accountTemplate, String accountType)");
		List<AmtbAcctType> accountTypes = this.daoHelper.getAccountTypeDao().getAccountTypes(accountTemplate);
		for(AmtbAcctType accountType2 : accountTypes){
			if(accountType2.getAcctType().equals(accountType)){
				return true;
			}
		}
		return false;
	}
	public List<PmtbProductType> getAllProductTypes(){
		logger.info("getAllProductTypes()");
		return this.daoHelper.getProductTypeDao().getAllProductType();
	}
	public void saveAccountTypes(List<AmtbAcctType> accountTypes){
		logger.info("saveAccountTypes(List<AccountType> accountTypes)");
		for(AmtbAcctType accountType : accountTypes){
			this.daoHelper.getAccountTypeDao().update(accountType);
		}
	}
	public boolean createAccountType(String accountTemplate, String accountType){
		logger.info("createAccountType(String accountTemplate, String accountType)");
		AmtbAcctType newAcctType = new AmtbAcctType();
		newAcctType.setAcctTemplate(accountTemplate);
		newAcctType.setAcctType(accountType);
		return this.daoHelper.getAccountTypeDao().save(newAcctType)!=null ? true : false;
	}
}