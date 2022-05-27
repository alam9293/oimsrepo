package com.cdgtaxi.ibs.product.business;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.zkoss.zul.Listitem;

import com.cdgtaxi.ibs.common.business.GenericBusiness;
import com.cdgtaxi.ibs.common.model.AmtbAccount;
import com.cdgtaxi.ibs.common.model.AmtbAcctStatus;
import com.cdgtaxi.ibs.common.model.IttbCpCustCardIssuance;
import com.cdgtaxi.ibs.common.model.IttbRecurringCharge;
import com.cdgtaxi.ibs.common.model.IttbRecurringChargeTagAcct;
import com.cdgtaxi.ibs.common.model.IttbRecurringChargeTagCard;
import com.cdgtaxi.ibs.common.model.PmtbCardNoSequence;
import com.cdgtaxi.ibs.common.model.PmtbProduct;
import com.cdgtaxi.ibs.common.model.PmtbProductCreditLimit;
import com.cdgtaxi.ibs.common.model.PmtbProductRetag;
import com.cdgtaxi.ibs.common.model.PmtbProductStatus;
import com.cdgtaxi.ibs.common.model.PmtbProductType;
import com.cdgtaxi.ibs.common.model.forms.IssueProductForm;
import com.cdgtaxi.ibs.master.model.MstbMasterTable;
import com.cdgtaxi.ibs.product.ui.ProductSearchCriteria;

public interface ProductBusiness extends GenericBusiness {

	public AmtbAccount getAccountWithParentAndChildren(String accountNo);
	public Map<String,String> getAccountNoAndName(String accNo, String name);
	public Map<String,Map<String,String>> getDivOrSubApplInfo(String accNo, String type);
	public Map<String,Map<String,String>> getDepartmentInfo(String accNo);
	public Map<String,Map<String,String>> getAccountInfo(String accountNo);	
	public List<PmtbProductType> getIssuableProductTypes(String accNo, boolean isPrepaid);
	public String getMaxCardno(PmtbProductType productType);
	public Map<String,Map<String,String>> getProducts(ProductSearchCriteria productSearchCriteria);
	public List<IttbRecurringChargeTagAcct> getRecurringChargeTagAcct(String tokenId);
	public List<IttbRecurringChargeTagCard> getRecurringChargeTagCard(String tokenId);
	public List<IttbRecurringCharge> searchRConly(String tokenId);
	public List<IttbRecurringCharge> searchRC(ProductSearchCriteria productSearchCriteria);
	public List<IttbRecurringCharge> searchRC2(ProductSearchCriteria productSearchCriteria);
	public AmtbAccount getAccount(String accNo);
	public AmtbAccount getAccountWithParent(String accNo);
	public Map<String,Map<String,String>> getProductsbyIdSet(HashSet<BigDecimal> productIdSet);
	public Map<String,Map<String,String>> getProductsbyIdSet(HashSet<BigDecimal> productIdSet, boolean forAssignCard);
	public boolean saveRetag(boolean isToday,Timestamp effectiveDate,HashSet<BigDecimal>  productIdSet,String retagRemarks,String accountNo,String previousAccountNo);
	public int getCountIssuedCards(String cardnoStart,String cardnoEnd, PmtbProductType productType);
	public boolean checkCard(String cardnoStart,PmtbProductType cardType);
	public PmtbProduct getProductById(BigDecimal id);
	public BigDecimal getProductMaxUpdatableCreditLimit(HashSet<BigDecimal>productIdSet);
	public Date getValidExpiryDate(HashSet<BigDecimal> productIdSet);
	public java.sql.Timestamp getValidExpiryDateTime(HashSet<BigDecimal> productIdSet);
	public int recycleProducts(String startCardNo,String endCardNo,String userId);
	public Map<String,String> getOtuCardTypes();
	public PmtbProductType getProductType (PmtbProduct product);
	public Map<String, Map<String, String>> getRenewProductHistory(BigDecimal productId);
	public Map<String, Map<String, String>> getCardAssignmentHistory(BigDecimal productId);
	public Map<String, Map<String, String>> getReplaceProductHistory(BigDecimal productId);
	public Map<String, Map<String, String>> getCreditLimitHistory(BigDecimal productId);
	public Map<String, Map<String, String>> getRetagHistory(BigDecimal productId);
	public Map<String, Map<String, String>> getStatusHistory(BigDecimal productId);
	public Map<String,String> getAllProductTypes();
	public PmtbProductStatus getLatestProductStatus(String cardNo, Timestamp tripDt);
	public String getAccountParentIdbyProductNo(String productId);
	public Map<String, Map<String, String>> getProductIssuanceHistory(String accountId, String productType);
	
	public List<AmtbAccount> getActiveDivOrSubApplList(String accountNo);
	public List<AmtbAccount> getActiveDepartmentList(String accountNo);
	public List<AmtbAccount> getActiveDepartmentByDivisionList(String divisionAccountNo);
	public  List<AmtbAccount>  getActiveAccountList(String accNo,String name);
	
	public Map<String,Map<String,String>> getActiveDivOrSubApplInfo(String accNo);
	public Map<String,Map<String,String>> getActiveDepartmentInfoByDivisionAcctNo(String accNo);
	public Map<String,String> getActiveAccountNoAndName(String accNo, String name);
	
	//public PmtbProductRetag getUpdateFutureRetagSchedule(PmtbProduct product,Timestamp effectiveDate);
	public PmtbProductRetag getFutureRetagSchedule(PmtbProduct product);
	public PmtbProductStatus  getFutureSuspendSchedule(PmtbProduct product);
	public PmtbProductCreditLimit getFutureCreditLimitScheduleHalfWay(PmtbProduct product);
	public PmtbProductCreditLimit getFutureCreditLimitScheduleTotallyFuture(PmtbProduct product);
	public PmtbProductStatus  getFutureReactivateSchedule(PmtbProduct product);
	public void UpdateFutureSchedules(PmtbProduct product,String logInUserId);
		
	public PmtbProductType getProductTypebyProductId(BigDecimal productId);
	public boolean isFutureTermination(PmtbProduct product,Date effectiveDate);
	public PmtbProductStatus getFutureTermination(PmtbProduct product,Date currentDate);
	public boolean isFutureTerminationByRange(PmtbProduct product,Date currentDate,Date suspensionDate);
	public boolean isFutureTerminationByRange(List<BigDecimal> productIds,Date startDate, Date endDate);
	public boolean hasStatus(List<BigDecimal> productIds, Date statusDate);
	
	public boolean isMultipleCorporate(HashSet<BigDecimal> selectedProductIdSet);
	public boolean isDuplicateScheduleCreditLimit(String creditLimitType,Date effectiveDateFrom,BigDecimal productId);
	
	public boolean isProductNameInUse(String productTypeName);
	public boolean isProductNameInUse(String productTypeName,String productTypeId);
	
	public boolean hasPendingCreditReview(PmtbProduct product);
	public boolean checkAuthorizedAccount(Integer accountNo,BigDecimal productNo);
	//public AmtbAccountStatus getCurrentStatus(Collection<AmtbAccountStatus> statuses);

	//For Viewing Account Aging
	public BigDecimal getLess30DaysTotalOutstandingAmount(AmtbAccount debtToAccount);
	public BigDecimal getLess60DaysTotalOutstandingAmount(AmtbAccount debtToAccount);
	public BigDecimal getLess90DaysTotalOutstandingAmount(AmtbAccount debtToAccount);
	public BigDecimal getMore90DaysTotalOutstandingAmount(AmtbAccount debtToAccount);
	public void updateProductAPI(PmtbProduct product,String userId,MstbMasterTable masterTable) throws Exception;
	public void updateProductAPIActive(PmtbProduct product,String userId) throws Exception;
	public void updateProductAPICardLost(PmtbProduct product,String userId,MstbMasterTable masterTable) throws Exception;
	public void createProductAPI(PmtbProduct product,String userId) throws Exception;
	public Map<String, String> createASRequest(PmtbProduct product, String userId) throws Exception;
	public List<PmtbProduct> issueProduct(IssueProductForm form) throws Exception;
	public void commonIssueProduct(PmtbProductType productType, List<PmtbProduct> productList, String userId) throws Exception;	
	public void terminateProducts(List<PmtbProduct> products,List<PmtbProductStatus> productStatuses, String userId, String terminateReason) throws Exception;
	public void updateProduct(PmtbProduct product, String userLoginId, String oldNameOnProduct) throws Exception;		
	// for suspending and activating
	public void suspendProduct(List<BigDecimal> productIds, Timestamp suspensionTimestamp, Timestamp reactivationTimestamp, MstbMasterTable reason, String remarks, String userId) throws Exception;
	public void reactivateProduct(List<BigDecimal> productIds, Timestamp reactivationTimestamp, MstbMasterTable reason, String remarks, String userId) throws Exception;
	public List<Object[]> getNegativeExternalProduct(String cardNoStart, String cardNoEnd, PmtbProductType productType);
	public List<String> getProducts(String custNo, String cardHolderName);
	public List<String> getDistinctNameOnCards(String custNo, String nameOnCard);
	public List<AmtbAcctStatus> getAccountStatus(PmtbProduct product);
	/**
	 * to check whether the products has termination status
	 * @param productNos
	 * @return true if there is termination status
	 */
	public boolean hasFutureTermination(Collection<BigDecimal> productNos);
	/**
	 * to check whether the products are terminated
	 * @param productNos
	 * @return true if there are products terminated
	 */
	public boolean hasTerminated(Collection<BigDecimal> productNos);
	/**
	 * to terminate products
	 * @param productNos
	 * @param terminateDate
	 * @param terminateRemarks
	 * @param terminateReason
	 * @param userId
	 */
	public void terminateProducts(Collection<BigDecimal> productNos, Date terminateDate, String terminateRemarks, String terminateReason, String userId);
	
	public PmtbProduct getProduct(BigDecimal productNo);
	
	public List<PmtbProduct> getProductsbyIds(Collection<BigDecimal> productIds);
	
	public PmtbProductStatus getSuspendProductStatus(PmtbProduct product);
	
	public PmtbCardNoSequence getCardNoSequence(PmtbProductType productType);
	
	public boolean containPrepaidProducts(Set<BigDecimal> productIdSet);

	public PmtbProduct replaceProduct(PmtbProduct product, PmtbProductType productType, java.sql.Date expiryDate, Timestamp expiryTime, boolean isGenerateNewCard, boolean isWaiveReplacementFee, BigDecimal fee, String replacementRemarks, String replacementReason, String userId) throws Exception;
		
	public PmtbProduct getProductAndAccount(PmtbProduct product);
	
	public List<IttbCpCustCardIssuance> checkAssignCardDate(Date assignDate, String cardNo, BigDecimal productNo);
	
	public void updateAssignCards(IttbCpCustCardIssuance iccci, Date assignDate);
	
	public boolean checkDeleteAssignCard(Listitem item);
	public void deleteAssignCard(Listitem item) throws Exception;
	public void renewVirtualEmail(PmtbProduct product, Date currentDate);

	public PmtbProduct getProductByCard(String cardNo);
	public PmtbProductType getProductTypeByName(String product);
	public boolean getCheckProductIndustry(PmtbProduct product, AmtbAccount acct);
}