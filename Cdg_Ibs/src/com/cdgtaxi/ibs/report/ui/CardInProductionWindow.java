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
import org.zkoss.zul.Listitem;

import com.cdgtaxi.ibs.Uri;
import com.cdgtaxi.ibs.common.NonConfigurableConstants;
import com.cdgtaxi.ibs.report.Constants;
import com.cdgtaxi.ibs.report.exception.FormatNotSupportedException;
import com.cdgtaxi.ibs.util.DateUtil;
import com.elixirtech.ers2.client.ERSClient;
import com.elixirtech.net.NetException;

public class CardInProductionWindow extends ReportWindow implements CSVFormat{
	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(CreditBalanceWindow.class);
	public CardInProductionWindow() throws IOException {
		super("Card In Production", "Product");
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
		String productType = (String)reportParamsProperties.get("01. Product Type");
		String issueStart = (String)reportParamsProperties.get("02. Issuance Start Date");
		String issueEnd = (String)reportParamsProperties.get("03. Issuance End Date");
		String renewStart = (String)reportParamsProperties.get("04. Renewal Start Date");
		String renewEnd = (String)reportParamsProperties.get("05. Renewal End Date");
		String replaceStart = (String)reportParamsProperties.get("06. Replacement Start Date");
		String replaceEnd = (String)reportParamsProperties.get("07. Replacement End Date");
		String cardStart = (String)reportParamsProperties.get("08. Card No Start");
		String cardEnd = (String)reportParamsProperties.get("09. Card No End");
		String cardStatus = (String)reportParamsProperties.get("10. Card Status");
		String sortBy = (String)reportParamsProperties.get("11. Sort By");
		StringBuffer buffer = new StringBuffer();
		//Report Header
		buffer.append(Constants.TEXT_QUALIFIER+"Integrated Billing System"+Constants.TEXT_QUALIFIER+",");
		buffer.append("\n");
		buffer.append(Constants.TEXT_QUALIFIER+"Credit Balance Report"+Constants.TEXT_QUALIFIER+",");
		buffer.append("\n");
		buffer.append(Constants.TEXT_QUALIFIER+"Selection Criteria:"+Constants.TEXT_QUALIFIER+",");
		buffer.append("\n");
		buffer.append("\n");
		if(productType!=null && productType.length()>0){
			String productTypeName = ((Listbox)this.getFellow("01. Product Type")).getSelectedItem().getLabel();
			buffer.append(Constants.TEXT_QUALIFIER+"Product Type: "+productTypeName+Constants.TEXT_QUALIFIER+",");
			buffer.append("\n");
		}
		if((issueStart!=null && issueStart.length()>0) || (issueEnd!=null && issueEnd.length()!=0)){
			buffer.append(Constants.TEXT_QUALIFIER+"Issuance Date: ");
			if(issueStart!=null && issueStart.length()>0){
				buffer.append(issueStart);
			}else{
				buffer.append(issueEnd);
			}
			buffer.append(" to ");
			if(issueEnd!=null && issueEnd.length()!=0){
				buffer.append(issueEnd+Constants.TEXT_QUALIFIER+",");
			}else{
				buffer.append(issueStart+Constants.TEXT_QUALIFIER+",");
			}
			buffer.append("\n");
		}
		if((renewStart!=null && renewStart.length()>0) || (renewEnd!=null && renewEnd.length()!=0)){
			buffer.append(Constants.TEXT_QUALIFIER+"Renewal Date: ");
			if(renewStart!=null && renewStart.length()>0){
				buffer.append(renewStart);
			}else{
				buffer.append(renewEnd);
			}
			buffer.append(" to ");
			if(renewEnd!=null && renewEnd.length()!=0){
				buffer.append(renewEnd+Constants.TEXT_QUALIFIER+",");
			}else{
				buffer.append(renewStart+Constants.TEXT_QUALIFIER+",");
			}
			buffer.append("\n");
		}
		if((replaceStart!=null && replaceStart.length()>0) || (replaceEnd!=null && replaceEnd.length()!=0)){
			buffer.append(Constants.TEXT_QUALIFIER+"Replacement Date: ");
			if(replaceStart!=null && replaceStart.length()>0){
				buffer.append(replaceStart);
			}else{
				buffer.append(replaceEnd);
			}
			buffer.append(" to ");
			if(replaceEnd!=null && replaceEnd.length()!=0){
				buffer.append(replaceEnd+Constants.TEXT_QUALIFIER+",");
			}else{
				buffer.append(replaceStart+Constants.TEXT_QUALIFIER+",");
			}
			buffer.append("\n");
		}
		if((cardStart!=null && cardStart.length()>0) || (cardEnd!=null && cardEnd.length()!=0)){
			buffer.append(Constants.TEXT_QUALIFIER+"Card No: ");
			if(cardStart!=null && cardStart.length()>0){
				buffer.append(cardStart);
			}else{
				buffer.append(cardEnd);
			}
			buffer.append(" to ");
			if(cardEnd!=null && cardEnd.length()!=0){
				buffer.append(cardEnd+Constants.TEXT_QUALIFIER+",");
			}else{
				buffer.append(cardStart+Constants.TEXT_QUALIFIER+",");
			}
			buffer.append("\n");
		}
		if(cardStatus!=null && cardStatus.length()>0){
			String cardStatusName = ((Listbox)this.getFellow("10. Card Status")).getSelectedItem().getLabel();
			buffer.append(Constants.TEXT_QUALIFIER+"Card Status< "+cardStatusName+Constants.TEXT_QUALIFIER+",");
			buffer.append("\n");
		}
		String sort = ((Listbox)this.getFellow("11. Sort By")).getSelectedItem().getLabel();
		buffer.append(Constants.TEXT_QUALIFIER+"Sort By: "+sort+Constants.TEXT_QUALIFIER+",");
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
		buffer.append(Constants.TEXT_QUALIFIER+"Div Id"+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+"Div Name"+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+"Dept Id"+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+"Dept Name"+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+"Card No"+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+"Ship Contact Person"+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+"Card Holder Name"+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+"Name On Card"+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+"Card Credit Limit"+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+"Card Value"+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+"Expiry Date (MM/YYYY)"+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+"Current Prdt Status"+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+"Card Reason"+Constants.TEXT_QUALIFIER+",");
		buffer.append("\n");
		//Data
		List<Object[]> results = this.businessHelper.getReportBusiness().getCardInProduction(
				productType,
				issueStart!=null && issueStart.length()!=0 ? issueStart + " 00:00:00" : issueEnd!=null && issueEnd.length()!=0 ? issueEnd + " 00:00:00" : issueStart,
				issueEnd!=null && issueEnd.length()!=0 ? issueEnd + " 23:59:59" : issueStart!=null && issueStart.length()!=0 ? issueStart + " 23:59:59" : issueEnd,
				renewStart!=null && renewStart.length()!=0 ? renewStart + " 00:00:00" : renewEnd!=null && renewEnd.length()!=0 ? renewEnd + " 00:00:00" : renewStart,
				renewEnd!=null && renewEnd.length()!=0 ? renewEnd + " 23:59:59" : renewStart!=null && renewStart.length()!=0 ? renewStart + " 23:59:59" : renewEnd,
				replaceStart!=null && replaceStart.length()!=0 ? replaceStart + " 00:00:00" : replaceEnd!=null && replaceEnd.length()!=0 ? replaceEnd + " 00:00:00" : replaceStart,
				replaceEnd!=null && replaceEnd.length()!=0 ? replaceEnd + " 23:59:59" : replaceStart!=null && replaceStart.length()!=0 ? replaceStart + " 23:59:59" : replaceEnd,
				cardStart!=null && cardStart.length()!=0 ? cardStart : cardEnd!=null && cardEnd.length()!=0 ? cardEnd : cardStart,
				cardEnd!=null && cardEnd.length()!=0 ? cardEnd : cardStart!=null && cardStart.length()!=0 ? cardStart : cardEnd,
				cardStatus,
				sortBy
		);
		logger.info("size = " + results.size());
		if(results.size()!=0){
			for(int i=0;i<results.size(); i++){
				buffer.append(""+Constants.TEXT_QUALIFIER+(i+1)+Constants.TEXT_QUALIFIER+",");
				Object[] result = results.get(i);
				for(int j=0;j<result.length;j++){//for(Object data : result){
					Object data = result[j];
					if(data!=null){
						if(j==6){
							buffer.append(""+Constants.TEXT_QUALIFIER+'\''+data+Constants.TEXT_QUALIFIER+",");
						}else if(j==13){
							buffer.append(""+Constants.TEXT_QUALIFIER+NonConfigurableConstants.PRODUCT_STATUS.get(data)+Constants.TEXT_QUALIFIER+",");
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
	public void removeNewCardStatus(){
		Listbox statuses = (Listbox)this.getFellow("10. Card Status");
		Listitem newItem = null;
		for(Object status : statuses.getChildren()){
			Listitem statusItem = (Listitem)status;
			if(statusItem.getValue().equals("N")){
				newItem = statusItem;
				break;
			}
		}
		if(newItem!=null){
			statuses.removeChild(newItem);
		}
	}
}