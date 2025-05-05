package com.cdgtaxi.ibs.report.ui;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;

import org.apache.commons.httpclient.HttpException;
import org.apache.log4j.Logger;
import org.zkoss.util.media.AMedia;
import org.zkoss.zk.ui.metainfo.EventHandler;
import org.zkoss.zk.ui.metainfo.ZScript;
import org.zkoss.zk.ui.util.ConditionImpl;
import org.zkoss.zul.Filedownload;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.Label;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Radio;
import org.zkoss.zul.Radiogroup;
import org.zkoss.zul.Row;
import org.zkoss.zul.Rows;

import com.cdgtaxi.ibs.Uri;
import com.cdgtaxi.ibs.report.Constants;
import com.cdgtaxi.ibs.report.exception.FormatNotSupportedException;
import com.cdgtaxi.ibs.util.DateUtil;
import com.cdgtaxi.ibs.web.component.Combobox;
import com.elixirtech.ers2.client.ERSClient;
import com.elixirtech.net.NetException;

public class CreditBalanceWindow extends ReportWindow implements CSVFormat {
	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(CreditBalanceWindow.class);
	public CreditBalanceWindow() throws IOException {
		super("Credit Balance", "Account");
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
		String creditBalance = (String)reportParamsProperties.get("1. Credit Balance ($)");
		String acctNo = (String)reportParamsProperties.get("2a. Account No");
		String acctName = (String)reportParamsProperties.get("2b. Account Name");
		String div = (String)reportParamsProperties.get("2c. Division");
		String dept = (String)reportParamsProperties.get("2d. Department");
		String prodType = (String)reportParamsProperties.get("3. Product Type");
		String salespersonNo = (String)reportParamsProperties.get("4. Salesperson");
		String sortBy = (String)reportParamsProperties.get("5. Sort By");
		StringBuffer buffer = new StringBuffer();
		//Report Header
		buffer.append(Constants.TEXT_QUALIFIER+"Integrated Billing System"+Constants.TEXT_QUALIFIER+",");
		buffer.append("\n");
		buffer.append(Constants.TEXT_QUALIFIER+"Credit Balance Report"+Constants.TEXT_QUALIFIER+",");
		buffer.append("\n");
		buffer.append(Constants.TEXT_QUALIFIER+"Selection Criteria:"+Constants.TEXT_QUALIFIER+",");
		buffer.append("\n");
		buffer.append("\n");
		if(creditBalance!=null && creditBalance.length()>0){
			buffer.append(Constants.TEXT_QUALIFIER+"Credit Balance< "+creditBalance+Constants.TEXT_QUALIFIER+",");
			buffer.append("\n");
		}
		if(acctNo!=null && acctNo.length()>0){
			buffer.append(Constants.TEXT_QUALIFIER+"Account No: "+acctNo+Constants.TEXT_QUALIFIER+",");
			buffer.append("\n");
		}
		if(acctName!=null && acctName.length()>0){
			buffer.append(Constants.TEXT_QUALIFIER+"Account Name: "+acctName+Constants.TEXT_QUALIFIER+",");
			buffer.append("\n");
		}
		if(div!=null && div.length()>0){
			String divName = ((Listbox)this.getFellow("2c. Division")).getSelectedItem().getLabel();
			buffer.append(Constants.TEXT_QUALIFIER+"Division: "+divName+Constants.TEXT_QUALIFIER+",");
			buffer.append("\n");
		}
		if(dept!=null && dept.length()>0){
			String deptName = ((Listbox)this.getFellow("2d. Department")).getSelectedItem().getLabel();
			buffer.append(Constants.TEXT_QUALIFIER+"Division: "+deptName+Constants.TEXT_QUALIFIER+",");
			buffer.append("\n");
		}
		if(prodType!=null && prodType.length()>0){
			String prodTypeName = ((Listbox)this.getFellow("3. Product Type")).getSelectedItem().getLabel();
			buffer.append(Constants.TEXT_QUALIFIER+"Product Type: "+prodTypeName+Constants.TEXT_QUALIFIER+",");
			buffer.append("\n");
		}
		if(salespersonNo!=null && salespersonNo.length()>0){
			String salespersonName = ((Listbox)this.getFellow("4. Salesperson")).getSelectedItem().getLabel();
			buffer.append(Constants.TEXT_QUALIFIER+"Salesperson: "+salespersonName+Constants.TEXT_QUALIFIER+",");
			buffer.append("\n");
		}
		String sort = ((Listbox)this.getFellow("5. Sort By")).getSelectedItem().getLabel();
		buffer.append(Constants.TEXT_QUALIFIER+"Sort By: "+sort+Constants.TEXT_QUALIFIER+",");
		buffer.append("\n");
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
		buffer.append(Constants.TEXT_QUALIFIER+"Sales Person"+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+"Prdts Subscription"+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+"Div Code"+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+"Div Name"+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+"Div Prdts Subscription"+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+"Dept Code"+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+"Dept Name"+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+"Dept Prdts Subscription"+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+"Card No"+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+"Credit Limit ($)"+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+"Credit Balance ($)"+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+"Ship Contact"+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+"Ship Contact No(Tel, Mobile)"+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+"Ship Email"+Constants.TEXT_QUALIFIER+",");
		buffer.append(Constants.TEXT_QUALIFIER+"Ship Address"+Constants.TEXT_QUALIFIER+",");
		buffer.append("\n");
		//Data
		List<Object[]> results = this.businessHelper.getReportBusiness().getCreditBalance(creditBalance, "%" + acctNo + "%", "%" + acctName + "%", div, dept, prodType, salespersonNo, sortBy);
		logger.info("size = " + results.size());
		if(results.size()!=0){
			for(int i=0;i<results.size(); i++){
				buffer.append(""+Constants.TEXT_QUALIFIER+(i+1)+Constants.TEXT_QUALIFIER+",");
				Object[] result = results.get(i);
				for(int j=0;j<result.length;j++){//for(Object data : result){
					Object data = result[j];
					if(data!=null){
						if(j==10){
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
	public void populateReportParameters(Rows rows) throws NetException{
		super.populateReportParameters(rows);
		// populating radio buttons
		Row criteriaRow = new Row();
		Label label = new Label("Criteria Selection");
		label.setSclass("fieldLabel");
		criteriaRow.appendChild(label);
		Radiogroup criteriaGroup =  new Radiogroup();
		criteriaGroup.setId("criteriaGroup");
		criteriaRow.appendChild(criteriaGroup);
		ZScript showInfo = ZScript.parseContent("reportWindow.selectCriteria()");
		showInfo.setLanguage("java");
		EventHandler pdEvent = new EventHandler(showInfo, ConditionImpl.getInstance(null, null));
		criteriaGroup.addEventHandler("onCheck", pdEvent);
		Radio acctRadio = new Radio("Account Criteria");
		acctRadio.setSelected(true);
		criteriaGroup.appendChild(acctRadio);
		Radio salesRadio = new Radio("Salesperson Criteria");
		criteriaGroup.appendChild(salesRadio);
		this.getFellow("2a. Account No").getParent().getParent().insertBefore(criteriaRow, this.getFellow("2a. Account No").getParent());
		try{
			selectCriteria();
		}catch(InterruptedException ie){
			logger.error(ie);
		}
	}
	public void selectCriteria() throws InterruptedException{
		Radiogroup criteriaGroup = (Radiogroup)this.getFellow("criteriaGroup");
		if(criteriaGroup.getSelectedItem().getLabel().equals("Account Criteria")){
			Intbox acctNo = (Intbox)this.getFellow("2a. Account No");
			acctNo.setDisabled(false);
			Combobox acctName = (Combobox)this.getFellow("2b. Account Name");
			acctName.setDisabled(false);
			Listbox productTypes = (Listbox)this.getFellow("3. Product Type");
			productTypes.setDisabled(false);
			Listbox salespersons = (Listbox)this.getFellow("4. Salesperson");
			salespersons.setSelectedIndex(0);
			salespersons.setDisabled(true);
			logger.info("acct");
		}else{
			Intbox acctNo = (Intbox)this.getFellow("2a. Account No");
			acctNo.setValue(null);
			acctNo.setDisabled(true);
			Combobox acctName = (Combobox)this.getFellow("2b. Account Name");
			acctName.setValue(null);
			acctName.setSelectedItem(null);
			acctName.getChildren().clear();
			acctName.setDisabled(true);
			this.onSelectAccountName(acctNo);
			Listbox productTypes = (Listbox)this.getFellow("3. Product Type");
			productTypes.setSelectedIndex(0);
			productTypes.setDisabled(true);
			Listbox salespersons = (Listbox)this.getFellow("4. Salesperson");
			salespersons.setDisabled(false);
			logger.info("salesperson");
		}
	}
}