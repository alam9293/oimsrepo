package com.cdgtaxi.ibs.report.ui;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.DecimalFormat;
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
import com.cdgtaxi.ibs.util.StringUtil;
import com.elixirtech.ers2.client.ERSClient;
import com.elixirtech.net.NetException;

public class ItemTypeRevenueProfitReport extends ReportWindow implements CSVFormat {
	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(ItemTypeRevenueProfitReport.class);
	public ItemTypeRevenueProfitReport() throws IOException {
		super("Item Type Revenue and Profit Report", "Revenue");
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
		String startMonth = (String)reportParamsProperties.get("1. Start Month");
		String endMonth = (String)reportParamsProperties.get("2. End Month");
		String itemTypeNo = (String)reportParamsProperties.get("3. Item Type");
		String entityNo = (String)reportParamsProperties.get("4. Entity");
		
		StringBuffer buffer = new StringBuffer();
		//Report Header
		buffer.append(Constants.TEXT_QUALIFIER+"Integrated Billing System"+Constants.TEXT_QUALIFIER+",");
		buffer.append("\n");
		buffer.append(Constants.TEXT_QUALIFIER+"Item Type Revenue and Profit Report"+Constants.TEXT_QUALIFIER+",");
		buffer.append("\n");
		buffer.append(Constants.TEXT_QUALIFIER+"Selection Criteria:"+Constants.TEXT_QUALIFIER+",");
		buffer.append("\n");
		buffer.append("\n");
		if((startMonth!=null && startMonth.length()>0) || (endMonth!=null && endMonth.length()!=0)){
			buffer.append(Constants.TEXT_QUALIFIER+"Invoice Month: ");
			if(startMonth!=null && startMonth.length()>0){
				buffer.append(this.convertYYYYMMDDtoMMMYYYY(startMonth));
			}else{
				buffer.append(this.convertYYYYMMDDtoMMMYYYY(endMonth));
			}
			buffer.append(" to ");
			if(endMonth!=null && endMonth.length()!=0){
				buffer.append(this.convertYYYYMMDDtoMMMYYYY(endMonth)+Constants.TEXT_QUALIFIER+",");
			}else{
				buffer.append(this.convertYYYYMMDDtoMMMYYYY(startMonth)+Constants.TEXT_QUALIFIER+",");
			}
			buffer.append("\n");
		}
		if(itemTypeNo!=null && itemTypeNo.length()>0){
			String itemTypeName = ((Listbox)this.getFellow("3. Item Type")).getSelectedItem().getLabel();
			buffer.append(Constants.TEXT_QUALIFIER+"Item Type: "+itemTypeName+Constants.TEXT_QUALIFIER+",");
			buffer.append("\n");
		}
		
		if(entityNo!=null && entityNo.length()>0) {
					String entityName = ((Listbox)this.getFellow("4. Entity")).getSelectedItem().getLabel();
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
		buffer.append(Constants.TEXT_QUALIFIER+"Month"+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+"Item Type"+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+"Quantity Sold"+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+"Sales Revenue $"+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+"Quantity Expire"+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+"Expire Revenue $"+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+"Quantity Redeemed"+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+"Quantity Not Redeemed"+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+"Realized / Unrealized Profit (GROSS)"+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+"Processing Fee"+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+"Discounts"+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+"Realized / Unrealized Profit (NET)"+Constants.TEXT_QUALIFIER+",");
		buffer.append("\n");
		//Data
		List<Object[]> results = this.businessHelper.getReportBusiness().getItemTypeRevenueProfitReport(
				startMonth, endMonth, itemTypeNo, entityNo
		);
		logger.info("size = " + results.size());
		String prevItemType = null;
		DecimalFormat amtFormat = new DecimalFormat(StringUtil.GLOBAL_INTEGER_FORMAT);
		DecimalFormat valueFormat = new DecimalFormat(StringUtil.GLOBAL_DECIMAL_FORMAT);
		double totals[] = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		if(results.size()!=0){
			for(int i=0;i<results.size(); i++){
				if(prevItemType!=null && !prevItemType.equals(results.get(i)[1])){
					buffer.append(Constants.TEXT_QUALIFIER+prevItemType+" Total"+Constants.TEXT_QUALIFIER+",");
					buffer.append(Constants.TEXT_QUALIFIER+Constants.TEXT_QUALIFIER+",");
					buffer.append(Constants.TEXT_QUALIFIER+amtFormat.format(totals[0])+Constants.TEXT_QUALIFIER+",");
					buffer.append(Constants.TEXT_QUALIFIER+valueFormat.format(totals[1])+Constants.TEXT_QUALIFIER+",");
					buffer.append(Constants.TEXT_QUALIFIER+amtFormat.format(totals[2])+Constants.TEXT_QUALIFIER+",");
					buffer.append(Constants.TEXT_QUALIFIER+valueFormat.format(totals[3])+Constants.TEXT_QUALIFIER+",");
					buffer.append(Constants.TEXT_QUALIFIER+amtFormat.format(totals[4])+Constants.TEXT_QUALIFIER+",");
					buffer.append(Constants.TEXT_QUALIFIER+amtFormat.format(totals[5])+Constants.TEXT_QUALIFIER+",");
					buffer.append(Constants.TEXT_QUALIFIER+valueFormat.format(totals[6])+Constants.TEXT_QUALIFIER+",");
					buffer.append(Constants.TEXT_QUALIFIER+valueFormat.format(totals[7])+Constants.TEXT_QUALIFIER+",");
					buffer.append(Constants.TEXT_QUALIFIER+valueFormat.format(totals[8])+Constants.TEXT_QUALIFIER+",");
					buffer.append(Constants.TEXT_QUALIFIER+valueFormat.format(totals[9])+Constants.TEXT_QUALIFIER+",");
					buffer.append("\n");
					for(int counter=0;counter<totals.length;counter++){
						totals[counter] = 0;
					}
					buffer.append(Constants.TEXT_QUALIFIER+"Month"+Constants.TEXT_QUALIFIER+",");
					buffer.append(Constants.TEXT_QUALIFIER+"Item Type"+Constants.TEXT_QUALIFIER+",");
					buffer.append(Constants.TEXT_QUALIFIER+"Quantity Sold"+Constants.TEXT_QUALIFIER+",");
					buffer.append(Constants.TEXT_QUALIFIER+"Sales Revenue $"+Constants.TEXT_QUALIFIER+",");
					buffer.append(Constants.TEXT_QUALIFIER+"Quantity Expire"+Constants.TEXT_QUALIFIER+",");
					buffer.append(Constants.TEXT_QUALIFIER+"Expire Revenue $"+Constants.TEXT_QUALIFIER+",");
					buffer.append(Constants.TEXT_QUALIFIER+"Quantity Redeemed"+Constants.TEXT_QUALIFIER+",");
					buffer.append(Constants.TEXT_QUALIFIER+"Quantity Not Redeemed"+Constants.TEXT_QUALIFIER+",");
					buffer.append(Constants.TEXT_QUALIFIER+"Realized / Unrealized Profit (GROSS)"+Constants.TEXT_QUALIFIER+",");
					buffer.append(Constants.TEXT_QUALIFIER+"Processing Fee"+Constants.TEXT_QUALIFIER+",");
					buffer.append(Constants.TEXT_QUALIFIER+"Discounts"+Constants.TEXT_QUALIFIER+",");
					buffer.append(Constants.TEXT_QUALIFIER+"Realized / Unrealized Profit (NET)"+Constants.TEXT_QUALIFIER+",");
					buffer.append("\n");
				}
				prevItemType = (String)results.get(i)[1];
				Object[] result = results.get(i);
				totals[0] += Double.parseDouble(((String)result[2]).replaceAll(",", ""));
				totals[1] += Double.parseDouble(((String)result[3]).replaceAll(",", ""));
				totals[2] += Double.parseDouble(((String)result[4]).replaceAll(",", ""));
				totals[3] += Double.parseDouble(((String)result[5]).replaceAll(",", ""));
				totals[4] += Double.parseDouble(((String)result[6]).replaceAll(",", ""));
				totals[5] += Double.parseDouble(((String)result[7]).replaceAll(",", ""));
				totals[6] += Double.parseDouble(((String)result[8]).replaceAll(",", ""));
				totals[7] += Double.parseDouble(((String)result[9]).replaceAll(",", ""));
				totals[8] += Double.parseDouble(((String)result[10]).replaceAll(",", ""));
				totals[9] += Double.parseDouble(((String)result[11]).replaceAll(",", ""));
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
			buffer.append(Constants.TEXT_QUALIFIER+prevItemType+" Total"+Constants.TEXT_QUALIFIER+",");
			buffer.append(Constants.TEXT_QUALIFIER+Constants.TEXT_QUALIFIER+",");
			buffer.append(Constants.TEXT_QUALIFIER+amtFormat.format(totals[0])+Constants.TEXT_QUALIFIER+",");
			buffer.append(Constants.TEXT_QUALIFIER+valueFormat.format(totals[1])+Constants.TEXT_QUALIFIER+",");
			buffer.append(Constants.TEXT_QUALIFIER+amtFormat.format(totals[2])+Constants.TEXT_QUALIFIER+",");
			buffer.append(Constants.TEXT_QUALIFIER+valueFormat.format(totals[3])+Constants.TEXT_QUALIFIER+",");
			buffer.append(Constants.TEXT_QUALIFIER+amtFormat.format(totals[4])+Constants.TEXT_QUALIFIER+",");
			buffer.append(Constants.TEXT_QUALIFIER+amtFormat.format(totals[5])+Constants.TEXT_QUALIFIER+",");
			buffer.append(Constants.TEXT_QUALIFIER+valueFormat.format(totals[6])+Constants.TEXT_QUALIFIER+",");
			buffer.append(Constants.TEXT_QUALIFIER+valueFormat.format(totals[7])+Constants.TEXT_QUALIFIER+",");
			buffer.append(Constants.TEXT_QUALIFIER+valueFormat.format(totals[8])+Constants.TEXT_QUALIFIER+",");
			buffer.append(Constants.TEXT_QUALIFIER+valueFormat.format(totals[9])+Constants.TEXT_QUALIFIER+",");
			buffer.append("\n");
		}else{
			buffer.append(""+Constants.TEXT_QUALIFIER+"No records found."+Constants.TEXT_QUALIFIER+",");
			buffer.append("\n");
		}
		return buffer;
	}
}