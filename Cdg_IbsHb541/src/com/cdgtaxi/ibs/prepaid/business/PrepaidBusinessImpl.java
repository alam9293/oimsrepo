package com.cdgtaxi.ibs.prepaid.business;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.StringReader;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.Security;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonValue;
import javax.net.ssl.HttpsURLConnection;

import org.apache.commons.codec.binary.Base64;
import org.apache.log4j.Logger;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.springframework.orm.hibernate5.HibernateOptimisticLockingFailureException;
import org.zkforge.json.simple.JSONArray;
import org.zkforge.json.simple.JSONObject;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zkplus.spring.SpringUtil;

import com.cdgtaxi.ibs.Uri;
import com.cdgtaxi.ibs.acl.model.SatbUser;
import com.cdgtaxi.ibs.common.ConfigurableConstants;
import com.cdgtaxi.ibs.common.MasterSetup;
import com.cdgtaxi.ibs.common.NonConfigurableConstants;
import com.cdgtaxi.ibs.common.business.GenericBusinessImpl;
import com.cdgtaxi.ibs.common.dao.Sequence;
import com.cdgtaxi.ibs.common.model.AmtbAccount;
import com.cdgtaxi.ibs.common.model.AmtbAcctMainContact;
import com.cdgtaxi.ibs.common.model.AmtbContactPerson;
import com.cdgtaxi.ibs.common.model.BmtbDraftInvDetail;
import com.cdgtaxi.ibs.common.model.BmtbDraftInvHeader;
import com.cdgtaxi.ibs.common.model.BmtbDraftInvSummary;
import com.cdgtaxi.ibs.common.model.BmtbInvoiceDetail;
import com.cdgtaxi.ibs.common.model.BmtbInvoiceHeader;
import com.cdgtaxi.ibs.common.model.BmtbInvoiceSummary;
import com.cdgtaxi.ibs.common.model.BmtbIssuanceInvoiceTxn;
import com.cdgtaxi.ibs.common.model.BmtbNote;
import com.cdgtaxi.ibs.common.model.BmtbNoteFlow;
import com.cdgtaxi.ibs.common.model.BmtbPaymentReceipt;
import com.cdgtaxi.ibs.common.model.BmtbPaymentReceiptDetail;
import com.cdgtaxi.ibs.common.model.BmtbTopUpInvoiceTxn;
import com.cdgtaxi.ibs.common.model.FmtbEntityMaster;
import com.cdgtaxi.ibs.common.model.FmtbTransactionCode;
import com.cdgtaxi.ibs.common.model.PmtbAdjustmentReq;
import com.cdgtaxi.ibs.common.model.PmtbCardNoSequence;
import com.cdgtaxi.ibs.common.model.PmtbExtBalExpDateReq;
import com.cdgtaxi.ibs.common.model.PmtbIssuanceReq;
import com.cdgtaxi.ibs.common.model.PmtbIssuanceReqCard;
import com.cdgtaxi.ibs.common.model.PmtbPrepaidCardTxn;
import com.cdgtaxi.ibs.common.model.PmtbPrepaidReq;
import com.cdgtaxi.ibs.common.model.PmtbProduct;
import com.cdgtaxi.ibs.common.model.PmtbProductType;
import com.cdgtaxi.ibs.common.model.PmtbTopUpReq;
import com.cdgtaxi.ibs.common.model.PmtbTopUpReqCard;
import com.cdgtaxi.ibs.common.model.PmtbTransferReq;
import com.cdgtaxi.ibs.common.model.forms.ApplyAmtInfo;
import com.cdgtaxi.ibs.common.model.forms.GstInfo;
import com.cdgtaxi.ibs.common.model.forms.SearchPrepaidProductForm;
import com.cdgtaxi.ibs.common.model.forms.SearchPrepaidRequestForm;
import com.cdgtaxi.ibs.common.model.forms.TransferableAmtInfo;
import com.cdgtaxi.ibs.common.ui.CommonWindow;
import com.cdgtaxi.ibs.interfaces.as.api.API;
import com.cdgtaxi.ibs.interfaces.as.model.AsvwProduct;
import com.cdgtaxi.ibs.interfaces.cdge.exception.ExpectedException;
import com.cdgtaxi.ibs.master.model.MstbCreditTermMaster;
import com.cdgtaxi.ibs.master.model.MstbPromotionCashPlus;
import com.cdgtaxi.ibs.util.AccountUtil;
import com.cdgtaxi.ibs.util.CardNoGenerator;
import com.cdgtaxi.ibs.util.DateUtil;
import com.cdgtaxi.ibs.util.EmailUtil;
import com.cdgtaxi.ibs.util.GstUtil;
import com.cdgtaxi.ibs.util.LoggerUtil;
import com.cdgtaxi.ibs.util.PrepaidUtil;
import com.cdgtaxi.ibs.util.ProductUtil;
import com.cdgtaxi.tls2.TSLSocketConnectionFactory;
import com.elixirtech.net.NetException;
import com.google.common.base.Joiner;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Lists;
import com.google.common.collect.Multimap;
import com.google.common.collect.Sets;

import sun.misc.BASE64Encoder;

public class PrepaidBusinessImpl extends GenericBusinessImpl implements PrepaidBusiness {

	private static final Logger logger = Logger.getLogger(PrepaidBusinessImpl.class);

	public void createIssuanceRequest(PmtbIssuanceReq request) throws NetException, IOException {

		request.setRequestType(NonConfigurableConstants.PREPAID_REQUEST_TYPE_ISSUANCE);
		request.setRequestDate(new Date());
		SatbUser requestor = this.daoHelper.getUserDao().getUserWithRoles(CommonWindow.getUserId());
		request.setRequestBy(requestor);
		List<PmtbIssuanceReqCard> cardList = request.getHolderCardList();

		boolean containCashplus = false;
		for (PmtbIssuanceReqCard card : cardList) {

			BigDecimal cashplus = card.getCashplus();

			if (cashplus != null && cashplus.doubleValue() > 0) {
				containCashplus = true;
				break;
			}
		}

		logger.info("issuance contain cashback? " + containCashplus);
		request.setApprovalRequired(NonConfigurableConstants.getBooleanFlag(containCashplus));

		for (PmtbIssuanceReqCard card : cardList) {
			// to let the request card no generated via sequence
			card.setReqCardNo(null);
			card.setPmtbIssuanceReq(request);
		}

		if (NonConfigurableConstants.getBoolean(request.getApprovalRequired())) {
			request.setApprovalStatus(NonConfigurableConstants.PREPAID_APPROVAL_STATUS_PENDING);
		}

		this.save(request);
		this.saveAll(cardList);

		// the card list have to manually add into request cards after saving
		// if we set before savings, since the request card no is null, at the time
		// conversion from list to set,
		// only one record will be contains the set
		request.setPmtbIssuanceReqCards(Sets.newHashSet(cardList));

		// if there is approval required
		if (NonConfigurableConstants.getBoolean(request.getApprovalRequired())) {
			request.setStatus(NonConfigurableConstants.PREPAID_REQUEST_STATUS_PENDING_APPROVAL);
			super.update(request);

			sendRequestEmail(request, ConfigurableConstants.EMAIL_TYPE_PREPAID_ISSUE_REQUEST_SUBMIT);

		}
		// else automatically 'approve' the issuance request
		else {
			approveIssuanceRequest(request, CommonWindow.getUserLoginIdAndDomain());
		}

	}

	public void createTopUpRequest(PmtbTopUpReq request) throws ExpectedException {

		request.setRequestType(NonConfigurableConstants.PREPAID_REQUEST_TYPE_TOP_UP);

		request.setRequestDate(new Date());
		SatbUser requestor = this.daoHelper.getUserDao().getUserWithRoles(CommonWindow.getUserId());
		request.setRequestBy(requestor);

		boolean containCashplus = false;

		List<PmtbTopUpReqCard> cardList = request.getHolderCardList();

		for (PmtbTopUpReqCard card : cardList) {

			BigDecimal cashplus = card.getTopUpCashplus();
			if (cashplus != null && cashplus.doubleValue() > 0) {
				containCashplus = true;
				break;
			}
		}
		logger.info("top up contain cashback? " + containCashplus);
		// if there is no any cash back, approval from other user is not required.

		request.setApprovalRequired(NonConfigurableConstants.getBooleanFlag(containCashplus));

		if (NonConfigurableConstants.getBoolean(request.getApprovalRequired())) {
			request.setApprovalStatus(NonConfigurableConstants.PREPAID_APPROVAL_STATUS_PENDING);
		}

		for (PmtbTopUpReqCard card : cardList) {
			// to let the request card no generated via sequence
			card.setReqCardNo(null);
			card.setPmtbTopUpReq(request);
		}

		this.save(request);
		this.saveAll(cardList);

		request.setPmtbTopUpReqCards(Sets.newHashSet(cardList));

		// if there is approval required
		if (NonConfigurableConstants.getBoolean(request.getApprovalRequired())) {
			request.setStatus(NonConfigurableConstants.PREPAID_REQUEST_STATUS_PENDING_APPROVAL);
			super.update(request);

			sendRequestEmail(request, ConfigurableConstants.EMAIL_TYPE_PREPAID_TOPUP_REQUEST_SUBMIT);

		}
		// else automatically 'approve' the issuance request
		else {
			approveTopUpRequest(request, CommonWindow.getUserLoginIdAndDomain());
		}
	}

	public static String encrypt(String str) {
		Random rand = new Random((new Date()).getTime());
		BASE64Encoder encoder = new BASE64Encoder();

		byte[] salt = new byte[8];

		rand.nextBytes(salt);

		return encoder.encode(salt) + encoder.encode(str.getBytes());
	}

	// duplicate of createtopuprequest
	public void createTopUpCreditRequest(PmtbTopUpReq request, String link) throws ExpectedException {

		request.setRequestType(NonConfigurableConstants.PREPAID_REQUEST_TYPE_TOP_UP);
		request.setUri(link);
		request.setFailureUri((request.getUri().replace("redirect", "redirectFail")));
		request.setRequestDate(new Date());
		SatbUser requestor = this.daoHelper.getUserDao().getUserWithRoles(CommonWindow.getUserId());
		request.setRequestBy(requestor);

		boolean containCashplus = false;

		List<PmtbTopUpReqCard> cardList = request.getHolderCardList();

		for (PmtbTopUpReqCard card : cardList) {

			BigDecimal cashplus = card.getTopUpCashplus();
			if (cashplus != null && cashplus.doubleValue() > 0) {
				containCashplus = true;
				break;
			}
		}
		logger.info("top up contain cashback? " + containCashplus);
		// if there is no any cash back, approval from other user is not required.

		request.setApprovalRequired(NonConfigurableConstants.getBooleanFlag(false));

		if (NonConfigurableConstants.getBoolean(request.getApprovalRequired())) {
			request.setApprovalStatus(NonConfigurableConstants.PREPAID_APPROVAL_STATUS_PENDING);
		}

		for (PmtbTopUpReqCard card : cardList) {
			// to let the request card no generated via sequence
			card.setReqCardNo(null);
			card.setPmtbTopUpReq(request);
		}

		this.save(request);
		this.saveAll(cardList);

		request.setPmtbTopUpReqCards(Sets.newHashSet(cardList));

		// if there is approval required
		if (NonConfigurableConstants.getBoolean(request.getApprovalRequired())) {
			request.setStatus(NonConfigurableConstants.PREPAID_REQUEST_STATUS_PENDING_APPROVAL);
			super.update(request);

			sendRequestEmail(request, ConfigurableConstants.EMAIL_TYPE_PREPAID_TOPUP_REQUEST_SUBMIT);

		}
		// else automatically 'approve' the issuance request
		else {
			approveTopUpRequest(request, CommonWindow.getUserLoginIdAndDomain());
		}
	}

	// duplicate of createtopuprequest
	public void recreateTopUpCreditRequest(PmtbTopUpReq request, String link) throws ExpectedException {

		request.setRequestType(NonConfigurableConstants.PREPAID_REQUEST_TYPE_TOP_UP);
		request.setUri(link);
		request.setFailureUri((request.getUri().replace("redirect", "redirectFail")));
		request.setRequestDate(new Date());
		SatbUser requestor = this.daoHelper.getUserDao().getUserWithRoles(CommonWindow.getUserId());
		request.setRequestBy(requestor);

		boolean containCashplus = false;

//			List<PmtbTopUpReqCard> cardList = request.getHolderCardList();
//
//			for (PmtbTopUpReqCard card : cardList) {
//
//				BigDecimal cashplus = card.getTopUpCashplus();
//				if (cashplus != null && cashplus.doubleValue() > 0) {
//					containCashplus = true;
//					break;
//				}
//			}
		logger.info("top up contain cashback? " + containCashplus);
		// if there is no any cash back, approval from other user is not required.

		request.setApprovalRequired(NonConfigurableConstants.getBooleanFlag(false));

		if (NonConfigurableConstants.getBoolean(request.getApprovalRequired())) {
			request.setApprovalStatus(NonConfigurableConstants.PREPAID_APPROVAL_STATUS_PENDING);
		}

//			for (PmtbTopUpReqCard card : cardList) {
//				// to let the request card no generated via sequence
//				card.setReqCardNo(null);
//				card.setPmtbTopUpReq(request);
//			}

		this.update(request);
//			this.saveAll(cardList);

//			request.setPmtbTopUpReqCards(Sets.newHashSet(cardList));

		// if there is approval required
//			if (NonConfigurableConstants.getBoolean(request.getApprovalRequired())) {
//				request.setStatus(NonConfigurableConstants.PREPAID_REQUEST_STATUS_PENDING_APPROVAL);
//				super.update(request);
//
//				sendRequestEmail(request, ConfigurableConstants.EMAIL_TYPE_PREPAID_TOPUP_REQUEST_SUBMIT);
//
//			}
		// this is for resubmit , since the hiraechy is alreayd created , we do not need
		// to go in again
		// if(request.getBmtbInvoiceHeader()==null) {
		approveTopUpCreditRequest(request, "AUTO APPROVED");
		// }
	}

	public void createTransferBalanceRequest(PmtbTransferReq request) {

		request.setRequestType(NonConfigurableConstants.PREPAID_REQUEST_TYPE_TRANSFER);
		request.setRequestDate(new Date());
		SatbUser requestor = this.daoHelper.getUserDao().getUserWithRoles(CommonWindow.getUserId());
		request.setRequestBy(requestor);
		request.setApprovalRequired(NonConfigurableConstants.BOOLEAN_YN_YES);
		request.setApprovalStatus(NonConfigurableConstants.PREPAID_APPROVAL_STATUS_PENDING);
		request.setStatus(NonConfigurableConstants.PREPAID_REQUEST_STATUS_PENDING_APPROVAL);
		this.save(request);

		sendRequestEmail(request, ConfigurableConstants.EMAIL_TYPE_PREPAID_TRANSFER_REQUEST_SUBMIT);

	}

	public void createExtendBalanceExpiryDateRequest(PmtbExtBalExpDateReq request) {

		request.setRequestType(NonConfigurableConstants.PREPAID_REQUEST_TYPE_EXTEND_BALANCE_EXPIRY_DATE);
		request.setRequestDate(new Date());
		SatbUser requestor = this.daoHelper.getUserDao().getUserWithRoles(CommonWindow.getUserId());
		request.setRequestBy(requestor);
		request.setApprovalRequired(NonConfigurableConstants.BOOLEAN_YN_YES);
		request.setApprovalStatus(NonConfigurableConstants.PREPAID_APPROVAL_STATUS_PENDING);
		request.setStatus(NonConfigurableConstants.PREPAID_REQUEST_STATUS_PENDING_APPROVAL);
		this.save(request);

		sendRequestEmail(request, ConfigurableConstants.EMAIL_TYPE_PREPAID_EXTEND_REQUEST_SUBMIT);

	}

	public void createAdjustmentRequest(PmtbAdjustmentReq req) {

		BigDecimal adjustValue = req.getAdjustValueAmount();
		BigDecimal adjustCashplus = req.getAdjustCashplusAmount();

		BigDecimal adjustValueGst = req.getAdjustValueGst();
		BigDecimal adjustCashplusGst = req.getAdjustCashplusGst();

		BigDecimal adjustValueWithGst = adjustValue.add(adjustValueGst);
		BigDecimal adjustCashplusWithGst = adjustCashplus.add(adjustCashplusGst);

		PmtbProduct product = req.getPmtbProduct();
		BigDecimal oriCardValue = product.getCardValue();
		BigDecimal oriCashplus = product.getCashplus();

		logger.info("current card value: " + oriCardValue);
		logger.info("current cashplus: " + oriCashplus);
		logger.info("adjust value with gst: " + adjustValueWithGst);
		logger.info("adjust cashplus with gst: " + adjustCashplusWithGst);

		validateAdjustable(oriCardValue, adjustValueWithGst, oriCashplus, adjustCashplusWithGst);

		req.setRequestType(NonConfigurableConstants.PREPAID_REQUEST_TYPE_ADJUSTEMENT);
		req.setRequestDate(new Date());
		SatbUser requestor = this.daoHelper.getUserDao().getUserWithRoles(CommonWindow.getUserId());
		req.setRequestBy(requestor);
		req.setApprovalRequired(NonConfigurableConstants.BOOLEAN_YN_YES);
		req.setApprovalStatus(NonConfigurableConstants.PREPAID_APPROVAL_STATUS_PENDING);
		req.setStatus(NonConfigurableConstants.PREPAID_REQUEST_STATUS_PENDING_APPROVAL);
		this.save(req);

		sendRequestEmail(req, ConfigurableConstants.EMAIL_TYPE_PREPAID_ADJUST_REQUEST_SUBMIT);

	}

	private void sendRequestEmail(PmtbPrepaidReq req, String emailType) {

		if (NonConfigurableConstants.getBoolean(req.getApprovalRequired())) {

			AmtbAccount acct = req.getAmtbAccount();
			acct = this.daoHelper.getAccountDao().getAccountWithParent(String.valueOf(acct.getAccountNo()));

			AmtbAccount topAcct = AccountUtil.getTopLevelAccount(acct);

			List<String> toEmails = new ArrayList<String>();
			List<String> ccEmails = new ArrayList<String>();

			List<String> approverNameList = Lists.newArrayList();
			List<SatbUser> approvers = this.daoHelper.getUserDao().searchUser(Uri.APPROVAL_PREPAID_REQ);
			for (SatbUser approver : approvers) {
				toEmails.add(approver.getEmail());
				approverNameList.add(approver.getName());

			}
			SatbUser requestor = req.getRequestBy();
			// ccEmails.add(requestor.getEmail());

			String approverName = Joiner.on(", ").join(approverNameList);

			EmailUtil.sendEmail(toEmails.toArray(new String[] {}), ccEmails.toArray(new String[] {}),
					ConfigurableConstants.getEmailText(emailType, ConfigurableConstants.EMAIL_SUBJECT),
					ConfigurableConstants.getEmailText(emailType, ConfigurableConstants.EMAIL_CONTENT)
							.replaceAll("#custNo#", topAcct.getCustNo())
							.replaceAll("#acctName#", topAcct.getAccountName())
							.replaceAll("#requestNo#", String.valueOf(req.getReqNo()))
							.replaceAll("#submitterUserName#", requestor.getName())
							.replaceAll("#approverUserName#", approverName));
		}

	}

	private void sendApprovalEmail(PmtbPrepaidReq req, String emailType) {

		if (NonConfigurableConstants.getBoolean(req.getApprovalRequired())) {

			AmtbAccount acct = req.getAmtbAccount();
			acct = this.daoHelper.getAccountDao().getAccountWithParent(String.valueOf(acct.getAccountNo()));
			AmtbAccount topAcct = AccountUtil.getTopLevelAccount(acct);

			List<String> toEmails = new ArrayList<String>();
			List<String> ccEmails = new ArrayList<String>();
			List<SatbUser> approvers = this.daoHelper.getUserDao().searchUser(Uri.APPROVAL_PREPAID_REQ);
//			for(SatbUser approver : approvers){
//				ccEmails.add(approver.getEmail());
//
//			}
			SatbUser requestor = req.getRequestBy();
			toEmails.add(requestor.getEmail());

			EmailUtil.sendEmail(toEmails.toArray(new String[] {}), ccEmails.toArray(new String[] {}),
					ConfigurableConstants.getEmailText(emailType, ConfigurableConstants.EMAIL_SUBJECT),
					ConfigurableConstants.getEmailText(emailType, ConfigurableConstants.EMAIL_CONTENT)
							.replaceAll("#custNo#", topAcct.getCustNo())
							.replaceAll("#acctName#", topAcct.getAccountName())
							.replaceAll("#requestNo#", String.valueOf(req.getReqNo()))
							.replaceAll("#submitterUserName#", requestor.getName()));
		}

	}

	public void generatePrepaidInvoiceFileForEligibleRequest(PmtbPrepaidReq req) throws NetException, IOException {

		String approvalRequired = req.getApprovalRequired();
		BmtbInvoiceHeader header = req.getBmtbInvoiceHeader();

		if ((NonConfigurableConstants.getBoolean(approvalRequired)
				&& NonConfigurableConstants.PREPAID_APPROVAL_STATUS_APPROVED.equals(req.getApprovalStatus()))
				|| !NonConfigurableConstants.getBoolean(approvalRequired)) {

			byte[] bytes = this.businessHelper.getReportBusiness().generatePrepaidInvoice(
					header.getAmtbAccountByAccountNo(), header.getInvoiceHeaderNo().toString(), false);
			this.businessHelper.getInvoiceBusiness().updateInvoiceHeaderFile(bytes, header);
		} else {
			logger.debug("Request " + req.getReqNo() + " not yet ready to generate invoice file...");
		}
	}

	public void approveIssuanceRequest(PmtbIssuanceReq req, String user) throws NetException, IOException {

		validateRequestBeenApproved(req);

		// create the issuance invoice
		createIssuanceInvoice(req, user, false);

		// if the credit term is COD, product card will only be created upon full
		// payment
		// else the card will be created immediately before payment receipt
		if (req.getMstbCreditTermMaster() == null) {
			req.setStatus(NonConfigurableConstants.PREPAID_REQUEST_STATUS_PENDING_PAYMENT);
		} else {
			issueProduct(req, false, user);
		}

		// if it is real approve
		if (NonConfigurableConstants.getBoolean(req.getApprovalRequired())) {
			SatbUser approver = this.daoHelper.getUserDao().getUserWithRoles(CommonWindow.getUserId());
			req.setApprovalBy(approver);
			req.setApprovalDate(new Date());
			req.setApprovalStatus(NonConfigurableConstants.PREPAID_APPROVAL_STATUS_APPROVED);
		}

		req.setProcessDate(new Date());
		super.update(req, user);

		sendApprovalEmail(req, ConfigurableConstants.EMAIL_TYPE_PREPAID_ISSUE_REQUEST_APPROVED);

	}

	public void approveTopUpRequest(PmtbTopUpReq req, String user) throws ExpectedException {

		validateRequestBeenApproved(req);

		// validate is the top up product has been suspended or terminated
		Set<PmtbTopUpReqCard> pmtbTopUpReqCards = req.getPmtbTopUpReqCards();
		Set<String> failedCards = Sets.newHashSet();
		for (PmtbTopUpReqCard card : pmtbTopUpReqCards) {
			PmtbProduct pmtbProduct = card.getPmtbProduct();
			String currentStatus = pmtbProduct.getCurrentStatus();
			if (currentStatus.equals(NonConfigurableConstants.PRODUCT_STATUS_SUSPENDED)
					|| currentStatus.equals(NonConfigurableConstants.PRODUCT_STATUS_TERMINATED)
					|| currentStatus.equals(NonConfigurableConstants.PRODUCT_STATUS_PARENT_SUSPENDED)) {
				failedCards.add(pmtbProduct.getCardNo());
			}
		}

		if (!failedCards.isEmpty()) {
			throw new WrongValueException(
					"Failed to approve top up request as there exists terminated or suspended card(s): "
							+ Joiner.on(", ").join(failedCards));
		}

		// create the top up invoice
		createTopUpInvoice(req, user, false);

		if (req.getUri() != null) {
			doProcessingJSON(req);
		}
		// if the credit term is COD, product card will only be top up upon full payment
		// else the card will be top up immediately before payment receipt
		if (req.getMstbCreditTermMaster() == null) {
			req.setStatus(NonConfigurableConstants.PREPAID_REQUEST_STATUS_PENDING_PAYMENT);
		} else {
			topUp(req, false, user);
		}

		// if it is real approve
		if (NonConfigurableConstants.getBoolean(req.getApprovalRequired())) {
			SatbUser approver = this.daoHelper.getUserDao().getUserWithRoles(CommonWindow.getUserId());
			req.setApprovalBy(approver);
			req.setApprovalDate(new Date());
			req.setApprovalStatus(NonConfigurableConstants.PREPAID_APPROVAL_STATUS_APPROVED);
		}

		req.setProcessDate(new Date());
		super.update(req, user);

		if (req.getUri() != null || req.getUri()!="") {
			// this is for red dot api
//			 sendRedDotApprovalEmail(req,ConfigurableConstants.EMAIL_TYPE_PREPAID_CREDIT_REQUEST_APPROVED);
		} else {
			sendApprovalEmail(req, ConfigurableConstants.EMAIL_TYPE_PREPAID_TOPUP_REQUEST_APPROVED);
		}

	}

	public void doProcessingJSON(PmtbTopUpReq req) throws ExpectedException {
		boolean apiHit = false;
		try {
			String returnReddotInvoiceNo = "";

			req.setUri(req.getUri().concat(encrypt(req.getReqNo().toString())));
			req.setFailureUri((req.getUri().replace("redirect", "redirectFail")));
			// JSON TO SENT
			// SearchPrepaidRequestForm form = buildSearchForm();
			// List<Object[]> requestList =
			// businessHelper.getPrepaidBusiness().searchPrepaidCreditInvoiceRequest(form);

			JSONObject mainJson = new JSONObject();
			Date date = Calendar.getInstance().getTime();
			DateFormat dateFormat = new SimpleDateFormat("ddMMyyhhmmss");
//	        String templateId = dateFormat.format(date);

			Map properties = (Map) SpringUtil.getBean("webserviceProperties");
			// retrieve properties val
			String authorizationUser = (String) properties.get("ws.authorizationUser");
			String authorizationPW = (String) properties.get("ws.authorizationPW");
			String merchantId = (String) properties.get("ws.merchantId");
			String termsAndConditions = (String) properties.get("ws.termsAndConditions");
			String templateId = (String) properties.get("ws.templateId");
			String senderName = (String) properties.get("reddot.senderName");
			String senderEmail = (String) properties.get("reddot.senderEmail");
			String subject = (String) properties.get("reddot.subject");
			String sendInvoiceUrl = (String) properties.get("ws.sendInvoiceUrl");
			String cardNo = "";
			String cardsName = "";
			AmtbAccount topLvlAccount = req.getAmtbAccount();
			while(topLvlAccount.getAmtbAccount() != null)
			{
				topLvlAccount = topLvlAccount.getAmtbAccount();
			}

			Set<PmtbTopUpReqCard> reqCards = req.getPmtbTopUpReqCards();
			mainJson.put("grandTotalAmount",
					req.getTotalAmount().add(req.getTotalAmount().multiply(new BigDecimal("0.07"))));
			mainJson.put("subtotalAmount", req.getTotalAmount());

			mainJson.put("templateId", templateId);
			mainJson.put("merchantId", merchantId);
			mainJson.put("termsAndConditions", termsAndConditions);
			JSONObject fields = new JSONObject();
			Date currDate = new Date();
			SimpleDateFormat formatter = new SimpleDateFormat("yyyymmddhhmmss");
			String sampleRef = formatter.format(date);
			fields.put("merchant_ref", sampleRef);
			fields.put("invoice_currency", "SGD");
			fields.put("subject", subject);
			fields.put("callbackfail", req.getFailureUri());

			String mainContactEmail = "";
			AmtbAccount acct2 = this.daoHelper.getAccountDao().getAccountWithParentandContact(""+req.getAmtbAccount().getAccountNo());
			for (AmtbAcctMainContact mainContact : acct2.getAmtbAcctMainContacts()) {
				if(mainContact.getComp_id().getMainContactType().equals(NonConfigurableConstants.MAIN_CONTACT_TYPE_BILLING)){
					AmtbContactPerson contact = mainContact.getAmtbContactPerson();
					mainContactEmail = contact.getMainContactEmail();
					break;
				}
			}
			//in case current email is empty
			if(mainContactEmail.trim().equals(""))
			{
				acct2 = this.daoHelper.getAccountDao().getAccountWithParentandContact(""+topLvlAccount.getAccountNo());

				for (AmtbAcctMainContact mainContact : topLvlAccount.getAmtbAccount().getAmtbAcctMainContacts()) {
					if(mainContact.getComp_id().getMainContactType().equals(NonConfigurableConstants.MAIN_CONTACT_TYPE_BILLING)){
						AmtbContactPerson contact = mainContact.getAmtbContactPerson();
						mainContactEmail = contact.getMainContactEmail();
						break;
					}
				}
			}

			fields.put("recipient_email", mainContactEmail); // find email
			fields.put("sender_email", senderEmail);
			fields.put("sender_name", senderName);
			fields.put("callback", req.getUri());

			mainJson.put("fields", fields);
			logger.info("callback > " + req.getUri());
			JSONArray productsList = new JSONArray();
			JSONObject products = new JSONObject();

			products.put("product_name", "Topup for card ("+sampleRef+")");
			products.put("unit_price", req.getTotalAmount());
			products.put("quantity", "1");
			products.put("total", req.getTotalAmount());
			productsList.add(products);

			subject.replace("#accountNo#", topLvlAccount.getCustNo())
					.replace("#invoiceNo#", req.getBmtbInvoiceHeader().getInvoiceNo().toString())
					.replace("#cabChargeNo#", cardNo)
					.replace("#cabChargeName#", cardsName);

			// tax
			JSONObject tax = new JSONObject();
			tax.put("value", "7");
			tax.put("type", "percentage");

			mainJson.put("tax", tax);
			mainJson.put("products", productsList);

//			HostnameVerifier trustAllHostnames = new HostnameVerifier() {
//				@Override
//				public boolean verify(String hostname, SSLSession session) {
//					return true; // Just allow them all.
//				}
//			};

//			HttpsURLConnection.setDefaultHostnameVerifier(trustAllHostnames);

//			logger.info("open connection: " + sendInvoiceUrl + " with authorizationUser: " + authorizationUser +" and authorizationPW: " + authorizationPW);
			logger.info("mainJson: " + mainJson);
			URL url = new URL(sendInvoiceUrl);

			Security.addProvider(new BouncyCastleProvider());

			logger.info("Opening connection with the url: " + sendInvoiceUrl);
			HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
			logger.info(":::::::Manage to open connection successfully::::");
			logger.info("auth: " + authorizationUser + ":" + authorizationPW);
			logger.info("encoded auth: " + Base64.encodeBase64((authorizationUser + ":" + authorizationPW).getBytes()));


			connection.setSSLSocketFactory(new TSLSocketConnectionFactory());
			connection.setRequestMethod("POST");
			String auth = authorizationUser + ":" + authorizationPW;

			byte[] encodedAuth = Base64.encodeBase64(auth.getBytes());

			connection.setRequestProperty("Authorization", "Basic " + new String(encodedAuth));

			logger.info("::::::Connection:::::");
			logger.info(connection);
			logger.info("::::::End of Connection:::::");
//			logger.info("request property: " + connection.getRequestProperties());
//			logger.info("header fields: " + connection.getHeaderFields());
//			logger.info("request method: " + connection.getRequestMethod());

			connection.setDoOutput(true);
			logger.info("after do output: ");


			OutputStream os = connection.getOutputStream();
			os.write(mainJson.toString().getBytes());
			os.flush();
			os.close();
			InputStream input = connection.getInputStream();

			logger.info("Opening response code: ");
			logger.info("status code: " + connection.getResponseCode());
			BufferedReader inn = new BufferedReader(new InputStreamReader(input));
			String inputLinee;
			StringBuffer response = new StringBuffer();
			while ((inputLinee = inn.readLine()) != null) {
				response.append(inputLinee);
			}
			inn.close();

			logger.info("RED DOT MESSAGE" + response.toString());
			javax.json.JsonReader jsonReader = Json.createReader(new StringReader(response.toString()));
			javax.json.JsonObject jsonObject = jsonReader.readObject();
			JsonValue result = jsonObject.get("success");
			logger.info("RESULT!!!!!!!!!");
			logger.info(result);

			if (result != null && !result.toString().isEmpty() && result.toString() == "true") { // success
				apiHit = true;

				JsonObject redDotInvoiceNo = jsonObject.getJsonObject("response");
				returnReddotInvoiceNo = redDotInvoiceNo.getString("invoiceNo");
				logger.info("test "+returnReddotInvoiceNo);
				req.setRedDotInvoiceNo(returnReddotInvoiceNo.trim());
				this.save(req);

				System.out.println(req.getUri());
				BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
				String inputLine;
				StringBuffer responsee = new StringBuffer();
				while ((inputLine = in.readLine()) != null) {
					response.append(inputLine);
				}
				in.close();
				// print result
				logger.info("Responses : " + responsee.toString());

				//send email
				if(returnReddotInvoiceNo != null && !returnReddotInvoiceNo.trim().equals(""))
				{
					logger.info("Sending Email for : "+returnReddotInvoiceNo);
					String responseMsg = reddotSendEmail(returnReddotInvoiceNo);

					if (responseMsg.trim().equalsIgnoreCase("Success"))
						logger.info("Send Email successful for "+returnReddotInvoiceNo);
					else
						logger.info("Send Email fail "+returnReddotInvoiceNo);
				}
				else
					logger.info("Send Email fail "+returnReddotInvoiceNo);

			} else {
				logger.info("Red Dot Api Failed");
			}
		} catch (Exception e) {
			LoggerUtil.printStackTrace(logger, e);
		}

		if (!apiHit) {
			throw new ExpectedException("Red Dot API Failed !");
		}

	}

	public void approveTopUpCreditRequest(PmtbTopUpReq req, String user) throws ExpectedException {

		validateRequestBeenApproved(req);

		// validate is the top up product has been suspended or terminated
		Set<PmtbTopUpReqCard> pmtbTopUpReqCards = req.getPmtbTopUpReqCards();
		Set<String> failedCards = Sets.newHashSet();
		for (PmtbTopUpReqCard card : pmtbTopUpReqCards) {
			PmtbProduct pmtbProduct = card.getPmtbProduct();
			String currentStatus = pmtbProduct.getCurrentStatus();
			if (currentStatus.equals(NonConfigurableConstants.PRODUCT_STATUS_SUSPENDED)
					|| currentStatus.equals(NonConfigurableConstants.PRODUCT_STATUS_TERMINATED)
					|| currentStatus.equals(NonConfigurableConstants.PRODUCT_STATUS_PARENT_SUSPENDED)) {
				failedCards.add(pmtbProduct.getCardNo());
			}
		}

		if (!failedCards.isEmpty()) {
			throw new WrongValueException(
					"Failed to approve top up request as there exists terminated or suspended card(s): "
							+ Joiner.on(", ").join(failedCards));
		}

		// create the top up invoice
		// not needed for resubmit as this has been created by normal flow
//		createTopUpInvoice(req, user, false);

		if (req.getUri() != null) {
			req.setUri(req.getUri().concat(encrypt(req.getReqNo().toString())));
			req.setFailureUri((req.getUri().replace("redirect", "redirectFail")));
			doProcessingJSON(req);
		}
		// if the credit term is COD, product card will only be top up upon full payment
		// else the card will be top up immediately before payment receipt
		if (req.getMstbCreditTermMaster() == null) {
			req.setStatus(NonConfigurableConstants.PREPAID_REQUEST_STATUS_PENDING_PAYMENT);
		} else {
			topUp(req, false, user);
		}

		// if it is real approve
		if (NonConfigurableConstants.getBoolean(req.getApprovalRequired())) {
//			SatbUser approver = this.daoHelper.getUserDao().getUserWithRoles(CommonWindow.getUserId());
//			req.setApprovalBy(approver);
			req.setApprovalDate(new Date());
			req.setApprovalStatus(NonConfigurableConstants.PREPAID_APPROVAL_STATUS_APPROVED);
		}

		req.setProcessDate(new Date());
		super.update(req, user);
	}

	private void validatTerminatedOrSuspended(PmtbProduct product) {

		String currentStatus = product.getCurrentStatus();
		if (currentStatus.equals(NonConfigurableConstants.PRODUCT_STATUS_SUSPENDED)
				|| currentStatus.equals(NonConfigurableConstants.PRODUCT_STATUS_TERMINATED)) {
			throw new WrongValueException(
					"Failed to approve request as card " + product.getCardNo() + " was terminated or suspended.");
		}
	}

	public void approveTransferRequest(PmtbTransferReq req, String user) {

		validateRequestBeenApproved(req);

		PmtbProduct fromProduct = req.getFromPmtbProduct();
		fromProduct = this.daoHelper.getProductDao().getProductAndAccount(fromProduct);

		PmtbProduct toProduct = req.getToPmtbProduct();
		toProduct = this.daoHelper.getProductDao().getProductAndAccount(toProduct);

		// validate is the from product or to product has been suspended or terminated
		validatTerminatedOrSuspended(fromProduct);
		validatTerminatedOrSuspended(toProduct);

		// recalculate the actual transferable value and cash plus, as the value might
		// has been change at approval time
		TransferableAmtInfo transferInfo = this.businessHelper.getPrepaidBusiness()
				.calculateTransferableValueAndCashPlus(fromProduct);
		BigDecimal txferableCardValue = transferInfo.getCardValue();
		BigDecimal txferableCashplus = transferInfo.getCashplus();

		logger.info("txferableCardValue: " + txferableCardValue);
		logger.info("txferableCashplus: " + txferableCashplus);

		if (txferableCardValue.doubleValue() < 0) {
			throw new WrongValueException("Transferable value (" + txferableCardValue
					+ ") cannot be less than zero. This might be due to data inconsistency issue.");
		}

		if (txferableCashplus.doubleValue() < 0) {
			throw new WrongValueException("Transferable cashplus (" + txferableCashplus
					+ ") cannot be less than zero. This might be due to data inconsistency issue.");
		}

		BigDecimal transferValue = null;
		BigDecimal transferCashplus = null;
		BigDecimal transferCreditBalance = null;

		if (NonConfigurableConstants.getBoolean(req.getTransferAllFlag())) {
			transferValue = txferableCardValue;
			transferCashplus = txferableCashplus;
		} else {
			transferValue = req.getTransferCardValue();
			transferCashplus = req.getTransferCashplus();
		}

		if (transferValue.doubleValue() > txferableCardValue.doubleValue()) {
			throw new WrongValueException("Transfer value (" + transferValue + ") cannot more than transferable value ("
					+ txferableCardValue + ").");
		}

		if (transferCashplus.doubleValue() > txferableCashplus.doubleValue()) {
			throw new WrongValueException("Transfer cashplus (" + transferCashplus
					+ ") cannot more than transferable cashplus (" + txferableCashplus + ").");
		}

		transferCreditBalance = transferValue.add(transferCashplus);

		// update the actual from card and to card value, cash plus so that the request
		// details show properly under view after approved
		req.setFromCardValue(fromProduct.getCardValue());
		req.setFromCashplus(fromProduct.getCashplus());
		req.setToCardValue(toProduct.getCardValue());
		req.setToCashplus(toProduct.getCashplus());
		req.setTxferableCardValue(txferableCardValue);
		req.setTxferableCashplus(txferableCashplus);

		req.setTransferCardValue(transferValue);
		req.setTransferCashplus(transferCashplus);

		// determine the applied amount to product
		ApplyAmtInfo applyAmtInfo = new ApplyAmtInfo();
		applyAmtInfo.setApplyCardValue(transferValue);
		applyAmtInfo.setApplyCashplus(transferCashplus);
		applyAmtInfo.setApplyCreditBalance(transferCreditBalance);

		// process From Product
		PrepaidUtil.deductProductAmtWithApplyAmtInfo(fromProduct, applyAmtInfo, false);

		// process To Product
		PrepaidUtil.addProductAmtWithApplyAmtInfo(toProduct, applyAmtInfo);

		// the transfer fee is deducted from to card value followed by cash plus
		ApplyAmtInfo transferFeeApplyAmount = null;
		GstInfo transferGstInfo = null;
		if (!NonConfigurableConstants.getBoolean(req.getWaiveTransferFeeFlag())) {
			BigDecimal transferFee = req.getTransferFee();

			AmtbAccount amtbAccount = req.getAmtbAccount();
			FmtbEntityMaster entity = amtbAccount.getFmtbArContCodeMaster().getFmtbEntityMaster();
			BigDecimal gstRate = this.daoHelper.getFinanceDao().getLatestGST(entity.getEntityNo(), null,
					DateUtil.getCurrentTimestamp(), NonConfigurableConstants.TRANSACTION_TYPE_PREPAID_TRANSFER_FEE);

			transferGstInfo = GstUtil.backwardCalculateGstInfo(transferFee, gstRate);

			BigDecimal transferFeeWithGst = transferGstInfo.getAmountWithGst();
			transferFeeApplyAmount = PrepaidUtil.calculateCardValuePrefApplyAmount(toProduct.getCardValue(),
					toProduct.getCashplus(), transferFeeWithGst);

			PrepaidUtil.deductProductAmtWithApplyAmtInfo(toProduct, transferFeeApplyAmount, true);
		}

		req.setStatus(NonConfigurableConstants.PREPAID_REQUEST_STATUS_COMPLETED);

		super.update(fromProduct);
		super.update(toProduct);

		// if it is real approve
		if (NonConfigurableConstants.getBoolean(req.getApprovalRequired())) {
			SatbUser approver = this.daoHelper.getUserDao().getUserWithRoles(CommonWindow.getUserId());
			req.setApprovalBy(approver);
			req.setApprovalDate(new Date());
			req.setApprovalStatus(NonConfigurableConstants.PREPAID_APPROVAL_STATUS_APPROVED);
		}

		req.setProcessDate(new Date());
		super.update(req);

		// create the PREPAID transaction for report
		List<PmtbPrepaidCardTxn> txns = Lists.newArrayList();
		txns.add(PmtbPrepaidCardTxn.buildTxn(req, fromProduct,
				NonConfigurableConstants.PREPAID_TXN_TYPE_EXTERNAL_TRANSFER_OUT, transferCreditBalance.negate(), null,
				transferValue.negate(), transferCashplus.negate(), false));

		txns.add(PmtbPrepaidCardTxn.buildTxn(req, toProduct,
				NonConfigurableConstants.PREPAID_TXN_TYPE_EXTERNAL_TRANSFER_IN, transferCreditBalance, null,
				transferValue, transferCashplus, false));

		if (!NonConfigurableConstants.getBoolean(req.getWaiveTransferFeeFlag())) {

			BigDecimal transferFeeWithoutGst = transferGstInfo.getAmountWithoutGst();
			BigDecimal transferFeeGst = transferGstInfo.getGst();

			BigDecimal transferFeeDeductFromValue = transferFeeApplyAmount.getApplyCardValue();
			BigDecimal transferFeeDeductFromCashPlus = transferFeeApplyAmount.getApplyCashplus();

			PmtbPrepaidCardTxn transferFeeTxn = PmtbPrepaidCardTxn.buildTxn(req, toProduct,
					NonConfigurableConstants.PREPAID_TXN_TYPE_TRANSFER_FEE, transferFeeWithoutGst.negate(),
					transferFeeGst.negate(), transferFeeDeductFromValue.negate(),
					transferFeeDeductFromCashPlus.negate(), false);

			super.save(transferFeeTxn);
			super.businessHelper.getPrepaidBusiness().createPrepaidDirectReceipt(transferFeeTxn, user);

		}

		super.saveAll(txns);

		// update the AS
		try {
			logger.debug("update AS of from product: " + fromProduct.getProductNo());
			API.updateProductAPIActive(fromProduct, user);

			logger.debug("update AS of to product: " + toProduct.getProductNo());
			API.updateProductAPIActive(toProduct, user);

		} catch (Exception e) {
			LoggerUtil.printStackTrace(logger, e);
			throw new WrongValueException("Failed to create AS request: " + e.getMessage());
		}

		sendApprovalEmail(req, ConfigurableConstants.EMAIL_TYPE_PREPAID_TRANSFER_REQUEST_APPROVED);

	}

	public void approveExtendBalanceExpiryDateRequest(PmtbExtBalExpDateReq req, String user) {

		PmtbProduct pmtbProduct = req.getPmtbProduct();

		validateRequestBeenApproved(req);

		// validate is the product has been suspended or terminated
		validatTerminatedOrSuspended(pmtbProduct);

		java.util.Date newBalExpDate = req.getNewBalExpDate();

		ProductUtil.validateBalanceExpiryDateNoPast(newBalExpDate);
		ProductUtil.validateNoEarlierThanCurrentBalanceExpiryDate(newBalExpDate, pmtbProduct.getBalanceExpiryDate());

		pmtbProduct.setBalanceExpiryDate(newBalExpDate);

		pmtbProduct.setBalanceForfeitedFlag(NonConfigurableConstants.BOOLEAN_YN_NO);

		super.update(pmtbProduct);

		req.setStatus(NonConfigurableConstants.PREPAID_REQUEST_STATUS_COMPLETED);

		// if it is real approve
		if (NonConfigurableConstants.getBoolean(req.getApprovalRequired())) {
			SatbUser approver = this.daoHelper.getUserDao().getUserWithRoles(CommonWindow.getUserId());
			req.setApprovalBy(approver);
			req.setApprovalDate(new Date());
			req.setApprovalStatus(NonConfigurableConstants.PREPAID_APPROVAL_STATUS_APPROVED);
		}

		req.setProcessDate(new Date());
		super.update(req);

		// update the AS
		try {
			logger.debug("update AS of product: " + pmtbProduct.getProductNo());
			API.updateProductAPIActive(pmtbProduct, user);

		} catch (Exception e) {
			LoggerUtil.printStackTrace(logger, e);
			throw new WrongValueException("Failed to create AS request: " + e.getMessage());
		}

		sendApprovalEmail(req, ConfigurableConstants.EMAIL_TYPE_PREPAID_EXTEND_REQUEST_APPROVED);
	}

	public void approveAdjustmentRequest(PmtbAdjustmentReq req, String user) {

		validateRequestBeenApproved(req);

		BigDecimal adjustValue = req.getAdjustValueAmount();
		BigDecimal adjustCashplus = req.getAdjustCashplusAmount();

		BigDecimal adjustValueGst = req.getAdjustValueGst();
		BigDecimal adjustCashplusGst = req.getAdjustCashplusGst();

		BigDecimal adjustValueWithGst = adjustValue.add(adjustValueGst);
		BigDecimal adjustCashplusWithGst = adjustCashplus.add(adjustCashplusGst);

		PmtbProduct product = req.getPmtbProduct();
		product = this.daoHelper.getProductDao().getProductAndAccount(product);

		validatTerminatedOrSuspended(product);

		BigDecimal oriCardValue = product.getCardValue();
		BigDecimal oriCashplus = product.getCashplus();
		BigDecimal oriCreditBalance = product.getCreditBalance();

		logger.info("current card value: " + oriCardValue);
		logger.info("current cashplus: " + oriCashplus);
		logger.info("adjust value with gst: " + adjustValueWithGst);
		logger.info("adjust cashplus with gst: " + adjustCashplusWithGst);

		validateAdjustable(oriCardValue, adjustValueWithGst, oriCashplus, adjustCashplusWithGst);

		BigDecimal finalCardValue = oriCardValue.add(adjustValueWithGst);
		BigDecimal finalCashplus = oriCashplus.add(adjustCashplusWithGst);
		BigDecimal finalCreditBalance = oriCreditBalance.add(adjustValueWithGst).add(adjustCashplusWithGst);

		product.setCardValue(finalCardValue);
		product.setCashplus(finalCashplus);
		product.setCreditBalance(finalCreditBalance);

		super.update(product);

		req.setOriValueAmount(oriCardValue);
		req.setOriCashplusAmount(oriCashplus);
		req.setStatus(NonConfigurableConstants.PREPAID_REQUEST_STATUS_COMPLETED);

		// if it is real approve
		if (NonConfigurableConstants.getBoolean(req.getApprovalRequired())) {
			SatbUser approver = this.daoHelper.getUserDao().getUserWithRoles(CommonWindow.getUserId());
			req.setApprovalBy(approver);
			req.setApprovalDate(new Date());
			req.setApprovalStatus(NonConfigurableConstants.PREPAID_APPROVAL_STATUS_APPROVED);
		}

		req.setProcessDate(new Date());
		super.update(req);

		// create the PREPAID transaction for report
		List<PmtbPrepaidCardTxn> txns = Lists.newArrayList();
		if (adjustValueWithGst.doubleValue() != 0) {
			PmtbPrepaidCardTxn adjustValuePrepaidTxn = PmtbPrepaidCardTxn.buildTxn(req, product,
					NonConfigurableConstants.PREPAID_TXN_TYPE_ADJUST_VALUE, adjustValue, adjustValueGst,
					adjustValueWithGst, null, true);

			adjustValuePrepaidTxn.setFmtbTransactionCode(req.getAdjustValueTxnCode());

			txns.add(adjustValuePrepaidTxn);
		}

		if (adjustCashplusWithGst.doubleValue() != 0) {
			PmtbPrepaidCardTxn adjustCashPlusPrepaidTxn = PmtbPrepaidCardTxn.buildTxn(req, product,
					NonConfigurableConstants.PREPAID_TXN_TYPE_ADJUST_CASHPLUS, adjustCashplus, adjustCashplusGst, null,
					adjustCashplusWithGst, true);

			adjustCashPlusPrepaidTxn.setFmtbTransactionCode(req.getAdjustCashplusTxnCode());

			txns.add(adjustCashPlusPrepaidTxn);
		}

		super.saveAll(txns);

		// update the AS
		try {
			API.updateProductAPIActive(product, user);

		} catch (Exception e) {
			LoggerUtil.printStackTrace(logger, e);
			throw new WrongValueException("Failed to create AS request: " + e.getMessage());
		}

		sendApprovalEmail(req, ConfigurableConstants.EMAIL_TYPE_PREPAID_ADJUST_REQUEST_APPROVED);

	}

	private void validateAdjustable(BigDecimal currentCardValue, BigDecimal adjustCardValue, BigDecimal currentCashplus,
									BigDecimal adjustCashplus) {

		// check whether the downward adjustment is not enough to reduce the card’s
		// value
		if (adjustCardValue.doubleValue() < 0) {

			if (currentCardValue.doubleValue() < Math.abs(adjustCardValue.doubleValue())) {
				throw new WrongValueException("Downward adjustment is not enough to reduce the card’s value.");
			}
		}

		// check whether the downward adjustment is not enough to reduce the card’s cash
		// plus
		if (adjustCashplus.doubleValue() < 0) {

			if (currentCashplus.doubleValue() < Math.abs(adjustCashplus.doubleValue())) {
				throw new WrongValueException("Downward adjustment is not enough to reduce the card’s cash plus.");
			}
		}

	}

	public void rejectPrepaidRequest(PmtbPrepaidReq req, String user) {

		validateRequestBeenApproved(req);

		String status = NonConfigurableConstants.PREPAID_REQUEST_STATUS_REJECTED;
		req.setStatus(status);

		SatbUser approver = this.daoHelper.getUserDao().getUserWithRoles(CommonWindow.getUserId());
		req.setApprovalBy(approver);
		req.setApprovalDate(new Date());
		req.setApprovalStatus(NonConfigurableConstants.PREPAID_APPROVAL_STATUS_REJECTED);

		req.setProcessDate(new Date());
		super.update(req, user);

		if (req instanceof PmtbIssuanceReq) {
			sendApprovalEmail(req, ConfigurableConstants.EMAIL_TYPE_PREPAID_ISSUE_REQUEST_REJECTED);

		} else if (req instanceof PmtbTopUpReq) {
			sendApprovalEmail(req, ConfigurableConstants.EMAIL_TYPE_PREPAID_TOPUP_REQUEST_REJECTED);

		} else if (req instanceof PmtbTransferReq) {
			sendApprovalEmail(req, ConfigurableConstants.EMAIL_TYPE_PREPAID_TRANSFER_REQUEST_REJECTED);

		} else if (req instanceof PmtbAdjustmentReq) {
			// do nothing, as reject prepaid adjustment request has its own method

		} else if (req instanceof PmtbExtBalExpDateReq) {
			sendApprovalEmail(req, ConfigurableConstants.EMAIL_TYPE_PREPAID_EXTEND_REQUEST_REJECTED);
		}

	}

	private void rejectAdjustmentRequest(PmtbAdjustmentReq req, String user) {

		PmtbProduct product = req.getPmtbProduct();
		BigDecimal oriCardValue = product.getCardValue();
		BigDecimal oriCashplus = product.getCashplus();

		req.setOriValueAmount(oriCardValue);
		req.setOriCashplusAmount(oriCashplus);

		String status = NonConfigurableConstants.PREPAID_REQUEST_STATUS_REJECTED;
		req.setStatus(status);

		SatbUser approver = this.daoHelper.getUserDao().getUserWithRoles(CommonWindow.getUserId());
		req.setApprovalBy(approver);
		req.setApprovalDate(new Date());
		req.setApprovalStatus(NonConfigurableConstants.PREPAID_APPROVAL_STATUS_REJECTED);

		req.setProcessDate(new Date());
		super.update(req, user);

		sendApprovalEmail(req, ConfigurableConstants.EMAIL_TYPE_PREPAID_ADJUST_REQUEST_REJECTED);
	}

	public void issueProduct(PmtbIssuanceReq req, boolean withPayment, String userId) {

		List<PmtbProduct> productList = Lists.newArrayList();
		List<PmtbPrepaidCardTxn> prepaidTxnList = Lists.newArrayList();
		Set<PmtbIssuanceReqCard> pmtbIssuanceReqCards = req.getPmtbIssuanceReqCards();
		for (PmtbIssuanceReqCard card : pmtbIssuanceReqCards) {

			PmtbProduct product = PmtbProduct.buildProduct(card);

			BigDecimal cashplus = card.getCashplus();
			BigDecimal cardValue = card.getCardValue();
			BigDecimal creditBalance = cashplus.add(cardValue);
			product.setCashplus(cashplus);
			product.setCardValue(cardValue);
			product.setCreditBalance(creditBalance);
			product.setBalanceForfeitedFlag(NonConfigurableConstants.BOOLEAN_YN_NO);

			productList.add(product);

			prepaidTxnList.addAll(buildIssuancePrepaidTxns(product, card));

			// the reason to put product into transient product instead of PMTB product as
			// request card might be already been persisted,
			// set PMTB product will cause transient exception as the product haven't been
			// saved.
			card.setTransientProduct(product);
		}

		try {

			// allocate card no to each product then save it
			for (PmtbProduct p : productList) {
				PmtbProductType productType = p.getPmtbProductType();

				PmtbCardNoSequence cardSeq = this.businessHelper.getProductBusiness().getCardNoSequence(productType);

				Integer count = CardNoGenerator.nextCardNoSeq(cardSeq);

				String cardNo = CardNoGenerator.generateCardNo(count, productType);
				p.setCardNo(cardNo);

				cardSeq.setCount(BigDecimal.valueOf(count));
				super.update(cardSeq);

				// during product is issue, AS request will be created
				this.businessHelper.getProductBusiness().commonIssueProduct(productType, Lists.newArrayList(p), userId);
			}

			// till here all the products has been saved, it is time to update the request
			// card with product
			for (PmtbIssuanceReqCard card : pmtbIssuanceReqCards) {
				card.setPmtbProduct(card.getTransientProduct());
				super.update(card);
			}
		} catch (HibernateOptimisticLockingFailureException e) {
			LoggerUtil.printStackTrace(logger, e);
			throw new WrongValueException(
					"There are other users issue the same product type and causing concurrency exception, please retry to issue the product.");
		} catch (Exception e) {
			LoggerUtil.printStackTrace(logger, e);
			throw new WrongValueException("Failed to issue product: " + e.getMessage());
		}

		super.saveAll(prepaidTxnList);
		super.updateAll(pmtbIssuanceReqCards);

		if (withPayment) {
			req.setStatus(NonConfigurableConstants.PREPAID_REQUEST_STATUS_COMPLETED);
		} else {
			req.setStatus(NonConfigurableConstants.PREPAID_REQUEST_STATUS_COMPLETED_WO_PAYMENT);
		}

		super.update(req);

	}

	public void topUp(PmtbTopUpReq req, boolean withPayment, String user) {
		// top up the product
		Set<PmtbTopUpReqCard> pmtbTopUpReqCards = req.getPmtbTopUpReqCards();
		List<PmtbProduct> productList = Lists.newArrayList();

		for (PmtbTopUpReqCard card : pmtbTopUpReqCards) {

			PmtbProduct product = card.getPmtbProduct();
			product = (PmtbProduct) this.daoHelper.getProductDao().getProductAndAccount(product);

			BigDecimal creditBalance = product.getCreditBalance();

			// determine paid value, cash plus, credit balance and credit limit
			BigDecimal topUpCashplus = card.getTopUpCashplus();
			BigDecimal topUpValue = card.getTopUpValue();

			BigDecimal cardValue = product.getCardValue().add(topUpValue);
			BigDecimal cashplus = product.getCashplus().add(topUpCashplus);
			creditBalance = creditBalance.add(topUpCashplus).add(topUpValue);

			product.setCashplus(cashplus);
			product.setCardValue(cardValue);
			product.setCreditBalance(creditBalance);

			Date newBalanceExpiryDate = card.getNewBalanceExpiryDate();
			Date currentBalanceExpiryDate = product.getBalanceExpiryDate();

			try {
				ProductUtil.validateNoEarlierThanCurrentBalanceExpiryDate(newBalanceExpiryDate,
						currentBalanceExpiryDate);
			} catch (WrongValueException e) {
				throw new WrongValueException("New Balance Expiry Date ("
						+ DateUtil.convertDateToStrWithGDFormat(newBalanceExpiryDate) + ") of card: "
						+ product.getCardNo() + " cannot be earlier than current Balance Expiry Date ( "
						+ DateUtil.convertDateToStrWithGDFormat(currentBalanceExpiryDate) + ").");
			}

			product.setBalanceExpiryDate(newBalanceExpiryDate);

			// reset the forfeited flag so that it could be forfeited again
			product.setBalanceForfeitedFlag(NonConfigurableConstants.BOOLEAN_YN_NO);

			productList.add(product);

		}

		List<PmtbPrepaidCardTxn> prepaidTxnList = buildTopUpPrepaidTxns(req);

		super.updateAll(productList);
		super.saveAll(prepaidTxnList);

		if (withPayment) {
			req.setStatus(NonConfigurableConstants.PREPAID_REQUEST_STATUS_COMPLETED);
		} else {
			req.setStatus(NonConfigurableConstants.PREPAID_REQUEST_STATUS_COMPLETED_WO_PAYMENT);
		}

		super.update(req);

		// update the AS
		try {
			for (PmtbProduct p : productList) {
				API.updateProductAPIActive(p, user);
			}
		} catch (Exception e) {
			LoggerUtil.printStackTrace(logger, e);
			throw new WrongValueException("Failed to create AS request: " + e.getMessage());
		}

	}

	private List<PmtbPrepaidCardTxn> buildIssuancePrepaidTxns(PmtbProduct product, PmtbIssuanceReqCard card) {

		PmtbIssuanceReq req = card.getPmtbIssuanceReq();

		List<PmtbPrepaidCardTxn> txns = Lists.newArrayList();
		BigDecimal cardValue = card.getCardValue();
		BigDecimal cashplus = card.getCashplus();
		BigDecimal issueAmount = cardValue.add(cashplus);

		// create initial value transaction
		txns.add(PmtbPrepaidCardTxn.buildTxn(req, product, NonConfigurableConstants.PREPAID_TXN_TYPE_ISSUE, issueAmount,
				null, cardValue, cashplus, false));

		return txns;
	}

	private List<PmtbPrepaidCardTxn> buildTopUpPrepaidTxns(PmtbTopUpReq req) {

		Set<PmtbTopUpReqCard> pmtbTopUpReqCards = req.getPmtbTopUpReqCards();
		List<PmtbPrepaidCardTxn> txns = Lists.newArrayList();
		for (PmtbTopUpReqCard card : pmtbTopUpReqCards) {

			BigDecimal cardValue = card.getTopUpValue();
			BigDecimal cashplus = card.getTopUpCashplus();

			BigDecimal topUpAmount = cardValue.add(cashplus);

			PmtbProduct product = card.getPmtbProduct();

			// create top up transaction
			txns.add(PmtbPrepaidCardTxn.buildTxn(req, product, NonConfigurableConstants.PREPAID_TXN_TYPE_TOP_UP,
					topUpAmount, null, cardValue, cashplus, false));

		}

		return txns;

	}

	private void buildPrepaidInvoice(BmtbInvoiceHeader header, List<BmtbInvoiceSummary> summaries, AmtbAccount acct,
									 Date invoiceDate, Date dueDate, String user) {

		Timestamp currentTime = DateUtil.getCurrentTimestamp();
		java.sql.Date currentDate = DateUtil.getCurrentDate();

		header.setAmtbAccountByAccountNo(acct);
		header.setAmtbAccountByDebtTo(acct);
		header.setInvoiceDate(DateUtil.convertUtilDateToSqlDate(invoiceDate));
		header.setDueDate(DateUtil.convertUtilDateToSqlDate(dueDate));
		header.setBilledFlag(NonConfigurableConstants.BOOLEAN_NO);
		header.setUpdatedBy(user);
		header.setInvoiceNo(daoHelper.getInvoiceDao().getNextSequenceNo(Sequence.INVOICE_NO_SEQUENCE).longValue());
		header.setInvoiceFormat(NonConfigurableConstants.INVOICE_FORMAT_MISC);
		// status will be always closed for PREPAID invoice
		header.setInvoiceStatus(NonConfigurableConstants.INVOICE_STATUS_OUTSTANDING);

		header.setStatementPeriodFromDate(currentDate);
		header.setStatementPeriodToDate(currentDate);
		BigDecimal latePaymentInterest = daoHelper.getAccountDao().getLatestLatePaymentRate(acct,
				header.getInvoiceDate());
		header.setLatePaymentInterest(latePaymentInterest);
		header.setTxnCutoffDate(currentDate);

		header.setUpdatedDt(currentTime);

		// to indicate the invoice is from PREPAID
		header.setPrepaidFlag(NonConfigurableConstants.BOOLEAN_YN_YES);
		header.setBmtbInvoiceSummaries(Sets.newHashSet(summaries));

		BigDecimal grandTotal = BigDecimal.ZERO;
		for (BmtbInvoiceSummary summary : header.getBmtbInvoiceSummaries()) {

			BigDecimal qty = new BigDecimal(summary.getQuantity());
			BigDecimal price = summary.getUnitPrice().multiply(qty);
			BigDecimal totalAmount = price.subtract(summary.getDiscount()).add(summary.getGst());
			grandTotal = grandTotal.add(totalAmount);

			summary.setBmtbInvoiceHeader(header);
			summary.setAmtbAccount(acct);
			summary.setTotalNew(totalAmount);
			summary.setBalance(totalAmount);
			summary.setAdminFee(new BigDecimal("0.00"));
			summary.setNewTxn(price);

			summary.setDisplayPriority1(1);
			summary.setDisplayPriority2(1);

			Set<BmtbInvoiceDetail> bmtbInvoiceDetails = summary.getBmtbInvoiceDetails();
			if (bmtbInvoiceDetails == null || bmtbInvoiceDetails.isEmpty()) {
				bmtbInvoiceDetails = Sets.newHashSet();
				bmtbInvoiceDetails.add(new BmtbInvoiceDetail());
				summary.setBmtbInvoiceDetails(bmtbInvoiceDetails);
			}

			BmtbInvoiceDetail detail = summary.getBmtbInvoiceDetails().iterator().next();

			detail.setBmtbInvoiceSummary(summary);
			detail.setInvoiceDetailType(summary.getSummaryType());
			detail.setInvoiceDetailName(summary.getSummaryName());
			detail.setPmtbProductType(summary.getPmtbProductType());
			detail.setAmtbAccount(acct);
			detail.setOutstandingAmount(summary.getBalance());
			detail.setNewTxn(summary.getNewTxn());
			detail.setTotalNew(summary.getTotalNew());
			detail.setAdminFee(summary.getAdminFee());
			detail.setGst(summary.getGst());

			detail.setUpdatedBy(user);
			detail.setUpdatedDt(currentTime);

		}

		header.setOutstandingAmount(grandTotal);
		header.setNewBalance(grandTotal);
		header.setNewTxn(grandTotal);

	}

	public Object createIssuanceInvoice(PmtbIssuanceReq req, String user, boolean isDraft) {

		// build the transactions
		List<BmtbIssuanceInvoiceTxn> txns = buildIssuanceInvoiceTxns(req);

		// generate the invoice summary
		List<BmtbInvoiceSummary> summaries = buildIssuanceInvoiceSummary(txns);

		// generate the invoice
		BmtbInvoiceHeader header = new BmtbInvoiceHeader();
		buildIssuanceInvoice(header, summaries, req, user);

		req.setBmtbInvoiceHeader(header);

		// save or update the entities to database
		if (isDraft) {

			BmtbDraftInvHeader draftHeader = BmtbDraftInvHeader.copy(header, null);
			List<BmtbDraftInvSummary> draftSummaries = Lists.newArrayList();
			for (BmtbInvoiceSummary summary : summaries) {
				// copy the summaries
				BmtbDraftInvSummary draftSummary = BmtbDraftInvSummary.copy(summary, draftHeader);
				draftSummaries.add(draftSummary);

				List<BmtbDraftInvDetail> draftDetails = Lists.newArrayList();
				Set<BmtbInvoiceDetail> details = summary.getBmtbInvoiceDetails();
				for (BmtbInvoiceDetail detail : details) {
					BmtbDraftInvDetail draftDetail = BmtbDraftInvDetail.copy(detail, draftSummary);
					draftDetails.add(draftDetail);
				}
				draftSummary.setBmtbDraftInvDetails(Sets.newHashSet(draftDetails));
			}
			draftHeader.setBmtbDraftInvSummaries(Sets.newHashSet(draftSummaries));

			// this should also save the header, summaries and details
			super.save(draftHeader);

			return draftHeader;
		} else {
			// save or update the entities to database
			// this should also save the header, summaries and details
			super.save(header);

			super.saveAll(txns);

			return header;

		}

	}

	private void buildIssuanceInvoice(BmtbInvoiceHeader header, List<BmtbInvoiceSummary> summaries, PmtbIssuanceReq req,
									  String user) {

		AmtbAccount acct = req.getAmtbAccount();
		// the invoice date is the date that issuance request being approved
		Date invoiceDate = new Date();
		MstbCreditTermMaster mstbCreditTermMaster = req.getMstbCreditTermMaster();
		Date dueDate = null;
		if (mstbCreditTermMaster != null) {
			int creditTrem = MasterSetup.getCreditTermManager()
					.getCurrentCreditTerm(mstbCreditTermMaster.getCreditTermPlanNo());
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(invoiceDate);
			calendar.add(Calendar.DATE, creditTrem);
			dueDate = calendar.getTime();
		} else
			// For COD, the due date is null
			dueDate = null;

		buildPrepaidInvoice(header, summaries, acct, invoiceDate, dueDate, user);
	}

	private List<BmtbIssuanceInvoiceTxn> buildIssuanceInvoiceTxns(PmtbIssuanceReq req) {

		// generate the PREPAID transactions
		Set<PmtbIssuanceReqCard> pmtbIssuanceReqCards = req.getPmtbIssuanceReqCards();
		List<BmtbIssuanceInvoiceTxn> txns = Lists.newArrayList();

		// get GST
		// retrieve entity no from the top account
		Timestamp runDate = DateUtil.getCurrentTimestamp();
		AmtbAccount amtbAccount = req.getAmtbAccount();

		amtbAccount = this.daoHelper.getAccountDao().getAccountWithParent(String.valueOf(amtbAccount.getAccountNo()));
		AmtbAccount topAcct = AccountUtil.getTopLevelAccount(amtbAccount);

		FmtbEntityMaster entity = topAcct.getFmtbArContCodeMaster().getFmtbEntityMaster();

		BigDecimal issuanceFeeGstRate = this.daoHelper.getFinanceDao().getLatestGST(entity.getEntityNo(), null, runDate,
				NonConfigurableConstants.TRANSACTION_TYPE_PREPAID_ISSUANCE_FEE);

		for (PmtbIssuanceReqCard card : pmtbIssuanceReqCards) {

			logger.debug("Processing txn for issuance card: " + card.getReqCardNo());
			// create initial balance transaction
			txns.add(BmtbIssuanceInvoiceTxn.buildTxn(card,
					NonConfigurableConstants.INVOICE_SUMMARY_DETAIL_TYPE_PREPAID_ISSUE_VALUE, card.getCardValue(), null,
					null));

			// create initial cash back
			Set<MstbPromotionCashPlus> mstbPromotionCashPluses = card.getMstbPromotionCashPluses();
			for (MstbPromotionCashPlus promotion : mstbPromotionCashPluses) {
				String promoCode = promotion.getPromoCode();
				// store the promotion code into remarks
				BigDecimal cashplus = promotion.getCashplus();

				BmtbIssuanceInvoiceTxn cashPlusTxn = BmtbIssuanceInvoiceTxn.buildTxn(card,
						NonConfigurableConstants.INVOICE_SUMMARY_DETAIL_TYPE_PREPAID_ISSUE_CASHPLUS, cashplus, cashplus,
						null);
				cashPlusTxn.setDescription(promoCode);
				txns.add(cashPlusTxn);
			}

			// create issuance fee transaction
			BigDecimal issuanceFee = card.getIssuanceFee();
			if (issuanceFee.doubleValue() != 0) {
				BigDecimal issuanceFeeGst = GstUtil.forwardCalculateGst(issuanceFee, issuanceFeeGstRate);

				txns.add(BmtbIssuanceInvoiceTxn.buildTxn(card,
						NonConfigurableConstants.INVOICE_SUMMARY_DETAIL_TYPE_PREPAID_ISSUANCE_FEE, issuanceFee, null,
						issuanceFeeGst));

				// create waive issuance fee transaction
				if (NonConfigurableConstants.getBoolean(card.getWaiveIssuanceFeeFlag())) {

					txns.add(BmtbIssuanceInvoiceTxn.buildTxn(card,
							NonConfigurableConstants.INVOICE_SUMMARY_DETAIL_TYPE_PREPAID_ISSUANCE_FEE_WAIVAL,
							issuanceFee.negate(), null, issuanceFeeGst != null ? issuanceFeeGst.negate() : null));
				}
			}
		}

		// create the delivery fee transaction
		BigDecimal deliveryCharge = req.getDeliveryCharge();

		if (deliveryCharge.doubleValue() > 0) {

			FmtbTransactionCode deliveryChargeTxnCode = req.getDeliveryChargeTxnCode();
			BigDecimal deliveryChargeGstRate = this.daoHelper.getFinanceDao().getLatestGST(entity.getEntityNo(),
					runDate, deliveryChargeTxnCode.getTxnCode());
			BigDecimal deliveryChargeGst = GstUtil.forwardCalculateGst(deliveryCharge, deliveryChargeGstRate);

			BmtbIssuanceInvoiceTxn deliveryChargeTxn = BmtbIssuanceInvoiceTxn.buildTxn(req,
					deliveryChargeTxnCode.getTxnType(), req.getDeliveryCharge(), null, deliveryChargeGst);
			deliveryChargeTxn.setDescription(NonConfigurableConstants.INVOICE_SUMMARY_NAME_DELIVERY_FEE);
			deliveryChargeTxn.setTxnCode(deliveryChargeTxnCode);

			txns.add(deliveryChargeTxn);
		}

		// the discount cannot be in negative form, it might cause issue during payment
		// create the discount fee transaction
		BigDecimal discount = req.getDiscount();
		if (discount.doubleValue() > 0) {
			BigDecimal negativeDiscount = discount.negate();
			txns.add(BmtbIssuanceInvoiceTxn.buildTxn(req,
					NonConfigurableConstants.INVOICE_SUMMARY_DETAIL_TYPE_PREPAID_DISCOUNT, negativeDiscount, null,
					null));
		}

		return txns;
	}

	private List<BmtbInvoiceSummary> buildIssuanceInvoiceSummary(List<BmtbIssuanceInvoiceTxn> issuanceInvoiceTxns) {
		// generate summary

		// group the transaction
		Multimap<SummaryGroup, BmtbIssuanceInvoiceTxn> txnSummaryMap = ArrayListMultimap.create();

		String name = null;
		for (BmtbIssuanceInvoiceTxn txn : issuanceInvoiceTxns) {

			SummaryGroup group = new SummaryGroup();
			String txnType = txn.getTxnType();
			PmtbIssuanceReqCard card = txn.getPmtbIssuanceReqCard();
			PmtbProductType productType = null;
			FmtbTransactionCode txnCode = txn.getTxnCode();

			if (card != null) {
				productType = card.getPmtbProductType();
			}

			// based on different transaction type, it might have custom summary name shown
			// in the report
			if (NonConfigurableConstants.INVOICE_SUMMARY_DETAIL_TYPE_PREPAID_ISSUE_VALUE.equals(txnType)) {
				name = productType.getName() + " TOP UP";

			} else if (NonConfigurableConstants.INVOICE_SUMMARY_DETAIL_TYPE_PREPAID_ISSUE_CASHPLUS.equals(txnType)) {

				name = NonConfigurableConstants.INVOICE_SUMMARY_TYPE_GROUP_NAME.get(txnType);
				name += " " + txn.getDescription();

			}
			// cater for delivery transaction code
			else if (txnCode != null) {
				name = txn.getDescription();
			} else {

				name = NonConfigurableConstants.INVOICE_SUMMARY_TYPE_GROUP_NAME.get(txnType);
			}

			group.setType(txnType);
			group.setName(name);
			group.setUnitPrice(txn.getAmount());
			group.setProductType(productType);
			group.setTxnCode(txn.getTxnCode());

			// the summary map will based on the equal and hash code method to determine
			// whether the transaction is in the same group
			txnSummaryMap.put(group, txn);

			logger.debug("name: " + group.getName() + " txn: " + group.getType() + " " + group.hashCode() + " "
					+ txnSummaryMap.get(group).size());

		}

		// now is time to create the summary
		List<BmtbInvoiceSummary> summaryList = Lists.newArrayList();
		for (SummaryGroup group : txnSummaryMap.keySet()) {

			Collection<BmtbIssuanceInvoiceTxn> txns = txnSummaryMap.get(group);

			BigDecimal totalGst = new BigDecimal("0.00");
			BigDecimal totalDiscount = new BigDecimal("0.00");
			for (BmtbIssuanceInvoiceTxn txn : txns) {
				totalGst = totalGst.add(txn.getGst());
				totalDiscount = totalDiscount.add(txn.getDiscount());
			}

			BmtbInvoiceSummary summary = new BmtbInvoiceSummary();

			summary.setSummaryName(group.getName());
			summary.setSummaryType(group.getType());
			summary.setPmtbProductType(group.getProductType());

			BigDecimal unitPrice = group.getUnitPrice();
			int quantity = txns.size();

			summary.setUnitPrice(unitPrice);
			summary.setQuantity((long) quantity);

			BigDecimal newTxn = unitPrice.multiply(BigDecimal.valueOf(quantity));

			summary.setNewTxn(newTxn);
			summary.setDiscount(totalDiscount);
			summary.setGst(totalGst);

			BigDecimal balance = newTxn.add(summary.getDiscount()).add(summary.getGst());

			summary.setBalance(balance);

			// set the summary into transactions
			for (BmtbIssuanceInvoiceTxn txn : txns) {
				txn.setBmtbInvoiceSummary(summary);
			}

			FmtbTransactionCode txnCode = group.getTxnCode();

			// to set the delivery MISC code into invoice detail
			BmtbInvoiceDetail detail = new BmtbInvoiceDetail();
			if (txnCode != null) {
				detail.setFmtbTransactionCode(txnCode);
			}
			summary.setBmtbInvoiceDetails(Sets.newHashSet(detail));

			summaryList.add(summary);
		}

		return summaryList;
	}

	public Object createTopUpInvoice(PmtbTopUpReq req, String user, boolean isDraft) {

		// build the transactions
		List<BmtbTopUpInvoiceTxn> txns = buildTopUpInvoiceTxns(req);

		// generate the invoice summary
		List<BmtbInvoiceSummary> summaries = buildTopUpInvoiceSummary(txns);

		// generate the invoice
		BmtbInvoiceHeader header = new BmtbInvoiceHeader();
		buildTopUpInvoice(header, summaries, req, user);

		req.setBmtbInvoiceHeader(header);

		// save or update the entities to database
		if (isDraft) {

			BmtbDraftInvHeader draftHeader = BmtbDraftInvHeader.copy(header, null);
			List<BmtbDraftInvSummary> draftSummaries = Lists.newArrayList();
			for (BmtbInvoiceSummary summary : summaries) {
				// copy the summaries
				BmtbDraftInvSummary draftSummary = BmtbDraftInvSummary.copy(summary, draftHeader);
				draftSummaries.add(draftSummary);

				List<BmtbDraftInvDetail> draftDetails = Lists.newArrayList();
				Set<BmtbInvoiceDetail> details = summary.getBmtbInvoiceDetails();
				for (BmtbInvoiceDetail detail : details) {
					BmtbDraftInvDetail draftDetail = BmtbDraftInvDetail.copy(detail, draftSummary);
					draftDetails.add(draftDetail);
				}
				draftSummary.setBmtbDraftInvDetails(Sets.newHashSet(draftDetails));
			}
			draftHeader.setBmtbDraftInvSummaries(Sets.newHashSet(draftSummaries));

			// this should also save the header, summaries and details
			super.save(draftHeader);

			return draftHeader;
		} else {
			// save or update the entities to database
			// this should also save the header, summaries and details
			super.save(header);

			super.saveAll(txns);

			return header;

		}
	}

	private void buildTopUpInvoice(BmtbInvoiceHeader header, List<BmtbInvoiceSummary> summaries, PmtbTopUpReq req,
								   String user) {

		AmtbAccount acct = req.getAmtbAccount();
		// the invoice date is the date that issuance request being approved
		Date invoiceDate = new Date();
		MstbCreditTermMaster mstbCreditTermMaster = req.getMstbCreditTermMaster();
		Date dueDate = null;
		if (mstbCreditTermMaster != null) {
			int creditTrem = MasterSetup.getCreditTermManager()
					.getCurrentCreditTerm(mstbCreditTermMaster.getCreditTermPlanNo());
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(invoiceDate);
			calendar.add(Calendar.DATE, creditTrem);
			dueDate = calendar.getTime();
		} else
			// For COD, the due date is null
			dueDate = null;

		buildPrepaidInvoice(header, summaries, acct, invoiceDate, dueDate, user);
	}

	private List<BmtbTopUpInvoiceTxn> buildTopUpInvoiceTxns(PmtbTopUpReq req) {

		// generate the PREPAID transactions
		Set<PmtbTopUpReqCard> pmtbTopUpReqCards = req.getPmtbTopUpReqCards();

		// get GST
		// retrieve entity no from the top account
		Timestamp runDate = DateUtil.getCurrentTimestamp();
		AmtbAccount amtbAccount = req.getAmtbAccount();

		amtbAccount = this.daoHelper.getAccountDao().getAccountWithParent(String.valueOf(amtbAccount.getAccountNo()));
		AmtbAccount topAcct = AccountUtil.getTopLevelAccount(amtbAccount);

		FmtbEntityMaster entity = topAcct.getFmtbArContCodeMaster().getFmtbEntityMaster();

		BigDecimal gstRate = this.daoHelper.getFinanceDao().getLatestGST(entity.getEntityNo(), null, runDate,
				NonConfigurableConstants.TRANSACTION_TYPE_PREPAID_TOPUP_FEE);

		List<BmtbTopUpInvoiceTxn> txns = Lists.newArrayList();
		for (PmtbTopUpReqCard card : pmtbTopUpReqCards) {

			// create top up value transaction
			txns.add(BmtbTopUpInvoiceTxn.buildTxn(card,
					NonConfigurableConstants.INVOICE_SUMMARY_DETAIL_TYPE_PREPAID_TOP_UP_VALUE, card.getTopUpValue(),
					null, null));

			// create top up cash back transaction
			Set<MstbPromotionCashPlus> mstbPromotionCashPluses = card.getMstbPromotionCashPluses();
			for (MstbPromotionCashPlus promotion : mstbPromotionCashPluses) {
				String promoCode = promotion.getPromoCode();
				// store the promotion code into remarks
				BigDecimal cashplus = promotion.getCashplus();
				BmtbTopUpInvoiceTxn cashPlusTxn = BmtbTopUpInvoiceTxn.buildTxn(card,
						NonConfigurableConstants.INVOICE_SUMMARY_DETAIL_TYPE_PREPAID_TOP_UP_CASHPLUS, cashplus,
						cashplus, null);
				cashPlusTxn.setDescription(promoCode);
				txns.add(cashPlusTxn);

			}

			// create top up fee transaction
			BigDecimal topupFee = card.getTopUpFee();

			if (topupFee.doubleValue() != 0) {

				BigDecimal topupFeeGst = GstUtil.forwardCalculateGst(topupFee, gstRate);

				txns.add(BmtbTopUpInvoiceTxn.buildTxn(card,
						NonConfigurableConstants.INVOICE_SUMMARY_DETAIL_TYPE_PREPAID_TOP_UP_FEE, topupFee, null,
						topupFeeGst));

				// create waive top up fee transaction
				if (NonConfigurableConstants.getBoolean(card.getWaiveTopUpFeeFlag())) {

					txns.add(BmtbTopUpInvoiceTxn.buildTxn(card,
							NonConfigurableConstants.INVOICE_SUMMARY_DETAIL_TYPE_PREPAID_TOP_UP_FEE_WAIVAL,
							topupFee.negate(), null, topupFeeGst != null ? topupFeeGst.negate() : null));
				}
			}

		}
		return txns;
	}

	private List<BmtbInvoiceSummary> buildTopUpInvoiceSummary(List<BmtbTopUpInvoiceTxn> invoiceTxns) {
		// generate summary

		// group the transaction
		Multimap<SummaryGroup, BmtbTopUpInvoiceTxn> txnSummaryMap = ArrayListMultimap.create();

		String name = null;
		// under the MISC report, cash back are included in top up balance, later there
		// is a cash back transaction with amount of negative deduct from the balance
		for (BmtbTopUpInvoiceTxn txn : invoiceTxns) {

			SummaryGroup group = new SummaryGroup();
			String txnType = txn.getTxnType();
			PmtbTopUpReqCard card = txn.getPmtbTopUpReqCard();
			PmtbProductType productType = null;
			if (card != null) {
				productType = card.getPmtbProduct().getPmtbProductType();
			}

			// based on different transaction type, it might have custom summary name shown
			// in the report
			if (NonConfigurableConstants.INVOICE_SUMMARY_DETAIL_TYPE_PREPAID_TOP_UP_VALUE.equals(txnType)) {
				name = productType.getName() + " TOP UP";

			} else if (NonConfigurableConstants.INVOICE_SUMMARY_DETAIL_TYPE_PREPAID_TOP_UP_CASHPLUS.equals(txnType)) {

				name = NonConfigurableConstants.INVOICE_SUMMARY_TYPE_GROUP_NAME.get(txnType);
				name += " " + txn.getDescription();

			} else {
				name = NonConfigurableConstants.INVOICE_SUMMARY_TYPE_GROUP_NAME.get(txnType);

			}

			group.setName(name);
			group.setType(txnType);
			group.setProductType(productType);
			group.setUnitPrice(txn.getAmount());
			group.setTxnCode(null);

			// the summary map will based on the equal and hash code method to determine
			// whether the transaction is in the same group
			txnSummaryMap.put(group, txn);

		}

		// now is time to create the summary
		List<BmtbInvoiceSummary> summaryList = Lists.newArrayList();
		for (SummaryGroup group : txnSummaryMap.keySet()) {

			Collection<BmtbTopUpInvoiceTxn> txns = txnSummaryMap.get(group);
			BigDecimal totalGst = new BigDecimal("0.00");
			BigDecimal totalDiscount = new BigDecimal("0.00");
			for (BmtbTopUpInvoiceTxn txn : txns) {
				totalGst = totalGst.add(txn.getGst());
				totalDiscount = totalDiscount.add(txn.getDiscount());
			}

			BmtbInvoiceSummary summary = new BmtbInvoiceSummary();

			summary.setSummaryName(group.getName());
			summary.setSummaryType(group.getType());
			summary.setPmtbProductType(group.getProductType());

			BigDecimal unitPrice = group.getUnitPrice();
			int quantity = txns.size();

			summary.setUnitPrice(unitPrice);
			summary.setQuantity((long) quantity);

			BigDecimal newTxn = unitPrice.multiply(BigDecimal.valueOf(quantity));

			summary.setNewTxn(newTxn);
			summary.setDiscount(totalDiscount);
			summary.setGst(totalGst);

			// set the summary into transactions
			for (BmtbTopUpInvoiceTxn txn : txns) {
				txn.setBmtbInvoiceSummary(summary);
			}

			BmtbInvoiceDetail detail = new BmtbInvoiceDetail();
			summary.setBmtbInvoiceDetails(Sets.newHashSet(detail));

			summaryList.add(summary);
		}

		return summaryList;
	}

	public List<PmtbPrepaidReq> searchPrepaidRequest(SearchPrepaidRequestForm form) {

		return this.daoHelper.getPrepaidDao().searchPrepaidRequest(form);

	}

	public List<Object[]> searchPrepaidCreditInvoiceRequest(SearchPrepaidRequestForm form) {

		return this.daoHelper.getPrepaidDao().searchPrepaidInvoiceRequest(form);

	}

	public List<PmtbProduct> searchPrepaidProducts(SearchPrepaidProductForm form) {

		return this.daoHelper.getPrepaidDao().searchPrepaidProducts(form);

	}

	public List<PmtbProduct> getTopUpableProducts(Integer accountNo, String productTypeId, String cardNo,
												  String cardName) {
		return this.daoHelper.getPrepaidDao().getTopUpableProducts(accountNo, productTypeId, cardNo, cardName);
	}

	public List<PmtbProduct> getProductsForTransferReq(List<BigDecimal> productNoList) {
		return this.daoHelper.getPrepaidDao().getProductsForTransferReq(productNoList);
	}

	public PmtbPrepaidReq getPrepaidRequest(BigDecimal requestNo) {
		return this.daoHelper.getPrepaidDao().getPrepaidRequest(requestNo);
	}

	public PmtbTransferReq getPrepaidTransferRequest(BigDecimal requestNo) {
		return this.daoHelper.getPrepaidDao().getPrepaidTransferRequest(requestNo);
	}

	public PmtbIssuanceReq getPrepaidIssuanceRequest(BigDecimal requestNo) {
		return this.daoHelper.getPrepaidDao().getPrepaidIssuanceRequest(requestNo);
	}

	public PmtbTopUpReq getPrepaidTopUpRequest(BigDecimal requestNo) {
		return this.daoHelper.getPrepaidDao().getPrepaidTopUpRequest(requestNo);
	}

	public PmtbTopUpReq getPrepaidCreditTopUpRequest(BigDecimal requestNo) {
		return this.daoHelper.getPrepaidDao().getPrepaidCreditTopUpRequest(requestNo);
	}

	public PmtbProduct getPrepaidProduct(BigDecimal productNo) {
		return this.daoHelper.getPrepaidDao().getPrepaidProduct(productNo);
	}

	public class SummaryGroup {

		private String name;
		private BigDecimal unitPrice;
		private String type;
		private PmtbProductType productType;
		private FmtbTransactionCode txnCode;

		public String getName() {
			return name;
		}

		public BigDecimal getUnitPrice() {
			return unitPrice;
		}

		public void setName(String name) {
			this.name = name;
		}

		public void setUnitPrice(BigDecimal unitPrice) {
			this.unitPrice = unitPrice;
		}

		public String getType() {
			return type;
		}

		public void setType(String type) {
			this.type = type;
		}

		public FmtbTransactionCode getTxnCode() {
			return txnCode;
		}

		public void setTxnCode(FmtbTransactionCode txnCode) {
			this.txnCode = txnCode;
		}

		public PmtbProductType getProductType() {
			return productType;
		}

		public void setProductType(PmtbProductType productType) {
			this.productType = productType;
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + getOuterType().hashCode();
			result = prime * result + ((name == null) ? 0 : name.hashCode());
			result = prime * result + ((productType == null) ? 0 : productType.hashCode());
			result = prime * result + ((txnCode == null) ? 0 : txnCode.hashCode());
			result = prime * result + ((type == null) ? 0 : type.hashCode());
			result = prime * result + ((unitPrice == null) ? 0 : unitPrice.hashCode());
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			SummaryGroup other = (SummaryGroup) obj;
			if (!getOuterType().equals(other.getOuterType()))
				return false;
			if (name == null) {
				if (other.name != null)
					return false;
			} else if (!name.equals(other.name))
				return false;
			if (productType == null) {
				if (other.productType != null)
					return false;
			} else if (!productType.equals(other.productType))
				return false;
			if (txnCode == null) {
				if (other.txnCode != null)
					return false;
			} else if (!txnCode.equals(other.txnCode))
				return false;
			if (type == null) {
				if (other.type != null)
					return false;
			} else if (!type.equals(other.type))
				return false;
			if (unitPrice == null) {
				if (other.unitPrice != null)
					return false;
			} else if (!unitPrice.equals(other.unitPrice))
				return false;
			return true;
		}

		private PrepaidBusinessImpl getOuterType() {
			return PrepaidBusinessImpl.this;
		}

	}

	public BigDecimal getLatestGST(Integer entityNo, String productTypeId, Timestamp tripDt, String txnType) {
		return this.daoHelper.getFinanceDao().getLatestGST(entityNo, productTypeId, tripDt, txnType);
	}

	public TransferableAmtInfo calculateTransferableValueAndCashPlus(PmtbProduct product) {

		AsvwProduct asvwProduct = this.businessHelper.getEnquiryBusiness().getAsvwProduct(product.getCardNo());

		if (asvwProduct == null) {
			throw new WrongValueException("Unable to find Card No : " + product.getCardNo() + " in the AS");
		}

		BigDecimal asTotalTxnAmt = asvwProduct.getTotalTxnAmt();
		BigDecimal ibsBalance = product.getCreditBalance();

		logger.info("asTotalTxnAmt: " + asTotalTxnAmt);
		logger.info("ibsBalance: " + ibsBalance);

		BigDecimal totalTransferableAmount = ibsBalance.subtract(asTotalTxnAmt);

		TransferableAmtInfo transfer = PrepaidUtil.calculateCashplusPrefApplyAmount(product.getCardValue(),
				product.getCashplus(), totalTransferableAmount);

		return transfer;
	}

	public void createPrepaidDirectReceipt(PmtbPrepaidCardTxn txn, String user) {

		// now only supported for transfer fee and replacement fee
		String txnType = txn.getTxnType();

		if (!txnType.equals(NonConfigurableConstants.PREPAID_TXN_TYPE_REPLACEMENT_FEE)
				&& !txnType.equals(NonConfigurableConstants.PREPAID_TXN_TYPE_TRANSFER_FEE)) {
			throw new WrongValueException("Fail to build direct receipt, unsupported transaction type: " + txnType);
		}

		// the returned amount and GST are in negative value, negate it again when save
		// into payment receipt
		BigDecimal amount = txn.getAmount().negate();
		BigDecimal gst = txn.getGst().negate();
		PmtbProduct product = txn.getPmtbProduct();
		AmtbAccount acct = product.getAmtbAccount();

		BigDecimal paymentAmount = amount.add(gst);

		BmtbPaymentReceipt paymentReceipt = new BmtbPaymentReceipt();
		paymentReceipt.setAmtbAccount(acct);
		paymentReceipt.setPaymentNo(null);
		paymentReceipt.setMstbMasterTableByPaymentMode(ConfigurableConstants.getMasterTable(
				ConfigurableConstants.PAYMENT_MODE, NonConfigurableConstants.PAYMENT_MODE_DIRECT_RECEIPT));

		paymentReceipt.setPaymentAmount(paymentAmount);
		paymentReceipt.setPaymentDate(DateUtil.getCurrentDate());
		paymentReceipt.setReceiptDt(DateUtil.getCurrentTimestamp());
		paymentReceipt.setMstbBankMaster(null);
		paymentReceipt.setMstbBranchMaster(null);

		paymentReceipt.setRemarks(NonConfigurableConstants.PREPAID_TXN_TYPE.get(txnType));
		paymentReceipt.setFmtbBankCode(null);

		paymentReceipt.setExcessAmount(BigDecimal.ZERO);
		paymentReceipt.setBilledFlag(NonConfigurableConstants.BOOLEAN_YN_NO);
		paymentReceipt.setCancelBilledFlag(NonConfigurableConstants.BOOLEAN_YN_NO);

		this.daoHelper.getGenericDao().save(paymentReceipt, user);

		BmtbPaymentReceiptDetail paymentReceiptDetail = new BmtbPaymentReceiptDetail();
		paymentReceiptDetail.setAppliedAmount(paymentReceipt.getPaymentAmount());
		paymentReceiptDetail.setBilledFlag(NonConfigurableConstants.BOOLEAN_NO);
		paymentReceiptDetail.setCancelBilledFlag(NonConfigurableConstants.BOOLEAN_NO);
		paymentReceiptDetail.setBmtbPaymentReceipt(paymentReceipt);
		paymentReceiptDetail.setBmtbInvoiceHeader(null);
		paymentReceiptDetail.setBmtbInvoiceDetail(null);
		paymentReceiptDetail.setPmtbPrepaidCardTxn(txn);

		paymentReceipt.setExcessAmount(BigDecimal.ZERO);
		paymentReceiptDetail.setDeductedCreditBalance(paymentReceipt.getPaymentAmount());

		paymentReceipt.setPrepaidFlag(NonConfigurableConstants.BOOLEAN_YES);

		this.daoHelper.getGenericDao().save(paymentReceiptDetail, user);

	}

	public BmtbInvoiceHeader getPrepaidInvoiceHeader(long invoiceHeaderNo) {
		return this.daoHelper.getPrepaidDao().getPrepaidInvoiceHeader(invoiceHeaderNo);
	}

	public void cancelPrepaidInvoice(BmtbInvoiceHeader header, String user) {

		if (!NonConfigurableConstants.getBoolean(header.getPrepaidFlag())) {
			throw new WrongValueException("Currently cancel invoice not support for non prepaid invoice.");
		}

		// set outstanding amount to Zero
		logger.info("previous outstanding amount of invoice: " + header.getInvoiceHeaderNo() + " is "
				+ header.getOutstandingAmount());
		header.setOutstandingAmount(BigDecimal.ZERO);
		header.setInvoiceStatus(NonConfigurableConstants.INVOICE_STATUS_CANCELLED);
		header.setCancelDt(DateUtil.getCurrentTimestamp());
		this.daoHelper.getGenericDao().update(header, user);

		// Create one receipt detail for each invoice detail
		Set<BmtbInvoiceSummary> invoiceSummaries = header.getBmtbInvoiceSummaries();
		for (BmtbInvoiceSummary invoiceSummary : invoiceSummaries) {
			Set<BmtbInvoiceDetail> invoiceDetails = invoiceSummary.getBmtbInvoiceDetails();
			for (BmtbInvoiceDetail invoiceDetail : invoiceDetails) {
				// update outstanding amount
				invoiceDetail.setOutstandingAmount(new BigDecimal(0));
				this.daoHelper.getGenericDao().update(invoiceDetail, user);
			}
		}

		// Credit note is created no matter what is the new value.
		BmtbNote note = new BmtbNote();

		note.setAmtbAccount(header.getAmtbAccountByDebtTo());
		note.setRemarks(null);
		note.setGst(BigDecimal.ZERO);
		note.setDiscount(BigDecimal.ZERO);
		note.setBmtbInvoiceHeader(header);
		note.setNoteAmount(header.getNewBalance());
		note.setNoteType(NonConfigurableConstants.NOTE_TYPE_CREDIT);
		note.setFmtbTransactionCode(null);
		note.setMstbMasterTableByReason(null);
		note.setCreatedBy(user);

		note.setStatus(NonConfigurableConstants.NOTE_STATUS_PENDING);
		note.setCreatedDt(DateUtil.getCurrentTimestamp());
		note.setNoteTxnType(NonConfigurableConstants.NOTE_TXN_TYPE_CANCEL_INV);
		note.setAdminFee(BigDecimal.ZERO);
		note.setProdDis(BigDecimal.ZERO);
		note.setPromoDis(BigDecimal.ZERO);

		// the auto created credit note will be marked as billed and cannot be
		// cancelled.
		note.setStatus(NonConfigurableConstants.NOTE_STATUS_BILLED);

		this.daoHelper.getGenericDao().save(note);

		// after creating the trip-based note, the note flow required to be inserted as
		// well.
		BmtbNoteFlow newToPendingFlow = new BmtbNoteFlow();
		newToPendingFlow.setBmtbNote(note);
		newToPendingFlow.setFromStatus(NonConfigurableConstants.NOTE_STATUS_NEW);
		newToPendingFlow.setToStatus(NonConfigurableConstants.NOTE_STATUS_PENDING);
		newToPendingFlow
				.setSatbUser((SatbUser) this.daoHelper.getUserDao().get(SatbUser.class, CommonWindow.getUserId()));
		newToPendingFlow.setFlowDt(DateUtil.convertDateToTimestamp(DateUtil.getCurrentDate()));
		this.save(newToPendingFlow);

		BmtbNoteFlow pendingToApproveFlow = new BmtbNoteFlow();
		pendingToApproveFlow.setBmtbNote(note);
		pendingToApproveFlow.setFromStatus(NonConfigurableConstants.NOTE_STATUS_PENDING);
		pendingToApproveFlow.setToStatus(NonConfigurableConstants.NOTE_STATUS_ACTIVE);
		pendingToApproveFlow
				.setSatbUser((SatbUser) this.daoHelper.getUserDao().get(SatbUser.class, CommonWindow.getUserId()));
		pendingToApproveFlow.setFlowDt(DateUtil.convertDateToTimestamp(DateUtil.getCurrentDate()));
		this.save(pendingToApproveFlow);

	}

	private void validateRequestBeenApproved(PmtbPrepaidReq req) {

		// to avoid multiple approved
		if (NonConfigurableConstants.getBoolean(req.getApprovalRequired())) {

			if (!req.getApprovalStatus().equals(NonConfigurableConstants.PREPAID_APPROVAL_STATUS_PENDING)) {
				throw new WrongValueException(
						"Failed to approve/reject request. The request might has been approved/rejected.");
			}
		}

	}

	public void commonApprove(PmtbPrepaidReq req, String user) throws NetException, IOException, ExpectedException {

		super.update(req);

		if (req instanceof PmtbIssuanceReq) {
			approveIssuanceRequest((PmtbIssuanceReq) req, user);

		} else if (req instanceof PmtbTopUpReq) {
			approveTopUpRequest((PmtbTopUpReq) req, user);

		} else if (req instanceof PmtbTransferReq) {
			approveTransferRequest((PmtbTransferReq) req, user);

		} else if (req instanceof PmtbAdjustmentReq) {
			approveAdjustmentRequest((PmtbAdjustmentReq) req, user);

		} else if (req instanceof PmtbExtBalExpDateReq) {
			approveExtendBalanceExpiryDateRequest((PmtbExtBalExpDateReq) req, user);
		}

	}

	public void commonReject(PmtbPrepaidReq req, String user) {

		super.update(req);

		if (req instanceof PmtbAdjustmentReq) {
			rejectAdjustmentRequest((PmtbAdjustmentReq) req, user);

		} else {
			rejectPrepaidRequest(req, user);
		}

	}

	public PmtbPrepaidReq getPrepaidRequestWithInvoiceHeader(Long invoiceHeaderNo) {

		return this.daoHelper.getPrepaidDao().getPrepaidRequestWithInvoiceHeader(invoiceHeaderNo);
	}

	public String reddotSendEmail(String reddotInvoiceNo) throws IOException
	{
		String responseFromReddot = "";

		Map properties = (Map)SpringUtil.getBean("webserviceProperties");
		//retrieve properties value
		String authorization = (String)properties.get("ws.authorization");
		String authorizationUser = (String) properties.get("ws.authorizationUser");
		String authorizationPW = (String) properties.get("ws.authorizationPW");
		String sendEmailUrl = (String) properties.get("ws.sendEmailUrl");
		JSONObject mainJson = new JSONObject();

		mainJson.put("type", "email");
		mainJson.put("invoiceNo", reddotInvoiceNo);
		//API TO CALL
		URL obj = new URL(sendEmailUrl);

		Security.addProvider(new BouncyCastleProvider());
		HttpsURLConnection postConnection = (HttpsURLConnection) obj.openConnection();

		postConnection.setRequestMethod("POST");

		postConnection.setRequestProperty("Content-Type", "application/json");

		// retrieve properties val
		String auth = authorizationUser + ":" + authorizationPW;

		byte[] encodedAuth = Base64.encodeBase64(auth.getBytes());

		postConnection.setSSLSocketFactory(new TSLSocketConnectionFactory());
		postConnection.setRequestProperty("Authorization", "Basic " + new String(encodedAuth));
		postConnection.setDoOutput(true);
		logger.info("json email : "+mainJson.toString());
		OutputStream os = postConnection.getOutputStream();
		os.write(mainJson.toString().getBytes());
		os.flush();
		os.close();
		int responseCode = postConnection.getResponseCode();
		System.out.println("POST Response Code :  " + responseCode);
		System.out.println("POST Response Message : " + postConnection.getResponseMessage());

		if (responseCode == HttpURLConnection.HTTP_CREATED) { //success

			BufferedReader in = new BufferedReader(new InputStreamReader(postConnection.getInputStream()));
			String inputLine;
			StringBuffer response = new StringBuffer();
			while ((inputLine = in .readLine()) != null) {
				response.append(inputLine);
			} in .close();
			// print result
			System.out.println(response.toString());
			responseFromReddot = "Success";
		} else {
			System.out.println("POST NOT WORKED");
			responseFromReddot = "fail";
		}


		return responseFromReddot;
	}
}
