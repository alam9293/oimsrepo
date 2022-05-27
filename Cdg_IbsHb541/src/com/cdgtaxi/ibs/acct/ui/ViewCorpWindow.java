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
import org.zkoss.zul.Window;

import com.cdgtaxi.ibs.Uri;
import com.cdgtaxi.ibs.common.ConfigurableConstants;
import com.cdgtaxi.ibs.common.NonConfigurableConstants;
import com.cdgtaxi.ibs.common.ui.CommonWindow;
import com.cdgtaxi.ibs.exporter.PdfExportItem;
import com.cdgtaxi.ibs.exporter.ZkPdfExporter;
import com.cdgtaxi.ibs.interfaces.cnii.CniiInterfaceException;
import com.cdgtaxi.ibs.master.model.MstbMasterTable;
import com.cdgtaxi.ibs.report.Constants;
import com.cdgtaxi.ibs.util.DateUtil;
import com.cdgtaxi.ibs.util.StringUtil;
import com.elixirtech.net.NetException;
import com.lowagie.text.PageSize;

public class ViewCorpWindow extends CommonInnerAcctWindow {
	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(ViewCorpWindow.class);
	private String custNo, acctStatus, createdDt;
	
	@SuppressWarnings("unchecked")
	public ViewCorpWindow() throws InterruptedException{
		logger.info("ViewCorpWindow()");
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
		displayCorpFields(accountDetails);
		// for subscriptions
		Listbox listbox = (Listbox)this.getFellow("prodSubscriptions");
		listbox.getItems().clear();
		Set<Map<String, String>> prodSubscriptions = this.businessHelper.getAccountBusiness().getProductSubscriptions(custNo, null);
		for(Map<String, String> prodSubscription : prodSubscriptions){
			Listitem item = new Listitem();
			item.appendChild(newListcell(prodSubscription.get("prodType")==null ? "-" : prodSubscription.get("prodType")));
			item.appendChild(newListcell(prodSubscription.get("prodDiscount")==null ? "-" : prodSubscription.get("prodDiscount")));
			item.appendChild(newListcell(prodSubscription.get("rewards")==null ? "-" : prodSubscription.get("rewards")));
			item.appendChild(newListcell(prodSubscription.get("subscribeFee")==null ? "-" : prodSubscription.get("subscribeFee")));
			item.appendChild(newListcell(prodSubscription.get("issuanceFee")==null ? "-" : prodSubscription.get("issuanceFee")));
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
		if(!this.checkUriAccess(Uri.CREDIT_REVIEW_CORP))
			((Button)this.getFellow("creditReviewBtn")).setDisabled(true);
		if(!this.checkUriAccess(Uri.EDIT_CORP_DETAILS))
			((Button)this.getFellow("editBtn")).setDisabled(true);
		if(!this.checkUriAccess(Uri.SUSPEND_CORP))
			((Button)this.getFellow("suspendBtn")).setDisabled(true);
		if(!this.checkUriAccess(Uri.REACTIVATE_CORP))
			((Button)this.getFellow("reactivateBtn")).setDisabled(true);
		if(!this.checkUriAccess(Uri.TERMINATE_CORP))
			((Button)this.getFellow("terminateBtn")).setDisabled(true);
		if(!this.checkUriAccess(Uri.EINVOICE_EMAIL))
			((Button)this.getFellow("eInvoiceEmailBtn")).setDisabled(true);
	}
	private void displayCommonFields(Map<String, Object> accountDetails){
		// common details
		((Label)this.getFellow("custNo")).setValue((String)accountDetails.get("custNo"));
		((Label)this.getFellow("acctStatus")).setValue((String)accountDetails.get("acctStatus"));
		acctStatus = (String)accountDetails.get("acctStatus");
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
		((Label)this.getFellow("acctType")).setValue((String)accountDetails.get("acctType"));
		((Label)this.getFellow("acctName")).setValue((String)accountDetails.get("acctName"));
		((Label)this.getFellow("tel")).setValue((String)accountDetails.get("tel"));
		if(accountDetails.get("fax")!=null){
			((Label)this.getFellow("fax")).setValue((String)accountDetails.get("fax"));
		}else{
			((Label)this.getFellow("fax")).setValue("-");
		}
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
		if(accountDetails.get("aceIndicator")!=null){
			((Label)this.getFellow("aceIndicator")).setValue(NonConfigurableConstants.BOOLEAN_YN.get(accountDetails.get("aceIndicator")));
		}else{
			((Label)this.getFellow("aceIndicator")).setValue("-");
		}
		if(accountDetails.get("coupaIndicator")!=null){
			((Label)this.getFellow("coupaIndicator")).setValue(NonConfigurableConstants.BOOLEAN_YN.get(accountDetails.get("coupaIndicator")));
		}else{
			((Label)this.getFellow("coupaIndicator")).setValue("-");
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
		
		if(accountDetails.get("printTaxInvOnly")!= null)
			((Label)this.getFellow("printTaxInvOnly")).setValue(NonConfigurableConstants.BOOLEAN_YN.get(accountDetails.get("printTaxInvOnly")));

		if(accountDetails.get("aceIndicator")!= null)
			((Label)this.getFellow("aceIndicator")).setValue(NonConfigurableConstants.BOOLEAN_YN.get(accountDetails.get("aceIndicator")));
		
		if(accountDetails.get("coupaIndicator")!= null)
			((Label)this.getFellow("coupaIndicator")).setValue(NonConfigurableConstants.BOOLEAN_YN.get(accountDetails.get("coupaIndicator")));
		
		if(accountDetails.get("eInvoiceEmail")!= null)
			((Label)this.getFellow("eInvoiceEmail")).setValue(checkPDFEInvoiceEmail((String)accountDetails.get("eInvoiceEmail")));
		else
			((Label)this.getFellow("eInvoiceEmail")).setValue("-");
		
		if(accountDetails.get("eInvoiceEmailFlag")!= null)
			((Label)this.getFellow("eInvoiceEmailFlag")).setValue(NonConfigurableConstants.BOOLEAN_YN.get(accountDetails.get("eInvoiceEmailFlag")));
		else
			((Label)this.getFellow("eInvoiceEmailFlag")).setValue("-");
		
	}
	private void displayCorpFields(Map<String, Object> accountDetails){
		if(accountDetails.get("rcbNo")!=null){
			((Label)this.getFellow("rcbNo")).setValue((String)accountDetails.get("rcbNo"));
		}else{
			((Label)this.getFellow("rcbNo")).setValue("-");
		}
		String industry = ConfigurableConstants.getIndustries().get(accountDetails.get("industryCode"));
		((Label)this.getFellow("industry")).setValue(industry);
		if(accountDetails.get("rcbDate")!=null){
			String rcbDate = DateUtil.convertDateToStr((Date)accountDetails.get("rcbDate"), DateUtil.GLOBAL_DATE_FORMAT);
			((Label)this.getFellow("rcbDate")).setValue(rcbDate);
		}else{
			((Label)this.getFellow("rcbDate")).setValue("-");
		}
//		if(accountDetails.get("corpDate")!=null){
//			String corpDate = DateUtil.convertDateToStr((Date)accountDetails.get("corpDate"), DateUtil.GLOBAL_DATE_FORMAT);
//			((Label)this.getFellow("corpDate")).setValue(corpDate);
//		}else{
//			((Label)this.getFellow("corpDate")).setValue("-");
//		}
		if(accountDetails.get("capital")!=null){
			String capital = StringUtil.bigDecimalToString((BigDecimal)accountDetails.get("capital"), StringUtil.GLOBAL_DECIMAL_FORMAT);
			((Label)this.getFellow("capital")).setValue(capital);
		}else{
			((Label)this.getFellow("capital")).setValue("-");
		}
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
		String country = ConfigurableConstants.getCountries().get(accountDetails.get("countryCode"));
		((Label)this.getFellow("country")).setValue(country);
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
		if(accountDetails.get("projectCode")!=null){
			((Label)this.getFellow("projectCode")).setValue(NonConfigurableConstants.BOOLEAN.get(accountDetails.get("projectCode")));
		}else{
			((Label)this.getFellow("projectCode")).setValue("-");
		}
		if(accountDetails.get("salutationCode")!=null){
			String salutation = ConfigurableConstants.getSalutations().get(accountDetails.get("salutationCode"));
			((Label)this.getFellow("salutation")).setValue(salutation);
		}else{
			((Label)this.getFellow("salutation")).setValue("-");
		}
		if(accountDetails.get("authPerson")!=null){
			((Label)this.getFellow("authPerson")).setValue((String)accountDetails.get("authPerson"));
		}else{
			((Label)this.getFellow("authPerson")).setValue("-");
		}
		if(accountDetails.get("authTitle")!=null){
			((Label)this.getFellow("authTitle")).setValue((String)accountDetails.get("authTitle"));
		}else{
			((Label)this.getFellow("authTitle")).setValue("-");
		}
		String invoiceFormat = NonConfigurableConstants.INVOICE_FORMAT.get(accountDetails.get("invoiceFormatCode"));
		((Label)this.getFellow("invoiceFormat")).setValue(invoiceFormat);
		String invoiceSorting = NonConfigurableConstants.INVOICE_SORTING.get(accountDetails.get("invoiceSortingCode"));
		((Label)this.getFellow("invoiceSorting")).setValue(invoiceSorting);
		//Govt eInvoice Enhancement
		String govtEInvFlag = NonConfigurableConstants.GOVT_EINV_FLAGS.get(accountDetails.get("govtEInvFlag"));
		((Label)this.getFellow("govtEInvLabel")).setValue(govtEInvFlag);
		MstbMasterTable businessUnitMaster = (MstbMasterTable) accountDetails.get("businessUnit");
		if(businessUnitMaster != null){
			((Label)this.getFellow("businessUnitLabel")).setValue(businessUnitMaster.getMasterCode() + " - " + businessUnitMaster.getMasterValue());
		}
		else
			((Label)this.getFellow("businessUnitLabel")).setValue("");
		
		//pubbs flag
		if(accountDetails.get("pubbsFlag")!= null)
			((Label)this.getFellow("pubbs")).setValue(NonConfigurableConstants.BOOLEAN_YN.get(accountDetails.get("pubbsFlag")));
		//fi flag
		if(accountDetails.get("fiFlag")!= null)
			((Label)this.getFellow("fiFlag")).setValue(NonConfigurableConstants.BOOLEAN_YN.get(accountDetails.get("fiFlag")));
	}
	public void edit() throws InterruptedException{
		logger.info("edit()");
		Map<String, String> params = new HashMap<String, String>();
		params.put("custNo", custNo);
		params.put("createdDt", createdDt);
		this.forward(Uri.EDIT_CORP_DETAILS, params, this.getParent());
	}
	public CommonWindow back() throws InterruptedException{
		if(this.getParent().getParent().getParent().getParent() instanceof ManageCorpAcctWindow){
			((ManageCorpAcctWindow)this.getParent().getParent().getParent().getParent()).back();
		}
		return null;
	}
	public void creditReview() throws InterruptedException{
		logger.info("creditReview()");
		forward(Uri.CREDIT_REVIEW_CORP);
	}
	public void suspend() throws InterruptedException{
		logger.info("suspend()");
		forward(Uri.SUSPEND_CORP);
	}
	public void reactivate() throws InterruptedException{
		logger.info("reactivate()");
		forward(Uri.REACTIVATE_CORP);
	}
	public void terminate() throws InterruptedException{
		logger.info("terminate()");
		forward(Uri.TERMINATE_CORP);
	}
	private void forward(String uri) throws InterruptedException{
		logger.info("forward(String uri)");
		Map<String, String> params = new HashMap<String, String>();
		params.put("custNo", custNo);
		params.put("acctStatus", acctStatus);
		this.forward(uri, params, this.getParent());
	}
	public void manageProdSubscription() throws InterruptedException{
		logger.info("manageProdSubscription()");
		forward(Uri.MANAGE_PROD_SUBSC);
	}
	private void enableAllBtns(){
		logger.info("enableAllBtns()");
		((Button)this.getFellow("editBtn")).setDisabled(false);
		((Button)this.getFellow("creditReviewBtn")).setDisabled(false);
		((Button)this.getFellow("suspendBtn")).setDisabled(false);
		((Button)this.getFellow("reactivateBtn")).setDisabled(false);
		((Button)this.getFellow("terminateBtn")).setDisabled(false);
	}
	public void viewStatusHistory() throws SuspendNotAllowedException, InterruptedException{
		logger.info("viewStatusHistory()");
		super.viewStatusHistory(custNo, null, null, null);
	}
	public void viewCreditLimitHistory() throws InterruptedException{
		logger.info("viewCreditLimitHistory()");
		String acctName = ((Label)this.getFellow("acctName")).getValue();
		super.viewCreditLimitHistory(custNo, null, null, null, acctName);
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
			List<String> unsubscribesError = new ArrayList<String>();
			
			for(Object item : listbox.getItems()){
				Checkbox selectCheck = (Checkbox)((Listitem)item).getLastChild().getLastChild();
				if(selectCheck.isChecked()){
					if(this.businessHelper.getAccountBusiness().hasPendApproveSubscription(custNo, (String)((Listitem)item).getValue()))
						unsubscribesError.add((String)((Listitem)item).getLabel());
					else
						unsubscribes.add((String)((Listitem)item).getValue());
				}
			}
			if(!unsubscribesError.isEmpty()) {
				Messagebox.show("There is currently subscription for Product Type that is pending for approval. ('"+unsubscribesError+"')", "Edit Product Subscription", Messagebox.OK, Messagebox.ERROR);
				return;
			}
			else if(unsubscribes.isEmpty()){
				Messagebox.show("No selected subscription", "Manage Product Subscription", Messagebox.OK, Messagebox.EXCLAMATION);
				return;
			}
			
			if(this.businessHelper.getAccountBusiness().hasIssuedProducts(custNo, null, null, NonConfigurableConstants.CORPORATE_LEVEL, unsubscribes)){
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
	public void overdueReminder() throws InterruptedException{
		logger.info("overdueReminder()");
		forward(Uri.OVERDUE_REMINDER_CORP);
	}
	
	public void eInvoiceEmail() throws InterruptedException{
		logger.info("eInvoiceEmail()");
		forward(Uri.EINVOICE_EMAIL_CORP);
	}
	
	public void pdfPreview() throws InterruptedException, IOException{
		logger.info("pdfPreview()");

		List<PdfExportItem> items = new ArrayList<PdfExportItem>();
		items.add(new PdfExportItem((Grid)this.getFellow("accountGrid"), "Account Details", null));
		items.add(new PdfExportItem((Listbox)this.getFellow("prodSubscriptions"), "Product Subscriptions", new int[]{5}));
		items.add(new PdfExportItem((Grid)this.getFellow("corpGrid"), "Corp Details", null));
		items.add(new PdfExportItem((Grid)this.getFellow("invoiceGrid"), "Invoice Details", null));
		items.add(new PdfExportItem((Grid)this.getFellow("assessmentGrid"), "Assessment", null));
		
		Window contWin = (Window)this.getRoot().getFellow("corpWindow").getFellow("searchContactWindow");
		items.add(new PdfExportItem((Listbox)contWin.getFellow("contacts"), "Contact Persons", null));
		
		Window divWin = (Window)this.getRoot().getFellow("corpWindow").getFellow("searchDivisionWindow");
		items.add(new PdfExportItem((Listbox)divWin.getFellow("divisions"), "Divisions", new int[]{0}));
		
		Window deptWin = (Window)this.getRoot().getFellow("corpWindow").getFellow("searchDeptWindow");
		items.add(new PdfExportItem((Listbox)deptWin.getFellow("depts"), "Departments", new int[]{0}));
		
		Window billingWin = (Window)this.getRoot().getFellow("corpWindow").getFellow("viewBillingWindow");
        items.add(new PdfExportItem((Grid)billingWin.getFellow("billingCycleGrid"), "Billing Cycle Information", null));
        items.add(new PdfExportItem((Grid)billingWin.getFellow("creditTermGrid"), "Credit Term Information", null));
        items.add(new PdfExportItem((Grid)billingWin.getFellow("earlyPaymentGrid"), "Early Payment Information", null));
        items.add(new PdfExportItem((Grid)billingWin.getFellow("latePaymentGrid"), "Late Payment Information", null));
        items.add(new PdfExportItem((Grid)billingWin.getFellow("promotionGrid"), "Promotion Information", null));
		items.add(new PdfExportItem((Grid)billingWin.getFellow("customerBankGrid"), "Customer Bank Information", null));
		items.add(new PdfExportItem((Grid)billingWin.getFellow("paymentModeGrid"), "Payment Mode", null));
		items.add(new PdfExportItem((Grid)billingWin.getFellow("depositCollectionGrid"), "Deposit Collection", null));
		items.add(new PdfExportItem((Grid)billingWin.getFellow("invoiceForDeposit"), "Invoice For Deposit", null));
		
		Window rewardWin = (Window)this.getRoot().getFellow("corpWindow").getFellow("viewRewardsWindow");
		items.add(new PdfExportItem((Grid)rewardWin.getFellow("rewardPointGrid"), "Rewards Details", null));
		items.add(new PdfExportItem((Grid)rewardWin.getFellow("pointAwardGrid"), "Points Award Details", null));

	    ByteArrayOutputStream out = new ByteArrayOutputStream();
	     
		ZkPdfExporter exp = new ZkPdfExporter(PageSize.A4.rotate());
		try {
		exp.export(items, out);
	     
	    AMedia amedia = new AMedia("View_Corp_Account.pdf", "pdf", "application/pdf", out.toByteArray());
	    Filedownload.save(amedia);   
		
		out.close();
		}catch (Exception e) {
			e.printStackTrace();
			try {
				Messagebox.show(com.cdgtaxi.ibs.common.NonConfigurableConstants.COMMON_ERROR_MESSAGE,
						"Error", Messagebox.OK, Messagebox.ERROR);
			} catch (InterruptedException e1) { e.printStackTrace(); }
		}
	}
	
}