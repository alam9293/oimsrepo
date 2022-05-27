package com.cdgtaxi.ibs.report.ui;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

import org.apache.commons.httpclient.HttpException;
import org.apache.log4j.Logger;
import org.zkoss.util.media.AMedia;
import org.zkoss.zul.Filedownload;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listitem;

import com.cdgtaxi.ibs.report.Constants;
import com.cdgtaxi.ibs.report.exception.FormatNotSupportedException;
import com.cdgtaxi.ibs.util.DateUtil;
import com.cdgtaxi.ibs.util.StringUtil;
import com.elixirtech.ers2.client.ERSClient;
import com.elixirtech.net.NetException;

public class RevenueByProductCardType extends ReportWindow implements CSVFormat {
	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(RevenueByProductCardType.class);
	public RevenueByProductCardType() throws IOException {
		super("Revenue By Product Card Type", "Non Billable");
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
			logger.info("RevenueReport 1. Starting CSV Generation...");
			StringBuffer dataInCSV = this.generateCSVData(reportParamsProperties);
			AMedia media = new AMedia(report.replaceAll(" ", "_")+"."+extension, extension, format, dataInCSV.toString());
			logger.info("RevenueReport 9. Downloading CSV Generation...");
			
			logger.info("File Name : "+media.getName());
			Filedownload.save(media);
		}else{
			throw new FormatNotSupportedException();
		}
	}
	public void addNoneListitem(){
		Listbox productTypeListbox = (Listbox)this.getFellow("5. Product Type");
		productTypeListbox.appendChild(new Listitem("NONE IN HOUSE PRODUCTS", "AAA"));
		Listbox paymentListbox = (Listbox)this.getFellow("6. Payment Type");
		paymentListbox.appendChild(new Listitem("NONE", "AAAAAA"));
	}
	public StringBuffer generateCSVData(Properties reportParamsProperties) throws FormatNotSupportedException {
		String revenueStart = (String)reportParamsProperties.get("1. Revenue Start Date");
		String revenueEnd = (String)reportParamsProperties.get("2. Revenue End Date");
		String entityNo = (String)reportParamsProperties.get("3. Entity");
		String providerNo = (String)reportParamsProperties.get("4. Company Code");
		String productTypeId = (String)reportParamsProperties.get("5. Product Type");
		String paymentTypeId = (String)reportParamsProperties.get("6. Payment Type");
		StringBuffer buffer = new StringBuffer();
		
		System.out.println("revenueStart :"+revenueStart);
		System.out.println("revenueEnd :"+revenueEnd);
		System.out.println("entityNo :"+entityNo);
		System.out.println("providerNo :"+providerNo);
		System.out.println("productTypeId :"+productTypeId);
		System.out.println("paymentTypeId :"+paymentTypeId);
		
		//Report Header
		buffer.append(Constants.TEXT_QUALIFIER+"Integrated Billing System"+Constants.TEXT_QUALIFIER+",");
		buffer.append("\n");
		buffer.append(Constants.TEXT_QUALIFIER+"Revenue Report By Product and Card Type"+Constants.TEXT_QUALIFIER+",");
		buffer.append("\n");
		buffer.append(Constants.TEXT_QUALIFIER+"Selection Criteria:"+Constants.TEXT_QUALIFIER+",");
		buffer.append("\n");
		buffer.append("\n");
		if((revenueStart!=null && revenueStart.length()>0) || (revenueEnd!=null && revenueEnd.length()!=0)){
			buffer.append(Constants.TEXT_QUALIFIER+"Revenue Date: ");
			if(revenueStart!=null && revenueStart.length()>0){
				buffer.append(revenueStart);
			}else{
				buffer.append(revenueEnd);
			}
			buffer.append(" to ");
			if(revenueEnd!=null && revenueEnd.length()!=0){
				buffer.append(revenueEnd+Constants.TEXT_QUALIFIER+",");
			}else{
				buffer.append(revenueStart+Constants.TEXT_QUALIFIER+",");
			}
			buffer.append("\n");
		}
		if(entityNo!=null && entityNo.length()>0){
			String entityName = ((Listbox)this.getFellow("3. Entity")).getSelectedItem().getLabel();
			buffer.append(Constants.TEXT_QUALIFIER+"Entity: "+entityName+Constants.TEXT_QUALIFIER+",");
			buffer.append("\n");
		}
		if(providerNo!=null && providerNo.length()>0){
			String providerName = ((Listbox)this.getFellow("4. Company Code")).getSelectedItem().getLabel();
			buffer.append(Constants.TEXT_QUALIFIER+"Company Code: "+providerName+Constants.TEXT_QUALIFIER+",");
			buffer.append("\n");
		}
		if(productTypeId!=null && productTypeId.length()>0){
			String productTypeName = ((Listbox)this.getFellow("5. Product Type")).getSelectedItem().getLabel();
			buffer.append(Constants.TEXT_QUALIFIER+"Product Type: "+productTypeName+Constants.TEXT_QUALIFIER+",");
			buffer.append("\n");
		}
		if(paymentTypeId!=null && paymentTypeId.length()>0){
			String paymentTypeName = ((Listbox)this.getFellow("6. Payment Type")).getSelectedItem().getLabel();
			buffer.append(Constants.TEXT_QUALIFIER+"Payment Type: "+paymentTypeName+Constants.TEXT_QUALIFIER+",");
			buffer.append("\n");
		}
		buffer.append("\n");
		buffer.append("\n");
		buffer.append(Constants.TEXT_QUALIFIER+"Printed By: "+reportParamsProperties.get("printedBy")+Constants.TEXT_QUALIFIER+",");
		buffer.append("\n");
		buffer.append(Constants.TEXT_QUALIFIER+"Report Date: "+DateUtil.convertDateToStr(DateUtil.getCurrentDate(), DateUtil.GLOBAL_DATE_FORMAT)+Constants.TEXT_QUALIFIER+",");
		buffer.append("\n");
		buffer.append("\n");
		
		logger.info("RevenueReport 2a. Buffer End, Starting SQL 1");
		//Data
		List<Object[]> results1 = this.businessHelper.getReportBusiness().getRevenueByProductCardType(
				revenueStart!=null && revenueStart.length()!=0 ? revenueStart + " 00:00:00" : revenueEnd!=null && revenueEnd.length()!=0 ? revenueEnd + " 00:00:00" : revenueStart,
				revenueEnd!=null && revenueEnd.length()!=0 ? revenueEnd + " 23:59:59" : revenueStart!=null && revenueStart.length()!=0 ? revenueStart + " 23:59:59" : revenueEnd,
				entityNo, providerNo, productTypeId, paymentTypeId
		);

		logger.info("RevenueReport 2a. SQL END 2a");
		logger.info("RevenueReport 2b. Buffer End, Starting SQL 2");
		List<Object[]> results2 = this.businessHelper.getReportBusiness().getRevenueByProductCardType2(
				revenueStart!=null && revenueStart.length()!=0 ? revenueStart + " 00:00:00" : revenueEnd!=null && revenueEnd.length()!=0 ? revenueEnd + " 00:00:00" : revenueStart,
				revenueEnd!=null && revenueEnd.length()!=0 ? revenueEnd + " 23:59:59" : revenueStart!=null && revenueStart.length()!=0 ? revenueStart + " 23:59:59" : revenueEnd,
				entityNo, providerNo, productTypeId, paymentTypeId
		);

		logger.info("RevenueReport 2b. SQL END 2b");
		logger.info("results1 size > "+results1.size());
		logger.info("results2 size > "+results2.size());
		List<Object[]> results = new ArrayList<Object[]>();
		if(results1 != null)
			results.addAll(results1);
		if(results2 != null)
			results.addAll(results2);
		
		logger.info("SQL size = " + results.size());
		logger.info("RevenueReport 3. SQL Complete, Preparing Excel Parsing Data..");
		// now parsing the data first
		if(results.size()!=0){
			Set<String> providerHeaders = new TreeSet<String>(new Comparator<String>(){
				public int compare(String o1, String o2) {
					return o1.compareTo(o2);
				}
			});
			Map<String, Map<String, Map<String, Map<String, Map<String, Double>>>>> revenue = new TreeMap<String, Map<String, Map<String, Map<String, Map<String, Double>>>>>(new Comparator<String>(){
				public int compare(String o1, String o2) {
					return o1.compareTo(o2);
				}
			});
			Map<String, Map<String, Double>> ccCCATotal = new HashMap<String, Map<String, Double>>();
			Map<String, Map<String, Double>> CCATotal = new HashMap<String, Map<String, Double>>();
			Map<String, Map<String, Double>> nonCCATotal = new HashMap<String, Map<String, Double>>();
			Map<String, Map<String, Double>> grandTotal = new HashMap<String, Map<String, Double>>();
				grandTotal = new TreeMap<String, Map<String, Double>>(new Comparator<String>() {
					public int compare(String o1, String o2) {
						return o1.compareTo(o2);
					}
					});
				
				logger.info("RevenueReport 4. Starting for loop thru each results size to count total amount.");
			for(int i=0;i<results.size(); i++){
				logger.info("4a inside for > "+i);
				Object[] result = results.get(i);
				String type = (String)result[0],
					name = (String)result[1],
					entity = ((String)result[2]).equals("CCA") ||  ((String)result[2]).equals("CCA (Total)") ? "CCA" : "Non-CCA",
					provider = (String)result[3];
				Double txnCount = Double.parseDouble(((String)result[4]).replaceAll(",", "")),
					txnAmt = Double.parseDouble(((String)result[5]).replaceAll(",", "")),
					admin = Double.parseDouble(((String)result[6]).replaceAll(",", "")),
					gst = Double.parseDouble(((String)result[7]).replaceAll(",", "")),
					premium = Double.parseDouble(((String)result[8]).replaceAll(",", "")),
					premiumGst = Double.parseDouble(((String)result[9]).replaceAll(",", "")),
					total = Double.parseDouble(((String)result[10]).replaceAll(",", ""));
				logger.info("type = " + type + ", name = " + name + ", entity = " + entity + ", provider = " + provider);
				logger.info("txnCount = " + txnCount + ", txnAmt = " + txnAmt + ", admin = " + admin + ", adminGst = " + gst + ", premium = " + premium + ", premiumGst = "+ premiumGst +", total = " + total);
				// adding into header
				providerHeaders.add(provider);
				Map<String, Map<String, Map<String, Map<String, Double>>>> entityData = revenue.get(entity);
				if(entityData == null){
					entityData = new TreeMap<String, Map<String, Map<String, Map<String, Double>>>>(new Comparator<String>(){
						public int compare(String o1, String o2) {
							return o2.compareTo(o1);
						}
					});
					revenue.put(entity, entityData);
				}
				Map<String, Map<String, Map<String, Double>>> typeData = entityData.get(type);
				if(typeData == null){
					typeData = new TreeMap<String, Map<String, Map<String, Double>>>(new Comparator<String>(){
						public int compare(String o1, String o2) {
							return o1.compareTo(o2);
						}
					});
					entityData.put(type, typeData);
				}
				Map<String, Map<String, Double>> nameData = typeData.get(name);
				if(nameData == null){
					nameData = new TreeMap<String, Map<String, Double>>(new Comparator<String>(){
						public int compare(String o1, String o2) {
							return o1.compareTo(o2);
						}
					});
					typeData.put(name, nameData);
				}
				Map<String, Double> providersData = nameData.get(provider);
				if(providersData == null){
					providersData = new HashMap<String, Double>();
					nameData.put(provider, providersData);
				}
				providersData.put("txnCount", providersData.get("txnCount")==null ? txnCount : providersData.get("txnCount") + txnCount);
				providersData.put("txnAmt", providersData.get("txnAmt")==null ? txnAmt : providersData.get("txnAmt") + txnAmt);
				providersData.put("admin", providersData.get("admin")==null ? admin : providersData.get("admin") + admin);
				providersData.put("gst", providersData.get("gst")==null ? gst : providersData.get("gst") + gst);
				providersData.put("premium", providersData.get("premium")==null ? premium : providersData.get("premium") + premium);
				providersData.put("premiumGst", providersData.get("premiumGst")==null ? premiumGst : providersData.get("premiumGst") + premiumGst);
				providersData.put("total", providersData.get("total")==null ? total : providersData.get("total") + total);
				// now adding total
				if(entity.equals("CCA")){
					if(type.equals("CC")){
						Map<String, Double> providerData = ccCCATotal.get(provider);
						if(providerData == null){
							providerData = new HashMap<String, Double>();
							ccCCATotal.put(provider, providerData);
						}
						providerData.put("txnCount", providerData.get("txnCount")==null ? txnCount : txnCount + providerData.get("txnCount"));
						providerData.put("txnAmt", providerData.get("txnAmt")==null ? txnAmt : txnAmt + providerData.get("txnAmt"));
						providerData.put("admin", providerData.get("admin")==null ? admin : admin + providerData.get("admin"));
						providerData.put("gst", providerData.get("gst")==null ? gst : gst + providerData.get("gst"));
						providerData.put("premium", providerData.get("premium")==null ? premium : premium + providerData.get("premium"));
						providerData.put("premiumGst", providerData.get("premiumGst")==null ? premiumGst : premiumGst + providerData.get("premiumGst"));
						providerData.put("total", providerData.get("total")==null ? total : total + providerData.get("total"));
					}
					Map<String, Double> providerData = CCATotal.get(provider);
					if(providerData == null){
						providerData = new HashMap<String, Double>();
						CCATotal.put(provider, providerData);
					}
					providerData.put("txnCount", providerData.get("txnCount")==null ? txnCount : txnCount + providerData.get("txnCount"));
					providerData.put("txnAmt", providerData.get("txnAmt")==null ? txnAmt : txnAmt + providerData.get("txnAmt"));
					providerData.put("admin", providerData.get("admin")==null ? admin : admin + providerData.get("admin"));
					providerData.put("gst", providerData.get("gst")==null ? gst : gst + providerData.get("gst"));
					providerData.put("premium", providerData.get("premium")==null ? premium : premium + providerData.get("premium"));
					providerData.put("premiumGst", providerData.get("premiumGst")==null ? premiumGst : premiumGst + providerData.get("premiumGst"));
					providerData.put("total", providerData.get("total")==null ? total : total + providerData.get("total"));
				}else{
					Map<String, Double> providerData = nonCCATotal.get(provider);
					if(providerData == null){
						providerData = new HashMap<String, Double>();
						nonCCATotal.put(provider, providerData);
					}
					providerData.put("txnCount", providerData.get("txnCount")==null ? txnCount : txnCount + providerData.get("txnCount"));
					providerData.put("txnAmt", providerData.get("txnAmt")==null ? txnAmt : txnAmt + providerData.get("txnAmt"));
					providerData.put("admin", providerData.get("admin")==null ? admin : admin + providerData.get("admin"));
					providerData.put("gst", providerData.get("gst")==null ? gst : gst + providerData.get("gst"));
					providerData.put("premium", providerData.get("premium")==null ? premium : premium + providerData.get("premium"));
					providerData.put("premiumGst", providerData.get("premiumGst")==null ? premiumGst : premiumGst + providerData.get("premiumGst"));
					providerData.put("total", providerData.get("total")==null ? total : total + providerData.get("total"));
				}
				Map<String, Double> providerData = grandTotal.get(provider);
				if(providerData == null){
					providerData = new HashMap<String, Double>();
					grandTotal.put(provider, providerData);
				}
				providerData.put("txnCount", providerData.get("txnCount")==null ? txnCount : txnCount + providerData.get("txnCount"));
				providerData.put("txnAmt", providerData.get("txnAmt")==null ? txnAmt : txnAmt + providerData.get("txnAmt"));
				providerData.put("admin", providerData.get("admin")==null ? admin : admin + providerData.get("admin"));
				providerData.put("gst", providerData.get("gst")==null ? gst : gst + providerData.get("gst"));
				providerData.put("premium", providerData.get("premium")==null ? premium : premium + providerData.get("premium"));
				providerData.put("premiumGst", providerData.get("premiumGst")==null ? premiumGst : premiumGst + providerData.get("premiumGst"));
				providerData.put("total", providerData.get("total")==null ? total : total + providerData.get("total"));
			}
			
			logger.info("RevenueReport 5. Total Complete, Generating Header");
			// now displaying
			// displaying header first
			buffer.append(Constants.TEXT_QUALIFIER+"Sales Product"+Constants.TEXT_QUALIFIER+",");
			for(String providerHeader : providerHeaders){
				buffer.append(Constants.TEXT_QUALIFIER+providerHeader+Constants.TEXT_QUALIFIER+","+Constants.TEXT_QUALIFIER+Constants.TEXT_QUALIFIER+","+Constants.TEXT_QUALIFIER+Constants.TEXT_QUALIFIER+","+Constants.TEXT_QUALIFIER+Constants.TEXT_QUALIFIER+","+Constants.TEXT_QUALIFIER+Constants.TEXT_QUALIFIER+","+Constants.TEXT_QUALIFIER+Constants.TEXT_QUALIFIER+","+Constants.TEXT_QUALIFIER+Constants.TEXT_QUALIFIER+",");
			}
			buffer.append(Constants.TEXT_QUALIFIER+"GRAND TOTAL"+Constants.TEXT_QUALIFIER);
			buffer.append("\n");
			buffer.append(Constants.TEXT_QUALIFIER+Constants.TEXT_QUALIFIER+",");
			for(String providerHeader : providerHeaders){
				buffer.append(Constants.TEXT_QUALIFIER+"No. of Trips"+Constants.TEXT_QUALIFIER+",");
				buffer.append(Constants.TEXT_QUALIFIER+"Txn Amt($)"+Constants.TEXT_QUALIFIER+",");
				buffer.append(Constants.TEXT_QUALIFIER+"Admin($)"+Constants.TEXT_QUALIFIER+",");
				buffer.append(Constants.TEXT_QUALIFIER+"Admin GST($)"+Constants.TEXT_QUALIFIER+",");
				buffer.append(Constants.TEXT_QUALIFIER+"Insurance Premium($)"+Constants.TEXT_QUALIFIER+",");
				buffer.append(Constants.TEXT_QUALIFIER+"Insurance Premium GST($)"+Constants.TEXT_QUALIFIER+",");
				buffer.append(Constants.TEXT_QUALIFIER+"Total($)"+Constants.TEXT_QUALIFIER+",");
			}
			buffer.append(Constants.TEXT_QUALIFIER+"No. of Trips"+Constants.TEXT_QUALIFIER+",");
			buffer.append(Constants.TEXT_QUALIFIER+"Txn Amt($)"+Constants.TEXT_QUALIFIER+",");
			buffer.append(Constants.TEXT_QUALIFIER+"Admin($)"+Constants.TEXT_QUALIFIER+",");
			buffer.append(Constants.TEXT_QUALIFIER+"Admin GST($)"+Constants.TEXT_QUALIFIER+",");
			buffer.append(Constants.TEXT_QUALIFIER+"Insurance Premium($)"+Constants.TEXT_QUALIFIER+",");
			buffer.append(Constants.TEXT_QUALIFIER+"Insurance Premium GST($)"+Constants.TEXT_QUALIFIER+",");
			buffer.append(Constants.TEXT_QUALIFIER+"Total($)"+Constants.TEXT_QUALIFIER);
			buffer.append("\n");
			// displaying data
			DecimalFormat ddf = new DecimalFormat(StringUtil.GLOBAL_DECIMAL_FORMAT);
			DecimalFormat idf = new DecimalFormat(StringUtil.GLOBAL_INTEGER_FORMAT);
			
			logger.info("RevenueReport 6. Putting values .. size : "+revenue.keySet().size());
			int sixA = 0;
			for(String entity : revenue.keySet()){
				
				sixA++;
				logger.info("RevenueReport 6a for each count > "+sixA);
				
				Map<String, Map<String, Map<String, Map<String, Double>>>> entityData = revenue.get(entity);
				buffer.append(Constants.TEXT_QUALIFIER+entity+" Products"+Constants.TEXT_QUALIFIER);
				buffer.append("\n");
				if(!entityData.keySet().contains("NCC") && entity.equals("CCA")){
					buffer.append(Constants.TEXT_QUALIFIER+"No records found"+Constants.TEXT_QUALIFIER);
					buffer.append("\n");
				}
				buffer.append("\n");
				for(String type : entityData.keySet()){
					Map<String, Map<String, Map<String, Double>>> typeData = entityData.get(type);
					if(type.equals("CC") && entity.equals("CCA")){
						buffer.append(Constants.TEXT_QUALIFIER+"CREDIT CARD:"+Constants.TEXT_QUALIFIER);
						buffer.append("\n");
					}
					for(String name : typeData.keySet()){
						buffer.append(Constants.TEXT_QUALIFIER+name+Constants.TEXT_QUALIFIER+",");
						Map<String, Map<String, Double>> providerData = typeData.get(name);
						Map<String, Double> providerTotal = new HashMap<String, Double>();
						for(String providerName : providerHeaders){
							boolean found = false;
							for(String provider : providerData.keySet()){
								if(providerName.equals(provider)){
									Map<String, Double> data = providerData.get(provider);
									buffer.append(Constants.TEXT_QUALIFIER+idf.format(data.get("txnCount"))+Constants.TEXT_QUALIFIER+",");
									buffer.append(Constants.TEXT_QUALIFIER+ddf.format(data.get("txnAmt"))+Constants.TEXT_QUALIFIER+",");
									buffer.append(Constants.TEXT_QUALIFIER+ddf.format(data.get("admin"))+Constants.TEXT_QUALIFIER+",");
									buffer.append(Constants.TEXT_QUALIFIER+ddf.format(data.get("gst"))+Constants.TEXT_QUALIFIER+",");
									buffer.append(Constants.TEXT_QUALIFIER+ddf.format(data.get("premium"))+Constants.TEXT_QUALIFIER+",");
									buffer.append(Constants.TEXT_QUALIFIER+ddf.format(data.get("premiumGst"))+Constants.TEXT_QUALIFIER+",");
									buffer.append(Constants.TEXT_QUALIFIER+ddf.format(data.get("total"))+Constants.TEXT_QUALIFIER+",");
									
									if(!provider.contains("Total"))
									{
										providerTotal.put("txnCount", providerTotal.get("txnCount")==null ? data.get("txnCount") : data.get("txnCount") + providerTotal.get("txnCount"));
										providerTotal.put("txnAmt", providerTotal.get("txnAmt")==null ? data.get("txnAmt") : data.get("txnAmt") + providerTotal.get("txnAmt"));
										providerTotal.put("admin", providerTotal.get("admin")==null ? data.get("admin") : data.get("admin") + providerTotal.get("admin"));
										providerTotal.put("gst", providerTotal.get("gst")==null ? data.get("gst") : data.get("gst") + providerTotal.get("gst"));
										providerTotal.put("premium", providerTotal.get("premium")==null ? data.get("premium") : data.get("premium") + providerTotal.get("premium"));
										providerTotal.put("premiumGst", providerTotal.get("premiumGst")==null ? data.get("premiumGst") : data.get("premiumGst") + providerTotal.get("premiumGst"));
										providerTotal.put("total", providerTotal.get("total")==null ? data.get("total") : data.get("total") + providerTotal.get("total"));
									}
									found = true;
									break;
								}
							}
							if(!found){
								buffer.append(Constants.TEXT_QUALIFIER+idf.format(0)+Constants.TEXT_QUALIFIER+",");
								buffer.append(Constants.TEXT_QUALIFIER+ddf.format(0)+Constants.TEXT_QUALIFIER+",");
								buffer.append(Constants.TEXT_QUALIFIER+ddf.format(0)+Constants.TEXT_QUALIFIER+",");
								buffer.append(Constants.TEXT_QUALIFIER+ddf.format(0)+Constants.TEXT_QUALIFIER+",");
								buffer.append(Constants.TEXT_QUALIFIER+ddf.format(0)+Constants.TEXT_QUALIFIER+",");
								buffer.append(Constants.TEXT_QUALIFIER+ddf.format(0)+Constants.TEXT_QUALIFIER+",");
								buffer.append(Constants.TEXT_QUALIFIER+ddf.format(0)+Constants.TEXT_QUALIFIER+",");
							}
						}
						buffer.append(Constants.TEXT_QUALIFIER+idf.format(providerTotal.get("txnCount"))+Constants.TEXT_QUALIFIER+",");
						buffer.append(Constants.TEXT_QUALIFIER+ddf.format(providerTotal.get("txnAmt"))+Constants.TEXT_QUALIFIER+",");
						buffer.append(Constants.TEXT_QUALIFIER+ddf.format(providerTotal.get("admin"))+Constants.TEXT_QUALIFIER+",");
						buffer.append(Constants.TEXT_QUALIFIER+ddf.format(providerTotal.get("gst"))+Constants.TEXT_QUALIFIER+",");
						buffer.append(Constants.TEXT_QUALIFIER+ddf.format(providerTotal.get("premium"))+Constants.TEXT_QUALIFIER+",");
						buffer.append(Constants.TEXT_QUALIFIER+ddf.format(providerTotal.get("premiumGst"))+Constants.TEXT_QUALIFIER+",");
						buffer.append(Constants.TEXT_QUALIFIER+ddf.format(providerTotal.get("total"))+Constants.TEXT_QUALIFIER);
						buffer.append("\n");
					}
					if(type.equals("CC") && entity.equals("CCA")){
						buffer.append(Constants.TEXT_QUALIFIER+"TOTAL CREDIT CARD"+Constants.TEXT_QUALIFIER+",");
						Double txnCountTotal = 0.0, txnAmtTotal = 0.0, adminTotal = 0.0, gstTotal = 0.0, premiumTotal = 0.0, premiumGstTotal = 0.0, totalTotal = 0.0;
						for(String providerName : providerHeaders){
							boolean found = false;
							for(String provider : ccCCATotal.keySet()){
								if(providerName.equals(provider)){
									Map<String, Double> data = ccCCATotal.get(provider);
									if(!provider.contains("(Total)"))
									{
										txnCountTotal += data.get("txnCount");
										txnAmtTotal += data.get("txnAmt");
										adminTotal += data.get("admin");
										gstTotal += data.get("gst");
										premiumTotal += data.get("premium");
										premiumGstTotal += data.get("premiumGst");
										totalTotal += data.get("total");
									}
									buffer.append(Constants.TEXT_QUALIFIER+idf.format(data.get("txnCount"))+Constants.TEXT_QUALIFIER+",");
									buffer.append(Constants.TEXT_QUALIFIER+ddf.format(data.get("txnAmt"))+Constants.TEXT_QUALIFIER+",");
									buffer.append(Constants.TEXT_QUALIFIER+ddf.format(data.get("admin"))+Constants.TEXT_QUALIFIER+",");
									buffer.append(Constants.TEXT_QUALIFIER+ddf.format(data.get("gst"))+Constants.TEXT_QUALIFIER+",");
									buffer.append(Constants.TEXT_QUALIFIER+ddf.format(data.get("premium"))+Constants.TEXT_QUALIFIER+",");
									buffer.append(Constants.TEXT_QUALIFIER+ddf.format(data.get("premiumGst"))+Constants.TEXT_QUALIFIER+",");
									buffer.append(Constants.TEXT_QUALIFIER+ddf.format(data.get("total"))+Constants.TEXT_QUALIFIER+",");
									found = true;
									break;
								}
							}
							if(!found){
								buffer.append(Constants.TEXT_QUALIFIER+idf.format(0)+Constants.TEXT_QUALIFIER+",");
								buffer.append(Constants.TEXT_QUALIFIER+ddf.format(0)+Constants.TEXT_QUALIFIER+",");
								buffer.append(Constants.TEXT_QUALIFIER+ddf.format(0)+Constants.TEXT_QUALIFIER+",");
								buffer.append(Constants.TEXT_QUALIFIER+ddf.format(0)+Constants.TEXT_QUALIFIER+",");
								buffer.append(Constants.TEXT_QUALIFIER+ddf.format(0)+Constants.TEXT_QUALIFIER+",");
								buffer.append(Constants.TEXT_QUALIFIER+ddf.format(0)+Constants.TEXT_QUALIFIER+",");
								buffer.append(Constants.TEXT_QUALIFIER+ddf.format(0)+Constants.TEXT_QUALIFIER+",");
							}
						}
						buffer.append(Constants.TEXT_QUALIFIER+idf.format(txnCountTotal)+Constants.TEXT_QUALIFIER+",");
						buffer.append(Constants.TEXT_QUALIFIER+ddf.format(txnAmtTotal)+Constants.TEXT_QUALIFIER+",");
						buffer.append(Constants.TEXT_QUALIFIER+ddf.format(adminTotal)+Constants.TEXT_QUALIFIER+",");
						buffer.append(Constants.TEXT_QUALIFIER+ddf.format(gstTotal)+Constants.TEXT_QUALIFIER+",");
						buffer.append(Constants.TEXT_QUALIFIER+ddf.format(premiumTotal)+Constants.TEXT_QUALIFIER+",");
						buffer.append(Constants.TEXT_QUALIFIER+ddf.format(premiumGstTotal)+Constants.TEXT_QUALIFIER+",");
						buffer.append(Constants.TEXT_QUALIFIER+ddf.format(totalTotal)+Constants.TEXT_QUALIFIER+",");
						buffer.append("\n");
					}
				}
				buffer.append("\n");
				buffer.append(Constants.TEXT_QUALIFIER+"TOTAL REVENUE FOR "+entity+Constants.TEXT_QUALIFIER+",");
				Map<String, Map<String, Double>> total = null;
				if(entity.equals("CCA") || entity.equals("CCA (Total)")){
					total = CCATotal;
				}else{
					total = nonCCATotal;
				}
				Double txnCountTotal = 0.0, txnAmtTotal = 0.0, adminTotal = 0.0, gstTotal = 0.0, premiumTotal = 0.0, premiumGstTotal = 0.0, totalTotal = 0.0;
				for(String providerHeader : providerHeaders){
					boolean found = false;
					for(String provider : total.keySet()){
						if(providerHeader.equals(provider)){
							Map<String, Double> data = total.get(provider);
							if(!provider.contains("Total"))
							{
								txnCountTotal += data.get("txnCount");
								txnAmtTotal += data.get("txnAmt");
								adminTotal += data.get("admin");
								gstTotal += data.get("gst");
								premiumTotal += data.get("premium");
								premiumGstTotal += data.get("premiumGst");
								totalTotal += data.get("total");
							}
								buffer.append(Constants.TEXT_QUALIFIER+idf.format(data.get("txnCount"))+Constants.TEXT_QUALIFIER+",");
								buffer.append(Constants.TEXT_QUALIFIER+ddf.format(data.get("txnAmt"))+Constants.TEXT_QUALIFIER+",");
								buffer.append(Constants.TEXT_QUALIFIER+ddf.format(data.get("admin"))+Constants.TEXT_QUALIFIER+",");
								buffer.append(Constants.TEXT_QUALIFIER+ddf.format(data.get("gst"))+Constants.TEXT_QUALIFIER+",");
								buffer.append(Constants.TEXT_QUALIFIER+ddf.format(data.get("premium"))+Constants.TEXT_QUALIFIER+",");
								buffer.append(Constants.TEXT_QUALIFIER+ddf.format(data.get("premiumGst"))+Constants.TEXT_QUALIFIER+",");
								buffer.append(Constants.TEXT_QUALIFIER+ddf.format(data.get("total"))+Constants.TEXT_QUALIFIER+",");
								found = true;
								break;
							}
					}
					if(!found){
						buffer.append(Constants.TEXT_QUALIFIER+idf.format(0)+Constants.TEXT_QUALIFIER+",");
						buffer.append(Constants.TEXT_QUALIFIER+ddf.format(0)+Constants.TEXT_QUALIFIER+",");
						buffer.append(Constants.TEXT_QUALIFIER+ddf.format(0)+Constants.TEXT_QUALIFIER+",");
						buffer.append(Constants.TEXT_QUALIFIER+ddf.format(0)+Constants.TEXT_QUALIFIER+",");
						buffer.append(Constants.TEXT_QUALIFIER+ddf.format(0)+Constants.TEXT_QUALIFIER+",");
						buffer.append(Constants.TEXT_QUALIFIER+ddf.format(0)+Constants.TEXT_QUALIFIER+",");
						buffer.append(Constants.TEXT_QUALIFIER+ddf.format(0)+Constants.TEXT_QUALIFIER+",");
					}
				}
				buffer.append(Constants.TEXT_QUALIFIER+idf.format(txnCountTotal)+Constants.TEXT_QUALIFIER+",");
				buffer.append(Constants.TEXT_QUALIFIER+ddf.format(txnAmtTotal)+Constants.TEXT_QUALIFIER+",");
				buffer.append(Constants.TEXT_QUALIFIER+ddf.format(adminTotal)+Constants.TEXT_QUALIFIER+",");
				buffer.append(Constants.TEXT_QUALIFIER+ddf.format(gstTotal)+Constants.TEXT_QUALIFIER+",");
				buffer.append(Constants.TEXT_QUALIFIER+ddf.format(premiumTotal)+Constants.TEXT_QUALIFIER+",");
				buffer.append(Constants.TEXT_QUALIFIER+ddf.format(premiumGstTotal)+Constants.TEXT_QUALIFIER+",");
				buffer.append(Constants.TEXT_QUALIFIER+ddf.format(totalTotal)+Constants.TEXT_QUALIFIER+",");
				buffer.append("\n");
				buffer.append("\n");
			}
			
			logger.info("RevenueReport 7. Put Value Complete, Generating Grant Total Revenue. size > "+grandTotal.keySet().size());
			buffer.append(Constants.TEXT_QUALIFIER+"GRAND TOTAL REVENUE"+Constants.TEXT_QUALIFIER+",");
			int sevenA = 0;
			Double txnCountTotal = 0.0, txnAmtTotal = 0.0, adminTotal = 0.0, gstTotal = 0.0, premiumTotal = 0.0, premiumGstTotal = 0.0, totalTotal = 0.0;
			for(String provider : grandTotal.keySet()){
				sevenA++;
				logger.info("7a at row > "+sevenA);
				Map<String, Double> data = grandTotal.get(provider);

				if(!provider.contains("Total"))
				{
					txnCountTotal += data.get("txnCount");
					txnAmtTotal += data.get("txnAmt");
					adminTotal += data.get("admin");
					gstTotal += data.get("gst");
					premiumTotal += data.get("premium");
					premiumGstTotal += data.get("premiumGst");
					totalTotal += data.get("total");
				}
				buffer.append(Constants.TEXT_QUALIFIER+idf.format(data.get("txnCount"))+Constants.TEXT_QUALIFIER+",");
				buffer.append(Constants.TEXT_QUALIFIER+ddf.format(data.get("txnAmt"))+Constants.TEXT_QUALIFIER+",");
				buffer.append(Constants.TEXT_QUALIFIER+ddf.format(data.get("admin"))+Constants.TEXT_QUALIFIER+",");
				buffer.append(Constants.TEXT_QUALIFIER+ddf.format(data.get("gst"))+Constants.TEXT_QUALIFIER+",");
				buffer.append(Constants.TEXT_QUALIFIER+ddf.format(data.get("premium"))+Constants.TEXT_QUALIFIER+",");
				buffer.append(Constants.TEXT_QUALIFIER+ddf.format(data.get("premiumGst"))+Constants.TEXT_QUALIFIER+",");
				buffer.append(Constants.TEXT_QUALIFIER+ddf.format(data.get("total"))+Constants.TEXT_QUALIFIER+",");
			}
			
			logger.info("RevenueReport 8. Grant Total Completed, finishing up..");
			
			buffer.append(Constants.TEXT_QUALIFIER+idf.format(txnCountTotal)+Constants.TEXT_QUALIFIER+",");
			buffer.append(Constants.TEXT_QUALIFIER+ddf.format(txnAmtTotal)+Constants.TEXT_QUALIFIER+",");
			buffer.append(Constants.TEXT_QUALIFIER+ddf.format(adminTotal)+Constants.TEXT_QUALIFIER+",");
			buffer.append(Constants.TEXT_QUALIFIER+ddf.format(gstTotal)+Constants.TEXT_QUALIFIER+",");
			buffer.append(Constants.TEXT_QUALIFIER+ddf.format(premiumTotal)+Constants.TEXT_QUALIFIER+",");
			buffer.append(Constants.TEXT_QUALIFIER+ddf.format(premiumGstTotal)+Constants.TEXT_QUALIFIER+",");
			buffer.append(Constants.TEXT_QUALIFIER+ddf.format(totalTotal)+Constants.TEXT_QUALIFIER+",");
			buffer.append("\n");
		}
		return buffer;
	}
}