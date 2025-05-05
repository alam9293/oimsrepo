package com.cdgtaxi.ibs.acct.ui;

import java.util.Map;

import org.apache.log4j.Logger;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Label;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;

import com.cdgtaxi.ibs.common.NonConfigurableConstants;

public class EditContactWindow extends AddContactWindow {
	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(EditContactWindow.class);
	private String custNo, contactPersonNo, acctStatus;
	@SuppressWarnings("unchecked")
	public EditContactWindow() throws InterruptedException{
		super();
		logger.info("EditContactWindow()");
		//account number
		Map<String, String> map = Executions.getCurrent().getArg();
		custNo = map.get("custNo");
		if(custNo==null || custNo.trim().length()==0){
			Messagebox.show("No account number found!", "Edit Contact", Messagebox.OK, Messagebox.ERROR);
		}
		// getting contact person id
		contactPersonNo = map.get("contactPersonNo");
		// checking account status. if null, show error and back
		if(contactPersonNo==null || contactPersonNo.trim().length()==0){
			Messagebox.show("No Contact Person!", "Edit Contact", Messagebox.OK, Messagebox.ERROR);
		}
		// getting account status
		acctStatus = map.get("acctStatus");
		// checking account status. if null, show error and back
		if(acctStatus==null || acctStatus.trim().length()==0){
			Messagebox.show("No account status!", "Edit Contact", Messagebox.OK, Messagebox.ERROR);
		}
	}
	public void init(){
		Map<String, String> contactDetails = this.businessHelper.getAccountBusiness().getContact(contactPersonNo);
		if(contactDetails.get("mainBilling")!=null){
			((Checkbox)this.getFellow("isMainBilling")).setChecked(true);
			if(!acctStatus.equals(NonConfigurableConstants.ACCOUNT_STATUS_PENDING_ACTIVATION)){
				((Checkbox)this.getFellow("isMainBilling")).setDisabled(true);
			}
		}
		if(contactDetails.get("mainShipping")!=null){
			((Checkbox)this.getFellow("isMainShipping")).setChecked(true);
			if(!acctStatus.equals(NonConfigurableConstants.ACCOUNT_STATUS_PENDING_ACTIVATION)){
				((Checkbox)this.getFellow("isMainShipping")).setDisabled(true);
			}
		}
		if(contactDetails.get("updateCostCentre")!=null){
			if(contactDetails.get("updateCostCentre").equals(NonConfigurableConstants.BOOLEAN_YN_YES)){
				((Checkbox)this.getFellow("updateCostCentre")).setChecked(true);
			}
		}
		if(contactDetails.get("mainContactSalCode")!=null){
			Listbox mainContactSalList = (Listbox)this.getFellow("mainContactSal");
			for(Object item : mainContactSalList.getItems()){
				if(((Listitem)item).getValue().equals(contactDetails.get("mainContactSalCode"))){
					((Listitem)item).setSelected(true);
					break;
				}
			}
		}
		if(contactDetails.get("mainContactRace")!=null){
			Listbox mainContactRaceList = (Listbox)this.getFellow("mainContactRace");
			for(Object item : mainContactRaceList.getItems()){
				if(((Listitem)item).getValue().equals(contactDetails.get("mainContactRace"))){
					((Listitem)item).setSelected(true);
					break;
				}
			}
		}
		((Textbox)this.getFellow("mainContactName")).setValue(contactDetails.get("mainContactName"));
		((Textbox)this.getFellow("mainContactTitle")).setValue(contactDetails.get("mainContactTitle"));
		((Textbox)this.getFellow("mainContactTel")).setValue(contactDetails.get("mainContactTel"));
		((Textbox)this.getFellow("mainContactMobile")).setValue(contactDetails.get("mainContactMobile"));
		((Textbox)this.getFellow("mainContactFax")).setValue(contactDetails.get("mainContactFax"));
		((Textbox)this.getFellow("mainContactEmail")).setValue(contactDetails.get("mainContactEmail"));
		if(contactDetails.get("subContactSalCode")!=null){
			Listbox subContactSalList = (Listbox)this.getFellow("subContactSal");
			for(Object item : subContactSalList.getItems()){
				if(((Listitem)item).getValue().equals(contactDetails.get("subContactSalCode"))){
					((Listitem)item).setSelected(true);
					break;
				}
			}
		}
		((Textbox)this.getFellow("subContactName")).setValue(contactDetails.get("subContactName"));
		((Textbox)this.getFellow("subContactTitle")).setValue(contactDetails.get("subContactTitle"));
		((Textbox)this.getFellow("subContactTel")).setValue(contactDetails.get("subContactTel"));
		((Textbox)this.getFellow("subContactMobile")).setValue(contactDetails.get("subContactMobile"));
		((Textbox)this.getFellow("subContactFax")).setValue(contactDetails.get("subContactFax"));
		((Textbox)this.getFellow("subContactEmail")).setValue(contactDetails.get("subContactEmail"));
		if(contactDetails.get("subContactRace")!=null){
			Listbox subContactRaceList = (Listbox)this.getFellow("subContactRace");
			for(Object item : subContactRaceList.getItems()){
				if(((Listitem)item).getValue().equals(contactDetails.get("subContactRace"))){
					((Listitem)item).setSelected(true);
					break;
				}
			}
		}
		((Textbox)this.getFellow("blkNo")).setValue(contactDetails.get("blkNo"));
		((Textbox)this.getFellow("unitNo")).setValue(contactDetails.get("unitNo"));
		((Textbox)this.getFellow("street")).setValue(contactDetails.get("street"));
		((Textbox)this.getFellow("building")).setValue(contactDetails.get("building"));
		((Textbox)this.getFellow("area")).setValue(contactDetails.get("area"));
		if(contactDetails.get("countryCode")!=null){
			Listbox countryList = (Listbox)this.getFellow("countryList");
			for(Object item : countryList.getItems()){
				if(((Listitem)item).getValue().equals(contactDetails.get("countryCode"))){
					((Listitem)item).setSelected(true);
					this.checkCountry((Listitem)item, (Textbox)this.getFellow("city"), (Textbox)this.getFellow("state"));
					break;
				}
			}
		}
		((Textbox)this.getFellow("city")).setValue(contactDetails.get("city"));
		((Textbox)this.getFellow("state")).setValue(contactDetails.get("state"));
		((Textbox)this.getFellow("postal")).setValue(contactDetails.get("postal"));
		Listbox booleans = (Listbox)this.getFellow("useCorpAddress");
		for(Object item : booleans.getItems()){
			if(((Listitem)item).getValue().equals(contactDetails.get("useCorpAddress"))){
				((Listitem)item).setSelected(true);
				break;
			}
		}
		if(booleans.getSelectedItem()==null){
			booleans.setSelectedIndex(0);
		}
		updateAddress(booleans.getSelectedItem());
		
		Label contactPersonIdLabel = (Label)this.getFellow("contactPersonId");
		contactPersonIdLabel.setValue(contactPersonNo);
	}
	public void delete() throws InterruptedException{
		if(Messagebox.show("Confirm Delete?", "Edit Contact", Messagebox.OK | Messagebox.CANCEL, Messagebox.QUESTION) == Messagebox.OK){
			if(!acctStatus.equals(NonConfigurableConstants.ACCOUNT_STATUS.get(NonConfigurableConstants.ACCOUNT_STATUS_PENDING_ACTIVATION))){
				if(this.businessHelper.getAccountBusiness().isMainContact(contactPersonNo)){
					Messagebox.show("Unable to delete. Contact is main contact", "Edit Contact", Messagebox.OK, Messagebox.ERROR);
					return;
				}
			}
			if(this.businessHelper.getAccountBusiness().deleteContact(contactPersonNo)){
				Messagebox.show("Contact Deleted.", "Edit Contact", Messagebox.OK, Messagebox.INFORMATION);
				this.back();
			}else{
				Messagebox.show("Unable to delete contact. Please try again.", "Edit Contact", Messagebox.OK, Messagebox.ERROR);
			}
		}
	}
	public void save() throws InterruptedException{
		Listbox countryList = (Listbox)this.getFellow("countryList");
		if(!countryList.getSelectedItem().getValue().equals("SG")){
			String city = ((Textbox)this.getFellow("city")).getValue();
			if(city==null || city.length()==0){
				Messagebox.show("Please enter city", "Edit Contact", Messagebox.OK, Messagebox.INFORMATION);
				return;
			}
			String state = ((Textbox)this.getFellow("state")).getValue();
			if(state==null || state.length()==0){
				Messagebox.show("Please enter state", "Edit Contact", Messagebox.OK, Messagebox.INFORMATION);
				return;
			}
		}
		// checking for main contact
		Checkbox isMainBilling = ((Checkbox)this.getFellow("isMainBilling"));
		Checkbox isMainShipping = ((Checkbox)this.getFellow("isMainShipping"));
		if((isMainBilling.isChecked()&&!isMainBilling.isDisabled()) || (isMainShipping.isChecked()&&!isMainShipping.isDisabled())){
			Map<String, Integer> mainContacts = this.businessHelper.getAccountBusiness().getAccountMainContact(custNo);
			if((isMainBilling.isChecked()&&!isMainBilling.isDisabled()) && (isMainShipping.isChecked()&&!isMainShipping.isDisabled()) && mainContacts.size()==2){
				if(Messagebox.show("There are existing main billing and shipping contacts. Continue?", "Edit Contact", Messagebox.OK | Messagebox.CANCEL, Messagebox.INFORMATION) == Messagebox.CANCEL){
					return;
				}
			}else if(isMainBilling.isChecked()&&!isMainBilling.isDisabled()){
				if(mainContacts.get("mainBilling")!=null){
					if(Messagebox.show("There is existing main billing contact. Continue?", "Edit Contact", Messagebox.OK | Messagebox.CANCEL, Messagebox.INFORMATION) == Messagebox.CANCEL){
						return;
					}
				}
			}else if(isMainShipping.isChecked()&&!isMainShipping.isDisabled()){
				if(mainContacts.get("mainShipping")!=null){
					if(Messagebox.show("There is existing main shipping contact. Continue?", "Edit Contact", Messagebox.OK | Messagebox.CANCEL, Messagebox.INFORMATION) == Messagebox.CANCEL){
						return;
					}
				}
			}
		}
		Map<String, String> contactDetails = getContactInput();
		contactDetails.put("contactPersonNo", contactPersonNo);
		contactDetails.put("userId", this.getUserLoginIdAndDomain());
		if(this.businessHelper.getAccountBusiness().updateContact(contactDetails)){
			Messagebox.show("Contact Updated!", "Edit Contact", Messagebox.OK, Messagebox.INFORMATION);
			this.back();
		}else{
			Messagebox.show("Unable to update contact. Please try again later", "Edit Contact", Messagebox.OK, Messagebox.ERROR);
		}
	}
}
