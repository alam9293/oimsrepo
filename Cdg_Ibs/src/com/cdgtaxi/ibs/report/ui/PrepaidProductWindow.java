package com.cdgtaxi.ibs.report.ui;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Set;

import org.apache.commons.httpclient.HttpException;
import org.apache.log4j.Logger;
import org.zkoss.util.media.AMedia;
import org.zkoss.zhtml.Messagebox;
import org.zkoss.zk.ui.Components;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zk.ui.ext.AfterCompose;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Comboitem;
import org.zkoss.zul.Decimalbox;
import org.zkoss.zul.Filedownload;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listitem;

import com.cdgtaxi.ibs.Uri;
import com.cdgtaxi.ibs.common.MasterSetup;
import com.cdgtaxi.ibs.common.NonConfigurableConstants;
import com.cdgtaxi.ibs.common.model.AmtbAccount;
import com.cdgtaxi.ibs.common.model.AmtbAcctType;
import com.cdgtaxi.ibs.common.model.PmtbProductType;
import com.cdgtaxi.ibs.master.model.MstbReportFormatMap;
import com.cdgtaxi.ibs.report.Constants;
import com.cdgtaxi.ibs.util.ComponentUtil;
import com.cdgtaxi.ibs.util.DateUtil;
import com.cdgtaxi.ibs.web.component.Datebox;
import com.elixirtech.net.NetException;

@SuppressWarnings("unchecked")
public class PrepaidProductWindow extends ReportWindow implements AfterCompose{
	
	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(PrepaidProductWindow.class);
	
	private Long reportRsrcId;
	
	private Intbox accountNoIB;
	private Combobox accountNameCB;
	private Listbox accountTypeLB, accountStatusLB, productTypeLB, cardStatusLB, reportFormat, sortByLB, entityLB;
	private Datebox cardExpiryDateFromDB, cardExpiryDateToDB;
	private Decimalbox cardNoFromDMB, cardNoToDMB;
	
	public PrepaidProductWindow() throws IOException{
		super("Prepaid Product","Prepaid");
		reportRsrcId = new Long(this.getHttpServletRequest().getParameter("rsrcId"));
	}
	
	public void afterCompose() {
		Components.wireVariables(this, this);
		
		List accountTypes = this.businessHelper.getGenericBusiness().getAll(AmtbAcctType.class);
		Collections.sort(accountTypes, new Comparator(){
			public int compare(Object obj1, Object obj2) {
				return ((AmtbAcctType)obj1).getAcctType().compareTo(((AmtbAcctType)obj2).getAcctType());
			}
		});
		accountTypeLB.appendChild(ComponentUtil.createNotRequiredListItem());
		accountTypeLB.setSelectedIndex(0);
		for(AmtbAcctType acctType : (List<AmtbAcctType>)accountTypes){
			accountTypeLB.appendChild(new Listitem(acctType.getAcctType(), acctType.getAcctTypeNo()));
		}
		
		accountStatusLB.appendChild(ComponentUtil.createNotRequiredListItem());
		accountStatusLB.setSelectedIndex(0);
		Set<Entry<String,String>> accountStatusEntries = NonConfigurableConstants.ACCOUNT_STATUS.entrySet();
		for(Entry<String,String> entry : accountStatusEntries){
			accountStatusLB.appendChild(new Listitem(entry.getValue(), entry.getKey()));
		}
		
		List productTypes = this.businessHelper.getProductTypeBusiness().getPrepaidProductType();
		Collections.sort(productTypes, new Comparator(){
			public int compare(Object obj1, Object obj2) {
				return ((PmtbProductType)obj1).getName().compareTo(((PmtbProductType)obj2).getName());
			}
		});
		for(PmtbProductType productType : (List<PmtbProductType>)productTypes){
			productTypeLB.appendChild(new Listitem(productType.getName(), productType.getProductTypeId()));
		}
		productTypeLB.setSelectedIndex(0);
		
		cardStatusLB.appendChild(ComponentUtil.createNotRequiredListItem());
		cardStatusLB.setSelectedIndex(0);
		Set<Entry<String,String>> productStatusEntries = NonConfigurableConstants.PRODUCT_STATUS.entrySet();
		for(Entry<String, String> entry : productStatusEntries){
			if(entry.getKey().equals(NonConfigurableConstants.PRODUCT_STATUS_NEW)) continue;
			cardStatusLB.appendChild(new Listitem(entry.getValue(), entry.getKey()));
		}
		
		sortByLB.appendItem("ACCOUNT NAME", "1");
		
		entityLB.appendChild(ComponentUtil.createNotRequiredListItem());
		entityLB.setSelectedIndex(0);
		Map<Integer, String> entities = MasterSetup.getEntityManager().getAllMasters();
		for(Integer entityNo : entities.keySet()){
			entityLB.appendChild(new Listitem(entities.get(entityNo),entityNo.toString()));
		}
	}
	
	public void onSelectAccountName() throws InterruptedException{
		logger.info("");

		try{
			if(accountNameCB.getSelectedItem()!=null){
				AmtbAccount selectedAccount = (AmtbAccount)accountNameCB.getSelectedItem().getValue();
				accountNoIB.setText(selectedAccount.getCustNo());
			}
		}
		catch(Exception e){
			Messagebox.show(com.cdgtaxi.ibs.common.NonConfigurableConstants.COMMON_ERROR_MESSAGE,
					"Error", Messagebox.OK, Messagebox.ERROR);
			e.printStackTrace();
		}
	}
	
	public void searchBillableAccountByAccountNo() throws InterruptedException{
		logger.info("");

		Integer accountNo = accountNoIB.getValue();
		
		if(accountNo==null) return;
		
		//accountName still the same as selected one, skip
		if(accountNameCB.getSelectedItem()!=null){
			AmtbAccount selectedAccount = (AmtbAccount)accountNameCB.getSelectedItem().getValue();
			if(accountNo.toString().equals(selectedAccount.getCustNo())) {
				return;
			}
		}

		//Clear combobox for a new search
		accountNameCB.setText("");
		//Clear list for every new search
		accountNameCB.getChildren().clear();

		try{
			List<AmtbAccount> accounts = this.businessHelper.getPaymentBusiness().searchBillableAccount(accountNo.toString(), null);
			for(AmtbAccount account : accounts){
				Comboitem item = new Comboitem(account.getAccountName()+" ("+account.getCustNo()+")");
				item.setValue(account);
				accountNameCB.appendChild(item);
			}
			if(accounts.size()==1) {
				accountNameCB.setSelectedIndex(0);
				this.onSelectAccountName();
			}
		}
		catch(Exception e){
			Messagebox.show(com.cdgtaxi.ibs.common.NonConfigurableConstants.COMMON_ERROR_MESSAGE,
					"Error", Messagebox.OK, Messagebox.ERROR);
			e.printStackTrace();
		}
	}

	public void searchBillableAccountByAccountName(String name) throws InterruptedException{
		logger.info("");
		
		//only begin new search if input is greater than 2
		if(name.length()<3) {
			return;
		}

		//accountName still the same as selected one, skip
		if(accountNameCB.getSelectedItem()!=null){
			AmtbAccount selectedAccount = (AmtbAccount)accountNameCB.getSelectedItem().getValue();
			if(name.equals(selectedAccount.getAccountName()+" ("+selectedAccount.getCustNo()+")")) {
				return;
			}
		}

		//clear textbox for a new search
		accountNoIB.setText("");
		//Clear list for every new search
		accountNameCB.getChildren().clear();

		try{
			List<AmtbAccount> accounts = this.businessHelper.getPaymentBusiness().searchBillableAccount(null, name);
			for(AmtbAccount account : accounts){
				Comboitem item = new Comboitem(account.getAccountName()+" ("+account.getCustNo()+")");
				item.setValue(account);
				accountNameCB.appendChild(item);
			}
			if(accounts.size()==1){
				accountNameCB.setSelectedIndex(0);
				this.onSelectAccountName();
			}
			else accountNameCB.open();
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
		
		Properties reportParamsProperties = new Properties();
		reportParamsProperties.put(Constants.PRINTED_BY, getUserLoginIdAndDomain());
		
		if(accountNameCB.getSelectedItem()!=null)
			reportParamsProperties.put("accountName", ((AmtbAccount)accountNameCB.getSelectedItem().getValue()).getAccountName());
		else if(accountNameCB.getText()!=null && accountNameCB.getText().length()>0)
			reportParamsProperties.put("accountName", accountNameCB.getText().toUpperCase());
		else
			reportParamsProperties.put("accountName", "");
		
		if(accountNoIB.getValue()!=null) reportParamsProperties.put("custNo", accountNoIB.getValue().toString());
		else reportParamsProperties.put("custNo", "");
		
		if(accountStatusLB.getSelectedItem().getValue().toString().length()>0){
			String status = accountStatusLB.getSelectedItem().getValue().toString();
			reportParamsProperties.put("accountStatus", status);
			reportParamsProperties.put("accountStatusLabel", NonConfigurableConstants.ACCOUNT_STATUS.get(status));
		}
		else{
			reportParamsProperties.put("accountStatus", "");
			reportParamsProperties.put("accountStatusLabel", "");
		}
		
		if(accountTypeLB.getSelectedItem().getValue().toString().length()>0)
			reportParamsProperties.put("accountType", accountTypeLB.getSelectedItem().getValue().toString());
		else
			reportParamsProperties.put("accountType", "");
			
		if(cardNoFromDMB.getValue()!=null && cardNoToDMB.getValue()!=null){
			if(cardNoFromDMB.getValue().compareTo(cardNoToDMB.getValue()) > 0)
				throw new WrongValueException(cardNoFromDMB, "Card No From cannot be greater than Card No To");
			
			BigDecimal cardNoFrom = cardNoFromDMB.getValue();
			BigDecimal cardNoTo = cardNoToDMB.getValue();
			reportParamsProperties.put("cardNoFrom", cardNoFrom.toString());
			reportParamsProperties.put("cardNoTo", cardNoTo.toString());
		}
		else if(cardNoFromDMB.getValue()!=null || cardNoToDMB.getValue()!=null){
			BigDecimal cardNoFrom = cardNoFromDMB.getValue();
			BigDecimal cardNoTo = cardNoToDMB.getValue();
			cardNoFrom = cardNoFrom==null?cardNoTo:cardNoFrom;
			cardNoTo = cardNoTo==null?cardNoFrom:cardNoTo;
			reportParamsProperties.put("cardNoFrom", cardNoFrom.toString());
			reportParamsProperties.put("cardNoTo", cardNoTo.toString());
		}
		else{
			reportParamsProperties.put("cardNoFrom", "");
			reportParamsProperties.put("cardNoTo", "");
		}
		
		if(cardStatusLB.getSelectedItem().getValue().toString().length()>0){
			String status = cardStatusLB.getSelectedItem().getValue().toString();
			reportParamsProperties.put("cardStatus", status);
			reportParamsProperties.put("cardStatusLabel", NonConfigurableConstants.PRODUCT_STATUS.get(status));
		}
		else{
			reportParamsProperties.put("cardStatus", "");
			reportParamsProperties.put("cardStatusLabel", "");
		}
		
		if(entityLB.getSelectedItem().getValue().toString().length()>0){
			String entityNo = entityLB.getSelectedItem().getValue().toString();
			reportParamsProperties.put("entityNo", entityNo);
		}
		else{
			reportParamsProperties.put("entityNo", "");
		}
		
		if(productTypeLB.getSelectedItem().getValue().toString().length()>0)
			reportParamsProperties.put("prdTypeId", productTypeLB.getSelectedItem().getValue().toString());
		else
			reportParamsProperties.put("prdTypeId", "");
		
		if(cardExpiryDateFromDB.getValue()!=null && cardExpiryDateToDB.getValue()!=null){
			Date expiryDateFrom = cardExpiryDateFromDB.getValue();
			Date expiryDateTo = cardExpiryDateToDB.getValue();
			
			if(expiryDateFrom.after(expiryDateTo))
				throw new WrongValueException(cardExpiryDateFromDB, "Expiry Date From cannot be later than Expiry Date To");
			
			reportParamsProperties.put("expiryDateFrom", DateUtil.convertDateToStr(expiryDateFrom, DateUtil.REPORT_DATE_FORMAT));
			reportParamsProperties.put("expiryDateTo", DateUtil.convertDateToStr(expiryDateTo, DateUtil.REPORT_DATE_FORMAT));
		}
		else if(cardExpiryDateFromDB.getValue()!=null || cardExpiryDateToDB.getValue()!=null){
			Date expiryDateFrom = cardExpiryDateFromDB.getValue();
			Date expiryDateTo = cardExpiryDateToDB.getValue();
			expiryDateFrom = expiryDateFrom==null?expiryDateTo:expiryDateFrom;
			expiryDateTo = expiryDateTo==null?expiryDateFrom:expiryDateTo;
			reportParamsProperties.put("expiryDateFrom", DateUtil.convertDateToStr(expiryDateFrom, DateUtil.REPORT_DATE_FORMAT));
			reportParamsProperties.put("expiryDateTo", DateUtil.convertDateToStr(expiryDateTo, DateUtil.REPORT_DATE_FORMAT));
		}
		else{
			reportParamsProperties.put("expiryDateFrom", "");
			reportParamsProperties.put("expiryDateTo", "");
		}
		
		//retrieve format
		if(reportFormat.getSelectedItem()==null) throw new WrongValueException(reportFormat, "* Mandatory field");
		String extension = (String)reportFormat.getSelectedItem().getLabel();
		String format = (String)reportFormat.getSelectedItem().getValue();

		this.displayProcessing();
		
		if(format.equals(Constants.FORMAT_CSV)){
			StringBuffer dataInCSV = this.generateCSVData(reportParamsProperties);
			AMedia media = new AMedia("Prepaid_Product"+"."+extension, extension, format, dataInCSV.toString());
			Filedownload.save(media);
		}
		else{
			ByteArrayOutputStream os = new ByteArrayOutputStream();
			getClient().renderReport(getRepository()+"/"+getReportCategory()+"/"+getReport()+"/"+getReport()+".rml", format, os, reportParamsProperties);
			AMedia media = new AMedia(getReport().replaceAll(" ", "_")+"."+extension, extension, format, os.toByteArray());
			
			HashMap params = new HashMap();
			params.put("report", media);
			this.forward(Uri.REPORT_RESULT, params);
			
			os.close();
			getClient().close();
		}
	}

	private StringBuffer generateCSVData(Properties reportParamsProperties){
			
		StringBuffer sb = new StringBuffer();
		
		//Report Header
		sb.append(Constants.TEXT_QUALIFIER+"Integrated Billing System"+Constants.TEXT_QUALIFIER+",");
		sb.append("\n");
		sb.append(Constants.TEXT_QUALIFIER+"Prepaid Product"+Constants.TEXT_QUALIFIER+",");
		sb.append("\n");
		sb.append(Constants.TEXT_QUALIFIER+"Selection Criteria:"+Constants.TEXT_QUALIFIER+",");
		sb.append("\n");
		
		//Line Break
		sb.append("\n");
		
		String productTypeId = (String)reportParamsProperties.get("prdTypeId");
		if(productTypeId!=null && productTypeId.length()>0){
			PmtbProductType productType = (PmtbProductType)this.businessHelper.getGenericBusiness().get(PmtbProductType.class, productTypeId);
			sb.append(Constants.TEXT_QUALIFIER+"Product Type: "+productType.getName()+Constants.TEXT_QUALIFIER+",");
			sb.append("\n");
		}
		
		String expiryDateFrom = (String)reportParamsProperties.get("expiryDateFrom");
		String expiryDateTo = (String)reportParamsProperties.get("expiryDateTo");
		if(expiryDateFrom!=null && expiryDateFrom.length()>0){
			sb.append(Constants.TEXT_QUALIFIER+"Card Expiry: "+Constants.TEXT_QUALIFIER+",");
			sb.append(Constants.TEXT_QUALIFIER+expiryDateFrom+Constants.TEXT_QUALIFIER+",");
			sb.append(Constants.TEXT_QUALIFIER+"To"+Constants.TEXT_QUALIFIER+",");
			sb.append(Constants.TEXT_QUALIFIER+expiryDateTo+Constants.TEXT_QUALIFIER+",");
			sb.append("\n");
		}
		
		String cardStatus = (String)reportParamsProperties.get("cardStatus");
		String cardStatusLabel = (String)reportParamsProperties.get("cardStatusLabel");
		if(cardStatus!=null && cardStatus.length()>0){
			sb.append(Constants.TEXT_QUALIFIER+"Card Status: "+cardStatusLabel+Constants.TEXT_QUALIFIER+",");
			sb.append("\n");
		}
		
		String cardNoFrom = (String)reportParamsProperties.get("cardNoFrom");
		String cardNoTo = (String)reportParamsProperties.get("cardNoTo");
		if(cardNoFrom!=null && cardNoFrom.length()>0){
			sb.append(Constants.TEXT_QUALIFIER+"Card No: "+Constants.TEXT_QUALIFIER+",");
			sb.append(Constants.TEXT_QUALIFIER+"'"+cardNoFrom+Constants.TEXT_QUALIFIER+",");
			sb.append(Constants.TEXT_QUALIFIER+"To"+Constants.TEXT_QUALIFIER+",");
			sb.append(Constants.TEXT_QUALIFIER+"'"+cardNoTo+Constants.TEXT_QUALIFIER+",");
			sb.append("\n");
		}
		
		String accountTypeNo = (String)reportParamsProperties.get("accountType");
		if(accountTypeNo!=null && accountTypeNo.length()>0){
			AmtbAcctType acctType = (AmtbAcctType)this.businessHelper.getGenericBusiness().get(AmtbAcctType.class, new Integer(accountTypeNo));
			sb.append(Constants.TEXT_QUALIFIER+"Account Type: "+acctType.getAcctType()+Constants.TEXT_QUALIFIER+",");
			sb.append("\n");
		}
		
		String custNo = (String)reportParamsProperties.get("custNo");
		if(custNo!=null && custNo.length()>0){
			sb.append(Constants.TEXT_QUALIFIER+"Account No: "+custNo+Constants.TEXT_QUALIFIER+",");
			sb.append("\n");
		}
		
		String accountName = (String)reportParamsProperties.get("accountName");
		if(accountName!=null && accountName.length()>0){
			sb.append(Constants.TEXT_QUALIFIER+"Account Name: "+accountName+Constants.TEXT_QUALIFIER+",");
			sb.append("\n");
		}
		
		String accountStatus = (String)reportParamsProperties.get("accountStatus");
		String accountStatusLabel = (String)reportParamsProperties.get("accountStatusLabel");
		if(accountStatus!=null && accountStatus.length()>0){
			sb.append(Constants.TEXT_QUALIFIER+"Account Status: "+accountStatusLabel+Constants.TEXT_QUALIFIER+",");
			sb.append("\n");
		}
		
		String entityNo = (String)reportParamsProperties.get("entityNo");
		if(entityNo!=null && entityNo.length()>0) {
			String entityName = ((Listbox)this.getFellow("entityLB")).getSelectedItem().getLabel();
			sb.append(Constants.TEXT_QUALIFIER+"Entity: "+entityName+Constants.TEXT_QUALIFIER+",");
			sb.append("\n");
		}
		
		//Line Break
		sb.append("\n");
		
		sb.append(Constants.TEXT_QUALIFIER+"Printed By: "+getUserLoginIdAndDomain()+Constants.TEXT_QUALIFIER+",");
		sb.append("\n");
		sb.append(Constants.TEXT_QUALIFIER+"Report Date: "+DateUtil.convertDateToStr(DateUtil.getCurrentDate(), DateUtil.GLOBAL_DATE_FORMAT)+Constants.TEXT_QUALIFIER+",");
		sb.append("\n");
		
		//Line Break
		sb.append("\n");
		
		//Column Title
		sb.append(Constants.TEXT_QUALIFIER+"S/N"+Constants.TEXT_QUALIFIER+",");
		sb.append(Constants.TEXT_QUALIFIER+"Account No"+Constants.TEXT_QUALIFIER+",");
		sb.append(Constants.TEXT_QUALIFIER+"Account Name"+Constants.TEXT_QUALIFIER+",");
		sb.append(Constants.TEXT_QUALIFIER+"Account Type"+Constants.TEXT_QUALIFIER+",");
		sb.append(Constants.TEXT_QUALIFIER+"Account Status"+Constants.TEXT_QUALIFIER+",");
		sb.append(Constants.TEXT_QUALIFIER+"Card No"+Constants.TEXT_QUALIFIER+",");
		sb.append(Constants.TEXT_QUALIFIER+"Card Paid Value($)"+Constants.TEXT_QUALIFIER+",");
		sb.append(Constants.TEXT_QUALIFIER+"Value Plus($)"+Constants.TEXT_QUALIFIER+",");
		sb.append(Constants.TEXT_QUALIFIER+"Transfer Card No From"+Constants.TEXT_QUALIFIER+",");
		sb.append(Constants.TEXT_QUALIFIER+"Transfer Card Value From($)"+Constants.TEXT_QUALIFIER+",");
		sb.append(Constants.TEXT_QUALIFIER+"Transfer Card No To"+Constants.TEXT_QUALIFIER+",");
		sb.append(Constants.TEXT_QUALIFIER+"Transfer Card Value To($)"+Constants.TEXT_QUALIFIER+",");
		sb.append(Constants.TEXT_QUALIFIER+"Transfer Card Date"+Constants.TEXT_QUALIFIER+",");
		sb.append(Constants.TEXT_QUALIFIER+"Value Used($)"+Constants.TEXT_QUALIFIER+",");
		sb.append(Constants.TEXT_QUALIFIER+"Charges($)"+Constants.TEXT_QUALIFIER+",");
		sb.append(Constants.TEXT_QUALIFIER+"Card Balance($)"+Constants.TEXT_QUALIFIER+",");
		sb.append(Constants.TEXT_QUALIFIER+"Card Expiry"+Constants.TEXT_QUALIFIER+",");
		sb.append(Constants.TEXT_QUALIFIER+"Expired Value($)"+Constants.TEXT_QUALIFIER+",");
		sb.append(Constants.TEXT_QUALIFIER+"Card Status"+Constants.TEXT_QUALIFIER+",");
		
		sb.append("\n");
		
		//Data
		List<Object[]> rowsOfData = this.businessHelper.getReportBusiness().getPrepaidProduct(custNo, accountName, 
				productTypeId, expiryDateFrom, expiryDateTo, cardStatus, cardNoFrom, cardNoTo, accountTypeNo, accountStatus, entityNo);
		
		int serialNoCounter = 0;
		for(Object[] columnDataObject : rowsOfData){
			serialNoCounter++;
			sb.append(""+Constants.TEXT_QUALIFIER+serialNoCounter+Constants.TEXT_QUALIFIER+",");
			for(int i=0; i<columnDataObject.length; i++){
				Object data = columnDataObject[i];
				
				if(i==4 || i==7 || i==9){
					if(data!=null) sb.append(""+Constants.TEXT_QUALIFIER+"'"+data.toString().replaceAll("\"", "\"\"")+Constants.TEXT_QUALIFIER+",");
					else sb.append(Constants.TEXT_QUALIFIER+Constants.TEXT_QUALIFIER+",");
				}
				else if(i==14){
					Object valueUsed = columnDataObject[12];
					Object cardStatusData = columnDataObject[17];
					Object cardBalance = data;
					
					if(cardStatusData!=null && cardStatusData.toString().equals("TERMINATED"))
						cardBalance = BigDecimal.ZERO;
					else
						if(valueUsed!=null && cardBalance!=null) cardBalance = new BigDecimal((String)cardBalance).subtract(new BigDecimal((String)valueUsed));
					

					if(cardBalance!=null) sb.append(""+Constants.TEXT_QUALIFIER+cardBalance.toString().replaceAll("\"", "\"\"")+Constants.TEXT_QUALIFIER+",");
					else sb.append(Constants.TEXT_QUALIFIER+Constants.TEXT_QUALIFIER+",");
				}
				else if(i==16){
					Object expiredValue = data;
					Object cardStatusData = columnDataObject[17];
					Object valueUsed = columnDataObject[12];
					Object cardBalance = columnDataObject[14];
					
					if(cardStatusData!=null && cardStatusData.toString().equals("TERMINATED")){
						if(valueUsed!=null && cardBalance!=null) cardBalance = new BigDecimal((String)cardBalance).subtract(new BigDecimal((String)valueUsed));
						if(cardBalance!=null && new BigDecimal(cardBalance.toString()).compareTo(BigDecimal.ZERO) != 0)
							expiredValue = cardBalance;
					}
					
					if(expiredValue!=null) sb.append(""+Constants.TEXT_QUALIFIER+expiredValue.toString().replaceAll("\"", "\"\"")+Constants.TEXT_QUALIFIER+",");
					else sb.append(Constants.TEXT_QUALIFIER+Constants.TEXT_QUALIFIER+",");
				}
				else{
					if(data!=null) sb.append(""+Constants.TEXT_QUALIFIER+data.toString().replaceAll("\"", "\"\"")+Constants.TEXT_QUALIFIER+",");
					else sb.append(Constants.TEXT_QUALIFIER+Constants.TEXT_QUALIFIER+",");
				}
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
		accountNameCB.getChildren().clear();
		accountNameCB.setText("");
	}
	
	public void reset(){
		accountNoIB.setText("");
		this.clearAccountNameComboBox();
		accountTypeLB.setSelectedIndex(0);
		accountStatusLB.setSelectedIndex(0);
		productTypeLB.setSelectedIndex(0);
		cardStatusLB.setSelectedIndex(0);
		entityLB.setSelectedIndex(0);
		reportFormat.setSelectedIndex(0);
		cardExpiryDateFromDB.setText("");
		cardExpiryDateToDB.setText("");
		cardNoFromDMB.setText("");
		cardNoToDMB.setText("");
	}
	
	@Override
	public void refresh() throws InterruptedException {
		// TODO Auto-generated method stub
	}
}