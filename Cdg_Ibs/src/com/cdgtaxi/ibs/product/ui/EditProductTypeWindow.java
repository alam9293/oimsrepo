package com.cdgtaxi.ibs.product.ui;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.log4j.Logger;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zul.Decimalbox;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;

import com.cdgtaxi.ibs.Uri;
import com.cdgtaxi.ibs.common.ConfigurableConstants;
import com.cdgtaxi.ibs.common.NonConfigurableConstants;
import com.cdgtaxi.ibs.common.model.PmtbProductType;
import com.cdgtaxi.ibs.common.ui.CommonWindow;
import com.cdgtaxi.ibs.master.model.MstbMasterTable;
import com.cdgtaxi.ibs.util.ComponentUtil;
import com.cdgtaxi.ibs.web.component.CapsTextbox;

public class EditProductTypeWindow  extends CommonWindow {
			
	private static final long serialVersionUID = -6136282678314618446L;
	private static Logger logger = Logger.getLogger(IssueProductWindow.class);
	private PmtbProductType productType=new PmtbProductType();
	
	public EditProductTypeWindow (){
			
		Map params = Executions.getCurrent().getArg();
		String productTypeId = (String)params.get("productTypeId");
		productType = (PmtbProductType)this.businessHelper.getProductTypeBusiness().getProductType(productTypeId);
		logger.info("Search PrductType by ID  :"+productTypeId);
		
//		logger.info("productName= "+ productType.getName());
//		logger.info("No of digit= "+ productType.getNumberOfDigit());
//		logger.info("BIN range= "+ productType.getBinRange());
//		logger.info("Sub BIN range= "+ productType.getSubBinRange());
//		logger.info("Batch Issue= "+ productType.getBatchIssue());
//		logger.info("Name on Product= "+ productType.getNameOnProduct());
//		logger.info("Luhn_check="+productType.getLuhnCheck());
//		logger.info("Usage_limitation="+productType.getOneTimeUsage());
//		logger.info("Fixed_value="+productType.getFixedValue());
//		logger.info("Credit_limit="+productType.getCreditLimit());
//		logger.info("Validity_period="+productType.getValidityPeriod());
//		logger.info("Default_card_status="+productType.getDefaultCardStatus());
//		logger.info("Negative File Check= "+ productType.getNegativeFileCheck());
//		logger.info("Issueable= "+ productType.getIssuable());
//		logger.info("Issue Type= "+ productType.getIssueType());
//		logger.info("Default Valid Period (Expiry months)= "+ productType.getDefaultValidPeriod());
//				
		if(productType==null)
			throw new NullPointerException("UserId["+productType+"] not found!"); //This should not happen at all

	}
			
	public PmtbProductType getProductType(){
		return productType;
	}
		
	public List<Listitem> getCardStatus(){
		
		logger.info("getCardStatus()");
		ArrayList<Listitem> cardStatus = new ArrayList<Listitem>();
		//This one is to show (-) dash in the list
		//cardStatus.add((Listitem) ComponentUtil.createNotRequiredListItem());
		for(String key : NonConfigurableConstants.PRODUCT_STATUS.keySet()){
			if(!key.equalsIgnoreCase(NonConfigurableConstants.PRODUCT_STATUS_NEW)){
				if(!key.equalsIgnoreCase(NonConfigurableConstants.PRODUCT_STATUS_PARENT_SUSPENDED))
					if(!key.equalsIgnoreCase(NonConfigurableConstants.PRODUCT_STATUS_USED))
						if(!key.equalsIgnoreCase(NonConfigurableConstants.PRODUCT_STATUS_TERMINATED))
							cardStatus.add(new Listitem(NonConfigurableConstants.PRODUCT_STATUS.get(key), key));
			}
		}
		return cardStatus;
	}
		
	public void batchIssueCheck(){
	
		Listbox batch_issueList = (Listbox)this.getFellow("batch_issue");
		if(batch_issueList.getSelectedItem()!=null){
			String batchIssue= (String)batch_issueList.getSelectedItem().getValue();
			if(batchIssue.equalsIgnoreCase("Y"))
				((Listbox)this.getFellow("issue_type")).setDisabled(false);
			else ((Listbox)this.getFellow("issue_type")).setDisabled(true);
		}
	}
					
	public void issuableCheck(){
		
		Listbox issuableList = (Listbox)this.getFellow("issuable");
		String issuable= (String)issuableList.getSelectedItem().getValue();
		logger.info("Issuable = "+issuable);
		if(issuable.equalsIgnoreCase("N")){
			((Listbox)this.getFellow("issue_type")).setDisabled(true);
			//((Intbox)this.getFellow("no_of_digits")).setValue(null);
			//((Intbox)this.getFellow("no_of_digits")).setDisabled(true);
			//((Textbox)this.getFellow("bin")).setValue(null);
			//((Textbox)this.getFellow("bin")).setDisabled(true);
			//((Textbox)this.getFellow("sub_bin")).setValue(null);
			//((Textbox)this.getFellow("sub_bin")).setDisabled(true);
			((Listbox)this.getFellow("batch_issue")).setDisabled(true);
			((Listbox)this.getFellow("name_on_card")).setDisabled(true);
			
			//((Listbox)this.getFellow("luhn_check")).setDisabled(true);
			//((Listbox)this.getFellow("usage_limitation")).setDisabled(true);
			//((Listbox)this.getFellow("fixed_value")).setDisabled(true);
			//((Listbox)this.getFellow("credit_limit")).setDisabled(true);
			//((Listbox)this.getFellow("validity_period")).setDisabled(true);
			((Listbox)this.getFellow("card_status")).setDisabled(true);
			//((Listbox)this.getFellow("negative_file_check")).setDisabled(true);
			//((Listbox)this.getFellow("issuable")).setDisabled(true);
			((Listbox)this.getFellow("issue_type")).setDisabled(true);
			((Intbox)this.getFellow("default_expiry")).setValue(0);
			((Intbox)this.getFellow("default_expiry")).setDisabled(true);
			((Decimalbox)this.getFellow("replacementFees")).setDisabled(true);
			((Decimalbox)this.getFellow("replacementFees")).setValue(new BigDecimal("0"));
			//batchIssueCheck();
		}
		else{
			((Listbox)this.getFellow("issue_type")).setDisabled(false);
			//((Intbox)this.getFellow("no_of_digits")).setDisabled(false);
			//((Textbox)this.getFellow("bin")).setDisabled(false);
			//((Textbox)this.getFellow("sub_bin")).setDisabled(false);
			((Listbox)this.getFellow("batch_issue")).setDisabled(false);
			((Listbox)this.getFellow("name_on_card")).setDisabled(false);
			//((Listbox)this.getFellow("luhn_check")).setDisabled(false);
			//((Listbox)this.getFellow("usage_limitation")).setDisabled(false);
			//((Listbox)this.getFellow("fixed_value")).setDisabled(false);
			//((Listbox)this.getFellow("credit_limit")).setDisabled(false);
			//((Listbox)this.getFellow("validity_period")).setDisabled(false);
			((Listbox)this.getFellow("card_status")).setDisabled(false);
			//((Listbox)this.getFellow("negative_file_check")).setDisabled(true);
			//((Listbox)this.getFellow("issuable")).setDisabled(true);
			((Listbox)this.getFellow("issue_type")).setDisabled(false);
			((Intbox)this.getFellow("default_expiry")).setDisabled(false);
			((Decimalbox)this.getFellow("replacementFees")).setDisabled(false);
			batchIssueCheck();
			ValidityPeriodCheck();
		}
	}
	public void cardlessCheck(){
		
		Listbox cardlessList = (Listbox)this.getFellow("cardlessListBox");
		String cardless= (String)cardlessList.getSelectedItem().getValue();
		logger.info("Cardless = "+cardless);
		if(cardless.equalsIgnoreCase("N")){
			((Listbox)this.getFellow("smsTopUpFormatListBox")).setDisabled(false);
			((Decimalbox)this.getFellow("transferFeeField")).setDisabled(false);
			((Decimalbox)this.getFellow("topupFeeField")).setDisabled(false);
			
			((Listbox)this.getFellow("hotelListBox")).setDisabled(false);
			((Listbox)this.getFellow("issuable")).setDisabled(false);
			((Listbox)this.getFellow("batch_issue")).setDisabled(false);
			((Listbox)this.getFellow("luhn_check")).setDisabled(false);
			((Listbox)this.getFellow("negative_file_check")).setDisabled(false);
			((Listbox)this.getFellow("prepaidListBox")).setDisabled(false);
			((Listbox)this.getFellow("contactlessListBox")).setDisabled(false);
			((Intbox)this.getFellow("no_of_digits")).setDisabled(false);
			((Intbox)this.getFellow("defaultBalanceExpMonthsField")).setDisabled(false);
			((Textbox)this.getFellow("bin")).setDisabled(false);
			((Textbox)this.getFellow("sub_bin")).setDisabled(false);
			
			issuableCheck();
		}
		else{
			//check got product.
			try{

					((Listbox)this.getFellow("smsTopUpFormatListBox")).setDisabled(true);
					((Decimalbox)this.getFellow("transferFeeField")).setDisabled(true);
					((Decimalbox)this.getFellow("topupFeeField")).setDisabled(true);
					
					((Decimalbox)this.getFellow("transferFeeField")).setValue(new BigDecimal("0"));
					((Decimalbox)this.getFellow("topupFeeField")).setValue(new BigDecimal("0"));
					
					Listbox issuableList = (Listbox)this.getFellow("issuable");
					for(Object itemOfList :  issuableList .getItems()){
						if(((Listitem)itemOfList).getValue().equals("N")){
							((Listitem)itemOfList).setSelected(true);
							break;
						}
					}
					((Listbox)this.getFellow("issuable")).setDisabled(true);
					((Listbox)this.getFellow("batch_issue")).setDisabled(true);

					((Listbox)this.getFellow("luhn_check")).setDisabled(true);
					((Listbox)this.getFellow("negative_file_check")).setDisabled(true);
					((Listbox)this.getFellow("prepaidListBox")).setDisabled(true);
					((Listbox)this.getFellow("contactlessListBox")).setDisabled(true);
					((Listbox)this.getFellow("smsExpiryFormatListBox")).setDisabled(true);
					((Listbox)this.getFellow("smsFormatListBox")).setDisabled(true);
					((Intbox)this.getFellow("no_of_digits")).setDisabled(true);
					((Intbox)this.getFellow("defaultBalanceExpMonthsField")).setDisabled(true);
					((Textbox)this.getFellow("bin")).setDisabled(true);
					((Textbox)this.getFellow("sub_bin")).setDisabled(true);

					((Listbox)this.getFellow("hotelListBox")).setDisabled(true);
					Listbox hotelList = (Listbox)this.getFellow("hotelListBox");
					for(Object itemOfList :  hotelList .getItems()){
						if(((Listitem)itemOfList).getValue().equals("N")){
							((Listitem)itemOfList).setSelected(true);
							break;
						}
					}
					
					((Intbox)this.getFellow("no_of_digits")).setValue(null);
					((Decimalbox)this.getFellow("transferFeeField")).setValue(new BigDecimal("0"));
					((Decimalbox)this.getFellow("topupFeeField")).setValue(new BigDecimal("0"));
						
					issuableCheck();
			}
			catch(Exception e){
			}
			finally{}
			
		}
	}
	public List<Listitem> getSMSFormatList(){
		logger.info("getSMSFormatList()");
		
		ArrayList<Listitem> smsFormatList = new ArrayList<Listitem>();
		smsFormatList.add((Listitem) ComponentUtil.createNotRequiredListItem());
		Map<String, String> smsFormatMasters = ConfigurableConstants.getSMSFormat();
		for(Entry<String, String> entry : smsFormatMasters.entrySet()){
			smsFormatList.add(new Listitem(entry.getKey(), entry.getKey()));
		}
		return smsFormatList;
	}
	
	public List<Listitem> getSMSExpiryFormatList(){
		logger.info("getSMSExpiryFormatList()");
		
		ArrayList<Listitem> smsFormatList = new ArrayList<Listitem>();
		smsFormatList.add((Listitem) ComponentUtil.createNotRequiredListItem());
		Map<String, String> smsFormatMasters = ConfigurableConstants.getSMSExpiryFormat();
		for(Entry<String, String> entry : smsFormatMasters.entrySet()){
			smsFormatList.add(new Listitem(entry.getKey(), entry.getKey()));
		}
		return smsFormatList;
	}
	
	public List<Listitem> getSMSTopUpFormatList(){
		logger.info("getSMSTopUpFormatList()");
		
		ArrayList<Listitem> smsFormatList = new ArrayList<Listitem>();
		smsFormatList.add((Listitem) ComponentUtil.createNotRequiredListItem());
		Map<String, String> smsFormatMasters = ConfigurableConstants.getSMSTopUpFormat();
		for(Entry<String, String> entry : smsFormatMasters.entrySet()){
			smsFormatList.add(new Listitem(entry.getKey(), entry.getKey()));
		}
		return smsFormatList;
	}
	
	public List<Listitem> getYesNoBlankStatus(){
		
		logger.info("getCardStatus()");
		ArrayList<Listitem> cardStatus = new ArrayList<Listitem>();
		//This one is to show (-) dash in the list
		//cardStatus.add((Listitem) ComponentUtil.createNotRequiredListItem());
		for(String key : NonConfigurableConstants.BOOLEAN_YN.keySet()){
			cardStatus.add(new Listitem(NonConfigurableConstants.BOOLEAN_YN.get(key), key));
		}
		return cardStatus;
	}
			
	public List<Listitem> getYesNoNABlankStatus(){
	
		logger.info("getCardStatus()");
		ArrayList<Listitem> cardStatus = new ArrayList<Listitem>();
		//This one is to show (-) dash in the list
		//cardStatus.add((Listitem) ComponentUtil.createNotRequiredListItem());
		for(String key : NonConfigurableConstants.BOOLEAN.keySet()){
			cardStatus.add(new Listitem(NonConfigurableConstants.BOOLEAN.get(key), key));
		}
		return cardStatus;
	}
		
	public List<Listitem> getIssueTypeList(){
		
		logger.info("getIssueTypeList()");
		ArrayList<Listitem> issueType = new ArrayList<Listitem>();
		//This one is to show (-) dash in the list
		//	issueType.add((Listitem) ComponentUtil.createNotRequiredListItem());
		for(String key : NonConfigurableConstants.ISSUE_TYPE.keySet()){
			issueType.add(new Listitem(NonConfigurableConstants.ISSUE_TYPE.get(key), key));
		}
		return issueType;
	}
		
	public void ValidityPeriodCheck(){
		Listbox validityPeriodList = (Listbox)this.getFellow("validity_period");
		String validityPeriod= (String)validityPeriodList.getSelectedItem().getValue();
		logger.info("Issuable = "+validityPeriod);
		if(!validityPeriod.equalsIgnoreCase(NonConfigurableConstants.BOOLEAN_NO)){
			((Intbox)this.getFellow("default_expiry")).setDisabled(false);
		}
		else{
			((Intbox)this.getFellow("default_expiry")).setValue(0);
			((Intbox)this.getFellow("default_expiry")).setDisabled(true);
		}
			
		
	}
	public void saveProductTypes() throws InterruptedException {
				
		String errorMessage="";
		boolean check_flag=true;
		boolean checkProductName=false;
		
		String productName= ((Textbox)this.getFellow("name")).getValue();
		if(productName==null){
			check_flag=false;
			errorMessage="Product Name cannot be blank";
			throw new WrongValueException(this.getFellow("name"), errorMessage);
			//Messagebox.show(errorMessage, "Edit Product Type", Messagebox.OK, Messagebox.INFORMATION);
		}
		else{
			if(productName.trim().length()<1){
				check_flag=false;
				errorMessage="Product Name cannot be blank";
				throw new WrongValueException(this.getFellow("name"), errorMessage);
				//Messagebox.show(errorMessage, "Edit Product Type", Messagebox.OK, Messagebox.INFORMATION);
			}
			else{
				//to check the whether Type Name is already used or not
				logger.info("Check");
				logger.info("productTypeName"+productName);
				logger.info("getProductTypeId"+productType.getProductTypeId());
				checkProductName=this.businessHelper.getProductBusiness().isProductNameInUse(productName,productType.getProductTypeId());
				if(checkProductName){
					check_flag=false;
					errorMessage="Product Type Name had been used";
					throw new WrongValueException(this.getFellow("name"), errorMessage);
				}
			}
		}
		//
		String numberOfDigit= ((Intbox)this.getFellow("no_of_digits")).getText();
		String binNumber= ((Textbox)this.getFellow("bin")).getText();
		String subBin= ((Textbox)this.getFellow("sub_bin")).getText();
		Listbox issuableList = (Listbox)this.getFellow("issuable");
		String issuable=(String)issuableList.getSelectedItem().getValue();
		
		Listbox batch_issueList = (Listbox)this.getFellow("batch_issue");
		String batchIssue="";
		if(batch_issueList.getSelectedItem()!=null)
			batchIssue=(String)batch_issueList.getSelectedItem().getValue();
		else
			batchIssue="NA";
		Listbox name_on_cardList = (Listbox)this.getFellow("name_on_card");
		String nameOnCard="";//nameOnCard
		if(name_on_cardList.getSelectedItem()!=null)
			nameOnCard=(String)name_on_cardList.getSelectedItem().getValue();
		else
			nameOnCard="NA";
		Listbox luhn_checkList = (Listbox)this.getFellow("luhn_check");
		String luhnCheck=(String)luhn_checkList.getSelectedItem().getValue();
		Listbox usage_limitationList = (Listbox)this.getFellow("usage_limitation");
		String usageLimitation= (String)usage_limitationList.getSelectedItem().getValue();
		Listbox fixed_valueList = (Listbox)this.getFellow("fixed_value");
		String fixedValue= (String)fixed_valueList.getSelectedItem().getValue();
		Listbox credit_limitList = (Listbox)this.getFellow("credit_limit");
		String creditLimit= (String)credit_limitList.getSelectedItem().getValue();
		Listbox validity_periodList = (Listbox)this.getFellow("validity_period");
		String validityPeriod=(String)validity_periodList.getSelectedItem().getValue();
		Listbox cardStatusList = (Listbox)this.getFellow("card_status");
		String defaultCardStatus=(String)cardStatusList.getSelectedItem().getValue();
		Listbox negative_file_checkList = (Listbox)this.getFellow("negative_file_check");
		String negativeFileCheck=(String)negative_file_checkList.getSelectedItem().getValue();
		//extra_field
		String interfaceMappingValue= (String)((CapsTextbox)this.getFellow("interface_mapping_value")).getValue();
		Listbox issue_typeList = (Listbox)this.getFellow("issue_type");
		String issueType=(String)issue_typeList.getSelectedItem().getValue();
		String defaultExpiry= ((Intbox)this.getFellow("default_expiry")).getText();
		BigDecimal replacementFees=((Decimalbox)this.getFellow("replacementFees")).getValue();
		
		//fields for customer portal
		String loginRegistrationFlag = (String)((Listbox)this.getFellow("loginRegistrationListBox")).getSelectedItem().getValue();
		String hotelFlag = (String)((Listbox)this.getFellow("hotelListBox")).getSelectedItem().getValue();
		
		//Prepaid
		String prepaidFlag = (String)((Listbox)this.getFellow("prepaidListBox")).getSelectedItem().getValue();
		
		//Contactless
		String contactlessFlag = (String)((Listbox)this.getFellow("contactlessListBox")).getSelectedItem().getValue();
		
		//Cardless
		String cardlessFlag = (String)((Listbox)this.getFellow("cardlessListBox")).getSelectedItem().getValue();
		
		//Virtual
		String virtualFlag = (String)((Listbox)this.getFellow("virtualListBox")).getSelectedItem().getValue();
		
		//SMS Format
		String smsFormat = (String)((Listbox)this.getFellow("smsFormatListBox")).getSelectedItem().getValue();
		
		//SMS Format
		String smsExpiryFormat = (String)((Listbox)this.getFellow("smsExpiryFormatListBox")).getSelectedItem().getValue();
				
		//SMS Format
		String smsTopUpFormat = (String)((Listbox)this.getFellow("smsTopUpFormatListBox")).getSelectedItem().getValue();
				
		
		Intbox defaultBalanceExpMonthsField = (Intbox)this.getFellow("defaultBalanceExpMonthsField");
		Decimalbox topupFeeField = (Decimalbox)this.getFellow("topupFeeField");
		Decimalbox transferFeeField = (Decimalbox)this.getFellow("transferFeeField");
	
		Integer defaultBalanceExpMonths = defaultBalanceExpMonthsField.getValue();
		BigDecimal topupFee= topupFeeField.getValue();
		BigDecimal transferFee= transferFeeField.getValue();
		
		//start code
		if(cardlessFlag.equalsIgnoreCase("Y"))
		{
			//Disabled
			if(batch_issueList.isDisabled())
				batchIssue = "N";
			if(issuableList.isDisabled())
				issuable = "N";
			if(((Listbox)this.getFellow("smsTopUpFormatListBox")).isDisabled())
				smsTopUpFormat = "N";
			if(luhn_checkList.isDisabled())
				luhnCheck = "N";
			if(negative_file_checkList.isDisabled())
				negativeFileCheck = "N";
			if(((Listbox)this.getFellow("prepaidListBox")).isDisabled())
				prepaidFlag = "N";
			if(((Listbox)this.getFellow("contactlessListBox")).isDisabled())
				contactlessFlag = "N";
			if(((Listbox)this.getFellow("smsTopUpFormatListBox")).isDisabled())
				smsTopUpFormat = "N";
			
			usageLimitation = "NA";
			fixedValue = "NA";
			creditLimit = "NA";
			validityPeriod = "N";
			defaultCardStatus = "NA";
			issueType = "NA";
			defaultExpiry = "0";
			replacementFees = new BigDecimal("0.00");			
			defaultBalanceExpMonths = new Integer("3");
			transferFee = new BigDecimal("0");
			topupFee = new BigDecimal("0");
			binNumber = "NA";
			subBin = "NA";
		}
		
		if(issuable.equalsIgnoreCase("Y")){
			if(check_flag){if(check_flag){
				if( numberOfDigit==null){
						check_flag=false;
						errorMessage="No of Digit cannot be blank";
						throw new WrongValueException(this.getFellow("no_of_digits"), errorMessage);
						// Messagebox.show(errorMessage, "Create Product Type", Messagebox.OK, Messagebox.INFORMATION);
					}
					else{
						if( numberOfDigit.trim().length()<1){
							check_flag=false;
							errorMessage="No of Digit cannot be blank";
							throw new WrongValueException(this.getFellow("no_of_digits"), errorMessage);
							//	 Messagebox.show(errorMessage, "Create Product Type", Messagebox.OK, Messagebox.INFORMATION);
						}
						else if(Integer.parseInt(numberOfDigit)>19){
							check_flag=false;
							errorMessage="No of Digit must be less than or equal 19";
							throw new WrongValueException(this.getFellow("no_of_digits"), errorMessage);
							// Messagebox.show(errorMessage, "Create Product Type", Messagebox.OK, Messagebox.INFORMATION);
						}
//						else if(Integer.parseInt(numberOfDigit)>13){
//								 check_flag=false;
//								 errorMessage="Number of Digit should be greater than 13 for the Product Type with BIN range.";
//								 throw new WrongValueException(this.getFellow("no_of_digits"), errorMessage);
//						}
						else if(Integer.parseInt(numberOfDigit)>=10 && Integer.parseInt(numberOfDigit)<13 && luhnCheck.equalsIgnoreCase(NonConfigurableConstants.BOOLEAN_YES)){
							 check_flag=false;
							 errorMessage="Card type with Number of Digit less than 13 should not have luhn check.";
							 Messagebox.show(errorMessage, "Edit Product Type", Messagebox.OK, Messagebox.INFORMATION);
							 //throw new WrongValueException(this.getFellow("no_of_digits"), errorMessage);
						}
						else if(Integer.parseInt(numberOfDigit)<10){
							 check_flag=false;
							 errorMessage="Number of Digit should be greater than or equal to 10.";
							 throw new WrongValueException(this.getFellow("no_of_digits"), errorMessage);
						}
					}
				}
			}
			
//			if(check_flag){
//				
//				if(validityPeriod.equalsIgnoreCase(NonConfigurableConstants.BOOLEAN_YES)){
//					if(((Intbox)this.getFellow("default_expiry")).getValue()<1)
//						check_flag=false;
//						errorMessage="Default Expiry (Months) should be greater than 0.";
//						Messagebox.show(errorMessage, "Edit Product Type", Messagebox.OK, Messagebox.INFORMATION);
//				}
//				
//			}
			if(check_flag){
				if( binNumber==null || binNumber.trim().length()<1){
					binNumber="";
					if(Integer.parseInt(numberOfDigit)>12){
							
							check_flag=false;
							errorMessage="Product Type with Number of Digit greater than 12 should have BIN range.";
							 Messagebox.show(errorMessage, "Edit Product Type", Messagebox.OK, Messagebox.INFORMATION);
						}
				}
				else{
					if(!binNumber.equalsIgnoreCase("")){
						if( binNumber.trim().length()>0 && binNumber.trim().length()<6 && Integer.parseInt(numberOfDigit)>=13){
							check_flag=false;
							errorMessage="BIN Number length should be 0 or 6";
							throw new WrongValueException(this.getFellow("bin"), errorMessage);
						}
						else if(Integer.parseInt(numberOfDigit)<13){
							check_flag=false;
							 Messagebox.show(errorMessage, "Edit Product Type", Messagebox.OK, Messagebox.INFORMATION);
							throw new WrongValueException(this.getFellow("bin"), errorMessage);
						}
						
						else{
							BigInteger BinCheck=null;
							try{
								BinCheck=new BigInteger(binNumber);
								BinCheck=null;
							}catch(NumberFormatException nfe){
								BinCheck=null;
								check_flag=false;
								errorMessage="Bin Number allows number only";
								throw new WrongValueException(this.getFellow("bin"), errorMessage);
							}
						}
					}		
				}
				
			}	
			if(check_flag){
				if( subBin==null || subBin.trim().length()<1){
						subBin="";
						//check_flag=false;
						//errorMessage="Sub Bin Number cannot be blank";
				}
				else if(Integer.parseInt(numberOfDigit)<13){
					check_flag=false;
					errorMessage="Number of Digit should be greater than 12 for the Product Type with Sub BIN range.";
					 Messagebox.show(errorMessage, "Edit Product Type", Messagebox.OK, Messagebox.INFORMATION);
				}
				else if(subBin!=null && subBin.trim().length()>0){
					BigInteger subBinCheck=null;
					try{
						subBinCheck=new BigInteger(subBin);
						subBinCheck=null;
					}
					catch(NumberFormatException nfe){
						subBinCheck=null;
						check_flag=false;
						errorMessage="Sub Bin Number allows number only";
						throw new WrongValueException(this.getFellow("sub_bin"), errorMessage);
					}
				}
			}
			
			if(check_flag){
				if(binNumber.equalsIgnoreCase("")&& !subBin.equalsIgnoreCase("")){
					check_flag=false;
					errorMessage="Sub Bin Number without BIN number.";
					throw new WrongValueException(this.getFellow("sub_bin"), errorMessage);
				}
			}
			
		}
	
//		if(replacementFees==null)
//			replacementFees=new BigDecimal("0");
		if(replacementFees==null){
			//			replacementFees=new BigDecimal("0");
			check_flag=false;
			errorMessage="Replacemnt field is a mandatory field";
			throw new WrongValueException(this.getFellow("replacementFees"), errorMessage);
		}
		if(check_flag){
			if( defaultExpiry==null){
				check_flag=false;
				errorMessage="Default Expiry Months cannot be blank";
				throw new WrongValueException(this.getFellow("default_expiry"), errorMessage);
				//Messagebox.show(errorMessage, "Edit Product Type", Messagebox.OK, Messagebox.INFORMATION);
			}
			else{
				if( defaultExpiry.trim().length()<1){
					check_flag=false;
					errorMessage="Default Expiry Months cannot be blank";
					throw new WrongValueException(this.getFellow("default_expiry"), errorMessage);
					//	Messagebox.show(errorMessage, "Edit Product Type", Messagebox.OK, Messagebox.INFORMATION);
				}
			}
		}
				
		//				logger.info("Search PrductType");
		//				logger.info("productName= "+ productType.getName());
		//				logger.info("No of digit= "+ productType.getNumberOfDigit());
		//				logger.info("BIN range= "+ productType.getBinRange());
		//				logger.info("Sub BIN range= "+ productType.getSubBinRange());
		//				logger.info("Batch Issue= "+ productType.getBatchIssue());
		//				logger.info("Name on Product= "+ productType.getNameOnProduct());
		//				logger.info("Luhn_check="+productType.getLuhnCheck());
		//				logger.info("Usage_limitation="+productType.getOneTimeUsage());
		//				logger.info("Fixed_value="+productType.getFixedValue());
		//				logger.info("Credit_limit="+productType.getCreditLimit());
		//				logger.info("Validity_period="+productType.getValidityPeriod());
		//				logger.info("Default_card_status="+productType.getDefaultCardStatus());
		//				logger.info("Negative File Check= "+ productType.getNegativeFileCheck());
		//				logger.info("Issueable= "+ productType.getIssuable());
		//				logger.info("Issue Type= "+ productType.getIssueType());
		//				logger.info("Default Valid Period (Expiry months)= "+ productType.getDefaultValidPeriod());
		//				
					//boolean check=false;
		
		if(check_flag){
			boolean checkBin=false;
			logger.info("<<<<Product Type Id"+ productType.getProductTypeId());
			
			if(!binNumber.equalsIgnoreCase("") && !binNumber.equals("NA") && !cardlessFlag.equalsIgnoreCase("Y"))
			{
				checkBin=this.businessHelper.getProductTypeBusiness().getCheckBin(binNumber,subBin,productType.getProductTypeId());
				
				if(checkBin){
					check_flag=false;
					logger.info("This is the BIN check .. already have one ...");
					errorMessage="This BIN and Sub BIN have been used by another card type.";
					Messagebox.show(errorMessage, "Edit Product Type", Messagebox.OK, Messagebox.INFORMATION);
					}
				}
			}
		
		if(check_flag){
			
			if(NonConfigurableConstants.getBoolean(prepaidFlag)){
				if(!NonConfigurableConstants.getBoolean(creditLimit)){
					throw new WrongValueException("Credit Limit of product type cannot be NO if it is prepaid");
				}
			}
			
			
			productType.setName(productName.toUpperCase());
			productType.setDefaultValidPeriod(0);//Set 0 as default;
			productType.setVirtualProduct(virtualFlag);

			//Bin Range
			if(binNumber.trim().equals("")) productType.setBinRange("NA");
			else productType.setBinRange(binNumber);
			//Sub Bin Range
			if(subBin.trim().equals("")) productType.setSubBinRange("NA");
			else productType.setSubBinRange(subBin);
			
			if(issuable.equalsIgnoreCase("Y")){
				
				if(numberOfDigit.trim().equals("")) productType.setNumberOfDigit(0);
				else productType.setNumberOfDigit(Integer.parseInt(numberOfDigit));
				productType.setBatchIssue(batchIssue);
				productType.setNameOnProduct(nameOnCard);
				productType.setLuhnCheck(luhnCheck);
				productType.setOneTimeUsage(usageLimitation);
				productType.setCreditLimit(creditLimit);
				productType.setFixedValue(fixedValue);
				productType.setValidityPeriod(validityPeriod);
				productType.setDefaultCardStatus(defaultCardStatus);
				productType.setExternalCard(negativeFileCheck);
				productType.setIssuable(issuable);
				//add extra field
				productType.setInterfaceMappingValue(interfaceMappingValue);
				if(batchIssue.equalsIgnoreCase("N")) productType.setIssueType("NA");
				else productType.setIssueType(issueType);
				productType.setReplacementFees(replacementFees);
				
				if(!validityPeriod.equalsIgnoreCase(NonConfigurableConstants.BOOLEAN_NO)){
					if(numberOfDigit.trim().equals("")) productType.setDefaultValidPeriod(0);
					else productType.setDefaultValidPeriod(Integer.parseInt(defaultExpiry));
				}
				else {
					//Changed by Seng Tat to help Henry as he changed create product type but not here
					//separate the null and 0 in default validity period.
					productType.setDefaultValidPeriod(-1);
				}
			}
			else{
				productType.setBatchIssue("NA");
				productType.setNameOnProduct("NA");
				productType.setLuhnCheck("NA");
				productType.setOneTimeUsage("NA");
				productType.setCreditLimit("NA");
				productType.setFixedValue("NA");
				productType.setValidityPeriod("NA");
				productType.setDefaultCardStatus("NA");
				productType.setExternalCard(negativeFileCheck);
				productType.setIssuable(issuable);
				productType.setIssueType("NA");
				productType.setReplacementFees(new BigDecimal("0"));
				productType.setInterfaceMappingValue(interfaceMappingValue);
				if(numberOfDigit.trim().equals("")) productType.setNumberOfDigit(0);
				else productType.setNumberOfDigit(Integer.parseInt(numberOfDigit));
			}
			
			productType.setLoginRegistration(loginRegistrationFlag);
			productType.setHotel(hotelFlag);
			productType.setPrepaid(prepaidFlag);
			productType.setContactless(contactlessFlag);
			productType.setCardless(cardlessFlag);
			productType.setMstbMasterTableBySMSFormat(ConfigurableConstants.getMasterTable(ConfigurableConstants.TXSMS_MASTER_TYPE, smsFormat));
			productType.setMstbMasterTableBySMSExpiryFormat(ConfigurableConstants.getMasterTable(ConfigurableConstants.EXSMS_MASTER_TYPE, smsExpiryFormat));
			productType.setMstbMasterTableBySMSTopupFormat(ConfigurableConstants.getMasterTable(ConfigurableConstants.TUSMS_MASTER_TYPE, smsTopUpFormat));
			
			productType.setDefaultBalanceExpMonths(defaultBalanceExpMonths);
			productType.setTopUpFee(topupFee);
			productType.setTransferFee(transferFee);
			
//			if(numberOfDigit.trim().equals(""))
//				productType.setDefaultValidPeriod(0);
//			else
//				productType.setDefaultValidPeriod(Integer.parseInt(defaultExpiry));
//			
			int confirmMessage = Messagebox.show( "Update product type ?","Edit Product Type Confirmation ",Messagebox.OK | Messagebox.CANCEL,Messagebox.QUESTION);
			if(confirmMessage == Messagebox.OK){
				try{
					this.businessHelper.getGenericBusiness().update(productType);
				    Messagebox.show("Product Type is updated successfully", "Edit Product", Messagebox.OK, Messagebox.INFORMATION);
					this.back();
				}
				catch(Exception e){
					Messagebox.show(com.cdgtaxi.ibs.common.NonConfigurableConstants.HIBERNATE_ERROR_MESSAGE, 
						"Error", Messagebox.OK, Messagebox.ERROR);
					Executions.getCurrent().sendRedirect(""); //TO DO A REFRESH
					e.printStackTrace();
				}
			}
		}
	}
			
	public void cancel() throws InterruptedException{
		this.back();
	}
	
	@Override
	public void refresh() throws InterruptedException {
		((Textbox)this.getFellow("name")).setValue("");
	}
			
	public void refreshNewProduct()throws InterruptedException {
		this.forward(Uri.ADD_PRODUCT_TYPE,null);
	}
			
	public void getSubBin(){
		Textbox subBin=(Textbox)this.getFellow("sub_bin");
		
		if(productType.getSubBinRange().equalsIgnoreCase(NonConfigurableConstants.BOOLEAN_NA))
			subBin.setValue(null);
		else
			subBin.setValue(productType.getSubBinRange());
	}
	public void getBin(){
		Textbox Bin=(Textbox)this.getFellow("bin");
		
		if(productType.getBinRange().equalsIgnoreCase(NonConfigurableConstants.BOOLEAN_NA))
			Bin.setValue(null);
		else
			Bin.setValue(productType.getBinRange());
	}
	
	public void UpdateSelectedItems(){
		//Batch Issue
		Listbox batchIssueList = (Listbox)this.getFellow("batch_issue");
		for(Object itemOfList : batchIssueList.getItems()){
			if(((Listitem)itemOfList).getValue().equals(productType.getBatchIssue())){
				((Listitem)itemOfList).setSelected(true);
				//this.updateProductType();
				break;
			}
		}
		//Name on Card
		Listbox nameOnCardList = (Listbox)this.getFellow("name_on_card");
		for(Object itemOfList : nameOnCardList.getItems()){
			if(((Listitem)itemOfList).getValue().equals(productType.getNameOnProduct())){
				((Listitem)itemOfList).setSelected(true);
				//this.updateProductType();
				break;
			}
		}
		//Luhn Check
		Listbox luhnCheckList = (Listbox)this.getFellow("luhn_check");
		for(Object itemOfList : luhnCheckList.getItems()){
			if(((Listitem)itemOfList).getValue().equals(productType.getLuhnCheck())){
				((Listitem)itemOfList).setSelected(true);
				//this.updateProductType();
				break;
			}
		}
		//Usage Limitation
		Listbox usageLimitationList = (Listbox)this.getFellow("usage_limitation");
		for(Object itemOfList :  usageLimitationList .getItems()){
			if(((Listitem)itemOfList).getValue().equals(productType.getOneTimeUsage())){
				((Listitem)itemOfList).setSelected(true);
				//this.updateProductType();
				break;
			}
		}
		//Fixed Value
		Listbox fixedValueList = (Listbox)this.getFellow("fixed_value");
		for(Object itemOfList :  fixedValueList .getItems()){
			if(((Listitem)itemOfList).getValue().equals(productType.getFixedValue())){
				((Listitem)itemOfList).setSelected(true);
				//this.updateProductType();
				break;
			}
		}
		//Credit Limit
		Listbox creditLimitList = (Listbox)this.getFellow("credit_limit");
		for(Object itemOfList :  creditLimitList .getItems()){
			if(((Listitem)itemOfList).getValue().equals(productType.getCreditLimit())){
				((Listitem)itemOfList).setSelected(true);
				//this.updateProductType();
				break;
			}
		}
		//Validity Period
		Listbox validityPeriodList = (Listbox)this.getFellow("validity_period");
		for(Object itemOfList :  validityPeriodList .getItems()){
			if(((Listitem)itemOfList).getValue().equals(productType.getValidityPeriod())){
				((Listitem)itemOfList).setSelected(true);
				//this.updateProductType();
				break;
			}
		}
		//Default Card Status
		Listbox defaultCardStatusList = (Listbox)this.getFellow("card_status");
		for(Object itemOfList :  defaultCardStatusList .getItems()){
			if(((Listitem)itemOfList).getValue().equals(productType.getDefaultCardStatus())){
				((Listitem)itemOfList).setSelected(true);
				//this.updateProductType();
				break;
			}
		}
		//Negative File Check
		Listbox negativeFileCheckList = (Listbox)this.getFellow("negative_file_check");
		for(Object itemOfList :  negativeFileCheckList .getItems()){
			if(((Listitem)itemOfList).getValue().equals(productType.getExternalCard())){
				((Listitem)itemOfList).setSelected(true);
				//this.updateProductType();
				break;
			}
		}
		// Issuable
		Listbox issuableList = (Listbox)this.getFellow("issuable");
		for(Object itemOfList :  issuableList .getItems()){
			if(((Listitem)itemOfList).getValue().equals(productType.getIssuable())){
				((Listitem)itemOfList).setSelected(true);
				//this.updateProductType();
				break;
			}
		}
		// Issue Type
		Listbox issueTypeList = (Listbox)this.getFellow("issue_type");
		for(Object itemOfList :  issueTypeList .getItems()){
			if(((Listitem)itemOfList).getValue().equals(productType.getIssueType())){
				((Listitem)itemOfList).setSelected(true);
				//this.updateProductType();
				break;
			}
		}
		batchIssueCheck();
		issuableCheck();
		ValidityPeriodCheck();
		// Login Registration
		Listbox loginRegistrationListBox = (Listbox)this.getFellow("loginRegistrationListBox");
		for(Object itemOfList :  loginRegistrationListBox.getItems()){
			if(((Listitem)itemOfList).getValue().equals(productType.getLoginRegistration())){
				((Listitem)itemOfList).setSelected(true);
				break;
			}
		}
		// Hotel
		Listbox hotelListBox = (Listbox)this.getFellow("hotelListBox");
		for(Object itemOfList :  hotelListBox.getItems()){
			if(((Listitem)itemOfList).getValue().equals(productType.getHotel())){
				((Listitem)itemOfList).setSelected(true);
				break;
			}
		}
		// Prepaid
		Listbox prepaidListBox = (Listbox)this.getFellow("prepaidListBox");
		for(Object itemOfList :  prepaidListBox.getItems()){
			if(((Listitem)itemOfList).getValue().equals(productType.getPrepaid())){
				((Listitem)itemOfList).setSelected(true);
				break;
			}
		}
		// Contactless
		Listbox contactlessListBox = (Listbox)this.getFellow("contactlessListBox");
		for(Object itemOfList :  contactlessListBox.getItems()){
			if(((Listitem)itemOfList).getValue().equals(productType.getContactless())){
				((Listitem)itemOfList).setSelected(true);
				break;
			}
		}
		// Cardless
		Listbox cardlessListBox = (Listbox)this.getFellow("cardlessListBox");
		for(Object itemOfList :  cardlessListBox.getItems()){
			if(((Listitem)itemOfList).getValue().equals(productType.getCardless())){
				((Listitem)itemOfList).setSelected(true);
				break;
			}
		}

		cardlessCheck();
		
		// Virtual
		Listbox virtualListBox = (Listbox)this.getFellow("virtualListBox");
		for(Object itemOfList :  virtualListBox.getItems()){
			if(((Listitem)itemOfList).getValue().equals(productType.getVirtualProduct())){
				((Listitem)itemOfList).setSelected(true);
				break;
			}
		}
		
		// SMS Format
		Listbox smsFormatListBox = (Listbox)this.getFellow("smsFormatListBox");
		for(Object itemOfList :  smsFormatListBox.getItems()){
			MstbMasterTable smsFormatMasterTable = productType.getMstbMasterTableBySMSFormat();
			if(smsFormatMasterTable!=null && ((Listitem)itemOfList).getValue().equals(smsFormatMasterTable.getMasterCode())){
				((Listitem)itemOfList).setSelected(true);
				break;
			}
		}
		
		// SMS Expiry Format
		Listbox smsExpiryFormatListBox = (Listbox)this.getFellow("smsExpiryFormatListBox");
		for(Object itemOfList :  smsExpiryFormatListBox.getItems()){
			MstbMasterTable smsExpiryFormatMasterTable = productType.getMstbMasterTableBySMSExpiryFormat();
			if(smsExpiryFormatMasterTable!=null && ((Listitem)itemOfList).getValue().equals(smsExpiryFormatMasterTable.getMasterCode())){
				((Listitem)itemOfList).setSelected(true);
				break;
			}
		}
		
		// SMS Top Up Format
		Listbox smsTopUpFormatListBox = (Listbox)this.getFellow("smsTopUpFormatListBox");
		for(Object itemOfList :  smsTopUpFormatListBox.getItems()){
			MstbMasterTable smsTopUpFormatMasterTable = productType.getMstbMasterTableBySMSTopupFormat();
			if(smsTopUpFormatMasterTable!=null && ((Listitem)itemOfList).getValue().equals(smsTopUpFormatMasterTable.getMasterCode())){
				((Listitem)itemOfList).setSelected(true);
				break;
			}
		}
		
		
	}
	
	public void populateValidityPeriodListBox(){
		Listbox validityPeriodLB = (Listbox)this.getFellow("validity_period");
		Set<Entry<String,String>> entries = NonConfigurableConstants.VALIDITY_PERIOD_FLAGS.entrySet();
		for(Entry<String,String> entry : entries){
			validityPeriodLB.appendItem(entry.getValue(), entry.getKey());
			if(productType!= null && productType.getValidityPeriod().equals(entry.getKey()));
				validityPeriodLB.setSelectedIndex(validityPeriodLB.getItemCount()-1);
		}
		if(validityPeriodLB.getSelectedItem() == null) validityPeriodLB.setSelectedIndex(0);
	}
}