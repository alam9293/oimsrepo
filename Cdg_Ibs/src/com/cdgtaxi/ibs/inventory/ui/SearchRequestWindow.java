package com.cdgtaxi.ibs.inventory.ui;

import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.Map.Entry;

import org.apache.log4j.Logger;
import org.zkoss.zhtml.Messagebox;
import org.zkoss.zk.ui.Components;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zk.ui.ext.AfterCompose;
import org.zkoss.zul.Button;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Comboitem;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.Label;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Row;
import org.zkoss.zul.Textbox;

import com.cdgtaxi.ibs.Uri;
import com.cdgtaxi.ibs.common.ConfigurableConstants;
import com.cdgtaxi.ibs.common.NonConfigurableConstants;
import com.cdgtaxi.ibs.common.model.AmtbAccount;
import com.cdgtaxi.ibs.common.model.forms.SearchIssuanceRequestForm;
import com.cdgtaxi.ibs.common.ui.CommonWindow;
import com.cdgtaxi.ibs.util.ComponentUtil;
import com.cdgtaxi.ibs.util.DateUtil;
import com.cdgtaxi.ibs.util.StringUtil;
import com.cdgtaxi.ibs.web.component.Constants;

public class SearchRequestWindow extends CommonWindow implements AfterCompose{

	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(SearchRequestWindow.class);
	private Integer selectedIndex = null;
	
	private Intbox accountNoIB, requestNoIB;
	private Combobox accountNameCB;
	private Listbox divisionLB, departmentLB, requestStatusLB, resultLB;
	private Row divisionDepartmentRow;
	private Label divisionLbl, departmentLbl;
	private Datebox requestDateFromDB, requestDateToDB;
	private Button newRequestBtn;
	private Textbox requestorTB;
	
	public void afterCompose() {
		Components.wireVariables(this, this);
		
		if(!this.checkUriAccess(Uri.REQUEST_INVENTORY_ISSUANCE))
			newRequestBtn.setDisabled(true);
		
		requestStatusLB.appendChild(ComponentUtil.createNotRequiredListItem());
		Set<Entry<String, String>> requestStatusEntries = NonConfigurableConstants.INVENTORY_REQUEST_STATUS.entrySet();
		for(Entry<String, String> entry : requestStatusEntries){
			if(!entry.getKey().equals(NonConfigurableConstants.INVENTORY_REQUEST_STATUS_NEW))
				requestStatusLB.appendItem(entry.getValue(), entry.getKey());
			if(entry.getKey().equals(NonConfigurableConstants.INVENTORY_REQUEST_STATUS_PENDING))
				requestStatusLB.setSelectedIndex(requestStatusLB.getItemCount()-1);
		}
		if(requestStatusLB.getSelectedItem()==null) requestStatusLB.setSelectedIndex(0);
		
		//Default search result to pending items
		SearchIssuanceRequestForm form = new SearchIssuanceRequestForm();
		form.requestStatus = NonConfigurableConstants.INVENTORY_REQUEST_STATUS_PENDING;
		List<Object[]> results = this.businessHelper.getInventoryBusiness().searchIssuanceRequest(form);
		if(results.size()>0){
			for(Object[] result : results){
				Listitem item = new Listitem();
				item.setValue(result[0]);
				item.appendChild(newListcell(result[0], StringUtil.GLOBAL_STRING_FORMAT));
				item.appendChild(newListcell(result[1], StringUtil.GLOBAL_STRING_FORMAT));
				item.appendChild(newListcell(result[2], StringUtil.GLOBAL_STRING_FORMAT));
				item.appendChild(newListcell(NonConfigurableConstants.INVENTORY_REQUEST_STATUS.get(result[3]), StringUtil.GLOBAL_STRING_FORMAT));
				item.appendChild(newListcell(result[4], StringUtil.GLOBAL_STRING_FORMAT));
				item.appendChild(newListcell(result[5], StringUtil.GLOBAL_INTEGER_FORMAT));
				item.appendChild(newListcell(result[6], DateUtil.LAST_UPDATED_DATE_FORMAT));
				item.appendChild(newListcell(result[7], StringUtil.GLOBAL_STRING_FORMAT));
				resultLB.appendChild(item);
			}
			
			if(results.size()>ConfigurableConstants.getMaxQueryResult())
				resultLB.removeItemAt(ConfigurableConstants.getMaxQueryResult());
			
			if(resultLB.getListfoot()!=null)
				resultLB.removeChild(resultLB.getListfoot());
		}
		else{
			if(resultLB.getListfoot()==null){
				resultLB.appendChild(ComponentUtil.createNoRecordsFoundListFoot(8));
			}
		}
		
		resultLB.setMold(Constants.LISTBOX_MOLD_PAGING);
		resultLB.setPageSize(10);
	}
	
	public void onSelectAccountName() throws InterruptedException{
		logger.info("");
		
		try{
			//Fix to bypass IE6 issue with double spacing
			if(accountNameCB.getChildren().size()==1)
				accountNameCB.setSelectedIndex(0);
			
			if(accountNameCB.getSelectedItem()!=null){
				AmtbAccount selectedAccount = (AmtbAccount)accountNameCB.getSelectedItem().getValue();
				selectedIndex = accountNameCB.getSelectedIndex();
				accountNoIB.setText(selectedAccount.getCustNo());
				
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
				departmentLB.setSelectedIndex(0);
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
				divisionLB.setSelectedIndex(0);
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
		
		Integer accountNo = accountNoIB.getValue();
		
		if(accountNo==null) return;
		
		//accountName still the same as selected one, skip
		if(accountNameCB.getSelectedItem()!=null){
			AmtbAccount selectedAccount = (AmtbAccount)accountNameCB.getSelectedItem().getValue();
			if(accountNo.toString().equals(selectedAccount.getCustNo())) return;
		}
		
		//Clear combobox for a new search
		accountNameCB.setText("");
		//Clear list for every new search
		accountNameCB.getChildren().clear();
		//Clear division + department
		this.setDivisionInputInvisible();
		this.setDepartmentInputInvisible();
		//clear selectedIndex
		selectedIndex = null;
		
		try{
			List<AmtbAccount> accounts = this.businessHelper.getPaymentBusiness().searchBillableAccount(accountNo.toString(), null);
			for(AmtbAccount account : accounts){
				Comboitem item = new Comboitem(account.getAccountName()+" ("+account.getCustNo()+")");
				item.setValue(account);
				accountNameCB.appendChild(item);
			}
			if(accounts.size()==1) {
				accountNameCB.setSelectedIndex(0);
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
		//only begin new search if input is greater than 2
		if(name.length()<3) return;
		
		//accountName still the same as selected one, skip
		if(accountNameCB.getSelectedItem()!=null){
			AmtbAccount selectedAccount = (AmtbAccount)accountNameCB.getSelectedItem().getValue();
			if(name.equals(selectedAccount.getAccountName()+" ("+selectedAccount.getCustNo()+")"))
				return;
		}
		
		//clear textbox for a new search
		accountNoIB.setText("");
		//Clear list for every new search
		accountNameCB.getChildren().clear();
		//Clear division + department
		this.setDivisionInputInvisible();
		this.setDepartmentInputInvisible();
		//clear selectedIndex
		selectedIndex = null;
		
		try{
			List<AmtbAccount> accounts = this.businessHelper.getPaymentBusiness().searchBillableAccount(null, name);
			for(AmtbAccount account : accounts){
				Comboitem item = new Comboitem(account.getAccountName()+" ("+account.getCustNo()+")");
				item.setValue(account);
				accountNameCB.appendChild(item);
			}
			if(accounts.size()==1){
				accountNameCB.setSelectedIndex(0);
				this.onSelectAccountName();
			}
			else accountNameCB.open();
		}
		catch(Exception e){
			Messagebox.show(com.cdgtaxi.ibs.common.NonConfigurableConstants.COMMON_ERROR_MESSAGE, 
					"Error", Messagebox.OK, Messagebox.ERROR);
			e.printStackTrace();
		}
	}
	
	private void setDivisionInputVisible(List<AmtbAccount> divisions){
		divisionDepartmentRow.setVisible(true);
		divisionLbl.setVisible(true);
		divisionLB.setVisible(true);
		divisionLB.getChildren().clear();
		divisionLB.appendChild(ComponentUtil.createNotRequiredListItem());
		
		for(AmtbAccount division : divisions){
			Listitem newItem = new Listitem(division.getAccountName()+" ("+division.getCode()+")");
			newItem.setValue(division);
			divisionLB.appendChild(newItem);
		}
		// Focus on division box so that the onchanging event for search account name will not be triggered.
		if (divisionLB.getItemCount()> 0)
			divisionLB.focus();
	}
	
	private void setDepartmentInputVisible(List<AmtbAccount> departments){
		departmentLbl.setVisible(true);
		departmentLB.setVisible(true);
		departmentLB.getChildren().clear();
		departmentLB.appendChild(ComponentUtil.createNotRequiredListItem());
		
		for(AmtbAccount department : departments){
			Listitem newItem = new Listitem(department.getAccountName()+" ("+department.getCode()+")");
			newItem.setValue(department);
			departmentLB.appendChild(newItem);
		}
	}
	
	private void setDivisionInputInvisible(){
		divisionDepartmentRow.setVisible(false);
		divisionLbl.setVisible(false);
		divisionLB.setVisible(false);
		divisionLB.getChildren().clear();
	}
	
	private void setDepartmentInputInvisible(){
		departmentLbl.setVisible(false);
		departmentLB.setVisible(false);
		departmentLB.getChildren().clear();
	}
	
	public void search(boolean checkForAtLeastOneCriteriaSelected) throws InterruptedException{
		try{
			resultLB.getItems().clear();
			
			SearchIssuanceRequestForm form = this.buildSearchForm();
			
			if(form.isAtLeastOneCriteriaSelected == false &&
					checkForAtLeastOneCriteriaSelected == true){
				Messagebox.show("Please enter one of the selection criteria", 
						"Error", Messagebox.OK, Messagebox.ERROR);
				return;
			}
			else if(form.isAtLeastOneCriteriaSelected == false &&
					checkForAtLeastOneCriteriaSelected == false){
				//Skip refreshing
				return;
			}
			
			this.displayProcessing();
			
			List<Object[]> results = this.businessHelper.getInventoryBusiness().searchIssuanceRequest(form);
			if(results.size()>0){
				
				if(results.size()>ConfigurableConstants.getMaxQueryResult())
					Messagebox.show(NonConfigurableConstants.getExceedMaxResultMessage(), "Alert", Messagebox.OK, Messagebox.INFORMATION);
				
				for(Object[] result : results){
					Listitem item = new Listitem();
					item.setValue(result[0]);
					item.appendChild(newListcell(result[0], StringUtil.GLOBAL_STRING_FORMAT));
					item.appendChild(newListcell(result[1], StringUtil.GLOBAL_STRING_FORMAT));
					item.appendChild(newListcell(result[2], StringUtil.GLOBAL_STRING_FORMAT));
					item.appendChild(newListcell(NonConfigurableConstants.INVENTORY_REQUEST_STATUS.get(result[3]), StringUtil.GLOBAL_STRING_FORMAT));
					item.appendChild(newListcell(result[4], StringUtil.GLOBAL_STRING_FORMAT));
					item.appendChild(newListcell(result[5], StringUtil.GLOBAL_INTEGER_FORMAT));
					item.appendChild(newListcell(result[6], DateUtil.LAST_UPDATED_DATE_FORMAT));
					item.appendChild(newListcell(result[7], StringUtil.GLOBAL_STRING_FORMAT));
					resultLB.appendChild(item);
				}
				
				if(results.size()>ConfigurableConstants.getMaxQueryResult())
					resultLB.removeItemAt(ConfigurableConstants.getMaxQueryResult());
				
				if(resultLB.getListfoot()!=null)
					resultLB.removeChild(resultLB.getListfoot());
			}
			else{
				if(resultLB.getListfoot()==null){
					resultLB.appendChild(ComponentUtil.createNoRecordsFoundListFoot(8));
				}
			}
			
			resultLB.setMold(Constants.LISTBOX_MOLD_PAGING);
			resultLB.setPageSize(10);
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
	
	private SearchIssuanceRequestForm buildSearchForm() throws WrongValueException{
		
		SearchIssuanceRequestForm form = new SearchIssuanceRequestForm();
		
		//Request Date From & Receipt Date To
		if(requestDateFromDB.getValue()!=null || requestDateToDB.getValue()!=null){
			if(requestDateFromDB.getValue()!=null && requestDateToDB.getValue()!=null){
				if(requestDateFromDB.getValue().compareTo(requestDateToDB.getValue())>0)
					throw new WrongValueException(requestDateFromDB, "Request Date From cannot be later than Request Date To");
			}
			else if(requestDateFromDB.getValue()!=null && requestDateToDB.getValue()==null)
				requestDateToDB.setValue(requestDateFromDB.getValue());
			else if(requestDateToDB.getValue()!=null && requestDateFromDB.getValue()==null)
				requestDateFromDB.setValue(requestDateToDB.getValue());
			
			form.requestDateFrom = DateUtil.convertUtilDateToSqlDate(requestDateFromDB.getValue());
			form.requestDateTo = DateUtil.convertUtilDateToSqlDate(requestDateToDB.getValue());
			form.isAtLeastOneCriteriaSelected = true;
		}
		
		if(departmentLB.getSelectedItem()!=null){
			if(!(departmentLB.getSelectedItem().getValue() instanceof String)){
				form.department = ((AmtbAccount)departmentLB.getSelectedItem().getValue());
				form.isAtLeastOneCriteriaSelected = true;
			}
		}
		
		if(form.department == null){
			if(divisionLB.getSelectedItem()!=null){
				if(!(divisionLB.getSelectedItem().getValue() instanceof String)){
					form.division = ((AmtbAccount)divisionLB.getSelectedItem().getValue());
					form.isAtLeastOneCriteriaSelected = true;
				}
			}
		}
		
		if(form.division == null){
			if(selectedIndex != null)
				accountNameCB.setSelectedIndex(selectedIndex);
			
			if(accountNameCB.getSelectedItem()!=null){
				form.account = ((AmtbAccount)accountNameCB.getSelectedItem().getValue());
				form.isAtLeastOneCriteriaSelected = true;
			}
			else{
				String accountName = accountNameCB.getValue();
				if(accountName!=null && accountName.length()>=3){
					form.accountName = accountNameCB.getValue()!=null ? accountNameCB.getValue().toUpperCase() : null;
					form.isAtLeastOneCriteriaSelected = true;
				}
			}
		}
		
		if(form.account == null && accountNoIB.getValue()!=null){
			form.customerNo = accountNoIB.getValue().toString();
			form.isAtLeastOneCriteriaSelected = true;
		}
		
		if(requestNoIB.getValue()!=null){
			form.requestNo = requestNoIB.getValue();
			form.isAtLeastOneCriteriaSelected = true;
		}
		
		if(requestStatusLB.getSelectedItem().getValue().toString().length()>0){
			form.requestStatus = requestStatusLB.getSelectedItem().getValue().toString();
			form.isAtLeastOneCriteriaSelected = true;
		}
		
		if(requestorTB.getValue()!=null && requestorTB.getValue().length()>0){
			form.requestor = requestorTB.getValue();
			form.isAtLeastOneCriteriaSelected = true;
		}
		
		return form;
	}
	
	public void viewRequest() throws InterruptedException {
		Integer requestNo = (Integer) resultLB.getSelectedItem().getValue();
		try {
			if(this.checkUriAccess(Uri.VIEW_INVENTORY_REQUEST_ISSUANCE)){
				HashMap<String, Object> map = new HashMap<String, Object>();
				map.put("issueReqNo", requestNo);
				forward(Uri.VIEW_INVENTORY_REQUEST_ISSUANCE, map);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			Messagebox.show(ex.toString());
		}
	}
	
	@Override
	public void refresh() throws InterruptedException {
		this.search(false);
	}
	
	public void reset(){
		accountNameCB.setValue("");
		accountNameCB.getChildren().clear();
		accountNoIB.setText("");
		this.setDivisionInputInvisible();
		this.setDepartmentInputInvisible();
		
		requestNoIB.setText("");
		requestStatusLB.setSelectedIndex(0);
		requestDateFromDB.setText("");
		requestDateToDB.setText("");
		requestorTB.setValue("");
		
		resultLB.getItems().clear();
		if(resultLB.getListfoot()==null){
			resultLB.appendChild(ComponentUtil.createNoRecordsFoundListFoot(8));
		}
		resultLB.setMold(Constants.LISTBOX_MOLD_PAGING);
		resultLB.setPageSize(10);
		
		selectedIndex = null;
	}
	
	public void requestIssuance() throws InterruptedException {
		try {
			forward(Uri.REQUEST_INVENTORY_ISSUANCE, null);
		} catch (Exception ex) {
			ex.printStackTrace();
			Messagebox.show(ex.toString());
		}
	}
}
