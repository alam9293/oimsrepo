package com.cdgtaxi.ibs.acct.ui;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.SuspendNotAllowedException;
import org.zkoss.zk.ui.metainfo.EventHandler;
import org.zkoss.zk.ui.metainfo.ZScript;
import org.zkoss.zk.ui.util.ConditionImpl;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Decimalbox;
import org.zkoss.zul.Image;
import org.zkoss.zul.Label;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Row;
import org.zkoss.zul.Rows;
import org.zkoss.zul.Textbox;

import com.cdgtaxi.ibs.common.ConfigurableConstants;
import com.cdgtaxi.ibs.common.NonConfigurableConstants;
import com.cdgtaxi.ibs.common.exception.IBSException;
import com.cdgtaxi.ibs.common.ui.CommonWindow;
import com.cdgtaxi.ibs.util.ComponentUtil;


public class EditPAPersWindow extends CommonAcctWindow{
	// default
	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(EditPAPersWindow.class);
	private List<Listitem> industries = new ArrayList<Listitem>();
	private List<Listitem> countries = new ArrayList<Listitem>();
	private List<Listitem> salutations = new ArrayList<Listitem>();
	private List<Listitem> infoSources = new ArrayList<Listitem>();
	private List<Listitem> booleanList = new ArrayList<Listitem>();
	private List<Listitem> optionalBooleanList = new ArrayList<Listitem>();
	private List<Listitem> jobStatus = new ArrayList<Listitem>();
	private List<Listitem> race = ComponentUtil.convertToListitems(ConfigurableConstants.getRace(), false);
	
	private String custNo, createdDt;
	@SuppressWarnings("unchecked")
	public EditPAPersWindow() throws InterruptedException{
		logger.info("EditPAPersWindow()");
		//account number
		Map<String, String> map = Executions.getCurrent().getArg();
		custNo = map.get("custNo");
		createdDt = map.get("createdDt");
		if(custNo==null || custNo.trim().length()==0){
			Messagebox.show("No account number found!", "Edit Personal", Messagebox.OK, Messagebox.ERROR);
		}
		// getting data from non configurable constants
		// getting boolean
		booleanList.add(new Listitem(NonConfigurableConstants.BOOLEAN.get(NonConfigurableConstants.BOOLEAN_NO), NonConfigurableConstants.BOOLEAN_NO));
		booleanList.add(new Listitem(NonConfigurableConstants.BOOLEAN.get(NonConfigurableConstants.BOOLEAN_YES), NonConfigurableConstants.BOOLEAN_YES));
		// getting optional boolean
		optionalBooleanList.add((Listitem)ComponentUtil.createNotRequiredListItem());
		optionalBooleanList.add(new Listitem(NonConfigurableConstants.BOOLEAN.get(NonConfigurableConstants.BOOLEAN_NO), NonConfigurableConstants.BOOLEAN_NO));
		optionalBooleanList.add(new Listitem(NonConfigurableConstants.BOOLEAN.get(NonConfigurableConstants.BOOLEAN_YES), NonConfigurableConstants.BOOLEAN_YES));
		// getting data from master table
		// getting industry
		Map<String, String> masterIndustry = ConfigurableConstants.getIndustries();
		for(String masterCode : masterIndustry.keySet()){
			this.industries.add(new Listitem(masterIndustry.get(masterCode), masterCode));
		}
		// getting job status
		Map<String, String> masterJobStatus = ConfigurableConstants.getJobStatuses();
		for(String masterCode : masterJobStatus.keySet()){
			this.jobStatus.add(new Listitem(masterJobStatus.get(masterCode), masterCode));
		}
		// getting country
		Map<String, String> masterCountry = ConfigurableConstants.getCountries();
		for(String masterCode : masterCountry.keySet()){
			this.countries.add(new Listitem(masterCountry.get(masterCode), masterCode));
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
	}
	public void init(){
		Map<String, Object> accountDetails = this.businessHelper.getAccountBusiness().searchAccount(custNo);
		displayCommonFields(accountDetails);
		displayPersFields(accountDetails);
	}
	private void displayCommonFields(Map<String, Object> accountDetails){
		// common details
		((Label)this.getFellow("custNo")).setValue((String)accountDetails.get("custNo"));
		((Label)this.getFellow("acctStatus")).setValue((String)accountDetails.get("acctStatus"));
		((Label)this.getFellow("acctType")).setValue((String)accountDetails.get("acctType"));
		((Label)this.getFellow("createdDt")).setValue(createdDt);
		((Textbox)this.getFellow("acctName")).setValue((String)accountDetails.get("acctName"));
		((Textbox)this.getFellow("tel")).setValue((String)accountDetails.get("tel"));
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
		if(accountDetails.get("lastUpdatedBy")!=null){
			((Label)this.getFellow("lastUpdatedBy")).setValue((String)accountDetails.get("lastUpdatedBy"));
			((Label)this.getFellow("lastUpdatedDate")).setValue((String)accountDetails.get("lastUpdatedDate"));
			((Label)this.getFellow("lastUpdatedTime")).setValue((String)accountDetails.get("lastUpdatedTime"));
		}
		Listbox recurring = (Listbox)this.getFellow("recurring");
		for(Object recurringFlag : recurring.getChildren()){
			if(((Listitem)recurringFlag).getValue().equals(accountDetails.get("recurring"))){
				((Listitem)recurringFlag).setSelected(true);
				break;
			}
		}

		displayProductSubscriptions(accountDetails);
	}
	private void displayPersFields(Map<String, Object> accountDetails){
		Listbox salutations = (Listbox)this.getFellow("salutationList");
		for(Object salutation : salutations.getChildren()){
			if(((Listitem)salutation).getValue().equals(accountDetails.get("salutationCode"))){
				((Listitem)salutation).setSelected(true);
				break;
			}
		}
		Listbox races = (Listbox)this.getFellow("mainContactRace");
		for(Object race : races.getChildren()){
			if(((Listitem)race).getValue().equals(accountDetails.get("race"))){
				((Listitem)race).setSelected(true);
				break;
			}
		}
		
		((Textbox)this.getFellow("nric")).setValue((String)accountDetails.get("nric"));
		((Datebox)this.getFellow("birthdate")).setValue((Date)accountDetails.get("birthdate"));
		((Textbox)this.getFellow("email")).setValue((String)accountDetails.get("email"));
		((Textbox)this.getFellow("mobile")).setValue((String)accountDetails.get("mobile"));
		((Textbox)this.getFellow("office")).setValue((String)accountDetails.get("office"));
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
		// checking the billing same
		if((accountDetails.get("blkNo")==null ? accountDetails.get("billBlkNo")==null : accountDetails.get("blkNo").equals(accountDetails.get("billBlkNo")))
			&& (accountDetails.get("unitNo")==null ? accountDetails.get("billUnitNo")==null : accountDetails.get("unitNo").equals(accountDetails.get("billUnitNo")))
			&& (accountDetails.get("street")==null ? accountDetails.get("billStreet")==null : accountDetails.get("street").equals(accountDetails.get("billStreet")))
			&& (accountDetails.get("building")==null ? accountDetails.get("billBuilding")==null : accountDetails.get("building").equals(accountDetails.get("billBuilding")))
			&& (accountDetails.get("area")==null ? accountDetails.get("billArea")==null : accountDetails.get("area").equals(accountDetails.get("billArea")))
			&& (accountDetails.get("countryCode")==null ? accountDetails.get("billCountryCode")==null : accountDetails.get("countryCode").equals(accountDetails.get("billCountryCode")))
			&& (accountDetails.get("city")==null ? accountDetails.get("billCity")==null : accountDetails.get("city").equals(accountDetails.get("billCity")))
			&& (accountDetails.get("state")==null ? accountDetails.get("billState")==null : accountDetails.get("state").equals(accountDetails.get("billState")))
			&& (accountDetails.get("postal")==null ? accountDetails.get("billPostal")==null : accountDetails.get("postal").equals(accountDetails.get("billPostal")))
		){
			Listbox billAdd = (Listbox)this.getFellow("billAdd");
			for(Object booleanItem : billAdd.getItems()){
				if(((Listitem)booleanItem).getValue().equals(NonConfigurableConstants.BOOLEAN_YN_YES)){
					((Listitem)booleanItem).setSelected(true);
					this.checkBill((Listitem)booleanItem);
					break;
				}
			}

			((Textbox)this.getFellow("billBlkNo")).setValue((String)accountDetails.get("blkNo"));
			((Textbox)this.getFellow("billUnitNo")).setValue((String)accountDetails.get("unitNo"));
			((Textbox)this.getFellow("billStreet")).setValue((String)accountDetails.get("street"));
			((Textbox)this.getFellow("billBuilding")).setValue((String)accountDetails.get("building"));
			((Textbox)this.getFellow("billArea")).setValue((String)accountDetails.get("area"));
			Listbox billCountryList = (Listbox)this.getFellow("billCountryList");
			for(Object billCountryItem : billCountryList.getItems()){
				if (accountDetails.get("countryCode") != null && !"".equalsIgnoreCase((String)accountDetails.get("countryCode")))
				{
					if(((Listitem)billCountryItem).getValue().equals(accountDetails.get("countryCode"))){
						((Listitem)billCountryItem).setSelected(true);
						checkCountry(((Listitem)billCountryItem), (Textbox)this.getFellow("billCity"), (Textbox)this.getFellow("billState"));
						break;
					}
				}
				else
				{
					if(((Listitem)billCountryItem).getValue().equals(NonConfigurableConstants.SG_COUNTRY_CODE)){
						((Listitem)billCountryItem).setSelected(true);
						checkCountry(((Listitem)billCountryItem), (Textbox)this.getFellow("billCity"), (Textbox)this.getFellow("billState"));
						break;
					}
				}
			}
			((Textbox)this.getFellow("billCity")).setValue((String)accountDetails.get("city"));
			((Textbox)this.getFellow("billState")).setValue((String)accountDetails.get("state"));
			((Textbox)this.getFellow("billPostal")).setValue((String)accountDetails.get("postal"));
			
			
		}else{
			Listbox billAdd = (Listbox)this.getFellow("billAdd");
			for(Object booleanItem : billAdd.getItems()){
				if(((Listitem)booleanItem).getValue().equals(NonConfigurableConstants.BOOLEAN_YN_NO)){
					((Listitem)booleanItem).setSelected(true);
					this.checkBill((Listitem)booleanItem);
					break;
				}
			}
			((Textbox)this.getFellow("billBlkNo")).setValue((String)accountDetails.get("billBlkNo"));
			((Textbox)this.getFellow("billUnitNo")).setValue((String)accountDetails.get("billUnitNo"));
			((Textbox)this.getFellow("billStreet")).setValue((String)accountDetails.get("billStreet"));
			((Textbox)this.getFellow("billBuilding")).setValue((String)accountDetails.get("billBuilding"));
			((Textbox)this.getFellow("billArea")).setValue((String)accountDetails.get("billArea"));
			Listbox billCountryList = (Listbox)this.getFellow("billCountryList");
			for(Object billCountryItem : billCountryList.getItems()){
				if(((Listitem)billCountryItem).getValue().equals(accountDetails.get("billCountryCode"))){
					((Listitem)billCountryItem).setSelected(true);
					checkCountry(((Listitem)billCountryItem), (Textbox)this.getFellow("billCity"), (Textbox)this.getFellow("billState"));
					break;
				}
			}
			((Textbox)this.getFellow("billCity")).setValue((String)accountDetails.get("billCity"));
			((Textbox)this.getFellow("billState")).setValue((String)accountDetails.get("billState"));
			((Textbox)this.getFellow("billPostal")).setValue((String)accountDetails.get("billPostal"));
		}
		// checking the shipping same
		if((accountDetails.get("blkNo")==null ? accountDetails.get("shipBlkNo")==null : accountDetails.get("blkNo").equals(accountDetails.get("shipBlkNo")))
			&& (accountDetails.get("unitNo")==null ? accountDetails.get("shipUnitNo")==null : accountDetails.get("unitNo").equals(accountDetails.get("shipUnitNo")))
			&& (accountDetails.get("street")==null ? accountDetails.get("shipStreet")==null : accountDetails.get("street").equals(accountDetails.get("shipStreet")))
			&& (accountDetails.get("building")==null ? accountDetails.get("shipBuilding")==null : accountDetails.get("building").equals(accountDetails.get("shipBuilding")))
			&& (accountDetails.get("area")==null ? accountDetails.get("shipArea")==null : accountDetails.get("area").equals(accountDetails.get("shipArea")))
			&& (accountDetails.get("countryCode")==null ? accountDetails.get("shipCountryCode")==null : accountDetails.get("countryCode").equals(accountDetails.get("shipCountryCode")))
			&& (accountDetails.get("city")==null ? accountDetails.get("shipCity")==null : accountDetails.get("city").equals(accountDetails.get("shipCity")))
			&& (accountDetails.get("state")==null ? accountDetails.get("shipState")==null : accountDetails.get("state").equals(accountDetails.get("shipState")))
			&& (accountDetails.get("postal")==null ? accountDetails.get("shipPostal")==null : accountDetails.get("postal").equals(accountDetails.get("shipPostal")))
		){
			Listbox shipAdd = (Listbox)this.getFellow("shipAdd");
			for(Object booleanItem : shipAdd.getItems()){
				if(((Listitem)booleanItem).getValue().equals(NonConfigurableConstants.BOOLEAN_YN_YES)){
					((Listitem)booleanItem).setSelected(true);
					this.checkShip((Listitem)booleanItem);
					break;
				}
			}
			((Textbox)this.getFellow("shipBlkNo")).setValue((String)accountDetails.get("blkNo"));
			((Textbox)this.getFellow("shipUnitNo")).setValue((String)accountDetails.get("unitNo"));
			((Textbox)this.getFellow("shipStreet")).setValue((String)accountDetails.get("street"));
			((Textbox)this.getFellow("shipBuilding")).setValue((String)accountDetails.get("building"));
			((Textbox)this.getFellow("shipArea")).setValue((String)accountDetails.get("area"));
			Listbox shipCountryList = (Listbox)this.getFellow("shipCountryList");
			for(Object shipCountryItem : shipCountryList.getItems()){
				if (accountDetails.get("countryCode") != null && !"".equalsIgnoreCase((String)accountDetails.get("countryCode")))
				{
					if(((Listitem)shipCountryItem).getValue().equals(accountDetails.get("countryCode"))){
						((Listitem)shipCountryItem).setSelected(true);
						checkCountry(((Listitem)shipCountryItem), (Textbox)this.getFellow("shipCity"), (Textbox)this.getFellow("shipState"));
						break;
					}
				}
				else
				{
					if(((Listitem)shipCountryItem).getValue().equals(NonConfigurableConstants.SG_COUNTRY_CODE)){
						((Listitem)shipCountryItem).setSelected(true);
						checkCountry(((Listitem)shipCountryItem), (Textbox)this.getFellow("shipCity"), (Textbox)this.getFellow("shipState"));
						break;
					}
				}
			}
			((Textbox)this.getFellow("shipCity")).setValue((String)accountDetails.get("city"));
			((Textbox)this.getFellow("shipState")).setValue((String)accountDetails.get("state"));
			((Textbox)this.getFellow("shipPostal")).setValue((String)accountDetails.get("postal"));
			
			
		}else{
			Listbox shipAdd = (Listbox)this.getFellow("shipAdd");
			for(Object booleanItem : shipAdd.getItems()){
				if(((Listitem)booleanItem).getValue().equals(NonConfigurableConstants.BOOLEAN_YN_NO)){
					((Listitem)booleanItem).setSelected(true);
					this.checkShip((Listitem)booleanItem);
					break;
				}
			}
			((Textbox)this.getFellow("shipBlkNo")).setValue((String)accountDetails.get("shipBlkNo"));
			((Textbox)this.getFellow("shipUnitNo")).setValue((String)accountDetails.get("shipUnitNo"));
			((Textbox)this.getFellow("shipStreet")).setValue((String)accountDetails.get("shipStreet"));
			((Textbox)this.getFellow("shipBuilding")).setValue((String)accountDetails.get("shipBuilding"));
			((Textbox)this.getFellow("shipArea")).setValue((String)accountDetails.get("shipArea"));
			Listbox shipCountryList = (Listbox)this.getFellow("shipCountryList");
			for(Object shipCountryItem : shipCountryList.getItems()){
				if(((Listitem)shipCountryItem).getValue().equals(accountDetails.get("shipCountryCode"))){
					((Listitem)shipCountryItem).setSelected(true);
					checkCountry(((Listitem)shipCountryItem), (Textbox)this.getFellow("shipCity"), (Textbox)this.getFellow("shipState"));
					break;
				}
			}
			((Textbox)this.getFellow("shipCity")).setValue((String)accountDetails.get("shipCity"));
			((Textbox)this.getFellow("shipState")).setValue((String)accountDetails.get("shipState"));
			((Textbox)this.getFellow("shipPostal")).setValue((String)accountDetails.get("shipPostal"));

		}
		Listbox jobStatusList = (Listbox)this.getFellow("jobStatusList");
		for(Object jobStatusItem : jobStatusList.getItems()){
			if(((Listitem)jobStatusItem).getValue().equals(accountDetails.get("jobStatusCode"))){
				((Listitem)jobStatusItem).setSelected(true);
				break;
			}
		}
		((Textbox)this.getFellow("occupation")).setValue((String)accountDetails.get("occupation"));
		Listbox industries = (Listbox)this.getFellow("industryList");
		for(Object industry : industries.getItems()){
			if(((Listitem)industry).getValue().equals(accountDetails.get("industryCode"))){
				((Listitem)industry).setSelected(true);
				break;
			}
		}
		((Textbox)this.getFellow("employerName")).setValue((String)accountDetails.get("employerName"));
		((Textbox)this.getFellow("empBlkNo")).setValue((String)accountDetails.get("empBlkNo"));
		((Textbox)this.getFellow("empUnitNo")).setValue((String)accountDetails.get("empUnitNo"));
		((Textbox)this.getFellow("empStreet")).setValue((String)accountDetails.get("empStreet"));
		((Textbox)this.getFellow("empBuilding")).setValue((String)accountDetails.get("empBuilding"));
		((Textbox)this.getFellow("empArea")).setValue((String)accountDetails.get("empArea"));
		Listbox empCountryList = (Listbox)this.getFellow("empCountryList");
		for(Object country : empCountryList.getChildren()){
			if(((Listitem)country).getValue().equals(accountDetails.get("empCountryCode"))){
				((Listitem)country).setSelected(true);
				this.checkCountry((Listitem)country, (Textbox)this.getFellow("empCity"), (Textbox)this.getFellow("empState"));
				break;
			}
		}
		((Textbox)this.getFellow("empCity")).setValue((String)accountDetails.get("empCity"));
		((Textbox)this.getFellow("empState")).setValue((String)accountDetails.get("empState"));
		((Textbox)this.getFellow("empPostal")).setValue((String)accountDetails.get("empPostal"));
		((Decimalbox)this.getFellow("monthlyIncome")).setValue((BigDecimal)accountDetails.get("monthlyIncome"));
		if(accountDetails.get("empLength")!=null){
			((Decimalbox)this.getFellow("empLength")).setValue(new BigDecimal((Integer)accountDetails.get("empLength")));
		}
	}
	@SuppressWarnings("unchecked")
	private void displayProductSubscriptions(Map<String, Object> accountDetails){
		Listbox prodSubscribe = (Listbox)this.getFellow("prodSubscribe");
		Set<Map<String, String>> productSubscriptions = (Set<Map<String, String>>)accountDetails.get("prodSubscribe");
		for(Iterator<Map<String, String>> iter = productSubscriptions.iterator(); iter.hasNext();){
			Map<String, String> productSubscription = iter.next();
			Listitem prodSub = new Listitem();
			// numbering label
			prodSub.appendChild(new Listcell((prodSubscribe.getItems().size()+1)+""));
			// product type label
			prodSub.appendChild(new Listcell(productSubscription.get("prodType")));
			// product discount label
			prodSub.appendChild(new Listcell(productSubscription.get("prodDiscount")==null ? "-" : productSubscription.get("prodDiscount")));
			// product discount [?] image
			Image prodDiscountImage = new Image("/images/question.png");
			prodDiscountImage.setStyle("cursor: help");
			ZScript showInfo = ZScript.parseContent("editCorpDetailsWindow.displayProdDiscount()");
			showInfo.setLanguage("java");
			EventHandler pdEvent = new EventHandler(showInfo, ConditionImpl.getInstance(null, null));
			prodDiscountImage.addEventHandler("onClick", pdEvent);
			Listcell prodDiscountCell = new Listcell();
			prodDiscountCell.appendChild(prodDiscountImage);
			prodSub.appendChild(prodDiscountCell);
			// rewards label
			prodSub.appendChild(new Listcell(productSubscription.get("rewards")==null ? "-" : productSubscription.get("rewards")));
			// rewards [?] image
			Image rewardsImage = new Image("/images/question.png");
			rewardsImage.setStyle("cursor: help");
			showInfo = ZScript.parseContent("editPersDetailsWindow.displayLoyalty()");
			showInfo.setLanguage("java");
			pdEvent = new EventHandler(showInfo, ConditionImpl.getInstance(null, null));
			rewardsImage.addEventHandler("onClick", pdEvent);
			Listcell rewardsCell = new Listcell();
			rewardsCell.appendChild(rewardsImage);
			prodSub.appendChild(rewardsCell);
			
			// subscription fee label
			prodSub.appendChild(new Listcell(productSubscription.get("subscribeFee")==null ? "-" : productSubscription.get("subscribeFee")));
			// subscription fee [?] image
			Image subscribeImage = new Image("/images/question.png");
			subscribeImage.setStyle("cursor: help");
			showInfo = ZScript.parseContent("editPersDetailsWindow.displaySubscription()");
			showInfo.setLanguage("java");
			pdEvent = new EventHandler(showInfo, ConditionImpl.getInstance(null, null));
			subscribeImage.addEventHandler("onClick", pdEvent);
			Listcell subscribeCell = new Listcell();
			subscribeCell.appendChild(subscribeImage);
			prodSub.appendChild(subscribeCell);
			
			// issuance fee label
			prodSub.appendChild(new Listcell(productSubscription.get("issuanceFee")==null ? "-" : productSubscription.get("issuanceFee")));
			// subscription fee [?] image
			Image issuanceImage = new Image("/images/question.png");
			issuanceImage.setStyle("cursor: help");
			showInfo = ZScript.parseContent("editPersDetailsWindow.displayIssuance()");
			showInfo.setLanguage("java");
			pdEvent = new EventHandler(showInfo, ConditionImpl.getInstance(null, null));
			issuanceImage.addEventHandler("onClick", pdEvent);
			Listcell issuanceCell = new Listcell();
			issuanceCell.appendChild(issuanceImage);
			prodSub.appendChild(issuanceCell);
			
			prodSubscribe.appendChild(prodSub);
		}
	}
	public List<Listitem> getCountries(){
		return cloneList(this.countries);
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
	public List<Listitem> getBooleanList(){
		return cloneList(this.booleanList);
	}
	public List<Listitem> getJobStatus(){
		return this.jobStatus;
	}
	public void save() throws InterruptedException{
		logger.info("save()");
		try
		{
			Map<String, Object> details = getScreenInput();
			details.put("custNo", custNo);
			String userId = getUserLoginIdAndDomain();
			details.put("userId", userId);
			if(this.businessHelper.getAccountBusiness().updatePers(details)){
				Messagebox.show("Account saved.", "Edit Personal", Messagebox.OK, Messagebox.INFORMATION);
				if(this.getParent().getParent().getParent().getParent() instanceof ManagePersAcctWindow){
					((ManagePersAcctWindow)this.getParent().getParent().getParent().getParent()).back();
				}
			}else{
				Messagebox.show("Unable to save. Please try again later.", "Edit Personal", Messagebox.OK, Messagebox.ERROR);
			}
		}catch(IBSException e){
			Messagebox.show(e.getMessage(), "Edit Personal", Messagebox.OK, Messagebox.INFORMATION);
		}
	}
	public void activate() throws InterruptedException{
		try
		{
			Map<String, Object> details = getScreenInput();
			details.put("custNo", custNo);
			String userId = getUserLoginIdAndDomain();
			details.put("userId", userId);
			if(this.businessHelper.getAccountBusiness().updatePers(details)){
				Messagebox.show("Account saved.", "Edit Personal", Messagebox.OK, Messagebox.INFORMATION);
			}else{
				Messagebox.show("Unable to save. Please try again later.", "Edit Personal", Messagebox.OK, Messagebox.ERROR);
				return;
			}
			try {
				String error = this.businessHelper.getAccountBusiness().activateAcct(custNo, getUserLoginIdAndDomain());
				if(error.equals(NonConfigurableConstants.ACCOUNT_ACTIVATION_NO_ERROR)){
					Messagebox.show("Account("+custNo+") Activated", "Edit Personal", Messagebox.OK, Messagebox.INFORMATION);
					if(this.getParent().getParent().getParent().getParent() instanceof ManagePersAcctWindow){
						((ManagePersAcctWindow)this.getParent().getParent().getParent().getParent()).back();
					}
				}else if(error.equals(NonConfigurableConstants.ACCOUNT_ACTIVATION_ERROR_BILLING)){
					Messagebox.show("Billing details not completed. Please complete billing details and try again", "Edit Personal", Messagebox.OK, Messagebox.ERROR);
				}else if(error.equals(NonConfigurableConstants.ACCOUNT_ACTIVATION_ERROR_MAIN_BILLING)){
					Messagebox.show("No main billing contact person selected. Please select one and try again", "Edit Personal", Messagebox.OK, Messagebox.ERROR);
				}else if(error.equals(NonConfigurableConstants.ACCOUNT_ACTIVATION_ERROR_MAIN_SHIPPING)){
					Messagebox.show("No main shipping contact person selected. Please select one and try again", "Edit Personal", Messagebox.OK, Messagebox.ERROR);
				}
			} catch (Exception e) {
				logger.error("Error", e);
				Messagebox.show("Unable to activate account! Please try again later", "Edit Personal", Messagebox.OK, Messagebox.ERROR);
			}
		}
		catch(IBSException e){
			Messagebox.show(e.getMessage(), "Edit Personnel", Messagebox.OK, Messagebox.INFORMATION);
		}
	}
	public CommonWindow back() throws InterruptedException{
		if(this.getParent().getParent().getParent().getParent() instanceof ManagePersAcctWindow){
			return ((ManagePersAcctWindow)this.getParent().getParent().getParent().getParent()).back();
		}
		return null;
	}
	@Override
	public void refresh() throws InterruptedException {
		init();
	}
	private Map<String, Object> getScreenInput() throws IBSException{
		Map<String, Object> details = new HashMap<String, Object>();
		// salutation
		String salutationCode = (String)((Listbox)this.getFellow("salutationList")).getSelectedItem().getValue();
		details.put("salutationCode", salutationCode);
		//race
		Listbox mainContactRaceList = (Listbox)this.getFellow("mainContactRace");
		if(mainContactRaceList.getSelectedItem()!=null && mainContactRaceList.getSelectedItem().getValue()!=null){
			details.put("mainContactRace", (String)mainContactRaceList.getSelectedItem().getValue());
		}
		// name
		String acctName = ((Textbox)this.getFellow("acctName")).getValue();
		details.put("acctName", acctName);
		// name on card
		String nameOnCard = ((Textbox)this.getFellow("nameOnCard")).getValue();
		details.put("nameOnCard", nameOnCard);
		// eInvoice
		String eInvoice = (String)((Listbox)this.getFellow("eInvoice")).getSelectedItem().getValue();
		if(eInvoice!=null && eInvoice.length()!=0){
			details.put("eInvoice", eInvoice);
		}
		String invoicePrinting = (String)((Listbox)this.getFellow("invoicePrinting")).getSelectedItem().getValue();
		if(invoicePrinting!=null && invoicePrinting.length()!=0){
			details.put("invoicePrinting", invoicePrinting);
		}
		String outsourcePrinting = (String)((Listbox)this.getFellow("outsourcePrinting")).getSelectedItem().getValue();
		details.put("outsourcePrinting", outsourcePrinting);
		String sms = (String)((Listbox)this.getFellow("sms")).getSelectedItem().getValue();
		if(sms!=null && sms.length()!=0){
			details.put("sms", sms);
		}
		String smsExpiry = (String)((Listbox)this.getFellow("smsExpiry")).getSelectedItem().getValue();
		if(smsExpiry!=null && smsExpiry.length()!=0){
			details.put("smsExpiry", smsExpiry);
		}
		String smsTopUp = (String)((Listbox)this.getFellow("smsTopUp")).getSelectedItem().getValue();
		if(smsTopUp!=null && smsTopUp.length()!=0){
			details.put("smsTopUp", smsTopUp);
		}
		//nric
		String nric = ((Textbox)this.getFellow("nric")).getValue();
		details.put("nric", nric);
		// birthdate
		Date birthdate = ((Datebox)this.getFellow("birthdate")).getValue();
		details.put("birthdate", birthdate);
		// tel
		String tel = ((Textbox)this.getFellow("tel")).getValue();
		details.put("tel", tel);
		// email
		String email = ((Textbox)this.getFellow("email")).getValue();
		details.put("email", email);
		// mobile
		String mobile = ((Textbox)this.getFellow("mobile")).getValue();
		details.put("mobile", mobile);
		// office
		String office = ((Textbox)this.getFellow("office")).getValue();
		details.put("office", office);
		// address
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
		// information source
		String infoSourceCode = (String)((Listbox)this.getFellow("infoSourceList")).getSelectedItem().getValue();
		details.put("infoSourceCode", infoSourceCode);
		// billing address
		// checking if same address
		String billSame = (String)((Listbox)this.getFellow("billAdd")).getSelectedItem().getValue();
		if(billSame.equals(NonConfigurableConstants.BOOLEAN_YN_YES)){// if billing is same
			details.put("billSame", billSame);
		}else{// if not
			String billBlkNo = ((Textbox)this.getFellow("billBlkNo")).getValue();
			details.put("billBlkNo", billBlkNo);
			String billUnitNo = ((Textbox)this.getFellow("billUnitNo")).getValue();
			details.put("billUnitNo", billUnitNo);
			String billStreet = ((Textbox)this.getFellow("billStreet")).getValue();
			details.put("billStreet", billStreet);
			String billBuilding = ((Textbox)this.getFellow("billBuilding")).getValue();
			details.put("billBuilding", billBuilding);
			String billArea = ((Textbox)this.getFellow("billArea")).getValue();
			details.put("billArea", billArea);
			String billCountryCode = (String)((Listbox)this.getFellow("billCountryList")).getSelectedItem().getValue();
			details.put("billCountryCode", billCountryCode);
			String billCity = ((Textbox)this.getFellow("billCity")).getValue();
			details.put("billCity", billCity);
			String billState = ((Textbox)this.getFellow("billState")).getValue();
			details.put("billState", billState);
			String billPostal = ((Textbox)this.getFellow("billPostal")).getValue();
			details.put("billPostal", billPostal);
		}
		// shipping address
		String shipSame = (String)((Listbox)this.getFellow("shipAdd")).getSelectedItem().getValue();
		if(shipSame.equals(NonConfigurableConstants.BOOLEAN_YN_YES)){// if shipping is same
			details.put("shipSame", shipSame);
		}else{// if not
			String shipBlkNo = ((Textbox)this.getFellow("shipBlkNo")).getValue();
			details.put("shipBlkNo", shipBlkNo);
			String shipUnitNo = ((Textbox)this.getFellow("shipUnitNo")).getValue();
			details.put("shipUnitNo", shipUnitNo);
			String shipStreet = ((Textbox)this.getFellow("shipStreet")).getValue();
			details.put("shipStreet", shipStreet);
			String shipBuilding = ((Textbox)this.getFellow("shipBuilding")).getValue();
			details.put("shipBuilding", shipBuilding);
			String shipArea = ((Textbox)this.getFellow("shipArea")).getValue();
			details.put("shipArea", shipArea);
			String shipCountryCode = (String)((Listbox)this.getFellow("shipCountryList")).getSelectedItem().getValue();
			details.put("shipCountryCode", shipCountryCode);
			String shipCity = ((Textbox)this.getFellow("shipCity")).getValue();
			details.put("shipCity", shipCity);
			String shipState = ((Textbox)this.getFellow("shipState")).getValue();
			details.put("shipState", shipState);
			String shipPostal = ((Textbox)this.getFellow("shipPostal")).getValue();
			details.put("shipPostal", shipPostal);
		}
		// job status
		String jobStatusCode = (String)((Listbox)this.getFellow("jobStatusList")).getSelectedItem().getValue();
		details.put("jobStatusCode", jobStatusCode);
		// occupation
		String occupation = ((Textbox)this.getFellow("occupation")).getValue();
		details.put("occupation", occupation);
		// industry
		String industryCode = (String)((Listbox)this.getFellow("industryList")).getSelectedItem().getValue();
		details.put("industryCode", industryCode);
		// emp name
		String employerName = ((Textbox)this.getFellow("employerName")).getValue();
		details.put("employerName", employerName);
		// employer address
		String empBlkNo = ((Textbox)this.getFellow("empBlkNo")).getValue();
		details.put("empBlkNo", empBlkNo);
		String empUnitNo = ((Textbox)this.getFellow("empUnitNo")).getValue();
		details.put("empUnitNo", empUnitNo);
		String empStreet = ((Textbox)this.getFellow("empStreet")).getValue();
		details.put("empStreet", empStreet);
		String empBuilding = ((Textbox)this.getFellow("empBuilding")).getValue();
		details.put("empBuilding", empBuilding);
		String empArea = ((Textbox)this.getFellow("empArea")).getValue();
		details.put("empArea", empArea);
		String empCountryCode = (String)((Listbox)this.getFellow("empCountryList")).getSelectedItem().getValue();
		details.put("empCountryCode", empCountryCode);
		String empCity = ((Textbox)this.getFellow("empCity")).getValue();
		details.put("empCity", empCity);
		String empState = ((Textbox)this.getFellow("empState")).getValue();
		details.put("empState", empState);
		String empPostal = ((Textbox)this.getFellow("empPostal")).getValue();
		details.put("empPostal", empPostal);
		// monthly income
		BigDecimal monthlyIncome = ((Decimalbox)this.getFellow("monthlyIncome")).getValue();
		details.put("monthlyIncome", monthlyIncome);
		// employment length
		BigDecimal empLength = ((Decimalbox)this.getFellow("empLength")).getValue();
		details.put("empLength", empLength);
		
		details.put("printTaxInvoiceOnly", "N");
		
		String recurring = (String)((Listbox)this.getFellow("recurring")).getSelectedItem().getValue();
		if(recurring.equals("-")){
			throw new IBSException(
					"Value "+recurring+" not allowed");
			
		}else {
			details.put("recurring", recurring);
		}
		
		return details;
	}
	public void checkBill(Listitem selected){
		logger.info("checkBill(Listitem selected)");
		Rows rows = (Rows)this.getFellow("billAddress");
		if(selected.getValue().equals(NonConfigurableConstants.BOOLEAN_NO)){
			// displaying all rows
			for(Object eachRow : rows.getChildren()){
				((Row)eachRow).setVisible(true);
			}
		}else{
			for(int i=1;i<rows.getChildren().size();i++){
				// hiding all rows except first row
				((Row)rows.getChildren().get(i)).setVisible(false);
			}
		}
	}
	public void checkShip(Listitem selected){
		logger.info("checkShip(Listitem selected)");
		Rows rows = (Rows)this.getFellow("shipAddress");
		if(selected.getValue().equals(NonConfigurableConstants.BOOLEAN_NO)){
			// displaying all rows
			for(Object eachRow : rows.getChildren()){
				((Row)eachRow).setVisible(true);
			}
		}else{
			for(int i=1;i<rows.getChildren().size();i++){
				// hiding all rows except first row
				((Row)rows.getChildren().get(i)).setVisible(false);
			}
		}
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
	public List<Listitem> getRace() {
		return cloneList(race);
	}
}