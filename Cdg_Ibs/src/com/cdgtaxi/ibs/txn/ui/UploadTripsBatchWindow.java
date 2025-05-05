package com.cdgtaxi.ibs.txn.ui;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.orm.hibernate3.HibernateOptimisticLockingFailureException;
import org.zkoss.util.media.Media;
import org.zkoss.zk.ui.Components;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zk.ui.ext.AfterCompose;
import org.zkoss.zk.ui.metainfo.EventHandler;
import org.zkoss.zk.ui.metainfo.ZScript;
import org.zkoss.zk.ui.util.ConditionImpl;
import org.zkoss.zul.Fileupload;
import org.zkoss.zul.Image;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Radio;

import com.cdgtaxi.ibs.common.ConfigurableConstants;
import com.cdgtaxi.ibs.common.NonConfigurableConstants;
import com.cdgtaxi.ibs.common.exception.ProcessUploadTripBatchException;
import com.cdgtaxi.ibs.common.model.AmtbAccount;
import com.cdgtaxi.ibs.common.model.PmtbProduct;
import com.cdgtaxi.ibs.common.model.PmtbProductType;
import com.cdgtaxi.ibs.common.model.TmtbAcquireTxn;
import com.cdgtaxi.ibs.common.model.email.FileAttachment;
import com.cdgtaxi.ibs.common.ui.CommonWindow;
import com.cdgtaxi.ibs.master.model.MstbMasterTable;
import com.cdgtaxi.ibs.util.ComponentUtil;
import com.cdgtaxi.ibs.util.DateUtil;
import com.cdgtaxi.ibs.web.component.Constants;

@SuppressWarnings("unchecked")
public class UploadTripsBatchWindow extends CommonWindow implements AfterCompose {

	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(UploadTripsBatchWindow.class);

	private Radio radioWithCard, radioWithCardless;
	private Listbox attachmentLB;
	
	private Map<byte[], String> attachmentFile = new HashMap();
	
	private List<TmtbAcquireTxn> successTxn = new ArrayList<TmtbAcquireTxn>();
	private List<String> failTxn = new ArrayList<String>();
	
	private SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
	private SimpleDateFormat formatter2 = new SimpleDateFormat("dd/MM/yyyy");
	private String cvsSplitBy = ",";
	
	private static final String DATE_FORMAT = "dd/MM/yyyy";
	private static final String TIME_FORMAT = "HH:mm:ss";
	private InputStream inputStream = null;
	private String contentType = "";
	
	public void afterCompose() {
		
		Components.wireVariables(this, this);
		
		radioWithCardless.setChecked(true);
	
	}
	
	public void uploadCsvField() throws InterruptedException {
		try {

			Media media = Fileupload.get("Please choose a CSV file...", "Upload Trips via CSV");

			if (media != null) {

			   String[] filename = media.getName().split("\\.(?=[^\\.]+$)"); //split filename from it's extension

				if (!media.getContentType().equals("application/vnd.ms-excel")) {
					throw new WrongValueException("Only CSV file can be accepted!");
				} 
				else if (!filename[1].equalsIgnoreCase("csv")) {
					throw new WrongValueException("Only CSV file can be accepted!");
				}
				else {
					byte[] bytes;
					bytes = media.getByteData();
			
					File file = File.createTempFile("IBS", "");
					FileOutputStream outputStream = new FileOutputStream(file);
					outputStream.write(bytes);
					logger.info("Temp File: " + file.getAbsolutePath());
					
					this.appendFileAttachmentToListbox(new FileAttachment(media.getName(), file), bytes);
				}
			}
		} catch (WrongValueException wve) {
			throw wve;
		} catch (Exception e) {
			e.printStackTrace();
			Messagebox.show(com.cdgtaxi.ibs.common.NonConfigurableConstants.COMMON_ERROR_MESSAGE, "Error",
					Messagebox.OK, Messagebox.ERROR);
		}
	}
	
	private String getCellValue(Cell cell) {
	    switch (cell.getCellType()) {
	    case Cell.CELL_TYPE_STRING:
	        return cell.getStringCellValue();
	 
	    case Cell.CELL_TYPE_BOOLEAN:	    	
	        return String.valueOf(cell.getBooleanCellValue());
	 
	    case Cell.CELL_TYPE_NUMERIC:
	    	double value = cell.getNumericCellValue();
	    	//check if value is to big to be integer AND if value is more then 16 digit will be cut of by excel
	    	if (value > Integer.MAX_VALUE){
	    		if (String.valueOf((long)cell.getNumericCellValue()).length() > 15)
	    			return String.valueOf(-1);
	    		else
	    			return String.valueOf((long)cell.getNumericCellValue());    	
	    	}
	    		
	    	if (((value == Math.floor(value)) && !Double.isInfinite(value)))
	    		return String.valueOf((int)cell.getNumericCellValue());
	    	else
	    		return String.valueOf(cell.getNumericCellValue());
	        
	    }
	 
	    return null;
	}
	
	public void uploadXlsField() throws InterruptedException {
		try{
			Media media = Fileupload.get("Please choose a XLS file...", "Upload Trips via XLS");
			if (media != null) {
				 String[] filename = media.getName().split("\\.(?=[^\\.]+$)"); //split filename from it's extension
				 if(filename[(filename.length -1)].equals("xls") || filename[(filename.length-1)].equals("xlsx") ){											
					inputStream = media.getStreamData();	
					contentType = media.getContentType();
					byte[] bytes;
					bytes = media.getByteData();
			
					File file = File.createTempFile("IBS", "");
					logger.info("Temp File: " + file.getAbsolutePath());

					this.appendFileAttachmentToListbox(new FileAttachment(media.getName(), file), bytes);
				 }else{
					 throw new WrongValueException("Only XLS file can be accepted!");
				 }
			}
			
		} catch (WrongValueException wve) {
			throw wve;
		} catch (Exception e) {
			e.printStackTrace();
			Messagebox.show(com.cdgtaxi.ibs.common.NonConfigurableConstants.COMMON_ERROR_MESSAGE, "Error",
					Messagebox.OK, Messagebox.ERROR);
		}
	}
	
	public void uploadXls() throws InterruptedException, IOException{
		logger.info("");
		this.displayProcessing();
		try {
			
			int successCnt = 0;			
			String cardlessYesNo = "";
			if(radioWithCard.isChecked()) cardlessYesNo = "N";
			else if(radioWithCardless.isChecked()) cardlessYesNo = "Y";
			
			if(inputStream != null) {
				List<Map<String, String>> txnErrorDetailsList = new ArrayList<Map<String, String>>();	
			 
				Workbook workbook = null;
			    if (contentType.equals("application/vnd.ms-excel")){
			    	workbook = (Workbook) new HSSFWorkbook(inputStream);
			    }else if (contentType.equalsIgnoreCase("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet")){
			    	workbook = (Workbook) new XSSFWorkbook(inputStream);
			    }
			    Sheet firstSheet = (Sheet) workbook.getSheetAt(0);
			    Iterator<Row> iterator = firstSheet.iterator();
			    int lastColumn = 0;
			    while (iterator.hasNext() ) {
			    	
			    	// remove first row as it should be header and also check if the total column cannot be less then 37
			    	Row currentRow = iterator.next();			    	
			    	if(currentRow.getRowNum() == 0){			    	
			    		lastColumn = Math.max(currentRow.getLastCellNum(), 37);
			    		continue;
			    	}	
			    	
			    	//break out/stop if the first cell (S/N) is blank/empty			    	
			    	if(currentRow.getCell(0, Row.RETURN_BLANK_AS_NULL) == null ){
			    		break;
			    	}
			    	
	                Map<String, String> txnDetails = new HashMap<String, String>();
	                ArrayList<String> arraytxn = new ArrayList<String>();

	                try{
		                for (int cn = 0; cn < lastColumn; cn++){
		                	Cell currentCell = currentRow.getCell(cn, Row.RETURN_BLANK_AS_NULL);
		                	
		                	// insert null value as blank and parsing of date and time
		                	if (currentCell == null) {
		                		arraytxn.add("");
		                	}else{
		                		if(radioWithCardless.isChecked()){
			                		switch(cn){
					                	case 9:
					                		try {
					                			arraytxn.add(String.valueOf(new SimpleDateFormat(DATE_FORMAT).format(currentCell.getDateCellValue())));
					                		} catch(Exception e) { throw new ProcessUploadTripBatchException("Trip Start Date alpha format is wrong. "+getCellValue(currentCell)); }
					                		break;
					                	case 11:
					                		try {
					                			arraytxn.add(String.valueOf(new SimpleDateFormat(DATE_FORMAT).format(currentCell.getDateCellValue())));
					                		} catch(Exception e) { throw new ProcessUploadTripBatchException("Trip End Date alpha format is wrong. "+getCellValue(currentCell)); }
					                		break;
					                	case 21:
					                		try {
						                		arraytxn.add(String.valueOf(new SimpleDateFormat(DATE_FORMAT).format(currentCell.getDateCellValue())));
					                		} catch(Exception e) { throw new ProcessUploadTripBatchException("Book Date alpha format is wrong. > "+getCellValue(currentCell)); }
						                		break;
					                	case 10:
					                		try {
					                			arraytxn.add(String.valueOf(new SimpleDateFormat(TIME_FORMAT).format(currentCell.getDateCellValue())));
					                		} catch(Exception e) { throw new ProcessUploadTripBatchException("Trip Start Time alpha format is wrong. > "+getCellValue(currentCell)); }
					                		break;
					                	case 12:
					                		try {
					                			arraytxn.add(String.valueOf(new SimpleDateFormat(TIME_FORMAT).format(currentCell.getDateCellValue())));
					                		} catch(Exception e) { throw new ProcessUploadTripBatchException("Trip End Time alpha format is wrong. > "+getCellValue(currentCell)); }
					                		break;
					                	default:
					                			try {
					                				arraytxn.add(getCellValue(currentCell));	 
						                		} catch(Exception e) { throw new ProcessUploadTripBatchException("This Field data's format is wrong. > "+getCellValue(currentCell)); }
			                		}
			                	}else if(radioWithCard.isChecked()){	                		
			                		switch(cn){
					                	case 11:
					                		try {
					                			arraytxn.add(String.valueOf(new SimpleDateFormat(DATE_FORMAT).format(currentCell.getDateCellValue())));
					                		} catch(Exception e) { throw new ProcessUploadTripBatchException("Trip Start Date alpha format is wrong. "+getCellValue(currentCell)); }
					                		break;
					                	case 13:
					                		try {
					                			arraytxn.add(String.valueOf(new SimpleDateFormat(DATE_FORMAT).format(currentCell.getDateCellValue())));
					                		} catch(Exception e) { throw new ProcessUploadTripBatchException("Trip End Date alpha format is wrong. "+getCellValue(currentCell)); }
					                		break;
					                	case 12:
					                		try {
					                			arraytxn.add(String.valueOf(new SimpleDateFormat(TIME_FORMAT).format(currentCell.getDateCellValue())));
					                		} catch(Exception e) { throw new ProcessUploadTripBatchException("Trip Start Time alpha format is wrong. "+getCellValue(currentCell)); }
					                		break;
					                	case 14:
					                		try {
					                			arraytxn.add(String.valueOf(new SimpleDateFormat(TIME_FORMAT).format(currentCell.getDateCellValue())));
					                		} catch(Exception e) { throw new ProcessUploadTripBatchException("Trip End Time alpha format is wrong. "+getCellValue(currentCell)); }
					                		break;
					                	default:
					                		try {
				                				arraytxn.add(getCellValue(currentCell));	 
					                		} catch(Exception e) { throw new ProcessUploadTripBatchException("This Field data's format is wrong. > "+getCellValue(currentCell)); }	                	
			                		}
			                	}else{
			            			Messagebox.show(com.cdgtaxi.ibs.common.NonConfigurableConstants.COMMON_ERROR_MESSAGE, "Error",
			            					Messagebox.OK, Messagebox.ERROR);
			                	}
		                	}		                			             	                	
		                }//end of for loop
		               String[] txn =  arraytxn.toArray(new String[arraytxn.size()]);
		               
		               if(radioWithCard.isChecked()) txnDetails = getTxnDetailsCard(txn);
			           else if(radioWithCardless.isChecked()) txnDetails = getTxnDetailsCardless(txn);
		               
		               //System.out.println("going to create batch");
			           this.businessHelper.getTxnBusiness().createBatchTripUpload(txnDetails, getUserLoginIdAndDomain(), cardlessYesNo);
			            
			           successCnt++;
	               
	                	}catch(ProcessUploadTripBatchException e){
			        		Map<String, String> txnDetailsError = new HashMap<String, String>();
			        		
			        		txnDetailsError.put("sn", arraytxn.get(0));
			        		txnDetailsError.put("Reason", e.getMessage());
			        		e.printStackTrace();
			        		
			        		StringWriter errors = new StringWriter();
			        		e.printStackTrace(new PrintWriter(errors));
			        		logger.info("Error : "+errors.toString());
			        		txnErrorDetailsList.add(txnDetailsError);
			        	}
			        	catch(Exception e) {
			        		Map<String, String> txnDetailsError = new HashMap<String, String>();
			        		
			        		txnDetailsError.put("sn", arraytxn.get(0));
			        		txnDetailsError.put("Reason", "Error, Please Check Log");
			        		e.printStackTrace();
			        		
			        		StringWriter errors = new StringWriter();
			        		e.printStackTrace(new PrintWriter(errors));
			        		 
			        		logger.info("Error : "+errors.toString());
			        	
			        		txnErrorDetailsList.add(txnDetailsError);
			        	}
			    }// end of while loop
			
			    displayFailureResult(txnErrorDetailsList);
			    Messagebox.show(successCnt +" Trip(s) uploaded successfully", "Upload Trip Batch", Messagebox.OK, Messagebox.INFORMATION);
			}else{
				Messagebox.show("No Excel file is uploaded", "Upload Trip Batch", Messagebox.OK, Messagebox.ERROR);
				return;
			}			
		} catch (WrongValueException wve) {
			throw wve;
		} catch (HibernateOptimisticLockingFailureException holfe) {
			Messagebox.show(com.cdgtaxi.ibs.common.NonConfigurableConstants.HIBERNATE_ERROR_MESSAGE, "Error",
					Messagebox.OK, Messagebox.ERROR);

			Executions.getCurrent().sendRedirect(""); // TO DO A REFRESH

			holfe.printStackTrace();
		} catch (Exception e) {
			StringWriter errors = new StringWriter();
    		e.printStackTrace(new PrintWriter(errors));
    		logger.info("Error : "+errors.toString());
			e.printStackTrace();
			Messagebox.show(com.cdgtaxi.ibs.common.NonConfigurableConstants.COMMON_ERROR_MESSAGE, "Error",
					Messagebox.OK, Messagebox.ERROR);
		}
	}

	public void uploadCsv() throws InterruptedException {
		logger.info("");
		this.displayProcessing();
		try {
			
			String cardlessYesNo = "";
			if(radioWithCard.isChecked()) cardlessYesNo = "N";
			else if(radioWithCardless.isChecked())	cardlessYesNo = "Y";
			
			if(!attachmentFile.isEmpty()) {
				for(Map.Entry<byte[], String> entry : attachmentFile.entrySet()) 
				{
					List<Map<String, String>> txnErrorDetailsList = new ArrayList<Map<String, String>>();					
				
					System.out.println("hi > "+entry.getValue());
					System.out.println("Cardless Card "+radioWithCard);
					
					InputStream is = null;
					BufferedReader bfReader = null;
					is = new ByteArrayInputStream(entry.getKey());
					bfReader = new BufferedReader(new InputStreamReader(is));
				    String temp = null;
			        int successCnt = 0;
			        
			        while((temp = bfReader.readLine()) != null)
			        {
			        	System.out.println("temp > "+temp);
				        	String[] txn = temp.split(cvsSplitBy);
				            Map<String, String> txnDetails = new HashMap<String, String>();
				        	try
				        	{
					            if(txn[0] == null)
					            	continue;
					            else if (txn[0].trim().equals(""))
					            	continue;
					            else if(txn[0].trim().equalsIgnoreCase("S/N"))
					            	continue;
					            else if(txn[1].trim().equalsIgnoreCase("Job No"))
					            	continue;
					            
					            if(radioWithCard.isChecked())
					            	txnDetails = getTxnDetailsCard(txn);
					            else if(radioWithCardless.isChecked())
					            	txnDetails = getTxnDetailsCardless(txn);
					            
//					            txnDetailsList.add(txnDetails);
					            System.out.println("going to create batch");
					            this.businessHelper.getTxnBusiness().createBatchTripUpload(txnDetails, getUserLoginIdAndDomain(), cardlessYesNo);
					            
					            successCnt++;
				        	}
				        	catch(ProcessUploadTripBatchException e){
				        		Map<String, String> txnDetailsError = new HashMap<String, String>();
				        		
				        		txnDetailsError.put("sn", txn[0]);
				        		txnDetailsError.put("Reason", e.getMessage());
				        		
				        		txnErrorDetailsList.add(txnDetailsError);
				        	}
				        	catch(Exception e) {
				        		Map<String, String> txnDetailsError = new HashMap<String, String>();
				        		
				        		txnDetailsError.put("sn", txn[0]);
				        		txnDetailsError.put("Reason", "Error, Please Check Log");
				        		e.printStackTrace();
				        		txnErrorDetailsList.add(txnDetailsError);
				        	}
			        }
				
			        displayFailureResult(txnErrorDetailsList);
			        Messagebox.show(successCnt +" Trip(s) uploaded successfully", "Upload Trip Batch", Messagebox.OK, Messagebox.INFORMATION);
					break;
					
				}
			}
			else {
				Messagebox.show("No CSV uploaded", "Upload Trip Batch", Messagebox.OK, Messagebox.ERROR);
				return;
			}

		} catch (WrongValueException wve) {
			throw wve;
		} catch (HibernateOptimisticLockingFailureException holfe) {
			Messagebox.show(com.cdgtaxi.ibs.common.NonConfigurableConstants.HIBERNATE_ERROR_MESSAGE, "Error",
					Messagebox.OK, Messagebox.ERROR);

			Executions.getCurrent().sendRedirect(""); // TO DO A REFRESH

			holfe.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
			Messagebox.show(com.cdgtaxi.ibs.common.NonConfigurableConstants.COMMON_ERROR_MESSAGE, "Error",
					Messagebox.OK, Messagebox.ERROR);
		}
	}
	
	private  Map<String, String> getTxnDetailsCardless(String[] txn ) throws ParseException
	{
		Map<String, String> txnDetails = new HashMap<String, String>();
		
		//check for big number error (ERROR CODE IS -1)
		for(String s: txn){
			if(s.equals("-1"))	
		    	  throw new ProcessUploadTripBatchException(NonConfigurableConstants.NUMBER_TO_BIG);
		}
	    
	    String jobNo = txn[1];
	    
	    if(jobNo != null && !jobNo.trim().equals(""))
	    {
	      	if(this.businessHelper.getTxnBusiness().checkJobNo(jobNo))	
		    	  throw new ProcessUploadTripBatchException(NonConfigurableConstants.DUPLICATE_JOB_NO);
	        
	      	 if(jobNo.length() > 15)
		      		throw new ProcessUploadTripBatchException("Job No should not exceed 15 char in length.");
	      	
	      	txnDetails.put("jobNo", jobNo);
	    }
	    
		String custNo = txn[2];
		AmtbAccount acct = this.businessHelper.getAccountBusiness().getAccountByCustNo(custNo);
		
		if (acct == null){
			throw new ProcessUploadTripBatchException("Account No. is not found.");
		}
		if(acct != null)
		{
			if(acct.getCustNo() == null)
				throw new ProcessUploadTripBatchException("Account No. is invalid.");
		}
		if (this.businessHelper.getTxnBusiness().isAccountClosed(acct.getCustNo().toString()))
			throw new ProcessUploadTripBatchException("The issued account for the selected card is closed");

		txnDetails.put("acctNo", custNo);
		String smallAcctNo = acct.getAccountNo().toString();
		txnDetails.put("name", acct.getAccountName());
		
		if(acct.getAccountCategory().trim().equalsIgnoreCase("CORP"))
		{
			//division
			if(txn[3]!=null && !txn[3].equals(""))
			{
				AmtbAccount divAcct = this.businessHelper.getAccountBusiness().getAccountByCustNoAndCode(custNo, txn[3], "DIV");
				if(divAcct != null && divAcct.getAccountCategory().trim().equalsIgnoreCase("DIV"))
				{
					txnDetails.put("division", divAcct.getAccountNo().toString());
					smallAcctNo = divAcct.getAccountNo().toString();
				}
				else{
					throw new ProcessUploadTripBatchException("Division Code is invalid");
					}
			}
	
			//department
			if(txn[4] !=null && !txn[4].equals("")){
				AmtbAccount deptAcct = this.businessHelper.getAccountBusiness().getAccountByCustNoAndCodeAndCode(custNo, txn[4], "DEPT", txn[3]);
				if(deptAcct != null && deptAcct.getAccountCategory().trim().equalsIgnoreCase("DEPT"))
				{
					txnDetails.put("department", deptAcct.getAccountNo().toString());
					smallAcctNo = deptAcct.getAccountNo().toString();
				}
				else
					throw new ProcessUploadTripBatchException("Department Code is invalid");
			}
		}
		else if(acct.getAccountCategory().equalsIgnoreCase("APP"))
		{
			if(txn[3]!=null && !txn[3].equals("")) 
			{
				AmtbAccount sappAcct = this.businessHelper.getAccountBusiness().getAccountByCustNoAndCode(custNo, txn[3], "SAPP");
				if(sappAcct != null && sappAcct.getAccountCategory().trim().equalsIgnoreCase("SAPP"))
				{
						txnDetails.put("subApplicant", sappAcct.getAccountNo().toString());
						smallAcctNo = sappAcct.getAccountNo().toString();
				}
				else
					throw new ProcessUploadTripBatchException("Sub Application Code is invalid");
			}
		}
		
		if(txn[5] == null || txn[5].equals(""))
			throw new ProcessUploadTripBatchException("Product Type is Mandatory");
		else
		{
			PmtbProductType pmtbProductType = this.businessHelper.getProductBusiness().getProductTypeByName(txn[5]);
			if(pmtbProductType != null)
			{
				String prodTypeValid = "N";
				List<PmtbProductType> productTypeList = this.businessHelper.getTxnBusiness().searchPremierAccountsProductTypes(acct.getCustNo(), smallAcctNo);

				if(productTypeList != null)
				{
					for(PmtbProductType prod : productTypeList)
					{
						if(prod.getName().trim().equalsIgnoreCase(pmtbProductType.getName()))
						{
							if(prod.getCardless().equalsIgnoreCase("Y")){
								prodTypeValid = "Y";
								txnDetails.put("productType", pmtbProductType.getProductTypeId());
								break;
							}
						}
					}
				}
				
				if(prodTypeValid.trim().equalsIgnoreCase("N"))
					throw new ProcessUploadTripBatchException("Product Type is Invalid for the account");
			}
			else
				throw new ProcessUploadTripBatchException("Product Type is Invalid");
		}
		
		if(txn[25] == null || txn[25].equals(""))
			throw new ProcessUploadTripBatchException("Vehicle Type is Mandatory");
		else
		{
			MstbMasterTable vehicleTypeMaster = ConfigurableConstants
					.getMasterTableByMasterValue(
							ConfigurableConstants.VEHICLE_MODEL,
							txn[25]);

			if(vehicleTypeMaster != null)
				txnDetails.put("vehicleType", vehicleTypeMaster.getMasterCode());
			else
				throw new ProcessUploadTripBatchException("Vehicle Type is Invalid");
		}
		
		if(txn[23] == null || txn[23].equals(""))
			throw new ProcessUploadTripBatchException("Job Type is Mandatory");
		else
		{
			MstbMasterTable jobTypeMaster = ConfigurableConstants
					.getMasterTableByMasterValue(
							ConfigurableConstants.JOB_TYPE,
							txn[23]);
			if(jobTypeMaster != null)
				txnDetails.put("jobType", jobTypeMaster.getMasterCode());
			else
				throw new ProcessUploadTripBatchException("Job Type is Invalid");
		}
		
		if(txn[7] == null || txn[7].equals(""))
			throw new ProcessUploadTripBatchException("Taxi No is Mandatory");
		else
			txnDetails.put("taxiNo", txn[7]);
		
		if(txn[8] == null || txn[8].equals(""))
			throw new ProcessUploadTripBatchException("Driver NRIC is Mandatory");
		else
			txnDetails.put("nric", txn[8]);
		

		if(txn[9] == null || txn[9].equals("") || txn[10] == null || txn[10].equals("") )
			throw new ProcessUploadTripBatchException(NonConfigurableConstants.MANDATORY_TRIP_START);
		else if(txn[9].contains("-"))
			throw new ProcessUploadTripBatchException("Trip Start Date contain invalid character");
		else if(txn[9].length() != 10)
			throw new ProcessUploadTripBatchException("Trip Start Date length is wrong");
		else if(txn[10].length() != 8)
			throw new ProcessUploadTripBatchException("Trip Start Time length is wrong");
		
		String tripStartDateTime = txn[9] + " " + txn[10];

		Timestamp startDate = null;
		if (tripStartDateTime != null && !tripStartDateTime.trim().equals(""))
		{
			Date startDateWithTime = formatter.parse(tripStartDateTime);
			startDate = DateUtil.convertDateToTimestamp(startDateWithTime);
		}
		
		if(startDate == null)
			throw new ProcessUploadTripBatchException(NonConfigurableConstants.MANDATORY_TRIP_START);
		
		txnDetails.put("startDate", DateUtil.convertTimestampToStr(startDate, DateUtil.TRIPS_DATE_FORMAT));
		
		
		if(txn[11] == null || txn[11].equals("") || txn[12] == null || txn[12].equals("") )
			throw new ProcessUploadTripBatchException(NonConfigurableConstants.MANDATORY_TRIP_END);
		else if(txn[11].contains("-"))
			throw new ProcessUploadTripBatchException("Trip End Date contain invalid character");
		else if(txn[11].length() != 10)
			throw new ProcessUploadTripBatchException("Trip End Date length is wrong");
		else if(txn[12].length() != 8)
			throw new ProcessUploadTripBatchException("Trip End Time length is wrong");
		
		String tripEndDateTime = txn[11] +" " + txn[12];
		Timestamp endDate = null;
		if (tripEndDateTime != null && !tripEndDateTime.trim().equals(""))
		{
			Date endDateWithTime = formatter.parse(tripEndDateTime);
			endDate = DateUtil.convertDateToTimestamp(endDateWithTime);
		}
		
		if(endDate == null)
			throw new ProcessUploadTripBatchException(NonConfigurableConstants.MANDATORY_TRIP_END);
		
		txnDetails.put("endDate", DateUtil.convertTimestampToStr(endDate, DateUtil.TRIPS_DATE_FORMAT));

		// Validation - Start Date cannot be later than end date
		if (startDate.after(endDate)) {
			throw new ProcessUploadTripBatchException("Trip Start Date cannot be earlier than Trip End Date");
		}
		
		// Add company Code
		if(txn[6] == null || txn[6].equals(""))
			throw new ProcessUploadTripBatchException("Company Code is Mandatory");
		else
		{
			MstbMasterTable companyCdMaster = ConfigurableConstants
					.getMasterTableByMasterValue(ConfigurableConstants.SERVICE_PROVIDER,
							txn[6]);
			if(companyCdMaster != null)
				txnDetails.put("companyCd", companyCdMaster.getMasterCode());
			else
				throw new ProcessUploadTripBatchException("Company Code is Invalid");
		}
		
		if(txn[13] == null || txn[13].equals(""))
			throw new ProcessUploadTripBatchException("Fare Amount is Mandatory");
		else
		{
			DecimalFormat nf = new DecimalFormat("#.00");	
			String fareAmt = nf.format(new BigDecimal(txn[13].toString()));
			txnDetails.put("fareAmt", fareAmt);
		}
		
		txnDetails.put("destination", txn[28]);
		txnDetails.put("remarks", txn[29]);
		txnDetails.put("projCode", txn[16]);
		txnDetails.put("projCodeReason", txn[17]);
		
		// checkbox for complimentary
		String complimentary = txn[14];
		if (complimentary != null && (complimentary.equalsIgnoreCase("Y") || complimentary.equalsIgnoreCase("YES")))
			txnDetails.put("complimentary", NonConfigurableConstants.BOOLEAN_YES);
		else
			txnDetails.put("complimentary", NonConfigurableConstants.BOOLEAN_NO);
		
		// Premier txn additional fields
		txnDetails.put("surchargeDesc", txn[15]);
		txnDetails.put("bookedBy", txn[20]);
		txnDetails.put("bookedRef", txn[18]);
		txnDetails.put("flightInfo", txn[19]);
		
		String tripBookedDateDate = txn[21];
		if(txn[21].contains("-"))
			throw new ProcessUploadTripBatchException("Booked Date contain invalid character");
		//else if(txn[21].length() != 10)
			//throw new ProcessUploadTripBatchException("Booked Date length is wrong");
		
		if (tripBookedDateDate != null && !"".equals(tripBookedDateDate))
			txnDetails.put("bookedDate", DateUtil.convertDateToStr(formatter2.parse(tripBookedDateDate), DateUtil.TRIPS_DATE_FORMAT));
		
		// Trip Type
		if(txn[22] == null || txn[22].equals(""))
			throw new ProcessUploadTripBatchException("Trip Type is Mandatory");
		else
		{
			MstbMasterTable vehicleMaster = ConfigurableConstants
					.getMasterTableByMasterValue(
							ConfigurableConstants.VEHICLE_TRIP_TYPE,
							txn[22]);

			if(vehicleMaster != null)
				txnDetails.put("tripType", vehicleMaster.getMasterCode());
			else
				throw new ProcessUploadTripBatchException("Trip Type is Invalid");
		}
		// Vehicle Group
		if(txn[24] == null || txn[24].equals(""))
			throw new ProcessUploadTripBatchException("Vehicle Group is Mandatory");
		else
		{
			MstbMasterTable vehicleMaster = ConfigurableConstants
					.getMasterTableByMasterValue(
							ConfigurableConstants.VEHICLE_TYPE_MASTER_CODE,
							txn[24]);
			if(vehicleMaster != null)
				txnDetails.put("vehicleGroup", vehicleMaster.getMasterCode());
			else
				throw new ProcessUploadTripBatchException("Vehicle Group is Invalid");
		}
		txnDetails.put("paxName", txn[26]);
		
		//pickup 
		if(txn[27] == null || txn[27].equals(""))
			throw new ProcessUploadTripBatchException("Pickup is Mandatory");
		else
			txnDetails.put("pickup", txn[27]);
		
		String updateFms = txn[30];
		if (updateFms != null && (updateFms.equalsIgnoreCase("Y") || updateFms.equalsIgnoreCase("YES")))
			txnDetails.put("toUpdateFMSList",  NonConfigurableConstants.BOOLEAN_YES);
		else
			txnDetails.put("toUpdateFMSList", NonConfigurableConstants.BOOLEAN_NO);
		
		if(NonConfigurableConstants.BOOLEAN_YES.equals(txnDetails.get("toUpdateFMSList"))) {

			if(txn[31] == null || txn[31].equals(""))
				throw new ProcessUploadTripBatchException("Refund/Collect is Mandatory");
			else
			{
				if(txn[31].trim().equals("REFUND") || txn[31].trim().equals("R"))
					txnDetails.put("updateFMSList", "R");
				else if(txn[31].trim().equals("COLLECT") || txn[31].trim().equals("C"))
					txnDetails.put("updateFMSList", "C");
				else
					throw new ProcessUploadTripBatchException("Refund/Collect is Invalid");
			}
			if(txn[32] == null || txn[32].equals(""))
				throw new ProcessUploadTripBatchException("FMS Amount is Mandatory");
			else
				txnDetails.put("FMSAmount", txn[32]);
			if (txn[33] != null && !txn[33].equals(""))
				txnDetails.put("incentiveAmt", txn[33]);
			if (txn[34] != null && !txn[34].equals(""))
				txnDetails.put("promoAmt", txn[34]);
			if (txn[35] != null && !txn[35].equals(""))
				txnDetails.put("cabRewardsAmt", txn[35]);
			if (txn[36] != null && !txn[36].equals(""))
				txnDetails.put("levy", txn[36]);
			
			String msg = this.businessHelper.getTxnBusiness().verifyFMSDriverVehicleAssoc(txnDetails.get("taxiNo"), txnDetails.get("nric"),
					DateUtil.convertStrToTimestamp(txnDetails.get("startDate"), DateUtil.FMS_TRIPS_DATE_FORMAT),
					DateUtil.convertStrToTimestamp(txnDetails.get("endDate"), DateUtil.FMS_TRIPS_DATE_FORMAT));
			if (!NonConfigurableConstants.BOOLEAN_YES.equalsIgnoreCase(msg) && !NonConfigurableConstants.IGNORE_FLAG.equalsIgnoreCase(msg)) {
				// Note: error message will be changed again
				throw new ProcessUploadTripBatchException("Interface Error to FMS - " + "Driver Association Not Valid");
			} else
				logger.info("FMS Interface is disabled");
		}
		
		txnDetails.put("user", getUserLoginIdAndDomain());
		
		return txnDetails;
	}
	
	private  Map<String, String> getTxnDetailsCard(String[] txn ) throws ParseException
	{
		Map<String, String> txnDetails = new HashMap<String, String>();
	    
		//check for big number error (ERROR CODE IS -1)
		for(String s: txn){
			if(s.equals("-1"))	
		    	  throw new ProcessUploadTripBatchException(NonConfigurableConstants.NUMBER_TO_BIG);
		}
		
	    String jobNo = txn[1];
	    
	    if(jobNo != null && !jobNo.trim().equals(""))
	    {
	      	
	      if(this.businessHelper.getTxnBusiness().checkJobNo(jobNo))	//true = got  false = no have
	    	  throw new ProcessUploadTripBatchException(NonConfigurableConstants.DUPLICATE_JOB_NO);
	    
	      if(jobNo.length() > 15)
	      		throw new ProcessUploadTripBatchException("Job No should not exceed 15 char in length.");
	      
	      	txnDetails.put("jobNo", jobNo);
	    }
	 
		String custNo = txn[2];
		
		AmtbAccount acct = this.businessHelper.getAccountBusiness().getAccountByCustNo(custNo);
		
		if (acct == null){
			throw new ProcessUploadTripBatchException("Account No. is not found.");
		}
		if(acct != null)
		{
			if(acct.getCustNo() == null)
				throw new ProcessUploadTripBatchException("Account No. is invalid.");
		}
		if (this.businessHelper.getTxnBusiness().isAccountClosed(acct.getCustNo().toString()))
			throw new ProcessUploadTripBatchException("The issued account for the selected card is closed");

		txnDetails.put("acctNo", acct.getAccountNo().toString());
		txnDetails.put("name", acct.getAccountName());
		
		Integer acctNo = acct.getAccountNo();
		
		if(acct.getAccountCategory().trim().equalsIgnoreCase("CORP"))
		{
			//division
			if(txn[3]!=null && !txn[3].equals(""))
			{
				AmtbAccount divAcct = this.businessHelper.getAccountBusiness().getAccountByCustNoAndCode(custNo, txn[3], "DIV");
				
				if(divAcct != null && divAcct.getAccountCategory().trim().equalsIgnoreCase("DIV"))
				{
					txnDetails.put("division", divAcct.getAccountNo().toString());
					acctNo = divAcct.getAccountNo();
				}
				else
					throw new ProcessUploadTripBatchException("Division Code is invalid");
			}
			//department
			if(txn[4] !=null && !txn[4].equals(""))
			{
				AmtbAccount deptAcct = this.businessHelper.getAccountBusiness().getAccountByCustNoAndCodeAndCode(custNo, txn[4], "DEPT", txn[3]);
				if(deptAcct != null && deptAcct.getAccountCategory().trim().equalsIgnoreCase("DEPT"))
				{
					txnDetails.put("department", deptAcct.getAccountNo().toString());
					acctNo = deptAcct.getAccountNo();
				}
				else
					throw new ProcessUploadTripBatchException("Department Code is invalid");
			}
		}
		else if(acct.getAccountCategory().equalsIgnoreCase("APP"))
		{
			if(txn[3]!=null && !txn[3].equals(""))
			{
				AmtbAccount sappAcct = this.businessHelper.getAccountBusiness().getAccountByCustNoAndCode(custNo, txn[3], "SAPP");
				if(sappAcct != null && sappAcct.getAccountCategory().trim().equalsIgnoreCase("SAPP"))
				{
					txnDetails.put("subApplicant", sappAcct.getAccountNo().toString());
					acctNo = sappAcct.getAccountNo();
				}
				else
					throw new ProcessUploadTripBatchException("Sub Application Code is invalid");
			}
		}
		
		boolean isPrepaid = false;
		
		if(txn[5] == null || txn[5].equals(""))
			throw new ProcessUploadTripBatchException("Card No is Mandatory");
		else
		{
			PmtbProduct pmtbProduct = this.businessHelper.getProductBusiness().getProductByCard(txn[5]);
			if(pmtbProduct != null)
			{
				txnDetails.put("cardNo", txn[5]);
				
				if(!pmtbProduct.getAmtbAccount().getAccountNo().equals(acctNo))
					throw new ProcessUploadTripBatchException("Card No's account is not the same as Account");
				
				if (NonConfigurableConstants.PRODUCT_STATUS_USED.equals(pmtbProduct.getCurrentStatus()))
					throw new ProcessUploadTripBatchException("Unable to create new trips as the card has been used already");
				
				if(txn[7] == null || txn[7].equals(""))
					throw new ProcessUploadTripBatchException("Product Type is Mandatory");
				else
				{
					if(!pmtbProduct.getPmtbProductType().getName().trim().equalsIgnoreCase(txn[7]))
						throw new ProcessUploadTripBatchException("Product Type column is Different from Card No's Product Type");
					else
					{
						PmtbProductType pmtbProductType = this.businessHelper.getProductBusiness().getProductTypeByName(txn[7]);
						if(pmtbProductType != null)
						{
							txnDetails.put("productType", pmtbProductType.getProductTypeId());
							isPrepaid = NonConfigurableConstants.getBoolean(pmtbProductType.getPrepaid());
						}
						else
							throw new ProcessUploadTripBatchException("Product Type is Invalid");
					}
				}
			}
			else
				throw new ProcessUploadTripBatchException("Card No is Invalid");
		}
		
		
		if(txn[9] == null || txn[9].equals(""))
			throw new ProcessUploadTripBatchException("Taxi No is Mandatory");
		else
			txnDetails.put("taxiNo", txn[9]);
		
		if(txn[10] == null || txn[10].equals(""))
			throw new ProcessUploadTripBatchException("Driver NRIC is Mandatory");
		else
			txnDetails.put("nric", txn[10]);

		if(txn[11] == null || txn[11].equals("") || txn[12] == null || txn[12].equals("") )
			throw new ProcessUploadTripBatchException(NonConfigurableConstants.MANDATORY_TRIP_START);
		else if(txn[11].contains("-"))
			throw new ProcessUploadTripBatchException("Trip Start Date contain invalid character");
		else if(txn[11].length() != 10)
			throw new ProcessUploadTripBatchException("Trip Start Date length is wrong");
		else if(txn[12].length() != 8)
			throw new ProcessUploadTripBatchException("Trip Start Time length is wrong");
		
		String tripStartDateTime = txn[11] + " " + txn[12];

		Timestamp startDate = null;
		if (tripStartDateTime != null && !tripStartDateTime.trim().equals(""))
		{
			Date startDateWithTime = formatter.parse(tripStartDateTime);
			startDate = DateUtil.convertDateToTimestamp(startDateWithTime);
		}
		
		if(startDate == null)
			throw new ProcessUploadTripBatchException(NonConfigurableConstants.MANDATORY_TRIP_START);
		
		txnDetails.put("startDate", DateUtil.convertTimestampToStr(startDate, DateUtil.TRIPS_DATE_FORMAT));
		
		AmtbAccount acctx = this.businessHelper.getTxnBusiness().getAccount(txn[5], startDate);
		if (acctx == null) 
			throw new ProcessUploadTripBatchException("There is no such card in the system");
		
		if(txn[13] == null || txn[13].equals("") || txn[14] == null || txn[14].equals("") )
			throw new ProcessUploadTripBatchException(NonConfigurableConstants.MANDATORY_TRIP_END);
		else if(txn[13].contains("-"))
			throw new ProcessUploadTripBatchException("Trip End Date contain invalid character");
		else if(txn[13].length() != 10)
			throw new ProcessUploadTripBatchException("Trip End Date length is wrong");
		else if(txn[14].length() != 8)
			throw new ProcessUploadTripBatchException("Trip End Time length is wrong");
		
		String tripEndDateTime = txn[13] +" " + txn[14];
		Timestamp endDate = null;
		if (tripEndDateTime != null && !tripEndDateTime.trim().equals(""))
		{
			Date endDateWithTime = formatter.parse(tripEndDateTime);
			endDate = DateUtil.convertDateToTimestamp(endDateWithTime);
		}
		
		if(endDate == null)
			throw new ProcessUploadTripBatchException(NonConfigurableConstants.MANDATORY_TRIP_END);
		
		txnDetails.put("endDate", DateUtil.convertTimestampToStr(endDate, DateUtil.TRIPS_DATE_FORMAT));

		// Validation - Start Date cannot be later than end date
		if (startDate.after(endDate)) {
			throw new ProcessUploadTripBatchException("Trip Start Date cannot be earlier than Trip End Date");
		}
		
		// Add company Code
		if(txn[8] == null || txn[8].equals(""))
			throw new ProcessUploadTripBatchException("Company Code is Mandatory");
		else
		{
			MstbMasterTable companyCdMaster = ConfigurableConstants
					.getMasterTableByMasterValue(ConfigurableConstants.SERVICE_PROVIDER,
							txn[8]);
			if(companyCdMaster != null)
				txnDetails.put("companyCd", companyCdMaster.getMasterCode());
			else
				throw new ProcessUploadTripBatchException("Company Code is Invalid");
		}
		if(txn[24] == null || txn[24].equals(""))
			throw new ProcessUploadTripBatchException("Vehicle Type is Mandatory");
		else
		{
			MstbMasterTable vehicleTypeMaster = ConfigurableConstants
					.getMasterTableByMasterValue(
							ConfigurableConstants.VEHICLE_MODEL,
							txn[24]);
			if(vehicleTypeMaster != null)
				txnDetails.put("vehicleType", vehicleTypeMaster.getMasterCode());
			else
				throw new ProcessUploadTripBatchException("Vehicle Type is Invalid");
		}
		
		if(txn[23] == null || txn[23].equals(""))
			throw new ProcessUploadTripBatchException("Job Type is Mandatory");
		else
		{
			MstbMasterTable jobTypeMaster = ConfigurableConstants
					.getMasterTableByMasterValue(
							ConfigurableConstants.JOB_TYPE,
							txn[23]);
			if(jobTypeMaster != null)
				txnDetails.put("jobType", jobTypeMaster.getMasterCode());
			else
				throw new ProcessUploadTripBatchException("Job Type is Invalid");
		}

		txnDetails.put("salesDraft", txn[6]);
		txnDetails.put("projCode", txn[20]);
		txnDetails.put("projCodeReason", txn[21]);
		
		if(txn[15] == null || txn[15].equals(""))
			throw new ProcessUploadTripBatchException("Fare Amount is Mandatory");
		else
		{
			DecimalFormat nf = new DecimalFormat("#.00");	
			String fareAmt = nf.format(new BigDecimal(txn[15].toString()));
			txnDetails.put("fareAmt", fareAmt);
		}
		
		if(isPrepaid)
		{
			//Admin Fee
			if(txn[16] != null || !txn[16].equals(""))
			{
				DecimalFormat nf = new DecimalFormat("#.00");	
				String prepaidAdminFee = nf.format(new BigDecimal(txn[16].toString()));
				txnDetails.put("prepaidAdminFee", prepaidAdminFee);
			}
			else
				txnDetails.put("prepaidAdminFee", "0.00");
			
			//GST Value
			if(txn[17] != null || !txn[17].equals(""))
			{
				DecimalFormat nf = new DecimalFormat("#.00");	
				String prepaidGst = nf.format(new BigDecimal(txn[17].toString()));
				txnDetails.put("prepaidGst", prepaidGst);
			}
			else
				txnDetails.put("prepaidGst", "0.00");
		}
		if(txn[25] == null || txn[25].equals(""))
			throw new ProcessUploadTripBatchException("Pickup is Mandatory");
		else
			txnDetails.put("pickup", txn[25]);
		
		txnDetails.put("destination", txn[26]);
		txnDetails.put("remarks", txn[27]);
		
		// checkbox for complimentary
		String complimentary = txn[18];
		if (complimentary != null && (complimentary.equalsIgnoreCase("Y") || complimentary.equalsIgnoreCase("YES")))
			txnDetails.put("complimentary", NonConfigurableConstants.BOOLEAN_YES);
		else
			txnDetails.put("complimentary", NonConfigurableConstants.BOOLEAN_NO);

		txnDetails.put("surchargeDesc", txn[19]);
		
		
		String updateFms = txn[28];
		if (updateFms != null && (updateFms.equalsIgnoreCase("Y") || updateFms.equalsIgnoreCase("YES")))
			txnDetails.put("toUpdateFMSList",  NonConfigurableConstants.BOOLEAN_YES);
		else
			txnDetails.put("toUpdateFMSList", NonConfigurableConstants.BOOLEAN_NO);
		
		if(NonConfigurableConstants.BOOLEAN_YES.equals(txnDetails.get("toUpdateFMSList"))) {
			if(txn[29] == null || txn[29].equals(""))
				throw new ProcessUploadTripBatchException("Refund/Collect is Mandatory");
			else
			{
				if(txn[29].trim().equals("REFUND") || txn[29].trim().equals("R"))
					txnDetails.put("updateFMSList", "R");
				else if(txn[29].trim().equals("COLLECT") || txn[29].trim().equals("C"))
					txnDetails.put("updateFMSList", "C");
				else
					throw new ProcessUploadTripBatchException("Refund/Collect is Invalid");
			}
			if(txn[30] == null || txn[30].equals(""))
				throw new ProcessUploadTripBatchException("FMS Amount is Mandatory");
			else
				txnDetails.put("FMSAmount", txn[30]);
			if (txn[31] != null && !txn[31].equals(""))
				txnDetails.put("incentiveAmt", txn[31]);
			if (txn[32] != null && !txn[32].equals(""))
				txnDetails.put("promoAmt", txn[32]);
			if (txn[33] != null && !txn[33].equals(""))
				txnDetails.put("cabRewardsAmt", txn[33]);
			if (txn[34] != null && !txn[34].equals(""))
				txnDetails.put("levy", txn[34]);
			
			String msg = this.businessHelper.getTxnBusiness().verifyFMSDriverVehicleAssoc(txnDetails.get("taxiNo"), txnDetails.get("nric"),
					DateUtil.convertStrToTimestamp(txnDetails.get("startDate"), DateUtil.FMS_TRIPS_DATE_FORMAT),
					DateUtil.convertStrToTimestamp(txnDetails.get("endDate"), DateUtil.FMS_TRIPS_DATE_FORMAT));
			if (!NonConfigurableConstants.BOOLEAN_YES.equalsIgnoreCase(msg) && !NonConfigurableConstants.IGNORE_FLAG.equalsIgnoreCase(msg)) {
				// Note: error message will be changed again
				throw new ProcessUploadTripBatchException("Interface Error to FMS - " + "Driver Association Not Valid");
			} else
				logger.info("FMS Interface is disabled");
		}
		
//		Row prepaidAdminRow = (Row) this.getFellow("prepaidAdminRow");
//		if(prepaidAdminRow.isVisible()){
//			txnDetails.put("prepaidAdminFee", ((Decimalbox) this.getFellow("prepaidAdminFee")).getValue().toString());
//			txnDetails.put("prepaidGst", ((Decimalbox) this.getFellow("prepaidGst")).getValue().toString());
//			
//		}
		
		// Trip Type
		if(txn[22] == null || txn[22].equals(""))
			throw new ProcessUploadTripBatchException("Trip Type is Mandatory");
		else
		{
			MstbMasterTable tripTypeMaster = ConfigurableConstants
					.getMasterTableByMasterValue(
							ConfigurableConstants.VEHICLE_TRIP_TYPE,
							txn[22]);
			if(tripTypeMaster != null)
				txnDetails.put("tripType", tripTypeMaster.getMasterCode());
			else
				throw new ProcessUploadTripBatchException("Trip Type is Invalid");
		}
		
		
		txnDetails.put("user", getUserLoginIdAndDomain());
		
		return txnDetails;
	}
	
	@Override
	public void refresh() {

	}

	public void clickRadioWithCard() {
		if(radioWithCardless.isChecked())
			radioWithCardless.setChecked(false);
	}
	public void clickRadioWithCardless() {
		if(radioWithCard.isChecked())
			radioWithCard.setChecked(false);
	}
	private void appendFileAttachmentToListbox(FileAttachment attachment, byte[] bytes) {
		
		for (Object object : attachmentLB.getItems()) {
			Listitem item = (Listitem) object;
			attachmentLB.removeChild(item);
		}
		attachmentFile.clear();
		
		Listitem item = new Listitem();
		item.appendChild(newListcell(attachment.fileName));
		item.setValue(attachment);
		attachmentFile.put(bytes, attachment.fileName);

		Listcell deleteImageCell = new Listcell();
		Image deleteImage = new Image("/images/delete.png");
		deleteImage.setStyle("cursor: pointer");
		// returns a listitem
		ZScript showInfo = ZScript
				.parseContent("uploadTripsBatchWindow.deleteRow(self.getParent().getParent())");
		showInfo.setLanguage("java");
		EventHandler pdEvent = new EventHandler(showInfo, ConditionImpl.getInstance(null, null));
		deleteImage.addEventHandler("onClick", pdEvent);
		deleteImageCell.appendChild(deleteImage);
		item.appendChild(deleteImageCell);

		attachmentLB.appendChild(item);
	}
	public void deleteRow(Listitem item) {
		attachmentLB.removeChild(item);
	}
	public void displayFailureResult(List<Map<String, String>> txnErrorDetailsList )
	{

		Listbox resultListBox = (Listbox)this.getFellow("resultFailList");
		resultListBox.setMold(Constants.LISTBOX_MOLD_DEFAULT);
		resultListBox.getItems().clear();
		
		if(txnErrorDetailsList.size()>0)
		{
			for(Map<String, String> txnErrorDetails : txnErrorDetailsList)
			{
				Listitem item = new Listitem();
				item.appendChild(newListcell(txnErrorDetails.get("sn")));
				item.appendChild(newListcell(txnErrorDetails.get("Reason")));
				
				resultListBox.appendChild(item);
			}
			
			if(txnErrorDetailsList.size()>ConfigurableConstants.getMaxQueryResult())
				resultListBox.removeItemAt(ConfigurableConstants.getMaxQueryResult());
			
			if(resultListBox.getListfoot()!=null)
				resultListBox.removeChild(resultListBox.getListfoot());
		}
		else
		{
			if(resultListBox.getListfoot()==null){
				resultListBox.appendChild(ComponentUtil.createNoRecordsFoundListFoot(10));
			}
		}
		
		for (Object object : attachmentLB.getItems()) {
			Listitem item = (Listitem) object;
			attachmentLB.removeChild(item);
		}
		attachmentFile.clear();
	}
	
}
