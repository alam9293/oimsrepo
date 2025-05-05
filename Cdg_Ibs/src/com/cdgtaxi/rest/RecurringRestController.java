package com.cdgtaxi.rest;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.zkforge.json.simple.JSONObject;
import org.zkforge.json.simple.parser.JSONParser;
import org.zkoss.zkplus.spring.SpringUtil;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Longbox;
import org.zkoss.zul.Textbox;

import com.cdgtaxi.ibs.common.ConfigurableConstants;
import com.cdgtaxi.ibs.common.NonConfigurableConstants;
import com.cdgtaxi.ibs.common.business.BusinessHelper;
import com.cdgtaxi.ibs.common.model.AmtbAccount;
import com.cdgtaxi.ibs.common.model.IttbRecurringCharge;
import com.cdgtaxi.ibs.common.model.IttbRecurringChargeTagAcct;
import com.cdgtaxi.ibs.common.model.IttbRecurringChargeTagCard;
import com.cdgtaxi.ibs.common.model.PmtbProduct;
import com.cdgtaxi.ibs.common.model.PmtbTopUpReq;
import com.cdgtaxi.ibs.common.model.PmtbTopUpReqCard;
import com.cdgtaxi.ibs.common.model.forms.PaymentInfo;
import com.cdgtaxi.ibs.common.ui.CommonWindow;
import com.cdgtaxi.ibs.product.ui.ProductSearchCriteria;
import com.cdgtaxi.ibs.util.DateUtil;
import com.cdgtaxi.ibs.util.EmailUtil;

public class RecurringRestController extends CommonWindow {

	private static final long serialVersionUID = 5482551235361783789L;
	private static Logger logger = Logger.getLogger(RestController.class);
	private String productNo = "";
	private Listbox resultList;
	protected Longbox invoiceNo;
	protected Textbox cardNo;
	protected Listbox resultLB;
	protected Datebox requestDateFromField;
	protected Datebox requestDateToField;
	protected BusinessHelper businessHelper;

	public RecurringRestController() {
		businessHelper = (BusinessHelper) SpringUtil.getBean("businessHelper");
	}

	public static String getJSON(HttpServletRequest request) throws IOException {

		String body = null;
		StringBuilder stringBuilder = new StringBuilder();
		BufferedReader bufferedReader = null;

		try {
			InputStream inputStream = request.getInputStream();
			if (inputStream != null) {
				bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
				char[] charBuffer = new char[128];
				int bytesRead = -1;
				while ((bytesRead = bufferedReader.read(charBuffer)) > 0) {
					stringBuilder.append(charBuffer, 0, bytesRead);
				}
			} else {
				stringBuilder.append("");
			}
		} catch (IOException ex) {
			throw ex;
		} finally {
			if (bufferedReader != null) {
				try {
					bufferedReader.close();
				} catch (IOException ex) {
					throw ex;
				}
			}
		}

		body = stringBuilder.toString();
		return body;
	}

	public void redirect() {
		HttpServletRequest request = this.getHttpServletRequest();
		HttpServletResponse response = this.getHttpServletResponse();
		try {
			JSONParser parser = new JSONParser();
			// String jsonString = getJSON(request);

			JSONObject json = (JSONObject) parser.parse(new InputStreamReader(request.getInputStream()));
			String tokenId = (String) json.get("tokenId");
			String tokenExpiry = (String) json.get("tokenExpiry");
			String cabChargeCardNo = (String) json.get("cabChargeCardNo");
			String cabChargeAccountNo = (String) json.get("cabChargeAccountNo");
			String creditCardNo = (String) json.get("creditCardNo");
			String creditCardExpiry = (String) json.get("creditCardExpiry");

//				businessHelper.getAccountBusiness().getpmtb
			// AmtbAccount account =
			// this.businessHelper.getAccountBusiness().getAccountByCustNo(custNo);

			AmtbAccount account = null;

			PrintWriter out = response.getWriter();
			response.setContentType("application/json");
			response.setCharacterEncoding("UTF-8");

			out.flush();
			if (cabChargeAccountNo != null && cabChargeAccountNo =="" && cabChargeCardNo != null && cabChargeCardNo=="" ) {
				JSONObject jsonResponse = new JSONObject();
				jsonResponse.put("status", "failed");
				jsonResponse.put("remarks", "please check input parameter");
				out.print(jsonResponse);
			} else {
				if (cabChargeAccountNo != null && cabChargeAccountNo != "" && !cabChargeAccountNo.equals("")) {
					account = this.businessHelper.getAccountBusiness().getRawAccountMain((cabChargeAccountNo));
				} else if (cabChargeCardNo != null) {

				}

				boolean found = false;
				ProductSearchCriteria searchForm = new ProductSearchCriteria();
				searchForm.setTokenId(tokenId);
				List<IttbRecurringCharge> allRC = this.businessHelper.getProductBusiness().searchRC(searchForm);
				if(allRC!=null && allRC.size()!=0) {
					logger.info("Token already existed");
					return;
				}
				searchForm.setTokenActive("Y");
			
				// to check if the account is already associated with the existed
				List<IttbRecurringCharge> allActiveRC = this.businessHelper.getProductBusiness().searchRC(searchForm);

				boolean foundCard = false;
				boolean foundAcct = false;
				// .getAllActiveRecurringRecord();
				if (allActiveRC != null) {
					for (IttbRecurringCharge rc : allActiveRC) {
						// validate if existing token has added this account
						if (account != null) {
							System.out.println(rc.getIttbRecurringChargeTagAcct().size());
							if (rc.getIttbRecurringChargeTagAcct().size() > 0) {
								for (IttbRecurringChargeTagAcct tAcct : rc.getIttbRecurringChargeTagAcct()) {
									if (tAcct.getAmtbAccount().equals(account)) {
										foundAcct = true;
										break;
									}
								}
							}
						}
						// validate if exisiting token has added the card
						if (cabChargeCardNo != null && cabChargeCardNo != "") {
							if (rc.getIttbRecurringChargeTagCard().size() > 0) {
								for (IttbRecurringChargeTagCard tCard : rc.getIttbRecurringChargeTagCard()) {
									if (tCard.getPmtbProduct().getProductNo().equals(new BigDecimal(cabChargeCardNo))) {
										foundCard = true;
										break;
									}
								}
							}
						}
					}
				}
				if (foundCard || foundAcct) {
					logger.info("Skipping this record as Product / Account is already tag some where");
					return;
				}

				// to insert new record
				logger.info("Insert New Token!");
				IttbRecurringCharge newRC = new IttbRecurringCharge();
				Date date1 = new SimpleDateFormat("MMM-yy").parse(tokenExpiry);
				System.out.println(parseCustomDate(date1).getTime());
				newRC.setTokenExpiry(parseCustomDate(date1));
				newRC.setTokenId(tokenId);

				Date date2 = new SimpleDateFormat("MMM-yy").parse(creditCardExpiry);
				newRC.setCreditCardNoExpiry(parseCustomDate(date2));
				newRC.setCreditCardNo(creditCardNo);
				this.businessHelper.getProductBusiness().save(newRC);

				if (account != null) {
					IttbRecurringChargeTagAcct tagAcct = new IttbRecurringChargeTagAcct();
					tagAcct.setAmtbAccount(account);
					tagAcct.setRecurringChargeId(newRC);

					this.businessHelper.getProductBusiness().save(tagAcct);
				}
				if (creditCardNo != null && creditCardNo != "") {
					PmtbProduct card = this.businessHelper.getProductBusiness().getProductByCard(creditCardNo);
					if (card != null) {
						IttbRecurringChargeTagCard tagCard = new IttbRecurringChargeTagCard();
						tagCard.setPmtbProduct(card);
						tagCard.setRecurringChargeId(newRC);
						this.businessHelper.getProductBusiness().save(tagCard);
					}
				}

			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public Date parseCustomDate(Date date) {
		// since file retrieve is always on the 1st of the month , we will need to set
		// it to last day as credit card expires on the last day of the month
		Calendar.getInstance().getActualMaximum(Calendar.DAY_OF_MONTH);
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
		return calendar.getTime();
	}

	public AmtbAccount getAccount(String originalCustomerNo) {

		// 1. get Account
		AmtbAccount account = new AmtbAccount();
		String custNo = "";
		String divCode = "";
		String deptCode = "";
		// depending on size,
		// if size 5 or 6 = custno
		// if size 9 or 10 = custno + div
		// if size 13 or 14 = custno + div + dept
		// String custNo = row.getCell(0).getStringCellValue();

		if (originalCustomerNo.length() == 5 || originalCustomerNo.length() == 9 || originalCustomerNo.length() == 13)
			custNo = originalCustomerNo.substring(0, 5);
		else if (originalCustomerNo.length() == 6 || originalCustomerNo.length() == 10
				|| originalCustomerNo.length() == 14)
			custNo = originalCustomerNo.substring(0, 6);
		else if (originalCustomerNo.length() == 3 || originalCustomerNo.length() == 7
				|| originalCustomerNo.length() == 11)
			custNo = originalCustomerNo.substring(0, 3);
		else
			logger.info("Account not found");

		if (originalCustomerNo.length() == 9 || originalCustomerNo.length() == 13)
			divCode = originalCustomerNo.substring(5, 9);
		else if (originalCustomerNo.length() == 10 || originalCustomerNo.length() == 14)
			divCode = originalCustomerNo.substring(6, 10);
		else if (originalCustomerNo.length() == 7 || originalCustomerNo.length() == 12)
			divCode = originalCustomerNo.substring(3, 7);

		if (originalCustomerNo.length() == 13)
			deptCode = originalCustomerNo.substring(9, 13);
		else if (originalCustomerNo.length() == 14)
			deptCode = originalCustomerNo.substring(10, 14);
		else if (originalCustomerNo.length() == 11)
			deptCode = originalCustomerNo.substring(7, 11);

		logger.info("CustNo > " + custNo + " || Div Code > " + divCode + "  || Dept Code > " + deptCode);

		if (!deptCode.trim().equals("")) {
			account = (AmtbAccount) this.businessHelper.getAccountBusiness().getRawAccount(custNo, divCode, deptCode,
					"DEPT");
		} else if (!divCode.trim().equals("")) {
			account = (AmtbAccount) this.businessHelper.getAccountBusiness().getRawAccount(custNo, divCode, "", "DIV");
		} else {
			account = (AmtbAccount) this.businessHelper.getAccountBusiness().getRawAccount(custNo, null, null, "");
		}

		if (account == null)
			logger.info("Account not found");
		return account;
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
			// Decimalbox paymentAmountDecimalBox = (Decimalbox)
			// this.getFellow("ccPaymentAmount");
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

	private void sendApprovalEmail(PmtbTopUpReq req, String emailType) {

		if (NonConfigurableConstants.getBoolean(req.getApprovalRequired())) {

			AmtbAccount acct = req.getAmtbAccount();
			List<String> ccEmails = new ArrayList<String>();

			String emails = ConfigurableConstants.getEmailText(emailType, "EMAIL");

			String cardNo = "";
			Set<PmtbTopUpReqCard> cards = req.getPmtbTopUpReqCards();
			for (PmtbTopUpReqCard c : cards) {
				cardNo += c.getReqCardNo();
				if (cards.size() > 1) {
					cardNo += ",";
				}
			}

			EmailUtil.sendEmail(emails.split(","), ccEmails.toArray(new String[] {}),
					ConfigurableConstants.getEmailText(emailType, ConfigurableConstants.EMAIL_SUBJECT),
					ConfigurableConstants.getEmailText(emailType, ConfigurableConstants.EMAIL_CONTENT)

							.replaceAll("#cardNo#", cardNo)
							.replaceAll("#nameOnCard#", req.getAmtbAccount().getNameOnCard()));
		}

	}

}
