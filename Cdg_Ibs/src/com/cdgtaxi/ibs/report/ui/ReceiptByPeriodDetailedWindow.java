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
import org.zkoss.zul.Listitem;

import com.cdgtaxi.ibs.Uri;
import com.cdgtaxi.ibs.common.NonConfigurableConstants;
import com.cdgtaxi.ibs.report.Constants;
import com.cdgtaxi.ibs.util.DateUtil;
import com.elixirtech.ers2.client.ERSClient;
import com.elixirtech.net.NetException;

public class ReceiptByPeriodDetailedWindow extends ReportWindow implements CSVFormat {
	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(ReceiptByPeriodDetailedWindow.class);
	public ReceiptByPeriodDetailedWindow() throws IOException {
		super("Receipt By Period Detailed", "Receipt");
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
		String receiptStart = (String)reportParamsProperties.get("01. Receipt Start Date");
		String receiptEnd = (String)reportParamsProperties.get("02. Receipt End Date");
		String cancelStart = (String)reportParamsProperties.get("03. Cancel Start Date");
		String cancelEnd = (String)reportParamsProperties.get("04. Cancel End Date");
		String acctNo = (String)reportParamsProperties.get("05a. Account No");
		String acctName = (String)reportParamsProperties.get("05b. Account Name");
		String invoiceNo = (String)reportParamsProperties.get("06. Invoice No");
		String receiptNo = (String)reportParamsProperties.get("07. Receipt No");
		String paymentMode = (String)reportParamsProperties.get("08. Payment Mode");
		String order = (String)reportParamsProperties.get("09. Order");
		String entityNo = (String)reportParamsProperties.get("10. Entity");
		String salespersonNo = (String)reportParamsProperties.get("11. Sales Person");
		StringBuffer buffer = new StringBuffer();
		//Report Header
		buffer.append(Constants.TEXT_QUALIFIER+"Integrated Billing System"+Constants.TEXT_QUALIFIER+",");
		buffer.append("\n");
		buffer.append(Constants.TEXT_QUALIFIER+"Receipt By Period Detailed"+Constants.TEXT_QUALIFIER+",");
		buffer.append("\n");
		buffer.append(Constants.TEXT_QUALIFIER+"Selection Criteria:"+Constants.TEXT_QUALIFIER+",");
		buffer.append("\n");
		buffer.append("\n");
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
		if((cancelStart!=null && cancelStart.length()>0) || (cancelEnd!=null && cancelEnd.length()!=0)){
			buffer.append(Constants.TEXT_QUALIFIER+"Cancelled Date: ");
			if(cancelStart!=null && cancelStart.length()>0){
				buffer.append(cancelStart);
			}else{
				buffer.append(cancelEnd);
			}
			buffer.append(" to ");
			if(cancelEnd!=null && cancelEnd.length()!=0){
				buffer.append(cancelEnd+Constants.TEXT_QUALIFIER+",");
			}else{
				buffer.append(cancelStart+Constants.TEXT_QUALIFIER+",");
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
		if(invoiceNo!=null && invoiceNo.length()>0){
			buffer.append(Constants.TEXT_QUALIFIER+"Invoice No: "+invoiceNo+Constants.TEXT_QUALIFIER+",");
			buffer.append("\n");
		}
		if(receiptNo!=null && receiptNo.length()>0){
			buffer.append(Constants.TEXT_QUALIFIER+"Receipt No: "+receiptNo+Constants.TEXT_QUALIFIER+",");
			buffer.append("\n");
		}
		if(paymentMode!=null && paymentMode.length()>0){
			String payment = ((Listbox)this.getFellow("08. Payment Mode")).getSelectedItem().getLabel();
			buffer.append(Constants.TEXT_QUALIFIER+"Payment Mode: "+payment+Constants.TEXT_QUALIFIER+",");
			buffer.append("\n");
		}
		String orderBy = ((Listbox)this.getFellow("09. Order")).getSelectedItem().getLabel();
		if(entityNo!=null && entityNo.length()>0){
			String entityName = ((Listbox)this.getFellow("10. Entity")).getSelectedItem().getLabel();
			buffer.append(Constants.TEXT_QUALIFIER+"Entity Name: "+entityName+Constants.TEXT_QUALIFIER+",");
			buffer.append("\n");
		}
		if(salespersonNo!=null && salespersonNo.length()>0){
			String salesperson = ((Listbox)this.getFellow("11. Sales Person")).getSelectedItem().getLabel();
			buffer.append(Constants.TEXT_QUALIFIER+"Sales Person: "+salesperson+Constants.TEXT_QUALIFIER+",");
			buffer.append("\n");
		}
		buffer.append(Constants.TEXT_QUALIFIER+"Order: "+orderBy+Constants.TEXT_QUALIFIER+",");
		buffer.append("\n");
		buffer.append("\n");
		buffer.append(Constants.TEXT_QUALIFIER+"Printed By: "+reportParamsProperties.get("printedBy")+Constants.TEXT_QUALIFIER+",");
		buffer.append("\n");
		buffer.append(Constants.TEXT_QUALIFIER+"Report Date: "+DateUtil.convertDateToStr(DateUtil.getCurrentDate(), DateUtil.GLOBAL_DATE_FORMAT)+Constants.TEXT_QUALIFIER+",");
		buffer.append("\n");
		buffer.append("\n");
		//Column Title
		buffer.append(Constants.TEXT_QUALIFIER+"S/N"+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+"Account No"+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+"Account Name"+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+"Receipt No"+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+"Receipt Date"+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+"Cancelled Date"+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+"Applied Amt($)"+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+"Excess Amt($)"+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+"Total Amt($)"+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+"Cancelled Amt($)"+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+"Net Received Amt($)"+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+"Payment Mode"+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+"Cheque/Account No."+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+"Bank"+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+"Salesperson"+Constants.TEXT_QUALIFIER+",");
		buffer.append("\n");
		//Data
		List<Object[]> results = this.businessHelper.getReportBusiness().getReceiptByPeriodDetailed(
				receiptStart!=null && receiptStart.length()!=0 ? receiptStart + " 00:00:00" : receiptEnd!=null && receiptEnd.length()!=0 ? receiptEnd + " 00:00:00" : receiptStart,
				receiptEnd!=null && receiptEnd.length()!=0 ? receiptEnd + " 23:59:59" : receiptStart!=null && receiptStart.length()!=0 ? receiptStart + " 23:59:59" : receiptEnd,
				cancelStart!=null && cancelStart.length()!=0 ? cancelStart + " 00:00:00" : cancelEnd!=null && cancelEnd.length()!=0 ? cancelEnd + " 00:00:00" : cancelStart,
				cancelEnd!=null && cancelEnd.length()!=0 ? cancelEnd + " 23:59:59" : cancelStart!=null && cancelStart.length()!=0 ? cancelStart + " 23:59:59" : cancelEnd,
				"%" + acctNo + "%",
				"%" + acctName + "%",
				invoiceNo,
				receiptNo,
				paymentMode,
				order,
				entityNo,
				salespersonNo
				
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
	public void removeMemoPaymentMode(){
		Listbox paymentModes = (Listbox)this.getFellow("08. Payment Mode");
		Listitem memoItem = null;
		for(Object paymentMode : paymentModes.getChildren()){
			Listitem paymentItem = (Listitem)paymentMode;
			if(paymentItem.getValue().equals(NonConfigurableConstants.PAYMENT_MODE_MEMO)){
				memoItem = paymentItem;
				break;
			}
		}
		if(memoItem!=null){
			paymentModes.removeChild(memoItem);
		}
	}
}