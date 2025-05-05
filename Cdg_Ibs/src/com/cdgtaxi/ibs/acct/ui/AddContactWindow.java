package com.cdgtaxi.ibs.acct.ui;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;

import com.cdgtaxi.ibs.common.ConfigurableConstants;
import com.cdgtaxi.ibs.common.NonConfigurableConstants;
import com.cdgtaxi.ibs.util.ComponentUtil;

public class AddContactWindow extends CommonAcctWindow {
	private static final long serialVersionUID = 1L;
	private List<Listitem> salutations = ComponentUtil.convertToListitems(ConfigurableConstants.getSalutations(), false);
	private List<Listitem> race = ComponentUtil.convertToListitems(ConfigurableConstants.getRace(), false);
	private List<Listitem> booleans = ComponentUtil.convertToListitems(NonConfigurableConstants.BOOLEAN_YN, true);
	private List<Listitem> countries = ComponentUtil.convertToListitems(ConfigurableConstants.getCountries(), true);
	private static final Logger logger = Logger.getLogger(AddContactWindow.class);
	private String custNo;
	
	@SuppressWarnings("unchecked")
	public AddContactWindow() throws InterruptedException{
		logger.info("AddContactWindow()");
		//account number
		Map<String, String> map = Executions.getCurrent().getArg();
		custNo = map.get("custNo");
		if(custNo==null || custNo.length()==0){
			Messagebox.show("No account number found!", "Create Contact", Messagebox.OK, Messagebox.ERROR);
			this.back();
		}
	}
	public void init(){
		if(custNo!=null){
			Map<String, String> corpAddress = this.businessHelper.getAccountBusiness().getCorporateAddress(custNo);
			((Textbox)this.getFellow("blkNo")).setText(corpAddress.get("corpBlock"));
			((Textbox)this.getFellow("unitNo")).setText(corpAddress.get("corpUnit"));
			((Textbox)this.getFellow("street")).setText(corpAddress.get("corpStreet"));
			((Textbox)this.getFellow("building")).setText(corpAddress.get("corpBuilding"));
			((Textbox)this.getFellow("area")).setText(corpAddress.get("corpArea"));
			Listbox countryList = (Listbox)this.getFellow("countryList");
			for(Object countryObj : countryList.getItems()){
				Listitem countryItem = (Listitem)countryObj;
				if(countryItem.getValue().equals(corpAddress.get("corpCountryCode"))){
					countryItem.setSelected(true);
					break;
				}
			}
			((Textbox)this.getFellow("city")).setText(corpAddress.get("corpCity"));
			((Textbox)this.getFellow("state")).setText(corpAddress.get("corpState"));
			((Textbox)this.getFellow("postal")).setText(corpAddress.get("corpPostal"));
		}
	}
	public void save() throws InterruptedException{
		Listbox countryList = (Listbox)this.getFellow("countryList");
		if(!countryList.getSelectedItem().getValue().equals("SG")){
			String city = ((Textbox)this.getFellow("city")).getValue();
			if(city==null || city.length()==0){
				Messagebox.show("Please enter city", "Create Contact", Messagebox.OK, Messagebox.INFORMATION);
				return;
			}
			String state = ((Textbox)this.getFellow("state")).getValue();
			if(state==null || state.length()==0){
				Messagebox.show("Please enter state", "Create Contact", Messagebox.OK, Messagebox.INFORMATION);
				return;
			}
		}
		// checking for main contact
		Checkbox isMainBilling = ((Checkbox)this.getFellow("isMainBilling"));
		Checkbox isMainShipping = ((Checkbox)this.getFellow("isMainShipping"));
		if(isMainBilling.isChecked() || isMainShipping.isChecked()){
			Map<String, Integer> mainContacts = this.businessHelper.getAccountBusiness().getAccountMainContact(custNo);
			if(isMainBilling.isChecked() && isMainShipping.isChecked() && mainContacts.size()==2){
				if(Messagebox.show("There are existing main billing and shipping contacts. Continue?", "Create Contact", Messagebox.OK | Messagebox.CANCEL, Messagebox.INFORMATION) == Messagebox.CANCEL){
					return;
				}
			}else if(isMainBilling.isChecked()){
				if(mainContacts.get("mainBilling")!=null){
					if(Messagebox.show("There is existing main billing contact. Continue?", "Create Contact", Messagebox.OK | Messagebox.CANCEL, Messagebox.INFORMATION) == Messagebox.CANCEL){
						return;
					}
				}
			}else if(isMainShipping.isChecked()){
				if(mainContacts.get("mainShipping")!=null){
					if(Messagebox.show("There is existing main shipping contact. Continue?", "Create Contact", Messagebox.OK | Messagebox.CANCEL, Messagebox.INFORMATION) == Messagebox.CANCEL){
						return;
					}
				}
			}
		}
		if(this.businessHelper.getAccountBusiness().createContact(getContactInput())){
			Messagebox.show("Contact Created", "Create Contact", Messagebox.OK, Messagebox.INFORMATION);
			this.back();
		}else{
			Messagebox.show("Unable to save contact. Please try again later", "Create Contact", Messagebox.OK, Messagebox.ERROR);
		}
	}
	protected Map<String, String> getContactInput(){
		Map<String, String> contactDetails = new HashMap<String, String>();
		contactDetails.put("custNo", custNo);
		contactDetails.put("userId", this.getUserLoginIdAndDomain());
		Listbox mainContactSalList = (Listbox)this.getFellow("mainContactSal");
		if(mainContactSalList.getSelectedItem()!=null && mainContactSalList.getSelectedItem().getValue()!=null){
			contactDetails.put("mainContactSalCode", (String)mainContactSalList.getSelectedItem().getValue());
		}
		Listbox mainContactRaceList = (Listbox)this.getFellow("mainContactRace");
		if(mainContactRaceList.getSelectedItem()!=null && mainContactRaceList.getSelectedItem().getValue()!=null){
			contactDetails.put("mainContactRace", (String)mainContactRaceList.getSelectedItem().getValue());
		}
		contactDetails.put("mainContactName", ((Textbox)this.getFellow("mainContactName")).getValue());
		contactDetails.put("mainContactTitle", ((Textbox)this.getFellow("mainContactTitle")).getValue());
		contactDetails.put("mainContactTel", ((Textbox)this.getFellow("mainContactTel")).getValue());
		contactDetails.put("mainContactMobile", ((Textbox)this.getFellow("mainContactMobile")).getValue());
		contactDetails.put("mainContactFax", ((Textbox)this.getFellow("mainContactFax")).getValue());
		contactDetails.put("mainContactEmail", ((Textbox)this.getFellow("mainContactEmail")).getValue());
		if(((Checkbox)this.getFellow("isMainBilling")).isChecked()){
			contactDetails.put("mainBilling", "Y");
		}
		if(((Checkbox)this.getFellow("isMainShipping")).isChecked()){
			contactDetails.put("mainShipping", "Y");
		}
		if(((Checkbox)this.getFellow("updateCostCentre")).isChecked()){
			contactDetails.put("updateCostCentre", "Y");
		}else{
			contactDetails.put("updateCostCentre", "N");
		}
		Listbox subContactSalList = (Listbox)this.getFellow("subContactSal");
		if(subContactSalList.getSelectedItem()!=null && subContactSalList.getSelectedItem().getValue()!=null){
			contactDetails.put("subContactSalCode", (String)subContactSalList.getSelectedItem().getValue());
		}
		Listbox subContactRaceList = (Listbox)this.getFellow("subContactRace");
		if(subContactRaceList.getSelectedItem()!=null && subContactRaceList.getSelectedItem().getValue()!=null){
			contactDetails.put("subContactRace", (String)subContactRaceList.getSelectedItem().getValue());
		}
		contactDetails.put("subContactName", ((Textbox)this.getFellow("subContactName")).getValue());
		contactDetails.put("subContactTitle", ((Textbox)this.getFellow("subContactTitle")).getValue());
		contactDetails.put("subContactTel", ((Textbox)this.getFellow("subContactTel")).getValue());
		contactDetails.put("subContactMobile", ((Textbox)this.getFellow("subContactMobile")).getValue());
		contactDetails.put("subContactFax", ((Textbox)this.getFellow("subContactFax")).getValue());
		contactDetails.put("subContactEmail", ((Textbox)this.getFellow("subContactEmail")).getValue());
		Listbox booleansList = (Listbox)this.getFellow("useCorpAddress");
		if(booleansList.getSelectedItem().getValue().equals(NonConfigurableConstants.BOOLEAN_YN_YES)){
			contactDetails.put("useCorpAddress", "Y");
		}else{
			contactDetails.put("blkNo", ((Textbox)this.getFellow("blkNo")).getValue());
			contactDetails.put("unitNo", ((Textbox)this.getFellow("unitNo")).getValue());
			contactDetails.put("street", ((Textbox)this.getFellow("street")).getValue());
			contactDetails.put("building", ((Textbox)this.getFellow("building")).getValue());
			contactDetails.put("area", ((Textbox)this.getFellow("area")).getValue());
			Listbox countryList = (Listbox)this.getFellow("countryList");
			String countryCode = (String)countryList.getSelectedItem().getValue();
			contactDetails.put("countryCode", countryCode);
			if(!countryCode.equals("SG")){
				contactDetails.put("city", ((Textbox)this.getFellow("city")).getValue());
				contactDetails.put("state", ((Textbox)this.getFellow("state")).getValue());
			}else{
				contactDetails.put("city", null);
				contactDetails.put("state", null);
			}
			contactDetails.put("postal", ((Textbox)this.getFellow("postal")).getValue());
		}
		return contactDetails;
	}
	public void updateAddress(Listitem selected){
		if(selected.getValue().equals(NonConfigurableConstants.BOOLEAN_YN_YES)){
			displayAddress(false);
		}else{
			displayAddress(true);
		}
	}
	private void displayAddress(boolean display){
		this.getFellow("blkNo").getParent().setVisible(display);
		this.getFellow("street").getParent().setVisible(display);
		this.getFellow("area").getParent().setVisible(display);
		this.getFellow("city").getParent().setVisible(display);
		this.getFellow("postal").getParent().setVisible(display);
	}
	@Override
	public void refresh() throws InterruptedException {
	}
	public List<Listitem> getSalutations() {
		return cloneList(salutations);
	}
	public List<Listitem> getRace() {
		return cloneList(race);
	}
	public List<Listitem> getBooleans() {
		return booleans;
	}
	public List<Listitem> getCountries() {
		for(Listitem country : countries){
			if(country.getValue().equals(ConfigurableConstants.COUNTRY_MASTER_CODE_SG)){
				country.setSelected(true);
			}
		}
		return countries;
	}
}
