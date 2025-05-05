package com.cdgtaxi.ibs.txn.business;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Comparator;
import java.util.ConcurrentModificationException;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import org.apache.log4j.Logger;
import org.springframework.dao.ConcurrencyFailureException;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zkplus.spring.SpringUtil;

import com.cdgtaxi.ibs.Uri;
import com.cdgtaxi.ibs.acl.model.SatbUser;
import com.cdgtaxi.ibs.common.ConfigurableConstants;
import com.cdgtaxi.ibs.common.MasterSetup;
import com.cdgtaxi.ibs.common.NonConfigurableConstants;
import com.cdgtaxi.ibs.common.business.GenericBusinessImpl;
import com.cdgtaxi.ibs.common.dao.Sequence;
import com.cdgtaxi.ibs.common.exception.ProcessTripException;
import com.cdgtaxi.ibs.common.model.AmtbAccount;
import com.cdgtaxi.ibs.common.model.AmtbAcctMainContact;
import com.cdgtaxi.ibs.common.model.AmtbAcctStatus;
import com.cdgtaxi.ibs.common.model.BmtbInvoiceHeader;
import com.cdgtaxi.ibs.common.model.BmtbInvoiceTxn;
import com.cdgtaxi.ibs.common.model.BmtbNote;
import com.cdgtaxi.ibs.common.model.BmtbNoteFlow;
import com.cdgtaxi.ibs.common.model.FmtbEntityMaster;
import com.cdgtaxi.ibs.common.model.FmtbTransactionCode;
import com.cdgtaxi.ibs.common.model.IttbCpGuestProduct;
import com.cdgtaxi.ibs.common.model.IttbFmsDrvrRfndColReq;
import com.cdgtaxi.ibs.common.model.IttbSetlTxn;
import com.cdgtaxi.ibs.common.model.IttbTripsTxn;
import com.cdgtaxi.ibs.common.model.IttbTripsTxnError;
import com.cdgtaxi.ibs.common.model.IttbTripsTxnReq;
import com.cdgtaxi.ibs.common.model.PmtbBalanceForfeiture;
import com.cdgtaxi.ibs.common.model.PmtbPrepaidCardTxn;
import com.cdgtaxi.ibs.common.model.PmtbProduct;
import com.cdgtaxi.ibs.common.model.PmtbProductReplacement;
import com.cdgtaxi.ibs.common.model.PmtbProductRetag;
import com.cdgtaxi.ibs.common.model.PmtbProductStatus;
import com.cdgtaxi.ibs.common.model.PmtbProductType;
import com.cdgtaxi.ibs.common.model.TmtbAcquireTxn;
import com.cdgtaxi.ibs.common.model.TmtbTxnReviewReq;
import com.cdgtaxi.ibs.common.model.TmtbTxnReviewReqFlow;
import com.cdgtaxi.ibs.common.model.VwIntfSetlForIb;
import com.cdgtaxi.ibs.common.model.VwIntfTripsForIb;
import com.cdgtaxi.ibs.common.model.forms.ApplyAmtInfo;
import com.cdgtaxi.ibs.common.model.forms.GstInfo;
import com.cdgtaxi.ibs.common.ui.CommonWindow;
import com.cdgtaxi.ibs.interfaces.as.api.API;
import com.cdgtaxi.ibs.interfaces.fms.IBSFMSDriverVehicleAssocClient;
import com.cdgtaxi.ibs.master.model.MstbAdminFeeDetail;
import com.cdgtaxi.ibs.master.model.MstbMasterTable;
import com.cdgtaxi.ibs.master.model.MstbProdDiscDetail;
import com.cdgtaxi.ibs.txn.ui.TxnSearchCriteria;
import com.cdgtaxi.ibs.util.AccountUtil;
import com.cdgtaxi.ibs.util.DateUtil;
import com.cdgtaxi.ibs.util.EmailUtil;
import com.cdgtaxi.ibs.util.GstUtil;
import com.cdgtaxi.ibs.util.LoggerUtil;
import com.cdgtaxi.ibs.util.PrepaidUtil;
import com.google.common.collect.Lists;
import com.google.common.collect.Ordering;

public class TxnBusinessImpl extends GenericBusinessImpl implements TxnBusiness {
	private static final Logger logger = Logger
			.getLogger(TxnBusinessImpl.class);
	private final BigDecimal HUNDRED = new BigDecimal(100)
			.setScale(NonConfigurableConstants.NO_OF_DECIMAL);
	private final BigDecimal ZERO = new BigDecimal(0)
			.setScale(NonConfigurableConstants.NO_OF_DECIMAL);

	public static final String PREPAID_HANDLE_TRIP_ACTION_VOID = "VOID";
	public static final String PREPAID_HANDLE_TRIP_ACTION_NEW = "NEW";
	public static final String PREPAID_HANDLE_TRIP_ACTION_EDIT = "EDIT";
	
	public static Ordering<TmtbTxnReviewReqFlow> TmtbTxnReviewReqFlowNoOrder = Ordering.natural().onResultOf(
			new com.google.common.base.Function<TmtbTxnReviewReqFlow, Integer>() {
				
				public Integer apply(TmtbTxnReviewReqFlow reqFlow) {
					return reqFlow.getTxnReviewReqFlowNo();
				}
				
			});
	
	public Map<String, String> getAllProductTypes() {
		logger.info("getAllProductTypes");
		List<PmtbProductType> productTypeList = this.daoHelper
				.getProductTypeDao().getAllProductType();
		Map<String, String> returnMap = new HashMap<String, String>();
		for (PmtbProductType productType : productTypeList) {
			returnMap
					.put(productType.getProductTypeId(), productType.getName());
		}
		return returnMap;
	}

	public Map<String, String> getPremierProductTypes() {
		logger.info("getAllProductTypes");
		List<PmtbProductType> productTypeList = this.daoHelper
				.getProductTypeDao().getAllCardlessProductType();
		Map<String, String> returnMap = new HashMap<String, String>();
		for (PmtbProductType productType : productTypeList) {
				returnMap.put(productType.getProductTypeId(), productType.getName());
		}
		return returnMap;
	}

	public Map<String, String> getExternalProductTypes() {
		logger.info("getAllProductTypes");
		List<PmtbProductType> productTypeList = this.daoHelper
				.getProductTypeDao().getAllProductType();
		Map<String, String> returnMap = new HashMap<String, String>();
		for (PmtbProductType productType : productTypeList) {
			if (NonConfigurableConstants.BOOLEAN_YES.equals(productType
					.getExternalCard()))
				returnMap.put(productType.getProductTypeId(),
						productType.getName());
		}
		return returnMap;
	}

	public Map<String, String> getEntityMaster() {
		logger.info("getEntityMaster");
		List<FmtbEntityMaster> entityMasterList = MasterSetup
				.getEntityManager().getSetupDao().getAllEntities();
		Map<String, String> returnMap = new HashMap<String, String>();
		for (FmtbEntityMaster entityMaster : entityMasterList) {
			returnMap.put(entityMaster.getEntityNo().toString(),
					entityMaster.getEntityName());
		}
		return returnMap;
	}

	public AmtbAccount getAccount(String cardNo, Timestamp startDt) {
		// To return account instance
		return this.daoHelper.getAccountDao().getAccount(cardNo, startDt);
	}

	public AmtbAcctMainContact getMainBillingContact(AmtbAccount amtbAccount) {
		return this.daoHelper.getAccountDao()
				.getMainBillingContact(amtbAccount);
	}

	public PmtbProduct getProduct(String cardNo) {
		// To return account instance
		return this.daoHelper.getProductDao().getProduct(cardNo);
	}

	public PmtbProductRetag getRetagProductsByDate(BigDecimal productId,
			Timestamp ts) {
		return this.daoHelper.getProductDao().getRetagProductsByDate(productId,
				ts);
	}

	public PmtbProductStatus getEarliestProductIssuedStatus(String cardNo) {
		// To return account instance
		return this.daoHelper.getProductDao().getEarliestProductIssuedStatus(
				cardNo);
	}

	public PmtbProduct getProduct(PmtbProduct pmtbProduct) {
		// To return account instance
		return this.daoHelper.getProductDao().getProduct(pmtbProduct);
	}
	public List<AmtbAccount> searchPremierAccounts(AmtbAccount amtbAccount) {
		// Get all premier product Type ID
		List<PmtbProductType> pmtbProductType = this.daoHelper.getProductTypeDao()
				.getPremierServiceProductType();
		if (pmtbProductType != null)
			return this.daoHelper.getAccountDao().getPremierAccountsAcct(
					amtbAccount, pmtbProductType);
		else
			return null;
	}
	
	public List<PmtbProductType> searchPremierAccountsProductTypes(String custNo, String acctNo) {
		
		if(acctNo == null || acctNo.trim().equals(""))
			acctNo = this.daoHelper.getAccountDao().getAccountByCustNo(custNo).getAccountNo().toString();
		
		// Get all premier product Type of account
		List<PmtbProductType> pmtbProductTypeList = this.daoHelper.getProductTypeDao().getPremierAccountProductType(acctNo);
		
		List<PmtbProductType> pmtbProductTypeReturn = new ArrayList<PmtbProductType>();
		if(pmtbProductTypeList != null)
		{
			for(PmtbProductType prod : pmtbProductTypeList)
			{
				if(prod.getCardless().equalsIgnoreCase("Y")){
					pmtbProductTypeReturn.add(prod);
				}
			}
		}
//		System.out.println("test > "+pmtbProductTypeReturn.size());
		
		if(pmtbProductTypeReturn.size() != 0)
			return pmtbProductTypeReturn;
		else
			return null;
	}

	public List<AmtbAccount> searchPremierAccounts(String custNo,
			String acctName) {
		// Get all active account
		// Get all premier product Type ID
		List<PmtbProductType> pmtbProductType = this.daoHelper.getProductTypeDao()
				.getPremierServiceProductType();
		if (pmtbProductType != null)
		{
			return this.daoHelper.getAccountDao().getPremierAccount(custNo,
						acctName, pmtbProductType);
		}
		else
			return null;
	}
	public Boolean searchPremierAccountsGotProductType(String custNo, String productTypeId) {
		
		List<AmtbAccount> accounts = null;
		PmtbProductType pmtbProductType = this.daoHelper.getProductTypeDao()
				.getProductType(productTypeId);
		List<PmtbProductType> pmtbProductTypeList = new ArrayList<PmtbProductType>();
		pmtbProductTypeList.add(pmtbProductType);
		
		
		if (pmtbProductType != null)
		{
			accounts = this.daoHelper.getAccountDao().getPremierAccount(custNo,
						null, pmtbProductTypeList);
		}
		
		if(accounts != null)
			return true;
		else
			return false;
	}

	public List<AmtbAccount> searchAccounts(String custNo, String acctName) {
		// Get all active account
		return this.daoHelper.getAccountDao().getAccounts(custNo, acctName);
	}

	public List<AmtbAccount> searchExternalAccounts(String custNo,
			String acctName, String productTypeId) {
		// Get all active account
//		if (productTypeId != null)
//			return this.daoHelper.getAccountDao().getPremierAccount(custNo,
//					acctName, productTypeId);
//		else
			return null;
	}

	public List<AmtbAccount> searchExternalAccounts(AmtbAccount amtbAccount,
			String productTypeId) {

		if (productTypeId != null)
			return this.daoHelper.getAccountDao().getPremierAccounts(
					amtbAccount, productTypeId);
		else
			return null;
	}

	public List<AmtbAccount> searchAccounts(AmtbAccount amtbAccount) {
		return this.daoHelper.getAccountDao().getAccounts(amtbAccount);
	}

	public List<AmtbAccount> searchAccounts(String productTypeId,
			AmtbAccount amtbAccount) {
		return this.daoHelper.getAccountDao().getAccounts(amtbAccount,
				productTypeId);
	}

	public String createTxn(Map<String, String> txnDetails, String user)
			throws Exception {
		try {
			logger.info("createContact(Map<String, String> contactDetails)");
			TmtbAcquireTxn tmtbAcquireTxn = new TmtbAcquireTxn();

			this.setTxnDetails(tmtbAcquireTxn, txnDetails, true);

			//Default offlineFlag to 'N'
			tmtbAcquireTxn.setOfflineFlag(NonConfigurableConstants.BOOLEAN_NO);
			
			// now saving it to database and return
			if (this.daoHelper.getTxnDao().createTxn(tmtbAcquireTxn, user)) {
				// update the credit balance
				updateAccountCreditBalanceForUI(
						tmtbAcquireTxn.getPmtbProduct(),
						tmtbAcquireTxn.getAmtbAccount(), tmtbAcquireTxn
								.getBillableAmt().negate(),
						txnDetails.get("user"));
				// Check if it is 1-time usage card
				if (NonConfigurableConstants.BOOLEAN_YES.equals(tmtbAcquireTxn
						.getPmtbProductType().getOneTimeUsage())) {
					this.updateProductUsed(tmtbAcquireTxn.getPmtbProduct(),
							tmtbAcquireTxn.getTripStartDt(),
							txnDetails.get("user"), true);
				}
				// Check if it is a multi-usage card
				if (NonConfigurableConstants.BOOLEAN_YES.equals(tmtbAcquireTxn
						.getPmtbProductType().getCreditLimit())) {
					// Special rules: Do not update for One-time-usage card even
					// though credit limit is Y
					if (NonConfigurableConstants.BOOLEAN_NO
							.equals(tmtbAcquireTxn.getPmtbProductType()
									.getOneTimeUsage())) {
						
						PmtbProduct product = tmtbAcquireTxn.getPmtbProduct();
						
						
						if(NonConfigurableConstants.getBoolean(product.getPmtbProductType().getPrepaid())){
							handleTripForPrepaidCard(product, tmtbAcquireTxn, PREPAID_HANDLE_TRIP_ACTION_NEW);
						} else {
							BigDecimal tripUsageAmount = tmtbAcquireTxn.getBillableAmt();
							product.setCreditBalance(product.getCreditBalance().subtract(tripUsageAmount));
							
							// 20160518 Fix for the Credit Balance Discrepancy issue
							checkReplaceCard(product.getCardNo(), tripUsageAmount, "subtract", user);
						}

						this.daoHelper.getProductDao().update(product);
						this.updateProductCreditLimitForUI(product, user);

					}
				}

				// Send to FMS
				if (NonConfigurableConstants.BOOLEAN_YES.equals(tmtbAcquireTxn
						.getFmsFlag())) {
					this.updateFMSReq(tmtbAcquireTxn);
				}

				return tmtbAcquireTxn.getJobNo();
			} else
				return null;
		} catch (Exception e) {
			StringWriter errors = new StringWriter();
    		e.printStackTrace(new PrintWriter(errors));
    		logger.info("Error : "+errors.toString());
			e.printStackTrace();
			throw new Exception("createTxn Exception");
		}
	}

	public String createPremierTxn(Map<String, String> txnDetails, String user)
			throws Exception {
		try {
			logger.info("createContact(Map<String, String> contactDetails)");
			TmtbAcquireTxn tmtbAcquireTxn = new TmtbAcquireTxn();

			this.setPremierTxnDetails(tmtbAcquireTxn, txnDetails);
			// now saving it to database and return
			if (this.daoHelper.getTxnDao().createTxn(tmtbAcquireTxn, user)) {
				// update the credit balance
				updateAccountCreditBalanceForUI(null,
						tmtbAcquireTxn.getAmtbAccount(), tmtbAcquireTxn
								.getBillableAmt().negate(),
						txnDetails.get("user"));
				// Do not need to send to AS as it is premier service

				// Send to FMS
				if (NonConfigurableConstants.BOOLEAN_YES.equals(tmtbAcquireTxn
						.getFmsFlag())) {
					this.updateFMSReq(tmtbAcquireTxn);
				}
				return tmtbAcquireTxn.getJobNo();
			} else
				return null;

		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("createPremierTxn Exception");
		}
	}

	public String createExternalTxn(Map<String, String> txnDetails, String user)
			throws Exception {
		try {
			logger.info("createContact(Map<String, String> contactDetails)");
			TmtbAcquireTxn tmtbAcquireTxn = new TmtbAcquireTxn();

			this.setExternalTxnDetails(tmtbAcquireTxn, txnDetails);

			// now saving it to database and return
			if (this.daoHelper.getTxnDao().createTxn(tmtbAcquireTxn, user)) {
				// update the credit balance
				updateAccountCreditBalanceForUI(null,
						tmtbAcquireTxn.getAmtbAccount(), tmtbAcquireTxn
								.getBillableAmt().negate(),
						txnDetails.get("user"));
				// Do not need to send to AS as it is premier service

				// Send to FMS
				if (NonConfigurableConstants.BOOLEAN_YES.equals(tmtbAcquireTxn
						.getFmsFlag())) {
					this.updateFMSReq(tmtbAcquireTxn);
				}
				return tmtbAcquireTxn.getJobNo();
			} else
				return null;

		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("createExternalTxn Exception");
		}
	}

	public boolean updateTxn(Map<String, String> txnDetails,
			TmtbAcquireTxn oldAcquireTxn) throws Exception {
		logger.info("update Txn");
		TmtbTxnReviewReq tmtbTxnReviewReq = new TmtbTxnReviewReq();
		TmtbTxnReviewReqFlow tmtbTxnReviewReqFlow = new TmtbTxnReviewReqFlow();
		Integer requestId = null;
		if (NonConfigurableConstants.TRANSACTION_SCREEN_VOID.equals(txnDetails
				.get("actionTxn"))) {
			try {
				if (NonConfigurableConstants.TRANSACTION_STATUS_ACTIVE
						.equals(oldAcquireTxn.getTxnStatus()))
					this.setTxnDetailsForEdit(
							tmtbTxnReviewReq,
							tmtbTxnReviewReqFlow,
							txnDetails,
							oldAcquireTxn,
							NonConfigurableConstants.TRANSACTION_REQUEST_STATUS_NEW,
							NonConfigurableConstants.TRANSACTION_REQUEST_STATUS_PENDING_VOID);
				else
					this.setTxnDetailsForEdit(
							tmtbTxnReviewReq,
							tmtbTxnReviewReqFlow,
							txnDetails,
							oldAcquireTxn,
							NonConfigurableConstants.TRANSACTION_REQUEST_STATUS_NEW,
							NonConfigurableConstants.TRANSACTION_REQUEST_STATUS_PENDING_REFUND);

				requestId = (Integer) this.daoHelper.getTxnDao().save(
						tmtbTxnReviewReq);
				// Set the status for old Txn to PENDING
				if (NonConfigurableConstants.TRANSACTION_STATUS_ACTIVE
						.equals(oldAcquireTxn.getTxnStatus()))
					oldAcquireTxn
							.setTxnStatus(NonConfigurableConstants.TRANSACTION_STATUS_PENDING_EDIT_ACTIVE);
				else
					oldAcquireTxn
							.setTxnStatus(NonConfigurableConstants.TRANSACTION_STATUS_PENDING_EDIT_BILLED);

				this.daoHelper.getTxnDao().update(oldAcquireTxn);
			} catch (Exception e) {
				e.printStackTrace();
				throw new Exception("SQL error");
			}

		} else if (NonConfigurableConstants.TRANSACTION_SCREEN_EDIT
				.equals(txnDetails.get("actionTxn"))) {
			try {
				if (NonConfigurableConstants.TRANSACTION_STATUS_ACTIVE
						.equals(oldAcquireTxn.getTxnStatus()))
					// For txn = active, should be set to pending void
					this.setTxnDetailsForEdit(
							tmtbTxnReviewReq,
							tmtbTxnReviewReqFlow,
							txnDetails,
							oldAcquireTxn,
							NonConfigurableConstants.TRANSACTION_REQUEST_STATUS_NEW,
							NonConfigurableConstants.TRANSACTION_REQUEST_STATUS_PENDING_VOID);
				else
					// For txn = billed, should be set to pending refund
					this.setTxnDetailsForEdit(
							tmtbTxnReviewReq,
							tmtbTxnReviewReqFlow,
							txnDetails,
							oldAcquireTxn,
							NonConfigurableConstants.TRANSACTION_REQUEST_STATUS_NEW,
							NonConfigurableConstants.TRANSACTION_REQUEST_STATUS_PENDING_REFUND);
				
				requestId = (Integer) this.daoHelper.getTxnDao().save(
						tmtbTxnReviewReq);
				if (NonConfigurableConstants.TRANSACTION_STATUS_ACTIVE
						.equals(oldAcquireTxn.getTxnStatus()))
					oldAcquireTxn
							.setTxnStatus(NonConfigurableConstants.TRANSACTION_STATUS_PENDING_EDIT_ACTIVE);
				else
					oldAcquireTxn
							.setTxnStatus(NonConfigurableConstants.TRANSACTION_STATUS_PENDING_EDIT_BILLED);
				this.daoHelper.getTxnDao().update(oldAcquireTxn);
			} catch (Exception e) {
				e.printStackTrace();
				throw new Exception("SQL error");
			}
		}
		// Send email to approver personnel
		// sending email
		List<String> toEmails = new ArrayList<String>();
		List<String> ccEmails = new ArrayList<String>();
		// cc is not required
		SatbUser user = this.daoHelper.getUserDao().getUserWithRoles(CommonWindow.getUserId());
		// ccEmails.add(user.getEmail());
		List<SatbUser> approvers = this.daoHelper.getUserDao().searchUser(
				Uri.SEARCH_APPROVE_TXN);
		StringBuffer approverNames = new StringBuffer();
		for (SatbUser approver : approvers) {
			toEmails.add(approver.getEmail());
			approverNames.append(approver.getName() + ",");
		}
		approverNames
				.delete(approverNames.length() - 1, approverNames.length());
		EmailUtil
				.sendEmail(
						toEmails.toArray(new String[] {}),
						ccEmails.toArray(new String[] {}),
						ConfigurableConstants
								.getEmailText(
										ConfigurableConstants.EMAIL_TYPE_TRIPS_REQUEST_SUBMIT,
										ConfigurableConstants.EMAIL_SUBJECT),
						ConfigurableConstants
								.getEmailText(
										ConfigurableConstants.EMAIL_TYPE_TRIPS_REQUEST_SUBMIT,
										ConfigurableConstants.EMAIL_CONTENT)
								.replaceAll("#custNo#",
										txnDetails.get("acctNo"))
								.replaceAll("#submiter#", user.getName())
								.replaceAll("#acctName#",
										txnDetails.get("name"))
								.replaceAll("#userName#",
										approverNames.toString())
								.replaceAll("#jobNo#", oldAcquireTxn.getJobNo()));

		return true;
	}

	public boolean updateTxn(TmtbAcquireTxn newAcquireTxn,
			TmtbAcquireTxn oldAcquireTxn, TmtbTxnReviewReq tmtbTxnReviewReq,
			String remarks, String user) throws ConcurrencyFailureException, Exception {
		// create an approved status for the same req
		// create a new txn in tmtbacquiretxn based on union of updated
		// information off new req + old txn.
		String addBackCredit = "false";
		logger.info("update Txn");

		List<String> cardLessProductList = new ArrayList<String>();
		List<PmtbProductType> cardlessProducts = this.businessHelper.getProductTypeBusiness().getCardlessProductType();
		for(PmtbProductType cardlessProduct : cardlessProducts) {
			cardLessProductList.add(cardlessProduct.getProductTypeId().trim());
		}
		
		if (NonConfigurableConstants.TRANSACTION_SCREEN_VOID
				.equals(tmtbTxnReviewReq.getActionTxn())) {
			// Must be status = PB and it is not a complimentary trip originally
			if (NonConfigurableConstants.TRANSACTION_STATUS_PENDING_EDIT_BILLED
					.equals(oldAcquireTxn.getTxnStatus())
					&& (NonConfigurableConstants.BOOLEAN_NO
							.equalsIgnoreCase(oldAcquireTxn.getComplimentary()) || oldAcquireTxn
							.getComplimentary() == null)) {
				// VOID only - do not need to create a new transaction
				oldAcquireTxn
						.setTxnStatus(NonConfigurableConstants.TRANSACTION_STATUS_REFUND);
				// Create a new credit note for the txn
				BmtbInvoiceHeader bmtbInvoiceHeader = this.daoHelper
						.getTxnDao().getInvoiceNo(
								oldAcquireTxn.getAcquireTxnNo());
				BmtbInvoiceTxn bmtbInvoiceTxn = this.daoHelper.getTxnDao()
						.getInvoiceTxn(oldAcquireTxn.getAcquireTxnNo());

				//billed PREPAID trips will not tie to invoice
				if (bmtbInvoiceHeader != null && bmtbInvoiceTxn != null) {

					// Credit note is created no matter what is the new value.
					BmtbNote bmtbNote = new BmtbNote();
					bmtbNote.setAmtbAccount(oldAcquireTxn.getAmtbAccount());
					bmtbNote.setBmtbInvoiceHeader(bmtbInvoiceHeader);
					bmtbNote.setBmtbInvoiceTxnByIssuedInvoiceTxnNo(bmtbInvoiceTxn);
					bmtbNote.setNoteType(NonConfigurableConstants.NOTE_TYPE_CREDIT);
					bmtbNote.setNoteTxnType(NonConfigurableConstants.NOTE_TXN_TYPE_TRIP_BASED);
					bmtbNote.setNoteAmount(oldAcquireTxn.getBillableAmt());
					bmtbNote.setDiscount(ZERO);
					bmtbNote.setPromoDis(oldAcquireTxn.getPromoDisValue());
					if (oldAcquireTxn.getPmtbProductType() != null) {
						String consolidateRemarks = null;
//						if (!NonConfigurableConstants.PREMIER_SERVICE
//								.equals(oldAcquireTxn.getPmtbProductType()
//										.getProductTypeId())) {
						if (!cardLessProductList.contains(oldAcquireTxn.getPmtbProductType()
										.getProductTypeId())) {
							if (NonConfigurableConstants.BOOLEAN_YES
									.equals(oldAcquireTxn.getPmtbProductType()
											.getExternalCard())) {
								// External card
								consolidateRemarks = "Card No: "
										+ oldAcquireTxn.getExternalCardNo()
										+ "\n";
							} else {
								consolidateRemarks = "Card No: "
										+ oldAcquireTxn.getPmtbProduct()
												.getCardNo() + "\n";
							}
							consolidateRemarks = consolidateRemarks
									+ "Job No: "
									+ oldAcquireTxn.getJobNo()
									+ "\n"
									+ "Travel Date/Time: "
									+ DateUtil.convertTimestampToStr(
											oldAcquireTxn.getTripStartDt(),
											DateUtil.TRIPS_DATE_FORMAT)
									+ " TO "
									+ DateUtil.convertTimestampToStr(
											oldAcquireTxn.getTripEndDt(),
											DateUtil.TRIPS_DATE_FORMAT) + "\n"
									+ "Taxi No: " + oldAcquireTxn.getTaxiNo()
									+ "\n" + "Pickup / Destination: "
									+ oldAcquireTxn.getPickupAddress();
							if (oldAcquireTxn.getDestination() != null
									&& !"".equals(oldAcquireTxn
											.getDestination()))
								consolidateRemarks += " TO "
										+ oldAcquireTxn.getDestination() + "\n";
						} else {

							consolidateRemarks = "Job No: "
									+ oldAcquireTxn.getJobNo()
									+ "\n"
									+ "Travel Date/Time: "
									+ DateUtil.convertTimestampToStr(
											oldAcquireTxn.getTripStartDt(),
											DateUtil.TRIPS_DATE_FORMAT)
									+ " TO "
									+ DateUtil.convertTimestampToStr(
											oldAcquireTxn.getTripEndDt(),
											DateUtil.TRIPS_DATE_FORMAT) + "\n"
									+ "Taxi No: " + oldAcquireTxn.getTaxiNo()
									+ "\n" + "Pickup / Destination: "
									+ oldAcquireTxn.getPickupAddress();
							if (oldAcquireTxn.getDestination() != null
									&& !"".equals(oldAcquireTxn
											.getDestination()))
								consolidateRemarks += " TO "
										+ oldAcquireTxn.getDestination() + "\n";

						}
						bmtbNote.setRemarks(consolidateRemarks);

						AmtbAccount txnAcct = oldAcquireTxn.getAmtbAccount();
						AmtbAccount acct = this.daoHelper.getAccountDao().getAccount(String.valueOf(txnAcct.getAccountNo()));
						AmtbAccount topAcct = AccountUtil.getTopLevelAccount(acct);
						//the transaction code is populate based on the account's entity
						AmtbAccount topAcctWithEntity = this.daoHelper.getAccountDao().getAccountWithEntity(topAcct);;
						FmtbEntityMaster entityMaster = topAcctWithEntity.getFmtbArContCodeMaster().getFmtbEntityMaster();
						
						FmtbTransactionCode fmtbTransactionCode = this.daoHelper
								.getFinanceDao().getFmtbTransactionCode(oldAcquireTxn.getPmtbProductType(), entityMaster);
						if (fmtbTransactionCode != null)
							bmtbNote.setFmtbTransactionCode(fmtbTransactionCode);
						else
							throw new Exception("Missing FmtbTransactionCode");
					} else
						throw new Exception("Missing Product Type");
					bmtbNote.setProdDis(oldAcquireTxn.getProdDisValue());
					bmtbNote.setAdminFee(oldAcquireTxn.getAdminFeeValue());
					bmtbNote.setGst(oldAcquireTxn.getGstValue());
					bmtbNote.setStatus(NonConfigurableConstants.NOTE_STATUS_ACTIVE);
					bmtbNote.setCreatedDt(DateUtil
							.convertDateToTimestamp(DateUtil.getCurrentDate()));
					bmtbNote.setCreatedBy(user);
					// create the note
					try {
						this.daoHelper.getGenericDao().save(bmtbNote);
						
						//after creating the trip-based note, the note flow required to be inserted as well.
						BmtbNoteFlow newToPendingFlow = new BmtbNoteFlow();
						newToPendingFlow.setBmtbNote(bmtbNote);
						newToPendingFlow.setFromStatus(NonConfigurableConstants.NOTE_STATUS_NEW);
						newToPendingFlow.setToStatus(NonConfigurableConstants.NOTE_STATUS_PENDING);
						newToPendingFlow.setSatbUser((SatbUser)this.daoHelper.getUserDao().get(SatbUser.class, CommonWindow.getUserId()));
						newToPendingFlow.setFlowDt(DateUtil.convertDateToTimestamp(DateUtil.getCurrentDate()));
						this.save(newToPendingFlow);
						
						BmtbNoteFlow pendingToApproveFlow = new BmtbNoteFlow();
						pendingToApproveFlow.setBmtbNote(bmtbNote);
						pendingToApproveFlow.setFromStatus(NonConfigurableConstants.NOTE_STATUS_PENDING);
						pendingToApproveFlow.setToStatus(NonConfigurableConstants.NOTE_STATUS_ACTIVE);
						pendingToApproveFlow.setSatbUser((SatbUser)this.daoHelper.getUserDao().get(SatbUser.class, CommonWindow.getUserId()));
						pendingToApproveFlow.setFlowDt(DateUtil.convertDateToTimestamp(DateUtil.getCurrentDate()));
						this.save(pendingToApproveFlow);
						
					} catch (Exception e) {
						logger.error(e, e);
					}
				} else {
					logger.info("Txn not tie to invoice..");
					addBackCredit = "true";
				}
				oldAcquireTxn.setTxnStatus(NonConfigurableConstants.TRANSACTION_STATUS_REFUND);
			} else {
				// not billed but void
				oldAcquireTxn.setTxnStatus(NonConfigurableConstants.TRANSACTION_STATUS_VOID);
				addBackCredit = "true";
			}

			try {
				TmtbTxnReviewReqFlow tmtbTxnReviewReqFlow = new TmtbTxnReviewReqFlow();
				tmtbTxnReviewReqFlow.setRemarks(remarks);
				tmtbTxnReviewReqFlow.setSatbUser(this.daoHelper.getUserDao().getUserWithRoles(CommonWindow.getUserId()));
				if (!this.daoHelper.getTxnDao().updateTxn(oldAcquireTxn))
					throw new Exception("SQL error");
				// we use transaction_status_void because the status had been
				// changed in oldAcquireTxn
				if (NonConfigurableConstants.TRANSACTION_STATUS_VOID
						.equals(oldAcquireTxn.getTxnStatus()))
					this.createTxnReqStatus(
							NonConfigurableConstants.TRANSACTION_REQUEST_STATUS_PENDING_VOID,
							NonConfigurableConstants.TRANSACTION_REQUEST_STATUS_APPROVED,
							tmtbTxnReviewReq, tmtbTxnReviewReqFlow);
				else
					this.createTxnReqStatus(
							NonConfigurableConstants.TRANSACTION_REQUEST_STATUS_PENDING_REFUND,
							NonConfigurableConstants.TRANSACTION_REQUEST_STATUS_APPROVED,
							tmtbTxnReviewReq, tmtbTxnReviewReqFlow);
				
				if(addBackCredit.equalsIgnoreCase("true"))
				{
					// Add back the balance for the previous account
					this.updateAccountCreditBalanceForUI(
							oldAcquireTxn.getPmtbProduct(),
							oldAcquireTxn.getAmtbAccount(),
							oldAcquireTxn.getBillableAmt(), user);
					// Add back the product credit limit for the card if it is a
					// multi-usage card.
					// Retrieve the product back for updating
					if (oldAcquireTxn.getPmtbProduct() != null) {
						PmtbProduct oldProduct = this.daoHelper.getProductDao()
								.getProduct(oldAcquireTxn.getPmtbProduct());
	
						if (NonConfigurableConstants.BOOLEAN_YES
								.equals(oldAcquireTxn.getPmtbProductType()
										.getOneTimeUsage())) {
							if (NonConfigurableConstants.BOOLEAN_YES
									.equals(tmtbTxnReviewReq.getOtuFlag()))
								this.updateProductNotUsed(oldProduct,
										oldAcquireTxn.getTripStartDt(), user);
							// this.updateProductUsed(oldAcquireTxn.getPmtbProduct(),
							// oldAcquireTxn.getTripStartDt(), user);
						} else {
							// Check if it is a multi-usage card
							if (NonConfigurableConstants.BOOLEAN_YES
									.equals(oldAcquireTxn.getPmtbProductType()
											.getCreditLimit())) {
								// Special rules: Do not update for One-time-usage
								// card even though credit limit is Y
								if (NonConfigurableConstants.BOOLEAN_NO
										.equals(oldAcquireTxn.getPmtbProductType()
												.getOneTimeUsage())) {
									
									if(NonConfigurableConstants.getBoolean(oldProduct.getPmtbProductType().getPrepaid())){
										handleTripForPrepaidCard(oldProduct, oldAcquireTxn, PREPAID_HANDLE_TRIP_ACTION_VOID);
										
									} else {
										BigDecimal tripUsageAmount = oldAcquireTxn.getBillableAmt();
										oldProduct.setCreditBalance(oldProduct.getCreditBalance().subtract(tripUsageAmount.negate()));
										
										// 20160518 Fix for the Credit Balance Discrepancy issue
										checkReplaceCard(oldProduct.getCardNo(), tripUsageAmount.negate(), "subtract", user);
									}
									
									this.daoHelper.getProductDao().update(oldProduct);
									this.updateProductCreditLimitForUI(oldProduct, user);
								
								}
							}
						}
					}
				}
				// Send to FMS
				if (NonConfigurableConstants.BOOLEAN_YES.equals(newAcquireTxn
						.getFmsFlag())) {
					// Set the creation user for new trips for use later
					newAcquireTxn.setCreatedBy(user);
					this.updateFMSReq(newAcquireTxn);
				}
				// Send email to requestor personnel
				// sending email
				List<String> toEmails = new ArrayList<String>();
				List<String> ccEmails = new ArrayList<String>();
				StringBuffer approverNames = new StringBuffer();
				// cc is not required
				// SatbUser satbUser =
				// this.daoHelper.getUserDao().getUser(user);
				// ccEmails.add(satbUser.getEmail());
				// toEmails.add(tmtbTxnReviewReq.getTmtbTxnReviewReqFlows());
				Iterator<TmtbTxnReviewReqFlow> iter = tmtbTxnReviewReq
						.getTmtbTxnReviewReqFlows().iterator();
				if (iter != null) {
					TmtbTxnReviewReqFlow prevTmtbTxnReviewReqFlow = iter.next();
					toEmails.add(prevTmtbTxnReviewReqFlow.getSatbUser()
							.getEmail());
					approverNames.append(prevTmtbTxnReviewReqFlow.getSatbUser()
							.getName());
				}

				// Retrieve the cust no and name using acctNo
				AmtbAccount amtbAccount = this.daoHelper.getAccountDao()
						.getAccount(
								newAcquireTxn.getAmtbAccount().getAccountNo());
				while (!NonConfigurableConstants.ACCOUNT_CATEGORY_CORPORATE
						.equals(amtbAccount.getAccountCategory())
						&& !NonConfigurableConstants.ACCOUNT_CATEGORY_APPLICANT
								.equals(amtbAccount.getAccountCategory())) {
					amtbAccount = amtbAccount.getAmtbAccount();
				}
				EmailUtil
						.sendEmail(
								toEmails.toArray(new String[] {}),
								ccEmails.toArray(new String[] {}),
								ConfigurableConstants
										.getEmailText(
												ConfigurableConstants.EMAIL_TYPE_TRIPS_REQUEST_SUBMIT,
												ConfigurableConstants.EMAIL_SUBJECT),
								ConfigurableConstants
										.getEmailText(
												ConfigurableConstants.EMAIL_TYPE_TRIPS_REQUEST_APPROVED,
												ConfigurableConstants.EMAIL_CONTENT)
										.replaceAll("#custNo#",
												amtbAccount.getCustNo())
										.replaceAll("#acctName#",
												amtbAccount.getAccountName())
										.replaceAll("#userName#",
												approverNames.toString())
										.replaceAll("#jobNo#",
												newAcquireTxn.getJobNo()));
			}
			catch (ConcurrencyFailureException e) {
				throw e;
			}
			catch (Exception e) {
				e.printStackTrace();
				throw new Exception("SQL error");
			}
		} else if (NonConfigurableConstants.TRANSACTION_SCREEN_EDIT
				.equals(tmtbTxnReviewReq.getActionTxn())) {
			// Create a credit note is txn status is billed.
			// Must be status = PB and it is not a complimentary trip originally
			if (NonConfigurableConstants.TRANSACTION_STATUS_PENDING_EDIT_BILLED
					.equals(oldAcquireTxn.getTxnStatus())
					&& (NonConfigurableConstants.BOOLEAN_NO
							.equalsIgnoreCase(oldAcquireTxn.getComplimentary()) || oldAcquireTxn
							.getComplimentary() == null)) {
				// If the status is billed txn
				BmtbInvoiceHeader bmtbInvoiceHeader = this.daoHelper
						.getTxnDao().getInvoiceNo(
								oldAcquireTxn.getAcquireTxnNo());
				if(bmtbInvoiceHeader!=null){
					BmtbInvoiceTxn bmtbInvoiceTxn = this.daoHelper.getTxnDao()
							.getInvoiceTxn(oldAcquireTxn.getAcquireTxnNo());
					// Credit note is created no matter what is the new value.
					BmtbNote bmtbNote = new BmtbNote();
					bmtbNote.setAmtbAccount(oldAcquireTxn.getAmtbAccount());
					bmtbNote.setBmtbInvoiceHeader(bmtbInvoiceHeader);
					bmtbNote.setBmtbInvoiceTxnByIssuedInvoiceTxnNo(bmtbInvoiceTxn);
					bmtbNote.setNoteType(NonConfigurableConstants.NOTE_TYPE_CREDIT);
					bmtbNote.setNoteTxnType(NonConfigurableConstants.NOTE_TXN_TYPE_TRIP_BASED);
					bmtbNote.setNoteAmount(oldAcquireTxn.getBillableAmt());
					bmtbNote.setDiscount(new BigDecimal(0));
					bmtbNote.setPromoDis(oldAcquireTxn.getPromoDisValue());
					if (oldAcquireTxn.getPmtbProductType() != null) {
						String consolidateRemarks = null;
//						if (!NonConfigurableConstants.PREMIER_SERVICE
//								.equals(oldAcquireTxn.getPmtbProductType()
//										.getProductTypeId())) {
						if (!cardLessProductList.contains(oldAcquireTxn.getPmtbProductType()
										.getProductTypeId())) {
							if (NonConfigurableConstants.BOOLEAN_YES
									.equals(oldAcquireTxn.getPmtbProductType()
											.getExternalCard())) {
								// External card
								consolidateRemarks = "Card No: "
										+ oldAcquireTxn.getExternalCardNo() + "\n";
							} else {
								consolidateRemarks = "Card No: "
										+ oldAcquireTxn.getPmtbProduct()
												.getCardNo() + "\n";
							}
							consolidateRemarks = consolidateRemarks
									+ "Job No: "
									+ oldAcquireTxn.getJobNo()
									+ "\n"
									+ "Travel Date/Time: "
									+ DateUtil.convertTimestampToStr(
											oldAcquireTxn.getTripStartDt(),
											DateUtil.TRIPS_DATE_FORMAT)
									+ " TO "
									+ DateUtil.convertTimestampToStr(
											oldAcquireTxn.getTripEndDt(),
											DateUtil.TRIPS_DATE_FORMAT) + "\n"
									+ "Taxi No: " + oldAcquireTxn.getTaxiNo()
									+ "\n" + "Pickup / Destination: "
									+ oldAcquireTxn.getPickupAddress();
							if (oldAcquireTxn.getDestination() != null
									&& !"".equals(oldAcquireTxn.getDestination()))
								consolidateRemarks += " TO "
										+ oldAcquireTxn.getDestination() + "\n";
						} else {
	
							consolidateRemarks = "Job No: "
									+ oldAcquireTxn.getJobNo()
									+ "\n"
									+ "Travel Date/Time: "
									+ DateUtil.convertTimestampToStr(
											oldAcquireTxn.getTripStartDt(),
											DateUtil.TRIPS_DATE_FORMAT)
									+ " TO "
									+ DateUtil.convertTimestampToStr(
											oldAcquireTxn.getTripEndDt(),
											DateUtil.TRIPS_DATE_FORMAT) + "\n"
									+ "Taxi No: " + oldAcquireTxn.getTaxiNo()
									+ "\n" + "Pickup / Destination: "
									+ oldAcquireTxn.getPickupAddress();
							if (oldAcquireTxn.getDestination() != null
									&& !"".equals(oldAcquireTxn.getDestination()))
								consolidateRemarks += " TO "
										+ oldAcquireTxn.getDestination() + "\n";
						}
						bmtbNote.setRemarks(consolidateRemarks);
						
						AmtbAccount txnAcct = oldAcquireTxn.getAmtbAccount();
						AmtbAccount acct = this.daoHelper.getAccountDao().getAccount(String.valueOf(txnAcct.getAccountNo()));
						AmtbAccount topAcct = AccountUtil.getTopLevelAccount(acct);
						//the transaction code is populate based on the account's entity
						AmtbAccount topAcctWithEntity = this.daoHelper.getAccountDao().getAccountWithEntity(topAcct);;
						FmtbEntityMaster entityMaster = topAcctWithEntity.getFmtbArContCodeMaster().getFmtbEntityMaster();
						
						FmtbTransactionCode fmtbTransactionCode = this.daoHelper
								.getFinanceDao().getFmtbTransactionCode(oldAcquireTxn.getPmtbProductType(), entityMaster);
						if (fmtbTransactionCode != null)
							bmtbNote.setFmtbTransactionCode(fmtbTransactionCode);
						else
							throw new Exception("Missing FmtbTransactionCode");
					} else
						throw new Exception("Missing Product Type");
					bmtbNote.setProdDis(oldAcquireTxn.getProdDisValue());
					bmtbNote.setAdminFee(oldAcquireTxn.getAdminFeeValue());
					bmtbNote.setGst(oldAcquireTxn.getGstValue());
					bmtbNote.setStatus(NonConfigurableConstants.NOTE_STATUS_ACTIVE);
					bmtbNote.setCreatedDt(DateUtil.convertDateToTimestamp(DateUtil
							.getCurrentDate()));
					bmtbNote.setCreatedBy(user);
					
					// create the note
					this.daoHelper.getGenericDao().save(bmtbNote);
					
					//after creating the trip-based note, the note flow required to be inserted as well.
					BmtbNoteFlow newToPendingFlow = new BmtbNoteFlow();
					newToPendingFlow.setBmtbNote(bmtbNote);
					newToPendingFlow.setFromStatus(NonConfigurableConstants.NOTE_STATUS_NEW);
					newToPendingFlow.setToStatus(NonConfigurableConstants.NOTE_STATUS_PENDING);
					newToPendingFlow.setSatbUser((SatbUser)this.daoHelper.getUserDao().get(SatbUser.class, CommonWindow.getUserId()));
					newToPendingFlow.setFlowDt(DateUtil.convertDateToTimestamp(DateUtil.getCurrentDate()));
					this.save(newToPendingFlow);
					
					BmtbNoteFlow pendingToApproveFlow = new BmtbNoteFlow();
					pendingToApproveFlow.setBmtbNote(bmtbNote);
					pendingToApproveFlow.setFromStatus(NonConfigurableConstants.NOTE_STATUS_PENDING);
					pendingToApproveFlow.setToStatus(NonConfigurableConstants.NOTE_STATUS_ACTIVE);
					pendingToApproveFlow.setSatbUser((SatbUser)this.daoHelper.getUserDao().get(SatbUser.class, CommonWindow.getUserId()));
					pendingToApproveFlow.setFlowDt(DateUtil.convertDateToTimestamp(DateUtil.getCurrentDate()));
					this.save(pendingToApproveFlow);
				}
				
				oldAcquireTxn.setTxnStatus(NonConfigurableConstants.TRANSACTION_STATUS_REFUND);
			} else {
				// not billed but edit
				oldAcquireTxn.setTxnStatus(NonConfigurableConstants.TRANSACTION_STATUS_VOID);
			}

			try {
				TmtbTxnReviewReqFlow tmtbTxnReviewReqFlow = new TmtbTxnReviewReqFlow();
				tmtbTxnReviewReqFlow.setRemarks(remarks);
				tmtbTxnReviewReqFlow.setSatbUser(this.daoHelper.getUserDao().getUserWithRoles(CommonWindow.getUserId()));
				if (NonConfigurableConstants.TRANSACTION_STATUS_VOID
						.equals(oldAcquireTxn.getTxnStatus()))
					this.createTxnReqStatus(
							NonConfigurableConstants.TRANSACTION_REQUEST_STATUS_PENDING_VOID,
							NonConfigurableConstants.TRANSACTION_REQUEST_STATUS_APPROVED,
							tmtbTxnReviewReq, tmtbTxnReviewReqFlow);
				else
					this.createTxnReqStatus(
							NonConfigurableConstants.TRANSACTION_REQUEST_STATUS_PENDING_REFUND,
							NonConfigurableConstants.TRANSACTION_REQUEST_STATUS_APPROVED,
							tmtbTxnReviewReq, tmtbTxnReviewReqFlow);

				if (this.daoHelper.getTxnDao().updateTxn(oldAcquireTxn)) {
					if (!this.daoHelper.getTxnDao().createTxn(newAcquireTxn,
							user))
						throw new Exception("SQL concurrency error");
					// Add back the balance for the previous account
					// If the old acquier txn account is the same as the new
					// acquire txn account, combine into 1 request by sending
					// only the difference
					if (oldAcquireTxn.getAmtbAccount().getAccountNo() != newAcquireTxn
							.getAmtbAccount().getAccountNo()
							&& (oldAcquireTxn.getPmtbProduct() == newAcquireTxn
									.getPmtbProduct() || oldAcquireTxn
									.getPmtbProduct()
									.getProductNo()
									.compareTo(
											newAcquireTxn.getPmtbProduct()
													.getProductNo()) != 0)) {
						this.updateAccountCreditBalanceForUI(
								oldAcquireTxn.getPmtbProduct(),
								oldAcquireTxn.getAmtbAccount(),
								oldAcquireTxn.getBillableAmt(), user);
						// Deduct the balance for the new account
						this.updateAccountCreditBalanceForUI(newAcquireTxn
								.getPmtbProduct(), newAcquireTxn
								.getAmtbAccount(), newAcquireTxn
								.getBillableAmt().negate(), user);
					} else {
						BigDecimal diffBillableAmt = oldAcquireTxn
								.getBillableAmt().subtract(
										newAcquireTxn.getBillableAmt());
						this.updateAccountCreditBalanceForUI(
								newAcquireTxn.getPmtbProduct(),
								newAcquireTxn.getAmtbAccount(),
								diffBillableAmt, user);
					}

					if (oldAcquireTxn.getPmtbProduct() != null) {
						// Check if it is a multi-usage card
						if (NonConfigurableConstants.BOOLEAN_YES
								.equals(oldAcquireTxn.getPmtbProductType()
										.getCreditLimit())) {
							// Special rules: Do not update for One-time-usage
							// card even though credit limit is Y
							if (NonConfigurableConstants.BOOLEAN_NO
									.equals(oldAcquireTxn.getPmtbProductType()
											.getOneTimeUsage())) {
								
								PmtbProduct oldProduct = oldAcquireTxn.getPmtbProduct();
								PmtbProduct newProduct = newAcquireTxn.getPmtbProduct();
								
								BigDecimal oldTripUsageAmount = oldAcquireTxn.getBillableAmt();
								BigDecimal newTripUsageAmount = newAcquireTxn.getBillableAmt();
								
								if (oldAcquireTxn
										.getPmtbProduct()
										.getProductNo()
										.compareTo(
												newAcquireTxn.getPmtbProduct()
														.getProductNo()) != 0) {
									
									if(NonConfigurableConstants.getBoolean(oldProduct.getPmtbProductType().getPrepaid())){
										handleTripForPrepaidCard(oldProduct, oldAcquireTxn, PREPAID_HANDLE_TRIP_ACTION_VOID);
									} else {
										oldProduct.setCreditBalance(oldProduct.getCreditBalance().subtract(oldTripUsageAmount.negate()));

										// 20160518 Fix for the Credit Balance Discrepancy issue
										checkReplaceCard(oldProduct.getCardNo(), oldTripUsageAmount.negate(), "subtract", user);
									}

									this.daoHelper.getProductDao().update(oldProduct);
									this.updateProductCreditLimitForUI(oldProduct, user);
								
									
									if(NonConfigurableConstants.getBoolean(newProduct.getPmtbProductType().getPrepaid())){
										handleTripForPrepaidCard(newProduct, newAcquireTxn, PREPAID_HANDLE_TRIP_ACTION_EDIT);
									} else {
										newProduct.setCreditBalance(newProduct.getCreditBalance().subtract(newTripUsageAmount));

										// 20160518 Fix for the Credit Balance Discrepancy issue
										checkReplaceCard(newProduct.getCardNo(), newTripUsageAmount, "subtract", user);
									}
									this.daoHelper.getProductDao().update(newProduct);
									this.updateProductCreditLimitForUI(newProduct, user);
									
								} else {
									
									
									
									if(NonConfigurableConstants.getBoolean(newProduct.getPmtbProductType().getPrepaid())){
										
										handleTripForPrepaidCard(oldProduct, oldAcquireTxn, PREPAID_HANDLE_TRIP_ACTION_VOID);
										handleTripForPrepaidCard(newProduct, newAcquireTxn, PREPAID_HANDLE_TRIP_ACTION_EDIT);
										
										this.daoHelper.getProductDao().update(oldProduct);
									} else {
										
										
										BigDecimal diff = newTripUsageAmount.subtract(oldTripUsageAmount);
										newProduct.setCreditBalance(newProduct.getCreditBalance().subtract(diff));
										
										// 20160518 Fix for the Credit Balance Discrepancy issue
										checkReplaceCard(newProduct.getCardNo(), diff, "subtract", user);
									}
																	
									this.daoHelper.getProductDao().update(newProduct);
									this.updateProductCreditLimitForUI(newAcquireTxn.getPmtbProduct(), user);
								}
							}
						}

					}

					// Send to FMS
					if (NonConfigurableConstants.BOOLEAN_YES
							.equals(newAcquireTxn.getFmsFlag())) {
						newAcquireTxn.setCreatedBy(user);
						this.updateFMSReq(newAcquireTxn);
					}
					// Send email to requestor personnel
					// sending email
					List<String> toEmails = new ArrayList<String>();
					List<String> ccEmails = new ArrayList<String>();
					StringBuffer approverNames = new StringBuffer();
					// SatbUser satbUser =
					// this.daoHelper.getUserDao().getUser(user);
					// ccEmails.add(satbUser.getEmail());
					// toEmails.add(tmtbTxnReviewReq.getTmtbTxnReviewReqFlows());
					Iterator<TmtbTxnReviewReqFlow> iter = tmtbTxnReviewReq
							.getTmtbTxnReviewReqFlows().iterator();
					if (iter != null) {
						TmtbTxnReviewReqFlow prevTmtbTxnReviewReqFlow = iter
								.next();
						toEmails.add(prevTmtbTxnReviewReqFlow.getSatbUser()
								.getEmail());
						approverNames.append(prevTmtbTxnReviewReqFlow
								.getSatbUser().getName() + ",");
					}
					approverNames.delete(approverNames.length() - 1,
							approverNames.length());

					// Retrieve the cust no and name using acctNo
					logger.info("Account number: "
							+ newAcquireTxn.getAmtbAccount().getAccountNo());
					AmtbAccount amtbAccount = this.daoHelper.getAccountDao()
							.getAccount(
									newAcquireTxn.getAmtbAccount()
											.getAccountNo());
					while (!NonConfigurableConstants.ACCOUNT_CATEGORY_CORPORATE
							.equals(amtbAccount.getAccountCategory())
							&& !NonConfigurableConstants.ACCOUNT_CATEGORY_APPLICANT
									.equals(amtbAccount.getAccountCategory())) {
						amtbAccount = amtbAccount.getAmtbAccount();
					}
					EmailUtil
							.sendEmail(
									toEmails.toArray(new String[] {}),
									ccEmails.toArray(new String[] {}),
									ConfigurableConstants
											.getEmailText(
													ConfigurableConstants.EMAIL_TYPE_TRIPS_REQUEST_SUBMIT,
													ConfigurableConstants.EMAIL_SUBJECT),
									ConfigurableConstants
											.getEmailText(
													ConfigurableConstants.EMAIL_TYPE_TRIPS_REQUEST_APPROVED,
													ConfigurableConstants.EMAIL_CONTENT)
											.replaceAll("#custNo#",
													amtbAccount.getCustNo())
											.replaceAll(
													"#acctName#",
													amtbAccount
															.getAccountName())
											.replaceAll("#userName#",
													approverNames.toString())
											.replaceAll("#jobNo#",
													newAcquireTxn.getJobNo()));
				} else {
					throw new Exception("SQL error");
				}
			} 
			catch (ConcurrencyFailureException e) {
				throw e;
			}
			catch (Exception e) {
				e.printStackTrace();
				throw new Exception("SQL error");
			}

		}
		return true;
	}

	public boolean updateRejTxn(TmtbAcquireTxn oldAcquireTxn,
			TmtbTxnReviewReq tmtbTxnReviewReq, String remarks, String user)
			throws ConcurrencyFailureException, Exception {
		// Create rejection status to close the loop

		logger.info("updateRejTxn");

		if (NonConfigurableConstants.TRANSACTION_STATUS_PENDING_EDIT_BILLED
				.equals(oldAcquireTxn.getTxnStatus())) {
			try {
				TmtbTxnReviewReqFlow tmtbTxnReviewReqFlow = new TmtbTxnReviewReqFlow();
				tmtbTxnReviewReqFlow.setRemarks(remarks);
				tmtbTxnReviewReqFlow.setSatbUser(this.daoHelper.getUserDao().getUserWithRoles(CommonWindow.getUserId()));
				oldAcquireTxn
						.setTxnStatus(NonConfigurableConstants.TRANSACTION_STATUS_BILLED);
				if (!this.daoHelper.getTxnDao().updateTxn(oldAcquireTxn))
					throw new Exception("SQL error");
				this.createTxnReqStatus(
						NonConfigurableConstants.TRANSACTION_REQUEST_STATUS_PENDING_REFUND,
						NonConfigurableConstants.TRANSACTION_REQUEST_STATUS_REJECTED,
						tmtbTxnReviewReq, tmtbTxnReviewReqFlow);
				// Send email to requestor personnel
				// sending email
				List<String> toEmails = new ArrayList<String>();
				List<String> ccEmails = new ArrayList<String>();
				StringBuffer approverNames = new StringBuffer();
				// cc is not required
				// SatbUser satbUser =
				// this.daoHelper.getUserDao().getUser(user);
				// ccEmails.add(satbUser.getEmail());
				// toEmails.add(tmtbTxnReviewReq.getTmtbTxnReviewReqFlows());
				Iterator<TmtbTxnReviewReqFlow> iter = tmtbTxnReviewReq
						.getTmtbTxnReviewReqFlows().iterator();
				if (iter != null) {
					TmtbTxnReviewReqFlow prevTmtbTxnReviewReqFlow = iter.next();
					toEmails.add(prevTmtbTxnReviewReqFlow.getSatbUser()
							.getEmail());
					approverNames.append(prevTmtbTxnReviewReqFlow.getSatbUser()
							.getName());
				}

				// Retrieve the cust no and name using acctNo
				AmtbAccount amtbAccount = this.daoHelper.getAccountDao()
						.getAccount(
								oldAcquireTxn.getAmtbAccount().getAccountNo());
				while (!NonConfigurableConstants.ACCOUNT_CATEGORY_CORPORATE
						.equals(amtbAccount.getAccountCategory())
						&& !NonConfigurableConstants.ACCOUNT_CATEGORY_APPLICANT
								.equals(amtbAccount.getAccountCategory())) {
					amtbAccount = amtbAccount.getAmtbAccount();
				}
				EmailUtil
						.sendEmail(
								toEmails.toArray(new String[] {}),
								ccEmails.toArray(new String[] {}),
								ConfigurableConstants
										.getEmailText(
												ConfigurableConstants.EMAIL_TYPE_TRIPS_REQUEST_SUBMIT,
												ConfigurableConstants.EMAIL_SUBJECT),
								ConfigurableConstants
										.getEmailText(
												ConfigurableConstants.EMAIL_TYPE_TRIPS_REQUEST_REJECTED,
												ConfigurableConstants.EMAIL_CONTENT)
										.replaceAll("#custNo#",
												amtbAccount.getCustNo())
										.replaceAll("#acctName#",
												amtbAccount.getAccountName())
										.replaceAll("#userName#",
												approverNames.toString())
										.replaceAll("#jobNo#",
												oldAcquireTxn.getJobNo()));
			} 
			catch (ConcurrencyFailureException e) {
				throw e;
			}
			catch (Exception e) {
				e.printStackTrace();
				throw new Exception("SQL error");
			}
		} else if (NonConfigurableConstants.TRANSACTION_STATUS_PENDING_EDIT_ACTIVE
				.equals(oldAcquireTxn.getTxnStatus())) {
			try {
				TmtbTxnReviewReqFlow tmtbTxnReviewReqFlow = new TmtbTxnReviewReqFlow();
				tmtbTxnReviewReqFlow.setRemarks(remarks);
				tmtbTxnReviewReqFlow.setSatbUser(this.daoHelper.getUserDao().getUserWithRoles(CommonWindow.getUserId()));

				oldAcquireTxn
						.setTxnStatus(NonConfigurableConstants.TRANSACTION_STATUS_ACTIVE);
				if (!this.daoHelper.getTxnDao().updateTxn(oldAcquireTxn))
					throw new Exception("SQL error");
				this.createTxnReqStatus(
						NonConfigurableConstants.TRANSACTION_REQUEST_STATUS_PENDING_VOID,
						NonConfigurableConstants.TRANSACTION_REQUEST_STATUS_REJECTED,
						tmtbTxnReviewReq, tmtbTxnReviewReqFlow);
				// Send email to requestor personnel
				// sending email
				List<String> toEmails = new ArrayList<String>();
				List<String> ccEmails = new ArrayList<String>();
				StringBuffer approverNames = new StringBuffer();
				// cc is not required
				// SatbUser satbUser =
				// this.daoHelper.getUserDao().getUser(user);
				// ccEmails.add(satbUser.getEmail());
				// toEmails.add(tmtbTxnReviewReq.getTmtbTxnReviewReqFlows());
				Iterator<TmtbTxnReviewReqFlow> iter = tmtbTxnReviewReq
						.getTmtbTxnReviewReqFlows().iterator();
				if (iter != null) {
					TmtbTxnReviewReqFlow prevTmtbTxnReviewReqFlow = iter.next();
					toEmails.add(prevTmtbTxnReviewReqFlow.getSatbUser()
							.getEmail());
					approverNames.append(prevTmtbTxnReviewReqFlow.getSatbUser()
							.getName());
				}

				// Retrieve the cust no and name using acctNo
				AmtbAccount amtbAccount = this.daoHelper.getAccountDao()
						.getAccount(
								oldAcquireTxn.getAmtbAccount().getAccountNo());
				while (!NonConfigurableConstants.ACCOUNT_CATEGORY_CORPORATE
						.equals(amtbAccount.getAccountCategory())
						&& !NonConfigurableConstants.ACCOUNT_CATEGORY_APPLICANT
								.equals(amtbAccount.getAccountCategory())) {
					amtbAccount = amtbAccount.getAmtbAccount();
				}
				EmailUtil
						.sendEmail(
								toEmails.toArray(new String[] {}),
								ccEmails.toArray(new String[] {}),
								ConfigurableConstants
										.getEmailText(
												ConfigurableConstants.EMAIL_TYPE_TRIPS_REQUEST_SUBMIT,
												ConfigurableConstants.EMAIL_SUBJECT),
								ConfigurableConstants
										.getEmailText(
												ConfigurableConstants.EMAIL_TYPE_TRIPS_REQUEST_REJECTED,
												ConfigurableConstants.EMAIL_CONTENT)
										.replaceAll("#custNo#",
												amtbAccount.getCustNo())
										.replaceAll("#acctName#",
												amtbAccount.getAccountName())
										.replaceAll("#userName#",
												approverNames.toString())
										.replaceAll("#jobNo#",
												oldAcquireTxn.getJobNo()));
			} 
			catch (ConcurrencyFailureException e) {
				throw e;
			}
			catch (Exception e) {
				e.printStackTrace();
				throw new Exception("SQL error");
			}
		} else {
			throw new Exception("Invalid Status for TRIPS Approval.");
		}
		return true;
	}

	public void setTxnDetails(TmtbAcquireTxn tmtbAcquireTxn,
			Map<String, String> txnDetails, boolean updateViaCard)
			throws Exception {
		AmtbAccount amtbAccount = null;
		BigDecimal prodDisPercent = null, adminFeePercent = null;
		MstbProdDiscDetail discountDetail = null;
		PmtbProduct pmtbProduct = null;
		BigDecimal bdTotalAmt = BigDecimal.ZERO
				.setScale(NonConfigurableConstants.NO_OF_DECIMAL);

		MstbAdminFeeDetail adminFeeDetail = null;

		// Normal trips' fields
		java.sql.Timestamp tripStartTimeStamp = null;
		String tripStart = txnDetails.get("startDate");
		String cardNo = txnDetails.get("cardNo");
		String acctNo = txnDetails.get("acctNo");
		String name = txnDetails.get("name");
		String division = txnDetails.get("division");
		String department = txnDetails.get("department");
		String subApplicant = txnDetails.get("subApplicant");
		String billContact = txnDetails.get("billContact");
		String productType = txnDetails.get("productType");
		String taxiNo = txnDetails.get("taxiNo");
		String nric = txnDetails.get("nric");
		String tripEnd = txnDetails.get("endDate");
		String companyCd = txnDetails.get("companyCd");
		String fareAmt = txnDetails.get("fareAmt");
		String pickup = txnDetails.get("pickup");
		String destination = txnDetails.get("destination");
		String remarks = txnDetails.get("remarks");
		String txnID = txnDetails.get("txnID");
		String fmsAmt = txnDetails.get("FMSAmount");
		String incentiveAmt = txnDetails.get("incentiveAmt");
		String promoAmt = txnDetails.get("promoAmt");
		String cabRewardsAmt = txnDetails.get("cabRewardsAmt");
		String salesDraft = txnDetails.get("salesDraft");
		String updateFMS = txnDetails.get("updateFMSList");
		String toUpdateFMSList = txnDetails.get("toUpdateFMSList");
		String projCode = txnDetails.get("projCode");
		String projCodeReason = txnDetails.get("projCodeReason");
		String complimentary = txnDetails.get("complimentary");
		String vehicleType = txnDetails.get("vehicleType");
		String jobType = txnDetails.get("jobType");
		String prepaidAdminFee = txnDetails.get("prepaidAdminFee");
		String prepaidGst = txnDetails.get("prepaidGst");
		String surchargeDesc = txnDetails.get("surchargeDesc");
		String tripType = txnDetails.get("tripType");
		String jobNoGen = txnDetails.get("jobNo");
		
		logger.info("txnID: " + txnID);
		logger.info("tripStart: " + tripStart);
		logger.info("cardNo: " + cardNo);
		logger.info("acctNo: " + acctNo);
		logger.info("name: " + name);
		logger.info("division: " + division);
		logger.info("department: " + department);
		logger.info("subApplicant: " + subApplicant);
		logger.info("billContact: " + billContact);
		logger.info("productType: " + productType);
		logger.info("taxiNo: " + taxiNo);
		logger.info("nric: " + nric);
		logger.info("tripEnd: " + tripEnd);
		logger.info("companyCd: " + companyCd);
		logger.info("fareAmt: " + fareAmt);
		logger.info("pickup: " + pickup);
		logger.info("destination: " + destination);
		logger.info("remarks: " + remarks);
		logger.info("vehicleType: " + vehicleType);
		logger.info("jobType: " + jobType);
		logger.info("prepaidAdminFee: " + prepaidAdminFee);
		logger.info("prepaidGst: " + prepaidGst);
		logger.info("tripType: "+ tripType);

		if (txnID != null && !"".equals(txnID)) {
			tmtbAcquireTxn.setAcquireTxnNo(Integer.parseInt(txnID));
		}
		// Calculate the Admin Fee and GST per transaction
		// Convert the trip date to timestamp
		if (tripStart != null && !"".equals(tripStart)) {
			tripStartTimeStamp = DateUtil.convertStrToTimestamp(tripStart,
					DateUtil.TRIPS_DATE_FORMAT);
		}

		if(jobNoGen != null)
		{
			logger.info("from uploadBatch with jobNo: "+jobNoGen);
			String jobNo = jobNoGen;
			tmtbAcquireTxn.setJobNo(jobNo);
		}
		else
		{
			// Set job no - to retrieve from sequence no			
			String jobNo = this.daoHelper.getGenericDao()
					.getNextSequenceNo(Sequence.JOB_NO_SEQUENCE).toString();
			if (jobNo != null && !"".equals(jobNo))
				tmtbAcquireTxn.setJobNo(jobNo);

			/* Remove new job no feature
			String sequence = this.daoHelper.getGenericDao()
					.getNextSequenceNo(Sequence.JOB_NO_SEQUENCE3).toString();
			boolean passable = false;
			System.out.println("TESTING SYSTEM meltest sequence:"+ sequence);
			Date date = DateUtil.getCurrentDate();			
			System.out.println("TESTING SYSTEM meltest date:"+ date);
			String jobNo = date+sequence;
			System.out.println("TESTING SYSTEM meltest jobNo:"+ jobNo);
			if (jobNo != null && !"".equals(jobNo) && passable == true)
				tmtbAcquireTxn.setJobNo(jobNo);
			*/
		}
		if (updateViaCard) {
			// take account via card no
			amtbAccount = this.daoHelper.getAccountDao().getAccount(cardNo,
					tripStartTimeStamp);
		} else {
			// take account via department/division/corporate
			// If department is selected
			if (department != null && !"".equals(department)) {
				amtbAccount = this.daoHelper.getAccountDao().getAccount(
						department);
			}
			// If division is selected
			else if (division != null && !"".equals(division)) {
				amtbAccount = this.daoHelper.getAccountDao().getAccount(
						division);
			}
			// If corporate is selected
			else if (subApplicant != null && !"".equals(subApplicant)) {
				amtbAccount = this.daoHelper.getAccountDao().getAccount(
						subApplicant);
			} else {
				amtbAccount = this.daoHelper.getAccountDao().getAccount(acctNo);
			}
			tmtbAcquireTxn.setAmtbAccount(amtbAccount);
		}

		if (amtbAccount != null) {
			// set atmbaccount
			tmtbAcquireTxn.setAmtbAccount(amtbAccount);
			// Retrieve the product discount from parent corporate account
			AmtbAccount parentAccount = amtbAccount;
			logger.info("Account No: " + parentAccount.getAccountNo());
			BigDecimal prodDisValue = null;
			while (parentAccount != null) {
				if (parentAccount.getAccountCategory().equals(
						NonConfigurableConstants.ACCOUNT_CATEGORY_CORPORATE)
						|| parentAccount
								.getAccountCategory()
								.equals(NonConfigurableConstants.ACCOUNT_CATEGORY_APPLICANT)) {
					break;
				} else
					parentAccount = parentAccount.getAmtbAccount();
			}
			// Deduct credit limit from the account + product (if multi-usage)
			// Need to check if product is used for single use, if used, error

			// Set product object
			if (tripStartTimeStamp != null) {
				pmtbProduct = this.daoHelper.getProductDao().getProduct(cardNo,
						tripStartTimeStamp);

				tmtbAcquireTxn.setPmtbProduct(pmtbProduct);
				if (pmtbProduct != null) {
					// check if the product has been re-tagged before using
					// internal product_no. If it is being re-tagged, used the
					// correct re-tagged account
					// based on trip-date
					PmtbProductRetag pmtbProductRetag = this.daoHelper
							.getProductDao().getRetagProductsByDate(
									pmtbProduct.getProductNo(),
									tripStartTimeStamp);
					if (pmtbProductRetag != null) {
						// There is a retagged. set account to this retagged
						// account
						tmtbAcquireTxn.setAmtbAccount(pmtbProductRetag
								.getAmtbAccountByCurrentAccountNo());
					}
					tmtbAcquireTxn.setPmtbProductType(pmtbProduct
							.getPmtbProductType());
				} else
					throw new Exception("Product Type is null");
			} else {
				// throw exception - trip start is a mandatory field
				throw new Exception("Trip start date is null");
			}
			
			// Conversion of string to BigDecimal
			if (fareAmt != null && !"".equals(fareAmt)) {
				float lTotalAmt = Float.parseFloat(fareAmt);
				bdTotalAmt = BigDecimal.valueOf(lTotalAmt);
				tmtbAcquireTxn.setFareAmt(bdTotalAmt);
			} else {
				// throw exception - payment is a mandatory field
				throw new Exception("Payment is null");
			}
			
			if(NonConfigurableConstants.getBoolean(pmtbProduct.getPmtbProductType().getPrepaid())){
				BigDecimal fareAmount = tmtbAcquireTxn.getFareAmt();
				
				BigDecimal adminFee = new BigDecimal(prepaidAdminFee);
				BigDecimal gst = new BigDecimal(prepaidGst);
				
				tmtbAcquireTxn.setAdminFeeValue(adminFee);
				tmtbAcquireTxn.setGstValue(gst);
		
				tmtbAcquireTxn.setProdDisPercent(ZERO);
				tmtbAcquireTxn.setProdDisValue(ZERO);
				
				BigDecimal billeableAmount = fareAmount.add(adminFee).add(gst);
				
				tmtbAcquireTxn.setBillableAmt(billeableAmount);
				
			} else {
				
				tmtbAcquireTxn.setBillableAmt(tmtbAcquireTxn.getFareAmt());
			}
			
			
			// Billable amount algorithm
			// Product Type = fixed value, the billable amount should be the
			// fixed value in voucher
			if (NonConfigurableConstants.BOOLEAN_YES
					.equalsIgnoreCase(pmtbProduct.getPmtbProductType()
							.getFixedValue())) {
				tmtbAcquireTxn.setBillableAmt(pmtbProduct.getFixedValue());
				// Use billable amount for calculation
			}

			// If complimentary is "Y" set billable amount to 0
			if (complimentary != null && !"".equals(complimentary)) {
				tmtbAcquireTxn.setComplimentary(complimentary);
				if (NonConfigurableConstants.BOOLEAN_YES
						.equalsIgnoreCase(complimentary)) {
					tmtbAcquireTxn.setBillableAmt(ZERO);
				}
			}

			// Retrieve vehicle type
			if (vehicleType != null && !"".equals(vehicleType)) {
				MstbMasterTable vehicleTypeMaster = ConfigurableConstants
						.getMasterTable(ConfigurableConstants.VEHICLE_MODEL,
								vehicleType);
				tmtbAcquireTxn
						.setMstbMasterTableByVehicleModel(vehicleTypeMaster);
			}
			// Retrieve trip type
			if (jobType != null && !"".equals(jobType)) {
				MstbMasterTable jobTypeMaster = ConfigurableConstants
						.getMasterTable(ConfigurableConstants.JOB_TYPE, jobType);
				tmtbAcquireTxn.setMstbMasterTableByJobType(jobTypeMaster);
			}

			// To use billable amount for calculation
			if(!NonConfigurableConstants.getBoolean(pmtbProduct.getPmtbProductType().getPrepaid())){
			
				bdTotalAmt = tmtbAcquireTxn.getBillableAmt();
				bdTotalAmt = bdTotalAmt.setScale(
						NonConfigurableConstants.NO_OF_DECIMAL,
						BigDecimal.ROUND_HALF_UP);
	
				// Admin fee effective date is based on current date
				adminFeeDetail = this.daoHelper.getProductDao().getAdminFee(
						parentAccount.getAccountNo(),
						DateUtil.getCurrentTimestamp());
				if (adminFeeDetail != null) {
					// Calculate admin fee
					adminFeePercent = adminFeeDetail.getAdminFee();
					adminFeePercent.setScale(
							NonConfigurableConstants.NO_OF_DECIMAL,
							BigDecimal.ROUND_HALF_UP);
					tmtbAcquireTxn.setAdminFeePercent(adminFeePercent);
					BigDecimal adminFeeValue = bdTotalAmt.multiply(adminFeePercent
							.divide(HUNDRED, 4, BigDecimal.ROUND_HALF_UP));
					adminFeeValue = adminFeeValue.setScale(
							NonConfigurableConstants.NO_OF_DECIMAL,
							BigDecimal.ROUND_HALF_UP);
					tmtbAcquireTxn.setAdminFeeValue(adminFeeValue);
					logger.info("Admin Fee Value: " + adminFeeValue);
	
				} else {
					tmtbAcquireTxn.setAdminFeePercent(ZERO);
					tmtbAcquireTxn.setAdminFeeValue(ZERO);
				}
	
				// Discount effective date is based on current date
				discountDetail = this.daoHelper.getProductDao().getProductDiscount(
						parentAccount.getAccountNo(),
						DateUtil.getCurrentTimestamp(),
						pmtbProduct.getPmtbProductType().getProductTypeId());
				if (discountDetail != null) {
					prodDisPercent = discountDetail.getProductDiscount();
					prodDisPercent.setScale(NonConfigurableConstants.NO_OF_DECIMAL,
							BigDecimal.ROUND_HALF_UP);
					tmtbAcquireTxn.setProdDisPercent(prodDisPercent);
					BigDecimal disAdmFeePercent = tmtbAcquireTxn
							.getAdminFeePercent().subtract(prodDisPercent);
					BigDecimal disAdmFee = bdTotalAmt.multiply(disAdmFeePercent
							.divide(HUNDRED, 4, BigDecimal.ROUND_HALF_UP));
					disAdmFee = disAdmFee.setScale(
							NonConfigurableConstants.NO_OF_DECIMAL,
							BigDecimal.ROUND_HALF_UP);
					prodDisValue = tmtbAcquireTxn.getAdminFeeValue().subtract(
							disAdmFee);
					prodDisValue = prodDisValue.setScale(
							NonConfigurableConstants.NO_OF_DECIMAL,
							BigDecimal.ROUND_HALF_UP);
					tmtbAcquireTxn.setProdDisValue(prodDisValue);
					logger.info("Product Discount Value: " + prodDisValue);
				} else {
					tmtbAcquireTxn.setProdDisPercent(ZERO);
					tmtbAcquireTxn.setProdDisValue(ZERO);
				}
	
				// Admin fee effective date is based on current date
				AmtbAccount amtbAccountWithEntity = this.daoHelper.getAccountDao()
						.getAccountWithEntity(parentAccount);
				BigDecimal gstPercent = this.daoHelper.getFinanceDao()
						.getLatestGST(
								amtbAccountWithEntity.getFmtbArContCodeMaster()
										.getFmtbEntityMaster().getEntityNo(),
								tmtbAcquireTxn.getPmtbProductType()
										.getProductTypeId(), tripStartTimeStamp);
	
				if (gstPercent != null) {
					gstPercent.setScale(NonConfigurableConstants.NO_OF_DECIMAL,
							BigDecimal.ROUND_HALF_UP);
					tmtbAcquireTxn.setGstPercent(gstPercent);
					logger.info("GST percent: " + gstPercent);
	
					BigDecimal gstValue = (tmtbAcquireTxn.getAdminFeeValue()
							.subtract(tmtbAcquireTxn.getProdDisValue()))
							.multiply(gstPercent.divide(HUNDRED, 4,
									BigDecimal.ROUND_HALF_UP));
					gstValue = gstValue.setScale(
							NonConfigurableConstants.NO_OF_DECIMAL,
							BigDecimal.ROUND_HALF_UP);
					tmtbAcquireTxn.setGstValue(gstValue);
					logger.info("GST Fee = " + gstValue.toString());
				} else {
					// Calculate gst percent
					// BigDecimal gstPercent =
					// this.daoHelper.getTxnDao().getGstByTripDate(tripStartTimeStamp);
					gstPercent = BigDecimal.ZERO;
					gstPercent.setScale(NonConfigurableConstants.NO_OF_DECIMAL,
							BigDecimal.ROUND_HALF_UP);
					tmtbAcquireTxn.setGstPercent(gstPercent);
					logger.info("GST percent: " + gstPercent);
					// Zero GST as admin fee is zero
					tmtbAcquireTxn.setGstValue(ZERO);
				}
			}
		}

		// tmtbAcquireTxn.setProjectDesc(projectDesc)
		// Enhancement to set for promo disc and value
		tmtbAcquireTxn.setPromoDisPercent(ZERO);
		tmtbAcquireTxn.setPromoDisValue(ZERO);

		// Retrieve Company Code
		if (companyCd != null && !"".equals(companyCd)) {
			MstbMasterTable companyCdMaster = ConfigurableConstants
					.getMasterTable(ConfigurableConstants.SERVICE_PROVIDER,
							companyCd);
			tmtbAcquireTxn.setMstbMasterTableByServiceProvider(companyCdMaster);
		}

		if (destination != null && !"".equals(destination))
			tmtbAcquireTxn.setDestination(destination);

		if (nric != null && !"".equals(nric))
			tmtbAcquireTxn.setNric(nric);

		if (pickup != null && !"".equals(pickup))
			tmtbAcquireTxn.setPickupAddress(pickup);

		if (salesDraft != null && !"".equals(salesDraft))
			tmtbAcquireTxn.setSalesDraftNo(salesDraft);

		if (projCode != null && !"".equals(projCode))
			tmtbAcquireTxn.setProjectDesc(projCode);
		
		if (projCodeReason != null && !"".equals(projCodeReason))
			tmtbAcquireTxn.setTripCodeReason(projCodeReason);

		if (surchargeDesc != null && !"".equals(surchargeDesc))
			tmtbAcquireTxn.setSurcharge(surchargeDesc);

		if (updateFMS != null && !"".equals(updateFMS))
			tmtbAcquireTxn.setUpdateFms(updateFMS);

		if (fmsAmt != null && !"".equals(fmsAmt))
			tmtbAcquireTxn.setFmsAmt(new BigDecimal(fmsAmt));
		
		if (incentiveAmt != null && !"".equals(incentiveAmt))
			tmtbAcquireTxn.setIncentiveAmt((new BigDecimal(incentiveAmt)));
		
		if (promoAmt != null && !"".equals(promoAmt))
			tmtbAcquireTxn.setPromoAmt((new BigDecimal(promoAmt)));
		if (cabRewardsAmt != null && !"".equals(cabRewardsAmt))
			tmtbAcquireTxn.setCabRewardsAmt((new BigDecimal(cabRewardsAmt)));

		// Vehicle No
		if (taxiNo != null && !"".equals(taxiNo))
			tmtbAcquireTxn.setTaxiNo(taxiNo);

		// Trip End Date
		java.sql.Timestamp tripEndTimeStamp;
		if (tripEnd != null && !"".equals(tripEnd)) {
			tripEndTimeStamp = DateUtil.convertStrToTimestamp(tripEnd,
					DateUtil.TRIPS_DATE_FORMAT);
			tmtbAcquireTxn.setTripEndDt(tripEndTimeStamp);
		}

		// Trip Start Date
		if (tripStart != null && !"".equals(tripStart)) {
			tmtbAcquireTxn.setTripStartDt(tripStartTimeStamp);
		}

		if (remarks != null && !"".equals(remarks)) {
			tmtbAcquireTxn.setRemarks(remarks);
		}

		if (toUpdateFMSList != null && !"".equals(toUpdateFMSList)) {
			tmtbAcquireTxn.setFmsFlag(toUpdateFMSList);
		}

		// Set Levy to 0 for normal trips
		tmtbAcquireTxn.setLevy(ZERO);

		
		// Retrieve trip type
		if (tripType != null && !"".equals(tripType)) {
			MstbMasterTable tripTypeMaster = ConfigurableConstants
					.getMasterTable(ConfigurableConstants.VEHICLE_TRIP_TYPE,
							tripType);
			tmtbAcquireTxn.setMstbMasterTableByTripType(tripTypeMaster);
			
			if(tripTypeMaster.getInterfaceMappingValue().length() >= 4)
			{
				if(tripTypeMaster.getInterfaceMappingValue().substring(0, 4).equalsIgnoreCase("FLAT")
						&& tripTypeMaster.getMasterStatus().trim().equalsIgnoreCase("A"))	
				{
					logger.info("Trip Type is Flat Fare, setting GST & Admin to 0");
					tmtbAcquireTxn.setGstValue(ZERO);
//					tmtbAcquireTxn.setGstPercent(ZERO);
					tmtbAcquireTxn.setAdminFeeValue(ZERO);
//					tmtbAcquireTxn.setAdminFeePercent(ZERO);
					tmtbAcquireTxn.setProdDisValue(ZERO);
				}
			}
		}
		
		// To change it to use master code

		PmtbProductType pmtbProductType = tmtbAcquireTxn.getPmtbProductType();
		if(pmtbProductType!=null && NonConfigurableConstants.getBoolean(pmtbProductType.getPrepaid())){
			tmtbAcquireTxn.setTxnStatus(NonConfigurableConstants.TRANSACTION_STATUS_BILLED);
		} else {
			tmtbAcquireTxn.setTxnStatus(NonConfigurableConstants.TRANSACTION_STATUS_ACTIVE);
		}
		
	}

	public void setTxnDetailsForEdit(TmtbTxnReviewReq tmtbTxnReviewReq,
			TmtbTxnReviewReqFlow tmtbTxnReviewReqFlow,
			Map<String, String> txnDetails, TmtbAcquireTxn oldAcquireTxn,
			String fromStatus, String toStatus) throws Exception {
		AmtbAccount amtbAccount = null;
		BigDecimal bdTotalAmt = null;

		// Normal trips' fields
		java.sql.Timestamp tripStartTimeStamp = null;
		String tripStart = txnDetails.get("startDate");
		String acctNo = txnDetails.get("acctNo");
		String name = txnDetails.get("name");
		String division = txnDetails.get("division");
		String department = txnDetails.get("department");
		String subApplicant = txnDetails.get("subApplicant");
		String productType = txnDetails.get("productType");
		String tripEnd = txnDetails.get("endDate");
		String companyCd = txnDetails.get("companyCd");
		String fareAmt = txnDetails.get("fareAmt");
		String prepaidAdminFee = txnDetails.get("prepaidAdminFee");
		String prepaidGst = txnDetails.get("prepaidGst");
		
		String pickup = txnDetails.get("pickup");
		String destination = txnDetails.get("destination");
		String remarks = txnDetails.get("remarks");
		String appRemarks = txnDetails.get("appRemarks");
		String userid = txnDetails.get("userId");
		String fmsAmt = txnDetails.get("FMSAmount");
		String incentiveAmt = txnDetails.get("incentiveAmt");
		String promoAmt = txnDetails.get("promoAmt");
		String cabRewardsAmt = txnDetails.get("cabRewardsAmt");
		String updateFMS = txnDetails.get("updateFMSList");
		String toUpdateFMSList = txnDetails.get("toUpdateFMSList");
		String salesDraft = txnDetails.get("salesDraft");
		String cancelOTU = txnDetails.get("cancelOTU");
		String projCode = txnDetails.get("projCode");
		String projCodeReason = txnDetails.get("projCodeReason");
		String complimentary = txnDetails.get("complimentary");
		String vehicleType = txnDetails.get("vehicleType");
		String jobType = txnDetails.get("jobType");
		String premVehicleType = txnDetails.get("premVehicleType");
		String premJobType = txnDetails.get("premJobType");

		// Premier trips' fields
		String paxName = txnDetails.get("paxName");
		String bookedBy = txnDetails.get("bookedBy");
		String bookedRef = txnDetails.get("bookedRef");
		String flightInfo = txnDetails.get("flightInfo");
		String bookedDate = txnDetails.get("bookedDate");
		String tripType = txnDetails.get("tripType");
		String vehicleGroup = txnDetails.get("vehicleGroup");
		String levy = txnDetails.get("levy");
		String surchargeDesc = txnDetails.get("surchargeDesc");

		logger.info("tripStart: " + tripStart);
		logger.info("acctNo: " + acctNo);
		logger.info("name: " + name);
		logger.info("division: " + division);
		logger.info("department: " + department);
		logger.info("subApplicant: " + subApplicant);
		logger.info("productType: " + productType);
		logger.info("tripEnd: " + tripEnd);
		logger.info("companyCd: " + companyCd);
		logger.info("fareAmt: " + fareAmt);
		logger.info("pickup: " + pickup);
		logger.info("destination: " + destination);
		logger.info("remarks: " + remarks);
		logger.info("vehicleType: " + vehicleType);
		logger.info("jobType: " + jobType);
		logger.info("premVehicleType: " + premVehicleType);
		logger.info("premJobType: " + premJobType);
		logger.info("incentiveAmt: " + incentiveAmt);
		logger.info("promoAmt: " + promoAmt);
		logger.info("cabRewardsAmt: " + cabRewardsAmt);
		logger.info("surchargeDesc: "+ surchargeDesc);
		
		// Set the status table
		SatbUser satbUser = this.daoHelper.getUserDao().getUserWithRoles(CommonWindow.getUserId());
		tmtbTxnReviewReqFlow.setSatbUser(satbUser);
		tmtbTxnReviewReqFlow.setRemarks(appRemarks);
		tmtbTxnReviewReqFlow.setFlowDt(DateUtil.getCurrentTimestamp());
		tmtbTxnReviewReqFlow.setFromStatus(fromStatus);
		tmtbTxnReviewReqFlow.setToStatus(toStatus);
		// add hash set
		tmtbTxnReviewReq.getTmtbTxnReviewReqFlows().add(tmtbTxnReviewReqFlow);
		tmtbTxnReviewReqFlow.setTmtbTxnReviewReq(tmtbTxnReviewReq);

		// Calculate the Admin Fee and GST per transaction
		// Convert the trip date to timestamp
		if (tripStart != null && !"".equals(tripStart)) {
			tripStartTimeStamp = DateUtil.convertStrToTimestamp(tripStart,
					DateUtil.TRIPS_DATE_FORMAT);
		}

		tmtbTxnReviewReq.setActionTxn(txnDetails.get("actionTxn"));

		// take account via department/division/corporate as card no is
		// non-editable
		// They can have a txn tied to an account instead of a card.
		if (department != null && !"".equals(department)) {
			amtbAccount = this.daoHelper.getAccountDao().getAccount(department);
		}
		// If division is selected
		else if (division != null && !"".equals(division)) {
			amtbAccount = this.daoHelper.getAccountDao().getAccount(division);
		} else if (subApplicant != null && !"".equals(subApplicant)) {
			amtbAccount = this.daoHelper.getAccountDao().getAccount(
					subApplicant);
		}
		// If corporate is selected or applicant is selected
		else if (acctNo != null && !"".equals(acctNo)) {
			amtbAccount = this.daoHelper.getAccountDao().getAccountByCustNo(
					acctNo);
		} else {
			// Not setting the account info as there is no changes.
		}

		if (amtbAccount != null) {
			if (amtbAccount.getAccountNo() != oldAcquireTxn.getAmtbAccount()
					.getAccountNo()) {
				tmtbTxnReviewReq.setAmtbAccount(amtbAccount);
			}
		}

		// Conversion of string to BigDecimal
		if (fareAmt != null && !"".equals(fareAmt)) {
			float lTotalAmt = Float.parseFloat(fareAmt);
			bdTotalAmt = BigDecimal.valueOf(lTotalAmt);
			tmtbTxnReviewReq.setFareAmt(bdTotalAmt);
		}

		if (companyCd != null && !"".equals(companyCd)) {
			MstbMasterTable companyCdMaster = ConfigurableConstants
					.getMasterTable(ConfigurableConstants.SERVICE_PROVIDER,
							companyCd);
			tmtbTxnReviewReq
					.setMstbMasterTableByServiceProvider(companyCdMaster);
		}

		// Can be blank
		if (destination != null)
			tmtbTxnReviewReq.setDestination(destination);

		if (pickup != null && !"".equals(pickup))
			tmtbTxnReviewReq.setPickupAddress(pickup);

		// Can be blank
		if (remarks != null)
			tmtbTxnReviewReq.setRemarks(remarks);

		// Trip End Date
		java.sql.Timestamp tripEndTimeStamp;
		if (tripEnd != null && !"".equals(tripEnd)) {
			tripEndTimeStamp = DateUtil.convertStrToTimestamp(tripEnd,
					DateUtil.TRIPS_DATE_FORMAT);
			tmtbTxnReviewReq.setTripEndDt(tripEndTimeStamp);
		}

		// Trip Start Date
		if (tripStart != null && !"".equals(tripStart)) {
			tmtbTxnReviewReq.setTripStartDt(tripStartTimeStamp);
		}

		// Can be blank
		if (paxName != null)
			tmtbTxnReviewReq.setPassengerName(paxName);

		// Can be blank
		if (levy != null && !"".equals(levy))
			tmtbTxnReviewReq.setLevy(new BigDecimal(levy));

		if (surchargeDesc != null)
			tmtbTxnReviewReq.setSurcharge(surchargeDesc);

		if (updateFMS != null && !"".equals(updateFMS))
			tmtbTxnReviewReq.setUpdateFms(updateFMS);

		if (fmsAmt != null && !"".equals(fmsAmt))
			tmtbTxnReviewReq.setFmsAmt(new BigDecimal(fmsAmt));
		
		if (prepaidAdminFee != null && !"".equals(prepaidAdminFee))
			tmtbTxnReviewReq.setAdminFeeValue(new BigDecimal(prepaidAdminFee));
		
		if (prepaidGst != null && !"".equals(prepaidGst))
			tmtbTxnReviewReq.setGstValue(new BigDecimal(prepaidGst));
		
		if (incentiveAmt != null && !"".equals(incentiveAmt))
			tmtbTxnReviewReq.setIncentiveAmt(new BigDecimal(incentiveAmt));
		
		if(promoAmt != null && !"".equals(promoAmt))
			tmtbTxnReviewReq.setPromoAmt(new BigDecimal(promoAmt));
		
		if(cabRewardsAmt != null && !"".equals(cabRewardsAmt))
			tmtbTxnReviewReq.setCabRewardsAmt(new BigDecimal(cabRewardsAmt));
		
		if (bookedBy != null)
			tmtbTxnReviewReq.setBookedBy(bookedBy);

		if (bookedRef != null)
			tmtbTxnReviewReq.setBookingRef(bookedRef);

		if (flightInfo != null)
			tmtbTxnReviewReq.setFlightInfo(flightInfo);

		if (bookedDate != null)
			tmtbTxnReviewReq.setBookDateTime(DateUtil.convertStrToTimestamp(
					bookedDate, DateUtil.TRIPS_DATE_FORMAT));

		if (toUpdateFMSList != null && !"".equals(toUpdateFMSList))
			tmtbTxnReviewReq.setFmsFlag(toUpdateFMSList);

		if (cancelOTU != null && !"".equals(cancelOTU))
			tmtbTxnReviewReq.setOtuFlag(cancelOTU);

		if (salesDraft != null)
			tmtbTxnReviewReq.setSalesDraftNo(salesDraft);

		if (projCode != null)
			tmtbTxnReviewReq.setProjectDesc(projCode);
		
		if (projCodeReason != null)
			tmtbTxnReviewReq.setTripCodeReason(projCodeReason);

		// If complimentary is "Y" set billable amount to 0
		if (complimentary != null && !"".equals(complimentary)) {
			tmtbTxnReviewReq.setComplimentary(complimentary);
		}

		// Retrieve vehicle -type- group
		if (vehicleGroup != null && !"".equals(vehicleGroup)) {
			MstbMasterTable vehicleMaster = ConfigurableConstants
					.getMasterTable(
							ConfigurableConstants.VEHICLE_TYPE_MASTER_CODE,
							vehicleGroup);
			tmtbTxnReviewReq.setMstbMasterTableByVehicleType(vehicleMaster);
		}
		// Retrieve trip type
		if (tripType != null && !"".equals(tripType)) {
			MstbMasterTable tripTypeMaster = ConfigurableConstants
					.getMasterTable(ConfigurableConstants.VEHICLE_TRIP_TYPE,
							tripType);
			tmtbTxnReviewReq.setMstbMasterTableByTripType(tripTypeMaster);
		}
		List<String> cardLessProductList = new ArrayList<String>();
		List<PmtbProductType> cardlessProducts = this.businessHelper.getProductTypeBusiness().getCardlessProductType();
		for(PmtbProductType cardlessProduct : cardlessProducts) {
			cardLessProductList.add(cardlessProduct.getProductTypeId().trim());
		}
		
		if (cardLessProductList.contains(oldAcquireTxn
				.getPmtbProductType().getProductTypeId())) {
			if (premVehicleType != null && !"".equals(premVehicleType)) {
				MstbMasterTable vehicleTypeMaster = ConfigurableConstants
						.getMasterTable(ConfigurableConstants.VEHICLE_MODEL,
								premVehicleType);
				tmtbTxnReviewReq
						.setMstbMasterTableByVehicleModel(vehicleTypeMaster);
			}
			else if (vehicleType != null && !"".equals(vehicleType)) {
				MstbMasterTable vehicleTypeMaster = ConfigurableConstants
						.getMasterTable(ConfigurableConstants.VEHICLE_MODEL,
								vehicleType);
				tmtbTxnReviewReq
						.setMstbMasterTableByVehicleModel(vehicleTypeMaster);
			}
				
			// Retrieve job type
			if (premJobType != null && !"".equals(premJobType)) {
				MstbMasterTable jobTypeMaster = ConfigurableConstants
						.getMasterTable(ConfigurableConstants.JOB_TYPE,
								premJobType);
				tmtbTxnReviewReq.setMstbMasterTableByJobType(jobTypeMaster);
			}
			else if (jobType != null && !"".equals(jobType)) {
					MstbMasterTable jobTypeMaster = ConfigurableConstants
							.getMasterTable(ConfigurableConstants.JOB_TYPE, jobType);
					tmtbTxnReviewReq.setMstbMasterTableByJobType(jobTypeMaster);
				}
			
		} else {
			if (vehicleType != null && !"".equals(vehicleType)) {
				MstbMasterTable vehicleTypeMaster = ConfigurableConstants
						.getMasterTable(ConfigurableConstants.VEHICLE_MODEL,
								vehicleType);
				tmtbTxnReviewReq
						.setMstbMasterTableByVehicleModel(vehicleTypeMaster);
			}
			// Retrieve job type
			if (jobType != null && !"".equals(jobType)) {
				MstbMasterTable jobTypeMaster = ConfigurableConstants
						.getMasterTable(ConfigurableConstants.JOB_TYPE, jobType);
				tmtbTxnReviewReq.setMstbMasterTableByJobType(jobTypeMaster);
			}
		}

		tmtbTxnReviewReq.setPmtbProductType(oldAcquireTxn.getPmtbProductType());
		// Set product if available
		if (oldAcquireTxn.getPmtbProduct() != null)
			tmtbTxnReviewReq.setPmtbProduct(oldAcquireTxn.getPmtbProduct());
		else if (oldAcquireTxn.getExternalCardNo() != null
				&& !"".equals(oldAcquireTxn.getExternalCardNo())) {
			tmtbTxnReviewReq.setExternalCardNo(oldAcquireTxn
					.getExternalCardNo());
		}

		// Set the old transaction
		tmtbTxnReviewReq.setTmtbAcquireTxn(oldAcquireTxn);
	}

	public void setPremierTxnDetails(TmtbAcquireTxn tmtbAcquireTxn,
			Map<String, String> txnDetails) {
		AmtbAccount amtbAccount = null;
		BigDecimal prodDisPercent = null, adminFeePercent = null;
		MstbProdDiscDetail discountDetail = null;
		BigDecimal bdTotalAmt = null;
		MstbAdminFeeDetail adminFeeDetail = null;

		// Normal trips' fields
		java.sql.Timestamp tripStartTimeStamp = null;
		String tripStart = txnDetails.get("startDate");
		String cardNo = txnDetails.get("cardNo");
		String acctNo = txnDetails.get("acctNo");
		String name = txnDetails.get("name");
		String division = txnDetails.get("division");
		String department = txnDetails.get("department");
		String subApplicant = txnDetails.get("subApplicant");
		String billContact = txnDetails.get("billContact");
		String productType = txnDetails.get("productType");
		String taxiNo = txnDetails.get("taxiNo");
		String nric = txnDetails.get("nric");
		String tripEnd = txnDetails.get("endDate");
		String companyCd = txnDetails.get("companyCd");
		String fareAmt = txnDetails.get("fareAmt");
		String pickup = txnDetails.get("pickup");
		String destination = txnDetails.get("destination");
		String remarks = txnDetails.get("remarks");
		String fmsAmt = txnDetails.get("FMSAmount");
		String incentiveAmt = txnDetails.get("incentiveAmt");
		String promoAmt = txnDetails.get("promoAmt");
		String CabRewardsAmt = txnDetails.get("cabRewardsAmt");
		String updateFMS = txnDetails.get("updateFMSList");
		String toUpdateFMSList = txnDetails.get("toUpdateFMSList");
		String projCode = txnDetails.get("projCode");
		String projCodeReason = txnDetails.get("projCodeReason");
		String complimentary = txnDetails.get("complimentary");
		String vehicleType = txnDetails.get("vehicleType");
		String jobType = txnDetails.get("jobType");

		// Premier trips' fields
		String paxName = txnDetails.get("paxName");
		String bookedBy = txnDetails.get("bookedBy");
		String bookedDate = txnDetails.get("bookedDate");
		String bookedRef = txnDetails.get("bookedRef");
		String flightInfo = txnDetails.get("flightInfo");
		String tripType = txnDetails.get("tripType");
		String vehicleGroup = txnDetails.get("vehicleGroup");
		String levy = txnDetails.get("levy");
		String surchargeDesc = txnDetails.get("surchargeDesc");

		String jobNoGen = txnDetails.get("jobNo");
		
		logger.info("tripStart: " + tripStart);
		logger.info("cardNo: " + cardNo);
		logger.info("acctNo: " + acctNo);
		logger.info("name: " + name);
		logger.info("division: " + division);
		logger.info("department: " + department);
		logger.info("subApplicant: " + subApplicant);
		logger.info("billContact: " + billContact);
		logger.info("productType: " + productType);
		logger.info("taxiNo: " + taxiNo);
		logger.info("nric: " + nric);
		logger.info("tripEnd: " + tripEnd);
		logger.info("companyCd: " + companyCd);
		logger.info("fareAmt: " + fareAmt);
		logger.info("pickup: " + pickup);
		logger.info("destination: " + destination);
		logger.info("remarks: " + remarks);
		logger.info("vehicleType: " + vehicleType);
		logger.info("jobType: " + jobType);
		
		// Calculate the Admin Fee and GST per transaction
		// Convert the trip date to timestamp
		if (tripStart != null && !"".equals(tripStart)) {
			tripStartTimeStamp = DateUtil.convertStrToTimestamp(tripStart,
					DateUtil.TRIPS_DATE_FORMAT);
		}

		// New transaction no is automatically retrieve by Hibernate

		if(jobNoGen != null)
		{
			String jobNo = jobNoGen;
			tmtbAcquireTxn.setJobNo(jobNo);
		}
		else
		{
			// Set job no - to retrieve from sequence no			
			String jobNo = this.daoHelper.getGenericDao()
					.getNextSequenceNo(Sequence.JOB_NO_SEQUENCE).toString();
			if (jobNo != null && !"".equals(jobNo))
				tmtbAcquireTxn.setJobNo(jobNo);

			/* Disable new job no feature
						String sequence = this.daoHelper.getGenericDao()
								.getNextSequenceNo(Sequence.JOB_NO_SEQUENCE3).toString();
						boolean passable = false;
						System.out.println("TESTING SYSTEM meltest sequence:"+ sequence);
						String formatted = String.format("%4s", sequence).replace(" ","0");
						System.out.println("TESTING SYSTEM meltest formatted:"+ formatted);
						String date = DateUtil.getYearMonthDay();		
						System.out.println("TESTING SYSTEM meltest date:"+ date);
						String jobNo = date+formatted;
						System.out.println("TESTING SYSTEM meltest jobNo:"+ jobNo);
						if (jobNo != null && !"".equals(jobNo) && passable == true)
							tmtbAcquireTxn.setJobNo(jobNo);
			*/
		}
		if (subApplicant != null && !"".equals(subApplicant)) {
			amtbAccount = this.daoHelper.getAccountDao().getAccount(
					subApplicant);
		}
		// If department is selected
		else if (department != null && !"".equals(department)) {
			amtbAccount = this.daoHelper.getAccountDao().getAccount(department);
		}
		// If division is selected
		else if (division != null && !"".equals(division)) {
			amtbAccount = this.daoHelper.getAccountDao().getAccount(division);
		}
		// If corporate is selected
		else {
			amtbAccount = this.daoHelper.getAccountDao().getAccountByCustNo(
					acctNo);
			// amtbAccount = this.daoHelper.getAccountDao().getAccount(acctNo);
		}

		tmtbAcquireTxn.setAmtbAccount(amtbAccount);

		if (amtbAccount != null) {
			// Retrieve the product discount from parent corporate account
			AmtbAccount parentAccount = amtbAccount;
			BigDecimal prodDisValue = null;
			while (parentAccount != null) {
				if (parentAccount.getAccountCategory().equals(
						NonConfigurableConstants.ACCOUNT_CATEGORY_CORPORATE)
						|| parentAccount
								.getAccountCategory()
								.equals(NonConfigurableConstants.ACCOUNT_CATEGORY_APPLICANT)) {
					break;
				} else
					parentAccount = parentAccount.getAmtbAccount();
			}
			// Deduct credit limit from the account + product (if multi-usage)
			// Need to check if product is used for single use, if used, error

			// Set product to null as there is no product no for premier service
			tmtbAcquireTxn.setPmtbProduct(null);
			PmtbProductType pmtbProductType = this.daoHelper
					.getProductTypeDao().getProductType(
							productType);
			tmtbAcquireTxn.setPmtbProductType(pmtbProductType);

			// Conversion of string to BigDecimal
			if (fareAmt != null && !"".equals(fareAmt)) {
				float lTotalAmt = Float.parseFloat(fareAmt);
				bdTotalAmt = BigDecimal.valueOf(lTotalAmt);
				tmtbAcquireTxn.setFareAmt(bdTotalAmt);
				tmtbAcquireTxn.setBillableAmt(bdTotalAmt);
			}

			// If complimentary is "Y" set billable amount to 0
			if (complimentary != null && !"".equals(complimentary)) {
				tmtbAcquireTxn.setComplimentary(complimentary);
				if (NonConfigurableConstants.BOOLEAN_YES
						.equalsIgnoreCase(complimentary)) {
					tmtbAcquireTxn.setBillableAmt(ZERO);
				}
			}

			bdTotalAmt = tmtbAcquireTxn.getBillableAmt();
			bdTotalAmt = bdTotalAmt.setScale(
					NonConfigurableConstants.NO_OF_DECIMAL,
					BigDecimal.ROUND_HALF_UP);

			// Admin fee effective date is based on current date
			adminFeeDetail = this.daoHelper.getProductDao().getAdminFee(
					parentAccount.getAccountNo(),
					DateUtil.getCurrentTimestamp());
			if (adminFeeDetail != null) {
				// Calculate admin fee
				adminFeePercent = adminFeeDetail.getAdminFee();
				adminFeePercent.setScale(
						NonConfigurableConstants.NO_OF_DECIMAL,
						BigDecimal.ROUND_HALF_UP);
				tmtbAcquireTxn.setAdminFeePercent(adminFeePercent);
				BigDecimal adminFeeValue = bdTotalAmt.multiply(adminFeePercent
						.divide(HUNDRED, 4, BigDecimal.ROUND_HALF_UP));
				adminFeeValue = adminFeeValue.setScale(
						NonConfigurableConstants.NO_OF_DECIMAL,
						BigDecimal.ROUND_HALF_UP);
				tmtbAcquireTxn.setAdminFeeValue(adminFeeValue);
				logger.info("Admin Fee Value: " + adminFeeValue);

			} else {
				tmtbAcquireTxn.setAdminFeePercent(ZERO);
				tmtbAcquireTxn.setAdminFeeValue(ZERO);
			}

			// Discount effective date is based on current date
			discountDetail = this.daoHelper.getProductDao().getProductDiscount(
					parentAccount.getAccountNo(),
					DateUtil.getCurrentTimestamp(),
					pmtbProductType.getProductTypeId());
			if (discountDetail != null) {
				prodDisPercent = discountDetail.getProductDiscount();
				prodDisPercent.setScale(NonConfigurableConstants.NO_OF_DECIMAL,
						BigDecimal.ROUND_HALF_UP);
				tmtbAcquireTxn.setProdDisPercent(prodDisPercent);
				BigDecimal disAdmFeePercent = tmtbAcquireTxn
						.getAdminFeePercent().subtract(prodDisPercent);
				BigDecimal disAdmFee = bdTotalAmt.multiply(disAdmFeePercent
						.divide(HUNDRED, 4, BigDecimal.ROUND_HALF_UP));
				disAdmFee = disAdmFee.setScale(
						NonConfigurableConstants.NO_OF_DECIMAL,
						BigDecimal.ROUND_HALF_UP);
				prodDisValue = tmtbAcquireTxn.getAdminFeeValue().subtract(
						disAdmFee);
				prodDisValue = prodDisValue.setScale(
						NonConfigurableConstants.NO_OF_DECIMAL,
						BigDecimal.ROUND_HALF_UP);
				tmtbAcquireTxn.setProdDisValue(prodDisValue);
				logger.info("Product Discount Value: " + prodDisValue);
			} else {
				tmtbAcquireTxn.setProdDisPercent(ZERO);
				tmtbAcquireTxn.setProdDisValue(ZERO);
			}

			// Admin fee effective date is based on current date
			AmtbAccount amtbAccountWithEntity = this.daoHelper.getAccountDao()
					.getAccountWithEntity(parentAccount);
			BigDecimal gstPercent = this.daoHelper.getFinanceDao()
					.getLatestGST(
							amtbAccountWithEntity.getFmtbArContCodeMaster()
									.getFmtbEntityMaster().getEntityNo(),
							tmtbAcquireTxn.getPmtbProductType()
									.getProductTypeId(), tripStartTimeStamp);

			if (gstPercent != null) {
				gstPercent.setScale(NonConfigurableConstants.NO_OF_DECIMAL,
						BigDecimal.ROUND_HALF_UP);
				tmtbAcquireTxn.setGstPercent(gstPercent);
				logger.info("GST percent: " + gstPercent);

				BigDecimal gstValue = (tmtbAcquireTxn.getAdminFeeValue()
						.subtract(tmtbAcquireTxn.getProdDisValue()))
						.multiply(gstPercent.divide(HUNDRED, 4,
								BigDecimal.ROUND_HALF_UP));
				gstValue = gstValue.setScale(
						NonConfigurableConstants.NO_OF_DECIMAL,
						BigDecimal.ROUND_HALF_UP);
				tmtbAcquireTxn.setGstValue(gstValue);
				logger.info("GST Fee = " + gstValue.toString());
			} else {
				// Calculate gst percent
				// BigDecimal gstPercent =
				// this.daoHelper.getTxnDao().getGstByTripDate(tripStartTimeStamp);
				gstPercent = BigDecimal.ZERO;
				gstPercent.setScale(NonConfigurableConstants.NO_OF_DECIMAL,
						BigDecimal.ROUND_HALF_UP);
				tmtbAcquireTxn.setGstPercent(gstPercent);
				logger.info("GST percent: " + gstPercent);
				// Zero GST as admin fee is zero
				tmtbAcquireTxn.setGstValue(ZERO);
			}

		}

		// tmtbAcquireTxn.setProjectDesc(projectDesc)
		tmtbAcquireTxn.setPromoDisPercent(ZERO);
		tmtbAcquireTxn.setPromoDisValue(ZERO);

		// Retrieve Company Code
		if (companyCd != null && !"".equals(companyCd)) {
			MstbMasterTable companyCdMaster = ConfigurableConstants
					.getMasterTable(ConfigurableConstants.SERVICE_PROVIDER,
							companyCd);
			tmtbAcquireTxn.setMstbMasterTableByServiceProvider(companyCdMaster);
		}

		if (bookedBy != null && !"".equals(bookedBy))
			tmtbAcquireTxn.setBookedBy(bookedBy);

		if (bookedRef != null && !"".equals(bookedRef))
			tmtbAcquireTxn.setBookingRef(bookedRef);

		if (flightInfo != null && !"".equals(flightInfo))
			tmtbAcquireTxn.setFlightInfo(flightInfo);

		// Booked Date
		java.sql.Timestamp bookedTimeStamp;
		if (bookedDate != null && !"".equals(bookedDate)) {
			bookedTimeStamp = DateUtil.convertStrToTimestamp(bookedDate,
					"dd/MM/yyyy");
			tmtbAcquireTxn.setBookDateTime(bookedTimeStamp);
		}
		// tmtbAcquireTxn.setBookingRef(bookRef);

		if (destination != null && !"".equals(destination))
			tmtbAcquireTxn.setDestination(destination);

		// Retrieve vehicle type
		if (vehicleGroup != null && !"".equals(vehicleGroup)) {
			MstbMasterTable vehicleMaster = ConfigurableConstants
					.getMasterTable(
							ConfigurableConstants.VEHICLE_TYPE_MASTER_CODE,
							vehicleGroup);
			tmtbAcquireTxn.setMstbMasterTableByVehicleType(vehicleMaster);
		}
		// Retrieve trip type
		if (tripType != null && !"".equals(tripType)) {
			MstbMasterTable tripTypeMaster = ConfigurableConstants
					.getMasterTable(ConfigurableConstants.VEHICLE_TRIP_TYPE,
							tripType);
			tmtbAcquireTxn.setMstbMasterTableByTripType(tripTypeMaster);
			
			if(tripTypeMaster.getInterfaceMappingValue().length() >= 4)
			{
				if(tripTypeMaster.getInterfaceMappingValue().substring(0, 4).equalsIgnoreCase("FLAT")
						&& tripTypeMaster.getMasterStatus().trim().equalsIgnoreCase("A"))
				{
					logger.info("Trip Type is Flat Fare, setting GST & Admin to 0");
					tmtbAcquireTxn.setGstValue(ZERO);
//					tmtbAcquireTxn.setGstPercent(ZERO);
					tmtbAcquireTxn.setAdminFeeValue(ZERO);
//					tmtbAcquireTxn.setAdminFeePercent(ZERO);
					tmtbAcquireTxn.setProdDisValue(ZERO);
				}
			}
		}

		// Retrieve vehicle -model- type
		if (vehicleType != null && !"".equals(vehicleType)) {
			MstbMasterTable vehicleTypeMaster = ConfigurableConstants
					.getMasterTable(ConfigurableConstants.VEHICLE_MODEL,
							vehicleType);
			tmtbAcquireTxn.setMstbMasterTableByVehicleModel(vehicleTypeMaster);
		}
		// Retrieve job type
		if (jobType != null && !"".equals(jobType)) {
			MstbMasterTable jobTypeMaster = ConfigurableConstants
					.getMasterTable(ConfigurableConstants.JOB_TYPE, jobType);
			tmtbAcquireTxn.setMstbMasterTableByJobType(jobTypeMaster);
		}

		if (nric != null && !"".equals(nric))
			tmtbAcquireTxn.setNric(nric);

		if (remarks != null && !"".equals(remarks))
			tmtbAcquireTxn.setRemarks(remarks);

		if (projCode != null && !"".equals(projCode))
			tmtbAcquireTxn.setProjectDesc(projCode);
		
		if (projCodeReason != null && !"".equals(projCodeReason))
			tmtbAcquireTxn.setTripCodeReason(projCodeReason);

		if (paxName != null && !"".equals(paxName))
			tmtbAcquireTxn.setPassengerName(paxName);

		if (pickup != null && !"".equals(pickup))
			tmtbAcquireTxn.setPickupAddress(pickup);
		if (levy != null && !"".equals(levy))
			tmtbAcquireTxn.setLevy(new BigDecimal(levy));

		if (surchargeDesc != null && !"".equals(surchargeDesc))
			tmtbAcquireTxn.setSurcharge(surchargeDesc);

		if (updateFMS != null && !"".equals(updateFMS))
			tmtbAcquireTxn.setUpdateFms(updateFMS);

		if (fmsAmt != null && !"".equals(fmsAmt))
			tmtbAcquireTxn.setFmsAmt(new BigDecimal(fmsAmt));
		
		if (incentiveAmt != null && !"".equals(incentiveAmt))
			tmtbAcquireTxn.setIncentiveAmt(new BigDecimal(incentiveAmt));

		if(promoAmt != null && !"".equals(promoAmt))
			tmtbAcquireTxn.setPromoAmt(new BigDecimal(promoAmt));
		
		if(CabRewardsAmt != null && !"".equals(CabRewardsAmt))
			tmtbAcquireTxn.setCabRewardsAmt(new BigDecimal(CabRewardsAmt));
		
		if (toUpdateFMSList != null && !"".equals(toUpdateFMSList))
			tmtbAcquireTxn.setFmsFlag(toUpdateFMSList);

		// Vehicle No
		if (taxiNo != null && !"".equals(taxiNo))
			tmtbAcquireTxn.setTaxiNo(taxiNo);

		// Trip End Date
		java.sql.Timestamp tripEndTimeStamp;
		if (tripEnd != null && !"".equals(tripEnd)) {
			tripEndTimeStamp = DateUtil.convertStrToTimestamp(tripEnd,
					DateUtil.TRIPS_DATE_FORMAT);
			tmtbAcquireTxn.setTripEndDt(tripEndTimeStamp);
		}

		// Trip Start Date
		if (tripStart != null && !"".equals(tripStart)) {
			tmtbAcquireTxn.setTripStartDt(tripStartTimeStamp);
		}

		if (remarks != null && !"".equals(remarks)) {
			tmtbAcquireTxn.setRemarks(remarks);
		}
		tmtbAcquireTxn
				.setTxnStatus(NonConfigurableConstants.TRANSACTION_STATUS_ACTIVE);
	}

	public void setExternalTxnDetails(TmtbAcquireTxn tmtbAcquireTxn,
			Map<String, String> txnDetails) {
		AmtbAccount amtbAccount = null;
		BigDecimal prodDisPercent = null, adminFeePercent = null;
		MstbProdDiscDetail discountDetail = null;
		BigDecimal bdTotalAmt = null;
		MstbAdminFeeDetail adminFeeDetail = null;

		// Normal trips' fields
		java.sql.Timestamp tripStartTimeStamp = null;
		String tripStart = txnDetails.get("startDate");
		String cardNo = txnDetails.get("cardNo");
		String acctNo = txnDetails.get("acctNo");
		String name = txnDetails.get("name");
		String division = txnDetails.get("division");
		String department = txnDetails.get("department");
		String subApplicant = txnDetails.get("subApplicant");
		String billContact = txnDetails.get("billContact");
		String productType = txnDetails.get("productType");
		String taxiNo = txnDetails.get("taxiNo");
		String nric = txnDetails.get("nric");
		String tripEnd = txnDetails.get("endDate");
		String companyCd = txnDetails.get("companyCd");
		String fareAmt = txnDetails.get("fareAmt");
		String pickup = txnDetails.get("pickup");
		String destination = txnDetails.get("destination");
		String remarks = txnDetails.get("remarks");
		String fmsAmt = txnDetails.get("FMSAmount");
		String updateFMS = txnDetails.get("updateFMSList");
		String toUpdateFMSList = txnDetails.get("toUpdateFMSList");
		String projCode = txnDetails.get("projCode");
		String projCodeReason = txnDetails.get("projCodeReason");
		String complimentary = txnDetails.get("complimentary");
		String vehicleType = txnDetails.get("vehicleType");
		String jobType = txnDetails.get("jobType");
		String promoAmt = txnDetails.get("promoAmt");
		String cabRewardsAmt = txnDetails.get("cabRewardsAmt");
		String surchargeDesc = txnDetails.get("surchargeDesc");
		String tripType = txnDetails.get("tripType");
		
		logger.info("tripStart: " + tripStart);
		logger.info("cardNo: " + cardNo);
		logger.info("acctNo: " + acctNo);
		logger.info("name: " + name);
		logger.info("division: " + division);
		logger.info("department: " + department);
		logger.info("subApplicant: " + subApplicant);
		logger.info("billContact: " + billContact);
		logger.info("productType: " + productType);
		logger.info("taxiNo: " + taxiNo);
		logger.info("nric: " + nric);
		logger.info("tripEnd: " + tripEnd);
		logger.info("companyCd: " + companyCd);
		logger.info("fareAmt: " + fareAmt);
		logger.info("pickup: " + pickup);
		logger.info("destination: " + destination);
		logger.info("remarks: " + remarks);
		logger.info("vehicleType: " + vehicleType);
		logger.info("jobType: " + jobType);
		logger.info("tripType: " + tripType);

		tmtbAcquireTxn.setExternalCardNo(cardNo);
		// Calculate the Admin Fee and GST per transaction
		// Convert the trip date to timestamp
		if (tripStart != null && !"".equals(tripStart)) {
			tripStartTimeStamp = DateUtil.convertStrToTimestamp(tripStart,
					DateUtil.TRIPS_DATE_FORMAT);
		}

		// New transaction no is automatically retrieve by Hibernate

		// Set job no - to retrieve from sequence no
		String jobNo = this.daoHelper.getGenericDao()
				.getNextSequenceNo(Sequence.JOB_NO_SEQUENCE).toString();
		if (jobNo != null && !"".equals(jobNo))
			tmtbAcquireTxn.setJobNo(jobNo);

		if (subApplicant != null && !"".equals(subApplicant)) {
			amtbAccount = this.daoHelper.getAccountDao().getAccount(
					subApplicant);
		}
		// If department is selected
		else if (department != null && !"".equals(department)) {
			amtbAccount = this.daoHelper.getAccountDao().getAccount(department);
		}
		// If division is selected
		else if (division != null && !"".equals(division)) {
			amtbAccount = this.daoHelper.getAccountDao().getAccount(division);
		}
		// If corporate is selected
		else {
			amtbAccount = this.daoHelper.getAccountDao().getAccountByCustNo(
					acctNo);
			// amtbAccount = this.daoHelper.getAccountDao().getAccount(acctNo);
		}

		tmtbAcquireTxn.setAmtbAccount(amtbAccount);

		if (amtbAccount != null) {
			// Retrieve the product discount from parent corporate account
			AmtbAccount parentAccount = amtbAccount;
			BigDecimal prodDisValue = null;
			while (parentAccount != null) {
				if (parentAccount.getAccountCategory().equals(
						NonConfigurableConstants.ACCOUNT_CATEGORY_CORPORATE)
						|| parentAccount
								.getAccountCategory()
								.equals(NonConfigurableConstants.ACCOUNT_CATEGORY_APPLICANT)) {
					break;
				} else
					parentAccount = parentAccount.getAmtbAccount();
			}
			// Deduct credit limit from the account + product (if multi-usage)
			// Need to check if product is used for single use, if used, error

			// Set product to null as there is no product no for premier service
			tmtbAcquireTxn.setPmtbProduct(null);
			PmtbProductType pmtbProductType = this.daoHelper
					.getProductTypeDao().getProductType(productType);
			tmtbAcquireTxn.setPmtbProductType(pmtbProductType);

			// Conversion of string to BigDecimal
			if (fareAmt != null && !"".equals(fareAmt)) {
				float lTotalAmt = Float.parseFloat(fareAmt);
				bdTotalAmt = BigDecimal.valueOf(lTotalAmt);
				tmtbAcquireTxn.setFareAmt(bdTotalAmt);
				tmtbAcquireTxn.setBillableAmt(bdTotalAmt);
			}

			// If complimentary is "Y" set billable amount to 0
			if (complimentary != null && !"".equals(complimentary)) {
				tmtbAcquireTxn.setComplimentary(complimentary);
				if (NonConfigurableConstants.BOOLEAN_YES
						.equalsIgnoreCase(complimentary)) {
					tmtbAcquireTxn.setBillableAmt(ZERO);
				}
			}

			bdTotalAmt = tmtbAcquireTxn.getBillableAmt();
			bdTotalAmt = bdTotalAmt.setScale(
					NonConfigurableConstants.NO_OF_DECIMAL,
					BigDecimal.ROUND_HALF_UP);

			// Admin fee effective date is based on current date
			adminFeeDetail = this.daoHelper.getProductDao().getAdminFee(
					parentAccount.getAccountNo(),
					DateUtil.getCurrentTimestamp());
			if (adminFeeDetail != null) {
				// Calculate admin fee
				adminFeePercent = adminFeeDetail.getAdminFee();
				adminFeePercent.setScale(
						NonConfigurableConstants.NO_OF_DECIMAL,
						BigDecimal.ROUND_HALF_UP);
				tmtbAcquireTxn.setAdminFeePercent(adminFeePercent);
				BigDecimal adminFeeValue = bdTotalAmt.multiply(adminFeePercent
						.divide(HUNDRED, 4, BigDecimal.ROUND_HALF_UP));
				adminFeeValue = adminFeeValue.setScale(
						NonConfigurableConstants.NO_OF_DECIMAL,
						BigDecimal.ROUND_HALF_UP);
				tmtbAcquireTxn.setAdminFeeValue(adminFeeValue);
				logger.info("Admin Fee Value: " + adminFeeValue);
			} else {
				tmtbAcquireTxn.setAdminFeePercent(ZERO);
				tmtbAcquireTxn.setAdminFeeValue(ZERO);
			}

			// Discount effective date is based on current date
			discountDetail = this.daoHelper.getProductDao().getProductDiscount(
					parentAccount.getAccountNo(),
					DateUtil.getCurrentTimestamp(), productType);
			if (discountDetail != null) {
				prodDisPercent = discountDetail.getProductDiscount();
				prodDisPercent.setScale(NonConfigurableConstants.NO_OF_DECIMAL,
						BigDecimal.ROUND_HALF_UP);
				tmtbAcquireTxn.setProdDisPercent(prodDisPercent);
				BigDecimal disAdmFeePercent = tmtbAcquireTxn
						.getAdminFeePercent().subtract(prodDisPercent);
				BigDecimal disAdmFee = bdTotalAmt.multiply(disAdmFeePercent
						.divide(HUNDRED, 4, BigDecimal.ROUND_HALF_UP));
				disAdmFee = disAdmFee.setScale(
						NonConfigurableConstants.NO_OF_DECIMAL,
						BigDecimal.ROUND_HALF_UP);
				prodDisValue = tmtbAcquireTxn.getAdminFeeValue().subtract(
						disAdmFee);
				prodDisValue = prodDisValue.setScale(
						NonConfigurableConstants.NO_OF_DECIMAL,
						BigDecimal.ROUND_HALF_UP);
				tmtbAcquireTxn.setProdDisValue(prodDisValue);
				logger.info("Product Discount Value: " + prodDisValue);
			} else {
				tmtbAcquireTxn.setProdDisPercent(ZERO);
				tmtbAcquireTxn.setProdDisValue(ZERO);
			}

			// Admin fee effective date is based on current date
			AmtbAccount amtbAccountWithEntity = this.daoHelper.getAccountDao()
					.getAccountWithEntity(parentAccount);
			BigDecimal gstPercent = this.daoHelper.getFinanceDao()
					.getLatestGST(
							amtbAccountWithEntity.getFmtbArContCodeMaster()
									.getFmtbEntityMaster().getEntityNo(),
							tmtbAcquireTxn.getPmtbProductType()
									.getProductTypeId(), tripStartTimeStamp);

			if (gstPercent != null) {
				gstPercent.setScale(NonConfigurableConstants.NO_OF_DECIMAL,
						BigDecimal.ROUND_HALF_UP);
				tmtbAcquireTxn.setGstPercent(gstPercent);
				logger.info("GST percent: " + gstPercent);

				BigDecimal gstValue = (tmtbAcquireTxn.getAdminFeeValue()
						.subtract(tmtbAcquireTxn.getProdDisValue()))
						.multiply(gstPercent.divide(HUNDRED, 4,
								BigDecimal.ROUND_HALF_UP));
				gstValue = gstValue.setScale(
						NonConfigurableConstants.NO_OF_DECIMAL,
						BigDecimal.ROUND_HALF_UP);
				tmtbAcquireTxn.setGstValue(gstValue);
				logger.info("GST Fee = " + gstValue.toString());
			} else {
				// Calculate gst percent
				// BigDecimal gstPercent =
				// this.daoHelper.getTxnDao().getGstByTripDate(tripStartTimeStamp);
				gstPercent = BigDecimal.ZERO;
				gstPercent.setScale(NonConfigurableConstants.NO_OF_DECIMAL,
						BigDecimal.ROUND_HALF_UP);
				tmtbAcquireTxn.setGstPercent(gstPercent);
				logger.info("GST percent: " + gstPercent);
				// Zero GST as admin fee is zero
				tmtbAcquireTxn.setGstValue(ZERO);
			}

		}

		// tmtbAcquireTxn.setProjectDesc(projectDesc)
		tmtbAcquireTxn.setPromoDisPercent(ZERO);
		tmtbAcquireTxn.setPromoDisValue(ZERO);

		// Retrieve Company Code
		if (companyCd != null && !"".equals(companyCd)) {
			MstbMasterTable companyCdMaster = ConfigurableConstants
					.getMasterTable(ConfigurableConstants.SERVICE_PROVIDER,
							companyCd);
			tmtbAcquireTxn.setMstbMasterTableByServiceProvider(companyCdMaster);
		}

		if (destination != null && !"".equals(destination))
			tmtbAcquireTxn.setDestination(destination);

		// Retrieve vehicle model
		if (vehicleType != null && !"".equals(vehicleType)) {
			MstbMasterTable vehicleTypeMaster = ConfigurableConstants
					.getMasterTable(ConfigurableConstants.VEHICLE_MODEL,
							vehicleType);
			tmtbAcquireTxn.setMstbMasterTableByVehicleModel(vehicleTypeMaster);
		}
		
		// Retrieve trip type
		if (tripType != null && !"".equals(tripType)) {
			MstbMasterTable tripTypeMaster = ConfigurableConstants
					.getMasterTable(ConfigurableConstants.VEHICLE_TRIP_TYPE,
							tripType);
			tmtbAcquireTxn.setMstbMasterTableByTripType(tripTypeMaster);
			
			if(tripTypeMaster.getInterfaceMappingValue().length() >= 4)
			{
				if(tripTypeMaster.getInterfaceMappingValue().substring(0, 4).equalsIgnoreCase("FLAT")
						&& tripTypeMaster.getMasterStatus().trim().equalsIgnoreCase("A"))
				{
					logger.info("Trip Type is Flat Fare, setting GST & Admin to 0");
					tmtbAcquireTxn.setGstValue(ZERO);
//					tmtbAcquireTxn.setGstPercent(ZERO);
					tmtbAcquireTxn.setAdminFeeValue(ZERO);
//					tmtbAcquireTxn.setAdminFeePercent(ZERO);
					tmtbAcquireTxn.setProdDisValue(ZERO);
				}
			}
		}
		
		// Retrieve job type
		if (jobType != null && !"".equals(jobType)) {
			MstbMasterTable jobTypeMaster = ConfigurableConstants
					.getMasterTable(ConfigurableConstants.JOB_TYPE, jobType);
			tmtbAcquireTxn.setMstbMasterTableByJobType(jobTypeMaster);
		}

		if (nric != null && !"".equals(nric))
			tmtbAcquireTxn.setNric(nric);

		if (remarks != null && !"".equals(remarks))
			tmtbAcquireTxn.setRemarks(remarks);

		if (projCode != null && !"".equals(projCode))
			tmtbAcquireTxn.setProjectDesc(projCode);
		
		if (projCodeReason != null && !"".equals(projCodeReason))
			tmtbAcquireTxn.setTripCodeReason(projCodeReason);

		if (pickup != null && !"".equals(pickup))
			tmtbAcquireTxn.setPickupAddress(pickup);

		if (updateFMS != null && !"".equals(updateFMS))
			tmtbAcquireTxn.setUpdateFms(updateFMS);

		if (fmsAmt != null && !"".equals(fmsAmt))
			tmtbAcquireTxn.setFmsAmt(new BigDecimal(fmsAmt));

		if (surchargeDesc != null && !"".equals(surchargeDesc))
			tmtbAcquireTxn.setSurcharge(surchargeDesc);
		
		if (promoAmt != null && !"".equals(promoAmt))
			tmtbAcquireTxn.setPromoAmt(new BigDecimal(promoAmt));
		
		if (cabRewardsAmt != null && !"".equals(cabRewardsAmt))
			tmtbAcquireTxn.setCabRewardsAmt(new BigDecimal(cabRewardsAmt));
		
		if (toUpdateFMSList != null && !"".equals(toUpdateFMSList))
			tmtbAcquireTxn.setFmsFlag(toUpdateFMSList);

		// Vehicle No
		if (taxiNo != null && !"".equals(taxiNo))
			tmtbAcquireTxn.setTaxiNo(taxiNo);

		// Trip End Date
		java.sql.Timestamp tripEndTimeStamp;
		if (tripEnd != null && !"".equals(tripEnd)) {
			tripEndTimeStamp = DateUtil.convertStrToTimestamp(tripEnd,
					DateUtil.TRIPS_DATE_FORMAT);
			tmtbAcquireTxn.setTripEndDt(tripEndTimeStamp);
		}

		// Trip Start Date
		if (tripStart != null && !"".equals(tripStart)) {
			tmtbAcquireTxn.setTripStartDt(tripStartTimeStamp);
		}

		if (remarks != null && !"".equals(remarks)) {
			tmtbAcquireTxn.setRemarks(remarks);
		}
		tmtbAcquireTxn
				.setTxnStatus(NonConfigurableConstants.TRANSACTION_STATUS_ACTIVE);
	}

	public PmtbProductType getProductType(String cardNo, java.util.Date tripDt) {
		PmtbProductType pmtbProductType = this.daoHelper.getProductTypeDao()
				.getProductType(cardNo, tripDt);
		return pmtbProductType;
	}

	public List<TmtbTxnReviewReq> searchRemark(String jobNo, Integer txnNo, String txnType) {
		return this.daoHelper.getTxnDao().getRemark(jobNo, txnNo, txnType);
	}
	
	public List<TmtbAcquireTxn> searchTxns(TxnSearchCriteria txnSearchCriteria) {
		return this.daoHelper.getTxnDao().getTxns(txnSearchCriteria);
	}

	public TmtbAcquireTxn searchTxn(String txnID) {
		return this.daoHelper.getTxnDao().getTxn(txnID);
	}

	public TmtbTxnReviewReq getTxnReq(String txnReqID) {
		return this.daoHelper.getTxnDao().getTxnReq(txnReqID);
	}

	public List<TmtbTxnReviewReq> getTxnReqs(String txnID) {
		return this.daoHelper.getTxnDao().getTxnApprovedReqs(txnID);
	}

	public List<TmtbTxnReviewReq> searchTxnReqs() {
		return this.daoHelper.getTxnDao().getTxnReqs();
	}

	public boolean updateTxnReqStatus(TmtbAcquireTxn oldAcquireTxn,
			TmtbTxnReviewReq tmtbTxnReviewReq, String remarks) throws Exception {
		if (NonConfigurableConstants.TRANSACTION_SCREEN_VOID
				.equals(tmtbTxnReviewReq.getActionTxn())) {
			try {
				TmtbTxnReviewReqFlow tmtbTxnReviewReqFlow = new TmtbTxnReviewReqFlow();
				tmtbTxnReviewReqFlow.setRemarks(remarks);
				if (NonConfigurableConstants.TRANSACTION_STATUS_ACTIVE
						.equals(oldAcquireTxn.getTxnStatus()))
					this.createTxnReqStatus(
							NonConfigurableConstants.TRANSACTION_REQUEST_STATUS_PENDING_VOID,
							NonConfigurableConstants.TRANSACTION_REQUEST_STATUS_REJECTED,
							tmtbTxnReviewReq, tmtbTxnReviewReqFlow);
				else
					this.createTxnReqStatus(
							NonConfigurableConstants.TRANSACTION_REQUEST_STATUS_PENDING_REFUND,
							NonConfigurableConstants.TRANSACTION_REQUEST_STATUS_REJECTED,
							tmtbTxnReviewReq, tmtbTxnReviewReqFlow);

			} catch (Exception e) {
				e.printStackTrace();
				throw new Exception("SQL error");
			}
		} else if (NonConfigurableConstants.TRANSACTION_SCREEN_EDIT
				.equals(tmtbTxnReviewReq.getActionTxn())) {
			try {
				TmtbTxnReviewReqFlow tmtbTxnReviewReqFlow = new TmtbTxnReviewReqFlow();
				tmtbTxnReviewReqFlow.setRemarks(remarks);
				if (NonConfigurableConstants.TRANSACTION_STATUS_ACTIVE
						.equals(oldAcquireTxn.getTxnStatus()))
					this.createTxnReqStatus(
							NonConfigurableConstants.TRANSACTION_REQUEST_STATUS_PENDING_VOID,
							NonConfigurableConstants.TRANSACTION_REQUEST_STATUS_REJECTED,
							tmtbTxnReviewReq, tmtbTxnReviewReqFlow);
				else
					this.createTxnReqStatus(
							NonConfigurableConstants.TRANSACTION_REQUEST_STATUS_PENDING_REFUND,
							NonConfigurableConstants.TRANSACTION_REQUEST_STATUS_REJECTED,
							tmtbTxnReviewReq, tmtbTxnReviewReqFlow);

			} catch (Exception e) {
				e.printStackTrace();
				throw new Exception("SQL error");
			}

		}
		return true;
	}

	private boolean createTxnReqStatus(String fromStatus, String toStatus,
			TmtbTxnReviewReq tmtbTxnReviewReq,
			TmtbTxnReviewReqFlow tmtbTxnReviewReqFlow) {
		tmtbTxnReviewReqFlow.setFlowDt(DateUtil.getCurrentTimestamp());
		tmtbTxnReviewReqFlow.setFromStatus(fromStatus);
		tmtbTxnReviewReqFlow.setToStatus(toStatus);
		tmtbTxnReviewReqFlow.setTmtbTxnReviewReq(tmtbTxnReviewReq);
		// Set the parent request flow from request table
		Iterator<TmtbTxnReviewReqFlow> iter = tmtbTxnReviewReq
				.getTmtbTxnReviewReqFlows().iterator();
		TmtbTxnReviewReqFlow temp = iter.next();
		// Retrieve the first one as it is the latest status.
		temp.getTmtbTxnReviewReqFlows().add(tmtbTxnReviewReqFlow);
		tmtbTxnReviewReqFlow.setTmtbTxnReviewReqFlow(temp);

		this.daoHelper.getTxnDao().save(tmtbTxnReviewReqFlow);
		return true;
	}

	private boolean populateNewAcquireTxn(TmtbAcquireTxn newAcquireTxn,
			TmtbAcquireTxn oldAcquireTxn, TmtbTxnReviewReq tmtbTxnReviewReq)
			throws Exception {
		BigDecimal prodDisPercent = null, adminFeePercent = null;
		MstbProdDiscDetail discountDetail = null;
		BigDecimal bdTotalAmt = null;
		MstbAdminFeeDetail adminFeeDetail = null;

		// ****** Set values that will not changed
		newAcquireTxn.setJobNo(oldAcquireTxn.getJobNo());
		newAcquireTxn.setNric(oldAcquireTxn.getNric());
		newAcquireTxn.setTaxiNo(oldAcquireTxn.getTaxiNo());
		newAcquireTxn.setUpdatedBy(oldAcquireTxn.getUpdatedBy());
		newAcquireTxn.setUpdatedDt(oldAcquireTxn.getUpdatedDt());
		if (oldAcquireTxn.getExternalCardNo() != null
				&& !"".equals(oldAcquireTxn.getExternalCardNo())) {
			newAcquireTxn.setExternalCardNo(oldAcquireTxn.getExternalCardNo());
		}
		// added distance into the not changed list on 09/04/18
		if (oldAcquireTxn.getDistance() != null)
			newAcquireTxn.setDistance(oldAcquireTxn.getDistance());
		
		// Transfer job type
		// newAcquireTxn.setJobType(oldAcquireTxn.getJobType());
		newAcquireTxn.setMstbMasterTableByJobType(oldAcquireTxn
				.getMstbMasterTableByJobType());

		newAcquireTxn.setIttbCpGuestProduct(oldAcquireTxn
				.getIttbCpGuestProduct());

		if (tmtbTxnReviewReq.getAmtbAccount() != null)
			// Use new account value
			newAcquireTxn.setAmtbAccount(tmtbTxnReviewReq.getAmtbAccount());
		else
			newAcquireTxn.setAmtbAccount(oldAcquireTxn.getAmtbAccount());

		// ******* get Parent account for calculation
		// Retrieve the product discount from parent corporate account
		AmtbAccount parentAccount = newAcquireTxn.getAmtbAccount();
		logger.info("Account No: " + parentAccount.getAccountNo());
		BigDecimal prodDisValue = null;
		while (parentAccount != null) {
			if (parentAccount.getAccountCategory().equals(
					NonConfigurableConstants.ACCOUNT_CATEGORY_CORPORATE)
					|| parentAccount
							.getAccountCategory()
							.equals(NonConfigurableConstants.ACCOUNT_CATEGORY_APPLICANT)) {
				break;
			} else
				parentAccount = parentAccount.getAmtbAccount();
		}

		// ****** Check for change in fare

		if (tmtbTxnReviewReq.getFareAmt() != null) {
			newAcquireTxn.setFareAmt(tmtbTxnReviewReq.getFareAmt());
		} else {
			newAcquireTxn.setFareAmt(oldAcquireTxn.getFareAmt());
		}
		
		if(NonConfigurableConstants.getBoolean(oldAcquireTxn.getPmtbProductType().getPrepaid())){
			BigDecimal fareAmount = newAcquireTxn.getFareAmt() !=null ? newAcquireTxn.getFareAmt() : BigDecimal.ZERO;
			
			if (tmtbTxnReviewReq.getAdminFeeValue() != null) {
				newAcquireTxn.setAdminFeeValue(tmtbTxnReviewReq.getAdminFeeValue());
			} else {
				newAcquireTxn.setAdminFeeValue(oldAcquireTxn.getAdminFeeValue());
			}
			
			if (tmtbTxnReviewReq.getGstValue() != null) {
				newAcquireTxn.setGstValue(tmtbTxnReviewReq.getGstValue());
			} else {
				newAcquireTxn.setGstValue(oldAcquireTxn.getGstValue());
			}
			
			BigDecimal adminFee =  newAcquireTxn.getAdminFeeValue() !=null ? newAcquireTxn.getAdminFeeValue() : BigDecimal.ZERO;
			BigDecimal gst =  newAcquireTxn.getGstValue() !=null ? newAcquireTxn.getGstValue() : BigDecimal.ZERO;
		
			BigDecimal billeableAmount = fareAmount.add(adminFee).add(gst);
			newAcquireTxn.setBillableAmt(billeableAmount);
	
			newAcquireTxn.setProdDisPercent(ZERO);
			newAcquireTxn.setProdDisValue(ZERO);
			
		} else {
			
			newAcquireTxn.setBillableAmt(newAcquireTxn.getFareAmt());
		}
		
		

		if (tmtbTxnReviewReq.getComplimentary() != null) {
			newAcquireTxn.setComplimentary(tmtbTxnReviewReq.getComplimentary());
			if (NonConfigurableConstants.BOOLEAN_YES
					.equalsIgnoreCase(tmtbTxnReviewReq.getComplimentary())) {
				// Zero billable amount if complimentary
				newAcquireTxn.setBillableAmt(ZERO);
			}
		}

		// ******** No change in product but assigned to new txn
		if (oldAcquireTxn.getPmtbProduct() != null) {
			newAcquireTxn.setPmtbProduct(oldAcquireTxn.getPmtbProduct());
		}

		if (oldAcquireTxn.getPmtbProductType() != null) {
			newAcquireTxn
					.setPmtbProductType(oldAcquireTxn.getPmtbProductType());
			if (oldAcquireTxn.getPmtbProductType().getFixedValue()
					.equals(NonConfigurableConstants.BOOLEAN_YES)) {
				// if product fixed value, set to product fixed value for
				// billable amt
				newAcquireTxn.setBillableAmt(oldAcquireTxn.getPmtbProduct()
						.getFixedValue());
			}
		}

		bdTotalAmt = newAcquireTxn.getBillableAmt();
		bdTotalAmt = bdTotalAmt.setScale(
				NonConfigurableConstants.NO_OF_DECIMAL,
				BigDecimal.ROUND_HALF_UP);

		// ****** Check for change in remarks
		newAcquireTxn.setRemarks(tmtbTxnReviewReq.getRemarks());
		newAcquireTxn.setTripCodeReason(tmtbTxnReviewReq.getTripCodeReason());

		// ****** Check for trip start
		if (tmtbTxnReviewReq.getTripStartDt() != null)
			newAcquireTxn.setTripStartDt(tmtbTxnReviewReq.getTripStartDt());
		else
			newAcquireTxn.setTripStartDt(oldAcquireTxn.getTripStartDt());

		// ****** Check for trip end
		if (tmtbTxnReviewReq.getTripEndDt() != null)
			newAcquireTxn.setTripEndDt(tmtbTxnReviewReq.getTripEndDt());
		else
			newAcquireTxn.setTripEndDt(oldAcquireTxn.getTripEndDt());

		// ****** Check for company code
		if (tmtbTxnReviewReq.getMstbMasterTableByServiceProvider() != null)
			newAcquireTxn.setMstbMasterTableByServiceProvider(tmtbTxnReviewReq
					.getMstbMasterTableByServiceProvider());
		else
			newAcquireTxn.setMstbMasterTableByServiceProvider(oldAcquireTxn
					.getMstbMasterTableByServiceProvider());

		// ***** Check for pickup address
		if (tmtbTxnReviewReq.getPickupAddress() != null)
			newAcquireTxn.setPickupAddress(tmtbTxnReviewReq.getPickupAddress());
		else
			newAcquireTxn.setPickupAddress(oldAcquireTxn.getPickupAddress());

		// ***** Check for destination address

		newAcquireTxn.setDestination(tmtbTxnReviewReq.getDestination());
		// ***** Check for Premier Service fields
		// ***** Booked By
		newAcquireTxn.setBookedBy(tmtbTxnReviewReq.getBookedBy());
		// ***** Book Ref
		newAcquireTxn.setBookingRef(tmtbTxnReviewReq.getBookingRef());
		// ***** Book Date
		newAcquireTxn.setBookDateTime(tmtbTxnReviewReq.getBookDateTime());
		// ***** Flight Info
		newAcquireTxn.setFlightInfo(tmtbTxnReviewReq.getFlightInfo());

		newAcquireTxn.setFmsFlag(tmtbTxnReviewReq.getFmsFlag());
		
		newAcquireTxn.setUpdateFms(tmtbTxnReviewReq.getUpdateFms());
		newAcquireTxn.setFmsAmt(tmtbTxnReviewReq.getFmsAmt());
		newAcquireTxn.setIncentiveAmt(tmtbTxnReviewReq.getIncentiveAmt());
		newAcquireTxn.setPromoAmt(tmtbTxnReviewReq.getPromoAmt());
		newAcquireTxn.setCabRewardsAmt(tmtbTxnReviewReq.getCabRewardsAmt());
		newAcquireTxn.setLevy(tmtbTxnReviewReq.getLevy());
		

		if (tmtbTxnReviewReq.getMstbMasterTableByTripType() != null)
			newAcquireTxn.setMstbMasterTableByTripType(tmtbTxnReviewReq
					.getMstbMasterTableByTripType());
		else
			newAcquireTxn.setMstbMasterTableByTripType(oldAcquireTxn
					.getMstbMasterTableByTripType());

		if (tmtbTxnReviewReq.getMstbMasterTableByVehicleType() != null)
			newAcquireTxn.setMstbMasterTableByVehicleType(tmtbTxnReviewReq
					.getMstbMasterTableByVehicleType());
		else
			newAcquireTxn.setMstbMasterTableByVehicleType(oldAcquireTxn
					.getMstbMasterTableByVehicleType());

		if (tmtbTxnReviewReq.getMstbMasterTableByJobType() != null)
			newAcquireTxn.setMstbMasterTableByJobType(tmtbTxnReviewReq
					.getMstbMasterTableByJobType());
		else
			newAcquireTxn.setMstbMasterTableByJobType(oldAcquireTxn
					.getMstbMasterTableByJobType());

		if (tmtbTxnReviewReq.getMstbMasterTableByVehicleModel() != null)
			newAcquireTxn.setMstbMasterTableByVehicleModel(tmtbTxnReviewReq
					.getMstbMasterTableByVehicleModel());
		else
			newAcquireTxn.setMstbMasterTableByVehicleModel(oldAcquireTxn
					.getMstbMasterTableByVehicleModel());

		if(tmtbTxnReviewReq.getProjectDesc() != null && 
				tmtbTxnReviewReq.getProjectDesc().length() > 0)
			newAcquireTxn.setProjectDesc(tmtbTxnReviewReq.getProjectDesc());
		
		newAcquireTxn.setPassengerName(tmtbTxnReviewReq.getPassengerName());
		newAcquireTxn.setSurcharge(tmtbTxnReviewReq.getSurcharge());

		
		if(!NonConfigurableConstants.getBoolean(oldAcquireTxn.getPmtbProductType().getPrepaid())){
		
			// Admin fee effective date is based on current date
			adminFeeDetail = this.daoHelper.getProductDao().getAdminFee(
					parentAccount.getAccountNo(), DateUtil.getCurrentTimestamp());
			if (adminFeeDetail != null) {
				// Calculate admin fee
				adminFeePercent = adminFeeDetail.getAdminFee();
				adminFeePercent.setScale(NonConfigurableConstants.NO_OF_DECIMAL,
						BigDecimal.ROUND_HALF_UP);
				newAcquireTxn.setAdminFeePercent(adminFeePercent);
				BigDecimal adminFeeValue = bdTotalAmt.multiply(adminFeePercent
						.divide(HUNDRED, 4, BigDecimal.ROUND_HALF_UP));
				adminFeeValue = adminFeeValue.setScale(
						NonConfigurableConstants.NO_OF_DECIMAL,
						BigDecimal.ROUND_HALF_UP);
				newAcquireTxn.setAdminFeeValue(adminFeeValue);
				logger.info("Admin Fee Value: " + adminFeeValue);
	
			} else {
				newAcquireTxn.setAdminFeePercent(ZERO);
				newAcquireTxn.setAdminFeeValue(ZERO);
			}
	
			// Discount effective date is based on current date
			discountDetail = this.daoHelper.getProductDao().getProductDiscount(
					parentAccount.getAccountNo(), DateUtil.getCurrentTimestamp(),
					newAcquireTxn.getPmtbProductType().getProductTypeId());
			if (discountDetail != null) {
				prodDisPercent = discountDetail.getProductDiscount();
				prodDisPercent.setScale(NonConfigurableConstants.NO_OF_DECIMAL,
						BigDecimal.ROUND_HALF_UP);
				newAcquireTxn.setProdDisPercent(prodDisPercent);
				BigDecimal disAdmFeePercent = newAcquireTxn.getAdminFeePercent()
						.subtract(prodDisPercent);
				BigDecimal disAdmFee = bdTotalAmt.multiply(disAdmFeePercent.divide(
						HUNDRED, 4, BigDecimal.ROUND_HALF_UP));
				disAdmFee = disAdmFee.setScale(
						NonConfigurableConstants.NO_OF_DECIMAL,
						BigDecimal.ROUND_HALF_UP);
				prodDisValue = newAcquireTxn.getAdminFeeValue().subtract(disAdmFee);
				prodDisValue = prodDisValue.setScale(
						NonConfigurableConstants.NO_OF_DECIMAL,
						BigDecimal.ROUND_HALF_UP);
				newAcquireTxn.setProdDisValue(prodDisValue);
				logger.info("Product Discount Value: " + prodDisValue);
			} else {
				newAcquireTxn.setProdDisPercent(ZERO);
				newAcquireTxn.setProdDisValue(ZERO);
			}
	
			// Admin fee effective date is based on current date
			AmtbAccount amtbAccountWithEntity = this.daoHelper.getAccountDao()
					.getAccountWithEntity(parentAccount);
			BigDecimal gstPercent = this.daoHelper.getFinanceDao().getLatestGST(
					amtbAccountWithEntity.getFmtbArContCodeMaster()
							.getFmtbEntityMaster().getEntityNo(),
					newAcquireTxn.getPmtbProductType().getProductTypeId(),
					newAcquireTxn.getTripStartDt());
	
			if (gstPercent != null) {
				gstPercent.setScale(NonConfigurableConstants.NO_OF_DECIMAL,
						BigDecimal.ROUND_HALF_UP);
				newAcquireTxn.setGstPercent(gstPercent);
				logger.info("GST percent: " + gstPercent);
	
				BigDecimal gstValue = (newAcquireTxn.getAdminFeeValue()
						.subtract(newAcquireTxn.getProdDisValue()))
						.multiply(gstPercent.divide(HUNDRED, 4,
								BigDecimal.ROUND_HALF_UP));
				gstValue = gstValue.setScale(
						NonConfigurableConstants.NO_OF_DECIMAL,
						BigDecimal.ROUND_HALF_UP);
				newAcquireTxn.setGstValue(gstValue);
				logger.info("GST Fee = " + gstValue.toString());
			} else {
				// Calculate gst percent
				// BigDecimal gstPercent =
				// this.daoHelper.getTxnDao().getGstByTripDate(tripStartTimeStamp);
				gstPercent = BigDecimal.ZERO;
				gstPercent.setScale(NonConfigurableConstants.NO_OF_DECIMAL,
						BigDecimal.ROUND_HALF_UP);
				newAcquireTxn.setGstPercent(gstPercent);
				logger.info("GST percent: " + gstPercent);
				// Zero GST as admin fee is zero
				newAcquireTxn.setGstValue(ZERO);
			}
		}

		newAcquireTxn.setPromoDisValue(ZERO);
		newAcquireTxn.setPromoDisPercent(ZERO);

		//Copy offline flag
		newAcquireTxn.setOfflineFlag(oldAcquireTxn.getOfflineFlag());
		
		PmtbProductType pmtbProductType = newAcquireTxn.getPmtbProductType();
		if(pmtbProductType!=null && NonConfigurableConstants.getBoolean(pmtbProductType.getPrepaid())){
			newAcquireTxn.setTxnStatus(NonConfigurableConstants.TRANSACTION_STATUS_BILLED);
		} else {
			newAcquireTxn.setTxnStatus(NonConfigurableConstants.TRANSACTION_STATUS_ACTIVE);
		}
		
		if(newAcquireTxn.getMstbMasterTableByTripType() != null)
		{
			if(newAcquireTxn.getMstbMasterTableByTripType().getInterfaceMappingValue().length() >= 4)
			{
				if(newAcquireTxn.getMstbMasterTableByTripType().getInterfaceMappingValue().substring(0, 4).equalsIgnoreCase("FLAT")
						&& newAcquireTxn.getMstbMasterTableByTripType().getMasterStatus().trim().equalsIgnoreCase("A"))	
				{
					logger.info("Trip Type is Flat Fare, setting GST & Admin to 0");
					newAcquireTxn.setGstValue(ZERO);
//					newAcquireTxn.setGstPercent(ZERO);
					newAcquireTxn.setAdminFeeValue(ZERO);
//					newAcquireTxn.setAdminFeePercent(ZERO);
					newAcquireTxn.setProdDisValue(ZERO);
				}
			}
		}
		
		return true;
	}

	public boolean updateTxns(List<Integer> requestIds, String remarks,
			String user, String status) throws ConcurrencyFailureException, Exception {
		try {
			Iterator<Integer> iter = requestIds.iterator();
			while (iter.hasNext()) {
				Integer temp = iter.next();
				// Retrieve the txn one by one
				TmtbTxnReviewReq tmtbTxnReviewReq = this.daoHelper.getTxnDao()
						.getTxnReq(temp.toString());
				//
				populateOldAcquireTxnWithLastUpdatedDetails(tmtbTxnReviewReq, tmtbTxnReviewReq.getTmtbAcquireTxn());
				
				TmtbAcquireTxn newAcquireTxn = new TmtbAcquireTxn();
				populateNewAcquireTxn(newAcquireTxn,
						tmtbTxnReviewReq.getTmtbAcquireTxn(), tmtbTxnReviewReq);
				
				//get transaction latest request status
				Set<TmtbTxnReviewReqFlow> tmtbTxnReviewReqFlows = tmtbTxnReviewReq.getTmtbTxnReviewReqFlows();
				TreeSet<TmtbTxnReviewReqFlow> sortedTmtbTxnReviewReqFlows = new TreeSet<TmtbTxnReviewReqFlow>(
						new Comparator<TmtbTxnReviewReqFlow>(){
							public int compare(TmtbTxnReviewReqFlow o1, TmtbTxnReviewReqFlow o2) {
								return o1.getTxnReviewReqFlowNo().compareTo(o2.getTxnReviewReqFlowNo());
							}
						}
				);
				sortedTmtbTxnReviewReqFlows.addAll(tmtbTxnReviewReqFlows);
				String lastReqStatus = sortedTmtbTxnReviewReqFlows.last().getToStatus();
				
				//check if it is still haven't been approved or rejected
				if(!NonConfigurableConstants.TRANSACTION_REQUEST_STATUS_APPROVED.equals(lastReqStatus) &&
						 !NonConfigurableConstants.TRANSACTION_REQUEST_STATUS_REJECTED.equals(lastReqStatus))
				{
					if (NonConfigurableConstants.TRANSACTION_REQUEST_STATUS_APPROVED
							.equals(status)) {
						updateTxn(newAcquireTxn,
								tmtbTxnReviewReq.getTmtbAcquireTxn(),
								tmtbTxnReviewReq, remarks, user);
					} else {
						updateRejTxn(tmtbTxnReviewReq.getTmtbAcquireTxn(),
								tmtbTxnReviewReq, remarks, user);
					}
				}else {
					//treat trip that has been approved or rejected is caused by concurrency problem
					throw new ConcurrencyFailureException("Concurrency Error");
				}
			}
		}
		catch (ConcurrencyFailureException e) {
			throw e;
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new Exception("Update Multiple Txns");
		}

		return true;
	}


	private void populateOldAcquireTxnWithLastUpdatedDetails(TmtbTxnReviewReq tmtbTxnReviewReq, TmtbAcquireTxn oldAcquireTxn){
		
		Set<TmtbTxnReviewReqFlow> tmtbTxnReviewReqFlows = tmtbTxnReviewReq.getTmtbTxnReviewReqFlows();
		
		List<TmtbTxnReviewReqFlow> sortedTmtbTxnReviewReqFlows = TmtbTxnReviewReqFlowNoOrder.reverse().sortedCopy(tmtbTxnReviewReqFlows);
		//get the last req flows which is the submitter details
		TmtbTxnReviewReqFlow lastTmtbTxnReviewReqFlow = sortedTmtbTxnReviewReqFlows.get(0);
		
		oldAcquireTxn.setUpdatedBy(lastTmtbTxnReviewReqFlow.getSatbUser().getLoginId());
		oldAcquireTxn.setUpdatedDt(lastTmtbTxnReviewReqFlow.getFlowDt());
	
	}
	
	public boolean updateTxn(TmtbAcquireTxn oldAcquireTxn,
			TmtbTxnReviewReq tmtbTxnReviewReq, String remarks, String user,
			String status) throws ConcurrencyFailureException, Exception {
		try {
			TmtbAcquireTxn newAcquireTxn = new TmtbAcquireTxn();
			tmtbTxnReviewReq = this.daoHelper.getTxnDao().getTxnReq(
					tmtbTxnReviewReq.getTxnReviewReqNo().toString());

			populateOldAcquireTxnWithLastUpdatedDetails(tmtbTxnReviewReq, tmtbTxnReviewReq.getTmtbAcquireTxn());
			
			populateNewAcquireTxn(newAcquireTxn,
					tmtbTxnReviewReq.getTmtbAcquireTxn(), tmtbTxnReviewReq);
			
			//get transaction latest request status
			Set<TmtbTxnReviewReqFlow> tmtbTxnReviewReqFlows = tmtbTxnReviewReq.getTmtbTxnReviewReqFlows();
			TreeSet<TmtbTxnReviewReqFlow> sortedTmtbTxnReviewReqFlows = new TreeSet<TmtbTxnReviewReqFlow>(
					new Comparator<TmtbTxnReviewReqFlow>(){
						public int compare(TmtbTxnReviewReqFlow o1, TmtbTxnReviewReqFlow o2) {
							return o1.getTxnReviewReqFlowNo().compareTo(o2.getTxnReviewReqFlowNo());
						}
					}
			);
			sortedTmtbTxnReviewReqFlows.addAll(tmtbTxnReviewReqFlows);
			String lastReqStatus = sortedTmtbTxnReviewReqFlows.last().getToStatus();
			
			//check if it is still haven't been approved or rejected
			if(!NonConfigurableConstants.TRANSACTION_REQUEST_STATUS_APPROVED.equals(lastReqStatus) &&
					 !NonConfigurableConstants.TRANSACTION_REQUEST_STATUS_REJECTED.equals(lastReqStatus))
			{
				if (NonConfigurableConstants.TRANSACTION_REQUEST_STATUS_APPROVED
						.equals(status)) {
					updateTxn(newAcquireTxn, tmtbTxnReviewReq.getTmtbAcquireTxn(),
							tmtbTxnReviewReq, remarks, user);
				} else {
					updateRejTxn(tmtbTxnReviewReq.getTmtbAcquireTxn(),
							tmtbTxnReviewReq, remarks, user);
				}
			}
			else {
				//treat trip that has been approved or rejected is caused by concurrency problem
				throw new ConcurrencyFailureException("Concurrency Error");
			}
		}
		catch (ConcurrencyFailureException e) {
			throw e;
		}
		catch (Exception e) {
			LoggerUtil.printStackTrace(logger, e);
			throw new Exception("Update Txns Exception");
		}

		return true;
	}

	private boolean updateAccountCreditBalance(PmtbProduct product,
			AmtbAccount account, BigDecimal amount, String user,
			boolean isPremier) throws Exception {
		try {
			if (product != null) {
				PmtbProductType productType = product.getPmtbProductType();
				if (productType.getFixedValue()
						.equals(NonConfigurableConstants.BOOLEAN_YES)) {
					// if fare > product fixed value, set to product fixed value
					BigDecimal fixedValue = product.getFixedValue();
					if (amount.compareTo(ZERO) >= 0) {
						amount = fixedValue;
					} else {
						amount = fixedValue.negate();
					}
				}

				if(NonConfigurableConstants.getBoolean(productType.getPrepaid())){
					logger.debug("Ignore to update credit balance of account as product type of card " + product.getCardNo() +" is prepaid.");
					return true;
				}
			}

			// Should set the amount to positive before sending to AS
			BigDecimal tmpBillableAmt = BigDecimal.ZERO;
			if (amount.signum() < 0) {
				tmpBillableAmt = amount.negate();
			} else
				tmpBillableAmt = amount;

			do {
				// Amount is required to be negative here as we will use it to
				// substract from credit balance. If it is positive, we will add
				// to
				// credit balance
				account.setCreditBalance(account
						.getCreditBalance()
						.add(amount)
						.setScale(NonConfigurableConstants.NO_OF_DECIMAL,
								BigDecimal.ROUND_HALF_UP));
				account = (AmtbAccount) this.daoHelper.getGenericDao().merge(
						account);

				if (isPremier)
					API.updateAccountBillingInformation(API
							.formulateAccountId(account), account
							.getCreditBalance().toString(), "0", API
							.formulateParentAccountId(account), user);
				else
					API.updateAccountBillingInformation(API
							.formulateAccountId(account), account
							.getCreditBalance().toString(), tmpBillableAmt
							.toString(), API.formulateParentAccountId(account),
							user);
				// API.updateAccount(API.formulateAccountId(account),
				// account.getCreditBalance().toString(),
				// API.formulateParentAccountId(account), user,
				// API.ASYNCHRONOUS);
				account = account.getAmtbAccount();
			} while (account != null);

		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("Update Account Exception");
		}

		return true;
	}

	private boolean updateAccountCreditBalanceForUI(PmtbProduct product,
			AmtbAccount account, BigDecimal amount, String user)
			throws Exception {
		try {
			if (product != null) {
				
				PmtbProductType productType = product.getPmtbProductType();
				if (productType.getFixedValue()
						.equals(NonConfigurableConstants.BOOLEAN_YES)) {
					// if fare > product fixed value, set to product fixed value
					BigDecimal fixedValue = product.getFixedValue();
					if (amount.compareTo(ZERO) >= 0) {
						amount = fixedValue;
					} else {
						amount = fixedValue.negate();
					}
				}
				
				if(NonConfigurableConstants.getBoolean(productType.getPrepaid())){
					logger.debug("Ignore to update credit balance of account as product type of card " + product.getCardNo() +" is prepaid.");
					return true;
				}
			}

			do {
				// Amount is required to be negative here as we will use it to
				// substract from credit balance. If it is positive, we will add
				// to
				// credit balance
				account.setCreditBalance(account
						.getCreditBalance()
						.add(amount)
						.setScale(NonConfigurableConstants.NO_OF_DECIMAL,
								BigDecimal.ROUND_HALF_UP));
				account = (AmtbAccount) this.daoHelper.getGenericDao().merge(
						account);

				// For UI, should be 0
				API.updateAccountBillingInformation(API
						.formulateAccountId(account), account
						.getCreditBalance().toString(), "0", API
						.formulateParentAccountId(account), user);
				// API.updateAccount(API.formulateAccountId(account),
				// account.getCreditBalance().toString(),
				// API.formulateParentAccountId(account), user,
				// API.ASYNCHRONOUS);
				account = account.getAmtbAccount();
			} while (account != null);

		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("Update Account Exception");
		}

		return true;
	}

	private boolean updateProductUsed(PmtbProduct product,
			Timestamp tripStartDate, String user, boolean isAS)
			throws Exception {
		try {
			// Get the latest product status based on trip start date
			if (product != null) {
				MstbMasterTable reasonMasterTable = ConfigurableConstants
						.getMasterTable(
								ConfigurableConstants.PRODUCT_USED_REASON,
								ConfigurableConstants.PRODUCT_USED_CODE);
				PmtbProductStatus pmtbProductStatus = this.daoHelper
						.getProductDao().getLatestProductStatus(
								product.getCardNo(),
								DateUtil.getCurrentTimestamp());
				PmtbProductStatus newPmtbProductStatus = new PmtbProductStatus();
				if (pmtbProductStatus != null) {
					product.setCurrentStatus(NonConfigurableConstants.PRODUCT_STATUS_USED);
					newPmtbProductStatus.setMstbMasterTable(reasonMasterTable);
					newPmtbProductStatus.setPmtbProduct(product);
					newPmtbProductStatus.setStatusDt(tripStartDate);
					newPmtbProductStatus.setStatusFrom(pmtbProductStatus
							.getStatusTo());
					newPmtbProductStatus
							.setStatusTo(NonConfigurableConstants.PRODUCT_STATUS_USED);
					newPmtbProductStatus.setStatusRemarks(reasonMasterTable
							.getMasterValue());
					newPmtbProductStatus.setCreatedBy(user);
					newPmtbProductStatus.setCreatedDt(DateUtil
							.getCurrentTimestamp());
					this.daoHelper.getProductDao().save(newPmtbProductStatus);
					this.daoHelper.getProductDao().save(product);
					// Delete the future status
					List<PmtbProductStatus> pmtbProductStatusList = this.daoHelper
							.getProductDao().getFutureProductStatuses(
									product.getCardNo(),
									DateUtil.getCurrentTimestamp());
					if (pmtbProductStatusList != null) {
						Iterator<PmtbProductStatus> iter = pmtbProductStatusList
								.iterator();
						while (iter.hasNext()) {
							// Delete the entity
							this.daoHelper.getProductDao().delete(iter.next());
						}
					}
					if (isAS)
						API.updateProductUsage(
								product.getCardNo(),
								NonConfigurableConstants.AS_PRODUCT_STATUS_USED,
								user, API.ASYNCHRONOUS);
				} else
					throw new Exception("Product Status is null");
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("Update Product Status Exception");
		}

		return true;
	}

	private boolean updateProductNotUsed(PmtbProduct product,
			Timestamp tripStartDate, String user) throws Exception {
		try {
			// Get the latest product status based on trip start date
			if (product != null) {
				MstbMasterTable masterTable = null;
				PmtbProductStatus pmtbProductStatus = this.daoHelper
						.getProductDao().getLatestProductStatus(
								product.getCardNo(), tripStartDate);
				PmtbProductStatus newPmtbProductStatus = new PmtbProductStatus();

				// Retrieve current acct status
				AmtbAcctStatus currAmtbAcctStatus = this.daoHelper
						.getProductDao().getCurrentIssuedAccountStatus(
								product.getCardNo(),
								DateUtil.getCurrentTimestamp());
				if (currAmtbAcctStatus != null) {
					String prevStatus = null;
					prevStatus = currAmtbAcctStatus.getAcctStatus();
					// Set the current status
					if (NonConfigurableConstants.ACCOUNT_STATUS_ACTIVE
							.equalsIgnoreCase(prevStatus)) {
						masterTable = ConfigurableConstants.getMasterTable(
								ConfigurableConstants.ISSUE_REASON_TYPE,
								ConfigurableConstants.ISSUE_REASON_CODE);
					} else {
						masterTable = currAmtbAcctStatus.getMstbMasterTable();

					}

					newPmtbProductStatus.setMstbMasterTable(masterTable);
					newPmtbProductStatus.setStatusRemarks(masterTable
							.getMasterValue());
					newPmtbProductStatus.setPmtbProduct(product);
					newPmtbProductStatus.setStatusDt(DateUtil
							.getCurrentTimestamp());
					newPmtbProductStatus
							.setStatusFrom(NonConfigurableConstants.PRODUCT_STATUS_USED);
					// Next status is the current account status
					newPmtbProductStatus.setStatusTo(prevStatus);
					newPmtbProductStatus.setCreatedBy(user);
					newPmtbProductStatus.setCreatedDt(DateUtil
							.getCurrentTimestamp());
					this.daoHelper.getProductDao().save(newPmtbProductStatus);
					// Check issued account for current status (in the event it
					// is suspend or terminated or close
					product.setCurrentStatus(prevStatus);
					this.daoHelper.getProductDao().save(product);
					API.updateProductUsage(
							product.getCardNo(),
							NonConfigurableConstants.AS_PRODUCT_STATUS_NOT_USED,
							user, API.ASYNCHRONOUS);

					if (NonConfigurableConstants.ACCOUNT_STATUS_ACTIVE
							.equalsIgnoreCase(currAmtbAcctStatus
									.getAcctStatus())) {
						// Only check put in the rest of the future status if
						// the account is active
						// Retrieve all the statuses of issued account for the
						// product
						// rebuild history for future suspension and termination
						// based on issued account
						List<AmtbAcctStatus> amtbAcctStatusList = this.daoHelper
								.getProductDao()
								.getFutureIssuedAccountStatuses(
										product.getCardNo(),
										DateUtil.getCurrentTimestamp());
						if (amtbAcctStatusList != null) {
							Iterator<AmtbAcctStatus> iter = amtbAcctStatusList
									.iterator();
							while (iter.hasNext()) {
								AmtbAcctStatus amtbAcctStatus = iter.next();
								masterTable = amtbAcctStatus
										.getMstbMasterTable();

								if (NonConfigurableConstants.ACCOUNT_STATUS_PARENT_SUSPENDED
										.equalsIgnoreCase(amtbAcctStatus
												.getAcctStatus())
										|| NonConfigurableConstants.ACCOUNT_STATUS_SUSPENDED
												.equalsIgnoreCase(amtbAcctStatus
														.getAcctStatus())) {
									// Parent suspend
									// Create a new product status
									newPmtbProductStatus = new PmtbProductStatus();
									newPmtbProductStatus
											.setMstbMasterTable(masterTable);
									newPmtbProductStatus
											.setPmtbProduct(product);
									newPmtbProductStatus
											.setStatusDt(amtbAcctStatus
													.getEffectiveDt());
									newPmtbProductStatus
											.setStatusFrom(prevStatus);
									newPmtbProductStatus
											.setStatusTo(amtbAcctStatus
													.getAcctStatus());
									newPmtbProductStatus
											.setStatusRemarks(masterTable
													.getMasterValue());
									newPmtbProductStatus.setCreatedBy(user);
									newPmtbProductStatus.setCreatedDt(DateUtil
											.getCurrentTimestamp());
									this.daoHelper.getProductDao().save(
											newPmtbProductStatus);
									prevStatus = amtbAcctStatus.getAcctStatus();
								} else if (NonConfigurableConstants.ACCOUNT_STATUS_TERMINATED
										.equalsIgnoreCase(amtbAcctStatus
												.getAcctStatus())
										|| NonConfigurableConstants.ACCOUNT_STATUS_CLOSED
												.equalsIgnoreCase(amtbAcctStatus
														.getAcctStatus())) {
									// Closed or terminated
									// Parent suspend
									newPmtbProductStatus
											.setMstbMasterTable(masterTable);
									newPmtbProductStatus
											.setPmtbProduct(product);
									newPmtbProductStatus
											.setStatusDt(amtbAcctStatus
													.getEffectiveDt());
									newPmtbProductStatus
											.setStatusFrom(prevStatus);
									newPmtbProductStatus
											.setStatusTo(NonConfigurableConstants.PRODUCT_STATUS_TERMINATED);
									newPmtbProductStatus
											.setStatusRemarks(masterTable
													.getMasterValue());
									newPmtbProductStatus.setCreatedBy(user);
									newPmtbProductStatus.setCreatedDt(DateUtil
											.getCurrentTimestamp());
									this.daoHelper.getProductDao().save(
											newPmtbProductStatus);
									prevStatus = amtbAcctStatus.getAcctStatus();
								}
							}
						}
					}
				} else
					throw new Exception("Product Status is null");
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("Update Product Status Exception");
		}

		return true;
	}
	
	
	public BigDecimal handleTripForPrepaidCard(PmtbProduct product, TmtbAcquireTxn txn, String action){
	
		logger.debug("handle " + action + " action for trip " + txn.getJobNo() + " of prepaid card: " + product.getProductNo());
		
		//determine the chargeable amount of trip
		BigDecimal billableAmt = txn.getBillableAmt();
		
		BigDecimal tripChargeableAmount = billableAmt;
		
		//check whether the trip is before forfeited date
		Date tripEndDate = txn.getTripEndDt();
		Object[] forfeitureObj = this.daoHelper.getPrepaidDao().getLastBalanceForfeitureAsAt(product.getProductNo(), tripEndDate);
		PmtbBalanceForfeiture lastBalanceForfeiture = forfeitureObj!=null && forfeitureObj[0]!=null ? (PmtbBalanceForfeiture) forfeitureObj[0] : null;
		
		boolean tripBeforeForfeited = false;
		if(lastBalanceForfeiture!=null){
			
			//the last balance FOFEITURE we retrieve from getLastBalanceForfeitureAsAt is detached. It would cause unique problem when handleTripForPrepaidCard being implement
			//twice in the same business service method.
			//the easier implementation to avoid this is to attach it back into session by id.
			lastBalanceForfeiture = (PmtbBalanceForfeiture) super.get(PmtbBalanceForfeiture.class, lastBalanceForfeiture.getBalanceForfeitureNo());
			
			Date lastBalanceForfeitedDate = lastBalanceForfeiture.getForfeitedDate();
			if(lastBalanceForfeitedDate!=null && tripEndDate!=null){
				if(tripEndDate.before(lastBalanceForfeitedDate)){
					tripBeforeForfeited = true;
				}
			}
		}
		
		//determine the apply amount based on the action
		//apply amount will consist of amount to be deducted from card value or cash plus
		ApplyAmtInfo applyAmount = null;
		if(PREPAID_HANDLE_TRIP_ACTION_VOID.equals(action)){
			
			//the return apply amount is in negative form, deduct credit balance with it will instead add the credit balance
			applyAmount = createPrepaidVoidTrips(txn, tripChargeableAmount);
			
		} else if(PREPAID_HANDLE_TRIP_ACTION_NEW.equals(action) || PREPAID_HANDLE_TRIP_ACTION_EDIT.equals(action)){
			//the return apply amount will be positive
			if(tripBeforeForfeited){
				applyAmount = PrepaidUtil.calculateCardValuePrefApplyAmount(lastBalanceForfeiture.getForfeitCardValue(), lastBalanceForfeiture.getForfeitCashplus(), tripChargeableAmount);
			} else {
				applyAmount = PrepaidUtil.calculateCardValuePrefApplyAmount(product.getCardValue(), product.getCashplus(), tripChargeableAmount);
			}
			
			createPrepaidNewOrEditTrips(txn, applyAmount, action);

		} else {
			throw new WrongValueException("Not supported action");
		}
		
		logger.debug(applyAmount);

		//based on the apply amount, if the trip is before forfeited, create forfeit adjust TXN, else deduct the amount from product
		if(tripBeforeForfeited){
			logger.info("create prepaid forfeit adjust txn for product: " + product.getProductNo());
			createForfeitAdjustPrepaidCardTxn(product, lastBalanceForfeiture, applyAmount);
		
		} else {
			logger.info("deduct credit balance from product: " + product.getProductNo());
			PrepaidUtil.deductProductAmtWithApplyAmtInfo(product, applyAmount, false);
		}

		return billableAmt;
	}
	
	
	
	private void createForfeitAdjustPrepaidCardTxn(PmtbProduct txnProduct, PmtbBalanceForfeiture lastBalanceForfeiture, ApplyAmtInfo applyAmount){
		
		BigDecimal adjustCardValue = applyAmount.getApplyCardValue();
		BigDecimal adjustCashplus = applyAmount.getApplyCashplus();

		//the return amount is positive
		BigDecimal forfeitedCardValue = lastBalanceForfeiture.getForfeitCardValue();
		BigDecimal forfeitedCashplus = lastBalanceForfeiture.getForfeitCashplus();
		
		BigDecimal actualForfeitCardValue = forfeitedCardValue.subtract(adjustCardValue);
		BigDecimal actualForfeitCashplus = forfeitedCashplus.subtract(adjustCashplus);
		
		//recalculate the last balance FORFEITURE card value and cash plus
		lastBalanceForfeiture.setForfeitCardValue(actualForfeitCardValue);
		lastBalanceForfeiture.setForfeitCashplus(actualForfeitCashplus);
		
		super.update(lastBalanceForfeiture);
		
		BigDecimal lastCardValueForfeitureGstRate =  lastBalanceForfeiture.getForfeitCardValueGstRate();
		BigDecimal lastCashplusForfeitureGstRate = lastBalanceForfeiture.getForfeitCashplusGstRate();
		
		// get the adjust forfeit card value 
		GstInfo cardValueGstInfo = GstUtil.backwardCalculateGstInfo(adjustCardValue, lastCardValueForfeitureGstRate);
		BigDecimal  adjustCardValueWithoutGst = cardValueGstInfo.getAmountWithoutGst();
		BigDecimal  adjustCardValueGst = cardValueGstInfo.getGst();
		
		// get the adjust forfeit cash plus 
		GstInfo cashplusGstGstInfo = GstUtil.backwardCalculateGstInfo(adjustCashplus, lastCashplusForfeitureGstRate);
		BigDecimal  adjustCashplusWithoutGst = cashplusGstGstInfo.getAmountWithoutGst();
		BigDecimal adjustCashplusGst = cashplusGstGstInfo.getGst();

		List<PmtbPrepaidCardTxn> forfeitTxns = Lists.newArrayList();
		
		forfeitTxns.add(PmtbPrepaidCardTxn.buildTxn(null, txnProduct, NonConfigurableConstants.PREPAID_TXN_TYPE_FORFEIT_ADJUST_VALUE, 
				adjustCardValueWithoutGst, adjustCardValueGst , adjustCardValue, null, true));
		
		if(adjustCashplus!=null && adjustCashplus.doubleValue()!=0){
			
			forfeitTxns.add(PmtbPrepaidCardTxn.buildTxn(null, txnProduct, NonConfigurableConstants.PREPAID_TXN_TYPE_FORFEIT_ADJUST_CASHPLUS, 
					adjustCashplusWithoutGst, adjustCashplusGst , null, adjustCashplus, true));
		}
		
		super.saveAll(forfeitTxns);
	
	
	}
	
	
	private void createPrepaidNewOrEditTrips(TmtbAcquireTxn txn, ApplyAmtInfo applyAmount, String action){
		

		logger.debug("Create prepaid new trip from txn job " + txn.getJobNo() + " of product " + txn.getPmtbProduct().getProductNo());
		BigDecimal applyCardValue = applyAmount.getApplyCardValue();
		BigDecimal applyCashPlus =  applyAmount.getApplyCashplus();
		BigDecimal applyCreditBalance = applyAmount.getApplyCreditBalance();
		
		boolean glable = true;
	
		String txnType = "";
		if(action.equals(PREPAID_HANDLE_TRIP_ACTION_EDIT)){
			txnType = NonConfigurableConstants.PREPAID_TXN_TYPE_EDIT_TRIP;
		} else {
			txnType = NonConfigurableConstants.PREPAID_TXN_TYPE_TRIP;
		}
		
		super.save(PmtbPrepaidCardTxn.buildTripTxn(txn, txnType, 
				applyCreditBalance.negate(), 
				applyCardValue.negate(), 
				applyCashPlus.negate(),
					glable));
	}
	
	private ApplyAmtInfo createPrepaidVoidTrips(TmtbAcquireTxn txn, BigDecimal tripChargeableAmount){
		
		Integer acquireTxnNo = txn.getAcquireTxnNo();
		logger.debug("Create prepaid void trip from txn job " + txn.getJobNo() + " of product " + txn.getPmtbProduct().getProductNo());
		BigDecimal applyCardValue = BigDecimal.ZERO;
		BigDecimal applyCashPlus = BigDecimal.ZERO;
		BigDecimal applyCreditBalance = tripChargeableAmount.negate();
		
		boolean glable = true;
		
		ApplyAmtInfo applyAmount = new ApplyAmtInfo();
		
		List<PmtbPrepaidCardTxn> activeTxns = this.daoHelper.getPrepaidDao().getPrepaidCardTxnsByAcquireTxnNo(acquireTxnNo);
		if(activeTxns!=null){
	
			for(PmtbPrepaidCardTxn pTxn: activeTxns){
				
				//the return amount of PREPAID trip TXN is in negative
				BigDecimal amount = pTxn.getAmount();
				BigDecimal cardValue = pTxn.getApplyCardValue();
				BigDecimal cashPlus = pTxn.getApplyCashplus();
				
				super.save(PmtbPrepaidCardTxn.buildTripTxn(txn, NonConfigurableConstants.PREPAID_TXN_TYPE_VOID_TRIP, amount.negate(), 
						cardValue.negate(), cashPlus.negate(), glable));
				
				applyCardValue = applyCardValue.add(cardValue);
				
				if(cashPlus!=null){
					applyCashPlus = applyCashPlus.add(cashPlus);
				}
				
			}
			
		} else {
			logger.debug("Something wrong! No any prepaid txn found for acquire job no: " + txn.getJobNo());
		}
	
		//it is correct that the return apply amount will be in negative form as it is a void request
		applyAmount.setApplyCardValue(applyCardValue);
		applyAmount.setApplyCashplus(applyCashPlus);
		applyAmount.setApplyCreditBalance(applyCreditBalance);
		
		
		return applyAmount;
		
	}


	private boolean updateProductCreditLimit(PmtbProduct product, BigDecimal tripUsageAmount, String user) throws Exception {

		try {
			// Update Product Credit Limit
			if (product != null) {
	
				API.updateProductBillingInformation(
						product.getCardNo(),
						product.getCreditBalance()
								.setScale(
										NonConfigurableConstants.NO_OF_DECIMAL,
										BigDecimal.ROUND_HALF_UP).toString(),
							tripUsageAmount.setScale(
								NonConfigurableConstants.NO_OF_DECIMAL,
								BigDecimal.ROUND_HALF_UP).toString(),
						API.formulateAccountId(product.getAmtbAccount()), user);
		
			}
		} catch (Exception e) {
			LoggerUtil.printStackTrace(logger, e);
			throw new Exception("Update Product Credit Limit Exception");
		}

		return true;
	}

	private boolean updateProductCreditLimitForUI(PmtbProduct product, String user) throws Exception {

		try {
			// Update Product Credit Limit
			if (product != null) {

				//different from update product credit limit from interface, the total TXN amount billed is always 0
				API.updateProductBillingInformation(
						product.getCardNo(),
						product.getCreditBalance()
								.setScale(
										NonConfigurableConstants.NO_OF_DECIMAL,
										BigDecimal.ROUND_HALF_UP).toString(),
						"0",
						API.formulateAccountId(product.getAmtbAccount()), user);

			}
		} catch (Exception e) {
			LoggerUtil.printStackTrace(logger, e);
			throw new Exception("Update Product Credit Limit Exception");
		}

		return true;
	}

	private boolean updateFMSReq(TmtbAcquireTxn tmtbAcquireTxn)
			throws Exception {
		try {
			List<IttbFmsDrvrRfndColReq> fmsDrvrRfndColReqList = this.daoHelper
					.getTxnDao().getPendingFMSReq(
							tmtbAcquireTxn.getJobNo(),
							tmtbAcquireTxn.getNric(),
							tmtbAcquireTxn.getTaxiNo(),
							tmtbAcquireTxn.getPmtbProductType()
									.getInterfaceMappingValue());
			Boolean isCardless = new Boolean(false);
			List<PmtbProductType> cardlessProducts = this.businessHelper.getProductTypeBusiness().getCardlessProductType();
			for(PmtbProductType cardlessProduct : cardlessProducts)
			{
				if(cardlessProduct.getProductTypeId().equals(tmtbAcquireTxn.getPmtbProductType().getProductTypeId()))
				{
					isCardless = true;
					break;
				}
			}
			
			if (fmsDrvrRfndColReqList == null) {
				// no such request. create new request
				IttbFmsDrvrRfndColReq fmsDrvrRfndColReq = new IttbFmsDrvrRfndColReq();
					
				// Get corporate account

				// Only set account name if premier service
//				if (NonConfigurableConstants.PREMIER_SERVICE
//						.equals(tmtbAcquireTxn.getPmtbProductType()
//								.getProductTypeId())) {
				if(isCardless)
				{
					AmtbAccount parentAccount = tmtbAcquireTxn.getAmtbAccount();
					while (parentAccount != null) {
						if (parentAccount
								.getAccountCategory()
								.equals(NonConfigurableConstants.ACCOUNT_CATEGORY_CORPORATE)
								|| parentAccount
										.getAccountCategory()
										.equals(NonConfigurableConstants.ACCOUNT_CATEGORY_APPLICANT)) {
							break;
						} else
							parentAccount = parentAccount.getAmtbAccount();
					}
					fmsDrvrRfndColReq.setAccountName(parentAccount
							.getAccountName());
				} else
					fmsDrvrRfndColReq.setAccountName("");

				if (tmtbAcquireTxn.getDestination() != null
						&& !"".equals(tmtbAcquireTxn.getDestination())) {
					fmsDrvrRfndColReq.setDestination(tmtbAcquireTxn
							.getDestination());
				} else {
					fmsDrvrRfndColReq
							.setDestination(NonConfigurableConstants.NA_FLAG);
				}
				fmsDrvrRfndColReq.setEntityCode(tmtbAcquireTxn
						.getMstbMasterTableByServiceProvider()
						.getInterfaceMappingValue());
				fmsDrvrRfndColReq.setJobNo(tmtbAcquireTxn.getJobNo());
				if (tmtbAcquireTxn.getLevy() != null)
					fmsDrvrRfndColReq.setLevy(tmtbAcquireTxn.getLevy()
							.setScale(NonConfigurableConstants.NO_OF_DECIMAL,
									BigDecimal.ROUND_HALF_UP));
				else
					fmsDrvrRfndColReq.setLevy(ZERO);
				fmsDrvrRfndColReq.setNric(tmtbAcquireTxn.getNric());
				if (NonConfigurableConstants.FMS_COLLECT.equals(tmtbAcquireTxn
						.getUpdateFms())) {
					fmsDrvrRfndColReq.setRefundAmt(tmtbAcquireTxn
							.getFmsAmt()
							.setScale(NonConfigurableConstants.NO_OF_DECIMAL,
									BigDecimal.ROUND_HALF_UP).negate());
					if (tmtbAcquireTxn.getIncentiveAmt() != null) {
						fmsDrvrRfndColReq.setIncentiveAmt(tmtbAcquireTxn
								.getIncentiveAmt()
								.setScale(NonConfigurableConstants.NO_OF_DECIMAL,
										BigDecimal.ROUND_HALF_UP).negate());
					}
					else {
						fmsDrvrRfndColReq.setIncentiveAmt(ZERO);
					}
					if (tmtbAcquireTxn.getPromoAmt() != null) {
						fmsDrvrRfndColReq.setPromoAmt(tmtbAcquireTxn.getPromoAmt().setScale(NonConfigurableConstants.NO_OF_DECIMAL, BigDecimal.ROUND_HALF_UP).negate());
					}
					else {
						fmsDrvrRfndColReq.setPromoAmt(ZERO);
					}
					if (tmtbAcquireTxn.getCabRewardsAmt() != null) {
						fmsDrvrRfndColReq.setCabRewardsAmt(tmtbAcquireTxn.getCabRewardsAmt().setScale(NonConfigurableConstants.NO_OF_DECIMAL, BigDecimal.ROUND_HALF_UP).negate());
					}
					else {
						fmsDrvrRfndColReq.setCabRewardsAmt(ZERO);
					}
				} else {
					fmsDrvrRfndColReq.setRefundAmt(tmtbAcquireTxn.getFmsAmt()
							.setScale(NonConfigurableConstants.NO_OF_DECIMAL,
									BigDecimal.ROUND_HALF_UP));
					if (tmtbAcquireTxn.getIncentiveAmt() != null) {
						fmsDrvrRfndColReq.setIncentiveAmt(tmtbAcquireTxn.getIncentiveAmt()
								.setScale(NonConfigurableConstants.NO_OF_DECIMAL,
										BigDecimal.ROUND_HALF_UP));
					} else {
						fmsDrvrRfndColReq.setIncentiveAmt(ZERO);
					}
					if (tmtbAcquireTxn.getPromoAmt() != null) {
						fmsDrvrRfndColReq.setPromoAmt(tmtbAcquireTxn.getPromoAmt()
								.setScale(NonConfigurableConstants.NO_OF_DECIMAL,
										BigDecimal.ROUND_HALF_UP));
					} else {
						fmsDrvrRfndColReq.setPromoAmt(ZERO);
					}
					if (tmtbAcquireTxn.getCabRewardsAmt() != null) {
						fmsDrvrRfndColReq.setCabRewardsAmt(tmtbAcquireTxn.getCabRewardsAmt()
								.setScale(NonConfigurableConstants.NO_OF_DECIMAL,
										BigDecimal.ROUND_HALF_UP));
					} else {
						fmsDrvrRfndColReq.setCabRewardsAmt(ZERO);
					}
				}
				fmsDrvrRfndColReq.setPickupAddress(tmtbAcquireTxn
						.getPickupAddress());
				fmsDrvrRfndColReq.setReqDate(DateUtil.getCurrentTimestamp());
				fmsDrvrRfndColReq
						.setReqStatus(NonConfigurableConstants.STATUS_PENDING);
				if (NonConfigurableConstants.PREMIER_SERVICE
						.equals(tmtbAcquireTxn.getPmtbProductType()
								.getProductTypeId())) {
					fmsDrvrRfndColReq
							.setServiceType(NonConfigurableConstants.FMS_SERVICE_PREMIER);
				} else {
					fmsDrvrRfndColReq
							.setServiceType(NonConfigurableConstants.FMS_SERVICE_NORMAL);
				}
				fmsDrvrRfndColReq.setFareAmt(tmtbAcquireTxn.getFareAmt());
				fmsDrvrRfndColReq.setPaymentMode(tmtbAcquireTxn
						.getPmtbProductType().getInterfaceMappingValue());
				fmsDrvrRfndColReq.setTaxiNo(tmtbAcquireTxn.getTaxiNo());
				fmsDrvrRfndColReq.setTripEndDt(tmtbAcquireTxn.getTripEndDt());
				fmsDrvrRfndColReq.setTripStartDt(tmtbAcquireTxn
						.getTripStartDt());
				fmsDrvrRfndColReq
						.setJobType(NonConfigurableConstants.JOB_TYPE_STREET);
				fmsDrvrRfndColReq.setPaymentMode(tmtbAcquireTxn
						.getPmtbProductType().getInterfaceMappingValue());
				// fmsDrvrRfndColReq.setUpdatedBy(NonConfigurableConstants.INTERFACE_USER);
				if (tmtbAcquireTxn.getUpdatedBy() != null
						&& !"".equals(tmtbAcquireTxn.getUpdatedBy()))
					fmsDrvrRfndColReq.setUpdatedBy(tmtbAcquireTxn
							.getUpdatedBy());
				else
					fmsDrvrRfndColReq.setUpdatedBy(tmtbAcquireTxn
							.getCreatedBy());
				fmsDrvrRfndColReq.setUpdatedDt(DateUtil.getCurrentTimestamp());

				this.daoHelper.getTxnDao().save(fmsDrvrRfndColReq);
			} else {
				// Iterate the request to add
				// then delete all the pending requests
				Iterator<IttbFmsDrvrRfndColReq> iter = fmsDrvrRfndColReqList
						.iterator();
				BigDecimal levyTotal = ZERO;
				BigDecimal fmsAmtTotal = ZERO;
				BigDecimal incentiveAmtTotal = ZERO;
				BigDecimal promoAmtTotal = ZERO;
				BigDecimal cabRewardsAmtTotal = ZERO;
				Timestamp requestDt = null;
				if (NonConfigurableConstants.FMS_COLLECT.equals(tmtbAcquireTxn
						.getUpdateFms())) {
					if (tmtbAcquireTxn.getFmsAmt() != null)
						fmsAmtTotal = tmtbAcquireTxn.getFmsAmt().negate();
					if (tmtbAcquireTxn.getIncentiveAmt() != null)
						incentiveAmtTotal = tmtbAcquireTxn.getIncentiveAmt().negate();
					if (tmtbAcquireTxn.getPromoAmt() != null)
						promoAmtTotal = tmtbAcquireTxn.getPromoAmt().negate();
					if (tmtbAcquireTxn.getCabRewardsAmt() != null)
						cabRewardsAmtTotal = tmtbAcquireTxn.getCabRewardsAmt().negate();
				} else {
					if (tmtbAcquireTxn.getFmsAmt() != null)
						fmsAmtTotal = tmtbAcquireTxn.getFmsAmt();
					if (tmtbAcquireTxn.getIncentiveAmt() != null)
						incentiveAmtTotal = tmtbAcquireTxn.getIncentiveAmt();
					if (tmtbAcquireTxn.getPromoAmt() != null)
						promoAmtTotal = tmtbAcquireTxn.getPromoAmt();
					if (tmtbAcquireTxn.getCabRewardsAmt() != null)
						cabRewardsAmtTotal = tmtbAcquireTxn.getCabRewardsAmt();
				}
				// User will key in the positive and negative sign themselves.
				if (tmtbAcquireTxn.getLevy() != null)
					levyTotal = tmtbAcquireTxn.getLevy();
				while (iter.hasNext()) {
					IttbFmsDrvrRfndColReq temp = iter.next();
					if (temp.getLevy() != null)
						levyTotal = levyTotal.add(temp.getLevy());
					if (temp.getRefundAmt() != null)
						fmsAmtTotal = fmsAmtTotal.add(temp.getRefundAmt());
					if (temp.getIncentiveAmt() != null)
						incentiveAmtTotal = incentiveAmtTotal.add(temp.getIncentiveAmt());
					if (temp.getPromoAmt() != null)
						promoAmtTotal = promoAmtTotal.add(temp.getPromoAmt());
					if (temp.getCabRewardsAmt() != null)
						cabRewardsAmtTotal = cabRewardsAmtTotal.add(temp.getCabRewardsAmt());
					if (temp.getReqDate() != null)
						requestDt = temp.getReqDate();
					// Delete the records
					this.daoHelper.getTxnDao().delete(temp);
				}

				// no such request. create new request
				IttbFmsDrvrRfndColReq fmsDrvrRfndColReq = new IttbFmsDrvrRfndColReq();
				// Only set account name if premier service
//				if (NonConfigurableConstants.PREMIER_SERVICE
//						.equals(tmtbAcquireTxn.getPmtbProductType()
//								.getProductTypeId())) {
				if(isCardless) {
					AmtbAccount parentAccount = tmtbAcquireTxn.getAmtbAccount();
					while (parentAccount != null) {
						if (parentAccount
								.getAccountCategory()
								.equals(NonConfigurableConstants.ACCOUNT_CATEGORY_CORPORATE)
								|| parentAccount
										.getAccountCategory()
										.equals(NonConfigurableConstants.ACCOUNT_CATEGORY_APPLICANT)) {
							break;
						} else
							parentAccount = parentAccount.getAmtbAccount();
					}
					fmsDrvrRfndColReq.setAccountName(parentAccount
							.getAccountName());
				} else
					fmsDrvrRfndColReq.setAccountName("");
				// fmsDrvrRfndColReq.setAccountName(tmtbAcquireTxn.getAmtbAccount().getAccountName());
				if (tmtbAcquireTxn.getDestination() != null
						&& !"".equals(tmtbAcquireTxn.getDestination())) {
					fmsDrvrRfndColReq.setDestination(tmtbAcquireTxn
							.getDestination());
				} else {
					fmsDrvrRfndColReq
							.setDestination(NonConfigurableConstants.NA_FLAG);
				}

				fmsDrvrRfndColReq.setEntityCode(tmtbAcquireTxn
						.getMstbMasterTableByServiceProvider()
						.getInterfaceMappingValue());
				fmsDrvrRfndColReq.setJobNo(tmtbAcquireTxn.getJobNo());
				fmsDrvrRfndColReq
						.setJobType(NonConfigurableConstants.JOB_TYPE_STREET);
				fmsDrvrRfndColReq.setLevy(levyTotal.setScale(
						NonConfigurableConstants.NO_OF_DECIMAL,
						BigDecimal.ROUND_HALF_UP));
				fmsDrvrRfndColReq.setNric(tmtbAcquireTxn.getNric());
				fmsDrvrRfndColReq.setFareAmt(tmtbAcquireTxn.getFareAmt());
				fmsDrvrRfndColReq.setRefundAmt(fmsAmtTotal);
				fmsDrvrRfndColReq.setIncentiveAmt(incentiveAmtTotal);
				fmsDrvrRfndColReq.setPromoAmt(promoAmtTotal);
				fmsDrvrRfndColReq.setCabRewardsAmt(cabRewardsAmtTotal);
				fmsDrvrRfndColReq.setPickupAddress(tmtbAcquireTxn
						.getPickupAddress());
				fmsDrvrRfndColReq.setReqDate(requestDt);
				fmsDrvrRfndColReq
						.setReqStatus(NonConfigurableConstants.STATUS_PENDING);
				if (NonConfigurableConstants.PREMIER_SERVICE
						.equals(tmtbAcquireTxn.getPmtbProductType()
								.getProductTypeId())) {
					fmsDrvrRfndColReq
							.setServiceType(NonConfigurableConstants.FMS_SERVICE_PREMIER);
				} else {
					fmsDrvrRfndColReq
							.setServiceType(NonConfigurableConstants.FMS_SERVICE_NORMAL);
				}
				fmsDrvrRfndColReq.setPaymentMode(tmtbAcquireTxn
						.getPmtbProductType().getInterfaceMappingValue());
				fmsDrvrRfndColReq.setTaxiNo(tmtbAcquireTxn.getTaxiNo());
				fmsDrvrRfndColReq.setTripEndDt(tmtbAcquireTxn.getTripEndDt());
				fmsDrvrRfndColReq.setTripStartDt(tmtbAcquireTxn
						.getTripStartDt());
				fmsDrvrRfndColReq.setPaymentMode(tmtbAcquireTxn
						.getPmtbProductType().getInterfaceMappingValue());
				// fmsDrvrRfndColReq.setUpdatedBy(NonConfigurableConstants.INTERFACE_USER);
				if (tmtbAcquireTxn.getUpdatedBy() != null
						&& !"".equals(tmtbAcquireTxn.getUpdatedBy()))
					fmsDrvrRfndColReq.setUpdatedBy(tmtbAcquireTxn
							.getUpdatedBy());
				else
					fmsDrvrRfndColReq.setUpdatedBy(tmtbAcquireTxn
							.getCreatedBy());
				fmsDrvrRfndColReq.setUpdatedDt(DateUtil.getCurrentTimestamp());

				this.daoHelper.getTxnDao().save(fmsDrvrRfndColReq);
			}

		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("Update FMS Exception");
		}

		return true;
	}

	public boolean isAccountClosed(String acctNo) {
		AmtbAcctStatus amtbAccountStatus = null;
		amtbAccountStatus = this.daoHelper.getAccountDao()
				.getAccountLatestStatus(acctNo);
		if (amtbAccountStatus != null) {
			if (NonConfigurableConstants.ACCOUNT_STATUS_CLOSED
					.equals(amtbAccountStatus.getAcctStatus())) {
				logger.info("Account is closed");
				return true;
			}
		}

		logger.info("Account is opened");
		return false;
	}

	public boolean isRequestCreated(String txnID) {
		if (this.daoHelper.getTxnDao().getTxnReqs(txnID) == null) {
			return false;
		} else {
			return true;
		}
	}

	public boolean hasDuplicateDraftNo(String salesDraftNo) {
		if (this.daoHelper.getTxnDao().getTxnByDraftNo(salesDraftNo) == null) {
			return false;
		} else {
			return true;
		}
	}

	public void createTxnForInterface(IttbTripsTxn ittbTripsTxn,
			TmtbAcquireTxn tmtbAcquireTxn) throws Exception {
		
		AmtbAccount amtbAccount = null;
		BigDecimal prodDisPercent = null, adminFeePercent = null;
		MstbProdDiscDetail discountDetail = null;
		PmtbProduct pmtbProduct = null;
		BigDecimal bdTotalAmt = null;
		MstbAdminFeeDetail adminFeeDetail = null;
		PmtbProductType pmtbProductType = null;

		String paymentMode = ittbTripsTxn.getPaymentMode();
		MstbMasterTable aptMaster = ConfigurableConstants
				.getMasterTableByInterfaceMapping(ConfigurableConstants.ACQUIRER_PYMT_TYPE,
						paymentMode);
		List<String> cardLessPaymentList = new ArrayList<String>();
		List<String> cardLessInterfaceMappingList = new ArrayList<String>();
		cardLessInterfaceMappingList.add("CABC");
		cardLessInterfaceMappingList.add("EVCH");
		
		try {
			List<PmtbProductType> cardlessProducts = this.businessHelper.getProductTypeBusiness().getCardlessProductType();
			for(PmtbProductType cardlessProduct : cardlessProducts) {
				cardLessInterfaceMappingList.add(cardlessProduct.getInterfaceMappingValue().trim());
				cardLessPaymentList.add(cardlessProduct.getInterfaceMappingValue().trim());
			}
			// check for payment mode
			// if payment mode = CABC, EVCH, INVO, it will considered as a
			// billable txn
//			if ("CABC".equals(paymentMode) || "EVCH".equals(paymentMode)
//					|| "INVO".equals(paymentMode)) {
			if(cardLessInterfaceMappingList.contains(paymentMode)) {
				// Retrieval of data
				String jobNo = ittbTripsTxn.getJobNo();
				Timestamp tripStartTimeStamp = ittbTripsTxn.getTripStart();
				Timestamp tripEndTimeStamp = ittbTripsTxn.getTripEnd();
				String dest = ittbTripsTxn.getDestination();
				String driverIC = ittbTripsTxn.getDriverIc();
				String vehicleNo = ittbTripsTxn.getVehicleNo();
				String acctlv1 = ittbTripsTxn.getAccountLv1();
				String acctlv2 = ittbTripsTxn.getAccountLv2();
				String acctlv3 = ittbTripsTxn.getAccountLv3();
				BigDecimal totalAmt = ittbTripsTxn.getTotalAmount();
				
				//for PREPAID usage
				BigDecimal txnAmount = ittbTripsTxn.getTxnAmount();
				BigDecimal adminAmount = ittbTripsTxn.getAdminAmount();
				BigDecimal gstAmount = ittbTripsTxn.getGstAmount();
				
				String bookRef = ittbTripsTxn.getBookingReference();
				String jobStatus = ittbTripsTxn.getJobStatus();
				String bookVehGrp = ittbTripsTxn.getBookedVehicleGroup();
				// String product = ittbTripsTxn.getProduct();
				// Should be productID instead
				String product = ittbTripsTxn.getProductId();
				String paxName = ittbTripsTxn.getPaxName();
				String surchargeDesc = ittbTripsTxn.getSurchargeDescription();
				String cardNo = ittbTripsTxn.getCardNo();
				String pickup = ittbTripsTxn.getPickup();
				String complimentary = ittbTripsTxn.getComplimentary();
				String approvalCode = ittbTripsTxn.getApprovalCode();
				String salesDraftNo = ittbTripsTxn.getSalesDraftNo();
				String jobType = ittbTripsTxn.getJobType();
				Timestamp setlDt = ittbTripsTxn.getSetlDate();
				String bankTID = ittbTripsTxn.getBankTid();
				String bankBatchCloseNo = ittbTripsTxn.getBankBatchCloseNo();
				BigDecimal levy = ittbTripsTxn.getDriverLevy();
				BigDecimal incentiveAmt = ittbTripsTxn.getIncentiveAmt();
				BigDecimal promoAmt = ittbTripsTxn.getPromoAmt();
				BigDecimal cabRewardsAmt = ittbTripsTxn.getCabRewardsAmt();
				String entity = ittbTripsTxn.getEntity();
				Timestamp bookedDateTime = ittbTripsTxn.getBookedDateTime();
				String bookedBy = ittbTripsTxn.getAgentName();
				String flightDetails = ittbTripsTxn.getFlightDetails();
				String serviceType = ittbTripsTxn.getServiceType();
				Timestamp pickupDt = ittbTripsTxn.getPickupDt();
				Long hotelChargeTo = ittbTripsTxn.getHotelChargeTo();
				String vehicleModel = ittbTripsTxn.getVehType();
				
				String tripCode = ittbTripsTxn.getTripCode();
				String tripDesc = ittbTripsTxn.getTripDesc();

				/***
				 * Validation Rules
				 */
				// Validate Job No
				if (this.daoHelper.getTxnDao().hasTxn(jobNo)) {
					throw new ProcessTripException(NonConfigurableConstants.DUPLICATE_JOB_NO);
				}

				// New transaction no is automatically retrieve by Hibernate
				// TmtbAcquireTxn tmtbAcquireTxn = new TmtbAcquireTxn();

				// Retrieve a product no based on hotel_charge_to
				IttbCpGuestProduct ittbCpGuestProduct = this.daoHelper
						.getTxnDao().getIttbCpGuestProduct(hotelChargeTo);
				tmtbAcquireTxn.setIttbCpGuestProduct(ittbCpGuestProduct);

				// Trip Start Date
				if (tripStartTimeStamp == null) {
					// If trip start date is not provided, we had to retrieve
					// from pickup Dt
					// This is only for premier service
					if (pickupDt != null) {
						tripStartTimeStamp = pickupDt;
						tripEndTimeStamp = pickupDt;
						tmtbAcquireTxn.setTripStartDt(tripStartTimeStamp);
						tmtbAcquireTxn.setTripEndDt(pickupDt);
					} else
						// throw mandatory trip start date exception
						throw new ProcessTripException(NonConfigurableConstants.MANDATORY_TRIP_START);
				} else
					tmtbAcquireTxn.setTripStartDt(tripStartTimeStamp);

				//19/12/2021 - Trip date Enhance start
				boolean toIgnoreTrip = checkTripEnhanceXDay(tmtbAcquireTxn.getTripStartDt());
				
				if(toIgnoreTrip)
					throw new ProcessTripException(NonConfigurableConstants.TRIPDATEIGNORE);
				
				//19/12/2021 - Trip date enhance end
				
				if (jobNo != null && !"".equals(jobNo))
					tmtbAcquireTxn.setJobNo(jobNo);
				else
					throw new ProcessTripException(NonConfigurableConstants.MANDATORY_JOB_NO);

				// Trip End Date

				if (tripEndTimeStamp != null) {
					tmtbAcquireTxn.setTripEndDt(tripEndTimeStamp);
				} else
					throw new ProcessTripException(NonConfigurableConstants.MANDATORY_TRIP_END);

				// tmtbAcquireTxn.setMstbMasterTableByServiceProvider(mstbMasterTableByServiceProvider);

				// Conversion of string to BigDecimal
				// total amount must be >0 - confirmed by comfort
				if (totalAmt != null && totalAmt.signum() > 0) {
					
					tmtbAcquireTxn.setBillableAmt(totalAmt);
					// If complimentary is "Y" set billable amount to 0

					if (complimentary != null && !"".equals(complimentary)) {
						tmtbAcquireTxn.setComplimentary(complimentary);
						if (NonConfigurableConstants.BOOLEAN_YES
								.equalsIgnoreCase(complimentary)) {
							tmtbAcquireTxn.setBillableAmt(ZERO);
						}
					} else
						tmtbAcquireTxn
								.setComplimentary(NonConfigurableConstants.BOOLEAN_NO);
					bdTotalAmt = tmtbAcquireTxn.getBillableAmt();
				} else {
					// throw exception - payment is a mandatory field
					throw new ProcessTripException(NonConfigurableConstants.MANDATORY_TXN_AMOUNT);
				}

				if (driverIC != null && !"".equals(driverIC))
					tmtbAcquireTxn.setNric(driverIC);
				else
					tmtbAcquireTxn.setNric(null);

				// Vehicle No
				if (vehicleNo != null && !"".equals(vehicleNo))
					tmtbAcquireTxn.setTaxiNo(vehicleNo);
				else
					tmtbAcquireTxn.setTaxiNo(null);

				// Destination
				if (dest != null && !"".equals(dest))
					tmtbAcquireTxn.setDestination(dest);
				else
					tmtbAcquireTxn.setDestination(null);
				
				// Trip Code
				if (tripCode != null && !"".equals(tripCode))
					tmtbAcquireTxn.setProjectDesc(tripCode);
				else
					tmtbAcquireTxn.setProjectDesc(null);
				
				// Trip Desc
				if (tripDesc != null && !"".equals(tripDesc))
					tmtbAcquireTxn.setTripCodeReason(tripDesc);
				else
					tmtbAcquireTxn.setTripCodeReason(null);

				//19/4/2019 CR0419009 map generic vehicle type 
				boolean defaultVehicleType = false;
				
				// Retrieve service provider
				if (entity != null & !"".equals(entity)) {
					MstbMasterTable serviceProviderMaster = ConfigurableConstants
							.getMasterTableByInterfaceMapping(
									ConfigurableConstants.SERVICE_PROVIDER,
									entity);

					if (serviceProviderMaster == null)
					{
						logger.info("Error in createTxnForInterface : Mandatory Entity ("+entity+"): Service Provider not Found");
						throw new ProcessTripException(NonConfigurableConstants.MANDATORY_ENTITY + " (SPR) - "+entity);
					}
					// Set Service Provider
					tmtbAcquireTxn
							.setMstbMasterTableByServiceProvider(serviceProviderMaster);
					
					//19/4/2019 CR0419009 map generic vehicle type 
					MstbMasterTable downloadTripMapGenericVehicleTypeMaster = ConfigurableConstants
							.getMasterTable(
									ConfigurableConstants.DOWNLOAD_TRIP_MAP_VEHICLE_TYPE,
									entity);
					
					if(downloadTripMapGenericVehicleTypeMaster != null
							&& downloadTripMapGenericVehicleTypeMaster.getMasterValue() != null)
					{
						if(downloadTripMapGenericVehicleTypeMaster.getMasterStatus().equals("A"))
						{
							logger.info("Map Generic Vehicle Type Code to Entity: ("+entity+"): "+downloadTripMapGenericVehicleTypeMaster.getMasterValue());
							
							MstbMasterTable vehicleModelMaster = ConfigurableConstants
									.getAllMasterTable(ConfigurableConstants.VEHICLE_MODEL, downloadTripMapGenericVehicleTypeMaster.getMasterValue());
							
							if (vehicleModelMaster != null) {
								logger.info("Generic Vehicle Type (Entity: ("+entity+")) Mapped successfully: " + vehicleModelMaster.getMasterValue());
								tmtbAcquireTxn
								.setMstbMasterTableByVehicleModel(vehicleModelMaster);
								defaultVehicleType = true;
							}
							else
							{
								logger.info("Generic Vehicle Type (Entity: ("+entity+")) not found in master list ("+downloadTripMapGenericVehicleTypeMaster.getMasterValue()+"). Using default vehicle type");
							}
						}
						else
						{
							logger.info("Generic Vehicle Type (Entity: ("+entity+")) not Active. Using default vehicle type");
						}
					}
					else
					{
						logger.info("No Generic Vehicle Type Code found for Entity: ("+entity+")");
					}
					
				} else {
					logger.info("Error in createTxnForInterface : Mandatory Entity ("+entity+"): Entity null or Empty");
					throw new ProcessTripException(NonConfigurableConstants.MANDATORY_ENTITY + " (Entity) - "+entity);
				}

				// Retrieve job type
				if (jobType != null & !"".equals(jobType)) {
					MstbMasterTable jobTypeMaster = ConfigurableConstants
							.getMasterTableByInterfaceMapping(
									ConfigurableConstants.JOB_TYPE, jobType);

					if (jobTypeMaster == null) {
						logger.info("JOB TYPE: " + jobType);
						throw new ProcessTripException(NonConfigurableConstants.JOB_TYPE_NOT_FOUND);
					}

					// Set vehicle type
					tmtbAcquireTxn.setMstbMasterTableByJobType(jobTypeMaster);
				}

				// Retrieve vehicle model
				if (vehicleModel != null & !"".equals(vehicleModel)) {
					
					if(!defaultVehicleType)
					{
						MstbMasterTable vehicleModelMaster = ConfigurableConstants
								.getMasterTableByInterfaceMapping(
										ConfigurableConstants.VEHICLE_MODEL,
										vehicleModel);
	
						if (vehicleModelMaster == null) {
							logger.info("VEHICLE MODEL: " + vehicleModel);
							throw new ProcessTripException(NonConfigurableConstants.VEHICLE_MODEL_NOT_FOUND);
						}
	
						tmtbAcquireTxn
								.setMstbMasterTableByVehicleModel(vehicleModelMaster);
					}
					else
						logger.info("Generic Vehicle Type Model Mapped");
				}

				// Surcharge
				if (surchargeDesc != null && !"".equals(surchargeDesc))
					tmtbAcquireTxn.setSurcharge(surchargeDesc);
				else
					tmtbAcquireTxn.setSurcharge(null);
				
				
				if (product != null & !"".equals(product)) {
					MstbMasterTable vehicleMaster = ConfigurableConstants
							.getMasterTableByInterfaceMapping(
									ConfigurableConstants.VEHICLE_TRIP_TYPE,
									product);
					// Set trip type. Now trip type is not a master record.
					tmtbAcquireTxn
							.setMstbMasterTableByTripType(vehicleMaster);
				} else {
					throw new ProcessTripException(NonConfigurableConstants.MANDATORY_TRIP_TYPE);
				}

				// Get the account based on acctlv1, acctlv2, acctlv3 (for
				// premier only)
				// PREMIER SERVICE
//				if ("INVO".equals(paymentMode)) {
				if(cardLessPaymentList.contains(paymentMode)) {
					// If premier service, the trip start date and trip end date
					// is taken from pickupDt
					
					String cardlessProductType = "";
					for(PmtbProductType cardlessProduct : cardlessProducts) {
						if(cardlessProduct.getInterfaceMappingValue().trim().equals(paymentMode)) {
							cardlessProductType = cardlessProduct.getProductTypeId();
							break;
						}
					}
					
					if (pickupDt != null) {
						tmtbAcquireTxn.setTripStartDt(pickupDt);
						tmtbAcquireTxn.setTripEndDt(pickupDt);
					} else
						throw new ProcessTripException(NonConfigurableConstants.MANDATORY_TRIP_START);

					// Retrieve vehicle type
					if (bookVehGrp != null && !"".equals(bookVehGrp)) {
						MstbMasterTable vehicleMaster = ConfigurableConstants
								.getMasterTableByInterfaceMapping(
										ConfigurableConstants.VEHICLE_TYPE_MASTER_CODE,
										bookVehGrp);

						if (vehicleMaster == null)
							throw new ProcessTripException(NonConfigurableConstants.MANDATORY_VEHICLE_TYPE);

						tmtbAcquireTxn
								.setMstbMasterTableByVehicleType(vehicleMaster);
					} else {
						throw new ProcessTripException(NonConfigurableConstants.MANDATORY_VEHICLE_TYPE);
					}
					/**
					 * Assignment of rest of PREMIER SERVICE required fields
					 */
					// tmtbAcquireTxn.setBookedBy(bookedBy);
					if (bookRef != null && !"".equals(bookRef))
						tmtbAcquireTxn.setBookingRef(bookRef);

					if (flightDetails != null && !"".equals(flightDetails))
						tmtbAcquireTxn.setFlightInfo(flightDetails);

					if (bookedBy != null && !"".equals(bookedBy))
						tmtbAcquireTxn.setBookedBy(bookedBy);

					if (bookedDateTime != null)
						tmtbAcquireTxn.setBookDateTime(bookedDateTime);

					// Pax Name
					if (paxName != null && !"".equals(paxName))
						tmtbAcquireTxn.setPassengerName(paxName);
					else
						tmtbAcquireTxn.setPassengerName(null);

					/***
					 * Calculation of admin fee/GST based on account
					 */
					// Premier services

					// Need to do a mapping for the case of parallel run
					// Get the mapped cust no if any
					String newCustNo = this.daoHelper.getTxnDao()
							.getMappedCustNo(acctlv1);
					if (newCustNo != null) {
						// assigned to acctLv1
						acctlv1 = newCustNo;
					}

					amtbAccount = this.daoHelper.getAccountDao()
							.getAccountbyID(acctlv1, acctlv2, acctlv3);
					tmtbAcquireTxn.setAmtbAccount(amtbAccount);
					// Can pre-defined premier service product type
					pmtbProductType = this.daoHelper.getProductTypeDao()
							.getProductType(
//									NonConfigurableConstants.PREMIER_SERVICE);
									cardlessProductType);
					tmtbAcquireTxn.setPmtbProductType(pmtbProductType);

					if (amtbAccount != null) {
						// Retrieve the product discount from parent corporate
						// account
						AmtbAccount parentAccount = amtbAccount;
						BigDecimal prodDisValue = null;
						while (parentAccount != null) {
							if (parentAccount
									.getAccountCategory()
									.equals(NonConfigurableConstants.ACCOUNT_CATEGORY_CORPORATE)
									|| parentAccount
											.getAccountCategory()
											.equals(NonConfigurableConstants.ACCOUNT_CATEGORY_APPLICANT)) {
								break;
							} else
								parentAccount = parentAccount.getAmtbAccount();
						}

						logger.info("Account No = "
								+ parentAccount.getAccountNo().toString());

						if(!NonConfigurableConstants.getBoolean(pmtbProductType.getPrepaid())){
							
							tmtbAcquireTxn.setFareAmt(totalAmt);
							
							// Admin fee effective date is based on current date
							adminFeeDetail = this.daoHelper.getProductDao()
									.getAdminFee(parentAccount.getAccountNo(),
											DateUtil.getCurrentTimestamp());
							if (adminFeeDetail != null) {
								// Calculate admin fee
								adminFeePercent = adminFeeDetail.getAdminFee();
								adminFeePercent.setScale(
										NonConfigurableConstants.NO_OF_DECIMAL,
										BigDecimal.ROUND_HALF_UP);
								tmtbAcquireTxn.setAdminFeePercent(adminFeePercent);
								BigDecimal adminFeeValue = bdTotalAmt
										.multiply(adminFeePercent.divide(HUNDRED,
												4, BigDecimal.ROUND_HALF_UP));
								adminFeeValue = adminFeeValue.setScale(
										NonConfigurableConstants.NO_OF_DECIMAL,
										BigDecimal.ROUND_HALF_UP);
								tmtbAcquireTxn.setAdminFeeValue(adminFeeValue);
								logger.info("Admin Fee Value: " + adminFeeValue);
	
							} else {
								tmtbAcquireTxn.setAdminFeePercent(ZERO);
								tmtbAcquireTxn.setAdminFeeValue(ZERO);
							}
	
							// Discount effective date is based on current date
							discountDetail = this.daoHelper.getProductDao()
									.getProductDiscount(
											parentAccount.getAccountNo(),
											DateUtil.getCurrentTimestamp(),
											tmtbAcquireTxn.getPmtbProductType()
													.getProductTypeId());
							if (discountDetail != null) {
								prodDisPercent = discountDetail
										.getProductDiscount();
								prodDisPercent.setScale(
										NonConfigurableConstants.NO_OF_DECIMAL,
										BigDecimal.ROUND_HALF_UP);
								tmtbAcquireTxn.setProdDisPercent(prodDisPercent);
								BigDecimal disAdmFeePercent = tmtbAcquireTxn
										.getAdminFeePercent().subtract(
												prodDisPercent);
								BigDecimal disAdmFee = bdTotalAmt
										.multiply(disAdmFeePercent.divide(HUNDRED,
												4, BigDecimal.ROUND_HALF_UP));
								disAdmFee = disAdmFee.setScale(
										NonConfigurableConstants.NO_OF_DECIMAL,
										BigDecimal.ROUND_HALF_UP);
								prodDisValue = tmtbAcquireTxn.getAdminFeeValue()
										.subtract(disAdmFee);
								prodDisValue = prodDisValue.setScale(
										NonConfigurableConstants.NO_OF_DECIMAL,
										BigDecimal.ROUND_HALF_UP);
								tmtbAcquireTxn.setProdDisValue(prodDisValue);
								logger.info("Product Discount Value: "
										+ prodDisValue);
							} else {
								tmtbAcquireTxn.setProdDisPercent(ZERO);
								tmtbAcquireTxn.setProdDisValue(ZERO);
							}
	
							// Admin fee effective date is based on current date
							AmtbAccount amtbAccountWithEntity = this.daoHelper
									.getAccountDao().getAccountWithEntity(
											parentAccount);
							BigDecimal gstPercent = this.daoHelper.getFinanceDao()
									.getLatestGST(
											amtbAccountWithEntity
													.getFmtbArContCodeMaster()
													.getFmtbEntityMaster()
													.getEntityNo(),
											tmtbAcquireTxn.getPmtbProductType()
													.getProductTypeId(),
											tmtbAcquireTxn.getTripStartDt());
	
							if (gstPercent != null) {
								gstPercent.setScale(
										NonConfigurableConstants.NO_OF_DECIMAL,
										BigDecimal.ROUND_HALF_UP);
								tmtbAcquireTxn.setGstPercent(gstPercent);
								logger.info("GST percent: " + gstPercent);
	
								BigDecimal gstValue = (tmtbAcquireTxn
										.getAdminFeeValue().subtract(tmtbAcquireTxn
										.getProdDisValue())).multiply(gstPercent
										.divide(HUNDRED, 4,
												BigDecimal.ROUND_HALF_UP));
								gstValue = gstValue.setScale(
										NonConfigurableConstants.NO_OF_DECIMAL,
										BigDecimal.ROUND_HALF_UP);
								tmtbAcquireTxn.setGstValue(gstValue);
								logger.info("GST Fee = " + gstValue.toString());
							} else {
								// Calculate gst percent
								// BigDecimal gstPercent =
								// this.daoHelper.getTxnDao().getGstByTripDate(tripStartTimeStamp);
								gstPercent = BigDecimal.ZERO;
								gstPercent.setScale(
										NonConfigurableConstants.NO_OF_DECIMAL,
										BigDecimal.ROUND_HALF_UP);
								tmtbAcquireTxn.setGstPercent(gstPercent);
								logger.info("GST percent: " + gstPercent);
								// Zero GST as admin fee is zero
								tmtbAcquireTxn.setGstValue(ZERO);
							}
						
						}
						

					} else {
						throw new ProcessTripException(NonConfigurableConstants.NO_SUCH_ACCOUNT);
					}

				}
				// Get the account based on
				else {
					// Check whether it is a external product type first
					PmtbProductType externalProductType = this.daoHelper
							.getProductTypeDao().getExternalProductType(
									cardNo.substring(0, 6),
									cardNo.substring(6, 10));

					if (externalProductType != null) {
						List<Integer> accountNos = this.daoHelper
								.getProductTypeDao()
								.getAccountSubscribedToExternalProductType(
										externalProductType.getProductTypeId());
						if (accountNos.isEmpty())
							throw new ProcessTripException(NonConfigurableConstants.NO_SUCH_EXTERNAL_PRODUCT_TYPE_ACCOUNT);
						else if (accountNos.size() > 1)
							throw new ProcessTripException(NonConfigurableConstants.MORE_THAN_ONE_EXTERNAL_PRODUCT_TYPE_ACCOUNT);
						else {
							// Account is always top level
							Integer accountNo = accountNos.get(0);
							amtbAccount = (AmtbAccount) this.get(
									AmtbAccount.class, accountNo);
							if (amtbAccount == null)
								throw new ProcessTripException(NonConfigurableConstants.NO_SUCH_EXTERNAL_PRODUCT_TYPE_ACCOUNT);

							tmtbAcquireTxn.setAmtbAccount(amtbAccount);
							tmtbAcquireTxn
									.setPmtbProductType(externalProductType);
							tmtbAcquireTxn.setExternalCardNo(cardNo);

							if(!NonConfigurableConstants.getBoolean(externalProductType.getPrepaid())){
							
								tmtbAcquireTxn.setFareAmt(totalAmt);
								
								// Admin fee effective date is based on current date
								adminFeeDetail = this.daoHelper.getProductDao()
										.getAdminFee(amtbAccount.getAccountNo(),
												DateUtil.getCurrentTimestamp());
								if (adminFeeDetail != null) {
									// Calculate admin fee
									adminFeePercent = adminFeeDetail.getAdminFee();
									adminFeePercent.setScale(
											NonConfigurableConstants.NO_OF_DECIMAL,
											BigDecimal.ROUND_HALF_UP);
									tmtbAcquireTxn
											.setAdminFeePercent(adminFeePercent);
									BigDecimal adminFeeValue = bdTotalAmt
											.multiply(adminFeePercent.divide(
													HUNDRED, 4,
													BigDecimal.ROUND_HALF_UP));
									adminFeeValue = adminFeeValue.setScale(
											NonConfigurableConstants.NO_OF_DECIMAL,
											BigDecimal.ROUND_HALF_UP);
									tmtbAcquireTxn.setAdminFeeValue(adminFeeValue);
									logger.info("Admin Fee Value: " + adminFeeValue);
	
								} else {
									tmtbAcquireTxn.setAdminFeePercent(ZERO);
									tmtbAcquireTxn.setAdminFeeValue(ZERO);
								}
	
								logger.info("Account No = "
										+ amtbAccount.getAccountNo().toString());
								BigDecimal prodDisValue = null;
								discountDetail = this.daoHelper.getProductDao()
										.getProductDiscount(
												amtbAccount.getAccountNo(),
												DateUtil.getCurrentTimestamp(),
												externalProductType
														.getProductTypeId());
								if (discountDetail != null) {
									prodDisPercent = discountDetail
											.getProductDiscount();
									prodDisPercent.setScale(
											NonConfigurableConstants.NO_OF_DECIMAL,
											BigDecimal.ROUND_HALF_UP);
									tmtbAcquireTxn
											.setProdDisPercent(prodDisPercent);
									BigDecimal disAdmFeePercent = tmtbAcquireTxn
											.getAdminFeePercent().subtract(
													prodDisPercent);
									BigDecimal disAdmFee = bdTotalAmt
											.multiply(disAdmFeePercent.divide(
													HUNDRED, 4,
													BigDecimal.ROUND_HALF_UP));
									disAdmFee = disAdmFee.setScale(
											NonConfigurableConstants.NO_OF_DECIMAL,
											BigDecimal.ROUND_HALF_UP);
									prodDisValue = tmtbAcquireTxn
											.getAdminFeeValue().subtract(disAdmFee);
									prodDisValue = prodDisValue.setScale(
											NonConfigurableConstants.NO_OF_DECIMAL,
											BigDecimal.ROUND_HALF_UP);
									tmtbAcquireTxn.setProdDisValue(prodDisValue);
									logger.info("Product Discount Value: "
											+ prodDisValue);
								} else {
									tmtbAcquireTxn.setProdDisPercent(ZERO);
									tmtbAcquireTxn.setProdDisValue(ZERO);
								}
	
								// Admin fee effective date is based on current date
								AmtbAccount amtbAccountWithEntity = this.daoHelper
										.getAccountDao().getAccountWithEntity(
												amtbAccount);
								BigDecimal gstPercent = this.daoHelper
										.getFinanceDao().getLatestGST(
												amtbAccountWithEntity
														.getFmtbArContCodeMaster()
														.getFmtbEntityMaster()
														.getEntityNo(),
												tmtbAcquireTxn.getPmtbProductType()
														.getProductTypeId(),
												tmtbAcquireTxn.getTripStartDt());
	
								if (gstPercent != null) {
									gstPercent.setScale(
											NonConfigurableConstants.NO_OF_DECIMAL,
											BigDecimal.ROUND_HALF_UP);
									tmtbAcquireTxn.setGstPercent(gstPercent);
									logger.info("GST percent: " + gstPercent);
	
									BigDecimal gstValue = (tmtbAcquireTxn
											.getAdminFeeValue()
											.subtract(tmtbAcquireTxn
													.getProdDisValue()))
											.multiply(gstPercent.divide(HUNDRED, 4,
													BigDecimal.ROUND_HALF_UP));
									gstValue = gstValue.setScale(
											NonConfigurableConstants.NO_OF_DECIMAL,
											BigDecimal.ROUND_HALF_UP);
									tmtbAcquireTxn.setGstValue(gstValue);
									logger.info("GST Fee = " + gstValue.toString());
								} else {
									// Calculate gst percent
									// BigDecimal gstPercent =
									// this.daoHelper.getTxnDao().getGstByTripDate(tripStartTimeStamp);
									gstPercent = BigDecimal.ZERO;
									gstPercent.setScale(
											NonConfigurableConstants.NO_OF_DECIMAL,
											BigDecimal.ROUND_HALF_UP);
									tmtbAcquireTxn.setGstPercent(gstPercent);
									logger.info("GST percent: " + gstPercent);
									// Zero GST as admin fee is zero
									tmtbAcquireTxn.setGstValue(ZERO);
								}
							}
						}
					} else {
						// Convert the trip date to timestamp
						amtbAccount = this.daoHelper.getAccountDao()
								.getAccountForDownload(cardNo,
										tripStartTimeStamp);
						tmtbAcquireTxn.setAmtbAccount(amtbAccount);

						if (amtbAccount != null) {
							// Retrieve the product discount from parent
							// corporate account
							AmtbAccount parentAccount = amtbAccount;
							BigDecimal prodDisValue = null;
							while (parentAccount != null) {
								if (parentAccount
										.getAccountCategory()
										.equals(NonConfigurableConstants.ACCOUNT_CATEGORY_CORPORATE)
										|| parentAccount
												.getAccountCategory()
												.equals(NonConfigurableConstants.ACCOUNT_CATEGORY_APPLICANT)) {
									break;
								} else
									parentAccount = parentAccount
											.getAmtbAccount();
							}

							// Set product object
							if (tripStartTimeStamp != null) {
								pmtbProduct = this.daoHelper.getProductDao()
										.getProduct(cardNo, tripStartTimeStamp);
								if (pmtbProduct != null) {
									// Check if product is currently being used
									if (NonConfigurableConstants.PRODUCT_STATUS_USED
											.equalsIgnoreCase(pmtbProduct
													.getCurrentStatus())) {
										throw new ProcessTripException(NonConfigurableConstants.USED_PRODUCT);
									}

									// check if the product has been re-tagged
									// before using internal product_no. If it
									// is being re-tagged, used the correct
									// re-tagged account
									// based on trip-date
									PmtbProductRetag pmtbProductRetag = this.daoHelper
											.getProductDao()
											.getRetagProductsByDate(
													pmtbProduct.getProductNo(),
													tripStartTimeStamp);
									if (pmtbProductRetag != null) {
										// There is a retagged. set account to
										// this retagged account
										tmtbAcquireTxn
												.setAmtbAccount(pmtbProductRetag
														.getAmtbAccountByCurrentAccountNo());
									}
									tmtbAcquireTxn.setPmtbProduct(pmtbProduct);
									pmtbProductType = pmtbProduct
											.getPmtbProductType();
									tmtbAcquireTxn
											.setPmtbProductType(pmtbProduct
													.getPmtbProductType());
									if (pmtbProduct
											.getPmtbProductType()
											.getFixedValue()
											.equals(NonConfigurableConstants.BOOLEAN_YES)) {
										// if product fixed value, set to
										// product fixed value for billable amt
										if (complimentary != null
												&& !"".equals(complimentary)) {
											if (NonConfigurableConstants.BOOLEAN_NO
													.equalsIgnoreCase(complimentary)) {
												tmtbAcquireTxn
														.setBillableAmt(pmtbProduct
																.getFixedValue());
											} else
												tmtbAcquireTxn
														.setBillableAmt(BigDecimal.ZERO);
										} else {
											tmtbAcquireTxn
													.setBillableAmt(pmtbProduct
															.getFixedValue());
										}
										bdTotalAmt = tmtbAcquireTxn
												.getBillableAmt();
									}
								} else {
									throw new ProcessTripException(NonConfigurableConstants.NO_SUCH_PRODUCT);
								}
							}

							if(!NonConfigurableConstants.getBoolean(pmtbProductType.getPrepaid())){
							
								tmtbAcquireTxn.setFareAmt(totalAmt);
								
								// Admin fee effective date is based on current date
								adminFeeDetail = this.daoHelper.getProductDao()
										.getAdminFee(parentAccount.getAccountNo(),
												DateUtil.getCurrentTimestamp());
								if (adminFeeDetail != null) {
									// Calculate admin fee
									adminFeePercent = adminFeeDetail.getAdminFee();
									adminFeePercent.setScale(
											NonConfigurableConstants.NO_OF_DECIMAL,
											BigDecimal.ROUND_HALF_UP);
									tmtbAcquireTxn
											.setAdminFeePercent(adminFeePercent);
									BigDecimal adminFeeValue = bdTotalAmt
											.multiply(adminFeePercent.divide(
													HUNDRED, 4,
													BigDecimal.ROUND_HALF_UP));
									adminFeeValue = adminFeeValue.setScale(
											NonConfigurableConstants.NO_OF_DECIMAL,
											BigDecimal.ROUND_HALF_UP);
									tmtbAcquireTxn.setAdminFeeValue(adminFeeValue);
									logger.info("Admin Fee Value: " + adminFeeValue);
	
								} else {
									tmtbAcquireTxn.setAdminFeePercent(ZERO);
									tmtbAcquireTxn.setAdminFeeValue(ZERO);
								}
	
								logger.info("Account No = "
										+ parentAccount.getAccountNo().toString());
								discountDetail = this.daoHelper.getProductDao()
										.getProductDiscount(
												parentAccount.getAccountNo(),
												DateUtil.getCurrentTimestamp(),
												pmtbProduct.getPmtbProductType()
														.getProductTypeId());
								if (discountDetail != null) {
									prodDisPercent = discountDetail
											.getProductDiscount();
									prodDisPercent.setScale(
											NonConfigurableConstants.NO_OF_DECIMAL,
											BigDecimal.ROUND_HALF_UP);
									tmtbAcquireTxn
											.setProdDisPercent(prodDisPercent);
									BigDecimal disAdmFeePercent = tmtbAcquireTxn
											.getAdminFeePercent().subtract(
													prodDisPercent);
									BigDecimal disAdmFee = bdTotalAmt
											.multiply(disAdmFeePercent.divide(
													HUNDRED, 4,
													BigDecimal.ROUND_HALF_UP));
									disAdmFee = disAdmFee.setScale(
											NonConfigurableConstants.NO_OF_DECIMAL,
											BigDecimal.ROUND_HALF_UP);
									prodDisValue = tmtbAcquireTxn
											.getAdminFeeValue().subtract(disAdmFee);
									prodDisValue = prodDisValue.setScale(
											NonConfigurableConstants.NO_OF_DECIMAL,
											BigDecimal.ROUND_HALF_UP);
									tmtbAcquireTxn.setProdDisValue(prodDisValue);
									logger.info("Product Discount Value: "
											+ prodDisValue);
								} else {
									tmtbAcquireTxn.setProdDisPercent(ZERO);
									tmtbAcquireTxn.setProdDisValue(ZERO);
								}
	
								// Admin fee effective date is based on current date
								AmtbAccount amtbAccountWithEntity = this.daoHelper
										.getAccountDao().getAccountWithEntity(
												parentAccount);
								BigDecimal gstPercent = this.daoHelper
										.getFinanceDao().getLatestGST(
												amtbAccountWithEntity
														.getFmtbArContCodeMaster()
														.getFmtbEntityMaster()
														.getEntityNo(),
												tmtbAcquireTxn.getPmtbProductType()
														.getProductTypeId(),
												tmtbAcquireTxn.getTripStartDt());
	
								if (gstPercent != null) {
									gstPercent.setScale(
											NonConfigurableConstants.NO_OF_DECIMAL,
											BigDecimal.ROUND_HALF_UP);
									tmtbAcquireTxn.setGstPercent(gstPercent);
									logger.info("GST percent: " + gstPercent);
	
									BigDecimal gstValue = (tmtbAcquireTxn
											.getAdminFeeValue()
											.subtract(tmtbAcquireTxn
													.getProdDisValue()))
											.multiply(gstPercent.divide(HUNDRED, 4,
													BigDecimal.ROUND_HALF_UP));
									gstValue = gstValue.setScale(
											NonConfigurableConstants.NO_OF_DECIMAL,
											BigDecimal.ROUND_HALF_UP);
									tmtbAcquireTxn.setGstValue(gstValue);
									logger.info("GST Fee = " + gstValue.toString());
								} else {
									// Calculate gst percent
									// BigDecimal gstPercent =
									// this.daoHelper.getTxnDao().getGstByTripDate(tripStartTimeStamp);
									gstPercent = BigDecimal.ZERO;
									gstPercent.setScale(
											NonConfigurableConstants.NO_OF_DECIMAL,
											BigDecimal.ROUND_HALF_UP);
									tmtbAcquireTxn.setGstPercent(gstPercent);
									logger.info("GST percent: " + gstPercent);
									// Zero GST as admin fee is zero
									tmtbAcquireTxn.setGstValue(ZERO);
								}
							}

						} else {
							throw new ProcessTripException(NonConfigurableConstants.NO_SUCH_PRODUCT);
						}
					}
				}
				
				PmtbProductType productType = tmtbAcquireTxn.getPmtbProductType();
				
				//now handle admin fee and gst of prepaid product, they are downloaded from view instead of calcualted from total amount
				if(NonConfigurableConstants.getBoolean(productType.getPrepaid())){
					
					tmtbAcquireTxn.setFareAmt(txnAmount);
					
					adminAmount = adminAmount!=null? adminAmount:ZERO;
					gstAmount = gstAmount!=null ? gstAmount : ZERO;
					
					tmtbAcquireTxn.setAdminFeeValue(adminAmount);
					tmtbAcquireTxn.setGstValue(gstAmount);
					
					tmtbAcquireTxn.setProdDisPercent(ZERO);
					tmtbAcquireTxn.setProdDisValue(ZERO);
				}
				
				// Retrieve trip type
				if (product != null && !"".equals(product)) {
					MstbMasterTable tripTypeMaster = ConfigurableConstants
							.getMasterTableByInterfaceMapping(ConfigurableConstants.VEHICLE_TRIP_TYPE,
									product);
					
					if(tripTypeMaster != null)
					{
						tmtbAcquireTxn.setMstbMasterTableByTripType(tripTypeMaster);
						
						if(tripTypeMaster.getInterfaceMappingValue() != null)
						{
							if(tripTypeMaster.getMasterStatus().trim().equalsIgnoreCase("A"))
							{
								if(tripTypeMaster.getInterfaceMappingValue().length() >= 4)
								{
									if(tripTypeMaster.getInterfaceMappingValue().substring(0, 4).equalsIgnoreCase("FLAT"))	
									{
										logger.info("Trip Type is Flat Fare, setting GST & Admin to 0");
										tmtbAcquireTxn.setGstValue(ZERO);
										tmtbAcquireTxn.setAdminFeeValue(ZERO);
										tmtbAcquireTxn.setProdDisValue(ZERO);
									}
								}
							}
							else
								throw new ProcessTripException(NonConfigurableConstants.TRIP_TYPE_NOT_ACTIVE, product);	
						}
						else
							throw new ProcessTripException("Trip Type No Interface Mapping Value", product);	
					} 
					else
						throw new ProcessTripException(NonConfigurableConstants.TRIP_TYPE_NOT_FOUND, product);	
				} 
				else 
					throw new ProcessTripException(NonConfigurableConstants.MANDATORY_TRIP_TYPE);
				
				//check if future trips exists
				if(tmtbAcquireTxn.getTripStartDt().after(DateUtil.getCurrentTimestamp())) {
					throw new ProcessTripException(NonConfigurableConstants.FUTURE_TRIP);
				}
				if(tmtbAcquireTxn.getTripEndDt().after(DateUtil.getCurrentTimestamp())) {
					throw new ProcessTripException(NonConfigurableConstants.FUTURE_TRIP);
				}
				
				//check if account status is terminated
				AmtbAccount txnAmtbAccount = tmtbAcquireTxn.getAmtbAccount();
				if(txnAmtbAccount!=null){
					
					List<AmtbAcctStatus> amtbAcctStatuses = this.daoHelper.getAccountDao().getStatuses(txnAmtbAccount.getAccountNo());
					if(amtbAcctStatuses!=null && amtbAcctStatuses.size()>0){
						AmtbAcctStatus amtbStatusBeforeTripStart = getStatus(amtbAcctStatuses, new Date(tripStartTimeStamp.getTime()));
						if(amtbStatusBeforeTripStart!=null && NonConfigurableConstants.ACCOUNT_STATUS_TERMINATED.equals(amtbStatusBeforeTripStart.getAcctStatus())){
							throw new ProcessTripException(NonConfigurableConstants.TERMINATED_ACCOUNT);
						}
					}
				}
				
				//check if product is terminated
				PmtbProduct txnPmtbProduct = tmtbAcquireTxn.getPmtbProduct();
				if(txnPmtbProduct!=null)
				{
					PmtbProductStatus pmtbProductStatus = this.daoHelper.getProductDao().getLatestProductStatus(txnPmtbProduct.getCardNo(), tripStartTimeStamp);
					if(pmtbProductStatus!=null)
					{
						if (NonConfigurableConstants.PRODUCT_STATUS_TERMINATED.equalsIgnoreCase(pmtbProductStatus.getStatusTo())) {
							throw new ProcessTripException(NonConfigurableConstants.TERMINATED_PRODUCT);
						}
					}
				}
				
				// tmtbAcquireTxn.setProjectDesc(projectDesc)
				tmtbAcquireTxn.setPromoDisPercent(ZERO);
				tmtbAcquireTxn.setPromoDisValue(ZERO);

				if (pickup != null && !"".equals(pickup))
					tmtbAcquireTxn.setPickupAddress(pickup);
				else
					tmtbAcquireTxn.setPickupAddress("N.A.");
				// Job Status
				if (jobStatus != null && !"".equals(jobStatus))
					tmtbAcquireTxn.setJobStatus(jobStatus);
				else
					tmtbAcquireTxn.setJobStatus(null);
				// Payment Mode
				if (paymentMode != null && !"".equals(paymentMode))
					tmtbAcquireTxn.setPaymentMode(paymentMode);
				else
					tmtbAcquireTxn.setPaymentMode(null);

				// Approval Code
				if (approvalCode != null && !"".equals(approvalCode))
					tmtbAcquireTxn.setApprovalCode(approvalCode);
				else
					tmtbAcquireTxn.setApprovalCode(null);

				// Sales Draft No
				if (salesDraftNo != null && !"".equals(salesDraftNo))
					tmtbAcquireTxn.setSalesDraftNo(salesDraftNo);
				else
					tmtbAcquireTxn.setSalesDraftNo(null);

				// Settlement Date
				if (setlDt != null) {
					tmtbAcquireTxn.setSetlDate(DateUtil
							.convertTimestampToSQLDate(setlDt));
				} else
					tmtbAcquireTxn.setSetlDate(null);

				// Bank TID
				if (bankTID != null && !"".equals(bankTID))
					tmtbAcquireTxn.setBankTid(bankTID);
				else
					tmtbAcquireTxn.setBankTid(bankTID);

				// Bank Batch Close No
				if (bankBatchCloseNo != null && !"".equals(bankBatchCloseNo))
					tmtbAcquireTxn.setBankBatchCloseNo(bankBatchCloseNo);
				else
					tmtbAcquireTxn.setBankBatchCloseNo(null);

				
				//Check whether it is prepaid
				if(NonConfigurableConstants.getBoolean(productType.getPrepaid())){
					tmtbAcquireTxn.setTxnStatus(NonConfigurableConstants.TRANSACTION_STATUS_BILLED);
				} else {
					tmtbAcquireTxn.setTxnStatus(NonConfigurableConstants.TRANSACTION_STATUS_ACTIVE);
				}
				
				tmtbAcquireTxn
						.setCreatedBy(NonConfigurableConstants.INTERFACE_USER);
				tmtbAcquireTxn.setCreatedDt(DateUtil.getCurrentTimestamp());
				tmtbAcquireTxn.setFmsFlag(NonConfigurableConstants.BOOLEAN_NO);
				if (levy != null) {
					tmtbAcquireTxn.setLevy(levy);
				} else
					tmtbAcquireTxn.setLevy(ZERO);
				
				if (incentiveAmt != null) {
					tmtbAcquireTxn.setIncentiveAmt(incentiveAmt);
				} else
					tmtbAcquireTxn.setIncentiveAmt(ZERO);
				
				if (promoAmt != null) {
					tmtbAcquireTxn.setPromoAmt(promoAmt);
				} else
					tmtbAcquireTxn.setPromoAmt(ZERO);
				
				if (cabRewardsAmt != null) {
					tmtbAcquireTxn.setCabRewardsAmt(cabRewardsAmt);
				} else
					tmtbAcquireTxn.setCabRewardsAmt(ZERO);
				
				//Copy offline flag
				tmtbAcquireTxn.setOfflineFlag(ittbTripsTxn.getOfflineFlag());
				
				//distance, voucher amount and discount amount
				tmtbAcquireTxn.setDistance(ittbTripsTxn.getDistance());
				tmtbAcquireTxn.setVoucherAmount(ittbTripsTxn.getVoucherAmount());
				tmtbAcquireTxn.setDiscountAmount(ittbTripsTxn.getDiscountAmount());
				
				// tmtbAcquireTxn.setTxnType(txnType);

				// save this object into db
				// this.daoHelper.getTxnDao().save(tmtbAcquireTxn);

				// Check if it is 1-time usage card
				if (NonConfigurableConstants.BOOLEAN_YES.equals(productType.getOneTimeUsage())){

					// Not required to inform AS as there are very few cases
					// whereby the trips did not go through the PG. By right
					// AS will be updated already.
					this.updateProductUsed(pmtbProduct,
							tmtbAcquireTxn.getTripStartDt(),
							NonConfigurableConstants.INTERFACE_USER, false);
				}
				// Check if it is a multi-usage card
				if (NonConfigurableConstants.BOOLEAN_YES.equals(productType.getCreditLimit())) {

					// Deduct credit limit of product
					if (NonConfigurableConstants.BOOLEAN_NO

							.equals(productType
									.getOneTimeUsage())) {
						// Should not deduct for product if it is a premier
						// service trip
						if (!"INVO".equals(paymentMode)) {
							
							BigDecimal tripUsageAmount = null;
							PmtbProduct txnProduct = tmtbAcquireTxn.getPmtbProduct();
							
							if(NonConfigurableConstants.getBoolean(productType.getPrepaid())){
								tripUsageAmount = handleTripForPrepaidCard(txnProduct, tmtbAcquireTxn , PREPAID_HANDLE_TRIP_ACTION_NEW);
							} else {
								tripUsageAmount = tmtbAcquireTxn.getBillableAmt();
								txnProduct.setCreditBalance(txnProduct.getCreditBalance().subtract(tripUsageAmount));
								
								// 20160518 Fix for the Credit Balance Discrepancy issue
								checkReplaceCard(txnProduct.getCardNo(), tripUsageAmount, "subtract" , "");
							}
							
							this.daoHelper.getProductDao().update(txnProduct);
							this.updateProductCreditLimit(txnProduct, tripUsageAmount, NonConfigurableConstants.INTERFACE_USER);
						
						}
					}
				}
				// Update credit balance in account
				// Should not deduct for account if it is a premier service trip
				if (!"INVO".equals(paymentMode))
					this.updateAccountCreditBalance(tmtbAcquireTxn
							.getPmtbProduct(), tmtbAcquireTxn.getAmtbAccount(),
							tmtbAcquireTxn.getBillableAmt().negate(),
							NonConfigurableConstants.INTERFACE_USER, false);
				else
					this.updateAccountCreditBalance(tmtbAcquireTxn
							.getPmtbProduct(), tmtbAcquireTxn.getAmtbAccount(),
							tmtbAcquireTxn.getBillableAmt().negate(),
							NonConfigurableConstants.INTERFACE_USER, true);	
				
				
			}
			// If payment mode = CRCD, NETS, it will be considered as a
			// non-billable txn
			else if ("CRCA".equals(paymentMode) || "NETS".equals(paymentMode) || "KARHOO".equals(paymentMode)) {
				throw new ProcessTripException(NonConfigurableConstants.IGNORE);
			}
			//if can find in master table "Apt", will be considered as a non billable txn.
			else if(aptMaster != null) {
				if(aptMaster.getMasterStatus().trim().equalsIgnoreCase("A")) {
					throw new ProcessTripException(NonConfigurableConstants.IGNORE);
				}
				else
					throw new ProcessTripException(NonConfigurableConstants.INVALID_PAYMENT_MODE);
			}
			// Any other payment mode is considered as an error
			else {
				throw new ProcessTripException(NonConfigurableConstants.INVALID_PAYMENT_MODE);
			}
		} catch (ConcurrentModificationException cme) {
			LoggerUtil.printStackTrace(logger, cme);
			throw new ProcessTripException(NonConfigurableConstants.CONCURRENT_EXCEPTION);
		} catch (Exception e) {
			
			if(e instanceof ProcessTripException){
				throw e;
			} else {
				String jobno ="(Failed to get Job No)";
				if(ittbTripsTxn!=null && ittbTripsTxn.getJobNo()!=null)
					jobno = ittbTripsTxn.getJobNo();
				
				logger.error("Unhandled Error of txn with Job No: "+ jobno);
				LoggerUtil.printStackTrace(logger, e);
				throw new ProcessTripException(NonConfigurableConstants.UNHANDLED_EXCEPTION);
			}
			
		}


	}

	public List<VwIntfTripsForIb> searchTRIPSview(int records) {
		return this.daoHelper.getTxnDao().getTRIPSview(records);
	}

	public List<IttbTripsTxn> searchTxnView(int records) {
		return this.daoHelper.getTxnDao().getTxnView(records);
	}

	private void updateSucceeded(int[] succeededList) throws Exception {
		this.daoHelper.getTxnDao().updateSucceeded(succeededList);
	}

	public void started(int retrievalPK) throws Exception {
		this.daoHelper.getTxnDao().started(retrievalPK);
	}

	public void ended(int retrievalPK) throws Exception {
		this.daoHelper.getTxnDao().ended(retrievalPK);
	}

	public String sendTRIPSReq(List<IttbTripsTxn> requestList, int[] stats)
			throws Exception {
		// Retrieve from request table
		int totalNoOfRecords = 0;
		int totalNoOfSuccess = 0;
		int totalNoOfFailed = 0;
		int totalNoOfIgnored = 0;
		int totalNoOfConcurrent = 0;
		totalNoOfRecords = stats[0];
		totalNoOfSuccess = stats[1];
		totalNoOfFailed = stats[2];
		totalNoOfIgnored = stats[3];
		totalNoOfConcurrent = stats[4];

		try {
			if (requestList != null) {
				//implement to save the trip one by one instead of lumpsum in a list to avoid possibly transient exception issue
				//ArrayList<IttbTripsTxnError> tripsErrorList = new ArrayList<IttbTripsTxnError>();
				//ArrayList<TmtbAcquireTxn> tripsList = new ArrayList<TmtbAcquireTxn>();
				logger.info("Request List Size: " + requestList.size());
				Iterator<IttbTripsTxn> iter = requestList.iterator();

				while (iter.hasNext()) {
					IttbTripsTxn temp = iter.next();
					
					try {
					
						TmtbAcquireTxn tmtbAcquireTxn = new TmtbAcquireTxn();
						// To change to new interface value
						// Loop the request table
						logger.info("PERFORMANCE CHECK: Before createTxnForInterface");
						this.createTxnForInterface(temp, tmtbAcquireTxn);
						logger.info("PERFORMANCE CHECK: After createTxnForInterface");
						// Add to the success arraylist
						
						totalNoOfSuccess++;
						//tripsList.add(tmtbAcquireTxn);
						this.daoHelper.getGenericDao().save(tmtbAcquireTxn, NonConfigurableConstants.INTERFACE_USER);
						
						this.daoHelper.getGenericDao().delete(temp);
	
					} catch(ProcessTripException e){
						String msg = e.getMessage();
						if (NonConfigurableConstants.IGNORE.equals(msg)) {
							// Do nothing
							totalNoOfIgnored++;
							this.daoHelper.getGenericDao().delete(temp);
						} else if (NonConfigurableConstants.CONCURRENT_EXCEPTION
								.equals(msg)) {
							totalNoOfConcurrent++;
							// Do not delete the record in temp table
							// clone and insert into our error tables for
							// information only
							IttbTripsTxnError ittbTripsTxnError = this
									.cloneTxnError(temp);
							ittbTripsTxnError
									.setCreateBy(NonConfigurableConstants.INTERFACE_USER);
							ittbTripsTxnError.setCreateDate(DateUtil
									.getCurrentTimestamp());
							ittbTripsTxnError.setErrorMsg(msg);
							
							//tripsErrorList.add(ittbTripsTxnError);
							this.daoHelper.getGenericDao().save(ittbTripsTxnError, NonConfigurableConstants.INTERFACE_USER);
							
							
						} else {
							// clone and insert into our error tables
							IttbTripsTxnError ittbTripsTxnError = this
									.cloneTxnError(temp);
							ittbTripsTxnError
									.setCreateBy(NonConfigurableConstants.INTERFACE_USER);
							ittbTripsTxnError.setCreateDate(DateUtil
									.getCurrentTimestamp());
							ittbTripsTxnError.setErrorMsg(msg);
							
							//tripsErrorList.add(ittbTripsTxnError);
							this.daoHelper.getGenericDao().save(ittbTripsTxnError, NonConfigurableConstants.INTERFACE_USER);
							
							// Add to total list for printing of error
							totalNoOfFailed++;
							this.daoHelper.getGenericDao().delete(temp);
						}
						
					}
					
					totalNoOfRecords++;

				}

				/*
				logger.info("PERFORMANCE CHECK: Before saving all trips list");
				this.daoHelper.getGenericDao().saveAll(tripsList,
						NonConfigurableConstants.INTERFACE_USER);
				logger.info("PERFORMANCE CHECK: After saving all trips list");

				logger.info("PERFORMANCE CHECK: Before saving all error trips list");
				this.daoHelper.getGenericDao().saveAll(tripsErrorList,
						NonConfigurableConstants.INTERFACE_USER);
				logger.info("PERFORMANCE CHECK: After saving all error trips list");*/
			}
			stats[0] = totalNoOfRecords;
			stats[1] = totalNoOfSuccess;
			stats[2] = totalNoOfFailed;
			stats[3] = totalNoOfIgnored;
			stats[4] = totalNoOfConcurrent;
		} catch (Exception e) {
			throw new Exception(e);
		} finally {

		}
		return "SUCCESS";
	}

	public int retrieveFromTRIPS(List<VwIntfTripsForIb> requestList, int[] stats)
			throws Exception {
		// Retrieve from request table

		ArrayList<String> tripsSuccessArrayList = new ArrayList<String>();
		ArrayList<IttbTripsTxnError> tripsErrorList = new ArrayList<IttbTripsTxnError>();
		ArrayList<IttbTripsTxn> tripsList = new ArrayList<IttbTripsTxn>();

		int totalNoOfRecords = 0;
		int totalNoOfSuccess = stats[0];
		int totalNoOfIgnored = stats[1];

		try {
			if (requestList != null) {
				logger.info("Request List Size: " + requestList.size());
				Iterator<VwIntfTripsForIb> iter = requestList.iterator();
				
				List<String> cardLessInterfaceMappingList = new ArrayList<String>();
				cardLessInterfaceMappingList.add("CABC");
				cardLessInterfaceMappingList.add("EVCH");
				List<PmtbProductType> cardlessProducts = this.businessHelper.getProductTypeBusiness().getCardlessProductType();
				
				for(PmtbProductType cardlessProduct : cardlessProducts) {
					if(cardlessProduct.getInterfaceMappingValue() != null)
					{
						if(!cardlessProduct.getInterfaceMappingValue().trim().equals(""))
							cardLessInterfaceMappingList.add(cardlessProduct.getInterfaceMappingValue().trim());
					}
				}
				while (iter.hasNext()) {
					String msg = "";
					VwIntfTripsForIb temp = iter.next();
					// To change to new interface value
					// Loop the request table

					
					String paymentMode = temp.getPaymentMode();
//					if ("CABC".equals(paymentMode)
//							|| "EVCH".equals(paymentMode)
//							|| "INVO".equals(paymentMode)) {
					if (cardLessInterfaceMappingList.contains(paymentMode)) {
						// Insert only those required
						// Set to a list for mass insert
						IttbTripsTxn ittbTripsTxn = this.cloneTrips(temp);
						ittbTripsTxn
								.setCreateBy(NonConfigurableConstants.INTERFACE_USER);
						ittbTripsTxn.setCreateDate(DateUtil
								.getCurrentTimestamp());
						tripsList.add(ittbTripsTxn);
						totalNoOfSuccess++;
					} else {
						// Not required to logged to error for other payment
						// mode
						totalNoOfIgnored++;
					}
					// Put to successlist - note: always success regardless if
					// IBS failed - unless it is a DB error
					tripsSuccessArrayList.add(temp.getTripIntfPk().toString());
					totalNoOfRecords++;
				}
				// flush the records
				this.daoHelper.getGenericDao().saveAll(tripsList,
						NonConfigurableConstants.INTERFACE_USER);

				// Call the stored procedure to update the view
				// Iterate hashmap and save this object into db
				int[] tripsSuccessList = new int[tripsSuccessArrayList.size()];

				// Update success to TRIPS view
				if (tripsSuccessArrayList != null) {
					if (!tripsSuccessArrayList.isEmpty()) {
						for (int i = 0; i < tripsSuccessArrayList.size(); i++) {
							tripsSuccessList[i] = Integer
									.parseInt(tripsSuccessArrayList.get(i));
						}
						//toDo: comment below for SIT testing
						this.updateSucceeded(tripsSuccessList); 
					}
				}
			}
			tripsSuccessArrayList.clear();
			tripsErrorList.clear();

			stats[0] = totalNoOfSuccess;
			stats[1] = totalNoOfIgnored;
		} catch (Exception e) {
			throw new Exception(e);
		} finally {

		}
		return totalNoOfRecords;
	}

	private IttbTripsTxnError cloneTxnError(IttbTripsTxn ittbTripsTxn) {
		IttbTripsTxnError ittbTripsTxnError = new IttbTripsTxnError();
		ittbTripsTxnError.setTripIntfPk(ittbTripsTxn.getTripIntfPk());
		ittbTripsTxnError.setAccountLv1(ittbTripsTxn.getAccountLv1());
		ittbTripsTxnError.setAccountLv2(ittbTripsTxn.getAccountLv2());
		ittbTripsTxnError.setAccountLv3(ittbTripsTxn.getAccountLv3());
		ittbTripsTxnError.setBankBatchCloseNo(ittbTripsTxn
				.getBankBatchCloseNo());
		ittbTripsTxnError.setBankTid(ittbTripsTxn.getBankTid());
		ittbTripsTxnError.setBookedBy(ittbTripsTxn.getBookedBy());
		ittbTripsTxnError.setBookedDateTime(ittbTripsTxn.getBookedDateTime());
		ittbTripsTxnError.setBookedVehicleGroup(ittbTripsTxn
				.getBookedVehicleGroup());
		ittbTripsTxnError.setBookingReference(ittbTripsTxn
				.getBookingReference());
		ittbTripsTxnError.setCardNo(ittbTripsTxn.getCardNo());
		ittbTripsTxnError.setComplimentary(ittbTripsTxn.getComplimentary());
		ittbTripsTxnError.setCardType(ittbTripsTxn.getCardType());
		ittbTripsTxnError.setDestination(ittbTripsTxn.getDestination());
		ittbTripsTxnError.setDriverIc(ittbTripsTxn.getDriverIc());
		ittbTripsTxnError.setDriverLevy(ittbTripsTxn.getDriverLevy());
		ittbTripsTxnError.setIncentiveAmt(ittbTripsTxn.getIncentiveAmt());
		ittbTripsTxnError.setPromoAmt(ittbTripsTxn.getPromoAmt());
		ittbTripsTxnError.setCabRewardsAmt(ittbTripsTxn.getCabRewardsAmt());
		ittbTripsTxnError.setEntity(ittbTripsTxn.getEntity());
		ittbTripsTxnError.setJobNo(ittbTripsTxn.getJobNo());
		ittbTripsTxnError.setJobStatus(ittbTripsTxn.getJobStatus());
		ittbTripsTxnError.setJobType(ittbTripsTxn.getJobType());
		ittbTripsTxnError.setPaxName(ittbTripsTxn.getPaxName());
		ittbTripsTxnError.setPaymentMode(ittbTripsTxn.getPaymentMode());
		ittbTripsTxnError.setPickup(ittbTripsTxn.getPickup());
		// ittbTripsTxnError.setProduct(ittbTripsTxn.getProduct());
		// Should get productID instead as it is the code that we are mapping
		ittbTripsTxnError.setProductId(ittbTripsTxn.getProductId());
		ittbTripsTxnError.setSalesDraftNo(ittbTripsTxn.getSalesDraftNo());
		ittbTripsTxnError.setSetlDate(ittbTripsTxn.getSetlDate());
		ittbTripsTxnError.setSurchargeDescription(ittbTripsTxn
				.getSurchargeDescription());
		ittbTripsTxnError.setTripStart(ittbTripsTxn.getTripStart());
		ittbTripsTxnError.setTripEnd(ittbTripsTxn.getTripEnd());
		ittbTripsTxnError.setTotalAmount(ittbTripsTxn.getTotalAmount());
		ittbTripsTxnError.setTxnAmount(ittbTripsTxn.getTxnAmount());
		ittbTripsTxnError.setVehicleNo(ittbTripsTxn.getVehicleNo());
		ittbTripsTxnError.setAgentName(ittbTripsTxn.getAgentName());
		ittbTripsTxnError.setFlightDetails(ittbTripsTxn.getFlightDetails());
		ittbTripsTxnError.setPickupDt(ittbTripsTxn.getPickupDt());
		ittbTripsTxnError.setHotelChargeTo(ittbTripsTxn.getHotelChargeTo());
		ittbTripsTxnError.setVehType(ittbTripsTxn.getVehType());
		ittbTripsTxnError.setMid(ittbTripsTxn.getMid());
		ittbTripsTxnError.setOfflineFlag(ittbTripsTxn.getOfflineFlag());
		ittbTripsTxnError.setOfflineTxnDate(ittbTripsTxn.getOfflineTxnDate());

		ittbTripsTxnError.setDistance(ittbTripsTxn.getDistance());
		ittbTripsTxnError.setVoucherAmount(ittbTripsTxn.getVoucherAmount());
		ittbTripsTxnError.setDiscountAmount(ittbTripsTxn.getDiscountAmount());
		
		ittbTripsTxnError.setGstAmount(ittbTripsTxn.getGstAmount());
		ittbTripsTxnError.setAdminAmount(ittbTripsTxn.getAdminAmount());
		
		ittbTripsTxnError.setTripCode(ittbTripsTxn.getTripCode());
		ittbTripsTxnError.setTripDesc(ittbTripsTxn.getTripDesc());
		
		return ittbTripsTxnError;
	}

	private IttbTripsTxn cloneTrips(VwIntfTripsForIb vwIntfTripsForIb) {
		IttbTripsTxn ittbTripsTxn = new IttbTripsTxn();
		ittbTripsTxn.setTripIntfPk(vwIntfTripsForIb.getTripIntfPk());
		ittbTripsTxn.setAccountLv1(vwIntfTripsForIb.getAccountLv1());
		ittbTripsTxn.setAccountLv2(vwIntfTripsForIb.getAccountLv2());
		ittbTripsTxn.setAccountLv3(vwIntfTripsForIb.getAccountLv3());
		ittbTripsTxn
				.setBankBatchCloseNo(vwIntfTripsForIb.getBankBatchCloseNo());
		ittbTripsTxn.setBankTid(vwIntfTripsForIb.getBankTid());
		ittbTripsTxn.setBookedBy(vwIntfTripsForIb.getBookedBy());
		ittbTripsTxn.setBookedDateTime(vwIntfTripsForIb.getBookedDateTime());
		ittbTripsTxn.setBookedVehicleGroup(vwIntfTripsForIb
				.getBookedVehicleGroup());
		ittbTripsTxn
				.setBookingReference(vwIntfTripsForIb.getBookingReference());
		ittbTripsTxn.setCardNo(vwIntfTripsForIb.getCardNo());
		ittbTripsTxn.setComplimentary(vwIntfTripsForIb.getComplimentary());
		ittbTripsTxn.setCardType(vwIntfTripsForIb.getCardType());
		ittbTripsTxn.setDestination(vwIntfTripsForIb.getDestination());
		ittbTripsTxn.setDriverIc(vwIntfTripsForIb.getDriverIc());
		ittbTripsTxn.setDriverLevy(vwIntfTripsForIb.getDriverLevy());
		ittbTripsTxn.setIncentiveAmt(vwIntfTripsForIb.getIncentiveAmt());
		ittbTripsTxn.setPromoAmt(vwIntfTripsForIb.getPromoAmt());
		ittbTripsTxn.setCabRewardsAmt(vwIntfTripsForIb.getCabRewardsAmt());
		ittbTripsTxn.setEntity(vwIntfTripsForIb.getEntity());
		ittbTripsTxn.setJobNo(vwIntfTripsForIb.getJobNo());
		ittbTripsTxn.setJobStatus(vwIntfTripsForIb.getJobStatus());
		ittbTripsTxn.setJobType(vwIntfTripsForIb.getJobType());
		ittbTripsTxn.setPaxName(vwIntfTripsForIb.getPaxName());
		ittbTripsTxn.setPaymentMode(vwIntfTripsForIb.getPaymentMode());
		ittbTripsTxn.setPickup(vwIntfTripsForIb.getPickup());
		// IttbTripsTxn.setProduct(vwIntfTripsForIb.getProduct());
		// Should get productID instead as it is the code that we are mapping
		ittbTripsTxn.setProductId(vwIntfTripsForIb.getProductId());
		ittbTripsTxn.setSalesDraftNo(vwIntfTripsForIb.getSalesDraftNo());
		ittbTripsTxn.setSetlDate(vwIntfTripsForIb.getSetlDate());
		ittbTripsTxn.setSurchargeDescription(vwIntfTripsForIb
				.getSurchargeDescription());
		ittbTripsTxn.setTripStart(vwIntfTripsForIb.getTripStart());
		ittbTripsTxn.setTripEnd(vwIntfTripsForIb.getTripEnd());
		ittbTripsTxn.setTxnAmount(vwIntfTripsForIb.getTxnAmount());
		ittbTripsTxn.setTotalAmount(vwIntfTripsForIb.getTotalAmount());
		ittbTripsTxn.setVehicleNo(vwIntfTripsForIb.getVehicleNo());
		ittbTripsTxn.setAgentName(vwIntfTripsForIb.getAgentName());
		ittbTripsTxn.setFlightDetails(vwIntfTripsForIb.getFlightDetails());
		ittbTripsTxn.setPickupDt(vwIntfTripsForIb.getPickupDt());
		ittbTripsTxn.setHotelChargeTo(vwIntfTripsForIb.getHotelChargeTo());
		ittbTripsTxn.setVehType(vwIntfTripsForIb.getVehType());
		ittbTripsTxn.setMid(vwIntfTripsForIb.getMid());
		ittbTripsTxn.setBookingFee(vwIntfTripsForIb.getBookingFee());
		ittbTripsTxn.setAdminAmount(vwIntfTripsForIb.getAdminAmount());
		ittbTripsTxn.setGstAmount(vwIntfTripsForIb.getGstAmount());
		ittbTripsTxn.setOfflineFlag(vwIntfTripsForIb.getOfflineFlag());
		ittbTripsTxn.setOfflineTxnDate(vwIntfTripsForIb.getOfflineTxnDate());

		ittbTripsTxn.setDistance(vwIntfTripsForIb.getDistance());
		ittbTripsTxn.setVoucherAmount(vwIntfTripsForIb.getVoucherAmount());
		ittbTripsTxn.setDiscountAmount(vwIntfTripsForIb.getDiscountAmount());
		
		ittbTripsTxn.setTripCode(vwIntfTripsForIb.getTripCode());
		ittbTripsTxn.setTripDesc(vwIntfTripsForIb.getTripDesc());
		
		return ittbTripsTxn;
	}

	public String verifyFMSDriverVehicleAssoc(String taxiNo, String nric,
			Timestamp tripStart, Timestamp tripEnd) {
		// Call CNII Synchronise interface
		Map<String, String> prop = (Map) SpringUtil
				.getBean("webserviceProperties");
		String msg = null;
		if (NonConfigurableConstants.BOOLEAN_NO.equals((String) prop
				.get("ws.fms.disable"))) {
			// Not disabled - so send interface
			msg = IBSFMSDriverVehicleAssocClient
					.validateFMSDriverVehicleAssociation(taxiNo, nric,
							tripStart, tripEnd);
			return msg;
		} else {
			logger.info("FMS interface is disabled");
			return NonConfigurableConstants.IGNORE_FLAG;
		}

	}

	public List<IttbTripsTxnReq> getTripsReqs() {
		return this.daoHelper.getTxnDao().getTripsReqs();
	}

	public TmtbAcquireTxn getLatestTxnByJobNo(String jobNo) {
		return this.daoHelper.getTxnDao().getLatestTxnByJobNo(jobNo);
	}

	public List<VwIntfSetlForIb> searchSETLview(int records) {
		return this.daoHelper.getTxnDao().getSETLSview(records);
	}

	public int retrieveFromSETL(List<VwIntfSetlForIb> setlList, int[] stats)
			throws Exception {
		// Retrieve from request table

		List<BigDecimal> tripsSuccessArrayList = new ArrayList<BigDecimal>();
		List<IttbSetlTxn> tripsList = new ArrayList<IttbSetlTxn>();

		int totalNoOfRecords = 0;
		int totalNoOfCreditCardTrips 	= stats[2];
		int totalNoOfNetsTrips 			= stats[3];
		int totalNoOfNfpTrips 			= stats[4];
		int totalNoOfCupTrips 			= stats[5];
		int totalNoOfEpinTrips 			= stats[6];
		int totalNoOfEzlTrips 			= stats[7];
		int totalNoOfDashTrips 			= stats[8];
		int totalNoOfIgnored 			= stats[9];
		int totalNoOfKarhooTrips 		= stats[10];
		int totalNoOfLazadaTrips 		= stats[11];
		int totalNoOfNewNonBillableTxnTrips = stats[12];
		

		try {
			if (setlList != null) {
				logger.info("SETL List Size: " + setlList.size());
				Iterator<VwIntfSetlForIb> iter = setlList.iterator();

				while (iter.hasNext()) {
					String msg = "";
					VwIntfSetlForIb temp = iter.next();
					// To change to new interface value
					// Loop the request table
					String paymentMode = temp.getPaymentMode();
					MstbMasterTable aptMaster = ConfigurableConstants
							.getMasterTableByInterfaceMapping(ConfigurableConstants.ACQUIRER_PYMT_TYPE,
									paymentMode);
					
					//19/12/2021 - Trip date Enhance x days to ignore start
					boolean toIgnoreTrip = checkTripEnhanceXDay(temp.getTripStart());
					
					if(toIgnoreTrip)
					{
						totalNoOfIgnored++;
					}
					//19/12/2021 - Trip date enhance x days to ignore end
					else if ("CRCA".equals(paymentMode)) {
						IttbSetlTxn setlTxn = this.cloneSetl(temp);
						tripsList.add(setlTxn);
						totalNoOfCreditCardTrips++;
					} else if ("NETS".equals(paymentMode)) {
						IttbSetlTxn setlTxn = this.cloneSetl(temp);
						tripsList.add(setlTxn);
						totalNoOfNetsTrips++;
					} else if ("NFP".equals(paymentMode)) {
						IttbSetlTxn setlTxn = this.cloneSetl(temp);
						tripsList.add(setlTxn);
						totalNoOfNfpTrips++;
					} 
					else if ("CUP".equals(paymentMode)) {
						IttbSetlTxn setlTxn = this.cloneSetl(temp);
						tripsList.add(setlTxn);
						totalNoOfCupTrips++;
					}
					else if ("EPIN".equals(paymentMode)) {
						IttbSetlTxn setlTxn = this.cloneSetl(temp);
						tripsList.add(setlTxn);
						totalNoOfEpinTrips++;
					}
					else if ("EZL".equals(paymentMode)) {
						IttbSetlTxn setlTxn = this.cloneSetl(temp);
						tripsList.add(setlTxn);
						totalNoOfEzlTrips++;
					}
					else if ("DASH".equals(paymentMode)) {
						IttbSetlTxn setlTxn = this.cloneSetl(temp);
						tripsList.add(setlTxn);
						totalNoOfDashTrips++;
					}
					else if ("KARHOO".equals(paymentMode)) {
						IttbSetlTxn setlTxn = this.cloneSetl(temp);
						tripsList.add(setlTxn);
						totalNoOfKarhooTrips++;
					}
					else if ("LAZ".equals(paymentMode)) {
						IttbSetlTxn setlTxn = this.cloneSetl(temp);
						tripsList.add(setlTxn);
						totalNoOfLazadaTrips++;
					}
					else if(aptMaster != null)
					{
						if(aptMaster.getMasterStatus().trim().equalsIgnoreCase("A")) {
							IttbSetlTxn setlTxn = this.cloneSetl(temp);
							tripsList.add(setlTxn);
							totalNoOfNewNonBillableTxnTrips++;
						}
						else
							totalNoOfIgnored++;
					}
					else {
						// Not required to logged to error for other payment
						// mode
						totalNoOfIgnored++;
					}
					// Put to successlist - note: always success regardless if
					// IBS failed - unless it is a DB error
					tripsSuccessArrayList.add(temp.getSetlPk());
					totalNoOfRecords++;
				}

				// flush the records
				this.daoHelper.getGenericDao().saveAll(tripsList,
						NonConfigurableConstants.INTERFACE_USER);

				// Call the stored procedure to update the view
				// Iterate hashmap and save this object into db

				// Update success to TRIPS view
				//toDo: comment below for SIT
				if (tripsSuccessArrayList != null
						&& !tripsSuccessArrayList.isEmpty()) {
					BigDecimal[] bigDecimalArray = new BigDecimal[tripsSuccessArrayList
							.size()];
					this.daoHelper.getTxnDao().updateSucceededForSETL(
							tripsSuccessArrayList.toArray(bigDecimalArray));
				}
			}

			tripsSuccessArrayList.clear();

			stats[2] = totalNoOfCreditCardTrips;
			stats[3] = totalNoOfNetsTrips;
			stats[4] = totalNoOfNfpTrips;
			stats[5] = totalNoOfCupTrips;
			stats[6] = totalNoOfEpinTrips;
			stats[7] = totalNoOfEzlTrips;
			stats[8] = totalNoOfDashTrips;
			stats[9] = totalNoOfIgnored;
			stats[10] = totalNoOfKarhooTrips;
			stats[11] = totalNoOfLazadaTrips;
			stats[12] = totalNoOfNewNonBillableTxnTrips;
		} catch (Exception e) {
			throw new Exception(e);
		}

		return totalNoOfRecords;
	}

	private IttbSetlTxn cloneSetl(VwIntfSetlForIb vwIntfSetlForIb) {
		IttbSetlTxn setlTxn = new IttbSetlTxn();

		setlTxn.setSetlPk(vwIntfSetlForIb.getSetlPk());
		setlTxn.setJobNo(vwIntfSetlForIb.getJobNo());
		setlTxn.setVehicleNo(vwIntfSetlForIb.getVehicleNo());
		setlTxn.setDriverIc(vwIntfSetlForIb.getDriverIc());
		setlTxn.setEntity(vwIntfSetlForIb.getEntity());
		setlTxn.setTxnDate(vwIntfSetlForIb.getTxnDate());
		setlTxn.setPaymentMode(vwIntfSetlForIb.getPaymentMode());
		setlTxn.setCardNo(vwIntfSetlForIb.getCardNo());
		setlTxn.setApprovalCode(vwIntfSetlForIb.getApprovalCode());
		setlTxn.setTxnAmount(vwIntfSetlForIb.getTxnAmount());
		setlTxn.setAdminAmount(vwIntfSetlForIb.getAdminAmount());
		setlTxn.setGstAmount(vwIntfSetlForIb.getGstAmount());
		setlTxn.setFileId(vwIntfSetlForIb.getFileId());
		setlTxn.setCreatedDt(vwIntfSetlForIb.getCreatedDt());
		setlTxn.setJobStatus(vwIntfSetlForIb.getJobStatus());
		setlTxn.setReconFlag(vwIntfSetlForIb.getReconFlag());
		setlTxn.setReconDt(vwIntfSetlForIb.getReconDt());
		setlTxn.setFareAmount(vwIntfSetlForIb.getFareAmount());
		setlTxn.setTid(vwIntfSetlForIb.getTid());
		setlTxn.setMid(vwIntfSetlForIb.getMid());
		setlTxn.setStan(vwIntfSetlForIb.getStan());
		setlTxn.setTripStart(vwIntfSetlForIb.getTripStart());
		setlTxn.setTripEnd(vwIntfSetlForIb.getTripEnd());
		setlTxn.setPickup(vwIntfSetlForIb.getPickup());
		setlTxn.setDestination(vwIntfSetlForIb.getDestination());
		setlTxn.setSalesDraftNo(vwIntfSetlForIb.getSalesDraftNo());
		setlTxn.setErrorCode(vwIntfSetlForIb.getErrorCode());
		setlTxn.setFlowthruAction(vwIntfSetlForIb.getFlowthruAction());
		setlTxn.setFlowthruBy(vwIntfSetlForIb.getFlowthruBy());
		setlTxn.setFlowthruDt(vwIntfSetlForIb.getFlowthruDt());
		setlTxn.setFlowthruComments(vwIntfSetlForIb.getFlowthruComments());
		setlTxn.setFlowthruTripIntfPk(vwIntfSetlForIb.getFlowthruTripIntfPk());
		setlTxn.setSetlRef(vwIntfSetlForIb.getSetlRef());
		setlTxn.setSetlDate(vwIntfSetlForIb.getSetlDate());
		setlTxn.setBankTid(vwIntfSetlForIb.getBankTid());
		setlTxn.setBankBatchCloseNo(vwIntfSetlForIb.getBankBatchCloseNo());
		setlTxn.setCardType(vwIntfSetlForIb.getCardType());
		setlTxn.setOfflineFlag(vwIntfSetlForIb.getOfflineFlag());
		setlTxn.setOfflineTxnDate(vwIntfSetlForIb.getOfflineTxnDate());
		setlTxn.setPspRefNo1(vwIntfSetlForIb.getPspRefNo1());
		setlTxn.setPspRefNo2(vwIntfSetlForIb.getPspRefNo2());
		setlTxn.setTxnAmount1(vwIntfSetlForIb.getTxnAmount1());
		setlTxn.setTxnAmount2(vwIntfSetlForIb.getTxnAmount2());
		setlTxn.setPolicyNumber(vwIntfSetlForIb.getPolicyNumber());
		setlTxn.setStartOfInsurance(vwIntfSetlForIb.getStartOfInsurance());
		setlTxn.setEndOfInsurance(vwIntfSetlForIb.getEndOfInsurance());

		
		if(vwIntfSetlForIb.getPremiumAmount() != null)
		{
			try {
				BigDecimal premiumAmount = new BigDecimal(vwIntfSetlForIb.getPremiumAmount());
				setlTxn.setPremiumAmount(premiumAmount);
//				setlTxn.setTxnAmount(setlTxn.getTxnAmount().add(premiumAmount));
			}
			catch(Exception e)
			{
				logger.info("Premium Amount error, setting it to 0. "+e.getMessage());
				setlTxn.setPremiumAmount(new BigDecimal("0"));
			}
		}
		if(vwIntfSetlForIb.getPremiumGst() != null)
		{
			try {
				BigDecimal premiumGst = new BigDecimal(vwIntfSetlForIb.getPremiumGst());
				setlTxn.setPremiumGst(premiumGst);
//				setlTxn.setTxnAmount(setlTxn.getTxnAmount().add(premiumGst));
			}
			catch(Exception e)
			{
				logger.info("Premium GST error, setting it to 0. "+e.getMessage());
				setlTxn.setPremiumGst(new BigDecimal("0"));
			}
		}
		if(setlTxn.getPaymentMode() != null)
		{
			//NOF = Nets - Nets Click ,,  DBSPOF = DbsPaylah (on File) 
			if(setlTxn.getPaymentMode().equalsIgnoreCase("NOF") || setlTxn.getPaymentMode().equalsIgnoreCase("DBSPOF"))
			{
				if(setlTxn.getPolicyNumber() != null && !setlTxn.getPolicyNumber().trim().equals(""))
				{
					if(setlTxn.getPremiumAmount() != null && setlTxn.getPremiumGst() != null)
					{
						setlTxn.setFareAmount(setlTxn.getFareAmount().subtract(setlTxn.getPremiumAmount()));
						setlTxn.setFareAmount(setlTxn.getFareAmount().subtract(setlTxn.getPremiumGst()));
					}
				}
			}
		}
		if(vwIntfSetlForIb.getPolicyStatus() != null)
		{
			if(vwIntfSetlForIb.getPolicyStatus().equalsIgnoreCase("Conformed") || vwIntfSetlForIb.getPolicyStatus().equalsIgnoreCase("Confirmed"))
				setlTxn.setPolicyStatus(NonConfigurableConstants.HLA_POLICY_STATUS_ACTIVE); 
			else if(vwIntfSetlForIb.getPolicyStatus().equalsIgnoreCase("CANCELLED"))
				setlTxn.setPolicyStatus(NonConfigurableConstants.HLA_POLICY_STATUS_CANCELLED);
		}
		setlTxn.setProductId(vwIntfSetlForIb.getProductId());
		String productId = vwIntfSetlForIb.getProductId();
		// Retrieve trip type
		if (productId != null && !"".equals(productId)) {
			MstbMasterTable tripTypeMaster = ConfigurableConstants
					.getMasterTableByInterfaceMapping(ConfigurableConstants.VEHICLE_TRIP_TYPE,
							productId);
			
			if(tripTypeMaster != null)
			{
				if(tripTypeMaster.getInterfaceMappingValue() != null)
				{
					if(tripTypeMaster.getMasterStatus().trim().equalsIgnoreCase("A"))
					{
						if(tripTypeMaster.getInterfaceMappingValue().length() >= 4)
						{
								if(tripTypeMaster.getInterfaceMappingValue().substring(0, 4).equalsIgnoreCase("FLAT"))	
								{
									logger.info("Non Billable Trip Type is Flat Fare, setting GST & Admin to 0");
									setlTxn.setGstAmount(ZERO);
									setlTxn.setAdminAmount(ZERO);
								}
						}
					}
					else
						logger.info("Non Billable Trip Type Not Active for : " +setlTxn.getProductId() + " job : "+setlTxn.getJobNo());
//						throw new ProcessTripException(NonConfigurableConstants.TRIP_TYPE_NOT_ACTIVE, productId);	
				}
				else
					logger.info("Non Billable Trip type No Interface Mapping Value found for : "+setlTxn.getProductId()+ " job : "+setlTxn.getJobNo());
//					throw new ProcessTripException("Trip Type No Interface Mapping Value", product);	
			} 
			else
				logger.info("Non Billable Trip Type Not Found in system for : " +setlTxn.getProductId() + " job : "+setlTxn.getJobNo());
//				throw new ProcessTripException(NonConfigurableConstants.TRIP_TYPE_NOT_FOUND, product);	
		} 
		else
			logger.info("Non Billable Trip Type Not found in download for : " +setlTxn.getProductId() + " job : "+setlTxn.getJobNo());
//			throw new ProcessTripException(NonConfigurableConstants.MANDATORY_TRIP_TYPE);
		
		
		return setlTxn;
	}

	public boolean hasActiveOrBilledTripByJobNo(String jobNo) {
		return this.daoHelper.getTxnDao().hasActiveOrBilledTripByJobNo(jobNo);
	}

	public String getNote(int tmtbAcquireTxnNo) {
		BmtbNote bmtbNote = this.daoHelper.getNoteDao().getNoteByAcquireTxn(
				(tmtbAcquireTxnNo));
		if (bmtbNote != null) {
			return bmtbNote.getNoteNo().toString();
		} else {
			return null;
		}
	}

	public List<TmtbTxnReviewReqFlow> getTxnReqFlows(String txnReqID) {
		return this.daoHelper.getTxnDao().getTxnReqFlows(txnReqID);
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
	
	public List<Object[]> getPreviousApproval(String jobNo) {
		return this.daoHelper.getTxnDao().getPreviousApproval(jobNo);
	}
	public int checkReplaceCard(String productCardNo, BigDecimal returnCreditBalanceAmount, String addSubtract, String user) throws Exception {
		//Take currentCardNo & find NewCardNo's Prod & Update, then loop it to check newcardno got replace or not	

			logger.info("Checking CardNo > "+productCardNo+" if replaced before");
			List<PmtbProductReplacement> replacedProductsList = this.daoHelper.getProductDao().getReplacedCardProducts(productCardNo);
			
			//if got future card, update the limit also
			if(replacedProductsList.size() > 0) {
				logger.info("Records Found, checking accountNo, cardNo and ProductNo if match..");
				for(PmtbProductReplacement replace : replacedProductsList)
				{
					PmtbProduct currProd = replace.getPmtbProduct();
					PmtbProduct newCardProd = this.daoHelper.getProductDao().getNewCardProduct(replace.getNewCardNo(), currProd.getAmtbAccount().getAccountNo(), replace.getNewProductNo());
					if(null != newCardProd) //If got record, update credit balance.
					{
						//replace.newCardNo & currProduct.AccountNo & newProdNo must be same for the new Product.
						if(replace.getNewProductNo().equals(newCardProd.getProductNo())) //newProdNo @ pmtbReplace must be same as currProd ProductNo.
						{
							if(addSubtract.trim().equalsIgnoreCase("add"))
								newCardProd.setCreditBalance(newCardProd.getCreditBalance().add(returnCreditBalanceAmount));
							else if(addSubtract.trim().equalsIgnoreCase("subtract"))
								newCardProd.setCreditBalance(newCardProd.getCreditBalance().subtract(returnCreditBalanceAmount));
					
							this.update(newCardProd, user + " (ReplaceTxn)");
							logger.info("Updating Credit Limit for Card No > "+newCardProd.getCardNo());
							
							// Still we need to update payment to update the amount used in AS
							// to call API.formulateAccountId
							if(null != user && !user.trim().equalsIgnoreCase(""))
								this.updateProductCreditLimitForUI(newCardProd, user +" (ReplaceTxn)");
							else
								this.updateProductCreditLimit(newCardProd, returnCreditBalanceAmount, NonConfigurableConstants.INTERFACE_USER  +" (ReplaceTxn)");
							
							logger.info("Updating Credit Limit for Product AS");
							
							return checkReplaceCard(newCardProd.getCardNo(), returnCreditBalanceAmount, addSubtract, user);
						}
					}
				}
			}
			return 1;
		}
	
	public void createBatchTripUpload(Map<String, String> txnDetails, String user, String cardlessYesNo ) throws Exception 
	{	
		if(cardlessYesNo.trim().equalsIgnoreCase("N"))
			createTxn(txnDetails, user);
		else if(cardlessYesNo.trim().equalsIgnoreCase("Y"))
			createPremierTxn(txnDetails, user);
			
	}
	
	public boolean checkJobNo(String jobNo) {
		return this.daoHelper.getTxnDao().checkJobNo(jobNo);
	}
	

	//19/12/2021 - Trip date Enhance x Days
	public boolean checkTripEnhanceXDay(Date tripStartDate)
	{
		boolean toIgnore = false;
		
		if(tripStartDate != null)
		{
			MstbMasterTable master = ConfigurableConstants
					.getMasterTable(ConfigurableConstants.DOWNLOAD_TRIP_X_DAY,
							"CONT");
			Integer configXday = Integer.parseInt(master.getMasterValue().trim());
			java.sql.Date configDay = DateUtil.convertUtilDateToSqlDate(DateUtil.convertStrToDate(
					DateUtil.getYearMonthDay(), "yyyyMMdd"));
			
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(configDay);
			calendar.add(Calendar.DATE, (-1 * configXday));
			
			if(tripStartDate.compareTo(calendar.getTime()) == -1)
				toIgnore = true;
		}
		return toIgnore;
	}
}