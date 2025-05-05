package com.cdgtaxi.rest;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;
import org.zkoss.zkplus.spring.SpringUtil;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Longbox;
import org.zkoss.zul.Textbox;

import com.cdgtaxi.ibs.acl.model.SatbUser;
import com.cdgtaxi.ibs.common.ConfigurableConstants;
import com.cdgtaxi.ibs.common.NonConfigurableConstants;
import com.cdgtaxi.ibs.common.business.BusinessHelper;
import com.cdgtaxi.ibs.common.model.PmtbTopUpReq;
import com.cdgtaxi.ibs.common.model.PmtbTopUpReqCard;
import com.cdgtaxi.ibs.common.model.forms.InvoicePaymentDetail;
import com.cdgtaxi.ibs.common.model.forms.PaymentInfo;
import com.cdgtaxi.ibs.common.ui.CommonWindow;
import com.cdgtaxi.ibs.util.DateUtil;
import com.cdgtaxi.ibs.util.EmailUtil;
import com.cdgtaxi.ibs.util.LoggerUtil;
	
	public class RestController extends CommonWindow {

	private static final long serialVersionUID = 5482551235361783789L;
	private static Logger logger = Logger.getLogger(RestController.class);
	private String productNo="";
	private Listbox resultList;
	protected Longbox invoiceNo;
	protected Textbox cardNo;
	protected Listbox resultLB;
	protected Datebox requestDateFromField;
	protected Datebox requestDateToField;
	protected BusinessHelper businessHelper;
	protected Map properties;
	
	public RestController() {
		businessHelper = (BusinessHelper)SpringUtil.getBean("businessHelper");
		properties = (Map) SpringUtil.getBean("webserviceProperties");
	}
	public void redirect() {
	
		PmtbTopUpReq req = null;
		
		try {
			String reqNo = decrypt(getParams());
			req = this.businessHelper.getPrepaidBusiness().getPrepaidCreditTopUpRequest(new BigDecimal(reqNo));

			
			PaymentInfo paymentInfo = this.buildPaymentInfo(req);
			// Check for selected amount, must not be zero
			
			List<InvoicePaymentDetail> invoicePaymentDetails = new ArrayList<InvoicePaymentDetail>();
			
			InvoicePaymentDetail paymentDetail = new InvoicePaymentDetail(req.getBmtbInvoiceHeader());
			paymentDetail.setFullPayment(true);
			invoicePaymentDetails.add(paymentDetail);
			
			if (paymentDetail.getInvoiceDetailAppliedAmount().size() > 0) {
				invoicePaymentDetails.add(paymentDetail);
			}
			

			Long receiptNo = this.businessHelper.getPaymentBusiness().createPaymentReceipt(
					paymentInfo, invoicePaymentDetails, "RED DOT");

			// reward early payment is applicable
			this.businessHelper.getPaymentBusiness().rewardEarlyPayment(paymentInfo.getAccount(),
					"RED DOT", invoicePaymentDetails);

//			// Show result
//			Messagebox.show("New payment receipt created (Receipt No: " + receiptNo + ").",
//					"Create Payment Receipt", Messagebox.OK, Messagebox.INFORMATION);
			
			sendRedDotApprovalEmail(req, ConfigurableConstants.EMAIL_TYPE_PREPAID_CREDIT_REQUEST_APPROVED);
		} catch (Exception e){
			LoggerUtil.printStackTrace(logger, e);
			logger.debug("Failed to generate report for request " + req.getReqNo());
		}
		
	}
	
	
	private PaymentInfo buildPaymentInfo(PmtbTopUpReq topup) {
		PaymentInfo paymentInfo = new PaymentInfo();

		
		paymentInfo.setAccount(topup.getAmtbAccount());

		// division
		
//		if (divisionListBox.getSelectedItem() != null) {
//			if (!(divisionListBox.getSelectedItem().getValue() instanceof String)) {
//				paymentInfo.setDivision((AmtbAccount) divisionListBox.getSelectedItem().getValue());
//			}
//		}
		// department
//		Listbox departmentListBox = (Listbox) this.getFellow("departmentListBox");
//		if (departmentListBox.getSelectedItem() != null) {
//			if (!(departmentListBox.getSelectedItem().getValue() instanceof String)) {
//				paymentInfo.setDepartment((AmtbAccount) departmentListBox.getSelectedItem().getValue());
//			}
//		}
		// payment mode
		paymentInfo.setPaymentMode(NonConfigurableConstants.PAYMENT_MODE_CREDIT_CARD);
		// payment date
		paymentInfo.setPaymentDate(new java.sql.Date(Calendar.getInstance().getTime().getTime()));
		// receipt date
		paymentInfo.setReceiptDt(DateUtil.getCurrentTimestamp());

		if (paymentInfo.getPaymentMode().equals(NonConfigurableConstants.PAYMENT_MODE_CREDIT_CARD)) {
			// txn ref no
//			CapsTextbox txnRefNoTextBox = (CapsTextbox) this.getFellow("ccTxnRefNo");
//			paymentInfo.setTxnRefNo(txnRefNoTextBox.getValue());
			// payment amount
		//	Decimalbox paymentAmountDecimalBox = (Decimalbox) this.getFellow("ccPaymentAmount");
			paymentInfo.setPaymentAmount(topup.getTotalAmount());
		} 

		// remarks
		paymentInfo.setRemarks("CREDIT CARD API");
		// bank in
//		Listbox bankListBox = (Listbox) this.getFellow("bankInBankListBox");
//		if (bankListBox.getSelectedItem() == null) {
//			throw new WrongValueException(bankListBox, "* Mandatory field");
//		}
//		FmtbBankCode selectedBankInBank = (FmtbBankCode) bankListBox.getSelectedItem().getValue();
//		paymentInfo.setBankInNo(selectedBankInBank.getBankCodeNo());


		return paymentInfo;
	}
	
	@Override
	public void refresh() throws InterruptedException {
		// TODO Auto-generated method stub
		
	}
	
	private void sendRedDotApprovalEmail(PmtbTopUpReq req, String emailType){
		
		// retrieve properties val
		String subject = (String) properties.get("reddot.subject");
		String senderName = (String) properties.get("reddot.senderName");
		String senderEmail = (String) properties.get("reddot.senderEmail");
		if(NonConfigurableConstants.getBoolean(req.getApprovalRequired())){
			
		//	AmtbAccount acct = req.getAmtbAccount();
		//	acct = this.daoHelper.getAccountDao().getAccountWithParent(String.valueOf(acct.getAccountNo()));
//			AmtbAccount topAcct = AccountUtil.getTopLevelAccount(acct);
			
			List<String> toEmails = new ArrayList<String>();
			List<String> ccEmails = new ArrayList<String>();
		//	List<SatbUser> approvers = this.daoHelper.getUserDao().searchUser(Uri.APPROVAL_PREPAID_REQ);
//			for(SatbUser approver : approvers){
//				ccEmails.add(approver.getEmail());
//				
//			}
			SatbUser requestor = req.getRequestBy();
			toEmails.add(requestor.getEmail());
			Set<PmtbTopUpReqCard> reqCards = req.getPmtbTopUpReqCards();
			StringBuilder sbCardNo = new StringBuilder();
			StringBuilder sbCardName = new StringBuilder();
			String cards = "";
			String cardsName ="";
			for (PmtbTopUpReqCard s : reqCards) { 
				sbCardNo.append(s.getPmtbProduct().getCardNo()).append(","); 
				sbCardName.append(s.getPmtbProduct().getNameOnProduct()).append(","); 
			} 
			cards = sbCardNo.deleteCharAt(sbCardNo.length() - 1).toString();
			cardsName = sbCardName.deleteCharAt(sbCardName.length() - 1).toString();


			subject.replace("#accountNo#", "")
			.replace("#invoiceNo#", req.getBmtbInvoiceHeader().getInvoiceNo().toString())
			.replace("#cabChargeNo#", cards)
			.replace("#cabChargeName#", cardsName);
			  
			EmailUtil.sendEmailwithSender(senderEmail,toEmails.toArray(new String[]{}), ccEmails.toArray(new String[]{}),
					ConfigurableConstants.getEmailText(emailType, subject)
					.replaceAll("#accountNo#", req.getAmtbAccount().getAccountNo().toString())
					.replaceAll("#invoiceNo#", req.getBmtbInvoiceHeader().getInvoiceNo().toString())
					.replaceAll("#cabChargeNo#", cards)
					.replaceAll("#cabChargeName#", cardsName),
					ConfigurableConstants.getEmailText(emailType, ConfigurableConstants.EMAIL_CONTENT)
					
					);
		}
		
		
	}
		
}


