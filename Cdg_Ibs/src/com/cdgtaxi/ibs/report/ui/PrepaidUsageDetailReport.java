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

public class PrepaidUsageDetailReport extends ReportWindow {

	
	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(PrepaidUsageDetailReport.class);
	
	public PrepaidUsageDetailReport() throws IOException {
		super("Prepaid Usage Detail Report", "Prepaid");
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
		String cardNo = (String)reportParamsProperties.get("03. Card No");
		String cardStatus = (String)reportParamsProperties.get("04. Card Status");
		String productTypeId = (String)reportParamsProperties.get("05. Product Type");
		String issueStartDate = (String)reportParamsProperties.get("06. Issue Start Date"); 
		String issueEndDate = (String)reportParamsProperties.get("07. Issue End Date");
		String tripStartDate = (String)reportParamsProperties.get("08. Trip Start Date"); 
		String tripEndDate = (String)reportParamsProperties.get("09. Trip End Date");
		String entityNo = (String)reportParamsProperties.get("10. Entity");
		String sortBy = (String)reportParamsProperties.get("11. Sort By");
		
		data.add(new String[]{"Integrated Billing System"});
		data.add(new String[]{"Prepaid Usage Detail Report"});
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
		if(!Strings.isNullOrEmpty(cardNo)){
			data.add(new String[]{"Card No: " + cardNo});
		}
		
		if(!Strings.isNullOrEmpty(cardStatus)){
			data.add(new String[]{"Card Status: " + NonConfigurableConstants.PRODUCT_STATUS.get(cardStatus)});
		}
		if(!Strings.isNullOrEmpty(productTypeId)){
			Map<String, String> productTypes = this.businessHelper.getReportBusiness().getPrepaidProductTypes();
			data.add(new String[]{"Product Type: " + productTypes.get(productTypeId)});
		}
		if(!Strings.isNullOrEmpty(issueStartDate)){
			data.add(new String[]{"Issue Start Date: " +  issueStartDate});
		}
		if(!Strings.isNullOrEmpty(issueEndDate)){
			data.add(new String[]{"Issue End Date: " + issueEndDate});
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
			
		data.add(new String[] {"Account No", "Account Name", "Division Code", "Division Name", "Department Code", "Department Name",
				  "Card No", "Name on Card", "Issued Date", "Value Expiry Date", "Card Expiry Date", "Card Status",
				  "Months Subscribed", "Purchase Amount to Date", "Top Up", "Booking", "Trips", "%"
		});
		
		// Data
		List<Object[]> results = this.businessHelper.getReportBusiness().getPrepaidUsageDetailRecords(acctType, acctNo, acctName, cardNo, cardStatus, productTypeId, issueStartDate, issueEndDate, tripStartDate, tripEndDate, entityNo, sortBy);
		
		if (results.size() == 0) {
			data.add(new String[]{"No records found"});
		} else {
			
			List<String[]> strResults = convertToStringArray(results);
			for(String[] record: strResults){
				record[6] = appendWithQuote(record[6]);
				record[11] = NonConfigurableConstants.PRODUCT_STATUS.get(record[11]);
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