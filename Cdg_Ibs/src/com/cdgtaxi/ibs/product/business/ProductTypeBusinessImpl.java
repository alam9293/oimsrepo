package com.cdgtaxi.ibs.product.business;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.cdgtaxi.ibs.common.NonConfigurableConstants;
import com.cdgtaxi.ibs.common.business.GenericBusinessImpl;
import com.cdgtaxi.ibs.common.model.PmtbProductType;
import com.cdgtaxi.ibs.interfaces.as.api.API;


public class ProductTypeBusinessImpl extends GenericBusinessImpl implements ProductTypeBusiness{
	private static final Logger logger = Logger.getLogger(ProductTypeBusinessImpl.class);
	public Map<String, Map<String, String>> getProductTypes(PmtbProductType productType){
		logger.info("getAccountTypes(String template)");
		// checking for duplicates
		Map<String, Map<String, String>> returnMap = new LinkedHashMap<String, Map<String, String>>();
		List<PmtbProductType> productTypes = this.daoHelper.getProductTypeDao().getProductType(productType);
		//List<ProductType> returnAccountTypes = new ArrayList<ProductType>();
		if(productTypes!=null){
			//Set<Integer> check = new HashSet<Integer>();
			for(PmtbProductType checkProductType : productTypes){
				Map<String, String> productTypeMap = new LinkedHashMap<String, String>();
				productTypeMap.put("name", checkProductType.getName());
				productTypeMap.put("numberOfDigit", ""+checkProductType.getNumberOfDigit());
				productTypeMap.put("binRange", checkProductType.getBinRange());
				productTypeMap.put("subBinRange", checkProductType.getSubBinRange());
				productTypeMap.put("batchIssue", checkProductType.getBatchIssue());
				productTypeMap.put("nameOnProduct", checkProductType.getNameOnProduct());
				productTypeMap.put("luhnCheck", checkProductType.getLuhnCheck());
				productTypeMap.put("usageLimitation", checkProductType.getOneTimeUsage());
				productTypeMap.put("fixedValue", checkProductType.getFixedValue());
				productTypeMap.put("creditLimit", checkProductType.getCreditLimit());
				productTypeMap.put("validityPeriod", checkProductType.getValidityPeriod());
				productTypeMap.put("defaultCardStatus", checkProductType.getDefaultCardStatus());
				productTypeMap.put("negativeFileCheck", ""+checkProductType.getExternalCard());
				productTypeMap.put("issuable", checkProductType.getIssuable());
				productTypeMap.put("issueType", checkProductType.getIssueType());
				productTypeMap.put("defaultValidPeriod", ""+checkProductType.getDefaultValidPeriod());
				productTypeMap.put("loginRegistration", ""+checkProductType.getLoginRegistration());
				productTypeMap.put("hotel", ""+checkProductType.getHotel());
				productTypeMap.put("contactless", ""+checkProductType.getContactless());
				productTypeMap.put("cardless", ""+checkProductType.getCardless());
//				productTypeMap.put("virtual", ""+checkProductType.getVirtualProduct());
				returnMap.put(checkProductType.getProductTypeId(), productTypeMap);
			}
			return returnMap;
		}else	
			return null;
	}
	public PmtbProductType getProductType(String productTypeId){
		PmtbProductType productType=new PmtbProductType();
		productType=this.daoHelper.getProductTypeDao().getProductType(productTypeId);
		return productType;
	}
	public Boolean checkGotProduct(String productTypeId){
		return this.daoHelper.getProductTypeDao().checkGotProduct(productTypeId);
	}
	
	public List<PmtbProductType> getProductTypes(List<String> productTypeId){

		return this.daoHelper.getProductTypeDao().getProductTypes(productTypeId);
	}
	
	
	public List<PmtbProductType> getAllProductTypes(){
		logger.info("getAllProductTypes()");
		return this.daoHelper.getProductTypeDao().getAllProductType();
	}
	
	public boolean getCheckBin(String binNumber, String subBin, boolean isExternalCard){
		boolean check=false;
		List<PmtbProductType> productTypes = getAllProductTypes();
		for(PmtbProductType checkProductType : productTypes){
			//If both bin and sub bin are NA, skip check
			if(!(checkProductType.getBinRange().equalsIgnoreCase(NonConfigurableConstants.BOOLEAN_NA) && 
					checkProductType.getSubBinRange().equalsIgnoreCase(NonConfigurableConstants.BOOLEAN_NA))){
				logger.info("COMPARING BIN "+checkProductType.getBinRange()+" SUB BIN "+checkProductType.getSubBinRange());
				if(checkProductType.getBinRange().equalsIgnoreCase(binNumber)){
					if(!subBin.equalsIgnoreCase("")){
						if(checkProductType.getSubBinRange().equalsIgnoreCase(subBin)){	
							check=true;
							logger.info("BIN AND SUB BIN MATCHED!");
							break;
						}
						else if(checkProductType.getSubBinRange().startsWith(subBin)){
							check=true;
							logger.info("BIN AND SUB BIN(NEW SUB BIN PART OF COMPARATOR) MATCHED!");
							break;
						}
						else if(subBin.startsWith(checkProductType.getSubBinRange())){
							check=true;
							logger.info("BIN AND SUB BIN(COMPARATOR PART OF NEW SUB BIN) MATCHED!");
							break;
						}
					}
					else{
						if(isExternalCard){
							if(checkProductType.getSubBinRange().equalsIgnoreCase(NonConfigurableConstants.BOOLEAN_NA) &&
									subBin.equalsIgnoreCase("")){
								logger.info("BIN AND SUB BIN(BOTH NULL) MATCHED!");
								check=true;
								break;
							}
						}
						else{
							logger.info("SUB BIN IS EMPTY WHICH IS ALLOWED ONLY FOR EXTERNAL PRODUCT TYPES!");
							check=true;
							break;
						}
					}
				}
			}
		}
		return check;
	}
	
	public boolean getCheckBin(String binNumber,String subBin,String productTypeId){
		boolean check=false;
		List<PmtbProductType> productTypes = getAllProductTypes();
		for(PmtbProductType checkProductType : productTypes){
			if(checkProductType.getBinRange()!=null && checkProductType.getSubBinRange()!=null){
				if(checkProductType.getBinRange().equalsIgnoreCase(binNumber) &&
						checkProductType.getSubBinRange().equalsIgnoreCase(subBin) &&
						  (!checkProductType.getProductTypeId().equalsIgnoreCase(productTypeId)))	
							check=true;
			}
			
		}
		return check;
		
	}
	
//	public void createProductTypeAPI(PmtbProductType productType, String userId) {
//		//PmtbProduct productAndAccount= (PmtbProduct) this.daoHelper.getProductDao().getProductAndAccount(product);
//		try{
//			//expiryDateFlag-> Validity period
//		API.createProductType(
//				productType.getProductTypeId(),
//				productType.getName(),
//				productType.getBinRange(),
//				productType.getSubBinRange(),
//				productType.getOneTimeUsage(), 
//				productType.getFixedValue(),
//				productType.getCreditLimit(),
//				productType.getExternalCard(),
//				productType.getValidityPeriod(),
//				productType.getDefaultCardStatus(),
//				userId);
//	
//		}catch(Exception e){e.printStackTrace();}
//		
//	}
	
	public List<PmtbProductType> getAllProductType(){
		return this.daoHelper.getProductTypeDao().getAllProductType();
	}
	
	public List<PmtbProductType> getPrepaidProductType(){
		return this.daoHelper.getProductTypeDao().getPrepaidProductType();
	}
	
	/*
	public void saveAccountTypes(List<AccountType> accountTypes){
		logger.info("saveAccountTypes(List<AccountType> accountTypes)");
		for(AccountType accountType : accountTypes){
			this.daoHelper.getAccountTypeDao().update(accountType);
		}
	}
	public boolean createAccountType(String accountTemplate, String accountType){
		logger.info("createAccountType(String accountTemplate, String accountType)");
		AccountType newAcctType = new AccountType();
		newAcctType.setAccountTemplate(accountTemplate);
		newAcctType.setAccountType(accountType);
		return this.daoHelper.getAccountTypeDao().save(newAcctType)!=null ? true : false;
	}*/
	
	public Serializable save(PmtbProductType newProductType, String userId) throws Exception{
		
		String expiryDateFlag = NonConfigurableConstants.BOOLEAN_YES;
		if(newProductType.getIssuable().equals(NonConfigurableConstants.BOOLEAN_NO))
			expiryDateFlag = NonConfigurableConstants.BOOLEAN_NO;
		else{
			if(newProductType.getValidityPeriod()!=NonConfigurableConstants.VALIDITY_PERIOD_FLAG_NO)
				expiryDateFlag = NonConfigurableConstants.BOOLEAN_YES;
			else
				expiryDateFlag = NonConfigurableConstants.BOOLEAN_NO;
		}
		
		API.createProductType(newProductType.getProductTypeId(), newProductType.getName(), 
				newProductType.getBinRange(), newProductType.getSubBinRange(), newProductType.getOneTimeUsage(), 
				newProductType.getFixedValue(), 
				newProductType.getCreditLimit(), 
				newProductType.getExternalCard(), 
				//Because AS only understands Y/N, therefore any new flags has to be casted as 'Y' as long as it is not 'N'
				expiryDateFlag, 
				newProductType.getPrepaid(),
				NonConfigurableConstants.STATUS_ACTIVE, 
				userId, 
				newProductType.getContactless());
		
		return this.daoHelper.getGenericDao().save(newProductType);
	}
	
	public List<PmtbProductType> getExternalProductType(){
		return this.daoHelper.getProductTypeDao().getExternalProductType();
	}
	
	public PmtbProductType getExternalProductType(String binRange, String subBinRange){
		return this.daoHelper.getProductTypeDao().getExternalProductType(binRange, subBinRange);
	}
	public List<PmtbProductType> getCardlessProductType(){
		return this.daoHelper.getProductTypeDao().getAllCardlessProductType();
	}
	
}