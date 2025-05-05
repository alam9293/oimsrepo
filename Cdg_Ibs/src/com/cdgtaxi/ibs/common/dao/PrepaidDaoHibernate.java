package com.cdgtaxi.ibs.common.dao;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.classic.Session;
import org.hibernate.criterion.Conjunction;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.Subqueries;

import com.cdgtaxi.ibs.common.ConfigurableConstants;
import com.cdgtaxi.ibs.common.NonConfigurableConstants;
import com.cdgtaxi.ibs.common.model.AmtbAccount;
import com.cdgtaxi.ibs.common.model.BmtbInvoiceHeader;
import com.cdgtaxi.ibs.common.model.PmtbIssuanceReq;
import com.cdgtaxi.ibs.common.model.PmtbPrepaidCardTxn;
import com.cdgtaxi.ibs.common.model.PmtbPrepaidReq;
import com.cdgtaxi.ibs.common.model.PmtbProduct;
import com.cdgtaxi.ibs.common.model.PmtbTopUpReq;
import com.cdgtaxi.ibs.common.model.PmtbTransferReq;
import com.cdgtaxi.ibs.common.model.forms.SearchPrepaidProductForm;
import com.cdgtaxi.ibs.common.model.forms.SearchPrepaidRequestForm;
import com.cdgtaxi.ibs.util.LoggerUtil;
import com.cdgtaxi.ibs.util.QueryUtil;
import com.google.common.collect.Lists;


public class PrepaidDaoHibernate extends GenericDaoHibernate implements PrepaidDao{

	
	private static final Logger logger = Logger.getLogger(PrepaidDaoHibernate.class);
	
	@SuppressWarnings("unchecked")
	public List<Object[]> searchPrepaidInvoiceRequest(SearchPrepaidRequestForm form){
		
		Session session = this.getHibernateTemplate().getSessionFactory().openSession();
		List results = new ArrayList();
		try{
			Query query = session.getNamedQuery("searchInvoiceCreditPrepaid");
			
			if(form.getCardNo().length() != 0) query.setParameter("cardNo", form.getCardNo().toString(), Hibernate.STRING);
			else query.setParameter("cardNo", null, Hibernate.STRING);
			
			if(form.getInvoiceNoFrom()!=null) query.setParameter("invoiceNo", form.getInvoiceNoFrom().toString(), Hibernate.STRING);
			else query.setParameter("invoiceNo", null, Hibernate.STRING);
			
			if(form.getRequestDateFrom()!=null) query.setParameter("requestDateFromField", form.getRequestDateFrom(), Hibernate.DATE);
			else query.setParameter("requestDateFromField", null, Hibernate.DATE);
			
			if(form.getRequestDateTo()!=null) query.setParameter("requestDateToField", form.getRequestDateTo(), Hibernate.DATE);
			else query.setParameter("requestDateToField", null, Hibernate.DATE);
			
			
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
	public List<PmtbPrepaidReq> searchPrepaidRequest(SearchPrepaidRequestForm form){
		
		DetachedCriteria dc = DetachedCriteria.forClass(PmtbPrepaidReq.class);
		
		dc.createCriteria("amtbAccount", "acct", Criteria.LEFT_JOIN);
		dc.createCriteria("acct.fmtbArContCodeMaster", "ar", DetachedCriteria.LEFT_JOIN);
		dc.createCriteria("ar.fmtbEntityMaster", DetachedCriteria.LEFT_JOIN);
		
		dc.createCriteria("acct.amtbAccount", "parentAcct",  Criteria.LEFT_JOIN);
		dc.createCriteria("parentAcct.amtbAccount", "grandAcct",  Criteria.LEFT_JOIN);
		
		
		dc.createCriteria("requestBy", "requestBy", DetachedCriteria.INNER_JOIN);
		dc.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		
		if(!QueryUtil.isEmpty(form.getReqNo())){
			dc.add(Restrictions.eq("reqNo", form.getReqNo()));
		}
		
		if(!QueryUtil.isEmpty(form.getAccountNo())){
			
			Disjunction disj = Restrictions.disjunction();
			disj.add(Restrictions.eq("acct.accountNo", form.getAccountNo()));
			disj.add(Restrictions.eq("parentAcct.accountNo", form.getAccountNo()));
			disj.add(Restrictions.eq("grandAcct.accountNo", form.getAccountNo()));
			
			dc.add(disj);
		}
		
		if(!QueryUtil.isEmpty(form.getRequestor())){
			dc.add(Restrictions.like("requestBy.name", form.getRequestor(), MatchMode.ANYWHERE));
		}
		if(!QueryUtil.isEmpty(form.getRequestDateFrom())){
			dc.add(Restrictions.ge("requestDate", form.getRequestDateFrom()));
		}
		if(!QueryUtil.isEmpty(form.getRequestDateTo())){
			dc.add(Restrictions.le("requestDate", form.getRequestDateTo()));
		}
		if(!QueryUtil.isEmpty(form.getStatus())){
			dc.add(Restrictions.eq("status", form.getStatus()));
		}
		if(!QueryUtil.isEmpty(form.getRequestType())){
			dc.add(Restrictions.eq("requestType", form.getRequestType()));
		}
		
		dc.addOrder(Order.desc("reqNo"));
		
		return findDefaultMaxResultByCriteria(dc);
		
		
	}
	
	
	@SuppressWarnings("unchecked")
	public List<PmtbProduct> searchPrepaidProducts(SearchPrepaidProductForm form){
		
		DetachedCriteria dc = DetachedCriteria.forClass(PmtbProduct.class);
		dc.createCriteria("amtbAccount", "acct",  Criteria.LEFT_JOIN);
		dc.createCriteria("acct.amtbAccount", "parentAcct",  Criteria.LEFT_JOIN);
		dc.createCriteria("parentAcct.amtbAccount", "grandAcct",  Criteria.LEFT_JOIN);
		
		DetachedCriteria productTypeCriteria = dc.createCriteria("pmtbProductType");
		productTypeCriteria.add(Restrictions.eq("prepaid", NonConfigurableConstants.BOOLEAN_YES));
		
		if(!QueryUtil.isEmpty(form.getAccountNo())){
			
			Disjunction disj = Restrictions.disjunction();
			disj.add(Restrictions.eq("acct.accountNo", form.getAccountNo()));
			disj.add(Restrictions.eq("parentAcct.accountNo", form.getAccountNo()));
			disj.add(Restrictions.eq("grandAcct.accountNo", form.getAccountNo()));
			
			dc.add(disj);
		}
		
		if(!QueryUtil.isEmpty(form.getCardNoStart())){
			dc.add(Restrictions.ge("cardNo", form.getCardNoStart()));
		}
		if(!QueryUtil.isEmpty(form.getCardNoEnd())){
			dc.add(Restrictions.le("cardNo", form.getCardNoEnd()));
		}
		if(!QueryUtil.isEmpty(form.getNameOnCard())){
			dc.add(Restrictions.like("nameOnProduct", form.getNameOnCard(), MatchMode.ANYWHERE));
		}
		
		if(!QueryUtil.isEmpty(form.getCardExpiryFrom())){
			dc.add(Restrictions.ge("expiryDate", form.getCardExpiryFrom()));
		}
		if(!QueryUtil.isEmpty(form.getCardExpiryTo())){
			dc.add(Restrictions.le("expiryDate", form.getCardExpiryTo()));
		}
		if(!QueryUtil.isEmpty(form.getStatus())){
			dc.add(Restrictions.eq("currentStatus", form.getStatus()));
		}
		
		if(!QueryUtil.isEmpty(form.getBalanceExpiryFrom())){
			dc.add(Restrictions.ge("balanceExpiryDate", form.getBalanceExpiryFrom()));
		}
		if(!QueryUtil.isEmpty(form.getBalanceExpiryTo())){
			dc.add(Restrictions.le("balanceExpiryDate", form.getBalanceExpiryTo()));
		}
		
		//product status not in suspended and terminated
		dc.add(Restrictions.not(Restrictions.in("currentStatus", new String[]{NonConfigurableConstants.PRODUCT_STATUS_SUSPENDED, NonConfigurableConstants.PRODUCT_STATUS_TERMINATED, NonConfigurableConstants.PRODUCT_STATUS_PARENT_SUSPENDED})));
	
		
		return findDefaultMaxResultByCriteria(dc);
		
		
	}
	
	
	public List<PmtbProduct> getTopUpableProducts(Integer accountNo, String productTypeId, String cardNo,String cardName){

		//the input account no will be charge to self account
		logger.info("*****Retriving data by product with accountNo: " + accountNo +" cardNo: "+cardNo+" and card name: "+cardName+".");

		DetachedCriteria acctDc = DetachedCriteria.forClass(AmtbAccount.class, "acct");
		acctDc.createCriteria("acct.amtbAccount", "parentAcct",  Criteria.LEFT_JOIN);
		acctDc.createCriteria("parentAcct.amtbAccount", "grandParentAcct",  Criteria.LEFT_JOIN);
		
		Disjunction disj = Restrictions.disjunction();
		
		//Condition 1 - find own account which it charge to self
		Conjunction conj1 = Restrictions.conjunction();
		conj1.add(Restrictions.eq("acct.accountNo", accountNo));
		conj1.add(Restrictions.isNotNull("acct.invoiceFormat"));
		
		//Condition 2 - find account which is charge to parent and its parent is charge to self
		Conjunction conj2 = Restrictions.conjunction();
		conj2.add(Restrictions.eq("parentAcct.accountNo", accountNo));
		conj2.add(Restrictions.isNotNull("parentAcct.invoiceFormat"));
		conj2.add(Restrictions.isNull("acct.invoiceFormat"));
		
		//Condition 2 - find account which is charge to parent, its parent is also charge to parent and its grand parent charge to self
		Conjunction conj3 = Restrictions.conjunction();
		conj3.add(Restrictions.eq("grandParentAcct.accountNo", accountNo));
		conj3.add(Restrictions.isNotNull("grandParentAcct.invoiceFormat"));
		conj3.add(Restrictions.isNull("parentAcct.invoiceFormat"));
		conj3.add(Restrictions.isNull("acct.invoiceFormat"));

		disj.add(conj1);
		disj.add(conj2);
		disj.add(conj3);
		
		acctDc.add(disj);
		acctDc.setProjection(Projections.distinct(Projections.property("acct.accountNo")));
		
		DetachedCriteria dc = DetachedCriteria.forClass(PmtbProduct.class);
		dc.createCriteria("pmtbProductType", "productType", Criteria.LEFT_JOIN);
		dc.createCriteria("amtbAccount", "productAcct",  Criteria.LEFT_JOIN);
	
		if(cardNo!=null && cardNo.trim().length()>0) {
			dc.add(Restrictions.eq("cardNo",cardNo));
		}

		if(cardName!=null && cardName.trim().length()>0) {
			dc.add(Restrictions.ilike("nameOnProduct", cardName, MatchMode.ANYWHERE));
		}
	
		dc.add(Restrictions.eq("productType.prepaid", NonConfigurableConstants.BOOLEAN_YES));
		dc.add(Restrictions.eq("productType.productTypeId", productTypeId));
		
		dc.add(Subqueries.propertyIn("productAcct.accountNo", acctDc));
		
		//product status not in suspended and terminated
		dc.add(Restrictions.not(Restrictions.in("currentStatus", new String[]{NonConfigurableConstants.PRODUCT_STATUS_SUSPENDED, NonConfigurableConstants.PRODUCT_STATUS_TERMINATED, NonConfigurableConstants.PRODUCT_STATUS_PARENT_SUSPENDED})));
		
		dc.addOrder(Order.asc("cardHolderName"));

		@SuppressWarnings("unchecked")
		List<PmtbProduct> results = this.findAllByCriteria(dc);
		
		return results;

	}
	
	@SuppressWarnings("unchecked")
	public List<PmtbProduct> getProductsForTransferReq(List<BigDecimal> productNoList){
		
		DetachedCriteria dc = DetachedCriteria.forClass(PmtbProduct.class);
		dc.createCriteria("pmtbProductType");
		DetachedCriteria acctCriteria = dc.createCriteria("amtbAccount");
		DetachedCriteria parentAcctCriteria = acctCriteria.createCriteria("amtbAccount", Criteria.LEFT_JOIN);
		parentAcctCriteria.createCriteria("amtbAccount", Criteria.LEFT_JOIN);

		dc.add(Restrictions.in("productNo", productNoList.toArray()));
		
		dc.addOrder(Order.asc("productNo"));
		
		List<PmtbProduct> results = this.findAllByCriteria(dc);
		return results;
	}


	public PmtbPrepaidReq getPrepaidRequest(BigDecimal requestNo) {
		
		DetachedCriteria dc = DetachedCriteria.forClass(PmtbPrepaidReq.class);
		dc.add(Restrictions.eq("reqNo", requestNo));
		
		return firstResult(dc);
	}


	public PmtbIssuanceReq getPrepaidIssuanceRequest(BigDecimal requestNo) {
		DetachedCriteria dc = DetachedCriteria.forClass(PmtbIssuanceReq.class);
		dc.createCriteria("amtbAccount", "acct", Criteria.LEFT_JOIN);
		dc.createCriteria("acct.fmtbArContCodeMaster", "ar", DetachedCriteria.LEFT_JOIN);
		dc.createCriteria("ar.fmtbEntityMaster", DetachedCriteria.LEFT_JOIN);
		dc.createCriteria("pmtbIssuanceReqCards", "card", Criteria.LEFT_JOIN);
		dc.createCriteria("card.mstbPromotionCashPluses", Criteria.LEFT_JOIN);
		dc.add(Restrictions.eq("reqNo", requestNo));
		
		return firstResult(dc);
	}


	public PmtbTopUpReq getPrepaidTopUpRequest(BigDecimal requestNo) {

		DetachedCriteria dc = DetachedCriteria.forClass(PmtbTopUpReq.class);
		dc.createCriteria("amtbAccount", "acct", Criteria.LEFT_JOIN);
		dc.createCriteria("acct.fmtbArContCodeMaster", "ar", DetachedCriteria.LEFT_JOIN);
		dc.createCriteria("ar.fmtbEntityMaster", DetachedCriteria.LEFT_JOIN);
		dc.createCriteria("pmtbTopUpReqCards", "card", Criteria.LEFT_JOIN);
		dc.createCriteria("card.pmtbProduct", "product", Criteria.LEFT_JOIN);
		dc.createCriteria("product.pmtbProductType", Criteria.LEFT_JOIN);
		dc.createCriteria("card.mstbPromotionCashPluses", Criteria.LEFT_JOIN);
		dc.add(Restrictions.eq("reqNo", requestNo));
		
		return firstResult(dc);
	}
	
	public PmtbTopUpReq getPrepaidCreditTopUpRequest(BigDecimal requestNo) {
		DetachedCriteria dc = DetachedCriteria.forClass(PmtbTopUpReq.class);
		dc.createCriteria("bmtbInvoiceHeader","ih", Criteria.LEFT_JOIN);
		dc.createCriteria("ih.bmtbInvoiceSummaries","ihs", DetachedCriteria.LEFT_JOIN);
		dc.createCriteria("ihs.bmtbInvoiceDetails","ihsd", DetachedCriteria.LEFT_JOIN);
		dc.createCriteria("amtbAccount", "acct", Criteria.LEFT_JOIN);
		dc.createCriteria("acct.fmtbArContCodeMaster", "ar", DetachedCriteria.LEFT_JOIN);
		dc.createCriteria("ar.fmtbEntityMaster", DetachedCriteria.LEFT_JOIN);
		dc.createCriteria("pmtbTopUpReqCards", "card", Criteria.LEFT_JOIN);
		dc.createCriteria("card.pmtbProduct", "product", Criteria.LEFT_JOIN);
		dc.createCriteria("product.pmtbProductType", Criteria.LEFT_JOIN);
		dc.createCriteria("card.mstbPromotionCashPluses", Criteria.LEFT_JOIN);
		dc.add(Restrictions.eq("reqNo", requestNo));
		return firstResult(dc);
	}
	
	public PmtbTransferReq getPrepaidTransferRequest(BigDecimal requestNo) {
		
		DetachedCriteria dc = DetachedCriteria.forClass(PmtbTransferReq.class);
		dc.createCriteria("amtbAccount", "acct", DetachedCriteria.LEFT_JOIN);
		dc.createCriteria("acct.fmtbArContCodeMaster", "ar", DetachedCriteria.LEFT_JOIN);
		dc.createCriteria("ar.fmtbEntityMaster", DetachedCriteria.LEFT_JOIN);
		
		dc.createCriteria("fromPmtbProduct", "fromProduct", Criteria.LEFT_JOIN);
		dc.createCriteria("fromProduct.pmtbProductType", Criteria.LEFT_JOIN);
		
		dc.createCriteria("fromProduct.amtbAccount", "fromProductAcct", Criteria.LEFT_JOIN);
		dc.createCriteria("fromProductAcct.amtbAccount", "fromProductParentAcct",  Criteria.LEFT_JOIN);
		dc.createCriteria("fromProductParentAcct.amtbAccount", "fromProductGrandAcct",  Criteria.LEFT_JOIN);
		
		dc.createCriteria("toPmtbProduct", "toProduct", Criteria.LEFT_JOIN);
		dc.createCriteria("toProduct.pmtbProductType", Criteria.LEFT_JOIN);
		
		dc.createCriteria("toProduct.amtbAccount", "toProductAcct", Criteria.LEFT_JOIN);
		dc.createCriteria("toProductAcct.amtbAccount", "toProductParentAcct",  Criteria.LEFT_JOIN);
		dc.createCriteria("toProductParentAcct.amtbAccount", "toProductGrandAcct",  Criteria.LEFT_JOIN);
		
		dc.add(Restrictions.eq("reqNo", requestNo));
		
		return firstResult(dc);
		
		
	}

	
	public PmtbProduct getPrepaidProduct(BigDecimal productNo){
		
		DetachedCriteria dc = DetachedCriteria.forClass(PmtbProduct.class);
		DetachedCriteria productTypeCriteria = dc.createCriteria("pmtbProductType", Criteria.LEFT_JOIN);
		dc.createCriteria("amtbAccount", "acct", Criteria.LEFT_JOIN);
		dc.createCriteria("acct.amtbAccount", "parentAcct",  Criteria.LEFT_JOIN);
		dc.createCriteria("parentAcct.amtbAccount", "grandAcct",  Criteria.LEFT_JOIN);
		
		productTypeCriteria.add(Restrictions.eq("prepaid", NonConfigurableConstants.BOOLEAN_YES));
		dc.add(Restrictions.eq("productNo", productNo));
		
		return firstResult(dc);
		
	}
	
	
	public PmtbPrepaidReq getPrepaidRequestWithInvoiceHeader(Long invoiceHeaderNo) {
		
		DetachedCriteria dc = DetachedCriteria.forClass(PmtbPrepaidReq.class);
		dc.createCriteria("bmtbInvoiceHeader", "inv", Criteria.LEFT_JOIN);
		dc.createCriteria("pmtbIssuanceReqCards", "iReqCard", Criteria.LEFT_JOIN);
		dc.createCriteria("iReqCard.mstbPromotionCashPluses", Criteria.LEFT_JOIN);
		dc.createCriteria("pmtbTopUpReqCards", "tReqCard", Criteria.LEFT_JOIN);
		dc.createCriteria("tReqCard.mstbPromotionCashPluses", Criteria.LEFT_JOIN);
		
		dc.add(Restrictions.eq("inv.invoiceHeaderNo", invoiceHeaderNo));
		
		return firstResult(dc);
	}
	
	
	
	public List<PmtbPrepaidCardTxn> getPrepaidCardTxnsByAcquireTxnNo(Integer acquireTxnNo){
		
		DetachedCriteria dc = DetachedCriteria.forClass(PmtbPrepaidCardTxn.class);
		dc.createCriteria("tmtbAcquireTxn", "acqTxn", Criteria.LEFT_JOIN);
		dc.add(Restrictions.in("txnType", new String[]{NonConfigurableConstants.PREPAID_TXN_TYPE_TRIP, NonConfigurableConstants.PREPAID_TXN_TYPE_EDIT_TRIP}));
		dc.add(Restrictions.eq("acqTxn.acquireTxnNo", acquireTxnNo));
	
		@SuppressWarnings("unchecked")
		List<PmtbPrepaidCardTxn> results = this.findAllByCriteria(dc);
		
		return results;

	}
	
	
	@SuppressWarnings("unchecked")
	public Object[] getLastBalanceForfeitureAsAt(BigDecimal productNo, Date asAtDate){
		
		Session session = this.getHibernateTemplate().getSessionFactory().openSession();
		List<Object[]> results = Lists.newArrayList();
		try{
			Query query = session.getNamedQuery("getLastBalanceForfeitureAsAt");
		
			query.setParameter("productNo", productNo, Hibernate.BIG_DECIMAL);
			query.setParameter("asAtDate", asAtDate, Hibernate.DATE);
			query.setMaxResults(ConfigurableConstants.getMaxQueryResult());
			results = query.list();
		}
		catch(Exception e){
			LoggerUtil.printStackTrace(logger, e);
		}
		finally{
			session.close();
		}

		if(!results.isEmpty()){
			return results.get(0);
		}
		
		return null;
		

	}
	
	
	public BmtbInvoiceHeader getPrepaidInvoiceHeader(long invoiceHeaderNo){
		
		DetachedCriteria dc = DetachedCriteria.forClass(BmtbInvoiceHeader.class);
		dc.createCriteria("amtbAccountByDebtTo",
				DetachedCriteria.INNER_JOIN);
		dc.createCriteria("amtbAccountByAccountNo", DetachedCriteria.INNER_JOIN);

		DetachedCriteria invoiceSummaryCriteria = dc.createCriteria(
				"bmtbInvoiceSummaries", DetachedCriteria.LEFT_JOIN);
	
		invoiceSummaryCriteria.createCriteria("bmtbInvoiceDetails",
				DetachedCriteria.LEFT_JOIN);
		
		dc.add(Restrictions.idEq(invoiceHeaderNo));
		
		return firstResult(dc);
		
	}
	
	
	
	
	
	
}