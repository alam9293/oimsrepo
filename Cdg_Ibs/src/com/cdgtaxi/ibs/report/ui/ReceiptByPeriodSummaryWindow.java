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
import org.zkoss.zul.Listitem;

import com.cdgtaxi.ibs.Uri;
import com.cdgtaxi.ibs.common.NonConfigurableConstants;
import com.cdgtaxi.ibs.report.Constants;
import com.cdgtaxi.ibs.util.DateUtil;
import com.elixirtech.ers2.client.ERSClient;
import com.elixirtech.net.NetException;

public class ReceiptByPeriodSummaryWindow extends ReportWindow implements CSVFormat {
	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(ReceiptByPeriodSummaryWindow.class);
	public ReceiptByPeriodSummaryWindow() throws IOException {
		super("Receipt By Period Summary", "Receipt");
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
//			StringBuffer dataInCSVPayment = this.generateCSVDataPayment(reportParamsProperties);
//			AMedia media2 = new AMedia(report.replaceAll(" ", "_")+"_payment"+"."+extension, extension, format, dataInCSVPayment.toString());
//			Filedownload.save(media2);
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
		String receiptStart = (String)reportParamsProperties.get("1. Receipt Start Date");
		String receiptEnd = (String)reportParamsProperties.get("2. Receipt End Date");
		String paymentMode = (String)reportParamsProperties.get("3. Payment Mode");
		String entityNo = (String)reportParamsProperties.get("4. Entity");
		String salespersonNo = (String)reportParamsProperties.get("5. Sales Person");
		StringBuffer buffer = new StringBuffer();
		//Report Header
		buffer.append(Constants.TEXT_QUALIFIER+"Integrated Billing System"+Constants.TEXT_QUALIFIER+",");
		buffer.append("\n");
		buffer.append(Constants.TEXT_QUALIFIER+"Receipt By Period Summary"+Constants.TEXT_QUALIFIER+",");
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
		if(paymentMode!=null && paymentMode.length()>0){
			String payment = ((Listbox)this.getFellow("3. Payment Mode")).getSelectedItem().getLabel();
			buffer.append(Constants.TEXT_QUALIFIER+"Payment Mode: "+payment+Constants.TEXT_QUALIFIER+",");
			buffer.append("\n");
		}
		
		if(entityNo!=null && entityNo.length()>0) {
			String entityName = ((Listbox)this.getFellow("4. Entity")).getSelectedItem().getLabel();
			buffer.append(Constants.TEXT_QUALIFIER+"Entity: "+entityName+Constants.TEXT_QUALIFIER+",");
			buffer.append("\n");
		}
		if(salespersonNo!=null && salespersonNo.length()>0){
			String salesperson = ((Listbox)this.getFellow("5. Sales Person")).getSelectedItem().getLabel();
			buffer.append(Constants.TEXT_QUALIFIER+"Sales Person: "+salesperson+Constants.TEXT_QUALIFIER+",");
			buffer.append("\n");
		}
		
		buffer.append("\n");
		buffer.append("\n");
		buffer.append(Constants.TEXT_QUALIFIER+"Printed By: "+reportParamsProperties.get("printedBy")+Constants.TEXT_QUALIFIER+",");
		buffer.append("\n");
		buffer.append(Constants.TEXT_QUALIFIER+"Report Date: "+DateUtil.convertDateToStr(DateUtil.getCurrentDate(), DateUtil.GLOBAL_DATE_FORMAT)+Constants.TEXT_QUALIFIER+",");
		buffer.append("\n");
		buffer.append("Summary By Date\n");
		//Column Title
		buffer.append(Constants.TEXT_QUALIFIER+"S/N"+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+"Date"+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+"Receipt Amt ($)"+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+"Cancelled Amt ($)"+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+"Net Received ($)"+Constants.TEXT_QUALIFIER+",");
		buffer.append("\n");
		List<Object[]> results = this.businessHelper.getReportBusiness().getReceiptByPeriodSummaryDate(
				receiptStart!=null && receiptStart.length()!=0 ? receiptStart + " 00:00:00" : receiptEnd!=null && receiptEnd.length()!=0 ? receiptEnd + " 00:00:00" : receiptStart,
				receiptEnd!=null && receiptEnd.length()!=0 ? receiptEnd + " 23:59:59" : receiptStart!=null && receiptStart.length()!=0 ? receiptStart + " 23:59:59" : receiptEnd,
				paymentMode, entityNo, salespersonNo
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
				String netTotal = (new BigDecimal(result[1]==null || result[1].toString().length()==0 ? "0" : (String)result[1].toString().replaceAll(",", ""))).subtract(new BigDecimal(result[2]==null || result[2].toString().length()==0 ? "0" : (String)result[2].toString().replaceAll(",", ""))).toString();
				buffer.append(netTotal);
				buffer.append("\n");
			}
		}else{
			buffer.append(""+Constants.TEXT_QUALIFIER+"No records found."+Constants.TEXT_QUALIFIER+",");
			buffer.append("\n");
		}
		buffer.append("\n");
		buffer.append("Summary By Payment Mode\n");
		//Column Title
		buffer.append(Constants.TEXT_QUALIFIER+"S/N"+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+"Payment Mode"+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+"Receipt Amt ($)"+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+"Cancelled Amt ($)"+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+"Net Received ($)"+Constants.TEXT_QUALIFIER+",");
		buffer.append("\n");
		results = this.businessHelper.getReportBusiness().getReceiptByPeriodSummaryPayment(
				receiptStart!=null && receiptStart.length()!=0 ? receiptStart + " 00:00:00" : receiptEnd!=null && receiptEnd.length()!=0 ? receiptEnd + " 00:00:00" : receiptStart,
				receiptEnd!=null && receiptEnd.length()!=0 ? receiptEnd + " 23:59:59" : receiptStart!=null && receiptStart.length()!=0 ? receiptStart + " 23:59:59" : receiptEnd,
				paymentMode, entityNo, salespersonNo
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
				String netTotal = (new BigDecimal(result[1]==null || result[1].toString().length()==0 ? "0" : (String)result[1].toString().replaceAll(",", ""))).subtract(new BigDecimal(result[2]==null || result[2].toString().length()==0 ? "0" : (String)result[2].toString().replaceAll(",", ""))).toString();
				buffer.append(netTotal);
				buffer.append("\n");
			}
		}else{
			buffer.append(""+Constants.TEXT_QUALIFIER+"No records found."+Constants.TEXT_QUALIFIER+",");
			buffer.append("\n");
		}
		return buffer;
	}
	public void removeMemoPaymentMode(){
		Listbox paymentModes = (Listbox)this.getFellow("3. Payment Mode");
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