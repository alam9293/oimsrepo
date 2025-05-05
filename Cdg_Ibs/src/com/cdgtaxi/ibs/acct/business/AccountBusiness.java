package com.cdgtaxi.ibs.acct.business;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.cdgtaxi.ibs.common.business.GenericBusiness;
import com.cdgtaxi.ibs.common.model.AmtbAccount;
import com.cdgtaxi.ibs.common.model.AmtbAcctCredTerm;
import com.cdgtaxi.ibs.common.model.AmtbAcctStatus;
import com.cdgtaxi.ibs.common.model.AmtbContactPerson;
import com.cdgtaxi.ibs.common.model.AmtbCredRevReq;
import com.cdgtaxi.ibs.common.model.AmtbSubscTo;
import com.cdgtaxi.ibs.common.model.BmtbInvoiceHeader;
import com.cdgtaxi.ibs.common.model.IttbCniiAcctReq;
import com.cdgtaxi.ibs.common.model.IttbRecurringCharge;
import com.cdgtaxi.ibs.common.model.PmtbProductType;
import com.cdgtaxi.ibs.common.model.forms.SearchByAccountForm;
import com.cdgtaxi.ibs.interfaces.cnii.CniiInterfaceException;
import com.cdgtaxi.ibs.master.model.LrtbRewardMaster;
import com.cdgtaxi.ibs.master.model.MstbIssuanceFeeMaster;
import com.cdgtaxi.ibs.master.model.MstbProdDiscMaster;
import com.cdgtaxi.ibs.master.model.MstbSubscFeeMaster;

public interface AccountBusiness  extends GenericBusiness{
	public Map<Integer, Map<String, String>> searchAccounts(String custNo, String acctName, String acctStatus, String contactPerson);
	public Map<Integer, Map<String, String>> searchAccounts(String custNo, String acctName, String acctCode, String contactPerson, String contactPersonTel, int level, String parentName, String acctStatus);
	public Map<String, Object> searchAccount(String custNo);
	public Map<String, Object> searchAccountHeader(String custNo);
	public Map<Integer, Map<String, String>> searchContacts(String custNo, String mainContactName, String mainContactEmail, String mainContactTel, String mainContactMobile);
	public boolean createContact(Map<String, String> contactDetails);
	public Map<String, String> getContact(String contactPersonNo);
	public boolean deleteContact(String contactPersonNo);
	public boolean isMainContact(String contactPersonNo);
	public boolean updateContact(Map<String, String> contactDetails);
	/**
	 * method to get product subscription of an account
	 * @param custNo - the corporate or applicant number
	 * @param code - the sub account code. if null, will only get corporate or applicant product subscription.
	 * @return
	 */
	public Set<Map<String, String>> getProductSubscriptions(String custNo, String code);
	public boolean createCorpSubAccount(Map<String, Object> accountDetails) throws Exception;
	public boolean createPersSubAccount(Map<String, Object> accountDetails) throws Exception;
	public boolean checkParentCreditLimit(String custNo, String parentCode, BigDecimal creditLimit);
	public boolean checkChildrenCreditLimit(String custNo, String code, BigDecimal creditLimit);
	public Map<String, Object> getAccount(String custNo, int level, String parentCode, String code);
	public HashMap updateCorpSubAccount(Map<String, Object> acctDetails) throws CniiInterfaceException;
	public Map<String, List<Map<String, Object>>> getBillingDetailsNew(String custNo, boolean flag);
	public Map<String, List<Map<String, Object>>> getBillingDetails(String custNo);
	public Map<String, List<Map<String, Object>>> getTopRowOfBillingDetails(String custNo);
	public Map<String, List<Map<String, Object>>> getBillingCycleHistoricalDetails(String custNo);
	public Map<String, List<Map<String, Object>>> getCreditTermHistoricalDetails(String custNo);
	public Map<String, List<Map<String, Object>>> getEarlyPaymentHistoricalDetails(String custNo);
	public Map<String, List<Map<String, Object>>> getLatePaymentHistoricalDetails(String custNo);
	public Map<String, List<Map<String, Object>>> getPromotionHistoricalDetails(String custNo);
	public boolean updateBillingDetails(String custNo, Map<Date, Map<String, Object>> billingDetails, Map<Date, Integer> creditTerms, Map<Date, Integer> earlyPymts, Map<Date, Integer> latePymts, Set<Map<String, Object>> promotions, Map<String, Object> bankInfo, String userId);
	public List<Map<String, Map<Date, Map<String, Integer>>>> getRewardsDetails(String custNo);
	public ArrayList<HashMap<String, Object>> getRewardsSummary(String custNo);
	public boolean updateInitialRewardsPoints(String custNo, BigDecimal initialPoints, String userId);
	public boolean updateCorp(Map<String, Object> corpDetails);
	public boolean updateCorpWithCNII(Map<String, Object> corpDetails) throws CniiInterfaceException;
	public boolean updatePers(Map<String, Object> persDetails);
	public boolean updatePersWithCNII(Map<String, Object> persDetails);
	public Map<String, Map<String, String>> getDepositDetails(String custNo);
	public BmtbInvoiceHeader generateDepositInvoice(BigDecimal depositAmt, Date invoiceDate, String custNo, String userId) throws Exception;
	public String activateAcct(String custNo, String userId) throws Exception;
	public String getContactName(Integer contactPersonNo);
	public boolean checkCode(String custNo, int level, String parentCode, String code);
	public Map<String, Map<String, String>> searchPersAccounts(String parentCustNo, String acctName, String nric, String email);
	public HashMap updatePersSubAccount(Map<String, Object> accountDetails) throws CniiInterfaceException;
	public Map<String, Object> getSubPersAccount(String acctNo);
	public void deleteSubAccount(Integer accountNo, String userId);
	public boolean createBillingRequest(String custNo, Map<Date, Map<String, Object>> billingDetails, Map<Date, Integer> creditTerms, Map<Date, Integer> earlyPymts, Map<Date, Integer> latePymts, Set<Map<String, Object>> promotions, Map<String, Object> bankInfo, String userId);
	public boolean hasPendingBillingChangeRequest(String custNo);
	public boolean hasPendingPermanentCreditReview(String custNo);
	public Map<Integer, Map<String, Object>> getAccounts(String custNo, String parentCode, int level);
	public boolean hasFutureTerminate(String custNo, String parentCode, String code);
	public boolean hasFutureSuspend(String custNo, String parentCode, String code);
	public boolean hasFutureReactivate(String custNo, String parentCode, String code);
	public boolean hasFutureTerminate(String custNo, String subAcctNo);
	public boolean hasFutureSuspend(String custNo, String subAcctNo);
	public boolean hasFutureReactivate(String custNo, String subAcctNo);
	public void suspendAcct(String custNo, String parentCode, String code, Date suspendDate, Date reactivateDate, String suspendCode, String remarks, String userId, boolean isChild) throws Exception;
	public void suspendAcct(String custNo, String subAcctNo, Date suspendDate, Date reactivateDate, String suspendCode, String remarks, String userId, boolean isChild) throws Exception;
	public void reactivateAcct(String custNo, String parentCode, String code, Date reactivateDate, String reactivateCode, String remarks, String userId) throws Exception;
	public void reactivateAcct(String custNo, String subAcctNo, Date reactivateDate, String reactivateCode, String remarks, String userId) throws Exception;
	public void closeAcct(String custNo, String parentCode, String code, Date closeDate, String closeCode, String remarks, String userId) throws Exception;
	public void closeAcct(String custNo, String subAcctNo, Date closeDate, String closeCode, String remarks, String userId) throws Exception;
	public void terminateAcct(String custNo, String parentCode, String code, Date terminateDate, String terminateCode, String remarks, String userId) throws Exception;
	public void terminateAcct(String custNo, String subAcctNo, Date terminateDate, String terminateCode, String remarks, String userId) throws Exception;
	public boolean hasPastCloseTerminate(String custNo, String parentCode, String code, List<Date> dates);
	public boolean hasPastCloseTerminate(String custNo, String subAcctNo, List<Date> dates);
	public void creditReviewAcct(String custNo, String parentCode, String code, BigDecimal newCreditLimit, String type, Date effectiveDateFrom, Date effectiveDateTo, String remarks, String userId) throws Exception;
	public void creditReviewAcct(String custNo, String subAcctNo, BigDecimal newCreditLimit, String type, Date effectiveDateFrom, Date effectiveDateTo, String remarks, String userId) throws Exception;
	public boolean hasPendingCreditReview(String custNo);
//	public void unsubscribeProductType(String custNo, List<String> productTypeIds) throws CniiInterfaceException;
	public List<Map<String, Object>> getUnsubscribedProductTypes(String custNo);
	public Map<Integer, Map<String, String>> getPendingBillingChangeRequests();
	public Map<String, List<Map<String, String>>> getBillingChangeRequest(Integer requestId);
	public void approveBillingChangeRequest(Integer requestId, String remarks, String userId);
	public void rejectBillingChangeRequest(Integer requestId, String remarks, String userId);
	public Map<Integer, Map<String, String>> getPendingCreditReviewRequests();
	public Map<String, String> getPendingCreditReviewRequest(Integer requestId);
	public void approveCreditReviewRequest(List<Integer> requestIds, String remarks, String userId) throws Exception;
	public void rejectCreditReviewRequest(List<Integer> requestIds, String remarks, String userId);
	public boolean hasTempCreditLimit(String custNo, String parentCode, String code);
	public boolean hasTempChildFutureCreditLimit(String custNo, String parentCode, String code);
	public void clearFutureChildCreditLimit(String custNo, String parentCode, String code, String userId);
	public boolean hasTempCreditLimit(String custNo, String subAcctNo);
	public Map<Integer, String> getAccounts(Integer salespersonNo, String industryCode);
	public boolean hasFutureTransferAcct(Collection<Integer> accountNos);
	public void transferAccount(Integer fromSalesperson, Integer toSalesperson, Date effectiveDate, Collection<Integer> accountNos, String userId);
	public Map<Integer, Map<String, String>> getAllAcctTransferReqs();
	public Map<String, Object> getAcctTransferReq(Integer requestNo);
	public void deleteAcctTransferReq(Integer requestNo);
	public List<Map<String, String>> getCreditReviews(String custNo, String subAcctNo, String parentCode, String code);
	public List<Map<String, String>> getStatuses(String custNo, String subAcctNo, String parentCode, String code);
	public void deleteStatus(Integer statusNo);
	public Map<Integer, Map<String, String>> getAllBillingChangeRequest(String custNo);
	public Map<Integer, Map<String, String>> getBillingChangeRequests(String custNo, String acctName, Date from, Date to, String status, String requester);
	public Map<Integer, Map<String, String>> getCreditReviewRequests(String custNo, String acctName, Date from, Date to, String status, String requester);
	public Map<String, String> getCreditReviewRequest(Integer requestNo);
	public boolean hasPermCreditLimit(String custNo, String parentCode, String code, Date effectiveDate);
	public boolean hasPermCreditLimit(String custNo, String subAcctNo, Date effectiveDate);
	public Map<String, Integer> getAccountMainContact(String custNo);
	public Map<String, String> getCorporateAddress(String custNo);
	public List<AmtbAccount> searchAccount(SearchByAccountForm form);
	public AmtbContactPerson getMainContactByType(Integer accountNo,
			String mainContactTypeBilling);
	public Long generateMiscInvoice(BmtbInvoiceHeader invoice);
	public AmtbAccount getAccountWithParent(AmtbAccount account);
	public AmtbAccount getTopLevelAccount(AmtbAccount account);
	public boolean isDebtCleared(String custNo);
	public BigDecimal getDepositAmount(String custNo);
	public Long generateMemoRefundForDeposit(String custNo, BigDecimal depositAmount, String userId) throws Exception;
	public Map<String, String> getProductSubscription(String custNo, String productTypeId);
	public boolean hasIssuedProducts(String custNo, String code, String parentCode, int level, List<String> productTypes);
	public AmtbAccount getAccount(String accountNo);
	public AmtbAccount getAccountByCustNoAndCode(String custNo, String code, String level);
	public AmtbAccount getRawAccount(String custNo, String divCode, String deptCode, String type);
	public AmtbAccount getRawAccountMain(String custNo);
	public AmtbAccount getAccountByCustNoAndCodeAndCode(String custNo, String code, String level, String code2);
	public AmtbAccount getAccountByCustNo(String accountNo);
	public boolean hasExternalCardSubscription(String productTypeId);
	public void saveRecurring(IttbRecurringCharge rc);
	public AmtbAccount getAccountSubscribedToExternalCard(String productTypeId);
	public boolean checkPromotionOverlapping(String custNo, Integer promotionNo, Date fromDate, Date toDate);
	public Map<Integer, String> searchAccounts(String contactPersonNo, String contactPersonType);
	public void transferContact(String fromContactPersonNo, String toContactPersonNo, List<Integer> acctNos, String userId, String contactPersonType);
	public AmtbAcctStatus getCurrentStatus(Collection<AmtbAcctStatus> statuses);
	public List<AmtbAcctStatus> getStatuses(Integer accountNo);
	public List<AmtbContactPerson> getTransferContacts(String custNo, String contactPersonType);
	public boolean containGovtEInvoice(String custNo, Integer excludeAcctNo);
	public boolean containPubbs(String custNo, Integer excludeAcctNo);
	public boolean containFi(String custNo, Integer excludeAcctNo);

	public List<AmtbAcctCredTerm> getCreditTerms(Integer accountNo);
	public AmtbAcctCredTerm getEffectiveCreditTerm(Collection<AmtbAcctCredTerm> credTerms);
	
	public AmtbSubscTo getSubscribeTo(AmtbAccount account, PmtbProductType productType, Date runDate);
	
	public List<AmtbAccount> getTopLevelAccountsWithEntity(String custNo,String name);
	
	public AmtbAccount getAccountWithEntity(AmtbAccount amtbAccount);
	
	public List<AmtbAccount> getActiveChargeToParentDivisionAccounts(AmtbAccount topAcct);
	
	public List<AmtbAccount> getActiveChargeToParentDepartmentAccounts(AmtbAccount divAcct);
	
	public AmtbAccount getAccountWithParent(String accountNo);
	
	public void sendEmail(String custNo, String parentCode, String Code, String subAcctNo, String suspendActivate, String errorMessage);
	
	public void updateInvoiceOverdue(String custNo, String parentCode, String code, String wholeEmail, String overdueInvoiceReminder, String userId) throws Exception;
	public void updateInvoiceOverdue(String custNo, String subAcctNo, String wholeEmail, String overdueInvoiceReminder, String userId) throws Exception;

	public boolean hasPendApproveSubscription(String custNo, String productTypeId);
	public boolean hasPendApproveSubscriptionCorpSubAccount(Map<String, Object> acctDetails);
	public void unsubscribeProductType(AmtbAccount acct, PmtbProductType pmtbProductType, AmtbSubscTo subscription) throws CniiInterfaceException;
	public void unsubscribeProductTypeApproval(String custNo, List<String> productTypeIds);
	public void addProductSubscription(AmtbAccount acct, PmtbProductType productType, MstbProdDiscMaster productDiscount, LrtbRewardMaster reward, MstbSubscFeeMaster subscription, MstbIssuanceFeeMaster issuance, Timestamp effectiveDt) throws CniiInterfaceException;
	public void addProductSubscriptionApproval(String custNo, String productTypeId, Integer productDiscountId, Integer rewardId, Integer subscriptionId, Integer issuanceId);
	public void updateProductSubscription(String custNo, String productTypeId, MstbProdDiscMaster productDiscount, LrtbRewardMaster reward, MstbSubscFeeMaster subfee, MstbIssuanceFeeMaster issuance, AmtbSubscTo subscription);
	public void updateProductSubscriptionApproval(String custNo, String productTypeId, Integer productDiscountId, Integer rewardId, Integer subscriptionId, Integer issuanceId);
	public Map<Integer, Map<String, String>> getSubscriptionRequests(String custNo, String acctName, Date from, Date to, String status, String requester, String action);
	public Map<Integer, Map<String, String>> getPendingSubscriptionRequests();
	public Map<String, String> getPendingSubscriptionRequest(Integer requestId);
	public void approveSubscriptionRequest(List<Integer> requestIds, String remarks) throws Exception;
	public void rejectSubscriptionRequest(List<Integer> requestIds, String remarks);
	public boolean checkParentCreditLimitRange(String custNo, String parentNo, Date fromDate, Date toDate, BigDecimal limit);
	public AmtbCredRevReq getPendingCreditReviewRequestsCheck(Integer requestId);
	public AmtbAccount getFmtbArContCodeMaster(String custNo);
	public String updateCniiAcctSyncProcedure(ArrayList<IttbCniiAcctReq> list) throws Exception;
	
	public void updateEInvoiceEmail(String eInvoiceEmailFlag, String eInvoiceEmail, String eInvoiceEmailSubject, String eInvoiceEmailZipFlag, String eInvoiceEmailAttachment, String eInvoiceEmailPage, String custNo, String parentCode, String code, String userId) throws Exception;
	public void updateEInvoiceEmail(String eInvoiceEmailFlag, String eInvoiceEmail, String eInvoiceEmailSubject, String eInvoiceEmailZipFlag, String eInvoiceEmailAttachment, String eInvoiceEmailPage, String custNo, String subAcctNo, String userId) throws Exception;
	public Map<Integer, Map<String, String>> searchAllAccountsByParentAcct(String custNoString);

}