package com.cdgtaxi.ibs.report.ui;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
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
import com.cdgtaxi.ibs.util.DateUtil;
import com.cdgtaxi.ibs.util.StringUtil;
import com.elixirtech.ers2.client.ERSClient;
import com.elixirtech.net.NetException;

public class TripReconciliationReportWindow extends ReportWindow implements CSVFormat {
	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(TripReconciliationReportWindow.class);
	public TripReconciliationReportWindow() throws IOException {
		super("Trip Reconciliation", "Trips");
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
		String extension = Constants.EXTENSION_CSV;
		StringBuffer dataInCSV = this.generateCSVData(reportParamsProperties);
		AMedia media = new AMedia(report.replaceAll(" ", "_")+"."+extension, extension, Constants.FORMAT_CSV, dataInCSV.toString());
		Filedownload.save(media);
	}
	public StringBuffer generateCSVData(Properties reportParamsProperties) {
		
		String entity = (String)reportParamsProperties.get("01. Entity");
		String accountNo = (String)reportParamsProperties.get("02a. Account No");
		String accountName = (String)reportParamsProperties.get("02b. Account Name");
		String division = (String)reportParamsProperties.get("02c. Division");
		String department = (String)reportParamsProperties.get("02d. Department");
		String productType = (String)reportParamsProperties.get("03. Product Type");
		String startDate = (String)reportParamsProperties.get("04. Trip Start Date");
		String endDate = (String)reportParamsProperties.get("05. Trip End Date");
		String uploadStartDate = (String)reportParamsProperties.get("06. Upload Start Date");
		String uploadEndDate = (String)reportParamsProperties.get("07. Upload End Date");
		String companyCode = (String)reportParamsProperties.get("08. Company Code");
		String txnStatus = (String)reportParamsProperties.get("09. Txn Status");
		String sortBy = (String)reportParamsProperties.get("10. Sort By");

		StringBuffer buffer = new StringBuffer();
		
		//Report Header
		buffer.append(Constants.TEXT_QUALIFIER+"Integrated Billing System"+Constants.TEXT_QUALIFIER+",");
		buffer.append("\n");
		buffer.append(Constants.TEXT_QUALIFIER+"Trip Reconciliation Report"+Constants.TEXT_QUALIFIER+",");
		buffer.append("\n");
		buffer.append("\n");
		buffer.append(Constants.TEXT_QUALIFIER+"Selection Criteria:"+Constants.TEXT_QUALIFIER+",");
		buffer.append("\n");
		
		if(entity !=null && entity.length() > 0){
			String entityStr = ((Listbox)this.getFellow("01. Entity")).getSelectedItem().getLabel();
			buffer.append(Constants.TEXT_QUALIFIER+"Entity: "+entityStr+Constants.TEXT_QUALIFIER+",");
			buffer.append("\n");
		}
		if(accountNo !=null && accountNo.length() > 0){
			buffer.append(Constants.TEXT_QUALIFIER+"Account No: "+accountNo+Constants.TEXT_QUALIFIER+",");
			buffer.append("\n");
		}
		if(accountName !=null && accountName.length() > 0){
			buffer.append(Constants.TEXT_QUALIFIER+"Account Name: "+accountName+Constants.TEXT_QUALIFIER+",");
			buffer.append("\n");
		}
		if(division !=null && division.length() > 0){
			String divisionStr = ((Listbox)this.getFellow("02c. Division")).getSelectedItem().getLabel();
			buffer.append(Constants.TEXT_QUALIFIER+"Division: "+divisionStr+Constants.TEXT_QUALIFIER+",");
			buffer.append("\n");
		}
		if(department !=null && department.length() > 0){
			String departmentStr = ((Listbox)this.getFellow("02d. Department")).getSelectedItem().getLabel();
			buffer.append(Constants.TEXT_QUALIFIER+"Department: "+departmentStr+Constants.TEXT_QUALIFIER+",");
			buffer.append("\n");
		}
		if(productType !=null && productType.length() > 0){
			String productTypeStr = ((Listbox)this.getFellow("03. Product Type")).getSelectedItem().getLabel();
			buffer.append(Constants.TEXT_QUALIFIER+"Product Type: "+productTypeStr+Constants.TEXT_QUALIFIER+",");
			buffer.append("\n");
		}
		if((startDate!=null && startDate.length()>0) || (endDate!=null && endDate.length()!=0)){
			buffer.append(Constants.TEXT_QUALIFIER+"Trip Date: ");
			if(startDate!=null && startDate.length()>0){
				buffer.append(startDate);
			}else{
				buffer.append(endDate);
			}
			buffer.append(" to ");
			if(endDate!=null && endDate.length()!=0){
				buffer.append(endDate+Constants.TEXT_QUALIFIER+",");
			}else{
				buffer.append(startDate+Constants.TEXT_QUALIFIER+",");
			}
			buffer.append("\n");
		}
		if((uploadStartDate!=null && uploadStartDate.length()>0) || (uploadEndDate!=null && uploadEndDate.length()!=0)){
			buffer.append(Constants.TEXT_QUALIFIER+"Upload Date: ");
			if(uploadStartDate!=null && uploadStartDate.length()>0){
				buffer.append(uploadStartDate);
			}else{
				buffer.append(uploadEndDate);
			}
			buffer.append(" to ");
			if(uploadEndDate!=null && uploadEndDate.length()!=0){
				buffer.append(uploadEndDate+Constants.TEXT_QUALIFIER+",");
			}else{
				buffer.append(uploadStartDate+Constants.TEXT_QUALIFIER+",");
			}
			buffer.append("\n");
		}
		if(companyCode !=null && companyCode.length() > 0){
			String companyCodeStr = ((Listbox)this.getFellow("08. Company Code")).getSelectedItem().getLabel();
			buffer.append(Constants.TEXT_QUALIFIER+"Entity: "+companyCodeStr+Constants.TEXT_QUALIFIER+",");
			buffer.append("\n");
		}
		if(txnStatus!=null && txnStatus.length() > 0){
			String txnStatusStr = ((Listbox)this.getFellow("09. Txn Status")).getSelectedItem().getLabel();
			buffer.append(Constants.TEXT_QUALIFIER+"Transaction Status: "+txnStatusStr+Constants.TEXT_QUALIFIER+",");
			buffer.append("\n");
		}
		if(sortBy!=null && sortBy.length() > 0){
			//sortBy is printing code
			String orderByStr = ((Listbox)this.getFellow("10. Sort By")).getSelectedItem().getLabel();
			buffer.append(Constants.TEXT_QUALIFIER+"Sort By: "+orderByStr+Constants.TEXT_QUALIFIER+",");
			buffer.append("\n");
		}
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
		buffer.append(Constants.TEXT_QUALIFIER+"S/N"+Constants.TEXT_QUALIFIER+",");	
		buffer.append(Constants.TEXT_QUALIFIER+"Entity"+Constants.TEXT_QUALIFIER+",");	
		buffer.append(Constants.TEXT_QUALIFIER+"Account No"+Constants.TEXT_QUALIFIER+",");	
		buffer.append(Constants.TEXT_QUALIFIER+"Account Name"+Constants.TEXT_QUALIFIER+",");	
		buffer.append(Constants.TEXT_QUALIFIER+"Card No / Job Type"+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+"Name On Card"+Constants.TEXT_QUALIFIER+",");	
		buffer.append(Constants.TEXT_QUALIFIER+"Upload Date"+Constants.TEXT_QUALIFIER+",");	
		buffer.append(Constants.TEXT_QUALIFIER+"Job No"+Constants.TEXT_QUALIFIER+",");	
		buffer.append(Constants.TEXT_QUALIFIER+"Travel Date"+Constants.TEXT_QUALIFIER+",");	
		buffer.append(Constants.TEXT_QUALIFIER+"Travel Time"+Constants.TEXT_QUALIFIER+",");	
		buffer.append(Constants.TEXT_QUALIFIER+"Taxi No"+Constants.TEXT_QUALIFIER+",");	
		buffer.append(Constants.TEXT_QUALIFIER+"Driver NRIC"+Constants.TEXT_QUALIFIER+",");	
		buffer.append(Constants.TEXT_QUALIFIER+"Company Code"+Constants.TEXT_QUALIFIER+",");	
		buffer.append(Constants.TEXT_QUALIFIER+"Pickup Address"+Constants.TEXT_QUALIFIER+",");	
		buffer.append(Constants.TEXT_QUALIFIER+"Destination"+Constants.TEXT_QUALIFIER+",");	
		buffer.append(Constants.TEXT_QUALIFIER+"Taxi Fare($)"+Constants.TEXT_QUALIFIER+",");	
		buffer.append(Constants.TEXT_QUALIFIER+"Billed Amt"+Constants.TEXT_QUALIFIER+",");	
		buffer.append(Constants.TEXT_QUALIFIER+"Admin($)"+Constants.TEXT_QUALIFIER+",");	
		buffer.append(Constants.TEXT_QUALIFIER+"GST($)"+Constants.TEXT_QUALIFIER+",");	
		buffer.append(Constants.TEXT_QUALIFIER+"Total($)"+Constants.TEXT_QUALIFIER+",");	
		buffer.append(Constants.TEXT_QUALIFIER+"Invoice Month"+Constants.TEXT_QUALIFIER+",");	
		buffer.append(Constants.TEXT_QUALIFIER+"Invoice Year"+Constants.TEXT_QUALIFIER+",");	
		buffer.append(Constants.TEXT_QUALIFIER+"Invoice No"+Constants.TEXT_QUALIFIER+",");	
		buffer.append(Constants.TEXT_QUALIFIER+"Billing Contact"+Constants.TEXT_QUALIFIER+",");	
		buffer.append(Constants.TEXT_QUALIFIER+"Division Code"+Constants.TEXT_QUALIFIER+",");	
		buffer.append(Constants.TEXT_QUALIFIER+"Division Name"+Constants.TEXT_QUALIFIER+",");	
		buffer.append(Constants.TEXT_QUALIFIER+"Department Code"+Constants.TEXT_QUALIFIER+",");	
		buffer.append(Constants.TEXT_QUALIFIER+"Department Name"+Constants.TEXT_QUALIFIER+",");	
		buffer.append(Constants.TEXT_QUALIFIER+"Vehicle Type"+Constants.TEXT_QUALIFIER+",");	
		buffer.append(Constants.TEXT_QUALIFIER+"Trip Type"+Constants.TEXT_QUALIFIER+",");	
		buffer.append(Constants.TEXT_QUALIFIER+"Passenger"+Constants.TEXT_QUALIFIER+",");	
		buffer.append(Constants.TEXT_QUALIFIER+"Flight Info"+Constants.TEXT_QUALIFIER+",");	
		buffer.append(Constants.TEXT_QUALIFIER+"Book By"+Constants.TEXT_QUALIFIER+",");	
		buffer.append(Constants.TEXT_QUALIFIER+"Booking Ref"+Constants.TEXT_QUALIFIER+",");	
		buffer.append(Constants.TEXT_QUALIFIER+"Surcharge"+Constants.TEXT_QUALIFIER+",");	
		buffer.append(Constants.TEXT_QUALIFIER+"Project Code"+Constants.TEXT_QUALIFIER+",");	
		buffer.append(Constants.TEXT_QUALIFIER+"Transaction Status"+Constants.TEXT_QUALIFIER+",");
		buffer.append("\n");
		//Data

		List<Object[]> results = this.businessHelper.getReportBusiness().getTripReconciliationReport(
				entity,
				accountNo,
				accountName,
				division,
				department,
				productType,
				startDate!=null && startDate.length()!=0 ? startDate + " 00:00:00" : endDate!=null && endDate.length()!=0 ? endDate + " 00:00:00" : startDate,
				endDate!=null && endDate.length()!=0 ? endDate + " 23:59:59" : startDate!=null && startDate.length()!=0 ? startDate + " 23:59:59" : endDate,
				uploadStartDate!=null && uploadStartDate.length()!=0 ? uploadStartDate + " 00:00:00" : uploadEndDate!=null && uploadEndDate.length()!=0 ? uploadEndDate + " 00:00:00" : uploadStartDate,
				uploadEndDate!=null && uploadEndDate.length()!=0 ? uploadEndDate + " 23:59:59" : uploadStartDate!=null && uploadStartDate.length()!=0 ? uploadStartDate + " 23:59:59" : uploadEndDate,		
				companyCode,
				txnStatus,
				sortBy
		);
		if(results.size()!=0){
			List<Integer> displayFullDataLine = new ArrayList<Integer>();
			displayFullDataLine.add(3);
			displayFullDataLine.add(6);
			displayFullDataLine.add(23);
			displayFullDataLine.add(25);
			Map<String, Double> totals = new HashMap<String, Double>();
			for(int i=0;i<results.size(); i++){
				buffer.append(""+Constants.TEXT_QUALIFIER+(i+1)+Constants.TEXT_QUALIFIER+",");
				Object[] result = results.get(i);
				totals.put("fare", totals.get("fare")!=null ? totals.get("fare") + Double.parseDouble(result[14].toString().replaceAll(",", "")) : Double.parseDouble(result[14].toString().replaceAll(",", "")));
				totals.put("billed", totals.get("billed")!=null ? totals.get("billed") + Double.parseDouble(result[15].toString().replaceAll(",", "")) : Double.parseDouble(result[15].toString().replaceAll(",", "")));
				totals.put("admin", totals.get("admin")!=null ? totals.get("admin") + Double.parseDouble(result[16].toString().replaceAll(",", "")) : Double.parseDouble(result[16].toString().replaceAll(",", "")));
				totals.put("gst", totals.get("gst")!=null ? totals.get("gst") + Double.parseDouble(result[17].toString().replaceAll(",", "")) : Double.parseDouble(result[17].toString().replaceAll(",", "")));
				totals.put("total", totals.get("total")!=null ? totals.get("total") + Double.parseDouble(result[18].toString().replaceAll(",", "")) : Double.parseDouble(result[18].toString().replaceAll(",", "")));
				for(int j=0;j<result.length;j++){//for(Object data : result){
					Object data = result[j];
					if(data!=null){
						if(j==10)
							buffer.append(""+Constants.TEXT_QUALIFIER+StringUtil.maskNric(data.toString())+Constants.TEXT_QUALIFIER+",");
						if(j==35)
							data = NonConfigurableConstants.TXN_STATUS.get(data);
						if(displayFullDataLine.contains(j))
							buffer.append(""+Constants.TEXT_QUALIFIER+"\'"+data+Constants.TEXT_QUALIFIER+",");
						else
							buffer.append(""+Constants.TEXT_QUALIFIER+data+Constants.TEXT_QUALIFIER+",");
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
			buffer.append(Constants.TEXT_QUALIFIER+Constants.TEXT_QUALIFIER+",");
			buffer.append(Constants.TEXT_QUALIFIER+Constants.TEXT_QUALIFIER+",");
			buffer.append(Constants.TEXT_QUALIFIER+Constants.TEXT_QUALIFIER+",");
			buffer.append(Constants.TEXT_QUALIFIER+Constants.TEXT_QUALIFIER+",");
			buffer.append(Constants.TEXT_QUALIFIER+Constants.TEXT_QUALIFIER+",");
			buffer.append(Constants.TEXT_QUALIFIER+Constants.TEXT_QUALIFIER+",");
			buffer.append(Constants.TEXT_QUALIFIER+Constants.TEXT_QUALIFIER+",");
			buffer.append(Constants.TEXT_QUALIFIER+Constants.TEXT_QUALIFIER+",");
			buffer.append(Constants.TEXT_QUALIFIER+Constants.TEXT_QUALIFIER+",");
			buffer.append(Constants.TEXT_QUALIFIER+Constants.TEXT_QUALIFIER+",");
			buffer.append(Constants.TEXT_QUALIFIER+Constants.TEXT_QUALIFIER+",");
			DecimalFormat df = new DecimalFormat(StringUtil.GLOBAL_DECIMAL_FORMAT);
			buffer.append(Constants.TEXT_QUALIFIER+df.format(totals.get("fare"))+Constants.TEXT_QUALIFIER+",");
			buffer.append(Constants.TEXT_QUALIFIER+df.format(totals.get("billed"))+Constants.TEXT_QUALIFIER+",");
			buffer.append(Constants.TEXT_QUALIFIER+df.format(totals.get("admin"))+Constants.TEXT_QUALIFIER+",");
			buffer.append(Constants.TEXT_QUALIFIER+df.format(totals.get("gst"))+Constants.TEXT_QUALIFIER+",");
			buffer.append(Constants.TEXT_QUALIFIER+df.format(totals.get("total"))+Constants.TEXT_QUALIFIER+",");
		}else{
			buffer.append(""+Constants.TEXT_QUALIFIER+"No records found."+Constants.TEXT_QUALIFIER+",");
			buffer.append("\n");
		}

		return buffer;
		
	}
}