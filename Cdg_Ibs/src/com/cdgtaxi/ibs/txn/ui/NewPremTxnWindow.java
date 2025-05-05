package com.cdgtaxi.ibs.txn.ui;


import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Comboitem;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Decimalbox;
import org.zkoss.zul.Label;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Row;

import com.cdgtaxi.ibs.common.ConfigurableConstants;
import com.cdgtaxi.ibs.common.NonConfigurableConstants;
import com.cdgtaxi.ibs.common.model.AmtbAccount;
import com.cdgtaxi.ibs.common.model.PmtbProductType;
import com.cdgtaxi.ibs.common.ui.CommonWindow;
import com.cdgtaxi.ibs.util.ComponentUtil;
import com.cdgtaxi.ibs.util.DateUtil;
import com.cdgtaxi.ibs.util.LoggerUtil;
import com.cdgtaxi.ibs.web.component.CapsTextbox;


public class NewPremTxnWindow extends CommonWindow {
	
	private static Logger logger = Logger.getLogger(NewPremTxnWindow.class);

	
	public NewPremTxnWindow(){
		
	}
	
	public void onCreate()
	{
		CapsTextbox accountNoTextBox = (CapsTextbox)this.getFellow("accountNoTextBox");
		accountNoTextBox.focus();
	}


	@Override
	public void refresh() throws InterruptedException {
			
	}
	
	public List<Listitem> getProductTypes() {
		List<Listitem> productTypeList = ComponentUtil.convertToListitems(this.businessHelper.getTxnBusiness().getAllProductTypes(), false);
		return cloneList(productTypeList);
	}

	public List<Listitem> getPremierProductType() {
		List<Listitem> productTypeList = ComponentUtil.convertToListitems(this.businessHelper.getTxnBusiness().getPremierProductTypes(), false);
		return cloneList(productTypeList);
	}
	
	
	protected List<Listitem> cloneList(List<Listitem> listitems){
		List<Listitem> returnList = new ArrayList<Listitem>();
		for(Listitem listitem : listitems){
			returnList.add(new Listitem(listitem.getLabel(), listitem.getValue()));
		}
		return returnList;
	}
	
	public List<Listitem> getServiceProvider() {
		List<Listitem> serviceProviderList = ComponentUtil.convertToListitems(ConfigurableConstants.getServiceProvider(), false);
		return cloneList(serviceProviderList);
	}

	public List<Listitem> getTripType() {
		List<Listitem> tripTypeList = ComponentUtil.convertToListitems(ConfigurableConstants.getVehicleTripTypes(), false);
		return cloneList(tripTypeList);
	}
	
	public List<Listitem> getVehicleGroup() {
		List<Listitem> vehicleGroupList = ComponentUtil.convertToListitems(ConfigurableConstants.getVehicleTypes(), false);
		return cloneList(vehicleGroupList);
	}
	
	public List<Listitem> getJobType() {
		List<Listitem> jobTypeList = ComponentUtil.convertToListitems(ConfigurableConstants.getJobType(), false);
		return cloneList(jobTypeList);
	}
	
	public List<Listitem> getVehicleType() {
		List<Listitem> vehicleTypeList = ComponentUtil.convertToListitems(ConfigurableConstants.getVehicleModel(), false);
		return cloneList(vehicleTypeList);
	}
	
	public void onSelectAccountName() throws InterruptedException{
		logger.info("");
		try{
			Combobox accountNameComboBox = (Combobox)this.getFellow("accountNameComboBox");
			CapsTextbox accountNoTextBox = (CapsTextbox)this.getFellow("accountNoTextBox");
			
			if(accountNameComboBox.getSelectedItem()!=null){
				AmtbAccount selectedAccount = (AmtbAccount)accountNameComboBox.getSelectedItem().getValue();
				// Validation - Closed account cannot create transaction
				// Only need to check corporate or applicant. Not required for division as it will be terminated instead of closed.
				if (this.businessHelper.getTxnBusiness().isAccountClosed(selectedAccount.getCustNo().toString()))
				{
					((Combobox)this.getFellow("accountNameComboBox")).getChildren().clear();
					((Combobox)this.getFellow("accountNameComboBox")).setValue(null);
					((CapsTextbox)this.getFellow("accountNoTextBox")).setValue(null);
					Messagebox.show("The issued account for the selected card is closed", "Create Trip", Messagebox.OK, Messagebox.ERROR);
					return;
				}
				accountNoTextBox.setText(selectedAccount.getCustNo());
				
				// Check if required to display project code
//				checkProjectCode(selectedAccount);
				
				//Display division or department according to account category
				if(selectedAccount.getAccountCategory().equals(NonConfigurableConstants.ACCOUNT_CATEGORY_CORPORATE)){
					List<AmtbAccount> divisions = this.businessHelper.getTxnBusiness().searchPremierAccounts(selectedAccount);
					if (divisions != null && !divisions.isEmpty())
					{
						((Listbox)this.getFellow("productType")).setDisabled(false);
						this.setDivisionInputVisible(divisions);
						this.setDepartmentInputInvisible();
					}
					this.setSubApplicantInvisible();
				}
				else if(selectedAccount.getAccountCategory().equals(NonConfigurableConstants.ACCOUNT_CATEGORY_APPLICANT))
				{
					List<AmtbAccount> subApplicants = this.businessHelper.getTxnBusiness().searchPremierAccounts(selectedAccount);
					if (subApplicants != null && !subApplicants.isEmpty())
					{
						((Listbox)this.getFellow("productType")).setDisabled(false);
						this.setSubApplicantVisible(subApplicants);
					}
					this.setDivisionInputInvisible();
					this.setDepartmentInputInvisible();					
				}
				else{
					this.setDivisionInputInvisible();
					this.setDepartmentInputInvisible();
				}
				checkProductType();
			}
			else{
				
				Listbox productTypeListBox = (Listbox)this.getFellow("productType");
				productTypeListBox.getChildren().clear();
				Listitem blankItem = new Listitem("-");
				blankItem.setValue(null);
				productTypeListBox.appendChild(blankItem);
				productTypeListBox.setSelectedIndex(0);
				
				((Listbox)this.getFellow("productType")).setDisabled(true);
				
				this.setDivisionInputInvisible();
				this.setDepartmentInputInvisible();
			}
		}
		catch(Exception e){
			Messagebox.show(com.cdgtaxi.ibs.common.NonConfigurableConstants.COMMON_ERROR_MESSAGE, 
					"Error", Messagebox.OK, Messagebox.ERROR);
			LoggerUtil.printStackTrace(logger, e);
		}
	}
	
	public void onSelectDivision() throws InterruptedException{
		logger.info("");
		
		try{
			Listbox divisionListBox = (Listbox)this.getFellow("divisionListBox");
			AmtbAccount selectedValue = (AmtbAccount) divisionListBox.getSelectedItem().getValue();
			
			if(selectedValue instanceof AmtbAccount){

				List<AmtbAccount> departments = this.businessHelper.getTxnBusiness().searchPremierAccounts((AmtbAccount) selectedValue);
				logger.info("Account No: " + selectedValue.getAccountNo());
				if (departments != null && !departments.isEmpty())
					this.setDepartmentInputVisible(departments);
			}
			else{
				this.setDepartmentInputInvisible();
				
				Listbox productTypeListBox = (Listbox)this.getFellow("productType");
				productTypeListBox.getChildren().clear();
				
				Listitem blankItem = new Listitem("-");
				blankItem.setValue(null);
				productTypeListBox.appendChild(blankItem);
				productTypeListBox.setSelectedIndex(0);
			}

			checkProductType();
		}
		catch(Exception e){
			Messagebox.show(com.cdgtaxi.ibs.common.NonConfigurableConstants.COMMON_ERROR_MESSAGE, 
					"Error", Messagebox.OK, Messagebox.ERROR);
			LoggerUtil.printStackTrace(logger, e);
		}
	}
	public void onSelectDept() throws InterruptedException{
		logger.info("");
		
		try{
			
			Listbox departmentListBox = (Listbox)this.getFellow("departmentListBox");
			AmtbAccount selectedValue = (AmtbAccount) departmentListBox.getSelectedItem().getValue();
			
			if(selectedValue instanceof AmtbAccount){
				
			}
			else {
				Listbox productTypeListBox = (Listbox)this.getFellow("productType");
				productTypeListBox.getChildren().clear();
				
				Listitem blankItem = new Listitem("-");
				blankItem.setValue(null);
				productTypeListBox.appendChild(blankItem);
				productTypeListBox.setSelectedIndex(0);
			}

			checkProductType();
		}
		catch(Exception e){
			Messagebox.show(com.cdgtaxi.ibs.common.NonConfigurableConstants.COMMON_ERROR_MESSAGE, 
					"Error", Messagebox.OK, Messagebox.ERROR);
			LoggerUtil.printStackTrace(logger, e);
		}
	}
	public void onSelectSubAppl() throws InterruptedException{
		logger.info("");
		
		try{
			
			Listbox subApplicantListBox = (Listbox)this.getFellow("subApplicant");
			AmtbAccount selectedValue = (AmtbAccount) subApplicantListBox.getSelectedItem().getValue();
			
			if(selectedValue instanceof AmtbAccount) {
				checkProductType();
			}
			else {
				Listbox productTypeListBox = (Listbox)this.getFellow("productType");
				productTypeListBox.getChildren().clear();
				
				Listitem blankItem = new Listitem("-");
				blankItem.setValue(null);
				productTypeListBox.appendChild(blankItem);
				productTypeListBox.setSelectedIndex(0);
			}
		}
		catch(Exception e){
			Messagebox.show(com.cdgtaxi.ibs.common.NonConfigurableConstants.COMMON_ERROR_MESSAGE, 
					"Error", Messagebox.OK, Messagebox.ERROR);
			LoggerUtil.printStackTrace(logger, e);
		}
	}
	
	public void onSelectUpdateFMS() throws InterruptedException{
		logger.info("onSelectUpdateFMS");
		
		try{
			Listbox toUpdateFMSListBox = (Listbox)this.getFellow("toUpdateFMSList");
			String selectedValue = (String) toUpdateFMSListBox.getSelectedItem().getValue();
			
			if (NonConfigurableConstants.BOOLEAN_YES.equals(selectedValue))
			{
				// enable the fields
				((Row)this.getFellow("FMSRow")).setVisible(true);
				((Row)this.getFellow("FMSRow2")).setVisible(true);
				((Row)this.getFellow("FMSRow3")).setVisible(true);
				((Label)this.getFellow("levyLabel")).setVisible(true);
				((Decimalbox)this.getFellow("levy")).setVisible(true);
				((Decimalbox)this.getFellow("FMSAmount")).setConstraint(new com.cdgtaxi.ibs.web.constraint.RequiredZeroOrGreaterConstraint());
			}
			else
			{
				// disable the fields
				((Row)this.getFellow("FMSRow")).setVisible(false);
				((Row)this.getFellow("FMSRow2")).setVisible(false);
				((Row)this.getFellow("FMSRow3")).setVisible(false);
				((Label)this.getFellow("levyLabel")).setVisible(false);
				((Decimalbox)this.getFellow("levy")).setVisible(false);
				((Decimalbox)this.getFellow("FMSAmount")).setConstraint("");
			}
		}
		catch(Exception e){
			Messagebox.show(com.cdgtaxi.ibs.common.NonConfigurableConstants.COMMON_ERROR_MESSAGE, 
					"Error", Messagebox.OK, Messagebox.ERROR);
			LoggerUtil.printStackTrace(logger, e);
		}
	}
	
	
	
/*	public void onSelectDepartment() throws InterruptedException{
		logger.info("");
		
		try{
			Listbox departmentListBox = (Listbox)this.getFellow("departmentListBox");
			Object selectedValue = departmentListBox.getSelectedItem().getValue();
			
			if(selectedValue instanceof AmtbAccount){
				// populate the card no fields
				
			}
		}
		catch(Exception e){
			Messagebox.show(com.cdgtaxi.ibs.common.NonConfigurableConstants.COMMON_ERROR_MESSAGE, 
					"Error", Messagebox.OK, Messagebox.ERROR);
			e.printStackTrace();
		}
	}*/
	
	private void setDivisionInputVisible(List<AmtbAccount> divisions){
		((Row)this.getFellow("divisionDepartmentRow")).setVisible(true);
		((Label)this.getFellow("divisionLabel")).setVisible(true);
		Listbox divisionListBox = (Listbox)this.getFellow("divisionListBox");
		divisionListBox.setVisible(true);
		divisionListBox.getChildren().clear();
		
		Listitem blankItem = new Listitem("-");
		blankItem.setValue(null);
		divisionListBox.appendChild(blankItem);
		for(AmtbAccount division : divisions){
			Listitem newItem = new Listitem(division.getAccountName()+ " (" + division.getCode() + ")");
			newItem.setValue(division);
			divisionListBox.appendChild(newItem);
		}
	}
	
	private void setDepartmentInputVisible(List<AmtbAccount> departments){
		((Row)this.getFellow("departmentRow")).setVisible(true);
		((Label)this.getFellow("departmentLabel")).setVisible(true);
		Listbox departmentListBox = (Listbox)this.getFellow("departmentListBox");
		departmentListBox.setVisible(true);
		departmentListBox.getChildren().clear();
		
		Listitem blankItem = new Listitem("-");
		blankItem.setValue(null);
		departmentListBox.appendChild(blankItem);
		for(AmtbAccount department : departments){
			Listitem newItem = new Listitem(department.getAccountName()+ " (" + department.getCode() + ")");
			newItem.setValue(department);
			departmentListBox.appendChild(newItem);
		}
	}
	
	private void setDivisionInputInvisible()
	{
		((Row)this.getFellow("divisionDepartmentRow")).setVisible(false);
		((Label)this.getFellow("divisionLabel")).setVisible(false);
		Listbox divisionListBox = (Listbox)this.getFellow("divisionListBox");
		divisionListBox.setVisible(false);
		divisionListBox.getChildren().clear();
	}
	
	private void setDepartmentInputInvisible()
	{
		((Row)this.getFellow("departmentRow")).setVisible(false);
		((Label)this.getFellow("departmentLabel")).setVisible(false);
		Listbox departmentListBox = (Listbox)this.getFellow("departmentListBox");
		departmentListBox.setVisible(false);
		departmentListBox.getChildren().clear();
	}
	
	private void setSubApplicantInvisible()
	{
		((Row)this.getFellow("subApplicantRow")).setVisible(false);
		((Label)this.getFellow("subApplicantLabel")).setVisible(false);
		Listbox subApplicantListBox = (Listbox)this.getFellow("subApplicant");
		subApplicantListBox.setVisible(false);
		subApplicantListBox.getChildren().clear();
	}
	
	private void setSubApplicantVisible(List<AmtbAccount> subApplicants){
		((Row)this.getFellow("subApplicantRow")).setVisible(true);
		((Label)this.getFellow("subApplicantLabel")).setVisible(true);
		Listbox subApplicantListbox = (Listbox)this.getFellow("subApplicant");
		subApplicantListbox.setVisible(true);
		subApplicantListbox.getChildren().clear();
		
		Listitem blankItem = new Listitem("-");
		blankItem.setValue(null);
		subApplicantListbox.appendChild(blankItem);
		for(AmtbAccount subApplicant : subApplicants){
			Listitem newItem = new Listitem(subApplicant.getAccountName());
			newItem.setValue(subApplicant);
			subApplicantListbox.appendChild(newItem);
		}	
	}
	
//	private void setProjectCodeVisible(){
//		((Row)this.getFellow("projCodeRow")).setVisible(true);
//		CapsTextbox projCode = (CapsTextbox) this.getFellow("projCode");
//		projCode.setValue(null);
//		
//		((Row)this.getFellow("projCodeReasonRow")).setVisible(true);
//		CapsTextbox projCodeReason = (CapsTextbox) this.getFellow("projCodeReason");
//		projCodeReason.setValue(null);
//	}
//	
//	private void setProjectCodeInvisible(){
//		((Row)this.getFellow("projCodeRow")).setVisible(false);
//		CapsTextbox projCode = (CapsTextbox) this.getFellow("projCode");
//		projCode.setValue(null);
//		
//		((Row)this.getFellow("projCodeReasonRow")).setVisible(false);
//		CapsTextbox projCodeReason = (CapsTextbox) this.getFellow("projCodeReason");
//		projCodeReason.setValue(null);
//	}
//	
//	private void checkProjectCode(AmtbAccount acct)
//	{
//		if (acct.getAmtbCorporateDetails() != null)
//		{
//			if (acct.getAmtbCorporateDetails().iterator().hasNext())
//			{
//				AmtbCorporateDetail amtbCorporateDetail = acct.getAmtbCorporateDetails().iterator().next();
//				if (NonConfigurableConstants.BOOLEAN_YES.equals(amtbCorporateDetail.getProjectCode()))
//				{
//					setProjectCodeVisible();
//				}
//				else
//					setProjectCodeInvisible();
//			}
//			else
//				setProjectCodeInvisible();
//		}
//		else
//			setProjectCodeInvisible();
//	}
	public void checkProductType() throws InterruptedException {
		
		String custNo = ((CapsTextbox)this.getFellow("accountNoTextBox")).getValue();
		String acctNo = "";

		Listbox subApplicantListBox = (Listbox)this.getFellow("subApplicant");
		Listbox departmentListBox = (Listbox)this.getFellow("departmentListBox");
		Listbox divisionListBox = (Listbox)this.getFellow("divisionListBox");
		
		if(subApplicantListBox.getSelectedItem() != null)
		{
			if(subApplicantListBox.getSelectedItem().getValue() != null)
				acctNo = ((AmtbAccount) departmentListBox.getSelectedItem().getValue()).getAccountNo().toString();
		}
		else if(departmentListBox.getSelectedItem() != null)
		{
			if(departmentListBox.getSelectedItem().getValue() != null)
				acctNo = ((AmtbAccount) departmentListBox.getSelectedItem().getValue()).getAccountNo().toString();
		}
		else if(divisionListBox.getSelectedItem() != null)
		{
			if(divisionListBox.getSelectedItem().getValue() != null)
				acctNo = ((AmtbAccount) divisionListBox.getSelectedItem().getValue()).getAccountNo().toString();
		}
		List<PmtbProductType> productTypeList = this.businessHelper.getTxnBusiness().searchPremierAccountsProductTypes(custNo, acctNo);


		Listbox productTypeListBox = (Listbox)this.getFellow("productType");
		productTypeListBox.getChildren().clear();
		
		Listitem blankItem = new Listitem("-");
		blankItem.setValue(null);
		productTypeListBox.appendChild(blankItem);
		productTypeListBox.setSelectedIndex(0);
		
		Map<String, String> returnMap = new HashMap<String, String>();
		for (PmtbProductType productType : productTypeList) {
				returnMap.put(productType.getProductTypeId(), productType.getName());
				Listitem newItem = new Listitem(productType.getName());
				newItem.setValue(productType.getProductTypeId());
				productTypeListBox.appendChild(newItem);
		}
		
	}
	public void searchAccount() throws InterruptedException{
		logger.info("");

		//Retrieve entered input
		Combobox accountNameComboBox = (Combobox)this.getFellow("accountNameComboBox");
		String customerNo = ((CapsTextbox)this.getFellow("accountNoTextBox")).getValue();
		
		//accountName still the same as selected one, skip
		if(accountNameComboBox.getSelectedItem()!=null){
			AmtbAccount selectedAccount = (AmtbAccount)accountNameComboBox.getSelectedItem().getValue();
			if(customerNo.equals(selectedAccount.getCustNo())) return;
		}
		
		accountNameComboBox.setText("");
		accountNameComboBox.getChildren().clear();
		this.onSelectAccountName();
		
		if (customerNo == null || "".equals(customerNo) || (customerNo.length()<3))
		{
			Listbox productTypeListBox = (Listbox)this.getFellow("productType");
			productTypeListBox.getChildren().clear();
			Listitem blankItem = new Listitem("-");
			blankItem.setValue(null);
			productTypeListBox.appendChild(blankItem);
			productTypeListBox.setSelectedIndex(0);
			
			((Listbox)this.getFellow("productType")).setDisabled(true);
			return;
		}

		try{
	
			// Name is not required
			List<AmtbAccount> accounts = this.businessHelper.getTxnBusiness().searchPremierAccounts(customerNo, null);
			if (accounts != null && !accounts.isEmpty())
			{
				((Listbox)this.getFellow("productType")).setDisabled(false);
				
				for(AmtbAccount account : accounts)
				{
					logger.info("Account inserted: " + account.getAccountName());
					Comboitem item = new Comboitem(account.getAccountName()+" ("+account.getCustNo()+")");
					item.setValue(account);
					accountNameComboBox.appendChild(item);
				}
				if(accounts.size()==1) {
					accountNameComboBox.setSelectedIndex(0);
					this.onSelectAccountName();
				}
			}
			else
			{
				//Clear list for every new search
				Listbox productTypeListBox = (Listbox)this.getFellow("productType");
				productTypeListBox.getChildren().clear();
				Listitem blankItem = new Listitem("-");
				blankItem.setValue(null);
				productTypeListBox.appendChild(blankItem);
				productTypeListBox.setSelectedIndex(0);
				
				((Listbox)this.getFellow("productType")).setDisabled(true);
				accountNameComboBox.getChildren().clear();
				((CapsTextbox)this.getFellow("accountNoTextBox")).setValue(null);
				// Set focus back to accountText
				((CapsTextbox)this.getFellow("accountNoTextBox")).setFocus(true);
				Messagebox.show("There is no such account in the system", 
						"Information", Messagebox.OK, Messagebox.INFORMATION);
			}
		}
		catch(Exception e){
			Messagebox.show(com.cdgtaxi.ibs.common.NonConfigurableConstants.COMMON_ERROR_MESSAGE, 
					"Error", Messagebox.OK, Messagebox.ERROR);
			LoggerUtil.printStackTrace(logger, e);
		}
	}
	
	public void searchAccountName() throws InterruptedException{
		logger.info("");

		//Retrieve entered input
		Combobox accountNameComboBox = (Combobox)this.getFellow("accountNameComboBox");
		String name = accountNameComboBox.getValue();
		
		if (name == null || "".equals(name) || (name.length()<3))
		{
			return;
		}
		
		if(accountNameComboBox.getSelectedItem()!=null){
			AmtbAccount selectedAccount = (AmtbAccount)accountNameComboBox.getSelectedItem().getValue();
			if(name.equals(selectedAccount.getAccountName()+" ("+selectedAccount.getCustNo()+")"))
				return;
		}
		
		((CapsTextbox)this.getFellow("accountNoTextBox")).setText("");
		accountNameComboBox.setText("");
		accountNameComboBox.getChildren().clear();
		this.onSelectAccountName();

		try{
				
			// Name is not required
			List<AmtbAccount> accounts = this.businessHelper.getTxnBusiness().searchPremierAccounts(null, name);
			if (accounts != null && !accounts.isEmpty())
			{
				for(AmtbAccount account : accounts)
				{
					logger.info("Account inserted: " + account.getAccountName());
					Comboitem item = new Comboitem(account.getAccountName()+" ("+account.getCustNo()+")");
					item.setValue(account);
					accountNameComboBox.appendChild(item);
				}
				if(accounts.size()==1) {
					accountNameComboBox.setSelectedIndex(0);
					this.onSelectAccountName();
				}
			}
			else
			{
				//Clear list for every new search
				accountNameComboBox.getChildren().clear();
				((CapsTextbox)this.getFellow("accountNoTextBox")).setValue(null);
				accountNameComboBox.setFocus(true);
				Messagebox.show("There is no such account in the system", 
						"Information", Messagebox.OK, Messagebox.INFORMATION);
			}
		}
		catch(Exception e){
			Messagebox.show(com.cdgtaxi.ibs.common.NonConfigurableConstants.COMMON_ERROR_MESSAGE, 
					"Error", Messagebox.OK, Messagebox.ERROR);
			LoggerUtil.printStackTrace(logger, e);
		}
	}
	
	public void searchAccountName(String name) throws InterruptedException{
		logger.info("");

		//Retrieve entered input
		Combobox accountNameComboBox = (Combobox)this.getFellow("accountNameComboBox");
		
		if (name == null || "".equals(name) || (name.length()<3))
		{
			return;
		}
		
		if(accountNameComboBox.getSelectedItem()!=null){
			AmtbAccount selectedAccount = (AmtbAccount)accountNameComboBox.getSelectedItem().getValue();
			if(name.equals(selectedAccount.getAccountName()+" ("+selectedAccount.getCustNo()+")"))
				return;
		}
		
		((CapsTextbox)this.getFellow("accountNoTextBox")).setText("");
		accountNameComboBox.setText("");
		accountNameComboBox.getChildren().clear();
		this.onSelectAccountName();

		try{
				
			// Name is not required
			List<AmtbAccount> accounts = this.businessHelper.getTxnBusiness().searchPremierAccounts(null, name);
			if (accounts != null && !accounts.isEmpty())
			{
				for(AmtbAccount account : accounts)
				{
					logger.info("Account inserted: " + account.getAccountName());
					Comboitem item = new Comboitem(account.getAccountName()+" ("+account.getCustNo()+")");
					item.setValue(account);
					accountNameComboBox.appendChild(item);
				}
				if(accounts.size()==1) {
					accountNameComboBox.setSelectedIndex(0);
					this.onSelectAccountName();
				}
				else
					accountNameComboBox.open();
			}
		}
		catch(Exception e){
			Messagebox.show(com.cdgtaxi.ibs.common.NonConfigurableConstants.COMMON_ERROR_MESSAGE, 
					"Error", Messagebox.OK, Messagebox.ERROR);
			LoggerUtil.printStackTrace(logger, e);
		}
	}
	
	private Date combineDateHrMinSec(Date startDt, int hr, int min, int sec)
	{
		Calendar calendar = Calendar.getInstance();
		if (startDt != null)
		{
			calendar.setTime(startDt);
		}
		else
		{
			return null;
		}
		
		calendar.add(Calendar.HOUR_OF_DAY, hr);
		calendar.add(Calendar.MINUTE, min);
		calendar.add(Calendar.SECOND, sec);
		return calendar.getTime();
	}
	
	public void save() throws InterruptedException{
		logger.info("save");
		displayProcessing();
		// getting the account template
		// Generated from sequence number
		try{
			Map<String, String> txnDetails = getTxnDetails();
			// Validation rules
			Listbox toUpdateFMSListBox = (Listbox)this.getFellow("toUpdateFMSList");
			if (toUpdateFMSListBox.getSelectedItem() != null)
			{
				String selectedValue = (String) toUpdateFMSListBox.getSelectedItem().getValue();
				
				if (NonConfigurableConstants.BOOLEAN_YES.equals(selectedValue))
				{
					Listbox updateFMSListbox = (Listbox) this.getFellow("updateFMSList");
					if (updateFMSListbox.getSelectedItem() != null)
					{
						if ("".equals(updateFMSListbox.getSelectedItem().getValue()))
						{
							Messagebox.show("Collect/Refund is a mandatory field", "Create Cardless Trip", Messagebox.OK, Messagebox.ERROR);
							return;
						}
					}
					else
					{
						Messagebox.show("Collect/Refund is a mandatory field", "Create Cardless Trip", Messagebox.OK, Messagebox.ERROR);
						return;
					}
					String msg = this.businessHelper.getTxnBusiness().verifyFMSDriverVehicleAssoc(txnDetails.get("taxiNo"), txnDetails.get("nric"), DateUtil.convertStrToTimestamp(txnDetails.get("startDate"), DateUtil.FMS_TRIPS_DATE_FORMAT), DateUtil.convertStrToTimestamp(txnDetails.get("endDate"), DateUtil.FMS_TRIPS_DATE_FORMAT));
					if (!NonConfigurableConstants.BOOLEAN_YES.equalsIgnoreCase(msg) && !NonConfigurableConstants.IGNORE_FLAG.equalsIgnoreCase(msg))
					{
						Messagebox.show("Interface Error to FMS - " + msg, "Create Cardless Trip", Messagebox.OK, Messagebox.ERROR);
						return;
					}
				}
			}
			else
			{
				Messagebox.show("Update FMS is a mandatory field", "Create Cardless Trip", Messagebox.OK, Messagebox.ERROR);
				return;
			}
			Listbox productTypeListbox = (Listbox) this.getFellow("productType");
			if (productTypeListbox.getSelectedItem() != null)
			{

				if (productTypeListbox.getSelectedItem().getValue() == null || "".equals(productTypeListbox.getSelectedItem().getValue()))
				{
					Messagebox.show("Product Type is a mandatory field", "Create Cardless Trip", Messagebox.OK, Messagebox.ERROR);
					return;
				}
			}
			else
			{
				Messagebox.show("Product Type is a mandatory field", "Create Cardless Trip", Messagebox.OK, Messagebox.ERROR);
				return;
			}
			
			Listbox companyCdListbox = (Listbox) this.getFellow("companyCd");
			if (companyCdListbox.getSelectedItem() != null)
			{
				if ("".equals(companyCdListbox.getSelectedItem().getValue()))
				{
					Messagebox.show("Company Code is a mandatory field", "Create Cardless Trip", Messagebox.OK, Messagebox.ERROR);
					return;
				}
			}
			else
			{
				Messagebox.show("Company Code is a mandatory field", "Create Cardless Trip", Messagebox.OK, Messagebox.ERROR);
				return;
			}
			
			Listbox tripType = (Listbox) this.getFellow("tripType");
			if (tripType.getSelectedItem() != null)
			{
				if ("".equals(tripType.getSelectedItem().getValue()))
				{
					Messagebox.show("Trip Type is a mandatory field", "Create Cardless Trip", Messagebox.OK, Messagebox.ERROR);
					return;
				}
			}
			else
			{
				Messagebox.show("Trip Type is a mandatory field", "Create Cardless Trip", Messagebox.OK, Messagebox.ERROR);
				return;
			}
			
			Listbox vehicleGroup = (Listbox) this.getFellow("vehicleGroup");
			if (vehicleGroup.getSelectedItem() != null)
			{
				if ("".equals(vehicleGroup.getSelectedItem().getValue()))
				{
					Messagebox.show("Vehicle Type is a mandatory field", "Create Cardless Trip", Messagebox.OK, Messagebox.ERROR);
					return;
				}
			}
			else
			{
				Messagebox.show("Vehicle Type is a mandatory field", "Create Cardless Trip", Messagebox.OK, Messagebox.ERROR);
				return;
			}
			
			Listbox jobType = (Listbox) this.getFellow("jobType");
			if (jobType.getSelectedItem() != null)
			{
				if ("".equals(jobType.getSelectedItem().getValue()))
				{
					Messagebox.show("Job Type is a mandatory field", "Create Cardless Trip", Messagebox.OK, Messagebox.ERROR);
					return;
				}
			}
			else
			{
				Messagebox.show("Job Type is a mandatory field", "Create Cardless Trip", Messagebox.OK, Messagebox.ERROR);
				return;
			}
			
			Listbox vehicleType = (Listbox) this.getFellow("vehicleType");
			if (vehicleType.getSelectedItem() != null)
			{
				if ("".equals(vehicleType.getSelectedItem().getValue()))
				{
					Messagebox.show("Vehicle Type is a mandatory field", "Create Cardless Trip", Messagebox.OK, Messagebox.ERROR);
					return;
				}
			}
			else
			{
				Messagebox.show("Vehicle Type is a mandatory field", "Create Cardless Trip", Messagebox.OK, Messagebox.ERROR);
				return;
			}
			
			// Validation - Closed account cannot create transaction
			if (this.businessHelper.getTxnBusiness().isAccountClosed(txnDetails.get("acctNo")))
			{
				Messagebox.show("Unable to create new Trips as the account is closed", "Create Cardless Trip", Messagebox.OK, Messagebox.ERROR);
				return;
			}
			
			// Validation - Start Date cannot be later than end date
			Timestamp tripStartTimeStamp = DateUtil.convertStrToTimestamp(txnDetails.get("startDate"), DateUtil.TRIPS_DATE_FORMAT);
			Timestamp tripEndTimeStamp = DateUtil.convertStrToTimestamp(txnDetails.get("endDate"), DateUtil.TRIPS_DATE_FORMAT);
			
			if (tripStartTimeStamp.after(tripEndTimeStamp))
			{
				Messagebox.show("Trip Start Date cannot be earlier than Trip End Date", "Create Cardless Trip", Messagebox.OK, Messagebox.ERROR);
				return;
			}
			

			
			
			String jobNo = this.businessHelper.getTxnBusiness().createPremierTxn(txnDetails, getUserLoginIdAndDomain());
			if(jobNo != null)
			{
				Messagebox.show("New Cardless trip created with \nJob No: " + jobNo , "Create Cardless Trip",  Messagebox.OK, Messagebox.INFORMATION);
				
			}else{
				Messagebox.show("Unable to save trip. Please try again later", "Create Cardless Trip", Messagebox.OK, Messagebox.ERROR);
			}
			logger.info("saved successfully");
			Executions.getCurrent().sendRedirect(""); //TO DO A REFRESH
		}
		catch (WrongValueException wve)
		{
			throw wve;
		}
		catch (Exception e)
		{
			logger.info("exception in saving");
			Messagebox.show(com.cdgtaxi.ibs.common.NonConfigurableConstants.COMMON_ERROR_MESSAGE, 
					"Error", Messagebox.OK, Messagebox.ERROR);
			LoggerUtil.printStackTrace(logger, e);
			Executions.getCurrent().sendRedirect(""); //TO DO A REFRESH
		}
	}
	
	protected Map<String, String> getTxnDetails(){
		Map<String, String> txnDetails = new HashMap<String, String>();
		
		//contactDetails.put("userId", this.getUserLoginId());
		
		//selected account
		Combobox accountNameComboBox = (Combobox)this.getFellow("accountNameComboBox");
		if(accountNameComboBox.getSelectedItem()==null)
			throw new WrongValueException(accountNameComboBox, "* Mandatory field");
		//txnDetails.put("acctNo", ((AmtbAccount) accountNameComboBox.getSelectedItem().getValue()).getAccountNo().toString());
		txnDetails.put("acctNo", ((CapsTextbox)this.getFellow("accountNoTextBox")).getValue());
		txnDetails.put("name", ((AmtbAccount) accountNameComboBox.getSelectedItem().getValue()).getAccountName());
		//division
		Listbox divisionListBox = (Listbox)this.getFellow("divisionListBox");
		if(divisionListBox.getSelectedItem()!=null){
			if((divisionListBox.getSelectedItem().getValue()!= null))
				txnDetails.put("division", ((AmtbAccount) divisionListBox.getSelectedItem().getValue()).getAccountNo().toString());
		}
		//department
		Listbox departmentListBox = (Listbox)this.getFellow("departmentListBox");
		if(departmentListBox.getSelectedItem()!=null){
			if((departmentListBox.getSelectedItem().getValue()!= null))
				txnDetails.put("department", ((AmtbAccount) departmentListBox.getSelectedItem().getValue()).getAccountNo().toString());
		}
		
		Listbox subApplicantListBox = (Listbox)this.getFellow("subApplicant");
		if(subApplicantListBox.getSelectedItem()!=null){
			if((subApplicantListBox.getSelectedItem().getValue() != null))
				txnDetails.put("subApplicant", ((AmtbAccount) subApplicantListBox.getSelectedItem().getValue()).getAccountNo().toString());
		}
		
		Listbox productType = (Listbox)this.getFellow("productType");
		if (productType.getSelectedItem()!= null && productType.getSelectedItem().getValue()!=null)
		{
			txnDetails.put("productType", (String) productType.getSelectedItem().getValue());
		}
		Listbox vehicleType = (Listbox) this.getFellow("vehicleType");
		if (vehicleType.getSelectedItem()!= null && vehicleType.getSelectedItem().getValue() !=null)
		{
			txnDetails.put("vehicleType", (String) vehicleType.getSelectedItem().getValue());
		}
		
		Listbox jobType = (Listbox) this.getFellow("jobType");
		if (jobType.getSelectedItem()!= null && jobType.getSelectedItem().getValue() !=null)
		{
			txnDetails.put("jobType", (String) jobType.getSelectedItem().getValue());
		}
		
		
		txnDetails.put("taxiNo", ((CapsTextbox)this.getFellow("taxiNo")).getValue());
		txnDetails.put("nric", ((CapsTextbox)this.getFellow("nric")).getValue());
		
		Datebox startDateBox = (Datebox) this.getFellow("startDate");
		Listbox startTimeHr = (Listbox) this.getFellow("startTimeHrDDL");
		Listbox startTimeMin = (Listbox) this.getFellow("startTimeMinDDL");
		Listbox startTimeSec = (Listbox) this.getFellow("startTimeSecDDL");
		Timestamp startDate = null;
		if (startDateBox != null && startDateBox.getValue() != null)
		{
			Date startDateWithTime = combineDateHrMinSec(startDateBox.getValue(), Integer.parseInt(startTimeHr.getSelectedItem().getLabel()),
					Integer.parseInt(startTimeMin.getSelectedItem().getLabel()),Integer.parseInt(startTimeSec.getSelectedItem().getLabel()));
			startDate = DateUtil.convertDateToTimestamp(startDateWithTime);
		}
		
		txnDetails.put("startDate", DateUtil.convertTimestampToStr(startDate, DateUtil.TRIPS_DATE_FORMAT));
		
		Datebox endDateBox = (Datebox) this.getFellow("endDate");
		Listbox endTimeHr = (Listbox) this.getFellow("endTimeHrDDL");
		Listbox endTimeMin = (Listbox) this.getFellow("endTimeMinDDL");
		Listbox endTimeSec = (Listbox) this.getFellow("endTimeSecDDL");
		Timestamp endDate = null;
		if (endDateBox != null && endDateBox.getValue() != null)
		{
			Date endDateWithTime = combineDateHrMinSec(endDateBox.getValue(), Integer.parseInt(endTimeHr.getSelectedItem().getLabel()),
					Integer.parseInt(endTimeMin.getSelectedItem().getLabel()),Integer.parseInt(endTimeSec.getSelectedItem().getLabel()));
			endDate = DateUtil.convertDateToTimestamp(endDateWithTime);
		}

		txnDetails.put("endDate", DateUtil.convertTimestampToStr(endDate, DateUtil.TRIPS_DATE_FORMAT));

		// Add company Code
		Listbox companyCd = (Listbox) this.getFellow("companyCd");
		if (companyCd.getSelectedItem()!= null && companyCd.getSelectedItem().getValue() !=null)
		{
			txnDetails.put("companyCd", (String) companyCd.getSelectedItem().getValue());
		}
		//txnDetails.put("companyCd", ((CapsTextbox)this.getFellow("cardNo")).getValue());
		txnDetails.put("fareAmt", ((Decimalbox)this.getFellow("fareAmt")).getValue().toString());
		txnDetails.put("pickup", ((CapsTextbox)this.getFellow("pickup")).getValue());
		txnDetails.put("destination", ((CapsTextbox)this.getFellow("destination")).getValue());
		txnDetails.put("remarks", ((CapsTextbox)this.getFellow("remarks")).getValue());
		txnDetails.put("projCode", ((CapsTextbox)this.getFellow("projCode")).getValue());
		txnDetails.put("projCodeReason", ((CapsTextbox)this.getFellow("projCodeReason")).getValue());
		
		// checkbox for complimentary
		Checkbox checkbox = (Checkbox)this.getFellow("complimentary");
		if (checkbox.isChecked())
			txnDetails.put("complimentary", NonConfigurableConstants.BOOLEAN_YES);
		else
			txnDetails.put("complimentary", NonConfigurableConstants.BOOLEAN_NO);
		
		// Premier txn additional fields
		txnDetails.put("surchargeDesc", ((CapsTextbox)this.getFellow("surchargeDesc")).getValue());
		txnDetails.put("bookedBy", ((CapsTextbox)this.getFellow("bookedBy")).getValue());
		txnDetails.put("bookedRef", ((CapsTextbox)this.getFellow("bookedRef")).getValue());
		txnDetails.put("flightInfo", ((CapsTextbox)this.getFellow("flightInfo")).getValue());
		
		Datebox bookedDateBox = (Datebox) this.getFellow("bookedDate");
		if (bookedDateBox.getValue() != null && !"".equals(bookedDateBox.getValue()))
			txnDetails.put("bookedDate", DateUtil.convertDateToStr(bookedDateBox.getValue(), DateUtil.TRIPS_DATE_FORMAT));
		
		// Trip Type
		Listbox tripType = (Listbox) this.getFellow("tripType");
		if (tripType.getSelectedItem()!= null && tripType.getSelectedItem().getValue() !=null)
		{
			txnDetails.put("tripType", (String) tripType.getSelectedItem().getValue());
		}
		
		// Vehicle Group
		Listbox vehicleGroup = (Listbox) this.getFellow("vehicleGroup");
		if (vehicleGroup.getSelectedItem()!= null && vehicleGroup.getSelectedItem().getValue() !=null)
		{
			txnDetails.put("vehicleGroup", (String) vehicleGroup.getSelectedItem().getValue());
		}
		
		txnDetails.put("paxName", ((CapsTextbox)this.getFellow("paxName")).getValue());
		
		Listitem toUpdateFMSListitem = ((Listbox)this.getFellow("toUpdateFMSList")).getSelectedItem();
		String toUpdateFMS = null;
		if (toUpdateFMSListitem != null) {
			toUpdateFMS = (String)toUpdateFMSListitem.getValue();
			txnDetails.put("toUpdateFMSList",  toUpdateFMS);
		}
		
		if(NonConfigurableConstants.BOOLEAN_YES.equals(toUpdateFMS)) {
			Listitem updateFMSListitem = ((Listbox)this.getFellow("updateFMSList")).getSelectedItem();
			if (updateFMSListitem != null)
				txnDetails.put("updateFMSList", (String)(updateFMSListitem.getValue()));
			if (((Decimalbox)this.getFellow("FMSAmount")).getValue() != null)
				txnDetails.put("FMSAmount", ((Decimalbox)this.getFellow("FMSAmount")).getValue().toString());
			if (((Decimalbox)this.getFellow("incentiveAmt")).getValue() != null)
				txnDetails.put("incentiveAmt", ((Decimalbox)this.getFellow("incentiveAmt")).getValue().toString());
			if (((Decimalbox)this.getFellow("promoAmt")).getValue() != null)
				txnDetails.put("promoAmt", ((Decimalbox)this.getFellow("promoAmt")).getValue().toString());
			if (((Decimalbox)this.getFellow("cabRewardsAmt")).getValue() != null)
				txnDetails.put("cabRewardsAmt", ((Decimalbox)this.getFellow("cabRewardsAmt")).getValue().toString());
			if (((Decimalbox)this.getFellow("levy")).getValue()!= null)
				txnDetails.put("levy", ((Decimalbox)this.getFellow("levy")).getValue().toString());
		}
		
		txnDetails.put("user", getUserLoginIdAndDomain());
		return txnDetails;
	}
	
	public void reset() throws InterruptedException{
		logger.info("reset");
		this.removeConstraints();
		Datebox endDateBox = (Datebox) this.getFellow("endDate");
		endDateBox.setValue(null);
		Listbox endTimeHr = (Listbox) this.getFellow("endTimeHrDDL");
		endTimeHr.setSelectedIndex(0);
		Listbox endTimeMin = (Listbox) this.getFellow("endTimeMinDDL");
		endTimeMin.setSelectedIndex(0);

		Listbox endTimeSec = (Listbox) this.getFellow("endTimeSecDDL");
		endTimeSec.setSelectedIndex(0);
		Datebox startDateBox = (Datebox) this.getFellow("startDate");
		startDateBox.setValue(null);
		Listbox startTimeHr = (Listbox) this.getFellow("startTimeHrDDL");
		startTimeHr.setSelectedIndex(0);
		Listbox startTimeMin = (Listbox) this.getFellow("startTimeMinDDL");
		startTimeMin.setSelectedIndex(0);
		Listbox startTimeSec = (Listbox) this.getFellow("startTimeSecDDL");
		startTimeSec.setSelectedIndex(0);
		
		//Listbox productTypeListbox = (Listbox) this.getFellow("productType");
		//productTypeListbox.setSelectedIndex(0);
		((CapsTextbox)this.getFellow("remarks")).setValue(null);
		((CapsTextbox)this.getFellow("pickup")).setValue(null);
		((CapsTextbox)this.getFellow("destination")).setValue(null);
		((CapsTextbox)this.getFellow("surchargeDesc")).setValue(null);
		((CapsTextbox)this.getFellow("paxName")).setValue(null);
		((CapsTextbox)this.getFellow("bookedBy")).setValue(null);
		((CapsTextbox)this.getFellow("bookedRef")).setValue(null);
		((CapsTextbox)this.getFellow("flightInfo")).setValue(null);
		((Checkbox)this.getFellow("complimentary")).setChecked(false);
		((Datebox)this.getFellow("bookedDate")).setValue(null);
		((Decimalbox)this.getFellow("FMSAmount")).setValue(null);
		((Decimalbox)this.getFellow("incentiveAmt")).setValue(null);
		((Decimalbox)this.getFellow("promoAmt")).setValue(null);
		((Decimalbox)this.getFellow("cabRewardsAmt")).setValue(null);
		((Decimalbox)this.getFellow("fareAmt")).setValue(null);
		((Decimalbox)this.getFellow("levy")).setValue(null);
		((Listbox)this.getFellow("companyCd")).setSelectedIndex(0);
		((Listbox)this.getFellow("vehicleType")).setSelectedIndex(0);
		((Listbox)this.getFellow("jobType")).setSelectedIndex(0);
		((Listbox)this.getFellow("updateFMSList")).setSelectedIndex(0);
		((Listbox)this.getFellow("vehicleGroup")).setSelectedIndex(0);
		((Listbox)this.getFellow("tripType")).setSelectedIndex(0);
		((CapsTextbox)this.getFellow("taxiNo")).setValue(null);
		((CapsTextbox)this.getFellow("nric")).setValue(null);
		((Listbox)this.getFellow("toUpdateFMSList")).setSelectedIndex(0);
		((CapsTextbox)this.getFellow("bookedRef")).setValue(null);
		((CapsTextbox)this.getFellow("flightInfo")).setValue(null);
		((Checkbox)this.getFellow("complimentary")).setChecked(false);
		// disable the fields
		((Row)this.getFellow("FMSRow")).setVisible(false);
		((Row)this.getFellow("FMSRow2")).setVisible(false);
		((Label)this.getFellow("levyLabel")).setVisible(false);
		((Decimalbox)this.getFellow("levy")).setVisible(false);
		((Row)this.getFellow("projCodeRow")).setVisible(false);
		((CapsTextbox)this.getFellow("projCode")).setValue(null);
		((Row)this.getFellow("projCodeReasonRow")).setVisible(false);
		((CapsTextbox)this.getFellow("projCodeReason")).setValue(null);
		Combobox accountNameComboBox = (Combobox)this.getFellow("accountNameComboBox");
		accountNameComboBox.getChildren().clear();
		accountNameComboBox.setText("");
		CapsTextbox accountNoTextBox = (CapsTextbox)this.getFellow("accountNoTextBox");
		accountNoTextBox.setValue(null);
		accountNoTextBox.focus();
		this.setDepartmentInputInvisible();
		this.setDivisionInputInvisible();
		this.setSubApplicantInvisible();
		this.addConstraints();
		
	}
	
	private void removeConstraints() throws InterruptedException{
		((Datebox) this.getFellow("endDate")).setConstraint("");
		((Datebox) this.getFellow("startDate")).setConstraint("");
		((CapsTextbox)this.getFellow("remarks")).setConstraint("");
		((CapsTextbox)this.getFellow("pickup")).setConstraint("");
		((CapsTextbox)this.getFellow("destination")).setConstraint("");
		((Decimalbox)this.getFellow("FMSAmount")).setConstraint("");
		((Decimalbox)this.getFellow("fareAmt")).setConstraint("");
		((CapsTextbox)this.getFellow("taxiNo")).setConstraint("");
		((CapsTextbox)this.getFellow("nric")).setConstraint("");
	}
	
	private void addConstraints() throws InterruptedException{
		((Datebox) this.getFellow("endDate")).setConstraint(new com.cdgtaxi.ibs.web.constraint.RequiredBeforeOrEqualsCurrentDateConstraint());
		((Datebox) this.getFellow("startDate")).setConstraint(new com.cdgtaxi.ibs.web.constraint.RequiredBeforeOrEqualsCurrentDateConstraint());
		((CapsTextbox)this.getFellow("remarks")).setConstraint(new com.cdgtaxi.ibs.web.constraint.RequiredConstraint());
		((CapsTextbox)this.getFellow("pickup")).setConstraint(new com.cdgtaxi.ibs.web.constraint.RequiredConstraint());
		((CapsTextbox)this.getFellow("destination")).setConstraint(new com.cdgtaxi.ibs.web.constraint.RequiredConstraint());
		((Decimalbox)this.getFellow("fareAmt")).setConstraint(new com.cdgtaxi.ibs.web.constraint.RequiredZeroAmountForFareConstraint());
		((CapsTextbox)this.getFellow("taxiNo")).setConstraint(new com.cdgtaxi.ibs.web.constraint.RequiredConstraint());
		((CapsTextbox)this.getFellow("nric")).setConstraint(new com.cdgtaxi.ibs.web.constraint.RequiredConstraint());
	}
	
}
