package com.cdgtaxi.ibs.report.ui;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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

public class LoyaltyProgramReport extends ReportWindow implements CSVFormat {
	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(LoyaltyProgramReport.class);
	public LoyaltyProgramReport() throws IOException {
		super("Loyalty Program Report", "Rewards");
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
			filterDoubleQuote(reportParamsProperties);
			StringBuffer dataInCSV = this.generateCSVData(reportParamsProperties);
			AMedia media = new AMedia(report.replaceAll(" ", "_")+"."+extension, extension, format, dataInCSV.toString());
			Filedownload.save(media);
		}else{
			ByteArrayOutputStream os = new ByteArrayOutputStream();
			client.renderReport(repository+"/"+reportCategory+"/"+report+"/"+report+".rml", format, os, reportParamsProperties);
			AMedia media = new AMedia(report.replaceAll(" ", "_")+"."+extension, extension, format, os.toByteArray());
			if(format.equals(Constants.FORMAT_EXCEL)){
				throw new FormatNotSupportedException();
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
		String acctNo = (String)reportParamsProperties.get("2a. Account No");
		String acctName = (String)reportParamsProperties.get("2b. Account Name");
		String acctStatus = (String)reportParamsProperties.get("3. Account Status");
		String productTypeId = (String)reportParamsProperties.get("4. Subscribed Product Type");
		String pointStart = (String)reportParamsProperties.get("5. Points Date Start Month");
		String pointEnd = (String)reportParamsProperties.get("6. Points Date End Month");
		String salespersonNo = (String)reportParamsProperties.get("7. Sales Person");
		String sort = (String)reportParamsProperties.get("8. Sort By");
		String entityNo = (String)reportParamsProperties.get("9. Entity");
		StringBuffer buffer = new StringBuffer();
		//Report Header
		buffer.append(Constants.TEXT_QUALIFIER+"Integrated Billing System"+Constants.TEXT_QUALIFIER+",");
		buffer.append("\n");
		buffer.append(Constants.TEXT_QUALIFIER+"Loyalty Program Report"+Constants.TEXT_QUALIFIER+",");
		buffer.append("\n");
		buffer.append(Constants.TEXT_QUALIFIER+"Selection Criteria:"+Constants.TEXT_QUALIFIER+",");
		buffer.append("\n");
		buffer.append("\n");
		if(acctTypeNo!=null && acctTypeNo.length()>0){
			String acctTypeName = ((Listbox)this.getFellow("1. Account Type")).getSelectedItem().getLabel();
			buffer.append(Constants.TEXT_QUALIFIER+"Account Type: "+acctTypeName+Constants.TEXT_QUALIFIER+",");
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
			String acctStatusName = ((Listbox)this.getFellow("3. Account Status")).getSelectedItem().getLabel();
			buffer.append(Constants.TEXT_QUALIFIER+"Account Type: "+acctStatusName+Constants.TEXT_QUALIFIER+",");
			buffer.append("\n");
		}
		if(productTypeId!=null && productTypeId.length()>0){
			buffer.append(Constants.TEXT_QUALIFIER+"Product Type: "+productTypeId+Constants.TEXT_QUALIFIER+",");
			buffer.append("\n");
		}
		try{
			if((pointStart!=null && pointStart.length()>0) || (pointEnd!=null && pointEnd.length()!=0)){
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				buffer.append(Constants.TEXT_QUALIFIER+"Points Date: ");
				if(pointStart!=null && pointStart.length()>0){
					buffer.append(new SimpleDateFormat("MM/yyyy").format(sdf.parse(pointStart)));
				}else{
					buffer.append(new SimpleDateFormat("MM/yyyy").format(sdf.parse(pointEnd)));
				}
				buffer.append(" to ");
				if(pointEnd!=null && pointEnd.length()!=0){
					buffer.append(new SimpleDateFormat("MM/yyyy").format(sdf.parse(pointEnd))+Constants.TEXT_QUALIFIER+",");
				}else{
					buffer.append(new SimpleDateFormat("MM/yyyy").format(sdf.parse(pointStart))+Constants.TEXT_QUALIFIER+",");
					}
				buffer.append("\n");
			}
		}catch(ParseException pe){
			logger.error(pe);
			pe.printStackTrace();
		}
		if(salespersonNo!=null && salespersonNo.length()>0){
			String salespersonName = ((Listbox)this.getFellow("7. Sales Person")).getSelectedItem().getLabel();
			buffer.append(Constants.TEXT_QUALIFIER+"Sales Person: "+salespersonName+Constants.TEXT_QUALIFIER+",");
			buffer.append("\n");
		}
		String sortLabel = ((Listbox)this.getFellow("8. Sort By")).getSelectedItem().getLabel();
		buffer.append(Constants.TEXT_QUALIFIER+"Sort By: "+sortLabel+Constants.TEXT_QUALIFIER+",");
		buffer.append("\n");
		
		if(entityNo!=null && entityNo.length()>0) {
			String entityName = ((Listbox)this.getFellow("9. Entity")).getSelectedItem().getLabel();
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
		buffer.append(Constants.TEXT_QUALIFIER+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+"Opening balance"+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+"Earned"+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+"Adjusted"+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+"Redeemed"+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+"Expired / Forfeiture"+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+"Closing balance"+Constants.TEXT_QUALIFIER+",");
		buffer.append("\n");
		buffer.append(Constants.TEXT_QUALIFIER+"Account No"+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+"Account Name"+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+"Prdts Subscription"+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+"Value per Point\nS$"+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+"Points"+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+"Value ($)"+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+"Points"+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+"Value ($)"+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+"Points"+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+"Value ($)"+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+"Points"+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+"Value ($)"+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+"Points"+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+"Value ($)"+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+"Points"+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+"Value ($)"+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+"CutOff Date"+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+"Expiry Date (Customer)"+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+"Join Date"+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+"Terminate Date"+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+"Account Status"+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+"Ship Contact Person"+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+"Ship Contact No (Tel, Mobile)"+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+"Ship Fax"+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+"Ship Email"+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+"Ship Address Block No"+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+"Ship Address Street Name"+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+"Ship Address Unit No"+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+"Ship Address Building Name"+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+"Ship Address Area"+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+"Ship Address Postal"+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+"Sales Person"+Constants.TEXT_QUALIFIER+",");
		buffer.append("\n");
		//Data
		List<Object[]> results = this.businessHelper.getReportBusiness().getLoyaltyProgramReport(
				acctTypeNo,
				acctNo!=null ? "%" +acctNo+ "%": null,
				acctName !=null ? "%" +acctName+ "%" : null,
				acctStatus,
				productTypeId!=null && productTypeId.length()!=0 ? "%" +productTypeId+ "%" : null,
				pointStart!=null ? pointStart : pointEnd,
				pointEnd!=null ? pointEnd : pointStart,
				salespersonNo,
				sort,
				entityNo		
		);
		logger.info("size = " + results.size());
		if(results.size()!=0){
			double[] totals = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
			for(int i=0;i<results.size(); i++){
				Object[] result = results.get(i);
				if(result[4]!=null){
					totals[0]+=Double.parseDouble(result[4].toString().replaceAll(",", ""));
				}
				if(result[5]!=null){
					totals[1]+=Double.parseDouble(result[5].toString().replaceAll(",", ""));
				}
				if(result[6]!=null){
					totals[2]+=Double.parseDouble(result[6].toString().replaceAll(",", ""));
				}
				if(result[7]!=null){
					totals[3]+=Double.parseDouble(result[7].toString().replaceAll(",", ""));
				}
				if(result[8]!=null){
					totals[4]+=Double.parseDouble(result[8].toString().replaceAll(",", ""));
				}
				if(result[9]!=null){
					totals[5]+=Double.parseDouble(result[9].toString().replaceAll(",", ""));
				}
				if(result[10]!=null){
					totals[6]+=Double.parseDouble(result[10].toString().replaceAll(",", ""));
				}
				if(result[11]!=null){
					totals[7]+=Double.parseDouble(result[11].toString().replaceAll(",", ""));
				}
				if(result[12]!=null){
					totals[8]+=Double.parseDouble(result[12].toString().replaceAll(",", ""));
				}
				if(result[13]!=null){
					totals[9]+=Double.parseDouble(result[13].toString().replaceAll(",", ""));
				}
				if(result[14]!=null){
					totals[10]+=Double.parseDouble(result[14].toString().replaceAll(",", ""));
				}
				if(result[15]!=null){
					totals[11]+=Double.parseDouble(result[15].toString().replaceAll(",", ""));
				}
				for(int j=0;j<result.length;j++){//for(Object data : result){
					Object data = result[j];
					if(data!=null){
						if(j==20){
							buffer.append(""+Constants.TEXT_QUALIFIER+NonConfigurableConstants.ACCOUNT_STATUS.get(data)+Constants.TEXT_QUALIFIER+",");
						}else{
							buffer.append(""+Constants.TEXT_QUALIFIER+data+Constants.TEXT_QUALIFIER+",");
						}
					}else{
						buffer.append(Constants.TEXT_QUALIFIER+Constants.TEXT_QUALIFIER+",");
					}
				}
				buffer.append("\n");
			}
			buffer.append(Constants.TEXT_QUALIFIER+Constants.TEXT_QUALIFIER+",");
			buffer.append(Constants.TEXT_QUALIFIER+Constants.TEXT_QUALIFIER+",");
			buffer.append(Constants.TEXT_QUALIFIER+Constants.TEXT_QUALIFIER+",");
			buffer.append(Constants.TEXT_QUALIFIER+Constants.TEXT_QUALIFIER+",");
			buffer.append(Constants.TEXT_QUALIFIER+new DecimalFormat(StringUtil.GLOBAL_INTEGER_FORMAT).format(totals[0])+Constants.TEXT_QUALIFIER+",");
			buffer.append(Constants.TEXT_QUALIFIER+new DecimalFormat(StringUtil.GLOBAL_DECIMAL_FORMAT).format(totals[1])+Constants.TEXT_QUALIFIER+",");
			buffer.append(Constants.TEXT_QUALIFIER+new DecimalFormat(StringUtil.GLOBAL_INTEGER_FORMAT).format(totals[2])+Constants.TEXT_QUALIFIER+",");
			buffer.append(Constants.TEXT_QUALIFIER+new DecimalFormat(StringUtil.GLOBAL_DECIMAL_FORMAT).format(totals[3])+Constants.TEXT_QUALIFIER+",");
			buffer.append(Constants.TEXT_QUALIFIER+new DecimalFormat(StringUtil.GLOBAL_INTEGER_FORMAT).format(totals[4])+Constants.TEXT_QUALIFIER+",");
			buffer.append(Constants.TEXT_QUALIFIER+new DecimalFormat(StringUtil.GLOBAL_DECIMAL_FORMAT).format(totals[5])+Constants.TEXT_QUALIFIER+",");
			buffer.append(Constants.TEXT_QUALIFIER+new DecimalFormat(StringUtil.GLOBAL_INTEGER_FORMAT).format(totals[6])+Constants.TEXT_QUALIFIER+",");
			buffer.append(Constants.TEXT_QUALIFIER+new DecimalFormat(StringUtil.GLOBAL_DECIMAL_FORMAT).format(totals[7])+Constants.TEXT_QUALIFIER+",");
			buffer.append(Constants.TEXT_QUALIFIER+new DecimalFormat(StringUtil.GLOBAL_INTEGER_FORMAT).format(totals[8])+Constants.TEXT_QUALIFIER+",");
			buffer.append(Constants.TEXT_QUALIFIER+new DecimalFormat(StringUtil.GLOBAL_DECIMAL_FORMAT).format(totals[9])+Constants.TEXT_QUALIFIER+",");
			buffer.append(Constants.TEXT_QUALIFIER+new DecimalFormat(StringUtil.GLOBAL_DECIMAL_FORMAT).format(totals[10])+Constants.TEXT_QUALIFIER+",");
			buffer.append(Constants.TEXT_QUALIFIER+new DecimalFormat(StringUtil.GLOBAL_DECIMAL_FORMAT).format(totals[11])+Constants.TEXT_QUALIFIER+",");
		}else{
			buffer.append(""+Constants.TEXT_QUALIFIER+"No records found."+Constants.TEXT_QUALIFIER+",");
			buffer.append("\n");
		}
		return buffer;
	}
}