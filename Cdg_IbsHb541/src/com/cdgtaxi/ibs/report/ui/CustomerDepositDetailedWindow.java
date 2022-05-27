package com.cdgtaxi.ibs.report.ui;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
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

public class CustomerDepositDetailedWindow extends ReportWindow implements CSVFormat {
	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(CustomerDepositDetailedWindow.class);
	public CustomerDepositDetailedWindow() throws IOException {
		super("Customer Deposit Detailed", "Account");
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
		String depositStart = (String)reportParamsProperties.get("1. Deposit Invoice Start Date");
		String depositEnd = (String)reportParamsProperties.get("2. Deposit Invoice End Date");
		String receiptStart = (String)reportParamsProperties.get("3. Receipt Start Date");
		String receiptEnd = (String)reportParamsProperties.get("4. Receipt End Date");
		String acctNo = (String)reportParamsProperties.get("5a. Account No");
		String acctName = (String)reportParamsProperties.get("5b. Account Name");
		String entity = (String)reportParamsProperties.get("6. Entity");
		String depositStatus = (String)reportParamsProperties.get("7. Deposit Status");
		String sort = (String)reportParamsProperties.get("8. Sort By");
		StringBuffer buffer = new StringBuffer();
		//Report Header
		buffer.append(Constants.TEXT_QUALIFIER+"Integrated Billing System"+Constants.TEXT_QUALIFIER+",");
		buffer.append("\n");
		buffer.append(Constants.TEXT_QUALIFIER+"Customer Deposit Detailed"+Constants.TEXT_QUALIFIER+",");
		buffer.append("\n");
		buffer.append(Constants.TEXT_QUALIFIER+"Selection Criteria:"+Constants.TEXT_QUALIFIER+",");
		buffer.append("\n");
		buffer.append("\n");
		if((depositStart!=null && depositStart.length()>0) || (depositEnd!=null && depositEnd.length()!=0)){
			buffer.append(Constants.TEXT_QUALIFIER+"Deposit Invoice Date: ");
			if(depositStart!=null && depositStart.length()>0){
				buffer.append(depositStart);
			}else{
				buffer.append(depositEnd);
			}
			buffer.append(" to ");
			if(depositEnd!=null && depositEnd.length()!=0){
				buffer.append(depositEnd+Constants.TEXT_QUALIFIER+",");
			}else{
				buffer.append(depositStart+Constants.TEXT_QUALIFIER+",");
			}
			buffer.append("\n");
		}
		if((receiptStart!=null && receiptStart.length()>0) || (receiptEnd!=null && receiptEnd.length()!=0)){
			buffer.append(Constants.TEXT_QUALIFIER+"Receipt Date: ");
			if(receiptStart!=null && receiptStart.length()>0){
				buffer.append(receiptStart);
			}else{
				buffer.append(receiptEnd);
			}
			buffer.append(" to ");
			if(receiptEnd!=null && receiptEnd.length()!=0){
				buffer.append(receiptEnd+Constants.TEXT_QUALIFIER+",");
			}else{
				buffer.append(receiptStart+Constants.TEXT_QUALIFIER+",");
			}
			buffer.append("\n");
		}
		if(acctNo!=null && acctNo.length()>0){
			buffer.append(Constants.TEXT_QUALIFIER+"Account No: "+acctNo+Constants.TEXT_QUALIFIER+",");
			buffer.append("\n");
		}
		if(acctName!=null && acctName.length()>0){
			buffer.append(Constants.TEXT_QUALIFIER+"Account Name: "+acctName+Constants.TEXT_QUALIFIER+",");
			buffer.append("\n");
		}
		if(entity!=null && entity.length()>0){
			String entityName = ((Listbox)this.getFellow("6. Entity")).getSelectedItem().getLabel();
			buffer.append(Constants.TEXT_QUALIFIER+"Entity: "+entityName+Constants.TEXT_QUALIFIER+",");
			buffer.append("\n");
		}
		if(depositStatus!=null && depositStatus.length()>0){
			String depositStatusName = ((Listbox)this.getFellow("7. Deposit Status")).getSelectedItem().getLabel();
			buffer.append(Constants.TEXT_QUALIFIER+"Deposit Status: "+depositStatusName+Constants.TEXT_QUALIFIER+",");
			buffer.append("\n");
		}
		if(sort!=null && sort.length()>0){
			String sortBy = ((Listbox)this.getFellow("8. Sort By")).getSelectedItem().getLabel();
			buffer.append(Constants.TEXT_QUALIFIER+"Sort By: "+sortBy+Constants.TEXT_QUALIFIER+",");
			buffer.append("\n");
		}
		buffer.append("\n");
		buffer.append("\n");
		buffer.append(Constants.TEXT_QUALIFIER+"Printed By: "+reportParamsProperties.get("printedBy")+Constants.TEXT_QUALIFIER+",");
		buffer.append("\n");
		buffer.append(Constants.TEXT_QUALIFIER+"Report Date: "+DateUtil.convertDateToStr(DateUtil.getCurrentDate(), DateUtil.GLOBAL_DATE_FORMAT)+Constants.TEXT_QUALIFIER+",");
		buffer.append("\n");
		buffer.append("Receipts\n");
		//Column Title
		buffer.append(Constants.TEXT_QUALIFIER+"S/N"+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+"Account No"+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+"Account Name"+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+"Invoice No"+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+"Invoice Date"+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+"Receipt No"+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+"Receipt Date"+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+"Applied Amt ($)"+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+"Deposit Amt ($)"+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+"Received Deposit Amt ($)"+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+"Outstanding Deposit Amt ($)"+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+"Status"+Constants.TEXT_QUALIFIER+",");
		buffer.append("\n");
		List<Object[]> results = this.businessHelper.getReportBusiness().getCustomerDepositDetailedReceipts(
				depositStart!=null && depositStart.length()!=0 ? depositStart + " 00:00:00" : depositEnd!=null && depositEnd.length()!=0 ? depositEnd + " 00:00:00" : depositStart,
				depositEnd!=null && depositEnd.length()!=0 ? depositEnd + " 23:59:59" : depositStart!=null && depositStart.length()!=0 ? depositStart + " 23:59:59" : depositEnd,
				receiptStart!=null && receiptStart.length()!=0 ? receiptStart + " 00:00:00" : receiptEnd!=null && receiptEnd.length()!=0 ? receiptEnd + " 00:00:00" : receiptStart,
				receiptEnd!=null && receiptEnd.length()!=0 ? receiptEnd + " 23:59:59" : receiptStart!=null && receiptStart.length()!=0 ? receiptStart + " 23:59:59" : receiptEnd,
				"%"+acctNo+"%",
				"%"+acctName+"%",
				entity,
				depositStatus,
				sort
		);
		logger.info("size = " + results.size());
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
		buffer.append("\n");
		buffer.append("Refunds\n");
		//Column Title
		buffer.append(Constants.TEXT_QUALIFIER+"S/N"+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+"Account No"+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+"Account Name"+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+"Credit Note/Memo No"+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+"Credit Note/Refund Date"+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+"Offset Amt ($)"+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+"Refund Amt ($)"+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+"Deposit Amt ($)"+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+"Balance ($)"+Constants.TEXT_QUALIFIER+",");
		buffer.append("\n");
		results = this.businessHelper.getReportBusiness().getCustomerDepositDetailedRefunds(
				depositStart!=null && depositStart.length()!=0 ? depositStart + " 00:00:00" : depositEnd!=null && depositEnd.length()!=0 ? depositEnd + " 00:00:00" : depositStart,
				depositEnd!=null && depositEnd.length()!=0 ? depositEnd + " 23:59:59" : depositStart!=null && depositStart.length()!=0 ? depositStart + " 23:59:59" : depositEnd,
				receiptStart!=null && receiptStart.length()!=0 ? receiptStart + " 00:00:00" : receiptEnd!=null && receiptEnd.length()!=0 ? receiptEnd + " 00:00:00" : receiptStart,
				receiptEnd!=null && receiptEnd.length()!=0 ? receiptEnd + " 23:59:59" : receiptStart!=null && receiptStart.length()!=0 ? receiptStart + " 23:59:59" : receiptEnd,
				"%"+acctNo+"%",
				"%"+acctName+"%",
				entity
		);
		logger.info("size = " + results.size());
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