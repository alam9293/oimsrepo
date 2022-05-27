package com.cdgtaxi.ibs.txn.ui;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.zkoss.util.media.AMedia;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Comboitem;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Decimalbox;
import org.zkoss.zul.Filedownload;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.Label;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Row;

import com.cdgtaxi.ibs.Uri;
import com.cdgtaxi.ibs.common.ConfigurableConstants;
import com.cdgtaxi.ibs.common.NonConfigurableConstants;
import com.cdgtaxi.ibs.common.model.AmtbAccount;
import com.cdgtaxi.ibs.common.model.TmtbAcquireTxn;
import com.cdgtaxi.ibs.common.ui.CommonWindow;
import com.cdgtaxi.ibs.exporter.PdfExportItem;
import com.cdgtaxi.ibs.exporter.ZkPdfExporter;
import com.cdgtaxi.ibs.util.ComponentUtil;
import com.cdgtaxi.ibs.util.DateUtil;
import com.cdgtaxi.ibs.util.FieldFormatterUtil;
import com.cdgtaxi.ibs.util.StringUtil;
import com.cdgtaxi.ibs.web.component.CapsTextbox;
import com.cdgtaxi.ibs.web.component.Constants;
import com.lowagie.text.PageSize;

public class SearchTxnWindow extends CommonWindow{
	private static Logger logger = Logger.getLogger(SearchTxnWindow.class);
	private static final long serialVersionUID = 1L;
	private List<Listitem> txnStatus = new ArrayList<Listitem>();

	public SearchTxnWindow(){
		// adding all txn status
		txnStatus.add((Listitem)ComponentUtil.createNotRequiredListItem());
		for(String statusCode : NonConfigurableConstants.TXN_STATUS.keySet()){
			txnStatus.add(new Listitem(NonConfigurableConstants.TXN_STATUS.get(statusCode), statusCode));
		}
	}
	
	public List<Listitem> getProductTypes() {
		List<Listitem> productTypeList = ComponentUtil.convertToListitems(this.businessHelper.getTxnBusiness().getAllProductTypes(), false);
		List<Listitem> cloneList = cloneList(productTypeList);
		return sortList(cloneList);
	}
	
	public List<Listitem> getTxnStatus() {
		List<Listitem> statusList = ComponentUtil.convertToListitems(NonConfigurableConstants.TXN_STATUS, false);
		return cloneList(statusList);
	}
	
	protected List<Listitem> sortList(List<Listitem> listitems){
		
		Collections.sort(listitems, new Comparator<Listitem>(){
			public int compare(Listitem o1, Listitem o2) {
				return o1.getLabel().compareTo(o2.getLabel());
			}
		});
		return listitems;
	}
	
	protected List<Listitem> cloneList(List<Listitem> listitems){
		List<Listitem> returnList = new ArrayList<Listitem>();
		for(Listitem listitem : listitems){
			returnList.add(new Listitem(listitem.getLabel(), listitem.getValue()));
		}
		return returnList;
	}
	
	public void onSelectAccountName() throws InterruptedException{
		logger.info("");
			
		try{
			Combobox accountNameComboBox = (Combobox)this.getFellow("accountNameComboBox");
			Intbox accountNoIntBox = (Intbox)this.getFellow("accountNoIntBox");
			//CapsTextbox accountNoTextBox = (CapsTextbox)this.getFellow("accountNoTextBox");
			
			//Fix to bypass IE6 issue with double spacing
			if(accountNameComboBox.getChildren().size()==1)
				accountNameComboBox.setSelectedIndex(0);
			
			if(accountNameComboBox.getSelectedItem()!=null){
				AmtbAccount selectedAccount = (AmtbAccount)accountNameComboBox.getSelectedItem().getValue();
				accountNoIntBox.setText(selectedAccount.getCustNo());
				
				//Display division or department according to account category
				if(selectedAccount.getAccountCategory().equals(NonConfigurableConstants.ACCOUNT_CATEGORY_CORPORATE)){
					List<AmtbAccount> divisions = this.businessHelper.getTxnBusiness().searchAccounts(selectedAccount);
					if (divisions != null && !divisions.isEmpty())
					{
						this.setDivisionInputVisible(divisions);
						this.setDepartmentInputInvisible();
					}
					this.setSubApplicantInvisible();
				}
				else if(selectedAccount.getAccountCategory().equals(NonConfigurableConstants.ACCOUNT_CATEGORY_APPLICANT))
				{
					List<AmtbAccount> subApplicants = this.businessHelper.getTxnBusiness().searchAccounts(selectedAccount);
					if (subApplicants != null && !subApplicants.isEmpty())
					{
						this.setSubApplicantVisible(subApplicants);
					}
					this.setDivisionInputInvisible();
					this.setDepartmentInputInvisible();					
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
	
	public void onSelectDivision() throws InterruptedException{
		logger.info("");
		
		try{
			Listbox divisionListBox = (Listbox)this.getFellow("divisionListBox");
			AmtbAccount selectedValue = (AmtbAccount) divisionListBox.getSelectedItem().getValue();
			
			if(selectedValue instanceof AmtbAccount){
				List<AmtbAccount> departments = this.businessHelper.getTxnBusiness().searchAccounts(selectedValue);
				logger.info("Account No: " + selectedValue.getAccountNo());
				if (departments != null && !departments.isEmpty())
					this.setDepartmentInputVisible(departments);
			}
			else{
				this.setDepartmentInputInvisible();
				// Populate the necessary information
			}
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
		
		Listitem blankItem = new Listitem("-");
		blankItem.setValue(null);
		divisionListBox.appendChild(blankItem);
		for(AmtbAccount division : divisions){
			Listitem newItem = new Listitem(division.getAccountName()+ " (" + division.getCode() + ")");
			newItem.setValue(division);
			divisionListBox.appendChild(newItem);
		}
		divisionListBox.setSelectedIndex(0);
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
		departmentListBox.setSelectedIndex(0);
	}
	
	private void setDivisionInputInvisible(){
		((Row)this.getFellow("divisionDepartmentRow")).setVisible(false);
		((Label)this.getFellow("divisionLabel")).setVisible(false);
		Listbox divisionListBox = (Listbox)this.getFellow("divisionListBox");
		divisionListBox.setVisible(false);
		divisionListBox.getChildren().clear();
	}
	
	private void setDepartmentInputInvisible(){
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
	
	public void searchAccount() throws InterruptedException{
		logger.info("");

		//Retrieve entered input
		Combobox accountNameComboBox = (Combobox)this.getFellow("accountNameComboBox");
		Integer customerNo = ((Intbox)this.getFellow("accountNoIntBox")).getValue();
		
		if (customerNo == null)
		{
			return;
		}
		//accountName still the same as selected one, skip
		if(accountNameComboBox.getSelectedItem()!=null){
			AmtbAccount selectedAccount = (AmtbAccount)accountNameComboBox.getSelectedItem().getValue();
			if(customerNo.toString().equals(selectedAccount.getCustNo())) return;
		}
		
		accountNameComboBox.setText("");
		accountNameComboBox.getChildren().clear();
		//this.onSelectAccountName();
		
		

		try{
	
			// Name is not required
			List<AmtbAccount> accounts = this.businessHelper.getTxnBusiness().searchAccounts(customerNo.toString(), "");
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
				((Intbox)this.getFellow("accountNoIntBox")).setValue(null);
				// Set focus back to accountText
				((Intbox)this.getFellow("accountNoIntBox")).setFocus(true);
				Messagebox.show("There is no such account in the system", 
						"Information", Messagebox.OK, Messagebox.INFORMATION);
			}
		}
		catch(Exception e){
			Messagebox.show(com.cdgtaxi.ibs.common.NonConfigurableConstants.COMMON_ERROR_MESSAGE, 
					"Error", Messagebox.OK, Messagebox.ERROR);
			e.printStackTrace();
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
		
		((Intbox)this.getFellow("accountNoIntBox")).setText("");
		accountNameComboBox.setText("");
		accountNameComboBox.getChildren().clear();
		this.onSelectAccountName();

		try{
			
			
			// Name is not required
			List<AmtbAccount> accounts = this.businessHelper.getTxnBusiness().searchAccounts(null, name);
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
			else
			{
				//Clear list for every new search
				accountNameComboBox.getChildren().clear();
				((Intbox)this.getFellow("accountNoIntBox")).setValue(null);
				accountNameComboBox.setFocus(true);
				Messagebox.show("There is no such account in the system", 
						"Information", Messagebox.OK, Messagebox.INFORMATION);
			}
		}
		catch(Exception e){
			Messagebox.show(com.cdgtaxi.ibs.common.NonConfigurableConstants.COMMON_ERROR_MESSAGE, 
					"Error", Messagebox.OK, Messagebox.ERROR);
			e.printStackTrace();
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
		
		((Intbox)this.getFellow("accountNoIntBox")).setText("");
		accountNameComboBox.setText("");
		accountNameComboBox.getChildren().clear();
		this.onSelectAccountName();

		try{
			
			
			// Name is not required
			List<AmtbAccount> accounts = this.businessHelper.getTxnBusiness().searchAccounts(null, name);
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
			e.printStackTrace();
		}
	}
	/**
	 * Search for all account
	 */
	public void search() throws InterruptedException{
		logger.info("search()");

		TxnSearchCriteria txnSearchCriteria = new TxnSearchCriteria();

		// getting custNo string from textbox
		String custNoString = ((Intbox)this.getFellow("accountNoIntBox")).getText();
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
		
		try
		{
			// getting account name
			Combobox accountNameComboBox = (Combobox)this.getFellow("accountNameComboBox");
			String name = accountNameComboBox.getValue();
			
			// Get divisisonListbox and department list box
			Listbox divisionListBox = (Listbox)this.getFellow("divisionListBox");
			Listbox departmentListBox = (Listbox)this.getFellow("departmentListBox");
			Listbox subApplicantListbox = (Listbox)this.getFellow("subApplicant");
			
			String jobNo = ((CapsTextbox)this.getFellow("jobNo")).getValue();
			String taxiNo = ((CapsTextbox)this.getFellow("taxiNo")).getValue();
			String cardStartNo = ((CapsTextbox)this.getFellow("cardStartNo")).getValue();
			String cardEndNo = ((CapsTextbox)this.getFellow("cardEndNo")).getValue();
			String nric = ((CapsTextbox)this.getFellow("nric")).getValue();
			String pickup = ((CapsTextbox)this.getFellow("pickup")).getValue();
			String destination = ((CapsTextbox)this.getFellow("destination")).getValue();
			String salesDraftNo = ((CapsTextbox)this.getFellow("salesDraftNo")).getValue();
			Listbox productTypeListBox = (Listbox) this.getFellow("productType");
			String productType = null;
			if (productTypeListBox.getSelectedItem() != null)
				productType = (String) productTypeListBox.getSelectedItem().getValue();
			Listbox txnStatusListBox = (Listbox) this.getFellow("txnStatus");
			String txnStatus = null;
			if (txnStatusListBox.getSelectedItem() != null)
				txnStatus = (String) txnStatusListBox.getSelectedItem().getValue();
			String fareAmt = null;
			if (((Decimalbox)this.getFellow("fareAmt")).getValue() != null)
				fareAmt = ((Decimalbox)this.getFellow("fareAmt")).getValue().toString();
			
			
			Datebox startDateBox = (Datebox) this.getFellow("startDate");
			Listbox startTimeHr = (Listbox) this.getFellow("startTimeHrDDL");
			Listbox startTimeMin = (Listbox) this.getFellow("startTimeMinDDL");
			Listbox startTimeSec = (Listbox) this.getFellow("startTimeSecDDL");
			Timestamp startDate = null;
			Datebox endDateBox = (Datebox) this.getFellow("endDate");
			Listbox endTimeHr = (Listbox) this.getFellow("endTimeHrDDL");
			Listbox endTimeMin = (Listbox) this.getFellow("endTimeMinDDL");
			Listbox endTimeSec = (Listbox) this.getFellow("endTimeSecDDL");
			Timestamp endDate = null;
			// start time in format of HH24MISS
			String endTimeOnly = null;
			String startTimeOnly = null;
			
			if (startDateBox != null && startDateBox.getValue() != null)
			{
				// Date is available
				Date startDateWithTime = combineDateHrMinSec(startDateBox.getValue(), Integer.parseInt(startTimeHr.getSelectedItem().getLabel()),
						Integer.parseInt(startTimeMin.getSelectedItem().getLabel()),Integer.parseInt(startTimeSec.getSelectedItem().getLabel()));
				startDate = DateUtil.convertDateToTimestamp(startDateWithTime);
				if (endDateBox != null && endDateBox.getValue() != null)
				{
					// Date is available
					Date endDateWithTime = combineDateHrMinSec(endDateBox.getValue(), Integer.parseInt(endTimeHr.getSelectedItem().getLabel()),
							Integer.parseInt(endTimeMin.getSelectedItem().getLabel()),Integer.parseInt(endTimeSec.getSelectedItem().getLabel()));
					endDate = DateUtil.convertDateToTimestamp(endDateWithTime);
				}
				//else if (!"000000".equals(endTimeHr.getSelectedItem().getLabel() + endTimeMin.getSelectedItem().getLabel() +
				//		endTimeSec.getSelectedItem().getLabel()))
				//{
					// Date is not available, just the time search only
				//	endTimeOnly = endTimeHr.getSelectedItem().getLabel() + endTimeMin.getSelectedItem().getLabel() +
				//		endTimeSec.getSelectedItem().getLabel();
				//}
				
				else
				{
					// Default end date to current date
					// Latest rule: Geok Hua: Default end date to start date if end date is null
					Date endDateWithTime = combineDateHrMinSec(startDateBox.getValue(), Integer.parseInt(endTimeHr.getSelectedItem().getLabel()),
							Integer.parseInt(endTimeMin.getSelectedItem().getLabel()),Integer.parseInt(endTimeSec.getSelectedItem().getLabel()));
					endDate = DateUtil.convertDateToTimestamp(endDateWithTime);
					//endDate = DateUtil.getCurrentTimestamp();
					endDateBox.setValue(endDate);
					// Populate the end date time

				}
			}
			else if (endDateBox != null && endDateBox.getValue() != null)
			{
				Messagebox.show("Please enter the start date", 
						"Error", Messagebox.OK, Messagebox.ERROR);
				return;
			}
			/*else if (!"000000".equals(startTimeHr.getSelectedItem().getLabel() + startTimeMin.getSelectedItem().getLabel() +
					startTimeSec.getSelectedItem().getLabel()))
			{
				// Date is not available, just the time search only
				startTimeOnly = startTimeHr.getSelectedItem().getLabel() + startTimeMin.getSelectedItem().getLabel() +
					startTimeSec.getSelectedItem().getLabel();
				if (!"000000".equals(endTimeHr.getSelectedItem().getLabel() + endTimeMin.getSelectedItem().getLabel() +
						endTimeSec.getSelectedItem().getLabel()))
					endTimeOnly = endTimeHr.getSelectedItem().getLabel() + endTimeMin.getSelectedItem().getLabel() +
					endTimeSec.getSelectedItem().getLabel();
			}*/
			
			/**** 
			 * Validation Criteria
			 */
			if (cardStartNo != null && cardEndNo != null && !"".equals(cardStartNo)
					&& !"".equals(cardEndNo))
			{
				// default to the max length of either card no first
				if (cardStartNo.length() >= cardEndNo.length())
				{
					cardStartNo = FieldFormatterUtil.formatAsNumber(cardStartNo, cardStartNo.length(), 0, false, true, "R");
					cardEndNo = FieldFormatterUtil.formatAsNumber(cardEndNo, cardStartNo.length(), 0, false, true, "R");
				}
				else
				{
					cardStartNo = FieldFormatterUtil.formatAsNumber(cardStartNo, cardEndNo.length(), 0, false, true, "R");
					cardEndNo = FieldFormatterUtil.formatAsNumber(cardEndNo, cardEndNo.length(), 0, false, true, "R");
				}
				
				((CapsTextbox)this.getFellow("cardStartNo")).setValue(cardStartNo);
				((CapsTextbox)this.getFellow("cardEndNo")).setValue(cardEndNo);
				
				if (Long.parseLong(cardStartNo) > Long.parseLong(cardEndNo))
				{
					Messagebox.show("Card End No should be greater than Card Start No", 
							"Error", Messagebox.OK, Messagebox.ERROR);
					return;
				}					
			}
			
			if (startDate != null && endDate != null)
			{
				if (startDate.after(endDate))
				{
					Messagebox.show("Start Date should be earlier than End Date", 
							"Error", Messagebox.OK, Messagebox.ERROR);
					return;
				}
			}
			
			/**** 
			 * Check Search Criteria
			 */
			if ((jobNo == null || "".equals(jobNo)))
			{
				if ((cardStartNo == null || "".equals(cardStartNo)))
				{
					// Check for Product type + trip start date + trip end date
					if (( productType == null || "".equals(productType)) && startDate == null && endDate == null)
					{
						// check for Taxi + trip start date/time + trip end date/time
						if ((taxiNo == null || "".equals(taxiNo)) && startDate == null && endDate == null)
						
						// if ((taxiNo == null || "".equals(taxiNo)) && (startTimeOnly == null || "".equals(startTimeOnly)) 
						// && (endTimeOnly == null || "".equals(endTimeOnly)))
						{
							// Ensure that all the fields must have values
							Messagebox.show("Please enter one of the selection criteria", 
							"Error", Messagebox.OK, Messagebox.ERROR);
							return;
						}
						else if ((taxiNo == null || "".equals(taxiNo)) || startDate == null || endDate == null)
						{
							// Ensure that all the fields must have values
							Messagebox.show("A combination of Taxi No, Start Time and End Time must be entered", 
							"Error", Messagebox.OK, Messagebox.ERROR);
							return;
						}
					}
					else if (productType == null || "".equals(productType) || startDate == null || endDate == null)
					{
						// check for Taxi + trip start date/time + trip end date/time
						if ((taxiNo == null || "".equals(taxiNo)) && startDate == null && endDate == null)
						
						// if ((taxiNo == null || "".equals(taxiNo)) && (startTimeOnly == null || "".equals(startTimeOnly)) 
						// && (endTimeOnly == null || "".equals(endTimeOnly)))
						{
							// Ensure that all the fields must have values
							Messagebox.show("A combination of Product Type, Start Date and End Date must be entered", 
									"Error", Messagebox.OK, Messagebox.ERROR);
									return;
						}
						else if ((taxiNo == null || "".equals(taxiNo)) || startDate == null || endDate == null)
						{
							// Ensure that all the fields must have values
							Messagebox.show("A combination of Product Type, Start Date and End Date must be entered", 
							"Error", Messagebox.OK, Messagebox.ERROR);
							return;
						}
						
					}
				}
			}
					
			/***
			 * Population of txn criteria object for search
			 */
			if (subApplicantListbox.getSelectedItem() != null && subApplicantListbox.getSelectedItem().getValue() != null)
			{
				txnSearchCriteria.setAmtbAccount((AmtbAccount)subApplicantListbox.getSelectedItem().getValue());
			}
			else if (departmentListBox.getSelectedItem() != null && departmentListBox.getSelectedItem().getValue() != null)
			{
				txnSearchCriteria.setAmtbAccount((AmtbAccount)departmentListBox.getSelectedItem().getValue());
			}
			else if (divisionListBox.getSelectedItem() != null && divisionListBox.getSelectedItem().getValue() != null)
			{
				txnSearchCriteria.setAmtbAccount((AmtbAccount)divisionListBox.getSelectedItem().getValue());
			}
			else if (accountNameComboBox.getSelectedItem() != null)
			{
				txnSearchCriteria.setAmtbAccount((AmtbAccount)accountNameComboBox.getSelectedItem().getValue());
			}
			txnSearchCriteria.setCardNoStart(cardStartNo);
			txnSearchCriteria.setCardNoEnd(cardEndNo);
			txnSearchCriteria.setFareAmt(fareAmt);
			txnSearchCriteria.setJobNo(jobNo);
			txnSearchCriteria.setNric(nric);
			txnSearchCriteria.setProductType(productType);
			txnSearchCriteria.setTaxiNo(taxiNo);
			txnSearchCriteria.setTxnStatus(txnStatus);
			txnSearchCriteria.setSalesDraftNo(salesDraftNo);
			txnSearchCriteria.setPickup(pickup);
			txnSearchCriteria.setDestination(destination);
			
			txnSearchCriteria.setTripStartDate(DateUtil.convertTimestampToStr(startDate, DateUtil.TRIPS_DATE_FORMAT));
			txnSearchCriteria.setTripEndDate(DateUtil.convertTimestampToStr(endDate, DateUtil.TRIPS_DATE_FORMAT));
			
			Listbox txns = (Listbox)this.getFellow("txns");
			// clearing any previous search
			txns.getItems().clear();
			// getting results from business layer
			displayProcessing();
			List<TmtbAcquireTxn> results = this.businessHelper.getTxnBusiness().searchTxns(txnSearchCriteria);
			// for each result
			if (results != null && !results.isEmpty())
			{
				if(results.size()>ConfigurableConstants.getMaxQueryResult())
					Messagebox.show(NonConfigurableConstants.getExceedMaxResultMessage(), "Alert", Messagebox.OK, Messagebox.INFORMATION);
				
				int count=0;
				
				for(TmtbAcquireTxn txnItem : results)
				{
					count++;
					
					// creating a new row and append it to rows
					Listitem txn = new Listitem(); 
					txns.appendChild(txn);
					// setting the list as the value
					txn.setValue(txnItem);
					// Handle
					if (NonConfigurableConstants.ACCOUNT_CATEGORY_DEPARTMENT.equals(txnItem.getAmtbAccount().getAccountCategory()))
					{
						txn.appendChild(newListcell(txnItem.getAmtbAccount().getAmtbAccount().getAmtbAccount().getCustNo()));
					}
					else if (NonConfigurableConstants.ACCOUNT_CATEGORY_DIVISION.equals(txnItem.getAmtbAccount().getAccountCategory()))
					{
						txn.appendChild(newListcell(txnItem.getAmtbAccount().getAmtbAccount().getCustNo()));			
					}
					else if (NonConfigurableConstants.ACCOUNT_CATEGORY_CORPORATE.equals(txnItem.getAmtbAccount().getAccountCategory()))
					{
						txn.appendChild(newListcell(txnItem.getAmtbAccount().getCustNo()));
					}
					else if (NonConfigurableConstants.ACCOUNT_CATEGORY_APPLICANT.equals(txnItem.getAmtbAccount().getAccountCategory()))
					{
						txn.appendChild(newListcell(txnItem.getAmtbAccount().getCustNo()));
					}
					else if (NonConfigurableConstants.ACCOUNT_CATEGORY_SUB_APPLICANT.equals(txnItem.getAmtbAccount().getAccountCategory()))
					{
						txn.appendChild(newListcell(txnItem.getAmtbAccount().getAmtbAccount().getCustNo()));
					}
					else
						txn.appendChild(newListcell("-"));
					
					if (txnItem.getPmtbProduct() != null)
						txn.appendChild(newListcell(txnItem.getPmtbProduct().getCardNo()));
					else
						// if external card is not empty, show external card no
						if (txnItem.getExternalCardNo() != null && !"".equals(txnItem.getExternalCardNo()))
						{
							txn.appendChild(newListcell(txnItem.getExternalCardNo()));
						}
						else
						{
							txn.appendChild(newListcell("-"));
						}
					txn.appendChild(newListcell(txnItem.getPmtbProductType().getProductTypeId()));
					txn.appendChild(newListcell(NonConfigurableConstants.TXN_STATUS.get(txnItem.getTxnStatus())));
					txn.appendChild(newListcell(txnItem.getJobNo()));
					txn.appendChild(newListcell(txnItem.getTaxiNo()));
					txn.appendChild(newListcell(StringUtil.maskNric(txnItem.getNric())));
					txn.appendChild(newListcell(txnItem.getFareAmt(), StringUtil.GLOBAL_DECIMAL_FORMAT));
					txn.appendChild(newListcell(DateUtil.convertTimestampToStr(txnItem.getTripStartDt(), DateUtil.TRIPS_DATE_FORMAT)));					
				}

				//To show the no record found message below the list
				if(txns.getListfoot()!=null && count>0)
					txns.removeChild(txns.getListfoot());	
				txns.setMold(Constants.LISTBOX_MOLD_PAGING);
					//resultListBox.setPageSize(10);
				txns.setPageSize(ConfigurableConstants.getSortPagingSize());
				
				if(results.size()<1){
					if(txns.getListfoot()!=null)
						txns.removeChild(txns.getListfoot());	
					txns.appendChild(ComponentUtil.createNoRecordsFoundListFoot(11));
				}
				
				if(results.size()>ConfigurableConstants.getMaxQueryResult())
					txns.removeItemAt(ConfigurableConstants.getMaxQueryResult());
				
				// to display the total count
//				if (txns.getItemCount() > 0)
//					txns.appendChild(ComponentUtil.createTotalRecordListFoot(11, txns.getItemCount()));
				((Grid)this.getFellow("gridTest")).setVisible(true);
				((Grid)this.getFellow("gridButton")).setVisible(true);
				((Label)this.getFellow("footTotalValue")).setValue("Total Count : "+txns.getItemCount());
				
				txns.setVisible(true);
			}
			else
			{
				Messagebox.show("No matching transactions.", "Manage Transactions", Messagebox.OK, Messagebox.INFORMATION);
				((Label)this.getFellow("footTotalValue")).setValue("Total Count : 0");
				((Grid)this.getFellow("gridTest")).setVisible(false);
			}
		}
		catch(Exception e){
			Messagebox.show(com.cdgtaxi.ibs.common.NonConfigurableConstants.COMMON_ERROR_MESSAGE, 
					"Error", Messagebox.OK, Messagebox.ERROR);
			e.printStackTrace();
		}
	}
	@SuppressWarnings("unchecked")
	public void selectTxn() throws InterruptedException{
		logger.info("selectTxn()");
		// getting the list box
		Listbox txns = (Listbox)this.getFellow("txns");
		// getting the selected item
		Listitem selectedTxn = txns.getSelectedItem();
		// creating params
		Map<String, String> params = new LinkedHashMap<String, String>();
		// getting the selected account details
		TmtbAcquireTxn txnDetails = (TmtbAcquireTxn) selectedTxn.getValue();
		// putting txnID into the params
		params.put("txnID", txnDetails.getAcquireTxnNo().toString());

		// Forward to view page
		this.forward(Uri.VIEW_TXN, params);

	}
	
	public void reset() throws InterruptedException
	{
		logger.debug("SearchTxnWindow refresh()");
		
		// Clear all the values
		((Intbox)this.getFellow("accountNoIntBox")).setText(null);
		
		Combobox accountNameComboBox = (Combobox)this.getFellow("accountNameComboBox");
		accountNameComboBox.getChildren().clear();
		accountNameComboBox.setText(null);
		
		this.setDivisionInputInvisible();
		this.setDepartmentInputInvisible();
		
		((CapsTextbox)this.getFellow("jobNo")).setValue(null);
		((CapsTextbox)this.getFellow("taxiNo")).setValue(null);
		((CapsTextbox)this.getFellow("cardStartNo")).setValue(null);
		((CapsTextbox)this.getFellow("cardEndNo")).setValue(null);
		((CapsTextbox)this.getFellow("nric")).setValue(null);
		((CapsTextbox)this.getFellow("salesDraftNo")).setValue(null);
		((CapsTextbox)this.getFellow("pickup")).setValue(null);
		((CapsTextbox)this.getFellow("destination")).setValue(null);
		Listbox productTypeListBox = (Listbox) this.getFellow("productType");
		productTypeListBox.setSelectedIndex(0);
		((Decimalbox)this.getFellow("fareAmt")).setValue(null);
		
		Datebox startDateBox = (Datebox) this.getFellow("startDate");
		startDateBox.setValue(null);
		Listbox startTimeHr = (Listbox) this.getFellow("startTimeHrDDL");
		startTimeHr.setSelectedIndex(0);
		Listbox startTimeMin = (Listbox) this.getFellow("startTimeMinDDL");
		startTimeMin.setSelectedIndex(0);
		Listbox startTimeSec = (Listbox) this.getFellow("startTimeSecDDL");
		startTimeSec.setSelectedIndex(0);
		Datebox endDateBox = (Datebox) this.getFellow("endDate");
		endDateBox.setValue(null);
		Listbox endTimeHr = (Listbox) this.getFellow("endTimeHrDDL");
		endTimeHr.setSelectedIndex(23);
		Listbox endTimeMin = (Listbox) this.getFellow("endTimeMinDDL");
		endTimeMin.setSelectedIndex(59);
		Listbox endTimeSec = (Listbox) this.getFellow("endTimeSecDDL");
		endTimeSec.setSelectedIndex(59);
		// reset txn status
		Listbox txnStatus = (Listbox) this.getFellow("txnStatus");
		txnStatus.setSelectedIndex(0);
		
		// Clear txns
		Listbox txns = (Listbox)this.getFellow("txns");
		txns.getItems().clear();
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

	@Override
	public void refresh() throws InterruptedException {
		search();
	}
	
	public void exportResult() throws InterruptedException, IOException {
		logger.info("exportResult");
		Combobox accountNameComboBox = (Combobox)this.getFellow("accountNameComboBox");
		
		if(((CapsTextbox)this.getFellow("pickup")).getValue() != null)
			((CapsTextbox)this.getFellow("pickup")).setValue(((CapsTextbox)this.getFellow("pickup")).getValue().toUpperCase());
		if(((CapsTextbox)this.getFellow("destination")).getValue() != null)
			((CapsTextbox)this.getFellow("destination")).setValue(((CapsTextbox)this.getFellow("destination")).getValue().toUpperCase());
		if(((CapsTextbox)this.getFellow("salesDraftNo")).getValue() != null)
			((CapsTextbox)this.getFellow("salesDraftNo")).setValue(((CapsTextbox)this.getFellow("salesDraftNo")).getValue().toUpperCase());
		if(((CapsTextbox)this.getFellow("taxiNo")).getValue() != null)
			((CapsTextbox)this.getFellow("taxiNo")).setValue(((CapsTextbox)this.getFellow("taxiNo")).getValue().toUpperCase());
		if(((CapsTextbox)this.getFellow("nric")).getValue() != null)
			((CapsTextbox)this.getFellow("nric")).setValue(((CapsTextbox)this.getFellow("nric")).getValue().toUpperCase());
		
		
		List<PdfExportItem> items = new ArrayList<PdfExportItem>();
		items.add(new PdfExportItem((Grid)this.getFellow("accountGrid"), "Account Details", null));
		items.add(new PdfExportItem((Grid)this.getFellow("searchFieldGrid"), "Search Transaction", null));
		items.add(new PdfExportItem((Listbox)this.getFellow("txns"), "Transactions", null));
		
	    ByteArrayOutputStream out = new ByteArrayOutputStream();
	     
		ZkPdfExporter exp = new ZkPdfExporter(PageSize.A4.rotate());
		try{
		exp.export(items, out, accountNameComboBox.getValue());
	     
	    AMedia amedia = new AMedia("Maint_Trip_Txn.pdf", "pdf", "application/pdf", out.toByteArray());
	    Filedownload.save(amedia);   
		
		out.close();
		}catch (Exception e) {
			try {
				Messagebox.show(com.cdgtaxi.ibs.common.NonConfigurableConstants.COMMON_ERROR_MESSAGE,
						"Error", Messagebox.OK, Messagebox.ERROR);
			} catch (InterruptedException e1) { e.printStackTrace(); }
		}
	}
}
