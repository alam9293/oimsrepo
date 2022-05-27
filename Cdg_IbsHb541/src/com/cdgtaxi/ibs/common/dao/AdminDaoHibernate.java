package com.cdgtaxi.ibs.common.dao;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.FetchMode;
import org.hibernate.Session;
import org.hibernate.criterion.Conjunction;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.Subqueries;
import org.hibernate.query.Query;
import org.hibernate.resource.transaction.spi.TransactionStatus;
import org.hibernate.type.StandardBasicTypes;

import com.cdgtaxi.ibs.common.ConfigurableConstants;
import com.cdgtaxi.ibs.common.HibernateStoredSession;
import com.cdgtaxi.ibs.common.NonConfigurableConstants;
import com.cdgtaxi.ibs.common.model.AmtbAccount;
import com.cdgtaxi.ibs.common.model.AmtbApplication;
import com.cdgtaxi.ibs.common.model.AmtbBillReq;
import com.cdgtaxi.ibs.common.model.BmtbPaymentReceipt;
import com.cdgtaxi.ibs.common.model.FmtbArContCodeDetail;
import com.cdgtaxi.ibs.common.model.FmtbArContCodeMaster;
import com.cdgtaxi.ibs.common.model.FmtbBankCode;
import com.cdgtaxi.ibs.common.model.FmtbBankPaymentDetail;
import com.cdgtaxi.ibs.common.model.FmtbBankPaymentMaster;
import com.cdgtaxi.ibs.common.model.FmtbEntityMaster;
import com.cdgtaxi.ibs.common.model.FmtbGlLogHeader;
import com.cdgtaxi.ibs.common.model.FmtbNonBillableDetail;
import com.cdgtaxi.ibs.common.model.FmtbNonBillableMaster;
import com.cdgtaxi.ibs.common.model.FmtbTaxCode;
import com.cdgtaxi.ibs.common.model.FmtbTransactionCode;
import com.cdgtaxi.ibs.common.model.IttbGiroReq;
import com.cdgtaxi.ibs.common.model.IttbGiroReturnReq;
import com.cdgtaxi.ibs.common.model.IttbGiroSetup;
import com.cdgtaxi.ibs.common.model.IttbGiroUobHeader;
import com.cdgtaxi.ibs.common.model.IttbGovtEinvoiceReq;
import com.cdgtaxi.ibs.common.model.IttbPubbsReq;
import com.cdgtaxi.ibs.common.model.IttbRecurringDtl;
import com.cdgtaxi.ibs.common.model.IttbRecurringReq;
import com.cdgtaxi.ibs.common.model.LrtbRewardAccount;
import com.cdgtaxi.ibs.common.model.PmtbProductType;
import com.cdgtaxi.ibs.common.model.forms.SearchAcquirerForm;
import com.cdgtaxi.ibs.common.model.forms.SearchAcquirerMdrForm;
import com.cdgtaxi.ibs.common.model.forms.SearchAcquirerPaymentTypeForm;
import com.cdgtaxi.ibs.common.model.forms.SearchBankPaymentForm;
import com.cdgtaxi.ibs.common.model.forms.SearchGLBankForm;
import com.cdgtaxi.ibs.common.model.forms.SearchGiroRequestForm;
import com.cdgtaxi.ibs.common.model.forms.SearchGiroReturnRequestForm;
import com.cdgtaxi.ibs.common.model.forms.SearchGovtEInvRequestForm;
import com.cdgtaxi.ibs.common.model.forms.SearchNonBillableForm;
import com.cdgtaxi.ibs.common.model.forms.SearchPrepaidPromotionForm;
import com.cdgtaxi.ibs.common.model.forms.SearchPromoPlanHistoryForm;
import com.cdgtaxi.ibs.common.model.forms.SearchPromotionForm;
import com.cdgtaxi.ibs.common.model.forms.SearchPubbsRequestForm;
import com.cdgtaxi.ibs.common.model.forms.SearchRecurringRequestForm;
import com.cdgtaxi.ibs.common.model.forms.SearchTransactionCodeForm;
import com.cdgtaxi.ibs.master.model.MstbAcquirer;
import com.cdgtaxi.ibs.master.model.MstbAcquirerMdr;
import com.cdgtaxi.ibs.master.model.MstbAcquirerPymtType;
import com.cdgtaxi.ibs.master.model.MstbAdminFeeDetail;
import com.cdgtaxi.ibs.master.model.MstbAdminFeeMaster;
import com.cdgtaxi.ibs.master.model.MstbBankMaster;
import com.cdgtaxi.ibs.master.model.MstbBranchMaster;
import com.cdgtaxi.ibs.master.model.MstbCreditTermDetail;
import com.cdgtaxi.ibs.master.model.MstbCreditTermMaster;
import com.cdgtaxi.ibs.master.model.MstbEarlyPaymentDetail;
import com.cdgtaxi.ibs.master.model.MstbEarlyPaymentMaster;
import com.cdgtaxi.ibs.master.model.MstbInvoicePromo;
import com.cdgtaxi.ibs.master.model.MstbIssuanceFeeDetail;
import com.cdgtaxi.ibs.master.model.MstbIssuanceFeeMaster;
import com.cdgtaxi.ibs.master.model.MstbLatePaymentDetail;
import com.cdgtaxi.ibs.master.model.MstbLatePaymentMaster;
import com.cdgtaxi.ibs.master.model.MstbMasterTable;
import com.cdgtaxi.ibs.master.model.MstbProdDiscDetail;
import com.cdgtaxi.ibs.master.model.MstbProdDiscMaster;
import com.cdgtaxi.ibs.master.model.MstbPromoReq;
import com.cdgtaxi.ibs.master.model.MstbPromotion;
import com.cdgtaxi.ibs.master.model.MstbPromotionCashPlus;
import com.cdgtaxi.ibs.master.model.MstbSalesperson;
import com.cdgtaxi.ibs.master.model.MstbSubscFeeDetail;
import com.cdgtaxi.ibs.master.model.MstbSubscFeeMaster;
import com.cdgtaxi.ibs.master.model.MstbVolDiscDetail;
import com.cdgtaxi.ibs.master.model.MstbVolDiscMaster;
import com.cdgtaxi.ibs.util.DateUtil;
import com.google.common.base.Strings;

@SuppressWarnings("rawtypes")
public class AdminDaoHibernate extends GenericDaoHibernate implements AdminDao {
	
	public MstbVolDiscMaster getVolumeDiscountPlanWithDetails(Integer planNo) {
		DetachedCriteria planCriteria = DetachedCriteria.forClass(MstbVolDiscMaster.class);
		@SuppressWarnings("deprecation")
		DetachedCriteria planDetailCriteria = planCriteria.createCriteria("mstbVolDiscDetails", org.hibernate.sql.JoinType.LEFT_OUTER_JOIN);
		planDetailCriteria.createCriteria("mstbVolDiscTiers", org.hibernate.sql.JoinType.LEFT_OUTER_JOIN);
		planCriteria.add(Restrictions.idEq(planNo));

		List list = findAllByCriteria(planCriteria);
		if (list.size() > 0) {
			return (MstbVolDiscMaster) list.get(0);
		} else {
			return null;
		}
	}

	public MstbVolDiscDetail getVolumeDiscountPlanDetailWithTiers(
			Integer planDetailNo) {
		DetachedCriteria planCriteria = DetachedCriteria.forClass(MstbVolDiscDetail.class);
		planCriteria.createCriteria("mstbVolDiscTiers", org.hibernate.sql.JoinType.LEFT_OUTER_JOIN);
		planCriteria.createCriteria("mstbVolDiscMaster", org.hibernate.sql.JoinType.LEFT_OUTER_JOIN);
		planCriteria.add(Restrictions.idEq(planDetailNo));

		List list = findAllByCriteria(planCriteria);
		if (list.size() > 0) {
			return (MstbVolDiscDetail) list.get(0);
		} else {
			return null;
		}
	}

	public MstbProdDiscMaster getProductDiscountPlanWithDetails(Integer planNo) {
		DetachedCriteria planCriteria = DetachedCriteria.forClass(MstbProdDiscMaster.class);
		planCriteria.createCriteria("mstbProdDiscDetails", org.hibernate.sql.JoinType.LEFT_OUTER_JOIN);
		planCriteria.add(Restrictions.idEq(planNo));

		List list = findAllByCriteria(planCriteria);
		if (list.size() > 0) {
			return (MstbProdDiscMaster) list.get(0);
		} else {
			return null;
		}
	}

	public MstbProdDiscDetail getProductDiscountPlanDetail(Integer planDetailNo) {
		DetachedCriteria planCriteria = DetachedCriteria.forClass(MstbProdDiscDetail.class);
		planCriteria.setFetchMode("mstbProdDiscMaster", FetchMode.JOIN);
		planCriteria.add(Restrictions.idEq(planDetailNo));

		List list = findAllByCriteria(planCriteria);
		if (list.size() > 0) {
			return (MstbProdDiscDetail) list.get(0);
		} else {
			return null;
		}
	}

	public MstbAdminFeeMaster getAdminFeePlanWithDetails(Integer planNo) {
		DetachedCriteria planCriteria = DetachedCriteria.forClass(MstbAdminFeeMaster.class);
		planCriteria.createCriteria("mstbAdminFeeDetails", org.hibernate.sql.JoinType.LEFT_OUTER_JOIN);
		planCriteria.add(Restrictions.idEq(planNo));

		List list = findAllByCriteria(planCriteria);
		if (list.size() > 0) {
			return (MstbAdminFeeMaster) list.get(0);
		} else {
			return null;
		}
	}

	public MstbBankMaster getBankWithBranches(Integer bankNo) {
		DetachedCriteria criteria = DetachedCriteria.forClass(MstbBankMaster.class);
		
		DetachedCriteria branchCriteria = criteria.createCriteria("mstbBranchMasters", org.hibernate.sql.JoinType.LEFT_OUTER_JOIN);
		branchCriteria.createCriteria("bmtbPaymentReceipts", org.hibernate.sql.JoinType.LEFT_OUTER_JOIN);
		branchCriteria.createCriteria("amtbBillReqs", org.hibernate.sql.JoinType.LEFT_OUTER_JOIN);
		branchCriteria.createCriteria("amtbAccounts", org.hibernate.sql.JoinType.LEFT_OUTER_JOIN);
		
		criteria.add(Restrictions.idEq(bankNo));

		List list = findAllByCriteria(criteria);
		if (list.size() > 0) {
			return (MstbBankMaster) list.get(0);
		} else {
			return null;
		}
	}

	public MstbCreditTermMaster getCreditTermPlanWithDetails(
			Integer creditTermPlanNo) {
		DetachedCriteria planCriteria = DetachedCriteria.forClass(MstbCreditTermMaster.class);
		planCriteria.createCriteria("mstbCreditTermDetails", org.hibernate.sql.JoinType.LEFT_OUTER_JOIN);
		planCriteria.add(Restrictions.idEq(creditTermPlanNo));

		List list = findAllByCriteria(planCriteria);
		if (list.size() > 0) {
			return (MstbCreditTermMaster) list.get(0);
		} else {
			return null;
		}
	}
	
	public List<Integer> getAllCreditTerms() {
		DetachedCriteria mstbCreditTermDetailCriteria = DetachedCriteria.forClass(MstbCreditTermDetail.class);

		mstbCreditTermDetailCriteria.setProjection(Projections.distinct(Projections.property("creditTerm")));
		mstbCreditTermDetailCriteria.addOrder(Order.asc("creditTerm"));
		List list = findAllByCriteria(mstbCreditTermDetailCriteria);
		return list;
		
	}
	
	@SuppressWarnings("unchecked")
	public List<MstbPromotionCashPlus> getAllPromotionCashPlus() {
		
		DetachedCriteria criteria = DetachedCriteria.forClass(MstbPromotionCashPlus.class);
		criteria.addOrder(Order.asc("promoCode"));
		return findDefaultMaxResultByCriteria(criteria);
	
		
	}

	public MstbEarlyPaymentMaster getEarlyPaymentPlanWithDetails(
			Integer earlyPaymentPlanNo) {
		DetachedCriteria planCriteria = DetachedCriteria.forClass(MstbEarlyPaymentMaster.class);
		planCriteria.createCriteria("mstbEarlyPaymentDetails", org.hibernate.sql.JoinType.LEFT_OUTER_JOIN);
		planCriteria.add(Restrictions.idEq(earlyPaymentPlanNo));

		List list = findAllByCriteria(planCriteria);
		if (list.size() > 0) {
			return (MstbEarlyPaymentMaster) list.get(0);
		} else {
			return null;
		}
	}

	public FmtbBankCode getGLBankWithBranches(Integer bankNo) {
		DetachedCriteria codeCriteria = DetachedCriteria.forClass(FmtbBankCode.class);
		codeCriteria.setFetchMode("fmtbEntityMaster", FetchMode.JOIN);
		codeCriteria.setFetchMode("mstbMasterTable", FetchMode.JOIN);
		codeCriteria.add(Restrictions.idEq(bankNo));

		List list = findAllByCriteria(codeCriteria);
		if (list.size() > 0) {
			return (FmtbBankCode) list.get(0);
		} else {
			return null;
		}
	}

	public FmtbArContCodeMaster getGLControlCodeWithDetails(Integer codeNo) {
		DetachedCriteria planCriteria = DetachedCriteria.forClass(FmtbArContCodeMaster.class);
		planCriteria.createCriteria("fmtbArContCodeDetails", org.hibernate.sql.JoinType.LEFT_OUTER_JOIN);
		planCriteria.createCriteria("fmtbEntityMaster", org.hibernate.sql.JoinType.LEFT_OUTER_JOIN);
		//		planCriteria.setFetchMode("mstbMasterTable", FetchMode.JOIN);
		planCriteria.add(Restrictions.idEq(codeNo));

		List list = findAllByCriteria(planCriteria);
		if (list.size() > 0) {
			return (FmtbArContCodeMaster) list.get(0);
		} else {
			return null;
		}
	}

	public MstbLatePaymentMaster getLatePaymentPlanWithDetails(
			Integer latePaymentPlanNo) {
		DetachedCriteria planCriteria = DetachedCriteria.forClass(MstbLatePaymentMaster.class);
		planCriteria.createCriteria("mstbLatePaymentDetails", org.hibernate.sql.JoinType.LEFT_OUTER_JOIN);
		planCriteria.add(Restrictions.idEq(latePaymentPlanNo));

		List list = findAllByCriteria(planCriteria);
		if (list.size() > 0) {
			return (MstbLatePaymentMaster) list.get(0);
		} else {
			return null;
		}
	}

	public MstbSubscFeeMaster getSubscriptionFeePlanWithDetails(
			Integer subscriptionFeeNo) {
		DetachedCriteria planCriteria = DetachedCriteria.forClass(MstbSubscFeeMaster.class);
		planCriteria.createCriteria("mstbSubscFeeDetails", org.hibernate.sql.JoinType.LEFT_OUTER_JOIN);
		planCriteria.add(Restrictions.idEq(subscriptionFeeNo));

		List list = findAllByCriteria(planCriteria);
		if (list.size() > 0) {
			return (MstbSubscFeeMaster) list.get(0);
		} else {
			return null;
		}
	}
	
	public MstbIssuanceFeeMaster getIssuanceFeePlanWithDetails(
			Integer issuanceFeeNo) {
		DetachedCriteria planCriteria = DetachedCriteria.forClass(MstbIssuanceFeeMaster.class);
		planCriteria.createCriteria("mstbIssuanceFeeDetails", org.hibernate.sql.JoinType.LEFT_OUTER_JOIN);
		planCriteria.add(Restrictions.idEq(issuanceFeeNo));

		List list = findAllByCriteria(planCriteria);
		if (list.size() > 0) {
			return (MstbIssuanceFeeMaster) list.get(0);
		} else {
			return null;
		}
	}

	public MstbAdminFeeDetail getAdminFeePlanDetail(Integer planDetailNo) {
		DetachedCriteria planCriteria = DetachedCriteria.forClass(MstbAdminFeeDetail.class);
		planCriteria.setFetchMode("mstbAdminFeeMaster", FetchMode.JOIN);
		planCriteria.add(Restrictions.idEq(planDetailNo));

		List list = findAllByCriteria(planCriteria);
		if (list.size() > 0) {
			return (MstbAdminFeeDetail) list.get(0);
		} else {
			return null;
		}
	}

	public MstbCreditTermDetail getCreditTermPlanDetail(Integer planDetailNo) {
		DetachedCriteria planCriteria = DetachedCriteria.forClass(MstbCreditTermDetail.class);
		planCriteria.setFetchMode("mstbCreditTermMaster", FetchMode.JOIN);
		planCriteria.add(Restrictions.idEq(planDetailNo));

		List list = findAllByCriteria(planCriteria);
		if (list.size() > 0) {
			return (MstbCreditTermDetail) list.get(0);
		} else {
			return null;
		}
	}

	public MstbEarlyPaymentDetail getEarlyPaymentPlanDetail(Integer planDetailNo) {
		DetachedCriteria planCriteria = DetachedCriteria.forClass(MstbEarlyPaymentDetail.class);
		planCriteria.setFetchMode("mstbEarlyPaymentMaster", FetchMode.JOIN);
		planCriteria.add(Restrictions.idEq(planDetailNo));

		List list = findAllByCriteria(planCriteria);
		if (list.size() > 0) {
			return (MstbEarlyPaymentDetail) list.get(0);
		} else {
			return null;
		}
	}

	public FmtbArContCodeDetail getGLControlCodeDetail(Integer planDetailNo) {
		DetachedCriteria planCriteria = DetachedCriteria.forClass(FmtbArContCodeDetail.class);
		DetachedCriteria planMasterCriteria = planCriteria.setFetchMode("fmtbArContCodeMaster", FetchMode.JOIN);
		planMasterCriteria.setFetchMode("fmtbEntityMaster", FetchMode.JOIN);
		planCriteria.setFetchMode("mstbMasterTable", FetchMode.JOIN);
		planCriteria.add(Restrictions.idEq(planDetailNo));

		List list = findAllByCriteria(planCriteria);
		if (list.size() > 0) {
			return (FmtbArContCodeDetail) list.get(0);
		} else {
			return null;
		}
	}

	public MstbLatePaymentDetail getLatePaymentPlanDetail(Integer planDetailNo) {
		DetachedCriteria planCriteria = DetachedCriteria.forClass(MstbLatePaymentDetail.class);
		planCriteria.setFetchMode("mstbLatePaymentMaster", FetchMode.JOIN);
		planCriteria.add(Restrictions.idEq(planDetailNo));

		List list = findAllByCriteria(planCriteria);
		if (list.size() > 0) {
			return (MstbLatePaymentDetail) list.get(0);
		} else {
			return null;
		}
	}

	public MstbSubscFeeDetail getSubscriptionFeePlanDetail(Integer planDetailNo) {
		DetachedCriteria planCriteria = DetachedCriteria.forClass(MstbSubscFeeDetail.class);
		planCriteria.setFetchMode("mstbSubscFeeMaster", FetchMode.JOIN);
		planCriteria.add(Restrictions.idEq(planDetailNo));

		List list = findAllByCriteria(planCriteria);
		if (list.size() > 0) {
			return (MstbSubscFeeDetail) list.get(0);
		} else {
			return null;
		}
	}
	
	public MstbIssuanceFeeDetail getIssuanceFeePlanDetail(Integer planDetailNo) {
		DetachedCriteria planCriteria = DetachedCriteria.forClass(MstbIssuanceFeeDetail.class);
		planCriteria.setFetchMode("mstbIssuanceFeeMaster", FetchMode.JOIN);
		planCriteria.add(Restrictions.idEq(planDetailNo));

		List list = findAllByCriteria(planCriteria);
		if (list.size() > 0) {
			return (MstbIssuanceFeeDetail) list.get(0);
		} else {
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	public List<Object[]> getGLBankCodes(SearchGLBankForm form) {
		this.getActiveDBTransaction();
		List results = new ArrayList();
		Session session = null;
		try{
			session = this.currentSession();
			Query query = session.getNamedQuery("searchGLBank");
			
			if(form.entityNo!=null) query.setParameter("entityNo", form.entityNo, StandardBasicTypes.INTEGER);
			else query.setParameter("entityNo", null, StandardBasicTypes.INTEGER);
			if(form.bankCode!=null) query.setParameter("bankCode", form.bankCode, StandardBasicTypes.INTEGER);
			else query.setParameter("bankCode", null, StandardBasicTypes.STRING);
			if(form.branchCode!=null) query.setParameter("branchCode", form.branchCode, StandardBasicTypes.STRING);
			else query.setParameter("branchCode", null, StandardBasicTypes.STRING);
			if(form.effDateFromDate!=null & form.effDateToDate!=null){
				query.setParameter("effectiveDateFrom", form.effDateFromDate, StandardBasicTypes.DATE);
				query.setParameter("effectiveDateTo", form.effDateToDate, StandardBasicTypes.DATE);
			}
			else{
				query.setParameter("effectiveDateFrom", null, StandardBasicTypes.DATE);
				query.setParameter("effectiveDateTo", null, StandardBasicTypes.DATE);
			}
			
			query.setMaxResults(ConfigurableConstants.getMaxQueryResult());
			results = query.list();
		}
		catch(Exception e){
			e.printStackTrace();
		}
		finally{
			session.close();
		}

		return results;
	}

	@SuppressWarnings("unchecked")
	public List<FmtbArContCodeMaster> getGLControlCodes(Integer entityNo) {
		DetachedCriteria codeCriteria = DetachedCriteria.forClass(FmtbArContCodeMaster.class);
		codeCriteria.createCriteria("fmtbEntityMaster", "e", org.hibernate.sql.JoinType.LEFT_OUTER_JOIN);
		if (entityNo != null) {
			codeCriteria.add(Restrictions.eq("e.id", entityNo));
		}
		codeCriteria.addOrder(Order.asc("e.entityName"));
		codeCriteria.addOrder(Order.asc("arControlCode"));
		codeCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		return findAllByCriteria(codeCriteria);
	}

	public MstbBranchMaster getBankBranch(Integer branchNo) {
		DetachedCriteria branchCriteria = DetachedCriteria.forClass(MstbBranchMaster.class);
		branchCriteria.setFetchMode("mstbBankMaster", FetchMode.JOIN);
		branchCriteria.add(Restrictions.idEq(branchNo));

		List list = findAllByCriteria(branchCriteria);
		if (list.size() > 0) {
			return (MstbBranchMaster) list.get(0);
		} else {
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	public List<FmtbTaxCode> getGLTaxCodes(Integer entityNo, String taxType) {
		DetachedCriteria codeCriteria = DetachedCriteria.forClass(FmtbTaxCode.class);
		codeCriteria.createCriteria("fmtbEntityMaster", "e", org.hibernate.sql.JoinType.LEFT_OUTER_JOIN);
		DetachedCriteria taxTypeMasterCriteria = codeCriteria.createCriteria("mstbMasterTable", org.hibernate.sql.JoinType.LEFT_OUTER_JOIN);
		
		if (entityNo != null)
			codeCriteria.add(Restrictions.eq("e.id", entityNo));
		if(taxType !=null && taxType.length()>0)
			taxTypeMasterCriteria.add(Restrictions.eq("masterCode", taxType));
			
		codeCriteria.addOrder(Order.asc("e.entityName"));
		codeCriteria.addOrder(Order.asc("glTaxCode"));
		codeCriteria.addOrder(Order.desc("effectiveDate"));
		return findAllByCriteria(codeCriteria);
	}

	public FmtbTaxCode getGLTaxCode(Integer taxCodeNo) {
		DetachedCriteria codeCriteria = DetachedCriteria.forClass(FmtbTaxCode.class);
		codeCriteria.setFetchMode("fmtbEntityMaster", FetchMode.JOIN);
		codeCriteria.setFetchMode("mstbMasterTable", FetchMode.JOIN);
		codeCriteria.add(Restrictions.idEq(taxCodeNo));

		List list = findAllByCriteria(codeCriteria);
		if (list.size() > 0) {
			return (FmtbTaxCode) list.get(0);
		} else {
			return null;
		}
	}
	
	@SuppressWarnings("unchecked")
	public List<Object[]> getTransactionCodes(SearchTransactionCodeForm form) {
		this.getActiveDBTransaction();
		Session session = this.currentSession();
		List results = new ArrayList();
		try{
			Query query = session.getNamedQuery("searchTransactionCode");
			if(form.entityNo!=null) query.setParameter("entityNo", form.entityNo, StandardBasicTypes.INTEGER);
			else query.setParameter("entityNo", null, StandardBasicTypes.INTEGER);
			if(form.txnType!=null) query.setParameter("txnType", form.txnType, StandardBasicTypes.STRING);
			else query.setParameter("txnType", null, StandardBasicTypes.STRING);
			if(form.taxType!=null) query.setParameter("taxType", form.taxType, StandardBasicTypes.STRING);
			else query.setParameter("taxType", null, StandardBasicTypes.STRING);
			if(form.txnCode!=null) query.setParameter("txnCode", form.txnCode, StandardBasicTypes.STRING);
			else query.setParameter("txnCode", null, StandardBasicTypes.STRING);
			if(form.effDateFromDate!=null & form.effDateToDate!=null){
				query.setParameter("effectiveDateFrom", form.effDateFromDate, StandardBasicTypes.DATE);
				query.setParameter("effectiveDateTo", form.effDateToDate, StandardBasicTypes.DATE);
			}
			else{
				query.setParameter("effectiveDateFrom", null, StandardBasicTypes.DATE);
				query.setParameter("effectiveDateTo", null, StandardBasicTypes.DATE);
			}
			
			query.setMaxResults(ConfigurableConstants.getMaxQueryResult());
			results = query.list();
		}
		catch(Exception e){
			e.printStackTrace();
		}
		finally{
			session.close();
		}

		return results;
	}

	public FmtbTransactionCode getTransactionCode(Integer transactionCodeNo) {
		DetachedCriteria codeCriteria = DetachedCriteria.forClass(FmtbTransactionCode.class);
		codeCriteria.setFetchMode("fmtbEntityMaster", FetchMode.JOIN);
		codeCriteria.setFetchMode("pmtbProductType", FetchMode.JOIN);
		codeCriteria.setFetchMode("mstbMasterTable", FetchMode.JOIN);
		codeCriteria.add(Restrictions.idEq(transactionCodeNo));

		List list = findAllByCriteria(codeCriteria);
		if (list.size() > 0) {
			return (FmtbTransactionCode) list.get(0);
		} else {
			return null;
		}
	}

	public FmtbEntityMaster getEntity(Integer entityNo) {
		DetachedCriteria codeCriteria = DetachedCriteria.forClass(FmtbEntityMaster.class);
		codeCriteria.setFetchMode("mstbMasterTable", FetchMode.JOIN);
		codeCriteria.add(Restrictions.idEq(entityNo));

		List list = findAllByCriteria(codeCriteria);
		if (list.size() > 0) {
			return (FmtbEntityMaster) list.get(0);
		} else {
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	public List<MstbAdminFeeMaster> getAdminFeePlans() {
		DetachedCriteria criteria = DetachedCriteria.forClass(MstbAdminFeeMaster.class);
		criteria.setFetchMode("mstbAdminFeeDetails", FetchMode.JOIN);
		criteria.addOrder(Order.asc("adminFeePlanName"));
		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		return findAllByCriteria(criteria);
	}

	@SuppressWarnings("unchecked")
	public List<MstbBankMaster> getBanks() {
		DetachedCriteria criteria = DetachedCriteria.forClass(MstbBankMaster.class);
		//criteria.setFetchMode("bmtbPaymentReceipts", FetchMode.JOIN);
		//criteria.setFetchMode("mstbBranchMasters", FetchMode.JOIN);
		//criteria.setFetchMode("amtbBillReqs", FetchMode.JOIN);
        //criteria.setFetchMode("amtbAccounts", FetchMode.JOIN);

		criteria.addOrder(Order.asc("bankCode"));
		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		return findAllByCriteria(criteria);
	}

	@SuppressWarnings("unchecked")
	public List<MstbCreditTermMaster> getCreditTermPlans() {
		DetachedCriteria criteria = DetachedCriteria.forClass(MstbCreditTermMaster.class);
		criteria.setFetchMode("mstbCreditTermDetails", FetchMode.JOIN);
		criteria.addOrder(Order.asc("creditTermPlanName"));
		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		return findAllByCriteria(criteria);
	}

	@SuppressWarnings("unchecked")
	public List<MstbEarlyPaymentMaster> getEarlyPaymentPlans() {
		DetachedCriteria criteria = DetachedCriteria.forClass(MstbEarlyPaymentMaster.class);
		criteria.setFetchMode("mstbEarlyPaymentDetails", FetchMode.JOIN);
		criteria.addOrder(Order.asc("earlyPaymentPlanName"));
		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		return findAllByCriteria(criteria);
	}

	@SuppressWarnings("unchecked")
	public List<FmtbEntityMaster> getEntities() {
		DetachedCriteria criteria = DetachedCriteria.forClass(FmtbEntityMaster.class);
		criteria.addOrder(Order.asc("entityName"));
		return findAllByCriteria(criteria);
	}
	
	@SuppressWarnings("unchecked")
	public List<FmtbEntityMaster> getActiveEntities() {
		DetachedCriteria criteria = DetachedCriteria.forClass(FmtbEntityMaster.class);
		criteria.add(Restrictions.or(
				Restrictions.ge("effectiveEndDate", DateUtil.getCurrentDate()), 
				Restrictions.isNull("effectiveEndDate"))
				);
		criteria.addOrder(Order.asc("entityName"));
		return findAllByCriteria(criteria);
	}
	
	public boolean isRecordDeletable(FmtbEntityMaster master){
		DetachedCriteria criteria1 = DetachedCriteria.forClass(FmtbGlLogHeader.class);
		criteria1.add(Restrictions.eq("fmtbEntityMaster", master));
		if(this.findMaxResultByCriteria(criteria1, 1).size() > 0)
			return false;
		else{
			DetachedCriteria criteria2 = DetachedCriteria.forClass(FmtbBankCode.class);
			criteria2.add(Restrictions.eq("fmtbEntityMaster", master));
			if(this.findMaxResultByCriteria(criteria2, 1).size() > 0)
				return false;
			else{
				DetachedCriteria criteria3 = DetachedCriteria.forClass(FmtbArContCodeMaster.class);
				criteria3.add(Restrictions.eq("fmtbEntityMaster", master));
				if(this.findMaxResultByCriteria(criteria3, 1).size() > 0)
					return false;
				else{
					DetachedCriteria criteria4 = DetachedCriteria.forClass(FmtbTransactionCode.class);
					criteria4.add(Restrictions.eq("fmtbEntityMaster", master));
					if(this.findMaxResultByCriteria(criteria4, 1).size() > 0)
						return false;
					else{
						DetachedCriteria criteria5 = DetachedCriteria.forClass(FmtbTaxCode.class);
						criteria5.add(Restrictions.eq("fmtbEntityMaster", master));
						if(this.findMaxResultByCriteria(criteria5, 1).size() > 0)
							return false;
					}
				}
			}
		}
		return true;
	}
	
	public boolean isRecordDeletable(FmtbArContCodeMaster master){
		DetachedCriteria criteria1 = DetachedCriteria.forClass(FmtbArContCodeDetail.class);
		criteria1.add(Restrictions.eq("fmtbArContCodeMaster", master));
		if(this.findMaxResultByCriteria(criteria1, 1).size() > 0)
			return false;
		else{
			DetachedCriteria criteria2 = DetachedCriteria.forClass(AmtbAccount.class);
			criteria2.add(Restrictions.eq("fmtbArContCodeMaster", master));
			if(this.findMaxResultByCriteria(criteria2, 1).size() > 0)
				return false;
			else{
				DetachedCriteria criteria3 = DetachedCriteria.forClass(AmtbApplication.class);
				criteria3.add(Restrictions.eq("fmtbArContCodeMaster", master));
				if(this.findMaxResultByCriteria(criteria3, 1).size() > 0)
					return false;
			}
		}
		return true;
	}
	
	public boolean isRecordDeletable(MstbBankMaster master){
		DetachedCriteria criteria1 = DetachedCriteria.forClass(BmtbPaymentReceipt.class);
		criteria1.add(Restrictions.eq("mstbBankMaster", master));
		if(this.findMaxResultByCriteria(criteria1, 1).size() > 0)
			return false;
		else{
			DetachedCriteria criteria2 = DetachedCriteria.forClass(MstbBranchMaster.class);
			criteria2.add(Restrictions.eq("mstbBankMaster", master));
			if(this.findMaxResultByCriteria(criteria2, 1).size() > 0)
				return false;
			else{
				DetachedCriteria criteria3 = DetachedCriteria.forClass(AmtbBillReq.class);
				criteria3.add(Restrictions.eq("mstbBankMaster", master));
				if(this.findMaxResultByCriteria(criteria3, 1).size() > 0)
					return false;
				else{
					DetachedCriteria criteria4 = DetachedCriteria.forClass(AmtbAccount.class);
					criteria4.add(Restrictions.eq("mstbBankMaster", master));
					if(this.findMaxResultByCriteria(criteria4, 1).size() > 0)
						return false;
				}
			}
		}
		return true;
	}
	
	public boolean isRecordDeletable(MstbBranchMaster branch){
		DetachedCriteria criteria1 = DetachedCriteria.forClass(BmtbPaymentReceipt.class);
		criteria1.add(Restrictions.eq("mstbBankMaster", branch.getMstbBankMaster()));
		if(this.findMaxResultByCriteria(criteria1, 1).size() > 0)
			return false;
		else{
			DetachedCriteria criteria2 = DetachedCriteria.forClass(AmtbBillReq.class);
			criteria2.add(Restrictions.eq("mstbBankMaster", branch.getMstbBankMaster()));
			if(this.findMaxResultByCriteria(criteria2, 1).size() > 0)
				return false;
			else{
				DetachedCriteria criteria3 = DetachedCriteria.forClass(AmtbAccount.class);
				criteria3.add(Restrictions.eq("mstbBankMaster", branch.getMstbBankMaster()));
				if(this.findMaxResultByCriteria(criteria3, 1).size() > 0)
					return false;
			}
		}
		return true;
	}

	@SuppressWarnings("unchecked")
	public List<MstbLatePaymentMaster> getLatePaymentPlans() {
		DetachedCriteria criteria = DetachedCriteria.forClass(MstbLatePaymentMaster.class);
		criteria.setFetchMode("mstbLatePaymentDetails", FetchMode.JOIN);
		criteria.addOrder(Order.asc("latePaymentPlanName"));
		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		return findAllByCriteria(criteria);
	}

	@SuppressWarnings("unchecked")
	public List<MstbProdDiscMaster> getProductDiscountPlans() {
		DetachedCriteria criteria = DetachedCriteria.forClass(MstbProdDiscMaster.class);
		criteria.createCriteria("mstbProdDiscDetails", org.hibernate.sql.JoinType.LEFT_OUTER_JOIN);
		criteria.addOrder(Order.asc("productDiscountPlanName"));
		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		
		return findAllByCriteria(criteria);
	}

	@SuppressWarnings("unchecked")
	public List<PmtbProductType> getProductTypes() {
		DetachedCriteria criteria = DetachedCriteria.forClass(PmtbProductType.class);
		criteria.addOrder(Order.asc("name"));
		return findAllByCriteria(criteria);
	}

	@SuppressWarnings("unchecked")
	public List<MstbSalesperson> getSalesPersons() {
		DetachedCriteria criteria = DetachedCriteria.forClass(MstbSalesperson.class);
		criteria.addOrder(Order.asc("name"));
		return findAllByCriteria(criteria);
	}

	@SuppressWarnings("unchecked")
	public List<MstbSubscFeeMaster> getSubscriptionFeePlans() {
		DetachedCriteria criteria = DetachedCriteria.forClass(MstbSubscFeeMaster.class);
		criteria.createCriteria("mstbSubscFeeDetails", org.hibernate.sql.JoinType.LEFT_OUTER_JOIN);
		criteria.addOrder(Order.asc("subscriptionFeeName"));
		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		return findAllByCriteria(criteria);
	}
	
	@SuppressWarnings("unchecked")
	public List<MstbIssuanceFeeMaster> getIssuanceFeePlans() {
		DetachedCriteria criteria = DetachedCriteria.forClass(MstbIssuanceFeeMaster.class);
		criteria.createCriteria("mstbIssuanceFeeDetails", org.hibernate.sql.JoinType.LEFT_OUTER_JOIN);
		criteria.addOrder(Order.asc("issuanceFeeName"));
		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		return findAllByCriteria(criteria);
	}

	@SuppressWarnings("unchecked")
	public List<MstbVolDiscMaster> getVolumeDiscountPlans() {
		DetachedCriteria criteria = DetachedCriteria.forClass(MstbVolDiscMaster.class);
		criteria.createCriteria("mstbVolDiscDetails", org.hibernate.sql.JoinType.LEFT_OUTER_JOIN);
		criteria.addOrder(Order.asc("volumeDiscountPlanName"));
		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		return findAllByCriteria(criteria);
	}

	public boolean hasDuplicateEffectiveDate(FmtbTaxCode tax) {
		DetachedCriteria criteria = DetachedCriteria.forClass(FmtbTaxCode.class);
		criteria.createCriteria("fmtbEntityMaster", "entity", org.hibernate.sql.JoinType.LEFT_OUTER_JOIN);
		criteria.createCriteria("mstbMasterTable", "taxType", org.hibernate.sql.JoinType.LEFT_OUTER_JOIN);
		criteria.add(Restrictions.eq("entity.entityNo", tax.getFmtbEntityMaster().getEntityNo()));
		criteria.add(Restrictions.eq("taxType.masterCode", tax.getMstbMasterTable().getMasterCode()));
		criteria.add(Restrictions.eq("effectiveDate", tax.getEffectiveDate()));
		if (tax.getTaxCodeNo() != null) {
			criteria.add(Restrictions.ne("taxCodeNo", tax.getTaxCodeNo()));
		}
		return findAllByCriteria(criteria).size() > 0;
	}

	public boolean hasDuplicateEffectiveDate(MstbVolDiscDetail detail) {
		DetachedCriteria criteria = DetachedCriteria.forClass(MstbVolDiscDetail.class);
		criteria.createCriteria("mstbVolDiscMaster", "m", org.hibernate.sql.JoinType.LEFT_OUTER_JOIN);
		criteria.add(Restrictions.eq("m.id", detail.getMstbVolDiscMaster().getVolumeDiscountPlanNo()));
		criteria.add(Restrictions.eq("effectiveDt", detail.getEffectiveDt()));
		if (detail.getVolumeDiscountPlanDetailNo() != null) {
			criteria.add(Restrictions.ne("id", detail.getVolumeDiscountPlanDetailNo()));
		}
		return findAllByCriteria(criteria).size() > 0;
	}

	public boolean hasDuplicateEffectiveDate(MstbProdDiscDetail detail) {
		DetachedCriteria criteria = DetachedCriteria.forClass(MstbProdDiscDetail.class);
		criteria.createCriteria("mstbProdDiscMaster", "m", org.hibernate.sql.JoinType.LEFT_OUTER_JOIN);
		criteria.add(Restrictions.eq("m.id", detail.getMstbProdDiscMaster().getProductDiscountPlanNo()));
		criteria.add(Restrictions.eq("effectiveDt", detail.getEffectiveDt()));
		if (detail.getProdDiscountPlanDetailNo() != null) {
			criteria.add(Restrictions.ne("id", detail.getProdDiscountPlanDetailNo()));
		}
		return findAllByCriteria(criteria).size() > 0;
	}

	public boolean hasDuplicateEffectiveDate(MstbAdminFeeDetail detail) {
		DetachedCriteria criteria = DetachedCriteria.forClass(MstbAdminFeeDetail.class);
		criteria.createCriteria("mstbAdminFeeMaster", "m", org.hibernate.sql.JoinType.LEFT_OUTER_JOIN);
		criteria.add(Restrictions.eq("m.id", detail.getMstbAdminFeeMaster().getAdminFeePlanNo()));
		criteria.add(Restrictions.eq("effectiveDt", detail.getEffectiveDt()));
		if (detail.getAdminFeePlanDetailNo() != null) {
			criteria.add(Restrictions.ne("id", detail.getAdminFeePlanDetailNo()));
		}
		return findAllByCriteria(criteria).size() > 0;
	}

	public boolean hasDuplicateEffectiveDate(MstbCreditTermDetail detail) {
		DetachedCriteria criteria = DetachedCriteria.forClass(MstbCreditTermDetail.class);
		criteria.createCriteria("mstbCreditTermMaster", "m", org.hibernate.sql.JoinType.LEFT_OUTER_JOIN);
		criteria.add(Restrictions.eq("m.id", detail.getMstbCreditTermMaster().getCreditTermPlanNo()));
		criteria.add(Restrictions.eq("effectiveDt", detail.getEffectiveDt()));
		if (detail.getMstbCreditTermDetailNo() != null) {
			criteria.add(Restrictions.ne("id", detail.getMstbCreditTermDetailNo()));
		}
		return findAllByCriteria(criteria).size() > 0;
	}

	public boolean hasDuplicateEffectiveDate(MstbSubscFeeDetail detail) {
		DetachedCriteria criteria = DetachedCriteria.forClass(MstbSubscFeeDetail.class);
		criteria.createCriteria("mstbSubscFeeMaster", "m", org.hibernate.sql.JoinType.LEFT_OUTER_JOIN);
		criteria.add(Restrictions.eq("m.id", detail.getMstbSubscFeeMaster().getSubscriptionFeeNo()));
		criteria.add(Restrictions.eq("effectiveDt", detail.getEffectiveDt()));
		if (detail.getSubscriptionFeeDetailNo() != null) {
			criteria.add(Restrictions.ne("id", detail.getSubscriptionFeeDetailNo()));
		}
		return findAllByCriteria(criteria).size() > 0;
	}
	
	public boolean hasDuplicateEffectiveDate(MstbIssuanceFeeDetail detail) {
		DetachedCriteria criteria = DetachedCriteria.forClass(MstbIssuanceFeeDetail.class);
		criteria.createCriteria("mstbIssuanceFeeMaster", "m", org.hibernate.sql.JoinType.LEFT_OUTER_JOIN);
		criteria.add(Restrictions.eq("m.id", detail.getMstbIssuanceFeeMaster().getIssuanceFeeNo()));
		criteria.add(Restrictions.eq("effectiveDt", detail.getEffectiveDt()));
		if (detail.getIssuanceFeeDetailNo() != null) {
			criteria.add(Restrictions.ne("id", detail.getIssuanceFeeDetailNo()));
		}
		return findAllByCriteria(criteria).size() > 0;
	}

	public boolean hasDuplicateEffectiveDate(MstbLatePaymentDetail detail) {
		DetachedCriteria criteria = DetachedCriteria.forClass(MstbLatePaymentDetail.class);
		criteria.createCriteria("mstbLatePaymentMaster", "m", org.hibernate.sql.JoinType.LEFT_OUTER_JOIN);
		criteria.add(Restrictions.eq("m.id", detail.getMstbLatePaymentMaster().getLatePaymentPlanNo()));
		criteria.add(Restrictions.eq("effectiveDt", detail.getEffectiveDt()));
		if (detail.getLatePaymentPlanDetailNo() != null) {
			criteria.add(Restrictions.ne("id", detail.getLatePaymentPlanDetailNo()));
		}
		return findAllByCriteria(criteria).size() > 0;
	}

	public boolean hasDuplicateEffectiveDate(MstbEarlyPaymentDetail detail) {
		DetachedCriteria criteria = DetachedCriteria.forClass(MstbEarlyPaymentDetail.class);
		criteria.createCriteria("mstbEarlyPaymentMaster", "m", org.hibernate.sql.JoinType.LEFT_OUTER_JOIN);
		criteria.add(Restrictions.eq("m.id", detail.getMstbEarlyPaymentMaster().getEarlyPaymentPlanNo()));
		criteria.add(Restrictions.eq("effectiveDt", detail.getEffectiveDt()));
		if (detail.getEarlyPaymentPlanDetailNo() != null) {
			criteria.add(Restrictions.ne("id", detail.getEarlyPaymentPlanDetailNo()));
		}
		return findAllByCriteria(criteria).size() > 0;
	}

	public boolean hasDuplicateEffectiveDate(FmtbTransactionCode transactionCode) {
		DetachedCriteria criteria = DetachedCriteria.forClass(FmtbTransactionCode.class);
		criteria.createCriteria("fmtbEntityMaster", "entity", org.hibernate.sql.JoinType.LEFT_OUTER_JOIN);
		criteria.createCriteria("mstbMasterTable", "taxType", org.hibernate.sql.JoinType.LEFT_OUTER_JOIN);
		criteria.add(Restrictions.eq("entity.entityNo", transactionCode.getFmtbEntityMaster().getEntityNo()));
		criteria.add(Restrictions.eq("txnCode", transactionCode.getTxnCode()));
		criteria.add(Restrictions.eq("effectiveDate", transactionCode.getEffectiveDate()));
		if (transactionCode.getTransactionCodeNo() != null) {
			criteria.add(Restrictions.ne("id", transactionCode.getTransactionCodeNo()));
		}
		return findAllByCriteria(criteria).size() > 0;
	}

	public boolean hasDuplicateName(MstbVolDiscMaster plan) {
		DetachedCriteria criteria = DetachedCriteria.forClass(MstbVolDiscMaster.class);
		criteria.add(Restrictions.eq("volumeDiscountPlanName", plan.getVolumeDiscountPlanName()));
		if (plan.getVolumeDiscountPlanNo() != null) {
			criteria.add(Restrictions.ne("id", plan.getVolumeDiscountPlanNo()));
		}
		return findAllByCriteria(criteria).size() > 0;
	}

	public boolean hasDuplicateName(MstbProdDiscMaster plan) {
		DetachedCriteria criteria = DetachedCriteria.forClass(MstbProdDiscMaster.class);
		criteria.add(Restrictions.eq("productDiscountPlanName", plan.getProductDiscountPlanName()));
		if (plan.getProductDiscountPlanNo() != null) {
			criteria.add(Restrictions.ne("id", plan.getProductDiscountPlanNo()));
		}
		return findAllByCriteria(criteria).size() > 0;
	}

	public boolean hasDuplicateName(MstbAdminFeeMaster plan) {
		DetachedCriteria criteria = DetachedCriteria.forClass(MstbAdminFeeMaster.class);
		criteria.add(Restrictions.eq("adminFeePlanName", plan.getAdminFeePlanName()));
		if (plan.getAdminFeePlanNo() != null) {
			criteria.add(Restrictions.ne("id", plan.getAdminFeePlanNo()));
		}
		return findAllByCriteria(criteria).size() > 0;
	}

	public boolean hasDuplicateName(MstbCreditTermMaster plan) {
		DetachedCriteria criteria = DetachedCriteria.forClass(MstbCreditTermMaster.class);
		criteria.add(Restrictions.eq("creditTermPlanName", plan.getCreditTermPlanName()));
		if (plan.getCreditTermPlanNo() != null) {
			criteria.add(Restrictions.ne("id", plan.getCreditTermPlanNo()));
		}
		return findAllByCriteria(criteria).size() > 0;
	}

	public boolean hasDuplicateName(MstbEarlyPaymentMaster plan) {
		DetachedCriteria criteria = DetachedCriteria.forClass(MstbEarlyPaymentMaster.class);
		criteria.add(Restrictions.eq("earlyPaymentPlanName", plan.getEarlyPaymentPlanName()));
		if (plan.getEarlyPaymentPlanNo() != null) {
			criteria.add(Restrictions.ne("id", plan.getEarlyPaymentPlanNo()));
		}
		return findAllByCriteria(criteria).size() > 0;
	}

	public boolean hasDuplicateName(MstbLatePaymentMaster plan) {
		DetachedCriteria criteria = DetachedCriteria.forClass(MstbLatePaymentMaster.class);
		criteria.add(Restrictions.eq("latePaymentPlanName", plan.getLatePaymentPlanName()));
		if (plan.getLatePaymentPlanNo() != null) {
			criteria.add(Restrictions.ne("id", plan.getLatePaymentPlanNo()));
		}
		return findAllByCriteria(criteria).size() > 0;
	}

	public boolean hasDuplicateName(MstbSubscFeeMaster plan) {
		DetachedCriteria criteria = DetachedCriteria.forClass(MstbSubscFeeMaster.class);
		criteria.add(Restrictions.eq("subscriptionFeeName", plan.getSubscriptionFeeName()));
		if (plan.getSubscriptionFeeNo() != null) {
			criteria.add(Restrictions.ne("id", plan.getSubscriptionFeeNo()));
		}
		return findAllByCriteria(criteria).size() > 0;
	}
	
	public boolean hasDuplicateName(MstbIssuanceFeeMaster plan) {
		DetachedCriteria criteria = DetachedCriteria.forClass(MstbIssuanceFeeMaster.class);
		criteria.add(Restrictions.eq("issuanceFeeName", plan.getIssuanceFeeName()));
		if (plan.getIssuanceFeeNo() != null) {
			criteria.add(Restrictions.ne("id", plan.getIssuanceFeeNo()));
		}
		return findAllByCriteria(criteria).size() > 0;
	}

	public boolean hasDuplicateName(MstbSalesperson salesPerson) {
		DetachedCriteria criteria = DetachedCriteria.forClass(MstbSalesperson.class);
		criteria.add(Restrictions.eq("name", salesPerson.getName()));
		if (salesPerson.getSalespersonNo() != null) {
			criteria.add(Restrictions.ne("id", salesPerson.getSalespersonNo()));
		}
		return findAllByCriteria(criteria).size() > 0;
	}

	public boolean hasDuplicateName(FmtbEntityMaster entity) {
		DetachedCriteria criteria = DetachedCriteria.forClass(FmtbEntityMaster.class);
		criteria.add(Restrictions.eq("entityName", entity.getEntityName()));
		if (entity.getEntityNo() != null) {
			criteria.add(Restrictions.ne("id", entity.getEntityNo()));
		}
		return findAllByCriteria(criteria).size() > 0;
	}
	
	

	public boolean hasDuplicateCode(FmtbBankCode bank) {
		DetachedCriteria criteria = DetachedCriteria.forClass(FmtbBankCode.class);
		criteria.add(Restrictions.eq("fmtbEntityMaster", bank.getFmtbEntityMaster()));
		criteria.add(Restrictions.eq("bankCode", bank.getBankCode()));
		criteria.add(Restrictions.eq("branchCode", bank.getBranchCode()));
		criteria.add(Restrictions.eq("effectiveDate", bank.getEffectiveDate()));
		criteria.add(Restrictions.eq("bankAcctNo", bank.getBankAcctNo()));
		if(bank.getBankCodeNo()!=null)
			criteria.add(Restrictions.ne("bankCodeNo", bank.getBankCodeNo()));
		return findAllByCriteria(criteria).size() > 0;
	}

	public boolean hasDuplicateCode(FmtbEntityMaster entity) {
		DetachedCriteria criteria = DetachedCriteria.forClass(FmtbEntityMaster.class);
		criteria.add(Restrictions.eq("entityCode", entity.getEntityCode()));
		if (entity.getEntityNo() != null) {
			criteria.add(Restrictions.ne("entityNo", entity.getEntityNo()));
		}
		return findAllByCriteria(criteria).size() > 0;
	}

	public boolean hasDuplicateCode(MstbBankMaster bank) {
		DetachedCriteria criteria = DetachedCriteria.forClass(MstbBankMaster.class);
		criteria.add(Restrictions.eq("bankCode", bank.getBankCode()));
		if (bank.getBankMasterNo() != null) {
			criteria.add(Restrictions.ne("bankMasterNo", bank.getBankMasterNo()));
		}
		return findAllByCriteria(criteria).size() > 0;
	}

	public boolean hasDuplicateCode(FmtbArContCodeMaster code) {
		DetachedCriteria criteria = DetachedCriteria.forClass(FmtbArContCodeMaster.class);
		criteria.add(Restrictions.eq("arControlCode", code.getArControlCode()));
		criteria.add(Restrictions.eq("fmtbEntityMaster", code.getFmtbEntityMaster()));
		if (code.getArControlCodeNo() != null) {
			criteria.add(Restrictions.ne("arControlCodeNo", code.getArControlCodeNo()));
		}
		return findAllByCriteria(criteria).size() > 0;
	}

	public boolean hasDuplicateCode(MstbBranchMaster branch) {
		DetachedCriteria criteria = DetachedCriteria.forClass(MstbBranchMaster.class);
		DetachedCriteria bankMasterCriteria = criteria.createCriteria("mstbBankMaster", org.hibernate.sql.JoinType.LEFT_OUTER_JOIN);
		
		criteria.add(Restrictions.eq("branchCode", branch.getBranchCode()));
		bankMasterCriteria.add(Restrictions.idEq(branch.getMstbBankMaster().getBankMasterNo()));
		
		if (branch.getBranchMasterNo() != null) {
			criteria.add(Restrictions.ne("branchMasterNo", branch.getBranchMasterNo()));
		}
		return findAllByCriteria(criteria).size() > 0;
	}
	
	public boolean hasDuplicateName(MstbBranchMaster branch) {
		DetachedCriteria criteria = DetachedCriteria.forClass(MstbBranchMaster.class);
		DetachedCriteria bankMasterCriteria = criteria.createCriteria("mstbBankMaster", org.hibernate.sql.JoinType.LEFT_OUTER_JOIN);
		
		criteria.add(Restrictions.eq("branchName", branch.getBranchName()));
		bankMasterCriteria.add(Restrictions.idEq(branch.getMstbBankMaster().getBankMasterNo()));
		
		if (branch.getBranchMasterNo() != null) {
			criteria.add(Restrictions.ne("branchMasterNo", branch.getBranchMasterNo()));
		}
		return findAllByCriteria(criteria).size() > 0;
	}
	
	@SuppressWarnings("unchecked")
	public List<MstbAcquirer> searchAcquirer(SearchAcquirerForm form){
		DetachedCriteria criteria = DetachedCriteria.forClass(MstbAcquirer.class);
		
		if(form.name!=null && form.name.length()>0)
			criteria.add(Restrictions.like("name", form.name, MatchMode.ANYWHERE));
		
		return findDefaultMaxResultByCriteria(criteria);
	}
	@SuppressWarnings("unchecked")
	public List<MstbAcquirer> getAcquirerByExample(){
		DetachedCriteria criteria = DetachedCriteria.forClass(MstbAcquirer.class);
		criteria.addOrder(Order.asc("name"));
		return findAllByCriteria(criteria);
	}
	@SuppressWarnings("unchecked")
	public List<FmtbBankPaymentMaster> searchBankPayment(SearchBankPaymentForm form){
		DetachedCriteria criteria = DetachedCriteria.forClass(FmtbBankPaymentMaster.class);
		criteria.createCriteria("mstbAcquirer", org.hibernate.sql.JoinType.LEFT_OUTER_JOIN);
		criteria.createCriteria("fmtbBankPaymentDetails", org.hibernate.sql.JoinType.LEFT_OUTER_JOIN);
		criteria.add(Restrictions.eq("mstbAcquirer.acquirerNo", form.acquirer.getAcquirerNo()));
		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		return findDefaultMaxResultByCriteria(criteria);
	}
	@SuppressWarnings("unchecked")
	public List<FmtbNonBillableMaster> searchNonBillable(SearchNonBillableForm form){
		DetachedCriteria criteria = DetachedCriteria.forClass(FmtbNonBillableMaster.class);
		DetachedCriteria masterSPCriteria = criteria.createCriteria("mstbMasterTableByServiceProvider", org.hibernate.sql.JoinType.LEFT_OUTER_JOIN);
		DetachedCriteria masterPTCriteria = criteria.createCriteria("mstbMasterTableByPymtTypeMasterNo", org.hibernate.sql.JoinType.LEFT_OUTER_JOIN);
		criteria.createCriteria("fmtbNonBillableDetails", org.hibernate.sql.JoinType.LEFT_OUTER_JOIN);
		
		if(form.service_provider!=null){
			masterSPCriteria.add(Restrictions.eq("masterCode", form.service_provider));
		}
		if(form.card_type!=null){
			masterPTCriteria.add(Restrictions.eq("masterCode", form.card_type));
		}
		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		return findDefaultMaxResultByCriteria(criteria);
	}
	@SuppressWarnings("unchecked")
	public List<FmtbBankPaymentMaster> getBankPaymentByForeignExample(FmtbBankPaymentMaster master){
		DetachedCriteria criteria = DetachedCriteria.forClass(FmtbBankPaymentMaster.class);
		if(master.getMasterNo()!=null){
			criteria.add(Restrictions.ne("masterNo", master.getMasterNo()));
		}
		criteria.add(Restrictions.eq("mstbAcquirer.acquirerNo", master.getMstbAcquirer().getAcquirerNo()));
		return findAllByCriteria(criteria);
	}
	@SuppressWarnings("unchecked")
	public List<FmtbBankPaymentDetail> getBankPaymentDetailByForeignExample(FmtbBankPaymentDetail detail){
		DetachedCriteria criteria = DetachedCriteria.forClass(FmtbBankPaymentDetail.class);
		criteria.add(Restrictions.eq("effectiveDate", detail.getEffectiveDate()));
		criteria.add(Restrictions.eq("fmtbBankPaymentMaster.masterNo", detail.getFmtbBankPaymentMaster().getMasterNo()));
		if(detail.getDetailNo()!=null)
			criteria.add(Restrictions.ne("detailNo", detail.getDetailNo()));
		return findAllByCriteria(criteria);
	}
	public boolean hasDuplicateRecord(FmtbNonBillableMaster master){		
		DetachedCriteria criteria = DetachedCriteria.forClass(FmtbNonBillableMaster.class);
		criteria.add(Restrictions.eq("mstbMasterTableByServiceProvider.masterNo", master.getMstbMasterTableByServiceProvider().getMasterNo()));
		criteria.add(Restrictions.eq("mstbMasterTableByPymtTypeMasterNo.masterNo", master.getMstbMasterTableByPymtTypeMasterNo().getMasterNo()));
		if(master.getMasterNo()!=null){
			criteria.add(Restrictions.ne("masterNo", master.getMasterNo()));
		}
		return findAllByCriteria(criteria).size() > 0;
	}
	
	@SuppressWarnings("unchecked")
	public List<FmtbArContCodeMaster> getARControlCode(Integer entity_no){
		DetachedCriteria criteria = DetachedCriteria.forClass(FmtbArContCodeMaster.class);
		DetachedCriteria entityCriteria = criteria.createCriteria("fmtbEntityMaster", org.hibernate.sql.JoinType.LEFT_OUTER_JOIN);
		entityCriteria.add(Restrictions.eq("entityNo", entity_no));
		criteria.addOrder(Order.asc("arControlCode"));
		
		return findAllByCriteria(criteria);
	}
	
	@SuppressWarnings("unchecked")
	public List<MstbAcquirerPymtType> searchAcquirerPaymentType(SearchAcquirerPaymentTypeForm form){
		DetachedCriteria criteria = DetachedCriteria.forClass(MstbAcquirerPymtType.class);
		criteria.createCriteria("mstbMasterTable", "mmt", org.hibernate.sql.JoinType.LEFT_OUTER_JOIN);
		DetachedCriteria acquirerCriteria = criteria.createCriteria("mstbAcquirer", "mstbacq", org.hibernate.sql.JoinType.LEFT_OUTER_JOIN);
		
		if(form.acquirerNo!=null)
			acquirerCriteria.add(Restrictions.eq("acquirerNo", form.acquirerNo));
		if(form.value!=null && form.value.length()>0)
			criteria.add(Restrictions.eq("mmt.masterCode", form.value));
		if(form.effDateFromDate!=null || form.effDateToDate!=null){
			/*
			 	E.g. Search input
				From: 01/04/2010
				To: 01/06/2010
				
				Should appear:
				01/01/2010 to NULL
				01/05/2010 to NULL
				01/01/2010 to 30/04/2010
				01/05/2010 to 31/05/2010
				15/05/2010 to 01/07/2010
				
				Should not appear:
				01/01/2010 to 01/03/2010
				01/07/2010 to NULL
				01/07/2010 to 01/08/2010
			 */
		
			Disjunction disjunction = Restrictions.disjunction();
			
			Conjunction c1 = Restrictions.conjunction();
			c1.add(Restrictions.le("effectiveDtFrom", DateUtil.convertDateToTimestamp(DateUtil.convertDateTo2359Hours(form.effDateFromDate))));
			c1.add(Restrictions.isNull("effectiveDtTo"));
			disjunction.add(c1);
			
			Conjunction c2 = Restrictions.conjunction();
			c2.add(Restrictions.le("effectiveDtFrom", DateUtil.convertDateToTimestamp(DateUtil.convertDateTo2359Hours(form.effDateToDate))));
			c2.add(Restrictions.isNull("effectiveDtTo"));
			disjunction.add(c2);
			
			disjunction.add(Restrictions.sqlRestriction("? between effective_Dt_From and effective_Dt_To", DateUtil.convertDateToTimestamp(form.effDateFromDate), StandardBasicTypes.TIMESTAMP));
			
			disjunction.add(Restrictions.sqlRestriction("? between effective_Dt_From and effective_Dt_To", DateUtil.convertDateToTimestamp(form.effDateToDate), StandardBasicTypes.TIMESTAMP));
			
			Conjunction c3 = Restrictions.conjunction();
			c3.add(Restrictions.ge("effectiveDtFrom", DateUtil.convertDateToTimestamp(DateUtil.convertDateTo0000Hours(form.effDateFromDate))));
			c3.add(Restrictions.le("effectiveDtTo", DateUtil.convertDateToTimestamp(DateUtil.convertDateTo2359Hours(form.effDateToDate))));
			disjunction.add(c3);
			
			criteria.add(disjunction);
		}

		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		return findDefaultMaxResultByCriteria(criteria);
	}
	
	@SuppressWarnings("unchecked")
	public List<Object[]> searchAcquirerMdr(SearchAcquirerMdrForm form){
		//TODO
//		DetachedCriteria criteria = DetachedCriteria.forClass(MstbAcquirerMdr.class);
//		criteria.createCriteria("mstbAcquirer", "a", org.hibernate.sql.JoinType.LEFT_OUTER_JOIN);
//		
//		if(form.acquirer!=null)
//			criteria.add(Restrictions.eq("a.acquirerNo", form.acquirer.getAcquirerNo()));
//		if(form.rate!=null)
//			criteria.add(Restrictions.eq("rate", form.rate));
//		if(form.effDateFromDate!=null && form.effDateToDate!=null){
//			
//			/*
//			 	E.g. Search input
//				From: 01/04/2010
//				To: 01/06/2010
//				
//				Should appear:
//				01/01/2010 to NULL
//				01/05/2010 to NULL
//				01/01/2010 to 30/04/2010
//				01/05/2010 to 31/05/2010
//				15/05/2010 to 01/07/2010
//				
//				Should not appear:
//				01/01/2010 to 01/03/2010
//				01/07/2010 to NULL
//				01/07/2010 to 01/08/2010
//		 */
//			
//			criteria.add(Restrictions.between("effectiveDate", form.effDateFromDate, form.effDateToDate));
//		}
//		
//		return this.findDefaultMaxResultByCriteria(criteria);
		
		
		this.getActiveDBTransaction();
		Session session = this.currentSession();
		List results = new ArrayList();
		try{
			Query query = session.getNamedQuery("searchAcquirerMDR");
			
			if(form.acquirer!=null) query.setParameter("acquirerNo", form.acquirer.getAcquirerNo(), StandardBasicTypes.INTEGER);
			else query.setParameter("acquirerNo", null, StandardBasicTypes.INTEGER);
			if(form.rate!=null) query.setParameter("rate", form.rate, StandardBasicTypes.BIG_DECIMAL);
			else query.setParameter("rate", null, StandardBasicTypes.BIG_DECIMAL);
			if(form.effDateFromDate!=null & form.effDateToDate!=null){
				query.setParameter("effectiveDateFrom", form.effDateFromDate, StandardBasicTypes.DATE);
				query.setParameter("effectiveDateTo", form.effDateToDate, StandardBasicTypes.DATE);
			}
			else{
				query.setParameter("effectiveDateFrom", null, StandardBasicTypes.DATE);
				query.setParameter("effectiveDateTo", null, StandardBasicTypes.DATE);
			}
			
			query.setMaxResults(ConfigurableConstants.getMaxQueryResult());
			results = query.list();
		}
		catch(Exception e){
			e.printStackTrace();
		}
		finally{
			session.close();
		}

		return results;
	}
	
	public MstbAcquirerPymtType getAcquirerPaymentType(Integer pymtTypeNo){
		DetachedCriteria criteria = DetachedCriteria.forClass(MstbAcquirerPymtType.class);
		criteria.createCriteria("mstbMasterTable", org.hibernate.sql.JoinType.LEFT_OUTER_JOIN);
		criteria.createCriteria("mstbAcquirer", "mstbacq", org.hibernate.sql.JoinType.LEFT_OUTER_JOIN);
		criteria.createCriteria("mstbAcquirerPymtComm", org.hibernate.sql.JoinType.LEFT_OUTER_JOIN);
		criteria.add(Restrictions.idEq(pymtTypeNo));
		
		List results = findAllByCriteria(criteria);
		if(results.isEmpty()) return null;
		else return (MstbAcquirerPymtType)results.get(0);
	}
	
	public MstbAcquirerMdr getAcquirerMdr(Integer mdrNo){
		DetachedCriteria criteria = DetachedCriteria.forClass(MstbAcquirerMdr.class);
		criteria.createCriteria("mstbAcquirer", "mstbacq", org.hibernate.sql.JoinType.LEFT_OUTER_JOIN);
		criteria.add(Restrictions.idEq(mdrNo));
		
		List results = findAllByCriteria(criteria);
		if(results.isEmpty()) return null;
		else return (MstbAcquirerMdr)results.get(0);
	}
	
	public FmtbNonBillableMaster getNonBillableMaster(Integer masterNo){
		DetachedCriteria criteria = DetachedCriteria.forClass(FmtbNonBillableMaster.class);
		DetachedCriteria detailCriteria = criteria.createCriteria("fmtbNonBillableDetails", org.hibernate.sql.JoinType.LEFT_OUTER_JOIN);
		DetachedCriteria arContCodeCriteria = detailCriteria.createCriteria("fmtbArContCodeMaster", org.hibernate.sql.JoinType.LEFT_OUTER_JOIN);
		arContCodeCriteria.createCriteria("fmtbEntityMaster", org.hibernate.sql.JoinType.LEFT_OUTER_JOIN);
		criteria.createCriteria("mstbMasterTableByServiceProvider", org.hibernate.sql.JoinType.LEFT_OUTER_JOIN);
		criteria.createCriteria("mstbMasterTableByPymtTypeMasterNo", org.hibernate.sql.JoinType.LEFT_OUTER_JOIN);
		criteria.add(Restrictions.eq("masterNo", masterNo));
		List results = findAllByCriteria(criteria);
		if(results.isEmpty()) return null;
		else return (FmtbNonBillableMaster)results.get(0);
	}
	
	public FmtbBankPaymentMaster getBankPaymentMaster(Integer masterNo){
		DetachedCriteria criteria = DetachedCriteria.forClass(FmtbBankPaymentMaster.class);
		DetachedCriteria detail = criteria.createCriteria("fmtbBankPaymentDetails", org.hibernate.sql.JoinType.LEFT_OUTER_JOIN);
		DetachedCriteria arCont = detail.createCriteria("fmtbArContCodeMaster", org.hibernate.sql.JoinType.LEFT_OUTER_JOIN);
		arCont.createCriteria("fmtbEntityMaster", org.hibernate.sql.JoinType.LEFT_OUTER_JOIN);
		criteria.createCriteria("mstbAcquirer", org.hibernate.sql.JoinType.LEFT_OUTER_JOIN);
		
		criteria.add(Restrictions.eq("masterNo", masterNo));
		List results = findAllByCriteria(criteria);
		if(results.isEmpty()) return null;
		else return (FmtbBankPaymentMaster)results.get(0);
	}
	public FmtbBankPaymentDetail getBankPaymentDetail(Integer detailNo){
		DetachedCriteria criteria = DetachedCriteria.forClass(FmtbBankPaymentDetail.class);
		criteria.createCriteria("fmtbBankPaymentMaster", org.hibernate.sql.JoinType.LEFT_OUTER_JOIN);
		DetachedCriteria arContCodecriteria = criteria.createCriteria("fmtbArContCodeMaster", org.hibernate.sql.JoinType.LEFT_OUTER_JOIN);
		arContCodecriteria.createCriteria("fmtbEntityMaster", org.hibernate.sql.JoinType.LEFT_OUTER_JOIN);
		
		criteria.add(Restrictions.eq("detailNo", detailNo));
		
		List results = findAllByCriteria(criteria);
		if(results.isEmpty()) return null;
		else return (FmtbBankPaymentDetail)results.get(0);
	}
	
	public FmtbNonBillableDetail getNonBillableDetail(Integer detailNo){
		DetachedCriteria criteria = DetachedCriteria.forClass(FmtbNonBillableDetail.class);
		criteria.createCriteria("fmtbNonBillableMaster", org.hibernate.sql.JoinType.LEFT_OUTER_JOIN);
		DetachedCriteria arContCodecriteria = criteria.createCriteria("fmtbArContCodeMaster", org.hibernate.sql.JoinType.LEFT_OUTER_JOIN);
		arContCodecriteria.createCriteria("fmtbEntityMaster", org.hibernate.sql.JoinType.LEFT_OUTER_JOIN);
		
		criteria.add(Restrictions.eq("detailNo", detailNo));
		
		List results = findAllByCriteria(criteria);
		if(results.isEmpty()) return null;
		else return (FmtbNonBillableDetail)results.get(0);
	}
	
	public boolean hasDuplicateRecord(MstbAcquirerMdr acquirerMdr){
		DetachedCriteria criteria = DetachedCriteria.forClass(MstbAcquirerMdr.class);
		criteria.createCriteria("mstbAcquirer", "mstbacq", org.hibernate.sql.JoinType.LEFT_OUTER_JOIN);
		criteria.add(Restrictions.eq("mstbacq.acquirerNo", acquirerMdr.getMstbAcquirer().getAcquirerNo()));
		criteria.add(Restrictions.eq("effectiveDate", acquirerMdr.getEffectiveDate()));
		if(acquirerMdr.getMdrNo()!=null){
			criteria.add(Restrictions.ne("mdrNo", acquirerMdr.getMdrNo()));
		}
		return findAllByCriteria(criteria).size() > 0;
	}
	
	public boolean hasDuplicateRecord(FmtbNonBillableMaster master, FmtbNonBillableDetail detail){
		DetachedCriteria criteria = DetachedCriteria.forClass(FmtbNonBillableDetail.class);
		DetachedCriteria mastercriteria = criteria.createCriteria("fmtbNonBillableMaster", org.hibernate.sql.JoinType.LEFT_OUTER_JOIN);
		criteria.add(Restrictions.eq("effectiveDate", detail.getEffectiveDate()));
		if(detail.getDetailNo()!=null)
			criteria.add(Restrictions.ne("detailNo", detail.getDetailNo()));
		
		mastercriteria.add(Restrictions.eq("masterNo", master.getMasterNo()));
		return findAllByCriteria(criteria).size() > 0;
	}
	
	public boolean hasDuplicateRecord(MstbAcquirerPymtType acquirerPymtType) {
		DetachedCriteria criteria = DetachedCriteria.forClass(MstbAcquirerPymtType.class);
		criteria.createCriteria("mstbAcquirer", "mstbacq", org.hibernate.sql.JoinType.LEFT_OUTER_JOIN);
		criteria.createCriteria("mstbMasterTable", "mstbmt", org.hibernate.sql.JoinType.LEFT_OUTER_JOIN);
		if(acquirerPymtType.getPymtTypeNo()!=null)
			criteria.add(Restrictions.ne("pymtTypeNo", acquirerPymtType.getPymtTypeNo()));
//		criteria.add(Restrictions.eq("mstbacq.acquirerNo", acquirerPymtType.getMstbAcquirer().getAcquirerNo()));
		criteria.add(Restrictions.eq("mstbmt.masterNo", acquirerPymtType.getMstbMasterTable().getMasterNo()));
		
		Disjunction disjunction2 = Restrictions.disjunction();
		
		Conjunction conjunction = Restrictions.conjunction();
		conjunction.add(Restrictions.sqlRestriction("? between effective_Dt_From and effective_Dt_To", acquirerPymtType.getEffectiveDtFrom(), StandardBasicTypes.TIMESTAMP));
		
		if(acquirerPymtType.getEffectiveDtTo()!=null){
			conjunction.add(Restrictions.sqlRestriction("? between effective_Dt_From and effective_Dt_To", acquirerPymtType.getEffectiveDtTo(), StandardBasicTypes.TIMESTAMP));
			disjunction2.add(conjunction);
		}
		
		if(acquirerPymtType.getEffectiveDtTo()!=null){
			Conjunction conjunction2 = Restrictions.conjunction();
			conjunction2.add(Restrictions.sqlRestriction("? between effective_Dt_From and effective_Dt_To", acquirerPymtType.getEffectiveDtFrom(), StandardBasicTypes.TIMESTAMP));
			conjunction2.add(Restrictions.sqlRestriction("? >= effective_Dt_To", acquirerPymtType.getEffectiveDtTo(), StandardBasicTypes.TIMESTAMP));
			disjunction2.add(conjunction2);
			
			Conjunction conjunction3 = Restrictions.conjunction();
			conjunction3.add(Restrictions.sqlRestriction("? <= effective_Dt_From", acquirerPymtType.getEffectiveDtFrom(), StandardBasicTypes.TIMESTAMP));
			conjunction3.add(Restrictions.sqlRestriction("? >= effective_Dt_From", acquirerPymtType.getEffectiveDtTo(), StandardBasicTypes.TIMESTAMP));
			disjunction2.add(conjunction3);
			
			Conjunction conjunction4 = Restrictions.conjunction();
			conjunction4.add(Restrictions.sqlRestriction("? >= effective_Dt_From", acquirerPymtType.getEffectiveDtFrom(), StandardBasicTypes.TIMESTAMP));
			conjunction4.add(Restrictions.isNull("effectiveDtTo"));
			disjunction2.add(conjunction4);
		}
		else{
			Conjunction conjunction2 = Restrictions.conjunction();
			conjunction2.add(Restrictions.sqlRestriction("? between effective_Dt_From and effective_Dt_To", acquirerPymtType.getEffectiveDtFrom(), StandardBasicTypes.TIMESTAMP));
			disjunction2.add(conjunction2);
			
			Conjunction conjunction3 = Restrictions.conjunction();
			conjunction3.add(Restrictions.sqlRestriction("? <= effective_Dt_From", acquirerPymtType.getEffectiveDtFrom(), StandardBasicTypes.TIMESTAMP));
			disjunction2.add(conjunction3);
			
			Conjunction conjunction4 = Restrictions.conjunction();
			conjunction4.add(Restrictions.sqlRestriction("? >= effective_Dt_From", acquirerPymtType.getEffectiveDtFrom(), StandardBasicTypes.TIMESTAMP));
			conjunction4.add(Restrictions.isNull("effectiveDtTo"));
			disjunction2.add(conjunction4);
		}

		criteria.add(disjunction2);
		
		return findAllByCriteria(criteria).size() > 0;
	}
	
	@SuppressWarnings("unchecked")
	public List<MstbMasterTable> getPaymentType(MstbAcquirer acquirer){
		DetachedCriteria criteria = DetachedCriteria.forClass(MstbMasterTable.class);
		DetachedCriteria acquirerPymtTypeCriteria = criteria.createCriteria("mstbAcquirerPymtTypes","mapy", org.hibernate.sql.JoinType.LEFT_OUTER_JOIN);
		DetachedCriteria acquirerCriteria = acquirerPymtTypeCriteria.createCriteria("mstbAcquirer", "ma",  org.hibernate.sql.JoinType.LEFT_OUTER_JOIN);
		
		acquirerCriteria.add(Restrictions.idEq(acquirer.getAcquirerNo()));
		acquirerPymtTypeCriteria.add(Restrictions.le("effectiveDtFrom", DateUtil.getCurrentDate()));
		acquirerPymtTypeCriteria.add(Restrictions.isNotNull("pymtTypeNo"));
		
		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		
		return findAllByCriteria(criteria);
	}
	
	public boolean isAcquirerDeletable(int acquirer_no){
		DetachedCriteria criteria = DetachedCriteria.forClass(MstbAcquirer.class);
		criteria.createCriteria("mstbAcquirerMdrs", "mdr", org.hibernate.sql.JoinType.LEFT_OUTER_JOIN);
		criteria.createCriteria("mstbAcquirerPymtTypes", "pymtType", org.hibernate.sql.JoinType.LEFT_OUTER_JOIN);
		
		criteria.add(Restrictions.idEq(acquirer_no));
		criteria.add(Restrictions.or(
				Restrictions.isNotNull("mdr.mdrNo"), 
				Restrictions.isNotNull("pymtType.pymtTypeNo")
			)
		);
		

		if (findAllByCriteria(criteria).size() > 0) {
			return false;
		} else {
			return true;
		}
	}
	
	@SuppressWarnings("unchecked")
	public List<MstbAcquirer> getAcquirer() {
		DetachedCriteria criteria = DetachedCriteria.forClass(MstbAcquirer.class);
		criteria.addOrder(Order.asc("name"));
		return findAllByCriteria(criteria);
	}
	
	@SuppressWarnings("unchecked")
	public List<String> getTransactionCodeType(Integer entityNo, String txn_type){
		DetachedCriteria criteria = DetachedCriteria.forClass(FmtbTransactionCode.class);
		DetachedCriteria entityCriteria = criteria.createCriteria("fmtbEntityMaster", org.hibernate.sql.JoinType.LEFT_OUTER_JOIN);
		
		criteria.add(Restrictions.eq("txnType", txn_type));
		entityCriteria.add(Restrictions.idEq(entityNo));
		criteria.setProjection(Projections.distinct(Projections.property("txnCode")));
		criteria.addOrder(Order.asc("txnCode"));
		
		return findAllByCriteria(criteria);
	}
	
	public boolean hasDuplicateName(Integer promoNo, String name){
		
		DetachedCriteria criteria = DetachedCriteria.forClass(MstbPromotion.class);
		DetachedCriteria promoDetailCriteria = criteria.createCriteria("currentPromoDetail", org.hibernate.sql.JoinType.LEFT_OUTER_JOIN);
		promoDetailCriteria.add(Restrictions.eq("name", name));
		
		if(promoNo!=null)
			criteria.add(Restrictions.not(Restrictions.idEq(promoNo)));
		
		List results = findAllByCriteria(criteria);
		if(!results.isEmpty()) 
			return true;
		else 
			return false;

	}
	
	
	public boolean hasDuplicateCashPlusPromoCode(String promoCode){
		
		DetachedCriteria criteria = DetachedCriteria.forClass(MstbPromotionCashPlus.class);
		criteria.add(Restrictions.eq("promoCode", promoCode));
		List results = findAllByCriteria(criteria);
	
		return (!results.isEmpty()? true: false);

	}
	

	@SuppressWarnings("unchecked")
	public List<MstbPromotion> searchCurrentPromotion(SearchPromotionForm form){
		DetachedCriteria criteria = DetachedCriteria.forClass(MstbPromotion.class);
		DetachedCriteria promoDetailCriteria = criteria.createCriteria("currentPromoDetail", "promoDetail", org.hibernate.sql.JoinType.LEFT_OUTER_JOIN);
		DetachedCriteria prodTypeCriteria = promoDetailCriteria.createCriteria("pmtbProductType", org.hibernate.sql.JoinType.LEFT_OUTER_JOIN);
		DetachedCriteria jobTypeCriteria = promoDetailCriteria.createCriteria("mstbMasterTableByJobType", org.hibernate.sql.JoinType.LEFT_OUTER_JOIN);
		DetachedCriteria vehicleModelCriteria = promoDetailCriteria.createCriteria("mstbMasterTableByVehicleModel", org.hibernate.sql.JoinType.LEFT_OUTER_JOIN);

		if(form.name!=null && form.name.length()>0)
			promoDetailCriteria.add(Restrictions.like("name", form.name, MatchMode.ANYWHERE));
		if(form.type!=null && form.type.length()>0)
			promoDetailCriteria.add(Restrictions.eq("type", form.type));
		if(form.productType!=null)
			prodTypeCriteria.add(Restrictions.eq("productTypeId", form.productType.getProductTypeId()));
		if(form.promoType!=null && form.promoType.length()>0)
			promoDetailCriteria.add(Restrictions.eq("promoType", form.promoType));
		if(form.effDateFrom!=null && form.effDateTo!=null){
			
			/*
			 	E.g. Search input
				From: 01/04/2010
				To: 01/06/2010
				
				Should appear:
				01/01/2010 to NULL
				01/05/2010 to NULL
				01/01/2010 to 30/04/2010
				01/05/2010 to 31/05/2010
				15/05/2010 to 01/07/2010
				
				Should not appear:
				01/01/2010 to 01/03/2010
				01/07/2010 to NULL
				01/07/2010 to 01/08/2010
			 */
			
			Disjunction disjunction = Restrictions.disjunction();
			
			Conjunction c1 = Restrictions.conjunction();
			c1.add(Restrictions.le("effectiveDtFrom", form.effDateFrom));
			c1.add(Restrictions.isNull("effectiveDtTo"));
			disjunction.add(c1);
			
			Conjunction c2 = Restrictions.conjunction();
			c2.add(Restrictions.le("effectiveDtFrom", form.effDateTo));
			c2.add(Restrictions.isNull("effectiveDtTo"));
			disjunction.add(c2);
			
			disjunction.add(Restrictions.sqlRestriction("? between {alias}.effective_Dt_From and {alias}.effective_Dt_To", DateUtil.convertDateToTimestamp(form.effDateFrom), StandardBasicTypes.TIMESTAMP));
			
			disjunction.add(Restrictions.sqlRestriction("? between {alias}.effective_Dt_From and {alias}.effective_Dt_To", DateUtil.convertDateToTimestamp(form.effDateTo), StandardBasicTypes.TIMESTAMP));
			
			Conjunction c3 = Restrictions.conjunction();
			c3.add(Restrictions.ge("effectiveDtFrom", form.effDateFrom));
			c3.add(Restrictions.le("effectiveDtTo", form.effDateTo));
			disjunction.add(c3);
			
			promoDetailCriteria.add(disjunction);
		}
		if(form.jobType!=null && form.jobType.length()>0){
			MstbMasterTable master = ConfigurableConstants.getMasterTable(ConfigurableConstants.JOB_TYPE, form.jobType);
			jobTypeCriteria.add(Restrictions.idEq(master.getMasterNo()));
		}
		if(form.vehicleModel!=null && form.vehicleModel.length()>0){
			MstbMasterTable master = ConfigurableConstants.getMasterTable(ConfigurableConstants.VEHICLE_MODEL, form.vehicleModel);
			vehicleModelCriteria.add(Restrictions.idEq(master.getMasterNo()));
		}
		if(form.status!=null && form.status.length()>0){
			criteria.add(Restrictions.eq("currentStatus", form.status));
		}
		
		return findDefaultMaxResultByCriteria(criteria);
	}
	
	public List<MstbPromotion> searchLastPromoReq(SearchPromotionForm form){
		
		DetachedCriteria criteria = DetachedCriteria.forClass(MstbPromotion.class);
		DetachedCriteria lastPromoReqCriteria = criteria.createCriteria("lastPromoReq", org.hibernate.sql.JoinType.LEFT_OUTER_JOIN);
		DetachedCriteria fromPromoDetailCriteria = lastPromoReqCriteria.createCriteria("fromPromoDetail", org.hibernate.sql.JoinType.LEFT_OUTER_JOIN);
		DetachedCriteria lastPromoReqFlowCriteria  = lastPromoReqCriteria.createCriteria("lastPromoReqFlow", org.hibernate.sql.JoinType.LEFT_OUTER_JOIN);
		
		DetachedCriteria toPromoDetailCriteria = lastPromoReqFlowCriteria.createCriteria("toPromoDetail", "toPromoDetail", org.hibernate.sql.JoinType.LEFT_OUTER_JOIN);

		DetachedCriteria prodTypeCriteria = toPromoDetailCriteria.createCriteria("pmtbProductType", org.hibernate.sql.JoinType.LEFT_OUTER_JOIN);
		DetachedCriteria jobTypeCriteria = toPromoDetailCriteria.createCriteria("mstbMasterTableByJobType", org.hibernate.sql.JoinType.LEFT_OUTER_JOIN);
		DetachedCriteria vehicleModelCriteria = toPromoDetailCriteria.createCriteria("mstbMasterTableByVehicleModel", org.hibernate.sql.JoinType.LEFT_OUTER_JOIN);
		
		if(form.name!=null && form.name.length()>0)
			toPromoDetailCriteria.add(Restrictions.like("name", form.name, MatchMode.ANYWHERE));
		if(form.type!=null && form.type.length()>0)
			toPromoDetailCriteria.add(Restrictions.eq("type", form.type));
		if(form.productType!=null)
			prodTypeCriteria.add(Restrictions.eq("productTypeId", form.productType.getProductTypeId()));
		if(form.promoType!=null && form.promoType.length()>0)
			toPromoDetailCriteria.add(Restrictions.eq("promoType", form.promoType));
		if(form.effDateFrom!=null && form.effDateTo!=null){
			
			Disjunction disjunction = Restrictions.disjunction();
			
			Conjunction c1 = Restrictions.conjunction();
			c1.add(Restrictions.le("effectiveDtFrom", form.effDateFrom));
			c1.add(Restrictions.isNull("effectiveDtTo"));
			disjunction.add(c1);
			
			Conjunction c2 = Restrictions.conjunction();
			c2.add(Restrictions.le("effectiveDtFrom", form.effDateTo));
			c2.add(Restrictions.isNull("effectiveDtTo"));
			disjunction.add(c2);
			
			disjunction.add(Restrictions.sqlRestriction("? between {alias}.effective_Dt_From and {alias}.effective_Dt_To", DateUtil.convertDateToTimestamp(form.effDateFrom), StandardBasicTypes.TIMESTAMP));
			
			disjunction.add(Restrictions.sqlRestriction("? between {alias}.effective_Dt_From and {alias}.effective_Dt_To", DateUtil.convertDateToTimestamp(form.effDateTo), StandardBasicTypes.TIMESTAMP));
			
			Conjunction c3 = Restrictions.conjunction();
			c3.add(Restrictions.ge("effectiveDtFrom", form.effDateFrom));
			c3.add(Restrictions.le("effectiveDtTo", form.effDateTo));
			disjunction.add(c3);
		
			toPromoDetailCriteria.add(disjunction);
		}
		if(form.jobType!=null && form.jobType.length()>0){
			MstbMasterTable master = ConfigurableConstants.getMasterTable(ConfigurableConstants.JOB_TYPE, form.jobType);
			jobTypeCriteria.add(Restrictions.idEq(master.getMasterNo()));
		}
		if(form.vehicleModel!=null && form.vehicleModel.length()>0){
			MstbMasterTable master = ConfigurableConstants.getMasterTable(ConfigurableConstants.VEHICLE_MODEL, form.vehicleModel);
			vehicleModelCriteria.add(Restrictions.idEq(master.getMasterNo()));
		}
		if(form.status!=null && form.status.length()>0){
			lastPromoReqFlowCriteria.add(Restrictions.eq("toStatus", form.status));
		}
		
		return findDefaultMaxResultByCriteria(criteria);
	}
	
	
	@SuppressWarnings("unchecked")
	public List<MstbPromotionCashPlus> searchPrepaidPromotion(SearchPrepaidPromotionForm form){
		
		DetachedCriteria criteria = DetachedCriteria.forClass(MstbPromotionCashPlus.class, "promo");
		
		if(!Strings.isNullOrEmpty(form.promoCode)){
			criteria.add(Restrictions.like("promoCode", form.promoCode, MatchMode.ANYWHERE));
		}
		
		if(form.effDateFrom!=null && form.effDateTo!=null){
			
			Disjunction disjunction = Restrictions.disjunction();
					
			Conjunction c1 = Restrictions.conjunction();
			c1.add(Restrictions.le("effectiveDtFrom", form.effDateFrom));
			c1.add(Restrictions.isNull("effectiveDtTo"));
			disjunction.add(c1);
			
			Conjunction c2 = Restrictions.conjunction();
			c2.add(Restrictions.le("effectiveDtFrom", form.effDateTo));
			c2.add(Restrictions.isNull("effectiveDtTo"));
			disjunction.add(c2);
			
			disjunction.add(Restrictions.sqlRestriction("? between {alias}.effective_Dt_From and {alias}.effective_Dt_To", DateUtil.convertDateToTimestamp(form.effDateFrom), StandardBasicTypes.TIMESTAMP));
			
			disjunction.add(Restrictions.sqlRestriction("? between {alias}.effective_Dt_From and {alias}.effective_Dt_To", DateUtil.convertDateToTimestamp(form.effDateTo), StandardBasicTypes.TIMESTAMP));
			
			Conjunction c3 = Restrictions.conjunction();
			c3.add(Restrictions.ge("effectiveDtFrom", form.effDateFrom));
			c3.add(Restrictions.le("effectiveDtTo", form.effDateTo));
			disjunction.add(c3);
			
			criteria.add(disjunction);
		}
				
		
		return findDefaultMaxResultByCriteria(criteria);
	}
	
	@SuppressWarnings("unchecked")
	public List<MstbPromotionCashPlus> getEffectivePrepaidPromotions(){
		
		DetachedCriteria criteria = DetachedCriteria.forClass(MstbPromotionCashPlus.class);
		
		java.util.Date currentDate = new java.util.Date();
	
		Disjunction disj = Restrictions.disjunction();
		disj.add(Restrictions.isNull("effectiveDtTo"));
		disj.add(Restrictions.ge("effectiveDtTo", currentDate));

		criteria.add(Restrictions.le("effectiveDtFrom", currentDate));
		criteria.add(disj);
		
		return findDefaultMaxResultByCriteria(criteria);
	}

	
	public List<MstbPromoReq> searchPromoPlanHistory(SearchPromoPlanHistoryForm form){
		
		DetachedCriteria criteria = DetachedCriteria.forClass(MstbPromoReq.class);
		criteria.createCriteria("mstbPromotion", org.hibernate.sql.JoinType.LEFT_OUTER_JOIN);
		criteria.createCriteria("fromPromoDetail", org.hibernate.sql.JoinType.LEFT_OUTER_JOIN);
		
		DetachedCriteria promoReqFlowCriteria  = criteria.createCriteria("mstbPromoReqFlows", org.hibernate.sql.JoinType.LEFT_OUTER_JOIN);
		promoReqFlowCriteria.add(Restrictions.eq("reqFromStatus", NonConfigurableConstants.PROMOTION_REQUEST_STATUS_NEW));
		
		DetachedCriteria toPromoDetailCriteria = promoReqFlowCriteria.createCriteria("toPromoDetail", org.hibernate.sql.JoinType.LEFT_OUTER_JOIN);
		
		DetachedCriteria prodTypeCriteria = toPromoDetailCriteria.createCriteria("pmtbProductType", org.hibernate.sql.JoinType.LEFT_OUTER_JOIN);
		DetachedCriteria jobTypeCriteria = toPromoDetailCriteria.createCriteria("mstbMasterTableByJobType", org.hibernate.sql.JoinType.LEFT_OUTER_JOIN);
		DetachedCriteria vehicleModelCriteria = toPromoDetailCriteria.createCriteria("mstbMasterTableByVehicleModel", org.hibernate.sql.JoinType.LEFT_OUTER_JOIN);
		
		if(form.name!=null && form.name.length()>0)
			toPromoDetailCriteria.add(Restrictions.like("name", form.name, MatchMode.ANYWHERE));
		if(form.type!=null && form.type.length()>0)
			toPromoDetailCriteria.add(Restrictions.eq("type", form.type));
		if(form.productType!=null)
			prodTypeCriteria.add(Restrictions.eq("productTypeId", form.productType.getProductTypeId()));
		if(form.promoType!=null && form.promoType.length()>0)
			toPromoDetailCriteria.add(Restrictions.eq("promoType", form.promoType));
		if(form.effDateFrom!=null && form.effDateTo!=null){
			
			Disjunction disjunction = Restrictions.disjunction();
			
			Conjunction c1 = Restrictions.conjunction();
			c1.add(Restrictions.isNull("effectiveDtTo"));
			c1.add(Restrictions.le("effectiveDtFrom", form.effDateFrom));
			disjunction.add(c1);
			
			disjunction.add(Restrictions.between("effectiveDtFrom", form.effDateFrom, form.effDateTo));
			disjunction.add(Restrictions.between("effectiveDtTo", form.effDateFrom, form.effDateTo));
			
			toPromoDetailCriteria.add(disjunction);
		}
		if(form.jobType!=null && form.jobType.length()>0){
			MstbMasterTable master = ConfigurableConstants.getMasterTable(ConfigurableConstants.JOB_TYPE, form.jobType);
			jobTypeCriteria.add(Restrictions.idEq(master.getMasterNo()));
		}
		if(form.vehicleModel!=null && form.vehicleModel.length()>0){
			MstbMasterTable master = ConfigurableConstants.getMasterTable(ConfigurableConstants.VEHICLE_MODEL, form.vehicleModel);
			vehicleModelCriteria.add(Restrictions.idEq(master.getMasterNo()));
		}
		
		DetachedCriteria lastPromoReqFlowCriteria  = criteria.createCriteria("lastPromoReqFlow", org.hibernate.sql.JoinType.LEFT_OUTER_JOIN);
		if(form.reqToStatus!=null && form.reqToStatus.length()>0){
			lastPromoReqFlowCriteria.add(Restrictions.eq("reqToStatus", form.reqToStatus));
		} else {
			lastPromoReqFlowCriteria.add(Restrictions.in("reqToStatus", new String[]{NonConfigurableConstants.PROMOTION_REQUEST_STATUS_APPROVED, NonConfigurableConstants.PROMOTION_REQUEST_STATUS_REJECTED}));
		}
		
		lastPromoReqFlowCriteria.addOrder(Order.desc("createdDt"));
		
		return findDefaultMaxResultByCriteria(criteria);
	}
	
	
	@SuppressWarnings("unchecked")
	public List<MstbPromoReq> getPendingPromoReq(){
		DetachedCriteria criteria = DetachedCriteria.forClass(MstbPromoReq.class);
		DetachedCriteria promotionCriteria = criteria.createCriteria("mstbPromotion", org.hibernate.sql.JoinType.LEFT_OUTER_JOIN);
		DetachedCriteria currentPromoDetailCriteria =promotionCriteria.createCriteria("currentPromoDetail", org.hibernate.sql.JoinType.LEFT_OUTER_JOIN);
		currentPromoDetailCriteria.createCriteria("pmtbProductType", org.hibernate.sql.JoinType.LEFT_OUTER_JOIN);
		currentPromoDetailCriteria.createCriteria("mstbMasterTableByJobType", org.hibernate.sql.JoinType.LEFT_OUTER_JOIN);
		currentPromoDetailCriteria.createCriteria("mstbMasterTableByVehicleModel", org.hibernate.sql.JoinType.LEFT_OUTER_JOIN);
		
		DetachedCriteria fromPromoDetailCriteria = criteria.createCriteria("fromPromoDetail", org.hibernate.sql.JoinType.LEFT_OUTER_JOIN);
		
		fromPromoDetailCriteria.createCriteria("pmtbProductType", org.hibernate.sql.JoinType.LEFT_OUTER_JOIN);
		fromPromoDetailCriteria.createCriteria("mstbMasterTableByJobType", org.hibernate.sql.JoinType.LEFT_OUTER_JOIN);
		fromPromoDetailCriteria.createCriteria("mstbMasterTableByVehicleModel", org.hibernate.sql.JoinType.LEFT_OUTER_JOIN);
		
		DetachedCriteria lastPromoReqFlowCriteria  = criteria.createCriteria("lastPromoReqFlow", org.hibernate.sql.JoinType.LEFT_OUTER_JOIN);
		lastPromoReqFlowCriteria.add(Restrictions.eq("reqToStatus", NonConfigurableConstants.PROMOTION_REQUEST_STATUS_PENDING));
		
		DetachedCriteria toPromoDetailCriteria = lastPromoReqFlowCriteria.createCriteria("toPromoDetail", org.hibernate.sql.JoinType.LEFT_OUTER_JOIN);
		toPromoDetailCriteria.createCriteria("pmtbProductType", org.hibernate.sql.JoinType.LEFT_OUTER_JOIN);
		toPromoDetailCriteria.createCriteria("mstbMasterTableByJobType", org.hibernate.sql.JoinType.LEFT_OUTER_JOIN);
		toPromoDetailCriteria.createCriteria("mstbMasterTableByVehicleModel", org.hibernate.sql.JoinType.LEFT_OUTER_JOIN);
		
		lastPromoReqFlowCriteria.addOrder(Order.desc("createdDt"));
		
		return findDefaultMaxResultByCriteria(criteria);
	}
	
	@SuppressWarnings("unchecked")
	public MstbPromoReq getPromoReq(Integer promoReqNo){
		DetachedCriteria criteria = DetachedCriteria.forClass(MstbPromoReq.class);
		 criteria.createCriteria("mstbPromotion", org.hibernate.sql.JoinType.LEFT_OUTER_JOIN);
		DetachedCriteria fromPromoDetailCriteria = criteria.createCriteria("fromPromoDetail", org.hibernate.sql.JoinType.LEFT_OUTER_JOIN);
		DetachedCriteria promoReqFlowCriteria = criteria.createCriteria("mstbPromoReqFlows", org.hibernate.sql.JoinType.LEFT_OUTER_JOIN);
		DetachedCriteria toPromoDetailCriteria = promoReqFlowCriteria.createCriteria("toPromoDetail", org.hibernate.sql.JoinType.LEFT_OUTER_JOIN);
		
		fromPromoDetailCriteria.createCriteria("pmtbProductType", org.hibernate.sql.JoinType.LEFT_OUTER_JOIN);
		fromPromoDetailCriteria.createCriteria("mstbMasterTableByJobType", org.hibernate.sql.JoinType.LEFT_OUTER_JOIN);
		fromPromoDetailCriteria.createCriteria("mstbMasterTableByVehicleModel", org.hibernate.sql.JoinType.LEFT_OUTER_JOIN);
		
		toPromoDetailCriteria.createCriteria("pmtbProductType", org.hibernate.sql.JoinType.LEFT_OUTER_JOIN);
		toPromoDetailCriteria.createCriteria("mstbMasterTableByJobType", org.hibernate.sql.JoinType.LEFT_OUTER_JOIN);
		toPromoDetailCriteria.createCriteria("mstbMasterTableByVehicleModel", org.hibernate.sql.JoinType.LEFT_OUTER_JOIN);
		
		criteria.add(Restrictions.idEq(promoReqNo));
		
		List results = findAllByCriteria(criteria);
		if(results.isEmpty()) return null;
		else return (MstbPromoReq)results.get(0);
	}
	
	
	@SuppressWarnings({"unused"})
	public MstbPromotion getPromotion(Integer promoNo){
		DetachedCriteria criteria = DetachedCriteria.forClass(MstbPromotion.class);
		
		DetachedCriteria promoDetailCriteria =criteria.createCriteria("currentPromoDetail", org.hibernate.sql.JoinType.LEFT_OUTER_JOIN);
		DetachedCriteria cpdProdTypeCriteria = promoDetailCriteria.createCriteria("pmtbProductType", org.hibernate.sql.JoinType.LEFT_OUTER_JOIN);
		DetachedCriteria cpdJobTypeCriteria = promoDetailCriteria.createCriteria("mstbMasterTableByJobType", org.hibernate.sql.JoinType.LEFT_OUTER_JOIN);
		DetachedCriteria cpdVehicleModelCriteria = promoDetailCriteria.createCriteria("mstbMasterTableByVehicleModel", org.hibernate.sql.JoinType.LEFT_OUTER_JOIN);
		
		DetachedCriteria lastPromoReqCriteria =criteria.createCriteria("lastPromoReq", org.hibernate.sql.JoinType.LEFT_OUTER_JOIN);
		
		DetachedCriteria fromPromoDetailCriteria =lastPromoReqCriteria.createCriteria("fromPromoDetail", org.hibernate.sql.JoinType.LEFT_OUTER_JOIN);
		DetachedCriteria fpdProdTypeCriteria = fromPromoDetailCriteria.createCriteria("pmtbProductType", org.hibernate.sql.JoinType.LEFT_OUTER_JOIN);
		DetachedCriteria fpdJobTypeCriteria = fromPromoDetailCriteria.createCriteria("mstbMasterTableByJobType", org.hibernate.sql.JoinType.LEFT_OUTER_JOIN);
		DetachedCriteria fpdVehicleModelCriteria = fromPromoDetailCriteria.createCriteria("mstbMasterTableByVehicleModel", org.hibernate.sql.JoinType.LEFT_OUTER_JOIN);
		
		DetachedCriteria lastPromoReqFlowCriteria =lastPromoReqCriteria.createCriteria("lastPromoReqFlow", org.hibernate.sql.JoinType.LEFT_OUTER_JOIN);
		DetachedCriteria toPromoDetailCriteria =lastPromoReqFlowCriteria.createCriteria("toPromoDetail", org.hibernate.sql.JoinType.LEFT_OUTER_JOIN);
		DetachedCriteria tpdProdTypeCriteria = toPromoDetailCriteria.createCriteria("pmtbProductType", org.hibernate.sql.JoinType.LEFT_OUTER_JOIN);
		DetachedCriteria tpdJobTypeCriteria = toPromoDetailCriteria.createCriteria("mstbMasterTableByJobType", org.hibernate.sql.JoinType.LEFT_OUTER_JOIN);
		DetachedCriteria tpdVehicleModelCriteria = toPromoDetailCriteria.createCriteria("mstbMasterTableByVehicleModel", org.hibernate.sql.JoinType.LEFT_OUTER_JOIN);
		
		criteria.add(Restrictions.idEq(promoNo));
		
		List results = findAllByCriteria(criteria);
		if(results.isEmpty()) return null;
		else return (MstbPromotion)results.get(0);
	}
	
	public MstbPromotionCashPlus getPromotionCashPlus(String promoCode){
		DetachedCriteria criteria = DetachedCriteria.forClass(MstbPromotionCashPlus.class);
		criteria.add(Restrictions.idEq(promoCode));
		
		List results = findAllByCriteria(criteria);
		if(results.isEmpty()){
			return null;
		}
		else {
			return (MstbPromotionCashPlus)results.get(0);
		}
	}
	
	
	
	@SuppressWarnings("unchecked")
	public void setBankBranchDefaultNo(Integer entityNo,String userLoginId){
		DetachedCriteria criteria = DetachedCriteria.forClass(FmtbBankCode.class);
		criteria.add(Restrictions.eq("fmtbEntityMaster.entityNo", entityNo));
		criteria.add(Restrictions.eq("isDefault", NonConfigurableConstants.BOOLEAN_YN_YES));
		
		List<FmtbBankCode> results = this.findDefaultMaxResultByCriteria(criteria);
		while(results.isEmpty() == false){
			for(FmtbBankCode bankCode : results){
				bankCode.setIsDefault(NonConfigurableConstants.BOOLEAN_YN_NO);
				this.update(bankCode,userLoginId);
			}
			this.getHibernateTemplate().flush();
			
			results = this.findDefaultMaxResultByCriteria(criteria);
		}
		
		//Below method does not work with weblogic ear file deployment
//		int recordsUpdated = this.getHibernateTemplate().bulkUpdate(
//				//HQL
//				"Update FmtbBankCode " +
//				"set isDefault=? " +
//				"where fmtbEntityMaster.entityNo=? and isDefault=?",
//				//Parameters
//				new Object[]{NonConfigurableConstants.BOOLEAN_YN_NO, entityNo,
//						NonConfigurableConstants.BOOLEAN_YN_YES});
//		
//		this.logger.info("updated "+recordsUpdated+" records...");
	}
	
	@SuppressWarnings("unchecked")
	public void setBankBranchDefaultYes(Integer entityNo, String bankCode, String branchCode, String userLoginId){
		DetachedCriteria criteria = DetachedCriteria.forClass(FmtbBankCode.class);
		criteria.add(Restrictions.eq("fmtbEntityMaster.entityNo", entityNo));
		criteria.add(Restrictions.eq("bankCode", bankCode));
		criteria.add(Restrictions.eq("branchCode", branchCode));
		criteria.add(Restrictions.eq("isDefault", NonConfigurableConstants.BOOLEAN_YN_NO));
		
		List<FmtbBankCode> results = this.findDefaultMaxResultByCriteria(criteria);
		while(results.isEmpty() == false){
			for(FmtbBankCode fmtbBankCode : results){
				fmtbBankCode.setIsDefault(NonConfigurableConstants.BOOLEAN_YN_YES);
				this.update(fmtbBankCode,userLoginId);
			}
			this.getHibernateTemplate().flush();
			
			results = this.findDefaultMaxResultByCriteria(criteria);
		}
		
		//Below method does not work with weblogic ear file deployment
//		int recordsUpdated = this.getHibernateTemplate().bulkUpdate(
//				//HQL
//				"Update FmtbBankCode " +
//				"set isDefault=? " +
//				"where fmtbEntityMaster.entityNo=? and bankCode=? and branchCode=?",
//				//Parameters
//				new Object[]{NonConfigurableConstants.BOOLEAN_YN_YES, entityNo,
//						bankCode, branchCode});
//		
//		this.logger.info("updated "+recordsUpdated+" records...");
	}
	
	public boolean hasDuplicateEffectiveDate(FmtbArContCodeDetail detail) {
		DetachedCriteria criteria = DetachedCriteria.forClass(FmtbArContCodeDetail.class);
		criteria.add(Restrictions.eq("fmtbArContCodeMaster", detail.getFmtbArContCodeMaster()));
		criteria.add(Restrictions.eq("effectiveDt", detail.getEffectiveDt()));
		
		if (detail.getArContCodeDetailNo() != null) {
			criteria.add(Restrictions.ne("arContCodeDetailNo", detail.getArContCodeDetailNo()));
		}
		return findAllByCriteria(criteria).size() > 0;
	}
	
	/*
	 * This method is to check whether ar cont code has been used.
	 * If not, the edit screen will allows to change the entity.
	 * Need to append new tables that reference to this table over here.
	 */
	public boolean isArContCodeBeenUsed(Integer arControlCodeNo){
		
		DetachedCriteria criteria = DetachedCriteria.forClass(AmtbAccount.class);
		criteria.add(Restrictions.eq("fmtbArContCodeMaster.arControlCodeNo", arControlCodeNo));
		if(this.findMaxResultByCriteria(criteria, 1).size() > 0)
			return true;
		
		criteria = DetachedCriteria.forClass(AmtbApplication.class);
		criteria.add(Restrictions.eq("fmtbArContCodeMaster.arControlCodeNo", arControlCodeNo));
		if(this.findMaxResultByCriteria(criteria, 1).size() > 0)
			return true;
		
		criteria = DetachedCriteria.forClass(FmtbBankPaymentDetail.class);
		criteria.add(Restrictions.eq("fmtbArContCodeMaster.arControlCodeNo", arControlCodeNo));
		if(this.findMaxResultByCriteria(criteria, 1).size() > 0)
			return true;
		
		criteria = DetachedCriteria.forClass(FmtbNonBillableDetail.class);
		criteria.add(Restrictions.eq("fmtbArContCodeMaster.arControlCodeNo", arControlCodeNo));
		if(this.findMaxResultByCriteria(criteria, 1).size() > 0)
			return true;

		return false;
	}
	
	public FmtbTransactionCode getEarliestEffectedTxnCode(String txnType, String txnCode){
		DetachedCriteria criteria = DetachedCriteria.forClass(FmtbTransactionCode.class);
		criteria.add(Restrictions.eq("txnCode", txnCode));
		criteria.add(Restrictions.eq("txnType", txnType));
		criteria.addOrder(Order.asc("effectiveDate"));
		return (FmtbTransactionCode) this.findMaxResultByCriteria(criteria, 1).get(0);
	}
	
	@SuppressWarnings("unchecked")
	public List<LrtbRewardAccount> getActiveRewardsAccount(){
		DetachedCriteria criteria = DetachedCriteria.forClass(LrtbRewardAccount.class);
		criteria.add(Restrictions.eq("expiredBilledFlag", NonConfigurableConstants.BOOLEAN_NO));
		return this.findAllByCriteria(criteria);
	}
	
	public FmtbBankCode checkDefaultCollectionBankExist(Integer entityNo, Date effectiveDate, Integer bankCodeNo){
		DetachedCriteria criteria = DetachedCriteria.forClass(FmtbBankCode.class);
		DetachedCriteria entity = criteria.createCriteria("fmtbEntityMaster", org.hibernate.sql.JoinType.LEFT_OUTER_JOIN);
		entity.add(Restrictions.idEq(entityNo));
		criteria.add(Restrictions.eq("isDefault", NonConfigurableConstants.BOOLEAN_YES));
		criteria.add(Restrictions.eq("effectiveDate", effectiveDate));
		if(bankCodeNo != null){
			criteria.add(Restrictions.ne("bankCodeNo", bankCodeNo));
		}
		
		List result = this.findMaxResultByCriteria(criteria, 1);
		if(result.isEmpty()) return null;
		else return (FmtbBankCode) result.get(0);
	}
	
	public FmtbBankCode checkDefaultGiroBankExist(Date effectiveDate, Integer bankCodeNo){
		DetachedCriteria criteria = DetachedCriteria.forClass(FmtbBankCode.class);
		criteria.add(Restrictions.eq("isDefaultGiroBank", NonConfigurableConstants.BOOLEAN_YES));
		criteria.add(Restrictions.eq("effectiveDate", effectiveDate));
		if(bankCodeNo != null){
			criteria.add(Restrictions.ne("bankCodeNo", bankCodeNo));
		}
		
		List result = this.findMaxResultByCriteria(criteria, 1);
		if(result.isEmpty()) return null;
		else return (FmtbBankCode) result.get(0);
	}
	
	@SuppressWarnings("unchecked")
	public List<IttbGiroSetup> getActiveGiroSetup(){
		DetachedCriteria criteria = DetachedCriteria.forClass(IttbGiroSetup.class);
		DetachedCriteria entityCriteria = criteria.createCriteria("fmtbEntityMaster", org.hibernate.sql.JoinType.LEFT_OUTER_JOIN);
		entityCriteria.addOrder(Order.asc("entityName"));
		return this.findAllByCriteria(criteria);
	}
	
	@SuppressWarnings("unchecked")
	public List<IttbGiroReq> getPendingGiroRequest(IttbGiroSetup setup){
		DetachedCriteria criteria = DetachedCriteria.forClass(IttbGiroReq.class);
		criteria.add(Restrictions.eq("ittbGiroSetup", setup));
		return this.findAllByCriteria(criteria);
	}
	
	public FmtbBankCode getLatestEntityBank(FmtbEntityMaster entityMaster){
		DetachedCriteria criteria = DetachedCriteria.forClass(FmtbBankCode.class);
		criteria.add(Restrictions.eq("fmtbEntityMaster", entityMaster));
		criteria.add(Restrictions.le("effectiveDate", DateUtil.getCurrentDate()));
		criteria.add(Restrictions.eq("isDefaultGiroBank", NonConfigurableConstants.BOOLEAN_YES));
		criteria.addOrder(Order.desc("effectiveDate"));
		List results = this.findMaxResultByCriteria(criteria, 1);
		if(results.isEmpty()) return null;
		return (FmtbBankCode) results.get(0);
	}
	
	@SuppressWarnings("unchecked")
	public  List<IttbGiroReq> getGiroRequest(IttbGiroSetup setup, Date requestDate){
		DetachedCriteria criteria = DetachedCriteria.forClass(IttbGiroReq.class);
		criteria.add(Restrictions.in("status", new String[]{
				NonConfigurableConstants.GIRO_REQUEST_STATUS_PENDING, 
				NonConfigurableConstants.GIRO_REQUEST_STATUS_PENDING_PROGRESS,
				NonConfigurableConstants.GIRO_REQUEST_STATUS_IN_PROGRESS}));
		criteria.add(Restrictions.eq("ittbGiroSetup", setup));
		criteria.add(Restrictions.eq("requestDate", requestDate));
		return this.findAllByCriteria(criteria);
	}
	
	@SuppressWarnings("unchecked")
	public  List<IttbGiroReq> getExistingGiroRequest(IttbGiroSetup setup){
		DetachedCriteria criteria = DetachedCriteria.forClass(IttbGiroReq.class);
		criteria.add(Restrictions.eq("ittbGiroSetup", setup));
		return this.findAllByCriteria(criteria);
	}
	
	@SuppressWarnings("unchecked")
	public List<IttbGiroReq> searchGiroRequest(SearchGiroRequestForm form) {
		
		DetachedCriteria criteria = DetachedCriteria.forClass(IttbGiroReq.class);
		DetachedCriteria setupCriteria = criteria.createCriteria("ittbGiroSetup", org.hibernate.sql.JoinType.LEFT_OUTER_JOIN);
		DetachedCriteria entityCriteria = setupCriteria.createCriteria("fmtbEntityMaster", org.hibernate.sql.JoinType.LEFT_OUTER_JOIN);
		
		if(form.entity!=null) entityCriteria.add(Restrictions.idEq(form.entity.getEntityNo()));
		if(form.requestNo!=null) criteria.add(Restrictions.idEq(form.requestNo));
		if(form.requestDate!=null) criteria.add(Restrictions.eq("requestDate", form.requestDate));
		if(form.requestTime!=null) criteria.add(Restrictions.eq("requestTime", form.requestTime));
		if(form.valueDate!=null) criteria.add(Restrictions.eq("valueDate", form.valueDate));
		if(form.cutoffDate!=null) criteria.add(Restrictions.eq("cutoffDate", form.cutoffDate));
		if(form.status!=null) criteria.add(Restrictions.eq("status", form.status));
		
		return this.findDefaultMaxResultByCriteria(criteria);
	}
	
	public IttbGiroReq searchGiroRequest(Long requestNo) {
		
		DetachedCriteria criteria = DetachedCriteria.forClass(IttbGiroReq.class);
		DetachedCriteria setupCriteria = criteria.createCriteria("ittbGiroSetup", org.hibernate.sql.JoinType.LEFT_OUTER_JOIN);
		setupCriteria.createCriteria("fmtbEntityMaster", org.hibernate.sql.JoinType.LEFT_OUTER_JOIN);
		
		criteria.add(Restrictions.idEq(requestNo));
		List results = this.findDefaultMaxResultByCriteria(criteria);
		if(results.isEmpty()) return null;
		else return (IttbGiroReq) results.get(0);
	}
	
	public IttbGiroSetup getInvoiceGiroDay(Integer entityNo){
		DetachedCriteria criteria = DetachedCriteria.forClass(IttbGiroSetup.class);
		DetachedCriteria entityCriteria = criteria.createCriteria("fmtbEntityMaster", org.hibernate.sql.JoinType.LEFT_OUTER_JOIN);
		
		entityCriteria.add(Restrictions.idEq(entityNo));
		List results = this.findAllByCriteria(criteria);
		
		if(results.isEmpty()) return null;
		else return (IttbGiroSetup) results.get(0);
	}
	
	public boolean isGiroReturnFileUploadedBefore(String fileName){
		DetachedCriteria criteria = DetachedCriteria.forClass(IttbGiroUobHeader.class);
		criteria.add(Restrictions.like("returnFileName", fileName, MatchMode.START));
		List results = this.findMaxResultByCriteria(criteria, 1);
		
		return results.isEmpty() == false;
	}
	
	public IttbGiroUobHeader getCorrespondingGiroOutgoingFile(String returnFileName){
		DetachedCriteria criteria = DetachedCriteria.forClass(IttbGiroUobHeader.class);
		criteria.createCriteria("ittbGiroUobDetails", org.hibernate.sql.JoinType.LEFT_OUTER_JOIN);
		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		
		//Left join data
		DetachedCriteria request = criteria.createCriteria("ittbGiroReq", org.hibernate.sql.JoinType.LEFT_OUTER_JOIN);
		DetachedCriteria setup = request.createCriteria("ittbGiroSetup", org.hibernate.sql.JoinType.LEFT_OUTER_JOIN);
		setup.createCriteria("fmtbEntityMaster", org.hibernate.sql.JoinType.LEFT_OUTER_JOIN);
		
		criteria.add(Restrictions.eq("giroFileName", returnFileName.substring(0, 10)));
		criteria.add(Restrictions.isNull("returnFileName"));
		criteria.addOrder(Order.desc("uobHeaderNo"));
		
		List results = this.findMaxResultByCriteria(criteria, 1);
		
		if(results.isEmpty()) return null;
		else return (IttbGiroUobHeader) results.get(0);
	}
	
	@SuppressWarnings("unchecked")
	public List<IttbGiroReturnReq> searchGiroReturnRequest(SearchGiroReturnRequestForm form) {
		DetachedCriteria criteria = DetachedCriteria.forClass(IttbGiroReturnReq.class);
		criteria.createCriteria("ittbGiroUobHeader", org.hibernate.sql.JoinType.LEFT_OUTER_JOIN);
		
		if(form.requestNo!=null) criteria.add(Restrictions.idEq(form.requestNo));
		if(form.processingDate!=null) criteria.add(Restrictions.eq("processingDate", form.processingDate));
		if(form.processingTime!=null) criteria.add(Restrictions.eq("processingTime", form.processingTime));
		if(form.status!=null) criteria.add(Restrictions.eq("status", form.status));
		
		return this.findDefaultMaxResultByCriteria(criteria);
	}
	
	public IttbGiroReturnReq searchGiroReturnRequest(Long requestNo) {
		
		DetachedCriteria criteria = DetachedCriteria.forClass(IttbGiroReturnReq.class);
		criteria.createCriteria("ittbGiroUobHeader", org.hibernate.sql.JoinType.LEFT_OUTER_JOIN);
		
		criteria.add(Restrictions.idEq(requestNo));
		List results = this.findDefaultMaxResultByCriteria(criteria);
		if(results.isEmpty()) return null;
		else return (IttbGiroReturnReq) results.get(0);
	}
	
	@SuppressWarnings("unchecked")
	public List<IttbGovtEinvoiceReq> searchGovtEInvRequest(SearchGovtEInvRequestForm form) {
		DetachedCriteria govtEInvRequestCrit = DetachedCriteria.forClass(IttbGovtEinvoiceReq.class);
		DetachedCriteria govtEInvHdrDtlsCrit = govtEInvRequestCrit.createCriteria("ittbGovtEinvoiceHdrDtls", org.hibernate.sql.JoinType.LEFT_OUTER_JOIN);
		govtEInvHdrDtlsCrit.createCriteria("ittbGovtEinvoiceLineDtls", org.hibernate.sql.JoinType.LEFT_OUTER_JOIN);
		DetachedCriteria billGenReqCrit = govtEInvRequestCrit.createCriteria("bmtbBillGenReq", org.hibernate.sql.JoinType.LEFT_OUTER_JOIN);
		DetachedCriteria accountCrit = govtEInvRequestCrit.createCriteria("amtbAccount", org.hibernate.sql.JoinType.LEFT_OUTER_JOIN);
		
		if (form.requestNo != null)
			govtEInvRequestCrit.add(Restrictions.idEq(form.requestNo));
		if (form.status != null)
			govtEInvRequestCrit.add(Restrictions.eq("status", form.status));
		if (form.billGenRequestNo != null)
			billGenReqCrit.add(Restrictions.idEq(form.billGenRequestNo));
		if (form.invoiceDate != null)
			govtEInvRequestCrit.add(Restrictions.eq("invoiceDate", form.invoiceDate));
		// Take note this is currently been used for duplication check
		if (form.invoiceNoFrom != null)
			govtEInvRequestCrit.add(Restrictions.eq("invoiceNoFrom", form.invoiceNoFrom));
		// Take note this is currently been used for duplication check
		if (form.invoiceNoTo != null)
			govtEInvRequestCrit.add(Restrictions.eq("invoiceNoTo", form.invoiceNoTo));
		if (form.accountNo != null)
			accountCrit.add(Restrictions.idEq(form.accountNo));
		if(form.requestDateFrom != null)
			govtEInvRequestCrit.add(Restrictions.between("createdDt",
					DateUtil.convertDateToTimestamp(DateUtil.convertDateTo0000Hours(form.requestDateFrom)),
					DateUtil.convertDateToTimestamp(DateUtil.convertDateTo2359Hours(form.requestDateTo))));
		
		// Due to left join, the result returned more than max result.
		// End search result may only retrieve total entities less than max result.
		DetachedCriteria subqueryCriteria = this.createMaxResultSubquery(govtEInvRequestCrit, "req_no");
		govtEInvRequestCrit.add(Subqueries.propertyIn("reqNo", subqueryCriteria));
		
		govtEInvRequestCrit.addOrder(Order.desc("reqNo"));
		
		govtEInvRequestCrit.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		return this.findAllByCriteria(govtEInvRequestCrit);
	}
	
	public IttbGovtEinvoiceReq searchGovtEInvRequest(Integer requestNo) {
		DetachedCriteria govtEInvRequestCrit = DetachedCriteria.forClass(IttbGovtEinvoiceReq.class);
		DetachedCriteria govtEInvHdrDtls = govtEInvRequestCrit.createCriteria("ittbGovtEinvoiceHdrDtls", org.hibernate.sql.JoinType.LEFT_OUTER_JOIN);
		govtEInvHdrDtls.createCriteria("ittbGovtEinvoiceLineDtls", org.hibernate.sql.JoinType.LEFT_OUTER_JOIN);
		
		govtEInvRequestCrit.add(Restrictions.idEq(requestNo));
		
		govtEInvRequestCrit.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		List results = this.findAllByCriteria(govtEInvRequestCrit);
		
		if (results.isEmpty())
			return null;
		else
			return (IttbGovtEinvoiceReq) results.get(0);
	}
	
	
	public boolean isPromotionCashPlusDeletable(String promoCode){
		
		return false;
		
	}
	public List<MstbInvoicePromo> getListInvoicePromo(String name, Date dateFrom, Date dateTo, String initial) {
		DetachedCriteria criteria = DetachedCriteria.forClass(MstbInvoicePromo.class);

		if(dateFrom != null && dateTo != null)
		{
			if(name!=null && !name.equals(""))
				criteria.add(Restrictions.eq("name", name));
			if(dateFrom!=null)
				criteria.add(Restrictions.ge("insertDt", dateFrom));
			if(dateTo != null)
				criteria.add(Restrictions.le("insertDt", dateTo));
		}
		else {
			if(initial != null) {
				Disjunction or = Restrictions.disjunction();
				or.add(Restrictions.eq("promo1","Y"));
				or.add(Restrictions.eq("promo2","Y"));
				or.add(Restrictions.eq("promo3","Y"));
				criteria.add(or);
			}
		}
		criteria.addOrder(Order.desc("insertDt"));	 
		List<MstbInvoicePromo> results = this.findAllByCriteria(criteria);
		
		if (results.isEmpty())
			return null;
		else
			return results;
	}
	
	public MstbInvoicePromo getInvoicePromo(Integer invoicePromoId){
		DetachedCriteria criteria = DetachedCriteria.forClass(MstbInvoicePromo.class);
		criteria.add(Restrictions.idEq(invoicePromoId));
		
		List results = findAllByCriteria(criteria);
		if(results.isEmpty()) return null;
		else return (MstbInvoicePromo)results.get(0);
	}
	public boolean checkPromoNumber(String promoNumber, Timestamp insDate, int promoId) {
		DetachedCriteria criteria = DetachedCriteria.forClass(MstbInvoicePromo.class);
		
		if(promoId != 0)
			criteria.add(Restrictions.ne("invoicePromoId",promoId));
		
		if(promoNumber.equals("1"))
			criteria.add(Restrictions.eq("promo1", "Y"));
		else if(promoNumber.equals("2"))
			criteria.add(Restrictions.eq("promo2", "Y"));
		else if(promoNumber.equals("3"))
			criteria.add(Restrictions.eq("promo3", "Y"));
		
		if(insDate != null)
			criteria.add(Restrictions.eq("insertDt", insDate));
		
		List<MstbInvoicePromo> results = this.findAllByCriteria(criteria);
		if(results.isEmpty() == false){
			return true;
		}
		
		return false;
	}
	
	@SuppressWarnings("unchecked")
	public List<IttbPubbsReq> searchPubbsRequest(SearchPubbsRequestForm form) {
		DetachedCriteria pubbsRequestCrit = DetachedCriteria.forClass(IttbPubbsReq.class);
		DetachedCriteria pubbsDtlsCrit = pubbsRequestCrit.createCriteria("ittbPubbsDtls", org.hibernate.sql.JoinType.LEFT_OUTER_JOIN);
		DetachedCriteria billGenReqCrit = pubbsRequestCrit.createCriteria("bmtbBillGenReq", org.hibernate.sql.JoinType.LEFT_OUTER_JOIN);
		DetachedCriteria accountCrit = pubbsRequestCrit.createCriteria("amtbAccount", org.hibernate.sql.JoinType.LEFT_OUTER_JOIN);
		
		if (form.requestNo != null)
			pubbsRequestCrit.add(Restrictions.idEq(form.requestNo));
		if (form.status != null)
			pubbsRequestCrit.add(Restrictions.eq("status", form.status));
		if (form.billGenRequestNo != null)
			billGenReqCrit.add(Restrictions.idEq(form.billGenRequestNo));
		if (form.invoiceDate != null)
			pubbsRequestCrit.add(Restrictions.eq("invoiceDate", form.invoiceDate));
		// Take note this is currently been used for duplication check
		if (form.invoiceNoFrom != null)
			pubbsRequestCrit.add(Restrictions.eq("invoiceNoFrom", form.invoiceNoFrom));
		// Take note this is currently been used for duplication check
		if (form.invoiceNoTo != null)
			pubbsRequestCrit.add(Restrictions.eq("invoiceNoTo", form.invoiceNoTo));
		if (form.accountNo != null)
			accountCrit.add(Restrictions.idEq(form.accountNo));
		if(form.requestDateFrom != null)
			pubbsRequestCrit.add(Restrictions.between("createdDt",
					DateUtil.convertDateToTimestamp(DateUtil.convertDateTo0000Hours(form.requestDateFrom)),
					DateUtil.convertDateToTimestamp(DateUtil.convertDateTo2359Hours(form.requestDateTo))));
		
		// Due to left join, the result returned more than max result.
		// End search result may only retrieve total entities less than max result.
		DetachedCriteria subqueryCriteria = this.createMaxResultSubquery(pubbsRequestCrit, "req_no");
		pubbsRequestCrit.add(Subqueries.propertyIn("reqNo", subqueryCriteria));
		
		pubbsRequestCrit.addOrder(Order.desc("reqNo"));
		
		pubbsRequestCrit.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		return this.findAllByCriteria(pubbsRequestCrit);
	}
	
	public IttbPubbsReq searchPubbsRequest(Integer requestNo) {
		DetachedCriteria pubbsRequestCrit = DetachedCriteria.forClass(IttbPubbsReq.class);
		DetachedCriteria pubbsHdrDtls = pubbsRequestCrit.createCriteria("ittbPubbsDtls", org.hibernate.sql.JoinType.LEFT_OUTER_JOIN);
		
		pubbsRequestCrit.add(Restrictions.idEq(requestNo));
		
		pubbsRequestCrit.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		List results = this.findAllByCriteria(pubbsRequestCrit);
		
		if (results.isEmpty())
			return null;
		else
			return (IttbPubbsReq) results.get(0);
	}

	@SuppressWarnings("unchecked")
	public List<IttbRecurringReq> searchIttbRecurringRequest(SearchRecurringRequestForm form) {
		DetachedCriteria recurringRequestCrit = DetachedCriteria.forClass(IttbRecurringReq.class);
		DetachedCriteria recurringDtlsCrit = recurringRequestCrit.createCriteria("ittbRecurringDtls", org.hibernate.sql.JoinType.LEFT_OUTER_JOIN);
		
		if (form.requestNo != null)
			recurringRequestCrit.add(Restrictions.idEq(form.requestNo));
		if (form.status != null)
			recurringRequestCrit.add(Restrictions.eq("status", form.status));
		if (form.invoiceDate != null)
			recurringRequestCrit.add(Restrictions.eq("invoiceDate", form.invoiceDate));
		if (form.recurringAutoManual != null)
			recurringRequestCrit.add(Restrictions.eq("recurringAutoManual", form.recurringAutoManual));
		// Take note this is currently been used for duplication check
		if (form.invoiceNos != null)
			recurringRequestCrit.add(Restrictions.like("invoiceNos", form.invoiceNos));
		// Take note this is currently been used for duplication check
		if (form.accountNos != null)
			recurringRequestCrit.add(Restrictions.like("accountNos", form.accountNos));
		if(form.requestDateFrom != null)
			recurringRequestCrit.add(Restrictions.between("createdDt",
					DateUtil.convertDateToTimestamp(DateUtil.convertDateTo0000Hours(form.requestDateFrom)),
					DateUtil.convertDateToTimestamp(DateUtil.convertDateTo2359Hours(form.requestDateTo))));
		
		// Due to left join, the result returned more than max result.
		// End search result may only retrieve total entities less than max result.
		DetachedCriteria subqueryCriteria = this.createMaxResultSubquery(recurringRequestCrit, "req_no");
		recurringRequestCrit.add(Subqueries.propertyIn("reqNo", subqueryCriteria));
		
		recurringRequestCrit.addOrder(Order.desc("reqNo"));
		
		recurringRequestCrit.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		return this.findAllByCriteria(recurringRequestCrit);
	}
	
	public IttbRecurringReq searchIttbRecurringRequest(Integer requestNo) {
		DetachedCriteria recurringRequestCrit = DetachedCriteria.forClass(IttbRecurringReq.class);
		DetachedCriteria recurringHdrDtls = recurringRequestCrit.createCriteria("ittbRecurringDtls", org.hibernate.sql.JoinType.LEFT_OUTER_JOIN);
		
		recurringRequestCrit.add(Restrictions.idEq(requestNo));
		
		recurringRequestCrit.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		List results = this.findAllByCriteria(recurringRequestCrit);
		
		if (results.isEmpty())
			return null;
		else
			return (IttbRecurringReq) results.get(0);
	}
	
	public IttbRecurringDtl getRecurringDtlwInvoice(String referenceId, String invoiceNo, String tokenId) {
		DetachedCriteria recurringRequestCrit = DetachedCriteria.forClass(IttbRecurringDtl.class);
		DetachedCriteria recurringReq = recurringRequestCrit.createCriteria("ittbRecurringReq", org.hibernate.sql.JoinType.LEFT_OUTER_JOIN);
		recurringRequestCrit.add(Restrictions.eq("referenceId", referenceId));
		recurringRequestCrit.add(Restrictions.eq("invoiceNo", invoiceNo));
		recurringRequestCrit.add(Restrictions.eq("tokenId", tokenId));
		recurringRequestCrit.add(Restrictions.isNull("status"));
		recurringRequestCrit.add(Restrictions.isNull("error"));
		
		recurringReq.add(Restrictions.eq("status", "C"));
		
		recurringRequestCrit.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		List results = this.findAllByCriteria(recurringRequestCrit);
		
		if (results.isEmpty())
			return null;
		else
			return (IttbRecurringDtl) results.get(0);
	}
}