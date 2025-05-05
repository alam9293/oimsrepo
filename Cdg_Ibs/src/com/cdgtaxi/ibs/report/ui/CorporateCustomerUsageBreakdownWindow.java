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
import com.elixirtech.ers2.client.ERSClient;
import com.elixirtech.net.NetException;

public class CorporateCustomerUsageBreakdownWindow extends ReportWindow implements CSVFormat{
	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(CreditBalanceWindow.class);
	public CorporateCustomerUsageBreakdownWindow() throws IOException {
		super("Corporate Customer Usage Breakdown", "Account");
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
		String invoiceMonth = (String)reportParamsProperties.get("1. Invoice Month");
		String productType = (String)reportParamsProperties.get("2. Product Type");
		String accountStatus = (String)reportParamsProperties.get("3. Account Status");
		String businessNature = (String)reportParamsProperties.get("4. Business Nature");
		String salesPerson = (String)reportParamsProperties.get("5. Sales Person");
		String numberOfRecords = (String)reportParamsProperties.get("6. Number of Records");
		String sortBy = (String)reportParamsProperties.get("7. Sort By");
		String entityNo = (String)reportParamsProperties.get("8. Entity");
		
		StringBuffer buffer = new StringBuffer();

		//Report Header
		buffer.append(Constants.TEXT_QUALIFIER+"Integrated Billing System"+Constants.TEXT_QUALIFIER+",");
		buffer.append("\n");
		buffer.append(Constants.TEXT_QUALIFIER+"Corporate Customer Usage Breakdown Report"+Constants.TEXT_QUALIFIER+",");
		buffer.append("\n");
		buffer.append(Constants.TEXT_QUALIFIER+"Selection Criteria:"+Constants.TEXT_QUALIFIER+",");
		buffer.append("\n");
		buffer.append("\n");
		if(invoiceMonth!=null && invoiceMonth.length()>0){
			buffer.append(Constants.TEXT_QUALIFIER+"Invoice Month: "+this.convertYYYYMMDDtoMMMYYYY(invoiceMonth)+Constants.TEXT_QUALIFIER+",");
			buffer.append("\n");
		}
		if(productType!=null && productType.length()>0){
			String productTypeName = ((Listbox)this.getFellow("2. Product Type")).getSelectedItem().getLabel();
			buffer.append(Constants.TEXT_QUALIFIER+"Product Type: "+productTypeName+Constants.TEXT_QUALIFIER+",");
			buffer.append("\n");
		}
		if(accountStatus!=null && accountStatus.length()>0){
			String accountStatusName = ((Listbox)this.getFellow("3. Account Status")).getSelectedItem().getLabel();
			buffer.append(Constants.TEXT_QUALIFIER+"Account Status: "+accountStatusName+Constants.TEXT_QUALIFIER+",");
			buffer.append("\n");
		}
		if(businessNature!=null && businessNature.length()>0){
			String businessNatureName = ((Listbox)this.getFellow("4. Business Nature")).getSelectedItem().getLabel();
			buffer.append(Constants.TEXT_QUALIFIER+"Business Nature: "+businessNatureName+Constants.TEXT_QUALIFIER+",");
			buffer.append("\n");
		}
		if(salesPerson!=null && salesPerson.length()>0){
			String salesPersonName = ((Listbox)this.getFellow("5. Sales Person")).getSelectedItem().getLabel();
			buffer.append(Constants.TEXT_QUALIFIER+"Sales Person: "+salesPersonName+Constants.TEXT_QUALIFIER+",");
			buffer.append("\n");
		}
		if(numberOfRecords!=null && numberOfRecords.length()>0){
			buffer.append(Constants.TEXT_QUALIFIER+"Number of Records: "+numberOfRecords+Constants.TEXT_QUALIFIER+",");
			buffer.append("\n");
		}
		
		if(sortBy!=null && sortBy.length()>0){
			buffer.append(Constants.TEXT_QUALIFIER+"Sort By: "+NonConfigurableConstants.CORP_CUST_USAGE_BREAKDOWN_ORDER.get(sortBy)+Constants.TEXT_QUALIFIER+",");
			buffer.append("\n");
		}

		if(entityNo!=null && entityNo.length()>0) {
			String entityName = ((Listbox)this.getFellow("8. Entity")).getSelectedItem().getLabel();
			buffer.append(Constants.TEXT_QUALIFIER+"Entity: "+entityName+Constants.TEXT_QUALIFIER+",");
			buffer.append("\n");
		}
		
		buffer.append("\n");
		buffer.append(Constants.TEXT_QUALIFIER+"Report Format: "+Constants.EXTENSION_CSV+Constants.TEXT_QUALIFIER+",");
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
		buffer.append(Constants.TEXT_QUALIFIER+"Current Month Invoice Amount (S$)"+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+"Last  Month Invoice Amount(S$)"+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+"Current  Month- 2 Invoice Amount(S$)"+Constants.TEXT_QUALIFIER+",");
		buffer.append("\n");
		
		//Data
		List<Object[]> results = this.businessHelper.getReportBusiness().getCorporateCustomerBreakdownUsage(
				invoiceMonth,productType,accountStatus,businessNature,salesPerson,numberOfRecords.replaceAll(",", ""),sortBy,entityNo
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
