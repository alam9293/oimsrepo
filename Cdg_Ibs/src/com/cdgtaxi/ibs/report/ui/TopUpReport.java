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

public class TopUpReport extends ReportWindow {

	
	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(PrepaidApprovalReport.class);
	
	public TopUpReport() throws IOException {
		super("Top Up Report", "Prepaid");
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
		String mobileNo = (String)reportParamsProperties.get("05. Mobile No");
		String productTypeId = (String)reportParamsProperties.get("06. Product Type");
		String topUpType = (String)reportParamsProperties.get("07. Top Up Type");
		String promoCode = (String)reportParamsProperties.get("08. Promo Code");
		String topUpStartDate = (String)reportParamsProperties.get("09. Top Up Start Date"); 
		String topUpEndDate = (String)reportParamsProperties.get("10. Top Up End Date");
		String minTopUp = (String)reportParamsProperties.get("11. Min Top Up"); 
		String entityNo = (String)reportParamsProperties.get("12. Entity");
		String sortBy = (String)reportParamsProperties.get("13. Sort By");
		
		data.add(new String[]{"Integrated Billing System"});
		data.add(new String[]{"Top Up Report"});
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
		if(!Strings.isNullOrEmpty(mobileNo)){
			data.add(new String[]{"Mobile No: " + mobileNo});
		}
		if(!Strings.isNullOrEmpty(productTypeId)){
			Map<String, String> productTypes = this.businessHelper.getReportBusiness().getPrepaidProductTypes();
			data.add(new String[]{"Product Type: " + productTypes.get(productTypeId)});
		}
		if(!Strings.isNullOrEmpty(topUpType)){
			data.add(new String[]{"Top Up Type: " + NonConfigurableConstants.PREPAID_TOP_UP_TYPE.get(topUpType)});
		}
		if(!Strings.isNullOrEmpty(promoCode)){
			data.add(new String[]{"Promo Code: " +  promoCode});
		}
		if(!Strings.isNullOrEmpty(topUpStartDate)){
			data.add(new String[]{"Top Up Start Date: " +  topUpStartDate});
		}
		
		if(!Strings.isNullOrEmpty(topUpEndDate)){
			data.add(new String[]{"Top Up End Date: " + topUpEndDate});
		}
		
		if(!Strings.isNullOrEmpty(minTopUp)){
			data.add(new String[]{"Min Top Up: " + minTopUp});
		}
		
		if(!Strings.isNullOrEmpty(entityNo)){
			Map<Integer, String> entities = MasterSetup.getEntityManager().getAllMasters();
			data.add(new String[]{"Entity: " + entities.get(Integer.valueOf(entityNo))});
		}
		if(!Strings.isNullOrEmpty(sortBy)){
			data.add(new String[]{"Sort By: " + NonConfigurableConstants.PREPAID_TOP_UP_REPORT_ORDER.get(sortBy)});
		}
			
	
		// Line Break
		data.add(new String[]{""});
		
		data.add(new String[]{"Printed By: "+ reportParamsProperties.get("printedBy")});

		data.add(new String[]{"Report Date: "
				+ DateUtil.convertDateToStr(DateUtil.getCurrentDate(), DateUtil.GLOBAL_DATE_FORMAT)});

		// Line Break
		data.add(new String[]{""});
			
		data.add(new String[] {"Account No", "Account Name", "Division Code", "Division Name", "Department Code", "Department Name",
				  "Card No", "Name on Card", "Issued Date", "Mobile No", "Value Expiry Date", "Card Expiry Date", "Card Status",
				  "No of Top-Up", "Total Top-Up Amount", "CashPlus" , "Top Up", "Ship Contact Salutation", "Ship Contact Person", "Area", "Block",
				  "Street", "Unit", "Building Name", "Country", "State", "City", "Postal Code"
		});
		
		// Data
		List<Object[]> results = this.businessHelper.getReportBusiness().getTopUpRecords(acctType, acctNo, acctName, cardNo, cardStatus, mobileNo, productTypeId, topUpType, promoCode, topUpStartDate, topUpEndDate, minTopUp, entityNo, sortBy);
		if (results.size() == 0) {
			data.add(new String[]{"No records found"});
		} else {
			
			List<String[]> strResults = convertToStringArray(results);
			for(String[] record: strResults){
				//card no
				record[6] = appendWithQuote(record[6]);
				
				//card status
				record[12] = NonConfigurableConstants.PRODUCT_STATUS.get(record[12]);
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