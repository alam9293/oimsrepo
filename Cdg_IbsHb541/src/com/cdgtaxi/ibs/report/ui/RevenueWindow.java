package com.cdgtaxi.ibs.report.ui;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;

import org.apache.commons.httpclient.HttpException;
import org.apache.log4j.Logger;
import org.zkoss.util.media.AMedia;
import org.zkoss.zul.Filedownload;
import org.zkoss.zul.Listbox;

import com.cdgtaxi.ibs.Uri;
import com.cdgtaxi.ibs.common.NonConfigurableConstants;
import com.cdgtaxi.ibs.common.model.PmtbProductType;
import com.cdgtaxi.ibs.report.Constants;
import com.cdgtaxi.ibs.report.exception.FormatNotSupportedException;
import com.cdgtaxi.ibs.util.DateUtil;
import com.elixirtech.ers2.client.ERSClient;
import com.elixirtech.net.NetException;
import com.google.common.collect.ComparisonChain;

public class RevenueWindow extends ReportWindow implements CSVFormat {
	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(RevenueWindow.class);
	public RevenueWindow() throws IOException {
		super("Revenue", "Revenue");
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

	public StringBuffer generateCSVData(Properties reportParamsProperties) throws FormatNotSupportedException{
		String entityNo = (String)reportParamsProperties.get("1. Entity");
		String invoiceStart = (String)reportParamsProperties.get("2. Invoice Start Date");
		String invoiceEnd = (String)reportParamsProperties.get("3. Invoice End Date");
		StringBuffer buffer = new StringBuffer();
		//Report Header
		buffer.append(Constants.TEXT_QUALIFIER+"Integrated Billing System"+Constants.TEXT_QUALIFIER+",");
		buffer.append("\n");
		buffer.append(Constants.TEXT_QUALIFIER+"Revenue"+Constants.TEXT_QUALIFIER+",");
		buffer.append("\n");
		buffer.append("\n");
		buffer.append(Constants.TEXT_QUALIFIER+"Selection Criteria:"+Constants.TEXT_QUALIFIER+",");
		buffer.append("\n");
		// criteria
		if(entityNo!=null && entityNo.length()>0){
			String entityName = ((Listbox)this.getFellow("1. Entity")).getSelectedItem().getLabel();
			buffer.append(Constants.TEXT_QUALIFIER+"Entity: "+entityName+Constants.TEXT_QUALIFIER+",");
			buffer.append("\n");
		}
		if((invoiceStart!=null && invoiceStart.length()>0) || (invoiceEnd!=null && invoiceEnd.length()!=0)){
			buffer.append(Constants.TEXT_QUALIFIER+"Invoice Date: ");
			if(invoiceStart!=null && invoiceStart.length()>0){
				buffer.append(invoiceStart);
			}else{
				buffer.append(invoiceEnd);
			}
			buffer.append(" to ");
			if(invoiceEnd!=null && invoiceEnd.length()!=0){
				buffer.append(invoiceEnd+Constants.TEXT_QUALIFIER+",");
			}else{
				buffer.append(invoiceStart+Constants.TEXT_QUALIFIER+",");
			}
			buffer.append("\n");
		}
		buffer.append("\n");
		buffer.append(Constants.TEXT_QUALIFIER+"Printed By: "+reportParamsProperties.get("printedBy")+Constants.TEXT_QUALIFIER+",");
		buffer.append("\n");
		buffer.append(Constants.TEXT_QUALIFIER+"Report Date: "+DateUtil.convertDateToStr(DateUtil.getCurrentDate(), DateUtil.GLOBAL_DATE_FORMAT)+Constants.TEXT_QUALIFIER+",");
		buffer.append("\n");
		buffer.append("\n");
		
		//Retrieve dynamic headers
		Object[] headers = this.businessHelper.getReportBusiness().getRevenueHeader().get(0);
		
		List<String> productTypeIds = this.businessHelper.getReportBusiness().getRevenueHeaderForProductTypeId(
				entityNo,
				invoiceStart!=null && invoiceStart.length()!=0 ? invoiceStart + " 00:00:00" : invoiceEnd!=null && invoiceEnd.length()!=0 ? invoiceEnd + " 00:00:00" : invoiceStart,
				invoiceEnd!=null && invoiceEnd.length()!=0 ? invoiceEnd + " 23:59:59" : invoiceStart!=null && invoiceStart.length()!=0 ? invoiceStart + " 23:59:59" : invoiceEnd
		);
		
		
		List<PmtbProductType> productTypes = this.businessHelper.getProductTypeBusiness().getProductTypes(productTypeIds);
	
		Collections.sort(productTypes, new Comparator<PmtbProductType>(){

			public int compare(PmtbProductType o1, PmtbProductType o2) {
				return ComparisonChain.start()
						.compare(o1.getPrepaid(), o2.getPrepaid())
						.compare(o1.getProductTypeId(), o2.getProductTypeId())
						.result();
				}
			}
		);
		
		//Column Title
		int beforeDynamicColumns = 5;
		
		buffer.append(Constants.TEXT_QUALIFIER+"ACCT NO"+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+"ACCT NAME"+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+"INV DATE"+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+"INV NO"+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+"INV AMT"+Constants.TEXT_QUALIFIER+",");
		
		for(PmtbProductType productType: productTypes){
			String productTypeId = productType.getProductTypeId();
			
			String prepaid = productType.getPrepaid();
			if(NonConfigurableConstants.getBoolean(prepaid)){
				
				buffer.append(Constants.TEXT_QUALIFIER+productTypeId+" DEFERRED INCOME"+Constants.TEXT_QUALIFIER+",");
				buffer.append(Constants.TEXT_QUALIFIER+productTypeId+" CASHPLUS"+Constants.TEXT_QUALIFIER+",");
				buffer.append(Constants.TEXT_QUALIFIER+productTypeId+" CARD ISSUANCE FEE"+Constants.TEXT_QUALIFIER+",");
				buffer.append(Constants.TEXT_QUALIFIER+productTypeId+" CARD ISSUANCE FEE DISCOUNT"+Constants.TEXT_QUALIFIER+",");
				buffer.append(Constants.TEXT_QUALIFIER+productTypeId+" TOP UP FEE"+Constants.TEXT_QUALIFIER+",");
				buffer.append(Constants.TEXT_QUALIFIER+productTypeId+" TOP UP FEE DISCOUNT"+Constants.TEXT_QUALIFIER+",");
			
			} else {
				buffer.append(Constants.TEXT_QUALIFIER+productTypeId+" FARE"+Constants.TEXT_QUALIFIER+",");
				buffer.append(Constants.TEXT_QUALIFIER+productTypeId+" FULL ADMIN FEE"+Constants.TEXT_QUALIFIER+",");
				buffer.append(Constants.TEXT_QUALIFIER+productTypeId+" ADMIN FEE DIS"+Constants.TEXT_QUALIFIER+",");
			}
			
		}
		
		buffer.append(Constants.TEXT_QUALIFIER+headers[0]+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+headers[1]+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+headers[2]+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+headers[3]+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+headers[4]+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+headers[5]+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+headers[6]+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+headers[7]+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+"MISC"+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+"CREDIT AMT"+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+"DEBIT AMT"+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+"PREPAID DISCOUNT"+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+"GST"+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+"Grand Total"+Constants.TEXT_QUALIFIER+",");
		buffer.append("\n");
		
		//Data
		List<Object[]> results = this.businessHelper.getReportBusiness().getRevenueSummary(
				entityNo,
				invoiceStart!=null && invoiceStart.length()!=0 ? invoiceStart + " 00:00:00" : invoiceEnd!=null && invoiceEnd.length()!=0 ? invoiceEnd + " 00:00:00" : invoiceStart,
				invoiceEnd!=null && invoiceEnd.length()!=0 ? invoiceEnd + " 23:59:59" : invoiceStart!=null && invoiceStart.length()!=0 ? invoiceStart + " 23:59:59" : invoiceEnd
		);
		logger.info("size = " + results.size());
		
		if(results.size()!=0){
			for(int i=0;i<results.size(); i++){
				Object[] dataArray = results.get(i);
				Object invoiceHeaderNo = dataArray[0];
				
				//For First Half of Columns before Dynamic Columns
				for(int j=1;j<=beforeDynamicColumns;j++){
					Object data = dataArray[j];
					if(data!=null){
						buffer.append(""+Constants.TEXT_QUALIFIER+data+Constants.TEXT_QUALIFIER+",");
					}else{
						buffer.append(Constants.TEXT_QUALIFIER+"0.00"+Constants.TEXT_QUALIFIER+",");
					}
				}
				
				//Dynamic Columns
				Map<String, Object[]> productTypeTripInfos = this.businessHelper.getReportBusiness().getRevenueProductTypeTripInfos(
						invoiceHeaderNo.toString(),
						invoiceStart!=null && invoiceStart.length()!=0 ? invoiceStart + " 00:00:00" : invoiceEnd!=null && invoiceEnd.length()!=0 ? invoiceEnd + " 00:00:00" : invoiceStart,
						invoiceEnd!=null && invoiceEnd.length()!=0 ? invoiceEnd + " 23:59:59" : invoiceStart!=null && invoiceStart.length()!=0 ? invoiceStart + " 23:59:59" : invoiceEnd
				);
				
				//Dynamic Columns
				Map<String, Object[]> productTypePrepaidInfos = this.businessHelper.getReportBusiness().getRevenueProductTypePrepaidInfos(
						invoiceHeaderNo.toString(),
						invoiceStart!=null && invoiceStart.length()!=0 ? invoiceStart + " 00:00:00" : invoiceEnd!=null && invoiceEnd.length()!=0 ? invoiceEnd + " 00:00:00" : invoiceStart,
						invoiceEnd!=null && invoiceEnd.length()!=0 ? invoiceEnd + " 23:59:59" : invoiceStart!=null && invoiceStart.length()!=0 ? invoiceStart + " 23:59:59" : invoiceEnd
				);
				
				for(PmtbProductType productType: productTypes){
					String productTypeId = productType.getProductTypeId();
					
					String prepaid = productType.getPrepaid();
					if(NonConfigurableConstants.getBoolean(prepaid)){
						
						if(productTypeTripInfos.get(productTypeId)!=null){
							Object[] amountArray = productTypePrepaidInfos.get(productTypeId);
							
							buffer.append(Constants.TEXT_QUALIFIER+amountArray[1].toString()+Constants.TEXT_QUALIFIER+",");
							buffer.append(Constants.TEXT_QUALIFIER+amountArray[2].toString()+Constants.TEXT_QUALIFIER+",");
							buffer.append(Constants.TEXT_QUALIFIER+amountArray[3].toString()+Constants.TEXT_QUALIFIER+",");
							buffer.append(Constants.TEXT_QUALIFIER+amountArray[4].toString()+Constants.TEXT_QUALIFIER+",");
							buffer.append(Constants.TEXT_QUALIFIER+amountArray[5].toString()+Constants.TEXT_QUALIFIER+",");
							buffer.append(Constants.TEXT_QUALIFIER+amountArray[6].toString()+Constants.TEXT_QUALIFIER+",");
						}
						else{
							buffer.append(Constants.TEXT_QUALIFIER+"0.00"+Constants.TEXT_QUALIFIER+",");
							buffer.append(Constants.TEXT_QUALIFIER+"0.00"+Constants.TEXT_QUALIFIER+",");
							buffer.append(Constants.TEXT_QUALIFIER+"0.00"+Constants.TEXT_QUALIFIER+",");
							buffer.append(Constants.TEXT_QUALIFIER+"0.00"+Constants.TEXT_QUALIFIER+",");
							buffer.append(Constants.TEXT_QUALIFIER+"0.00"+Constants.TEXT_QUALIFIER+",");
							buffer.append(Constants.TEXT_QUALIFIER+"0.00"+Constants.TEXT_QUALIFIER+",");
						}

					}else {
						
						if(productTypeTripInfos.get(productTypeId)!=null){
							Object[] amountArray = productTypeTripInfos.get(productTypeId);
							buffer.append(Constants.TEXT_QUALIFIER+amountArray[1].toString()+Constants.TEXT_QUALIFIER+",");
							buffer.append(Constants.TEXT_QUALIFIER+amountArray[2].toString()+Constants.TEXT_QUALIFIER+",");
							buffer.append(Constants.TEXT_QUALIFIER+amountArray[3].toString()+Constants.TEXT_QUALIFIER+",");
						}
						else{
							buffer.append(Constants.TEXT_QUALIFIER+"0.00"+Constants.TEXT_QUALIFIER+",");
							buffer.append(Constants.TEXT_QUALIFIER+"0.00"+Constants.TEXT_QUALIFIER+",");
							buffer.append(Constants.TEXT_QUALIFIER+"0.00"+Constants.TEXT_QUALIFIER+",");
						}
					}

				}
				
				//For Second Half of Columns after Dynamic Columns
				for(int j=beforeDynamicColumns+1; j<dataArray.length; j++){
					Object data = dataArray[j];
					if(data!=null){
						buffer.append(""+Constants.TEXT_QUALIFIER+data+Constants.TEXT_QUALIFIER+",");
					}else{
						buffer.append(Constants.TEXT_QUALIFIER+"0.00"+Constants.TEXT_QUALIFIER+",");
					}
				}
				
				//Grand Total
				if(dataArray[5]!=null){
					buffer.append(""+Constants.TEXT_QUALIFIER+dataArray[5]+Constants.TEXT_QUALIFIER+",");
				}else{
					buffer.append(Constants.TEXT_QUALIFIER+"0.00"+Constants.TEXT_QUALIFIER+",");
				}
				buffer.append("\n");
			}
		}else{
			buffer.append(""+Constants.TEXT_QUALIFIER+"No records found."+Constants.TEXT_QUALIFIER+",");
			buffer.append("\n");
		}
		buffer.append("\n");
		
		// misc
		Map<String, String> miscHeaders = this.businessHelper.getReportBusiness().getRevenueHeaderForMisc();
		Map<String, Integer> miscHeadersIndex = new HashMap<String, Integer>();
		
		buffer.append(Constants.TEXT_QUALIFIER+"ACCT NO"+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+"ACCT NAME"+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+"INV DATE"+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+"INV NO"+Constants.TEXT_QUALIFIER+",");
		
		int counter = 1;
		for(Entry<String,String> entry : miscHeaders.entrySet()){
			buffer.append(Constants.TEXT_QUALIFIER+entry.getValue()+Constants.TEXT_QUALIFIER+",");
			buffer.append(Constants.TEXT_QUALIFIER+entry.getValue()+" DISCOUNT"+Constants.TEXT_QUALIFIER+",");
			miscHeadersIndex.put(entry.getKey(), counter++);
		}
		buffer.append(Constants.TEXT_QUALIFIER+"TOTAL"+Constants.TEXT_QUALIFIER+",");
		buffer.append("\n");
		//Data
		results = this.businessHelper.getReportBusiness().getRevenueMisc(
				entityNo,
				invoiceStart!=null && invoiceStart.length()!=0 ? invoiceStart + " 00:00:00" : invoiceEnd!=null && invoiceEnd.length()!=0 ? invoiceEnd + " 00:00:00" : invoiceStart,
				invoiceEnd!=null && invoiceEnd.length()!=0 ? invoiceEnd + " 23:59:59" : invoiceStart!=null && invoiceStart.length()!=0 ? invoiceStart + " 23:59:59" : invoiceEnd
		);
		logger.info("size = " + results.size());
		if(results.size()!=0){
			
			String prevInvoice = null;
			Double total = new Double(0);
			Integer recordSize = 4+(miscHeaders.size()*2)+1;
			Object[] record = new Object[recordSize];
			Integer totalIndex = 4+(miscHeaders.size()*2);
			
			for(Object[] result : results){
				String custNo 		= (String)result[0];
				String accountName 	= (String)result[1];
				String invoiceDate	= (String)result[2];
				String invoiceNo	= (String)result[3];
				String txnCodeNo	= (String)result[4];
				String amount 		= (String)result[5];
				String discount 	= (String)result[6];
				
				//first record
				if(prevInvoice==null){
					prevInvoice=invoiceNo;
					record = new Object[recordSize];
					total = new Double(0);
					
					record[0] = custNo;
					record[1] = accountName;
					record[2] = invoiceDate;
					record[3] = invoiceNo;
					
					if(txnCodeNo == null) continue;
					
					record[3+miscHeadersIndex.get(txnCodeNo)+(miscHeadersIndex.get(txnCodeNo)-1)] = amount;
					record[3+miscHeadersIndex.get(txnCodeNo)+(miscHeadersIndex.get(txnCodeNo)-1)+1] = discount;
					record[totalIndex] = total += new Double(amount) + new Double(discount);
				}
				//new row record
				else if(!prevInvoice.equals(invoiceNo)){
					//print prev record
					for(Object value : record)
						buffer.append(""+Constants.TEXT_QUALIFIER+(value==null?0:value)+Constants.TEXT_QUALIFIER+",");
					buffer.append("\n");
					
					//Populate current record
					prevInvoice = invoiceNo;
					record = new Object[recordSize];
					total = new Double(0);
					
					record[0] = custNo;
					record[1] = accountName;
					record[2] = invoiceDate;
					record[3] = invoiceNo;
					
					if(txnCodeNo == null) continue;
					
					record[3+miscHeadersIndex.get(txnCodeNo)+(miscHeadersIndex.get(txnCodeNo)-1)] = amount;
					record[3+miscHeadersIndex.get(txnCodeNo)+(miscHeadersIndex.get(txnCodeNo)-1)+1] = discount;
					record[totalIndex] = total += new Double(amount) + new Double(discount);
				}
				//same row record
				else{
					if(txnCodeNo == null) continue;
					record[3+miscHeadersIndex.get(txnCodeNo)+(miscHeadersIndex.get(txnCodeNo)-1)] = amount;
					record[3+miscHeadersIndex.get(txnCodeNo)+(miscHeadersIndex.get(txnCodeNo)-1)+1] = discount;
					record[totalIndex] = total += new Double(amount) + new Double(discount);
				}
			}
			//print last populated record
			for(Object value : record)
				buffer.append(""+Constants.TEXT_QUALIFIER+(value==null?0:value)+Constants.TEXT_QUALIFIER+",");
			buffer.append("\n");
			
		}else{
			buffer.append(""+Constants.TEXT_QUALIFIER+"No records found."+Constants.TEXT_QUALIFIER+",");
			buffer.append("\n");
		}
		return buffer;
	}
}
