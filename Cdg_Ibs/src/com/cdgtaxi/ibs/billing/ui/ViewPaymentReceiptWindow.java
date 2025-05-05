package com.cdgtaxi.ibs.billing.ui;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import org.apache.log4j.Logger;
import org.zkoss.util.media.AMedia;
import org.zkoss.zhtml.Messagebox;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zk.ui.ext.AfterCompose;
import org.zkoss.zul.Button;
import org.zkoss.zul.Filedownload;
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
import com.cdgtaxi.ibs.common.ui.CommonWindow;
import com.cdgtaxi.ibs.report.Constants;
import com.cdgtaxi.ibs.util.DateUtil;
import com.cdgtaxi.ibs.util.StringUtil;
import com.elixirtech.net.NetException;

public class ViewPaymentReceiptWindow extends CommonWindow implements AfterCompose{

	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(ViewPaymentReceiptWindow.class);
	private static final String SELF = "viewPaymentReceiptWindow";
	private Map<String, ArrayList<Component>> paymentDetailMap = new HashMap<String, ArrayList<Component>>();
	private BmtbPaymentReceipt paymentReceipt;
	
	public ViewPaymentReceiptWindow(){
		Map params = Executions.getCurrent().getArg();
		Long receiptNo = (Long)params.get("receiptNo");
		paymentReceipt = this.businessHelper.getPaymentBusiness().searchPaymentReceipt(receiptNo);
		if(paymentReceipt == null)
			throw new NullPointerException("Payment Receipt["+receiptNo+"] not found!"); //This should not happen at all
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
			((Label)this.getFellow("departmentLabel")).setVisible(true);
			Label departmentNameLabel = (Label)this.getFellow("departmentNameLabel");
			departmentNameLabel.setValue(account.getAccountName());
			departmentNameLabel.setVisible(true);
			((Label)this.getFellow("divisionLabel")).setVisible(true);
			Label divisionNameLabel = (Label)this.getFellow("divisionNameLabel");
			divisionNameLabel.setValue(account.getAmtbAccount().getAccountName());
			divisionNameLabel.setVisible(true);
			accountNoLabel.setValue(account.getAmtbAccount().getAmtbAccount().getCustNo());
			accountNameLabel.setValue(account.getAmtbAccount().getAmtbAccount().getAccountName());
		}
		else if(account.getAccountCategory().equals(NonConfigurableConstants.ACCOUNT_CATEGORY_DIVISION)){
			Row divisionDepartmentRow = (Row)this.getFellow("divisionDepartmentRow");
			divisionDepartmentRow.setVisible(true);
			((Label)this.getFellow("divisionLabel")).setVisible(true);
			Label divisionNameLabel = (Label)this.getFellow("divisionNameLabel");
			divisionNameLabel.setValue(account.getAccountName());
			divisionNameLabel.setVisible(true);
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
			Label axsReceiptDateLabel = (Label)this.getFellow("othersReceiptDateLabel");
			axsReceiptDateLabel.setValue(DateUtil.convertTimestampToStr(paymentReceipt.getReceiptDt(), DateUtil.GLOBAL_DATE_FORMAT));
			Label axsTxnRefNoLabel = (Label)this.getFellow("othersTxnRefNoLabel");
			axsTxnRefNoLabel.setValue(paymentReceipt.getPaymentNo());
			
			((Row)this.getFellow("othersDetail_2")).setVisible(true);
			Label axsPaymentAmountLabel = (Label)this.getFellow("othersPaymentAmountLabel");
			axsPaymentAmountLabel.setValue(StringUtil.bigDecimalToString(paymentReceipt.getPaymentAmount(), StringUtil.GLOBAL_DECIMAL_FORMAT));
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
		
		if(paymentReceipt.getCancelDt()==null){
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
		}
		
		Set<Long> keys = listItemMap.keySet();
		for(Long key : keys){
			if(appliedInvoiceList.getListfoot()!=null)
				appliedInvoiceList.removeChild(appliedInvoiceList.getListfoot());
			
			appliedInvoiceList.appendChild(listItemMap.get(key));
		}
		
		if(paymentReceipt.getCancelDt()==null)
			totalAppliedAmountLabel.setValue(StringUtil.bigDecimalToString(totalAppliedAmount, StringUtil.GLOBAL_DECIMAL_FORMAT));
		else totalAppliedAmountLabel.setValue("0.00");
		
		//Receipt Status
		Label receiptStatusLabel = (Label)this.getFellow("receiptStatusLabel");
		BigDecimal excessAmount = paymentReceipt.getPaymentAmount().subtract(totalAppliedAmount);
		if(paymentReceipt.getBmtbPaymentReceipt()!=null)
			excessAmount = excessAmount.subtract(paymentReceipt.getBmtbPaymentReceipt().getPaymentAmount());
		if(paymentReceipt.getCancelDt()!=null) receiptStatusLabel.setValue(NonConfigurableConstants.RECEIPT_STATUS.get(NonConfigurableConstants.RECEIPT_STATUS_CANCELLED));
		else if(excessAmount.compareTo(new BigDecimal(0))>0) receiptStatusLabel.setValue(NonConfigurableConstants.RECEIPT_STATUS.get(NonConfigurableConstants.RECEIPT_STATUS_EXCESS));
		else if(excessAmount.compareTo(new BigDecimal(0))==0) receiptStatusLabel.setValue(NonConfigurableConstants.RECEIPT_STATUS.get(NonConfigurableConstants.RECEIPT_STATUS_CLOSED));
		
		//Total Excess Amount Label
		if(excessAmount.compareTo(new BigDecimal(0))>0){
			Label totalExcessAmountLabel = (Label)this.getFellow("totalExcessAmountLabel");
			if(paymentReceipt.getCancelDt()==null)
				totalExcessAmountLabel.setValue(StringUtil.bigDecimalToString(excessAmount, StringUtil.GLOBAL_DECIMAL_FORMAT));
			else totalExcessAmountLabel.setValue("0.00");
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
		
		//Cancel Remarks Section
		//receipt not cancelled yet, show cancel reasons, remarks and cancel button
		if(paymentReceipt.getCancelDt()==null){
			((Component)this.getFellow("cancelRemarksDiv")).setVisible(false);
			if(this.checkUriAccess(Uri.CANCEL_PAYMENT_RECEIPT)){
				Button cancelReceiptButton = (Button)this.getFellow("cancelReceiptButton");
				cancelReceiptButton.setVisible(true);
			}
		}
		else{
			((Component)this.getFellow("cancelRemarksDiv")).setVisible(true);
			
			Label cancellationReasonLabel = (Label)this.getFellow("cancellationReasonLabel");
			cancellationReasonLabel.setVisible(true);
			Label cancelRemarksLabel = (Label)this.getFellow("cancelRemarksLabel");
			cancelRemarksLabel.setVisible(true);
			
			cancellationReasonLabel.setValue(paymentReceipt.getMstbMasterTableByCancellationReason().getMasterValue());
			cancelRemarksLabel.setValue(paymentReceipt.getCancelRemarks());
		}
		
		//ApplyExcessAmountButton
		if(paymentReceipt.getExcessAmount().compareTo(new BigDecimal(0)) > 0){
			if(this.checkUriAccess(Uri.APPLY_EXCESS_AMOUNT_PAYMENT_RECEIPT)){
				((Component)this.getFellow("applyExcessAmountButton")).setVisible(true);
			}
			if(!paymentReceipt.getMstbMasterTableByPaymentMode().getMasterCode().equals(NonConfigurableConstants.PAYMENT_MODE_CONTRA)){
				if(this.checkUriAccess(Uri.REFUND_EXCESS_AMOUNT_PAYMENT_RECEIPT)){
					((Component)this.getFellow("refundExcessAmountButton")).setVisible(true);
				}
			}
		}
		
		//refund amount
		if(paymentReceipt.getBmtbPaymentReceipt()!=null){
			this.getFellow("memoDetail").setVisible(true);
			((Label)this.getFellow("memoReceiptNoLabel")).setValue(paymentReceipt.getBmtbPaymentReceipt().getPaymentReceiptNo()+"");
			((Label)this.getFellow("refundAmountLabel")).setValue(StringUtil.bigDecimalToString(paymentReceipt.getBmtbPaymentReceipt().getPaymentAmount(),
					StringUtil.GLOBAL_DECIMAL_FORMAT));
		}
	}
	
	public void cancelPaymentReceipt() throws InterruptedException{
		logger.info("");
		
		if(NonConfigurableConstants.getBoolean(this.paymentReceipt.getPrepaidFlag())){
			throw new WrongValueException("Payment receipts created from prepaid's transactions are not allow to cancel.");
		}
		
		try{
			Map params = new HashMap();
			params.put("paymentReceipt", this.paymentReceipt);
			this.forward(Uri.CANCEL_PAYMENT_RECEIPT, params);
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
	
	public void applyExcessAmount() throws InterruptedException{
		logger.info("");
		
		try{
			Map params = new HashMap();
			params.put("paymentReceipt", this.paymentReceipt);
			this.forward(Uri.APPLY_EXCESS_AMOUNT_PAYMENT_RECEIPT, params);
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
	
	public void refundExcessAmount() throws InterruptedException{
		logger.info("");
		
		try{
			if(Messagebox.show("You about to refund a total of $"+StringUtil.bigDecimalToString(this.paymentReceipt.getExcessAmount(), StringUtil.GLOBAL_DECIMAL_FORMAT)+" overpayment amount, are you sure?", "Refund Excess Amount", Messagebox.OK | Messagebox.CANCEL, Messagebox.QUESTION)==Messagebox.OK){
				Long receiptNo = this.businessHelper.getPaymentBusiness().generateMemoRefundForExcessAmount(this.paymentReceipt, getUserLoginIdAndDomain());
				int response = Messagebox.show(
						"Excess Amount Refund has been successfully processed." +
						"Receipt No is "+receiptNo+"." +
						"Do you wish to download the memo receipt?", "Refund Excess Amount",
						Messagebox.YES | Messagebox.NO, Messagebox.INFORMATION);
				if (response == Messagebox.YES) {
					this.displayProcessing();
					AMedia media = generateMemo(receiptNo);
					Filedownload.save(media);
				}
			}
			
			this.back();
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
		String reportName = NonConfigurableConstants.REPORT_NAME_MEMO_OVERPAYMENT_REFUND;

		byte[] bytes =
			businessHelper.getReportBusiness().generate(reportName, reportCategory, outputFormat, params);

		return new AMedia(reportName+"_" + receiptNo + ".pdf", "pdf", outputFormat, bytes);
	}
	public void printReceipt() throws NetException, IOException{
		Properties params = new Properties();
		params.put("1. Receipt No", paymentReceipt.getPaymentReceiptNo().toString());

		String outputFormat = Constants.FORMAT_PDF;
		String reportCategory = "Receipt";
		String reportName = "Payment Receipt";

		byte[] bytes =
			businessHelper.getReportBusiness().generate(reportName, reportCategory, outputFormat, params);
		AMedia media = new AMedia(reportName+"_" + paymentReceipt.getPaymentReceiptNo() + ".pdf", "pdf", outputFormat, bytes);
		Filedownload.save(media);
	}
	
	@Override
	public void refresh() {
		
	}
}
