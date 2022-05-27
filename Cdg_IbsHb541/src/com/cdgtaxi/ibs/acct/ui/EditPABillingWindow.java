package com.cdgtaxi.ibs.acct.ui;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Blob;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

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
import org.zkoss.zul.Textbox;

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
import com.cdgtaxi.ibs.web.constraint.RequiredEqualOrLaterThanCurrentDateConstraint;
import com.elixirtech.net.NetException;

public class EditPABillingWindow extends CommonInnerAcctWindow {
	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(EditPABillingWindow.class);
	private String custNo, acctStatus;
	private String acctType, createdDt;
	List<Listitem> billingCycles = ComponentUtil.convertToListitems(NonConfigurableConstants.BILLING_CYCLES, true);
	List<Listitem> creditTerms, earlyPayments, latePayments, banks, pymtMode, promotions;
	String volumeDiscountName = "-", adminFeeName = "-";
	@SuppressWarnings("unchecked")
	public EditPABillingWindow() throws InterruptedException{
		logger.info("EditPABillingWindow()");
		Map<Integer, String> promotionMasters = MasterSetup.getPromotionManager().getAllEffectiveAccountPromotions();
		promotions = new ArrayList<Listitem>();
		for(Integer promotionNo : promotionMasters.keySet()){
			promotions.add(new Listitem(promotionMasters.get(promotionNo), promotionNo));
		}
		Map<Integer, String> creditTermMasters = MasterSetup.getCreditTermManager().getAllMasters();
		creditTerms = new ArrayList<Listitem>();
		for(Integer creditTermNo : creditTermMasters.keySet()){
			creditTerms.add(new Listitem(creditTermMasters.get(creditTermNo), creditTermNo));
		}
		pymtMode = new ArrayList<Listitem>();
		for(String paymentCode : ConfigurableConstants.getPaymentModes().keySet()){
			if(paymentCode.equals("CT") || paymentCode.equals("MEMO")){
				continue;
			}
			pymtMode.add(new Listitem(ConfigurableConstants.getPaymentModes().get(paymentCode), paymentCode));
		}
		Map<Integer, String> earlyPymtMasters = MasterSetup.getEarlyPaymentManager().getAllMasters();
		earlyPayments = new ArrayList<Listitem>();
		for(Integer earlyPymtNo : earlyPymtMasters.keySet()){
			earlyPayments.add(new Listitem(earlyPymtMasters.get(earlyPymtNo), earlyPymtNo));
		}
		Map<Integer, String> latePymtMasters = MasterSetup.getLatePaymentManager().getAllMasters();
		latePayments = new ArrayList<Listitem>();
		for(Integer latePymtNo : latePymtMasters.keySet()){
			latePayments.add(new Listitem(latePymtMasters.get(latePymtNo), latePymtNo));
		}
		Map<Integer, String> bankMasters = MasterSetup.getBankManager().getAllMasters();
		banks = new ArrayList<Listitem>();
		banks.add((Listitem)ComponentUtil.createNotRequiredListItem());
		for(Integer bankNo : bankMasters.keySet()){
			banks.add(new Listitem(bankMasters.get(bankNo), bankNo));
		}
		Collections.sort(banks, new Comparator<Listitem>() {
			public int compare(Listitem o1, Listitem o2) {
				return o1.getLabel().compareTo(o2.getLabel());
			}
		});
		latePayments = new ArrayList<Listitem>();
		for(Integer latePymtNo : latePymtMasters.keySet()){
			latePayments.add(new Listitem(latePymtMasters.get(latePymtNo), latePymtNo));
		}
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
	
	@SuppressWarnings("unchecked")
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
		Map<String, List<Map<String, Object>>> billingDetails = this.businessHelper.getAccountBusiness().getBillingDetailsNew(custNo, false);
		// billing
		List<Map<String, Object>> billing = billingDetails.get("billing");
		Rows billingRows = (Rows)this.getFellow("billing");
		while(billingRows.getChildren().size()!=1){
			billingRows.removeChild(billingRows.getFirstChild());
		}
		Map<Integer, String> volumeDiscounts = MasterSetup.getVolumeDiscountManager().getAllMasters();
		Map<Integer, String> adminFees = MasterSetup.getAdminFeeManager().getAllMasters();
		for(Map<String, Object> bill : billing){
			Row billingRow = new Row();
			billingRow.appendChild(new Label(""+billingRows.getChildren().size()));
			Listbox billingCycle = new Listbox();
			billingCycle.setWidth("100%"); billingCycle.setRows(1); billingCycle.setMold("select");
			billingCycle.getItems().addAll(cloneList(billingCycles));
			if(bill.get("billingCycle")!=null){
				for(Object billCycle : billingCycle.getItems()){
					if(((Listitem)billCycle).getValue().equals(bill.get("billingCycle"))){
						((Listitem)billCycle).setSelected(true);
						break;
					}
				}
			}else{
				billingCycle.setSelectedIndex(0);
			}
			billingRow.appendChild(billingCycle);
			if(bill.get("volumeDiscount")!=null){
				volumeDiscountName = volumeDiscounts.get(bill.get("volumeDiscount"));
			}
			billingRow.appendChild(new Label(volumeDiscountName));
			// adding [?] to row
			Image volDiscImage = new Image("/images/question.png");
			volDiscImage.setStyle("cursor: help");
			ZScript showInfo = ZScript.parseContent("editPABillingWindow.displayVolumeDiscount()");
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
			showInfo = ZScript.parseContent("editPABillingWindow.displayAdminFee()");
			showInfo.setLanguage("java");
			pdEvent = new EventHandler(showInfo, ConditionImpl.getInstance(null, null));
			adminFeeImage.addEventHandler("onClick", pdEvent);
			billingRow.appendChild(adminFeeImage);
			Datebox effectiveDate = new Datebox();
			effectiveDate.setWidth("90%");
			effectiveDate.setFormat(DateUtil.GLOBAL_DATE_FORMAT);
			effectiveDate.setValue((Date)bill.get("effectiveDate"));
			effectiveDate.setDisabled(true);
			billingRow.appendChild(effectiveDate);
			// adding x to row
			Image deleteImage = new Image("/images/delete.png");
//			deleteImage.setStyle("cursor: pointer");
//			showInfo = ZScript.parseContent("editPABillingWindow.deleteRow(self.getParent())");
//			showInfo.setLanguage("java");
//			pdEvent = new EventHandler(showInfo, ConditionImpl.getInstance(null, null));
//			deleteImage.addEventHandler("onClick", pdEvent);
			deleteImage.setVisible(false);
			billingRow.appendChild(deleteImage);
			billingRows.insertBefore(billingRow, billingRows.getLastChild());
		}
		// credit term
		Rows creditTermRows = (Rows)this.getFellow("creditTerm");
		while(creditTermRows.getChildren().size()!=1){
			creditTermRows.removeChild(creditTermRows.getFirstChild());
		}
		List<Map<String, Object>> creditTerm = billingDetails.get("creditTerm");
		if(creditTerm.size()==0){
			this.addCreditTerm();
		}
		for(Map<String, Object> credit : creditTerm){
			Row creditTermRow = new Row();
			creditTermRow.appendChild(new Label(""+creditTermRows.getChildren().size()));
			Listbox creditTermBox = new Listbox();
			creditTermBox.setWidth("100%"); creditTermBox.setRows(1); creditTermBox.setMold("select");
			creditTermBox.getItems().addAll(cloneList(creditTerms));
			if(credit.get("creditTerm")!=null){
				for(Object creditTermItem : creditTermBox.getItems()){
					if(((Listitem)creditTermItem).getValue().equals(credit.get("creditTerm"))){
						((Listitem)creditTermItem).setSelected(true);
						break;
					}
				}
			}
			creditTermRow.appendChild(creditTermBox);
			// adding [?] to row
			Image creditTermImage = new Image("/images/question.png");
			creditTermImage.setStyle("cursor: help");
			ZScript showInfo = ZScript.parseContent("editPABillingWindow.displayCreditTerm()");
			showInfo.setLanguage("java");
			EventHandler pdEvent = new EventHandler(showInfo, ConditionImpl.getInstance(null, null));
			creditTermImage.addEventHandler("onClick", pdEvent);
			creditTermRow.appendChild(creditTermImage);
			Datebox effectiveDate = new Datebox();
			effectiveDate.setWidth("91.5%");
			effectiveDate.setFormat(DateUtil.GLOBAL_DATE_FORMAT);
			effectiveDate.setValue((Date)credit.get("effectiveDate"));
			effectiveDate.setDisabled(true);
			creditTermRow.appendChild(effectiveDate);
			// adding x to row
			Image deleteImage = new Image("/images/delete.png");
//			deleteImage.setStyle("cursor: pointer");
//			showInfo = ZScript.parseContent("editPABillingWindow.deleteRow(self.getParent())");
//			showInfo.setLanguage("java");
//			pdEvent = new EventHandler(showInfo, ConditionImpl.getInstance(null, null));
//			deleteImage.addEventHandler("onClick", pdEvent);
			deleteImage.setVisible(false);
			creditTermRow.appendChild(deleteImage);
			creditTermRows.insertBefore(creditTermRow, creditTermRows.getLastChild());
		}
		// early payment
		Map<Integer, String> earlyPayments = MasterSetup.getEarlyPaymentManager().getAllMasters();
		Rows earlyPymtRows = (Rows)this.getFellow("earlyPayment");
		earlyPymtRows.getChildren().clear();
		List<Map<String, Object>> earlyPymt = billingDetails.get("earlyPymt");
		for(Map<String, Object> early : earlyPymt){
			Row earlyPymtRow = new Row();
			earlyPymtRow.appendChild(new Label(""+(earlyPymtRows.getChildren().size()+1)));
			earlyPymtRow.appendChild(new Label(earlyPayments.get(early.get("earlyPymt"))));
			// adding [?] to row
			Image earlyPymtImage = new Image("/images/question.png");
			earlyPymtImage.setStyle("cursor: help");
			ZScript showInfo = ZScript.parseContent("editPABillingWindow.displayEarlyDiscount()");
			showInfo.setLanguage("java");
			EventHandler pdEvent = new EventHandler(showInfo, ConditionImpl.getInstance(null, null));
			earlyPymtImage.addEventHandler("onClick", pdEvent);
			earlyPymtRow.appendChild(earlyPymtImage);
			earlyPymtRow.appendChild(new Label(DateUtil.convertDateToStr((Date)early.get("effectiveDate"), DateUtil.GLOBAL_DATE_FORMAT)));
			earlyPymtRows.appendChild(earlyPymtRow);
		}
		// late payment
		Map<Integer, String> latePayments = MasterSetup.getLatePaymentManager().getAllMasters();
		Rows latePymtRows = (Rows)this.getFellow("latePayment");
		latePymtRows.getChildren().clear();
		List<Map<String, Object>> latePymt = billingDetails.get("latePymt");
		for(Map<String, Object> late : latePymt){
			Row latePymtRow = new Row();
			latePymtRow.appendChild(new Label(""+(latePymtRows.getChildren().size()+1)));
			latePymtRow.appendChild(new Label(latePayments.get(late.get("latePymt"))));
			// adding [?] to row
			Image latePymtImage = new Image("/images/question.png");
			latePymtImage.setStyle("cursor: help");
			ZScript showInfo = ZScript.parseContent("editPABillingWindow.displayLateInterest()");
			showInfo.setLanguage("java");
			EventHandler pdEvent = new EventHandler(showInfo, ConditionImpl.getInstance(null, null));
			latePymtImage.addEventHandler("onClick", pdEvent);
			latePymtRow.appendChild(latePymtImage);
			latePymtRow.appendChild(new Label(DateUtil.convertDateToStr((Date)late.get("effectiveDate"), DateUtil.GLOBAL_DATE_FORMAT)));
			latePymtRows.appendChild(latePymtRow);
		}
		// credit term
		Rows promotionRows = (Rows)this.getFellow("promotion");
		while(promotionRows.getChildren().size()!=1){
			promotionRows.removeChild(promotionRows.getFirstChild());
		}
		List<Map<String, Object>> promotion = billingDetails.get("promotion");
		for(Map<String, Object> promo : promotion){
			Row promotionRow = new Row();
			promotionRow.appendChild(new Label(""+promotionRows.getChildren().size()));
			Listbox promotionBox = new Listbox();
			promotionBox.setWidth("100%"); promotionBox.setRows(1); promotionBox.setMold("select");
			promotionBox.getItems().addAll(cloneList(promotions));
			if(promo.get("promotion")!=null){
				for(Object promotionItem : promotionBox.getItems()){
					if(((Listitem)promotionItem).getValue().equals(promo.get("promotion"))){
						((Listitem)promotionItem).setSelected(true);
						break;
					}
				}
			}
			promotionRow.appendChild(promotionBox);
			// adding [?] to row
			Image promotionImage = new Image("/images/question.png");
			promotionImage.setStyle("cursor: help");
			ZScript showInfo = ZScript.parseContent("editPABillingWindow.displayPromotion()");
			showInfo.setLanguage("java");
			EventHandler pdEvent = new EventHandler(showInfo, ConditionImpl.getInstance(null, null));
			promotionImage.addEventHandler("onClick", pdEvent);
			promotionRow.appendChild(promotionImage);
			Datebox effectiveDateFrom = new Datebox();
			effectiveDateFrom.setWidth("91.5%");
			effectiveDateFrom.setFormat(DateUtil.GLOBAL_DATE_FORMAT);
			effectiveDateFrom.setValue((Date)promo.get("effectiveDateFrom"));
			promotionRow.appendChild(effectiveDateFrom);
			Datebox effectiveDateTo = new Datebox();
			effectiveDateTo.setWidth("91.5%");
			effectiveDateTo.setFormat(DateUtil.GLOBAL_DATE_FORMAT);
			effectiveDateTo.setValue((Date)promo.get("effectiveDateTo"));
			promotionRow.appendChild(effectiveDateTo);
			// adding x to row
			Image deleteImage = new Image("/images/delete.png");
			deleteImage.setStyle("cursor: pointer");
			showInfo = ZScript.parseContent("editPABillingWindow.deleteRow(self.getParent())");
			showInfo.setLanguage("java");
			pdEvent = new EventHandler(showInfo, ConditionImpl.getInstance(null, null));
			deleteImage.addEventHandler("onClick", pdEvent);
			promotionRow.appendChild(deleteImage);
			promotionRows.insertBefore(promotionRow, promotionRows.getLastChild());
		}
		// bank info
		List<Map<String, Object>> bankInfo = billingDetails.get("bankInfo");
		if(!bankInfo.isEmpty()){
			if(bankInfo.get(0).get("bankMaster")!=null){
				Listbox bankList = (Listbox)this.getFellow("bankList");
				for(Object bankItem : bankList.getItems()){
					if(((Listitem)bankItem).getValue().equals(bankInfo.get(0).get("bankMaster"))){
						((Listitem)bankItem).setSelected(true);
						updateBranchs((Listitem)bankItem);
						break;
					}
				}
			}else{
				((Listbox)this.getFellow("bankList")).setSelectedIndex(0);
				updateBranchs((Listitem)this.getFellow("bankList").getFirstChild());
			}
			if(bankInfo.get(0).get("branchMaster")!=null){
				Listbox branchList = (Listbox)this.getFellow("branchList");
				for(Object branchItem : branchList.getItems()){
					if(((Listitem)branchItem).getValue().equals(bankInfo.get(0).get("branchMaster"))){
						((Listitem)branchItem).setSelected(true);
						break;
					}
				}
			}else{
				((Listbox)this.getFellow("branchList")).appendChild((Listitem)ComponentUtil.createNotRequiredListItem());
				((Listbox)this.getFellow("branchList")).setSelectedIndex(0);
			}
			if(bankInfo.get(0).get("bankAcctNo")!=null){
				((Textbox)this.getFellow("bankAcctNo")).setValue((String)bankInfo.get(0).get("bankAcctNo"));
			}else{
				((Textbox)this.getFellow("bankAcctNo")).setValue(null);
			}
			if(bankInfo.get(0).get("defaultPaymentMode")!=null){
				Listbox pymtModesList = (Listbox)this.getFellow("paymentModeList");
				for(Object pymtModeItem : pymtModesList.getItems()){
					if(((Listitem)pymtModeItem).getValue().equals(bankInfo.get(0).get("defaultPaymentMode"))){
						((Listitem)pymtModeItem).setSelected(true);
						break;
					}
				}
			}else{
				((Listbox)this.getFellow("paymentModeList")).setSelectedIndex(0);
			}
		}else{
			((Listbox)this.getFellow("bankList")).setSelectedIndex(0);
			updateBranchs((Listitem)this.getFellow("bankList").getFirstChild());
			((Listbox)this.getFellow("branchList")).setSelectedIndex(0);
			((Textbox)this.getFellow("bankAcctNo")).setValue(null);
			((Listbox)this.getFellow("paymentModeList")).setSelectedIndex(0);
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
		if(!this.checkUriAccess(Uri.ISSUE_DEPOSIT_INVOICE)){
			((Button)this.getFellow("depositBtn")).setDisabled(true);
		}
		//If payment mode selected is Interbank Giro, bank account is mandatory and enabled otherwise disabled.
		if(((Listbox)this.getFellow("paymentModeList")).getSelectedItem().getValue().equals(NonConfigurableConstants.PAYMENT_MODE_INTERBANK_GIRO)){
			((Textbox)this.getFellow("bankAcctNo")).setDisabled(false);
		}
		else{
			((Textbox)this.getFellow("bankAcctNo")).setValue(null);
			((Textbox)this.getFellow("bankAcctNo")).setDisabled(true);
		}
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
				branchList.appendChild(new Listitem(
						branchs.get(branchNo).get(BankMasterManager.DETAIL_BRANCH_CODE)
						+ " - " +
						branchs.get(branchNo).get(BankMasterManager.DETAIL_BRANCH_NAME)
						, branchNo));
			}
		}
	}
	@SuppressWarnings("unchecked")
	public void addBilling(){
		logger.info("addBilling()");
		Rows billingRows = (Rows)this.getFellow("billing");
		Row billingRow = new Row();
		billingRow.appendChild(new Label(""+billingRows.getChildren().size()));
		Listbox billingCycle = new Listbox();
		billingCycle.setWidth("100%"); billingCycle.setRows(1); billingCycle.setMold("select");
		billingCycle.getItems().addAll(cloneList(billingCycles));
		billingCycle.setSelectedIndex(0);
		billingRow.appendChild(billingCycle);
		billingRow.appendChild(new Label(volumeDiscountName));
		// adding [?] to row
		Image volDiscImage = new Image("/images/question.png");
		volDiscImage.setStyle("cursor: help");
		ZScript showInfo = ZScript.parseContent("editPABillingWindow.displayVolumeDiscount()");
		showInfo.setLanguage("java");
		EventHandler pdEvent = new EventHandler(showInfo, ConditionImpl.getInstance(null, null));
		volDiscImage.addEventHandler("onClick", pdEvent);
		billingRow.appendChild(volDiscImage);
		billingRow.appendChild(new Label(adminFeeName));
		// adding [?] to row
		Image adminFeeImage = new Image("/images/question.png");
		adminFeeImage.setStyle("cursor: help");
		showInfo = ZScript.parseContent("editPABillingWindow.displayAdminFee()");
		showInfo.setLanguage("java");
		pdEvent = new EventHandler(showInfo, ConditionImpl.getInstance(null, null));
		adminFeeImage.addEventHandler("onClick", pdEvent);
		billingRow.appendChild(adminFeeImage);
		Datebox effectiveDate = new Datebox();
		effectiveDate.setWidth("90%");
		effectiveDate.setFormat(DateUtil.GLOBAL_DATE_FORMAT);
		effectiveDate.setValue(convertToSQLDate(Calendar.getInstance()));
		billingRow.appendChild(effectiveDate);
		// adding x to row
		Image deleteImage = new Image("/images/delete.png");
		deleteImage.setStyle("cursor: pointer");
		showInfo = ZScript.parseContent("editPABillingWindow.deleteRow(self.getParent())");
		showInfo.setLanguage("java");
		pdEvent = new EventHandler(showInfo, ConditionImpl.getInstance(null, null));
		deleteImage.addEventHandler("onClick", pdEvent);
		billingRow.appendChild(deleteImage);
		billingRows.insertBefore(billingRow, billingRows.getLastChild());
	}
	@SuppressWarnings("unchecked")
	public void addCreditTerm(){
		logger.info("addCreditTerm()");
		Rows creditTermRows = (Rows)this.getFellow("creditTerm");
		Row creditTermRow = new Row();
		creditTermRow.appendChild(new Label(""+creditTermRows.getChildren().size()));
		Listbox creditTermBox = new Listbox();
		creditTermBox.setWidth("100%"); creditTermBox.setRows(1); creditTermBox.setMold("select");
		creditTermBox.getItems().addAll(cloneList(creditTerms));
		creditTermBox.setSelectedIndex(0);
		creditTermRow.appendChild(creditTermBox);
		// adding [?] to row
		Image creditTermImage = new Image("/images/question.png");
		creditTermImage.setStyle("cursor: help");
		ZScript showInfo = ZScript.parseContent("editPABillingWindow.displayCreditTerm()");
		showInfo.setLanguage("java");
		EventHandler pdEvent = new EventHandler(showInfo, ConditionImpl.getInstance(null, null));
		creditTermImage.addEventHandler("onClick", pdEvent);
		creditTermRow.appendChild(creditTermImage);
		Datebox effectiveDate = new Datebox();
		effectiveDate.setWidth("91.5%");
		effectiveDate.setFormat(DateUtil.GLOBAL_DATE_FORMAT);
		effectiveDate.setValue(convertToSQLDate(Calendar.getInstance()));
		effectiveDate.setDisabled(true);
		creditTermRow.appendChild(effectiveDate);
		// adding x to row
		Image deleteImage = new Image("/images/delete.png");
//		deleteImage.setStyle("cursor: pointer");
//		showInfo = ZScript.parseContent("editPABillingWindow.deleteRow(self.getParent())");
//		showInfo.setLanguage("java");
//		pdEvent = new EventHandler(showInfo, ConditionImpl.getInstance(null, null));
//		deleteImage.addEventHandler("onClick", pdEvent);
		deleteImage.setVisible(false);
		creditTermRow.appendChild(deleteImage);
		creditTermRows.insertBefore(creditTermRow, creditTermRows.getLastChild());
	}
	@SuppressWarnings("unchecked")
	public void addPromotion() throws InterruptedException{
		if(promotions.size()==0){
			Messagebox.show("No Promotion available!", "Edit Billing", Messagebox.OK, Messagebox.EXCLAMATION);
			return;
		}
		logger.info("addPromotion()");
		Rows promotionRows = (Rows)this.getFellow("promotion");
		Row promotionRow = new Row();
		promotionRow.appendChild(new Label(""+promotionRows.getChildren().size()));
		Listbox promotionBox = new Listbox();
		promotionBox.setWidth("100%"); promotionBox.setRows(1); promotionBox.setMold("select");
		promotionBox.getItems().addAll(cloneList(promotions));
		promotionBox.setSelectedIndex(0);
		promotionRow.appendChild(promotionBox);
		// adding [?] to row
		Image promotionImage = new Image("/images/question.png");
		promotionImage.setStyle("cursor: help");
		ZScript showInfo = ZScript.parseContent("editPABillingWindow.displayPromotion()");
		showInfo.setLanguage("java");
		EventHandler pdEvent = new EventHandler(showInfo, ConditionImpl.getInstance(null, null));
		promotionImage.addEventHandler("onClick", pdEvent);
		promotionRow.appendChild(promotionImage);
		Datebox effectiveDateFrom = new Datebox();
		effectiveDateFrom.setConstraint(new RequiredEqualOrLaterThanCurrentDateConstraint());
		effectiveDateFrom.setWidth("91.5%");
		effectiveDateFrom.setFormat(DateUtil.GLOBAL_DATE_FORMAT);
		effectiveDateFrom.setValue(convertToSQLDate(Calendar.getInstance()));
		promotionRow.appendChild(effectiveDateFrom);
		Datebox effectiveDateTo = new Datebox();
		effectiveDateTo.setWidth("91.5%");
		effectiveDateTo.setFormat(DateUtil.GLOBAL_DATE_FORMAT);
		promotionRow.appendChild(effectiveDateTo);
		// adding x to row
		Image deleteImage = new Image("/images/delete.png");
		deleteImage.setStyle("cursor: pointer");
		showInfo = ZScript.parseContent("editPABillingWindow.deleteRow(self.getParent())");
		showInfo.setLanguage("java");
		pdEvent = new EventHandler(showInfo, ConditionImpl.getInstance(null, null));
		deleteImage.addEventHandler("onClick", pdEvent);
		promotionRow.appendChild(deleteImage);
		promotionRows.insertBefore(promotionRow, promotionRows.getLastChild());
	}
	public void save() throws InterruptedException{
		logger.info("save()");
		// Extracting inputs
		// billing inputs
		Rows billingRows = (Rows)this.getFellow("billing");
		Map<Date, Map<String, Object>> billingMap = new TreeMap<Date, Map<String, Object>>(new Comparator<Date>(){
			public int compare(Date o1, Date o2) {
				return o1.compareTo(o2);
			}
		});
		for(Object row : billingRows.getChildren()){
			if(!row.equals(billingRows.getLastChild())){
				Row billingRow = (Row)row;
				Datebox effectiveDate = (Datebox)billingRow.getChildren().get(6);
				if(effectiveDate.getValue()==null){
					Messagebox.show("Please fill in all effective dates for Billing Details", "Edit Billing", Messagebox.OK, Messagebox.EXCLAMATION);
					return;
				}
				Listbox billingCycle = (Listbox)billingRow.getChildren().get(1);
				Listitem billingCycleItem = billingCycle.getSelectedItem();
				Map<String, Object> billingDetail = new HashMap<String, Object>();
				billingDetail.put("billingCycle", billingCycleItem.getValue());
				if(billingMap.put(effectiveDate.getValue(), billingDetail)!=null){
					Messagebox.show("Duplicate effective dates in Billing Details", "Edit Billing", Messagebox.OK, Messagebox.EXCLAMATION);
					return;
				}
			}
		}
		// now checking for no changes
		String prevCycle = null;
		for(Date effectiveDate : billingMap.keySet()){
			if(billingMap.get(effectiveDate).get("billingCycle").equals(prevCycle)){
				Messagebox.show("No changes detected for Billing Details. Please change billing details.", "Edit billing", Messagebox.OK, Messagebox.EXCLAMATION);
				return;
			}else{
				prevCycle = (String)billingMap.get(effectiveDate).get("billingCycle");
			}
		}
		// credit term inputs
		Rows creditTermRows = (Rows)this.getFellow("creditTerm");
		Map<Date, Integer> creditTermMap = new TreeMap<Date, Integer>(new Comparator<Date>(){
			public int compare(Date o1, Date o2) {
				return o1.compareTo(o2);
			}
		});
		for(Object row : creditTermRows.getChildren()){
			if(!row.equals(creditTermRows.getLastChild())){
				Row creditTermRow = (Row)row;
				Datebox effectiveDate = (Datebox)creditTermRow.getChildren().get(3);
				if(effectiveDate.getValue()==null){
					Messagebox.show("Please fill in all effective dates for Credit Term", "Edit Billing", Messagebox.OK, Messagebox.EXCLAMATION);
					return;
				}
				Listbox creditTerm = (Listbox)creditTermRow.getChildren().get(1);
				Listitem creditTermItem = creditTerm.getSelectedItem();
				if(creditTermMap.put(effectiveDate.getValue(), (Integer)creditTermItem.getValue())!=null){
					Messagebox.show("Duplicate effective dates in Credit Term", "Edit Billing", Messagebox.OK, Messagebox.EXCLAMATION);
					return;
				}
			}
		}
		// now checking for no changes
		Integer prevTerm = null;
		for(Date effectiveDate : creditTermMap.keySet()){
			if(creditTermMap.get(effectiveDate).equals(prevTerm)){
				Messagebox.show("No changes detected for Credit Term. Please change credit terms.", "Edit billing", Messagebox.OK, Messagebox.EXCLAMATION);
				return;
			}else{
				prevTerm = creditTermMap.get(effectiveDate);
			}
		}
		// promotion inputs
		Rows promotionRows = (Rows)this.getFellow("promotion");
		Set<Map<String, Object>> promotionMaps = new TreeSet<Map<String, Object>>(new Comparator<Map<String, Object>>(){
			public int compare(Map<String, Object> o1, Map<String, Object> o2) {
				return ((Date)o1.get("effectiveDateFrom")).compareTo((Date)o2.get("effectiveDateFrom"));
			}
		});
		for(Object row : promotionRows.getChildren()){
			if(!row.equals(promotionRows.getLastChild())){
				Row promotionRow = (Row)row;
				Datebox effectiveDateFrom = (Datebox)promotionRow.getChildren().get(3);
				if(effectiveDateFrom.getValue()==null){
					Messagebox.show("Please fill in all effective dates from for Promotions", "Edit Billing", Messagebox.OK, Messagebox.EXCLAMATION);
					return;
				}
				Datebox effectiveDateTo = (Datebox)promotionRow.getChildren().get(4);
				Listbox promotion = (Listbox)promotionRow.getChildren().get(1);
				Listitem promotionItem = promotion.getSelectedItem();
				Map<String, Object> promotionMap = new HashMap<String, Object>();
				promotionMap.put("effectiveDateFrom", effectiveDateFrom.getValue());
				promotionMap.put("effectiveDateTo", effectiveDateTo.getValue());
				promotionMap.put("promoNo", promotionItem.getValue());
				promotionMaps.add(promotionMap);
			}
		}
		// bank info inputs
		Map<String, Object> bankMap = new HashMap<String, Object>();
		Listbox bankList = (Listbox)this.getFellow("bankList");
		if(bankList.getSelectedItem()!=null){
			if(bankList.getSelectedItem().getValue()!=null && !bankList.getSelectedItem().getValue().equals("")){
				bankMap.put("bankNo", (Integer)bankList.getSelectedItem().getValue());
			}
		}
		Listbox branchList = (Listbox)this.getFellow("branchList");
		if(branchList.getSelectedItem()!=null){
			if(branchList.getSelectedItem().getValue()!=null && !branchList.getSelectedItem().getValue().equals("")){
				bankMap.put("branchNo", (Integer)branchList.getSelectedItem().getValue());
			}
		}
		bankMap.put("bankAcctNo", ((Textbox)this.getFellow("bankAcctNo")).getValue());
		Listbox paymentModeList = (Listbox)this.getFellow("paymentModeList");
		if(paymentModeList.getSelectedItem()!=null){
			if(paymentModeList.getSelectedItem().getValue()!=null && !paymentModeList.getSelectedItem().getValue().equals("")){
				bankMap.put("paymentModeCode", (String)paymentModeList.getSelectedItem().getValue());
				
				//Validation Check
				if(((String)paymentModeList.getSelectedItem().getValue()).equals(NonConfigurableConstants.PAYMENT_MODE_INTERBANK_GIRO)){
					if(bankMap.get("bankNo") == null || 
							bankMap.get("branchNo") == null ||
							bankMap.get("bankAcctNo") == null ||
							bankMap.get("bankAcctNo").toString().length()==0){
						Messagebox.show("Customer Bank Information must be all filled up when default payment mode is INTERBANK GIRO!",
								"Edit Billing", Messagebox.OK, Messagebox.ERROR);
						return;
					}
				}
			}
		}
		// now checking that billing, credit term and late payment must have one effective
		boolean found = false;
		for(Date effectiveDate : billingMap.keySet()){
			if(effectiveDate.before(DateUtil.getCurrentDate())){
				found = true;
				break;
			}
		}
		if(!found){
			Messagebox.show("There is no Billing Details that is in effect!", "Edit Billing", Messagebox.OK, Messagebox.ERROR);
			return;
		}
		found = false;
		for(Date effectiveDate : creditTermMap.keySet()){
			if(effectiveDate.before(DateUtil.getCurrentDate())){
				found = true;
				break;
			}
		}
		if(!found){
			Messagebox.show("There is no Credit Term that is in effect!", "Edit Billing", Messagebox.OK, Messagebox.ERROR);
			return;
		}
		String userId = getUserLoginIdAndDomain();
		if(this.businessHelper.getAccountBusiness().updateBillingDetails(custNo, billingMap, creditTermMap, null, null, promotionMaps, bankMap, userId)){
			Messagebox.show("Billing information saved", "Edit Billing", Messagebox.OK, Messagebox.INFORMATION);
		}else{
			Messagebox.show("Unable to save. Please try again later", "Edit Billing", Messagebox.OK, Messagebox.INFORMATION);
		}
	}
	public void viewDeposit() throws InterruptedException{
		logger.info("viewDeposit()");
		if(Messagebox.show("Proceed without saving?", "Edit Billing", Messagebox.OK | Messagebox.CANCEL, Messagebox.QUESTION) == Messagebox.OK){
			Map<String, String> params = new HashMap<String, String>();
			params.put("custNo", custNo);
			params.put("acctStatus", acctStatus);
			this.forward(Uri.VIEW_DEPOSIT, params, this.getParent());
		}
	}
	@Override
	public void refresh() throws InterruptedException {
		this.init();
	}
	public List<Listitem> getPaymentModes(){
		return this.pymtMode;
	}
	public List<Listitem> getBanks(){
		return this.banks;
	}
	public void deleteRow(Row row){
		Rows rows = (Rows)row.getParent();
		rows.removeChild(row);
		// now renumber the rows
		for(int i=1;i<rows.getChildren().size();i++){
			Row tempRow = (Row)rows.getChildren().get(i-1);
			((Label)tempRow.getFirstChild()).setValue(i + "");
		}
	}
	private java.sql.Date convertToSQLDate(Calendar calendar){
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		return new java.sql.Date(calendar.getTimeInMillis());
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
		params.put("reprint", "N");

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

		return new AMedia(reportName + ".pdf", "pdf", outputFormat, bytes);
	}
	
	public void onSelectPaymentMode(Listitem item){
		//If payment mode selected is Interbank Giro, bank account is mandatory and enabled otherwise disabled.
		if(item.getValue().equals(NonConfigurableConstants.PAYMENT_MODE_INTERBANK_GIRO)){
			((Textbox)this.getFellow("bankAcctNo")).setDisabled(false);
		}
		else{
			((Textbox)this.getFellow("bankAcctNo")).setValue(null);
			((Textbox)this.getFellow("bankAcctNo")).setDisabled(true);
		}
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