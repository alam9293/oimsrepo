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
import com.cdgtaxi.ibs.report.exception.FormatNotSupportedException;
import com.cdgtaxi.ibs.util.DateUtil;
import com.cdgtaxi.ibs.util.StringUtil;
import com.elixirtech.ers2.client.ERSClient;
import com.elixirtech.net.NetException;

public class CashlessBankCollectionDetailed extends ReportWindow implements CSVFormat {
	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(CashlessBankCollectionDetailed.class);
	public CashlessBankCollectionDetailed() throws IOException {
		super("Cashless Bank Collection Detailed", "Non Billable");
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
		String batchNo = (String)reportParamsProperties.get("01. Batch No");
		String creditStart = (String)reportParamsProperties.get("02. Credit Start Date");
		String creditEnd = (String)reportParamsProperties.get("03. Credit End Date");
		String batchStart = (String)reportParamsProperties.get("04. Batch Start Date");
		String batchEnd = (String)reportParamsProperties.get("05. Batch End Date");
		String entityNo = (String)reportParamsProperties.get("06. Entity");
		String acquirerNo = (String)reportParamsProperties.get("07. Acquirer");
		String paymentTypeNo = (String)reportParamsProperties.get("08. Payment Type");
		String providerNo = (String)reportParamsProperties.get("09. Company Code");
		String taxiNo = (String)reportParamsProperties.get("10. Taxi No");
		String driverIc = (String)reportParamsProperties.get("11. Driver IC");
		String txnStatus = (String)reportParamsProperties.get("12. Txn Status");
		String sort = (String)reportParamsProperties.get("13. Sort By");
		StringBuffer buffer = new StringBuffer();
		//Report Header
		buffer.append(Constants.TEXT_QUALIFIER+"Integrated Billing System"+Constants.TEXT_QUALIFIER+",");
		buffer.append("\n");
		buffer.append(Constants.TEXT_QUALIFIER+"Cashless Bank Collection Detailed"+Constants.TEXT_QUALIFIER+",");
		buffer.append("\n");
		buffer.append(Constants.TEXT_QUALIFIER+"Selection Criteria:"+Constants.TEXT_QUALIFIER+",");
		buffer.append("\n");
		buffer.append("\n");
		if(batchNo!=null && batchNo.length()>0){
			buffer.append(Constants.TEXT_QUALIFIER+"Batch No: "+batchNo+Constants.TEXT_QUALIFIER+",");
			buffer.append("\n");
		}
		if((creditStart!=null && creditStart.length()>0) || (creditEnd!=null && creditEnd.length()!=0)){
			buffer.append(Constants.TEXT_QUALIFIER+"Credit Date: ");
			if(creditStart!=null && creditStart.length()>0){
				buffer.append(creditStart);
			}else{
				buffer.append(creditEnd);
			}
			buffer.append(" to ");
			if(creditEnd!=null && creditEnd.length()!=0){
				buffer.append(creditEnd+Constants.TEXT_QUALIFIER+",");
			}else{
				buffer.append(creditStart+Constants.TEXT_QUALIFIER+",");
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
			String entityName = ((Listbox)this.getFellow("06. Entity")).getSelectedItem().getLabel();
			buffer.append(Constants.TEXT_QUALIFIER+"Entity: "+entityName+Constants.TEXT_QUALIFIER+",");
			buffer.append("\n");
		}
		if(acquirerNo!=null && acquirerNo.length()>0){
			String acquirerName = ((Listbox)this.getFellow("07. Acquirer")).getSelectedItem().getLabel();
			buffer.append(Constants.TEXT_QUALIFIER+"Acquirer: "+acquirerName+Constants.TEXT_QUALIFIER+",");
			buffer.append("\n");
		}
		if(paymentTypeNo!=null && paymentTypeNo.length()>0){
			String paymentTypeName = ((Listbox)this.getFellow("08. Payment Type")).getSelectedItem().getLabel();
			buffer.append(Constants.TEXT_QUALIFIER+"Payment Type: "+paymentTypeName+Constants.TEXT_QUALIFIER+",");
			buffer.append("\n");
		}
		if(providerNo!=null && providerNo.length()>0){
			String providerName = ((Listbox)this.getFellow("09. Company Code")).getSelectedItem().getLabel();
			buffer.append(Constants.TEXT_QUALIFIER+"Company Code: "+providerName+Constants.TEXT_QUALIFIER+",");
			buffer.append("\n");
		}
		if(taxiNo!=null && taxiNo.length()>0){
			buffer.append(Constants.TEXT_QUALIFIER+"Taxi No: "+taxiNo+Constants.TEXT_QUALIFIER+",");
			buffer.append("\n");
		}
		if(driverIc!=null && driverIc.length()>0){
			buffer.append(Constants.TEXT_QUALIFIER+"Driver IC: "+StringUtil.maskNric(driverIc)+Constants.TEXT_QUALIFIER+",");
			buffer.append("\n");
		}
		if(txnStatus!=null && txnStatus.length()>0){
			String txnStatusName = ((Listbox)this.getFellow("12. Txn Status")).getSelectedItem().getLabel();
			buffer.append(Constants.TEXT_QUALIFIER+"Txn Status: "+txnStatusName+Constants.TEXT_QUALIFIER+",");
			buffer.append("\n");
		}
		String sortLabel = ((Listbox)this.getFellow("13. Sort By")).getSelectedItem().getLabel();
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
		buffer.append(Constants.TEXT_QUALIFIER+"Card Number"+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+"Payment Type"+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+"Batch Date"+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+"Batch No"+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+"System Creation Date for Batch"+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+"System Last Updated Date for Batch"+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+"System Creation Date for Bank Advice"+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+"System Last Updated Date for Bank Advice"+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+"Trip Start Date"+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+"Taxi No"+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+"Job Number"+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+"Insurance Premium Policy Number"+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+"Policy Status"+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+"Driver IC"+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+"Bank CR Date"+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+"Txn Amt ($)"+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+"Admin Amt ($)"+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+"Admin GST Amt ($)"+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+"Insurance Premium Amt ($)"+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+"Insurance Premium GST Amt ($)"+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+"Total Txn Amt ($)"+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+"Amount Received ($)"+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+"Rejected Amount ($)"+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+"O/S Amount"+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+"Status"+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+"Remarks"+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+"Offline"+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+"Business Partner Commission ($)"+Constants.TEXT_QUALIFIER+",");
		buffer.append("\n");
		//Data
		List<Object[]> results = this.businessHelper.getReportBusiness().getCashlessBankCollectionDetailed(
				batchNo,
				creditStart!=null && creditStart.length()!=0 ? creditStart + " 00:00:00" : creditEnd!=null && creditEnd.length()!=0 ? creditEnd + " 00:00:00" : creditStart,
				creditEnd!=null && creditEnd.length()!=0 ? creditEnd + " 23:59:59" : creditStart!=null && creditStart.length()!=0 ? creditStart + " 23:59:59" : creditEnd,
				batchStart!=null && batchStart.length()!=0 ? batchStart + " 00:00:00" : batchEnd!=null && batchEnd.length()!=0 ? batchEnd + " 00:00:00" : batchStart,
				batchEnd!=null && batchEnd.length()!=0 ? batchEnd + " 23:59:59" : batchStart!=null && batchStart.length()!=0 ? batchStart + " 23:59:59" : batchEnd,
				entityNo, acquirerNo, paymentTypeNo, providerNo, taxiNo, driverIc, txnStatus, sort
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
						}else if(j==14){
								buffer.append(""+Constants.TEXT_QUALIFIER+StringUtil.maskNric(data.toString())+Constants.TEXT_QUALIFIER+",");
						}else if(j==25){
							buffer.append(""+Constants.TEXT_QUALIFIER+NonConfigurableConstants.NON_BILLABLE_TXN_STATUS.get(data)+Constants.TEXT_QUALIFIER+",");
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