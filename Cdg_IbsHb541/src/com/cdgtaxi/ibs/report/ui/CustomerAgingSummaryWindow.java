package com.cdgtaxi.ibs.report.ui;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.httpclient.HttpException;
import org.apache.log4j.Logger;
import org.zkoss.util.media.AMedia;
import org.zkoss.zhtml.Messagebox;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zk.ui.ext.AfterCompose;
import org.zkoss.zkplus.spring.SpringUtil;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Comboitem;
import org.zkoss.zul.Decimalbox;
import org.zkoss.zul.Filedownload;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listitem;

import com.cdgtaxi.ibs.Uri;
import com.cdgtaxi.ibs.common.MasterSetup;
import com.cdgtaxi.ibs.common.model.AmtbAccount;
import com.cdgtaxi.ibs.common.model.FmtbArContCodeMaster;
import com.cdgtaxi.ibs.common.model.FmtbEntityMaster;
import com.cdgtaxi.ibs.common.ui.CommonWindow;
import com.cdgtaxi.ibs.master.model.MstbReportFormatMap;
import com.cdgtaxi.ibs.report.Constants;
import com.cdgtaxi.ibs.util.DateUtil;
import com.elixirtech.ers2.client.ERSClient;
import com.elixirtech.net.NetException;

public class CustomerAgingSummaryWindow extends CommonWindow implements AfterCompose{
	
	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(CustomerAgingSummaryWindow.class);
	
	private String report = "Customer Aging Summary";
	private String reportCategory = "Aging";
	private Long reportRsrcId;
	private ERSClient client;
	
	//Report Server Properties
	private static String ip;
	private static String port;
	private static String username;
	private static String password;
	private static String repository;
	
	@SuppressWarnings("unchecked")
	public CustomerAgingSummaryWindow() throws IOException{
		//retrieve properties bean
		Map elixirReportProperties = (Map)SpringUtil.getBean("elixirReportProperties");
		//retrieve properties value
		ip = (String)elixirReportProperties.get("report.server.ip");
		port = (String)elixirReportProperties.get("report.server.port");
		username = (String)elixirReportProperties.get("report.server.username");
		password = (String)elixirReportProperties.get("report.server.password");
		repository = (String)elixirReportProperties.get("report.server.repository.location");
		
		reportRsrcId = new Long(this.getHttpServletRequest().getParameter("rsrcId"));
		
		client = new ERSClient(ip, Integer.parseInt(port), username, password);
		client.setSecure(false);
	}
	
	public void afterCompose() {
		//populate entity list
		Listbox entityListBox = (Listbox)this.getFellow("entityList");
		Map<Integer, String> entities = MasterSetup.getEntityManager().getAllMasters();
		for(Integer entityNo : entities.keySet()){
			entityListBox.appendChild(new Listitem(entities.get(entityNo),entityNo.toString()));
		}
		entityListBox.setSelectedIndex(0);
		try {
			this.onSelectEntity(entityListBox);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		//populate typeList
		Listbox typeList = (Listbox)this.getFellow("typeList");
		typeList.appendChild(new Listitem("All", "All"));
		typeList.appendChild(new Listitem("CREDIT NOTE", "C"));
		typeList.appendChild(new Listitem("DEBIT NOTE", "D"));
		typeList.appendChild(new Listitem("INVOICE", "I"));
		typeList.appendChild(new Listitem("RECEIPT", "R"));
		typeList.setSelectedIndex(0);
		
		//populate typeList
		Listbox sortByList = (Listbox)this.getFellow("sortByList");
		sortByList.appendChild(new Listitem("ACCOUNT NAME / ID", "1"));
		sortByList.appendChild(new Listitem("OUTSTANDING AMOUNT (ASC)", "2"));
		sortByList.appendChild(new Listitem("OUTSTANDING AMOUNT (DESC)", "3"));
		sortByList.setSelectedIndex(0);
	}
	
	public void searchTopLevelAccountByAccountName(String name) throws InterruptedException{
		logger.info("");
		Combobox accountNameComboBox = (Combobox)this.getFellow("accountNameComboBox");
		
		//only begin new search if input is greater than 2
		if(name.length()<3) {
			return;
		}

		//accountName still the same as selected one, skip
		if(accountNameComboBox.getSelectedItem()!=null){
			AmtbAccount selectedAccount = (AmtbAccount)accountNameComboBox.getSelectedItem().getValue();
			if(name.equals(selectedAccount.getAccountName()+" ("+selectedAccount.getCustNo()+")")) {
				return;
			}
		}

		//clear textbox for a new search
		Intbox accountNoIntBox = (Intbox)this.getFellow("accountNoIntBox");
		accountNoIntBox.setText("");
		//Clear list for every new search
		accountNameComboBox.getChildren().clear();

		try{
			List<AmtbAccount> accounts = this.businessHelper.getReportBusiness().searchTopLevelAccount(null, name);
			for(AmtbAccount account : accounts){
				Comboitem item = new Comboitem(account.getAccountName()+" ("+account.getCustNo()+")");
				item.setValue(account);
				accountNameComboBox.appendChild(item);
			}
			if(accounts.size()==1){
				accountNameComboBox.setSelectedIndex(0);
				this.onSelectAccountName();
			}
			else accountNameComboBox.open();
		}
		catch(Exception e){
			Messagebox.show(com.cdgtaxi.ibs.common.NonConfigurableConstants.COMMON_ERROR_MESSAGE,
					"Error", Messagebox.OK, Messagebox.ERROR);
			e.printStackTrace();
		}
	}
	
	public void searchTopLevelAccountByAccountNo() throws InterruptedException{
		logger.info("");

		
		Intbox accountNoIntBox = (Intbox)this.getFellow("accountNoIntBox");
		Combobox accountNameComboBox = (Combobox)this.getFellow("accountNameComboBox");
		Integer accountNo = accountNoIntBox.getValue();
		
		if(accountNo==null) return;
		
		//accountName still the same as selected one, skip
		if(accountNameComboBox.getSelectedItem()!=null){
			AmtbAccount selectedAccount = (AmtbAccount)accountNameComboBox.getSelectedItem().getValue();
			if(accountNo.toString().equals(selectedAccount.getCustNo())) {
				return;
			}
		}

		//Clear combobox for a new search
		accountNameComboBox.setText("");
		//Clear list for every new search
		accountNameComboBox.getChildren().clear();

		try{
			List<AmtbAccount> accounts = this.businessHelper.getPaymentBusiness().searchBillableAccount(accountNo.toString(), null);
			for(AmtbAccount account : accounts){
				Comboitem item = new Comboitem(account.getAccountName()+" ("+account.getCustNo()+")");
				item.setValue(account);
				accountNameComboBox.appendChild(item);
			}
			if(accounts.size()==1) {
				accountNameComboBox.setSelectedIndex(0);
				this.onSelectAccountName();
			}
		}
		catch(Exception e){
			Messagebox.show(com.cdgtaxi.ibs.common.NonConfigurableConstants.COMMON_ERROR_MESSAGE,
					"Error", Messagebox.OK, Messagebox.ERROR);
			e.printStackTrace();
		}
	}
	
	public void onSelectAccountName() throws InterruptedException{
		logger.info("");

		try{
			Combobox accountNameComboBox = (Combobox)this.getFellow("accountNameComboBox");
			Intbox accountNoIntBox = (Intbox)this.getFellow("accountNoIntBox");

			if(accountNameComboBox.getSelectedItem()!=null){
				AmtbAccount selectedAccount = (AmtbAccount)accountNameComboBox.getSelectedItem().getValue();
				accountNoIntBox.setText(selectedAccount.getCustNo());
			}
		}
		catch(Exception e){
			Messagebox.show(com.cdgtaxi.ibs.common.NonConfigurableConstants.COMMON_ERROR_MESSAGE,
					"Error", Messagebox.OK, Messagebox.ERROR);
			e.printStackTrace();
		}
	}

	public void onSelectEntity(Listbox listbox) throws InterruptedException{
		try{
			Listitem selectedItem = listbox.getSelectedItem();
			String entityNo = (String)selectedItem.getValue();
			
			Listbox arControlCodeList = (Listbox)this.getFellow("arControlCodeList");
			arControlCodeList.getChildren().clear();
			arControlCodeList.appendChild(new Listitem("All", "All"));
			arControlCodeList.setSelectedIndex(0);
			
			List<FmtbArContCodeMaster> arContCodeList = this.businessHelper.getReportBusiness().searchArContCode(new Integer(entityNo));
			for(FmtbArContCodeMaster code : arContCodeList){
				arControlCodeList.appendChild(new Listitem(code.getArControlCode(), code.getArControlCodeNo()));
			}
		}
		catch(Exception e){
			Messagebox.show(com.cdgtaxi.ibs.common.NonConfigurableConstants.COMMON_ERROR_MESSAGE,
					"Error", Messagebox.OK, Messagebox.ERROR);
			e.printStackTrace();
		}
	}
	
	public void populateReportFormatList(Listbox listbox) throws NetException{
		List<MstbReportFormatMap> reportFormatMapList = this.businessHelper.getReportBusiness().getReportFormatMap(this.reportRsrcId);
		boolean firstItem = true;
		for(MstbReportFormatMap formatMap : reportFormatMapList){
			Listitem listItem = new Listitem(formatMap.getReportFormat(), Constants.EXTENSION_MAP.get(formatMap.getReportFormat()));
			if(firstItem){
				listItem.setSelected(true);
				firstItem = false;
			}
			listbox.appendChild(listItem);
		}
	}
	
	public void generate() throws HttpException, IOException, InterruptedException, NetException, WrongValueException {
		logger.info("");
		this.displayProcessing();
		
		String accountNo = "";
		String accountName = "";
		String entity = "";
		String arControl = "";
		String salesPerson = "";
		String outstandingAmount = "";
		String daysLate = "";
		String type = "";
		String sortBy = "";
		
		//Retrieve report parameters
		Properties reportParamsProperties = new Properties();
		
		Intbox accountNoIntBox = (Intbox)this.getFellow("accountNoIntBox");
		if(accountNoIntBox.getValue()!=null) {
			reportParamsProperties.put("1. Account No", accountNoIntBox.getValue().toString());
			accountNo = accountNoIntBox.getValue().toString();
		}
		else {
			Combobox accountNameComboBox = (Combobox)this.getFellow("accountNameComboBox");
			if(accountNameComboBox.getSelectedItem()!=null){
				AmtbAccount selectedAccount = (AmtbAccount)accountNameComboBox.getSelectedItem().getValue();
				reportParamsProperties.put("1. Account No", selectedAccount.getAccountNo().toString());
				reportParamsProperties.put("2. Account Name", selectedAccount.getAccountName());
				accountNo = accountNoIntBox.getValue().toString();
				accountName = selectedAccount.getAccountName();
			}
			else{
				if(accountNameComboBox.getText()!=null && accountNameComboBox.getText().length()>0) {
					reportParamsProperties.put("2. Account Name", accountNameComboBox.getText().toUpperCase());
					accountName = accountNameComboBox.getText().toUpperCase();
				}
			}
		}
		
		Listbox entityList = (Listbox)this.getFellow("entityList");
		reportParamsProperties.put("3. Entity", entityList.getSelectedItem().getValue().toString());
		entity = entityList.getSelectedItem().getValue().toString();
		
		Listbox arControlCodeList = (Listbox)this.getFellow("arControlCodeList");
		if(arControlCodeList.getSelectedItem()!=null){
			if(!arControlCodeList.getSelectedItem().getValue().toString().equals("All")){
				reportParamsProperties.put("4. AR Control", arControlCodeList.getSelectedItem().getValue().toString());
				arControl = arControlCodeList.getSelectedItem().getValue().toString();
			}
		}
		
		Decimalbox outstandingAmtDecimalBox = (Decimalbox)this.getFellow("outstandingAmtDecimalBox");
		if(outstandingAmtDecimalBox.getValue()!=null){
			reportParamsProperties.put("5. Outstanding Amount", outstandingAmtDecimalBox.getValue().toString());
			outstandingAmount = outstandingAmtDecimalBox.getValue().toString();
		}
		
		Listbox typeList = (Listbox)this.getFellow("typeList");
		if(typeList.getSelectedItem()!=null){
			if(!typeList.getSelectedItem().getValue().toString().equals("All")){
				reportParamsProperties.put("6. Type", typeList.getSelectedItem().getValue().toString());
				type = typeList.getSelectedItem().getValue().toString();
			}
		}
		
		Listbox sortByList = (Listbox)this.getFellow("sortByList");
		if(sortByList.getSelectedItem()!=null){
			reportParamsProperties.put("7. Sort By", sortByList.getSelectedItem().getValue().toString());
			sortBy = sortByList.getSelectedItem().getValue().toString();
		}
		
		//putting the printerBy value in it
		reportParamsProperties.put(Constants.PRINTED_BY, getUserLoginIdAndDomain());
		
		logger.info(report+" Report Parameters:"+reportParamsProperties.toString());
		
		//retrieve format
		Listbox formatList = (Listbox)this.getFellow("reportFormat");
		if(formatList.getSelectedItem()==null) throw new WrongValueException(formatList, "* Mandatory field");
		String extension = (String)formatList.getSelectedItem().getLabel();
		String format = (String)formatList.getSelectedItem().getValue();

		if(format.equals(Constants.FORMAT_CSV)){
			StringBuffer dataInCSV = this.generateCSVData(accountNo, accountName, entity, arControl, outstandingAmount, type, sortBy, getUserLoginIdAndDomain());
			AMedia media = new AMedia(report.replaceAll(" ", "_")+"."+extension, extension, format, dataInCSV.toString());
			Filedownload.save(media);
		}
		else{
			ByteArrayOutputStream os = new ByteArrayOutputStream();
			client.renderReport(repository+"/"+reportCategory+"/"+report+"/"+report+".rml", format, os, reportParamsProperties);
			AMedia media = new AMedia(report.replaceAll(" ", "_")+"."+extension, extension, format, os.toByteArray());
	
			if(format.equals(Constants.FORMAT_EXCEL)){
				Filedownload.save(media);
			}
			else if(format.equals(Constants.FORMAT_PDF)){
				HashMap params = new HashMap();
				params.put("report", media);
				this.forward(Uri.REPORT_RESULT, params);
			}
			
			os.close();
		}
		
		client.close();
	}

	private StringBuffer generateCSVData(String accountNo, String accountName, String entityNo,
			String arControlCodeNo, String outstandingAmount, String type, String sortBy, String printedBy){
		
		StringBuffer sb = new StringBuffer();
		
		//Report Header
		sb.append(Constants.TEXT_QUALIFIER+"Integrated Billing System"+Constants.TEXT_QUALIFIER+",");
		sb.append("\n");
		sb.append(Constants.TEXT_QUALIFIER+"Customer Aging Report (Summary)"+Constants.TEXT_QUALIFIER+",");
		sb.append("\n");
		sb.append(Constants.TEXT_QUALIFIER+"Selection Criteria:"+Constants.TEXT_QUALIFIER+",");
		sb.append("\n");
		
		//Line Break
		sb.append("\n");
		
		if(accountNo!=null && accountNo.length()>0){
			sb.append(Constants.TEXT_QUALIFIER+"Account No: "+accountNo+Constants.TEXT_QUALIFIER+",");
			sb.append("\n");
		}
		if(accountName!=null && accountName.length()>0){
			sb.append(Constants.TEXT_QUALIFIER+"Account Name: "+accountName+Constants.TEXT_QUALIFIER+",");
			sb.append("\n");
		}
		if(entityNo!=null && entityNo.length()>0){
			FmtbEntityMaster entityMaster = (FmtbEntityMaster)MasterSetup.getEntityManager().getMaster(new Integer(entityNo));
			sb.append(Constants.TEXT_QUALIFIER+"Entity: "+entityMaster.getEntityName()+Constants.TEXT_QUALIFIER+",");
			sb.append("\n");
		}
		if(arControlCodeNo!=null && arControlCodeNo.length()>0){
			FmtbArContCodeMaster arContCodeMaster = (FmtbArContCodeMaster)this.businessHelper.getGenericBusiness().get(FmtbArContCodeMaster.class, new Integer(arControlCodeNo));
			sb.append(Constants.TEXT_QUALIFIER+"AR Control Code: "+arContCodeMaster.getArControlCode()+Constants.TEXT_QUALIFIER+",");
			sb.append("\n");
		}
		if(outstandingAmount!=null && outstandingAmount.length()>0){
			sb.append(Constants.TEXT_QUALIFIER+"Outstanding Amount ($): "+outstandingAmount+Constants.TEXT_QUALIFIER+",");
			sb.append("\n");
		}
		if(type!=null && type.length()>0){
			
			String value = "";
			if(type.equals("I")) value = "INVOICE";
			else if(type.equals("R")) value = "RECEIPT";
			else if(type.equals("C")) value = "CREDIT NOTE";
			else if(type.equals("D")) value = "DEBIT NOTE";
			
			sb.append(Constants.TEXT_QUALIFIER+"Type: "+value+Constants.TEXT_QUALIFIER+",");
			sb.append("\n");
		}
		
		//Line Break
		sb.append("\n");
		
		sb.append(Constants.TEXT_QUALIFIER+"Printed By: "+printedBy+Constants.TEXT_QUALIFIER+",");
		sb.append("\n");
		sb.append(Constants.TEXT_QUALIFIER+"Report Date: "+DateUtil.convertDateToStr(DateUtil.getCurrentDate(), DateUtil.GLOBAL_DATE_FORMAT)+Constants.TEXT_QUALIFIER+",");
		sb.append("\n");
		
		//Line Break
		sb.append("\n");
		
		//Column Title
		sb.append(Constants.TEXT_QUALIFIER+"Account No"+Constants.TEXT_QUALIFIER+",");
		sb.append(Constants.TEXT_QUALIFIER+"Account Name"+Constants.TEXT_QUALIFIER+",");
		sb.append(Constants.TEXT_QUALIFIER+"Account Category"+Constants.TEXT_QUALIFIER+",");
		sb.append(Constants.TEXT_QUALIFIER+"Outstanding Amount ($)"+Constants.TEXT_QUALIFIER+",");
		sb.append(Constants.TEXT_QUALIFIER+"Current Amount ($)"+Constants.TEXT_QUALIFIER+",");
		sb.append(Constants.TEXT_QUALIFIER+"1-30 Days ($)"+Constants.TEXT_QUALIFIER+",");
		sb.append(Constants.TEXT_QUALIFIER+"31-60 Days ($)"+Constants.TEXT_QUALIFIER+",");
		sb.append(Constants.TEXT_QUALIFIER+"61-90 Days ($)"+Constants.TEXT_QUALIFIER+",");
		sb.append(Constants.TEXT_QUALIFIER+"91-120 Days ($)"+Constants.TEXT_QUALIFIER+",");
		sb.append(Constants.TEXT_QUALIFIER+"121-150 Days ($)"+Constants.TEXT_QUALIFIER+",");
		sb.append(Constants.TEXT_QUALIFIER+"151-180 Days ($)"+Constants.TEXT_QUALIFIER+",");
		sb.append(Constants.TEXT_QUALIFIER+"181-210 Days ($)"+Constants.TEXT_QUALIFIER+",");
		sb.append(Constants.TEXT_QUALIFIER+"211-360 Days ($)"+Constants.TEXT_QUALIFIER+",");
		sb.append(Constants.TEXT_QUALIFIER+">361 Days ($)"+Constants.TEXT_QUALIFIER+",");
		sb.append(Constants.TEXT_QUALIFIER+"Turnover For Year (Days)"+Constants.TEXT_QUALIFIER+",");
		sb.append(Constants.TEXT_QUALIFIER+"Industry"+Constants.TEXT_QUALIFIER+",");
		sb.append(Constants.TEXT_QUALIFIER+"Sales Person"+Constants.TEXT_QUALIFIER+",");
		sb.append(Constants.TEXT_QUALIFIER+"Prdts Subscription"+Constants.TEXT_QUALIFIER+",");
		sb.append("\n");
		
		//Data
		List<Object[]> rowsOfData = this.businessHelper.getReportBusiness().getCustomerAgingSummary(accountNo, accountName, entityNo, arControlCodeNo, outstandingAmount, type, sortBy);
		for(Object[] columnDataObject : rowsOfData){
			for(Object data : columnDataObject){
				if(data!=null){
					sb.append(""+Constants.TEXT_QUALIFIER+data.toString().replaceAll("\"", "\"\"")+Constants.TEXT_QUALIFIER+",");
				}
				else sb.append(Constants.TEXT_QUALIFIER+Constants.TEXT_QUALIFIER+",");
			}
			sb.append("\n");
		}
		
		if(rowsOfData.size()==0){
			sb.append(Constants.TEXT_QUALIFIER+"No records found"+Constants.TEXT_QUALIFIER+",");
			sb.append("\n");
		}
		
		return sb;
	}
	
	public void clearAccountNameComboBox(){
		Combobox accountNameComboBox = (Combobox)this.getFellow("accountNameComboBox");
		accountNameComboBox.getChildren().clear();
		accountNameComboBox.setText("");
	}
	
	public void reset(){
		((Intbox)this.getFellow("accountNoIntBox")).setText("");
		this.clearAccountNameComboBox();
		((Listbox)this.getFellow("entityList")).setSelectedIndex(0);
		((Listbox)this.getFellow("arControlCodeList")).setSelectedIndex(0);
		((Decimalbox)this.getFellow("outstandingAmtDecimalBox")).setText("");
		((Listbox)this.getFellow("typeList")).setSelectedIndex(0);
		((Listbox)this.getFellow("sortByList")).setSelectedIndex(0);
	}
	
	@Override
	public void refresh() throws InterruptedException {
		// TODO Auto-generated method stub
	}
}