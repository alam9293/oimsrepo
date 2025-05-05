package com.cdgtaxi.ibs.common.dao;

import java.math.BigDecimal;
import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.cdgtaxi.ibs.common.model.IttbCpCustCardIssuance;
import com.cdgtaxi.ibs.common.model.IttbCpLogin;
import com.cdgtaxi.ibs.common.model.IttbCpLoginNew;
import com.cdgtaxi.ibs.common.model.IttbCpMasterLogin;
import com.cdgtaxi.ibs.common.model.IttbCpMasterTagAcct;

public class PortalDaoHibernate extends GenericDaoHibernate implements PortalDao{

	@SuppressWarnings({"unchecked","unused"})
	public List<IttbCpLogin> searchPortalUser(String userLoginId, String cardNo,
			Integer contactPersonId, String contactPersonName, Integer accountNo, String accountName, String masterLoginId){
		
		DetachedCriteria login = DetachedCriteria.forClass(IttbCpLogin.class);
		DetachedCriteria product = login.createCriteria("pmtbProduct", DetachedCriteria.LEFT_JOIN);
		DetachedCriteria contactPerson = login.createCriteria("amtbContactPerson", DetachedCriteria.LEFT_JOIN);
		
		DetachedCriteria account = product.createCriteria("amtbAccount", "acct", DetachedCriteria.LEFT_JOIN);
			DetachedCriteria masterLogTagAccount1a = account.createCriteria("ittbCpMasterTagAcct", "ittbCpMasterTagAcct1a", DetachedCriteria.LEFT_JOIN);
			DetachedCriteria masterLogAccount1a = masterLogTagAccount1a.createCriteria("masterLoginNo", "masterLoginNo1a", DetachedCriteria.LEFT_JOIN);
		DetachedCriteria paccount = account.createCriteria("amtbAccount", "pacct", DetachedCriteria.LEFT_JOIN);
			DetachedCriteria masterLogTagAccount1b = paccount.createCriteria("ittbCpMasterTagAcct", "ittbCpMasterTagAcct1b", DetachedCriteria.LEFT_JOIN);
			DetachedCriteria masterLogAccount1b = masterLogTagAccount1b.createCriteria("masterLoginNo", "masterLoginNo1b", DetachedCriteria.LEFT_JOIN);
		DetachedCriteria gpaccount = paccount.createCriteria("amtbAccount", "gpacct", DetachedCriteria.LEFT_JOIN);
			DetachedCriteria masterLogTagAccount1c = gpaccount.createCriteria("ittbCpMasterTagAcct", "ittbCpMasterTagAcct1c", DetachedCriteria.LEFT_JOIN);
			DetachedCriteria masterLogAccount1c = masterLogTagAccount1c.createCriteria("masterLoginNo", "masterLoginNo1c", DetachedCriteria.LEFT_JOIN);
		
		DetachedCriteria account2 = contactPerson.createCriteria("amtbAccount", "acct2", DetachedCriteria.LEFT_JOIN);
			DetachedCriteria masterLogTagAccount2a = account2.createCriteria("ittbCpMasterTagAcct", "ittbCpMasterTagAcct2a", DetachedCriteria.LEFT_JOIN);
			DetachedCriteria masterLogAccount2a = masterLogTagAccount2a.createCriteria("masterLoginNo", "masterLoginNo2a", DetachedCriteria.LEFT_JOIN);
		DetachedCriteria paccount2 = account2.createCriteria("amtbAccount", "pacct2", DetachedCriteria.LEFT_JOIN);
			DetachedCriteria masterLogTagAccount2b = paccount2.createCriteria("ittbCpMasterTagAcct", "ittbCpMasterTagAcct2b", DetachedCriteria.LEFT_JOIN);
			DetachedCriteria masterLogAccount2b = masterLogTagAccount2b.createCriteria("masterLoginNo", "masterLoginNo2b", DetachedCriteria.LEFT_JOIN);
		DetachedCriteria gpaccount2 = paccount2.createCriteria("amtbAccount", "gpacct2", DetachedCriteria.LEFT_JOIN);
			DetachedCriteria masterLogTagAccount2c = gpaccount2.createCriteria("ittbCpMasterTagAcct", "ittbCpMasterTagAcct2c", DetachedCriteria.LEFT_JOIN);
			DetachedCriteria masterLogAccount2c = masterLogTagAccount2c.createCriteria("masterLoginNo", "masterLoginNo2c", DetachedCriteria.LEFT_JOIN);
		
		if(userLoginId!=null && userLoginId.length()>0)
			login.add(Restrictions.ilike("comp_id.loginId", userLoginId, MatchMode.ANYWHERE));
		if(cardNo!=null && cardNo.length()>0)
			product.add(Restrictions.ilike("cardNo", cardNo, MatchMode.START));
		if(contactPersonId!=null)
			contactPerson.add(Restrictions.eq("contactPersonNo", contactPersonId));
		if(contactPersonName!=null && contactPersonName.length()>0)
			contactPerson.add(Restrictions.ilike("mainContactName", contactPersonName, MatchMode.ANYWHERE));
		if(accountNo!=null){
			Disjunction disjunction = Restrictions.disjunction();
			disjunction.add(Restrictions.eq("acct.custNo", accountNo.toString()));
			disjunction.add(Restrictions.eq("pacct.custNo", accountNo.toString()));
			disjunction.add(Restrictions.eq("gpacct.custNo", accountNo.toString()));
			disjunction.add(Restrictions.eq("acct2.custNo", accountNo.toString()));
			disjunction.add(Restrictions.eq("pacct2.custNo", accountNo.toString()));
			disjunction.add(Restrictions.eq("gpacct2.custNo", accountNo.toString()));
			login.add(disjunction);
		}
		if(accountName!=null && accountName.length()>0){
			Disjunction disjunction = Restrictions.disjunction();
			disjunction.add(Restrictions.ilike("acct.accountName", accountName, MatchMode.ANYWHERE));
			disjunction.add(Restrictions.ilike("pacct.accountName", accountName, MatchMode.ANYWHERE));
			disjunction.add(Restrictions.ilike("gpacct.accountName", accountName, MatchMode.ANYWHERE));
			disjunction.add(Restrictions.ilike("acct2.accountName", accountName, MatchMode.ANYWHERE));
			disjunction.add(Restrictions.ilike("pacct2.accountName", accountName, MatchMode.ANYWHERE));
			disjunction.add(Restrictions.ilike("gpacct2.accountName", accountName, MatchMode.ANYWHERE));
			login.add(disjunction);
		}
		if(masterLoginId!=null && masterLoginId.length()>0){
			Disjunction disjunction = Restrictions.disjunction();
			disjunction.add(Restrictions.ilike("masterLoginNo1a.loginId", masterLoginId, MatchMode.ANYWHERE));
			disjunction.add(Restrictions.ilike("masterLoginNo1b.loginId", masterLoginId, MatchMode.ANYWHERE));
			disjunction.add(Restrictions.ilike("masterLoginNo1c.loginId", masterLoginId, MatchMode.ANYWHERE));
			disjunction.add(Restrictions.ilike("masterLoginNo2a.loginId", masterLoginId, MatchMode.ANYWHERE));
			disjunction.add(Restrictions.ilike("masterLoginNo2b.loginId", masterLoginId, MatchMode.ANYWHERE));
			disjunction.add(Restrictions.ilike("masterLoginNo2c.loginId", masterLoginId, MatchMode.ANYWHERE));
			login.add(disjunction);
		}
		List results = this.findDefaultMaxResultByCriteria(login);
		return results;
	}
	@SuppressWarnings({"unchecked","unused"})
	public List<IttbCpLoginNew> searchNewPortalUser(String userLoginId, String cardNo,
			Integer contactPersonId, String contactPersonName, Integer accountNo, String accountName, String masterLoginId, String accessId){
		
		DetachedCriteria login = DetachedCriteria.forClass(IttbCpLoginNew.class);
		DetachedCriteria product = login.createCriteria("pmtbProduct", DetachedCriteria.LEFT_JOIN);
		DetachedCriteria contactPerson = login.createCriteria("amtbContactPerson", DetachedCriteria.LEFT_JOIN);
		
		DetachedCriteria account = product.createCriteria("amtbAccount", "acct", DetachedCriteria.LEFT_JOIN);
			DetachedCriteria masterLogTagAccount1a = account.createCriteria("ittbCpMasterTagAcct", "ittbCpMasterTagAcct1a", DetachedCriteria.LEFT_JOIN);
			DetachedCriteria masterLogAccount1a = masterLogTagAccount1a.createCriteria("masterLoginNo", "masterLoginNo1a", DetachedCriteria.LEFT_JOIN);
		DetachedCriteria paccount = account.createCriteria("amtbAccount", "pacct", DetachedCriteria.LEFT_JOIN);
			DetachedCriteria masterLogTagAccount1b = paccount.createCriteria("ittbCpMasterTagAcct", "ittbCpMasterTagAcct1b", DetachedCriteria.LEFT_JOIN);
			DetachedCriteria masterLogAccount1b = masterLogTagAccount1b.createCriteria("masterLoginNo", "masterLoginNo1b", DetachedCriteria.LEFT_JOIN);
		DetachedCriteria gpaccount = paccount.createCriteria("amtbAccount", "gpacct", DetachedCriteria.LEFT_JOIN);
			DetachedCriteria masterLogTagAccount1c = gpaccount.createCriteria("ittbCpMasterTagAcct", "ittbCpMasterTagAcct1c", DetachedCriteria.LEFT_JOIN);
			DetachedCriteria masterLogAccount1c = masterLogTagAccount1c.createCriteria("masterLoginNo", "masterLoginNo1c", DetachedCriteria.LEFT_JOIN);
		
		DetachedCriteria account2 = contactPerson.createCriteria("amtbAccount", "acct2", DetachedCriteria.LEFT_JOIN);
			DetachedCriteria masterLogTagAccount2a = account2.createCriteria("ittbCpMasterTagAcct", "ittbCpMasterTagAcct2a", DetachedCriteria.LEFT_JOIN);
			DetachedCriteria masterLogAccount2a = masterLogTagAccount2a.createCriteria("masterLoginNo", "masterLoginNo2a", DetachedCriteria.LEFT_JOIN);
		DetachedCriteria paccount2 = account2.createCriteria("amtbAccount", "pacct2", DetachedCriteria.LEFT_JOIN);
			DetachedCriteria masterLogTagAccount2b = paccount2.createCriteria("ittbCpMasterTagAcct", "ittbCpMasterTagAcct2b", DetachedCriteria.LEFT_JOIN);
			DetachedCriteria masterLogAccount2b = masterLogTagAccount2b.createCriteria("masterLoginNo", "masterLoginNo2b", DetachedCriteria.LEFT_JOIN);
		DetachedCriteria gpaccount2 = paccount2.createCriteria("amtbAccount", "gpacct2", DetachedCriteria.LEFT_JOIN);
			DetachedCriteria masterLogTagAccount2c = gpaccount2.createCriteria("ittbCpMasterTagAcct", "ittbCpMasterTagAcct2c", DetachedCriteria.LEFT_JOIN);
			DetachedCriteria masterLogAccount2c = masterLogTagAccount2c.createCriteria("masterLoginNo", "masterLoginNo2c", DetachedCriteria.LEFT_JOIN);
		
		if(userLoginId!=null && userLoginId.length()>0)
			login.add(Restrictions.eq("loginId", userLoginId));
		if(accessId!=null && accessId.length()>0)
			login.add(Restrictions.ilike("accessId", accessId, MatchMode.ANYWHERE));
		if(cardNo!=null && cardNo.length()>0)
			product.add(Restrictions.ilike("cardNo", cardNo, MatchMode.START));
		if(contactPersonId!=null)
			contactPerson.add(Restrictions.eq("contactPersonNo", contactPersonId));
		if(contactPersonName!=null && contactPersonName.length()>0)
			contactPerson.add(Restrictions.ilike("mainContactName", contactPersonName, MatchMode.ANYWHERE));
		if(accountNo!=null){
			Disjunction disjunction = Restrictions.disjunction();
			disjunction.add(Restrictions.eq("acct.custNo", accountNo.toString()));
			disjunction.add(Restrictions.eq("pacct.custNo", accountNo.toString()));
			disjunction.add(Restrictions.eq("gpacct.custNo", accountNo.toString()));
			disjunction.add(Restrictions.eq("acct2.custNo", accountNo.toString()));
			disjunction.add(Restrictions.eq("pacct2.custNo", accountNo.toString()));
			disjunction.add(Restrictions.eq("gpacct2.custNo", accountNo.toString()));
			login.add(disjunction);
		}
		if(accountName!=null && accountName.length()>0){
			Disjunction disjunction = Restrictions.disjunction();
			disjunction.add(Restrictions.ilike("acct.accountName", accountName, MatchMode.ANYWHERE));
			disjunction.add(Restrictions.ilike("pacct.accountName", accountName, MatchMode.ANYWHERE));
			disjunction.add(Restrictions.ilike("gpacct.accountName", accountName, MatchMode.ANYWHERE));
			disjunction.add(Restrictions.ilike("acct2.accountName", accountName, MatchMode.ANYWHERE));
			disjunction.add(Restrictions.ilike("pacct2.accountName", accountName, MatchMode.ANYWHERE));
			disjunction.add(Restrictions.ilike("gpacct2.accountName", accountName, MatchMode.ANYWHERE));
			login.add(disjunction);
		}
		if(masterLoginId!=null && masterLoginId.length()>0){
			Disjunction disjunction = Restrictions.disjunction();
			disjunction.add(Restrictions.ilike("masterLoginNo1a.loginId", masterLoginId, MatchMode.ANYWHERE));
			disjunction.add(Restrictions.ilike("masterLoginNo1b.loginId", masterLoginId, MatchMode.ANYWHERE));
			disjunction.add(Restrictions.ilike("masterLoginNo1c.loginId", masterLoginId, MatchMode.ANYWHERE));
			disjunction.add(Restrictions.ilike("masterLoginNo2a.loginId", masterLoginId, MatchMode.ANYWHERE));
			disjunction.add(Restrictions.ilike("masterLoginNo2b.loginId", masterLoginId, MatchMode.ANYWHERE));
			disjunction.add(Restrictions.ilike("masterLoginNo2c.loginId", masterLoginId, MatchMode.ANYWHERE));
			login.add(disjunction);
		}
		List results = this.findDefaultMaxResultByCriteria(login);
		return results;
	}
	@SuppressWarnings("unchecked")
	public List<IttbCpLogin> getPortalUser(BigDecimal productNo){
		DetachedCriteria login = DetachedCriteria.forClass(IttbCpLogin.class);
		DetachedCriteria product = login.createCriteria("pmtbProduct", DetachedCriteria.LEFT_JOIN);
		
		product.add(Restrictions.idEq(productNo));
		
		return this.findAllByCriteria(login);
	}
	@SuppressWarnings("unchecked")
	public List<IttbCpLoginNew> getPortalUserNew(BigDecimal productNo){
		DetachedCriteria login = DetachedCriteria.forClass(IttbCpLoginNew.class);
		DetachedCriteria product = login.createCriteria("pmtbProduct", DetachedCriteria.LEFT_JOIN);
		
		product.add(Restrictions.idEq(productNo));
		
		return this.findAllByCriteria(login);
	}
	@SuppressWarnings("unchecked")
	public List<IttbCpCustCardIssuance> getCustomerCardIssuance(String cardNo){
		DetachedCriteria cardIssuance = DetachedCriteria.forClass(IttbCpCustCardIssuance.class);
		cardIssuance.add(Restrictions.eq("cardNo", cardNo));
		cardIssuance.addOrder(Order.asc("issuedOn"));
		return this.findAllByCriteria(cardIssuance);
	}

	@SuppressWarnings("unchecked")
	public List<IttbCpMasterLogin> getCpMasterLogin(String masterLoginNo){
		DetachedCriteria requestCriteria = DetachedCriteria.forClass(IttbCpMasterLogin.class);
		requestCriteria.add(Restrictions.ilike("loginId",  masterLoginNo, MatchMode.ANYWHERE));
		return findAllByCriteria(requestCriteria);
	}
	@SuppressWarnings("unchecked")
	public IttbCpMasterLogin getCpMasterLogin(Integer masterLoginNo){
		DetachedCriteria requestCriteria = DetachedCriteria.forClass(IttbCpMasterLogin.class);
		DetachedCriteria TagCriteria = requestCriteria.createCriteria("ittbCpMasterTagAcct", DetachedCriteria.LEFT_JOIN);
		requestCriteria.add(Restrictions.eq("masterLoginNo",  masterLoginNo));
		
		List <IttbCpMasterLogin> results = this.findAllByCriteria(requestCriteria);
		
		if(results.isEmpty()) return null;
		else return results.get(0);
	}
	@SuppressWarnings("unchecked")
	public IttbCpMasterTagAcct getMasterLoginTagAcct(Integer masterLoginNo, String acctName, Integer acctNo){
		DetachedCriteria requestCriteria = DetachedCriteria.forClass(IttbCpMasterTagAcct.class);
		DetachedCriteria mlCriteria = requestCriteria.createCriteria("masterLoginNo", DetachedCriteria.LEFT_JOIN);
		requestCriteria.add(Restrictions.sqlRestriction("ittbcpmast1_.master_Login_No = '"+masterLoginNo+"'"));
		requestCriteria.add(Restrictions.eq("accountNo",acctNo));
		requestCriteria.add(Restrictions.eq("accountName",acctName));
		
		List <IttbCpMasterTagAcct> results = this.findAllByCriteria(requestCriteria);
		
		if(results.isEmpty()) return null;
		else return results.get(0);
	}
	@SuppressWarnings("unchecked")
	public List<IttbCpMasterTagAcct> getMasterLoginTagAcct(Integer masterLoginNo){
		DetachedCriteria requestCriteria = DetachedCriteria.forClass(IttbCpMasterTagAcct.class);
		DetachedCriteria mlCriteria = requestCriteria.createCriteria("masterLoginNo", DetachedCriteria.LEFT_JOIN);
		requestCriteria.add(Restrictions.sqlRestriction("ittbcpmast1_.master_Login_No = '"+masterLoginNo+"'"));
		
		return this.findAllByCriteria(requestCriteria);
	}
	@SuppressWarnings("unchecked")
	public IttbCpMasterLogin getCpMasterLoginById(String loginId){
		DetachedCriteria requestCriteria = DetachedCriteria.forClass(IttbCpMasterLogin.class);
//		requestCriteria.add(Restrictions.eq("loginId",  loginId));
		requestCriteria.add(Restrictions.sqlRestriction("upper(login_Id) = upper('"+loginId+"')"));
		List <IttbCpMasterLogin> results = this.findAllByCriteria(requestCriteria);
		
		if(results.isEmpty()) return null;
		else return results.get(0);
	}
}