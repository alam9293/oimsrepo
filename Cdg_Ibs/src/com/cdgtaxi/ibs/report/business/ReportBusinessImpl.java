package com.cdgtaxi.ibs.report.business;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.TreeSet;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.zkoss.zkplus.spring.SpringUtil;

import com.cdgtaxi.ibs.acl.model.SatbResource;
import com.cdgtaxi.ibs.acl.model.SatbRole;
import com.cdgtaxi.ibs.acl.model.SatbUser;
import com.cdgtaxi.ibs.common.NonConfigurableConstants;
import com.cdgtaxi.ibs.common.business.GenericBusinessImpl;
import com.cdgtaxi.ibs.common.model.AmtbAccount;
import com.cdgtaxi.ibs.common.model.AmtbAcctStatus;
import com.cdgtaxi.ibs.common.model.AmtbAcctType;
import com.cdgtaxi.ibs.common.model.AmtbContactPerson;
import com.cdgtaxi.ibs.common.model.FmtbArContCodeMaster;
import com.cdgtaxi.ibs.common.model.ImtbItem;
import com.cdgtaxi.ibs.common.model.ImtbItemType;
import com.cdgtaxi.ibs.common.model.PmtbProduct;
import com.cdgtaxi.ibs.common.model.PmtbProductRenew;
import com.cdgtaxi.ibs.common.model.PmtbProductReplacement;
import com.cdgtaxi.ibs.common.model.PmtbProductType;
import com.cdgtaxi.ibs.common.model.TmtbNonBillableTxnCrca;
import com.cdgtaxi.ibs.common.ui.CommonWindow;
import com.cdgtaxi.ibs.master.model.MstbAcquirer;
import com.cdgtaxi.ibs.master.model.MstbMasterTable;
import com.cdgtaxi.ibs.master.model.MstbPromotionCashPlus;
import com.cdgtaxi.ibs.master.model.MstbReportFormatMap;
import com.cdgtaxi.ibs.report.Constants;
import com.cdgtaxi.ibs.util.DateUtil;
import com.cdgtaxi.ibs.util.StringUtil;
import com.elixirtech.ers2.client.ERSClient;
import com.elixirtech.net.NetException;
import com.google.common.base.Strings;

public class ReportBusinessImpl extends GenericBusinessImpl implements ReportBusiness{
	private static final Logger logger = Logger.getLogger(ReportBusinessImpl.class);

	private Map<String, String> reportProperties;
	//Report Server Properties
	private static String ip;
	private static Integer port;
	private static String username;
	private static String password;
	private static String repository;

	public List<SatbResource> searchReportCategories(String userLoginId){

		SatbUser user = (SatbUser)this.daoHelper.getUserDao().getUserWithRoles(CommonWindow.getUserId());
		Iterator<SatbRole> iter = user.getSatbRoles().iterator();
		List<SatbResource> satbResourceList = new ArrayList<SatbResource>();
		while (iter.hasNext())
		{
			SatbRole role = iter.next();
			List<SatbResource> temp = this.daoHelper.getResourceDao().getReportCategories(role);
			Iterator<SatbResource> iterResource = temp.iterator();
			while (iterResource.hasNext())
			{
				SatbResource satbResource = iterResource.next();
				if (!satbResourceList.contains(satbResource))
				{
					satbResourceList.add(satbResource);
				}
			}
		}

		return satbResourceList;
	}

	public List<SatbResource> searchReports(SatbResource reportCategory, String userLoginId){

		SatbUser user = (SatbUser)this.daoHelper.getUserDao().getUserWithRoles(CommonWindow.getUserId());
		Iterator<SatbRole> iter = user.getSatbRoles().iterator();
		List<SatbResource> satbResourceList = new ArrayList<SatbResource>();
		while (iter.hasNext())
		{
			SatbRole role = iter.next();
			List<SatbResource> temp = this.daoHelper.getResourceDao().getReports(reportCategory, role);
			Iterator<SatbResource> iterResource = temp.iterator();
			while (iterResource.hasNext())
			{
				SatbResource satbResource = iterResource.next();
				if (!satbResourceList.contains(satbResource))
				{
					satbResourceList.add(satbResource);
				}
			}
		}
		return satbResourceList;
	}
	public Map<String, String> getIssuableNonOTUProductTypes(){
		List<PmtbProductType> productTypes = this.daoHelper.getProductTypeDao().getAllProductTypes();
		Map<String, String> returnMap = new HashMap<String, String>();
		for(PmtbProductType productType : productTypes){
			if(productType.getOneTimeUsage().equals(NonConfigurableConstants.BOOLEAN_YN_NO)){
				returnMap.put(productType.getProductTypeId(), productType.getName());
			}
		}
		return returnMap;
	}
	public Map<String, String> getIssuableProductTypes(){
		List<PmtbProductType> productTypes = this.daoHelper.getProductTypeDao().getAllProductTypes();
		TreeSet<PmtbProductType> sortedProductTypes = new TreeSet<PmtbProductType>(new Comparator<PmtbProductType>(){
			public int compare(PmtbProductType o1, PmtbProductType o2) {
				return o1.getName().compareTo(o2.getName());
			}
		});
		sortedProductTypes.addAll(productTypes);
		Map<String, String> returnMap = new LinkedHashMap<String, String>();
		for(PmtbProductType productType : sortedProductTypes){
			returnMap.put(productType.getProductTypeId(), productType.getName());
		}
		return returnMap;
	}
	public Map<String, String> getAllProductTypes(){
		List<PmtbProductType> productTypes = this.daoHelper.getProductTypeDao().getAllProductType();
		Map<String, String> returnMap = new HashMap<String, String>();
		for(PmtbProductType productType : productTypes){
			returnMap.put(productType.getProductTypeId(), productType.getName());
		}
		return returnMap;
	}
	public Map<Integer, String> getAllAccountTypes(){
		List<AmtbAcctType> acctTypes = this.daoHelper.getAccountTypeDao().getAllAccountTypes();
		Map<Integer, String> returnMap = new LinkedHashMap<Integer, String>();
		for(AmtbAcctType acctType : acctTypes){
			returnMap.put(acctType.getAcctTypeNo(), acctType.getAcctType());
		}
		return returnMap;
	}
	public Map<String, String> getPrepaidProductTypes(){
		List<PmtbProductType> productTypes = this.daoHelper.getProductTypeDao().getAllProductTypes();
		Map<String, String> returnMap = new HashMap<String, String>();
		for(PmtbProductType productType : productTypes){
			if(productType.getPrepaid().equals(NonConfigurableConstants.BOOLEAN_YN_YES)){
				returnMap.put(productType.getProductTypeId(), productType.getName());
			}
		}
		return returnMap;
	}
	public Map<String, String> getBinRangeProductTypes(){
		List<PmtbProductType> productTypes = this.daoHelper.getProductTypeDao().getAllProductTypes();
		Map<String, String> returnMap = new HashMap<String, String>();
		for(PmtbProductType productType : productTypes){
			if((productType.getBinRange().equalsIgnoreCase("601089")&& productType.getSubBinRange().startsWith("65"))){
				returnMap.put(productType.getProductTypeId(), productType.getName());
			}
		}
		return returnMap;
	}

	private void saveOrUpdateEmbossingProducts(List<PmtbProduct> products)
	{
		List<PmtbProduct> saveProducts = new ArrayList<PmtbProduct>();
		List<PmtbProductRenew> saveRenews = new ArrayList<PmtbProductRenew>();
		List<PmtbProductReplacement> saveReplaces = new ArrayList<PmtbProductReplacement>();

		for(PmtbProduct product : products){

			logger.info("save or update card no = " + product.getCardNo());

			product.setEmbossFlag(NonConfigurableConstants.BOOLEAN_YES);
			saveProducts.add(product);
			for(PmtbProductReplacement replace : product.getPmtbProductReplacements()){
				replace.setEmbossFlag(NonConfigurableConstants.BOOLEAN_YES);
				saveReplaces.add(replace);
			}
			for(PmtbProductRenew renew : product.getPmtbProductRenews()){
				renew.setEmbossFlag(NonConfigurableConstants.BOOLEAN_YES);
				saveRenews.add(renew);
			}
		}

		this.daoHelper.getGenericDao().saveOrUpdateAll(saveProducts);
		this.daoHelper.getGenericDao().saveOrUpdateAll(saveReplaces);
		this.daoHelper.getGenericDao().saveOrUpdateAll(saveRenews);
	}


	public List<Map<String, String>> getEmbossingProducts(String productTypeId, String cardNoStart, String cardNoEnd, Date issueStart, Date issueEnd, Date replaceStart, Date replaceEnd, Date renewStart, Date renewEnd, String sortBy, boolean isReprint, String userId){
		List<PmtbProduct> products = this.daoHelper.getProductDao().getEmbossingProducts(productTypeId, cardNoStart, cardNoEnd, issueStart, issueEnd, replaceStart, replaceEnd, renewStart, renewEnd, sortBy, isReprint);
		List<Map<String, String>> returnList = new ArrayList<Map<String, String>>();
		String BIN = null;

		for(PmtbProduct product : products){
			Map<String, String> productMap = new HashMap<String, String>();
			if(BIN==null){
				BIN = product.getPmtbProductType().getOneTimeUsage();
			}
			// common fields
			productMap.put("BIN", BIN);
			productMap.put("cardNo", product.getCardNo());
			if(BIN!=null && BIN.equals(NonConfigurableConstants.BOOLEAN_YN_NO)){// iso cards
				productMap.put("cardHolderName", product.getNameOnProduct());
				productMap.put("expiryMth", DateUtil.convertDateToStr(product.getExpiryDate(), DateUtil.INTERFACE_AS_PRODUCT_EXPIRY_DATE_FORMAT));
				if(product.getAmtbAccount().getAmtbPersonalDetails().isEmpty()){
					productMap.put("acctNameOnCard", product.getAmtbAccount().getNameOnCard());
				}
			}
			// non iso cards
			else{
				if(product.getPmtbProductType().getFixedValue().equals(NonConfigurableConstants.BOOLEAN_YES) && product.getCreditLimit()!=null){
					productMap.put("creditLimit", StringUtil.bigDecimalToString(product.getCreditLimit(), "0000"));
					productMap.put("fixedValue", StringUtil.bigDecimalToString(product.getFixedValue(), "0000"));
				}
				if(product.getExpiryTime()!=null){
					productMap.put("expiryDate", DateUtil.convertDateToStr(product.getExpiryDate(), "dd/MM/yy"));
				}else if(product.getExpiryDate()!=null){
					productMap.put("expiryDate", DateUtil.convertDateToStr(product.getExpiryDate(), "MM/yy"));
				}
				if(product.getPmtbProductType().getLuhnCheck().equals(NonConfigurableConstants.BOOLEAN_YES)){
					productMap.put("otuLuhnCheckCardNo",
							product.getCardNo().substring(0, 6) +
							" " +
							product.getCardNo().substring(6, product.getCardNo().length()-1) +
							" " +
							product.getCardNo().substring(product.getCardNo().length()-1)
					);
				}
				productMap.put("acctNameOnCard", product.getAmtbAccount().getNameOnCard());
				if(product.getExpiryDate()!=null){
					productMap.put("expiryMth", DateUtil.convertDateToStr(product.getExpiryDate(), DateUtil.INTERFACE_AS_PRODUCT_EXPIRY_DATE_FORMAT));
				}else{
					productMap.put("expiryMth", "0000");
				}
				if(product.getAmtbAccount().getAmtbPersonalDetails().isEmpty()){
					LinkedList<String> accountNames = new LinkedList<String>();
					AmtbAccount parent = product.getAmtbAccount();
					while(parent!=null){
						accountNames.add(parent.getAccountName());
						parent = parent.getAmtbAccount();
					}
					int counter = accountNames.size();
					while(!accountNames.isEmpty()){
						productMap.put("cardName" + counter--, accountNames.poll());
					}
				}else{
					productMap.put("cardName1", product.getAmtbAccount().getAccountName());
				}
			}
			returnList.add(productMap);
			logger.info("card no = " + product.getCardNo());
		}

		logger.info("number of cards = " + returnList.size());
		saveOrUpdateEmbossingProducts(products);

		return returnList;
	}


	public List<Map<String, String>> getContactlessEmbossingProducts(String productTypeId, String cardNoStart, String cardNoEnd, Date issueStart, Date issueEnd, Date replaceStart, Date replaceEnd, Date renewStart, Date renewEnd, String sortBy, boolean isReprint, String userId){

		List<PmtbProduct> products = this.daoHelper.getProductDao().getEmbossingProducts(productTypeId, cardNoStart, cardNoEnd, issueStart, issueEnd, replaceStart, replaceEnd, renewStart, renewEnd, sortBy, isReprint);

		List<Map<String, String>> returnList = new ArrayList<Map<String, String>>();
		for(PmtbProduct product : products){
			Map<String, String> productMap = new HashMap<String, String>();
			AmtbAccount amtbAccount = product.getAmtbAccount();
			String accountCategory = amtbAccount.getAccountCategory();
			boolean isPersonalAccount = !amtbAccount.getAmtbPersonalDetails().isEmpty();
			boolean isOTUProduct = product.getPmtbProductType().getOneTimeUsage().equals(NonConfigurableConstants.BOOLEAN_YES);
			String cardNo = product.getCardNo();

			//Track 1
			StringBuilder track1Sb = new StringBuilder();
			track1Sb.append("B");
			track1Sb.append(StringUtils.rightPad(cardNo, 16, '0'));
			track1Sb.append("^");
			track1Sb.append(amtbAccount.getNameOnCard());
			track1Sb.append("^");
			productMap.put("track1", track1Sb.toString());

			//Track 2
			StringBuilder track2Sb = new StringBuilder();
			track2Sb.append(StringUtils.rightPad(cardNo, 16, '0'));
			track2Sb.append("=");
			Date productExpiryDate = product.getExpiryDate();
			track2Sb.append((productExpiryDate!=null)?DateUtil.convertDateToStr(productExpiryDate, DateUtil.INTERFACE_AS_PRODUCT_EXPIRY_DATE_FORMAT):"0000");
			track2Sb.append("101000000000000");
			productMap.put("track2", track2Sb.toString());

			//Card No
			productMap.put("cardNo", cardNo);

			//Card Name
			String cardName=null;
			if(product.getEmbossNameOnCard().equals(NonConfigurableConstants.BOOLEAN_YES)) {
				if(isOTUProduct) {
					if(isPersonalAccount)  {
						cardName = amtbAccount.getAccountName();
					}
					else {
						if(NonConfigurableConstants.ACCOUNT_CATEGORY_CORPORATE.equals(accountCategory)) {
							cardName = amtbAccount.getAccountName();
						}
					}
				}
				else {
					cardName = product.getNameOnProduct();
				}
			}
			productMap.put("cardName", cardName);

			//Card Name Division, Card Name Department
			String cardNameDivision=null;
			String cardNameDepartment=null;
			if(isPersonalAccount) {
				cardNameDivision = "";
				cardNameDepartment = "";
			}
			else {
				if(isOTUProduct) {

					AmtbAccount parentAcct = amtbAccount;
					while(parentAcct!=null) {
						String parentAcctCategory = parentAcct.getAccountCategory();
						if(NonConfigurableConstants.ACCOUNT_CATEGORY_DEPARTMENT.equals(parentAcctCategory)) {
							cardNameDepartment =  parentAcct.getAccountName();
						}
						else if(NonConfigurableConstants.ACCOUNT_CATEGORY_DIVISION.equals(parentAcctCategory)) {
							cardNameDivision = parentAcct.getAccountName();
						}
						parentAcct = parentAcct.getAmtbAccount();
					}
				}
				else {
					cardNameDepartment = "";
					cardNameDivision = amtbAccount.getNameOnCard();
				}
			}
			productMap.put("cardNameDivision", cardNameDivision);
			productMap.put("cardNameDepartment", cardNameDepartment);

			//Fixed Value
			productMap.put("fixedValue", "$" + StringUtils.defaultString(StringUtil.bigDecimalToString(product.getFixedValue())));

			//Expiry Date
			String expiryDateStr=null;
			String expryDatePersoStr=null;
			if(product.getExpiryTime()!=null){
				expiryDateStr = DateUtil.convertDateToStr(product.getExpiryTime(), "dd/MM/yy");
				expryDatePersoStr =  DateUtil.convertDateToStr(product.getExpiryTime(), "yyMM");
			}else if(product.getExpiryDate()!=null){
				expiryDateStr = DateUtil.convertDateToStr(product.getExpiryDate(), "MM/yy");
				expryDatePersoStr =  DateUtil.convertDateToStr(product.getExpiryDate(), "yyMM");
			}
			productMap.put("expiryDate", expiryDateStr);
			productMap.put("expiryDatePerso", expryDatePersoStr);

			//Force Online Status
			String forceOnlineStatus = (NonConfigurableConstants.AS_REQUEST_PRODUCT_STATUS_ACTIVE.equals(product.getCurrentStatus()))
											? "0" : "1";
			productMap.put("forceOnlineStatus", forceOnlineStatus);

			//Offline Count Limit
			String offlineCountStr = StringUtils.leftPad(StringUtil.numberToString(product.getOfflineCount()), 6, '0');
			productMap.put("offlineCountLimit", offlineCountStr);

			//Offline Amount Limit (Accumulative)
			BigDecimal offlineAmount = product.getOfflineAmount();
			BigDecimal offlineAmountInCents = null;
			if(offlineAmount!=null)
				offlineAmountInCents = offlineAmount.multiply(new BigDecimal("100"));

			String offlineAmountInCentsStr = StringUtils.leftPad(StringUtil.bigDecimalToString(offlineAmountInCents, StringUtil.WITHOUT_DECIMAL_PLAIN_FORMAT), 6, '0');
			productMap.put("offlineAmountLimitAccumulative", offlineAmountInCentsStr);

			//Offline Amount Limit (Per Txn)
			BigDecimal offlineTxnAmount = product.getOfflineTxnAmount();
			BigDecimal offlineTxnAmountInCents = null;
			if(offlineTxnAmount!=null)
				offlineTxnAmountInCents = offlineTxnAmount.multiply(new BigDecimal("100"));

			String offlineTxnAmountInCentsStr = StringUtils.leftPad(StringUtil.bigDecimalToString(offlineTxnAmountInCents, StringUtil.WITHOUT_DECIMAL_PLAIN_FORMAT), 6, '0');
			productMap.put("offlineAmountLimitPerTxn", offlineTxnAmountInCentsStr);

			returnList.add(productMap);
		}
		saveOrUpdateEmbossingProducts(products);
		return returnList;
	}

	public boolean hasEmbossingProducts(String productTypeId, String cardNoStart, String cardNoEnd, Date issueStart, Date issueEnd, Date replaceStart, Date replaceEnd, Date renewStart, Date renewEnd){
		return this.daoHelper.getProductDao().hasEmbossingProducts(productTypeId, cardNoStart, cardNoEnd, issueStart, issueEnd, replaceStart, replaceEnd, renewStart, renewEnd);
	}
	public byte[] generate(String report, String reportCategory,
			String format, Properties params) throws NetException, IOException {
		String outputLocation = "";
		String reportName = report;

		if(null != params.get("invoiceOnly")) {
			if("Y".equals(params.get("invoiceOnly")))
				reportName = reportName + " Invoice Only";
		}

		if (reportCategory == null) {
			outputLocation = repository + "/" + report + "/" + reportName + ".rml";
		} else {
			outputLocation = repository + "/" + reportCategory + "/" + report + "/" + reportName + ".rml";
		}
		logger.debug("Generating report " + reportName + " as " + format);
		logger.debug("Params: " + params.toString());

		ByteArrayOutputStream os = new ByteArrayOutputStream();
		ERSClient client = new ERSClient(ip, port, username, password);
		client.setSecure(false);
		client.renderReport(outputLocation, format, os, params);

		byte[] bytes = os.toByteArray();

		os.close();
		client.close();

		logger.debug("Report generated");

		return bytes;
	}

	public void setReportProperties(Map<String, String> reportProperties) {
		this.reportProperties = reportProperties;

		ip = reportProperties.get("report.server.ip");
		port = Integer.parseInt(reportProperties.get("report.server.port"));
		username = reportProperties.get("report.server.username");
		password = reportProperties.get("report.server.password");
		repository = reportProperties.get("report.server.repository.location");
	}
	public Map<String, String> searchAccount(String custNo, String custName){
		List<AmtbAccount> accts = this.daoHelper.getAccountDao().getAccounts(custNo, custName, null, null);
		Map<String, String> returnMap = new HashMap<String, String>();
		for(AmtbAccount acct : accts){
			returnMap.put(acct.getCustNo(), acct.getAccountName());
		}
		return returnMap;
	}
	public Map<String, String> searchChildrenAccount(String custNo, boolean isBillable){
		List<AmtbAccount> accts = this.daoHelper.getAccountDao().getAccounts(custNo, null, null, null, null, NonConfigurableConstants.DIVISION_LEVEL, null);
		TreeSet<AmtbAccount> sortedAccts = new TreeSet<AmtbAccount>(new Comparator<AmtbAccount>(){
			public int compare(AmtbAccount o1, AmtbAccount o2) {
				return (o1.getAccountName() +" (" + o1.getCode() + ") ").compareTo(o2.getAccountName() + " (" + o2.getCode() + ")");
			}
		});

		if(isBillable) {
			for(AmtbAccount acct: accts){
				if(acct.getInvoiceFormat()!=null)
					sortedAccts.add(acct);
			}
		}
		else {
			sortedAccts.addAll(accts);
		}

		Map<String, String> returnMap = new LinkedHashMap<String, String>();
		for(AmtbAccount acct : sortedAccts){
			if(acct.getCode()!=null){
				returnMap.put(acct.getAccountNo().toString(), acct.getAccountName()+ "(" + acct.getCode() + ")");
			}else{
				returnMap.put(acct.getAccountNo().toString(), acct.getAccountName());
			}
		}
		return returnMap;
	}
	public Map<String, String> searchChildrenAccount(String custNo, String parentCode, boolean isBillable){
		List<AmtbAccount> accts = this.daoHelper.getAccountDao().getAccounts(custNo, parentCode, NonConfigurableConstants.DEPARTMENT_LEVEL);
		TreeSet<AmtbAccount> sortedAccts = new TreeSet<AmtbAccount>(new Comparator<AmtbAccount>(){
			public int compare(AmtbAccount o1, AmtbAccount o2) {
//				return (o1.getAccountName() +" (" + o1.getCustNo() + ")").compareTo(o2.getAccountName() + " (" + o2.getCustNo() + ")");
				return (o1.getAccountName() +" (" + o1.getCustNo() + ") ( "+o1.getCode()+" )").compareTo(o2.getAccountName() + " (" + o2.getCustNo() + ") ( "+o2.getCode()+" )");
			}
		});

		if(isBillable) {
			for(AmtbAccount acct: accts){
				if(acct.getInvoiceFormat()!=null)
					sortedAccts.add(acct);
			}
		}
		else {
			sortedAccts.addAll(accts);
		}

		Map<String, String> returnMap = new LinkedHashMap<String, String>();
		for(AmtbAccount acct : sortedAccts){
			returnMap.put(acct.getAccountNo().toString(), acct.getAccountName()+ "(" + acct.getCode() + ")");
		}
		return returnMap;
	}

	public List<AmtbAccount> searchTopLevelAccount(String custNo, String custName){
		return this.daoHelper.getAccountDao().getTopLevelAccounts(custNo, custName);
	}
	
	public List<AmtbAccount> findAccDeatil(String custNo, String custName){
		return this.daoHelper.getAccountDao().findAccdtlbyaccNoandName(custNo, custName);
	}

	public List<FmtbArContCodeMaster> searchArContCode(Integer entityNo){
		return this.daoHelper.getAccountDao().getArContCode(entityNo);
	}
	public boolean checkProductTypeByID(String productTypeID){
		boolean checkFlag=false;
		PmtbProductType productType=this.daoHelper.getProductTypeDao().getProductType(productTypeID);
		if(productType!=null){
			if(productType.getBatchIssue().equalsIgnoreCase(NonConfigurableConstants.BOOLEAN_YES))
				checkFlag=true;
			else
				checkFlag=false;
		}
		return checkFlag;
	}

	public List<Object[]> getCustomerAgingDetail(String accountNo, String accountName, String entityNo,
			String arControlCodeNo, String salesPersonNo, String outstandingAmount, String daysLate, String type,
			String sortBy){
		return this.daoHelper.getReportDao().getCustomerAgingDetail(accountNo, accountName, entityNo, arControlCodeNo, salesPersonNo, outstandingAmount, daysLate, type, sortBy);
	}

	public List<Object[]> getCustomerAgingSummary(String accountNo, String accountName, String entityNo,
			String arControlCodeNo, String outstandingAmount, String type, String sortBy){
		return this.daoHelper.getReportDao().getCustomerAgingSummary(accountNo, accountName, entityNo, arControlCodeNo, outstandingAmount, type, sortBy);
	}

	public List<Object[]> getSoftCopyInvoiceAndTripsDetailByInvoiceNoCustomize(String accountNo,
			String invoiceMonthYear, String invoiceNo, String sortBy){
		return this.daoHelper.getReportDao().getSoftCopyInvoiceAndTripsDetailByInvoiceNoCustomize(accountNo,
				invoiceMonthYear, invoiceNo, sortBy);
	}

	public List<Object[]> getSoftCopyInvoiceAndTripsDetailByInvoiceNoCSV(String accountNo,
			String invoiceMonthYear, String invoiceNo, String chargeTo, String sortBy){
		return this.daoHelper.getReportDao().getSoftCopyInvoiceAndTripsDetailByInvoiceNoCSV(accountNo,
				invoiceMonthYear, invoiceNo, chargeTo, sortBy);
	}

	public List<Object[]> getSoftCopyInvoiceAndTripsDetailByTransactionStatus(String accountNo,
			String accountName, String cardNo, String txnStatus, String productTypeId,
			String tripStartDate, String tripEndDate, String sortBy){
		return this.daoHelper.getReportDao().getSoftCopyInvoiceAndTripsDetailByTransactionStatus(accountNo,
				accountName, cardNo, txnStatus, productTypeId, tripStartDate, tripEndDate, sortBy);
	}


	public List<MstbMasterTable> getASCTDRConfig() {
		return this.daoHelper.getReportDao().getASCTDRConfig();
	}

	public List<MstbReportFormatMap> getReportFormatMap(Long resourceId){
		return this.daoHelper.getReportDao().getReportFormatMap(resourceId);
	}
	public Map<String, String> getAccountContacts(String custNo){
		List<AmtbContactPerson> contacts = this.daoHelper.getAccountDao().getContacts(custNo, null, null, null, null);
		TreeSet<AmtbContactPerson> sortedContacts = new TreeSet<AmtbContactPerson>(new Comparator<AmtbContactPerson>(){
			public int compare(AmtbContactPerson o1, AmtbContactPerson o2) {
				StringBuffer contact1 = new StringBuffer();
				if(o1.getMstbMasterTableByMainContactSal()!=null){
					contact1.append(o1.getMstbMasterTableByMainContactSal().getMasterValue() + " ");
				}
				contact1.append(o1.getMainContactName());
				if(o1.getSubContactName()!=null && o1.getSubContactName().length()!=0){
					contact1.append(" / ");
					if(o1.getMstbMasterTableBySubContactSal()!=null){
						contact1.append(o1.getMstbMasterTableBySubContactSal().getMasterValue() + " ");
					}
					contact1.append(o1.getSubContactName());
				}
				StringBuffer contact2 = new StringBuffer();
				if(o2.getMstbMasterTableByMainContactSal()!=null){
					contact2.append(o2.getMstbMasterTableByMainContactSal().getMasterValue() + " ");
				}
				contact2.append(o2.getMainContactName());
				if(o2.getSubContactName()!=null && o2.getSubContactName().length()!=0){
					contact2.append(" / ");
					if(o2.getMstbMasterTableBySubContactSal()!=null){
						contact2.append(o2.getMstbMasterTableBySubContactSal().getMasterValue() + " ");
					}
					contact2.append(o2.getSubContactName());
				}
				return contact1.toString().compareTo(contact2.toString());
			}
		});
		sortedContacts.addAll(contacts);
		Map<String, String> returnMap = new LinkedHashMap<String, String>();
		for(AmtbContactPerson contact : sortedContacts){
			StringBuffer contactName = new StringBuffer();
			if(contact.getMstbMasterTableByMainContactSal()!=null){
				contactName.append(contact.getMstbMasterTableByMainContactSal().getMasterValue() + " ");
			}
			contactName.append(contact.getMainContactName());
			if(contact.getSubContactName()!=null && contact.getSubContactName().length()!=0){
				contactName.append(" / ");
				if(contact.getMstbMasterTableBySubContactSal()!=null){
					contactName.append(contact.getMstbMasterTableBySubContactSal().getMasterValue() + " ");
				}
				contactName.append(contact.getSubContactName());
			}
			returnMap.put(contact.getContactPersonNo().toString(), contactName.toString());
		}
		return returnMap;
	}
	public Map<Integer, String> getAllAcquirers(){
		List<MstbAcquirer> acquirers = this.daoHelper.getNonBillableDao().getAllAcquirers();
		Map<Integer, String> returnMap = new LinkedHashMap<Integer, String>();
		for(MstbAcquirer acquirer : acquirers){
			returnMap.put(acquirer.getAcquirerNo(), acquirer.getName());
		}
		return returnMap;
	}
	public Map<Integer, String> getAllItemTypes(){
		List<ImtbItemType> itemTypes = this.daoHelper.getInventoryDao().getAllItemTypes();
		Map<Integer, String> returnMap = new LinkedHashMap<Integer, String>();
		for(ImtbItemType itemType : itemTypes){
			returnMap.put(itemType.getItemTypeNo(), itemType.getTypeName());
		}
		return returnMap;
	}
	public Map<Integer, String> getAllCreditTrems(){
		List<Integer> creditTerms = this.daoHelper.getAdminDao().getAllCreditTerms();
		Map<Integer, String> returnMap = new LinkedHashMap<Integer, String>();
		for(Integer creditTrem : creditTerms){
			returnMap.put(creditTrem, String.valueOf(creditTrem));
		}
		return returnMap;
	}
	public Map<String, String> getAllPromotionCashPlusCodes(){
		 List<MstbPromotionCashPlus> allPromotionCashPlus = this.daoHelper.getAdminDao().getAllPromotionCashPlus();
		Map<String, String> returnMap = new LinkedHashMap<String, String>();
		for(MstbPromotionCashPlus promo : allPromotionCashPlus){
			returnMap.put(promo.getPromoCode(), promo.getPromoCode());
		}
		return returnMap;
	}
	public Set<String> getAllRedeemPoint(){
		return this.daoHelper.getInventoryDao().getAllRedeemPoint();
	}
	public List<Object[]> getCustomerReport(String acctTypeNo, String acctNo, String acctName, String acctStatus, String productTypeId, String industryNo, String joinStart, String joinEnd, String terminateStart, String terminateEnd, String salespersonNo, String sortBy, String entityNo){
		logger.info("getCustomerReport(acctTypeNo = "+acctTypeNo+", acctNo = "+acctNo+", acctName = "+acctName+", acctStatus = "+acctStatus+", productTypeId = "+productTypeId+", industryNo = "+industryNo+", joinStart = "+joinStart+", joinEnd = "+joinEnd+", terminateStart = "+terminateStart+", terminateEnd = "+terminateEnd+", salespersonNo = "+salespersonNo+", sortBy = "+sortBy+", entityNo ="+ entityNo +")");
		return this.daoHelper.getReportDao().getCustomerReport(acctTypeNo, acctNo, acctName, acctStatus, productTypeId, industryNo, joinStart, joinEnd, terminateStart, terminateEnd, salespersonNo, sortBy, entityNo);
	}
	public List<Object[]> getReceiptByPeriodDetailed(String receiptStart, String receiptEnd, String cancelStart, String cancelEnd, String acctNo, String acctName, String invoiceNo, String receiptNo, String paymentMode, String order, String entityNo, String salespersonNo){
		logger.info("getReceiptByPeriodDetailed(receiptStart = "+receiptStart+", receiptEnd = "+receiptEnd+", cancelStart = "+cancelStart+", cancelEnd = "+cancelEnd+", acctNo = "+acctNo+", acctName = "+acctName+", invoiceNo = "+invoiceNo+", receiptNo = "+receiptNo+", paymentMode = "+paymentMode+", order = "+order+ ", entityNo = " + entityNo + ", salespersonNo = " + salespersonNo +")");
		return this.daoHelper.getReportDao().getReceiptByPeriodDetailed(receiptStart, receiptEnd, cancelStart, cancelEnd, acctNo, acctName, invoiceNo, receiptNo, paymentMode, order, entityNo, salespersonNo);
	}
	public List<Object[]> getReceiptByPeriodSummaryDate(String receiptStart, String receiptEnd, String paymentMode, String entityNo, String salespersonNo){
		logger.info("getReceiptByPeriodSummaryDate(receiptStart = "+receiptStart+", receiptEnd = "+receiptEnd+", paymentMode = "+paymentMode+ ", entityNo=" + entityNo +", salespersonNo=" + salespersonNo+")");
		return this.daoHelper.getReportDao().getReceiptByPeriodSummaryDate(receiptStart, receiptEnd, paymentMode, entityNo, salespersonNo);
	}
	public List<Object[]> getReceiptByPeriodSummaryPayment(String receiptStart, String receiptEnd, String paymentMode, String entityNo, String salespersonNo){
		logger.info("getReceiptByPeriodSummaryPayment(receiptStart = "+receiptStart+", receiptEnd = "+receiptEnd+", paymentMode = "+paymentMode+ ", entityNo=" + entityNo+ ", salespersonNo=" + salespersonNo+")");
		return this.daoHelper.getReportDao().getReceiptByPeriodSummaryPayment(receiptStart, receiptEnd, paymentMode, entityNo, salespersonNo);
	}
	public List<Object[]> getCreditDebitNote(String noteStart, String noteEnd, String cancelStart, String cancelEnd, String acctNo, String acctName, String noteType, String order, String entityNo){
		logger.info("getCreditDebitNote(noteStart = "+noteStart+", noteEnd = "+noteEnd+", cancelStart = "+cancelStart+", cancelEnd = "+cancelEnd+", acctNo = "+acctNo+", acctName = "+acctName+", noteType = "+noteType+", order = "+order+ ", entityNo = " + entityNo +")");
		return this.daoHelper.getReportDao().getCreditDebitNote(noteStart, noteEnd, cancelStart, cancelEnd, acctNo, acctName, noteType, order, entityNo);
	}
	public List<Object[]> getCreditBalance(String creditBalance, String acctNo, String acctName, String divNo, String deptNo, String productType, String salespersonNo, String sort){
		logger.info("getCreditBalance(creditBalance = "+creditBalance+", acctNo = "+acctNo+", acctName = "+acctName+", divNo = "+divNo+", deptNo = "+deptNo+", productType = "+productType+", salespersonNo = "+salespersonNo+", sort = "+sort+")");
		return this.daoHelper.getReportDao().getCreditBalance(creditBalance, acctNo, acctName, divNo, deptNo, productType, salespersonNo, sort);
	}
	public List<Object[]> getCardInProduction(String productType, String issueStart, String issueEnd, String renewStart, String renewEnd, String replaceStart, String replaceEnd, String cardStart, String cardEnd, String cardStatus, String sortBy){
		logger.info("getCardInProduction(productType = "+productType+", issueStart = "+issueStart+", issueEnd = "+issueEnd+", renewStart = "+renewStart+", renewEnd = "+renewEnd+", replaceStart = "+replaceStart+", replaceEnd = "+replaceEnd+", cardStart = "+cardStart+", cardEnd = "+cardEnd+", cardStatus = "+cardStatus+", sortBy = "+sortBy+")");
		return this.daoHelper.getReportDao().getCardInProduction(productType, issueStart, issueEnd, renewStart, renewEnd, replaceStart, replaceEnd, cardStart, cardEnd, cardStatus, sortBy);
	}
	public List<Object[]> getCustomerDepositSummary(String acctNo, String acctName, String entity){
		logger.info("getCustomerDepositSummary(acctNo = "+acctNo+", acctName = "+acctName+", entity = "+entity+")");
		return this.daoHelper.getReportDao().getCustomerDepositSummary(acctNo, acctName, entity);
	}
	public List<Object[]> getCustomerDepositDetailedReceipts(String depositStart, String depositEnd, String receiptStart, String receiptEnd, String acctNo, String acctName, String entity, String depositStatus, String sort){
		logger.info("getCustomerDepositDetailedReceipts(depositStart = "+depositStart+", depositEnd = "+depositEnd+", receiptStart = "+receiptStart+", receiptEnd = "+receiptEnd+", acctNo = "+acctNo+", acctName = "+acctName+", entity = "+entity+", depositStatus = "+depositStatus+", sort = "+sort+")");
		return this.daoHelper.getReportDao().getCustomerDepositDetailedReceipts(depositStart, depositEnd, receiptStart, receiptEnd, acctNo, acctName, entity, depositStatus, sort);
	}
	public List<Object[]> getCustomerDepositDetailedRefunds(String depositStart, String depositEnd, String receiptStart, String receiptEnd, String acctNo, String acctName, String entity){
		logger.info("getCustomerDepositDetailedReceipts(depositStart = "+depositStart+", depositEnd = "+depositEnd+", receiptStart = "+receiptStart+", receiptEnd = "+receiptEnd+", acctNo = "+acctNo+", acctName = "+acctName+", entity = "+entity+")");
		return this.daoHelper.getReportDao().getCustomerDepositDetailedRefunds(depositStart, depositEnd, receiptStart, receiptEnd, acctNo, acctName, entity);
	}
	public List<Object[]> getDailyChequeDepositListing(String receivedStartDate, String receivedEndDate, String entity, String quickCheckDeposit, String order){
		logger.info("getDailyChequeDepositListing(receivedStartDate = "+receivedStartDate+", receivedEndDate = "+receivedEndDate +  ", entity = "+entity+   ", quickCheckDeposit = "+quickCheckDeposit + ", order = "+order +")");
		return this.daoHelper.getReportDao().getDailyChequeDepositListing(receivedStartDate, receivedEndDate, entity, quickCheckDeposit, order);
	}
	public List<Object[]> getCustomerUsage(String accountNo, String accountName, String invoiceStartMonth, String invoiceEndMonth, String productType, String entityNo){
		logger.info("getCustomerUsage(accountNo = "+accountNo+", accountName = "+accountName +  ", invoiceStartMonth = "+invoiceStartMonth+   ", invoiceEndMonth = "+invoiceEndMonth + ", productType = "+ productType + ", entityNo = " + entityNo+")");
		return this.daoHelper.getReportDao().getCustomerUsage(accountNo, accountName, invoiceStartMonth, invoiceEndMonth, productType, entityNo);
	}
	public List<Object[]> getCustomerUsageCardLevel(String accountNo, String accountName, String invoiceStartMonth, String invoiceEndMonth, String productType, String entityNo, String expiryDT, String cardNo){
		logger.info("getCustomerUsageCardLevel(accountNo = "+accountNo+", accountName = "+accountName +  ", invoiceStartMonth = "+invoiceStartMonth+   ", invoiceEndMonth = "+invoiceEndMonth + ", productType = "+ productType + ", entityNo = " + entityNo+ ", expiryDT = " + expiryDT+ ", cardNo = " + cardNo+")");
		return this.daoHelper.getReportDao().getCustomerUsageCardLevel(accountNo, accountName, invoiceStartMonth, invoiceEndMonth, productType, entityNo, expiryDT, cardNo);
	}
	public List<Object[]> getCustomerUsageComparsion(String invoiceMonth, String productType, String sort, String entityNo){
		logger.info("getCustomerUsageComparsion(invoiceMonth = "+invoiceMonth+", productType = "+productType+", sort = "+sort+", entityNo = "+entityNo+")");
		return this.daoHelper.getReportDao().getCustomerUsageComparsion(invoiceMonth, productType, sort, entityNo);
	}
	public List<Object[]> getRevenueSummary(String entityNo, String invoiceStart, String invoiceEnd){
		logger.info("getRevenueSummary(entityNo = "+entityNo+", invoiceStart = "+invoiceStart+", invoiceEnd = "+invoiceEnd+")");
		return this.daoHelper.getReportDao().getRevenueSummary(entityNo, invoiceStart, invoiceEnd);
	}
	public List<Object[]> getRevenueMisc(String entityNo, String invoiceStart, String invoiceEnd){
		logger.info("getRevenueMisc(String entityNo, String invoiceStart, String invoiceEnd)");
		return this.daoHelper.getReportDao().getRevenueMisc(entityNo, invoiceStart, invoiceEnd);
	}
	public List<Object[]> getRevenueHeader(){
		logger.info("getRevenueHeader()");
		return this.daoHelper.getReportDao().getRevenueHeader();
	}
	public Map<String, String> getRevenueHeaderForMisc(){
		return this.daoHelper.getReportDao().getRevenueHeaderForMisc();
	}
	public List<String> getRevenueHeaderForProductTypeId(String entityNo, String invoiceStart, String invoiceEnd){
		return this.daoHelper.getReportDao().getRevenueHeaderForProductTypeId(entityNo, invoiceStart, invoiceEnd);
	}
	public Map<String, Object[]> getRevenueProductTypeTripInfos(String invoiceHeaderNo, String invoiceStart, String invoiceEnd){
		return this.daoHelper.getReportDao().getRevenueProductTypeTripInfos(invoiceHeaderNo, invoiceStart, invoiceEnd);
	}
	public Map<String, Object[]> getRevenueProductTypePrepaidInfos(String invoiceHeaderNo, String invoiceStart, String invoiceEnd){
		return this.daoHelper.getReportDao().getRevenueProductTypePrepaidInfos(invoiceHeaderNo, invoiceStart, invoiceEnd);
	}
	public List<Object[]> getSalesReportBySalesperson(String invoiceStart, String invoiceEnd, String productType, String sort, String entityNo){
		logger.info("getSalesReportBySalesperson(invoiceStart = "+invoiceStart+", invoiceEnd = "+invoiceEnd+", productType = "+productType+", sort = "+sort+ ", entity= " + entityNo +")");
		return this.daoHelper.getReportDao().getSalesReportBySalesperson(invoiceStart, invoiceEnd, productType, sort, entityNo);
	}
	public List<Object[]> getBankChargebackReport(String chargebackStart, String chargebackEnd, String batchStart, String batchEnd, String entityNo, String providerNo, String acquirerNo, String sort){
		logger.info("getBankChargebackReport(chargebackStart = "+chargebackStart+", chargebackEnd = "+chargebackEnd+", batchStart = "+batchStart+", batchEnd = "+batchEnd+", entityNo = "+entityNo+", providerNo = "+providerNo+", acquirerNo = "+acquirerNo+", sort = "+sort+")");
		return this.daoHelper.getReportDao().getBankChargebackReport(chargebackStart, chargebackEnd, batchStart, batchEnd, entityNo, providerNo, acquirerNo, sort);
	}
	public List<Object[]> getCashlessAgingReportDetailed(String agingDate, String batchStart, String batchEnd, String entityNo, String providerNo, String acquirerNo, String sort){
		logger.info("getCashlessAgingReportDetailed(agingDate = "+agingDate+", batchStart = "+batchStart+", batchEnd = "+batchEnd+", entityNo = "+entityNo+", providerNo = "+providerNo+", acquirerNo = "+acquirerNo+", sort = "+sort+")");
		return this.daoHelper.getReportDao().getCashlessAgingReportDetailed(agingDate, batchStart, batchEnd, entityNo, providerNo, acquirerNo, sort);
	}
	public List<Object[]> getCashlessAgingReportSummary(String agingDate, String batchStart, String batchEnd, String entityNo, String providerNo, String acquirerNo){
		logger.info("getCashlessAgingReportSummary(agingDate = "+agingDate+", batchStart = "+batchStart+", batchEnd = "+batchEnd+", entityNo = "+entityNo+", providerNo = "+providerNo+", acquirerNo = "+acquirerNo+")");
		return this.daoHelper.getReportDao().getCashlessAgingReportSummary(agingDate, batchStart, batchEnd, entityNo, providerNo, acquirerNo);
	}
	public List<Object[]> getCashlessBankCollectionSummary(String batchNo, String creditStart, String creditEnd, String batchStart, String batchEnd, String entityNo, String acquirerNo, String paymentTypeNo, String providerNo){
		logger.info("getCashlessBankCollectionSummary(batchNo = "+batchNo+", creditStart = "+creditStart+", creditEnd = "+creditEnd+", batchStart = "+batchStart+", batchEnd = "+batchEnd+", entityNo = "+entityNo+", acquirerNo = "+acquirerNo+", paymentTypeNo = "+paymentTypeNo+", providerNo = "+providerNo+")");
		return this.daoHelper.getReportDao().getCashlessBankCollectionSummary(batchNo, creditStart, creditEnd, batchStart, batchEnd, entityNo, acquirerNo, paymentTypeNo, providerNo);
	}
	public List<Object[]> getCashlessBankCollectionDetailed(String batchNo, String creditStart, String creditEnd, String batchStart, String batchEnd, String entityNo, String acquirerNo, String paymentTypeNo, String providerNo, String taxiNo, String driverIc, String txnStatus, String sort){
		logger.info("getCashlessBankCollectionDetailed(batchNo = "+batchNo+", creditStart = "+creditStart+", creditEnd = "+creditEnd+", batchStart = "+batchStart+", batchEnd = "+batchEnd+", entityNo = "+entityNo+", acquirerNo = "+acquirerNo+", paymentTypeNo = "+paymentTypeNo+", providerNo = "+providerNo+", taxiNo = "+taxiNo+", driverIc = "+driverIc+", txnStatus = "+txnStatus+", sort = "+sort+")");
		return this.daoHelper.getReportDao().getCashlessBankCollectionDetailed(batchNo, creditStart, creditEnd, batchStart, batchEnd, entityNo, acquirerNo, paymentTypeNo, providerNo, taxiNo, driverIc, txnStatus, sort);
	}
	public List<Object[]> getCashlessTxnByAmtRange(String tripStart, String tripEnd, String paymentTypeNo, String entityNo){
		logger.info("getCashlessTxnByAmtRange(tripStart = "+tripStart+", tripEnd = "+tripEnd+", paymentTypeNo = "+paymentTypeNo+", entityNo = "+ entityNo +")");
		return this.daoHelper.getReportDao().getCashlessTxnByAmtRange(tripStart, tripEnd, paymentTypeNo, entityNo);
	}
	public List<Object[]> getCashlessCollectionStatisticsSummary(String creditStart, String creditEnd, String entityNo, String acquirerNo){
		logger.info("getCashlessCollectionStatisticsSummary(creditStart = "+creditStart+", creditEnd = "+creditEnd+", entityNo = "+entityNo+", acquirerNo = "+acquirerNo+")");
		return this.daoHelper.getReportDao().getCashlessCollectionStatisticsSummary(creditStart, creditEnd, entityNo, acquirerNo);
	}
	public List<Object[]> getCashlessCollectionStatisticsDetailed(String creditStart, String creditEnd, String entityNo, String acquirerNo){
		logger.info("getCashlessCollectionStatisticsDetailed(creditStart = "+creditStart+", creditEnd = "+creditEnd+", entityNo = "+entityNo+", acquirerNo = "+acquirerNo+")");
		return this.daoHelper.getReportDao().getCashlessCollectionStatisticsDetailed(creditStart, creditEnd, entityNo, acquirerNo);
	}
	public List<Object[]> getInvoiceReport(String entity, String invoiceStart, String invoiceEnd, String invoiceType, String eInvoice, String salesPerson, String sortBy){
		logger.info("getInvoiceReport(entity = "+entity+", invoiceStart = "+invoiceStart+", invoiceEnd = "+invoiceEnd+", invoiceType = "+invoiceType+", eInvoice = "+eInvoice+", salesPerson = "+salesPerson+", sortBy = "+sortBy+")");
		return this.daoHelper.getReportDao().getInvoiceReport(entity, invoiceStart, invoiceEnd, invoiceType, eInvoice, salesPerson, sortBy);
	}
	public List<Object[]> getRevenueByProductCardType(String revenueStart, String revenueEnd, String entity, String provider, String productType, String paymentType){
		logger.info("getRevenueByProductCardType(revenueStart = "+revenueStart+", revenueEnd = "+revenueEnd+", entity = "+entity+", provider = "+provider+", productType = "+productType+", paymentType = "+paymentType+")");
		return this.daoHelper.getReportDao().getRevenueByProductCardType(revenueStart, revenueEnd, entity, provider, productType, paymentType);
	}
	public List<Object[]> getRevenueByProductCardType2(String revenueStart, String revenueEnd, String entity, String provider, String productType, String paymentType){
		logger.info("getRevenueByProductCardType2(revenueStart = "+revenueStart+", revenueEnd = "+revenueEnd+", entity = "+entity+", provider = "+provider+", productType = "+productType+", paymentType = "+paymentType+")");
		return this.daoHelper.getReportDao().getRevenueByProductCardType2(revenueStart, revenueEnd, entity, provider, productType, paymentType);
	}
	public List<Object[]> getCreditCardPromoDetails(String batchStart, String batchEnd, String tripStart, String tripEnd, String paymentTypeNo, String binStart, String binEnd, String promo, String txnStatus, String batchStatus, String sort){
		logger.info("getCreditCardPromoDetails(batchStart = "+batchStart+", batchEnd = "+batchEnd+", tripStart = "+tripStart+", tripEnd = "+tripEnd+", paymentTypeNo = "+paymentTypeNo+", binStart = "+binStart+", binEnd = "+binEnd+", promo = "+promo+", txnStatus = "+txnStatus+", batchStatus = "+batchStatus+", sort = "+sort+")");
		return this.daoHelper.getReportDao().getCreditCardPromoDetails(batchStart, batchEnd, tripStart, tripEnd, paymentTypeNo, binStart, binEnd, promo,  txnStatus, batchStatus, sort);
	}
	public List<Object[]> getInventoryMovementReportDetailed(String stockStart, String stockEnd, String itemTypeNo, String entityNo){
		logger.info("getInventoryMovementReportDetailed(stockStart = "+stockStart+", stockEnd = "+stockEnd+", itemTypeNo = "+itemTypeNo+", entityNo = "+entityNo+" )");
		return this.daoHelper.getReportDao().getInventoryMovementReportDetailed(stockStart, stockEnd, itemTypeNo, entityNo);
	}
	public List<Object[]> getTripReconciliationReport(String entity,String accountNo,String accountName,String division,String department,String productType,String startDate,String endDate,String uploadStartDate,String uploadEndDate,String companyCode,String txnStatus,String sortBy){
		logger.info("getPremierServiceTripReconReport(Entity = "+entity+" accountNo = "+accountNo+", accountName = "+accountName+", division = "+division+", department = "+department+", Product Type = "+productType+" startDate = "+startDate+", endDate = "+endDate+", Upload Start Date = "+uploadStartDate+" Upload End Date = "+uploadEndDate+" Company Code = "+companyCode+" txnStatus = "+txnStatus+", sortBy = "+sortBy+")");
		return this.daoHelper.getReportDao().getTripReconciliationReport(entity,accountNo,accountName,division,department,productType,startDate,endDate,uploadStartDate,uploadEndDate,companyCode,txnStatus,sortBy);
	}
	public List<Object[]> getCorporateCustomerBreakdownUsage(String invoiceMonth,String productType,String accountStatus,String businessNature,String salesPerson,String numberOfRecords,String sortBy, String entityNo){
		logger.info("getContactPerson(invoiceMonth = "+invoiceMonth+", productType = "+productType+", accountStatus = "+accountStatus+", businessNature = "+businessNature+", salesPerson = "+salesPerson+", numberOfRecords = "+numberOfRecords+", sortBy = "+sortBy+ ", entityNo= " +entityNo+ ")");
		return this.daoHelper.getReportDao().getCorporateCustomerBreakdownUsageReport(invoiceMonth,productType,accountStatus,businessNature,salesPerson,numberOfRecords,sortBy, entityNo);
	}
	public List<Object[]> getContactPerson(String accountNo,String accountName,String division,String department,String accountStatus,String contactPersonName,String typeOfContact,String businessNature,String productType,String salesPerson,String sortBy, String entityNo){
		logger.info("getContactPerson(accountNo = "+accountNo+", accountName = "+accountName+", division = "+division+", department = "+department+", accountStatus = "+accountStatus+", contactPersonName = "+contactPersonName+", typeOfContact = "+typeOfContact+", businessNature = "+businessNature+", productType = "+productType+", salesPerson = "+salesPerson+", sortBy = "+sortBy+ ", enityNo = "+ entityNo +")");
		return this.daoHelper.getReportDao().getContactPersonReport(accountNo,accountName,division,department,accountStatus,contactPersonName,typeOfContact,businessNature,productType,salesPerson,sortBy, entityNo);
	}
	public List<Object[]> getPrepaidProduct(String custNo, String accountName, String productTypeId,
			String expiryDateFrom, String expiryDateTo, String cardStatus, String cardNoFrom, String cardNoTo,
			String accountTypeNo, String accountStatus, String entityNo){
		return this.daoHelper.getReportDao().getPrepaidProduct(custNo, accountName, productTypeId, expiryDateFrom, expiryDateTo, cardStatus, cardNoFrom, cardNoTo, accountTypeNo, accountStatus, entityNo);
	}
	public List<Object[]> getCardStatisticReport(String productStatusDate, String issueStart, String issueEnd, String terminateStart, String terminateEnd, String suspendStart, String suspendEnd, String replaceStart, String replaceEnd, String productType, String productStatus, String acctStatus){
		logger.info("getCardStatisticReport(productStatusDate = "+productStatusDate+", issueStart = "+issueStart+", issueEnd = "+issueEnd+", terminateStart = "+terminateStart+", terminateEnd = "+terminateEnd+", suspendStart = "+suspendStart+", suspendEnd = "+suspendEnd+", replaceStart = "+replaceStart+", replaceEnd = "+replaceEnd+", productType = "+productType+", productStatus = "+productStatus+", acctStatus = "+acctStatus+")");
		return this.daoHelper.getReportDao().getCardStatisticReport(productStatusDate, issueStart, issueEnd, terminateStart, terminateEnd, suspendStart, suspendEnd, replaceStart, replaceEnd, productType, productStatus, acctStatus);
	}
	public List<Object[]> getCardStatisticReportSummary(String productStatusDate, String issueStart, String issueEnd, String terminateStart, String terminateEnd, String suspendStart, String suspendEnd, String replaceStart, String replaceEnd, String productType, String productStatus, String acctStatus){
		logger.info("getCardStatisticReportSummary(productStatusDate = "+productStatusDate+", issueStart = "+issueStart+", issueEnd = "+issueEnd+", terminateStart = "+terminateStart+", terminateEnd = "+terminateEnd+", suspendStart = "+suspendStart+", suspendEnd = "+suspendEnd+", replaceStart = "+replaceStart+", replaceEnd = "+replaceEnd+", productType = "+productType+", productStatus = "+productStatus+", acctStatus = "+acctStatus+")");
		return this.daoHelper.getReportDao().getCardStatisticReportSummary(productStatusDate, issueStart, issueEnd, terminateStart, terminateEnd, suspendStart, suspendEnd, replaceStart, replaceEnd, productType, productStatus, acctStatus);
	}
	public List<Object[]> getNewAccountsRevenue(String acctTypeNo, String joinedStart, String joinedEnd, String usageYear, String entityNo, String acctStatus, String industryCode, String salespersonNo){
		logger.info("getNewAccountsRevenue(acctTypeNo = "+acctTypeNo+", joinedStart = "+joinedStart+", joinedEnd = "+joinedEnd+", usageYear = "+usageYear+", entityNo = "+entityNo+", acctStatus = "+acctStatus+", industryCode = "+industryCode+", salespersonNo = "+salespersonNo+")");
		return this.daoHelper.getReportDao().getNewAccountsRevenue(acctTypeNo, joinedStart, joinedEnd, usageYear, entityNo, acctStatus, industryCode, salespersonNo);
	}
	public List<Object[]> getRefundDepositReport(String acctNo, String acctName, String acctStatus, String terminateStart, String terminateEnd, String refund, String entityNo){
		logger.info("getNewAccountsRevenue(acctNo = "+acctNo+", acctName = "+acctName+", acctStatus = "+acctStatus+", terminateStart = "+terminateStart+", terminateEnd = "+terminateEnd+", refund = "+refund+ ", entityNo =" + entityNo +")");
		return this.daoHelper.getReportDao().getRefundDepositReport(acctNo, acctName, acctStatus, terminateStart, terminateEnd, refund, entityNo);
	}
	public List<Object[]> getFinancialMemoReport(String acctNo, String acctName, String memoStart, String memoEnd, String memoNo, String invoiceNo, String receiptNo, String memoType, String sort, String entityNo){
		logger.info("getFinancialMemoReport(acctNo = "+acctNo+", acctName = "+acctName+", memoStart = "+memoStart+", memoEnd = "+memoEnd+", memoNo = "+memoNo+", invoiceNo = "+invoiceNo+", receiptNo = "+receiptNo+", memoType = "+memoType+", sort = "+sort+ ", entityNo= " + entityNo +")");
		return this.daoHelper.getReportDao().getFinancialMemoReport(acctNo, acctName, memoStart, memoEnd, memoNo, invoiceNo, receiptNo, memoType, sort, entityNo);
	}
	public List<Object[]> getItemTypeRevenueProfitReport(String startMonth, String endMonth, String itemTypeNo, String entityNo){
		logger.info("getItemTypeRevenueProfitReport(startMonth = "+startMonth+", endMonth = "+endMonth+", itemTypeNo = "+itemTypeNo+ ", entityNo=" + entityNo + ")");
		return this.daoHelper.getReportDao().getItemTypeRevenueProfitReport(startMonth, endMonth, itemTypeNo, entityNo);
	}
	public List<Object[]> getMonthlyDebtManagementDebt(String acctTypeNo, String acctStatus, String receiptMonth, String industryCode, String salespersonNo, String entityNo){
		logger.info("getMonthlyDebtManagementDebt(acctTypeNo = "+acctTypeNo+", acctStatus = "+acctStatus+", receiptMonth = "+receiptMonth+", industryCode = "+industryCode+", salespersonNo = "+salespersonNo+ ", entityNo= " +entityNo + ")");
		return this.daoHelper.getReportDao().getMonthlyDebtManagementDebt(acctTypeNo, acctStatus, receiptMonth, industryCode, salespersonNo, entityNo);
	}
	public List<Object[]> getMonthlyDebtManagementReceived(String acctTypeNo, String acctStatus, String receiptMonth, String industryCode, String salespersonNo, String entityNo){
		logger.info("getMonthlyDebtManagementReceived(acctTypeNo = "+acctTypeNo+", acctStatus = "+acctStatus+", receiptMonth = "+receiptMonth+", industryCode = "+industryCode+", salespersonNo = "+salespersonNo+ ", entityNo= " +entityNo + ")");
		return this.daoHelper.getReportDao().getMonthlyDebtManagementReceived(acctTypeNo, acctStatus, receiptMonth, industryCode, salespersonNo, entityNo);
	}
	public List<Object[]> getMonthlyDebtManagementClosing(String acctTypeNo, String acctStatus, String receiptMonth, String industryCode, String salespersonNo, String entityNo){
		logger.info("getMonthlyDebtManagementClosing(acctTypeNo = "+acctTypeNo+", acctStatus = "+acctStatus+", receiptMonth = "+receiptMonth+", industryCode = "+industryCode+", salespersonNo = "+salespersonNo+ ", entityNo= " +entityNo + ")");
		return this.daoHelper.getReportDao().getMonthlyDebtManagementClosing(acctTypeNo, acctStatus, receiptMonth, industryCode, salespersonNo, entityNo);
	}
	public List<Object[]> getTimelyPaymentStatisticsDetailed(String mthYear, String entityNo, String creditTerm, String type){
		logger.info("getTimelyPaymentStatisticsDetailed(mthYear = "+mthYear+", entityNo = "+entityNo+", creditTerm = "+creditTerm+", type = "+type + ")");
		return this.daoHelper.getReportDao().getTimelyPaymentStatisticsDetailed(mthYear, entityNo, creditTerm, type);
	}
	public List<Object[]> getMovementReport(String entityNo, String productItemType, String acctNo, String acctName, String acctStatus, String productStatus, String mthYear, String type){
		logger.info("getMovementReport(entityNo = "+entityNo+", productItemType = "+productItemType+", acctNo = "+acctNo+", acctName = "+acctName+", acctStatus = "+acctStatus+", productStatus = "+productStatus+", mthYear = "+mthYear+", type = "+type+")");
		return this.daoHelper.getReportDao().getMovementReport(entityNo, productItemType, acctNo, acctName, acctStatus, productStatus, mthYear, type);
	}
	public List<Object[]> getLoyaltyProgramReport(String acctTypeNo, String acctNo, String acctName, String acctStatus, String productTypeId, String pointStart, String pointEnd, String salespersonNo, String sort, String entityNo){
		logger.info("getLoyaltyProgramReport(acctTypeNo = "+acctTypeNo+", acctNo = "+acctNo+", acctName = "+acctName+", acctStatus = "+acctStatus+", productTypeId = "+productTypeId+", pointStart = "+pointStart+", pointEnd = "+pointEnd+", salespersonNo = "+salespersonNo+", sort = "+sort+ ", entityNo = "+entityNo+ " )");
		return this.daoHelper.getReportDao().getLoyaltyProgramReport(acctTypeNo, acctNo, acctName, acctStatus, productTypeId, pointStart, pointEnd, salespersonNo, sort, entityNo);
	}
	public List<Object[]> getTripAdjustmentReport(String acctNo, String acctName, String paymentType, String createStart, String createEnd, String approveStart, String approveEnd, String entityNo, String providerNo, String approvalStatus, String txnStatus, String action, String fmsUpdate, String sort){
		logger.info("getTripAdjustmentReport(acctNo = "+acctNo+", acctName = "+acctName+", paymentType = "+paymentType+", createStart = "+createStart+", createEnd = "+createEnd+", approveStart = "+approveStart+", approveEnd = "+approveEnd+", entityNo = "+entityNo+", providerNo = "+providerNo+", approvalStatus = "+approvalStatus+", txnStatus = "+txnStatus+" action = "+action+", fmsUpdate = "+fmsUpdate+", sort = "+sort+")");
		return this.daoHelper.getReportDao().getTripAdjustmentReport(acctNo, acctName, paymentType, createStart, createEnd, approveStart, approveEnd, entityNo, providerNo, approvalStatus, txnStatus, action, fmsUpdate, sort);
	}
	public List<Object[]> getGiroSummary(String valueDateFrom, String valueDateTo, String generationDateFrom,
			String generationDateTo, String uploadDateFrom, String uploadDateTo, String entityNo){
		return this.daoHelper.getReportDao().getGiroSummary(valueDateFrom, valueDateTo, generationDateFrom, generationDateTo, uploadDateFrom, uploadDateTo, entityNo);
	}
	public List<Object[]> getGiroFile(String custNo, String divAcct, String deptAcct, String entityNo, String cutOffDate) {
		return this.daoHelper.getReportDao().getGiroFile(custNo, divAcct, deptAcct, entityNo, cutOffDate);
	}

	public List<Object[]> getGiroReject(String valueDateFrom, String valueDateTo, String acctTypeNo,
			String salesPersonNo, String rejectedCode, String rejectedBy, String entityNo){
		return this.daoHelper.getReportDao().getGiroReject(valueDateFrom, valueDateTo, acctTypeNo, salesPersonNo, rejectedCode, rejectedBy, entityNo);
	}
	public List<Object[]> getErrorTxnReport(String txnStart, String txnEnd, String uploadStart, String uploadEnd, String errorMsg, String offline, String jobNo, String cardNo, String nric, String taxiNo, String sort){
		logger.info("getTripAdjustmentReport(txnStart = "+txnStart+", txnEnd = "+txnEnd+", uploadStart = "+uploadStart+", uploadEnd = "+uploadEnd+", errorMsg = "+errorMsg+", offline = "+offline+", jobNo = "+jobNo+", cardNo = "+cardNo+", nric = "+nric+", taxiNo = "+taxiNo+", sort = "+sort+")");
		return this.daoHelper.getReportDao().getErrorTxnReport(txnStart, txnEnd, uploadStart, uploadEnd, errorMsg, offline, jobNo, cardNo, nric, taxiNo, sort);
	}

	public List<Object[]> getApprovalRequestRecords(String acctType, String acctNo, String acctName, String cardNo,
			String requestStartDate, String requestEndDate, String approvalStartDate, String approvalEndDate, String actionType, String approvalStatus, String entity, String sortBy){
		return this.daoHelper.getReportDao().getApprovalRequestRecords(acctType, acctNo, acctName, cardNo,
				requestStartDate, requestEndDate, approvalStartDate, approvalEndDate, actionType, approvalStatus, entity, sortBy);
	}

	public List<Object[]> getTopUpRecords(String acctType, String acctNo, String acctName, String cardNo,
			String cardStatus, String mobileNo, String productTypeId, String topUpType, String promoCode,
			String topUpStartDate, String topUpEndDate, String minTopUp, String entityNo, String sortBy){

		return this.daoHelper.getReportDao().getTopUpRecords(acctType, acctNo, acctName, cardNo, cardStatus, mobileNo, productTypeId, topUpType, promoCode, topUpStartDate, topUpEndDate, minTopUp, entityNo, sortBy);

	}

	public List<Object[]> getPrepaidUsageRecords(String acctType, String acctNo, String acctName, String acctStatus, String subscribeProductTypeId, String joinStartDate, String joinEndDate, String tripStartDate, String tripEndDate,
			  String entityNo, String sortBy){

		return this.daoHelper.getReportDao().getPrepaidUsageRecords(acctType, acctNo, acctName, acctStatus, subscribeProductTypeId, joinStartDate, joinEndDate, tripStartDate, tripEndDate, entityNo, sortBy);
	}

	public List<Object[]> getPrepaidUsageDetailRecords(String acctType, String acctNo, String acctName, String cardNo, String cardStatus, String productTypeId, String issueStartDate, String issueEndDate, String tripStartDate, String tripEndDate,
			  String entityNo, String sortBy){

		return this.daoHelper.getReportDao().getPrepaidUsageDetailRecords(acctType, acctNo, acctName, cardNo, cardStatus, productTypeId, issueStartDate, issueEndDate, tripStartDate, tripEndDate, entityNo, sortBy);
	}

	public List<Object[]> getPrepaidCardTransactionRecords(String cardNo){

		return this.daoHelper.getReportDao().getPrepaidCardTransactionRecords(cardNo);
	}


	public List<String> getAllTxnErrorMsg(){
		logger.info("getAllTxnErrorMsg()");
		return this.daoHelper.getReportDao().getAllTxnErrorMsg();
	}
	//added by vani 28.10.2010 for UAM Report
	/**
	 * Added by CDG.
	 */
	public Map<Long, String> getAllRoles(){
		List<SatbRole> roles = this.daoHelper.getRoleDao().getActiveRoles();
		Map<Long, String> returnMap = new LinkedHashMap<Long, String>();
		for(SatbRole role : roles){
			returnMap.put(role.getRoleId() , role.getName());
		}
		return returnMap;
	}
	//added jtaruc 16/03/11 for Offline Approval Report
	public List<Object[]> getASOfflineApproval(String txnStart, String txnEnd,String offlineTxnStart,String offlineTxnEnd,String messageType,String error,String cardNo,String sortBy){
		logger.info("getASOfflineApproval(txnStart = "+txnStart+", txnEnd = "+txnEnd+", offlineTxnStart = "+offlineTxnStart+", offlineTxnEnd = "+offlineTxnEnd+", messageType = "+messageType+", error = "+error+", cardNo = "+cardNo+", sortBy = "+sortBy+")");
		return this.daoHelper.getReportDao().getASOfflineApproval(txnStart, txnEnd, offlineTxnStart, offlineTxnEnd, messageType, error, cardNo, sortBy);
	}
	/**
	 * Added end
	 */

	public List<ImtbItem> getUnredeemedVoucher(java.sql.Date issuedDateFrom,
			java.sql.Date issuedDateTo, java.sql.Date expiryDateFrom, java.sql.Date expiryDateTo,
			Integer itemTypeNo, String status, java.sql.Date batchDateAsAt, java.sql.Date redeemDateAsAt, Integer entityNo) {
		return this.daoHelper.getReportDao().getUnredeemedVoucher(issuedDateFrom, issuedDateTo,
				expiryDateFrom, expiryDateTo, itemTypeNo, status, batchDateAsAt, redeemDateAsAt, entityNo);
	}

	public List<ImtbItem> getRedeemedVoucher(BigDecimal serialNoStart, BigDecimal serialNoEnd,
			Integer itemTypeNo, java.sql.Date batchDateStart, java.sql.Date batchDateEnd, java.sql.Date redeemDateStart,  java.sql.Date redeemDateEnd, int accountNo, Integer entityNo) {

		return this.daoHelper.getReportDao().getRedeemedVoucher(serialNoStart, serialNoEnd,
				 itemTypeNo, batchDateStart, batchDateEnd, redeemDateStart, redeemDateEnd, accountNo, entityNo);
	}

	public List<Object[]> suspensionReactivationVoucher(String actionStartDate,
			String actionEndDate, String approvalStartDate, String approvalEndDate,
			String serialNoStart, String serialNoEnd, String itemTypeNo, String actionType,
			String approvalStatus, String entityNo) {
		return this.daoHelper.getReportDao().suspensionReactivationVoucher(actionStartDate,
				actionEndDate, approvalStartDate, approvalEndDate, serialNoStart, serialNoEnd,
				itemTypeNo, actionType, approvalStatus, entityNo);
	}

	public List<Object[]> adjustVoucherRedemption(String batchNo, String actionStartDate,
			String actionEndDate, String approvalStartDate, String approvalEndDate,
			String serialNoStart, String serialNoEnd, String itemTypeNo, String actionType,
			String approvalStatus, String entityNo) {
		return this.daoHelper.getReportDao().adjustVoucherRedemption(batchNo, actionStartDate,
				actionEndDate, approvalStartDate, approvalEndDate, serialNoStart, serialNoEnd,
				itemTypeNo, actionType, approvalStatus, entityNo);
	}

	public List<AmtbAccount> getGovtEInvChildrenAccounts(AmtbAccount topLevelAccount){
		return this.daoHelper.getAccountDao().getGovtEInvChildrenAccounts(topLevelAccount);
	}
	public List<AmtbAccount> getPubbsChildrenAccounts(AmtbAccount topLevelAccount){
		return this.daoHelper.getAccountDao().getPubbsChildrenAccounts(topLevelAccount);
	}
	public List<Object[]> getGovtEInv(String accountNo, String accountName, String subAccountNo,
			String entityNo, String businessUnit, String govtEInvFlag, String returnStatus, String requestNo,
			String requestDateFrom, String requestDateTo, String invoiceDateFrom, String invoiceDateTo) {
		return this.daoHelper.getReportDao().getGovtEInv(accountNo, accountName, subAccountNo, entityNo,
				businessUnit, govtEInvFlag, returnStatus, requestNo, requestDateFrom, requestDateTo,
				invoiceDateFrom, invoiceDateTo);
	}
	public List<Object[]> getPubbs(String accountNo, String accountName, String subAccountNo,
			String entityNo, String pubbsFlag, String returnStatus, String requestNo,
			String requestDateFrom, String requestDateTo, String invoiceDateFrom, String invoiceDateTo) {
		return this.daoHelper.getReportDao().getPubbs(accountNo, accountName, subAccountNo, entityNo,
				pubbsFlag, returnStatus, requestNo, requestDateFrom, requestDateTo,
				invoiceDateFrom, invoiceDateTo);
	}


	public byte[] generatePrepaidInvoice(AmtbAccount account2, String invoiceHeaderNo, boolean isDraft) throws NetException, IOException {

		AmtbAccount account = this.businessHelper.getAccountBusiness().getAccount(Integer.toString(account2.getAccountNo()));
		AmtbAccount topLevelAccount = this.businessHelper.getAccountBusiness().getTopLevelAccount(account);
		String customerName = topLevelAccount.getAccountName();
		String billedTo = "";

		AmtbContactPerson contactPerson = this.businessHelper.getAccountBusiness().getMainContactByType(account.getAccountNo(), NonConfigurableConstants.MAIN_CONTACT_TYPE_BILLING);

		String address1 = Strings.nullToEmpty(contactPerson.getAddressBlock()) + " " + Strings.nullToEmpty(contactPerson.getAddressStreet());

		String address2 = Strings.nullToEmpty(contactPerson.getAddressUnit()) + " " + Strings.nullToEmpty(contactPerson.getAddressBuilding());

		String address3 = Strings.nullToEmpty(contactPerson.getAddressArea());

//		String address4 = Strings.nullToEmpty(contactPerson.getAddressState()) + " " + Strings.nullToEmpty(contactPerson.getAddressCity()) + " " + Strings.nullToEmpty(contactPerson.getAddressPostal());
		String address4 = "";
		String countryCB = "";

		if(null != contactPerson.getMstbMasterTableByAddressCountry())
			countryCB = contactPerson.getMstbMasterTableByAddressCountry().getMasterValue();
		if(countryCB.equals("SINGAPORE")) {
			address4 = countryCB + " " + Strings.nullToEmpty(contactPerson.getAddressPostal());
		} else {
			address4 = countryCB + " " + Strings.nullToEmpty(contactPerson.getAddressState()) + " " + Strings.nullToEmpty(contactPerson.getAddressCity()) + " " + Strings.nullToEmpty(contactPerson.getAddressPostal()) ;
		}

		address1 = address1.trim();
		address2 = address2.trim();
		address3 = address3.trim();
		address4 = address4.trim();

		String acctCategory = account.getAccountCategory();

		String billingContact = "";
		//empty value if account type is personnel
		if(acctCategory.equals(NonConfigurableConstants.ACCOUNT_CATEGORY_APPLICANT) || (acctCategory.equals(NonConfigurableConstants.ACCOUNT_CATEGORY_SUB_APPLICANT))){
			//do nothing
		}else {
			billingContact = contactPerson.getMainContactName();
			billingContact = billingContact.toUpperCase();
		}

		if (NonConfigurableConstants.ACCOUNT_CATEGORY_DIVISION.equals(account.getAccountCategory()) ||
				NonConfigurableConstants.ACCOUNT_CATEGORY_DEPARTMENT.equals(account.getAccountCategory()) ||
				NonConfigurableConstants.ACCOUNT_CATEGORY_SUB_APPLICANT.equals(account.getAccountCategory()))
		{
			billedTo = account.getAccountName() + " (" + account.getCode() + ")";
		}
		else
			billedTo = account.getAccountName();


		Properties params = new Properties();
		params.put("invoiceHeaderNo", invoiceHeaderNo);
		if(isDraft){
			params.put("invoiceHeaderTbl", "bmtb_draft_inv_header");
			params.put("invoiceSummaryTbl", "bmtb_draft_inv_summary");
			params.put("isDraft", "Y");

		} else {
			params.put("invoiceHeaderTbl", "bmtb_invoice_header");
			params.put("invoiceSummaryTbl", "bmtb_invoice_summary");
			params.put("isDraft", "N");
		}

		params.put("customer name", customerName.replaceAll("'", "''"));
		params.put("bill to account name", billedTo.replaceAll("'", "''"));
		params.put("address 1", address1.replaceAll("'", "''"));
		if(address2!=null) {
			params.put("address 2", address2.replaceAll("'", "''"));
		}
		if(address3!=null) {
			params.put("address 3", address3.replaceAll("'", "''"));
		}
		params.put("address 4", address4.replaceAll("'", "''"));
		params.put("billing contact", billingContact.replaceAll("'", "''"));
		params.put("postal", contactPerson.getAddressPostal().replaceAll("'", "''"));
		@SuppressWarnings("rawtypes")
		Map pdfGenProperties = (Map)SpringUtil.getBean("pdfGenProperties");
		Integer.parseInt((String)pdfGenProperties.get("pdfgen.buffer.duedate"));
		params.put("bufferDays", pdfGenProperties.get("pdfgen.buffer.duedate"));

		String outputFormat = Constants.FORMAT_PDF;
		String reportName = NonConfigurableConstants.REPORT_NAME_INV_MISC_PREPAID;

		byte[] bytes =  generate(reportName, null, outputFormat, params);

		return bytes;

	}

	public List<Object[]> getEmailAudit(String dateFrom, String dateTo) {
		return this.daoHelper.getReportDao().getEmailAudit(dateFrom, dateTo);
	}
	public List<Object[]> getBirthdayAnnouncement(String birthdayDateFrom, String birthdayDateTo, String joinDateFrom, String joinDateTo, String accountStatus) {
		return this.daoHelper.getReportDao().getBirthdayAnnouncement(birthdayDateFrom, birthdayDateTo, joinDateFrom, joinDateTo, accountStatus);
	}

	public List<Object[]> getAydenPaymentMatchingSummaryReport(String settlementStartDate, String settlementEndDate, String batchNo, String recordType1, String recordType2){
		logger.info("getAydenPaymentMatchingSummaryReport(settlementStartDate = "+settlementStartDate+", settlementEndDate = "+settlementEndDate+", batchNo = "+batchNo+", recordType1 = "+recordType1+", recordType2 = "+recordType2+")");
		return this.daoHelper.getReportDao().getAydenPaymentMatchingSummaryReport(settlementStartDate,settlementEndDate,batchNo,recordType1,recordType2);
	}

	public List<Object[]> getAydenPaymentMatchingSummaryReport2(String settlementStartDate, String settlementEndDate, String batchNo, String recordType1, String recordType2){
		return this.daoHelper.getReportDao().getAydenPaymentMatchingSummaryReport2(settlementStartDate,settlementEndDate,batchNo,recordType1, recordType2);
	}

	public List<Object[]> getAydenPaymentMatchingExcessAmount(String settlementStartDate, String settlementEndDate, String batchNo){
		return this.daoHelper.getReportDao().getAydenPaymentMatchingExcessAmount(settlementStartDate,settlementEndDate,batchNo);
	}

	public List<TmtbNonBillableTxnCrca> getAydenPaymentMatchingBreakdownReport(String settlementStartDate, String settlementEndDate, String batchNo, String recordType1, String recordType2){
		return this.daoHelper.getReportDao().getAydenPaymentMatchingBreakdownReport(settlementStartDate,settlementEndDate,batchNo,recordType1, recordType2);
	}
	
	public List<AmtbAcctStatus> getAccountstsAllbyAccnoandAccName(String custNo, String custName){
		return this.daoHelper.getAccountDao().getAccountStatusAll(custNo, custName);
	}

}