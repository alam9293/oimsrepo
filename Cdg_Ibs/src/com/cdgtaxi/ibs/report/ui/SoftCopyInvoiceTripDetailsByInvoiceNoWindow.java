package com.cdgtaxi.ibs.report.ui;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

import org.apache.commons.httpclient.HttpException;
import org.apache.log4j.Logger;
import org.zkoss.util.media.AMedia;
import org.zkoss.zhtml.Messagebox;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zk.ui.ext.AfterCompose;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Comboitem;
import org.zkoss.zul.Filedownload;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Longbox;
import org.zkoss.zul.Row;

import com.cdgtaxi.ibs.common.NonConfigurableConstants;
import com.cdgtaxi.ibs.common.model.AmtbAccount;
import com.cdgtaxi.ibs.common.ui.CommonWindow;
import com.cdgtaxi.ibs.master.model.MstbReportFormatMap;
import com.cdgtaxi.ibs.report.Constants;
import com.cdgtaxi.ibs.util.ComponentUtil;
import com.cdgtaxi.ibs.util.DateUtil;
import com.cdgtaxi.ibs.util.StringUtil;
import com.cdgtaxi.ibs.web.component.Datebox;
import com.elixirtech.net.NetException;

public class SoftCopyInvoiceTripDetailsByInvoiceNoWindow extends CommonWindow implements AfterCompose{
	
	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(SoftCopyInvoiceTripDetailsByInvoiceNoWindow.class);
	
	private Long reportRsrcId;
	
	public SoftCopyInvoiceTripDetailsByInvoiceNoWindow() throws IOException{
		reportRsrcId = new Long(this.getHttpServletRequest().getParameter("rsrcId"));
	}
	
	public void afterCompose() {
		//populate typeList
		Listbox sortByList = (Listbox)this.getFellow("sortByList");
		sortByList.appendChild(new Listitem("TRIP DATE", "1"));
		sortByList.appendChild(new Listitem("CARD NO.", "2"));
		sortByList.setSelectedIndex(0);
		
		//populate typeList
		Listbox chargeToListBox = (Listbox)this.getFellow("chargeToListBox");
		chargeToListBox.appendChild(new Listitem("NO", "N"));
		chargeToListBox.appendChild(new Listitem("YES", "Y"));
		chargeToListBox.setSelectedIndex(0);
	}
	
	public void onSelectAccountName() throws InterruptedException{
		logger.info("");

		try{
			Combobox accountNameComboBox = (Combobox)this.getFellow("accountNameComboBox");
			Intbox accountNoIntBox = (Intbox)this.getFellow("accountNoIntBox");

			if(accountNameComboBox.getSelectedItem()!=null){
				AmtbAccount selectedAccount = (AmtbAccount)accountNameComboBox.getSelectedItem().getValue();
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
		if(name.length()<3) {
			return;
		}

		//accountName still the same as selected one, skip
		if(accountNameComboBox.getSelectedItem()!=null){
			AmtbAccount selectedAccount = (AmtbAccount)accountNameComboBox.getSelectedItem().getValue();
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
			List<AmtbAccount> accounts = this.businessHelper.getPaymentBusiness().searchBillableAccount(null, name);
			for(AmtbAccount account : accounts){
				Comboitem item = new Comboitem(account.getAccountName()+" ("+account.getCustNo()+")");
				item.setValue(account);
				accountNameComboBox.appendChild(item);
			}
			if(accounts.size()==1){
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
		((Row)this.getFellow("divisionRow")).setVisible(true);
		Listbox divisionListBox = (Listbox)this.getFellow("divisionListBox");
		divisionListBox.getChildren().clear();
		divisionListBox.appendChild(ComponentUtil.createNotRequiredListItem());

		for(AmtbAccount division : divisions){
			Listitem newItem = new Listitem(division.getAccountName()+" ("+division.getCode()+")");
			newItem.setValue(division);
			divisionListBox.appendChild(newItem);
		}
	}

	private void setDepartmentInputVisible(List<AmtbAccount> departments){
		((Row)this.getFellow("departmentRow")).setVisible(true);
		Listbox departmentListBox = (Listbox)this.getFellow("departmentListBox");
		departmentListBox.getChildren().clear();
		departmentListBox.appendChild(ComponentUtil.createNotRequiredListItem());

		for(AmtbAccount department : departments){
			Listitem newItem = new Listitem(department.getAccountName()+" ("+department.getCode()+")");
			newItem.setValue(department);
			departmentListBox.appendChild(newItem);
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
	
	@SuppressWarnings("unchecked")
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
		this.onSelectReportFormat(listbox);
	}
	
	public void generate() throws HttpException, IOException, InterruptedException, NetException, WrongValueException {
		logger.info("");
		
		String accountNo = "";
		String invoiceMonthYear = "";
		String invoiceNo = "";
		String sortBy = "";
		String chargeTo = "";
		
		//selected account
		Combobox accountNameComboBox = (Combobox)this.getFellow("accountNameComboBox");
		if(accountNameComboBox.getSelectedItem()==null) {
			throw new WrongValueException(accountNameComboBox, "* Mandatory field");
		}
		accountNo = ((AmtbAccount)accountNameComboBox.getSelectedItem().getValue()).getAccountNo().toString();
		//division
		Listbox divisionListBox = (Listbox)this.getFellow("divisionListBox");
		if(divisionListBox.getSelectedItem()!=null){
			if(!(divisionListBox.getSelectedItem().getValue() instanceof String)) {
				accountNo = ((AmtbAccount)divisionListBox.getSelectedItem().getValue()).getAccountNo().toString();
			}
		}
		//department
		Listbox departmentListBox = (Listbox)this.getFellow("departmentListBox");
		if(departmentListBox.getSelectedItem()!=null){
			if(!(departmentListBox.getSelectedItem().getValue() instanceof String)) {
				accountNo = ((AmtbAccount)departmentListBox.getSelectedItem().getValue()).getAccountNo().toString();
			}
		}
		
		Datebox invoiceMonthYearDateBox = (Datebox)this.getFellow("invoiceMonthYearDateBox");
		invoiceMonthYear = invoiceMonthYearDateBox.getText();
		
		Longbox invoiceNoLongBox = (Longbox)this.getFellow("invoiceNoLongBox");
		invoiceNo = invoiceNoLongBox.getText();
		
		Listbox sortByList = (Listbox)this.getFellow("sortByList");
		sortBy = sortByList.getSelectedItem().getValue().toString();
		
		if(invoiceNo.length()==0 && invoiceMonthYear.length()==0){
			Messagebox.show("Either \"Invoice Month & Year\" or \"Invoice No.\" must have an input",
					"Invalid Input", Messagebox.OK, Messagebox.ERROR);
			return;
		}
		
		Listbox chargeToListBox = (Listbox)this.getFellow("chargeToListBox");
		chargeTo = chargeToListBox.getSelectedItem().getValue().toString();
		
		//retrieve format
		Listbox formatList = (Listbox)this.getFellow("reportFormat");
		if(formatList.getSelectedItem()==null) throw new WrongValueException(formatList, "* Mandatory field");
		String extension = (String)formatList.getSelectedItem().getLabel();
		String format = (String)formatList.getSelectedItem().getValue();

		this.displayProcessing();
		
		if(format.equals(Constants.FORMAT_CUSTOMIZE)){
			StringBuffer dataInFlatFile = this.generateFlatFileData(accountNo, invoiceMonthYear, invoiceNo, sortBy);
			
			if(dataInFlatFile.toString().length()==0){
				Messagebox.show("There are no records generate from the given input",
						"No records", Messagebox.OK, Messagebox.INFORMATION);
				return;
			}
			
			String fileName = (dataInFlatFile.substring(2,23)).trim();
			AMedia media = new AMedia(fileName, "", "application/download", dataInFlatFile.toString());
			Filedownload.save(media);
		}
		else if(format.equals(Constants.FORMAT_CSV)){
			StringBuffer dataInCSV = this.generateCSVData(accountNo, invoiceMonthYear, invoiceNo, chargeTo, sortBy);
			AMedia media = new AMedia("Soft_Copy_Invoice_And_Trips_Detail_By_Invoice_No"+"."+extension, extension, format, dataInCSV.toString());
			Filedownload.save(media);
		}
	}

	private StringBuffer generateCSVData(String accountNo, String invoiceMonthYear, 
			String invoiceNo, String chargeTo, String sortBy){
				
			StringBuffer sb = new StringBuffer();
			
			//Report Header
			sb.append(Constants.TEXT_QUALIFIER+"Integrated Billing System"+Constants.TEXT_QUALIFIER+",");
			sb.append("\n");
			sb.append(Constants.TEXT_QUALIFIER+"Soft Copy Invoice"+Constants.TEXT_QUALIFIER+",");
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
			if(invoiceMonthYear!=null && invoiceMonthYear.length()>0){
				sb.append(Constants.TEXT_QUALIFIER+"Invoice Month & Year: "+invoiceMonthYear+Constants.TEXT_QUALIFIER+",");
				sb.append("\n");
			}
			if(invoiceNo!=null && invoiceNo.length()>0){
				sb.append(Constants.TEXT_QUALIFIER+"Invoice No: "+invoiceNo+Constants.TEXT_QUALIFIER+",");
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
			
			//Column Title
			sb.append(Constants.TEXT_QUALIFIER+"S/N"+Constants.TEXT_QUALIFIER+",");
			sb.append(Constants.TEXT_QUALIFIER+"Card No / Job Type"+Constants.TEXT_QUALIFIER+",");
			sb.append(Constants.TEXT_QUALIFIER+"Card Holder Name"+Constants.TEXT_QUALIFIER+",");
			sb.append(Constants.TEXT_QUALIFIER+"Issued To"+Constants.TEXT_QUALIFIER+",");
			sb.append(Constants.TEXT_QUALIFIER+"Job No"+Constants.TEXT_QUALIFIER+",");
			sb.append(Constants.TEXT_QUALIFIER+"Travel Date"+Constants.TEXT_QUALIFIER+",");
			sb.append(Constants.TEXT_QUALIFIER+"Travel Time"+Constants.TEXT_QUALIFIER+",");
			sb.append(Constants.TEXT_QUALIFIER+"Taxi No"+Constants.TEXT_QUALIFIER+",");
			sb.append(Constants.TEXT_QUALIFIER+"Pickup Address"+Constants.TEXT_QUALIFIER+",");
			sb.append(Constants.TEXT_QUALIFIER+"Destination"+Constants.TEXT_QUALIFIER+",");
			sb.append(Constants.TEXT_QUALIFIER+"Taxi Fare($)"+Constants.TEXT_QUALIFIER+",");
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
			List<Object[]> rowsOfData = this.businessHelper.getReportBusiness().getSoftCopyInvoiceAndTripsDetailByInvoiceNoCSV(accountNo, invoiceMonthYear, invoiceNo, chargeTo, sortBy);
			int serialNoCounter = 0;
			for(Object[] columnDataObject : rowsOfData){
				serialNoCounter++;
				sb.append(""+Constants.TEXT_QUALIFIER+serialNoCounter+Constants.TEXT_QUALIFIER+",");
				String cardHolderName = "";
				String issuedTo = "";
				
				for(int i=0; i<columnDataObject.length; i++){
					Object data = columnDataObject[i];
					
					if(i==17){
						i++;
						Object data2 = columnDataObject[i];
						if(data!=null && data2!=null) sb.append(""+Constants.TEXT_QUALIFIER+data.toString().replaceAll("\"", "\"\"")+" / "+data2.toString().replaceAll("\"", "\"\"")+Constants.TEXT_QUALIFIER+",");
						else if(data!=null) sb.append(""+Constants.TEXT_QUALIFIER+data.toString().replaceAll("\"", "\"\"")+Constants.TEXT_QUALIFIER+",");
						else sb.append(Constants.TEXT_QUALIFIER+Constants.TEXT_QUALIFIER+",");
					}
					else if(i==1 || i==2){
						
						//if 1 == card holder name , dont do anything
						if(i==1){
							if(data != null)
								cardHolderName = data.toString();
						}
						else if(i == 2) {
							
							if(data != null)
								issuedTo = data.toString();
							
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
					else if(i==0 || i==3 || i==19 || i==21 || i ==30){
						if(data!=null) sb.append(""+Constants.TEXT_QUALIFIER+"'"+data.toString().replaceAll("\"", "\"\"")+Constants.TEXT_QUALIFIER+",");
						else sb.append(Constants.TEXT_QUALIFIER+Constants.TEXT_QUALIFIER+",");
					}
					else{
						if(data!=null) sb.append(""+Constants.TEXT_QUALIFIER+data.toString().replaceAll("\"", "\"\"")+Constants.TEXT_QUALIFIER+",");
						else sb.append(Constants.TEXT_QUALIFIER+Constants.TEXT_QUALIFIER+",");
					}
				}
				
				sb.append("\n");
			}
			
			if(rowsOfData.size()==0){
				sb.append(Constants.TEXT_QUALIFIER+"No records found"+Constants.TEXT_QUALIFIER+",");
				sb.append("\n");
			}
			
			return sb;
		}
	
	private StringBuffer generateFlatFileData(String accountNo, String invoiceMonthYear, String invoiceNo, String sortBy){
		StringBuffer sb = new StringBuffer();
		
		String fileName = "IBSINV";
		int txnCount = 0;
		BigDecimal totalTxnAmount = BigDecimal.ZERO;
		
		List<Object[]> rowsOfData = this.businessHelper.getReportBusiness().getSoftCopyInvoiceAndTripsDetailByInvoiceNoCustomize(accountNo, invoiceMonthYear, invoiceNo, sortBy);
		if(rowsOfData.size()==0) return sb;
		
		//Header Record
		Object[] dataForHeaderRecord = rowsOfData.get(0);
		fileName += ((String)dataForHeaderRecord[1]).trim(); //+invoice no
		fileName = StringUtil.appendRight(fileName, 21, " ");
		String fileCreatedDate = DateUtil.convertDateToStr(DateUtil.getCurrentDate(), "ddMMyyyy");
		String headerRecord = "HR"+fileName+fileCreatedDate+"\r\n";
		sb.append(headerRecord);
		
		//Data Record
		for(Object[] data : rowsOfData){
			
			String recordType 		= (String)data[0];
			String invoiceNoStr 	= (String)data[1];
			String invoiceDate 		= (String)data[2];
			String code				= (String)data[3];
			String cardNo 			= (String)data[4];
			String taxiNo 			= (String)data[5];
			String tripDate 		= (String)data[6];
			String tripTime 		= (String)data[7];
			String fareAmount 		= (String)data[8];
			String pickup 			= (String)data[9];
			String destination 		= (String)data[10];
			String jobNo 			= (String)data[11];
			String projectCode 		= (String)data[12];
			String tripCodeReason 	= (String)data[13];
			String cardHolderEmail 	= (String)data[14];
			String employeeId	 	= (String)data[15];
			
			sb.append(recordType);
			sb.append(StringUtil.appendLeft(invoiceNoStr, 15, " "));
			sb.append(invoiceDate);
			sb.append(StringUtil.appendLeft(code==null?"":code, 4, " "));
			sb.append(StringUtil.appendLeft(cardNo==null?"":cardNo, 16, " "));
			sb.append(StringUtil.appendLeft(taxiNo, 10, " "));
			sb.append(tripDate);
			sb.append(" "+tripTime);
			sb.append("59"); //Constant
			if(fareAmount.length()==7) sb.append(" "); //length of 7 means is positive no
			sb.append(fareAmount);
			sb.append(StringUtil.appendRight(pickup, 100, " "));
			sb.append(StringUtil.appendRight(destination, 100, " "));
			sb.append(StringUtil.appendLeft(jobNo, 30, " "));
			sb.append(StringUtil.appendLeft(projectCode, 80, " "));
			sb.append(StringUtil.appendLeft(tripCodeReason, 80, " "));
			sb.append(StringUtil.appendLeft(cardHolderEmail, 80, " "));
			sb.append(StringUtil.appendLeft(employeeId, 80, " "));
			sb.append("                  ");
			sb.append("\r\n");
			
			totalTxnAmount = totalTxnAmount.add(new BigDecimal(fareAmount.trim()));
			txnCount++;
		}
		
		//Trailer Record
		String totalTxnAmountStr = "";
		if(totalTxnAmount.compareTo(BigDecimal.ZERO)==-1) totalTxnAmountStr += "-";
		totalTxnAmountStr += StringUtil.appendLeft(""+totalTxnAmount.setScale(2).abs(), 10, "0");
		String trailerRecord = "TR"+fileName+StringUtil.appendLeft(""+txnCount, 6, "0")+totalTxnAmountStr+"\r\n";
		sb.append(trailerRecord);
		
		return sb;
	}
	
	public void clearAccountNameComboBox(){
		Combobox accountNameComboBox = (Combobox)this.getFellow("accountNameComboBox");
		accountNameComboBox.getChildren().clear();
		accountNameComboBox.setText("");
	}
	
	public void onSelectReportFormat(Listbox reportFormatListBox){
		String reportFormat = reportFormatListBox.getSelectedItem().getValue().toString();
		if(reportFormat.equals(Constants.FORMAT_CSV))
			((Listbox)this.getFellow("chargeToListBox")).setDisabled(false);
		else
			((Listbox)this.getFellow("chargeToListBox")).setDisabled(true);
	}
	
	public void reset(){
		((Intbox)this.getFellow("accountNoIntBox")).setText("");
		this.clearAccountNameComboBox();
		this.setDepartmentInputInvisible();
		this.setDivisionInputInvisible();
		((Datebox)this.getFellow("invoiceMonthYearDateBox")).setText("");
		((Longbox)this.getFellow("invoiceNoLongBox")).setText("");
		((Listbox)this.getFellow("sortByList")).setSelectedIndex(0);
	}
	
	@Override
	public void refresh() throws InterruptedException {
		// TODO Auto-generated method stub
	}
}