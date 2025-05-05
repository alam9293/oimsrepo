package com.cdgtaxi.ibs.acct.ui;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.SuspendNotAllowedException;
import org.zkoss.zul.Button;
import org.zkoss.zul.Label;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;

import com.cdgtaxi.ibs.Uri;
import com.cdgtaxi.ibs.common.NonConfigurableConstants;
import com.cdgtaxi.ibs.util.StringUtil;

public class SearchSubPersWindow extends CommonInnerAcctWindow {
	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(SearchSubPersWindow.class);
	private String custNo, acctStatus, parentNric, parentAcctName;
	private String acctType, createdDt;
	@SuppressWarnings("unchecked")
	public SearchSubPersWindow() throws InterruptedException{
		logger.info("SearchSubWindow()");
		//account number
		Map<String, String> map = Executions.getCurrent().getArg();
		custNo = map.get("custNo");
		if(custNo==null || custNo.trim().length()==0){
			logger.info("custNo = " + custNo);
			Messagebox.show("No account number found!", "Search Sub Applicant", Messagebox.OK, Messagebox.ERROR);
		}
		// getting account status
		acctStatus = map.get("acctStatus");
		// checking account status. if null, show error and back
		if(acctStatus==null || acctStatus.trim().length()==0){
			Messagebox.show("No Account Status!", "Search Sub Applicant", Messagebox.OK, Messagebox.ERROR);
		}
		
		acctType = map.get("acctType");
		createdDt = map.get("createdDt");
	}
	public void init(){

		((Label)this.getFellow("custNo")).setValue(custNo);
		((Label)this.getFellow("acctStatus")).setValue(acctStatus);
		((Label)this.getFellow("acctType")).setValue(acctType);
		((Label)this.getFellow("createdDt")).setValue(createdDt);
		
		logger.info("init()");
		if(parentNric==null || parentAcctName==null){
			Map<String, Object> details = this.businessHelper.getAccountBusiness().searchAccount(custNo);
			parentNric = (String)details.get("nric");
			parentAcctName = (String)details.get("acctName");
		}
		((Label)this.getFellow("parentNric")).setValue(StringUtil.maskNric(parentNric));
		((Label)this.getFellow("parentAcctName")).setValue(parentAcctName);
		((Textbox)this.getFellow("nric")).setText(null);
		((Textbox)this.getFellow("acctName")).setText(null);
		((Textbox)this.getFellow("email")).setText(null);
		Listbox subBox = (Listbox)this.getFellow("subApplicants");
		subBox.getItems().clear();
		
		if(!this.checkUriAccess(Uri.ADD_SUB_PERS))
			((Button)this.getFellow("createBtn")).setDisabled(true);
	}
	public void search() throws InterruptedException{
		logger.info("search()");
		String nric = ((Textbox)this.getFellow("nric")).getText();
		String acctName = ((Textbox)this.getFellow("acctName")).getText();
		String email = ((Textbox)this.getFellow("email")).getText();
		Map<String, Map<String, String>> subApplicants = this.businessHelper.getAccountBusiness().searchPersAccounts(custNo, acctName, nric, email);
		Listbox subApplicantsBox = (Listbox)this.getFellow("subApplicants");
		subApplicantsBox.getItems().clear();
		for(String acctNo : subApplicants.keySet()){
			Map<String, String> subApplicant = subApplicants.get(acctNo);
			Listitem subApplicantItem = new Listitem();
			subApplicantItem.setValue(acctNo);
			subApplicantItem.appendChild(newListcell(StringUtil.maskNric(subApplicant.get("nric"))));
			subApplicantItem.appendChild(newListcell(subApplicant.get("acctName")));
			subApplicantItem.appendChild(newListcell(subApplicant.get("email")));
			subApplicantsBox.appendChild(subApplicantItem);
		}
	}
	public void selectApplicant(Listitem selectedItem) throws InterruptedException{
		Map<String, String> params = new HashMap<String, String>();
		params.put("custNo", custNo);
		params.put("acctStatus", acctStatus);
		params.put("acctNo", (String)selectedItem.getValue());
		if(acctStatus.equals(NonConfigurableConstants.ACCOUNT_STATUS.get(NonConfigurableConstants.ACCOUNT_STATUS_PENDING_ACTIVATION))){
			this.forward(Uri.EDIT_SUB_PERS, params, this.getParent());
		}else{
			this.forward(Uri.VIEW_SUB_PERS, params, this.getParent());
		}
	}
	public void addSubApplicant() throws InterruptedException{
		logger.info("addSubApplicant()");
		Map<String, String> params = new HashMap<String, String>();
		params.put("custNo", custNo);
		params.put("acctStatus", acctStatus);
		this.forward(Uri.ADD_SUB_PERS, params, this.getParent());
	}
	@Override
	public void refresh() throws InterruptedException {
		logger.info("refresh()");
		init();
		this.search();
	}
	public void viewStatusHistory() throws SuspendNotAllowedException, InterruptedException{
		logger.info("viewStatusHistory()");
		super.viewStatusHistory(custNo, null, null, null);
	}
}