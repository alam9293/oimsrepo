package com.cdgtaxi.ibs.report.ui;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Properties;

import org.apache.commons.httpclient.HttpException;
import org.apache.log4j.Logger;
import org.zkoss.util.media.AMedia;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Filedownload;
import org.zkoss.zul.Listbox;

import com.cdgtaxi.ibs.report.Constants;
import com.cdgtaxi.ibs.report.exception.FormatNotSupportedException;
import com.cdgtaxi.ibs.util.DateUtil;
import com.elixirtech.ers2.client.ERSClient;
import com.elixirtech.net.NetException;

public class TimelyPaymentStatisticsDetailedReport extends ReportWindow {

	
	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(TimelyPaymentStatisticsDetailedReport.class);
	
	public TimelyPaymentStatisticsDetailedReport() throws IOException {
		super("Timely Payment Statistics Detailed Report", "Aging");
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
			AMedia media = new AMedia(report.replaceAll(" ", "_") + "." + extension, extension, format,
					dataInCSV.toString());
			Filedownload.save(media);
		}else{
			throw new FormatNotSupportedException();
			
		}
	}

	

	private StringBuffer generateCSVData(Properties reportParamsProperties) {

		String mthYearCriteria = (String)reportParamsProperties.get("1. Month");
		String entityNoCriteria = (String)reportParamsProperties.get("2. Entity");
		String creditTermCriteria = (String)reportParamsProperties.get("3. Credit Term");
		String typeCriteria = (String)reportParamsProperties.get("4. Type");
		StringBuffer sb = new StringBuffer();

		// Report Header
		sb.append(Constants.TEXT_QUALIFIER + "Integrated Billing System" + Constants.TEXT_QUALIFIER
				+ ",");
		sb.append("\n");
		sb.append(Constants.TEXT_QUALIFIER + "Timely Payment Statistics Detailed Report"
				+ Constants.TEXT_QUALIFIER + ",");
		sb.append("\n");
		sb.append(Constants.TEXT_QUALIFIER + "Selection Criteria:" + Constants.TEXT_QUALIFIER + ",");
		sb.append("\n");

		// Line Break
		sb.append("\n");

		if(mthYearCriteria!=null && mthYearCriteria.length()>0){
			DateFormat df = new SimpleDateFormat("MMM-yyyy");
			sb.append(Constants.TEXT_QUALIFIER+"Mth/ Year: "+df.format(((Datebox)this.getFellow("1. Month")).getValue())+Constants.TEXT_QUALIFIER+",");
			sb.append("\n");
		}

		if(entityNoCriteria!=null && entityNoCriteria.length()>0){
			String entityName = ((Listbox)this.getFellow("2. Entity")).getSelectedItem().getLabel();
			sb.append(Constants.TEXT_QUALIFIER+"Entity: "+entityName+Constants.TEXT_QUALIFIER+",");
			sb.append("\n");
		}
		
		if (creditTermCriteria != null && !"".equals(creditTermCriteria)) {

			sb.append(Constants.TEXT_QUALIFIER + "Credit Term: " + creditTermCriteria + Constants.TEXT_QUALIFIER + ",");
			sb.append("\n");
		}
		
		if (typeCriteria != null && !"-".equals(typeCriteria)) {
			String typeName = ((Listbox)this.getFellow("4. Type")).getSelectedItem().getLabel();
			sb.append(Constants.TEXT_QUALIFIER + "Type: " + typeName + Constants.TEXT_QUALIFIER + ",");
			sb.append("\n");
		}
		
		// Line Break
		sb.append("\n");

		sb.append(Constants.TEXT_QUALIFIER+"Printed By: "+reportParamsProperties.get("printedBy")+Constants.TEXT_QUALIFIER+",");
	
		sb.append("\n");
		sb.append(Constants.TEXT_QUALIFIER + "Report Date: "
				+ DateUtil.convertDateToStr(DateUtil.getCurrentDate(), DateUtil.GLOBAL_DATE_FORMAT)
				+ Constants.TEXT_QUALIFIER + ",");
		sb.append("\n");

		// Line Break
		sb.append("\n");
			
		
		// Column Title
		sb.append(Constants.TEXT_QUALIFIER + "S/N" + Constants.TEXT_QUALIFIER + ",");
		sb.append(Constants.TEXT_QUALIFIER + "Account No" + Constants.TEXT_QUALIFIER + ",");
		sb.append(Constants.TEXT_QUALIFIER + "Account Name" + Constants.TEXT_QUALIFIER + ",");
		sb.append(Constants.TEXT_QUALIFIER + "Credit Term" + Constants.TEXT_QUALIFIER + ",");
		sb.append(Constants.TEXT_QUALIFIER + "Billing Cycle" + Constants.TEXT_QUALIFIER + ",");
		sb.append("\n");

		// Data
		List<Object[]> results = this.businessHelper.getReportBusiness().getTimelyPaymentStatisticsDetailed(mthYearCriteria, entityNoCriteria, creditTermCriteria, typeCriteria);
		
		if (results.size() == 0) {
			sb.append(Constants.TEXT_QUALIFIER + "No records found" + Constants.TEXT_QUALIFIER
					+ ",");
			sb.append("\n");
		} else {
			
			String accountNo = null;
			String accountName = null;
			String creditTerm = null;
			String billingCycle = null;
			
			for(int i=0;i<results.size(); i++){
				Object[] result = results.get(i);
				accountNo = (String)result[0];
				accountName = (String) result[1];
				creditTerm = (String) result[2];
				billingCycle = (String) result[3];
				
				sb.append("" + Constants.TEXT_QUALIFIER + (i+1) + Constants.TEXT_QUALIFIER + ",");
				sb.append("" + Constants.TEXT_QUALIFIER + accountNo + Constants.TEXT_QUALIFIER + ",");
				sb.append("" + Constants.TEXT_QUALIFIER + accountName+ Constants.TEXT_QUALIFIER + ",");
				sb.append("" + Constants.TEXT_QUALIFIER + creditTerm + Constants.TEXT_QUALIFIER + ",");
				sb.append("" + Constants.TEXT_QUALIFIER + billingCycle + Constants.TEXT_QUALIFIER + ",");
				sb.append("\n");
			}
			
		}

		return sb;
	}
}