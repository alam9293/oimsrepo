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
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zul.Decimalbox;
import org.zkoss.zul.Filedownload;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listitem;

import com.cdgtaxi.ibs.Uri;
import com.cdgtaxi.ibs.common.NonConfigurableConstants;
import com.cdgtaxi.ibs.report.Constants;
import com.cdgtaxi.ibs.report.exception.FormatNotSupportedException;
import com.cdgtaxi.ibs.util.DateUtil;
import com.cdgtaxi.ibs.web.constraint.RequiredConstraint;
import com.elixirtech.ers2.client.ERSClient;
import com.elixirtech.net.NetException;

public class CreditCardPromoDetails extends ReportWindow implements CSVFormat {
	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(CreditCardPromoDetails.class);
	public CreditCardPromoDetails() throws IOException {
		super("Credit Card Promo Details", "Non Billable");
	}
	public void init(){//additional init
		Decimalbox promo = (Decimalbox)this.getFellow("08. Promo (%)");
		promo.setMaxlength(6);
		promo.setFormat("0.00");
		promo.setConstraint(new RequiredConstraint(){
			public void validate(Component arg0, Object arg1) throws WrongValueException {
				super.validate(arg0, arg1);
				if(arg1!=null && arg1 instanceof BigDecimal){
					if(((BigDecimal)arg1).compareTo(new BigDecimal(100))>0){
						throw new WrongValueException(arg0, "* Must be lesser than 100");
					}else if(((BigDecimal)arg1).doubleValue()<0){
						throw new WrongValueException(arg0, "* Must be greater or equals to 0");
					}
				}
			}
		});
		Listbox paymentTypes = (Listbox)this.getFellow("05. Payment Type");
		for(Object obj : paymentTypes.getChildren()){
			Listitem paymentType = (Listitem)obj;
			if(paymentType.getValue().equals("N")){
				paymentTypes.removeChild(paymentType);
				break;
			}
		}
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
		String batchStart = (String)reportParamsProperties.get("01. Batch Start Date");
		String batchEnd = (String)reportParamsProperties.get("02. Batch End Date");
		String tripStart = (String)reportParamsProperties.get("03. Trip Start Date");
		String tripEnd = (String)reportParamsProperties.get("04. Trip End Date");
		String paymentTypeNo = (String)reportParamsProperties.get("05. Payment Type");
		String binStart = (String)reportParamsProperties.get("06. Card Bin Range Start");
		String binEnd = (String)reportParamsProperties.get("07. Card Bin Range End");
		String promo = (String)reportParamsProperties.get("08. Promo (%)");
		String txnStatus = (String)reportParamsProperties.get("09. Txn Status");
		String batchStatus = (String)reportParamsProperties.get("10. Batch Status");
		String sort = (String)reportParamsProperties.get("11. Sort By");
		StringBuffer buffer = new StringBuffer();
		//Report Header
		buffer.append(Constants.TEXT_QUALIFIER+"Integrated Billing System"+Constants.TEXT_QUALIFIER+",");
		buffer.append("\n");
		buffer.append(Constants.TEXT_QUALIFIER+"Credit Card Promo Details"+Constants.TEXT_QUALIFIER+",");
		buffer.append("\n");
		buffer.append(Constants.TEXT_QUALIFIER+"Selection Criteria:"+Constants.TEXT_QUALIFIER+",");
		buffer.append("\n");
		buffer.append("\n");
		if((batchStart!=null && batchStart.length()>0) || (batchEnd!=null && batchEnd.length()!=0)){
			buffer.append(Constants.TEXT_QUALIFIER+"Batch Date: ");
			if(batchStart!=null && batchStart.length()>0){
				buffer.append(batchStart);
			}else{
				buffer.append(batchEnd);
			}
			buffer.append(" to ");
			if(batchEnd!=null && batchEnd.length()!=0){
				buffer.append(batchEnd+Constants.TEXT_QUALIFIER+",");
			}else{
				buffer.append(batchStart+Constants.TEXT_QUALIFIER+",");
			}
			buffer.append("\n");
		}
		if((tripStart!=null && tripStart.length()>0) || (tripEnd!=null && tripEnd.length()!=0)){
			buffer.append(Constants.TEXT_QUALIFIER+"Trip Date: ");
			if(tripStart!=null && tripStart.length()>0){
				buffer.append(tripStart);
			}else{
				buffer.append(tripEnd);
			}
			buffer.append(" to ");
			if(tripEnd!=null && tripEnd.length()!=0){
				buffer.append(tripEnd+Constants.TEXT_QUALIFIER+",");
			}else{
				buffer.append(tripStart+Constants.TEXT_QUALIFIER+",");
			}
			buffer.append("\n");
		}
		if(paymentTypeNo!=null && paymentTypeNo.length()>0){
			String paymentTypeName = ((Listbox)this.getFellow("05. Payment Type")).getSelectedItem().getLabel();
			buffer.append(Constants.TEXT_QUALIFIER+"Payment Type: "+paymentTypeName+Constants.TEXT_QUALIFIER+",");
			buffer.append("\n");
		}
		if((binStart!=null && binStart.length()>0) || (binEnd!=null && binEnd.length()!=0)){
			buffer.append(Constants.TEXT_QUALIFIER+"BIN Range: ");
			if(binStart!=null && binStart.length()>0){
				buffer.append(binStart);
			}else{
				buffer.append(binEnd);
			}
			buffer.append(" to ");
			if(binEnd!=null && binEnd.length()!=0){
				buffer.append(binEnd+Constants.TEXT_QUALIFIER+",");
			}else{
				buffer.append(binStart+Constants.TEXT_QUALIFIER+",");
			}
			buffer.append("\n");
		}
		if(promo!=null && promo.length()>0){
			buffer.append(Constants.TEXT_QUALIFIER+"Promo (%): "+promo+Constants.TEXT_QUALIFIER+",");
			buffer.append("\n");
		}
		if(txnStatus!=null && txnStatus.length()>0){
			String txnStatusName = ((Listbox)this.getFellow("09. Txn Status")).getSelectedItem().getLabel();
			buffer.append(Constants.TEXT_QUALIFIER+"Transaction Status: "+txnStatusName+Constants.TEXT_QUALIFIER+",");
			buffer.append("\n");
		}
		if(batchStatus!=null && batchStatus.length()>0){
			String batchStatusName = ((Listbox)this.getFellow("10. Batch Status")).getSelectedItem().getLabel();
			buffer.append(Constants.TEXT_QUALIFIER+"Batch Status: "+batchStatusName+Constants.TEXT_QUALIFIER+",");
			buffer.append("\n");
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
		buffer.append(Constants.TEXT_QUALIFIER+"S/N"+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+"Payment Type"+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+"Credit Card No"+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+"Trip Date"+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+"Txn Amt($)"+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+"Admin ($)"+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+"GST ($)"+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+"Total ($)"+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+"Promo ("+promo+"%) Amt"+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+"Txn Status"+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+"Credit Date"+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+"Batch No"+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+"Batch Date"+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+"Batch Status"+Constants.TEXT_QUALIFIER+",");
		buffer.append("\n");
		//Data
		List<Object[]> results = this.businessHelper.getReportBusiness().getCreditCardPromoDetails(
				batchStart!=null && batchStart.length()!=0 ? batchStart + " 00:00:00" : batchEnd!=null && batchEnd.length()!=0 ? batchEnd + " 00:00:00" : batchStart,
				batchEnd!=null && batchEnd.length()!=0 ? batchEnd + " 23:59:59" : batchStart!=null && batchStart.length()!=0 ? batchStart + " 23:59:59" : batchEnd,
				tripStart!=null && tripStart.length()!=0 ? tripStart + " 00:00:00" : tripEnd!=null && tripEnd.length()!=0 ? tripEnd + " 00:00:00" : tripStart,
				tripEnd!=null && tripEnd.length()!=0 ? tripEnd + " 23:59:59" : tripStart!=null && tripStart.length()!=0 ? tripStart + " 23:59:59" : tripEnd,
				paymentTypeNo,
				binStart!=null && binStart.length()!=0 ? binStart : binEnd!=null && binEnd.length()!=0 ? binEnd : binStart,
				binEnd!=null && binEnd.length()!=0 ? binEnd : binStart!=null && binStart.length()!=0 ? binStart : binEnd,
				promo, txnStatus, batchStatus, sort
		);
		logger.info("size = " + results.size());
		if(results.size()!=0){
			double totalFare = 0, totalAdmin = 0, totalGST = 0, totalTotal = 0;
			for(int i=0;i<results.size(); i++){
				buffer.append(""+Constants.TEXT_QUALIFIER+(i+1)+Constants.TEXT_QUALIFIER+",");
				Object[] result = results.get(i);
				for(int j=0;j<result.length;j++){//for(Object data : result){
					Object data = result[j];
					if(data!=null){
						if(j==1){
							buffer.append(""+Constants.TEXT_QUALIFIER+'\''+data+Constants.TEXT_QUALIFIER+",");
						}else if(j==8){
							buffer.append(""+Constants.TEXT_QUALIFIER+NonConfigurableConstants.NON_BILLABLE_TXN_STATUS.get(data)+Constants.TEXT_QUALIFIER+",");
						}else if(j==12){
							buffer.append(""+Constants.TEXT_QUALIFIER+NonConfigurableConstants.NON_BILLABLE_BATCH_STATUS.get(data)+Constants.TEXT_QUALIFIER+",");
						}else{
							buffer.append(""+Constants.TEXT_QUALIFIER+data+Constants.TEXT_QUALIFIER+",");
						}
						if(j==3){
							totalFare += Double.parseDouble(data.toString().replaceAll(",", ""));
						}else if(j==4){
							totalAdmin += Double.parseDouble(data.toString().replaceAll(",", ""));
						}else if(j==5){
							totalGST += Double.parseDouble(data.toString().replaceAll(",", ""));
						}else if(j==6){
							totalTotal += Double.parseDouble(data.toString().replaceAll(",", ""));
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