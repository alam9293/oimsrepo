package com.cdgtaxi.ibs.common.ui;

import java.util.List;

import org.apache.log4j.Logger;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zk.ui.event.CreateEvent;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.event.InputEvent;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Comboitem;
import org.zkoss.zul.Intbox;

import com.cdgtaxi.ibs.common.model.AmtbAccount;
import com.cdgtaxi.ibs.util.ComponentUtil;

@SuppressWarnings("serial")
public abstract class CommonSearchByAccountWindow extends CommonWindow{

	protected static final String ACCOUNT_NO_ID = "accountNoIntBox";
	protected static final String ACCOUNT_NAME_ID = "accountNameComboBox";

	protected Intbox accountNoIntBox;
	protected Combobox accountNameComboBox;
	protected AmtbAccount selectedAccount;

	protected static Logger logger;
	
	public CommonSearchByAccountWindow() {
		logger = Logger.getLogger(getClass());
	}

	public void onCreate(CreateEvent ce) throws Exception{
		
		//initiate account no integer box
		accountNoIntBox = (Intbox)getFellow(ACCOUNT_NO_ID);
		accountNoIntBox.setMaxlength(6);
		accountNoIntBox.addEventListener(Events.ON_CHANGE, new EventListener() {
			public void onEvent(Event arg0) throws Exception {
				onChangeCustNo();
			}
		});
		accountNoIntBox.addEventListener(Events.ON_OK, new EventListener() {
			public void onEvent(Event arg0) throws Exception {
				onChangeCustNo();
			}
		});
		
		//initiate account name combo box
		accountNameComboBox = (Combobox)getFellow(ACCOUNT_NAME_ID);
		accountNameComboBox.setStyle("text-transform:uppercase");
		accountNameComboBox.addEventListener(Events.ON_CHANGING, new EventListener() {
			public void onEvent(Event e) throws Exception {
				onChangingAccountName(((InputEvent)e).getValue());
			}
		});
		accountNameComboBox.addEventListener(Events.ON_SELECT, new EventListener() {
			public void onEvent(Event e) throws Exception {
				onSelectAccountName();
			}
		});
		accountNameComboBox.setTooltiptext("Minimum search criteria is 3 characters");
		accountNameComboBox.setAutodrop(true);
		accountNameComboBox.setAutocomplete(false);
	}

	
	public void onSelectAccountName() throws InterruptedException{
		logger.debug("onSelectAccountName");
	
		if(accountNameComboBox.getSelectedItem()!=null){
			selectedAccount = ComponentUtil.getSelectedItem(accountNameComboBox);
			
			if(!selectedAccount.getCustNo().equals(accountNoIntBox.getText())){
				accountNoIntBox.setText(selectedAccount.getCustNo());
			}
		} else {
			selectedAccount = null;
		}
		
	}

	public abstract List<AmtbAccount> searchAccountsByCustNoAndName(String custNo, String accountName);
	
	
	public void onChangeCustNo() throws InterruptedException{
		logger.debug("onChangeCustNo");
		
		Integer custNo = accountNoIntBox.getValue();
		
		List<AmtbAccount> accounts = null;
		if(custNo!=null && !"".equals(custNo)) {
			accounts = searchAccountsByCustNoAndName(custNo.toString(), null);
		}

		buildAccountNameCombobox(accounts);
		
		

	}

	
	public void onChangingAccountName(String name) throws InterruptedException{
		logger.debug("Searching accounts by Account Name: " + name);

		//only begin new search if input is greater than 2
		if(name.length()<3) {
			return;
		}
		
		//account still the same as selected one, skip
		if(selectedAccount!=null && accountNameComboBox.getSelectedItem()!=null){
			AmtbAccount selectedAcct = ComponentUtil.getSelectedItem(accountNameComboBox);
			if(selectedAcct.getAccountNo().equals(selectedAccount.getAccountNo())){
				return;
			}
		}
		
		
		List<AmtbAccount> accounts = searchAccountsByCustNoAndName(null, name);
		buildAccountNameCombobox(accounts);

	}
	
	
	private void buildAccountNameCombobox(List<AmtbAccount> accounts) throws InterruptedException{
		
		accountNameComboBox.getChildren().clear();
		accountNameComboBox.setSelectedItem(null);
		
		if(accounts!=null && !accounts.isEmpty()){
			//Clear combo box for a new search
			for(AmtbAccount account : accounts){
				Comboitem item = new Comboitem(account.getAccountName()+" ("+account.getCustNo()+")");
				item.setValue(account);
				accountNameComboBox.appendChild(item);
			}
			if(accounts.size()>0){
				if(accounts.size()==1){
					accountNameComboBox.setSelectedIndex(0);
					Events.sendEvent(new Event( Events.ON_SELECT, accountNameComboBox, accounts.get(0)));
					
				} else {
					accountNameComboBox.open();
				}
				
			}
		} else{
			//still proceed to select account name even account is null
			onSelectAccountName();
		}

	}

	
	public void reset() throws InterruptedException {
		accountNoIntBox.setValue(null);
		accountNameComboBox.setValue(null);
		accountNameComboBox.getChildren().clear();
		selectedAccount = null;
	}
	
	public void checkAccountNotNull(){
		
		if(selectedAccount==null){
			throw new WrongValueException("Please fill in the valid account no.");
		}
		
	}
	
	public AmtbAccount getSelectedAccount(){
		
		return selectedAccount;
	}

	@Override
	public void refresh() throws InterruptedException {
		
	}
}
