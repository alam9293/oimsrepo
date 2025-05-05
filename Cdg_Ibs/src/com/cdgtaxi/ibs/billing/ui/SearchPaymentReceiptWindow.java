package com.cdgtaxi.ibs.billing.ui;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;
import org.zkoss.zhtml.Messagebox;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Comboitem;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.Label;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Longbox;
import org.zkoss.zul.Row;

import com.cdgtaxi.ibs.Uri;
import com.cdgtaxi.ibs.common.ConfigurableConstants;
import com.cdgtaxi.ibs.common.NonConfigurableConstants;
import com.cdgtaxi.ibs.common.model.AmtbAccount;
import com.cdgtaxi.ibs.common.model.BmtbPaymentReceipt;
import com.cdgtaxi.ibs.common.model.forms.SearchPaymentReceiptForm;
import com.cdgtaxi.ibs.common.ui.CommonWindow;
import com.cdgtaxi.ibs.util.ComponentUtil;
import com.cdgtaxi.ibs.util.DateUtil;
import com.cdgtaxi.ibs.util.StringUtil;
import com.cdgtaxi.ibs.web.component.CapsTextbox;
import com.cdgtaxi.ibs.web.component.Constants;

public class SearchPaymentReceiptWindow extends CommonWindow{

	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(SearchPaymentReceiptWindow.class);
	private static final String SELF = "searchCancelPaymentReceiptWindow";
	private Integer selectedIndex = null;
	
	public void onSelectAccountName() throws InterruptedException{
		logger.info("");
		
		try{
			Combobox accountNameComboBox = (Combobox)this.getFellow("accountNameComboBox");
			Intbox accountNoIntBox = (Intbox)this.getFellow("accountNoIntBox");
			
			//Fix to bypass IE6 issue with double spacing
			if(accountNameComboBox.getChildren().size()==1)
				accountNameComboBox.setSelectedIndex(0);
			
			if(accountNameComboBox.getSelectedItem()!=null){
				AmtbAccount selectedAccount = (AmtbAccount)accountNameComboBox.getSelectedItem().getValue();
				selectedIndex = accountNameComboBox.getSelectedIndex();
				accountNoIntBox.setText(selectedAccount.getCustNo());
				
				//Display division or department according to account category
				if(selectedAccount.getAccountCategory().equals(NonConfigurableConstants.ACCOUNT_CATEGORY_CORPORATE)){
					List<AmtbAccount> divisions = this.businessHelper.getPaymentBusiness().searchBillableAccountByParentAccount(selectedAccount);
					List<AmtbAccount> departments = this.businessHelper.getPaymentBusiness().searchBillableAccountByGrandParentAccount(selectedAccount);
					this.setDivisionInputVisible(divisions);
					this.setDepartmentInputVisible(departments);
				}
				else{
					this.setDivisionInputInvisible();
					this.setDepartmentInputInvisible();
				}
			}
			else{
				this.setDivisionInputInvisible();
				this.setDepartmentInputInvisible();
			}
		}
		catch(Exception e){
			Messagebox.show(com.cdgtaxi.ibs.common.NonConfigurableConstants.COMMON_ERROR_MESSAGE, 
					"Error", Messagebox.OK, Messagebox.ERROR);
			e.printStackTrace();
		}
	}
	
	public void onSelectDivision(Listbox divisionListBox) throws InterruptedException{
		logger.info("");
		
		try{
			Object selectedValue = divisionListBox.getSelectedItem().getValue();
			if((selectedValue instanceof String) == false){
				Listbox departmentListBox = (Listbox)this.getFellow("departmentListBox");
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
		logger.info("");
		
		Intbox accountNoIntBox = (Intbox)this.getFellow("accountNoIntBox");
		Combobox accountNameComboBox = (Combobox)this.getFellow("accountNameComboBox");
		Integer accountNo = accountNoIntBox.getValue();
		
		if(accountNo==null) return;
		
		//accountName still the same as selected one, skip
		if(accountNameComboBox.getSelectedItem()!=null){
			AmtbAccount selectedAccount = (AmtbAccount)accountNameComboBox.getSelectedItem().getValue();
			if(accountNo.toString().equals(selectedAccount.getCustNo())) return;
		}
		
		//Clear combobox for a new search
		accountNameComboBox.setText("");
		//Clear list for every new search
		accountNameComboBox.getChildren().clear();
		//Clear division + department
		this.setDivisionInputInvisible();
		this.setDepartmentInputInvisible();
		
		try{
			List<AmtbAccount> accounts = this.businessHelper.getPaymentBusiness().searchBillableAccount(accountNo.toString(), null);
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
		logger.info("");
		Combobox accountNameComboBox = (Combobox)this.getFellow("accountNameComboBox");
		
		//only begin new search if input is greater than 2
		if(name.length()<3) return;
		
		//accountName still the same as selected one, skip
		if(accountNameComboBox.getSelectedItem()!=null){
			AmtbAccount selectedAccount = (AmtbAccount)accountNameComboBox.getSelectedItem().getValue();
			if(name.equals(selectedAccount.getAccountName()+" ("+selectedAccount.getCustNo()+")"))
				return;
		}
		
		//clear textbox for a new search
		Intbox accountNoIntBox = (Intbox)this.getFellow("accountNoIntBox");
		accountNoIntBox.setText("");
		//Clear list for every new search
		accountNameComboBox.getChildren().clear();
		//Clear division + department
		this.setDivisionInputInvisible();
		this.setDepartmentInputInvisible();
		
		try{
			List<AmtbAccount> accounts = this.businessHelper.getPaymentBusiness().searchBillableAccount(null, name);
			for(AmtbAccount account : accounts){
				Comboitem item = new Comboitem(account.getAccountName()+" ("+account.getCustNo()+")");
				item.setValue(account);
				accountNameComboBox.appendChild(item);
			}
			if(accounts==null || accounts.size()==0){
				selectedIndex = null;
			}
			else if(accounts.size()==1){
				accountNameComboBox.setSelectedIndex(0);
				this.onSelectAccountName();
			}
			else accountNameComboBox.open();
		}
		catch(Exception e){
			Messagebox.show(com.cdgtaxi.ibs.common.NonConfigurableConstants.COMMON_ERROR_MESSAGE, 
					"Error", Messagebox.OK, Messagebox.ERROR);
			e.printStackTrace();
		}
	}
	
	private void setDivisionInputVisible(List<AmtbAccount> divisions){
		((Row)this.getFellow("divisionDepartmentRow")).setVisible(true);
		((Label)this.getFellow("divisionLabel")).setVisible(true);
		Listbox divisionListBox = (Listbox)this.getFellow("divisionListBox");
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
		((Label)this.getFellow("departmentLabel")).setVisible(true);
		Listbox departmentListBox = (Listbox)this.getFellow("departmentListBox");
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
		((Row)this.getFellow("divisionDepartmentRow")).setVisible(false);
		((Label)this.getFellow("divisionLabel")).setVisible(false);
		Listbox divisionListBox = (Listbox)this.getFellow("divisionListBox");
		divisionListBox.setVisible(false);
		divisionListBox.getChildren().clear();
	}
	
	private void setDepartmentInputInvisible(){
		((Label)this.getFellow("departmentLabel")).setVisible(false);
		Listbox departmentListBox = (Listbox)this.getFellow("departmentListBox");
		departmentListBox.setVisible(false);
		departmentListBox.getChildren().clear();
	}
	
	public void populatePaymentMode(Listbox paymentModeListBox) throws InterruptedException{
		logger.info("");
		
		try{
			List<Listitem> paymentModes = ComponentUtil.convertToListitems(ConfigurableConstants.getPaymentModes(), true);
			paymentModeListBox.appendChild(ComponentUtil.createNotRequiredListItem());
			for(Listitem listItem : paymentModes){
				paymentModeListBox.appendChild(listItem);
			}
		}
		catch(Exception e){
			Messagebox.show(com.cdgtaxi.ibs.common.NonConfigurableConstants.COMMON_ERROR_MESSAGE, 
					"Error", Messagebox.OK, Messagebox.ERROR);
			e.printStackTrace();
		}
	}
	
	public void populateReceiptStatus(Listbox receiptStatusListBox) throws InterruptedException{
		logger.info("");
		
		try{
			receiptStatusListBox.appendChild(ComponentUtil.createNotRequiredListItem());
			
			Set statusSet = NonConfigurableConstants.RECEIPT_STATUS.keySet();
			Iterator statusIterator = statusSet.iterator();
			while(statusIterator.hasNext()){
				String statusKey = (String)statusIterator.next();
				Listitem listItem = new Listitem(NonConfigurableConstants.RECEIPT_STATUS.get(statusKey), statusKey);
				receiptStatusListBox.appendChild(listItem);
			}
		}
		catch(Exception e){
			Messagebox.show(com.cdgtaxi.ibs.common.NonConfigurableConstants.COMMON_ERROR_MESSAGE, 
					"Error", Messagebox.OK, Messagebox.ERROR);
			e.printStackTrace();
		}
	}
	
	public void searchPaymentReceipt() throws InterruptedException{
		logger.info("");
		
		try{
			Listbox resultListBox = (Listbox)this.getFellow("resultList");
			resultListBox.getItems().clear();
			
			SearchPaymentReceiptForm form = this.buildSearchForm();
			
			if(form.isAtLeastOneCriteriaSelected()==false){
				Messagebox.show("Please enter one of the selection criteria", 
						"Error", Messagebox.OK, Messagebox.ERROR);
				return;
			}
			
			this.displayProcessing();
			
			List<BmtbPaymentReceipt> results = this.businessHelper.getPaymentBusiness().searchPaymentReceipt(form);
			if(results.size()>0){
				
				if(results.size()>ConfigurableConstants.getMaxQueryResult())
					Messagebox.show(NonConfigurableConstants.getExceedMaxResultMessage(), "Alert", Messagebox.OK, Messagebox.INFORMATION);
				
				for(BmtbPaymentReceipt paymentReceipt : results){
					Listitem item = new Listitem();
					item.setValue(paymentReceipt.getPaymentReceiptNo());
					item.appendChild(newListcell(paymentReceipt.getPaymentReceiptNo(), StringUtil.GLOBAL_STRING_FORMAT));
					item.appendChild(newListcell(new Integer(this.businessHelper.getPaymentBusiness().getCustomerNo(paymentReceipt.getAmtbAccount().getAccountNo())), StringUtil.GLOBAL_STRING_FORMAT));
					AmtbAccount tempAccount = paymentReceipt.getAmtbAccount();
					String accountNameLabel = null;
					do{
						accountNameLabel = tempAccount.getAccountName();
					}
					while((tempAccount = tempAccount.getAmtbAccount())!=null);
					item.appendChild(newListcell(accountNameLabel));
					item.appendChild(newListcell(paymentReceipt.getReceiptDt(), DateUtil.GLOBAL_DATE_FORMAT));
					item.appendChild(newListcell(paymentReceipt.getPaymentDate(), DateUtil.GLOBAL_DATE_FORMAT));
					item.appendChild(newListcell(paymentReceipt.getMstbMasterTableByPaymentMode().getMasterValue()));
					item.appendChild(newListcell(paymentReceipt.getPaymentAmount(), StringUtil.GLOBAL_DECIMAL_FORMAT));
					item.appendChild(newListcell(paymentReceipt.getExcessAmount(), StringUtil.GLOBAL_DECIMAL_FORMAT));
					item.appendChild(newListcell(paymentReceipt.getPaymentAmount().subtract(paymentReceipt.getExcessAmount()), StringUtil.GLOBAL_DECIMAL_FORMAT));
					if(!paymentReceipt.getMstbMasterTableByPaymentMode().getMasterCode().equals(NonConfigurableConstants.PAYMENT_MODE_MEMO)){
						if(paymentReceipt.getCancelDt()!=null)
							item.appendChild(newListcell(NonConfigurableConstants.RECEIPT_STATUS.get(NonConfigurableConstants.RECEIPT_STATUS_CANCELLED)));
						else if(paymentReceipt.getExcessAmount().compareTo(new BigDecimal(0)) == 0)
							item.appendChild(newListcell(NonConfigurableConstants.RECEIPT_STATUS.get(NonConfigurableConstants.RECEIPT_STATUS_CLOSED)));
						else
							item.appendChild(newListcell(NonConfigurableConstants.RECEIPT_STATUS.get(NonConfigurableConstants.RECEIPT_STATUS_EXCESS)));
					}
					else{
						if(paymentReceipt.getChequeNo()==null || paymentReceipt.getChequeNo().length()==0)
							item.appendChild(newListcell(NonConfigurableConstants.RECEIPT_STATUS.get(NonConfigurableConstants.RECEIPT_STATUS_PENDING)));
						else
							item.appendChild(newListcell(NonConfigurableConstants.RECEIPT_STATUS.get(NonConfigurableConstants.RECEIPT_STATUS_CLOSED)));
					}
					resultListBox.appendChild(item);
				}
				
				if(results.size()>ConfigurableConstants.getMaxQueryResult())
					resultListBox.removeItemAt(ConfigurableConstants.getMaxQueryResult());
				
				if(resultListBox.getListfoot()!=null)
					resultListBox.removeChild(resultListBox.getListfoot());
			}
			else{
				if(resultListBox.getListfoot()==null){
					resultListBox.appendChild(ComponentUtil.createNoRecordsFoundListFoot(10));
				}
			}
			
			resultListBox.setMold(Constants.LISTBOX_MOLD_PAGING);
			resultListBox.setPageSize(10);
		}
		catch(WrongValueException wve){
			throw wve;
		}
		catch(Exception e){
			Messagebox.show(com.cdgtaxi.ibs.common.NonConfigurableConstants.COMMON_ERROR_MESSAGE, 
					"Error", Messagebox.OK, Messagebox.ERROR);
			e.printStackTrace();
			logger.debug(e.getMessage());
		}
	}
	
	private SearchPaymentReceiptForm buildSearchForm() throws WrongValueException{
		
		SearchPaymentReceiptForm form = new SearchPaymentReceiptForm();
		
		Listbox departmentListBox = (Listbox)this.getFellow("departmentListBox");
		if(departmentListBox.getSelectedItem()!=null){
			if(!(departmentListBox.getSelectedItem().getValue() instanceof String))
				form.setDepartment((AmtbAccount)departmentListBox.getSelectedItem().getValue());
		}
		
		if(form.getDepartment()==null){
			Listbox divisionListBox = (Listbox)this.getFellow("divisionListBox");
			if(divisionListBox.getSelectedItem()!=null){
				if(!(divisionListBox.getSelectedItem().getValue() instanceof String))
					form.setDivision((AmtbAccount)divisionListBox.getSelectedItem().getValue());
			}
		}
		
		if(form.getDivision()==null){
			Combobox accountNameComboBox = (Combobox)this.getFellow("accountNameComboBox");
			if(selectedIndex != null)
				accountNameComboBox.setSelectedIndex(selectedIndex);
			
			if(accountNameComboBox.getSelectedItem()!=null)
				form.setAccount((AmtbAccount)accountNameComboBox.getSelectedItem().getValue());
			else{
				String accountName = accountNameComboBox.getValue();
				if(accountName!=null && accountName.length()>=3)
					form.setAccountName(accountNameComboBox.getValue());
			}
		}
		
		if(form.getAccount()==null){
			String customerNo = ((Intbox)this.getFellow("accountNoIntBox")).getText();
			form.setCustomerNo(customerNo);
		}
		
		Longbox receiptNoLongBox = (Longbox)this.getFellow("receiptNoLongBox");
		form.setReceiptNo(receiptNoLongBox.getValue());
		Listbox paymentModeListBox = (Listbox)this.getFellow("paymentModeListBox");
		String selectedPaymentMode = (String)paymentModeListBox.getSelectedItem().getValue();
		if(!selectedPaymentMode.equals("")){
			form.setPaymentMode(ConfigurableConstants.getMasterTable(ConfigurableConstants.PAYMENT_MODE, selectedPaymentMode));
		}
		//Receipt Date From & Receipt Date To
		Datebox receiptDateFromDateBox = (Datebox)this.getFellow("receiptDateFromDateBox");
		Datebox receiptDateToDateBox = (Datebox)this.getFellow("receiptDateToDateBox");
		if(receiptDateFromDateBox.getValue()!=null){
			if(receiptDateToDateBox.getValue()!=null){
				//Do validation check
				if(receiptDateFromDateBox.getValue().compareTo(receiptDateToDateBox.getValue())>0)
					throw new WrongValueException(receiptDateFromDateBox, "Receipt Date From cannot be later than Receipt Date To");
				else
					form.setReceiptDateFrom(DateUtil.convertUtilDateToSqlDate(receiptDateFromDateBox.getValue()));
			}
			else{
				//Receipt Date From entered but Receipt Date To not entered
				//Receipt Date To set to Receipt Date From
				form.setReceiptDateFrom(DateUtil.convertUtilDateToSqlDate(receiptDateFromDateBox.getValue()));
				form.setReceiptDateTo(DateUtil.convertUtilDateToSqlDate(receiptDateFromDateBox.getValue()));
			}
		}
		if(receiptDateToDateBox.getValue()!=null)
			form.setReceiptDateTo(DateUtil.convertUtilDateToSqlDate(receiptDateToDateBox.getValue()));
		
		Listbox receiptStatusListBox = (Listbox)this.getFellow("receiptStatusListBox");
		String selectedReceiptStatus = (String)receiptStatusListBox.getSelectedItem().getValue();
		if(!selectedReceiptStatus.equals("")){
			form.setReceiptStatus(selectedReceiptStatus);
		}
		
		CapsTextbox chequeNoTextBox = (CapsTextbox)this.getFellow("chequeNoTextBox");
		String chequeNo = chequeNoTextBox.getValue();
		form.setChequeNo(chequeNo);
		
		CapsTextbox txnRefNoTextBox = (CapsTextbox)this.getFellow("txnRefNoTextBox");
		String txnRefNo = txnRefNoTextBox.getValue();
		form.setTransactionRefNo(txnRefNo);
		
		return form;
	}
	
	public void viewReceipt() throws InterruptedException{
		logger.info("");
		
		try{
			Listbox resultListBox = (Listbox)this.getFellow("resultList");
			Long receiptNo = (Long)resultListBox.getSelectedItem().getValue();
			Map map = new HashMap();
			map.put("receiptNo", receiptNo);
			
			BmtbPaymentReceipt paymentReceipt = this.businessHelper.getPaymentBusiness().searchPaymentReceipt(receiptNo);
			if(!paymentReceipt.getMstbMasterTableByPaymentMode().getMasterCode().equals(NonConfigurableConstants.PAYMENT_MODE_MEMO)){
				this.forward(Uri.VIEW_PAYMENT_RECEIPT, map);
			}
			else{
				if(this.checkUriAccess(Uri.VIEW_PAYMENT_RECEIPT))
					this.forward(Uri.VIEW_MEMO_RECEIPT, map);
			}
		}
		catch(WrongValueException wve){
			throw wve;
		}
		catch(Exception e){
			Messagebox.show(com.cdgtaxi.ibs.common.NonConfigurableConstants.COMMON_ERROR_MESSAGE, 
					"Error", Messagebox.OK, Messagebox.ERROR);
			e.printStackTrace();
		}
	}
	
	@Override
	public void refresh() throws InterruptedException {
		this.searchPaymentReceipt();
	}
	
	public void reset(){
		Combobox accountNameComboBox = (Combobox)this.getFellow("accountNameComboBox");
		accountNameComboBox.setValue("");
		accountNameComboBox.getChildren().clear();
		Intbox accountNoIntBox = (Intbox)this.getFellow("accountNoIntBox");
		accountNoIntBox.setText("");
		this.setDivisionInputInvisible();
		this.setDepartmentInputInvisible();
		Longbox receiptNoLongBox = (Longbox)this.getFellow("receiptNoLongBox");
		receiptNoLongBox.setText("");
		Listbox paymentModeListBox = (Listbox)this.getFellow("paymentModeListBox");
		paymentModeListBox.setSelectedIndex(0);
		CapsTextbox chequeNoTextBox = (CapsTextbox)this.getFellow("chequeNoTextBox");
		chequeNoTextBox.setValue("");
		CapsTextbox txnRefNoTextBox = (CapsTextbox)this.getFellow("txnRefNoTextBox");
		txnRefNoTextBox.setValue("");
		Datebox receiptDateFromDateBox = (Datebox)this.getFellow("receiptDateFromDateBox");
		receiptDateFromDateBox.setText("");
		Datebox receiptDateToDateBox = (Datebox)this.getFellow("receiptDateToDateBox");
		receiptDateToDateBox.setText("");
		Listbox receiptStatusListBox = (Listbox)this.getFellow("receiptStatusListBox");
		receiptStatusListBox.setSelectedIndex(0);
		Listbox resultListBox = (Listbox)this.getFellow("resultList");
		resultListBox.getItems().clear();
		if(resultListBox.getListfoot()==null){
			resultListBox.appendChild(ComponentUtil.createNoRecordsFoundListFoot(10));
		}
		resultListBox.setMold(Constants.LISTBOX_MOLD_PAGING);
		resultListBox.setPageSize(10);
		selectedIndex = null;
	}
}
