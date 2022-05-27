package com.cdgtaxi.ibs.common.dao;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.cdgtaxi.ibs.common.model.AmtbAccount;
import com.cdgtaxi.ibs.common.model.AmtbAcctStatus;
import com.cdgtaxi.ibs.common.model.AmtbContactPerson;
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
import com.cdgtaxi.ibs.master.model.MstbProdDiscDetail;
import com.cdgtaxi.ibs.product.ui.ProductSearchCriteria;

public interface ProductDao extends GenericDao {
	
	public List<PmtbProduct> getAllProduct();
	public String getMaxCardNo(PmtbProductType productType);
	public List<PmtbProduct> getProducts(ProductSearchCriteria productSearchCriteria,PmtbProductType productType);
	public List<PmtbProduct> getProducts(ProductSearchCriteria productSearchCriteria,PmtbProductType productType, HashSet<Integer> accountIdSet);
	public AmtbContactPerson getcontactPersonCountry(AmtbContactPerson AmcontactPerson);
	public List<PmtbProduct> getProductsbyIdSet(Collection<BigDecimal> productIdSet);
	public List<PmtbProduct> getProductsAssignCardbyIdSet(Collection<BigDecimal> productIdSet);
	public PmtbProduct getProduct(BigDecimal productId);
	//public PmtbProductStatus getProductStatus(PmtbProduct product,Timestamp currentDate);
	public PmtbProductStatus getProductStatus(PmtbProduct product);
	public int getCountIssuedCards(String cardnoStart,String cardnoEnd,PmtbProductType productType); 
	public boolean checkCard(String cardnoStart, PmtbProductType cardType); 
	public java.sql.Date getValidExpiryDate (HashSet<BigDecimal> productIdSet);
	public java.sql.Timestamp getValidExpiryDateTime(HashSet<BigDecimal> productIdSet);
	public List<PmtbProduct> getOtuCards(String startCardNo,String endCardNo);
	public List<PmtbProductType> getOtuCardTypes();
	public PmtbProduct getProduct(PmtbProduct product);
	public List<PmtbProductRenew> getRenewProducts(BigDecimal productId);
	public List<PmtbProductReplacement> getReplaceProducts(BigDecimal productId);
	public List<PmtbProductReplacement> getReplaceProducts(String cardNo);
	public List<PmtbProductCreditLimit> getUpdatedCreditLimtProducts(BigDecimal productId);
	public List<PmtbProductRetag> getRetagProducts(BigDecimal productId);
	public List<PmtbProduct> getRetagProducts(List <String> cardNoList );
	public PmtbProductRetag getRetagProductsByDate(BigDecimal productId, Timestamp ts);
	public List<PmtbProductStatus> getProductStatus(BigDecimal productId);
	public List<PmtbProduct> getProductsCreditLimit (HashSet<BigDecimal> productIdSet);
	public List<PmtbProduct> getProductIssuanceHistory(String accountId, String productType);
	public AmtbAcctStatus getAccountStatus(Integer accNo);
	public MstbProdDiscDetail getProductDiscount(Integer accountNo, Timestamp tripDt, String productTypeID);
	public MstbAdminFeeDetail getAdminFee(Integer accountNo, Timestamp tripDt);
	public PmtbProduct getProduct(String cardNo, Timestamp tripDt);
	public PmtbProductStatus getLatestProductStatus(String cardNo, Timestamp tripDt);
	public List<PmtbProductStatus> getFutureProductStatuses(String cardNo, Timestamp tripDt);
	public List<AmtbAcctStatus> getFutureIssuedAccountStatuses(String cardNo, Timestamp tripDt);
	public AmtbAcctStatus getCurrentIssuedAccountStatus(String cardNo, Timestamp tripDt);
	public PmtbProductStatus getEarliestProductIssuedStatus(String cardNo);
	public PmtbProduct getProduct(String cardNo);
	public PmtbProductType getProductTypebyProductId(BigDecimal productId);
	public boolean isFutureTermination (PmtbProduct product,Date effectiveDate);
	public PmtbProductStatus getFutureTermination (PmtbProduct product,Date effectiveDate);
	public boolean isFutureTerminationByRange(PmtbProduct product,Date currentDate,Date suspensionDate);
	public List<PmtbProduct> getProductsWithStatus(AmtbAccount acct);
	public boolean isDuplicateScheduleCreditLimit(String creditLimitType,Date effectiveDateFrom,BigDecimal productId);
	public PmtbProduct getProductsbyId(BigDecimal productNo);
	public List<PmtbProduct> getEmbossingProducts(String productTypeId, String cardNoStart, String cardNoEnd, Date issueStart, Date issueEnd, Date replaceStart, Date replaceEnd, Date renewStart, Date renewEnd, String sortBy, boolean isReprint);
	public boolean checkAuthorizedAccount(Integer accountNo,BigDecimal productNo);
	public PmtbProduct getProductAndAccount(PmtbProduct product);
	public boolean hasEmbossingProducts(String productTypeId, String cardNoStart, String cardNoEnd, Date issueStart, Date issueEnd, Date replaceStart, Date replaceEnd, Date renewStart, Date renewEnd);
	public List<PmtbProduct> getAllProductsbyParentID(Integer accountNo,ProductSearchCriteria productSearchCriteria, PmtbProductType productType);
	public boolean isFutureTerminationByRange(List<BigDecimal> productIds, Date currentDate, Date suspendDate);
	public boolean hasStatus(List<BigDecimal> productIds, Date statusDate);
	public List<PmtbProduct> getProducts(Collection<BigDecimal> productIds);
	public List<Object[]> getNegativeExternalProduct(String cardNoStart, String cardNoEnd, PmtbProductType productType);
	public List<String> getProducts(String custNo, String cardHolderName);
	public List<String> getDistinctNameOnCards(String custNo, String nameOnCard);
	/**
	 * to check for termination status for the products.
	 * @param productNos
	 * @return true if there are terminated status in the products.
	 */
	public boolean hasFutureTermination(Collection<BigDecimal> productNos);
	/**
	 * to check for current terminated products
	 * @param productNos
	 * @return true if one of the products is terminated
	 */
	public boolean hasTerminated(Collection<BigDecimal> productNos);
	
	public PmtbCardNoSequence getCardNoSequence(String binRange, String subBinRange, Integer numberOfDigit);
	public String retrieveMaxCardNo(String binRange, String subBinRange, Integer numberOfDigit);
	public boolean containPrepaidProducts(Set<BigDecimal> productIdSet);
	public List<PmtbProductReplacement> getReplacedCardProducts(String cardNo);
	public PmtbProduct getNewCardProduct(String cardNo, Integer accountNo, BigDecimal productNo);
	public List<IttbCpCustCardIssuance> checkAssignCardDate(Date assignDate, String cardNo, BigDecimal productNo);
	public List<IttbCpCustCardIssuance> getPreviousAssignCard(IttbCpCustCardIssuance iccci, Date assignDate);
	public List<IttbCpCustCardIssuance> getFutureAssignCard(IttbCpCustCardIssuance iccci, Date assignDate);
	public List<IttbCpCustCardIssuance> deletePreviousAssignCard(IttbCpCustCardIssuance iccci, Date assignDate);
	public List<IttbCpCustCardIssuance> deleteFutureAssignCard(IttbCpCustCardIssuance iccci, Date assignDate);
	public List<IttbCpCustCardIssuance> getAssignCard(IttbCpCustCardIssuance iccci, Date assignDate);
	public List<IttbCpCustCardIssuance> checkAssignCardExistIttbRecord(IttbCpCustCardIssuance iccci);
	public IttbCpCustCardIssuance getNearestAssignCard(IttbCpCustCardIssuance iccci, Date assignDate);
	public IttbCpLogin getIttbCpLogin(BigDecimal productNo);
	public IttbCpLoginNew getIttbCpLoginNew(BigDecimal productNo);
	public List<PmtbProductStatus> getFutureProductStatusesByProductNo(BigDecimal productNo, Timestamp tripDt);
	public PmtbProduct getProductByCard(String cardNo);
	public PmtbProductType getProductTypeByName(String name);
	public String getMasterIndustry();
	public String getIndustry(AmtbAccount acct);
	
	public IttbRecurringChargeTagCard getProductsRecurringTokenByProduct(PmtbProduct prod);
	
	public IttbRecurringDtl getRefFrontList(String refFront);
}