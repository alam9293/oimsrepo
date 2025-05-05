package com.cdgtaxi.ibs.acct.ui;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.log4j.Logger;
import org.zkoss.zhtml.Messagebox;
import org.zkoss.zk.ui.Components;
import org.zkoss.zk.ui.ext.AfterCompose;
import org.zkoss.zul.Button;
import org.zkoss.zul.Comboitem;
import org.zkoss.zul.Constraint;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listitem;

import com.cdgtaxi.ibs.common.NonConfigurableConstants;
import com.cdgtaxi.ibs.common.model.AmtbContactPerson;
import com.cdgtaxi.ibs.util.ComponentUtil;
import com.cdgtaxi.ibs.web.component.Combobox;

@SuppressWarnings("unchecked")
public class TransferContactWindow extends CommonAcctWindow implements AfterCompose{
	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(TransferContactWindow.class);

	private Listbox contactPersonTypeList;
	private Intbox custNoIB;
	
	public TransferContactWindow() {
		logger.info("TransferAccountWindow()");
	}
	
	public void afterCompose() {
		Components.wireVariables(this, this);
		contactPersonTypeList.appendChild(ComponentUtil.createNotRequiredListItem());
		contactPersonTypeList.appendChild(ComponentUtil.createNotRequiredAllListItem());
		for(Entry<String, String> entry : NonConfigurableConstants.MAIN_CONTACT.entrySet()){
			Listitem item = new Listitem();
			item.setLabel(entry.getValue());
			item.setValue(entry.getKey());
			contactPersonTypeList.appendChild(item);
		}
		contactPersonTypeList.setSelectedIndex(0);
	}

	public void searchAccountByAccountName(String name, Combobox combo) throws InterruptedException {
		logger.info("searchAccountByAccountName(name = " + name + ", combo = " + combo.getChildren().size() + ")");
		// only begin new search if input is greater than 2
		if (name == null || name.length() < 3)
			return;
		// accountName still the same as selected one, skip
		if (combo.getSelectedItem() != null) {
			if (name.equals(combo.getSelectedItem().getLabel()))
				return;
		}
		// clear textbox for a new search
		Constraint originalConstraint = custNoIB.getConstraint();
		Constraint nullConstraint = null;
		custNoIB.setConstraint(nullConstraint);
		custNoIB.setText("");
		custNoIB.setConstraint(originalConstraint);
		// Clear list for every new search
		combo.getChildren().clear();
		try {
			Map<String, String> accounts = this.businessHelper.getReportBusiness().searchAccount(null, name.toUpperCase());
			for (String custNo : accounts.keySet()) {
				Comboitem item = new Comboitem(accounts.get(custNo) + " (" + custNo + ")");
				item.setValue(custNo);
				combo.appendChild(item);
			}
			if (accounts.size() == 1) {
				combo.setSelectedIndex(0);
				this.onSelectAccountName(custNoIB);
			} else
				combo.open();
		} catch (Exception e) {
			Messagebox.show(com.cdgtaxi.ibs.common.NonConfigurableConstants.COMMON_ERROR_MESSAGE, "Error", Messagebox.OK, Messagebox.ERROR);
			e.printStackTrace();
		}
	}

	public void onSelectAccountName(Intbox intbox) throws InterruptedException {
		logger.info("onSelectAccountName(intbox = " + intbox.getValue() + ")");
		try {
			Combobox accountNameComboBox = (Combobox) this.getFellow("custName");
			if (accountNameComboBox.getSelectedItem() != null) {
				String custNo = (String) accountNameComboBox.getSelectedItem().getValue();
				intbox.setText(custNo);
				updateContacts();
			}
		} catch (Exception e) {
			Messagebox.show(com.cdgtaxi.ibs.common.NonConfigurableConstants.COMMON_ERROR_MESSAGE, "Error", Messagebox.OK, Messagebox.ERROR);
			e.printStackTrace();
		}
	}

	public void searchAccountByAccountNo(Integer customerNo, Intbox intbox) throws InterruptedException {
		logger.info("searchAccountByAccountNo(customerNo = " + customerNo + ", intbox = " + intbox.getValue() + ")");
		Combobox accountNameComboBox = (Combobox) this.getFellow("custName");
		if (customerNo == null || customerNo < 0) {
			accountNameComboBox.getChildren().clear();
			accountNameComboBox.setText("");
			Listbox fromContactList = (Listbox) this.getFellow("fromContactList");
			Listbox toContactList = (Listbox) this.getFellow("toContactList");
			Listbox acctList = (Listbox) this.getFellow("acctList");
			fromContactList.getChildren().clear();
			toContactList.getChildren().clear();
			acctList.getChildren().clear();
			fromContactList.setDisabled(true);
			toContactList.setDisabled(true);
			acctList.setDisabled(true);
			return;
		}
		// accountName still the same as selected one, skip
		if (accountNameComboBox.getSelectedItem() != null) {
			String selectedNo = (String) accountNameComboBox.getSelectedItem().getValue();
			if (selectedNo.equals(customerNo))
				return;
		}
		// Clear combobox for a new search
		accountNameComboBox.setText("");
		// Clear list for every new search
		accountNameComboBox.getChildren().clear();
		// Clear division + department
		// append zero in front
		// customerNo = StringUtil.appendLeft(customerNo, 3, "0");
		try {
			Map<String, String> accounts = this.businessHelper.getReportBusiness().searchAccount(customerNo.toString(), null);
			for (String custNo : accounts.keySet()) {
				Comboitem item = new Comboitem(accounts.get(custNo) + " (" + custNo + ")");
				item.setValue(custNo);
				accountNameComboBox.appendChild(item);
			}
			if (accounts.size() == 1) {
				accountNameComboBox.setSelectedIndex(0);
				this.onSelectAccountName(intbox);
			} else {
				accountNameComboBox.open();
				intbox.focus();
			}
		} catch (Exception e) {
			Messagebox.show(com.cdgtaxi.ibs.common.NonConfigurableConstants.COMMON_ERROR_MESSAGE, "Error", Messagebox.OK, Messagebox.ERROR);
			e.printStackTrace();
		}
	}

	public void updateAccounts() {
		logger.info("updateAccounts()");
		Listbox contactListBox = (Listbox) this.getFellow("fromContactList");
		String fromContact = contactListBox.getSelectedItem() != null ? (String) contactListBox.getSelectedItem().getValue() : null;
		contactListBox = (Listbox) this.getFellow("toContactList");
		String toContact = contactListBox.getSelectedItem() != null ? (String) contactListBox.getSelectedItem().getValue() : null;
		Listbox acctListbox = (Listbox) this.getFellow("acctList");
		Button submitBtn = (Button) this.getFellow("submitBtn");
		String contactPersonType = (String) contactPersonTypeList.getSelectedItem().getValue();
		if (fromContact != null && fromContact.length() != 0 && toContact != null && toContact.length() != 0 && !fromContact.equals(toContact)) {
			acctListbox.getChildren().clear();
			acctListbox.setDisabled(false);
			submitBtn.setDisabled(false);
			Map<Integer, String> accts = this.businessHelper.getAccountBusiness().searchAccounts(fromContact, contactPersonType);
			for (Integer acctNo : accts.keySet()) {
				acctListbox.appendChild(new Listitem(accts.get(acctNo), acctNo));
			}
		} else {
			acctListbox.getChildren().clear();
			acctListbox.setDisabled(true);
			submitBtn.setDisabled(true);
		}
	}
	
	public void updateContacts() throws InterruptedException{
		try{
			Integer custNo = custNoIB.getValue();
			String contactPersonType = (String) contactPersonTypeList.getSelectedItem().getValue();
			if(custNo == null || (contactPersonType != null && contactPersonType.length() == 0)){
				Listbox contactListBox = (Listbox) this.getFellow("fromContactList");
				contactListBox.getChildren().clear();
				contactListBox.setDisabled(true);
				contactListBox = (Listbox) this.getFellow("toContactList");
				contactListBox.getChildren().clear();
				contactListBox.setDisabled(true);
				((Listbox) this.getFellow("acctList")).setDisabled(true);
				this.getFellow("acctList").getChildren().clear();
			}
			
			List<AmtbContactPerson> contactPersons = this.businessHelper.getAccountBusiness().getTransferContacts(custNo+"", contactPersonType);
			List<AmtbContactPerson> allContactPersons = this.businessHelper.getAccountBusiness().getTransferContacts(custNo+"", null);
			
			Listbox contactListBox = (Listbox) this.getFellow("fromContactList");
			contactListBox.getChildren().clear();
			contactListBox.setDisabled(false);
			contactListBox.appendChild(ComponentUtil.createNotRequiredListItem());
			
			for(AmtbContactPerson contactPerson : contactPersons){
				String name = contactPerson.getMainContactName();
				/*if(contactPerson.getMstbMasterTableByMainContactSal()!=null)
					name = contactPerson.getMstbMasterTableByMainContactSal().getMasterValue() + " " + name;*/
				
				Listitem newItem = new Listitem(name);
				newItem.setValue(contactPerson.getContactPersonNo().toString());
				contactListBox.appendChild(newItem);
			}
			
			contactListBox.setSelectedIndex(0);
			contactListBox = (Listbox) this.getFellow("toContactList");
			contactListBox.getChildren().clear();
			contactListBox.appendChild(ComponentUtil.createNotRequiredListItem());
			contactListBox.setDisabled(false);
			
			for(AmtbContactPerson contactPerson : allContactPersons){
				String name = contactPerson.getMainContactName();
				/*if(contactPerson.getMstbMasterTableByMainContactSal()!=null)
					name = contactPerson.getMstbMasterTableByMainContactSal().getMasterValue() + " " + name;*/
				
				Listitem newItem = new Listitem(name);
				newItem.setValue(contactPerson.getContactPersonNo().toString());
				contactListBox.appendChild(newItem);
			}
			
			contactListBox.setSelectedIndex(0);
			((Listbox) this.getFellow("acctList")).setDisabled(true);
			this.getFellow("acctList").getChildren().clear();
			
		} catch (Exception e) {
			Messagebox.show(com.cdgtaxi.ibs.common.NonConfigurableConstants.COMMON_ERROR_MESSAGE, "Error", Messagebox.OK, Messagebox.ERROR);
			e.printStackTrace();
		}
	}

	public void submit() throws InterruptedException {
		logger.info("submit()");
		Listbox acctList = (Listbox) this.getFellow("acctList");
		Set<Listitem> selectedAccts = acctList.getSelectedItems();
		if (selectedAccts == null || selectedAccts.size() == 0) {
			Messagebox.show("Please select one or more accounts", "Transfer Contact", Messagebox.OK, Messagebox.EXCLAMATION);
			return;
		}
		if (Messagebox.show("Confirm transfer?", "Transfer Contact", Messagebox.OK | Messagebox.CANCEL, Messagebox.QUESTION) == Messagebox.OK) {
			Listbox contactListBox = (Listbox) this.getFellow("fromContactList");
			String fromContact = contactListBox.getSelectedItem() != null ? (String) contactListBox.getSelectedItem().getValue() : null;
			contactListBox = (Listbox) this.getFellow("toContactList");
			String toContact = contactListBox.getSelectedItem() != null ? (String) contactListBox.getSelectedItem().getValue() : null;
			String contactPersonType = (String) contactPersonTypeList.getSelectedItem().getValue();
			List<Integer> selectedAcctNos = new ArrayList<Integer>();
			for (Listitem selectedAcct : selectedAccts) {
				selectedAcctNos.add((Integer) selectedAcct.getValue());
			}
			this.businessHelper.getAccountBusiness().transferContact(fromContact, toContact, selectedAcctNos, getUserLoginIdAndDomain(), contactPersonType);
			Messagebox.show("Accounts transferred", "Transfer Accounts", Messagebox.OK, Messagebox.INFORMATION);
			this.refresh();
		}
	}

	@Override
	public void refresh() throws InterruptedException {
		((Listbox) this.getFellow("acctList")).setDisabled(true);
		this.getFellow("acctList").getChildren().clear();
		((Listbox) this.getFellow("fromContactList")).setDisabled(true);
		this.getFellow("fromContactList").getChildren().clear();
		((Listbox) this.getFellow("toContactList")).setDisabled(true);
		this.getFellow("toContactList").getChildren().clear();
		((Combobox)this.getFellow("custName")).getChildren().clear();
		((Combobox)this.getFellow("custName")).setText("");
		custNoIB.setValue(null);
		contactPersonTypeList.setSelectedIndex(0);
	}

	
}