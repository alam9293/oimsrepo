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

public class CashlessTxnByAmtRange extends ReportWindow implements CSVFormat {
	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(CashlessTxnByAmtRange.class);
	public CashlessTxnByAmtRange() throws IOException {
		super("Cashless Transaction By Amount Range", "Non Billable");
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
		String tripStart = (String)reportParamsProperties.get("1. Trip Start Date");
		String tripEnd = (String)reportParamsProperties.get("2. Trip End Date");
		String paymentTypeNo = (String)reportParamsProperties.get("3. Payment Type");
		String entityNo = (String)reportParamsProperties.get("4. Entity");
		StringBuffer buffer = new StringBuffer();
		//Report Header
		buffer.append(Constants.TEXT_QUALIFIER+"Integrated Billing System"+Constants.TEXT_QUALIFIER+",");
		buffer.append("\n");
		buffer.append(Constants.TEXT_QUALIFIER+"Cashless Transaction By Amount Range"+Constants.TEXT_QUALIFIER+",");
		buffer.append("\n");
		buffer.append(Constants.TEXT_QUALIFIER+"Selection Criteria:"+Constants.TEXT_QUALIFIER+",");
		buffer.append("\n");
		buffer.append("\n");
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
			String paymentTypeName = ((Listbox)this.getFellow("3. Payment Type")).getSelectedItem().getLabel();
			buffer.append(Constants.TEXT_QUALIFIER+"Payment Type: "+paymentTypeName+Constants.TEXT_QUALIFIER+",");
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
		buffer.append("\n");
		List<Object[]> results = this.businessHelper.getReportBusiness().getCashlessTxnByAmtRange(
				tripStart!=null && tripStart.length()!=0 ? tripStart + " 00:00:00" : tripEnd!=null && tripEnd.length()!=0 ? tripEnd + " 00:00:00" : tripStart,
				tripEnd!=null && tripEnd.length()!=0 ? tripEnd + " 23:59:59" : tripStart!=null && tripStart.length()!=0 ? tripStart + " 23:59:59" : tripEnd,
				paymentTypeNo,
				entityNo
		);
		// extracting the data first
		List<String> providerNames = new ArrayList<String>();
		Map<String, Map<String, Map<Integer, Integer>>> paymentTypes = new LinkedHashMap<String, Map<String, Map<Integer, Integer>>>();
		if(results.size()!=0){
			for(Object[] result : results){
				String masterCode = (String)result[0], paymentTypeName = (String)result[1];
				Integer range = Integer.parseInt((String)result[2]), count = Integer.parseInt((String)result[3]);
				Map<String, Map<Integer, Integer>> paymentType = paymentTypes.get(paymentTypeName);
				if(paymentType==null){
					paymentType = new LinkedHashMap<String, Map<Integer, Integer>>();
					paymentTypes.put(paymentTypeName, paymentType);
				}
				Map<Integer, Integer> provider = paymentType.get(masterCode);
				if(provider==null){
					if(!providerNames.contains(masterCode)){
						providerNames.add(masterCode);
					}
					provider = new LinkedHashMap<Integer, Integer>();
					paymentType.put(masterCode, provider);
				}
				if(provider.get(range)!=null){
					provider.put(range, provider.get(range) + count);
				}else{
					provider.put(range, count);
				}
			}
		}
		// now showing the total.
		// Column Title
		buffer.append(((Listbox)this.getFellow("3. Payment Type")).getSelectedItem().getLabel() + "\n");
		buffer.append(Constants.TEXT_QUALIFIER+Constants.TEXT_QUALIFIER+"GRAND TOTAL,");
		buffer.append(Constants.TEXT_QUALIFIER+Constants.TEXT_QUALIFIER+",");
		for(String providerName : providerNames){
			buffer.append(Constants.TEXT_QUALIFIER+providerName+Constants.TEXT_QUALIFIER+",");
			buffer.append(Constants.TEXT_QUALIFIER+Constants.TEXT_QUALIFIER+",");
		}
		buffer.append("\n");
		buffer.append(Constants.TEXT_QUALIFIER+"S/N"+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+"Amount Range"+Constants.TEXT_QUALIFIER+",");
		for(int i=0;i<providerNames.size();i++){
			buffer.append(Constants.TEXT_QUALIFIER+"No. Of Txns"+Constants.TEXT_QUALIFIER+",");
			buffer.append(Constants.TEXT_QUALIFIER+"%"+Constants.TEXT_QUALIFIER+",");
		}
		buffer.append(Constants.TEXT_QUALIFIER+"Total Txns"+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+"%"+Constants.TEXT_QUALIFIER+",");
		buffer.append("\n");
		if(results.size()==0){
			buffer.append(""+Constants.TEXT_QUALIFIER+"No records found."+Constants.TEXT_QUALIFIER+",");
			buffer.append("\n");
			return buffer;
		}
		// data
		int serial = 1;
		// now getting the total for each provider
		Map<String, Integer> providerTotals = new HashMap<String, Integer>();
		for(String paymentTypeId : paymentTypes.keySet()){
			Map<String, Map<Integer, Integer>> paymentType = paymentTypes.get(paymentTypeId);
			for(String providerId : paymentType.keySet()){
				Map<Integer, Integer> provider = paymentType.get(providerId);
				for(Integer range : provider.keySet()){
					if(providerTotals.get(providerId)==null){
						providerTotals.put(providerId, provider.get(range));
					}else{
						providerTotals.put(providerId, providerTotals.get(providerId) + provider.get(range));
					}
				}
			}
		}
		// now displaying the individual.
		DecimalFormat df = new DecimalFormat(StringUtil.GLOBAL_DECIMAL_FORMAT);
		Map<String, Integer> grandTotals = new HashMap<String, Integer>();
		for(int i=1;i<9;i++){
			buffer.append(""+Constants.TEXT_QUALIFIER+serial+++Constants.TEXT_QUALIFIER+",");
			switch(i){
				case 1:buffer.append(""+Constants.TEXT_QUALIFIER+"<=3.00"+Constants.TEXT_QUALIFIER+",");break;
				case 2:buffer.append(""+Constants.TEXT_QUALIFIER+"3.01-5.00"+Constants.TEXT_QUALIFIER+",");break;
				case 3:buffer.append(""+Constants.TEXT_QUALIFIER+"5.01-10.00"+Constants.TEXT_QUALIFIER+",");break;
				case 4:buffer.append(""+Constants.TEXT_QUALIFIER+"10.01-20.00"+Constants.TEXT_QUALIFIER+",");break;
				case 5:buffer.append(""+Constants.TEXT_QUALIFIER+"20.01-30.00"+Constants.TEXT_QUALIFIER+",");break;
				case 6:buffer.append(""+Constants.TEXT_QUALIFIER+"30.01-40.00"+Constants.TEXT_QUALIFIER+",");break;
				case 7:buffer.append(""+Constants.TEXT_QUALIFIER+"40.01-50.00"+Constants.TEXT_QUALIFIER+",");break;
				default:buffer.append(""+Constants.TEXT_QUALIFIER+">50.00"+Constants.TEXT_QUALIFIER+",");break;
			}
			int rangeGrandTotal = 0;
			int overallGrandTotal = 0;
			for(String providerName : providerNames){
				Integer rangeTotal = 0;
				for(String paymentTypeId : paymentTypes.keySet()){
					Map<String, Map<Integer, Integer>> paymentType = paymentTypes.get(paymentTypeId);
					for(String providerId : paymentType.keySet()){
						if(providerId.equals(providerName)){
							Map<Integer, Integer> provider = paymentType.get(providerId);
							for(Integer range : provider.keySet()){
								if(range==i){
									rangeTotal += provider.get(range);
								}
							}
						}
					}
				}
				rangeGrandTotal += rangeTotal;
				overallGrandTotal += providerTotals.get(providerName);
				if(grandTotals.get(providerName)!=null){
					grandTotals.put(providerName, grandTotals.get(providerName) + rangeTotal);
				}else{
					grandTotals.put(providerName, rangeTotal);
				}
				buffer.append(""+Constants.TEXT_QUALIFIER+rangeTotal+Constants.TEXT_QUALIFIER+",");
				buffer.append(""+Constants.TEXT_QUALIFIER+(df.format(rangeTotal*100.00/providerTotals.get(providerName)))+Constants.TEXT_QUALIFIER+",");
			}
			buffer.append(""+Constants.TEXT_QUALIFIER+rangeGrandTotal+Constants.TEXT_QUALIFIER+",");
			buffer.append(""+Constants.TEXT_QUALIFIER+(df.format(rangeGrandTotal*100.00/overallGrandTotal))+Constants.TEXT_QUALIFIER+",");
			buffer.append("\n");
		}
		buffer.append(""+Constants.TEXT_QUALIFIER+Constants.TEXT_QUALIFIER+",");
		buffer.append(""+Constants.TEXT_QUALIFIER+"TOTAL"+Constants.TEXT_QUALIFIER+",");
		int overallGrandTotal = 0;
		for(String providerName : providerNames){
			overallGrandTotal += grandTotals.get(providerName);
			buffer.append(""+Constants.TEXT_QUALIFIER+grandTotals.get(providerName)+Constants.TEXT_QUALIFIER+",");
			buffer.append(""+Constants.TEXT_QUALIFIER+"100.00"+Constants.TEXT_QUALIFIER+",");
		}
		buffer.append(""+Constants.TEXT_QUALIFIER+overallGrandTotal+Constants.TEXT_QUALIFIER+",");
		buffer.append(""+Constants.TEXT_QUALIFIER+"100.00"+Constants.TEXT_QUALIFIER+",");
		buffer.append("\n");
		for(String paymentTypeId : paymentTypes.keySet()){
			buffer.append("\n");
			buffer.append(Constants.TEXT_QUALIFIER+Constants.TEXT_QUALIFIER+",");
			buffer.append(Constants.TEXT_QUALIFIER+Constants.TEXT_QUALIFIER+",");
			for(String providerName : providerNames){
				buffer.append(Constants.TEXT_QUALIFIER+providerName+Constants.TEXT_QUALIFIER+",");
				buffer.append(Constants.TEXT_QUALIFIER+Constants.TEXT_QUALIFIER+",");
			}
			buffer.append("\n");
			buffer.append(Constants.TEXT_QUALIFIER+paymentTypeId+Constants.TEXT_QUALIFIER+",");
			buffer.append(Constants.TEXT_QUALIFIER+"Amount Range"+Constants.TEXT_QUALIFIER+",");
			for(int i=0;i<providerNames.size();i++){
				buffer.append(Constants.TEXT_QUALIFIER+"No. Of Txns"+Constants.TEXT_QUALIFIER+",");
				buffer.append(Constants.TEXT_QUALIFIER+"%"+Constants.TEXT_QUALIFIER+",");
			}
			buffer.append(Constants.TEXT_QUALIFIER+"Total Txns"+Constants.TEXT_QUALIFIER+",");
			buffer.append(Constants.TEXT_QUALIFIER+"%"+Constants.TEXT_QUALIFIER+",");
			buffer.append("\n");
			grandTotals.clear();
			int paymentGrandTotal = 0;
			for(String providerName : providerNames){
				Integer rangeTotal = 0;
				Map<String, Map<Integer, Integer>> paymentType = paymentTypes.get(paymentTypeId);
				for(String providerId : paymentType.keySet()){
					if(providerId.equals(providerName)){
						Map<Integer, Integer> provider = paymentType.get(providerId);
						for(Integer range : provider.keySet()){
							rangeTotal += provider.get(range);
						}
					}
				}
				paymentGrandTotal += rangeTotal;
				if(grandTotals.get(providerName)!=null){
					grandTotals.put(providerName, grandTotals.get(providerName) + rangeTotal);
				}else{
					grandTotals.put(providerName, rangeTotal);
				}
			}
			
			serial = 1;
			for(int i=1;i<9;i++){
				buffer.append(""+Constants.TEXT_QUALIFIER+serial+++Constants.TEXT_QUALIFIER+",");
				switch(i){
					case 1:buffer.append(""+Constants.TEXT_QUALIFIER+"<=3.00"+Constants.TEXT_QUALIFIER+",");break;
					case 2:buffer.append(""+Constants.TEXT_QUALIFIER+"3.01-5.00"+Constants.TEXT_QUALIFIER+",");break;
					case 3:buffer.append(""+Constants.TEXT_QUALIFIER+"5.01-10.00"+Constants.TEXT_QUALIFIER+",");break;
					case 4:buffer.append(""+Constants.TEXT_QUALIFIER+"10.01-20.00"+Constants.TEXT_QUALIFIER+",");break;
					case 5:buffer.append(""+Constants.TEXT_QUALIFIER+"20.01-30.00"+Constants.TEXT_QUALIFIER+",");break;
					case 6:buffer.append(""+Constants.TEXT_QUALIFIER+"30.01-40.00"+Constants.TEXT_QUALIFIER+",");break;
					case 7:buffer.append(""+Constants.TEXT_QUALIFIER+"40.01-50.00"+Constants.TEXT_QUALIFIER+",");break;
					default:buffer.append(""+Constants.TEXT_QUALIFIER+">50.00"+Constants.TEXT_QUALIFIER+",");break;
				}
				int rangeGrandTotal = 0;
				for(String providerName : providerNames){
					Integer rangeTotal = 0;
					Map<String, Map<Integer, Integer>> paymentType = paymentTypes.get(paymentTypeId);
					for(String providerId : paymentType.keySet()){
						if(providerId.equals(providerName)){
							Map<Integer, Integer> provider = paymentType.get(providerId);
							for(Integer range : provider.keySet()){
								if(range==i){
									rangeTotal += provider.get(range);
								}
							}
						}
					}
					rangeGrandTotal += rangeTotal;
					buffer.append(""+Constants.TEXT_QUALIFIER+rangeTotal+Constants.TEXT_QUALIFIER+",");
					buffer.append(""+Constants.TEXT_QUALIFIER+(df.format(rangeTotal*100.00/(grandTotals.get(providerName)!=null && grandTotals.get(providerName)!=0 ? grandTotals.get(providerName) : 1)))+Constants.TEXT_QUALIFIER+",");
				}
				buffer.append(""+Constants.TEXT_QUALIFIER+rangeGrandTotal+Constants.TEXT_QUALIFIER+",");
				buffer.append(""+Constants.TEXT_QUALIFIER+(df.format(rangeGrandTotal*100.00/paymentGrandTotal))+Constants.TEXT_QUALIFIER+",");
				buffer.append("\n");
			}
			buffer.append(""+Constants.TEXT_QUALIFIER+Constants.TEXT_QUALIFIER+",");
			buffer.append(""+Constants.TEXT_QUALIFIER+"TOTAL"+Constants.TEXT_QUALIFIER+",");
			int paymentTypeOverallGrandTotal = 0;
			for(String providerName : providerNames){
				paymentTypeOverallGrandTotal += grandTotals.get(providerName);
				buffer.append(""+Constants.TEXT_QUALIFIER+grandTotals.get(providerName)+Constants.TEXT_QUALIFIER+",");
				buffer.append(""+Constants.TEXT_QUALIFIER+"100.00"+Constants.TEXT_QUALIFIER+",");
			}
			buffer.append(""+Constants.TEXT_QUALIFIER+paymentTypeOverallGrandTotal+Constants.TEXT_QUALIFIER+",");
			buffer.append(""+Constants.TEXT_QUALIFIER+"100.00"+Constants.TEXT_QUALIFIER+",");
			buffer.append("\n");buffer.append("\n");buffer.append("\n");
		}
		return buffer;
	}
}