package com.cdgtaxi.ibs.report.ui;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.httpclient.HttpException;
import org.apache.log4j.Logger;
import org.zkoss.util.media.AMedia;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Filedownload;
import org.zkoss.zul.Listbox;

import com.cdgtaxi.ibs.Uri;
import com.cdgtaxi.ibs.report.Constants;
import com.cdgtaxi.ibs.report.exception.FormatNotSupportedException;
import com.cdgtaxi.ibs.util.DateUtil;
import com.cdgtaxi.ibs.util.StringUtil;
import com.elixirtech.ers2.client.ERSClient;
import com.elixirtech.net.NetException;

public class MonthlyDebtManagementReport extends ReportWindow implements CSVFormat {
	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(MonthlyDebtManagementReport.class);
	public MonthlyDebtManagementReport() throws IOException {
		super("Monthly Debt Management Report", "Aging");
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
		String acctTypeNo = (String)reportParamsProperties.get("1. Account Type");
		String acctStatus = (String)reportParamsProperties.get("2. Account Status");
		String receiptMonth = (String)reportParamsProperties.get("3. Receipt Action By Month");
		String industryCode = (String)reportParamsProperties.get("4. Business Nature");
		String salespersonNo = (String)reportParamsProperties.get("5. Sales Person");
		String entityNo = (String)reportParamsProperties.get("6. Entity");
		
		StringBuffer buffer = new StringBuffer();
		//Report Header
		buffer.append(Constants.TEXT_QUALIFIER+"Integrated Billing System"+Constants.TEXT_QUALIFIER+",");
		buffer.append("\n");
		buffer.append(Constants.TEXT_QUALIFIER+"Monthly Debt Management Report"+Constants.TEXT_QUALIFIER+",");
		buffer.append("\n");
		buffer.append(Constants.TEXT_QUALIFIER+"Selection Criteria:"+Constants.TEXT_QUALIFIER+",");
		buffer.append("\n");
		buffer.append("\n");
		if(acctTypeNo!=null && acctTypeNo.length()>0){
			String acctTypeName = ((Listbox)this.getFellow("1. Account Type")).getSelectedItem().getLabel();
			buffer.append(Constants.TEXT_QUALIFIER+"Account Type: "+acctTypeName+Constants.TEXT_QUALIFIER+",");
			buffer.append("\n");
		}
		if(acctStatus!=null && acctStatus.length()>0){
			String acctStatusName = ((Listbox)this.getFellow("2. Account Status")).getSelectedItem().getLabel();
			buffer.append(Constants.TEXT_QUALIFIER+"Account Status: "+acctStatusName+Constants.TEXT_QUALIFIER+",");
			buffer.append("\n");
		}
		if(receiptMonth!=null && receiptMonth.length()>0){
			buffer.append(Constants.TEXT_QUALIFIER+"Receipt Action By Month: "+this.convertYYYYMMDDtoMMMYYYY(receiptMonth)+Constants.TEXT_QUALIFIER+",");
			buffer.append("\n");
		}
		if(industryCode!=null && industryCode.length()>0){
			String industryName = ((Listbox)this.getFellow("4. Business Nature")).getSelectedItem().getLabel();
			buffer.append(Constants.TEXT_QUALIFIER+"Business Nature: "+industryName+Constants.TEXT_QUALIFIER+",");
			buffer.append("\n");
		}
		if(salespersonNo!=null && salespersonNo.length()>0){
			String salespersonName = ((Listbox)this.getFellow("5. Sales Person")).getSelectedItem().getLabel();
			buffer.append(Constants.TEXT_QUALIFIER+"Sales Person: "+salespersonName+Constants.TEXT_QUALIFIER+",");
			buffer.append("\n");
		}
		
		if(entityNo!=null && entityNo.length()>0) {
			String entityName = ((Listbox)this.getFellow("6. Entity")).getSelectedItem().getLabel();
			buffer.append(Constants.TEXT_QUALIFIER+"Entity: "+entityName+Constants.TEXT_QUALIFIER+",");
			buffer.append("\n");
		}
		
		buffer.append("\n");
		buffer.append(Constants.TEXT_QUALIFIER+"Printed By: "+reportParamsProperties.get("printedBy")+Constants.TEXT_QUALIFIER+",");
		buffer.append("\n");
		buffer.append(Constants.TEXT_QUALIFIER+"Report Date: "+DateUtil.convertDateToStr(DateUtil.getCurrentDate(), DateUtil.GLOBAL_DATE_FORMAT)+Constants.TEXT_QUALIFIER+",");
		buffer.append("\n");
		buffer.append("\n");
		// debts
		//Column Title
		buffer.append(Constants.TEXT_QUALIFIER+"Total Opening Debt"+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+"Total"+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+"Current"+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+"1 - 30 Days"+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+"31 - 60 Days"+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+"60 Days & Above"+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+Constants.TEXT_QUALIFIER+",");
		buffer.append("\n");
		Calendar selectedCalendar = Calendar.getInstance();
		selectedCalendar.setTimeInMillis(((Datebox)this.getFellow("3. Receipt Action By Month")).getValue().getTime());
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		selectedCalendar.set(Calendar.MONTH, selectedCalendar.get(Calendar.MONTH)-1);
		selectedCalendar.set(Calendar.DAY_OF_MONTH, selectedCalendar.getActualMaximum(Calendar.DAY_OF_MONTH));
		buffer.append(Constants.TEXT_QUALIFIER+"as at "+dateFormat.format(selectedCalendar.getTime())+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+"Amount ($)"+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+"Amount ($)"+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+"% of Total Debt By Salesperson"+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+"% of Grand Total Debt"+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+"% By Age"+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+"Amount ($)"+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+"% of Total Debt By Salesperson"+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+"% of Grand Total Debt"+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+"% By Age"+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+"Amount ($)"+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+"% of Total Debt By Salesperson"+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+"% of Grand Total Debt"+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+"% By Age"+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+"Amount ($)"+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+"% of Total Debt By Salesperson"+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+"% of Grand Total Debt"+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+"% By Age"+Constants.TEXT_QUALIFIER+",");
		buffer.append("\n");
		//Data
		List<Object[]> results = this.businessHelper.getReportBusiness().getMonthlyDebtManagementDebt(
				acctTypeNo, acctStatus, receiptMonth, industryCode, salespersonNo, entityNo
		);
		logger.info("size = " + results.size());
		if(results.size()!=0){
			// looping thru once to get the total first
			Map<String, Double> totals = new HashMap<String, Double>(); 
			for(int i=0;i<results.size(); i++){
				Object[] result = results.get(i);
				totals.put("total", totals.get("total")!=null ? totals.get("total") + Double.parseDouble(((String)result[1]).replaceAll(",", "")) : Double.parseDouble(((String)result[1]).replaceAll(",", "")));
				totals.put("current", totals.get("current")!=null ? totals.get("current") + Double.parseDouble(((String)result[2]).replaceAll(",", "")) : Double.parseDouble(((String)result[2]).replaceAll(",", "")));
				totals.put("less30", totals.get("less30")!=null ? totals.get("less30") + Double.parseDouble(((String)result[3]).replaceAll(",", "")) : Double.parseDouble(((String)result[3]).replaceAll(",", "")));
				totals.put("less60", totals.get("less60")!=null ? totals.get("less60") + Double.parseDouble(((String)result[4]).replaceAll(",", "")) : Double.parseDouble(((String)result[4]).replaceAll(",", "")));
				totals.put("more60", totals.get("more60")!=null ? totals.get("more60") + Double.parseDouble(((String)result[5]).replaceAll(",", "")) : Double.parseDouble(((String)result[5]).replaceAll(",", "")));
			}
			DecimalFormat df = new DecimalFormat(StringUtil.GLOBAL_DECIMAL_FORMAT);
			for(int i=0;i<results.size(); i++){
				Object[] result = results.get(i);
				for(int j=0;j<result.length;j++){//for(Object data : result){
					Object data = result[j];
					if(data!=null){
						if(j==0){// name
							buffer.append(""+Constants.TEXT_QUALIFIER+data+Constants.TEXT_QUALIFIER+",");
						}else if(j==1){
							buffer.append(""+Constants.TEXT_QUALIFIER+data+Constants.TEXT_QUALIFIER+",");
						}else if(j==2){
							buffer.append(""+Constants.TEXT_QUALIFIER+data+Constants.TEXT_QUALIFIER+",");
							double current = Double.parseDouble(((String)data).replaceAll(",", ""));
							double total = Double.parseDouble(((String)result[1]).replaceAll(",", ""));
							double percentBySP = 100*current/total;
							buffer.append(""+Constants.TEXT_QUALIFIER+df.format(percentBySP)+Constants.TEXT_QUALIFIER+",");
							double percentByTotal = 100*current/((totals.get("total")!=null && totals.get("total") !=0) ? totals.get("total") : 1);
							buffer.append(""+Constants.TEXT_QUALIFIER+df.format(percentByTotal)+Constants.TEXT_QUALIFIER+",");
							double percentByAge = 100*current/((totals.get("current")!=null && totals.get("current")!=0) ? totals.get("current") : 1);
							buffer.append(""+Constants.TEXT_QUALIFIER+df.format(percentByAge)+Constants.TEXT_QUALIFIER+",");
						}else if(j==3){
							buffer.append(""+Constants.TEXT_QUALIFIER+data+Constants.TEXT_QUALIFIER+",");
							double less30 = Double.parseDouble(((String)data).replaceAll(",", ""));
							double total = Double.parseDouble(((String)result[1]).replaceAll(",", ""));
							double percentBySP = 100*less30/total;
							buffer.append(""+Constants.TEXT_QUALIFIER+df.format(percentBySP)+Constants.TEXT_QUALIFIER+",");
							double percentByTotal = 100*less30/((totals.get("total")!=null && totals.get("total") !=0) ? totals.get("total") : 1);
							buffer.append(""+Constants.TEXT_QUALIFIER+df.format(percentByTotal)+Constants.TEXT_QUALIFIER+",");
							double percentByAge = 100*less30/((totals.get("less30")!=null && totals.get("less30")!=0) ? totals.get("less30") : 1);
							buffer.append(""+Constants.TEXT_QUALIFIER+df.format(percentByAge)+Constants.TEXT_QUALIFIER+",");
						}else if(j==4){
							buffer.append(""+Constants.TEXT_QUALIFIER+data+Constants.TEXT_QUALIFIER+",");
							double less60 = Double.parseDouble(((String)data).replaceAll(",", ""));
							double total = Double.parseDouble(((String)result[1]).replaceAll(",", ""));
							double percentBySP = 100*less60/total;
							buffer.append(""+Constants.TEXT_QUALIFIER+df.format(percentBySP)+Constants.TEXT_QUALIFIER+",");
							double percentByTotal = 100*less60/((totals.get("total")!=null && totals.get("total") !=0) ? totals.get("total") : 1);
							buffer.append(""+Constants.TEXT_QUALIFIER+df.format(percentByTotal)+Constants.TEXT_QUALIFIER+",");
							double percentByAge = 100*less60/((totals.get("less60")!=null && totals.get("less60")!=0) ? totals.get("less60") : 1);
							buffer.append(""+Constants.TEXT_QUALIFIER+df.format(percentByAge)+Constants.TEXT_QUALIFIER+",");
						}else if(j==5){
							buffer.append(""+Constants.TEXT_QUALIFIER+data+Constants.TEXT_QUALIFIER+",");
							double more60 = Double.parseDouble(((String)data).replaceAll(",", ""));
							double total = Double.parseDouble(((String)result[1]).replaceAll(",", ""));
							double percentBySP = 100*more60/total;
							buffer.append(""+Constants.TEXT_QUALIFIER+df.format(percentBySP)+Constants.TEXT_QUALIFIER+",");
							double percentByTotal = 100*more60/((totals.get("total")!=null && totals.get("total") !=0) ? totals.get("total") : 1);
							buffer.append(""+Constants.TEXT_QUALIFIER+df.format(percentByTotal)+Constants.TEXT_QUALIFIER+",");
							double percentByAge = 100*more60/((totals.get("more60")!=null && totals.get("more60")!=0) ? totals.get("more60") : 1);
							buffer.append(""+Constants.TEXT_QUALIFIER+df.format(percentByAge)+Constants.TEXT_QUALIFIER+",");
						}
					}else{
						buffer.append(Constants.TEXT_QUALIFIER+Constants.TEXT_QUALIFIER+",");
					}
				}
				buffer.append("\n");
			}
			buffer.append(""+Constants.TEXT_QUALIFIER+"Grand Total"+Constants.TEXT_QUALIFIER+",");
			buffer.append(""+Constants.TEXT_QUALIFIER+df.format(totals.get("total"))+Constants.TEXT_QUALIFIER+",");
			buffer.append(""+Constants.TEXT_QUALIFIER+df.format(totals.get("current"))+Constants.TEXT_QUALIFIER+",");
			buffer.append(""+Constants.TEXT_QUALIFIER+df.format(100*totals.get("current")/totals.get("total"))+Constants.TEXT_QUALIFIER+",");
			buffer.append(""+Constants.TEXT_QUALIFIER+df.format(100*totals.get("current")/totals.get("total"))+Constants.TEXT_QUALIFIER+",");
			buffer.append(""+Constants.TEXT_QUALIFIER+df.format(100.00)+Constants.TEXT_QUALIFIER+",");
			buffer.append(""+Constants.TEXT_QUALIFIER+df.format(totals.get("less30"))+Constants.TEXT_QUALIFIER+",");
			buffer.append(""+Constants.TEXT_QUALIFIER+df.format(100*totals.get("less30")/totals.get("total"))+Constants.TEXT_QUALIFIER+",");
			buffer.append(""+Constants.TEXT_QUALIFIER+df.format(100*totals.get("less30")/totals.get("total"))+Constants.TEXT_QUALIFIER+",");
			buffer.append(""+Constants.TEXT_QUALIFIER+df.format(100.00)+Constants.TEXT_QUALIFIER+",");
			buffer.append(""+Constants.TEXT_QUALIFIER+df.format(totals.get("less60"))+Constants.TEXT_QUALIFIER+",");
			buffer.append(""+Constants.TEXT_QUALIFIER+df.format(100*totals.get("less60")/totals.get("total"))+Constants.TEXT_QUALIFIER+",");
			buffer.append(""+Constants.TEXT_QUALIFIER+df.format(100*totals.get("less60")/totals.get("total"))+Constants.TEXT_QUALIFIER+",");
			buffer.append(""+Constants.TEXT_QUALIFIER+df.format(100.00)+Constants.TEXT_QUALIFIER+",");
			buffer.append(""+Constants.TEXT_QUALIFIER+df.format(totals.get("more60"))+Constants.TEXT_QUALIFIER+",");
			buffer.append(""+Constants.TEXT_QUALIFIER+df.format(100*totals.get("more60")/totals.get("total"))+Constants.TEXT_QUALIFIER+",");
			buffer.append(""+Constants.TEXT_QUALIFIER+df.format(100*totals.get("more60")/totals.get("total"))+Constants.TEXT_QUALIFIER+",");
			buffer.append(""+Constants.TEXT_QUALIFIER+df.format(100.00)+Constants.TEXT_QUALIFIER+",");
			buffer.append("\n");
		}else{
			buffer.append(""+Constants.TEXT_QUALIFIER+"No records found."+Constants.TEXT_QUALIFIER+",");
			buffer.append("\n");
		}
		buffer.append("\n");
		// received
		//Column Title
		buffer.append(Constants.TEXT_QUALIFIER+"Total Receivables"+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+"Total"+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+"Current"+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+"1 - 30 Days"+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+"31 - 60 Days"+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+"60 Days & Above"+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+Constants.TEXT_QUALIFIER+",");
		buffer.append("\n");
		selectedCalendar.set(Calendar.MONTH, selectedCalendar.get(Calendar.MONTH)+1);
		selectedCalendar.set(Calendar.DAY_OF_MONTH, selectedCalendar.getActualMinimum(Calendar.DAY_OF_MONTH));
		buffer.append(Constants.TEXT_QUALIFIER+dateFormat.format(selectedCalendar.getTime()));
		selectedCalendar.set(Calendar.DAY_OF_MONTH, selectedCalendar.getActualMaximum(Calendar.DAY_OF_MONTH));
		buffer.append(" to "+dateFormat.format(selectedCalendar.getTime())+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+"Amount ($)"+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+"Amount ($)"+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+"% of Total Received By Salesperson"+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+"% of Grand Total Received"+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+"% By Age"+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+"Amount ($)"+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+"% of Total Received By Salesperson"+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+"% of Grand Total Received"+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+"% By Age"+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+"Amount ($)"+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+"% of Total Received By Salesperson"+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+"% of Grand Total Received"+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+"% By Age"+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+"Amount ($)"+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+"% of Total Received By Salesperson"+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+"% of Grand Total Received"+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+"% By Age"+Constants.TEXT_QUALIFIER+",");
		buffer.append("\n");
		//Data
		results = this.businessHelper.getReportBusiness().getMonthlyDebtManagementReceived(
				acctTypeNo, acctStatus, receiptMonth, industryCode, salespersonNo, entityNo
		);
		logger.info("size = " + results.size());
		if(results.size()!=0){
			// looping thru once to get the total first
			Map<String, Double> totals = new HashMap<String, Double>(); 
			for(int i=0;i<results.size(); i++){
				Object[] result = results.get(i);
				totals.put("total", totals.get("total")!=null ? totals.get("total") + Double.parseDouble(((String)result[1]).replaceAll(",", "")) : Double.parseDouble(((String)result[1]).replaceAll(",", "")));
				totals.put("current", totals.get("current")!=null ? totals.get("current") + Double.parseDouble(((String)result[2]).replaceAll(",", "")) : Double.parseDouble(((String)result[2]).replaceAll(",", "")));
				totals.put("less30", totals.get("less30")!=null ? totals.get("less30") + Double.parseDouble(((String)result[3]).replaceAll(",", "")) : Double.parseDouble(((String)result[3]).replaceAll(",", "")));
				totals.put("less60", totals.get("less60")!=null ? totals.get("less60") + Double.parseDouble(((String)result[4]).replaceAll(",", "")) : Double.parseDouble(((String)result[4]).replaceAll(",", "")));
				totals.put("more60", totals.get("more60")!=null ? totals.get("more60") + Double.parseDouble(((String)result[5]).replaceAll(",", "")) : Double.parseDouble(((String)result[5]).replaceAll(",", "")));
			}
			DecimalFormat df = new DecimalFormat(StringUtil.GLOBAL_DECIMAL_FORMAT);
			for(int i=0;i<results.size(); i++){
				Object[] result = results.get(i);
				for(int j=0;j<result.length;j++){//for(Object data : result){
					Object data = result[j];
					if(data!=null){
						if(j==0){// name
							buffer.append(""+Constants.TEXT_QUALIFIER+data+Constants.TEXT_QUALIFIER+",");
						}else if(j==1){
							buffer.append(""+Constants.TEXT_QUALIFIER+data+Constants.TEXT_QUALIFIER+",");
						}else if(j==2){
							buffer.append(""+Constants.TEXT_QUALIFIER+data+Constants.TEXT_QUALIFIER+",");
							double current = Double.parseDouble(((String)data).replaceAll(",", ""));
							double total = Double.parseDouble(((String)result[1]).replaceAll(",", ""));
							double percentBySP = 100*current/total;
							buffer.append(""+Constants.TEXT_QUALIFIER+df.format(percentBySP)+Constants.TEXT_QUALIFIER+",");
							double percentByTotal = 100*current/((totals.get("total")!=null && totals.get("total")!=0) ? totals.get("total") : 1);
							buffer.append(""+Constants.TEXT_QUALIFIER+df.format(percentByTotal)+Constants.TEXT_QUALIFIER+",");
							double percentByAge = 100*current/((totals.get("current")!=null && totals.get("current")!=0) ? totals.get("current") : 1);
							buffer.append(""+Constants.TEXT_QUALIFIER+df.format(percentByAge)+Constants.TEXT_QUALIFIER+",");
						}else if(j==3){
							buffer.append(""+Constants.TEXT_QUALIFIER+data+Constants.TEXT_QUALIFIER+",");
							double less30 = Double.parseDouble(((String)data).replaceAll(",", ""));
							double total = Double.parseDouble(((String)result[1]).replaceAll(",", ""));
							double percentBySP = 100*less30/total;
							buffer.append(""+Constants.TEXT_QUALIFIER+df.format(percentBySP)+Constants.TEXT_QUALIFIER+",");
							double percentByTotal = 100*less30/((totals.get("total")!=null && totals.get("total")!=0) ? totals.get("total") : 1);
							buffer.append(""+Constants.TEXT_QUALIFIER+df.format(percentByTotal)+Constants.TEXT_QUALIFIER+",");
							double percentByAge = 100*less30/((totals.get("less30")!=null && totals.get("less30")!=0) ? totals.get("less30") : 1);
							buffer.append(""+Constants.TEXT_QUALIFIER+df.format(percentByAge)+Constants.TEXT_QUALIFIER+",");
						}else if(j==4){
							buffer.append(""+Constants.TEXT_QUALIFIER+data+Constants.TEXT_QUALIFIER+",");
							double less60 = Double.parseDouble(((String)data).replaceAll(",", ""));
							double total = Double.parseDouble(((String)result[1]).replaceAll(",", ""));
							double percentBySP = 100*less60/total;
							buffer.append(""+Constants.TEXT_QUALIFIER+df.format(percentBySP)+Constants.TEXT_QUALIFIER+",");
							double percentByTotal = 100*less60/((totals.get("total")!=null && totals.get("total")!=0) ? totals.get("total") : 1);
							buffer.append(""+Constants.TEXT_QUALIFIER+df.format(percentByTotal)+Constants.TEXT_QUALIFIER+",");
							double percentByAge = 100*less60/((totals.get("less60")!=null && totals.get("less60")!=0) ? totals.get("less60") : 1);
							buffer.append(""+Constants.TEXT_QUALIFIER+df.format(percentByAge)+Constants.TEXT_QUALIFIER+",");
						}else if(j==5){
							buffer.append(""+Constants.TEXT_QUALIFIER+data+Constants.TEXT_QUALIFIER+",");
							double more60 = Double.parseDouble(((String)data).replaceAll(",", ""));
							double total = Double.parseDouble(((String)result[1]).replaceAll(",", ""));
							double percentBySP = 100*more60/total;
							buffer.append(""+Constants.TEXT_QUALIFIER+df.format(percentBySP)+Constants.TEXT_QUALIFIER+",");
							double percentByTotal = 100*more60/((totals.get("total")!=null && totals.get("total")!=0) ? totals.get("total") : 1);
							buffer.append(""+Constants.TEXT_QUALIFIER+df.format(percentByTotal)+Constants.TEXT_QUALIFIER+",");
							double percentByAge = 100*more60/((totals.get("more60")!=null && totals.get("more60")!=0) ? totals.get("more60") : 1);
							buffer.append(""+Constants.TEXT_QUALIFIER+df.format(percentByAge)+Constants.TEXT_QUALIFIER+",");
						}
					}else{
						buffer.append(Constants.TEXT_QUALIFIER+Constants.TEXT_QUALIFIER+",");
					}
				}
				buffer.append("\n");
			}
			buffer.append(""+Constants.TEXT_QUALIFIER+"Grand Total"+Constants.TEXT_QUALIFIER+",");
			buffer.append(""+Constants.TEXT_QUALIFIER+df.format(totals.get("total"))+Constants.TEXT_QUALIFIER+",");
			buffer.append(""+Constants.TEXT_QUALIFIER+df.format(totals.get("current"))+Constants.TEXT_QUALIFIER+",");
			buffer.append(""+Constants.TEXT_QUALIFIER+df.format(100*totals.get("current")/totals.get("total"))+Constants.TEXT_QUALIFIER+",");
			buffer.append(""+Constants.TEXT_QUALIFIER+df.format(100*totals.get("current")/totals.get("total"))+Constants.TEXT_QUALIFIER+",");
			buffer.append(""+Constants.TEXT_QUALIFIER+df.format(100.00)+Constants.TEXT_QUALIFIER+",");
			buffer.append(""+Constants.TEXT_QUALIFIER+df.format(totals.get("less30"))+Constants.TEXT_QUALIFIER+",");
			buffer.append(""+Constants.TEXT_QUALIFIER+df.format(100*totals.get("less30")/totals.get("total"))+Constants.TEXT_QUALIFIER+",");
			buffer.append(""+Constants.TEXT_QUALIFIER+df.format(100*totals.get("less30")/totals.get("total"))+Constants.TEXT_QUALIFIER+",");
			buffer.append(""+Constants.TEXT_QUALIFIER+df.format(100.00)+Constants.TEXT_QUALIFIER+",");
			buffer.append(""+Constants.TEXT_QUALIFIER+df.format(totals.get("less60"))+Constants.TEXT_QUALIFIER+",");
			buffer.append(""+Constants.TEXT_QUALIFIER+df.format(100*totals.get("less60")/totals.get("total"))+Constants.TEXT_QUALIFIER+",");
			buffer.append(""+Constants.TEXT_QUALIFIER+df.format(100*totals.get("less60")/totals.get("total"))+Constants.TEXT_QUALIFIER+",");
			buffer.append(""+Constants.TEXT_QUALIFIER+df.format(100.00)+Constants.TEXT_QUALIFIER+",");
			buffer.append(""+Constants.TEXT_QUALIFIER+df.format(totals.get("more60"))+Constants.TEXT_QUALIFIER+",");
			buffer.append(""+Constants.TEXT_QUALIFIER+df.format(100*totals.get("more60")/totals.get("total"))+Constants.TEXT_QUALIFIER+",");
			buffer.append(""+Constants.TEXT_QUALIFIER+df.format(100*totals.get("more60")/totals.get("total"))+Constants.TEXT_QUALIFIER+",");
			buffer.append(""+Constants.TEXT_QUALIFIER+df.format(100.00)+Constants.TEXT_QUALIFIER+",");
			buffer.append("\n");
		}else{
			buffer.append(""+Constants.TEXT_QUALIFIER+"No records found."+Constants.TEXT_QUALIFIER+",");
			buffer.append("\n");
		}
		buffer.append("\n");
		// closing
		//Column Title
		buffer.append(Constants.TEXT_QUALIFIER+"Total Closing Debt"+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+"Total"+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+"Current"+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+"1 - 30 Days"+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+"31 - 60 Days"+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+"60 Days & Above"+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+Constants.TEXT_QUALIFIER+",");
		buffer.append("\n");
		buffer.append(Constants.TEXT_QUALIFIER+"as at "+dateFormat.format(selectedCalendar.getTime())+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+"Amount ($)"+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+"Amount ($)"+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+"% of Total Closing Debt By Salesperson"+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+"% of Grand Total Closing Debt"+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+"% By Age"+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+"Amount ($)"+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+"% of Total Closing Debt By Salesperson"+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+"% of Grand Total Closing Debt"+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+"% By Age"+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+"Amount ($)"+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+"% of Total Closing Debt By Salesperson"+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+"% of Grand Total Closing Debt"+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+"% By Age"+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+"Amount ($)"+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+"% of Total Closing Debt By Salesperson"+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+"% of Grand Total Closing Debt"+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+"% By Age"+Constants.TEXT_QUALIFIER+",");
		buffer.append("\n");
		//Data
		results = this.businessHelper.getReportBusiness().getMonthlyDebtManagementClosing(
				acctTypeNo, acctStatus, receiptMonth, industryCode, salespersonNo, entityNo
		);
		logger.info("size = " + results.size());
		if(results.size()!=0){
			// looping thru once to get the total first
			Map<String, Double> totals = new HashMap<String, Double>(); 
			for(int i=0;i<results.size(); i++){
				Object[] result = results.get(i);
				totals.put("total", totals.get("total")!=null ? totals.get("total") + Double.parseDouble(((String)result[1]).replaceAll(",", "")) : Double.parseDouble(((String)result[1]).replaceAll(",", "")));
				totals.put("current", totals.get("current")!=null ? totals.get("current") + Double.parseDouble(((String)result[2]).replaceAll(",", "")) : Double.parseDouble(((String)result[2]).replaceAll(",", "")));
				totals.put("less30", totals.get("less30")!=null ? totals.get("less30") + Double.parseDouble(((String)result[3]).replaceAll(",", "")) : Double.parseDouble(((String)result[3]).replaceAll(",", "")));
				totals.put("less60", totals.get("less60")!=null ? totals.get("less60") + Double.parseDouble(((String)result[4]).replaceAll(",", "")) : Double.parseDouble(((String)result[4]).replaceAll(",", "")));
				totals.put("more60", totals.get("more60")!=null ? totals.get("more60") + Double.parseDouble(((String)result[5]).replaceAll(",", "")) : Double.parseDouble(((String)result[5]).replaceAll(",", "")));
			}
			DecimalFormat df = new DecimalFormat(StringUtil.GLOBAL_DECIMAL_FORMAT);
			for(int i=0;i<results.size(); i++){
				Object[] result = results.get(i);
				for(int j=0;j<result.length;j++){//for(Object data : result){
					Object data = result[j];
					if(data!=null){
						if(j==0){// name
							buffer.append(""+Constants.TEXT_QUALIFIER+data+Constants.TEXT_QUALIFIER+",");
						}else if(j==1){
							buffer.append(""+Constants.TEXT_QUALIFIER+data+Constants.TEXT_QUALIFIER+",");
						}else if(j==2){
							buffer.append(""+Constants.TEXT_QUALIFIER+data+Constants.TEXT_QUALIFIER+",");
							double current = Double.parseDouble(((String)data).replaceAll(",", ""));
							double total = Double.parseDouble(((String)result[1]).replaceAll(",", ""));
							double percentBySP = 100*current/total;
							buffer.append(""+Constants.TEXT_QUALIFIER+df.format(percentBySP)+Constants.TEXT_QUALIFIER+",");
							double percentByTotal = 100*current/((totals.get("total")!=null && totals.get("total") !=0) ? totals.get("total") : 1);
							buffer.append(""+Constants.TEXT_QUALIFIER+df.format(percentByTotal)+Constants.TEXT_QUALIFIER+",");
							double percentByAge = 100*current/((totals.get("current")!=null && totals.get("current")!=0) ? totals.get("current") : 1);
							buffer.append(""+Constants.TEXT_QUALIFIER+df.format(percentByAge)+Constants.TEXT_QUALIFIER+",");
						}else if(j==3){
							buffer.append(""+Constants.TEXT_QUALIFIER+data+Constants.TEXT_QUALIFIER+",");
							double less30 = Double.parseDouble(((String)data).replaceAll(",", ""));
							double total = Double.parseDouble(((String)result[1]).replaceAll(",", ""));
							double percentBySP = 100*less30/total;
							buffer.append(""+Constants.TEXT_QUALIFIER+df.format(percentBySP)+Constants.TEXT_QUALIFIER+",");
							double percentByTotal = 100*less30/((totals.get("total")!=null && totals.get("total") !=0) ? totals.get("total") : 1);
							buffer.append(""+Constants.TEXT_QUALIFIER+df.format(percentByTotal)+Constants.TEXT_QUALIFIER+",");
							double percentByAge = 100*less30/((totals.get("less30")!=null && totals.get("less30")!=0) ? totals.get("less30") : 1);
							buffer.append(""+Constants.TEXT_QUALIFIER+df.format(percentByAge)+Constants.TEXT_QUALIFIER+",");
						}else if(j==4){
							buffer.append(""+Constants.TEXT_QUALIFIER+data+Constants.TEXT_QUALIFIER+",");
							double less60 = Double.parseDouble(((String)data).replaceAll(",", ""));
							double total = Double.parseDouble(((String)result[1]).replaceAll(",", ""));
							double percentBySP = 100*less60/total;
							buffer.append(""+Constants.TEXT_QUALIFIER+df.format(percentBySP)+Constants.TEXT_QUALIFIER+",");
							double percentByTotal = 100*less60/((totals.get("total")!=null && totals.get("total") !=0) ? totals.get("total") : 1);
							buffer.append(""+Constants.TEXT_QUALIFIER+df.format(percentByTotal)+Constants.TEXT_QUALIFIER+",");
							double percentByAge = 100*less60/((totals.get("less60")!=null && totals.get("less60")!=0) ? totals.get("less60") : 1);
							buffer.append(""+Constants.TEXT_QUALIFIER+df.format(percentByAge)+Constants.TEXT_QUALIFIER+",");
						}else if(j==5){
							buffer.append(""+Constants.TEXT_QUALIFIER+data+Constants.TEXT_QUALIFIER+",");
							double more60 = Double.parseDouble(((String)data).replaceAll(",", ""));
							double total = Double.parseDouble(((String)result[1]).replaceAll(",", ""));
							double percentBySP = 100*more60/total;
							buffer.append(""+Constants.TEXT_QUALIFIER+df.format(percentBySP)+Constants.TEXT_QUALIFIER+",");
							double percentByTotal = 100*more60/((totals.get("total")!=null && totals.get("total") !=0) ? totals.get("total") : 1);
							buffer.append(""+Constants.TEXT_QUALIFIER+df.format(percentByTotal)+Constants.TEXT_QUALIFIER+",");
							double percentByAge = 100*more60/((totals.get("more60")!=null && totals.get("more60")!=0) ? totals.get("more60") : 1);
							buffer.append(""+Constants.TEXT_QUALIFIER+df.format(percentByAge)+Constants.TEXT_QUALIFIER+",");
						}
					}else{
						buffer.append(Constants.TEXT_QUALIFIER+Constants.TEXT_QUALIFIER+",");
					}
				}
				buffer.append("\n");
			}
			buffer.append(""+Constants.TEXT_QUALIFIER+"Grand Total"+Constants.TEXT_QUALIFIER+",");
			buffer.append(""+Constants.TEXT_QUALIFIER+df.format(totals.get("total"))+Constants.TEXT_QUALIFIER+",");
			buffer.append(""+Constants.TEXT_QUALIFIER+df.format(totals.get("current"))+Constants.TEXT_QUALIFIER+",");
			buffer.append(""+Constants.TEXT_QUALIFIER+df.format(100*totals.get("current")/totals.get("total"))+Constants.TEXT_QUALIFIER+",");
			buffer.append(""+Constants.TEXT_QUALIFIER+df.format(100*totals.get("current")/totals.get("total"))+Constants.TEXT_QUALIFIER+",");
			buffer.append(""+Constants.TEXT_QUALIFIER+df.format(100.00)+Constants.TEXT_QUALIFIER+",");
			buffer.append(""+Constants.TEXT_QUALIFIER+df.format(totals.get("less30"))+Constants.TEXT_QUALIFIER+",");
			buffer.append(""+Constants.TEXT_QUALIFIER+df.format(100*totals.get("less30")/totals.get("total"))+Constants.TEXT_QUALIFIER+",");
			buffer.append(""+Constants.TEXT_QUALIFIER+df.format(100*totals.get("less30")/totals.get("total"))+Constants.TEXT_QUALIFIER+",");
			buffer.append(""+Constants.TEXT_QUALIFIER+df.format(100.00)+Constants.TEXT_QUALIFIER+",");
			buffer.append(""+Constants.TEXT_QUALIFIER+df.format(totals.get("less60"))+Constants.TEXT_QUALIFIER+",");
			buffer.append(""+Constants.TEXT_QUALIFIER+df.format(100*totals.get("less60")/totals.get("total"))+Constants.TEXT_QUALIFIER+",");
			buffer.append(""+Constants.TEXT_QUALIFIER+df.format(100*totals.get("less60")/totals.get("total"))+Constants.TEXT_QUALIFIER+",");
			buffer.append(""+Constants.TEXT_QUALIFIER+df.format(100.00)+Constants.TEXT_QUALIFIER+",");
			buffer.append(""+Constants.TEXT_QUALIFIER+df.format(totals.get("more60"))+Constants.TEXT_QUALIFIER+",");
			buffer.append(""+Constants.TEXT_QUALIFIER+df.format(100*totals.get("more60")/totals.get("total"))+Constants.TEXT_QUALIFIER+",");
			buffer.append(""+Constants.TEXT_QUALIFIER+df.format(100*totals.get("more60")/totals.get("total"))+Constants.TEXT_QUALIFIER+",");
			buffer.append(""+Constants.TEXT_QUALIFIER+df.format(100.00)+Constants.TEXT_QUALIFIER+",");
			buffer.append("\n");
		}else{
			buffer.append(""+Constants.TEXT_QUALIFIER+"No records found."+Constants.TEXT_QUALIFIER+",");
			buffer.append("\n");
		}
		return buffer;
	}
}