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
import com.cdgtaxi.ibs.common.exception.ASWebserviceConnectionException;
import com.cdgtaxi.ibs.common.exception.ASWebserviceException;
import com.cdgtaxi.ibs.common.model.PmtbProductType;
import com.cdgtaxi.ibs.common.ui.CommonWindow;
import com.cdgtaxi.ibs.util.ComponentUtil;
import com.cdgtaxi.ibs.util.LoggerUtil;
import com.cdgtaxi.ibs.web.component.CapsTextbox;

@SuppressWarnings({"serial"})
public class CreateProductTypeWindow extends CommonWindow {
		
	private static Logger logger = Logger.getLogger(IssueProductWindow.class);

		
	public List<Listitem> getCardStatus(){
	logger.info("getCardStatus()");
	ArrayList<Listitem> cardStatus = new ArrayList<Listitem>();
	for(String key : NonConfigurableConstants.PRODUCT_STATUS.keySet()){
		if(!key.equalsIgnoreCase(NonConfigurableConstants.PRODUCT_STATUS_NEW)){
			if(!key.equalsIgnoreCase(NonConfigurableConstants.PRODUCT_STATUS_PARENT_SUSPENDED))
				if(!key.equalsIgnoreCase(NonConfigurableConstants.PRODUCT_STATUS_TERMINATED))
					if(!key.equalsIgnoreCase(NonConfigurableConstants.PRODUCT_STATUS_USED))
						cardStatus.add(new Listitem(NonConfigurableConstants.PRODUCT_STATUS.get(key), key));
		}
		//cardStatus.add(new Listitem(NonConfigurableConstants.PRODUCT_STATUS.get(key), key));
	} 
	return cardStatus;
	}
	public void batchIssueCheck(){
		Listbox batch_issueList = (Listbox)this.getFellow("batch_issue");
		String batchIssue= (String)batch_issueList.getSelectedItem().getValue();
		if(batchIssue.equalsIgnoreCase(NonConfigurableConstants.BOOLEAN_YES))
			((Listbox)this.getFellow("issue_type")).setDisabled(false);
		else ((Listbox)this.getFellow("issue_type")).setDisabled(true);
	}
				
	public void issuableCheck(){
		Listbox issuableList = (Listbox)this.getFellow("issuable");
		String issuable= (String)issuableList.getSelectedItem().getValue();
		logger.info("Issuable = "+issuable);
		if(issuable.equalsIgnoreCase(NonConfigurableConstants.BOOLEAN_NO)){
			((Listbox)this.getFellow("issue_type")).setDisabled(true);
			//((Intbox)this.getFellow("no_of_digits")).setDisabled(true);
			//((Textbox)this.getFellow("bin")).setDisabled(true);
			//((Textbox)this.getFellow("sub_bin")).setDisabled(true);
			//((Intbox)this.getFellow("no_of_digits")).setValue(0);
			//((Textbox)this.getFellow("bin")).setValue(null);
			//((Textbox)this.getFellow("sub_bin")).setValue(null);
			
			((Listbox)this.getFellow("batch_issue")).setDisabled(true);
			((Listbox)this.getFellow("name_on_card")).setDisabled(true);
			//((Listbox)this.getFellow("luhn_check")).setDisabled(true);
			((Listbox)this.getFellow("usage_limitation")).setDisabled(true);
			((Listbox)this.getFellow("fixed_value")).setDisabled(true);
			((Listbox)this.getFellow("credit_limit")).setDisabled(true);
			((Listbox)this.getFellow("validity_period")).setDisabled(true);
			((Listbox)this.getFellow("card_status")).setDisabled(true);
			//((Listbox)this.getFellow("negative_file_check")).setDisabled(true);
			//((Listbox)this.getFellow("issuable")).setDisabled(true);
			((Listbox)this.getFellow("issue_type")).setDisabled(true);
			((Intbox)this.getFellow("default_expiry")).setDisabled(true);
			((Decimalbox)this.getFellow("replacementFees")).setDisabled(true);
			((Intbox)this.getFellow("default_expiry")).setValue(0);
			((Decimalbox)this.getFellow("replacementFees")).setValue(new BigDecimal("0"));
			//batchIssueCheck();
		}
		else{
			((Listbox)this.getFellow("issue_type")).setDisabled(false);
			((Intbox)this.getFellow("no_of_digits")).setDisabled(false);
			((Intbox)this.getFellow("bin")).setDisabled(false);
			((Intbox)this.getFellow("sub_bin")).setDisabled(false);
			((Listbox)this.getFellow("batch_issue")).setDisabled(false);
			((Listbox)this.getFellow("name_on_card")).setDisabled(false);
			((Listbox)this.getFellow("luhn_check")).setDisabled(false);
			((Listbox)this.getFellow("usage_limitation")).setDisabled(false);
			((Listbox)this.getFellow("fixed_value")).setDisabled(false);
			((Listbox)this.getFellow("credit_limit")).setDisabled(false);
			((Listbox)this.getFellow("validity_period")).setDisabled(false);
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
			((Listbox)this.getFellow("issuable")).setDisabled(false);
			((Listbox)this.getFellow("batch_issue")).setDisabled(false);
			((Listbox)this.getFellow("smsTopUpFormatListBox")).setDisabled(false);
			((Listbox)this.getFellow("luhn_check")).setDisabled(false);
			((Listbox)this.getFellow("negative_file_check")).setDisabled(false);
			((Listbox)this.getFellow("prepaidListBox")).setDisabled(false);
			((Listbox)this.getFellow("contactlessListBox")).setDisabled(false);
			((Decimalbox)this.getFellow("transferFeeField")).setDisabled(false);
			((Decimalbox)this.getFellow("topupFeeField")).setDisabled(false);
			((Intbox)this.getFellow("no_of_digits")).setDisabled(false);
			((Intbox)this.getFellow("defaultBalanceExpMonthsField")).setDisabled(false);
			((Intbox)this.getFellow("bin")).setDisabled(false);
			((Intbox)this.getFellow("sub_bin")).setDisabled(false);
			((Listbox)this.getFellow("hotelListBox")).setDisabled(false);
			issuableCheck();
		}
		else{
			((Listbox)this.getFellow("issuable")).setDisabled(true);
			((Listbox)this.getFellow("batch_issue")).setDisabled(true);
			((Listbox)this.getFellow("smsTopUpFormatListBox")).setDisabled(true);

			((Listbox)this.getFellow("luhn_check")).setDisabled(true);
			((Listbox)this.getFellow("negative_file_check")).setDisabled(true);
			((Listbox)this.getFellow("prepaidListBox")).setDisabled(true);
			((Listbox)this.getFellow("contactlessListBox")).setDisabled(true);
			((Decimalbox)this.getFellow("transferFeeField")).setDisabled(true);
			((Decimalbox)this.getFellow("topupFeeField")).setDisabled(true);
			((Intbox)this.getFellow("no_of_digits")).setDisabled(true);
			((Intbox)this.getFellow("defaultBalanceExpMonthsField")).setDisabled(true);
			((Intbox)this.getFellow("bin")).setDisabled(true);
			((Intbox)this.getFellow("sub_bin")).setDisabled(true);
			
			((Intbox)this.getFellow("no_of_digits")).setValue(null);
			((Decimalbox)this.getFellow("transferFeeField")).setValue(new BigDecimal("0"));
			((Decimalbox)this.getFellow("topupFeeField")).setValue(new BigDecimal("0"));
			
			// Issuable
			Listbox issuableList = (Listbox)this.getFellow("issuable");
			for(Object itemOfList :  issuableList .getItems()){
				if(((Listitem)itemOfList).getValue().equals("N")){
					((Listitem)itemOfList).setSelected(true);
					//this.updateProductType();
					break;
				}
			}
			((Listbox)this.getFellow("hotelListBox")).setDisabled(true);
			Listbox hotelList = (Listbox)this.getFellow("hotelListBox");
			for(Object itemOfList :  hotelList .getItems()){
				if(((Listitem)itemOfList).getValue().equals("N")){
					((Listitem)itemOfList).setSelected(true);
					break;
				}
			}
			
			issuableCheck();
		}
	}
	public void ValidityPeriodCheck(){
		Listbox validityPeriodList = (Listbox)this.getFellow("validity_period");
		String validityPeriod= (String)validityPeriodList.getSelectedItem().getValue();
		logger.info("Issuable = "+validityPeriod);
		if(!validityPeriod.equalsIgnoreCase(NonConfigurableConstants.VALIDITY_PERIOD_FLAG_NO)){
			((Intbox)this.getFellow("default_expiry")).setDisabled(false);
		}
		else{
			((Intbox)this.getFellow("default_expiry")).setValue(0);
			((Intbox)this.getFellow("default_expiry")).setDisabled(true);
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
		for(String key : NonConfigurableConstants.BOOLEAN_YN.keySet()){
			cardStatus.add(new Listitem(NonConfigurableConstants.BOOLEAN_YN.get(key), key));
		}
			return cardStatus;
	}
		
	public List<Listitem> getYesNoNABlankStatus(){
		logger.info("getCardStatus()");
		ArrayList<Listitem> cardStatus = new ArrayList<Listitem>();
		for(String key : NonConfigurableConstants.BOOLEAN.keySet()){
			cardStatus.add(new Listitem(NonConfigurableConstants.BOOLEAN.get(key), key));
		}
		return cardStatus;
	}
	public List<Listitem> getIssueTypeList(){
		logger.info("getIssueTypeList()");
		ArrayList<Listitem> issueType = new ArrayList<Listitem>();
		for(String key : NonConfigurableConstants.ISSUE_TYPE.keySet()){
			issueType.add(new Listitem(NonConfigurableConstants.ISSUE_TYPE.get(key), key));
		}
		return issueType;
	}
	public void saveProductTypes() throws InterruptedException {
		//Getting the components from create.zul
		Listbox batch_issueList = (Listbox)this.getFellow("batch_issue");
		Listbox name_on_cardList = (Listbox)this.getFellow("name_on_card");
		Listbox luhn_checkList = (Listbox)this.getFellow("luhn_check");
		Listbox usage_limitationList = (Listbox)this.getFellow("usage_limitation");
		Listbox fixed_valueList = (Listbox)this.getFellow("fixed_value");
		Listbox credit_limitList = (Listbox)this.getFellow("credit_limit");
		Listbox validity_periodList = (Listbox)this.getFellow("validity_period");
		Listbox cardStatusList = (Listbox)this.getFellow("card_status");
		Listbox negative_file_checkList = (Listbox)this.getFellow("negative_file_check");
		Listbox issuableList = (Listbox)this.getFellow("issuable");
		Listbox issue_typeList = (Listbox)this.getFellow("issue_type");
		Listbox loginRegistrationListBox = (Listbox)this.getFellow("loginRegistrationListBox");
		Listbox hotelListBox = (Listbox)this.getFellow("hotelListBox");
		Listbox prepaidListBox = (Listbox)this.getFellow("prepaidListBox");
		Listbox contactlessListBox = (Listbox)this.getFellow("contactlessListBox");
		Listbox cardlessListBox = (Listbox)this.getFellow("cardlessListBox");
		Listbox virtualListBox = (Listbox)this.getFellow("virtualListBox");
		Listbox smsFormatListBox = (Listbox)this.getFellow("smsFormatListBox");
		Listbox smsExpiryFormatListBox = (Listbox)this.getFellow("smsExpiryFormatListBox");
		Listbox smsTopUpFormatListBox = (Listbox)this.getFellow("smsTopUpFormatListBox");
		Intbox defaultBalanceExpMonthsField = (Intbox)this.getFellow("defaultBalanceExpMonthsField");
		Decimalbox topupFeeField = (Decimalbox)this.getFellow("topupFeeField");
		Decimalbox transferFeeField = (Decimalbox)this.getFellow("transferFeeField");
		
		//Assigning the card type info
		String batchIssue=(String)batch_issueList.getSelectedItem().getValue();
		String nameOnCard=(String)name_on_cardList.getSelectedItem().getValue();
		String luhnCheck=(String)luhn_checkList.getSelectedItem().getValue();
		String usageLimitation= (String)usage_limitationList.getSelectedItem().getValue();
		String fixedValue= (String)fixed_valueList.getSelectedItem().getValue();
		String creditLimit= (String)credit_limitList.getSelectedItem().getValue();
		String validityPeriod=(String)validity_periodList.getSelectedItem().getValue();
		String defaultCardStatus=(String)cardStatusList.getSelectedItem().getValue();
		String negativeFileCheck=(String)negative_file_checkList.getSelectedItem().getValue();
		String issuable=(String)issuableList.getSelectedItem().getValue();
		String issueType=(String)issue_typeList.getSelectedItem().getValue();
		String defaultExpiry= ((Intbox)this.getFellow("default_expiry")).getText();
		String productTypeName= (String)((CapsTextbox)this.getFellow("name")).getValue();
		String productTypeId= (String)((CapsTextbox)this.getFellow("id")).getValue();
		BigDecimal replacementFees=((Decimalbox)this.getFellow("replacementFees")).getValue();
		String numberOfDigit= ((Intbox)this.getFellow("no_of_digits")).getText();
		String binNumber= ((Intbox)this.getFellow("bin")).getText();
		String subBin= ((Intbox)this.getFellow("sub_bin")).getText();
		String loginRegistrationFlag = (String)loginRegistrationListBox.getSelectedItem().getValue();
		String hotelFlag = (String)hotelListBox.getSelectedItem().getValue();
		String prepaidFlag = (String)prepaidListBox.getSelectedItem().getValue();
		String contactlessFlag = (String)contactlessListBox.getSelectedItem().getValue();
		String cardlessFlag = (String)cardlessListBox.getSelectedItem().getValue();
		String virtualFlag = (String)virtualListBox.getSelectedItem().getValue();
		String smsFormat = (String)smsFormatListBox.getSelectedItem().getValue();
		String smsExpiryFormat = (String)smsExpiryFormatListBox.getSelectedItem().getValue();
		String smsTopUpFormat = (String)smsTopUpFormatListBox.getSelectedItem().getValue();
		
		Integer defaultBalanceExpMonths = defaultBalanceExpMonthsField.getValue();
		BigDecimal topupFee= topupFeeField.getValue();
		BigDecimal transferFee= transferFeeField.getValue();
		
		//extra field
		String interfaceMappingValue= (String)((CapsTextbox)this.getFellow("interface_mapping_value")).getValue();
		String errorMessage="";
		PmtbProductType productType=new PmtbProductType();
		boolean check_flag=true;
		boolean checkProductName=false; 		

		logger.info("This one is the productTypeID :>>>>> "+productTypeId);
		//start code
		if(productTypeId==null){
			
			check_flag=false;
			errorMessage="Product ID cannot be blank";
			throw new WrongValueException(this.getFellow("id"), errorMessage);
		}
		else{
			if(productTypeId.trim().length()<2){
				check_flag=false;
				errorMessage="Product Type ID should be 2 alphanumeric.";
				throw new WrongValueException(this.getFellow("id"), errorMessage);
			}
			else{
				PmtbProductType checkProductType=(PmtbProductType)this.businessHelper.getProductTypeBusiness().getProductType(productTypeId);
				if(checkProductType!=null){
					check_flag=false;
					errorMessage="Product Type ID had been used.";
					throw new WrongValueException(this.getFellow("id"), errorMessage);
				}
				checkProductType=null;
			}
		}
		
		if(cardlessFlag.equalsIgnoreCase("Y"))
		{
			//Disabled
			if(batch_issueList.isDisabled())
				batchIssue = "N";
			if(issuableList.isDisabled())
				issuable = "N";
			if(smsTopUpFormatListBox.isDisabled())
				smsTopUpFormat = "N";
			if(luhn_checkList.isDisabled())
				luhnCheck = "N";
			if(negative_file_checkList.isDisabled())
				negativeFileCheck = "N";
			if(prepaidListBox.isDisabled())
				prepaidFlag = "N";
			if(contactlessListBox.isDisabled())
				contactlessFlag = "N";
			if(smsTopUpFormatListBox.isDisabled())
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
		else
		{
			if(replacementFees==null){
				//			replacementFees=new BigDecimal("0");
				check_flag=false;
				errorMessage="Replacemnt field is a mandatory field";
				throw new WrongValueException(this.getFellow("replacementFees"), errorMessage);
			}
					
			if(check_flag){
				if(productTypeName==null){
					check_flag=false;
					errorMessage="Product Type Name cannot be blank";
					throw new WrongValueException(this.getFellow("name"), errorMessage);
				}
				else{
					if(productTypeName.trim().length()<1){
						check_flag=false;
						errorMessage="Product Type Name cannot be blank";
						throw new WrongValueException(this.getFellow("name"), errorMessage);
					}
					else{
						//to check the whether Type Name is already used or not
						checkProductName=this.businessHelper.getProductBusiness().isProductNameInUse(productTypeName);
						if(checkProductName){
							check_flag=false;
							errorMessage="Product Type Name had been used";
							throw new WrongValueException(this.getFellow("name"), errorMessage);
						}
					}
				}
			}
		
			//start code
			if(check_flag){
				if( numberOfDigit==null || numberOfDigit.trim().length()<1 ){
					check_flag=false;
					errorMessage="No of Digit cannot be blank";
					throw new WrongValueException(this.getFellow("no_of_digits"), errorMessage);
					// Messagebox.show(errorMessage, "Create Product Type", Messagebox.OK, Messagebox.INFORMATION);
				}
				else if(Integer.parseInt(numberOfDigit)==0){
					if(issuable.equalsIgnoreCase(NonConfigurableConstants.BOOLEAN_NO) && 
							negativeFileCheck.equalsIgnoreCase(NonConfigurableConstants.BOOLEAN_NO))
					{
						logger.info("This is the premier service");
					}
					else{
						check_flag=false;
						errorMessage="No of Digit cannot be 0 except for Premier Service";
						Messagebox.show(errorMessage, "Create Product Type", Messagebox.OK, Messagebox.INFORMATION);
					}
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
			
			if(check_flag){
				if( binNumber==null || binNumber.trim().length()<1){
					binNumber="";
					if(Integer.parseInt(numberOfDigit)>12){
							check_flag=false;
							errorMessage="Product Type with Number of Digit greater than 12 should have BIN range.";
							 Messagebox.show(errorMessage, "Edit Product Type", Messagebox.OK, Messagebox.INFORMATION);
						}
				}else{
					if(!binNumber.equalsIgnoreCase("")){
						if( binNumber.trim().length()>0 && binNumber.trim().length()<6 && Integer.parseInt(numberOfDigit)>12){
							check_flag=false;
							errorMessage="BIN Number length should be 0 or 6.";
							throw new WrongValueException(this.getFellow("bin"), errorMessage);
						}
						else if(Integer.parseInt(numberOfDigit)<13){
							check_flag=false;
							errorMessage="Number of Digit should be greater than 12 for the Product Type with BIN range.";
							 Messagebox.show(errorMessage, "Edit Product Type", Messagebox.OK, Messagebox.INFORMATION);
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
				
//			if(check_flag){
//				if(validityPeriod.equalsIgnoreCase(NonConfigurableConstants.BOOLEAN_YES)){
//					if(((Intbox)this.getFellow("default_expiry")).getValue()<1)
//						check_flag=false;
//						errorMessage="Default Expiry (Months) should be greater than 0.";
//						Messagebox.show(errorMessage, "Edit Product Type", Messagebox.OK, Messagebox.INFORMATION);
//				}
//				
//			}
			
			if(check_flag){
				if(binNumber.equalsIgnoreCase("")&& !subBin.equalsIgnoreCase("")){
					check_flag=false;
					errorMessage="Sub Bin Number without BIN number.";
					throw new WrongValueException(this.getFellow("sub_bin"), errorMessage);
				}
			}
				
			if(issuable.equalsIgnoreCase("Y")){
				if(check_flag){
					if( defaultExpiry==null){
						check_flag=false;
						errorMessage="Default Expiry Months cannot be blank";
						throw new WrongValueException(this.getFellow("default_expiry"), errorMessage);
					// Messagebox.show(errorMessage, "Create Product Type", Messagebox.OK, Messagebox.INFORMATION);
					}
					else{
						if( defaultExpiry.trim().length()<1 && check_flag){
							check_flag=false;
							errorMessage="Default Expiry Months cannot be blank";
							throw new WrongValueException(this.getFellow("default_expiry"), errorMessage);
							//Messagebox.show(errorMessage, "Create Product Type", Messagebox.OK, Messagebox.INFORMATION);
						}
					}
				}
			}
			
			if(check_flag){
				boolean checkBin=false;
				logger.info("++++++++++++++++++++++++++++++++++++++");
				logger.info("Bin is "+binNumber);
				logger.info("Sub Bin is "+subBin);
				logger.info("++++++++++++++++++++++++++++++++++++++");
				if(!binNumber.equalsIgnoreCase("") && !binNumber.equals("NA"))
				{
					if(binNumber.length()>0)
						checkBin=this.businessHelper.getProductTypeBusiness().getCheckBin(binNumber,subBin,negativeFileCheck.equals(NonConfigurableConstants.BOOLEAN_YES));
					if(checkBin) {
						check_flag=false;
						errorMessage="This BIN and Sub BIN have been used by another card type.";
						Messagebox.show(errorMessage, "Create Product Type", Messagebox.OK, Messagebox.INFORMATION);
					}
				}
			}
		//end of code
				
			if(check_flag){
	
				if(NonConfigurableConstants.getBoolean(prepaidFlag)){
					if(!NonConfigurableConstants.getBoolean(creditLimit)){
						throw new WrongValueException("Credit Limit of product type cannot be NO if it is prepaid");
					}
				}
			}
		}
		if(check_flag)
		{
//			logger.info("productType.setProductTypeId "+productType.getProductTypeId());
			
			productType.setProductTypeId(productTypeId);
			productType.setName(productTypeName);
			productType.setDefaultValidPeriod(0); //Set 0 as default;
			productType.setVirtualProduct(virtualFlag);
			//Bin Range
			if(binNumber.trim().equals("")) productType.setBinRange("NA");
			else productType.setBinRange(binNumber);
			//Sub Bin Range
			if(subBin.trim().equals("")) productType.setSubBinRange("NA");
			else productType.setSubBinRange(subBin);
			
			if(issuable.equalsIgnoreCase(NonConfigurableConstants.BOOLEAN_YES)){
				//no of Digit
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
				//extra field
				productType.setInterfaceMappingValue(interfaceMappingValue);
				if(batchIssue.equalsIgnoreCase("N"))
					productType.setIssueType("NA");
				else
					productType.setIssueType(issueType);
				productType.setReplacementFees(replacementFees);
				if(!validityPeriod.equalsIgnoreCase(NonConfigurableConstants.VALIDITY_PERIOD_FLAG_NO)){
					if(numberOfDigit.trim().equals(""))
						productType.setDefaultValidPeriod(0);
					else
						productType.setDefaultValidPeriod(Integer.parseInt(defaultExpiry));
				}
				else{
					//Changed by Henry to separate the null and 0 in default validity period.
					//productType.setDefaultValidPeriod(0);
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
			
//			logger.info("Search PrductType");
//			logger.info("productName= "+ productType.getName());
//			logger.info("No of digit= "+ productType.getNumberOfDigit());
//			logger.info("BIN range= "+ productType.getBinRange());
//			logger.info("Sub BIN range= "+ productType.getSubBinRange());
//			logger.info("Batch Issue= "+ productType.getBatchIssue());
//			logger.info("Name on Product= "+ productType.getNameOnProduct());
//			logger.info("Luhn_check="+productType.getLuhnCheck());
//			logger.info("Usage_limitation="+productType.getOneTimeUsage());
//			logger.info("Fixed_value="+productType.getFixedValue());
//			logger.info("Credit_limit="+productType.getCreditLimit());
//			logger.info("Validity_period="+productType.getValidityPeriod());
//			logger.info("Default_card_status="+productType.getDefaultCardStatus());
//			logger.info("Negative File Check= "+ productType.getNegativeFileCheck());
//			logger.info("Issueable= "+ productType.getIssuable());
//			logger.info("Issue Type= "+ productType.getIssueType());
//			logger.info("Default Valid Period (Expiry months)= "+ productType.getDefaultValidPeriod());
			boolean check=false;
			int confirmMessage = Messagebox.show( "Create product type?","Create Product Type Confirmation ",Messagebox.OK | Messagebox.CANCEL,Messagebox.QUESTION);
			if(confirmMessage == Messagebox.OK){
				try{
					
					check=this.businessHelper.getProductTypeBusiness().save(productType, getUserLoginIdAndDomain())!=null ? true : false;
				}
				catch(ASWebserviceConnectionException ace){
					Messagebox.show(ace.getMessage(), "Error", Messagebox.OK, Messagebox.ERROR);
					Executions.getCurrent().sendRedirect(""); //TO DO A REFRESH
					ace.printStackTrace();
				}
				catch(ASWebserviceException ae){
					Messagebox.show(ae.getMessage(), "Error", Messagebox.OK, Messagebox.ERROR);
					Executions.getCurrent().sendRedirect(""); //TO DO A REFRESH
					ae.printStackTrace();
				}
				catch(Exception e){
					Messagebox.show(com.cdgtaxi.ibs.common.NonConfigurableConstants.HIBERNATE_ERROR_MESSAGE, 
							"Error", Messagebox.OK, Messagebox.ERROR);
					Executions.getCurrent().sendRedirect(""); //TO DO A REFRESH
					LoggerUtil.printStackTrace(logger, e);
				}
				
				if(check) Messagebox.show("New product Type is created successfully", "Create Message", Messagebox.OK, Messagebox.INFORMATION);
				else Messagebox.show("New product Type cannot be created", "Create Message", Messagebox.OK, Messagebox.ERROR);
				
				this.back();
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
	
	public void populateValidityPeriodListBox(){
		Listbox validityPeriodLB = (Listbox)this.getFellow("validity_period");
		Set<Entry<String,String>> entries = NonConfigurableConstants.VALIDITY_PERIOD_FLAGS.entrySet();
		for(Entry<String,String> entry : entries){
			validityPeriodLB.appendItem(entry.getValue(), entry.getKey());
		}
		validityPeriodLB.setSelectedIndex(0);
	}
}