package com.cdgtaxi.ibs.report.ui;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.httpclient.HttpException;
import org.apache.log4j.Logger;
import org.zkoss.util.media.AMedia;
import org.zkoss.zul.Filedownload;
import org.zkoss.zul.Listbox;

import com.cdgtaxi.ibs.common.ConfigurableConstants;
import com.cdgtaxi.ibs.common.NonConfigurableConstants;
import com.cdgtaxi.ibs.report.Constants;
import com.cdgtaxi.ibs.report.exception.FormatNotSupportedException;
import com.cdgtaxi.ibs.util.DateUtil;
import com.cdgtaxi.ibs.util.StringUtil;
import com.elixirtech.ers2.client.ERSClient;
import com.elixirtech.net.NetException;

public class ASOfflineApprovalWindow extends ReportWindow implements CSVFormat {
	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(ASOfflineApprovalWindow.class);
	public ASOfflineApprovalWindow() throws IOException {
		super("AS Offline Approval", "Trips");
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
	public StringBuffer generateCSVData(Properties reportParamsProperties) throws FormatNotSupportedException {
		String txnStart = (String)reportParamsProperties.get("1. Txn Start Date");
		String txnEnd = (String)reportParamsProperties.get("2. Txn End Date");
		String offlineTxnStart = (String)reportParamsProperties.get("3. Offline Txn Start Date");
		String offlineTxnEnd = (String)reportParamsProperties.get("4. Offline Txn End Date");
		String messageType = (String)reportParamsProperties.get("5. Message Type");
		String error = (String)reportParamsProperties.get("6. Error");
		String cardNo = (String)reportParamsProperties.get("7. Card No");
		String sortBy = (String)reportParamsProperties.get("8. Sort By");
		StringBuffer buffer = new StringBuffer();
		
		//Report Header
		buffer.append(Constants.TEXT_QUALIFIER+"Integrated Billing System"+Constants.TEXT_QUALIFIER+",");
		buffer.append("\n");
		buffer.append(Constants.TEXT_QUALIFIER+"AS Offline Approval Report"+Constants.TEXT_QUALIFIER+",");
		buffer.append("\n");
		buffer.append("\n");
		buffer.append(Constants.TEXT_QUALIFIER+"Selection Criteria:"+Constants.TEXT_QUALIFIER+",");
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
		if((offlineTxnStart!=null && offlineTxnStart.length()>0) || (offlineTxnEnd!=null && offlineTxnEnd.length()!=0)){
			buffer.append(Constants.TEXT_QUALIFIER+"Offline Txn Date: ");
			if(offlineTxnStart!=null && offlineTxnStart.length()>0){
				buffer.append(offlineTxnStart);
			}else{
				buffer.append(offlineTxnEnd);
			}
			buffer.append(" to ");
			if(offlineTxnEnd!=null && offlineTxnEnd.length()!=0){
				buffer.append(offlineTxnEnd+Constants.TEXT_QUALIFIER+",");
			}else{
				buffer.append(offlineTxnStart+Constants.TEXT_QUALIFIER+",");
			}
			buffer.append("\n");
		}
		if(messageType!=null && messageType.length()>0){
			String msgTypeName = ((Listbox)this.getFellow("5. Message Type")).getSelectedItem().getLabel();
			buffer.append(Constants.TEXT_QUALIFIER+"Message Type: "+msgTypeName+Constants.TEXT_QUALIFIER+",");
			buffer.append("\n");
		}
		if(error!=null && error.length()>0){
			String errorName = ((Listbox)this.getFellow("6. Error")).getSelectedItem().getLabel();
			buffer.append(Constants.TEXT_QUALIFIER+"Error: "+errorName+Constants.TEXT_QUALIFIER+",");
			buffer.append("\n");
		}
		if(cardNo!=null && cardNo.length()>0){
			buffer.append(Constants.TEXT_QUALIFIER+"Card No: "+cardNo+Constants.TEXT_QUALIFIER+",");
			buffer.append("\n");
		}
		String sortLabel = ((Listbox)this.getFellow("8. Sort By")).getSelectedItem().getLabel();
		buffer.append(Constants.TEXT_QUALIFIER+"Sort By: "+sortLabel+Constants.TEXT_QUALIFIER+",");
		buffer.append("\n");
		Map<String, String> mrr = ConfigurableConstants.getMasters("MRR");
		for(String key : mrr.keySet()){
			buffer.append(Constants.TEXT_QUALIFIER+"Max Record: "+mrr.get(key)+Constants.TEXT_QUALIFIER+",");
			buffer.append("\n");
			break;
		}		
		buffer.append("\n");
		buffer.append(Constants.TEXT_QUALIFIER+"Printed By: "+reportParamsProperties.get("printedBy")+Constants.TEXT_QUALIFIER+",");
		buffer.append("\n");
		buffer.append(Constants.TEXT_QUALIFIER+"Report Date: "+DateUtil.convertDateToStr(DateUtil.getCurrentDate(), DateUtil.GLOBAL_DATE_FORMAT)+Constants.TEXT_QUALIFIER+",");
		buffer.append("\n");
		buffer.append("\n");
		
		//Column Title
		buffer.append(Constants.TEXT_QUALIFIER+"Txn Date"+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+"Offline Txn Date"+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+"Message Type"+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+"Merchant ID"+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+"Terminal ID"+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+"System Trace No"+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+"Retreival Ref No"+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+"Card No"+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+"Product Type"+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+"Expiry Date (YYMM)"+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+"Fixed Value ($)"+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+"Txn Amount ($)"+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+"Response Date"+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+"Response Code"+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+"Approval Code"+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+"Error Code"+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+"Error Description"+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+"Txn Status"+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+"Batch No"+Constants.TEXT_QUALIFIER);
		buffer.append("\n");
		//Data
		List<Object[]> results = this.businessHelper.getReportBusiness().getASOfflineApproval(
				txnStart!=null && txnStart.length()!=0 ? txnStart + " 00:00:00" : txnEnd!=null && txnEnd.length()!=0 ? txnEnd + " 00:00:00" : txnStart,
				txnEnd!=null && txnEnd.length()!=0 ? txnEnd + " 23:59:59" : txnStart!=null && txnStart.length()!=0 ? txnStart + " 23:59:59" : txnEnd,
				offlineTxnStart!=null && offlineTxnStart.length()!=0 ? offlineTxnStart + " 00:00:00" : offlineTxnEnd!=null && offlineTxnEnd.length()!=0 ? offlineTxnEnd + " 00:00:00" : offlineTxnStart,
				offlineTxnEnd!=null && offlineTxnEnd.length()!=0 ? offlineTxnEnd + " 23:59:59" : offlineTxnStart!=null && offlineTxnStart.length()!=0 ? offlineTxnStart + " 23:59:59" : offlineTxnEnd,
				messageType, error, cardNo, sortBy
		);
		logger.info("size = " + results.size());
		if(results.size()!=0){
			for(int i=0;i<results.size(); i++){
				Object[] result = results.get(i);
				for(int j=0;j<result.length;j++){//for(Object data : result){
					Object data = result[j];
					if(data!=null){
						if (j==2){						
							buffer.append(""+Constants.TEXT_QUALIFIER+NonConfigurableConstants.AS_OFFLINE_MSG_TYPE.get(data)+Constants.TEXT_QUALIFIER+",");
						}else if (j==5){
							buffer.append(""+Constants.TEXT_QUALIFIER+'\''+data+Constants.TEXT_QUALIFIER+",");
						}else if (j==6){
							buffer.append(""+Constants.TEXT_QUALIFIER+'\''+data+Constants.TEXT_QUALIFIER+",");
						}else if (j==7){
							buffer.append(""+Constants.TEXT_QUALIFIER+'\''+data+Constants.TEXT_QUALIFIER+",");
						}else if (j==9){
							buffer.append(""+Constants.TEXT_QUALIFIER+'\''+data+Constants.TEXT_QUALIFIER+",");
						}else if (j==10){
							buffer.append(""+Constants.TEXT_QUALIFIER+(".".equals(data) ? 0 : Double.parseDouble(data.toString()))+Constants.TEXT_QUALIFIER+",");
						}else if (j==11){
							buffer.append(""+Constants.TEXT_QUALIFIER+(".".equals(data) ? 0 : Double.parseDouble(data.toString()))+Constants.TEXT_QUALIFIER+",");
						}else if (j==13){
							buffer.append(""+Constants.TEXT_QUALIFIER+'\''+data+Constants.TEXT_QUALIFIER+",");
						}else if (j==14){
							buffer.append(""+Constants.TEXT_QUALIFIER+'\''+data+Constants.TEXT_QUALIFIER+",");
						}else if(j==17){
							buffer.append(""+Constants.TEXT_QUALIFIER+NonConfigurableConstants.AS_TXN_STATUS.get(data)+Constants.TEXT_QUALIFIER+",");
						}else if (j==18){
							buffer.append(""+Constants.TEXT_QUALIFIER+'\''+data+Constants.TEXT_QUALIFIER+",");
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