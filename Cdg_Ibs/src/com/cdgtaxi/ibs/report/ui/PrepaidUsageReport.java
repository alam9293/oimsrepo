package com.cdgtaxi.ibs.report.ui;

import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.httpclient.HttpException;
import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.zkoss.util.media.AMedia;
import org.zkoss.zul.Filedownload;
import org.zkoss.zul.Listbox;

import au.com.bytecode.opencsv.CSVWriter;

import com.cdgtaxi.ibs.common.MasterSetup;
import com.cdgtaxi.ibs.common.NonConfigurableConstants;
import com.cdgtaxi.ibs.report.Constants;
import com.cdgtaxi.ibs.report.exception.FormatNotSupportedException;
import com.cdgtaxi.ibs.util.DateUtil;
import com.elixirtech.net.NetException;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;

public class PrepaidUsageReport extends ReportWindow {

	
	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(PrepaidUsageReport.class);
	
	public PrepaidUsageReport() throws IOException {
		super("Prepaid Usage Report", "Prepaid");
	}

	@Override
	public void generate() throws HttpException, IOException, InterruptedException, NetException {
		Properties reportParamsProperties = new Properties();
		if(super.generate(reportParamsProperties)==null){
			return;
		}
		//retrieve format
		String report = super.getReport();
		Listbox formatList = (Listbox)this.getFellow("reportFormat");
		String extension = (String)formatList.getSelectedItem().getLabel();
		String format = (String)formatList.getSelectedItem().getValue();
		if(format.equals(Constants.FORMAT_CSV)){
			
			StringBuffer dataInCSV = this.generateCSVData(reportParamsProperties);
			AMedia media = new AMedia(report.replaceAll(" ", "_") + "." + extension, extension, format,
					dataInCSV.toString());
			Filedownload.save(media);
		}else{
			throw new FormatNotSupportedException();
			
		}
	}

	

	private StringBuffer generateCSVData(Properties reportParamsProperties) {

		
		ArrayList<String[]> data = Lists.newArrayList();
		
		String acctType = (String)reportParamsProperties.get("01. Account Type");
		String acctNo = (String)reportParamsProperties.get("02a. Account No");
		String acctName = (String)reportParamsProperties.get("02b. Account Name");
		String acctStatus = (String)reportParamsProperties.get("03. Account Status");
		String subscribeProductTypeId = (String)reportParamsProperties.get("04. Subscribed Product Type");
		String joinStartDate = (String)reportParamsProperties.get("05. Join Start Date"); 
		String joinEndDate = (String)reportParamsProperties.get("06. Join End Date");
		String tripStartDate = (String)reportParamsProperties.get("07. Trip Start Date"); 
		String tripEndDate = (String)reportParamsProperties.get("08. Trip End Date");

		String entityNo = (String)reportParamsProperties.get("09. Entity");
		String sortBy = (String)reportParamsProperties.get("10. Sort By");
		
		data.add(new String[]{"Integrated Billing System"});
		data.add(new String[]{"Prepaid Usage Report"});
		data.add(new String[]{"Selection Criteria:"});
		
		// Line Break
		data.add(new String[]{""});
		
		if(!Strings.isNullOrEmpty(acctType)){
			data.add(new String[]{"Account Type: " + NonConfigurableConstants.ACCOUNT_TEMPLATES.get(acctType)});
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
		if(!Strings.isNullOrEmpty(subscribeProductTypeId)){
			Map<String, String> productTypes = this.businessHelper.getReportBusiness().getPrepaidProductTypes();
			data.add(new String[]{"Subscribed Product Type: " + productTypes.get(subscribeProductTypeId)});
		}
		if(!Strings.isNullOrEmpty(joinStartDate)){
			data.add(new String[]{"Join Start Date: " +  joinStartDate});
		}
		
		if(!Strings.isNullOrEmpty(joinEndDate)){
			data.add(new String[]{"Join End Date: " + joinEndDate});
		}
		if(!Strings.isNullOrEmpty(tripStartDate)){
			data.add(new String[]{"Trip Start Date: " +  tripStartDate});
		}
		if(!Strings.isNullOrEmpty(tripEndDate)){
			data.add(new String[]{"Trip End Date: " + tripEndDate});
		}
		if(!Strings.isNullOrEmpty(entityNo)){
			Map<Integer, String> entities = MasterSetup.getEntityManager().getAllMasters();
			data.add(new String[]{"Entity: " + entities.get(Integer.valueOf(entityNo))});
		}
		if(!Strings.isNullOrEmpty(sortBy)){
			data.add(new String[]{"Sort By: " + NonConfigurableConstants.PREPAID_USAGE_REPORT_ORDER.get(sortBy)});
		}
			
	
		// Line Break
		data.add(new String[]{""});
		
		data.add(new String[]{"Printed By: "+ reportParamsProperties.get("printedBy")});

		data.add(new String[]{"Report Date: "
				+ DateUtil.convertDateToStr(DateUtil.getCurrentDate(), DateUtil.GLOBAL_DATE_FORMAT)});

		// Line Break
		data.add(new String[]{""});
			
		data.add(new String[] {"Account No", "Account Name", "Join Date", "Account Status", "Months Joined", "Purchase Amt to Date", "Top-up", "Booking", "Trips", "%" 
		});
		
		// Data
		List<Object[]> results = this.businessHelper.getReportBusiness().getPrepaidUsageRecords(acctType, acctNo, acctName, acctStatus, subscribeProductTypeId, joinStartDate, joinEndDate, tripStartDate, tripEndDate, entityNo, sortBy);
		if (results.size() == 0) {
			data.add(new String[]{"No records found"});
		} else {
			
			List<String[]> strResults = convertToStringArray(results);
			for(String[] record: strResults){
				record[3] = NonConfigurableConstants.ACCOUNT_STATUS.get(record[3]);
			}	
			data.addAll(strResults);
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