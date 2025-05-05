package com.cdgtaxi.ibs.report.ui;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.httpclient.HttpException;
import org.apache.log4j.Logger;
import org.zkoss.util.media.AMedia;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Filedownload;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Rows;

import com.cdgtaxi.ibs.report.Constants;
import com.cdgtaxi.ibs.report.exception.FormatNotSupportedException;
import com.cdgtaxi.ibs.util.DateUtil;
import com.cdgtaxi.ibs.web.constraint.RequiredConstraint;
import com.elixirtech.ers2.client.ERSClient;
import com.elixirtech.net.NetException;

public class GiroFileWindow extends ReportWindow {

	
	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(TimelyPaymentStatisticsDetailedReport.class);
	private Intbox accountNoTB;
	private Combobox accountNameCB;
	private Listbox entityLB, departmentLB, divisionLB;
	private Datebox cutOffDateDB;
	
	
	public GiroFileWindow() throws IOException {
		super("GIRO File Report", "GIRO");
	}
	
	
	@Override
	public void populateReportParameters(Rows rows) throws NetException {
		super.populateReportParameters(rows);
		accountNoTB = (Intbox)this.getFellow("1a. Account No");
		accountNameCB = (Combobox)this.getFellow("1b. Account Name");
		divisionLB = (Listbox)this.getFellow("1c. Division");
		departmentLB= (Listbox)this.getFellow("1d. Department");
		
		entityLB = (Listbox)this.getFellow("2. Entity");
		cutOffDateDB = (Datebox)this.getFellow("3. Cut Off Date");
		
		
	}

	//Override search division and department account so that it only list out bill able account
	@Override
	public Map<String, String> searchDivisionAccount(String custNo) {
		return businessHelper.getReportBusiness().searchChildrenAccount(custNo, true);
	}
	
	@Override
	public Map<String, String> searchDepartmentAccount(String custNo, String parentCode) {
		
		return businessHelper.getReportBusiness().searchChildrenAccount(custNo, parentCode, true);
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

		String custNoCriteria = (String)reportParamsProperties.get("1a. Account No");
		String accountNameCriteria = (String)reportParamsProperties.get("1b. Account Name");
		String divisionCriteria = (String)reportParamsProperties.get("1c. Division");
		String departmentCriteria = (String)reportParamsProperties.get("1d. Department");
		String entityCriteria = (String)reportParamsProperties.get("2. Entity");
		String cutOffDateCriteria = (String)reportParamsProperties.get("3. Cut Off Date");
		StringBuffer sb = new StringBuffer();

		// Report Header
		sb.append(Constants.TEXT_QUALIFIER + "Integrated Billing System" + Constants.TEXT_QUALIFIER
				+ ",");
		sb.append("\n");
		sb.append(Constants.TEXT_QUALIFIER + "GIRO File Report"
				+ Constants.TEXT_QUALIFIER + ",");
		sb.append("\n");
		sb.append(Constants.TEXT_QUALIFIER + "Selection Criteria:" + Constants.TEXT_QUALIFIER + ",");
		sb.append("\n");

		// Line Break
		sb.append("\n");

		if (custNoCriteria != null && !"".equals(custNoCriteria)) {
			sb.append(Constants.TEXT_QUALIFIER + "Account No: " + custNoCriteria + Constants.TEXT_QUALIFIER + ",");
			sb.append("\n");
		}
		
		if(accountNameCriteria!=null && accountNameCriteria.length()>0){
			String accountName = ((Combobox)this.getFellow("1b. Account Name")).getSelectedItem().getLabel();
			sb.append(Constants.TEXT_QUALIFIER+"Account Name: "+accountName+Constants.TEXT_QUALIFIER+",");
			sb.append("\n");
		}
		if(divisionCriteria!=null && divisionCriteria.length()>0){
			String divisionName = ((Listbox)this.getFellow("1c. Division")).getSelectedItem().getLabel();
			sb.append(Constants.TEXT_QUALIFIER+"Division: "+divisionName+Constants.TEXT_QUALIFIER+",");
			sb.append("\n");
		}
		if(departmentCriteria!=null && departmentCriteria.length()>0){
			String departmentName = ((Listbox)this.getFellow("1d. Department")).getSelectedItem().getLabel();
			sb.append(Constants.TEXT_QUALIFIER+"Department: "+departmentName+Constants.TEXT_QUALIFIER+",");
			sb.append("\n");
		}
		
		if(entityCriteria!=null && entityCriteria.length()>0){
			String entityName = ((Listbox)this.getFellow("2. Entity")).getSelectedItem().getLabel();
			sb.append(Constants.TEXT_QUALIFIER+"Entity: "+entityName+Constants.TEXT_QUALIFIER+",");
			sb.append("\n");
		}
		if(cutOffDateCriteria!=null &&cutOffDateCriteria.length()>0){
			String cutOffDate = DateUtil.convertDateToStr(((Datebox)this.getFellow("3. Cut Off Date")).getValue(), DateUtil.REPORT_DATE_FORMAT);
			sb.append(Constants.TEXT_QUALIFIER+"Cut Off Date: "+cutOffDate+Constants.TEXT_QUALIFIER+",");
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
		sb.append(Constants.TEXT_QUALIFIER + "Entity" + Constants.TEXT_QUALIFIER + ",");
		sb.append(Constants.TEXT_QUALIFIER + "Account No" + Constants.TEXT_QUALIFIER + ",");
		sb.append(Constants.TEXT_QUALIFIER + "Account Name" + Constants.TEXT_QUALIFIER + ",");
		sb.append(Constants.TEXT_QUALIFIER + "Billed To" + Constants.TEXT_QUALIFIER + ",");
		sb.append(Constants.TEXT_QUALIFIER + "Outstanding Amount" + Constants.TEXT_QUALIFIER + ",");
		sb.append("\n");

		// Data
		List<Object[]> results = null;
		
		results = this.businessHelper.getReportBusiness().getGiroFile(custNoCriteria, divisionCriteria, departmentCriteria, entityCriteria, cutOffDateCriteria);
		
		if (results.size() == 0) {
			sb.append(Constants.TEXT_QUALIFIER + "No records found" + Constants.TEXT_QUALIFIER
					+ ",");
			sb.append("\n");
		} else {
			
			String entity = null;
			String accountNo = null;
			String accountName = null;
			String code = null;
			String billedTo = null;
			BigDecimal outstandingAmount = null;
			BigDecimal totalOutstandingAmount = new BigDecimal(0);
			
			for(int i=0;i<results.size(); i++){
				Object[] result = results.get(i);
				entity = (String)result[0];
				accountNo = (String) result[1];
				accountName = (String) result[2];
				billedTo = (String) result[3];
				code = (String) result[4];
				if(code!=null)
					billedTo = billedTo + " (" + code + ")";
				outstandingAmount = (BigDecimal)result[5];
				totalOutstandingAmount = totalOutstandingAmount.add(outstandingAmount);
				
				sb.append("" + Constants.TEXT_QUALIFIER + (i+1) + Constants.TEXT_QUALIFIER + ",");
				sb.append("" + Constants.TEXT_QUALIFIER + entity + Constants.TEXT_QUALIFIER + ",");
				sb.append("" + Constants.TEXT_QUALIFIER + accountNo + Constants.TEXT_QUALIFIER + ",");
				sb.append("" + Constants.TEXT_QUALIFIER + accountName+ Constants.TEXT_QUALIFIER + ",");
				sb.append("" + Constants.TEXT_QUALIFIER + billedTo + Constants.TEXT_QUALIFIER + ",");
				sb.append("" + Constants.TEXT_QUALIFIER + outstandingAmount + Constants.TEXT_QUALIFIER + ",");
				sb.append("\n");
			}
			
			
			for(int i=0; i<4; i++) {
				sb.append(Constants.TEXT_QUALIFIER+Constants.TEXT_QUALIFIER+",");
			}
			sb.append(Constants.TEXT_QUALIFIER + "TOTAL" + Constants.TEXT_QUALIFIER + ",");
			sb.append(Constants.TEXT_QUALIFIER +  totalOutstandingAmount + Constants.TEXT_QUALIFIER + ",");
			
		}

		return sb;
	}
	
	public void reset(){
		
		accountNoTB.setValue(null);
		accountNameCB.setText("");
		accountNameCB.getChildren().clear();
		divisionLB.clearSelection();
		departmentLB.clearSelection();
		entityLB.setSelectedIndex(0);
		cutOffDateDB.setConstraint("");
		cutOffDateDB.setText("");
		cutOffDateDB.setConstraint(new RequiredConstraint());
		
	}
	
}