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

import com.cdgtaxi.ibs.report.Constants;
import com.cdgtaxi.ibs.report.exception.FormatNotSupportedException;
import com.cdgtaxi.ibs.util.DateUtil;
import com.cdgtaxi.ibs.util.StringUtil;
import com.elixirtech.ers2.client.ERSClient;
import com.elixirtech.net.NetException;

public class CashlessCollectionStatistics extends ReportWindow implements CSVFormat {
	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(CashlessCollectionStatistics.class);
	public CashlessCollectionStatistics() throws IOException {
		super("Cashless Collection Statistics", "Non Billable");
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
		String creditStart = (String)reportParamsProperties.get("1. Credit Start Date");
		String creditEnd = (String)reportParamsProperties.get("2. Credit End Date");
		String entityNo = (String)reportParamsProperties.get("3. Entity");
		String acquirerNo = (String)reportParamsProperties.get("4. Acquirer");
		StringBuffer buffer = new StringBuffer();
		//Report Header
		buffer.append(Constants.TEXT_QUALIFIER+"Integrated Billing System"+Constants.TEXT_QUALIFIER+",");
		buffer.append("\n");
		buffer.append(Constants.TEXT_QUALIFIER+"Cashless Collection Statistics"+Constants.TEXT_QUALIFIER+",");
		buffer.append("\n");
		buffer.append(Constants.TEXT_QUALIFIER+"Selection Criteria:"+Constants.TEXT_QUALIFIER+",");
		buffer.append("\n");
		buffer.append("\n");
		if((creditStart!=null && creditStart.length()>0) || (creditEnd!=null && creditEnd.length()!=0)){
			buffer.append(Constants.TEXT_QUALIFIER+"Credit Date: ");
			if(creditStart!=null && creditStart.length()>0){
				buffer.append(creditStart);
			}else{
				buffer.append(creditEnd);
			}
			buffer.append(" to ");
			if(creditEnd!=null && creditEnd.length()!=0){
				buffer.append(creditEnd+Constants.TEXT_QUALIFIER+",");
			}else{
				buffer.append(creditStart+Constants.TEXT_QUALIFIER+",");
			}
			buffer.append("\n");
		}
		if(entityNo!=null && entityNo.length()>0){
			String entityName = ((Listbox)this.getFellow("3. Entity")).getSelectedItem().getLabel();
			buffer.append(Constants.TEXT_QUALIFIER+"Entity: "+entityName+Constants.TEXT_QUALIFIER+",");
			buffer.append("\n");
		}
		if(acquirerNo!=null && acquirerNo.length()>0){
			String acquirerName = ((Listbox)this.getFellow("4. Acquirer")).getSelectedItem().getLabel();
			buffer.append(Constants.TEXT_QUALIFIER+"Acquirer: "+acquirerName+Constants.TEXT_QUALIFIER+",");
			buffer.append("\n");
		}
		buffer.append("\n");
		buffer.append("\n");
		buffer.append(Constants.TEXT_QUALIFIER+"Printed By: "+reportParamsProperties.get("printedBy")+Constants.TEXT_QUALIFIER+",");
		buffer.append("\n");
		buffer.append(Constants.TEXT_QUALIFIER+"Report Date: "+DateUtil.convertDateToStr(DateUtil.getCurrentDate(), DateUtil.GLOBAL_DATE_FORMAT)+Constants.TEXT_QUALIFIER+",");
		buffer.append("\n");
		buffer.append("\n");
		List<Object[]> summary = this.businessHelper.getReportBusiness().getCashlessCollectionStatisticsSummary(
				creditStart!=null && creditStart.length()!=0 ? creditStart + " 00:00:00" : creditEnd!=null && creditEnd.length()!=0 ? creditEnd + " 00:00:00" : creditStart,
				creditEnd!=null && creditEnd.length()!=0 ? creditEnd + " 23:59:59" : creditStart!=null && creditStart.length()!=0 ? creditStart + " 23:59:59" : creditEnd,
				entityNo, acquirerNo
		);
		// show the summary first
		//Column Title
		buffer.append(Constants.TEXT_QUALIFIER+"Entity"+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+"Net Received ($)"+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+"Chargeback Amount($)"+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+"Rejected Amount ($)"+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+"MDR ($)"+Constants.TEXT_QUALIFIER);
		buffer.append("\n");
		// data
		if(summary.size()!=0){
			for(int i=0;i<summary.size(); i++){
				Object[] result = summary.get(i);
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
		}else{
			buffer.append(""+Constants.TEXT_QUALIFIER+"No records found."+Constants.TEXT_QUALIFIER+",");
			buffer.append("\n");
		}
		List<Object[]> detailed = this.businessHelper.getReportBusiness().getCashlessCollectionStatisticsDetailed(
				creditStart!=null && creditStart.length()!=0 ? creditStart + " 00:00:00" : creditEnd!=null && creditEnd.length()!=0 ? creditEnd + " 00:00:00" : creditStart,
				creditEnd!=null && creditEnd.length()!=0 ? creditEnd + " 23:59:59" : creditStart!=null && creditStart.length()!=0 ? creditStart + " 23:59:59" : creditEnd,
				entityNo, acquirerNo
		);
		// extracting the data first
		List<String> acquirerNames = new ArrayList<String>();
		Map<String, Map<String, Map<String, String>>> acquirers = new LinkedHashMap<String, Map<String, Map<String, String>>>();
		if(detailed.size()!=0){
			for(Object[] result : detailed){
				String acquirerName = (String)result[0], creditDate = (String)result[1];
				String netReceived = (String)result[2], chargeback = (String)result[3], rejected = (String)result[4], mdr = (String)result[5], mdrPercent = (String)result[6];
				Map<String, Map<String, String>> acquirer = acquirers.get(creditDate);
				if(acquirer==null){
					acquirer = new LinkedHashMap<String, Map<String, String>>();
					acquirers.put(creditDate, acquirer);
				}
				Map<String, String> batch = acquirer.get(acquirerName);
				if(batch==null){
					if(!acquirerNames.contains(acquirerName)){
						acquirerNames.add(acquirerName);
					}
					batch = new LinkedHashMap<String, String>();
					acquirer.put(acquirerName, batch);
				}
				batch.put("net", netReceived);
				batch.put("chargeback", chargeback);
				batch.put("rejected", rejected);
				batch.put("mdr", mdr);
				batch.put("mdrPercent", mdrPercent);
			}
		}
		// now showing the total.
		// Column Title
		buffer.append(Constants.TEXT_QUALIFIER+Constants.TEXT_QUALIFIER+",");
		for(String acquirerName : acquirerNames){
			buffer.append(Constants.TEXT_QUALIFIER+acquirerName+Constants.TEXT_QUALIFIER+",");
			buffer.append(Constants.TEXT_QUALIFIER+Constants.TEXT_QUALIFIER+",");
			buffer.append(Constants.TEXT_QUALIFIER+Constants.TEXT_QUALIFIER+",");
			buffer.append(Constants.TEXT_QUALIFIER+Constants.TEXT_QUALIFIER+",");
			buffer.append(Constants.TEXT_QUALIFIER+Constants.TEXT_QUALIFIER+",");
		}
		buffer.append("\n");
		buffer.append(Constants.TEXT_QUALIFIER+"CR Date"+Constants.TEXT_QUALIFIER+",");
		for(int i=0;i<acquirerNames.size();i++){
			buffer.append(Constants.TEXT_QUALIFIER+"Net Received ($)"+Constants.TEXT_QUALIFIER+",");
			buffer.append(Constants.TEXT_QUALIFIER+"Chargeback Amount($)"+Constants.TEXT_QUALIFIER+",");
			buffer.append(Constants.TEXT_QUALIFIER+"Rejected Amount ($)"+Constants.TEXT_QUALIFIER+",");
			buffer.append(Constants.TEXT_QUALIFIER+"MDR ($)"+Constants.TEXT_QUALIFIER+",");
			buffer.append(Constants.TEXT_QUALIFIER+"MDR (%)"+Constants.TEXT_QUALIFIER+",");
		}
		buffer.append("\n");
		if(summary.size()==0){
			buffer.append(""+Constants.TEXT_QUALIFIER+"No records found."+Constants.TEXT_QUALIFIER+",");
			buffer.append("\n");
			return buffer;
		}
		// data
		// now getting the total for each provider
		Map<String, Map<String, Double>> acquirerTotals = new HashMap<String, Map<String, Double>>();
		for(String acquirerId : acquirers.keySet()){
			Map<String, Map<String, String>> acquirer = acquirers.get(acquirerId);
			Map<String, Double> acquirerTotal = acquirerTotals.get(acquirerId);
			if(acquirerTotal == null){
				acquirerTotal = new HashMap<String, Double>();
			}
			for(String batchDate : acquirer.keySet()){
				Map<String, String> batch = acquirer.get(batchDate);
				for(String batchType : batch.keySet()){
					if(acquirerTotal.get(batchType) == null){
						acquirerTotal.put(batchType, Double.parseDouble(batch.get(batchType).replaceAll(",", "")));
					}else{
						acquirerTotal.put(batchType, acquirerTotal.get(batchType) + Double.parseDouble(batch.get(batchType).replaceAll(",", "")));
					}
				}
			}
		}
		// now displaying the individual.
		for(String date : acquirers.keySet()){
			buffer.append(""+Constants.TEXT_QUALIFIER+date+Constants.TEXT_QUALIFIER+",");
			Map<String, Map<String, String>> acquirer = acquirers.get(date);
			for(String acquirerNameLabel : acquirerNames){
				boolean found = false;
				for(String acquirerName : acquirer.keySet()){
					if(acquirerName.equals(acquirerNameLabel)){
						Map<String, String> batch = acquirer.get(acquirerName);
						buffer.append(""+Constants.TEXT_QUALIFIER+batch.get("net")+Constants.TEXT_QUALIFIER+",");
						buffer.append(""+Constants.TEXT_QUALIFIER+batch.get("chargeback")+Constants.TEXT_QUALIFIER+",");
						buffer.append(""+Constants.TEXT_QUALIFIER+batch.get("rejected")+Constants.TEXT_QUALIFIER+",");
						buffer.append(""+Constants.TEXT_QUALIFIER+batch.get("mdr")+Constants.TEXT_QUALIFIER+",");
						buffer.append(""+Constants.TEXT_QUALIFIER+batch.get("mdrPercent")+Constants.TEXT_QUALIFIER+",");
						found = true;
						break;
					}
				}
				if(!found){
					buffer.append(""+Constants.TEXT_QUALIFIER+"0.00"+Constants.TEXT_QUALIFIER+",");
					buffer.append(""+Constants.TEXT_QUALIFIER+"0.00"+Constants.TEXT_QUALIFIER+",");
					buffer.append(""+Constants.TEXT_QUALIFIER+"0.00"+Constants.TEXT_QUALIFIER+",");
					buffer.append(""+Constants.TEXT_QUALIFIER+"0.00"+Constants.TEXT_QUALIFIER+",");
					buffer.append(""+Constants.TEXT_QUALIFIER+"0.00"+Constants.TEXT_QUALIFIER+",");
				}
				
			}
			buffer.append("\n");
		}
		return buffer;
	}
}