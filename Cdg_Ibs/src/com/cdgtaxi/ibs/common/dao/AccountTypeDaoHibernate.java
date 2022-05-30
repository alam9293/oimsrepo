package com.cdgtaxi.ibs.common.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import com.cdgtaxi.ibs.common.model.AmtbAcctType;

public class AccountTypeDaoHibernate extends GenericDaoHibernate implements AccountTypeDao{
	/**
	 * method to extract all account types of a category
	 * @param category - the selected category
	 * @return a list containing the account types that match the category
	 */
	@SuppressWarnings("unchecked")
	public List<AmtbAcctType> getAccountTypes(String template){
		DetachedCriteria accountTypeCriteria = DetachedCriteria.forClass(AmtbAcctType.class);
		DetachedCriteria productTypeCriteria = accountTypeCriteria.createCriteria("pmtbProductTypes", DetachedCriteria.LEFT_JOIN);
		productTypeCriteria.createCriteria("mstbMasterTableBySMSFormat", DetachedCriteria.LEFT_JOIN);
		accountTypeCriteria.add(Restrictions.eq("acctTemplate", template));
		accountTypeCriteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		return this.findAllByCriteria(accountTypeCriteria);
	}
	/**
	 * method to extract one account type with the matching id
	 * @param accountTypeNo - the account type number
	 * @return the list with the matching account type
	 */
	@SuppressWarnings("unchecked")
	public List<AmtbAcctType> getAccountType(Integer accountTypeNo) {
		DetachedCriteria accountTypeCriteria = DetachedCriteria.forClass(AmtbAcctType.class);
		accountTypeCriteria.createCriteria("pmtbProductTypes", DetachedCriteria.LEFT_JOIN);
		accountTypeCriteria.add(Restrictions.idEq(accountTypeNo));
		return this.findAllByCriteria(accountTypeCriteria);
	}
	/**
	 * method to extract all account types
	 * @return a list containing the account types
	 */
	@SuppressWarnings("unchecked")
	public List<AmtbAcctType> getAllAccountTypes(){
		DetachedCriteria accountTypeCriteria = DetachedCriteria.forClass(AmtbAcctType.class);
		return this.findAllByCriteria(accountTypeCriteria);
	}
}