package com.cdgtaxi.ibs.report.ui;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;

import org.apache.commons.httpclient.HttpException;
import org.apache.log4j.Logger;
import org.zkoss.util.media.AMedia;
import org.zkoss.zul.Filedownload;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Messagebox;

import com.cdgtaxi.ibs.Uri;
import com.cdgtaxi.ibs.report.Constants;
import com.cdgtaxi.ibs.util.DateUtil;
import com.elixirtech.ers2.client.ERSClient;
import com.elixirtech.net.NetException;

public class CustomerUsageCardLevelWindow extends ReportWindow implements CSVFormat {
	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(CustomerUsageCardLevelWindow.class);
	public CustomerUsageCardLevelWindow() throws IOException {
		super("Customer Usage @ Card Level", "Trips");
	}
	@Override
	public void generate() throws HttpException, IOException, InterruptedException, NetException {
		Properties reportParamsProperties = new Properties();
		if(super.generate(reportParamsProperties)==null){
			return;
		}

		//Check Date must be less than 1 year @ customer usage @card level
		String invoiceStartMonth = (String)reportParamsProperties.get("2. Invoice Start Month");
		String invoiceEndMonth = (String)reportParamsProperties.get("3. Invoice End Month");
		Date invoiceStartDate = DateUtil.convertStrToDate(invoiceStartMonth, "yyyy-MM-dd");
		Date invoiceEndDate = DateUtil.convertStrToDate(invoiceEndMonth, "yyyy-MM-dd");
		
		Calendar startCalendar = new GregorianCalendar();
		startCalendar.setTime(invoiceStartDate);
		Calendar endCalendar = new GregorianCalendar();
		endCalendar.setTime(invoiceEndDate);
		int diffYear = endCalendar.get(Calendar.YEAR) - startCalendar.get(Calendar.YEAR);
		int diffMonth = diffYear * 12 + endCalendar.get(Calendar.MONTH) - startCalendar.get(Calendar.MONTH);
		if(diffMonth>11)
		{
			Messagebox.show("Date Range must be less than 1 year", "Report", Messagebox.OK, Messagebox.ERROR);
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
		
		filterDoubleQuote(reportParamsProperties);
		
		String accountNo = (String)reportParamsProperties.get("1a. Account No");
		String accountName = (String)reportParamsProperties.get("1b. Account Name");
		String invoiceStartMonth = (String)reportParamsProperties.get("2. Invoice Start Month");
		String invoiceEndMonth = (String)reportParamsProperties.get("3. Invoice End Month");
		String productType = (String)reportParamsProperties.get("4. Product Type (Only list product types with bin range starting with 60108965)");
		String entityNo = (String)reportParamsProperties.get("5. Entity");
		String expiryDT = (String)reportParamsProperties.get("6. Expiry Date");
		String cardNo = (String)reportParamsProperties.get("7. Card No");
		StringBuffer buffer = new StringBuffer();
		//Report Header
		buffer.append(Constants.TEXT_QUALIFIER+"Integrated Billing System"+Constants.TEXT_QUALIFIER+",");
		buffer.append("\n");
		buffer.append(Constants.TEXT_QUALIFIER+"Monthly Usage Report By Products"+Constants.TEXT_QUALIFIER+",");
		buffer.append("\n");
		buffer.append("\n");
		buffer.append(Constants.TEXT_QUALIFIER+"Selection Criteria:"+Constants.TEXT_QUALIFIER+",");
		buffer.append("\n");
		
		if((invoiceStartMonth!=null && invoiceStartMonth.length()>0) || (invoiceEndMonth!=null && invoiceEndMonth.length()!=0)){
			buffer.append(Constants.TEXT_QUALIFIER+"Invoice Month: ");
			if(invoiceStartMonth!=null && invoiceStartMonth.length()>0){
				buffer.append(this.convertYYYYMMDDtoMMMYYYY(invoiceStartMonth));
			}else{
				buffer.append(this.convertYYYYMMDDtoMMMYYYY(invoiceEndMonth));
			}
			buffer.append(" to ");
			if(invoiceEndMonth!=null && invoiceEndMonth.length()!=0){
				buffer.append(this.convertYYYYMMDDtoMMMYYYY(invoiceEndMonth)+Constants.TEXT_QUALIFIER+",");
			}else{
				buffer.append(this.convertYYYYMMDDtoMMMYYYY(invoiceStartMonth)+Constants.TEXT_QUALIFIER+",");
			}
			buffer.append("\n");
		}
		
		
		if(accountNo !=null && accountNo.length() > 0){
			buffer.append(Constants.TEXT_QUALIFIER+"Account No: "+accountNo+Constants.TEXT_QUALIFIER+",");
			buffer.append("\n");
		}		
		
		if(accountName !=null && accountName.length() > 0){
			buffer.append(Constants.TEXT_QUALIFIER+"Account Name: "+accountName+Constants.TEXT_QUALIFIER+",");
			buffer.append("\n");
		}	
		
		if(productType  !=null && productType.length() > 0){
			String productTypeStr = ((Listbox)this.getFellow("4. Product Type (Only list product types with bin range starting with 60108965)")).getSelectedItem().getLabel();
			buffer.append(Constants.TEXT_QUALIFIER+"Product Type : "+productTypeStr +Constants.TEXT_QUALIFIER);
			buffer.append("\n");
		}
		
		if(entityNo!=null && entityNo.length()>0) {
			String entityName = ((Listbox)this.getFellow("5. Entity")).getSelectedItem().getLabel();
			buffer.append(Constants.TEXT_QUALIFIER+"Entity: "+entityName+Constants.TEXT_QUALIFIER+",");
			buffer.append("\n");
		}
		//Added new criteria 
		if(expiryDT !=null && expiryDT.length() > 0){
			buffer.append(Constants.TEXT_QUALIFIER+"Expiry Date: "+expiryDT+Constants.TEXT_QUALIFIER+",");
			buffer.append("\n");
		}
		if(cardNo !=null && cardNo.length() > 0){
			buffer.append(Constants.TEXT_QUALIFIER+"Card No: "+cardNo+Constants.TEXT_QUALIFIER+",");
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
		buffer.append(Constants.TEXT_QUALIFIER+"Card Nos"+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+"Card Holder Name"+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+"Division Name"+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+"Division Code"+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+"Department Name"+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+"Department Code"+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+"Invoice Month"+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+"Trips"+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+"Amt($)"+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+"Admin(%)"+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+"Admin($)"+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+"GST($)"+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+"Total($)"+Constants.TEXT_QUALIFIER);
		buffer.append("\n");
		
		//Data
		List<Object[]> results = this.businessHelper.getReportBusiness().getCustomerUsageCardLevel(
				accountNo,
				accountName,
				invoiceStartMonth,
				invoiceEndMonth,
				productType,
				entityNo,
				expiryDT,
				cardNo
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