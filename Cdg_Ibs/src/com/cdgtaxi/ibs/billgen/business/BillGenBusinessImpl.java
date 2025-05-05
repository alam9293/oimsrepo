package com.cdgtaxi.ibs.billgen.business;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.hibernate.ObjectNotFoundException;
import org.zkoss.zul.Listitem;

import com.cdgtaxi.ibs.common.NonConfigurableConstants;
import com.cdgtaxi.ibs.common.business.GenericBusinessImpl;
import com.cdgtaxi.ibs.common.exception.BillGenErrorFoundException;
import com.cdgtaxi.ibs.common.exception.BillGenRequestCreationException;
import com.cdgtaxi.ibs.common.exception.BillGenRequestExistanceException;
import com.cdgtaxi.ibs.common.model.AmtbAccount;
import com.cdgtaxi.ibs.common.model.BmtbBillGenError;
import com.cdgtaxi.ibs.common.model.BmtbBillGenReq;
import com.cdgtaxi.ibs.common.model.BmtbBillGenSetup;
import com.cdgtaxi.ibs.common.model.FmtbEntityMaster;
import com.cdgtaxi.ibs.util.DateUtil;

public class BillGenBusinessImpl extends GenericBusinessImpl implements BillGenBusiness {
	public Integer createNormalRequest(Integer billGenSetupNo, Integer monthOfBillGen, Integer entityNo, String createdBy) throws Exception{
		BmtbBillGenSetup billGenSetup = (BmtbBillGenSetup)this.daoHelper.getGenericDao().get(BmtbBillGenSetup.class, billGenSetupNo);
		BmtbBillGenSetup billGenSetupAdHoc = (BmtbBillGenSetup)this.daoHelper.getGenericDao().get(BmtbBillGenSetup.class, NonConfigurableConstants.BILL_GEN_SETUP_AD_HOC);
		if(billGenSetup==null || billGenSetupAdHoc==null)
			throw new Exception("Unable to find the selected bill gen setup");
		List<BmtbBillGenSetup> billGenSetups = new ArrayList<BmtbBillGenSetup>();
		billGenSetups.add(billGenSetup);
		billGenSetups.add(billGenSetupAdHoc);
		
		Calendar requestDateCalendar = Calendar.getInstance();
		//have to be careful here. If we're at December then choosing Jan is next year.
		if(requestDateCalendar.get(Calendar.MONTH)==Calendar.DECEMBER){
			if(Calendar.JANUARY == (monthOfBillGen-1))
				requestDateCalendar.add(Calendar.MONTH, 1);
			else
				requestDateCalendar.set(Calendar.MONTH, monthOfBillGen-1);
		}
		else 
			requestDateCalendar.set(Calendar.MONTH, monthOfBillGen-1);
		requestDateCalendar.set(Calendar.DAY_OF_MONTH, billGenSetup.getBillGenDay());
		
		String hour = billGenSetup.getBillGenTime().split(":")[0];
		String mins = billGenSetup.getBillGenTime().split(":")[1];
		requestDateCalendar.set(Calendar.HOUR_OF_DAY, new Integer(hour));
		requestDateCalendar.set(Calendar.MINUTE, new Integer(mins));
		
		//do checking if settings is e.g. 31 Nov... the request date will become 1st Dec
		//so we need to change to last day of Nov
		while(requestDateCalendar.get(Calendar.MONTH)!=monthOfBillGen-1)
			requestDateCalendar.add(Calendar.DAY_OF_YEAR, -1);
		
		if(requestDateCalendar.get(Calendar.DATE) == Calendar.getInstance().get(Calendar.DATE) &&
				requestDateCalendar.get(Calendar.MONTH) == Calendar.getInstance().get(Calendar.MONTH) &&
				requestDateCalendar.get(Calendar.YEAR) == Calendar.getInstance().get(Calendar.YEAR) &&
				requestDateCalendar.get(Calendar.HOUR) == Calendar.getInstance().get(Calendar.HOUR)){
			//Allow it to pass
		}
		else if(requestDateCalendar.compareTo(Calendar.getInstance())<0)
			throw new BillGenRequestCreationException("Bill gen request date cannot be later than current date");
		
		Date requestDate = new Date(requestDateCalendar.getTimeInMillis());
		
		boolean isAnotherRequestExist = this.daoHelper.getBillGenRequestDao().checkRequestExist(billGenSetups, requestDate, entityNo, null);
		if(isAnotherRequestExist) throw new BillGenRequestExistanceException();
		
		FmtbEntityMaster entity = (FmtbEntityMaster)this.get(FmtbEntityMaster.class, entityNo);
		if(entity==null) throw new ObjectNotFoundException(entityNo, "Entity Master not found");
		
		BmtbBillGenReq newRequest = new BmtbBillGenReq();
		newRequest.setBmtbBillGenSetupBySetupNo(billGenSetup);
		newRequest.setStatus(NonConfigurableConstants.BILL_GEN_REQUEST_STATUS_PENDING);
		newRequest.setBillGenMonth(monthOfBillGen);
		newRequest.setRequestDate(requestDate);
		newRequest.setFmtbEntityMaster(entity);
		newRequest.setBillGenTime(billGenSetup.getBillGenTime());
		return (Integer)this.daoHelper.getGenericDao().save(newRequest, createdBy);
	}
	
	public Integer createAdHocRequest(AmtbAccount topLevelAccount, String createdBy) throws Exception{
		
		//1. Check for Error
		if(this.daoHelper.getBillGenErrorDao().checkForError(topLevelAccount))
			throw new BillGenErrorFoundException();
		
		//2. Check that No other request allocated for this corporate
		if(this.daoHelper.getBillGenRequestDao().checkRequestExist(topLevelAccount, DateUtil.getCurrentDate()))
			throw new BillGenRequestExistanceException();
		
		BmtbBillGenSetup adHocBillGenSetup = (BmtbBillGenSetup)this.daoHelper.getGenericDao().get(BmtbBillGenSetup.class, NonConfigurableConstants.BILL_GEN_SETUP_AD_HOC);
		if(adHocBillGenSetup==null) throw new Exception("Unable to find the selected bill gen setup");
		
		BmtbBillGenReq newRequest = new BmtbBillGenReq();
		newRequest.setBmtbBillGenSetupBySetupNo(adHocBillGenSetup);
		newRequest.setStatus(NonConfigurableConstants.BILL_GEN_REQUEST_STATUS_PENDING);
		newRequest.setBillGenMonth(DateUtil.getCurrentMonth());
		newRequest.setRequestDate(DateUtil.getCurrentDate());
		newRequest.setAmtbAccount(topLevelAccount);
		
		this.daoHelper.getGenericDao().load(topLevelAccount, topLevelAccount.getAccountNo());
		newRequest.getAmtbAccounts().add(topLevelAccount);
		newRequest.getAmtbAccounts().addAll(this.daoHelper.getAccountDao().getBilliableAccountByParentAccount(topLevelAccount));
		newRequest.getAmtbAccounts().addAll(this.daoHelper.getAccountDao().getBilliableAccountByGrandParentAccount(topLevelAccount));
		
		return (Integer)this.daoHelper.getGenericDao().save(newRequest, createdBy);
	}
	
	public Integer createDraftRequest(AmtbAccount topLevelAccount, String createdBy) throws Exception{
		
		//1. Check for Error
		if(this.daoHelper.getBillGenErrorDao().checkForError(topLevelAccount))
			throw new BillGenErrorFoundException();
		
//		//2. Check that No other request allocated for this corporate
//		if(this.daoHelper.getBillGenRequestDao().checkRequestExist(topLevelAccount, DateUtil.getCurrentDate()))
//			throw new BillGenRequestExistanceException();
		
		BmtbBillGenSetup draftBillGenSetup = (BmtbBillGenSetup)this.daoHelper.getGenericDao().get(BmtbBillGenSetup.class, NonConfigurableConstants.BILL_GEN_SETUP_DRAFT);
		if(draftBillGenSetup==null) throw new Exception("Unable to find the selected bill gen setup");
		
		BmtbBillGenReq newRequest = new BmtbBillGenReq();
		newRequest.setBmtbBillGenSetupBySetupNo(draftBillGenSetup);
		newRequest.setStatus(NonConfigurableConstants.BILL_GEN_REQUEST_STATUS_PENDING);
		newRequest.setBillGenMonth(DateUtil.getCurrentMonth());
		newRequest.setRequestDate(DateUtil.getCurrentDate());
		newRequest.setAmtbAccount(topLevelAccount);
		
		this.daoHelper.getGenericDao().load(topLevelAccount, topLevelAccount.getAccountNo());
		newRequest.getAmtbAccounts().add(topLevelAccount);
		newRequest.getAmtbAccounts().addAll(this.daoHelper.getAccountDao().getBilliableAccountByParentAccount(topLevelAccount));
		newRequest.getAmtbAccounts().addAll(this.daoHelper.getAccountDao().getBilliableAccountByGrandParentAccount(topLevelAccount));
		
		return (Integer)this.daoHelper.getGenericDao().save(newRequest, createdBy);
	}
	
	public List<AmtbAccount> searchBillableAccount(String customerNo, String name, String code){
		return this.daoHelper.getAccountDao().getBilliableAccount(customerNo, name, code);
	}
	
	public boolean checkInvoiceExist(Integer accountNo, Integer billGenRequestNo){
		return this.daoHelper.getInvoiceDao().checkInvoiceExist(accountNo, billGenRequestNo);
	}
	
	public List<BmtbBillGenReq> searchRequest(Integer requestNo, String status, Integer setupNo, Date requestDateFrom, Date requestDateTo, Integer entityNo){
		return this.daoHelper.getBillGenRequestDao().get(requestNo, status, setupNo, requestDateFrom, requestDateTo, entityNo);
	}
	public List<BmtbBillGenReq> searchRequest(Integer requestNo, String status, Integer setupNo, Date requestDateFrom, Date requestDateTo, Integer entityNo, String backward){
		return this.daoHelper.getBillGenRequestDao().get(requestNo, status, setupNo, requestDateFrom, requestDateTo, entityNo, backward);
	}
	public boolean checkRequestExist(List<BmtbBillGenSetup> billGenSetups, Date requestDate, Integer entityNo, Integer requestNo){
		return this.daoHelper.getBillGenRequestDao().checkRequestExist(billGenSetups, requestDate, entityNo, requestNo);
	}
	
	public void saveSetupsChanges(List<BmtbBillGenSetup> setups, String updatedBy){
		for(BmtbBillGenSetup setup : setups){
			this.daoHelper.getGenericDao().update(setup, updatedBy);
		}
	}
	
	public String getCustomerNo(Integer accountNo){
		return this.daoHelper.getAccountDao().getCustomerNo(accountNo);
	}
	
	public List<BmtbBillGenError> listAccountsWithError(BmtbBillGenReq billGenRequest){
		return this.daoHelper.getBillGenErrorDao().get(billGenRequest);
	}
	
	public List<Integer> regen(BmtbBillGenReq billGenRequest, List<Listitem> errors, String userId) throws Exception{
		BmtbBillGenSetup adHocSetup = (BmtbBillGenSetup)this.get(BmtbBillGenSetup.class, NonConfigurableConstants.BILL_GEN_SETUP_AD_HOC);
		if(adHocSetup==null) throw new Exception("Unable to find ad hoc bill gen setup in database!");
		
		billGenRequest.setStatus(NonConfigurableConstants.BILL_GEN_REQUEST_STATUS_REGENERATED);
		this.update(billGenRequest, userId);
		
		List<AmtbAccount> nullNullGrouping = new ArrayList<AmtbAccount>();
		List<AmtbAccount> noNullGrouping = new ArrayList<AmtbAccount>();
		List<AmtbAccount> yesNullGrouping = new ArrayList<AmtbAccount>();
		List<AmtbAccount> yesNoGrouping = new ArrayList<AmtbAccount>();
		List<AmtbAccount> yesYesGrouping = new ArrayList<AmtbAccount>();
		
		for(Listitem item : errors){
			BmtbBillGenError error = (BmtbBillGenError)item.getValue();
			if(error.getVdFlag() == null && error.getRwdFlag()==null)
				nullNullGrouping.add(error.getAmtbAccountByBillableAccountNo());
			else if(error.getVdFlag().equals("N") && error.getRwdFlag()==null)
				noNullGrouping.add(error.getAmtbAccountByBillableAccountNo());
			else if(error.getVdFlag().equals("Y") && error.getRwdFlag()==null)
				yesNullGrouping.add(error.getAmtbAccountByBillableAccountNo());
			else if(error.getVdFlag().equals("Y") && error.getRwdFlag().equals("N"))
				yesNoGrouping.add(error.getAmtbAccountByBillableAccountNo());
			else if(error.getVdFlag().equals("Y") && error.getRwdFlag().equals("Y"))
				yesYesGrouping.add(error.getAmtbAccountByBillableAccountNo());
		}
		
		List<Integer> newRequestNos = new ArrayList<Integer>();
		
		if(!nullNullGrouping.isEmpty()){
			newRequestNos.add(this.createRegenRequest(adHocSetup, billGenRequest, nullNullGrouping, userId, null, null));
		}
		if(!noNullGrouping.isEmpty()){
			newRequestNos.add(this.createRegenRequest(adHocSetup, billGenRequest, noNullGrouping, userId, "N", null));
		}
		if(!yesNullGrouping.isEmpty()){
			newRequestNos.add(this.createRegenRequest(adHocSetup, billGenRequest, yesNullGrouping, userId, "Y", null));
		}
		if(!yesNoGrouping.isEmpty()){
			newRequestNos.add(this.createRegenRequest(adHocSetup, billGenRequest, yesNoGrouping, userId, "Y", "N"));
		}
		if(!yesYesGrouping.isEmpty()){
			newRequestNos.add(this.createRegenRequest(adHocSetup, billGenRequest, yesYesGrouping, userId, "Y", "Y"));
		}
		
		return newRequestNos;
	}
	private Integer createRegenRequest(BmtbBillGenSetup adHocSetup, BmtbBillGenReq parentRequest, 
			List<AmtbAccount> accounts, String userId,
			String vdFlag, String rwdFlag){
		
		this.evict(parentRequest.getAmtbAccount());
		
		BmtbBillGenReq newRequest = new BmtbBillGenReq();
		newRequest.setStatus(NonConfigurableConstants.BILL_GEN_REQUEST_STATUS_PENDING);
		newRequest.setBmtbBillGenSetupBySetupNo(adHocSetup);
		newRequest.setBmtbBillGenReq(parentRequest);
		if(parentRequest.getBmtbBillGenSetupByRegenSetupNo()==null) newRequest.setBmtbBillGenSetupByRegenSetupNo(parentRequest.getBmtbBillGenSetupBySetupNo());
		else newRequest.setBmtbBillGenSetupByRegenSetupNo(parentRequest.getBmtbBillGenSetupByRegenSetupNo());
		newRequest.setBillGenMonth(parentRequest.getBillGenMonth());
		newRequest.setRequestDate(DateUtil.getCurrentDate());
		newRequest.setVdFlag(vdFlag);
		newRequest.setRwdFlag(rwdFlag);
		newRequest.setFmtbEntityMaster(parentRequest.getFmtbEntityMaster());
		newRequest.getAmtbAccounts().addAll(accounts);
		
		return (Integer)this.save(newRequest, userId);
	}
	
	public List<AmtbAccount> getBilliableAccountOnlyTopLevelWithEffectiveEntity(String customerNo, String name){
		return this.daoHelper.getAccountDao().getBilliableAccountOnlyTopLevelWithEffectiveEntity(customerNo, name);
	}
}
