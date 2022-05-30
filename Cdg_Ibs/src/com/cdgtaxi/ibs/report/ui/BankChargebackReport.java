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
import com.cdgtaxi.ibs.report.Constants;
import com.cdgtaxi.ibs.report.exception.FormatNotSupportedException;
import com.cdgtaxi.ibs.util.DateUtil;
import com.elixirtech.ers2.client.ERSClient;
import com.elixirtech.net.NetException;

public class BankChargebackReport extends ReportWindow implements CSVFormat {
	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(BankChargebackReport.class);
	public BankChargebackReport() throws IOException {
		super("Bank Chargeback Report", "Non Billable");
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
		String chargebackStart = (String)reportParamsProperties.get("1. Chargeback Start Date");
		String chargebackEnd = (String)reportParamsProperties.get("2. Chargeback End Date");
		String batchStart = (String)reportParamsProperties.get("3. Batch Start Date");
		String batchEnd = (String)reportParamsProperties.get("4. Batch End Date");
		String entityNo = (String)reportParamsProperties.get("5. Entity");
		String providerNo = (String)reportParamsProperties.get("6. Company Code");
		String acquirerNo = (String)reportParamsProperties.get("7. Acquirer");
		String sort = (String)reportParamsProperties.get("8. Sort By");
		StringBuffer buffer = new StringBuffer();
		//Report Header
		buffer.append(Constants.TEXT_QUALIFIER+"Integrated Billing System"+Constants.TEXT_QUALIFIER+",");
		buffer.append("\n");
		buffer.append(Constants.TEXT_QUALIFIER+"Bank Chargeback Report"+Constants.TEXT_QUALIFIER+",");
		buffer.append("\n");
		buffer.append(Constants.TEXT_QUALIFIER+"Selection Criteria:"+Constants.TEXT_QUALIFIER+",");
		buffer.append("\n");
		buffer.append("\n");
		if((chargebackStart!=null && chargebackStart.length()>0) || (chargebackEnd!=null && chargebackEnd.length()!=0)){
			buffer.append(Constants.TEXT_QUALIFIER+"Chargeback Date: ");
			if(chargebackStart!=null && chargebackStart.length()>0){
				buffer.append(chargebackStart);
			}else{
				buffer.append(chargebackEnd);
			}
			buffer.append(" to ");
			if(chargebackEnd!=null && chargebackEnd.length()!=0){
				buffer.append(chargebackEnd+Constants.TEXT_QUALIFIER+",");
			}else{
				buffer.append(chargebackStart+Constants.TEXT_QUALIFIER+",");
			}
			buffer.append("\n");
		}
		if((batchStart!=null && batchStart.length()>0) || (batchEnd!=null && batchEnd.length()!=0)){
			buffer.append(Constants.TEXT_QUALIFIER+"Batch Date: ");
			if(batchStart!=null && batchStart.length()>0){
				buffer.append(batchStart);
			}else{
				buffer.append(batchEnd);
			}
			buffer.append(" to ");
			if(batchEnd!=null && batchEnd.length()!=0){
				buffer.append(batchEnd+Constants.TEXT_QUALIFIER+",");
			}else{
				buffer.append(batchStart+Constants.TEXT_QUALIFIER+",");
			}
			buffer.append("\n");
		}
		if(entityNo!=null && entityNo.length()>0){
			String entityName = ((Listbox)this.getFellow("5. Entity")).getSelectedItem().getLabel();
			buffer.append(Constants.TEXT_QUALIFIER+"Entity: "+entityName+Constants.TEXT_QUALIFIER+",");
			buffer.append("\n");
		}else{
			buffer.append(Constants.TEXT_QUALIFIER+"Entity: ALL"+Constants.TEXT_QUALIFIER+",");
			buffer.append("\n");
		}
		if(providerNo!=null && providerNo.length()>0){
			String providerName = ((Listbox)this.getFellow("6. Company Code")).getSelectedItem().getLabel();
			buffer.append(Constants.TEXT_QUALIFIER+"Company Code: "+providerName+Constants.TEXT_QUALIFIER+",");
			buffer.append("\n");
		}else{
			buffer.append(Constants.TEXT_QUALIFIER+"Company Code: ALL"+Constants.TEXT_QUALIFIER+",");
			buffer.append("\n");
		}
		if(acquirerNo!=null && acquirerNo.length()>0){
			String acquirerName = ((Listbox)this.getFellow("7. Acquirer")).getSelectedItem().getLabel();
			buffer.append(Constants.TEXT_QUALIFIER+"Acquirer: "+acquirerName+Constants.TEXT_QUALIFIER+",");
			buffer.append("\n");
		}
		String sortLabel = ((Listbox)this.getFellow("8. Sort By")).getSelectedItem().getLabel();
		buffer.append(Constants.TEXT_QUALIFIER+"Sort By: "+sortLabel+Constants.TEXT_QUALIFIER+",");
		buffer.append("\n");
		buffer.append("\n");
		buffer.append(Constants.TEXT_QUALIFIER+"Printed By: "+reportParamsProperties.get("printedBy")+Constants.TEXT_QUALIFIER+",");
		buffer.append("\n");
		buffer.append(Constants.TEXT_QUALIFIER+"Report Date: "+DateUtil.convertDateToStr(DateUtil.getCurrentDate(), DateUtil.GLOBAL_DATE_FORMAT)+Constants.TEXT_QUALIFIER+",");
		buffer.append("\n");
		buffer.append("\n");
		//Column Title
		buffer.append(Constants.TEXT_QUALIFIER+"S/N"+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+"Acquirer"+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+"Card No"+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+"Batch No"+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+"Batch Date"+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+"System Last Updated Date for Chargeback"+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+"Trip Start Date"+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+"Taxi No"+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+"Job Number"+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+"Insurance Premium Policy Number"+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+"Policy Status"+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+"Chargeback Date"+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+"Txn Amt($)"+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+"Admin Amt($)"+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+"Admin GST Amt($)"+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+"Insurance Premium Amt ($)"+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+"Insurance Premium GST Amt ($)"+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+"Total Txn Amt($)"+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+"MDR Amt($)"+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+"Net Amt($)"+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+"Chargeback Type"+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+"Reason"+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+"Offline"+Constants.TEXT_QUALIFIER+",");
		buffer.append("\n");
		//Data
		List<Object[]> results = this.businessHelper.getReportBusiness().getBankChargebackReport(
				chargebackStart!=null && chargebackStart.length()!=0 ? chargebackStart + " 00:00:00" : chargebackEnd!=null && chargebackEnd.length()!=0 ? chargebackEnd + " 00:00:00" : chargebackStart,
				chargebackEnd!=null && chargebackEnd.length()!=0 ? chargebackEnd + " 23:59:59" : chargebackStart!=null && chargebackStart.length()!=0 ? chargebackStart + " 23:59:59" : chargebackEnd,
				batchStart!=null && batchStart.length()!=0 ? batchStart + " 00:00:00" : batchEnd!=null && batchEnd.length()!=0 ? batchEnd + " 00:00:00" : batchStart,
				batchEnd!=null && batchEnd.length()!=0 ? batchEnd + " 23:59:59" : batchStart!=null && batchStart.length()!=0 ? batchStart + " 23:59:59" : batchEnd,
				entityNo, providerNo, acquirerNo, sort
		);
		logger.info("size = " + results.size());
		if(results.size()!=0){
			for(int i=0;i<results.size(); i++){
				buffer.append(""+Constants.TEXT_QUALIFIER+(i+1)+Constants.TEXT_QUALIFIER+",");
				Object[] result = results.get(i);
				for(int j=0;j<result.length;j++){//for(Object data : result){
					Object data = result[j];
					if(data!=null){
						if(j==1){
							buffer.append(""+Constants.TEXT_QUALIFIER+'\''+data+Constants.TEXT_QUALIFIER+",");
						}else{
							buffer.append(""+Constants.TEXT_QUALIFIER+data+Constants.TEXT_QUALIFIER+",");
						}
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