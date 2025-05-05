package com.cdgtaxi.ibs.report.ui;

import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.apache.commons.httpclient.HttpException;
import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.zkoss.util.media.AMedia;
import org.zkoss.zul.Filedownload;
import org.zkoss.zul.Listbox;

import au.com.bytecode.opencsv.CSVWriter;

import com.cdgtaxi.ibs.common.NonConfigurableConstants;
import com.cdgtaxi.ibs.report.Constants;
import com.cdgtaxi.ibs.report.exception.FormatNotSupportedException;
import com.cdgtaxi.ibs.util.DateUtil;
import com.elixirtech.net.NetException;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;

public class PrepaidCardTransactionReport extends ReportWindow {

	
	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(PrepaidCardTransactionReport.class);
	
	public PrepaidCardTransactionReport() throws IOException {
		super("Prepaid Card Transaction Report", "Prepaid");
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
		String cardNo = (String)reportParamsProperties.get("1. Card No");
		
		data.add(new String[]{"Integrated Billing System"});
		data.add(new String[]{"Prepaid Card Transaction Report"});
		data.add(new String[]{"Selection Criteria:"});
		
		// Line Break
		data.add(new String[]{""});
		
		
		if(!Strings.isNullOrEmpty(cardNo)){
			data.add(new String[]{"Card No: " + cardNo});
		}
			
	
		// Line Break
		data.add(new String[]{""});
		
		data.add(new String[]{"Printed By: "+ reportParamsProperties.get("printedBy")});

		data.add(new String[]{"Report Date: "
				+ DateUtil.convertDateToStr(DateUtil.getCurrentDate(), DateUtil.GLOBAL_DATE_FORMAT)});

		// Line Break
		data.add(new String[]{""});
			
		data.add(new String[] {  "Date", "Action", "Amount($)", "Card Balance", "Job No", "Travel Date", "Travel Time", "Taxi No", "Pickup Address", "Destination", 
				"Taxi Fare($)", "Admin($)", "GST($)", "Total($)"
		});
		
		// Data
		List<Object[]> results = this.businessHelper.getReportBusiness().getPrepaidCardTransactionRecords(cardNo);
		if (results.size() == 0) {
			data.add(new String[]{"No records found"});
		} else {
			
			List<String[]> strResults = convertToStringArray(results);
			for(String[] record: strResults){
				
				record[1] = NonConfigurableConstants.PREPAID_TXN_TYPE.get(record[1]);
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