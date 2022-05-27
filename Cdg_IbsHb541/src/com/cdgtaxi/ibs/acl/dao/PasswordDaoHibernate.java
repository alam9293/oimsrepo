package com.cdgtaxi.ibs.acl.dao;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.cdgtaxi.ibs.acl.Constants;
import com.cdgtaxi.ibs.acl.model.SatbPassword;
import com.cdgtaxi.ibs.acl.model.SatbUser;
import com.cdgtaxi.ibs.common.dao.GenericDaoHibernate;

public class PasswordDaoHibernate extends GenericDaoHibernate implements PasswordDao{

	PasswordDaoHibernate() {
		super();
		// TODO Auto-generated constructor stub
	}

	public List<SatbPassword> getPreviousUsedPasswords(SatbUser user){
		DetachedCriteria passwordCriteria = DetachedCriteria.forClass(SatbPassword.class);
		passwordCriteria.add(Restrictions.eq("aclUser", user));
		passwordCriteria.addOrder(Order.desc("time"));
		return this.findMaxResultByCriteria(passwordCriteria, Constants.PASSWORD_HISTORY);
	}
	
}
