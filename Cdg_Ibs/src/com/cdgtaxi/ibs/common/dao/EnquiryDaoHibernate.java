package com.cdgtaxi.ibs.common.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import com.cdgtaxi.ibs.interfaces.as.model.AsvwProduct;
import com.cdgtaxi.ibs.interfaces.as.model.IttbAsMasterReq;

public class EnquiryDaoHibernate extends GenericDaoHibernate implements EnquiryDao {
	
	public AsvwProduct getAsvwProduct(String cardNo)
	{
		DetachedCriteria asvwProductCriteria = DetachedCriteria.forClass(AsvwProduct.class);
		// 2 levels maximum
		DetachedCriteria asvwAccountCriteria = asvwProductCriteria.createCriteria("asvwAccount", Criteria.LEFT_JOIN);
		DetachedCriteria parentAccountCriteria = asvwAccountCriteria.createCriteria("asvwAccount", Criteria.LEFT_JOIN);
		parentAccountCriteria.createCriteria("asvwAccount", Criteria.LEFT_JOIN);
		asvwProductCriteria.createCriteria("asvwProductExpiryDates", Criteria.LEFT_JOIN);
		asvwProductCriteria.createCriteria("asvwProductType", Criteria.LEFT_JOIN);
		asvwProductCriteria.add(Restrictions.eq("cardNo", cardNo));
		
		List<AsvwProduct> results = findAllByCriteria(asvwProductCriteria);
		if (!results.isEmpty())
			return results.get(0);
		else
			return null;
	}
	
	public boolean isExternalProductRequestExist(String cardNo){
		DetachedCriteria criteria = DetachedCriteria.forClass(IttbAsMasterReq.class);
		DetachedCriteria detailCriteria = criteria.createCriteria("ittbAsAddNegProdReq", DetachedCriteria.LEFT_JOIN);
		
		criteria.add(Restrictions.eq("reqStatus", IttbAsMasterReq.PENDING));
		detailCriteria.add(Restrictions.eq("cardNo", cardNo));
		
		if(this.findAllByCriteria(criteria).isEmpty()) return false;
		else return true;
	}
}
