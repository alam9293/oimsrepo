package com.cdgtaxi.ibs.billgen.business;

import java.sql.Date;
import java.util.List;

import org.zkoss.zul.Listitem;

import com.cdgtaxi.ibs.common.business.GenericBusiness;
import com.cdgtaxi.ibs.common.model.AmtbAccount;
import com.cdgtaxi.ibs.common.model.BmtbBillGenError;
import com.cdgtaxi.ibs.common.model.BmtbBillGenReq;
import com.cdgtaxi.ibs.common.model.BmtbBillGenSetup;

public interface BillGenBusiness extends GenericBusiness {
	
	public Integer createNormalRequest(Integer billGenSetupNo, Integer monthOfBillGen, Integer entityNo, String createdBy) throws Exception;
	public Integer createAdHocRequest(AmtbAccount topLevelAccount, String createdBy) throws Exception;
	public Integer createDraftRequest(AmtbAccount topLevelAccount, String createdBy) throws Exception;
	public List<AmtbAccount> searchBillableAccount(String customerNo, String name, String code);
	public boolean checkInvoiceExist(Integer accountNo, Integer billGenRequestNo);
	public List<BmtbBillGenReq> searchRequest(Integer requestNo, String status, Integer setupNo, Date requestDateFrom, Date requestDateTo, Integer entityNo);
	public List<BmtbBillGenReq> searchRequest(Integer requestNo, String status, Integer setupNo, Date requestDateFrom, Date requestDateTo, Integer entityNo, String backward);
	public boolean checkRequestExist(List<BmtbBillGenSetup> billGenSetups, Date requestDate, Integer entityNo, Integer requestNo);
	public void saveSetupsChanges(List<BmtbBillGenSetup> setups, String updatedBy);
	public String getCustomerNo(Integer accountNo);
	public List<BmtbBillGenError> listAccountsWithError(BmtbBillGenReq billGenRequest);
	public List<Integer> regen(BmtbBillGenReq billGenRequest, List<Listitem> errors, String userId) throws Exception;
	public List<AmtbAccount> getBilliableAccountOnlyTopLevelWithEffectiveEntity(String customerNo, String name);
}
