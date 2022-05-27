package com.cdgtaxi.ibs.acct.ui;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Blob;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.httpclient.HttpException;
import org.apache.log4j.Logger;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.zkoss.util.media.AMedia;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.SuspendNotAllowedException;
import org.zkoss.zk.ui.metainfo.EventHandler;
import org.zkoss.zk.ui.metainfo.ZScript;
import org.zkoss.zk.ui.util.ConditionImpl;
import org.zkoss.zkplus.hibernate.HibernateUtil;
import org.zkoss.zul.Button;
import org.zkoss.zul.Constraint;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Decimalbox;
import org.zkoss.zul.Filedownload;
import org.zkoss.zul.Image;
import org.zkoss.zul.Label;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Row;
import org.zkoss.zul.Rows;

import com.cdgtaxi.ibs.Uri;
import com.cdgtaxi.ibs.common.BankMasterManager;
import com.cdgtaxi.ibs.common.ConfigurableConstants;
import com.cdgtaxi.ibs.common.MasterSetup;
import com.cdgtaxi.ibs.common.NonConfigurableConstants;
import com.cdgtaxi.ibs.common.model.BmtbInvoiceFile;
import com.cdgtaxi.ibs.common.model.BmtbInvoiceHeader;
import com.cdgtaxi.ibs.report.Constants;
import com.cdgtaxi.ibs.util.ComponentUtil;
import com.cdgtaxi.ibs.util.DateUtil;
import com.cdgtaxi.ibs.util.StringUtil;
import com.elixirtech.net.NetException;

public class ViewBillingWindow extends CommonInnerAcctWindow {
	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(ViewBillingWindow.class);
	private String custNo, acctStatus;
	private String acctType, createdDt;
	@SuppressWarnings("unchecked")
	public ViewBillingWindow() throws InterruptedException{
		logger.info("ViewBillingWindow()");
		//customer number
		Map<String, String> map = Executions.getCurrent().getArg();
		custNo = map.get("custNo");
		if(custNo==null || custNo.trim().length()==0){
			Messagebox.show("No account number found!", "Edit Billing", Messagebox.OK, Messagebox.ERROR);
		}
		// account status
		acctStatus = map.get("acctStatus");
		if(acctStatus==null || acctStatus.trim().length()==0){
			Messagebox.show("No account status found!", "Edit Billing", Messagebox.OK, Messagebox.ERROR);
		}
		
		acctType = map.get("acctType");
		createdDt = map.get("createdDt");
	}
	
	public void init() throws InterruptedException{

		((Label)this.getFellow("custNo")).setValue(custNo);
		((Label)this.getFellow("acctStatus")).setValue(acctStatus);
		((Label)this.getFellow("acctType")).setValue(acctType);
		((Label)this.getFellow("createdDt")).setValue(createdDt);
		
		logger.info("init()");
		if(custNo==null || custNo.trim().length()==0){
			this.back();
		}else if(acctStatus==null || acctStatus.trim().length()==0){
			this.back();
		}
		Map<String, List<Map<String, Object>>> billingDetails = this.businessHelper.getAccountBusiness().getTopRowOfBillingDetails(custNo);
//		Map<String, List<Map<String, Object>>> billingDetails = this.businessHelper.getAccountBusiness().getBillingDetails(custNo);
		if(billingDetails != null) {
			// billing
			List<Map<String, Object>> billing = billingDetails.get("billing");
			Rows billingRows = (Rows)this.getFellow("billing");
			billingRows.getChildren().clear();
			Map<Integer, String> volumeDiscounts = MasterSetup.getVolumeDiscountManager().getAllMasters();
			Map<Integer, String> adminFees = MasterSetup.getAdminFeeManager().getAllMasters();
			// previous billing cycle, volume discount and admin fee
			String billingCycle = "-", volumeDiscountName = "-", adminFeeName = "-";
			
			// check if Billing object is null
			if(billing != null) {
				if (billing.size() > 0) {
					for(Map<String, Object> bill : billing){
						Row billingRow = new Row();
						billingRow.appendChild(new Label(""+(billingRows.getChildren().size()+1)));
						if(bill.get("billingCycle")!=null){
							billingCycle = NonConfigurableConstants.BILLING_CYCLES.get(bill.get("billingCycle"));
						}
						billingRow.appendChild(new Label(billingCycle));
						if(bill.get("volumeDiscount")!=null){
							volumeDiscountName = volumeDiscounts.get(bill.get("volumeDiscount"));
						}
						else{
							volumeDiscountName = "-";
						}
						billingRow.appendChild(new Label(volumeDiscountName));
						// adding [?] to row
						Image volDiscImage = new Image("/images/question.png");
						volDiscImage.setStyle("cursor: help");
						ZScript showInfo = ZScript.parseContent("viewBillingWindow.displayVolumeDiscount()");
						showInfo.setLanguage("java");
						EventHandler pdEvent = new EventHandler(showInfo, ConditionImpl.getInstance(null, null));
						volDiscImage.addEventHandler("onClick", pdEvent);
						billingRow.appendChild(volDiscImage);
						if(bill.get("adminFee")!=null){
							adminFeeName = adminFees.get(bill.get("adminFee"));
						}
						billingRow.appendChild(new Label(adminFeeName));
						// adding [?] to row
						Image adminFeeImage = new Image("/images/question.png");
						adminFeeImage.setStyle("cursor: help");
						showInfo = ZScript.parseContent("viewBillingWindow.displayAdminFee()");
						showInfo.setLanguage("java");
						pdEvent = new EventHandler(showInfo, ConditionImpl.getInstance(null, null));
						adminFeeImage.addEventHandler("onClick", pdEvent);
						billingRow.appendChild(adminFeeImage);
						Label effectiveDate = new Label(DateUtil.convertDateToStr((Date)bill.get("effectiveDate"), DateUtil.GLOBAL_DATE_FORMAT));
						billingRow.appendChild(effectiveDate);
						billingRows.appendChild(billingRow);
					} 
				} else {
					Row billingRow = new Row();
					billingRow.appendChild(new Label(""));
					billingRow.appendChild(new Label("No Record Found"));
					billingRows.appendChild(billingRow);
				}
			}
			// credit term
			String prevCreditTerm = null;
			Rows creditTermRows = (Rows)this.getFellow("creditTerm");
			creditTermRows.getChildren().clear();
			List<Map<String, Object>> creditTerm = billingDetails.get("creditTerm");
			Map<Integer, String> creditTerms = MasterSetup.getCreditTermManager().getAllMasters();
			// check if creditTerm object is null
			if(creditTerm != null) {
				if (creditTerm.size() > 0) {
					for(Map<String, Object> credit : creditTerm){
						Row creditTermRow = new Row();
						creditTermRow.appendChild(new Label(""+(creditTermRows.getChildren().size()+1)));
						if(credit.get("creditTerm")!=null){
							prevCreditTerm = creditTerms.get(credit.get("creditTerm"));
						}
						creditTermRow.appendChild(new Label(prevCreditTerm));
						// adding [?] to row
						Image creditTermImage = new Image("/images/question.png");
						creditTermImage.setStyle("cursor: help");
						ZScript showInfo = ZScript.parseContent("viewBillingWindow.displayCreditTerm()");
						showInfo.setLanguage("java");
						EventHandler pdEvent = new EventHandler(showInfo, ConditionImpl.getInstance(null, null));
						creditTermImage.addEventHandler("onClick", pdEvent);
						creditTermRow.appendChild(creditTermImage);
						Label effectiveDate = new Label(DateUtil.convertDateToStr((Date)credit.get("effectiveDate"), DateUtil.GLOBAL_DATE_FORMAT));
						creditTermRow.appendChild(effectiveDate);
						creditTermRows.appendChild(creditTermRow);
					}
				} else {
					Row creditTermRow = new Row();
					creditTermRow.appendChild(new Label(""));
					creditTermRow.appendChild(new Label("No Record Found"));
					creditTermRows.appendChild(creditTermRow);
				}
			}
			// early payment
			Map<Integer, String> earlyPayments = MasterSetup.getEarlyPaymentManager().getAllMasters();
			Rows earlyPymtRows = (Rows)this.getFellow("earlyPayment");
			earlyPymtRows.getChildren().clear();
			List<Map<String, Object>> earlyPymt = billingDetails.get("earlyPymt");
			// check if earlyPymt object is null
			if(earlyPymt != null) {
				if (earlyPymt.size() > 0) {
					for(Map<String, Object> early : earlyPymt){
						Row earlyPymtRow = new Row();
						earlyPymtRow.appendChild(new Label(""+(earlyPymtRows.getChildren().size()+1)));
						if(early.get("earlyPymt")!=null){
							earlyPymtRow.appendChild(new Label(earlyPayments.get(early.get("earlyPymt"))));
						}else{
							earlyPymtRow.appendChild(new Label("-"));
						}
						// adding [?] to row
						Image earlyPymtImage = new Image("/images/question.png");
						earlyPymtImage.setStyle("cursor: help");
						ZScript showInfo = ZScript.parseContent("viewBillingWindow.displayEarlyDiscount()");
						showInfo.setLanguage("java");
						EventHandler pdEvent = new EventHandler(showInfo, ConditionImpl.getInstance(null, null));
						earlyPymtImage.addEventHandler("onClick", pdEvent);
						earlyPymtRow.appendChild(earlyPymtImage);
						earlyPymtRow.appendChild(new Label(DateUtil.convertDateToStr((Date)early.get("effectiveDate"), DateUtil.GLOBAL_DATE_FORMAT)));
						earlyPymtRows.appendChild(earlyPymtRow);
					}
				}
				else {
					Row earlyPymtRow = new Row();
					earlyPymtRow.appendChild(new Label(""));
					earlyPymtRow.appendChild(new Label("No Record Found"));
					earlyPymtRows.appendChild(earlyPymtRow);
				}
			}
			// late payment
			Map<Integer, String> latePayments = MasterSetup.getLatePaymentManager().getAllMasters();
			Rows latePymtRows = (Rows)this.getFellow("latePayment");
			latePymtRows.getChildren().clear();
			List<Map<String, Object>> latePymt = billingDetails.get("latePymt");
			// check if latePayments object is null
			if(latePymt != null) {
				if (latePymt.size() > 0) {
					for(Map<String, Object> late : latePymt){
						Row latePymtRow = new Row();
						latePymtRow.appendChild(new Label(""+(latePymtRows.getChildren().size()+1)));
						latePymtRow.appendChild(new Label(latePayments.get(late.get("latePymt"))));
						// adding [?] to row
						Image latePymtImage = new Image("/images/question.png");
						latePymtImage.setStyle("cursor: help");
						ZScript showInfo = ZScript.parseContent("viewBillingWindow.displayLateInterest()");
						showInfo.setLanguage("java");
						EventHandler pdEvent = new EventHandler(showInfo, ConditionImpl.getInstance(null, null));
						latePymtImage.addEventHandler("onClick", pdEvent);
						latePymtRow.appendChild(latePymtImage);
						latePymtRow.appendChild(new Label(DateUtil.convertDateToStr((Date)late.get("effectiveDate"), DateUtil.GLOBAL_DATE_FORMAT)));
						latePymtRows.appendChild(latePymtRow);
					}
				}
				else {
					Row latePymtRow = new Row();
					latePymtRow.appendChild(new Label(""));
					latePymtRow.appendChild(new Label("No Record Found"));
					latePymtRows.appendChild(latePymtRow);
				}
			}
			// promotion
			Rows promotionRows = (Rows)this.getFellow("promotion");
			promotionRows.getChildren().clear();
			List<Map<String, Object>> promotion = billingDetails.get("promotion");
			Map<Integer, String> promotions = MasterSetup.getPromotionManager().getAllMasters();
			// check if promotion object is null
			if(promotion != null) {
				if (promotion.size() > 0) {
					for(Map<String, Object> promo : promotion){
						Row promotionRow = new Row();
						promotionRow.appendChild(new Label(""+(promotionRows.getChildren().size()+1)));
						promotionRow.appendChild(new Label(promotions.get(promo.get("promotion"))));
						// adding [?] to row
						Image promotionImage = new Image("/images/question.png");
						promotionImage.setStyle("cursor: help");
						ZScript showInfo = ZScript.parseContent("viewBillingWindow.displayPromotion()");
						showInfo.setLanguage("java");
						EventHandler pdEvent = new EventHandler(showInfo, ConditionImpl.getInstance(null, null));
						promotionImage.addEventHandler("onClick", pdEvent);
						promotionRow.appendChild(promotionImage);
						Label effectiveDateFrom = new Label(DateUtil.convertDateToStr((Date)promo.get("effectiveDateFrom"), DateUtil.GLOBAL_DATE_FORMAT));
						promotionRow.appendChild(effectiveDateFrom);
						
						Label effectiveDateTo = promo.get("effectiveDateTo")!=null ? new Label(DateUtil.convertDateToStr((Date)promo.get("effectiveDateTo"), DateUtil.GLOBAL_DATE_FORMAT)) : new Label("-");
						promotionRow.appendChild(effectiveDateTo);
						promotionRows.appendChild(promotionRow);
					}
				}
				else {
					Row promotionRow = new Row();
					promotionRow.appendChild(new Label(""));
					promotionRow.appendChild(new Label("No Record Found"));
					promotionRows.appendChild(promotionRow);
				}
			}
			
			// bank info
			List<Map<String, Object>> bankInfo = billingDetails.get("bankInfo");
			
			System.out.println("bankInfo: " +bankInfo);
			Map<Integer, String> banks = MasterSetup.getBankManager().getAllMasters();
			if(bankInfo != null) {
				if(!bankInfo.isEmpty()){
					if(bankInfo.get(0).get("bankMaster")!=null){
						((Label)this.getFellow("bank")).setValue(banks.get(bankInfo.get(0).get("bankMaster")));
					}else{
						((Label)this.getFellow("bank")).setValue("-");
					}
					if(bankInfo.get(0).get("branchMaster")!=null){
						Map<Integer, Map<String, String>> branchs = MasterSetup.getBankManager().getAllDetails((Integer)bankInfo.get(0).get("bankMaster"));
						((Label)this.getFellow("branch")).setValue(
								branchs.get(bankInfo.get(0).get("branchMaster")).get(BankMasterManager.DETAIL_BRANCH_CODE)
								+ " - " +
								branchs.get(bankInfo.get(0).get("branchMaster")).get(BankMasterManager.DETAIL_BRANCH_NAME));
					}else{
						((Label)this.getFellow("branch")).setValue("-");
					}
					if(bankInfo.get(0).get("bankAcctNo")!=null){
						((Label)this.getFellow("bankAcctNo")).setValue((String)bankInfo.get(0).get("bankAcctNo"));
					}else{
						((Label)this.getFellow("bankAcctNo")).setValue("-");
					}
					if(bankInfo.get(0).get("defaultPaymentMode")!=null){
						((Label)this.getFellow("paymentMode")).setValue(ConfigurableConstants.getPaymentModes().get(bankInfo.get(0).get("defaultPaymentMode")));
					}else{
						((Label)this.getFellow("paymentMode")).setValue("-");
					}
				}else{
					((Label)this.getFellow("bank")).setValue("-");
					((Label)this.getFellow("branch")).setValue("-");
					((Label)this.getFellow("bankAcctNo")).setValue("-");
					((Label)this.getFellow("paymentMode")).setValue("-");
				}
			}
		}
		// deposit
		BigDecimal totalRequested = new BigDecimal(0);
		Map<String, Map<String, String>> deposits = this.businessHelper.getAccountBusiness().getDepositDetails(custNo);
		Map<String, String> depositAmts = deposits.get("depositAmts");
		((Label)this.getFellow("requiredDeposit")).setValue(depositAmts.get("requiredDeposit"));
		((Label)this.getFellow("collectedDeposit")).setValue(depositAmts.get("collectedDeposit"));
		Rows invoices = (Rows)this.getFellow("invoices");
		invoices.getChildren().clear();
		for(String invoiceNo : deposits.keySet()){
			if(deposits.get(invoiceNo).get("invoiceNo")!=null){
				Map<String, String> deposit = deposits.get(invoiceNo);
				totalRequested = totalRequested.add(new BigDecimal(deposit.get("invoiceAmt").replaceAll(",", "")));
				Row invoice = new Row();
				invoice.appendChild(new Label((invoices.getChildren().size()+1)+""));
				invoice.appendChild(new Label(deposit.get("invoiceNo")));
				invoice.appendChild(new Label(deposit.get("invoiceDate")));
				invoice.appendChild(new Label(deposit.get("invoiceAmt")));
				invoice.appendChild(new Label(deposit.get("paidAmt")));
				invoice.appendChild(new Label(deposit.get("outstandingAmt")));
				invoice.appendChild(new Label(deposit.get("invoiceStatus")));
				invoices.appendChild(invoice);
			}
		}
		((Label)this.getFellow("totalRequestedDeposit")).setValue(StringUtil.bigDecimalToString(totalRequested, StringUtil.GLOBAL_DECIMAL_FORMAT));
		Decimalbox depositAmt = (Decimalbox)this.getFellow("depositAmt");
		Constraint tempConstraint = depositAmt.getConstraint();
		Constraint nullConstraint = null;
		depositAmt.setConstraint(nullConstraint);
		depositAmt.setValue(null);
		depositAmt.setConstraint(tempConstraint);
		Datebox invoiceDate = (Datebox)this.getFellow("invoiceDate");
		tempConstraint = invoiceDate.getConstraint();
		nullConstraint = null;
		invoiceDate.setConstraint(nullConstraint);
		invoiceDate.setValue(DateUtil.getCurrentDate());
		invoiceDate.setConstraint(tempConstraint);
		
		// checking access for deposit invoice
		if(!this.checkUriAccess(Uri.ISSUE_DEPOSIT_INVOICE))
			((Button)this.getFellow("depositBtn")).setDisabled(true);
		if(!this.checkUriAccess(Uri.EDIT_BILLING))
			((Button)this.getFellow("editBillingBtn")).setDisabled(true);
		if(!this.checkUriAccess(Uri.SEARCH_BILLING_REQ))
			((Button)this.getFellow("viewBillingHistoryBtn")).setDisabled(true);
	}
	public void updateBranchs(Listitem selectedBank){
		logger.info("updateBranchs()");
		if(selectedBank.getValue()!=null && !selectedBank.getValue().equals("")){
			Listbox branchList = (Listbox)this.getFellow("branchList");
			if(branchList.getFirstChild()==null){
				branchList.appendChild(ComponentUtil.createNotRequiredListItem());
			}
			Listitem firstItem = branchList.getItemAtIndex(0);
			branchList.getItems().clear();
			branchList.appendChild(firstItem);
			Map<Integer, Map<String, String>> branchs = MasterSetup.getBankManager().getAllDetails((Integer)selectedBank.getValue());
			for(Integer branchNo : branchs.keySet()){
				branchList.appendChild(new Listitem(branchs.get(branchNo).get(BankMasterManager.DETAIL_BRANCH_NAME), branchNo));
			}
		}
		
	}
	public void edit() throws InterruptedException{
		logger.info("edit()");
		Map<String, String> params = new HashMap<String, String>();
		params.put("custNo", custNo);
		params.put("acctStatus", acctStatus);
		this.forward(Uri.EDIT_BILLING, params, this.getParent());
	}
	public void viewHistory() throws InterruptedException{
		logger.info("viewHistory()");
		// TODO : to complete view history
		Map<String, String> params = new HashMap<String, String>();
		params.put("custNo", custNo);
		params.put("acctStatus", acctStatus);
		this.forward(Uri.SEARCH_BILLING_REQ, params, this.getParent());
	}
	public void viewDeposit() throws InterruptedException{
		logger.info("viewDeposit()");
		Map<String, String> params = new HashMap<String, String>();
		params.put("custNo", custNo);
		params.put("acctStatus", acctStatus);
		this.forward(Uri.VIEW_DEPOSIT, params, this.getParent());
	}
	@Override
	public void refresh() throws InterruptedException {
		this.init();
	}
	public void generateInvoice() throws InterruptedException{
		Map<String, Integer> contacts = this.businessHelper.getAccountBusiness().getAccountMainContact(custNo);
		boolean hasMainBilling = false;
		for(String type : contacts.keySet()){
			if(type.equals("mainBilling")){
				hasMainBilling = true;
				break;
			}
		}
		if(!hasMainBilling){
			Messagebox.show("No main billing contact person! Please create main billing contact person first.", "Create Division", Messagebox.OK, Messagebox.ERROR);
			return;
		}
		if(Messagebox.show("Generate invoice?", "Deposit", Messagebox.OK | Messagebox.CANCEL, Messagebox.QUESTION)==Messagebox.OK){
			BigDecimal deposit = ((Decimalbox)this.getFellow("depositAmt")).getValue();
			Date invoiceDate = ((Datebox)this.getFellow("invoiceDate")).getValue();
			if(deposit!=null && invoiceDate!=null){
				try{
					BmtbInvoiceHeader header = this.businessHelper.getAccountBusiness().generateDepositInvoice(deposit, invoiceDate, custNo, getUserLoginIdAndDomain()); 
					if(header!=null){
						try{
							AMedia media = this.generate(header);
							int response = Messagebox.show(
									"Invoice has been successfully issued and generated. Do you wish to download the invoice?", "Issue Misc. Invoice",
									Messagebox.YES | Messagebox.NO, Messagebox.INFORMATION);
							if (response == Messagebox.YES) {
								Filedownload.save(media);
							}
						}
						catch(NetException ne){
							Messagebox.show("Unable to generate invoice in pdf. Please inform the administrator", "Deposit", Messagebox.OK, Messagebox.ERROR);
						}
						catch(IOException ioe){
							Messagebox.show("Unable to generate invoice in pdf. Please inform the administrator", "Deposit", Messagebox.OK, Messagebox.ERROR);
						}
						init();
					}else{
						Messagebox.show("Unable to generate invoice. Please try again later", "Deposit", Messagebox.OK, Messagebox.ERROR);
					}
				}
				catch(Exception ex){
					Messagebox.show(ex.getMessage(), "Deposit", Messagebox.OK, Messagebox.ERROR);
				}
			}else{
				Messagebox.show("Please key in deposit amount and invoice date", "Deposit", Messagebox.OK, Messagebox.EXCLAMATION);
			}
		}
	}
	
	private AMedia generate(BmtbInvoiceHeader header) throws HttpException, IOException, InterruptedException, NetException {
		Properties params = new Properties();
		params.put("invoiceHeaderNo", header.getInvoiceHeaderNo().toString());
		
		String outputFormat = Constants.FORMAT_PDF;
		String reportName = NonConfigurableConstants.REPORT_NAME_INV_DEPOSIT;

		byte[] bytes =
			businessHelper.getReportBusiness().generate(reportName, null, outputFormat, params);

		BmtbInvoiceFile newInvoiceFile = new BmtbInvoiceFile();
		Session session = HibernateUtil.getSessionFactory().openSession();
		session.beginTransaction();
		Blob image = Hibernate.getLobCreator(session).createBlob(bytes);
		newInvoiceFile.setInvoiceFile(image);
		header.setBmtbInvoiceFile(newInvoiceFile);
		businessHelper.getGenericBusiness().save(newInvoiceFile);
		businessHelper.getGenericBusiness().update(header);

		return new AMedia(reportName+ ".pdf", "pdf", outputFormat, bytes);
	}
	public void viewStatusHistory() throws SuspendNotAllowedException, InterruptedException{
		logger.info("viewStatusHistory()");
		super.viewStatusHistory(custNo, null, null, null);
	}
	
	public void viewBillingCycleHistory() throws SuspendNotAllowedException, InterruptedException{
		logger.info("viewBillingCycleHistory()");
		super.viewBillingCycleHistory(custNo);
	}
	public void viewCreditTermHistory() throws SuspendNotAllowedException, InterruptedException{
		logger.info("viewCreditTermHistory()");
		super.viewCreditTermHistory(custNo);
	}
	public void viewEarlyPaymentHistory() throws SuspendNotAllowedException, InterruptedException{
		logger.info("viewEarlyPaymentHistory()");
		super.viewEarlyPaymentHistory(custNo);
	}
	public void viewLatePaymentHistory() throws SuspendNotAllowedException, InterruptedException{
		logger.info("viewLatePaymentHistory()");
		super.viewLatePaymentHistory(custNo);
	}
	public void viewPromotionHistory() throws SuspendNotAllowedException, InterruptedException{
		logger.info("viewPromotionHistory()");
		super.viewPromotionHistory(custNo);
	}
}