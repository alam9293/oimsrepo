package com.cdgtaxi.ibs.report.ui;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.apache.commons.httpclient.HttpException;
import org.apache.log4j.Logger;
import org.zkoss.util.media.AMedia;
import org.zkoss.zul.Filedownload;
import org.zkoss.zul.Listbox;

import com.cdgtaxi.ibs.report.Constants;
import com.cdgtaxi.ibs.report.exception.FormatNotSupportedException;
import com.cdgtaxi.ibs.util.DateUtil;
import com.elixirtech.ers2.client.ERSClient;
import com.elixirtech.net.NetException;

public class InvoiceReportWindow extends ReportWindow implements CSVFormat {
	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(InvoiceReportWindow.class);
	public InvoiceReportWindow() throws IOException {
		super("Invoice Report", "Trips");
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
			throw new FormatNotSupportedException();
		}
	}
	public StringBuffer generateCSVData(Properties reportParamsProperties) {
		String entity = (String)reportParamsProperties.get("1. Entity");
		String invoiceStartDate = (String)reportParamsProperties.get("2. Invoice Start Date");
		String invoiceEndDate = (String)reportParamsProperties.get("3. Invoice End Date");
		String invoiceType = (String)reportParamsProperties.get("4. Invoice Type");
		String eInvoice = (String)reportParamsProperties.get("5. Opt for eInvoice");
		String salesPerson = (String)reportParamsProperties.get("6. Sales Person");
		String sortBy = (String)reportParamsProperties.get("7. Sort By");
		StringBuffer buffer = new StringBuffer();
		//Report Header
		buffer.append(Constants.TEXT_QUALIFIER+"Integrated Billing System"+Constants.TEXT_QUALIFIER+",");
		buffer.append("\n");
		buffer.append(Constants.TEXT_QUALIFIER+"Invoice Report"+Constants.TEXT_QUALIFIER+",");
		buffer.append("\n");
		buffer.append("\n");
		buffer.append(Constants.TEXT_QUALIFIER+"Selection Criteria:"+Constants.TEXT_QUALIFIER+",");
		buffer.append("\n");
		if(entity !=null && entity.length() > 0){
			String entityStr = ((Listbox)this.getFellow("1. Entity")).getSelectedItem().getLabel();
			buffer.append(Constants.TEXT_QUALIFIER+"Entity: "+entityStr+Constants.TEXT_QUALIFIER+",");
			buffer.append("\n");
		}
		if((invoiceStartDate!=null && invoiceStartDate.length()>0) || (invoiceEndDate!=null && invoiceEndDate.length()!=0)){
			buffer.append(Constants.TEXT_QUALIFIER+"Invoice Date: ");
			if(invoiceStartDate!=null && invoiceStartDate.length()>0){
				buffer.append(invoiceStartDate);
			}else{
				buffer.append(invoiceEndDate);
			}
			buffer.append(" to ");
			if(invoiceEndDate!=null && invoiceEndDate.length()!=0){
				buffer.append(invoiceEndDate+Constants.TEXT_QUALIFIER+",");
			}else{
				buffer.append(invoiceStartDate+Constants.TEXT_QUALIFIER+",");
			}
			buffer.append("\n");
		}
		if(invoiceType!=null && invoiceType.length() > 0){
			String invoiceTypeStr = ((Listbox)this.getFellow("4. Invoice Type")).getSelectedItem().getLabel();
			buffer.append(Constants.TEXT_QUALIFIER+"Invoice Type: "+invoiceTypeStr+Constants.TEXT_QUALIFIER+",");
			buffer.append("\n");
		}
		if(eInvoice!=null && eInvoice.length() > 0){
			String eInvoiceStr = ((Listbox)this.getFellow("5. Opt for eInvoice")).getSelectedItem().getLabel();
			buffer.append(Constants.TEXT_QUALIFIER+"Opt for eInvoice: "+eInvoiceStr+Constants.TEXT_QUALIFIER+",");
			buffer.append("\n");
		}
		if(salesPerson!=null && salesPerson.length() > 0){
			String salesPersonStr = ((Listbox)this.getFellow("6. Sales Person")).getSelectedItem().getLabel();
			buffer.append(Constants.TEXT_QUALIFIER+"Sales Person: "+salesPersonStr+Constants.TEXT_QUALIFIER+",");
			buffer.append("\n");
		}
		if(sortBy!=null && sortBy.length() > 0){
			String sortByStr = ((Listbox)this.getFellow("7. Sort By")).getSelectedItem().getLabel();
			buffer.append(Constants.TEXT_QUALIFIER+"Sort By: "+sortByStr+Constants.TEXT_QUALIFIER);
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
		buffer.append(Constants.TEXT_QUALIFIER+"Account Category"+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+"Opt-In eInvoice"+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+"Invoice No"+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+"Invoice Date"+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+"Invoice Type"+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+"Prev Balance"+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+"Payment"+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+"New Txn"+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+"New Balance"+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+"0-30 Days"+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+"31-60 Days"+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+"61-90 Days"+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+"> 90 Days"+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+"Charge To"+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+"Sales Person"+Constants.TEXT_QUALIFIER + ",");
		buffer.append(Constants.TEXT_QUALIFIER+"Invoice Printing"+Constants.TEXT_QUALIFIER+ ",");
		buffer.append(Constants.TEXT_QUALIFIER+"Outsource Printing"+Constants.TEXT_QUALIFIER);
		buffer.append("\n");
		//Data
		List<Object[]> results = new ArrayList<Object[]>();
		List<Object[]> results2 = this.businessHelper.getReportBusiness().getInvoiceReport(
				entity,
				invoiceStartDate!=null && invoiceStartDate.length()!=0 ? invoiceStartDate + " 00:00:00" : invoiceEndDate!=null && invoiceEndDate.length()!=0 ? invoiceEndDate + " 00:00:00" : invoiceStartDate,
				invoiceEndDate!=null && invoiceEndDate.length()!=0 ? invoiceEndDate + " 23:59:59" : invoiceStartDate!=null && invoiceStartDate.length()!=0 ? invoiceStartDate + " 23:59:59" : invoiceEndDate,
				invoiceType,
				eInvoice,
				salesPerson,
				sortBy
		);
		//check prepaid
		if(results2.size() != 0)
		{
			for(int i=0;i<results2.size(); i++){
				Object[] result2 = results2.get(i);
				
				String prepaidCheck1 = result2[0].toString();
				String prepaidCheck2 = result2[0].toString();
				String prepaidCheck3 = result2[0].toString();
				Integer total = Integer.parseInt(prepaidCheck1) + Integer.parseInt(prepaidCheck2) + Integer.parseInt(prepaidCheck3);
//				System.out.println("test > "+total);
				
				if(total == 0)
					results.add(result2);
			}
//			System.out.println("results2 > "+results2.size());
//			System.out.println("result1 > "+results.size());
			
			if(results.size()!=0){
				for(int i=0;i<results.size(); i++){
					buffer.append(""+Constants.TEXT_QUALIFIER+(i+1)+Constants.TEXT_QUALIFIER+",");
					Object[] result = results.get(i);
										
					for(int j=0;j<result.length;j++){//for(Object data : result){
						
						if(j==0 || j==1 || j==2)
							continue;
						
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
		}
		else{
			buffer.append(""+Constants.TEXT_QUALIFIER+"No records found."+Constants.TEXT_QUALIFIER+",");
			buffer.append("\n");
		}
		return buffer;
		
	}
}