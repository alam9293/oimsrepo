package com.cdgtaxi.ibs.acct.ui;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import org.apache.log4j.Logger;
import org.springframework.orm.hibernate5.HibernateOptimisticLockingFailureException;
import org.zkoss.util.media.AMedia;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.SuspendNotAllowedException;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zul.Button;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Filedownload;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Label;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Row;
import org.zkoss.zul.Rows;
import org.zkoss.zul.Window;

import com.cdgtaxi.ibs.Uri;
import com.cdgtaxi.ibs.common.ConfigurableConstants;
import com.cdgtaxi.ibs.common.NonConfigurableConstants;
import com.cdgtaxi.ibs.common.ui.CommonWindow;
import com.cdgtaxi.ibs.exporter.PdfExportItem;
import com.cdgtaxi.ibs.exporter.ZkPdfExporter;
import com.cdgtaxi.ibs.interfaces.cnii.CniiInterfaceException;
import com.cdgtaxi.ibs.report.Constants;
import com.cdgtaxi.ibs.util.DateUtil;
import com.cdgtaxi.ibs.util.StringUtil;
import com.elixirtech.net.NetException;
import com.lowagie.text.PageSize;

public class ViewPersWindow extends CommonInnerAcctWindow {
	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(ViewCorpWindow.class);
	private String custNo, createdDt;
	
	@SuppressWarnings("unchecked")
	public ViewPersWindow() throws InterruptedException{
		logger.info("ViewPersWindow()");
		//account number
		Map<String, String> map = Executions.getCurrent().getArg();
		custNo = map.get("custNo");
		createdDt = map.get("createdDt");
		if(custNo==null || custNo.trim().length()==0){
			Messagebox.show("No account number found!", "View Corporate", Messagebox.OK, Messagebox.ERROR);
		}
		
	}
	@Override
	public void refresh() throws InterruptedException {
		init();
	}
	public void init(){
		Map<String, Object> accountDetails = this.businessHelper.getAccountBusiness().searchAccount(custNo);
		displayCommonFields(accountDetails);
		displayPersFields(accountDetails);
		// for subscriptions
		Listbox listbox = (Listbox)this.getFellow("prodSubscriptions");
		listbox.getItems().clear();
		Set<Map<String, String>> prodSubscriptions = this.businessHelper.getAccountBusiness().getProductSubscriptions(custNo, null);
		for(Map<String, String> prodSubscription : prodSubscriptions){
			Listitem item = new Listitem();
			item.appendChild(new Listcell(prodSubscription.get("prodType")==null ? "-" : prodSubscription.get("prodType")));
			item.appendChild(new Listcell(prodSubscription.get("prodDiscount")==null ? "-" : prodSubscription.get("prodDiscount")));
			item.appendChild(new Listcell(prodSubscription.get("rewards")==null ? "-" : prodSubscription.get("rewards")));
			item.appendChild(new Listcell(prodSubscription.get("subscribeFee")==null ? "-" : prodSubscription.get("subscribeFee")));
			item.appendChild(new Listcell(prodSubscription.get("issuanceFee")==null ? "-" : prodSubscription.get("issuanceFee")));
			Listcell checkCell = new Listcell();
			checkCell.appendChild(new Checkbox());
			item.appendChild(checkCell);
			item.setValue(prodSubscription.get("prodTypeId"));
			listbox.appendChild(item);
		}

		if(!this.checkUriAccess(Uri.ADD_PROD_SUBSC)){
			((Button)this.getFellow("subscribeBtn")).setDisabled(true);
			((Button)this.getFellow("unsubscribeBtn")).setDisabled(true);
		}
		if(!this.checkUriAccess(Uri.VIEW_STATUS_HISTORY))
			((Button)this.getFellow("viewStatusHistBtn")).setDisabled(true);
		if(!this.checkUriAccess(Uri.VIEW_CREDIT_REVIEW))
			((Button)this.getFellow("viewCreditLimitHistBtn")).setDisabled(true);
		if(!this.checkUriAccess(Uri.CREDIT_REVIEW_PERS))
			((Button)this.getFellow("creditReviewBtn")).setDisabled(true);
		if(!this.checkUriAccess(Uri.EDIT_PERS_DETAILS))
			((Button)this.getFellow("editBtn")).setDisabled(true);
		if(!this.checkUriAccess(Uri.SUSPEND_PERS))
			((Button)this.getFellow("suspendBtn")).setDisabled(true);
		if(!this.checkUriAccess(Uri.REACTIVATE_PERS))
			((Button)this.getFellow("reactivateBtn")).setDisabled(true);
		if(!this.checkUriAccess(Uri.TERMINATE_PERS))
			((Button)this.getFellow("terminateBtn")).setDisabled(true);
		if(!this.checkUriAccess(Uri.EINVOICE_EMAIL))
			((Button)this.getFellow("eInvoiceEmailBtn")).setDisabled(true);
	}
	private void displayCommonFields(Map<String, Object> accountDetails){
		// common details
		((Label)this.getFellow("custNo")).setValue((String)accountDetails.get("custNo"));
		((Label)this.getFellow("acctStatus")).setValue((String)accountDetails.get("acctStatus"));
		enableAllBtns();
		if(accountDetails.get("acctStatus").equals(NonConfigurableConstants.ACCOUNT_STATUS.get(NonConfigurableConstants.ACCOUNT_STATUS_SUSPENDED))){
			((Button)this.getFellow("suspendBtn")).setDisabled(true);
		}
		if(accountDetails.get("acctStatus").equals(NonConfigurableConstants.ACCOUNT_STATUS.get(NonConfigurableConstants.ACCOUNT_STATUS_ACTIVE))){
			((Button)this.getFellow("reactivateBtn")).setDisabled(true);
		}
		if(accountDetails.get("acctStatus").equals(NonConfigurableConstants.ACCOUNT_STATUS.get(NonConfigurableConstants.ACCOUNT_STATUS_CLOSED)) || 
				accountDetails.get("acctStatus").equals(NonConfigurableConstants.ACCOUNT_STATUS.get(NonConfigurableConstants.ACCOUNT_STATUS_TERMINATED))){
			((Button)this.getFellow("editBtn")).setDisabled(true);
			((Button)this.getFellow("creditReviewBtn")).setDisabled(true);
			((Button)this.getFellow("suspendBtn")).setDisabled(true);
			((Button)this.getFellow("reactivateBtn")).setDisabled(true);
			((Button)this.getFellow("subscribeBtn")).setDisabled(true);
			((Button)this.getFellow("unsubscribeBtn")).setDisabled(true);
			if(accountDetails.get("acctStatus").equals(NonConfigurableConstants.ACCOUNT_STATUS.get(NonConfigurableConstants.ACCOUNT_STATUS_CLOSED))){
				((Button)this.getFellow("terminateBtn")).setDisabled(true);
				((Button)this.getFellow("refundDepositBtn")).setDisabled(false);
			}
		}
		if(accountDetails.get("acctStatus").equals(NonConfigurableConstants.ACCOUNT_STATUS.get(NonConfigurableConstants.ACCOUNT_STATUS_CLOSED))){
			((Button)this.getFellow("refundDepositBtn")).setDisabled(false);
		}
		((Label)this.getFellow("acctType")).setValue((String)accountDetails.get("acctType"));
		((Label)this.getFellow("acctName")).setValue((String)accountDetails.get("acctName"));
		((Label)this.getFellow("nameOnCard")).setValue((String)accountDetails.get("nameOnCard"));
		if(accountDetails.get("eInvoice")!=null){
			((Label)this.getFellow("eInvoice")).setValue(NonConfigurableConstants.BOOLEAN_YN.get(accountDetails.get("eInvoice")));
		}else{
			((Label)this.getFellow("eInvoice")).setValue("-");
		}
		if(accountDetails.get("invoicePrinting")!=null){
			((Label)this.getFellow("invoicePrinting")).setValue(NonConfigurableConstants.INVOICE_PRINTING.get(accountDetails.get("invoicePrinting")));
		}else{
			((Label)this.getFellow("invoicePrinting")).setValue("-");
		}
		if(accountDetails.get("outsourcePrinting")!=null){
			((Label)this.getFellow("outsourcePrinting")).setValue(NonConfigurableConstants.BOOLEAN_YN.get(accountDetails.get("outsourcePrinting")));
		}else{
			((Label)this.getFellow("outsourcePrinting")).setValue("-");
		}
		if(accountDetails.get("sms")!=null){
			((Label)this.getFellow("sms")).setValue(NonConfigurableConstants.BOOLEAN_YN.get(accountDetails.get("sms")));
		}else{
			((Label)this.getFellow("sms")).setValue("-");
		}
		if(accountDetails.get("recurring")!=null){
			((Label)this.getFellow("recurring")).setValue(NonConfigurableConstants.BOOLEAN_YN.get(accountDetails.get("recurring")));
		}else{
			((Label)this.getFellow("recurring")).setValue("-");
		}
		if(accountDetails.get("recurringChargeDay")!=null){
			((Label)this.getFellow("recurringChargeDay")).setValue(NonConfigurableConstants.DAYSwithSuffix.get((accountDetails.get("recurringChargeDay")).toString()));
		}else{
			((Label)this.getFellow("recurringChargeDay")).setValue("-");
		}
		if(accountDetails.get("smsExpiry")!=null){
			((Label)this.getFellow("smsExpiry")).setValue(NonConfigurableConstants.BOOLEAN_YN.get(accountDetails.get("smsExpiry")));
		}else{
			((Label)this.getFellow("smsExpiry")).setValue("-");
		}
		if(accountDetails.get("smsTopUp")!=null){
			((Label)this.getFellow("smsTopUp")).setValue(NonConfigurableConstants.BOOLEAN_YN.get(accountDetails.get("smsTopUp")));
		}else{
			((Label)this.getFellow("smsTopUp")).setValue("-");
		}
		((Label)this.getFellow("entity")).setValue((String)accountDetails.get("entity"));
		((Label)this.getFellow("arAcct")).setValue((String)accountDetails.get("arAcct"));
		((Label)this.getFellow("approver")).setValue((String)accountDetails.get("approver"));
		((Label)this.getFellow("creditLimit")).setValue((String)accountDetails.get("creditLimit"));
		((Label)this.getFellow("creditBalance")).setValue((String)accountDetails.get("creditBalance"));
		if(accountDetails.get("tempCreditLimit")!=null){
			((Label)this.getFellow("tempCreditLimit")).setValue((String)accountDetails.get("tempCreditLimit"));
		}else{
			((Label)this.getFellow("tempCreditLimit")).setValue("-");
		}
		((Label)this.getFellow("deposit")).setValue((String)accountDetails.get("deposit"));
		((Label)this.getFellow("salesPerson")).setValue((String)accountDetails.get("salesPerson"));
		((Label)this.getFellow("remarks")).setValue((String)accountDetails.get("remarks"));
		if(accountDetails.get("infoSourceCode")!=null){
			String infoSource = ConfigurableConstants.getInformationSources().get(accountDetails.get("infoSourceCode"));
			((Label)this.getFellow("infoSource")).setValue(infoSource);
		}else{
			((Label)this.getFellow("infoSource")).setValue("-");
		}
		((Label)this.getFellow("createdDate")).setValue((String)accountDetails.get("createdDate"));
		if(accountDetails.get("lastUpdatedBy")!=null){
			((Label)this.getFellow("lastUpdatedBy")).setValue((String)accountDetails.get("lastUpdatedBy"));
			((Label)this.getFellow("lastUpdatedDate")).setValue((String)accountDetails.get("lastUpdatedDate"));
			((Label)this.getFellow("lastUpdatedTime")).setValue((String)accountDetails.get("lastUpdatedTime"));
		}
		
		if(accountDetails.get("overdueReminder")!=null)
			((Label)this.getFellow("overdueReminder")).setValue(NonConfigurableConstants.BOOLEAN_YN.get(accountDetails.get("overdueReminder")));
		else
			((Label)this.getFellow("overdueReminder")).setValue("-");
		
		if(accountDetails.get("reminderEmail")!= null)
			((Label)this.getFellow("reminderEmail")).setValue(checkReminderEmail((String)accountDetails.get("reminderEmail")));
		else
			((Label)this.getFellow("reminderEmail")).setValue("-");
		
		if(accountDetails.get("eInvoiceEmail")!= null)
			((Label)this.getFellow("eInvoiceEmail")).setValue(checkPDFEInvoiceEmail((String)accountDetails.get("eInvoiceEmail")));
		else
			((Label)this.getFellow("eInvoiceEmail")).setValue("-");
		
		if(accountDetails.get("eInvoiceEmailFlag")!= null)
			((Label)this.getFellow("eInvoiceEmailFlag")).setValue(NonConfigurableConstants.BOOLEAN_YN.get(accountDetails.get("eInvoiceEmailFlag")));
		else
			((Label)this.getFellow("eInvoiceEmailFlag")).setValue("-");
	}
	private void displayPersFields(Map<String, Object> accountDetails){
		// displaying
		if(accountDetails.get("salutationCode")!=null){
			((Label)this.getFellow("salutation")).setValue(ConfigurableConstants.getSalutations().get(accountDetails.get("salutationCode")));
		}else{
			((Label)this.getFellow("salutation")).setValue("-");
		}
		if(accountDetails.get("race")!=null){
			((Label)this.getFellow("race")).setValue(ConfigurableConstants.getRace().get(accountDetails.get("race")));
		}else{
			((Label)this.getFellow("race")).setValue("-");
		}
		((Label)this.getFellow("nric")).setValue(StringUtil.maskNric((String)accountDetails.get("nric")));
		if(accountDetails.get("birthdate")!=null){
			((Label)this.getFellow("birthdate")).setValue(DateUtil.convertDateToStr((Date)accountDetails.get("birthdate"), DateUtil.GLOBAL_DATE_FORMAT));
		}else{
			((Label)this.getFellow("birthdate")).setValue("-");
		}
		((Label)this.getFellow("tel")).setValue((String)accountDetails.get("tel"));
		if(accountDetails.get("blkNo")!=null){
			((Label)this.getFellow("blkNo")).setValue((String)accountDetails.get("blkNo"));
		}else{
			((Label)this.getFellow("blkNo")).setValue("-");
		}
		if(accountDetails.get("unitNo")!=null){
			((Label)this.getFellow("unitNo")).setValue((String)accountDetails.get("unitNo"));
		}else{
			((Label)this.getFellow("unitNo")).setValue("-");
		}
		((Label)this.getFellow("street")).setValue((String)accountDetails.get("street"));
		if(accountDetails.get("building")!=null){
			((Label)this.getFellow("building")).setValue((String)accountDetails.get("building"));
		}else{
			((Label)this.getFellow("building")).setValue("-");
		}
		if(accountDetails.get("area")!=null){
			((Label)this.getFellow("area")).setValue((String)accountDetails.get("area"));
		}else{
			((Label)this.getFellow("area")).setValue("-");
		}
		((Label)this.getFellow("country")).setValue(ConfigurableConstants.getCountries().get(accountDetails.get("countryCode")));
		if(accountDetails.get("city")!=null){
			((Label)this.getFellow("city")).setValue((String)accountDetails.get("city"));
		}else{
			((Label)this.getFellow("city")).setValue("-");
		}
		if(accountDetails.get("state")!=null){
			((Label)this.getFellow("state")).setValue((String)accountDetails.get("state"));
		}else{
			((Label)this.getFellow("state")).setValue("-");
		}
		((Label)this.getFellow("postal")).setValue((String)accountDetails.get("postal"));
		if(accountDetails.get("empBlkNo")!=null){
			((Label)this.getFellow("empBlkNo")).setValue((String)accountDetails.get("empBlkNo"));
		}else{
			((Label)this.getFellow("empBlkNo")).setValue("-");
		}
		if(accountDetails.get("empUnitNo")!=null){
			((Label)this.getFellow("empUnitNo")).setValue((String)accountDetails.get("empUnitNo"));
		}else{
			((Label)this.getFellow("empUnitNo")).setValue("-");
		}
		((Label)this.getFellow("empStreet")).setValue((String)accountDetails.get("empStreet"));
		if(accountDetails.get("empBuilding")!=null){
			((Label)this.getFellow("empBuilding")).setValue((String)accountDetails.get("empBuilding"));
		}else{
			((Label)this.getFellow("empBuilding")).setValue("-");
		}
		if(accountDetails.get("empArea")!=null){
			((Label)this.getFellow("empArea")).setValue((String)accountDetails.get("empArea"));
		}else{
			((Label)this.getFellow("empArea")).setValue("-");
		}
		((Label)this.getFellow("empCountry")).setValue(ConfigurableConstants.getCountries().get(accountDetails.get("empCountryCode")));
		if(accountDetails.get("empCity")!=null){
			((Label)this.getFellow("empCity")).setValue((String)accountDetails.get("empCity"));
		}else{
			((Label)this.getFellow("empCity")).setValue("-");
		}
		if(accountDetails.get("empState")!=null){
			((Label)this.getFellow("empState")).setValue((String)accountDetails.get("empState"));
		}else{
			((Label)this.getFellow("empState")).setValue("-");
		}
		((Label)this.getFellow("empPostal")).setValue((String)accountDetails.get("empPostal"));
		((Label)this.getFellow("jobStatus")).setValue(ConfigurableConstants.getJobStatuses().get(accountDetails.get("jobStatusCode")));
		((Label)this.getFellow("occupation")).setValue((String)accountDetails.get("occupation"));
		((Label)this.getFellow("employerName")).setValue((String)accountDetails.get("employerName"));
		((Label)this.getFellow("industry")).setValue(ConfigurableConstants.getIndustries().get(accountDetails.get("industryCode")));
		((Label)this.getFellow("monthlyIncome")).setValue(StringUtil.bigDecimalToString((BigDecimal)accountDetails.get("monthlyIncome"), StringUtil.GLOBAL_DECIMAL_FORMAT));
		if(accountDetails.get("empLength")!=null){
			((Label)this.getFellow("empLength")).setValue(((Integer)accountDetails.get("empLength")).toString());
		}else{
			((Label)this.getFellow("empLength")).setValue("-");
		}
		if(accountDetails.get("email")!=null){
			((Label)this.getFellow("email")).setValue((String)accountDetails.get("email"));
		}else{
			((Label)this.getFellow("email")).setValue("-");
		}
		if(accountDetails.get("mobile")!=null){
			((Label)this.getFellow("mobile")).setValue((String)accountDetails.get("mobile"));
		}else{
			((Label)this.getFellow("mobile")).setValue("-");
		}
		if(accountDetails.get("office")!=null){
			((Label)this.getFellow("office")).setValue((String)accountDetails.get("office"));
		}else{
			((Label)this.getFellow("office")).setValue("-");
		}
		// displaying billing address
		if((accountDetails.get("blkNo")==null ? accountDetails.get("billBlkNo")==null : accountDetails.get("blkNo").equals(accountDetails.get("billBlkNo")))
				&& (accountDetails.get("unitNo")==null ? accountDetails.get("billUnitNo")==null : accountDetails.get("unitNo").equals(accountDetails.get("billUnitNo")))
				&& (accountDetails.get("street")==null ? accountDetails.get("billStreet")==null : accountDetails.get("street").equals(accountDetails.get("billStreet")))
				&& (accountDetails.get("building")==null ? accountDetails.get("billBuilding")==null : accountDetails.get("building").equals(accountDetails.get("billBuilding")))
				&& (accountDetails.get("area")==null ? accountDetails.get("billArea")==null : accountDetails.get("area").equals(accountDetails.get("billArea")))
				&& (accountDetails.get("countryCode")==null ? accountDetails.get("billCountryCode")==null : accountDetails.get("countryCode").equals(accountDetails.get("billCountryCode")))
				&& (accountDetails.get("city")==null ? accountDetails.get("billCity")==null : accountDetails.get("city").equals(accountDetails.get("billCity")))
				&& (accountDetails.get("state")==null ? accountDetails.get("billState")==null : accountDetails.get("state").equals(accountDetails.get("billState")))
				&& (accountDetails.get("postal")==null ? accountDetails.get("billPostal")==null : accountDetails.get("postal").equals(accountDetails.get("billPostal")))
		){
			Rows billAddress = (Rows)this.getFellow("billAddress");
			for(Object row : billAddress.getChildren()){
				if(!row.equals(billAddress.getFirstChild())){
					((Row)row).setVisible(false);
				}
			}
			((Label)this.getFellow("billAdd")).setValue(NonConfigurableConstants.BOOLEAN_YN.get(NonConfigurableConstants.BOOLEAN_YES));
		}else{
			Rows billAddress = (Rows)this.getFellow("billAddress");
			for(Object row : billAddress.getChildren()){
				if(!row.equals(billAddress.getFirstChild())){
					((Row)row).setVisible(true);
				}
			}
			((Label)this.getFellow("billAdd")).setValue(NonConfigurableConstants.BOOLEAN_YN.get(NonConfigurableConstants.BOOLEAN_NO));
			((Label)this.getFellow("billBlkNo")).setValue((String)accountDetails.get("billBlkNo"));
			((Label)this.getFellow("billUnitNo")).setValue((String)accountDetails.get("billUnitNo"));
			((Label)this.getFellow("billStreet")).setValue((String)accountDetails.get("billStreet"));
			((Label)this.getFellow("billBuilding")).setValue((String)accountDetails.get("billBuilding"));
			((Label)this.getFellow("billArea")).setValue((String)accountDetails.get("billArea"));
			((Label)this.getFellow("billCountry")).setValue(ConfigurableConstants.getCountries().get((String)accountDetails.get("billCountryCode")));
			((Label)this.getFellow("billCity")).setValue((String)accountDetails.get("billCity"));
			((Label)this.getFellow("billState")).setValue((String)accountDetails.get("billState"));
			((Label)this.getFellow("billPostal")).setValue((String)accountDetails.get("billPostal"));
		}
		// displaying shipping address
		if((accountDetails.get("blkNo")==null ? accountDetails.get("shipBlkNo")==null : accountDetails.get("blkNo").equals(accountDetails.get("shipBlkNo")))
				&& (accountDetails.get("unitNo")==null ? accountDetails.get("shipUnitNo")==null : accountDetails.get("unitNo").equals(accountDetails.get("shipUnitNo")))
						&& (accountDetails.get("street")==null ? accountDetails.get("shipStreet")==null : accountDetails.get("street").equals(accountDetails.get("shipStreet")))
						&& (accountDetails.get("building")==null ? accountDetails.get("shipBuilding")==null : accountDetails.get("building").equals(accountDetails.get("shipBuilding")))
						&& (accountDetails.get("area")==null ? accountDetails.get("shipArea")==null : accountDetails.get("area").equals(accountDetails.get("shipArea")))
						&& (accountDetails.get("countryCode")==null ? accountDetails.get("shipCountryCode")==null : accountDetails.get("countryCode").equals(accountDetails.get("shipCountryCode")))
						&& (accountDetails.get("city")==null ? accountDetails.get("shipCity")==null : accountDetails.get("city").equals(accountDetails.get("shipCity")))
						&& (accountDetails.get("state")==null ? accountDetails.get("shipState")==null : accountDetails.get("state").equals(accountDetails.get("shipState")))
						&& (accountDetails.get("postal")==null ? accountDetails.get("shipPostal")==null : accountDetails.get("postal").equals(accountDetails.get("shipPostal")))
		){
			Rows billAddress = (Rows)this.getFellow("shipAddress");
			for(Object row : billAddress.getChildren()){
				if(!row.equals(billAddress.getFirstChild())){
					((Row)row).setVisible(false);
				}
			}
			((Label)this.getFellow("shipAdd")).setValue(NonConfigurableConstants.BOOLEAN_YN.get(NonConfigurableConstants.BOOLEAN_YES));
		}else{
			Rows billAddress = (Rows)this.getFellow("shipAddress");
			for(Object row : billAddress.getChildren()){
				if(!row.equals(billAddress.getFirstChild())){
					((Row)row).setVisible(true);
				}
			}
			((Label)this.getFellow("shipAdd")).setValue(NonConfigurableConstants.BOOLEAN_YN.get(NonConfigurableConstants.BOOLEAN_NO));
			((Label)this.getFellow("shipBlkNo")).setValue((String)accountDetails.get("shipBlkNo"));
			((Label)this.getFellow("shipUnitNo")).setValue((String)accountDetails.get("shipUnitNo"));
			((Label)this.getFellow("shipStreet")).setValue((String)accountDetails.get("shipStreet"));
			((Label)this.getFellow("shipBuilding")).setValue((String)accountDetails.get("shipBuilding"));
			((Label)this.getFellow("shipArea")).setValue((String)accountDetails.get("shipArea"));
			((Label)this.getFellow("shipCountry")).setValue(ConfigurableConstants.getCountries().get((String)accountDetails.get("shipCountryCode")));
			((Label)this.getFellow("shipCity")).setValue((String)accountDetails.get("shipCity"));
			((Label)this.getFellow("shipState")).setValue((String)accountDetails.get("shipState"));
			((Label)this.getFellow("shipPostal")).setValue((String)accountDetails.get("shipPostal"));
		}
	}
	public void edit() throws InterruptedException{
		logger.info("edit()");
		Map<String, String> params = new HashMap<String, String>();
		params.put("custNo", custNo);
		params.put("createdDt", createdDt);
		this.forward(Uri.EDIT_PERS_DETAILS, params, this.getParent());
	}
	public CommonWindow back() throws InterruptedException{
		if(this.getParent().getParent().getParent().getParent() instanceof ManagePersAcctWindow){
			((ManagePersAcctWindow)this.getParent().getParent().getParent().getParent()).back();
		}
		return null;
	}
	public void creditReview() throws InterruptedException{
		logger.info("creditReview()");
		forward(Uri.CREDIT_REVIEW_CORP);
	}
	public void suspend() throws InterruptedException{
		logger.info("suspend()");
		forward(Uri.SUSPEND_PERS);
	}
	public void reactivate() throws InterruptedException{
		logger.info("reactivate()");
		forward(Uri.REACTIVATE_PERS);
	}
	public void terminate() throws InterruptedException{
		logger.info("terminate()");
		forward(Uri.TERMINATE_PERS);
	}
	private void forward(String uri) throws InterruptedException{
		logger.info("forward(String uri)");
		Map<String, String> params = new HashMap<String, String>();
		params.put("custNo", custNo);
		this.forward(uri, params, this.getParent());
	}
	public void manageProdSubscription() throws InterruptedException{
		logger.info("manageProdSubscription()");
		forward(Uri.MANAGE_PROD_SUBSC);
	}
	private void enableAllBtns(){
		((Button)this.getFellow("editBtn")).setDisabled(false);
		((Button)this.getFellow("creditReviewBtn")).setDisabled(false);
		((Button)this.getFellow("suspendBtn")).setDisabled(false);
		((Button)this.getFellow("reactivateBtn")).setDisabled(false);
		((Button)this.getFellow("terminateBtn")).setDisabled(false);
	}
	public void viewStatusHistory() throws SuspendNotAllowedException, InterruptedException{
		super.viewStatusHistory(custNo, null, null, null);
	}
	public void viewCreditLimitHistory() throws InterruptedException{
		logger.info("viewCreditLimitHistory()");
		String acctName = ((Label)this.getFellow("acctName")).getValue();
		super.viewCreditLimitHistory(custNo, null, null, null, acctName);
	}
	public void checkAll(boolean checked){
		logger.info("checkAll(boolean checked)");
		Listbox listbox = (Listbox)this.getFellow("prodSubscriptions");
		for(Object item : listbox.getItems()){
			Checkbox selectCheck = (Checkbox)((Listitem)item).getLastChild().getLastChild();
			selectCheck.setChecked(checked);
		}
	}
	public void subscribe() throws InterruptedException{
		logger.info("subscribe()");
		Map<String, String> params = new HashMap<String, String>();
		params.put("custNo", custNo);
		this.forward(Uri.ADD_PROD_SUBSC, params, this.getParent());
	}
	public void unsubscribe() throws InterruptedException, CniiInterfaceException{
		logger.info("unsubscribe()");
		if(Messagebox.show("Confirm unsubscribe?", "Manage Product Subscription", Messagebox.OK | Messagebox.CANCEL, Messagebox.QUESTION) == Messagebox.OK){
			Listbox listbox = (Listbox)this.getFellow("prodSubscriptions");
			List<String> unsubscribes = new ArrayList<String>();
			for(Object item : listbox.getItems()){
				Checkbox selectCheck = (Checkbox)((Listitem)item).getLastChild().getLastChild();
				if(selectCheck.isChecked()){
					unsubscribes.add((String)((Listitem)item).getValue());
				}
			}
			if(unsubscribes.isEmpty()){
				Messagebox.show("No selected subscription", "Manage Product Subscription", Messagebox.OK, Messagebox.EXCLAMATION);
				return;
			}
			if(this.businessHelper.getAccountBusiness().hasIssuedProducts(custNo, null, null, NonConfigurableConstants.APPLICANT_LEVEL, unsubscribes)){
				if(Messagebox.show("Existing product issued for selected product types. Proceed?", "Manage Product Subscription", Messagebox.OK | Messagebox.CANCEL, Messagebox.QUESTION)==Messagebox.OK){
//					this.businessHelper.getAccountBusiness().unsubscribeProductType(custNo, unsubscribes);
					this.businessHelper.getAccountBusiness().unsubscribeProductTypeApproval(custNo, unsubscribes);
					Messagebox.show("Subscription sent for approval", "Manage Product Subscription", Messagebox.OK, Messagebox.INFORMATION);
					this.refresh();
				}
			}else{
//				this.businessHelper.getAccountBusiness().unsubscribeProductType(custNo, unsubscribes);
				this.businessHelper.getAccountBusiness().unsubscribeProductTypeApproval(custNo, unsubscribes);
				Messagebox.show("Subscription sent for approval", "Manage Product Subscription", Messagebox.OK, Messagebox.INFORMATION);
				this.refresh();
			}
		}
	}
	public void editSubscription(Listitem selected) throws InterruptedException{
		logger.info("editSubscription()");
		Map<String, String> params = new HashMap<String, String>();
		params.put("custNo", custNo);
		params.put("productTypeId", selected.getValue().toString());
		this.forward(Uri.EDIT_PROD_SUBSC, params, this.getParent());
	}
	public void refundDeposit() throws InterruptedException{
		logger.info("refundDeposit()");
		this.displayProcessing();
		try{
			boolean isDebtCleared = this.businessHelper.getAccountBusiness().isDebtCleared(this.custNo);
			if(!isDebtCleared){
				Messagebox.show("Account's debt is not cleared. " +
						"Please clear the debt either by creating new payment or " +
						"use deposit to offset the debt by issuing credit note.", 
						"Refund Deposit", Messagebox.OK, Messagebox.ERROR);
			}
			else{
				BigDecimal depositAmount = this.businessHelper.getAccountBusiness().getDepositAmount(custNo);
				if(depositAmount.compareTo(new BigDecimal(0))>0){
					if(Messagebox.show("You about to refund a total of $"+StringUtil.bigDecimalToString(depositAmount, StringUtil.GLOBAL_DECIMAL_FORMAT)+" deposit amount, are you sure?", "Deposit", Messagebox.OK | Messagebox.CANCEL, Messagebox.QUESTION)==Messagebox.OK){
						Long receiptNo = this.businessHelper.getAccountBusiness().generateMemoRefundForDeposit(custNo, depositAmount, getUserLoginIdAndDomain());
						int response = Messagebox.show(
								"Deposit Refund has been successfully processed." +
								"Receipt No is "+receiptNo+"." +
								"Do you wish to download the memo receipt?", "Refund Deposit",
								Messagebox.YES | Messagebox.NO, Messagebox.INFORMATION);
						if (response == Messagebox.YES) {
							this.displayProcessing();
							AMedia media = generateMemo(receiptNo);
							Filedownload.save(media);
						}
					}
				}
				else{
					Messagebox.show("There is no deposit amount left for refund!", 
							"Refund Deposit", Messagebox.OK, Messagebox.INFORMATION);
				}
			}
		}
		catch(WrongValueException wve){
			throw wve;
		}
		catch(HibernateOptimisticLockingFailureException holfe){
			Messagebox.show(com.cdgtaxi.ibs.common.NonConfigurableConstants.HIBERNATE_ERROR_MESSAGE,
					"Error", Messagebox.OK, Messagebox.ERROR);

			Executions.getCurrent().sendRedirect(""); //TO DO A REFRESH

			holfe.printStackTrace();
		}
		catch(Exception e){
			Messagebox.show(com.cdgtaxi.ibs.common.NonConfigurableConstants.COMMON_ERROR_MESSAGE,
					"Error", Messagebox.OK, Messagebox.ERROR);
			e.printStackTrace();
		}
	}
	private AMedia generateMemo(Long receiptNo) throws NetException, IOException{
		Properties params = new Properties();
		params.put("paymentReceiptNo", receiptNo.toString());

		String outputFormat = Constants.FORMAT_PDF;
		String reportCategory = NonConfigurableConstants.REPORT_CATEGORY_MEMO;
		String reportName = NonConfigurableConstants.REPORT_NAME_MEMO_DEPOSIT_REFUND;

		byte[] bytes =
			businessHelper.getReportBusiness().generate(reportName, reportCategory, outputFormat, params);

		return new AMedia(reportName+"_" + receiptNo + ".pdf", "pdf", outputFormat, bytes);
	}
	public void overdueReminder() throws InterruptedException{
		logger.info("overdueReminder()");
		forward(Uri.OVERDUE_REMINDER_PERS);
	}
	
	public void eInvoiceEmail() throws InterruptedException{
		logger.info("eInvoiceEmail()");
		forward(Uri.EINVOICE_EMAIL_PERS);
	}
	
	public void pdfPreview() throws InterruptedException, IOException{
		logger.info("pdfPreview()");
		
		//System.out.println("parent " + this.getRoot().getFellow("persWindow").getFellow("searchSubPersWindow").getFellow("subApplicants"));

		List<PdfExportItem> items = new ArrayList<PdfExportItem>();
		items.add(new PdfExportItem((Grid)this.getFellow("accountDetailsGrid"), "Account Details", null));
		items.add(new PdfExportItem((Listbox)this.getFellow("prodSubscriptions"), "Product Subscriptions", new int[]{5}));
		items.add(new PdfExportItem((Grid)this.getFellow("mainApplicantDetailsGrid"), "Main Applicant Details", null));
		items.add(new PdfExportItem((Grid)this.getFellow("billingAddressGrid"), "Billing Address", null));
		items.add(new PdfExportItem((Grid)this.getFellow("shippingAddressGrid"), "Shipping Address", null));
		items.add(new PdfExportItem((Grid)this.getFellow("employmentDetailsGrid"), "Employment Details", null));
		items.add(new PdfExportItem((Grid)this.getFellow("invoiceGrid"), "Invoice", null));
		items.add(new PdfExportItem((Grid)this.getFellow("assessmentGrid"), "Assessment", null));
		items.add(new PdfExportItem((Grid)this.getFellow("updatedByGrid"), "Last Updated By", null));
	
		Window subAppt = (Window)this.getRoot().getFellow("persWindow").getFellow("searchSubPersWindow");
		items.add(new PdfExportItem((Listbox)subAppt.getFellow("subApplicants"), "Sub Applicants", null));
		
		Window billingWin = (Window)this.getRoot().getFellow("persWindow").getFellow("viewBillingWindow");
        items.add(new PdfExportItem((Grid)billingWin.getFellow("billingCycleGrid"), "Billing Cycle Information", null));
        items.add(new PdfExportItem((Grid)billingWin.getFellow("creditTermGrid"), "Credit Term Information", null));
        items.add(new PdfExportItem((Grid)billingWin.getFellow("earlyPaymentGrid"), "Early Payment Information", null));
        items.add(new PdfExportItem((Grid)billingWin.getFellow("latePaymentGrid"), "Late Payment Information", null));
        items.add(new PdfExportItem((Grid)billingWin.getFellow("promotionGrid"), "Promotion Information", null));
		items.add(new PdfExportItem((Grid)billingWin.getFellow("customerBankGrid"), "Customer Bank Information", null));
		items.add(new PdfExportItem((Grid)billingWin.getFellow("paymentModeGrid"), "Payment Mode", null));
		items.add(new PdfExportItem((Grid)billingWin.getFellow("depositCollectionGrid"), "Deposit Collection", null));
		items.add(new PdfExportItem((Grid)billingWin.getFellow("invoiceForDeposit"), "Invoice For Deposit", null));
		
		Window rewardWin = (Window)this.getRoot().getFellow("persWindow").getFellow("viewRewardsWindow");
		items.add(new PdfExportItem((Grid)rewardWin.getFellow("rewardPointGrid"), "Rewards Details", null));
		items.add(new PdfExportItem((Grid)rewardWin.getFellow("pointAwardGrid"), "Points Award Details", null));
		
	    ByteArrayOutputStream out = new ByteArrayOutputStream();
	     
		ZkPdfExporter exp = new ZkPdfExporter(PageSize.A4.rotate());
		try {
		exp.export(items, out);
	     
	    AMedia amedia = new AMedia("View_Pers_Account.pdf", "pdf", "application/pdf", out.toByteArray());
	    Filedownload.save(amedia);   
		
		out.close();
		}catch (Exception e) {
			try {
				Messagebox.show(com.cdgtaxi.ibs.common.NonConfigurableConstants.COMMON_ERROR_MESSAGE,
						"Error", Messagebox.OK, Messagebox.ERROR);
			} catch (InterruptedException e1) { e.printStackTrace(); }
		}
	}
	
}