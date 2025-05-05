package com.cdgtaxi.ibs.report.ui;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import org.apache.commons.httpclient.HttpException;
import org.apache.log4j.Logger;
import org.zkoss.util.media.AMedia;
import org.zkoss.zhtml.Messagebox;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zk.ui.ext.AfterCompose;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Decimalbox;
import org.zkoss.zul.Filedownload;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Row;

import com.cdgtaxi.ibs.common.NonConfigurableConstants;
import com.cdgtaxi.ibs.common.model.AmtbAccount;
import com.cdgtaxi.ibs.common.model.PmtbProductType;
import com.cdgtaxi.ibs.common.ui.CommonWindow;
import com.cdgtaxi.ibs.master.model.MstbMasterTable;
import com.cdgtaxi.ibs.master.model.MstbReportFormatMap;
import com.cdgtaxi.ibs.report.Constants;
import com.cdgtaxi.ibs.util.AccountSearchUtil;
import com.cdgtaxi.ibs.util.DateUtil;
import com.cdgtaxi.ibs.web.component.Datebox;
import com.cdgtaxi.ibs.web.constraint.RequiredConstraint;
import com.elixirtech.net.NetException;

public class SoftCopyInvoiceTripDetailsByTransactionStatusWindow extends CommonWindow implements AfterCompose{
	
	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(SoftCopyInvoiceTripDetailsByTransactionStatusWindow.class);
	
	private Long reportRsrcId;
	
	public SoftCopyInvoiceTripDetailsByTransactionStatusWindow() throws IOException{
		reportRsrcId = new Long(this.getHttpServletRequest().getParameter("rsrcId"));
	}
	
	public void afterCompose() {
		//populate typeList
		Listbox sortByList = (Listbox)this.getFellow("sortByList");
		sortByList.appendChild(new Listitem("TRIP DATE", "1"));
		sortByList.appendChild(new Listitem("CARD NO.", "2"));
		sortByList.setSelectedIndex(0);
		
		//populate product type list
		Listbox productTypeListBox = (Listbox)this.getFellow("productTypeListBox");
		productTypeListBox.appendChild(new Listitem("ALL", ""));
		productTypeListBox.setSelectedIndex(0);
		
		List<PmtbProductType> productTypes = this.businessHelper.getProductTypeBusiness().getAllProductType();
		for(PmtbProductType productType : productTypes){
			productTypeListBox.appendChild(new Listitem(productType.getName(), productType.getProductTypeId()));
		}
		
		//populate transaction status list
		Listbox transactionStatusListBox = (Listbox)this.getFellow("transactionStatusListBox");
		transactionStatusListBox.appendChild(new Listitem("-", ""));
		transactionStatusListBox.setSelectedIndex(0);
		
		Set<String> txnStatusKeySet = NonConfigurableConstants.TXN_STATUS.keySet();
		for(String key : txnStatusKeySet){
			transactionStatusListBox.appendChild(new Listitem(NonConfigurableConstants.TXN_STATUS.get(key), key));
			
			if(key.equals(NonConfigurableConstants.TRANSACTION_STATUS_BILLED))
				transactionStatusListBox.setSelectedIndex(transactionStatusListBox.getChildren().size()-1);
		}
	}
	
	public void onSelectAccountName() throws InterruptedException{
		logger.info("");

		try{
			Combobox accountNameComboBox = (Combobox)this.getFellow("accountNameComboBox");
			Intbox accountNoIntBox = (Intbox)this.getFellow("accountNoIntBox");

			if(accountNameComboBox.getSelectedItem()!=null){
				
				String selectedAccountNo = (String)accountNameComboBox.getSelectedItem().getValue();
				AmtbAccount selectedAccount = (AmtbAccount)this.businessHelper.getGenericBusiness().get(AmtbAccount.class, new Integer(selectedAccountNo));
				accountNoIntBox.setText(selectedAccount.getCustNo());
				
				Listbox divisionListBox = (Listbox)this.getFellow("divisionListBox");
				AccountSearchUtil.populateDivisionOrSubApplicantName(divisionListBox, selectedAccountNo);
				
				if(divisionListBox.getChildren().size()>0)
					((Row)this.getFellow("divisionRow")).setVisible(true);
				else
					((Row)this.getFellow("divisionRow")).setVisible(false);
			}
			else{
				this.clear();
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
			String selectedValue = (String)divisionListBox.getSelectedItem().getValue();
			if(selectedValue!=null && selectedValue.length()>0){
				Listbox departmentListBox = (Listbox)this.getFellow("departmentListBox");
				AccountSearchUtil.populateDepartmentName(departmentListBox, selectedValue);//populate department combo box
				if(departmentListBox.getChildren().size()>0)
					((Row)this.getFellow("departmentRow")).setVisible(true);
				else
					((Row)this.getFellow("departmentRow")).setVisible(false);
			}
		}
		catch(Exception e){
			Messagebox.show(com.cdgtaxi.ibs.common.NonConfigurableConstants.COMMON_ERROR_MESSAGE,
					"Error", Messagebox.OK, Messagebox.ERROR);
			e.printStackTrace();
		}
	}

	public void searchAccountByAccountNo() throws InterruptedException{
		logger.info("");

		
		Intbox accountNoIntBox = (Intbox)this.getFellow("accountNoIntBox");
		Combobox accountNameComboBox = (Combobox)this.getFellow("accountNameComboBox");
		Integer accountNo = accountNoIntBox.getValue();
		
		if(accountNo==null) return;
		
		//accountName still the same as selected one, skip
		if(accountNameComboBox.getSelectedItem()!=null){
			String selectedAccountNo = (String)accountNameComboBox.getSelectedItem().getValue();
			AmtbAccount selectedAccount = (AmtbAccount)this.businessHelper.getGenericBusiness().get(AmtbAccount.class, new Integer(selectedAccountNo));
			if(accountNo.toString().equals(selectedAccount.getCustNo())) {
				return;
			}
		}

		//Clear combobox for a new search
		accountNameComboBox.setText("");
		//Clear list for every new search
		accountNameComboBox.getChildren().clear();
		//Clear listed invoices + division + department
		this.clear();

		try{
			AccountSearchUtil.populateAccountNameCbo(accountNameComboBox,accountNo.toString(),"");
			if(accountNameComboBox.getChildren().size()==1) {
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

	public void searchAccountByAccountName(String name) throws InterruptedException{
		logger.info("");
		Combobox accountNameComboBox = (Combobox)this.getFellow("accountNameComboBox");
		
		//only begin new search if input is greater than 2
		if(name.length()<3) {
			return;
		}

		//accountName still the same as selected one, skip
		if(accountNameComboBox.getSelectedItem()!=null){
			String selectedAccountNo = (String)accountNameComboBox.getSelectedItem().getValue();
			AmtbAccount selectedAccount = (AmtbAccount)this.businessHelper.getGenericBusiness().get(AmtbAccount.class, new Integer(selectedAccountNo));
			if(name.equals(selectedAccount.getAccountName()+" ("+selectedAccount.getCustNo()+")")) {
				return;
			}
		}

		//clear textbox for a new search
		Intbox accountNoIntBox = (Intbox)this.getFellow("accountNoIntBox");
		accountNoIntBox.setText("");
		//Clear list for every new search
		accountNameComboBox.getChildren().clear();
		//Clear listed invoices + division + department
		this.clear();

		try{
			AccountSearchUtil.populateAccountNameCbo(accountNameComboBox,"",name);
			if(accountNameComboBox.getChildren().size()==1){
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

	private void setDivisionInputInvisible(){
		((Row)this.getFellow("divisionRow")).setVisible(false);
		Listbox divisionListBox = (Listbox)this.getFellow("divisionListBox");
		divisionListBox.getChildren().clear();
	}

	private void setDepartmentInputInvisible(){
		((Row)this.getFellow("departmentRow")).setVisible(false);
		Listbox departmentListBox = (Listbox)this.getFellow("departmentListBox");
		departmentListBox.getChildren().clear();
	}
	
	private void clear(){
		this.setDivisionInputInvisible();
		this.setDepartmentInputInvisible();
	}
	
	public void populateReportFormatList(Listbox listbox) throws NetException{
		List<MstbReportFormatMap> reportFormatMapList = this.businessHelper.getReportBusiness().getReportFormatMap(this.reportRsrcId);
		boolean firstItem = true;
		for(MstbReportFormatMap formatMap : reportFormatMapList){
			Listitem listItem = new Listitem(formatMap.getReportFormat(), Constants.EXTENSION_MAP.get(formatMap.getReportFormat()));
			if(firstItem){
				listItem.setSelected(true);
				firstItem = false;
			}
			listbox.appendChild(listItem);
		}
	}
	
	public void generate() throws HttpException, IOException, InterruptedException, NetException, WrongValueException {
		logger.info("");
		
		String accountNo = "";
		String accountName = "";
		String tripStartDate = "";
		String tripEndDate = "";
		String productTypeId = "";
		String transactionStatus = "";
		String cardNo = "";
		String sortBy = "";
		
		//selected account
		Combobox accountNameComboBox = (Combobox)this.getFellow("accountNameComboBox");
		if(accountNameComboBox.getSelectedItem()==null) {
			throw new WrongValueException(accountNameComboBox, "* Mandatory field");
		}
		accountNo = (String)accountNameComboBox.getSelectedItem().getValue();
		//division
		Listbox divisionListBox = (Listbox)this.getFellow("divisionListBox");
		if(divisionListBox.getSelectedItem()!=null){
			String value = (String)divisionListBox.getSelectedItem().getValue();
			if(value!=null && value.length()>0) {
				accountNo = value;
			}
		}
		//department
		Listbox departmentListBox = (Listbox)this.getFellow("departmentListBox");
		if(departmentListBox.getSelectedItem()!=null){
			String value = (String)departmentListBox.getSelectedItem().getValue();
			if(value!=null && value.length()>0) {
				accountNo = value;
			}
		}
		
		Datebox tripStartDateBox = (Datebox)this.getFellow("tripStartDateBox");
		tripStartDate = tripStartDateBox.getText();
		
		Datebox tripEndDateBox = (Datebox)this.getFellow("tripEndDateBox");
		if(tripEndDateBox.getValue()!=null &&
				tripEndDateBox.getValue().compareTo(tripStartDateBox.getValue())<0)
			throw new WrongValueException(tripEndDateBox, "Trip End Date cannot be earlier than Trip Start Date");
		
		if(tripEndDateBox.getValue()==null){
			tripEndDateBox.setText(tripStartDate);
			tripEndDate = tripStartDate;
		}
		else{
			tripEndDate = tripEndDateBox.getText();
		}
		
		Listbox productTypeListBox = (Listbox)this.getFellow("productTypeListBox");
		productTypeId = (String)productTypeListBox.getSelectedItem().getValue();
		
		Listbox transactionStatusListBox = (Listbox)this.getFellow("transactionStatusListBox");
		transactionStatus = (String)transactionStatusListBox.getSelectedItem().getValue();
		
		Decimalbox cardNoDecimalBox = (Decimalbox)this.getFellow("cardNoDecimalBox");
		if(cardNoDecimalBox.getValue()!=null)
			cardNo = cardNoDecimalBox.getValue().toString();
		
		Listbox sortByList = (Listbox)this.getFellow("sortByList");
		sortBy = sortByList.getSelectedItem().getValue().toString();
		
		//retrieve format
		Listbox formatList = (Listbox)this.getFellow("reportFormat");
		if(formatList.getSelectedItem()==null) throw new WrongValueException(formatList, "* Mandatory field");
		String extension = (String)formatList.getSelectedItem().getLabel();
		String format = (String)formatList.getSelectedItem().getValue();

		this.displayProcessing();
		
		if(format.equals(Constants.FORMAT_CSV)){
			StringBuffer dataInCSV = this.generateCSVData(accountNo, accountName, cardNo, transactionStatus, productTypeId, tripStartDate, tripEndDate, sortBy);
			AMedia media = new AMedia("Soft_Copy_Invoice_And_Trips_Detail_By_Transaction_Status"+"."+extension, extension, format, dataInCSV.toString());
			Filedownload.save(media);
		}
	}

	private StringBuffer generateCSVData(String accountNo, 
			String accountNameInput, String cardNo, String txnStatus, String productTypeId,
			String tripStartDate, String tripEndDate, String sortBy){
			
		StringBuffer sb = new StringBuffer();
		
		//Report Header
		sb.append(Constants.TEXT_QUALIFIER+"Integrated Billing System"+Constants.TEXT_QUALIFIER+",");
		sb.append("\n");
		sb.append(Constants.TEXT_QUALIFIER+"Trips Detail"+Constants.TEXT_QUALIFIER+",");
		sb.append("\n");
		sb.append(Constants.TEXT_QUALIFIER+"Selection Criteria:"+Constants.TEXT_QUALIFIER+",");
		sb.append("\n");
		
		//Line Break
		sb.append("\n");
		
		if(accountNo!=null && accountNo.length()>0){
			
			AmtbAccount account = (AmtbAccount)this.businessHelper.getGenericBusiness().get(AmtbAccount.class, new Integer(accountNo));
			account = this.businessHelper.getAccountBusiness().getAccountWithParent(account);
			
			if(account.getAccountCategory().equals(NonConfigurableConstants.ACCOUNT_CATEGORY_DEPARTMENT)){
				String custNo = account.getAmtbAccount().getAmtbAccount().getCustNo();
				sb.append(Constants.TEXT_QUALIFIER+"Account No: "+custNo+Constants.TEXT_QUALIFIER+",");
				sb.append("\n");
				
				String accountName = account.getAmtbAccount().getAccountName()+" ("+account.getAmtbAccount().getCode()+") / "+account.getAccountName()+" ("+account.getCode()+")";
				sb.append(Constants.TEXT_QUALIFIER+"Account Name: "+accountName+Constants.TEXT_QUALIFIER+",");
				sb.append("\n");
			}
			else if(account.getAccountCategory().equals(NonConfigurableConstants.ACCOUNT_CATEGORY_DIVISION)){
				String custNo = account.getAmtbAccount().getCustNo();
				sb.append(Constants.TEXT_QUALIFIER+"Account No: "+custNo+Constants.TEXT_QUALIFIER+",");
				sb.append("\n");
				
				String accountName = account.getAmtbAccount().getAccountName()+" ("+account.getCode()+")";
				sb.append(Constants.TEXT_QUALIFIER+"Account Name: "+accountName+Constants.TEXT_QUALIFIER+",");
				sb.append("\n");
			}
			else if(account.getAccountCategory().equals(NonConfigurableConstants.ACCOUNT_CATEGORY_CORPORATE) ||
					account.getAccountCategory().equals(NonConfigurableConstants.ACCOUNT_CATEGORY_APPLICANT)){
				String custNo = account.getCustNo();
				sb.append(Constants.TEXT_QUALIFIER+"Account No: "+custNo+Constants.TEXT_QUALIFIER+",");
				sb.append("\n");
				
				String accountName = account.getAccountName();
				sb.append(Constants.TEXT_QUALIFIER+"Account Name: "+accountName+Constants.TEXT_QUALIFIER+",");
				sb.append("\n");
			}
			else if(account.getAccountCategory().equals(NonConfigurableConstants.ACCOUNT_CATEGORY_SUB_APPLICANT)){
				String custNo = account.getAmtbAccount().getCustNo();
				sb.append(Constants.TEXT_QUALIFIER+"Account No: "+custNo+Constants.TEXT_QUALIFIER+",");
				sb.append("\n");
				
				String accountName = account.getAccountName();
				sb.append(Constants.TEXT_QUALIFIER+"Account Name: "+accountName+Constants.TEXT_QUALIFIER+",");
				sb.append("\n");
			}
		}
		if(tripStartDate!=null && tripStartDate.length()>0){
			sb.append(Constants.TEXT_QUALIFIER+"Trip Start Date: "+tripStartDate+Constants.TEXT_QUALIFIER+",");
			sb.append("\n");
		}
		if(tripEndDate!=null && tripEndDate.length()>0){
			sb.append(Constants.TEXT_QUALIFIER+"Trip End Date: "+tripEndDate+Constants.TEXT_QUALIFIER+",");
			sb.append("\n");
		}
		if(productTypeId!=null && productTypeId.length()==0){
			sb.append(Constants.TEXT_QUALIFIER+"Product Type: ALL"+Constants.TEXT_QUALIFIER+",");
			sb.append("\n");
		}
		else{
			PmtbProductType productType = (PmtbProductType)this.businessHelper.getGenericBusiness().get(PmtbProductType.class, productTypeId);
			sb.append(Constants.TEXT_QUALIFIER+"Product Type: "+productType.getName()+Constants.TEXT_QUALIFIER+",");
			sb.append("\n");
		}
		if(txnStatus!=null && txnStatus.length()>0){
			sb.append(Constants.TEXT_QUALIFIER+"Transaction Status: "+NonConfigurableConstants.TXN_STATUS.get(txnStatus)+Constants.TEXT_QUALIFIER+",");
			sb.append("\n");
		}
		if(cardNo!=null && cardNo.length()>0){
			sb.append(Constants.TEXT_QUALIFIER+"Card No: "+cardNo+Constants.TEXT_QUALIFIER+",");
			sb.append("\n");
		}
		
		//Line Break
		sb.append("\n");
		
		sb.append(Constants.TEXT_QUALIFIER+"Printed By: "+getUserLoginIdAndDomain()+Constants.TEXT_QUALIFIER+",");
		sb.append("\n");
		sb.append(Constants.TEXT_QUALIFIER+"Report Date: "+DateUtil.convertDateToStr(DateUtil.getCurrentDate(), DateUtil.GLOBAL_DATE_FORMAT)+Constants.TEXT_QUALIFIER+",");
		sb.append("\n");
		
		//Line Break
		sb.append("\n");
		
		//Check how many additional Surcharge Columns for fare.
		List<MstbMasterTable> mstbMasterTableList = this.businessHelper.getReportBusiness().getASCTDRConfig();
		HashMap<String, String> hValueMap = new HashMap();
		HashMap<String, String> surchargeHMap = new HashMap();
		HashMap mapSurchargeAllInitial = new HashMap();
		
		if(mstbMasterTableList != null)
		{
			if(mstbMasterTableList.size() > 0 )
			{
				for(MstbMasterTable mstbObj : mstbMasterTableList)
				{
					String value = mstbObj.getMasterValue();
					String[] parts = value.split(";");
					String part1 = parts[0];
					String part2 = parts[1];
					
					hValueMap.put(part1, part2);
					surchargeHMap.put(mstbObj.getMasterCode(), part2);
				}
			}
		}
		
//		System.out.println("test first > "+hValueMap);
//		System.out.println("test2 > "+surchargeHMap);
		
		//Column Title
		sb.append(Constants.TEXT_QUALIFIER+"S/N"+Constants.TEXT_QUALIFIER+",");
		sb.append(Constants.TEXT_QUALIFIER+"Card No / Job Type"+Constants.TEXT_QUALIFIER+","); //0
		sb.append(Constants.TEXT_QUALIFIER+"Card Holder Name"+Constants.TEXT_QUALIFIER+",");
		sb.append(Constants.TEXT_QUALIFIER+"Issued To"+Constants.TEXT_QUALIFIER+",");
		sb.append(Constants.TEXT_QUALIFIER+"Job No"+Constants.TEXT_QUALIFIER+",");
		sb.append(Constants.TEXT_QUALIFIER+"Travel Date"+Constants.TEXT_QUALIFIER+",");
		sb.append(Constants.TEXT_QUALIFIER+"Travel Time"+Constants.TEXT_QUALIFIER+",");
		sb.append(Constants.TEXT_QUALIFIER+"Taxi No"+Constants.TEXT_QUALIFIER+",");
		sb.append(Constants.TEXT_QUALIFIER+"Pickup Address"+Constants.TEXT_QUALIFIER+",");
		sb.append(Constants.TEXT_QUALIFIER+"Destination"+Constants.TEXT_QUALIFIER+",");
		sb.append(Constants.TEXT_QUALIFIER+"Job Type"+Constants.TEXT_QUALIFIER+",");
		sb.append(Constants.TEXT_QUALIFIER+"Meter Fare ($)"+Constants.TEXT_QUALIFIER+",");    //10
		
		//new extra column for #104. & order by the 01,02,03,04,05 etc
		if(!hValueMap.isEmpty()) {
			SortedSet<String> keys = new TreeSet<String>(hValueMap.keySet());
			for (String key : keys) {
				sb.append(Constants.TEXT_QUALIFIER+hValueMap.get(key)+" ($)"+Constants.TEXT_QUALIFIER+","); //11  for all the surcharge together
				mapSurchargeAllInitial.put(hValueMap.get(key), new BigDecimal(0));
			}
		}

		sb.append(Constants.TEXT_QUALIFIER+"Total Fare ($)"+Constants.TEXT_QUALIFIER+",");   //12
		
		sb.append(Constants.TEXT_QUALIFIER+"Admin($)"+Constants.TEXT_QUALIFIER+",");
		sb.append(Constants.TEXT_QUALIFIER+"GST($)"+Constants.TEXT_QUALIFIER+",");
		sb.append(Constants.TEXT_QUALIFIER+"Total($)"+Constants.TEXT_QUALIFIER+",");
		sb.append(Constants.TEXT_QUALIFIER+"Distance Run (KM)"+Constants.TEXT_QUALIFIER+",");
		sb.append(Constants.TEXT_QUALIFIER+"Invoice Month"+Constants.TEXT_QUALIFIER+",");
		sb.append(Constants.TEXT_QUALIFIER+"Invoice Year"+Constants.TEXT_QUALIFIER+",");
		sb.append(Constants.TEXT_QUALIFIER+"Invoice No"+Constants.TEXT_QUALIFIER+",");
		sb.append(Constants.TEXT_QUALIFIER+"Billing Contact"+Constants.TEXT_QUALIFIER+",");
		sb.append(Constants.TEXT_QUALIFIER+"Division Code"+Constants.TEXT_QUALIFIER+",");
		sb.append(Constants.TEXT_QUALIFIER+"Division Name"+Constants.TEXT_QUALIFIER+",");
		sb.append(Constants.TEXT_QUALIFIER+"Department Code"+Constants.TEXT_QUALIFIER+",");
		sb.append(Constants.TEXT_QUALIFIER+"Department Name"+Constants.TEXT_QUALIFIER+",");
		sb.append(Constants.TEXT_QUALIFIER+"Vehicle Type"+Constants.TEXT_QUALIFIER+",");
		sb.append(Constants.TEXT_QUALIFIER+"Trip Type"+Constants.TEXT_QUALIFIER+",");
		sb.append(Constants.TEXT_QUALIFIER+"Passenger"+Constants.TEXT_QUALIFIER+",");
		sb.append(Constants.TEXT_QUALIFIER+"Flight Info"+Constants.TEXT_QUALIFIER+",");
		sb.append(Constants.TEXT_QUALIFIER+"Book By"+Constants.TEXT_QUALIFIER+",");
		sb.append(Constants.TEXT_QUALIFIER+"Booking Ref"+Constants.TEXT_QUALIFIER+",");
		sb.append(Constants.TEXT_QUALIFIER+"Surcharge"+Constants.TEXT_QUALIFIER+",");
		sb.append(Constants.TEXT_QUALIFIER+"Trip Code"+Constants.TEXT_QUALIFIER+",");
		sb.append(Constants.TEXT_QUALIFIER+"Trip Code Reason"+Constants.TEXT_QUALIFIER+",");
		sb.append(Constants.TEXT_QUALIFIER+"Card Holder Email"+Constants.TEXT_QUALIFIER+",");
		sb.append(Constants.TEXT_QUALIFIER+"Employee ID"+Constants.TEXT_QUALIFIER+",");
		
		sb.append("\n");
		
		//Data
		List<Object[]> rowsOfData = this.businessHelper.getReportBusiness().getSoftCopyInvoiceAndTripsDetailByTransactionStatus(accountNo, accountNameInput, cardNo, txnStatus, productTypeId, tripStartDate, tripEndDate, sortBy);
		int serialNoCounter = 0;
		
		for(Object[] columnDataObject : rowsOfData){
			serialNoCounter++;
			sb.append(""+Constants.TEXT_QUALIFIER+serialNoCounter+Constants.TEXT_QUALIFIER+",");
			String cardHolderName = "";
			String issuedTo = "";
			String oneTimeUsage = "";
			
			HashMap mapSurchargeAll = new HashMap();
			mapSurchargeAll = (HashMap) mapSurchargeAllInitial.clone();
			BigDecimal surchargeFare = new BigDecimal("0.00");
			BigDecimal hiddenSurchargeFare = new BigDecimal("0.00");
			BigDecimal meterFare = new BigDecimal("0.00");

			String errorSurcharge = "";
			
			for(int i=0; i<columnDataObject.length; i++){
				Object data = columnDataObject[i];
				
				if(i==21){
					i++;
					Object data2 = columnDataObject[i];
					if(data!=null && data2!=null) sb.append(""+Constants.TEXT_QUALIFIER+data.toString().replaceAll("\"", "\"\"")+" / "+data2.toString().replaceAll("\"", "\"\"")+Constants.TEXT_QUALIFIER+",");
					else if(data!=null) sb.append(""+Constants.TEXT_QUALIFIER+data.toString().replaceAll("\"", "\"\"")+Constants.TEXT_QUALIFIER+",");
					else sb.append(Constants.TEXT_QUALIFIER+Constants.TEXT_QUALIFIER+",");
				}
				else if(i==1 || i==2 || i==3){
					
					//if 1 == card holder name , dont do anything
					if(i==1){
						if(data != null)
							cardHolderName = data.toString();
					}
					else if(i==2) {
						if(data != null)
							oneTimeUsage = data.toString();
					}
					else if(i == 3) {
						
						if(data != null)
							issuedTo = data.toString();
						
						//IF PREPAID 
						//	The Card holder Name reflects the Name of the product Type 
						//	and Issued To column will display Blank if the card was not assigned to another card holder
						if(oneTimeUsage.trim().equals("Y"))
						{
							sb.append(""+Constants.TEXT_QUALIFIER+cardHolderName.replaceAll("\"", "\"\"")+Constants.TEXT_QUALIFIER+",");
							sb.append(""+Constants.TEXT_QUALIFIER+issuedTo.replaceAll("\"", "\"\"")+Constants.TEXT_QUALIFIER+",");
						}
						else {
						//NOT PREPAID
							//if issuedTo not blank, CardHolderName == issuedTo
							if(!"".equals(issuedTo.trim())) {
								sb.append(""+Constants.TEXT_QUALIFIER+issuedTo.replaceAll("\"", "\"\"")+Constants.TEXT_QUALIFIER+",");
								sb.append(""+Constants.TEXT_QUALIFIER+issuedTo.replaceAll("\"", "\"\"")+Constants.TEXT_QUALIFIER+",");
							}
							//if issuedTo is blank, issuedTo == CardHolderName
							else if("".equals(issuedTo.trim())) {
								sb.append(""+Constants.TEXT_QUALIFIER+cardHolderName.replaceAll("\"", "\"\"")+Constants.TEXT_QUALIFIER+",");
								sb.append(""+Constants.TEXT_QUALIFIER+cardHolderName.replaceAll("\"", "\"\"")+Constants.TEXT_QUALIFIER+",");
							}
						}	
					}
				}
				else if(i == 11 || i == 12 ) {
					
					if(i==11) {
						meterFare = new BigDecimal(data.toString());
					}	
					if(i==12) {
						//Read the whole surcharge field   eg abkd:10.00;pkhc:5.00
							
						if(data != null)
						{
							try {
							String surchargeValue = data.toString();
							String[] surchargeParts = surchargeValue.split("\\s+");
								try {
									for(int x = 0; x < surchargeParts.length; x++) {
										String[] surchargeEachPart = surchargeParts[x].split(":");
										String surchargeEachPartName = surchargeEachPart[0];
										String surchargeEachPartValue = surchargeEachPart[1];
		
										String surchargeGroup = "";
										if(null != surchargeHMap.get(surchargeEachPartName))
											surchargeGroup = surchargeHMap.get(surchargeEachPartName).toString();
										
										if(mapSurchargeAll.containsKey(surchargeGroup)) {
											BigDecimal currentSurcharge = (BigDecimal) mapSurchargeAll.get(surchargeGroup);
											currentSurcharge = currentSurcharge.add(new BigDecimal(surchargeEachPartValue));
											mapSurchargeAll.put(surchargeGroup, currentSurcharge);
											surchargeFare = surchargeFare.add(new BigDecimal(surchargeEachPartValue));
										}
										else
											hiddenSurchargeFare = hiddenSurchargeFare.add(new BigDecimal(surchargeEachPartValue));
											//Still track dontknow for what.. maybe future got use.
									}
								}catch(Exception e) {
									errorSurcharge = "Error in Surcharge Code";
									System.out.println("Error in Surcharge Code");
								}
							} catch(Exception e) {
								System.out.println("Error in WHOLE Surcharge Code");
							}
						}
						
						// 23/12/2016 Jheneffer say meter fare downloaded alr got surcharge, so must subtract
						// but hidden surcharge still add back in.
						BigDecimal rightMeterFare = meterFare.subtract(surchargeFare);
						
						sb.append(Constants.TEXT_QUALIFIER+rightMeterFare+Constants.TEXT_QUALIFIER+",");
						//new extra column for #104. & order by the 01,02,03,04,05 etc
						if(!hValueMap.isEmpty()) {
							SortedSet<String> keys2 = new TreeSet<String>(hValueMap.keySet());
							for (String key2 : keys2) {
								BigDecimal surchargeToPut = new BigDecimal("0");
								if(null != mapSurchargeAll.get(hValueMap.get(key2)))
									surchargeToPut = (BigDecimal) mapSurchargeAll.get(hValueMap.get(key2));
								sb.append(Constants.TEXT_QUALIFIER+surchargeToPut+Constants.TEXT_QUALIFIER+",");
							}
						}

						// 23/12/2016 so total Fare == the meter fare downloaded
						sb.append(""+Constants.TEXT_QUALIFIER+(meterFare.toString()).replaceAll("\"", "\"\"")+Constants.TEXT_QUALIFIER+",");
					}
				}
				else if(i==0 || i==4 || i==23 || i==25 || i==33){
					if(data!=null) sb.append(""+Constants.TEXT_QUALIFIER+"'"+data.toString().replaceAll("\"", "\"\"")+Constants.TEXT_QUALIFIER+",");
					else sb.append(Constants.TEXT_QUALIFIER+Constants.TEXT_QUALIFIER+",");
				}
				else{
					if(data!=null) sb.append(""+Constants.TEXT_QUALIFIER+data.toString().replaceAll("\"", "\"\"")+Constants.TEXT_QUALIFIER+",");
					else sb.append(Constants.TEXT_QUALIFIER+Constants.TEXT_QUALIFIER+",");
				}
				
			}
//			if(!errorSurcharge.trim().equals(""))
//				sb.append(""+Constants.TEXT_QUALIFIER+(errorSurcharge.toString()).replaceAll("\"", "\"\"")+Constants.TEXT_QUALIFIER+",");
		
			sb.append("\n");
		}
		
		if(rowsOfData.size()==0){
			sb.append(Constants.TEXT_QUALIFIER+"No records found"+Constants.TEXT_QUALIFIER+",");
			sb.append("\n");
		}
		
		return sb;
	}
	
	public void clearAccountNameComboBox(){
		Combobox accountNameComboBox = (Combobox)this.getFellow("accountNameComboBox");
		accountNameComboBox.getChildren().clear();
		accountNameComboBox.setText("");
	}
	
	public void reset(){
		((Intbox)this.getFellow("accountNoIntBox")).setText("");
		this.clearAccountNameComboBox();
		this.setDepartmentInputInvisible();
		this.setDivisionInputInvisible();
		Datebox tripStartDateBox = (Datebox)this.getFellow("tripStartDateBox");
		tripStartDateBox.setConstraint("");
		tripStartDateBox.setText("");
		tripStartDateBox.setConstraint(new RequiredConstraint());
		((Datebox)this.getFellow("tripEndDateBox")).setText("");
		((Listbox)this.getFellow("productTypeListBox")).setSelectedIndex(0);
		((Listbox)this.getFellow("transactionStatusListBox")).setSelectedIndex(1);
		((Decimalbox)this.getFellow("cardNoDecimalBox")).setText("");
		((Listbox)this.getFellow("sortByList")).setSelectedIndex(0);
	}
	
	@Override
	public void refresh() throws InterruptedException {
		// TODO Auto-generated method stub
	}
}