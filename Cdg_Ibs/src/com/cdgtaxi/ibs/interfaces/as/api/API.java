package com.cdgtaxi.ibs.interfaces.as.api;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.naming.OperationNotSupportedException;
import javax.xml.ws.WebServiceException;

import org.apache.log4j.Logger;

import com.cdgtaxi.ibs.common.NonConfigurableConstants;
import com.cdgtaxi.ibs.common.dao.GenericDao;
import com.cdgtaxi.ibs.common.dao.Sequence;
import com.cdgtaxi.ibs.common.exception.ASWebserviceConnectionException;
import com.cdgtaxi.ibs.common.exception.ASWebserviceException;
import com.cdgtaxi.ibs.common.model.AmtbAccount;
import com.cdgtaxi.ibs.common.model.PmtbProduct;
import com.cdgtaxi.ibs.common.model.PmtbProductType;
import com.cdgtaxi.ibs.interfaces.as.model.IttbAsAddAcctReq;
import com.cdgtaxi.ibs.interfaces.as.model.IttbAsAddNegProdReq;
import com.cdgtaxi.ibs.interfaces.as.model.IttbAsAddProdReq;
import com.cdgtaxi.ibs.interfaces.as.model.IttbAsMasterReq;
import com.cdgtaxi.ibs.interfaces.as.model.IttbAsRevAcctTxnReq;
import com.cdgtaxi.ibs.interfaces.as.model.IttbAsRevProdTxnReq;
import com.cdgtaxi.ibs.interfaces.as.model.IttbAsUpdAcctReq;
import com.cdgtaxi.ibs.interfaces.as.model.IttbAsUpdBillAcctReq;
import com.cdgtaxi.ibs.interfaces.as.model.IttbAsUpdBillProdReq;
import com.cdgtaxi.ibs.interfaces.as.model.IttbAsUpdPayAcctReq;
import com.cdgtaxi.ibs.interfaces.as.model.IttbAsUpdPayProdReq;
import com.cdgtaxi.ibs.interfaces.as.model.IttbAsUpdProdReq;
import com.cdgtaxi.ibs.interfaces.as.model.IttbAsUpdUsageReq;
import com.cdgtaxi.ibs.interfaces.as.model.ProductResult;
import com.cdgtaxi.ibs.interfaces.as.webservice.client.ComfortDelGroImpl;
import com.cdgtaxi.ibs.interfaces.as.webservice.client.ComfortDelGroImplService;
import com.cdgtaxi.ibs.util.DateUtil;
import com.cdgtaxi.ibs.util.LoggerUtil;
import com.cdgtaxi.ibs.util.StringUtil;

public class API {
	
	private static final Logger logger = Logger.getLogger(API.class);
	public static final String SYNCHRONOUS = "S";
	public static final String ASYNCHRONOUS = "A";
	
	public static final String ACCOUNT_TYPE_CORPORATE = "CO";
	public static final String ACCOUNT_TYPE_DIVISION = "DI";
	public static final String ACCOUNT_TYPE_DEPARTMENT = "DP";
	public static final String ACCOUNT_TYPE_PERSONAL = "PS";
	public static final String ACCOUNT_TYPE_SUBAPPLICANT = "SA";
	
	public static final String PRODUCT_REASON_CODE_EXPIRED 	= "E";
	public static final String PRODUCT_REASON_CODE_LOST		= "L";
	public static final String PRODUCT_REASON_CODE_DEFAULT	= "D";
	
	// MAP keys for product request
	public static final String PRODUCT_CARD_NO = "cardNo";
	public static final String PRODUCT_STATUS = "status";
	public static final String PRODUCT_NEW_CREDIT_BALANCE = "newCreditBalance";
	public static final String PRODUCT_EXPIRY_DATE = "expiryDate";
	public static final String PRODUCT_ACCOUNT_ID = "accountId";
	public static final String PRODUCT_REASON_CODE = "reasonCode";
	public static final String PRODUCT_UPDATED_BY = "updateBy";
	public static final String PRODUCT_TRANSFER_MODE = "transferMode";
	public static final String PRODUCT_TYPE_ID = "productTypeId";
	public static final String PRODUCT_FIXED_VALUE = "fixedValue";
	public static final String PRODUCT_CREDIT_BALANCE = "creditBalance";
	public static final String PRODUCT_CREATE_BY = "createBy";
	public static final String PRODUCT_OFFLINE_COUNT = "offlineCount";
	public static final String PRODUCT_OFFLINE_AMOUNT = "offlineAmount";
	public static final String PRODUCT_OFFLINE_TXN_AMOUNT = "offlineTxnAmount";
	public static final String PRODUCT_FORCE_ONLINE = "forceOnline";
	
	private static GenericDao genericDao;
	public GenericDao getGenericDao() { return genericDao; }
	public void setGenericDao(GenericDao genericDao) { this.genericDao = genericDao; }
	
	/**
	 * [Synchronous] use this method to create a new product type
	 * @param productTypeId
	 * @param productTypeName
	 * @param binRange
	 * @param subBinRange
	 * @param oneTimeUsageFlag
	 * @param fixedValueFlag
	 * @param creditLimitFlag
	 * @param negativeFileCheckFlag
	 * @param status A - Active, I - Inactive
	 * @param createBy
	 * @return Error message from AS, otherwise empty string
	 * @throws Exception
	 */
	
	public static void createProductType(String productTypeId, String productTypeName, 
			String binRange, String subBinRange, String oneTimeUsageFlag, 
			String fixedValueFlag, String creditLimitFlag, String negativeFileCheckFlag, 
			String expiryDateFlag, String prepaid, String status, String createBy, String contactless) throws Exception{
		
		ComfortDelGroImpl proxy = null;
		try{
			proxy = (ComfortDelGroImpl)  new ComfortDelGroImplService().getComfortDelGroImplPort();
		}
		catch(WebServiceException wse){
			wse.printStackTrace();
			throw new ASWebserviceConnectionException();
		}
		
		String value = proxy.handShake();
		if(value.equals("ComfortDelGro"))
			logger.info("HAND SHAKE SUCCESSFUL!");
		else
			throw new ASWebserviceConnectionException();
		
		//NEED TO CAST 'NA' as 'N'
		binRange = binRange.equals(NonConfigurableConstants.BOOLEAN_NA) ? "" : binRange;
		subBinRange = subBinRange.equals(NonConfigurableConstants.BOOLEAN_NA) ? "" : subBinRange;
		oneTimeUsageFlag = oneTimeUsageFlag.equals(NonConfigurableConstants.BOOLEAN_NA) ? NonConfigurableConstants.BOOLEAN_NO : oneTimeUsageFlag;
		fixedValueFlag = fixedValueFlag.equals(NonConfigurableConstants.BOOLEAN_NA) ? NonConfigurableConstants.BOOLEAN_NO : fixedValueFlag;
		creditLimitFlag = creditLimitFlag.equals(NonConfigurableConstants.BOOLEAN_NA) ? NonConfigurableConstants.BOOLEAN_NO : creditLimitFlag;
		negativeFileCheckFlag = negativeFileCheckFlag.equals(NonConfigurableConstants.BOOLEAN_NA) ? NonConfigurableConstants.BOOLEAN_NO : negativeFileCheckFlag;
		expiryDateFlag = expiryDateFlag.equals(NonConfigurableConstants.BOOLEAN_NA) ? NonConfigurableConstants.BOOLEAN_NO : expiryDateFlag;
		
		String errorMsg = proxy.createProductType(null, productTypeId, productTypeName, binRange, subBinRange, oneTimeUsageFlag, 
				fixedValueFlag, creditLimitFlag, negativeFileCheckFlag, expiryDateFlag, prepaid, status, createBy, contactless);
		
		if(errorMsg!=null && errorMsg.length()>0)
			throw new ASWebserviceException("[AS Webservice] "+errorMsg);
	}
	
	/**
	 * Added by Yiming. To enhance the speed of AS-IBS interfacing.
	 * [Asynchronous] use this method to create a product
	 * @param cardNo
	 * @param accountId
	 * @param productTypeId
	 * @param fixedValue
	 * @param creditBalance
	 * @param expiryDate YYMM
	 * @param status
	 * @param reasonCode
	 * @param createBy
	 * @throws Exception
	 */
	//Brian: 200091106: For retag product, need to send to AS as UPDATE request instead of CREATE request
	//public static void createProducts(List<Map<String, String>> details, ) throws Exception{
	//Add new param List<PmtbProduct> retaggedProducts for comparison purpose.
	//Brian: 200091106 Ends
	public static void createProducts(List<Map<String, String>> details, List<PmtbProduct> recycledProducts) throws Exception{
		logger.info("create products size = " + details.size());
		List<IttbAsMasterReq> requests = new ArrayList<IttbAsMasterReq>();
		List<IttbAsAddProdReq> createRequests = new ArrayList<IttbAsAddProdReq>();
		List<IttbAsUpdProdReq> updateRequests = new ArrayList<IttbAsUpdProdReq>();
		for(Map<String, String> detail : details){
			IttbAsMasterReq masterRequest = new IttbAsMasterReq();
			masterRequest.setReqId(DateUtil.getYearMonthDay()+StringUtil.appendLeft(genericDao.getNextSequenceNo(Sequence.REQUEST_NO_SEQUENCE).toString(), 6, "0"));
			masterRequest.setReqStatus(IttbAsMasterReq.PENDING);
			masterRequest.setReqDate(DateUtil.getCurrentTimestamp());
			
			boolean updateProductRequest = false;
			//Detect if this is a retag product
			if(recycledProducts!=null && recycledProducts.size()>0){
				for(PmtbProduct recycledProduct :recycledProducts){
					if(recycledProduct!=null && recycledProduct.getCardNo()!=null && recycledProduct.getCardNo().equals(detail.get(PRODUCT_CARD_NO))) {
						
						updateProductRequest = true;
						logger.info("retagged product - sending UPDATE PRODUCT REQUEST instead for cardno " + detail.get(PRODUCT_CARD_NO) + "!");
						break;
					}
				}
			}
			
			if(updateProductRequest) {
				IttbAsUpdProdReq request = new IttbAsUpdProdReq();
				masterRequest.setIttbAsUpdProdReq(request);
				masterRequest.setReqTable(IttbAsMasterReq.UPDATE_PRODUCT_REQUEST);
				
				request.setIttbAsMasterReq(masterRequest);
				request.setReqId(masterRequest.getReqId());
				request.setCardNo(detail.get(PRODUCT_CARD_NO));
				request.setAcctId(detail.get(PRODUCT_ACCOUNT_ID));
				//request.setProdTypeId(detail.get(PRODUCT_TYPE_ID));
				//request.setNewFixedValue(detail.get(PRODUCT_FIXED_VALUE));
				request.setNewCreditLimit(detail.get(PRODUCT_CREDIT_BALANCE));
				request.setExpiryDate(detail.get(PRODUCT_EXPIRY_DATE));
				request.setStatus(detail.get(PRODUCT_STATUS));
				request.setReasonCode(detail.get(PRODUCT_REASON_CODE));
				request.setUpdateBy(detail.get(PRODUCT_CREATE_BY));
				request.setOfflineCount(detail.get(PRODUCT_OFFLINE_COUNT));
				request.setOfflineAmount(detail.get(PRODUCT_OFFLINE_AMOUNT));
				request.setOfflineTxnAmount(detail.get(PRODUCT_OFFLINE_TXN_AMOUNT));
				request.setForceOnline(detail.get(PRODUCT_FORCE_ONLINE));
				requests.add(masterRequest);
				updateRequests.add(request); 
			} else {

				IttbAsAddProdReq request = new IttbAsAddProdReq();
				masterRequest.setIttbAsAddProdReq(request);
				masterRequest.setReqTable(IttbAsMasterReq.CREATE_PRODUCT_REQUEST);
				
				request.setIttbAsMasterReq(masterRequest);
				request.setReqId(masterRequest.getReqId());
				request.setCardNo(detail.get(PRODUCT_CARD_NO));
				request.setAcctId(detail.get(PRODUCT_ACCOUNT_ID));
				request.setProdTypeId(detail.get(PRODUCT_TYPE_ID));
				request.setFixedValue(detail.get(PRODUCT_FIXED_VALUE));
				request.setCreditLimit(detail.get(PRODUCT_CREDIT_BALANCE));
				request.setExpiryDate(detail.get(PRODUCT_EXPIRY_DATE));
				request.setStatus(detail.get(PRODUCT_STATUS));
				request.setReasonCode(detail.get(PRODUCT_REASON_CODE));
				request.setCreateBy(detail.get(PRODUCT_CREATE_BY));
				request.setOfflineCount(detail.get(PRODUCT_OFFLINE_COUNT));
				request.setOfflineAmount(detail.get(PRODUCT_OFFLINE_AMOUNT));
				request.setOfflineTxnAmount(detail.get(PRODUCT_OFFLINE_TXN_AMOUNT));
				request.setForceOnline(detail.get(PRODUCT_FORCE_ONLINE));
				requests.add(masterRequest);
				createRequests.add(request); 
				}
		}
		genericDao.saveOrUpdateAll(requests);
		
		if(createRequests!=null && createRequests.size()>0)
			genericDao.saveOrUpdateAll(createRequests);
		
		if(updateRequests!=null && updateRequests.size()>0)
			genericDao.saveOrUpdateAll(updateRequests);
		
	}
	public static void createProductAPI(PmtbProduct product, String userId) throws Exception{
		
		String fixedValue=null;
		String creditBalance=null;
		String expiryDate=null;
		try{
			if(product.getFixedValue()!=null)
				fixedValue=product.getFixedValue().toString();
			if(	product.getCreditBalance()!=null)
				creditBalance=product.getCreditBalance().toString();
			if(product.getExpiryDate()!=null)
				expiryDate=DateUtil.convertDateToStr(product.getExpiryDate(), DateUtil.INTERFACE_AS_PRODUCT_EXPIRY_DATE_FORMAT);
		}catch(Exception e) {
			LoggerUtil.printStackTrace(logger, e);
			throw e;
		
		}
		
		try{
			String status = null;
			AmtbAccount amtbAccount = product.getAmtbAccount();
			
			if(product.getCurrentStatus().equalsIgnoreCase(NonConfigurableConstants.PRODUCT_STATUS_ACTIVE)){
				status = NonConfigurableConstants.AS_REQUEST_PRODUCT_STATUS_ACTIVE;
				
				//for PREPAID
				if(NonConfigurableConstants.getBoolean(product.getExpiredFlag())){
					status = NonConfigurableConstants.AS_REQUEST_PRODUCT_STATUS_INACTIVE;
				} else {
					status = NonConfigurableConstants.AS_REQUEST_PRODUCT_STATUS_ACTIVE;
				}
			}
			else {
				status = NonConfigurableConstants.AS_REQUEST_PRODUCT_STATUS_INACTIVE;
			}
			API.createProduct(
					product.getCardNo(),
					API.formulateAccountId(amtbAccount),
					product.getPmtbProductType().getProductTypeId(),
					fixedValue,
					creditBalance,
					expiryDate,
					status,
					"",
					userId,
					StringUtil.numberToString(product.getOfflineCount()),
					StringUtil.bigDecimalToString(product.getOfflineAmount()),
					StringUtil.bigDecimalToString(product.getOfflineTxnAmount()),
					API.formulateForceOnline(product, product.getPmtbProductType())
				);
		}
		catch(Exception e) {
			LoggerUtil.printStackTrace(logger, e);
			throw e;
		}
	}
	
	
	/**
	 * [Asynchronous] use this method to create a product
	 * @param cardNo
	 * @param accountId
	 * @param productTypeId
	 * @param fixedValue
	 * @param creditBalance
	 * @param expiryDate YYMM
	 * @param status
	 * @param reasonCode
	 * @param createBy
	 * @throws Exception
	 */
	public static void createProduct(String cardNo, String accountId, String productTypeId,
			String fixedValue, String creditBalance, String expiryDate, String status, 
			String reasonCode, String createBy, String offlineCount, String offlineAmount, String offlineTxnAmount, String forceOnline) throws Exception{
		
		logger.info("createProduct - "+cardNo+","+accountId+","+productTypeId+","+fixedValue+","+
				creditBalance+","+expiryDate+","+status+","+reasonCode+","+createBy+","+offlineCount+","+offlineAmount+","+offlineTxnAmount+","+forceOnline);
		
		IttbAsMasterReq masterRequest = new IttbAsMasterReq();
		masterRequest.setReqId(DateUtil.getYearMonthDay()+StringUtil.appendLeft(genericDao.getNextSequenceNo(Sequence.REQUEST_NO_SEQUENCE).toString(), 6, "0"));
		masterRequest.setReqTable(IttbAsMasterReq.CREATE_PRODUCT_REQUEST);
		masterRequest.setReqStatus(IttbAsMasterReq.PENDING);
		masterRequest.setReqDate(DateUtil.getCurrentTimestamp());
		genericDao.save(masterRequest);
		
		IttbAsAddProdReq request = new IttbAsAddProdReq();
		request.setIttbAsMasterReq(masterRequest);
		request.setReqId(masterRequest.getReqId());
		request.setCardNo(cardNo);
		request.setAcctId(accountId);
		request.setProdTypeId(productTypeId);
		request.setFixedValue(fixedValue);
		request.setCreditLimit(creditBalance);
		request.setExpiryDate(expiryDate);
		request.setStatus(status);
		request.setReasonCode(reasonCode);
		request.setCreateBy(createBy);
		request.setOfflineCount(offlineCount);
		request.setOfflineAmount(offlineAmount);
		request.setOfflineTxnAmount(offlineTxnAmount);
		request.setForceOnline(forceOnline);
		genericDao.save(request);
	}
	/**
	 * Added by Yiming. To enhance the speed of AS-IBS interfacing.
	 * [Synchronous  / Asynchronous] use this method to update a product
	 * @param cardNo
	 * @param status
	 * @param newCreditBalance
	 * @param expiryDate YYMM
	 * @param accountId
	 * @param reasonCode
	 * @param updateBy
	 * @param transferMode A - Asynchronous, S - Synchronous
	 * @return Error message from AS, otherwise empty string
	 * @throws Exception
	 */
	public static void updateProducts(List<Map<String, String>> details) throws Exception{
		logger.info("update products size = " + details.size());
		List<IttbAsMasterReq> requests = new ArrayList<IttbAsMasterReq>();
		List<IttbAsUpdProdReq> updateRequests = new ArrayList<IttbAsUpdProdReq>();
		for(Map<String, String> detail : details){
			if(detail.get("transferMode") == null){
				throw new IllegalArgumentException("Transfer Mode cannot be null");
			}else if(detail.get("transferMode").equals(API.SYNCHRONOUS)){
				throw new OperationNotSupportedException("This operation is not supported for IBS1");
				/*
				 * TODO Calling webservice will return a message.
				 * If message empty = no error
				 * Else got error, throw the exception
				 */
			}else if(detail.get("transferMode").equals(API.ASYNCHRONOUS)){
				IttbAsMasterReq masterRequest = new IttbAsMasterReq();
				masterRequest.setReqId(DateUtil.getYearMonthDay()+StringUtil.appendLeft(genericDao.getNextSequenceNo(Sequence.REQUEST_NO_SEQUENCE).toString(), 6, "0"));
				masterRequest.setReqTable(IttbAsMasterReq.UPDATE_PRODUCT_REQUEST);
				masterRequest.setReqStatus(IttbAsMasterReq.PENDING);
				masterRequest.setReqDate(DateUtil.getCurrentTimestamp());
				IttbAsUpdProdReq request = new IttbAsUpdProdReq();
				request.setIttbAsMasterReq(masterRequest);
				request.setReqId(masterRequest.getReqId());
				request.setCardNo(detail.get("cardNo"));
				request.setStatus(detail.get("status"));
				request.setNewCreditLimit(detail.get("newCreditBalance"));
				request.setExpiryDate(detail.get("expiryDate"));
				request.setAcctId(detail.get("accountId"));
				request.setReasonCode(detail.get("reasonCode"));
				request.setUpdateBy(detail.get("updateBy"));
				request.setOfflineCount(detail.get(API.PRODUCT_OFFLINE_COUNT));
				request.setOfflineAmount(detail.get(API.PRODUCT_OFFLINE_AMOUNT));
				request.setOfflineTxnAmount(detail.get(API.PRODUCT_OFFLINE_TXN_AMOUNT));
				request.setForceOnline(detail.get(API.PRODUCT_FORCE_ONLINE));
				masterRequest.setIttbAsUpdProdReq(request);
				requests.add(masterRequest);
				updateRequests.add(request);
			}else{
				throw new OperationNotSupportedException("Operation Supported: A-Asynchronous S-Synchronous");
			}
		}
		genericDao.saveOrUpdateAll(requests);
		genericDao.saveOrUpdateAll(updateRequests);
	}
	
	public static void updateProductAPIActive(PmtbProduct product, String userId) throws Exception {
		
		AmtbAccount amtbAccount = product.getAmtbAccount();
		PmtbProductType pmtbProductType = product.getPmtbProductType();
		String status = NonConfigurableConstants.AS_REQUEST_PRODUCT_STATUS_ACTIVE;
		
		//for PREPAID
		if(NonConfigurableConstants.getBoolean(product.getExpiredFlag())){
			status = NonConfigurableConstants.AS_REQUEST_PRODUCT_STATUS_INACTIVE;
		} else {
			status = NonConfigurableConstants.AS_REQUEST_PRODUCT_STATUS_ACTIVE;
		}
		
		String creditBalance = (product.getCreditBalance()!=null) ? product.getCreditBalance().toString() : "";
	
		API.updateProduct(
				product.getCardNo(),
				status,
				creditBalance,
				DateUtil.convertDateToStr(product.getExpiryDate(), DateUtil.INTERFACE_AS_PRODUCT_EXPIRY_DATE_FORMAT),
				API.formulateAccountId(amtbAccount),
				"",
				userId,
				StringUtil.numberToString(product.getOfflineCount()),
				StringUtil.bigDecimalToString(product.getOfflineAmount()),
				StringUtil.bigDecimalToString(product.getOfflineTxnAmount()),
				API.formulateForceOnline(product, pmtbProductType),
				NonConfigurableConstants.AS_REQUEST_TRANSFER_MODE_ASYNCHRONOUS);
		
	}
	
	
	/**
	 * [Synchronous  / Asynchronous] use this method to update a product
	 * @param cardNo
	 * @param status
	 * @param newCreditBalance
	 * @param expiryDate YYMM
	 * @param accountId
	 * @param reasonCode
	 * @param updateBy
	 * @param transferMode A - Asynchronous, S - Synchronous
	 * @return Error message from AS, otherwise empty string
	 * @throws Exception
	 */
	public static void updateProduct(String cardNo, String status, String newCreditBalance, String expiryDate,
			String accountId, String reasonCode, String updateBy, String offlineCount, String offlineAmount, String offlineTxnAmount, String forceOnline, String transferMode) throws Exception{
		
		logger.info("updateProduct - "+cardNo+","+status+","+newCreditBalance+","+expiryDate+","+
				accountId+","+reasonCode+","+updateBy+","+offlineCount+","+offlineAmount+","+offlineTxnAmount+","+forceOnline+","+transferMode);
		
		if(transferMode == null){
			throw new IllegalArgumentException("Transfer Mode cannot be null");
		}
		else if(transferMode.equals(API.SYNCHRONOUS)){
			throw new OperationNotSupportedException("This operation is not supported for IBS1");
			/*
			 * TODO Calling webservice will return a message.
			 * If message empty = no error
			 * Else got error, throw the exception
			 */
		}
		else if(transferMode.equals(API.ASYNCHRONOUS)){
				
			IttbAsMasterReq masterRequest = new IttbAsMasterReq();
			masterRequest.setReqId(DateUtil.getYearMonthDay()+StringUtil.appendLeft(genericDao.getNextSequenceNo(Sequence.REQUEST_NO_SEQUENCE).toString(), 6, "0"));
			masterRequest.setReqTable(IttbAsMasterReq.UPDATE_PRODUCT_REQUEST);
			masterRequest.setReqStatus(IttbAsMasterReq.PENDING);
			masterRequest.setReqDate(DateUtil.getCurrentTimestamp());
			genericDao.save(masterRequest);
			
			IttbAsUpdProdReq request = new IttbAsUpdProdReq();
			request.setIttbAsMasterReq(masterRequest);
			request.setReqId(masterRequest.getReqId());
			request.setCardNo(cardNo);
			request.setStatus(status);
			request.setNewCreditLimit(newCreditBalance);
			request.setExpiryDate(expiryDate);
			request.setAcctId(accountId);
			request.setReasonCode(reasonCode);
			request.setUpdateBy(updateBy);
			request.setOfflineCount(offlineCount);
			request.setOfflineAmount(offlineAmount);
			request.setOfflineTxnAmount(offlineTxnAmount);
			request.setForceOnline(forceOnline);
			genericDao.save(request);
			
		}
		else{
			throw new OperationNotSupportedException("Operation Supported: A-Asynchronous S-Synchronous");
		}
	}
	
	/**
	 * [Asynchronous] use this method to update product billing information
	 * @param cardNo
	 * @param newCreditBalance
	 * @param totalTxnAmountBilled
	 * @param accountId
	 * @param updateBy
	 * @throws Exception
	 */
	public static void updateProductBillingInformation(String cardNo, String newCreditBalance, 
			String totalTxnAmountBilled, String accountId, String updateBy) throws Exception{
		
		logger.info("updateProductBillingInformation - "+cardNo+","+newCreditBalance+","+
				totalTxnAmountBilled+","+accountId+","+updateBy);
		
		IttbAsMasterReq masterRequest = new IttbAsMasterReq();
		masterRequest.setReqId(DateUtil.getYearMonthDay()+StringUtil.appendLeft(genericDao.getNextSequenceNo(Sequence.REQUEST_NO_SEQUENCE).toString(), 6, "0"));
		masterRequest.setReqTable(IttbAsMasterReq.UPDATE_PROD_BILLING_REQUEST);
		masterRequest.setReqStatus(IttbAsMasterReq.PENDING);
		masterRequest.setReqDate(DateUtil.getCurrentTimestamp());
		genericDao.save(masterRequest);
		
		IttbAsUpdBillProdReq request = new IttbAsUpdBillProdReq();
		request.setIttbAsMasterReq(masterRequest);
		request.setReqId(masterRequest.getReqId());
		request.setCardNo(cardNo);
		request.setNewCreditLimit(newCreditBalance);
		request.setTotalTxnAmtBilled(totalTxnAmountBilled);
		request.setAcctId(accountId);
		request.setUpdateBy(updateBy);
		genericDao.save(request);
	}
	
	/**
	 * [Asynchronous] use this method to update product credit limit
	 * @param cardNo
	 * @param newCreditBalance
	 * @param accountId
	 * @param updateBy
	 * @throws Exception
	 */
	public static void updateProductPayment(String cardNo, String newCreditBalance,
			String accountId, String updateBy) throws Exception{
		
		logger.info("updateProductPayment - "+cardNo+","+newCreditBalance+","+
				accountId+","+updateBy);
		
		IttbAsMasterReq masterRequest = new IttbAsMasterReq();
		masterRequest.setReqId(DateUtil.getYearMonthDay()+StringUtil.appendLeft(genericDao.getNextSequenceNo(Sequence.REQUEST_NO_SEQUENCE).toString(), 6, "0"));
		masterRequest.setReqTable(IttbAsMasterReq.UPDATE_PROD_PAYMENT_REQUEST);
		masterRequest.setReqStatus(IttbAsMasterReq.PENDING);
		masterRequest.setReqDate(DateUtil.getCurrentTimestamp());
		genericDao.save(masterRequest);
		
		IttbAsUpdPayProdReq request = new IttbAsUpdPayProdReq();
		request.setIttbAsMasterReq(masterRequest);
		request.setReqId(masterRequest.getReqId());
		request.setCardNo(cardNo);
		request.setNewCreditLimit(newCreditBalance);
		request.setAcctId(accountId);
		request.setUpdateBy(updateBy);
		genericDao.save(request);
	}
	
	/**
	 * [Asynchronous] use this method to create an account
	 * @param accountId
	 * @param accountType
	 * @param creditBalance
	 * @param parentId
	 * @param createBy
	 * @throws Exception
	 */
	public static void createAccount(String accountId, String accountType, String creditBalance,
			String parentId, String createBy) throws Exception{
		
		logger.info("createAccount - "+accountId+","+accountType+","+creditBalance+","+parentId+","+createBy);
		
		IttbAsMasterReq masterRequest = new IttbAsMasterReq();
		masterRequest.setReqId(DateUtil.getYearMonthDay()+StringUtil.appendLeft(genericDao.getNextSequenceNo(Sequence.REQUEST_NO_SEQUENCE).toString(), 6, "0"));
		masterRequest.setReqTable(IttbAsMasterReq.CREATE_ACCOUNT_REQUEST);
		masterRequest.setReqStatus(IttbAsMasterReq.PENDING);
		masterRequest.setReqDate(DateUtil.getCurrentTimestamp());
		genericDao.save(masterRequest);
		
		IttbAsAddAcctReq request = new IttbAsAddAcctReq();
		request.setIttbAsMasterReq(masterRequest);
		request.setReqId(masterRequest.getReqId());
		request.setAcctId(accountId);
		request.setAcctType(accountType);
		request.setCreditLimit(creditBalance);
		request.setParentId(parentId);
		request.setCreateBy(createBy);
		genericDao.save(request);
	}
	
	/**
	 * [Synchronous] use this method to update an account
	 * @param accountId
	 * @param parentId
	 * @param newCreditBalance
	 * @param updateBy
	 * @param transferMode A - Asynchronous, S - Synchronous
	 * @return Error message from AS, otherwise empty string
	 * @throws Exception
	 */
	public static void updateAccount(String accountId, String newCreditBalance, String parentId, String updateBy, String transferMode) throws Exception{
		
		logger.info("updateAccount - "+accountId+","+newCreditBalance+","+parentId+","+updateBy+","+transferMode);
		
		if(transferMode == null){
			throw new IllegalArgumentException("Transfer Mode cannot be null");
		}
		else if(transferMode.equals(API.SYNCHRONOUS)){
			throw new OperationNotSupportedException("This operation is not supported for IBS1");
			/*
			 * TODO Calling webservice will return a message.
			 * If message empty = no error
			 * Else got error, throw the exception
			 */
		}
		else if(transferMode.equals(API.ASYNCHRONOUS)){
				
			IttbAsMasterReq masterRequest = new IttbAsMasterReq();
			masterRequest.setReqId(DateUtil.getYearMonthDay()+StringUtil.appendLeft(genericDao.getNextSequenceNo(Sequence.REQUEST_NO_SEQUENCE).toString(), 6, "0"));
			masterRequest.setReqTable(IttbAsMasterReq.UPDATE_ACCOUNT_REQUEST);
			masterRequest.setReqStatus(IttbAsMasterReq.PENDING);
			masterRequest.setReqDate(DateUtil.getCurrentTimestamp());
			genericDao.save(masterRequest);
			
			IttbAsUpdAcctReq request = new IttbAsUpdAcctReq();
			request.setIttbAsMasterReq(masterRequest);
			request.setReqId(masterRequest.getReqId());
			request.setAcctId(accountId);
			request.setNewCreditLimit(newCreditBalance);
			request.setParentId(parentId);
			request.setUpdateBy(updateBy);
			genericDao.save(request);
			
		}
		else{
			throw new OperationNotSupportedException("Operation Supported: A-Asynchronous S-Synchronous");
		}
	}
	
	/**
	 * [Asynchronous] use this method to update account billing information
	 * @param accountId
	 * @param newCreditBalance
	 * @param totalTxnAmountBilled
	 * @param parentAccountId
	 * @param updateBy
	 * @throws Exception
	 */
	public static void updateAccountBillingInformation(String accountId, String newCreditBalance, 
			String totalTxnAmountBilled, String parentAccountId, String updateBy) throws Exception{
		
		logger.info("updateAccountBillingInformation - "+accountId+","+newCreditBalance+","+
				totalTxnAmountBilled+","+parentAccountId+","+updateBy);
		
		IttbAsMasterReq masterRequest = new IttbAsMasterReq();
		masterRequest.setReqId(DateUtil.getYearMonthDay()+StringUtil.appendLeft(genericDao.getNextSequenceNo(Sequence.REQUEST_NO_SEQUENCE).toString(), 6, "0"));
		masterRequest.setReqTable(IttbAsMasterReq.UPDATE_ACCT_BILLING_REQUEST);
		masterRequest.setReqStatus(IttbAsMasterReq.PENDING);
		masterRequest.setReqDate(DateUtil.getCurrentTimestamp());
		genericDao.save(masterRequest);
		
		IttbAsUpdBillAcctReq request = new IttbAsUpdBillAcctReq();
		request.setIttbAsMasterReq(masterRequest);
		request.setReqId(masterRequest.getReqId());
		request.setAcctId(accountId);
		request.setNewCreditLimit(newCreditBalance);
		request.setTotalTxnAmtBilled(totalTxnAmountBilled);
		request.setParentId(parentAccountId);
		request.setUpdateBy(updateBy);
		genericDao.save(request);
			
	}
	
	/**
	 * [Asynchronous] use this method to update account credit limit
	 * @param accountId
	 * @param newCreditBalance
	 * @param parentAccountId
	 * @param updateBy
	 * @throws Exception
	 */
	public static void updateAccountPayment(String accountId, String newCreditBalance, 
			String parentAccountId, String updateBy) throws Exception{
		
		logger.info("updateAccountPayment - "+accountId+","+newCreditBalance+","+
				parentAccountId+","+updateBy);
		
		IttbAsMasterReq masterRequest = new IttbAsMasterReq();
		masterRequest.setReqId(DateUtil.getYearMonthDay()+StringUtil.appendLeft(genericDao.getNextSequenceNo(Sequence.REQUEST_NO_SEQUENCE).toString(), 6, "0"));
		masterRequest.setReqTable(IttbAsMasterReq.UPDATE_ACCT_PAYMENT_REQUEST);
		masterRequest.setReqStatus(IttbAsMasterReq.PENDING);
		masterRequest.setReqDate(DateUtil.getCurrentTimestamp());
		genericDao.save(masterRequest);
		
		IttbAsUpdPayAcctReq request = new IttbAsUpdPayAcctReq();
		request.setIttbAsMasterReq(masterRequest);
		request.setReqId(masterRequest.getReqId());
		request.setAcctId(accountId);
		request.setNewCreditLimit(newCreditBalance);
		request.setParentId(parentAccountId);
		request.setUpdateBy(updateBy);
		genericDao.save(request);
	}
	
	/**
	 * [Asynchronous] use this product to create/delete a negative product
	 * @param cardNo
	 * @param status N - New Product, D - Delete Product
	 * @param createBy
	 * @throws Exception
	 */
	public static void createNegativeProduct(String cardNo, String status, String createBy) throws Exception{
		
		logger.info("createNegativeProduct - "+cardNo+","+status+","+createBy);
			
		IttbAsMasterReq masterRequest = new IttbAsMasterReq();
		masterRequest.setReqId(DateUtil.getYearMonthDay()+StringUtil.appendLeft(genericDao.getNextSequenceNo(Sequence.REQUEST_NO_SEQUENCE).toString(), 6, "0"));
		masterRequest.setReqTable(IttbAsMasterReq.CREATE_NEG_PROD_REQUEST);
		masterRequest.setReqStatus(IttbAsMasterReq.PENDING);
		masterRequest.setReqDate(DateUtil.getCurrentTimestamp());
		genericDao.save(masterRequest);
		
		IttbAsAddNegProdReq request = new IttbAsAddNegProdReq();
		request.setIttbAsMasterReq(masterRequest);
		request.setReqId(masterRequest.getReqId());
		request.setCardNo(cardNo);
		request.setStatus(status);
		request.setCreateBy(createBy);
		genericDao.save(request);
	}
	
	/**
	 * [Asynchronous] use this method to reverse a transaction
	 * @param cardNo
	 * @param amountToSubtract
	 * @param accountId
	 * @param updateBy
	 * @throws Exception
	 */
	public static void reversalTransaction(String cardNo, String amountToSubtract, 
			String accountId, String updateBy) throws Exception{
		
		logger.info("reversalTransaction - "+cardNo+","+amountToSubtract+","+
				accountId+","+updateBy);
		
		IttbAsMasterReq masterRequest = new IttbAsMasterReq();
		masterRequest.setReqId(DateUtil.getYearMonthDay()+StringUtil.appendLeft(genericDao.getNextSequenceNo(Sequence.REQUEST_NO_SEQUENCE).toString(), 6, "0"));
		masterRequest.setReqTable(IttbAsMasterReq.REVERSAL_TXN_REQUEST);
		masterRequest.setReqStatus(IttbAsMasterReq.PENDING);
		masterRequest.setReqDate(DateUtil.getCurrentTimestamp());
		genericDao.save(masterRequest);
		
		IttbAsRevProdTxnReq request = new IttbAsRevProdTxnReq();
		request.setIttbAsMasterReq(masterRequest);
		request.setReqId(masterRequest.getReqId());
		request.setCardNo(cardNo);
		request.setTxnAmt(amountToSubtract);
		request.setAcctId(accountId);
		request.setUpdateBy(updateBy);
		genericDao.save(request);
	}
	
	/**
	 * [Asynchronous] use this method to reverse a transaction for account
	 * @param accountId
	 * @param amountToSubtract
	 * @param updateBy
	 * @throws Exception
	 */
	public static void reversalTransactionForAccount(String accountId, String amountToSubtract, 
			String parentId, String updateBy) throws Exception{
		
		logger.info("reversalTransactionForAccount - "+accountId+","+amountToSubtract+","+
				parentId+","+updateBy);
		
		IttbAsMasterReq masterRequest = new IttbAsMasterReq();
		masterRequest.setReqId(DateUtil.getYearMonthDay()+StringUtil.appendLeft(genericDao.getNextSequenceNo(Sequence.REQUEST_NO_SEQUENCE).toString(), 6, "0"));
		masterRequest.setReqTable(IttbAsMasterReq.REVERSAL_TXN_FOR_ACCT_REQUEST);
		masterRequest.setReqStatus(IttbAsMasterReq.PENDING);
		masterRequest.setReqDate(DateUtil.getCurrentTimestamp());
		genericDao.save(masterRequest);
		
		IttbAsRevAcctTxnReq request = new IttbAsRevAcctTxnReq();
		request.setIttbAsMasterReq(masterRequest);
		request.setReqId(masterRequest.getReqId());
		request.setAcctId(accountId);
		request.setTxnAmt(amountToSubtract);
		request.setParentId(parentId);
		request.setUpdateBy(updateBy);
		genericDao.save(request);
	}
	
	/**
	 * [Synchronous  / Asynchronous] use this method to update an OTU product usage
	 * @param cardNo
	 * @param usage
	 * @param updateBy
	 * @param transferMode A - Asynchronous, S - Synchronous
	 * @return Error message from AS, otherwise empty string
	 * @throws Exception
	 */
	public static void updateProductUsage(String cardNo, String usage, 
			String updateBy, String transferMode) throws Exception{
		
		logger.info("updateProductUsage - "+cardNo+","+usage+","+updateBy+","+transferMode);
		
		if(transferMode == null){
			throw new IllegalArgumentException("Transfer Mode cannot be null");
		}
		else if(transferMode.equals(API.SYNCHRONOUS)){
			throw new OperationNotSupportedException("This operation is not supported for IBS1");
			/*
			 * TODO Calling webservice will return a message.
			 * If message empty = no error
			 * Else got error, throw the exception
			 */
		}
		else if(transferMode.equals(API.ASYNCHRONOUS)){
				
			IttbAsMasterReq masterRequest = new IttbAsMasterReq();
			masterRequest.setReqId(DateUtil.getYearMonthDay()+StringUtil.appendLeft(genericDao.getNextSequenceNo(Sequence.REQUEST_NO_SEQUENCE).toString(), 6, "0"));
			masterRequest.setReqTable(IttbAsMasterReq.UPDATE_PRODUCT_USAGE_REQUEST);
			masterRequest.setReqStatus(IttbAsMasterReq.PENDING);
			masterRequest.setReqDate(DateUtil.getCurrentTimestamp());
			genericDao.save(masterRequest);
			
			IttbAsUpdUsageReq request = new IttbAsUpdUsageReq();
			request.setIttbAsMasterReq(masterRequest);
			request.setReqId(masterRequest.getReqId());
			request.setCardNo(cardNo);
			request.setUsage(usage);
			request.setUpdateBy(updateBy);
			genericDao.save(request);
				
		}
		else{
			throw new OperationNotSupportedException("Operation Supported: A-Asynchronous S-Synchronous");
		}
	}
	
	/**
	 * [Synchronous] To enquire Product details
	 * @param cardNo Product ID aka Card No
	 * @return ProductResult object
	 * @throws Exception
	 */
	public static ProductResult enquireProduct(String cardNo) throws Exception{
		
		throw new OperationNotSupportedException("This operation is not supported for IBS1");
		
		//TODO
//		logger.info("enquireProduct - "+cardNo);
//		
//		return null;
	}
	
	/**
	 * [Synchronous] To enquire transaction details.
	 * @param txnFromDate Transaction Start Date Range DDMMYYYYHH24MISS Example 22092008235900 22nd Sept 2008 11:59:00pm
	 * @param txnToDate Transaction End Date Range DDMMYYYYHH24MISS 24092008000000 24nd Sept 2008 12:00:00am
	 * @param txnType S - SALES, V - VOID SALES, SR - SALES REVERSAL, VR - VOID REVERSAL
	 * @param cardNo Product ID aka Card No
	 * @param prodTypeId Product Type ID aka Card Type
	 * @param taxiNo
	 * @param jobNo
	 * @param driverIc
	 * @param maskedCardNo 'Y' to mask return card no as following 'XXXXXXXXXXXX1234'
	 * @return List of TransactionResult objects
	 * @throws Exception
	 */
	public static List enquireTransaction(String txnFromDate, String txnToDate, String txnType, 
			String cardNo, String prodTypeId, String taxiNo, String jobNo,
			String driverIc, String maskedCardNo) throws Exception{
		
		throw new OperationNotSupportedException("This operation is not supported for IBS1");
		
		//TODO
//		logger.info("enquireTransaction - "+txnFromDate+","+txnToDate+","+txnType+","+
//				cardNo+","+prodTypeId+","+maskedCardNo+",");
//		
//		return new ArrayList();
	}
	
	public static String formulateAccountId(AmtbAccount amtbAccount)
	{
		String custNo = null;
		String lvl2Code = null;
		String lvl3Code = null;

		if (NonConfigurableConstants.ACCOUNT_CATEGORY_CORPORATE.equals(amtbAccount.getAccountCategory()) || 
				(NonConfigurableConstants.ACCOUNT_CATEGORY_APPLICANT.equals(amtbAccount.getAccountCategory())))
		{
			custNo = amtbAccount.getCustNo();
			return custNo;
		}
		else if (NonConfigurableConstants.ACCOUNT_CATEGORY_DIVISION.equals(amtbAccount.getAccountCategory()) || 
				(NonConfigurableConstants.ACCOUNT_CATEGORY_SUB_APPLICANT.equals(amtbAccount.getAccountCategory())))
		{
			custNo = amtbAccount.getAmtbAccount().getCustNo();
			lvl2Code = amtbAccount.getCode();
			return custNo+lvl2Code;
		}
		else
		{
			custNo = amtbAccount.getAmtbAccount().getAmtbAccount().getCustNo();
			lvl2Code = amtbAccount.getAmtbAccount().getCode();
			lvl3Code = amtbAccount.getCode();
			return custNo+lvl2Code+lvl3Code;
		}
	}
	
	public static String formulateParentAccountId(AmtbAccount amtbAccount)
	{
		String parentCustNo = null;
		String lvl2ParentCode = null;

		if (NonConfigurableConstants.ACCOUNT_CATEGORY_CORPORATE.equals(amtbAccount.getAccountCategory()) || 
				(NonConfigurableConstants.ACCOUNT_CATEGORY_APPLICANT.equals(amtbAccount.getAccountCategory())))
		{
			return null;
		}
		else if (NonConfigurableConstants.ACCOUNT_CATEGORY_DIVISION.equals(amtbAccount.getAccountCategory()) || 
				(NonConfigurableConstants.ACCOUNT_CATEGORY_SUB_APPLICANT.equals(amtbAccount.getAccountCategory())))
		{
			parentCustNo = amtbAccount.getAmtbAccount().getCustNo();
			return parentCustNo;
		}
		else
		{
			parentCustNo = amtbAccount.getAmtbAccount().getAmtbAccount().getCustNo();
			lvl2ParentCode = amtbAccount.getAmtbAccount().getCode();
			return parentCustNo+lvl2ParentCode;
		}
	}
	
	public static String formulateForceOnline(PmtbProduct product, PmtbProductType pmtbProductType)
	{
		String forceOnlineFlag = null;
		if(NonConfigurableConstants.BOOLEAN_YES.equals(pmtbProductType.getContactless())) {
			forceOnlineFlag = (NonConfigurableConstants.AS_REQUEST_PRODUCT_STATUS_ACTIVE.equals(product.getCurrentStatus())) 
			? NonConfigurableConstants.BOOLEAN_NO 
			: NonConfigurableConstants.BOOLEAN_YES;
		}
		return forceOnlineFlag;
	}
}
