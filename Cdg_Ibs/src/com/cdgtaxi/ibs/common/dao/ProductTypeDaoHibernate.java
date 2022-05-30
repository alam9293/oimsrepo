package com.cdgtaxi.ibs.common.dao;

import java.util.ArrayList;
import java.util.List;

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

import com.cdgtaxi.ibs.common.NonConfigurableConstants;
import com.cdgtaxi.ibs.common.model.AmtbSubscTo;
import com.cdgtaxi.ibs.common.model.PmtbProduct;
import com.cdgtaxi.ibs.common.model.PmtbProductType;

@SuppressWarnings("unchecked")
public class ProductTypeDaoHibernate extends GenericDaoHibernate implements ProductTypeDao{
	private static final int MAX_ROW = 1;

	/**
	 * Extracting of all product types available in the system
	 */
	public List<PmtbProductType> getAllProductType() {
		DetachedCriteria criteria = DetachedCriteria.forClass(PmtbProductType.class);
		criteria.createCriteria("mstbMasterTableBySMSFormat", DetachedCriteria.LEFT_JOIN);
		criteria.createCriteria("mstbMasterTableBySMSExpiryFormat", DetachedCriteria.LEFT_JOIN);
		criteria.createCriteria("mstbMasterTableBySMSTopupFormat", DetachedCriteria.LEFT_JOIN);
		criteria.addOrder(Order.asc("name"));
		return this.findAllByCriteria(criteria);
	}
	public List<PmtbProductType> getAllCardlessProductType() {
		DetachedCriteria criteria = DetachedCriteria.forClass(PmtbProductType.class);
		criteria.add(Restrictions.eq("cardless", "Y"));
		criteria.add(Restrictions.isNotNull("interfaceMappingValue"));
		criteria.createCriteria("mstbMasterTableBySMSFormat", DetachedCriteria.LEFT_JOIN);
		criteria.createCriteria("mstbMasterTableBySMSExpiryFormat", DetachedCriteria.LEFT_JOIN);
		criteria.createCriteria("mstbMasterTableBySMSTopupFormat", DetachedCriteria.LEFT_JOIN);
		criteria.addOrder(Order.asc("name"));
		return this.findAllByCriteria(criteria);
	}
	
	public List<PmtbProductType> getPremierAccountProductType(String acctNo){

		DetachedCriteria productTypeCriteria = DetachedCriteria.forClass(PmtbProductType.class);
		DetachedCriteria subscribeTosCriteria = productTypeCriteria.createCriteria("amtbSubscTos", Criteria.LEFT_JOIN);
		
		if(acctNo!=null && !acctNo.trim().equals("")) {
			subscribeTosCriteria.add(Restrictions.eq("comp_id.amtbAccount.accountNo", Integer.parseInt(acctNo)));
		}
		else
			return null;
		
		productTypeCriteria.addOrder(Order.asc("name"));
		
		return this.findAllByCriteria(productTypeCriteria);
	}
	/**
	 * getting product type based on product type id
	 */
	public PmtbProductType getProductType(String productTypeId){
		//return (PmtbProductType)this.get(PmtbProductType.class, productTypeId);
		
		DetachedCriteria productTypeCriteria = DetachedCriteria.forClass(PmtbProductType.class);
		productTypeCriteria.add(Restrictions.idEq(productTypeId));
		productTypeCriteria.createCriteria("mstbMasterTableBySMSFormat", DetachedCriteria.LEFT_JOIN);
		productTypeCriteria.createCriteria("mstbMasterTableBySMSExpiryFormat", DetachedCriteria.LEFT_JOIN);
		productTypeCriteria.createCriteria("mstbMasterTableBySMSTopupFormat", DetachedCriteria.LEFT_JOIN);
		List results = this.findAllByCriteria(productTypeCriteria);
		if(results.isEmpty()) {
			return null;
		} else {
			return (PmtbProductType) results.get(0);
		}
	}
	
	//Add a method for manage product type
	public List<PmtbProductType> getProductType(PmtbProductType productType) {
		return this.getAll(productType);
	}
	
	public List<PmtbProductType> getAll(PmtbProductType productType){
		DetachedCriteria productTypeCriteria = DetachedCriteria.forClass(PmtbProductType.class);
		productTypeCriteria.createCriteria("mstbMasterTableBySMSFormat", DetachedCriteria.LEFT_JOIN);
		productTypeCriteria.createCriteria("mstbMasterTableBySMSExpiryFormat", DetachedCriteria.LEFT_JOIN);
		productTypeCriteria.createCriteria("mstbMasterTableBySMSTopupFormat", DetachedCriteria.LEFT_JOIN);
		//
		//DetachedCriteria accountTypeCriteria = DetachedCriteria.forClass(AccountType.class);
		
		if(productType.getName().trim().length()>0)
			productTypeCriteria.add(Restrictions.ilike("name", productType.getName(), MatchMode.ANYWHERE));
		if(productType.getNumberOfDigit()>0)
			productTypeCriteria.add(Restrictions.eq("numberOfDigit",(Integer)productType.getNumberOfDigit()));
		if(productType.getBinRange().trim().length()>0)
			productTypeCriteria.add(Restrictions.ilike("binRange", productType.getBinRange(), MatchMode.ANYWHERE));
		if(productType.getSubBinRange().trim().length()>0)
			productTypeCriteria.add(Restrictions.ilike("subBinRange", productType.getSubBinRange(), MatchMode.ANYWHERE));
		if(!productType.getBatchIssue().equalsIgnoreCase("")){
			productTypeCriteria.add(Restrictions.ilike("batchIssue", productType.getBatchIssue(),MatchMode.END));
			System.out.print("productType.getBatchIssue() one is not blank"+productType.getBatchIssue()+".");
		}
		if(!productType.getNameOnProduct().equalsIgnoreCase(""))
			productTypeCriteria.add(Restrictions.ilike("nameOnProduct", productType.getNameOnProduct(), MatchMode.END));
		if(!productType.getLuhnCheck().equalsIgnoreCase(""))
			productTypeCriteria.add(Restrictions.ilike("luhnCheck", productType.getLuhnCheck(),MatchMode.END));
		if(!productType.getOneTimeUsage().equalsIgnoreCase(""))
			productTypeCriteria.add(Restrictions.ilike("oneTimeUsage", productType.getOneTimeUsage(),MatchMode.END));
		if(!productType.getFixedValue().equalsIgnoreCase(""))
			productTypeCriteria.add(Restrictions.ilike("fixedValue", productType.getFixedValue(),MatchMode.END));
		if(!productType.getCreditLimit().equalsIgnoreCase(""))
			productTypeCriteria.add(Restrictions.ilike("creditLimit", productType.getCreditLimit(),MatchMode.END));
		if(!productType.getValidityPeriod().equalsIgnoreCase(""))
			productTypeCriteria.add(Restrictions.ilike("validityPeriod", productType.getValidityPeriod(),MatchMode.END));
		if(!productType.getValidityPeriod().equalsIgnoreCase(""))
			productTypeCriteria.add(Restrictions.ilike("validityPeriod", productType.getValidityPeriod(),MatchMode.END));
		if(!productType.getDefaultCardStatus().equalsIgnoreCase(""))
			productTypeCriteria.add(Restrictions.eq("defaultCardStatus", productType.getDefaultCardStatus()));
		if(!productType.getExternalCard().equalsIgnoreCase("-"))
			productTypeCriteria.add(Restrictions.ilike("externalCard", productType.getExternalCard(),MatchMode.END));
		if(!productType.getIssuable().equalsIgnoreCase(""))
			productTypeCriteria.add(Restrictions.ilike("issuable",productType.getIssuable(),MatchMode.END));
		if(!productType.getIssueType().equalsIgnoreCase(""))
			productTypeCriteria.add(Restrictions.ilike("issueType", productType.getIssueType(),MatchMode.END));
		if(productType.getDefaultValidPeriod()>=0)
			productTypeCriteria.add(Restrictions.eq("defaultValidPeriod",(Integer)productType.getDefaultValidPeriod()));
//		else if(productType.getDefaultValidPeriod()==-1)
//			productTypeCriteria.add(Restrictions.eq("defaultValidPeriod",-1));
		if(!productType.getLoginRegistration().equals(""))
			productTypeCriteria.add(Restrictions.eq("loginRegistration", productType.getLoginRegistration()));
		if(!productType.getHotel().equals(""))
			productTypeCriteria.add(Restrictions.eq("hotel", productType.getHotel()));
		if(!productType.getCardless().equals(""))
			productTypeCriteria.add(Restrictions.eq("cardless", productType.getCardless()));
		if(!productType.getVirtualProduct().equals(""))
			productTypeCriteria.add(Restrictions.eq("virtualProduct", productType.getVirtualProduct()));
		if(productType.getPrepaid()!=null && !productType.getPrepaid().equals(""))
			productTypeCriteria.add(Restrictions.eq("prepaid", productType.getPrepaid()));
		if(productType.getContactless()!=null && !productType.getContactless().equals(""))
			productTypeCriteria.add(Restrictions.eq("contactless", productType.getContactless()));
		if(productType.getMstbMasterTableBySMSFormat()!=null && !productType.getMstbMasterTableBySMSFormat().equals(""))
			productTypeCriteria.add(Restrictions.eq("mstbMasterTableBySMSFormat", productType.getMstbMasterTableBySMSFormat()));
		if(productType.getMstbMasterTableBySMSExpiryFormat()!=null && !productType.getMstbMasterTableBySMSExpiryFormat().equals(""))
			productTypeCriteria.add(Restrictions.eq("mstbMasterTableBySMSExpiryFormat", productType.getMstbMasterTableBySMSExpiryFormat()));
		if(productType.getMstbMasterTableBySMSTopupFormat()!=null && !productType.getMstbMasterTableBySMSTopupFormat().equals(""))
			productTypeCriteria.add(Restrictions.eq("mstbMasterTableBySMSTopupFormat", productType.getMstbMasterTableBySMSTopupFormat()));
		if(productType.getDefaultBalanceExpMonths()!=null)
			productTypeCriteria.add(Restrictions.eq("defaultBalanceExpMonths", productType.getDefaultBalanceExpMonths()));
		
		
		logger.info("Criteria : "+productTypeCriteria.toString());
		return this.findDefaultMaxResultByCriteria(productTypeCriteria);
		//getHibernateTemplate().findByCriteria(productTypeCriteria);
	}

	
	public List<AmtbSubscTo> getAmtbSubscToForIssueProduct(String acctNo, boolean isPrepaid){
		
		//not PREPAID sub query
		DetachedCriteria productTypeDc = DetachedCriteria.forClass(PmtbProductType.class);
		productTypeDc.setProjection(Projections.projectionList().add(Projections.property("productTypeId")));
		productTypeDc.add(Restrictions.eq("prepaid", NonConfigurableConstants.getBooleanFlag(isPrepaid)));
		productTypeDc.add(Restrictions.eq("issuable", NonConfigurableConstants.BOOLEAN_YN_YES));

		DetachedCriteria dc = DetachedCriteria.forClass(AmtbSubscTo.class);
		
		//only find those subscribe product type which is non PREPAID
		dc.add(Subqueries.propertyIn("comp_id.pmtbProductType", productTypeDc));
		dc.add(Restrictions.eq("comp_id.amtbAccount.accountNo", Integer.valueOf(acctNo)));
		List<AmtbSubscTo> results = this.findAllByCriteria(dc);
		return results;
	}
	
	public List<PmtbProductType> getAllProductTypes(){
		
		DetachedCriteria productTypeCriteria = DetachedCriteria.forClass(PmtbProductType.class);
		productTypeCriteria.add(Restrictions.eq("issuable",NonConfigurableConstants.BOOLEAN_YES));
		//productTypeCriteria.add(Restrictions.ilike("productTypeId","", MatchMode.ANYWHERE));
		return this.findDefaultMaxResultByCriteria(productTypeCriteria);
		
	}
	
	
	public List<PmtbProductType> getProductTypes(List<String> productTypeIds){
		
		if(!productTypeIds.isEmpty()){
		
			DetachedCriteria dc = DetachedCriteria.forClass(PmtbProductType.class);
			dc.add(Restrictions.in("productTypeId", productTypeIds));

			return this.findAllByCriteria(dc);
		} else {
			return new ArrayList<PmtbProductType>();
		}
		
	}
	
	public PmtbProductType getProductType(String cardNo, java.util.Date tripDt){
		DetachedCriteria productTypeCriteria = DetachedCriteria.forClass(PmtbProductType.class);
		DetachedCriteria productCriteria = productTypeCriteria.createCriteria("pmtbProducts", Criteria.LEFT_JOIN);
		DetachedCriteria productStatusCriteria = productCriteria.createCriteria("pmtbProductStatuses",Criteria.INNER_JOIN);
		productStatusCriteria.add(Restrictions.le("statusDt", tripDt));
		productCriteria.add(Restrictions.eq("cardNo", cardNo));
		productCriteria.addOrder(Order.desc("productNo"));	
		List results = this.findMaxResultByCriteria(productTypeCriteria, MAX_ROW);
		if(results.isEmpty()) return null;
		else return (PmtbProductType)results.get(0);
	}
	
	public List<PmtbProductType> getPremierServiceProductType(){
		// Technically speaking should have only 1 product type that is called PREMIER SERVICE
		// 10/6/2017 - change to grab all cardless (premier = cardless).
		DetachedCriteria productTypeCriteria = DetachedCriteria.forClass(PmtbProductType.class);
		
		productTypeCriteria.add(Restrictions.eq("cardless", "Y"));
//		Disjunction disjunction = Restrictions.disjunction();
//		disjunction.add(Restrictions.like("productTypeId", NonConfigurableConstants.PREMIER_SERVICE));
//		disjunction.add(Restrictions.like("productTypeId", NonConfigurableConstants.NO_CARD_ISSUANCE));
//		
//		productTypeCriteria.add(disjunction);
		
		List results = this.findAllByCriteria(productTypeCriteria);
		if(results.isEmpty()) return null;
		else return results;	
	}
	public boolean isProductNameInUse(String productTypeName) {
		
		DetachedCriteria productTypeCriteria = DetachedCriteria.forClass(PmtbProductType.class);
		productTypeCriteria.add(Restrictions.like("name", productTypeName));
		List results = this.findAllByCriteria(productTypeCriteria);
		logger.info("Product Name In Use"+results.size());
		if(results.isEmpty()) return false;
		else return true;	
	}
	
	public boolean isProductNameInUse(String productTypeName,String productTypeId) {
		
		DetachedCriteria productTypeCriteria = DetachedCriteria.forClass(PmtbProductType.class);
		productTypeCriteria.add(Restrictions.ne("productTypeId", productTypeId));
		productTypeCriteria.add(Restrictions.like("name", productTypeName));
		List results = this.findAllByCriteria(productTypeCriteria);
		logger.info("Criteria"+productTypeCriteria.toString());
		logger.info("Product Name In Use"+results.size());
		if(results.isEmpty()) return false;
		else return true;	
	}
	
	public List<PmtbProductType> getPrepaidProductType(){
		DetachedCriteria productTypeCriteria = DetachedCriteria.forClass(PmtbProductType.class);
		productTypeCriteria.add(Restrictions.eq("prepaid", NonConfigurableConstants.BOOLEAN_YES));
		productTypeCriteria.addOrder(Order.asc("name"));
		return this.findAllByCriteria(productTypeCriteria);
	}
	
	public List<PmtbProductType> getExternalProductType(){
		DetachedCriteria productTypeCriteria = DetachedCriteria.forClass(PmtbProductType.class);
		productTypeCriteria.add(Restrictions.eq("externalCard", NonConfigurableConstants.BOOLEAN_YES));
		productTypeCriteria.addOrder(Order.asc("name"));
		return this.findAllByCriteria(productTypeCriteria);
	}
	
	public PmtbProductType getExternalProductType(String binRange, String subBinRange){
		DetachedCriteria criteria = DetachedCriteria.forClass(PmtbProductType.class);
		
		criteria.add(Restrictions.eq("externalCard", NonConfigurableConstants.BOOLEAN_YES));
		
		Conjunction conjunction = Restrictions.conjunction();
		conjunction.add(Restrictions.eq("binRange",binRange));
		conjunction.add(Restrictions.eq("subBinRange",subBinRange));
		
		Conjunction conjunction2 = Restrictions.conjunction();
		conjunction2.add(Restrictions.eq("binRange",binRange));
		conjunction2.add(Restrictions.eq("subBinRange","NA"));
		
		Disjunction disjunction = Restrictions.disjunction();
		disjunction.add(conjunction);
		disjunction.add(conjunction2);
		
		criteria.add(disjunction);
		criteria.addOrder(Order.asc("subBinRange")); //This will push records with null at the last
		
		List resultList = this.findAllByCriteria(criteria);
		if(resultList.size()>0)
			return (PmtbProductType) resultList.get(0);
		else
			return null;
	}
	
	/* (non-Javadoc)
	 * @see com.cdgtaxi.ibs.common.dao.ProductTypeDao#getAccountSubscribedToExternalProductType(java.lang.String)
	 */
	public List<Integer> getAccountSubscribedToExternalProductType(String productTypeId){
		Session session = this.getHibernateTemplate().getSessionFactory().openSession();
		List results = new ArrayList();
		try{
			Query query = session.getNamedQuery("getAccountSubscribedToExternalProductType");
			query.setParameter("productTypeId", productTypeId, Hibernate.STRING);
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
	
	public boolean checkGotProduct(String productTypeId) {
		
		DetachedCriteria productCriteria = DetachedCriteria.forClass(PmtbProduct.class);
		DetachedCriteria productTypeCriteria= productCriteria.createCriteria("pmtbProductType", DetachedCriteria.LEFT_JOIN);
		productTypeCriteria.add(Restrictions.eq("productTypeId", productTypeId));
		List<PmtbProduct> resultList =  this.findDefaultMaxResultByCriteria(productCriteria);
		if(resultList.isEmpty()) return false;
		else return true;
	}
}