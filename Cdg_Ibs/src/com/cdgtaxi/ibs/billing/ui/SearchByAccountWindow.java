package com.cdgtaxi.ibs.billing.ui;

import java.util.List;

import org.apache.log4j.Logger;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zk.ui.event.CreateEvent;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Comboitem;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.Label;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Row;

import com.cdgtaxi.ibs.common.NonConfigurableConstants;
import com.cdgtaxi.ibs.common.model.AmtbAccount;
import com.cdgtaxi.ibs.common.model.forms.SearchByAccountForm;
import com.cdgtaxi.ibs.common.ui.CommonWindow;
import com.cdgtaxi.ibs.util.ComponentUtil;

@SuppressWarnings("serial")
public abstract class SearchByAccountWindow extends CommonWindow {
	protected static final String DEPT_ID = "departmentListBox";
	protected static final String DEPT_LABEL_ID = "departmentLabel";
	protected static final String DIV_DEPT_ROW_ID = "divisionDepartmentRow";
	protected static final String DIV_LABEL_ID = "divisionLabel";
	protected static final String DIV_ID = "divisionListBox";
	protected static final String ACCOUNT_NO_ID = "accountNoIntBox";
	protected static final String ACCOUNT_NAME_ID = "accountNameComboBox";

	protected Intbox accountNoIntBox;
	protected Combobox accountNameComboBox;
	protected Listbox divisionListBox;
	protected Listbox departmentListBox;
	protected Row divDeptRow;
	
	private static Logger logger;
	private Integer selectedIndex = null;

	public SearchByAccountWindow() {
		logger = Logger.getLogger(getClass());
	}

	public void onCreate(CreateEvent ce) throws Exception{
		accountNoIntBox = (Intbox)getFellow(ACCOUNT_NO_ID);
		accountNameComboBox = (Combobox)getFellow(ACCOUNT_NAME_ID);
		if (hasFellow(DIV_ID)) {
			divisionListBox = (Listbox)getFellow(DIV_ID);
		}
		if (hasFellow(DIV_ID)) {
			departmentListBox = (Listbox)getFellow(DEPT_ID);
		}
		if (hasFellow(DIV_ID)) {
			divDeptRow = (Row) getFellow(DIV_DEPT_ROW_ID);
			divDeptRow.setVisible(false);
		}
	}

	protected void populateAccountForm(SearchByAccountForm form) throws WrongValueException{
		if(departmentListBox.getSelectedItem()!=null){
			if(!(departmentListBox.getSelectedItem().getValue() instanceof String)) {
				form.setDepartment((AmtbAccount)departmentListBox.getSelectedItem().getValue());
			}
		}

		if(form.getDepartment()==null){
			if(divisionListBox.getSelectedItem()!=null){
				if(!(divisionListBox.getSelectedItem().getValue() instanceof String)) {
					form.setDivision((AmtbAccount)divisionListBox.getSelectedItem().getValue());
				}
			}
		}

		if(form.getDivision()==null){
			
			//Fix to bypass IE6 issue with double spacing
			if(selectedIndex != null)
				accountNameComboBox.setSelectedIndex(selectedIndex);
			
			if(accountNameComboBox.getSelectedItem()!=null) {
				form.setAccount((AmtbAccount)accountNameComboBox.getSelectedItem().getValue());
			} else{
				form.setAccountName(accountNameComboBox.getValue());
			}
		}

		if(form.getAccount()==null){
			form.setCustomerNo(accountNoIntBox.getText());
		}
	}

	public void onSelectAccountName() throws InterruptedException{
		logger.debug("Account Name selected");

		try{
			//Fix to bypass IE6 issue with double spacing
			if(accountNameComboBox.getChildren().size()==1)
				accountNameComboBox.setSelectedIndex(0);

			if(accountNameComboBox.getSelectedItem()!=null){
				AmtbAccount selectedAccount = (AmtbAccount)accountNameComboBox.getSelectedItem().getValue();
				selectedIndex = accountNameComboBox.getSelectedIndex();
				accountNoIntBox.setText(selectedAccount.getCustNo());

				//Display division or department according to account category
				if(selectedAccount.getAccountCategory().equals(NonConfigurableConstants.ACCOUNT_CATEGORY_CORPORATE)){
					List<AmtbAccount> divisions = businessHelper.getPaymentBusiness().searchBillableAccountByParentAccount(selectedAccount);
					List<AmtbAccount> departments = businessHelper.getPaymentBusiness().searchBillableAccountByGrandParentAccount(selectedAccount);
					setDivisionInputVisible(divisions);
					setDepartmentInputVisible(departments);
				}
				else{
					setDivisionInputInvisible();
					setDepartmentInputInvisible();
				}
			}
			else{
				setDivisionInputInvisible();
				setDepartmentInputInvisible();
			}
		}
		catch(Exception e){
			Messagebox.show(com.cdgtaxi.ibs.common.NonConfigurableConstants.COMMON_ERROR_MESSAGE,
					"Error", Messagebox.OK, Messagebox.ERROR);
			e.printStackTrace();
		}
	}

	public void onSelectDivision(Listbox divisionListBox) throws InterruptedException{
		logger.debug("Division selected");

		try{
			Object selectedValue = divisionListBox.getSelectedItem().getValue();
			if((selectedValue instanceof String) == false){
				Listbox departmentListBox = (Listbox)getFellow("departmentListBox");
				departmentListBox.setSelectedIndex(0);
			}
		}
		catch(Exception e){
			Messagebox.show(com.cdgtaxi.ibs.common.NonConfigurableConstants.COMMON_ERROR_MESSAGE,
					"Error", Messagebox.OK, Messagebox.ERROR);
			e.printStackTrace();
		}
	}

	public void onSelectDepartment(Listbox departmentListBox) throws InterruptedException{
		logger.info("");

		try{
			Object selectedValue = departmentListBox.getSelectedItem().getValue();
			if((selectedValue instanceof String) == false){
				Listbox divisionListBox = (Listbox)this.getFellow("divisionListBox");
				divisionListBox.setSelectedIndex(0);
			}
		}
		catch(Exception e){
			Messagebox.show(com.cdgtaxi.ibs.common.NonConfigurableConstants.COMMON_ERROR_MESSAGE,
					"Error", Messagebox.OK, Messagebox.ERROR);
			e.printStackTrace();
		}
	}

	public void searchBillableAccountByAccountNo() throws InterruptedException{
		logger.debug("Searching accounts by Account No");

		Integer accountNo = accountNoIntBox.getValue();
		//		Integer accountNo = ((Intbox)getFellow(ACCOUNT_NO_ID)).getValue();
		//		Combobox accountNameComboBox = (Combobox) getFellow(ACCOUNT_NAME_ID);

		if(accountNo==null) {
			return;
		}

		//accountName still the same as selected one, skip
		if(accountNameComboBox.getSelectedItem()!=null){
			AmtbAccount selectedAccount = (AmtbAccount)accountNameComboBox.getSelectedItem().getValue();
			if(accountNo.toString().equals(selectedAccount.getCustNo())) {
				return;
			}
		}

		//Clear combobox for a new search
		accountNameComboBox.setRawValue(null);
		//Clear list for every new search
		accountNameComboBox.getChildren().clear();
		//Clear division + department
		onSelectAccountName();

		try{
			List<AmtbAccount> accounts = businessHelper.getPaymentBusiness().searchBillableAccount(accountNo.toString(), null);
			for(AmtbAccount account : accounts){
				Comboitem item = new Comboitem(account.getAccountName()+" ("+account.getCustNo()+")");
				item.setValue(account);
				accountNameComboBox.appendChild(item);
			}
			if(accounts.size()==1) {
				accountNameComboBox.setSelectedIndex(0);
				this.onSelectAccountName();
			}
		}
		catch(Exception e){
			Messagebox.show(com.cdgtaxi.ibs.common.NonConfigurableConstants.COMMON_ERROR_MESSAGE,
					"Error", Messagebox.OK, Messagebox.ERROR);
			e.printStackTrace();
		}
	}

	public void searchBillableAccountByAccountName(String name) throws InterruptedException{
		logger.debug("Searching accounts by Account Name");

		//Retrieve entered input
		//		Combobox accountNameComboBox = (Combobox) getFellow(ACCOUNT_NAME_ID);
		//		String name = accountNameComboBox.getValue();

		//only begin new search if input is greater than 2
		if(name.length()<3) {
			return;
		}
		

		//accountName still the same as selected one, skip
		if(accountNameComboBox.getSelectedItem()!=null){
			AmtbAccount selectedAccount = (AmtbAccount) accountNameComboBox.getSelectedItem().getValue();
			if(name.equals(selectedAccount.getAccountName()+" ("+selectedAccount.getCustNo()+")")) {
				return;
			}
		}

		//clear textbox for a new search
		//		Intbox accountNoIntBox = (Intbox)getFellow(ACCOUNT_NO_ID);
		accountNoIntBox.setRawValue(null);
		//Clear list for every new search
		accountNameComboBox.getChildren().clear();
		//Clear division + department
		onSelectAccountName();

		try{
			List<AmtbAccount> accounts = businessHelper.getPaymentBusiness().searchBillableAccount(null, name);
			for(AmtbAccount account : accounts){
				Comboitem item = new Comboitem(account.getAccountName()+" ("+account.getCustNo()+")");
				item.setValue(account);
				accountNameComboBox.appendChild(item);
			}
			if(accounts.size()==1){
				//				accountNameComboBox.setSelectedIndex(0);
				this.onSelectAccountName();
			} else {
				accountNameComboBox.open();
			}
		}
		catch(Exception e){
			Messagebox.show(com.cdgtaxi.ibs.common.NonConfigurableConstants.COMMON_ERROR_MESSAGE,
					"Error", Messagebox.OK, Messagebox.ERROR);
			e.printStackTrace();
		}
	}

	private void setDivisionInputVisible(List<AmtbAccount> divisions){
		((Row)getFellow(DIV_DEPT_ROW_ID)).setVisible(true);
		((Label)getFellow(DIV_LABEL_ID)).setVisible(true);
		Listbox divisionListBox = (Listbox)getFellow(DIV_ID);
		divisionListBox.setVisible(true);
		divisionListBox.getChildren().clear();
		divisionListBox.appendChild(ComponentUtil.createNotRequiredListItem());

		for(AmtbAccount division : divisions){
			Listitem newItem = new Listitem(division.getAccountName()+" ("+division.getCode()+")");
			newItem.setValue(division);
			divisionListBox.appendChild(newItem);
		}
		// Focus on division box so that the onchanging event for search account name will not be triggered.
		if (divisionListBox.getItemCount()> 0)
			divisionListBox.focus();
	}

	private void setDepartmentInputVisible(List<AmtbAccount> departments){
		((Label)getFellow(DEPT_LABEL_ID)).setVisible(true);
		Listbox departmentListBox = (Listbox)getFellow(DEPT_ID);
		departmentListBox.setVisible(true);
		departmentListBox.getChildren().clear();
		departmentListBox.appendChild(ComponentUtil.createNotRequiredListItem());

		for(AmtbAccount department : departments){
			Listitem newItem = new Listitem(department.getAccountName()+" ("+department.getCode()+")");
			newItem.setValue(department);
			departmentListBox.appendChild(newItem);
		}
	}

	private void setDivisionInputInvisible(){
		((Row)getFellow(DIV_DEPT_ROW_ID)).setVisible(false);
		((Label)getFellow(DIV_LABEL_ID)).setVisible(false);
		Listbox divisionListBox = (Listbox)getFellow(DIV_ID);
		divisionListBox.setVisible(false);
		divisionListBox.getChildren().clear();
	}

	private void setDepartmentInputInvisible(){
		((Label)getFellow(DEPT_LABEL_ID)).setVisible(false);
		Listbox departmentListBox = (Listbox)getFellow(DEPT_ID);
		departmentListBox.setVisible(false);
		departmentListBox.getChildren().clear();
	}

	public void reset() throws InterruptedException {
		accountNoIntBox.setValue(null);
		accountNameComboBox.setValue(null);
		selectedIndex = null;
		accountNameComboBox.getChildren().clear();
		divisionListBox.clearSelection();
		departmentListBox.clearSelection();
		divDeptRow.setVisible(false);
	}

	@Override
	public void refresh() throws InterruptedException {}
}
