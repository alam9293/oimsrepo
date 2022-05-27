package com.cdgtaxi.ibs.billing.ui;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;
import org.springframework.orm.hibernate5.HibernateOptimisticLockingFailureException;
import org.zkoss.zhtml.Messagebox;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.ext.AfterCompose;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Label;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Row;

import com.cdgtaxi.ibs.Uri;
import com.cdgtaxi.ibs.common.NonConfigurableConstants;
import com.cdgtaxi.ibs.common.model.AmtbAccount;
import com.cdgtaxi.ibs.common.model.BmtbPaymentReceipt;
import com.cdgtaxi.ibs.common.model.BmtbPaymentReceiptDetail;
import com.cdgtaxi.ibs.common.model.FmtbBankCode;
import com.cdgtaxi.ibs.common.model.forms.InvoicePaymentDetail;
import com.cdgtaxi.ibs.common.ui.CommonWindow;
import com.cdgtaxi.ibs.util.ComponentUtil;
import com.cdgtaxi.ibs.util.DateUtil;
import com.cdgtaxi.ibs.util.StringUtil;

public class ApplyExcessAmountPaymentReceiptWindow extends CommonWindow implements AfterCompose{

	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(ApplyExcessAmountPaymentReceiptWindow.class);
	private static final String SELF = "applyExcessAmountPaymentReceiptWindow";
	private Map<String, ArrayList<Component>> paymentDetailMap = new HashMap<String, ArrayList<Component>>();
	private BmtbPaymentReceipt paymentReceipt;
	private BigDecimal previousAppliedAmount = new BigDecimal(0);
	private BigDecimal selectedAmount = new BigDecimal(0);
	
	public ApplyExcessAmountPaymentReceiptWindow(){
		Map params = Executions.getCurrent().getArg();
		paymentReceipt = (BmtbPaymentReceipt)params.get("paymentReceipt");
		if(paymentReceipt == null)
			throw new NullPointerException("Payment Receipt Object not found!"); //This should not happen at all
	}
	
	public void registerPaymentDetail(Component component, String key){
		ArrayList<Component> paymentDetail = this.paymentDetailMap.get(key);
		if(paymentDetail==null) paymentDetail = new ArrayList<Component>();
		paymentDetail.add(component);
		this.paymentDetailMap.put(key, paymentDetail);
	}
	
	//After the components are created, we initialize those label values from previous input screen
	public void afterCompose() {
		
		AmtbAccount account = paymentReceipt.getAmtbAccount();
		//Payment Receipt Section
		Label receiptNoLabel = (Label)this.getFellow("receiptNoLabel");
		receiptNoLabel.setValue(paymentReceipt.getPaymentReceiptNo().toString());
		
		Label accountNoLabel = (Label)this.getFellow("accountNoLabel");
		Label accountNameLabel = (Label)this.getFellow("accountNameLabel");
		if(account.getAccountCategory().equals(NonConfigurableConstants.ACCOUNT_CATEGORY_DEPARTMENT)){
			Row divisionDepartmentRow = (Row)this.getFellow("divisionDepartmentRow");
			divisionDepartmentRow.setVisible(true);
			Label departmentNameLabel = (Label)this.getFellow("departmentNameLabel");
			departmentNameLabel.setValue(account.getAccountName());
			Label divisionNameLabel = (Label)this.getFellow("divisionNameLabel");
			divisionNameLabel.setValue(account.getAmtbAccount().getAccountName());
			accountNoLabel.setValue(account.getAmtbAccount().getAmtbAccount().getCustNo());
			accountNameLabel.setValue(account.getAmtbAccount().getAmtbAccount().getAccountName());
		}
		else if(account.getAccountCategory().equals(NonConfigurableConstants.ACCOUNT_CATEGORY_DIVISION)){
			Row divisionDepartmentRow = (Row)this.getFellow("divisionDepartmentRow");
			divisionDepartmentRow.setVisible(true);
			Label divisionNameLabel = (Label)this.getFellow("divisionNameLabel");
			divisionNameLabel.setValue(account.getAccountName());
			accountNoLabel.setValue(account.getAmtbAccount().getCustNo());
			accountNameLabel.setValue(account.getAmtbAccount().getAccountName());
		}
		else{
			accountNoLabel.setValue(account.getCustNo());
			accountNameLabel.setValue(account.getAccountName());
		}
		
		Label paymentModeLabel = (Label)this.getFellow("paymentModeLabel");
		paymentModeLabel.setValue(paymentReceipt.getMstbMasterTableByPaymentMode().getMasterValue());
		Label paymentDateLabel = (Label)this.getFellow("paymentDateLabel");
		paymentDateLabel.setValue(DateUtil.convertDateToStr(paymentReceipt.getPaymentDate(), DateUtil.GLOBAL_DATE_FORMAT));
		
		if(NonConfigurableConstants.specificPaymentModes.get(paymentReceipt.getMstbMasterTableByPaymentMode().getMasterCode()) == null){
			((Row)this.getFellow("othersDetail_1")).setVisible(true);
			Label othersReceiptDateLabel = (Label)this.getFellow("othersReceiptDateLabel");
			othersReceiptDateLabel.setValue(DateUtil.convertTimestampToStr(paymentReceipt.getReceiptDt(), DateUtil.GLOBAL_DATE_FORMAT));
			Label othersTxnRefNoLabel = (Label)this.getFellow("othersTxnRefNoLabel");
			othersTxnRefNoLabel.setValue(paymentReceipt.getPaymentNo());
			
			((Row)this.getFellow("othersDetail_2")).setVisible(true);
			Label othersPaymentAmountLabel = (Label)this.getFellow("othersPaymentAmountLabel");
			othersPaymentAmountLabel.setValue(StringUtil.bigDecimalToString(paymentReceipt.getPaymentAmount(), StringUtil.GLOBAL_DECIMAL_FORMAT));
		}
		else if(paymentReceipt.getMstbMasterTableByPaymentMode().getMasterCode().equals(NonConfigurableConstants.PAYMENT_MODE_AXS)){
			((Row)this.getFellow("axsDetail_1")).setVisible(true);
			Label axsReceiptDateLabel = (Label)this.getFellow("axsReceiptDateLabel");
			axsReceiptDateLabel.setValue(DateUtil.convertTimestampToStr(paymentReceipt.getReceiptDt(), DateUtil.GLOBAL_DATE_FORMAT));
			Label axsTxnRefNoLabel = (Label)this.getFellow("axsTxnRefNoLabel");
			axsTxnRefNoLabel.setValue(paymentReceipt.getPaymentNo());
			
			((Row)this.getFellow("axsDetail_2")).setVisible(true);
			Label axsPaymentAmountLabel = (Label)this.getFellow("axsPaymentAmountLabel");
			axsPaymentAmountLabel.setValue(StringUtil.bigDecimalToString(paymentReceipt.getPaymentAmount(), StringUtil.GLOBAL_DECIMAL_FORMAT));
		}
		else if(paymentReceipt.getMstbMasterTableByPaymentMode().getMasterCode().equals(NonConfigurableConstants.PAYMENT_MODE_BANK_TRANSFER)){
			((Row)this.getFellow("bankTransferDetail_1")).setVisible(true);
			Label btReceiptDateLabel = (Label)this.getFellow("btReceiptDateLabel");
			btReceiptDateLabel.setValue(DateUtil.convertTimestampToStr(paymentReceipt.getReceiptDt(), DateUtil.GLOBAL_DATE_FORMAT));
			Label btTxnRefNoLabel = (Label)this.getFellow("btTxnRefNoLabel");
			btTxnRefNoLabel.setValue(paymentReceipt.getPaymentNo());
			
			((Row)this.getFellow("bankTransferDetail_2")).setVisible(true);
			Label btBankLabel = (Label)this.getFellow("btBankLabel");
			if(paymentReceipt.getMstbBankMaster()!=null)
				btBankLabel.setValue(paymentReceipt.getMstbBankMaster().getBankName());
			else
				btBankLabel.setValue("-");
			Label btBranchLabel = (Label)this.getFellow("btBranchLabel");
			if(paymentReceipt.getMstbBranchMaster()!=null)
				btBranchLabel.setValue(paymentReceipt.getMstbBranchMaster().getBranchName());
			else
				btBranchLabel.setValue("-");
			
			((Row)this.getFellow("bankTransferDetail_3")).setVisible(true);
			Label btPaymentAmountLabel = (Label)this.getFellow("btPaymentAmountLabel");
			btPaymentAmountLabel.setValue(StringUtil.bigDecimalToString(paymentReceipt.getPaymentAmount(), StringUtil.GLOBAL_DECIMAL_FORMAT));
		}
		else if(paymentReceipt.getMstbMasterTableByPaymentMode().getMasterCode().equals(NonConfigurableConstants.PAYMENT_MODE_CASH)){
			((Row)this.getFellow("cashDetail_1")).setVisible(true);
			Label caReceiptDate = (Label)this.getFellow("caReceiptDateLabel");
			caReceiptDate.setValue(DateUtil.convertTimestampToStr(paymentReceipt.getReceiptDt(), DateUtil.GLOBAL_DATE_FORMAT));
			Label caPaymentAmountLabel = (Label)this.getFellow("caPaymentAmountLabel");
			caPaymentAmountLabel.setValue(StringUtil.bigDecimalToString(paymentReceipt.getPaymentAmount(), StringUtil.GLOBAL_DECIMAL_FORMAT));
		}
		else if(paymentReceipt.getMstbMasterTableByPaymentMode().getMasterCode().equals(NonConfigurableConstants.PAYMENT_MODE_CONTRA)){
			((Row)this.getFellow("contraDetail_1")).setVisible(true);
			Label contraReceiptDateLabel = (Label)this.getFellow("contraReceiptDateLabel");
			contraReceiptDateLabel.setValue(DateUtil.convertTimestampToStr(paymentReceipt.getReceiptDt(), DateUtil.GLOBAL_DATE_FORMAT));
			Label contraPaymentAmountLabel = (Label)this.getFellow("contraPaymentAmountLabel");
			contraPaymentAmountLabel.setValue(StringUtil.bigDecimalToString(paymentReceipt.getPaymentAmount(), StringUtil.GLOBAL_DECIMAL_FORMAT));
		}
		else if(paymentReceipt.getMstbMasterTableByPaymentMode().getMasterCode().equals(NonConfigurableConstants.PAYMENT_MODE_CHEQUE)){
			((Row)this.getFellow("cqDetail_1")).setVisible(true);
			Label cqReceiptDateLabel = (Label)this.getFellow("cqReceiptDateLabel");
			cqReceiptDateLabel.setValue(DateUtil.convertTimestampToStr(paymentReceipt.getReceiptDt(), DateUtil.GLOBAL_DATE_FORMAT));
			Label chequeDateLabel = (Label)this.getFellow("chequeDateLabel");
			chequeDateLabel.setValue(DateUtil.convertDateToStr(paymentReceipt.getChequeDate(), DateUtil.GLOBAL_DATE_FORMAT));
			
			((Row)this.getFellow("cqDetail_2")).setVisible(true);
			Label chequeNoLabel = (Label)this.getFellow("chequeNoLabel");
			chequeNoLabel.setValue(paymentReceipt.getChequeNo());
			Label quickCheckDepositLabel = (Label)this.getFellow("quickCheckDepositLabel");
			quickCheckDepositLabel.setValue(NonConfigurableConstants.BOOLEAN_YN.get(paymentReceipt.getQuickChequeDeposit()));
			
			((Row)this.getFellow("cqDetail_3")).setVisible(true);
			Label cqBankLabel = (Label)this.getFellow("cqBankLabel");
			cqBankLabel.setValue(paymentReceipt.getMstbBankMaster().getBankName());
			Label cqBranchLabel = (Label)this.getFellow("cqBranchLabel");
			cqBranchLabel.setValue(paymentReceipt.getMstbBranchMaster().getBranchName());
			
			((Row)this.getFellow("cqDetail_4")).setVisible(true);
			Label cqPaymentAmountLabel = (Label)this.getFellow("cqPaymentAmountLabel");
			cqPaymentAmountLabel.setValue(StringUtil.bigDecimalToString(paymentReceipt.getPaymentAmount(), StringUtil.GLOBAL_DECIMAL_FORMAT));
		}
		else if(paymentReceipt.getMstbMasterTableByPaymentMode().getMasterCode().equals(NonConfigurableConstants.PAYMENT_MODE_CREDIT_CARD)){
			((Row)this.getFellow("creditCardDetail_1")).setVisible(true);
			Label ccReceiptDateLabel = (Label)this.getFellow("ccReceiptDateLabel");
			ccReceiptDateLabel.setValue(DateUtil.convertTimestampToStr(paymentReceipt.getReceiptDt(), DateUtil.GLOBAL_DATE_FORMAT));
			Label ccTxnRefNoLabel = (Label)this.getFellow("ccTxnRefNoLabel");
			ccTxnRefNoLabel.setValue(paymentReceipt.getPaymentNo());
			
			((Row)this.getFellow("creditCardDetail_2")).setVisible(true);
			Label ccPaymentAmountLabel = (Label)this.getFellow("ccPaymentAmountLabel");
			ccPaymentAmountLabel.setValue(StringUtil.bigDecimalToString(paymentReceipt.getPaymentAmount(), StringUtil.GLOBAL_DECIMAL_FORMAT));
		}
		else if(paymentReceipt.getMstbMasterTableByPaymentMode().getMasterCode().equals(NonConfigurableConstants.PAYMENT_MODE_GIRO)){
			((Row)this.getFellow("giroDetail_1")).setVisible(true);
			Label grReceiptDateLabel = (Label)this.getFellow("grReceiptDateLabel");
			grReceiptDateLabel.setValue(DateUtil.convertTimestampToStr(paymentReceipt.getReceiptDt(), DateUtil.GLOBAL_DATE_FORMAT));
			Label grTxnRefNoLabel = (Label)this.getFellow("grTxnRefNoLabel");
			grTxnRefNoLabel.setValue(paymentReceipt.getPaymentNo());
			
			((Row)this.getFellow("giroDetail_2")).setVisible(true);
			Label grBankLabel = (Label)this.getFellow("grBankLabel");
			if(paymentReceipt.getMstbBankMaster()!=null)
				grBankLabel.setValue(paymentReceipt.getMstbBankMaster().getBankName());
			else
				grBankLabel.setValue("-");
			Label grBranchLabel = (Label)this.getFellow("grBranchLabel");
			if(paymentReceipt.getMstbBranchMaster()!=null)
				grBranchLabel.setValue(paymentReceipt.getMstbBranchMaster().getBranchName());
			else
				grBranchLabel.setValue("-");
			
			((Row)this.getFellow("giroDetail_3")).setVisible(true);
			Label grPaymentAmountLabel = (Label)this.getFellow("grPaymentAmountLabel");
			grPaymentAmountLabel.setValue(StringUtil.bigDecimalToString(paymentReceipt.getPaymentAmount(), StringUtil.GLOBAL_DECIMAL_FORMAT));
		}
		
		Label remarksLabel = (Label)this.getFellow("remarksLabel");
		remarksLabel.setMultiline(true);
		remarksLabel.setValue(paymentReceipt.getRemarks());
		
		//Bank In Section
		Label bankInLabel = (Label)this.getFellow("bankInLabel");
		FmtbBankCode bankInBank = paymentReceipt.getFmtbBankCode();
		if(bankInBank!=null){
			bankInLabel.setValue(bankInBank.getBankName()+" - "+bankInBank.getBranchName()+" ("+bankInBank.getBankAcctNo()+")");
		}
		//Applied To Section
		Label totalAppliedAmountLabel = (Label)this.getFellow("totalAppliedAmountLabel");
		BigDecimal totalAppliedAmount = new BigDecimal(0);
		
		Listbox appliedInvoiceList = (Listbox)this.getFellow("appliedInvoiceList");
		HashMap<Long, Listitem> listItemMap = new HashMap<Long, Listitem>();
		
		Set<BmtbPaymentReceiptDetail> paymentDetails = paymentReceipt.getBmtbPaymentReceiptDetails();
		for(BmtbPaymentReceiptDetail paymentDetail : paymentDetails){
			
			if(paymentDetail.getBmtbInvoiceHeader()!=null) {
				if(listItemMap.get(paymentDetail.getBmtbInvoiceHeader().getInvoiceNo())!=null){
					Listitem item = listItemMap.get(paymentDetail.getBmtbInvoiceHeader().getInvoiceNo());
					//++item applied amount
					item.setValue(((BigDecimal)item.getValue()).add(paymentDetail.getAppliedAmount()));
					//update listcell
					Listcell cell = (Listcell)item.getChildren().get(4);
					cell.setLabel(StringUtil.bigDecimalToString((BigDecimal)item.getValue(), StringUtil.GLOBAL_DECIMAL_FORMAT));
					cell.setValue(item.getValue());
					
				}
				else{
					Listitem item = new Listitem();
					item.setValue(paymentDetail.getAppliedAmount());
					item.appendChild(newListcell(paymentDetail.getBmtbInvoiceHeader().getInvoiceNo(), StringUtil.GLOBAL_STRING_FORMAT));
					item.appendChild(newListcell(paymentDetail.getBmtbInvoiceHeader().getInvoiceDate(), DateUtil.GLOBAL_DATE_FORMAT));
					item.appendChild(newListcell(paymentDetail.getBmtbInvoiceHeader().getAmtbAccountByDebtTo().getAccountName()));
					item.appendChild(newListcell(paymentDetail.getBmtbInvoiceHeader().getNewTxn(), StringUtil.GLOBAL_DECIMAL_FORMAT));
					item.appendChild(newListcell(paymentDetail.getAppliedAmount(), StringUtil.GLOBAL_DECIMAL_FORMAT));
					item.appendChild(newListcell(NonConfigurableConstants.INVOICE_STATUS.get(paymentDetail.getBmtbInvoiceHeader().getInvoiceStatus())));
					
					//add into map
					listItemMap.put(paymentDetail.getBmtbInvoiceHeader().getInvoiceNo(), item);
				}
			}
			
			//++total applied amount
			totalAppliedAmount = totalAppliedAmount.add(paymentDetail.getAppliedAmount());
		}
		
		Set<Long> keys = listItemMap.keySet();
		for(Long key : keys){
			if(appliedInvoiceList.getListfoot()!=null)
				appliedInvoiceList.removeChild(appliedInvoiceList.getListfoot());
			
			appliedInvoiceList.appendChild(listItemMap.get(key));
		}
		
		totalAppliedAmountLabel.setValue(StringUtil.bigDecimalToString(totalAppliedAmount, StringUtil.GLOBAL_DECIMAL_FORMAT));
		
		//Receipt Status
		Label receiptStatusLabel = (Label)this.getFellow("receiptStatusLabel");
		BigDecimal excessAmount = paymentReceipt.getPaymentAmount().subtract(totalAppliedAmount);
		if(paymentReceipt.getCancelDt()!=null) receiptStatusLabel.setValue(NonConfigurableConstants.RECEIPT_STATUS.get(NonConfigurableConstants.RECEIPT_STATUS_CANCELLED));
		else if(excessAmount.compareTo(new BigDecimal(0))>0) receiptStatusLabel.setValue(NonConfigurableConstants.RECEIPT_STATUS.get(NonConfigurableConstants.RECEIPT_STATUS_EXCESS));
		else if(excessAmount.compareTo(new BigDecimal(0))==0) receiptStatusLabel.setValue(NonConfigurableConstants.RECEIPT_STATUS.get(NonConfigurableConstants.RECEIPT_STATUS_CLOSED));
		
		//Total Excess Amount Label
		if(excessAmount.compareTo(new BigDecimal(0))>0){
			Label totalExcessAmountLabel = (Label)this.getFellow("totalExcessAmountLabel");
			totalExcessAmountLabel.setValue(StringUtil.bigDecimalToString(excessAmount, StringUtil.GLOBAL_DECIMAL_FORMAT));
		}
		
		//Created By Section
		Label createdByLabel = (Label)this.getFellow("createdByLabel");
		Label createdDateLabel = (Label)this.getFellow("createdDateLabel");
		Label createdTimeLabel = (Label)this.getFellow("createdTimeLabel");
		
		createdByLabel.setValue(this.paymentReceipt.getCreatedBy());
		createdDateLabel.setValue(DateUtil.convertTimestampToStr(this.paymentReceipt.getCreatedDt(), DateUtil.GLOBAL_DATE_FORMAT));
		createdTimeLabel.setValue(DateUtil.convertTimestampToStr(this.paymentReceipt.getCreatedDt(), DateUtil.GLOBAL_TIME_FORMAT));
		
		//Last Updated Section
		Label lastUpdatedByLabel = (Label)this.getFellow("lastUpdatedByLabel");
		Label lastUpdatedDateLabel = (Label)this.getFellow("lastUpdatedDateLabel");
		Label lastUpdatedTimeLabel = (Label)this.getFellow("lastUpdatedTimeLabel");
		
		if(this.paymentReceipt.getUpdatedDt()==null){
			lastUpdatedByLabel.setValue("-");
			lastUpdatedDateLabel.setValue("-");
			lastUpdatedTimeLabel.setValue("-");
		}
		else{
			lastUpdatedByLabel.setValue(this.paymentReceipt.getUpdatedBy());
			lastUpdatedDateLabel.setValue(DateUtil.convertTimestampToStr(this.paymentReceipt.getUpdatedDt(), DateUtil.GLOBAL_DATE_FORMAT));
			lastUpdatedTimeLabel.setValue(DateUtil.convertTimestampToStr(this.paymentReceipt.getUpdatedDt(), DateUtil.GLOBAL_TIME_FORMAT));
		}
		
		//Outstanding Invoices Section
		if(account.getAccountCategory().equals(NonConfigurableConstants.ACCOUNT_CATEGORY_DEPARTMENT))
			this.searchOutstandingInvoice(account.getAmtbAccount().getAmtbAccount());
		else if(account.getAccountCategory().equals(NonConfigurableConstants.ACCOUNT_CATEGORY_DIVISION) || account.getAccountCategory().equals(NonConfigurableConstants.ACCOUNT_CATEGORY_SUB_APPLICANT))
			this.searchOutstandingInvoice(account.getAmtbAccount());
		else
			this.searchOutstandingInvoice(account);
	}
	
	private void searchOutstandingInvoice(AmtbAccount account) {
		Listbox outstandingInvoiceList = (Listbox)this.getFellow("outstandingInvoiceList");
		outstandingInvoiceList.getItems().clear();
		
		List<InvoicePaymentDetail> outstandingInvoices = new ArrayList<InvoicePaymentDetail>();
		outstandingInvoices = this.businessHelper.getPaymentBusiness().searchOutstandingInvoice(account);
		
		if(outstandingInvoices.size()>0){
			for(InvoicePaymentDetail paymentDetail : outstandingInvoices){
				Listitem item = new Listitem();
				item.setValue(paymentDetail);
				item.appendChild(newListcell(paymentDetail.getInvoiceHeader().getInvoiceNo(), StringUtil.GLOBAL_STRING_FORMAT));
				item.appendChild(newListcell(paymentDetail.getInvoiceHeader().getInvoiceDate(), DateUtil.GLOBAL_DATE_FORMAT));
				item.appendChild(newListcell(paymentDetail.getInvoiceHeader().getAmtbAccountByDebtTo().getAccountName()));
				item.appendChild(newListcell(paymentDetail.getInvoiceHeader().getNewTxn(), StringUtil.GLOBAL_DECIMAL_FORMAT));
				BigDecimal invoiceAppliedAmount = new BigDecimal(0);
				invoiceAppliedAmount = paymentDetail.getInvoiceHeader().getNewTxn().subtract(paymentDetail.getInvoiceHeader().getOutstandingAmount());
				item.appendChild(newListcell(invoiceAppliedAmount, StringUtil.GLOBAL_DECIMAL_FORMAT));
				item.appendChild(newListcell(paymentDetail.getInvoiceHeader().getOutstandingAmount(), StringUtil.GLOBAL_DECIMAL_FORMAT));
				item.appendChild(newListcell(new BigDecimal(0), StringUtil.GLOBAL_DECIMAL_FORMAT));
				
				//last one is the checkbox
				Listcell checkboxListCell = new Listcell();
				Checkbox checkBox = new Checkbox();
				checkBox.addEventListener(Events.ON_CHECK, new EventListener() {
					public boolean isAsap() {
					    return true;
					}
					
		            public void onEvent(Event event) throws Exception {
		            	Checkbox self = (Checkbox)event.getTarget();
		            	
		            	ApplyExcessAmountPaymentReceiptWindow window = (ApplyExcessAmountPaymentReceiptWindow)self.getPage().getFellow(ApplyExcessAmountPaymentReceiptWindow.SELF);
		            	window.onCheckInvoice(self);
		            }
		        });
				checkboxListCell.appendChild(checkBox);
				item.appendChild(checkboxListCell);
				
				outstandingInvoiceList.appendChild(item);
			}
			
			if(outstandingInvoiceList.getListfoot()!=null)
				outstandingInvoiceList.removeChild(outstandingInvoiceList.getListfoot());
		}
		else{
			if(outstandingInvoiceList.getListfoot()==null){
				outstandingInvoiceList.appendChild(ComponentUtil.createNoRecordsFoundListFoot(8));
			}
		}
	}
	
	public void onCheckInvoice(Checkbox checkbox) throws InterruptedException{
		logger.info("");
		
		try{
			Checkbox checkAll = (Checkbox)this.getFellow("checkAll");
			checkAll.setChecked(false);
			
			//retrieve selected amount label
			Label selectedAmountLabel = (Label)this.getFellow("selectedAmountLabel");
			
			//retrieve invoice header from listitem
			Listitem invoiceListItem = (Listitem)((Listcell)checkbox.getParent()).getParent();
			InvoicePaymentDetail paymentDetail = (InvoicePaymentDetail)invoiceListItem.getValue();
			
			//adjust applied amount
			if(checkbox.isChecked()){
				
				//check make sure remaining amount is able to full payment the selected invoice
				BigDecimal remainingAmount = this.paymentReceipt.getExcessAmount();
				remainingAmount = remainingAmount.subtract(selectedAmount);
				if(remainingAmount.compareTo(paymentDetail.getInvoiceHeader().getOutstandingAmount()) < 0){
					checkbox.setChecked(false);
					Messagebox.show("Insufficient fund to pay for the whole invoice.", "Apply Excess Amount Payment Receipt", Messagebox.OK, Messagebox.ERROR);
					return;
				}
				
				//subtract away any partial applied amount
				selectedAmount = selectedAmount.subtract(paymentDetail.getTotalAppliedAmount());
				
				//full payment, so set the payment detail total applied amount into new full payment
				paymentDetail.setFullPayment(true);
				paymentDetail.setTotalAppliedAmount(paymentDetail.getInvoiceHeader().getOutstandingAmount());
				
				//add into the selected amount
				selectedAmount = selectedAmount.add(paymentDetail.getTotalAppliedAmount());
				selectedAmountLabel.setValue(StringUtil.bigDecimalToString(selectedAmount, StringUtil.GLOBAL_DECIMAL_FORMAT));
				
				//apply amount
				Listcell applyAmountListCell = (Listcell)invoiceListItem.getChildren().get(6);
				applyAmountListCell.setLabel(StringUtil.bigDecimalToString(paymentDetail.getTotalAppliedAmount(), StringUtil.GLOBAL_DECIMAL_FORMAT));
				applyAmountListCell.setValue(paymentDetail.getTotalAppliedAmount());
			}
			else{
				paymentDetail.setFullPayment(false);
				paymentDetail.setTotalAppliedAmount(new BigDecimal(0));
				selectedAmount = selectedAmount.subtract(paymentDetail.getInvoiceHeader().getOutstandingAmount());
				selectedAmountLabel.setValue(StringUtil.bigDecimalToString(selectedAmount, StringUtil.GLOBAL_DECIMAL_FORMAT));
				
				//apply amount
				Listcell applyAmountListCell = (Listcell)invoiceListItem.getChildren().get(6);
				applyAmountListCell.setLabel("0.00");
				applyAmountListCell.setValue(BigDecimal.ZERO);
			}
			
			//remove payment detail in the map
			paymentDetail.setInvoiceDetailAppliedAmount(new HashMap());
		}
		catch(Exception e){
			Messagebox.show(com.cdgtaxi.ibs.common.NonConfigurableConstants.COMMON_ERROR_MESSAGE, 
					"Error", Messagebox.OK, Messagebox.ERROR);
			e.printStackTrace();
		}
	}
	
	public void checkAll() throws InterruptedException{
		logger.info("");
		
		try{
			Checkbox checkAll = (Checkbox)this.getFellow("checkAll");
			Listbox outstandingInvoiceList = (Listbox)this.getFellow("outstandingInvoiceList");
			Label selectedAmountLabel = (Label)this.getFellow("selectedAmountLabel");
			
			List<Listitem> listItems = outstandingInvoiceList.getItems();
			for(Listitem listItem : listItems){
				InvoicePaymentDetail paymentDetail = (InvoicePaymentDetail)listItem.getValue();
				
				//remove payment detail in the map
				paymentDetail.setInvoiceDetailAppliedAmount(new HashMap());
				
				//adjust applied amount
				if(checkAll.isChecked()){
					Checkbox checkbox = (Checkbox)listItem.getLastChild().getLastChild();
					if(!checkbox.isChecked()){
						checkbox.setChecked(true);
						//subtract away any partial applied amount
						selectedAmount = selectedAmount.subtract(paymentDetail.getTotalAppliedAmount());
						//full payment, so set the payment detail total applied amount into new full payment
						paymentDetail.setFullPayment(true);
						paymentDetail.setTotalAppliedAmount(paymentDetail.getInvoiceHeader().getOutstandingAmount());
						//add into the selected amount
						selectedAmount = selectedAmount.add(paymentDetail.getTotalAppliedAmount());
						selectedAmountLabel.setValue(StringUtil.bigDecimalToString(selectedAmount, StringUtil.GLOBAL_DECIMAL_FORMAT));
						//apply amount
						Listcell applyAmountListCell = (Listcell)listItem.getChildren().get(6);
						applyAmountListCell.setLabel(StringUtil.bigDecimalToString(paymentDetail.getTotalAppliedAmount(), StringUtil.GLOBAL_DECIMAL_FORMAT));
						applyAmountListCell.setValue(paymentDetail.getTotalAppliedAmount());
					}
				}
				else{
					Checkbox checkbox = (Checkbox)listItem.getLastChild().getLastChild();
					if(checkbox.isChecked()){
						checkbox.setChecked(false);
						paymentDetail.setFullPayment(false);
						paymentDetail.setTotalAppliedAmount(new BigDecimal(0));
						selectedAmount = selectedAmount.subtract(paymentDetail.getInvoiceHeader().getOutstandingAmount());
						selectedAmountLabel.setValue(StringUtil.bigDecimalToString(selectedAmount, StringUtil.GLOBAL_DECIMAL_FORMAT));
						//apply amount
						Listcell applyAmountListCell = (Listcell)listItem.getChildren().get(6);
						applyAmountListCell.setLabel("0.00");
						applyAmountListCell.setValue(BigDecimal.ZERO);
					}
				}
			}
		}
		catch(Exception e){
			Messagebox.show(com.cdgtaxi.ibs.common.NonConfigurableConstants.COMMON_ERROR_MESSAGE, 
					"Error", Messagebox.OK, Messagebox.ERROR);
			e.printStackTrace();
		}
	}
	
	public void applyExcessAmountPayment() throws InterruptedException{
		logger.info("");
		
		try{
			//Check for selected amount, must not be zero
			if(selectedAmount.compareTo(new BigDecimal(0)) == 0){
				Messagebox.show("Total applied amount cannot be equals to $0.", "Error", Messagebox.OK, Messagebox.ERROR);
				return;
			}
			else if(selectedAmount.compareTo(BigDecimal.ZERO) < 0){
				Messagebox.show("Total selected amount cannot be lesser than $0.", "Error", Messagebox.OK, Messagebox.ERROR);
				return;
			}
			//Check selected amount greater than payment amount
			else if(selectedAmount.compareTo(this.paymentReceipt.getExcessAmount()) > 0){
				Messagebox.show("Total selected amount cannot be greater than remaining excess payment amount.", "Error", Messagebox.OK, Messagebox.ERROR);
				return;
			}
			//If there is excess amount, alert user before proceed to create payment receipt
			else if(selectedAmount.compareTo(this.paymentReceipt.getExcessAmount()) < 0){
				if(Messagebox.show("There is excess amount. Continue to apply payment receipt?", "Apply Payment Receipt", Messagebox.OK | Messagebox.CANCEL, Messagebox.QUESTION)==Messagebox.CANCEL){
					return;
				}
			}
			
			List<InvoicePaymentDetail> invoicePaymentDetails = new ArrayList<InvoicePaymentDetail>();
			Listbox outstandingInvoiceList = (Listbox)this.getFellow("outstandingInvoiceList");
			List<Listitem> invoiceListItems = outstandingInvoiceList.getItems();
			for(Listitem invoiceListItem : invoiceListItems){
				InvoicePaymentDetail paymentDetail = (InvoicePaymentDetail)invoiceListItem.getValue();
				if(paymentDetail.isFullPayment()) invoicePaymentDetails.add(paymentDetail);
				else if(paymentDetail.getInvoiceDetailAppliedAmount().size()>0) invoicePaymentDetails.add(paymentDetail);
			}
			
			this.businessHelper.getPaymentBusiness().applyPaymentReceipt(this.paymentReceipt, invoicePaymentDetails, getUserLoginIdAndDomain());
			
			//reward early payment is applicable
			AmtbAccount account = paymentReceipt.getAmtbAccount();
			if(account.getAccountCategory().equals(NonConfigurableConstants.ACCOUNT_CATEGORY_DEPARTMENT))
				account = account.getAmtbAccount().getAmtbAccount();
			else if(account.getAccountCategory().equals(NonConfigurableConstants.ACCOUNT_CATEGORY_DIVISION))
				account = account.getAmtbAccount();
			//must use top level account
			this.businessHelper.getPaymentBusiness().rewardEarlyPayment(account, getUserLoginIdAndDomain(), invoicePaymentDetails);
			
			//Show result
			Messagebox.show("Apply payment receipt successful.", "Apply Payment Receipt", Messagebox.OK, Messagebox.INFORMATION);
			
			this.back().back(); //back to search window
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
	
	public void applyExcessAmountPartialPayment(Listbox invoiceListBox) throws InterruptedException{
		logger.info("");
		
		try{
			//Retrieve selected invoice
			Listitem selectedItem = invoiceListBox.getSelectedItem();
			InvoicePaymentDetail invoicePaymentDetail = (InvoicePaymentDetail)selectedItem.getValue();
			
			this.previousAppliedAmount = invoicePaymentDetail.getTotalAppliedAmount();
			
			//uncheck checkbox
			Checkbox fullPaymentCheckbox = (Checkbox)((Listcell)selectedItem.getLastChild()).getLastChild();
			if(fullPaymentCheckbox.isChecked()) fullPaymentCheckbox.setChecked(false);
			
			//uncheck checkall
			Checkbox checkAll = (Checkbox)this.getFellow("checkAll");
			checkAll.setChecked(false);
			
			HashMap map = new HashMap();
			map.put("invoicePaymentDetail", invoicePaymentDetail);
			map.put("paymentReceipt", paymentReceipt);
			this.forward(Uri.APPLY_PARTIAL_PAYMENT, map);
		}
		catch(WrongValueException wve){
			//if don do unselect, the user cant select again when there is a validation error.
			invoiceListBox.clearSelection();
			throw wve;
		}
		catch(Exception e){
			Messagebox.show(com.cdgtaxi.ibs.common.NonConfigurableConstants.COMMON_ERROR_MESSAGE, 
					"Error", Messagebox.OK, Messagebox.ERROR);
			e.printStackTrace();
		}
	}
	
	public void updateAppliedAmount(InvoicePaymentDetail invoicePaymentDetail) throws InterruptedException{
		logger.info("");
		
		try{
			Label selectedAmountLabel = (Label)this.getFellow("selectedAmountLabel");
			Listbox outstandingInvoiceList = (Listbox)this.getFellow("outstandingInvoiceList");
			Listitem selectedListItem = outstandingInvoiceList.getSelectedItem();
			
			//Update selected amount by subtracting previous selected amount then add back new amount
			selectedAmount = selectedAmount.subtract(this.previousAppliedAmount);
			//we need to take away the min applied amount as the total selected amount is only the outstanding amount exclusive of discount
			selectedAmount = selectedAmount.add(invoicePaymentDetail.getTotalAppliedAmount());
			selectedAmountLabel.setValue(StringUtil.bigDecimalToString(selectedAmount, StringUtil.GLOBAL_DECIMAL_FORMAT));
			
			//surely no longer is fullpayment
			invoicePaymentDetail.setFullPayment(false);
			
			//Update the payment detail within the listitem
			selectedListItem.setValue(invoicePaymentDetail);
			
			//apply amount
			Listcell applyAmountListCell = (Listcell)selectedListItem.getChildren().get(6);
			applyAmountListCell.setLabel(StringUtil.bigDecimalToString(invoicePaymentDetail.getTotalAppliedAmount(), StringUtil.GLOBAL_DECIMAL_FORMAT));
			applyAmountListCell.setValue(invoicePaymentDetail.getTotalAppliedAmount());
			
			//Clear selection
			outstandingInvoiceList.clearSelection();
		}
		catch(Exception e){
			Messagebox.show(com.cdgtaxi.ibs.common.NonConfigurableConstants.COMMON_ERROR_MESSAGE, 
					"Error", Messagebox.OK, Messagebox.ERROR);
			e.printStackTrace();
		}
	}
	
	public void clearSelection(boolean isFullPaymentChecked){
		Listbox outstandingInvoiceList = (Listbox)this.getFellow("outstandingInvoiceList");
		//amazing after from forward back to this window, the checkbox will uncheck itself.
		Listcell listCell = (Listcell)outstandingInvoiceList.getSelectedItem().getChildren().get(7);
		((Checkbox)listCell.getFirstChild()).setChecked(isFullPaymentChecked);
		//clear invoice list selection so that user can click again
		outstandingInvoiceList.clearSelection();
	}
	
	@Override
	public void refresh() {
		
	}
}
