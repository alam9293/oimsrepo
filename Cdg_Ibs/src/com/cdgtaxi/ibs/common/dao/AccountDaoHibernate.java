package com.cdgtaxi.ibs.common.dao;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Conjunction;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.Subqueries;

import com.cdgtaxi.ibs.acl.model.SatbAuditLog;
import com.cdgtaxi.ibs.acl.model.SatbUser;
import com.cdgtaxi.ibs.common.ConfigurableConstants;
import com.cdgtaxi.ibs.common.NonConfigurableConstants;
import com.cdgtaxi.ibs.common.model.AmtbAccount;
import com.cdgtaxi.ibs.common.model.AmtbAcctCredLimit;
import com.cdgtaxi.ibs.common.model.AmtbAcctCredTerm;
import com.cdgtaxi.ibs.common.model.AmtbAcctLatePymt;
import com.cdgtaxi.ibs.common.model.AmtbAcctMainContact;
import com.cdgtaxi.ibs.common.model.AmtbAcctSalesperson;
import com.cdgtaxi.ibs.common.model.AmtbAcctSalespersonReq;
import com.cdgtaxi.ibs.common.model.AmtbAcctStatus;
import com.cdgtaxi.ibs.common.model.AmtbBillReq;
import com.cdgtaxi.ibs.common.model.AmtbBillReqFlow;
import com.cdgtaxi.ibs.common.model.AmtbContactPerson;
import com.cdgtaxi.ibs.common.model.AmtbCorporateDetail;
import com.cdgtaxi.ibs.common.model.AmtbCredRevReq;
import com.cdgtaxi.ibs.common.model.AmtbCredRevReqFlow;
import com.cdgtaxi.ibs.common.model.AmtbPersonalDetail;
import com.cdgtaxi.ibs.common.model.AmtbSubscProdReq;
import com.cdgtaxi.ibs.common.model.AmtbSubscTo;
import com.cdgtaxi.ibs.common.model.AmtbSubscToPK;
import com.cdgtaxi.ibs.common.model.FmtbArContCodeMaster;
import com.cdgtaxi.ibs.common.model.IttbRecurringCharge;
import com.cdgtaxi.ibs.common.model.IttbRecurringChargeTagAcct;
import com.cdgtaxi.ibs.common.model.IttbRecurringChargeTagCard;
import com.cdgtaxi.ibs.common.model.PmtbProduct;
import com.cdgtaxi.ibs.common.model.PmtbProductType;
import com.cdgtaxi.ibs.common.model.forms.SearchByAccountForm;
import com.cdgtaxi.ibs.master.model.MstbLatePaymentDetail;
import com.cdgtaxi.ibs.master.model.MstbMasterTable;
import com.cdgtaxi.ibs.master.model.MstbSalesperson;
import com.cdgtaxi.ibs.product.ui.ProductSearchCriteria;
import com.cdgtaxi.ibs.util.DateUtil;
import com.cdgtaxi.ibs.util.StringUtil;
import com.google.common.base.Strings;
import com.mchange.v2.c3p0.C3P0ProxyConnection;

@SuppressWarnings({ "rawtypes" })
public class AccountDaoHibernate extends GenericDaoHibernate implements AccountDao{

	private static final int MAX_ROW = 1;
	/**
	 * This method return an account with given account no.
	 * The top 2 level and bottom 2 level will be joined.
	 * Assuming it is division level, the corp and depts will be pulled out as well.
	 * @param accountNo
	 * @return AmtbAccount
	 */
	public AmtbAccount getAccount(String accountNo){

		DetachedCriteria accountCriteria = DetachedCriteria.forClass(AmtbAccount.class);

		//Assuming pulled out account is dept level
		DetachedCriteria parentAccountCriteria = accountCriteria.createCriteria("amtbAccount", Criteria.LEFT_JOIN); //join division
		DetachedCriteria grandParentAccountCriteria = parentAccountCriteria.createCriteria("amtbAccount", Criteria.LEFT_JOIN); //join corporation

		//Assuming pulled out account is corporation level
		DetachedCriteria childrenAccountCriteria = accountCriteria.createCriteria("amtbAccounts", Criteria.LEFT_JOIN); //join division
		DetachedCriteria grandChildrenAccountCriteria = childrenAccountCriteria.createCriteria("amtbAccounts", Criteria.LEFT_JOIN); //join dept

		//Due to Left Join, Need to do Distinct
		accountCriteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);

		accountCriteria.add(Restrictions.idEq(Integer.parseInt(accountNo)));

		List results = this.findAllByCriteria(accountCriteria);
		if(results.isEmpty()) {
			return null;
		} else {
			return (AmtbAccount) results.get(0);
		}
	}

	@SuppressWarnings("unchecked")
	public List<IttbRecurringChargeTagAcct> getRecurringChargeTagAcct(String tokenId){
		DetachedCriteria recurringCriteria = DetachedCriteria.forClass(IttbRecurringChargeTagAcct.class);
		recurringCriteria.createCriteria("recurringChargeId", "rc", DetachedCriteria.LEFT_JOIN);
		recurringCriteria.createCriteria("amtbAccount", "acct", DetachedCriteria.LEFT_JOIN);
		recurringCriteria.createCriteria("acct.amtbAccount", "acct2", DetachedCriteria.LEFT_JOIN);
		recurringCriteria.createCriteria("acct2.amtbAccount", "acct3", DetachedCriteria.LEFT_JOIN);
		recurringCriteria.add(Restrictions.eq("rc.tokenId", tokenId));
	
		List<IttbRecurringChargeTagAcct> results = this.findAllByCriteria(recurringCriteria);
		if(results.isEmpty()) {
			return null;
		} else {
			return results;
		}

	}
	
	@SuppressWarnings("unchecked")
	public List<IttbRecurringChargeTagCard> getRecurringChargeTagCard(String tokenId){
		DetachedCriteria recurringCriteria = DetachedCriteria.forClass(IttbRecurringChargeTagCard.class);
		
//		recurringCriteria.add(Restrictions.eq("to", rc.getAmtbAccount()));
		recurringCriteria.createCriteria("pmtbProduct","prod", DetachedCriteria.LEFT_JOIN);
		recurringCriteria.createCriteria("prod.pmtbProductType","prodtype", DetachedCriteria.LEFT_JOIN);
		recurringCriteria.createCriteria("recurringChargeId", "rc", DetachedCriteria.LEFT_JOIN);
		recurringCriteria.add(Restrictions.eq("rc.tokenId", tokenId));
	
		List<IttbRecurringChargeTagCard> results = this.findAllByCriteria(recurringCriteria);
		if(results.isEmpty()) {
			return null;
		} else {
			return results;
		}

	}
	
	@SuppressWarnings("unchecked")
	public IttbRecurringChargeTagAcct getAcctRecurringTokenByAccount(AmtbAccount acct){
		DetachedCriteria recurringCriteria = DetachedCriteria.forClass(IttbRecurringChargeTagAcct.class);
		recurringCriteria.createCriteria("amtbAccount", "acct", Criteria.LEFT_JOIN);
		recurringCriteria.createCriteria("recurringChargeId", "token", Criteria.LEFT_JOIN);
		recurringCriteria.add(Restrictions.eq("acct.accountNo", acct.getAccountNo()));
		recurringCriteria.add(Restrictions.gt("token.tokenExpiry", new java.util.Date()));

		// Due to Left Join, Need to do Distinct
		recurringCriteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);

		
		List results = this.findAllByCriteria(recurringCriteria);
		if(results.isEmpty()) {
			return null;
		} else {
			return (IttbRecurringChargeTagAcct) results.get(0);
		}
	}
	
	@SuppressWarnings("unchecked")
	public List<IttbRecurringCharge> searchRConly(String tokenId){
		DetachedCriteria recurringCriteria = DetachedCriteria.forClass(IttbRecurringCharge.class);
		recurringCriteria.add(Restrictions.eq("tokenId", tokenId));

		recurringCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		List<IttbRecurringCharge> results = this.findAllByCriteria(recurringCriteria);
		if(results.isEmpty()) {
			return null;
		} else {
			return results;
		}

	}
	
	@SuppressWarnings("unchecked")
	public List<IttbRecurringCharge> searchRC(ProductSearchCriteria productSearchCriteria){
		DetachedCriteria recurringCriteria = DetachedCriteria.forClass(IttbRecurringCharge.class);
		recurringCriteria.createCriteria("ittbRecurringChargeTagAcct","tagAcct", DetachedCriteria.LEFT_JOIN);
		recurringCriteria.createCriteria("ittbRecurringChargeTagCard","tagCard", DetachedCriteria.LEFT_JOIN);
		recurringCriteria.createCriteria("tagCard.pmtbProduct","prod", DetachedCriteria.LEFT_JOIN);
		
		DetachedCriteria parentAccountCriteria2 = recurringCriteria.createCriteria("prod.amtbAccount", "acct1a", DetachedCriteria.LEFT_JOIN);
		parentAccountCriteria2.createCriteria("amtbAccount", "acct2a", DetachedCriteria.LEFT_JOIN);
		parentAccountCriteria2.createCriteria("acct2a.amtbAccount", "acct3a", DetachedCriteria.LEFT_JOIN);
		
		DetachedCriteria parentAccountCriteria = recurringCriteria.createCriteria("tagAcct.amtbAccount","acct", DetachedCriteria.LEFT_JOIN);
		parentAccountCriteria.createCriteria("amtbAccount", "acct2", DetachedCriteria.LEFT_JOIN);
		parentAccountCriteria.createCriteria("acct2.amtbAccount", "acct3", DetachedCriteria.LEFT_JOIN);
	
		
//		recurringCriteria.add(Restrictions.eq("to", rc.getAmtbAccount()));
	//	
	//	recurringCriteria.createCriteria("amtbAccount", "am", DetachedCriteria.LEFT_JOIN);
		if(!productSearchCriteria.getTokenId().isEmpty()) {
			recurringCriteria.add(Restrictions.eq("tokenId", productSearchCriteria.getTokenId()));
		}
		if(!productSearchCriteria.getCardNoStart().isEmpty()) {
			recurringCriteria.add(Restrictions.eq("prod.cardNo", productSearchCriteria.getCardNoStart()));
		}
		if(!productSearchCriteria.getAccNo().isEmpty()) {
			Disjunction disjunction = Restrictions.disjunction();
			disjunction.add(Restrictions.eq("acct.accountNo", Integer.parseInt(productSearchCriteria.getAccNo())));
			disjunction.add(Restrictions.eq("acct2.accountNo", Integer.parseInt(productSearchCriteria.getAccNo())));
			disjunction.add(Restrictions.eq("acct3.accountNo", Integer.parseInt(productSearchCriteria.getAccNo())));
			disjunction.add(Restrictions.eq("acct1a.accountNo", Integer.parseInt(productSearchCriteria.getAccNo())));
			disjunction.add(Restrictions.eq("acct2a.accountNo", Integer.parseInt(productSearchCriteria.getAccNo())));
			disjunction.add(Restrictions.eq("acct3a.accountNo", Integer.parseInt(productSearchCriteria.getAccNo())));
			recurringCriteria.add(disjunction);			
		}
		if(productSearchCriteria.getExpiryDateFrom()!=null) {
			recurringCriteria.add(Restrictions.ge("tokenExpiry", productSearchCriteria.getExpiryDateFrom()));
			recurringCriteria.add(Restrictions.le("tokenExpiry", productSearchCriteria.getExpiryDateTo()));
		}
		if(productSearchCriteria.getCcExpiryDateFrom()!=null) {
			recurringCriteria.add(Restrictions.ge("creditCardNoExpiry", productSearchCriteria.getCcExpiryDateFrom()));
			recurringCriteria.add(Restrictions.le("creditCardNoExpiry", productSearchCriteria.getCcExpiryDateTo()));
		}
		if(productSearchCriteria.getTokenActive()!=null) {
			recurringCriteria.add(Restrictions.gt("tokenExpiry", new Date()));
		}
		
		recurringCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		List<IttbRecurringCharge> results = this.findAllByCriteria(recurringCriteria);
		if(results.isEmpty()) {
			return null;
		} else {
			return results;
		}

	}
	@SuppressWarnings("unchecked")
	public List<IttbRecurringCharge> searchRC2(ProductSearchCriteria productSearchCriteria){
		DetachedCriteria recurringCriteria = DetachedCriteria.forClass(IttbRecurringCharge.class);
		recurringCriteria.createCriteria("ittbRecurringChargeTagAcct","tagAcct", DetachedCriteria.LEFT_JOIN);
		recurringCriteria.createCriteria("ittbRecurringChargeTagCard","tagCard", DetachedCriteria.LEFT_JOIN);
		recurringCriteria.createCriteria("tagCard.pmtbProduct","prod", DetachedCriteria.LEFT_JOIN);
		recurringCriteria.createCriteria("tagAcct.amtbAccount","acct", DetachedCriteria.LEFT_JOIN);
//		recurringCriteria.add(Restrictions.eq("to", rc.getAmtbAccount()));
	//	
	//	recurringCriteria.createCriteria("amtbAccount", "am", DetachedCriteria.LEFT_JOIN);
		if(!productSearchCriteria.getTokenId().isEmpty()) {
			recurringCriteria.add(Restrictions.eq("tokenId", productSearchCriteria.getTokenId()));
		}
		if(!productSearchCriteria.getCardNoStart().isEmpty()) {
			recurringCriteria.add(Restrictions.eq("prod.cardNo", productSearchCriteria.getCardNoStart()));
		}
		if(!productSearchCriteria.getAccNo().isEmpty()) {
			recurringCriteria.add(Restrictions.eq("acct.accountNo", Integer.parseInt(productSearchCriteria.getAccNo())));
		}
		if(productSearchCriteria.getExpiryDateFrom()!=null) {
			recurringCriteria.add(Restrictions.ge("tokenExpiry", productSearchCriteria.getExpiryDateFrom()));
			recurringCriteria.add(Restrictions.le("tokenExpiry", productSearchCriteria.getExpiryDateTo()));
		}
		
		if(productSearchCriteria.getTokenActive()!=null) {
			recurringCriteria.add(Restrictions.gt("tokenExpiry", new Date()));
		}
		
		recurringCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		List<IttbRecurringCharge> results = this.findAllByCriteria(recurringCriteria);
		if(results.isEmpty()) {
			return null;
		} else {
			return results;
		}
	}

	public AmtbAccount getRawAccount(String custNo, String divCode, String deptCode, String type)
	{
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(AmtbAccount.class);
		if(type.equals("DEPT"))
		{
			//above criteria id dept
			DetachedCriteria accountCriteria = detachedCriteria.createCriteria("amtbAccount", Criteria.LEFT_JOIN); //join div
			DetachedCriteria parentAccountCriteria = accountCriteria.createCriteria("amtbAccount", Criteria.LEFT_JOIN); //join corp
			detachedCriteria.add(Restrictions.eq("accountCategory", type));
			detachedCriteria.add(Restrictions.eq("code", deptCode));
			accountCriteria.add(Restrictions.eq("accountCategory", "DIV"));
			accountCriteria.add(Restrictions.eq("code", divCode));
			parentAccountCriteria.add(Restrictions.eq("custNo", custNo));
		}
		else if(type.equals("DIV")) //div or sapp
		{
			//above criteria is div
			DetachedCriteria accountCriteria = detachedCriteria.createCriteria("amtbAccount", Criteria.LEFT_JOIN); //join corp
//			detachedCriteria.add(Restrictions.eq("accountCategory", type));

			detachedCriteria.add(Restrictions.or(Restrictions.eq("accountCategory", "DIV"), Restrictions.eq("accountCategory", "SAPP")));
			detachedCriteria.add(Restrictions.eq("code", divCode));
			accountCriteria.add(Restrictions.eq("custNo", custNo));
		}
		else
		{
			detachedCriteria.add(Restrictions.eq("custNo", custNo));
		}
		
		List<AmtbAccount> results = this.findAllByCriteria(detachedCriteria);
		logger.info("***********Results Count"+results.size());

		if(results.isEmpty()) {
			return null;
		} else {
			return (AmtbAccount) results.get(0);
		}
	}
	public AmtbAccount getAccountByCustNoAndCode(String custNo, String code, String level){

		DetachedCriteria accountCriteria = DetachedCriteria.forClass(AmtbAccount.class);

		//Assuming pulled out account is div level
		DetachedCriteria parentAccountCriteria = accountCriteria.createCriteria("amtbAccount", "acct2", Criteria.LEFT_JOIN); //join division
		
		parentAccountCriteria.add(Restrictions.eq("custNo", custNo));
		
		accountCriteria.add(Restrictions.eq("code", code));
		accountCriteria.add(Restrictions.eq("accountCategory", level));
		
		
		//Due to Left Join, Need to do Distinct
		accountCriteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);


		List results = this.findAllByCriteria(accountCriteria);
		if(results.isEmpty()) {
			return null;
		} else {
			return (AmtbAccount) results.get(0);
		}
	}
	public AmtbAccount getAccountByCustNoAndCodeAndCode(String custNo, String code, String level, String code2){

		DetachedCriteria accountCriteria = DetachedCriteria.forClass(AmtbAccount.class);

		//Assuming pulled out account is dept level
		DetachedCriteria parentAccountCriteria = accountCriteria.createCriteria("amtbAccount", "acct2", Criteria.LEFT_JOIN); //join division
		DetachedCriteria grandParentAccountCriteria = parentAccountCriteria.createCriteria("amtbAccount", "acct3", Criteria.LEFT_JOIN); //join corporation

		grandParentAccountCriteria.add(Restrictions.eq("custNo", custNo));
		parentAccountCriteria.add(Restrictions.eq("code", code2));
		accountCriteria.add(Restrictions.eq("code", code));
		accountCriteria.add(Restrictions.eq("accountCategory", level));
		
		
		//Due to Left Join, Need to do Distinct
		accountCriteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);


		List results = this.findAllByCriteria(accountCriteria);
		if(results.isEmpty()) {
			return null;
		} else {
			return (AmtbAccount) results.get(0);
		}
	}
	public AmtbAccount getAccountsByCustNo(String custNo){

		DetachedCriteria accountCriteria = DetachedCriteria.forClass(AmtbAccount.class);

		//Assuming pulled out account is dept level
		DetachedCriteria parentAccountCriteria = accountCriteria.createCriteria("amtbAccount", Criteria.LEFT_JOIN); //join division
		DetachedCriteria grandParentAccountCriteria = parentAccountCriteria.createCriteria("amtbAccount", Criteria.LEFT_JOIN); //join corporation

		//Assuming pulled out account is corporation level
		DetachedCriteria childrenAccountCriteria = accountCriteria.createCriteria("amtbAccounts", Criteria.LEFT_JOIN); //join division
		DetachedCriteria grandChildrenAccountCriteria = childrenAccountCriteria.createCriteria("amtbAccounts", Criteria.LEFT_JOIN); //join dept

		//Due to Left Join, Need to do Distinct
		accountCriteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);

		accountCriteria.add(Restrictions.eq("custNo",custNo));

		List results = this.findAllByCriteria(accountCriteria);
		if(results.isEmpty()) {
			return null;
		} else {
			return (AmtbAccount) results.get(0);
		}
	}
	public AmtbAccount getAccountWithParentandContact(String accountNo){

		DetachedCriteria accountCriteria = DetachedCriteria.forClass(AmtbAccount.class);

		DetachedCriteria accountMainContactCriteria = accountCriteria.createCriteria("amtbAcctMainContacts", Criteria.LEFT_JOIN);
		accountMainContactCriteria.createCriteria("amtbContactPerson", DetachedCriteria.LEFT_JOIN);
//		accountMainContactCriteria.add(Restrictions.eq("comp_id.mainContactType", NonConfigurableConstants.MAIN_CONTACT_TYPE_SHIPPING));
		 
		
		//Assuming pulled out account is dept level
		DetachedCriteria parentAccountCriteria = accountCriteria.createCriteria("amtbAccount", Criteria.LEFT_JOIN); //join division
		DetachedCriteria parentAcctMainContactCriteria = parentAccountCriteria.createCriteria("amtbAcctMainContacts", Criteria.LEFT_JOIN);
		parentAcctMainContactCriteria.createCriteria("amtbContactPerson", DetachedCriteria.LEFT_JOIN);
//		parentAcctMainContactCriteria.add(Restrictions.eq("comp_id.mainContactType", NonConfigurableConstants.MAIN_CONTACT_TYPE_SHIPPING));
		
		DetachedCriteria grandParentAccountCriteria = parentAccountCriteria.createCriteria("amtbAccount", Criteria.LEFT_JOIN); //join corporation
		DetachedCriteria grandParentAcctMainContactCriteria = grandParentAccountCriteria.createCriteria("amtbAcctMainContacts", Criteria.LEFT_JOIN);
		grandParentAcctMainContactCriteria.createCriteria("amtbContactPerson", DetachedCriteria.LEFT_JOIN);
//		grandParentAcctMainContactCriteria.add(Restrictions.eq("comp_id.mainContactType", NonConfigurableConstants.MAIN_CONTACT_TYPE_SHIPPING));
		
		
		//Due to Left Join, Need to do Distinct
		accountCriteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		accountCriteria.add(Restrictions.idEq(Integer.parseInt(accountNo)));

		List results = this.findAllByCriteria(accountCriteria);
		if(results.isEmpty()) {
			return null;
		} else {
			return (AmtbAccount) results.get(0);
		}
	}
	public AmtbAccount getAccountWithParent(String accountNo){

		DetachedCriteria accountCriteria = DetachedCriteria.forClass(AmtbAccount.class);

		//Assuming pulled out account is dept level
		DetachedCriteria parentAccountCriteria = accountCriteria.createCriteria("amtbAccount", Criteria.LEFT_JOIN); //join division
		DetachedCriteria grandParentAccountCriteria = parentAccountCriteria.createCriteria("amtbAccount", Criteria.LEFT_JOIN); //join corporation

		//Due to Left Join, Need to do Distinct
		accountCriteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		accountCriteria.add(Restrictions.idEq(Integer.parseInt(accountNo)));

		List results = this.findAllByCriteria(accountCriteria);
		if(results.isEmpty()) {
			return null;
		} else {
			return (AmtbAccount) results.get(0);
		}
	}
	//	public AmtbAccount getAccountByAccountNo(String accountNo){
	//
	//		DetachedCriteria accountCriteria = DetachedCriteria.forClass(AmtbAccount.class);
	//
	//		//Assuming pulled out account is dept level
	//		accountCriteria.add(Restrictions.eq("accountNo",new Integer(accountNo), MatchMode.ANYWHERE));
	//
	//	//	accountCriteria.add(Restrictions.idEq(Integer.parseInt(accountNo)));
	//
	//		List results = this.findAllByCriteria(accountCriteria);
	//		if(results.isEmpty()) return null;
	//		else return (AmtbAccount) results.get(0);
	//	}
	//

	@SuppressWarnings("unchecked")
	public List<AmtbAccount> getAccountNoAndName (String accNo,String name){

		logger.info("*****Retriving data by account(cust No) :"+accNo+" and account name :"+name+".");

		DetachedCriteria accountCriteria = DetachedCriteria.forClass(AmtbAccount.class);

		if(accNo!=null && accNo.trim().length()>0) {
			//accountCriteria.add(Restrictions.ilike("custNo",accNo, MatchMode.ANYWHERE));
			//change ilike to exact match eq
			//accountCriteria.add(Restrictions.or(Restrictions.eq("custNo",accNo, MatchMode.ANYWHERE),Restrictions.eq("custNo",new Integer(accNo).toString())));
			accountCriteria.add(Restrictions.eq("custNo",accNo));
		}

		if(name!=null && name.trim().length()>0)
		{
			accountCriteria.add(Restrictions.ilike("accountName",name, MatchMode.ANYWHERE));
		}
		
		//must be top level
		accountCriteria.add(Restrictions.in("accountCategory", new String[]{NonConfigurableConstants.ACCOUNT_CATEGORY_APPLICANT, NonConfigurableConstants.ACCOUNT_CATEGORY_CORPORATE}));

		//accountCriteria.add(Restrictions.isNull("amtbParentAccount"));
		//if(accNo.trim().length()>0 || name.trim().length()>0) {
		//	accountCriteria.add(Restrictions.isNull("amtbAccount"));
		//}

		logger.info("***********This is the criteria"+accountCriteria.toString());
		//		return this.findDefaultMaxResultByCriteria(accountCriteria);
		
		accountCriteria.addOrder(Order.asc("accountName"));

		List<AmtbAccount> results = this.findAllByCriteria(accountCriteria);
		logger.info("***********Results Count"+results.size());

		if(results.isEmpty()) {
			return null;
		} else {
			return  results;
		}

	}

	
	@SuppressWarnings("unchecked")
	public List<AmtbAccount> getTopLevelAccountsWithEntity(String custNo,String name){

		DetachedCriteria dc = DetachedCriteria.forClass(AmtbAccount.class);
		dc.createCriteria("fmtbArContCodeMaster", "ar", DetachedCriteria.LEFT_JOIN);
		dc.createCriteria("ar.fmtbEntityMaster", DetachedCriteria.LEFT_JOIN);
		
		if(!Strings.isNullOrEmpty(custNo)) {
			dc.add(Restrictions.eq("custNo",custNo));
		}

		if(!Strings.isNullOrEmpty(name)){
			dc.add(Restrictions.ilike("accountName",name, MatchMode.ANYWHERE));
		}
		
		//must be top level
		dc.add(Restrictions.in("accountCategory", new String[]{NonConfigurableConstants.ACCOUNT_CATEGORY_APPLICANT, NonConfigurableConstants.ACCOUNT_CATEGORY_CORPORATE}));
	
		dc.addOrder(Order.asc("accountName"));
		return findAllByCriteria(dc);
	}
	
	
	@SuppressWarnings("unchecked")

	public AmtbAccount getAccountInfo(String accNo){
		//Updated method
		logger.debug("Retriving data(Corp or Applicant Info) by Account No "+accNo);
		DetachedCriteria accountCriteria = DetachedCriteria.forClass(AmtbAccount.class);
		
		if(accNo.trim().length()>0){
			accountCriteria.add(Restrictions.eq("accountNo",new Integer(accNo)));
			DetachedCriteria mainContactCriteria = accountCriteria.createCriteria("amtbAcctMainContacts", DetachedCriteria.LEFT_JOIN);
			mainContactCriteria.createCriteria("amtbContactPerson", DetachedCriteria.LEFT_JOIN);
			
			mainContactCriteria.add(Restrictions.eq("comp_id.mainContactType", NonConfigurableConstants.MAIN_CONTACT_TYPE_SHIPPING));
			
			}
		logger.debug("This one is Detached Criteria"+accountCriteria.toString());
		List results = this.findMaxResultByCriteria(accountCriteria,1);
		if(results.isEmpty()) {
			return null;
		} else {
			return (AmtbAccount)results.get(0);
		}
	}
	@SuppressWarnings("unchecked")
	public List<AmtbAccount> getDivOrSubApplInfo(String accNo){

		logger.info("Retrieving data 1(Division list or Sub Applicant List) by Account No "+accNo);
		DetachedCriteria divOrSubApplCriteria = DetachedCriteria.forClass(AmtbAccount.class);
		
		List results = null;
		if(accNo.trim().length()>0){

			DetachedCriteria parentAccountCriteria = divOrSubApplCriteria.createCriteria("amtbAccount", DetachedCriteria.LEFT_JOIN);
			parentAccountCriteria.add(Restrictions.eq("accountNo",new Integer(accNo)));
			
			divOrSubApplCriteria.addOrder(Order.asc("accountName"));
			results = this.findDefaultMaxResultByCriteria(divOrSubApplCriteria);
			if(results.isEmpty()) {
				return null;
			} else {
				return results;
			}
		}
		else
			return null;		
	}	
	@SuppressWarnings("unchecked")
	public List<AmtbAccount> getDivOrSubApplInfo2(String accNo){
		logger.info("Retrieving data 2(Division list or Sub Applicant List) by Account No "+accNo);
		
		if(accNo.trim().length()>0){
			Session session = this.getHibernateTemplate().getSessionFactory().getCurrentSession();
			Transaction txn = session.beginTransaction();
			Query query = session.createSQLQuery("select acct.account_no from AMTB_ACCOUNT acct inner join ( select ACCOUNT_NO, max(EFFECTIVE_DT) as EFFECTIVE_DT from AMTB_ACCT_STATUS where EFFECTIVE_DT < SYSTIMESTAMP group by ACCOUNT_NO ) last_status on acct.ACCOUNT_NO = last_status.ACCOUNT_NO and acct.account_no in ( select this_.account_no from AMTB_ACCOUNT "
					+ "this_ inner join AMTB_ACCOUNT amtbaccoun2_ on this_.PARENT_NO=amtbaccoun2_.ACCOUNT_NO and amtbaccoun2_.ACCOUNT_NO = :accNo ) "
					+ "inner join AMTB_ACCT_STATUS status on status.ACCOUNT_NO = last_status.ACCOUNT_NO and status.EFFECTIVE_DT = last_status.EFFECTIVE_DT and status.acct_status != 'C'");
			query.setString("accNo", accNo!=null&&accNo.trim().length()!=0?accNo:"%");
			logger.info("test1 = " + (accNo!=null&&accNo.trim().length()!=0?accNo:"%"));
			logger.info("sql = " + query.getQueryString());
			List<BigDecimal> acctNos = query.list();
			logger.info("Size = " + acctNos.size());
			txn.commit();
			List<AmtbAccount> returnList = new ArrayList<AmtbAccount>();
			if(acctNos.isEmpty()){
				return returnList;
			}
			List<Integer> accountNos = new ArrayList<Integer>();
			for(BigDecimal acctNo : acctNos){
				if(!accountNos.contains(acctNo.intValue())){
					accountNos.add(acctNo.intValue());
				}
				if(accountNos.size()>=ConfigurableConstants.getMaxQueryResult()+1){
					break;
				}
			}
			for(int i=0;i<accountNos.size();i+=1000){
				List<Integer> tempList = accountNos.subList(i, i+1000>accountNos.size()?accountNos.size():i+1000);
				logger.info(tempList);
				DetachedCriteria accountCriteria = DetachedCriteria.forClass(AmtbAccount.class);
				accountCriteria.addOrder(Order.asc("custNo"));
				accountCriteria.add(Restrictions.in("accountNo", tempList));
				accountCriteria
					.createCriteria("amtbAcctMainContacts", DetachedCriteria.LEFT_JOIN)
					.createCriteria("amtbContactPerson", DetachedCriteria.LEFT_JOIN);
				accountCriteria.createCriteria("amtbAcctType", DetachedCriteria.LEFT_JOIN);
				accountCriteria.createCriteria("amtbAcctStatuses", DetachedCriteria.LEFT_JOIN);
				DetachedCriteria arContCodeMasterCriteria = accountCriteria.createCriteria("fmtbArContCodeMaster", DetachedCriteria.LEFT_JOIN);
				arContCodeMasterCriteria.createCriteria("fmtbEntityMaster", DetachedCriteria.LEFT_JOIN);
				List<AmtbAccount> accounts = this.findAllByCriteria(accountCriteria);
				returnList.addAll(accounts);
			}
			return returnList;
		}
		else
			return null;		
	}
	
	@SuppressWarnings("unchecked")
	public List<AmtbAccount> getDepartmentInfoByDivisionAcctNo(String accNo){

		logger.info("Retriving Department List by Division's Account No "+accNo);
		DetachedCriteria divOrSubApplCriteria = DetachedCriteria.forClass(AmtbAccount.class);
		if(accNo.trim().length()>0){
			DetachedCriteria parentAccountCriteria = divOrSubApplCriteria.createCriteria("amtbAccount", DetachedCriteria.LEFT_JOIN);
			parentAccountCriteria.createCriteria("amtbAccount", DetachedCriteria.LEFT_JOIN);
			parentAccountCriteria.add(Restrictions.eq("accountNo",new Integer(accNo)));
			
			divOrSubApplCriteria.addOrder(Order.asc("accountName"));
			
			List results = this.findDefaultMaxResultByCriteria(divOrSubApplCriteria);
			if(results.isEmpty()) {
				return null;
			} else {
				return results;
			}
		}
		else
			return null;
	}
	public List<AmtbAccount> getDepartmentInfo(String accNo){

		DetachedCriteria dc = DetachedCriteria.forClass(AmtbAccount.class);
		if(accNo.trim().length()>0){
			DetachedCriteria parentDc = dc.createCriteria("amtbAccount", DetachedCriteria.LEFT_JOIN);
			DetachedCriteria grandDc = parentDc.createCriteria("amtbAccount", DetachedCriteria.LEFT_JOIN);
			grandDc.add(Restrictions.eq("accountNo",new Integer(accNo)));
			
			dc.addOrder(Order.asc("accountName"));
			
			List results = this.findDefaultMaxResultByCriteria(dc);
			if(results.isEmpty()) {
				return null;
			} else {
				return results;
			}
		}
		else
			return null;
	}
	/*private void insertTest(){
		// testing for list
		Calendar now = Calendar.getInstance();
		System.out.println("Time started = " + now.getTime());
		FmtbArContCodeMaster arCtrl = (FmtbArContCodeMaster)MasterSetup.getEntityManager().getDetail(1);
		AmtbAcctType acctType = (AmtbAcctType)this.findAllByCriteria(DetachedCriteria.forClass(AmtbAcctType.class).add(Restrictions.eq("acctTypeNo",1))).get(0);
		SatbUser user = (SatbUser)this.findAllByCriteria(DetachedCriteria.forClass(SatbUser.class).add(Restrictions.eq("userId", (long)3))).get(0);
		MstbMasterTable country = ConfigurableConstants.getMasterTable(ConfigurableConstants.COUNTRY_MASTER_CODE, ConfigurableConstants.COUNTRY_MASTER_CODE_SG);
		MstbAdminFeeMaster admin = (MstbAdminFeeMaster)MasterSetup.getAdminFeeManager().getMaster(1);
		MstbCreditTermMaster creditTerm = (MstbCreditTermMaster)MasterSetup.getCreditTermManager().getMaster(1);
		MstbLatePaymentMaster late = (MstbLatePaymentMaster)MasterSetup.getLatePaymentManager().getMaster(1);
		MstbSalesperson sales = (MstbSalesperson)MasterSetup.getSalespersonManager().getMaster(1);
		PmtbProductType productType = (PmtbProductType)this.findAllByCriteria(DetachedCriteria.forClass(PmtbProductType.class).add(Restrictions.eq("productTypeId", "CC"))).get(0);
		MstbMasterTable statusReason = ConfigurableConstants.getMasterTable("IR", "IC");
		MstbMasterTable industry = ConfigurableConstants.getMasterTable("IND","F");
		String account_no_prefix = "9";
		//String product_no_prefix = "6010896501";
		String product_no_prefix = "9";
		int account_count = 1;
		int child_account_count_per_level = 1000;
		double credit_limit_per_account_and_product = 100.00;
		int product_per_account = 1;
		int account_counter = 1, product_counter = 1;
		List<AmtbAccount> accounts2 = new ArrayList<AmtbAccount>();
		//List<AmtbCorporateDetail> corpDetails = new ArrayList<AmtbCorporateDetail>();
		List<AmtbAcctCredLimit> creditLimits = new ArrayList<AmtbAcctCredLimit>();
		List<AmtbContactPerson> contacts = new ArrayList<AmtbContactPerson>();
		List<AmtbAcctMainContact> mains = new ArrayList<AmtbAcctMainContact>();
		List<AmtbAcctSalesperson> salespersons = new ArrayList<AmtbAcctSalesperson>();
		List<AmtbAcctStatus> statuses = new ArrayList<AmtbAcctStatus>();
		List<AmtbAcctAdminFee> adminFees = new ArrayList<AmtbAcctAdminFee>();
		List<AmtbAcctBillCycle> cycles = new ArrayList<AmtbAcctBillCycle>();
		List<AmtbAcctCredTerm> creditTerms = new ArrayList<AmtbAcctCredTerm>();
		List<AmtbAcctLatePymt> lates = new ArrayList<AmtbAcctLatePymt>();
		List<PmtbProduct> products = new ArrayList<PmtbProduct>();
		List<PmtbProductCreditLimit> productCreditLimits = new ArrayList<PmtbProductCreditLimit>();
		List<PmtbProductStatus> productStatuses = new ArrayList<PmtbProductStatus>();
		for(int i=0;i<account_count;i++){
			String corp_no = account_no_prefix + account_counter++;
			while(corp_no.length()<6){
				corp_no = corp_no.substring(0, account_no_prefix.length()) + "0" + corp_no.substring(account_no_prefix.length());
			}
			AmtbAccount account = new AmtbAccount();
			account.setCustNo(corp_no);
			account.setAccountCategory("CORP");
			account.setCreditLimit(new BigDecimal(credit_limit_per_account_and_product));
			account.setCreditBalance(new BigDecimal(credit_limit_per_account_and_product));
			account.setDeposit(new BigDecimal(credit_limit_per_account_and_product));
			account.setAccountName("AUTO GENERATED");
			account.setNameOnCard("AUTO GENERATED");
			account.setInvoiceFormat(NonConfigurableConstants.INVOICE_FORMAT_ACCOUNT);
			account.setInvoiceSorting(NonConfigurableConstants.INVOICE_SORTING_CARD);
			account.setCreatedDt(DateUtil.getCurrentTimestamp());
			account.setCreatedBy("auto");
			account.setVersion(0);
			account.setFmtbArContCodeMaster(arCtrl);
			account.setAmtbAcctType(acctType);
			accounts2.add(account);
			account.setAmtbCorporateDetails(new HashSet<AmtbCorporateDetail>());
			account.getAmtbCorporateDetails().add(new AmtbCorporateDetail("AUTO TEL", null, null, null, null, null, null, null, null, "AUTO STREET", null, null, null, null, "POSTAL", "N", 0, industry, null, country, account));
			// corp credit limit
			AmtbAcctCredLimit creditLimit = new AmtbAcctCredLimit("P", new BigDecimal(credit_limit_per_account_and_product), DateUtil.getCurrentTimestamp(), null, "AUTO GENERATED", 0, account, user);
			creditLimits.add(creditLimit);
			//account.getAmtbAcctCredLimits().add(creditLimit);
			// contact
			AmtbContactPerson contact = new AmtbContactPerson("AUTO GENERATED", null, "AUTO", null, null, null, null, null, null, null, null, null, "N", "N", null, null, "AUTO GENERATED", null, null, "AUTO", null, null, null, null, null, null, 0, country, null, null, account, new HashSet<AmtbAcctMainContact>(), new HashSet<IttbCpLogin>());
			contacts.add(contact);
			//account.getAmtbContactPersons().add(contact);
			// main contacts
			AmtbAcctMainContact mainContact = new AmtbAcctMainContact(new AmtbAcctMainContactPK(NonConfigurableConstants.MAIN_CONTACT_TYPE_BILLING, account), 0, contact);
			mains.add(mainContact);
			//contact.getAmtbAcctMainContacts().add(mainContact);
			//account.getAmtbAcctMainContacts().add(mainContact);
			mainContact = new AmtbAcctMainContact(new AmtbAcctMainContactPK(NonConfigurableConstants.MAIN_CONTACT_TYPE_SHIPPING, account), 0, contact);
			mains.add(mainContact);
			//contact.getAmtbAcctMainContacts().add(mainContact);
			//account.getAmtbAcctMainContacts().add(mainContact);
			// sales person
			AmtbAcctSalesperson salesperson = new AmtbAcctSalesperson(DateUtil.getCurrentTimestamp(), null, 0, sales, account, null);
			salespersons.add(salesperson);
			//account.getAmtbAcctSalespersons().add(salesperson);
			// status
			AmtbAcctStatus status = new AmtbAcctStatus(DateUtil.getCurrentTimestamp(), "AUTO GENERATED", "A", 0, null, account, user);
			statuses.add(status);
			//account.getAmtbAcctStatuses().add(status);
			// admin fee
			AmtbAcctAdminFee acctAdmin = new AmtbAcctAdminFee(DateUtil.getCurrentDate(), 0, account, admin);
			adminFees.add(acctAdmin);
			//account.getAmtbAcctAdminFees().add(acctAdmin);
			// billing cycle
			AmtbAcctBillCycle billingCycle = new AmtbAcctBillCycle("M", DateUtil.getCurrentDate(), 0, account);
			cycles.add(billingCycle);
			//account.getAmtbAcctBillCycles().add(billingCycle);
			// credit term
			AmtbAcctCredTerm acctCreditTerm = new AmtbAcctCredTerm(DateUtil.getCurrentDate(), 0, account, creditTerm);
			creditTerms.add(acctCreditTerm);
			//account.getAmtbAcctCredTerms().add(acctCreditTerm);
			// late payment
			AmtbAcctLatePymt acctLate = new AmtbAcctLatePymt(DateUtil.getCurrentDate(), 0, account, late);
			lates.add(acctLate);
			//account.getAmtbAcctLatePymts().add(acctLate);
			// DIVISION
			for(int j=0;j<child_account_count_per_level;j++){
				String division_no = account_no_prefix + account_counter++;
				while(division_no.length()<6){
					division_no = division_no.substring(0, account_no_prefix.length()) + "0" + division_no.substring(account_no_prefix.length());
				}
				String code = ""+j;
				while(code.length()<4){
					code = "0" + code;
				}
				AmtbAccount div = new AmtbAccount();
				div.setAccountCategory("DIV");
				div.setCreditLimit(new BigDecimal(credit_limit_per_account_and_product));
				div.setCreditBalance(new BigDecimal(credit_limit_per_account_and_product));
				div.setDeposit(new BigDecimal(credit_limit_per_account_and_product));
				div.setNameOnCard("AUTO GENERATED");
				div.setAccountName("AUTO GENERATED");
				div.setCode(code);
				div.setCreatedDt(DateUtil.getCurrentTimestamp());
				div.setCreatedBy("auto");
				div.setVersion(0);
				div.setAmtbAccount(account);
				accounts2.add(div);
				// division credit limit
				creditLimit = new AmtbAcctCredLimit("P", new BigDecimal(credit_limit_per_account_and_product), DateUtil.getCurrentTimestamp(), null, "AUTO GENERATED", 0, div, user);
				creditLimits.add(creditLimit);
				// main contacts
				mainContact = new AmtbAcctMainContact(new AmtbAcctMainContactPK(NonConfigurableConstants.MAIN_CONTACT_TYPE_BILLING, div), 0, contact);
				mains.add(mainContact);
				mainContact = new AmtbAcctMainContact(new AmtbAcctMainContactPK(NonConfigurableConstants.MAIN_CONTACT_TYPE_SHIPPING, div), 0, contact);
				mains.add(mainContact);
				// status
				status = new AmtbAcctStatus(DateUtil.getCurrentTimestamp(), "AUTO GENERATED", "A", 0, null, div, user);
				statuses.add(status);
				for(int k=0;k<child_account_count_per_level/100;k++){
					String dept_no = account_no_prefix + account_counter++;
					while(dept_no.length()!=6){
						dept_no = dept_no.substring(0, account_no_prefix.length()) + "0" + dept_no.substring(account_no_prefix.length());
					}
					String code2 = ""+j;
					while(code2.length()<4){
						code2 = "0" + code2;
					}
					AmtbAccount dept = new AmtbAccount();
					dept.setAccountCategory("DEPT");
					dept.setCreditLimit(new BigDecimal(credit_limit_per_account_and_product));
					dept.setCreditBalance(new BigDecimal(credit_limit_per_account_and_product));
					dept.setDeposit(new BigDecimal(credit_limit_per_account_and_product));
					dept.setNameOnCard("AUTO GENERATED");
					dept.setAccountName("AUTO GENERATED");
					dept.setCreatedDt(DateUtil.getCurrentTimestamp());
					dept.setCreatedBy("auto");
					dept.setVersion(0);
					dept.setAmtbAccount(div);
					accounts2.add(dept);
					// dept credit limit
					creditLimit = new AmtbAcctCredLimit("P", new BigDecimal(credit_limit_per_account_and_product), DateUtil.getCurrentTimestamp(), null, "AUTO GENERATED", 0, dept, user);
					creditLimits.add(creditLimit);
					// main contacts
					mainContact = new AmtbAcctMainContact(new AmtbAcctMainContactPK(NonConfigurableConstants.MAIN_CONTACT_TYPE_BILLING, dept), 0, contact);
					mains.add(mainContact);
					mainContact = new AmtbAcctMainContact(new AmtbAcctMainContactPK(NonConfigurableConstants.MAIN_CONTACT_TYPE_SHIPPING, dept), 0, contact);
					mains.add(mainContact);
					// status
					status = new AmtbAcctStatus(DateUtil.getCurrentTimestamp(), "AUTO GENERATED", "A", 0, null, dept, user);
					statuses.add(status);
					// products
					for(int l=0;l<product_per_account;l++){
						String product_no = product_no_prefix + product_counter++;
						while(product_no.length()<16){
							product_no = product_no.substring(0, product_no_prefix.length()) + "0" + product_no.substring(product_no_prefix.length());
						}
						// product
						PmtbProduct product = new PmtbProduct(product_no, "NAMELESS", new BigDecimal(credit_limit_per_account_and_product), null, new BigDecimal(credit_limit_per_account_and_product), DateUtil.getCurrentDate(), DateUtil.getLastDateOfMonth(DateUtil.getCurrentDate()), null, null, null, null, null, null, null, null, null, null, null, 0, "Y", null, "A", "N", null, null, null, null, null, null, null, product_no, null, null, productType, dept, null, null, null, null, null, null, null, null, new HashSet<IttbCpLogin>(),new HashSet<IttbCpGuestProduct>(), null, null, null, null, null);
						products.add(product);
						// product credit limit
						PmtbProductCreditLimit productCreditLimit = new PmtbProductCreditLimit("P", new BigDecimal(credit_limit_per_account_and_product), DateUtil.getCurrentTimestamp(), null, "AUTO GENERATED", 0, null, null, null, null, product);
						productCreditLimits.add(productCreditLimit);
						// product status
						PmtbProductStatus productStatus = new PmtbProductStatus("N", "A", DateUtil.getCurrentTimestamp(), "AUTO GENERATED", 0, null, null, null, null, statusReason, product);
						productStatuses.add(productStatus);
					}
				}
				// products
				for(int l=0;l<product_per_account;l++){
					String product_no = product_no_prefix + product_counter++;
					while(product_no.length()<16){
						product_no = product_no.substring(0, product_no_prefix.length()) + "0" + product_no.substring(product_no_prefix.length());
					}
					// product
					PmtbProduct product = new PmtbProduct(product_no, "NAMELESS", new BigDecimal(credit_limit_per_account_and_product), null, new BigDecimal(credit_limit_per_account_and_product), DateUtil.getCurrentDate(), DateUtil.getLastDateOfMonth(DateUtil.getCurrentDate()), null, null, null, null, null, null, null, null, null, null, null, 0, "Y", null, "A", "N", null, null, null, null, null, null, null, product_no, null, null, productType, div, null, null, null, null, null, null, null, null, new HashSet<IttbCpLogin>(),new HashSet<IttbCpGuestProduct>(), null, null, null, null, null);
					products.add(product);
					// product credit limit
					PmtbProductCreditLimit productCreditLimit = new PmtbProductCreditLimit("P", new BigDecimal(credit_limit_per_account_and_product), DateUtil.getCurrentTimestamp(), null, "AUTO GENERATED", 0, null, null, null, null, product);
					productCreditLimits.add(productCreditLimit);
					// product status
					PmtbProductStatus productStatus = new PmtbProductStatus("N", "A", DateUtil.getCurrentTimestamp(), "AUTO GENERATED", 0, null, null, null, null, statusReason, product);
					productStatuses.add(productStatus);
				}
			}
			// products
			for(int l=0;l<product_per_account;l++){
				String product_no = product_no_prefix + product_counter++;
				while(product_no.length()<16){
					product_no = product_no.substring(0, product_no_prefix.length()) + "0" + product_no.substring(product_no_prefix.length());
				}
				// product
				PmtbProduct product = new PmtbProduct(product_no, "NAMELESS", new BigDecimal(credit_limit_per_account_and_product), null, new BigDecimal(credit_limit_per_account_and_product), DateUtil.getCurrentDate(), DateUtil.getLastDateOfMonth(DateUtil.getCurrentDate()), null, null, null, null, null, null, null, null, null, null, null, 0, "Y", null, "A", "N", null, null, null, null, null, null, null, product_no, null, null, productType, account, null, null, null, null, null, null, null, null, new HashSet<IttbCpLogin>(),new HashSet<IttbCpGuestProduct>(), null, null, null, null, null);
				products.add(product);
				// product credit limit
				PmtbProductCreditLimit productCreditLimit = new PmtbProductCreditLimit("P", new BigDecimal(credit_limit_per_account_and_product), DateUtil.getCurrentTimestamp(), null, "AUTO GENERATED", 0, null, null, null, null, product);
				productCreditLimits.add(productCreditLimit);
				// product status
				PmtbProductStatus productStatus = new PmtbProductStatus("N", "A", DateUtil.getCurrentTimestamp(), "AUTO GENERATED", 0, null, null, null, null, statusReason, product);
				productStatuses.add(productStatus);
			}
		}
		this.getHibernateTemplate().saveOrUpdateAll(accounts2);
		this.getHibernateTemplate().saveOrUpdateAll(creditLimits);
		this.getHibernateTemplate().saveOrUpdateAll(contacts);
		this.getHibernateTemplate().saveOrUpdateAll(mains);
		this.getHibernateTemplate().saveOrUpdateAll(salespersons);
		this.getHibernateTemplate().saveOrUpdateAll(statuses);
		this.getHibernateTemplate().saveOrUpdateAll(adminFees);
		this.getHibernateTemplate().saveOrUpdateAll(cycles);
		this.getHibernateTemplate().saveOrUpdateAll(creditTerms);
		this.getHibernateTemplate().saveOrUpdateAll(lates);
		logger.info("inserted " + (accounts2.size()*2+creditLimits.size()+contacts.size()+mains.size()+salespersons.size()+statuses.size()+adminFees.size()
				+cycles.size()+creditTerms.size()+lates.size()) + " rows");
		logger.info("time finished = " + Calendar.getInstance().getTime());
		logger.info("time taken = " + (Calendar.getInstance().getTimeInMillis() - now.getTimeInMillis()) + " ms");
		this.getHibernateTemplate().saveOrUpdateAll(products);
		this.getHibernateTemplate().saveOrUpdateAll(productCreditLimits);
		this.getHibernateTemplate().saveOrUpdateAll(productStatuses);
		logger.info("inserted " + (products.size()+productCreditLimits.size()+productStatuses.size()) + " rows");
		logger.info("time finished = " + Calendar.getInstance().getTime());
		logger.info("time taken = " + (Calendar.getInstance().getTimeInMillis() - now.getTimeInMillis()) + " ms");
	}*/
	
	/**
	 * method to search for account with customer number and account name
	 * @param - custNo = the customer number
	 * @param - acctName = name of the account
	 * @return - list of the matching accounts
	 */
	@SuppressWarnings("unchecked")
	public List<AmtbAccount> getAccounts(String custNo, String acctName, String acctStatus, String contactPerson){
		//insertTest();
		logger.info("getAccounts(String custNo, String acctName, String acctStatus, String contactPerson)");
		Session session = this.getHibernateTemplate().getSessionFactory().getCurrentSession();
		Transaction txn = session.beginTransaction();
		Query query = session.createSQLQuery("select distinct acct.ACCOUNT_NO from AMTB_ACCOUNT acct " +
				"inner join (select ACCOUNT_NO, max(EFFECTIVE_DT) as EFFECTIVE_DT from AMTB_ACCT_STATUS where EFFECTIVE_DT < SYSTIMESTAMP group by ACCOUNT_NO) last_status on acct.ACCOUNT_NO = last_status.ACCOUNT_NO and acct.CUST_NO like :CUST_NO and acct.ACCOUNT_NAME like :ACCOUNT_NAME " +
				"inner join AMTB_ACCT_STATUS status on status.ACCOUNT_NO = last_status.ACCOUNT_NO and status.EFFECTIVE_DT = last_status.EFFECTIVE_DT and status.ACCT_STATUS like :ACCT_STATUS " +
				"left join AMTB_CONTACT_PERSON contact on acct.ACCOUNT_NO = contact.ACCOUNT_NO " +
				(contactPerson!=null && contactPerson.length()!=0 ? "where (contact.MAIN_CONTACT_NAME like :MAIN_CONTACT_NAME or contact.SUB_CONTACT_NAME like :MAIN_CONTACT_NAME)" : "")
				
		);
		query.setString("CUST_NO", custNo!=null&&custNo.trim().length()!=0?custNo:"%");
		logger.info("test1 = " + (custNo!=null&&custNo.trim().length()!=0?custNo:"%"));
		query.setString("ACCOUNT_NAME", "%"+(acctName==null?"":acctName)+"%");
		logger.info("test2 = " + "%"+(acctName==null?"":acctName)+"%");
		query.setString("ACCT_STATUS", (acctStatus!=null&&acctStatus.length()!=0?acctStatus:"%"));
		logger.info("test3 = " + (acctStatus!=null&&acctStatus.length()!=0?acctStatus:"%"));
		if(contactPerson!=null && contactPerson.length()!=0){
			query.setString("MAIN_CONTACT_NAME", "%"+(contactPerson==null?"":contactPerson)+"%");
		}
		logger.info("test4 = " + "%"+(contactPerson==null?"":contactPerson)+"%");
		logger.info("sql = " + query.getQueryString());
		List<BigDecimal> acctNos = query.list();
		logger.info("Size = " + acctNos.size());
		txn.commit();
		List<AmtbAccount> returnList = new ArrayList<AmtbAccount>();
		if(acctNos.isEmpty()){
			return returnList;
		}
		List<Integer> accountNos = new ArrayList<Integer>();
		for(BigDecimal acctNo : acctNos){
			if(!accountNos.contains(acctNo.intValue())){
				accountNos.add(acctNo.intValue());
			}
			if(accountNos.size()>=ConfigurableConstants.getMaxQueryResult()+1){
				break;
			}
		}
		for(int i=0;i<accountNos.size();i+=1000){
			List<Integer> tempList = accountNos.subList(i, i+1000>accountNos.size()?accountNos.size():i+1000);
			logger.info(tempList);
			DetachedCriteria accountCriteria = DetachedCriteria.forClass(AmtbAccount.class);
			accountCriteria.addOrder(Order.asc("custNo"));
			accountCriteria.add(Restrictions.in("accountNo", tempList));
			accountCriteria
				.createCriteria("amtbAcctMainContacts", DetachedCriteria.LEFT_JOIN)
				.createCriteria("amtbContactPerson", DetachedCriteria.LEFT_JOIN);
			accountCriteria.createCriteria("amtbAcctType", DetachedCriteria.LEFT_JOIN);
			accountCriteria.createCriteria("amtbAcctStatuses", DetachedCriteria.LEFT_JOIN);
			DetachedCriteria arContCodeMasterCriteria = accountCriteria.createCriteria("fmtbArContCodeMaster", DetachedCriteria.LEFT_JOIN);
			arContCodeMasterCriteria.createCriteria("fmtbEntityMaster", DetachedCriteria.LEFT_JOIN);
			List<AmtbAccount> accounts = this.findAllByCriteria(accountCriteria);
			returnList.addAll(accounts);
		}
//		query.setInteger("account_no", acct.getAccountNo());
//		logger.info("deleted subscriptions = " + query.executeUpdate());
//		query = session.createSQLQuery("delete from amtb_acct_main_contact where account_no = :account_no");
//		query.setInteger("account_no", acct.getAccountNo());
//		logger.info("deleted main contacts = " + query.executeUpdate());
//		query = session.createSQLQuery("delete from amtb_personal_detail where account_no = :account_no");
//		query.setInteger("account_no", acct.getAccountNo());
//		logger.info("deleted personal detail = " + query.executeUpdate());
//		query = session.createSQLQuery("delete from amtb_account where account_no = :account_no");
//		query.setInteger("account_no", acct.getAccountNo());
//		logger.info("delete accounts = " + query.executeUpdate());
		return returnList;
//		if(acctStatus!=null && acctStatus.length()!=0){
//			List<AmtbAccount> returnList = new ArrayList<AmtbAccount>();
//			for(AmtbAccount account : accounts){
//				TreeSet<AmtbAcctStatus> arrangedStatus = new TreeSet<AmtbAcctStatus>(new Comparator<AmtbAcctStatus>(){
//					public int compare(AmtbAcctStatus as1, AmtbAcctStatus as2) {
//						return as1.getEffectiveDt().compareTo(as2.getEffectiveDt());
//					}
//				});
//				for(AmtbAcctStatus status : account.getAmtbAcctStatuses()){
//					if(status.getEffectiveDt().before(DateUtil.getCurrentTimestamp())){
//						arrangedStatus.add(status);
//					}
//				}
//				if(acctStatus.equals(arrangedStatus.last().getAcctStatus())){
//					returnList.add(account);
//				}
//			}
//			return returnList;
//		}else{
//			return accounts;
//		}
	}
	@SuppressWarnings("unchecked")
	public List<AmtbAccount> getAccounts(String custNo, String parentCode, int level){
		logger.info("getAccounts(String custNo, String parentCode, int level)");
		logger.info("getAccounts(custNo = "+custNo+", parentCode = "+parentCode+", level = "+level+")");
		DetachedCriteria acctCriteria = DetachedCriteria.forClass(AmtbAccount.class);
		acctCriteria.createCriteria("amtbAcctStatuses", DetachedCriteria.LEFT_JOIN);
		for(int i=0;i<level;i++){
			acctCriteria = acctCriteria.createCriteria("amtbAccount", DetachedCriteria.LEFT_JOIN);
			if(i==0 && parentCode!=null && parentCode.trim().length()!=0){
				acctCriteria.add(Restrictions.eq("code", parentCode));
			}
		}
		if(custNo!=null && custNo.trim().length()!=0){
			acctCriteria.add(Restrictions.eq("custNo", custNo));
		}
		return this.findAllByCriteria(acctCriteria);
	}
	@SuppressWarnings("unchecked")
	public List<AmtbAccount> getAccounts(String custNo, String acctName, String acctCode, String contactPerson, String contactPersonTel, int level, String parentName){
		logger.info("getAccounts(String custNo, String acctName, String acctCode, String contactPerson, String contactPersonTel, int level)");
		logger.info("custNo = "+custNo+", acctName = "+acctName+", acctCode = "+acctCode+", contactPerson = "+contactPerson+", contactPersonTel = "+contactPersonTel);
		if(custNo!=null && custNo.trim().length()!=0){
			DetachedCriteria accountCriteria = DetachedCriteria.forClass(AmtbAccount.class);
			// filtering account name
			if(acctName!=null && acctName.trim().length()!=0){
				accountCriteria.add(Restrictions.ilike("accountName", acctName, MatchMode.ANYWHERE));
			}
			// filtering account code
			if(acctCode!=null && acctCode.trim().length()!=0){
				accountCriteria.add(Restrictions.ilike("code", acctCode, MatchMode.ANYWHERE));
			}
			// joining up with main contact
			{
				accountCriteria.createCriteria("amtbAcctMainContacts", "main", DetachedCriteria.LEFT_JOIN);
				accountCriteria.createCriteria("main.amtbContactPerson", "mainContact",DetachedCriteria.FULL_JOIN);
				// now filtering
				DetachedCriteria contactCriteria = DetachedCriteria.forClass(AmtbContactPerson.class);
				contactCriteria.createCriteria("amtbAccount", DetachedCriteria.LEFT_JOIN).add(Restrictions.eq("custNo", custNo));
				contactCriteria.createCriteria("amtbAcctMainContacts", "mainContacts", DetachedCriteria.LEFT_JOIN);
				contactCriteria.setProjection(Projections.distinct(Projections.property("mainContacts.comp_id.amtbAccount")));
				if(contactPerson!=null && contactPerson.trim().length()!=0){
					contactCriteria.add(Restrictions.ilike("mainContactName", contactPerson, MatchMode.ANYWHERE));
				}
				if(contactPersonTel!=null && contactPersonTel.trim().length()!=0){
					contactCriteria.add(Restrictions.ilike("mainContactTel", contactPersonTel, MatchMode.ANYWHERE));
				}
				accountCriteria.add(Subqueries.propertyIn("accountNo", contactCriteria));
			}
			// joining up with status
			accountCriteria.createCriteria("amtbAcctStatuses", DetachedCriteria.LEFT_JOIN);
			DetachedCriteria tempCriteria = accountCriteria;
			for(int i=0;i<level;i++){
				tempCriteria = tempCriteria.createCriteria("amtbAccount", DetachedCriteria.FULL_JOIN);
				if(i==0 && parentName!=null && parentName.length()!=0){
					tempCriteria.add(Restrictions.ilike("accountName", parentName, MatchMode.ANYWHERE));
				}
			}
			tempCriteria.add(Restrictions.eq("custNo", custNo));
			accountCriteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
			accountCriteria.addOrder(Order.asc("accountName"));
			return this.findAllByCriteria(accountCriteria);
		}else{
			throw new IllegalArgumentException("parent customer number empty!");
		}
	}
	
	@SuppressWarnings("unchecked")
	public AmtbAccount retrieveAccount(String custNo){
		logger.info("retrieveAccount(String custNo)");
		DetachedCriteria accountCriteria = DetachedCriteria.forClass(AmtbAccount.class);
		accountCriteria = accountCriteria.add(Restrictions.eq("custNo", custNo));
		accountCriteria.createCriteria("amtbAcctType", DetachedCriteria.LEFT_JOIN);
		// retrieve first to get details
		List<AmtbAccount> accounts = this.findAllByCriteria(accountCriteria);
		if(accounts.isEmpty()){
			return null;
		}else{
			AmtbAccount account = accounts.get(0);
			// checking whether is it personal or corporate
			if(account.getAmtbAcctType().getAcctTemplate().equals(NonConfigurableConstants.ACCOUNT_TEMPLATES_CORPORATE)){
				// if corporate
				accountCriteria.createCriteria("amtbCorporateDetails", DetachedCriteria.LEFT_JOIN);
			}else{
				// if personal
				accountCriteria.createCriteria("amtbPersonalDetails", DetachedCriteria.LEFT_JOIN);
			}
			// joining up with ar control code and entity
			{
				DetachedCriteria arCriteria = accountCriteria.createCriteria("fmtbArContCodeMaster", DetachedCriteria.LEFT_JOIN);
				arCriteria.createCriteria("fmtbEntityMaster", DetachedCriteria.LEFT_JOIN);
			}
			// joining up with sales person
			{
				DetachedCriteria acctSales = accountCriteria.createCriteria("amtbAcctSalespersons", DetachedCriteria.LEFT_JOIN);
				acctSales.createCriteria("mstbSalesperson", DetachedCriteria.LEFT_JOIN);
			}
			// joining up with product subscriptions
			{
				DetachedCriteria subscribeTo = accountCriteria.createCriteria("amtbSubscTos", DetachedCriteria.LEFT_JOIN);
				subscribeTo.createCriteria("comp_id.pmtbProductType", DetachedCriteria.LEFT_JOIN);
				subscribeTo.createCriteria("mstbProdDiscMaster", DetachedCriteria.LEFT_JOIN);
				subscribeTo.createCriteria("lrtbRewardMaster", DetachedCriteria.LEFT_JOIN);
				subscribeTo.createCriteria("mstbSubscFeeMaster", DetachedCriteria.LEFT_JOIN);
				subscribeTo.createCriteria("mstbIssuanceFeeMaster", DetachedCriteria.LEFT_JOIN);
			}
			
			// joining up with main contact
			{
				accountCriteria.createCriteria("amtbAcctMainContacts", DetachedCriteria.LEFT_JOIN)
				.createCriteria("amtbContactPerson", DetachedCriteria.LEFT_JOIN);
			}
			// joining up with credit limit
			{
				DetachedCriteria creditLimit = accountCriteria.createCriteria("amtbAcctCredLimits", DetachedCriteria.LEFT_JOIN);
				creditLimit.createCriteria("satbUser", DetachedCriteria.LEFT_JOIN);
			}
			// joining up with business unit master
			{
				accountCriteria.createCriteria("mstbMasterTableByBusinessUnit", DetachedCriteria.LEFT_JOIN);
			}
			// now joining up with the account status
			accountCriteria.createCriteria("amtbAcctStatuses", DetachedCriteria.LEFT_JOIN);
			// retrieve again with the details
			accounts = this.findAllByCriteria(accountCriteria);
			if(accounts.isEmpty()){
				return null;
			}else{
				return accounts.get(0);
			}
		}
	}
	
	public List<AmtbAccount> searchAccts(String custNo, String acctName, String acctCode, String contactPerson, String contactPersonTel, int level, String parentName, String acctStatus){
		logger.info("searchAccts(String custNo, String acctName, String acctCode, String contactPerson, String contactPersonTel, int level, String parentName, String acctStatus)");
		logger.info("custNo = "+custNo+", acctName = "+acctName+", acctCode = "+acctCode+", contactPerson = "+contactPerson+", contactPersonTel = "+contactPersonTel);
		if(custNo!=null && custNo.trim().length()!=0){
			DetachedCriteria accountCriteria = DetachedCriteria.forClass(AmtbAccount.class, "acct");
			// filtering account name
			if(acctName!=null && acctName.trim().length()!=0){
				accountCriteria.add(Restrictions.ilike("accountName", acctName, MatchMode.ANYWHERE));
			}
			// filtering account code
			if(acctCode!=null && acctCode.trim().length()!=0){
				accountCriteria.add(Restrictions.ilike("code", acctCode, MatchMode.ANYWHERE));
			}
			// joining up with main contact
			{
				accountCriteria.createCriteria("amtbAcctMainContacts", "main", DetachedCriteria.LEFT_JOIN);
				accountCriteria.createCriteria("main.amtbContactPerson", "mainContact",DetachedCriteria.FULL_JOIN);
				// now filtering
				DetachedCriteria contactCriteria = DetachedCriteria.forClass(AmtbContactPerson.class);
				contactCriteria.createCriteria("amtbAccount", DetachedCriteria.LEFT_JOIN).add(Restrictions.eq("custNo", custNo));
				contactCriteria.createCriteria("amtbAcctMainContacts", "mainContacts", DetachedCriteria.LEFT_JOIN);
				contactCriteria.setProjection(Projections.distinct(Projections.property("mainContacts.comp_id.amtbAccount")));
				if(contactPerson!=null && contactPerson.trim().length()!=0){
					contactCriteria.add(Restrictions.ilike("mainContactName", contactPerson, MatchMode.ANYWHERE));
				}
				if(contactPersonTel!=null && contactPersonTel.trim().length()!=0){
					contactCriteria.add(Restrictions.ilike("mainContactTel", contactPersonTel, MatchMode.ANYWHERE));
				}
				accountCriteria.add(Subqueries.propertyIn("accountNo", contactCriteria));
			}
			// joining up with last status
			DetachedCriteria lastAcctStatusCriteria = accountCriteria.createCriteria("amtbAcctStatuses", "last_status", DetachedCriteria.LEFT_JOIN);
			DetachedCriteria temp = DetachedCriteria.forClass(AmtbAcctStatus.class, "lastStatus");
			temp.add(Restrictions.eqProperty("lastStatus.amtbAccount", "acct.accountNo"));
			temp.add(Restrictions.le("lastStatus.effectiveDt", DateUtil.getCurrentTimestamp()));
			ProjectionList list = Projections.projectionList();
			list.add(Projections.max("lastStatus.effectiveDt"));
			temp.setProjection(list);
			
			//Add acct_status_no is not null restriction to cater for those new created division and department in pending activation account.
			lastAcctStatusCriteria.add(Restrictions.or(Restrictions.isNull("last_status.acctStatusNo"), Subqueries.propertyEq("effectiveDt", temp)));
			
			if(acctStatus!=null && !"".equals(acctStatus)) {
				lastAcctStatusCriteria.add(Restrictions.eq("acctStatus", acctStatus));
			}
			DetachedCriteria tempCriteria = accountCriteria;
			for(int i=0;i<level;i++){
				tempCriteria = tempCriteria.createCriteria("amtbAccount", DetachedCriteria.FULL_JOIN);
				if(i==0 && parentName!=null && parentName.length()!=0){
					tempCriteria.add(Restrictions.ilike("accountName", parentName, MatchMode.ANYWHERE));
				}
			}
			tempCriteria.add(Restrictions.eq("custNo", custNo));
			accountCriteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
			accountCriteria.addOrder(Order.asc("accountName"));
			return this.findAllByCriteria(accountCriteria);
		}else{
			throw new IllegalArgumentException("parent customer number empty!");
		}
	}
	
	
	
	public boolean checkRCBNo(String rcbNo){
		logger.info("checkRCBNo(String rcbNo)");
		DetachedCriteria corpCriteria = DetachedCriteria.forClass(AmtbCorporateDetail.class);
		corpCriteria.add(Restrictions.eq("rcbNo", rcbNo));
		return !this.findAllByCriteria(corpCriteria).isEmpty();
	}
	public boolean checkCorporateName(String name){
		logger.info("checkCorporateName(String name)");
		DetachedCriteria acctCriteria = DetachedCriteria.forClass(AmtbAccount.class);
		acctCriteria.add(Restrictions.eq("accountName", name));
		acctCriteria.createCriteria("amtbCorporateDetails", DetachedCriteria.INNER_JOIN);
		return !this.findAllByCriteria(acctCriteria).isEmpty();
	}
	@SuppressWarnings("unchecked")
	public List<AmtbContactPerson> getContacts(String custNo, String mainContactName, String mainContactEmail, String mainContactTel, String mainContactMobile){
		logger.info("getContacts(String mainContactName, String mainContactEmail, String mainContactTel, String mainContactMobile)");
		DetachedCriteria contactCriteria = DetachedCriteria.forClass(AmtbContactPerson.class);
		contactCriteria.createCriteria("mstbMasterTableBySubContactSal", DetachedCriteria.LEFT_JOIN);
		contactCriteria.createCriteria("mstbMasterTableByMainContactSal", DetachedCriteria.LEFT_JOIN);
		contactCriteria.createCriteria("mstbMasterTableByMainContactRace", DetachedCriteria.LEFT_JOIN);
		contactCriteria.createCriteria("mstbMasterTableBySubContactRace", DetachedCriteria.LEFT_JOIN);
		
		contactCriteria.createCriteria("mstbMasterTableByAddressCountry", DetachedCriteria.LEFT_JOIN);
		// joining up with account table.
		{
			DetachedCriteria acctCriteria = contactCriteria.createCriteria("amtbAccount", DetachedCriteria.LEFT_JOIN);
			acctCriteria.add(Restrictions.eq("custNo", custNo));
		}
		// joining up with the main contact
		{
//			DetachedCriteria mainCriteria = contactCriteria.createCriteria("amtbAcctMainContacts", DetachedCriteria.LEFT_JOIN);
//			mainCriteria.createCriteria("comp_id.amtbAccount", DetachedCriteria.LEFT_JOIN);
		}
		// now adding restrictions
		if(mainContactName!=null && mainContactName.length()!=0){
			contactCriteria.add(Restrictions.ilike("mainContactName", mainContactName, MatchMode.ANYWHERE));
		}
		if(mainContactEmail!=null && mainContactEmail.length()!=0){
			contactCriteria.add(Restrictions.ilike("mainContactEmail", mainContactEmail, MatchMode.ANYWHERE));
		}
		if(mainContactTel!=null && mainContactTel.length()!=0){
			contactCriteria.add(Restrictions.ilike("mainContactTel", mainContactTel, MatchMode.ANYWHERE));
		}
		if(mainContactMobile!=null && mainContactMobile.length()!=0){
			contactCriteria.add(Restrictions.ilike("mainContactMobile", mainContactMobile, MatchMode.ANYWHERE));
		}
		contactCriteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		return this.findAllByCriteria(contactCriteria);
	}
	public AmtbCorporateDetail getCorporateDetail(AmtbAccount acct){
		DetachedCriteria corpCriteria = DetachedCriteria.forClass(AmtbCorporateDetail.class);
		corpCriteria.add(Restrictions.eq("amtbAccount", acct));
		return (AmtbCorporateDetail)this.findAllByCriteria(corpCriteria).get(0);
	}
	public AmtbPersonalDetail getPersonalDetail(AmtbAccount acct){
		DetachedCriteria persCriteria = DetachedCriteria.forClass(AmtbPersonalDetail.class);
		persCriteria.add(Restrictions.eq("amtbAccount", acct));
		return (AmtbPersonalDetail)this.findAllByCriteria(persCriteria).get(0);
	}
	public boolean createContact(AmtbContactPerson newContact, List<AmtbAcctMainContact> mainContacts, String userId){
		this.save(newContact, userId);
		List<AmtbAcctMainContact> updated = new ArrayList<AmtbAcctMainContact>();
		for(AmtbAcctMainContact mainContact : mainContacts){
			AmtbAcctMainContact prevMainContact = (AmtbAcctMainContact)this.getHibernateTemplate().get(AmtbAcctMainContact.class, mainContact.getComp_id());
			if(prevMainContact!=null){
				prevMainContact.setAmtbContactPerson(newContact);
				this.update(prevMainContact, userId);
				updated.add(mainContact);
			}
		}
		mainContacts.removeAll(updated);
		this.getHibernateTemplate().saveOrUpdateAll(mainContacts);
		return true;
	}
	@SuppressWarnings("unchecked")
	public AmtbContactPerson getContact(Integer contactPersonNo){
		DetachedCriteria contactCriteria = DetachedCriteria.forClass(AmtbContactPerson.class);
		contactCriteria.add(Restrictions.eq("contactPersonNo", contactPersonNo));
		contactCriteria.createCriteria("mstbMasterTableBySubContactSal", DetachedCriteria.LEFT_JOIN);
		contactCriteria.createCriteria("mstbMasterTableByMainContactSal", DetachedCriteria.LEFT_JOIN);
		contactCriteria.createCriteria("mstbMasterTableBySubContactRace", DetachedCriteria.LEFT_JOIN);
		contactCriteria.createCriteria("mstbMasterTableByMainContactRace", DetachedCriteria.LEFT_JOIN);
		contactCriteria.createCriteria("mstbMasterTableByAddressCountry", DetachedCriteria.LEFT_JOIN);
		contactCriteria.createCriteria("amtbAccount", DetachedCriteria.LEFT_JOIN);
		contactCriteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		List<AmtbContactPerson> contactPersons = this.findAllByCriteria(contactCriteria);
		if(contactPersons.isEmpty()){
			return null;
		}else{
			return contactPersons.get(0);
		}
	}
	public boolean deleteContact(Integer contactPersonNo){
		//		DetachedCriteria mainCriteria = DetachedCriteria.forClass(AmtbAcctMainContact.class);
		//		mainCriteria.createCriteria("amtbContactPerson", DetachedCriteria.FULL_JOIN)
		//			.add(Restrictions.eq("contactPersonNo", contactPersonNo));
		//		this.getHibernateTemplate().deleteAll(this.findAllByCriteria(mainCriteria));
		this.getHibernateTemplate().delete(this.getHibernateTemplate().get(AmtbContactPerson.class, contactPersonNo));
		return true;
	}
	public boolean isMainContact(Integer contactPersonNo){
		DetachedCriteria mainCriteria = DetachedCriteria.forClass(AmtbAcctMainContact.class);
		mainCriteria.createCriteria("amtbContactPerson", DetachedCriteria.LEFT_JOIN)
		.add(Restrictions.eq("contactPersonNo", contactPersonNo));
		return !this.findAllByCriteria(mainCriteria).isEmpty();
	}
	public boolean updateContact(AmtbContactPerson updatedContact, List<AmtbAcctMainContact> mainContacts, String userId){
		this.update(updatedContact, userId);
		List<AmtbAcctMainContact> updated = new ArrayList<AmtbAcctMainContact>();
		for(AmtbAcctMainContact mainContact : mainContacts){
			AmtbAcctMainContact prevMainContact = (AmtbAcctMainContact)this.getHibernateTemplate().get(AmtbAcctMainContact.class, mainContact.getComp_id());
			if(prevMainContact!=null){
				prevMainContact.setAmtbContactPerson(updatedContact);
				this.update(prevMainContact);
				updated.add(mainContact);
			}
		}
		mainContacts.removeAll(updated);
		this.getHibernateTemplate().saveOrUpdateAll(mainContacts);
		return true;
	}
	@SuppressWarnings("unchecked")
	public boolean checkCreditLimit(String custNo, String parentCode, BigDecimal creditLimit){
		DetachedCriteria acctCriteria = DetachedCriteria.forClass(AmtbAccount.class);
		if(parentCode!=null && parentCode.trim().length()!=0){
			acctCriteria.add(Restrictions.eq("code", parentCode));
			acctCriteria.createCriteria("amtbAccount", DetachedCriteria.FULL_JOIN)
			.add(Restrictions.eq("custNo", custNo));
		}else{
			acctCriteria.add(Restrictions.eq("custNo", custNo));
		}
		List<AmtbAccount> accounts = this.findAllByCriteria(acctCriteria);
		if(!accounts.isEmpty()){
			if(accounts.get(0).getCreditLimit().compareTo(creditLimit)>=0){
				return false;
			}else{
				return true;
			}
		}else{
			throw new IllegalArgumentException("Can't find account with custNo = " + custNo + " and parentCode = " + parentCode);
		}
	}
	@SuppressWarnings("unchecked")
	public boolean checkChildrenCreditLimit(String custNo, String code, BigDecimal creditLimit){
		DetachedCriteria acctCriteria = DetachedCriteria.forClass(AmtbAccount.class);
		DetachedCriteria parentAcctCriteria = acctCriteria.createCriteria("amtbAccount", DetachedCriteria.INNER_JOIN);
		if(code!=null && code.trim().length()!=0){
			parentAcctCriteria.add(Restrictions.eq("code", code));
			parentAcctCriteria.createCriteria("amtbAccount", DetachedCriteria.INNER_JOIN)
			.add(Restrictions.eq("custNo", custNo));
		}else{
			parentAcctCriteria.add(Restrictions.eq("custNo", custNo));
		}
		
		DetachedCriteria acctStatusCriteria = acctCriteria.createCriteria("amtbAcctStatuses", "acct_status", DetachedCriteria.LEFT_JOIN);
		DetachedCriteria subQuery = DetachedCriteria.forClass(AmtbAcctStatus.class, "subQuery");
		subQuery.add(Restrictions.eqProperty("subQuery.amtbAccount", "acct_status.amtbAccount"));
		subQuery.add(Restrictions.lt("subQuery.effectiveDt", new Date()));
		subQuery.setProjection(Projections.max("subQuery.acctStatusNo"));
		
		acctStatusCriteria.add(Subqueries.propertyEq("acct_status.acctStatusNo" ,subQuery));
		acctStatusCriteria.add(Restrictions.not(Restrictions.eq("acct_status.acctStatus", NonConfigurableConstants.ACCOUNT_STATUS_CLOSED)));
		
		List<AmtbAccount> accounts = this.findAllByCriteria(acctCriteria);
		for(AmtbAccount account : accounts){
			if(account.getCreditLimit().compareTo(creditLimit)>0){
				return true;
			}
		}
		return false;
	}
	public boolean createAccount(AmtbAccount newAcct, List<AmtbAcctMainContact> mainContacts, String userId){
		save(newAcct, userId);
		this.getHibernateTemplate().saveOrUpdateAll(mainContacts);
		return true;
	}
	@SuppressWarnings("unchecked")
	public AmtbAccount getAccount(String custNo, int level, String parentCode, String code) throws IllegalArgumentException{
		logger.info("getAccount(String custNo, int level, String parentCode, String code)");
		logger.info("custNo = "+custNo+",level = "+level+", parentCode = "+parentCode+" code = "+code);
		if(custNo!=null && custNo.trim().length()!=0){
			DetachedCriteria acctCriteria = DetachedCriteria.forClass(AmtbAccount.class);
			if(code!=null && code.trim().length()!=0){
				acctCriteria.add(Restrictions.eq("code", code));
			}
			DetachedCriteria tempCriteria = acctCriteria;
			for(int i=0;i<level;i++){
				tempCriteria = tempCriteria.createCriteria("amtbAccount", DetachedCriteria.FULL_JOIN);
				if(i==0){// first loop. filter parent code
					if(parentCode!=null && parentCode.trim().length()!=0){
						tempCriteria.add(Restrictions.eq("code", parentCode));
					}
				}
			}
			tempCriteria.add(Restrictions.eq("custNo", custNo));
			{
				DetachedCriteria mainContactCriteria = acctCriteria.createCriteria("amtbAcctMainContacts", DetachedCriteria.LEFT_JOIN);
				mainContactCriteria.createCriteria("amtbContactPerson", DetachedCriteria.LEFT_JOIN);
			}
			{
				DetachedCriteria subscribeCriteria = acctCriteria.createCriteria("amtbSubscTos", DetachedCriteria.LEFT_JOIN);
				subscribeCriteria.createCriteria("comp_id.pmtbProductType", DetachedCriteria.LEFT_JOIN);
				subscribeCriteria.createCriteria("mstbProdDiscMaster", DetachedCriteria.LEFT_JOIN);
				subscribeCriteria.createCriteria("lrtbRewardMaster", DetachedCriteria.LEFT_JOIN);
				subscribeCriteria.createCriteria("mstbSubscFeeMaster", DetachedCriteria.LEFT_JOIN);
				subscribeCriteria.createCriteria("mstbIssuanceFeeMaster", DetachedCriteria.LEFT_JOIN);
			}
			{
				DetachedCriteria arCtrlCriteria = acctCriteria.createCriteria("fmtbArContCodeMaster", DetachedCriteria.FULL_JOIN);
				arCtrlCriteria.createCriteria("fmtbEntityMaster", DetachedCriteria.FULL_JOIN);
			}
			//GIRO
			{
				//Default Payment Mode
				acctCriteria.createCriteria("mstbMasterTableByDefaultPaymentMode", Criteria.LEFT_JOIN);
				//Bank Master
				acctCriteria.createCriteria("mstbBankMaster", Criteria.LEFT_JOIN);
			}
			// Govt eInvoice, Business Unit
			{
				acctCriteria.createCriteria("mstbMasterTableByBusinessUnit", Criteria.LEFT_JOIN);
			}
			List<AmtbAccount> accts = this.findAllByCriteria(acctCriteria);
			return accts.isEmpty() ? null : accts.get(0);
		}else{
			throw new IllegalArgumentException("customer number is empty!");
		}
	}
	@SuppressWarnings("unchecked")
	public boolean updateAccount(AmtbAccount updatedAcct, List<AmtbAcctMainContact> mainContacts, List<String> subscribedTos, String userName){
		// account
		this.update(updatedAcct, userName);
		// getting the top account
		AmtbAccount topAccount = updatedAcct.getAmtbAccount() == null ? updatedAcct : updatedAcct.getAmtbAccount().getAmtbAccount() == null ? updatedAcct.getAmtbAccount() : updatedAcct.getAmtbAccount().getAmtbAccount();
		// only retrieving all product types
		DetachedCriteria prodSubscriptions = DetachedCriteria.forClass(AmtbSubscTo.class);
		prodSubscriptions.add(Restrictions.eq("comp_id.amtbAccount", topAccount));
		prodSubscriptions.createCriteria("comp_id.pmtbProductType", DetachedCriteria.FULL_JOIN);
		List<AmtbSubscTo> topSubscribedTos = this.findAllByCriteria(prodSubscriptions);
		// adding
		for(String subscribedTo : subscribedTos){
			boolean found = false;
			for (AmtbSubscTo subscribe : updatedAcct.getAmtbSubscTos()) {
				if(subscribe.getComp_id().getPmtbProductType().getProductTypeId().equals(subscribedTo)){
					found = true;
					break;
				}
			}
			if(!found){
				for(AmtbSubscTo topSubscribedTo : topSubscribedTos){
					if(topSubscribedTo.getComp_id().getPmtbProductType().getProductTypeId().equals(subscribedTo)){
						AmtbSubscTo newSubscription = new AmtbSubscTo();
						newSubscription.setEffectiveDt(DateUtil.getCurrentTimestamp());
						newSubscription.setComp_id(new AmtbSubscToPK(topSubscribedTo.getComp_id().getPmtbProductType(), updatedAcct));
						newSubscription.setLrtbRewardMaster(topSubscribedTo.getLrtbRewardMaster());
						newSubscription.setMstbProdDiscMaster(topSubscribedTo.getMstbProdDiscMaster());
						newSubscription.setMstbSubscFeeMaster(topSubscribedTo.getMstbSubscFeeMaster());
						newSubscription.setMstbIssuanceFeeMaster(topSubscribedTo.getMstbIssuanceFeeMaster());
						this.save(newSubscription, userName);
					}
				}
			}
		}
		// removing
		List<AmtbSubscTo> subscribes = new ArrayList<AmtbSubscTo>();
		for (AmtbSubscTo subscribedTo : updatedAcct.getAmtbSubscTos()) {
			if(!subscribedTos.contains(subscribedTo.getComp_id().getPmtbProductType().getProductTypeId())){
				subscribes.add(subscribedTo);
			}
		}
		this.getHibernateTemplate().deleteAll(subscribes);
		//		for(AmtbSubscribeTo topSubscribedTo : topSubscribedTos){
		//			if(subscribedTos.contains(topSubscribedTo.getComp_id().getPmtbProductType().getProductTypeId())){
		//				topSubscribedTo.getComp_id().setAmtbAccount(updatedAcct);
		//				topSubscribedTo.setEffectiveDt(DateUtil.getCurrentTimestamp());
		//				this.save(topSubscribedTo, userName);
		//			}
		//		}
		return true;
	}
	@SuppressWarnings("unchecked")
	public AmtbAccount getAccount(Integer accountNo){
		DetachedCriteria acctCriteria = DetachedCriteria.forClass(AmtbAccount.class);
		acctCriteria.add(Restrictions.eq("accountNo", accountNo));
		acctCriteria.createCriteria("amtbAcctType", DetachedCriteria.LEFT_JOIN);
		acctCriteria.createCriteria("mstbMasterTableByInformationSource", DetachedCriteria.LEFT_JOIN);
		acctCriteria.createCriteria("fmtbArContCodeMaster", DetachedCriteria.LEFT_JOIN);
		acctCriteria.createCriteria("amtbAcctMainContacts", DetachedCriteria.LEFT_JOIN);
		acctCriteria.createCriteria("amtbContactPersons", DetachedCriteria.LEFT_JOIN);
		acctCriteria.createCriteria("amtbSubscTos", DetachedCriteria.LEFT_JOIN)
		.createCriteria("comp_id.pmtbProductType", DetachedCriteria.LEFT_JOIN);
		acctCriteria.createCriteria("amtbAccount", DetachedCriteria.FULL_JOIN);
		acctCriteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		List<AmtbAccount> list = this.findAllByCriteria(acctCriteria);
		if (list.isEmpty())
			return null;
		else
			return (AmtbAccount)list.get(0);
	}
	@SuppressWarnings("unchecked")
	public List<AmtbAcctMainContact> getMainContacts(Integer acctNo){
		DetachedCriteria mainContactCriteria = DetachedCriteria.forClass(AmtbAcctMainContact.class);
		mainContactCriteria.createCriteria("amtbContactPerson", DetachedCriteria.LEFT_JOIN);
		mainContactCriteria.add(Restrictions.eq("comp_id.amtbAccount", getAccount(acctNo)));
		mainContactCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		return this.findAllByCriteria(mainContactCriteria);
	}
	
	@SuppressWarnings("unchecked")
	public AmtbAcctMainContact getMainBillingContact(AmtbAccount amtbAccount){
		DetachedCriteria mainContactCriteria = DetachedCriteria.forClass(AmtbAcctMainContact.class);
		mainContactCriteria.createCriteria("amtbContactPerson", DetachedCriteria.LEFT_JOIN);
		mainContactCriteria.add(Restrictions.eq("comp_id.amtbAccount", amtbAccount));
		mainContactCriteria.add(Restrictions.eq("comp_id.mainContactType", NonConfigurableConstants.MAIN_CONTACT_TYPE_BILLING));
		mainContactCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		List<AmtbAcctMainContact> acctContacts = this.findAllByCriteria(mainContactCriteria);
		return acctContacts.isEmpty() ? null : acctContacts.get(0);

	}

	@SuppressWarnings("unchecked")
	public AmtbAccount getBillingDetails(String custNo){
		DetachedCriteria acctCriteria = DetachedCriteria.forClass(AmtbAccount.class);
		acctCriteria.add(Restrictions.eq("custNo", custNo));
		// joining up with account billing cycle
		acctCriteria.createCriteria("amtbAcctBillCycles", DetachedCriteria.LEFT_JOIN);
		// joining up with volume discount
		acctCriteria.createCriteria("amtbAcctVolDiscs", DetachedCriteria.LEFT_JOIN)
		.createCriteria("mstbVolDiscMaster", DetachedCriteria.FULL_JOIN);
		// joining up with admin fees
		acctCriteria.createCriteria("amtbAcctAdminFees", DetachedCriteria.LEFT_JOIN)
		.createCriteria("mstbAdminFeeMaster", DetachedCriteria.FULL_JOIN);
		// joining up with credit term
		acctCriteria.createCriteria("amtbAcctCredTerms", DetachedCriteria.LEFT_JOIN)
		.createCriteria("mstbCreditTermMaster", DetachedCriteria.FULL_JOIN);
		// joining up with early payment
		acctCriteria.createCriteria("amtbAcctEarlyPymts", DetachedCriteria.LEFT_JOIN)
		.createCriteria("mstbEarlyPaymentMaster", DetachedCriteria.FULL_JOIN);
		// joining up with late payment
		acctCriteria.createCriteria("amtbAcctLatePymts", DetachedCriteria.LEFT_JOIN)
		.createCriteria("mstbLatePaymentMaster", DetachedCriteria.FULL_JOIN);
		// joining up with promotion
		acctCriteria.createCriteria("amtbAcctPromotions", DetachedCriteria.LEFT_JOIN)
		.createCriteria("mstbPromotion", DetachedCriteria.FULL_JOIN);
		// joining up with bank
		acctCriteria.createCriteria("mstbBankMaster", DetachedCriteria.LEFT_JOIN);
		// joining up with branch
		acctCriteria.createCriteria("mstbBranchMaster", DetachedCriteria.LEFT_JOIN);
		// joining up with default payment
		acctCriteria.createCriteria("mstbMasterTableByDefaultPaymentMode", DetachedCriteria.LEFT_JOIN);
		List<AmtbAccount> accts = this.findAllByCriteria(acctCriteria);
		return accts.isEmpty() ? null : accts.get(0);
	}
	

	@SuppressWarnings("unchecked")
	public AmtbAccount getCurrentAndFutureBillingDetails(String custNo){
		DetachedCriteria acctCriteria = DetachedCriteria.forClass(AmtbAccount.class);
		acctCriteria.add(Restrictions.eq("custNo", custNo));
		// joining up with account billing cycle
		acctCriteria.createCriteria("amtbAcctBillCycles", DetachedCriteria.LEFT_JOIN);
		// joining up with volume discount
		acctCriteria.createCriteria("amtbAcctVolDiscs", DetachedCriteria.LEFT_JOIN)
		.createCriteria("mstbVolDiscMaster", DetachedCriteria.FULL_JOIN);
		// joining up with admin fees
		acctCriteria.createCriteria("amtbAcctAdminFees", DetachedCriteria.LEFT_JOIN)
		.createCriteria("mstbAdminFeeMaster", DetachedCriteria.FULL_JOIN);
		// joining up with credit term
		acctCriteria.createCriteria("amtbAcctCredTerms", DetachedCriteria.LEFT_JOIN)
		.createCriteria("mstbCreditTermMaster", DetachedCriteria.FULL_JOIN);
		// joining up with early payment
		acctCriteria.createCriteria("amtbAcctEarlyPymts", DetachedCriteria.LEFT_JOIN)
		.createCriteria("mstbEarlyPaymentMaster", DetachedCriteria.FULL_JOIN);
		// joining up with late payment
		acctCriteria.createCriteria("amtbAcctLatePymts", DetachedCriteria.LEFT_JOIN)
		.createCriteria("mstbLatePaymentMaster", DetachedCriteria.FULL_JOIN);
		// joining up with promotion
		acctCriteria.createCriteria("amtbAcctPromotions", DetachedCriteria.LEFT_JOIN)
		.createCriteria("mstbPromotion", DetachedCriteria.FULL_JOIN);
		// joining up with bank
		acctCriteria.createCriteria("mstbBankMaster", DetachedCriteria.LEFT_JOIN);
		// joining up with branch
		acctCriteria.createCriteria("mstbBranchMaster", DetachedCriteria.LEFT_JOIN);
		// joining up with default payment
		acctCriteria.createCriteria("mstbMasterTableByDefaultPaymentMode", DetachedCriteria.LEFT_JOIN);

		// ensure Current and Future billing details
		acctCriteria.add(Restrictions.ge("createdDt", new Date()));
		acctCriteria.add(Restrictions.ge("updatedDt", new Date()));
//		acctCriteria.addOrder(Order.asc("updatedDt"));
		
		List<AmtbAccount> accts = this.findAllByCriteria(acctCriteria);
		return accts.isEmpty() ? null : accts.get(0);
	}
	
	@SuppressWarnings("unchecked")
	public AmtbAccount getBillingCycleHistoricalDetails(String custNo){
		DetachedCriteria acctCriteria = DetachedCriteria.forClass(AmtbAccount.class);
		acctCriteria.add(Restrictions.eq("custNo", custNo));
		// ensure only past billing details
		// joining up with account billing cycle
		acctCriteria.createCriteria("amtbAcctBillCycles", DetachedCriteria.LEFT_JOIN);
		// joining up with volume discount
		acctCriteria.createCriteria("amtbAcctVolDiscs", DetachedCriteria.LEFT_JOIN)
		.createCriteria("mstbVolDiscMaster", DetachedCriteria.FULL_JOIN);
		// joining up with admin fees
		DetachedCriteria adminFee = acctCriteria.createCriteria("amtbAcctAdminFees", "amtbAcctAdminFees", DetachedCriteria.LEFT_JOIN)
		.createCriteria("mstbAdminFeeMaster", DetachedCriteria.FULL_JOIN);
		// joining up with bank
		acctCriteria.createCriteria("mstbBankMaster", DetachedCriteria.LEFT_JOIN);
		// joining up with branch
		acctCriteria.createCriteria("mstbBranchMaster", DetachedCriteria.LEFT_JOIN);
		// joining up with default payment
		acctCriteria.createCriteria("mstbMasterTableByDefaultPaymentMode", DetachedCriteria.LEFT_JOIN);
		
		adminFee.add(Restrictions.le("amtbAcctAdminFees.effectiveDate", new Date()));
		
		List<AmtbAccount> accts = this.findAllByCriteria(acctCriteria);
		return accts.isEmpty() ? null : accts.get(0);
	}
	@SuppressWarnings("unchecked")
	public AmtbAccount getCurrentAndFutureCreditTermDetails(String custNo){
		DetachedCriteria acctCriteria = DetachedCriteria.forClass(AmtbAccount.class);
		acctCriteria.add(Restrictions.eq("custNo", custNo));
		// ensure only past billing details
		acctCriteria.add(Restrictions.ge("createdDt", new Date()));
		acctCriteria.add(Restrictions.ge("updatedDt", new Date()));
		// joining up with credit term // take out for billing cycle ..
		acctCriteria.createCriteria("amtbAcctCredTerms", DetachedCriteria.LEFT_JOIN)
		.createCriteria("mstbCreditTermMaster", DetachedCriteria.FULL_JOIN);
		List<AmtbAccount> accts = this.findAllByCriteria(acctCriteria);
		return accts.isEmpty() ? null : accts.get(0);
	}
	@SuppressWarnings("unchecked")
	public AmtbAccount getCreditTermHistoricalDetails(String custNo){
		DetachedCriteria acctCriteria = DetachedCriteria.forClass(AmtbAccount.class);
		acctCriteria.add(Restrictions.eq("custNo", custNo));
		// ensure only past billing details
		// joining up with credit term // take out for billing cycle ..
		DetachedCriteria creditTerms = acctCriteria.createCriteria("amtbAcctCredTerms", "amtbAcctCredTerms", DetachedCriteria.LEFT_JOIN)
		.createCriteria("mstbCreditTermMaster", DetachedCriteria.FULL_JOIN);
		
		creditTerms.add(Restrictions.le("amtbAcctCredTerms.effectiveDate", new Date()));
		
		List<AmtbAccount> accts = this.findAllByCriteria(acctCriteria);
		return accts.isEmpty() ? null : accts.get(0);
	}
	@SuppressWarnings("unchecked")
	public AmtbAccount getEarlyPaymentHistoricalDetails(String custNo){
		DetachedCriteria acctCriteria = DetachedCriteria.forClass(AmtbAccount.class);
		acctCriteria.add(Restrictions.eq("custNo", custNo));
		// ensure only past billing details
		// joining up with early payment
		DetachedCriteria earlyPymts = acctCriteria.createCriteria("amtbAcctEarlyPymts", "amtbAcctEarlyPymts", DetachedCriteria.LEFT_JOIN)
		.createCriteria("mstbEarlyPaymentMaster", DetachedCriteria.FULL_JOIN);

		earlyPymts.add(Restrictions.le("amtbAcctEarlyPymts.effectiveDate", new Date()));
		
		List<AmtbAccount> accts = this.findAllByCriteria(acctCriteria);
		return accts.isEmpty() ? null : accts.get(0);
	}
	@SuppressWarnings("unchecked")
	public AmtbAccount getLatePaymentHistoricalDetails(String custNo){
		DetachedCriteria acctCriteria = DetachedCriteria.forClass(AmtbAccount.class);
		acctCriteria.add(Restrictions.eq("custNo", custNo));
		// ensure only past billing details
		// joining up with late payment
		
		DetachedCriteria latePymts = acctCriteria.createCriteria("amtbAcctLatePymts", "amtbAcctLatePymts", DetachedCriteria.LEFT_JOIN)
		.createCriteria("mstbLatePaymentMaster", DetachedCriteria.FULL_JOIN);
		
		latePymts.add(Restrictions.le("amtbAcctLatePymts.effectiveDate", new Date()));
		
		List<AmtbAccount> accts = this.findAllByCriteria(acctCriteria);
		return accts.isEmpty() ? null : accts.get(0);
	}
	@SuppressWarnings("unchecked")
	public AmtbAccount getPromotionHistoricalDetails(String custNo){
		DetachedCriteria acctCriteria = DetachedCriteria.forClass(AmtbAccount.class);
		acctCriteria.add(Restrictions.eq("custNo", custNo));
		// ensure only past billing details
		// joining up with promotion
		DetachedCriteria promotions = acctCriteria.createCriteria("amtbAcctPromotions", "amtbAcctPromotions", DetachedCriteria.LEFT_JOIN)
		.createCriteria("mstbPromotion", DetachedCriteria.FULL_JOIN);

		promotions.add(Restrictions.le("amtbAcctPromotions.effectiveDateFrom", new Date()));
		
		List<AmtbAccount> accts = this.findAllByCriteria(acctCriteria);
		return accts.isEmpty() ? null : accts.get(0);
	}
	
	@SuppressWarnings("unchecked")
	public AmtbAccount getRewardsDetails(String custNo){
		DetachedCriteria acctCriteria = DetachedCriteria.forClass(AmtbAccount.class);
		acctCriteria.add(Restrictions.eq("custNo", custNo));
		acctCriteria.createCriteria("lrtbRewardAccounts", DetachedCriteria.LEFT_JOIN)
		.createCriteria("lrtbRewardTxns", DetachedCriteria.LEFT_JOIN)
		.createCriteria("lrtbRewardMaster", DetachedCriteria.LEFT_JOIN);
		List<AmtbAccount> accts = this.findAllByCriteria(acctCriteria);
		return accts.isEmpty() ? null : accts.get(0);
	}
	
	@SuppressWarnings("unchecked")
	public AmtbAccount getDepositDetails(String custNo){
		DetachedCriteria acctCriteria = DetachedCriteria.forClass(AmtbAccount.class);
		acctCriteria.add(Restrictions.eq("custNo", custNo));
		DetachedCriteria depositTxnCriteria = acctCriteria.createCriteria("bmtbInvoiceDepositTxns", "depositTxn", DetachedCriteria.LEFT_JOIN);
		depositTxnCriteria.createCriteria("bmtbInvoiceHeader", DetachedCriteria.LEFT_JOIN).addOrder(Order.asc("invoiceNo"));
		
		Disjunction disjunction = Restrictions.disjunction();
		disjunction.add(Restrictions.eq("depositTxn.txnType", NonConfigurableConstants.DEPOSIT_TXN_TYPE_REQUEST));
		disjunction.add(Restrictions.isNull("depositTxn.txnType"));
		acctCriteria.add(disjunction);
		
		acctCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		List<AmtbAccount> accts = this.findAllByCriteria(acctCriteria);
		return accts.isEmpty() ? null : accts.get(0);
	}
	
	public AmtbAccount getParent(AmtbAccount account) {
		DetachedCriteria AccountContactCriteria = DetachedCriteria.forClass(AmtbAccount.class);
		AccountContactCriteria.add(Restrictions.eq("accountNo", account.getAccountNo()))
		.createCriteria("amtbAccount", DetachedCriteria.LEFT_JOIN)
		.createCriteria("amtbAccount", DetachedCriteria.LEFT_JOIN);
		return (AmtbAccount)this.findAllByCriteria(AccountContactCriteria).get(0);
	}

	@SuppressWarnings("unchecked")
	public List<AmtbAccount> getBilliableAccountOnlyTopLevel(String customerNo, String name){
		DetachedCriteria accountCriteria = DetachedCriteria.forClass(AmtbAccount.class);
		accountCriteria.createCriteria("amtbAcctType", DetachedCriteria.INNER_JOIN);
		accountCriteria.createCriteria("mstbMasterTableByDefaultPaymentMode", DetachedCriteria.LEFT_JOIN);
		accountCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);

		if(customerNo!=null){
			accountCriteria.add(Restrictions.eq("custNo", customerNo));
		}
		if(name!=null && name.length()>=3) {
			accountCriteria.add(Restrictions.ilike("accountName", name, MatchMode.ANYWHERE));
		}

		//billiable = invoice format not null
		accountCriteria.add(Restrictions.isNotNull("invoiceFormat"));
		//must be top level
		accountCriteria.add(Restrictions.in("accountCategory", new String[]{NonConfigurableConstants.ACCOUNT_CATEGORY_APPLICANT, NonConfigurableConstants.ACCOUNT_CATEGORY_CORPORATE}));

		accountCriteria.addOrder(Order.asc("accountName"));

		return this.findAllByCriteria(accountCriteria);
	}

	@SuppressWarnings("unchecked")
	public List<AmtbAccount> getBilliableAccount(String customerNo, String name, String code){
		DetachedCriteria accountCriteria = DetachedCriteria.forClass(AmtbAccount.class);
		accountCriteria.createCriteria("amtbAcctType", DetachedCriteria.LEFT_JOIN);
		accountCriteria.createCriteria("mstbMasterTableByDefaultPaymentMode", DetachedCriteria.LEFT_JOIN);
		accountCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);

		if(customerNo!=null && customerNo.length()>=3){
			Integer customerNoInteger = null;
			try{ customerNoInteger = new Integer(customerNo); }
			catch(Exception e){	}
			if(customerNoInteger!=null && customerNoInteger.toString().length()<=3){
				accountCriteria.add(
						Restrictions.or(
								Restrictions.ilike("custNo", customerNo, MatchMode.ANYWHERE),
								Restrictions.eq("custNo", customerNoInteger.toString())
						)
				);
			} else {
				accountCriteria.add(Restrictions.ilike("custNo", customerNo, MatchMode.ANYWHERE));
			}
		}
		if(name!=null && name.length()>=3) {
			accountCriteria.add(Restrictions.ilike("accountName", name, MatchMode.ANYWHERE));
		}
		if(code!=null && code.length()>=3) {
			accountCriteria.add(Restrictions.ilike("code", code, MatchMode.ANYWHERE));
		}

		//billiable = invoice format not null
		accountCriteria.add(Restrictions.isNotNull("invoiceFormat"));

		accountCriteria.addOrder(Order.asc("accountName"));

		return this.findAllByCriteria(accountCriteria);
	}

	@SuppressWarnings("unchecked")
	public List<AmtbAccount> getBilliableAccountByParentAccount(AmtbAccount parentAccount){
		DetachedCriteria accountCriteria = DetachedCriteria.forClass(AmtbAccount.class);
		DetachedCriteria parentAccountCriteria = accountCriteria.createCriteria("amtbAccount", Criteria.LEFT_JOIN);
		parentAccountCriteria.add(Restrictions.idEq(parentAccount.getAccountNo()));

		//billiable = invoice format not null
		accountCriteria.add(Restrictions.isNotNull("invoiceFormat"));

		accountCriteria.addOrder(Order.asc("accountName"));
		accountCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);

		return this.findAllByCriteria(accountCriteria);
	}

	@SuppressWarnings("unchecked")
	public List<AmtbAccount> getBilliableAccountByGrandParentAccount(AmtbAccount grandParentAccount){
		DetachedCriteria accountCriteria = DetachedCriteria.forClass(AmtbAccount.class);
		DetachedCriteria parentAccountCriteria = accountCriteria.createCriteria("amtbAccount", Criteria.LEFT_JOIN);
		DetachedCriteria grandParentAccountCriteria = parentAccountCriteria.createCriteria("amtbAccount", Criteria.LEFT_JOIN);
		grandParentAccountCriteria.add(Restrictions.idEq(grandParentAccount.getAccountNo()));

		//billiable = invoice format not null
		accountCriteria.add(Restrictions.isNotNull("invoiceFormat"));

		accountCriteria.addOrder(Order.asc("accountName"));
		accountCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);

		return this.findAllByCriteria(accountCriteria);
	}

	@SuppressWarnings("unchecked")
	public List<AmtbAccount> getPersAccounts(String parentCustNo, String acctName, String nric, String email){
		DetachedCriteria acctCriteria = DetachedCriteria.forClass(AmtbAccount.class);
		if(acctName!=null && acctName.trim().length()!=0){
			acctCriteria.add(Restrictions.ilike("accountName", acctName, MatchMode.ANYWHERE));
		}
		DetachedCriteria persCriteria = acctCriteria.createCriteria("amtbPersonalDetails", DetachedCriteria.FULL_JOIN);
		if(nric!=null && nric.trim().length()!=0){
			persCriteria.add(Restrictions.ilike("nric", nric, MatchMode.ANYWHERE));
		}
		DetachedCriteria contactCriteria = acctCriteria.createCriteria("amtbContactPersons", DetachedCriteria.FULL_JOIN);
		if(email!=null && email.trim().length()!=0){
			contactCriteria.add(Restrictions.ilike("mainContactEmail", email, MatchMode.ANYWHERE));
		}
		if(parentCustNo!=null && parentCustNo.trim().length()!=0){
			DetachedCriteria parentCriteria = acctCriteria.createCriteria("amtbAccount", DetachedCriteria.LEFT_JOIN);
			parentCriteria.add(Restrictions.eq("custNo", parentCustNo));
		}
		return this.findAllByCriteria(acctCriteria);
	}


	@SuppressWarnings("unchecked")
	public List<AmtbAccount> getChildrenAccountsWithSubscriptionsAndMainContacts(Integer parentAccountNo){
		DetachedCriteria acctCriteria = DetachedCriteria.forClass(AmtbAccount.class);
		acctCriteria.createCriteria("amtbSubscTos", DetachedCriteria.LEFT_JOIN);
		acctCriteria.createCriteria("amtbAcctMainContacts", DetachedCriteria.LEFT_JOIN);
		acctCriteria = acctCriteria.createCriteria("amtbAccount", DetachedCriteria.LEFT_JOIN);
		acctCriteria.add(Restrictions.eq("accountNo", parentAccountNo));
		acctCriteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		return this.findAllByCriteria(acctCriteria);
	}
	
	@SuppressWarnings("unchecked")
	public List<AmtbAccount> getChildrenAccountsWithSubscriptions(Integer parentAccountNo){
		DetachedCriteria acctCriteria = DetachedCriteria.forClass(AmtbAccount.class);
		acctCriteria.createCriteria("amtbSubscTos", DetachedCriteria.LEFT_JOIN);
		acctCriteria = acctCriteria.createCriteria("amtbAccount", DetachedCriteria.LEFT_JOIN);
		acctCriteria.add(Restrictions.eq("accountNo", parentAccountNo));
		acctCriteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		return this.findAllByCriteria(acctCriteria);
	}
	
	@SuppressWarnings("unchecked")
	public void deleteAccount(Integer accountNo, String userId){
		DetachedCriteria acctCriteria = DetachedCriteria.forClass(AmtbAccount.class);
		acctCriteria.add(Restrictions.eq("accountNo", accountNo));
		// joining 3 levels
		acctCriteria.createCriteria("amtbAccounts", DetachedCriteria.LEFT_JOIN)
		.createCriteria("amtbAccounts", DetachedCriteria.LEFT_JOIN);
		acctCriteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		List<AmtbAccount> accts = this.findAllByCriteria(acctCriteria);
		if(!accts.isEmpty()){
			AmtbAccount acct = accts.get(0);
			clearAccount(acct, 2, userId);
		}
	}
	private void clearAccount(AmtbAccount acct, int depth, String userId){
		logger.info("clearAccount(AmtbAccount acct, int depth)");
		if(depth != 1){
			Set<AmtbAccount> childrenAccts = acct.getAmtbAccounts();
			for(AmtbAccount childAcct : childrenAccts){
				clearAccount(childAcct, depth-1, userId);
			}
		}
		/* to use SQL instead of HQL. Due to hibernate is not able to handle recursive
		 * relationships properly where it exist in amtb_account table.
		 */
		// creating audit log
		SatbAuditLog log = new SatbAuditLog();
		log.setLoginId(userId);
		log.setAction("D");
		log.setEntity(AmtbAccount.class.getName());
		log.setEntityId(acct.getAccountNo().toString());
		log.setNewValue(null);
		log.setOldValue(acct.toString());
		log.setTime(DateUtil.getCurrentTimestamp());
		this.save(log);
		Session session = getHibernateTemplate().getSessionFactory().openSession();
		Transaction txn = session.beginTransaction();
		Query query = session.createSQLQuery("delete from amtb_subsc_to where account_no = :account_no");
		query.setInteger("account_no", acct.getAccountNo());
		logger.info("deleted subscriptions = " + query.executeUpdate());
		query = session.createSQLQuery("delete from amtb_acct_main_contact where account_no = :account_no");
		query.setInteger("account_no", acct.getAccountNo());
		logger.info("deleted main contacts = " + query.executeUpdate());
		query = session.createSQLQuery("delete from amtb_contact_person where account_no = :account_no");
		query.setInteger("account_no", acct.getAccountNo());
		logger.info("deleted contact person = " + query.executeUpdate());
		query = session.createSQLQuery("delete from amtb_personal_detail where account_no = :account_no");
		query.setInteger("account_no", acct.getAccountNo());
		logger.info("deleted personal detail = " + query.executeUpdate());
		query = session.createSQLQuery("delete from amtb_account where account_no = :account_no");
		query.setInteger("account_no", acct.getAccountNo());
		logger.info("delete accounts = " + query.executeUpdate());
		txn.commit();
	}

	@SuppressWarnings("unchecked")
	public boolean hasPendingBillingChangeRequest(String custNo){
		DetachedCriteria acctCriteria = DetachedCriteria.forClass(AmtbAccount.class);
		acctCriteria.add(Restrictions.eq("custNo", custNo));
		acctCriteria.createCriteria("amtbBillReqs", DetachedCriteria.LEFT_JOIN)
		.createCriteria("amtbBillReqFlows", DetachedCriteria.LEFT_JOIN);
		List<AmtbAccount> accts = this.findAllByCriteria(acctCriteria);
		if(!accts.isEmpty()){
			AmtbAccount acct = accts.get(0);
			for(AmtbBillReq request : acct.getAmtbBillReqs()){
				TreeSet<AmtbBillReqFlow> sortedFlow = new TreeSet<AmtbBillReqFlow>(new Comparator<AmtbBillReqFlow>(){
					public int compare(AmtbBillReqFlow o1, AmtbBillReqFlow o2) {
						return o1.getFlowDt().compareTo(o2.getFlowDt());
					}
				});
				sortedFlow.addAll(request.getAmtbBillReqFlows());
				if(!sortedFlow.isEmpty()){
					if(sortedFlow.last().getToStatus().equals(NonConfigurableConstants.BILLING_REQUEST_STATUS_PENDING)){
						return true;
					}
				}
			}
		}
		return false;
	}
	@SuppressWarnings("unchecked")
	public List<AmtbAccount> getChildrenAccountsWithStatuses(Integer parentNo){
		DetachedCriteria acctCriteria = DetachedCriteria.forClass(AmtbAccount.class);
		acctCriteria.createCriteria("amtbAccount", DetachedCriteria.LEFT_JOIN)
		.add(Restrictions.eq("accountNo", parentNo));
		acctCriteria.createCriteria("amtbAcctStatuses", DetachedCriteria.LEFT_JOIN);
		acctCriteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		return this.findAllByCriteria(acctCriteria);
	}
	@SuppressWarnings("unchecked")
	public List<AmtbAcctStatus> getStatuses(Integer accountNo){
		DetachedCriteria statusCriteria = DetachedCriteria.forClass(AmtbAcctStatus.class);
		statusCriteria.createCriteria("amtbAccount", DetachedCriteria.LEFT_JOIN)
		.add(Restrictions.eq("accountNo", accountNo));
		return this.findAllByCriteria(statusCriteria);
	}
	@SuppressWarnings("unchecked")
	// TODO: Product Subscription Approval
	// taken from getAccountWithCreditDetails
	public AmtbAccount getAccountWithCreditDetails(String custNo, int level, String parentCode, String code){
		DetachedCriteria acctCriteria = DetachedCriteria.forClass(AmtbAccount.class);
		if(code!=null && code.trim().length()!=0){
			acctCriteria.add(Restrictions.eq("code", code));
		}
		DetachedCriteria tempCriteria = acctCriteria;
		for(int i=0;i<level;i++){
			tempCriteria = tempCriteria.createCriteria("amtbAccount", DetachedCriteria.FULL_JOIN);
			if(i==0){// first loop. filter parent code
				if(parentCode!=null && parentCode.trim().length()!=0){
					tempCriteria.add(Restrictions.eq("code", parentCode));
				}
			}
		}
		tempCriteria.add(Restrictions.eq("custNo", custNo));
		acctCriteria.createCriteria("amtbAcctCredLimits", DetachedCriteria.LEFT_JOIN);
		acctCriteria.createCriteria("amtbCredRevReqs", DetachedCriteria.LEFT_JOIN)
		.createCriteria("amtbCredRevReqFlows", DetachedCriteria.LEFT_JOIN);
		List<AmtbAccount> accts = this.findAllByCriteria(acctCriteria);
		if(!accts.isEmpty()){
			return accts.get(0);
		}else{
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	public AmtbAccount getAccountWithProductSubscriptionApprovalDetails(String custNo, int level, String parentCode, String code){
		DetachedCriteria acctCriteria = DetachedCriteria.forClass(AmtbAccount.class);
		if(code!=null && code.trim().length()!=0){
			acctCriteria.add(Restrictions.eq("code", code));
		}
		DetachedCriteria tempCriteria = acctCriteria;
		for(int i=0;i<level;i++){
			tempCriteria = tempCriteria.createCriteria("amtbAccount", DetachedCriteria.FULL_JOIN);
			if(i==0){// first loop. filter parent code
				if(parentCode!=null && parentCode.trim().length()!=0){
					tempCriteria.add(Restrictions.eq("code", parentCode));
				}
			}
		}
		tempCriteria.add(Restrictions.eq("custNo", custNo));
		acctCriteria.createCriteria("amtbAcctCredLimits", DetachedCriteria.LEFT_JOIN);
		acctCriteria.createCriteria("amtbCredRevReqs", DetachedCriteria.LEFT_JOIN)
		.createCriteria("amtbCredRevReqFlows", DetachedCriteria.LEFT_JOIN);
		List<AmtbAccount> accts = this.findAllByCriteria(acctCriteria);
		if(!accts.isEmpty()){
			return accts.get(0);
		}else{
			return null;
		}
	}
	@SuppressWarnings("unchecked")
	public AmtbAccount getAccountWithCreditDetails(Integer accountNo){
		DetachedCriteria acctCriteria = DetachedCriteria.forClass(AmtbAccount.class)
		.add(Restrictions.eq("accountNo", accountNo));
		acctCriteria.createCriteria("amtbAcctCredLimits", DetachedCriteria.LEFT_JOIN)
		.createCriteria("satbUser", DetachedCriteria.LEFT_JOIN);
		acctCriteria.createCriteria("amtbCredRevReqs", DetachedCriteria.LEFT_JOIN)
		.createCriteria("amtbCredRevReqFlows", DetachedCriteria.LEFT_JOIN);
		acctCriteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		List<AmtbAccount> accts = this.findAllByCriteria(acctCriteria);
		if(!accts.isEmpty()){
			return accts.get(0);
		}else{
			return null;
		}
	}
	
	public AmtbAccount getAccount(String cardNo, Timestamp tripDt){
		DetachedCriteria accountCriteria = DetachedCriteria.forClass(AmtbAccount.class);
		accountCriteria.createCriteria("amtbCorporateDetails", Criteria.LEFT_JOIN);
		DetachedCriteria account2ndLvlCriteria = accountCriteria.createCriteria("amtbAccount", Criteria.LEFT_JOIN);
		account2ndLvlCriteria.createCriteria("amtbCorporateDetails", Criteria.LEFT_JOIN);
		DetachedCriteria account3rdLvlCriteria = account2ndLvlCriteria.createCriteria("amtbAccount", Criteria.LEFT_JOIN);
		account3rdLvlCriteria.createCriteria("amtbCorporateDetails", Criteria.LEFT_JOIN);
		DetachedCriteria mainBillingContacts = accountCriteria.createCriteria("amtbAcctMainContacts", Criteria.LEFT_JOIN);
		DetachedCriteria productCriteria = accountCriteria.createCriteria("pmtbProducts", Criteria.LEFT_JOIN);
		DetachedCriteria productStatusCriteria = productCriteria.createCriteria("pmtbProductStatuses",Criteria.LEFT_JOIN);
		productCriteria.createCriteria("pmtbProductType", Criteria.LEFT_JOIN);
		// cannot remove this as it will only retrieve an account if there is such a product at that time
		//logger.info("Date & Time: " + tripDt.toString());
		// productStatusCriteria.add(Restrictions.le("statusDt", DateUtil.convertStrToTimestamp(DateUtil.convertTimestampToStr(tripDt, DateUtil.TRIPS_DATE_FORMAT), DateUtil.TRIPS_DATE_FORMAT)));
		productStatusCriteria.add(Restrictions.le("statusDt", tripDt));
		
		//productStatusCriteria.add(Restrictions.eq("statusTo", NonConfigurableConstants.ACCOUNT_STATUS_ACTIVE));
		mainBillingContacts.add(Restrictions.eq("comp_id.mainContactType", NonConfigurableConstants.MAIN_CONTACT_TYPE_BILLING));
		productCriteria.add(Restrictions.like("cardNo", cardNo));
		productCriteria.addOrder(Order.desc("productNo"));
		//productStatusCriteria.addOrder(Order.desc("statusDt"));
		//accountCriteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		List results = this.findMaxResultByCriteria(accountCriteria, MAX_ROW);
		if(results.isEmpty()) {
			return null;
		} else {
			return (AmtbAccount)results.get(0);
		}
	}

	public AmtbAccount getAccountForDownload(String cardNo, Timestamp tripDt){
		DetachedCriteria accountCriteria = DetachedCriteria.forClass(AmtbAccount.class);
		DetachedCriteria account2ndLvlCriteria = accountCriteria.createCriteria("amtbAccount", Criteria.LEFT_JOIN);
		account2ndLvlCriteria.createCriteria("amtbAccount", Criteria.LEFT_JOIN);
		DetachedCriteria productCriteria = accountCriteria.createCriteria("pmtbProducts", Criteria.LEFT_JOIN);
		DetachedCriteria productStatusCriteria = productCriteria.createCriteria("pmtbProductStatuses",Criteria.LEFT_JOIN);
		productStatusCriteria.add(Restrictions.le("statusDt", tripDt));
		
		productCriteria.add(Restrictions.like("cardNo", cardNo));
		productCriteria.addOrder(Order.desc("productNo"));
		List results = this.findMaxResultByCriteria(accountCriteria, MAX_ROW);
		if(results.isEmpty()) {
			return null;
		} else {
			return (AmtbAccount)results.get(0);
		}
	}

	@SuppressWarnings("unchecked")
	public List<AmtbAccount> getPremierAccountsAcct(AmtbAccount amtbAccount, List<PmtbProductType> productTypes){
		// Find all the children for the particular amtbAccount
		DetachedCriteria accountCriteria = DetachedCriteria.forClass(AmtbAccount.class);

		// Get only premier account
		DetachedCriteria parentAcctCriteria = accountCriteria.createCriteria("amtbAccount", Criteria.LEFT_JOIN);
		DetachedCriteria subscribeTosCriteria = accountCriteria.createCriteria("amtbSubscTos", Criteria.LEFT_JOIN);
	//	subscribeTosCriteria.add(Restrictions.eq("comp_id.pmtbProductType.productTypeId", productTypeId));
	
		Disjunction disjunction = Restrictions.disjunction();
		
		for(PmtbProductType productType : productTypes) {
			disjunction.add(Restrictions.eq("comp_id.pmtbProductType.productTypeId", productType.getProductTypeId()));
		}
		subscribeTosCriteria.add(disjunction);
		
		parentAcctCriteria.add(Restrictions.eq("accountNo", amtbAccount.getAccountNo()));

		accountCriteria.addOrder(Order.asc("accountName"));
		accountCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		List<AmtbAccount> results = this.findAllByCriteria(accountCriteria);

		if(results.isEmpty()) {
			return null;
		} else {
			return  results;
		}
	}
	@SuppressWarnings("unchecked")
	public List<AmtbAccount> getPremierAccount (String accNo,String name, List<PmtbProductType> productTypes){

		logger.info("Retriving data by name "+name);
		DetachedCriteria accountCriteria = DetachedCriteria.forClass(AmtbAccount.class);
		accountCriteria.createCriteria("amtbCorporateDetails", Criteria.LEFT_JOIN);
		if(accNo!=null && accNo.length()>2) {
			//accountCriteria.add(Restrictions.or(Restrictions.ilike("custNo",accNo, MatchMode.EXACT),Restrictions.eq("custNo",new Integer(accNo).toString())));
			accountCriteria.add(Restrictions.eq("custNo",new Integer(accNo).toString()));
		}
		if(name!=null && name.length()>2) {
			accountCriteria.add(Restrictions.ilike("accountName", name, MatchMode.ANYWHERE));
			accountCriteria.add(Restrictions.isNotNull("custNo"));
		}


		logger.info("***********This is the criteria"+accountCriteria.toString());

		// Get only premier account
//		accountCriteria.add(Restrictions.in("accountCategory", new String[]{NonConfigurableConstants.ACCOUNT_CATEGORY_APPLICANT, NonConfigurableConstants.ACCOUNT_CATEGORY_CORPORATE}));
		DetachedCriteria subscribeTosCriteria = accountCriteria.createCriteria("amtbSubscTos", Criteria.LEFT_JOIN);
		
		Disjunction disjunction = Restrictions.disjunction();
		
		for(PmtbProductType productType : productTypes) {
			disjunction.add(Restrictions.eq("comp_id.pmtbProductType.productTypeId", productType.getProductTypeId()));
		}
		subscribeTosCriteria.add(disjunction);
		accountCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		List<AmtbAccount> results = this.findAllByCriteria(accountCriteria);
		if(results.isEmpty()) {
			return null;
		} else {
			return  results;
		}

	}

	@SuppressWarnings("unchecked")
	public List<AmtbAccount> getPremierAccounts (AmtbAccount amtbAccount, String productTypeId){
		// Find all the children for the particular amtbAccount
		DetachedCriteria accountCriteria = DetachedCriteria.forClass(AmtbAccount.class);

		// Get only premier account
		DetachedCriteria parentAcctCriteria = accountCriteria.createCriteria("amtbAccount", Criteria.LEFT_JOIN);
		DetachedCriteria subscribeTosCriteria = accountCriteria.createCriteria("amtbSubscTos", Criteria.LEFT_JOIN);
		subscribeTosCriteria.add(Restrictions.eq("comp_id.pmtbProductType.productTypeId", productTypeId));
		parentAcctCriteria.add(Restrictions.eq("accountNo", amtbAccount.getAccountNo()));

		accountCriteria.addOrder(Order.asc("accountName"));
		accountCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		List<AmtbAccount> results = this.findAllByCriteria(accountCriteria);

		if(results.isEmpty()) {
			return null;
		} else {
			return  results;
		}
	}

	@SuppressWarnings("unchecked")
	public List<AmtbAccount> getAccounts (AmtbAccount amtbAccount){
		// Find all the children for the particular amtbAccount
		DetachedCriteria accountCriteria = DetachedCriteria.forClass(AmtbAccount.class);

		// Get account
		DetachedCriteria parentAcctCriteria = accountCriteria.createCriteria("amtbAccount", Criteria.LEFT_JOIN);

		parentAcctCriteria.add(Restrictions.eq("accountNo", amtbAccount.getAccountNo()));

		// To check for only corporate account and applicant account

		//accountCriteria.add(Restrictions.in("accountCategory", new String[]{NonConfigurableConstants.ACCOUNT_CATEGORY_APPLICANT, NonConfigurableConstants.ACCOUNT_CATEGORY_CORPORATE}));
		accountCriteria.addOrder(Order.asc("accountName"));
		accountCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		List<AmtbAccount> results = this.findAllByCriteria(accountCriteria);

		if(results.isEmpty()) {
			return null;
		} else {
			return  results;
		}
	}
	
	@SuppressWarnings("unchecked")
	public List<AmtbAccount> getAccounts (AmtbAccount amtbAccount, String productTypeId){
		// Find all the children for the particular amtbAccount
		DetachedCriteria accountCriteria = DetachedCriteria.forClass(AmtbAccount.class);
		DetachedCriteria subscribeTo = accountCriteria.createCriteria("amtbSubscTos", DetachedCriteria.LEFT_JOIN);
		subscribeTo.createCriteria("comp_id.pmtbProductType", DetachedCriteria.LEFT_JOIN);

		// Get account
		DetachedCriteria parentAcctCriteria = accountCriteria.createCriteria("amtbAccount", Criteria.LEFT_JOIN);

		parentAcctCriteria.add(Restrictions.eq("accountNo", amtbAccount.getAccountNo()));
		subscribeTo.add(Restrictions.eq("comp_id.pmtbProductType.productTypeId", productTypeId));
		accountCriteria.addOrder(Order.asc("accountName"));
		accountCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		List<AmtbAccount> results = this.findAllByCriteria(accountCriteria);

		if(results.isEmpty()) {
			return null;
		} else {
			return  results;
		}
	}

	@SuppressWarnings("unchecked")
	public List<AmtbAccount> getAccounts(String accNo,String name){

		logger.info("Retriving data by name "+name);
		DetachedCriteria accountCriteria = DetachedCriteria.forClass(AmtbAccount.class);
		//DetachedCriteria childAccountCriteria = accountCriteria.createCriteria("amtbAccounts", Criteria.LEFT_JOIN); //join division
		//childAccountCriteria.createCriteria("amtbAccounts", Criteria.LEFT_JOIN); //join department

		if(accNo!=null) {
			//accountCriteria.add(Restrictions.or(Restrictions.ilike("custNo",accNo, MatchMode.EXACT),Restrictions.eq("custNo",new Integer(accNo).toString())));
			accountCriteria.add(Restrictions.eq("custNo",new Integer(accNo).toString()));
		}
		if(name!=null && name.length()>2) {
			accountCriteria.add(Restrictions.ilike("accountName", name, MatchMode.ANYWHERE));
		}
		
		//must be top level
		accountCriteria.add(Restrictions.in("accountCategory", new String[]{NonConfigurableConstants.ACCOUNT_CATEGORY_APPLICANT, NonConfigurableConstants.ACCOUNT_CATEGORY_CORPORATE}));


		logger.info("***********This is the criteria"+accountCriteria.toString());

		List<AmtbAccount> results = this.findAllByCriteria(accountCriteria);
		if(results.isEmpty()) {
			return null;
		} else {
			return  results;
		}
	}
	@SuppressWarnings("unchecked")
	public AmtbAccount getAllAccountByCustNo(String custNo){

		DetachedCriteria accountCriteria = DetachedCriteria.forClass(AmtbAccount.class);
		DetachedCriteria childAccountCriteria = accountCriteria.createCriteria("amtbAccounts", Criteria.LEFT_JOIN); 
		childAccountCriteria.createCriteria("amtbAccounts", Criteria.LEFT_JOIN); 
		accountCriteria.add(Restrictions.eq("custNo",custNo));

		List<AmtbAccount> results = this.findAllByCriteria(accountCriteria);
		if(results.isEmpty()) {
			return null;
		} else {
			return  results.get(0);
		}
	}

	@SuppressWarnings("unchecked")
	public AmtbAccount getAccountByCustNo(String custNo){

		logger.info("Retriving data by Cust No "+custNo);
		DetachedCriteria accountCriteria = DetachedCriteria.forClass(AmtbAccount.class);
		//DetachedCriteria childAccountCriteria = accountCriteria.createCriteria("amtbAccounts", Criteria.LEFT_JOIN); //join division
		//childAccountCriteria.createCriteria("amtbAccounts", Criteria.LEFT_JOIN); //join department

		accountCriteria.add(Restrictions.eq("custNo",custNo));

		List<AmtbAccount> results = this.findAllByCriteria(accountCriteria);
		if(results.isEmpty()) {
			return null;
		} else {
			return  results.get(0);
		}
	}
	
	@SuppressWarnings("unchecked")
	public List<AmtbAccount> getAccountsByCustNo_Bk(ProductSearchCriteria productSearchCriteria){

		DetachedCriteria accountCriteria = DetachedCriteria.forClass(AmtbAccount.class);
		//DetachedCriteria childAccountCriteria = accountCriteria.createCriteria("amtbAccounts", Criteria.LEFT_JOIN); //join division
		//childAccountCriteria.createCriteria("amtbAccounts", Criteria.LEFT_JOIN); //join department
		if(productSearchCriteria.getCustNo()!=null && productSearchCriteria.getCustNo().trim().length()>0){
			accountCriteria.add(Restrictions.eq("custNo",productSearchCriteria.getCustNo()));
			//accountCriteria.add(Restrictions.or(Restrictions.ilike("custNo",productSearchCriteria.getCustNo(), MatchMode.ANYWHERE),Restrictions.eq("custNo",new Integer(productSearchCriteria.getCustNo()).toString())));
			//accountCriteria.add(Restrictions.like("custNo", productSearchCriteria.getCustNo()));
		}
		if(productSearchCriteria.getAccName()!=null && productSearchCriteria.getAccName().trim().length()>0)
			accountCriteria.add(Restrictions.ilike("accountName", productSearchCriteria.getAccName(),MatchMode.ANYWHERE));
		List<AmtbAccount> results = this.findAllByCriteria(accountCriteria);
		if(results!=null)
			logger.info("Result size "+results.size());
		logger.info("accountCriteria"+accountCriteria.toString());
		if(results.isEmpty()) {
			return null;
		} else {
			return  results;
		}
	}
	
	@SuppressWarnings("unchecked")
	public AmtbAccount getAccountsByCustNo(ProductSearchCriteria productSearchCriteria){

		DetachedCriteria accountCriteria = DetachedCriteria.forClass(AmtbAccount.class);
		//DetachedCriteria childAccountCriteria = accountCriteria.createCriteria("amtbAccounts", Criteria.LEFT_JOIN); //join division
		//childAccountCriteria.createCriteria("amtbAccounts", Criteria.LEFT_JOIN); //join department
		if(productSearchCriteria.getCustNo()!=null && productSearchCriteria.getCustNo().trim().length()>0){
			accountCriteria.add(Restrictions.eq("custNo",productSearchCriteria.getCustNo()));
			//accountCriteria.add(Restrictions.or(Restrictions.ilike("custNo",productSearchCriteria.getCustNo(), MatchMode.ANYWHERE),Restrictions.eq("custNo",new Integer(productSearchCriteria.getCustNo()).toString())));
			//accountCriteria.add(Restrictions.like("custNo", productSearchCriteria.getCustNo()));
		}
		//if(productSearchCriteria.getAccName()!=null && productSearchCriteria.getAccName().trim().length()>0)
			//accountCriteria.add(Restrictions.ilike("accountName", productSearchCriteria.getAccName(),MatchMode.ANYWHERE));
		List<AmtbAccount> results =this.findAllByCriteria(accountCriteria);
		if(results!=null)
			logger.info("Result size "+results.size());
		logger.info("accountCriteria"+accountCriteria.toString());
		if(results.isEmpty()) {
			return null;
		} else {
			return  results.get(0);
		}
	}

	@SuppressWarnings("unchecked")
	public List<AmtbBillReq> getPendingBillingRequests(){
		DetachedCriteria requestCriteria = DetachedCriteria.forClass(AmtbBillReq.class);
		DetachedCriteria flowCriteria = requestCriteria.createCriteria("amtbBillReqFlows", DetachedCriteria.LEFT_JOIN);
		flowCriteria.createCriteria("satbUser", DetachedCriteria.INNER_JOIN);
		flowCriteria.addOrder(Order.asc("flowDt"));
		requestCriteria.createCriteria("amtbAccount", DetachedCriteria.INNER_JOIN);
		requestCriteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		List<AmtbBillReq> requests = findAllByCriteria(requestCriteria);
		List<AmtbBillReq> returnList = new ArrayList<AmtbBillReq>();
		for(AmtbBillReq request : requests){
			boolean pending = true;
			for(AmtbBillReqFlow flow : request.getAmtbBillReqFlows()){
				if(!flow.getToStatus().equals(NonConfigurableConstants.BILLING_REQUEST_STATUS_PENDING)){
					pending = false;
					break;
				}
			}
			if(pending){
				returnList.add(request);
			}
		}
		return returnList;
	}
	@SuppressWarnings("unchecked")
	public AmtbBillReq getBillingRequest(Integer requestId){
		DetachedCriteria requestCriteria = DetachedCriteria.forClass(AmtbBillReq.class);
		requestCriteria.add(Restrictions.eq("billReqNo", requestId));
		requestCriteria.createCriteria("amtbBillReqFlows", DetachedCriteria.LEFT_JOIN).createCriteria("satbUser", DetachedCriteria.LEFT_JOIN);
		requestCriteria.createCriteria("amtbAcctBillCycleReqs", DetachedCriteria.LEFT_JOIN);
		requestCriteria.createCriteria("amtbAcctVolDiscReqs", DetachedCriteria.LEFT_JOIN);
		requestCriteria.createCriteria("amtbAcctCredTermReqs", DetachedCriteria.LEFT_JOIN);
		requestCriteria.createCriteria("amtbAcctEarlyPymtReqs", DetachedCriteria.LEFT_JOIN);
		requestCriteria.createCriteria("amtbAcctLatePymtReqs", DetachedCriteria.LEFT_JOIN);
		requestCriteria.createCriteria("amtbAcctPromotionReqs", DetachedCriteria.LEFT_JOIN).createCriteria("amtbAcctPromotion", DetachedCriteria.LEFT_JOIN);
		DetachedCriteria acctCriteria = requestCriteria.createCriteria("amtbAccount", DetachedCriteria.INNER_JOIN);
		acctCriteria.createCriteria("amtbAcctBillCycles", DetachedCriteria.LEFT_JOIN);
		acctCriteria.createCriteria("amtbAcctVolDiscs", DetachedCriteria.LEFT_JOIN).createCriteria("mstbVolDiscMaster", DetachedCriteria.LEFT_JOIN);
		acctCriteria.createCriteria("amtbAcctAdminFees", DetachedCriteria.LEFT_JOIN).createCriteria("mstbAdminFeeMaster", DetachedCriteria.LEFT_JOIN);
		acctCriteria.createCriteria("amtbAcctCredTerms", DetachedCriteria.LEFT_JOIN).createCriteria("mstbCreditTermMaster", DetachedCriteria.LEFT_JOIN);
		acctCriteria.createCriteria("amtbAcctEarlyPymts", DetachedCriteria.LEFT_JOIN).createCriteria("mstbEarlyPaymentMaster", DetachedCriteria.LEFT_JOIN);
		acctCriteria.createCriteria("amtbAcctLatePymts", DetachedCriteria.LEFT_JOIN).createCriteria("mstbLatePaymentMaster", DetachedCriteria.LEFT_JOIN);
		DetachedCriteria  acctPromotionCriteria = acctCriteria.createCriteria("amtbAcctPromotions", DetachedCriteria.LEFT_JOIN);
		DetachedCriteria promotionCriteria = acctPromotionCriteria.createCriteria("mstbPromotion", DetachedCriteria.LEFT_JOIN);
		promotionCriteria.createCriteria("currentPromoDetail", DetachedCriteria.LEFT_JOIN);
		
		requestCriteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		List<AmtbBillReq> requests = findAllByCriteria(requestCriteria);
		return requests.isEmpty() ? null : requests.get(0);
	}
	@SuppressWarnings("unchecked")
	public List<AmtbCredRevReq> getPendingCreditReviewRequests(){
		DetachedCriteria requestCriteria = DetachedCriteria.forClass(AmtbCredRevReq.class);
		DetachedCriteria flowCriteria = requestCriteria.createCriteria("amtbCredRevReqFlows", DetachedCriteria.LEFT_JOIN);
		flowCriteria.createCriteria("satbUser", DetachedCriteria.INNER_JOIN);
		flowCriteria.addOrder(Order.asc("flowDt"));
		requestCriteria.createCriteria("amtbAccount", DetachedCriteria.INNER_JOIN);
		requestCriteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		List<AmtbCredRevReq> requests = findAllByCriteria(requestCriteria);
		List<AmtbCredRevReq> returnList = new ArrayList<AmtbCredRevReq>();
		for(AmtbCredRevReq request : requests){
			boolean pending = true;
			for(AmtbCredRevReqFlow flow : request.getAmtbCredRevReqFlows()){
				if(!flow.getToStatus().equals(NonConfigurableConstants.CREDIT_REVIEW_REQUEST_STATUS_PENDING)){
					pending = false;
					break;
				}
			}
			if(pending){
				returnList.add(request);
			}
		}
		return returnList;
	}
	@SuppressWarnings("unchecked")
	public AmtbCredRevReq getPendingCreditReviewRequest(Integer requestId){
		DetachedCriteria requestCriteria = DetachedCriteria.forClass(AmtbCredRevReq.class);
		requestCriteria.add(Restrictions.eq("creditReviewRequestNo", requestId));
		requestCriteria.createCriteria("amtbCredRevReqFlows", DetachedCriteria.LEFT_JOIN)
		.createCriteria("satbUser", DetachedCriteria.INNER_JOIN);
		requestCriteria.createCriteria("amtbAccount", DetachedCriteria.LEFT_JOIN)
		.createCriteria("amtbAccount", DetachedCriteria.LEFT_JOIN);
		requestCriteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		List<AmtbCredRevReq> requests = findAllByCriteria(requestCriteria);
		return requests.isEmpty() ? null : requests.get(0);
	}
	@SuppressWarnings("unchecked")
	public List<AmtbAcctCredLimit> getCreditLimits(Integer accountNo){
		DetachedCriteria creditCriteria = DetachedCriteria.forClass(AmtbAcctCredLimit.class);
		creditCriteria.createCriteria("amtbAccount", DetachedCriteria.LEFT_JOIN)
		.add(Restrictions.eq("accountNo", accountNo));
		return this.findAllByCriteria(creditCriteria);
	}
	
	@SuppressWarnings("unchecked")
	public List<AmtbAcctCredLimit> getChildCreditLimits(Integer accountNo){
		DetachedCriteria creditCriteria = DetachedCriteria.forClass(AmtbAcctCredLimit.class);
		DetachedCriteria acctCriteria = creditCriteria.createCriteria("amtbAccount", DetachedCriteria.LEFT_JOIN);
		acctCriteria.createCriteria("amtbAccount", DetachedCriteria.LEFT_JOIN).add(Restrictions.eq("accountNo", accountNo));
		return this.findAllByCriteria(creditCriteria);
	}
	@SuppressWarnings("unchecked")
	public List<AmtbAcctCredLimit> getChildChildCreditLimits(Integer accountNo){
		DetachedCriteria creditCriteria = DetachedCriteria.forClass(AmtbAcctCredLimit.class);
		DetachedCriteria acctCriteria = creditCriteria.createCriteria("amtbAccount", DetachedCriteria.LEFT_JOIN);
		DetachedCriteria acctacctCriteria = acctCriteria.createCriteria("amtbAccount", DetachedCriteria.LEFT_JOIN);
		acctacctCriteria.createCriteria("amtbAccount", DetachedCriteria.LEFT_JOIN).add(Restrictions.eq("accountNo", accountNo));
		return this.findAllByCriteria(creditCriteria);
	}
	@SuppressWarnings("unchecked")
	public BigDecimal getActivePermCreditLimit(Integer accountNo){
		DetachedCriteria creditCriteria = DetachedCriteria.forClass(AmtbAcctCredLimit.class);
		creditCriteria.createCriteria("amtbAccount", DetachedCriteria.LEFT_JOIN).add(Restrictions.eq("accountNo", accountNo));
		creditCriteria.add(Restrictions.eq("creditLimitType", "P"));
		creditCriteria.add(Restrictions.le("effectiveDtFrom", new Date()));

		creditCriteria.addOrder(Order.desc("effectiveDtFrom"));
		
		List<AmtbAcctCredLimit> requests = findAllByCriteria(creditCriteria);
		if(!requests.isEmpty() && requests != null)
			return requests.get(0).getNewCreditLimit();
		else
			return null;
	}
	@SuppressWarnings("unchecked")
	public List<AmtbAcctCredTerm> getCreditTerms(Integer accountNo){
		DetachedCriteria creditCriteria = DetachedCriteria.forClass(AmtbAcctCredTerm.class);
		creditCriteria.createCriteria("mstbCreditTermMaster", DetachedCriteria.LEFT_JOIN);
		creditCriteria.createCriteria("amtbAccount", DetachedCriteria.LEFT_JOIN)
		.add(Restrictions.eq("accountNo", accountNo));
		return this.findAllByCriteria(creditCriteria);
	}
	public List<AmtbAcctCredLimit> getListCreditLimits(Integer accountNo) {
		DetachedCriteria creditCriteria = DetachedCriteria.forClass(AmtbAcctCredLimit.class);
		creditCriteria.createCriteria("amtbAccount", DetachedCriteria.LEFT_JOIN).add(Restrictions.eq("accountNo", accountNo));	
		creditCriteria.addOrder(Order.desc("effectiveDtFrom"));
		
		return this.findAllByCriteria(creditCriteria);
	}
	public AmtbAcctCredLimit getNearestCreditLimits(Integer accountNo, Date toDate) {
		DetachedCriteria creditCriteria = DetachedCriteria.forClass(AmtbAcctCredLimit.class);
		creditCriteria.createCriteria("amtbAccount", DetachedCriteria.LEFT_JOIN).add(Restrictions.eq("accountNo", accountNo));
		creditCriteria.add(Restrictions.eq("creditLimitType", "P"));
		creditCriteria.add(Restrictions.le("effectiveDtFrom", toDate));
		
		creditCriteria.addOrder(Order.desc("effectiveDtFrom"));
		
		List<AmtbAcctCredLimit> requests = findAllByCriteria(creditCriteria);
		return requests.isEmpty() ? null : requests.get(0);
	}
	
	
	@SuppressWarnings("unchecked")
	public List<AmtbAccount> getAccountsBySalesperson(Integer salespersonNo, String industryCode){
		DetachedCriteria acctCriteria = DetachedCriteria.forClass(AmtbAccount.class);
		if(industryCode!=null && industryCode.length()!=0){
			MstbMasterTable industry = ConfigurableConstants.getMasterTable(ConfigurableConstants.INDUSTRY_MASTER_CODE, industryCode);
			acctCriteria.createCriteria("amtbCorporateDetails", DetachedCriteria.LEFT_JOIN)
			.add(Restrictions.or(Restrictions.isNull("mstbMasterTableByIndustry"), Restrictions.eq("mstbMasterTableByIndustry", industry)));
			acctCriteria.createCriteria("amtbPersonalDetails", DetachedCriteria.LEFT_JOIN)
			.add(Restrictions.or(Restrictions.isNull("mstbMasterTableByIndustry"), Restrictions.eq("mstbMasterTableByIndustry", industry)));

		}
		acctCriteria.createCriteria("amtbAcctSalespersons", DetachedCriteria.LEFT_JOIN)
		.add(Restrictions.isNull("effectiveDtTo"))
		.createCriteria("mstbSalesperson", DetachedCriteria.LEFT_JOIN)
		.add(Restrictions.eq("salespersonNo", salespersonNo));
		return this.findAllByCriteria(acctCriteria);
	}
	public boolean hasFutureTransferAcct(Collection<Integer> accountNos){
		DetachedCriteria acctSalespersonCrtieria = DetachedCriteria.forClass(AmtbAcctSalesperson.class);
		acctSalespersonCrtieria.add(Restrictions.gt("effectiveDtFrom", DateUtil.getCurrentTimestamp()));
		acctSalespersonCrtieria.createCriteria("amtbAccount", DetachedCriteria.FULL_JOIN)
		.add(Restrictions.in("accountNo", accountNos));
		return !this.findAllByCriteria(acctSalespersonCrtieria).isEmpty();
	}
	@SuppressWarnings("unchecked")
	public void transferAcct(Integer fromSalespersonNo, Integer toSalespersonNo, Date effectiveDate, Collection<Integer> accountNos, SatbUser user){
		AmtbAcctSalespersonReq request = new AmtbAcctSalespersonReq();
		
		//Selected date does not have the current time therefore need to pump in the current time
		Calendar selectedDateCalendar = Calendar.getInstance();
		selectedDateCalendar.setTime(effectiveDate);
		Calendar currentDateTimeCalendar = Calendar.getInstance();
		selectedDateCalendar.set(Calendar.HOUR, currentDateTimeCalendar.get(Calendar.HOUR));
		selectedDateCalendar.set(Calendar.MINUTE, currentDateTimeCalendar.get(Calendar.MINUTE));
		selectedDateCalendar.set(Calendar.SECOND, currentDateTimeCalendar.get(Calendar.SECOND));
		selectedDateCalendar.set(Calendar.MILLISECOND, currentDateTimeCalendar.get(Calendar.MILLISECOND));
		selectedDateCalendar.set(Calendar.AM_PM, currentDateTimeCalendar.get(Calendar.AM_PM));
		request.setEffectiveDt(new Timestamp(selectedDateCalendar.getTimeInMillis()));
		
		request.setRequestDt(DateUtil.getCurrentTimestamp());
		DetachedCriteria acctCriteria = DetachedCriteria.forClass(AmtbAccount.class)
		.add(Restrictions.in("accountNo", accountNos))
		.createCriteria("amtbAcctSalespersons", DetachedCriteria.LEFT_JOIN)
		.add(Restrictions.isNull("effectiveDtTo"));
		List<AmtbAccount> accounts = this.findAllByCriteria(acctCriteria);
		MstbSalesperson fromSalesperson = (MstbSalesperson)this.get(MstbSalesperson.class, fromSalespersonNo);
		MstbSalesperson toSalesperson = (MstbSalesperson)this.get(MstbSalesperson.class, toSalespersonNo);
		request.setMstbSalespersonByFromSalespersonNo(fromSalesperson);
		request.setMstbSalespersonByToSalespersonNo(toSalesperson);
		request.setSatbUser(user);
		ArrayList<AmtbAcctSalesperson> updateSalespersons = new ArrayList<AmtbAcctSalesperson>();
		for(AmtbAccount account : accounts){
			AmtbAcctSalesperson currentSalesperson = account.getAmtbAcctSalespersons().iterator().next();
			currentSalesperson.setEffectiveDtTo(new Timestamp(selectedDateCalendar.getTimeInMillis()));
			updateSalespersons.add(currentSalesperson);
			AmtbAcctSalesperson newSalesperson = new AmtbAcctSalesperson();
			newSalesperson.setAmtbAccount(account);
			newSalesperson.setAmtbAcctSalespersonReq(request);
			newSalesperson.setEffectiveDtFrom(new Timestamp(selectedDateCalendar.getTimeInMillis()));
			newSalesperson.setMstbSalesperson(toSalesperson);
			request.getAmtbAcctSalespersons().add(newSalesperson);
		}
		this.saveOrUpdateAll(updateSalespersons);
		this.save(request);
	}
	@SuppressWarnings("unchecked")
	public List<AmtbAcctSalespersonReq> getAllTransferAcctReqs(){
		DetachedCriteria requestCriteria = DetachedCriteria.forClass(AmtbAcctSalespersonReq.class);
		requestCriteria.createCriteria("mstbSalespersonByFromSalespersonNo", DetachedCriteria.LEFT_JOIN);
		requestCriteria.createCriteria("mstbSalespersonByToSalespersonNo", DetachedCriteria.LEFT_JOIN);
		requestCriteria.createCriteria("amtbAcctSalespersons", DetachedCriteria.LEFT_JOIN);
		requestCriteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		return this.findAllByCriteria(requestCriteria);
	}
	@SuppressWarnings("unchecked")
	public AmtbAcctSalespersonReq getTransferAcctReq(Integer requestNo){
		DetachedCriteria requestCriteria = DetachedCriteria.forClass(AmtbAcctSalespersonReq.class)
		.add(Restrictions.eq("acctSalespersonReqNo", requestNo));
		requestCriteria.createCriteria("mstbSalespersonByFromSalespersonNo", DetachedCriteria.LEFT_JOIN);
		requestCriteria.createCriteria("mstbSalespersonByToSalespersonNo", DetachedCriteria.LEFT_JOIN);
		requestCriteria.createCriteria("amtbAcctSalespersons", DetachedCriteria.LEFT_JOIN)
		.createCriteria("amtbAccount", DetachedCriteria.LEFT_JOIN);
		requestCriteria.createCriteria("satbUser", DetachedCriteria.LEFT_JOIN);
		requestCriteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		List<AmtbAcctSalespersonReq> requests = this.findAllByCriteria(requestCriteria);
		return requests.isEmpty() ? null : requests.get(0);
	}
	@SuppressWarnings("unchecked")
	public void deleteTransferAcctReq(Integer requestNo){
		DetachedCriteria requestCriteria = DetachedCriteria.forClass(AmtbAcctSalespersonReq.class)
		.add(Restrictions.eq("acctSalespersonReqNo", requestNo));
		requestCriteria.createCriteria("amtbAcctSalespersons", DetachedCriteria.LEFT_JOIN)
		.createCriteria("amtbAccount", DetachedCriteria.LEFT_JOIN);
		requestCriteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		List<AmtbAcctSalespersonReq> requests = this.findAllByCriteria(requestCriteria);
		if(!requests.isEmpty()){
			AmtbAcctSalespersonReq request = requests.get(0);
			List<AmtbAccount> accounts = new ArrayList<AmtbAccount>();
			for(AmtbAcctSalesperson salesperson : request.getAmtbAcctSalespersons()){
				accounts.add(salesperson.getAmtbAccount());
			}
			DetachedCriteria prevSalesCriteria = DetachedCriteria.forClass(AmtbAcctSalesperson.class)
			.add(Restrictions.in("amtbAccount", accounts))
			.add(Restrictions.eq("effectiveDtTo", request.getEffectiveDt()));
			List<AmtbAcctSalesperson> prevSales = this.findAllByCriteria(prevSalesCriteria);
			for(AmtbAcctSalesperson prevSale : prevSales){
				prevSale.setEffectiveDtTo(null);
				this.update(prevSale);
			}
			this.delete(request);
		}
	}
	@SuppressWarnings("unchecked")
	public List<AmtbAcctStatus> getAccountStatus(Integer accountNo){
		DetachedCriteria statusCriteria = DetachedCriteria.forClass(AmtbAcctStatus.class);
		statusCriteria.createCriteria("amtbAccount", DetachedCriteria.LEFT_JOIN)
		.add(Restrictions.eq("accountNo", accountNo));
		statusCriteria.createCriteria("satbUser", DetachedCriteria.LEFT_JOIN);
		statusCriteria.createCriteria("mstbMasterTable", DetachedCriteria.LEFT_JOIN);
		statusCriteria.addOrder(Order.asc("effectiveDt"));
		return this.findAllByCriteria(statusCriteria);
	}
	@SuppressWarnings("unchecked")
	public List<AmtbBillReq> getBillingRequest(String custNo){
		DetachedCriteria requestCriteria = DetachedCriteria.forClass(AmtbBillReq.class);
		requestCriteria.createCriteria("amtbAccount", DetachedCriteria.LEFT_JOIN)
		.add(Restrictions.eq("custNo", custNo));
		requestCriteria.createCriteria("amtbBillReqFlows", DetachedCriteria.LEFT_JOIN)
		.createCriteria("satbUser", DetachedCriteria.LEFT_JOIN);
		return this.findAllByCriteria(requestCriteria);
	}
	@SuppressWarnings("unchecked")
	public List<AmtbBillReq> getBillingRequest(String custNo, String acctName, Date from, Date to, String status, String requester){
		DetachedCriteria requestCriteria = DetachedCriteria.forClass(AmtbBillReq.class);
		if(from!=null){
			requestCriteria.add(Restrictions.gt("requestDt", from));
		}
		if(to!=null){
			Calendar toCalendar = Calendar.getInstance();
			toCalendar.setTimeInMillis(to.getTime());
			requestCriteria.add(Restrictions.lt("requestDt", DateUtil.convertTo2359Hours(toCalendar)));
		}
		DetachedCriteria acctCriteria = requestCriteria.createCriteria("amtbAccount", DetachedCriteria.LEFT_JOIN);
		if(custNo!=null && custNo.length()!=0){
			acctCriteria.add(Restrictions.ilike("custNo", custNo, MatchMode.ANYWHERE));
		}
		if(acctName!=null && acctName.length()!=0){
			acctCriteria.add(Restrictions.ilike("accountName", acctName, MatchMode.ANYWHERE));
		}
		requestCriteria.createCriteria("amtbBillReqFlows", DetachedCriteria.LEFT_JOIN)
		.createCriteria("satbUser", DetachedCriteria.LEFT_JOIN);
		requestCriteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		List<AmtbBillReq> returnList;
		List<AmtbBillReq> requests = this.findAllByCriteria(requestCriteria);
		if((status!=null && status.length()!=0) || (requester!=null && requester.length()!=0)){
			returnList = new ArrayList<AmtbBillReq>();
			for(AmtbBillReq request : requests){
				TreeSet<AmtbBillReqFlow> sortedFlows = new TreeSet<AmtbBillReqFlow>(new Comparator<AmtbBillReqFlow>(){
					public int compare(AmtbBillReqFlow o1, AmtbBillReqFlow o2) {
						return o1.getFlowDt().compareTo(o2.getFlowDt());
					}
				});
				sortedFlows.addAll(request.getAmtbBillReqFlows());
				if(status!=null && status.length()!=0 && requester!=null && requester.length()!=0){
					if(sortedFlows.last().getToStatus().equals(status) && sortedFlows.first().getSatbUser().getName().toUpperCase().indexOf(requester.toUpperCase())!=-1){
						returnList.add(request);
					}
				}else if(requester!=null && requester.length()!=0){
					if(sortedFlows.first().getSatbUser().getName().toUpperCase().indexOf(requester.toUpperCase())!=-1){
						returnList.add(request);
					}
				}else if(status!=null && status.length()!=0){
					if(sortedFlows.last().getToStatus().equals(status)){
						returnList.add(request);
					}
				}
			}
		}else{
			returnList = requests;
		}
		return returnList;
	}
	@SuppressWarnings("unchecked")
	public List<AmtbCredRevReq> getCreditReviewRequest(String custNo, String acctName, Date from, Date to, String status, String requester){
		DetachedCriteria requestCriteria = DetachedCriteria.forClass(AmtbCredRevReq.class);
		DetachedCriteria acctCriteria = requestCriteria.createCriteria("amtbAccount", DetachedCriteria.LEFT_JOIN);
		if(custNo!=null && custNo.length()!=0){
			acctCriteria.add(Restrictions.ilike("custNo", custNo, MatchMode.ANYWHERE));
		}
		if(acctName!=null && acctName.length()!=0){
			acctCriteria.add(Restrictions.ilike("accountName", acctName, MatchMode.ANYWHERE));
		}
		requestCriteria.createCriteria("amtbCredRevReqFlows", DetachedCriteria.LEFT_JOIN)
		.createCriteria("satbUser", DetachedCriteria.LEFT_JOIN);
		requestCriteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		List<AmtbCredRevReq> returnList = new ArrayList<AmtbCredRevReq>();
		List<AmtbCredRevReq> requests = this.findAllByCriteria(requestCriteria);
		for(AmtbCredRevReq request : requests){
			TreeSet<AmtbCredRevReqFlow> sortedFlows = new TreeSet<AmtbCredRevReqFlow>(new Comparator<AmtbCredRevReqFlow>(){
				public int compare(AmtbCredRevReqFlow o1, AmtbCredRevReqFlow o2) {
					return o1.getFlowDt().compareTo(o2.getFlowDt());
				}
			});
			sortedFlows.addAll(request.getAmtbCredRevReqFlows());
			if(from!=null){ // start date is input
				if(to!=null){ // both start and end date are input
					if(sortedFlows.first().getFlowDt().before(from) || sortedFlows.first().getFlowDt().after(DateUtil.convertDateTo2359Hours(to))){
						continue;
					}
				}else{//only start date is input
					if(sortedFlows.first().getFlowDt().before(from) || sortedFlows.first().getFlowDt().after(DateUtil.convertDateTo2359Hours(from))){
						continue;
					}
				}
			}else if(to!=null){// only end date is input
				if(sortedFlows.first().getFlowDt().before(DateUtil.convertDateTo2359Hours(to)) || sortedFlows.first().getFlowDt().after(DateUtil.convertDateTo2359Hours(to))){
					continue;
				}
			}
			if(status!=null && status.length()!=0){
				if(!sortedFlows.last().getToStatus().equals(status)){
					continue;
				}
			}
			if(requester!=null && requester.length()!=0){
				if(!sortedFlows.first().getSatbUser().getName().toLowerCase().equals(requester.toLowerCase())){
					continue;
				}
			}
			returnList.add(request);
		}
		return returnList;
	}
	@SuppressWarnings("unchecked")
	public AmtbCredRevReq getCreditReviewRequest(Integer requestNo){
		DetachedCriteria requestCriteria = DetachedCriteria.forClass(AmtbCredRevReq.class);
		requestCriteria.add(Restrictions.eq("creditReviewRequestNo", requestNo));
		requestCriteria.createCriteria("amtbCredRevReqFlows", DetachedCriteria.LEFT_JOIN)
		.createCriteria("satbUser", DetachedCriteria.LEFT_JOIN);
		requestCriteria.createCriteria("amtbAccount", DetachedCriteria.LEFT_JOIN)
		.createCriteria("amtbAcctCredLimits", DetachedCriteria.LEFT_JOIN)
		.add(Restrictions.eq("creditLimitType", NonConfigurableConstants.CREDIT_LIMIT_PERMANENT));
		requestCriteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		List<AmtbCredRevReq> requests = this.findAllByCriteria(requestCriteria);
		return requests.isEmpty() ? null : requests.get(0);
	}
	@SuppressWarnings("unchecked")
	public List<AmtbSubscTo> getProductSubscriptions(AmtbAccount acct){
		DetachedCriteria subscribeCriteria = DetachedCriteria.forClass(AmtbSubscTo.class)
		.add(Restrictions.eq("amtbAccount", acct));
		subscribeCriteria.createCriteria("comp_id.pmtbProductType", DetachedCriteria.LEFT_JOIN);
		subscribeCriteria.createCriteria("mstbProdDiscMaster", DetachedCriteria.LEFT_JOIN);
		subscribeCriteria.createCriteria("lrtbRewardMaster", DetachedCriteria.LEFT_JOIN);
		subscribeCriteria.createCriteria("mstbSubscFeeMaster", DetachedCriteria.LEFT_JOIN);
		subscribeCriteria.createCriteria("mstbIssuanceFeeMaster", DetachedCriteria.LEFT_JOIN);
		return this.findAllByCriteria(subscribeCriteria);
	}

	@SuppressWarnings("unchecked")
	public List<AmtbAccount> getAccounts(Collection accountNos){
		DetachedCriteria criteria = DetachedCriteria.forClass(AmtbAccount.class);
		criteria.add(Restrictions.in("accountNo", accountNos));

		return this.findAllByCriteria(criteria);
	}

	public boolean hasPermCreditLimit(Integer acctNo, Date effectiveDate){
		DetachedCriteria accountCriteria = DetachedCriteria.forClass(AmtbAccount.class)
		.add(Restrictions.eq("accountNo", acctNo))
		.createCriteria("amtbAcctCredLimits", DetachedCriteria.INNER_JOIN)
		.add(Restrictions.eq("effectiveDtFrom", effectiveDate));
		return !this.findAllByCriteria(accountCriteria).isEmpty();
	}
	@SuppressWarnings("unchecked")
	public AmtbAccount getAccountMainContacts(String custNo){
		DetachedCriteria acctCriteria = DetachedCriteria.forClass(AmtbAccount.class)
		.add(Restrictions.eq("custNo", custNo));
		acctCriteria.createCriteria("amtbAcctMainContacts", DetachedCriteria.LEFT_JOIN)
		.createCriteria("amtbContactPerson", DetachedCriteria.LEFT_JOIN);
		acctCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		List<AmtbAccount> accts = this.findAllByCriteria(acctCriteria);
		return accts.isEmpty() ? null : accts.get(0);
	}

	@SuppressWarnings("unchecked")
	public AmtbAcctStatus getAccountLatestStatus(String accountNo){
		DetachedCriteria statusCriteria = DetachedCriteria.forClass(AmtbAcctStatus.class);
		statusCriteria.createCriteria("amtbAccount", DetachedCriteria.LEFT_JOIN)
		.add(Restrictions.eq("custNo", accountNo));
		statusCriteria.add(Restrictions.le("effectiveDt", DateUtil.getCurrentDate()));
		statusCriteria.addOrder(Order.desc("effectiveDt"));

		List<AmtbAcctStatus> results = this.findMaxResultByCriteria(statusCriteria, MAX_ROW);
		if(results.isEmpty()) {
			return null;
		} else {
			return  results.get(0);
		}
	}
	@SuppressWarnings("unchecked")
	public AmtbCorporateDetail getCorporateDetail(String custNo){
		DetachedCriteria corpCriteria = DetachedCriteria.forClass(AmtbCorporateDetail.class);
		corpCriteria.createCriteria("amtbAccount", DetachedCriteria.LEFT_JOIN)
		.add(Restrictions.eq("custNo", custNo));
		List<AmtbCorporateDetail> details = this.findAllByCriteria(corpCriteria);
		return details.isEmpty() ? null : details.get(0);
	}

	@SuppressWarnings("unchecked")
	public List<AmtbAccount> getAccounts(SearchByAccountForm form) {
		DetachedCriteria accountCriteria = DetachedCriteria.forClass(AmtbAccount.class);
		// retrieve parent and grandparent also, where applicable
		accountCriteria.createCriteria("amtbAccount", DetachedCriteria.LEFT_JOIN)
		.createCriteria("amtbAccount", DetachedCriteria.LEFT_JOIN);
		//DetachedCriteria statusCriteria = accountCriteria.createCriteria("amtbAcctStatuses", DetachedCriteria.LEFT_JOIN);

		// Criteria for account
		if(form.getDepartment() != null) {
			accountCriteria.add(Restrictions.idEq(form.getDepartment().getAccountNo()));
		} else if(form.getDivision() != null) {
			accountCriteria.add(Restrictions.idEq(form.getDivision().getAccountNo()));
		} else if(form.getAccount() != null) {
			accountCriteria.add(Restrictions.idEq(form.getAccount().getAccountNo()));
		} else {
			if (!StringUtil.isBlank(form.getCustomerNo())) {
				accountCriteria.add(Restrictions.eq("custNo", form.getCustomerNo()));
			}

			if (!StringUtil.isBlank(form.getAccountName())) {
				accountCriteria.add(Restrictions.eq("accountName", form.getAccountName()));
			}
		}

		if (form.isBillable()) {
			accountCriteria.add(Restrictions.isNotNull("invoiceFormat"));
		}

		//statusCriteria.add(Restrictions.eq("acctStatus", NonConfigurableConstants.ACCOUNT_STATUS_ACTIVE));
		accountCriteria.addOrder(Order.asc("accountNo"));

		return findDefaultMaxResultByCriteria(accountCriteria);
	}

	@SuppressWarnings("unchecked")
	public AmtbContactPerson getMainContactByType(Integer accountNo,
			String contactType) {
		DetachedCriteria contactCriteria = DetachedCriteria.forClass(AmtbContactPerson.class);
		DetachedCriteria mainContactCriteria = contactCriteria.createCriteria("amtbAcctMainContacts");
		contactCriteria.createCriteria("mstbMasterTableByAddressCountry", DetachedCriteria.LEFT_JOIN);
		contactCriteria.createCriteria("mstbMasterTableByMainContactSal", DetachedCriteria.LEFT_JOIN);
		contactCriteria.createCriteria("mstbMasterTableBySubContactSal", DetachedCriteria.LEFT_JOIN);
		contactCriteria.createCriteria("mstbMasterTableByMainContactRace", DetachedCriteria.LEFT_JOIN);
		contactCriteria.createCriteria("mstbMasterTableBySubContactRace", DetachedCriteria.LEFT_JOIN);
		
		mainContactCriteria.add(Restrictions.eq("id.mainContactType", contactType));
		mainContactCriteria.add(Restrictions.eq("id.amtbAccount.accountNo", accountNo));

		List list = findDefaultMaxResultByCriteria(contactCriteria);
		if (list.size() > 0) {
			return (AmtbContactPerson) list.get(0);
		}
		return null;
	}

	/**
	 * Copied from LatePaymentDaoHibernate.java from bill gen module
	 */
	public BigDecimal getLatestLatePaymentRate(AmtbAccount account, Date invoiceDate) {
		//Get Latest Credit Term Record
		Criteria latePaymentCriteria = getSession().createCriteria(AmtbAcctLatePymt.class);
		Criteria accountCriteria = latePaymentCriteria.createCriteria("amtbAccount", Criteria.INNER_JOIN);

		//Find corporate account no if billing account is division or department
		//Only corporate holds the late payment rate
		Integer accountNo = new Integer(0);
		if(account.getAccountCategory().equals(NonConfigurableConstants.ACCOUNT_CATEGORY_DEPARTMENT)){
			accountNo = account.getAmtbAccount().getAmtbAccount().getAccountNo();
		}
		else if(account.getAccountCategory().equals(NonConfigurableConstants.ACCOUNT_CATEGORY_DIVISION)){
			accountNo = account.getAmtbAccount().getAccountNo();
		}
		else if(account.getAccountCategory().equals(NonConfigurableConstants.ACCOUNT_CATEGORY_CORPORATE)){
			accountNo = account.getAccountNo();
		}
		else{
			accountNo = account.getAccountNo();
		}
		accountCriteria.add(Restrictions.idEq(accountNo));

		latePaymentCriteria.add(Restrictions.le("effectiveDate", invoiceDate));
		latePaymentCriteria.addOrder(Order.desc("effectiveDate"));
		latePaymentCriteria.setMaxResults(1);

		List results = latePaymentCriteria.list();
		if(results.isEmpty()) {
			return new BigDecimal(0);
		}

		//Found the latest record
		AmtbAcctLatePymt acctLatePayment = (AmtbAcctLatePymt)results.get(0);

		//Now find the latest effective credit term under the plan
		Criteria latePaymentDetailCriteria = getSession().createCriteria(MstbLatePaymentDetail.class);
		Criteria latePaymentMasterCriteria = latePaymentDetailCriteria.createCriteria("mstbLatePaymentMaster", Criteria.INNER_JOIN);
		Criteria accountLatePaymentCriteria = latePaymentMasterCriteria.createCriteria("amtbAcctLatePymts", Criteria.INNER_JOIN);
		accountLatePaymentCriteria.add(Restrictions.idEq(acctLatePayment.getAcctLatePymtNo()));

		//		Calendar invoiceDateCalendar = Calendar.getInstance();
		//		invoiceDateCalendar.setTime(invoiceDate);
		latePaymentDetailCriteria.add(Restrictions.le("effectiveDt", invoiceDate));
		latePaymentDetailCriteria.addOrder(Order.desc("effectiveDt"));
		latePaymentDetailCriteria.setMaxResults(1);

		List results2 = latePaymentDetailCriteria.list();
		if(results2.isEmpty()) {
			return new BigDecimal(0);
		}

		return ((MstbLatePaymentDetail)results2.get(0)).getLatePayment();
	}

	public String getCustomerNo(Integer accountNo){
		DetachedCriteria accountCriteria = DetachedCriteria.forClass(AmtbAccount.class);
		DetachedCriteria acc2Criteria = accountCriteria.createCriteria("amtbAccount", "acc2", DetachedCriteria.LEFT_JOIN);
		DetachedCriteria acc3Criteria = acc2Criteria.createCriteria("amtbAccount", "acc3", DetachedCriteria.LEFT_JOIN);

		accountCriteria.add(Restrictions.idEq(accountNo));

		ProjectionList projections = Projections.projectionList();
		projections.add(Projections.property("custNo"));
		projections.add(Projections.property("acc2.custNo"));
		projections.add(Projections.property("acc3.custNo"));
		accountCriteria.setProjection(projections);

		List results = this.findAllByCriteria(accountCriteria);
		if(results.isEmpty()) {
			return "-";
		} else{
			Object[] resultArray = (Object[])results.get(0);
			for(Object obj : resultArray){
				if(obj!=null) {
					return obj.toString();
				}
			}
			return "-";
		}
	}
	@SuppressWarnings("unchecked")
	public AmtbSubscTo getProductSubscription(String custNo, String productTypeId){
		DetachedCriteria acctCriteria = DetachedCriteria.forClass(AmtbAccount.class);
		acctCriteria.add(Restrictions.eq("custNo", custNo));
		DetachedCriteria subscriptionCriteria = DetachedCriteria.forClass(AmtbSubscTo.class);
		subscriptionCriteria.add(Restrictions.eq("comp_id.amtbAccount.accountNo", ((AmtbAccount)this.findAllByCriteria(acctCriteria).get(0)).getAccountNo()));
		subscriptionCriteria.add(Restrictions.eq("comp_id.pmtbProductType.productTypeId", productTypeId));
		subscriptionCriteria.createCriteria("mstbProdDiscMaster", DetachedCriteria.LEFT_JOIN);
		subscriptionCriteria.createCriteria("lrtbRewardMaster", DetachedCriteria.LEFT_JOIN);
		subscriptionCriteria.createCriteria("mstbSubscFeeMaster", DetachedCriteria.LEFT_JOIN);
		subscriptionCriteria.createCriteria("mstbIssuanceFeeMaster", DetachedCriteria.LEFT_JOIN);
		List<AmtbSubscTo> subscriptions = this.findAllByCriteria(subscriptionCriteria);
		return subscriptions.isEmpty() ? null : subscriptions.get(0);
	}
	
		@SuppressWarnings("unchecked")
		public List<AmtbAccount> getAllAccountsByParentId(String accountNo,String category) {
		
		DetachedCriteria accountCriteria = DetachedCriteria.forClass(AmtbAccount.class);
		DetachedCriteria childrenAccountCriteria = accountCriteria.createCriteria("amtbAccount", Criteria.LEFT_JOIN); //join division
		
				
		if((category.equalsIgnoreCase(NonConfigurableConstants.ACCOUNT_CATEGORY_CORPORATE))||
				(category.equalsIgnoreCase(NonConfigurableConstants.ACCOUNT_CATEGORY_APPLICANT))){	
			DetachedCriteria grandChildrenAccountCriteria = childrenAccountCriteria.createCriteria("amtbAccount", Criteria.LEFT_JOIN); //join corporation
			accountCriteria.add(Restrictions.eq("accountNo", new Integer(accountNo)));
		}
		else if((category.equalsIgnoreCase(NonConfigurableConstants.ACCOUNT_CATEGORY_DIVISION))||
				(category.equalsIgnoreCase(NonConfigurableConstants.ACCOUNT_CATEGORY_SUB_APPLICANT))){	
			accountCriteria.add(Restrictions.eq("accountNo", new Integer(accountNo)));

		}
		//Assuming pulled out account is corporation level
		//DetachedCriteria childrenAccountCriteria = accountCriteria.createCriteria("amtbAccounts", Criteria.LEFT_JOIN); //join division
		//DetachedCriteria grandChildrenAccountCriteria = childrenAccountCriteria.createCriteria("amtbAccounts", Criteria.LEFT_JOIN); //join dept
		//Due to Left Join, Need to do Distinct
		accountCriteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		List<AmtbAccount> results = this.findAllByCriteria(accountCriteria);
		if(results.isEmpty()) {
			return null;
		} else {
			return results;
		}
	}

	@SuppressWarnings("unchecked")
	public List<AmtbAccount> getAllAccounts() {
		logger.info("Retrieve accounts");
		DetachedCriteria accountCriteria = DetachedCriteria.forClass(AmtbAccount.class);
	
		DetachedCriteria childrenAccountCriteria = accountCriteria.createCriteria("amtbAccount", Criteria.LEFT_JOIN); //join division
		DetachedCriteria grandChildrenAccountCriteria = childrenAccountCriteria.createCriteria("amtbAccount", Criteria.LEFT_JOIN); //join corporation
		//accountCriteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		accountCriteria.add(Restrictions.isNotNull("custNo"));
		List<AmtbAccount> results = this.findAllByCriteria(accountCriteria);
		
		if(!results.isEmpty())
			logger.info("Total account set "+results.size());
		logger.info("accountCriteria "+accountCriteria.toString());
		if(results.isEmpty()) {
			//logger.info("OMG No record!!!!");
			return null;
		} else 
			return results;
	}
	
	@SuppressWarnings("unchecked")
	public List<AmtbAccount> getAllAccountsbyParentID(Integer parentAccountId) {
		logger.info("Retrieve accounts");
		DetachedCriteria accountCriteria = DetachedCriteria.forClass(AmtbAccount.class);
		DetachedCriteria childrenAccountCriteria = accountCriteria.createCriteria("amtbAccount", Criteria.LEFT_JOIN); //join division
		childrenAccountCriteria.add(Restrictions.eq("accountNo",parentAccountId));
		List<AmtbAccount> results = this.findAllByCriteria(accountCriteria);
		logger.info(accountCriteria.toString());
		if(!results.isEmpty())
			logger.info("Total account set "+results.size());
		if(results.isEmpty()) {
			return null;
		} else 
			return results;
	}
	
	public AmtbAccount getAccountbyID(String acctLv1, String acctLv2, String acctLv3)
	{
		// If it is a department account
		DetachedCriteria accountCriteria = DetachedCriteria.forClass(AmtbAccount.class);
		DetachedCriteria parentAccountCriteria = accountCriteria.createCriteria("amtbAccount", Criteria.LEFT_JOIN);
		DetachedCriteria parentParentAccountCriteria = parentAccountCriteria.createCriteria("amtbAccount",Criteria.LEFT_JOIN);
		
		if (acctLv3 != null && !"".equals(acctLv3))
		{
			parentParentAccountCriteria.add(Restrictions.eq("custNo", acctLv1));
			parentAccountCriteria.add(Restrictions.eq("code", acctLv2));
			accountCriteria.add(Restrictions.eq("code", acctLv3 ));
			//parentParentAccountCriteria.createCriteria("fmtbArContCodeMaster", Criteria.LEFT_JOIN).createCriteria("fmtbEntityMaster", Criteria.LEFT_JOIN);
		}
		// if it is a division account
		else if (acctLv2 != null && !"".equals(acctLv2))
		{
			parentAccountCriteria.add(Restrictions.eq("custNo", acctLv1));
			accountCriteria.add(Restrictions.eq("code", acctLv2));
			//parentAccountCriteria.createCriteria("fmtbArContCodeMaster", Criteria.LEFT_JOIN).createCriteria("fmtbEntityMaster", Criteria.LEFT_JOIN);
		}
		// if it is a corporate account
		else
		{
			accountCriteria.add(Restrictions.eq("custNo", acctLv1));
			//accountCriteria.createCriteria("fmtbArContCodeMaster", Criteria.LEFT_JOIN).createCriteria("fmtbEntityMaster", Criteria.LEFT_JOIN);
		}
	
		List results = this.findMaxResultByCriteria(accountCriteria, MAX_ROW);
		if(results.isEmpty()) return null;
		else return (AmtbAccount)results.get(0);
	}
	
	@SuppressWarnings("unchecked")
	public AmtbAccount getAccountWithEntity(AmtbAccount amtbAccount){
		DetachedCriteria acctCriteria = DetachedCriteria.forClass(AmtbAccount.class);
		acctCriteria.createCriteria("fmtbArContCodeMaster", Criteria.LEFT_JOIN).createCriteria("fmtbEntityMaster", Criteria.LEFT_JOIN);
		acctCriteria.add(Restrictions.eq("accountNo", amtbAccount.getAccountNo()));
		
		//Default Payment Mode
		acctCriteria.createCriteria("mstbMasterTableByDefaultPaymentMode", Criteria.LEFT_JOIN);
		//Bank Master
		acctCriteria.createCriteria("mstbBankMaster", Criteria.LEFT_JOIN);
		
		acctCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		List<AmtbAccount> accts = this.findAllByCriteria(acctCriteria);
		return accts.isEmpty() ? null : accts.get(0);
	}
	
	@SuppressWarnings("unchecked")
	public boolean hasIssuedProducts(String custNo, String code, String parentCode, int level, List<String> productTypes){
		logger.info("hasIssuedProducts(custNo = "+custNo+", code = "+code+", parentCode = "+parentCode+", level = "+level+", productTypes = "+productTypes.size()+")");
		DetachedCriteria acctCriteria = DetachedCriteria.forClass(AmtbAccount.class);
		if(code!=null && code.trim().length()!=0){
			acctCriteria.add(Restrictions.eq("code", code));
		}
		DetachedCriteria tempCriteria = acctCriteria;
		for(int i=0;i<level;i++){
			tempCriteria = tempCriteria.createCriteria("amtbAccount", DetachedCriteria.LEFT_JOIN);
			if(i==0 && parentCode!=null && parentCode.trim().length()!=0){
				tempCriteria.add(Restrictions.eq("code", parentCode));
			}
		}
		if(custNo!=null && custNo.trim().length()!=0){
			tempCriteria.add(Restrictions.eq("custNo", custNo));
		}
		DetachedCriteria productCriteria = acctCriteria.createCriteria("pmtbProducts", DetachedCriteria.LEFT_JOIN);
		productCriteria
			.createCriteria("pmtbProductType", DetachedCriteria.LEFT_JOIN)
			.add(Restrictions.in("productTypeId", productTypes));
		List<PmtbProduct> products = getHibernateTemplate().findByCriteria(productCriteria, 0 ,1);
		if(products.isEmpty()){
			acctCriteria = DetachedCriteria.forClass(AmtbAccount.class);
			if(code!=null && code.trim().length()!=0){
				acctCriteria.add(Restrictions.eq("code", code));
			}
			tempCriteria = acctCriteria;
			for(int i=0;i<level;i++){
				tempCriteria = tempCriteria.createCriteria("amtbAccount", DetachedCriteria.LEFT_JOIN);
				if(i==0 && parentCode!=null && parentCode.trim().length()!=0){
					tempCriteria.add(Restrictions.eq("code", parentCode));
				}
			}
			if(custNo!=null && custNo.trim().length()!=0){
				tempCriteria.add(Restrictions.eq("custNo", custNo));
			}
			acctCriteria.createCriteria("amtbAccounts", DetachedCriteria.LEFT_JOIN);
			List<AmtbAccount> accts = this.findAllByCriteria(acctCriteria);
			return hasIssuedProducts(accts, productTypes);
		}else{
			return true;
		}
	}
	@SuppressWarnings("unchecked")
	private boolean hasIssuedProducts(List<AmtbAccount> accts, List<String> productTypes){
		logger.info("hasIssuedProducts(accts = "+accts.size()+", productTypes = "+productTypes.size()+")");
		boolean found = false;
		for(AmtbAccount acct : accts){
			DetachedCriteria productCriteria = DetachedCriteria.forClass(PmtbProduct.class);
			productCriteria
				.createCriteria("pmtbProductType", DetachedCriteria.LEFT_JOIN)
				.add(Restrictions.in("productTypeId", productTypes));
			productCriteria.add(Restrictions.eq("amtbAccount", acct));
			List<PmtbProduct> products = getHibernateTemplate().findByCriteria(productCriteria, 0 ,1);
			if(products.isEmpty()){
				DetachedCriteria childCriteria = DetachedCriteria.forClass(AmtbAccount.class);
				childCriteria.add(Restrictions.eq("amtbAccount", acct));
				List<AmtbAccount> children = this.findAllByCriteria(childCriteria);
				if(hasIssuedProducts(children, productTypes)){
					found = true;
					break;
				}
			}else{
				found = true;
				break;
			}
		}
		return found;
	}
	
	@SuppressWarnings("unchecked")
	public List<AmtbAccount> getTopLevelAccounts(String custNo, String custName){
		DetachedCriteria acctCriteria = DetachedCriteria.forClass(AmtbAccount.class);
		
		if(custNo!=null) acctCriteria.add(Restrictions.eq("custNo",custNo));
		if(custName!=null && custName.length()>2) acctCriteria.add(Restrictions.ilike("accountName",custName,MatchMode.START));
		
		acctCriteria.add(Restrictions.in("accountCategory", new String[]{NonConfigurableConstants.ACCOUNT_CATEGORY_APPLICANT, 
				NonConfigurableConstants.ACCOUNT_CATEGORY_CORPORATE}));
		
		return this.findDefaultMaxResultByCriteria(acctCriteria);
	}
	
	@SuppressWarnings("unchecked")
	public List<FmtbArContCodeMaster> getArContCode(Integer entityNo){
		DetachedCriteria criteria = DetachedCriteria.forClass(FmtbArContCodeMaster.class);
		DetachedCriteria entityCriteria =  criteria.createCriteria("fmtbEntityMaster", DetachedCriteria.INNER_JOIN);
		if(entityNo!=null) entityCriteria.add(Restrictions.eq("entityNo", entityNo));
		return this.findAllByCriteria(criteria);
	}
	
	public boolean isAccountMainBilling(Integer contactNo){
		logger.info("isAccountMainBilling(Integer contactNo)");
		return isAccountMain(contactNo, NonConfigurableConstants.MAIN_CONTACT_TYPE_BILLING);
	}
	
	public boolean isAccountMainShipping(Integer contactNo){
		logger.info("isAccountMainShipping(Integer contactNo)");
		return isAccountMain(contactNo, NonConfigurableConstants.MAIN_CONTACT_TYPE_SHIPPING);
	}
	
	@SuppressWarnings("unchecked")
	private boolean isAccountMain(Integer contactNo, String type){
		logger.info("isAccountMain(Integer contactNo, String type)");
		Session session = this.getHibernateTemplate().getSessionFactory().getCurrentSession();
		Transaction txn = session.beginTransaction();
		Query query = session.createSQLQuery("select contact.contact_person_no from AMTB_CONTACT_PERSON contact" +
				" inner join AMTB_ACCT_MAIN_CONTACT main on contact.CONTACT_PERSON_NO = main.CONTACT_PERSON_NO" +
				" where contact.ACCOUNT_NO = main.ACCOUNT_NO and contact.contact_person_no = :contact_no and main.main_contact_type = :type");
		query.setInteger("contact_no", contactNo);
		query.setString("type", type);
		List<BigDecimal> acctNos = query.list();
		txn.commit();
		return !acctNos.isEmpty();
	}
	
	public AmtbContactPerson getAccountMainBilling(String custNo){
		logger.info("getAccountMainBilling(Integer contactNo)");
		return getAccountMain(custNo, NonConfigurableConstants.MAIN_CONTACT_TYPE_BILLING);
	}
	
	public AmtbContactPerson getAccountMainShipping(String custNo){
		logger.info("getAccountMainShipping(Integer contactNo)");
		return getAccountMain(custNo, NonConfigurableConstants.MAIN_CONTACT_TYPE_SHIPPING);
	}
	
	@SuppressWarnings("unchecked")
	private AmtbContactPerson getAccountMain(String custNo, String type){
		logger.info("getAccountMain(custNo = "+custNo+", type = "+type+")");
		// need to clear cache due to top level are cache. Unable to do main contact as top as unable to join
		// up with comp_id.amtbAccount
		getHibernateTemplate().clear();
//		DetachedCriteria subAcct = DetachedCriteria.forClass(AmtbAccount.class);
//		subAcct.add(Restrictions.eq("custNo", custNo));
//		subAcct.setProjection(Projections.distinct(Projections.property("accountNo")));
//		DetachedCriteria mainCriteria = DetachedCriteria.forClass(AmtbAcctMainContact.class);
//		mainCriteria.add(Restrictions.eq("comp_id.mainContactType", type));
//		mainCriteria.createCriteria("amtbContactPerson", DetachedCriteria.LEFT_JOIN);
//		mainCriteria.add(Subqueries.propertyEq("comp_id.amtbAccount", subAcct));
//		List<AmtbAcctMainContact> mains = this.findAllByCriteria(mainCriteria);
//		return mains.isEmpty() ? null : mains.get(0).getAmtbContactPerson();
		DetachedCriteria acctCriteria = DetachedCriteria.forClass(AmtbAccount.class);
		acctCriteria.add(Restrictions.eq("custNo", custNo));
		DetachedCriteria mainCriteria = acctCriteria.createCriteria("amtbAcctMainContacts", DetachedCriteria.LEFT_JOIN);
		mainCriteria.add(Restrictions.eq("comp_id.mainContactType", type));
		mainCriteria.createCriteria("amtbContactPerson", DetachedCriteria.LEFT_JOIN);
		List<AmtbAccount> accts = this.findAllByCriteria(acctCriteria);
//		logger.info(accts.get(0).getAmtbAcctMainContacts().iterator().next().getAmtbContactPerson().getContactPersonNo());
		return accts.isEmpty() ? null : accts.get(0).getAmtbAcctMainContacts().iterator().next().getAmtbContactPerson();
	}

	public AmtbAccount getAccountByCustomerId(String custId) {
		DetachedCriteria accountCriteria = DetachedCriteria.forClass(AmtbAccount.class);
		accountCriteria.add(Restrictions.eq("custNo", custId));
		List results = this.findAllByCriteria(accountCriteria);
		if(results.isEmpty()) {
			return null;
		} else {
			return (AmtbAccount) results.get(0);
		}
	}
	
	@SuppressWarnings("unchecked")
	public List<AmtbAccount> getAccountSubscribedToExternalCard(String productTypeId){
		logger.info("getAccountSubscribedToProductType(productTypeId = "+productTypeId+")");
		DetachedCriteria acctCriteria = DetachedCriteria.forClass(AmtbAccount.class);
		acctCriteria.createCriteria("amtbSubscTos", DetachedCriteria.LEFT_JOIN)
		.add(Restrictions.eq("comp_id.pmtbProductType.productTypeId", productTypeId));
		acctCriteria.createCriteria("amtbAcctStatuses", DetachedCriteria.LEFT_JOIN);
		return this.findAllByCriteria(acctCriteria);
	}
	
	@SuppressWarnings("unchecked")
	public List<AmtbAccount> getBilliableAccountOnlyTopLevelWithEffectiveEntity(String customerNo, String name){
		DetachedCriteria accountCriteria = DetachedCriteria.forClass(AmtbAccount.class);
		accountCriteria.createCriteria("amtbAcctType", DetachedCriteria.INNER_JOIN);
		accountCriteria.createCriteria("mstbMasterTableByDefaultPaymentMode", DetachedCriteria.LEFT_JOIN);
		DetachedCriteria arContCodeMaster = accountCriteria.createCriteria("fmtbArContCodeMaster", DetachedCriteria.LEFT_JOIN);
		DetachedCriteria entity = arContCodeMaster.createCriteria("fmtbEntityMaster", DetachedCriteria.LEFT_JOIN);
		accountCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);

		if(customerNo!=null){
			accountCriteria.add(Restrictions.eq("custNo", customerNo));
		}
		if(name!=null && name.length()>=3) {
			accountCriteria.add(Restrictions.ilike("accountName", name, MatchMode.ANYWHERE));
		}

		//billiable = invoice format not null
		accountCriteria.add(Restrictions.isNotNull("invoiceFormat"));
		//must be top level
		accountCriteria.add(Restrictions.in("accountCategory", new String[]{NonConfigurableConstants.ACCOUNT_CATEGORY_APPLICANT, NonConfigurableConstants.ACCOUNT_CATEGORY_CORPORATE}));
		//entity must be effective
		entity.add(Restrictions.or(
				Restrictions.ge("effectiveEndDate", DateUtil.getCurrentDate()),
				Restrictions.isNull("effectiveEndDate")
			));
		
		accountCriteria.addOrder(Order.asc("accountName"));

		return this.findAllByCriteria(accountCriteria);
	}
	
	@SuppressWarnings("unchecked")
	public List<AmtbAccount> getAccountSubscribedToExternalCardWithContacts(String productTypeId){
		logger.info("getAccountSubscribedToProductType(productTypeId = "+productTypeId+")");
		DetachedCriteria acctCriteria = DetachedCriteria.forClass(AmtbAccount.class);
		acctCriteria.createCriteria("amtbCorporateDetails", Criteria.LEFT_JOIN);
		acctCriteria.createCriteria("amtbSubscTos", DetachedCriteria.LEFT_JOIN)
		.add(Restrictions.eq("comp_id.pmtbProductType.productTypeId", productTypeId));
		acctCriteria.createCriteria("amtbAcctStatuses", DetachedCriteria.LEFT_JOIN);
		DetachedCriteria mainBillingContacts = acctCriteria.createCriteria("amtbAcctMainContacts", Criteria.LEFT_JOIN);
				
		//productStatusCriteria.add(Restrictions.eq("statusTo", NonConfigurableConstants.ACCOUNT_STATUS_ACTIVE));
		mainBillingContacts.add(Restrictions.eq("comp_id.mainContactType", NonConfigurableConstants.MAIN_CONTACT_TYPE_BILLING));
		return this.findAllByCriteria(acctCriteria);
	}
	
	@SuppressWarnings("unchecked")
	public boolean checkPromotionOverlapping(String custNo, Integer promotionNo, Date fromDate, Date toDate){
		Session session = this.getHibernateTemplate().getSessionFactory().openSession();
		List results = new ArrayList();
		try{
			Query query = session.getNamedQuery("checkPromotionOverlapping");
			query.setParameter("custNo", custNo, Hibernate.STRING);
			query.setParameter("promotionNo", promotionNo, Hibernate.INTEGER);
			query.setParameter("fromDate", fromDate, Hibernate.DATE);
			query.setParameter("toDate", toDate, Hibernate.DATE);
			results = query.list();
			return (Integer)results.get(0) == 1; //result is 1 means YES there are overlapping
		}
		catch(Exception e){
			e.printStackTrace();
		}
		finally{
			session.close();
		}

		return false;
	}
	
	@SuppressWarnings("unchecked")
	public List<AmtbAccount> getAccountsByMainContactPerson(String contactPersonNo, String contactPersonType){
		logger.info("getAccountsByMainContactPerson(contactPersonNo = "+contactPersonNo+")");
		DetachedCriteria acctCriteria = DetachedCriteria.forClass(AmtbAccount.class);
		DetachedCriteria mainContactCriteria = acctCriteria.createCriteria("amtbAcctMainContacts", Criteria.LEFT_JOIN);
		DetachedCriteria contactCriteria = mainContactCriteria.createCriteria("amtbContactPerson", Criteria.LEFT_JOIN);
		if(contactPersonType != null && contactPersonType.length()>0)
			mainContactCriteria.add(Restrictions.eq("comp_id.mainContactType", contactPersonType));
		contactCriteria.add(Restrictions.eq("contactPersonNo", Integer.parseInt(contactPersonNo)));
		acctCriteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		return this.findAllByCriteria(acctCriteria);
	}

	@SuppressWarnings("unchecked")
	public List<AmtbAccount> getChildAccountSubscribedToByTopLevelAccountNoAndProductTypeId(String custNo, String productTypeId){
		DetachedCriteria acctCrit = DetachedCriteria.forClass(AmtbAccount.class);
		DetachedCriteria subscToCrit = acctCrit.createCriteria("amtbSubscTos", DetachedCriteria.LEFT_JOIN);
		DetachedCriteria acct2Crit = acctCrit.createCriteria("amtbAccount", "acct2", DetachedCriteria.LEFT_JOIN);
		@SuppressWarnings("unused")
		DetachedCriteria acct3Crit = acct2Crit.createCriteria("amtbAccount", "acct3", DetachedCriteria.LEFT_JOIN);
		
		subscToCrit.add(Restrictions.eq("comp_id.pmtbProductType.productTypeId", productTypeId));
		acctCrit.add(Restrictions.or(Restrictions.eq("acct2.custNo", custNo), Restrictions.eq("acct3.custNo", custNo)));
		
		return this.findAllByCriteria(subscToCrit);
	}

	@SuppressWarnings("unchecked")
	public List<AmtbContactPerson> getTransferContacts(String custNo, String contactPersonType) {

		DetachedCriteria contactPersonCrit = DetachedCriteria.forClass(AmtbContactPerson.class);
		contactPersonCrit.createCriteria("mstbMasterTableByMainContactSal",
				DetachedCriteria.LEFT_JOIN);

		// only retrieve contacts under a top level account
		DetachedCriteria acctCrit = contactPersonCrit.createCriteria("amtbAccount",
				DetachedCriteria.LEFT_JOIN);
		acctCrit.add(Restrictions.eq("custNo", custNo));

		// we want to be able to filter by billing, shipping or list all of them
		if (contactPersonType != null && contactPersonType.length() > 0) {
			DetachedCriteria mainContactCrit = contactPersonCrit.createCriteria(
					"amtbAcctMainContacts", DetachedCriteria.LEFT_JOIN);
			mainContactCrit.add(Restrictions.eq("comp_id.mainContactType", contactPersonType));
		}

		contactPersonCrit.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		contactPersonCrit.addOrder(Order.asc("mainContactName"));

		return this.findAllByCriteria(contactPersonCrit);
	}
	
	@SuppressWarnings("unchecked")
	public List<AmtbAccount> getGovtEInvChildrenAccounts(AmtbAccount topLevelAccount){
		
		DetachedCriteria acctCrit = DetachedCriteria.forClass(AmtbAccount.class);
		DetachedCriteria acct2Crit = acctCrit.createCriteria("amtbAccount", "acct2", DetachedCriteria.LEFT_JOIN);
		DetachedCriteria acct3Crit = acct2Crit.createCriteria("amtbAccount", "acct3", DetachedCriteria.LEFT_JOIN);
		
		// subscribed to govt einv
		acctCrit.add(Restrictions.isNotNull("govtEInvoiceFlag"));
		acctCrit.add(Restrictions.ne("govtEInvoiceFlag", NonConfigurableConstants.GOVT_EINV_FLAG_NO));
		// belongs to given top level account
		Disjunction disjunction = Restrictions.disjunction();
		disjunction.add(Restrictions.eq("acct2.accountNo", topLevelAccount.getAccountNo()));
		disjunction.add(Restrictions.eq("acct3.accountNo", topLevelAccount.getAccountNo()));
		acctCrit.add(disjunction);
		
		acctCrit.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		return this.findAllByCriteria(acctCrit);
	}
	@SuppressWarnings("unchecked")
	public List<AmtbAccount> getPubbsChildrenAccounts(AmtbAccount topLevelAccount){
		
		DetachedCriteria acctCrit = DetachedCriteria.forClass(AmtbAccount.class);
		DetachedCriteria acct2Crit = acctCrit.createCriteria("amtbAccount", "acct2", DetachedCriteria.LEFT_JOIN);
		DetachedCriteria acct3Crit = acct2Crit.createCriteria("amtbAccount", "acct3", DetachedCriteria.LEFT_JOIN);
		
		// subscribed to pubbs
		acctCrit.add(Restrictions.isNotNull("pubbsFlag"));
		acctCrit.add(Restrictions.ne("pubbsFlag", NonConfigurableConstants.PUBBS_FLAG_NO));
		// belongs to given top level account
		Disjunction disjunction = Restrictions.disjunction();
		disjunction.add(Restrictions.eq("acct2.accountNo", topLevelAccount.getAccountNo()));
		disjunction.add(Restrictions.eq("acct3.accountNo", topLevelAccount.getAccountNo()));
		acctCrit.add(disjunction);
		
		acctCrit.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		return this.findAllByCriteria(acctCrit);
	}
	
	@SuppressWarnings("unchecked")
	public AmtbSubscTo getSubscribeTo(AmtbAccount account, PmtbProductType productType, Date runDate){
		
		DetachedCriteria subscribeToCriteria = DetachedCriteria.forClass(AmtbSubscTo.class);
		
		//LEFT JOIN
		subscribeToCriteria.createCriteria("mstbProdDiscMaster", Criteria.LEFT_JOIN);
		subscribeToCriteria.createCriteria("lrtbRewardMaster", Criteria.LEFT_JOIN);
		subscribeToCriteria.createCriteria("mstbSubscFeeMaster", Criteria.LEFT_JOIN);
		
		subscribeToCriteria.add(Restrictions.eq("comp_id.amtbAccount", account));
		subscribeToCriteria.add(Restrictions.eq("comp_id.pmtbProductType", productType));
		subscribeToCriteria.add(Restrictions.le("effectiveDt",DateUtil.convertDateTo2359Hours(runDate)));
		
		subscribeToCriteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		
		//Pick the latest subscribedTo Record
		subscribeToCriteria.addOrder(Order.desc("effectiveDt"));
		List results = this.findAllByCriteria(subscribeToCriteria);
		
		return (AmtbSubscTo) firstResult(results);
	}
	
	
	
	@SuppressWarnings("unchecked")
	public List<AmtbAccount> getChargeToParentDivisionAccounts(AmtbAccount topAcct){
		
		DetachedCriteria dc = DetachedCriteria.forClass(AmtbAccount.class, "acct");
		dc.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		dc.createCriteria("amtbAcctStatuses", Criteria.LEFT_JOIN);
		
		dc.createCriteria("acct.amtbAccount", "parentAcct", Criteria.LEFT_JOIN);
		dc.add(Restrictions.eq("parentAcct.accountNo", topAcct.getAccountNo()));
		dc.add(Restrictions.isNull("invoiceFormat"));
		
		List results = this.findAllByCriteria(dc);
		return results;
		
	}
	
	public List<AmtbAccount> getChargeToParentDepartmentAccounts(AmtbAccount divAcct){
		
		logger.debug("getChargeToParentDepartmentAccounts");
		
		DetachedCriteria dc = DetachedCriteria.forClass(AmtbAccount.class, "acct");
		dc.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		dc.createCriteria("amtbAcctStatuses", Criteria.LEFT_JOIN);
		
		dc.createCriteria("acct.amtbAccount", "parentAcct", Criteria.LEFT_JOIN);
		dc.createCriteria("parentAcct.amtbAccount", "grandParentAcct", Criteria.LEFT_JOIN);
		
		dc.add(Restrictions.eq("parentAcct.accountNo", divAcct.getAccountNo()));
		dc.add(Restrictions.isNull("invoiceFormat"));
		
		List results = this.findAllByCriteria(dc);
		
		return results;
		
	}
	
	public List<MstbMasterTable> getMstbtable_EMFSRA(){
		DetachedCriteria accountCriteria = DetachedCriteria.forClass(MstbMasterTable.class);
		accountCriteria.add(Restrictions.sqlRestriction(" (trim(this_.master_TYPE) = 'EMFSRA' )"));
		
		return this.findAllByCriteria(accountCriteria);
	}
	
	public AmtbAcctSalesperson getAccountMainSalesPerson(String custNo) {
		DetachedCriteria amtbASCriteria = DetachedCriteria.forClass(AmtbAcctSalesperson.class);
		DetachedCriteria acctCrit = amtbASCriteria.createCriteria("amtbAccount", DetachedCriteria.LEFT_JOIN);
		DetachedCriteria mstbCrit = amtbASCriteria.createCriteria("mstbSalesperson", DetachedCriteria.LEFT_JOIN);
		
		amtbASCriteria.add(Restrictions.le("effectiveDtFrom", DateUtil.getCurrentTimestamp()));
		acctCrit.add(Restrictions.eq("custNo", custNo));
		amtbASCriteria.addOrder(Order.desc("effectiveDtFrom"));
		
		List <AmtbAcctSalesperson> results = this.findAllByCriteria(amtbASCriteria);
		if(results.isEmpty()) return null;
		else return results.get(0);
	}
	
	public List<AmtbSubscProdReq> getAccountPendApproveSubscription(Integer acctNo, String productTypeId) {
		DetachedCriteria subscProdReqCriteria = DetachedCriteria.forClass(AmtbSubscProdReq.class);
		DetachedCriteria acctCrit = subscProdReqCriteria.createCriteria("amtbAccount", DetachedCriteria.LEFT_JOIN);
		DetachedCriteria productCrit = subscProdReqCriteria.createCriteria("pmtbProductType", Criteria.LEFT_JOIN);
		
		acctCrit.add(Restrictions.eq("accountNo", acctNo));
		productCrit.add(Restrictions.eq("productTypeId", productTypeId));
		subscProdReqCriteria.add(Restrictions.eq("appStatus",NonConfigurableConstants.SUBSCRIPTION_APPROVE_STATUS_PENDING_APPROVED));
		return this.findAllByCriteria(subscProdReqCriteria);
	}
	@SuppressWarnings("unchecked")
	public List<AmtbSubscProdReq> getPendingSubscriptionRequests(){
		DetachedCriteria requestCriteria = DetachedCriteria.forClass(AmtbSubscProdReq.class);
		DetachedCriteria acctCrit = requestCriteria.createCriteria("amtbAccount", DetachedCriteria.LEFT_JOIN);
		DetachedCriteria parentAccountCriteria = acctCrit.createCriteria("amtbAccount", DetachedCriteria.LEFT_JOIN);
		
		requestCriteria.add(Restrictions.eq("appStatus",NonConfigurableConstants.SUBSCRIPTION_APPROVE_STATUS_PENDING_APPROVED));
		requestCriteria.addOrder(Order.asc("subProdReqNo"));
	
		return findAllByCriteria(requestCriteria);
	}
	@SuppressWarnings("unchecked")
	public AmtbSubscTo getUnsubscribePlans(Integer acctNo, String productTypeId){
		
		DetachedCriteria acctCriteria = DetachedCriteria.forClass(AmtbAccount.class);
		acctCriteria.add(Restrictions.eq("accountNo", acctNo));
		DetachedCriteria subscriptionCriteria = DetachedCriteria.forClass(AmtbSubscTo.class);
		subscriptionCriteria.add(Restrictions.eq("comp_id.amtbAccount.accountNo", ((AmtbAccount)this.findAllByCriteria(acctCriteria).get(0)).getAccountNo()));
		subscriptionCriteria.add(Restrictions.eq("comp_id.pmtbProductType.productTypeId", productTypeId));
		subscriptionCriteria.createCriteria("mstbProdDiscMaster", DetachedCriteria.LEFT_JOIN);
		subscriptionCriteria.createCriteria("lrtbRewardMaster", DetachedCriteria.LEFT_JOIN);
		subscriptionCriteria.createCriteria("mstbSubscFeeMaster", DetachedCriteria.LEFT_JOIN);
		subscriptionCriteria.createCriteria("mstbIssuanceFeeMaster", DetachedCriteria.LEFT_JOIN);
		List<AmtbSubscTo> subscriptions = this.findAllByCriteria(subscriptionCriteria);
		return subscriptions.isEmpty() ? null : subscriptions.get(0);
	}
	@SuppressWarnings("unchecked")
	public AmtbSubscProdReq getPendingSubscriptionRequest(Integer requestId){
		DetachedCriteria requestCriteria = DetachedCriteria.forClass(AmtbSubscProdReq.class);
		requestCriteria.add(Restrictions.eq("subProdReqNo", requestId));
		DetachedCriteria acctCrit = requestCriteria.createCriteria("amtbAccount", DetachedCriteria.LEFT_JOIN);
		DetachedCriteria productCrit = requestCriteria.createCriteria("pmtbProductType", Criteria.LEFT_JOIN);
		requestCriteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		List<AmtbSubscProdReq> requests = findAllByCriteria(requestCriteria);
		return requests.isEmpty() ? null : requests.get(0);
	}
	@SuppressWarnings("unchecked")
	public List<AmtbSubscProdReq> getSubscriptionRequest(String custNo, String acctName, Date from, Date to, String status, String requester, String action){
		DetachedCriteria requestCriteria = DetachedCriteria.forClass(AmtbSubscProdReq.class);
		DetachedCriteria acctCriteria = requestCriteria.createCriteria("amtbAccount", DetachedCriteria.LEFT_JOIN);
		DetachedCriteria parentAccountCriteria = acctCriteria.createCriteria("amtbAccount", Criteria.LEFT_JOIN); //join division
		DetachedCriteria grandParentAccountCriteria = parentAccountCriteria.createCriteria("amtbAccount", Criteria.LEFT_JOIN); //join corporation

		if(custNo!=null && custNo.length()!=0){
//			acctCriteria.add(Restrictions.ilike("custNo", custNo, MatchMode.ANYWHERE));
			acctCriteria.add(Restrictions.sqlRestriction("( amtbaccoun1_.cust_No like '%"+custNo+"%' or amtbaccoun2_.cust_no like '%"+custNo+"%' or amtbaccoun3_.cust_no like '%"+custNo+"%' )"));
		}
		if(acctName!=null && acctName.length()!=0){
//			acctCriteria.add(Restrictions.ilike("accountName", acctName, MatchMode.ANYWHERE));
			acctCriteria.add(Restrictions.sqlRestriction("( amtbaccoun1_.account_name like '%"+acctName+"%' or amtbaccoun2_.account_name like '%"+acctName+"%' or amtbaccoun3_.account_name like '%"+acctName+"%' )"));
		}
		requestCriteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		List<Long> requesterNoList = new ArrayList();
		if(requester != null) {
			DetachedCriteria userCriteria = DetachedCriteria.forClass(SatbUser.class);
			userCriteria.add(Restrictions.ilike("name",  requester, MatchMode.ANYWHERE));
			List<SatbUser> userReq = this.findAllByCriteria(userCriteria);
			for(SatbUser user : userReq) {
				requesterNoList.add(user.getUserId());
			}
		}
		List<AmtbSubscProdReq> returnList = new ArrayList<AmtbSubscProdReq>();
		List<AmtbSubscProdReq> requests = this.findAllByCriteria(requestCriteria);
		for(AmtbSubscProdReq request : requests){
			
			if(from!=null){ // start date is input
				if(to!=null){ // both start and end date are input
					if(request.getReqDt().before(from) || request.getReqDt().after(DateUtil.convertDateTo2359Hours(to))){
						continue;
					}
				}else{//only start date is input
					if(request.getReqDt().before(from) || request.getReqDt().after(DateUtil.convertDateTo2359Hours(from))){
						continue;
					}
				}
			}else if(to!=null){// only end date is input
				if(request.getReqDt().before(DateUtil.convertDateTo2359Hours(to)) || request.getReqDt().after(DateUtil.convertDateTo2359Hours(to))){
					continue;
				}
			}
			if(status!=null && status.length()!=0){
				if(!request.getAppStatus().equals(status)){
					continue;
				}
			}
			if(requester!=null && requester.length()!=0){
				if(!requesterNoList.contains(request.getReqBy())) {
					continue;
				}
			}
			if(action!=null && action.length()!=0){
				if(action.equals(NonConfigurableConstants.SUBSCRIPTION_ACTION_STATUS_SUBSCRIBE) && !(request.getSubscAction().equals("S") || request.getSubscAction().equals("SD") || request.getSubscAction().equals("E")))
					continue;
				else if(action.equals(NonConfigurableConstants.SUBSCRIPTION_ACTION_STATUS_UNSUBSCRIBE) && !(request.getSubscAction().equals("U") || request.getSubscAction().equals("UD")))
					continue;
			}
			returnList.add(request);
		}
		return returnList;
	}
	
	public void updateOutsourcePrintFlag(AmtbAccount acct)
	{
		String outsourcePrintFlag = acct.getOutsourcePrintingFlag();
		
		if(acct.getAmtbAccounts() != null) 
		{
			for(AmtbAccount acctSecond : acct.getAmtbAccounts())
			{
				acctSecond.setOutsourcePrintingFlag(outsourcePrintFlag);
				
				if(acctSecond.getAmtbAccounts() != null) 
				{
					for(AmtbAccount acctThird : acctSecond.getAmtbAccounts())
					{
						acctThird.setOutsourcePrintingFlag(outsourcePrintFlag);
						this.update(acctThird);
					}
				}
				
				this.update(acctSecond);
			}
		}
	}
	//rs-ibs-cnii-acctsync
	public void updateCniiAcctSyncProcedure(String account_id, String account_code, String account_name, String parent_id, Date terminate_dt, Date susp_dt_start, Date susp_dt_end, String updated_by) throws Exception
	{
		Session session = null;
		Connection conn = null;
		C3P0ProxyConnection castCon = null;
		PreparedStatement ps = null;
		try
		{
			//String url = "jdbc:oracle:thin:@//10.0.0.124:1521/wizvision";
			//Class.forName("oracle.jdbc.driver.OracleDriver");
			//Connection conn = DriverManager.getConnection(url, "IBS_USR", "ibsusr");
			session = this.getHibernateTemplate().getSessionFactory().openSession();
			//conn = session.connection();
			
			/////// For Tomcat testing
			//castCon = (C3P0ProxyConnection) ((ConnectionWrapper)conn).getWrappedConnection();
			//Method method = C3P0NativeJdbcExtractor.class.getMethod("getRawConnection", new Class[]{Connection.class});
			//conn = (Connection) castCon.rawConnectionOperation(method, null, new Object[] {C3P0ProxyConnection.RAW_CONNECTION});
			
			//ps = (OraclePreparedStatement) ((OracleConnection)conn).prepareStatement("begin esc_pkg_ibs_interface.sync_ps_account(?,?,?,?,?,?,?,?);end;");
			ps = session.connection().prepareStatement("begin esc_pkg_ibs_interface.sync_ps_account(?,?,?,?,?,?,?,?);end;");
			//ps = (OraclePreparedStatement) ((OracleConnection)conn).prepareStatement("begin sync_ps_account(?,?,?,?,?,?,?,?);end;");
			
			ps.setString(1, account_id);
			ps.setString(2, account_code);
			ps.setString(3, account_name);
			
			if(parent_id != null)
				ps.setString(4, parent_id);
			else
				ps.setNull(4,  java.sql.Types.VARCHAR);
			
			if(terminate_dt != null)
				ps.setDate(5, new java.sql.Date(terminate_dt.getTime()));
			else
				ps.setNull(5, java.sql.Types.DATE);
			
			if(susp_dt_start != null)
				ps.setDate(6, new java.sql.Date(susp_dt_start.getTime()));
			else
				ps.setNull(6, java.sql.Types.DATE);
			
			if(susp_dt_end != null)
				ps.setDate(7, new java.sql.Date(susp_dt_end.getTime()));
			else
				ps.setNull(7, java.sql.Types.DATE);
				
			ps.setString(8, updated_by);

			
			ps.execute();
			ps.getConnection().commit();
			
		}
		catch (Exception e)
		{
			e.printStackTrace();
			if (ps != null)
				ps.close();
			if (castCon != null)
				castCon.close();
			if(session.isConnected()){
				session.close();
			}
			throw new Exception("Oracle exception");
		}
		finally
		{
			if (ps != null)
				ps.close();
			if (castCon != null)
				castCon.close();
			if(session.isConnected()){
				session.close();
			}
		}
	}

	@SuppressWarnings("unchecked")
	public List<AmtbAccount> getAllSubAccountsByParentAccount(String topLevelAccountCustNo){
		
		DetachedCriteria acctCrit = DetachedCriteria.forClass(AmtbAccount.class);
		DetachedCriteria acct2Crit = acctCrit.createCriteria("amtbAccount", "acct2", DetachedCriteria.LEFT_JOIN);
		DetachedCriteria acct3Crit = acct2Crit.createCriteria("amtbAccount", "acct3", DetachedCriteria.LEFT_JOIN);
		
		// belongs to given top level account
		Disjunction disjunction = Restrictions.disjunction();
		disjunction.add(Restrictions.eq("acct2.custNo", topLevelAccountCustNo));
		disjunction.add(Restrictions.eq("acct3.custNo", topLevelAccountCustNo));
		acctCrit.add(disjunction);
		
		acctCrit.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		return this.findAllByCriteria(acctCrit);
	}
	
	@SuppressWarnings("unchecked")
	public List<AmtbAccount> getAccountRecurringByChargeDay(Integer day){
		
		DetachedCriteria acctCrit = DetachedCriteria.forClass(AmtbAccount.class);
		DetachedCriteria acct2Crit = acctCrit.createCriteria("amtbAccount", "acct2", DetachedCriteria.LEFT_JOIN);
		DetachedCriteria acct3Crit = acct2Crit.createCriteria("amtbAccount", "acct3", DetachedCriteria.LEFT_JOIN);
		
		// belongs to given top level account
//		acctCrit.add(Restrictions.eq("recurringFlag", "Y"));
//		acctCrit.add(Restrictions.eq("recurringChargeDay", day));
		
		// belongs to given top level account
		Disjunction disjunction = Restrictions.disjunction();
		Conjunction conjunction2 = Restrictions.conjunction();
		Conjunction conjunction3 = Restrictions.conjunction();
		Conjunction conjunction4 = Restrictions.conjunction();
		conjunction2.add(Restrictions.eq("recurringFlag", "Y"));
		conjunction2.add(Restrictions.eq("recurringChargeDay", day));
		conjunction3.add(Restrictions.eq("acct2.recurringFlag", "Y"));
		conjunction3.add(Restrictions.eq("acct2.recurringChargeDay", day));
		conjunction4.add(Restrictions.eq("acct3.recurringFlag", "Y"));
		conjunction4.add(Restrictions.eq("acct3.recurringChargeDay", day));
		disjunction.add(conjunction2);
		disjunction.add(conjunction3);
		disjunction.add(conjunction4);
		acctCrit.add(disjunction);
		
		acctCrit.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		return this.findAllByCriteria(acctCrit);
	}
	
		
	@SuppressWarnings("unchecked")
	public List<AmtbAcctStatus> getAccountStatusAll(String accountNo, String custName){
		DetachedCriteria statusCriteria = DetachedCriteria.forClass(AmtbAcctStatus.class);
		statusCriteria.createCriteria("amtbAccount", DetachedCriteria.LEFT_JOIN)
		.add(Restrictions.eq("custNo", accountNo));
		statusCriteria.add(Restrictions.le("effectiveDt", DateUtil.getCurrentDate()));
		statusCriteria.addOrder(Order.desc("effectiveDt"));
		return this.findDefaultMaxResultByCriteria(statusCriteria);
	}
	
	@SuppressWarnings("unchecked")
	public List<AmtbAccount> findAccdtlbyaccNoandName(String custNo, String custName){
		DetachedCriteria acctCriteria = DetachedCriteria.forClass(AmtbAccount.class);
		
		if(custNo!=null) acctCriteria.add(Restrictions.eq("custNo",custNo));
		if(custName!=null) acctCriteria.add(Restrictions.eq("accountName",custName));
		return this.findDefaultMaxResultByCriteria(acctCriteria);
	}
	
}