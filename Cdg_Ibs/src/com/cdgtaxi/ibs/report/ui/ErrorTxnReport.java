package com.cdgtaxi.ibs.report.ui;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.httpclient.HttpException;
import org.apache.log4j.Logger;
import org.zkoss.util.media.AMedia;
import org.zkoss.zul.Filedownload;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listitem;

import com.cdgtaxi.ibs.Uri;
import com.cdgtaxi.ibs.common.ConfigurableConstants;
import com.cdgtaxi.ibs.report.Constants;
import com.cdgtaxi.ibs.report.exception.FormatNotSupportedException;
import com.cdgtaxi.ibs.util.ComponentUtil;
import com.cdgtaxi.ibs.util.DateUtil;
import com.cdgtaxi.ibs.util.StringUtil;
import com.elixirtech.ers2.client.ERSClient;
import com.elixirtech.net.NetException;

public class ErrorTxnReport extends ReportWindow implements CSVFormat {
	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(ErrorTxnReport.class);
	public ErrorTxnReport() throws IOException {
		super("Error Transaction Report", "Trips");
	}
	
	public void init(){
		Listbox errorMsgListbox = (Listbox)this.getFellow("05. Error Msg");
		errorMsgListbox.getChildren().clear();
		List<String> errorMsgs = this.businessHelper.getReportBusiness().getAllTxnErrorMsg();
		errorMsgListbox.appendChild(ComponentUtil.createNotRequiredListItem());
		for(String errorMsg : errorMsgs){
			errorMsgListbox.appendChild(new Listitem(errorMsg, errorMsg));
		}
		errorMsgListbox.setSelectedIndex(0);
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
		String txnStart = (String)reportParamsProperties.get("01. Txn Start Date");
		String txnEnd = (String)reportParamsProperties.get("02. Txn End Date");
		String uploadStart = (String)reportParamsProperties.get("03. Upload Start Date");
		String uploadEnd = (String)reportParamsProperties.get("04. Upload End Date");
		String errorMsg = (String)reportParamsProperties.get("05. Error Msg");
		String offline = (String)reportParamsProperties.get("06. Offline");
		String jobNo = (String)reportParamsProperties.get("07. Job No");
		String cardNo = (String)reportParamsProperties.get("08. Card No");
		String nric = (String)reportParamsProperties.get("09. Driver NRIC");
		String taxiNo = (String)reportParamsProperties.get("10. Taxi No");
		String sort = (String)reportParamsProperties.get("11. Sort By");
		StringBuffer buffer = new StringBuffer();
		//Report Header
		buffer.append(Constants.TEXT_QUALIFIER+"Integrated Billing System"+Constants.TEXT_QUALIFIER+",");
		buffer.append("\n");
		buffer.append(Constants.TEXT_QUALIFIER+"Error Transaction Report"+Constants.TEXT_QUALIFIER+",");
		buffer.append("\n");
		buffer.append(Constants.TEXT_QUALIFIER+"Selection Criteria:"+Constants.TEXT_QUALIFIER+",");
		buffer.append("\n");
		buffer.append("\n");
		if((txnStart!=null && txnStart.length()>0) || (txnEnd!=null && txnEnd.length()!=0)){
			buffer.append(Constants.TEXT_QUALIFIER+"Txn Date: ");
			if(txnStart!=null && txnStart.length()>0){
				buffer.append(txnStart);
			}else{
				buffer.append(txnEnd);
			}
			buffer.append(" to ");
			if(txnEnd!=null && txnEnd.length()!=0){
				buffer.append(txnEnd+Constants.TEXT_QUALIFIER+",");
			}else{
				buffer.append(txnStart+Constants.TEXT_QUALIFIER+",");
			}
			buffer.append("\n");
		}
		if((uploadStart!=null && uploadStart.length()>0) || (uploadEnd!=null && uploadEnd.length()!=0)){
			buffer.append(Constants.TEXT_QUALIFIER+"Upload Date: ");
			if(uploadStart!=null && uploadStart.length()>0){
				buffer.append(uploadStart);
			}else{
				buffer.append(uploadEnd);
			}
			buffer.append(" to ");
			if(uploadEnd!=null && uploadEnd.length()!=0){
				buffer.append(uploadEnd+Constants.TEXT_QUALIFIER+",");
			}else{
				buffer.append(uploadStart+Constants.TEXT_QUALIFIER+",");
			}
			buffer.append("\n");
		}
		if(errorMsg!=null && errorMsg.length()>0){
			String errorMsgName = ((Listbox)this.getFellow("05. Error Msg")).getSelectedItem().getLabel();
			buffer.append(Constants.TEXT_QUALIFIER+"Error Msg: "+errorMsgName+Constants.TEXT_QUALIFIER+",");
			buffer.append("\n");
		}else{
			buffer.append(Constants.TEXT_QUALIFIER+"Error Msg: ALL"+Constants.TEXT_QUALIFIER+",");
			buffer.append("\n");
		}
		if(offline!=null && offline.length()>0){
			String offlineName = ((Listbox)this.getFellow("06. Offline")).getSelectedItem().getLabel();
			buffer.append(Constants.TEXT_QUALIFIER+"Offline: "+offlineName+Constants.TEXT_QUALIFIER+",");
			buffer.append("\n");
		}
		if(jobNo!=null && jobNo.length()>0){
			buffer.append(Constants.TEXT_QUALIFIER+"Job No: "+jobNo+Constants.TEXT_QUALIFIER+",");
			buffer.append("\n");
		}
		if(cardNo!=null && cardNo.length()>0){
			buffer.append(Constants.TEXT_QUALIFIER+"Card No: "+cardNo+Constants.TEXT_QUALIFIER+",");
			buffer.append("\n");
		}
		if(nric!=null && nric.length()>0){
			buffer.append(Constants.TEXT_QUALIFIER+"Driver NRIC: "+StringUtil.maskNric(nric)+Constants.TEXT_QUALIFIER+",");
			buffer.append("\n");
		}
		if(taxiNo!=null && taxiNo.length()>0){
			buffer.append(Constants.TEXT_QUALIFIER+"Taxi No: "+taxiNo+Constants.TEXT_QUALIFIER+",");
			buffer.append("\n");
		}
		Map<String, String> mrr = ConfigurableConstants.getMasters("MRR");
		for(String key : mrr.keySet()){
			buffer.append(Constants.TEXT_QUALIFIER+"Max Record: "+mrr.get(key)+Constants.TEXT_QUALIFIER+",");
			buffer.append("\n");
			break;
		}
		String sortLabel = ((Listbox)this.getFellow("11. Sort By")).getSelectedItem().getLabel();
		buffer.append(Constants.TEXT_QUALIFIER+"Sort By: "+sortLabel+Constants.TEXT_QUALIFIER+",");
		buffer.append("\n");
		buffer.append("\n");
		buffer.append(Constants.TEXT_QUALIFIER+"Printed By: "+reportParamsProperties.get("printedBy")+Constants.TEXT_QUALIFIER+",");
		buffer.append("\n");
		buffer.append(Constants.TEXT_QUALIFIER+"Report Date: "+DateUtil.convertDateToStr(DateUtil.getCurrentDate(), DateUtil.GLOBAL_DATE_FORMAT)+Constants.TEXT_QUALIFIER+",");
		buffer.append("\n");
		buffer.append("\n");
		//Column Title
		
		//new
		buffer.append(Constants.TEXT_QUALIFIER+"CARD_NO"+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+"JOB_NO"+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+"VEHICLE_NO"+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+"DRIVER_IC"+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+"ENTITY"+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+"TOTAL_AMOUNT"+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+"TRIP_START"+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+"TRIP_END"+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+"PICKUP_DT"+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+"PICKUP"+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+"DESTINATION"+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+"JOB_TYPE"+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+"BOOKED_DATE_TIME"+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+"BOOKED_VEHICLE_GROUP"+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+"VEH_TYPE"+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+"PAX_NAME"+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+"BOOKING_REFERENCE"+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+"TRIP TYPE"+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+"ACCOUNT_LV1_NAME"+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+"ACCOUNT_LV2_NAME"+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+"ACCOUNT_LV3_NAME"+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+"SURCHARGE_DESCRIPTION"+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+"COMPLIMENTARY"+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+"FLIGHT_DETAILS"+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+"AGENT_NAME"+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+"SALES_DRAFT_NO"+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+"PAYMENT_MODE"+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+"SETL_DATE"+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+"BANK_TID"+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+"MID"+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+"BANK_BATCH_CLOSE_NO"+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+"HOTEL_CHARGE_TO"+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+"CREATE_DATE"+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+"JOB_STATUS"+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+"OFFLINE"+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+"OFFLINE_TXN_DATE"+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+"ERROR_MSG"+Constants.TEXT_QUALIFIER+",");
		
		buffer.append(Constants.TEXT_QUALIFIER+"*DELETE AFTER THIS*"+Constants.TEXT_QUALIFIER+",");

		buffer.append(Constants.TEXT_QUALIFIER+"S/N"+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+"JOB_NO"+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+"ACCOUNT_NO"+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+"DIVSION"+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+"DEPARTMENT"+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+"CARD_NO"+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+"SALES_DRAFT_NO"+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+"PRODUCT_TYPE"+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+"COMPANY_CODE"+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+"TAXI_NO"+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+"DRIVER_NRIC"+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+"TRIP_START_DATE"+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+"TRIP_START_TIME"+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+"TRIP_END_DATE"+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+"TRIP_END_TIME"+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+"FARE_AMOUNT"+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+"ADMIN_FEE_VALUE"+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+"GST_VALUE"+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+"COMPLIMENTARY"+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+"SURCHARGE_REMARKS"+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+"TRIP_CODE"+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+"TRIP_CODE_REASON"+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+"TRIP_TYPE"+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+"JOB_TYPE"+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+"VEHICLE_TYPE"+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+"PICKUP"+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+"DESTINATION"+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+"REMARKS"+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+"UPDATE_FMS?"+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+"REFUND/COLLECT?"+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+"FMS_AMOUNT"+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+"INCENTIVE_AMOUNT"+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+"PROMO_AMOUNT"+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+"CABREWARDS_AMOUNT"+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+"LEVY_AMOUNT"+Constants.TEXT_QUALIFIER+",");

		buffer.append("\n");
		//Data
		List<Object[]> results = this.businessHelper.getReportBusiness().getErrorTxnReport(
				txnStart!=null && txnStart.length()!=0 ? txnStart + " 00:00:00" : txnEnd!=null && txnEnd.length()!=0 ? txnEnd + " 00:00:00" : txnStart,
				txnEnd!=null && txnEnd.length()!=0 ? txnEnd + " 23:59:59" : txnStart!=null && txnStart.length()!=0 ? txnStart + " 23:59:59" : txnEnd,
				uploadStart!=null && uploadStart.length()!=0 ? uploadStart + " 00:00:00" : uploadEnd!=null && uploadEnd.length()!=0 ? uploadEnd + " 00:00:00" : uploadStart,
				uploadEnd!=null && uploadEnd.length()!=0 ? uploadEnd + " 23:59:59" : uploadStart!=null && uploadStart.length()!=0 ? uploadStart + " 23:59:59" : uploadEnd,
				errorMsg, offline, jobNo, cardNo, nric, taxiNo, sort
		);
		logger.info("size = " + results.size());
		if(results.size()!=0){
			for(int i=0;i<results.size(); i++){
				Object[] result = results.get(i);
				int no = i + 1;
				for(int j=0;j<result.length;j++){//for(Object data : result){
					Object data = result[j];

					if(j==38) {
						buffer.append(""+Constants.TEXT_QUALIFIER+no+Constants.TEXT_QUALIFIER+",");
					}
					
					if(data!=null){
						if(j==0 || j==1){
							buffer.append(""+Constants.TEXT_QUALIFIER+'\''+data+Constants.TEXT_QUALIFIER+",");
						}else if(j==3) {
							buffer.append(""+Constants.TEXT_QUALIFIER+StringUtil.maskNric(data.toString())+Constants.TEXT_QUALIFIER+",");
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