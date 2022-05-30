package com.cdgtaxi.ibs.acct.ui;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Tab;
import org.zkoss.zul.Tabbox;
import org.zkoss.zul.Tabpanel;
import org.zkoss.zul.Window;

import com.cdgtaxi.ibs.Uri;
import com.cdgtaxi.ibs.common.NonConfigurableConstants;
import com.cdgtaxi.ibs.common.ui.CommonWindow;
import com.cdgtaxi.ibs.util.DateUtil;

public class ManageCorpAcctWindow extends CommonWindow {
	// default
	private static final long serialVersionUID = 1L;
	// logger for logging
	private static Logger logger = Logger.getLogger(ManageCorpAcctWindow.class);
	// account number and status that this window is handling
	private String custNo, acctStatus;
	/**
	 * default constructor
	 * @throws InterruptedException
	 */
	@SuppressWarnings("unchecked")
	public ManageCorpAcctWindow() throws InterruptedException{
		logger.info("ManageCorpAcctWindow()");
		// getting the param
		Map<String, String> params = Executions.getCurrent().getArg();
		// getting account number
		custNo = params.get("custNo");
		// checking account number. If null, return error and go back
		if(custNo==null || custNo.trim().length()==0){
			Messagebox.show("No Account Number!", "Manage Corporate Account", Messagebox.OK, Messagebox.ERROR);
			this.back();
		}
		// getting account status
		acctStatus = params.get("acctStatus");
		// checking account status. if null, show error and back
		if(acctStatus==null || acctStatus.trim().length()==0){
			logger.info("account status = " + acctStatus);
			Messagebox.show("No Account Status!", "Manage Corporate Account", Messagebox.OK, Messagebox.ERROR);
			this.back();
		}
	}
	/**
	 * to init the window
	 */
	public void init(){
		// getting the tabbox to add the windows
		Tabbox tabbox = (Tabbox)this.getFellow("acctTabbox");
		// if it is pending activation, show all edit.
		String firstPage = Uri.VIEW_CORP_DETAILS;
		String secondPage = Uri.SEARCH_CONTACT;
		String thirdPage = Uri.SEARCH_DIVISION;
		String fourthPage = Uri.SEARCH_DEPT;
		String fifthPage = Uri.VIEW_BILLING;
		//String sixthPage = Uri.EDIT_PA_REWARDS;
		String sixthPage = Uri.VIEW_REWARDS;
		if(acctStatus.equals(NonConfigurableConstants.ACCOUNT_STATUS.get(NonConfigurableConstants.ACCOUNT_STATUS_PENDING_ACTIVATION))){
			firstPage = Uri.EDIT_PA_CORP_DETAILS;
			fifthPage = Uri.EDIT_PA_BILLING;
			sixthPage = Uri.EDIT_PA_REWARDS;
		}
		// creating an argument to send it to different windows
		Map<String, String> args = new HashMap<String,String>();
		Map<String, Object> accountDetails = this.businessHelper.getAccountBusiness().searchAccountHeader(custNo);
		
		// putting account number to args
		args.put("custNo", custNo);
		args.put("acctStatus", acctStatus);
		args.put("acctType", (String)accountDetails.get("acctType"));
		args.put("acctNo", Integer.toString((Integer)accountDetails.get("acctNo")));
		args.put("createdDt", DateUtil.convertTimestampToStr((Timestamp) accountDetails.get("createdDt"), DateUtil.GLOBAL_DATE_FORMAT));
		// creating a new corp tab
		Tab tab = new Tab("Corporate");
		// adding tab to the tabbox
		tabbox.getTabs().appendChild(tab);
		// adding edit pending activation corp to panel
		Tabpanel panel = new Tabpanel();
		// creating edit
		Window win = (Window) Executions.createComponents(firstPage, panel, args);
		panel.appendChild(win);
		// adding panel to tabbox
		tabbox.getTabpanels().appendChild(panel);
		/******************************************************/
		// creating new contact tab
		tab = new Tab("Contact");
		tabbox.getTabs().appendChild(tab);
		panel = new Tabpanel();
		win = (Window) Executions.createComponents(secondPage, panel, args);
		panel.appendChild(win);
		tabbox.getTabpanels().appendChild(panel);
		/******************************************************/
		// creating new division tab
		tab = new Tab("Division");
		tabbox.getTabs().appendChild(tab);
		panel = new Tabpanel();
		win = (Window) Executions.createComponents(thirdPage, panel, args);
		panel.appendChild(win);
		tabbox.getTabpanels().appendChild(panel);
		/******************************************************/
		// creating new department tab
		tab = new Tab("Department");
		tabbox.getTabs().appendChild(tab);
		panel = new Tabpanel();
		win = (Window) Executions.createComponents(fourthPage, panel, args);
		panel.appendChild(win);
		tabbox.getTabpanels().appendChild(panel);
		/******************************************************/
		// creating new billing tab
		tab = new Tab("Billing");
		tabbox.getTabs().appendChild(tab);
		panel = new Tabpanel();
		win = (Window) Executions.createComponents(fifthPage, panel, args);
		panel.appendChild(win);
		tabbox.getTabpanels().appendChild(panel);
		/******************************************************/
		// creating new rewards tab
		tab = new Tab("Rewards");
		tabbox.getTabs().appendChild(tab);
		panel = new Tabpanel();
		win = (Window) Executions.createComponents(sixthPage, panel, args);
		panel.appendChild(win);
		tabbox.getTabpanels().appendChild(panel);
	}
	@Override
	public void refresh() throws InterruptedException {
	}
}