package com.cdgtaxi.ibs.common.dao;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.Subqueries;
import org.hibernate.resource.transaction.spi.TransactionStatus;
import org.hibernate.type.StandardBasicTypes;

import com.cdgtaxi.ibs.common.ConfigurableConstants;
import com.cdgtaxi.ibs.common.NonConfigurableConstants;
import com.cdgtaxi.ibs.common.model.AmtbAccount;
import com.cdgtaxi.ibs.common.model.AmtbAcctStatus;
import com.cdgtaxi.ibs.common.model.AmtbContactPerson;
import com.cdgtaxi.ibs.common.model.AmtbCorporateDetail;
import com.cdgtaxi.ibs.common.model.IttbCpCustCardIssuance;
import com.cdgtaxi.ibs.common.model.IttbCpLogin;
import com.cdgtaxi.ibs.common.model.IttbCpLoginNew;
import com.cdgtaxi.ibs.common.model.IttbRecurringChargeTagCard;
import com.cdgtaxi.ibs.common.model.IttbRecurringDtl;
import com.cdgtaxi.ibs.common.model.PmtbCardNoSequence;
import com.cdgtaxi.ibs.common.model.PmtbProduct;
import com.cdgtaxi.ibs.common.model.PmtbProductCreditLimit;
import com.cdgtaxi.ibs.common.model.PmtbProductRenew;
import com.cdgtaxi.ibs.common.model.PmtbProductReplacement;
import com.cdgtaxi.ibs.common.model.PmtbProductRetag;
import com.cdgtaxi.ibs.common.model.PmtbProductStatus;
import com.cdgtaxi.ibs.common.model.PmtbProductType;
import com.cdgtaxi.ibs.master.model.MstbAdminFeeDetail;
import com.cdgtaxi.ibs.master.model.MstbMasterTable;
import com.cdgtaxi.ibs.master.model.MstbProdDiscDetail;
import com.cdgtaxi.ibs.product.ui.ProductSearchCriteria;
import com.cdgtaxi.ibs.util.DateUtil;
import com.google.common.collect.Lists;

public class ProductDaoHibernate extends GenericDaoHibernate implements ProductDao{
	
	private static final int MAX_ROW = 1;

	@SuppressWarnings("unchecked")
	public List<PmtbProduct> getAllProduct() {
		DetachedCriteria productCriteria = DetachedCriteria.forClass(PmtbProduct.class);
		return this.findAllByCriteria(productCriteria);
	}
	
	//These five methods are for View Hisory of (Renew,Replacement,Retag,Update Credit Limit and Status)
	//Start Code
	@SuppressWarnings("unchecked")
	public List<PmtbProductRenew> getRenewProducts(BigDecimal productId) {
		 DetachedCriteria productRenewCriteria = DetachedCriteria.forClass(PmtbProductRenew.class);
		 logger.info("ProductNo :"+ productId.toString());
		 DetachedCriteria productCriteria= productRenewCriteria.createCriteria("pmtbProduct", org.hibernate.sql.JoinType.LEFT_OUTER_JOIN);
		 productCriteria.add(Restrictions.eq("productNo", productId));
		 // productRenewCriteria.add(Restrictions.eq("productRenewNo", productId));
		 
		 List<PmtbProductRenew> results = this.findAllByCriteria(productRenewCriteria);
		 logger.info("The size of the result set"+results.size());
		 if(results.isEmpty()) return null;
		 else return results;
	}
	
	@SuppressWarnings("unchecked")
	public List<PmtbProductCreditLimit> getUpdatedCreditLimtProducts(BigDecimal productId){
		 DetachedCriteria productCreditLimitCriteria = DetachedCriteria.forClass(PmtbProductCreditLimit.class);
		 logger.info("ProductNo :"+ productId.toString());
		 DetachedCriteria productCriteria= productCreditLimitCriteria.createCriteria("pmtbProduct", org.hibernate.sql.JoinType.LEFT_OUTER_JOIN);
		 productCriteria.add(Restrictions.eq("productNo", productId));
		 List<PmtbProductCreditLimit> results = this.findAllByCriteria(productCreditLimitCriteria);
		 logger.info("The size of the result set"+results.size());
		 if(results.isEmpty()) return null;
		 else return results;
	}
	
	@SuppressWarnings("unchecked")
	public List<PmtbProductRetag> getRetagProducts(BigDecimal productId){
		 DetachedCriteria productRetagCriteria = DetachedCriteria.forClass(PmtbProductRetag.class);
		 logger.info("ProductNo :"+ productId.toString());
		 DetachedCriteria productCriteria= productRetagCriteria.createCriteria("pmtbProduct", org.hibernate.sql.JoinType.LEFT_OUTER_JOIN);
		 productCriteria.add(Restrictions.eq("productNo", productId));
		 DetachedCriteria accountCriteriaCurrentAccountNo= productRetagCriteria.createCriteria("amtbAccountByCurrentAccountNo", org.hibernate.sql.JoinType.LEFT_OUTER_JOIN);
		 DetachedCriteria accountCriteriaNewAccountNo= productRetagCriteria.createCriteria("amtbAccountByNewAccountNo", org.hibernate.sql.JoinType.LEFT_OUTER_JOIN);
		 List<PmtbProductRetag> results = this.findAllByCriteria(productRetagCriteria);
		 logger.info("The size of the result set"+results.size());
		 if(results.isEmpty()) return null;
		 else return results;
	}
	
	//Get the list of retag products from the card no list
	@SuppressWarnings("unchecked")
	public List<PmtbProduct> getRetagProducts(List <String> cardNoList ){
		 DetachedCriteria productCriteria = DetachedCriteria.forClass(PmtbProduct.class);
		 productCriteria.add(Restrictions.in("cardNo", cardNoList));
		 productCriteria.add(Restrictions.eq("currentStatus", NonConfigurableConstants.PRODUCT_STATUS_RECYCLED));
		 List<PmtbProduct> results = this.findAllByCriteria(productCriteria);
		 logger.info("The size of the result set"+results.size());
		 if(results.isEmpty()) return null;
		 else return results;
	}
	
	public PmtbProductRetag getRetagProductsByDate(BigDecimal productId, Timestamp ts){
		 DetachedCriteria productRetagCriteria = DetachedCriteria.forClass(PmtbProductRetag.class);
		 DetachedCriteria productCriteria= productRetagCriteria.createCriteria("pmtbProduct", org.hibernate.sql.JoinType.LEFT_OUTER_JOIN);
		 productCriteria.add(Restrictions.eq("productNo", productId));
		 DetachedCriteria accountCriteriaCurrentAccountNo= productRetagCriteria.createCriteria("amtbAccountByCurrentAccountNo", org.hibernate.sql.JoinType.LEFT_OUTER_JOIN);
		 accountCriteriaCurrentAccountNo.createCriteria("amtbCorporateDetails", Criteria.LEFT_JOIN);
		 DetachedCriteria account2ndLvlCriteria = accountCriteriaCurrentAccountNo.createCriteria("amtbAccount", Criteria.LEFT_JOIN);
		 account2ndLvlCriteria.createCriteria("amtbCorporateDetails", Criteria.LEFT_JOIN);
		 DetachedCriteria account3rdLvlCriteria = account2ndLvlCriteria.createCriteria("amtbAccount", Criteria.LEFT_JOIN);
		 account3rdLvlCriteria.createCriteria("amtbCorporateDetails", Criteria.LEFT_JOIN);
		 DetachedCriteria mainBillingContacts = accountCriteriaCurrentAccountNo.createCriteria("amtbAcctMainContacts", Criteria.LEFT_JOIN);
		 mainBillingContacts.add(Restrictions.eq("comp_id.mainContactType", NonConfigurableConstants.MAIN_CONTACT_TYPE_BILLING));
		 productRetagCriteria.add(Restrictions.ge("effectiveDt", ts));
		 productRetagCriteria.addOrder(Order.asc("effectiveDt"));
		 List<PmtbProductRetag> results = this.findAllByCriteria(productRetagCriteria);
		 if(results.isEmpty()) return null;
		 else return results.get(0);
	}
	
	@SuppressWarnings("unchecked")
	public List<PmtbProductReplacement> getReplaceProducts(BigDecimal productId) {
		 DetachedCriteria productReplacementCriteria = DetachedCriteria.forClass(PmtbProductReplacement.class);
		 logger.info("ProductNo :"+ productId.toString());
		 DetachedCriteria productCriteria= productReplacementCriteria.createCriteria("pmtbProduct", org.hibernate.sql.JoinType.LEFT_OUTER_JOIN);
		 productCriteria.add(Restrictions.eq("productNo", productId));
		 List<PmtbProductReplacement> results = this.findAllByCriteria(productReplacementCriteria);
		 logger.info("The size of the result set"+results.size());
		 if(results.isEmpty()) return null;
		 else return results;
	}
	
	public List<PmtbProductReplacement> getReplaceProducts(String cardNo) {
		 DetachedCriteria productReplacementCriteria = DetachedCriteria.forClass(PmtbProductReplacement.class);

		 productReplacementCriteria.add(Restrictions.or(Restrictions.eq("currentCardNo", cardNo), Restrictions.eq("newCardNo", cardNo)));
		 productReplacementCriteria.add(Restrictions.ge("mstbMasterTable.masterNo", new Integer(0)));
		 List<PmtbProductReplacement> results = this.findAllByCriteria(productReplacementCriteria);
		 
		 if(results.isEmpty()) return null;
		 else return results;
	}
	
	@SuppressWarnings("unchecked")
	public List<PmtbProductStatus> getProductStatus(BigDecimal productId) {
		 DetachedCriteria productStatusCriteria = DetachedCriteria.forClass(PmtbProductStatus.class);
		 logger.info("ProductNo :"+ productId.toString());
		 DetachedCriteria productCriteria= productStatusCriteria.createCriteria("pmtbProduct", org.hibernate.sql.JoinType.LEFT_OUTER_JOIN);
		 productStatusCriteria.createCriteria("mstbMasterTable", org.hibernate.sql.JoinType.LEFT_OUTER_JOIN);
		 productCriteria.add(Restrictions.eq("productNo", productId));
		 productStatusCriteria.addOrder(Order.desc("statusDt"));
		 productStatusCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		// productStatusCriteria.addOrder(Order.asc("statusDt"));
		 List<PmtbProductStatus> results = this.findAllByCriteria(productStatusCriteria);
		 logger.info("Criteria"+productStatusCriteria.toString());
		 logger.info("The size of the result set"+results.size());
		 if(results.isEmpty()) return null;
		 else return results;
	}
	
	//End of Code
	
	public String getMaxCardNo(PmtbProductType productType){
		
		DetachedCriteria productCriteria = DetachedCriteria.forClass(PmtbProduct.class);
		logger.debug("Retriving Max Card No by Card Type ");
		productCriteria.setProjection(Projections.max("cardNo"));
		//Start Code
		if(productType.getNumberOfDigit()>12)
			productCriteria.add(Restrictions.eq("pmtbProductType",productType));
		else{
			DetachedCriteria productTypeCriteria= productCriteria.createCriteria("pmtbProductType", org.hibernate.sql.JoinType.LEFT_OUTER_JOIN);
			productTypeCriteria.add(Restrictions.eq("numberOfDigit",productType.getNumberOfDigit()));
		}
		
		//End of Code
		List results = this.findMaxResultByCriteria(productCriteria,1);
		if(results.isEmpty()) return null;
		else return (String)results.get(0);
	}
	
	public AmtbContactPerson getcontactPersonCountry(AmtbContactPerson contactPerson){
		
		DetachedCriteria contactPersonCriteria = DetachedCriteria.forClass(AmtbContactPerson.class);
		contactPersonCriteria.add(Restrictions.eq("contactPersonNo", new Integer(contactPerson.getContactPersonNo())));
		DetachedCriteria masterCountryCriteria= contactPersonCriteria.createCriteria("mstbMasterTableByAddressCountry", org.hibernate.sql.JoinType.LEFT_OUTER_JOIN);
		List results = this.findMaxResultByCriteria(contactPersonCriteria,1);
		if(results.isEmpty()) return null;
		else return (AmtbContactPerson)results.get(0);
	}
/////////////
	public int getCountIssuedCards(String cardnoStart,String cardnoEnd,PmtbProductType productType){
		
		DetachedCriteria productCriteria = DetachedCriteria.forClass(PmtbProduct.class);
		productCriteria.add(Restrictions.ge("cardNo",cardnoStart));
		productCriteria.add(Restrictions.le("cardNo",cardnoEnd));
		if(productType.getOneTimeUsage().equalsIgnoreCase(NonConfigurableConstants.BOOLEAN_YES)){
			DetachedCriteria productTypeCriteria= productCriteria.createCriteria("pmtbProductType", org.hibernate.sql.JoinType.LEFT_OUTER_JOIN);
			productTypeCriteria.add(Restrictions.eq("oneTimeUsage",NonConfigurableConstants.BOOLEAN_YES));
			productCriteria.add(Restrictions.ne("currentStatus",NonConfigurableConstants.PRODUCT_STATUS_RECYCLED));
			//
			if(productType.getNumberOfDigit()<=12){
				productTypeCriteria.add(Restrictions.eq("numberOfDigit",productType.getNumberOfDigit()));
			}
			//
			
		}
		else
			productCriteria.add(Restrictions.eq("pmtbProductType", productType));
		List results = this.findAllByCriteria(productCriteria);
		logger.info("Criteria "+productCriteria.toString());
		logger.info("Count"+results.size());
		if(results.isEmpty()) return 0;
		else return results.size();
	}; 
	
	public PmtbProductType getProductTypebyProductId(BigDecimal productId){
		DetachedCriteria productTypeCriteria = DetachedCriteria.forClass(PmtbProductType.class);
		
		DetachedCriteria productCriteria= productTypeCriteria.createCriteria("pmtbProducts", org.hibernate.sql.JoinType.LEFT_OUTER_JOIN);
		productCriteria.add(Restrictions.eq("productNo",productId));
		
		List<PmtbProductType> results = this.findMaxResultByCriteria(productTypeCriteria,1);
		if(results.isEmpty()) return null;
		else return (PmtbProductType)results.get(0);
	}
//////////////	
	//start code for new method
	public List<PmtbProduct>getProducts(ProductSearchCriteria productSearchCriteria,PmtbProductType productType, HashSet<Integer> accountIdSet){
		
		DetachedCriteria productCriteria = DetachedCriteria.forClass(PmtbProduct.class);
		DetachedCriteria productStatusCriteria= productCriteria.createCriteria("pmtbProductStatuses", org.hibernate.sql.JoinType.LEFT_OUTER_JOIN);
		logger.debug("Retriving Products by product search Criteria ");
		logger.info("Account No : "+productSearchCriteria.getAccNo());
		
		DetachedCriteria accountCriteria= productCriteria.createCriteria("amtbAccount", org.hibernate.sql.JoinType.LEFT_OUTER_JOIN);
		accountCriteria.add(Restrictions.in("accountNo",accountIdSet));
		
		
		if(productSearchCriteria.getProductStatus()!=null){
			productCriteria.add(Restrictions.eq("currentStatus",productSearchCriteria.getProductStatus()));
		}
			//logger.info("This is criteria"+productSearchCriteria.getAccNo());
		if( productType!=null){
			logger.info("Product Type"+productType.getName());
			productCriteria.add(Restrictions.eq("pmtbProductType",productType));
		}	
		else{
			DetachedCriteria productTypeCriteria= productCriteria.createCriteria("pmtbProductType", org.hibernate.sql.JoinType.LEFT_OUTER_JOIN);
		}
		//	DetachedCriteria productStatus= productCriteria.createCriteria("pmtbProductStatuses", org.hibernate.sql.JoinType.LEFT_OUTER_JOIN);
		//	DetachedCriteria productTypeCriteria= productCriteria.createCriteria("pmtbProductType", org.hibernate.sql.JoinType.LEFT_OUTER_JOIN);
		//	}
		//if(productType!=null )
		//productCriteria.add(Restrictions.eq("pmtbProductType",productType));
		//if(productSearchCriteria.getCardNoStart().trim().length()>0 )		
		//if(productSearchCriteria.getCardNoEnd().trim().length()>0 )
		if(productSearchCriteria.getCardNoStart()!=null && productSearchCriteria.getCardNoEnd()!=null ){
			if(productSearchCriteria.getCardNoStart().trim().length()>0 && productSearchCriteria.getCardNoEnd().trim().length()>0)
				//productCriteria.add(Restrictions.between("cardNo", productSearchCriteria.getCardNoStart(), productSearchCriteria.getCardNoEnd()));
				productCriteria.add(Restrictions.sqlRestriction("to_number(CARD_NO) >= "+ productSearchCriteria.getCardNoStart()));
				productCriteria.add(Restrictions.sqlRestriction("to_number(CARD_NO) <= "+ productSearchCriteria.getCardNoEnd()));
	
		}
		else{
			if(productSearchCriteria.getCardNoStart()!=null && productSearchCriteria.getCardNoEnd()==null ){
				logger.info("Card No Start"+productSearchCriteria.getCardNoStart());
				if(productSearchCriteria.getCardNoStart().trim().length()>0)
					productCriteria.add(Restrictions.like("cardNo", productSearchCriteria.getCardNoStart()));
					//productCriteria.add(Restrictions.ge("cardNo", productSearchCriteria.getCardNoStart()));
			}
				
			if(productSearchCriteria.getCardNoEnd()!=null && productSearchCriteria.getCardNoStart()==null){
				logger.info("Card No End"+productSearchCriteria.getCardNoEnd());
				if(productSearchCriteria.getCardNoEnd().trim().length()>0)
					productCriteria.add(Restrictions.like("cardNo", productSearchCriteria.getCardNoEnd()));
					//productCriteria.add(Restrictions.le("cardNo", productSearchCriteria.getCardNoEnd()));
			}
		}
		
		if(productSearchCriteria.getIssueDateFrom()!=null){
			productCriteria.add(Restrictions.ge("issueDate",new java.sql.Date(productSearchCriteria.getIssueDateFrom().getTime())));
		}
		
		if(productSearchCriteria.getIssueDateTo()!=null)
			productCriteria.add(Restrictions.le("issueDate",new java.sql.Date(productSearchCriteria.getIssueDateTo().getTime())));
		if(productSearchCriteria.getExpiryDateFrom()!=null)
			productCriteria.add(Restrictions.ge("expiryDate",new java.sql.Date(productSearchCriteria.getExpiryDateFrom().getTime())));
		if(productSearchCriteria.getExpiryDateTo()!=null)
			productCriteria.add(Restrictions.le("expiryDate",new java.sql.Date(productSearchCriteria.getExpiryDateTo().getTime())));
	
		//For Product Suspension
		//Left Join with the Status Table where status to is  'S' (Suspend)
		//Check the suspend date range criteria
		//Check the sort by Criteria
		logger.info("###############################PRODUCT "+productSearchCriteria.getProductStatus());
		
		//DetachedCriteria productStatusCriteria= productCriteria.createCriteria("pmtbProductStatuses", org.hibernate.sql.JoinType.LEFT_OUTER_JOIN);
		if(productSearchCriteria.getProductStatus()==null || productSearchCriteria.getProductStatus().equalsIgnoreCase(NonConfigurableConstants.PRODUCT_STATUS_SUSPENDED)){
			logger.info("**********************PRODUCT "+productSearchCriteria.getProductStatus());
			//if(productSearchCriteria.getProductStatus().equalsIgnoreCase(NonConfigurableConstants.PRODUCT_STATUS_SUSPENDED))
			if((productSearchCriteria.getSuspensionDateFrom()!=null)|| (productSearchCriteria.getSuspensionDateTo()!=null))
				productStatusCriteria.add(Restrictions.eq("statusTo",NonConfigurableConstants.PRODUCT_STATUS_SUSPENDED));
			if(productSearchCriteria.getSuspensionDateFrom()!=null)
				productStatusCriteria.add(Restrictions.ge("statusDt",new java.sql.Timestamp(productSearchCriteria.getSuspensionDateFrom().getTime())));
			if(productSearchCriteria.getSuspensionDateTo()!=null)
				productStatusCriteria.add(Restrictions.le("statusDt",new java.sql.Timestamp(productSearchCriteria.getSuspensionDateTo().getTime())));
		}	

		if(productSearchCriteria.getSortBy()!=null && !productSearchCriteria.getSortBy().equalsIgnoreCase("") ){
			if(productSearchCriteria.getSortBy().equalsIgnoreCase(NonConfigurableConstants.SORT_BY_ISSUE_DATE))
				productCriteria.addOrder(Order.desc("issueDate"));
			if(productSearchCriteria.getSortBy().equalsIgnoreCase(NonConfigurableConstants.SORT_BY_EXPIRY_DATE))
				productCriteria.addOrder(Order.desc("expiryDate"));
			if(productSearchCriteria.getSortBy().equalsIgnoreCase(NonConfigurableConstants.SORT_BY_SUSPENSION_START_DATE)){
				productStatusCriteria.addOrder(Order.desc("statusDt"));
			}
		}
		else{
			logger.info("Default Sort");
			productCriteria.addOrder(Order.desc("cardNo"));
		}
		
		
		logger.info("This one is Detached Criteria "+productCriteria.toString());
		///return (List<PmtbProduct>)this.findAllByCriteria(productCriteria);
		//List<PmtbProduct> results = this.findAllByCriteria(productCriteria);
		List<PmtbProduct> results = this.findDefaultMaxResultByCriteria(productCriteria);
		logger.info("result size"+results.size());
		if(results.isEmpty()) return null;
		else return results;
	}
	
	//end of Code for new method
	@SuppressWarnings("unchecked")
	public List<PmtbProduct>getProducts(ProductSearchCriteria productSearchCriteria,PmtbProductType productType){
		
		DetachedCriteria productCriteria = DetachedCriteria.forClass(PmtbProduct.class);
		//DetachedCriteria productStatusCriteria= productCriteria.createCriteria("pmtbProductStatuses", org.hibernate.sql.JoinType.LEFT_OUTER_JOIN);
		DetachedCriteria accountCriteria= productCriteria.createCriteria("amtbAccount", org.hibernate.sql.JoinType.LEFT_OUTER_JOIN);
		accountCriteria	.createCriteria("amtbAccount", org.hibernate.sql.JoinType.LEFT_OUTER_JOIN).createCriteria("amtbAccount", org.hibernate.sql.JoinType.LEFT_OUTER_JOIN);
		
		//Account No Filter
		if(productSearchCriteria.getAccNo()!=null && productSearchCriteria.getAccNo().trim().length()>0){
			accountCriteria.add(Restrictions.like("accountNo", new Integer(productSearchCriteria.getAccNo())));
		}
		else{
			//Customer No Filter
			if(productSearchCriteria.getCustNo()!=null && productSearchCriteria.getCustNo().trim().length()>0){
				accountCriteria.add(Restrictions.eq("custNo",productSearchCriteria.getCustNo()));
			}
		}
		
		//Account Name Filter
		if(productSearchCriteria.getAccName()!=null && productSearchCriteria.getAccName().trim().length()>0)
			accountCriteria.add(Restrictions.ilike("accountName", productSearchCriteria.getAccName(),MatchMode.ANYWHERE));

		//Product Status Filter
		if(productSearchCriteria.getProductStatus()!=null)
			productCriteria.add(Restrictions.eq("currentStatus",productSearchCriteria.getProductStatus()));
		else
			productCriteria.add(Restrictions.ne("currentStatus",NonConfigurableConstants.PRODUCT_STATUS_RECYCLED));

		//Product Type Filter
		if( productType!=null){
			productCriteria.add(Restrictions.eq("pmtbProductType",productType));
		}	
		else{
			DetachedCriteria productTypeCriteria= productCriteria.createCriteria("pmtbProductType", org.hibernate.sql.JoinType.LEFT_OUTER_JOIN);
		}
		
		//Name On Card Filter
		if (productSearchCriteria.getNameOnCard()!= null && !"".equalsIgnoreCase(productSearchCriteria.getNameOnCard()))
			productCriteria.add(Restrictions.ilike("nameOnProduct", productSearchCriteria.getNameOnCard(),MatchMode.ANYWHERE));
		
		//Employee ID
		if (productSearchCriteria.getEmployeeId()!= null && !"".equalsIgnoreCase(productSearchCriteria.getEmployeeId()))
			productCriteria.add(Restrictions.ilike("employeeId", productSearchCriteria.getEmployeeId(),MatchMode.ANYWHERE));
		
		//Card Holder Mobile
		if(productSearchCriteria.getCardHolderMobile()!=null && productSearchCriteria.getCardHolderMobile().trim().length()>0){
			productCriteria.add(Restrictions.eq("cardHolderMobile",productSearchCriteria.getCardHolderMobile()));
		}
		
		//Card Holder Email
		if (productSearchCriteria.getCardHolderEmail()!= null && !"".equalsIgnoreCase(productSearchCriteria.getCardHolderEmail()))
			productCriteria.add(Restrictions.ilike("cardHolderEmail", productSearchCriteria.getCardHolderEmail(),MatchMode.ANYWHERE));
		
		//Card No Filter
		if(productSearchCriteria.getCardNoStart()!=null && productSearchCriteria.getCardNoEnd()!=null ){
			if(productSearchCriteria.getCardNoStart().trim().length()>0 && productSearchCriteria.getCardNoEnd().trim().length()>0){
				productCriteria.add(Restrictions.sqlRestriction("to_number(CARD_NO) >= "+ productSearchCriteria.getCardNoStart()));
				productCriteria.add(Restrictions.sqlRestriction("to_number(CARD_NO) <= "+ productSearchCriteria.getCardNoEnd()));
			}
		}
		else{
			if(productSearchCriteria.getCardNoStart()!=null && productSearchCriteria.getCardNoEnd()==null ){
				if(productSearchCriteria.getCardNoStart().trim().length()>0)
					productCriteria.add(Restrictions.like("cardNo", productSearchCriteria.getCardNoStart()));
			}
				
			if(productSearchCriteria.getCardNoEnd()!=null && productSearchCriteria.getCardNoStart()==null){
				if(productSearchCriteria.getCardNoEnd().trim().length()>0)
					productCriteria.add(Restrictions.like("cardNo", productSearchCriteria.getCardNoEnd()));
			}
		}
		
		//Issue Date From Filter
		if(productSearchCriteria.getIssueDateFrom()!=null){
			productCriteria.add(Restrictions.ge("issueDate",new java.sql.Date(productSearchCriteria.getIssueDateFrom().getTime())));
		}
		//Issue Date To Filter
		if(productSearchCriteria.getIssueDateTo()!=null)
			productCriteria.add(Restrictions.le("issueDate",new java.sql.Date(productSearchCriteria.getIssueDateTo().getTime())));
		
		//Expiry Date From Filter
		if(productSearchCriteria.getExpiryDateFrom()!=null)
			productCriteria.add(Restrictions.ge("expiryDate",new java.sql.Date(productSearchCriteria.getExpiryDateFrom().getTime())));
		//Expiry Date To Filter
		if(productSearchCriteria.getExpiryDateTo()!=null)
			productCriteria.add(Restrictions.le("expiryDate",new java.sql.Date(productSearchCriteria.getExpiryDateTo().getTime())));
	

		if(productSearchCriteria.getBalanceExpiryDateFrom()!=null)
			productCriteria.add(Restrictions.ge("balanceExpiryDate",productSearchCriteria.getBalanceExpiryDateFrom()));
		if(productSearchCriteria.getBalanceExpiryDateTo()!=null)
			productCriteria.add(Restrictions.le("balanceExpiryDate", productSearchCriteria.getBalanceExpiryDateTo()));

		//For Product Suspension
		if(productSearchCriteria.getSuspensionDateFrom()!=null || productSearchCriteria.getSuspensionDateTo()!=null){
			DetachedCriteria productStatusCriteria= productCriteria.createCriteria("pmtbProductStatuses","prod_status", org.hibernate.sql.JoinType.LEFT_OUTER_JOIN);;

			if(productSearchCriteria.getProductStatus()!=null && productSearchCriteria.getProductStatus().equals(NonConfigurableConstants.PRODUCT_STATUS_SUSPENDED)){
				//Pickup latest product status record
				DetachedCriteria subQuery = DetachedCriteria.forClass(PmtbProductStatus.class, "subQuery");
				subQuery.add(Restrictions.eq("subQuery.statusTo", NonConfigurableConstants.PRODUCT_STATUS_SUSPENDED));
				subQuery.add(Restrictions.le("subQuery.statusDt", new Date()));
				subQuery.add(Restrictions.eqProperty("subQuery.pmtbProduct", "prod_status.pmtbProduct"));
				subQuery.setProjection(Projections.max("statusDt"));
				productStatusCriteria.add(Subqueries.propertyEq("prod_status.statusDt", subQuery));
				
				if(productSearchCriteria.getSuspensionDateFrom()!=null)
					productStatusCriteria.add(Restrictions.ge("prod_status.statusDt",new java.sql.Timestamp(productSearchCriteria.getSuspensionDateFrom().getTime())));
				if(productSearchCriteria.getSuspensionDateTo()!=null)
					productStatusCriteria.add(Restrictions.le("prod_status.statusDt",new java.sql.Timestamp(productSearchCriteria.getSuspensionDateTo().getTime())));
			}
			else{
				productStatusCriteria.add(Restrictions.eq("prod_status.statusTo", NonConfigurableConstants.PRODUCT_STATUS_SUSPENDED));
				if(productSearchCriteria.getSuspensionDateFrom()!=null)
					productStatusCriteria.add(Restrictions.ge("prod_status.statusDt",new java.sql.Timestamp(productSearchCriteria.getSuspensionDateFrom().getTime())));
				if(productSearchCriteria.getSuspensionDateTo()!=null)
					productStatusCriteria.add(Restrictions.le("prod_status.statusDt",new java.sql.Timestamp(productSearchCriteria.getSuspensionDateTo().getTime())));
			}
			
			if(productSearchCriteria.getSortBy().equalsIgnoreCase(NonConfigurableConstants.SORT_BY_SUSPENSION_START_DATE)){
				productStatusCriteria.addOrder(Order.desc("statusDt"));
		}
		
		//Sorting
		if(productSearchCriteria.getSortBy()!=null && !productSearchCriteria.getSortBy().equalsIgnoreCase("") ){
			if(productSearchCriteria.getSortBy().equalsIgnoreCase(NonConfigurableConstants.SORT_BY_ISSUE_DATE))
				productCriteria.addOrder(Order.desc("issueDate"));
			if(productSearchCriteria.getSortBy().equalsIgnoreCase(NonConfigurableConstants.SORT_BY_EXPIRY_DATE))
				productCriteria.addOrder(Order.desc("expiryDate"));
			
			}
		}
		else{
			productCriteria.addOrder(Order.desc("cardNo"));
		}
		
		productCriteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		
		//Result
		List<PmtbProduct> results = this.findDefaultMaxResultByCriteria(productCriteria);
		if(results.isEmpty()) return null;
		else return results;
	}
	
		@SuppressWarnings("unchecked")
		public List<PmtbProduct> getProductsbyIdSet(Collection<BigDecimal> productIdSet){
			
			logger.debug("Retriving Products by product search Criteria ");
			DetachedCriteria productCriteria = DetachedCriteria.forClass(PmtbProduct.class, "product");
			productCriteria.add(Restrictions.in("productNo", productIdSet));
			DetachedCriteria productType= productCriteria.createCriteria("pmtbProductType", org.hibernate.sql.JoinType.LEFT_OUTER_JOIN);
			DetachedCriteria productStatusCriteria= productCriteria.createCriteria("pmtbProductStatuses","prod_status", org.hibernate.sql.JoinType.LEFT_OUTER_JOIN);
			//productStatusCriteria.add(Restrictions.eqProperty("statusTo", "product.currentStatus"));
			
			DetachedCriteria subQuery = DetachedCriteria.forClass(PmtbProductStatus.class, "subQuery");
			
			//subQuery.add(Restrictions.eq("subQuery.statusTo", "product.current_status"));
			subQuery.add(Restrictions.le("subQuery.statusDt", new Date()));
			subQuery.add(Restrictions.eqProperty("subQuery.pmtbProduct", "prod_status.pmtbProduct"));
			subQuery.setProjection(Projections.max("statusDt"));

//			DetachedCriteria mainQuery = DetachedCriteria.forClass(FmtbTransactionCode.class, "mainQuery");
//	
			//productStatusCriteria.add(Subqueries.propertyEq("prod_status.statusDt", subQuery));
			productStatusCriteria.add(Subqueries.propertyEq("prod_status.statusDt", subQuery));
			
			
			DetachedCriteria  accountCriteria= productCriteria.createCriteria("amtbAccount", org.hibernate.sql.JoinType.LEFT_OUTER_JOIN);
			//accountCriteria.createCriteria("amtbAcctStatuses", org.hibernate.sql.JoinType.LEFT_OUTER_JOIN);
			DetachedCriteria  secondLevelaccountCriteria=accountCriteria.createCriteria("amtbAccount", org.hibernate.sql.JoinType.LEFT_OUTER_JOIN);
			//secondLevelaccountCriteria.createCriteria("amtbAcctStatuses", org.hibernate.sql.JoinType.LEFT_OUTER_JOIN);
			DetachedCriteria  thirdLevelaccountCriteria=secondLevelaccountCriteria.createCriteria("amtbAccount", org.hibernate.sql.JoinType.LEFT_OUTER_JOIN);
			//thirdLevelaccountCriteria.createCriteria("amtbAcctStatuses", org.hibernate.sql.JoinType.LEFT_OUTER_JOIN);
			productCriteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
			List<PmtbProduct> results = this.findAllByCriteria(productCriteria);
			if(results.isEmpty()) return null;
			else return results;
			//return (List<PmtbProduct>)this.findAllByCriteria(productCriteria);
	}
		@SuppressWarnings("unchecked")
		public List<PmtbProduct> getProductsAssignCardbyIdSet(Collection<BigDecimal> productIdSet){
			
			logger.debug("Retriving Products by product search Criteria ");
			DetachedCriteria productCriteria = DetachedCriteria.forClass(PmtbProduct.class, "product");
			productCriteria.add(Restrictions.in("productNo", productIdSet));
			DetachedCriteria productType= productCriteria.createCriteria("pmtbProductType", org.hibernate.sql.JoinType.LEFT_OUTER_JOIN);
			DetachedCriteria productStatusCriteria= productCriteria.createCriteria("pmtbProductStatuses","prod_status", org.hibernate.sql.JoinType.LEFT_OUTER_JOIN);
			DetachedCriteria ittbCpCustCardIssuances = productCriteria.createCriteria("ittbCpCustCardIssuances", "ittb", org.hibernate.sql.JoinType.LEFT_OUTER_JOIN);
			
			DetachedCriteria subQuery = DetachedCriteria.forClass(PmtbProductStatus.class, "subQuery");
			
			subQuery.add(Restrictions.le("subQuery.statusDt", new Date()));
			subQuery.add(Restrictions.eqProperty("subQuery.pmtbProduct", "prod_status.pmtbProduct"));
			subQuery.setProjection(Projections.max("statusDt"));

			productStatusCriteria.add(Subqueries.propertyEq("prod_status.statusDt", subQuery));

			productCriteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
			List<PmtbProduct> results = this.findAllByCriteria(productCriteria);
			if(results.isEmpty()) return null;
			else return results;
	}
		
	public PmtbProduct getProduct(BigDecimal productId){
		logger.debug("Retriving Products by product search Criteria ");
		DetachedCriteria productCriteria = DetachedCriteria.forClass(PmtbProduct.class);
		DetachedCriteria amtbAccount = productCriteria.createCriteria("amtbAccount", org.hibernate.sql.JoinType.LEFT_OUTER_JOIN);
		DetachedCriteria amtbAccount2 = amtbAccount.createCriteria("amtbAccount", org.hibernate.sql.JoinType.LEFT_OUTER_JOIN);
		DetachedCriteria amtbAccount3 = amtbAccount2.createCriteria("amtbAccount", org.hibernate.sql.JoinType.LEFT_OUTER_JOIN);
//		productCriteria.createCriteria("amtbAccount", org.hibernate.sql.JoinType.LEFT_OUTER_JOIN);
		productCriteria.add(Restrictions.eq("productNo", productId));
		productCriteria.createCriteria("pmtbProductType", org.hibernate.sql.JoinType.LEFT_OUTER_JOIN);
		List<PmtbProduct> results = this.findAllByCriteria(productCriteria);
		if(results.isEmpty()) return null;
		else return results.get(0);
		//return (List<PmtbProduct>)this.findAllByCriteria(productCriteria);
	}
		
	@SuppressWarnings("unchecked")
	public PmtbProductStatus getProductStatus(PmtbProduct product){
			
		DetachedCriteria productStatusCriteria = DetachedCriteria.forClass(PmtbProductStatus.class);
		logger.debug("Retriving Products Status by Product object ");
		if(product==null) logger.info("This product is null");
		productStatusCriteria.add(Restrictions.eq("pmtbProduct", product));
		productStatusCriteria.add(Restrictions.eq("statusTo",NonConfigurableConstants.PRODUCT_STATUS_SUSPENDED));
//		productStatusCriteria.add(Restrictions.le("statusDt",currentDate));
//		productStatusCriteria.addOrder(Order.desc("statusDt"));
		List<PmtbProductStatus> results = this.findMaxResultByCriteria(productStatusCriteria,1);
		if(results.isEmpty()) return null;
		else return (PmtbProductStatus)results.get(0);
	}
		///\\
	@SuppressWarnings("unchecked")
	public boolean checkCard(String cardnoStart, PmtbProductType cardType){ 
	
		DetachedCriteria productCriteria = DetachedCriteria.forClass(PmtbProduct.class);
		productCriteria.add(Restrictions.eq("cardNo",cardnoStart));
		//
		if(cardType.getOneTimeUsage().equalsIgnoreCase(NonConfigurableConstants.BOOLEAN_YES)){
			DetachedCriteria productTypeCriteria= productCriteria.createCriteria("pmtbProductType", org.hibernate.sql.JoinType.LEFT_OUTER_JOIN);
			productTypeCriteria.add(Restrictions.eq("oneTimeUsage",NonConfigurableConstants.BOOLEAN_YES));
			//productCriteria.add(Restrictions.ne("currentStatus",NonConfigurableConstants.PRODUCT_STATUS_RECYCLED));
		}
		
		productCriteria.add(Restrictions.ne("currentStatus",NonConfigurableConstants.PRODUCT_STATUS_RECYCLED));
//		else
//			productCriteria.add(Restrictions.eq("pmtbProductType",cardType));
		
		List<PmtbProduct> results = this.findMaxResultByCriteria(productCriteria,1);
		if(results.isEmpty()) return false; //no card duplicate
		else return true;
	}


	@SuppressWarnings("unchecked")
	public java.sql.Date getValidExpiryDate(HashSet<BigDecimal> productIdSet) {
		DetachedCriteria productCriteria = DetachedCriteria.forClass(PmtbProduct.class);
		productCriteria.add(Restrictions.in("productNo", productIdSet));
		productCriteria.setProjection(Projections.max("expiryDate"));
		List<java.sql.Date> results = this.findMaxResultByCriteria(productCriteria,1);
		if(results.isEmpty()) return null;
		else return results.get(0);
	}
	
	@SuppressWarnings("unchecked")
	public java.sql.Timestamp getValidExpiryDateTime(HashSet<BigDecimal> productIdSet) {
		DetachedCriteria productCriteria = DetachedCriteria.forClass(PmtbProduct.class);
		productCriteria.add(Restrictions.in("productNo", productIdSet));
		productCriteria.setProjection(Projections.max("expiryTime"));
		List<java.sql.Timestamp> results = this.findMaxResultByCriteria(productCriteria,1);
		if(results.isEmpty()) return null;
		else return results.get(0);
	}

	@SuppressWarnings("unchecked")
	public List<PmtbProduct> getOtuCards(String startCardNo, String endCardNo) {
		DetachedCriteria productCriteria = DetachedCriteria.forClass(PmtbProduct.class);
		if(endCardNo==null || endCardNo.trim().equals(""))
			productCriteria.add(Restrictions.eq("cardNo", startCardNo));
			
		else{
			productCriteria.add(Restrictions.ge("cardNo", startCardNo));
			productCriteria.add(Restrictions.le("cardNo", endCardNo));
		}
		productCriteria.add(Restrictions.eq("currentStatus", NonConfigurableConstants.PRODUCT_STATUS_ACTIVE));
		//if(endCardNo!=null || !endCardNo.trim().equals(""))
			
		//productCriteria.add(Restrictions.eq("pmtbProductType", productType));
		DetachedCriteria productType= productCriteria.createCriteria("pmtbProductType", org.hibernate.sql.JoinType.LEFT_OUTER_JOIN);
		productType.add(Restrictions.eq("oneTimeUsage",NonConfigurableConstants.BOOLEAN_YES));
//		return  this.findAllByCriteria(productCriteria);
		logger.info("criteria"+productCriteria.toString());
		List<PmtbProduct> results = this.findAllByCriteria(productCriteria);
		logger.info("result size"+results.size());
		if(results.isEmpty()) return null;
		else return results;
	}

	public List<PmtbProductType> getOtuCardTypes() {
		DetachedCriteria productTypeCriteria = DetachedCriteria.forClass(PmtbProductType.class);
		productTypeCriteria.add(Restrictions.eq("oneTimeUsage", NonConfigurableConstants.BOOLEAN_YES));
		return this.findAllByCriteria(productTypeCriteria);
	}


	public PmtbProduct getProduct(PmtbProduct product) {
		DetachedCriteria productCriteria = DetachedCriteria.forClass(PmtbProduct.class);
		productCriteria.add(Restrictions.eq("productNo", product.getProductNo()));
		DetachedCriteria productType= productCriteria.createCriteria("pmtbProductType", DetachedCriteria.FULL_JOIN);
		List<PmtbProduct> results = this.findMaxResultByCriteria(productCriteria,1);
		logger.info("result size"+results.size());
		if(results.isEmpty()) return null;
		else return (PmtbProduct)results.get(0);
	
	}
		
	public List<PmtbProduct> getProductsCreditLimit (HashSet<BigDecimal> productIdSet){
		
		DetachedCriteria productCriteria = DetachedCriteria.forClass(PmtbProduct.class);
		productCriteria.add(Restrictions.in("productNo", productIdSet));
		DetachedCriteria productType= productCriteria.createCriteria("pmtbProductType", org.hibernate.sql.JoinType.LEFT_OUTER_JOIN);
		DetachedCriteria productStatus= productCriteria.createCriteria("pmtbProductStatuses", org.hibernate.sql.JoinType.LEFT_OUTER_JOIN);
		DetachedCriteria account= productCriteria.createCriteria("amtbAccount", org.hibernate.sql.JoinType.LEFT_OUTER_JOIN);
		List<PmtbProduct> results = this.findAllByCriteria(productCriteria);
		if(results.isEmpty()) return null;
		else return results;
		//return (List<PmtbProduct>)this.findAllByCriteria(productCriteria);
		
	}
	
	public List<PmtbProduct> getProductIssuanceHistory(String accountId, String productType){
		
		DetachedCriteria productCriteria = DetachedCriteria.forClass(PmtbProduct.class);
		DetachedCriteria accountCriteria = productCriteria.createCriteria("amtbAccount", org.hibernate.sql.JoinType.LEFT_OUTER_JOIN);
		accountCriteria.add(Restrictions.eq("accountNo", Integer.parseInt(accountId)));
		DetachedCriteria productTypeCriteria= productCriteria.createCriteria("pmtbProductType", org.hibernate.sql.JoinType.LEFT_OUTER_JOIN);
		productTypeCriteria.add(Restrictions.eq("productTypeId", productType));
		productCriteria.addOrder(Order.desc("createdDt"));
		List<PmtbProduct> results = this.findMaxResultByCriteria(productCriteria,5);
		logger.info("This is criteria :"+productCriteria.toString());
		if(results.isEmpty()) return null;
		else return results;
		//return (List<PmtbProduct>)this.findAllByCriteria(productCriteria);
		
	}

	public AmtbAcctStatus getAccountStatus(Integer accNo) {
		
		DetachedCriteria amtbAccountStatusCriteria = DetachedCriteria.forClass(AmtbAcctStatus.class);
		//amtbAccountStatusCriteria.add(Restrictions.eq("amtbAccount", accountId));
		DetachedCriteria accountCriteria= amtbAccountStatusCriteria.createCriteria("amtbAccount", org.hibernate.sql.JoinType.LEFT_OUTER_JOIN);
		accountCriteria.add(Restrictions.eq("accountNo", accNo));
		List<AmtbAcctStatus> results = this.findMaxResultByCriteria(amtbAccountStatusCriteria,1);
		if(results.isEmpty()) return null;
		else return results.get(0);
	}
		
	public MstbProdDiscDetail getProductDiscount(Integer accountNo, Timestamp tripDt, String productTypeID)
	{
		// If it is a department account
		DetachedCriteria productDiscDetailCriteria = DetachedCriteria.forClass(MstbProdDiscDetail.class);
		DetachedCriteria productDiscMasterCriteria = productDiscDetailCriteria.createCriteria("mstbProdDiscMaster", Criteria.LEFT_JOIN);
		DetachedCriteria subscribeToCriteria = productDiscMasterCriteria.createCriteria("amtbSubscTos", Criteria.LEFT_JOIN);
		
		subscribeToCriteria.add(Restrictions.eq("comp_id.amtbAccount.accountNo", accountNo));
		subscribeToCriteria.add(Restrictions.eq("comp_id.pmtbProductType.productTypeId", productTypeID));
		subscribeToCriteria.add(Restrictions.le("effectiveDt", tripDt));
		productDiscDetailCriteria.add(Restrictions.le("effectiveDt", tripDt));
		subscribeToCriteria.addOrder(Order.desc("effectiveDt"));
	
	
		List results = this.findMaxResultByCriteria(productDiscDetailCriteria, MAX_ROW);
		if(results.isEmpty()) return null;
		else return (MstbProdDiscDetail)results.get(0);
	}	

	@SuppressWarnings("unchecked")
	public MstbAdminFeeDetail getAdminFee(Integer topAcctNo, Timestamp tripDt)
	{
		this.getActiveDBTransaction();
		Session session = this.currentSession();
		List<MstbAdminFeeDetail> list = Lists.newArrayList();
		try{
			Query query = session.getNamedQuery("getAdminFee");
			query.setParameter("topAcctNo", topAcctNo, StandardBasicTypes.INTEGER);
			query.setParameter("runDate", tripDt, StandardBasicTypes.DATE);
			list = query.list();
		}
		catch(Exception e){
			e.printStackTrace();
		}
		finally{
			session.close();
		}

		if(list.isEmpty()){
			logger.debug("Not able to find MstbAdminFeeDetail with its effective date before "+ tripDt + " for account " + topAcctNo);
			return null;
		}
				
		return list.get(0);

	}	
	
	public PmtbProduct getProduct(String cardNo, Timestamp tripDt){
		DetachedCriteria productCriteria = DetachedCriteria.forClass(PmtbProduct.class);
		DetachedCriteria productStatusCriteria = productCriteria.createCriteria("pmtbProductStatuses",Criteria.LEFT_JOIN);
		productStatusCriteria.add(Restrictions.le("statusDt", tripDt));
		productCriteria.add(Restrictions.eq("cardNo", cardNo));
		productCriteria.addOrder(Order.desc("productNo"));	
		List results = this.findMaxResultByCriteria(productCriteria, MAX_ROW);
		if(results.isEmpty()) return null;
		else return (PmtbProduct)results.get(0);
	}
	
	public PmtbProductStatus getLatestProductStatus(String cardNo, Timestamp tripDt)
	{
		DetachedCriteria productStatusCriteria = DetachedCriteria.forClass(PmtbProductStatus.class);
		DetachedCriteria productCriteria = productStatusCriteria.createCriteria("pmtbProduct",Criteria.LEFT_JOIN);
		productStatusCriteria.createCriteria("mstbMasterTable",Criteria.LEFT_JOIN);
		productStatusCriteria.add(Restrictions.le("statusDt", tripDt));
		productCriteria.add(Restrictions.eq("cardNo", cardNo));
		productStatusCriteria.addOrder(Order.desc("statusDt"));
		List results = this.findMaxResultByCriteria(productStatusCriteria, MAX_ROW);
		if(results.isEmpty()) return null;
		else return (PmtbProductStatus)results.get(0);
	}
	
	public List<PmtbProductStatus> getFutureProductStatuses(String cardNo, Timestamp tripDt)
	{
		DetachedCriteria productStatusCriteria = DetachedCriteria.forClass(PmtbProductStatus.class);
		DetachedCriteria productCriteria = productStatusCriteria.createCriteria("pmtbProduct",Criteria.LEFT_JOIN);
		productStatusCriteria.add(Restrictions.ge("statusDt", tripDt));
		productCriteria.add(Restrictions.eq("cardNo", cardNo));
		
		//productStatusCriteria.addOrder(Order.desc("statusDt"));
		List<PmtbProductStatus> results = this.findAllByCriteria(productStatusCriteria);
		if(results.isEmpty()) return null;
		else return results;
	}
	
	public List<PmtbProductStatus> getFutureProductStatusesByProductNo(BigDecimal productNo, Timestamp tripDt)
	{
		DetachedCriteria productStatusCriteria = DetachedCriteria.forClass(PmtbProductStatus.class);
		productStatusCriteria.add(Restrictions.ge("statusDt", tripDt));
		productStatusCriteria.add(Restrictions.sqlRestriction(" this_.PRODUCT_NO = '"+productNo+"' "));
		//productStatusCriteria.addOrder(Order.desc("statusDt"));
		List<PmtbProductStatus> results = this.findAllByCriteria(productStatusCriteria);
		if(results.isEmpty()) return null;
		else return results;
	}
	
	public List<AmtbAcctStatus> getFutureIssuedAccountStatuses(String cardNo, Timestamp tripDt)
	{
		DetachedCriteria acctStatusCriteria = DetachedCriteria.forClass(AmtbAcctStatus.class);
		acctStatusCriteria.createCriteria("mstbMasterTable", Criteria.LEFT_JOIN);
		DetachedCriteria acctCriteria = acctStatusCriteria.createCriteria("amtbAccount",Criteria.LEFT_JOIN);
		DetachedCriteria productCriteria = acctCriteria.createCriteria("pmtbProducts", Criteria.LEFT_JOIN);
		acctStatusCriteria.add(Restrictions.ge("effectiveDt", tripDt));
		productCriteria.add(Restrictions.eq("cardNo", cardNo));
		
		//productStatusCriteria.addOrder(Order.desc("effectiveDt"));
		List<AmtbAcctStatus> results = this.findAllByCriteria(acctStatusCriteria);
		if(results.isEmpty()) return null;
		else return results;
	}
	
	public AmtbAcctStatus getCurrentIssuedAccountStatus(String cardNo, Timestamp tripDt)
	{
		DetachedCriteria acctStatusCriteria = DetachedCriteria.forClass(AmtbAcctStatus.class);
		DetachedCriteria acctCriteria = acctStatusCriteria.createCriteria("amtbAccount",Criteria.LEFT_JOIN);
		DetachedCriteria productCriteria = acctCriteria.createCriteria("pmtbProducts", Criteria.LEFT_JOIN);
		acctStatusCriteria.add(Restrictions.le("effectiveDt", tripDt));
		productCriteria.add(Restrictions.eq("cardNo", cardNo));
		
		acctStatusCriteria.addOrder(Order.desc("effectiveDt"));
		List<AmtbAcctStatus> results = this.findAllByCriteria(acctStatusCriteria);
		if(results.isEmpty()) return null;
		else return results.get(0);
	}
	
	public PmtbProductStatus getEarliestProductIssuedStatus(String cardNo)
	{
		DetachedCriteria productStatusCriteria = DetachedCriteria.forClass(PmtbProductStatus.class);
		DetachedCriteria productCriteria = productStatusCriteria.createCriteria("pmtbProduct",Criteria.FULL_JOIN);
		productCriteria.add(Restrictions.eq("cardNo", cardNo));
		productStatusCriteria.add(Restrictions.eq("statusFrom", NonConfigurableConstants.PRODUCT_STATUS_NEW));
		productStatusCriteria.addOrder(Order.asc("statusDt"));
		List results = this.findMaxResultByCriteria(productStatusCriteria, MAX_ROW);
		if(results.isEmpty()) return null;
		else return (PmtbProductStatus)results.get(0);
	}
	
	public PmtbProduct getProduct(String cardNo){
		DetachedCriteria productCriteria = DetachedCriteria.forClass(PmtbProduct.class);
		productCriteria.add(Restrictions.eq("cardNo", cardNo));
		productCriteria.addOrder(Order.desc("productNo"));
		// Get the latest productNo
		List results = this.findMaxResultByCriteria(productCriteria, MAX_ROW);
		if(results.isEmpty()) return null;
		else return (PmtbProduct)results.get(0);
	}
	public PmtbProduct getProductByCard(String cardNo){
		DetachedCriteria productCriteria = DetachedCriteria.forClass(PmtbProduct.class);
		productCriteria.createCriteria("pmtbProductType", org.hibernate.sql.JoinType.LEFT_OUTER_JOIN);
		productCriteria.add(Restrictions.eq("cardNo", cardNo));
		// Get the latest productNo
		List results = this.findAllByCriteria(productCriteria);
		if(results.isEmpty()) return null;
		else return (PmtbProduct)results.get(0);
	}
	public PmtbProductType getProductTypeByName(String name){
		DetachedCriteria productTypeCriteria = DetachedCriteria.forClass(PmtbProductType.class);
		productTypeCriteria.add(Restrictions.eq("name", name));
		// Get the latest productNo
		List results = this.findAllByCriteria(productTypeCriteria);
		if(results.isEmpty()) return null;
		else return (PmtbProductType)results.get(0);
	}
	@SuppressWarnings("unchecked")
	public List<PmtbProduct> getProductsWithStatus(AmtbAccount acct){
		DetachedCriteria productCriteria = DetachedCriteria.forClass(PmtbProduct.class);
		productCriteria.add(Restrictions.eq("amtbAccount", acct));
		productCriteria.createCriteria("pmtbProductStatuses", org.hibernate.sql.JoinType.LEFT_OUTER_JOIN);
		productCriteria.createCriteria("pmtbProductType", org.hibernate.sql.JoinType.LEFT_OUTER_JOIN);
		productCriteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		return this.findAllByCriteria(productCriteria);
	}

	@SuppressWarnings("unchecked")
	public boolean isFutureTermination(PmtbProduct product, Date effectiveDate) {
		
		//		boolean futureTermnination=false;
//		System.out.println("HERE I AM HERE"+product.getCardNo());
//		System.out.println("HERE I AM HERE"+product.getProductNo().toString());
		DetachedCriteria productStatusCriteria = DetachedCriteria.forClass(PmtbProductStatus.class);
		productStatusCriteria.add(Restrictions.eq("statusTo", NonConfigurableConstants.PRODUCT_STATUS_TERMINATED));
		productStatusCriteria.add(Restrictions.ge("statusDt",new java.sql.Timestamp(effectiveDate.getTime())));
		DetachedCriteria productCriteria = productStatusCriteria.createCriteria("pmtbProduct",Criteria.FULL_JOIN);
		productCriteria.add(Restrictions.eq("productNo", product.getProductNo()));
		List<PmtbProductStatus> results = this.findAllByCriteria(productStatusCriteria);
		logger.info("Criteria"+productStatusCriteria.toString());
		if(results.isEmpty()) return false;
		else return true;
	}
	
	@SuppressWarnings("unchecked")
	public PmtbProductStatus getFutureTermination(PmtbProduct product, Date effectiveDate) {
		
		DetachedCriteria productStatusCriteria = DetachedCriteria.forClass(PmtbProductStatus.class);
		productStatusCriteria.add(Restrictions.eq("statusTo", NonConfigurableConstants.PRODUCT_STATUS_TERMINATED));
		productStatusCriteria.add(Restrictions.ge("statusDt",new java.sql.Timestamp(effectiveDate.getTime())));
		DetachedCriteria productCriteria = productStatusCriteria.createCriteria("pmtbProduct",Criteria.FULL_JOIN);
		productCriteria.add(Restrictions.eq("productNo", product.getProductNo()));
		List<PmtbProductStatus> results = this.findMaxResultByCriteria(productStatusCriteria,1);
		logger.info("Criteria"+productStatusCriteria.toString());
		if(results.isEmpty()) return null;
		else return (PmtbProductStatus)results.get(0);
	}
	
	public boolean isFutureTerminationByRange(PmtbProduct product, Date currentDate, Date suspendDate) {
		
		DetachedCriteria productStatusCriteria = DetachedCriteria.forClass(PmtbProductStatus.class);
		productStatusCriteria.add(Restrictions.eq("statusTo", NonConfigurableConstants.PRODUCT_STATUS_TERMINATED));
		productStatusCriteria.add(Restrictions.ge("statusDt",new java.sql.Timestamp(currentDate.getTime())));
		productStatusCriteria.add(Restrictions.le("statusDt",new java.sql.Timestamp(suspendDate.getTime())));
		DetachedCriteria productCriteria = productStatusCriteria.createCriteria("pmtbProduct",Criteria.FULL_JOIN);
		productCriteria.add(Restrictions.eq("productNo", product.getProductNo()));
		List<PmtbProductStatus> results = this.findAllByCriteria(productStatusCriteria);
		logger.info("Criteria"+productStatusCriteria.toString());
		if(results.isEmpty()) return false;
		else return true;
	}

	public boolean isDuplicateScheduleCreditLimit(String creditLimitType,Date effectiveDateFrom,BigDecimal productId) {
		
		 DetachedCriteria productCreditLimitCriteria = DetachedCriteria.forClass(PmtbProductCreditLimit.class);
		 productCreditLimitCriteria.add(Restrictions.eq("creditLimitType", creditLimitType));
		 productCreditLimitCriteria.add(Restrictions.eq("effectiveDtFrom",new java.sql.Timestamp(effectiveDateFrom.getTime())));
			DetachedCriteria productCriteria =  productCreditLimitCriteria.createCriteria("pmtbProduct",Criteria.LEFT_JOIN);
			productCriteria.add(Restrictions.eq("productNo",productId));
				
		 List<PmtbProductCreditLimit> results = this.findAllByCriteria(productCreditLimitCriteria);
		 logger.info("The size of the result set "+productCreditLimitCriteria.toString());
		 logger.info("The size of the result set "+results.size());
		 if(results.isEmpty()) return false;
		 else return true;
	}

	public PmtbProduct getProductsbyId(BigDecimal productNo) {
		
		logger.debug("Retriving Products by product No ");
		DetachedCriteria productCriteria = DetachedCriteria.forClass(PmtbProduct.class);
		productCriteria.add(Restrictions.eq("productNo", productNo));
		productCriteria.add(Restrictions.ne("currentStatus", NonConfigurableConstants.PRODUCT_STATUS_RECYCLED));
		DetachedCriteria account= productCriteria.createCriteria("amtbAccount", org.hibernate.sql.JoinType.LEFT_OUTER_JOIN);
		List<PmtbProduct> results = this.findAllByCriteria(productCriteria);
		logger.info("Result's Size   : "+results.size());
		logger.info("Criteria   : "+productCriteria.toString());
		if(results.isEmpty()) return null;
		else return results.get(0);

	}
	@SuppressWarnings("unchecked")
	public List<PmtbProduct> getEmbossingProducts(String productTypeId, String cardNoStart, String cardNoEnd, Date issueStart, Date issueEnd, Date replaceStart, Date replaceEnd, Date renewStart, Date renewEnd, String sortBy, boolean isReprint){
		logger.info("getEmbossingProducts(String productTypeId, String cardNoStart, String cardNoEnd, Date issueStart, Date issueEnd, Date replaceStart, Date replaceEnd)");
		DetachedCriteria productCriteria = DetachedCriteria.forClass(PmtbProduct.class);
		// joining up with product type and filter by product type id
		productCriteria.createCriteria("pmtbProductType", org.hibernate.sql.JoinType.LEFT_OUTER_JOIN)
			.add(Restrictions.eq("productTypeId", productTypeId));
		// only active or suspended cards
		productCriteria.add(Restrictions.or(
				Restrictions.eq("currentStatus", NonConfigurableConstants.PRODUCT_STATUS_ACTIVE),
				Restrictions.or(Restrictions.eq("currentStatus", NonConfigurableConstants.PRODUCT_STATUS_SUSPENDED),
						Restrictions.eq("currentStatus", NonConfigurableConstants.PRODUCT_STATUS_PARENT_SUSPENDED))));
		if(cardNoStart!=null && cardNoStart.length()!=0){
			if(cardNoEnd!=null && cardNoEnd.length()!=0){// with start and end
				productCriteria.add(Restrictions.between("cardNo", new BigDecimal(cardNoStart).toString(), new BigDecimal(cardNoEnd).toString()));
			}else{// only start
				productCriteria.add(Restrictions.eq("cardNo", new BigDecimal(cardNoStart).toString()));
			}
		}
		Disjunction disjunction = Restrictions.disjunction();
		// issue
		if(issueStart!=null){
//			if(issueEnd!=null){// with start and end
//				productCriteria.add(Restrictions.between("issueDate", issueStart, issueEnd));
//			}else{
//				productCriteria.add(Restrictions.eq("issueDate", issueStart));
//			}
//			if(!isReprint){
//				productCriteria.add(Restrictions.or(Restrictions.eq("embossFlag", NonConfigurableConstants.BOOLEAN_NO), Restrictions.isNull("embossFlag")));
//			}
			DetachedCriteria issueCriteria = DetachedCriteria.forClass(PmtbProduct.class);
			if(issueEnd!=null){// with start and end
				issueCriteria.add(Restrictions.between("issueDate", issueStart, issueEnd));
			}else{
				issueCriteria.add(Restrictions.eq("issueDate", issueStart));
			}
			if(!isReprint){
				issueCriteria.add(Restrictions.or(Restrictions.eq("embossFlag", NonConfigurableConstants.BOOLEAN_NO), Restrictions.isNull("embossFlag")));
			}
			issueCriteria.setProjection(Projections.distinct(Projections.property("productNo")));
			disjunction.add(Subqueries.propertyIn("productNo", issueCriteria));
		}
		// replace
		if(replaceStart!=null){
//			DetachedCriteria replaceCriteria = productCriteria.createCriteria("pmtbProductReplacements", org.hibernate.sql.JoinType.LEFT_OUTER_JOIN);
//			if(replaceEnd!=null){
//				replaceCriteria.add(Restrictions.between("replacementDate", replaceStart, replaceEnd));
//			}else{
//				replaceCriteria.add(Restrictions.eq("replacementDate", replaceStart));
//			}
//			if(!isReprint){
//				replaceCriteria.add(Restrictions.or(Restrictions.eq("embossFlag", NonConfigurableConstants.BOOLEAN_NO), Restrictions.isNull("embossFlag")));
//			}
			DetachedCriteria replaceCriteria = DetachedCriteria.forClass(PmtbProductReplacement.class);
			if(replaceEnd!=null){
				replaceCriteria.add(Restrictions.between("replacementDate", replaceStart, replaceEnd));
			}else{
				replaceCriteria.add(Restrictions.eq("replacementDate", replaceStart));
			}
			if(!isReprint){
				replaceCriteria.add(Restrictions.or(Restrictions.eq("embossFlag", NonConfigurableConstants.BOOLEAN_NO), Restrictions.isNull("embossFlag")));
			}
			replaceCriteria.setProjection(Projections.distinct(Projections.property("pmtbProduct")));
			disjunction.add(Subqueries.propertyIn("productNo", replaceCriteria));
		}
		// renew
		if(renewStart!=null){
//			DetachedCriteria renewCriteria = productCriteria.createCriteria("pmtbProductRenews", org.hibernate.sql.JoinType.LEFT_OUTER_JOIN);
//			if(renewEnd!=null){
//				renewCriteria.add(Restrictions.between("renewDate", renewStart, renewEnd));
//			}else{
//				renewCriteria.add(Restrictions.eq("renewDate", renewStart));
//			}
//			if(!isReprint){
//				renewCriteria.add(Restrictions.or(Restrictions.eq("embossFlag", NonConfigurableConstants.BOOLEAN_NO), Restrictions.isNull("embossFlag")));
//			}
			DetachedCriteria renewCriteria = DetachedCriteria.forClass(PmtbProductRenew.class);
			if(renewEnd!=null){
				renewCriteria.add(Restrictions.between("renewDate", renewStart, renewEnd));
			}else{
				renewCriteria.add(Restrictions.eq("renewDate", renewStart));
			}
			if(!isReprint){
				renewCriteria.add(Restrictions.or(Restrictions.eq("embossFlag", NonConfigurableConstants.BOOLEAN_NO), Restrictions.isNull("embossFlag")));
			}
			renewCriteria.setProjection(Projections.distinct(Projections.property("pmtbProduct")));
			disjunction.add(Subqueries.propertyIn("productNo", renewCriteria));
		}
		productCriteria.add(disjunction);
		DetachedCriteria acctCriteria = productCriteria.createCriteria("amtbAccount", org.hibernate.sql.JoinType.LEFT_OUTER_JOIN);
		if(sortBy.equals(NonConfigurableConstants.PRODUCT_EMBOSS_SORT_BY_ACCT_NO)){
			acctCriteria.addOrder(Order.asc("code"));
			acctCriteria.addOrder(Order.asc("custNo"));
		}
		acctCriteria = acctCriteria.createCriteria("amtbAccount", org.hibernate.sql.JoinType.LEFT_OUTER_JOIN);
		if(sortBy.equals(NonConfigurableConstants.PRODUCT_EMBOSS_SORT_BY_ACCT_NO)){
			acctCriteria.addOrder(Order.asc("code"));
			acctCriteria.addOrder(Order.asc("custNo"));
		}
		acctCriteria = acctCriteria.createCriteria("amtbAccount", org.hibernate.sql.JoinType.LEFT_OUTER_JOIN);
		if(sortBy.equals(NonConfigurableConstants.PRODUCT_EMBOSS_SORT_BY_ACCT_NO)){
			acctCriteria.addOrder(Order.asc("code"));
			acctCriteria.addOrder(Order.asc("custNo"));
		}
		acctCriteria.createCriteria("amtbPersonalDetails", org.hibernate.sql.JoinType.LEFT_OUTER_JOIN);
		productCriteria.addOrder(Order.asc("cardNo"));
		productCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		return this.findAllByCriteria(productCriteria);
	}

	@SuppressWarnings("unchecked")
	public boolean checkAuthorizedAccount(Integer accountNo,BigDecimal productNo) {
		
		PmtbProduct product=getProdcutbyProductNo(productNo);
		DetachedCriteria accountCriteria = DetachedCriteria.forClass(AmtbAccount.class);
		logger.info("New Account>>>"+accountNo);
		logger.info("Product Type >>>"+product.getPmtbProductType().getName());
		DetachedCriteria subscribeToCriteria = accountCriteria.createCriteria("amtbSubscTos", Criteria.LEFT_JOIN);
		subscribeToCriteria.add(Restrictions.eq("comp_id.amtbAccount.accountNo", accountNo));
		subscribeToCriteria.add(Restrictions.eq("comp_id.pmtbProductType", product.getPmtbProductType()));
		List<PmtbProduct> results = this.findAllByCriteria(accountCriteria);
		 logger.info("**********Criteraia : "+accountCriteria.toString());
		 logger.info("The size of the result set : "+results.size());
		 if(results.isEmpty()) return false;
		 else return true;
		
	}
	public PmtbProduct getProdcutbyProductNo(BigDecimal productNo){
		
		//PmtbProduct product=new PmtbProduct();
		DetachedCriteria productCriteria = DetachedCriteria.forClass(PmtbProduct.class);
		// joining up with product type and filter by product type id
		productCriteria.createCriteria("pmtbProductType", org.hibernate.sql.JoinType.LEFT_OUTER_JOIN);
		productCriteria.add(Restrictions.eq("productNo", productNo));
		List<PmtbProduct> results = this.findMaxResultByCriteria(productCriteria,1);
		logger.info("**********Criteraia : "+productCriteria.toString());
		logger.info("The size of the result set : "+results.size());
		if(results.isEmpty()) return null;
		else return results.get(0);
		//return product;
	}

	public PmtbProduct getProductAndAccount(PmtbProduct product) {
		DetachedCriteria productCriteria = DetachedCriteria.forClass(PmtbProduct.class);
		productCriteria.createCriteria("amtbAccount", org.hibernate.sql.JoinType.LEFT_OUTER_JOIN)
		.createCriteria("amtbAccount", org.hibernate.sql.JoinType.LEFT_OUTER_JOIN)
		.createCriteria("amtbAccount", org.hibernate.sql.JoinType.LEFT_OUTER_JOIN);
		productCriteria.add(Restrictions.eq("productNo", product.getProductNo()));
		productCriteria.createCriteria("pmtbProductType", org.hibernate.sql.JoinType.LEFT_OUTER_JOIN);
		List<PmtbProduct> results = this.findMaxResultByCriteria(productCriteria,1);
		if(results.isEmpty()) return null;
		else return results.get(0);
	}
	public boolean hasEmbossingProducts(String productTypeId, String cardNoStart, String cardNoEnd, Date issueStart, Date issueEnd, Date replaceStart, Date replaceEnd, Date renewStart, Date renewEnd){
		logger.info("getEmbossingProducts(String productTypeId, String cardNoStart, String cardNoEnd, Date issueStart, Date issueEnd, Date replaceStart, Date replaceEnd)");
		DetachedCriteria productCriteria = DetachedCriteria.forClass(PmtbProduct.class);
			
		// joining up with product type and filter by product type id
		productCriteria.createCriteria("pmtbProductType", org.hibernate.sql.JoinType.LEFT_OUTER_JOIN)
			.add(Restrictions.eq("productTypeId", productTypeId));
		// only active or suspended cards
		productCriteria.add(Restrictions.or(
				Restrictions.eq("currentStatus", NonConfigurableConstants.PRODUCT_STATUS_ACTIVE),
				Restrictions.or(Restrictions.eq("currentStatus", NonConfigurableConstants.PRODUCT_STATUS_SUSPENDED),
						Restrictions.eq("currentStatus", NonConfigurableConstants.PRODUCT_STATUS_PARENT_SUSPENDED))));
		if(cardNoStart!=null && cardNoStart.length()!=0){
			if(cardNoEnd!=null && cardNoEnd.length()!=0){// with start and end
				productCriteria.add(Restrictions.between("cardNo", cardNoStart, cardNoEnd));
			}else{// only start
				productCriteria.add(Restrictions.eq("cardNo", cardNoStart));
			}
		}
		// issue
		if(issueStart!=null){
			if(issueEnd!=null){// with start and end
				productCriteria.add(Restrictions.between("issueDate", issueStart, issueEnd));
			}else{
				productCriteria.add(Restrictions.eq("issueDate", issueStart));
			}
		}
		// replace
		if(replaceStart!=null){
			DetachedCriteria replaceCriteria = productCriteria.createCriteria("pmtbProductReplacements", org.hibernate.sql.JoinType.LEFT_OUTER_JOIN);
			if(replaceEnd!=null){
				replaceCriteria.add(Restrictions.between("replacementDate", replaceStart, replaceEnd));
			}else{
				replaceCriteria.add(Restrictions.eq("replacementDate", replaceStart));
			}
		}
		// renew
		if(renewStart!=null){
			DetachedCriteria renewCriteria = productCriteria.createCriteria("pmtbProductRenews", org.hibernate.sql.JoinType.LEFT_OUTER_JOIN);
			if(renewEnd!=null){
				renewCriteria.add(Restrictions.between("renewDate", renewStart, renewEnd));
			}else{
				renewCriteria.add(Restrictions.eq("renewDate", renewStart));
			}
		}
		return !this.findAllByCriteria(productCriteria).isEmpty();
	}

	//Optimized
	
	public List<PmtbProduct> getAllProductsbyParentID(Integer parentAccountId, ProductSearchCriteria productSearchCriteria,PmtbProductType productType) {
		logger.info("Retrieve accounts");
		DetachedCriteria productCriteria = DetachedCriteria.forClass(PmtbProduct.class);
		
		//If Account is Dept
		DetachedCriteria ChildAccountCriteria = productCriteria.createCriteria("amtbAccount","threeLevel" ,Criteria.LEFT_JOIN); //join dept
		DetachedCriteria ParentAccountCriteria = ChildAccountCriteria.createCriteria("amtbAccount","twoLevel" , Criteria.LEFT_JOIN); //join division
		DetachedCriteria GrandParentAccountCriteria = ParentAccountCriteria.createCriteria("amtbAccount","oneLevel" , Criteria.LEFT_JOIN); //join corp
		
		DetachedCriteria mainContactCriteria = ChildAccountCriteria.createCriteria("amtbAcctMainContacts", "mainShippingContacts", Criteria.LEFT_JOIN);
		mainContactCriteria.createCriteria("amtbContactPerson", Criteria.LEFT_JOIN);
		
		productCriteria.add(Restrictions.or(				
			Restrictions.eq("threeLevel.accountNo",parentAccountId),
				Restrictions.or(
					Restrictions.eq("oneLevel.accountNo",parentAccountId),
					Restrictions.eq("twoLevel.accountNo",parentAccountId)
				)
			)
		);
		
		
		logger.info("Status"+productSearchCriteria.getProductStatus());
		if(productSearchCriteria.getProductStatus()!=null){
			productCriteria.add(Restrictions.eq("currentStatus",productSearchCriteria.getProductStatus()));
		}
		else
			// To prevent recycled from retrieving if ALL is selected for product status
			productCriteria.add(Restrictions.ne("currentStatus", NonConfigurableConstants.PRODUCT_STATUS_RECYCLED));
			
		if( productType!=null){
			logger.info("Product Type"+productType.getName());
			productCriteria.add(Restrictions.eq("pmtbProductType",productType));
		}	
		
		if(productSearchCriteria.getCardNoStart()!=null && productSearchCriteria.getCardNoEnd()!=null ){
			if(productSearchCriteria.getCardNoStart().trim().length()>0 && productSearchCriteria.getCardNoEnd().trim().length()>0){
				//productCriteria.add(Restrictions.between("cardNo", productSearchCriteria.getCardNoStart(), productSearchCriteria.getCardNoEnd()));
				productCriteria.add(Restrictions.sqlRestriction("to_number(CARD_NO) >= "+ productSearchCriteria.getCardNoStart()));
				productCriteria.add(Restrictions.sqlRestriction("to_number(CARD_NO) <= "+ productSearchCriteria.getCardNoEnd()));
			}
		}
		else{
			if(productSearchCriteria.getCardNoStart()!=null && productSearchCriteria.getCardNoEnd()==null ){
				logger.info("Card No Start"+productSearchCriteria.getCardNoStart());
				if(productSearchCriteria.getCardNoStart().trim().length()>0)
					productCriteria.add(Restrictions.like("cardNo", productSearchCriteria.getCardNoStart()));
			}
				
			if(productSearchCriteria.getCardNoEnd()!=null && productSearchCriteria.getCardNoStart()==null){
				logger.info("Card No End"+productSearchCriteria.getCardNoEnd());
				if(productSearchCriteria.getCardNoEnd().trim().length()>0)
					productCriteria.add(Restrictions.like("cardNo", productSearchCriteria.getCardNoEnd()));
			}
		}
		
		if (productSearchCriteria.getNameOnCard()!= null && !"".equalsIgnoreCase(productSearchCriteria.getNameOnCard()))
		{
			productCriteria.add(Restrictions.ilike("nameOnProduct", productSearchCriteria.getNameOnCard(),MatchMode.ANYWHERE));
		}
		
		if(productSearchCriteria.getIssueDateFrom()!=null){
			productCriteria.add(Restrictions.ge("issueDate",new java.sql.Date(productSearchCriteria.getIssueDateFrom().getTime())));
		}
		
		if(productSearchCriteria.getIssueDateTo()!=null)
			productCriteria.add(Restrictions.le("issueDate",new java.sql.Date(productSearchCriteria.getIssueDateTo().getTime())));
		if(productSearchCriteria.getExpiryDateFrom()!=null)
			productCriteria.add(Restrictions.ge("expiryDate",new java.sql.Date(productSearchCriteria.getExpiryDateFrom().getTime())));
		if(productSearchCriteria.getExpiryDateTo()!=null)
			productCriteria.add(Restrictions.le("expiryDate",new java.sql.Date(productSearchCriteria.getExpiryDateTo().getTime())));
		
		
		
		if(productSearchCriteria.getBalanceExpiryDateFrom()!=null)
			productCriteria.add(Restrictions.ge("balanceExpiryDate",productSearchCriteria.getBalanceExpiryDateFrom()));
		if(productSearchCriteria.getBalanceExpiryDateTo()!=null)
			productCriteria.add(Restrictions.le("balanceExpiryDate", productSearchCriteria.getBalanceExpiryDateTo()));

		if(productSearchCriteria.getProductStatus()==null || productSearchCriteria.getProductStatus().equalsIgnoreCase(NonConfigurableConstants.PRODUCT_STATUS_SUSPENDED)){
			//DetachedCriteria productStatusCriteria= productCriteria.createCriteria("pmtbProductStatuses","prod_status", DetachedCriteria.INNER_JOIN);;
			DetachedCriteria productStatusCriteria= productCriteria.createCriteria("pmtbProductStatuses","prod_status", org.hibernate.sql.JoinType.LEFT_OUTER_JOIN);;
			//productStatusCriteria.add(Restrictions.eq("prod_status.statusTo",NonConfigurableConstants.PRODUCT_STATUS_SUSPENDED));
			//productStatusCriteria.setProjection(Projections.max("prod_status.statusDt"));

			//Start Sub Query
			DetachedCriteria subQuery = DetachedCriteria.forClass(PmtbProductStatus.class, "subQuery");
			if(productSearchCriteria.getProductStatus()!=null )
				subQuery.add(Restrictions.eq("subQuery.statusTo", NonConfigurableConstants.PRODUCT_STATUS_SUSPENDED));
			subQuery.add(Restrictions.le("subQuery.statusDt", new Date()));
			subQuery.add(Restrictions.eqProperty("subQuery.pmtbProduct", "prod_status.pmtbProduct"));
			subQuery.setProjection(Projections.max("statusDt"));

//			DetachedCriteria mainQuery = DetachedCriteria.forClass(FmtbTransactionCode.class, "mainQuery");
//	
			productStatusCriteria.add(Subqueries.propertyEq("prod_status.statusDt", subQuery));
			//End of Sub Query
			
			if(productSearchCriteria.getSortBy().equalsIgnoreCase(NonConfigurableConstants.SORT_BY_SUSPENSION_START_DATE)){
				productStatusCriteria.addOrder(Order.desc("statusDt"));
			//if((productSearchCriteria.getSuspensionDateFrom()!=null)|| (productSearchCriteria.getSuspensionDateTo()!=null))
			if(productSearchCriteria.getSuspensionDateFrom()==null && productSearchCriteria.getSuspensionDateTo()==null){
				//productStatusCriteria.add(Restrictions.sqlRestriction("rownum<=1 and status_Dt <= sysdate order by staus_Dt des"));
				logger.info("Suspend Dates are null");
				productStatusCriteria.add(Restrictions.le("prod_status.statusDt",DateUtil.getCurrentTimestamp()));
				
			}
			else{
				if(productSearchCriteria.getSuspensionDateFrom()!=null)
					productStatusCriteria.add(Restrictions.ge("statusDt",new java.sql.Timestamp(productSearchCriteria.getSuspensionDateFrom().getTime())));
				if(productSearchCriteria.getSuspensionDateTo()!=null)
					productStatusCriteria.add(Restrictions.le("statusDt",new java.sql.Timestamp(productSearchCriteria.getSuspensionDateTo().getTime())));
			}	
		}
		//	DetachedCriteria productTypeCriteria= productCriteria.createCriteria("pmtbProductType", org.hibernate.sql.JoinType.LEFT_OUTER_JOIN);
		logger.info("Sort By "+productSearchCriteria.getSortBy());
		if(productSearchCriteria.getSortBy()!=null && !productSearchCriteria.getSortBy().equalsIgnoreCase("") ){
			if(productSearchCriteria.getSortBy().equalsIgnoreCase(NonConfigurableConstants.SORT_BY_ISSUE_DATE))
				productCriteria.addOrder(Order.desc("issueDate"));
			if(productSearchCriteria.getSortBy().equalsIgnoreCase(NonConfigurableConstants.SORT_BY_EXPIRY_DATE))
				productCriteria.addOrder(Order.desc("expiryDate"));
			
			}
		}
		else{
			logger.info("Default Sort");
			productCriteria.addOrder(Order.desc("cardNo"));
		}
		
		mainContactCriteria.add(Restrictions.eq("mainShippingContacts.comp_id.mainContactType", NonConfigurableConstants.MAIN_CONTACT_TYPE_SHIPPING));
			
		//	productCriteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		
		//List<PmtbProduct> results = this.findAllByCriteria(productCriteria);
		logger.info("DETACH CRITERIA IS : \n"+productCriteria.toString());
		List<PmtbProduct> results = this.findDefaultMaxResultByCriteria(productCriteria);
		if(results.isEmpty()) {
			return null;
		}else{
			logger.info("Result Size: "+results.size());
			return results;
		}
	}
	public boolean isFutureTerminationByRange(List<BigDecimal> productIds, Date currentDate, Date suspendDate) {
		DetachedCriteria productStatusCriteria = DetachedCriteria.forClass(PmtbProductStatus.class);
		productStatusCriteria.add(Restrictions.eq("statusTo", NonConfigurableConstants.PRODUCT_STATUS_TERMINATED));
		productStatusCriteria.add(Restrictions.ge("statusDt",new java.sql.Timestamp(currentDate.getTime())));
		productStatusCriteria.add(Restrictions.le("statusDt",new java.sql.Timestamp(suspendDate.getTime())));
		DetachedCriteria productCriteria = productStatusCriteria.createCriteria("pmtbProduct",org.hibernate.sql.JoinType.LEFT_OUTER_JOIN);
		productCriteria.add(Restrictions.in("productNo", productIds));
		List<PmtbProductStatus> results = this.findAllByCriteria(productStatusCriteria);
		if(results.isEmpty()) return false;
		else return true;
	}
	public boolean hasStatus(List<BigDecimal> productIds, Date statusDate) {
		DetachedCriteria productStatusCriteria = DetachedCriteria.forClass(PmtbProductStatus.class);
		productStatusCriteria.add(Restrictions.eq("statusDt",new java.sql.Timestamp(statusDate.getTime())));
		DetachedCriteria productCriteria = productStatusCriteria.createCriteria("pmtbProduct",org.hibernate.sql.JoinType.LEFT_OUTER_JOIN);
		productCriteria.add(Restrictions.in("productNo", productIds));
		List<PmtbProductStatus> results = this.findAllByCriteria(productStatusCriteria);
		if(results.isEmpty()) return false;
		else return true;
	}
	public List<PmtbProduct> getProducts(Collection<BigDecimal> productIds){
		DetachedCriteria productCriteria = DetachedCriteria.forClass(PmtbProduct.class);
		productCriteria.add(Restrictions.in("productNo", productIds));
		productCriteria.createCriteria("pmtbProductStatuses", org.hibernate.sql.JoinType.LEFT_OUTER_JOIN);
		productCriteria
			.createCriteria("amtbAccount", org.hibernate.sql.JoinType.LEFT_OUTER_JOIN)
			.createCriteria("amtbAcctStatuses", org.hibernate.sql.JoinType.LEFT_OUTER_JOIN);
		productCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		return this.findAllByCriteria(productCriteria);
	}
	
	public List<Object[]> getNegativeExternalProduct(String cardNoStart, String cardNoEnd, PmtbProductType productType){
		
		this.getActiveDBTransaction();
		Session session = this.currentSession();
		List results = new ArrayList();
		try{
			Query query = session.getNamedQuery("getNegativeExternalProduct");
			
			query.setParameter("cardNoStart", cardNoStart, StandardBasicTypes.STRING);
			query.setParameter("cardNoEnd", cardNoEnd, StandardBasicTypes.STRING);
			query.setParameter("productTypeId", productType==null ? null : productType.getProductTypeId(), StandardBasicTypes.STRING);
			
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
	public List<String> getProducts(String custNo, String cardHolderName) {
		this.getActiveDBTransaction();
		Session session = this.currentSession();
		try{
			cardHolderName = (cardHolderName!=null&&cardHolderName.length()>0)?(cardHolderName.toUpperCase()+"%"):cardHolderName;
			Query query = session.getNamedQuery("getDistinctCardHolderName");
			query.setParameter("custNo", custNo, StandardBasicTypes.STRING);
			query.setParameter("name", cardHolderName, StandardBasicTypes.STRING);
			return query.list();
		}
		catch(Exception e){
			e.printStackTrace();
		}
		finally{
			session.close();
		}

		return new ArrayList();
	}
	
	
	@SuppressWarnings("unchecked")
	public List<String> getDistinctNameOnCards(String custNo, String nameOnCard) {
		this.getActiveDBTransaction();
		Session session = this.currentSession();
		try{
			
			if(nameOnCard!=null){
				nameOnCard = "%" + nameOnCard.toUpperCase()+"%";
			}
			Query query = session.getNamedQuery("getDistinctNameOnCards");
			query.setParameter("custNo", custNo, StandardBasicTypes.STRING);
			query.setParameter("name", nameOnCard, StandardBasicTypes.STRING);
			return query.list();
		}
		catch(Exception e){
			e.printStackTrace();
		}
		finally{
			session.close();
		}

		return new ArrayList();
	}
	
	public boolean hasFutureTermination(Collection<BigDecimal> productNos){
		DetachedCriteria productCriteria = DetachedCriteria.forClass(PmtbProduct.class);
		productCriteria.add(Restrictions.in("productNo", productNos));
		DetachedCriteria statusCriteria = productCriteria.createCriteria("pmtbProductStatuses", org.hibernate.sql.JoinType.LEFT_OUTER_JOIN);
		statusCriteria.add(Restrictions.eq("statusTo", NonConfigurableConstants.PRODUCT_STATUS_TERMINATED));
		if(this.findAllByCriteria(productCriteria).isEmpty()){
			return false;
		}else{
			return true;
		}
	}
	public boolean hasTerminated(Collection<BigDecimal> productNos){
		DetachedCriteria productCriteria = DetachedCriteria.forClass(PmtbProduct.class);
		productCriteria.add(Restrictions.in("productNo", productNos));
		productCriteria.add(Restrictions.or(Restrictions.eq("currentStatus", NonConfigurableConstants.PRODUCT_STATUS_RECYCLED), 
				Restrictions.or(
				Restrictions.eq("currentStatus", NonConfigurableConstants.PRODUCT_STATUS_TERMINATED),
				Restrictions.eq("currentStatus", NonConfigurableConstants.PRODUCT_STATUS_USED)
			))
		);
		if(this.findAllByCriteria(productCriteria).isEmpty()){
			return false;
		}else{
			return true;
		}
	}

	public String retrieveMaxCardNo(String binRange, String subBinRange, Integer numberOfDigit){
		
		DetachedCriteria dc = DetachedCriteria.forClass(PmtbProduct.class);
		
		logger.debug("Retriving Max Card No by Card Type");
		logger.debug("binRange: " + binRange + " subBinRange: " + subBinRange + " numberOfDigit: " + numberOfDigit);
		if(binRange==null){
			binRange = "";
		}
		if(subBinRange==null){
			subBinRange = "";
		}
		
		dc.setProjection(Projections.max("cardNo"));
		
		dc.add(Restrictions.ilike("cardNo", binRange + subBinRange ,MatchMode.START));
		dc.add(Restrictions.sqlRestriction("length(card_no) = " + numberOfDigit));
		
		return firstResult(dc);
	}
	
	public PmtbCardNoSequence getCardNoSequence(String binRange, String subBinRange, Integer numberOfDigit){
		
		DetachedCriteria dc = DetachedCriteria.forClass(PmtbCardNoSequence.class);
		dc.add(Restrictions.eq("binRange", binRange));
		dc.add(Restrictions.eq("subBinRange", subBinRange));
		dc.add(Restrictions.eq("numberOfDigit", numberOfDigit));
	
		return firstResult(dc);
		
		
	}
	
	
	@SuppressWarnings("rawtypes")
	public boolean containPrepaidProducts(Set<BigDecimal> productIdSet){

		DetachedCriteria dc = DetachedCriteria.forClass(PmtbProduct.class);
		DetachedCriteria typeDc = dc.createCriteria("pmtbProductType", org.hibernate.sql.JoinType.LEFT_OUTER_JOIN);
		typeDc.add(Restrictions.eq("prepaid",  NonConfigurableConstants.BOOLEAN_YES));
		
		List results = findAllByCriteria(dc);
		if(!results.isEmpty()){
			return true;
		}
		
		return false;
	}
	
	public List<PmtbProductReplacement> getReplacedCardProducts(String cardNo){
		
		DetachedCriteria replaceCriteria = DetachedCriteria.forClass(PmtbProductReplacement.class);
		DetachedCriteria productCriteria = replaceCriteria.createCriteria("pmtbProduct", DetachedCriteria.INNER_JOIN);
		replaceCriteria.add(Restrictions.eq("currentCardNo", cardNo));
		replaceCriteria.add(Restrictions.neProperty("currentCardNo", "newCardNo"));
		return this.findAllByCriteria(replaceCriteria);
	}
	
	public PmtbProduct getNewCardProduct(String cardNo, Integer accountNo, BigDecimal productNo) {
		
		DetachedCriteria productCriteria = DetachedCriteria.forClass(PmtbProduct.class);
		DetachedCriteria accountCriteria = productCriteria.createCriteria("amtbAccount", "amtbAccount", DetachedCriteria.INNER_JOIN);
		productCriteria.add(Restrictions.eq("cardNo", cardNo));
		productCriteria.add(Restrictions.eq("amtbAccount.accountNo", accountNo));
		productCriteria.add(Restrictions.eq("productNo", productNo));
		
		List<PmtbProduct> prodList = this.findAllByCriteria(productCriteria);
		if(!prodList.isEmpty())
			return (PmtbProduct) prodList.get(0);
		else
			return null;
	}

	public List<IttbCpCustCardIssuance> checkAssignCardDate(Date assignDate, String cardNo, BigDecimal productNo) {
		boolean result = true;
		
		DetachedCriteria dc = DetachedCriteria.forClass(IttbCpCustCardIssuance.class);
		dc.add(Restrictions.eq("cardNo", cardNo));
		dc.add(Restrictions.eq("productNo", productNo));
		dc.add(Restrictions.le("issuedOn", assignDate));
		dc.add(Restrictions.ge("returnedOn", assignDate));
		
		return this.findAllByCriteria(dc);
	}
	public List<IttbCpCustCardIssuance> getPreviousAssignCard(IttbCpCustCardIssuance iccci, Date assignDate) {
		
		DetachedCriteria dc = DetachedCriteria.forClass(IttbCpCustCardIssuance.class);
		dc.add(Restrictions.eq("cardNo", iccci.getCardNo()));
		dc.add(Restrictions.eq("productNo", iccci.getProductNo()));
		dc.add(Restrictions.le("issuedOn", assignDate));
		dc.add(Restrictions.isNull("returnedOn"));
		dc.addOrder(Order.desc("issuedOn"));
		return this.findAllByCriteria(dc);
	}
	public List<IttbCpCustCardIssuance> getFutureAssignCard(IttbCpCustCardIssuance iccci, Date assignDate) {
		
		DetachedCriteria dc = DetachedCriteria.forClass(IttbCpCustCardIssuance.class);
		dc.add(Restrictions.eq("cardNo", iccci.getCardNo()));
		dc.add(Restrictions.eq("productNo", iccci.getProductNo()));
		dc.add(Restrictions.gt("issuedOn", assignDate));
		dc.addOrder(Order.asc("issuedOn"));
		return this.findAllByCriteria(dc);
	}
	public List<IttbCpCustCardIssuance> deletePreviousAssignCard(IttbCpCustCardIssuance iccci, Date assignDate) {
		
		DetachedCriteria dc = DetachedCriteria.forClass(IttbCpCustCardIssuance.class);
		dc.add(Restrictions.eq("cardNo", iccci.getCardNo()));
		dc.add(Restrictions.le("issuedOn", assignDate));
		dc.addOrder(Order.desc("issuedOn"));
		dc.addOrder(Order.desc("updatedDt"));
		return this.findAllByCriteria(dc);
	}
	public List<IttbCpCustCardIssuance> deleteFutureAssignCard(IttbCpCustCardIssuance iccci, Date assignDate) {
		
		DetachedCriteria dc = DetachedCriteria.forClass(IttbCpCustCardIssuance.class);
		dc.add(Restrictions.eq("cardNo", iccci.getCardNo()));
		dc.add(Restrictions.ge("issuedOn", assignDate));
		dc.addOrder(Order.asc("issuedOn"));
		dc.addOrder(Order.asc("updatedDt"));
		return this.findAllByCriteria(dc);
	}
	public List<IttbCpCustCardIssuance> getAssignCard(IttbCpCustCardIssuance iccci, Date assignDate) {
		
		DetachedCriteria dc = DetachedCriteria.forClass(IttbCpCustCardIssuance.class);
		dc.add(Restrictions.eq("cardNo", iccci.getCardNo()));
		dc.add(Restrictions.eq("issuedOn", assignDate));
		return this.findAllByCriteria(dc);
	}
	public List<IttbCpCustCardIssuance> checkAssignCardExistIttbRecord(IttbCpCustCardIssuance iccci) {
		
		DetachedCriteria dc = DetachedCriteria.forClass(IttbCpCustCardIssuance.class);
		dc.add(Restrictions.eq("cardNo", iccci.getCardNo()));
		dc.add(Restrictions.eq("productNo", iccci.getProductNo()));
		return this.findAllByCriteria(dc);
	}
	public IttbCpCustCardIssuance getNearestAssignCard(IttbCpCustCardIssuance iccci, Date assignDate) {
		
		DetachedCriteria dc = DetachedCriteria.forClass(IttbCpCustCardIssuance.class);
		dc.add(Restrictions.eq("cardNo", iccci.getCardNo()));
		dc.add(Restrictions.eq("productNo", iccci.getProductNo()));
		dc.add(Restrictions.le("issuedOn", assignDate));
		dc.addOrder(Order.desc("issuedOn"));
		dc.addOrder(Order.desc("updatedDt"));
		
		List<IttbCpCustCardIssuance> ittbListReturn = this.findAllByCriteria(dc);
		
		if(!ittbListReturn.isEmpty())
			return (IttbCpCustCardIssuance) ittbListReturn.get(0);
		else
			return null;
	}
	public IttbCpLogin getIttbCpLogin(BigDecimal productNo)
	{
		DetachedCriteria dc = DetachedCriteria.forClass(IttbCpLogin.class);
		DetachedCriteria accountCriteria = dc.createCriteria("pmtbProduct", "pmtbProduct", DetachedCriteria.INNER_JOIN);
		dc.add(Restrictions.eq("pmtbProduct.productNo", productNo));
		
		List<IttbCpLogin> ittbListReturn = this.findAllByCriteria(dc);
		if(!ittbListReturn.isEmpty())
			return (IttbCpLogin) ittbListReturn.get(0);
		else
			return null;
	}
	public IttbCpLoginNew getIttbCpLoginNew(BigDecimal productNo)
	{
		DetachedCriteria dc = DetachedCriteria.forClass(IttbCpLoginNew.class);
		DetachedCriteria accountCriteria = dc.createCriteria("pmtbProduct", "pmtbProduct", DetachedCriteria.INNER_JOIN);
		dc.add(Restrictions.eq("pmtbProduct.productNo", productNo));
		
		List<IttbCpLoginNew> ittbListReturn = this.findAllByCriteria(dc);
		if(!ittbListReturn.isEmpty())
			return (IttbCpLoginNew) ittbListReturn.get(0);
		else
			return null;
	}

	public String getMasterIndustry() {

		DetachedCriteria mm = DetachedCriteria.forClass(MstbMasterTable.class);
		mm.add(Restrictions.eq("masterType", "EINOB"));
		mm.add(Restrictions.eq("masterStatus", "A"));
		
		List<MstbMasterTable> mmList = this.findAllByCriteria(mm);
		
		if(!mmList.isEmpty())
			return (String) mmList.get(0).getMasterValue();
		else
			return null;
	}
	
	//one of them will be null
	public String getIndustry(AmtbAccount acct) {

		DetachedCriteria accountCriteria = DetachedCriteria.forClass(AmtbAccount.class);
			
		DetachedCriteria corpCriteria = accountCriteria.createCriteria("amtbCorporateDetails", org.hibernate.sql.JoinType.LEFT_OUTER_JOIN);
		corpCriteria.createCriteria("mstbMasterTableByIndustry", org.hibernate.sql.JoinType.LEFT_OUTER_JOIN);
		
		accountCriteria.add(Restrictions.eq("custNo", acct.getCustNo()));
			
		List<AmtbAccount> acctList = this.findAllByCriteria(accountCriteria);
			
		if(!acctList.isEmpty())
		{
			AmtbAccount acct2 = (AmtbAccount) acctList.get(0);
			
			if(acct2.getAmtbCorporateDetails() != null)
			{
				AmtbCorporateDetail corp = acct2.getAmtbCorporateDetails().iterator().next();
				
				return corp.getMstbMasterTableByIndustry().getMasterValue();
			}
		}
		return null;
	}
	
	public IttbRecurringChargeTagCard getProductsRecurringTokenByProduct(PmtbProduct product) {
		DetachedCriteria productCriteria = DetachedCriteria.forClass(IttbRecurringChargeTagCard.class);
		productCriteria.createCriteria("pmtbProduct", "prod", Criteria.LEFT_JOIN);
		productCriteria.createCriteria("recurringChargeId", "token", Criteria.LEFT_JOIN);
		productCriteria.add(Restrictions.eq("prod.productNo", product.getProductNo()));
		productCriteria.add(Restrictions.gt("token.tokenExpiry", new java.util.Date()));

		// Due to Left Join, Need to do Distinct
		productCriteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);

		List results = this.findAllByCriteria(productCriteria);
		if(results.isEmpty()) {
			return null;
		} else {
			return (IttbRecurringChargeTagCard) results.get(0);
		}
	}
	public IttbRecurringDtl getRefFrontList(String refFront) {
		
		DetachedCriteria productCriteria = DetachedCriteria.forClass(IttbRecurringDtl.class);

		productCriteria.add(Restrictions.like("referenceId", refFront, MatchMode.ANYWHERE));
		
		productCriteria.addOrder(Order.desc("referenceId"));
		List results = this.findAllByCriteria(productCriteria);
		
		if(results.isEmpty()) {
			return null;
		} else {
			return (IttbRecurringDtl) results.get(0);
		}
	}
}