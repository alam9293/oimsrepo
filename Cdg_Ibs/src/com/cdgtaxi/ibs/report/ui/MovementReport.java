package com.cdgtaxi.ibs.report.ui;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.StringWriter;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.httpclient.HttpException;
import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.zkoss.util.media.AMedia;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Filedownload;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listitem;

import au.com.bytecode.opencsv.CSVWriter;

import com.cdgtaxi.ibs.Uri;
import com.cdgtaxi.ibs.common.MasterSetup;
import com.cdgtaxi.ibs.common.NonConfigurableConstants;
import com.cdgtaxi.ibs.report.Constants;
import com.cdgtaxi.ibs.report.exception.FormatNotSupportedException;
import com.cdgtaxi.ibs.util.DateUtil;
import com.cdgtaxi.ibs.util.StringUtil;
import com.elixirtech.ers2.client.ERSClient;
import com.elixirtech.net.NetException;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

public class MovementReport extends ReportWindow implements CSVFormat {
	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(MovementReport.class);
	public MovementReport() throws IOException {
		super("Movement Report", "Prepaid");
	}
	// to init the product type
	public void init(){
		Listbox listbox = (Listbox)this.getFellow("2. Product Type");
	
		for(Object item : listbox.getChildren()){
			Listitem listitem = (Listitem)item;
			listitem.setValue(listitem.getValue().toString());
		}
	
		// removing new from the product status
		listbox = (Listbox)this.getFellow("5. Card Status");
	
		Object newItem = null;
		for(Object item : listbox.getChildren()){
			Listitem listitem = (Listitem)item;
			if(listitem.getValue().equals(NonConfigurableConstants.PRODUCT_STATUS_NEW)){
				newItem = listitem;
				break;
			}
		}
		if(newItem!=null){
			listbox.removeChild((Listitem)newItem);
		}
	}
	@Override
	public void generate() throws HttpException, IOException, InterruptedException, NetException {
		Properties reportParamsProperties = new Properties();
		if(super.generate(reportParamsProperties)==null){
			return;
		}
		//retrieve format
		String report = super.getReport();
		ERSClient client = super.getClient();
		String repository = super.getRepository();
		String reportCategory = super.getReportCategory();
		Listbox formatList = (Listbox)this.getFellow("reportFormat");
		String extension = (String)formatList.getSelectedItem().getLabel();
		String format = (String)formatList.getSelectedItem().getValue();
		if(format.equals(Constants.FORMAT_CSV)){
			StringBuffer dataInCSV = this.generateCSVData(reportParamsProperties);
			AMedia media = new AMedia(report.replaceAll(" ", "_")+"."+extension, extension, format, dataInCSV.toString());
			Filedownload.save(media);
		}else{
			ByteArrayOutputStream os = new ByteArrayOutputStream();
			client.renderReport(repository+"/"+reportCategory+"/"+report+"/"+report+".rml", format, os, reportParamsProperties);
			AMedia media = new AMedia(report.replaceAll(" ", "_")+"."+extension, extension, format, os.toByteArray());
			if(format.equals(Constants.FORMAT_EXCEL)){
				Filedownload.save(media);
			}else if(format.equals(Constants.FORMAT_PDF)){
				HashMap params = new HashMap();
				params.put("report", media);
				this.forward(Uri.REPORT_RESULT, params);
			}
			os.close();
			client.close();
		}
	}
	public StringBuffer generateCSVData(Properties reportParamsProperties) throws FormatNotSupportedException {
		
		ArrayList<String[]> data = Lists.newArrayList();
		
		filterDoubleQuote(reportParamsProperties);
		
		String entityNo = (String)reportParamsProperties.get("1. Entity");
		String productItemType = (String)reportParamsProperties.get("2. Product Type");
		String acctNo = (String)reportParamsProperties.get("3a. Account No");
		String acctName = (String)reportParamsProperties.get("3b. Account Name");
		String acctStatus = (String)reportParamsProperties.get("4. Account Status");
		String productStatus = (String)reportParamsProperties.get("5. Card Status");
		String mthYear = (String)reportParamsProperties.get("6. Mth/ Year");
		String type = (String)reportParamsProperties.get("7. Type");
		
		data.add(new String[]{"Integrated Billing System"});
		data.add(new String[]{"Movement Report"});
		data.add(new String[]{"Selection Criteria:"});
		
		// Line Break
		data.add(new String[]{""});
		
		if(!Strings.isNullOrEmpty(entityNo)){
			Map<Integer, String> entities = MasterSetup.getEntityManager().getAllMasters();
			data.add(new String[]{"Entity: " + entities.get(Integer.valueOf(entityNo))});
		}
		
		if(!Strings.isNullOrEmpty(productItemType)){
			Map<String, String> productTypes = this.businessHelper.getReportBusiness().getPrepaidProductTypes();
			data.add(new String[]{"Product Type: " +  productTypes.get(productItemType)});
		}
	
		if(!Strings.isNullOrEmpty(acctNo)){
			data.add(new String[]{"Account No: " + acctNo});
		}
		if(!Strings.isNullOrEmpty(acctName)){
			data.add(new String[]{"Account Name: " + acctName});
		}
		if(!Strings.isNullOrEmpty(acctStatus)){
			data.add(new String[]{"Account Status: " + NonConfigurableConstants.ACCOUNT_STATUS.get(acctStatus)});
		}
		
		if(!Strings.isNullOrEmpty(productStatus)){
			data.add(new String[]{"Product Status: " + NonConfigurableConstants.PRODUCT_STATUS.get(productStatus)});
		}
		

		if(!Strings.isNullOrEmpty(mthYear)){
			DateFormat df = new SimpleDateFormat("MMM-yyyy");
			data.add(new String[]{"Mth/ Year: " +  df.format(((Datebox)this.getFellow("6. Mth/ Year")).getValue())});
		}
		
		if(!Strings.isNullOrEmpty(type)){
			data.add(new String[]{"Type: " + NonConfigurableConstants.MOVEMENT_REPORT_TYPE.get(type)});
		}
		
			
		// Line Break
		data.add(new String[]{""});
		
		data.add(new String[]{"Printed By: "+ reportParamsProperties.get("printedBy")});

		data.add(new String[]{"Report Date: "
				+ DateUtil.convertDateToStr(DateUtil.getCurrentDate(), DateUtil.GLOBAL_DATE_FORMAT)});

		// Line Break
		data.add(new String[]{""});
		
		
		String[] firstRow = new String[] {"",  "", "", "", "", "", "", "", "", 
				"Opening Balance", "", "",
				"Purchase / Additional", "", "",
				"Transfered In", "", "",
				"Used for Trip Settlement", "", "", "", "", "", "", 
				"Offsetted / Deducted", "", "", "", "", "", 
				"Adjustment", "", "",
				"Transfered Out", "", "",
				"Expired / Forfeited", "", "", "", "",
				"Closing Balance", "", ""
				};
		
		
		
		data.add(firstRow);
		
		data.add(new String[] {"S/N", "Entity", "Account No", "Account Name", "Account Status", "Card No", "Card Issued Date", "Card Expiry Date", "Card Status",
				"Paid Value", "CashPlus", "Total",
				"Paid Value", "CashPlus", "Total",
				"Paid Value", "CashPlus", "Total",
				"Trip Amount", "", "", "", "Paid Value", "CashPlus", "Total Fare",
				"Deducted Amount", "", "", "Paid Value", "CashPlus", "Total",
				"Paid Value", "CashPlus", "Total",
				"Paid Value", "CashPlus", "Total",
				"Paid Value", "", "", "CashPlus", "Total",
				"Paid Value", "CashPlus", "Total"
				
		});
		
		
		data.add(new String[] {"", "", "", "", "", "", "", "", "",
				"", "", "",
				"", "", "",
				"", "", "",
				"Taxi", "Admin", "GST", "Total", "", "", "",
				"Fee", "GST", "Total", "", "", "",
				"", "", "",
				"", "", "",
				"Before", "GST", "Total", "", "",
				"", "", ""
		});
			
		//determine which number of column are sum able
		Map<Integer, Double> sumableColumnNoMap = Maps.newHashMap();
		List<Integer> sumableColumnNoLists = Lists.newArrayList(
				9, 10, 11, 
				12, 13, 14, 
				15, 16, 17, 
				18, 19, 20, 21, 22, 23, 24,
				25, 26, 27, 28, 29, 30,
				31, 32, 33,
				34, 35, 36,
				37, 38, 39, 40, 41,
				42, 43, 44
		);
		for(Integer columnNo: sumableColumnNoLists){
			sumableColumnNoMap.put(columnNo, Double.valueOf(0));
		}
		
		
		//Data
		List<Object[]> results = this.businessHelper.getReportBusiness().getMovementReport(
				entityNo, productItemType,
				acctNo,
				acctName,
				acctStatus,
				productStatus,
				mthYear,
				type
		);
		
		if (results.size() == 0) {
			data.add(new String[]{"No records found"});
		} else {
			
			List<String[]> strResults = convertToStringArray(results);
			
			//build the serial no
			int size = strResults.size();
			for(int i=0; i<size; i++){
				String[] record = strResults.get(i);
				
				LinkedList<String> consolidateRecordList = Lists.newLinkedList(Lists.newArrayList(record));
				consolidateRecordList.addFirst(String.valueOf(i+1));

				String[] consolidateRecord = new String[consolidateRecordList.size()];
				consolidateRecordList.toArray(consolidateRecord);
				strResults.set(i, consolidateRecord);
			}	
			
			for(String[] record: strResults){
				//calculate the sum of sum able column
				for(int i=0; i<record.length; i++){
					
					if(sumableColumnNoMap.containsKey(i)){
						double cummulativeValue= sumableColumnNoMap.get(i);
						cummulativeValue += Double.parseDouble(((String)record[i]).replaceAll(",", ""));
						sumableColumnNoMap.put(i, cummulativeValue);
					}
				}
				
				//card no
				record[5] = appendWithQuote(record[5]);
						
				record[4] = NonConfigurableConstants.ACCOUNT_STATUS.get(record[4]);
				record[8] = NonConfigurableConstants.PRODUCT_STATUS.get(record[8]);
		
			}
			data.addAll(strResults);
			
			
			DecimalFormat df = new DecimalFormat(StringUtil.GLOBAL_DECIMAL_FORMAT);
			
			//add the total sum of sum able column to bottom of list
			String[] sumRecord = new String[firstRow.length];
			for(int i=0; i<firstRow.length; i++){
				
				String value = "";
				if(sumableColumnNoMap.containsKey(i)){
					double columnValue = sumableColumnNoMap.get(i);
					value = df.format(columnValue);
				}
				sumRecord[i] = value;
			}
			
			sumRecord[8] = "Grand Total: ";

			data.add(sumRecord);
		}
			
		
		StringWriter writer = new StringWriter();
		CSVWriter csvWriter = new CSVWriter(writer);
		csvWriter.writeAll(data);
		try {
			if (csvWriter != null)
				csvWriter.close();
		} catch (IOException ioe) {
		}
		IOUtils.closeQuietly(writer);
		
		return new StringBuffer(writer.toString());
		
	
	}
}