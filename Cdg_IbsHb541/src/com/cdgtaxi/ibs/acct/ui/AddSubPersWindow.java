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

public class AddSubPersWindow extends CommonInnerAcctWindow {
	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(AddSubPersWindow.class);
	private List<Listitem> salutations = ComponentUtil.convertToListitems(ConfigurableConstants.getSalutations(), false);
	private List<Listitem> race = ComponentUtil.convertToListitems(ConfigurableConstants.getRace(), false);
	private List<Listitem> relationships = ComponentUtil.convertToListitems(ConfigurableConstants.getRelationships(), true);
	private List<Listitem> countries = ComponentUtil.convertToListitems(ConfigurableConstants.getCountries(), true);
	private List<Listitem> optionalBooleanList = new ArrayList<Listitem>();
	private List<Listitem> daysUnits = new ArrayList<Listitem>();
	private String custNo, acctStatus;

	@SuppressWarnings("unchecked")
	public AddSubPersWindow() throws InterruptedException{
		logger.info("AddSubWindow()");
		//customer number
		Map<String, String> map = Executions.getCurrent().getArg();
		custNo = map.get("custNo");
		if(custNo==null || custNo.trim().length()==0){
			Messagebox.show("No account number found!", "Create Sub Applicant", Messagebox.OK, Messagebox.ERROR);
		}
		// account status
		acctStatus = map.get("acctStatus");
		if(acctStatus==null || acctStatus.trim().length()==0){
			Messagebox.show("No account status found!", "Create Sub Applicant", Messagebox.OK, Messagebox.ERROR);
		}
		// initing default country
		for(Listitem country : countries){
			if(country.getValue().equals("SG")){
				country.setSelected(true);
				break;
			}
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
		}else if(acctStatus==null || acctStatus.trim().length()==0){
			this.back();
		}
		// initing the product subscriptions
		Rows rows = (Rows)this.getFellow("prodSubscriptions");
		Set<Map<String, String>> prodSubscriptions = this.businessHelper.getAccountBusiness().getProductSubscriptions(custNo, null);
		for(Map<String, String> prodSubscription : prodSubscriptions){
			Row row = new Row();
			row.appendChild(new Label(rows.getChildren().size()+""));
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
	public void save() throws InterruptedException, CniiInterfaceException, IBSException{
		logger.info("save()");
		if(Messagebox.show("Create Sub Applicant?", "Create Sub Applicant", Messagebox.OK | Messagebox.CANCEL, Messagebox.QUESTION)==Messagebox.OK){
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
				Messagebox.show("No product subscriptions!", "Create Sub Applicant", Messagebox.OK, Messagebox.EXCLAMATION);
				return;
			}
			accountDetails.put("prodSubscriptions", prodSubs);
			// checking credit limit
			BigDecimal divCreditLimit = ((Decimalbox)this.getFellow("creditLimit")).getValue();
			accountDetails.put("creditLimit", divCreditLimit);
			if(this.businessHelper.getAccountBusiness().checkParentCreditLimit(custNo, null, divCreditLimit)){
				Messagebox.show("Credit Limit is greater than Personal!", "Create Sub Applicant", Messagebox.OK, Messagebox.ERROR);
				return;
			}
			// account
			accountDetails.put("custNo", custNo);
			accountDetails.put("userId", getUserId());
			String salCode = (String)((Listbox)this.getFellow("sal")).getSelectedItem().getValue();
			accountDetails.put("salCode", salCode);
			String raceCode = (String)((Listbox)this.getFellow("race")).getSelectedItem().getValue();
			accountDetails.put("race", raceCode);
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
			logger.info("email = " + email);
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
					Messagebox.show("Please input city and state!", "Create Sub Applicant", Messagebox.OK, Messagebox.ERROR);
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
			try {
				if(this.businessHelper.getAccountBusiness().createPersSubAccount(accountDetails)){
					Messagebox.show("Sub Applicant Created.", "Create Sub Applicant", Messagebox.OK, Messagebox.INFORMATION);
					this.back();
				}else{
					Messagebox.show("Unable to create sub applicant! Please try again later", "Create Sub Applicant", Messagebox.OK, Messagebox.ERROR);
				}
			} catch (Exception e) {
				logger.error("Error", e);
				Messagebox.show("Unable to create sub applicant! Please try again later", "Create Sub Applicant", Messagebox.OK, Messagebox.ERROR);
			}
		}
	}
	@Override
	public void refresh() throws InterruptedException {
	}
	public List<Listitem> getSalutations(){
		return salutations;
	}
	public List<Listitem> getRace(){
		return race;
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
	public void setOptionalList(List<Listitem> optionalBooleanList) {
		this.optionalBooleanList = optionalBooleanList;
	}
	public List<Listitem> getDaysUnits(){
		return this.daysUnits;
	}
}