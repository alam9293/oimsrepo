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

public class CustomerReportWindow extends ReportWindow implements CSVFormat{
	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(CustomerReportWindow.class);
	public CustomerReportWindow() throws IOException {
		super("Customer Report", "Account");
	}
	public StringBuffer generateCSVData(Properties reportParamsProperties) {
		String acctTypeNo = (String)reportParamsProperties.get("01. Account Type");
		String acctNo = (String)reportParamsProperties.get("02a. Account No");
		String acctName = (String)reportParamsProperties.get("02b. Account Name");
		String acctStatus = (String)reportParamsProperties.get("03. Account Status");
		String productTypeId = (String)reportParamsProperties.get("04. Subscribed Product Type");
		String industryNo = (String)reportParamsProperties.get("05. Business Nature");
		String joinStart = (String)reportParamsProperties.get("06. Join Start Date");
		String joinEnd = (String)reportParamsProperties.get("07. Join End Date");
		String terminateStart = (String)reportParamsProperties.get("08. Terminated Start Date");
		String terminateEnd = (String)reportParamsProperties.get("09. Terminated End Date");
		String salespersonNo = (String)reportParamsProperties.get("10. Sales Person");
		String sortBy = (String)reportParamsProperties.get("11. Sort By");
		String entityNo = (String)reportParamsProperties.get("12. Entity");
		StringBuffer buffer = new StringBuffer();
		//Report Header
		buffer.append(Constants.TEXT_QUALIFIER+"Integrated Billing System"+Constants.TEXT_QUALIFIER+",");
		buffer.append("\n");
		buffer.append(Constants.TEXT_QUALIFIER+"Customer Report"+Constants.TEXT_QUALIFIER+",");
		buffer.append("\n");
		buffer.append(Constants.TEXT_QUALIFIER+"Selection Criteria:"+Constants.TEXT_QUALIFIER+",");
		buffer.append("\n");
		buffer.append("\n");
		if(acctTypeNo!=null && acctTypeNo.length()>0){
			String acctType = ((Listbox)this.getFellow("01. Account Type")).getSelectedItem().getLabel();
			buffer.append(Constants.TEXT_QUALIFIER+"Account Type: "+acctType+Constants.TEXT_QUALIFIER+",");
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
		if(acctStatus!=null && acctStatus.length()>0){
			String acctStat = ((Listbox)this.getFellow("03. Account Status")).getSelectedItem().getLabel();
			buffer.append(Constants.TEXT_QUALIFIER+"Account Status: "+acctStat+Constants.TEXT_QUALIFIER+",");
			buffer.append("\n");
		}
		if(productTypeId!=null && productTypeId.length()>0){
			String productType = ((Listbox)this.getFellow("04. Subscribed Product Type")).getSelectedItem().getLabel();
			buffer.append(Constants.TEXT_QUALIFIER+"Subscribed Product Type: "+productType+Constants.TEXT_QUALIFIER+",");
			buffer.append("\n");
		}
		if(industryNo!=null && industryNo.length()>0){
			String industry = ((Listbox)this.getFellow("05. Business Nature")).getSelectedItem().getLabel();
			buffer.append(Constants.TEXT_QUALIFIER+"Business Nature: "+industry+Constants.TEXT_QUALIFIER+",");
			buffer.append("\n");
		}
		if((joinStart!=null && joinStart.length()>0) || (joinEnd!=null && joinEnd.length()!=0)){
			buffer.append(Constants.TEXT_QUALIFIER+"Join Date: ");
			if(joinStart!=null && joinStart.length()>0){
				buffer.append(joinStart);
			}else{
				buffer.append(joinEnd);
			}
			buffer.append(" to ");
			if(joinEnd!=null && joinEnd.length()!=0){
				buffer.append(joinEnd+Constants.TEXT_QUALIFIER+",");
			}else{
				buffer.append(joinStart+Constants.TEXT_QUALIFIER+",");
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
		if(salespersonNo!=null && salespersonNo.length()>0){
			String salesperson = ((Listbox)this.getFellow("10. Sales Person")).getSelectedItem().getLabel();
			buffer.append(Constants.TEXT_QUALIFIER+"Sales Person: "+salesperson+Constants.TEXT_QUALIFIER+",");
			buffer.append("\n");
		}
		if(sortBy!=null && sortBy.length()>0){
			buffer.append(Constants.TEXT_QUALIFIER+"Sort By: "+NonConfigurableConstants.CUSTOMER_ORDER.get(sortBy)+Constants.TEXT_QUALIFIER+",");
			buffer.append("\n");
		}
		
		if(entityNo!=null && entityNo.length()>0) {
			String entityName = ((Listbox)this.getFellow("12. Entity")).getSelectedItem().getLabel();
			buffer.append(Constants.TEXT_QUALIFIER+"Entity: "+entityName+Constants.TEXT_QUALIFIER+",");
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
		buffer.append(Constants.TEXT_QUALIFIER+"Account No"+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+"Account Name"+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+"Prdts Subscription"+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+"Ace Indicator"+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+"Business Nature"+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+"Join Date"+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+"Terminate Date"+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+"Account Status"+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+"Last Trip Date"+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+"Last Prdt Used"+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+"Credit Limit ($)"+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+"Credit Balance ($)"+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+"Ship Contact Person"+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+"Ship Contact No (Tel, Mobile)"+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+"Ship Fax"+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+"Ship Email"+Constants.TEXT_QUALIFIER+",");
		
//		buffer.append(Constants.TEXT_QUALIFIER+"Ship Address"+Constants.TEXT_QUALIFIER+",");
		/* area, block, street, unit, building name, country, state, city and postal code */
		buffer.append(Constants.TEXT_QUALIFIER+"Area"+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+"Block"+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+"Street"+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+"Unit"+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+"Building Name"+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+"Country"+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+"State"+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+"City"+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+"Postal Code"+Constants.TEXT_QUALIFIER+",");
		
		buffer.append(Constants.TEXT_QUALIFIER+"Billing Cycle Setup"+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+"Last Invoice Amount"+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+"Sales Person"+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+"Prdt Type Disc/Rewards Plan"+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+"Credit Term"+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+"Early Payment Discount"+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+"Late Interest Charge"+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+"Volume Discount Plan"+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+"Promotion Plan"+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+"E-Invoice"+Constants.TEXT_QUALIFIER+",");
		
		buffer.append(Constants.TEXT_QUALIFIER+"Default Payment Mode"+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+"Bank"+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+"Branch"+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+"Bank Account No"+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+"Base Admin Fee"+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+"Government eInvoice"+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+"PUBBS Billing"+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+"Outsource Printing"+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+"FI Govt"+Constants.TEXT_QUALIFIER+",");
		buffer.append("\n");
		
		//Data
		List<Object[]> results = this.businessHelper.getReportBusiness().getCustomerReport(
				"%" + acctTypeNo + "%",
				"%" + acctNo + "%",
				"%" + acctName + "%",
				acctStatus,
				productTypeId,
				industryNo,
				joinStart!=null && joinStart.length()!=0 ? joinStart + " 00:00:00" : joinEnd!=null && joinEnd.length()!=0 ? joinEnd + " 00:00:00" : joinStart,
				joinEnd!=null && joinEnd.length()!=0 ? joinEnd + " 23:59:59" : joinStart!=null && joinStart.length()!=0 ? joinStart + " 23:59:59" : joinEnd,
				terminateStart!=null && terminateStart.length()!=0 ? terminateStart + " 00:00:00" : terminateEnd!=null && terminateEnd.length()!=0 ? terminateEnd + " 00:00:00" : terminateStart,
				terminateEnd!=null && terminateEnd.length()!=0 ? terminateEnd + " 23:59:59" : terminateStart!=null && terminateStart.length()!=0 ? terminateStart + " 23:59:59" : terminateEnd,
				salespersonNo,
				sortBy,
				entityNo
		);
		logger.info("size = " + results.size());
		if(results.size()!=0){
			for(int i=0;i<results.size(); i++){
				buffer.append(""+Constants.TEXT_QUALIFIER+(i+1)+Constants.TEXT_QUALIFIER+",");
				Object[] result = results.get(i);
				for(int j=0;j<result.length;j++){//for(Object data : result){
					Object data = result[j];
					if(data!=null){
						if(j==7){//status
							buffer.append(""+Constants.TEXT_QUALIFIER+NonConfigurableConstants.ACCOUNT_STATUS.get(data)+Constants.TEXT_QUALIFIER+",");
						}else if(j==25){// billing cycle
							buffer.append(""+Constants.TEXT_QUALIFIER+NonConfigurableConstants.BILLING_CYCLES.get(data)+Constants.TEXT_QUALIFIER+",");
						}else if(j==34){// eInvoice
							buffer.append(""+Constants.TEXT_QUALIFIER+NonConfigurableConstants.BOOLEAN.get(data)+Constants.TEXT_QUALIFIER+",");
						}else if(j==17 || j==24 || j==38){
							
							if(!data.toString().trim().equals("") && data != null)
							{
								String data2 = data.toString().trim();
								if(data2.contains(","))
									buffer.append("'"+data2.replaceAll(",", " ")+",");
								else
									buffer.append("'"+data+",");
							}
							else
								buffer.append("'"+data+",");
						}else{
							buffer.append(""+Constants.TEXT_QUALIFIER+data+Constants.TEXT_QUALIFIER+",");
						}
					}else{
						if(j==34){// eInvoice
							buffer.append(""+Constants.TEXT_QUALIFIER+"NO"+Constants.TEXT_QUALIFIER+",");
						}else{
							buffer.append(Constants.TEXT_QUALIFIER+Constants.TEXT_QUALIFIER+",");
						}
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
}