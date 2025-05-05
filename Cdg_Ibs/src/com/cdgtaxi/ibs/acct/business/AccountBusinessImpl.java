package com.cdgtaxi.ibs.acct.business;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.log4j.Logger;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zkplus.spring.SpringUtil;

import com.cdgtaxi.ibs.Uri;
import com.cdgtaxi.ibs.acl.Constants;
import com.cdgtaxi.ibs.acl.model.SatbUser;
import com.cdgtaxi.ibs.common.ConfigurableConstants;
import com.cdgtaxi.ibs.common.MasterSetup;
import com.cdgtaxi.ibs.common.NonConfigurableConstants;
import com.cdgtaxi.ibs.common.business.GenericBusinessImpl;
import com.cdgtaxi.ibs.common.dao.Sequence;
import com.cdgtaxi.ibs.common.model.AmtbAccount;
import com.cdgtaxi.ibs.common.model.AmtbAcctAdminFee;
import com.cdgtaxi.ibs.common.model.AmtbAcctAdminFeeReq;
import com.cdgtaxi.ibs.common.model.AmtbAcctBillCycle;
import com.cdgtaxi.ibs.common.model.AmtbAcctBillCycleReq;
import com.cdgtaxi.ibs.common.model.AmtbAcctCredLimit;
import com.cdgtaxi.ibs.common.model.AmtbAcctCredTerm;
import com.cdgtaxi.ibs.common.model.AmtbAcctCredTermReq;
import com.cdgtaxi.ibs.common.model.AmtbAcctEarlyPymt;
import com.cdgtaxi.ibs.common.model.AmtbAcctEarlyPymtReq;
import com.cdgtaxi.ibs.common.model.AmtbAcctLatePymt;
import com.cdgtaxi.ibs.common.model.AmtbAcctLatePymtReq;
import com.cdgtaxi.ibs.common.model.AmtbAcctMainContact;
import com.cdgtaxi.ibs.common.model.AmtbAcctMainContactPK;
import com.cdgtaxi.ibs.common.model.AmtbAcctPromotion;
import com.cdgtaxi.ibs.common.model.AmtbAcctPromotionReq;
import com.cdgtaxi.ibs.common.model.AmtbAcctSalesperson;
import com.cdgtaxi.ibs.common.model.AmtbAcctSalespersonReq;
import com.cdgtaxi.ibs.common.model.AmtbAcctStatus;
import com.cdgtaxi.ibs.common.model.AmtbAcctType;
import com.cdgtaxi.ibs.common.model.AmtbAcctVolDisc;
import com.cdgtaxi.ibs.common.model.AmtbAcctVolDiscReq;
import com.cdgtaxi.ibs.common.model.AmtbBillReq;
import com.cdgtaxi.ibs.common.model.AmtbBillReqFlow;
import com.cdgtaxi.ibs.common.model.AmtbContactPerson;
import com.cdgtaxi.ibs.common.model.AmtbCorporateDetail;
import com.cdgtaxi.ibs.common.model.AmtbCredRevReq;
import com.cdgtaxi.ibs.common.model.AmtbCredRevReqFlow;
import com.cdgtaxi.ibs.common.model.AmtbPersonalDetail;
import com.cdgtaxi.ibs.common.model.AmtbSubscProdReq;
import com.cdgtaxi.ibs.common.model.AmtbSubscTo;
import com.cdgtaxi.ibs.common.model.AmtbSubscToPK;
import com.cdgtaxi.ibs.common.model.BmtbInvoiceDepositTxn;
import com.cdgtaxi.ibs.common.model.BmtbInvoiceHeader;
import com.cdgtaxi.ibs.common.model.BmtbPaymentReceipt;
import com.cdgtaxi.ibs.common.model.IttbCniiAcctReq;
import com.cdgtaxi.ibs.common.model.IttbGiroSetup;
import com.cdgtaxi.ibs.common.model.IttbRecurringCharge;
import com.cdgtaxi.ibs.common.model.LrtbRewardAccount;
import com.cdgtaxi.ibs.common.model.LrtbRewardTxn;
import com.cdgtaxi.ibs.common.model.PmtbProduct;
import com.cdgtaxi.ibs.common.model.PmtbProductStatus;
import com.cdgtaxi.ibs.common.model.PmtbProductType;
import com.cdgtaxi.ibs.common.model.forms.SearchByAccountForm;
import com.cdgtaxi.ibs.common.ui.CommonWindow;
import com.cdgtaxi.ibs.interfaces.as.api.API;
import com.cdgtaxi.ibs.interfaces.cnii.CniiInterfaceException;
import com.cdgtaxi.ibs.master.model.LrtbRewardMaster;
import com.cdgtaxi.ibs.master.model.MstbAdminFeeMaster;
import com.cdgtaxi.ibs.master.model.MstbBankMaster;
import com.cdgtaxi.ibs.master.model.MstbBranchMaster;
import com.cdgtaxi.ibs.master.model.MstbCreditTermMaster;
import com.cdgtaxi.ibs.master.model.MstbEarlyPaymentMaster;
import com.cdgtaxi.ibs.master.model.MstbIssuanceFeeMaster;
import com.cdgtaxi.ibs.master.model.MstbLatePaymentMaster;
import com.cdgtaxi.ibs.master.model.MstbMasterTable;
import com.cdgtaxi.ibs.master.model.MstbProdDiscMaster;
import com.cdgtaxi.ibs.master.model.MstbPromoDetail;
import com.cdgtaxi.ibs.master.model.MstbPromotion;
import com.cdgtaxi.ibs.master.model.MstbSubscFeeMaster;
import com.cdgtaxi.ibs.master.model.MstbVolDiscMaster;
import com.cdgtaxi.ibs.util.DateUtil;
import com.cdgtaxi.ibs.util.EmailUtil;
import com.cdgtaxi.ibs.util.LoggerUtil;
import com.cdgtaxi.ibs.util.StringUtil;
import com.google.common.base.Function;
import com.google.common.base.Predicate;
import com.google.common.base.Strings;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.google.common.collect.Ordering;


public class AccountBusinessImpl extends GenericBusinessImpl implements AccountBusiness{
	private static final Logger logger = Logger.getLogger(AccountBusinessImpl.class);
	
	public void saveRecurring(IttbRecurringCharge rc){
		//logger.info("saveCorpApplication(Map<String, Object> corpDetails)");
		this.save(rc);
	}
	
	public Map<Integer, Map<String, String>> searchAccounts(String custNo, String acctName, String acctStatus, String contactPerson){
		logger.info("searchAccounts(String custNo, String acctName, String acctCode, String acctStatus, String contactPerson, String contactPersonTel)");
		List<AmtbAccount> accts = this.daoHelper.getAccountDao().getAccounts(custNo, acctName, acctStatus, contactPerson);
		Map<Integer, Map<String, String>> returnMap = new LinkedHashMap<Integer, Map<String, String>>();
		for(AmtbAccount acct : accts){
			Map<String, String> acctDetails = new LinkedHashMap<String, String>();
			acctDetails.put("custNo", acct.getCustNo());
			acctDetails.put("acctName", acct.getAccountName());
			acctDetails.put("acctCategory", acct.getAccountCategory());
			acctDetails.put("acctNo", acct.getAccountNo().toString());
			acctDetails.put("entityName", acct.getFmtbArContCodeMaster().getFmtbEntityMaster().getEntityName());
			for (AmtbAcctMainContact mainContact : acct.getAmtbAcctMainContacts()) {
				if(mainContact.getComp_id().getMainContactType().equals(NonConfigurableConstants.MAIN_CONTACT_TYPE_BILLING)){
					if(contactPerson!=null && contactPerson.trim().length()!=0){
						acctDetails.put("contactPerson", getContactCombinedNames(mainContact.getAmtbContactPerson()));
					}
					break;
				}
			}
			AmtbAcctStatus latestStatus = null;
			for(AmtbAcctStatus tempAcctStatus : acct.getAmtbAcctStatuses()){
				if(tempAcctStatus.getEffectiveDt().before(DateUtil.getCurrentTimestamp())){
					if(latestStatus==null){
						latestStatus = tempAcctStatus;
					}else{
						if(latestStatus.getEffectiveDt().before(tempAcctStatus.getEffectiveDt())){
							latestStatus = tempAcctStatus;
						}
					}
				}
			}
			acctDetails.put("acctStatus", NonConfigurableConstants.ACCOUNT_STATUS.get(latestStatus.getAcctStatus()));
			acctDetails.put("acctTemplate", acct.getAmtbAcctType().getAcctTemplate());
			returnMap.put(acct.getAccountNo(), acctDetails);
		}
		return returnMap;
	}

	public Map<Integer, Map<String, String>> searchAllAccountsByParentAcct(String topLevelAcct){
		logger.info("searchAccounts(String custNo, String acctName, String acctCode, String acctStatus, String contactPerson, String contactPersonTel)");
		AmtbAccount mainAcct = this.daoHelper.getAccountDao().retrieveAccount(topLevelAcct);
		List<AmtbAccount> accts = this.daoHelper.getAccountDao().getAllSubAccountsByParentAccount(topLevelAcct);
		Map<Integer, Map<String, String>> returnMap = new LinkedHashMap<Integer, Map<String, String>>();
		//add in main acc
		accts.add(mainAcct);
		for(AmtbAccount acct : accts){
			
			Map<String, String> acctDetails = new LinkedHashMap<String, String>();
			acctDetails.put("mainCustNo", mainAcct.getCustNo());
			acctDetails.put("mainAcctName", mainAcct.getAccountName());
		
			if(acct.getAccountCategory().equalsIgnoreCase("APP") || acct.getAccountCategory().equalsIgnoreCase("CORP"))
			{
				acctDetails.put("div", "");
				acctDetails.put("dept", "");
			}
			if(acct.getAccountCategory().equals("SAPP")) {
				acctDetails.put("sapp", acct.getAccountName() +" ("+ acct.getCode() +")");
			}
			else if(acct.getAccountCategory().equals("DIV")) {
				acctDetails.put("div", acct.getAccountName() +" ("+ acct.getCode() +")");
				acctDetails.put("dept", "");
			}
			else if(acct.getAccountCategory().equals("DEPT")) {
				acctDetails.put("dept", acct.getAccountName() +" ("+ acct.getCode() +")");
				acctDetails.put("div", acct.getAmtbAccount().getAccountName() +" ("+ acct.getAmtbAccount().getCode() +")");
			}
			
			acctDetails.put("acctName", acct.getAccountName());
	//		acctDetails.put("acctCategory", acct.getAccountCategory());
			acctDetails.put("acctNo", acct.getAccountNo().toString());
		//	acctDetails.put("entityName", acct.getFmtbArContCodeMaster().getFmtbEntityMaster().getEntityName());
			
			returnMap.put(acct.getAccountNo(), acctDetails);
		}
		return returnMap;
	}
	public Map<Integer, Map<String, String>> searchAccounts(String custNo, String acctName, String acctCode, String contactPerson, String contactPersonTel, int level, String parentName, String acctStatus){
		logger.info("searchAccounts(String custNo, String acctName, String acctCode, String contactPerson, String contactPersonTel, int level)");
		List<AmtbAccount> accts = this.daoHelper.getAccountDao().searchAccts(custNo, acctName, acctCode, contactPerson, contactPersonTel, level, parentName, acctStatus);
		Map<Integer, Map<String, String>> returnMap = new LinkedHashMap<Integer, Map<String, String>>();
		for(AmtbAccount acct : accts){
			Map<String, String> acctDetails = new LinkedHashMap<String, String>();
			if(acct.getAmtbAccount()!=null){
				acctDetails.put("parentCode", acct.getAmtbAccount().getCode());
				acctDetails.put("parentName", acct.getAmtbAccount().getAccountName());
			}
			acctDetails.put("acctCode", acct.getCode());
			acctDetails.put("acctName", acct.getAccountName());
			acctDetails.put("creditBalance", StringUtil.bigDecimalToString(acct.getCreditBalance(), StringUtil.GLOBAL_DECIMAL_FORMAT));
			acctDetails.put("creditLimit", StringUtil.bigDecimalToString(acct.getCreditLimit(), StringUtil.GLOBAL_DECIMAL_FORMAT));
			for (AmtbAcctMainContact mainContact : acct.getAmtbAcctMainContacts()) {
				if(mainContact.getComp_id().getMainContactType().equals(NonConfigurableConstants.MAIN_CONTACT_TYPE_BILLING)){
					acctDetails.put("billContactPerson", getContactCombinedNames(mainContact.getAmtbContactPerson()));
					acctDetails.put("billContactPersonTel", mainContact.getAmtbContactPerson().getMainContactTel());
				}
				if(mainContact.getComp_id().getMainContactType().equals(NonConfigurableConstants.MAIN_CONTACT_TYPE_SHIPPING)){
					acctDetails.put("shipContactPerson", getContactCombinedNames(mainContact.getAmtbContactPerson()));
					acctDetails.put("shipContactPersonTel", mainContact.getAmtbContactPerson().getMainContactTel());
				}
			}
		
			//this is for div/dept status
			if(acct.getAmtbAcctStatuses()!=null && acct.getAmtbAcctStatuses().size()>0){
				AmtbAcctStatus acctStatues = acct.getAmtbAcctStatuses().iterator().next();
				acctDetails.put("acctStatus",NonConfigurableConstants.ACCOUNT_STATUS.get(acctStatues.getAcctStatus()));
			}
			returnMap.put(acct.getAccountNo(), acctDetails);
		}
		return returnMap;
	}
	
	public Map<String, Object> searchAccountHeader(String custNo){
		AmtbAccount account = this.daoHelper.getAccountDao().retrieveAccount(custNo);
		Map<String, Object> returnMap = new HashMap<String, Object>();
		
		returnMap.put("acctType", account.getAmtbAcctType().getAcctType());
		returnMap.put("acctNo", account.getAccountNo());
		returnMap.put("createdDt", account.getCreatedDt());
	
		return returnMap;
	}
	public AmtbAccount getFmtbArContCodeMaster(String custNo) {
		return this.daoHelper.getAccountDao().retrieveAccount(custNo);
	}
	public Map<String, Object> searchAccount(String custNo){
		logger.info("searchAccount(String custNo)");
		AmtbAccount account = this.daoHelper.getAccountDao().retrieveAccount(custNo);
		Map<String, Object> returnMap = new HashMap<String, Object>();
		getCommonFields(returnMap, account);
		if(account.getAmtbAcctType().getAcctTemplate().equals(NonConfigurableConstants.ACCOUNT_TEMPLATES_CORPORATE)){
			getCorpFields(returnMap, account);
		}else{
			getPersFields(returnMap, account);
		}
		return returnMap;
	}
	public Map<Integer, Map<String, String>> searchContacts(String custNo, String mainContactName, String mainContactEmail, String mainContactTel, String mainContactMobile){
		logger.info("searchContacts(String custNo, String mainContactName, String mainContactEmail, String mainContactTel, String mainContactMobile)");
		List<AmtbContactPerson> contacts = this.daoHelper.getAccountDao().getContacts(custNo, mainContactName, mainContactEmail, mainContactTel, mainContactMobile);
		AmtbContactPerson mainBilling = this.daoHelper.getAccountDao().getAccountMainBilling(custNo);
		AmtbContactPerson mainShipping = this.daoHelper.getAccountDao().getAccountMainShipping(custNo);
		Integer mainBillingNo, mainShippingNo;
		if(mainBilling!=null){
			mainBillingNo = mainBilling.getContactPersonNo();
		}else{
			mainBillingNo = null;
		}
		if(mainShipping!=null){
			mainShippingNo = mainShipping.getContactPersonNo();
		}else{
			mainShippingNo = null;
		}
		Map<Integer, Map<String, String>> returnMap = new LinkedHashMap<Integer, Map<String, String>>();
		TreeSet<AmtbContactPerson> sortedContacts = new TreeSet<AmtbContactPerson>(new Comparator<AmtbContactPerson>(){
			public int compare(AmtbContactPerson o1, AmtbContactPerson o2) {
				int result = getContactCombinedNames(o1).compareTo(getContactCombinedNames(o2));
				//Same name but does not mean same contact person no so compare that to get result
				//if not result will be inaccurate
				//e.g. ID1,Sam Tan ID2,Sam Tan
				//Only one of the results will appear
				if(result==0)
					result = o1.getContactPersonNo().compareTo(o2.getContactPersonNo());
				return result;
			}
		});
		sortedContacts.addAll(contacts);
		for(AmtbContactPerson contact : sortedContacts){
			Map<String, String> contactDetails = new HashMap<String, String>();
			contactDetails.put("mainContactName", getContactCombinedNames(contact));
			contactDetails.put("mainContactTitle", contact.getMainContactTitle());
			contactDetails.put("mainContactTel", contact.getMainContactTel());
			contactDetails.put("mainContactMobile", contact.getMainContactMobile());
			contactDetails.put("mainContactFax", contact.getMainContactFax());
			contactDetails.put("mainContactEmail", contact.getMainContactEmail());
			contactDetails.put("sameCorpAdd", contact.getSameAsCorporate()==null ? NonConfigurableConstants.BOOLEAN.get(NonConfigurableConstants.BOOLEAN_NO) : NonConfigurableConstants.BOOLEAN.get(contact.getSameAsCorporate()));
			if(contact.getContactPersonNo().equals(mainBillingNo)){
				contactDetails.put("mainBilling", "Y");
			}
			if(contact.getContactPersonNo().equals(mainShippingNo)){
				contactDetails.put("mainShipping", "Y");
			}
			returnMap.put(contact.getContactPersonNo(), contactDetails);
		}
		return returnMap;
	}
	public Map<String, String> getContact(String contactPersonNo){
		logger.info("getContact(String contactPersonNo)");
		AmtbContactPerson contact = this.daoHelper.getAccountDao().getContact(Integer.parseInt(contactPersonNo));
		if(contact == null){
			return null;
		}
		Map<String, String> returnMap = new HashMap<String, String>();
		if(this.daoHelper.getAccountDao().isAccountMainBilling(contact.getContactPersonNo())){
			returnMap.put("mainBilling", "Y");
		}
		if(this.daoHelper.getAccountDao().isAccountMainShipping(contact.getContactPersonNo())){
			returnMap.put("mainShipping", "Y");
		}
		returnMap.put("updateCostCentre", contact.getUpdateCostCentre());
		if(contact.getMstbMasterTableByMainContactSal()!=null){
			returnMap.put("mainContactSalCode", contact.getMstbMasterTableByMainContactSal().getMasterCode());
		}
		returnMap.put("mainContactName", contact.getMainContactName());
		returnMap.put("mainContactTitle", contact.getMainContactTitle());
		returnMap.put("mainContactTel", contact.getMainContactTel());
		returnMap.put("mainContactMobile", contact.getMainContactMobile());
		returnMap.put("mainContactFax", contact.getMainContactFax());
		returnMap.put("mainContactEmail", contact.getMainContactEmail());
		if(contact.getMstbMasterTableByMainContactRace()!=null){
			returnMap.put("mainContactRace", contact.getMstbMasterTableByMainContactRace().getMasterCode());
		}
		if(contact.getMstbMasterTableBySubContactSal()!=null){
			returnMap.put("subContactSalCode", contact.getMstbMasterTableBySubContactSal().getMasterCode());
		}
		returnMap.put("subContactName", contact.getSubContactName());
		returnMap.put("subContactTitle", contact.getSubContactTitle());
		returnMap.put("subContactTel", contact.getSubContactTel());
		returnMap.put("subContactMobile", contact.getSubContactMobile());
		returnMap.put("subContactFax", contact.getSubContactFax());
		returnMap.put("subContactEmail", contact.getSubContactEmail());
		if(contact.getMstbMasterTableBySubContactRace()!=null){
			returnMap.put("subContactRace", contact.getMstbMasterTableBySubContactRace().getMasterCode());
		}
		returnMap.put("useCorpAddress", contact.getSameAsCorporate());
		returnMap.put("blkNo", contact.getAddressBlock());
		returnMap.put("unitNo", contact.getAddressUnit());
		returnMap.put("street", contact.getAddressStreet());
		returnMap.put("building", contact.getAddressBuilding());
		returnMap.put("area", contact.getAddressArea());
		if(contact.getMstbMasterTableByAddressCountry()!=null){
			returnMap.put("countryCode", contact.getMstbMasterTableByAddressCountry().getMasterCode());
		}
		returnMap.put("city", contact.getAddressCity());
		returnMap.put("state", contact.getAddressState());
		returnMap.put("postal", contact.getAddressPostal());
		returnMap.put("updatedBy", contact.getUpdatedBy());
		returnMap.put("updatdDate", DateUtil.convertTimestampToStr(contact.getUpdatedDt(), DateUtil.GLOBAL_DATE_FORMAT));
		returnMap.put("updateTime", DateUtil.convertTimestampToStr(contact.getUpdatedDt(), DateUtil.GLOBAL_TIME_FORMAT));
		return returnMap;
	}
	public boolean createContact(Map<String, String> contactDetails){
		logger.info("createContact(Map<String, String> contactDetails)");
		AmtbContactPerson newContact = new AmtbContactPerson();
		// retrieving the account first
		String custNo = contactDetails.get("custNo");
		AmtbAccount acct = this.daoHelper.getAccountDao().getAccount(custNo, 0, null, null);
		newContact.setAmtbAccount(acct);
		setContactDetails(newContact, contactDetails);
		List<AmtbAcctMainContact> mainContacts = new ArrayList<AmtbAcctMainContact>();
		if(contactDetails.get("mainBilling")!=null){
			AmtbAcctMainContact mainBilling = new AmtbAcctMainContact(new AmtbAcctMainContactPK(NonConfigurableConstants.MAIN_CONTACT_TYPE_BILLING, acct));
			mainBilling.setAmtbContactPerson(newContact);
			mainContacts.add(mainBilling);
		}
		if(contactDetails.get("mainShipping")!=null){
			AmtbAcctMainContact mainShipping = new AmtbAcctMainContact(new AmtbAcctMainContactPK(NonConfigurableConstants.MAIN_CONTACT_TYPE_SHIPPING, acct));
			mainShipping.setAmtbContactPerson(newContact);
			mainContacts.add(mainShipping);
		}

		// now saving it to database and return
		//return this.daoHelper.getAccountDao().createContact(newContact, username);
		return this.daoHelper.getAccountDao().createContact(newContact, mainContacts, contactDetails.get("userId"));
	}
	public boolean deleteContact(String contactPersonNo){
		logger.info("deleteContact(String contactPersonNo)");
		return this.daoHelper.getAccountDao().deleteContact(Integer.parseInt(contactPersonNo));
	}
	public boolean isMainContact(String contactPersonNo){
		logger.info("isMainContact(String contactPersonNo)");
		return this.daoHelper.getAccountDao().isMainContact(Integer.parseInt(contactPersonNo));
	}
	public boolean updateContact(Map<String, String> contactDetails){
		logger.info("updateContact(Map<String, String> contactDetails)");
		AmtbContactPerson contact = this.daoHelper.getAccountDao().getContact(Integer.parseInt(contactDetails.get("contactPersonNo")));
		setContactDetails(contact, contactDetails);
		List<AmtbAcctMainContact> mainContacts = new ArrayList<AmtbAcctMainContact>();
		// now checking main contact
		if(contactDetails.get("mainBilling")!=null){
			AmtbAcctMainContactPK pk = new AmtbAcctMainContactPK(NonConfigurableConstants.MAIN_CONTACT_TYPE_BILLING, contact.getAmtbAccount());
			AmtbAcctMainContact mainContact = new AmtbAcctMainContact(pk);
			mainContact.setAmtbContactPerson(contact);
			mainContacts.add(mainContact);
		}
		if(contactDetails.get("mainShipping")!=null){
			AmtbAcctMainContactPK pk = new AmtbAcctMainContactPK(NonConfigurableConstants.MAIN_CONTACT_TYPE_SHIPPING, contact.getAmtbAccount());
			AmtbAcctMainContact mainContact = new AmtbAcctMainContact(pk);
			mainContact.setAmtbContactPerson(contact);
			mainContacts.add(mainContact);
		}
		return this.daoHelper.getAccountDao().updateContact(contact, mainContacts, contactDetails.get("userId"));
	}
	public Set<Map<String, String>> getProductSubscriptions(String custNo, String code){
		logger.info("getProductSubscriptions(String custNo)");
		AmtbAccount acct = null;
		if(code!=null && code.trim().length()!=0){
			acct = this.daoHelper.getAccountDao().getAccount(custNo, NonConfigurableConstants.DIVISION_LEVEL, null, code);
		}else{
			acct = this.daoHelper.getAccountDao().retrieveAccount(custNo);
		}
		return getProductSubscriptions(acct);
	}
	private void getProductSubscriptions(Map<String, Object> returnMap, AmtbAccount acct){
		logger.info("getProductSubscriptions(Map<String, Object> returnMap, AmtbAccount acct)");
		returnMap.put("prodSubscribe", getProductSubscriptions(acct));
	}
	private Set<Map<String, String>> getProductSubscriptions(AmtbAccount acct){
		LinkedHashSet<Map<String, String>> productSubscriptions = new LinkedHashSet<Map<String, String>>();
		Set<AmtbSubscTo> subscriptions = acct.getAmtbSubscTos();
		for (AmtbSubscTo subscription : subscriptions) {
			HashMap<String, String> productSubscription = new HashMap<String, String>();
			productSubscription.put("prodType", subscription.getComp_id().getPmtbProductType().getName());
			productSubscription.put("prodTypeId", subscription.getComp_id().getPmtbProductType().getProductTypeId());
			productSubscription.put("prodDiscount", subscription.getMstbProdDiscMaster()==null ? null : subscription.getMstbProdDiscMaster().getProductDiscountPlanName());
			productSubscription.put("rewards", subscription.getLrtbRewardMaster()==null ? null : subscription.getLrtbRewardMaster().getRewardPlanName());
			productSubscription.put("subscribeFee", subscription.getMstbSubscFeeMaster()==null ? null : subscription.getMstbSubscFeeMaster().getSubscriptionFeeName());
			productSubscription.put("issuanceFee", subscription.getMstbIssuanceFeeMaster()==null ? null : subscription.getMstbIssuanceFeeMaster().getIssuanceFeeName());
			productSubscriptions.add(productSubscription);
		}
		return productSubscriptions;
	}
	private void getPersFields(Map<String, Object> returnMap, AmtbAccount account){
		AmtbPersonalDetail pers = account.getAmtbPersonalDetails().iterator().next();
		returnMap.put("nric", pers.getNric());
		returnMap.put("birthdate", pers.getBirthDt());
		returnMap.put("tel", pers.getTel());
		returnMap.put("blkNo", pers.getAddressBlock());
		returnMap.put("unitNo", pers.getAddressUnit());
		returnMap.put("street", pers.getAddressStreet());
		returnMap.put("building", pers.getAddressBuilding());
		returnMap.put("area", pers.getAddressArea());
		returnMap.put("countryCode", pers.getMstbMasterTableByAddressCountry().getMasterCode());
		returnMap.put("city", pers.getAddressCity());
		returnMap.put("state", pers.getAddressState());
		returnMap.put("postal", pers.getAddressPostal());
		returnMap.put("empBlkNo", pers.getEmployerBlock());
		returnMap.put("empUnitNo", pers.getEmployerUnit());
		returnMap.put("empStreet", pers.getEmployerStreet());
		returnMap.put("empBuilding", pers.getEmployerBuilding());
		returnMap.put("empArea", pers.getEmployerArea());
		if(pers.getMstbMasterTableByEmployerCountry()!=null){
			returnMap.put("empCountryCode", pers.getMstbMasterTableByEmployerCountry().getMasterCode());
		}
		returnMap.put("empCity", pers.getEmployerCity());
		returnMap.put("empState", pers.getEmployerState());
		returnMap.put("empPostal", pers.getEmployerPostal());
		returnMap.put("jobStatusCode", pers.getMstbMasterTableByJobStatus().getMasterCode());
		returnMap.put("occupation", pers.getOccupation());
		returnMap.put("employerName", pers.getEmployerName());
		returnMap.put("industryCode", pers.getMstbMasterTableByIndustry().getMasterCode());
		returnMap.put("monthlyIncome", pers.getMonthlyIncome());
		returnMap.put("empLength", pers.getEmployLengthYear());
		// now getting billing address
		List<AmtbAcctMainContact> mainContacts = this.daoHelper.getAccountDao().getMainContacts(account.getAccountNo());
		for(AmtbAcctMainContact mainContact : mainContacts){
			if(mainContact.getComp_id().getMainContactType().equals(NonConfigurableConstants.MAIN_CONTACT_TYPE_BILLING)){
				AmtbContactPerson billing = mainContact.getAmtbContactPerson();
				if(billing.getMstbMasterTableByMainContactSal()!=null){
					returnMap.put("salutationCode", billing.getMstbMasterTableByMainContactSal().getMasterCode());
				}
				if(billing.getMstbMasterTableByMainContactRace()!=null){
					returnMap.put("race", billing.getMstbMasterTableByMainContactRace().getMasterCode());
				}
				returnMap.put("email", billing.getMainContactEmail());
				returnMap.put("mobile", billing.getMainContactMobile());
				returnMap.put("office", billing.getMainContactTel());
				if(billing.getAddressBlock()!=null){
					returnMap.put("billBlkNo", billing.getAddressBlock());
				}
				if(billing.getAddressUnit()!=null){
					returnMap.put("billUnitNo", billing.getAddressUnit());
				}
				returnMap.put("billStreet", billing.getAddressStreet());
				if(billing.getAddressBuilding()!=null){
					returnMap.put("billBuilding", billing.getAddressBuilding());
				}
				if(billing.getAddressArea()!=null){
					returnMap.put("billArea", billing.getAddressArea());
				}
				returnMap.put("billCountryCode", billing.getMstbMasterTableByAddressCountry().getMasterCode());
				if(billing.getAddressCity()!=null){
					returnMap.put("billCity", billing.getAddressCity());
				}
				if(billing.getAddressState()!=null){
					returnMap.put("billState", billing.getAddressState());
				}
				returnMap.put("billPostal", billing.getAddressPostal());
			}else if(mainContact.getComp_id().getMainContactType().equals(NonConfigurableConstants.MAIN_CONTACT_TYPE_SHIPPING)){
				AmtbContactPerson shipping = mainContact.getAmtbContactPerson();
				if(shipping.getAddressBlock()!=null){
					returnMap.put("shipBlkNo", shipping.getAddressBlock());
				}
				if(shipping.getAddressUnit()!=null){
					returnMap.put("shipUnitNo", shipping.getAddressUnit());
				}
				returnMap.put("shipStreet", shipping.getAddressStreet());
				if(shipping.getAddressBuilding()!=null){
					returnMap.put("shipBuilding", shipping.getAddressBuilding());
				}
				if(shipping.getAddressArea()!=null){
					returnMap.put("shipArea", shipping.getAddressArea());
				}
				returnMap.put("shipCountryCode", shipping.getMstbMasterTableByAddressCountry().getMasterCode());
				if(shipping.getAddressCity()!=null){
					returnMap.put("shipCity", shipping.getAddressCity());
				}
				if(shipping.getAddressState()!=null){
					returnMap.put("shipState", shipping.getAddressState());
				}
				returnMap.put("shipPostal", shipping.getAddressPostal());
			}
		}
	}
	private void getCorpFields(Map<String, Object> returnMap, AmtbAccount account){
		logger.info("getCorpFields(Map<String, Object> returnMap, AmtbAccount account)");
		AmtbCorporateDetail corp = account.getAmtbCorporateDetails().iterator().next();
		returnMap.put("rcbNo", corp.getRcbNo());
		returnMap.put("industryCode", corp.getMstbMasterTableByIndustry().getMasterCode());
		returnMap.put("rcbDate", corp.getRcbDt());
		returnMap.put("capital", corp.getCapital());
		returnMap.put("blkNo", corp.getAddressBlock());
		returnMap.put("unitNo", corp.getAddressUnit());
		returnMap.put("street", corp.getAddressStreet());
		returnMap.put("building", corp.getAddressBuilding());
		returnMap.put("area", corp.getAddressArea());
		returnMap.put("countryCode", corp.getMstbMasterTableByAddressCountry().getMasterCode());
		returnMap.put("city", corp.getAddressCity());
		returnMap.put("state", corp.getAddressState());
		returnMap.put("postal", corp.getAddressPostal());
		returnMap.put("projectCode", corp.getProjectCode());
		if(corp.getMstbMasterTableByAuthPersonSal()!=null){
			returnMap.put("salutationCode", corp.getMstbMasterTableByAuthPersonSal().getMasterCode());
		}
		returnMap.put("tel", corp.getTel());
		returnMap.put("fax", corp.getFax());
		returnMap.put("authPerson", corp.getAuthPersonName());
		returnMap.put("authTitle", corp.getAuthPersonTitle());
		returnMap.put("invoiceFormatCode", account.getInvoiceFormat());
		returnMap.put("invoiceSortingCode", account.getInvoiceSorting());
		returnMap.put("govtEInvFlag", account.getGovtEInvoiceFlag());
		returnMap.put("businessUnit", account.getMstbMasterTableByBusinessUnit());
		returnMap.put("aceIndicator", account.getAceIndicator());
		returnMap.put("coupaIndicator", account.getCoupaIndicator());
		logger.info("account.getCoupaIndicator() :"+account.getCoupaIndicator());
		
		returnMap.put("pubbsFlag", account.getPubbsFlag());
		returnMap.put("fiFlag", account.getFiFlag());
	}
	private void getCommonFields(Map<String, Object> returnMap, AmtbAccount acct){
		// adding common details in account model
		returnMap.put("custNo", acct.getCustNo());
		TreeSet<AmtbAcctStatus> sortStatus = new TreeSet<AmtbAcctStatus>(new Comparator<AmtbAcctStatus>(){
			public int compare(AmtbAcctStatus o1, AmtbAcctStatus o2) {
				return o1.getEffectiveDt().compareTo(o2.getEffectiveDt());
			}
		});
		for(AmtbAcctStatus status : acct.getAmtbAcctStatuses()){
			if(status.getEffectiveDt().before(DateUtil.getCurrentTimestamp())){
				sortStatus.add(status);
			}
		}
		returnMap.put("acctStatus", NonConfigurableConstants.ACCOUNT_STATUS.get(sortStatus.last().getAcctStatus()));
		returnMap.put("acctType", acct.getAmtbAcctType().getAcctType());
		returnMap.put("acctNo", acct.getAccountNo());
		returnMap.put("acctName", acct.getAccountName());
		returnMap.put("createdDt", acct.getCreatedDt());
		returnMap.put("nameOnCard", acct.getNameOnCard());
		returnMap.put("eInvoice", acct.getEinvoiceFlag());
		returnMap.put("invoicePrinting", acct.getInvoicePrinting());
		returnMap.put("outsourcePrinting", acct.getOutsourcePrintingFlag());
		returnMap.put("sms", acct.getSmsFlag());
		returnMap.put("smsExpiry", acct.getSmsExpiryFlag());
		returnMap.put("smsTopUp", acct.getSmsTopupFlag());
		returnMap.put("arAcct", acct.getFmtbArContCodeMaster().getArControlCode());
		returnMap.put("entity", acct.getFmtbArContCodeMaster().getFmtbEntityMaster().getEntityName());
		returnMap.put("recurring", acct.getRecurringFlag());
		returnMap.put("recurringChargeDay", acct.getRecurringChargeDay());
		// if PA, get from account credit limit table
		if(sortStatus.last().getAcctStatus().equals(NonConfigurableConstants.ACCOUNT_STATUS_PENDING_ACTIVATION)){
			for (AmtbAcctCredLimit creditLimit : acct.getAmtbAcctCredLimits()) {
				if(creditLimit.getCreditLimitType().equals(NonConfigurableConstants.CREDIT_LIMIT_PERMANENT)){
					returnMap.put("creditLimit", StringUtil.bigDecimalToString(creditLimit.getNewCreditLimit(), StringUtil.GLOBAL_DECIMAL_FORMAT));
					returnMap.put("approver", creditLimit.getSatbUser().getName());
					returnMap.put("remarks", creditLimit.getRemarks());
					break;
				}
			}
		}else{// others get from account
			for (AmtbAcctCredLimit creditLimit : acct.getAmtbAcctCredLimits()) {
				if(creditLimit.getCreditLimitType().equals(NonConfigurableConstants.CREDIT_LIMIT_PERMANENT)){
					returnMap.put("approver", creditLimit.getSatbUser().getName());
					returnMap.put("remarks", creditLimit.getRemarks());
					break;
				}
			}
			returnMap.put("creditLimit", StringUtil.bigDecimalToString(acct.getCreditLimit(), StringUtil.GLOBAL_DECIMAL_FORMAT));
			returnMap.put("creditBalance", StringUtil.bigDecimalToString(acct.getCreditBalance(), StringUtil.GLOBAL_DECIMAL_FORMAT));
			if(acct.getTempCreditLimit()!=null){
				returnMap.put("tempCreditLimit", StringUtil.bigDecimalToString(acct.getTempCreditLimit(), StringUtil.GLOBAL_DECIMAL_FORMAT));
			}
		}
		returnMap.put("deposit", StringUtil.bigDecimalToString(acct.getDeposit(), StringUtil.GLOBAL_DECIMAL_FORMAT));
		if(acct.getMstbMasterTableByInformationSource()!=null){
			returnMap.put("infoSourceCode", acct.getMstbMasterTableByInformationSource().getMasterCode());
		}
		// getting current effective person
		AmtbAcctSalesperson currentSalesperson = null;
		for(AmtbAcctSalesperson salesperson : acct.getAmtbAcctSalespersons()){
			if(salesperson.getEffectiveDtFrom().before(DateUtil.getCurrentTimestamp())){
				if(
						currentSalesperson==null ||
						currentSalesperson.getEffectiveDtFrom().before(salesperson.getEffectiveDtFrom()) ||
						(currentSalesperson.getEffectiveDtFrom().equals(salesperson.getEffectiveDtFrom()) && salesperson.getEffectiveDtTo()==null)
						){
					currentSalesperson = salesperson;
				}
			}
		}
		if(currentSalesperson!=null){
			returnMap.put("salesPerson", currentSalesperson.getMstbSalesperson().getName());
		}else{
			returnMap.put("salesPerson", "-");
		}

		returnMap.put("overdueReminder", acct.getOverdueReminder());
		returnMap.put("printTaxInvOnly", acct.getPrintTaxInvoiceOnly());
		if(acct.getReminderEmail() != null)
			returnMap.put("reminderEmail", acct.getReminderEmail());
		else
			returnMap.put("reminderEmail", "-");
		
		if(acct.geteInvoiceEmail() != null)
			returnMap.put("eInvoiceEmail", acct.geteInvoiceEmail());
		else
			returnMap.put("eInvoiceEmail", "-");
		
		returnMap.put("eInvoiceEmailFlag", acct.geteInvoiceEmailFlag());
		
		// created details
		returnMap.put("createdDate", DateUtil.convertTimestampToStr(acct.getCreatedDt(), DateUtil.GLOBAL_DATE_FORMAT));
		// updated details
		returnMap.put("lastUpdatedBy", acct.getUpdatedBy());
		returnMap.put("lastUpdatedDate", DateUtil.convertTimestampToStr(acct.getUpdatedDt(), DateUtil.GLOBAL_DATE_FORMAT));
		returnMap.put("lastUpdatedTime", DateUtil.convertTimestampToStr(acct.getUpdatedDt(), DateUtil.GLOBAL_TIME_FORMAT));
		getProductSubscriptions(returnMap, acct);
	}
	public String getContactCombinedNames(AmtbContactPerson contact){
		StringBuffer contactName = new StringBuffer();
		if(contact!=null){
			/*if(contact.getMstbMasterTableByMainContactSal()!=null){
				contactName.append(contact.getMstbMasterTableByMainContactSal().getMasterValue());
				contactName.append(" ");
			}*/
			contactName.append(contact.getMainContactName());
			if(contact.getSubContactName()!=null){
				contactName.append(" / ");
				/*if(contact.getMstbMasterTableBySubContactSal()!=null){
					contactName.append(contact.getMstbMasterTableBySubContactSal().getMasterValue());
					contactName.append(" ");
				}*/
				contactName.append(contact.getSubContactName());
			}
		}
		return contactName.toString();
	}
	@SuppressWarnings("unchecked")
	public boolean createCorpSubAccount(Map<String, Object> accountDetails) throws Exception{
		SatbUser user = this.daoHelper.getUserDao().getUserWithRoles((Long)accountDetails.get("userId"));
		AmtbAccount newAcct = new AmtbAccount();
		newAcct.setCode((String)accountDetails.get("acctCode"));
		newAcct.setAccountName((String)accountDetails.get("acctName"));
		newAcct.setNameOnCard((String)accountDetails.get("nameOnCard"));
		newAcct.setCreditLimit((BigDecimal)accountDetails.get("creditLimit"));
		newAcct.setCreditBalance((BigDecimal)accountDetails.get("creditLimit"));
		newAcct.setInvoiceFormat((String)accountDetails.get("invoiceFormat"));
		newAcct.setInvoiceSorting((String)accountDetails.get("invoiceSorting"));
		// Govt eInvoice Enhancement
		newAcct.setGovtEInvoiceFlag((String)accountDetails.get("govtEInvFlag"));
		newAcct.setMstbMasterTableByBusinessUnit(ConfigurableConstants.getMasterTable(ConfigurableConstants.GOVT_EINVOICE_BUSINESS_UNIT_MASTER_TYPE, (String)accountDetails.get("businessUnit")));
		
		//pubbs
		newAcct.setPubbsFlag((String)accountDetails.get("pubbsFlag"));
		//fi
		newAcct.setFiFlag((String)accountDetails.get("fiFlag"));
				
		// parent
		if(accountDetails.get("parentCode")!=null){// denotes a parent division
			String custNo = (String)accountDetails.get("custNo");
			String parentCode = (String)accountDetails.get("parentCode");
			List<AmtbAccount> accts = this.daoHelper.getAccountDao().getAccounts(custNo, null, parentCode, null, null, 1, null);
			AmtbAccount parent = accts.isEmpty() ? null : accts.get(0);
			newAcct.setAmtbAccount(parent);
			newAcct.setAccountCategory(NonConfigurableConstants.ACCOUNT_CATEGORY_DEPARTMENT);
		}else{// denotes a parent corporate
			String custNo = (String)accountDetails.get("custNo");
			AmtbAccount parent = this.daoHelper.getAccountDao().retrieveAccount(custNo);
			newAcct.setAmtbAccount(parent);
			newAcct.setAccountCategory(NonConfigurableConstants.ACCOUNT_CATEGORY_DIVISION);
		}
		Integer acctNo = (Integer)this.save(newAcct, user.getLoginId());
		if(newAcct.getAmtbAccount()!=null){
			AmtbAcctStatus parentStatus = getCurrentStatus(newAcct.getAmtbAccount().getAmtbAcctStatuses());
			if(parentStatus!=null && !parentStatus.getAcctStatus().equals(NonConfigurableConstants.ACCOUNT_STATUS_PENDING_ACTIVATION)){
				// credit limit
				AmtbAcctCredLimit creditLimit = new AmtbAcctCredLimit();
				creditLimit.setAmtbAccount(newAcct);
				newAcct.getAmtbAcctCredLimits().add(creditLimit);
				creditLimit.setCreditLimitType(NonConfigurableConstants.CREDIT_LIMIT_PERMANENT);
				creditLimit.setEffectiveDtFrom(DateUtil.getCurrentTimestamp());
				creditLimit.setNewCreditLimit((BigDecimal)accountDetails.get("creditLimit"));
				creditLimit.setSatbUser(user);
				creditLimit.setRemarks("INITIAL CREDIT LIMIT");
				this.save(creditLimit);
			}
		}
		// status
		List<SuspendPeriod> parentSuspensions = getSuspensions(newAcct.getAmtbAccount(), DateUtil.getCurrentDate());
		for(SuspendPeriod parentSuspension : parentSuspensions){
			AmtbAcctStatus newStatus = new AmtbAcctStatus();
			newStatus.setAmtbAccount(newAcct);
			newAcct.getAmtbAcctStatuses().add(newStatus);
			newStatus.setAcctStatus(NonConfigurableConstants.ACCOUNT_STATUS_PARENT_SUSPENDED);
			newStatus.setEffectiveDt(parentSuspension.getSuspendStart().getEffectiveDt());
			newStatus.setMstbMasterTable(parentSuspension.getSuspendStart().getMstbMasterTable());
			newStatus.setSatbUser(parentSuspension.getSuspendStart().getSatbUser());
			newStatus.setStatusRemarks(parentSuspension.getSuspendStart().getStatusRemarks());
			this.save(newStatus);
			if(parentSuspension.getSuspendEnd()!=null){
				newStatus = new AmtbAcctStatus();
				newStatus.setAmtbAccount(newAcct);
				newAcct.getAmtbAcctStatuses().add(newStatus);
				newStatus.setAcctStatus(NonConfigurableConstants.ACCOUNT_STATUS_ACTIVE);
				newStatus.setEffectiveDt(parentSuspension.getSuspendEnd().getEffectiveDt());
				newStatus.setMstbMasterTable(parentSuspension.getSuspendEnd().getMstbMasterTable());
				newStatus.setSatbUser(parentSuspension.getSuspendEnd().getSatbUser());
				newStatus.setStatusRemarks(parentSuspension.getSuspendEnd().getStatusRemarks());
				this.save(newStatus);
			}
		}
		for(AmtbAcctStatus parentStatus : newAcct.getAmtbAccount().getAmtbAcctStatuses()){
			if(parentStatus.getAcctStatus().equals(NonConfigurableConstants.ACCOUNT_STATUS_TERMINATED)){
				AmtbAcctStatus newStatus = new AmtbAcctStatus();
				newStatus.setAmtbAccount(newAcct);
				newAcct.getAmtbAcctStatuses().add(newStatus);
				newStatus.setAcctStatus(NonConfigurableConstants.ACCOUNT_STATUS_TERMINATED);
				newStatus.setEffectiveDt(parentStatus.getEffectiveDt());
				newStatus.setMstbMasterTable(parentStatus.getMstbMasterTable());
				newStatus.setSatbUser(user);
				newStatus.setStatusRemarks(parentStatus.getStatusRemarks());
				this.save(newStatus);
			}
			if(parentStatus.getAcctStatus().equals(NonConfigurableConstants.ACCOUNT_STATUS_CLOSED)){
				AmtbAcctStatus newStatus = new AmtbAcctStatus();
				newStatus.setAmtbAccount(newAcct);
				newAcct.getAmtbAcctStatuses().add(newStatus);
				newStatus.setAcctStatus(NonConfigurableConstants.ACCOUNT_STATUS_CLOSED);
				newStatus.setEffectiveDt(parentStatus.getEffectiveDt());
				newStatus.setMstbMasterTable(parentStatus.getMstbMasterTable());
				newStatus.setSatbUser(user);
				newStatus.setStatusRemarks(parentStatus.getStatusRemarks());
				this.save(newStatus);
			}
		}
		if(getStatus(newAcct.getAmtbAcctStatuses(), DateUtil.getCurrentDate())==null){
			AmtbAcctStatus parentStatus = getCurrentStatus(newAcct.getAmtbAccount().getAmtbAcctStatuses());
			if(parentStatus!=null && !parentStatus.getAcctStatus().equals(NonConfigurableConstants.ACCOUNT_STATUS_PENDING_ACTIVATION)){
				AmtbAcctStatus newStatus = new AmtbAcctStatus();
				newStatus.setAmtbAccount(newAcct);
				newAcct.getAmtbAcctStatuses().add(newStatus);
				newStatus.setAcctStatus(NonConfigurableConstants.ACCOUNT_STATUS_ACTIVE);
				newStatus.setEffectiveDt(DateUtil.getCurrentTimestamp());
				newStatus.setSatbUser(user);
				this.save(newStatus);
			}
		}
		AmtbAcctMainContact mainBilling = new AmtbAcctMainContact(new AmtbAcctMainContactPK(NonConfigurableConstants.MAIN_CONTACT_TYPE_BILLING, newAcct));
		mainBilling.setAmtbContactPerson(this.daoHelper.getAccountDao().getContact((Integer)accountDetails.get("billingContact")));
		this.save(mainBilling,  user.getLoginId());
		if(accountDetails.get("shippingContact")!=null){
			AmtbAcctMainContact mainShipping = new AmtbAcctMainContact(new AmtbAcctMainContactPK(NonConfigurableConstants.MAIN_CONTACT_TYPE_SHIPPING, newAcct));
			mainShipping.setAmtbContactPerson(this.daoHelper.getAccountDao().getContact((Integer)accountDetails.get("shippingContact")));
			this.save(mainShipping,  user.getLoginId());
		}
		List<String> cardLessProductList = new ArrayList<String>();
		List<PmtbProductType> cardlessProducts = this.businessHelper.getProductTypeBusiness().getCardlessProductType();
		for(PmtbProductType cardlessProduct : cardlessProducts) {
			cardLessProductList.add(cardlessProduct.getProductTypeId().trim());
		}
		List<String> prodSubscriptions = (List<String>)accountDetails.get("prodSubscriptions");
		for(String prodSubscription : prodSubscriptions){
			for(AmtbSubscTo parentProdSubscription : newAcct.getAmtbAccount().getAmtbSubscTos()){
				PmtbProductType prodSubscribeProdType = parentProdSubscription.getComp_id().getPmtbProductType();
				if(prodSubscribeProdType.getProductTypeId().equals(prodSubscription)){
					AmtbSubscTo childProdSubscription = new AmtbSubscTo();
					childProdSubscription.setComp_id(parentProdSubscription.getComp_id());
					childProdSubscription.setLrtbRewardMaster(parentProdSubscription.getLrtbRewardMaster());
					childProdSubscription.setMstbProdDiscMaster(parentProdSubscription.getMstbProdDiscMaster());
					childProdSubscription.setMstbSubscFeeMaster(parentProdSubscription.getMstbSubscFeeMaster());
					childProdSubscription.setMstbIssuanceFeeMaster(parentProdSubscription.getMstbIssuanceFeeMaster());
					childProdSubscription.getComp_id().setAmtbAccount(newAcct);
					childProdSubscription.setEffectiveDt(DateUtil.getCurrentTimestamp());
					this.save(childProdSubscription);
					// interface cnii
					Map<String, String> prop = (Map)SpringUtil.getBean("webserviceProperties");
//					if(prodSubscribeProdType.getProductTypeId().equals(NonConfigurableConstants.PREMIER_SERVICE) && NonConfigurableConstants.BOOLEAN_NO.equals((String) prop.get("ws.cnii.disable"))){
					if(cardLessProductList.contains(prodSubscribeProdType.getProductTypeId()) && NonConfigurableConstants.BOOLEAN_NO.equals((String) prop.get("ws.cnii.disable"))){
						if(newAcct.getAmtbAccount()!=null){
							AmtbAcctStatus parentStatus = getCurrentStatus(newAcct.getAmtbAccount().getAmtbAcctStatuses());
							if(parentStatus!=null && !parentStatus.getAcctStatus().equals(NonConfigurableConstants.ACCOUNT_STATUS_PENDING_ACTIVATION)){
								IttbCniiAcctReq cniiRequest = new IttbCniiAcctReq();
								cniiRequest.setReqId(DateUtil.convertDateToStr(DateUtil.getCurrentDate(), DateUtil.GLOBAL_CNII_SEQ_FORMAT) + this.daoHelper.getAccountDao().getNextSequenceNo(Sequence.CNII_REQ_NO_SEQUENCE));
								cniiRequest.setReqDate(DateUtil.getCurrentTimestamp());
								cniiRequest.setEventType(NonConfigurableConstants.CNII_REQUEST_EVENT_TYPE_ACTIVATE);
								cniiRequest.setReqStatus(NonConfigurableConstants.CNII_REQUEST_STATUS_PENDING);
//								cniiRequest.setTerminateDt(DateUtil.getCurrentDate());
								cniiRequest.setValidFlg(NonConfigurableConstants.BOOLEAN_YES);
								cniiRequest.setUpdatedBy(NonConfigurableConstants.CNII_REQUEST_UPDATED_BY_IBS);
								StringBuffer acctId = new StringBuffer();
								StringBuffer parentId = new StringBuffer();
								AmtbAccount acct = newAcct.getAmtbAccount();
								if(acct.getCustNo()!=null && acct.getCustNo().length()!=0){
									acctId.append(acct.getCustNo());
									parentId.append(acct.getCustNo());
								}else{
									acctId.append(acct.getCode());
									parentId.append(acct.getCode());
									// now getting the parent
									AmtbAccount parent = acct.getAmtbAccount();
									if(parent!=null){// this shouldn't happen
										while(parent.getCustNo()==null){
											acctId.insert(0, parent.getCode());
											parentId.insert(0, parent.getCode());
											parent = this.daoHelper.getAccountDao().getParent(parent).getAmtbAccount();
										}
										acctId.insert(0, parent.getCustNo());
										parentId.insert(0, parent.getCustNo());
									}
								}
								acctId.append(newAcct.getCode());
								cniiRequest.setAccountId(acctId.toString());
								cniiRequest.setAccountCd(newAcct.getCode()); //surely is code because this is the method to create sub account
								cniiRequest.setAccountNm(newAcct.getAccountName());
								cniiRequest.setParentId(parentId.toString());
								cniiRequest.setUpdatedDt(DateUtil.getCurrentTimestamp());
								//this.save(cniiRequest);
								ArrayList<IttbCniiAcctReq> cniiList = new ArrayList<IttbCniiAcctReq>();
								cniiList.add(cniiRequest);
//								IBSCNIIUpdateAcctClient.send(cniiList);
								
								try
								{
									//29 Aug 2018 CNII Acct Sync
									String msg = updateCniiAcctSyncProcedure(cniiList);
									logger.info("CNII Acct Sync log : "+msg);
								} catch (Exception e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
									throw new CniiInterfaceException("CNII Interface Acct Sync Error - "+e);
								}
							}
						}
					}
					break;
				}
			}
		}
		// now interfacing to AS for new account
		if(newAcct.getAmtbAccount()!=null){
			AmtbAcctStatus parentStatus = getCurrentStatus(newAcct.getAmtbAccount().getAmtbAcctStatuses());
			if(parentStatus!=null && !parentStatus.getAcctStatus().equals(NonConfigurableConstants.ACCOUNT_STATUS_PENDING_ACTIVATION)){
				String accountType;
				if(newAcct.getAmtbAccount().getCustNo()!=null){
					accountType = API.ACCOUNT_TYPE_DIVISION;
				}else{
					accountType = API.ACCOUNT_TYPE_DEPARTMENT;
				}
				
				API.createAccount(API.formulateAccountId(newAcct), accountType, newAcct.getCreditLimit().toString(), API.formulateParentAccountId(newAcct),  user.getLoginId());
			}
		}
		return true;
	}
	@SuppressWarnings("unchecked")
	public HashMap updatePersSubAccount(Map<String, Object> accountDetails) throws CniiInterfaceException{
		String userId = (String)accountDetails.get("userId");
		HashMap result = new HashMap();
		
		AmtbAccount acct = this.daoHelper.getAccountDao().getAccount(Integer.parseInt((String)accountDetails.get("acctNo")));
		acct.setAccountName((String)accountDetails.get("acctName"));
		acct.setNameOnCard((String)accountDetails.get("nameOnCard"));
		acct.setCreditLimit((BigDecimal)accountDetails.get("creditLimit"));
		acct.setCreditBalance((BigDecimal)accountDetails.get("creditLimit"));
		this.update(acct, userId);
		AmtbPersonalDetail persDetail = this.daoHelper.getAccountDao().getPersonalDetail(acct);
		persDetail.setNric((String)accountDetails.get("nric"));
		persDetail.setBirthDt(DateUtil.convertDateToTimestamp((Date)accountDetails.get("birthdate")));
		persDetail.setMstbMasterTableByRelationToParent(ConfigurableConstants.getMasterTable(ConfigurableConstants.RELATIONSHIP_TO_ACCOUNT_CODE, (String)accountDetails.get("relationCode")));
		persDetail.setTel((String)accountDetails.get("tel"));
		persDetail.setAddressBlock((String)accountDetails.get("blkNo"));
		persDetail.setAddressUnit((String)accountDetails.get("unitNo"));
		persDetail.setAddressStreet((String)accountDetails.get("street"));
		persDetail.setAddressBuilding((String)accountDetails.get("building"));
		persDetail.setAddressArea((String)accountDetails.get("area"));
		persDetail.setMstbMasterTableByAddressCountry(ConfigurableConstants.getMasterTable(ConfigurableConstants.COUNTRY_MASTER_CODE, (String)accountDetails.get("countryCode")));
		persDetail.setAddressCity((String)accountDetails.get("city"));
		persDetail.setAddressState((String)accountDetails.get("state"));
		persDetail.setAddressPostal((String)accountDetails.get("postal"));
		acct.setRecurringFlag((String)accountDetails.get("recurring"));
		
		if(accountDetails.get("recurringChargeDay") != null && ((String)accountDetails.get("recurring")).equals("Y"))
		{
			String recurringChargeDayString = (String)accountDetails.get("recurringChargeDay");
			acct.setRecurringChargeDay(Integer.parseInt(recurringChargeDayString.substring(0, recurringChargeDayString.length() - 2)));
		}
		else
			acct.setRecurringChargeDay(null);
		
		this.update(persDetail);
		// setting default values for sub applicants
		//List<AmtbAcctMainContact> mainContacts = this.daoHelper.getAccountDao().getMainContacts(acct.getAccountNo());
		if(!acct.getAmtbContactPersons().isEmpty()){
			AmtbContactPerson contact = acct.getAmtbContactPersons().iterator().next();
			contact.setMainContactName((String)accountDetails.get("acctName"));
			contact.setMainContactEmail((String)accountDetails.get("email"));
			contact.setMainContactMobile((String)accountDetails.get("mobile"));
			contact.setMainContactTel((String)accountDetails.get("office"));
			contact.setAddressBlock((String)accountDetails.get("blkNo"));
			contact.setAddressUnit((String)accountDetails.get("unitNo"));
			contact.setAddressStreet((String)accountDetails.get("street"));
			contact.setAddressBuilding((String)accountDetails.get("building"));
			contact.setAddressArea((String)accountDetails.get("area"));
			contact.setMstbMasterTableByAddressCountry(ConfigurableConstants.getMasterTable(ConfigurableConstants.COUNTRY_MASTER_CODE, (String)accountDetails.get("countryCode")));
			contact.setAddressCity((String)accountDetails.get("city"));
			contact.setAddressState((String)accountDetails.get("state"));
			contact.setAddressPostal((String)accountDetails.get("postal"));
			if(accountDetails.get("salCode")!=null){
				contact.setMstbMasterTableByMainContactSal(ConfigurableConstants.getMasterTable(ConfigurableConstants.SALUTATION_MASTER_CODE, (String)accountDetails.get("salCode")));
			}
			if(accountDetails.get("race")!=null){
				contact.setMstbMasterTableByMainContactRace(ConfigurableConstants.getMasterTable(ConfigurableConstants.RACE_MASTER_CODE, (String)accountDetails.get("race")));
			}
			this.update(contact, userId);
		}
		// updating the product subscriptions
		List<String> prodSubscriptions = (List<String>)accountDetails.get("prodSubscriptions");
		AmtbAccount parentAcct = null;
		List<AmtbSubscProdReq> emailApproval = new ArrayList<AmtbSubscProdReq>();
		for(String prodSubscription : prodSubscriptions){
			boolean found = false;
			for(AmtbSubscTo subscription : acct.getAmtbSubscTos()){
				if(subscription.getComp_id().getPmtbProductType().getProductTypeId().equals(prodSubscription)){
					found = true;
					break;
				}
			}
			if(!found){
				if(parentAcct == null){
					parentAcct = this.daoHelper.getAccountDao().getAccount(acct.getAmtbAccount().getAccountNo());
				}
				for(AmtbSubscTo parentProdSubscription : parentAcct.getAmtbSubscTos()){
					PmtbProductType prodSubscribeProdType = parentProdSubscription.getComp_id().getPmtbProductType();
					if(prodSubscribeProdType.getProductTypeId().equals(prodSubscription)){
						
						AmtbSubscProdReq reqApproveSubscription = new AmtbSubscProdReq();
						
						reqApproveSubscription.setAmtbAccount(acct);
						reqApproveSubscription.setPmtbProductType(prodSubscribeProdType);

						reqApproveSubscription.setEffectiveDt(DateUtil.getCurrentTimestamp());
						reqApproveSubscription.setReqDt(DateUtil.getCurrentTimestamp());
						reqApproveSubscription.setReqBy(CommonWindow.getUserId());
						reqApproveSubscription.setAppStatus(NonConfigurableConstants.SUBSCRIPTION_APPROVE_STATUS_PENDING_APPROVED);
						reqApproveSubscription.setSubscAction(NonConfigurableConstants.SUBSCRIPTION_STATUS_SUBSCRIBE_DIV_DEPT);
						reqApproveSubscription.setRemarks("");
						
						if(parentProdSubscription.getMstbProdDiscMaster()!=null){
							reqApproveSubscription.setMstbProdDiscMaster(parentProdSubscription.getMstbProdDiscMaster());
						}
						if(parentProdSubscription.getLrtbRewardMaster()!=null){
							reqApproveSubscription.setLrtbRewardMaster(parentProdSubscription.getLrtbRewardMaster());
						}
						if(parentProdSubscription.getMstbSubscFeeMaster()!=null){
							reqApproveSubscription.setMstbSubscFeeMaster(parentProdSubscription.getMstbSubscFeeMaster());
						}
						if(parentProdSubscription.getMstbIssuanceFeeMaster()!=null){
							reqApproveSubscription.setMstbIssuanceFeeMaster(parentProdSubscription.getMstbIssuanceFeeMaster());
						}
					
						this.save(reqApproveSubscription);
						
						emailApproval.add(reqApproveSubscription);
						result.put("approve", "true");
//						AmtbSubscTo childProdSubscription = new AmtbSubscTo();
//						childProdSubscription.setComp_id(parentProdSubscription.getComp_id());
//						childProdSubscription.setLrtbRewardMaster(parentProdSubscription.getLrtbRewardMaster());
//						childProdSubscription.setMstbProdDiscMaster(parentProdSubscription.getMstbProdDiscMaster());
//						childProdSubscription.setMstbSubscFeeMaster(parentProdSubscription.getMstbSubscFeeMaster());
//						childProdSubscription.setMstbIssuanceFeeMaster(parentProdSubscription.getMstbIssuanceFeeMaster());
//						childProdSubscription.getComp_id().setAmtbAccount(acct);
//						childProdSubscription.setEffectiveDt(DateUtil.getCurrentTimestamp());
//						this.save(childProdSubscription);
//						Map<String, String> prop = (Map)SpringUtil.getBean("webserviceProperties");
//						if(prodSubscribeProdType.getProductTypeId().equals(NonConfigurableConstants.PREMIER_SERVICE) && NonConfigurableConstants.BOOLEAN_NO.equals((String) prop.get("ws.cnii.disable"))){
//							if(acct.getAmtbAccount()!=null){
//								AmtbAcctStatus parentStatus = getCurrentStatus(acct.getAmtbAccount().getAmtbAcctStatuses());
//								if(parentStatus==null || !parentStatus.getAcctStatus().equals(NonConfigurableConstants.ACCOUNT_STATUS_PENDING_ACTIVATION)){
//									IttbCniiAcctReq cniiRequest = new IttbCniiAcctReq();
//									cniiRequest.setReqId(DateUtil.convertDateToStr(DateUtil.getCurrentDate(), DateUtil.GLOBAL_CNII_SEQ_FORMAT) + this.daoHelper.getAccountDao().getNextSequenceNo(Sequence.CNII_REQ_NO_SEQUENCE));
//									cniiRequest.setReqDate(DateUtil.getCurrentTimestamp());
//									cniiRequest.setEventType(NonConfigurableConstants.CNII_REQUEST_EVENT_TYPE_ACTIVATE);
//									cniiRequest.setReqStatus(NonConfigurableConstants.CNII_REQUEST_STATUS_PENDING);
//									cniiRequest.setTerminateDt(DateUtil.getCurrentDate());
//									cniiRequest.setValidFlg(NonConfigurableConstants.BOOLEAN_YES);
//									cniiRequest.setUpdatedBy(NonConfigurableConstants.CNII_REQUEST_UPDATED_BY_IBS);
//									StringBuffer acctId = new StringBuffer();
//									StringBuffer parentId = new StringBuffer();
//									AmtbAccount parentAcct2 = acct.getAmtbAccount();
//									if(acct.getCustNo()!=null && acct.getCustNo().length()!=0){
//										acctId.append(acct.getCustNo());
//										// Should not have parent since acct is the top-most account
//										//parentId.append(acct.getCustNo());
//									}else{
//										acctId.append(acct.getCode());
//										// Not required to append as parent is always without the last code
//										// e.g. if dept = 304DIV1DEP1, parent will be 304DIV1
//										//parentId.append(acct.getCode());
//										// now getting the parent
//										AmtbAccount parent = parentAcct2.getAmtbAccount();
//										if(parent!=null){// this shouldn't happen
//											while(parent.getCustNo()==null){
//												acctId.insert(0, parent.getCode());
//												parentId.insert(0, parent.getCode());
//												parent = this.daoHelper.getAccountDao().getParent(parent).getAmtbAccount();
//											}
//											acctId.insert(0, parent.getCustNo());
//											parentId.insert(0, parent.getCustNo());
//										}
//									}
//									cniiRequest.setAccountId(acctId.toString());
//									if(parentAcct2.getCustNo()!=null && parentAcct2.getCustNo().length()!=0){
//										cniiRequest.setAccountCd(parentAcct2.getCustNo());
//									}else{
//										cniiRequest.setAccountCd(parentAcct2.getCode());
//									}
//									cniiRequest.setAccountNm(parentAcct2.getAccountName());
//									cniiRequest.setParentId(parentId.toString());
//									cniiRequest.setUpdatedDt(DateUtil.getCurrentTimestamp());
//									//this.save(cniiRequest);
//									ArrayList<IttbCniiAcctReq> cniiList = new ArrayList<IttbCniiAcctReq>();
//									cniiList.add(cniiRequest);
//									IBSCNIIUpdateAcctClient.send(cniiList);
//								}
//							}
//						}
						break;
					}
				}
			}
		}
		List<AmtbSubscTo> deleted = new ArrayList<AmtbSubscTo>();
		for(AmtbSubscTo subscription : acct.getAmtbSubscTos()){
			boolean found = false;
			for(String prodSubscription : prodSubscriptions){
				if(prodSubscription.equals(subscription.getComp_id().getPmtbProductType().getProductTypeId())){
					found = true;
					break;
				}
			}
			if(!found){
				deleted.add(subscription);
			}
		}
		acct.getAmtbSubscTos().clear();
		for(AmtbSubscTo delete : deleted){
//			this.delete(delete);
			PmtbProductType deletedProdType = delete.getComp_id().getPmtbProductType();
			
			AmtbSubscProdReq reqApproveSubscription = new AmtbSubscProdReq();
			
			reqApproveSubscription.setAmtbAccount(acct);
			reqApproveSubscription.setPmtbProductType(deletedProdType);

			reqApproveSubscription.setEffectiveDt(DateUtil.getCurrentTimestamp());
			reqApproveSubscription.setReqDt(DateUtil.getCurrentTimestamp());
			reqApproveSubscription.setReqBy(CommonWindow.getUserId());
			reqApproveSubscription.setAppStatus(NonConfigurableConstants.SUBSCRIPTION_APPROVE_STATUS_PENDING_APPROVED);
			reqApproveSubscription.setSubscAction(NonConfigurableConstants.SUBSCRIPTION_STATUS_UNSUBSCRIBE_DIV_DEPT);
			reqApproveSubscription.setRemarks("");

			this.save(reqApproveSubscription);	
			emailApproval.add(reqApproveSubscription);
			result.put("approve", "true");
		}
		
		if(emailApproval.size() > 0)
			sendSubscriptionApprovalEmail(emailApproval.get(0));
		
		result.put("result", "true");
		return result;
	}
	@SuppressWarnings("unchecked")
	public boolean createPersSubAccount(Map<String, Object> accountDetails) throws Exception{
		SatbUser user = this.daoHelper.getUserDao().getUserWithRoles((Long)accountDetails.get("userId"));
		// creating account
		AmtbAccount newAcct = new AmtbAccount();
		newAcct.setAccountName((String)accountDetails.get("acctName"));
		newAcct.setNameOnCard((String)accountDetails.get("nameOnCard"));
		newAcct.setCreditLimit((BigDecimal)accountDetails.get("creditLimit"));
		newAcct.setCreditBalance((BigDecimal)accountDetails.get("creditLimit"));
		// creating personal detail
		AmtbPersonalDetail persDetail = new AmtbPersonalDetail();
		persDetail.setAmtbAccount(newAcct);
		newAcct.getAmtbPersonalDetails().add(persDetail);
		persDetail.setNric((String)accountDetails.get("nric"));
		persDetail.setBirthDt(DateUtil.convertDateToTimestamp((Date)accountDetails.get("birthdate")));
		persDetail.setMstbMasterTableByRelationToParent(ConfigurableConstants.getMasterTable(ConfigurableConstants.RELATIONSHIP_TO_ACCOUNT_CODE, (String)accountDetails.get("relationCode")));
		persDetail.setTel((String)accountDetails.get("tel"));
		persDetail.setAddressBlock((String)accountDetails.get("blkNo"));
		persDetail.setAddressUnit((String)accountDetails.get("unitNo"));
		persDetail.setAddressStreet((String)accountDetails.get("street"));
		persDetail.setAddressBuilding((String)accountDetails.get("building"));
		persDetail.setAddressArea((String)accountDetails.get("area"));
		persDetail.setMstbMasterTableByAddressCountry(ConfigurableConstants.getMasterTable(ConfigurableConstants.COUNTRY_MASTER_CODE, (String)accountDetails.get("countryCode")));
		persDetail.setAddressCity((String)accountDetails.get("city"));
		persDetail.setAddressState((String)accountDetails.get("state"));
		persDetail.setAddressPostal((String)accountDetails.get("postal"));
		// getting parent
		String custNo = (String)accountDetails.get("custNo");
		AmtbAccount parent = this.daoHelper.getAccountDao().retrieveAccount(custNo);
		int lastCode = 0;
		for(AmtbAccount child : parent.getAmtbAccounts()){
			try{
				int code = Integer.parseInt(child.getCode());
				if(code > lastCode){
					lastCode = code;
				}
			}catch(NumberFormatException nfe){
				logger.info("Shouldn't be here as all code for sub applicants should be numeric");
				logger.error(nfe);
			}
		}
		newAcct.setCode(StringUtil.appendLeft(""+(++lastCode), 4, "0"));
		newAcct.setAmtbAccount(parent);
		newAcct.setAccountCategory(NonConfigurableConstants.ACCOUNT_CATEGORY_SUB_APPLICANT);
		newAcct.setGovtEInvoiceFlag(NonConfigurableConstants.GOVT_EINV_FLAG_NO);
		newAcct.setRecurringFlag((String) accountDetails.get("recurring"));
		
		if(accountDetails.get("recurringChargeDay") != null && ((String)accountDetails.get("recurring")).equals("Y"))
		{
			String recurringChargeDayString = (String)accountDetails.get("recurringChargeDay");
			newAcct.setRecurringChargeDay(Integer.parseInt(recurringChargeDayString.substring(0, recurringChargeDayString.length() - 2)));
		}
		else
			newAcct.setRecurringChargeDay(null);
		
		this.save(newAcct, user.getLoginId());
		// creating contact person
		AmtbContactPerson contact = new AmtbContactPerson();
		contact.setMainContactTitle(NonConfigurableConstants.ACCOUNT_CATEGORY_SUB_APPLICANT);
		contact.setMainContactName((String)accountDetails.get("acctName"));
		contact.setMainContactEmail((String)accountDetails.get("email"));
		contact.setMainContactMobile((String)accountDetails.get("mobile"));
		contact.setMainContactTel((String)accountDetails.get("office"));
		contact.setMstbMasterTableByMainContactSal(ConfigurableConstants.getMasterTable(ConfigurableConstants.SALUTATION_MASTER_CODE, (String)accountDetails.get("salCode")));
		contact.setMstbMasterTableByMainContactRace(ConfigurableConstants.getMasterTable(ConfigurableConstants.RACE_MASTER_CODE, (String)accountDetails.get("race")));
		contact.setAddressBlock((String)accountDetails.get("blkNo"));
		contact.setAddressUnit((String)accountDetails.get("unitNo"));
		contact.setAddressStreet((String)accountDetails.get("street"));
		contact.setAddressBuilding((String)accountDetails.get("building"));
		contact.setAddressArea((String)accountDetails.get("area"));
		contact.setMstbMasterTableByAddressCountry(ConfigurableConstants.getMasterTable(ConfigurableConstants.COUNTRY_MASTER_CODE, (String)accountDetails.get("countryCode")));
		contact.setAddressCity((String)accountDetails.get("city"));
		contact.setAddressState((String)accountDetails.get("state"));
		contact.setAddressPostal((String)accountDetails.get("postal"));
		contact.setAmtbAccount(newAcct);
		// creating credit limit
		if(newAcct.getAmtbAccount()!=null){
			AmtbAcctStatus parentStatus = getCurrentStatus(newAcct.getAmtbAccount().getAmtbAcctStatuses());
			if(parentStatus!=null && !parentStatus.getAcctStatus().equals(NonConfigurableConstants.ACCOUNT_STATUS_PENDING_ACTIVATION)){
				// credit limit
				AmtbAcctCredLimit creditLimit = new AmtbAcctCredLimit();
				creditLimit.setAmtbAccount(newAcct);
				newAcct.getAmtbAcctCredLimits().add(creditLimit);
				creditLimit.setCreditLimitType(NonConfigurableConstants.CREDIT_LIMIT_PERMANENT);
				creditLimit.setEffectiveDtFrom(DateUtil.getCurrentTimestamp());
				creditLimit.setNewCreditLimit((BigDecimal)accountDetails.get("creditLimit"));
				creditLimit.setSatbUser(user);
				creditLimit.setRemarks("INITIAL CREDIT LIMIT");
				this.save(creditLimit);
			}
		}
		this.save(contact, user.getLoginId());
		// creating main billing
		for(AmtbAcctMainContact parentMainContact : parent.getAmtbAcctMainContacts()){
			AmtbAcctMainContact mainContact = new AmtbAcctMainContact(new AmtbAcctMainContactPK(parentMainContact.getComp_id().getMainContactType(), newAcct));
			mainContact.setAmtbContactPerson(parentMainContact.getAmtbContactPerson());
			this.save(mainContact, user.getLoginId());
		}
		List<String> cardLessProductList = new ArrayList<String>();
		List<PmtbProductType> cardlessProducts = this.businessHelper.getProductTypeBusiness().getCardlessProductType();
		for(PmtbProductType cardlessProduct : cardlessProducts) {
			cardLessProductList.add(cardlessProduct.getProductTypeId().trim());
		}
		// creating product subscriptions
		List<String> prodSubscriptions = (List<String>)accountDetails.get("prodSubscriptions");
		for(String prodSubscription : prodSubscriptions){
			for(AmtbSubscTo parentProdSubscription : parent.getAmtbSubscTos()){
				PmtbProductType prodSubscribeProdType = parentProdSubscription.getComp_id().getPmtbProductType();
				if(prodSubscribeProdType.getProductTypeId().equals(prodSubscription)){
					AmtbSubscTo childProdSubscription = new AmtbSubscTo();
					childProdSubscription.setComp_id(parentProdSubscription.getComp_id());
					childProdSubscription.setLrtbRewardMaster(parentProdSubscription.getLrtbRewardMaster());
					childProdSubscription.setMstbProdDiscMaster(parentProdSubscription.getMstbProdDiscMaster());
					childProdSubscription.setMstbSubscFeeMaster(parentProdSubscription.getMstbSubscFeeMaster());
					childProdSubscription.setMstbIssuanceFeeMaster(parentProdSubscription.getMstbIssuanceFeeMaster());
					childProdSubscription.getComp_id().setAmtbAccount(newAcct);
					childProdSubscription.setEffectiveDt(DateUtil.getCurrentTimestamp());
					this.save(childProdSubscription);
					Map<String, String> prop = (Map)SpringUtil.getBean("webserviceProperties");
//					if(prodSubscribeProdType.getProductTypeId().equals(NonConfigurableConstants.PREMIER_SERVICE) && NonConfigurableConstants.BOOLEAN_NO.equals((String) prop.get("ws.cnii.disable"))){
					if(cardLessProductList.contains(prodSubscribeProdType.getProductTypeId()) && NonConfigurableConstants.BOOLEAN_NO.equals((String) prop.get("ws.cnii.disable"))){
						if(newAcct.getAmtbAccount()!=null){
							AmtbAcctStatus parentStatus = getCurrentStatus(newAcct.getAmtbAccount().getAmtbAcctStatuses());
							if(parentStatus==null || !parentStatus.getAcctStatus().equals(NonConfigurableConstants.ACCOUNT_STATUS_PENDING_ACTIVATION)){
								IttbCniiAcctReq cniiRequest = new IttbCniiAcctReq();
								cniiRequest.setReqId(DateUtil.convertDateToStr(DateUtil.getCurrentDate(), DateUtil.GLOBAL_CNII_SEQ_FORMAT) + this.daoHelper.getAccountDao().getNextSequenceNo(Sequence.CNII_REQ_NO_SEQUENCE));
								cniiRequest.setReqDate(DateUtil.getCurrentTimestamp());
								cniiRequest.setEventType(NonConfigurableConstants.CNII_REQUEST_EVENT_TYPE_ACTIVATE);
								cniiRequest.setReqStatus(NonConfigurableConstants.CNII_REQUEST_STATUS_PENDING);
								cniiRequest.setTerminateDt(DateUtil.getCurrentDate());
								cniiRequest.setValidFlg(NonConfigurableConstants.BOOLEAN_YES);
								cniiRequest.setUpdatedBy(NonConfigurableConstants.CNII_REQUEST_UPDATED_BY_IBS);
								StringBuffer acctId = new StringBuffer();
								StringBuffer parentId = new StringBuffer();
								AmtbAccount acct = newAcct.getAmtbAccount();
								if(acct.getCustNo()!=null && acct.getCustNo().length()!=0){
									acctId.append(acct.getCustNo());
									// Should not have parent since acct is the top-most account
									//parentId.append(acct.getCustNo());
								}else{
									acctId.append(acct.getCode());
									// Not required to append as parent is always without the last code
									// e.g. if dept = 304DIV1DEP1, parent will be 304DIV1
									//parentId.append(acct.getCode());
									// now getting the parent
									parent = acct.getAmtbAccount();
									if(parent!=null){// this shouldn't happen
										while(parent.getCustNo()==null){
											acctId.insert(0, parent.getCode());
											parentId.insert(0, parent.getCode());
											parent = this.daoHelper.getAccountDao().getParent(parent).getAmtbAccount();
										}
										acctId.insert(0, parent.getCustNo());
										parentId.insert(0, parent.getCustNo());
									}
								}
								cniiRequest.setAccountId(acctId.toString());
								if(acct.getCustNo()!=null && acct.getCustNo().length()!=0){
									cniiRequest.setAccountCd(acct.getCustNo());
								}else{
									cniiRequest.setAccountCd(acct.getCode());
								}
								cniiRequest.setAccountNm(acct.getAccountName());
								cniiRequest.setParentId(parentId.toString());
								cniiRequest.setUpdatedDt(DateUtil.getCurrentTimestamp());
								//this.save(cniiRequest);
								ArrayList<IttbCniiAcctReq> cniiList = new ArrayList<IttbCniiAcctReq>();
								cniiList.add(cniiRequest);
//								IBSCNIIUpdateAcctClient.send(cniiList);
								
								try
								{
									//29 Aug 2018 CNII Acct Sync
									String msg = updateCniiAcctSyncProcedure(cniiList);
									logger.info("CNII Acct Sync log : "+msg);
								} catch (Exception e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
									throw new CniiInterfaceException("CNII Interface Acct Sync Error - "+e);
								}
							}
						}
					}
					break;
				}
			}
		}
		// creating status
		List<SuspendPeriod> parentSuspensions = getSuspensions(newAcct.getAmtbAccount(), DateUtil.getCurrentDate());
		for(SuspendPeriod parentSuspension : parentSuspensions){
			AmtbAcctStatus newStatus = new AmtbAcctStatus();
			newStatus.setAmtbAccount(newAcct);
			newAcct.getAmtbAcctStatuses().add(newStatus);
			newStatus.setAcctStatus(NonConfigurableConstants.ACCOUNT_STATUS_PARENT_SUSPENDED);
			newStatus.setEffectiveDt(parentSuspension.getSuspendStart().getEffectiveDt());
			newStatus.setMstbMasterTable(parentSuspension.getSuspendStart().getMstbMasterTable());
			newStatus.setSatbUser(parentSuspension.getSuspendStart().getSatbUser());
			newStatus.setStatusRemarks(parentSuspension.getSuspendStart().getStatusRemarks());
			this.save(newStatus);
			if(parentSuspension.getSuspendEnd()!=null){
				newStatus = new AmtbAcctStatus();
				newStatus.setAmtbAccount(newAcct);
				newAcct.getAmtbAcctStatuses().add(newStatus);
				newStatus.setAcctStatus(NonConfigurableConstants.ACCOUNT_STATUS_ACTIVE);
				newStatus.setEffectiveDt(parentSuspension.getSuspendEnd().getEffectiveDt());
				newStatus.setMstbMasterTable(parentSuspension.getSuspendEnd().getMstbMasterTable());
				newStatus.setSatbUser(parentSuspension.getSuspendEnd().getSatbUser());
				newStatus.setStatusRemarks(parentSuspension.getSuspendEnd().getStatusRemarks());
				this.save(newStatus);
			}
		}
		for(AmtbAcctStatus parentStatus : newAcct.getAmtbAccount().getAmtbAcctStatuses()){
			if(parentStatus.getAcctStatus().equals(NonConfigurableConstants.ACCOUNT_STATUS_TERMINATED)){
				AmtbAcctStatus newStatus = new AmtbAcctStatus();
				newStatus.setAmtbAccount(newAcct);
				newAcct.getAmtbAcctStatuses().add(newStatus);
				newStatus.setAcctStatus(NonConfigurableConstants.ACCOUNT_STATUS_TERMINATED);
				newStatus.setEffectiveDt(parentStatus.getEffectiveDt());
				newStatus.setMstbMasterTable(parentStatus.getMstbMasterTable());
				newStatus.setSatbUser(user);
				newStatus.setStatusRemarks(parentStatus.getStatusRemarks());
				this.save(newStatus);
			}
			if(parentStatus.getAcctStatus().equals(NonConfigurableConstants.ACCOUNT_STATUS_CLOSED)){
				AmtbAcctStatus newStatus = new AmtbAcctStatus();
				newStatus.setAmtbAccount(newAcct);
				newAcct.getAmtbAcctStatuses().add(newStatus);
				newStatus.setAcctStatus(NonConfigurableConstants.ACCOUNT_STATUS_CLOSED);
				newStatus.setEffectiveDt(parentStatus.getEffectiveDt());
				newStatus.setMstbMasterTable(parentStatus.getMstbMasterTable());
				newStatus.setSatbUser(user);
				newStatus.setStatusRemarks(parentStatus.getStatusRemarks());
				this.save(newStatus);
			}
		}
		if(getStatus(newAcct.getAmtbAcctStatuses(), DateUtil.getCurrentDate())==null){
			AmtbAcctStatus parentStatus = getCurrentStatus(newAcct.getAmtbAccount().getAmtbAcctStatuses());
			if(parentStatus!=null && !parentStatus.getAcctStatus().equals(NonConfigurableConstants.ACCOUNT_STATUS_PENDING_ACTIVATION)){
				AmtbAcctStatus newStatus = new AmtbAcctStatus();
				newStatus.setAmtbAccount(newAcct);
				newAcct.getAmtbAcctStatuses().add(newStatus);
				newStatus.setAcctStatus(NonConfigurableConstants.ACCOUNT_STATUS_ACTIVE);
				newStatus.setEffectiveDt(DateUtil.getCurrentTimestamp());
				newStatus.setSatbUser(user);
				this.save(newStatus);
			}
		}
		// to interface with AS
		if(newAcct.getAmtbAccount()!=null){
			AmtbAcctStatus parentStatus = getCurrentStatus(newAcct.getAmtbAccount().getAmtbAcctStatuses());
			if(parentStatus!=null && !parentStatus.getAcctStatus().equals(NonConfigurableConstants.ACCOUNT_STATUS_PENDING_ACTIVATION)){
				API.createAccount(API.formulateAccountId(newAcct), API.ACCOUNT_TYPE_SUBAPPLICANT, newAcct.getCreditLimit().toString(), API.formulateParentAccountId(newAcct), user.getLoginId());
			}
		}
		return true;
	}
	public boolean checkParentCreditLimit(String custNo, String parentCode, BigDecimal creditLimit){
		return this.daoHelper.getAccountDao().checkCreditLimit(custNo, parentCode, creditLimit);
	}
	public boolean checkChildrenCreditLimit(String custNo, String code, BigDecimal creditLimit){
		return this.daoHelper.getAccountDao().checkChildrenCreditLimit(custNo, code, creditLimit);
	}
	private void setContactDetails(AmtbContactPerson contact, Map<String, String> contactDetails){
		if(contactDetails.get("mainContactSalCode")!=null){
			contact.setMstbMasterTableByMainContactSal(ConfigurableConstants.getMasterTable(ConfigurableConstants.SALUTATION_MASTER_CODE, contactDetails.get("mainContactSalCode")));
		}
		if(contactDetails.get("mainContactRace")!=null){
			contact.setMstbMasterTableByMainContactRace(ConfigurableConstants.getMasterTable(ConfigurableConstants.RACE_MASTER_CODE, contactDetails.get("mainContactRace")));
		}
		contact.setMainContactName(contactDetails.get("mainContactName"));
		contact.setMainContactTitle(contactDetails.get("mainContactTitle"));
		contact.setMainContactTel(contactDetails.get("mainContactTel"));
		contact.setMainContactMobile(contactDetails.get("mainContactMobile"));
		contact.setMainContactFax(contactDetails.get("mainContactFax"));
		contact.setMainContactEmail(contactDetails.get("mainContactEmail"));
		if(contactDetails.get("subContactSalCode")!=null){
			contact.setMstbMasterTableBySubContactSal(ConfigurableConstants.getMasterTable(ConfigurableConstants.SALUTATION_MASTER_CODE, contactDetails.get("subContactSalCode")));
		}
		if(contactDetails.get("subContactRace")!=null){
			contact.setMstbMasterTableBySubContactRace(ConfigurableConstants.getMasterTable(ConfigurableConstants.RACE_MASTER_CODE, contactDetails.get("subContactRace")));
		}
		contact.setSubContactName(contactDetails.get("subContactName"));
		contact.setSubContactTitle(contactDetails.get("subContactTitle"));
		contact.setSubContactTel(contactDetails.get("subContactTel"));
		contact.setSubContactMobile(contactDetails.get("subContactMobile"));
		contact.setSubContactFax(contactDetails.get("subContactFax"));
		contact.setSubContactEmail(contactDetails.get("subContactEmail"));
		contact.setUpdateCostCentre(contactDetails.get("updateCostCentre"));
		if(contactDetails.get("useCorpAddress")!=null){
			AmtbCorporateDetail corpDetails = this.daoHelper.getAccountDao().getCorporateDetail(contact.getAmtbAccount());
			contact.setSameAsCorporate(NonConfigurableConstants.BOOLEAN_YES);
			contact.setAddressBlock(corpDetails.getAddressBlock());
			contact.setAddressUnit(corpDetails.getAddressUnit());
			contact.setAddressStreet(corpDetails.getAddressStreet());
			contact.setAddressBuilding(corpDetails.getAddressBuilding());
			contact.setAddressArea(corpDetails.getAddressArea());
			contact.setMstbMasterTableByAddressCountry(corpDetails.getMstbMasterTableByAddressCountry());
			contact.setAddressCity(corpDetails.getAddressCity());
			contact.setAddressState(corpDetails.getAddressState());
			contact.setAddressPostal(corpDetails.getAddressPostal());
		}else{
			contact.setSameAsCorporate(NonConfigurableConstants.BOOLEAN_NO);
			contact.setAddressBlock(contactDetails.get("blkNo"));
			contact.setAddressUnit(contactDetails.get("unitNo"));
			contact.setAddressStreet(contactDetails.get("street"));
			contact.setAddressBuilding(contactDetails.get("building"));
			contact.setAddressArea(contactDetails.get("area"));
			contact.setMstbMasterTableByAddressCountry(ConfigurableConstants.getMasterTable(ConfigurableConstants.COUNTRY_MASTER_CODE, contactDetails.get("countryCode")));
			contact.setAddressCity(contactDetails.get("city"));
			contact.setAddressState(contactDetails.get("state"));
			contact.setAddressPostal(contactDetails.get("postal"));
		}
	}
	public Map<String, Object> getAccount(String custNo, int level, String parentCode, String code){
		AmtbAccount acct = this.daoHelper.getAccountDao().getAccount(custNo, level, parentCode, code);
		Map<String, Object> returnMap = new HashMap<String, Object>();
		if(acct!=null){
			if(acct.getAmtbAccount()!=null){
				AmtbAccount parentAccount = acct.getAmtbAccount();
				if(acct.getAmtbAccount().getAmtbAccount()!=null){
					StringBuffer buffer = new StringBuffer(parentAccount.getAccountName());
					buffer.append("("); buffer.append(parentAccount.getCode()); buffer.append(")");
					returnMap.put("parentName", buffer.toString());
				}
				returnMap.put("parentCreditLimit", StringUtil.bigDecimalToString(parentAccount.getCreditLimit(), StringUtil.GLOBAL_DECIMAL_FORMAT));
			
				if(parentAccount.getTempCreditLimit() != null)
					returnMap.put("parentTempCreditLimit", StringUtil.bigDecimalToString(parentAccount.getTempCreditLimit(), StringUtil.GLOBAL_DECIMAL_FORMAT));
				
			}
			Set<String> subscribed = new HashSet<String>();
			// product subscription
			for (AmtbSubscTo AmtbSubscTo : acct.getAmtbSubscTos()) {
				subscribed.add(AmtbSubscTo.getComp_id().getPmtbProductType().getProductTypeId());
			}
			AmtbAcctStatus currentStatus = getCurrentStatus(this.daoHelper.getAccountDao().getStatuses(acct.getAccountNo()));
			if(currentStatus!=null){
				returnMap.put("childStatus", currentStatus.getAcctStatus());
			}
			returnMap.put("productSubscriptions", subscribed);
			if(acct.getCreatedDt()!=null){
				returnMap.put("createdDate", DateUtil.convertTimestampToStr(acct.getCreatedDt(), DateUtil.GLOBAL_DATE_FORMAT));
			}
			returnMap.put("acctNo", acct.getAccountNo());
			returnMap.put("acctName", acct.getAccountName());
			returnMap.put("acctCode", acct.getCode());
			returnMap.put("nameOnCard", acct.getNameOnCard());
			returnMap.put("creditLimit", acct.getCreditLimit());
			returnMap.put("invoiceFormat", acct.getInvoiceFormat());
			returnMap.put("invoiceSorting", acct.getInvoiceSorting());
			returnMap.put("outsourcePrinting", acct.getOutsourcePrintingFlag());
			returnMap.put("govtEInvFlag", acct.getGovtEInvoiceFlag());
			returnMap.put("businessUnit", acct.getMstbMasterTableByBusinessUnit());
			returnMap.put("overdueReminder", acct.getOverdueReminder());
			returnMap.put("recurring",acct.getRecurringFlag());
			returnMap.put("recurringChargeDay",acct.getRecurringChargeDay());

			if(acct.getInvoiceFormat() == null)
			{
				//2nd lvl is empty.. take 3lvl; grandparent
				if(acct.getAmtbAccount() != null && acct.getAmtbAccount().getInvoiceFormat() == null  && acct.getAmtbAccount().getAmtbAccount() != null)
				{	
					returnMap.put("parentRecurring",acct.getAmtbAccount().getAmtbAccount().getRecurringFlag());
					returnMap.put("parentRecurringChargeDay",acct.getAmtbAccount().getAmtbAccount().getRecurringChargeDay());
				}
				else if( acct.getAmtbAccount() != null)
				{ //take 2nd lvl
					returnMap.put("parentRecurring",acct.getAmtbAccount().getRecurringFlag());
					returnMap.put("parentRecurringChargeDay",acct.getAmtbAccount().getRecurringChargeDay());
				}
			}

			if(acct.getReminderEmail() != null){
				returnMap.put("reminderEmail", acct.getReminderEmail());
			}
			
			returnMap.put("eInvoiceEmailFlag", acct.geteInvoiceEmailFlag());
			returnMap.put("eInvoiceEmailZipFlag", acct.geteInvoiceEmailZipFlag());
			
			if(acct.geteInvoiceEmail() != null){
				returnMap.put("eInvoiceEmail", acct.geteInvoiceEmail());
			}
			
			if(acct.geteInvoiceEmailSubject() != null){
				returnMap.put("eInvoiceEmailSubject", acct.geteInvoiceEmailSubject());
			}
			
			returnMap.put("eInvoiceEmailAttachment", acct.geteInvoiceEmailAttachment());
			returnMap.put("eInvoiceEmailPage", acct.geteInvoiceEmailPage());
			
			if(acct.getCreditBalance()!=null){
				returnMap.put("creditBalance", StringUtil.bigDecimalToString(acct.getCreditBalance(), StringUtil.GLOBAL_DECIMAL_FORMAT));
			}
			if(acct.getPrintTaxInvoiceOnly() != null) {
				returnMap.put("printTaxInvoiceOnlyList", acct.getPrintTaxInvoiceOnly());
			}
			else
				returnMap.put("printTaxInvoiceOnlyList", "N");
			
			returnMap.put("lastUpdatedBy", acct.getUpdatedBy());
			if(acct.getUpdatedDt()!=null){
				returnMap.put("lastUpdatedDate", DateUtil.convertTimestampToStr(acct.getUpdatedDt(), DateUtil.GLOBAL_DATE_FORMAT));
				returnMap.put("lastUpdatedTime", DateUtil.convertTimestampToStr(acct.getUpdatedDt(), DateUtil.GLOBAL_TIME_FORMAT));
			}
			for(AmtbAcctMainContact mainContact : acct.getAmtbAcctMainContacts()){
				if(mainContact.getComp_id().getMainContactType().equals(NonConfigurableConstants.MAIN_CONTACT_TYPE_BILLING)){
					returnMap.put("mainBilling", mainContact.getAmtbContactPerson().getContactPersonNo());
				}
				if(mainContact.getComp_id().getMainContactType().equals(NonConfigurableConstants.MAIN_CONTACT_TYPE_SHIPPING)){
					returnMap.put("mainShipping", mainContact.getAmtbContactPerson().getContactPersonNo());
				}
			}
			AmtbAccount parentAcct = acct;
			while(parentAcct.getAmtbAccount()!=null){
				parentAcct = parentAcct.getAmtbAccount();
			}
			parentAcct = this.daoHelper.getAccountDao().retrieveAccount(parentAcct.getCustNo());
			currentStatus = getCurrentStatus(parentAcct.getAmtbAcctStatuses());
			returnMap.put("acctStatus", currentStatus.getAcctStatus());
			returnMap.put("acctType", parentAcct.getAmtbAcctType().getAcctType());
			
			if(acct.getPubbsFlag() != null)
				returnMap.put("pubbsFlag", acct.getPubbsFlag());
			if(acct.getFiFlag() != null)
				returnMap.put("fiFlag", acct.getFiFlag());
		}
		return returnMap;
	}
	
	@SuppressWarnings("unchecked")
	public HashMap updateCorpSubAccount(Map<String, Object> acctDetails) throws CniiInterfaceException{
		ArrayList<IttbCniiAcctReq> cniiList = new ArrayList<IttbCniiAcctReq>();
		HashMap result = new HashMap();
		String userId = (String)acctDetails.get("userId");
		AmtbAccount acct = this.daoHelper.getAccountDao().getAccount((Integer)acctDetails.get("acctNo"));
		// checking parent
		if(acctDetails.get("parentCode")!=null){
			// checking if different
			if(!acct.getAmtbAccount().getCode().equals(acctDetails.get("parentCode"))){
				AmtbAccount newParent = this.daoHelper.getAccountDao().getAccount((String)acctDetails.get("custNo"), NonConfigurableConstants.DIVISION_LEVEL, null, (String)acctDetails.get("parentCode"));
				acct.setAmtbAccount(newParent);
			}
		}
		// update account
		acct.setCode((String)acctDetails.get("acctCode"));
		
		//If name changed, update to cnii is required.
		//but in order not to send twice e.g. update of name and subscribing of PS at the same time,
		//another boolean attribute is introduced
		boolean nameChanged = false;
		boolean updateSent = false;
		if(!acct.getAccountName().equals(acctDetails.get("acctName"))) nameChanged = true;
		acct.setAccountName((String)acctDetails.get("acctName"));
		
		acct.setNameOnCard((String)acctDetails.get("nameOnCard"));
		acct.setCreditLimit(((BigDecimal)acctDetails.get("creditLimit")));
		if(acct.getInvoiceFormat()!=null && acctDetails.get("invoiceFormat")==null){
			this.daoHelper.getInvoiceDao().debtToBilliableParent((Integer)acctDetails.get("acctNo"), userId);
			this.daoHelper.getPaymentReceiptDao().updateReceiptToParent((Integer)acctDetails.get("acctNo"), userId);
		}
		acct.setInvoiceFormat((String)acctDetails.get("invoiceFormat"));
		acct.setInvoiceSorting((String)acctDetails.get("invoiceSorting"));
		// Govt eInvoice Enhancement
		acct.setGovtEInvoiceFlag((String)acctDetails.get("govtEInvFlag"));
		if(acctDetails.get("businessUnit") != null)
			acct.setMstbMasterTableByBusinessUnit(ConfigurableConstants.getMasterTable(ConfigurableConstants.GOVT_EINVOICE_BUSINESS_UNIT_MASTER_TYPE, (String)acctDetails.get("businessUnit")));
		else
			acct.setMstbMasterTableByBusinessUnit(null);
		
		acct.setPrintTaxInvoiceOnly((String)acctDetails.get("printTaxInvoiceOnly"));
		acct.setPubbsFlag((String)acctDetails.get("pubbsFlag"));
		acct.setFiFlag((String)acctDetails.get("fiFlag"));
		acct.setRecurringFlag((String) acctDetails.get("recurring"));
		
		if(acctDetails.get("recurringChargeDay") != null && ((String)acctDetails.get("recurring")).equals("Y"))
		{
			String recurringChargeDayString = (String)acctDetails.get("recurringChargeDay");
			acct.setRecurringChargeDay(Integer.parseInt(recurringChargeDayString.substring(0, recurringChargeDayString.length() - 2)));
		}
		else
			acct.setRecurringChargeDay(null);
		
		this.update(acct, userId);
		
		// update main contact
		Integer billingContact = (Integer)acctDetails.get("billingContact");
		if(billingContact!=null){
			boolean found = false;
			for (AmtbAcctMainContact mainContact : acct.getAmtbAcctMainContacts()) {
				if(mainContact.getComp_id().getMainContactType().equals(NonConfigurableConstants.MAIN_CONTACT_TYPE_BILLING)){
					found = true;
					mainContact.setAmtbContactPerson(this.daoHelper.getAccountDao().getContact(billingContact));
					this.update(mainContact, userId);
					mainContact.getAmtbContactPerson().getAmtbAcctMainContacts().clear();
				}
			}
			if(!found){
				AmtbAcctMainContact mainBilling = new AmtbAcctMainContact(new AmtbAcctMainContactPK(NonConfigurableConstants.MAIN_CONTACT_TYPE_BILLING, acct));
				mainBilling.setAmtbContactPerson(this.daoHelper.getAccountDao().getContact(billingContact));
				this.save(mainBilling);
			}
		}
		Integer shippingContact = (Integer)acctDetails.get("shippingContact");
		if(shippingContact!=null){
			boolean found = false;
			for (AmtbAcctMainContact mainContact : acct.getAmtbAcctMainContacts()) {
				if(mainContact.getComp_id().getMainContactType().equals(NonConfigurableConstants.MAIN_CONTACT_TYPE_SHIPPING)){
					found = true;
					mainContact.setAmtbContactPerson(this.daoHelper.getAccountDao().getContact(shippingContact));
					this.update(mainContact);
					break;
				}
			}
			if(!found){
				AmtbAcctMainContact mainShipping = new AmtbAcctMainContact(new AmtbAcctMainContactPK(NonConfigurableConstants.MAIN_CONTACT_TYPE_SHIPPING, acct));
				mainShipping.setAmtbContactPerson(this.daoHelper.getAccountDao().getContact(shippingContact));
				this.save(mainShipping);
			}
		}else{
			for (AmtbAcctMainContact mainContact : acct.getAmtbAcctMainContacts()) {
				if(mainContact.getComp_id().getMainContactType().equals(NonConfigurableConstants.MAIN_CONTACT_TYPE_SHIPPING)){
					acct.getAmtbAcctMainContacts().remove(mainContact);
					this.delete(mainContact);
					break;
				}
			}
		}
		// update product subscriptions
		List<String> prodSubscriptions = (List<String>)acctDetails.get("prodSubscriptions");
		AmtbAccount parentAcct = null;
		List<AmtbSubscProdReq> emailApproval = new ArrayList<AmtbSubscProdReq>();
		//checking product subscriptions that are added
		for(String prodSubscription : prodSubscriptions){
			boolean found = false;
			for(AmtbSubscTo subscription : acct.getAmtbSubscTos()){
				if(subscription.getComp_id().getPmtbProductType().getProductTypeId().equals(prodSubscription)){
					found = true;
					break;
				}
			}
			if(!found){
				if(parentAcct == null){
					parentAcct = this.daoHelper.getAccountDao().getAccount(acct.getAmtbAccount().getAccountNo());
				}
				for(AmtbSubscTo parentProdSubscription : parentAcct.getAmtbSubscTos()){
					PmtbProductType prodSubscribeProdType = parentProdSubscription.getComp_id().getPmtbProductType();
					if(prodSubscribeProdType.getProductTypeId().equals(prodSubscription)){
						
						AmtbSubscProdReq reqApproveSubscription = new AmtbSubscProdReq();
						
						reqApproveSubscription.setAmtbAccount(acct);
						reqApproveSubscription.setPmtbProductType(prodSubscribeProdType);

						reqApproveSubscription.setEffectiveDt(DateUtil.getCurrentTimestamp());
						reqApproveSubscription.setReqDt(DateUtil.getCurrentTimestamp());
						reqApproveSubscription.setReqBy(CommonWindow.getUserId());
						reqApproveSubscription.setAppStatus(NonConfigurableConstants.SUBSCRIPTION_APPROVE_STATUS_PENDING_APPROVED);
						reqApproveSubscription.setSubscAction(NonConfigurableConstants.SUBSCRIPTION_STATUS_SUBSCRIBE_DIV_DEPT);
						reqApproveSubscription.setRemarks("");
						
						if(parentProdSubscription.getMstbProdDiscMaster()!=null){
							reqApproveSubscription.setMstbProdDiscMaster(parentProdSubscription.getMstbProdDiscMaster());
						}
						if(parentProdSubscription.getLrtbRewardMaster()!=null){
							reqApproveSubscription.setLrtbRewardMaster(parentProdSubscription.getLrtbRewardMaster());
						}
						if(parentProdSubscription.getMstbSubscFeeMaster()!=null){
							reqApproveSubscription.setMstbSubscFeeMaster(parentProdSubscription.getMstbSubscFeeMaster());
						}
						if(parentProdSubscription.getMstbIssuanceFeeMaster()!=null){
							reqApproveSubscription.setMstbIssuanceFeeMaster(parentProdSubscription.getMstbIssuanceFeeMaster());
						}
					
						this.save(reqApproveSubscription);
						emailApproval.add(reqApproveSubscription);
						result.put("approve", "true");
//						AmtbSubscTo childProdSubscription = new AmtbSubscTo();
//						childProdSubscription.setComp_id(parentProdSubscription.getComp_id());
//						childProdSubscription.setLrtbRewardMaster(parentProdSubscription.getLrtbRewardMaster());
//						childProdSubscription.setMstbProdDiscMaster(parentProdSubscription.getMstbProdDiscMaster());
//						childProdSubscription.setMstbSubscFeeMaster(parentProdSubscription.getMstbSubscFeeMaster());
//						childProdSubscription.setMstbIssuanceFeeMaster(parentProdSubscription.getMstbIssuanceFeeMaster());
//						childProdSubscription.getComp_id().setAmtbAccount(acct);
//						childProdSubscription.setEffectiveDt(DateUtil.getCurrentTimestamp());
//						this.save(childProdSubscription);
//						Map<String, String> prop = (Map)SpringUtil.getBean("webserviceProperties");
//						if(prodSubscribeProdType.getProductTypeId().equals(NonConfigurableConstants.PREMIER_SERVICE) && NonConfigurableConstants.BOOLEAN_NO.equals((String) prop.get("ws.cnii.disable"))){
//							if(acct.getAmtbAccount()!=null){
//								AmtbAcctStatus parentStatus = getCurrentStatus(acct.getAmtbAccount().getAmtbAcctStatuses());
//								if(parentStatus!=null && !parentStatus.getAcctStatus().equals(NonConfigurableConstants.ACCOUNT_STATUS_PENDING_ACTIVATION)){
//									IttbCniiAcctReq cniiRequest = new IttbCniiAcctReq();
//									cniiRequest.setReqId(DateUtil.convertDateToStr(DateUtil.getCurrentDate(), DateUtil.GLOBAL_CNII_SEQ_FORMAT) + this.daoHelper.getAccountDao().getNextSequenceNo(Sequence.CNII_REQ_NO_SEQUENCE));
//									cniiRequest.setReqDate(DateUtil.getCurrentTimestamp());
//									cniiRequest.setEventType(NonConfigurableConstants.CNII_REQUEST_EVENT_TYPE_ACTIVATE);
//									cniiRequest.setReqStatus(NonConfigurableConstants.CNII_REQUEST_STATUS_PENDING);
//									cniiRequest.setValidFlg(NonConfigurableConstants.BOOLEAN_YES);
//									cniiRequest.setUpdatedBy(NonConfigurableConstants.CNII_REQUEST_UPDATED_BY_IBS);
//									StringBuffer acctId = new StringBuffer();
//									StringBuffer parentId = new StringBuffer();
//									AmtbAccount parentAcct2 = acct.getAmtbAccount();
//									if(parentAcct2.getCustNo()!=null && parentAcct2.getCustNo().length()!=0){
//										acctId.append(parentAcct2.getCustNo());
//										parentId.append(parentAcct2.getCustNo());
//									}else{
//										acctId.append(parentAcct2.getCode());
//										parentId.append(parentAcct2.getCode());
//										// now getting the parent
//										AmtbAccount parent = parentAcct2.getAmtbAccount();
//										if(parent!=null){// this shouldn't happen
//											while(parent.getCustNo()==null){
//												acctId.insert(0, parent.getCode());
//												parentId.insert(0, parent.getCode());
//												parent = this.daoHelper.getAccountDao().getParent(parent).getAmtbAccount();
//											}
//											acctId.insert(0, parent.getCustNo());
//											parentId.insert(0, parent.getCustNo());
//										}
//									}
//									acctId.append(acct.getCode());
//									cniiRequest.setAccountId(acctId.toString());
//									cniiRequest.setAccountCd(acct.getCode());
//									cniiRequest.setAccountNm(acct.getAccountName());
//									cniiRequest.setParentId(parentId.toString());
//									cniiRequest.setUpdatedDt(DateUtil.getCurrentTimestamp());
//									//this.save(cniiRequest);
//									cniiList.add(cniiRequest);
//									
//									updateSent = true;
//								}
//							}
//						}
						break;
					}
				}
			}
		}
		//checking product subscriptions that are removed
		List<AmtbSubscTo> deleted = new ArrayList<AmtbSubscTo>();
		List<String> deletedProdTypeId = new ArrayList<String>();
		for(AmtbSubscTo subscription : acct.getAmtbSubscTos()){
			boolean found = false;
			for(String prodSubscription : prodSubscriptions){
				if(prodSubscription.equals(subscription.getComp_id().getPmtbProductType().getProductTypeId())){
					found = true;
					break;
				}
			}
			if(!found){
				deletedProdTypeId.add(subscription.getComp_id().getPmtbProductType().getProductTypeId());
				deleted.add(subscription);
			}
		}
		if(!deleted.isEmpty()){
			acct.getAmtbSubscTos().clear();
			for(AmtbSubscTo delete : deleted){
				
				PmtbProductType deletedProdType = delete.getComp_id().getPmtbProductType();
				
				AmtbSubscProdReq reqApproveSubscription = new AmtbSubscProdReq();
				
				reqApproveSubscription.setAmtbAccount(acct);
				reqApproveSubscription.setPmtbProductType(deletedProdType);

				reqApproveSubscription.setEffectiveDt(DateUtil.getCurrentTimestamp());
				reqApproveSubscription.setReqDt(DateUtil.getCurrentTimestamp());
				reqApproveSubscription.setReqBy(CommonWindow.getUserId());
				reqApproveSubscription.setAppStatus(NonConfigurableConstants.SUBSCRIPTION_APPROVE_STATUS_PENDING_APPROVED);
				reqApproveSubscription.setSubscAction(NonConfigurableConstants.SUBSCRIPTION_STATUS_UNSUBSCRIBE_DIV_DEPT);
				reqApproveSubscription.setRemarks("");

				this.save(reqApproveSubscription);	
				emailApproval.add(reqApproveSubscription);
				
				result.put("approve", "true");
//				this.delete(delete);
//				
//				//to check if premier service is deleted.
//				//if so, update to cnii
//				PmtbProductType deletedProdType = delete.getComp_id().getPmtbProductType();
//				Map<String, String> prop = (Map)SpringUtil.getBean("webserviceProperties");
//				if(deletedProdType.getProductTypeId().equals(NonConfigurableConstants.PREMIER_SERVICE) && NonConfigurableConstants.BOOLEAN_NO.equals((String) prop.get("ws.cnii.disable"))){
//					
//					AmtbAccount corpAcct = null;
//					//Assuming the current level is dept level
//					if(acct.getAmtbAccount()!=null && acct.getAmtbAccount().getAmtbAccount()!=null)
//						corpAcct = acct.getAmtbAccount().getAmtbAccount();
//					//The rest of the level should be division
//					else
//						corpAcct = acct.getAmtbAccount();
//					
//					AmtbAcctStatus corpStatus = getCurrentStatus(corpAcct.getAmtbAcctStatuses());
//					if(corpStatus!=null && !corpStatus.getAcctStatus().equals(NonConfigurableConstants.ACCOUNT_STATUS_PENDING_ACTIVATION)){
//						IttbCniiAcctReq cniiRequest = new IttbCniiAcctReq();
//						cniiRequest.setReqId(DateUtil.convertDateToStr(DateUtil.getCurrentDate(), DateUtil.GLOBAL_CNII_SEQ_FORMAT) + this.daoHelper.getAccountDao().getNextSequenceNo(Sequence.CNII_REQ_NO_SEQUENCE));
//						cniiRequest.setReqDate(DateUtil.getCurrentTimestamp());
//						cniiRequest.setEventType(NonConfigurableConstants.CNII_REQUEST_EVENT_TYPE_ACTIVATE);
//						cniiRequest.setReqStatus(NonConfigurableConstants.CNII_REQUEST_STATUS_PENDING);
//						cniiRequest.setTerminateDt(DateUtil.getCurrentDate());
//						cniiRequest.setValidFlg(NonConfigurableConstants.BOOLEAN_YES);
//						cniiRequest.setUpdatedBy(NonConfigurableConstants.CNII_REQUEST_UPDATED_BY_IBS);
//						StringBuffer acctId = new StringBuffer();
//						StringBuffer parentId = new StringBuffer();
//						AmtbAccount parentAcct2 = acct.getAmtbAccount();
//						if(parentAcct2.getCustNo()!=null && parentAcct2.getCustNo().length()!=0){
//							acctId.append(parentAcct2.getCustNo());
//							parentId.append(parentAcct2.getCustNo());
//						}else{
//							acctId.append(parentAcct2.getCode());
//							parentId.append(parentAcct2.getCode());
//							// now getting the parent
//							AmtbAccount parent = parentAcct2.getAmtbAccount();
//							if(parent!=null){// this shouldn't happen
//								while(parent.getCustNo()==null){
//									acctId.insert(0, parent.getCode());
//									parentId.insert(0, parent.getCode());
//									parent = this.daoHelper.getAccountDao().getParent(parent).getAmtbAccount();
//								}
//								acctId.insert(0, parent.getCustNo());
//								parentId.insert(0, parent.getCustNo());
//							}
//						}
//						acctId.append(acct.getCode());
//						cniiRequest.setAccountId(acctId.toString());
//						cniiRequest.setAccountCd(acct.getCode());
//						cniiRequest.setAccountNm(acct.getAccountName());
//						cniiRequest.setParentId(parentId.toString());
//						cniiRequest.setUpdatedDt(DateUtil.getCurrentTimestamp());
//						//this.save(cniiRequest);
//						cniiList.add(cniiRequest);
//					}
//				}
//			}
		}
		// now removing all product subscriptions that are in children of account
//		if(!deletedProdTypeId.isEmpty()){
//			List<AmtbAccount> childrenAccts = this.daoHelper.getAccountDao().getChildrenAccountsWithSubscriptionsAndMainContacts(acct.getAccountNo());
//			for(AmtbAccount childAcct : childrenAccts){
//				List<AmtbSubscTo> childDeleted = new ArrayList<AmtbSubscTo>();
//				for(AmtbSubscTo childSubscribed : childAcct.getAmtbSubscTos()){
//					if(deletedProdTypeId.contains(childSubscribed.getComp_id().getPmtbProductType().getProductTypeId())){
//						childDeleted.add(childSubscribed);
//						if(childDeleted.size()==deletedProdTypeId.size()){
//							break;
//						}
//					}
//				}
//				childAcct.getAmtbSubscTos().clear();
//				for(AmtbSubscTo childDelete : childDeleted){
//					this.delete(childDelete);
//					
//					//to check if premier service is deleted.
//					//if so, update to cnii
//					PmtbProductType deletedProdType = childDelete.getComp_id().getPmtbProductType();
//					Map<String, String> prop = (Map)SpringUtil.getBean("webserviceProperties");
//					if(deletedProdType.getProductTypeId().equals(NonConfigurableConstants.PREMIER_SERVICE) && NonConfigurableConstants.BOOLEAN_NO.equals((String) prop.get("ws.cnii.disable"))){
//						AmtbAccount corpAcct = childAcct.getAmtbAccount().getAmtbAccount();
//						AmtbAcctStatus corpStatus = getCurrentStatus(corpAcct.getAmtbAcctStatuses());
//						if(corpStatus!=null && !corpStatus.getAcctStatus().equals(NonConfigurableConstants.ACCOUNT_STATUS_PENDING_ACTIVATION)){
//							IttbCniiAcctReq cniiRequest = new IttbCniiAcctReq();
//							cniiRequest.setReqId(DateUtil.convertDateToStr(DateUtil.getCurrentDate(), DateUtil.GLOBAL_CNII_SEQ_FORMAT) + this.daoHelper.getAccountDao().getNextSequenceNo(Sequence.CNII_REQ_NO_SEQUENCE));
//							cniiRequest.setReqDate(DateUtil.getCurrentTimestamp());
//							cniiRequest.setEventType(NonConfigurableConstants.CNII_REQUEST_EVENT_TYPE_ACTIVATE);
//							cniiRequest.setReqStatus(NonConfigurableConstants.CNII_REQUEST_STATUS_PENDING);
//							cniiRequest.setTerminateDt(DateUtil.getCurrentDate());
//							cniiRequest.setValidFlg(NonConfigurableConstants.BOOLEAN_YES);
//							cniiRequest.setUpdatedBy(NonConfigurableConstants.CNII_REQUEST_UPDATED_BY_IBS);
//							StringBuffer acctId = new StringBuffer();
//							StringBuffer parentId = new StringBuffer();
//							//AmtbAccount parentAcct2 = acct.getAmtbAccount();
//							// since child = dept, parent should be div
//							AmtbAccount parentAcct2 = acct;
//							if(parentAcct2.getCustNo()!=null && parentAcct2.getCustNo().length()!=0){
//								acctId.append(parentAcct2.getCustNo());
//								parentId.append(parentAcct2.getCustNo());
//							}else{
//								acctId.append(parentAcct2.getCode());
//								parentId.append(parentAcct2.getCode());
//								// now getting the parent
//								AmtbAccount parent = parentAcct2.getAmtbAccount();
//								if(parent!=null){// this shouldn't happen
//									while(parent.getCustNo()==null){
//										acctId.insert(0, parent.getCode());
//										parentId.insert(0, parent.getCode());
//										parent = this.daoHelper.getAccountDao().getParent(parent).getAmtbAccount();
//									}
//									acctId.insert(0, parent.getCustNo());
//									parentId.insert(0, parent.getCustNo());
//								}
//							}
//							acctId.append(childAcct.getCode());
//							cniiRequest.setAccountId(acctId.toString());
//							cniiRequest.setAccountCd(childAcct.getCode());
//							cniiRequest.setAccountNm(childAcct.getAccountName());
//							cniiRequest.setParentId(parentId.toString());
//							cniiRequest.setUpdatedDt(DateUtil.getCurrentTimestamp());
//							//this.save(cniiRequest);
//							cniiList.add(cniiRequest);
//						}
//					}
//				}
//			}
		}
		List<String> cardLessProductList = new ArrayList<String>();
		List<PmtbProductType> cardlessProducts = this.businessHelper.getProductTypeBusiness().getCardlessProductType();
		for(PmtbProductType cardlessProduct : cardlessProducts) {
			cardLessProductList.add(cardlessProduct.getProductTypeId().trim());
		}
		//if name changed and yet after whole process there is not update of cnii,
		//then the below section will send the update just for change of name for this account
		if(nameChanged && !updateSent){
			Map<String, String> prop = (Map)SpringUtil.getBean("webserviceProperties");
			for(AmtbSubscTo prodSubscription : acct.getAmtbSubscTos()){
				PmtbProductType prodSubscribeProdType = prodSubscription.getComp_id().getPmtbProductType();
//				if(prodSubscribeProdType.getProductTypeId().equals(NonConfigurableConstants.PREMIER_SERVICE) && NonConfigurableConstants.BOOLEAN_NO.equals((String) prop.get("ws.cnii.disable"))){
				if(cardLessProductList.contains(prodSubscribeProdType.getProductTypeId()) && NonConfigurableConstants.BOOLEAN_NO.equals((String) prop.get("ws.cnii.disable"))){
					AmtbAcctStatus parentStatus = getCurrentStatus(acct.getAmtbAccount().getAmtbAcctStatuses());
					if(parentStatus!=null && !parentStatus.getAcctStatus().equals(NonConfigurableConstants.ACCOUNT_STATUS_PENDING_ACTIVATION)){
						IttbCniiAcctReq cniiRequest = new IttbCniiAcctReq();
						cniiRequest.setReqId(DateUtil.convertDateToStr(DateUtil.getCurrentDate(), DateUtil.GLOBAL_CNII_SEQ_FORMAT) + this.daoHelper.getAccountDao().getNextSequenceNo(Sequence.CNII_REQ_NO_SEQUENCE));
						cniiRequest.setReqDate(DateUtil.getCurrentTimestamp());
						cniiRequest.setEventType(NonConfigurableConstants.CNII_REQUEST_EVENT_TYPE_ACTIVATE);
						cniiRequest.setReqStatus(NonConfigurableConstants.CNII_REQUEST_STATUS_PENDING);
						cniiRequest.setValidFlg(NonConfigurableConstants.BOOLEAN_YES);
						cniiRequest.setUpdatedBy(NonConfigurableConstants.CNII_REQUEST_UPDATED_BY_IBS);
						StringBuffer acctId = new StringBuffer();
						StringBuffer parentId = new StringBuffer();
						AmtbAccount parentAcct2 = acct.getAmtbAccount();
						if(parentAcct2.getCustNo()!=null && parentAcct2.getCustNo().length()!=0){
							acctId.append(parentAcct2.getCustNo());
							parentId.append(parentAcct2.getCustNo());
						}else{
//							acctId.append(parentAcct2.getCode());
//							parentId.append(parentAcct2.getCode());
							// now getting the parent
//							AmtbAccount parent = parentAcct2.getAmtbAccount();
							AmtbAccount parent = acct.getAmtbAccount();
							if(parent!=null){// this shouldn't happen
								while(parent.getCustNo()==null){
									acctId.insert(0, parent.getCode());
									parentId.insert(0, parent.getCode());
									parent = this.daoHelper.getAccountDao().getParent(parent).getAmtbAccount();
								}
								acctId.insert(0, parent.getCustNo());
								parentId.insert(0, parent.getCustNo());
							}
						}
						acctId.append(acct.getCode());
						cniiRequest.setAccountId(acctId.toString());
						cniiRequest.setAccountCd(acct.getCode());
						cniiRequest.setAccountNm(acct.getAccountName());
						cniiRequest.setParentId(parentId.toString());
						cniiRequest.setUpdatedDt(DateUtil.getCurrentTimestamp());
						//this.save(cniiRequest);
						cniiList.add(cniiRequest);
					}
					break;
				}
			}
		}
		
		if(emailApproval.size() > 0)
			sendSubscriptionApprovalEmail(emailApproval.get(0));
		
		if(!cniiList.isEmpty())
		{
//			IBSCNIIUpdateAcctClient.send(cniiList);
			try {
			//29 Aug 2018 CNII Acct Sync
				String msg = updateCniiAcctSyncProcedure(cniiList);
				logger.info("CNII Acct Sync log : "+msg);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				throw new CniiInterfaceException("CNII Interface Acct Sync Error - "+e);
			}
		}
		result.put("result", "true");
		return result;
	}
	public Map<String, List<Map<String, Object>>> getBillingDetails(String custNo){
		//Old calling of getBillingDetails will route to getBillingDetailsNew with flag to call the CurrentAndFutureBillingDetails SQL
		//certain spoil one will call getBillingDetailsNew straight with false flag, to call the older SQL ..
		//will be fix 
		Map<String, List<Map<String, Object>>> returnMap = new HashMap<String, List<Map<String, Object>>>();
		returnMap = getBillingDetailsNew(custNo, true);
		
		return returnMap;
	}
	public Map<String, List<Map<String, Object>>> getTopRowOfBillingDetails(String custNo){
		//Old calling of getBillingDetails will route to getBillingDetailsNew with flag to call the CurrentAndFutureBillingDetails SQL
		//certain spoil one will call getBillingDetailsNew straight with false flag, to call the older SQL ..
		//will be fix 
		Map<String, List<Map<String, Object>>> returnMap = new HashMap<String, List<Map<String, Object>>>();
		returnMap = getTopRowOfBillingDetailsNew(custNo, true); 
		
		return returnMap;
	}
	public Map<String, List<Map<String, Object>>> getBillingDetailsNew(String custNo, boolean flag){
		AmtbAccount acct = new AmtbAccount();
		
		if(!flag)
			acct = this.daoHelper.getAccountDao().getBillingDetails(custNo);
		else
			acct = this.daoHelper.getAccountDao().getCurrentAndFutureBillingDetails(custNo);
		
		Map<String, List<Map<String, Object>>> returnMap = new HashMap<String, List<Map<String, Object>>>();
		// getting billing cycle, volume discount, and base admin fee
		Map<Date, Map<String, Object>> billingDetails = new TreeMap<Date, Map<String, Object>>(new Comparator<Date>(){
			public int compare(Date o1, Date o2) {
				return o1.compareTo(o2);
			}
		});
		// check for Null Account object (no Billing Details)
		if(acct != null) {
			for (AmtbAcctAdminFee adminFee : acct.getAmtbAcctAdminFees()) {
				Map<String, Object> billingDetail = billingDetails.get(adminFee.getEffectiveDate());
				if(billingDetail==null){
					billingDetail = new HashMap<String, Object>();
					billingDetails.put(adminFee.getEffectiveDate(), billingDetail);
				}
				billingDetail.put("adminFee", adminFee);
			}
			for (AmtbAcctBillCycle billingCycle : acct.getAmtbAcctBillCycles()) {
				Map<String, Object> billingDetail = billingDetails.get(billingCycle.getEffectiveDate());
				if(billingDetail==null){
					billingDetail = new HashMap<String, Object>();
					billingDetails.put(billingCycle.getEffectiveDate(), billingDetail);
				}
				billingDetail.put("billingCycle", billingCycle);
			}
			for (AmtbAcctVolDisc volumeDiscount : acct.getAmtbAcctVolDiscs()) {
				Map<String, Object> billingDetail = billingDetails.get(volumeDiscount.getEffectiveDate());
				if(billingDetail==null){
					billingDetail = new HashMap<String, Object>();
					billingDetails.put(volumeDiscount.getEffectiveDate(), billingDetail);
				}
				billingDetail.put("volumeDiscount", volumeDiscount);
			}
			List<Map<String, Object>> returnBillingList = new LinkedList<Map<String, Object>>();
			for(Date effectiveDate : billingDetails.keySet()){
				Map<String, Object> billingDetail = billingDetails.get(effectiveDate);
				Map<String, Object> returnBillingMap = new HashMap<String, Object>();
				AmtbAcctAdminFee adminFee = (AmtbAcctAdminFee)billingDetail.get("adminFee");
				if(adminFee!=null){
					returnBillingMap.put("adminFee", adminFee.getMstbAdminFeeMaster().getAdminFeePlanNo());
				}
				AmtbAcctBillCycle billingCycle = (AmtbAcctBillCycle)billingDetail.get("billingCycle");
				if(billingCycle!=null){
					returnBillingMap.put("billingCycle", billingCycle.getBillingCycle());
				}
				AmtbAcctVolDisc volumeDiscount = (AmtbAcctVolDisc)billingDetail.get("volumeDiscount");
				if(volumeDiscount!=null && volumeDiscount.getMstbVolDiscMaster()!=null){
					returnBillingMap.put("volumeDiscount", volumeDiscount.getMstbVolDiscMaster().getVolumeDiscountPlanNo());
				}
				returnBillingMap.put("effectiveDate", effectiveDate);
				returnBillingList.add(returnBillingMap);
			}
			returnMap.put("billing", returnBillingList);
			// getting credit term
			Set<AmtbAcctCredTerm> creditTerms = new TreeSet<AmtbAcctCredTerm>(new Comparator<AmtbAcctCredTerm>(){
				public int compare(AmtbAcctCredTerm o1, AmtbAcctCredTerm o2) {
					return o1.getEffectiveDate().compareTo(o2.getEffectiveDate());
				}
			});
			creditTerms.addAll(acct.getAmtbAcctCredTerms());
			List<Map<String, Object>> returnCreditTermList = new LinkedList<Map<String, Object>>();
			for(AmtbAcctCredTerm creditTerm : creditTerms){
				Map<String, Object> returnCreditTermMap = new HashMap<String, Object>();
				returnCreditTermMap.put("creditTerm", creditTerm.getMstbCreditTermMaster().getCreditTermPlanNo());
				returnCreditTermMap.put("effectiveDate", creditTerm.getEffectiveDate());
				returnCreditTermList.add(returnCreditTermMap);
			}
			returnMap.put("creditTerm", returnCreditTermList);
			// getting early payment
			Set<AmtbAcctEarlyPymt> earlyPymts = new TreeSet<AmtbAcctEarlyPymt>(new Comparator<AmtbAcctEarlyPymt>(){
				public int compare(AmtbAcctEarlyPymt o1, AmtbAcctEarlyPymt o2) {
					return o1.getEffectiveDate().compareTo(o2.getEffectiveDate());
				}
			});
			earlyPymts.addAll(acct.getAmtbAcctEarlyPymts());
			List<Map<String, Object>> returnEarlyPymtList = new LinkedList<Map<String, Object>>();
			for(AmtbAcctEarlyPymt earlyPymt : earlyPymts){
				Map<String, Object> returnEarlyPymtMap = new HashMap<String, Object>();
				if(earlyPymt.getMstbEarlyPaymentMaster()!=null){
					returnEarlyPymtMap.put("earlyPymt", earlyPymt.getMstbEarlyPaymentMaster().getEarlyPaymentPlanNo());
				}else{
					returnEarlyPymtMap.put("earlyPymt", null);
				}
				returnEarlyPymtMap.put("effectiveDate", earlyPymt.getEffectiveDate());
				returnEarlyPymtList.add(returnEarlyPymtMap);
			}
			returnMap.put("earlyPymt", returnEarlyPymtList);
			// getting late payment
			Set<AmtbAcctLatePymt> latePymts = new TreeSet<AmtbAcctLatePymt>(new Comparator<AmtbAcctLatePymt>(){
				public int compare(AmtbAcctLatePymt o1, AmtbAcctLatePymt o2) {
					return o1.getEffectiveDate().compareTo(o2.getEffectiveDate());
				}
			});
			latePymts.addAll(acct.getAmtbAcctLatePymts());
			List<Map<String, Object>> returnLatePymtList = new LinkedList<Map<String, Object>>();
			for(AmtbAcctLatePymt latePymt : latePymts){
				Map<String, Object> returnLatePymtMap = new HashMap<String, Object>();
				returnLatePymtMap.put("latePymt", latePymt.getMstbLatePaymentMaster().getLatePaymentPlanNo());
				returnLatePymtMap.put("effectiveDate", latePymt.getEffectiveDate());
				returnLatePymtList.add(returnLatePymtMap);
			}
			returnMap.put("latePymt", returnLatePymtList);
			Set<AmtbAcctPromotion> promotions = new TreeSet<AmtbAcctPromotion>(new Comparator<AmtbAcctPromotion>(){
				public int compare(AmtbAcctPromotion o1, AmtbAcctPromotion o2) {
					return o1.getEffectiveDateFrom().equals(o2.getEffectiveDateFrom()) ? o1.toString().compareTo(o2.toString()) : o1.getEffectiveDateFrom().compareTo(o2.getEffectiveDateFrom());
				}
			});
			promotions.addAll(acct.getAmtbAcctPromotions());
			List<Map<String, Object>> returnPromotionList = new LinkedList<Map<String, Object>>();
			for(AmtbAcctPromotion promotion : promotions){
				Map<String, Object> returnPromotionMap = new HashMap<String, Object>();
				returnPromotionMap.put("promotion", promotion.getMstbPromotion().getPromoNo());
				returnPromotionMap.put("effectiveDateFrom", promotion.getEffectiveDateFrom());
				returnPromotionMap.put("effectiveDateTo", promotion.getEffectiveDateTo());
				returnPromotionMap.put("acctPromotionNo", promotion.getAcctPromotionNo());
				returnPromotionList.add(returnPromotionMap);
			}
			returnMap.put("promotion", returnPromotionList);
			// getting bank information
			List<Map<String, Object>> bankInfoList = new LinkedList<Map<String, Object>>();
			Map<String, Object> returnBankInfoMap = new HashMap<String, Object>();
			if(acct.getMstbBankMaster()!=null){
				returnBankInfoMap.put("bankMaster", acct.getMstbBankMaster().getBankMasterNo());
			}
			if(acct.getMstbBranchMaster()!=null){
				returnBankInfoMap.put("branchMaster", acct.getMstbBranchMaster().getBranchMasterNo());
			}
			if(acct.getMstbMasterTableByDefaultPaymentMode()!=null){
				returnBankInfoMap.put("defaultPaymentMode", acct.getMstbMasterTableByDefaultPaymentMode().getMasterCode());
			}
			returnBankInfoMap.put("bankAcctNo", acct.getBankAcctNo());
			bankInfoList.add(returnBankInfoMap);
			returnMap.put("bankInfo", bankInfoList);
		}
		return returnMap;
	}

	public Map<String, List<Map<String, Object>>> getTopRowOfBillingDetailsNew(String custNo, boolean flag){
		AmtbAccount acct = new AmtbAccount();
		
		// Get all billing records, then check if > currentDate, keep at least one record if all are historical.
		if(flag)
			acct = this.daoHelper.getAccountDao().getBillingDetails(custNo);
		else
			acct = this.daoHelper.getAccountDao().getCurrentAndFutureBillingDetails(custNo);
		
		Map<String, List<Map<String, Object>>> returnMap = new HashMap<String, List<Map<String, Object>>>();
		// getting billing cycle, volume discount, and base admin fee
		Map<Date, Map<String, Object>> billingDetails = new TreeMap<Date, Map<String, Object>>(new Comparator<Date>(){
			public int compare(Date o1, Date o2) {
				return o2.compareTo(o1);
			}
		});
		// check for Null Account object (no Billing Details)
		if(acct != null) {
			for (AmtbAcctAdminFee adminFee : acct.getAmtbAcctAdminFees()) {
				Map<String, Object> billingDetail = billingDetails.get(adminFee.getEffectiveDate());
				if(billingDetail==null){
					billingDetail = new HashMap<String, Object>();
					billingDetails.put(adminFee.getEffectiveDate(), billingDetail);
				}
				billingDetail.put("adminFee", adminFee);
			}
			for (AmtbAcctBillCycle billingCycle : acct.getAmtbAcctBillCycles()) {
				Map<String, Object> billingDetail = billingDetails.get(billingCycle.getEffectiveDate());
				if(billingDetail==null){
					billingDetail = new HashMap<String, Object>();
					billingDetails.put(billingCycle.getEffectiveDate(), billingDetail);
				}
				billingDetail.put("billingCycle", billingCycle);
			}
			for (AmtbAcctVolDisc volumeDiscount : acct.getAmtbAcctVolDiscs()) {
				Map<String, Object> billingDetail = billingDetails.get(volumeDiscount.getEffectiveDate());
				if(billingDetail==null){
					billingDetail = new HashMap<String, Object>();
					billingDetails.put(volumeDiscount.getEffectiveDate(), billingDetail);
				}
				billingDetail.put("volumeDiscount", volumeDiscount);
			}
			List<Map<String, Object>> returnBillingList = new LinkedList<Map<String, Object>>();
			
			int recordCounter = 0;
			for(Date effectiveDate : billingDetails.keySet()){
				// check if effectiveDate is before today
				if (effectiveDate.before(new Date())) {
					// if effectiveDate is before Today, check recordCounter, must at least insert 1 record;
					if(recordCounter > 0) {
						continue;
					}
					recordCounter++;
				}
				Map<String, Object> billingDetail = billingDetails.get(effectiveDate);
				Map<String, Object> returnBillingMap = new HashMap<String, Object>();
				AmtbAcctAdminFee adminFee = (AmtbAcctAdminFee)billingDetail.get("adminFee");
				if(adminFee!=null){
					returnBillingMap.put("adminFee", adminFee.getMstbAdminFeeMaster().getAdminFeePlanNo());
				}
				AmtbAcctBillCycle billingCycle = (AmtbAcctBillCycle)billingDetail.get("billingCycle");
				if(billingCycle!=null){
					returnBillingMap.put("billingCycle", billingCycle.getBillingCycle());
				}
				AmtbAcctVolDisc volumeDiscount = (AmtbAcctVolDisc)billingDetail.get("volumeDiscount");
				if(volumeDiscount!=null && volumeDiscount.getMstbVolDiscMaster()!=null){
					returnBillingMap.put("volumeDiscount", volumeDiscount.getMstbVolDiscMaster().getVolumeDiscountPlanNo());
				}
				returnBillingMap.put("effectiveDate", effectiveDate);
				returnBillingList.add(returnBillingMap);
			}
			returnMap.put("billing", Lists.reverse(returnBillingList)); //pagination tab will show descending.
			// getting credit term
			Set<AmtbAcctCredTerm> creditTerms = new TreeSet<AmtbAcctCredTerm>(new Comparator<AmtbAcctCredTerm>(){
				public int compare(AmtbAcctCredTerm o1, AmtbAcctCredTerm o2) {
					return o2.getEffectiveDate().compareTo(o1.getEffectiveDate());
				}
			});
			creditTerms.addAll(acct.getAmtbAcctCredTerms());
			List<Map<String, Object>> returnCreditTermList = new LinkedList<Map<String, Object>>();
			
			int recordCounterCreditTerm = 0;
			for(AmtbAcctCredTerm creditTerm : creditTerms){
				if (creditTerm.getEffectiveDate().before(new Date())) {
					// if effectiveDate is before Today, check recordCounter, must at least insert 1 record;
					if(recordCounterCreditTerm > 0) {
						continue;
					}
					recordCounterCreditTerm++;
				}
				Map<String, Object> returnCreditTermMap = new HashMap<String, Object>();
				returnCreditTermMap.put("creditTerm", creditTerm.getMstbCreditTermMaster().getCreditTermPlanNo());
				returnCreditTermMap.put("effectiveDate", creditTerm.getEffectiveDate());
				returnCreditTermList.add(returnCreditTermMap);
			}
			returnMap.put("creditTerm", Lists.reverse(returnCreditTermList));  //pagination tab will show descending.
			// getting early payment
			Set<AmtbAcctEarlyPymt> earlyPymts = new TreeSet<AmtbAcctEarlyPymt>(new Comparator<AmtbAcctEarlyPymt>(){
				public int compare(AmtbAcctEarlyPymt o1, AmtbAcctEarlyPymt o2) {
					return o2.getEffectiveDate().compareTo(o1.getEffectiveDate());
				}
			});
			earlyPymts.addAll(acct.getAmtbAcctEarlyPymts());
			List<Map<String, Object>> returnEarlyPymtList = new LinkedList<Map<String, Object>>();

			int recordCounterEarlyPymt = 0;

			for(AmtbAcctEarlyPymt earlyPymt : earlyPymts){
				if (earlyPymt.getEffectiveDate().before(new Date())) {
					// if effectiveDate is before Today, check recordCounter, must at least insert 1 record;
					if(recordCounterEarlyPymt > 0) {
						continue;
					}
					recordCounterEarlyPymt++;
				}
				Map<String, Object> returnEarlyPymtMap = new HashMap<String, Object>();
				if(earlyPymt.getMstbEarlyPaymentMaster()!=null){
					returnEarlyPymtMap.put("earlyPymt", earlyPymt.getMstbEarlyPaymentMaster().getEarlyPaymentPlanNo());
				}else{
					returnEarlyPymtMap.put("earlyPymt", null);
				}
				returnEarlyPymtMap.put("effectiveDate", earlyPymt.getEffectiveDate());
				returnEarlyPymtList.add(returnEarlyPymtMap);
			}
			returnMap.put("earlyPymt", Lists.reverse(returnEarlyPymtList)); //pagination tab will show descending.
			
			// getting late payment
			Set<AmtbAcctLatePymt> latePymts = new TreeSet<AmtbAcctLatePymt>(new Comparator<AmtbAcctLatePymt>(){
				public int compare(AmtbAcctLatePymt o1, AmtbAcctLatePymt o2) {
					return o2.getEffectiveDate().compareTo(o1.getEffectiveDate());
				}
			});
			latePymts.addAll(acct.getAmtbAcctLatePymts());
			List<Map<String, Object>> returnLatePymtList = new LinkedList<Map<String, Object>>();

			int recordCounterLatePymt = 0;
			for(AmtbAcctLatePymt latePymt : latePymts){
				if (latePymt.getEffectiveDate().before(new Date())) {
					// if effectiveDate is before Today, check recordCounter, must at least insert 1 record;
					if(recordCounterLatePymt > 0) {
						continue;
					}
					recordCounterLatePymt++;
				}
				Map<String, Object> returnLatePymtMap = new HashMap<String, Object>();
				returnLatePymtMap.put("latePymt", latePymt.getMstbLatePaymentMaster().getLatePaymentPlanNo());
				returnLatePymtMap.put("effectiveDate", latePymt.getEffectiveDate());
				returnLatePymtList.add(returnLatePymtMap);
			}
			returnMap.put("latePymt", Lists.reverse(returnLatePymtList));  //pagination tab will show descending.
			
			Set<AmtbAcctPromotion> promotions = new TreeSet<AmtbAcctPromotion>(new Comparator<AmtbAcctPromotion>(){
				public int compare(AmtbAcctPromotion o1, AmtbAcctPromotion o2) {
					return o2.getEffectiveDateFrom().equals(o1.getEffectiveDateFrom()) ? o2.toString().compareTo(o1.toString()) : o2.getEffectiveDateFrom().compareTo(o1.getEffectiveDateFrom());
				}
			});
			promotions.addAll(acct.getAmtbAcctPromotions());
			List<Map<String, Object>> returnPromotionList = new LinkedList<Map<String, Object>>();

			int recordCounterPromotion = 0;
			for(AmtbAcctPromotion promotion : promotions){
				if (promotion.getEffectiveDateFrom().before(new Date())) {
					// if effectiveDate is before Today, check recordCounter, must at least insert 1 record;
					if(recordCounterPromotion > 0) {
						continue;
					}
					recordCounterPromotion++;
				}
				Map<String, Object> returnPromotionMap = new HashMap<String, Object>();
				returnPromotionMap.put("promotion", promotion.getMstbPromotion().getPromoNo());
				returnPromotionMap.put("effectiveDateFrom", promotion.getEffectiveDateFrom());
				returnPromotionMap.put("effectiveDateTo", promotion.getEffectiveDateTo());
				returnPromotionMap.put("acctPromotionNo", promotion.getAcctPromotionNo());
				returnPromotionList.add(returnPromotionMap);
			}
			returnMap.put("promotion", Lists.reverse(returnPromotionList));  //pagination tab will show descending.
			// getting bank information
			List<Map<String, Object>> bankInfoList = new LinkedList<Map<String, Object>>();
			Map<String, Object> returnBankInfoMap = new HashMap<String, Object>();
			if(acct.getMstbBankMaster()!=null){
				returnBankInfoMap.put("bankMaster", acct.getMstbBankMaster().getBankMasterNo());
			}
			if(acct.getMstbBranchMaster()!=null){
				returnBankInfoMap.put("branchMaster", acct.getMstbBranchMaster().getBranchMasterNo());
			}
			if(acct.getMstbMasterTableByDefaultPaymentMode()!=null){
				returnBankInfoMap.put("defaultPaymentMode", acct.getMstbMasterTableByDefaultPaymentMode().getMasterCode());
			}
			returnBankInfoMap.put("bankAcctNo", acct.getBankAcctNo());
			bankInfoList.add(returnBankInfoMap);
			returnMap.put("bankInfo", bankInfoList);
		}
		return returnMap;
	}
	public Map<String, List<Map<String, Object>>> getBillingCycleHistoricalDetails(String custNo){
		AmtbAccount acct = this.daoHelper.getAccountDao().getBillingCycleHistoricalDetails(custNo);
		Map<String, List<Map<String, Object>>> returnMap = new HashMap<String, List<Map<String, Object>>>();
		// getting billing cycle, volume discount, and base admin fee
		Map<Date, Map<String, Object>> billingDetails = new TreeMap<Date, Map<String, Object>>(new Comparator<Date>(){
			public int compare(Date o1, Date o2) {
				return o2.compareTo(o1);
			}
		});
		
		if(acct != null) {
			for (AmtbAcctAdminFee adminFee : acct.getAmtbAcctAdminFees()) {
				Map<String, Object> billingDetail = billingDetails.get(adminFee.getEffectiveDate());
				if(billingDetail==null){
					billingDetail = new HashMap<String, Object>();
					billingDetails.put(adminFee.getEffectiveDate(), billingDetail);
				}
				billingDetail.put("adminFee", adminFee);
			}
			for (AmtbAcctBillCycle billingCycle : acct.getAmtbAcctBillCycles()) {
				Map<String, Object> billingDetail = billingDetails.get(billingCycle.getEffectiveDate());
				if(billingDetail==null){
					billingDetail = new HashMap<String, Object>();
					billingDetails.put(billingCycle.getEffectiveDate(), billingDetail);
				}
				billingDetail.put("billingCycle", billingCycle);
			}
			for (AmtbAcctVolDisc volumeDiscount : acct.getAmtbAcctVolDiscs()) {
				Map<String, Object> billingDetail = billingDetails.get(volumeDiscount.getEffectiveDate());
				if(billingDetail==null){
					billingDetail = new HashMap<String, Object>();
					billingDetails.put(volumeDiscount.getEffectiveDate(), billingDetail);
				}
				billingDetail.put("volumeDiscount", volumeDiscount);
			}
			List<Map<String, Object>> returnBillingList = new LinkedList<Map<String, Object>>();
			//not showing first (Current Active) record in history
			int firstCount = 0 ;
			for(Date effectiveDate : billingDetails.keySet()){

				//not showing any records later den today
				if(effectiveDate.after(DateUtil.getCurrentDate())) 
					continue; 
				
				if(firstCount==0) {
					firstCount++;
					continue;
				}
				
				Map<String, Object> billingDetail = billingDetails.get(effectiveDate);
				Map<String, Object> returnBillingMap = new HashMap<String, Object>();
				AmtbAcctAdminFee adminFee = (AmtbAcctAdminFee)billingDetail.get("adminFee");
				if(adminFee!=null){
					returnBillingMap.put("adminFee", adminFee.getMstbAdminFeeMaster().getAdminFeePlanNo());
				}
				AmtbAcctBillCycle billingCycle = (AmtbAcctBillCycle)billingDetail.get("billingCycle");
				if(billingCycle!=null){
					returnBillingMap.put("billingCycle", billingCycle.getBillingCycle());
				}
				AmtbAcctVolDisc volumeDiscount = (AmtbAcctVolDisc)billingDetail.get("volumeDiscount");
				if(volumeDiscount!=null && volumeDiscount.getMstbVolDiscMaster()!=null){
					returnBillingMap.put("volumeDiscount", volumeDiscount.getMstbVolDiscMaster().getVolumeDiscountPlanNo());
				}
				returnBillingMap.put("effectiveDate", effectiveDate);
				returnBillingList.add(returnBillingMap);
			}
			returnMap.put("billing", returnBillingList);
			
			// getting bank information
			List<Map<String, Object>> bankInfoList = new LinkedList<Map<String, Object>>();
			Map<String, Object> returnBankInfoMap = new HashMap<String, Object>();
			if(acct.getMstbBankMaster()!=null){
				returnBankInfoMap.put("bankMaster", acct.getMstbBankMaster().getBankMasterNo());
			}
			if(acct.getMstbBranchMaster()!=null){
				returnBankInfoMap.put("branchMaster", acct.getMstbBranchMaster().getBranchMasterNo());
			}
			if(acct.getMstbMasterTableByDefaultPaymentMode()!=null){
				returnBankInfoMap.put("defaultPaymentMode", acct.getMstbMasterTableByDefaultPaymentMode().getMasterCode());
			}
			returnBankInfoMap.put("bankAcctNo", acct.getBankAcctNo());
			bankInfoList.add(returnBankInfoMap);
			returnMap.put("bankInfo", bankInfoList);
		}
		else {
			returnMap.put("billing", new LinkedList<Map<String, Object>>());
		}
		return returnMap;
	}
	public Map<String, List<Map<String, Object>>> getCreditTermHistoricalDetails(String custNo) {
		Map<String, List<Map<String, Object>>> returnMap = new HashMap<String, List<Map<String, Object>>>();
		AmtbAccount acct = this.daoHelper.getAccountDao().getCreditTermHistoricalDetails(custNo);
		// getting credit term
		Set<AmtbAcctCredTerm> creditTerms = new TreeSet<AmtbAcctCredTerm>(new Comparator<AmtbAcctCredTerm>(){
			public int compare(AmtbAcctCredTerm o1, AmtbAcctCredTerm o2) {
				return o2.getEffectiveDate().compareTo(o1.getEffectiveDate());
			}
		});
		//not showing first (Current Active) record in history
		int creditTermCount = 0;
		if (acct != null) {
			creditTerms.addAll(acct.getAmtbAcctCredTerms());
			List<Map<String, Object>> returnCreditTermList = new LinkedList<Map<String, Object>>();
			for(AmtbAcctCredTerm creditTerm : creditTerms){
				
				if(creditTermCount == 0) {
					creditTermCount++;
					continue;
				}
				
				Map<String, Object> returnCreditTermMap = new HashMap<String, Object>();
				returnCreditTermMap.put("creditTerm", creditTerm.getMstbCreditTermMaster().getCreditTermPlanNo());
				returnCreditTermMap.put("effectiveDate", creditTerm.getEffectiveDate());
				returnCreditTermList.add(returnCreditTermMap);
			}
			returnMap.put("creditTerm", returnCreditTermList);
		}
		return returnMap;
	}
	public Map<String, List<Map<String, Object>>> getEarlyPaymentHistoricalDetails(String custNo) {
		AmtbAccount acct = this.daoHelper.getAccountDao().getEarlyPaymentHistoricalDetails(custNo);
		Map<String, List<Map<String, Object>>> returnMap = new HashMap<String, List<Map<String, Object>>>();
		// getting early payment
		Set<AmtbAcctEarlyPymt> earlyPymts = new TreeSet<AmtbAcctEarlyPymt>(new Comparator<AmtbAcctEarlyPymt>(){
			public int compare(AmtbAcctEarlyPymt o1, AmtbAcctEarlyPymt o2) {
				return o2.getEffectiveDate().compareTo(o1.getEffectiveDate());
			}
		});
		//not showing first (Current Active) record in history
		int earlyPaymentCount = 0 ;
		if (acct != null) {
			earlyPymts.addAll(acct.getAmtbAcctEarlyPymts());
			List<Map<String, Object>> returnEarlyPymtList = new LinkedList<Map<String, Object>>();
			for(AmtbAcctEarlyPymt earlyPymt : earlyPymts){
				
				if(earlyPaymentCount == 0) {
					earlyPaymentCount++;
					continue;
				}
				
				Map<String, Object> returnEarlyPymtMap = new HashMap<String, Object>();
				if(earlyPymt.getMstbEarlyPaymentMaster()!=null){
					returnEarlyPymtMap.put("earlyPymt", earlyPymt.getMstbEarlyPaymentMaster().getEarlyPaymentPlanNo());
				}else{
					returnEarlyPymtMap.put("earlyPymt", null);
				}
				returnEarlyPymtMap.put("effectiveDate", earlyPymt.getEffectiveDate());
				returnEarlyPymtList.add(returnEarlyPymtMap);
			}
			returnMap.put("earlyPymt", returnEarlyPymtList);
		}
		return returnMap;
	}
	public Map<String, List<Map<String, Object>>> getLatePaymentHistoricalDetails(String custNo) {
		AmtbAccount acct = this.daoHelper.getAccountDao().getLatePaymentHistoricalDetails(custNo);
		Map<String, List<Map<String, Object>>> returnMap = new HashMap<String, List<Map<String, Object>>>();
		// getting late payment
		Set<AmtbAcctLatePymt> latePymts = new TreeSet<AmtbAcctLatePymt>(new Comparator<AmtbAcctLatePymt>(){
			public int compare(AmtbAcctLatePymt o1, AmtbAcctLatePymt o2) {
				return o2.getEffectiveDate().compareTo(o1.getEffectiveDate());
			}
		});
		//not showing first (Current Active) record in history
		int latePaymentCount = 0 ;
		if (acct != null) {
			latePymts.addAll(acct.getAmtbAcctLatePymts());
			List<Map<String, Object>> returnLatePymtList = new LinkedList<Map<String, Object>>();
			for(AmtbAcctLatePymt latePymt : latePymts){
				
				if(latePaymentCount == 0) {
					latePaymentCount++;
					continue;
				}
				Map<String, Object> returnLatePymtMap = new HashMap<String, Object>();
				returnLatePymtMap.put("latePymt", latePymt.getMstbLatePaymentMaster().getLatePaymentPlanNo());
				returnLatePymtMap.put("effectiveDate", latePymt.getEffectiveDate());
				returnLatePymtList.add(returnLatePymtMap);
			}
			returnMap.put("latePymt", returnLatePymtList);
		}
		return returnMap;
	}
	public Map<String, List<Map<String, Object>>> getPromotionHistoricalDetails(String custNo) {
		AmtbAccount acct = this.daoHelper.getAccountDao().getPromotionHistoricalDetails(custNo);
		Map<String, List<Map<String, Object>>> returnMap = new HashMap<String, List<Map<String, Object>>>();
		
		// promotion
		Set<AmtbAcctPromotion> promotions = new TreeSet<AmtbAcctPromotion>(new Comparator<AmtbAcctPromotion>(){
			public int compare(AmtbAcctPromotion o1, AmtbAcctPromotion o2) {
				return o2.getEffectiveDateFrom().equals(o1.getEffectiveDateFrom()) ? o2.toString().compareTo(o1.toString()) : o2.getEffectiveDateFrom().compareTo(o1.getEffectiveDateFrom());
			}
		});
		
		//not showing first (Current Active) record in history
		int promotionCount = 0;
		if (acct != null) {
			promotions.addAll(acct.getAmtbAcctPromotions());
			List<Map<String, Object>> returnPromotionList = new LinkedList<Map<String, Object>>();
			for(AmtbAcctPromotion promotion : promotions){
				logger.info("inside effective > "+promotion.getEffectiveDateFrom() + " acctPromoNo > "+promotion.getAcctPromotionNo());
				if(promotionCount == 0) {
					promotionCount++;
					continue;
				}
				
				Map<String, Object> returnPromotionMap = new HashMap<String, Object>();
				returnPromotionMap.put("promotion", promotion.getMstbPromotion().getPromoNo());
				returnPromotionMap.put("effectiveDateFrom", promotion.getEffectiveDateFrom());
				returnPromotionMap.put("effectiveDateTo", promotion.getEffectiveDateTo());
				returnPromotionMap.put("acctPromotionNo", promotion.getAcctPromotionNo());
				returnPromotionList.add(returnPromotionMap);
			}
			returnMap.put("promotion", returnPromotionList);

			// getting bank information
			List<Map<String, Object>> bankInfoList = new LinkedList<Map<String, Object>>();
			Map<String, Object> returnBankInfoMap = new HashMap<String, Object>();
			if(acct.getMstbBankMaster()!=null){
				returnBankInfoMap.put("bankMaster", acct.getMstbBankMaster().getBankMasterNo());
			}
			if(acct.getMstbBranchMaster()!=null){
				returnBankInfoMap.put("branchMaster", acct.getMstbBranchMaster().getBranchMasterNo());
			}
			if(acct.getMstbMasterTableByDefaultPaymentMode()!=null){
				returnBankInfoMap.put("defaultPaymentMode", acct.getMstbMasterTableByDefaultPaymentMode().getMasterCode());
			}
			returnBankInfoMap.put("bankAcctNo", acct.getBankAcctNo());
			bankInfoList.add(returnBankInfoMap);
			returnMap.put("bankInfo", bankInfoList);
		}
		return returnMap;
	}
	public boolean updateBillingDetails(String custNo, Map<Date, Map<String, Object>> billingDetails, Map<Date, Integer> creditTerms, Map<Date, Integer> earlyPymts, Map<Date, Integer> latePymts, Set<Map<String, Object>> promotions, Map<String, Object> bankInfo, String userId){
		AmtbAccount acct = this.daoHelper.getAccountDao().getBillingDetails(custNo);
		if(billingDetails!=null){
			for(Date effectiveDate : billingDetails.keySet()){
				Map<String, Object> billingDetail = billingDetails.get(effectiveDate);
				if(billingDetail.get("billingCycle")!=null){
					// searching for the corresponding billing cycle
					boolean found = false;
					for (AmtbAcctBillCycle billingCycle : acct.getAmtbAcctBillCycles()) {
						if(effectiveDate.equals(billingCycle.getEffectiveDate())){
							// found
							found = true;
							if(!billingDetail.get("billingCycle").equals(billingCycle.getBillingCycle())){
								billingCycle.setBillingCycle((String)billingDetail.get("billingCycle"));
								this.update(billingCycle);
							}
							break;
						}
					}
					// if not found, create a new object
					if(!found){
						AmtbAcctBillCycle billingCycle = new AmtbAcctBillCycle();
						billingCycle.setBillingCycle((String)billingDetail.get("billingCycle"));
						billingCycle.setEffectiveDate(DateUtil.convertUtilDateToSqlDate(effectiveDate));
						billingCycle.setAmtbAccount(acct);
						this.save(billingCycle);
						acct.getAmtbAcctBillCycles().add(billingCycle);
					}
				}
			}
			// now searching the deleted ones
			List<AmtbAcctBillCycle> deletedBillingCycles = new ArrayList<AmtbAcctBillCycle>();
			for(AmtbAcctBillCycle billingCycle : acct.getAmtbAcctBillCycles()){
				boolean found = false;
				for(Date effectiveDate : billingDetails.keySet()){
					Map<String, Object> billingDetail = billingDetails.get(effectiveDate);
					if(effectiveDate.getTime() == billingCycle.getEffectiveDate().getTime() && billingDetail.get("billingCycle")!=null && billingDetail.get("billingCycle").equals(billingCycle.getBillingCycle())){
						found = true;
						break;
					}
				}
				if(!found){
					deletedBillingCycles.add(billingCycle);
				}
			}
			acct.getAmtbAcctBillCycles().clear();
			for(AmtbAcctBillCycle deletedBillingCycle : deletedBillingCycles){
				this.delete(deletedBillingCycle);
			}
		}
		if(creditTerms!=null){
			for(Date effectiveDate : creditTerms.keySet()){
				boolean found = false;
				for (AmtbAcctCredTerm creditTerm : acct.getAmtbAcctCredTerms()) {
					if(effectiveDate.equals(creditTerm.getEffectiveDate())){
						found = true;
						if(!creditTerm.getMstbCreditTermMaster().getCreditTermPlanNo().equals(creditTerms.get(effectiveDate))){
							creditTerm.setMstbCreditTermMaster((MstbCreditTermMaster)MasterSetup.getCreditTermManager().getMaster(creditTerms.get(effectiveDate)));
							this.update(creditTerm);
						}
						break;
					}
				}
				if(!found){
					AmtbAcctCredTerm creditTerm = new AmtbAcctCredTerm();
					creditTerm.setMstbCreditTermMaster((MstbCreditTermMaster)MasterSetup.getCreditTermManager().getMaster(creditTerms.get(effectiveDate)));
					creditTerm.setEffectiveDate(DateUtil.convertUtilDateToSqlDate(effectiveDate));
					creditTerm.setAmtbAccount(acct);
					this.save(creditTerm);
					acct.getAmtbAcctCredTerms().add(creditTerm);
				}
			}
			// now searching the deleted ones
			List<AmtbAcctCredTerm> deletedCreditTerms = new ArrayList<AmtbAcctCredTerm>();
			for(AmtbAcctCredTerm creditTerm : acct.getAmtbAcctCredTerms()){
				boolean found = false;
				for(Date effectiveDate : creditTerms.keySet()){
					if(creditTerm.getMstbCreditTermMaster().getCreditTermPlanNo().equals(creditTerms.get(effectiveDate))){
						found = true;
						break;
					}
				}
				if(!found){
					deletedCreditTerms.add(creditTerm);
				}
			}
			acct.getAmtbAcctCredTerms().clear();
			for(AmtbAcctCredTerm deletedCreditTerm : deletedCreditTerms){
				this.delete(deletedCreditTerm);
			}
		}
		if(promotions!=null){
			List<Integer> foundNos = new ArrayList<Integer>();
			for(Map<String, Object> map : promotions){
				Date effectiveDateFrom = (Date)map.get("effectiveDateFrom");
				Date effectiveDateTo = (Date)map.get("effectiveDateTo");
				Integer promoNo = (Integer)map.get("promoNo");
				boolean found = false;
				for (AmtbAcctPromotion promotion : acct.getAmtbAcctPromotions()) {
					if(promoNo.equals(promotion.getMstbPromotion().getPromoNo())){
						if(!foundNos.contains(promotion.getAcctPromotionNo())){
							found = true;
							foundNos.add(promotion.getAcctPromotionNo());
							promotion.setEffectiveDateTo(DateUtil.convertUtilDateToSqlDate(effectiveDateTo));
							promotion.setEffectiveDateFrom(DateUtil.convertUtilDateToSqlDate(effectiveDateFrom));
							logger.info("update");
							this.update(promotion);
							break;
						}
					}
				}
				if(!found){
					AmtbAcctPromotion promotion = new AmtbAcctPromotion();
					promotion.setMstbPromotion((MstbPromotion)MasterSetup.getPromotionManager().getMaster(promoNo));
					promotion.setEffectiveDateFrom(DateUtil.convertUtilDateToSqlDate(effectiveDateFrom));
					promotion.setEffectiveDateTo(DateUtil.convertUtilDateToSqlDate(effectiveDateTo));
					promotion.setAmtbAccount(acct);
					logger.info("save");
					this.save(promotion);
				}
			}
			// now searching the deleted ones
			List<AmtbAcctPromotion> deletedPromotions = new ArrayList<AmtbAcctPromotion>();
			for(AmtbAcctPromotion promotion : acct.getAmtbAcctPromotions()){
				if(!foundNos.contains(promotion.getAcctPromotionNo())){
					deletedPromotions.add(promotion);
				}
			}
			acct.getAmtbAcctPromotions().clear();
			for(AmtbAcctPromotion deletedPromotion : deletedPromotions){
				logger.info("delete");
				this.delete(deletedPromotion);
			}
		}
		if(bankInfo!=null){
			if(bankInfo.get("bankNo")!=null){
				acct.setMstbBankMaster((MstbBankMaster)MasterSetup.getBankManager().getMaster((Integer)bankInfo.get("bankNo")));
			}else{
				if(acct.getMstbBankMaster()!=null){
					acct.setMstbBankMaster(null);
				}
			}
			if(bankInfo.get("branchNo")!=null){
				acct.setMstbBranchMaster((MstbBranchMaster)MasterSetup.getBankManager().getDetail((Integer)bankInfo.get("branchNo")));
			}else{
				if(acct.getMstbBranchMaster()!=null){
					acct.setMstbBranchMaster(null);
				}
			}
			if(bankInfo.get("bankAcctNo")!=null){
				acct.setBankAcctNo((String)bankInfo.get("bankAcctNo"));
			}else{
				if(acct.getBankAcctNo()!=null){
					acct.setBankAcctNo(null);
				}
			}
			if(bankInfo.get("paymentModeCode")!=null){
				acct.setMstbMasterTableByDefaultPaymentMode(ConfigurableConstants.getMasterTable(ConfigurableConstants.PAYMENT_MODE, (String)bankInfo.get("paymentModeCode")));
			}else{
				if(acct.getMstbMasterTableByDefaultPaymentMode()!=null){
					acct.setMstbMasterTableByDefaultPaymentMode(null);
				}
			}
		}
		this.daoHelper.getAccountDao().update(acct, userId);
		return true;
	}
	

	
	public ArrayList<HashMap<String, Object>> getRewardsSummary(String custNo){
		
		AmtbAccount acct = this.daoHelper.getAccountDao().getRewardsDetails(custNo);
		ArrayList<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();
		for (LrtbRewardAccount rewardAccount : acct.getLrtbRewardAccounts()) {
			Integer redeemPts=0, rewardPts=0, initialPts=0, adjustmentPts=0;
			for (LrtbRewardTxn txn : rewardAccount.getLrtbRewardTxns()) {
				if(txn.getLrtbRewardMaster()==null && txn.getLrtbGiftStock()!=null){// redeem
					redeemPts += txn.getRewardsPts();
				}else if(txn.getLrtbRewardMaster()!=null && txn.getLrtbGiftStock()==null){//reward
					rewardPts += txn.getRewardsPts();
				}else if(txn.getLrtbRewardMaster()==null && txn.getLrtbGiftStock()==null
						&& txn.getLrtbRewardAdjReq()==null && txn.getRewardsPts() >= 0) {//initial
					initialPts += txn.getRewardsPts();
				}else if(txn.getLrtbRewardAdjReq()!=null && txn.getLrtbGiftStock()==null){//adjustment
					adjustmentPts += txn.getRewardsPts();
				}
			}
			HashMap<String, Object> rewardsMap = new HashMap<String, Object>();
			rewardsMap.put("redeem", redeemPts);
			rewardsMap.put("reward", rewardPts);
			rewardsMap.put("initial", initialPts);
			rewardsMap.put("adjustment", adjustmentPts);
			rewardsMap.put("rewardAcct", rewardAccount);
			list.add(rewardsMap);
		}
		return list;
		
	}
	
	
	public List<Map<String, Map<Date, Map<String, Integer>>>> getRewardsDetails(String custNo){
		AmtbAccount acct = this.daoHelper.getAccountDao().getRewardsDetails(custNo);
		Map<Integer, Map<Date, Map<String, Integer>>> rewardsMap = new HashMap<Integer, Map<Date, Map<String, Integer>>>();
		Map<Integer, LrtbRewardMaster> rewardMasters = new HashMap<Integer, LrtbRewardMaster>();
		Map<Date, Integer> redeemMap = new HashMap<Date, Integer>();
		for (LrtbRewardAccount rewards : acct.getLrtbRewardAccounts()) {
			for (LrtbRewardTxn reward : rewards.getLrtbRewardTxns()) {
				if(reward.getLrtbRewardMaster()!=null){
					rewardMasters.put(reward.getLrtbRewardMaster().getRewardPlanNo(), reward.getLrtbRewardMaster());
				}
				Map<Date, Map<String, Integer>> rewardMap = rewardsMap.get(reward.getLrtbRewardMaster()!=null ? reward.getLrtbRewardMaster().getRewardPlanNo() : null);
				if(rewardMap == null){
					rewardMap = new TreeMap<Date, Map<String, Integer>>(new Comparator<Date>(){
						public int compare(Date o1, Date o2) {
							return o1.compareTo(o2);
						}
					});
					rewardsMap.put(reward.getLrtbRewardMaster()!=null ? reward.getLrtbRewardMaster().getRewardPlanNo() : null, rewardMap);
				}
				Map<String, Integer> expMap = rewardMap.get(rewards.getCutOffDt());
				if(expMap == null){
					expMap = new HashMap<String, Integer>();
					rewardMap.put(rewards.getCutOffDt(), expMap);
				}
				Integer points = reward.getRewardsPts();
				if(reward.getLrtbRewardMaster()==null && reward.getLrtbGiftStock()!=null){// redeem
					redeemMap.put(rewards.getCutOffDt(), redeemMap.get(rewards.getCutOffDt())==null ? points : redeemMap.get(rewards.getCutOffDt()) + points);
				}else if(reward.getLrtbRewardMaster()!=null && reward.getLrtbGiftStock()==null){//reward
					expMap.put("reward", expMap.get("reward")==null ? points : expMap.get("reward") + points);
					expMap.put(DateUtil.convertDateToStr(rewards.getExpireDt(), DateUtil.GLOBAL_DATE_FORMAT), -1);
				}else if(reward.getLrtbRewardMaster()==null && reward.getLrtbGiftStock()==null){//initial
					expMap.put("initial", expMap.get("initial")==null ? points : expMap.get("initial") + points);
					expMap.put(DateUtil.convertDateToStr(rewards.getExpireDt(), DateUtil.GLOBAL_DATE_FORMAT), -1);
				}
			}
		}
		// now adding redeem to the plans
		// adding expiry date to the plans. Not changing the structure of the return type.
		for(Integer planNo : rewardsMap.keySet()){
			if(planNo != null || rewardMasters.size()==0){
				Map<Date, Map<String, Integer>> rewardMap = rewardsMap.get(planNo);
				for(Date cutoffDate : rewardMap.keySet()){
					for(Date redeemDate : redeemMap.keySet()){
						if(cutoffDate.equals(redeemDate)){
							rewardMap.get(cutoffDate).put("redeem", redeemMap.get(redeemDate));
							for(LrtbRewardAccount rewards : acct.getLrtbRewardAccounts()){
								if(cutoffDate.equals(rewards.getCutOffDt())){
									rewardMap.get(cutoffDate).put(DateUtil.convertDateToStr(rewards.getExpireDt(), DateUtil.GLOBAL_DATE_FORMAT), -1);
									break;
								}
							}
							break;
						}
					}
				}
			}
		}
		List<Map<String, Map<Date, Map<String, Integer>>>> returnList = new ArrayList<Map<String, Map<Date, Map<String, Integer>>>>();
		for(Integer planNo : rewardsMap.keySet()){
			Map<String, Map<Date, Map<String, Integer>>> returnMap = new HashMap<String, Map<Date, Map<String, Integer>>>();
			if(planNo!=null){
				returnMap.put(rewardMasters.get(planNo).getRewardPlanName(), rewardsMap.get(planNo));
				returnList.add(returnMap);
			}else{
				returnMap.put("initial", rewardsMap.get(planNo));
				returnList.add(returnMap);
			}
		}
		return returnList;
	}
	public boolean updateInitialRewardsPoints(String custNo, BigDecimal initialPoints, String userId){
		AmtbAccount acct = this.daoHelper.getAccountDao().getRewardsDetails(custNo);
		if(acct.getLrtbRewardAccounts().isEmpty()){
			LrtbRewardAccount newAcct = new LrtbRewardAccount();
			MstbMasterTable master = ConfigurableConstants.getAllMasterTable(ConfigurableConstants.REWARDS_GRACE_PERIOD, NonConfigurableConstants.REWARDS_GRACE_PERIOD_MASTER_CODE);
			Integer gracePeriod = new Integer(master.getMasterValue());
			Integer ibsGracePeriod = new Integer(ConfigurableConstants.getMasterTable(ConfigurableConstants.REWARDS_GRACE_PERIOD, NonConfigurableConstants.REWARDS_IBS_GRACE_PERIOD_MASTER_CODE).getMasterValue());
			Calendar rewardsCutoffCalendar = DateUtil.getRewardsCutoffCalendar();
			newAcct.setCutOffDt(new Timestamp(rewardsCutoffCalendar.getTimeInMillis()));
			newAcct.setExpireDt(new Timestamp(DateUtil.getRewardsExpiryDt(rewardsCutoffCalendar, gracePeriod).getTimeInMillis()));
			rewardsCutoffCalendar.add(Calendar.MONTH, ibsGracePeriod);
			newAcct.setIbsExpireDt(new Timestamp(rewardsCutoffCalendar.getTimeInMillis()));
			newAcct.setAmtbAccount(acct);
			newAcct.setExpiredBilledFlag(NonConfigurableConstants.BOOLEAN_NO);
			newAcct.setExtMthToExpiry(gracePeriod);
			this.daoHelper.getAccountDao().save(newAcct);
			LrtbRewardTxn newTxn = new LrtbRewardTxn();
			newTxn.setLrtbRewardAccount(newAcct);
			newTxn.setRewardsPts(initialPoints.intValue());
			newTxn.setBilledFlag(NonConfigurableConstants.REWARDS_TXN_BILLED_FLAG_YES);
			acct.getLrtbRewardAccounts().add(newAcct);
			newAcct.getLrtbRewardTxns().add(newTxn);
			this.daoHelper.getAccountDao().save(newTxn, userId);
		}else{
			for (LrtbRewardAccount rewardAccount : acct.getLrtbRewardAccounts()) {
				LrtbRewardTxn rewardTxn = this.daoHelper.getRewardDao().getInitialRewardPointsTxn(rewardAccount);
				if(rewardTxn==null){
					rewardTxn = new LrtbRewardTxn();
					rewardTxn.setLrtbRewardAccount(rewardAccount);
					rewardTxn.setRewardsPts(initialPoints.intValue());
					this.daoHelper.getGenericDao().save(rewardTxn, userId);
				}else{
					rewardTxn.setRewardsPts(initialPoints.intValue());
					this.daoHelper.getGenericDao().update(rewardTxn);
				}
				break;
			}
		}
		this.daoHelper.getAccountDao().update(acct, userId);
		return true;
	}
	
	public boolean updateCorp(Map<String, Object> corpDetails){
		logger.info("updateCorp(Map<String, Object> corpDetails)");
		AmtbAccount acct = this.daoHelper.getAccountDao().getAccount((String)corpDetails.get("custNo"), 0, null, null);
		AmtbCorporateDetail corpDetail = this.daoHelper.getAccountDao().getCorporateDetail(acct);

		acct.setAccountName((String)corpDetails.get("acctName"));
		corpDetail.setRcbNo((String)corpDetails.get("rcbNo"));
		corpDetail.setMstbMasterTableByIndustry(ConfigurableConstants.getMasterTable(ConfigurableConstants.INDUSTRY_MASTER_CODE, (String)corpDetails.get("industryCode")));
		corpDetail.setRcbDt(DateUtil.convertDateToTimestamp((Date)corpDetails.get("rcbDate")));
		corpDetail.setCapital((BigDecimal)corpDetails.get("capital"));
		corpDetail.setTel((String)corpDetails.get("tel"));
		corpDetail.setFax((String)corpDetails.get("fax"));
		acct.setNameOnCard((String)corpDetails.get("nameOnCard"));
		acct.setEinvoiceFlag((String)corpDetails.get("eInvoice"));
		acct.setInvoicePrinting((String)corpDetails.get("invoicePrinting"));
		acct.setOutsourcePrintingFlag((String) corpDetails.get("outsourcePrinting"));
		acct.setAceIndicator((String) corpDetails.get("aceIndicator"));
		acct.setCoupaIndicator((String) corpDetails.get("coupaIndicator"));
		acct.setSmsFlag((String)corpDetails.get("sms"));
		acct.setSmsExpiryFlag((String)corpDetails.get("smsExpiry"));
		acct.setSmsTopupFlag((String)corpDetails.get("smsTopUp"));
		corpDetail.setAddressBlock((String)corpDetails.get("blkNo"));
		corpDetail.setAddressUnit((String)corpDetails.get("unitNo"));
		corpDetail.setAddressStreet((String)corpDetails.get("street"));
		corpDetail.setAddressBuilding((String)corpDetails.get("building"));
		corpDetail.setAddressArea((String)corpDetails.get("area"));
		corpDetail.setMstbMasterTableByAddressCountry(ConfigurableConstants.getMasterTable(ConfigurableConstants.COUNTRY_MASTER_CODE, (String)corpDetails.get("countryCode")));
		corpDetail.setAddressCity((String)corpDetails.get("city"));
		corpDetail.setAddressState((String)corpDetails.get("state"));
		corpDetail.setAddressPostal((String)corpDetails.get("postal"));
		corpDetail.setProjectCode((String)corpDetails.get("projectCode"));
		corpDetail.setMstbMasterTableByAuthPersonSal(ConfigurableConstants.getMasterTable(ConfigurableConstants.SALUTATION_MASTER_CODE, (String)corpDetails.get("salutationCode")));
		corpDetail.setAuthPersonName((String)corpDetails.get("authPerson"));
		corpDetail.setAuthPersonTitle((String)corpDetails.get("authTitle"));
		acct.setMstbMasterTableByInformationSource(ConfigurableConstants.getMasterTable(ConfigurableConstants.INFORMATION_SOURCE_MASTER_CODE, (String)corpDetails.get("infoSourceCode")));
		acct.setInvoiceFormat((String)corpDetails.get("invoiceFormat"));
		acct.setInvoiceSorting((String)corpDetails.get("invoiceSorting"));
		acct.setGovtEInvoiceFlag((String) corpDetails.get("govtEInvFlag"));
		acct.setMstbMasterTableByBusinessUnit(ConfigurableConstants.getMasterTable(ConfigurableConstants.GOVT_EINVOICE_BUSINESS_UNIT_MASTER_TYPE, (String)corpDetails.get("businessUnit")));
		acct.setPrintTaxInvoiceOnly((String) corpDetails.get("printTaxInvoiceOnly"));
		acct.setPubbsFlag((String)corpDetails.get("pubbsFlag"));
		acct.setFiFlag((String)corpDetails.get("fiFlag"));
		acct.setRecurringFlag((String)corpDetails.get("recurring"));
		
		if(corpDetails.get("recurringChargeDay") != null && ((String)corpDetails.get("recurring")).equals("Y"))
		{
			String recurringChargeDayString = (String)corpDetails.get("recurringChargeDay");
			acct.setRecurringChargeDay(Integer.parseInt(recurringChargeDayString.substring(0, recurringChargeDayString.length() - 2)));
		}
		else
			acct.setRecurringChargeDay(null);
		
		this.daoHelper.getAccountDao().update(corpDetail);
		this.daoHelper.getAccountDao().update(acct, (String)corpDetails.get("userId"));

		// now updating the contacts
		List<AmtbContactPerson> contacts = this.daoHelper.getAccountDao().getContacts(acct.getCustNo(), null, null, null, null);
		for(AmtbContactPerson contact : contacts){
			if(contact.getSameAsCorporate().equals(NonConfigurableConstants.BOOLEAN_YES)){
				contact.setAddressBlock(corpDetail.getAddressBlock());
				contact.setAddressUnit(corpDetail.getAddressUnit());
				contact.setAddressStreet(corpDetail.getAddressStreet());
				contact.setAddressBuilding(corpDetail.getAddressBuilding());
				contact.setAddressArea(corpDetail.getAddressArea());
				contact.setMstbMasterTableByAddressCountry(corpDetail.getMstbMasterTableByAddressCountry());
				contact.setAddressCity(corpDetail.getAddressCity());
				contact.setAddressState(corpDetail.getAddressState());
				contact.setAddressPostal(corpDetail.getAddressPostal());
				this.update(contact);
			}
		}
		return true;
	}
	
	public boolean updateCorpWithCNII(Map<String, Object> corpDetails) throws CniiInterfaceException {
		logger.info("updateCorpWithCNII(Map<String, Object> corpDetails)");
		AmtbAccount acct = this.daoHelper.getAccountDao().getAccount((String)corpDetails.get("custNo"), 0, null, null);
		AmtbCorporateDetail corpDetail = this.daoHelper.getAccountDao().getCorporateDetail(acct);
		String currentAcctName = acct.getAccountName();
		acct.setAccountName((String)corpDetails.get("acctName"));
		corpDetail.setRcbNo((String)corpDetails.get("rcbNo"));
		corpDetail.setMstbMasterTableByIndustry(ConfigurableConstants.getMasterTable(ConfigurableConstants.INDUSTRY_MASTER_CODE, (String)corpDetails.get("industryCode")));
		corpDetail.setRcbDt(DateUtil.convertDateToTimestamp((Date)corpDetails.get("rcbDate")));
		corpDetail.setCapital((BigDecimal)corpDetails.get("capital"));
		corpDetail.setTel((String)corpDetails.get("tel"));
		corpDetail.setFax((String)corpDetails.get("fax"));
		acct.setNameOnCard((String)corpDetails.get("nameOnCard"));
		acct.setEinvoiceFlag((String)corpDetails.get("eInvoice"));
		acct.setInvoicePrinting((String)corpDetails.get("invoicePrinting"));
		acct.setOutsourcePrintingFlag((String) corpDetails.get("outsourcePrinting"));
		acct.setSmsFlag((String)corpDetails.get("sms"));
		acct.setSmsExpiryFlag((String)corpDetails.get("smsExpiry"));
		acct.setSmsTopupFlag((String)corpDetails.get("smsTopUp"));
		corpDetail.setAddressBlock((String)corpDetails.get("blkNo"));
		corpDetail.setAddressUnit((String)corpDetails.get("unitNo"));
		corpDetail.setAddressStreet((String)corpDetails.get("street"));
		corpDetail.setAddressBuilding((String)corpDetails.get("building"));
		corpDetail.setAddressArea((String)corpDetails.get("area"));
		corpDetail.setMstbMasterTableByAddressCountry(ConfigurableConstants.getMasterTable(ConfigurableConstants.COUNTRY_MASTER_CODE, (String)corpDetails.get("countryCode")));
		corpDetail.setAddressCity((String)corpDetails.get("city"));
		corpDetail.setAddressState((String)corpDetails.get("state"));
		corpDetail.setAddressPostal((String)corpDetails.get("postal"));
		corpDetail.setProjectCode((String)corpDetails.get("projectCode"));
		corpDetail.setMstbMasterTableByAuthPersonSal(ConfigurableConstants.getMasterTable(ConfigurableConstants.SALUTATION_MASTER_CODE, (String)corpDetails.get("salutationCode")));
		corpDetail.setAuthPersonName((String)corpDetails.get("authPerson"));
		corpDetail.setAuthPersonTitle((String)corpDetails.get("authTitle"));
		acct.setMstbMasterTableByInformationSource(ConfigurableConstants.getMasterTable(ConfigurableConstants.INFORMATION_SOURCE_MASTER_CODE, (String)corpDetails.get("infoSourceCode")));
		acct.setInvoiceFormat((String)corpDetails.get("invoiceFormat"));
		acct.setInvoiceSorting((String)corpDetails.get("invoiceSorting"));
		// Govt eInvoice Enhancement
		acct.setGovtEInvoiceFlag((String)corpDetails.get("govtEInvFlag"));
		acct.setMstbMasterTableByBusinessUnit(ConfigurableConstants.getMasterTable(ConfigurableConstants.GOVT_EINVOICE_BUSINESS_UNIT_MASTER_TYPE, (String)corpDetails.get("businessUnit")));
		acct.setPrintTaxInvoiceOnly((String)corpDetails.get("printTaxInvoiceOnly"));
		acct.setAceIndicator((String)corpDetails.get("aceIndicator"));
		acct.setCoupaIndicator((String)corpDetails.get("coupaIndicator"));
		acct.setPubbsFlag((String)corpDetails.get("pubbsFlag"));
		acct.setFiFlag((String)corpDetails.get("fiFlag"));
		acct.setRecurringFlag((String)corpDetails.get("recurring"));
		
		logger.info("updateCorpWithCNII(Map<String, Object> corpDetails)"+(String)corpDetails.get("aceIndicator")+" "+(String)corpDetails.get("coupaIndicator"));
		
		if(corpDetails.get("recurringChargeDay") != null && ((String)corpDetails.get("recurring")).equals("Y"))
		{
			String recurringChargeDayString = (String)corpDetails.get("recurringChargeDay");
			acct.setRecurringChargeDay(Integer.parseInt(recurringChargeDayString.substring(0, recurringChargeDayString.length() - 2)));
		}
		else
			acct.setRecurringChargeDay(null);
		
		this.daoHelper.getAccountDao().update(corpDetail);
		this.daoHelper.getAccountDao().update(acct, (String)corpDetails.get("userId"));

		this.daoHelper.getAccountDao().updateOutsourcePrintFlag(acct);
		
		List<String> cardLessProductList = new ArrayList<String>();
		List<PmtbProductType> cardlessProducts = this.businessHelper.getProductTypeBusiness().getCardlessProductType();
		for(PmtbProductType cardlessProduct : cardlessProducts) {
			cardLessProductList.add(cardlessProduct.getProductTypeId().trim());
		}
		
		// if there is a change in name, send to CNII
		if (currentAcctName!= null && !currentAcctName.equalsIgnoreCase((String) corpDetails.get("acctName")))
		{
			// Call CNII Synchronise interface
			Map<String, String> prop = (Map)SpringUtil.getBean("webserviceProperties");
			if (NonConfigurableConstants.BOOLEAN_NO.equals((String) prop.get("ws.cnii.disable")))
			{
				// Not disabled - so send interface
				try
				{
					for(AmtbSubscTo subscription : acct.getAmtbSubscTos())
					{
						PmtbProductType productType = subscription.getComp_id().getPmtbProductType();
//						if(productType.getProductTypeId().equals(NonConfigurableConstants.PREMIER_SERVICE))
						if(cardLessProductList.contains(productType.getProductTypeId()))
						{
							ArrayList<IttbCniiAcctReq> cniiList = new ArrayList<IttbCniiAcctReq>();
							IttbCniiAcctReq cniiRequest = new IttbCniiAcctReq();
							cniiRequest.setReqId(DateUtil.convertDateToStr(DateUtil.getCurrentDate(), DateUtil.GLOBAL_CNII_SEQ_FORMAT) + this.daoHelper.getAccountDao().getNextSequenceNo(Sequence.CNII_REQ_NO_SEQUENCE));
							cniiRequest.setReqDate(DateUtil.getCurrentTimestamp());
							cniiRequest.setEventType(NonConfigurableConstants.CNII_REQUEST_EVENT_TYPE_ACTIVATE);
							cniiRequest.setReqStatus(NonConfigurableConstants.CNII_REQUEST_STATUS_PENDING);
							cniiRequest.setValidFlg(NonConfigurableConstants.BOOLEAN_YES);
							cniiRequest.setUpdatedBy(NonConfigurableConstants.CNII_REQUEST_UPDATED_BY_IBS);
							StringBuffer acctId = new StringBuffer();
							StringBuffer parentId = new StringBuffer();
							if(acct.getCustNo()!=null && acct.getCustNo().length()!=0){
								acctId.append(acct.getCustNo());
								// Should not have parent since acct is the top-most account
								//parentId.append(acct.getCustNo());
							}else{
								acctId.append(acct.getCode());
								// Not required to append as parent is always without the last code
								// e.g. if dept = 304DIV1DEP1, parent will be 304DIV1
								//parentId.append(acct.getCode());
								// now getting the parent
								AmtbAccount parent = acct.getAmtbAccount();
								if(parent!=null){// this shouldn't happen
									while(parent.getCustNo()==null){
										acctId.insert(0, parent.getCode());
										parentId.insert(0, parent.getCode());
										parent = this.daoHelper.getAccountDao().getParent(parent).getAmtbAccount();
									}
									acctId.insert(0, parent.getCustNo());
									parentId.insert(0, parent.getCustNo());
								}
							}
							cniiRequest.setAccountId(acctId.toString());
							if(acct.getCustNo()!=null && acct.getCustNo().length()!=0){
								cniiRequest.setAccountCd(acct.getCustNo());
							}else{
								cniiRequest.setAccountCd(acct.getCode());
							}
							cniiRequest.setAccountNm(acct.getAccountName());
							cniiRequest.setParentId(parentId.toString());
							cniiRequest.setUpdatedDt(DateUtil.getCurrentTimestamp());
							//this.save(cniiRequest);
							cniiList.add(cniiRequest);
//							IBSCNIIUpdateAcctClient.send(cniiList);
							
							//29 Aug 2018 CNII Acct Sync
							String msg = updateCniiAcctSyncProcedure(cniiList);
							logger.info("CNII Acct Sync log : "+msg);
						}
					}
				}
				catch (Exception e)
				{
					logger.error(e.getMessage());
					throw new CniiInterfaceException("CNII Interface Acct Sync Error - "+e);
				}
			}else{
				logger.info("CNII interface is disabled");
			}
		}

		// now updating the contacts
		List<AmtbContactPerson> contacts = this.daoHelper.getAccountDao().getContacts(acct.getCustNo(), null, null, null, null);
		for(AmtbContactPerson contact : contacts){
			if(contact.getSameAsCorporate().equals(NonConfigurableConstants.BOOLEAN_YES)){
				contact.setAddressBlock(corpDetail.getAddressBlock());
				contact.setAddressUnit(corpDetail.getAddressUnit());
				contact.setAddressStreet(corpDetail.getAddressStreet());
				contact.setAddressBuilding(corpDetail.getAddressBuilding());
				contact.setAddressArea(corpDetail.getAddressArea());
				contact.setMstbMasterTableByAddressCountry(corpDetail.getMstbMasterTableByAddressCountry());
				contact.setAddressCity(corpDetail.getAddressCity());
				contact.setAddressState(corpDetail.getAddressState());
				contact.setAddressPostal(corpDetail.getAddressPostal());
				this.update(contact);
			}
		}
		return true;
	}
	public boolean updatePers(Map<String, Object> persDetails){
		logger.info("updatePers(Map<String, Object> persDetails)");
		AmtbAccount acct = this.daoHelper.getAccountDao().getAccount((String)persDetails.get("custNo"), 0, null, null);
		AmtbPersonalDetail persDetail = this.daoHelper.getAccountDao().getPersonalDetail(acct);
		acct.setAccountName((String)persDetails.get("acctName"));
		acct.setNameOnCard((String)persDetails.get("nameOnCard"));
		acct.setEinvoiceFlag((String)persDetails.get("eInvoice"));
		acct.setPrintTaxInvoiceOnly((String)persDetails.get("printTaxInvoiceOnly"));
		acct.setInvoicePrinting((String)persDetails.get("invoicePrinting"));
		acct.setOutsourcePrintingFlag((String) persDetails.get("outsourcePrinting"));
		acct.setSmsFlag((String)persDetails.get("sms"));
		acct.setSmsExpiryFlag((String)persDetails.get("smsExpiry"));
		acct.setSmsTopupFlag((String)persDetails.get("smsTopUp"));
		acct.setPrintTaxInvoiceOnly((String)persDetails.get("printTaxInvoiceOnly"));
		acct.setRecurringFlag((String)persDetails.get("recurring"));
		
		if(persDetails.get("recurringChargeDay") != null && ((String)persDetails.get("recurring")).equals("Y"))
		{
			String recurringChargeDayString = (String)persDetails.get("recurringChargeDay");
			acct.setRecurringChargeDay(Integer.parseInt(recurringChargeDayString.substring(0, recurringChargeDayString.length() - 2)));
		}
		else
			acct.setRecurringChargeDay(null);
		
		persDetail.setNric((String)persDetails.get("nric"));
		if(persDetails.get("birthdate")!=null){
			persDetail.setBirthDt(DateUtil.convertDateToTimestamp((Date)persDetails.get("birthdate")));
		}
		persDetail.setTel((String)persDetails.get("tel"));
		persDetail.setAddressBlock((String)persDetails.get("blkNo"));
		persDetail.setAddressUnit((String)persDetails.get("unitNo"));
		persDetail.setAddressStreet((String)persDetails.get("street"));
		persDetail.setAddressBuilding((String)persDetails.get("building"));
		persDetail.setAddressArea((String)persDetails.get("area"));
		persDetail.setMstbMasterTableByAddressCountry(ConfigurableConstants.getMasterTable(ConfigurableConstants.COUNTRY_MASTER_CODE, (String)persDetails.get("countryCode")));
		persDetail.setAddressCity((String)persDetails.get("city"));
		persDetail.setAddressState((String)persDetails.get("state"));
		persDetail.setAddressPostal((String)persDetails.get("postal"));
		acct.setMstbMasterTableByInformationSource(ConfigurableConstants.getMasterTable(ConfigurableConstants.INFORMATION_SOURCE_MASTER_CODE, (String)persDetails.get("infoSourceCode")));
		persDetail.setMstbMasterTableByJobStatus(ConfigurableConstants.getMasterTable(ConfigurableConstants.JOB_STATUS_MASTER_CODE, (String)persDetails.get("jobStatusCode")));
		persDetail.setOccupation((String)persDetails.get("occupation"));
		persDetail.setMstbMasterTableByIndustry(ConfigurableConstants.getMasterTable(ConfigurableConstants.INDUSTRY_MASTER_CODE, (String)persDetails.get("industryCode")));
		persDetail.setEmployerName((String)persDetails.get("employerName"));
		persDetail.setEmployerBlock((String)persDetails.get("empBlkNo"));
		persDetail.setEmployerUnit((String)persDetails.get("empUnitNo"));
		persDetail.setEmployerStreet((String)persDetails.get("empStreet"));
		persDetail.setEmployerBuilding((String)persDetails.get("empBuilding"));
		persDetail.setEmployerArea((String)persDetails.get("empArea"));
		persDetail.setMstbMasterTableByEmployerCountry(ConfigurableConstants.getMasterTable(ConfigurableConstants.COUNTRY_MASTER_CODE, (String)persDetails.get("empCountryCode")));
		persDetail.setEmployerCity((String)persDetails.get("empCity"));
		persDetail.setEmployerState((String)persDetails.get("empState"));
		persDetail.setEmployerPostal((String)persDetails.get("empPostal"));
		persDetail.setMonthlyIncome((BigDecimal)persDetails.get("monthlyIncome"));

		if(persDetails.get("empLength")!=null){
			persDetail.setEmployLengthYear(((BigDecimal)persDetails.get("empLength")).intValue());
		}else{
			persDetail.setEmployLengthYear(0);
		}
		if(persDetails.get("mainContactRace")!=null){
			persDetail.setMstbMasterTableByMainContactRace(ConfigurableConstants.getMasterTable(ConfigurableConstants.RACE_MASTER_CODE, (String)persDetails.get("mainContactRace")));
		}
		
		List<AmtbAcctMainContact> mainContacts = this.daoHelper.getAccountDao().getMainContacts(acct.getAccountNo());
		String userId = (String)persDetails.get("userId");
		this.daoHelper.getAccountDao().update(persDetail);
		this.daoHelper.getAccountDao().update(acct, userId);
		this.daoHelper.getAccountDao().updateOutsourcePrintFlag(acct);
		// setting the common fields
		for(AmtbAcctMainContact mainContact : mainContacts){
			AmtbContactPerson contact = mainContact.getAmtbContactPerson();
			contact.setMainContactName((String)persDetails.get("acctName"));
			contact.setMainContactTel((String)persDetails.get("office"));
			contact.setMstbMasterTableByMainContactSal(ConfigurableConstants.getMasterTable(ConfigurableConstants.SALUTATION_MASTER_CODE, (String)persDetails.get("salutationCode")));
			contact.setMstbMasterTableByMainContactRace(ConfigurableConstants.getMasterTable(ConfigurableConstants.RACE_MASTER_CODE, (String)persDetails.get("race")));
			contact.setMainContactEmail((String)persDetails.get("email"));
			contact.setMainContactMobile((String)persDetails.get("mobile"));
			
			if(persDetail.getMstbMasterTableByMainContactRace() != null) {
				contact.setMstbMasterTableByMainContactRace(persDetail.getMstbMasterTableByMainContactRace());
			}
			
			// now specific address
			if(mainContact.getComp_id().getMainContactType().equals(NonConfigurableConstants.MAIN_CONTACT_TYPE_BILLING)){
				
				if(persDetail.getMstbMasterTableByMainContactRace() != null) {
					contact.setMstbMasterTableByMainContactRace(persDetail.getMstbMasterTableByMainContactRace());
				}
				
				if(persDetails.get("billSame")!=null){
					contact.setAddressBlock((String)persDetails.get("blkNo"));
					contact.setAddressUnit((String)persDetails.get("unitNo"));
					contact.setAddressStreet((String)persDetails.get("street"));
					contact.setAddressBuilding((String)persDetails.get("building"));
					contact.setAddressArea((String)persDetails.get("area"));
					contact.setMstbMasterTableByAddressCountry(ConfigurableConstants.getMasterTable(ConfigurableConstants.COUNTRY_MASTER_CODE, (String)persDetails.get("countryCode")));
					contact.setAddressCity((String)persDetails.get("city"));
					contact.setAddressState((String)persDetails.get("state"));
					contact.setAddressPostal((String)persDetails.get("postal"));
				}else{
					contact.setAddressBlock((String)persDetails.get("billBlkNo"));
					contact.setAddressUnit((String)persDetails.get("billUnitNo"));
					contact.setAddressStreet((String)persDetails.get("billStreet"));
					contact.setAddressBuilding((String)persDetails.get("billBuilding"));
					contact.setAddressArea((String)persDetails.get("billArea"));
					contact.setMstbMasterTableByAddressCountry(ConfigurableConstants.getMasterTable(ConfigurableConstants.COUNTRY_MASTER_CODE, (String)persDetails.get("billCountryCode")));
					contact.setAddressCity((String)persDetails.get("billCity"));
					contact.setAddressState((String)persDetails.get("billState"));
					contact.setAddressPostal((String)persDetails.get("billPostal"));
				}
			}
			if(mainContact.getComp_id().getMainContactType().equals(NonConfigurableConstants.MAIN_CONTACT_TYPE_SHIPPING)){
				if(persDetails.get("shipSame")!=null){
					contact.setAddressBlock((String)persDetails.get("blkNo"));
					contact.setAddressUnit((String)persDetails.get("unitNo"));
					contact.setAddressStreet((String)persDetails.get("street"));
					contact.setAddressBuilding((String)persDetails.get("building"));
					contact.setAddressArea((String)persDetails.get("area"));
					contact.setMstbMasterTableByAddressCountry(ConfigurableConstants.getMasterTable(ConfigurableConstants.COUNTRY_MASTER_CODE, (String)persDetails.get("countryCode")));
					contact.setAddressCity((String)persDetails.get("city"));
					contact.setAddressState((String)persDetails.get("state"));
					contact.setAddressPostal((String)persDetails.get("postal"));
					if(persDetail.getMstbMasterTableByMainContactRace() != null) {
						contact.setMstbMasterTableByMainContactRace(persDetail.getMstbMasterTableByMainContactRace());
					}
				}else{
					contact.setAddressBlock((String)persDetails.get("shipBlkNo"));
					contact.setAddressUnit((String)persDetails.get("shipUnitNo"));
					contact.setAddressStreet((String)persDetails.get("shipStreet"));
					contact.setAddressBuilding((String)persDetails.get("shipBuilding"));
					contact.setAddressArea((String)persDetails.get("shipArea"));
					contact.setMstbMasterTableByAddressCountry(ConfigurableConstants.getMasterTable(ConfigurableConstants.COUNTRY_MASTER_CODE, (String)persDetails.get("shipCountryCode")));
					contact.setAddressCity((String)persDetails.get("shipCity"));
					contact.setAddressState((String)persDetails.get("shipState"));
					contact.setAddressPostal((String)persDetails.get("shipPostal"));
				}
			}
			this.update(contact, userId);
		}
		return true;
	}
	
	public boolean updatePersWithCNII(Map<String, Object> persDetails){
		logger.info("updatePers(Map<String, Object> persDetails)");
		AmtbAccount acct = this.daoHelper.getAccountDao().getAccount((String)persDetails.get("custNo"), 0, null, null);
		AmtbPersonalDetail persDetail = this.daoHelper.getAccountDao().getPersonalDetail(acct);
		String currentAcctName = acct.getAccountName();
		acct.setAccountName((String)persDetails.get("acctName"));
		acct.setNameOnCard((String)persDetails.get("nameOnCard"));
		persDetail.setNric((String)persDetails.get("nric"));
		if(persDetails.get("birthdate")!=null){
			persDetail.setBirthDt(DateUtil.convertDateToTimestamp((Date)persDetails.get("birthdate")));
		}
		persDetail.setTel((String)persDetails.get("tel"));
		persDetail.setAddressBlock((String)persDetails.get("blkNo"));
		persDetail.setAddressUnit((String)persDetails.get("unitNo"));
		persDetail.setAddressStreet((String)persDetails.get("street"));
		persDetail.setAddressBuilding((String)persDetails.get("building"));
		persDetail.setAddressArea((String)persDetails.get("area"));
		persDetail.setMstbMasterTableByAddressCountry(ConfigurableConstants.getMasterTable(ConfigurableConstants.COUNTRY_MASTER_CODE, (String)persDetails.get("countryCode")));
		persDetail.setAddressCity((String)persDetails.get("city"));
		persDetail.setAddressState((String)persDetails.get("state"));
		persDetail.setAddressPostal((String)persDetails.get("postal"));
		acct.setMstbMasterTableByInformationSource(ConfigurableConstants.getMasterTable(ConfigurableConstants.INFORMATION_SOURCE_MASTER_CODE, (String)persDetails.get("infoSourceCode")));
		persDetail.setMstbMasterTableByJobStatus(ConfigurableConstants.getMasterTable(ConfigurableConstants.JOB_STATUS_MASTER_CODE, (String)persDetails.get("jobStatusCode")));
		persDetail.setOccupation((String)persDetails.get("occupation"));
		persDetail.setMstbMasterTableByIndustry(ConfigurableConstants.getMasterTable(ConfigurableConstants.INDUSTRY_MASTER_CODE, (String)persDetails.get("industryCode")));
		persDetail.setEmployerName((String)persDetails.get("employerName"));
		persDetail.setEmployerBlock((String)persDetails.get("empBlkNo"));
		persDetail.setEmployerUnit((String)persDetails.get("empUnitNo"));
		persDetail.setEmployerStreet((String)persDetails.get("empStreet"));
		persDetail.setEmployerBuilding((String)persDetails.get("empBuilding"));
		persDetail.setEmployerArea((String)persDetails.get("empArea"));
		persDetail.setMstbMasterTableByEmployerCountry(ConfigurableConstants.getMasterTable(ConfigurableConstants.COUNTRY_MASTER_CODE, (String)persDetails.get("empCountryCode")));
		persDetail.setEmployerCity((String)persDetails.get("empCity"));
		persDetail.setEmployerState((String)persDetails.get("empState"));
		persDetail.setEmployerPostal((String)persDetails.get("empPostal"));
		persDetail.setMonthlyIncome((BigDecimal)persDetails.get("monthlyIncome"));
		acct.setRecurringFlag((String)persDetails.get("recurring"));
		
		if(persDetails.get("recurringChargeDay") != null && ((String)persDetails.get("recurring")).equals("Y"))
		{
			String recurringChargeDayString = (String)persDetails.get("recurringChargeDay");
			acct.setRecurringChargeDay(Integer.parseInt(recurringChargeDayString.substring(0, recurringChargeDayString.length() - 2)));
		}
		else
			acct.setRecurringChargeDay(null);
		
		if(persDetails.get("empLength")!=null){
			persDetail.setEmployLengthYear(((BigDecimal)persDetails.get("empLength")).intValue());
		}else{
			persDetail.setEmployLengthYear(0);
		}
		List<AmtbAcctMainContact> mainContacts = this.daoHelper.getAccountDao().getMainContacts(acct.getAccountNo());
		String userId = (String)persDetails.get("userId");
		this.daoHelper.getAccountDao().update(persDetail);
		this.daoHelper.getAccountDao().update(acct, userId);
		
		List<String> cardLessProductList = new ArrayList<String>();
		List<PmtbProductType> cardlessProducts = this.businessHelper.getProductTypeBusiness().getCardlessProductType();
		for(PmtbProductType cardlessProduct : cardlessProducts) {
			cardLessProductList.add(cardlessProduct.getProductTypeId().trim());
		}
		
		// if there is a change in name, send to CNII
		if (currentAcctName!= null && !currentAcctName.equalsIgnoreCase((String) persDetails.get("acctName")))
		{
			// Call CNII Synchronise interface
			Map<String, String> prop = (Map)SpringUtil.getBean("webserviceProperties");
			if (NonConfigurableConstants.BOOLEAN_NO.equals((String) prop.get("ws.cnii.disable")))
			{
				// Not disabled - so send interface
				try
				{
					for(AmtbSubscTo subscription : acct.getAmtbSubscTos())
					{
						PmtbProductType productType = subscription.getComp_id().getPmtbProductType();
//						if(productType.getProductTypeId().equals(NonConfigurableConstants.PREMIER_SERVICE))
						if(cardLessProductList.contains(productType.getProductTypeId()))
						{
							ArrayList<IttbCniiAcctReq> cniiList = new ArrayList<IttbCniiAcctReq>();
							IttbCniiAcctReq cniiRequest = new IttbCniiAcctReq();
							cniiRequest.setReqId(DateUtil.convertDateToStr(DateUtil.getCurrentDate(), DateUtil.GLOBAL_CNII_SEQ_FORMAT) + this.daoHelper.getAccountDao().getNextSequenceNo(Sequence.CNII_REQ_NO_SEQUENCE));
							cniiRequest.setReqDate(DateUtil.getCurrentTimestamp());
							cniiRequest.setEventType(NonConfigurableConstants.CNII_REQUEST_EVENT_TYPE_ACTIVATE);
							cniiRequest.setReqStatus(NonConfigurableConstants.CNII_REQUEST_STATUS_PENDING);
							cniiRequest.setValidFlg(NonConfigurableConstants.BOOLEAN_YES);
							cniiRequest.setUpdatedBy(NonConfigurableConstants.CNII_REQUEST_UPDATED_BY_IBS);
							StringBuffer acctId = new StringBuffer();
							StringBuffer parentId = new StringBuffer();
							if(acct.getCustNo()!=null && acct.getCustNo().length()!=0){
								acctId.append(acct.getCustNo());
								// Should not have parent since acct is the top-most account
								//parentId.append(acct.getCustNo());
							}else{
								acctId.append(acct.getCode());
								// Not required to append as parent is always without the last code
								// e.g. if dept = 304DIV1DEP1, parent will be 304DIV1
								//parentId.append(acct.getCode());
								// now getting the parent
								AmtbAccount parent = acct.getAmtbAccount();
								if(parent!=null){// this shouldn't happen
									while(parent.getCustNo()==null){
										acctId.insert(0, parent.getCode());
										parentId.insert(0, parent.getCode());
										parent = this.daoHelper.getAccountDao().getParent(parent).getAmtbAccount();
									}
									acctId.insert(0, parent.getCustNo());
									parentId.insert(0, parent.getCustNo());
								}
							}
							cniiRequest.setAccountId(acctId.toString());
							if(acct.getCustNo()!=null && acct.getCustNo().length()!=0){
								cniiRequest.setAccountCd(acct.getCustNo());
							}else{
								cniiRequest.setAccountCd(acct.getCode());
							}
							cniiRequest.setAccountNm(acct.getAccountName());
							cniiRequest.setParentId(parentId.toString());
							cniiRequest.setUpdatedDt(DateUtil.getCurrentTimestamp());
							//this.save(cniiRequest);
							cniiList.add(cniiRequest);
//							IBSCNIIUpdateAcctClient.send(cniiList);
							
							try
							{
								//29 Aug 2018 CNII Acct Sync
								String msg = updateCniiAcctSyncProcedure(cniiList);
								logger.info("CNII Acct Sync log : "+msg);
							} catch (Exception e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
								throw new CniiInterfaceException("CNII Interface Acct Sync Error - "+e);
							}
						}
					}
				}
				catch (Exception e)
				{
					logger.error(e.getMessage());
				}
			}else{
				logger.info("CNII interface is disabled");
			}
		}
		
		// setting the common fields
		for(AmtbAcctMainContact mainContact : mainContacts){
			AmtbContactPerson contact = mainContact.getAmtbContactPerson();
			contact.setMstbMasterTableByMainContactSal(ConfigurableConstants.getMasterTable(ConfigurableConstants.SALUTATION_MASTER_CODE, (String)persDetails.get("salutationCode")));
			contact.setMstbMasterTableByMainContactRace(ConfigurableConstants.getMasterTable(ConfigurableConstants.RACE_MASTER_CODE, (String)persDetails.get("race")));
			contact.setMainContactEmail((String)persDetails.get("email"));
			contact.setMainContactMobile((String)persDetails.get("mobile"));
			contact.setMainContactTel((String)persDetails.get("office"));
			// now specific address
			if(mainContact.getComp_id().getMainContactType().equals(NonConfigurableConstants.MAIN_CONTACT_TYPE_BILLING)){
				if(persDetails.get("billSame")!=null){
					contact.setAddressBlock((String)persDetails.get("blkNo"));
					contact.setAddressUnit((String)persDetails.get("unitNo"));
					contact.setAddressStreet((String)persDetails.get("street"));
					contact.setAddressBuilding((String)persDetails.get("building"));
					contact.setAddressArea((String)persDetails.get("area"));
					contact.setMstbMasterTableByAddressCountry(ConfigurableConstants.getMasterTable(ConfigurableConstants.COUNTRY_MASTER_CODE, (String)persDetails.get("countryCode")));
					contact.setAddressCity((String)persDetails.get("city"));
					contact.setAddressState((String)persDetails.get("state"));
					contact.setAddressPostal((String)persDetails.get("postal"));
				}else{
					contact.setAddressBlock((String)persDetails.get("billBlkNo"));
					contact.setAddressUnit((String)persDetails.get("billUnitNo"));
					contact.setAddressStreet((String)persDetails.get("billStreet"));
					contact.setAddressBuilding((String)persDetails.get("billBuilding"));
					contact.setAddressArea((String)persDetails.get("billArea"));
					contact.setMstbMasterTableByAddressCountry(ConfigurableConstants.getMasterTable(ConfigurableConstants.COUNTRY_MASTER_CODE, (String)persDetails.get("billCountryCode")));
					contact.setAddressCity((String)persDetails.get("billCity"));
					contact.setAddressState((String)persDetails.get("billState"));
					contact.setAddressPostal((String)persDetails.get("billPostal"));
				}
			}
			if(mainContact.getComp_id().getMainContactType().equals(NonConfigurableConstants.MAIN_CONTACT_TYPE_SHIPPING)){
				if(persDetails.get("shipSame")!=null){
					contact.setAddressBlock((String)persDetails.get("blkNo"));
					contact.setAddressUnit((String)persDetails.get("unitNo"));
					contact.setAddressStreet((String)persDetails.get("street"));
					contact.setAddressBuilding((String)persDetails.get("building"));
					contact.setAddressArea((String)persDetails.get("area"));
					contact.setMstbMasterTableByAddressCountry(ConfigurableConstants.getMasterTable(ConfigurableConstants.COUNTRY_MASTER_CODE, (String)persDetails.get("countryCode")));
					contact.setAddressCity((String)persDetails.get("city"));
					contact.setAddressState((String)persDetails.get("state"));
					contact.setAddressPostal((String)persDetails.get("postal"));
				}else{
					contact.setAddressBlock((String)persDetails.get("shipBlkNo"));
					contact.setAddressUnit((String)persDetails.get("shipUnitNo"));
					contact.setAddressStreet((String)persDetails.get("shipStreet"));
					contact.setAddressBuilding((String)persDetails.get("shipBuilding"));
					contact.setAddressArea((String)persDetails.get("shipArea"));
					contact.setMstbMasterTableByAddressCountry(ConfigurableConstants.getMasterTable(ConfigurableConstants.COUNTRY_MASTER_CODE, (String)persDetails.get("shipCountryCode")));
					contact.setAddressCity((String)persDetails.get("shipCity"));
					contact.setAddressState((String)persDetails.get("shipState"));
					contact.setAddressPostal((String)persDetails.get("shipPostal"));
				}
			}
			this.update(contact, userId);
		}
		return true;
	}
	
	public Map<String, Map<String, String>> getDepositDetails(String custNo){
		Map<String, Map<String, String>> returnMap = new HashMap<String, Map<String, String>>();
		AmtbAccount acct = this.daoHelper.getAccountDao().getDepositDetails(custNo);
		BigDecimal collectedDeposit = new BigDecimal(0);
		if(acct!=null){
			for(BmtbInvoiceDepositTxn depositTxn : acct.getBmtbInvoiceDepositTxns()){
				BmtbInvoiceHeader header = depositTxn.getBmtbInvoiceHeader();
				Map<String, String> invoice = new HashMap<String, String>();
				invoice.put("invoiceNo", header.getInvoiceNo().toString());
				invoice.put("invoiceAmt", StringUtil.bigDecimalToString(header.getNewTxn(),StringUtil.GLOBAL_DECIMAL_FORMAT));
				invoice.put("invoiceDate", DateUtil.convertDateToStr(header.getInvoiceDate(), DateUtil.GLOBAL_DATE_FORMAT));
				invoice.put("paidAmt", StringUtil.bigDecimalToString(header.getNewTxn().subtract(header.getOutstandingAmount()),StringUtil.GLOBAL_DECIMAL_FORMAT));
				invoice.put("outstandingAmt", StringUtil.bigDecimalToString(header.getOutstandingAmount(), StringUtil.GLOBAL_DECIMAL_FORMAT));
				invoice.put("invoiceStatus", NonConfigurableConstants.INVOICE_STATUS.get(header.getInvoiceStatus()));
				collectedDeposit = collectedDeposit.add(header.getNewTxn().subtract(header.getOutstandingAmount()));
				returnMap.put(header.getInvoiceNo().toString(), invoice);
			}
		}
		Map<String, String> depositAmts = new HashMap<String, String>();
		if(acct != null) {
			depositAmts.put("requiredDeposit", StringUtil.bigDecimalToString(acct.getDeposit(), StringUtil.GLOBAL_DECIMAL_FORMAT));
			depositAmts.put("collectedDeposit", StringUtil.bigDecimalToString(collectedDeposit, StringUtil.GLOBAL_DECIMAL_FORMAT));
		}
		else
		{
			depositAmts.put("requiredDeposit", StringUtil.bigDecimalToString(new BigDecimal("0.00"), StringUtil.GLOBAL_DECIMAL_FORMAT));
			depositAmts.put("collectedDeposit", StringUtil.bigDecimalToString(new BigDecimal("0.00"), StringUtil.GLOBAL_DECIMAL_FORMAT));
		}
		returnMap.put("depositAmts", depositAmts);
		return returnMap;
	}
	public BmtbInvoiceHeader generateDepositInvoice(BigDecimal depositAmt, Date invoiceDate, String custNo, String userId) throws Exception{
		AmtbAccount acct = this.daoHelper.getAccountDao().getAccount(custNo, 0, null, null);
		
		if(!this.daoHelper.getInvoiceDao().checkGLDepositTxnCodeAvailable(acct.getFmtbArContCodeMaster().getFmtbEntityMaster().getEntityNo()))
			throw new Exception("No invoice deposit GL txn code found! Unable to create deposit invoice!");
		
		BmtbInvoiceHeader invoiceHeader = this.daoHelper.getInvoiceDao().generateDepositInvoice(depositAmt, invoiceDate, acct, userId);
		
		//GIRO date
		if(acct.getMstbMasterTableByDefaultPaymentMode().getMasterCode()
				.equals(NonConfigurableConstants.PAYMENT_MODE_INTERBANK_GIRO)){
			
			IttbGiroSetup giroSetup = this.daoHelper.getAdminDao().getInvoiceGiroDay(
					acct.getFmtbArContCodeMaster().getFmtbEntityMaster().getEntityNo());
			
			if(giroSetup == null)
				throw new Exception("GIRO SETUP is not found");
			
			invoiceHeader.setGiroBankAcctNo(acct.getMstbBankMaster().getBankName()+" "+acct.getBankAcctNo());
			
			Calendar giroDateCal = Calendar.getInstance();
			giroDateCal.setTime(invoiceDate);
			
			int giroDay = giroSetup.getInvoiceGiroDay();
			int lastDayOfMonth = giroDateCal.getActualMaximum(Calendar.DAY_OF_MONTH);
			if(lastDayOfMonth < giroSetup.getInvoiceGiroDay()) giroDay = lastDayOfMonth;
			
			invoiceHeader.setGiroDay(DateUtil.getDayInStr(giroDay));
			
			this.update(invoiceHeader);
		}
		
		return invoiceHeader;
	}
	public String activateAcct(String custNo, String userId) throws Exception{
		// checking main billing and shipping contact
		AmtbAccount acct = this.daoHelper.getAccountDao().getAccount(custNo, 0, null, null);
		boolean billing = false, shipping = false;
		for (AmtbAcctMainContact mainContact : acct.getAmtbAcctMainContacts()) {
			if(mainContact.getComp_id().getMainContactType().equals(NonConfigurableConstants.MAIN_CONTACT_TYPE_BILLING)){
				billing = true;
			}else if(mainContact.getComp_id().getMainContactType().equals(NonConfigurableConstants.MAIN_CONTACT_TYPE_SHIPPING)){
				shipping = true;
			}
		}
		if(!billing){
			return NonConfigurableConstants.ACCOUNT_ACTIVATION_ERROR_MAIN_BILLING;
		}
		if(!shipping){
			return NonConfigurableConstants.ACCOUNT_ACTIVATION_ERROR_MAIN_SHIPPING;
		}
		acct = this.daoHelper.getAccountDao().getBillingDetails(custNo);
		if(acct.getAmtbAcctBillCycles().size() == 0){
			return NonConfigurableConstants.ACCOUNT_ACTIVATION_ERROR_BILLING;
		}else{
			boolean hasCycle = false;
			for(AmtbAcctBillCycle cycle : acct.getAmtbAcctBillCycles()){
				if(cycle.getEffectiveDate().before(DateUtil.getCurrentDate())){
					hasCycle = true;
					break;
				}
			}
			if(!hasCycle){
				return NonConfigurableConstants.ACCOUNT_ACTIVATION_ERROR_BILLING;
			}
		}
		if(acct.getAmtbAcctCredTerms().size() == 0){
			return NonConfigurableConstants.ACCOUNT_ACTIVATION_ERROR_BILLING;
		}else{
			boolean hasCredit = false;
			for(AmtbAcctCredTerm creditTerm : acct.getAmtbAcctCredTerms()){
				if(creditTerm.getEffectiveDate().before(DateUtil.getCurrentDate())){
					hasCredit = true;
				}
			}
			if(!hasCredit){
				return NonConfigurableConstants.ACCOUNT_ACTIVATION_ERROR_BILLING;
			}
		}
		// all checked. can activate now.
		Map<Integer, Map<String, String>> premiers = new TreeMap<Integer, Map<String, String>>(new Comparator<Integer>(){
			public int compare(Integer o1, Integer o2) {
				return o1.compareTo(o2);
			}
		});
		// checking premier service
		insertPremierServiceNewRequest(acct, null, premiers);
		// adding new status
		AmtbAcctStatus newStatus = new AmtbAcctStatus();
		newStatus.setAmtbAccount(acct);
		newStatus.setAcctStatus(NonConfigurableConstants.ACCOUNT_STATUS_ACTIVE);
		newStatus.setEffectiveDt(DateUtil.getCurrentTimestamp());
		SatbUser user = this.daoHelper.getUserDao().getUserWithRoles(CommonWindow.getUserId());
		newStatus.setSatbUser(user);
		this.save(newStatus);
		// sending to AS for creating new account
		String accountType;
		if(acct.getAccountCategory().equals(NonConfigurableConstants.ACCOUNT_CATEGORY_CORPORATE)){
			accountType = API.ACCOUNT_TYPE_CORPORATE;
		}else{
			accountType = API.ACCOUNT_TYPE_PERSONAL;
		}
		API.createAccount(API.formulateAccountId(acct), accountType, acct.getCreditLimit().toString(), null, userId);
		// divisions
		List<AmtbAccount> divisions = null;
		//if (NonConfigurableConstants.ACCOUNT_CATEGORY_CORPORATE.equals(acct.getAccountCategory()))
		//{
			divisions = this.daoHelper.getAccountDao().getChildrenAccountsWithSubscriptionsAndMainContacts(acct.getAccountNo());
		//}
		
		for(AmtbAccount division : divisions){
			newStatus = new AmtbAcctStatus();
			newStatus.setAmtbAccount(division);
			newStatus.setAcctStatus(NonConfigurableConstants.ACCOUNT_STATUS_ACTIVE);
			newStatus.setEffectiveDt(DateUtil.getCurrentTimestamp());
			newStatus.setSatbUser(user);
			this.save(newStatus);
			// credit limit
			AmtbAcctCredLimit creditLimit = new AmtbAcctCredLimit();
			creditLimit.setAmtbAccount(division);
			division.getAmtbAcctCredLimits().add(creditLimit);
			creditLimit.setCreditLimitType(NonConfigurableConstants.CREDIT_LIMIT_PERMANENT);
			creditLimit.setEffectiveDtFrom(DateUtil.getCurrentTimestamp());
			creditLimit.setNewCreditLimit(division.getCreditLimit());
			creditLimit.setSatbUser(user);
			creditLimit.setRemarks("INITIAL CREDIT LIMIT");
			// now getting all children accounts and set them to active
			// only for corporate. Personal will only send main account
			// Change : Personal will send main and sub account also
			if(acct.getAccountCategory().equals(NonConfigurableConstants.ACCOUNT_CATEGORY_CORPORATE)){
				insertPremierServiceNewRequest(division, acct.getCustNo(), premiers);
				// sending to AS for creating new account
				if(division.getAccountCategory().equals(NonConfigurableConstants.ACCOUNT_CATEGORY_DIVISION)){
					accountType = API.ACCOUNT_TYPE_DIVISION;
				}else{
					accountType = API.ACCOUNT_TYPE_SUBAPPLICANT;
				}
				API.createAccount(acct.getCustNo()+division.getCode(), accountType, division.getCreditLimit().toString(), acct.getCustNo(), userId);
				// dept
				List<AmtbAccount> depts = this.daoHelper.getAccountDao().getChildrenAccountsWithSubscriptionsAndMainContacts(division.getAccountNo());
				for(AmtbAccount dept : depts){
					newStatus = new AmtbAcctStatus();
					newStatus.setAmtbAccount(dept);
					newStatus.setAcctStatus(NonConfigurableConstants.ACCOUNT_STATUS_ACTIVE);
					newStatus.setEffectiveDt(DateUtil.getCurrentTimestamp());
					newStatus.setSatbUser(user);
					this.save(newStatus);
					// credit limit
					creditLimit = new AmtbAcctCredLimit();
					creditLimit.setAmtbAccount(dept);
					dept.getAmtbAcctCredLimits().add(creditLimit);
					creditLimit.setCreditLimitType(NonConfigurableConstants.CREDIT_LIMIT_PERMANENT);
					creditLimit.setEffectiveDtFrom(DateUtil.getCurrentTimestamp());
					creditLimit.setNewCreditLimit(dept.getCreditLimit());
					creditLimit.setSatbUser(user);
					creditLimit.setRemarks("INITIAL CREDIT LIMIT");
					insertPremierServiceNewRequest(dept, acct.getCustNo() + division.getCode(), premiers);
					API.createAccount(acct.getCustNo()+division.getCode()+dept.getCode(), API.ACCOUNT_TYPE_DEPARTMENT, dept.getCreditLimit().toString(), acct.getCustNo()+division.getCode(), userId);
				}
			}
			else
			{
				if(division.getAccountCategory().equals(NonConfigurableConstants.ACCOUNT_CATEGORY_DIVISION)){
					accountType = API.ACCOUNT_TYPE_DIVISION;
				}else{
					accountType = API.ACCOUNT_TYPE_SUBAPPLICANT;
				}
				API.createAccount(acct.getCustNo()+division.getCode(), accountType, division.getCreditLimit().toString(), acct.getCustNo(), userId);
			}
		}
		// insert into CNII
		// For CNII list
		ArrayList<IttbCniiAcctReq> cniiList = new ArrayList<IttbCniiAcctReq>();
		for(Integer accountNo : premiers.keySet()){
			Map<String, String> acctMap = premiers.get(accountNo);
			IttbCniiAcctReq cniiRequest = new IttbCniiAcctReq();
			cniiRequest.setReqId(DateUtil.convertDateToStr(DateUtil.getCurrentDate(), DateUtil.GLOBAL_CNII_SEQ_FORMAT) + this.daoHelper.getAccountDao().getNextSequenceNo(Sequence.CNII_REQ_NO_SEQUENCE));
			cniiRequest.setReqDate(DateUtil.getCurrentTimestamp());
			cniiRequest.setEventType(NonConfigurableConstants.CNII_REQUEST_EVENT_TYPE_NEW);
			cniiRequest.setReqStatus(NonConfigurableConstants.CNII_REQUEST_STATUS_PENDING);
			cniiRequest.setValidFlg(NonConfigurableConstants.BOOLEAN_YES);
			cniiRequest.setUpdatedBy(NonConfigurableConstants.CNII_REQUEST_UPDATED_BY_IBS);
			cniiRequest.setAccountId(acctMap.get("acctId"));
			cniiRequest.setAccountCd(acctMap.get("acctCode"));
			cniiRequest.setAccountNm(acctMap.get("acctName"));
			cniiRequest.setParentId(acctMap.get("parentId"));
			cniiRequest.setUpdatedDt(DateUtil.getCurrentTimestamp());
			//this.save(cniiRequest);
			cniiList.add(cniiRequest);
		}
		
		// Call CNII Synchronise interface
		Map<String, String> prop = (Map)SpringUtil.getBean("webserviceProperties");
		if (NonConfigurableConstants.BOOLEAN_NO.equals((String) prop.get("ws.cnii.disable")))
		{
			// Not disabled - so send interface
			try
			{
//				IBSCNIIUpdateAcctClient.send(cniiList);
				
				//29 Aug 2018 CNII Acct Sync
				String msg = updateCniiAcctSyncProcedure(cniiList);
				logger.info("CNII Acct Sync log : "+msg);
			}
			catch (Exception e)
			{
				logger.error(e.getMessage());
				return NonConfigurableConstants.ACCOUNT_ACTIVATION_INTERFACE_ERROR;
			}
		}else{
			logger.info("CNII interface is disabled");
		}
			
		
		return NonConfigurableConstants.ACCOUNT_ACTIVATION_NO_ERROR;
	}
	private void insertPremierServiceNewRequest(AmtbAccount acct, String prefix, Map<Integer, Map<String, String>> premiers){
		boolean hasPS = false;
		List<String> cardLessProductList = new ArrayList<String>();
		List<PmtbProductType> cardlessProducts = this.businessHelper.getProductTypeBusiness().getCardlessProductType();
		for(PmtbProductType cardlessProduct : cardlessProducts) {
			cardLessProductList.add(cardlessProduct.getProductTypeId().trim());
		}
		
		for(AmtbSubscTo subscription : acct.getAmtbSubscTos()){
			if(cardLessProductList.contains(subscription.getComp_id().getPmtbProductType().getProductTypeId())){
				hasPS = true;
				break;
			}
		}
		if(hasPS){
			Map<String, String> acctMap = new HashMap<String, String>();
			if(acct.getCustNo()!=null){
				acctMap.put("acctId", acct.getCustNo());
			}else{
				acctMap.put("acctId", prefix + acct.getCode());
			}
			if(acct.getCustNo()!=null){
				acctMap.put("acctCode", acct.getCustNo());
			}else{
				acctMap.put("acctCode", acct.getCode());
			}
			acctMap.put("acctName", acct.getAccountName());
			acctMap.put("parentId", prefix);
			premiers.put(acct.getAccountNo(), acctMap);
		}
	}
	public String getContactName(Integer contactPersonNo){
		AmtbContactPerson contact = this.daoHelper.getAccountDao().getContact(contactPersonNo);
		return getContactCombinedNames(contact);
	}
	public boolean checkCode(String custNo, int level, String parentCode, String code){
		List<AmtbAccount> accts = this.daoHelper.getAccountDao().getAccounts(custNo, parentCode, level);
		boolean found = false;
		for(AmtbAccount acct : accts){
			if(code.equals(acct.getCode())){
				found = true;
				break;
			}
		}
		return found;
	}
	public Map<Integer, Map<String, Object>> getAccounts(String custNo, String parentCode, int level){
		logger.info("getAccounts(String custNo, String parentCode, int level)");
		logger.info("getAccounts(custNo = "+custNo+", parentCode = "+parentCode+", level = "+level+")");
		List<AmtbAccount> accts = this.daoHelper.getAccountDao().getAccounts(custNo, parentCode, level);
		Map<Integer, Map<String, Object>> returnMap = new HashMap<Integer, Map<String, Object>>();
		for(AmtbAccount acct : accts){
			Map<String, Object> acctMap = new HashMap<String, Object>();
			acctMap.put("acctName", acct.getAccountName());
			acctMap.put("acctCode", acct.getCode());
			acctMap.put("acctCategory", acct.getAccountCategory());
			acctMap.put("creditBalance", acct.getCreditBalance());
			acctMap.put("creditLimit", acct.getCreditLimit());
			if (acct.getAmtbAcctStatuses().size() > 0) {
				TreeSet<AmtbAcctStatus> sortedStatus = new TreeSet<AmtbAcctStatus>(new Comparator<AmtbAcctStatus>(){
					public int compare(AmtbAcctStatus o1, AmtbAcctStatus o2) {
						return o1.getEffectiveDt().compareTo(o2.getEffectiveDt());
					}
				});
				for(AmtbAcctStatus status : acct.getAmtbAcctStatuses()){
					if(status.getEffectiveDt().before(DateUtil.getCurrentTimestamp())){
						sortedStatus.add(status);
					}
				}
				acctMap.put("acctStatus", sortedStatus.last().getAcctStatus());
			} else {
				acctMap.put("acctStatus", null);
			}
			returnMap.put(acct.getAccountNo(), acctMap);
		}
		return returnMap;
	}
	public Map<String, Map<String, String>> searchPersAccounts(String parentCustNo, String acctName, String nric, String email){
		List<AmtbAccount> accts = this.daoHelper.getAccountDao().getPersAccounts(parentCustNo, acctName, nric, email);
		Map<String, Map<String, String>> returnMap = new HashMap<String, Map<String, String>>();
		for(AmtbAccount acct : accts){
			Map<String, String> acctDetails = new HashMap<String, String>();
			acctDetails.put("acctName", acct.getAccountName());
			acctDetails.put("nameOncard", acct.getNameOnCard());
			if(acct.getAmtbPersonalDetails().iterator().hasNext()){
				acctDetails.put("nric", acct.getAmtbPersonalDetails().iterator().next().getNric());
			}
			if(acct.getAmtbContactPersons().iterator().hasNext()){
				String contactEmail = acct.getAmtbContactPersons().iterator().next().getMainContactEmail();
				acctDetails.put("email", contactEmail);
			}
			returnMap.put(acct.getAccountNo().toString(), acctDetails);
		}
		return returnMap;
	}
	public Map<String, Object> getSubPersAccount(String acctNo){
		AmtbAccount acct = this.daoHelper.getAccountDao().getAccount(Integer.parseInt(acctNo));
		if(acct!=null){
			Map<String, Object> returnMap = new HashMap<String, Object>();
			returnMap.put("acctName", acct.getAccountName());
			returnMap.put("nameOnCard", acct.getNameOnCard());
			returnMap.put("creditLimit", acct.getCreditLimit());
			returnMap.put("creditBalance", StringUtil.bigDecimalToString(acct.getCreditBalance(), StringUtil.GLOBAL_DECIMAL_FORMAT));
			if(acct.getAmtbPersonalDetails().iterator().hasNext()){
				AmtbPersonalDetail persDetail = acct.getAmtbPersonalDetails().iterator().next();
				returnMap.put("nric", persDetail.getNric());
				returnMap.put("birthdate", persDetail.getBirthDt());
				if(persDetail.getMstbMasterTableByRelationToParent()!=null){
					returnMap.put("relationCode", persDetail.getMstbMasterTableByRelationToParent().getMasterCode());
				}

				returnMap.put("tel", persDetail.getTel());
				returnMap.put("blkNo", persDetail.getAddressBlock());
				returnMap.put("unitNo", persDetail.getAddressUnit());
				returnMap.put("street", persDetail.getAddressStreet());
				returnMap.put("building", persDetail.getAddressBuilding());
				returnMap.put("area", persDetail.getAddressArea());
				if(persDetail.getMstbMasterTableByAddressCountry()!=null){
					returnMap.put("countryCode", persDetail.getMstbMasterTableByAddressCountry().getMasterCode());
				}
				returnMap.put("city", persDetail.getAddressCity());
				returnMap.put("state", persDetail.getAddressState());
				returnMap.put("postal", persDetail.getAddressPostal());
			}
			if(acct.getAmtbContactPersons().iterator().hasNext()){
				AmtbContactPerson contact = acct.getAmtbContactPersons().iterator().next();
				if(contact.getMstbMasterTableByMainContactSal()!=null){
					returnMap.put("salCode", contact.getMstbMasterTableByMainContactSal().getMasterCode());
				}
				if(contact.getMstbMasterTableByMainContactRace()!=null){
					returnMap.put("race", contact.getMstbMasterTableByMainContactRace().getMasterCode());
				}
				returnMap.put("email", contact.getMainContactEmail());
				returnMap.put("mobile", contact.getMainContactMobile());
				returnMap.put("office", contact.getMainContactTel());
			}
			Set<String> subscribed = new HashSet<String>();
			// product subscription
			for (AmtbSubscTo AmtbSubscTo : acct.getAmtbSubscTos()) {
				subscribed.add(AmtbSubscTo.getComp_id().getPmtbProductType().getProductTypeId());
			}
			returnMap.put("productSubscriptions", subscribed);
			List<AmtbAcctStatus> statuses = this.daoHelper.getAccountDao().getStatuses(acct.getAccountNo());
			if(getCurrentStatus(statuses)!=null){
				returnMap.put("childStatus", getCurrentStatus(statuses).getAcctStatus());
			}
			
			returnMap.put("overdueReminder", acct.getOverdueReminder());
			if(acct.getReminderEmail() != null)
				returnMap.put("reminderEmail", acct.getReminderEmail());
			
			returnMap.put("eInvoiceEmailFlag", acct.geteInvoiceEmailFlag());
			returnMap.put("eInvoiceEmailZipFlag", acct.geteInvoiceEmailZipFlag());
			
			if(acct.geteInvoiceEmail() != null){
				returnMap.put("eInvoiceEmail", acct.geteInvoiceEmail());
			}
			
			if(acct.geteInvoiceEmailSubject() != null){
				returnMap.put("eInvoiceEmailSubject", acct.geteInvoiceEmailSubject());
			}
			
			returnMap.put("eInvoiceEmailAttachment", acct.geteInvoiceEmailAttachment());
			returnMap.put("eInvoiceEmailPage", acct.geteInvoiceEmailPage());

			returnMap.put("recurring", acct.getRecurringFlag());
			returnMap.put("recurringChargeDay", acct.getRecurringChargeDay());
			
			return returnMap;
		}
		return null;
	}
	public void deleteSubAccount(Integer accountNo, String userId){
		this.daoHelper.getAccountDao().deleteAccount(accountNo, userId);
	}
	public boolean createBillingRequest(String custNo, Map<Date, Map<String, Object>> billingDetails, Map<Date, Integer> creditTerms, Map<Date, Integer> earlyPymts, Map<Date, Integer> latePymts, Set<Map<String, Object>> promotions, Map<String, Object> bankInfo, String userId){
		logger.info("createBillingRequest(custNo = "+custNo+", billingDetails = " + billingDetails.size() + ", creditTerms = " + creditTerms.size() + ", earlyPymts = " + earlyPymts.size() + ", latePymts = " + latePymts.size() + ", promotions = " + promotions.size() + ", bankInfo = " + bankInfo.size() + ", userId = " + userId + ")");
		AmtbAccount acct = this.daoHelper.getAccountDao().getBillingDetails(custNo);
		AmtbBillReq billingRequest = new AmtbBillReq();
		// billing details
		if(billingDetails!=null){
			for(Date effectiveDate : billingDetails.keySet()){
				// skip if effective date is earlier than now.
				if(!DateUtil.isToday(effectiveDate) && effectiveDate.before(Calendar.getInstance().getTime())){
					continue;
				}
				Map<String, Object> billingDetail = billingDetails.get(effectiveDate);
				if(billingDetail.get("billingCycle")!=null){
					// searching for the corresponding billing cycle
					boolean found = false;
					for (AmtbAcctBillCycle billingCycle : acct.getAmtbAcctBillCycles()) {
						if(effectiveDate.equals(billingCycle.getEffectiveDate())){
							// found
							found = true;
							if(!billingDetail.get("billingCycle").equals(billingCycle.getBillingCycle())){
								// if not equals, create a new request
								AmtbAcctBillCycleReq cycleRequest = new AmtbAcctBillCycleReq();
								cycleRequest.setBillingCycle((String)billingDetail.get("billingCycle"));
								cycleRequest.setEffectiveDt(DateUtil.convertDateToTimestamp(effectiveDate));
								billingRequest.getAmtbAcctBillCycleReqs().add(cycleRequest);
								cycleRequest.setAmtbBillReq(billingRequest);
								cycleRequest.setEvent(NonConfigurableConstants.BILLING_REQUEST_EVENT_UPDATE);
							}
							break;
						}
					}
					// if not found,
					if(!found){
						AmtbAcctBillCycleReq cycleRequest = new AmtbAcctBillCycleReq();
						cycleRequest.setBillingCycle((String)billingDetail.get("billingCycle"));
						cycleRequest.setEffectiveDt(DateUtil.convertDateToTimestamp(effectiveDate));
						billingRequest.getAmtbAcctBillCycleReqs().add(cycleRequest);
						cycleRequest.setAmtbBillReq(billingRequest);
						cycleRequest.setEvent(NonConfigurableConstants.BILLING_REQUEST_EVENT_CREATE);
					}
				}
				boolean found = false;
				for(AmtbAcctVolDisc volumeDiscount : acct.getAmtbAcctVolDiscs()){
					if(effectiveDate.equals(volumeDiscount.getEffectiveDate())){
						// found
						found = true;
						// testing for null
						if((billingDetail.get("volumeDiscount")==volumeDiscount.getMstbVolDiscMaster())){
							break;
						}else if((
								(volumeDiscount.getMstbVolDiscMaster()==null && billingDetail.get("volumeDiscount")!=null) ||
								(billingDetail.get("volumeDiscount")==null) && volumeDiscount.getMstbVolDiscMaster()!=null) ||
								!billingDetail.get("volumeDiscount").equals(volumeDiscount.getMstbVolDiscMaster().getVolumeDiscountPlanNo())){
							// if not equals, create a new request
							AmtbAcctVolDiscReq volumeRequest = new AmtbAcctVolDiscReq();
							volumeRequest.setMstbVolDiscMaster((MstbVolDiscMaster)MasterSetup.getVolumeDiscountManager().getMaster((Integer)billingDetail.get("volumeDiscount")));
							volumeRequest.setEffectiveDt(DateUtil.convertDateToTimestamp(effectiveDate));
							billingRequest.getAmtbAcctVolDiscReqs().add(volumeRequest);
							volumeRequest.setAmtbBillReq(billingRequest);
							volumeRequest.setEvent(NonConfigurableConstants.BILLING_REQUEST_EVENT_UPDATE);
						}
						break;
					}
				}
				// if not found,
				if(!found){
					if(billingDetail.get("volumeDiscount")!=null || acct.getAmtbAcctVolDiscs().size()!=0){
						AmtbAcctVolDiscReq volumeRequest = new AmtbAcctVolDiscReq();
						volumeRequest.setMstbVolDiscMaster((MstbVolDiscMaster)MasterSetup.getVolumeDiscountManager().getMaster((Integer)billingDetail.get("volumeDiscount")));
						volumeRequest.setEffectiveDt(DateUtil.convertDateToTimestamp(effectiveDate));
						billingRequest.getAmtbAcctVolDiscReqs().add(volumeRequest);
						volumeRequest.setAmtbBillReq(billingRequest);
						volumeRequest.setEvent(NonConfigurableConstants.BILLING_REQUEST_EVENT_CREATE);
					}
				}
				found = false;
				for(AmtbAcctAdminFee adminFee : acct.getAmtbAcctAdminFees()){
					if(effectiveDate.equals(adminFee.getEffectiveDate())){
						// found
						found = true;
						if(!billingDetail.get("adminFee").equals(adminFee.getMstbAdminFeeMaster().getAdminFeePlanNo())){
							// if not equals, create a new request
							AmtbAcctAdminFeeReq adminRequest = new AmtbAcctAdminFeeReq();
							adminRequest.setMstbAdminFeeMaster((MstbAdminFeeMaster)MasterSetup.getAdminFeeManager().getMaster((Integer)billingDetail.get("adminFee")));
							adminRequest.setEffectiveDt(DateUtil.convertDateToTimestamp(effectiveDate));
							billingRequest.getAmtbAcctAdminFeeReqs().add(adminRequest);
							adminRequest.setAmtbBillReq(billingRequest);
							adminRequest.setEvent(NonConfigurableConstants.BILLING_REQUEST_EVENT_UPDATE);
						}
						break;
					}
				}
				// if not found,
				if(!found){
					if(billingDetail.get("adminFee")!=null || acct.getAmtbAcctAdminFees().size()!=0){
						AmtbAcctAdminFeeReq adminRequest = new AmtbAcctAdminFeeReq();
						adminRequest.setMstbAdminFeeMaster((MstbAdminFeeMaster)MasterSetup.getAdminFeeManager().getMaster((Integer)billingDetail.get("adminFee")));
						adminRequest.setEffectiveDt(DateUtil.convertDateToTimestamp(effectiveDate));
						billingRequest.getAmtbAcctAdminFeeReqs().add(adminRequest);
						adminRequest.setAmtbBillReq(billingRequest);
						adminRequest.setEvent(NonConfigurableConstants.BILLING_REQUEST_EVENT_CREATE);
					}
				}
			}
			for(AmtbAcctBillCycle billingCycle : acct.getAmtbAcctBillCycles()){
				boolean found = false;
				for(Date effectiveDate : billingDetails.keySet()){
					if(effectiveDate.equals(billingCycle.getEffectiveDate())){
						found = true;
						break;
					}
				}
				if(!found){
					AmtbAcctBillCycleReq cycleRequest = new AmtbAcctBillCycleReq();
					cycleRequest.setBillingCycle(billingCycle.getBillingCycle());
					cycleRequest.setEffectiveDt(DateUtil.convertDateToTimestamp(billingCycle.getEffectiveDate()));
					billingRequest.getAmtbAcctBillCycleReqs().add(cycleRequest);
					cycleRequest.setAmtbBillReq(billingRequest);
					cycleRequest.setEvent(NonConfigurableConstants.BILLING_REQUEST_EVENT_DELETE);
				}
			}
			for(AmtbAcctVolDisc volumeDiscount : acct.getAmtbAcctVolDiscs()){
				boolean found = false;
				for(Date effectiveDate : billingDetails.keySet()){
					if(effectiveDate.equals(volumeDiscount.getEffectiveDate())){
						found = true;
						break;
					}
				}
				if(!found){
					AmtbAcctVolDiscReq volumeRequest = new AmtbAcctVolDiscReq();
					volumeRequest.setMstbVolDiscMaster(volumeDiscount.getMstbVolDiscMaster());
					volumeRequest.setEffectiveDt(DateUtil.convertDateToTimestamp(volumeDiscount.getEffectiveDate()));
					billingRequest.getAmtbAcctVolDiscReqs().add(volumeRequest);
					volumeRequest.setAmtbBillReq(billingRequest);
					volumeRequest.setEvent(NonConfigurableConstants.BILLING_REQUEST_EVENT_DELETE);
				}
			}
			for(AmtbAcctAdminFee adminFee : acct.getAmtbAcctAdminFees()){
				boolean found = false;
				for(Date effectiveDate : billingDetails.keySet()){
					if(effectiveDate.equals(adminFee.getEffectiveDate())){
						found = true;
						break;
					}
				}
				if(!found){
					AmtbAcctAdminFeeReq adminRequest = new AmtbAcctAdminFeeReq();
					adminRequest.setMstbAdminFeeMaster(adminFee.getMstbAdminFeeMaster());
					adminRequest.setEffectiveDt(DateUtil.convertDateToTimestamp(adminFee.getEffectiveDate()));
					billingRequest.getAmtbAcctAdminFeeReqs().add(adminRequest);
					adminRequest.setAmtbBillReq(billingRequest);
					adminRequest.setEvent(NonConfigurableConstants.BILLING_REQUEST_EVENT_DELETE);
				}
			}
		}
		// credit term
		if(creditTerms!=null){
			for(Date effectiveDate : creditTerms.keySet()){
				// skip if effective date is earlier than now.
				if(!DateUtil.isToday(effectiveDate) && effectiveDate.before(Calendar.getInstance().getTime())){
					continue;
				}
				boolean found = false;
				for(AmtbAcctCredTerm creditTerm : acct.getAmtbAcctCredTerms()){
					if(effectiveDate.equals(creditTerm.getEffectiveDate())){
						found = true;
						if(!creditTerm.getMstbCreditTermMaster().getCreditTermPlanNo().equals(creditTerms.get(effectiveDate))){
							// if not equals, create a new request
							AmtbAcctCredTermReq creditTermRequest = new AmtbAcctCredTermReq();
							creditTermRequest.setMstbCreditTermMaster((MstbCreditTermMaster)MasterSetup.getCreditTermManager().getMaster(creditTerms.get(effectiveDate)));
							creditTermRequest.setEffectiveDt(DateUtil.convertDateToTimestamp(effectiveDate));
							billingRequest.getAmtbAcctCredTermReqs().add(creditTermRequest);
							creditTermRequest.setAmtbBillReq(billingRequest);
							creditTermRequest.setEvent(NonConfigurableConstants.BILLING_REQUEST_EVENT_UPDATE);
						}
						break;
					}
				}
				if(!found){
					AmtbAcctCredTermReq creditTermRequest = new AmtbAcctCredTermReq();
					creditTermRequest.setMstbCreditTermMaster((MstbCreditTermMaster)MasterSetup.getCreditTermManager().getMaster(creditTerms.get(effectiveDate)));
					creditTermRequest.setEffectiveDt(DateUtil.convertDateToTimestamp(effectiveDate));
					billingRequest.getAmtbAcctCredTermReqs().add(creditTermRequest);
					creditTermRequest.setAmtbBillReq(billingRequest);
					creditTermRequest.setEvent(NonConfigurableConstants.BILLING_REQUEST_EVENT_CREATE);
				}
			}
			for(AmtbAcctCredTerm creditTerm : acct.getAmtbAcctCredTerms()){
				boolean found = false;
				for(Date effectiveDate : creditTerms.keySet()){
					if(effectiveDate.equals(creditTerm.getEffectiveDate())){
						found = true;
						break;
					}
				}
				if(!found){
					AmtbAcctCredTermReq creditTermRequest = new AmtbAcctCredTermReq();
					creditTermRequest.setMstbCreditTermMaster(creditTerm.getMstbCreditTermMaster());
					creditTermRequest.setEffectiveDt(DateUtil.convertDateToTimestamp(creditTerm.getEffectiveDate()));
					billingRequest.getAmtbAcctCredTermReqs().add(creditTermRequest);
					creditTermRequest.setAmtbBillReq(billingRequest);
					creditTermRequest.setEvent(NonConfigurableConstants.BILLING_REQUEST_EVENT_DELETE);
				}
			}
		}
		// early pymt
		if(earlyPymts!=null){
			for(Date effectiveDate : earlyPymts.keySet()){
				// skip if effective date is earlier than now.
				if(!DateUtil.isToday(effectiveDate) && effectiveDate.before(Calendar.getInstance().getTime())){
					continue;
				}
				boolean found = false;
				for(AmtbAcctEarlyPymt earlyPymt : acct.getAmtbAcctEarlyPymts()){
					if(effectiveDate.equals(earlyPymt.getEffectiveDate())){
						found = true;
						if(!(earlyPymt.getMstbEarlyPaymentMaster()==null && earlyPymts.get(effectiveDate)==null)){// if any one of them is not null
							if(earlyPymt.getMstbEarlyPaymentMaster()==null ^ earlyPymts.get(effectiveDate)==null){// if one is null but the other is not
								AmtbAcctEarlyPymtReq earlyRequest = new AmtbAcctEarlyPymtReq();
								earlyRequest.setMstbEarlyPaymentMaster((MstbEarlyPaymentMaster)MasterSetup.getEarlyPaymentManager().getMaster(earlyPymts.get(effectiveDate)));
								earlyRequest.setEffectiveDt(DateUtil.convertDateToTimestamp(effectiveDate));
								billingRequest.getAmtbAcctEarlyPymtReqs().add(earlyRequest);
								earlyRequest.setAmtbBillReq(billingRequest);
								earlyRequest.setEvent(NonConfigurableConstants.BILLING_REQUEST_EVENT_UPDATE);
							}else{// if both is not null
								if(!earlyPymt.getMstbEarlyPaymentMaster().getEarlyPaymentPlanNo().equals(earlyPymts.get(effectiveDate))){
									AmtbAcctEarlyPymtReq earlyRequest = new AmtbAcctEarlyPymtReq();
									earlyRequest.setMstbEarlyPaymentMaster((MstbEarlyPaymentMaster)MasterSetup.getEarlyPaymentManager().getMaster(earlyPymts.get(effectiveDate)));
									earlyRequest.setEffectiveDt(DateUtil.convertDateToTimestamp(effectiveDate));
									billingRequest.getAmtbAcctEarlyPymtReqs().add(earlyRequest);
									earlyRequest.setAmtbBillReq(billingRequest);
									earlyRequest.setEvent(NonConfigurableConstants.BILLING_REQUEST_EVENT_UPDATE);
								}
							}
						}
						break;
					}
				}
				if(!found){
					AmtbAcctEarlyPymtReq earlyRequest = new AmtbAcctEarlyPymtReq();
					earlyRequest.setMstbEarlyPaymentMaster((MstbEarlyPaymentMaster)MasterSetup.getEarlyPaymentManager().getMaster(earlyPymts.get(effectiveDate)));
					earlyRequest.setEffectiveDt(DateUtil.convertDateToTimestamp(effectiveDate));
					billingRequest.getAmtbAcctEarlyPymtReqs().add(earlyRequest);
					earlyRequest.setAmtbBillReq(billingRequest);
					earlyRequest.setEvent(NonConfigurableConstants.BILLING_REQUEST_EVENT_CREATE);
				}
			}
			for(AmtbAcctEarlyPymt earlyPymt : acct.getAmtbAcctEarlyPymts()){
				boolean found = false;
				for(Date effectiveDate : earlyPymts.keySet()){
					if(effectiveDate.equals(earlyPymt.getEffectiveDate())){
						found = true;
						break;
					}
				}
				if(!found){
					AmtbAcctEarlyPymtReq earlyRequest = new AmtbAcctEarlyPymtReq();
					earlyRequest.setMstbEarlyPaymentMaster(earlyPymt.getMstbEarlyPaymentMaster());
					earlyRequest.setEffectiveDt(DateUtil.convertDateToTimestamp(earlyPymt.getEffectiveDate()));
					billingRequest.getAmtbAcctEarlyPymtReqs().add(earlyRequest);
					earlyRequest.setAmtbBillReq(billingRequest);
					earlyRequest.setEvent(NonConfigurableConstants.BILLING_REQUEST_EVENT_DELETE);
				}
			}
		}
		// late pymt
		if(latePymts!=null){
			for(Date effectiveDate : latePymts.keySet()){
				// skip if effective date is earlier than now.
				if(!DateUtil.isToday(effectiveDate) && effectiveDate.before(Calendar.getInstance().getTime())){
					continue;
				}
				boolean found = false;
				for(AmtbAcctLatePymt latePymt : acct.getAmtbAcctLatePymts()){
					if(effectiveDate.equals(latePymt.getEffectiveDate())){
						found = true;
						if(!latePymt.getMstbLatePaymentMaster().getLatePaymentPlanNo().equals(latePymts.get(effectiveDate))){
							AmtbAcctLatePymtReq lateRequest = new AmtbAcctLatePymtReq();
							lateRequest.setMstbLatePaymentMaster((MstbLatePaymentMaster)MasterSetup.getLatePaymentManager().getMaster(latePymts.get(effectiveDate)));
							lateRequest.setEffectiveDt(DateUtil.convertDateToTimestamp(effectiveDate));
							billingRequest.getAmtbAcctLatePymtReqs().add(lateRequest);
							lateRequest.setAmtbBillReq(billingRequest);
							lateRequest.setEvent(NonConfigurableConstants.BILLING_REQUEST_EVENT_UPDATE);
						}
						break;
					}
				}
				if(!found){
					AmtbAcctLatePymtReq lateRequest = new AmtbAcctLatePymtReq();
					lateRequest.setMstbLatePaymentMaster((MstbLatePaymentMaster)MasterSetup.getLatePaymentManager().getMaster(latePymts.get(effectiveDate)));
					lateRequest.setEffectiveDt(DateUtil.convertDateToTimestamp(effectiveDate));
					billingRequest.getAmtbAcctLatePymtReqs().add(lateRequest);
					lateRequest.setAmtbBillReq(billingRequest);
					lateRequest.setEvent(NonConfigurableConstants.BILLING_REQUEST_EVENT_CREATE);
				}
			}
			for(AmtbAcctLatePymt latePymt : acct.getAmtbAcctLatePymts()){
				boolean found = false;
				for(Date effectiveDate : latePymts.keySet()){
					if(effectiveDate.equals(latePymt.getEffectiveDate())){
						found = true;
						break;
					}
				}
				if(!found){
					AmtbAcctLatePymtReq lateRequest = new AmtbAcctLatePymtReq();
					lateRequest.setMstbLatePaymentMaster(latePymt.getMstbLatePaymentMaster());
					lateRequest.setEffectiveDt(DateUtil.convertDateToTimestamp(latePymt.getEffectiveDate()));
					billingRequest.getAmtbAcctLatePymtReqs().add(lateRequest);
					lateRequest.setAmtbBillReq(billingRequest);
					lateRequest.setEvent(NonConfigurableConstants.BILLING_REQUEST_EVENT_DELETE);
				}
			}
		}
		// promotions
		if(promotions!=null){
			List<Integer> foundNos = new ArrayList<Integer>();
			for(Map<String, Object> promotion : promotions){
				Date effectiveDateFrom = (Date)promotion.get("effectiveDateFrom");
				Date effectiveDateTo = (Date)promotion.get("effectiveDateTo");
				Integer promoNo = (Integer)promotion.get("promotion");
				Integer acctPromotionNo = (Integer)promotion.get("acctPromotionNo");
				// skip if both effective date is earlier than now.
				if(!DateUtil.isToday(effectiveDateFrom) && effectiveDateFrom.before(Calendar.getInstance().getTime())
						&& effectiveDateTo!=null && !DateUtil.isToday(effectiveDateTo) && effectiveDateTo.before(Calendar.getInstance().getTime())){
					continue;
				}
				boolean found = false;
				for(AmtbAcctPromotion promo : acct.getAmtbAcctPromotions()){
					if(promo.getAcctPromotionNo().equals(acctPromotionNo)){
						found = true;
						foundNos.add(promo.getAcctPromotionNo());
						if(
								!promotion.get("effectiveDateFrom").equals(promo.getEffectiveDateFrom()) ||
								!(promotion.get("effectiveDateTo")==null ? promo.getEffectiveDateTo()==null : promotion.get("effectiveDateTo").equals(promo.getEffectiveDateTo())) ||
								!promotion.get("promotion").equals(promo.getMstbPromotion().getPromoNo())){
							AmtbAcctPromotionReq promoRequest = new AmtbAcctPromotionReq();
							promoRequest.setMstbPromotion((MstbPromotion)MasterSetup.getPromotionManager().getMaster(promoNo));
							promoRequest.setEffectiveDtFrom(DateUtil.convertDateToTimestamp(effectiveDateFrom));
							promoRequest.setEffectiveDtTo(DateUtil.convertDateToTimestamp(effectiveDateTo));
							billingRequest.getAmtbAcctPromotionReqs().add(promoRequest);
							promoRequest.setAmtbBillReq(billingRequest);
							promoRequest.setEvent(NonConfigurableConstants.BILLING_REQUEST_EVENT_UPDATE);
							promoRequest.setAmtbAcctPromotion(promo);
						}
						break;
					}
				}
				if(!found){
					AmtbAcctPromotionReq promoRequest = new AmtbAcctPromotionReq();
					promoRequest.setMstbPromotion((MstbPromotion)MasterSetup.getPromotionManager().getMaster(promoNo));
					promoRequest.setEffectiveDtFrom(DateUtil.convertDateToTimestamp(effectiveDateFrom));
					promoRequest.setEffectiveDtTo(DateUtil.convertDateToTimestamp(effectiveDateTo));
					billingRequest.getAmtbAcctPromotionReqs().add(promoRequest);
					promoRequest.setAmtbBillReq(billingRequest);
					promoRequest.setEvent(NonConfigurableConstants.BILLING_REQUEST_EVENT_CREATE);
				}
			}
			for(AmtbAcctPromotion promotion : acct.getAmtbAcctPromotions()){
				if(!foundNos.contains(promotion.getAcctPromotionNo())){
					AmtbAcctPromotionReq promoRequest = new AmtbAcctPromotionReq();
					promoRequest.setMstbPromotion(promotion.getMstbPromotion());
					promoRequest.setEffectiveDtFrom(DateUtil.convertDateToTimestamp(promotion.getEffectiveDateFrom()));
					promoRequest.setEffectiveDtTo(DateUtil.convertDateToTimestamp(promotion.getEffectiveDateTo()));
					billingRequest.getAmtbAcctPromotionReqs().add(promoRequest);
					promoRequest.setAmtbBillReq(billingRequest);
					promoRequest.setEvent(NonConfigurableConstants.BILLING_REQUEST_EVENT_DELETE);
				}
			}
		}
		if(bankInfo!=null){
			if(bankInfo.get("bankNo")!=null){
				acct.setMstbBankMaster((MstbBankMaster)MasterSetup.getBankManager().getMaster((Integer)bankInfo.get("bankNo")));
			}else{
				if(acct.getMstbBankMaster()!=null){
					acct.setMstbBankMaster(null);
				}
			}
			if(bankInfo.get("branchNo")!=null){
				acct.setMstbBranchMaster((MstbBranchMaster)MasterSetup.getBankManager().getDetail((Integer)bankInfo.get("branchNo")));
			}else{
				if(acct.getMstbBranchMaster()!=null){
					acct.setMstbBranchMaster(null);
				}
			}
			if(bankInfo.get("bankAcctNo")!=null){
				acct.setBankAcctNo((String)bankInfo.get("bankAcctNo"));
			}else{
				if(acct.getBankAcctNo()!=null){
					acct.setBankAcctNo(null);
				}
			}
			if(bankInfo.get("paymentModeCode")!=null){
				acct.setMstbMasterTableByDefaultPaymentMode(ConfigurableConstants.getMasterTable(ConfigurableConstants.PAYMENT_MODE, (String)bankInfo.get("paymentModeCode")));
			}else{
				if(acct.getMstbMasterTableByDefaultPaymentMode()!=null){
					acct.setMstbMasterTableByDefaultPaymentMode(null);
				}
			}
		}
		SatbUser user = this.daoHelper.getUserDao().getUserWithRoles(CommonWindow.getUserId());
		if(billingRequest.getAmtbAcctBillCycleReqs().size()!=0 ||
				billingRequest.getAmtbAcctAdminFeeReqs().size()!=0 ||
				billingRequest.getAmtbAcctCredTermReqs().size()!=0 ||
				billingRequest.getAmtbAcctEarlyPymtReqs().size()!=0 ||
				billingRequest.getAmtbAcctLatePymtReqs().size()!=0 ||
				billingRequest.getAmtbAcctVolDiscReqs().size()!=0 ||
				billingRequest.getAmtbAcctPromotionReqs().size()!=0){
			AmtbBillReqFlow flow = new AmtbBillReqFlow();
			flow.setFlowDt(DateUtil.getCurrentTimestamp());
			flow.setSatbUser(user);
			flow.setFromStatus(NonConfigurableConstants.BILLING_REQUEST_STATUS_NEW);
			flow.setToStatus(NonConfigurableConstants.BILLING_REQUEST_STATUS_PENDING);
			billingRequest.getAmtbBillReqFlows().add(flow);
			flow.setAmtbBillReq(billingRequest);
			billingRequest.setAmtbAccount(acct);
			billingRequest.setRequestDt(DateUtil.getCurrentTimestamp());
			this.daoHelper.getAccountDao().save(billingRequest);
			// sending email
			List<String> toEmails = new ArrayList<String>();
			List<String> ccEmails = new ArrayList<String>();
			//ccEmails.add(user.getEmail());
			List<SatbUser> approvers = this.daoHelper.getUserDao().searchUser(Uri.APPROVE_BILLING);
			StringBuffer approverNames = new StringBuffer();
			for(SatbUser approver : approvers){
				toEmails.add(approver.getEmail());
				approverNames.append(approver.getName() + ",");
			}
			approverNames.delete(approverNames.length()-1, approverNames.length());
			EmailUtil.sendEmail(toEmails.toArray(new String[]{}), ccEmails.toArray(new String[]{}),
					ConfigurableConstants.getEmailText(ConfigurableConstants.EMAIL_TYPE_BILL_REQUEST_SUBMIT, ConfigurableConstants.EMAIL_SUBJECT),
					ConfigurableConstants.getEmailText(ConfigurableConstants.EMAIL_TYPE_BILL_REQUEST_SUBMIT, ConfigurableConstants.EMAIL_CONTENT)
					.replaceAll("#custNo#", acct.getCustNo())
					.replaceAll("#acctName#", acct.getAccountName())
					.replaceAll("#submiter#", user.getName())
					.replaceAll("#userName#", approverNames.toString()));

		}
		this.daoHelper.getAccountDao().update(acct, userId);
		return true;
	}
	public boolean hasPendingBillingChangeRequest(String custNo){
		return this.daoHelper.getAccountDao().hasPendingBillingChangeRequest(custNo);
	}
	public void terminateAcct(String custNo, String subAcctNo, Date terminateDate, String terminateCode, String remarks, String userId) throws Exception{
		AmtbAccount acct = getAccount(custNo, subAcctNo);
		ArrayList<IttbCniiAcctReq> cniiList = new ArrayList<IttbCniiAcctReq>();
		terminateAcct(acct, terminateDate, terminateCode, remarks, userId, cniiList);
		// Call CNII Synchronise interface
		Map<String, String> prop = (Map)SpringUtil.getBean("webserviceProperties");
		if (NonConfigurableConstants.BOOLEAN_NO.equals((String) prop.get("ws.cnii.disable")))
		{
			// Not disabled - so send interface
//			IBSCNIIUpdateAcctClient.send(cniiList);
			
			try
			{
				//29 Aug 2018 CNII Acct Sync
				String msg = updateCniiAcctSyncProcedure(cniiList);
				logger.info("CNII Acct Sync log : "+msg);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				throw new CniiInterfaceException("CNII Interface Acct Sync Error - "+e);
			}
		}else{
			logger.info("CNII interface is disabled");
		}
	}
	public void terminateAcct(String custNo, String parentCode, String code, Date terminateDate, String terminateCode, String remarks, String userId) throws Exception{
		logger.info("terminateAcct(custNo = "+custNo+", parentCode = "+parentCode+", code = "+code+", terminateDate = "+terminateDate+", terminateCode = "+terminateCode+", remarks = "+remarks+", userId = "+userId+")");
		AmtbAccount acct = getAccount(custNo, parentCode, code);
		ArrayList<IttbCniiAcctReq> cniiList = new ArrayList<IttbCniiAcctReq>();
		terminateAcct(acct, terminateDate, terminateCode, remarks, userId, cniiList);
		// Call CNII Synchronise interface
		Map<String, String> prop = (Map)SpringUtil.getBean("webserviceProperties");
		if (NonConfigurableConstants.BOOLEAN_NO.equals((String) prop.get("ws.cnii.disable")))
		{
			// Not disabled - so send interface
//			IBSCNIIUpdateAcctClient.send(cniiList);
			
			try
			{
				//29 Aug 2018 CNII Acct Sync
				String msg = updateCniiAcctSyncProcedure(cniiList);
				logger.info("CNII Acct Sync log : "+msg);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				throw new CniiInterfaceException("CNII Interface Acct Sync Error - "+e);
			}
		}else{
			logger.info("CNII interface is disabled");
		}
	}
	private void terminateAcct(AmtbAccount acct, Date terminateDate, String terminateCode, String remarks, String userId, ArrayList<IttbCniiAcctReq> cniiList) throws Exception{
		logger.info("terminateAcct(terminateDate = "+terminateDate+", terminateCode = "+terminateCode+", remarks = "+remarks+", userId = "+userId+")");
		boolean newStatus = false;
		AmtbAcctStatus terminateStatus = getFutureStatus(acct, NonConfigurableConstants.ACCOUNT_STATUS_TERMINATED);
		if(terminateStatus==null){
			newStatus = true;
			terminateStatus = new AmtbAcctStatus();
			terminateStatus.setAcctStatus(NonConfigurableConstants.ACCOUNT_STATUS_TERMINATED);
			terminateStatus.setAmtbAccount(acct);
		}else if(terminateStatus.getEffectiveDt().before(terminateDate)){// will not update terminate if account terminate is earlier
			return;
		}
		terminateStatus.setEffectiveDt(DateUtil.convertDateToTimestamp(terminateDate));
		terminateStatus.setMstbMasterTable(ConfigurableConstants.getMasterTable(ConfigurableConstants.ACCT_TERMINATE_REASON, terminateCode));
		terminateStatus.setSatbUser(this.daoHelper.getUserDao().getUserWithRoles(CommonWindow.getUserId()));
		terminateStatus.setStatusRemarks(remarks);
		if(newStatus){
			this.save(terminateStatus);
		}else{
			this.update(terminateStatus);
		}
		// now clearing all status after termination date
		List<AmtbAcctStatus> statuses = this.daoHelper.getAccountDao().getStatuses(acct.getAccountNo());
		List<AmtbAcctStatus> deleteStatuses = new ArrayList<AmtbAcctStatus>();
		for(AmtbAcctStatus status : statuses){
			if(status.getEffectiveDt().after(terminateDate)){
				deleteStatuses.add(status);
			}
		}
		statuses = null;
		for(AmtbAcctStatus deleteStatus : deleteStatuses){
			this.delete(deleteStatus);
			acct.getAmtbAcctStatuses().remove(deleteStatus);
		}
		List<String> cardLessProductList = new ArrayList<String>();
		List<PmtbProductType> cardlessProducts = this.businessHelper.getProductTypeBusiness().getCardlessProductType();
		for(PmtbProductType cardlessProduct : cardlessProducts) {
			cardLessProductList.add(cardlessProduct.getProductTypeId().trim());
		}
//		if(DateUtil.isToday(terminateDate)){
			// now updating cnii only if the terminate is today
			for(AmtbSubscTo subscription : acct.getAmtbSubscTos()){
				PmtbProductType productType = subscription.getComp_id().getPmtbProductType();
//				if(productType.getProductTypeId().equals(NonConfigurableConstants.PREMIER_SERVICE)){
				if(cardLessProductList.contains(productType.getProductTypeId())){
					IttbCniiAcctReq cniiRequest = new IttbCniiAcctReq();
					cniiRequest.setReqId(DateUtil.convertDateToStr(DateUtil.getCurrentDate(), DateUtil.GLOBAL_CNII_SEQ_FORMAT) + this.daoHelper.getAccountDao().getNextSequenceNo(Sequence.CNII_REQ_NO_SEQUENCE));
					cniiRequest.setReqDate(DateUtil.getCurrentTimestamp());
					cniiRequest.setEventType(NonConfigurableConstants.CNII_REQUEST_EVENT_TYPE_ACTIVATE);
					cniiRequest.setReqStatus(NonConfigurableConstants.CNII_REQUEST_STATUS_PENDING);
					cniiRequest.setTerminateDt(DateUtil.convertUtilDateToSqlDate(terminateDate));
					cniiRequest.setValidFlg(NonConfigurableConstants.BOOLEAN_YES);
					cniiRequest.setUpdatedBy(NonConfigurableConstants.CNII_REQUEST_UPDATED_BY_IBS);
					StringBuffer acctId = new StringBuffer();
					StringBuffer parentId = new StringBuffer();
					if(acct.getCustNo()!=null && acct.getCustNo().length()!=0){
						acctId.append(acct.getCustNo());
						// Should not have parent since acct is the top-most account
						//parentId.append(acct.getCustNo());
					}else{
						acctId.append(acct.getCode());
						// Not required to append as parent is always without the last code
						// e.g. if dept = 304DIV1DEP1, parent will be 304DIV1
						//parentId.append(acct.getCode());
						// now getting the parent
						AmtbAccount parent = acct.getAmtbAccount();
						if(parent!=null){// this shouldn't happen
							while(parent.getCustNo()==null){
								acctId.insert(0, parent.getCode());
								parentId.insert(0, parent.getCode());
								parent = this.daoHelper.getAccountDao().getParent(parent).getAmtbAccount();
							}
							acctId.insert(0, parent.getCustNo());
							parentId.insert(0, parent.getCustNo());
						}
					}
					cniiRequest.setAccountId(acctId.toString());
					if(acct.getCustNo()!=null && acct.getCustNo().length()!=0){
						cniiRequest.setAccountCd(acct.getCustNo());
					}else{
						cniiRequest.setAccountCd(acct.getCode());
					}
					cniiRequest.setAccountNm(acct.getAccountName());
					cniiRequest.setParentId(parentId.toString());
					cniiRequest.setUpdatedDt(DateUtil.getCurrentTimestamp());
					//this.save(cniiRequest);
					cniiList.add(cniiRequest);
					break;
				}
			}
//		}
		// terminating all products
		terminateProducts(acct, terminateDate, terminateCode, remarks, userId);
		// now terminating all children
		List<AmtbAccount> children = this.daoHelper.getAccountDao().getChildrenAccountsWithStatuses(acct.getAccountNo());
		for(AmtbAccount child : children){
			terminateAcct(child, terminateDate, terminateCode, remarks, userId, cniiList);
		}
	}
	private void terminateProducts(AmtbAccount acct, Date terminateDate, String terminateCode, String remarks, String userId) throws Exception{
		List<PmtbProduct> products = this.daoHelper.getProductDao().getProductsWithStatus(acct);
		List<PmtbProductStatus> updateStatuses = new ArrayList<PmtbProductStatus>();
		List<PmtbProductStatus> saveStatuses = new ArrayList<PmtbProductStatus>();
		List<Map<String, String>> ASRequests = new ArrayList<Map<String, String>>();
		MstbMasterTable masterCode = ConfigurableConstants.getMasterTable(ConfigurableConstants.ACCT_TERMINATE_REASON, terminateCode);
		for(PmtbProduct product : products){
			PmtbProductStatus currentStatus = getStatus(product.getPmtbProductStatuses(), terminateDate, false);
			if(!currentStatus.getStatusTo().equals(NonConfigurableConstants.PRODUCT_STATUS_ACTIVE)
					&& !currentStatus.getStatusTo().equals(NonConfigurableConstants.PRODUCT_STATUS_SUSPENDED)
					&& !currentStatus.getStatusTo().equals(NonConfigurableConstants.PRODUCT_STATUS_PARENT_SUSPENDED)){
				continue;
			}
			PmtbProductStatus terminateStatus = getFutureStatus(product, NonConfigurableConstants.PRODUCT_STATUS_TERMINATED);
			boolean newStatus = terminateStatus==null;
			if(newStatus){
				newStatus = true;
				terminateStatus = new PmtbProductStatus();
				terminateStatus.setStatusTo(NonConfigurableConstants.PRODUCT_STATUS_TERMINATED);
				terminateStatus.setPmtbProduct(product);
			}else if(terminateStatus.getStatusDt().before(terminateDate)){// will not update terminate if account terminate is earlier
				continue;
			}
			terminateStatus.setStatusFrom(currentStatus.getStatusTo());
			terminateStatus.setStatusDt(DateUtil.convertDateToTimestamp(terminateDate));
			terminateStatus.setMstbMasterTable(ConfigurableConstants.getMasterTable(ConfigurableConstants.ACCT_TERMINATE_REASON, terminateCode));
			terminateStatus.setStatusRemarks(remarks);
			if(newStatus){
				saveStatuses.add(terminateStatus);
				//this.save(terminateStatus, userId);
			}else{
				updateStatuses.add(terminateStatus);
				//this.update(terminateStatus, userId);
			}
			if(DateUtil.isToday(terminateDate)){
				product.setCurrentStatus(NonConfigurableConstants.PRODUCT_STATUS_TERMINATED);
			}
			this.update(product);
			this.daoHelper.getGenericDao().saveAll(saveStatuses, userId);
			this.daoHelper.getGenericDao().updateAll(updateStatuses, userId);
			// now updating AS
			if(DateUtil.isToday(terminateDate)){
				String creditLimit;
				if (product.getCreditBalance()!= null){
				//if(product.getCreditLimit()!=null){
					//creditLimit = product.getCreditLimit().toString();
					creditLimit = product.getCreditBalance().toString();
				}else{
					creditLimit = null;
				}
				String expiryDate;
				if(product.getExpiryDate()!=null){
					expiryDate = DateUtil.convertDateToStr(product.getExpiryDate(), DateUtil.INTERFACE_AS_PRODUCT_EXPIRY_DATE_FORMAT);
				}else{
					expiryDate = null;
				}
				Map<String, String> ASRequest = new HashMap<String, String>();
				ASRequest.put(API.PRODUCT_CARD_NO, product.getCardNo());
				ASRequest.put(API.PRODUCT_STATUS, NonConfigurableConstants.AS_REQUEST_PRODUCT_STATUS_INACTIVE);
				ASRequest.put(API.PRODUCT_NEW_CREDIT_BALANCE, creditLimit);
				ASRequest.put(API.PRODUCT_EXPIRY_DATE, expiryDate);
				ASRequest.put(API.PRODUCT_ACCOUNT_ID, API.formulateAccountId(acct));
				if(product.getPmtbProductType().getOneTimeUsage().equals(NonConfigurableConstants.BOOLEAN_YES)){
					ASRequest.put(API.PRODUCT_REASON_CODE, "C");
				}else{
					if(masterCode!=null){
						ASRequest.put(API.PRODUCT_REASON_CODE, masterCode.getInterfaceMappingValue());
					}else{
						ASRequest.put(API.PRODUCT_REASON_CODE, "");
					}
				}
				ASRequest.put(API.PRODUCT_UPDATED_BY, userId);
				ASRequest.put(API.PRODUCT_OFFLINE_COUNT, StringUtil.numberToString(product.getOfflineCount()));
				ASRequest.put(API.PRODUCT_OFFLINE_AMOUNT, StringUtil.bigDecimalToString(product.getOfflineAmount()));
				ASRequest.put(API.PRODUCT_OFFLINE_TXN_AMOUNT, StringUtil.bigDecimalToString(product.getOfflineTxnAmount()));
				ASRequest.put(API.PRODUCT_FORCE_ONLINE, API.formulateForceOnline(product, product.getPmtbProductType()));
				ASRequest.put(API.PRODUCT_TRANSFER_MODE, NonConfigurableConstants.AS_REQUEST_TRANSFER_MODE_ASYNCHRONOUS);
				ASRequests.add(ASRequest);
			}
		}
		API.updateProducts(ASRequests);
	}
	public void closeAcct(String custNo, String subAcctNo, Date closeDate, String closeCode, String remarks, String userId) throws Exception{
		AmtbAccount acct = getAccount(custNo, subAcctNo);
		ArrayList<IttbCniiAcctReq> cniiList = new ArrayList<IttbCniiAcctReq>();
		closeAcct(acct, closeDate, closeCode, remarks, userId, cniiList);
		Map<String, String> prop = (Map)SpringUtil.getBean("webserviceProperties");
		if (NonConfigurableConstants.BOOLEAN_NO.equals((String) prop.get("ws.cnii.disable"))){
			// Not disabled - so send interface
//			IBSCNIIUpdateAcctClient.send(cniiList);
			
			try
			{
				//29 Aug 2018 CNII Acct Sync
				String msg = updateCniiAcctSyncProcedure(cniiList);
				logger.info("CNII Acct Sync log : "+msg);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				throw new CniiInterfaceException("CNII Interface Acct Sync Error - "+e);
			}
		}else{
			logger.info("CNII interface is disabled");
		}
	}
	public void closeAcct(String custNo, String parentCode, String code, Date closeDate, String closeCode, String remarks, String userId) throws Exception{
		AmtbAccount acct = getAccount(custNo, parentCode, code);
		ArrayList<IttbCniiAcctReq> cniiList = new ArrayList<IttbCniiAcctReq>();
		closeAcct(acct, closeDate, closeCode, remarks, userId, cniiList);
		Map<String, String> prop = (Map)SpringUtil.getBean("webserviceProperties");
		if (NonConfigurableConstants.BOOLEAN_NO.equals((String) prop.get("ws.cnii.disable"))){
			// Not disabled - so send interface
//			IBSCNIIUpdateAcctClient.send(cniiList);
			
			//29 Aug 2018 CNII Acct Sync
			String msg = updateCniiAcctSyncProcedure(cniiList);
			logger.info("CNII Acct Sync log : "+msg);
		}else{
			logger.info("CNII interface is disabled");
		}
	}
	private void closeAcct(AmtbAccount acct, Date closeDate, String closeCode, String remarks, String userId, ArrayList<IttbCniiAcctReq> cniiList) throws Exception{
		boolean newStatus = false;
		AmtbAcctStatus closeStatus = getFutureStatus(acct, NonConfigurableConstants.ACCOUNT_STATUS_CLOSED);
		if(closeStatus==null){
			newStatus = true;
			closeStatus = new AmtbAcctStatus();
			closeStatus.setAcctStatus(NonConfigurableConstants.ACCOUNT_STATUS_CLOSED);
			closeStatus.setAmtbAccount(acct);
		}
		closeStatus.setEffectiveDt(DateUtil.convertDateToTimestamp(closeDate));
		closeStatus.setMstbMasterTable(ConfigurableConstants.getMasterTable(ConfigurableConstants.ACCT_TERMINATE_REASON, closeCode));
		closeStatus.setSatbUser(this.daoHelper.getUserDao().getUserWithRoles(CommonWindow.getUserId()));
		closeStatus.setStatusRemarks(remarks);
		if(newStatus){
			this.save(closeStatus);
		}else{
			this.update(closeStatus);
		}
		// now clearing all status after closed date
		List<AmtbAcctStatus> statuses = this.daoHelper.getAccountDao().getStatuses(acct.getAccountNo());
		List<AmtbAcctStatus> deleteStatuses = new ArrayList<AmtbAcctStatus>();
		for(AmtbAcctStatus status : statuses){
			if(status.getEffectiveDt().after(closeDate)){
				deleteStatuses.add(status);
			}
		}
		statuses = null;
		for(AmtbAcctStatus deleteStatus : deleteStatuses){
			this.delete(deleteStatus);
			acct.getAmtbAcctStatuses().remove(deleteStatus);
		}
		// terminating all products
		terminateProducts(acct, closeDate, closeCode, remarks, userId);
		List<String> cardLessProductList = new ArrayList<String>();
		List<PmtbProductType> cardlessProducts = this.businessHelper.getProductTypeBusiness().getCardlessProductType();
		for(PmtbProductType cardlessProduct : cardlessProducts) {
			cardLessProductList.add(cardlessProduct.getProductTypeId().trim());
		}
//		if(DateUtil.isToday(closeDate)){
			// now updating cnii only if the terminate is today
			for(AmtbSubscTo subscription : acct.getAmtbSubscTos()){
				PmtbProductType productType = subscription.getComp_id().getPmtbProductType();
//				if(productType.getProductTypeId().equals(NonConfigurableConstants.PREMIER_SERVICE)){
				if(cardLessProductList.contains(productType.getProductTypeId())){
					IttbCniiAcctReq cniiRequest = new IttbCniiAcctReq();
					cniiRequest.setReqId(DateUtil.convertDateToStr(DateUtil.getCurrentDate(), DateUtil.GLOBAL_CNII_SEQ_FORMAT) + this.daoHelper.getAccountDao().getNextSequenceNo(Sequence.CNII_REQ_NO_SEQUENCE));
					cniiRequest.setReqDate(DateUtil.getCurrentTimestamp());
					cniiRequest.setEventType(NonConfigurableConstants.CNII_REQUEST_EVENT_TYPE_ACTIVATE);
					cniiRequest.setReqStatus(NonConfigurableConstants.CNII_REQUEST_STATUS_PENDING);
					cniiRequest.setTerminateDt(DateUtil.convertUtilDateToSqlDate(closeDate));
					cniiRequest.setValidFlg(NonConfigurableConstants.BOOLEAN_YES);
					cniiRequest.setUpdatedBy(NonConfigurableConstants.CNII_REQUEST_UPDATED_BY_IBS);
					StringBuffer acctId = new StringBuffer();
					StringBuffer parentId = new StringBuffer();
					if(acct.getCustNo()!=null && acct.getCustNo().length()!=0){
						acctId.append(acct.getCustNo());
						// Should not have parent since acct is the top-most account
						//parentId.append(acct.getCustNo());
					}else{
						acctId.append(acct.getCode());
						// Not required to append as parent is always without the last code
						// e.g. if dept = 304DIV1DEP1, parent will be 304DIV1
						//parentId.append(acct.getCode());
						// now getting the parent
						AmtbAccount parent = acct.getAmtbAccount();
						if(parent!=null){// this shouldn't happen
							while(parent.getCustNo()==null){
								acctId.insert(0, parent.getCode());
								parentId.insert(0, parent.getCode());
								parent = this.daoHelper.getAccountDao().getParent(parent).getAmtbAccount();
							}
							acctId.insert(0, parent.getCustNo());
							parentId.insert(0, parent.getCustNo());
						}
					}
					cniiRequest.setAccountId(acctId.toString());
					if(acct.getCustNo()!=null && acct.getCustNo().length()!=0){
						cniiRequest.setAccountCd(acct.getCustNo());
					}else{
						cniiRequest.setAccountCd(acct.getCode());
					}
					cniiRequest.setAccountNm(acct.getAccountName());
					cniiRequest.setParentId(parentId.toString());
					cniiRequest.setUpdatedDt(DateUtil.getCurrentTimestamp());
					//this.save(cniiRequest);
					cniiList.add(cniiRequest);
					break;
				}
			}
//		}
		// now terminating all children
		List<AmtbAccount> children = this.daoHelper.getAccountDao().getChildrenAccountsWithStatuses(acct.getAccountNo());
		for(AmtbAccount child : children){
			closeAcct(child, closeDate, closeCode, remarks, userId, cniiList);
		}
	}
	public void reactivateAcct(String custNo, String subAcctNo, Date reactivateDate, String reactivateCode, String remarks, String userId) throws Exception{
		AmtbAccount acct = getAccount(custNo, subAcctNo);
		ArrayList<IttbCniiAcctReq> cniiList = new ArrayList<IttbCniiAcctReq>();
		reactivateAcct(acct, reactivateDate, reactivateCode, remarks, userId, cniiList);
		// Call CNII Synchronise interface
		Map<String, String> prop = (Map)SpringUtil.getBean("webserviceProperties");
		if (NonConfigurableConstants.BOOLEAN_NO.equals((String) prop.get("ws.cnii.disable")))
		{
			// Not disabled - so send interface
//			IBSCNIIUpdateAcctClient.send(cniiList);
			try
			{
				//29 Aug 2018 CNII Acct Sync
				String msg = updateCniiAcctSyncProcedure(cniiList);
				logger.info("CNII Acct Sync log : "+msg);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				throw new CniiInterfaceException("CNII Interface Acct Sync Error - "+e);
			}
		}else{
			logger.info("CNII interface is disabled");
		}
	}
	public void reactivateAcct(String custNo, String parentCode, String code, Date reactivateDate, String reactivateCode, String remarks, String userId) throws Exception{
		AmtbAccount acct = getAccount(custNo, parentCode, code);
		ArrayList<IttbCniiAcctReq> cniiList = new ArrayList<IttbCniiAcctReq>();
		reactivateAcct(acct, reactivateDate, reactivateCode, remarks, userId, cniiList);
		// Call CNII Synchronise interface
		Map<String, String> prop = (Map)SpringUtil.getBean("webserviceProperties");
		if (NonConfigurableConstants.BOOLEAN_NO.equals((String) prop.get("ws.cnii.disable")))
		{
			// Not disabled - so send interface
//			IBSCNIIUpdateAcctClient.send(cniiList);
			
			//29 Aug 2018 CNII Acct Sync
			String msg = updateCniiAcctSyncProcedure(cniiList);
			logger.info("CNII Acct Sync log : "+msg);
		}else{
			logger.info("CNII interface is disabled");
		}
	}
	private void reactivateAcct(AmtbAccount acct, Date reactivateDate, String reactivateCode, String remarks, String userId, ArrayList<IttbCniiAcctReq> list) throws Exception{
		boolean newStatus = false;
		List<AmtbAcctStatus> saveOrUpdateAll = new ArrayList<AmtbAcctStatus>();
		List<AmtbAcctStatus> deleteAll = new ArrayList<AmtbAcctStatus>();
		AmtbAcctStatus reactivateStatus = null;
		// getting next status
		for(AmtbAcctStatus status : acct.getAmtbAcctStatuses()){
			if(status.getEffectiveDt().after(DateUtil.getCurrentTimestamp())){
				if(reactivateStatus==null || reactivateStatus.getEffectiveDt().after(status.getEffectiveDt())){
					reactivateStatus = status;
				}
			}
		}
		if(reactivateStatus==null){
			newStatus = true;
			reactivateStatus = new AmtbAcctStatus();
			AmtbAccount parentAcct = acct.getAmtbAccount();
			if(parentAcct!=null){
				List<AmtbAcctStatus> statuses = this.daoHelper.getAccountDao().getStatuses(parentAcct.getAccountNo());
				AmtbAcctStatus currentStatus = getStatus(statuses, new Date(DateUtil.getCurrentDate().getTime()+1));
				if(currentStatus.getAcctStatus().equals(NonConfigurableConstants.ACCOUNT_STATUS_PARENT_SUSPENDED) || currentStatus.getAcctStatus().equals(NonConfigurableConstants.ACCOUNT_STATUS_SUSPENDED)){
					reactivateStatus.setAcctStatus(NonConfigurableConstants.ACCOUNT_STATUS_PARENT_SUSPENDED);
				}else{
					reactivateStatus.setAcctStatus(NonConfigurableConstants.ACCOUNT_STATUS_ACTIVE);
				}
			}else{
				reactivateStatus.setAcctStatus(NonConfigurableConstants.ACCOUNT_STATUS_ACTIVE);
			}
			reactivateStatus.setAmtbAccount(acct);
		}
		reactivateStatus.setEffectiveDt(DateUtil.convertDateToTimestamp(reactivateDate));
		reactivateStatus.setMstbMasterTable(ConfigurableConstants.getMasterTable(ConfigurableConstants.ACCT_REACTIVATE_REASON, reactivateCode));
		reactivateStatus.setSatbUser(this.daoHelper.getUserDao().getUserWithRoles(CommonWindow.getUserId()));
		reactivateStatus.setStatusRemarks(remarks);
		if(newStatus){
			saveOrUpdateAll.add(reactivateStatus);
			//this.save(reactivateStatus);
		}else{
			saveOrUpdateAll.add(reactivateStatus);
			//this.update(reactivateStatus);
		}
		acct.getAmtbAcctStatuses().add(reactivateStatus);
		// now deleting everything in between the suspension
		List<AmtbAcctStatus> deleted = new ArrayList<AmtbAcctStatus>();
		AmtbAcctStatus currentAcctSuspend = getCurrentStatus(acct.getAmtbAcctStatuses());
		if(currentAcctSuspend!=null){
			for(AmtbAcctStatus status : acct.getAmtbAcctStatuses()){
				if(status.equals(currentAcctSuspend)){
					continue;
				}
				if(status.equals(reactivateStatus)){
					continue;
				}
				if(status.getEffectiveDt().before(currentAcctSuspend.getEffectiveDt())){
					continue;
				}
				if(reactivateStatus!=null && status.getEffectiveDt().after(reactivateStatus.getEffectiveDt())){
					continue;
				}
				deleted.add(status);
				//this.delete(status);
			}
		}
		deleteAll.addAll(deleted);
		acct.getAmtbAcctStatuses().removeAll(deleted);
		// now checking for terminated status
		AmtbAcctStatus terminatedStatus = getFutureStatus(acct, NonConfigurableConstants.ACCOUNT_STATUS_TERMINATED);
		if(terminatedStatus!=null){// if there is termination. Clear everything except closed
			deleted.clear();
			for(AmtbAcctStatus status : acct.getAmtbAcctStatuses()){
				if(status.getEffectiveDt().after(terminatedStatus.getEffectiveDt()) && !status.getAcctStatus().equals(NonConfigurableConstants.ACCOUNT_STATUS_CLOSED)){
					deleted.add(status);
					//this.delete(status);
				}
			}
			deleteAll.addAll(deleted);
			acct.getAmtbAcctStatuses().removeAll(deleted);
		}
		this.daoHelper.getGenericDao().saveOrUpdateAll(saveOrUpdateAll);
		this.daoHelper.getGenericDao().deleteAll(deleteAll);
		this.update(acct, userId);
		List<String> cardLessProductList = new ArrayList<String>();
		List<PmtbProductType> cardlessProducts = this.businessHelper.getProductTypeBusiness().getCardlessProductType();
		for(PmtbProductType cardlessProduct : cardlessProducts) {
			cardLessProductList.add(cardlessProduct.getProductTypeId().trim());
		}
//		if(DateUtil.isToday(reactivateDate)){
			// now updating cnii only if the suspension is today
			for(AmtbSubscTo subscription : acct.getAmtbSubscTos()){
				PmtbProductType productType = subscription.getComp_id().getPmtbProductType();
//				if(productType.getProductTypeId().equals(NonConfigurableConstants.PREMIER_SERVICE)){
				if(cardLessProductList.contains(productType.getProductTypeId())){
					IttbCniiAcctReq cniiRequest = new IttbCniiAcctReq();
					cniiRequest.setReqId(DateUtil.convertDateToStr(DateUtil.getCurrentDate(), DateUtil.GLOBAL_CNII_SEQ_FORMAT) + this.daoHelper.getAccountDao().getNextSequenceNo(Sequence.CNII_REQ_NO_SEQUENCE));
					cniiRequest.setReqDate(DateUtil.getCurrentTimestamp());
					cniiRequest.setEventType(NonConfigurableConstants.CNII_REQUEST_EVENT_TYPE_ACTIVATE);
					cniiRequest.setReqStatus(NonConfigurableConstants.CNII_REQUEST_STATUS_PENDING);
					cniiRequest.setSuspensionStartDt(DateUtil.convertUtilDateToSqlDate(currentAcctSuspend.getEffectiveDt()));
					if(reactivateDate!=null){
						cniiRequest.setSuspensionEndDt(DateUtil.convertUtilDateToSqlDate(reactivateDate));
					}
					cniiRequest.setValidFlg(NonConfigurableConstants.BOOLEAN_YES);
					cniiRequest.setUpdatedBy(NonConfigurableConstants.CNII_REQUEST_UPDATED_BY_IBS);
					StringBuffer acctId = new StringBuffer();
					StringBuffer parentId = new StringBuffer();
					if(acct.getCustNo()!=null && acct.getCustNo().length()!=0){
						acctId.append(acct.getCustNo());
						// Should not have parent since acct is the top-most account
						//parentId.append(acct.getCustNo());
					}else{
						acctId.append(acct.getCode());
						// Not required to append as parent is always without the last code
						// e.g. if dept = 304DIV1DEP1, parent will be 304DIV1
						//parentId.append(acct.getCode());
						// now getting the parent
						AmtbAccount parent = acct.getAmtbAccount();
						if(parent!=null){// this shouldn't happen
							while(parent.getCustNo()==null){
								acctId.insert(0, parent.getCode());
								parentId.insert(0, parent.getCode());
								parent = this.daoHelper.getAccountDao().getParent(parent).getAmtbAccount();
							}
							acctId.insert(0, parent.getCustNo());
							parentId.insert(0, parent.getCustNo());
						}
					}
					cniiRequest.setAccountId(acctId.toString());
					if(acct.getCustNo()!=null && acct.getCustNo().length()!=0){
						cniiRequest.setAccountCd(acct.getCustNo());
					}else{
						cniiRequest.setAccountCd(acct.getCode());
					}
					cniiRequest.setAccountNm(acct.getAccountName());
					cniiRequest.setParentId(parentId.toString());
					cniiRequest.setUpdatedDt(DateUtil.getCurrentTimestamp());
					list.add(cniiRequest);
					//this.save(cniiRequest);
					break;
				}
			}
//		}
		// reactivate products
		reactivateProducts(acct, reactivateDate, reactivateCode, remarks, userId);
		// now reactivate all children
		List<AmtbAccount> children = this.daoHelper.getAccountDao().getChildrenAccountsWithStatuses(acct.getAccountNo());
		for(AmtbAccount child : children){
			if(getCurrentStatus(child.getAmtbAcctStatuses()).getAcctStatus().equals(NonConfigurableConstants.ACCOUNT_STATUS_PARENT_SUSPENDED)){
				reactivateAcct(child, reactivateDate, reactivateCode, remarks, userId, list);
			}
		}
	}
	private void reactivateProducts(AmtbAccount acct, Date reactivateDate, String reactivateCode, String remarks, String userId) throws Exception{
		logger.info("reactivateProducts(acct = "+acct.getAccountNo()+", reactivateDate = "+reactivateDate+", reactivateCode = "+reactivateCode+", remarks = "+remarks+", userId = "+userId+")");
		// to reactivate products
		List<PmtbProduct> products = this.daoHelper.getProductDao().getProductsWithStatus(acct);
		// getting the list of suspension for parent
		//List<SuspendPeriod> parentSuspensions = getSuspensions(acct, reactivateDate);
		////
		List<SuspendPeriod> parentSuspensions = new ArrayList<SuspendPeriod>();
		if(acct!=null){
			AmtbAcctStatus start = null;
			TreeSet<AmtbAcctStatus> sortedStatus = new TreeSet<AmtbAcctStatus>(new Comparator<AmtbAcctStatus>(){
				public int compare(AmtbAcctStatus o1, AmtbAcctStatus o2) {
					return o1.getEffectiveDt().compareTo(o2.getEffectiveDt());
				}
			});
			sortedStatus.addAll(acct.getAmtbAcctStatuses());
			AmtbAcctStatus currentStatus = getStatus(sortedStatus, reactivateDate);
			if(currentStatus!=null && (currentStatus.getAcctStatus().equals(NonConfigurableConstants.ACCOUNT_STATUS_SUSPENDED) || currentStatus.getAcctStatus().equals(NonConfigurableConstants.ACCOUNT_STATUS_PARENT_SUSPENDED))){
				start=currentStatus;
			}
			for(AmtbAcctStatus status : sortedStatus){
				
				if(currentStatus.getEffectiveDt().before(status.getEffectiveDt())) {
					if(status.getAcctStatus().equals(NonConfigurableConstants.ACCOUNT_STATUS_SUSPENDED) || status.getAcctStatus().equals(NonConfigurableConstants.ACCOUNT_STATUS_PARENT_SUSPENDED)){
						if(start==null){
							start = status;
						}
					}else if(status.getAcctStatus().equals(NonConfigurableConstants.ACCOUNT_STATUS_ACTIVE)){
						if(start!=null){
							parentSuspensions.add(new SuspendPeriod(start, status));
							start = null;
						}
					}
				}
			}
			if(start!=null){
				parentSuspensions.add(new SuspendPeriod(start, null));
			}
			
			/*for(SuspendPeriod p: parentSuspensions) {
				logger.debug("Start: " + p.getSuspendStart().getEffectiveDt() + " End: " + p.getSuspendEnd().getEffectiveDt());
			}*/
		}
		////
		List<PmtbProduct> updateProducts = new ArrayList<PmtbProduct>();
		List<PmtbProductStatus> updateStatuses = new ArrayList<PmtbProductStatus>();
		List<PmtbProductStatus> saveStatuses = new ArrayList<PmtbProductStatus>();
		List<PmtbProductStatus> deleteStatuses = new ArrayList<PmtbProductStatus>();
		List<Map<String, String>> ASRequests = new ArrayList<Map<String, String>>();
		for(PmtbProduct product : products){
			PmtbProductStatus currentStatus = getCurrentStatus(product.getPmtbProductStatuses(), false);
			List<PmtbProductStatus> productNewStatuses = new ArrayList<PmtbProductStatus>();
			List<PmtbProductStatus> productDeleteStatuses = new ArrayList<PmtbProductStatus>();
			// skipping those that can't be suspended
			if(currentStatus==null ||
					currentStatus.getStatusTo().equals(NonConfigurableConstants.PRODUCT_STATUS_TERMINATED) ||
					currentStatus.getStatusTo().equals(NonConfigurableConstants.PRODUCT_STATUS_RECYCLED) ||
					currentStatus.getStatusTo().equals(NonConfigurableConstants.PRODUCT_STATUS_USED)){
				continue;
			}
			// looping thru each product status
			for(PmtbProductStatus status : product.getPmtbProductStatuses()){
				String statusMasterType = status.getMstbMasterTable().getMasterType();
				if(statusMasterType.equals(ConfigurableConstants.PRODUCT_SUSPEND_REASON) || statusMasterType.equals(ConfigurableConstants.PRODUCT_REACTIVATE_REASON)){
					boolean found = false;
					for(SuspendPeriod parentSuspension : parentSuspensions){
						if(
								// if status = start of parent suspension or
								status.getStatusDt().getTime() == parentSuspension.getSuspendStart().getEffectiveDt().getTime() ||
								// status is after suspension start and (suspension end is null or status is before suspension end) or
								status.getStatusDt().after(parentSuspension.getSuspendStart().getEffectiveDt()) && (parentSuspension.getSuspendEnd()==null || status.getStatusDt().before(parentSuspension.getSuspendEnd().getEffectiveDt())) ||
								// parent suspension end is not null and status = end of parent suspension
								(parentSuspension.getSuspendEnd()!=null && status.getStatusDt().getTime() == parentSuspension.getSuspendEnd().getEffectiveDt().getTime())
							){
							found = true;
							if(status.getStatusTo().equals(NonConfigurableConstants.PRODUCT_STATUS_ACTIVE)){
								status.setStatusTo(NonConfigurableConstants.PRODUCT_STATUS_PARENT_SUSPENDED);
								updateStatuses.add(status);
								break;
							}
						}
					}
					if(!found){
						if(status.getStatusTo().equals(NonConfigurableConstants.PRODUCT_STATUS_PARENT_SUSPENDED)){
							status.setStatusTo(NonConfigurableConstants.PRODUCT_STATUS_ACTIVE);
							updateStatuses.add(status);
						}
					}
				}
			}
			// looping thru each account status
			for(SuspendPeriod parentSuspension : parentSuspensions){
				boolean startFound = false;
				boolean endFound = parentSuspension.getSuspendEnd()==null;
				for(PmtbProductStatus status : product.getPmtbProductStatuses()){
					String statusMasterType = status.getMstbMasterTable().getMasterType();
					// if status = start of parent suspension or
					if(status.getStatusDt().getTime() == parentSuspension.getSuspendStart().getEffectiveDt().getTime()){
						// if it is a product status
						if(statusMasterType.equals(ConfigurableConstants.PRODUCT_SUSPEND_REASON) || statusMasterType.equals(ConfigurableConstants.PRODUCT_REACTIVATE_REASON)){
							if(status.getStatusTo().equals(NonConfigurableConstants.PRODUCT_STATUS_ACTIVE)){
								if(getStatus(product.getPmtbProductStatuses(), status.getStatusDt(), false).getStatusTo().equals(NonConfigurableConstants.PRODUCT_STATUS_SUSPENDED)){
									status.setStatusTo(NonConfigurableConstants.PRODUCT_STATUS_SUSPENDED);
								}else{
									status.setStatusTo(NonConfigurableConstants.PRODUCT_STATUS_PARENT_SUSPENDED);
								}
								updateStatuses.add(status);
							}
						}else if(statusMasterType.equals(ConfigurableConstants.ACCT_SUSPEND_REASON) || statusMasterType.equals(ConfigurableConstants.ACCT_REACTIVATE_REASON)){// if it is an account status
							if(status.getStatusTo().equals(NonConfigurableConstants.PRODUCT_STATUS_PARENT_SUSPENDED) && getStatus(product.getPmtbProductStatuses(), status.getStatusDt(), false).getStatusTo().equals(NonConfigurableConstants.PRODUCT_STATUS_SUSPENDED)){
								status.setStatusTo(NonConfigurableConstants.PRODUCT_STATUS_SUSPENDED);
								updateStatuses.add(status);
							}else if(status.getStatusTo().equals(NonConfigurableConstants.PRODUCT_STATUS_SUSPENDED) && !getStatus(product.getPmtbProductStatuses(), status.getStatusDt(), false).getStatusTo().equals(NonConfigurableConstants.PRODUCT_STATUS_SUSPENDED)){
								status.setStatusTo(NonConfigurableConstants.PRODUCT_STATUS_PARENT_SUSPENDED);
								updateStatuses.add(status);
							}
						}
						startFound = true;
					}
					// parent suspension end is not null and status = end of parent suspension
					if(parentSuspension.getSuspendEnd()!=null && status.getStatusDt().getTime() == parentSuspension.getSuspendEnd().getEffectiveDt().getTime()){
						// if it is a product status
						if(statusMasterType.equals(ConfigurableConstants.PRODUCT_SUSPEND_REASON) || statusMasterType.equals(ConfigurableConstants.PRODUCT_REACTIVATE_REASON)){
							if(status.getStatusTo().equals(NonConfigurableConstants.PRODUCT_STATUS_PARENT_SUSPENDED)){
								status.setStatusTo(NonConfigurableConstants.PRODUCT_STATUS_ACTIVE);
								updateStatuses.add(status);
							}
						}else if(statusMasterType.equals(ConfigurableConstants.ACCT_SUSPEND_REASON) || statusMasterType.equals(ConfigurableConstants.ACCT_REACTIVATE_REASON)){// if it is an account status
							if(status.getStatusTo().equals(NonConfigurableConstants.PRODUCT_STATUS_PARENT_SUSPENDED)){
								status.setStatusTo(NonConfigurableConstants.PRODUCT_STATUS_ACTIVE);
								updateStatuses.add(status);
							}
						}
						endFound = true;
					}
					if(startFound && endFound){
						break;
					}
				}
				if(!startFound){
					PmtbProductStatus prevStatus = getStatus(product.getPmtbProductStatuses(), parentSuspension.getSuspendStart().getEffectiveDt(), false);
					if(prevStatus!=null){
						PmtbProductStatus newStatus = new PmtbProductStatus();
						newStatus.setMstbMasterTable(parentSuspension.getSuspendStart().getMstbMasterTable());
						newStatus.setPmtbProduct(product);
		
						newStatus.setStatusDt(parentSuspension.getSuspendStart().getEffectiveDt());
						newStatus.setStatusFrom(prevStatus.getStatusTo());
						newStatus.setStatusRemarks(parentSuspension.getSuspendStart().getStatusRemarks());
						if(prevStatus.getStatusTo().equals(NonConfigurableConstants.PRODUCT_STATUS_SUSPENDED)){
							newStatus.setStatusTo(NonConfigurableConstants.PRODUCT_STATUS_SUSPENDED);
						}else{
							newStatus.setStatusTo(NonConfigurableConstants.PRODUCT_STATUS_PARENT_SUSPENDED);
						}
						saveStatuses.add(newStatus);
					}
				}
				if(!endFound){
					PmtbProductStatus prevStatus = getStatus(product.getPmtbProductStatuses(), parentSuspension.getSuspendEnd().getEffectiveDt(), false);
					if(prevStatus!=null){
						logger.info("parentSuspensionEnd= " + parentSuspension.getSuspendEnd().getAcctStatusNo());
						logger.info("parentSuspensionEnd.MstbMasterTable= " + parentSuspension.getSuspendEnd().getMstbMasterTable().getMasterNo());
						PmtbProductStatus newStatus = new PmtbProductStatus();
						newStatus.setMstbMasterTable(parentSuspension.getSuspendEnd().getMstbMasterTable());
						newStatus.setPmtbProduct(product);
						productNewStatuses.add(newStatus);
						newStatus.setStatusDt(parentSuspension.getSuspendEnd().getEffectiveDt());
						newStatus.setStatusFrom(prevStatus.getStatusTo());
						newStatus.setStatusRemarks(parentSuspension.getSuspendEnd().getStatusRemarks());
						if(prevStatus.getStatusTo().equals(NonConfigurableConstants.PRODUCT_STATUS_SUSPENDED)){
							newStatus.setStatusTo(NonConfigurableConstants.PRODUCT_STATUS_SUSPENDED);
						}else{
							newStatus.setStatusTo(NonConfigurableConstants.PRODUCT_STATUS_ACTIVE);
						}
						saveStatuses.add(newStatus);
					}
				}
			}
			product.getPmtbProductStatuses().addAll(productNewStatuses);
			// now clearing all account status in the product that is not found.
			for(PmtbProductStatus status : product.getPmtbProductStatuses()){
				logger.info("status=" + status.getProductStatusNo());
				logger.info("status.MstbMasterTable=" + status.getMstbMasterTable().getMasterNo());
				// skip status that is of product status
				if(
						status.getMstbMasterTable().getMasterType().equals(ConfigurableConstants.PRODUCT_SUSPEND_REASON) ||
						status.getMstbMasterTable().getMasterType().equals(ConfigurableConstants.PRODUCT_REACTIVATE_REASON) ||
						status.getMstbMasterTable().getMasterType().equals(ConfigurableConstants.ISSUE_REASON_TYPE)){
					continue;
				}
				if(status.getStatusDt().before(new Date())){
					continue;
				}
				logger.info("status master = " + status.getMstbMasterTable().getMasterType());
				boolean found = false;
				for(SuspendPeriod parentSuspension : parentSuspensions){
					if(status.getStatusDt().getTime() == parentSuspension.getSuspendStart().getEffectiveDt().getTime()){
						found = true;
						break;
					}else if(parentSuspension.getSuspendEnd()!=null && status.getStatusDt().getTime() == parentSuspension.getSuspendEnd().getEffectiveDt().getTime()){
						found = true;
						break;
					}
					logger.info("status = " + status.getStatusDt().getTime());
					logger.info("parent suspend start = " + parentSuspension.getSuspendStart().getEffectiveDt().getTime());
					logger.info("parent suspend end = " + parentSuspension.getSuspendEnd()!=null ? parentSuspension.getSuspendEnd().getEffectiveDt().getTime() : parentSuspension.getSuspendEnd());
				}
				if(found){
					continue;
				}
				logger.info("found = " + found);
				if(status.getStatusDt().getTime() == reactivateDate.getTime()){
					continue;
				}
				productDeleteStatuses.add(status);
				logger.info("delete = " + status);
				deleteStatuses.add(status);
			}
			product.getPmtbProductStatuses().removeAll(productDeleteStatuses);
			if(DateUtil.isToday(reactivateDate)){
				AmtbAcctStatus currentAcctStatus = getStatus(acct.getAmtbAcctStatuses(), new Date(reactivateDate.getTime()+1));
				if(currentAcctStatus.getAcctStatus().equals(NonConfigurableConstants.PRODUCT_STATUS_PARENT_SUSPENDED) || currentAcctStatus.getAcctStatus().equals(NonConfigurableConstants.PRODUCT_STATUS_SUSPENDED)){
					product.setCurrentStatus(NonConfigurableConstants.PRODUCT_STATUS_PARENT_SUSPENDED);
				}else{
					product.setCurrentStatus(NonConfigurableConstants.PRODUCT_STATUS_ACTIVE);
				}
				// clearing product status
				updateProducts.add(product);
			}
			// now realigning the status to and from status
			Set<PmtbProductStatus> sortedStatuses = new TreeSet<PmtbProductStatus>(new Comparator<PmtbProductStatus>(){
				public int compare(PmtbProductStatus o1, PmtbProductStatus o2) {
					return o1.getStatusDt().compareTo(o2.getStatusDt());
				}
			});
			sortedStatuses.addAll(product.getPmtbProductStatuses());
			PmtbProductStatus prevStatus = null;
			for(PmtbProductStatus status : sortedStatuses){
				// skipping the first one as it is always "New" to product type default status
				logger.info("from = " + status.getStatusFrom() + ", to = " + status.getStatusTo());
				if(prevStatus != null){
					// if previous status to not equals status from
					if(!prevStatus.getStatusTo().equals(status.getStatusFrom())){
						status.setStatusFrom(prevStatus.getStatusTo());
						// if it is not the new status
						if(!saveStatuses.contains(status) && !updateStatuses.contains(status) && !deleteStatuses.contains(status)){
							updateStatuses.add(status);
						}
					}
				}
				// now assigning previous status and go to next status
				prevStatus = status;
			}
			product.getPmtbProductStatuses().clear();
			// now updating AS
			if(DateUtil.isToday(reactivateDate)){
				String creditLimit;
				if(product.getCreditBalance()!=null){
					creditLimit = product.getCreditBalance().toString();
				}else{
					creditLimit = null;
				}
				String expiryDate;
				if(product.getExpiryDate()!=null){
					expiryDate = DateUtil.convertDateToStr(product.getExpiryDate(), DateUtil.INTERFACE_AS_PRODUCT_EXPIRY_DATE_FORMAT);
				}else{
					expiryDate = null;
				}
				Map<String, String> ASRequest = new HashMap<String, String>();
				ASRequest.put(API.PRODUCT_CARD_NO, product.getCardNo());
				ASRequest.put(API.PRODUCT_STATUS, NonConfigurableConstants.AS_REQUEST_PRODUCT_STATUS_ACTIVE);
				ASRequest.put(API.PRODUCT_NEW_CREDIT_BALANCE, creditLimit);
				ASRequest.put(API.PRODUCT_EXPIRY_DATE, expiryDate);
				ASRequest.put(API.PRODUCT_ACCOUNT_ID, API.formulateAccountId(acct));
				ASRequest.put(API.PRODUCT_REASON_CODE, "");
				ASRequest.put(API.PRODUCT_UPDATED_BY, userId);
				ASRequest.put(API.PRODUCT_TRANSFER_MODE, NonConfigurableConstants.AS_REQUEST_TRANSFER_MODE_ASYNCHRONOUS);
				ASRequest.put(API.PRODUCT_OFFLINE_COUNT, StringUtil.numberToString(product.getOfflineCount()));
				ASRequest.put(API.PRODUCT_OFFLINE_AMOUNT, StringUtil.bigDecimalToString(product.getOfflineAmount()));
				ASRequest.put(API.PRODUCT_OFFLINE_TXN_AMOUNT,StringUtil.bigDecimalToString(product.getOfflineTxnAmount()));
				ASRequest.put(API.PRODUCT_FORCE_ONLINE, API.formulateForceOnline(product, product.getPmtbProductType()));
				ASRequests.add(ASRequest);
			}
		}
		logger.info("update products size = " + updateProducts.size());
		logger.info("update statuses size = " + updateStatuses.size());
		logger.info("saved statuses size = " + saveStatuses.size());
		logger.info("delete statuses size = " + deleteStatuses.size());
		logger.info("deleted statuses = " + deleteStatuses);
		this.daoHelper.getProductDao().updateAll(updateProducts, userId);
		this.daoHelper.getProductDao().updateAll(updateStatuses, userId);
		this.daoHelper.getProductDao().saveAll(saveStatuses, userId);
		this.daoHelper.getProductDao().deleteAll(deleteStatuses);
		API.updateProducts(ASRequests);
	
//		List<PmtbProduct> products = this.daoHelper.getProductDao().getProductsWithStatus(acct);
//		List<PmtbProductStatus> updateStatuses = new ArrayList<PmtbProductStatus>();
//		List<PmtbProductStatus> saveStatuses = new ArrayList<PmtbProductStatus>();
//		List<PmtbProductStatus> deleteStatuses = new ArrayList<PmtbProductStatus>();
//		List<Map<String, String>> ASRequests = new ArrayList<Map<String, String>>();
//		for(PmtbProduct product : products){
//			PmtbProductStatus currentProductStatus = getCurrentStatus(product.getPmtbProductStatuses());
//			if(currentProductStatus==null ||
//					currentProductStatus.getStatusTo().equals(NonConfigurableConstants.PRODUCT_STATUS_TERMINATED) ||
//					currentProductStatus.getStatusTo().equals(NonConfigurableConstants.PRODUCT_STATUS_RECYCLED) ||
//					currentProductStatus.getStatusTo().equals(NonConfigurableConstants.PRODUCT_STATUS_USED)){
//				continue;
//			}
//			boolean newStatus = false;
//			PmtbProductStatus reactivateStatus = null;
//			// getting next status
//			for(PmtbProductStatus status : product.getPmtbProductStatuses()){
//				if(status.getStatusDt().after(DateUtil.getCurrentTimestamp())){
//					if(reactivateStatus==null || reactivateStatus.getStatusDt().after(status.getStatusDt())){
//						reactivateStatus = status;
//					}
//				}
//			}
//			if(reactivateStatus==null){
//				newStatus = true;
//				reactivateStatus = new PmtbProductStatus();
//				AmtbAcctStatus currentStatus = getStatus(acct.getAmtbAcctStatuses(), new Date(reactivateDate.getTime()+1));
//				reactivateStatus.setStatusFrom(NonConfigurableConstants.PRODUCT_STATUS_PARENT_SUSPENDED);
//				if(currentStatus.getAcctStatus().equals(NonConfigurableConstants.PRODUCT_STATUS_PARENT_SUSPENDED) || currentStatus.getAcctStatus().equals(NonConfigurableConstants.PRODUCT_STATUS_SUSPENDED)){
//					reactivateStatus.setStatusTo(NonConfigurableConstants.PRODUCT_STATUS_PARENT_SUSPENDED);
//				}else{
//					reactivateStatus.setStatusTo(NonConfigurableConstants.PRODUCT_STATUS_ACTIVE);
//				}
//				reactivateStatus.setPmtbProduct(product);
//				product.getPmtbProductStatuses().add(reactivateStatus);
//			}
//			reactivateStatus.setStatusDt(DateUtil.convertDateToTimestamp(reactivateDate));
//			reactivateStatus.setMstbMasterTable(ConfigurableConstants.getMasterTable(ConfigurableConstants.ACCT_REACTIVATE_REASON, reactivateCode));
//			reactivateStatus.setStatusRemarks(remarks);
//			if(newStatus){
//				saveStatuses.add(reactivateStatus);
//				//this.save(reactivateStatus, userId);
//			}else{
//				updateStatuses.add(reactivateStatus);
//				//this.update(reactivateStatus, userId);
//			}
//			// now deleting everything in between the suspension
//			List<PmtbProductStatus> deleted = new ArrayList<PmtbProductStatus>();
//			PmtbProductStatus currentProductSuspend = getCurrentStatus(product.getPmtbProductStatuses());
//			if(currentProductSuspend!=null){
//				for(PmtbProductStatus status : product.getPmtbProductStatuses()){
//					if(status.equals(currentProductSuspend)){
//						continue;
//					}
//					if(status.equals(reactivateStatus)){
//						continue;
//					}
//					if(status.getStatusDt().before(currentProductSuspend.getStatusDt())){
//						continue;
//					}
//					if(reactivateStatus!=null && status.getStatusDt().after(reactivateStatus.getStatusDt())){
//						continue;
//					}
//					deleted.add(status);
//					//this.delete(status);
//				}
//			}
//			deleteStatuses.addAll(deleted);
//			product.getPmtbProductStatuses().removeAll(deleted);
//			// now checking terminate status
//			PmtbProductStatus terminateStatus = getFutureStatus(product, NonConfigurableConstants.PRODUCT_STATUS_TERMINATED);
//			if(terminateStatus!=null){
//				deleted.clear();
//				for(PmtbProductStatus status : product.getPmtbProductStatuses()){
//					if(status.getStatusDt().after(terminateStatus.getStatusDt())){
//						deleted.add(status);
//						this.delete(status);
//					}
//				}
//				deleteStatuses.addAll(deleted);
//				product.getPmtbProductStatuses().remove(deleted);
//			}
//			if(DateUtil.isToday(reactivateDate)){
//				AmtbAcctStatus currentStatus = getStatus(acct.getAmtbAcctStatuses(), new Date(reactivateDate.getTime()+1));
//				if(currentStatus.getAcctStatus().equals(NonConfigurableConstants.PRODUCT_STATUS_PARENT_SUSPENDED) || currentStatus.getAcctStatus().equals(NonConfigurableConstants.PRODUCT_STATUS_SUSPENDED)){
//					product.setCurrentStatus(NonConfigurableConstants.PRODUCT_STATUS_PARENT_SUSPENDED);
//				}else{
//					product.setCurrentStatus(NonConfigurableConstants.PRODUCT_STATUS_ACTIVE);
//				}
//			}
//			this.daoHelper.getGenericDao().updateAll(updateStatuses, userId);
//			this.daoHelper.getGenericDao().saveAll(saveStatuses, userId);
//			this.update(product, userId);
//			// now updating AS
//			if(DateUtil.isToday(reactivateDate)){
//				String creditLimit;
//				if(product.getCreditBalance()!=null){
//					creditLimit = product.getCreditBalance().toString();
//				}else{
//					creditLimit = null;
//				}
//				String expiryDate;
//				if(product.getExpiryDate()!=null){
//					expiryDate = DateUtil.convertDateToStr(product.getExpiryDate(), DateUtil.INTERFACE_AS_PRODUCT_EXPIRY_DATE_FORMAT);
//				}else{
//					expiryDate = null;
//				}
//				Map<String, String> ASRequest = new HashMap<String, String>();
//				ASRequest.put(API.PRODUCT_CARD_NO, product.getCardNo());
//				ASRequest.put(API.PRODUCT_STATUS, NonConfigurableConstants.AS_REQUEST_PRODUCT_STATUS_ACTIVE);
//				ASRequest.put(API.PRODUCT_NEW_CREDIT_BALANCE, creditLimit);
//				ASRequest.put(API.PRODUCT_EXPIRY_DATE, expiryDate);
//				ASRequest.put(API.PRODUCT_ACCOUNT_ID, API.formulateAccountId(acct));
//				ASRequest.put(API.PRODUCT_REASON_CODE, "");
//				ASRequest.put(API.PRODUCT_UPDATED_BY, userId);
//				ASRequest.put(API.PRODUCT_TRANSFER_MODE, NonConfigurableConstants.AS_REQUEST_TRANSFER_MODE_ASYNCHRONOUS);
//				ASRequests.add(ASRequest);
//			}
//		}
//		API.updateProducts(ASRequests);
	}
	public void suspendAcct(String custNo, String subAcctNo, Date suspendDate, Date reactivateDate, String suspendCode, String remarks, String userId, boolean isChild) throws Exception{
		AmtbAccount acct = getAccount(custNo, subAcctNo);
		ArrayList<IttbCniiAcctReq> cniiList = new ArrayList<IttbCniiAcctReq>();
		suspendAcct(acct, suspendDate, reactivateDate, suspendCode, remarks, userId, isChild, cniiList);
		// Call CNII Synchronise interface
		Map<String, String> prop = (Map)SpringUtil.getBean("webserviceProperties");
		if (NonConfigurableConstants.BOOLEAN_NO.equals((String) prop.get("ws.cnii.disable")))
		{
			// Not disabled - so send interface
//			IBSCNIIUpdateAcctClient.send(cniiList);
			try
			{
				//29 Aug 2018 CNII Acct Sync
				String msg = updateCniiAcctSyncProcedure(cniiList);
				logger.info("CNII Acct Sync log : "+msg);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				throw new CniiInterfaceException("CNII Interface Acct Sync Error - "+e);
			}
		}else{
			logger.info("CNII interface is disabled");
		}
	}
	public void suspendAcct(String custNo, String parentCode, String code, Date suspendDate, Date reactivateDate, String suspendCode, String remarks, String userId, boolean isChild) throws Exception{
		AmtbAccount acct = getAccount(custNo, parentCode, code);
		ArrayList<IttbCniiAcctReq> cniiList = new ArrayList<IttbCniiAcctReq>();
		suspendAcct(acct, suspendDate, reactivateDate, suspendCode, remarks, userId, isChild, cniiList);
		// Call CNII Synchronise interface
		Map<String, String> prop = (Map)SpringUtil.getBean("webserviceProperties");
		if (NonConfigurableConstants.BOOLEAN_NO.equals((String) prop.get("ws.cnii.disable")))
		{
			// Not disabled - so send interface
//			IBSCNIIUpdateAcctClient.send(cniiList);
			
			//29 Aug 2018 CNII Acct Sync
			String msg = updateCniiAcctSyncProcedure(cniiList);
			logger.info("CNII Acct Sync log : "+msg);
		}else{
			logger.info("CNII interface is disabled");
		}
	}
	private void suspendAcct(AmtbAccount acct, Date suspendDate, Date reactivateDate, String suspendCode, String remarks, String userId, boolean isChild, ArrayList<IttbCniiAcctReq> list) throws Exception{
		// getting the current suspend for this account
		List<AmtbAcctStatus> saveOrUpdateStatuses = new ArrayList<AmtbAcctStatus>();
		List<AmtbAcctStatus> deleteStatuses = new ArrayList<AmtbAcctStatus>();
		AmtbAcctStatus currentAcctSuspend = getFutureStatus(acct, NonConfigurableConstants.ACCOUNT_STATUS_SUSPENDED);
		AmtbAcctStatus currentAcctReactivate = null;
		if(currentAcctSuspend!=null){
			for(AmtbAcctStatus status : acct.getAmtbAcctStatuses()){
				if(status.getEffectiveDt().after(currentAcctSuspend.getEffectiveDt())){
					if(currentAcctReactivate==null || currentAcctReactivate.getEffectiveDt().after(status.getEffectiveDt())){
						if(status.getAcctStatus().equals(NonConfigurableConstants.ACCOUNT_STATUS_ACTIVE) || status.getAcctStatus().equals(NonConfigurableConstants.ACCOUNT_STATUS_PARENT_SUSPENDED)){
							currentAcctReactivate = status;
						}
					}
				}
			}
		}
		// getting the list of parent suspended status and reactivate
		List<AmtbAcctStatus> pSuspends = new ArrayList<AmtbAcctStatus>();
		List<AmtbAcctStatus> pActives = new ArrayList<AmtbAcctStatus>();
		for(AmtbAcctStatus status : acct.getAmtbAcctStatuses()){
			if(!status.equals(currentAcctReactivate) && status.getEffectiveDt().after(DateUtil.getCurrentTimestamp())){
				if(status.getAcctStatus().equals(NonConfigurableConstants.ACCOUNT_STATUS_ACTIVE)){
					pActives.add(status);
				}else if(status.getAcctStatus().equals(NonConfigurableConstants.ACCOUNT_STATUS_PARENT_SUSPENDED)){
					pSuspends.add(status);
				}
			}
		}
		Collections.sort(pSuspends, new Comparator<AmtbAcctStatus>(){
			public int compare(AmtbAcctStatus o1, AmtbAcctStatus o2) {
				return o1.getEffectiveDt().compareTo(o2.getEffectiveDt());
			}
		});
		Collections.sort(pActives, new Comparator<AmtbAcctStatus>(){
			public int compare(AmtbAcctStatus o1, AmtbAcctStatus o2) {
				return o1.getEffectiveDt().compareTo(o2.getEffectiveDt());
			}
		});
		Iterator<AmtbAcctStatus> pSuspendIter = pSuspends.iterator();
		Iterator<AmtbAcctStatus> pActiveIter = pActives.iterator();
		// getting the list of suspension for parent
		List<SuspendPeriod> parentSuspensions = getSuspensions(acct.getAmtbAccount(), suspendDate);
		for(SuspendPeriod parentSuspension : parentSuspensions){
			if(pSuspendIter.hasNext()){
				AmtbAcctStatus status = pSuspendIter.next();
				status.setAcctStatus(NonConfigurableConstants.ACCOUNT_STATUS_PARENT_SUSPENDED);
				status.setEffectiveDt(parentSuspension.getSuspendStart().getEffectiveDt());
				status.setMstbMasterTable(parentSuspension.getSuspendStart().getMstbMasterTable());
				status.setSatbUser(parentSuspension.getSuspendStart().getSatbUser());
				status.setStatusRemarks(parentSuspension.getSuspendStart().getStatusRemarks());
				saveOrUpdateStatuses.add(status);
				//this.update(status);
			}else{
				AmtbAcctStatus newStatus = new AmtbAcctStatus();
				newStatus.setAmtbAccount(acct);
				acct.getAmtbAcctStatuses().add(newStatus);
				newStatus.setAcctStatus(NonConfigurableConstants.ACCOUNT_STATUS_PARENT_SUSPENDED);
				newStatus.setEffectiveDt(parentSuspension.getSuspendStart().getEffectiveDt());
				newStatus.setMstbMasterTable(parentSuspension.getSuspendStart().getMstbMasterTable());
				newStatus.setSatbUser(parentSuspension.getSuspendStart().getSatbUser());
				newStatus.setStatusRemarks(parentSuspension.getSuspendStart().getStatusRemarks());
				saveOrUpdateStatuses.add(newStatus);
				//this.save(newStatus);
			}
			if(parentSuspension.getSuspendEnd()!=null){
				if(pActiveIter.hasNext()){
					AmtbAcctStatus status = pActiveIter.next();
					status.setAcctStatus(NonConfigurableConstants.ACCOUNT_STATUS_ACTIVE);
					status.setEffectiveDt(parentSuspension.getSuspendEnd().getEffectiveDt());
					status.setMstbMasterTable(parentSuspension.getSuspendEnd().getMstbMasterTable());
					status.setSatbUser(parentSuspension.getSuspendEnd().getSatbUser());
					status.setStatusRemarks(parentSuspension.getSuspendEnd().getStatusRemarks());
					saveOrUpdateStatuses.add(status);
					//this.update(status);
				}else{
					AmtbAcctStatus newStatus = new AmtbAcctStatus();
					newStatus.setAmtbAccount(acct);
					acct.getAmtbAcctStatuses().add(newStatus);
					newStatus.setAcctStatus(NonConfigurableConstants.ACCOUNT_STATUS_ACTIVE);
					newStatus.setEffectiveDt(parentSuspension.getSuspendEnd().getEffectiveDt());
					newStatus.setMstbMasterTable(parentSuspension.getSuspendEnd().getMstbMasterTable());
					newStatus.setSatbUser(parentSuspension.getSuspendEnd().getSatbUser());
					newStatus.setStatusRemarks(parentSuspension.getSuspendEnd().getStatusRemarks());
					saveOrUpdateStatuses.add(newStatus);
					//this.save(newStatus);
				}
			}
		}
		while(pSuspendIter.hasNext()){
			AmtbAcctStatus status = pSuspendIter.next();
			acct.getAmtbAcctStatuses().remove(status);
			deleteStatuses.add(status);
			//this.delete(status);
		}
		while(pActiveIter.hasNext()){
			AmtbAcctStatus status = pActiveIter.next();
			acct.getAmtbAcctStatuses().remove(status);
			deleteStatuses.add(status);
			//this.delete(status);
		}
		// getting user
		SatbUser user = this.daoHelper.getUserDao().getUserWithRoles(CommonWindow.getUserId());
		// now parent status are all in acct. Adding account suspension
		if(!isChild){
			// taking parameter as consideration. previous will be removed.
			boolean newStatus = currentAcctSuspend==null;
			if(newStatus){
				currentAcctSuspend = new AmtbAcctStatus();
				currentAcctSuspend.setAmtbAccount(acct);
				acct.getAmtbAcctStatuses().add(currentAcctSuspend);
				currentAcctSuspend.setAcctStatus(NonConfigurableConstants.ACCOUNT_STATUS_SUSPENDED);
			}
			currentAcctSuspend.setEffectiveDt(DateUtil.convertDateToTimestamp(suspendDate));
			currentAcctSuspend.setMstbMasterTable(ConfigurableConstants.getMasterTable(ConfigurableConstants.ACCT_SUSPEND_REASON, suspendCode));
			currentAcctSuspend.setSatbUser(user);
			currentAcctSuspend.setStatusRemarks(remarks);
			if(newStatus){
				saveOrUpdateStatuses.add(currentAcctSuspend);
				//this.save(currentAcctSuspend);
			}else{
				saveOrUpdateStatuses.add(currentAcctSuspend);
				//this.update(currentAcctSuspend);
			}
			if(reactivateDate!=null){
				newStatus = currentAcctReactivate==null;
				if(newStatus){
					currentAcctReactivate = new AmtbAcctStatus();
					currentAcctReactivate.setAmtbAccount(acct);
					acct.getAmtbAcctStatuses().add(currentAcctReactivate);
				}
				currentAcctReactivate.setEffectiveDt(DateUtil.convertDateToTimestamp(reactivateDate));
				// hard code to un black list otherwise products cannot support
				currentAcctReactivate.setMstbMasterTable(ConfigurableConstants.getMasterTable(ConfigurableConstants.ACCT_REACTIVATE_REASON, "0003"));
				currentAcctReactivate.setSatbUser(user);
				// hard coded otherwise products cannot support
				currentAcctReactivate.setStatusRemarks("END OF SUSPENSION");
				boolean parentSuspended = false;
				for(SuspendPeriod parentSuspension : parentSuspensions){
					if(parentSuspension.isSuspended(reactivateDate)){
						parentSuspended = true;
						break;
					}
				}
				if(parentSuspended){
					currentAcctReactivate.setAcctStatus(NonConfigurableConstants.ACCOUNT_STATUS_PARENT_SUSPENDED);
				}else{
					currentAcctReactivate.setAcctStatus(NonConfigurableConstants.ACCOUNT_STATUS_ACTIVE);
				}
				if(newStatus){
					saveOrUpdateStatuses.add(currentAcctReactivate);
					//this.save(currentAcctReactivate);
				}else{
					saveOrUpdateStatuses.add(currentAcctReactivate);
					//this.update(currentAcctReactivate);
				}
			}else{
				if(currentAcctReactivate!=null){
					acct.getAmtbAcctStatuses().remove(currentAcctReactivate);
					deleteStatuses.add(currentAcctReactivate);
					//this.delete(currentAcctReactivate);
				}
			}
		}
		// now deleting everything in between the suspension
		List<AmtbAcctStatus> deleted = new ArrayList<AmtbAcctStatus>();
		if(currentAcctSuspend!=null){
			for(AmtbAcctStatus status : acct.getAmtbAcctStatuses()){
				if(status.equals(currentAcctSuspend)){
					continue;
				}
				if(status.equals(currentAcctReactivate)){
					continue;
				}
				if(status.getEffectiveDt().before(currentAcctSuspend.getEffectiveDt())){
					continue;
				}
				if(currentAcctReactivate!=null && status.getEffectiveDt().after(currentAcctReactivate.getEffectiveDt())){
					continue;
				}
				deleted.add(status);
				deleteStatuses.add(status);
				//this.delete(status);
			}
		}
		acct.getAmtbAcctStatuses().removeAll(deleted);
		// now checking for terminated status
		AmtbAcctStatus terminatedStatus = getFutureStatus(acct, NonConfigurableConstants.ACCOUNT_STATUS_TERMINATED);
		if(terminatedStatus!=null){// if there is termination. Clear everything except closed
			deleted.clear();
			for(AmtbAcctStatus status : acct.getAmtbAcctStatuses()){
				if(status.getEffectiveDt().after(terminatedStatus.getEffectiveDt()) && !status.getAcctStatus().equals(NonConfigurableConstants.ACCOUNT_STATUS_CLOSED)){
					deleted.add(status);
					deleteStatuses.add(status);
					//this.delete(status);
				}
			}
			acct.getAmtbAcctStatuses().removeAll(deleted);
		}
		this.daoHelper.getGenericDao().updateAll(saveOrUpdateStatuses, "");
		this.daoHelper.getGenericDao().deleteAll(deleteStatuses);
		this.update(acct, userId);
		// suspending product
		suspendProducts(acct, suspendDate, reactivateDate, suspendCode, remarks, userId);
		List<String> cardLessProductList = new ArrayList<String>();
		List<PmtbProductType> cardlessProducts = this.businessHelper.getProductTypeBusiness().getCardlessProductType();
		for(PmtbProductType cardlessProduct : cardlessProducts) {
			cardLessProductList.add(cardlessProduct.getProductTypeId().trim());
		}
//		if(DateUtil.isToday(suspendDate)){
			// now updating cnii only if the suspension is today
			for(AmtbSubscTo subscription : acct.getAmtbSubscTos()){
				PmtbProductType productType = subscription.getComp_id().getPmtbProductType();
//				if(productType.getProductTypeId().equals(NonConfigurableConstants.PREMIER_SERVICE)){
				if(cardLessProductList.contains(productType.getProductTypeId())){
					IttbCniiAcctReq cniiRequest = new IttbCniiAcctReq();
					cniiRequest.setReqId(DateUtil.convertDateToStr(DateUtil.getCurrentDate(), DateUtil.GLOBAL_CNII_SEQ_FORMAT) + this.daoHelper.getAccountDao().getNextSequenceNo(Sequence.CNII_REQ_NO_SEQUENCE));
					cniiRequest.setReqDate(DateUtil.getCurrentTimestamp());
					cniiRequest.setEventType(NonConfigurableConstants.CNII_REQUEST_EVENT_TYPE_DEACTIVATE);
					cniiRequest.setReqStatus(NonConfigurableConstants.CNII_REQUEST_STATUS_PENDING);
					cniiRequest.setSuspensionStartDt(DateUtil.convertUtilDateToSqlDate(suspendDate));
					if(reactivateDate!=null){
						cniiRequest.setSuspensionEndDt(DateUtil.convertUtilDateToSqlDate(reactivateDate));
					}
					cniiRequest.setValidFlg(NonConfigurableConstants.BOOLEAN_YES);
					cniiRequest.setUpdatedBy(NonConfigurableConstants.CNII_REQUEST_UPDATED_BY_IBS);
					StringBuffer acctId = new StringBuffer();
					StringBuffer parentId = new StringBuffer();
					if(acct.getCustNo()!=null && acct.getCustNo().length()!=0){
						acctId.append(acct.getCustNo());
						// Should not have parent since acct is the top-most account
						//parentId.append(acct.getCustNo());
					}else{
						acctId.append(acct.getCode());
						// Not required to append as parent is always without the last code
						// e.g. if dept = 304DIV1DEP1, parent will be 304DIV1
						//parentId.append(acct.getCode());
						// now getting the parent
						AmtbAccount parent = acct.getAmtbAccount();
						if(parent!=null){// this shouldn't happen
							while(parent.getCustNo()==null){
								acctId.insert(0, parent.getCode());
								parentId.insert(0, parent.getCode());
								parent = this.daoHelper.getAccountDao().getParent(parent).getAmtbAccount();
							}
							acctId.insert(0, parent.getCustNo());
							parentId.insert(0, parent.getCustNo());
						}
					}
					cniiRequest.setAccountId(acctId.toString());
					if(acct.getCustNo()!=null && acct.getCustNo().length()!=0){
						cniiRequest.setAccountCd(acct.getCustNo());
					}else{
						cniiRequest.setAccountCd(acct.getCode());
					}
					cniiRequest.setAccountNm(acct.getAccountName());
					cniiRequest.setParentId(parentId.toString());
					cniiRequest.setUpdatedDt(DateUtil.getCurrentTimestamp());
					//this.save(cniiRequest);
					list.add(cniiRequest);
					break;
				}
			}
//		}
		// now suspending all children
		List<AmtbAccount> children = this.daoHelper.getAccountDao().getChildrenAccountsWithStatuses(acct.getAccountNo());
		for(AmtbAccount child : children){
			if(getCurrentStatus(child.getAmtbAcctStatuses()).getAcctStatus().equals(NonConfigurableConstants.ACCOUNT_STATUS_ACTIVE)){
				suspendAcct(child, suspendDate, reactivateDate, suspendCode, remarks, userId, true, list);
			}
		}
	}
	private List<SuspendPeriod> getSuspensions(AmtbAccount acct, Date date){
		List<SuspendPeriod> suspensions = new ArrayList<SuspendPeriod>();
		if(acct==null){
			return suspensions;
		}
		AmtbAcctStatus start = null;
		TreeSet<AmtbAcctStatus> sortedStatus = new TreeSet<AmtbAcctStatus>(new Comparator<AmtbAcctStatus>(){
			public int compare(AmtbAcctStatus o1, AmtbAcctStatus o2) {
				return o1.getEffectiveDt().compareTo(o2.getEffectiveDt());
			}
		});
		sortedStatus.addAll(acct.getAmtbAcctStatuses());
		AmtbAcctStatus currentStatus = getCurrentStatus(sortedStatus);
		if(currentStatus!=null && (currentStatus.getAcctStatus().equals(NonConfigurableConstants.ACCOUNT_STATUS_SUSPENDED) || currentStatus.getAcctStatus().equals(NonConfigurableConstants.ACCOUNT_STATUS_PARENT_SUSPENDED))){
			start=currentStatus;
		}
		for(AmtbAcctStatus status : sortedStatus){
			if(!status.getEffectiveDt().before(date)){
				if(status.getAcctStatus().equals(NonConfigurableConstants.ACCOUNT_STATUS_SUSPENDED) || status.getAcctStatus().equals(NonConfigurableConstants.ACCOUNT_STATUS_PARENT_SUSPENDED)){
					if(start==null){
						start = status;
					}
				}else if(status.getAcctStatus().equals(NonConfigurableConstants.ACCOUNT_STATUS_ACTIVE)){
					if(start!=null){
						suspensions.add(new SuspendPeriod(start, status));
						start = null;
					}
				}
			}
		}
		if(start!=null){
			suspensions.add(new SuspendPeriod(start, null));
		}
		return suspensions;
	}
	private void suspendProducts(AmtbAccount acct, Date suspendDate, Date reactivateDate, String suspendCode, String remarks, String userId) throws Exception{
		// to suspend products
		List<PmtbProduct> products = this.daoHelper.getProductDao().getProductsWithStatus(acct);
		// getting the list of suspension for parent
		List<SuspendPeriod> parentSuspensions = getSuspensions(acct, suspendDate);
		List<PmtbProduct> updateProducts = new ArrayList<PmtbProduct>();
		List<PmtbProductStatus> updateStatuses = new ArrayList<PmtbProductStatus>();
		List<PmtbProductStatus> saveStatuses = new ArrayList<PmtbProductStatus>();
		List<PmtbProductStatus> deleteStatuses = new ArrayList<PmtbProductStatus>();
		List<Map<String, String>> ASRequests = new ArrayList<Map<String, String>>();
		MstbMasterTable masterCode = ConfigurableConstants.getMasterTable(ConfigurableConstants.ACCT_SUSPEND_REASON, suspendCode);
		for(PmtbProduct product : products){
			PmtbProductStatus currentStatus = getCurrentStatus(product.getPmtbProductStatuses(), false);
			List<PmtbProductStatus> productNewStatuses = new ArrayList<PmtbProductStatus>();
			List<PmtbProductStatus> productDeleteStatuses = new ArrayList<PmtbProductStatus>();
			// skipping those that can't be suspended
			if(currentStatus==null ||
					currentStatus.getStatusTo().equals(NonConfigurableConstants.PRODUCT_STATUS_TERMINATED) ||
					currentStatus.getStatusTo().equals(NonConfigurableConstants.PRODUCT_STATUS_RECYCLED) ||
					currentStatus.getStatusTo().equals(NonConfigurableConstants.PRODUCT_STATUS_USED)){
				continue;
			}
			// looping thru each product status
			for(PmtbProductStatus status : product.getPmtbProductStatuses()){
				if(status.getStatusDt().before(new Date())){
					continue;
				}
				String statusMasterType = status.getMstbMasterTable().getMasterType();
				if(statusMasterType.equals(ConfigurableConstants.PRODUCT_SUSPEND_REASON) || statusMasterType.equals(ConfigurableConstants.PRODUCT_REACTIVATE_REASON)){
					boolean found = false;
					for(SuspendPeriod parentSuspension : parentSuspensions){
						if(
								// if status = start of parent suspension or
								status.getStatusDt().equals(parentSuspension.getSuspendStart().getEffectiveDt()) ||
								// status is after suspension start and (suspension end is null or status is before suspension end) or
								status.getStatusDt().after(parentSuspension.getSuspendStart().getEffectiveDt()) && (parentSuspension.getSuspendEnd()==null || status.getStatusDt().before(parentSuspension.getSuspendEnd().getEffectiveDt())) ||
								// parent suspension end is not null and status = end of parent suspension
								(parentSuspension.getSuspendEnd()!=null && status.getStatusDt().equals(parentSuspension.getSuspendEnd().getEffectiveDt()))
							){
							found = true;
							if(status.getStatusTo().equals(NonConfigurableConstants.PRODUCT_STATUS_ACTIVE)){
								status.setStatusTo(NonConfigurableConstants.PRODUCT_STATUS_PARENT_SUSPENDED);
								updateStatuses.add(status);
								break;
							}
						}
					}
					if(!found){
						if(status.getStatusTo().equals(NonConfigurableConstants.PRODUCT_STATUS_PARENT_SUSPENDED)){
							status.setStatusTo(NonConfigurableConstants.PRODUCT_STATUS_ACTIVE);
							updateStatuses.add(status);
							break;
						}
					}
				}
			}
			// looping thru each account status
			for(SuspendPeriod parentSuspension : parentSuspensions){
				boolean startFound = false;
				boolean endFound = parentSuspension.getSuspendEnd()==null;
				for(PmtbProductStatus status : product.getPmtbProductStatuses()){
					if(status.getStatusDt().before(new Date())){
						continue;
					}
					String statusMasterType = status.getMstbMasterTable().getMasterType();
					// if status = start of parent suspension or
					if(status.getStatusDt().equals(parentSuspension.getSuspendStart().getEffectiveDt())){
						// if it is a product status
						if(statusMasterType.equals(ConfigurableConstants.PRODUCT_SUSPEND_REASON) || statusMasterType.equals(ConfigurableConstants.PRODUCT_REACTIVATE_REASON)){
							if(status.getStatusTo().equals(NonConfigurableConstants.PRODUCT_STATUS_ACTIVE)){
								if(getStatus(product.getPmtbProductStatuses(), status.getStatusDt(), false).getStatusTo().equals(NonConfigurableConstants.PRODUCT_STATUS_SUSPENDED)){
									status.setStatusTo(NonConfigurableConstants.PRODUCT_STATUS_SUSPENDED);
								}else{
									status.setStatusTo(NonConfigurableConstants.PRODUCT_STATUS_PARENT_SUSPENDED);
								}
								updateStatuses.add(status);
							}
						}else if(statusMasterType.equals(ConfigurableConstants.ACCT_SUSPEND_REASON) || statusMasterType.equals(ConfigurableConstants.ACCT_REACTIVATE_REASON)){// if it is an account status
							if(status.getStatusTo().equals(NonConfigurableConstants.PRODUCT_STATUS_PARENT_SUSPENDED) && getStatus(product.getPmtbProductStatuses(), status.getStatusDt(), false).getStatusTo().equals(NonConfigurableConstants.PRODUCT_STATUS_SUSPENDED)){
								status.setStatusTo(NonConfigurableConstants.PRODUCT_STATUS_SUSPENDED);
								updateStatuses.add(status);
							}else if(status.getStatusTo().equals(NonConfigurableConstants.PRODUCT_STATUS_SUSPENDED) && !getStatus(product.getPmtbProductStatuses(), status.getStatusDt(), false).getStatusTo().equals(NonConfigurableConstants.PRODUCT_STATUS_SUSPENDED)){
								status.setStatusTo(NonConfigurableConstants.PRODUCT_STATUS_PARENT_SUSPENDED);
								updateStatuses.add(status);
							}
						}
						startFound = true;
					}
					// parent suspension end is not null and status = end of parent suspension
					if(parentSuspension.getSuspendEnd()!=null && status.getStatusDt().equals(parentSuspension.getSuspendEnd().getEffectiveDt())){
						// if it is a product status
						if(statusMasterType.equals(ConfigurableConstants.PRODUCT_SUSPEND_REASON) || statusMasterType.equals(ConfigurableConstants.PRODUCT_REACTIVATE_REASON)){
							if(status.getStatusTo().equals(NonConfigurableConstants.PRODUCT_STATUS_PARENT_SUSPENDED)){
								status.setStatusTo(NonConfigurableConstants.PRODUCT_STATUS_ACTIVE);
								updateStatuses.add(status);
							}
						}else if(statusMasterType.equals(ConfigurableConstants.ACCT_SUSPEND_REASON) || statusMasterType.equals(ConfigurableConstants.ACCT_REACTIVATE_REASON)){// if it is an account status
							if(status.getStatusTo().equals(NonConfigurableConstants.PRODUCT_STATUS_PARENT_SUSPENDED)){
								status.setStatusTo(NonConfigurableConstants.PRODUCT_STATUS_ACTIVE);
								updateStatuses.add(status);
							}
						}
						endFound = true;
					}
					if(startFound && endFound){
						break;
					}
				}
				if(!startFound){
					PmtbProductStatus prevStatus = getStatus(product.getPmtbProductStatuses(), parentSuspension.getSuspendStart().getEffectiveDt(), false);
					PmtbProductStatus newStatus = new PmtbProductStatus();
					newStatus.setMstbMasterTable(parentSuspension.getSuspendStart().getMstbMasterTable());
					newStatus.setPmtbProduct(product);
					productNewStatuses.add(newStatus);
					newStatus.setStatusDt(parentSuspension.getSuspendStart().getEffectiveDt());
					newStatus.setStatusFrom(prevStatus.getStatusTo());
					newStatus.setStatusRemarks(parentSuspension.getSuspendStart().getStatusRemarks());
					if(prevStatus.getStatusTo().equals(NonConfigurableConstants.PRODUCT_STATUS_SUSPENDED)){
						newStatus.setStatusTo(NonConfigurableConstants.PRODUCT_STATUS_SUSPENDED);
					}else{
						newStatus.setStatusTo(NonConfigurableConstants.PRODUCT_STATUS_PARENT_SUSPENDED);
					}
					saveStatuses.add(newStatus);
				}
				if(!endFound){
					PmtbProductStatus prevStatus = getStatus(product.getPmtbProductStatuses(), parentSuspension.getSuspendEnd().getEffectiveDt(), false);
					PmtbProductStatus newStatus = new PmtbProductStatus();
					newStatus.setMstbMasterTable(parentSuspension.getSuspendEnd().getMstbMasterTable());
					newStatus.setPmtbProduct(product);
					productNewStatuses.add(newStatus);
					newStatus.setStatusDt(parentSuspension.getSuspendEnd().getEffectiveDt());
					newStatus.setStatusFrom(prevStatus.getStatusTo());
					newStatus.setStatusRemarks(parentSuspension.getSuspendEnd().getStatusRemarks());
					if(prevStatus.getStatusTo().equals(NonConfigurableConstants.PRODUCT_STATUS_SUSPENDED)){
						newStatus.setStatusTo(NonConfigurableConstants.PRODUCT_STATUS_SUSPENDED);
					}else{
						newStatus.setStatusTo(NonConfigurableConstants.PRODUCT_STATUS_ACTIVE);
					}
					saveStatuses.add(newStatus);
				}
			}
			product.getPmtbProductStatuses().addAll(productNewStatuses);
			// now clearing all account status in the product that is not found.
			for(PmtbProductStatus status : product.getPmtbProductStatuses()){
				if(status.getStatusDt().before(new Date())){
					continue;
				}
				// skip status that is of product status
				if(
						status.getMstbMasterTable().getMasterType().equals(ConfigurableConstants.PRODUCT_SUSPEND_REASON) ||
						status.getMstbMasterTable().getMasterType().equals(ConfigurableConstants.PRODUCT_REACTIVATE_REASON) ||
						status.getMstbMasterTable().getMasterType().equals(ConfigurableConstants.ISSUE_REASON_TYPE)){
					continue;
				}
				boolean found = false;
				for(SuspendPeriod parentSuspension : parentSuspensions){
					if(status.getStatusDt().equals(parentSuspension.getSuspendStart().getEffectiveDt())){
						found = true;
						break;
					}else if(parentSuspension.getSuspendEnd()!=null && status.getStatusDt().equals(parentSuspension.getSuspendEnd().getEffectiveDt())){
						found = true;
						break;
					}
				}
				if(found){
					continue;
				}
				if(status.getStatusDt().getTime() == suspendDate.getTime()){
					continue;
				}
				if(reactivateDate!=null && status.getStatusDt().getTime() == reactivateDate.getTime()){
					continue;
				}
				productDeleteStatuses.add(status);
				deleteStatuses.add(status);
			}
			product.getPmtbProductStatuses().removeAll(productDeleteStatuses);
			if(DateUtil.isToday(suspendDate) && !product.getCurrentStatus().equals(NonConfigurableConstants.PRODUCT_STATUS_SUSPENDED)){
				product.setCurrentStatus(NonConfigurableConstants.PRODUCT_STATUS_PARENT_SUSPENDED);
				// clearing product status
				updateProducts.add(product);
			}
			// now realigning the status to and from status
			Set<PmtbProductStatus> sortedStatuses = new TreeSet<PmtbProductStatus>(new Comparator<PmtbProductStatus>(){
				public int compare(PmtbProductStatus o1, PmtbProductStatus o2) {
					return o1.getStatusDt().compareTo(o2.getStatusDt());
				}
			});
			sortedStatuses.addAll(product.getPmtbProductStatuses());
			PmtbProductStatus prevStatus = null;
			for(PmtbProductStatus status : sortedStatuses){
				// skipping the first one as it is always "New" to product type default status
				logger.info("from = " + status.getStatusFrom() + ", to = " + status.getStatusTo());
				if(prevStatus != null){
					// if previous status to not equals status from
					if(!prevStatus.getStatusTo().equals(status.getStatusFrom())){
						status.setStatusFrom(prevStatus.getStatusTo());
						// if it is not the new status
						if(!saveStatuses.contains(status) && !updateStatuses.contains(status) && !deleteStatuses.contains(status)){
							updateStatuses.add(status);
						}
					}
				}
				// now assigning previous status and go to next status
				prevStatus = status;
			}
			product.getPmtbProductStatuses().clear();
			// now updating AS
			if(DateUtil.isToday(suspendDate)){
				String creditLimit;
				if(product.getCreditBalance()!=null){
					creditLimit = product.getCreditBalance().toString();
				}else{
					creditLimit = null;
				}
				String expiryDate;
				if(product.getExpiryDate()!=null){
					expiryDate = DateUtil.convertDateToStr(product.getExpiryDate(), DateUtil.INTERFACE_AS_PRODUCT_EXPIRY_DATE_FORMAT);
				}else{
					expiryDate = null;
				}
				Map<String, String> ASRequest = new HashMap<String, String>();
				ASRequest.put(API.PRODUCT_CARD_NO, product.getCardNo());
				ASRequest.put(API.PRODUCT_STATUS, NonConfigurableConstants.AS_REQUEST_PRODUCT_STATUS_INACTIVE);
				ASRequest.put(API.PRODUCT_NEW_CREDIT_BALANCE, creditLimit);
				ASRequest.put(API.PRODUCT_EXPIRY_DATE, expiryDate);
				ASRequest.put(API.PRODUCT_ACCOUNT_ID, API.formulateAccountId(acct));
				if(masterCode!=null){
					ASRequest.put(API.PRODUCT_REASON_CODE, masterCode.getInterfaceMappingValue());
				}else{
					ASRequest.put(API.PRODUCT_REASON_CODE, "");
				}
				ASRequest.put(API.PRODUCT_UPDATED_BY, userId);
				ASRequest.put(API.PRODUCT_OFFLINE_COUNT, StringUtil.numberToString(product.getOfflineCount()));
				ASRequest.put(API.PRODUCT_OFFLINE_AMOUNT, StringUtil.bigDecimalToString(product.getOfflineAmount()));
				ASRequest.put(API.PRODUCT_OFFLINE_TXN_AMOUNT, StringUtil.bigDecimalToString(product.getOfflineTxnAmount()));
				ASRequest.put(API.PRODUCT_FORCE_ONLINE, API.formulateForceOnline(product, product.getPmtbProductType()));
				ASRequest.put(API.PRODUCT_TRANSFER_MODE, NonConfigurableConstants.AS_REQUEST_TRANSFER_MODE_ASYNCHRONOUS);
				ASRequests.add(ASRequest);
			}
//			PmtbProductStatus currentProductSuspend = getFutureStatus(product, NonConfigurableConstants.ACCOUNT_STATUS_SUSPENDED);
//			PmtbProductStatus currentProductReactivate = null;
//			if(currentProductSuspend!=null){
//				for(PmtbProductStatus status : product.getPmtbProductStatuses()){
//					if(status.getStatusDt().after(currentProductSuspend.getStatusDt())){
//						if(currentProductReactivate==null || currentProductReactivate.getStatusDt().after(status.getStatusDt())){
//							if(status.getStatusTo().equals(NonConfigurableConstants.PRODUCT_STATUS_ACTIVE) || status.getStatusTo().equals(NonConfigurableConstants.PRODUCT_STATUS_PARENT_SUSPENDED)){
//								currentProductReactivate = status;
//							}
//						}
//					}
//				}
//			}
//			// getting the list of parent suspended status and reactivate
//			List<PmtbProductStatus> pSuspends = new ArrayList<PmtbProductStatus>();
//			List<PmtbProductStatus> pActives = new ArrayList<PmtbProductStatus>();
//			for(PmtbProductStatus status : product.getPmtbProductStatuses()){
//				if(!status.equals(currentProductReactivate) && status.getStatusDt().after(DateUtil.getCurrentTimestamp())){
//					if(status.getStatusTo().equals(NonConfigurableConstants.ACCOUNT_STATUS_ACTIVE)){
//						pActives.add(status);
//					}else if(status.getStatusTo().equals(NonConfigurableConstants.ACCOUNT_STATUS_PARENT_SUSPENDED)){
//						pSuspends.add(status);
//					}
//				}
//			}
//			Collections.sort(pSuspends, new Comparator<PmtbProductStatus>(){
//				public int compare(PmtbProductStatus o1, PmtbProductStatus o2) {
//					return o1.getStatusDt().compareTo(o2.getStatusDt());
//				}
//			});
//			Collections.sort(pActives, new Comparator<PmtbProductStatus>(){
//				public int compare(PmtbProductStatus o1, PmtbProductStatus o2) {
//					return o1.getStatusDt().compareTo(o2.getStatusDt());
//				}
//			});
//			Iterator<PmtbProductStatus> pSuspendIter = pSuspends.iterator();
//			Iterator<PmtbProductStatus> pActiveIter = pActives.iterator();
//			// getting the list of suspension for parent
//			for(SuspendPeriod parentSuspension : parentSuspensions){
//				if(pSuspendIter.hasNext()){
//					PmtbProductStatus status = pSuspendIter.next();
//					status.setStatusTo(NonConfigurableConstants.ACCOUNT_STATUS_PARENT_SUSPENDED);
//					status.setStatusDt(parentSuspension.getSuspendStart().getEffectiveDt());
//					status.setMstbMasterTable(parentSuspension.getSuspendStart().getMstbMasterTable());
//					status.setStatusRemarks(parentSuspension.getSuspendStart().getStatusRemarks());
//					updateStatuses.add(status);
//					//this.update(status, parentSuspension.getSuspendStart().getSatbUser().getLoginId());
//				}else{
//					PmtbProductStatus newStatus = new PmtbProductStatus();
//					newStatus.setPmtbProduct(product);
//					product.getPmtbProductStatuses().add(newStatus);
//					newStatus.setStatusFrom(NonConfigurableConstants.PRODUCT_STATUS_ACTIVE);
//					newStatus.setStatusTo(NonConfigurableConstants.PRODUCT_STATUS_PARENT_SUSPENDED);
//					newStatus.setStatusDt(parentSuspension.getSuspendStart().getEffectiveDt());
//					newStatus.setMstbMasterTable(parentSuspension.getSuspendStart().getMstbMasterTable());
//					newStatus.setStatusRemarks(parentSuspension.getSuspendStart().getStatusRemarks());
//					saveStatuses.add(newStatus);
//					//this.save(newStatus, parentSuspension.getSuspendStart().getSatbUser().getLoginId());
//				}
//				if(parentSuspension.getSuspendEnd()!=null){
//					if(pActiveIter.hasNext()){
//						PmtbProductStatus status = pActiveIter.next();
//						status.setStatusTo(NonConfigurableConstants.ACCOUNT_STATUS_ACTIVE);
//						status.setStatusDt(parentSuspension.getSuspendEnd().getEffectiveDt());
//						status.setMstbMasterTable(parentSuspension.getSuspendEnd().getMstbMasterTable());
//						status.setStatusRemarks(parentSuspension.getSuspendEnd().getStatusRemarks());
//						updateStatuses.add(status);
//						//this.update(status, parentSuspension.getSuspendStart().getSatbUser().getLoginId());
//					}else{
//						PmtbProductStatus newStatus = new PmtbProductStatus();
//						newStatus.setPmtbProduct(product);
//						product.getPmtbProductStatuses().add(newStatus);
//						newStatus.setStatusFrom(NonConfigurableConstants.PRODUCT_STATUS_PARENT_SUSPENDED);
//						newStatus.setStatusTo(NonConfigurableConstants.ACCOUNT_STATUS_ACTIVE);
//						newStatus.setStatusDt(parentSuspension.getSuspendEnd().getEffectiveDt());
//						newStatus.setMstbMasterTable(parentSuspension.getSuspendEnd().getMstbMasterTable());
//						newStatus.setStatusRemarks(parentSuspension.getSuspendEnd().getStatusRemarks());
//						saveStatuses.add(newStatus);
//						this.save(newStatus, parentSuspension.getSuspendStart().getSatbUser().getLoginId());
//					}
//				}
//			}
//			while(pSuspendIter.hasNext()){
//				PmtbProductStatus status = pSuspendIter.next();
//				product.getPmtbProductStatuses().remove(status);
//				deleteStatuses.add(status);
//				//this.delete(status);
//			}
//			while(pActiveIter.hasNext()){
//				PmtbProductStatus status = pActiveIter.next();
//				product.getPmtbProductStatuses().remove(status);
//				deleteStatuses.add(status);
//				//this.delete(status);
//			}
//			// now deleting everything in between the suspension
//			List<PmtbProductStatus> deleted = new ArrayList<PmtbProductStatus>();
//			if(currentProductSuspend!=null){
//				for(PmtbProductStatus status : product.getPmtbProductStatuses()){
//					if(status.equals(currentProductSuspend)){
//						continue;
//					}
//					if(status.equals(currentProductReactivate)){
//						continue;
//					}
//					if(status.getStatusDt().before(currentProductSuspend.getStatusDt())){
//						continue;
//					}
//					if(currentProductReactivate!=null && status.getStatusDt().after(currentProductReactivate.getStatusDt())){
//						continue;
//					}
//					deleted.add(status);
//					//this.delete(status);
//				}
//			}
//			product.getPmtbProductStatuses().removeAll(deleted);
//			deleteStatuses.addAll(deleted);
//			// looping thru each status
//			for(PmtbProductStatus status : product.getPmtbProductStatuses()){
//				boolean found = false;
//				for(SuspendPeriod parentSuspension : parentSuspensions){
//					// if status is after suspension start and (suspension end is null or status is before suspension end
//					if(status.getStatusDt().after(parentSuspension.getSuspendStart().getEffectiveDt())
//							&& (parentSuspension.getSuspendEnd()==null
//									|| status.getStatusDt().before(parentSuspension.getSuspendEnd().getEffectiveDt()))){
//						if(status.getStatusTo().equals(NonConfigurableConstants.PRODUCT_STATUS_ACTIVE)){
//							status.setStatusTo(NonConfigurableConstants.PRODUCT_STATUS_PARENT_SUSPENDED);
//							updateStatuses.add(status);
//						}
//					}
//				}
//			}
//			// now checking terminate status
//			PmtbProductStatus terminateStatus = getFutureStatus(product, NonConfigurableConstants.PRODUCT_STATUS_TERMINATED);
//			if(terminateStatus!=null){
//				deleted.clear();
//				for(PmtbProductStatus status : product.getPmtbProductStatuses()){
//					if(status.getStatusDt().after(terminateStatus.getStatusDt())){
//						deleted.add(status);
//						//this.delete(status);
//					}
//				}
//				product.getPmtbProductStatuses().remove(deleted);
//				deleteStatuses.addAll(deleted);
//			}
//			if(DateUtil.isToday(suspendDate)){
//				product.setCurrentStatus(NonConfigurableConstants.PRODUCT_STATUS_PARENT_SUSPENDED);
//			}
//			this.update(product, userId);
//			this.daoHelper.getGenericDao().saveAll(saveStatuses, userId);
//			this.daoHelper.getGenericDao().updateAll(updateStatuses, userId);
//			this.daoHelper.getGenericDao().deleteAll(deleteStatuses);
//			// now updating AS
//			if(DateUtil.isToday(suspendDate)){
//				String creditLimit;
//				if(product.getCreditBalance()!=null){
//					creditLimit = product.getCreditBalance().toString();
//				}else{
//					creditLimit = null;
//				}
//				String expiryDate;
//				if(product.getExpiryDate()!=null){
//					expiryDate = DateUtil.convertDateToStr(product.getExpiryDate(), DateUtil.INTERFACE_AS_PRODUCT_EXPIRY_DATE_FORMAT);
//				}else{
//					expiryDate = null;
//				}
//				Map<String, String> ASRequest = new HashMap<String, String>();
//				ASRequest.put(API.PRODUCT_CARD_NO, product.getCardNo());
//				ASRequest.put(API.PRODUCT_STATUS, NonConfigurableConstants.AS_REQUEST_PRODUCT_STATUS_INACTIVE);
//				ASRequest.put(API.PRODUCT_NEW_CREDIT_BALANCE, creditLimit);
//				ASRequest.put(API.PRODUCT_EXPIRY_DATE, expiryDate);
//				ASRequest.put(API.PRODUCT_ACCOUNT_ID, API.formulateAccountId(acct));
//				if(masterCode!=null){
//					ASRequest.put(API.PRODUCT_REASON_CODE, masterCode.getInterfaceMappingValue());
//				}else{
//					ASRequest.put(API.PRODUCT_REASON_CODE, "");
//				}
//				ASRequest.put(API.PRODUCT_UPDATED_BY, userId);
//				ASRequest.put(API.PRODUCT_TRANSFER_MODE, NonConfigurableConstants.AS_REQUEST_TRANSFER_MODE_ASYNCHRONOUS);
//				ASRequests.add(ASRequest);
//			}
		}
		logger.info("update products size = " + updateProducts.size());
		logger.info("update statuses size = " + updateStatuses.size());
		logger.info("saved statuses size = " + saveStatuses.size());
		logger.info("delete statuses size = " + deleteStatuses.size());
		this.daoHelper.getProductDao().updateAll(updateProducts, userId);
		this.daoHelper.getProductDao().updateAll(updateStatuses, userId);
		this.daoHelper.getProductDao().saveAll(saveStatuses, userId);
		this.daoHelper.getProductDao().deleteAll(deleteStatuses);
		API.updateProducts(ASRequests);
	}
	private AmtbAcctStatus getFutureStatus(AmtbAccount acct, String status){
		for(AmtbAcctStatus acctStatus : acct.getAmtbAcctStatuses()){
			if(acctStatus.getEffectiveDt().after(DateUtil.getCurrentDate()) && acctStatus.getAcctStatus().equals(status)){
				return acctStatus;
			}
		}
		return null;
	}
	private PmtbProductStatus getFutureStatus(PmtbProduct product, String status){
		for(PmtbProductStatus productStatus : product.getPmtbProductStatuses()){
			if(productStatus.getStatusDt().after(DateUtil.getCurrentDate()) && productStatus.getStatusTo().equals(status)){
				return productStatus;
			}
		}
		return null;
	}
	private PmtbProductStatus getCurrentStatus(Collection<PmtbProductStatus> statuses, boolean temp){
		return getStatus(statuses, DateUtil.getCurrentTimestamp(), false);
	}
	private PmtbProductStatus getStatus(Collection<PmtbProductStatus> statuses, Date date , boolean temp){
		TreeSet<PmtbProductStatus> sortedStatus = new TreeSet<PmtbProductStatus>(new Comparator<PmtbProductStatus>(){
			public int compare(PmtbProductStatus o1, PmtbProductStatus o2) {
				return o1.getStatusDt().compareTo(o2.getStatusDt());
			}
		});
		for(PmtbProductStatus productStatus : statuses){
			if(productStatus.getStatusDt().before(date)){
				sortedStatus.add(productStatus);
			}
		}
		if(!sortedStatus.isEmpty()){
			return sortedStatus.last();
		}else{
			return null;
		}
	}
	public AmtbAcctStatus getCurrentStatus(Collection<AmtbAcctStatus> statuses){
		return getStatus(statuses, DateUtil.getCurrentTimestamp());
	}
	private AmtbAcctStatus getStatus(Collection<AmtbAcctStatus> statuses, Date date){
		TreeSet<AmtbAcctStatus> sortedStatus = new TreeSet<AmtbAcctStatus>(new Comparator<AmtbAcctStatus>(){
			public int compare(AmtbAcctStatus o1, AmtbAcctStatus o2) {
				return o1.getEffectiveDt().compareTo(o2.getEffectiveDt());
			}
		});
		for(AmtbAcctStatus acctStatus : statuses){
			if(acctStatus.getEffectiveDt().before(date)){
				sortedStatus.add(acctStatus);
			}
		}
		if(!sortedStatus.isEmpty()){
			return sortedStatus.last();
		}else{
			return null;
		}
	}
	public boolean hasPastCloseTerminate(String custNo, String parentCode, String code, List<Date> dates){
		AmtbAccount acct = getAccount(custNo, parentCode, code);
		for(Date date : dates){
			AmtbAcctStatus status = getStatus(acct.getAmtbAcctStatuses(), date);
			if(status.getAcctStatus().equals(NonConfigurableConstants.ACCOUNT_STATUS_CLOSED) ||
					status.getAcctStatus().equals(NonConfigurableConstants.ACCOUNT_STATUS_TERMINATED)){
				return true;
			}
		}
		return false;
	}
	public boolean hasPastCloseTerminate(String custNo, String subAcctNo, List<Date> dates){
		AmtbAccount acct = getAccount(custNo, subAcctNo);
		for(Date date : dates){
			AmtbAcctStatus status = getStatus(acct.getAmtbAcctStatuses(), date);
			if(status.getAcctStatus().equals(NonConfigurableConstants.ACCOUNT_STATUS_CLOSED) ||
					status.getAcctStatus().equals(NonConfigurableConstants.ACCOUNT_STATUS_TERMINATED)){
				return true;
			}
		}
		return false;
	}
	public boolean hasFutureReactivate(String custNo, String parentCode, String code){
		AmtbAccount acct = getAccount(custNo, parentCode, code);
		return hasFutureStatus(acct, NonConfigurableConstants.ACCOUNT_STATUS_ACTIVE);
	}
	public boolean hasFutureTerminate(String custNo, String parentCode, String code){
		AmtbAccount acct = getAccount(custNo, parentCode, code);
		return hasFutureStatus(acct, NonConfigurableConstants.ACCOUNT_STATUS_TERMINATED);
	}
	public boolean hasFutureSuspend(String custNo, String parentCode, String code){
		AmtbAccount acct = getAccount(custNo, parentCode, code);
		return hasFutureStatus(acct, NonConfigurableConstants.ACCOUNT_STATUS_SUSPENDED);
	}
	public boolean hasFutureReactivate(String custNo, String subAcctNo){
		AmtbAccount acct = getAccount(custNo, subAcctNo);
		return hasFutureStatus(acct, NonConfigurableConstants.ACCOUNT_STATUS_ACTIVE);
	}
	public boolean hasFutureTerminate(String custNo, String subAcctNo){
		AmtbAccount acct = getAccount(custNo, subAcctNo);
		return hasFutureStatus(acct, NonConfigurableConstants.ACCOUNT_STATUS_TERMINATED);
	}
	public boolean hasFutureSuspend(String custNo, String subAcctNo){
		AmtbAccount acct = getAccount(custNo, subAcctNo);
		return hasFutureStatus(acct, NonConfigurableConstants.ACCOUNT_STATUS_SUSPENDED);
	}
	private boolean hasFutureStatus(AmtbAccount acct, String status){
		for(AmtbAcctStatus acctStatus : acct.getAmtbAcctStatuses()){
			if(acctStatus.getEffectiveDt().after(DateUtil.getCurrentDate())){
				if(acctStatus.getAcctStatus().equals(status)){
					return true;
				}
			}
		}
		return false;
	}
	public boolean hasTempCreditLimit(String custNo, String parentCode, String code){
		AmtbAccount acct = getAccount(custNo, parentCode, code);
		List<AmtbAcctCredLimit> creditLimits = this.daoHelper.getAccountDao().getCreditLimits(acct.getAccountNo());
		if(getCurrentTempCreditLimit(creditLimits)!=null){
			return true;
		}else if(getFutureTempCreditLimit(creditLimits)!=null){
			return true;
		}else{
			return false;
		}
	}
	public boolean hasTempChildFutureCreditLimit(String custNo, String parentCode, String code){
		AmtbAccount acct = new AmtbAccount();
			
		acct = getAccount(custNo, parentCode, code);
			
		List<AmtbAcctCredLimit> creditLimits = this.daoHelper.getAccountDao().getChildCreditLimits(acct.getAccountNo());
		if(getCurrentTempCreditLimit(creditLimits)!=null){
			return true;
		}else if(getFutureTempCreditLimit(creditLimits)!=null){
			return true;
		}else{
			List<AmtbAcctCredLimit> creditLimitsChild = this.daoHelper.getAccountDao().getChildChildCreditLimits(acct.getAccountNo());
			if(creditLimitsChild != null ){
				if(creditLimitsChild.size() > 0 ) {
					if(getCurrentTempCreditLimit(creditLimitsChild)!=null){
						return true;
					}
					if(getFutureTempCreditLimit(creditLimitsChild)!=null)
						return true;
					else
						return false;
				}
			}
			
			return false;
		}
	}
	public void clearFutureChildCreditLimit(String custNo, String parentCode, String code, String userId){
		AmtbAccount acct = getAccount(custNo, parentCode, code);
//		System.out.println("test acct no to use > "+acct.getAccountNo());
		List<AmtbAcctCredLimit> futureChildCreditLimits = this.daoHelper.getAccountDao().getChildCreditLimits(acct.getAccountNo());
		if(futureChildCreditLimits != null) {
			for(AmtbAcctCredLimit creditLimit : futureChildCreditLimits) {
				if(creditLimit.getCreditLimitType().equals(NonConfigurableConstants.CREDIT_LIMIT_TEMPORARY) 
						&& creditLimit.getEffectiveDtFrom().after(DateUtil.getCurrentTimestamp())){
//					System.out.println("del > "+creditLimit.getAcctCredLimitNo()+ " version > "+creditLimit.getVersion());
					this.delete(creditLimit);
				}
				else if(creditLimit.getCreditLimitType().equals(NonConfigurableConstants.CREDIT_LIMIT_TEMPORARY)
						&& creditLimit.getEffectiveDtFrom().before(DateUtil.getCurrentTimestamp()) )
							fixEffectiveTempLimit(creditLimit, userId);
			}
		}
		List<AmtbAcctCredLimit> futureChildChildCreditLimits = this.daoHelper.getAccountDao().getChildChildCreditLimits(acct.getAccountNo());
		if(futureChildChildCreditLimits != null) {
			for(AmtbAcctCredLimit creditLimit2 : futureChildChildCreditLimits) {
				if(creditLimit2.getCreditLimitType().equals(NonConfigurableConstants.CREDIT_LIMIT_TEMPORARY) && creditLimit2.getEffectiveDtFrom().after(DateUtil.getCurrentTimestamp())){
//					System.out.println("del2 > "+creditLimit2.getAcctCredLimitNo()+ " version > "+creditLimit2.getVersion());
					this.delete(creditLimit2);
				}
				else if(creditLimit2.getCreditLimitType().equals(NonConfigurableConstants.CREDIT_LIMIT_TEMPORARY)
						&& creditLimit2.getEffectiveDtFrom().before(DateUtil.getCurrentTimestamp()) )
					fixEffectiveTempLimit(creditLimit2, userId);
			}
		}
	}
	public boolean hasTempCreditLimit(String custNo, String subAcctNo){
		AmtbAccount acct = getAccount(custNo, subAcctNo);
		List<AmtbAcctCredLimit> creditLimits = this.daoHelper.getAccountDao().getCreditLimits(acct.getAccountNo());
		if(getCurrentTempCreditLimit(creditLimits)!=null){
			return true;
		}else if(getFutureTempCreditLimit(creditLimits)!=null){
			return true;
		}else{
			return false;
		}
	}
	public boolean checkParentCreditLimitRange(String custNo, String code, Date fromDate, Date toDate, BigDecimal limit){
		AmtbAccount account = new AmtbAccount();
		
		if(null == code) 
			account = this.daoHelper.getAccountDao().getAccountByCustNo(custNo);
		else 
			account = getAccount(custNo, null, code);
		
		List<AmtbAcctCredLimit> listCreditLimits = this.daoHelper.getAccountDao().getListCreditLimits(account.getAccountNo());
//		System.out.println("account > "+account.getAccountNo());
		//Check through all Limit.
		if(listCreditLimits.size() > 0) {
			List<AmtbAcctCredLimit> toCheck = new ArrayList<AmtbAcctCredLimit>();
			Date checkDate_5 = null;
			
			System.out.println("size > "+listCreditLimits.size());
			//Scenario 1) 
			//if only 1 limit, add it to the toCheck obj..
			// eg :  05-07 to 20-07
			//    perm1: 01-01 to null
			// means only 1 effective perm limit..
			if(listCreditLimits.size() == 1) {
				toCheck.add(listCreditLimits.get(0));
//				System.out.println("A");
			} else {
				
				//Scenario 2) 
				//if got more than 1,  check see which one within the from & todate then add it to toCheck obj.
				// means as long as effective date from   is inside from & todate .. yes
				for(AmtbAcctCredLimit creLimit : listCreditLimits) {

					if(creLimit.getEffectiveDtFrom() != null)
						creLimit.setEffectiveDtFrom(resetDate(creLimit.getEffectiveDtFrom()));
					if(creLimit.getEffectiveDtTo() != null)
						creLimit.setEffectiveDtTo(resetDate(creLimit.getEffectiveDtTo()));
					
//					System.out.println("creLimit.get eff > "+creLimit.getEffectiveDtFrom() + " den fromDate > "+fromDate);

					
					//temp    20 to 25 chosen
					//        temp  19-30 start date,   
					//          19 < 25      &&  30 > 20
					if(null != creLimit.getEffectiveDtTo()) {
						
//						System.out.println("fark ni creLimit.getEffectiveDtFrom().compareTo(toDate) > "+creLimit.getEffectiveDtFrom().compareTo(toDate));
//						System.out.println("fark ni creLimit.getEffectiveDtTo().compareTo(fromDate) > "+creLimit.getEffectiveDtTo().compareTo(fromDate));
//						
//						System.out.println("fark 22 creLimit.getEffectiveDtFrom().compareTo(fromDate) > "+creLimit.getEffectiveDtFrom().compareTo(fromDate));
//						System.out.println("fark 3 . creLimit.getEffectiveDtTo().compareTo(toDate) > "+creLimit.getEffectiveDtTo().compareTo(toDate));
						
						if(creLimit.getEffectiveDtFrom().compareTo(toDate) < 0	&& creLimit.getEffectiveDtTo().compareTo(fromDate) >= 0) {
							toCheck.add(creLimit);
//							System.out.println("B.a1");
						}
						else if(creLimit.getEffectiveDtFrom().compareTo(fromDate) <= 0 && creLimit.getEffectiveDtTo().compareTo(toDate) >=0) {
							toCheck.add(creLimit);
//							System.out.println("B.a2");
						}
					}
					//Perm    must be within the date
					//      18th to 25th chosen
					//    if perm 24th, must be in middle.
					//                 24              > 18                      &&         24           < 25
					else {
						if(creLimit.getEffectiveDtFrom().compareTo(fromDate) >= 0 && creLimit.getEffectiveDtFrom().compareTo(toDate) < 0) {
							toCheck.add(creLimit);
//							System.out.println("B.b1");
						}
					}
				}
				
				//Scenario 3) 
				// if entered start date not exactly on the day of the temp start date, 
				// eg :  05-07 to 20-07
				//    perm1: 01-01 to null
				//    temp1: 07-07 to 15-07
				//    temp2: 19-07 to 20-07
				//                      then 05/07 to 07/07 ?  take prev perm lo..
				// take the nearest permanent date earlier than start date.
				boolean takeEarliestPerm = true;
				boolean takeScenario3 = false;
				
				if(!toCheck.isEmpty()) {
					AmtbAcctCredLimit checkEarliest = toCheck.get( toCheck.size() - 1);
//					System.out.println("hey hey > "+checkEarliest.getEffectiveDtFrom());
//					System.out.println("hey 2 > "+fromDate);
//					System.out.println("den > "+getZeroTimeDate(checkEarliest.getEffectiveDtFrom()).compareTo(fromDate));
					//     7 < 5
					if(getZeroTimeDate(checkEarliest.getEffectiveDtFrom()).compareTo(fromDate) > 0) {
						takeEarliestPerm = false;
						takeScenario3 = true;
//						System.out.println("C");
					}
				}
				//Scenario 4)
				// if in middle got a gap between new.from & prev.toDate.. (temp only)
				// eg :  05-07 to 20-07
				//    temp1: 05-07 to 15-07
				//    temp2: 19-07 to 20-07
				//								15-07 to 19-07 is wat?
				// take nearest perm date 
				List<AmtbAcctCredLimit> scenario4Dates = new ArrayList<AmtbAcctCredLimit>();
				if(!takeEarliestPerm) {
//					System.out.println("check 4 ");
					Date prevRecordFromDate = null;
					for(AmtbAcctCredLimit checkGap : toCheck) {
						
						if(checkGap.getCreditLimitType().equalsIgnoreCase("T")) {
							if(null != prevRecordFromDate) {
//								int compareDay = calculateDays(checkGap.getEffectiveDtTo(), prevRecordFromDate);
								//if got date bigger than 1 day inbetween, must take earliestperm.
								if(calculateDays(checkGap.getEffectiveDtTo(), prevRecordFromDate) > 1) {
									takeEarliestPerm = true;
//									System.out.println("D");
									scenario4Dates.add(checkGap);
								}
							}
							prevRecordFromDate = getZeroTimeDate(checkGap.getEffectiveDtFrom());
						}
					}
				}

				//Scenario 5)
				// if 3 & 4 also no have, check if at end got a 'hole'
				// eg :  05-07 to 20-07
				//    temp1: 05-07 to 15-07
				//    temp2: 18-07 to 19-07
				//								19-07 to 20-07 is wat?
				// if at end got more than 1 day of empty hole, must check takeEarliestPerm + add the date to check earliest perm date From
				
				// Scenario 5b) 
				// if at end is Permanent , search for the previous TEMP, & save the prevEffectiveDtFrom. & compare like scenario4.
//				System.out.println("takeearlyprem > "+takeEarliestPerm);
				if(!takeEarliestPerm || (!toCheck.isEmpty() && takeEarliestPerm)) {
					Date prevRecordFromDate = null;
					for(AmtbAcctCredLimit checkEndGap : toCheck) {
						
						if(prevRecordFromDate != null && checkEndGap.getCreditLimitType().equals("T")) {
							if(calculateDays(checkEndGap.getEffectiveDtTo(), prevRecordFromDate) > 1) {
								takeEarliestPerm = true;
//								System.out.println("E2");
								checkDate_5 = checkEndGap.getEffectiveDtFrom();
								break;
							}
						}
						else if(checkEndGap.getCreditLimitType().equals("T")) {
//							System.out.println("checkend > "+checkEndGap.getEffectiveDtTo());
//							System.out.println("todate > "+toDate);
//							System.out.println("checkoamsod > "+calculateDays(toDate,checkEndGap.getEffectiveDtTo()));
							if( calculateDays(toDate, checkEndGap.getEffectiveDtTo()) < 0) {
								takeEarliestPerm = true;
//								System.out.println("E1");
								checkDate_5 = checkEndGap.getEffectiveDtFrom();
								break;
							}
							else {
//								System.out.println("DAFUG");
								break;
							}
						}
						else
							prevRecordFromDate = getZeroTimeDate(checkEndGap.getEffectiveDtFrom());
					}
				}
				
				//Scenario 6)
				// if the takeEarliestPerm is true OR  toCheck is empty..
				// take the nearest Perm, latest record that is smaller than fromDate & toDate, 
				// and add it to toCheck obj.
				if(takeEarliestPerm || takeScenario3) {
//					System.out.println("scanrio3 > "+takeScenario3);
					//6.1  From Scenario 1, 3
					// take earlier prem date if there no other date within range.
					if( (null == toCheck || toCheck.size() == 0) || (takeScenario3)) {
						for(AmtbAcctCredLimit creLimit : listCreditLimits) {
							if(creLimit.getEffectiveDtFrom().compareTo(fromDate) < 0 && creLimit.getEffectiveDtFrom().compareTo(toDate) < 0) {
								if(creLimit.getCreditLimitType().equalsIgnoreCase("P")) {
									toCheck.add(creLimit);
//									System.out.println("F1");
									break;
								}
							}
						}
					}
					
					// 6.2 From Scenario 4
					// take earliest perm date that is from the date  from 6.2 
					if (null != scenario4Dates) {
						for(AmtbAcctCredLimit scenario4Date : scenario4Dates) {
							AmtbAcctCredLimit nearestCreditLimits = this.daoHelper.getAccountDao().getNearestCreditLimits(account.getAccountNo(), scenario4Date.getEffectiveDtFrom());
							if(null != nearestCreditLimits) {
								toCheck.add(nearestCreditLimits);
//								System.out.println("F3");
							}
						}
					}
					// 6.3 From Scenario 5
					// take earliest perm date that is from the date save in checkDate_E from 6.2 
					if (null != checkDate_5) {
						AmtbAcctCredLimit nearestCreditLimits = this.daoHelper.getAccountDao().getNearestCreditLimits(account.getAccountNo(), checkDate_5);
						if(null != nearestCreditLimits) {
							toCheck.add(nearestCreditLimits);
//							System.out.println("F4");
						}
					}
				}
			} // else
			
			
			//23/6/2017 new Scenario 7
			// as long as got inside a Temporary, will just pass it.
			for(AmtbAcctCredLimit eachLimit : toCheck) {
				if(eachLimit.getCreditLimitType().equalsIgnoreCase("T"))
				{
					if(eachLimit.getEffectiveDtFrom().compareTo(fromDate) >= 0 && eachLimit.getEffectiveDtTo().compareTo(toDate) <= 0)
					{
						if(eachLimit.getNewCreditLimit().compareTo(limit) >= 0)
							return false;
					}
				}
			}
			
			
//			System.out.println("toCheck size > "+toCheck.size());
//			System.out.println("toCheck > "+toCheck);
			//check every record added to toCheck obj
			//if any one limit is bigger than in the list, immediately throw error ..
			for(AmtbAcctCredLimit eachLimit : toCheck) {
//				System.out.println("check thru > + eachLimit "+eachLimit.getNewCreditLimit()+ " denn > "+limit);
				if(eachLimit.getNewCreditLimit().compareTo(limit) < 0)
					return true;
			}
		} //size > 0
		
//		System.out.println("by right shuld pass la");
		//false = ok to increase limit,   true = cannot
		return false;
//		return true;
	}
	public static Date getZeroTimeDate(Date fecha) {
		    Date res = fecha;
		    Calendar calendar = Calendar.getInstance();

		    calendar.setTime( fecha );
		    calendar.set(Calendar.HOUR_OF_DAY, 0);
		    calendar.set(Calendar.MINUTE, 0);
		    calendar.set(Calendar.SECOND, 0);
		    calendar.set(Calendar.MILLISECOND, 0);

		    res = calendar.getTime();

		    return res;
	}
	public int calculateDays(Date startDate, Date endDate)
	{
	        Calendar cal3 = Calendar.getInstance();
	        cal3.setTime(startDate);
	        Calendar cal4 = Calendar.getInstance();
	        cal4.setTime(endDate);
	        return daysBetween(cal3, cal4);
	}
	public int daysBetween(Calendar startDate, Calendar endDate) {
        Calendar date = (Calendar) startDate.clone();
        int daysBetween = 0;
        
        if(date.before(endDate)) {
	        while (date.before(endDate)) {
	            date.add(Calendar.DAY_OF_MONTH, 1);
	            daysBetween++;
	        }
        }
        else if(date.after(endDate)) {
        	while(date.after(endDate)) {
        		date.add(Calendar.DAY_OF_MONTH, -1);
        		daysBetween--;
        	}
        }
        return daysBetween;
    }

	public AmtbAccount getAccount(String accountNo){
		return this.daoHelper.getAccountDao().getAccount(accountNo);
	}
	public AmtbAccount getAccountByCustNoAndCode(String custNo, String code, String level){
		return this.daoHelper.getAccountDao().getAccountByCustNoAndCode(custNo, code, level);
	}	
	public AmtbAccount getAccountByCustNoAndCodeAndCode(String custNo, String code, String level, String code2){
		return this.daoHelper.getAccountDao().getAccountByCustNoAndCodeAndCode(custNo, code, level, code2);
	}
	public AmtbAccount getAccountByCustNo(String custNo){
		return this.daoHelper.getAccountDao().getAccountsByCustNo(custNo);
	}
	public AmtbAccount getRawAccountMain(String originalCustomerNo) {
		// 1. get Account
				AmtbAccount account = null;
				String custNo = "";
				String divCode = "";
				String deptCode = "";
				// depending on size,
				// if size 5 or 6 = custno
				// if size 9 or 10 = custno + div
				// if size 13 or 14 = custno + div + dept
				// String custNo = row.getCell(0).getStringCellValue();

				if (originalCustomerNo.length() == 5 || originalCustomerNo.length() == 9 || originalCustomerNo.length() == 13)
					custNo = originalCustomerNo.substring(0, 5);
				else if (originalCustomerNo.length() == 6 || originalCustomerNo.length() == 10
						|| originalCustomerNo.length() == 14)
					custNo = originalCustomerNo.substring(0, 6);
				else if (originalCustomerNo.length() == 3 || originalCustomerNo.length() == 7
						|| originalCustomerNo.length() == 11)
					custNo = originalCustomerNo.substring(0, 3);
				else
					logger.info("Account not found");

				if (originalCustomerNo.length() == 9 || originalCustomerNo.length() == 13)
					divCode = originalCustomerNo.substring(5, 9);
				else if (originalCustomerNo.length() == 10 || originalCustomerNo.length() == 14)
					divCode = originalCustomerNo.substring(6, 10);
				else if (originalCustomerNo.length() == 7 || originalCustomerNo.length() == 12)
					divCode = originalCustomerNo.substring(3, 7);

				if (originalCustomerNo.length() == 13)
					deptCode = originalCustomerNo.substring(9, 13);
				else if (originalCustomerNo.length() == 14)
					deptCode = originalCustomerNo.substring(10, 14);
				else if (originalCustomerNo.length() == 11)
					deptCode = originalCustomerNo.substring(7, 11);

				logger.info("CustNo > " + custNo + " || Div Code > " + divCode + "  || Dept Code > " + deptCode);

				if (!deptCode.trim().equals("")) {
					account = (AmtbAccount) this.businessHelper.getAccountBusiness().getRawAccount(custNo, divCode, deptCode,
							"DEPT");
				} else if (!divCode.trim().equals("")) {
					account = (AmtbAccount) this.businessHelper.getAccountBusiness().getRawAccount(custNo, divCode, "", "DIV");
				} else {
					account = (AmtbAccount) this.businessHelper.getAccountBusiness().getRawAccount(custNo, null, null, "");
				}

				if (account == null)
					logger.info("Account not found");
				return account;
	}
	public AmtbAccount getRawAccount(String custNo, String divCode, String deptCode, String type) {
		return this.daoHelper.getAccountDao().getRawAccount(custNo, divCode, deptCode , type);
	}
	private AmtbAccount getAccount(String custNo, String parentCode, String code){
		AmtbAccount acct;
		if(parentCode!=null && parentCode.trim().length()!=0){// department
			acct = this.daoHelper.getAccountDao().getAccount(custNo, 2, parentCode, code);
		}else if(code!=null && code.trim().length()!=0){// division
			acct = this.daoHelper.getAccountDao().getAccount(custNo, 1, null, code);
		}else{// corporate
			acct = this.daoHelper.getAccountDao().getAccount(custNo, 0, null, null);
		}
		return acct;
	}
	private AmtbAccount getAccount(String custNo, String subAcctNo){
		AmtbAccount acct;
		if(subAcctNo!=null && subAcctNo.trim().length()!=0){// sub applicant
			acct = this.daoHelper.getAccountDao().getAccount(Integer.parseInt(subAcctNo));
		}else{// applicant
			acct = this.daoHelper.getAccountDao().getAccount(custNo, 0, null, null);
		}
		return acct;
	}
	public boolean hasPendingCreditReview(String custNo){
		AmtbAccount acct;
		acct = this.daoHelper.getAccountDao().getAccountWithCreditDetails(custNo, 0, null, null);
		for(AmtbCredRevReq request : acct.getAmtbCredRevReqs()){
			TreeSet<AmtbCredRevReqFlow> sortedFlows = new TreeSet<AmtbCredRevReqFlow>(new Comparator<AmtbCredRevReqFlow>(){
				public int compare(AmtbCredRevReqFlow o1, AmtbCredRevReqFlow o2) {
					return o1.getFlowDt().compareTo(o2.getFlowDt());
				}
			});
			sortedFlows.addAll(request.getAmtbCredRevReqFlows());
			if(!sortedFlows.isEmpty()){
				if(sortedFlows.last().getToStatus().equals(NonConfigurableConstants.CREDIT_REVIEW_REQUEST_STATUS_PENDING)){
					return true;
				}
			}
		}
		return false;
	}
	public boolean hasPendingPermanentCreditReview(String custNo){
		AmtbAccount acct;
		acct = this.daoHelper.getAccountDao().getAccountWithCreditDetails(custNo, 0, null, null);
		for(AmtbCredRevReq request : acct.getAmtbCredRevReqs()){
			TreeSet<AmtbCredRevReqFlow> sortedFlows = new TreeSet<AmtbCredRevReqFlow>(new Comparator<AmtbCredRevReqFlow>(){
				public int compare(AmtbCredRevReqFlow o1, AmtbCredRevReqFlow o2) {
					return o1.getFlowDt().compareTo(o2.getFlowDt());
				}
			});
			sortedFlows.addAll(request.getAmtbCredRevReqFlows());
			if(!sortedFlows.isEmpty()){
				if(request.getCreditReviewType().equals(NonConfigurableConstants.CREDIT_LIMIT_PERMANENT) && sortedFlows.last().getToStatus().equals(NonConfigurableConstants.CREDIT_REVIEW_REQUEST_STATUS_PENDING)){
					return true;
				}
			}
		}
		return false;
	}
	public void creditReviewAcct(String custNo, String subAcctNo, BigDecimal newCreditLimit, String type, Date effectiveDateFrom, Date effectiveDateTo, String remarks, String userId) throws Exception{
		AmtbAccount acct = getAccount(custNo, subAcctNo);
		creditReviewAcct(acct, newCreditLimit, type, effectiveDateFrom, effectiveDateTo, remarks, userId);
	}
	public void creditReviewAcct(String custNo, String parentCode, String code, BigDecimal newCreditLimit, String type, Date effectiveDateFrom, Date effectiveDateTo, String remarks, String userId) throws Exception{
		AmtbAccount acct = getAccount(custNo, parentCode, code);
		creditReviewAcct(acct, newCreditLimit, type, effectiveDateFrom, effectiveDateTo, remarks, userId);
	}
	private void creditReviewAcct(AmtbAccount acct, BigDecimal newCreditLimit, String type, Date effectiveDateFrom, Date effectiveDateTo, String remarks, String userId) throws Exception{
		if(acct.getCustNo()!=null){//corporate/personal. need to rise request
			AmtbCredRevReq request = new AmtbCredRevReq();
			request.setAmtbAccount(acct);
			request.setCreditReviewType(type);
			request.setEffectiveDtFrom(DateUtil.convertDateToTimestamp(effectiveDateFrom));
			if(effectiveDateTo!=null){
				Calendar effectiveCalendarTo = Calendar.getInstance();
				effectiveCalendarTo.setTimeInMillis(effectiveDateTo.getTime());
				request.setEffectiveDtTo(DateUtil.convertDateToTimestamp(DateUtil.convertTo2359Hours(effectiveCalendarTo)));
			}
			request.setNewCreditLimit(newCreditLimit);
			request.setRemarks(remarks);
			AmtbCredRevReqFlow flow = new AmtbCredRevReqFlow();
			flow.setAmtbCredRevReq(request);
			request.getAmtbCredRevReqFlows().add(flow);
			flow.setFlowDt(DateUtil.getCurrentTimestamp());
			flow.setFromStatus(NonConfigurableConstants.CREDIT_REVIEW_REQUEST_STATUS_NEW);
			flow.setToStatus(NonConfigurableConstants.CREDIT_REVIEW_REQUEST_STATUS_PENDING);
			flow.setRemarks(remarks);
			SatbUser requester = this.daoHelper.getUserDao().getUserWithRoles(CommonWindow.getUserId());
			flow.setSatbUser(requester);
			this.save(request);
			// sending email to approver
			List<String> toEmails = new ArrayList<String>();
			List<String> ccEmails = new ArrayList<String>();
			List<SatbUser> approvers = this.daoHelper.getUserDao().searchUser(Uri.APPROVE_CREDIT_REVIEW);
			StringBuffer approverNames = new StringBuffer();
			for(SatbUser approver : approvers){
				toEmails.add(approver.getEmail());
				approverNames.append(approver.getName() + ",");
			}
			approvers = this.daoHelper.getUserDao().searchUser(Uri.APPROVE_CREDIT_REVIEW);
			for(SatbUser approver : approvers){
				if(!toEmails.contains(approver.getEmail())){
					toEmails.add(approver.getEmail());
					approverNames.append(approver.getName() + ",");
				}
			}
			approverNames.delete(approverNames.length()-1, approverNames.length());
			//ccEmails.add(requester.getEmail());
			EmailUtil.sendEmail(toEmails.toArray(new String[]{}), ccEmails.toArray(new String[]{}),
					ConfigurableConstants.getEmailText(ConfigurableConstants.EMAIL_TYPE_CREDIT_REQUEST_SUBMIT, ConfigurableConstants.EMAIL_SUBJECT),
					ConfigurableConstants.getEmailText(ConfigurableConstants.EMAIL_TYPE_CREDIT_REQUEST_SUBMIT, ConfigurableConstants.EMAIL_CONTENT)
					.replaceAll("#userName#", approverNames.toString())
					.replaceAll("#custNo#", acct.getCustNo())
					.replaceAll("#submiter#", requester.getName())
					.replaceAll("#acctName#", acct.getAccountName()));
		}else{// division/dept/sub
			AmtbAcctCredLimit creditLimit;
			// boolean to check whether it is a new status
			boolean newStatus = false;
			// getting all credit limit of an account
			List<AmtbAcctCredLimit> creditLimits = this.daoHelper.getAccountDao().getCreditLimits(acct.getAccountNo());
			BigDecimal endTempCreditLimit = null;
			// if the request is for temporary limit
			if(type.equals(NonConfigurableConstants.CREDIT_LIMIT_TEMPORARY)){
				// getting the current temp credit that is in effective if any
				creditLimit = getCurrentTempCreditLimit(creditLimits);
				// if no temp credit limit found
				if(creditLimit == null){
					// getting the future credit limit
					creditLimit = getFutureTempCreditLimit(creditLimits);
					// if not found, create a new credit limit
					if(creditLimit == null){
						newStatus = true;
						creditLimit = new AmtbAcctCredLimit();
					}
				}else{// if current credit limit found
					// end the current credit limit and create a new credit limit
					endTempCreditLimit = creditLimit.getNewCreditLimit();
					creditLimit.setEffectiveDtTo(DateUtil.getCurrentTimestamp());
					this.update(creditLimit);
					newStatus = true;
					creditLimit = new AmtbAcctCredLimit();
				}
			}else{
				newStatus = true;
				creditLimit = new AmtbAcctCredLimit();
			}
			if(newStatus){
				creditLimit.setAmtbAccount(acct);
				creditLimit.setCreditLimitType(type);
			}
			creditLimit.setAmtbAccount(acct);
			creditLimit.setCreditLimitType(type);
			creditLimit.setEffectiveDtFrom(DateUtil.convertDateToTimestamp(effectiveDateFrom));
			creditLimit.setEffectiveDtTo(DateUtil.convertDateToTimestamp(effectiveDateTo));
			creditLimit.setNewCreditLimit(newCreditLimit);
			creditLimit.setRemarks(remarks);
			creditLimit.setSatbUser(this.daoHelper.getUserDao().getUserWithRoles(CommonWindow.getUserId()));
			this.save(creditLimit);
			if(DateUtil.isToday(effectiveDateFrom)){
				BigDecimal difference = null;
				BigDecimal currentPerm = acct.getCreditLimit();
				BigDecimal currentTemp = acct.getTempCreditLimit()!=null ? acct.getTempCreditLimit() : new BigDecimal(0);
				BigDecimal nextPerm = type.equals(NonConfigurableConstants.CREDIT_LIMIT_PERMANENT) ? newCreditLimit : currentPerm;
				BigDecimal nextTemp = type.equals(NonConfigurableConstants.CREDIT_LIMIT_TEMPORARY) ? newCreditLimit : currentTemp;
				BigDecimal currentBigger = null, nextBigger = null;
				if(currentPerm.doubleValue() < currentTemp.doubleValue()){
					currentBigger = currentTemp;
				}else{
					currentBigger = currentPerm;
				}
				if(nextPerm.doubleValue() < nextTemp.doubleValue()){
					nextBigger = nextTemp;
				}else{
					nextBigger = nextPerm;
				}
				logger.info("test currentPerm = " + currentPerm);
				logger.info("test currentTemp = " + currentTemp);
				logger.info("test nextPerm = " + nextPerm);
				logger.info("test nextTemp = " + nextTemp);
				logger.info("test currentBigger = " + currentBigger);
				logger.info("test nextBigger = " + nextBigger);
				difference = nextBigger.subtract(currentBigger);
				logger.info("test difference = " + difference);
				if(type.equals(NonConfigurableConstants.CREDIT_LIMIT_TEMPORARY)){
					acct.setTempCreditLimit(newCreditLimit);
				}else{
					acct.setCreditLimit(newCreditLimit);
				}
//				if(type.equals(NonConfigurableConstants.CREDIT_LIMIT_PERMANENT)){
//					if(acct.getTempCreditLimit()!=null){
//						if(acct.getTempCreditLimit().doubleValue() < acct.getCreditLimit().doubleValue()){ // temp is lower than perm
//							if(newCreditLimit.doubleValue() < acct.getTempCreditLimit().doubleValue()){// perm will fall below temp
//								difference = acct.getTempCreditLimit().subtract(acct.getCreditLimit());
//							}else{// perm will still be above temp
//								difference = newCreditLimit.subtract(acct.getCreditLimit());
//							}
//						}else{// temp is higher than perm
//							if(newCreditLimit.doubleValue() < acct.getTempCreditLimit().doubleValue()){//perm will still be below temp
//								difference = new BigDecimal(0);
//							}else{// perm will go above temp
//								difference = newCreditLimit.subtract(acct.getTempCreditLimit());
//							}
//						}
//					}else{
//						difference = newCreditLimit.subtract(acct.getCreditLimit());
//					}
//					acct.setCreditLimit(newCreditLimit);
//				}else{
//					difference = newCreditLimit.subtract(acct.getCreditLimit());
//					acct.setTempCreditLimit(newCreditLimit);
//				}
				acct.setCreditBalance(acct.getCreditBalance().add(difference));
				this.update(acct);
				// updating credit balance to AS
				API.updateAccount(API.formulateAccountId(acct), acct.getCreditBalance().toString(), API.formulateParentAccountId(acct), userId, API.ASYNCHRONOUS);
			}
		}
		this.update(acct, userId);
	}
	public void unsubscribeProductType(AmtbAccount acct, PmtbProductType pmtbProductType, AmtbSubscTo subscription) throws CniiInterfaceException{
//		AmtbAccount corpAcct = this.daoHelper.getAccountDao().getAccount(custNo, NonConfigurableConstants.CORPORATE_LEVEL, null, null);
		List<AmtbSubscTo> deleted = new ArrayList<AmtbSubscTo>();
		deleted.add(subscription);
		
		ArrayList<IttbCniiAcctReq> cniiList = new ArrayList<IttbCniiAcctReq>();
		List<String> cardLessProductList = new ArrayList<String>();
		List<PmtbProductType> cardlessProducts = this.businessHelper.getProductTypeBusiness().getCardlessProductType();
		for(PmtbProductType cardlessProduct : cardlessProducts) {
			cardLessProductList.add(cardlessProduct.getProductTypeId().trim());
		}
		Map<String, String> prop = (Map)SpringUtil.getBean("webserviceProperties");
//		if(pmtbProductType.getProductTypeId().contains(NonConfigurableConstants.PREMIER_SERVICE) && NonConfigurableConstants.BOOLEAN_NO.equals((String) prop.get("ws.cnii.disable"))){
		if(cardLessProductList.contains(pmtbProductType.getProductTypeId()) && NonConfigurableConstants.BOOLEAN_NO.equals((String) prop.get("ws.cnii.disable"))){
			AmtbAcctStatus currentStatus = getCurrentStatus(acct.getAmtbAcctStatuses());
			if(currentStatus!=null && !currentStatus.getAcctStatus().equals(NonConfigurableConstants.ACCOUNT_STATUS_PENDING_ACTIVATION)){
				IttbCniiAcctReq cniiRequest = new IttbCniiAcctReq();
				cniiRequest.setReqId(DateUtil.convertDateToStr(DateUtil.getCurrentDate(), DateUtil.GLOBAL_CNII_SEQ_FORMAT) + this.daoHelper.getAccountDao().getNextSequenceNo(Sequence.CNII_REQ_NO_SEQUENCE));
				cniiRequest.setReqDate(DateUtil.getCurrentTimestamp());
				cniiRequest.setEventType(NonConfigurableConstants.CNII_REQUEST_EVENT_TYPE_NEW);
				cniiRequest.setReqStatus(NonConfigurableConstants.CNII_REQUEST_STATUS_PENDING);
				cniiRequest.setTerminateDt(DateUtil.getCurrentDate());
				cniiRequest.setValidFlg(NonConfigurableConstants.BOOLEAN_YES);
				cniiRequest.setUpdatedBy(NonConfigurableConstants.CNII_REQUEST_UPDATED_BY_IBS);
				cniiRequest.setAccountId(acct.getCustNo());
				cniiRequest.setAccountCd(acct.getCustNo());
				cniiRequest.setAccountNm(acct.getAccountName());
				cniiRequest.setUpdatedDt(DateUtil.getCurrentTimestamp());
				//this.save(cniiRequest);
				
				cniiList.add(cniiRequest);
				//IBSCNIIUpdateAcctClient.send(cniiList);
			}
		}
		if(!deleted.isEmpty()){
			for(AmtbSubscTo delete : deleted){
				this.delete(delete);
			}
		}
		
		// now removing all product subscriptions that are in children of account
		List<AmtbAccount> childrenAccts = this.daoHelper.getAccountDao().getChildrenAccountsWithSubscriptionsAndMainContacts(acct.getAccountNo());
		for(AmtbAccount childAcct : childrenAccts){
			List<AmtbSubscTo> childDeleted = new ArrayList<AmtbSubscTo>();
			for(AmtbSubscTo childSubscribed : childAcct.getAmtbSubscTos()){
				if(pmtbProductType.getProductTypeId().contains(childSubscribed.getComp_id().getPmtbProductType().getProductTypeId())){
					childDeleted.add(childSubscribed);
//					if(pmtbProductType.getProductTypeId().contains(NonConfigurableConstants.PREMIER_SERVICE) && NonConfigurableConstants.BOOLEAN_NO.equals((String) prop.get("ws.cnii.disable"))){
					if(cardLessProductList.contains(pmtbProductType.getProductTypeId()) && NonConfigurableConstants.BOOLEAN_NO.equals((String) prop.get("ws.cnii.disable"))){
						AmtbAcctStatus parentStatus = getCurrentStatus(childAcct.getAmtbAccount().getAmtbAcctStatuses());
						if(parentStatus!=null && !parentStatus.getAcctStatus().equals(NonConfigurableConstants.ACCOUNT_STATUS_PENDING_ACTIVATION)){
							IttbCniiAcctReq cniiRequest = new IttbCniiAcctReq();
							cniiRequest.setReqId(DateUtil.convertDateToStr(DateUtil.getCurrentDate(), DateUtil.GLOBAL_CNII_SEQ_FORMAT) + this.daoHelper.getAccountDao().getNextSequenceNo(Sequence.CNII_REQ_NO_SEQUENCE));
							cniiRequest.setReqDate(DateUtil.getCurrentTimestamp());
							cniiRequest.setEventType(NonConfigurableConstants.CNII_REQUEST_EVENT_TYPE_NEW);
							cniiRequest.setReqStatus(NonConfigurableConstants.CNII_REQUEST_STATUS_PENDING);
							cniiRequest.setTerminateDt(DateUtil.getCurrentDate());
							cniiRequest.setValidFlg(NonConfigurableConstants.BOOLEAN_YES);
							cniiRequest.setUpdatedBy(NonConfigurableConstants.CNII_REQUEST_UPDATED_BY_IBS);
							cniiRequest.setAccountId(acct.getCustNo()+childAcct.getCode());
							cniiRequest.setAccountCd(childAcct.getCode());
							cniiRequest.setParentId(acct.getCustNo());
							cniiRequest.setAccountNm(childAcct.getAccountName());
							cniiRequest.setUpdatedDt(DateUtil.getCurrentTimestamp());
							//this.save(cniiRequest);
							//ArrayList<IttbCniiAcctReq> cniiList = new ArrayList<IttbCniiAcctReq>();
							cniiList.add(cniiRequest);
							//IBSCNIIUpdateAcctClient.send(cniiList);
						}
					}
					break;
				}
			}
			childAcct.getAmtbSubscTos().clear();
			for(AmtbSubscTo childDelete : childDeleted){
				this.delete(childDelete);
			}
			
			List<AmtbAccount> grandAccts = this.daoHelper.getAccountDao().getChildrenAccountsWithSubscriptionsAndMainContacts(childAcct.getAccountNo());
			if(grandAccts.size()!=0){
				for(AmtbAccount grandAcct : grandAccts){
					List<AmtbSubscTo> grandDeleted = new ArrayList<AmtbSubscTo>();
					for(AmtbSubscTo grandSubscribed : grandAcct.getAmtbSubscTos()){
						if(pmtbProductType.getProductTypeId().contains(grandSubscribed.getComp_id().getPmtbProductType().getProductTypeId())){
							grandDeleted.add(grandSubscribed);
//							if(pmtbProductType.getProductTypeId().contains(NonConfigurableConstants.PREMIER_SERVICE) && NonConfigurableConstants.BOOLEAN_NO.equals((String) prop.get("ws.cnii.disable"))){
							if(cardLessProductList.contains(pmtbProductType.getProductTypeId()) && NonConfigurableConstants.BOOLEAN_NO.equals((String) prop.get("ws.cnii.disable"))){
								AmtbAcctStatus parentStatus = getCurrentStatus(grandAcct.getAmtbAccount().getAmtbAccount().getAmtbAcctStatuses());
								if(parentStatus!=null && !parentStatus.getAcctStatus().equals(NonConfigurableConstants.ACCOUNT_STATUS_PENDING_ACTIVATION)){
									IttbCniiAcctReq cniiRequest = new IttbCniiAcctReq();
									cniiRequest.setReqId(DateUtil.convertDateToStr(DateUtil.getCurrentDate(), DateUtil.GLOBAL_CNII_SEQ_FORMAT) + this.daoHelper.getAccountDao().getNextSequenceNo(Sequence.CNII_REQ_NO_SEQUENCE));
									cniiRequest.setReqDate(DateUtil.getCurrentTimestamp());
									cniiRequest.setEventType(NonConfigurableConstants.CNII_REQUEST_EVENT_TYPE_NEW);
									cniiRequest.setReqStatus(NonConfigurableConstants.CNII_REQUEST_STATUS_PENDING);
									cniiRequest.setTerminateDt(DateUtil.getCurrentDate());
									cniiRequest.setValidFlg(NonConfigurableConstants.BOOLEAN_YES);
									cniiRequest.setUpdatedBy(NonConfigurableConstants.CNII_REQUEST_UPDATED_BY_IBS);
									cniiRequest.setAccountId(acct.getCustNo()+childAcct.getCode()+grandAcct.getCode());
									cniiRequest.setAccountCd(grandAcct.getCode());
									cniiRequest.setParentId(acct.getCustNo()+childAcct.getCode());
									cniiRequest.setAccountNm(grandAcct.getAccountName());
									cniiRequest.setUpdatedDt(DateUtil.getCurrentTimestamp());
									//this.save(cniiRequest);
									//ArrayList<IttbCniiAcctReq> cniiList = new ArrayList<IttbCniiAcctReq>();
									cniiList.add(cniiRequest);
									//IBSCNIIUpdateAcctClient.send(cniiList);
								}
							}
							break;
						}
					}
					grandAcct.getAmtbSubscTos().clear();
					for(AmtbSubscTo grandDelete : grandDeleted){
						this.delete(grandDelete);
					}
				}
			}
		}
		if (NonConfigurableConstants.BOOLEAN_NO.equals((String) prop.get("ws.cnii.disable")))
		{
			try {
				String msg = updateCniiAcctSyncProcedure(cniiList);
				logger.info("CNII Acct Sync log : "+msg);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				throw new CniiInterfaceException("CNII Interface Acct Sync Error - "+e);
			}
//			IBSCNIIUpdateAcctClient.send(cniiList);
		}
	}
	public void unsubscribeProductTypeApproval(String custNo, List<String> productTypeIds) {
		AmtbAccount corpAcct = this.daoHelper.getAccountDao().getAccount(custNo, NonConfigurableConstants.CORPORATE_LEVEL, null, null);
		List<AmtbSubscTo> deleted = new ArrayList<AmtbSubscTo>();
		List<AmtbSubscProdReq> emailApproval = new ArrayList<AmtbSubscProdReq>();
		for(AmtbSubscTo subscription : corpAcct.getAmtbSubscTos()){
			if(productTypeIds.contains(subscription.getComp_id().getPmtbProductType().getProductTypeId())){
				
				AmtbSubscProdReq reqApproveSubscription = new AmtbSubscProdReq();
				
				reqApproveSubscription.setAmtbAccount(corpAcct);
				reqApproveSubscription.setPmtbProductType(subscription.getComp_id().getPmtbProductType());

				reqApproveSubscription.setEffectiveDt(DateUtil.getCurrentTimestamp());
				reqApproveSubscription.setReqDt(DateUtil.getCurrentTimestamp());
				reqApproveSubscription.setReqBy(CommonWindow.getUserId());
				reqApproveSubscription.setAppStatus(NonConfigurableConstants.SUBSCRIPTION_APPROVE_STATUS_PENDING_APPROVED);
				reqApproveSubscription.setSubscAction(NonConfigurableConstants.SUBSCRIPTION_STATUS_UNSUBSCRIBE); //UNSUB
				reqApproveSubscription.setRemarks("");
				
				this.save(reqApproveSubscription);
				emailApproval.add(reqApproveSubscription);
				deleted.add(subscription);
				if(deleted.size()==productTypeIds.size()){
					break;
				}
			}
		}
		if(emailApproval.size() > 0)
			sendSubscriptionApprovalEmail(emailApproval.get(0));
	}
	public List<Map<String, Object>> getUnsubscribedProductTypes(String custNo){
		AmtbAccount acct = this.daoHelper.getAccountDao().retrieveAccount(custNo);
		List<String> subscribedIds = new ArrayList<String>();
		for(AmtbSubscTo subscription : acct.getAmtbSubscTos()){
			subscribedIds.add(subscription.getComp_id().getPmtbProductType().getProductTypeId());
		}
		List<AmtbAcctType> acctTypes = this.daoHelper.getAccountTypeDao().getAccountType(acct.getAmtbAcctType().getAcctTypeNo());
		List<Map<String, Object>> returnList = new ArrayList<Map<String, Object>>();
		if(!acctTypes.isEmpty()){
			for(PmtbProductType productType : acctTypes.get(0).getPmtbProductTypes()){
				if(!subscribedIds.contains(productType.getProductTypeId())){
					Map<String, Object> productMap = new HashMap<String, Object>();
					productMap.put("productType", productType.getName());
					productMap.put("productTypeId", productType.getProductTypeId());
					productMap.put("digits", productType.getNumberOfDigit());
					productMap.put("bin", productType.getBinRange());
					productMap.put("subBin", productType.getSubBinRange());
					productMap.put("issuable", productType.getIssuable());
					productMap.put("nameOnCard", productType.getNameOnProduct());
					productMap.put("batch", productType.getBatchIssue());
					productMap.put("issueType", productType.getIssueType());
					productMap.put("fixedValue", productType.getFixedValue());
					productMap.put("creditLimit", productType.getCreditLimit());
					productMap.put("luhn", productType.getLuhnCheck());
					productMap.put("defaultStatus", productType.getDefaultCardStatus());
					productMap.put("negativeFile", productType.getExternalCard());
					productMap.put("otu", productType.getOneTimeUsage());
					productMap.put("validity", productType.getValidityPeriod());
					productMap.put("defaultExpiry", productType.getDefaultValidPeriod());
					productMap.put("replaceFee", productType.getReplacementFees());
					returnList.add(productMap);
				}
			}
		}
		return returnList;
	}
	public void addProductSubscription(AmtbAccount acct, PmtbProductType productType, MstbProdDiscMaster productDiscount, LrtbRewardMaster reward, MstbSubscFeeMaster subscription, MstbIssuanceFeeMaster issuance, Timestamp effectiveDt) throws CniiInterfaceException{
		AmtbSubscTo newSubscription = new AmtbSubscTo(new AmtbSubscToPK(productType, acct), effectiveDt);
		if(productDiscount!=null){
			newSubscription.setMstbProdDiscMaster(productDiscount);
		}
		if(reward!=null){
			newSubscription.setLrtbRewardMaster(reward);
		}
		if(subscription!=null){
			newSubscription.setMstbSubscFeeMaster(subscription);
		}
		if(issuance!=null){
			newSubscription.setMstbIssuanceFeeMaster(issuance);
		}
		Map<String, String> prop = (Map)SpringUtil.getBean("webserviceProperties");
		List<String> cardLessProductList = new ArrayList<String>();
		List<PmtbProductType> cardlessProducts = this.businessHelper.getProductTypeBusiness().getCardlessProductType();
		for(PmtbProductType cardlessProduct : cardlessProducts) {
			cardLessProductList.add(cardlessProduct.getProductTypeId().trim());
		}
//		if(productType.getProductTypeId().equals(NonConfigurableConstants.PREMIER_SERVICE) && NonConfigurableConstants.BOOLEAN_NO.equals((String) prop.get("ws.cnii.disable"))){
		if(cardLessProductList.contains(productType.getProductTypeId()) && NonConfigurableConstants.BOOLEAN_NO.equals((String) prop.get("ws.cnii.disable"))){
			AmtbAcctStatus currentStatus = getCurrentStatus(acct.getAmtbAcctStatuses());
			if(currentStatus!=null && !currentStatus.getAcctStatus().equals(NonConfigurableConstants.ACCOUNT_STATUS_PENDING_ACTIVATION)){
				IttbCniiAcctReq cniiRequest = new IttbCniiAcctReq();
				cniiRequest.setReqId(DateUtil.convertDateToStr(DateUtil.getCurrentDate(), DateUtil.GLOBAL_CNII_SEQ_FORMAT) + this.daoHelper.getAccountDao().getNextSequenceNo(Sequence.CNII_REQ_NO_SEQUENCE));
				cniiRequest.setReqDate(DateUtil.getCurrentTimestamp());
				cniiRequest.setEventType(NonConfigurableConstants.CNII_REQUEST_EVENT_TYPE_NEW);
				cniiRequest.setReqStatus(NonConfigurableConstants.CNII_REQUEST_STATUS_PENDING);
				cniiRequest.setValidFlg(NonConfigurableConstants.BOOLEAN_YES);
				cniiRequest.setUpdatedBy(NonConfigurableConstants.CNII_REQUEST_UPDATED_BY_IBS);
				cniiRequest.setAccountId(acct.getCustNo());
				cniiRequest.setAccountCd(acct.getCustNo());
				cniiRequest.setAccountNm(acct.getAccountName());
				cniiRequest.setUpdatedDt(DateUtil.getCurrentTimestamp());
				//this.save(cniiRequest);
				ArrayList<IttbCniiAcctReq> cniiList = new ArrayList<IttbCniiAcctReq>();
				cniiList.add(cniiRequest);
//				IBSCNIIUpdateAcctClient.send(cniiList);
				try {
					String msg = updateCniiAcctSyncProcedure(cniiList);
					logger.info("CNII Acct Sync log : "+msg);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					throw new CniiInterfaceException("CNII Interface Acct Sync Error - "+e);
				}
				
			}
		}
		this.save(newSubscription);
	}
	public void addProductSubscriptionApproval(String custNo, String productTypeId, Integer productDiscountId, Integer rewardId, Integer subscriptionId, Integer issuanceId) {
		AmtbAccount acct = this.daoHelper.getAccountDao().retrieveAccount(custNo);
		PmtbProductType productType = this.daoHelper.getProductTypeDao().getProductType(productTypeId);
		AmtbSubscProdReq reqApproveSubscription = new AmtbSubscProdReq();
		
		reqApproveSubscription.setAmtbAccount(acct);
		reqApproveSubscription.setPmtbProductType(productType);

		reqApproveSubscription.setEffectiveDt(DateUtil.getCurrentTimestamp());
		reqApproveSubscription.setReqDt(DateUtil.getCurrentTimestamp());
		reqApproveSubscription.setReqBy(CommonWindow.getUserId());
		reqApproveSubscription.setAppStatus(NonConfigurableConstants.SUBSCRIPTION_APPROVE_STATUS_PENDING_APPROVED);
		reqApproveSubscription.setSubscAction(NonConfigurableConstants.SUBSCRIPTION_STATUS_SUBSCRIBE);
		reqApproveSubscription.setRemarks("");
		
		if(productDiscountId!=null){
			reqApproveSubscription.setMstbProdDiscMaster((MstbProdDiscMaster)MasterSetup.getProductDiscountManager().getMaster(productDiscountId));
		}
		if(rewardId!=null){
			reqApproveSubscription.setLrtbRewardMaster((LrtbRewardMaster)MasterSetup.getRewardsManager().getMaster(rewardId));
		}
		if(subscriptionId!=null){
			reqApproveSubscription.setMstbSubscFeeMaster((MstbSubscFeeMaster) MasterSetup.getSubscriptionManager().getMaster(subscriptionId));
		}
		if(issuanceId!=null){
			reqApproveSubscription.setMstbIssuanceFeeMaster((MstbIssuanceFeeMaster) MasterSetup.getIssuanceManager().getMaster(issuanceId));
		}
	
		sendSubscriptionApprovalEmail(reqApproveSubscription);
		
		this.save(reqApproveSubscription);
	}
	public boolean hasPendApproveSubscription(String custNo, String productTypeId){
		logger.info("hasPendApproveSubscription(custNo = "+custNo+" , productTypeId = "+productTypeId+")");
		AmtbAccount amtbAccount = this.daoHelper.getAccountDao().getAccountByCustNo(custNo);
		List<AmtbSubscProdReq> prodReq = this.daoHelper.getAccountDao().getAccountPendApproveSubscription(amtbAccount.getAccountNo(), productTypeId);
		logger.info("prodReq = " + prodReq.size());
		if(prodReq.isEmpty())
			return false;
		else
			return true;
	}
	
	public Map<Integer, Map<String, String>> getAllBillingChangeRequest(String custNo){
		List<AmtbBillReq> requests = this.daoHelper.getAccountDao().getBillingRequest(custNo);
		Map<Integer, Map<String, String>> returnMap = new HashMap<Integer, Map<String,String>>();
		for(AmtbBillReq request : requests){
			Map<String, String> requestMap = new HashMap<String, String>();
			requestMap.put("acctName", request.getAmtbAccount().getAccountName());
			for(AmtbBillReqFlow flow : request.getAmtbBillReqFlows()){
				if(flow.getToStatus().equals(NonConfigurableConstants.BILLING_REQUEST_STATUS_PENDING)){
					requestMap.put("requester", flow.getSatbUser().getName());
					requestMap.put("requestDate", DateUtil.convertTimestampToStr(flow.getFlowDt(), DateUtil.GLOBAL_DATE_FORMAT));
					if(requestMap.get("status")==null){
						requestMap.put("status", NonConfigurableConstants.BILLING_REQUEST_STATUS.get(NonConfigurableConstants.BILLING_REQUEST_STATUS_PENDING));
					}
				}else if(flow.getToStatus().equals(NonConfigurableConstants.BILLING_REQUEST_STATUS_APPROVED) || flow.getToStatus().equals(NonConfigurableConstants.BILLING_REQUEST_STATUS_REJECTED)){
					requestMap.put("approver", flow.getSatbUser().getName());
					requestMap.put("approveDate", DateUtil.convertTimestampToStr(flow.getFlowDt(), DateUtil.GLOBAL_DATE_FORMAT));
					requestMap.put("status", NonConfigurableConstants.BILLING_REQUEST_STATUS.get(flow.getToStatus()));
				}
				if(requestMap.get("approver")!=null && requestMap.get("requester")!=null){
					break;
				}
			}
			if(requestMap.get("approver")==null){
				requestMap.put("approver", "-");
				requestMap.put("approveDate", "-");
			}
			returnMap.put(request.getBillReqNo(), requestMap);
		}
		return returnMap;
	}
	public Map<Integer, Map<String, String>> getPendingBillingChangeRequests(){
		List<AmtbBillReq> requests = this.daoHelper.getAccountDao().getPendingBillingRequests();
		Map<Integer, Map<String, String>> returnMap = new HashMap<Integer, Map<String, String>>();
		for(AmtbBillReq request : requests){
			Map<String, String> requestMap = new HashMap<String, String>();
			AmtbAccount acct = request.getAmtbAccount();
			requestMap.put("custNo", acct.getCustNo());
			requestMap.put("acctName", acct.getAccountName());
			AmtbBillReqFlow flow = request.getAmtbBillReqFlows().iterator().next();
			requestMap.put("reqDate", DateUtil.convertTimestampToStr(flow.getFlowDt(), DateUtil.GLOBAL_DATE_FORMAT));
			requestMap.put("remarks", flow.getRemarks());
			SatbUser user = flow.getSatbUser();
			requestMap.put("requester", user.getName());
			returnMap.put(request.getBillReqNo(), requestMap);
		}
		return returnMap;
	}
	public Map<String, List<Map<String, String>>> getBillingChangeRequest(Integer requestId){
		AmtbBillReq request = this.daoHelper.getAccountDao().getBillingRequest(requestId);
		Map<String, List<Map<String, String>>> returnMap = new HashMap<String, List<Map<String, String>>>();
		// request details
		List<Map<String, String>> requestDetails = new ArrayList<Map<String, String>>();
		Map<String, String> requestDetail = new HashMap<String, String>();
		TreeSet<AmtbBillReqFlow> sortedFlows = new TreeSet<AmtbBillReqFlow>(new Comparator<AmtbBillReqFlow>(){
			public int compare(AmtbBillReqFlow o1, AmtbBillReqFlow o2) {
				return o1.getFlowDt().compareTo(o2.getFlowDt());
			}
		});
		sortedFlows.addAll(request.getAmtbBillReqFlows());
		AmtbBillReqFlow flow = sortedFlows.first();
		requestDetail.put("requester", flow.getSatbUser().getName());
		requestDetail.put("reqDate", DateUtil.convertTimestampToStr(flow.getFlowDt(), DateUtil.GLOBAL_DATE_FORMAT));
		//requestDetail.put("reqRemarks", flow.getRemarks());
		requestDetail.put("reqRemarks", "-");
		if(sortedFlows.size()!=1){
			flow = sortedFlows.last();
			requestDetail.put("approver", flow.getSatbUser().getName());
			requestDetail.put("approveDate", DateUtil.convertTimestampToStr(flow.getFlowDt(), DateUtil.GLOBAL_DATE_FORMAT));
			requestDetail.put("approveRemarks", flow.getRemarks());
		}
		requestDetail.put("reqStatus", NonConfigurableConstants.BILLING_REQUEST_STATUS.get(flow.getToStatus()));
		requestDetails.add(requestDetail);
		returnMap.put("requestDetails", requestDetails);
		// billing details
		List<Map<String, String>> billingDetails = new ArrayList<Map<String, String>>();
		AmtbAccount acct = request.getAmtbAccount();
		// only one admin fee per account
		// Used to be only one admin fee. Now will have multiple.
		// Yiming 08 Apr 2010
		// AmtbAcctAdminFee adminFee = acct.getAmtbAcctAdminFees().iterator().next();
		TreeMap<Date, Map<String, String>> sortedBillingDetails = new TreeMap<Date, Map<String, String>>(new Comparator<Date>(){
			public int compare(Date o1, Date o2) {
				return o1.compareTo(o2);
			}
		});
		for(AmtbAcctVolDisc volumeDiscount : acct.getAmtbAcctVolDiscs()){
			Map<String, String> billingDetail = new HashMap<String, String>();
			billingDetail.put("status", NonConfigurableConstants.BILLING_REQUEST_STATUS.get(NonConfigurableConstants.BILLING_REQUEST_STATUS_APPROVED));
			if(volumeDiscount.getMstbVolDiscMaster()!=null){
				billingDetail.put("volumeDiscount", volumeDiscount.getMstbVolDiscMaster().getVolumeDiscountPlanName());
			}else{
				billingDetail.put("volumeDiscount", "-");
			}
			sortedBillingDetails.put(volumeDiscount.getEffectiveDate(), billingDetail);
		}
		for(AmtbAcctAdminFee adminFee : acct.getAmtbAcctAdminFees()){
			Map<String, String> billingDetail = sortedBillingDetails.get(adminFee.getEffectiveDate());
			if(billingDetail==null){
				billingDetail = new HashMap<String, String>();
				billingDetail.put("status", NonConfigurableConstants.BILLING_REQUEST_STATUS.get(NonConfigurableConstants.BILLING_REQUEST_STATUS_APPROVED));
			}
			billingDetail.put("adminFee", adminFee.getMstbAdminFeeMaster().getAdminFeePlanName());
			sortedBillingDetails.put(adminFee.getEffectiveDate(), billingDetail);
		}
		for(AmtbAcctBillCycle billingCycle : acct.getAmtbAcctBillCycles()){
			Map<String, String> billingDetail = sortedBillingDetails.get(billingCycle.getEffectiveDate());
			if(billingDetail==null){
				billingDetail = new HashMap<String, String>();
				billingDetail.put("status", NonConfigurableConstants.BILLING_REQUEST_STATUS.get(NonConfigurableConstants.BILLING_REQUEST_STATUS_APPROVED));
			}
			billingDetail.put("billingCycle", NonConfigurableConstants.BILLING_CYCLES.get(billingCycle.getBillingCycle()));
			sortedBillingDetails.put(billingCycle.getEffectiveDate(), billingDetail);
		}
		for(AmtbAcctVolDiscReq volumeDiscountRequest : request.getAmtbAcctVolDiscReqs()){
			Map<String, String> billingDetail = sortedBillingDetails.get(volumeDiscountRequest.getEffectiveDt());
			if(billingDetail==null){
				billingDetail = new HashMap<String, String>();
			}
			if(flow.getToStatus().equals(NonConfigurableConstants.BILLING_REQUEST_STATUS_APPROVED)){
				billingDetail.put("status", NonConfigurableConstants.BILLING_REQUEST_STATUS.get(NonConfigurableConstants.BILLING_REQUEST_STATUS_REQ_APPROVED));
			}else if(flow.getToStatus().equals(NonConfigurableConstants.BILLING_REQUEST_STATUS_REJECTED)){
				billingDetail.put("status", NonConfigurableConstants.BILLING_REQUEST_STATUS.get(NonConfigurableConstants.BILLING_REQUEST_STATUS_REQ_REJECTED));
			}else{
				billingDetail.put("status", NonConfigurableConstants.BILLING_REQUEST_STATUS.get(flow.getToStatus()));
			}
			if(volumeDiscountRequest.getMstbVolDiscMaster()!=null){
				billingDetail.put("volumeDiscount", volumeDiscountRequest.getMstbVolDiscMaster().getVolumeDiscountPlanName());
			}else{
				billingDetail.put("volumeDiscount", "-");
			}
			billingDetail.put("event", volumeDiscountRequest.getEvent());
			sortedBillingDetails.put(volumeDiscountRequest.getEffectiveDt(), billingDetail);
		}
		for(AmtbAcctBillCycleReq billCycleRequest : request.getAmtbAcctBillCycleReqs()){
			Map<String, String> billingDetail = sortedBillingDetails.get(billCycleRequest.getEffectiveDt());
			if(billingDetail==null){
				billingDetail = new HashMap<String, String>();
				if(flow.getToStatus().equals(NonConfigurableConstants.BILLING_REQUEST_STATUS_APPROVED)){
					billingDetail.put("status", NonConfigurableConstants.BILLING_REQUEST_STATUS.get(NonConfigurableConstants.BILLING_REQUEST_STATUS_REQ_APPROVED));
				}else if(flow.getToStatus().equals(NonConfigurableConstants.BILLING_REQUEST_STATUS_REJECTED)){
					billingDetail.put("status", NonConfigurableConstants.BILLING_REQUEST_STATUS.get(NonConfigurableConstants.BILLING_REQUEST_STATUS_REQ_REJECTED));
				}else{
					billingDetail.put("status", NonConfigurableConstants.BILLING_REQUEST_STATUS.get(flow.getToStatus()));
				}
			}
			billingDetail.put("billingCycle", NonConfigurableConstants.BILLING_CYCLES.get(billCycleRequest.getBillingCycle()));
			billingDetail.put("event", billCycleRequest.getEvent());
			sortedBillingDetails.put(billCycleRequest.getEffectiveDt(), billingDetail);
		}
		for(AmtbAcctAdminFeeReq adminFeeRequest : request.getAmtbAcctAdminFeeReqs()){
			Map<String, String> billingDetail = sortedBillingDetails.get(adminFeeRequest.getEffectiveDt());
			if(billingDetail==null){
				billingDetail = new HashMap<String, String>();
				if(flow.getToStatus().equals(NonConfigurableConstants.BILLING_REQUEST_STATUS_APPROVED)){
					billingDetail.put("status", NonConfigurableConstants.BILLING_REQUEST_STATUS.get(NonConfigurableConstants.BILLING_REQUEST_STATUS_REQ_APPROVED));
				}else if(flow.getToStatus().equals(NonConfigurableConstants.BILLING_REQUEST_STATUS_REJECTED)){
					billingDetail.put("status", NonConfigurableConstants.BILLING_REQUEST_STATUS.get(NonConfigurableConstants.BILLING_REQUEST_STATUS_REQ_REJECTED));
				}else{
					billingDetail.put("status", NonConfigurableConstants.BILLING_REQUEST_STATUS.get(flow.getToStatus()));
				}
			}
			billingDetail.put("adminFee", adminFeeRequest.getMstbAdminFeeMaster().getAdminFeePlanName());
			billingDetail.put("event", adminFeeRequest.getEvent());
			sortedBillingDetails.put(adminFeeRequest.getEffectiveDt(), billingDetail);
		}
		String prevVolume = null, prevCycle = null, prevAdmin = null;
		for(Date effectiveDate : sortedBillingDetails.keySet()){
			Map<String, String> billingDetail = sortedBillingDetails.get(effectiveDate);
			if(billingDetail.get("volumeDiscount")==null){
				if(prevVolume!=null){
					billingDetail.put("volumeDiscount", prevVolume);
				}else{
					billingDetail.put("volumeDiscount", "-");
				}
			}
			prevVolume = billingDetail.get("volumeDiscount");
			if(billingDetail.get("billingCycle")==null){
				billingDetail.put("billingCycle", prevCycle);
			}
			prevCycle = billingDetail.get("billingCycle");
			if(billingDetail.get("adminFee")==null){
				billingDetail.put("adminFee", prevAdmin);
			}
			prevAdmin = billingDetail.get("adminFee");
//			billingDetail.put("adminFee", adminFee.getMstbAdminFeeMaster().getAdminFeePlanName());
			billingDetail.put("effectiveDate", DateUtil.convertDateToStr(effectiveDate, DateUtil.GLOBAL_DATE_FORMAT));
			billingDetail.put("event", billingDetail.get("event"));
			billingDetails.add(billingDetail);
		}
		returnMap.put("billingDetails", billingDetails);
		// credit term
		List<Map<String, String>> creditTerms = new ArrayList<Map<String, String>>();
		TreeMap<Date, Map<String, String>> sortedCreditTerms = new TreeMap<Date, Map<String, String>>(new Comparator<Date>(){
			public int compare(Date o1, Date o2) {
				return o1.compareTo(o2);
			}
		});
		for(AmtbAcctCredTerm creditTerm : acct.getAmtbAcctCredTerms()){
			Map<String, String> creditTermMap = new HashMap<String, String>();
			creditTermMap.put("creditTerm", creditTerm.getMstbCreditTermMaster().getCreditTermPlanName());
			creditTermMap.put("effectiveDate", DateUtil.convertDateToStr(creditTerm.getEffectiveDate(), DateUtil.GLOBAL_DATE_FORMAT));
			creditTermMap.put("status", NonConfigurableConstants.BILLING_REQUEST_STATUS.get(NonConfigurableConstants.BILLING_REQUEST_STATUS_APPROVED));
			sortedCreditTerms.put(creditTerm.getEffectiveDate(), creditTermMap);
		}
		for(AmtbAcctCredTermReq creditTermRequest : request.getAmtbAcctCredTermReqs()){
			Map<String, String> creditTermMap = new HashMap<String, String>();
			creditTermMap.put("creditTerm", creditTermRequest.getMstbCreditTermMaster().getCreditTermPlanName());
			creditTermMap.put("effectiveDate", DateUtil.convertDateToStr(creditTermRequest.getEffectiveDt(), DateUtil.GLOBAL_DATE_FORMAT));
			if(flow.getToStatus().equals(NonConfigurableConstants.BILLING_REQUEST_STATUS_APPROVED)){
				creditTermMap.put("status", NonConfigurableConstants.BILLING_REQUEST_STATUS.get(NonConfigurableConstants.BILLING_REQUEST_STATUS_REQ_APPROVED));
			}else if(flow.getToStatus().equals(NonConfigurableConstants.BILLING_REQUEST_STATUS_APPROVED)){
				creditTermMap.put("status", NonConfigurableConstants.BILLING_REQUEST_STATUS.get(NonConfigurableConstants.BILLING_REQUEST_STATUS_REQ_REJECTED));
			}else{
				creditTermMap.put("status", NonConfigurableConstants.BILLING_REQUEST_STATUS.get(flow.getToStatus()));
			}
			creditTermMap.put("event", creditTermRequest.getEvent());
			sortedCreditTerms.put(creditTermRequest.getEffectiveDt(), creditTermMap);
		}
		for(Date effectiveDate : sortedCreditTerms.keySet()){
			Map<String, String> creditTermMap = sortedCreditTerms.get(effectiveDate);
			creditTerms.add(creditTermMap);
		}
		returnMap.put("creditTerms", creditTerms);
		// early payment
		List<Map<String, String>> earlyPymts = new ArrayList<Map<String, String>>();
		TreeMap<Date, Map<String, String>> sortedEarlyPymts = new TreeMap<Date, Map<String, String>>(new Comparator<Date>(){
			public int compare(Date o1, Date o2) {
				return o1.compareTo(o2);
			}
		});
		for(AmtbAcctEarlyPymt earlyPymt : acct.getAmtbAcctEarlyPymts()){
			Map<String, String> earlyPymtMap = new HashMap<String, String>();
			if(earlyPymt.getMstbEarlyPaymentMaster()!=null){
				earlyPymtMap.put("earlyPymt", earlyPymt.getMstbEarlyPaymentMaster().getEarlyPaymentPlanName());
			}else{
				earlyPymtMap.put("earlyPymt", "-");
			}
			earlyPymtMap.put("effectiveDate", DateUtil.convertDateToStr(earlyPymt.getEffectiveDate(), DateUtil.GLOBAL_DATE_FORMAT));
			earlyPymtMap.put("status", NonConfigurableConstants.BILLING_REQUEST_STATUS.get(NonConfigurableConstants.BILLING_REQUEST_STATUS_APPROVED));
			sortedEarlyPymts.put(earlyPymt.getEffectiveDate(), earlyPymtMap);
		}
		for(AmtbAcctEarlyPymtReq earlyPymtRequest : request.getAmtbAcctEarlyPymtReqs()){
			Map<String, String> earlyPymtMap = new HashMap<String, String>();
			if(earlyPymtRequest.getMstbEarlyPaymentMaster()!=null){
				earlyPymtMap.put("earlyPymt", earlyPymtRequest.getMstbEarlyPaymentMaster().getEarlyPaymentPlanName());
			}else{
				earlyPymtMap.put("earlyPymt", "-");
			}
			earlyPymtMap.put("effectiveDate", DateUtil.convertDateToStr(earlyPymtRequest.getEffectiveDt(), DateUtil.GLOBAL_DATE_FORMAT));
			if(flow.getToStatus().equals(NonConfigurableConstants.BILLING_REQUEST_STATUS_APPROVED)){
				earlyPymtMap.put("status", NonConfigurableConstants.BILLING_REQUEST_STATUS.get(NonConfigurableConstants.BILLING_REQUEST_STATUS_REQ_APPROVED));
			}else if(flow.getToStatus().equals(NonConfigurableConstants.BILLING_REQUEST_STATUS_APPROVED)){
				earlyPymtMap.put("status", NonConfigurableConstants.BILLING_REQUEST_STATUS.get(NonConfigurableConstants.BILLING_REQUEST_STATUS_REQ_REJECTED));
			}else{
				earlyPymtMap.put("status", NonConfigurableConstants.BILLING_REQUEST_STATUS.get(flow.getToStatus()));
			}
			earlyPymtMap.put("event", earlyPymtRequest.getEvent());
			sortedEarlyPymts.put(earlyPymtRequest.getEffectiveDt(), earlyPymtMap);
		}
		for(Date effectiveDate : sortedEarlyPymts.keySet()){
			Map<String, String> earlyPymtMap = sortedEarlyPymts.get(effectiveDate);
			earlyPymts.add(earlyPymtMap);
		}
		returnMap.put("earlyPymts", earlyPymts);
		// late payment
		List<Map<String, String>> latePymts = new ArrayList<Map<String, String>>();
		TreeMap<Date, Map<String, String>> sortedLatePymts = new TreeMap<Date, Map<String, String>>(new Comparator<Date>(){
			public int compare(Date o1, Date o2) {
				return o1.compareTo(o2);
			}
		});
		for(AmtbAcctLatePymt latePymt : acct.getAmtbAcctLatePymts()){
			Map<String, String> latePymtMap = new HashMap<String, String>();
			latePymtMap.put("latePymt", latePymt.getMstbLatePaymentMaster().getLatePaymentPlanName());
			latePymtMap.put("effectiveDate", DateUtil.convertDateToStr(latePymt.getEffectiveDate(), DateUtil.GLOBAL_DATE_FORMAT));
			latePymtMap.put("status", NonConfigurableConstants.BILLING_REQUEST_STATUS.get(NonConfigurableConstants.BILLING_REQUEST_STATUS_APPROVED));
			sortedLatePymts.put(latePymt.getEffectiveDate(), latePymtMap);
		}
		for(AmtbAcctLatePymtReq latePymtRequest : request.getAmtbAcctLatePymtReqs()){
			Map<String, String> latePymtMap = new HashMap<String, String>();
			latePymtMap.put("latePymt", latePymtRequest.getMstbLatePaymentMaster().getLatePaymentPlanName());
			latePymtMap.put("effectiveDate", DateUtil.convertDateToStr(latePymtRequest.getEffectiveDt(), DateUtil.GLOBAL_DATE_FORMAT));
			if(flow.getToStatus().equals(NonConfigurableConstants.BILLING_REQUEST_STATUS_APPROVED)){
				latePymtMap.put("status", NonConfigurableConstants.BILLING_REQUEST_STATUS.get(NonConfigurableConstants.BILLING_REQUEST_STATUS_REQ_APPROVED));
			}else if(flow.getToStatus().equals(NonConfigurableConstants.BILLING_REQUEST_STATUS_APPROVED)){
				latePymtMap.put("status", NonConfigurableConstants.BILLING_REQUEST_STATUS.get(NonConfigurableConstants.BILLING_REQUEST_STATUS_REQ_REJECTED));
			}else{
				latePymtMap.put("status", NonConfigurableConstants.BILLING_REQUEST_STATUS.get(flow.getToStatus()));
			}
			latePymtMap.put("event", latePymtRequest.getEvent());
			sortedLatePymts.put(latePymtRequest.getEffectiveDt(), latePymtMap);
		}
		for(Date effectiveDate : sortedLatePymts.keySet()){
			Map<String, String> latePymtMap = sortedLatePymts.get(effectiveDate);
			latePymts.add(latePymtMap);
		}
		returnMap.put("latePymts", latePymts);
		// promotion
		List<Map<String, String>> promotions = new ArrayList<Map<String, String>>();
		for(AmtbAcctPromotion promotion : acct.getAmtbAcctPromotions()){
			Map<String, String> promotionMap = new HashMap<String, String>();
			promotionMap.put("acctPromotionNo", promotion.getAcctPromotionNo().toString());
			
			MstbPromotion promo = promotion.getMstbPromotion();
			MstbPromoDetail promoDetail = promo.getCurrentPromoDetail();
			
			promotionMap.put("promotion", promoDetail.getName());
			promotionMap.put("effectiveDateFrom", DateUtil.convertDateToStr(promotion.getEffectiveDateFrom(), DateUtil.GLOBAL_DATE_FORMAT));
			promotionMap.put("effectiveDateTo", DateUtil.convertDateToStr(promotion.getEffectiveDateTo(), DateUtil.GLOBAL_DATE_FORMAT));
			promotionMap.put("status", NonConfigurableConstants.BILLING_REQUEST_STATUS.get(NonConfigurableConstants.BILLING_REQUEST_STATUS_APPROVED));
			promotions.add(promotionMap);
		}
		Collections.sort(promotions, new Comparator<Map<String, String>>(){
			public int compare(Map<String, String> o1, Map<String, String> o2) {
				return o1.get("effectiveDateFrom").compareTo(o2.get("effectiveDateFrom"));
			}
		});
		for(AmtbAcctPromotionReq promotionRequest : request.getAmtbAcctPromotionReqs()){
			Map<String, String> promotionMap = null;
			if(promotionRequest.getEvent().equals(NonConfigurableConstants.BILLING_REQUEST_EVENT_UPDATE)){
				for(Map<String, String> promotion : promotions){
					if(promotion.get("acctPromotionNo").equals(promotionRequest.getAmtbAcctPromotion().getAcctPromotionNo().toString())){
						promotionMap = promotion;
						Date effectiveDateFrom = DateUtil.convertStrToDate(promotion.get("effectiveDateFrom"), DateUtil.GLOBAL_DATE_FORMAT);
						if(effectiveDateFrom.before(DateUtil.getCurrentDate()) && !DateUtil.isToday(effectiveDateFrom)){
							if(promotion.get("effectiveDateFrom").equals(DateUtil.convertDateToStr(promotionRequest.getEffectiveDtFrom(), DateUtil.GLOBAL_DATE_FORMAT))){
								promotion.put("approvable", "true");
							}else{
								promotion.put("approvable", "false");
							}
						}else{
							if(promotionRequest.getEffectiveDtFrom().before(DateUtil.getCurrentDate()) && !DateUtil.isToday(promotionRequest.getEffectiveDtFrom())){
								promotion.put("approvable", "false");
							}else{
								promotion.put("approvable", "true");
							}
						}
						break;
					}
				}
			}else{
				promotionMap = new HashMap<String, String>();
				promotionMap.put("approvable", "true");
			}
			promotionMap.put("promotion", promotionRequest.getMstbPromotion().getCurrentPromoDetail().getName());
			promotionMap.put("effectiveDateFrom", DateUtil.convertDateToStr(promotionRequest.getEffectiveDtFrom(), DateUtil.GLOBAL_DATE_FORMAT));
			promotionMap.put("effectiveDateTo", DateUtil.convertDateToStr(promotionRequest.getEffectiveDtTo(), DateUtil.GLOBAL_DATE_FORMAT));
			if(flow.getToStatus().equals(NonConfigurableConstants.BILLING_REQUEST_STATUS_APPROVED)){
				promotionMap.put("status", NonConfigurableConstants.BILLING_REQUEST_STATUS.get(NonConfigurableConstants.BILLING_REQUEST_STATUS_REQ_APPROVED));
			}else if(flow.getToStatus().equals(NonConfigurableConstants.BILLING_REQUEST_STATUS_APPROVED)){
				promotionMap.put("status", NonConfigurableConstants.BILLING_REQUEST_STATUS.get(NonConfigurableConstants.BILLING_REQUEST_STATUS_REQ_REJECTED));
			}else{
				promotionMap.put("status", NonConfigurableConstants.BILLING_REQUEST_STATUS.get(flow.getToStatus()));
			}
			promotionMap.put("event", promotionRequest.getEvent());
			if(!promotionRequest.getEvent().equals(NonConfigurableConstants.BILLING_REQUEST_EVENT_UPDATE)){
				promotions.add(promotionMap);
			}
		}
		returnMap.put("promotions", promotions);
		return returnMap;
	}
	public void approveBillingChangeRequest(Integer requestId, String remarks, String userId){
		AmtbBillReq request = this.daoHelper.getAccountDao().getBillingRequest(requestId);
		AmtbAccount acct = request.getAmtbAccount();
		if(!request.getAmtbAcctBillCycleReqs().isEmpty()){
			for(AmtbAcctBillCycleReq billingCycle : request.getAmtbAcctBillCycleReqs()){
				AmtbAcctBillCycle newCycle = null;
				for(AmtbAcctBillCycle cycle : acct.getAmtbAcctBillCycles()){
					if(cycle.getEffectiveDate().equals(billingCycle.getEffectiveDt())){
						newCycle = cycle;
						break;
					}
				}
				if(billingCycle.getEvent().equals(NonConfigurableConstants.BILLING_REQUEST_EVENT_DELETE)){
					if(newCycle!=null){
						acct.getAmtbAcctBillCycles().remove(newCycle);
						this.delete(newCycle);
					}
				}else{
					boolean newStatus = newCycle==null;
					if(newStatus){
						newCycle = new AmtbAcctBillCycle();
						newCycle.setAmtbAccount(request.getAmtbAccount());
						newCycle.setEffectiveDate(new java.sql.Date(billingCycle.getEffectiveDt().getTime()));
					}
					newCycle.setBillingCycle(billingCycle.getBillingCycle());
					if(newStatus){
						this.save(newCycle);
					}else{
						this.update(newCycle);
					}
				}
			}
		}
		if(!request.getAmtbAcctVolDiscReqs().isEmpty()){
			for(AmtbAcctVolDiscReq volumeDiscount : request.getAmtbAcctVolDiscReqs()){
				AmtbAcctVolDisc newVolume = null;
				for(AmtbAcctVolDisc volume : acct.getAmtbAcctVolDiscs()){
					if(volume.getEffectiveDate().equals(volumeDiscount.getEffectiveDt())){
						newVolume = volume;
						break;
					}
				}
				if(volumeDiscount.getEvent().equals(NonConfigurableConstants.BILLING_REQUEST_EVENT_DELETE)){
					if(newVolume!=null){
						acct.getAmtbAcctVolDiscs().remove(newVolume);
						this.delete(newVolume);
					}
				}else{
					boolean newStatus = newVolume==null;
					if(newStatus){
						newVolume = new AmtbAcctVolDisc();
						newVolume.setAmtbAccount(request.getAmtbAccount());
						newVolume.setEffectiveDate(new java.sql.Date(volumeDiscount.getEffectiveDt().getTime()));
					}
					newVolume.setMstbVolDiscMaster(volumeDiscount.getMstbVolDiscMaster());
					if(newStatus){
						this.save(newVolume);
					}else{
						this.update(newVolume);
					}
				}
			}
		}
		if(!request.getAmtbAcctAdminFeeReqs().isEmpty()){
			for(AmtbAcctAdminFeeReq adminFee : request.getAmtbAcctAdminFeeReqs()){
				AmtbAcctAdminFee newAdmin = null;
				for(AmtbAcctAdminFee admin : acct.getAmtbAcctAdminFees()){
					if(admin.getEffectiveDate().equals(adminFee.getEffectiveDt())){
						newAdmin = admin;
						break;
					}
				}
				if(adminFee.getEvent().equals(NonConfigurableConstants.BILLING_REQUEST_EVENT_DELETE)){
					if(newAdmin!=null){
						acct.getAmtbAcctVolDiscs().remove(newAdmin);
						this.delete(newAdmin);
					}
				}else{
					boolean newStatus = newAdmin==null;
					if(newStatus){
						newAdmin = new AmtbAcctAdminFee();
						newAdmin.setAmtbAccount(request.getAmtbAccount());
						newAdmin.setEffectiveDate(new java.sql.Date(adminFee.getEffectiveDt().getTime()));
					}
					newAdmin.setMstbAdminFeeMaster(adminFee.getMstbAdminFeeMaster());
					if(newStatus){
						this.save(newAdmin);
					}else{
						this.update(newAdmin);
					}
				}
			}
		}
		if(!request.getAmtbAcctCredTermReqs().isEmpty()){
			for(AmtbAcctCredTermReq creditTerm : request.getAmtbAcctCredTermReqs()){
				AmtbAcctCredTerm newTerm = null;
				for(AmtbAcctCredTerm credit : acct.getAmtbAcctCredTerms()){
					if(credit.getEffectiveDate().equals(creditTerm.getEffectiveDt())){
						newTerm = credit;
						break;
					}
				}
				if(creditTerm.getEvent().equals(NonConfigurableConstants.BILLING_REQUEST_EVENT_DELETE)){
					if(newTerm!=null){
						acct.getAmtbAcctCredTerms().remove(newTerm);
						this.delete(newTerm);
					}
				}else{
					boolean newStatus = newTerm==null;
					if(newStatus){
						newTerm = new AmtbAcctCredTerm();
						newTerm.setAmtbAccount(request.getAmtbAccount());
						newTerm.setEffectiveDate(new java.sql.Date(creditTerm.getEffectiveDt().getTime()));
					}
					newTerm.setMstbCreditTermMaster(creditTerm.getMstbCreditTermMaster());
					if(newStatus){
						this.save(newTerm);
					}else{
						this.update(newTerm);
					}
				}
			}
		}
		if(!request.getAmtbAcctEarlyPymtReqs().isEmpty()){
			for(AmtbAcctEarlyPymtReq earlyPymt : request.getAmtbAcctEarlyPymtReqs()){
				AmtbAcctEarlyPymt newEarly = null;
				for(AmtbAcctEarlyPymt early : acct.getAmtbAcctEarlyPymts()){
					if(early.getEffectiveDate().equals(earlyPymt.getEffectiveDt())){
						newEarly = early;
						break;
					}
				}
				if(earlyPymt.getEvent().equals(NonConfigurableConstants.BILLING_REQUEST_EVENT_DELETE)){
					if(newEarly!=null){
						acct.getAmtbAcctEarlyPymts().remove(newEarly);
						this.delete(newEarly);
					}
				}else{
					boolean newStatus = newEarly==null;
					if(newStatus){
						newEarly = new AmtbAcctEarlyPymt();
						newEarly.setAmtbAccount(request.getAmtbAccount());
						newEarly.setEffectiveDate(new java.sql.Date(earlyPymt.getEffectiveDt().getTime()));
					}
					newEarly.setMstbEarlyPaymentMaster(earlyPymt.getMstbEarlyPaymentMaster());
					if(newStatus){
						this.save(newEarly);
					}else{
						this.update(newEarly);
					}
				}
			}
		}
		if(!request.getAmtbAcctLatePymtReqs().isEmpty()){
			for(AmtbAcctLatePymtReq latePymt : request.getAmtbAcctLatePymtReqs()){
				AmtbAcctLatePymt newLate = null;
				for(AmtbAcctLatePymt late : acct.getAmtbAcctLatePymts()){
					if(late.getEffectiveDate().equals(latePymt.getEffectiveDt())){
						newLate = late;
						break;
					}
				}
				if(latePymt.getEvent().equals(NonConfigurableConstants.BILLING_REQUEST_EVENT_DELETE)){
					if(newLate!=null){
						acct.getAmtbAcctLatePymts().remove(newLate);
						this.delete(newLate);
					}
				}else{
					boolean newStatus = newLate==null;
					if(newStatus){
						newLate = new AmtbAcctLatePymt();
						newLate.setAmtbAccount(request.getAmtbAccount());
						newLate.setEffectiveDate(new java.sql.Date(latePymt.getEffectiveDt().getTime()));
					}
					newLate.setMstbLatePaymentMaster(latePymt.getMstbLatePaymentMaster());
					if(newStatus){
						this.save(newLate);
					}else{
						this.update(newLate);
					}
				}
			}
		}
		if(!request.getAmtbAcctPromotionReqs().isEmpty()){
			for(AmtbAcctPromotionReq promo : request.getAmtbAcctPromotionReqs()){
				if(promo.getEvent().equals(NonConfigurableConstants.BILLING_REQUEST_EVENT_CREATE)){
					AmtbAcctPromotion newPromo = new AmtbAcctPromotion();
					newPromo.setAmtbAccount(request.getAmtbAccount());
					newPromo.setEffectiveDateFrom(new java.sql.Date(promo.getEffectiveDtFrom().getTime()));
					if(promo.getEffectiveDtTo()!=null){
						newPromo.setEffectiveDateTo(new java.sql.Date(promo.getEffectiveDtTo().getTime()));
					}else{
						newPromo.setEffectiveDateTo(null);
					}
					newPromo.setMstbPromotion(promo.getMstbPromotion());
					this.save(newPromo);
				}else if(promo.getEvent().equals(NonConfigurableConstants.BILLING_REQUEST_EVENT_DELETE)){
					for(AmtbAcctPromotion promotion : acct.getAmtbAcctPromotions()){
						if(promo.getAmtbAcctPromotion().getAcctPromotionNo().equals(promotion.getAcctPromotionNo())){
							acct.getAmtbAcctPromotions().remove(promotion);
							//AmtbAcctPromotion deletePromo = promotion;
							this.delete(promotion);
							break;
						}
					}
				}else if(promo.getEvent().equals(NonConfigurableConstants.BILLING_REQUEST_EVENT_UPDATE)){
					for(AmtbAcctPromotion promotion : acct.getAmtbAcctPromotions()){
						if(promo.getAmtbAcctPromotion().getAcctPromotionNo().equals(promotion.getAcctPromotionNo())){
							promotion.setEffectiveDateFrom(new java.sql.Date(promo.getEffectiveDtFrom().getTime()));
							if(promo.getEffectiveDtTo()!=null){
								promotion.setEffectiveDateTo(new java.sql.Date(promo.getEffectiveDtTo().getTime()));
							}else{
								promotion.setEffectiveDateTo(null);
							}
							promotion.setMstbPromotion(promo.getMstbPromotion());
							this.update(promotion);
							break;
						}
					}
//					boolean newStatus = newLate==null;
//					if(newStatus){
//						newLate = new AmtbAcctLatePymt();
//						newLate.setAmtbAccount(request.getAmtbAccount());
//						newLate.setEffectiveDate(new java.sql.Date(latePymt.getEffectiveDt().getTime()));
//					}
//					newLate.setMstbLatePaymentMaster(latePymt.getMstbLatePaymentMaster());
//					if(newStatus){
//						this.save(newLate);
//					}else{
//						this.update(newLate);
//					}
				}
			}
		}
		this.update(acct, userId);
		AmtbBillReqFlow approveFlow = new AmtbBillReqFlow();
		approveFlow.setAmtbBillReq(request);
		approveFlow.setFlowDt(DateUtil.getCurrentTimestamp());
		approveFlow.setFromStatus(NonConfigurableConstants.BILLING_REQUEST_STATUS_PENDING);
		approveFlow.setToStatus(NonConfigurableConstants.BILLING_REQUEST_STATUS_APPROVED);
		approveFlow.setRemarks(remarks);
		SatbUser approver = this.daoHelper.getUserDao().getUserWithRoles(CommonWindow.getUserId());
		approveFlow.setSatbUser(approver);
		this.save(approveFlow);
		// sending email
		List<String> toEmails = new ArrayList<String>();
		String userName = "";
		for(AmtbBillReqFlow flow : request.getAmtbBillReqFlows()){
			if(flow.getToStatus().equals(NonConfigurableConstants.BILLING_REQUEST_STATUS_PENDING)){
				toEmails.add(flow.getSatbUser().getEmail());
				userName = flow.getSatbUser().getName();
				break;
			}
		}
		EmailUtil.sendEmail(toEmails.toArray(new String[]{}), new String[]{},
				ConfigurableConstants.getEmailText(ConfigurableConstants.EMAIL_TYPE_BILL_REQUEST_APPROVED, ConfigurableConstants.EMAIL_SUBJECT),
				ConfigurableConstants.getEmailText(ConfigurableConstants.EMAIL_TYPE_BILL_REQUEST_APPROVED, ConfigurableConstants.EMAIL_CONTENT)
				.replaceAll("#userName#", userName)
				.replaceAll("#custNo#", acct.getCustNo())
				.replaceAll("#acctName#", acct.getAccountName()));
	}
	public void rejectBillingChangeRequest(Integer requestId, String remarks, String userId){
		AmtbBillReq request = this.daoHelper.getAccountDao().getBillingRequest(requestId);
		AmtbBillReqFlow rejectFlow = new AmtbBillReqFlow();
		rejectFlow.setAmtbBillReq(request);
		rejectFlow.setFlowDt(DateUtil.getCurrentTimestamp());
		rejectFlow.setFromStatus(NonConfigurableConstants.BILLING_REQUEST_STATUS_PENDING);
		rejectFlow.setToStatus(NonConfigurableConstants.BILLING_REQUEST_STATUS_REJECTED);
		rejectFlow.setRemarks(remarks);
		SatbUser approver = this.daoHelper.getUserDao().getUserWithRoles(CommonWindow.getUserId());
		rejectFlow.setSatbUser(approver);
		this.save(rejectFlow);
		// sending email
		String userName = "";
		List<String> toEmails = new ArrayList<String>();
		for(AmtbBillReqFlow flow : request.getAmtbBillReqFlows()){
			if(flow.getToStatus().equals(NonConfigurableConstants.BILLING_REQUEST_STATUS_PENDING)){
				toEmails.add(flow.getSatbUser().getEmail());
				userName = flow.getSatbUser().getName();
				break;
			}
		}
		EmailUtil.sendEmail(toEmails.toArray(new String[]{}), new String[]{},
				ConfigurableConstants.getEmailText(ConfigurableConstants.EMAIL_TYPE_BILL_REQUEST_REJECTED, ConfigurableConstants.EMAIL_SUBJECT),
				ConfigurableConstants.getEmailText(ConfigurableConstants.EMAIL_TYPE_BILL_REQUEST_REJECTED, ConfigurableConstants.EMAIL_CONTENT)
				.replaceAll("#userName#", userName)
				.replaceAll("#custNo#", request.getAmtbAccount().getCustNo())
				.replaceAll("#acctName#", request.getAmtbAccount().getAccountName()));
	}
	public Map<Integer, Map<String, String>> getPendingCreditReviewRequests(){
		List<AmtbCredRevReq> requests = this.daoHelper.getAccountDao().getPendingCreditReviewRequests();
		Map<Integer, Map<String, String>> returnMap = new HashMap<Integer, Map<String, String>>();
		for(AmtbCredRevReq request : requests){
			Map<String, String> requestMap = new HashMap<String, String>();
			AmtbAccount acct = request.getAmtbAccount();
			requestMap.put("custNo", acct.getCustNo());
			requestMap.put("acctName", acct.getAccountName());
			requestMap.put("creditLimit", StringUtil.bigDecimalToString(acct.getCreditLimit(), StringUtil.GLOBAL_DECIMAL_FORMAT));
			requestMap.put("newCreditLimit", StringUtil.bigDecimalToString(request.getNewCreditLimit(), StringUtil.GLOBAL_DECIMAL_FORMAT));
			AmtbCredRevReqFlow flow = request.getAmtbCredRevReqFlows().iterator().next();
			requestMap.put("reqDate", DateUtil.convertTimestampToStr(flow.getFlowDt(), DateUtil.GLOBAL_DATE_FORMAT));
			requestMap.put("remarks", flow.getRemarks());
			SatbUser user = flow.getSatbUser();
			requestMap.put("requester", user.getName());
			returnMap.put(request.getCreditReviewRequestNo(), requestMap);
		}
		return returnMap;
	}
	public Map<Integer, Map<String, String>> getPendingSubscriptionRequests(){

		Map<Integer, Map<String, String>> returnMap = new HashMap<Integer, Map<String, String>>();
		List<AmtbSubscProdReq> requests = this.daoHelper.getAccountDao().getPendingSubscriptionRequests();
		for(AmtbSubscProdReq request : requests){
			Map<String, String> requestMap = new HashMap<String, String>();
			AmtbAccount acct = request.getAmtbAccount();
			AmtbAccount topAcct = getTopLevelAccount(acct);
			requestMap.put("custNo", topAcct.getCustNo());
			requestMap.put("acctName", topAcct.getAccountName());
			
			if(acct.getAccountCategory().equalsIgnoreCase(NonConfigurableConstants.ACCOUNT_CATEGORY_DIVISION))
				requestMap.put("divCodeName", acct.getCode() + " - "+acct.getAccountName() );
			else
				requestMap.put("divCodeName", "-" );
			if(acct.getAccountCategory().equalsIgnoreCase(NonConfigurableConstants.ACCOUNT_CATEGORY_DEPARTMENT))
			{
				AmtbAccount parentAcct = getAccountWithParent(request.getAmtbAccount());
				requestMap.put("divCodeName", parentAcct.getAmtbAccount().getCode() + " - "+parentAcct.getAmtbAccount().getAccountName());
				requestMap.put("deptCodeName", acct.getCode() + " - "+acct.getAccountName() );
			}else
				requestMap.put("deptCodeName", "-");
			
			requestMap.put("prdType", request.getPmtbProductType().getName());
			
			if(request.getSubscAction().equals(NonConfigurableConstants.SUBSCRIPTION_STATUS_UNSUBSCRIBE) || request.getSubscAction().equals(NonConfigurableConstants.SUBSCRIPTION_STATUS_UNSUBSCRIBE_DIV_DEPT))
			{
				AmtbSubscTo unsubscribePlan = this.daoHelper.getAccountDao().getUnsubscribePlans(request.getAmtbAccount().getAccountNo(), request.getPmtbProductType().getProductTypeId());
				requestMap.put("action", "Unsubscribe");
				
				if(null != unsubscribePlan)
				{
					if(null != unsubscribePlan.getMstbProdDiscMaster())
						requestMap.put("prdDisc", unsubscribePlan.getMstbProdDiscMaster().getProductDiscountPlanName());
					else
						requestMap.put("prdDisc", "-");
					if(null != unsubscribePlan.getLrtbRewardMaster())
						requestMap.put("loyaltyPlan", unsubscribePlan.getLrtbRewardMaster().getRewardPlanName());
					else
						requestMap.put("loyaltyPlan", "-");
					if(null != unsubscribePlan.getMstbSubscFeeMaster())
						requestMap.put("subscPlan", unsubscribePlan.getMstbSubscFeeMaster().getSubscriptionFeeName());
					else
						requestMap.put("subscPlan", "-");
					if( null != unsubscribePlan.getMstbIssuanceFeeMaster())
						requestMap.put("issuancePlan", unsubscribePlan.getMstbIssuanceFeeMaster().getIssuanceFeeName());
					else
						requestMap.put("issuancePlan", "-");
				}
				else
				{
					requestMap.put("prodDisc", "-");
					requestMap.put("loyaltyPlan", "-");
					requestMap.put("subscPlan", "-");
					requestMap.put("issuancePlan", "-");
				}
			}
			else
			{
				requestMap.put("action", "Subscribe");
				if(null != request.getMstbProdDiscMaster())
					requestMap.put("prdDisc", request.getMstbProdDiscMaster().getProductDiscountPlanName());
				else 
					requestMap.put("prdDisc", "-");
				if(null != request.getLrtbRewardMaster())
					requestMap.put("loyaltyPlan", request.getLrtbRewardMaster().getRewardPlanName());
				else
					requestMap.put("loyaltyPlan", "-");
				if(null != request.getMstbSubscFeeMaster())
					requestMap.put("subscPlan", request.getMstbSubscFeeMaster().getSubscriptionFeeName());
				else
					requestMap.put("subscPlan", "-");
				if(null != request.getMstbIssuanceFeeMaster())
					requestMap.put("issuancePlan", request.getMstbIssuanceFeeMaster().getIssuanceFeeName());
				else
					requestMap.put("issuancePlan", "-");
			}
			SatbUser reqBy = this.daoHelper.getUserDao().getUserWithRoles(request.getReqBy());
			requestMap.put("reqBy", reqBy.getName());
			requestMap.put("reqDate", DateUtil.convertTimestampToStr(request.getReqDt(), DateUtil.GLOBAL_DATE_FORMAT));
			returnMap.put(request.getSubProdReqNo() , requestMap);
		}
		return returnMap;
	}
	public Map<String, String> getPendingCreditReviewRequest(Integer requestId){
		AmtbCredRevReq request = this.daoHelper.getAccountDao().getPendingCreditReviewRequest(requestId);
		Map<String, String> returnMap = new HashMap<String, String>();
		AmtbAccount acct = request.getAmtbAccount();
		returnMap.put("acctName", acct.getAccountName());
		returnMap.put("reviewType", NonConfigurableConstants.REVIEW_TYPES.get(request.getCreditReviewType()));
		returnMap.put("creditLimit", StringUtil.bigDecimalToString(acct.getCreditLimit(), StringUtil.GLOBAL_DECIMAL_FORMAT));
		returnMap.put("creditBalance", StringUtil.bigDecimalToString(acct.getCreditBalance(), StringUtil.GLOBAL_DECIMAL_FORMAT));
		returnMap.put("newCreditLimit", StringUtil.bigDecimalToString(request.getNewCreditLimit(), StringUtil.GLOBAL_DECIMAL_FORMAT));
		returnMap.put("effectiveDateFrom", DateUtil.convertTimestampToStr(request.getEffectiveDtFrom(), DateUtil.GLOBAL_DATE_FORMAT));
		if(request.getEffectiveDtTo()!=null){
			returnMap.put("effectiveDateTo", DateUtil.convertTimestampToStr(request.getEffectiveDtTo(), DateUtil.GLOBAL_DATE_FORMAT));
		}else{
			returnMap.put("effectiveDateTo", "-");
		}
		AmtbCredRevReqFlow flow = request.getAmtbCredRevReqFlows().iterator().next();
		returnMap.put("reqDate", DateUtil.convertTimestampToStr(flow.getFlowDt(), DateUtil.GLOBAL_DATE_FORMAT));
		returnMap.put("remarks", flow.getRemarks());
		SatbUser user = flow.getSatbUser();
		returnMap.put("requester", user.getName());
		return returnMap;
	}
	private AmtbAcctCredLimit getFutureTempCreditLimit(Collection<AmtbAcctCredLimit> creditLimits){
		for(AmtbAcctCredLimit creditLimit : creditLimits){
			if(creditLimit.getCreditLimitType().equals(NonConfigurableConstants.CREDIT_LIMIT_TEMPORARY) && creditLimit.getEffectiveDtFrom().after(DateUtil.getCurrentTimestamp())){
				return creditLimit;
			}
		}
		return null;
	}
	private AmtbAcctCredLimit getCurrentTempCreditLimit(Collection<AmtbAcctCredLimit> creditLimits){
		for(AmtbAcctCredLimit creditLimit : creditLimits){
			logger.info("type = " + creditLimit.getCreditLimitType());
			logger.info("from = " + creditLimit.getEffectiveDtFrom());
			logger.info("to = " + creditLimit.getEffectiveDtTo());
			logger.info("test = " + (creditLimit.getCreditLimitType().equals(NonConfigurableConstants.CREDIT_LIMIT_TEMPORARY)
					&& creditLimit.getEffectiveDtFrom().before(DateUtil.getCurrentTimestamp())
					&& creditLimit.getEffectiveDtTo().after(DateUtil.getCurrentTimestamp())));
			if(creditLimit.getCreditLimitType().equals(NonConfigurableConstants.CREDIT_LIMIT_TEMPORARY)
					&& creditLimit.getEffectiveDtFrom().before(DateUtil.getCurrentTimestamp())
					&& creditLimit.getEffectiveDtTo().after(DateUtil.getCurrentTimestamp())){
				return creditLimit;
			}
		}
		return null;
	}
	public void approveCreditReviewRequest(List<Integer> requestIds, String remarks, String userId) throws Exception{
		SatbUser approver = this.daoHelper.getUserDao().getUserWithRoles(CommonWindow.getUserId());
		for(Integer requestId : requestIds){
			// getting the request
			AmtbCredRevReq request = this.daoHelper.getAccountDao().getPendingCreditReviewRequest(requestId);
			// credit limit
			AmtbAcctCredLimit creditLimit;
			// boolean to check whether it is a new status
			boolean newStatus = false;
			BigDecimal endTempCreditLimit = null;
			// getting all credit limit of an account
			List<AmtbAcctCredLimit> creditLimits = this.daoHelper.getAccountDao().getCreditLimits(request.getAmtbAccount().getAccountNo());
			// if the request is for temporary limit
			if(request.getCreditReviewType().equals(NonConfigurableConstants.CREDIT_LIMIT_TEMPORARY)){
			
				//#39 temp increase temporary limit check
				if(this.businessHelper.getAccountBusiness().hasTempChildFutureCreditLimit(request.getAmtbAccount().getCustNo(), null, null)) {
						this.businessHelper.getAccountBusiness().clearFutureChildCreditLimit(request.getAmtbAccount().getCustNo(), null, null, userId);
				}
				
				// getting the current temp credit that is in effective if any
				creditLimit = getCurrentTempCreditLimit(creditLimits);
				// if no temp credit limit found
				if(creditLimit == null){
					// getting the future credit limit
					creditLimit = getFutureTempCreditLimit(creditLimits);
					// if not found, create a new credit limit
					if(creditLimit == null){
						newStatus = true;
						creditLimit = new AmtbAcctCredLimit();
					}
				}else{// if current credit limit found
					// end the current credit limit and create a new credit limit
					endTempCreditLimit = creditLimit.getNewCreditLimit();
					creditLimit.setEffectiveDtTo(DateUtil.getCurrentTimestamp());
					this.update(creditLimit);
					newStatus = true;
					creditLimit = new AmtbAcctCredLimit();
				}
			}else{
				newStatus = true;
				creditLimit = new AmtbAcctCredLimit();
			}
			if(newStatus){
				creditLimit.setAmtbAccount(request.getAmtbAccount());
				creditLimit.setCreditLimitType(request.getCreditReviewType());
			}
			if(DateUtil.isToday(request.getEffectiveDtFrom())){
				creditLimit.setEffectiveDtFrom(DateUtil.getCurrentTimestamp());
			}else{
				creditLimit.setEffectiveDtFrom(request.getEffectiveDtFrom());
			}
			if(request.getEffectiveDtTo()!=null){
				creditLimit.setEffectiveDtTo(request.getEffectiveDtTo());
			}
			creditLimit.setNewCreditLimit(request.getNewCreditLimit());
			creditLimit.setRemarks(request.getRemarks());
			creditLimit.setSatbUser(approver);
			if(newStatus){
				this.save(creditLimit);
			}else{
				this.update(creditLimit);
			}
			AmtbAccount acct = request.getAmtbAccount();
			if(request.getEffectiveDtFrom().before(DateUtil.getCurrentTimestamp())){
				BigDecimal difference = null;
				BigDecimal currentPerm = acct.getCreditLimit();
				BigDecimal currentTemp = acct.getTempCreditLimit()!=null ? acct.getTempCreditLimit() : new BigDecimal(0);
				BigDecimal nextPerm = request.getCreditReviewType().equals(NonConfigurableConstants.CREDIT_LIMIT_PERMANENT) ? request.getNewCreditLimit() : currentPerm;
				BigDecimal nextTemp = request.getCreditReviewType().equals(NonConfigurableConstants.CREDIT_LIMIT_TEMPORARY) ? request.getNewCreditLimit() : currentTemp;
				BigDecimal currentBigger = null, nextBigger = null;
				if(currentPerm.doubleValue() < currentTemp.doubleValue()){
					currentBigger = currentTemp;
				}else{
					currentBigger = currentPerm;
				}
				if(nextPerm.doubleValue() < nextTemp.doubleValue()){
					nextBigger = nextTemp;
				}else{
					nextBigger = nextPerm;
				}
				logger.info("test currentPerm = " + currentPerm);
				logger.info("test currentTemp = " + currentTemp);
				logger.info("test nextPerm = " + nextPerm);
				logger.info("test nextTemp = " + nextTemp);
				logger.info("test currentBigger = " + currentBigger);
				logger.info("test nextBigger = " + nextBigger);
				difference = nextBigger.subtract(currentBigger);
				logger.info("test difference = " + difference);
				if(request.getCreditReviewType().equals(NonConfigurableConstants.CREDIT_LIMIT_TEMPORARY)){
					acct.setTempCreditLimit(request.getNewCreditLimit());
				}else{
					acct.setCreditLimit(request.getNewCreditLimit());
				}
				acct.setCreditBalance(acct.getCreditBalance().add(difference));
				this.update(acct);
				// updating credit balance to AS
				API.updateAccount(API.formulateAccountId(acct), acct.getCreditBalance().toString(), API.formulateParentAccountId(acct), userId, API.ASYNCHRONOUS);
			}
			AmtbCredRevReqFlow approveFlow = new AmtbCredRevReqFlow();
			approveFlow.setAmtbCredRevReq(request);
			approveFlow.setFlowDt(DateUtil.getCurrentTimestamp());
			approveFlow.setFromStatus(NonConfigurableConstants.CREDIT_REVIEW_REQUEST_STATUS_PENDING);
			approveFlow.setToStatus(NonConfigurableConstants.CREDIT_REVIEW_REQUEST_STATUS_APPROVED);
			approveFlow.setRemarks(remarks);
			approveFlow.setSatbUser(approver);
			this.save(approveFlow);
			// sending email
			String userName = "";
			List<String> toEmails = new ArrayList<String>();
			for(AmtbCredRevReqFlow flow : request.getAmtbCredRevReqFlows()){
				if(flow.getToStatus().equals(NonConfigurableConstants.CREDIT_REVIEW_REQUEST_STATUS_PENDING)){
					toEmails.add(flow.getSatbUser().getEmail());
					userName = flow.getSatbUser().getName();
					break;
				}
			}
			EmailUtil.sendEmail(toEmails.toArray(new String[]{}), new String[]{},
					ConfigurableConstants.getEmailText(ConfigurableConstants.EMAIL_TYPE_CREDIT_REQUEST_APPROVED, ConfigurableConstants.EMAIL_SUBJECT),
					ConfigurableConstants.getEmailText(ConfigurableConstants.EMAIL_TYPE_CREDIT_REQUEST_APPROVED, ConfigurableConstants.EMAIL_CONTENT)
					.replaceAll("#userName#", userName)
					.replaceAll("#custNo#", acct.getCustNo())
					.replaceAll("#acctName#", acct.getAccountName()));
		}
	}
	public void rejectCreditReviewRequest(List<Integer> requestIds, String remarks, String userId){
		SatbUser approver = this.daoHelper.getUserDao().getUserWithRoles(CommonWindow.getUserId());
		for(Integer requestId : requestIds){
			AmtbCredRevReq request = this.daoHelper.getAccountDao().getPendingCreditReviewRequest(requestId);
			AmtbCredRevReqFlow rejectFlow = new AmtbCredRevReqFlow();
			rejectFlow.setAmtbCredRevReq(request);
			rejectFlow.setFlowDt(DateUtil.getCurrentTimestamp());
			rejectFlow.setFromStatus(NonConfigurableConstants.CREDIT_REVIEW_REQUEST_STATUS_PENDING);
			rejectFlow.setToStatus(NonConfigurableConstants.CREDIT_REVIEW_REQUEST_STATUS_REJECTED);
			rejectFlow.setRemarks(remarks);
			rejectFlow.setSatbUser(approver);
			this.save(rejectFlow);
			// sending email
			String userName = "";
			List<String> toEmails = new ArrayList<String>();
			for(AmtbCredRevReqFlow flow : request.getAmtbCredRevReqFlows()){
				if(flow.getToStatus().equals(NonConfigurableConstants.CREDIT_REVIEW_REQUEST_STATUS_PENDING)){
					toEmails.add(flow.getSatbUser().getEmail());
					userName = flow.getSatbUser().getName();
					break;
				}
			}
			EmailUtil.sendEmail(toEmails.toArray(new String[]{}), new String[]{},
					ConfigurableConstants.getEmailText(ConfigurableConstants.EMAIL_TYPE_CREDIT_REQUEST_REJECTED, ConfigurableConstants.EMAIL_SUBJECT),
					ConfigurableConstants.getEmailText(ConfigurableConstants.EMAIL_TYPE_CREDIT_REQUEST_REJECTED, ConfigurableConstants.EMAIL_CONTENT)
					.replaceAll("#userName#", userName)
					.replaceAll("#custNo#", request.getAmtbAccount().getCustNo())
					.replaceAll("#acctName#", request.getAmtbAccount().getAccountName()));
		}
	}
	public Map<Integer, String> getAccounts(Integer salespersonNo, String industryCode){
		List<AmtbAccount> accts = this.daoHelper.getAccountDao().getAccountsBySalesperson(salespersonNo, industryCode);
		TreeSet<AmtbAccount> sortedAcct = new TreeSet<AmtbAccount>(new Comparator<AmtbAccount>(){
			public int compare(AmtbAccount o1, AmtbAccount o2) {
				return (o1.getAccountName() +" (" + o1.getCustNo() + ")").compareTo(o2.getAccountName() + " (" + o2.getCustNo() + ")");
			}
		});
		sortedAcct.addAll(accts);
		Map<Integer, String> returnMap = new LinkedHashMap<Integer, String>();
		for(AmtbAccount acct : sortedAcct){
			returnMap.put(acct.getAccountNo(), acct.getAccountName() + " (" + acct.getCustNo() + ")");
		}
		return returnMap;
	}
	public boolean hasFutureTransferAcct(Collection<Integer> accountNos){
		return this.daoHelper.getAccountDao().hasFutureTransferAcct(accountNos);
	}
	public void transferAccount(Integer fromSalesperson, Integer toSalesperson, Date effectiveDate, Collection<Integer> accountNos, String userId){
		this.daoHelper.getAccountDao().transferAcct(fromSalesperson, toSalesperson, effectiveDate, accountNos, this.daoHelper.getUserDao().getUserWithRoles(CommonWindow.getUserId()));
	}
	public Map<Integer, Map<String, String>> getAllAcctTransferReqs(){
		List<AmtbAcctSalespersonReq> requests = this.daoHelper.getAccountDao().getAllTransferAcctReqs();
		Map<Integer, Map<String, String>> returnMap = new HashMap<Integer, Map<String, String>>();
		for(AmtbAcctSalespersonReq request : requests){
			Map<String, String> requestMap = new HashMap<String, String>();
			requestMap.put("fromSalesperson", request.getMstbSalespersonByFromSalespersonNo().getName());
			requestMap.put("toSalesperson", request.getMstbSalespersonByToSalespersonNo().getName());
			requestMap.put("effectiveDate", DateUtil.convertTimestampToStr(request.getEffectiveDt(), DateUtil.GLOBAL_DATE_FORMAT));
			requestMap.put("count", ""+request.getAmtbAcctSalespersons().size());
			returnMap.put(request.getAcctSalespersonReqNo(), requestMap);
		}
		return returnMap;
	}
	public Map<String, Object> getAcctTransferReq(Integer requestNo){
		AmtbAcctSalespersonReq request = this.daoHelper.getAccountDao().getTransferAcctReq(requestNo);
		Map<String, Object> returnMap = new HashMap<String, Object>();
		returnMap.put("fromSalesperson", request.getMstbSalespersonByFromSalespersonNo().getName());
		returnMap.put("toSalesperson", request.getMstbSalespersonByToSalespersonNo().getName());
		returnMap.put("effectiveDate", request.getEffectiveDt());
		returnMap.put("requester", request.getSatbUser().getName());
		returnMap.put("reqDate", DateUtil.convertTimestampToStr(request.getRequestDt(), DateUtil.GLOBAL_DATE_FORMAT));
		returnMap.put("reqTime", DateUtil.convertTimestampToStr(request.getRequestDt(), DateUtil.GLOBAL_TIME_FORMAT));
		Map<String, String> acctMap = new HashMap<String, String>();
		for(AmtbAcctSalesperson acctSales : request.getAmtbAcctSalespersons()){
			acctMap.put(acctSales.getAmtbAccount().getCustNo(), acctSales.getAmtbAccount().getAccountName());
		}
		returnMap.put("accounts", acctMap);
		return returnMap;
	}
	public void deleteAcctTransferReq(Integer requestNo){
		this.daoHelper.getAccountDao().deleteTransferAcctReq(requestNo);
	}
	public List<Map<String, String>> getCreditReviews(String custNo, String subAcctNo, String parentCode, String code){
		AmtbAccount acct;
		if(subAcctNo!=null && subAcctNo.length()!=0){
			acct = getAccount(custNo, subAcctNo);
		}else{
			acct = getAccount(custNo, parentCode, code);
		}
		List<Map<String, String>> returnList = new ArrayList<Map<String, String>>();
		acct = this.daoHelper.getAccountDao().getAccountWithCreditDetails(acct.getAccountNo());
		// will only get first credit limit
		TreeSet<AmtbAcctCredLimit> sortedCreditLimits = new TreeSet<AmtbAcctCredLimit>(new Comparator<AmtbAcctCredLimit>(){
			public int compare(AmtbAcctCredLimit o1, AmtbAcctCredLimit o2) {
				return o1.getEffectiveDtFrom().compareTo(o2.getEffectiveDtFrom());
			}
		});
		sortedCreditLimits.addAll(acct.getAmtbAcctCredLimits());
		AmtbAcctCredLimit oldCreditLimit = null;
		for(AmtbAcctCredLimit creditLimit : sortedCreditLimits){
			Map<String, String> creditLimitMap = new HashMap<String, String>();
			creditLimitMap.put("type", "creditLimit");
			creditLimitMap.put("acctName", acct.getAccountName());
			creditLimitMap.put("reviewType", NonConfigurableConstants.REVIEW_TYPES.get(creditLimit.getCreditLimitType()));
			if(oldCreditLimit!=null){
				creditLimitMap.put("oldCreditLimit", StringUtil.bigDecimalToString(oldCreditLimit.getNewCreditLimit(), StringUtil.GLOBAL_DECIMAL_FORMAT));
			}else{
				creditLimitMap.put("oldCreditLimit", "-");
			}
			creditLimitMap.put("newCreditLimit", StringUtil.bigDecimalToString(creditLimit.getNewCreditLimit(), StringUtil.GLOBAL_DECIMAL_FORMAT));
			creditLimitMap.put("effectiveFrom", DateUtil.convertTimestampToStr(creditLimit.getEffectiveDtFrom(), DateUtil.GLOBAL_DATE_FORMAT));
			if(creditLimit.getEffectiveDtTo()!=null){
				creditLimitMap.put("effectiveTo", DateUtil.convertTimestampToStr(creditLimit.getEffectiveDtTo(), DateUtil.GLOBAL_DATE_FORMAT));
			}else{
				creditLimitMap.put("effectiveTo", "-");
			}
			creditLimitMap.put("approver", creditLimit.getSatbUser().getName());
			creditLimitMap.put("remarks", creditLimit.getRemarks());
			returnList.add(creditLimitMap);
			if(creditLimit.getCreditLimitType().equals(NonConfigurableConstants.CREDIT_LIMIT_PERMANENT)){
				oldCreditLimit = creditLimit;
			}
		}
		if(!acct.getAmtbCredRevReqs().isEmpty()){
			List<AmtbCredRevReq> sortedRequests = new ArrayList<AmtbCredRevReq>();
			sortedRequests.addAll(acct.getAmtbCredRevReqs());
			Collections.sort(sortedRequests, new Comparator<AmtbCredRevReq>(){
				public int compare(AmtbCredRevReq o1, AmtbCredRevReq o2) {
					return o1.getEffectiveDtFrom().compareTo(o2.getEffectiveDtFrom());
				}
			});
			for(AmtbCredRevReq request : sortedRequests){
				oldCreditLimit = null;
				for(AmtbAcctCredLimit creditLimit : sortedCreditLimits){
					if(creditLimit.getCreditLimitType().equals(NonConfigurableConstants.CREDIT_LIMIT_PERMANENT) && creditLimit.getEffectiveDtFrom().before(request.getEffectiveDtFrom())){
						oldCreditLimit = creditLimit;
					}
				}
				Map<String, String> requestMap = new HashMap<String, String>();
				requestMap.put("type", "creditReview");
				requestMap.put("acctName", acct.getAccountName());
				requestMap.put("reviewType", NonConfigurableConstants.REVIEW_TYPES.get(request.getCreditReviewType()));
				if(oldCreditLimit!=null){
					requestMap.put("oldCreditLimit", StringUtil.bigDecimalToString(oldCreditLimit.getNewCreditLimit(), StringUtil.GLOBAL_DECIMAL_FORMAT));
				}else{
					requestMap.put("oldCreditLimit", "-");
				}
				requestMap.put("newCreditLimit", StringUtil.bigDecimalToString(request.getNewCreditLimit(), StringUtil.GLOBAL_DECIMAL_FORMAT));
				requestMap.put("effectiveFrom", DateUtil.convertTimestampToStr(request.getEffectiveDtFrom(), DateUtil.GLOBAL_DATE_FORMAT));
				if(request.getEffectiveDtTo()!=null){
					requestMap.put("effectiveTo", DateUtil.convertTimestampToStr(request.getEffectiveDtTo(), DateUtil.GLOBAL_DATE_FORMAT));
				}else{
					requestMap.put("effectiveTo", "-");
				}
				requestMap.put("remarks", request.getRemarks());
				TreeSet<AmtbCredRevReqFlow> sortedFlow = new TreeSet<AmtbCredRevReqFlow>(new Comparator<AmtbCredRevReqFlow>(){
					public int compare(AmtbCredRevReqFlow o1, AmtbCredRevReqFlow o2) {
						return o1.getFlowDt().compareTo(o2.getFlowDt());
					}
				});
				sortedFlow.addAll(request.getAmtbCredRevReqFlows());
				if(sortedFlow.isEmpty()){
					requestMap.put("status", "-");
					requestMap.put("requester", "-");
				}else{
					requestMap.put("status", NonConfigurableConstants.CREDIT_REVIEW_REQUEST_STATUS.get(sortedFlow.last().getToStatus()));
					if(sortedFlow.last().getFromStatus().equals(NonConfigurableConstants.CREDIT_REVIEW_REQUEST_STATUS_PENDING)){
						requestMap.put("approver", sortedFlow.last().getSatbUser().getName());
					}else{
						requestMap.put("approver", "-");
					}
					requestMap.put("requester", sortedFlow.first().getSatbUser().getName());
				}
				returnList.add(requestMap);
			}
		}
		Collections.sort(returnList, new Comparator<Map<String, String>>(){
			public int compare(Map<String, String> o1, Map<String, String> o2) {
				return DateUtil.convertStrToDate(o2.get("effectiveFrom"), DateUtil.GLOBAL_DATE_FORMAT).compareTo(DateUtil.convertStrToDate(o1.get("effectiveFrom"), DateUtil.GLOBAL_DATE_FORMAT));
			}
		});
		return returnList;
	}
	public List<Map<String, String>> getStatuses(String custNo, String subAcctNo, String parentCode, String code){
		AmtbAccount acct;
		if(subAcctNo!=null && subAcctNo.length()!=0){
			acct = getAccount(custNo, subAcctNo);
		}else{
			acct = getAccount(custNo, parentCode, code);
		}
		List<Map<String, String>> returnList = new ArrayList<Map<String, String>>();
		List<AmtbAcctStatus> statuses = this.daoHelper.getAccountDao().getAccountStatus(acct.getAccountNo());
		TreeSet<AmtbAcctStatus> sortedStatuses = new TreeSet<AmtbAcctStatus>(new Comparator<AmtbAcctStatus>(){
			public int compare(AmtbAcctStatus o1, AmtbAcctStatus o2) {
				return o2.getEffectiveDt().compareTo(o1.getEffectiveDt());
			}
		});
		sortedStatuses.addAll(statuses);
		for(AmtbAcctStatus status : sortedStatuses){
			Map<String, String> statusMap = new HashMap<String, String>();
			if(status.getMstbMasterTable()!=null){
				statusMap.put("reason", status.getMstbMasterTable().getMasterValue());
			}else{
				statusMap.put("reason", "-");
			}
			statusMap.put("status", NonConfigurableConstants.ACCOUNT_STATUS.get(status.getAcctStatus()));
			statusMap.put("effectiveDate", DateUtil.convertTimestampToStr(status.getEffectiveDt(), DateUtil.GLOBAL_DATE_FORMAT));
			if(status.getSatbUser()!=null){
				statusMap.put("user", status.getSatbUser().getName());
			}else{
				statusMap.put("user", "-");
			}
			if(status.getStatusRemarks()!=null){
				statusMap.put("remarks", status.getStatusRemarks());
			}else{
				statusMap.put("remarks", "-");
			}
			statusMap.put("statusNo", status.getAcctStatusNo().toString());
			returnList.add(statusMap);
		}
		return returnList;
	}
	public void deleteStatus(Integer statusNo){
		this.delete(this.get(AmtbAcctStatus.class, statusNo));
	}
	public Map<Integer, Map<String, String>> getBillingChangeRequests(String custNo, String acctName, Date from, Date to, String status, String requester){
		List<AmtbBillReq> requests = this.daoHelper.getAccountDao().getBillingRequest(custNo, acctName, from, to, status, requester);
		Map<Integer, Map<String, String>> returnMap = new HashMap<Integer, Map<String,String>>();
		for(AmtbBillReq request : requests){
			Map<String, String> requestMap = new HashMap<String, String>();
			requestMap.put("custNo", request.getAmtbAccount().getCustNo());
			requestMap.put("acctName", request.getAmtbAccount().getAccountName());
			TreeSet<AmtbBillReqFlow> sortedFlows = new TreeSet<AmtbBillReqFlow>(new Comparator<AmtbBillReqFlow>(){
				public int compare(AmtbBillReqFlow o1, AmtbBillReqFlow o2) {
					return o1.getFlowDt().compareTo(o2.getFlowDt());
				}
			});
			sortedFlows.addAll(request.getAmtbBillReqFlows());
			requestMap.put("requester", sortedFlows.first().getSatbUser().getName());
			requestMap.put("reqDate", DateUtil.convertTimestampToStr(request.getRequestDt(), DateUtil.GLOBAL_DATE_FORMAT));
			if(sortedFlows.size()!=1){
				requestMap.put("approver", sortedFlows.last().getSatbUser().getName());
				requestMap.put("approveDate", DateUtil.convertTimestampToStr(sortedFlows.last().getFlowDt(), DateUtil.GLOBAL_DATE_FORMAT));
			}else{
				requestMap.put("approver", "-");
				requestMap.put("approveDate", "-");
			}
			requestMap.put("status", NonConfigurableConstants.BILLING_REQUEST_STATUS.get(sortedFlows.last().getToStatus()));
			returnMap.put(request.getBillReqNo(), requestMap);
		}
		return returnMap;
	}
	public Map<Integer, Map<String, String>> getCreditReviewRequests(String custNo, String acctName, Date from, Date to, String status, String requester){
		List<AmtbCredRevReq> requests = this.daoHelper.getAccountDao().getCreditReviewRequest(custNo, acctName, from, to, status, requester);
		Map<Integer, Map<String, String>> returnMap = new HashMap<Integer, Map<String,String>>();
		for(AmtbCredRevReq request : requests){
			Map<String, String> requestMap = new HashMap<String, String>();
			requestMap.put("custNo", request.getAmtbAccount().getCustNo());
			requestMap.put("acctName", request.getAmtbAccount().getAccountName());
			TreeSet<AmtbCredRevReqFlow> sortedFlows = new TreeSet<AmtbCredRevReqFlow>(new Comparator<AmtbCredRevReqFlow>(){
				public int compare(AmtbCredRevReqFlow o1, AmtbCredRevReqFlow o2) {
					return o1.getFlowDt().compareTo(o2.getFlowDt());
				}
			});
			sortedFlows.addAll(request.getAmtbCredRevReqFlows());
			requestMap.put("requester", sortedFlows.first().getSatbUser().getName());
			requestMap.put("reqDate", DateUtil.convertTimestampToStr(sortedFlows.first().getFlowDt(), DateUtil.GLOBAL_DATE_FORMAT));
			if(sortedFlows.size()!=1){
				requestMap.put("approver", sortedFlows.last().getSatbUser().getName());
				requestMap.put("approveDate", DateUtil.convertTimestampToStr(sortedFlows.last().getFlowDt(), DateUtil.GLOBAL_DATE_FORMAT));
			}else{
				requestMap.put("approver", "-");
				requestMap.put("approveDate", "-");
			}
			requestMap.put("status", NonConfigurableConstants.CREDIT_REVIEW_REQUEST_STATUS.get(sortedFlows.last().getToStatus()));
			returnMap.put(request.getCreditReviewRequestNo(), requestMap);
		}
		return returnMap;
	}
	public Map<String, String> getCreditReviewRequest(Integer requestNo){
		AmtbCredRevReq request = this.daoHelper.getAccountDao().getCreditReviewRequest(requestNo);
		Map<String, String> returnMap = new HashMap<String, String>();
		returnMap.put("acctName", request.getAmtbAccount().getAccountName());
		returnMap.put("reviewType", NonConfigurableConstants.REVIEW_TYPES.get(request.getCreditReviewType()));
		TreeSet<AmtbCredRevReqFlow> sortedFlows = new TreeSet<AmtbCredRevReqFlow>(new Comparator<AmtbCredRevReqFlow>(){
			public int compare(AmtbCredRevReqFlow o1,AmtbCredRevReqFlow o2) {
				return o1.getFlowDt().compareTo(o2.getFlowDt());
			}
		});
		sortedFlows.addAll(request.getAmtbCredRevReqFlows());
		TreeSet<AmtbAcctCredLimit> sortedCreditLimit = new TreeSet<AmtbAcctCredLimit>(new Comparator<AmtbAcctCredLimit>(){
			public int compare(AmtbAcctCredLimit o1,AmtbAcctCredLimit o2) {
				return o1.getEffectiveDtFrom().compareTo(o2.getEffectiveDtFrom());
			}
		});
		for(AmtbAcctCredLimit creditLimit : request.getAmtbAccount().getAmtbAcctCredLimits()){
			if(creditLimit.getEffectiveDtFrom().before(sortedFlows.first().getFlowDt())){
				sortedCreditLimit.add(creditLimit);
			}
		}
		returnMap.put("oldCreditLimit", StringUtil.bigDecimalToString(sortedCreditLimit.last().getNewCreditLimit(), StringUtil.GLOBAL_DECIMAL_FORMAT));
		returnMap.put("creditBalance", StringUtil.bigDecimalToString(request.getAmtbAccount().getCreditBalance(), StringUtil.GLOBAL_DECIMAL_FORMAT));
		returnMap.put("newCreditLimit", StringUtil.bigDecimalToString(request.getNewCreditLimit(), StringUtil.GLOBAL_DECIMAL_FORMAT));
		returnMap.put("effectiveFrom", DateUtil.convertTimestampToStr(request.getEffectiveDtFrom(), DateUtil.GLOBAL_DATE_FORMAT));
		if(request.getEffectiveDtTo()!=null){
			returnMap.put("effectiveTo", DateUtil.convertTimestampToStr(request.getEffectiveDtTo(), DateUtil.GLOBAL_DATE_FORMAT));
		}else{
			returnMap.put("effectiveTo", "-");
		}
		returnMap.put("requester", sortedFlows.first().getSatbUser().getName());
		returnMap.put("reqDate", DateUtil.convertTimestampToStr(sortedFlows.first().getFlowDt(), DateUtil.GLOBAL_DATE_FORMAT));
		returnMap.put("reqRemarks", sortedFlows.first().getRemarks());
		if(sortedFlows.size()!=1){
			returnMap.put("approver", sortedFlows.last().getSatbUser().getName());
			returnMap.put("approveDate", DateUtil.convertTimestampToStr(sortedFlows.last().getFlowDt(), DateUtil.GLOBAL_DATE_FORMAT));
			returnMap.put("approveRemarks", sortedFlows.last().getRemarks());
		}else{
			returnMap.put("approver", "-");
			returnMap.put("approveDate", "-");
			returnMap.put("approveRemarks", "-");
		}
		returnMap.put("status", NonConfigurableConstants.CREDIT_REVIEW_REQUEST_STATUS.get(sortedFlows.last().getToStatus()));
		return returnMap;
	}
	public boolean hasPermCreditLimit(String custNo, String parentCode, String code, Date effectiveDate){
		return this.daoHelper.getAccountDao().hasPermCreditLimit(getAccount(custNo, parentCode, code).getAccountNo(), effectiveDate);
	}
	public boolean hasPermCreditLimit(String custNo, String subAcctNo, Date effectiveDate){
		return this.daoHelper.getAccountDao().hasPermCreditLimit(getAccount(custNo, subAcctNo).getAccountNo(), effectiveDate);
	}
	public Map<String, Integer> getAccountMainContact(String custNo){
		AmtbAccount acct = this.daoHelper.getAccountDao().getAccountMainContacts(custNo);
		Map<String, Integer> returnMap = new HashMap<String, Integer>();
		for(AmtbAcctMainContact mainContact : acct.getAmtbAcctMainContacts()){
			if(mainContact.getComp_id().getMainContactType().equals(NonConfigurableConstants.MAIN_CONTACT_TYPE_BILLING)){
				returnMap.put("mainBilling", mainContact.getAmtbContactPerson().getContactPersonNo());
			}else if(mainContact.getComp_id().getMainContactType().equals(NonConfigurableConstants.MAIN_CONTACT_TYPE_SHIPPING)){
				returnMap.put("mainShipping", mainContact.getAmtbContactPerson().getContactPersonNo());
			}
		}
		return returnMap;
	}
	public Map<String, String> getCorporateAddress(String custNo){
		Map<String, String> returnMap = new HashMap<String, String>();
		AmtbCorporateDetail corpDetails = this.daoHelper.getAccountDao().getCorporateDetail(custNo);
		if(corpDetails!=null){
			returnMap.put("corpBlock", corpDetails.getAddressBlock());
			returnMap.put("corpUnit", corpDetails.getAddressUnit());
			returnMap.put("corpStreet", corpDetails.getAddressStreet());
			returnMap.put("corpBuilding", corpDetails.getAddressBuilding());
			returnMap.put("corpArea", corpDetails.getAddressArea());
			if(corpDetails.getMstbMasterTableByAddressCountry()!=null){
				returnMap.put("corpCountryCode", corpDetails.getMstbMasterTableByAddressCountry().getMasterCode());
			}
			returnMap.put("corpCity", corpDetails.getAddressCity());
			returnMap.put("corpState", corpDetails.getAddressState());
			returnMap.put("corpPostal", corpDetails.getAddressPostal());
		}
		return returnMap;
	}
	public List<AmtbAccount> searchAccount(SearchByAccountForm form) {
		return daoHelper.getAccountDao().getAccounts(form);
	}
	public AmtbContactPerson getMainContactByType(Integer accountNo,
			String contactType) {
		return daoHelper.getAccountDao().getMainContactByType(accountNo, contactType);
	}
	
	public AmtbAccount getAccountWithParent(AmtbAccount account) {
		return daoHelper.getAccountDao().getParent(account);
	}
	public AmtbAccount getTopLevelAccount(AmtbAccount account) {
		AmtbAccount topLevelAccount = account;
		if (account.getAmtbAccount() != null) {
			topLevelAccount = account.getAmtbAccount();
			if (account.getAmtbAccount().getAmtbAccount() != null) {
				topLevelAccount = account.getAmtbAccount().getAmtbAccount();
			}
		}
		return topLevelAccount;
	}
	public boolean isDebtCleared(String custNo){
		AmtbAccount account = this.daoHelper.getAccountDao().retrieveAccount(custNo);
		BigDecimal debts = this.daoHelper.getInvoiceDao().getTotalOutstandingAmount(account.getAccountNo());
		if(debts!=null && debts.compareTo(new BigDecimal(0))!=0) {
			return false;
		} else {
			return true;
		}
	}
	public BigDecimal getDepositAmount(String custNo){
		AmtbAccount account = this.daoHelper.getAccountDao().retrieveAccount(custNo);
		BigDecimal totalRequestAmount = this.daoHelper.getDepositDao().getTotalRequestTxnAmount(account);
		BigDecimal totalRefundAmount = this.daoHelper.getDepositDao().getTotalRefundTxnAmount(account);
		if(totalRequestAmount==null) {
			return new BigDecimal(0);
		} else{
			if(totalRefundAmount!=null) {
				return totalRequestAmount.subtract(totalRefundAmount);
			} else {
				return totalRequestAmount;
			}
		}
	}
	public Long generateMemoRefundForDeposit(String custNo, BigDecimal depositAmount, String userId) throws Exception{
		AmtbAccount account = this.daoHelper.getAccountDao().retrieveAccount(custNo);
		if(account==null) {
			throw new Exception("Unable to retrieve account using customer no:"+custNo);
		}

		BmtbPaymentReceipt paymentReceipt = new BmtbPaymentReceipt();
		paymentReceipt.setAmtbAccount(account);
		paymentReceipt.setMstbMasterTableByPaymentMode(ConfigurableConstants.getMasterTable(ConfigurableConstants.PAYMENT_MODE, NonConfigurableConstants.PAYMENT_MODE_MEMO));
		paymentReceipt.setPaymentAmount(depositAmount);
		paymentReceipt.setExcessAmount(new BigDecimal(0));
		paymentReceipt.setPaymentDate(DateUtil.getCurrentDate());
		paymentReceipt.setReceiptDt(DateUtil.getCurrentTimestamp());
		//Receipt is not going to be reflected in the invoice
		paymentReceipt.setBilledFlag(NonConfigurableConstants.BOOLEAN_YES);
		paymentReceipt.setCancelBilledFlag(NonConfigurableConstants.BOOLEAN_NO);
		Long receiptNo = (Long)this.daoHelper.getGenericDao().save(paymentReceipt, userId);

		BmtbInvoiceDepositTxn refundDepositTxn = new BmtbInvoiceDepositTxn();
		refundDepositTxn.setAmtbAccount(account);
		refundDepositTxn.setTxnType(NonConfigurableConstants.DEPOSIT_TXN_TYPE_REFUND);
		refundDepositTxn.setAmount(depositAmount);
		refundDepositTxn.setTxnDate(DateUtil.getCurrentDate());
		refundDepositTxn.setBmtbPaymentReceipt(paymentReceipt);
		this.daoHelper.getGenericDao().save(refundDepositTxn, userId);

		return receiptNo;
	}
	public Map<String, String> getProductSubscription(String custNo, String productTypeId){
		AmtbSubscTo subscription = this.daoHelper.getAccountDao().getProductSubscription(custNo, productTypeId);
		Map<String, String> returnMap = new HashMap<String, String>();
		PmtbProductType productType = subscription.getComp_id().getPmtbProductType();
		returnMap.put("productType", productType.getName());
		returnMap.put("productTypeId", productType.getProductTypeId());
		returnMap.put("digits", ""+productType.getNumberOfDigit());
		returnMap.put("bin", productType.getBinRange());
		returnMap.put("subBin", productType.getSubBinRange());
		returnMap.put("issuable", productType.getIssuable());
		returnMap.put("nameOnCard", productType.getNameOnProduct());
		returnMap.put("batch", productType.getBatchIssue());
		returnMap.put("issueType", productType.getIssueType());
		returnMap.put("fixedValue", productType.getFixedValue());
		returnMap.put("creditLimit", productType.getCreditLimit());
		returnMap.put("luhn", productType.getLuhnCheck());
		returnMap.put("defaultStatus", productType.getDefaultCardStatus());
		returnMap.put("negativeFile", productType.getExternalCard());
		returnMap.put("otu", productType.getOneTimeUsage());
		returnMap.put("validity", productType.getValidityPeriod());
		returnMap.put("defaultExpiry", ""+productType.getDefaultValidPeriod());
		returnMap.put("replaceFee", StringUtil.bigDecimalToString(productType.getReplacementFees(), StringUtil.GLOBAL_DECIMAL_FORMAT));
		if(subscription.getLrtbRewardMaster()!=null){
			returnMap.put("rewards", subscription.getLrtbRewardMaster().getRewardPlanNo().toString());
		}
		if(subscription.getMstbProdDiscMaster()!=null){
			returnMap.put("productDiscount", subscription.getMstbProdDiscMaster().getProductDiscountPlanNo().toString());
		}
		if(subscription.getMstbSubscFeeMaster()!=null){
			returnMap.put("subscriptionFee", subscription.getMstbSubscFeeMaster().getSubscriptionFeeNo().toString());
		}
		if(subscription.getMstbIssuanceFeeMaster()!=null){
			returnMap.put("issuanceFee", subscription.getMstbIssuanceFeeMaster().getIssuanceFeeNo().toString());
		}
		return returnMap;
	}
	public void updateProductSubscription(String custNo, String productTypeId, MstbProdDiscMaster productDiscount, LrtbRewardMaster reward, MstbSubscFeeMaster subfee, MstbIssuanceFeeMaster issuance, AmtbSubscTo subscription){
		if(productDiscount!=null){
			subscription.setMstbProdDiscMaster(productDiscount);
		}else{
			subscription.setMstbProdDiscMaster(null);
		}
		if(reward!=null){
			subscription.setLrtbRewardMaster(reward);
		}else{
			subscription.setLrtbRewardMaster(null);
		}
		if(subfee!=null){
			subscription.setMstbSubscFeeMaster(subfee);
		}else{
			subscription.setMstbSubscFeeMaster(null);
		}
		if(issuance!=null){
			subscription.setMstbIssuanceFeeMaster(issuance);
		}else{
			subscription.setMstbIssuanceFeeMaster(null);
		}
		
		this.update(subscription);
		
		//rewards changes to have ripple effect
		List<AmtbAccount> amtbAccounts = this.daoHelper.getAccountDao().getChildAccountSubscribedToByTopLevelAccountNoAndProductTypeId(custNo, productTypeId);
		for(AmtbAccount account : amtbAccounts){
			AmtbSubscTo subscTo = account.getAmtbSubscTos().iterator().next();
			if(subscTo != null){
				if(productDiscount!=null)
					subscTo.setMstbProdDiscMaster(productDiscount);
				else
					subscTo.setMstbProdDiscMaster(null);
				if(reward!=null)
					subscTo.setLrtbRewardMaster(reward);
				else
					subscTo.setLrtbRewardMaster(null);
				if(subfee!=null)
					subscTo.setMstbSubscFeeMaster(subfee);
				else
					subscTo.setMstbSubscFeeMaster(null);
				if(issuance!=null)
					subscTo.setMstbIssuanceFeeMaster(issuance);
				else
					subscTo.setMstbIssuanceFeeMaster(null);
				this.update(subscTo);
			}
		}
	}
	public void updateProductSubscriptionApproval(String custNo, String productTypeId, Integer productDiscountId, Integer rewardId, Integer subscriptionId, Integer issuanceId){
		AmtbSubscTo subscription = this.daoHelper.getAccountDao().getProductSubscription(custNo, productTypeId);
		AmtbSubscProdReq reqApproveSubscription = new AmtbSubscProdReq();
		
		if(productDiscountId!=null){
			reqApproveSubscription.setMstbProdDiscMaster((MstbProdDiscMaster)MasterSetup.getProductDiscountManager().getMaster(productDiscountId));
		}else{
			reqApproveSubscription.setMstbProdDiscMaster(null);
		}
		if(rewardId!=null){
			reqApproveSubscription.setLrtbRewardMaster((LrtbRewardMaster)MasterSetup.getRewardsManager().getMaster(rewardId));
		}else{
			reqApproveSubscription.setLrtbRewardMaster(null);
		}
		if(subscriptionId!=null){
			reqApproveSubscription.setMstbSubscFeeMaster((MstbSubscFeeMaster) MasterSetup.getSubscriptionManager().getMaster(subscriptionId));
		}else{
			reqApproveSubscription.setMstbSubscFeeMaster(null);
		}
		if(issuanceId!=null){
			reqApproveSubscription.setMstbIssuanceFeeMaster((MstbIssuanceFeeMaster) MasterSetup.getIssuanceManager().getMaster(issuanceId));
		}else{
			reqApproveSubscription.setMstbIssuanceFeeMaster(null);
		}

		reqApproveSubscription.setAmtbAccount(subscription.getComp_id().getAmtbAccount());
		reqApproveSubscription.setPmtbProductType(subscription.getComp_id().getPmtbProductType());
		reqApproveSubscription.setEffectiveDt(subscription.getEffectiveDt());
		reqApproveSubscription.setReqDt(DateUtil.getCurrentTimestamp());
		reqApproveSubscription.setReqBy(CommonWindow.getUserId());
		reqApproveSubscription.setAppStatus(NonConfigurableConstants.SUBSCRIPTION_APPROVE_STATUS_PENDING_APPROVED);
		reqApproveSubscription.setSubscAction(NonConfigurableConstants.SUBSCRIPTION_STATUS_SUBSCRIBE_EDIT);
		reqApproveSubscription.setRemarks("");
		this.save(reqApproveSubscription);
		
		sendSubscriptionApprovalEmail(reqApproveSubscription);
		
	}
	public boolean hasIssuedProducts(String custNo, String code, String parentCode, int level, List<String> productTypes){
		return this.daoHelper.getAccountDao().hasIssuedProducts(custNo, code, parentCode, level, productTypes);
	}
	public boolean hasExternalCardSubscription(String productTypeId){
		logger.info("hasExternalCardSubscription(productTypeId = "+productTypeId+")");
		List<AmtbAccount> accts = this.daoHelper.getAccountDao().getAccountSubscribedToExternalCard(productTypeId);
		logger.info("accts = " + accts.size());
		if(accts.isEmpty()){
			return false;
		}
		for(AmtbAccount acct : accts){
			AmtbAcctStatus currentStatus = null;
			for(AmtbAcctStatus status : acct.getAmtbAcctStatuses()){
				if(status.getEffectiveDt().before(new Date())){
					if(currentStatus == null || currentStatus.getEffectiveDt().getTime() < status.getEffectiveDt().getTime()){
						currentStatus = status;
					}
				}
			}
			if(currentStatus!=null){
				if(!currentStatus.getAcctStatus().equals(NonConfigurableConstants.ACCOUNT_STATUS_CLOSED)){
					return true;
				}
			}
		}
		return false;
	}
	
	public AmtbAccount getAccountSubscribedToExternalCard(String productTypeId){
		logger.info("getAccountByExternalCard(productTypeId = "+productTypeId+")");
		List<AmtbAccount> accts = this.daoHelper.getAccountDao().getAccountSubscribedToExternalCardWithContacts(productTypeId);
		logger.info("accts = " + accts.size());
		if(accts.isEmpty()){
			return null;
		}
		for(AmtbAccount acct : accts){
			AmtbAcctStatus currentStatus = null;
			for(AmtbAcctStatus status : acct.getAmtbAcctStatuses()){
				if(status.getEffectiveDt().before(new Date())){
					if(currentStatus == null || currentStatus.getEffectiveDt().getTime() < status.getEffectiveDt().getTime()){
						currentStatus = status;
					}
				}
			}
			if(currentStatus!=null){
				if(!currentStatus.getAcctStatus().equals(NonConfigurableConstants.ACCOUNT_STATUS_CLOSED))
				{
					return acct;
				}
			}
		}
		return null;
	}
	
	public boolean checkPromotionOverlapping(String custNo, Integer promotionNo, Date fromDate, Date toDate){
		return this.daoHelper.getAccountDao().checkPromotionOverlapping(custNo, promotionNo, fromDate, toDate);
	}
	public Map<Integer, String> searchAccounts(String contactPersonNo, String contactPersonType){
		logger.info("searchAccounts(contactPersonNo = "+contactPersonNo+")");
		List<AmtbAccount> accts = this.daoHelper.getAccountDao().getAccountsByMainContactPerson(contactPersonNo, contactPersonType);
		Map<Integer, String> returnMap = new TreeMap<Integer, String>(new Comparator<Integer>(){
			public int compare(Integer o1, Integer o2) {
				return o1.compareTo(o2);
			}
		});
		for(AmtbAccount acct : accts){
			String code = (acct.getCustNo()!=null) ? acct.getCustNo() :  acct.getCode();
			
			returnMap.put(acct.getAccountNo(), acct.getAccountName() + "(" + acct.getAccountCategory() + ") (" + code + ")");
		}
		return returnMap;
	}
	public void transferContact(String fromContactPersonNo, String toContactPersonNo, List<Integer> acctNos, String userId, String contactPersonType){
		logger.info("fromContactPersonNo = "+fromContactPersonNo+", toContactPersonNo = "+toContactPersonNo);
		List<AmtbAccount> accts = this.daoHelper.getAccountDao().getAccountsByMainContactPerson(fromContactPersonNo, contactPersonType);
		AmtbContactPerson toContactPerson = this.daoHelper.getAccountDao().getContact(Integer.parseInt(toContactPersonNo));
		List<AmtbAcctMainContact> updateMainContacts = new ArrayList<AmtbAcctMainContact>();
		for(AmtbAccount acct : accts){
			if(acctNos.contains(acct.getAccountNo())){
				for(AmtbAcctMainContact mainContact : acct.getAmtbAcctMainContacts()){
					if(mainContact.getAmtbContactPerson().getContactPersonNo().equals(Integer.parseInt(fromContactPersonNo))){
						mainContact.setAmtbContactPerson(toContactPerson);
						updateMainContacts.add(mainContact);
					}
				}
			}
		}
		this.daoHelper.getAccountDao().updateAll(updateMainContacts, userId);
	}
	
	public List<AmtbAcctStatus> getStatuses(Integer accountNo){
		return this.daoHelper.getAccountDao().getStatuses(accountNo);
	}

	public List<AmtbContactPerson> getTransferContacts(String custNo, String contactPersonType){
		return this.daoHelper.getAccountDao().getTransferContacts(custNo, contactPersonType);
	}
	
	public boolean containGovtEInvoice(String custNo, Integer excludeAcctNo){
		
		AmtbAccount topAcct = this.daoHelper.getAccountDao().getAllAccountByCustNo(custNo);
		
		List<AmtbAccount> acctFlatList = flatenChildAccts(topAcct);
		boolean containInvoice = false;
		
		/*for(AmtbAccount acct: acctFlatList){
			logger.info("Flatten Account No: " + acct.getAccountNo());
		}*/
		for(AmtbAccount acct: acctFlatList){
			
			if(acct.getAccountNo().equals(excludeAcctNo)){
				logger.info("Excluded Account No: " + acct.getAccountNo());
				continue;
			}
			
			if(acct.getGovtEInvoiceFlag()!=null && !NonConfigurableConstants.GOVT_EINV_FLAG_NO.equals(acct.getGovtEInvoiceFlag())){
				containInvoice = true;
				logger.info("Account No: " + acct.getAccountNo() + " contain Govt e-Invoice");
				break;
			}
		}
		return containInvoice;

	}
	public boolean containPubbs(String custNo, Integer excludeAcctNo){
		
		AmtbAccount topAcct = this.daoHelper.getAccountDao().getAllAccountByCustNo(custNo);
		
		List<AmtbAccount> acctFlatList = flatenChildAccts(topAcct);
		boolean containPubbs = false;
		
		/*for(AmtbAccount acct: acctFlatList){
			logger.info("Flatten Account No: " + acct.getAccountNo());
		}*/
		for(AmtbAccount acct: acctFlatList){
			
			if(acct.getAccountNo().equals(excludeAcctNo)){
				logger.info("Excluded Account No: " + acct.getAccountNo());
				continue;
			}
			
			if(acct.getPubbsFlag()!=null && !NonConfigurableConstants.BOOLEAN_NO.equals(acct.getPubbsFlag())){
				containPubbs = true;
				logger.info("Account No: " + acct.getAccountNo() + " contain Pubbs Flag");
				break;
			}
		}
		return containPubbs;

	}
	public boolean containFi(String custNo, Integer excludeAcctNo){
		
		AmtbAccount topAcct = this.daoHelper.getAccountDao().getAllAccountByCustNo(custNo);
		
		List<AmtbAccount> acctFlatList = flatenChildAccts(topAcct);
		boolean containFi = false;
		
		/*for(AmtbAccount acct: acctFlatList){
			logger.info("Flatten Account No: " + acct.getAccountNo());
		}*/
		for(AmtbAccount acct: acctFlatList){
			
			if(acct.getAccountNo().equals(excludeAcctNo)){
				logger.info("Excluded Account No: " + acct.getAccountNo());
				continue;
			}
			
			if(acct.getFiFlag()!=null && !NonConfigurableConstants.BOOLEAN_NO.equals(acct.getFiFlag())){
				containFi = true;
				logger.info("Account No: " + acct.getAccountNo() + " contain Fi Flag");
				break;
			}
		}
		return containFi;

	}
	//recursive to flatten all children
	public List<AmtbAccount> flatenChildAccts(AmtbAccount acct){
		
		List<AmtbAccount> acctFlatList = new ArrayList<AmtbAccount>();
		acctFlatList.add(acct);
		Set<AmtbAccount> childAccts = acct.getAmtbAccounts();
		if(childAccts!=null && childAccts.size()>0){
			
			for(AmtbAccount childAcct: childAccts){
				
				acctFlatList.addAll(flatenChildAccts(childAcct));
			}
		}
		return acctFlatList;
	}
	
	
	public List<AmtbAcctCredTerm> getCreditTerms(Integer accountNo){
		
		return this.daoHelper.getAccountDao().getCreditTerms(accountNo);
		
	}
	
	public AmtbAcctCredTerm getEffectiveCreditTerm(Collection<AmtbAcctCredTerm> credTerms){
		
		if(credTerms!=null && !credTerms.isEmpty()){
			final Date currentDate = new Date();
			
			Ordering<AmtbAcctCredTerm> ascEffetiveDateOrder = Ordering.natural().onResultOf(new Function<AmtbAcctCredTerm, Date>() {
				public Date apply(AmtbAcctCredTerm credTerm) {
					return credTerm.getEffectiveDate();
				}
			});
			
			Predicate<AmtbAcctCredTerm> filter = new Predicate<AmtbAcctCredTerm>() {
		        public boolean apply(AmtbAcctCredTerm input) {
		            return input.getEffectiveDate().before(currentDate) || input.getEffectiveDate().equals(currentDate);
		        }
		    };
			
			List<AmtbAcctCredTerm> sortedCredTerms = ascEffetiveDateOrder.sortedCopy(Iterables.filter(credTerms, filter));
			
			return Iterables.getLast(sortedCredTerms);
		}
		return null;
	}
	
	
	
	
	public AmtbSubscTo getSubscribeTo(AmtbAccount account, PmtbProductType productType, Date runDate){
			
		return this.daoHelper.getAccountDao().getSubscribeTo(account, productType, runDate);
	}
	
	
	public List<AmtbAccount> getTopLevelAccountsWithEntity(String custNo,String name){
		return this.daoHelper.getAccountDao().getTopLevelAccountsWithEntity(custNo, name);
	}
	
	
	public AmtbAccount getAccountWithEntity(AmtbAccount amtbAccount){
		
		return this.daoHelper.getAccountDao().getAccountWithEntity(amtbAccount);
	}
	
	
	public List<AmtbAccount> getActiveChargeToParentDivisionAccounts(AmtbAccount topAcct){
		
		List<AmtbAccount> accounts = this.daoHelper.getAccountDao().getChargeToParentDivisionAccounts(topAcct);
		List<AmtbAccount> activeAcctList = Lists.newArrayList();
		if(accounts!=null){
			for(AmtbAccount account : accounts){
				
				AmtbAcctStatus accountStatus=getCurrentStatus(account.getAmtbAcctStatuses());
				if(accountStatus.getAcctStatus().equalsIgnoreCase(NonConfigurableConstants.ACCOUNT_STATUS_ACTIVE)){
					activeAcctList.add(account);
				}
			}
		}
		
		return activeAcctList;
	}
		
	
	public List<AmtbAccount> getActiveChargeToParentDepartmentAccounts(AmtbAccount divAcct){
		
		List<AmtbAccount> accounts = this.daoHelper.getAccountDao().getChargeToParentDepartmentAccounts(divAcct);
		List<AmtbAccount> activeAcctList = Lists.newArrayList();
		if(accounts!=null){
			for(AmtbAccount account : accounts){
				
				AmtbAcctStatus accountStatus=getCurrentStatus(account.getAmtbAcctStatuses());
				if(accountStatus.getAcctStatus().equalsIgnoreCase(NonConfigurableConstants.ACCOUNT_STATUS_ACTIVE)){
					activeAcctList.add(account);
				}
			}
		}
		
		return activeAcctList;
		
		
	}
	
	public AmtbAccount getAccountWithParent(String accountNo){
		return this.daoHelper.getAccountDao().getAccountWithParent(accountNo);
	}


	public void sendEmail(String custNo, String parentCode, String code, String subAcctNo, String suspendActivate, String errorMessage) {
		
		String content = "";
		String emailName = "";
		String emailAddress = "";
		String userName = (String) Sessions.getCurrent().getAttribute(Constants.USERNAME);
		String heading = "";
		String headingError = "";
		String successMsg = "Email successfully send to > ";
		String emailSubject = "";
		
		List<MstbMasterTable> mstbMasterTable = this.daoHelper.getAccountDao().getMstbtable_EMFSRA();
		for( MstbMasterTable mTable : mstbMasterTable)
		{
			//Get EmailSubj & Content
			if(mTable.getMasterCode().equalsIgnoreCase(ConfigurableConstants.EMAIL_SUBJECT) && mTable.getMasterType().equalsIgnoreCase(ConfigurableConstants.EMAIL_SUSPEND_REACTIVATE_ACCOUNT))
				emailSubject = mTable.getMasterValue();
		}
		
		if(suspendActivate.equalsIgnoreCase("R"))
		{
			heading = "reactivated";
			headingError = "reactivate";
			emailSubject = emailSubject.replaceAll("#suspendReactivate#", "Reactivation");
		}
		else if(suspendActivate.equalsIgnoreCase("S"))
		{
			heading = "suspended";
			headingError = "suspend";
			emailSubject = emailSubject.replaceAll("#suspendReactivate#", "Suspension");
		}
		
		try{
			AmtbAccount mainAcct = new AmtbAccount();
			mainAcct = getAccount(custNo, null , null);
			AmtbAcctSalesperson amtbAS = this.daoHelper.getAccountDao().getAccountMainSalesPerson(custNo);

			if(null != amtbAS)
			{
				emailName = amtbAS.getMstbSalesperson().getName();
				emailAddress = amtbAS.getMstbSalesperson().getEmail();
				successMsg += ""+emailAddress + " for "+headingError +". ";
			}
			else
				logger.info("No Sales Person Found for Cust No : "+custNo);
			
			if(!Strings.isNullOrEmpty(errorMessage))
			{
				content += "Encountered Errors while trying to "+headingError+" Account/Division/Department. <br/><br/>";
				content += errorMessage + " <br/>";
			}
			else
			{
				AmtbAccount acct = new AmtbAccount();
				
				if(code != null || parentCode != null) // Division/Department
					acct = getAccount(custNo, parentCode, code);
				else if(subAcctNo != null)
					acct = getAccount(custNo, subAcctNo);

				content += "Account No : "+mainAcct.getCustNo()+"<br/>";
				content += "Account Name : "+StringEscapeUtils.escapeHtml4(mainAcct.getAccountName())+" <br/>";
				successMsg += "Cust No. : "+mainAcct.getCustNo() +". ";
	
				if(!Strings.isNullOrEmpty(parentCode)){// department
	
					content += "Department Code : "+acct.getCode()+" <br/>";
					content += "Department Name : "+StringEscapeUtils.escapeHtml4(acct.getAccountName())+" <br/>";
					successMsg += "Department Code : "+acct.getCode()+". ";
				}
				else if(!Strings.isNullOrEmpty(code)){// division
				
					content += "Division Code : "+acct.getCode()+" <br/>";
					content += "Division Name : "+StringEscapeUtils.escapeHtml4(acct.getAccountName())+" <br/>";
					successMsg += "Division Code : "+acct.getCode()+". ";
				}
				else if(!Strings.isNullOrEmpty(subAcctNo))//sub application
				{
					content += "Sub Applicant Code : "+acct.getCode()+" <br/>";
					content += "Sub Applicant Name : "+StringEscapeUtils.escapeHtml4(acct.getAccountName())+" <br/>";
					successMsg += "Sub Applicant Code : "+acct.getCode()+". ";
				}
			}
			
			EmailUtil.sendEmailHtml( emailAddress , emailSubject, ""+ConfigurableConstants.getEmailText(ConfigurableConstants.EMAIL_SUSPEND_REACTIVATE_ACCOUNT, ConfigurableConstants.EMAIL_CONTENT)
					.replaceAll("#customerName#", emailName)
					.replaceAll("#table#", content)
					.replaceAll("#heading#", heading)
					.replaceAll("#userName#", userName));
			logger.info(successMsg);
		}catch (Exception e)
		{
			logger.info("Error in Sending Mail");
		}
	}
	
	public void updateInvoiceOverdue(String custNo, String parentCode, String code,  String wholeEmail, String overdueInvoiceReminder, String userId) throws Exception{
		AmtbAccount acct = getAccount(custNo, parentCode, code);
		acct.setOverdueReminder(overdueInvoiceReminder);
		acct.setReminderEmail(wholeEmail);
		this.update(acct, userId);
	}
	public void updateInvoiceOverdue(String custNo, String subAcctNo, String wholeEmail, String overdueInvoiceReminder, String userId) throws Exception{
		AmtbAccount acct = getAccount(custNo, subAcctNo);
		acct.setOverdueReminder(overdueInvoiceReminder);
		acct.setReminderEmail(wholeEmail);
		this.update(acct, userId);
	}

	// TODO: Product Subscription Approval
	public boolean hasPendingProductSubscriptionApproval(String custNo){
		AmtbAccount acct = this.daoHelper.getAccountDao().getAccountWithCreditDetails(custNo, 0, null, null);
		for(AmtbCredRevReq request : acct.getAmtbCredRevReqs()){
			TreeSet<AmtbCredRevReqFlow> sortedFlows = new TreeSet<AmtbCredRevReqFlow>(new Comparator<AmtbCredRevReqFlow>(){
				public int compare(AmtbCredRevReqFlow o1, AmtbCredRevReqFlow o2) {
					return o1.getFlowDt().compareTo(o2.getFlowDt());
				}
			});
			sortedFlows.addAll(request.getAmtbCredRevReqFlows());
			if(!sortedFlows.isEmpty()){
				if(sortedFlows.last().getToStatus().equals(NonConfigurableConstants.CREDIT_REVIEW_REQUEST_STATUS_PENDING)){
					return true;
				}
			}
		}
		return false;
	}
	
	
	public boolean hasPendApproveSubscriptionCorpSubAccount(Map<String, Object> acctDetails) {
		AmtbAccount acct = this.daoHelper.getAccountDao().getAccount(Integer.parseInt(acctDetails.get("acctNo").toString()));
		if(acctDetails.get("parentCode")!=null){
			// checking if different
			if(!acct.getAmtbAccount().getCode().equals(acctDetails.get("parentCode"))){
				AmtbAccount newParent = this.daoHelper.getAccountDao().getAccount((String)acctDetails.get("custNo"), NonConfigurableConstants.DIVISION_LEVEL, null, (String)acctDetails.get("parentCode"));
				acct.setAmtbAccount(newParent);
			}
		}
		// update product subscriptions
		List<String> prodSubscriptions = (List<String>)acctDetails.get("prodSubscriptions");
		AmtbAccount parentAcct = null;
		//checking product subscriptions that are added
		for(String prodSubscription : prodSubscriptions){
			boolean found = false;
			for(AmtbSubscTo subscription : acct.getAmtbSubscTos()){
				if(subscription.getComp_id().getPmtbProductType().getProductTypeId().equals(prodSubscription)){
					found = true;
					break;
				}
			}
			if(!found){
				if(parentAcct == null){
					parentAcct = this.daoHelper.getAccountDao().getAccount(acct.getAmtbAccount().getAccountNo());
				}
				for(AmtbSubscTo parentProdSubscription : parentAcct.getAmtbSubscTos()){
					PmtbProductType prodSubscribeProdType = parentProdSubscription.getComp_id().getPmtbProductType();
					if(prodSubscribeProdType.getProductTypeId().equals(prodSubscription)){
						List<AmtbSubscProdReq> prodReq = this.daoHelper.getAccountDao().getAccountPendApproveSubscription(acct.getAccountNo(), prodSubscribeProdType.getProductTypeId());
						if(!prodReq.isEmpty())
							return true;
						
						break;
					}
				}
			}
		}
		
		
		//checking product subscriptions that are removed
		List<AmtbSubscTo> deleted = new ArrayList<AmtbSubscTo>();
		List<String> deletedProdTypeId = new ArrayList<String>();
		for(AmtbSubscTo subscription : acct.getAmtbSubscTos()){
			boolean found = false;
			for(String prodSubscription : prodSubscriptions){
				if(prodSubscription.equals(subscription.getComp_id().getPmtbProductType().getProductTypeId())){
					found = true;
					break;
				}
			}
			if(!found){
				List<AmtbSubscProdReq> prodReq = this.daoHelper.getAccountDao().getAccountPendApproveSubscription(acct.getAccountNo(), subscription.getComp_id().getPmtbProductType().getProductTypeId());
				if(!prodReq.isEmpty())
					return true;				
				break;
			}
		}

		return false;
		
	}
	public Map<String, String> getPendingSubscriptionRequest(Integer requestId){
		AmtbSubscProdReq request = this.daoHelper.getAccountDao().getPendingSubscriptionRequest(requestId);
		Map<String, String> returnMap = new HashMap<String, String>();
		AmtbAccount topAcct = getTopLevelAccount(request.getAmtbAccount());
		
		returnMap.put("acctNo", topAcct.getCustNo().toString());
		returnMap.put("acctName", topAcct.getAccountName());
		returnMap.put("prodType", request.getPmtbProductType().getName());
		
		if(request.getAmtbAccount().getAccountCategory().equalsIgnoreCase(NonConfigurableConstants.ACCOUNT_CATEGORY_DIVISION)) {
			returnMap.put("divCode", request.getAmtbAccount().getCode());
			returnMap.put("divName", request.getAmtbAccount().getAccountName());
		}
		else {
			returnMap.put("divCode", "-");
			returnMap.put("divName", "-");
		}
		if(request.getAmtbAccount().getAccountCategory().equalsIgnoreCase(NonConfigurableConstants.ACCOUNT_CATEGORY_DEPARTMENT)) {
			AmtbAccount parentAcct = getAccountWithParent(request.getAmtbAccount());
			
			returnMap.put("divCode", parentAcct.getAmtbAccount().getCode());
			returnMap.put("divName", parentAcct.getAmtbAccount().getAccountName());
			returnMap.put("deptCode", request.getAmtbAccount().getCode());
			returnMap.put("deptName", request.getAmtbAccount().getAccountName());
		}
		else {
			returnMap.put("deptCode", "-");
			returnMap.put("deptName", "-");
		}
		if(request.getAmtbAccount().getAccountCategory().equalsIgnoreCase(NonConfigurableConstants.ACCOUNT_CATEGORY_APPLICANT))
		{
			returnMap.put("subAppln", "subAppln");
			returnMap.put("deptCode", "-");
			returnMap.put("deptName", "-");
		}
		if(request.getAmtbAccount().getAccountCategory().equalsIgnoreCase(NonConfigurableConstants.ACCOUNT_CATEGORY_SUB_APPLICANT))
		{
			returnMap.put("divCode", request.getAmtbAccount().getCode());
			returnMap.put("divName", request.getAmtbAccount().getAccountName());
			
			returnMap.put("subAppln", "subAppln");
		}
		
		if(request.getSubscAction().equals(NonConfigurableConstants.SUBSCRIPTION_STATUS_UNSUBSCRIBE) || request.getSubscAction().equals(NonConfigurableConstants.SUBSCRIPTION_STATUS_UNSUBSCRIBE_DIV_DEPT))
		{
			AmtbSubscTo unsubscribePlan = this.daoHelper.getAccountDao().getUnsubscribePlans(request.getAmtbAccount().getAccountNo(), request.getPmtbProductType().getProductTypeId());
			returnMap.put("action", "UNSUBSCRIBE");
			
			if(null != unsubscribePlan)
			{
				if(null != unsubscribePlan.getMstbProdDiscMaster())
					returnMap.put("prodDisc", unsubscribePlan.getMstbProdDiscMaster().getProductDiscountPlanName());
				else
					returnMap.put("prodDisc", "-");
				if(null != unsubscribePlan.getLrtbRewardMaster())
					returnMap.put("loyaltyPlan", unsubscribePlan.getLrtbRewardMaster().getRewardPlanName());
				else
					returnMap.put("loyaltyPlan", "-");
				if(null != unsubscribePlan.getMstbSubscFeeMaster())
					returnMap.put("subscPlan", unsubscribePlan.getMstbSubscFeeMaster().getSubscriptionFeeName());
				else
					returnMap.put("subscPlan", "-");
				if( null != unsubscribePlan.getMstbIssuanceFeeMaster())
					returnMap.put("issuancePlan", unsubscribePlan.getMstbIssuanceFeeMaster().getIssuanceFeeName());
				else
					returnMap.put("issuancePlan", "-");
			}
			else
			{
				returnMap.put("prodDisc", "-");
				returnMap.put("loyaltyPlan", "-");
				returnMap.put("subscPlan", "-");
				returnMap.put("issuancePlan", "-");
			}
			returnMap.put("prodDiscRed", "red");
			returnMap.put("loyaltyPlanRed", "red");
			returnMap.put("subscPlanRed", "red");
			returnMap.put("issuancePlanRed", "red");
		}
		else
		{
			returnMap.put("action", "SUBSCRIBE");
			if(null != request.getMstbProdDiscMaster())
				returnMap.put("prodDisc", request.getMstbProdDiscMaster().getProductDiscountPlanName());
			else 
				returnMap.put("prodDisc", "-");
			if(null != request.getLrtbRewardMaster())
				returnMap.put("loyaltyPlan", request.getLrtbRewardMaster().getRewardPlanName());
			else
				returnMap.put("loyaltyPlan", "-");
			if(null != request.getMstbSubscFeeMaster())
				returnMap.put("subscPlan", request.getMstbSubscFeeMaster().getSubscriptionFeeName());
			else
				returnMap.put("subscPlan", "-");
			if(null != request.getMstbIssuanceFeeMaster())
				returnMap.put("issuancePlan", request.getMstbIssuanceFeeMaster().getIssuanceFeeName());
			else
				returnMap.put("issuancePlan", "-");

			//Check differences so to highlight red
			AmtbSubscTo requestOriginal = this.daoHelper.getAccountDao().getSubscribeTo(request.getAmtbAccount(), request.getPmtbProductType(), new Date());

			if(null != requestOriginal)
			{
				if( (null != request.getMstbProdDiscMaster() && null == requestOriginal.getMstbProdDiscMaster())
					 || (null == request.getMstbProdDiscMaster() && null != requestOriginal.getMstbProdDiscMaster()))
						returnMap.put("prodDiscRed", "red");
				else if((request.getMstbProdDiscMaster() != requestOriginal.getMstbProdDiscMaster()))
						returnMap.put("prodDiscRed", "red");
				if( (null != request.getLrtbRewardMaster() && null == requestOriginal.getLrtbRewardMaster())
						 || (null == request.getLrtbRewardMaster() && null != requestOriginal.getLrtbRewardMaster()))
						returnMap.put("loyaltyPlanRed", "red");
				else if((request.getLrtbRewardMaster() != requestOriginal.getLrtbRewardMaster()))
						returnMap.put("loyaltyPlanRed", "red");
				if( (null != request.getMstbSubscFeeMaster() && null == requestOriginal.getMstbSubscFeeMaster())
						 || (null == request.getMstbSubscFeeMaster() && null != requestOriginal.getMstbSubscFeeMaster()))
						returnMap.put("subscPlanRed", "red");
				else if((request.getMstbSubscFeeMaster() != requestOriginal.getMstbSubscFeeMaster()))
						returnMap.put("subscPlanRed", "red");
				if( (null != request.getMstbIssuanceFeeMaster() && null == requestOriginal.getMstbIssuanceFeeMaster())
						 || (null == request.getMstbIssuanceFeeMaster() && null != requestOriginal.getMstbIssuanceFeeMaster()))
						returnMap.put("issuancePlanRed", "red");
				else if((request.getMstbIssuanceFeeMaster() != requestOriginal.getMstbIssuanceFeeMaster()))
						returnMap.put("issuancePlanRed", "red");
			}
			else
			{
				returnMap.put("prodDiscRed", "red");
				returnMap.put("loyaltyPlanRed", "red");
				returnMap.put("subscPlanRed", "red");
				returnMap.put("issuancePlanRed", "red");
			}
		}
		SatbUser reqBy = this.daoHelper.getUserDao().getUserWithRoles(request.getReqBy());
		returnMap.put("requester", reqBy.getName());
		returnMap.put("reqDate", DateUtil.convertTimestampToStr(request.getReqDt(), DateUtil.GLOBAL_DATE_FORMAT));
		returnMap.put("reqStatus", NonConfigurableConstants.SUBSCRIPTION_APPROVE_STATUS.get(request.getAppStatus()));
		
		if(null != request.getApproveBy())
		{
			SatbUser appBy = this.daoHelper.getUserDao().getUserWithRoles(request.getApproveBy());
			returnMap.put("appBy", appBy.getName());
			returnMap.put("appDt", DateUtil.convertTimestampToStr(request.getApproveDt(), DateUtil.GLOBAL_DATE_FORMAT));
			
			if(null != request.getRemarks())
				returnMap.put("appRemarks", request.getRemarks());
			else
				returnMap.put("appRemarks", "-");
		}
		else
		{
			returnMap.put("appBy", "-");
			returnMap.put("appDt", "-");
			returnMap.put("appRemarks", "-");
		}
		
		return returnMap;
	}
	public void rejectSubscriptionRequest(List<Integer> requestIds, String remarks){
		for(Integer requestId : requestIds){
			AmtbSubscProdReq request = this.daoHelper.getAccountDao().getPendingSubscriptionRequest(requestId);
			request.setAppStatus(NonConfigurableConstants.CREDIT_REVIEW_REQUEST_STATUS_REJECTED);
			request.setApproveDt(DateUtil.getCurrentTimestamp());
			request.setApproveBy(CommonWindow.getUserId());
			request.setRemarks(remarks);
			this.save(request);
			// sending email
			List<String> toEmails = new ArrayList<String>();
			
			SatbUser reqBy = this.daoHelper.getUserDao().getUserWithRoles(request.getReqBy());
			toEmails.add(reqBy.getEmail());
			
			AmtbAccount topAccount = getTopLevelAccount(request.getAmtbAccount());
			String divCode = "-";
			String divName = "-";
			String deptCode = "-";
			String deptName = "-";
			String subscription = "Subscribe";
			
			if(request.getSubscAction().equals(NonConfigurableConstants.SUBSCRIPTION_STATUS_UNSUBSCRIBE) || request.getSubscAction().equals(NonConfigurableConstants.SUBSCRIPTION_STATUS_UNSUBSCRIBE_DIV_DEPT))
				subscription = "Unsubscribe";
			
			if(request.getAmtbAccount().getAccountCategory().equalsIgnoreCase(NonConfigurableConstants.ACCOUNT_CATEGORY_DIVISION))
			{
				divCode = request.getAmtbAccount().getCode();
				divName = request.getAmtbAccount().getAccountName();
			}
			else if(request.getAmtbAccount().getAccountCategory().equalsIgnoreCase(NonConfigurableConstants.ACCOUNT_CATEGORY_DEPARTMENT))
			{
				deptCode = request.getAmtbAccount().getCode();
				deptName = request.getAmtbAccount().getAccountName();
				divCode = request.getAmtbAccount().getAmtbAccount().getCode();
				divName = request.getAmtbAccount().getAmtbAccount().getAccountName();
			}
			
			EmailUtil.sendEmail(toEmails.toArray(new String[]{}), new String[]{},
					ConfigurableConstants.getEmailText(ConfigurableConstants.EMAIL_TYPE_SUBSCRIPTION_REQUEST_REJECTED, ConfigurableConstants.EMAIL_SUBJECT)
					.replaceAll("#subscribe#", subscription),
					ConfigurableConstants.getEmailText(ConfigurableConstants.EMAIL_TYPE_SUBSCRIPTION_REQUEST_REJECTED, ConfigurableConstants.EMAIL_CONTENT)
					.replaceAll("#userName#", reqBy.getName())
					.replaceAll("#custNo#", topAccount.getCustNo())
					.replaceAll("#acctName#", topAccount.getAccountName())
					.replaceAll("#divCode#", divCode)
					.replaceAll("#divName#", divName)
					.replaceAll("#deptCode#", deptCode)
					.replaceAll("#prodType#", request.getPmtbProductType().getName())
					.replaceAll("#deptName#", deptName));
		}
	}
	public void approveSubscriptionRequest(List<Integer> requestIds, String remarks) throws Exception{
		SatbUser approver = this.daoHelper.getUserDao().getUserWithRoles(CommonWindow.getUserId());
		for(Integer requestId : requestIds){
			// getting the request
			AmtbSubscProdReq request = this.daoHelper.getAccountDao().getPendingSubscriptionRequest(requestId);
			request.setAppStatus(NonConfigurableConstants.CREDIT_REVIEW_REQUEST_STATUS_APPROVED);
			request.setApproveDt(DateUtil.getCurrentTimestamp());
			request.setApproveBy(CommonWindow.getUserId());
			request.setRemarks(remarks);
			this.save(request);
			
			//Subscribe
			AmtbSubscTo amtbSubscTo = this.daoHelper.getAccountDao().getSubscribeTo(request.getAmtbAccount(), request.getPmtbProductType(), new Date());
		
			//Check Unsubscribe or subscribe
			if(request.getSubscAction().trim().equalsIgnoreCase(NonConfigurableConstants.SUBSCRIPTION_STATUS_UNSUBSCRIBE)) {
				//Unsubscribe @ corp/pers
				unsubscribeProductType(request.getAmtbAccount(), request.getPmtbProductType(), amtbSubscTo);
			}
			else if(request.getSubscAction().trim().equalsIgnoreCase(NonConfigurableConstants.SUBSCRIPTION_STATUS_SUBSCRIBE_EDIT)) {
				//Edit @ corp/pers
				updateProductSubscription(amtbSubscTo.getComp_id().getAmtbAccount().getCustNo(), request.getPmtbProductType().getProductTypeId(), request.getMstbProdDiscMaster(), request.getLrtbRewardMaster(), request.getMstbSubscFeeMaster(), request.getMstbIssuanceFeeMaster(), amtbSubscTo);
			}
			else if(request.getSubscAction().trim().equalsIgnoreCase(NonConfigurableConstants.SUBSCRIPTION_STATUS_SUBSCRIBE)) {
				//Subscribe @ corp/pers
				addProductSubscription(request.getAmtbAccount(), request.getPmtbProductType(), request.getMstbProdDiscMaster(), request.getLrtbRewardMaster(), request.getMstbSubscFeeMaster(), request.getMstbIssuanceFeeMaster(), request.getEffectiveDt());
			}
			else if (request.getSubscAction().trim().equalsIgnoreCase(NonConfigurableConstants.SUBSCRIPTION_STATUS_SUBSCRIBE_DIV_DEPT) || request.getSubscAction().trim().equalsIgnoreCase(NonConfigurableConstants.SUBSCRIPTION_STATUS_UNSUBSCRIBE_DIV_DEPT)) {
				// Sub/unSub @ div/dept & subpers
				updateCorpSubAccountSubscription(request.getAmtbAccount(),request, amtbSubscTo, request.getSubscAction());
			}
			
		
			List<String> toEmails = new ArrayList<String>();
			
			SatbUser reqBy = this.daoHelper.getUserDao().getUserWithRoles(request.getReqBy());
			toEmails.add(reqBy.getEmail());
			
			AmtbAccount topAccount = getTopLevelAccount(request.getAmtbAccount());
			String divCode = "-";
			String divName = "-";
			String deptCode = "-";
			String deptName = "-";
			String subscription = "Subscribe";
			
			if(request.getSubscAction().equals(NonConfigurableConstants.SUBSCRIPTION_STATUS_UNSUBSCRIBE) || request.getSubscAction().equals(NonConfigurableConstants.SUBSCRIPTION_STATUS_UNSUBSCRIBE_DIV_DEPT))
				subscription = "Unsubscribe";
			
			if(request.getAmtbAccount().getAccountCategory().equalsIgnoreCase(NonConfigurableConstants.ACCOUNT_CATEGORY_DIVISION))
			{
				divCode = request.getAmtbAccount().getCode();
				divName = request.getAmtbAccount().getAccountName();
			}
			else if(request.getAmtbAccount().getAccountCategory().equalsIgnoreCase(NonConfigurableConstants.ACCOUNT_CATEGORY_DEPARTMENT))
			{
				deptCode = request.getAmtbAccount().getCode();
				deptName = request.getAmtbAccount().getAccountName();
				divCode = request.getAmtbAccount().getAmtbAccount().getCode();
				divName = request.getAmtbAccount().getAmtbAccount().getAccountName();
			}
			
			EmailUtil.sendEmail(toEmails.toArray(new String[]{}), new String[]{},
					ConfigurableConstants.getEmailText(ConfigurableConstants.EMAIL_TYPE_SUBSCRIPTION_REQUEST_APPROVED, ConfigurableConstants.EMAIL_SUBJECT)
					.replaceAll("#subscribe#", subscription),
					ConfigurableConstants.getEmailText(ConfigurableConstants.EMAIL_TYPE_SUBSCRIPTION_REQUEST_APPROVED, ConfigurableConstants.EMAIL_CONTENT)
					.replaceAll("#userName#", reqBy.getName())
					.replaceAll("#custNo#", topAccount.getCustNo())
					.replaceAll("#acctName#", topAccount.getAccountName())
					.replaceAll("#divCode#", divCode)
					.replaceAll("#divName#", divName)
					.replaceAll("#prodType#", request.getPmtbProductType().getName())
					.replaceAll("#deptCode#", deptCode)
					.replaceAll("#deptName#", deptName));
		}
	}
	
	public void updateCorpSubAccountSubscription(AmtbAccount acct, AmtbSubscProdReq request, AmtbSubscTo subscription, String subAction) throws CniiInterfaceException{
	
		ArrayList<IttbCniiAcctReq> cniiList = new ArrayList<IttbCniiAcctReq>();
		
		if(subAction.trim().equals(NonConfigurableConstants.SUBSCRIPTION_STATUS_SUBSCRIBE_DIV_DEPT))
		{
			AmtbSubscTo childProdSubscription = new AmtbSubscTo();
			AmtbSubscToPK compId = new AmtbSubscToPK(request.getPmtbProductType(), acct);
			
			childProdSubscription.setComp_id(compId);
			childProdSubscription.setEffectiveDt(request.getEffectiveDt());
			childProdSubscription.setLrtbRewardMaster(request.getLrtbRewardMaster());
			childProdSubscription.setMstbProdDiscMaster(request.getMstbProdDiscMaster());
			childProdSubscription.setMstbSubscFeeMaster(request.getMstbSubscFeeMaster());
			childProdSubscription.setMstbIssuanceFeeMaster(request.getMstbIssuanceFeeMaster());
			this.save(childProdSubscription);
			
			AmtbAccount parentAcct = getAccountWithParent(acct);
			Map<String, String> prop = (Map)SpringUtil.getBean("webserviceProperties");
			List<String> cardLessProductList = new ArrayList<String>();
			List<PmtbProductType> cardlessProducts = this.businessHelper.getProductTypeBusiness().getCardlessProductType();
			for(PmtbProductType cardlessProduct : cardlessProducts) {
				cardLessProductList.add(cardlessProduct.getProductTypeId().trim());
			}
			
			if(cardLessProductList.contains(request.getPmtbProductType().getProductTypeId()) && NonConfigurableConstants.BOOLEAN_NO.equals((String) prop.get("ws.cnii.disable"))){
				if(parentAcct!=null){
					AmtbAcctStatus parentStatus = getCurrentStatus(parentAcct.getAmtbAcctStatuses());
					if(parentStatus!=null && !parentStatus.getAcctStatus().equals(NonConfigurableConstants.ACCOUNT_STATUS_PENDING_ACTIVATION)){
						IttbCniiAcctReq cniiRequest = new IttbCniiAcctReq();
						cniiRequest.setReqId(DateUtil.convertDateToStr(DateUtil.getCurrentDate(), DateUtil.GLOBAL_CNII_SEQ_FORMAT) + this.daoHelper.getAccountDao().getNextSequenceNo(Sequence.CNII_REQ_NO_SEQUENCE));
						cniiRequest.setReqDate(DateUtil.getCurrentTimestamp());
						cniiRequest.setEventType(NonConfigurableConstants.CNII_REQUEST_EVENT_TYPE_ACTIVATE);
						cniiRequest.setReqStatus(NonConfigurableConstants.CNII_REQUEST_STATUS_PENDING);
						cniiRequest.setValidFlg(NonConfigurableConstants.BOOLEAN_YES);
						cniiRequest.setUpdatedBy(NonConfigurableConstants.CNII_REQUEST_UPDATED_BY_IBS);
						StringBuffer acctId = new StringBuffer();
						StringBuffer parentId = new StringBuffer();
						AmtbAccount parentAcct2 = parentAcct;
						if(parentAcct2.getCustNo()!=null && parentAcct2.getCustNo().length()!=0){
							acctId.append(parentAcct2.getCustNo());
							//parentId.append(parentAcct2.getCustNo());
						}else{
							acctId.append(parentAcct2.getCode());
							//parentId.append(parentAcct2.getCode());
							// now getting the parent
//							AmtbAccount parent = getAccountWithParent(parentAcct2);
							AmtbAccount parent = acct.getAmtbAccount();
							if(parent!=null){// this shouldn't happen
								while(parent.getCustNo()==null){
									acctId.insert(0, parent.getCode());
									parentId.insert(0, parent.getCode());
									parent = this.daoHelper.getAccountDao().getParent(parent).getAmtbAccount();
								}
								acctId.insert(0, parent.getCustNo());
								parentId.insert(0, parent.getCustNo());
							}
						}
//						acctId.append(acct.getCode());
						cniiRequest.setAccountId(acctId.toString());
						cniiRequest.setAccountCd(acct.getCode());
						cniiRequest.setAccountNm(acct.getAccountName());
						cniiRequest.setParentId(parentId.toString());
						cniiRequest.setUpdatedDt(DateUtil.getCurrentTimestamp());
						//this.save(cniiRequest);
						cniiList.add(cniiRequest);
						
					}
				}
			}
		}
		else if(subAction.trim().equalsIgnoreCase(NonConfigurableConstants.SUBSCRIPTION_STATUS_UNSUBSCRIBE_DIV_DEPT))
		{

			this.delete(subscription);
			PmtbProductType pmtbProductType = request.getPmtbProductType();
			
			//to check if premier service is deleted.
			//if so, update to cnii
			Map<String, String> prop = (Map)SpringUtil.getBean("webserviceProperties");
			List<String> cardLessProductList = new ArrayList<String>();
			List<PmtbProductType> cardlessProducts = this.businessHelper.getProductTypeBusiness().getCardlessProductType();
			for(PmtbProductType cardlessProduct : cardlessProducts) {
				cardLessProductList.add(cardlessProduct.getProductTypeId().trim());
			}
			
			
			// now removing all product subscriptions that are in children of account
			List<AmtbAccount> childrenAccts = this.daoHelper.getAccountDao().getChildrenAccountsWithSubscriptionsAndMainContacts(acct.getAccountNo());
			for(AmtbAccount childAcct : childrenAccts){
				List<AmtbSubscTo> childDeleted = new ArrayList<AmtbSubscTo>();
				for(AmtbSubscTo childSubscribed : childAcct.getAmtbSubscTos()){
					if(pmtbProductType.getProductTypeId().contains(childSubscribed.getComp_id().getPmtbProductType().getProductTypeId())){
						childDeleted.add(childSubscribed);
//						if(pmtbProductType.getProductTypeId().contains(NonConfigurableConstants.PREMIER_SERVICE) && NonConfigurableConstants.BOOLEAN_NO.equals((String) prop.get("ws.cnii.disable"))){
						if(cardLessProductList.contains(pmtbProductType.getProductTypeId()) && NonConfigurableConstants.BOOLEAN_NO.equals((String) prop.get("ws.cnii.disable"))){
							AmtbAcctStatus parentStatus = getCurrentStatus(childAcct.getAmtbAccount().getAmtbAcctStatuses());
							if(parentStatus!=null && !parentStatus.getAcctStatus().equals(NonConfigurableConstants.ACCOUNT_STATUS_PENDING_ACTIVATION)){
								IttbCniiAcctReq cniiRequest = new IttbCniiAcctReq();
								cniiRequest.setReqId(DateUtil.convertDateToStr(DateUtil.getCurrentDate(), DateUtil.GLOBAL_CNII_SEQ_FORMAT) + this.daoHelper.getAccountDao().getNextSequenceNo(Sequence.CNII_REQ_NO_SEQUENCE));
								cniiRequest.setReqDate(DateUtil.getCurrentTimestamp());
								cniiRequest.setEventType(NonConfigurableConstants.CNII_REQUEST_EVENT_TYPE_NEW);
								cniiRequest.setReqStatus(NonConfigurableConstants.CNII_REQUEST_STATUS_PENDING);
								cniiRequest.setTerminateDt(DateUtil.getCurrentDate());
								cniiRequest.setValidFlg(NonConfigurableConstants.BOOLEAN_YES);
								cniiRequest.setUpdatedBy(NonConfigurableConstants.CNII_REQUEST_UPDATED_BY_IBS);
								
								StringBuffer acctId = new StringBuffer();
								StringBuffer parentId = new StringBuffer();
								AmtbAccount parentAcct2 = getAccountWithParent(childAcct);
								if(parentAcct2.getCustNo()!=null && parentAcct2.getCustNo().length()!=0){
									acctId.append(parentAcct2.getCustNo());
									//parentId.append(parentAcct2.getCustNo());
								}else{
									//acctId.append(parentAcct2.getCode());
									//parentId.append(parentAcct2.getCode());
									// now getting the parent
//									AmtbAccount parent = getAccountWithParent(parentAcct2);
									AmtbAccount parent = parentAcct2.getAmtbAccount();
									if(parent!=null){// this shouldn't happen
										while(parent.getCustNo()==null){
											acctId.insert(0, parent.getCode());
											parentId.insert(0, parent.getCode());
											parent = this.daoHelper.getAccountDao().getParent(parent).getAmtbAccount();
										}
										acctId.insert(0, parent.getCustNo());
										parentId.insert(0, parent.getCustNo());
									}
								}
								acctId.append(childAcct.getCode());
								cniiRequest.setAccountId(acctId.toString());
								cniiRequest.setAccountCd(acct.getCode());
								cniiRequest.setParentId(parentId.toString());
								
								cniiRequest.setAccountCd(childAcct.getCode());
								cniiRequest.setAccountNm(childAcct.getAccountName());
								cniiRequest.setUpdatedDt(DateUtil.getCurrentTimestamp());
								

								//this.save(cniiRequest);
								//ArrayList<IttbCniiAcctReq> cniiList = new ArrayList<IttbCniiAcctReq>();
								cniiList.add(cniiRequest);
								//IBSCNIIUpdateAcctClient.send(cniiList);
							}
						}
						break;
					}
				}

				childAcct.getAmtbSubscTos().clear();
				for(AmtbSubscTo childDelete : childDeleted){
					this.delete(childDelete);
				}
			}
			
//			if(request.getPmtbProductType().getProductTypeId().equals(NonConfigurableConstants.PREMIER_SERVICE) && NonConfigurableConstants.BOOLEAN_NO.equals((String) prop.get("ws.cnii.disable"))){
			if(cardLessProductList.contains(request.getPmtbProductType().getProductTypeId()) && NonConfigurableConstants.BOOLEAN_NO.equals((String) prop.get("ws.cnii.disable"))){
				
				AmtbAccount corpAcct = getTopLevelAccount(acct);
				
				AmtbAcctStatus corpStatus = getCurrentStatus(corpAcct.getAmtbAcctStatuses());
				if(corpStatus!=null && !corpStatus.getAcctStatus().equals(NonConfigurableConstants.ACCOUNT_STATUS_PENDING_ACTIVATION)){
					IttbCniiAcctReq cniiRequest = new IttbCniiAcctReq();
					cniiRequest.setReqId(DateUtil.convertDateToStr(DateUtil.getCurrentDate(), DateUtil.GLOBAL_CNII_SEQ_FORMAT) + this.daoHelper.getAccountDao().getNextSequenceNo(Sequence.CNII_REQ_NO_SEQUENCE));
					cniiRequest.setReqDate(DateUtil.getCurrentTimestamp());
					cniiRequest.setEventType(NonConfigurableConstants.CNII_REQUEST_EVENT_TYPE_ACTIVATE);
					cniiRequest.setReqStatus(NonConfigurableConstants.CNII_REQUEST_STATUS_PENDING);
					cniiRequest.setTerminateDt(DateUtil.getCurrentDate());
					cniiRequest.setValidFlg(NonConfigurableConstants.BOOLEAN_YES);
					cniiRequest.setUpdatedBy(NonConfigurableConstants.CNII_REQUEST_UPDATED_BY_IBS);
					StringBuffer acctId = new StringBuffer();
					StringBuffer parentId = new StringBuffer();
					AmtbAccount parentAcct2 = getAccountWithParent(acct);
					if(parentAcct2.getCustNo()!=null && parentAcct2.getCustNo().length()!=0){
						acctId.append(parentAcct2.getCustNo());
						//parentId.append(parentAcct2.getCustNo());
					}else{
						//acctId.append(parentAcct2.getCode());
						//parentId.append(parentAcct2.getCode());
						// now getting the parent
//						AmtbAccount parent = getAccountWithParent(parentAcct2);
						AmtbAccount parent = acct.getAmtbAccount();
						if(parent!=null){// this shouldn't happen
							while(parent.getCustNo()==null){
								acctId.insert(0, parent.getCode());
								parentId.insert(0, parent.getCode());
								parent = this.daoHelper.getAccountDao().getParent(parent).getAmtbAccount();
							}
							acctId.insert(0, parent.getCustNo());
							parentId.insert(0, parent.getCustNo());
						}
					}
					acctId.append(acct.getCode());
					cniiRequest.setAccountId(acctId.toString());
					cniiRequest.setAccountCd(acct.getCode());
					cniiRequest.setAccountNm(acct.getAccountName());
					cniiRequest.setParentId(parentId.toString());
					cniiRequest.setUpdatedDt(DateUtil.getCurrentTimestamp());
					//this.save(cniiRequest);
					cniiList.add(cniiRequest);
				}
			}
		}
		if(!cniiList.isEmpty())
		{
//			try {
//				IBSCNIIUpdateAcctClient.send(cniiList);
				
				try {
					//29 Aug 2018 CNII Acct Sync
					String msg = updateCniiAcctSyncProcedure(cniiList);
					logger.info("CNII Acct Sync log : "+msg);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					throw new CniiInterfaceException("CNII Interface Acct Sync Error - "+e);
				}
//			} catch (CniiInterfaceException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
		}
	}
	public Map<Integer, Map<String, String>> getSubscriptionRequests(String custNo, String acctName, Date from, Date to, String status, String requester, String action){
		List<AmtbSubscProdReq> requests = this.daoHelper.getAccountDao().getSubscriptionRequest(custNo, acctName, from, to, status, requester, action);
		Map<Integer, Map<String, String>> returnMap = new HashMap<Integer, Map<String,String>>();
		
		for(AmtbSubscProdReq request : requests){
			Map<String, String> requestMap = new HashMap<String, String>();
			
			AmtbAccount topAcct = getTopLevelAccount(request.getAmtbAccount());
			
			requestMap.put("custNo", topAcct.getCustNo());
			requestMap.put("acctName", topAcct.getAccountName());
			
			if(request.getAmtbAccount().getAccountCategory().equalsIgnoreCase(NonConfigurableConstants.ACCOUNT_CATEGORY_DIVISION)) {
				requestMap.put("divCodeName", request.getAmtbAccount().getCode() +" - "+ request.getAmtbAccount().getAccountName());
			}
			else
				requestMap.put("divCodeName", "-");
			
			if(request.getAmtbAccount().getAccountCategory().equalsIgnoreCase(NonConfigurableConstants.ACCOUNT_CATEGORY_DEPARTMENT)) {
				AmtbAccount parentAcct = getAccountWithParent(request.getAmtbAccount());
				
				requestMap.put("divCodeName", parentAcct.getAmtbAccount().getCode() + " - "+parentAcct.getAmtbAccount().getAccountName());
				requestMap.put("deptCodeName", request.getAmtbAccount().getCode() +" - "+request.getAmtbAccount().getAccountName());
			}
			else 
				requestMap.put("deptCodeName", "-");
			
			if(request.getSubscAction().equals(NonConfigurableConstants.SUBSCRIPTION_STATUS_UNSUBSCRIBE) || request.getSubscAction().equals(NonConfigurableConstants.SUBSCRIPTION_STATUS_UNSUBSCRIBE_DIV_DEPT)) {
				requestMap.put("action", "UNSUBSCRIBE");
			}
			else if(request.getSubscAction().equals(NonConfigurableConstants.SUBSCRIPTION_STATUS_SUBSCRIBE) || request.getSubscAction().equals(NonConfigurableConstants.SUBSCRIPTION_STATUS_SUBSCRIBE_DIV_DEPT) || request.getSubscAction().equals(NonConfigurableConstants.SUBSCRIPTION_STATUS_SUBSCRIBE_EDIT)) {
				requestMap.put("action", "SUBSCRIBE");
			}
			SatbUser reqBy = this.daoHelper.getUserDao().getUserWithRoles(request.getReqBy());
			requestMap.put("reqBy", reqBy.getName());
			requestMap.put("reqDate", DateUtil.convertTimestampToStr(request.getReqDt(), DateUtil.GLOBAL_DATE_FORMAT));
			
			if(null != request.getApproveBy()){
				SatbUser user = this.daoHelper.getUserDao().getUserWithRoles(request.getApproveBy());
				requestMap.put("appBy", user.getName());
				requestMap.put("appDt", DateUtil.convertTimestampToStr(request.getApproveDt(), DateUtil.GLOBAL_DATE_FORMAT));
			}else{
				requestMap.put("appBy", "-");
				requestMap.put("appDt", "-");
			}
			requestMap.put("reqStatus", NonConfigurableConstants.SUBSCRIPTION_APPROVE_STATUS.get(request.getAppStatus()));
			returnMap.put(request.getSubProdReqNo(), requestMap);
		}
		return returnMap;
	}
	
	public void sendSubscriptionApprovalEmail(AmtbSubscProdReq request)
	{
		List<String> toEmails = new ArrayList<String>();
		
		SatbUser reqBy = this.daoHelper.getUserDao().getUserWithRoles(request.getReqBy());
		List<SatbUser> appBy = this.daoHelper.getUserDao().searchUser(Uri.APPROVE_CREDIT_REVIEW); // APPROVER EMAIL IS
		StringBuffer approverNames = new StringBuffer();
		for(SatbUser approver : appBy){
			toEmails.add(approver.getEmail());
			approverNames.append(approver.getName() + ",");
		}
		appBy = this.daoHelper.getUserDao().searchUser(Uri.APPROVE_CREDIT_REVIEW);
		for(SatbUser approver : appBy){
			if(!toEmails.contains(approver.getEmail())){
				toEmails.add(approver.getEmail());
				approverNames.append(approver.getName() + " ,");
			}
		}
		approverNames.delete(approverNames.length()-1, approverNames.length());
		
		AmtbAccount topAccount = getTopLevelAccount(request.getAmtbAccount());
		String divCode = "-";
		String divName = "-";
		String deptCode = "-";
		String deptName = "-";
		String subscription = "Subscribe";
		
		if(request.getSubscAction().equals(NonConfigurableConstants.SUBSCRIPTION_STATUS_UNSUBSCRIBE) || request.getSubscAction().equals(NonConfigurableConstants.SUBSCRIPTION_STATUS_UNSUBSCRIBE_DIV_DEPT))
			subscription = "Unsubscribe";
		
		if(request.getAmtbAccount().getAccountCategory().equalsIgnoreCase(NonConfigurableConstants.ACCOUNT_CATEGORY_DIVISION))
		{
			divCode = request.getAmtbAccount().getCode();
			divName = request.getAmtbAccount().getAccountName();
		}
		else if(request.getAmtbAccount().getAccountCategory().equalsIgnoreCase(NonConfigurableConstants.ACCOUNT_CATEGORY_DEPARTMENT))
		{
			deptCode = request.getAmtbAccount().getCode();
			deptName = request.getAmtbAccount().getAccountName();
			divCode = request.getAmtbAccount().getAmtbAccount().getCode();
			divName = request.getAmtbAccount().getAmtbAccount().getAccountName();
		}
		
		EmailUtil.sendEmail(toEmails.toArray(new String[]{}), new String[]{},
				ConfigurableConstants.getEmailText(ConfigurableConstants.EMAIL_TYPE_SUBSCRIPTION_REQUEST_SUBMIT, ConfigurableConstants.EMAIL_SUBJECT)
				.replaceAll("#subscribe#", subscription),
				ConfigurableConstants.getEmailText(ConfigurableConstants.EMAIL_TYPE_SUBSCRIPTION_REQUEST_SUBMIT, ConfigurableConstants.EMAIL_CONTENT)
				.replaceAll("#userName#", approverNames.toString())
				.replaceAll("#custNo#", topAccount.getCustNo())
				.replaceAll("#acctName#", topAccount.getAccountName())
				.replaceAll("#divCode#", divCode)
				.replaceAll("#divName#", divName)
				.replaceAll("#deptCode#", deptCode)
				.replaceAll("#deptName#", deptName)
				.replaceAll("#prodType#", request.getPmtbProductType().getName())
				.replaceAll("#submitter#", reqBy.getName()));
	}
	
	public Timestamp resetDate (Timestamp datex) {
		
		Date date = new Date(datex.getTime());                      // timestamp now
		Calendar cal = Calendar.getInstance();       // get calendar instance
		cal.setTime(date);                           // set cal to date
		cal.set(Calendar.HOUR_OF_DAY, 0);            // set hour to midnight
		cal.set(Calendar.MINUTE, 0);                 // set minute in hour
		cal.set(Calendar.SECOND, 0);                 // set second in minute
		cal.set(Calendar.MILLISECOND, 0);            // set millis in second
		Date zeroedDate = cal.getTime();
		
		return new Timestamp(zeroedDate.getTime());
	}
	
	public void fixEffectiveTempLimit(AmtbAcctCredLimit creditLimit, String userId) {			
			if(creditLimit.getEffectiveDtTo() != null) 
			{
				if(creditLimit.getEffectiveDtTo().after(DateUtil.getCurrentTimestamp())) 
				{
//					System.out.println("fixing Effective temp limit > "+creditLimit.getAmtbAccount().getAccountNo());
					BigDecimal newCreditLimit = this.daoHelper.getAccountDao().getActivePermCreditLimit(creditLimit.getAmtbAccount().getAccountNo());
					
					if(newCreditLimit != null)
					{
						try {
							creditLimit.setEffectiveDtTo(DateUtil.getCurrentTimestamp());
							this.update(creditLimit);
							
							AmtbAccount acct = getAccount(creditLimit.getAmtbAccount().getAccountNo().toString());
							
							BigDecimal difference = null;
							BigDecimal currentPerm = acct.getCreditLimit();
							BigDecimal currentTemp = acct.getTempCreditLimit()!=null ? acct.getTempCreditLimit() : new BigDecimal(0);
							BigDecimal nextPerm = newCreditLimit;
							BigDecimal nextTemp = currentTemp;
							BigDecimal currentBigger = null, nextBigger = null;
							if(currentPerm.doubleValue() < currentTemp.doubleValue()){
								currentBigger = currentTemp;
							}else{
								currentBigger = currentPerm;
							}

							nextBigger = nextPerm;
							
							logger.info("test account no = "+acct.getAccountNo());
							logger.info("test currentPerm = " + currentPerm);
							logger.info("test currentTemp = " + currentTemp);
							logger.info("test nextPerm = " + nextPerm);
							logger.info("test nextTemp = " + nextTemp);
							logger.info("test currentBigger = " + currentBigger);
							logger.info("test nextBigger = " + nextBigger);
							difference = nextBigger.subtract(currentBigger);
							logger.info("test difference = " + difference);
							
							acct.setTempCreditLimit(null);
							acct.setCreditLimit(newCreditLimit);
							acct.setCreditBalance(acct.getCreditBalance().add(difference));
							this.update(acct);
							// updating credit balance to AS
							API.updateAccount(API.formulateAccountId(acct), acct.getCreditBalance().toString(), API.formulateParentAccountId(acct), userId, API.ASYNCHRONOUS);
						}catch(Exception e) {
							e.printStackTrace();
						}
					}
				}
			}
	}
	public AmtbCredRevReq getPendingCreditReviewRequestsCheck(Integer requestId) {
		return this.daoHelper.getAccountDao().getPendingCreditReviewRequest(requestId);
	}
	public String updateCniiAcctSyncProcedure(ArrayList<IttbCniiAcctReq> list) throws Exception 
	{
		int successCount = 0;
		int errorCount = 0;
		String msg = "";
		
		if(!list.isEmpty())
		{
	        logger.info("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
	    	logger.info("@@@@@@@@@@ Starting IBS CNII  Account Sync  @@@@@@@@@@@");
	    	logger.info("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");

	    	//for testing to check the content
//			Iterator<IttbCniiAcctReq> iter2 = list.iterator();
//	    	while(iter2.hasNext())
//	    	{
//				   IttbCniiAcctReq temp = iter2.next();
//	    		logger.info("111111 sending record : "+successCount+1 + " || 1 account_id > "+temp.getAccountId() +" || 2 account_code > "+temp.getAccountCd() +" || 3 account_name > "+temp.getAccountNm() +" || 4 parent_id > "+temp.getParentId()
//		   		+ " || 5 terminate_dt > "+temp.getTerminateDt() +" 6 susp_dt_start > "+temp.getSuspensionStartDt() +" || 7 susp_dt_end > "+temp.getSuspensionEndDt() +" || 8 updated_by > "+temp.getUpdatedBy() + " ||");
//			
//	    	}
	    	
			   Iterator<IttbCniiAcctReq> iter = list.iterator();
			   while (iter.hasNext())
			   {
				   IttbCniiAcctReq temp = iter.next();
						   
				   try{
						
					   logger.info("sending record : "+successCount+1 + " || 1 account_id > "+temp.getAccountId() +" || 2 account_code > "+temp.getAccountCd() +" || 3 account_name > "+temp.getAccountNm() +" || 4 parent_id > "+temp.getParentId()
					   		+ " || 5 terminate_dt > "+temp.getTerminateDt() +" 6 susp_dt_start > "+temp.getSuspensionStartDt() +" || 7 susp_dt_end > "+temp.getSuspensionEndDt() +" || 8 updated_by > "+temp.getUpdatedBy() + " ||");
						
					   this.daoHelper.getAccountDao().updateCniiAcctSyncProcedure(
								temp.getAccountId(), 
								temp.getAccountCd(), 
								temp.getAccountNm(), 
								temp.getParentId(), 
								temp.getTerminateDt(), 
								temp.getSuspensionStartDt(), 
								temp.getSuspensionEndDt(), 
								temp.getUpdatedBy());
							   
					   	successCount++;
				   }
				   catch(Exception e) {
					   errorCount++;
					   logger.info("CNII Interface error - " + e.getMessage() + "\n");
				   	   LoggerUtil.printStackTrace(logger, e);
				   	   throw new Exception("CNII Interface Acct Sync Error - " + e.getMessage());
				   }
				   msg = "CNII Interface AcctSync Completed || Success Count = "+successCount+" || Error Count = "+errorCount +" || ";
			   }
		}
		else
		{
			// No need to send if there is no record
			//sendNumOfEntries.setText(Integer.toString(count));
			msg = "No records to transfer";
		}

    	logger.info("Transfer program completed - " + msg);
	   return msg;
	}
	
	public void updateEInvoiceEmail(String eInvoiceEmailFlag, String eInvoiceEmail, String eInvoiceEmailSubject, String eInvoiceEmailZipFlag, 
			String eInvoiceEmailAttachment, String eInvoiceEmailPage, String custNo, String parentCode, String code, String userId) throws Exception {
		
		AmtbAccount acct = getAccount(custNo, parentCode, code);
		
		acct.seteInvoiceEmailFlag(eInvoiceEmailFlag);
		acct.seteInvoiceEmail(eInvoiceEmail);
		acct.seteInvoiceEmailSubject(eInvoiceEmailSubject);
		acct.seteInvoiceEmailZipFlag(eInvoiceEmailZipFlag);
		acct.seteInvoiceEmailAttachment(eInvoiceEmailAttachment);
		acct.seteInvoiceEmailPage(eInvoiceEmailPage);
		
		this.update(acct, userId);
	}
	
	public void updateEInvoiceEmail(String eInvoiceEmailFlag, String eInvoiceEmail, String eInvoiceEmailSubject, String eInvoiceEmailZipFlag, 
			String eInvoiceEmailAttachment, String eInvoiceEmailPage, String custNo, String subAcctNo, String userId) throws Exception{
		AmtbAccount acct = getAccount(custNo, subAcctNo);
		
		acct.seteInvoiceEmailFlag(eInvoiceEmailFlag);
		acct.seteInvoiceEmail(eInvoiceEmail);
		acct.seteInvoiceEmailSubject(eInvoiceEmailSubject);
		acct.seteInvoiceEmailZipFlag(eInvoiceEmailZipFlag);
		acct.seteInvoiceEmailAttachment(eInvoiceEmailAttachment);
		acct.seteInvoiceEmailPage(eInvoiceEmailPage);
		
		this.update(acct, userId);
	}
}
class SuspendPeriod{
	private AmtbAcctStatus suspendStart;
	private AmtbAcctStatus suspendEnd;
	public SuspendPeriod(){
	}
	public SuspendPeriod(AmtbAcctStatus suspendStart, AmtbAcctStatus suspendEnd){
		this.suspendStart = suspendStart;
		this.suspendEnd = suspendEnd;
	}
	public AmtbAcctStatus getSuspendStart() {
		return suspendStart;
	}
	public void setSuspendStart(AmtbAcctStatus suspendStart) {
		this.suspendStart = suspendStart;
	}
	public AmtbAcctStatus getSuspendEnd() {
		return suspendEnd;
	}
	public void setSuspendEnd(AmtbAcctStatus suspendEnd) {
		this.suspendEnd = suspendEnd;
	}
	public boolean isSuspended(Timestamp timestamp){
		if(suspendStart==null){
			return false;
		}else if(suspendEnd==null){
			return this.suspendStart.getEffectiveDt().before(timestamp);
		}else{
			return this.suspendStart.getEffectiveDt().before(timestamp) && this.suspendEnd.getEffectiveDt().after(timestamp);
		}
	}
	public boolean isSuspended(Date date){
		if(suspendStart==null){
			return false;
		}else if(suspendEnd==null){
			return this.suspendStart.getEffectiveDt().before(date);
		}else{
			return this.suspendStart.getEffectiveDt().before(date) && this.suspendEnd.getEffectiveDt().after(date);
		}
	}
}