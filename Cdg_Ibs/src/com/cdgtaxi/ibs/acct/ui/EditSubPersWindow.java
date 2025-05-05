package com.cdgtaxi.ibs.acct.ui;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Datebox;
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

public class EditSubPersWindow extends AddSubPersWindow {
	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(EditSubPersWindow.class);
	private List<Listitem> salutations = ComponentUtil.convertToListitems(ConfigurableConstants.getSalutations(), false);
	private List<Listitem> races = ComponentUtil.convertToListitems(ConfigurableConstants.getRace(), false);
	private List<Listitem> relationships = ComponentUtil.convertToListitems(ConfigurableConstants.getRelationships(), true);
	private List<Listitem> countries = ComponentUtil.convertToListitems(ConfigurableConstants.getCountries(), true);
	private List<Listitem> optionalBooleanList = new ArrayList<Listitem>();
	private List<Listitem> daysUnits = new ArrayList<Listitem>();
	private String custNo, acctStatus, acctNo;
	
	@SuppressWarnings("unchecked")
	public EditSubPersWindow() throws InterruptedException{
		logger.info("EditSubPersWindow()");
		//customer number
		Map<String, String> map = Executions.getCurrent().getArg();
		custNo = map.get("custNo");
		if(custNo==null || custNo.trim().length()==0){
			Messagebox.show("No account number found!", "Edit Sub Applicant", Messagebox.OK, Messagebox.ERROR);
		}
		// account status
		acctStatus = map.get("acctStatus");
		if(acctStatus==null || acctStatus.trim().length()==0){
			Messagebox.show("No account status found!", "Edit Sub Applicant", Messagebox.OK, Messagebox.ERROR);
		}
		// account number
		acctNo = map.get("acctNo");
		if(acctNo==null || acctNo.trim().length()==0){
			Messagebox.show("No account no found!", "Edit Sub Applicant", Messagebox.OK, Messagebox.ERROR);
		}
		// getting optional boolean
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
		}else if(acctNo==null || acctNo.trim().length()==0){
			this.back();
			return;
		}
		if(acctStatus.equals(NonConfigurableConstants.ACCOUNT_STATUS.get(NonConfigurableConstants.ACCOUNT_STATUS_PENDING_ACTIVATION))){
			this.getFellow("deleteBtn").setVisible(true);
			((Decimalbox)this.getFellow("creditLimit")).setDisabled(false);
		}else{
			((Decimalbox)this.getFellow("creditLimit")).setDisabled(true);
		}
		// initing fields
		Map<String, Object> subDetails = this.businessHelper.getAccountBusiness().getSubPersAccount(acctNo);
		if(subDetails.get("salCode")!=null){
			Listbox salBox = (Listbox)this.getFellow("salList");
			for(Object salutation : salBox.getItems()){
				if(((Listitem)salutation).getValue().equals(subDetails.get("salCode"))){
					((Listitem)salutation).setSelected(true);
				}
			}
		}
		races.add((Listitem) ComponentUtil.createNotRequiredListItem());
		if(subDetails.get("race")!=null){
			Listbox raceBox = (Listbox)this.getFellow("raceList");
			for(Object raceObj : raceBox.getItems()){
				if(((Listitem)raceObj).getValue().equals(subDetails.get("race"))){
					((Listitem)raceObj).setSelected(true);
				}
			}
		}
		((Textbox)this.getFellow("acctName")).setValue((String)subDetails.get("acctName"));
		((Textbox)this.getFellow("nameOnCard")).setValue((String)subDetails.get("nameOnCard"));
		((Textbox)this.getFellow("nric")).setValue((String)subDetails.get("nric"));
		((Datebox)this.getFellow("birthdate")).setValue((Date)subDetails.get("birthdate"));
		if(subDetails.get("relationCode")!=null){
			Listbox relationBox = (Listbox)this.getFellow("relationList");
			for(Object relation : relationBox.getItems()){
				if(((Listitem)relation).getValue().equals(subDetails.get("relationCode"))){
					((Listitem)relation).setSelected(true);
				}
			}
		}
		if(subDetails.get("creditLimit")!=null){
			((Decimalbox)this.getFellow("creditLimit")).setValue((BigDecimal)subDetails.get("creditLimit"));
		}
		((Textbox)this.getFellow("tel")).setValue((String)subDetails.get("tel"));
		((Textbox)this.getFellow("email")).setValue((String)subDetails.get("email"));
		((Textbox)this.getFellow("mobile")).setValue((String)subDetails.get("mobile"));
		((Textbox)this.getFellow("office")).setValue((String)subDetails.get("office"));
		((Textbox)this.getFellow("blkNo")).setValue((String)subDetails.get("blkNo"));
		((Textbox)this.getFellow("unitNo")).setValue((String)subDetails.get("unitNo"));
		((Textbox)this.getFellow("street")).setValue((String)subDetails.get("street"));
		((Textbox)this.getFellow("building")).setValue((String)subDetails.get("building"));
		((Textbox)this.getFellow("area")).setValue((String)subDetails.get("area"));
		if(subDetails.get("countryCode")!=null){
			Listbox countryBox = (Listbox)this.getFellow("countryList");
			for(Object country : countryBox.getItems()){
				if(((Listitem)country).getValue().equals(subDetails.get("countryCode"))){
					((Listitem)country).setSelected(true);
					checkCountry(((Listitem)country), (Textbox)this.getFellow("city"), (Textbox)this.getFellow("state"));
				}
			}
		}
		((Textbox)this.getFellow("city")).setValue((String)subDetails.get("city"));
		((Textbox)this.getFellow("state")).setValue((String)subDetails.get("state"));
		((Textbox)this.getFellow("postal")).setValue((String)subDetails.get("postal"));
		// initing the product subscriptions
		Rows rows = (Rows)this.getFellow("prodSubscriptions");
		Set<String> productSubscriptions = (Set<String>)subDetails.get("productSubscriptions");
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
			String subscribed = productSubscriptions.contains(prodSubscription.get("prodTypeId")) ? NonConfigurableConstants.BOOLEAN_YN_YES : NonConfigurableConstants.BOOLEAN_YN_NO;
			for(Object subscribe : subscribeTo.getChildren()){
				if(((Listitem)subscribe).getValue().equals(subscribed)){
					((Listitem)subscribe).setSelected(true);
					break;
				}
			}
			subscribeTo.setWidth("100%"); subscribeTo.setRows(1); subscribeTo.setMold("select");
			row.appendChild(subscribeTo);
			rows.insertBefore(row, rows.getLastChild());
		}
		
		Listbox recurring = (Listbox)this.getFellow("recurring");
		for(Object recurringFlag : recurring.getChildren()){
			if(((Listitem)recurringFlag).getValue().equals(subDetails.get("recurring"))){
				((Listitem)recurringFlag).setSelected(true);
				break;
			}
		}
		Listbox recurringChargeDay = (Listbox)this.getFellow("recurringChargeDay");
		if(subDetails.get("recurringChargeDay")!=null){
			for(Object recurringCharging : recurringChargeDay.getChildren()){
				if(((Listitem)recurringCharging).getValue().equals(NonConfigurableConstants.DAYSwithSuffix.get((subDetails.get("recurringChargeDay")).toString()))){
					((Listitem)recurringCharging).setSelected(true);
					break;
				}
			}
		}
	}
	public void save() throws InterruptedException, CniiInterfaceException{
		logger.info("save()");
		try
		{
			if(Messagebox.show("Save Sub Applicant?", "Edit Sub Applicant", Messagebox.OK | Messagebox.CANCEL, Messagebox.QUESTION)==Messagebox.OK){
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
					Messagebox.show("No product subscriptions!", "Edit Sub Applicant", Messagebox.OK, Messagebox.EXCLAMATION);
					return;
				}
				accountDetails.put("prodSubscriptions", prodSubs);
				accountDetails.put("acctNo", acctNo);
				logger.info("acctno > "+acctNo);
				
				//check product if can approve Subscription
				if(this.businessHelper.getAccountBusiness().hasPendApproveSubscriptionCorpSubAccount(accountDetails)) {
					Messagebox.show("There is currently a subscription for this Product Type that is pending for approval.", "Edit Subscription", Messagebox.OK, Messagebox.ERROR);			
					return;
				}
	//			else
	//				Messagebox.show("Subcribe/Unsubcribe will be sent for approval if there are any changes to the subscription.", "Edit Subscription", Messagebox.OK, Messagebox.INFORMATION);
				
				
				// checking credit limit
				BigDecimal subCreditLimit = ((Decimalbox)this.getFellow("creditLimit")).getValue();
				accountDetails.put("creditLimit", subCreditLimit);
				if(this.businessHelper.getAccountBusiness().checkParentCreditLimit(custNo, null, subCreditLimit)){
					Messagebox.show("Credit Limit is greater than Personal!", "Edit Sub Applicant", Messagebox.OK, Messagebox.ERROR);
					return;
				}
				// account
				accountDetails.put("custNo", custNo);
	//			accountDetails.put("acctNo", acctNo);
				accountDetails.put("userId", getUserLoginIdAndDomain());
				
				Listbox salList = (Listbox)this.getFellow("salList");
				accountDetails.put("salCode", (String)salList.getSelectedItem().getValue());
				Listbox raceList = (Listbox)this.getFellow("raceList");
				accountDetails.put("race", (String)raceList.getSelectedItem().getValue());
				String subName = ((Textbox)this.getFellow("acctName")).getValue();
				accountDetails.put("acctName", subName);
				String subNameOnCard = ((Textbox)this.getFellow("nameOnCard")).getValue();
				accountDetails.put("nameOnCard", subNameOnCard);
				String nric = ((Textbox)this.getFellow("nric")).getValue();
				accountDetails.put("nric", nric);
				Date birthdate = ((Datebox)this.getFellow("birthdate")).getValue();
				accountDetails.put("birthdate", birthdate);
				String relationCode = (String)((Listbox)this.getFellow("relationList")).getSelectedItem().getValue();
				accountDetails.put("relationCode", relationCode);
				String tel = ((Textbox)this.getFellow("tel")).getValue();
				accountDetails.put("tel", tel);
				String email = ((Textbox)this.getFellow("email")).getValue();
				accountDetails.put("email", email);
				String mobile = ((Textbox)this.getFellow("mobile")).getValue();
				accountDetails.put("mobile", mobile);
				String office = ((Textbox)this.getFellow("office")).getValue();
				accountDetails.put("office", office);
				accountDetails.put("blkNo", ((Textbox)this.getFellow("blkNo")).getValue());
				accountDetails.put("unitNo", ((Textbox)this.getFellow("unitNo")).getValue());
				accountDetails.put("street", ((Textbox)this.getFellow("street")).getValue());
				accountDetails.put("building", ((Textbox)this.getFellow("building")).getValue());
				accountDetails.put("area", ((Textbox)this.getFellow("area")).getValue());
				Listbox countryList = (Listbox)this.getFellow("countryList");
				accountDetails.put("countryCode", (String)countryList.getSelectedItem().getValue());
				if(!countryList.getSelectedItem().getValue().equals("SG")){
					String city = ((Textbox)this.getFellow("city")).getValue();
					String state = ((Textbox)this.getFellow("state")).getValue();
					if(city!=null && city.trim().length()!=0 && state!=null && state.trim().length()!=0){
						accountDetails.put("city", city);
						accountDetails.put("state", state);
					}else{
						Messagebox.show("Please input city and state!", "Edit Sub Applicant", Messagebox.OK, Messagebox.ERROR);
						return;
					}
				}
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
				
				accountDetails.put("postal", ((Textbox)this.getFellow("postal")).getValue());
				
				HashMap result = this.businessHelper.getAccountBusiness().updatePersSubAccount(accountDetails);
				
				if(null != result.get("approve") && result.get("approve").toString().trim().equals("true"))
					Messagebox.show("Subscribe/Unsubscribe will be sent for approval.", "Edit Division", Messagebox.OK, Messagebox.INFORMATION);
				if(null != result.get("result") && result.get("result").toString().trim().equals("true")){
					Messagebox.show("Sub Applicant Saved.", "Edit Sub Applicant", Messagebox.OK, Messagebox.INFORMATION);
					this.back();
				}else{
					Messagebox.show("Unable to save sub applicant! Please try again later", "Edit Sub Applicant", Messagebox.OK, Messagebox.ERROR);
				}
			}
		}catch(IBSException e){
			Messagebox.show(e.getMessage(), "Edit Sub Applicant", Messagebox.OK, Messagebox.INFORMATION);
		}
	}
	public void delete() throws InterruptedException{
		if(Messagebox.show("Confirm Delete?", "Edit Sub Applicant", Messagebox.OK | Messagebox.CANCEL, Messagebox.QUESTION) == Messagebox.OK){
			this.businessHelper.getAccountBusiness().deleteSubAccount(Integer.parseInt(acctNo), getUserLoginIdAndDomain());
			Messagebox.show("Sub Applicant deleted", "Edit Sub Applicant", Messagebox.OK , Messagebox.INFORMATION);
			this.back();
		}
	}
	@Override
	public void refresh() throws InterruptedException {
	}
	public List<Listitem> getSalutations(){
		return salutations;
	}
	public List<Listitem> getRace(){
		return races;
	}
	public List<Listitem> getRelationships(){
		return relationships;
	}
	public List<Listitem> getCountries(){
		return countries;
	}
	public List<Listitem> getOptionalBooleanList() {
		return cloneList(optionalBooleanList);
	}
	public List<Listitem> getDaysUnits(){
		return this.daysUnits;
	}
}
