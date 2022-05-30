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
import com.cdgtaxi.ibs.util.StringUtil;
import com.elixirtech.ers2.client.ERSClient;
import com.elixirtech.net.NetException;

public class CustomerUsageComparsionWindow extends ReportWindow implements CSVFormat {
	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(CustomerUsageComparsionWindow.class);
	public CustomerUsageComparsionWindow() throws IOException {
		super("Customer Usage Comparsion", "Trips");
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
		String invoiceMonth = (String)reportParamsProperties.get("1. Invoice Month/Year");
		String productType = (String)reportParamsProperties.get("2. Product Type");
		String sortBy = (String)reportParamsProperties.get("3. Sort By");
		String entityNo = (String)reportParamsProperties.get("4. Entity");
		StringBuffer buffer = new StringBuffer();
		//Report Header
		buffer.append(Constants.TEXT_QUALIFIER+"Integrated Billing System"+Constants.TEXT_QUALIFIER+",");
		buffer.append("\n");
		buffer.append(Constants.TEXT_QUALIFIER+"Customer Usage Comparsion"+Constants.TEXT_QUALIFIER+",");
		buffer.append("\n");
		buffer.append(Constants.TEXT_QUALIFIER+"Selection Criteria:"+Constants.TEXT_QUALIFIER+",");
		buffer.append("\n");
		buffer.append("\n");
		if(invoiceMonth!=null && invoiceMonth.length()>0){
			buffer.append(Constants.TEXT_QUALIFIER+"Invoice Month/Year: "+this.convertYYYYMMDDtoMMMYYYY(invoiceMonth)+Constants.TEXT_QUALIFIER+",");
			buffer.append("\n");
		}
		if(productType!=null && productType.length()>0){
			String productTypeName = ((Listbox)this.getFellow("2. Product Type")).getSelectedItem().getLabel();
			buffer.append(Constants.TEXT_QUALIFIER+"Product Type: "+productTypeName+Constants.TEXT_QUALIFIER+",");
			buffer.append("\n");
		}
		if(sortBy!=null && sortBy.length()>0){
			buffer.append(Constants.TEXT_QUALIFIER+"Sort By: "+NonConfigurableConstants.CUC_ORDER.get(sortBy)+Constants.TEXT_QUALIFIER+",");
			buffer.append("\n");
		}
		if(entityNo!=null && entityNo.length()>0){
			String entityName = ((Listbox)this.getFellow("4. Entity")).getSelectedItem().getLabel();
			buffer.append(Constants.TEXT_QUALIFIER+"Entity Name: "+entityName+Constants.TEXT_QUALIFIER+",");
			buffer.append("\n");
		}
		buffer.append("\n");
		buffer.append("\n");
		buffer.append(Constants.TEXT_QUALIFIER+"Printed By: "+reportParamsProperties.get("printedBy")+Constants.TEXT_QUALIFIER+",");
		buffer.append("\n");
		buffer.append(Constants.TEXT_QUALIFIER+"Report Date: "+DateUtil.convertDateToStr(DateUtil.getCurrentDate(), DateUtil.GLOBAL_DATE_FORMAT)+Constants.TEXT_QUALIFIER+",");
		buffer.append("\n");
		//Column Title
		buffer.append(Constants.TEXT_QUALIFIER+"S/N"+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+"Account Name"+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+"Last Month Trips"+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+"Last Month Amt ($)"+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+"Last Month Admin (%)"+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+"Last Month Admin ($)"+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+"Last Month GST ($)"+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+"Last Month Total ($)"+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+"This Month Trips"+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+"This Month Amt ($)"+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+"This Month Admin (%)"+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+"This Month Admin ($)"+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+"This Month GST ($)"+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+"This Month Total ($)"+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+"Change Amt ($)"+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+"Change Amt (%)"+Constants.TEXT_QUALIFIER+",");
		buffer.append("\n");
		List<Object[]> results = this.businessHelper.getReportBusiness().getCustomerUsageComparsion(
				invoiceMonth,
				productType,
				sortBy, entityNo
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
						if(j>0 && j<13){
							buffer.append(Constants.TEXT_QUALIFIER+"0"+Constants.TEXT_QUALIFIER+",");
						}else{
							buffer.append(Constants.TEXT_QUALIFIER+Constants.TEXT_QUALIFIER+",");
						}
					}
				}
				logger.info("result[6] = "+result[6]);
				logger.info("result[12] = "+result[12]);
				if(result[12]!=null && result[6]!=null && !result[6].equals("0.00") && !result[12].equals("0.00")){
					BigDecimal thisTotal = new BigDecimal(((String)result[12]).replaceAll(",", ""));
					BigDecimal lastTotal = new BigDecimal(((String)result[6]).replaceAll(",", ""));
					BigDecimal difference = thisTotal.subtract(lastTotal);
					buffer.append(Constants.TEXT_QUALIFIER+StringUtil.bigDecimalToString(difference, StringUtil.GLOBAL_DECIMAL_FORMAT)+Constants.TEXT_QUALIFIER+",");
					if(lastTotal.equals(new BigDecimal("0.00"))){
						buffer.append(Constants.TEXT_QUALIFIER+"-"+Constants.TEXT_QUALIFIER+",");
					}else{
						buffer.append(Constants.TEXT_QUALIFIER+StringUtil.bigDecimalToString(new BigDecimal(difference.doubleValue() / lastTotal.doubleValue() * 100), StringUtil.GLOBAL_DECIMAL_FORMAT)+Constants.TEXT_QUALIFIER+",");
					}
				}else if(result[12]!=null && !result[12].equals("0.00")){
					BigDecimal thisTotal = new BigDecimal(((String)result[12]).replaceAll(",", ""));
					BigDecimal lastTotal = new BigDecimal("0");
					BigDecimal difference = thisTotal.subtract(lastTotal);
					buffer.append(Constants.TEXT_QUALIFIER+StringUtil.bigDecimalToString(difference, StringUtil.GLOBAL_DECIMAL_FORMAT)+Constants.TEXT_QUALIFIER+",");
					buffer.append(Constants.TEXT_QUALIFIER+"-"+Constants.TEXT_QUALIFIER+",");
				}else if(result[6]!=null && !result[6].equals("0.00")){
					BigDecimal thisTotal = new BigDecimal("0");
					BigDecimal lastTotal = new BigDecimal(((String)result[6]).replaceAll(",", ""));
					BigDecimal difference = thisTotal.subtract(lastTotal);
					buffer.append(Constants.TEXT_QUALIFIER+StringUtil.bigDecimalToString(difference, StringUtil.GLOBAL_DECIMAL_FORMAT)+Constants.TEXT_QUALIFIER+",");
					buffer.append(Constants.TEXT_QUALIFIER+StringUtil.bigDecimalToString(new BigDecimal(difference.doubleValue() / lastTotal.doubleValue() * 100), StringUtil.GLOBAL_DECIMAL_FORMAT)+Constants.TEXT_QUALIFIER+",");
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