package com.cdgtaxi.ibs.report.ui;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;

import org.apache.commons.httpclient.HttpException;
import org.apache.log4j.Logger;
import org.zkoss.util.media.AMedia;
import org.zkoss.zul.Filedownload;
import org.zkoss.zul.Listbox;

import com.cdgtaxi.ibs.Uri;
import com.cdgtaxi.ibs.common.NonConfigurableConstants;
import com.cdgtaxi.ibs.report.Constants;
import com.cdgtaxi.ibs.util.DateUtil;
import com.elixirtech.ers2.client.ERSClient;
import com.elixirtech.net.NetException;

public class DailyChequeDepositListingWindow extends ReportWindow implements CSVFormat {
	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(DailyChequeDepositListingWindow.class);
	public DailyChequeDepositListingWindow() throws IOException {
		super("Daily Cheque Deposit Listing", "Receipt");
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
	public StringBuffer generateCSVData(Properties reportParamsProperties) {
		// TODO Auto-generated method stub
		
		String receivedStartDate = (String)reportParamsProperties.get("1. Received Start Date");
		String receivedEndDate = (String)reportParamsProperties.get("2. Received End Date");
		String entity = (String)reportParamsProperties.get("3. Entity");
		String quickCheckDeposit = (String)reportParamsProperties.get("4. Quick Check Deposit");
		String order = (String)reportParamsProperties.get("5. Order");
		StringBuffer buffer = new StringBuffer();
		//Report Header
		buffer.append(Constants.TEXT_QUALIFIER+"Integrated Billing System"+Constants.TEXT_QUALIFIER+",");
		buffer.append("\n");
		buffer.append(Constants.TEXT_QUALIFIER+"Daily Cheque Deposit Listing"+Constants.TEXT_QUALIFIER+",");
		buffer.append("\n");
		buffer.append("\n");
		buffer.append(Constants.TEXT_QUALIFIER+"Selection Criteria:"+Constants.TEXT_QUALIFIER+",");
		buffer.append("\n");
		
		if((receivedStartDate!=null && receivedStartDate.length()>0) || (receivedEndDate!=null && receivedEndDate.length()!=0)){
			buffer.append(Constants.TEXT_QUALIFIER+"Received Date: ");
			if(receivedStartDate!=null && receivedStartDate.length()>0){
				buffer.append(receivedStartDate);
			}else{
				buffer.append(receivedEndDate);
			}
			buffer.append(" to ");
			if(receivedEndDate!=null && receivedEndDate.length()!=0){
				buffer.append(receivedEndDate+Constants.TEXT_QUALIFIER+",");
			}else{
				buffer.append(receivedStartDate+Constants.TEXT_QUALIFIER+",");
			}
			buffer.append("\n");
		}
		
		if(quickCheckDeposit !=null && quickCheckDeposit.length() > 0){
			String quickCheckDepositStr = ((Listbox)this.getFellow("4. Quick Check Deposit")).getSelectedItem().getLabel();
			buffer.append(Constants.TEXT_QUALIFIER+"Quick Check Deposit: "+quickCheckDepositStr+Constants.TEXT_QUALIFIER+",");
			buffer.append("\n");
		}	
		
		if(entity !=null && entity.length() > 0){
			String entityStr = ((Listbox)this.getFellow("3. Entity")).getSelectedItem().getLabel();
			buffer.append(Constants.TEXT_QUALIFIER+"Entity: "+entityStr+Constants.TEXT_QUALIFIER+",");
			buffer.append("\n");
		}	
		
		if(order  !=null && order .length() > 0){
			buffer.append(Constants.TEXT_QUALIFIER+"Order : "+order +Constants.TEXT_QUALIFIER);
			buffer.append("\n");
		}	
		
		buffer.append("\n");
		buffer.append(Constants.TEXT_QUALIFIER+"Printed By: "+reportParamsProperties.get("printedBy")+Constants.TEXT_QUALIFIER+",");
		buffer.append("\n");
		buffer.append(Constants.TEXT_QUALIFIER+"Report Date: "+DateUtil.convertDateToStr(DateUtil.getCurrentDate(), DateUtil.GLOBAL_DATE_FORMAT)+Constants.TEXT_QUALIFIER+",");
		buffer.append("\n");
		buffer.append("\n");
		
		//Column Title
		buffer.append(Constants.TEXT_QUALIFIER+"S/N"+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+"Bank"+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+"Branch"+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+"Cheque No"+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+"Received Date"+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+"Amount($)"+Constants.TEXT_QUALIFIER);
		buffer.append("\n");
		
		//Data
		List<Object[]> results = this.businessHelper.getReportBusiness().getDailyChequeDepositListing(
				receivedStartDate!=null && receivedStartDate.length()!=0 ? receivedStartDate + " 00:00:00" : receivedEndDate!=null && receivedEndDate.length()!=0 ? receivedEndDate + " 00:00:00" : receivedStartDate,
				receivedEndDate!=null && receivedEndDate.length()!=0 ? receivedEndDate + " 23:59:59" : receivedStartDate!=null && receivedStartDate.length()!=0 ? receivedStartDate + " 23:59:59" : receivedEndDate,
				entity,
				quickCheckDeposit,
				order
		);
		
		if(results.size()!=0){
			for(int i=0;i<results.size(); i++){
				buffer.append(""+Constants.TEXT_QUALIFIER+(i+1)+Constants.TEXT_QUALIFIER+",");
				Object[] result = results.get(i);
				for(int j=0;j<result.length;j++){//for(Object data : result){
					Object data = result[j];
					if(data!=null){
						buffer.append(""+Constants.TEXT_QUALIFIER+data+Constants.TEXT_QUALIFIER+",");
					}else{
						buffer.append(Constants.TEXT_QUALIFIER+Constants.TEXT_QUALIFIER+",");
					}
				}
				buffer.append("\n");
			}
		}else{
			buffer.append(""+Constants.TEXT_QUALIFIER+"No records found."+Constants.TEXT_QUALIFIER+",");
			buffer.append("\n");
		}
		return buffer;
		
	}
}