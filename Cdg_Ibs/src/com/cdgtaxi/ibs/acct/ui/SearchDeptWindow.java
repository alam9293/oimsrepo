package com.cdgtaxi.ibs.acct.ui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

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

public class SearchDeptWindow extends SearchDivisionWindow {
	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(SearchDeptWindow.class);
	private String custNo, acctStatus;
	private String acctType, createdDt;
	
	@SuppressWarnings("unchecked")
	public SearchDeptWindow() throws InterruptedException {
		super();
		logger.info("SearchDeptWindow()");
		//account number
		Map<String, String> map = Executions.getCurrent().getArg();
		custNo = map.get("custNo");
		if(custNo==null || custNo.trim().length()==0){
			logger.info("custNo = " + custNo);
			Messagebox.show("No account number found!", "Search Department", Messagebox.OK, Messagebox.ERROR);
		}
		// getting account status
		acctStatus = map.get("acctStatus");
		// checking account status. if null, show error and back
		if(acctStatus==null || acctStatus.trim().length()==0){
			Messagebox.show("No Account Status!", "Search Department", Messagebox.OK, Messagebox.ERROR);
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
		if(!this.checkUriAccess(Uri.ADD_DEPT))
			((Button)this.getFellow("createBtn")).setDisabled(true);
	}
	public void search() throws InterruptedException{
		logger.info("search()");
		String divName = ((Textbox)this.getFellow("divName")).getValue();
		String accountName = ((Textbox)this.getFellow("accountName")).getValue();
		String accountCode = ((Textbox)this.getFellow("accountCode")).getValue();
		String mainContactTel = ((Textbox)this.getFellow("mainContactTel")).getValue();
		String mainContactName = ((Textbox)this.getFellow("mainContactName")).getValue();
		String acctStatus = (String)((Listbox)this.getFellow("acctStatusList")).getSelectedItem().getValue();
		displayProcessing();
		Map<Integer, Map<String, String>> depts = this.businessHelper.getAccountBusiness().searchAccounts(custNo, accountName, accountCode, mainContactName, mainContactTel, NonConfigurableConstants.DEPARTMENT_LEVEL, divName, acctStatus);
		Listbox deptsBox = (Listbox)this.getFellow("depts");
		deptsBox.getItems().clear();
		if(depts.isEmpty()){
			((Listfooter)this.getFellow("noRecordFooter")).setVisible(true);
		}else{
			((Listfooter)this.getFellow("noRecordFooter")).setVisible(false);
			for(Integer acctNo : depts.keySet()){
				Map<String, String> dept = depts.get(acctNo);
				Listitem deptItem = new Listitem();
				deptItem.setValue(dept.get("parentCode"));
				
				Listcell checkBoxCell=new Listcell();
				checkBoxCell.appendChild(new Checkbox());
				deptItem.appendChild(checkBoxCell);
				deptItem.appendChild(newListcell(dept.get("parentName")));
				deptItem.appendChild(newListcell(dept.get("acctName")));
				deptItem.appendChild(newListcell(dept.get("acctCode")));
				deptItem.appendChild(newListcell(dept.get("creditLimit")));
				deptItem.appendChild(newListcell(dept.get("shipContactPerson")));
				deptItem.appendChild(newListcell(dept.get("shipContactPersonTel")));
				deptItem.appendChild(newListcell(dept.get("billContactPerson")));
				deptItem.appendChild(newListcell(dept.get("billContactPersonTel")));
				String status = dept.get("acctStatus");
				if(status==null || "".equals(status))
					status = "-";
				deptItem.appendChild(newListcell(status));
				deptsBox.appendChild(deptItem);
			}
		}
	}
	public void addDept() throws InterruptedException{
		logger.info("addDept()");
		Map<String, String> params = new HashMap<String, String>();
		params.put("custNo", custNo);
		params.put("acctStatus", acctStatus);
		this.forward(Uri.ADD_DEPT, params, this.getParent());
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
		Listbox deptsBox = (Listbox)this.getFellow("depts");
		deptsBox.getItems().clear();
		search();
	}
	public void selectDept(Listitem selectedItem) throws InterruptedException{
		logger.info("selectDept(Listitem selectedItem)");
		if(selectedItem.equals(selectedItem.getParent().getLastChild())){
			return;
		}else{
			Map<String, String> params = new HashMap<String, String>();
			params.put("custNo", custNo);
			params.put("acctStatus", acctStatus);
			params.put("parentCode", (String)selectedItem.getValue());
			params.put("code", ((Listcell)selectedItem.getChildren().get(3)).getLabel());
			if(acctStatus.equals(NonConfigurableConstants.ACCOUNT_STATUS.get(NonConfigurableConstants.ACCOUNT_STATUS_PENDING_ACTIVATION))){
				this.forward(Uri.EDIT_DEPT, params, this.getParent());
			}else{
				this.forward(Uri.VIEW_DEPT, params, this.getParent());
			}
		}
	}
	

	public void checkAll(){
		Listbox resultListBox = (Listbox)this.getFellow("depts");
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
		ArrayList<String> parentCodes = new ArrayList<String>();
		Listbox resultListBox = (Listbox)this.getFellow("depts");
		for(int i=0; i<resultListBox.getItemCount();i++){
			listItem=(Listitem)resultListBox.getItemAtIndex(i);
			checkBoxCell=(Listcell)listItem.getFirstChild();
			checkBox=(Checkbox)checkBoxCell.getFirstChild();
			if(checkBox.isChecked()){
				
				String code = (String)((Listcell)listItem.getChildren().get(3)).getLabel();
				String parentCode = (String)listItem.getValue();
				codes.add(code);
				parentCodes.add(parentCode);
			}
		}
		
		if(codes.size()>0){
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("custNo", custNo);
			params.put("code", codes);
			params.put("parentCode", parentCodes);
			forward(Uri.CREDIT_REVIEW_DEPT, params, this.getParent());
		} else {
			Messagebox.show("No Department Selected!", "Search Division", Messagebox.OK, Messagebox.ERROR);
		}
	}
	
	public void viewStatusHistory() throws SuspendNotAllowedException, InterruptedException{
		logger.info("viewStatusHistory()");
		super.viewStatusHistory(custNo, null, null, null);
	}
}
