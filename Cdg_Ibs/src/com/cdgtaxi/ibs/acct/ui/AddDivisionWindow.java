package com.cdgtaxi.ibs.acct.ui;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.log4j.Logger;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Decimalbox;
import org.zkoss.zul.Label;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Row;
import org.zkoss.zul.Rows;
import org.zkoss.zul.Textbox;

import com.cdgtaxi.ibs.common.ConfigurableConstants;
import com.cdgtaxi.ibs.common.NonConfigurableConstants;
import com.cdgtaxi.ibs.common.exception.IBSException;
import com.cdgtaxi.ibs.interfaces.cnii.CniiInterfaceException;
import com.cdgtaxi.ibs.util.ComponentUtil;

public class AddDivisionWindow extends CommonInnerAcctWindow {
	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(AddDivisionWindow.class);
	private List<Listitem> optionalBooleanList = new ArrayList<Listitem>();
	private List<Listitem> daysUnits = new ArrayList<Listitem>();
	private List<Listitem> chargeTos = ComponentUtil.convertToListitems(NonConfigurableConstants.INVOICE_CHARGE_TO, true);
	private List<Listitem> invoiceFormats = ComponentUtil.convertToListitems(NonConfigurableConstants.INVOICE_FORMAT, true);
	private List<Listitem> invoiceSortings = ComponentUtil.convertToListitems(NonConfigurableConstants.INVOICE_SORTING, true);
	private List<Listitem> govtEInvFlags = ComponentUtil.convertToListitems(NonConfigurableConstants.GOVT_EINV_FLAGS, true);
	private List<Listitem> businessUnits = new ArrayList<Listitem>();
	private List<Listitem> pubbsFlags = ComponentUtil.convertToListitems(NonConfigurableConstants.BOOLEAN_YN, true);
	private List<Listitem> fiFlags = ComponentUtil.convertToListitems(NonConfigurableConstants.BOOLEAN_YN, true);
	private String custNo, acctStatus;

	@SuppressWarnings("unchecked")
	public AddDivisionWindow() throws InterruptedException{
		logger.info("AddDivisionWindow()");
		//customer number
		Map<String, String> map = Executions.getCurrent().getArg();
		custNo = map.get("custNo");
		if(custNo==null || custNo.trim().length()==0){
			Messagebox.show("No account number found!", "Create Division", Messagebox.OK, Messagebox.ERROR);
		}
		// account status
		acctStatus = map.get("acctStatus");
		if(acctStatus==null || acctStatus.trim().length()==0){
			Messagebox.show("No account status found!", "Create Division", Messagebox.OK, Messagebox.ERROR);
		}
		// getting data from non configurable constants
		// getting boolean
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
		// checking
		if(custNo==null || custNo.trim().length()==0){
			this.back();
			return;
		}else if(acctStatus==null || acctStatus.trim().length()==0){
			this.back();
			return;
		}
		// initing the contacts
		Map<Integer, Map<String, String>> contacts = this.businessHelper.getAccountBusiness().searchContacts(custNo, null, null, null, null);
		if(contacts.isEmpty()){
			Messagebox.show("No contact person! Please create contact person first.", "Create Division", Messagebox.OK, Messagebox.ERROR);
			this.back();
			return;
		}
		// getting name on card
		Map<String, Object> parentDetail = this.businessHelper.getAccountBusiness().getAccount(custNo, NonConfigurableConstants.CORPORATE_LEVEL, null, null);
		((Textbox)this.getFellow("nameOnCard")).setValue((String)parentDetail.get("nameOnCard"));
		((Decimalbox)this.getFellow("creditLimit")).setValue((BigDecimal)parentDetail.get("creditLimit"));
		Listbox billingContactsList = (Listbox)this.getFellow("billingContact");
		Listbox shippingContactsList = (Listbox)this.getFellow("shippingContact");
		Map<String, Integer> mainContacts = this.businessHelper.getAccountBusiness().getAccountMainContact(custNo);
		for(Integer contactNo : contacts.keySet()){
			Listitem newBillingContactItem = new Listitem(contacts.get(contactNo).get("mainContactName"),contactNo);
			if(contactNo.equals(mainContacts.get("mainBilling"))){
				newBillingContactItem.setSelected(true);
			}
			billingContactsList.appendChild(newBillingContactItem);
			Listitem newShippingContactItem = new Listitem(contacts.get(contactNo).get("mainContactName"),contactNo);
			if(contactNo.equals(mainContacts.get("mainShipping"))){
				newShippingContactItem.setSelected(true);
			}
			shippingContactsList.appendChild(newShippingContactItem);
		}
		if(mainContacts.get("mainBilling")==null){
			billingContactsList.setSelectedIndex(0);
		}
		if(mainContacts.get("mainShipping")==null){
			shippingContactsList.setSelectedIndex(0);
		}
		// initing the product subscriptions
		Rows rows = (Rows)this.getFellow("prodSubscriptions");
		Set<Map<String, String>> prodSubscriptions = this.businessHelper.getAccountBusiness().getProductSubscriptions(custNo, null);
		for(Map<String, String> prodSubscription : prodSubscriptions){
			Row row = new Row();
			row.appendChild(new Label(prodSubscription.get("prodType")==null ? "-" : prodSubscription.get("prodType")));
			row.appendChild(new Label(prodSubscription.get("prodDiscount")==null ? "-" : prodSubscription.get("prodDiscount")));
			row.appendChild(new Label(prodSubscription.get("rewards")==null ? "-" : prodSubscription.get("rewards")));
			row.appendChild(new Label(prodSubscription.get("subscribeFee")==null ? "-" : prodSubscription.get("subscribeFee")));
			row.appendChild(new Label(prodSubscription.get("issuanceFee")==null ? "-" : prodSubscription.get("issuanceFee")));
			row.setValue(prodSubscription.get("prodTypeId"));
			Listbox subscribeTo = new Listbox();
			subscribeTo.setId("subscribeTo" + rows.getChildren().size());
			subscribeTo.getChildren().addAll(ComponentUtil.convertToListitems(NonConfigurableConstants.BOOLEAN_YN, true));
			subscribeTo.setSelectedIndex(0);
			subscribeTo.setWidth("100%"); subscribeTo.setRows(1); subscribeTo.setMold("select");
			row.appendChild(subscribeTo);
			rows.insertBefore(row, rows.getLastChild());
		}
	}
	public void checkChargeTo(Listitem selectedItem){
		logger.info("checkChargeTo(Listitem selectedItem)");
		boolean toParent = false;
		if(selectedItem.getValue().equals(NonConfigurableConstants.INVOICE_CHARGE_TO_PARENT)){
			toParent = true;
		}
		((Listbox)this.getFellow("invoiceFormat")).setDisabled(toParent);
		((Listbox)this.getFellow("invoiceSorting")).setDisabled(toParent);
		((Listbox)this.getFellow("govtEInvFlagList")).setDisabled(toParent);
		((Listbox)this.getFellow("businessUnitList")).setDisabled(toParent);
		((Listbox)this.getFellow("pubbsFlagList")).setDisabled(toParent);
		((Listbox)this.getFellow("fiFlagList")).setDisabled(toParent);
		((Listbox)this.getFellow("recurring")).setDisabled(toParent);
		((Listbox)this.getFellow("recurringChargeDay")).setDisabled(toParent);
	}
	public void save() throws InterruptedException, CniiInterfaceException{
		logger.info("save()");
		if(Messagebox.show("Create Division?", "Create Division", Messagebox.OK | Messagebox.CANCEL, Messagebox.QUESTION)==Messagebox.OK){
			try
			{
			Map<String, Object> accountDetails = new HashMap<String, Object>();
			// subscription
			Rows prodSubscriptions = (Rows)this.getFellow("prodSubscriptions");
			List<String> prodSubs = new ArrayList<String>();
			for(Object prodSubscription : prodSubscriptions.getChildren()){
				if(!prodSubscription.equals(prodSubscriptions.getLastChild())){// filter last button row
					Row row = (Row)prodSubscription;
					// now getting last cell of row
					Listbox subscribedList = (Listbox)row.getLastChild();
					if(subscribedList.getSelectedItem().getValue().equals(NonConfigurableConstants.BOOLEAN_YN_YES)){
						String prodTypeId = (String)row.getValue();
						prodSubs.add(prodTypeId);
					}
				}
			}
			if(prodSubs.isEmpty()){
				Messagebox.show("No product subscriptions!", "Create Division", Messagebox.OK, Messagebox.EXCLAMATION);
				return;
			}
			accountDetails.put("prodSubscriptions", prodSubs);
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
			if(this.businessHelper.getAccountBusiness().checkCode(custNo, NonConfigurableConstants.DIVISION_LEVEL, null, divCode)){
				Messagebox.show("Division code already in used! Please use another code", "Create Division", Messagebox.OK, Messagebox.ERROR);
				return;
			}
			// account
			accountDetails.put("userId", getUserId());
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
				accountDetails.put("businessUnit", businessUnit);
				if(govtEInvFlag.equals(NonConfigurableConstants.GOVT_EINV_FLAG_NO) && (businessUnit != null && businessUnit.length()>0))
					throw new IBSException(
							"Please unselect Business Unit as it is not applicable for accounts opt for NO in Government eInvoice");
				else if(!govtEInvFlag.equals(NonConfigurableConstants.GOVT_EINV_FLAG_NO) && (businessUnit == null || businessUnit.length()==0))
					throw new IBSException(
							"Business Unit is required for accounts opt for Government eInvoice");
				
				String pubbsFlag = (String)((Listbox)this.getFellow("pubbsFlagList")).getSelectedItem().getValue();
				accountDetails.put("pubbsFlag", pubbsFlag );
				String fiFlag = (String)((Listbox)this.getFellow("fiFlagList")).getSelectedItem().getValue();
				accountDetails.put("fiFlag", pubbsFlag );
				
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
				accountDetails.put("govtEInvFlag", NonConfigurableConstants.GOVT_EINV_FLAG_NO);
				accountDetails.put("businessUnit", null);
				accountDetails.put("pubbsFlag", NonConfigurableConstants.BOOLEAN_NO);
				accountDetails.put("fiFlag", NonConfigurableConstants.BOOLEAN_NO);
				accountDetails.put("recurring", NonConfigurableConstants.BOOLEAN_NO);
				accountDetails.put("recurringChargeDay", null);
			}
			if(NonConfigurableConstants.GOVT_EINV_FLAG_NO.equals((String)accountDetails.get("govtEInvFlag"))
					&& this.businessHelper.getAccountBusiness().containGovtEInvoice(custNo, null))
			{
				if(Messagebox.show("Set Government e-Invoice before proceeding?", "Warning", Messagebox.OK | Messagebox.CANCEL, Messagebox.INFORMATION)==Messagebox.OK){
					return;
				}
			}
//			if(NonConfigurableConstants.BOOLEAN_NO.equals((String)accountDetails.get("pubbsFlag"))
//					&& this.businessHelper.getAccountBusiness().containPubbs(custNo, null))
//			{
//				if(Messagebox.show("Set PUBBS Billing before proceeding?", "Warning", Messagebox.OK | Messagebox.CANCEL, Messagebox.INFORMATION)==Messagebox.OK){
//					return;
//				}
//			}
			if(!NonConfigurableConstants.GOVT_EINV_FLAG_NO.equals((String)accountDetails.get("govtEInvFlag")) &&
					!NonConfigurableConstants.BOOLEAN_NO.equals((String)accountDetails.get("pubbsFlag")))
			{
				Messagebox.show("Government eInvoice & PUBBS should not double invoice", "Add Division", Messagebox.OK, Messagebox.ERROR);
				return;
			}
			
			// main contacts
			Listbox billingContact = (Listbox)this.getFellow("billingContact");
			accountDetails.put("billingContact", billingContact.getSelectedItem().getValue());
			Listbox shippingContact = (Listbox)this.getFellow("shippingContact");
			Listitem shippingContactItem = shippingContact.getSelectedItem();
			if(shippingContactItem!=null && !shippingContactItem.getValue().equals("")){
				accountDetails.put("shippingContact", shippingContactItem.getValue());
			}else{
				accountDetails.put("shippingContact", billingContact.getSelectedItem().getValue());
			}
			
			if(this.businessHelper.getAccountBusiness().createCorpSubAccount(accountDetails)){
				Messagebox.show("Division Created.", "Create Division", Messagebox.OK, Messagebox.INFORMATION);
				this.back();
			}else{
				Messagebox.show("Unable to create division! Please try again later", "Create Division", Messagebox.OK, Messagebox.ERROR);
			}
			
			} 
			catch(IBSException e){
				Messagebox.show(e.getMessage(), "Create Division", Messagebox.OK , Messagebox.INFORMATION);
			}
			catch (Exception e) {
				logger.error("Error", e);
				Messagebox.show("Unable to create division! Please try again later", "Create Division", Messagebox.OK, Messagebox.ERROR);
			}
		}
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
	@Override
	public void refresh() throws InterruptedException {
	}
	public List<Listitem> getChargeTos(){
		return chargeTos;
	}
	public List<Listitem> getInvoiceFormats(){
		return invoiceFormats;
	}
	public List<Listitem> getInvoiceSortings(){
		return invoiceSortings;
	}
	public List<Listitem> getGovtEInvFlags(){
		return govtEInvFlags;
	}
	public List<Listitem> getPubbsFlags() {
		return pubbsFlags;
	}
	public void setPubbsFlags(List<Listitem> pubbsFlags) {
		this.pubbsFlags = pubbsFlags;
	}
	public List<Listitem> getFiFlags() {
		return fiFlags;
	}
	public void setFiFlags(List<Listitem> fiFlags) {
		this.fiFlags = fiFlags;
	}
	public List<Listitem> getBusinessUnits(){
		this.businessUnits.add((Listitem) ComponentUtil.createNotRequiredListItem());
		Map<String, String> businessUnitMasters = ConfigurableConstants.getBusinessUnits();
		for(Entry<String, String> entry : businessUnitMasters.entrySet()){
			this.businessUnits.add(new Listitem(entry.getKey() + " - " + entry.getValue(), entry.getKey()));
		}
		
		return businessUnits;
	}
}