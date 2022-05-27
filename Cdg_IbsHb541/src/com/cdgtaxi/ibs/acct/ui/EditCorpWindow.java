package com.cdgtaxi.ibs.acct.ui;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.log4j.Logger;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.SuspendNotAllowedException;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Decimalbox;
import org.zkoss.zul.Label;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;

import com.cdgtaxi.ibs.common.ConfigurableConstants;
import com.cdgtaxi.ibs.common.NonConfigurableConstants;
import com.cdgtaxi.ibs.common.exception.IBSException;
import com.cdgtaxi.ibs.interfaces.cnii.CniiInterfaceException;
import com.cdgtaxi.ibs.master.model.MstbMasterTable;
import com.cdgtaxi.ibs.util.ComponentUtil;

public class EditCorpWindow extends CommonInnerAcctWindow {
	// default
	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(EditCorpWindow.class);
	private List<Listitem> industries = new ArrayList<Listitem>();
	private List<Listitem> countries = new ArrayList<Listitem>();
	private List<Listitem> salutations = new ArrayList<Listitem>();
	private List<Listitem> infoSources = new ArrayList<Listitem>();
	private List<Listitem> invoiceFormats = new ArrayList<Listitem>();
	private List<Listitem> invoiceSorts = new ArrayList<Listitem>();
	private List<Listitem> booleanList = new ArrayList<Listitem>();
	private List<Listitem> optionalBooleanList = new ArrayList<Listitem>();
	private List<Listitem> govtEInvFlags = new ArrayList<Listitem>();
	private List<Listitem> businessUnits = new ArrayList<Listitem>();
	private String custNo, createdDt;
	private List<Listitem> daysUnits = new ArrayList<Listitem>();
	private Integer acctNo;
	
	@SuppressWarnings("unchecked")
	public EditCorpWindow() throws InterruptedException{
		logger.info("EditCorpWindow()");
		//account number
		Map<String, String> map = Executions.getCurrent().getArg();
		custNo = map.get("custNo");
		createdDt = map.get("createdDt");
		if(custNo==null || custNo.trim().length()==0){
			Messagebox.show("No account number found!", "Edit Corporate", Messagebox.OK, Messagebox.ERROR);
		}
		// getting data from non configurable constants
		// getting boolean
		booleanList.add(new Listitem(NonConfigurableConstants.BOOLEAN.get(NonConfigurableConstants.BOOLEAN_NO), NonConfigurableConstants.BOOLEAN_NO));
		booleanList.add(new Listitem(NonConfigurableConstants.BOOLEAN.get(NonConfigurableConstants.BOOLEAN_YES), NonConfigurableConstants.BOOLEAN_YES));
		// getting optional boolean
		optionalBooleanList.add((Listitem)ComponentUtil.createNotRequiredListItem());
		optionalBooleanList.add(new Listitem(NonConfigurableConstants.BOOLEAN.get(NonConfigurableConstants.BOOLEAN_NO), NonConfigurableConstants.BOOLEAN_NO));
		optionalBooleanList.add(new Listitem(NonConfigurableConstants.BOOLEAN.get(NonConfigurableConstants.BOOLEAN_YES), NonConfigurableConstants.BOOLEAN_YES));
		// getting data from non configurable constants
		invoiceFormats.add(new Listitem(NonConfigurableConstants.INVOICE_FORMAT.get(NonConfigurableConstants.INVOICE_FORMAT_ACCOUNT), NonConfigurableConstants.INVOICE_FORMAT_ACCOUNT));
		invoiceFormats.add(new Listitem(NonConfigurableConstants.INVOICE_FORMAT.get(NonConfigurableConstants.INVOICE_FORMAT_SUBACCOUNT), NonConfigurableConstants.INVOICE_FORMAT_SUBACCOUNT));
		// getting data from non configurable constants
		invoiceSorts.add(new Listitem(NonConfigurableConstants.INVOICE_SORTING.get(NonConfigurableConstants.INVOICE_SORTING_CARD), NonConfigurableConstants.INVOICE_SORTING_CARD));
		invoiceSorts.add(new Listitem(NonConfigurableConstants.INVOICE_SORTING.get(NonConfigurableConstants.INVOICE_SORTING_TXN_DATE), NonConfigurableConstants.INVOICE_SORTING_TXN_DATE));
		// getting data from master table
		// getting industry
		Map<String, String> masterIndustry = ConfigurableConstants.getIndustries();
		for(String masterCode : masterIndustry.keySet()){
			this.industries.add(new Listitem(masterIndustry.get(masterCode), masterCode));
		}
		// getting country
		Map<String, String> masterCountry = ConfigurableConstants.getCountries();
		for(String masterCode : masterCountry.keySet()){
			this.countries.add(new Listitem(masterCountry.get(masterCode), masterCode));
		}
		Map<String, String> masterDays = NonConfigurableConstants.DAYSwithSuffix;
		for(String masterCode : masterDays.keySet()){
			this.daysUnits.add(new Listitem(masterCode, masterDays.get(masterCode)));
		}
		// getting salutation
		salutations.add((Listitem) ComponentUtil.createNotRequiredListItem());
		Map<String, String> masterSalutation = ConfigurableConstants.getSalutations();
		for(String masterCode : masterSalutation.keySet()){
			this.salutations.add(new Listitem(masterSalutation.get(masterCode), masterCode));
		}
		// getting information source
		infoSources.add((Listitem) ComponentUtil.createNotRequiredListItem());
		Map<String, String> masterInfo = ConfigurableConstants.getInformationSources();
		for(String masterCode : masterInfo.keySet()){
			this.infoSources.add(new Listitem(masterInfo.get(masterCode), masterCode));
		}
		// init data for Govt EInv Flags
		for(Entry<String, String> entry : NonConfigurableConstants.GOVT_EINV_FLAGS.entrySet()){
			Listitem item = new Listitem(entry.getValue(), entry.getKey());
			item.setValue(entry.getKey());
			govtEInvFlags.add(item);
		}
		// init data for Business Units
		this.businessUnits.add((Listitem) ComponentUtil.createNotRequiredListItem());
		Map<String, String> businessUnitMasters = ConfigurableConstants.getBusinessUnits();
		for(Entry<String, String> entry : businessUnitMasters.entrySet()){
			this.businessUnits.add(new Listitem(entry.getKey() + " - " + entry.getValue(), entry.getKey()));
		}
	}
	public void init(){
		Map<String, Object> accountDetails = this.businessHelper.getAccountBusiness().searchAccount(custNo);
		logger.info("*********"+accountDetails);
		acctNo = (Integer)accountDetails.get("acctNo");
		displayCommonFields(accountDetails);
		displayCorpFields(accountDetails);
	}
	private void displayCommonFields(Map<String, Object> accountDetails){
		// common details
		((Label)this.getFellow("custNo")).setValue((String)accountDetails.get("custNo"));
		((Label)this.getFellow("acctStatus")).setValue((String)accountDetails.get("acctStatus"));
		((Label)this.getFellow("acctType")).setValue((String)accountDetails.get("acctType"));
		((Label)this.getFellow("createdDt")).setValue(createdDt);
		((Textbox)this.getFellow("acctName")).setValue((String)accountDetails.get("acctName"));
		((Textbox)this.getFellow("tel")).setValue((String)accountDetails.get("tel"));
		((Textbox)this.getFellow("fax")).setValue((String)accountDetails.get("fax"));
		((Textbox)this.getFellow("nameOnCard")).setValue((String)accountDetails.get("nameOnCard"));
		Listbox eInvoice = (Listbox)this.getFellow("eInvoice");
		for(Object invoiceFlag : eInvoice.getChildren()){
			if(((Listitem)invoiceFlag).getValue().equals(accountDetails.get("eInvoice"))){
				((Listitem)invoiceFlag).setSelected(true);
				break;
			}
		}
		Listbox invoicePrintings = (Listbox)this.getFellow("invoicePrinting");
		for(Object invoicePrinting : invoicePrintings.getChildren()){
			if(((Listitem)invoicePrinting).getValue().equals(accountDetails.get("invoicePrinting"))){
				((Listitem)invoicePrinting).setSelected(true);
				break;
			}
		}
		Listbox outsourcePrinting = (Listbox)this.getFellow("outsourcePrinting");
		for(Object outsourcePrintingFlag : outsourcePrinting.getChildren()){
			if(((Listitem)outsourcePrintingFlag).getValue().equals(accountDetails.get("outsourcePrinting"))){
				((Listitem)outsourcePrintingFlag).setSelected(true);
				break;
			}
		}
		Listbox sms = (Listbox)this.getFellow("sms");
		for(Object smsFlag : sms.getChildren()){
			if(((Listitem)smsFlag).getValue().equals(accountDetails.get("sms"))){
				((Listitem)smsFlag).setSelected(true);
				break;
			}
		}
		
		Listbox recurring = (Listbox)this.getFellow("recurring");
		for(Object recurringFlag : recurring.getChildren()){
			if(((Listitem)recurringFlag).getValue().equals(accountDetails.get("recurring"))){
				((Listitem)recurringFlag).setSelected(true);
				break;
			}
		}
		Listbox recurringChargeDay = (Listbox)this.getFellow("recurringChargeDay");
		if(accountDetails.get("recurringChargeDay")!=null){
			for(Object recurringCharging : recurringChargeDay.getChildren()){
				if(((Listitem)recurringCharging).getValue().equals(NonConfigurableConstants.DAYSwithSuffix.get((accountDetails.get("recurringChargeDay")).toString()))){
					((Listitem)recurringCharging).setSelected(true);
					break;
				}
			}
		}
		
		Listbox smsExpiry = (Listbox)this.getFellow("smsExpiry");
		for(Object smsExpiryFlag : smsExpiry.getChildren()){
			if(((Listitem)smsExpiryFlag).getValue().equals(accountDetails.get("smsExpiry"))){
				((Listitem)smsExpiryFlag).setSelected(true);
				break;
			}
		}
		Listbox smsTopUp = (Listbox)this.getFellow("smsTopUp");
		for(Object smsTopUpFlag : smsTopUp.getChildren()){
			if(((Listitem)smsTopUpFlag).getValue().equals(accountDetails.get("smsTopUp"))){
				((Listitem)smsTopUpFlag).setSelected(true);
				break;
			}
		}
		((Label)this.getFellow("entity")).setValue((String)accountDetails.get("entity"));
		((Label)this.getFellow("arAcct")).setValue((String)accountDetails.get("arAcct"));
		((Label)this.getFellow("approver")).setValue((String)accountDetails.get("approver"));
		((Label)this.getFellow("creditLimit")).setValue((String)accountDetails.get("creditLimit"));
		((Label)this.getFellow("deposit")).setValue((String)accountDetails.get("deposit"));
		((Label)this.getFellow("salesPerson")).setValue((String)accountDetails.get("salesPerson"));
		((Label)this.getFellow("remarks")).setValue((String)accountDetails.get("remarks"));
		Listbox infoSources = (Listbox)this.getFellow("infoSourceList");
		for(Object infoSource : infoSources.getChildren()){
			if(((Listitem)infoSource).getValue().equals(accountDetails.get("infoSourceCode"))){
				((Listitem)infoSource).setSelected(true);
				break;
			}
		}
		Listbox printTaxInvoiceOnlyListBox = (Listbox)this.getFellow("printTaxInvoiceOnlyList");
		for(Object printTaxInvoiceOnlyFlag : printTaxInvoiceOnlyListBox.getChildren()){
			if(((Listitem)printTaxInvoiceOnlyFlag).getValue().equals(accountDetails.get("printTaxInvOnly"))){
				((Listitem)printTaxInvoiceOnlyFlag).setSelected(true);
				break;
			}
		}
		Listbox aceIndicatorListBox = (Listbox)this.getFellow("aceIndicator");
		for(Object aceIndicator : aceIndicatorListBox.getChildren()){
			if(((Listitem)aceIndicator).getValue().equals(accountDetails.get("aceIndicator"))){
				((Listitem)aceIndicator).setSelected(true);
				break;
			}
		}
		logger.info("*********"+accountDetails.get("coupaIndicator"));
		Listbox coupaIndicatorListBox = (Listbox)this.getFellow("coupaIndicator");
		for(Object coupaIndicator : coupaIndicatorListBox.getChildren()){
			if(((Listitem)coupaIndicator).getValue().equals(accountDetails.get("coupaIndicator"))){
				logger.info("*********"+accountDetails.get("coupaIndicator"));
				((Listitem)coupaIndicator).setSelected(true);
				break;
			}
		}
	}
	private void displayCorpFields(Map<String, Object> accountDetails){
		((Textbox)this.getFellow("rcbNo")).setValue((String)accountDetails.get("rcbNo"));
		Listbox industries = (Listbox)this.getFellow("industryList");
		for(Object industry : industries.getChildren()){
			if(((Listitem)industry).getValue().equals(accountDetails.get("industryCode"))){
				((Listitem)industry).setSelected(true);
				break;
			}
		}
		((Datebox)this.getFellow("rcbDate")).setValue((Date)accountDetails.get("rcbDate"));
		//((Datebox)this.getFellow("corpDate")).setValue((Date)accountDetails.get("corpDate"));
		((Decimalbox)this.getFellow("capital")).setValue((BigDecimal)accountDetails.get("capital"));
		((Textbox)this.getFellow("blkNo")).setValue((String)accountDetails.get("blkNo"));
		((Textbox)this.getFellow("unitNo")).setValue((String)accountDetails.get("unitNo"));
		((Textbox)this.getFellow("street")).setValue((String)accountDetails.get("street"));
		((Textbox)this.getFellow("building")).setValue((String)accountDetails.get("building"));
		((Textbox)this.getFellow("area")).setValue((String)accountDetails.get("area"));
		Listbox countries = (Listbox)this.getFellow("countryList");
		for(Object country : countries.getChildren()){
			if(((Listitem)country).getValue().equals(accountDetails.get("countryCode"))){
				((Listitem)country).setSelected(true);
				this.checkCountry((Listitem)country, (Textbox)this.getFellow("city"), (Textbox)this.getFellow("state"));
				break;
			}
		}
		((Textbox)this.getFellow("city")).setValue((String)accountDetails.get("city"));
		((Textbox)this.getFellow("state")).setValue((String)accountDetails.get("state"));
		((Textbox)this.getFellow("postal")).setValue((String)accountDetails.get("postal"));
		Listbox projectCodeList = (Listbox)this.getFellow("projectCodeList");
		for(Object booleanObj : projectCodeList.getItems()){
			if(((Listitem)booleanObj).getValue().equals(accountDetails.get("projectCode"))){
				((Listitem)booleanObj).setSelected(true);
				break;
			}
		}
		Listbox salutations = (Listbox)this.getFellow("salutationList");
		for(Object salutation : salutations.getChildren()){
			if(((Listitem)salutation).getValue().equals(accountDetails.get("salutationCode"))){
				((Listitem)salutation).setSelected(true);
				break;
			}
		}
		((Textbox)this.getFellow("authPerson")).setValue((String)accountDetails.get("authPerson"));
		((Textbox)this.getFellow("authTitle")).setValue((String)accountDetails.get("authTitle"));
		Listbox invoiceFormats = (Listbox)this.getFellow("invoiceFormatList");
		for(Object invoiceFormat : invoiceFormats.getChildren()){
			if(((Listitem)invoiceFormat).getValue().equals(accountDetails.get("invoiceFormatCode"))){
				((Listitem)invoiceFormat).setSelected(true);
				break;
			}
		}
		Listbox invoiceSortings = (Listbox)this.getFellow("invoiceSortingList");
		for(Object invoiceSorting : invoiceSortings.getChildren()){
			if(((Listitem)invoiceSorting).getValue().equals(accountDetails.get("invoiceSortingCode"))){
				((Listitem)invoiceSorting).setSelected(true);
				break;
			}
		}
		// Govt eInvoice Enhancement
		Listbox govtEInvFlagList = (Listbox)this.getFellow("govtEInvFlagList");
		for(Object govtEInvFlag : govtEInvFlagList.getChildren()){
			if(((Listitem)govtEInvFlag).getValue().equals(accountDetails.get("govtEInvFlag"))){
				((Listitem)govtEInvFlag).setSelected(true);
				break;
			}
		}
		Listbox businessUnitList = (Listbox)this.getFellow("businessUnitList");
		MstbMasterTable businessUnitMaster = (MstbMasterTable) accountDetails.get("businessUnit");
		if (businessUnitMaster != null) {
			for (Object businessUnit : businessUnitList.getChildren()) {
				if (((Listitem) businessUnit).getValue().equals(businessUnitMaster.getMasterCode())) {
					((Listitem) businessUnit).setSelected(true);
					break;
				}
			}
		}
		
		Listbox pubbsFlagList = (Listbox)this.getFellow("pubbsFlagList");
		for(Object pubbsFlagListFlag : pubbsFlagList.getChildren()){
			if(((Listitem)pubbsFlagListFlag).getValue().equals(accountDetails.get("pubbsFlag"))){
				((Listitem)pubbsFlagListFlag).setSelected(true);
				break;
			}
		}
		Listbox fiFlagList = (Listbox)this.getFellow("fiFlagList");
		for(Object fiFlagListFlag : fiFlagList.getChildren()){
			if(((Listitem)fiFlagListFlag).getValue().equals(accountDetails.get("fiFlag"))){
				((Listitem)fiFlagListFlag).setSelected(true);
				break;
			}
		}
	}
	public List<Listitem> getCountries(){
		return this.countries;
	}
	public List<Listitem> getIndustries(){
		return this.industries;
	}
	public List<Listitem> getSalutations(){
		return this.salutations;
	}
	public List<Listitem> getInfoSources(){
		return this.infoSources;
	}
	public List<Listitem> getInvoiceFormats(){
		return this.invoiceFormats;
	}
	public List<Listitem> getInvoiceSorts(){
		return this.invoiceSorts;
	}
	public List<Listitem> getGovtEInvFlags(){
		return this.govtEInvFlags;
	}
	public List<Listitem> getBusinessUnits(){
		return this.businessUnits;
	}
	public List<Listitem> getDaysUnits(){
		return this.daysUnits;
	}
	
	public void save() throws InterruptedException{
		logger.info("save()");

		try{
			if(Messagebox.show("Save Account?", "Edit Corporate", Messagebox.OK | Messagebox.CANCEL, Messagebox.QUESTION)==Messagebox.OK){
				
				Map<String, Object> details = getScreenInput();
				details.put("custNo", custNo);
				String userId = this.getUserLoginIdAndDomain();
				details.put("userId", userId);
			
				if(NonConfigurableConstants.GOVT_EINV_FLAG_NO.equals((String)details.get("govtEInvFlag"))
						&& this.businessHelper.getAccountBusiness().containGovtEInvoice(custNo, acctNo))
				{
					if(Messagebox.show("Set Government e-Invoice before proceeding?", "Warning", Messagebox.OK | Messagebox.CANCEL, Messagebox.INFORMATION)==Messagebox.OK){
						return;
					}
				}
				if(NonConfigurableConstants.BOOLEAN_NO.equals((String)details.get("pubbsFlag"))
						&& this.businessHelper.getAccountBusiness().containPubbs(custNo, acctNo))
				{
					if(Messagebox.show("Set PUBBS Billing before proceeding?", "Warning", Messagebox.OK | Messagebox.CANCEL, Messagebox.INFORMATION)==Messagebox.OK){
						return;
					}
				}
				if(NonConfigurableConstants.BOOLEAN_NO.equals((String)details.get("fiFlag"))
						&& this.businessHelper.getAccountBusiness().containFi(custNo, acctNo))
				{
					if(Messagebox.show("Set Fi Flag before proceeding?", "Warning", Messagebox.OK | Messagebox.CANCEL, Messagebox.INFORMATION)==Messagebox.OK){
						return;
					}
				}
				if(!NonConfigurableConstants.GOVT_EINV_FLAG_NO.equals((String)details.get("govtEInvFlag")) &&
						!NonConfigurableConstants.PUBBS_FLAG_NO.equals((String)details.get("pubbsFlag")))
				{
					Messagebox.show("Government eInvoice & PUBBS should not double invoice", "Edit Corporate", Messagebox.OK, Messagebox.ERROR);
					return;
				}
				
				
				if(this.businessHelper.getAccountBusiness().updateCorpWithCNII(details)){
					Messagebox.show("Account saved.", "Edit Corporate", Messagebox.OK, Messagebox.INFORMATION);
					this.back();
				}else{
					Messagebox.show("Unable to save. Please try again later.", "Edit Corporate", Messagebox.OK, Messagebox.ERROR);
				}
			}
		}catch(CniiInterfaceException e){
			Messagebox.show(e.getMessage(), "Edit Corporate", Messagebox.OK, Messagebox.INFORMATION);
		}catch(IBSException e){
			Messagebox.show(e.getMessage(), "Edit Corporate", Messagebox.OK, Messagebox.INFORMATION);
		}
	}
	@Override
	public void refresh() throws InterruptedException {
		init();
	}
	private Map<String, Object> getScreenInput() throws IBSException{
		Map<String, Object> details = new HashMap<String, Object>();
		String acctName = ((Textbox)this.getFellow("acctName")).getValue();
		details.put("acctName", acctName);
		String rcbNo = ((Textbox)this.getFellow("rcbNo")).getValue();
		details.put("rcbNo", rcbNo);
		String industryCode = (String)((Listbox)this.getFellow("industryList")).getSelectedItem().getValue();
		details.put("industryCode", industryCode);
		Date rcbDate = ((Datebox)this.getFellow("rcbDate")).getValue();
		details.put("rcbDate", rcbDate);
//		Date corpDate = ((Datebox)this.getFellow("corpDate")).getValue();
//		details.put("corpDate", corpDate);
		BigDecimal capital = ((Decimalbox)this.getFellow("capital")).getValue();
		details.put("capital", capital);
		String tel = ((Textbox)this.getFellow("tel")).getValue();
		details.put("tel", tel);
		String fax = ((Textbox)this.getFellow("fax")).getValue();
		details.put("fax", fax);
		String nameOnCard = ((Textbox)this.getFellow("nameOnCard")).getValue();
		details.put("nameOnCard", nameOnCard);
		String eInvoice = (String)((Listbox)this.getFellow("eInvoice")).getSelectedItem().getValue();
		details.put("eInvoice", eInvoice);
		String invoicePrinting = (String)((Listbox)this.getFellow("invoicePrinting")).getSelectedItem().getValue();
		details.put("invoicePrinting", invoicePrinting);
		String outsourcePrinting = (String)((Listbox)this.getFellow("outsourcePrinting")).getSelectedItem().getValue();
		details.put("outsourcePrinting", outsourcePrinting);
		String sms = (String)((Listbox)this.getFellow("sms")).getSelectedItem().getValue();
		details.put("sms", sms);
		
		String recurring = (String)((Listbox)this.getFellow("recurring")).getSelectedItem().getValue();
		if(recurring.isEmpty() || recurring.equals("-")){
			throw new IBSException(
					"Recurring flag cannot be blank");
		}else {
			details.put("recurring", recurring);
		}
		String recurringChargeDay = (String)((Listbox)this.getFellow("recurringChargeDay")).getSelectedItem().getValue();
		if(recurring.equals("Y"))
		{
			if(recurringChargeDay.isEmpty() || recurringChargeDay.equals("-") ){
				throw new IBSException(
						"recurringChargeDay cannot be blank");
			}
		}
		details.put("recurringChargeDay", recurringChargeDay);
		
		String smsExpiry = (String)((Listbox)this.getFellow("smsExpiry")).getSelectedItem().getValue();
		details.put("smsExpiry", smsExpiry);
		String smsTopUp = (String)((Listbox)this.getFellow("smsTopUp")).getSelectedItem().getValue();
		details.put("smsTopUp", smsTopUp);
		String blkNo = ((Textbox)this.getFellow("blkNo")).getValue();
		details.put("blkNo", blkNo);
		String unitNo = ((Textbox)this.getFellow("unitNo")).getValue();
		details.put("unitNo", unitNo);
		String street = ((Textbox)this.getFellow("street")).getValue();
		details.put("street", street);
		String building = ((Textbox)this.getFellow("building")).getValue();
		details.put("building", building);
		String area = ((Textbox)this.getFellow("area")).getValue();
		details.put("area", area);
		String countryCode = (String)((Listbox)this.getFellow("countryList")).getSelectedItem().getValue();
		details.put("countryCode", countryCode);
		String city = ((Textbox)this.getFellow("city")).getValue();
		details.put("city", city);
		String state = ((Textbox)this.getFellow("state")).getValue();
		details.put("state", state);
		String postal = ((Textbox)this.getFellow("postal")).getValue();
		details.put("postal", postal);
		String projectCode = (String)((Listbox)this.getFellow("projectCodeList")).getSelectedItem().getValue();
		details.put("projectCode", projectCode);
		String salutationCode = (String)((Listbox)this.getFellow("salutationList")).getSelectedItem().getValue();
		details.put("salutationCode", salutationCode);
		String authPerson = ((Textbox)this.getFellow("authPerson")).getValue();
		details.put("authPerson", authPerson);
		String authTitle = ((Textbox)this.getFellow("authTitle")).getValue();
		details.put("authTitle", authTitle);
		String infoSourceCode = (String)((Listbox)this.getFellow("infoSourceList")).getSelectedItem().getValue();
		details.put("infoSourceCode", infoSourceCode);
		String invoiceFormat = (String)((Listbox)this.getFellow("invoiceFormatList")).getSelectedItem().getValue();
		details.put("invoiceFormat", invoiceFormat);
		String invoiceSorting = (String)((Listbox)this.getFellow("invoiceSortingList")).getSelectedItem().getValue();
		details.put("invoiceSorting", invoiceSorting);
		// Govt eInv Enhancement
		String govtEInvFlag = (String)((Listbox)this.getFellow("govtEInvFlagList")).getSelectedItem().getValue();
		details.put("govtEInvFlag", govtEInvFlag);
		String businessUnit = (String)((Listbox)this.getFellow("businessUnitList")).getSelectedItem().getValue();
		System.out.println("businessUnit:" + businessUnit);
		if(govtEInvFlag.equals(NonConfigurableConstants.GOVT_EINV_FLAG_NO) && (businessUnit != null && businessUnit.length()>0))
			throw new IBSException(
					"Please unselect Business Unit as it is not applicable for accounts opt for NO in Government eInvoice");
		else if(!govtEInvFlag.equals(NonConfigurableConstants.GOVT_EINV_FLAG_NO) && (businessUnit == null || businessUnit.length()==0))
			throw new IBSException(
					"Business Unit is required for accounts opt for Government eInvoice");
		details.put("businessUnit", businessUnit);
		String printTaxInvoiceOnly = (String)((Listbox)this.getFellow("printTaxInvoiceOnlyList")).getSelectedItem().getValue();
		details.put("printTaxInvoiceOnly", printTaxInvoiceOnly);
		String aceIndicator = (String)((Listbox)this.getFellow("aceIndicator")).getSelectedItem().getValue();
		details.put("aceIndicator", aceIndicator);
		
		String coupaIndicator = (String)((Listbox)this.getFellow("coupaIndicator")).getSelectedItem().getValue();
		details.put("coupaIndicator", coupaIndicator);
		
		String pubbsFlag = (String)((Listbox)this.getFellow("pubbsFlagList")).getSelectedItem().getValue();
		details.put("pubbsFlag", pubbsFlag);

		String fiFlag = (String)((Listbox)this.getFellow("fiFlagList")).getSelectedItem().getValue();
		details.put("fiFlag", fiFlag);

		return details;
	}
	public List<Listitem> getBooleanList() {
		return cloneList(booleanList);
	}
	public void setBooleanList(List<Listitem> booleanList) {
		this.booleanList = booleanList;
	}
	public List<Listitem> getOptionalBooleanList() {
		return cloneList(optionalBooleanList);
	}
	public void setOptionalList(List<Listitem> optionalBooleanList) {
		this.optionalBooleanList = optionalBooleanList;
	}
	
	public List<Listitem> getInvoicePrintingList() {
		
		List<Listitem> invoicePrintingList = new ArrayList<Listitem>();
		invoicePrintingList.add(new Listitem(NonConfigurableConstants.INVOICE_PRINTING.get(NonConfigurableConstants.INVOICE_PRINTING_DOUBLE_SIDED), NonConfigurableConstants.INVOICE_PRINTING_DOUBLE_SIDED));
		invoicePrintingList.add(new Listitem(NonConfigurableConstants.INVOICE_PRINTING.get(NonConfigurableConstants.INVOICE_PRINTING_SINGLE_SIDED), NonConfigurableConstants.INVOICE_PRINTING_SINGLE_SIDED));
		return invoicePrintingList;
	}
	public void viewStatusHistory() throws SuspendNotAllowedException, InterruptedException{
		logger.info("viewStatusHistory()");
		super.viewStatusHistory(custNo, null, null, null);
	}
}