package com.cdgtaxi.ibs.report.ui;

import java.io.IOException;
import java.util.List;
import java.util.Properties;

import org.apache.commons.httpclient.HttpException;
import org.apache.log4j.Logger;
import org.zkoss.util.media.AMedia;
import org.zkoss.zul.Filedownload;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Messagebox;

import com.cdgtaxi.ibs.common.NonConfigurableConstants;
import com.cdgtaxi.ibs.report.Constants;
import com.cdgtaxi.ibs.report.exception.FormatNotSupportedException;
import com.cdgtaxi.ibs.util.DateUtil;
import com.elixirtech.ers2.client.ERSClient;
import com.elixirtech.net.NetException;

public class RefundDepositReport extends ReportWindow implements CSVFormat {
	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(RefundDepositReport.class);
	public RefundDepositReport() throws IOException {
		super("Refund Deposit Report", "Account");
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
			if(dataInCSV==null){
				return;
			}
			AMedia media = new AMedia(report.replaceAll(" ", "_")+"."+extension, extension, format, dataInCSV.toString());
			Filedownload.save(media);
		}else{
			throw new FormatNotSupportedException();
		}
	}
	public StringBuffer generateCSVData(Properties reportParamsProperties) throws FormatNotSupportedException {
		String acctNo = (String)reportParamsProperties.get("1a. Account No");
		String acctName = (String)reportParamsProperties.get("1b. Account Name");
		String acctStatus = (String)reportParamsProperties.get("2. Account Status");
		String terminateStart = (String)reportParamsProperties.get("3. Terminated/Closed Start Date");
		String terminateEnd = (String)reportParamsProperties.get("4. Terminated/Closed End Date");
		String refund = (String)reportParamsProperties.get("5. Refund");
		String entityNo = (String)reportParamsProperties.get("6. Entity");
		if(
				(acctNo==null || acctNo.length()==0) &&
				(acctName==null || acctName.length()==0) &&
				(acctStatus==null || acctStatus.length()==0) &&
				(terminateStart==null || terminateStart.length()==0) &&
				(terminateEnd==null || terminateEnd.length()==0) &&
				(refund==null || refund.length()==0)
		){
			try {
				Messagebox.show("Either one of the following must be selected. [1a. Account No, 1b. Account Name, 2. Account Status, 3. Terminated/Closed Start Date, 4. Terminated/Closed End Date, 5. Refund]", "Report", Messagebox.OK, Messagebox.ERROR);
				return null;
			} catch (InterruptedException e) {
				logger.error(e);
			}
		}
		StringBuffer buffer = new StringBuffer();
		//Report Header
		buffer.append(Constants.TEXT_QUALIFIER+"Integrated Billing System"+Constants.TEXT_QUALIFIER+",");
		buffer.append("\n");
		buffer.append(Constants.TEXT_QUALIFIER+"Refund Deposit"+Constants.TEXT_QUALIFIER+",");
		buffer.append("\n");
		buffer.append(Constants.TEXT_QUALIFIER+"Selection Criteria:"+Constants.TEXT_QUALIFIER+",");
		buffer.append("\n");
		buffer.append("\n");
		if(acctNo!=null && acctNo.length()>0){
			buffer.append(Constants.TEXT_QUALIFIER+"Account No: "+acctNo+Constants.TEXT_QUALIFIER+",");
			buffer.append("\n");
		}
		if(acctName!=null && acctName.length()>0){
			buffer.append(Constants.TEXT_QUALIFIER+"Account Name: "+acctName+Constants.TEXT_QUALIFIER+",");
			buffer.append("\n");
		}
		if(acctStatus!=null && acctStatus.length()>0){
			String acctStat = ((Listbox)this.getFellow("2. Account Status")).getSelectedItem().getLabel();
			buffer.append(Constants.TEXT_QUALIFIER+"Account Status: "+acctStat+Constants.TEXT_QUALIFIER+",");
			buffer.append("\n");
		}
		if((terminateStart!=null && terminateStart.length()>0) || (terminateEnd!=null && terminateEnd.length()!=0)){
			buffer.append(Constants.TEXT_QUALIFIER+"Terminated/Closed Date: ");
			if(terminateStart!=null && terminateStart.length()>0){
				buffer.append(terminateStart);
			}else{
				buffer.append(terminateEnd);
			}
			buffer.append(" to ");
			if(terminateEnd!=null && terminateEnd.length()!=0){
				buffer.append(terminateEnd+Constants.TEXT_QUALIFIER+",");
			}else{
				buffer.append(terminateStart+Constants.TEXT_QUALIFIER+",");
			}
			buffer.append("\n");
		}
		if(refund!=null && refund.length()>0){
			String refundName = ((Listbox)this.getFellow("5. Refund")).getSelectedItem().getLabel();
			buffer.append(Constants.TEXT_QUALIFIER+"Refund: "+refundName+Constants.TEXT_QUALIFIER+",");
			buffer.append("\n");
		}
		
		if(entityNo!=null && entityNo.length()>0) {
			String entityName = ((Listbox)this.getFellow("6. Entity")).getSelectedItem().getLabel();
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
		buffer.append(Constants.TEXT_QUALIFIER+"S/N"+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+"Account No"+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+"Account Name"+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+"Deposit Amt ($)"+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+"Invoice No"+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+"Invoice Date"+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+"Receipt No"+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+"Receipt Date"+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+"Terminated Date"+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+"Closed Date"+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+"Account Status"+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+"Refunded"+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+"Offset Outstanding"+Constants.TEXT_QUALIFIER+",");
		buffer.append("\n");
		//Data
		List<Object[]> results = this.businessHelper.getReportBusiness().getRefundDepositReport(
				"%" + acctNo + "%",
				"%" + acctName + "%",
				acctStatus,
				terminateStart!=null && terminateStart.length()!=0 ? terminateStart + " 00:00:00" : terminateEnd!=null && terminateEnd.length()!=0 ? terminateEnd + " 00:00:00" : terminateStart,
				terminateEnd!=null && terminateEnd.length()!=0 ? terminateEnd + " 23:59:59" : terminateStart!=null && terminateStart.length()!=0 ? terminateStart + " 23:59:59" : terminateEnd,
				refund,
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
						if(j==9){
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
		}else{
			buffer.append(""+Constants.TEXT_QUALIFIER+"No records found."+Constants.TEXT_QUALIFIER+",");
			buffer.append("\n");
		}
		return buffer;
	}
}