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

public class PrepaidApprovalReport extends ReportWindow {

	
	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(PrepaidApprovalReport.class);
	
	public PrepaidApprovalReport() throws IOException {
		super("Prepaid Approval Report", "Prepaid");
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
		String requestStartDate = (String)reportParamsProperties.get("04. Request Start Date");
		String requestEndDate = (String)reportParamsProperties.get("05. Request End Date");
		String approvalStartDate = (String)reportParamsProperties.get("06. Approval Start Date");
		String approvalEndDate = (String)reportParamsProperties.get("07. Approval End Date");
		String actionType = (String)reportParamsProperties.get("08. Action Type");
		String approvalStatus = (String)reportParamsProperties.get("09. Approval Status");
		String entity = (String)reportParamsProperties.get("10. Entity");
		String sortBy = (String)reportParamsProperties.get("11. Sort By");
		
		data.add(new String[]{"Integrated Billing System"});
		data.add(new String[]{"Prepaid Approval Report"});
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
		if(!Strings.isNullOrEmpty(requestStartDate)){
			data.add(new String[]{"Request Start Date: " + requestStartDate});
		}
		if(!Strings.isNullOrEmpty(requestEndDate)){
			data.add(new String[]{"Request End Date: " + requestEndDate});
		}
		if(!Strings.isNullOrEmpty(approvalStartDate)){
			data.add(new String[]{"Approval Start Date: " + approvalStartDate});
		}
		if(!Strings.isNullOrEmpty(approvalEndDate)){
			data.add(new String[]{"Approval End Date: " + approvalEndDate});
		}
		if(!Strings.isNullOrEmpty(actionType)){
			data.add(new String[]{"Action Type: " +  NonConfigurableConstants.PREPAID_REQUEST_TYPE.get(actionType)});
		}
		if(!Strings.isNullOrEmpty(approvalStatus)){
			data.add(new String[]{"Approval Status: " +  NonConfigurableConstants.PREPAID_APPROVAL_STATUS.get(approvalStatus)});
		}
		if(!Strings.isNullOrEmpty(entity)){
			Map<Integer, String> entities = MasterSetup.getEntityManager().getAllMasters();
			data.add(new String[]{"Entity: " + entities.get(Integer.valueOf(entity))});
		}
		if(!Strings.isNullOrEmpty(sortBy)){
			data.add(new String[]{"Sort By: " + NonConfigurableConstants.PREPAID_APPROVAL_REPORT_ORDER.get(sortBy)});
		}
			
	
		// Line Break
		data.add(new String[]{""});
		
		data.add(new String[]{"Printed By: "+ reportParamsProperties.get("printedBy")});

		data.add(new String[]{"Report Date: "
				+ DateUtil.convertDateToStr(DateUtil.getCurrentDate(), DateUtil.GLOBAL_DATE_FORMAT)});

		// Line Break
		data.add(new String[]{""});
			
		data.add(new String[] { "Account No", "Account Name", "Division Code", "Division Name", "Department Code", "Department Name", 
			"Card No", "Name on Card", "Action Type", "Action Details", "Request Date", "Request By", "Requestor Reason", "Requestor Remarks",
			"Approval Date", "Approval By", "Approval Status", "Approval Remarks"
			});
		
		// Data
		List<Object[]> results = this.businessHelper.getReportBusiness().getApprovalRequestRecords(acctType, acctNo, acctName, cardNo, requestStartDate, requestEndDate, approvalStartDate, approvalEndDate, actionType, approvalStatus, entity, sortBy);
		if (results.size() == 0) {
			data.add(new String[]{"No records found"});
		} else {
			
			List<String[]> strResults = convertToStringArray(results);
			for(String[] record: strResults){
				//card no
				record[6] = appendWithQuote(record[6]);
				
				//approval status
				record[16] = NonConfigurableConstants.PREPAID_APPROVAL_STATUS.get(record[16]);
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