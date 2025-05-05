package com.cdgtaxi.ibs.product.business;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import org.apache.log4j.Logger;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Messagebox;

import com.cdgtaxi.ibs.acl.model.SatbUser;
import com.cdgtaxi.ibs.common.ConfigurableConstants;
import com.cdgtaxi.ibs.common.NonConfigurableConstants;
import com.cdgtaxi.ibs.common.business.GenericBusinessImpl;
import com.cdgtaxi.ibs.common.model.AmtbAccount;
import com.cdgtaxi.ibs.common.model.AmtbAcctMainContact;
import com.cdgtaxi.ibs.common.model.AmtbAcctStatus;
import com.cdgtaxi.ibs.common.model.AmtbContactPerson;
import com.cdgtaxi.ibs.common.model.AmtbCredRevReq;
import com.cdgtaxi.ibs.common.model.AmtbCredRevReqFlow;
import com.cdgtaxi.ibs.common.model.AmtbSubscTo;
import com.cdgtaxi.ibs.common.model.FmtbEntityMaster;
import com.cdgtaxi.ibs.common.model.IttbCpCustCardIssuance;
import com.cdgtaxi.ibs.common.model.IttbCpLoginArchivedNew;
import com.cdgtaxi.ibs.common.model.IttbCpLoginNew;
import com.cdgtaxi.ibs.common.model.IttbRecurringCharge;
import com.cdgtaxi.ibs.common.model.IttbRecurringChargeTagAcct;
import com.cdgtaxi.ibs.common.model.IttbRecurringChargeTagCard;
import com.cdgtaxi.ibs.common.model.PmtbCardNoSequence;
import com.cdgtaxi.ibs.common.model.PmtbPrepaidCardTxn;
import com.cdgtaxi.ibs.common.model.PmtbProduct;
import com.cdgtaxi.ibs.common.model.PmtbProductCreditLimit;
import com.cdgtaxi.ibs.common.model.PmtbProductRenew;
import com.cdgtaxi.ibs.common.model.PmtbProductReplacement;
import com.cdgtaxi.ibs.common.model.PmtbProductRetag;
import com.cdgtaxi.ibs.common.model.PmtbProductStatus;
import com.cdgtaxi.ibs.common.model.PmtbProductType;
import com.cdgtaxi.ibs.common.model.PmtbVirtualEmail;
import com.cdgtaxi.ibs.common.model.forms.ApplyAmtInfo;
import com.cdgtaxi.ibs.common.model.forms.GstInfo;
import com.cdgtaxi.ibs.common.model.forms.IssueProductForm;
import com.cdgtaxi.ibs.common.model.forms.IssueProductForm.IssueType;
import com.cdgtaxi.ibs.common.model.forms.TransferableAmtInfo;
import com.cdgtaxi.ibs.common.ui.CommonWindow;
import com.cdgtaxi.ibs.interfaces.as.api.API;
import com.cdgtaxi.ibs.master.model.MstbMasterTable;
import com.cdgtaxi.ibs.product.ui.ProductSearchCriteria;
import com.cdgtaxi.ibs.util.AccountUtil;
import com.cdgtaxi.ibs.util.CardNoGenerator;
import com.cdgtaxi.ibs.util.DateUtil;
import com.cdgtaxi.ibs.util.GstUtil;
import com.cdgtaxi.ibs.util.LoggerUtil;
import com.cdgtaxi.ibs.util.PrepaidUtil;
import com.cdgtaxi.ibs.util.StringUtil;
import com.google.common.base.Strings;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;

public class ProductBusinessImpl extends GenericBusinessImpl implements ProductBusiness{
	
	private static final Logger logger = Logger.getLogger(ProductBusinessImpl.class);
	
	public Map<String,String> getAccountNoAndName(String accNo, String name){
	
		Map<String, String> returnAccountMap =null;
		List <AmtbAccount> accounts=this.daoHelper.getAccountDao().getAccountNoAndName(accNo,name);
		if(accounts!=null){
			returnAccountMap = new LinkedHashMap<String,String>();
			for(AmtbAccount account : accounts){
				
				if(account.getCustNo()!=null)
					returnAccountMap.put(account.getAccountNo().toString(), account.getAccountName()+" ("+account.getCustNo()+")");
				else
					returnAccountMap.put(account.getAccountNo().toString(), account.getAccountName());
			}
		}
		return returnAccountMap ; 
	}
	
	public Map<String,Map<String,String>> getDivOrSubApplInfo(String accountNo, String type){
		
		logger.info("Getting  getDivOrSubApplInfo by account No..  : ");
		Map<String, Map<String, String>> returnMap = new LinkedHashMap<String, Map<String, String>>();
		List <AmtbAccount> accounts;
		
		if(type.equals("new"))
			accounts = this.daoHelper.getAccountDao().getDivOrSubApplInfo2(accountNo);
		else	
			accounts = this.daoHelper.getAccountDao().getDivOrSubApplInfo(accountNo);
		
		//logger.info("Size of the list "+accounts.size());
		if(accounts!=null){
			for(AmtbAccount account : accounts){
				Map<String, String> accountMap = new LinkedHashMap<String, String>();
				
				if(account.getCode()!=null)
					accountMap.put("divOrSubApplName",account.getAccountName()+" ("+account.getCode()+")");
				else
					accountMap.put("divOrSubApplName",account.getAccountName());
//				logger.info("Name of the account"+account.getAccountName());
				accountMap.put("accountNameOnCard",account.getNameOnCard());
				accountMap.put("accountCategory",account.getAccountCategory());
				returnMap.put(account.getAccountNo().toString(), accountMap);
			}
			return returnMap ;
		}else return null;
	}
	
	public Map<String,Map<String,String>> getDepartmentInfo(String accountNo){
		
		logger.info("Getting Department List ..  : ");
		Map<String, Map<String, String>> returnMap = new LinkedHashMap<String, Map<String, String>>();
		List <AmtbAccount> accounts=this.daoHelper.getAccountDao().getDepartmentInfoByDivisionAcctNo(accountNo);
//		logger.info("Size of the list "+accounts.size());
			if(accounts!=null){
			for(AmtbAccount account : accounts){
				Map<String, String> accountMap = new LinkedHashMap<String, String>();
				
				if(account.getCode()!=null)
					accountMap.put("departmentName",account.getAccountName()+" ("+account.getCode()+")");
				else
					accountMap.put("departmentName",account.getAccountName());
				logger.info("Name of the account"+account.getAccountName());
				returnMap.put(account.getAccountNo().toString(), accountMap);
			}
			return returnMap ;
		}else return null;
	}
	
	public Map<String,Map<String,String>> getAccountInfo(String accountNo){
		
		logger.info("Getting  getDivOrSubApplInfo by account No..  : "+accountNo);
		Map<String, Map<String, String>> returnMap = new LinkedHashMap<String, Map<String, String>>();
		AmtbAccount account=this.daoHelper.getAccountDao().getAccountInfo(accountNo);
		//AmtbAcctMainContact mainContact=(AmtbAcctMainContact)this.daoHelper.getGenericDao().get(AmtbAcctMainContact.class,new AmtbAcctMainContactPK(NonConfigurableConstants.MAIN_CONTACT_TYPE_SHIPPING,account));
		AmtbAcctMainContact mainContact = null;
		AmtbContactPerson contactPerson=null;
		AmtbContactPerson contactPersonCountry=null;


		Iterator<AmtbAcctMainContact> iter = account.getAmtbAcctMainContacts().iterator();
		// Always get the first one since there is only 1 main contact for shipping or billing
		if (iter.hasNext())
		{
			mainContact = iter.next();
			if(mainContact!=null){
				contactPerson=(AmtbContactPerson)this.daoHelper.getGenericDao().get(AmtbContactPerson.class,new Integer (mainContact.getAmtbContactPerson().getContactPersonNo()));
			}
			if(contactPerson!=null)
				contactPersonCountry=(AmtbContactPerson)this.daoHelper.getProductDao().getcontactPersonCountry(contactPerson);
			else{
				logger.info("TESTING....Contact Person Null.........");
			}
		}
		//MstbMasterTable masterTable = new MstbMasterTable(); masterTable.setMasterNo(contactPerson.getMstbMasterTableByAddressCountry().getMasterNo());
		//MstbMasterTable masterCountry=(MstbMasterTable)this.daoHelper.getGenericDao().get(MstbMasterTable.class,masterTable);
		String divOrSubApplName="";
		
		if(account!=null){
			Map<String, String> accountMap = new LinkedHashMap<String, String>();
			//logger.info("TESTING"+account.getCustNo());
			accountMap.put("custNo",account.getCustNo());//customer No is to show the user, account No is to use at backend only, customer No will be null for all except coporate account 
			divOrSubApplName=account.getAccountName();
			if(account.getCode()!=null)
				divOrSubApplName+=account.getCode();
			accountMap.put("divOrSubApplName",divOrSubApplName);
			accountMap.put("accountNameOnCard",account.getNameOnCard());
			accountMap.put("accountCategory",account.getAccountCategory());
			if(account.getCreditLimit()!=null)
				accountMap.put("parentCreditLimit",StringUtil.bigDecimalToString(account.getCreditLimit(),StringUtil.GLOBAL_DECIMAL_FORMAT));
			else
				accountMap.put("parentCreditLimit","-");
			
			accountMap.put("smsExpiryFlag", account.getSmsExpiryFlag());
			
			accountMap.put("smsTopUpFlag", account.getSmsTopupFlag());
			
			if(contactPerson!=null){
				//logger.info("Hello Contact Person is not null.............");
//				if(contactPerson.getMstbMasterTableByMainContactSal().getMasterValue()!=null)
//					accountMap.put("contactName",contactPerson.getMstbMasterTableByMainContactSal().getMasterValue()+" "+contactPerson.getMainContactName());
//				else
					accountMap.put("contactName",contactPerson.getMainContactName());
					
				String contactNo="";
				if(contactPerson.getMainContactMobile()!=null){
					contactNo=contactPerson.getMainContactMobile();
					if(contactPerson.getMainContactTel()!=null)
						contactNo+=", "+contactPerson.getMainContactTel();
				}
				else{
					if(contactPerson.getMainContactTel()!=null)
						contactNo=contactPerson.getMainContactTel();
					else
						contactNo="-";
				}
					//
				//[AREA][BLOCK] [STREET] [UNIT][BUILDING NAME][COUNTRY][STATE, CITY (hide if SINGAPORE)] [POSTAL CODE]
				//
				accountMap.put("contactNumber",contactNo);
				String block=contactPerson.getAddressBlock();
				String building=contactPerson.getAddressBuilding();
				String area=contactPerson.getAddressArea();
				String city=contactPerson.getAddressCity();
				String state=contactPerson.getAddressCity();
				String country=contactPersonCountry.getMstbMasterTableByAddressCountry().getMasterValue();
				String postal=contactPerson.getAddressPostal();
				String street=contactPerson.getAddressStreet();
				String unit=contactPerson.getAddressUnit();
				String address="";
				//logger.info("Country"+country);
				//logger.info("Postal"+postal);
				if(area!=null)
					address=area+", ";
				if(block!=null) 
					address=block+", ";	//address="Blk "+block+", ";
				if(street!=null) 
					address=address+street+", ";
				if(unit!=null) 
					address=address+unit+", ";
				if(building!=null)
					address=address+building+", ";
				if(country!=null) 
					address=address+country+" ";
				if(state!=null)
					address=address+state+", ";
				if(city!=null)
					address=address+city+", ";
				if(postal!=null)
					address=address+postal;
				
				
				accountMap.put("contactAddress",address);
			}
			else{
				accountMap.put("contactName","-");
				accountMap.put("contactNumber","-");
				accountMap.put("contactAddress","-");
			}
			if(account.getCustNo()!=null)
				returnMap.put(account.getCustNo().toString(),accountMap);
			else
				returnMap.put(account.getAccountNo().toString(),accountMap);
			
		}
		
		
		return returnMap ; 
	}
	
	public List<PmtbProductType> getIssuableProductTypes(String accNo, boolean isPrepaid){
		
		List<String> productTypeIds = Lists.newArrayList();
		List<AmtbSubscTo> subscToList =this.daoHelper.getProductTypeDao().getAmtbSubscToForIssueProduct(accNo, isPrepaid);
		for(AmtbSubscTo subscTo : subscToList){
			
			PmtbProductType productType = subscTo.getComp_id().getPmtbProductType();
			productTypeIds.add(productType.getProductTypeId());
			
			logger.debug("info: " + productType.getProductTypeId());
		}
		
		return this.daoHelper.getProductTypeDao().getProductTypes(productTypeIds);
	}
	
	public Map<String,String> getAllProductTypes(){
		
		Map<String, String> returnMap = new LinkedHashMap<String, String>();
		List<PmtbProductType> productTypes=this.daoHelper.getProductTypeDao().getAllProductTypes();
		TreeSet<PmtbProductType> sortedProductTypes = new TreeSet<PmtbProductType>(new Comparator<PmtbProductType>(){
			public int compare(PmtbProductType o1, PmtbProductType o2) {
				return o1.getName().compareTo(o2.getName());
			}
		});
		sortedProductTypes.addAll(productTypes);
		for(PmtbProductType productTypeId : sortedProductTypes)
			returnMap.put(productTypeId.getProductTypeId(),productTypeId.getName());
		if(productTypes!=null) return returnMap ;
		else return null;
		
	}
	
	public PmtbProductStatus getLatestProductStatus(String cardNo, Timestamp tripDt)
	{
		return this.daoHelper.getProductDao().getLatestProductStatus(cardNo, tripDt);
	}
	
	//this is to be deprecated, weird and poor performance way to calculate next card no
	public String getMaxCardno(PmtbProductType productType){
		
		String cardno="";
		cardno=this.daoHelper.getProductDao().getMaxCardNo(productType);
		return cardno;
	}
	
	
	public PmtbCardNoSequence getCardNoSequence(PmtbProductType productType){
		
		
		String binRange = productType.getBinRange();
		String subBinRange = productType.getSubBinRange();
		Integer numberOfDigit = productType.getNumberOfDigit();
		
		PmtbCardNoSequence seq = this.daoHelper.getProductDao().getCardNoSequence(binRange, subBinRange, numberOfDigit);
		if(seq==null){
			
			logger.debug("Card No Sequence for productType: " + productType.getProductTypeId() + " is empty. Create and save the new sequence");
			
			String processedCardNoStr = retrieveCurrentCardNo(productType);
			if(Strings.isNullOrEmpty(processedCardNoStr)){
				processedCardNoStr = "0";
			}
			
			BigDecimal processedCardNo = new BigDecimal(processedCardNoStr);
			
			seq = new PmtbCardNoSequence();
			seq.setBinRange(binRange);
			seq.setSubBinRange(subBinRange);
			seq.setNumberOfDigit(numberOfDigit);
			seq.setCount(processedCardNo);
			super.save(seq);
		} else {
			logger.debug("Retrieving Card No Sequence: " + seq.getSeqId());
			
		}
		
		return seq;
		
	}
	
	
	public String retrieveCurrentCardNo(PmtbProductType productType){
		
		String binRange = productType.getBinRange();
		String subBinRange = productType.getSubBinRange();
		Integer numberOfDigit = productType.getNumberOfDigit();
		
		String maxCardNo = this.daoHelper.getProductDao().retrieveMaxCardNo(binRange, subBinRange, numberOfDigit);
		if(maxCardNo==null){
			logger.debug("Max Card No not found.");
			return null;
		}
		
		String cardNum = maxCardNo;
		
		if(!Strings.isNullOrEmpty(binRange) && !NonConfigurableConstants.BOOLEAN_NA.equals(binRange)){
			
			if(!cardNum.startsWith(binRange)){
				logger.debug("Error while processing bin range. Bin range is: " + binRange +". Max Card No is: " + maxCardNo);
				throw new WrongValueException("Something error while populating the card no, please contact administrator!");
			}
			cardNum = cardNum.replaceFirst(binRange, "");
		}
		
		if(!Strings.isNullOrEmpty(subBinRange) && !NonConfigurableConstants.BOOLEAN_NA.equals(subBinRange)){
			
			if(!cardNum.startsWith(subBinRange)){
				logger.debug("Error while processing sub bin range. Sub bin range is: " + subBinRange +" Max Card No is: " + maxCardNo);
				throw new WrongValueException("Something error while populating the card no, please contact administrator!");
			}
			cardNum = cardNum.replaceFirst(subBinRange, "");
		}
		
		
		if(NonConfigurableConstants.getBoolean(productType.getLuhnCheck())){
			cardNum = cardNum.substring(0, cardNum.length()-1);
		}
		
		logger.debug("Processed card number is: " + cardNum);
	
		return 	cardNum;
		
		
	
	}
	
	

	public Map<String,Map<String,String>> getProducts(ProductSearchCriteria productSearchCriteria){
		logger.info("");
		Map<String, Map<String, String>> returnMap = new LinkedHashMap<String, Map<String, String>>();
		PmtbProductType productType=new PmtbProductType();
		if(!productSearchCriteria.getProductType().equalsIgnoreCase("-"))
			productType=(PmtbProductType)this.daoHelper.getProductTypeDao().getProductType(productSearchCriteria.getProductType());
		else productType=null;
		List<AmtbAccount> accounts=null;
		List <PmtbProduct> products=null;
		AmtbAccount parentAccount=null;
		HashSet<Integer> accountIdSet=new HashSet<Integer>();

		if(productSearchCriteria.getAccNo()!=null && productSearchCriteria.getAccNo().trim().length()>1 ){
			
			logger.info("By Account No"+productSearchCriteria.getAccNo());
			parentAccount=(AmtbAccount)this.daoHelper.getGenericDao().get(AmtbAccount.class,new Integer(productSearchCriteria.getAccNo()));
			if(parentAccount!=null){
				//products=this.daoHelper.getProductDao().getAllProductsbyParentID(Integer.parseInt(productSearchCriteria.getAccNo()),productSearchCriteria,productType);
				
				products=this.daoHelper.getProductDao().getAllProductsbyParentID(parentAccount.getAccountNo(),productSearchCriteria,productType);
				
			}else
				products=null;
		}else if(productSearchCriteria.getCustNo()!=null && productSearchCriteria.getCustNo().trim().length()>1){
			
			parentAccount=(AmtbAccount)this.daoHelper.getAccountDao().getAccountByCustNo(productSearchCriteria.getCustNo());
			if(parentAccount!=null){
				products=this.daoHelper.getProductDao().getAllProductsbyParentID(parentAccount.getAccountNo(),productSearchCriteria,productType);
				
			}else
			products=null;
		
		}else
			products=this.daoHelper.getProductDao().getProducts(productSearchCriteria,productType);
	
		if(products!=null){
			logger.info("Total Products:"+products.size());
			int testCount=1;
			for(PmtbProduct product : products){
				Map<String, String> productMap = new LinkedHashMap<String, String>();
				//AmtbAccount account=product.getAmtbAccount();

				//try{
				String category=product.getAmtbAccount().getAccountCategory();
				productMap.put("accountCategory",category);
				if (category.equalsIgnoreCase(NonConfigurableConstants.ACCOUNT_CATEGORY_CORPORATE) || 
						category.equalsIgnoreCase(NonConfigurableConstants.ACCOUNT_CATEGORY_APPLICANT))
				{
					productMap.put("parentAccountName",product.getAmtbAccount().getAccountName());
					productMap.put("parentAccountNo",product.getAmtbAccount().getCustNo());
					productMap.put("divSappName","-");
					productMap.put("deptName","-");
				}
				else if(category.equalsIgnoreCase(NonConfigurableConstants.ACCOUNT_CATEGORY_DIVISION) || 
						category.equalsIgnoreCase(NonConfigurableConstants.ACCOUNT_CATEGORY_SUB_APPLICANT))
				{
					productMap.put("parentAccountName",product.getAmtbAccount().getAmtbAccount().getAccountName());
					productMap.put("parentAccountNo",product.getAmtbAccount().getAmtbAccount().getCustNo());
					productMap.put("divSappName",product.getAmtbAccount().getAccountName() + " (" + product.getAmtbAccount().getCode()+")");
					productMap.put("deptName","-");
				}
				else if (category.equalsIgnoreCase(NonConfigurableConstants.ACCOUNT_CATEGORY_DEPARTMENT)){
					productMap.put("parentAccountName",product.getAmtbAccount().getAmtbAccount().getAmtbAccount().getAccountName());
					productMap.put("parentAccountNo",product.getAmtbAccount().getAmtbAccount().getAmtbAccount().getCustNo());
					productMap.put("divSappName",product.getAmtbAccount().getAmtbAccount().getAccountName() + " (" + product.getAmtbAccount().getAmtbAccount().getCode()+")");
					productMap.put("deptName",product.getAmtbAccount().getAccountName()+" ("+product.getAmtbAccount().getCode()+")");
				}
				else{
					productMap.put("divSappName","-");
					productMap.put("deptName","-");
				}
				
				
				// Retrieve the first contact since it is the main contact.
				productMap.put("contact", "-");
				for(AmtbAcctMainContact main : product.getAmtbAccount().getAmtbAcctMainContacts()){
					if(main.getComp_id().getMainContactType().equals(NonConfigurableConstants.MAIN_CONTACT_TYPE_SHIPPING)){
						productMap.put("contact", main.getAmtbContactPerson().getMainContactName());
						break;
					}
				}
				// fixed for different contact person name when only input card no
//				Iterator<AmtbAcctMainContact> iter = product.getAmtbAccount()
//						.getAmtbAcctMainContacts().iterator();
//				if (iter.hasNext())
//					productMap.put("contact", iter.next()
//							.getAmtbContactPerson().getMainContactName());
//				else
//					productMap.put("contact", "-");
				//changed the code from product Name to ID according to the format sent from comfort
				productMap.put("productType",product.getPmtbProductType().getProductTypeId());
				productMap.put("cardNo",product.getCardNo());
				//changed the code from Card Holder Name to Name on Card according to the format sent from comfort
				
				if(product.getNameOnProduct()!=null)
					productMap.put("nameOnCard",product.getNameOnProduct());
				else
					productMap.put("nameOnCard","-");
					
				productMap.put("issueDate",DateUtil.convertDateToStr(product.getIssueDate(),DateUtil.GLOBAL_DATE_FORMAT));
				productMap.put("expiryDate",DateUtil.convertDateToStr(product.getExpiryDate(),DateUtil.GLOBAL_EXPIRY_DATE_FORMAT));
				
				
				if(product.getBalanceExpiryDate()!=null)
					productMap.put("balanceExpiryDate",DateUtil.convertDateToStr(product.getBalanceExpiryDate(),DateUtil.GLOBAL_DATE_FORMAT));
				else
					productMap.put("balanceExpiryDate","-");
				if(product.getCurrentStatus().equalsIgnoreCase(NonConfigurableConstants.PRODUCT_STATUS_SUSPENDED)){
					//PmtbProductStatus productStatus=this.daoHelper.getProductDao().getProductStatus(product);
					PmtbProductStatus productStatus=product.getPmtbProductStatuses().iterator().next();
					if(productStatus!=null){
						productMap.put("suspendDate",DateUtil.convertDateToStr(productStatus.getStatusDt(),DateUtil.GLOBAL_DATE_FORMAT));
					}			
				}else
					productMap.put("suspendDate","-");
				
				productMap.put("status",NonConfigurableConstants.PRODUCT_STATUS.get(product.getCurrentStatus())); //
				
				//logger.info("PRODUCT"+testCount+"="+product.getProductNo().toString());
				testCount++;
				returnMap.put(product.getProductNo().toString(), productMap);
				
				if(product.getEmployeeId()!=null)
					productMap.put("employeeId",product.getEmployeeId());
				else
					productMap.put("employeeId","-");
				
				if(product.getCardHolderEmail()!=null)
					productMap.put("cardHolderEmail",product.getCardHolderEmail());
				else
					productMap.put("cardHolderEmail","-");
				
				if(product.getCardHolderMobile()!=null)
					productMap.put("cardHolderMobile",product.getCardHolderMobile());
				else
					productMap.put("cardHolderMobile","-");
				
			}
		}
		if(returnMap!=null)logger.info("CHECK SIZE"+returnMap.size());
		return returnMap ; 
	}
	
	public AmtbAccount getAccount(String accNo){
		AmtbAccount account=new AmtbAccount();
		account=(AmtbAccount)this.daoHelper.getGenericDao().get(AmtbAccount.class, new Integer(accNo));
		if(account==null) logger.info("Here is the NULL");
		return account;
	}
	

	public List<IttbRecurringChargeTagAcct> getRecurringChargeTagAcct(String tokenId){
		List<IttbRecurringChargeTagAcct> rcTagAcctList = Lists.newArrayList();
		
		List<IttbRecurringChargeTagAcct> rcTagAcctSet = this.daoHelper.getAccountDao().getRecurringChargeTagAcct(tokenId);
		
		if(rcTagAcctSet!=null){
			rcTagAcctList.addAll(rcTagAcctSet);
		}

		return rcTagAcctList;
		
	}
	
	public List<IttbRecurringChargeTagCard> getRecurringChargeTagCard(String tokenId){
		List<IttbRecurringChargeTagCard> rcTagCardList = Lists.newArrayList();
		
		List<IttbRecurringChargeTagCard> rcTagCardSet = this.daoHelper.getAccountDao().getRecurringChargeTagCard(tokenId);
		
		if(rcTagCardSet!=null){
			rcTagCardList.addAll(rcTagCardSet);
		}

		return rcTagCardList;
		
	}
	public List<IttbRecurringCharge> searchRConly(String tokenId){
		List<IttbRecurringCharge> rcList = Lists.newArrayList();
		
		List<IttbRecurringCharge> rcSet = this.daoHelper.getAccountDao().searchRConly(tokenId);
		
		if(rcSet!=null){
			rcList.addAll(rcSet);
		}
		rcList.size();
		return rcList;
		
	}
	
	public List<IttbRecurringCharge> searchRC(ProductSearchCriteria productSearchCriteria){
		List<IttbRecurringCharge> rcList = Lists.newArrayList();
		
		List<IttbRecurringCharge> rcSet = this.daoHelper.getAccountDao().searchRC(productSearchCriteria);
		
		if(rcSet!=null){
			rcList.addAll(rcSet);
		}
		rcList.size();
		return rcList;
		
	}
	
	public List<IttbRecurringCharge> searchRC2(ProductSearchCriteria productSearchCriteria){
		List<IttbRecurringCharge> rcList = Lists.newArrayList();
		
		List<IttbRecurringCharge> rcSet = this.daoHelper.getAccountDao().searchRC2(productSearchCriteria);
		
		if(rcSet!=null){
			rcList.addAll(rcSet);
		}
		rcList.size();
		return rcList;
		
	}
	
	public AmtbAccount getAccountWithParent(String accNo){

		return this.daoHelper.getAccountDao().getAccountWithParent(accNo);
	}
	
	public AmtbAccount getAccountWithParentAndChildren(String accountNo){
		return this.daoHelper.getAccountDao().getAccount(accountNo);
	}

	
	public List<PmtbProduct> getProductsbyIds(Collection<BigDecimal> productIds) {
		
		List<PmtbProduct> products = Lists.newArrayList();
		Iterable<List<BigDecimal>> productIdChunks = Iterables.partition(productIds, 1000);
		for(List<BigDecimal> chunk: productIdChunks){
			
			List<PmtbProduct> productSet = this.daoHelper.getProductDao().getProductsbyIdSet(chunk);
			if(productSet!=null){
				products.addAll(productSet);
			}
		}
		
		return products;
	}

	public Map<String, Map<String, String>> getProductsbyIdSet(HashSet<BigDecimal> productIdSet) {
		return getProductsbyIdSet(productIdSet, false);
	}
	//really no idea why things should be happen to be so complex
	public Map<String, Map<String, String>> getProductsbyIdSet(HashSet<BigDecimal> productIdSet, boolean forAssignCard) {
		
		logger.info("Getting HERE  getProducts..  : ");
		Map<String, Map<String, String>> returnMap = new LinkedHashMap<String, Map<String, String>>();
		//PmtbProductType productType=new PmtbProductType();
		
		//start code
		
		int count=0;
		int countItem=1; //To retrive the product 1000 at a time. Hibernate select something in(?) cannot provide  more than 1000
		int maxCount=productIdSet.size();
		int countId=1;
		//Map<String, Map<String, String>> dataMap=this.businessHelper.getProductBusiness().getProductsbyIdSet((HashSet<BigDecimal>)productIdSet);
		List <PmtbProduct> products=new ArrayList<PmtbProduct>();
		Set<BigDecimal> productIdSetTemp = new HashSet<BigDecimal> ();
		
		if(productIdSet.size()>1000){
			Set<BigDecimal> productIdSetAll = new HashSet<BigDecimal> ();
			Iterator<BigDecimal> it = productIdSet.iterator();
			while (it.hasNext()) {
				productIdSetAll.add((BigDecimal)it.next());
			}
			while(countItem*1000<=maxCount){
							
				it = productIdSetAll.iterator();
				while (it.hasNext()) {
			        // Get element
					productIdSetTemp.add((BigDecimal)(it.next()));
			    	it.remove();
					if(countId==1000) break;
			    	countId++;
			    }
				countId=1;
				countItem++;
				if(productIdSetTemp.size()>0){
					logger.info("SIZE"+productIdSetTemp.size());
					if(forAssignCard)
						products.addAll(this.daoHelper.getProductDao().getProductsAssignCardbyIdSet((HashSet<BigDecimal>)productIdSetTemp));
					else
						products.addAll(this.daoHelper.getProductDao().getProductsbyIdSet((HashSet<BigDecimal>)productIdSetTemp));
					productIdSetTemp = new HashSet<BigDecimal> ();
				}
			}
			if(productIdSetAll.size()!=0){
				logger.info("SIZE"+productIdSetAll.size());
				if(forAssignCard)
					products.addAll(this.daoHelper.getProductDao().getProductsAssignCardbyIdSet((HashSet<BigDecimal>)productIdSetAll));
				else
					products.addAll(this.daoHelper.getProductDao().getProductsbyIdSet((HashSet<BigDecimal>)productIdSetAll));
			}
		}else
		{
			if(forAssignCard)
				products=this.daoHelper.getProductDao().getProductsAssignCardbyIdSet(productIdSet);
			else
				products=this.daoHelper.getProductDao().getProductsbyIdSet(productIdSet);
		}
		//end of codes
		//List <PmtbProduct> products=this.daoHelper.getProductDao().getProductsbyIdSet(productIdSet);
		logger.info("products SIZE"+products.size());
		if(products!=null){
			for(PmtbProduct product : products){
				
				Map<String, String> productMap = new LinkedHashMap<String, String>();
				
				String category=product.getAmtbAccount().getAccountCategory();
				
				productMap.put("accountCategory",category);
				if(category.equalsIgnoreCase(NonConfigurableConstants.ACCOUNT_CATEGORY_CORPORATE) || 
						category.equalsIgnoreCase(NonConfigurableConstants.ACCOUNT_CATEGORY_APPLICANT)){
					productMap.put("accountName",product.getAmtbAccount().getAccountName());
					productMap.put("parentAccountNo",product.getAmtbAccount().getCustNo());
				}
				else if(category.equalsIgnoreCase(NonConfigurableConstants.ACCOUNT_CATEGORY_DIVISION) || 
						category.equalsIgnoreCase(NonConfigurableConstants.ACCOUNT_CATEGORY_SUB_APPLICANT))
				{	
					if (product.getAmtbAccount().getCode() != null)
						productMap.put("divSappName",product.getAmtbAccount().getAccountName()+" ("+product.getAmtbAccount().getCode()+")");
					else
						productMap.put("divSappName",product.getAmtbAccount().getAccountName());

					productMap.put("accountName",product.getAmtbAccount().getAmtbAccount().getAccountName());
					productMap.put("parentAccountNo",product.getAmtbAccount().getAmtbAccount().getCustNo());
				}
				else if (category.equalsIgnoreCase(NonConfigurableConstants.ACCOUNT_CATEGORY_DEPARTMENT)){
					if (product.getAmtbAccount().getAmtbAccount().getCode() != null)
						productMap.put("divSappName",product.getAmtbAccount().getAmtbAccount().getAccountName()+" ("+product.getAmtbAccount().getAmtbAccount().getCode()+")");
					else
						productMap.put("divSappName",product.getAmtbAccount().getAmtbAccount().getAccountName());

					productMap.put("accountName",product.getAmtbAccount().getAmtbAccount().getAmtbAccount().getAccountName());
					productMap.put("parentAccountNo",product.getAmtbAccount().getAmtbAccount().getAmtbAccount().getCustNo());
					productMap.put("deptName",product.getAmtbAccount().getAccountName()+" ("+product.getAmtbAccount().getCode()+")");
				}
				else
				{
					// Should not come here unless it is an unknown category
				}
				
				productMap.put("productType",product.getPmtbProductType().getName());
				productMap.put("cardNo",product.getCardNo());
				if(product.getCardHolderEmail()!=null)
					productMap.put("email",product.getCardHolderEmail());
				else
					productMap.put("email","-");
				if(product.getCardHolderMobile()!=null)
					productMap.put("cardHolderMobile",product.getCardHolderMobile());
				else
					productMap.put("cardHolderMobile","-");
				if(product.getEmployeeId()!=null)
					productMap.put("employeeId",product.getEmployeeId());
				else
					productMap.put("employeeId","-");
				
				if(product.getWaiveSubscFeeFlag()!=null){
					//Product table the column name is waive subscription fee and in view the label is subscription, so Waive "N" means subscription fee ="Y"
					if(product.getWaiveSubscFeeFlag().equalsIgnoreCase(NonConfigurableConstants.BOOLEAN_NO))
						productMap.put("subscriptionFees","Y");
					else
						productMap.put("subscriptionFees","N");
				}
				
				if(product.getIsIndividualCard() != null &&
						product.getIsIndividualCard().equals(NonConfigurableConstants.BOOLEAN_YES)){
					
					String cardHolder=null;
					
					if(product.getCardHolderSalutation()!=null){
						logger.info("Card Holder Salutation"+product.getCardHolderSalutation());
						cardHolder=product.getCardHolderSalutation();
					}
					if(product.getCardHolderName()!=null){
						if(cardHolder!=null)
							cardHolder+=" "+product.getCardHolderName();
						else
							cardHolder=product.getCardHolderName();
					}
				
					else{
						productMap.put("fixedValue",null);
					}
						
					if(cardHolder!=null)
						productMap.put("cardHolder",cardHolder);
					else
						productMap.put("cardHolder","-");
					
					if(product.getCardHolderTitle()!=null)
						productMap.put("position",product.getCardHolderTitle());
					else
						productMap.put("position","-");
				
					String cotactNo=null;
					if(product.getCardHolderMobile()!=null){
						cotactNo=product.getCardHolderMobile();
						if(product.getCardHolderTel()!=null)
							cotactNo+=", "+product.getCardHolderTel();
					}else{
						if(product.getCardHolderTel()!=null)
							cotactNo=product.getCardHolderTel();
					}
					
					if(cotactNo!=null)
						productMap.put("telephone",cotactNo);
					else
						productMap.put("telephone","-");
				}
				productMap.put("cardTypeId",product.getPmtbProductType().getProductTypeId());
				
				AmtbAcctStatus accountStatus=getCurrentStatus(product.getAmtbAccount().getAmtbAcctStatuses());
				//Only need to show in  Manage Specific Product, that means one by one, no need to show the Account Status for mass search
				if(products.size()==1){
					if(accountStatus!=null){
						productMap.put("accountStatus",NonConfigurableConstants.ACCOUNT_STATUS.get(accountStatus.getAcctStatus()));
						//logger.info("Status is null");
					}
					else
						productMap.put("accountStatus","-");
					
				}
				productMap.put("issueDate",DateUtil.convertDateToStr(product.getIssueDate(),DateUtil.GLOBAL_DATE_FORMAT));
				productMap.put("expiryDate",DateUtil.convertDateToStr(product.getExpiryDate(),DateUtil.GLOBAL_EXPIRY_DATE_FORMAT));
				
				if(product.getBalanceExpiryDate()!=null){
					productMap.put("balanceExpiryDate",DateUtil.convertDateToStr(product.getBalanceExpiryDate(),DateUtil.GLOBAL_DATE_TIME_MINUTE_FORMAT));
				} else {
					productMap.put("balanceExpiryDate", "-");
				}

				productMap.put("name",(String)product.getNameOnProduct());
				if(product.getCurrentStatus().equalsIgnoreCase(NonConfigurableConstants.PRODUCT_STATUS_SUSPENDED)){
					PmtbProductStatus productStatus=this.daoHelper.getProductDao().getProductStatus(product);//need to put timestamp comparison
					if (productStatus!=null)
						productMap.put("suspendDate",DateUtil.convertDateToStr(productStatus.getStatusDt(),DateUtil.GLOBAL_DATE_FORMAT));
					else 
						productMap.put("suspendDate","-");
				}
				else 
					productMap.put("suspendDate","-");
				productMap.put("status",NonConfigurableConstants.PRODUCT_STATUS.get(product.getCurrentStatus())); //
				
				productMap.put("productType",product.getPmtbProductType().getName());
				if(product.getPmtbProductType().getReplacementFees()!=null)
					productMap.put("replacementFees",StringUtil.bigDecimalToString(product.getPmtbProductType().getReplacementFees(),StringUtil.GLOBAL_DECIMAL_FORMAT));
				else
					productMap.put("replacementFees","0.00");
				if(product.getCreditLimit()!=null){
					//productMap.put("creditLimit",product.getCreditLimit().toString());
					productMap.put("creditLimit",StringUtil.bigDecimalToString(product.getCreditLimit(),StringUtil.GLOBAL_DECIMAL_FORMAT));
				}
				if(product.getTempCreditLimit()!=null){
					productMap.put("tempCreditLimit",StringUtil.bigDecimalToString(product.getTempCreditLimit(),StringUtil.GLOBAL_DECIMAL_FORMAT));
				}
				if(product.getCreditBalance()!=null){
					//productMap.put("creditBalance",product.getCreditBalance().toString());
					productMap.put("creditBalance",StringUtil.bigDecimalToString(product.getCreditBalance(),StringUtil.GLOBAL_DECIMAL_FORMAT));
					
				}
				else
					productMap.put("creditBalance","0.00");
				//productMap.put("accountName",product.getAmtbAccount().getAccountName());
				productMap.put("lastUpdatedBy", product.getUpdatedBy()==null?"-":product.getUpdatedBy());
				productMap.put("lastUpdatedDate", product.getUpdatedDt()==null?"-":DateUtil.convertTimestampToStr(product.getUpdatedDt(), DateUtil.GLOBAL_DATE_FORMAT));
				productMap.put("lastUpdatedTime", product.getUpdatedDt()==null?"-":DateUtil.convertTimestampToStr(product.getUpdatedDt(), DateUtil.GLOBAL_TIME_FORMAT));
				//Added to show fixed value
			  	//logger.info("Fixed Value "+product.getFixedValue());
				
				if(product.getFixedValue()!=null){
					//logger.info("Fixed Value "+product.getFixedValue());
					productMap.put("fixedValue",product.getFixedValue().toString());
				}
				
				productMap.put("validityPeriod", product.getPmtbProductType().getValidityPeriod());
				productMap.put("prepaid", product.getPmtbProductType().getPrepaid());
				if(product.getCardValue()!=null) productMap.put("cardValue", StringUtil.bigDecimalToString(product.getCardValue(), StringUtil.GLOBAL_DECIMAL_FORMAT));
				if(product.getCashplus()!=null) productMap.put("cashPlus", StringUtil.bigDecimalToString(product.getCashplus(), StringUtil.GLOBAL_DECIMAL_FORMAT));
				productMap.put("expiryDateUpToDay", DateUtil.convertDateToStr(product.getExpiryTime(), DateUtil.GLOBAL_DATE_FORMAT));
				productMap.put("expiryTime", DateUtil.convertDateToStr(product.getExpiryTime(), DateUtil.GLOBAL_TIME_FORMAT));
				productMap.put("nameOnCardFlag", product.getPmtbProductType().getNameOnProduct());
				productMap.put("isIndividualCard", product.getIsIndividualCard()==null?NonConfigurableConstants.BOOLEAN_NO:product.getIsIndividualCard());
				
				productMap.put("smsExpiryFlag", product.getSmsExpiryFlag());
				productMap.put("smsTopUpFlag", product.getSmsTopupFlag());
				productMap.put("contactlessFlag", product.getPmtbProductType().getContactless());
				productMap.put("offlineCount", StringUtil.numberToString(product.getOfflineCount()));
				productMap.put("offlineAmount", StringUtil.bigDecimalToString(product.getOfflineAmount(), StringUtil.GLOBAL_DECIMAL_FORMAT));
				productMap.put("offlineTxnAmount", StringUtil.bigDecimalToString(product.getOfflineTxnAmount(), StringUtil.GLOBAL_DECIMAL_FORMAT));
				productMap.put("embossNameOnCardFlag", product.getEmbossNameOnCard());

				//For Assign Card, get latest assign Date not later then today from getIttbCpCustCardIssuances 
				// and also batchflag must be Y, cause got updated in batchjob means NameOnProduct is updated at product already
				// if dont have take from product issueDate.
				if(null != product.getIttbCpCustCardIssuances()) {
					Timestamp latestIssuedOn = null;
					for(IttbCpCustCardIssuance ittb : product.getIttbCpCustCardIssuances()) {
						
						if(null == latestIssuedOn) {
							if(ittb.getIssuedOn().compareTo(new Date()) <= 0 && ittb.getBatchFlag().equalsIgnoreCase("Y")) 
								latestIssuedOn = ittb.getIssuedOn();
						}
						else {
							if((ittb.getIssuedOn().compareTo(latestIssuedOn) > 0 && (ittb.getIssuedOn().compareTo(new Date()) <= 0)) && ittb.getBatchFlag().equalsIgnoreCase("Y")) 
								latestIssuedOn = ittb.getIssuedOn();
						}
					}
					
					if(null == latestIssuedOn) 
						productMap.put("assignDate", DateUtil.convertDateToStr(product.getIssueDate(),DateUtil.LAST_UPDATED_DATE_FORMAT));
					else
						productMap.put("assignDate", DateUtil.convertTimestampToStr(latestIssuedOn, DateUtil.LAST_UPDATED_DATE_FORMAT));
				}
				
				returnMap.put(product.getProductNo().toString(), productMap);
			}
		}
		return returnMap; 
	}

	public Map<String, Map<String, String>> getRenewProductHistory(BigDecimal productId) {
		
		Map<String, Map<String, String>> returnMap = new LinkedHashMap<String, Map<String, String>>();
		List <PmtbProductRenew> renewProducts=this.daoHelper.getProductDao().getRenewProducts(productId);
		PmtbProduct product=(PmtbProduct)this.daoHelper.getGenericDao().get(PmtbProduct.class, productId);
		if(renewProducts!=null){
			for(PmtbProductRenew renewProduct : renewProducts){
				Map<String, String> productMap = new LinkedHashMap<String, String>();
				productMap.put("renewDate",DateUtil.convertDateToStr(renewProduct.getRenewDate(), DateUtil.GLOBAL_DATE_FORMAT));
				productMap.put("cardNo",product.getCardNo());
				productMap.put("currentExpiryDate",DateUtil.convertDateToStr(renewProduct.getCurrentExpDate(), DateUtil.GLOBAL_EXPIRY_DATE_FORMAT)); 
				productMap.put("newExpiryDate",DateUtil.convertDateToStr(renewProduct.getNewExpDate(), DateUtil.GLOBAL_EXPIRY_DATE_FORMAT));
				productMap.put("remarks",renewProduct.getRemarks());
				productMap.put("createdBy",renewProduct.getCreatedBy());
				productMap.put("createdDate",DateUtil.convertTimestampToStr(renewProduct.getCreatedDt(),DateUtil.GLOBAL_DATE_FORMAT));
				productMap.put("updatedBy",renewProduct.getUpdatedBy());
				productMap.put("updatedDate",DateUtil.convertTimestampToStr(renewProduct.getUpdatedDt(),DateUtil.GLOBAL_DATE_FORMAT));
				returnMap.put(renewProduct.getProductRenewNo().toString(), productMap);
			}
		}
		return returnMap; 
	}
	
	public Map<String, Map<String, String>> getCardAssignmentHistory(BigDecimal productId) {
		
		PmtbProduct product = this.daoHelper.getProductDao().getProduct(productId);
		Map<String, Map<String, String>> returnMap = new LinkedHashMap<String, Map<String, String>>();
		List <IttbCpCustCardIssuance> cardIssuances=this.daoHelper.getPortalDao().getCustomerCardIssuance(product.getCardNo());
		if(cardIssuances!=null && !cardIssuances.isEmpty()){
			for(IttbCpCustCardIssuance ittbCpCustCardIssuance : cardIssuances){
				Map<String, String> cardAssignmentHistoryMap = new LinkedHashMap<String, String>();
				cardAssignmentHistoryMap.put("cardNo",ittbCpCustCardIssuance.getCardNo());
				cardAssignmentHistoryMap.put("issuedTo",ittbCpCustCardIssuance.getIssuedTo());
				cardAssignmentHistoryMap.put("issuedOn",DateUtil.convertDateToStr(ittbCpCustCardIssuance.getIssuedOn(), DateUtil.TRIPS_DATE_FORMAT));
				if(null != ittbCpCustCardIssuance.getReturnedOn())
					cardAssignmentHistoryMap.put("returnedOn",DateUtil.convertDateToStr(ittbCpCustCardIssuance.getReturnedOn(), DateUtil.TRIPS_DATE_FORMAT));
				else
					cardAssignmentHistoryMap.put("returnedOn", "-");
				cardAssignmentHistoryMap.put("updatedBy", ittbCpCustCardIssuance.getUpdatedBy());
				cardAssignmentHistoryMap.put("updatedDt",DateUtil.convertDateToStr(ittbCpCustCardIssuance.getUpdatedDt(), DateUtil.TRIPS_DATE_FORMAT));
				cardAssignmentHistoryMap.put("createdBy", ittbCpCustCardIssuance.getCreatedBy());
				cardAssignmentHistoryMap.put("createdDt",DateUtil.convertDateToStr(ittbCpCustCardIssuance.getCreatedDt(), DateUtil.TRIPS_DATE_FORMAT));
				if(null != ittbCpCustCardIssuance.getRemarks())
				{
					if(!ittbCpCustCardIssuance.getRemarks().trim().equals(""))
						cardAssignmentHistoryMap.put("remarks", ittbCpCustCardIssuance.getRemarks());
					else
						cardAssignmentHistoryMap.put("remarks", "-");
				}
				else
					cardAssignmentHistoryMap.put("remarks", "-");
				returnMap.put(ittbCpCustCardIssuance.getIssuanceNo().toString(), cardAssignmentHistoryMap);
			}
		}
		return returnMap; 
	}
	
	public Map<String, Map<String, String>> getReplaceProductHistory(BigDecimal productId) {
		
		Map<String, Map<String, String>> returnMap = new LinkedHashMap<String, Map<String, String>>();
		PmtbProduct product = this.daoHelper.getProductDao().getProduct(productId);
		//List <PmtbProductReplacement> replaceProducts=this.daoHelper.getProductDao().getReplaceProducts(productId);
		List <PmtbProductReplacement> replaceProducts=this.daoHelper.getProductDao().getReplaceProducts(product.getCardNo());
		if(replaceProducts!=null){
			for(PmtbProductReplacement replaceProduct : replaceProducts){
				Map<String, String> productMap = new LinkedHashMap<String, String>();
				productMap.put("oldcardNo",replaceProduct.getCurrentCardNo());
				productMap.put("newcardNo",replaceProduct.getNewCardNo());
				productMap.put("replacementDate",DateUtil.convertDateToStr(replaceProduct.getReplacementDate(),DateUtil.GLOBAL_DATE_FORMAT));
				productMap.put("currentExpiryDate",DateUtil.convertDateToStr(replaceProduct.getCurrentExpDate(),DateUtil.GLOBAL_EXPIRY_DATE_FORMAT));
				productMap.put("newExpiryDate",DateUtil.convertDateToStr(replaceProduct.getNewExpDate(),DateUtil.GLOBAL_EXPIRY_DATE_FORMAT));
				if (replaceProduct.getReplacementFee() != null)
					productMap.put("charges", StringUtil.bigDecimalToString(replaceProduct.getReplacementFee(), StringUtil.GLOBAL_DECIMAL_FORMAT));
				else
					productMap.put("charges", "-");
				productMap.put("remarks",replaceProduct.getReplacementRemarks());
				productMap.put("createdBy",replaceProduct.getCreatedBy());
				productMap.put("createdDate",DateUtil.convertTimestampToStr(replaceProduct.getCreatedDt(),DateUtil.GLOBAL_DATE_FORMAT));
				productMap.put("updatedBy",replaceProduct.getUpdatedBy());
				productMap.put("updatedDate",DateUtil.convertTimestampToStr(replaceProduct.getUpdatedDt(),DateUtil.GLOBAL_DATE_FORMAT));
				returnMap.put(replaceProduct.getProductReplacementNo().toString(), productMap);
			}
		}
		return returnMap; 
	}
	
	public Map<String, Map<String, String>> getCreditLimitHistory(BigDecimal productId) {
		
		Map<String, Map<String, String>> returnMap = new LinkedHashMap<String, Map<String, String>>();
		List <PmtbProductCreditLimit > creditLimitUpdatedProducts=this.daoHelper.getProductDao().getUpdatedCreditLimtProducts(productId);
		PmtbProduct product=(PmtbProduct)this.daoHelper.getGenericDao().get(PmtbProduct.class, productId);
		
		if(creditLimitUpdatedProducts!=null){
			for(PmtbProductCreditLimit creditLimitUpdatedProduct : creditLimitUpdatedProducts){
				Map<String, String> productMap = new LinkedHashMap<String, String>();
				productMap.put("cardNo",product.getCardNo());
				productMap.put("newCreditLimit",StringUtil.bigDecimalToString(creditLimitUpdatedProduct.getNewCreditLimit(),StringUtil.GLOBAL_DECIMAL_FORMAT));
				productMap.put("effectiveDateFrom",DateUtil.convertDateToStr(creditLimitUpdatedProduct.getEffectiveDtFrom(), DateUtil.GLOBAL_DATE_FORMAT));
				if(creditLimitUpdatedProduct.getEffectiveDtTo()!=null)
					productMap.put("effectiveDateTo",DateUtil.convertDateToStr(creditLimitUpdatedProduct.getEffectiveDtTo(), DateUtil.GLOBAL_DATE_FORMAT));
				else
					productMap.put("effectiveDateTo","-");
				productMap.put("remarks",creditLimitUpdatedProduct.getRemarks());
				productMap.put("createdBy",creditLimitUpdatedProduct.getCreatedBy());
				productMap.put("createdDate",DateUtil.convertTimestampToStr(creditLimitUpdatedProduct.getCreatedDt(),DateUtil.GLOBAL_DATE_FORMAT));
				productMap.put("updatedBy",creditLimitUpdatedProduct.getUpdatedBy());
				productMap.put("updatedDate",DateUtil.convertTimestampToStr(creditLimitUpdatedProduct.getUpdatedDt(),DateUtil.GLOBAL_DATE_FORMAT));
				returnMap.put(creditLimitUpdatedProduct.getProductCreditLimitNo().toString(), productMap);
			}
		}
		return returnMap; 
	}
	
	public Map<String, Map<String, String>> getRetagHistory(BigDecimal productId) {
		
		Map<String, Map<String, String>> returnMap = new LinkedHashMap<String, Map<String, String>>();
		List <PmtbProductRetag > retagProducts=this.daoHelper.getProductDao().getRetagProducts(productId);
		PmtbProduct product=(PmtbProduct)this.daoHelper.getGenericDao().get(PmtbProduct.class, productId);
		String currentAccount="";
		String newAccount="";
		if(retagProducts!=null){
			for(PmtbProductRetag retagProduct : retagProducts){
				Map<String, String> productMap = new LinkedHashMap<String, String>();
				productMap.put("cardNo",product.getCardNo());
					try{
					if(retagProduct.getAmtbAccountByCurrentAccountNo().getCode()!=null){
						currentAccount=retagProduct.getAmtbAccountByCurrentAccountNo().getAccountName();
						currentAccount+="("+retagProduct.getAmtbAccountByCurrentAccountNo().getCode()+")";
					}
					else{
						currentAccount=retagProduct.getAmtbAccountByCurrentAccountNo().getAccountName();
						if(retagProduct.getAmtbAccountByCurrentAccountNo().getCustNo()!=null)
							currentAccount+="("+retagProduct.getAmtbAccountByCurrentAccountNo().getCustNo()+")";
					}
					
					if(retagProduct.getAmtbAccountByNewAccountNo().getCode()!=null){
						newAccount=retagProduct.getAmtbAccountByNewAccountNo().getAccountName();
						newAccount+="("+retagProduct.getAmtbAccountByNewAccountNo().getCode()+")";
					}
					else{
						newAccount=retagProduct.getAmtbAccountByNewAccountNo().getAccountName();
						if(retagProduct.getAmtbAccountByNewAccountNo().getCustNo()!=null)
							newAccount+="("+retagProduct.getAmtbAccountByNewAccountNo().getCustNo()+")";
					}
				}catch(Exception ext){logger.info("Exception Here");}
					
				productMap.put("currentAccountNo",currentAccount);
				productMap.put("newAccountNo",newAccount);
				productMap.put("effectiveDate",DateUtil.convertDateToStr(retagProduct.getEffectiveDt(), DateUtil.GLOBAL_DATE_FORMAT));
				productMap.put("remarks",retagProduct.getRetagRemarks());
				productMap.put("createdBy",retagProduct.getCreatedBy());
				productMap.put("createdDate",DateUtil.convertTimestampToStr(retagProduct.getCreatedDt(),DateUtil.GLOBAL_DATE_FORMAT));
				productMap.put("updatedBy",retagProduct.getUpdatedBy());
				productMap.put("updatedDate",DateUtil.convertTimestampToStr(retagProduct.getUpdatedDt(),DateUtil.GLOBAL_DATE_FORMAT));
				returnMap.put(retagProduct.getProductRetagNo().toString(), productMap);
			}
		}
		return returnMap; 
	}
	
	public Map<String, Map<String, String>> getProductIssuanceHistory(String accountId, String productType) {
		
		Map<String, Map<String, String>> returnMap = null;
		//AmtbAccount account=(AmtbAccount)this.daoHelper.getGenericDao().get(AmtbAccount.class, new Integer(accountId));
		List <PmtbProduct> products=this.daoHelper.getProductDao().getProductIssuanceHistory(accountId, productType);
	
		if(products!=null){
			returnMap = new LinkedHashMap<String, Map<String, String>>();
			for(PmtbProduct product : products){
				Map<String, String> productMap = new LinkedHashMap<String, String>();
				productMap.put("cardNo",product.getCardNo());
				productMap.put("cardType",product.getPmtbProductType().getName());
				//productMap.put("creditLimit",product.getCreditLimit().toString());
				if(product.getCreditLimit()!=null)
					productMap.put("creditLimit",StringUtil.bigDecimalToString(product.getCreditLimit(),StringUtil.GLOBAL_DECIMAL_FORMAT));
				if(product.getNameOnProduct()!=null)
					productMap.put("nameOnCard",product.getNameOnProduct());
				productMap.put("issueDate",DateUtil.convertDateToStr(product.getIssueDate(), DateUtil.GLOBAL_DATE_FORMAT));
				//logger.info("EXPIRY>>>>>>>>>"+product.getExpiryDate());
				//logger.info("\nIssue Date :"+product.getIssueDate()+" , "+"Created Date/Time : "+product.getCreatedDt());
				if(product.getExpiryDate()!=null)
					productMap.put("expiryDate",DateUtil.convertDateToStr(product.getExpiryDate(),DateUtil.GLOBAL_EXPIRY_DATE_FORMAT));
				else
					productMap.put("expiryDate","-");
				returnMap.put(product.getProductNo().toString(), productMap);
			}
		}
		return returnMap; 
	}
	
	public Map<String, Map<String, String>> getStatusHistory(BigDecimal productId) {
		
		Map<String, Map<String, String>> returnMap = new LinkedHashMap<String, Map<String, String>>();
		List <PmtbProductStatus> statusProducts=this.daoHelper.getProductDao().getProductStatus(productId);
		PmtbProduct product=(PmtbProduct)this.daoHelper.getGenericDao().get(PmtbProduct.class, productId);
		
		if(statusProducts!=null){
			for(PmtbProductStatus statusProduct : statusProducts){
				Map<String, String> productMap = new LinkedHashMap<String, String>();
				productMap.put("cardNo",product.getCardNo());
				productMap.put("statusFrom",NonConfigurableConstants.PRODUCT_STATUS.get(statusProduct.getStatusFrom()));
				productMap.put("statusTo",NonConfigurableConstants.PRODUCT_STATUS.get(statusProduct.getStatusTo()));
				productMap.put("statusDate",DateUtil.convertDateToStr(statusProduct.getStatusDt(),DateUtil.GLOBAL_DATE_FORMAT) + " " + DateUtil.convertDateToStr(statusProduct.getStatusDt(),DateUtil.GLOBAL_TIME_FORMAT));
				productMap.put("reason",statusProduct.getMstbMasterTable().getMasterValue());
				productMap.put("remarks",statusProduct.getStatusRemarks());
				productMap.put("createdBy",statusProduct.getCreatedBy());
				productMap.put("createdDate",DateUtil.convertTimestampToStr(statusProduct.getCreatedDt(),DateUtil.GLOBAL_DATE_FORMAT));
				productMap.put("updatedBy",statusProduct.getUpdatedBy());
				productMap.put("updatedDate",DateUtil.convertTimestampToStr(statusProduct.getUpdatedDt(),DateUtil.GLOBAL_DATE_FORMAT));
				returnMap.put(statusProduct.getProductStatusNo().toString(), productMap);
			}
		}
		return returnMap; 
	}
	
	public boolean saveRetag(boolean isToday, Timestamp effectiveDate,
						HashSet<BigDecimal> productIdSet, String retagRemarks,
								String accountNo, String previousAccountNo) {
		
		   Iterator<BigDecimal> It = productIdSet.iterator();
	        int count=0;
	        List<PmtbProductRetag> retagProducts=new ArrayList<PmtbProductRetag> ();
	        PmtbProductRetag retagProduct=new PmtbProductRetag(); 
	      //  PmtbProduct product=new PmtbProduct();
	        logger.info("currentaccount"+accountNo);
	        logger.info("currentaccount"+previousAccountNo);
	        logger.info("currentaccount");
	        logger.info("currentaccount");
	        AmtbAccount currentAccount=new AmtbAccount();
	        currentAccount=(AmtbAccount)this.daoHelper.getGenericDao().get(AmtbAccount.class, new Integer(previousAccountNo));
	        AmtbAccount newAccount=new AmtbAccount();
	        newAccount=(AmtbAccount)this.daoHelper.getGenericDao().get(AmtbAccount.class, new Integer(accountNo));
	        logger.info("newaccount");
	        try{
		        while (It.hasNext()) {
		        	PmtbProduct product=(PmtbProduct)this.daoHelper.getGenericDao().get(PmtbProduct.class, It.next());
		        	logger.info("product"+product.getCardNo());
		        	retagProduct.setAmtbAccountByCurrentAccountNo(currentAccount);
		        	retagProduct.setAmtbAccountByNewAccountNo(newAccount);
		        	retagProduct.setPmtbProduct(product);
		        	retagProduct.setEffectiveDt(effectiveDate);
		        	retagProduct.setRetagRemarks(retagRemarks);
		        	//retagProduct.setCardNo(product.getCardNo());
		        	//this.businessHelper.getGenericBusiness().save(issueProduct, this.getUserLoginId());
		        	//boolean checkSavingProductRetag=save(retagProduct)!=null ? true : false;
		        	//boolean checkSavingProductRetag=
		        	try{
		        		this.daoHelper.getGenericDao().save(retagProduct);
		        	//this.save(retagProduct);
		        	}
		        	catch(Exception e){
		        		e.printStackTrace();
		        		Messagebox.show("This cardno : "+product.getCardNo()+" cannot be retag, ", "Retag Product", Messagebox.OK, Messagebox.INFORMATION);
		        	}
		        //	if(!checkSavingProductRetag)
		        	
		        	//retagProducts.add(count,retagProduct);
		        	
		        	count++;
		        //	logger.info("Data insides "+It.next());
		                }
		    	Messagebox.show("Retag Process is completed ", "Retag Product", Messagebox.OK, Messagebox.INFORMATION);
	        	
	        }catch(Exception e){	
	        	try{
	        		Messagebox.show(com.cdgtaxi.ibs.common.NonConfigurableConstants.COMMON_ERROR_MESSAGE,
	        				"Error", Messagebox.OK, Messagebox.ERROR);
	        	}catch(Exception exp){exp.printStackTrace();}
					
			e.printStackTrace();}
	        
		// TODO Auto-generated method stub
		return false;
	}
	
	public int getCountIssuedCards(String cardnoStart,String cardnoEnd,PmtbProductType productType) {
		
		logger.debug("cardnoStart: " + cardnoStart);
		logger.debug("cardnoEnd: " + cardnoEnd);
		
		int count=this.daoHelper.getProductDao().getCountIssuedCards(cardnoStart,cardnoEnd,productType); 
		return count;
   }

	public boolean checkCard(String cardnoStart, PmtbProductType cardType) {
		boolean check=this.daoHelper.getProductDao().checkCard( cardnoStart, cardType) ;
		return check;
	}

	public PmtbProduct getProductById(BigDecimal id) {
	
		PmtbProduct product=new PmtbProduct();
		product=(PmtbProduct)this.daoHelper.getProductDao().getProduct(id);
		return product;
	        	
	}

	public BigDecimal getProductMaxUpdatableCreditLimit(HashSet<BigDecimal> productIdSet) {
		List <PmtbProduct> products=this.daoHelper.getProductDao().getProductsCreditLimit(productIdSet);
		BigDecimal maxCreditLimit=new BigDecimal("0");
		int i=0;
		if(products==null) logger.info("No Products");
		else{
			for(PmtbProduct product : products){
				logger.info("Product No"+product.getProductNo());
				if(product.getAmtbAccount()!=null){
					if(i==0)maxCreditLimit=product.getAmtbAccount().getCreditLimit();
					else if(product.getAmtbAccount().getCreditLimit()!=null){
						if(product.getAmtbAccount().getCreditLimit().compareTo(maxCreditLimit)<1)
							maxCreditLimit=product.getAmtbAccount().getCreditLimit();
							logger.info("This is credit Limti"+maxCreditLimit);	
					}
				}		
				logger.info("Count "+i);
				i++;
//				if(i==0)maxCreditLimit=product.getCreditLimit();
//				else if(product.getCreditLimit()!=null){
//					if(product.getCreditLimit().compareTo(maxCreditLimit)<1)
//						maxCreditLimit=product.getCreditLimit();
//						logger.info("This is credit Limti"+maxCreditLimit);	
//				}
				
			}
		}
		logger.info("I am the Omega, the last,This is credit Limti"+maxCreditLimit);	
		return maxCreditLimit;	
	}

	public Date getValidExpiryDate(HashSet<BigDecimal> productIdSet) {
		Date validExpiryDate=null;
		validExpiryDate=(Date)this.daoHelper.getProductDao().getValidExpiryDate(productIdSet);
		return validExpiryDate;
	}
	
	public java.sql.Timestamp getValidExpiryDateTime(HashSet<BigDecimal> productIdSet){
		return this.daoHelper.getProductDao().getValidExpiryDateTime(productIdSet);
	}

	public int recycleProducts(String startCardNo, String endCardNo,String userId) {
		int processStatus=1;
		
		//Brian: 200091106: Fix bug only one product status created for multiple products recycle
		//PmtbProductStatus productStatus=new PmtbProductStatus();
		//Brian: 200091106: Ends
		List <PmtbProduct> products=this.daoHelper.getProductDao().getOtuCards(startCardNo,endCardNo);
		Date currentDate=new Date();
		if(products==null)
			processStatus=3;
		else{
			
			for(PmtbProduct product : products){
				
				try{
					logger.info("Recycle Product");
					
					//Brian: 200091106: Fix bug only one product status created for multiple products recycle
					PmtbProductStatus productStatus=new PmtbProductStatus();
					//Brian: 200091106: Ends
						
					productStatus.setStatusDt(new Timestamp (currentDate.getTime()));
					productStatus.setPmtbProduct(product);
					productStatus.setStatusFrom(product.getCurrentStatus());
					productStatus.setStatusRemarks(NonConfigurableConstants.PRODUCT_RECYCLED_REMARKS);
					productStatus.setStatusTo(NonConfigurableConstants.PRODUCT_STATUS_RECYCLED);
					productStatus.setMstbMasterTable(ConfigurableConstants.getMasterTable(ConfigurableConstants.RECYCLE_REASON_TYPE, ConfigurableConstants.RECYCLE_CODE));
					logger.info("Status  "+productStatus.getStatusTo());
					this.daoHelper.getGenericDao().save(productStatus);
					try{
						product.setCurrentStatus(NonConfigurableConstants.PRODUCT_STATUS_RECYCLED);
						updateProductAPI(product, userId, ConfigurableConstants.getMasterTable(ConfigurableConstants.RECYCLE_REASON_TYPE, ConfigurableConstants.RECYCLE_CODE));
						logger.info("Id"+product.getProductNo().toString());
						this.daoHelper.getGenericDao().update(product);
					}catch(Exception e){
						e.printStackTrace();
						processStatus=2;
					}
				}catch(Exception e){
					e.printStackTrace();
					processStatus=2;
				}
			}
		}
		return processStatus;
	}

	public Map<String, String> getOtuCardTypes() {
		
		Map<String, String> returnAccountMap =null;
		List <PmtbProductType> prductTypes=this.daoHelper.getProductDao().getOtuCardTypes();
		if(prductTypes!=null){
			returnAccountMap = new LinkedHashMap<String,String>();
			for(PmtbProductType productType: prductTypes){
				returnAccountMap.put(productType.getProductTypeId().toString(), productType.getName());
			}
		}
			return returnAccountMap ; 
	}

	public PmtbProductType getProductType(PmtbProduct product) {
		PmtbProductType productType=new PmtbProductType();
		PmtbProduct returnProduct=new PmtbProduct();
		returnProduct=(PmtbProduct)this.daoHelper.getProductDao().getProduct(product);
		
		productType=returnProduct.getPmtbProductType();
		if(productType==null)logger.info("This is null");
		else logger.info("not null"+productType.getProductTypeId());
		return productType;
	}
	
	public PmtbProduct getProductByCard(String card) {
		PmtbProduct product=this.daoHelper.getProductDao().getProductByCard(card);
		if(product==null)logger.info("This is null");
		else logger.info("not null > "+product.getProductNo());
		return product;
	}
	public PmtbProductType getProductTypeByName(String name) {
		PmtbProductType productType=this.daoHelper.getProductDao().getProductTypeByName(name);
		if(productType==null)logger.info("This is null");
		else logger.info("not null > "+productType.getProductTypeId());
		return productType;
	}
	
	public String getAccountParentIdbyProductNo(String productId){
		String parentAccountNo="";
		PmtbProduct product=(PmtbProduct)this.daoHelper.getGenericDao().get(PmtbProduct.class,new BigDecimal(productId));
		AmtbAccount account=this.daoHelper.getAccountDao().getParent(product.getAmtbAccount());
//		logger.info("Here"+account.getAccountNo().toString());
		if(account.getAmtbAccount()==null)
			parentAccountNo=account.getAccountNo().toString();
		else if(account.getAmtbAccount().getAmtbAccount()==null)
			parentAccountNo=account.getAmtbAccount().getAccountNo().toString();
		else if(account.getAmtbAccount().getAmtbAccount().getAmtbAccount()==null)
			parentAccountNo=account.getAmtbAccount().getAmtbAccount().getAccountNo().toString();
//		logger.info("Parent Account No"+parentAccountNo);
		return parentAccountNo;
	
	}

	
	public  List<AmtbAccount>  getActiveAccountList(String accNo,String name) {
		
		List<AmtbAccount> activeAcctList = Lists.newArrayList();
		List <AmtbAccount> accounts=this.daoHelper.getAccountDao().getAccountNoAndName(accNo, name);
		
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
	
	public Map<String, String> getActiveAccountNoAndName(String accNo,String name) {
		
		Map<String, String> returnAccountMap =null;
		//private AmtbAccountStatus getCurrentStatus(Collection<AmtbAccountStatus> statuses){
		List <AmtbAccount> accounts=this.daoHelper.getAccountDao().getAccountNoAndName(accNo,name);
		if(accounts!=null){
			returnAccountMap = new LinkedHashMap<String,String>();
			for(AmtbAccount account : accounts){
				AmtbAcctStatus accountStatus=getCurrentStatus(account.getAmtbAcctStatuses());
				if(accountStatus!=null){
					logger.info("accountStatus.getAccountStatus()"+accountStatus.getAcctStatus());
				
					if(accountStatus.getAcctStatus().equalsIgnoreCase(NonConfigurableConstants.ACCOUNT_STATUS_ACTIVE)){
						if(account.getCustNo()!=null)
							returnAccountMap.put(account.getAccountNo().toString(), account.getAccountName()+" ("+account.getCustNo()+")");
						else
							returnAccountMap.put(account.getAccountNo().toString(), account.getAccountName());
					}
				}
			}
		}
		return returnAccountMap ; 
	}
	
	public List<AmtbAccount> getActiveDepartmentList(String accountNo) {
		
		List<AmtbAccount> activeAcctList = Lists.newArrayList();
		List <AmtbAccount> accounts=this.daoHelper.getAccountDao().getDepartmentInfo(accountNo);
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
	
	
	public List<AmtbAccount> getActiveDepartmentByDivisionList(String divisionAccountNo) {
		
		List<AmtbAccount> activeAcctList = Lists.newArrayList();
		List <AmtbAccount> accounts=this.daoHelper.getAccountDao().getDepartmentInfoByDivisionAcctNo(divisionAccountNo);
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
	
	
	public Map<String, Map<String, String>> getActiveDepartmentInfoByDivisionAcctNo(String accountNo) {

		Map<String, Map<String, String>> returnMap = new LinkedHashMap<String, Map<String, String>>();
		List <AmtbAccount> accounts=this.daoHelper.getAccountDao().getDepartmentInfoByDivisionAcctNo(accountNo);
		//private AmtbAccountStatus getCurrentStatus(Collection<AmtbAccountStatus> statuses){
		
		if (accounts != null){
			for(AmtbAccount account : accounts){
				Map<String, String> accountMap = new LinkedHashMap<String, String>();
				AmtbAcctStatus accountStatus=getCurrentStatus(account.getAmtbAcctStatuses());
				if(accountStatus!=null){
					logger.info("accountStatus.getAccountStatus()"+accountStatus.getAcctStatus());
					if(accountStatus.getAcctStatus().equalsIgnoreCase(NonConfigurableConstants.ACCOUNT_STATUS_ACTIVE)){
						if(account.getCode()!=null)
							accountMap.put("departmentName",account.getAccountName()+" ("+account.getCode()+")");
						else
							accountMap.put("departmentName",account.getAccountName());
						returnMap.put(account.getAccountNo().toString(), accountMap);
					}
				}
			}
		}else{
			returnMap=null;
		}
		return returnMap ; 
	}

	
	public List<AmtbAccount> getActiveDivOrSubApplList(String accountNo) {
		
		List<AmtbAccount> activeAcctList = Lists.newArrayList();
		List <AmtbAccount> accounts=this.daoHelper.getAccountDao().getDivOrSubApplInfo(accountNo);
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
	
	public Map<String, Map<String, String>> getActiveDivOrSubApplInfo(String accountNo) {
		//private AmtbAccountStatus getCurrentStatus(Collection<AmtbAccountStatus> statuses){
		Map<String, Map<String, String>> returnMap = new LinkedHashMap<String, Map<String, String>>();
		List <AmtbAccount> accounts=this.daoHelper.getAccountDao().getDivOrSubApplInfo(accountNo);
		if(accounts!=null){
			for(AmtbAccount account : accounts){
				
				AmtbAcctStatus accountStatus=getCurrentStatus(account.getAmtbAcctStatuses());
				Map<String, String> accountMap = new LinkedHashMap<String, String>();
				if(accountStatus!=null){
					//logger.info("accountStatus.getAccountStatus()"+accountStatus.getAcctStatus());
					if(accountStatus.getAcctStatus().equalsIgnoreCase(NonConfigurableConstants.ACCOUNT_STATUS_ACTIVE)){
						if(account.getCode()!=null)
							accountMap.put("divOrSubApplName",account.getAccountName()+" ("+account.getCode()+")");
						else
							accountMap.put("divOrSubApplName",account.getAccountName());
						accountMap.put("accountNameOnCard",account.getNameOnCard());
						accountMap.put("accountCategory",account.getAccountCategory());
						returnMap.put(account.getAccountNo().toString(), accountMap);
					}
				}
			}
		}else{
			returnMap=null;
		}
		return returnMap ;
	}
	
	private AmtbAcctStatus getCurrentStatus(Collection<AmtbAcctStatus> statuses){
		TreeSet<AmtbAcctStatus> sortedStatus = new TreeSet<AmtbAcctStatus>(new Comparator<AmtbAcctStatus>(){
			public int compare(AmtbAcctStatus o1, AmtbAcctStatus o2) {
				return o1.getEffectiveDt().compareTo(o2.getEffectiveDt());
			}
		});
		for(AmtbAcctStatus acctStatus : statuses){
			if(acctStatus.getEffectiveDt().before(DateUtil.getCurrentTimestamp())){
				sortedStatus.add(acctStatus);
			}
		}
		if(!sortedStatus.isEmpty()){
			return sortedStatus.last();
		}else{
			return null;
		}
	}

	public PmtbProductRetag getFutureRetagSchedule(PmtbProduct product) {
		
		Date today=new Date();
		List <PmtbProductRetag > retagProducts=this.daoHelper.getProductDao().getRetagProducts(product.getProductNo());
		
		if(retagProducts!=null){
			logger.info("Shouldn't be here");
			for(PmtbProductRetag retagProduct : retagProducts){
				if(retagProduct.getEffectiveDt().getTime()>today.getTime()){
					logger.info("Now returning"+retagProduct.getRetagRemarks());
					return retagProduct;
					//break;
				}
			}
			
		//	return returnRetag;
		}
		//else{
			logger.info("Should be here");
			return null;
		//}
		
	}
	
	public PmtbProductStatus  getFutureSuspendSchedule(PmtbProduct product) {
		Date today=new Date();
		List <PmtbProductStatus> productStatuses=this.daoHelper.getProductDao().getProductStatus(product.getProductNo());
		
		if(productStatuses!=null){
			logger.info("****getFutureSuspendSchedule");
			for(PmtbProductStatus productStatus : productStatuses){
				if(productStatus.getStatusDt().getTime()>today.getTime()){
					if(productStatus.getStatusTo().equalsIgnoreCase(NonConfigurableConstants.PRODUCT_STATUS_SUSPENDED))
						return productStatus;
				}
			}
		}
		return null;
	}
	
	public PmtbProductCreditLimit getFutureCreditLimitScheduleHalfWay(PmtbProduct product) {
		Date today=new Date();
		List <PmtbProductCreditLimit > creditLimits=this.daoHelper.getProductDao().getUpdatedCreditLimtProducts(product.getProductNo());
		
		if(creditLimits!=null){
			logger.info("Shouldn't be here");
			for(PmtbProductCreditLimit creditLimit : creditLimits){
				if(creditLimit.getCreditLimitType().equalsIgnoreCase(NonConfigurableConstants.REVIEW_TYPE_TEMPORARY)){
					if(creditLimit.getEffectiveDtFrom().getTime()<today.getTime()){
						if(creditLimit.getEffectiveDtTo().getTime()>today.getTime())
							return creditLimit;
					}
				}
			}
		}
		return null;
	}
	
	public PmtbProductCreditLimit getFutureCreditLimitScheduleTotallyFuture(PmtbProduct product) {
		Date today=new Date();
		List <PmtbProductCreditLimit > creditLimits=this.daoHelper.getProductDao().getUpdatedCreditLimtProducts(product.getProductNo());
		
		if(creditLimits!=null){
			logger.info("Shouldn't be here");
			for(PmtbProductCreditLimit creditLimit : creditLimits){
				if(creditLimit.getCreditLimitType().equalsIgnoreCase(NonConfigurableConstants.REVIEW_TYPE_TEMPORARY)){
					if(creditLimit.getEffectiveDtFrom().getTime()>today.getTime()){
						if(creditLimit.getEffectiveDtTo().getTime()>today.getTime())
							return creditLimit;
					}
				}
			}
		}
		return null;
	}
	public void UpdateFutureSchedules(PmtbProduct product,String userid) {
		Date today=new Date();
		try{
			List <PmtbProductStatus> productStatuses=this.daoHelper.getProductDao().getProductStatus(product.getProductNo());
			//Update Status Table's future schedule(s)
			if(productStatuses!=null){
				logger.info("Future Statuses Updated");
				for(PmtbProductStatus productStatus : productStatuses){
					if(productStatus.getStatusDt().getTime()>today.getTime()){
						this.daoHelper.getGenericDao().delete(productStatus);
					}
				}
			}
			//Update Credit Limit's table future shcedule(s)
			List <PmtbProductCreditLimit > creditLimits=this.daoHelper.getProductDao().getUpdatedCreditLimtProducts(product.getProductNo());
			if(creditLimits!=null){
				logger.info("Future Credit Limit Updated");
				for(PmtbProductCreditLimit creditLimit : creditLimits){
					if(creditLimit.getCreditLimitType().equalsIgnoreCase(NonConfigurableConstants.REVIEW_TYPE_TEMPORARY)){
						if(creditLimit.getEffectiveDtFrom().getTime()>today.getTime()){
							if(creditLimit.getEffectiveDtTo().getTime()>today.getTime()){
								this.daoHelper.getGenericDao().delete(creditLimit);
							}
						}
						else{
							if(creditLimit.getEffectiveDtTo().getTime()>today.getTime()){
								creditLimit.setEffectiveDtTo(new Timestamp(today.getTime()));
								this.daoHelper.getGenericDao().update(creditLimit,userid);
							}
						}
					}
				}
			}
			
			List <PmtbProductRetag > retagProducts=this.daoHelper.getProductDao().getRetagProducts(product.getProductNo());
			
			if(retagProducts!=null){
				logger.info("Future Retag Updated");
				for(PmtbProductRetag retagProduct : retagProducts){
					if(retagProduct.getEffectiveDt().getTime()>today.getTime()){
						this.daoHelper.getGenericDao().delete(retagProduct);
					}
				}
			}
		}catch(Exception e){e.printStackTrace();}
	}
	
	public PmtbProductStatus  getFutureReactivateSchedule(PmtbProduct product) {
		Date today=new Date();
		List <PmtbProductStatus> productStatuses=this.daoHelper.getProductDao().getProductStatus(product.getProductNo());
		
		if(productStatuses!=null){
			logger.info("Shouldn't be here");
			for(PmtbProductStatus productStatus : productStatuses){
				if(productStatus.getStatusDt().getTime()>today.getTime()){
					if(productStatus.getStatusTo().equalsIgnoreCase(NonConfigurableConstants.PRODUCT_STATUS_ACTIVE))
						return productStatus;
				}
			}
		}
		return null;
	}

	private synchronized void saveProduct_(PmtbProduct product,int count,String userId){
		
		
			PmtbProductType productType=new PmtbProductType();
			product.setPmtbProductType(productType);
			Map<String,Map<String,String>> cardInfoMap = new LinkedHashMap<String,Map<String,String>>();
	   		Map<String,String> dataMap = new LinkedHashMap<String,String>();
	   	
			String cardNo=getMaxCardno(productType);
			BigDecimal cardno=new BigDecimal("0");
			//cardno=getCardno(cardNo,productType);
			boolean checkSaveProcess=false;
			product.setCardNo(cardno.toString());
			product.setCurrentStatus(NonConfigurableConstants.PRODUCT_STATUS_ACTIVE);
			try{
				checkSaveProcess=this.save(product, userId)!=null ? true : false;
				if(!checkSaveProcess) 	
					Messagebox.show("Card No. " + product.getCardNo()+ " cannot be saved ", "Create Product", Messagebox.OK, Messagebox.INFORMATION);
				else{
					PmtbProductStatus productStatus=new PmtbProductStatus();
					productStatus.setPmtbProduct(product);
					productStatus.setStatusFrom(NonConfigurableConstants.APPLICATION_STATUS_NEW);
					productStatus.setStatusTo(productType.getDefaultCardStatus());
					productStatus.setStatusRemarks(NonConfigurableConstants.STATUS_REMARKS_ISSUE);
					productStatus.setStatusDt(DateUtil.getCurrentTimestamp());
					productStatus.setMstbMasterTable(ConfigurableConstants.getMasterTable("SS", "IC"));
					this.save(productStatus,userId);
					dataMap.put(product.getCardNo(), product.getCardNo());
					if(product.getExpiryDate()!=null)
						dataMap.put("expiry"+product.getCardNo(),DateUtil.convertDateToStr( product.getExpiryDate(),DateUtil.GLOBAL_DATE_FORMAT));
					dataMap.put("issue"+product.getCardNo(), DateUtil.convertDateToStr(product.getIssueDate(),DateUtil.GLOBAL_DATE_FORMAT));
					cardInfoMap.put(product.getCardNo(),dataMap);
				}
			}catch(Exception e){
				try{
					Messagebox.show(com.cdgtaxi.ibs.common.NonConfigurableConstants.COMMON_ERROR_MESSAGE, 
									"Error", Messagebox.OK, Messagebox.ERROR);
				}catch(Exception exp){}
					e.printStackTrace();
			}	
		
		
	}

	public PmtbProductType getProductTypebyProductId(BigDecimal productId) {

	PmtbProductType productType=this.daoHelper.getProductDao().getProductTypebyProductId(productId);
	return productType;
		
	}

	public boolean isFutureTermination(PmtbProduct product, Date effectiveDate) {
		boolean futureTermination=false;
		futureTermination=this.daoHelper.getProductDao().isFutureTermination(product,effectiveDate);
		
		return futureTermination;
	}
	
	public PmtbProductStatus getFutureTermination(PmtbProduct product, Date effectiveDate) {
		logger.info("Retrive Latest Termination Status");
		PmtbProductStatus futureTermination=null;
		futureTermination=this.daoHelper.getProductDao().getFutureTermination(product,effectiveDate);
		if(futureTermination==null) logger.info("I confess I am a null");
		logger.info("Status"+futureTermination.getProductStatusNo().toString());
		return futureTermination;
	}

	public boolean isFutureTerminationByRange(PmtbProduct product, Date currentDate, Date suspensionDate) {
		boolean futureTermination=false;
		futureTermination=this.daoHelper.getProductDao().isFutureTerminationByRange(product,currentDate,suspensionDate);
		return futureTermination;
	}
	
	public boolean isFutureTerminationByRange(List<BigDecimal> productIds, Date startDate, Date endDate) {
		return this.daoHelper.getProductDao().isFutureTerminationByRange(productIds, startDate, endDate);
	}
	public boolean hasStatus(List<BigDecimal> productIds, Date statusDate) {
		return this.daoHelper.getProductDao().hasStatus(productIds, statusDate);
	}
	
	public boolean isMultipleCorporate(HashSet<BigDecimal> selectedProductIdSet) {
		boolean checkMultipleCorporate=false;
		int i=0;
		String newParentAccount="";
		String oldParentAccount="";
		if(selectedProductIdSet.size()==1)
			checkMultipleCorporate=true;
		else{
			List <PmtbProduct> products=this.daoHelper.getProductDao().getProductsbyIdSet(selectedProductIdSet);
			if(products!=null){
				for(PmtbProduct product : products){
					
					Map<String, String> productMap = new LinkedHashMap<String, String>();
					AmtbAccount account=this.daoHelper.getAccountDao().getParent(product.getAmtbAccount());
					if(account.getAmtbAccount()==null){
						newParentAccount=account.getAccountNo().toString();
						//productMap.put("parentAccountNo",account.getCustNo());
					}
					else if(account.getAmtbAccount().getAmtbAccount()==null){
						newParentAccount=account.getAmtbAccount().getAccountNo().toString();
						//productMap.put("parentAccountNo",account.getAmtbAccount().getCustNo());
					}
					else if(account.getAmtbAccount().getAmtbAccount().getAmtbAccount()==null){
						newParentAccount=account.getAmtbAccount().getAmtbAccount().getAccountNo().toString();
						//productMap.put("parentAccountNo",account.getAmtbAccount().getAmtbAccount().getCustNo());
					}
					if(i==0){
						oldParentAccount=newParentAccount;
					}
					if(oldParentAccount.equalsIgnoreCase(newParentAccount)){
						checkMultipleCorporate=true;
						logger.info("Old Account "+oldParentAccount);
						logger.info("New Account "+newParentAccount);
					}
					else{
						checkMultipleCorporate=false;
						logger.info("Old Account "+oldParentAccount);
						logger.info("New Account "+newParentAccount);
						break;
					}
					if(i!=0){
						oldParentAccount=newParentAccount;
					}
					i++;
					
				}
			}
		}
		return checkMultipleCorporate;
	}

	public boolean isDuplicateScheduleCreditLimit(String creditLimitType,Date effectiveDateFrom, BigDecimal productId) {
		boolean checkDuplicateScheduleCreditLimit=false;
		checkDuplicateScheduleCreditLimit=this.daoHelper.getProductDao().isDuplicateScheduleCreditLimit(creditLimitType,effectiveDateFrom, productId);
		return checkDuplicateScheduleCreditLimit;
	}

	public boolean isProductNameInUse(String productTypeName) {
		
		boolean productNameInUse=false;
		logger.info("Check The Product Name In use");
		productNameInUse=this.daoHelper.getProductTypeDao().isProductNameInUse(productTypeName);
		if(productNameInUse)
			logger.info("Name is already been used");
		return productNameInUse;
	}
	
	public boolean isProductNameInUse(String productTypeName,String productTypeId){
		
		boolean productNameInUse=false;
		logger.info("Checking isProductNameInUse");
		logger.info("productTypeName"+productTypeName);
		logger.info("productTypeId"+productTypeId);
		productNameInUse=this.daoHelper.getProductTypeDao().isProductNameInUse(productTypeName,productTypeId);
		if(productNameInUse)
			logger.info("Name is already been used");
		return productNameInUse;
	}

	public boolean hasPendingCreditReview(PmtbProduct product) {
		
		boolean hasPendingPermanentCreditReview=false;
		String custNo=null;
		PmtbProduct productTemp=null;
		productTemp=this.daoHelper.getProductDao().getProductsbyId(product.getProductNo());
	
		AmtbAccount account=this.daoHelper.getAccountDao().getParent(product.getAmtbAccount());
		if(account.getAmtbAccount()==null){
			custNo=account.getCustNo();
		}
		else if(account.getAmtbAccount().getAmtbAccount()==null){
			custNo=account.getAmtbAccount().getCustNo();
		}
		else if(account.getAmtbAccount().getAmtbAccount().getAmtbAccount()==null){
			custNo=account.getAmtbAccount().getAmtbAccount().getCustNo();
		}
				
		if(custNo!=null){
			hasPendingPermanentCreditReview=hasPendingPermanentCreditReview(custNo);
			logger.info("################33TESTING PENDING CREDIT REVIREW");
			logger.info("hasPendingCreditReview		"+hasPendingPermanentCreditReview);
		}
		return hasPendingPermanentCreditReview;
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
	
	public BigDecimal getLess30DaysTotalOutstandingAmount(AmtbAccount debtToAccount){
		return this.daoHelper.getInvoiceDao().getLess30DaysTotalOutstandingAmount(debtToAccount, DateUtil.getCurrentDate());
	}
	
	public BigDecimal getLess60DaysTotalOutstandingAmount(AmtbAccount debtToAccount){
		return this.daoHelper.getInvoiceDao().getLess60DaysTotalOutstandingAmount(debtToAccount, DateUtil.getCurrentDate());
	}
	
	public BigDecimal getLess90DaysTotalOutstandingAmount(AmtbAccount debtToAccount){
		return this.daoHelper.getInvoiceDao().getLess90DaysTotalOutstandingAmount(debtToAccount, DateUtil.getCurrentDate());
	}
	
	public BigDecimal getMore90DaysTotalOutstandingAmount(AmtbAccount debtToAccount){
		return this.daoHelper.getInvoiceDao().getMore90DaysTotalOutstandingAmount(debtToAccount, DateUtil.getCurrentDate());
	}
	public boolean checkAuthorizedAccount(Integer accountNo,BigDecimal productNo){
		boolean isAuthorizedAccount=false;
		isAuthorizedAccount=this.daoHelper.getProductDao().checkAuthorizedAccount(accountNo,productNo);
		logger.info("CheckAuthorized_Account from Busineess : "+isAuthorizedAccount);
		return isAuthorizedAccount;
		
	}

	public void updateProductAPI(PmtbProduct product, String userId, MstbMasterTable masterTable) throws Exception {
		PmtbProduct productAndAccount= (PmtbProduct) this.daoHelper.getProductDao().getProductAndAccount(product);
		try{
			AmtbAccount amtbAccount = productAndAccount.getAmtbAccount();
			PmtbProductType pmtbProductType = productAndAccount.getPmtbProductType();
			// for product_type.credit_limit = N or NA, the credit balance can be null
			if (product.getCreditBalance() != null)
			{
				API.updateProduct(product.getCardNo(),
						NonConfigurableConstants.AS_REQUEST_PRODUCT_STATUS_INACTIVE,
						product.getCreditBalance().toString(),
						DateUtil.convertDateToStr(product.getExpiryDate(), DateUtil.INTERFACE_AS_PRODUCT_EXPIRY_DATE_FORMAT),
						API.formulateAccountId(amtbAccount),
						masterTable.getInterfaceMappingValue(),
						userId,
						StringUtil.numberToString(product.getOfflineCount()),
						StringUtil.bigDecimalToString(product.getOfflineAmount()),
						StringUtil.bigDecimalToString(product.getOfflineTxnAmount()),
						API.formulateForceOnline(product, pmtbProductType),
						NonConfigurableConstants.AS_REQUEST_TRANSFER_MODE_ASYNCHRONOUS);
			}
			else
			{
				API.updateProduct(product.getCardNo(),
						NonConfigurableConstants.AS_REQUEST_PRODUCT_STATUS_INACTIVE,
						"",
						DateUtil.convertDateToStr(product.getExpiryDate(), DateUtil.INTERFACE_AS_PRODUCT_EXPIRY_DATE_FORMAT),
						API.formulateAccountId(amtbAccount),
						masterTable.getInterfaceMappingValue(),
						userId,
						StringUtil.numberToString(product.getOfflineCount()),
						StringUtil.bigDecimalToString(product.getOfflineAmount()),
						StringUtil.bigDecimalToString(product.getOfflineTxnAmount()),
						API.formulateForceOnline(product, pmtbProductType),
						NonConfigurableConstants.AS_REQUEST_TRANSFER_MODE_ASYNCHRONOUS);
			}
		}
		catch(Exception e){e.printStackTrace();throw new Exception();}
		
	}
	
	public void updateProductAPICardLost(PmtbProduct product, String userId, MstbMasterTable masterTable) throws Exception {
		PmtbProduct productAndAccount= (PmtbProduct) this.daoHelper.getProductDao().getProductAndAccount(product);
		try{
			// Retrieve the interface mapping from master code table for renewal
			AmtbAccount amtbAccount = productAndAccount.getAmtbAccount();
			PmtbProductType pmtbProductType = productAndAccount.getPmtbProductType();
			// Get the reason from masterTable interface mapping
			// for product_type.credit_limit = N or NA, the credit balance can be null
			if (product.getCreditBalance() != null)
			{
				API.updateProduct(product.getCardNo(),
					NonConfigurableConstants.AS_REQUEST_PRODUCT_STATUS_INACTIVE,
					product.getCreditBalance().toString(),
					DateUtil.convertDateToStr(product.getExpiryDate(), DateUtil.INTERFACE_AS_PRODUCT_EXPIRY_DATE_FORMAT),
					API.formulateAccountId(amtbAccount),
					masterTable.getInterfaceMappingValue(),
					userId,
					StringUtil.numberToString(product.getOfflineCount()),
					StringUtil.bigDecimalToString(product.getOfflineAmount()),
					StringUtil.bigDecimalToString(product.getOfflineTxnAmount()),
					API.formulateForceOnline(product, pmtbProductType),
					NonConfigurableConstants.AS_REQUEST_TRANSFER_MODE_ASYNCHRONOUS);
			}
			else
			{
				API.updateProduct(product.getCardNo(),
						NonConfigurableConstants.AS_REQUEST_PRODUCT_STATUS_INACTIVE,
						"",
						DateUtil.convertDateToStr(product.getExpiryDate(), DateUtil.INTERFACE_AS_PRODUCT_EXPIRY_DATE_FORMAT),
						API.formulateAccountId(amtbAccount),
						masterTable.getInterfaceMappingValue(),
						userId,
						StringUtil.numberToString(product.getOfflineCount()),
						StringUtil.bigDecimalToString(product.getOfflineAmount()),
						StringUtil.bigDecimalToString(product.getOfflineTxnAmount()),
						API.formulateForceOnline(product, pmtbProductType),
						NonConfigurableConstants.AS_REQUEST_TRANSFER_MODE_ASYNCHRONOUS);
			}
		}
		catch(Exception e){e.printStackTrace();throw new Exception();}
		
	}
	public void updateProductAPIActive(PmtbProduct product, String userId) throws Exception {
		PmtbProduct productAndAccount= (PmtbProduct) this.daoHelper.getProductDao().getProductAndAccount(product);
		try {
			API.updateProductAPIActive(productAndAccount, userId);
		}
		catch(Exception e)
		{
			LoggerUtil.printStackTrace(logger, e);
			throw new Exception();
		}		
	}
	
	public Map<String, String> createASRequest(PmtbProduct product, String userId) throws Exception
	{

		String fixedValue=null;
		String creditBalance=null;
		String expiryDate=null;
	

		Map<String, String> ASRequest = new HashMap<String, String>();
		try{
			if(product.getFixedValue()!=null)
				fixedValue=product.getFixedValue().toString();
			if(	product.getCreditBalance()!=null)
				creditBalance=product.getCreditBalance().toString();
			if(product.getExpiryDate()!=null)
				expiryDate=DateUtil.convertDateToStr(product.getExpiryDate(), DateUtil.INTERFACE_AS_PRODUCT_EXPIRY_DATE_FORMAT);
			
		}catch(Exception e){
			LoggerUtil.printStackTrace(logger, e);
			throw e;
		}	
		
		try{

			AmtbAccount amtbAccount = this.businessHelper.getAccountBusiness().getAccountWithParent(product.getAmtbAccount());
			ASRequest.put(API.PRODUCT_ACCOUNT_ID, API.formulateAccountId(amtbAccount));
			if(product.getCurrentStatus().equalsIgnoreCase(NonConfigurableConstants.PRODUCT_STATUS_ACTIVE)){
				ASRequest.put(API.PRODUCT_STATUS, NonConfigurableConstants.AS_REQUEST_PRODUCT_STATUS_ACTIVE);
			}
			else
			{
				ASRequest.put(API.PRODUCT_STATUS, NonConfigurableConstants.AS_REQUEST_PRODUCT_STATUS_INACTIVE);
			}						
			
			ASRequest.put(API.PRODUCT_CARD_NO, product.getCardNo());
			ASRequest.put(API.PRODUCT_TYPE_ID,product.getPmtbProductType().getProductTypeId());
			ASRequest.put(API.PRODUCT_FIXED_VALUE,fixedValue);
			ASRequest.put(API.PRODUCT_CREDIT_BALANCE,creditBalance);
			ASRequest.put(API.PRODUCT_EXPIRY_DATE,expiryDate);	

			ASRequest.put(API.PRODUCT_REASON_CODE,"");
			ASRequest.put(API.PRODUCT_CREATE_BY, userId);
			ASRequest.put(API.PRODUCT_OFFLINE_COUNT, StringUtil.numberToString(product.getOfflineCount()));
			ASRequest.put(API.PRODUCT_OFFLINE_AMOUNT, StringUtil.bigDecimalToString(product.getOfflineAmount()));
			ASRequest.put(API.PRODUCT_OFFLINE_TXN_AMOUNT, StringUtil.bigDecimalToString(product.getOfflineTxnAmount()));
			ASRequest.put(API.PRODUCT_FORCE_ONLINE, API.formulateForceOnline(product, product.getPmtbProductType()));
		}catch(Exception e){
			LoggerUtil.printStackTrace(logger, e);
			throw e;
		}
		
		return ASRequest;
	}
	public void createProductAPI(PmtbProduct product, String userId) throws Exception {
		
		
		PmtbProduct productAndAccount= (PmtbProduct) this.daoHelper.getProductDao().getProductAndAccount(product);
		API.createProductAPI(productAndAccount, userId);
	}	

	public List<PmtbProduct> issueProduct(IssueProductForm form) throws Exception{
		
		PmtbProductType productType = form.getPmtbProductType();
		List<String> assignableCardNoList = populateAvailableCardNos(form);
		
		List<PmtbProduct> productList = Lists.newArrayList();
		for(String cardNo: assignableCardNoList){
			
			PmtbProduct product = PmtbProduct.buildProduct(form);
			
			//allocate card no
			product.setCardNo(cardNo);
			
			//populate credit balance based on credit limit
			product.setCreditBalance(product.getCreditLimit());

			productList.add(product);
		}
			
		commonIssueProduct(productType, productList, CommonWindow.getUserLoginIdAndDomain());
		return productList;
	}
	
	
	public void commonIssueProduct(PmtbProductType productType, List<PmtbProduct> productList, String userId) throws Exception{

		try{
			//set common product details
			Date issueDate = DateUtil.getCurrentDate();
			for(PmtbProduct product: productList){
				product.setIssueDate(new java.sql.Date(issueDate.getTime()));
				product.setCurrentStatus(productType.getDefaultCardStatus());
				product.setExpiredFlag(NonConfigurableConstants.BOOLEAN_YN_NO);

				//if "prepaid", set the last issuance fee charge date
				if(product.getPmtbProductType() != null)
				{
					if(product.getPmtbProductType().getPrepaid() != null)
					{
						if(product.getPmtbProductType().getPrepaid().equalsIgnoreCase("Y"))
							product.setLastIssuanceFeeChargeDate( new java.sql.Date(new Date().getTime()));
					}
				}
			}
			
			// Create new status
			List<PmtbProductStatus> productStatusList = Lists.newArrayList();
			for(PmtbProduct product: productList){
				PmtbProductStatus productStatus=new PmtbProductStatus();
				productStatus.setPmtbProduct(product);
				productStatus.setStatusFrom(NonConfigurableConstants.APPLICATION_STATUS_NEW);
				productStatus.setStatusTo(productType.getDefaultCardStatus());
				productStatus.setStatusRemarks(NonConfigurableConstants.STATUS_REMARKS_ISSUE);
				productStatus.setStatusDt(DateUtil.getCurrentTimestamp());
				productStatus.setMstbMasterTable(ConfigurableConstants.getMasterTable(ConfigurableConstants.ISSUE_REASON_TYPE, ConfigurableConstants.ISSUE_REASON_CODE));
				productStatusList.add(productStatus);				
			}
		
			//Credit Limit History For newly issued cards, only applicable for non prepaid product
			List<PmtbProductCreditLimit> productCreditLimitList = Lists.newArrayList();
			if(!NonConfigurableConstants.getBoolean(productType.getPrepaid())){
			
				for(PmtbProduct product: productList){
					
					//Credit Limit History For newly issued cards
					Timestamp creditLimitEffectiveDate= DateUtil.getCurrentTimestamp();
					if(productType.getCreditLimit().equalsIgnoreCase(NonConfigurableConstants.BOOLEAN_YES)){
						PmtbProductCreditLimit productCreditLimit=new PmtbProductCreditLimit();
						productCreditLimit.setPmtbProduct(product);
						productCreditLimit.setCreditLimitType(NonConfigurableConstants.REVIEW_TYPE_PERMANENT);
						productCreditLimit.setEffectiveDtFrom(creditLimitEffectiveDate);
						productCreditLimit.setNewCreditLimit(product.getCreditLimit());
						productCreditLimit.setRemarks(NonConfigurableConstants.STATUS_REMARKS_ISSUE);
						productCreditLimitList.add(productCreditLimit);
					}
				}
			}

			
			logger.info("Save products: " + productList.size());
			this.daoHelper.getGenericDao().saveAll(productList,userId);
			logger.info("Save product statuses: " + productStatusList.size());
			this.daoHelper.getGenericDao().saveAll(productStatusList,userId);
			logger.info("Save product credit limits: " + productCreditLimitList.size());
			this.daoHelper.getGenericDao().saveAll(productCreditLimitList,userId);
			
			logger.info("Insert AS Request(s)...");
			// Create AS request
			List<Map<String, String>> asRequests = new ArrayList<Map<String, String>>();
			for(PmtbProduct product: productList){
				asRequests.add(this.businessHelper.getProductBusiness().createASRequest(product, CommonWindow.getUserLoginIdAndDomain()));
			}
			
			
			//Brian: 200091106: For recycled product, need to send to AS as UPDATE request instead of CREATE request
			//construct card no list
			// For using IN, need to handle up to 1000 only. For every 1000, need to fire a separate request
			List <String> cardNoList = new ArrayList<String>();
			List <PmtbProduct> recycledProducts = new ArrayList<PmtbProduct>();
			
			for(PmtbProduct product: productList){
				cardNoList.add(product.getCardNo());
			}	
			Iterable<List<String>> cardNoListChunks = Iterables.partition(cardNoList, 1000);
			for(List<String> chunk: cardNoListChunks){
				
				List<PmtbProduct> temp = this.daoHelper.getProductDao().getRetagProducts(chunk);
				if(temp!=null){
					recycledProducts.addAll(temp);
				}
			}
			API.createProducts(asRequests,recycledProducts);
			logger.info("AS Request(s) had been inserted");
			
//			//16 Oct 2015 - Create ITTB_CP_CUST_CARD_ISSUANCE
//			for(PmtbProduct product: productList){
//				IttbCpCustCardIssuance ittbCp = new IttbCpCustCardIssuance();
//				ittbCp.setCardNo(product.getCardNo());
//				ittbCp.setProductNo((product.getProductNo()).longValue());
//				ittbCp.setProductTypeId(product.getPmtbProductType().getProductTypeId());
//				if(null != product.getNameOnProduct())
//					ittbCp.setIssuedTo(product.getNameOnProduct());
//				else
//					ittbCp.setIssuedTo(" "); //TODO ASK JHENEFFER SAY PUT WHAT
//				//INSERT DATE
//				ittbCp.setIssuedOn(new Timestamp(DateUtils.truncate(new Date(), Calendar.DATE).getTime()));
//				ittbCp.setCreatedBy(CommonWindow.getUserLoginIdAndDomain());
//				
//				ittbCp.setCreatedDt(new Timestamp(new Date().getTime()));
//				ittbCp.setUpdatedBy(CommonWindow.getUserLoginIdAndDomain());
//				ittbCp.setUpdatedDt(new Timestamp(new Date().getTime()));
//				ittbCp.setBatchFlag("Y");
//				this.save(ittbCp);
//			}
			
			//send email to virtual product.
			boolean isVirtual = NonConfigurableConstants.getBoolean(productType.getVirtualProduct());
			if(isVirtual)
			{
				logger.info("Issue Virtual Card Detected, preparing virtual email");
				createVirtualEmailList(productList, issueDate, "NEW CARD");
			}
			
			
		}
		catch(Exception e){
			logger.info("Saving will be rolled back."); 
			LoggerUtil.printStackTrace(logger, e);
			throw e;
		}
		
	}

	
	private List<String> populateAvailableCardNos(IssueProductForm form){
		
		
		IssueType type = form.getIssueType();
		PmtbProductType productType = form.getPmtbProductType();

		Integer currentNo  = null;
		PmtbCardNoSequence cardNoSequence = null;
		
		//generate or retrieve the first allocate able card no
		Integer totalCount = null;
		if(type==IssueType.COUNT){
			
			cardNoSequence = getCardNoSequence(productType);
			BigDecimal seqCount = cardNoSequence.getCount();
			
			totalCount = form.getNoOfCards();
			currentNo = seqCount.intValue();

		} else if(type==IssueType.RANGE){
			Integer cardNoStart = form.getCardNoStart();
			Integer cardNoEnd= form.getCardNoEnd();
			
			if(cardNoStart==null){
				throw new WrongValueException("Card No Start could not be null.");
			}
			
			totalCount =  cardNoEnd - cardNoStart + 1;
			currentNo = cardNoStart -1;

			//The validation has been done when submitted the request, while to be KIA SI, here we do the validation again
			String checkStartCard = CardNoGenerator.generateCardNo(cardNoStart, productType);
			String checkEndCard = CardNoGenerator.generateCardNo(cardNoEnd, productType);
			if (NonConfigurableConstants.getBoolean(productType.getLuhnCheck())) {
				checkStartCard = checkStartCard.substring(0, checkStartCard.length()-1) + "0";
				checkEndCard = checkEndCard.substring(0, checkEndCard.length()-1) + "0";
			}
			int countCreatedCards = this.businessHelper.getProductBusiness().getCountIssuedCards(checkStartCard, checkEndCard, productType);

			if (countCreatedCards > 0) {
				throw new WrongValueException("There Are " + countCreatedCards + " cards already issued within this range.");
			}
		
		}
		
		//generate next allocate able card no based on previous card no
		List<String> cardNoLists = Lists.newArrayList();
		for(int i=0; i<totalCount; i++){
			
			currentNo ++;
			String cardNo = CardNoGenerator.generateCardNo(currentNo, productType);
			cardNoLists.add(cardNo);
		}
		
		//to update so that can if there concurrency, exception will thrown to guarantee count number in sequence
		if(type==IssueType.COUNT){
			
			cardNoSequence.setCount(BigDecimal.valueOf(currentNo));
			super.update(cardNoSequence);
		}
		
		return cardNoLists;
		
		
	}
	
	
	
	public void terminateProducts(List<PmtbProduct> products,List<PmtbProductStatus> productStatuses, String userId, String terminateReason) throws Exception{
		
		try
		{
			logger.info("products Obj size"+products.size());
			logger.info("User Id"+userId);
			this.daoHelper.getGenericDao().saveAll(products,userId);
			logger.info("products Saved");
			logger.info("products Statuses Obj size"+productStatuses.size());
			this.daoHelper.getGenericDao().saveAll(productStatuses,userId);
			logger.info("products Statuses Saved");
			logger.info("AS Request(s) are inserting ...");
			
			Iterator<PmtbProduct> iter = products.iterator();
			while (iter.hasNext())
			{
				PmtbProduct product = iter.next();
				UpdateFutureSchedules(product,userId);
				updateProductAPI(product, userId,ConfigurableConstants.getMasterTable(ConfigurableConstants.PRODUCT_TERMINATE_REASON,terminateReason));
			}
		}
		catch (Exception e)
		{
			throw new Exception();
		}
	}

	
	private void moveStatusDuringReplacement(PmtbProduct oldProduct, PmtbProduct newProduct, PmtbProductStatus oldProductStatus, String userId){
		
		/*
		 * Yiming
		 * To move the old status from old card to new card if there is any
		 * 
		 */
		Set<PmtbProductStatus> statuses = new TreeSet<PmtbProductStatus>(new Comparator<PmtbProductStatus>(){
			public int compare(PmtbProductStatus o1, PmtbProductStatus o2) {
				return o1.getStatusDt().compareTo(o2.getStatusDt());
			}
		});
		statuses.addAll(oldProduct.getPmtbProductStatuses());
		Set<PmtbProductStatus> moveStatuses = new TreeSet<PmtbProductStatus>(new Comparator<PmtbProductStatus>(){
			public int compare(PmtbProductStatus o1, PmtbProductStatus o2) {
				return o1.getStatusDt().compareTo(o2.getStatusDt());
			}
		});
		PmtbProductStatus lastStatusBeforeReplace = null;
		for(PmtbProductStatus status : statuses){
			// only move those that are later than the replacement date
			if(status.getStatusDt().getTime() > oldProductStatus.getStatusDt().getTime()){
				moveStatuses.add(status);
			} else {
				lastStatusBeforeReplace = status;
			}
		}
		oldProduct.getPmtbProductStatuses().removeAll(moveStatuses);
		this.daoHelper.getGenericDao().deleteAll(moveStatuses);
		List<PmtbProductStatus> copyStatuses = new ArrayList<PmtbProductStatus>();
		boolean updateFirstStatus = lastStatusBeforeReplace!=null && lastStatusBeforeReplace.getStatusFrom().equals(NonConfigurableConstants.PRODUCT_STATUS_SUSPENDED) ? false : true;
		for(PmtbProductStatus status : moveStatuses){
			if(
					lastStatusBeforeReplace!=null &&
					lastStatusBeforeReplace.getStatusTo().equals(NonConfigurableConstants.PRODUCT_STATUS_SUSPENDED) &&
					status.getStatusFrom().equals(NonConfigurableConstants.PRODUCT_STATUS_NEW)){
				// that means it is suspended before replacement. to change to active instead.
				status.setStatusTo(NonConfigurableConstants.PRODUCT_STATUS_ACTIVE);
			}
			if(
					!updateFirstStatus &&
					status.getStatusFrom().equals(NonConfigurableConstants.PRODUCT_STATUS_SUSPENDED) &&
					(status.getStatusTo().equals(NonConfigurableConstants.PRODUCT_STATUS_ACTIVE) || status.getStatusTo().equals(NonConfigurableConstants.PRODUCT_STATUS_PARENT_SUSPENDED))
			){
				if(status.getStatusTo().equals(NonConfigurableConstants.PRODUCT_STATUS_ACTIVE)){
					// needs to check whether is there any status being removed from parent.
					List<AmtbAcctStatus> acctStatuses = this.getAccountStatus(oldProduct);
					for(AmtbAcctStatus acctStatus : acctStatuses){
						if(acctStatus.getEffectiveDt().getTime() > DateUtil.getCurrentTimestamp().getTime() && status.getStatusDt().getTime() > acctStatus.getEffectiveDt().getTime()){
							// status that are between first suspension and current date
							if(acctStatus.getAcctStatus().equals(NonConfigurableConstants.ACCOUNT_STATUS_ACTIVE)){
								PmtbProductStatus copyStatus = new PmtbProductStatus(
										NonConfigurableConstants.PRODUCT_STATUS_PARENT_SUSPENDED,
										NonConfigurableConstants.PRODUCT_STATUS_ACTIVE,
										acctStatus.getEffectiveDt(),
										acctStatus.getStatusRemarks(),
										0,
										null,
										null,
										null,
										null,
										acctStatus.getMstbMasterTable(),
										newProduct
								);
								copyStatuses.add(copyStatus);
							}
						}
					}
				}
				updateFirstStatus = true;
				continue;
			}
			// skipping terminated status
			if(status.getStatusTo().equals(NonConfigurableConstants.PRODUCT_STATUS_TERMINATED)){
				continue;
			}
			PmtbProductStatus copyStatus = new PmtbProductStatus(
					status.getStatusFrom(),
					status.getStatusTo(),
					status.getStatusDt(),
					status.getStatusRemarks(),
					status.getVersion(),
					status.getCreatedDt(),
					status.getCreatedBy(),
					status.getUpdatedDt(),
					status.getUpdatedBy(),
					status.getMstbMasterTable(),
					newProduct
			);
			copyStatuses.add(copyStatus);
		}
		this.daoHelper.getGenericDao().saveAll(copyStatuses, userId);
		
	}
	
	

	public PmtbProduct replaceProduct(PmtbProduct product, PmtbProductType productType, java.sql.Date expiryDate, Timestamp expiryTime, boolean isGenerateNewCard, boolean isWaiveReplacementFee, BigDecimal fee, String replacementRemarks, String replacementReason, String userId) throws Exception{

		Date currentDate=new Date();
		PmtbProductReplacement productReplacement = null;
	
		//Processing Indicator
	
		PmtbProduct newProduct = null;
		java.sql.Date replacementDate = new java.sql.Date(currentDate.getTime());
		/* *********************************
    	 * Create product replacement object
    	 * *********************************/
		
		logger.debug("Processing replacement of card no: " + product.getCardNo());
		
		productReplacement=new PmtbProductReplacement();
		productReplacement.setReplacementRemarks(replacementRemarks);
		productReplacement.setReplacementDate(replacementDate);
		productReplacement.setMstbMasterTable(ConfigurableConstants.getMasterTable(ConfigurableConstants.REPLACEMENT_REASON, replacementReason));
		productReplacement.setReplacementFee(fee);
		productReplacement.setWaiveReplacement(NonConfigurableConstants.getBooleanFlag(isWaiveReplacementFee));
		
		productReplacement.setPmtbProduct(product);
		productReplacement.setCurrentCardNo(product.getCardNo());
		
		//stamp the billed flag
		//if the product type is PREPAID, consider it is billed 
		//as the replacement fee etc is being processed immediately rather than generate the invoice
		if(NonConfigurableConstants.getBoolean(productType.getPrepaid())){
			productReplacement.setBilledFlag(NonConfigurableConstants.BOOLEAN_YES);
		} else {
			productReplacement.setBilledFlag(NonConfigurableConstants.BOOLEAN_NO);
		}        		
	 	
		//stamp the new expire date, time, current expire date time
		
       	java.sql.Date currentExpDate = product.getExpiryDate();
       	java.sql.Date newExpDate = null;
       	
       	if(expiryDate!=null){
       		newExpDate = expiryDate;
       	} else {
       		newExpDate = currentExpDate;
       	}
       	Timestamp currentExpTime = product.getExpiryTime();
       	Timestamp newExpTime = null;
   		
       	if(expiryTime!=null){
       		newExpTime = expiryTime;
       	}else {
       		newExpTime = currentExpTime;
       	}
       	
       	productReplacement.setCurrentExpDate(currentExpDate);
       	productReplacement.setNewExpDate(newExpDate);
       	productReplacement.setCurrentExpTime(currentExpTime);
       	productReplacement.setNewExpTime(newExpTime);

	       
		//stamp the new card no
       	String newCardNo =null;
       	if(isGenerateNewCard){
       		PmtbCardNoSequence seq = this.businessHelper.getProductBusiness().getCardNoSequence(productType);
       		Integer nextNum = CardNoGenerator.nextCardNoSeq(seq);
       		newCardNo = CardNoGenerator.generateCardNo(nextNum, productType);
       		
       		seq.setCount(BigDecimal.valueOf(nextNum));
       		super.update(seq);
       	} else {
       		newCardNo = product.getCardNo();
       	}
   		productReplacement.setNewCardNo(newCardNo);
	
   		
		if(isGenerateNewCard) {
    		/* **************************************
        	 * Create new product object for new card
        	 * **************************************/
    		newProduct =new PmtbProduct();
    		newProduct.setIssueDate(new java.sql.Date (currentDate.getTime()));
    		newProduct.setCardNo(productReplacement.getNewCardNo());
    		// change by tan yiming. cannot use active by default as the replaced card can be suspended
    		// need to find the current status
        	Set<AmtbAcctStatus> sorted = new TreeSet<AmtbAcctStatus>(new Comparator<AmtbAcctStatus>(){
				public int compare(AmtbAcctStatus o1, AmtbAcctStatus o2) {
					return o1.getEffectiveDt().compareTo(o2.getEffectiveDt());
				}
        	});
        	sorted.addAll(this.businessHelper.getProductBusiness().getAccountStatus(product));
        	AmtbAcctStatus acctCurrentStatus = null;
        	for(AmtbAcctStatus status : sorted){
        		if(status.getEffectiveDt().getTime() > DateUtil.getCurrentTimestamp().getTime()){
        			break;
        		}
        		acctCurrentStatus = status;
        	}
    		newProduct.setCurrentStatus(acctCurrentStatus.getAcctStatus().equals(NonConfigurableConstants.ACCOUNT_STATUS_ACTIVE) ? productType.getDefaultCardStatus() : NonConfigurableConstants.PRODUCT_STATUS_PARENT_SUSPENDED);
    		newProduct.setAmtbAccount(product.getAmtbAccount());
    		newProduct.setIsIndividualCard(product.getIsIndividualCard());
    		newProduct.setCardHolderName(product.getCardHolderName());
    		newProduct.setCardHolderSalutation(product.getCardHolderSalutation());
    		newProduct.setCardHolderTel(product.getCardHolderTel());
    		newProduct.setCardHolderTitle(product.getCardHolderTitle());
    		newProduct.setCardHolderMobile(product.getCardHolderMobile());
    		newProduct.setCardHolderEmail(product.getCardHolderEmail());
    		
    
    		if(NonConfigurableConstants.getBoolean(productType.getPrepaid())){
    			//do nothing here, the card value, cash plus and credit balance will be populated at logic below
    			
    		} else {
    			
    			//Change 14th Apr 2010 - Due to payment will not propagate the updating of Credit Balance
        		//from old card to replace card, GH decision is to simplify it by giving full credit balance
        		//to new replaced card.
//    			newProduct.setCreditBalance(product.getCreditLimit());
    			newProduct.setCreditBalance(product.getCreditBalance());
        		newProduct.setCreditLimit(product.getCreditLimit());
    		}
    		
    		
    		if(productReplacement.getNewExpDate()!=null){
    			newProduct.setExpiryDate(productReplacement.getNewExpDate());
    		}
    		if(productReplacement.getNewExpTime()!=null){
    			newProduct.setExpiryTime(productReplacement.getNewExpTime());
    		}

    		
    		
        	newProduct.setPmtbProductType(productType);
        	newProduct.setWaiveSubscFeeFlag(product.getWaiveSubscFeeFlag());
        					        	
        	newProduct.setSmsExpiryFlag(product.getSmsExpiryFlag());
        	newProduct.setSmsTopupFlag(product.getSmsTopupFlag());
        	
        	//Waive Card Issuance to always default to Y for replacement card.
        	newProduct.setWaiveIssuanceFeeFlag(NonConfigurableConstants.BOOLEAN_YES);
        	newProduct.setOfflineCount(product.getOfflineCount());
        	newProduct.setOfflineAmount(product.getOfflineAmount());
        	newProduct.setOfflineTxnAmount(product.getOfflineTxnAmount());
   
        	newProduct.setNameOnProduct(product.getTransientNameOnProduct());
        	newProduct.setEmbossNameOnCard(product.getTransientEmbossNameOnCard());
        	
        	
        	if(NonConfigurableConstants.getBoolean(productType.getPrepaid())){
        		newProduct.setExpiredFlag(product.getExpiredFlag());
            	
        		newProduct.setBalanceForfeitedFlag(product.getBalanceForfeitedFlag());
            	newProduct.setBalanceExpiryDate(product.getBalanceExpiryDate());
    		} 
        	
        	/* ****************************
        	 * Terminate Old Product Object
        	 * ****************************/
		
			if(NonConfigurableConstants.getBoolean(productType.getPrepaid())){
				//reset the forfeited flag so that the terminated card can be proceed by balance forfeiture batch job
				product.setBalanceForfeitedFlag(NonConfigurableConstants.BOOLEAN_YN_NO);
			}
			
			PmtbProductCreditLimit productCreditLimit = null;
			if(!NonConfigurableConstants.getBoolean(productType.getPrepaid())){
				productCreditLimit = populateCreditLimit(product, currentDate);
			}
			
			PmtbProductStatus oldProductStatus = populateProductStatus(product, productReplacement.getMstbMasterTable());
			
			// change by tan yiming. cannot use default card status as the card can be suspended currently
			String statusTo = acctCurrentStatus.getAcctStatus().equals(NonConfigurableConstants.ACCOUNT_STATUS_ACTIVE) ? productType.getDefaultCardStatus() : NonConfigurableConstants.PRODUCT_STATUS_PARENT_SUSPENDED;
			PmtbProductStatus newProductStatus	= populateNewCardProductStatus(newProduct, statusTo);
			product.setCurrentStatus(NonConfigurableConstants.PRODUCT_STATUS_TERMINATED);
			
			List<PmtbProductStatus> futureProductStatus = this.daoHelper.getProductDao().getFutureProductStatusesByProductNo(product.getProductNo(), DateUtil.getCurrentTimestamp());
			// clearing all status that is after the terminated.
			if(futureProductStatus != null)
			{
				List<PmtbProductStatus> deleteStatuses = new ArrayList<PmtbProductStatus>();
				for(PmtbProductStatus status : futureProductStatus){
					if(status.getStatusDt().after(DateUtil.getCurrentTimestamp())){
						deleteStatuses.add(status);
					}
				}
				 
				this.daoHelper.getProductDao().deleteAll(deleteStatuses);
			}
        	/* **************************************
        	 * Save all objects under one transaction 
        	 * **************************************/
			
			
			this.daoHelper.getGenericDao().update(product, userId);
			this.daoHelper.getGenericDao().save(oldProductStatus, userId);
			BigDecimal newProductNo = (BigDecimal) this.daoHelper.getGenericDao().save(newProduct, userId);
			productReplacement.setNewProductNo(newProductNo);
			this.daoHelper.getGenericDao().save(productReplacement, userId);
			this.daoHelper.getGenericDao().save(newProductStatus, userId);
			if(productCreditLimit!=null){
				this.daoHelper.getGenericDao().save(productCreditLimit, userId);
			}
			
			/* *****************************************************
			 * Seng Tat - 23/04/2010
			 * Old card replaced with new card
			 * To check whether old card is registered with a login.
			 * If yes, update login to new card.
			 * *****************************************************/
			List<IttbCpLoginNew> logins = this.daoHelper.getPortalDao().getPortalUserNew(product.getProductNo());
			for(IttbCpLoginNew login : logins){
				logger.info("Card Replacement - Login found:"+login.getLoginId());
				login.setPmtbProduct(newProduct);
				login.setUpdatedDt(DateUtil.getCurrentTimestamp());
				login.setUpdatedBy(userId);
				this.daoHelper.getGenericDao().update(newProduct, userId);
			}
			
			moveStatusDuringReplacement(product, newProduct, oldProductStatus, userId);
			
			
		}
		else{
			String oldNameOnProduct = product.getNameOnProduct();
			
			product.setNameOnProduct(product.getTransientNameOnProduct());
			product.setEmbossNameOnCard(product.getTransientEmbossNameOnCard());
			
			//Update Sync with ittbCpCustCardIssuance Table
			updateIttbNameOnCard(product, oldNameOnProduct);
			
        	product.setSmsExpirySent(NonConfigurableConstants.BOOLEAN_NO);
        	
		    if(productReplacement.getNewExpDate()!=null){
		    	product.setExpiryDate(productReplacement.getNewExpDate());
		    }
		    if(productReplacement.getNewExpTime()!=null){
		    	product.setExpiryTime(productReplacement.getNewExpTime());
		    }

		    this.daoHelper.getGenericDao().save(productReplacement, userId);
			this.daoHelper.getGenericDao().update(product, userId);
       		
		}
		
	
		//handle replacement for PREPAID
		if(NonConfigurableConstants.getBoolean(productType.getPrepaid())){
    		
			//do a transfer between from and to card
			PmtbProduct transferFromProduct = null;
			PmtbProduct transferToProduct = null;
			String transferInType = null;
			String transferOutType = null;
			if(isGenerateNewCard){
				//If new card is generated, the replacement fee is deduct from new card
				transferFromProduct = product;
				transferToProduct = newProduct; 
				transferInType = NonConfigurableConstants.PREPAID_TXN_TYPE_EXTERNAL_TRANSFER_IN;
				transferOutType = NonConfigurableConstants.PREPAID_TXN_TYPE_EXTERNAL_TRANSFER_OUT;
				
			} else {
				transferFromProduct = product;
				transferToProduct = product; 
				transferInType = NonConfigurableConstants.PREPAID_TXN_TYPE_INTERNAL_TRANSFER_IN;
				transferOutType = NonConfigurableConstants.PREPAID_TXN_TYPE_INTERNAL_TRANSFER_OUT;
			}
			
			
			TransferableAmtInfo transferInfo = this.businessHelper.getPrepaidBusiness().calculateTransferableValueAndCashPlus(transferFromProduct);
			//transfer all the transferable amount to apply amount
			ApplyAmtInfo applyAmtInfo = transferInfo.toApplyAmtInfo();
			
			//do a transfer from 
			PrepaidUtil.deductProductAmtWithApplyAmtInfo(transferFromProduct, applyAmtInfo, false);
				
			//do a transfer to 
			PrepaidUtil.addProductAmtWithApplyAmtInfo(transferToProduct, applyAmtInfo);
		
			this.daoHelper.getGenericDao().update(transferFromProduct, userId);
			this.daoHelper.getGenericDao().update(transferToProduct, userId);
			
			//create the PREPAID transaction
			BigDecimal transferedCardValue = applyAmtInfo.getApplyCardValue();
			BigDecimal transferedCashplus = applyAmtInfo.getApplyCashplus();
			BigDecimal transferedCreditBalance = applyAmtInfo.getApplyCreditBalance();
						
			List<PmtbPrepaidCardTxn> txns = Lists.newArrayList();
			txns.add(PmtbPrepaidCardTxn.buildTxn(null, transferFromProduct, transferOutType, 
					transferedCreditBalance.negate(), null, transferedCardValue.negate(),  transferedCashplus.negate(), false));
			
			txns.add(PmtbPrepaidCardTxn.buildTxn(null, transferToProduct, transferInType, 
					transferedCreditBalance, null, transferedCardValue, transferedCashplus, false));
			
			this.daoHelper.getGenericDao().saveAll(txns, userId);
			//process replacement fee
			if(!isWaiveReplacementFee){
    			
    			PmtbProduct productDeductReplaceFee = null;
    			if(isGenerateNewCard){
    				//If new card is generated, the replacement fee is deduct from new card
    				productDeductReplaceFee = newProduct; 
    			} else {
    				//Else deduct from existing card
    				productDeductReplaceFee = product;
    			}
    			// get GST of replacement
    			
    			AmtbAccount topAcct = AccountUtil.getTopLevelAccount(product.getAmtbAccount());
    			AmtbAccount topAcctWithEntity = this.daoHelper.getAccountDao().getAccountWithEntity(topAcct);
    			FmtbEntityMaster entity = topAcctWithEntity.getFmtbArContCodeMaster().getFmtbEntityMaster();
    			BigDecimal gstRate = this.daoHelper.getFinanceDao().getLatestGST(entity.getEntityNo(), null, DateUtil.getCurrentTimestamp(), NonConfigurableConstants.TRANSACTION_TYPE_PREPAID_REPLACEMENT_FEE);
    			GstInfo gstInfo = GstUtil.forwardCalculateGstInfo(fee, gstRate);
    			BigDecimal replacementFeeWithoutGst = gstInfo.getAmountWithoutGst();
    			BigDecimal replacementFeeGst = gstInfo.getGst();
    			BigDecimal replacementFeeWithGst = gstInfo.getAmountWithGst();
    			
    			
    			ApplyAmtInfo replacementFeeApplyAmount = PrepaidUtil.calculateCardValuePrefApplyAmount(productDeductReplaceFee.getCardValue(), productDeductReplaceFee.getCashplus(), replacementFeeWithGst);
    			PrepaidUtil.deductProductAmtWithApplyAmtInfo(productDeductReplaceFee, replacementFeeApplyAmount, true);
    			
    			BigDecimal deductFromValue = replacementFeeApplyAmount.getApplyCardValue();
    			BigDecimal deductFromCashPlus =  replacementFeeApplyAmount.getApplyCashplus();
    			
    			super.update(productDeductReplaceFee);
    			
    			PmtbPrepaidCardTxn replaceFeeTxn = PmtbPrepaidCardTxn.buildTxn(null, productDeductReplaceFee, NonConfigurableConstants.PREPAID_TXN_TYPE_REPLACEMENT_FEE, 
						replacementFeeWithoutGst.negate(), replacementFeeGst.negate(), deductFromValue.negate(), deductFromCashPlus.negate(), false);

    			super.save(replaceFeeTxn);
    			super.businessHelper.getPrepaidBusiness().createPrepaidDirectReceipt(replaceFeeTxn, userId);
    			
    			
    			
    		}
    	}
		
		//now handle AS
		if(isGenerateNewCard){
			/* **************************************
	    	 * After successful save, interface to AS 
	    	 * **************************************/
	    	this.businessHelper.getProductBusiness().updateProductAPICardLost(product, userId,
					ConfigurableConstants.getMasterTable(ConfigurableConstants.REPLACEMENT_REASON,replacementReason));
	    	this.businessHelper.getProductBusiness().createProductAPI(newProduct, userId);
		} else {
			this.businessHelper.getProductBusiness().updateProductAPIActive(product, userId);
		}
      
		//virtual email
		boolean isVirtual = NonConfigurableConstants.getBoolean(productType.getVirtualProduct());
		if(isVirtual)
		{
			List<PmtbProduct> productList = Lists.newArrayList();
			productList.add(product);
			logger.info("Replace Virtual Card Detected, preparing virtual email");
			
			String remarkx = "REPLACEMENT"; 
			MstbMasterTable mstbReplace = ConfigurableConstants.getMasterTable(ConfigurableConstants.REPLACEMENT_REASON,replacementReason);
			if(mstbReplace != null)
			{
				if(mstbReplace.getMasterValue() != null) {
					if(!mstbReplace.getMasterValue().trim().equals(""))
						remarkx = mstbReplace.getMasterValue();
				}
			}
			createVirtualEmailList(productList, replacementDate, remarkx);
			//replacementRemarks
		}	
		
		
		return newProduct;

	}
		
		
	private PmtbProductCreditLimit populateCreditLimit(PmtbProduct product, Date currentDate){
		
		/* **************************************
    	 * Populating Product Credit Limit Object
    	 * **************************************/
		PmtbProductType productType= product.getPmtbProductType();
    	PmtbProductCreditLimit productCreditLimit = null;
    	if(productType.getCreditLimit().equalsIgnoreCase(NonConfigurableConstants.BOOLEAN_YES)){
    		productCreditLimit = new PmtbProductCreditLimit();
    		productCreditLimit.setPmtbProduct(product);
			productCreditLimit.setCreditLimitType(NonConfigurableConstants.REVIEW_TYPE_PERMANENT);
			productCreditLimit.setEffectiveDtFrom(new Timestamp(currentDate.getTime()));
			productCreditLimit.setNewCreditLimit(product.getCreditLimit());
			productCreditLimit.setRemarks(NonConfigurableConstants.STATUS_REMARKS_ISSUE);
		}
		
    	return productCreditLimit;
		
	}
	
	private PmtbProductStatus populateNewCardProductStatus(PmtbProduct issueProduct, String statusTo){


		/* ************************************
		 * Populating New Product Status Object
		 * ************************************/
		PmtbProductStatus newCardproductStatus=new PmtbProductStatus();
		newCardproductStatus.setPmtbProduct(issueProduct);
		newCardproductStatus.setStatusFrom(NonConfigurableConstants.PRODUCT_STATUS_NEW);
		newCardproductStatus.setStatusTo(statusTo);
		newCardproductStatus.setStatusRemarks(NonConfigurableConstants.STATUS_REMARKS_REPLACED);
		newCardproductStatus.setStatusDt(DateUtil.getCurrentTimestamp());
		newCardproductStatus.setMstbMasterTable(ConfigurableConstants.getMasterTable(ConfigurableConstants.ISSUE_REASON_TYPE, ConfigurableConstants.ISSUE_REASON_CODE));
		
		return newCardproductStatus;
	}
	
	
	private PmtbProductStatus populateProductStatus(PmtbProduct product, MstbMasterTable masterTable){

		/* ******************************************
		 * Create new product status for the new card
		 * *******************************************/
		PmtbProductStatus productStatus=new PmtbProductStatus();
		productStatus.setPmtbProduct(product);
		productStatus.setStatusFrom(product.getCurrentStatus());
		productStatus.setStatusTo(NonConfigurableConstants.PRODUCT_STATUS_TERMINATED);
		productStatus.setStatusRemarks(NonConfigurableConstants.STATUS_REMARKS_REPLACED);
		productStatus.setStatusDt(DateUtil.getCurrentTimestamp());
		productStatus.setMstbMasterTable(ConfigurableConstants.getMasterTable(ConfigurableConstants.PRODUCT_TERMINATE_REASON,ConfigurableConstants.PRODUCT_TERMINATE_CODE));
		
		if(productStatus.getMstbMasterTable()==null){
			logger.warn("Terminate Reason Master Record not found, replacing with product replacement's");
			productStatus.setMstbMasterTable(masterTable);
		}

		return productStatus;
	}



	public void updateProduct(PmtbProduct product, String userLoginId, String oldNameOnProduct) throws Exception{
		
		try {
			PmtbProduct productAndAccount= (PmtbProduct) this.daoHelper.getProductDao().getProductAndAccount(product);
			String status;
			String reasonCode;
			String productCurrentStatus = product.getCurrentStatus();
			PmtbProductStatus currentStatus = getLatestProductStatus(product.getCardNo(), DateUtil.getCurrentTimestamp());
			
			if(NonConfigurableConstants.PRODUCT_STATUS_SUSPENDED.equals(productCurrentStatus) || 
					NonConfigurableConstants.PRODUCT_STATUS_PARENT_SUSPENDED.equals(productCurrentStatus) ||
					NonConfigurableConstants.PRODUCT_STATUS_TERMINATED.equals(productCurrentStatus) ||
					NonConfigurableConstants.PRODUCT_STATUS_RECYCLED.equals(productCurrentStatus)
					)
			{
				status = NonConfigurableConstants.AS_REQUEST_PRODUCT_STATUS_INACTIVE;
				reasonCode = currentStatus.getMstbMasterTable().getInterfaceMappingValue();
			}
			else
			{
				status = NonConfigurableConstants.AS_REQUEST_PRODUCT_STATUS_ACTIVE;
				reasonCode = null;
			}
			
			AmtbAccount amtbAccount = productAndAccount.getAmtbAccount();
			PmtbProductType pmtbProductType = productAndAccount.getPmtbProductType();
			API.updateProduct(product.getCardNo(),
					status,
					product.getCreditBalance().toString(),
					DateUtil.convertDateToStr(product.getExpiryDate(), DateUtil.INTERFACE_AS_PRODUCT_EXPIRY_DATE_FORMAT),
					API.formulateAccountId(amtbAccount),
					reasonCode,
					userLoginId,
					StringUtil.numberToString(product.getOfflineCount()),
					StringUtil.bigDecimalToString(product.getOfflineAmount()),
					StringUtil.bigDecimalToString(product.getOfflineTxnAmount()),
					API.formulateForceOnline(product, pmtbProductType),
					NonConfigurableConstants.AS_REQUEST_TRANSFER_MODE_ASYNCHRONOUS);
			
			this.daoHelper.getGenericDao().evict(productAndAccount);
			updateIttbNameOnCard(product, oldNameOnProduct);
			this.daoHelper.getGenericDao().update(product, userLoginId);
			
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		
	}
	
	public void suspendProduct(List<BigDecimal> productIds, Timestamp suspensionTimestamp, Timestamp reactivationTimestamp, MstbMasterTable reason, String remarks, String userId) throws Exception{
		List<PmtbProduct> products = this.daoHelper.getProductDao().getProducts(productIds);
		List<PmtbProduct> updatedProducts = new ArrayList<PmtbProduct>();
		List<PmtbProductStatus> newProductStatuses = new ArrayList<PmtbProductStatus>();
		List<PmtbProductStatus> updatedProductStatuses = new ArrayList<PmtbProductStatus>();
		List<PmtbProductStatus> deletedProductStatus = new ArrayList<PmtbProductStatus>();
		for(PmtbProduct product : products){
			// checking if current time. If yes, change the status.
			if(suspensionTimestamp.before(new Date())){// suspend now
				product.setCurrentStatus(NonConfigurableConstants.PRODUCT_STATUS_SUSPENDED);
				updatedProducts.add(product);
				// updating AS
				updateProductAPI(product, userId, reason);
			}
			// now creating the status
			PmtbProductStatus newStatus = new PmtbProductStatus();
			newStatus.setMstbMasterTable(reason);
			newStatus.setPmtbProduct(product);
			newStatus.setStatusDt(suspensionTimestamp);
			newStatus.setStatusFrom(product.getCurrentStatus());
			newStatus.setStatusRemarks(remarks);
			newStatus.setStatusTo(NonConfigurableConstants.PRODUCT_STATUS_SUSPENDED);
			newProductStatuses.add(newStatus);
			product.getPmtbProductStatuses().add(newStatus);
			for(PmtbProductStatus status : product.getPmtbProductStatuses()){
				if(status.getStatusDt().after(suspensionTimestamp) && (reactivationTimestamp==null || status.getStatusDt().before(reactivationTimestamp))){
					String statusMasterType = status.getMstbMasterTable().getMasterType();
					String statusTo = status.getStatusTo();
					if(
							(statusMasterType.equals(ConfigurableConstants.PRODUCT_SUSPEND_REASON) || statusMasterType.equals(ConfigurableConstants.PRODUCT_REACTIVATE_REASON))
					){
						deletedProductStatus.add(status);
					}else if(
							(statusMasterType.equals(ConfigurableConstants.ACCT_SUSPEND_REASON) || statusMasterType.equals(ConfigurableConstants.ACCT_REACTIVATE_REASON)) &&
							(statusTo.equals(NonConfigurableConstants.ACCOUNT_STATUS_ACTIVE))
					){
						status.setStatusTo(NonConfigurableConstants.PRODUCT_STATUS_SUSPENDED);
						if(!newProductStatuses.contains(status)){
							updatedProductStatuses.add(status);
						}
					}
				}
			}
			product.getPmtbProductStatuses().removeAll(deletedProductStatus);
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
						if(!newProductStatuses.contains(status)){
							updatedProductStatuses.add(status);
						}
					}
				}
				// now assigning previous status and go to next status
				prevStatus = status;
			}
		}
		updatedProductStatuses.removeAll(deletedProductStatus);
		this.daoHelper.getProductDao().updateAll(updatedProducts, userId);
		this.daoHelper.getProductDao().saveAll(newProductStatuses, userId);
		this.daoHelper.getProductDao().updateAll(updatedProductStatuses, userId);
		this.daoHelper.getProductDao().deleteAll(deletedProductStatus);
	}
	private String getAccountStatus(Collection<AmtbAcctStatus> statuses, Date date){
		TreeSet<AmtbAcctStatus> sortedStatuses = new TreeSet<AmtbAcctStatus>(new Comparator<AmtbAcctStatus>(){
			public int compare(AmtbAcctStatus o1, AmtbAcctStatus o2) {
				return o2.getEffectiveDt().compareTo(o1.getEffectiveDt());
			}
		});
		sortedStatuses.addAll(statuses);
		for(AmtbAcctStatus status : sortedStatuses){
			// getting the first date that is before the specified date
			if(status.getEffectiveDt().before(date)){
				return status.getAcctStatus();
			}
		}
		return null;
	}
	public void reactivateProduct(List<BigDecimal> productIds, Timestamp reactivationTimestamp, MstbMasterTable reason, String remarks, String userId) throws Exception{
		List<PmtbProduct> products = this.daoHelper.getProductDao().getProducts(productIds);
		List<PmtbProduct> updatedProducts = new ArrayList<PmtbProduct>();
		List<PmtbProductStatus> newProductStatuses = new ArrayList<PmtbProductStatus>();
		List<PmtbProductStatus> updatedProductStatuses = new ArrayList<PmtbProductStatus>();
		for(PmtbProduct product : products){
			// checking if parent suspended
			boolean parentSuspended = 
				getAccountStatus(product.getAmtbAccount().getAmtbAcctStatuses(), reactivationTimestamp) != null &&
				getAccountStatus(product.getAmtbAccount().getAmtbAcctStatuses(), reactivationTimestamp).equals(NonConfigurableConstants.ACCOUNT_STATUS_ACTIVE) ?
						false :
						true;
			// checking if current time. If yes, change the status.
			if(reactivationTimestamp.before(new Date())){// suspend now
				if(parentSuspended){
					product.setCurrentStatus(NonConfigurableConstants.PRODUCT_STATUS_PARENT_SUSPENDED);
				}else{
					product.setCurrentStatus(NonConfigurableConstants.PRODUCT_STATUS_ACTIVE);
					updateProductAPIActive(product, userId);
				}
				updatedProducts.add(product);
				// updating AS
			}
			// now creating the status
			PmtbProductStatus newStatus = new PmtbProductStatus();
			newStatus.setMstbMasterTable(reason);
			newStatus.setPmtbProduct(product);
			newStatus.setStatusDt(reactivationTimestamp);
			newStatus.setStatusFrom(product.getCurrentStatus());
			newStatus.setStatusRemarks(remarks);
			if(parentSuspended){
				newStatus.setStatusTo(NonConfigurableConstants.PRODUCT_STATUS_PARENT_SUSPENDED);
			}else{
				newStatus.setStatusTo(NonConfigurableConstants.PRODUCT_STATUS_ACTIVE);
			}
			newProductStatuses.add(newStatus);
			product.getPmtbProductStatuses().add(newStatus);
			for(PmtbProductStatus status : product.getPmtbProductStatuses()){
				if(status.getStatusDt().after(reactivationTimestamp)){
					String statusMasterType = status.getMstbMasterTable().getMasterType();
					String statusTo = status.getStatusTo();
					if(
							(statusMasterType.equals(ConfigurableConstants.PRODUCT_SUSPEND_REASON) || statusMasterType.equals(ConfigurableConstants.PRODUCT_REACTIVATE_REASON)) &&
							statusTo.equals(NonConfigurableConstants.PRODUCT_STATUS_SUSPENDED)
					){
						break;
					}
					if(
							(statusMasterType.equals(ConfigurableConstants.ACCT_SUSPEND_REASON) || statusMasterType.equals(ConfigurableConstants.ACCT_REACTIVATE_REASON)) &&
							statusTo.equals(NonConfigurableConstants.ACCOUNT_STATUS_SUSPENDED)
					){
						status.setStatusTo(NonConfigurableConstants.PRODUCT_STATUS_ACTIVE);
						if(!newProductStatuses.contains(status)){
							updatedProductStatuses.add(status);
						}
					}
				}
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
				if(prevStatus != null){
					// if previous status to not equals status from
					if(!prevStatus.getStatusTo().equals(status.getStatusFrom())){
						status.setStatusFrom(prevStatus.getStatusTo());
						// if it is not the new status
						if(!newProductStatuses.contains(status)){
							updatedProductStatuses.add(status);
						}
					}
				}
				// now assigning previous status and go to next status
				prevStatus = status;
			}
		}
		this.daoHelper.getProductDao().updateAll(updatedProducts, userId);
		this.daoHelper.getProductDao().saveAll(newProductStatuses, userId);
		this.daoHelper.getProductDao().updateAll(updatedProductStatuses, userId);
	}
	
	public List<Object[]> getNegativeExternalProduct(String cardNoStart, String cardNoEnd, PmtbProductType productType){
		return this.daoHelper.getProductDao().getNegativeExternalProduct(cardNoStart, cardNoEnd, productType);
	}
	
	public List<String> getProducts(String custNo, String cardHolderName){
		return this.daoHelper.getProductDao().getProducts(custNo, cardHolderName);
	}
	
	public List<String> getDistinctNameOnCards(String custNo, String nameOnCard){
		return this.daoHelper.getProductDao().getDistinctNameOnCards(custNo, nameOnCard);
	}
	
	public List<AmtbAcctStatus> getAccountStatus(PmtbProduct product){
		logger.info("getAccountStatus(product = "+product.getProductNo()+")");
		return this.daoHelper.getAccountDao().getAccountStatus(product.getAmtbAccount().getAccountNo());
	}
	public boolean hasFutureTermination(Collection<BigDecimal> productNos){
		return this.daoHelper.getProductDao().hasFutureTermination(productNos);
	}
	public boolean hasTerminated(Collection<BigDecimal> productNos){
		return this.daoHelper.getProductDao().hasTerminated(productNos);
	}
	public void terminateProducts(Collection<BigDecimal> productNos, Date terminateDate, String terminateRemarks, String terminateReason, String userId){
		logger.info("terminateProducts(productNos.size() = "+productNos.size()+", terminateDate = "+terminateDate+", terminateRemarks = "+terminateRemarks+", terminateReason = "+terminateReason+")");
		MstbMasterTable terminateMaster = ConfigurableConstants.getMasterTable(ConfigurableConstants.PRODUCT_TERMINATE_REASON, terminateReason);
		List<PmtbProduct> products = this.daoHelper.getProductDao().getProducts(productNos);
		List<PmtbProductStatus> newStatuses = new ArrayList<PmtbProductStatus>();
		List<PmtbProductStatus> updateStatuses = new ArrayList<PmtbProductStatus>();
		List<PmtbProductStatus> deleteStatuses = new ArrayList<PmtbProductStatus>();
		List<PmtbProduct> updateProducts = new ArrayList<PmtbProduct>();
		List<Map<String, String>> asRequests = new ArrayList<Map<String, String>>();
		for(PmtbProduct product : products){
			PmtbProductStatus currentStatus = null;
			for(PmtbProductStatus status : product.getPmtbProductStatuses()){
				if(!status.getStatusDt().after(terminateDate)){
					if(currentStatus==null || currentStatus.getStatusDt().before(status.getStatusDt())){
						currentStatus = status;
					}
				}
			}
			logger.info("current status for "+product.getProductNo()+" = " + currentStatus.getStatusTo());
			// now looking for the terminate status if any
			PmtbProductStatus terminateStatus = null;
			for(PmtbProductStatus status : product.getPmtbProductStatuses()){
				if(status.getStatusTo().equals(NonConfigurableConstants.PRODUCT_STATUS_TERMINATED)){
					terminateStatus = status;
					break;
				}
			}
			if(terminateStatus==null){
				terminateStatus = new PmtbProductStatus();
				terminateStatus.setPmtbProduct(product);
				newStatuses.add(terminateStatus);
			}else{
				updateStatuses.add(terminateStatus);
			}
			terminateStatus.setMstbMasterTable(terminateMaster);
			terminateStatus.setStatusDt(DateUtil.convertDateToTimestamp(terminateDate));
			terminateStatus.setStatusFrom(currentStatus.getStatusTo());
			terminateStatus.setStatusRemarks(terminateRemarks);
			terminateStatus.setStatusTo(NonConfigurableConstants.PRODUCT_STATUS_TERMINATED);
			// now clearing all status that is after the terminated.
			for(PmtbProductStatus status : product.getPmtbProductStatuses()){
				if(status.getStatusDt().after(terminateDate)){
					deleteStatuses.add(status);
				}
			}
			product.getPmtbProductStatuses().removeAll(deleteStatuses);
			// check if today then update status and update AS API
			if(DateUtil.isToday(terminateDate)){
				product.setCurrentStatus(NonConfigurableConstants.PRODUCT_STATUS_TERMINATED);
				updateProducts.add(product);
				PmtbProduct productAndAccount = (PmtbProduct) this.daoHelper.getProductDao().getProductAndAccount(product);
				PmtbProductType pmtbProductType = productAndAccount.getPmtbProductType();
				Map<String, String> asRequest = new HashMap<String, String>();
				asRequest.put("transferMode", API.ASYNCHRONOUS);
				asRequest.put("cardNo", product.getCardNo());
				asRequest.put("status", NonConfigurableConstants.AS_REQUEST_PRODUCT_STATUS_INACTIVE);
				asRequest.put("newCreditBalance", product.getCreditBalance()!=null ? product.getCreditBalance().toString() : "");
				asRequest.put("expiryDate", DateUtil.convertDateToStr(product.getExpiryDate(), DateUtil.INTERFACE_AS_PRODUCT_EXPIRY_DATE_FORMAT));
				asRequest.put("accountId", API.formulateAccountId(productAndAccount.getAmtbAccount()));
				asRequest.put("reasonCode", terminateMaster.getInterfaceMappingValue());
				asRequest.put("updateBy", userId);
				asRequest.put(API.PRODUCT_OFFLINE_COUNT, StringUtil.numberToString(product.getOfflineCount()));
				asRequest.put(API.PRODUCT_OFFLINE_AMOUNT, StringUtil.bigDecimalToString(product.getOfflineAmount()));
				asRequest.put(API.PRODUCT_OFFLINE_TXN_AMOUNT, StringUtil.bigDecimalToString(product.getOfflineTxnAmount()));
				asRequest.put(API.PRODUCT_FORCE_ONLINE, API.formulateForceOnline(product, pmtbProductType));
				asRequests.add(asRequest);
			}
		}
		this.daoHelper.getGenericDao().saveAll(newStatuses, userId);
		this.daoHelper.getGenericDao().updateAll(updateStatuses, userId);
		this.daoHelper.getGenericDao().deleteAll(deleteStatuses);
		this.daoHelper.getGenericDao().updateAll(updateProducts, userId);
		try {
			API.updateProducts(asRequests);
		} catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
		}
	}
	
	public PmtbProduct getProduct(BigDecimal productNo){
		return this.daoHelper.getProductDao().getProduct(productNo);
	}
	
	public PmtbProductStatus getSuspendProductStatus(PmtbProduct product){
		return this.daoHelper.getProductDao().getProductStatus(product);
	}
	
	public boolean containPrepaidProducts(Set<BigDecimal> productIdSet){
		return this.daoHelper.getProductDao().containPrepaidProducts(productIdSet);
	}
	
	
	public PmtbProduct getProductAndAccount(PmtbProduct product) {
		return this.daoHelper.getProductDao().getProductAndAccount(product);
		
	}
	
	public List<IttbCpCustCardIssuance> checkAssignCardDate(Date assignDate, String cardNo, BigDecimal productNo) {
		return this.daoHelper.getProductDao().checkAssignCardDate(assignDate, cardNo, productNo);
	}
	
	public void updateAssignCards(IttbCpCustCardIssuance iccci, Date assignDate)
	{
		try{
			Boolean firstTimeAssign = false;
			iccci.setBatchFlag("N");
			
			//Check if it has 0 assign record. first time assign card?
			List<IttbCpCustCardIssuance> ittbCheckRecordList = this.daoHelper.getProductDao().checkAssignCardExistIttbRecord(iccci);
			
			if(ittbCheckRecordList.size() == 0 )
			{
				//if no have, create another ittb record to save, so we know the card first name on card is 
				PmtbProduct product = new PmtbProduct();
			    product=(PmtbProduct)this.daoHelper.getProductDao().getProduct(iccci.getProductNo());
			    
			    //JY 07 Dec 2015 - the PmtbProductType Name On Product should be Y before creating initial Name on Card.
			    if(null != product.getPmtbProductType())
			    {
			    	if(product.getPmtbProductType().getNameOnProduct().trim().equalsIgnoreCase("Y"))
			    	{
						IttbCpCustCardIssuance newIccci = new IttbCpCustCardIssuance();
						newIccci.setCardNo(iccci.getCardNo());
						newIccci.setProductNo(iccci.getProductNo());
						newIccci.setProductTypeId(product.getPmtbProductType().getProductTypeId());
						newIccci.setIssuedTo(product.getNameOnProduct());
			  			newIccci.setIssuedOn(new Timestamp(product.getIssueDate().getTime()));
			  			newIccci.setCreatedBy(product.getCreatedBy());
			  			newIccci.setCreatedDt(product.getCreatedDt());
			  			newIccci.setUpdatedBy(CommonWindow.getUserLoginIdAndDomain());
			  			newIccci.setUpdatedDt(new Timestamp(new Date().getTime()));
			  			newIccci.setRemarks("Initial Name on Card");
			  			newIccci.setReturnedOn(new Timestamp(assignDate.getTime()));
			  			newIccci.setBatchFlag("Y");
			  			
			  			this.save(newIccci);
			  			
			  			firstTimeAssign = true;
			    	}
			    	else
				    	logger.info("Prodct Type Name On Product is 'N' or NA, skipping creation of Initial Name on Card");
			    }
			}
			else
			{
				//Check if there is Previous Record & Update Returned on to Assign Date
				List<IttbCpCustCardIssuance> ittbPrevCpCustCardList = this.daoHelper.getProductDao().getPreviousAssignCard(iccci, assignDate);
					 
				if(ittbPrevCpCustCardList.size() > 0)
				{
					for(IttbCpCustCardIssuance prevIccci : ittbPrevCpCustCardList)
					{
						 if(null == prevIccci.getReturnedOn())
						 {
							 prevIccci.setReturnedOn(new Timestamp(assignDate.getTime()));
							 prevIccci.setUpdatedBy(CommonWindow.getUserLoginIdAndDomain());
							 prevIccci.setUpdatedDt(new Timestamp(new Date().getTime()));
							 this.update(prevIccci);
							 break;
						 }
					 }
				}
			}
			
			 //Check got Any Later Record after Assign Date.
			 List<IttbCpCustCardIssuance> ittbFutureCpCustCardList = this.daoHelper.getProductDao().getFutureAssignCard(iccci, assignDate);
	
			 if(ittbFutureCpCustCardList.size() > 0)
			 {
				 for(IttbCpCustCardIssuance futureIccci : ittbFutureCpCustCardList)
				 {
					 iccci.setReturnedOn(futureIccci.getIssuedOn());
					 logger.info("This Card No. has a future record, setting Returned Date for : "+iccci.getCardNo());
					 break;
				 }
			 }
			 else
			 {
				 //Means no future record, can update the Name in PMTB_Product, but must check AssignDate is it future date
				 if(assignDate.compareTo(new Date()) <= 0)
				 {
					 PmtbProduct product = new PmtbProduct();
					 product=(PmtbProduct)this.daoHelper.getProductDao().getProduct(iccci.getProductNo());
					 product.setNameOnProduct(iccci.getIssuedTo());
					 product.setUpdatedBy(iccci.getUpdatedBy());
					 product.setUpdatedDt(DateUtil.getCurrentTimestamp());
					 this.save(product);
					 iccci.setBatchFlag("Y");
				 }
			 }
			 this.save(iccci);
			 
			 //if firstTimeAssign Is false , find ittbCplogin, if there is, move to archive.
			 if(!firstTimeAssign)
			 {
				 logger.info("Checking if productNo exist in CP Login.. ProductNo : "+iccci.getProductNo());
//				 IttbCpLogin ittbCpLogin = this.daoHelper.getProductDao().getIttbCpLogin(iccci.getProductNo());
				 IttbCpLoginNew ittbCpLoginNew = this.daoHelper.getProductDao().getIttbCpLoginNew(iccci.getProductNo());
				 if(null != ittbCpLoginNew)
				 {
//					 IttbCpLoginArchived archive = new IttbCpLoginArchived();
					 IttbCpLoginArchivedNew archiveNew = new IttbCpLoginArchivedNew();
					 
					 if(null != ittbCpLoginNew) {
//						if(null != ittbCpLoginNew.getLoginId())
//						 	archiveNew.setLoginId(ittbCpLoginNew.getLoginId());
						 

						 if(null != ittbCpLoginNew.getAccessId())
						 		archiveNew.setAccessId(ittbCpLoginNew.getAccessId());
						 
					 	if(null != ittbCpLoginNew.getLoginId())
					 		archiveNew.setLoginMethod(ittbCpLoginNew.getLoginMethod());
					 }
					 
				 	archiveNew.setLoginMethod(ittbCpLoginNew.getLoginMethod());
				 	archiveNew.setProductNo(ittbCpLoginNew.getPmtbProduct().getProductNo());
					 
				 	if( null != ittbCpLoginNew.getAmtbContactPerson())
						 archiveNew.setContactPersonNo(ittbCpLoginNew.getAmtbContactPerson().getContactPersonNo());
				 	
				 	
					 if(null != ittbCpLoginNew.getCreatedBy())
						 archiveNew.setCreatedBy(ittbCpLoginNew.getCreatedBy());
					 else
						 archiveNew.setCreatedBy(CommonWindow.getUserLoginIdAndDomain());
					 
					 if(null != ittbCpLoginNew.getCreatedDt())
						 archiveNew.setCreatedDt(ittbCpLoginNew.getCreatedDt());
					 else
						 archiveNew.setCreatedDt(new Timestamp(new Date().getTime()));
					 
					 archiveNew.setUpdatedBy(CommonWindow.getUserLoginIdAndDomain());
					 archiveNew.setUpdatedDt(new Timestamp(new Date().getTime()));
					 this.save(archiveNew);
					 this.delete(ittbCpLoginNew);
				 }
				 else
					 logger.info("ProductNo doesnt exist in Ittb CP Login New.");
			 }
		} catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
		}
	}

	public boolean checkDeleteAssignCard(Listitem item) {
		boolean result = false;
		
		try{
			SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
			Map<String,String> cardAssignMap = (Map<String,String>) item.getValue();
			
			String issuedOn = cardAssignMap.get("issuedOn");
			
			Date dateIssusedOn = formatter.parse(issuedOn);
			if(dateIssusedOn.compareTo(new Date()) <= 0)
				result = false;
			else
				result = true;
		} catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
		}
		
		return result;
	}
	public void deleteAssignCard(Listitem item) throws Exception {
		
		try{
			SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
			Map<String,String> cardAssignMap = (Map<String,String>) item.getValue();
			
			String cardNo = cardAssignMap.get("cardNo");
			String issuedTo = cardAssignMap.get("issuedTo");
			String issuedOn = cardAssignMap.get("issuedOn");
			
			IttbCpCustCardIssuance ittb = new IttbCpCustCardIssuance();
			ittb.setCardNo(cardNo);
			ittb.setIssuedTo(issuedTo);
			
			Date DateIssuedOn = formatter.parse(issuedOn);
			ittb.setIssuedOn(new Timestamp(DateIssuedOn.getTime()));
			
			List<IttbCpCustCardIssuance> ittbCurrCpCustCardList = this.daoHelper.getProductDao().getAssignCard(ittb, DateIssuedOn);
			for(IttbCpCustCardIssuance currIccci : ittbCurrCpCustCardList)
			{
				delete(currIccci);
				break;
			}
			
			//Check Previous Record & Return Date
			List<IttbCpCustCardIssuance> ittbPrevCpCustCardList = this.daoHelper.getProductDao().deletePreviousAssignCard(ittb, DateIssuedOn);
				 
			if(ittbPrevCpCustCardList.size() > 0)
			{
				for(IttbCpCustCardIssuance prevIccci : ittbPrevCpCustCardList)
				{
					 //Check got Any Later Record after Delete Record.
					 List<IttbCpCustCardIssuance> ittbFutureCpCustCardList = this.daoHelper.getProductDao().deleteFutureAssignCard(ittb, DateIssuedOn);
			
					 if(ittbFutureCpCustCardList.size() > 0)
					 {
						 for(IttbCpCustCardIssuance futureIccci : ittbFutureCpCustCardList)
						 {
							 prevIccci.setReturnedOn(futureIccci.getIssuedOn());
							 prevIccci.setUpdatedBy(CommonWindow.getUserLoginIdAndDomain());
							 prevIccci.setUpdatedDt(new Timestamp(new Date().getTime()));
							 this.update(prevIccci);
							 break;
						 }
						 break;
					 }
					 else
					 {
						 prevIccci.setReturnedOn(null);
						 this.update(prevIccci);
						 break;
					 }
				 }
			}
			
		} catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
			throw new Exception(e.getMessage());
		}
	}
	
	public void updateIttbNameOnCard(PmtbProduct product, String oldNameOnProduct) throws Exception
	{
		try{
			IttbCpCustCardIssuance iccci = new IttbCpCustCardIssuance();
			iccci.setCardNo(product.getCardNo());
			iccci.setProductNo(product.getProductNo());

			//find the ittbCardIssuance nearest issue_on to Sync
			IttbCpCustCardIssuance ittbNearestCpCustCard = this.daoHelper.getProductDao().getNearestAssignCard(iccci, new Date());
			
			//update issue_to to Sync;
			if(null != ittbNearestCpCustCard) {
				ittbNearestCpCustCard.setIssuedTo(product.getNameOnProduct());
				ittbNearestCpCustCard.setUpdatedBy(CommonWindow.getUserLoginIdAndDomain());
				ittbNearestCpCustCard.setUpdatedDt(new Timestamp(new Date().getTime()));
				this.update(ittbNearestCpCustCard);
			}
		}catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
			throw new Exception(e.getMessage());
		}
	}
	
	public void renewVirtualEmail(PmtbProduct product, Date issueDate)
	{
		try
		{
			List<PmtbProduct> productList = Lists.newArrayList();
			productList.add(product);
			logger.info("CARD RENEWAL Virtual Card Detected, preparing virtual email");
			createVirtualEmailList(productList, issueDate, "CARD RENEWAL");
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	public void createVirtualEmailList(List<PmtbProduct> productList, Date issueDate, String remarks) throws Exception
	{
		List<PmtbVirtualEmail> virtualEmailList = Lists.newArrayList();;
		SatbUser user = this.daoHelper.getUserDao().getUserWithRoles(CommonWindow.getUserId());
		
		for(PmtbProduct product: productList){
			PmtbVirtualEmail virtualEmail = new PmtbVirtualEmail();
			
			virtualEmail.setEmailStatus("N");
			virtualEmail.setPmtbProduct(product);
			virtualEmail.setActions(remarks);
			virtualEmail.setSatbUser(user);
			virtualEmail.setRequestDate(new Timestamp(System.currentTimeMillis()));
			
			virtualEmailList.add(virtualEmail);
		}
		
		try
		{
			if(!virtualEmailList.isEmpty())
				this.daoHelper.getGenericDao().saveAll(virtualEmailList, CommonWindow.getUserId().toString());
		}
		catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

	//either one will have record
	public boolean getCheckProductIndustry(PmtbProduct product, AmtbAccount acct)
	{
		try
		{
			String industry = "";
			String masterIndustry = "";
			
			masterIndustry = this.daoHelper.getProductDao().getMasterIndustry();
			
			if(masterIndustry == null || masterIndustry.trim().equals(""))
				return false;
			
			AmtbAccount topLvlAcct = new AmtbAccount();
			
			if(acct != null)
				topLvlAcct = acct;
			else if(product != null)
				topLvlAcct = product.getAmtbAccount();
			
			while(topLvlAcct.getCustNo() == null)
				topLvlAcct = topLvlAcct.getAmtbAccount();
			
			industry = this.daoHelper.getProductDao().getIndustry(topLvlAcct);

			if(industry == null || industry.trim().equals(""))
				return false;
				
			if(industry.trim().equalsIgnoreCase("") || masterIndustry.trim().equals(""))
				return false;

			if(industry.equals(masterIndustry))
				return true;
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		return false;
	}
}