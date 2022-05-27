package com.cdgtaxi.ibs.common.dao;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.Subqueries;

import com.cdgtaxi.ibs.acl.Constants;
import com.cdgtaxi.ibs.common.model.FmtbEntityMaster;
import com.cdgtaxi.ibs.common.model.FmtbTaxCode;
import com.cdgtaxi.ibs.common.model.FmtbTransactionCode;
import com.cdgtaxi.ibs.common.model.PmtbProductType;
import com.cdgtaxi.ibs.util.DateUtil;
import com.google.common.base.Strings;

public class FinanceDaoHibernate extends GenericDaoHibernate implements FinanceDao {
	private final static String FARE = "FA";
	private final static String ADMIN = "AF";

	@SuppressWarnings("unchecked")
	public List<FmtbTransactionCode> getEffectiveManualTxnCodes(int entityNo) {
		DetachedCriteria subQuery = DetachedCriteria.forClass(FmtbTransactionCode.class, "subQuery");
		DetachedCriteria subQueryFmtbQuery = subQuery.createCriteria("fmtbEntityMaster", org.hibernate.sql.JoinType.LEFT_OUTER_JOIN);

		subQueryFmtbQuery.add(Restrictions.eq("entityNo", entityNo));
		subQuery.add(Restrictions.eq("isManual", Constants.BOOLEAN_YES));
		subQuery.add(Restrictions.le("effectiveDate", new Date()));
		subQuery.add(Restrictions.eqProperty("txnCode", "mainQuery.txnCode"));

		subQuery.setProjection(Projections.max("effectiveDate"));

		DetachedCriteria mainQuery = DetachedCriteria.forClass(FmtbTransactionCode.class, "mainQuery");
		DetachedCriteria fmtbQuery = mainQuery.createCriteria("fmtbEntityMaster", org.hibernate.sql.JoinType.LEFT_OUTER_JOIN);
		fmtbQuery.createCriteria("mstbMasterTable", org.hibernate.sql.JoinType.LEFT_OUTER_JOIN);
		mainQuery.createCriteria("mstbMasterTable", org.hibernate.sql.JoinType.LEFT_OUTER_JOIN);
		mainQuery.createCriteria("pmtbProductType", org.hibernate.sql.JoinType.LEFT_OUTER_JOIN);

		fmtbQuery.add(Restrictions.eq("entityNo", entityNo));

		mainQuery.add(Subqueries.propertyEq("effectiveDate", subQuery));

		mainQuery.addOrder(Order.asc("description"));
		mainQuery.addOrder(Order.desc("effectiveDate"));

		return findAllByCriteria(mainQuery);
	}

	@SuppressWarnings("unchecked")
	public FmtbTaxCode getEffectiveTxnCode(int entityNo, Integer taxType) {
		DetachedCriteria criteria = DetachedCriteria.forClass(FmtbTaxCode.class);
		DetachedCriteria txnTypeCriteria = criteria.createCriteria("mstbMasterTable", org.hibernate.sql.JoinType.LEFT_OUTER_JOIN);
		DetachedCriteria entityCriteria = criteria.createCriteria("fmtbEntityMaster", org.hibernate.sql.JoinType.LEFT_OUTER_JOIN);

		txnTypeCriteria.add(Restrictions.eq("masterNo", taxType));
		criteria.add(Restrictions.le("effectiveDate", DateUtil.getCurrentTimestamp()));
		entityCriteria.add(Restrictions.idEq(entityNo));

		criteria.addOrder(Order.desc("effectiveDate"));
		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);

		List<FmtbTaxCode> list = findAllByCriteria(criteria);
		if (list.size() > 0) {
			return list.get(0);
		} else {
			return null;
		}
	}

	public FmtbTransactionCode getFmtbTransactionCode(PmtbProductType productType, FmtbEntityMaster entity) {
		DetachedCriteria fmtbTxnCodeCriteria = DetachedCriteria.forClass(FmtbTransactionCode.class);
		fmtbTxnCodeCriteria.add(Restrictions.eq("fmtbEntityMaster", entity));
		fmtbTxnCodeCriteria.add(Restrictions.eq("isManual", Constants.BOOLEAN_NO));
		fmtbTxnCodeCriteria.add(Restrictions.le("effectiveDate", DateUtil.getCurrentDate()));
		fmtbTxnCodeCriteria.add(Restrictions.eq("txnType", FARE));
		fmtbTxnCodeCriteria.add(Restrictions.eq("pmtbProductType", productType));

		fmtbTxnCodeCriteria.addOrder(Order.desc("effectiveDate"));
		List<FmtbTransactionCode> results = findAllByCriteria(fmtbTxnCodeCriteria);
		if (results.isEmpty())
			return null;
		else
			return results.get(0);
	}

	public BigDecimal getLatestGST(Integer entityNo, String productTypeId, Timestamp tripDt) {
		DetachedCriteria fmtbTxnCodeCriteria = DetachedCriteria.forClass(FmtbTransactionCode.class);
		DetachedCriteria productTypeCriteria = fmtbTxnCodeCriteria.createCriteria("pmtbProductType", Criteria.LEFT_JOIN);
		DetachedCriteria fmtbEntityMasterCriteria = fmtbTxnCodeCriteria.createCriteria("fmtbEntityMaster", Criteria.LEFT_JOIN);
		fmtbTxnCodeCriteria.createCriteria("mstbMasterTable", Criteria.LEFT_JOIN);
		fmtbTxnCodeCriteria.add(Restrictions.le("effectiveDate", tripDt));
		fmtbTxnCodeCriteria.add(Restrictions.eq("txnType", ADMIN));

		productTypeCriteria.add(Restrictions.eq("productTypeId", productTypeId));
		fmtbEntityMasterCriteria.add(Restrictions.eq("entityNo", entityNo));
		fmtbTxnCodeCriteria.addOrder(Order.desc("effectiveDate"));
		List<FmtbTransactionCode> results = findAllByCriteria(fmtbTxnCodeCriteria);
		if (results.isEmpty())
			return null;
		else {
			DetachedCriteria fmtbTaxCodeCriteria = DetachedCriteria.forClass(FmtbTaxCode.class);
			DetachedCriteria mstbMasterTableCriteria = fmtbTaxCodeCriteria.createCriteria("mstbMasterTable", Criteria.LEFT_JOIN);
			DetachedCriteria taxCodeEntityCriteria = fmtbTaxCodeCriteria.createCriteria("fmtbEntityMaster", Criteria.LEFT_JOIN);
			taxCodeEntityCriteria.add(Restrictions.eq("entityNo", entityNo));
			fmtbTaxCodeCriteria.add(Restrictions.le("effectiveDate", tripDt));
			mstbMasterTableCriteria.add(Restrictions.eq("masterNo", results.get(0).getMstbMasterTable().getMasterNo()));
			fmtbTaxCodeCriteria.addOrder(Order.desc("effectiveDate"));
			List<FmtbTaxCode> taxResults = findAllByCriteria(fmtbTaxCodeCriteria);
			if (taxResults.isEmpty())
				return null;
			else
				return taxResults.get(0).getTaxRate();
		}
	}

	public BigDecimal getLatestGST(Integer entityNo, String productTypeId, Timestamp tripDt, String txnType) {
		DetachedCriteria fmtbTxnCodeCriteria = DetachedCriteria.forClass(FmtbTransactionCode.class);
		DetachedCriteria productTypeCriteria = fmtbTxnCodeCriteria.createCriteria("pmtbProductType", Criteria.LEFT_JOIN);
		DetachedCriteria fmtbEntityMasterCriteria = fmtbTxnCodeCriteria.createCriteria("fmtbEntityMaster", Criteria.LEFT_JOIN);
		fmtbTxnCodeCriteria.createCriteria("mstbMasterTable", Criteria.LEFT_JOIN);
		fmtbTxnCodeCriteria.add(Restrictions.le("effectiveDate", tripDt));
		fmtbTxnCodeCriteria.add(Restrictions.eq("txnType", txnType));

		if (!Strings.isNullOrEmpty(productTypeId)) {
			productTypeCriteria.add(Restrictions.eq("productTypeId", productTypeId));
		}

		fmtbEntityMasterCriteria.add(Restrictions.eq("entityNo", entityNo));
		fmtbTxnCodeCriteria.addOrder(Order.desc("effectiveDate"));
		@SuppressWarnings("unchecked")
		List<FmtbTransactionCode> results = findAllByCriteria(fmtbTxnCodeCriteria);
		if (results.isEmpty()) {
			return null;
		} else {
			DetachedCriteria fmtbTaxCodeCriteria = DetachedCriteria.forClass(FmtbTaxCode.class);
			DetachedCriteria mstbMasterTableCriteria = fmtbTaxCodeCriteria.createCriteria("mstbMasterTable", Criteria.LEFT_JOIN);
			DetachedCriteria taxCodeEntityCriteria = fmtbTaxCodeCriteria.createCriteria("fmtbEntityMaster", Criteria.LEFT_JOIN);
			taxCodeEntityCriteria.add(Restrictions.eq("entityNo", entityNo));
			fmtbTaxCodeCriteria.add(Restrictions.le("effectiveDate", tripDt));
			mstbMasterTableCriteria.add(Restrictions.eq("masterNo", results.get(0).getMstbMasterTable().getMasterNo()));
			fmtbTaxCodeCriteria.addOrder(Order.desc("effectiveDate"));
			@SuppressWarnings("unchecked")
			List<FmtbTaxCode> taxResults = findAllByCriteria(fmtbTaxCodeCriteria);
			if (taxResults.isEmpty())
				return null;
			else
				return taxResults.get(0).getTaxRate();
		}
	}

	public BigDecimal getLatestGST(Integer entityNo, Timestamp tripDt, String txnCode) {
		DetachedCriteria fmtbTxnCodeCriteria = DetachedCriteria.forClass(FmtbTransactionCode.class);
		DetachedCriteria fmtbEntityMasterCriteria = fmtbTxnCodeCriteria.createCriteria("fmtbEntityMaster", Criteria.LEFT_JOIN);
		fmtbTxnCodeCriteria.createCriteria("mstbMasterTable", Criteria.LEFT_JOIN);
		fmtbTxnCodeCriteria.add(Restrictions.le("effectiveDate", tripDt));
		fmtbTxnCodeCriteria.add(Restrictions.eq("txnCode", txnCode));

		fmtbEntityMasterCriteria.add(Restrictions.eq("entityNo", entityNo));
		fmtbTxnCodeCriteria.addOrder(Order.desc("effectiveDate"));
		List<FmtbTransactionCode> results = findAllByCriteria(fmtbTxnCodeCriteria);
		if (results.isEmpty())
			return null;
		else {
			DetachedCriteria fmtbTaxCodeCriteria = DetachedCriteria.forClass(FmtbTaxCode.class);
			DetachedCriteria mstbMasterTableCriteria = fmtbTaxCodeCriteria.createCriteria("mstbMasterTable", Criteria.LEFT_JOIN);
			DetachedCriteria taxCodeEntityCriteria = fmtbTaxCodeCriteria.createCriteria("fmtbEntityMaster", Criteria.LEFT_JOIN);
			taxCodeEntityCriteria.add(Restrictions.eq("entityNo", entityNo));
			fmtbTaxCodeCriteria.add(Restrictions.le("effectiveDate", tripDt));
			mstbMasterTableCriteria.add(Restrictions.eq("masterNo", results.get(0).getMstbMasterTable().getMasterNo()));
			logger.info("Master No : " + results.get(0).getMstbMasterTable().getMasterNo());
			fmtbTaxCodeCriteria.addOrder(Order.desc("effectiveDate"));
			List<FmtbTaxCode> taxResults = findAllByCriteria(fmtbTaxCodeCriteria);
			if (taxResults.isEmpty())
				return null;
			else {
				logger.info("Tax Rate " + taxResults.get(0).getTaxRate());
				return taxResults.get(0).getTaxRate();
			}

		}
	}
}
