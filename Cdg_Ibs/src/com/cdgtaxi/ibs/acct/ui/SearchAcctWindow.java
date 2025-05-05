package com.cdgtaxi.ibs.acct.ui;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listheader;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;

import com.cdgtaxi.ibs.Uri;
import com.cdgtaxi.ibs.common.ConfigurableConstants;
import com.cdgtaxi.ibs.common.NonConfigurableConstants;
import com.cdgtaxi.ibs.common.ui.CommonWindow;
import com.cdgtaxi.ibs.util.ComponentUtil;
import com.cdgtaxi.ibs.util.StringUtil;

public class SearchAcctWindow extends CommonWindow{
	private static Logger logger = Logger.getLogger(SearchAcctWindow.class);
	private static final long serialVersionUID = 1L;
	private List<Listitem> acctStatus = new ArrayList<Listitem>();

	public SearchAcctWindow(){
		// adding all application status
		acctStatus.add((Listitem)ComponentUtil.createNotRequiredListItem());
		for(String statusCode : NonConfigurableConstants.ACCOUNT_STATUS.keySet()){
			if(statusCode.equals(NonConfigurableConstants.ACCOUNT_STATUS_PARENT_SUSPENDED)){
				continue;
			}
			acctStatus.add(new Listitem(NonConfigurableConstants.ACCOUNT_STATUS.get(statusCode), statusCode));
		}
	}
	public void init(){
		// getting account status
		String acctStatus = NonConfigurableConstants.ACCOUNT_STATUS_PENDING_ACTIVATION;
		// getting rows to add results
		Listbox accts = (Listbox)this.getFellow("accounts");
		// clearing any previous search
		accts.getItems().clear();
		// getting results from business layer
		Map<Integer, Map<String, String>> results = this.businessHelper.getAccountBusiness().searchAccounts(null, null, acctStatus, null);
		// for each result
		if(results.size()!=0){
			for(Integer custNo : results.keySet()){
				// creating a new row and append it to rows
				Listitem acct = new Listitem(); accts.appendChild(acct);
				// getting the details
				Map<String, String> acctDetails = results.get(custNo);
				// creating a list to hold required information of the account but hide from user
				Map<String, String> acctTemplateMap = new LinkedHashMap<String, String>();
				// putting account number and account template into list
				acctTemplateMap.put("custNo", acctDetails.get("custNo"));
				acctTemplateMap.put("acctTemplate", acctDetails.get("acctTemplate"));
				// setting the list as the value
				acct.setValue(acctTemplateMap);
				// creating listcell into listitem for account no(customer no)
				acct.appendChild(newListcell(new Integer(acctDetails.get("custNo")), StringUtil.GLOBAL_STRING_FORMAT));
				// creating listcell into listitem for account name
				acct.appendChild(newListcell(acctDetails.get("acctName")));
				// creating listcell into listitem for entity name
				acct.appendChild(newListcell(acctDetails.get("entityName")));
				// creating listcell into listitem for account status
				acct.appendChild(newListcell(acctDetails.get("acctStatus")));
			}
			accts.setVisible(true);
		}
	}
	/**
	 * Search for all account
	 */
	public void search() throws InterruptedException{
		logger.info("search()");
		// getting custNo string from textbox
		String custNoString = ((Intbox)this.getFellow("custNo")).getText();
		try{
			// if data exist
			if(custNoString!=null && custNoString.length()!=0){
				// try parsing it to integer
				int custNo = Integer.parseInt(custNoString);
				// if account number is lesser than 0 = invalid. setting string to null
				if(custNo < 0){
					Messagebox.show("Invalid number for account no. Continuing without account no", "Search Accounts", Messagebox.OK, Messagebox.EXCLAMATION);
					custNoString = null;
				}
			}
		}catch(NumberFormatException nfe){
			// Shouldn't happen
			Messagebox.show("Invalid format for account no. Continuing without account no", "Search Accounts", Messagebox.OK, Messagebox.EXCLAMATION);
			custNoString = null;
		}
		// getting account name
		String acctName = ((Textbox)this.getFellow("acctName")).getValue();
		// getting account status
		String acctStatus = (String)((Listbox)this.getFellow("acctStatusList")).getSelectedItem().getValue();
		// getting contact person name
		String contactPerson = ((Textbox)this.getFellow("contactPerson")).getValue();
		if(custNoString==null || custNoString.length()==0){
			if(acctName==null || acctName.length()<3){
					if(contactPerson==null || contactPerson.length()<3){
						Messagebox.show("Please input account no/name(min 3 chars)/contact person(min 3 chars)", "Search Accounts", Messagebox.OK, Messagebox.ERROR);
						return;
					}
			}
		}
		// getting rows to add results
		Listbox accts = (Listbox)this.getFellow("accounts");
		// clearing any previous search
		accts.getItems().clear();
		// getting results from business layer
		Map<Integer, Map<String, String>> results = this.businessHelper.getAccountBusiness().searchAccounts(custNoString, acctName, acctStatus, contactPerson);
		if(results.size()>ConfigurableConstants.getMaxQueryResult())
			Messagebox.show(NonConfigurableConstants.getExceedMaxResultMessage(), "Alert", Messagebox.OK, Messagebox.INFORMATION);
		// for each result
		if(results.size()!=0){
			for(Integer custNo : results.keySet()){
				// creating a new row and append it to rows
				Listitem acct = new Listitem(); accts.appendChild(acct);
				// getting the details
				Map<String, String> acctDetails = results.get(custNo);
				// creating a list to hold required information of the account but hide from user
				Map<String, String> acctTemplateMap = new LinkedHashMap<String, String>();
				// putting account number and account template into list
				acctTemplateMap.put("custNo", acctDetails.get("custNo"));
				acctTemplateMap.put("acctTemplate", acctDetails.get("acctTemplate"));
				// setting the list as the value
				acct.setValue(acctTemplateMap);
				// creating listcell into listitem for account no(customer no)
				acct.appendChild(newListcell(new Integer(acctDetails.get("custNo")), StringUtil.GLOBAL_STRING_FORMAT));
				// creating listcell into listitem for account name
				acct.appendChild(newListcell(acctDetails.get("acctName")));
				// creating listcell into listitem for entity name
				acct.appendChild(newListcell(acctDetails.get("entityName")));
				// creating listcell into listitem for account status
				acct.appendChild(newListcell(acctDetails.get("acctStatus")));
			}
			if(results.size()>ConfigurableConstants.getMaxQueryResult())
				accts.removeItemAt(ConfigurableConstants.getMaxQueryResult());
			accts.setVisible(true);
		}else{
			Messagebox.show("No matching account.", "Manage Accounts", Messagebox.OK, Messagebox.INFORMATION);
		}
	}
	public void initAcctNoHeader(){
		Listheader acctNoHeader = (Listheader)this.getFellow("acctNoHeader");
		acctNoHeader.setSortAscending(new Comparator<Listitem>(){
			public int compare(Listitem o1, Listitem o2) {
				String custNo1 = (String)((Listcell)o1.getFirstChild()).getLabel();
				String custNo2 = (String)((Listcell)o2.getFirstChild()).getLabel();
				return new Integer(custNo1).compareTo(Integer.parseInt(custNo2));
			}
		});
		acctNoHeader.setSortDescending(new Comparator<Listitem>(){
			public int compare(Listitem o1, Listitem o2) {
				String custNo1 = (String)((Listcell)o1.getFirstChild()).getLabel();
				String custNo2 = (String)((Listcell)o2.getFirstChild()).getLabel();
				return new Integer(custNo2).compareTo(Integer.parseInt(custNo1));
			}
		});
	}
	@SuppressWarnings("unchecked")
	public void selectAccount() throws InterruptedException{
		logger.info("selectAccount()");
		// getting the list box
		Listbox accounts = (Listbox)this.getFellow("accounts");
		// getting the selected item
		Listitem selectedAccount = accounts.getSelectedItem();
		// creating params
		Map<String, String> params = new LinkedHashMap<String, String>();
		// getting the selected account details
		Map<String, String> acctDetails = (Map<String, String>)selectedAccount.getValue();
		// putting account no into the params
		params.put("custNo", acctDetails.get("custNo"));
		// if pending activation
		String status = ((Listcell)selectedAccount.getChildren().get(3)).getLabel();
		params.put("acctStatus", status);
		if(acctDetails.get("acctTemplate").equals(NonConfigurableConstants.ACCOUNT_TEMPLATES_CORPORATE)){
			this.forward(Uri.MANAGE_CORP_ACCT, params);
		}else{
			this.forward(Uri.MANAGE_PERS_ACCT, params);
		}
	}
	@Override
	public void refresh() throws InterruptedException {
		((Intbox)this.getFellow("custNo")).setText(null);
		((Textbox)this.getFellow("acctName")).setText(null);
		((Listbox)this.getFellow("acctStatusList")).setSelectedIndex(0);
		((Textbox)this.getFellow("contactPerson")).setText(null);
		this.getFellow("accounts").setVisible(false);
		init();
	}
	public List<Listitem> getAccountStatus(){
		return this.acctStatus;
	}
}
