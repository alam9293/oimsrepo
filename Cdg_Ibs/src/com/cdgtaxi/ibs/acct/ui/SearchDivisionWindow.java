package com.cdgtaxi.ibs.acct.ui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.log4j.Logger;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.SuspendNotAllowedException;
import org.zkoss.zul.Button;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Label;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listfooter;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;

import com.cdgtaxi.ibs.Uri;
import com.cdgtaxi.ibs.common.NonConfigurableConstants;
import com.cdgtaxi.ibs.util.ComponentUtil;

public class SearchDivisionWindow extends CommonInnerAcctWindow {
	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(SearchDivisionWindow.class);
	private String custNo, parentStatus;
	private List<Listitem> acctStatuses = new ArrayList<Listitem>();
	private String acctType, createdDt;
	
	@SuppressWarnings("unchecked")
	public SearchDivisionWindow() throws InterruptedException{
		logger.info("SearchDivisionWindow()");
		//account number
		Map<String, String> map = Executions.getCurrent().getArg();
		custNo = map.get("custNo");
		if(custNo==null || custNo.trim().length()==0){
			logger.info("custNo = " + custNo);
			Messagebox.show("No account number found!", "Search Division", Messagebox.OK, Messagebox.ERROR);
		}
		// getting account status
		parentStatus = map.get("acctStatus");
		// checking account status. if null, show error and back
		if(parentStatus==null || parentStatus.trim().length()==0){
			Messagebox.show("No Account Status!", "Search Division", Messagebox.OK, Messagebox.ERROR);
		}
		
		// adding all account status
		acctStatuses.add((Listitem)ComponentUtil.createNotRequiredListItem());
		for(Entry<String, String> entry : NonConfigurableConstants.ACCOUNT_STATUS.entrySet()){
			acctStatuses.add(new Listitem(entry.getValue(), entry.getKey()));
		}
		
		acctType = map.get("acctType");
		createdDt = map.get("createdDt");
		
	}
	public void init() throws InterruptedException{
		
		((Label)this.getFellow("custNo")).setValue(custNo);
		((Label)this.getFellow("acctStatus")).setValue(parentStatus);
		((Label)this.getFellow("acctType")).setValue(acctType);
		((Label)this.getFellow("createdDt")).setValue(createdDt);
		
		if(parentStatus.equals(NonConfigurableConstants.ACCOUNT_STATUS.get(NonConfigurableConstants.ACCOUNT_STATUS_CLOSED))){
			Button createBtn = (Button)this.getFellow("createBtn");
			createBtn.setDisabled(true);
		}
		if(!this.checkUriAccess(Uri.ADD_DIVISION))
			((Button)this.getFellow("createBtn")).setDisabled(true);
	}
	public void search() throws InterruptedException{
		logger.info("search()");
		String accountName = ((Textbox)this.getFellow("accountName")).getValue();
		String accountCode = ((Textbox)this.getFellow("accountCode")).getValue();
		String mainContactTel = ((Textbox)this.getFellow("mainContactTel")).getValue();
		String mainContactName = ((Textbox)this.getFellow("mainContactName")).getValue();
		String acctStatus = (String)((Listbox)this.getFellow("acctStatusList")).getSelectedItem().getValue();
		displayProcessing();
		Map<Integer, Map<String, String>> divisions = this.businessHelper.getAccountBusiness().searchAccounts(custNo, accountName, accountCode, mainContactName, mainContactTel, NonConfigurableConstants.DIVISION_LEVEL, null, acctStatus);
		Listbox divisionsBox = (Listbox)this.getFellow("divisions");
		divisionsBox.getItems().clear();
		if(divisions.isEmpty()){
			((Listfooter)this.getFellow("noRecordFooter")).setVisible(true);
		}else{
			((Listfooter)this.getFellow("noRecordFooter")).setVisible(false);
			for(Integer acctNo : divisions.keySet()){
				Map<String, String> division = divisions.get(acctNo);
				Listitem divisionItem = new Listitem();
				divisionItem.setValue(division.get("acctCode"));
			
				Listcell checkBoxCell=new Listcell();
				checkBoxCell.appendChild(new Checkbox());
				divisionItem.appendChild(checkBoxCell);
				divisionItem.appendChild(newListcell(division.get("acctName")));
				divisionItem.appendChild(newListcell(division.get("acctCode")));
				divisionItem.appendChild(newListcell(division.get("creditLimit")));
				divisionItem.appendChild(newListcell(division.get("shipContactPerson")));
				divisionItem.appendChild(newListcell(division.get("shipContactPersonTel")));
				divisionItem.appendChild(newListcell(division.get("billContactPerson")));
				divisionItem.appendChild(newListcell(division.get("billContactPersonTel")));
				String status = division.get("acctStatus");
				if(status==null || "".equals(status))
					status = "-";
				divisionItem.appendChild(newListcell(status));
				divisionsBox.appendChild(divisionItem);
			}
		}
	}
	public void selectDivision(Listitem selectedItem) throws InterruptedException{
		logger.info("selectDivision(Listitem selectedItem)");
		if(selectedItem.equals(selectedItem.getParent().getLastChild())){
			return;
		}
		Map<String, String> params = new HashMap<String, String>();
		params.put("custNo", custNo);
		params.put("acctStatus", parentStatus);
		params.put("code", (String)selectedItem.getValue());
		if(parentStatus.equals(NonConfigurableConstants.ACCOUNT_STATUS.get(NonConfigurableConstants.ACCOUNT_STATUS_PENDING_ACTIVATION))){
			this.forward(Uri.EDIT_DIVISION, params, this.getParent());
		}else{
			this.forward(Uri.VIEW_DIVISION, params, this.getParent());
		}
	}
	public void addDivision() throws InterruptedException{
		logger.info("addDivision()");
		Map<String, String> params = new HashMap<String, String>();
		params.put("custNo", custNo);
		params.put("acctStatus", parentStatus);
		this.forward(Uri.ADD_DIVISION, params, this.getParent());
	}
	@Override
	public void refresh() throws InterruptedException {
		logger.info("refresh()");
		((Textbox)this.getFellow("accountName")).setText(null);
		((Textbox)this.getFellow("accountCode")).setText(null);
		((Textbox)this.getFellow("mainContactTel")).setText(null);
		((Textbox)this.getFellow("mainContactName")).setText(null);
		((Listfooter)this.getFellow("noRecordFooter")).setVisible(false);
		((Checkbox)this.getFellow("checkAll")).setChecked(false);
		Listbox divisionsBox = (Listbox)this.getFellow("divisions");
		divisionsBox.getItems().clear();
		search();
	}
	

	public void checkAll(){
		Listbox resultListBox = (Listbox)this.getFellow("divisions");
		Checkbox checkAll=(Checkbox)this.getFellow("checkAll");
		Checkbox checkBox=null;
		Listitem eachRowListItem=null;
		Listcell checkBoxCell=null;
		for(int i=0; i<resultListBox.getItemCount();i++){
			eachRowListItem=(Listitem)resultListBox.getItemAtIndex(i);
			checkBoxCell=(Listcell)eachRowListItem.getFirstChild();
			checkBox=(Checkbox)checkBoxCell.getFirstChild();
			if(!checkAll.isChecked())
				checkBox.setChecked(false);
			else
				checkBox.setChecked(true);
		}
	}

	public void creditReview() throws InterruptedException{
		Listitem listItem=null;
		Listcell checkBoxCell=null;
		Checkbox checkBox=null;
		ArrayList<String> codes = new ArrayList<String>();
		Listbox resultListBox = (Listbox)this.getFellow("divisions");
		for(int i=0; i<resultListBox.getItemCount();i++){
			listItem=(Listitem)resultListBox.getItemAtIndex(i);
			checkBoxCell=(Listcell)listItem.getFirstChild();
			checkBox=(Checkbox)checkBoxCell.getFirstChild();
			if(checkBox.isChecked()){
				codes.add((String)listItem.getValue());
			}
		}
		
		if(codes.size()>0){
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("custNo", custNo);
			params.put("code", codes);
			forward(Uri.CREDIT_REVIEW_DIV, params, this.getParent());
		} else {
			Messagebox.show("No Division Selected!", "Search Division", Messagebox.OK, Messagebox.ERROR);
		}
	}
	
	public List<Listitem> getAccountStatus(){
		return this.acctStatuses;
	}
	
	public void viewStatusHistory() throws SuspendNotAllowedException, InterruptedException{
		logger.info("viewStatusHistory()");
		super.viewStatusHistory(custNo, null, null, null);
	}
	
	
	
	
	
	
}