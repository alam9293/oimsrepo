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
import com.cdgtaxi.ibs.report.Constants;
import com.cdgtaxi.ibs.report.exception.FormatNotSupportedException;
import com.cdgtaxi.ibs.util.DateUtil;
import com.elixirtech.ers2.client.ERSClient;
import com.elixirtech.net.NetException;

public class CardStatisticsReport extends ReportWindow implements CSVFormat {
	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(CardStatisticsReport.class);
	public CardStatisticsReport() throws IOException {
		super("Card Statistics Report", "Product");
	}
	public void removeNewStatuses(){
		Listbox statuses = (Listbox)this.getFellow("11. Product Status");
		Listitem newItem = null;
		for(Object status : statuses.getChildren()){
			Listitem statusItem = (Listitem)status;
			if(statusItem.getValue().equals("N")){
				newItem = statusItem;
				break;
			}
		}
		if(newItem!=null){
			statuses.removeChild(newItem);
		}
		statuses = (Listbox)this.getFellow("12. Account Status");
		newItem = null;
		for(Object status : statuses.getChildren()){
			Listitem statusItem = (Listitem)status;
			if(statusItem.getValue().equals("N")){
				newItem = statusItem;
				break;
			}
		}
		if(newItem!=null){
			statuses.removeChild(newItem);
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
		String productStatusDate = (String)reportParamsProperties.get("01. Product Status Date");
		String issueStart = (String)reportParamsProperties.get("02. Issue Start Date");
		String issueEnd = (String)reportParamsProperties.get("03. Issue End Date");
		String terminateStart = (String)reportParamsProperties.get("04. Termination Start Date");
		String terminateEnd = (String)reportParamsProperties.get("05. Termination End Date");
		String suspendStart = (String)reportParamsProperties.get("06. Suspension Start Date");
		String suspendEnd = (String)reportParamsProperties.get("07. Suspension End Date");
		String replaceStart = (String)reportParamsProperties.get("08. Replacement Start Date");
		String replaceEnd = (String)reportParamsProperties.get("09. Replacement End Date");
		String productType = (String)reportParamsProperties.get("10. Product Type");
		String productStatus = (String)reportParamsProperties.get("11. Product Status");
		String acctStatus = (String)reportParamsProperties.get("12. Account Status");
		StringBuffer buffer = new StringBuffer();
		//Report Header
		buffer.append(Constants.TEXT_QUALIFIER+"Integrated Billing System"+Constants.TEXT_QUALIFIER+",");
		buffer.append("\n");
		buffer.append(Constants.TEXT_QUALIFIER+"Card Statistics Report"+Constants.TEXT_QUALIFIER+",");
		buffer.append("\n");
		buffer.append(Constants.TEXT_QUALIFIER+"Selection Criteria:"+Constants.TEXT_QUALIFIER+",");
		buffer.append("\n");
		buffer.append("\n");
		if(productStatusDate!=null && productStatusDate.length()>0){
			buffer.append(Constants.TEXT_QUALIFIER+"Product Status Date: "+productStatusDate+Constants.TEXT_QUALIFIER+",");
			buffer.append("\n");
		}
		if((issueStart!=null && issueStart.length()>0) || (issueEnd!=null && issueEnd.length()!=0)){
			buffer.append(Constants.TEXT_QUALIFIER+"Issue Date: ");
			if(issueStart!=null && issueStart.length()>0){
				buffer.append(issueStart);
			}else{
				buffer.append(issueEnd);
			}
			buffer.append(" to ");
			if(issueEnd!=null && issueEnd.length()!=0){
				buffer.append(issueEnd+Constants.TEXT_QUALIFIER+",");
			}else{
				buffer.append(issueStart+Constants.TEXT_QUALIFIER+",");
			}
			buffer.append("\n");
		}
		if((terminateStart!=null && terminateStart.length()>0) || (terminateEnd!=null && terminateEnd.length()!=0)){
			buffer.append(Constants.TEXT_QUALIFIER+"Terminated Date: ");
			if(terminateStart!=null && terminateStart.length()>0){
				buffer.append(terminateStart);
			}else{
				buffer.append(terminateEnd);
			}
			buffer.append(" to ");
			if(terminateEnd!=null && terminateEnd.length()!=0){
				buffer.append(terminateEnd+Constants.TEXT_QUALIFIER+",");
			}else{
				buffer.append(terminateStart+Constants.TEXT_QUALIFIER+",");
			}
			buffer.append("\n");
		}
		if((suspendStart!=null && suspendStart.length()>0) || (suspendEnd!=null && suspendEnd.length()!=0)){
			buffer.append(Constants.TEXT_QUALIFIER+"Suspension Date: ");
			if(suspendStart!=null && suspendStart.length()>0){
				buffer.append(suspendStart);
			}else{
				buffer.append(suspendEnd);
			}
			buffer.append(" to ");
			if(suspendEnd!=null && suspendEnd.length()!=0){
				buffer.append(suspendEnd+Constants.TEXT_QUALIFIER+",");
			}else{
				buffer.append(suspendStart+Constants.TEXT_QUALIFIER+",");
			}
			buffer.append("\n");
		}
		if((replaceStart!=null && replaceStart.length()>0) || (replaceEnd!=null && replaceEnd.length()!=0)){
			buffer.append(Constants.TEXT_QUALIFIER+"Replacement Date: ");
			if(replaceStart!=null && replaceStart.length()>0){
				buffer.append(replaceStart);
			}else{
				buffer.append(replaceEnd);
			}
			buffer.append(" to ");
			if(replaceEnd!=null && replaceEnd.length()!=0){
				buffer.append(replaceEnd+Constants.TEXT_QUALIFIER+",");
			}else{
				buffer.append(replaceStart+Constants.TEXT_QUALIFIER+",");
			}
			buffer.append("\n");
		}
		if(productType!=null && productType.length()>0){
			String productTypeName = ((Listbox)this.getFellow("10. Product Type")).getSelectedItem().getLabel();
			buffer.append(Constants.TEXT_QUALIFIER+"Product Type: "+productTypeName+Constants.TEXT_QUALIFIER+",");
			buffer.append("\n");
		}
		if(productStatus!=null && productStatus.length()>0){
			String productStatusName = ((Listbox)this.getFellow("11. Product Status")).getSelectedItem().getLabel();
			buffer.append(Constants.TEXT_QUALIFIER+"Product Status: "+productStatusName+Constants.TEXT_QUALIFIER+",");
			buffer.append("\n");
		}
		if(acctStatus!=null && acctStatus.length()>0){
			String acctStatusName = ((Listbox)this.getFellow("12. Account Status")).getSelectedItem().getLabel();
			buffer.append(Constants.TEXT_QUALIFIER+"Account Status: "+acctStatusName+Constants.TEXT_QUALIFIER+",");
			buffer.append("\n");
		}
		buffer.append("\n");
		buffer.append(Constants.TEXT_QUALIFIER+"Printed By: "+reportParamsProperties.get("printedBy")+Constants.TEXT_QUALIFIER+",");
		buffer.append("\n");
		buffer.append(Constants.TEXT_QUALIFIER+"Report Date: "+DateUtil.convertDateToStr(DateUtil.getCurrentDate(), DateUtil.GLOBAL_DATE_FORMAT)+Constants.TEXT_QUALIFIER+",");
		buffer.append("\n");
		buffer.append("\n");
		//Column Title
		buffer.append(Constants.TEXT_QUALIFIER+"Account No"+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+"Account Name"+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+"Closed Date"+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+"Termination Date"+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+"Suspended Date"+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+"No of Active Cards (A)"+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+"Terminated (a)"+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+"Suspended (b)"+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+"Parent Suspended (c)"+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+"Replacement use back old card number (d)"+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+"Lost (e)"+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+"Used (f)"+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+"Total Inactive Cards (B=a+b+c+d+e+f)"+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+"Grand Total (A+B)"+Constants.TEXT_QUALIFIER+",");
		buffer.append("\n");
		//Data
		List<Object[]> results = this.businessHelper.getReportBusiness().getCardStatisticReport(
				productStatusDate + " 23:59:59",
				issueStart!=null && issueStart.length()!=0 ? issueStart + " 00:00:00" : issueEnd!=null && issueEnd.length()!=0 ? issueEnd + " 00:00:00" : issueStart,
				issueEnd!=null && issueEnd.length()!=0 ? issueEnd + " 23:59:59" : issueStart!=null && issueStart.length()!=0 ? issueStart + " 23:59:59" : issueEnd,
				terminateStart!=null && terminateStart.length()!=0 ? terminateStart + " 00:00:00" : terminateEnd!=null && terminateEnd.length()!=0 ? terminateEnd + " 00:00:00" : terminateStart,
				terminateEnd!=null && terminateEnd.length()!=0 ? terminateEnd + " 23:59:59" : terminateStart!=null && terminateStart.length()!=0 ? terminateStart + " 23:59:59" : terminateEnd,
				suspendStart!=null && suspendStart.length()!=0 ? suspendStart + " 00:00:00" : suspendEnd!=null && suspendEnd.length()!=0 ? suspendEnd + " 00:00:00" : suspendStart,
				suspendEnd!=null && suspendEnd.length()!=0 ? suspendEnd + " 23:59:59" : suspendStart!=null && suspendStart.length()!=0 ? suspendStart + " 23:59:59" : suspendEnd,
				replaceStart!=null && replaceStart.length()!=0 ? replaceStart + " 00:00:00" : replaceEnd!=null && replaceEnd.length()!=0 ? replaceEnd + " 00:00:00" : replaceStart,
				replaceEnd!=null && replaceEnd.length()!=0 ? replaceEnd + " 23:59:59" : replaceStart!=null && replaceStart.length()!=0 ? replaceStart + " 23:59:59" : replaceEnd,
				productType, productStatus, acctStatus
		);
		logger.info("size = " + results.size());
		if(results.size()!=0){
			int[] totals = {0, 0, 0, 0, 0, 0, 0, 0, 0};
			for(int i=0;i<results.size(); i++){
				Object[] result = results.get(i);
				if(result[5]!=null && result[5].toString().length()!=0){
					totals[0] += Integer.parseInt(result[5].toString());
				}
				if(result[6]!=null && result[6].toString().length()!=0){
					totals[1] += Integer.parseInt(result[6].toString());
				}
				if(result[7]!=null && result[7].toString().length()!=0){
					totals[2] += Integer.parseInt(result[7].toString());
				}
				if(result[8]!=null && result[8].toString().length()!=0){
					totals[3] += Integer.parseInt(result[8].toString());
				}
				if(result[9]!=null && result[9].toString().length()!=0){
					totals[4] += Integer.parseInt(result[9].toString());
				}
				if(result[10]!=null && result[10].toString().length()!=0){
					totals[5] += Integer.parseInt(result[10].toString());
				}
				if(result[11]!=null && result[11].toString().length()!=0){
					totals[6] += Integer.parseInt(result[11].toString());
				}
				if(result[12]!=null && result[12].toString().length()!=0){
					totals[7] += Integer.parseInt(result[12].toString());
				}
				if(result[13]!=null && result[13].toString().length()!=0){
					totals[8] += Integer.parseInt(result[13].toString());
				}
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
			buffer.append(Constants.TEXT_QUALIFIER+Constants.TEXT_QUALIFIER+",");
			buffer.append(Constants.TEXT_QUALIFIER+"Total"+Constants.TEXT_QUALIFIER+",");
			buffer.append(Constants.TEXT_QUALIFIER+Constants.TEXT_QUALIFIER+",");
			buffer.append(Constants.TEXT_QUALIFIER+Constants.TEXT_QUALIFIER+",");
			buffer.append(Constants.TEXT_QUALIFIER+Constants.TEXT_QUALIFIER+",");
			buffer.append(Constants.TEXT_QUALIFIER+totals[0]+Constants.TEXT_QUALIFIER+",");
			buffer.append(Constants.TEXT_QUALIFIER+totals[1]+Constants.TEXT_QUALIFIER+",");
			buffer.append(Constants.TEXT_QUALIFIER+totals[2]+Constants.TEXT_QUALIFIER+",");
			buffer.append(Constants.TEXT_QUALIFIER+totals[3]+Constants.TEXT_QUALIFIER+",");
			buffer.append(Constants.TEXT_QUALIFIER+totals[4]+Constants.TEXT_QUALIFIER+",");
			buffer.append(Constants.TEXT_QUALIFIER+totals[5]+Constants.TEXT_QUALIFIER+",");
			buffer.append(Constants.TEXT_QUALIFIER+totals[6]+Constants.TEXT_QUALIFIER+",");
			buffer.append(Constants.TEXT_QUALIFIER+totals[7]+Constants.TEXT_QUALIFIER+",");
			buffer.append(Constants.TEXT_QUALIFIER+totals[8]+Constants.TEXT_QUALIFIER+",");
			buffer.append("\n");
		}else{
			buffer.append(""+Constants.TEXT_QUALIFIER+"No records found."+Constants.TEXT_QUALIFIER+",");
			buffer.append("\n");
		}
		// now for summary
		buffer.append(Constants.TEXT_QUALIFIER+"Summary"+Constants.TEXT_QUALIFIER);
		buffer.append("\n");
		results = this.businessHelper.getReportBusiness().getCardStatisticReportSummary(
				productStatusDate + " 23:59:59",
				issueStart!=null && issueStart.length()!=0 ? issueStart + " 00:00:00" : issueEnd!=null && issueEnd.length()!=0 ? issueEnd + " 00:00:00" : issueStart,
				issueEnd!=null && issueEnd.length()!=0 ? issueEnd + " 23:59:59" : issueStart!=null && issueStart.length()!=0 ? issueStart + " 23:59:59" : issueEnd,
				terminateStart!=null && terminateStart.length()!=0 ? terminateStart + " 00:00:00" : terminateEnd!=null && terminateEnd.length()!=0 ? terminateEnd + " 00:00:00" : terminateStart,
				terminateEnd!=null && terminateEnd.length()!=0 ? terminateEnd + " 23:59:59" : terminateStart!=null && terminateStart.length()!=0 ? terminateStart + " 23:59:59" : terminateEnd,
				suspendStart!=null && suspendStart.length()!=0 ? suspendStart + " 00:00:00" : suspendEnd!=null && suspendEnd.length()!=0 ? suspendEnd + " 00:00:00" : suspendStart,
				suspendEnd!=null && suspendEnd.length()!=0 ? suspendEnd + " 23:59:59" : suspendStart!=null && suspendStart.length()!=0 ? suspendStart + " 23:59:59" : suspendEnd,
				replaceStart!=null && replaceStart.length()!=0 ? replaceStart + " 00:00:00" : replaceEnd!=null && replaceEnd.length()!=0 ? replaceEnd + " 00:00:00" : replaceStart,
				replaceEnd!=null && replaceEnd.length()!=0 ? replaceEnd + " 23:59:59" : replaceStart!=null && replaceStart.length()!=0 ? replaceStart + " 23:59:59" : replaceEnd,
				productType, productStatus, acctStatus
		);
		logger.info("size = " + results.size());
		if(results.size()!=0){
			for(int i=0;i<results.size(); i++){
				Object[] result = results.get(i);
				for(int j=0;j<result.length;j++){//for(Object data : result){
					switch(j){
					case 0:
						buffer.append(""+Constants.TEXT_QUALIFIER+"No. of Active Account"+Constants.TEXT_QUALIFIER+",");
						break;
					case 1:
						buffer.append(""+Constants.TEXT_QUALIFIER+"No. of Closed Account"+Constants.TEXT_QUALIFIER+",");
						break;
					case 2:
						buffer.append(""+Constants.TEXT_QUALIFIER+"No. of Terminated Account"+Constants.TEXT_QUALIFIER+",");
						break;
					case 3:
						buffer.append(""+Constants.TEXT_QUALIFIER+"No. of Suspended Account"+Constants.TEXT_QUALIFIER+",");
						break;
					default:
						buffer.append(""+Constants.TEXT_QUALIFIER+""+Constants.TEXT_QUALIFIER+",");
						break;
					}
					logger.info("j = " + j);
					Object data = result[j];
					logger.info("data = " + data);
					if(data!=null){
						logger.info("in not null");
						buffer.append(""+Constants.TEXT_QUALIFIER+data+Constants.TEXT_QUALIFIER);
					}else{
						logger.info("in null");
						buffer.append(Constants.TEXT_QUALIFIER+Constants.TEXT_QUALIFIER);
					}
					buffer.append("\n");
				}
			}
		}else{
			buffer.append(""+Constants.TEXT_QUALIFIER+"No records found."+Constants.TEXT_QUALIFIER+",");
			buffer.append("\n");
		}
		return buffer;
	}
}