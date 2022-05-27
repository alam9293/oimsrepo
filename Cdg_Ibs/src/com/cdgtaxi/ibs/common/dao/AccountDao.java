package com.cdgtaxi.ibs.common.dao;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import com.cdgtaxi.ibs.acl.model.SatbUser;
import com.cdgtaxi.ibs.common.model.AmtbAccount;
import com.cdgtaxi.ibs.common.model.AmtbAcctCredLimit;
import com.cdgtaxi.ibs.common.model.AmtbAcctCredTerm;
import com.cdgtaxi.ibs.common.model.AmtbAcctMainContact;
import com.cdgtaxi.ibs.common.model.AmtbAcctSalesperson;
import com.cdgtaxi.ibs.common.model.AmtbAcctSalespersonReq;
import com.cdgtaxi.ibs.common.model.AmtbAcctStatus;
import com.cdgtaxi.ibs.common.model.AmtbBillReq;
import com.cdgtaxi.ibs.common.model.AmtbContactPerson;
import com.cdgtaxi.ibs.common.model.AmtbCorporateDetail;
import com.cdgtaxi.ibs.common.model.AmtbCredRevReq;
import com.cdgtaxi.ibs.common.model.AmtbPersonalDetail;
import com.cdgtaxi.ibs.common.model.AmtbSubscProdReq;
import com.cdgtaxi.ibs.common.model.AmtbSubscTo;
import com.cdgtaxi.ibs.common.model.FmtbArContCodeMaster;
import com.cdgtaxi.ibs.common.model.IttbRecurringCharge;
import com.cdgtaxi.ibs.common.model.IttbRecurringChargeTagAcct;
import com.cdgtaxi.ibs.common.model.IttbRecurringChargeTagCard;
import com.cdgtaxi.ibs.common.model.PmtbProductType;
import com.cdgtaxi.ibs.common.model.forms.SearchByAccountForm;
import com.cdgtaxi.ibs.master.model.MstbMasterTable;
import com.cdgtaxi.ibs.product.ui.ProductSearchCriteria;

public interface AccountDao extends GenericDao {
	public AmtbAccount getAccount(String accountNo);
	public List<IttbRecurringCharge> searchRConly(String tokenId);
	public List<IttbRecurringCharge> searchRC(ProductSearchCriteria productSearchCriteria);
	public List<IttbRecurringCharge> searchRC2(ProductSearchCriteria productSearchCriteria);
	public List<IttbRecurringChargeTagAcct> getRecurringChargeTagAcct(String tokenId);
	public List<IttbRecurringChargeTagCard> getRecurringChargeTagCard(String tokenId);
	public AmtbAccount getAccountByCustNoAndCode(String custNo, String code, String level);
	public AmtbAccount getAccountByCustNoAndCodeAndCode(String custNo, String code, String level, String code2);
	public AmtbAccount getAccountWithParentandContact(String accountNo);
	public AmtbAccount getAccountWithParent(String accountNo);
	public List<AmtbAccount> getAccountNoAndName(String accNo,String name);
	public List<AmtbAccount> getDivOrSubApplInfo (String accNo);
	public List<AmtbAccount> getDivOrSubApplInfo2 (String accNo);
	public List<AmtbAccount> getDepartmentInfo (String accNo);
	public List<AmtbAccount> getDepartmentInfoByDivisionAcctNo (String accNo);
	
	public AmtbAccount getAccountInfo(String accNo);
	/**
	 * get a list of accounts that match the custNo, acctName and status
	 * @param custNo
	 * @param acctName
	 * @param acctStatus
	 * @param contactPerson
	 * @return
	 */
	public List<AmtbAccount> getAccounts(String custNo, String acctName, String acctStatus, String contactPerson);
	/**
	 * get a list of accounts that match the parent customer number, account name, code,
	 * main billing contact name, main billing contact tel
	 * @param custNo
	 * @param acctName
	 * @param acctCode
	 * @param contactPerson
	 * @param contactPersonTel
	 * @return
	 * @throws IllegalArgumentException when parentCustNo is empty
	 */
	public List<AmtbAccount> getAccounts(String custNo, String acctName, String acctCode, String contactPerson, String contactPersonTel, int level, String parentName);
	/**
	 * retrieve an account according to a customer number
	 * @param custNo
	 * @return
	 */
	public AmtbAccount retrieveAccount(String custNo);
	/**
	 * checks whether RCB Registration number is in used already
	 * @param rcbNo - the registration number
	 * @return true if number exist, false if not exist
	 */
	
	public List<AmtbAccount> searchAccts(String custNo, String acctName, String acctCode, String contactPerson, String contactPersonTel, int level, String parentName, String acctStatus);
	
	public boolean checkRCBNo(String rcbNo);
	/**
	 * get a list of contact persons with the matching paramaters
	 * @param custNo - cust number
	 * @param mainContactName - the main contact name (match anywhere)
	 * @param mainContactEmail - the main contact email (match anywhere)
	 * @param mainContactTel - the main contact tel (match anywhere)
	 * @param mainContactMobile - the main contact mobile (match anywhere)
	 * @return a list?
	 */
	public List<AmtbContactPerson> getContacts(String custNo, String mainContactName, String mainContactEmail, String mainContactTel, String mainContactMobile);
	public AmtbAcctMainContact getMainBillingContact(AmtbAccount amtbAccount);
	/**
	 * get the corporate details with the account
	 * @param acct
	 * @return
	 */
	
	public AmtbCorporateDetail getCorporateDetail(AmtbAccount acct);
	/**
	 * get the personal details with the account
	 * @param acct
	 * @return
	 */
	public AmtbPersonalDetail getPersonalDetail(AmtbAccount acct);
	/**
	 * creates a new contact
	 * @param newContact
	 * @param mainContacts
	 * @param userId
	 * @return
	 */
	public boolean createContact(AmtbContactPerson newContact, List<AmtbAcctMainContact> mainContacts, String userId);
	/**
	 * gets a contact person
	 * @param contactPersonNo - the contact person id
	 * @return - the contact person
	 */
	public AmtbContactPerson getContact(Integer contactPersonNo);
	/**
	 * deletes a contact and all main contact.
	 * @param contactPersonNo - contact person id
	 * @return true if delete.
	 */
	public boolean deleteContact(Integer contactPersonNo);
	/**
	 * to check whether the contact is main contact
	 * @param contactPersonNo - contact person id
	 * @return - true if there is main contact
	 */
	public boolean isMainContact(Integer contactPersonNo);
	/**
	 * update the contact
	 * @param updatedContact
	 * @param mainContacts
	 * @param userId
	 * @return
	 */
	public boolean updateContact(AmtbContactPerson updatedContact, List<AmtbAcctMainContact> mainContacts, String userId);
	/**
	 * method to check whether credit limit is greater than the parent
	 * @param custNo - customer number
	 * @param parentCode - parent code
	 * @param creditLimit - the new credit limit
	 * @return true if credit limit greater than parent credit limit.
	 */
	public boolean checkCreditLimit(String custNo, String parentCode, BigDecimal creditLimit);
	public boolean checkChildrenCreditLimit(String custNo, String code, BigDecimal creditLimit);
	/**
	 * to create account
	 * @param newAcct
	 * @param mainContacts
	 * @param userId
	 * @return
	 */
	public boolean createAccount(AmtbAccount newAcct, List<AmtbAcctMainContact> mainContacts, String userId);
	/**
	 * to get the account with the match customer number, parent code, and self code
	 * if parentCode == null and code != null, will search for division
	 * if parentCode == null and code == null, will search for corporate/personal
	 * if parentCode != null and code != null, will search for department
	 * throws exception when parentCode !=null && code == null
	 * @param custNo - throws exception when empty. Exact Match
	 * @param level - denote the level of depth.
	 * @param parentCode - denote the parent's code.
	 * @param code - if null, will not search self code. Exact Match
	 * @return
	 * @throws IllegalArgumentException
	 */
	public AmtbAccount getAccount(String custNo, int level, String parentCode, String code) throws IllegalArgumentException;
	/**
	 * update the account with main contact, and subscriptions
	 * @param updatedAcct - the updated account
	 * @param mainContacts - the list of main contacts
	 * @param subscribedTos - the list of subscriptions
	 * @param userName - the username who updated it
	 * @return true if save successful
	 */
	public boolean updateAccount(AmtbAccount updatedAcct, List<AmtbAcctMainContact> mainContacts, List<String> subscribedTos, String userName);
	/**
	 * get account with account type, information source, ar control code, main contact and subscription
	 * that match the accountNo
	 * @param accountNo - the account no, not cust no
	 * @return - the matching account
	 */
	public AmtbAccount getAccount(Integer accountNo);
	public AmtbAccount getAccountsByCustNo(String custNo);
	public AmtbAccount getRawAccount(String custNo, String divCode, String deptCode, String type);
	/**
	 * gets account with the billing details, credit term, early late payments and bank information (Current and Future dates only)
	 * @param custNo the customer number
	 * @return the matching account
	 */
	public AmtbAccount getBillingDetails(String custNo);
	public AmtbAccount getCurrentAndFutureBillingDetails(String custNo);
	/**
	 * gets account with the billing details, credit term, early late payments and bank information (Past dates only)
	 * @param custNo the customer number
	 * @return the matching account
	 */
	public AmtbAccount getBillingCycleHistoricalDetails(String custNo);
	public AmtbAccount getCreditTermHistoricalDetails(String custNo);
	public AmtbAccount getEarlyPaymentHistoricalDetails(String custNo);
	public AmtbAccount getLatePaymentHistoricalDetails(String custNo);
	public AmtbAccount getPromotionHistoricalDetails(String custNo);
	
	/**
	 * gets account with the rewards details
	 * @param custNo the customer number
	 * @return the matching account
	 */
	public AmtbAccount getRewardsDetails(String custNo);
	/**
	 * gets account with deposits
	 * @param custNo the customer number
	 * @return the matching account
	 */
	public AmtbAccount getDepositDetails(String custNo);
	public AmtbAccount getParent(AmtbAccount account);
	//public AmtbAccountMainContact getMainShippingContact(String accNo);

	/**
	 * Get Top Level billable account with given customerNo or Name.
	 * The comparison with customerNo is using "like".
	 * @param customerNo
	 * @return A list of billable accounts
	 */
	public List<AmtbAccount> getBilliableAccountOnlyTopLevel(String customerNo, String name);
	public List<AmtbAccount> getBilliableAccount(String customerNo, String name, String code);
	public List<AmtbAccount> getAccounts(String custNo, String parentCode, int level);
	public List<AmtbAcctMainContact> getMainContacts(Integer acctNo);
	public List<AmtbAccount> getBilliableAccountByParentAccount(AmtbAccount parentAccount);
	public List<AmtbAccount> getBilliableAccountByGrandParentAccount(AmtbAccount grandParentAccount);
	public List<AmtbAccount> getPersAccounts(String parentCustNo, String acctName, String nric, String email);


	public List<AmtbAccount> getChildrenAccountsWithSubscriptionsAndMainContacts(Integer parentAccountNo);
	public List<AmtbAccount> getChildrenAccountsWithSubscriptions(Integer parentAccountNo);
	/**
	 * This method should be only used by PENDING ACTIVATION accounts. DO NOT use for active accounts
	 * @param accountNo
	 */
	public void deleteAccount(Integer accountNo, String userId);
	public boolean hasPendingBillingChangeRequest(String custNo);
	public List<AmtbAccount> getChildrenAccountsWithStatuses(Integer parentNo);
	public List<AmtbAcctStatus> getStatuses(Integer accountNo);
	public AmtbAccount getAccountWithCreditDetails(String custNo, int level, String parentCode, String code);
	public AmtbAccount getAccount(String cardNo, Timestamp tripDt);
	public AmtbAccount getAccountForDownload(String cardNo, Timestamp tripDt);
	public List<AmtbBillReq> getPendingBillingRequests();
	public AmtbBillReq getBillingRequest(Integer requestId);
	public List<AmtbAccount> getPremierAccount(String accNo,String name, List<PmtbProductType> productTypes);
	public List<AmtbAccount> getPremierAccounts(AmtbAccount amtbAccount, String productTypeId);
	public List<AmtbAccount> getPremierAccountsAcct(AmtbAccount amtbAccount, List<PmtbProductType> productTypeId);
	public List<AmtbAccount> getAccounts(AmtbAccount amtbAccount);
	public List<AmtbAccount> getAccounts (AmtbAccount amtbAccount, String productTypeId);
	public List<AmtbAccount> getAccounts(String accNo,String name);
	public AmtbAccount getAccountByCustNo(String custNo);
	public AmtbAccount getAllAccountByCustNo(String custno);
	public List<AmtbAcctCredLimit> getListCreditLimits(Integer accountNo);
	public AmtbAcctCredLimit getNearestCreditLimits(Integer accountNo, Date toDate);
	//public List<AmtbAccount> getAccountsByCustNo(ProductSearchCriteria productSearchCriteria);
	public AmtbAccount getAccountsByCustNo(ProductSearchCriteria productSearchCriteria);
	public List<AmtbAccount> getAllAccounts();
	public List<AmtbAccount> getAllAccountsbyParentID(Integer parentAccountId) ;
	//public List<AmtbAccount> getAllAccounts(String custNo);
	public List<AmtbCredRevReq> getPendingCreditReviewRequests();
	public AmtbCredRevReq getPendingCreditReviewRequest(Integer requestId);
	public List<AmtbAcctCredLimit> getCreditLimits(Integer accountNo);
	public List<AmtbAcctCredLimit> getChildCreditLimits(Integer accountNo);
	public List<AmtbAcctCredLimit> getChildChildCreditLimits(Integer accountNo);
	public BigDecimal getActivePermCreditLimit(Integer accountNo);
	public List<AmtbAccount> getAccountsBySalesperson(Integer salespersonNo, String industryCode);
	public boolean hasFutureTransferAcct(Collection<Integer> accountNos);
	public void transferAcct(Integer fromSalespersonNo, Integer toSalespersonNo, Date effectiveDate, Collection<Integer> accountNos, SatbUser user);
	public List<AmtbAcctSalespersonReq> getAllTransferAcctReqs();
	public AmtbAcctSalespersonReq getTransferAcctReq(Integer requestNo);
	public void deleteTransferAcctReq(Integer requestNo);
	public AmtbAccount getAccountWithCreditDetails(Integer accountNo);
	public List<AmtbAcctStatus> getAccountStatus(Integer accountNo);
	public List<AmtbBillReq> getBillingRequest(String custNo);
	public List<AmtbBillReq> getBillingRequest(String custNo, String acctName, Date from, Date to, String status, String requester);
	public List<AmtbCredRevReq> getCreditReviewRequest(String custNo, String acctName, Date from, Date to, String status, String requester);
	public AmtbCredRevReq getCreditReviewRequest(Integer requestNo);
	public List<AmtbAccount> getAccounts(Collection accountNos);
	public boolean checkCorporateName(String name);
	public boolean hasPermCreditLimit(Integer acctNo, Date effectiveDate);
	public AmtbAccount getAccountMainContacts(String custNo);
	public AmtbAcctStatus getAccountLatestStatus(String accountNo);
	public List<AmtbAcctStatus> getAccountStatusAll(String accountNo,String custName);
	public AmtbCorporateDetail getCorporateDetail(String custNo);
	public List<AmtbAccount> getAccounts(SearchByAccountForm form);
	public AmtbContactPerson getMainContactByType(Integer accountNo,
			String contactType);
	public BigDecimal getLatestLatePaymentRate(AmtbAccount acct,
			Date invoiceDate);
	/**
	 * To retrieve the top level customer no with given account no.
	 * The account no given can be at any level.
	 * @param accountNo Regardless of which account category's account no
	 * @return Customer No
	 */
	public String getCustomerNo(Integer accountNo);
	public AmtbSubscTo getProductSubscription(String custNo, String productTypeId);
	public List<AmtbAccount> getAllAccountsByParentId(String accountNo,String category);
	public AmtbAccount getAccountbyID(String acctLv1, String acctLv2, String acctLv3);
	public AmtbAccount getAccountWithEntity(AmtbAccount amtbAccount);
	public boolean hasIssuedProducts(String custNo, String code, String parentCode, int level, List<String> productTypes);
	public List<AmtbAccount> getTopLevelAccounts(String custNo, String custName);
	public List<AmtbAccount>findAccdtlbyaccNoandName(String custNo, String custName);
	public List<FmtbArContCodeMaster> getArContCode(Integer entityNo);
	public boolean isAccountMainBilling(Integer contactNo);
	public boolean isAccountMainShipping(Integer contactNo);
	public AmtbContactPerson getAccountMainBilling(String custNo);
	public AmtbContactPerson getAccountMainShipping(String custNo);
	
	public AmtbAccount getAccountByCustomerId(String custId);
	public List<AmtbAccount> getAccountSubscribedToExternalCard(String productTypeId);
	public List<AmtbAccount> getAccountSubscribedToExternalCardWithContacts(String productTypeId);
	public List<AmtbAccount> getBilliableAccountOnlyTopLevelWithEffectiveEntity(String customerNo, String name);
	public boolean checkPromotionOverlapping(String custNo, Integer promotionNo, Date fromDate, Date toDate);
	public List<AmtbAccount> getAccountsByMainContactPerson(String contactPersonNo, String contactPersonType);
	public List<AmtbAccount> getChildAccountSubscribedToByTopLevelAccountNoAndProductTypeId(String custNo, String productTypeId);

	public List<AmtbContactPerson> getTransferContacts(String custNo, String contactPersonType);
	
	/**
	 * Retrieve division or department accounts that subscribed to Govt eInvoice that belongs to a particular
	 * top level account
	 * 
	 * @param topLevelAccount
	 * @return
	 */
	public List<AmtbAccount> getGovtEInvChildrenAccounts(AmtbAccount topLevelAccount);
	
	public List<AmtbAccount> getPubbsChildrenAccounts(AmtbAccount topLevelAccount);
	
	public List<AmtbAcctCredTerm> getCreditTerms(Integer accountNo);
	
	public AmtbSubscTo getSubscribeTo(AmtbAccount account, PmtbProductType productType, Date runDate);
	
	public List<AmtbAccount> getTopLevelAccountsWithEntity(String custNo,String name);
	
	public List<AmtbAccount> getChargeToParentDivisionAccounts(AmtbAccount topAcct);
		
	
	public List<AmtbAccount> getChargeToParentDepartmentAccounts(AmtbAccount divAcct);
	public List<MstbMasterTable> getMstbtable_EMFSRA();
	
	public AmtbAcctSalesperson getAccountMainSalesPerson(String custNo);
		
	public AmtbSubscProdReq getPendingSubscriptionRequest(Integer requestId);
	public List<AmtbSubscProdReq> getSubscriptionRequest(String custNo, String acctName, Date from, Date to, String status, String requester, String action);
	public List<AmtbSubscProdReq> getAccountPendApproveSubscription(Integer acctNo, String productTypeId);
	public List<AmtbSubscProdReq> getPendingSubscriptionRequests();
	public AmtbSubscTo getUnsubscribePlans(Integer acctNo, String productTypeId);
	public void updateOutsourcePrintFlag(AmtbAccount acct);
	public void updateCniiAcctSyncProcedure(String account_id, String account_code, String account_name, String parent_id, Date terminate_dt, Date susp_dt_start, Date susp_dt_end, String updated_by) throws Exception;
	public List<AmtbAccount> getAllSubAccountsByParentAccount(String custNo);
	
	public List<AmtbAccount> getAccountRecurringByChargeDay(Integer day);

	public IttbRecurringChargeTagAcct getAcctRecurringTokenByAccount(AmtbAccount acct);
}