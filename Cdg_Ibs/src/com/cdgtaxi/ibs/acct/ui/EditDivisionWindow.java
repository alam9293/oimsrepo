package com.cdgtaxi.ibs.acct.ui;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Decimalbox;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Row;
import org.zkoss.zul.Rows;
import org.zkoss.zul.Textbox;

import com.cdgtaxi.ibs.common.NonConfigurableConstants;
import com.cdgtaxi.ibs.common.exception.IBSException;
import com.cdgtaxi.ibs.interfaces.cnii.CniiInterfaceException;
import com.cdgtaxi.ibs.master.model.MstbMasterTable;
import com.cdgtaxi.ibs.util.ComponentUtil;

public class EditDivisionWindow extends AddDivisionWindow {
	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(EditDivisionWindow.class);
	private String custNo, acctStatus, code;
	private Integer acctNo;
	private List<Listitem> booleanList = new ArrayList<Listitem>();
	private List<Listitem> daysUnits = new ArrayList<Listitem>();
	private List<Listitem> optionalBooleanList = new ArrayList<Listitem>();
	@SuppressWarnings("unchecked")
	public EditDivisionWindow() throws InterruptedException{
		super();
		logger.info("EditDivisionWindow()");
		//customer number
		Map<String, String> map = Executions.getCurrent().getArg();
		custNo = map.get("custNo");
		if(custNo==null || custNo.trim().length()==0){
			Messagebox.show("No account number found!", "Edit Division", Messagebox.OK, Messagebox.ERROR);
		}
		// account status
		acctStatus = map.get("acctStatus");
		if(acctStatus==null || acctStatus.trim().length()==0){
			Messagebox.show("No account status found!", "Edit Division", Messagebox.OK, Messagebox.ERROR);
		}
		// account code
		code = map.get("code");
		if(code==null || code.trim().length()==0){
			Messagebox.show("No account code found!", "Edit Division", Messagebox.OK, Messagebox.ERROR);
		}
		// getting data from non configurable constants
		// getting boolean
		booleanList.add(new Listitem(NonConfigurableConstants.BOOLEAN.get(NonConfigurableConstants.BOOLEAN_NO), NonConfigurableConstants.BOOLEAN_NO));
		booleanList.add(new Listitem(NonConfigurableConstants.BOOLEAN.get(NonConfigurableConstants.BOOLEAN_YES), NonConfigurableConstants.BOOLEAN_YES));
		optionalBooleanList.add((Listitem)ComponentUtil.createNotRequiredListItem());
		optionalBooleanList.add(new Listitem(NonConfigurableConstants.BOOLEAN.get(NonConfigurableConstants.BOOLEAN_NO), NonConfigurableConstants.BOOLEAN_NO));
		optionalBooleanList.add(new Listitem(NonConfigurableConstants.BOOLEAN.get(NonConfigurableConstants.BOOLEAN_YES), NonConfigurableConstants.BOOLEAN_YES));	
		Map<String, String> masterDays = NonConfigurableConstants.DAYSwithSuffix;
		for(String masterCode : masterDays.keySet()){
			this.daysUnits.add(new Listitem(masterCode, masterDays.get(masterCode)));
		}
	}
	@SuppressWarnings("unchecked")
	public void init() throws InterruptedException{
		logger.info("init()");
		super.init();
		if(acctStatus.equals(NonConfigurableConstants.ACCOUNT_STATUS.get(NonConfigurableConstants.ACCOUNT_STATUS_PENDING_ACTIVATION))){
			((Textbox)this.getFellow("code")).setDisabled(false);
			this.getFellow("deleteBtn").setVisible(true);
			((Decimalbox)this.getFellow("creditLimit")).setDisabled(false);
		}else{
			((Textbox)this.getFellow("code")).setDisabled(true);
			((Decimalbox)this.getFellow("creditLimit")).setDisabled(true);
		}
		Map<String, Object> acctDetails = this.businessHelper.getAccountBusiness().getAccount(custNo, 1, null, code);
		this.acctNo = (Integer)acctDetails.get("acctNo");
		Textbox codeText = (Textbox)this.getFellow("code");
		codeText.setValue((String)acctDetails.get("acctCode"));
		Textbox acctNameText = (Textbox)this.getFellow("acctName");
		acctNameText.setValue((String)acctDetails.get("acctName"));
		Textbox nameOnCardText = (Textbox)this.getFellow("nameOnCard");
		nameOnCardText.setValue((String)acctDetails.get("nameOnCard"));
		Decimalbox creditLimitDecimal = (Decimalbox)this.getFellow("creditLimit");
		creditLimitDecimal.setValue((BigDecimal)acctDetails.get("creditLimit"));
		if(acctDetails.get("invoiceFormat")!=null){
			Listbox chargeToBox = (Listbox)this.getFellow("chargeTo");
			List<Listitem> chargeToItems = chargeToBox.getChildren();
			for(Listitem chargeToItem : chargeToItems){
				if(chargeToItem.getValue().equals(NonConfigurableConstants.INVOICE_CHARGE_TO_SELF)){
					chargeToItem.setSelected(true);
					this.checkChargeTo(chargeToItem);
					break;
				}
			}
			Listbox invoiceFormatBox = (Listbox)this.getFellow("invoiceFormat");
			List<Listitem> invoiceFormatItems = invoiceFormatBox.getChildren();
			for(Listitem invoiceFormatItem : invoiceFormatItems){
				if(invoiceFormatItem.getValue().equals(acctDetails.get("invoiceFormat"))){
					invoiceFormatItem.setSelected(true);
					break;
				}
			}
			Listbox invoiceSortingBox = (Listbox)this.getFellow("invoiceSorting");
			List<Listitem> invoiceSortingItems = invoiceSortingBox.getChildren();
			for(Listitem invoiceSortingItem : invoiceSortingItems){
				if(invoiceSortingItem.getValue().equals(acctDetails.get("invoiceSorting"))){
					invoiceSortingItem.setSelected(true);
					break;
				}
			}
			Listbox printTaxInvoiceOnlyListBox = (Listbox)this.getFellow("printTaxInvoiceOnlyList");
			List<Listitem> printTaxInvoiceOnlyListItems = printTaxInvoiceOnlyListBox.getChildren();
			for(Listitem printTaxInvoiceOnlyListItem : printTaxInvoiceOnlyListItems){
				if(printTaxInvoiceOnlyListItem.getValue().equals(acctDetails.get("printTaxInvOnly"))){
					printTaxInvoiceOnlyListItem.setSelected(true);
					break;
				}
			}
		}
		Listbox billingContactBox = (Listbox)this.getFellow("billingContact");
		List<Listitem> billingContactItems = billingContactBox.getChildren();
		for(Listitem billingContactItem : billingContactItems){
			if(billingContactItem.getValue().equals(acctDetails.get("mainBilling"))){
				billingContactItem.setSelected(true);
				break;
			}
		}
		if(acctDetails.get("mainShipping")!=null){
			Listbox shippingContactBox = (Listbox)this.getFellow("shippingContact");
			List<Listitem> shippingContactItems = shippingContactBox.getChildren();
			for(Listitem shippingContactItem : shippingContactItems){
				if(shippingContactItem.getValue().equals(acctDetails.get("mainShipping"))){
					shippingContactItem.setSelected(true);
					break;
				}
			}
		}
		Rows rows = (Rows)this.getFellow("prodSubscriptions");
		Set<String> subscriptions = (Set<String>)acctDetails.get("productSubscriptions");
		for(Object rowObj : rows.getChildren()){
			Row row = (Row)rowObj;
			if(!row.equals(rows.getLastChild())){
				String subscribed = subscriptions.contains(row.getValue()) ? NonConfigurableConstants.BOOLEAN_YN_YES : NonConfigurableConstants.BOOLEAN_YN_NO;
				Listbox booleanList = (Listbox)row.getChildren().get(row.getChildren().size()-1);
				for(Object booleanObj : booleanList.getChildren()){
					Listitem booleanItem = (Listitem)booleanObj;
					if(booleanItem.getValue().equals(subscribed)){
						booleanItem.setSelected(true);
						break;
					}
				}
			}
		}
		
		// Govt eInvoice Enhancement
		Listbox govtEInvFlagList = (Listbox)this.getFellow("govtEInvFlagList");
		for(Object govtEInvFlag : govtEInvFlagList.getChildren()){
			if(((Listitem)govtEInvFlag).getValue().equals(acctDetails.get("govtEInvFlag"))){
				((Listitem)govtEInvFlag).setSelected(true);
				break;
			}
		}
		Listbox businessUnitList = (Listbox)this.getFellow("businessUnitList");
		MstbMasterTable businessUnitMaster = (MstbMasterTable) acctDetails.get("businessUnit");
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
			if(((Listitem)pubbsFlagListFlag).getValue().equals(acctDetails.get("pubbsFlag"))){
				((Listitem)pubbsFlagListFlag).setSelected(true);
				break;
			}
		}
		Listbox fiFlagList = (Listbox)this.getFellow("fiFlagList");
		for(Object fiFlagListFlag : fiFlagList.getChildren()){
			if(((Listitem)fiFlagListFlag).getValue().equals(acctDetails.get("fiFlag"))){
				((Listitem)fiFlagListFlag).setSelected(true);
				break;
			}
		}
		Listbox recurring = (Listbox)this.getFellow("recurring");
		for(Object recurringFlag : recurring.getChildren()){
			if(((Listitem)recurringFlag).getValue().equals(acctDetails.get("recurring"))){
				((Listitem)recurringFlag).setSelected(true);
				break;
			}
		}
		Listbox recurringChargeDay = (Listbox)this.getFellow("recurringChargeDay");
		for(Object recurringCharging : recurringChargeDay.getChildren()){
			if(((Listitem)recurringCharging).getValue().equals(acctDetails.get("recurringChargeDay")+ "th")){
				((Listitem)recurringCharging).setSelected(true);
				break;
			}
		}		
	}
	public void save() throws InterruptedException, CniiInterfaceException{
		logger.info("save()");
		try
		{
		if(Messagebox.show("Save Division?", "Edit Division", Messagebox.OK | Messagebox.CANCEL, Messagebox.QUESTION)==Messagebox.OK){
			Map<String, Object> accountDetails = new HashMap<String, Object>();
			// subscription
			Rows prodSubscriptions = (Rows)this.getFellow("prodSubscriptions");
			List<String> prodSubs = new ArrayList<String>();
			for(Object prodSubscription : prodSubscriptions.getChildren()){
				if(!prodSubscription.equals(prodSubscriptions.getLastChild())){// filter last button row
					Row row = (Row)prodSubscription;
					// now getting last cell of row
					Listbox subscribedList = (Listbox)row.getLastChild();
					// only add if selection is yes
					if(subscribedList.getSelectedItem().getValue().equals(NonConfigurableConstants.BOOLEAN_YN_YES)){
						String prodTypeId = (String)row.getValue();
						prodSubs.add(prodTypeId);
					}
				}
			}
			
			if(prodSubs.isEmpty()){
				Messagebox.show("No product subscriptions!", "Edit Division", Messagebox.OK, Messagebox.EXCLAMATION);
				return;
			}
			accountDetails.put("prodSubscriptions", prodSubs);
			accountDetails.put("acctNo", acctNo);
			//check product if can approve Subscription
			if(this.businessHelper.getAccountBusiness().hasPendApproveSubscriptionCorpSubAccount(accountDetails)) {
				Messagebox.show("There is currently a subscription for this Product Type that is pending for approval.", "Edit Subscription", Messagebox.OK, Messagebox.ERROR);			
				return;
			}
//			else
//				Messagebox.show("Subcribe/Unsubcribe will be sent for approval if there are any changes to the subscription.", "Edit Subscription", Messagebox.OK, Messagebox.INFORMATION);			
			
			// checking credit limit
			BigDecimal divCreditLimit = ((Decimalbox)this.getFellow("creditLimit")).getValue();
			accountDetails.put("creditLimit", divCreditLimit);
			if(this.businessHelper.getAccountBusiness().checkParentCreditLimit(custNo, null, divCreditLimit)){
				Messagebox.show("Credit Limit is greater than Corporate!", "Create Division", Messagebox.OK, Messagebox.ERROR);
				return;
			}
			String divCode = ((Textbox)this.getFellow("code")).getValue();
			accountDetails.put("acctCode", divCode);
			// checking code
			if(!code.equals(divCode)){
				if(this.businessHelper.getAccountBusiness().checkCode(custNo, NonConfigurableConstants.DIVISION_LEVEL, null, divCode)){
					Messagebox.show("Division code already in used! Please use another code", "Create Division", Messagebox.OK, Messagebox.ERROR);
					return;
				}
			}
			// account
//			accountDetails.put("acctNo", acctNo);
			accountDetails.put("userId", getUserLoginIdAndDomain());
			String divName = ((Textbox)this.getFellow("acctName")).getValue();
			accountDetails.put("acctName", divName);
			String divNameOnCard = ((Textbox)this.getFellow("nameOnCard")).getValue();
			accountDetails.put("nameOnCard", divNameOnCard);
			accountDetails.put("custNo", custNo);
			// charge to
			Listbox chargeToList = (Listbox)this.getFellow("chargeTo");
			Listitem chargeToItem = chargeToList.getSelectedItem();
			if(chargeToItem.getValue().equals(NonConfigurableConstants.INVOICE_CHARGE_TO_SELF)){
				Listbox invoiceFormat = (Listbox)this.getFellow("invoiceFormat");
				accountDetails.put("invoiceFormat", invoiceFormat.getSelectedItem().getValue());
				Listbox invoiceSorting = (Listbox)this.getFellow("invoiceSorting");
				accountDetails.put("invoiceSorting", invoiceSorting.getSelectedItem().getValue());
				// Govt eInv Enhancement
				String govtEInvFlag = (String)((Listbox)this.getFellow("govtEInvFlagList")).getSelectedItem().getValue();
				accountDetails.put("govtEInvFlag", govtEInvFlag);
				String businessUnit = (String)((Listbox)this.getFellow("businessUnitList")).getSelectedItem().getValue();
				if(govtEInvFlag.equals(NonConfigurableConstants.GOVT_EINV_FLAG_NO) && (businessUnit != null && businessUnit.length()>0))
					throw new IBSException(
							"Please unselect Business Unit as it is not applicable for accounts opt for NO in Government eInvoice");
				else if(!govtEInvFlag.equals(NonConfigurableConstants.GOVT_EINV_FLAG_NO) && (businessUnit == null || businessUnit.length()==0))
					throw new IBSException(
							"Business Unit is required for accounts opt for Government eInvoice");
				accountDetails.put("businessUnit", businessUnit);
				
				String printTaxInvoiceOnly = (String)((Listbox)this.getFellow("printTaxInvoiceOnlyList")).getSelectedItem().getValue();
				accountDetails.put("printTaxInvoiceOnly", printTaxInvoiceOnly);
				
				String pubbsFlag = (String)((Listbox)this.getFellow("pubbsFlagList")).getSelectedItem().getValue();
				accountDetails.put("pubbsFlag", pubbsFlag);
				
				String fiFlag = (String)((Listbox)this.getFellow("fiFlagList")).getSelectedItem().getValue();
				accountDetails.put("fiFlag", fiFlag);
				
				String recurring = (String)((Listbox)this.getFellow("recurring")).getSelectedItem().getValue();
				
				if(recurring.isEmpty() || recurring.equals("-")){
					throw new IBSException(
							"Recurring flag cannot be blank");
				}else {
					accountDetails.put("recurring", recurring);
				}
				
				String recurringChargeDay = (String)((Listbox)this.getFellow("recurringChargeDay")).getSelectedItem().getValue();
				if(recurring.equals("Y"))
				{
					if(recurringChargeDay.isEmpty() || recurringChargeDay.equals("-") ){
						throw new IBSException(
								"recurringChargeDay cannot be blank");
					}
				}
				accountDetails.put("recurringChargeDay", recurringChargeDay);
				
			}
			else{
				accountDetails.put("printTaxInvoiceOnly", NonConfigurableConstants.BOOLEAN_NO);
				accountDetails.put("govtEInvFlag", NonConfigurableConstants.GOVT_EINV_FLAG_NO);
				accountDetails.put("businessUnit", null);
				accountDetails.put("pubbsFlag", NonConfigurableConstants.BOOLEAN_NO);
				accountDetails.put("fiFlag", NonConfigurableConstants.BOOLEAN_NO);
				accountDetails.put("recurring", NonConfigurableConstants.BOOLEAN_NO);
				accountDetails.put("recurringChargeDay", null);
			}
			
			if(NonConfigurableConstants.GOVT_EINV_FLAG_NO.equals((String)accountDetails.get("govtEInvFlag"))
					&& this.businessHelper.getAccountBusiness().containGovtEInvoice(custNo, acctNo))
			{
				if(Messagebox.show("Set Government e-Invoice before proceeding?", "Warning", Messagebox.OK | Messagebox.CANCEL, Messagebox.INFORMATION)==Messagebox.OK){
					return;
				}
			}
			if(!NonConfigurableConstants.GOVT_EINV_FLAG_NO.equals((String)accountDetails.get("govtEInvFlag")) &&
					!NonConfigurableConstants.BOOLEAN_NO.equals((String)accountDetails.get("pubbsFlag")))
			{
				Messagebox.show("Government eInvoice & PUBBS should not double invoice", "Edit Division", Messagebox.OK, Messagebox.ERROR);
				return;
			}
			
			// main contact
			Listbox billingContact = (Listbox)this.getFellow("billingContact");
			accountDetails.put("billingContact", billingContact.getSelectedItem().getValue());
			Listbox shippingContact = (Listbox)this.getFellow("shippingContact");
			Listitem shippingContactItem = shippingContact.getSelectedItem();
			if(shippingContactItem!=null && !shippingContactItem.getValue().equals("")){
				accountDetails.put("shippingContact", shippingContactItem.getValue());
			} else {
				accountDetails.put("shippingContact", billingContact.getSelectedItem().getValue());
			}
			
			HashMap result = this.businessHelper.getAccountBusiness().updateCorpSubAccount(accountDetails);
			
			if(null != result.get("approve") && result.get("approve").toString().trim().equals("true"))
				Messagebox.show("Subscribe/Unsubscribe will be sent for approval.", "Edit Division", Messagebox.OK, Messagebox.INFORMATION);
			if(null != result.get("result") && result.get("result").toString().trim().equals("true")){
				Messagebox.show("Division Saved.", "Edit Division", Messagebox.OK, Messagebox.INFORMATION);
				this.back();
			}else{
				Messagebox.show("Unable to save division! Please try again later", "Edit Division", Messagebox.OK, Messagebox.ERROR);
			}
		}
		}
		catch(CniiInterfaceException e) {
			Messagebox.show(e.getMessage(), "Edit Division", Messagebox.OK , Messagebox.INFORMATION);
		}
		catch(IBSException e){
			Messagebox.show(e.getMessage(), "Edit Division", Messagebox.OK , Messagebox.INFORMATION);
		}
	}
	public void delete() throws InterruptedException{
		if(Messagebox.show("Confirm Delete? All department under this division will be deleted also!", "Edit Division", Messagebox.OK | Messagebox.CANCEL, Messagebox.QUESTION) == Messagebox.OK){
			this.businessHelper.getAccountBusiness().deleteSubAccount(acctNo, getUserLoginIdAndDomain());
			Messagebox.show("Division deleted", "Edit Division", Messagebox.OK , Messagebox.INFORMATION);
			this.back();
		}
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
	public List<Listitem> getDaysUnits(){
		return this.daysUnits;
	}
}