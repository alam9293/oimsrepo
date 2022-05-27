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

import com.cdgtaxi.ibs.Uri;
import com.cdgtaxi.ibs.common.NonConfigurableConstants;
import com.cdgtaxi.ibs.report.Constants;
import com.cdgtaxi.ibs.util.DateUtil;
import com.elixirtech.ers2.client.ERSClient;
import com.elixirtech.net.NetException;

public class CreditDebitNoteWindow extends ReportWindow implements CSVFormat {
	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(CreditDebitNoteWindow.class);
	public CreditDebitNoteWindow() throws IOException {
		super("Credit Debit Note", "Note");
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

	public StringBuffer generateCSVData(Properties reportParamsProperties) {
		String noteStart = (String)reportParamsProperties.get("1. Credit/Debit Note Start Date");
		String noteEnd = (String)reportParamsProperties.get("2. Credit/Debit Note End Date");
		String cancelStart = (String)reportParamsProperties.get("3. Cancelled Start Date");
		String cancelEnd = (String)reportParamsProperties.get("4. Cancelled End Date");
		String acctNo = (String)reportParamsProperties.get("5a. Account No");
		String acctName = (String)reportParamsProperties.get("5b. Account Name");
		String noteType = (String)reportParamsProperties.get("6. Type");
		String order = (String)reportParamsProperties.get("7. Order");
		String entityNo = (String)reportParamsProperties.get("8. Entity");
		StringBuffer buffer = new StringBuffer();
		//Report Header
		buffer.append(Constants.TEXT_QUALIFIER+"Integrated Billing System"+Constants.TEXT_QUALIFIER+",");
		buffer.append("\n");
		buffer.append(Constants.TEXT_QUALIFIER+"Credit/Debit Note"+Constants.TEXT_QUALIFIER+",");
		buffer.append("\n");
		buffer.append(Constants.TEXT_QUALIFIER+"Selection Criteria:"+Constants.TEXT_QUALIFIER+",");
		buffer.append("\n");
		buffer.append("\n");
		if((noteStart!=null && noteStart.length()>0) || (noteEnd!=null && noteEnd.length()!=0)){
			buffer.append(Constants.TEXT_QUALIFIER+"Debit & Credit Note Date: ");
			if(noteStart!=null && noteStart.length()>0){
				buffer.append(noteStart);
			}else{
				buffer.append(noteEnd);
			}
			buffer.append(" to ");
			if(noteEnd!=null && noteEnd.length()!=0){
				buffer.append(noteEnd+Constants.TEXT_QUALIFIER+",");
			}else{
				buffer.append(noteStart+Constants.TEXT_QUALIFIER+",");
			}
			buffer.append("\n");
		}
		if((cancelStart!=null && cancelStart.length()>0) || (cancelEnd!=null && cancelEnd.length()!=0)){
			buffer.append(Constants.TEXT_QUALIFIER+"Cancelled Date: ");
			if(cancelStart!=null && cancelStart.length()>0){
				buffer.append(cancelStart);
			}else{
				buffer.append(cancelEnd);
			}
			buffer.append(" to ");
			if(cancelEnd!=null && cancelEnd.length()!=0){
				buffer.append(cancelEnd+Constants.TEXT_QUALIFIER+",");
			}else{
				buffer.append(cancelStart+Constants.TEXT_QUALIFIER+",");
			}
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
		if(noteType!=null && noteType.length()>0){
			buffer.append(Constants.TEXT_QUALIFIER+"Note Type: "+NonConfigurableConstants.NOTE_TYPE.get(noteType)+Constants.TEXT_QUALIFIER+",");
			buffer.append("\n");
		}
		if(order!=null && order.length()>0){
			buffer.append(Constants.TEXT_QUALIFIER+"Order: "+NonConfigurableConstants.CDN_ORDER.get(order)+Constants.TEXT_QUALIFIER+",");
			buffer.append("\n");
		}
		
		if(entityNo!=null && entityNo.length()>0) {
			String entityName = ((Listbox)this.getFellow("8. Entity")).getSelectedItem().getLabel();
			buffer.append(Constants.TEXT_QUALIFIER+"Entity: "+entityName+Constants.TEXT_QUALIFIER+",");
			buffer.append("\n");
		}
		
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
		buffer.append(Constants.TEXT_QUALIFIER+"Debit/Credit Note No"+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+"Reference Invoice No"+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+"Outstanding Amount ($)"+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+"Debit/Credit Note Date"+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+"Type"+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+"Cancelled Date"+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+"Note Amt ($)"+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+"Admin Fee ($)"+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+"GST ($)"+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+"Discount ($)"+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+"Promo Discount ($)"+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+"Cr/Dr Amt ($)"+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+"Cancelled Cr/Dr Amt"+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+"Net Amt ($)"+Constants.TEXT_QUALIFIER+",");
		buffer.append("\n");
		//Data
		List<Object[]> results = this.businessHelper.getReportBusiness().getCreditDebitNote(
				noteStart!=null && noteStart.length()!=0 ? noteStart+" 00:00:00" : noteEnd!=null && noteEnd.length()!=0 ? noteEnd+" 00:00:00" : noteStart,
				noteEnd!=null && noteEnd.length()!=0 ? noteEnd+" 23:59:59" : noteStart!=null && noteStart.length()!=0 ? noteStart+" 23:59:59" : noteEnd,
				cancelStart!=null && cancelStart.length()!=0 ? cancelStart+" 00:00:00" : cancelEnd!=null && cancelEnd.length()!=0 ? cancelEnd+" 00:00:00" : cancelStart,
				cancelEnd!=null && cancelEnd.length()!=0 ? cancelEnd+" 23:59:59" : cancelStart!=null && cancelStart.length()!=0 ? cancelStart+" 23:59:59" : cancelEnd,
				"%"+acctNo+"%",
				"%"+acctName+"%",
				noteType,
				order,
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
						if(j==6){// payment mode
							buffer.append(""+Constants.TEXT_QUALIFIER+NonConfigurableConstants.NOTE_TYPE.get(data)+Constants.TEXT_QUALIFIER+",");
						}else{
							buffer.append(""+Constants.TEXT_QUALIFIER+data+Constants.TEXT_QUALIFIER+",");
						}
					}else{
						buffer.append(Constants.TEXT_QUALIFIER+Constants.TEXT_QUALIFIER+",");
					}
				}
				String netTotal = (new BigDecimal(result[13]==null ? "0" : ((String)result[13]).replaceAll(",", ""))).subtract(new BigDecimal(result[14]==null ? "0" : ((String)result[14]).replaceAll(",", ""))).toString();
				buffer.append(netTotal);
				buffer.append("\n");
			}
		}else{
			buffer.append(""+Constants.TEXT_QUALIFIER+"No records found."+Constants.TEXT_QUALIFIER+",");
			buffer.append("\n");
		}
		return buffer;
	}
}