package com.cdgtaxi.ibs.product.ui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.log4j.Logger;
import org.zkoss.zk.ui.ext.AfterCompose;
import org.zkoss.zul.Button;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;

import com.cdgtaxi.ibs.Uri;
import com.cdgtaxi.ibs.common.ConfigurableConstants;
import com.cdgtaxi.ibs.common.NonConfigurableConstants;
import com.cdgtaxi.ibs.common.model.PmtbProductType;
import com.cdgtaxi.ibs.common.ui.CommonWindow;
import com.cdgtaxi.ibs.util.ComponentUtil;
import com.cdgtaxi.ibs.web.component.Constants;
public class ManageProductTypeWindow extends CommonWindow implements AfterCompose{
		
	private static final long serialVersionUID = 3798017820177263278L;
	private static Logger logger = Logger.getLogger(IssueProductWindow.class);
	public  ManageProductTypeWindow(){}

	public void afterCompose(){
		if(!this.checkUriAccess(Uri.ADD_PRODUCT_TYPE))
			((Button)this.getFellow("createBtn")).setDisabled(true);
	}
	
	public List<Listitem> getCardStatus(){
		logger.info("getCardStatus()");
		ArrayList<Listitem> cardStatus = new ArrayList<Listitem>();
		//This one is to show (-) dash in the list
		cardStatus.add((Listitem) ComponentUtil.createNotRequiredListItem());
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
		cardStatus.add((Listitem) ComponentUtil.createNotRequiredListItem());
		for(String key : NonConfigurableConstants.BOOLEAN_YN.keySet()){
			cardStatus.add(new Listitem(NonConfigurableConstants.BOOLEAN_YN.get(key), key));
		}
		return cardStatus;
	}
		
	public List<Listitem> getYesNoNABlankStatus(){
		logger.info("getCardStatus()");
		ArrayList<Listitem> cardStatus = new ArrayList<Listitem>();
		//This one is to show (-) dash in the list
		cardStatus.add((Listitem) ComponentUtil.createNotRequiredListItem());
		for(String key : NonConfigurableConstants.BOOLEAN.keySet()){
			cardStatus.add(new Listitem(NonConfigurableConstants.BOOLEAN.get(key), key));
		}
		return cardStatus;
	}
		
	public List<Listitem> getIssueTypeList(){
		logger.info("getIssueTypeList()");
		ArrayList<Listitem> issueType = new ArrayList<Listitem>();
		//This one is to show (-) dash in the list
		issueType.add((Listitem) ComponentUtil.createNotRequiredListItem());
		for(String key : NonConfigurableConstants.ISSUE_TYPE.keySet()){
			issueType.add(new Listitem(NonConfigurableConstants.ISSUE_TYPE.get(key), key));
		}
		return issueType;
	}
		
	public void searchProductTypes() throws InterruptedException {
			
		String errorMessage="";
		
		String productName= ((Textbox)this.getFellow("name")).getValue();
		if(productName==null) productName="";
		
		String numberOfDigit= ((Intbox)this.getFellow("no_of_digits")).getText();
		if( numberOfDigit==null) numberOfDigit="";
		else{
			if(numberOfDigit.trim().length()<1) errorMessage="No of Digit cannot be blank";
		}
		
		String binNumber= ((Intbox)this.getFellow("bin")).getText();
		if( binNumber==null) binNumber="";

		String subBin= ((Intbox)this.getFellow("sub_bin")).getText();
		if( subBin==null) subBin="";
		
		Listbox batch_issueList = (Listbox)this.getFellow("batch_issue");
		String batchIssue=(String)batch_issueList.getSelectedItem().getValue();
		Listbox name_on_cardList = (Listbox)this.getFellow("name_on_card");
		String nameOnCard=(String)name_on_cardList.getSelectedItem().getValue();
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
		Listbox issuableList = (Listbox)this.getFellow("issuable");
		String issuable=(String)issuableList.getSelectedItem().getValue();
		Listbox issue_typeList = (Listbox)this.getFellow("issue_type");
		String issueType=(String)issue_typeList.getSelectedItem().getValue();
		String defaultExpiry= ((Intbox)this.getFellow("default_expiry")).getText();
		Integer defaultBalanceExpMonths = ((Intbox)this.getFellow("defaultBalanceExpMonthsField")).getValue();
		
		String loginRegistrationFlag = (String)((Listbox)this.getFellow("loginRegistrationListBox")).getSelectedItem().getValue();
		String hotelFlag = (String)((Listbox)this.getFellow("hotelListBox")).getSelectedItem().getValue();
		String prepaid = (String) ((Listbox)this.getFellow("prepaidListBox")).getSelectedItem().getValue();
		String contactless = (String) ((Listbox)this.getFellow("contactlessListBox")).getSelectedItem().getValue();
		String smsFormat = (String) ((Listbox)this.getFellow("smsFormatListBox")).getSelectedItem().getValue();
		String smsExpiryFormat = (String) ((Listbox)this.getFellow("smsExpiryFormatListBox")).getSelectedItem().getValue();
		String smsTopUpFormat = (String) ((Listbox)this.getFellow("smsTopUpFormatListBox")).getSelectedItem().getValue();
		String cardless = (String) ((Listbox)this.getFellow("cardlessListBox")).getSelectedItem().getValue();
		String virtual = (String) ((Listbox)this.getFellow("virtualListBox")).getSelectedItem().getValue();
		PmtbProductType productType=new PmtbProductType();
		productType.setName(productName.toUpperCase());
		if(!numberOfDigit.equalsIgnoreCase("") && numberOfDigit!=null)
			productType.setNumberOfDigit(Integer.parseInt(numberOfDigit));
		else 
			productType.setNumberOfDigit(0);
		productType.setBinRange(binNumber);
		productType.setSubBinRange(subBin);
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
		productType.setIssueType(issueType);
		if(!defaultExpiry.equalsIgnoreCase(""))	
			productType.setDefaultValidPeriod(Integer.parseInt(defaultExpiry));
		else
			productType.setDefaultValidPeriod(-1);
		productType.setLoginRegistration(loginRegistrationFlag);
		productType.setHotel(hotelFlag);
		productType.setPrepaid(prepaid);
		productType.setContactless(contactless);
		productType.setMstbMasterTableBySMSFormat(ConfigurableConstants.getMasterTable(ConfigurableConstants.TXSMS_MASTER_TYPE, smsFormat));
		productType.setMstbMasterTableBySMSExpiryFormat(ConfigurableConstants.getMasterTable(ConfigurableConstants.EXSMS_MASTER_TYPE, smsExpiryFormat));
		productType.setMstbMasterTableBySMSTopupFormat(ConfigurableConstants.getMasterTable(ConfigurableConstants.TUSMS_MASTER_TYPE, smsTopUpFormat));
		productType.setDefaultBalanceExpMonths(defaultBalanceExpMonths);
		productType.setCardless(cardless);
		productType.setVirtualProduct(virtual);
		//Debugging statements - enable when needed
//		logger.info("Search PrductType");
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

		Map<String, Map<String, String>> dataMap=this.businessHelper.getProductTypeBusiness().getProductTypes(productType);
		Listbox resultListBox = (Listbox)this.getFellow("resultList");
		resultListBox.setMold(Constants.LISTBOX_MOLD_DEFAULT);
		resultListBox.getItems().clear();
		
		if(dataMap.size()>ConfigurableConstants.getMaxQueryResult())
			Messagebox.show(NonConfigurableConstants.getExceedMaxResultMessage(), "Alert", Messagebox.OK, Messagebox.INFORMATION);
		
		try{
			int count=0;
			for(String produtTypeid : dataMap.keySet()){
				count++;
				
				Listitem item = new Listitem();
				item.setValue(produtTypeid);
				Map<String,String> productDetails = dataMap.get(produtTypeid);
				
				//Populating the table cells
				
				//Product Type Name
				item.appendChild(new Listcell(productDetails.get("name")));
				//No of Digits
				if(productDetails.get("numberOfDigit").equalsIgnoreCase("0"))
					item.appendChild(new Listcell("NA"));
				else
					item.appendChild(new Listcell(productDetails.get("numberOfDigit")));
				//Bin Range
				item.appendChild(new Listcell(productDetails.get("binRange")));
				//Sub Bin Range
				item.appendChild(new Listcell(productDetails.get("subBinRange")));
				//Default Status
				item.appendChild(new Listcell(NonConfigurableConstants.PRODUCT_STATUS.get(productDetails.get("defaultCardStatus"))));
				//Issue Type
				item.appendChild(new Listcell(NonConfigurableConstants.ISSUE_TYPE.get(productDetails.get("issueType"))));
				//Default Expiry
				if(productDetails.get("defaultValidPeriod").equalsIgnoreCase("-1"))
					item.appendChild(new Listcell("NA"));
				else
					item.appendChild(new Listcell(productDetails.get("defaultValidPeriod")));
				//Login Registration
				item.appendChild(newListcell(NonConfigurableConstants.BOOLEAN_YN.get(productDetails.get("loginRegistration"))));
				//Hotel
				item.appendChild(newListcell(NonConfigurableConstants.BOOLEAN_YN.get(productDetails.get("hotel"))));
				item.appendChild(newListcell(NonConfigurableConstants.BOOLEAN_YN.get(productDetails.get("contactless"))));
				item.appendChild(newListcell(NonConfigurableConstants.BOOLEAN_YN.get(productDetails.get("cardless"))));
				resultListBox.appendChild(item);
			}
			
			if(resultListBox.getListfoot()!=null && count>0)
				resultListBox.removeChild(resultListBox.getListfoot());	
			
			resultListBox.setMold(Constants.LISTBOX_MOLD_PAGING);
			resultListBox.setPageSize(ConfigurableConstants.getSortPagingSize());
			
			if(dataMap.size()>ConfigurableConstants.getMaxQueryResult())
				resultListBox.removeItemAt(ConfigurableConstants.getMaxQueryResult());
		}
		catch(Exception e){
			Messagebox.show(com.cdgtaxi.ibs.common.NonConfigurableConstants.COMMON_ERROR_MESSAGE, 
				"Error", Messagebox.OK, Messagebox.ERROR);
				e.printStackTrace();
		}
	}
		
	public void EditProductTypes() throws InterruptedException {
			
		logger.info("Edit Product Type");
		//	Retrieve selected value
		Listbox resultListBox = (Listbox)this.getFellow("resultList");
		String productTypeId = (String)resultListBox.getSelectedItem().getValue();
		logger.info("Product Type ID "+productTypeId);
		HashMap<String,String> map = new HashMap<String,String>();
		map.put("productTypeId", productTypeId);
		
		if(this.checkUriAccess(Uri.EDIT_PRODUCT_TYPE)) this.forward(Uri.EDIT_PRODUCT_TYPE,map);
		else if(this.checkUriAccess(Uri.VIEW_PRODUCT_TYPE)) this.forward(Uri.VIEW_PRODUCT_TYPE,map);
	}
			
	public void CreateProductTypes() throws InterruptedException {
			
		logger.info("Create Product Type");
		this.forward(Uri.ADD_PRODUCT_TYPE,null);
	}
	
	public void cancel() throws InterruptedException{
			this.back();
	}
		
	@Override
	public void refresh() throws InterruptedException {
		//this.searchProductTypes();
		searchProductTypes();
	}
		
	public void reset(){
		((Textbox)this.getFellow("name")).setValue("");
		((Intbox)this.getFellow("no_of_digits")).setValue(null);
		((Intbox)this.getFellow("bin")).setText("");
		((Intbox)this.getFellow("sub_bin")).setText("");
		((Listbox)this.getFellow("batch_issue")).setSelectedIndex(0);
		((Listbox)this.getFellow("name_on_card")).setSelectedIndex(0);
		((Listbox)this.getFellow("luhn_check")).setSelectedIndex(0);
		((Listbox)this.getFellow("usage_limitation")).setSelectedIndex(0);
		((Listbox)this.getFellow("fixed_value")).setSelectedIndex(0);
		((Listbox)this.getFellow("credit_limit")).setSelectedIndex(0);
		((Listbox)this.getFellow("validity_period")).setSelectedIndex(0);
		((Listbox)this.getFellow("card_status")).setSelectedIndex(0);
		((Listbox)this.getFellow("negative_file_check")).setSelectedIndex(0);
		((Listbox)this.getFellow("issuable")).setSelectedIndex(0);
		((Listbox)this.getFellow("issue_type")).setSelectedIndex(0);
		((Intbox)this.getFellow("default_expiry")).setValue(null);
		
		((Listbox)this.getFellow("loginRegistrationListBox")).setSelectedIndex(0);
		((Listbox)this.getFellow("hotelListBox")).setSelectedIndex(0);
		((Listbox)this.getFellow("prepaidListBox")).setSelectedIndex(0);
		((Listbox)this.getFellow("cardlessListBox")).setSelectedIndex(0);
		((Listbox)this.getFellow("contactlessListBox")).setSelectedIndex(0);
		((Listbox)this.getFellow("smsFormatListBox")).setSelectedIndex(0);
		
		Listbox resultListBox = (Listbox)this.getFellow("resultList");
		resultListBox.getItems().clear();
		if(resultListBox.getListfoot()==null){
			resultListBox.appendChild(ComponentUtil.createNoRecordsFoundListFoot(9));
		}
		resultListBox.setMold(Constants.LISTBOX_MOLD_PAGING);
		resultListBox.setPageSize(10);
	}
	
	public void populateValidityPeriodListBox(){
		Listbox validityPeriodLB = (Listbox)this.getFellow("validity_period");
		validityPeriodLB.appendChild((Listitem) ComponentUtil.createNotRequiredListItem());
		Set<Entry<String,String>> entries = NonConfigurableConstants.VALIDITY_PERIOD_FLAGS.entrySet();
		for(Entry<String,String> entry : entries){
			validityPeriodLB.appendItem(entry.getValue(), entry.getKey());
		}
		validityPeriodLB.appendItem(NonConfigurableConstants.BOOLEAN_NA, NonConfigurableConstants.BOOLEAN_NA);
		validityPeriodLB.setSelectedIndex(0);
	}
}

