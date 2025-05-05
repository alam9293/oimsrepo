package com.cdgtaxi.ibs.billing.ui;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;
import org.zkoss.zhtml.Messagebox;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.SuspendNotAllowedException;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.ext.AfterCompose;
import org.zkoss.zul.Decimalbox;
import org.zkoss.zul.Div;
import org.zkoss.zul.Label;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Row;

import com.cdgtaxi.ibs.Uri;
import com.cdgtaxi.ibs.common.ConfigurableConstants;
import com.cdgtaxi.ibs.common.MasterSetup;
import com.cdgtaxi.ibs.common.NonConfigurableConstants;
import com.cdgtaxi.ibs.common.model.AmtbAccount;
import com.cdgtaxi.ibs.common.model.BmtbInvoiceDetail;
import com.cdgtaxi.ibs.common.model.BmtbInvoiceSummary;
import com.cdgtaxi.ibs.common.model.BmtbPaymentReceipt;
import com.cdgtaxi.ibs.common.model.forms.InvoicePaymentDetail;
import com.cdgtaxi.ibs.common.model.forms.PaymentInfo;
import com.cdgtaxi.ibs.common.ui.CommonWindow;
import com.cdgtaxi.ibs.master.model.MstbBankMaster;
import com.cdgtaxi.ibs.master.model.MstbBranchMaster;
import com.cdgtaxi.ibs.util.DateUtil;
import com.cdgtaxi.ibs.util.StringUtil;
import com.cdgtaxi.ibs.web.constraint.NegativeAppliedAmountConstraint;
import com.cdgtaxi.ibs.web.constraint.OverPaymentConstraint;

public class ApplyPartialPaymentWindow extends CommonWindow implements AfterCompose{

	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(ApplyPartialPaymentWindow.class);
	private static final String SELF = "applyPartialPaymentWindow";
	private InvoicePaymentDetail invoicePaymentDetail;
	private PaymentInfo paymentInfo;
	
	@SuppressWarnings("unchecked")
	public ApplyPartialPaymentWindow() throws Exception{
		Map parameters = Executions.getCurrent().getArg();
		
		BmtbPaymentReceipt paymentReceipt = (BmtbPaymentReceipt)parameters.get("paymentReceipt");
		if(paymentReceipt==null){
			invoicePaymentDetail = (InvoicePaymentDetail)parameters.get("invoicePaymentDetail");
			paymentInfo = (PaymentInfo)parameters.get("paymentInfo");
		}
		else{
			invoicePaymentDetail = (InvoicePaymentDetail)parameters.get("invoicePaymentDetail");
			//Convert paymentReceipt into payment info
			paymentInfo = this.convertPaymentReceipt(paymentReceipt);
		}
	}
	
	private PaymentInfo convertPaymentReceipt(BmtbPaymentReceipt paymentReceipt){
		
		PaymentInfo paymentInfo = new PaymentInfo();
		
		//accounts
		AmtbAccount account = paymentReceipt.getAmtbAccount();
		if(account.getAccountCategory().equals(NonConfigurableConstants.ACCOUNT_CATEGORY_DEPARTMENT)){
			paymentInfo.setDepartment(account);
			paymentInfo.setDivision(account.getAmtbAccount());
			paymentInfo.setAccount(account.getAmtbAccount().getAmtbAccount());
		}
		else if(account.getAccountCategory().equals(NonConfigurableConstants.ACCOUNT_CATEGORY_DIVISION)){
			paymentInfo.setDivision(account);
			paymentInfo.setAccount(account.getAmtbAccount());
		}
		else{
			paymentInfo.setAccount(account);
		}
		
		//payment mode
		paymentInfo.setPaymentMode(paymentReceipt.getMstbMasterTableByPaymentMode().getMasterCode());
		//payment date
		paymentInfo.setPaymentDate(paymentReceipt.getPaymentDate());
		//receipt date
		paymentInfo.setReceiptDt(paymentReceipt.getReceiptDt());
		
		if(NonConfigurableConstants.specificPaymentModes.get(paymentInfo.getPaymentMode()) == null){
			//txn ref no
			paymentInfo.setTxnRefNo(paymentReceipt.getPaymentNo());
			//payment amount
			paymentInfo.setPaymentAmount(paymentReceipt.getPaymentAmount());
		}
		else if(paymentInfo.getPaymentMode().equals(NonConfigurableConstants.PAYMENT_MODE_AXS)){
			//txn ref no
			paymentInfo.setTxnRefNo(paymentReceipt.getPaymentNo());
			//payment amount
			paymentInfo.setPaymentAmount(paymentReceipt.getPaymentAmount());
		}
		else if(paymentInfo.getPaymentMode().equals(NonConfigurableConstants.PAYMENT_MODE_BANK_TRANSFER)){
			//txn ref no
			paymentInfo.setTxnRefNo(paymentReceipt.getPaymentNo());
			//payment amount
			paymentInfo.setPaymentAmount(paymentReceipt.getPaymentAmount());
			//bank
			if(paymentReceipt.getMstbBankMaster()!=null)
				paymentInfo.setBankNo(paymentReceipt.getMstbBankMaster().getBankMasterNo());
			//branch
			if(paymentReceipt.getMstbBranchMaster()!=null)
				paymentInfo.setBranchNo(paymentReceipt.getMstbBranchMaster().getBranchMasterNo());
		}
		else if(paymentInfo.getPaymentMode().equals(NonConfigurableConstants.PAYMENT_MODE_CASH)){
			//payment amount
			paymentInfo.setPaymentAmount(paymentReceipt.getPaymentAmount());
		}
		else if(paymentInfo.getPaymentMode().equals(NonConfigurableConstants.PAYMENT_MODE_CHEQUE)){
			//cheque date
			paymentInfo.setChequeDate(paymentReceipt.getChequeDate());
			//cheque no
			paymentInfo.setChequeNo(paymentReceipt.getChequeNo());
			//quick check deposit
			if(NonConfigurableConstants.BOOLEAN_YN_YES.equals(paymentReceipt.getQuickChequeDeposit()))
				paymentInfo.setQuickChequeDeposit(true);
			else
				// default should be false (if null)
				paymentInfo.setQuickChequeDeposit(false);
			//bank
			paymentInfo.setBankNo(paymentReceipt.getMstbBankMaster().getBankMasterNo());
			//branch
			paymentInfo.setBranchNo(paymentReceipt.getMstbBranchMaster().getBranchMasterNo());
			//payment amount
			paymentInfo.setPaymentAmount(paymentReceipt.getPaymentAmount());
		}
		else if(paymentInfo.getPaymentMode().equals(NonConfigurableConstants.PAYMENT_MODE_CREDIT_CARD)){
			//txn ref no
			paymentInfo.setTxnRefNo(paymentReceipt.getPaymentNo());
			//payment amount
			paymentInfo.setPaymentAmount(paymentReceipt.getPaymentAmount());
		}
		else if(paymentInfo.getPaymentMode().equals(NonConfigurableConstants.PAYMENT_MODE_GIRO)){
			//txn ref no
			paymentInfo.setTxnRefNo(paymentReceipt.getPaymentNo());
			//payment amount
			paymentInfo.setPaymentAmount(paymentReceipt.getPaymentAmount());
			//bank
			if(paymentReceipt.getMstbBankMaster()!=null)
				paymentInfo.setBankNo(paymentReceipt.getMstbBankMaster().getBankMasterNo());
			//branch
			if(paymentReceipt.getMstbBranchMaster()!=null)
				paymentInfo.setBranchNo(paymentReceipt.getMstbBranchMaster().getBranchMasterNo());
		}
		else if(paymentInfo.getPaymentMode().equals(NonConfigurableConstants.PAYMENT_MODE_CONTRA)){
			//payment amount
			paymentInfo.setPaymentAmount(paymentReceipt.getPaymentAmount());
		}
		
		//remarks
		paymentInfo.setRemarks(paymentReceipt.getRemarks());
		//bank in
		paymentInfo.setBankInNo(paymentReceipt.getFmtbBankCode().getBankCodeNo());
		
		//excess amount
		paymentInfo.setExcessAmount(paymentReceipt.getExcessAmount());
		
		return paymentInfo;
	}
	
	@Override
	public void refresh() throws InterruptedException {
		
	}

	//After the components are created, we initialize those label values from previous input screen
	public void afterCompose() {
		//Payment Receipt Section
		Label accountNoLabel = (Label)this.getFellow("accountNoLabel");
		accountNoLabel.setValue(paymentInfo.getAccount().getCustNo());
		Label accountNameLabel = (Label)this.getFellow("accountNameLabel");
		accountNameLabel.setValue(paymentInfo.getAccount().getAccountName());
		
		if(paymentInfo.getDivision()!=null){
			Row divisionDepartmentRow = (Row)this.getFellow("divisionDepartmentRow");
			divisionDepartmentRow.setVisible(true);
			Label divisionNameLabel = (Label)this.getFellow("divisionNameLabel");
			divisionNameLabel.setValue(paymentInfo.getDivision().getAccountName());
			if(paymentInfo.getDepartment()!=null){
				Label departmentNameLabel = (Label)this.getFellow("departmentNameLabel");
				departmentNameLabel.setValue(paymentInfo.getDivision().getAccountName());
			}
		}
		
		Label paymentModeLabel = (Label)this.getFellow("paymentModeLabel");
		paymentModeLabel.setValue(ConfigurableConstants.getMasterTable(ConfigurableConstants.PAYMENT_MODE, paymentInfo.getPaymentMode()).getMasterValue());
		Label paymentDateLabel = (Label)this.getFellow("paymentDateLabel");
		paymentDateLabel.setValue(DateUtil.convertDateToStr(paymentInfo.getPaymentDate(), DateUtil.GLOBAL_DATE_FORMAT));
		
		if(NonConfigurableConstants.specificPaymentModes.get(paymentInfo.getPaymentMode()) == null){
			((Row)this.getFellow("othersDetail_1")).setVisible(true);
			Label othersReceiptDateLabel = (Label)this.getFellow("othersReceiptDateLabel");
			othersReceiptDateLabel.setValue(DateUtil.convertTimestampToStr(paymentInfo.getReceiptDt(), DateUtil.GLOBAL_DATE_FORMAT));
			Label othersTxnRefNoLabel = (Label)this.getFellow("othersTxnRefNoLabel");
			othersTxnRefNoLabel.setValue(paymentInfo.getTxnRefNo());
			
			((Row)this.getFellow("othersDetail_2")).setVisible(true);
			Label othersPaymentAmountLabel = (Label)this.getFellow("othersPaymentAmountLabel");
			othersPaymentAmountLabel.setValue(StringUtil.bigDecimalToString(paymentInfo.getPaymentAmount(), StringUtil.GLOBAL_DECIMAL_FORMAT));
			Label excessLabel = new Label("Excess Amount($)");
			excessLabel.setStyle("font-weight: bold");
			excessLabel.setSclass("fieldLabel");
			((Row)this.getFellow("othersDetail_2")).appendChild(excessLabel);
			Label excessAmountLabel = new Label(StringUtil.bigDecimalToString(paymentInfo.getExcessAmount(), StringUtil.GLOBAL_DECIMAL_FORMAT));
			excessAmountLabel.setId("excessAmountLabel");
			((Row)this.getFellow("othersDetail_2")).appendChild(excessAmountLabel);
		}
		else if(paymentInfo.getPaymentMode().equals(NonConfigurableConstants.PAYMENT_MODE_AXS)){
			((Row)this.getFellow("axsDetail_1")).setVisible(true);
			Label axsReceiptDateLabel = (Label)this.getFellow("axsReceiptDateLabel");
			axsReceiptDateLabel.setValue(DateUtil.convertTimestampToStr(paymentInfo.getReceiptDt(), DateUtil.GLOBAL_DATE_FORMAT));
			Label axsTxnRefNoLabel = (Label)this.getFellow("axsTxnRefNoLabel");
			axsTxnRefNoLabel.setValue(paymentInfo.getTxnRefNo());
			
			((Row)this.getFellow("axsDetail_2")).setVisible(true);
			Label axsPaymentAmountLabel = (Label)this.getFellow("axsPaymentAmountLabel");
			axsPaymentAmountLabel.setValue(StringUtil.bigDecimalToString(paymentInfo.getPaymentAmount(), StringUtil.GLOBAL_DECIMAL_FORMAT));
			Label excessLabel = new Label("Excess Amount($)");
			excessLabel.setStyle("font-weight: bold");
			excessLabel.setSclass("fieldLabel");
			((Row)this.getFellow("axsDetail_2")).appendChild(excessLabel);
			Label excessAmountLabel = new Label(StringUtil.bigDecimalToString(paymentInfo.getExcessAmount(), StringUtil.GLOBAL_DECIMAL_FORMAT));
			excessAmountLabel.setId("excessAmountLabel");
			((Row)this.getFellow("axsDetail_2")).appendChild(excessAmountLabel);
		}
		else if(paymentInfo.getPaymentMode().equals(NonConfigurableConstants.PAYMENT_MODE_BANK_TRANSFER)){
			((Row)this.getFellow("bankTransferDetail_1")).setVisible(true);
			Label btReceiptDateLabel = (Label)this.getFellow("btReceiptDateLabel");
			btReceiptDateLabel.setValue(DateUtil.convertTimestampToStr(paymentInfo.getReceiptDt(), DateUtil.GLOBAL_DATE_FORMAT));
			Label btTxnRefNoLabel = (Label)this.getFellow("btTxnRefNoLabel");
			btTxnRefNoLabel.setValue(paymentInfo.getTxnRefNo());
			
			((Row)this.getFellow("bankTransferDetail_2")).setVisible(true);
			Label btBankLabel = (Label)this.getFellow("btBankLabel");
			if(paymentInfo.getBankNo()!=null)
				btBankLabel.setValue(((MstbBankMaster)MasterSetup.getBankManager().getMaster(paymentInfo.getBankNo())).getBankName());
			else
				btBankLabel.setValue("-");
			Label btBranchLabel = (Label)this.getFellow("btBranchLabel");
			if(paymentInfo.getBranchNo()!=null)
				btBranchLabel.setValue(((MstbBranchMaster)MasterSetup.getBankManager().getDetail(paymentInfo.getBranchNo())).getBranchName());
			else
				btBranchLabel.setValue("-");
			
			((Row)this.getFellow("bankTransferDetail_3")).setVisible(true);
			Label btPaymentAmountLabel = (Label)this.getFellow("btPaymentAmountLabel");
			btPaymentAmountLabel.setValue(StringUtil.bigDecimalToString(paymentInfo.getPaymentAmount(), StringUtil.GLOBAL_DECIMAL_FORMAT));
			Label excessLabel = new Label("Excess Amount($)");
			excessLabel.setStyle("font-weight: bold");
			excessLabel.setSclass("fieldLabel");
			((Row)this.getFellow("bankTransferDetail_3")).appendChild(excessLabel);
			Label excessAmountLabel = new Label(StringUtil.bigDecimalToString(paymentInfo.getExcessAmount(), StringUtil.GLOBAL_DECIMAL_FORMAT));
			excessAmountLabel.setId("excessAmountLabel");
			((Row)this.getFellow("bankTransferDetail_3")).appendChild(excessAmountLabel);
		}
		else if(paymentInfo.getPaymentMode().equals(NonConfigurableConstants.PAYMENT_MODE_CASH)){
			((Row)this.getFellow("cashDetail_1")).setVisible(true);
			Label caReceiptDate = (Label)this.getFellow("caReceiptDate");
			caReceiptDate.setValue(DateUtil.convertTimestampToStr(paymentInfo.getReceiptDt(), DateUtil.GLOBAL_DATE_FORMAT));
			Label caPaymentAmountLabel = (Label)this.getFellow("caPaymentAmountLabel");
			caPaymentAmountLabel.setValue(StringUtil.bigDecimalToString(paymentInfo.getPaymentAmount(), StringUtil.GLOBAL_DECIMAL_FORMAT));
			
			Row cashDetail2 = new Row();
			Label excessLabel = new Label("Excess Amount($)");
			excessLabel.setStyle("font-weight: bold");
			excessLabel.setSclass("fieldLabel");
			cashDetail2.appendChild(excessLabel);
			Label excessAmountLabel = new Label(StringUtil.bigDecimalToString(paymentInfo.getExcessAmount(), StringUtil.GLOBAL_DECIMAL_FORMAT));
			excessAmountLabel.setId("excessAmountLabel");
			cashDetail2.appendChild(excessAmountLabel);
			((Row)this.getFellow("cashDetail_1")).getParent().appendChild(cashDetail2);
		}
		else if(paymentInfo.getPaymentMode().equals(NonConfigurableConstants.PAYMENT_MODE_CONTRA)){
			((Row)this.getFellow("contraDetail_1")).setVisible(true);
			Label contraReceiptDate = (Label)this.getFellow("contraReceiptDate");
			contraReceiptDate.setValue(DateUtil.convertTimestampToStr(paymentInfo.getReceiptDt(), DateUtil.GLOBAL_DATE_FORMAT));
			Label contraPaymentAmountLabel = (Label)this.getFellow("contraPaymentAmountLabel");
			contraPaymentAmountLabel.setValue(StringUtil.bigDecimalToString(paymentInfo.getPaymentAmount(), StringUtil.GLOBAL_DECIMAL_FORMAT));
			
			Row contraDetail2 = new Row();
			Label excessLabel = new Label("Excess Amount($)");
			excessLabel.setStyle("font-weight: bold");
			excessLabel.setSclass("fieldLabel");
			contraDetail2.appendChild(excessLabel);
			Label excessAmountLabel = new Label(StringUtil.bigDecimalToString(paymentInfo.getExcessAmount(), StringUtil.GLOBAL_DECIMAL_FORMAT));
			excessAmountLabel.setId("excessAmountLabel");
			contraDetail2.appendChild(excessAmountLabel);
			((Row)this.getFellow("cashDetail_1")).getParent().appendChild(contraDetail2);
		}
		else if(paymentInfo.getPaymentMode().equals(NonConfigurableConstants.PAYMENT_MODE_CHEQUE)){
			((Row)this.getFellow("cqDetail_1")).setVisible(true);
			Label cqReceiptDateLabel = (Label)this.getFellow("cqReceiptDateLabel");
			cqReceiptDateLabel.setValue(DateUtil.convertTimestampToStr(paymentInfo.getReceiptDt(), DateUtil.GLOBAL_DATE_FORMAT));
			Label cqChequeDateBoxLabel = (Label)this.getFellow("cqChequeDateBoxLabel");
			cqChequeDateBoxLabel.setValue(DateUtil.convertDateToStr(paymentInfo.getChequeDate(), DateUtil.GLOBAL_DATE_FORMAT));
			
			((Row)this.getFellow("cqDetail_2")).setVisible(true);
			Label cqChequeNoLabel = (Label)this.getFellow("cqChequeNoLabel");
			cqChequeNoLabel.setValue(paymentInfo.getChequeNo());
			Label cqQuickChequeDepositLabel = (Label)this.getFellow("cqQuickChequeDepositLabel");
			cqQuickChequeDepositLabel.setValue(paymentInfo.isQuickChequeDeposit()==true?"YES":"NO");
			
			((Row)this.getFellow("cqDetail_3")).setVisible(true);
			Label cqBankLabel = (Label)this.getFellow("cqBankLabel");
			cqBankLabel.setValue(((MstbBankMaster)MasterSetup.getBankManager().getMaster(paymentInfo.getBankNo())).getBankName());
			Label cqBranchLabel = (Label)this.getFellow("cqBranchLabel");
			cqBranchLabel.setValue(((MstbBranchMaster)MasterSetup.getBankManager().getDetail(paymentInfo.getBranchNo())).getBranchName());
			
			((Row)this.getFellow("cqDetail_4")).setVisible(true);
			Label cqPaymentAmountLabel = (Label)this.getFellow("cqPaymentAmountLabel");
			cqPaymentAmountLabel.setValue(StringUtil.bigDecimalToString(paymentInfo.getPaymentAmount(), StringUtil.GLOBAL_DECIMAL_FORMAT));
			Label excessLabel = new Label("Excess Amount($)");
			excessLabel.setStyle("font-weight: bold");
			excessLabel.setSclass("fieldLabel");
			((Row)this.getFellow("cqDetail_4")).appendChild(excessLabel);
			Label excessAmountLabel = new Label(StringUtil.bigDecimalToString(paymentInfo.getExcessAmount(), StringUtil.GLOBAL_DECIMAL_FORMAT));
			excessAmountLabel.setId("excessAmountLabel");
			((Row)this.getFellow("cqDetail_4")).appendChild(excessAmountLabel);
		}
		else if(paymentInfo.getPaymentMode().equals(NonConfigurableConstants.PAYMENT_MODE_CREDIT_CARD)){
			((Row)this.getFellow("creditCardDetail_1")).setVisible(true);
			Label ccReceiptDateLabel = (Label)this.getFellow("ccReceiptDateLabel");
			ccReceiptDateLabel.setValue(DateUtil.convertTimestampToStr(paymentInfo.getReceiptDt(), DateUtil.GLOBAL_DATE_FORMAT));
			Label ccTxnRefNoLabel = (Label)this.getFellow("ccTxnRefNoLabel");
			ccTxnRefNoLabel.setValue(paymentInfo.getTxnRefNo());
			
			((Row)this.getFellow("creditCardDetail_2")).setVisible(true);
			Label ccPaymentAmountLabel = (Label)this.getFellow("ccPaymentAmountLabel");
			ccPaymentAmountLabel.setValue(StringUtil.bigDecimalToString(paymentInfo.getPaymentAmount(), StringUtil.GLOBAL_DECIMAL_FORMAT));
			Label excessLabel = new Label("Excess Amount($)");
			excessLabel.setStyle("font-weight: bold");
			excessLabel.setSclass("fieldLabel");
			((Row)this.getFellow("creditCardDetail_2")).appendChild(excessLabel);
			Label excessAmountLabel = new Label(StringUtil.bigDecimalToString(paymentInfo.getExcessAmount(), StringUtil.GLOBAL_DECIMAL_FORMAT));
			excessAmountLabel.setId("excessAmountLabel");
			((Row)this.getFellow("creditCardDetail_2")).appendChild(excessAmountLabel);
		}
		else if(paymentInfo.getPaymentMode().equals(NonConfigurableConstants.PAYMENT_MODE_GIRO)){
			((Row)this.getFellow("giroDetail_1")).setVisible(true);
			Label grReceiptDateLabel = (Label)this.getFellow("grReceiptDateLabel");
			grReceiptDateLabel.setValue(DateUtil.convertTimestampToStr(paymentInfo.getReceiptDt(), DateUtil.GLOBAL_DATE_FORMAT));
			Label grTxnRefNoLabel = (Label)this.getFellow("grTxnRefNoLabel");
			grTxnRefNoLabel.setValue(paymentInfo.getTxnRefNo());
			
			((Row)this.getFellow("giroDetail_2")).setVisible(true);
			Label grBankLabel = (Label)this.getFellow("grBankLabel");
			if(paymentInfo.getBankNo()!=null)
				grBankLabel.setValue(((MstbBankMaster)MasterSetup.getBankManager().getMaster(paymentInfo.getBankNo())).getBankName());
			else
				grBankLabel.setValue("-");
			Label grBranchLabel = (Label)this.getFellow("grBranchLabel");
			if(paymentInfo.getBranchNo()!=null)
				grBranchLabel.setValue(((MstbBranchMaster)MasterSetup.getBankManager().getDetail(paymentInfo.getBranchNo())).getBranchName());
			else
				grBranchLabel.setValue("-");
			
			((Row)this.getFellow("giroDetail_3")).setVisible(true);
			Label grPaymentAmountLabel = (Label)this.getFellow("grPaymentAmountLabel");
			grPaymentAmountLabel.setValue(StringUtil.bigDecimalToString(paymentInfo.getPaymentAmount(), StringUtil.GLOBAL_DECIMAL_FORMAT));
			Label excessLabel = new Label("Excess Amount($)");
			excessLabel.setStyle("font-weight: bold");
			excessLabel.setSclass("fieldLabel");
			((Row)this.getFellow("giroDetail_3")).appendChild(excessLabel);
			Label excessAmountLabel = new Label(StringUtil.bigDecimalToString(paymentInfo.getExcessAmount(), StringUtil.GLOBAL_DECIMAL_FORMAT));
			excessAmountLabel.setId("excessAmountLabel");
			((Row)this.getFellow("giroDetail_3")).appendChild(excessAmountLabel);
		}
		
		//Invoice Detail Section
		Label invoiceNoLabel = (Label)this.getFellow("invoiceNoLabel");
		invoiceNoLabel.setValue(invoicePaymentDetail.getInvoiceHeader().getInvoiceNo().toString());
		Label totalInvoiceAmountLabel = (Label)this.getFellow("totalInvoiceAmountLabel");
		totalInvoiceAmountLabel.setValue(StringUtil.bigDecimalToString(invoicePaymentDetail.getInvoiceHeader().getNewTxn(), StringUtil.GLOBAL_DECIMAL_FORMAT));
		Label outstandingAmountLabel = (Label)this.getFellow("outstandingAmountLabel");
		outstandingAmountLabel.setValue(StringUtil.bigDecimalToString(invoicePaymentDetail.getInvoiceHeader().getOutstandingAmount(), StringUtil.GLOBAL_DECIMAL_FORMAT));
		Label invoiceStatusLabel = (Label)this.getFellow("invoiceStatusLabel");
		invoiceStatusLabel.setValue(NonConfigurableConstants.INVOICE_STATUS.get(invoicePaymentDetail.getInvoiceHeader().getInvoiceStatus()));
		
		Listbox multiUsageProductsList = (Listbox)this.getFellow("multiUsageProductsList");
		Listbox otherProductsList = (Listbox)this.getFellow("otherProductsList");
		Listbox miscList = (Listbox)this.getFellow("miscList");
		Div multiUsageProductsDiv = (Div)this.getFellow("multiUsageProductsDiv");
		Div otherProductsDiv = (Div)this.getFellow("otherProductsDiv");
		Div miscDiv = (Div)this.getFellow("miscDiv");
		
		BigDecimal totalAppliedAmount = new BigDecimal("0.00");
		Set<BmtbInvoiceSummary> invoiceSummaries = this.invoicePaymentDetail.getInvoiceHeader().getBmtbInvoiceSummaries();
		for(BmtbInvoiceSummary invoiceSummary : invoiceSummaries){
			Set<BmtbInvoiceDetail> invoiceDetails = invoiceSummary.getBmtbInvoiceDetails();
			for(BmtbInvoiceDetail invoiceDetail : invoiceDetails){
				
				//0 outstanding amount, therefore we do not need to show
				if(invoiceDetail.getOutstandingAmount().compareTo(new BigDecimal(0)) == 0)
					continue;
				
				//Misc
				if(invoiceDetail.getPmtbProductType()==null ||
						invoiceDetail.getPmtbProductType().getPrepaid().equals(NonConfigurableConstants.BOOLEAN_YN_YES)
						){
					Listitem item = new Listitem();
					item.setValue(invoiceDetail);
					item.appendChild(newListcell(invoiceDetail.getInvoiceDetailName()));
					item.appendChild(newListcell(invoiceDetail.getAmtbAccount().getAccountName()));
					item.appendChild(newListcell(NonConfigurableConstants.ACCOUNT_CATEGORY.get(invoiceDetail.getAmtbAccount().getAccountCategory())));
					item.appendChild(newListcell(invoiceDetail.getTotalNew(), StringUtil.GLOBAL_DECIMAL_FORMAT));
					BigDecimal amountApplied = invoiceDetail.getTotalNew().subtract(invoiceDetail.getOutstandingAmount());
					item.appendChild(newListcell(amountApplied, StringUtil.GLOBAL_DECIMAL_FORMAT));
					item.appendChild(newListcell(invoiceDetail.getOutstandingAmount(), StringUtil.GLOBAL_DECIMAL_FORMAT));
					
					//Apply Amount Decimal Box
					Listcell applyAmountListCell = new Listcell();
					Decimalbox applyAmountDecimalBox = new Decimalbox();
					applyAmountDecimalBox.setFormat("#,##0.00");
					applyAmountDecimalBox.setConstraint(new OverPaymentConstraint(invoiceDetail.getOutstandingAmount()));
					applyAmountDecimalBox.addEventListener(Events.ON_CHANGE, new EventListener() {
						public boolean isAsap() {
						    return true;
						}
						
			            public void onEvent(Event event) throws Exception {
			            	ApplyPartialPaymentWindow window = (ApplyPartialPaymentWindow)event.getTarget().getPage().getFellow(ApplyPartialPaymentWindow.SELF);
			            	window.calculateTotalAppliedAmount();
			            }
			        });
					applyAmountDecimalBox.setStyle("text-align:right");
					
					//If it's discount amount, user cannot apply it
					if(invoiceDetail.getInvoiceDetailType().equals(NonConfigurableConstants.INVOICE_SUMMARY_DETAIL_TYPE_EARLY_PAYMENT) ||
							invoiceDetail.getInvoiceDetailType().equals(NonConfigurableConstants.INVOICE_SUMMARY_DETAIL_TYPE_VOLUME_DISCOUNT) ||
							(invoiceDetail.getInvoiceDetailType().equals(NonConfigurableConstants.INVOICE_SUMMARY_DETAIL_TYPE_CREDIT_DEBIT_NOTE)
							&& invoiceDetail.getOutstandingAmount().compareTo(BigDecimal.ZERO)<0) ||
							invoiceDetail.getInvoiceDetailType().equals(NonConfigurableConstants.INVOICE_SUMMARY_DETAIL_TYPE_PREPAID_DISCOUNT) ||
							invoiceDetail.getInvoiceDetailType().equals(NonConfigurableConstants.INVOICE_SUMMARY_DETAIL_TYPE_PREPAID_ISSUANCE_FEE_WAIVAL) ||
							invoiceDetail.getInvoiceDetailType().equals(NonConfigurableConstants.INVOICE_SUMMARY_DETAIL_TYPE_PREPAID_TOP_UP_FEE_WAIVAL) 
							
							){
						applyAmountDecimalBox.setConstraint(new NegativeAppliedAmountConstraint(invoiceDetail.getOutstandingAmount()));
					}
					
					BigDecimal appliedAmount = this.invoicePaymentDetail.getInvoiceDetailAppliedAmount().get(invoiceDetail.getInvoiceDetailNo());
					if(appliedAmount!=null){
						applyAmountDecimalBox.setValue(appliedAmount);
						totalAppliedAmount = totalAppliedAmount.add(appliedAmount);
					}
						
					applyAmountListCell.appendChild(applyAmountDecimalBox);
					item.appendChild(applyAmountListCell);
					miscList.appendChild(item);
					miscDiv.setVisible(true);
				}
				//Multi Usage Products
				else if(invoiceDetail.getPmtbProductType().getOneTimeUsage().equals(NonConfigurableConstants.BOOLEAN_YN_NO) &&
						invoiceDetail.getPmtbProductType().getIssuable().equals(NonConfigurableConstants.BOOLEAN_YN_YES)){
					//If this card has no new transaction, proceed to display next card
					if(invoiceDetail.getTotalNew().compareTo(new BigDecimal(0)) == 0) continue;
					
					Listitem item = new Listitem();
					item.setValue(invoiceDetail);
					item.appendChild(newListcell(invoiceDetail.getCardNo()));
					item.appendChild(new Listcell(invoiceDetail.getCardName()));
					item.appendChild(newListcell(invoiceDetail.getAmtbAccount().getAccountName()));
					item.appendChild(newListcell(NonConfigurableConstants.ACCOUNT_CATEGORY.get(invoiceDetail.getAmtbAccount().getAccountCategory())));
					item.appendChild(newListcell(invoiceDetail.getNewTxn(), StringUtil.GLOBAL_DECIMAL_FORMAT));
					item.appendChild(newListcell(invoiceDetail.getAdminFee(), StringUtil.GLOBAL_DECIMAL_FORMAT));
					item.appendChild(newListcell(invoiceDetail.getGst(), StringUtil.GLOBAL_DECIMAL_FORMAT));
					item.appendChild(newListcell(invoiceDetail.getTotalNew(), StringUtil.GLOBAL_DECIMAL_FORMAT));
					BigDecimal amountApplied = invoiceDetail.getTotalNew().subtract(invoiceDetail.getOutstandingAmount());
					item.appendChild(newListcell(amountApplied, StringUtil.GLOBAL_DECIMAL_FORMAT));
					item.appendChild(newListcell(invoiceDetail.getOutstandingAmount(), StringUtil.GLOBAL_DECIMAL_FORMAT));
					
					//Apply Amount Decimal Box
					Listcell applyAmountListCell = new Listcell();
					Decimalbox applyAmountDecimalBox = new Decimalbox();
					applyAmountDecimalBox.setFormat("#,##0.00");
					applyAmountDecimalBox.setConstraint(new OverPaymentConstraint(invoiceDetail.getOutstandingAmount()));
					applyAmountDecimalBox.addEventListener(Events.ON_CHANGE, new EventListener() {
						public boolean isAsap() {
						    return true;
						}
						
			            public void onEvent(Event event) throws Exception {
			            	ApplyPartialPaymentWindow window = (ApplyPartialPaymentWindow)event.getTarget().getPage().getFellow(ApplyPartialPaymentWindow.SELF);
			            	window.calculateTotalAppliedAmount();
			            }
			        });
					BigDecimal appliedAmount = this.invoicePaymentDetail.getInvoiceDetailAppliedAmount().get(invoiceDetail.getInvoiceDetailNo());
					if(appliedAmount!=null){
						applyAmountDecimalBox.setValue(appliedAmount);
						totalAppliedAmount = totalAppliedAmount.add(appliedAmount);
					}
					applyAmountDecimalBox.setStyle("text-align:right");
					applyAmountListCell.appendChild(applyAmountDecimalBox);
					item.appendChild(applyAmountListCell);
					multiUsageProductsList.appendChild(item);
					multiUsageProductsDiv.setVisible(true);
				}
				//Other Products
				else{
					Listitem item = new Listitem();
					item.setValue(invoiceDetail);
					item.appendChild(newListcell(invoiceDetail.getInvoiceDetailName()));
					item.appendChild(newListcell(invoiceDetail.getAmtbAccount().getAccountName()));
					item.appendChild(newListcell(NonConfigurableConstants.ACCOUNT_CATEGORY.get(invoiceDetail.getAmtbAccount().getAccountCategory())));
					item.appendChild(newListcell(invoiceDetail.getTotalNew(), StringUtil.GLOBAL_DECIMAL_FORMAT));
					BigDecimal amountApplied = invoiceDetail.getTotalNew().subtract(invoiceDetail.getOutstandingAmount());
					item.appendChild(newListcell(amountApplied, StringUtil.GLOBAL_DECIMAL_FORMAT));
					item.appendChild(newListcell(invoiceDetail.getOutstandingAmount(), StringUtil.GLOBAL_DECIMAL_FORMAT));
					
					//Apply Amount Decimal Box
					Listcell applyAmountListCell = new Listcell();
					Decimalbox applyAmountDecimalBox = new Decimalbox();
					applyAmountDecimalBox.setFormat("#,##0.00");
					applyAmountDecimalBox.setConstraint(new OverPaymentConstraint(invoiceDetail.getOutstandingAmount()));
					applyAmountDecimalBox.addEventListener(Events.ON_CHANGE, new EventListener() {
						public boolean isAsap() {
						    return true;
						}
						
			            public void onEvent(Event event) throws Exception {
			            	ApplyPartialPaymentWindow window = (ApplyPartialPaymentWindow)event.getTarget().getPage().getFellow(ApplyPartialPaymentWindow.SELF);
			            	window.calculateTotalAppliedAmount();
			            }
			        });
					applyAmountDecimalBox.setStyle("text-align:right");
					BigDecimal appliedAmount = this.invoicePaymentDetail.getInvoiceDetailAppliedAmount().get(invoiceDetail.getInvoiceDetailNo());
					if(appliedAmount!=null){
						applyAmountDecimalBox.setValue(appliedAmount);
						totalAppliedAmount = totalAppliedAmount.add(appliedAmount);
					}
					applyAmountListCell.appendChild(applyAmountDecimalBox);
					item.appendChild(applyAmountListCell);
					otherProductsList.appendChild(item);
					otherProductsDiv.setVisible(true);
				}
			}
		}
		
		//Update the total applied amount label
		Label totalAppliedAmountLabel = (Label)this.getFellow("totalAppliedAmountLabel");
		totalAppliedAmountLabel.setValue(StringUtil.bigDecimalToString(totalAppliedAmount, StringUtil.GLOBAL_DECIMAL_FORMAT));
	}
	
	public void apply() throws InterruptedException{
		logger.info("");
		
		try{
			Map<Long, BigDecimal> paymentAppliedMap = new HashMap<Long, BigDecimal>();
			Map<Long, BmtbInvoiceDetail> invoiceDetailAppliedMap = new HashMap<Long, BmtbInvoiceDetail>();
			Listbox multiUsageProductsList = (Listbox)this.getFellow("multiUsageProductsList");
			Listbox otherProductsList = (Listbox)this.getFellow("otherProductsList");
			Listbox miscList = (Listbox)this.getFellow("miscList");
			BigDecimal totalAppliedAmount = new BigDecimal(0);
			
			List<Listitem> multiUsagelistItems = multiUsageProductsList.getItems();
			for(Listitem listItem : multiUsagelistItems){
				Decimalbox applyAmountDecimalBox = (Decimalbox)((Listcell)listItem.getLastChild()).getLastChild();
				if(applyAmountDecimalBox.getValue()!=null){
					totalAppliedAmount = totalAppliedAmount.add(applyAmountDecimalBox.getValue());
					paymentAppliedMap.put(((BmtbInvoiceDetail)listItem.getValue()).getInvoiceDetailNo(), applyAmountDecimalBox.getValue());
					invoiceDetailAppliedMap.put(((BmtbInvoiceDetail)listItem.getValue()).getInvoiceDetailNo(), (BmtbInvoiceDetail)listItem.getValue());
				}
			}
			
			List<Listitem> otherProductslistItems = otherProductsList.getItems();
			for(Listitem listItem : otherProductslistItems){
				Decimalbox applyAmountDecimalBox = (Decimalbox)((Listcell)listItem.getLastChild()).getLastChild();
				if(applyAmountDecimalBox.getValue()!=null){
					totalAppliedAmount = totalAppliedAmount.add(applyAmountDecimalBox.getValue());
					paymentAppliedMap.put(((BmtbInvoiceDetail)listItem.getValue()).getInvoiceDetailNo(), applyAmountDecimalBox.getValue());
					invoiceDetailAppliedMap.put(((BmtbInvoiceDetail)listItem.getValue()).getInvoiceDetailNo(), (BmtbInvoiceDetail)listItem.getValue());
				}
			}
			
			List<Listitem> misclistItems = miscList.getItems();
			for(Listitem listItem : misclistItems){
				Decimalbox applyAmountDecimalBox = (Decimalbox)((Listcell)listItem.getLastChild()).getLastChild();
				if(!applyAmountDecimalBox.isDisabled()){
					if(applyAmountDecimalBox.getValue()!=null){
						totalAppliedAmount = totalAppliedAmount.add(applyAmountDecimalBox.getValue());
						paymentAppliedMap.put(((BmtbInvoiceDetail)listItem.getValue()).getInvoiceDetailNo(), applyAmountDecimalBox.getValue());
						invoiceDetailAppliedMap.put(((BmtbInvoiceDetail)listItem.getValue()).getInvoiceDetailNo(), (BmtbInvoiceDetail)listItem.getValue());
					}
				}
			}
			
			this.invoicePaymentDetail.setInvoiceDetailAppliedAmount(paymentAppliedMap);
			this.invoicePaymentDetail.setInvoiceDetailApplied(invoiceDetailAppliedMap);
			this.invoicePaymentDetail.setTotalAppliedAmount(totalAppliedAmount);
			
			CommonWindow window = this.back();
			if(window instanceof CreatePaymentReceiptWindow)
				((CreatePaymentReceiptWindow)window).updateAppliedAmount(invoicePaymentDetail);
			else if(window instanceof ApplyExcessAmountPaymentReceiptWindow)
				((ApplyExcessAmountPaymentReceiptWindow)window).updateAppliedAmount(invoicePaymentDetail);
		}
		catch(WrongValueException wve){
			throw wve;
		}
		catch(Exception e){
			Messagebox.show(com.cdgtaxi.ibs.common.NonConfigurableConstants.COMMON_ERROR_MESSAGE, 
					"Error", Messagebox.OK, Messagebox.ERROR);
			e.printStackTrace();
		}
	}
	
	public void clearAll() throws InterruptedException{
		logger.info("");
		
		try{
			Listbox multiUsageProductsList = (Listbox)this.getFellow("multiUsageProductsList");
			Listbox otherProductsList = (Listbox)this.getFellow("otherProductsList");
			Listbox miscList = (Listbox)this.getFellow("miscList");
			
			List<Listitem> multiUsagelistItems = multiUsageProductsList.getItems();
			for(Listitem listItem : multiUsagelistItems){
				Decimalbox applyAmountDecimalBox = (Decimalbox)((Listcell)listItem.getLastChild()).getLastChild();
				applyAmountDecimalBox.setValue(null);
			}
			
			List<Listitem> otherProductslistItems = otherProductsList.getItems();
			for(Listitem listItem : otherProductslistItems){
				Decimalbox applyAmountDecimalBox = (Decimalbox)((Listcell)listItem.getLastChild()).getLastChild();
				applyAmountDecimalBox.setValue(null);
			}
			
			List<Listitem> misclistItems = miscList.getItems();
			for(Listitem listItem : misclistItems){
				Decimalbox applyAmountDecimalBox = (Decimalbox)((Listcell)listItem.getLastChild()).getLastChild();
				if(!applyAmountDecimalBox.isDisabled())
					applyAmountDecimalBox.setValue(null);
			}
			
			this.invoicePaymentDetail.setInvoiceDetailAppliedAmount(new HashMap());
			this.invoicePaymentDetail.setTotalAppliedAmount(new BigDecimal(0));
			
			//reset label amount
			Label totalAppliedAmountLabel = (Label)this.getFellow("totalAppliedAmountLabel");
			totalAppliedAmountLabel.setValue("0.00");
		}
		catch(WrongValueException wve){
			throw wve;
		}
		catch(Exception e){
			Messagebox.show(com.cdgtaxi.ibs.common.NonConfigurableConstants.COMMON_ERROR_MESSAGE, 
					"Error", Messagebox.OK, Messagebox.ERROR);
			e.printStackTrace();
		}
	}
	
	public void back2() throws InterruptedException{
		CommonWindow window = this.back();
		if(window instanceof CreatePaymentReceiptWindow)
			((CreatePaymentReceiptWindow)window).clearSelection(this.invoicePaymentDetail.isFullPayment());
		else if(window instanceof ApplyExcessAmountPaymentReceiptWindow)
			((ApplyExcessAmountPaymentReceiptWindow)window).clearSelection(this.invoicePaymentDetail.isFullPayment());
	}
	
	public void calculateTotalAppliedAmount() throws InterruptedException{
		logger.info("");
		
		try{
			Listbox multiUsageProductsList = (Listbox)this.getFellow("multiUsageProductsList");
			Listbox otherProductsList = (Listbox)this.getFellow("otherProductsList");
			Listbox miscList = (Listbox)this.getFellow("miscList");
			Label totalAppliedAmountLabel = (Label)this.getFellow("totalAppliedAmountLabel");
			BigDecimal totalAppliedAmount = new BigDecimal("0.00");
			
			List<Listitem> multiUsagelistItems = multiUsageProductsList.getItems();
			for(Listitem listItem : multiUsagelistItems){
				Decimalbox applyAmountDecimalBox = (Decimalbox)((Listcell)listItem.getLastChild()).getLastChild();
				BigDecimal amount = applyAmountDecimalBox.getValue();
				if(amount!=null) totalAppliedAmount = totalAppliedAmount.add(amount);
			}
			
			List<Listitem> otherProductslistItems = otherProductsList.getItems();
			for(Listitem listItem : otherProductslistItems){
				Decimalbox applyAmountDecimalBox = (Decimalbox)((Listcell)listItem.getLastChild()).getLastChild();
				BigDecimal amount = applyAmountDecimalBox.getValue();
				if(amount!=null) totalAppliedAmount = totalAppliedAmount.add(amount);
			}
			
			List<Listitem> misclistItems = miscList.getItems();
			for(Listitem listItem : misclistItems){
				Decimalbox applyAmountDecimalBox = (Decimalbox)((Listcell)listItem.getLastChild()).getLastChild();
				BigDecimal amount = applyAmountDecimalBox.getValue();
				if(amount!=null) totalAppliedAmount = totalAppliedAmount.add(amount);
			}
			
			totalAppliedAmountLabel.setValue(StringUtil.bigDecimalToString(totalAppliedAmount, StringUtil.GLOBAL_DECIMAL_FORMAT));
		}
		catch(WrongValueException wve){
			throw wve;
		}
		catch(Exception e){
			Messagebox.show(com.cdgtaxi.ibs.common.NonConfigurableConstants.COMMON_ERROR_MESSAGE, 
					"Error", Messagebox.OK, Messagebox.ERROR);
			e.printStackTrace();
		}
	}
	
	public void autoPopulate() throws SuspendNotAllowedException, InterruptedException{
		Map map = new HashMap();
		map.put("totalOutstandingAmount", this.invoicePaymentDetail.getInvoiceHeader().getOutstandingAmount().add(this.invoicePaymentDetail.getMinAppliedAmount()));
		final AutoPopulatePaymentWindow window = (AutoPopulatePaymentWindow) Executions.createComponents(Uri.AUTO_POPULATE_PAYMENT, null, map);
		window.setMaximizable(false);
		window.doModal();
		
		BigDecimal autoPopulateAmount = window.getPaymentAmount();
		//user click cancels
		if(autoPopulateAmount==null)return;
		else autoPopulateAmount = autoPopulateAmount.add(this.invoicePaymentDetail.getMinAppliedAmount());
		this.clearAll();
		
		logger.info("Auto Populate Amount:"+autoPopulateAmount);
		
		Listbox multiUsageProductsList = (Listbox)this.getFellow("multiUsageProductsList");
		Listbox otherProductsList = (Listbox)this.getFellow("otherProductsList");
		Listbox miscList = (Listbox)this.getFellow("miscList");
		
		List<Listitem> multiUsagelistItems = multiUsageProductsList.getItems();
		for(Listitem listItem : multiUsagelistItems){
			if(autoPopulateAmount.compareTo(new BigDecimal(0))==0) break;
			
			Decimalbox applyAmountDecimalBox = (Decimalbox)((Listcell)listItem.getLastChild()).getLastChild();
			BmtbInvoiceDetail invoiceDetail = (BmtbInvoiceDetail)listItem.getValue();
			
			if(autoPopulateAmount.compareTo(invoiceDetail.getOutstandingAmount())>=0){
				applyAmountDecimalBox.setValue(invoiceDetail.getOutstandingAmount());
				autoPopulateAmount = autoPopulateAmount.subtract(invoiceDetail.getOutstandingAmount());
			}
			else{
				applyAmountDecimalBox.setValue(autoPopulateAmount);
				autoPopulateAmount = autoPopulateAmount.subtract(autoPopulateAmount);
			}
		}
		
		List<Listitem> otherProductslistItems = otherProductsList.getItems();
		for(Listitem listItem : otherProductslistItems){
			if(autoPopulateAmount.compareTo(new BigDecimal(0))==0) break;
			
			Decimalbox applyAmountDecimalBox = (Decimalbox)((Listcell)listItem.getLastChild()).getLastChild();
			BmtbInvoiceDetail invoiceDetail = (BmtbInvoiceDetail)listItem.getValue();
			
			if(autoPopulateAmount.compareTo(invoiceDetail.getOutstandingAmount())>=0){
				applyAmountDecimalBox.setValue(invoiceDetail.getOutstandingAmount());
				autoPopulateAmount = autoPopulateAmount.subtract(invoiceDetail.getOutstandingAmount());
			}
			else{
				applyAmountDecimalBox.setValue(autoPopulateAmount);
				autoPopulateAmount = autoPopulateAmount.subtract(autoPopulateAmount);
			}
		}
		
		List<Listitem> misclistItems = miscList.getItems();
		for(Listitem listItem : misclistItems){
			//Cannot break here as we need to populate the negative amount
			//if(autoPopulateAmount.compareTo(new BigDecimal(0))==0) break;
			
			Decimalbox applyAmountDecimalBox = (Decimalbox)((Listcell)listItem.getLastChild()).getLastChild();
			BmtbInvoiceDetail invoiceDetail = (BmtbInvoiceDetail)listItem.getValue();
			if(invoiceDetail.getOutstandingAmount().compareTo(BigDecimal.ZERO) > 0){
				//instead we continue
				if(autoPopulateAmount.compareTo(new BigDecimal(0))==0) continue;
				
				if(autoPopulateAmount.compareTo(invoiceDetail.getOutstandingAmount())>=0){
					applyAmountDecimalBox.setValue(invoiceDetail.getOutstandingAmount());
					autoPopulateAmount = autoPopulateAmount.subtract(invoiceDetail.getOutstandingAmount());
				}
				else{
					applyAmountDecimalBox.setValue(autoPopulateAmount);
					autoPopulateAmount = autoPopulateAmount.subtract(autoPopulateAmount);
				}
			}
			else{
				//Geok Hua: Even if user enetered $1, all the negative amount must be auto populated
				applyAmountDecimalBox.setValue(invoiceDetail.getOutstandingAmount());
			}
		}
		
		this.calculateTotalAppliedAmount();
	}
}
