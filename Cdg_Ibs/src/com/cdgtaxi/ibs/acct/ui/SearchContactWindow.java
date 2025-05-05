package com.cdgtaxi.ibs.acct.ui;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.SuspendNotAllowedException;
import org.zkoss.zul.Button;
import org.zkoss.zul.Label;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listfooter;
import org.zkoss.zul.Listheader;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;

import com.cdgtaxi.ibs.Uri;
import com.cdgtaxi.ibs.common.NonConfigurableConstants;

public class SearchContactWindow extends CommonInnerAcctWindow {
	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(SearchContactWindow.class);
	private String custNo, acctStatus;
	private String acctType, createdDt;
	@SuppressWarnings("unchecked")
	public SearchContactWindow() throws InterruptedException{
		logger.info("SearchContactWindow()");
		//account number
		Map<String, String> map = Executions.getCurrent().getArg();
		custNo = map.get("custNo");
		if(custNo==null || custNo.trim().length()==0){
			Messagebox.show("No account number found!", "Search Contact", Messagebox.OK, Messagebox.ERROR);
		}
		// getting account status
		acctStatus = map.get("acctStatus");
		// checking account status. if null, show error and back
		if(acctStatus==null || acctStatus.trim().length()==0){
			Messagebox.show("No Account Status!", "Search Contact", Messagebox.OK, Messagebox.ERROR);
		}
		
		acctType = map.get("acctType");
		createdDt = map.get("createdDt");

	}
	public void init() throws InterruptedException{
		
		((Label)this.getFellow("custNo")).setValue(custNo);
		((Label)this.getFellow("acctStatus")).setValue(acctStatus);
		((Label)this.getFellow("acctType")).setValue(acctType);
		((Label)this.getFellow("createdDt")).setValue(createdDt);
		
		if(acctStatus.equals(NonConfigurableConstants.ACCOUNT_STATUS.get(NonConfigurableConstants.ACCOUNT_STATUS_CLOSED))){
			Button createBtn = (Button)this.getFellow("createBtn");
			createBtn.setDisabled(true);
		}
		
		if(!this.checkUriAccess(Uri.ADD_CONTACT))
			((Button)this.getFellow("createBtn")).setDisabled(true);
	}
	public void search() throws InterruptedException{
		logger.info("search()");
		String mainContactName = ((Textbox)this.getFellow("mainContactName")).getText();
		String mainContactEmail = ((Textbox)this.getFellow("mainContactEmail")).getText();
		String mainContactTel = ((Textbox)this.getFellow("mainContactTel")).getText();
		String mainContactMobile = ((Textbox)this.getFellow("mainContactMobile")).getText();
		displayProcessing();
		Map<Integer, Map<String, String>> contacts = this.businessHelper.getAccountBusiness().searchContacts(custNo, mainContactName, mainContactEmail, mainContactTel, mainContactMobile);
		Listbox contactsBox = (Listbox)this.getFellow("contacts");
		contactsBox.getItems().clear();
		if(contacts.isEmpty()){
			((Listfooter)this.getFellow("noRecordFooter")).setVisible(true);
		}else{
			((Listfooter)this.getFellow("noRecordFooter")).setVisible(false);
			for(Integer contactNo : contacts.keySet()){
				Map<String, String> contact = contacts.get(contactNo);
				Listitem contactItem = new Listitem();
				contactItem.setValue(contactNo.toString());
				contactItem.appendChild(new Listcell(contact.get("mainContactName")));
				// removed on 30th Sept
				//contactItem.appendChild(new Listcell(contact.get("mainContactTitle")==null ? "-" : contact.get("mainContactTitle")));
				contactItem.appendChild(new Listcell(contact.get("mainContactTel")));
				contactItem.appendChild(new Listcell(contact.get("mainContactMobile")==null ? "-" : contact.get("mainContactMobile")));
				contactItem.appendChild(new Listcell(contact.get("mainContactFax")==null ? "-" : contact.get("mainContactFax")));
				contactItem.appendChild(new Listcell(contact.get("mainContactEmail")==null ? "-" : contact.get("mainContactEmail")));
				contactItem.appendChild(new Listcell(contact.get("sameCorpAdd")==null ? "-" : contact.get("sameCorpAdd")));
				contactItem.appendChild(new Listcell(contact.get("mainBilling")==null ? "-" : NonConfigurableConstants.BOOLEAN_YN.get(contact.get("mainBilling"))));
				contactItem.appendChild(new Listcell(contact.get("mainShipping")==null ? "-" : NonConfigurableConstants.BOOLEAN_YN.get(contact.get("mainShipping"))));
				contactsBox.appendChild(contactItem);
			}
			Listheader contactPersonHeader = (Listheader)this.getFellow("contactPersonHeader");
			contactPersonHeader.sort(false);
			contactPersonHeader.sort(true);
		}
	}
	@Override
	public void refresh() throws InterruptedException {
		logger.info("refresh()");
		((Textbox)this.getFellow("mainContactName")).setText(null);
		((Textbox)this.getFellow("mainContactEmail")).setText(null);
		((Textbox)this.getFellow("mainContactTel")).setText(null);
		((Textbox)this.getFellow("mainContactMobile")).setText(null);
		((Listfooter)this.getFellow("noRecordFooter")).setVisible(false);
		Listbox contactsBox = (Listbox)this.getFellow("contacts");
		contactsBox.getItems().clear();
		search();
	}
	public void addContact() throws InterruptedException{
		logger.info("addContact()");
		Map<String, String> params = new HashMap<String, String>();
		params.put("custNo", custNo);
		this.forward(Uri.ADD_CONTACT, params, this.getParent());
	}
	public void selectContact(Listitem selectedItem) throws InterruptedException{
		logger.info("selectContact(Listitem selectedItem)");
		if(selectedItem.equals(this.getFellow("contacts").getLastChild())){
			return;
		}
		Map<String, String> params = new HashMap<String, String>();
		params.put("custNo", custNo);
		params.put("acctStatus", acctStatus);
		params.put("contactPersonNo", (String)selectedItem.getValue());
		if(acctStatus.equals(NonConfigurableConstants.ACCOUNT_STATUS.get(NonConfigurableConstants.ACCOUNT_STATUS_PENDING_ACTIVATION))){
			this.forward(Uri.EDIT_CONTACT, params, this.getParent());
		}else{
			this.forward(Uri.VIEW_CONTACT, params, this.getParent());
		}
	}
	public void viewStatusHistory() throws SuspendNotAllowedException, InterruptedException{
		logger.info("viewStatusHistory()");
		super.viewStatusHistory(custNo, null, null, null);
	}
}