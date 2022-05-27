package com.cdgtaxi.ibs.billing.ui;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.zkoss.util.media.AMedia;
import org.zkoss.zhtml.Messagebox;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.ext.AfterCompose;
import org.zkoss.zul.Button;
import org.zkoss.zul.Filedownload;
import org.zkoss.zul.Label;
import org.zkoss.zul.Row;

import com.cdgtaxi.ibs.Uri;
import com.cdgtaxi.ibs.common.NonConfigurableConstants;
import com.cdgtaxi.ibs.common.model.AmtbAccount;
import com.cdgtaxi.ibs.common.model.BmtbPaymentReceipt;
import com.cdgtaxi.ibs.common.ui.CommonWindow;
import com.cdgtaxi.ibs.report.Constants;
import com.cdgtaxi.ibs.util.DateUtil;
import com.cdgtaxi.ibs.util.StringUtil;
import com.elixirtech.net.NetException;

public class ViewMemoReceiptWindow extends CommonWindow implements AfterCompose{

	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(ViewMemoReceiptWindow.class);
	private BmtbPaymentReceipt paymentReceipt;
	
	public ViewMemoReceiptWindow(){
		Map params = Executions.getCurrent().getArg();
		Long receiptNo = (Long)params.get("receiptNo");
		paymentReceipt = this.businessHelper.getPaymentBusiness().searchPaymentReceipt(receiptNo);
		if(paymentReceipt == null)
			throw new NullPointerException("Payment Receipt["+receiptNo+"] not found!"); //This should not happen at all
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
		
		//Receipt Status
		Label receiptStatusLabel = (Label)this.getFellow("receiptStatusLabel");
		if(paymentReceipt.getChequeNo()==null || paymentReceipt.getChequeNo().length()==0){
			receiptStatusLabel.setValue(NonConfigurableConstants.RECEIPT_STATUS.get(NonConfigurableConstants.RECEIPT_STATUS_PENDING));
			this.getFellow("updateChequeNoButton").setVisible(true);
		}
		else
			receiptStatusLabel.setValue(NonConfigurableConstants.RECEIPT_STATUS.get(NonConfigurableConstants.RECEIPT_STATUS_CLOSED));
		
		//Created By Section
		Label createdByLabel = (Label)this.getFellow("createdByLabel");
		Label createdDateLabel = (Label)this.getFellow("createdDateLabel");
		Label createdTimeLabel = (Label)this.getFellow("createdTimeLabel");
		
		createdByLabel.setValue(this.paymentReceipt.getCreatedBy());
		createdDateLabel.setValue(DateUtil.convertTimestampToStr(this.paymentReceipt.getCreatedDt(), DateUtil.GLOBAL_DATE_FORMAT));
		createdTimeLabel.setValue(DateUtil.convertTimestampToStr(this.paymentReceipt.getCreatedDt(), DateUtil.GLOBAL_TIME_FORMAT));
		
		
		//refund amount
		((Label)this.getFellow("refundAmountLabel")).setValue(StringUtil.bigDecimalToString(paymentReceipt.getPaymentAmount(), StringUtil.GLOBAL_DECIMAL_FORMAT));
		//Receipt Date
		Label receiptDateLabel = (Label)this.getFellow("receiptDateLabel");
		receiptDateLabel.setValue(DateUtil.convertTimestampToStr(paymentReceipt.getReceiptDt(), DateUtil.GLOBAL_DATE_FORMAT));
		
		((Label)this.getFellow("chequeNoLabel")).setValue(paymentReceipt.getChequeNo());
		
		if(!this.checkUriAccess(Uri.UPDATE_CHEQUE_NO))
			((Button)this.getFellow("updateChequeNoButton")).setDisabled(true);
	}
	
	public void reprint() throws InterruptedException{
		logger.info("");
		try{
			this.displayProcessing();
			if(paymentReceipt.getBmtbInvoiceDepositTxns().size()>0){
				AMedia media = generateDepositMemo(paymentReceipt.getPaymentReceiptNo());
				Filedownload.save(media);
			}
			else{
				AMedia media = generateMemo(paymentReceipt.getPaymentReceiptNo());
				Filedownload.save(media);
			}
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
	
	private AMedia generateDepositMemo(Long receiptNo) throws NetException, IOException{
		Properties params = new Properties();
		params.put("paymentReceiptNo", receiptNo.toString());

		String outputFormat = Constants.FORMAT_PDF;
		String reportCategory = NonConfigurableConstants.REPORT_CATEGORY_MEMO;
		String reportName = NonConfigurableConstants.REPORT_NAME_MEMO_DEPOSIT_REFUND;

		byte[] bytes =
			businessHelper.getReportBusiness().generate(reportName, reportCategory, outputFormat, params);

		return new AMedia(reportName+"_" + receiptNo + ".pdf", "pdf", outputFormat, bytes);
	}
	
	public void updateChequeNo() throws InterruptedException{
		logger.info("");
		try{
			Map params = new HashMap();
			params.put("paymentReceipt", this.paymentReceipt);
			
			final UpdateChequeNoWindow window = (UpdateChequeNoWindow) Executions.createComponents(Uri.UPDATE_CHEQUE_NO, null, params);
			window.setMaximizable(false);
			window.doModal();
			
			if(window.getChequeNo()!=null){
				Messagebox.show("Cheque no. updated successfully.", "Update Cheque No.", Messagebox.OK, Messagebox.INFORMATION);
				this.back();
			}
		}
		catch(Exception e){
			Messagebox.show(com.cdgtaxi.ibs.common.NonConfigurableConstants.COMMON_ERROR_MESSAGE, 
					"Error", Messagebox.OK, Messagebox.ERROR);
			e.printStackTrace();
		}
	}
	
	@Override
	public void refresh() {
		
	}
}
