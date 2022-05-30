package com.cdgtaxi.ibs.common.dao;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import com.cdgtaxi.ibs.common.model.AmtbAccount;
import com.cdgtaxi.ibs.common.model.FmtbBankCode;
import com.cdgtaxi.ibs.common.model.FmtbEntityMaster;
import com.cdgtaxi.ibs.util.DateUtil;

public class BankCodeDaoHibernate extends GenericDaoHibernate implements BankCodeDao {
	private static Logger logger = Logger.getLogger(BankCodeDaoHibernate.class);
	
	@SuppressWarnings("unchecked")
	public List<Object[]> getBankInBanks(AmtbAccount account){
		DetachedCriteria bankCodeCriteria = DetachedCriteria.forClass(FmtbBankCode.class);
		DetachedCriteria entityMasterCriteria = bankCodeCriteria.createCriteria("fmtbEntityMaster", DetachedCriteria.INNER_JOIN);
		DetachedCriteria arContCodeCriteria = entityMasterCriteria.createCriteria("fmtbArContCodeMasters", DetachedCriteria.INNER_JOIN);
		
		arContCodeCriteria.add(Restrictions.idEq(account.getFmtbArContCodeMaster().getArControlCodeNo()));
		bankCodeCriteria.add(Restrictions.le("effectiveDate", DateUtil.getCurrentDate()));
		bankCodeCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		
		ProjectionList projectionList = Projections.projectionList();
		projectionList.add(Projections.property("bankCode"));
		projectionList.add(Projections.property("branchCode"));
		projectionList.add(Projections.property("fmtbEntityMaster"));
		bankCodeCriteria.setProjection(Projections.distinct(projectionList));
		
		return this.findAllByCriteria(bankCodeCriteria);
	}
	
	public FmtbBankCode getLatestBankCode(String bankCode, String branchCode, FmtbEntityMaster entity){
		DetachedCriteria bankCodeCriteria = DetachedCriteria.forClass(FmtbBankCode.class);
		bankCodeCriteria.add(Restrictions.eq("bankCode", bankCode));
		bankCodeCriteria.add(Restrictions.eq("branchCode", branchCode));
		bankCodeCriteria.add(Restrictions.eq("fmtbEntityMaster", entity));
		bankCodeCriteria.add(Restrictions.le("effectiveDate", DateUtil.getCurrentDate()));
		
		bankCodeCriteria.addOrder(Order.desc("effectiveDate"));
		
		List results = this.findMaxResultByCriteria(bankCodeCriteria, 1);
		return results.isEmpty() ? null : (FmtbBankCode)results.get(0);
	}
}
