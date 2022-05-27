package com.cdgtaxi.ibs.common.dao;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.cdgtaxi.ibs.common.NonConfigurableConstants;
import com.cdgtaxi.ibs.common.model.AmtbAccount;
import com.cdgtaxi.ibs.common.model.BmtbBillGenError;
import com.cdgtaxi.ibs.common.model.BmtbBillGenReq;

public class BillGenErrorDaoHibernate extends GenericDaoHibernate implements BillGenErrorDao{
	
	/**
	 * To check whether the corporate encounters error in any bill gen request and still not resolve 
	 * @param topLevelAccount The corporate account
	 * @return true if there are error found, otherwise false
	 */
	public boolean checkForError(AmtbAccount topLevelAccount){
		DetachedCriteria criteria = DetachedCriteria.forClass(BmtbBillGenError.class);
		DetachedCriteria requestCriteria = criteria.createCriteria("bmtbBillGenReq", "request", DetachedCriteria.INNER_JOIN);
		DetachedCriteria setupCriteria = requestCriteria.createCriteria("bmtbBillGenSetupBySetupNo", DetachedCriteria.INNER_JOIN);
		criteria.add(Restrictions.eq("amtbAccountByTopLevelAccountNo", topLevelAccount));
		criteria.add(Restrictions.ne("request.status", NonConfigurableConstants.BILL_GEN_REQUEST_STATUS_REGENERATED));
		setupCriteria.add(Restrictions.ne("setupNo", NonConfigurableConstants.BILL_GEN_SETUP_DRAFT));
		List results = this.findAllByCriteria(criteria);
		if(results.isEmpty()) return false;
		else return true;
	}
	
	public List<BmtbBillGenError> get(BmtbBillGenReq billGenRequest){
		DetachedCriteria criteria = DetachedCriteria.forClass(BmtbBillGenError.class);
		criteria.createCriteria("amtbAccountByTopLevelAccountNo", "topLevelAccount", DetachedCriteria.INNER_JOIN);
		criteria.createCriteria("amtbAccountByBillableAccountNo", "billableAccount", DetachedCriteria.INNER_JOIN);
		
		criteria.add(Restrictions.eq("bmtbBillGenReq", billGenRequest));
		
		criteria.addOrder(Order.asc("topLevelAccount.accountNo"));
		criteria.addOrder(Order.asc("billableAccount.accountName"));
		
		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		
		return this.findAllByCriteria(criteria);
	}
}