package com.cdgtaxi.ibs.billing.business;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import org.apache.log4j.Logger;

import com.cdgtaxi.ibs.common.ConfigurableConstants;
import com.cdgtaxi.ibs.common.MasterSetup;
import com.cdgtaxi.ibs.common.NonConfigurableConstants;
import com.cdgtaxi.ibs.common.business.GenericBusinessImpl;
import com.cdgtaxi.ibs.common.model.AmtbAccount;
import com.cdgtaxi.ibs.common.model.BmtbInvoiceDetail;
import com.cdgtaxi.ibs.common.model.BmtbInvoiceHeader;
import com.cdgtaxi.ibs.common.model.BmtbInvoiceSummary;
import com.cdgtaxi.ibs.common.model.BmtbPaymentReceipt;
import com.cdgtaxi.ibs.common.model.BmtbPaymentReceiptDetail;
import com.cdgtaxi.ibs.common.model.FmtbBankCode;
import com.cdgtaxi.ibs.common.model.FmtbEntityMaster;
import com.cdgtaxi.ibs.common.model.PmtbIssuanceReq;
import com.cdgtaxi.ibs.common.model.PmtbPrepaidReq;
import com.cdgtaxi.ibs.common.model.PmtbProduct;
import com.cdgtaxi.ibs.common.model.PmtbProductReplacement;
import com.cdgtaxi.ibs.common.model.PmtbTopUpReq;
import com.cdgtaxi.ibs.common.model.forms.InvoicePaymentDetail;
import com.cdgtaxi.ibs.common.model.forms.PaymentInfo;
import com.cdgtaxi.ibs.common.model.forms.SearchPaymentReceiptForm;
import com.cdgtaxi.ibs.common.ui.CommonWindow;
import com.cdgtaxi.ibs.interfaces.as.api.API;
import com.cdgtaxi.ibs.master.model.MstbBankMaster;
import com.cdgtaxi.ibs.master.model.MstbBranchMaster;
import com.cdgtaxi.ibs.util.DateUtil;

public class PaymentBusinessImpl extends GenericBusinessImpl implements PaymentBusiness {

	private static final Logger logger = Logger.getLogger(PaymentBusinessImpl.class);
	private static Comparator<FmtbBankCode> bankCodeComparator = new Comparator<FmtbBankCode>() {
		public int compare(FmtbBankCode r1, FmtbBankCode r2) {
			try {
				String r1Name = r1.getBankName() + " - " + r1.getBranchName();
				String r2Name = r2.getBankName() + " - " + r2.getBranchName();

				return r1Name.compareTo(r2Name);
			} catch (Exception e) {
				e.printStackTrace();
			}

			return 0;
		}
	};

	public List<AmtbAccount> searchBillableAccount(String customerNo, String name) {
		return this.daoHelper.getAccountDao().getBilliableAccountOnlyTopLevel(customerNo, name);
	}

	public List<AmtbAccount> searchBillableAccountByParentAccount(AmtbAccount parentAccount) {
		return this.daoHelper.getAccountDao().getBilliableAccountByParentAccount(parentAccount);
	}

	public List<AmtbAccount> searchBillableAccountByGrandParentAccount(AmtbAccount grandParentAccount) {
		return this.daoHelper.getAccountDao().getBilliableAccountByGrandParentAccount(grandParentAccount);
	}

	public List<InvoicePaymentDetail> searchOutstandingInvoice(AmtbAccount account) {
		List<InvoicePaymentDetail> paymentDetails = new ArrayList<InvoicePaymentDetail>();
		List<BmtbInvoiceHeader> invoiceHeaders = this.daoHelper.getInvoiceDao()
				.getOutstandingInvoiceByAccount(account);
		// including children's invoices
		invoiceHeaders.addAll(this.daoHelper.getInvoiceDao().getOutstandingInvoiceByParentAccount(account));
		invoiceHeaders.addAll(this.daoHelper.getInvoiceDao().getOutstandingInvoiceByGrandParentAccount(
				account));
		for (BmtbInvoiceHeader invoiceHeader : invoiceHeaders) {
			InvoicePaymentDetail paymentDetail = new InvoicePaymentDetail(invoiceHeader);
			BigDecimal minAppliedAmount = this.daoHelper.getInvoiceDao().getTotalNegativeDetailAmount(
					invoiceHeader);
			minAppliedAmount = minAppliedAmount.multiply(new BigDecimal(-1)); // switch to positive
			paymentDetail.setMinAppliedAmount(minAppliedAmount);
			paymentDetails.add(paymentDetail);
		}
		return paymentDetails;
	}

	public List<InvoicePaymentDetail> searchNegativeInvoice(AmtbAccount account) {
		List<InvoicePaymentDetail> paymentDetails = new ArrayList<InvoicePaymentDetail>();
		List<BmtbInvoiceHeader> invoiceHeaders = this.daoHelper.getInvoiceDao().getNegativeInvoiceByAccount(
				account);
		// including children's invoices
		invoiceHeaders.addAll(this.daoHelper.getInvoiceDao().getNegativeInvoiceByParentAccount(account));
		invoiceHeaders.addAll(this.daoHelper.getInvoiceDao().getNegativeInvoiceByGrandParentAccount(account));
		for (BmtbInvoiceHeader invoiceHeader : invoiceHeaders) {
			InvoicePaymentDetail paymentDetail = new InvoicePaymentDetail(invoiceHeader);
			paymentDetails.add(paymentDetail);
		}
		return paymentDetails;
	}

	// Other than contra, the rest of payment mode should comes into this method.
	// Contract may be different as well but not confirmed.
	public Long createPaymentReceipt(PaymentInfo paymentInfo,
			List<InvoicePaymentDetail> invoicePaymentDetails, String createdBy) throws Exception {
		// Populate Payment Receipt First
		BmtbPaymentReceipt paymentReceipt = new BmtbPaymentReceipt();

		// payment belongs to which account
		if (paymentInfo.getDepartment() != null) {
			paymentReceipt.setAmtbAccount(paymentInfo.getDepartment());
		} else if (paymentInfo.getDivision() != null) {
			paymentReceipt.setAmtbAccount(paymentInfo.getDivision());
		} else {
			paymentReceipt.setAmtbAccount(paymentInfo.getAccount());
		}
		// payment ref no
		if (paymentInfo.getPaymentMode().equals(NonConfigurableConstants.PAYMENT_MODE_CASH)
				|| paymentInfo.getPaymentMode().equals(NonConfigurableConstants.PAYMENT_MODE_CHEQUE)) {
			paymentReceipt.setPaymentNo(null);
		} else {
			paymentReceipt.setPaymentNo(paymentInfo.getTxnRefNo());
		}
		// payment mode
		paymentReceipt.setMstbMasterTableByPaymentMode(ConfigurableConstants.getMasterTable(
				ConfigurableConstants.PAYMENT_MODE, paymentInfo.getPaymentMode()));
		// payment amount
		paymentReceipt.setPaymentAmount(paymentInfo.getPaymentAmount());
		// payment date
		paymentReceipt.setPaymentDate(paymentInfo.getPaymentDate());
		// receipt date
		paymentReceipt.setReceiptDt(paymentInfo.getReceiptDt());
		// bank
		paymentReceipt.setMstbBankMaster((MstbBankMaster) MasterSetup.getBankManager().getMaster(
				paymentInfo.getBankNo()));
		// branch
		paymentReceipt.setMstbBranchMaster((MstbBranchMaster) MasterSetup.getBankManager().getDetail(
				paymentInfo.getBranchNo()));

		if (paymentInfo.getPaymentMode().equals(NonConfigurableConstants.PAYMENT_MODE_CHEQUE)) {
			// Cheque Date
			paymentReceipt.setChequeDate(paymentInfo.getChequeDate());
			// Cheque No
			paymentReceipt.setChequeNo(paymentInfo.getChequeNo());
			// Quick Cheque Deposit
			paymentReceipt
					.setQuickChequeDeposit(paymentInfo.isQuickChequeDeposit() == true ? NonConfigurableConstants.BOOLEAN_YN_YES
							: NonConfigurableConstants.BOOLEAN_YN_NO);
		}

		// Remarks
		paymentReceipt.setRemarks(paymentInfo.getRemarks());
		// bank in
		if(paymentInfo.getBankInNo()!=null)
			paymentReceipt.setFmtbBankCode((FmtbBankCode) this.daoHelper.getGenericDao().get(FmtbBankCode.class,
					paymentInfo.getBankInNo()));
		// excess amount - deduct one by one by the payment details
		paymentReceipt.setExcessAmount(paymentReceipt.getPaymentAmount());
		// Billed Flag - default to N
		paymentReceipt.setBilledFlag(NonConfigurableConstants.BOOLEAN_YN_NO);
		// Cancel Billed Flag
		paymentReceipt.setCancelBilledFlag(NonConfigurableConstants.BOOLEAN_YN_NO);

		Serializable receiptNo = this.daoHelper.getGenericDao().save(paymentReceipt, createdBy);

		// **** populating payment receipt detail *****//
		for (InvoicePaymentDetail paymentDetail : invoicePaymentDetails) {

			BmtbInvoiceHeader invoiceHeader = paymentDetail.getInvoiceHeader();

			boolean isPrepaid = NonConfigurableConstants.getBoolean(invoiceHeader.getPrepaidFlag());
			
			// full payment
			if (paymentDetail.isFullPayment()) {

				// Create one receipt detail for each invoice detail
				Set<BmtbInvoiceSummary> invoiceSummaries = invoiceHeader.getBmtbInvoiceSummaries();
				for (BmtbInvoiceSummary invoiceSummary : invoiceSummaries) {
					Set<BmtbInvoiceDetail> invoiceDetails = invoiceSummary.getBmtbInvoiceDetails();
					for (BmtbInvoiceDetail invoiceDetail : invoiceDetails) {
						// no outstanding amount, ignore
						if (invoiceDetail.getOutstandingAmount().compareTo(new BigDecimal(0)) == 0) {
							continue;
						}

						// this is for later part to calculate returning of credit balance
						BigDecimal appliedAmount = invoiceDetail.getOutstandingAmount();

						// creates receipt detail for full payment
						BmtbPaymentReceiptDetail paymentReceiptDetail = new BmtbPaymentReceiptDetail();
						paymentReceiptDetail.setAppliedAmount(invoiceDetail.getOutstandingAmount());
						paymentReceiptDetail.setBilledFlag(NonConfigurableConstants.BOOLEAN_NO);
						paymentReceiptDetail.setCancelBilledFlag(NonConfigurableConstants.BOOLEAN_NO);
						paymentReceiptDetail.setBmtbPaymentReceipt(paymentReceipt);
						paymentReceiptDetail.setBmtbInvoiceHeader(invoiceHeader);
						paymentReceiptDetail.setBmtbInvoiceDetail(invoiceDetail);
						this.daoHelper.getGenericDao().save(paymentReceiptDetail, createdBy);

						// update outstanding amount
						invoiceDetail.setOutstandingAmount(new BigDecimal(0));
						this.daoHelper.getGenericDao().update(invoiceDetail, createdBy);

						// subtract excess amount
						paymentReceipt.setExcessAmount(paymentReceipt.getExcessAmount().subtract(
								paymentReceiptDetail.getAppliedAmount()));

						// Increase credit balance
						BigDecimal deductedCreditBalance = this.increaseCreditBalance(invoiceDetail,
								appliedAmount, createdBy, isPrepaid);
						paymentReceiptDetail.setDeductedCreditBalance(deductedCreditBalance);
						this.daoHelper.getGenericDao().update(paymentReceiptDetail);
					}
				}

				// save the updated excess amount
				this.daoHelper.getGenericDao().update(paymentReceipt);

				// update all invoice outstanding amount to 0 and the status as well
				invoiceHeader.setInvoiceStatus(NonConfigurableConstants.INVOICE_STATUS_CLOSED);
				invoiceHeader.setRecurringDoneFlag("Y");
				invoiceHeader.setOutstandingAmount(new BigDecimal(0));

				this.daoHelper.getGenericDao().update(invoiceHeader, createdBy);
				
				//issue or top up the pending payment PREPAID product upon full payment
				if(isPrepaid){
					long invoiceHeaderNo = invoiceHeader.getInvoiceHeaderNo();
					PmtbPrepaidReq req = this.daoHelper.getPrepaidDao().getPrepaidRequestWithInvoiceHeader(invoiceHeaderNo);
					if(req!=null){
						String status = req.getStatus();
						logger.info("Prepaid request status: " + status);
						if(NonConfigurableConstants.PREPAID_REQUEST_STATUS_PENDING_PAYMENT.equals(status)){
							if(req instanceof PmtbIssuanceReq){
								PmtbIssuanceReq iReq = (PmtbIssuanceReq) req;
								this.businessHelper.getPrepaidBusiness().issueProduct(iReq, true, CommonWindow.getUserLoginIdAndDomain());
							} else if (req instanceof PmtbTopUpReq){
								PmtbTopUpReq tReq = (PmtbTopUpReq) req;
								this.businessHelper.getPrepaidBusiness().topUp(tReq, true, CommonWindow.getUserLoginIdAndDomain());
							}
						}
					}
				}

			}
			// partial payment
			else {
				Map<Long, BigDecimal> appliedInvoiceDetailAmountMap = paymentDetail
						.getInvoiceDetailAppliedAmount();
				Map<Long, BmtbInvoiceDetail> appliedInvoiceDetailMap = paymentDetail
						.getInvoiceDetailApplied();
				Set<Long> keys = appliedInvoiceDetailMap.keySet();
				BigDecimal outstandingAmount = new BigDecimal("0");
				
				for (Long invoiceDetailNo : keys) {

					BmtbInvoiceDetail invoiceDetail = appliedInvoiceDetailMap.get(invoiceDetailNo);
					BigDecimal appliedAmount = appliedInvoiceDetailAmountMap.get(invoiceDetailNo);

					// creates receipt detail for partial payment
					BmtbPaymentReceiptDetail paymentReceiptDetail = new BmtbPaymentReceiptDetail();
					paymentReceiptDetail.setAppliedAmount(appliedAmount);
					paymentReceiptDetail.setBilledFlag(NonConfigurableConstants.BOOLEAN_NO);
					paymentReceiptDetail.setCancelBilledFlag(NonConfigurableConstants.BOOLEAN_NO);
					paymentReceiptDetail.setBmtbPaymentReceipt(paymentReceipt);
					paymentReceiptDetail.setBmtbInvoiceHeader(invoiceHeader);
					paymentReceiptDetail.setBmtbInvoiceDetail(invoiceDetail);
					this.daoHelper.getGenericDao().save(paymentReceiptDetail, createdBy);

					// update invoiceDetail
					invoiceDetail.setOutstandingAmount(invoiceDetail.getOutstandingAmount().subtract(
							appliedAmount));
					this.daoHelper.getGenericDao().update(invoiceDetail, createdBy);

					// reduce outstanding amount - inclusive discount amount (so must add back discount amount
					// to prevent negative amount)
					invoiceHeader.setOutstandingAmount(invoiceHeader.getOutstandingAmount().subtract(
							appliedAmount));
					outstandingAmount = invoiceHeader.getOutstandingAmount();
					
					// subtract excess amount
					paymentReceipt.setExcessAmount(paymentReceipt.getExcessAmount().subtract(
							paymentReceiptDetail.getAppliedAmount()));

					// Increase credit balance
					BigDecimal deductedCreditBalance = this.increaseCreditBalance(invoiceDetail,
							appliedAmount, createdBy, isPrepaid);
					paymentReceiptDetail.setDeductedCreditBalance(deductedCreditBalance);
					this.daoHelper.getGenericDao().update(paymentReceiptDetail);
				}

				// save the updated excess amount
				this.daoHelper.getGenericDao().update(paymentReceipt);

				// Partial Payment has managed to pay the rest for this invoice.
				if (this.daoHelper.getInvoiceDao().isInvoiceStillOutstanding(
						invoiceHeader.getInvoiceHeaderNo()) == false) {
					invoiceHeader.setInvoiceStatus(NonConfigurableConstants.INVOICE_STATUS_CLOSED);
					invoiceHeader.setRecurringDoneFlag("Y");
				}

				this.daoHelper.getGenericDao().update(invoiceHeader, createdBy);
				
				if(outstandingAmount.compareTo(new BigDecimal("0")) == 0)
				{
					if(isPrepaid){
						long invoiceHeaderNo = invoiceHeader.getInvoiceHeaderNo();
						PmtbPrepaidReq req = this.daoHelper.getPrepaidDao().getPrepaidRequestWithInvoiceHeader(invoiceHeaderNo);
						if(req!=null){
							String status = req.getStatus();
							logger.info("Prepaid request status: " + status);
							if(NonConfigurableConstants.PREPAID_REQUEST_STATUS_PENDING_PAYMENT.equals(status)){
								if(req instanceof PmtbIssuanceReq){
									PmtbIssuanceReq iReq = (PmtbIssuanceReq) req;
									this.businessHelper.getPrepaidBusiness().issueProduct(iReq, true, CommonWindow.getUserLoginIdAndDomain());
								} else if (req instanceof PmtbTopUpReq){
									PmtbTopUpReq tReq = (PmtbTopUpReq) req;
									this.businessHelper.getPrepaidBusiness().topUp(tReq, true, CommonWindow.getUserLoginIdAndDomain());
								}
							}
						}
					}
				}
			}
		}

		return (Long) receiptNo;
	}

	/**
	 * To increase back the credit balance after payment.
	 * 
	 * @param invoiceDetail
	 *            the invoice detail that is receiving the payment
	 * @param appliedAmount
	 *            the amount of payment to invoice detail
	 * @param updatedBy
	 * @throws Exception
	 */
	private BigDecimal increaseCreditBalance(BmtbInvoiceDetail invoiceDetail, BigDecimal appliedAmount,
			String updatedBy, boolean isPrepaid) throws Exception {

		BigDecimal returnCreditBalanceAmount = new BigDecimal(0);

		// applied $0 ignored
		if (appliedAmount.compareTo(new BigDecimal(0)) <= 0) {
			return returnCreditBalanceAmount;
		}
		// misc detail ignored
		if (invoiceDetail.getPmtbProductType() == null) {
			return returnCreditBalanceAmount;
		}

		// determine the txn amount to be return back to credit balance
		BigDecimal previousPayment = invoiceDetail.getTotalNew().subtract(
				invoiceDetail.getOutstandingAmount().add(appliedAmount));
		BigDecimal remainingTxnAmount = invoiceDetail.getNewTxn().subtract(previousPayment);

		// if remainingTxnAmount still can be returned to credit balance
		if (remainingTxnAmount.compareTo(new BigDecimal(0)) > 0) {
			if (remainingTxnAmount.compareTo(appliedAmount) > 0) {
				returnCreditBalanceAmount = appliedAmount;
			} else if (remainingTxnAmount.compareTo(appliedAmount) <= 0) {
				returnCreditBalanceAmount = remainingTxnAmount;
			}
		}

		if (returnCreditBalanceAmount.compareTo(new BigDecimal(0)) > 0) {
			// Card Based
			if (invoiceDetail.getPmtbProduct() != null) {
				PmtbProduct product = invoiceDetail.getPmtbProduct();
				// Credit balance can be null because the credit limit flag is not flagged as 'Y'
				if (product.getCreditBalance() != null) {
					
					if(!isPrepaid)
					{
						logger.info("Checking if got other replaced card for.. > "+product.getCardNo()+ " and Balance > "+returnCreditBalanceAmount);
						//Check got future card, Update the credit balance n loop
						checkReplaceCard(product.getCardNo(), returnCreditBalanceAmount, "add" , updatedBy);
						logger.info("Checking and update done");
					}
					
					product = (PmtbProduct) this.daoHelper.getGenericDao().load(PmtbProduct.class,	product.getProductNo());
					product.setCreditBalance(product.getCreditBalance().add(returnCreditBalanceAmount));
					product.setUpdatedBy(updatedBy);
					
					// 20 May 2016 - if balance more then limit, do Code Self Recovery.
					// if got temp limit , take temp, else take normal limit.
					// Do not let balance more then limit.
					product = doCheckProductBalanceRecovery(product);
					this.daoHelper.getGenericDao().update(product, product.getUpdatedBy());
				}
				// Still we need to update payment to update the amount used in AS
				// to call API.formulateAccountId
				API.updateProductPayment(product.getCardNo().toString(), product.getCreditBalance()
						.toString(), API.formulateAccountId(product.getAmtbAccount()), product.getUpdatedBy());
			}

			if(!isPrepaid){
				// Recursive method to loop starting from invoice detail's account all the way to top level.
				// ** using invoice detail's account because product's account might have changed
				AmtbAccount account = invoiceDetail.getAmtbAccount();
				do {
					// reload the account to prevent row updated issue
					account = (AmtbAccount) this.daoHelper.getGenericDao().load(AmtbAccount.class,
							account.getAccountNo());
	
					account.setCreditBalance(account.getCreditBalance().add(returnCreditBalanceAmount));
					
					//20 May 2016 if Acct balance above limit, process Balance Recovery.
					// Do not allow balance more then limit.
					account.setUpdatedBy(updatedBy);
					account = doCheckAccountBalanceRecovery(account);
					
					// System.out.println("********** "+account.getAccountNo()+","+account.getCreditBalance()+" **********");
					// System.out.println("********** "+account.getVersion()+" **********");
	
					/*
					 * 11 May 2010 Seng Tat
					 * Merging does not work here because payment is made across invoice details.
					 * At one state the top level account's version might be updated(merging) to 3
					 * but the other detail's account might still remains as version 1.
					 * To resolve we need to reload using "load" to pull out the updated persisted entity
					 * in the memory.
					 */
					// account = (AmtbAccount)this.daoHelper.getGenericDao().merge(account);
					this.daoHelper.getGenericDao().update(account);
	
					API.updateAccountPayment(API.formulateAccountId(account), account.getCreditBalance()
							.toString(), API.formulateParentAccountId(account), account.getUpdatedBy());
					account = account.getAmtbAccount();
				} while (account != null);
			}
		}

		return returnCreditBalanceAmount;
	}

	public void rewardEarlyPayment(AmtbAccount account, String updatedBy,
			List<InvoicePaymentDetail> invoicePaymentDetails) {
		for (InvoicePaymentDetail invPymtDtl : invoicePaymentDetails) {
			// retrieve paid invoice from invoice payment detail
			BmtbInvoiceHeader paidInvoice = invPymtDtl.getInvoiceHeader();
			// Paying an invoice without due date has no impact to early payment
			if (paidInvoice.getDueDate() != null) {
				// Check for early payment
				BigDecimal totalOutstandingAmount = this.daoHelper.getInvoiceDao().getTotalOutstandingAmount(
						account.getAccountNo(), paidInvoice.getDueDate());
				logger.info("Finding Due Outstanding Amount <" + account.getAccountNo() + ","
						+ paidInvoice.getDueDate() + ">: " + totalOutstandingAmount);
				// no other debts present then can award early
				if (totalOutstandingAmount == null
						|| totalOutstandingAmount.compareTo(new BigDecimal(0)) <= 0) {
					List<Long> invoiceHeaderNos = this.daoHelper.getInvoiceDao()
							.getInvoicesToRewardEarlyPayment(account.getAccountNo());
					logger.info("Rewarding Early Payment <" + account.getAccountNo() + ">: "
							+ invoiceHeaderNos.toString());
					for (Long invoiceHeaderNo : invoiceHeaderNos) {
						BmtbInvoiceHeader invoiceHeader = (BmtbInvoiceHeader) this.daoHelper.getGenericDao()
								.get(BmtbInvoiceHeader.class, invoiceHeaderNo);
						invoiceHeader.setEarlyPaymentFlag(NonConfigurableConstants.BOOLEAN_YES);
						this.daoHelper.getGenericDao().update(invoiceHeader, updatedBy);
					}
				}
			}
		}
	}

	public Long createContraReceipt(PaymentInfo paymentInfo,
			List<InvoicePaymentDetail> invoicePaymentDetails, String createdBy) throws Exception {
		// Populate Payment Receipt First
		BmtbPaymentReceipt paymentReceipt = new BmtbPaymentReceipt();

		// payment belongs to which account
		if (paymentInfo.getDepartment() != null) {
			paymentReceipt.setAmtbAccount(paymentInfo.getDepartment());
		} else if (paymentInfo.getDivision() != null) {
			paymentReceipt.setAmtbAccount(paymentInfo.getDivision());
		} else {
			paymentReceipt.setAmtbAccount(paymentInfo.getAccount());
		}
		// payment mode
		paymentReceipt.setMstbMasterTableByPaymentMode(ConfigurableConstants.getMasterTable(
				ConfigurableConstants.PAYMENT_MODE, paymentInfo.getPaymentMode()));
		// payment amount
		paymentReceipt.setPaymentAmount(paymentInfo.getPaymentAmount());
		// payment date
		paymentReceipt.setPaymentDate(paymentInfo.getPaymentDate());
		// receipt date
		paymentReceipt.setReceiptDt(paymentInfo.getReceiptDt());
		// Remarks
		paymentReceipt.setRemarks(paymentInfo.getRemarks());
		// bank in
		paymentReceipt.setFmtbBankCode((FmtbBankCode) this.daoHelper.getGenericDao().get(FmtbBankCode.class,
				paymentInfo.getBankInNo()));
		// excess amount - deduct one by one by the payment details
		paymentReceipt.setExcessAmount(paymentReceipt.getPaymentAmount());
		// Billed Flag - default to N
		paymentReceipt.setBilledFlag(NonConfigurableConstants.BOOLEAN_YN_NO);
		// Cancel Billed Flag
		paymentReceipt.setCancelBilledFlag(NonConfigurableConstants.BOOLEAN_YN_NO);

		Serializable receiptNo = this.daoHelper.getGenericDao().save(paymentReceipt, createdBy);

		BigDecimal paymentAmount = new BigDecimal(0);

		// **** populating payment receipt detail *****//
		for (InvoicePaymentDetail paymentDetail : invoicePaymentDetails) {

			BmtbInvoiceHeader invoiceHeader = paymentDetail.getInvoiceHeader();
			boolean isPrepaid = NonConfigurableConstants.getBoolean(invoiceHeader.getPrepaidFlag());
			
			// Create one receipt detail for each invoice detail
			Set<BmtbInvoiceSummary> invoiceSummaries = invoiceHeader.getBmtbInvoiceSummaries();
			for (BmtbInvoiceSummary invoiceSummary : invoiceSummaries) {
				Set<BmtbInvoiceDetail> invoiceDetails = invoiceSummary.getBmtbInvoiceDetails();
				for (BmtbInvoiceDetail invoiceDetail : invoiceDetails) {

					// no outstanding amount, ignore
					if (invoiceDetail.getOutstandingAmount().compareTo(new BigDecimal(0)) == 0) {
						continue;
					}

					// this is for later part to calculate returning of credit balance
					BigDecimal appliedAmount = invoiceDetail.getOutstandingAmount();

					// creates receipt detail for full payment
					BmtbPaymentReceiptDetail paymentReceiptDetail = new BmtbPaymentReceiptDetail();
					paymentReceiptDetail.setAppliedAmount(invoiceDetail.getOutstandingAmount());
					paymentReceiptDetail.setBilledFlag(NonConfigurableConstants.BOOLEAN_NO);
					paymentReceiptDetail.setCancelBilledFlag(NonConfigurableConstants.BOOLEAN_NO);
					paymentReceiptDetail.setBmtbPaymentReceipt(paymentReceipt);
					paymentReceiptDetail.setBmtbInvoiceHeader(invoiceHeader);
					paymentReceiptDetail.setBmtbInvoiceDetail(invoiceDetail);
					this.daoHelper.getGenericDao().save(paymentReceiptDetail, createdBy);

					// update outstanding amount
					invoiceDetail.setOutstandingAmount(new BigDecimal(0));
					this.daoHelper.getGenericDao().update(invoiceDetail, createdBy);

					// accumulate payment amount
					// * Applied Amount in invoice detail is negative amount so must change to positive
					paymentAmount = paymentAmount.add(appliedAmount.multiply(new BigDecimal(-1)));
				}
			}

			// save the updated payment and excess amount
			paymentReceipt.setPaymentAmount(paymentAmount);
			paymentReceipt.setExcessAmount(paymentAmount);
			this.daoHelper.getGenericDao().update(paymentReceipt);

			// update all invoice outstanding amount to 0 and the status as well
			invoiceHeader.setInvoiceStatus(NonConfigurableConstants.INVOICE_STATUS_CLOSED);
			invoiceHeader.setRecurringDoneFlag("Y");
			invoiceHeader.setOutstandingAmount(new BigDecimal(0));

			this.daoHelper.getGenericDao().update(invoiceHeader, createdBy);
			
			//29/06/2016 AF/0616/018 Clear-Document Should issue Prepaid
			//Contra sure is full payment.
			//issue or top up the pending payment PREPAID product upon full payment
			if(isPrepaid){
				long invoiceHeaderNo = invoiceHeader.getInvoiceHeaderNo();
				PmtbPrepaidReq req = this.daoHelper.getPrepaidDao().getPrepaidRequestWithInvoiceHeader(invoiceHeaderNo);
				if(req!=null){
					String status = req.getStatus();
					logger.info("Prepaid request status: " + status);
					if(NonConfigurableConstants.PREPAID_REQUEST_STATUS_PENDING_PAYMENT.equals(status)){
						if(req instanceof PmtbIssuanceReq){
							PmtbIssuanceReq iReq = (PmtbIssuanceReq) req;
							this.businessHelper.getPrepaidBusiness().issueProduct(iReq, true, CommonWindow.getUserLoginIdAndDomain());
						} else if (req instanceof PmtbTopUpReq){
							PmtbTopUpReq tReq = (PmtbTopUpReq) req;
							this.businessHelper.getPrepaidBusiness().topUp(tReq, true, CommonWindow.getUserLoginIdAndDomain());
						}
					}
				}
			}
		}

		return (Long) receiptNo;
	}

	public List<BmtbPaymentReceipt> searchPaymentReceipt(SearchPaymentReceiptForm form) {
		return this.daoHelper.getPaymentReceiptDao().getPaymentReceipt(form);
	}

	public BmtbPaymentReceipt searchPaymentReceipt(Long receiptNo) {
		return this.daoHelper.getPaymentReceiptDao().getPaymentReceipt(receiptNo);
	}

	public void applyPaymentReceipt(BmtbPaymentReceipt paymentReceipt,
			List<InvoicePaymentDetail> invoicePaymentDetails, String updatedBy) throws Exception {

		// **** populating payment receipt detail *****//
		for (InvoicePaymentDetail paymentDetail : invoicePaymentDetails) {

			BmtbInvoiceHeader invoiceHeader = paymentDetail.getInvoiceHeader();
			
			boolean isPrepaid = NonConfigurableConstants.getBoolean(invoiceHeader.getPrepaidFlag());
			
			// full payment
			if (paymentDetail.isFullPayment()) {

				// Create one receipt detail for each invoice detail
				Set<BmtbInvoiceSummary> invoiceSummaries = invoiceHeader.getBmtbInvoiceSummaries();
				for (BmtbInvoiceSummary invoiceSummary : invoiceSummaries) {
					Set<BmtbInvoiceDetail> invoiceDetails = invoiceSummary.getBmtbInvoiceDetails();
					for (BmtbInvoiceDetail invoiceDetail : invoiceDetails) {
						// no outstanding amount, ignore
						if (invoiceDetail.getOutstandingAmount().compareTo(new BigDecimal(0)) == 0) {
							continue;
						}

						// this is for later part to calculate returning of credit balance
						BigDecimal appliedAmount = invoiceDetail.getOutstandingAmount();

						// creates receipt detail for full payment
						BmtbPaymentReceiptDetail paymentReceiptDetail = new BmtbPaymentReceiptDetail();
						paymentReceiptDetail.setAppliedAmount(invoiceDetail.getOutstandingAmount());
						paymentReceiptDetail.setBilledFlag(NonConfigurableConstants.BOOLEAN_NO);
						paymentReceiptDetail.setCancelBilledFlag(NonConfigurableConstants.BOOLEAN_NO);
						paymentReceiptDetail.setBmtbPaymentReceipt(paymentReceipt);
						paymentReceiptDetail.setBmtbInvoiceHeader(invoiceHeader);
						paymentReceiptDetail.setBmtbInvoiceDetail(invoiceDetail);
						this.daoHelper.getGenericDao().save(paymentReceiptDetail, updatedBy);

						// update outstanding amount
						invoiceDetail.setOutstandingAmount(new BigDecimal(0));
						this.daoHelper.getGenericDao().update(invoiceDetail, updatedBy);

						// subtract excess amount
						paymentReceipt.setExcessAmount(paymentReceipt.getExcessAmount().subtract(
								paymentReceiptDetail.getAppliedAmount()));

						// Increase credit balance
						BigDecimal deductedCreditBalance = this.increaseCreditBalance(invoiceDetail,
								appliedAmount, updatedBy, isPrepaid);
						paymentReceiptDetail.setDeductedCreditBalance(deductedCreditBalance);
						this.daoHelper.getGenericDao().update(paymentReceiptDetail);
					}
				}

				// save the updated excess amount
				this.daoHelper.getGenericDao().update(paymentReceipt, updatedBy);

				// update all invoice outstanding amount to 0 and the status as well
				invoiceHeader.setInvoiceStatus(NonConfigurableConstants.INVOICE_STATUS_CLOSED);
				invoiceHeader.setRecurringDoneFlag("Y");
				invoiceHeader.setOutstandingAmount(new BigDecimal(0));

				this.daoHelper.getGenericDao().update(invoiceHeader, updatedBy);
				
				
				//issue or top up the pending payment PREPAID product upon full payment
				if(NonConfigurableConstants.getBoolean(invoiceHeader.getPrepaidFlag())){
					long invoiceHeaderNo = invoiceHeader.getInvoiceHeaderNo();
					PmtbPrepaidReq req = this.daoHelper.getPrepaidDao().getPrepaidRequestWithInvoiceHeader(invoiceHeaderNo);
					if(req!=null){
						String status = req.getStatus();
						logger.info("Prepaid request status: " + status);
						if(NonConfigurableConstants.PREPAID_REQUEST_STATUS_PENDING_PAYMENT.equals(status)){
							if(req instanceof PmtbIssuanceReq){
								PmtbIssuanceReq iReq = (PmtbIssuanceReq) req;
								this.businessHelper.getPrepaidBusiness().issueProduct(iReq, true, CommonWindow.getUserLoginIdAndDomain());
							} else if (req instanceof PmtbTopUpReq){
								PmtbTopUpReq tReq = (PmtbTopUpReq) req;
								this.businessHelper.getPrepaidBusiness().topUp(tReq, true, CommonWindow.getUserLoginIdAndDomain());
							}
						}
					}
				}
				
			}
			// partial payment
			else {
				Map<Long, BigDecimal> appliedInvoiceDetailAmountMap = paymentDetail
						.getInvoiceDetailAppliedAmount();
				Map<Long, BmtbInvoiceDetail> appliedInvoiceDetailMap = paymentDetail
						.getInvoiceDetailApplied();
				Set<Long> keys = appliedInvoiceDetailMap.keySet();
				for (Long invoiceDetailNo : keys) {

					BmtbInvoiceDetail invoiceDetail = appliedInvoiceDetailMap.get(invoiceDetailNo);
					BigDecimal appliedAmount = appliedInvoiceDetailAmountMap.get(invoiceDetailNo);

					// creates receipt detail for partial payment
					BmtbPaymentReceiptDetail paymentReceiptDetail = new BmtbPaymentReceiptDetail();
					paymentReceiptDetail.setAppliedAmount(appliedAmount);
					paymentReceiptDetail.setBilledFlag(NonConfigurableConstants.BOOLEAN_NO);
					paymentReceiptDetail.setCancelBilledFlag(NonConfigurableConstants.BOOLEAN_NO);
					paymentReceiptDetail.setBmtbPaymentReceipt(paymentReceipt);
					paymentReceiptDetail.setBmtbInvoiceHeader(invoiceHeader);
					paymentReceiptDetail.setBmtbInvoiceDetail(invoiceDetail);
					this.daoHelper.getGenericDao().save(paymentReceiptDetail, updatedBy);

					// update invoiceDetail
					invoiceDetail.setOutstandingAmount(invoiceDetail.getOutstandingAmount().subtract(
							appliedAmount));
					this.daoHelper.getGenericDao().update(invoiceDetail, updatedBy);

					// reduce outstanding amount - inclusive discount amount (so must add back discount amount
					// to prevent negative amount)
					invoiceHeader.setOutstandingAmount(invoiceHeader.getOutstandingAmount().subtract(
							appliedAmount));

					// subtract excess amount
					paymentReceipt.setExcessAmount(paymentReceipt.getExcessAmount().subtract(
							paymentReceiptDetail.getAppliedAmount()));

					// Increase credit balance
					BigDecimal deductedCreditBalance = this.increaseCreditBalance(invoiceDetail,
							appliedAmount, updatedBy, isPrepaid);
					paymentReceiptDetail.setDeductedCreditBalance(deductedCreditBalance);
					this.daoHelper.getGenericDao().update(paymentReceiptDetail);
				}

				// save the updated excess amount
				this.daoHelper.getGenericDao().update(paymentReceipt, updatedBy);

				// Partial Payment has managed to pay the rest for this invoice.
				if (this.daoHelper.getInvoiceDao().isInvoiceStillOutstanding(
						invoiceHeader.getInvoiceHeaderNo()) == false) {
					invoiceHeader.setInvoiceStatus(NonConfigurableConstants.INVOICE_STATUS_CLOSED);
					invoiceHeader.setRecurringDoneFlag("Y");
				}

				this.daoHelper.getGenericDao().update(invoiceHeader, updatedBy);
			}
		}
	}

	public void cancelPaymentReceipt(BmtbPaymentReceipt paymentReceipt, String updatedBy) throws Exception {
		// update the payment receipt first
		paymentReceipt.setCancelDt(DateUtil.getCurrentTimestamp());
		this.daoHelper.getGenericDao().update(paymentReceipt, updatedBy);

		// reverse the payment details
		Set<BmtbPaymentReceiptDetail> paymentDetails = paymentReceipt.getBmtbPaymentReceiptDetails();
		for (BmtbPaymentReceiptDetail paymentDetail : paymentDetails) {
			BmtbInvoiceHeader invoiceHeader = paymentDetail.getBmtbInvoiceHeader();
			BmtbInvoiceDetail invoiceDetail = paymentDetail.getBmtbInvoiceDetail();
			boolean isPrepaid = NonConfigurableConstants.getBoolean(invoiceHeader.getPrepaidFlag());
			
			// update invoiceHeader first
			if (invoiceHeader.getInvoiceStatus().equals(NonConfigurableConstants.INVOICE_STATUS_CLOSED)) {
				invoiceHeader.setInvoiceStatus(NonConfigurableConstants.INVOICE_STATUS_OUTSTANDING);
			}

			// take back current invoice early payment if awarded
			if (invoiceHeader.getEarlyPaymentFlag().equals(NonConfigurableConstants.BOOLEAN_YN_YES))
				invoiceHeader.setEarlyPaymentFlag(NonConfigurableConstants.BOOLEAN_YN_NO);

			invoiceHeader.setOutstandingAmount(invoiceHeader.getOutstandingAmount().add(
					paymentDetail.getAppliedAmount()));
			this.daoHelper.getGenericDao().update(invoiceHeader, updatedBy);

			// reverse back credit balance
			this.decreaseCreditBalance(invoiceDetail, paymentDetail.getAppliedAmount(), updatedBy, isPrepaid);

			// update invoiceDetail
			invoiceDetail.setOutstandingAmount(invoiceDetail.getOutstandingAmount().add(
					paymentDetail.getAppliedAmount()));
			this.daoHelper.getGenericDao().update(invoiceDetail, updatedBy);
		}

		// find the top level account
		AmtbAccount account = paymentReceipt.getAmtbAccount();
		if (account.getAccountCategory().equals(NonConfigurableConstants.ACCOUNT_CATEGORY_DEPARTMENT)) {
			account = account.getAmtbAccount().getAmtbAccount();
		} else if (account.getAccountCategory().equals(NonConfigurableConstants.ACCOUNT_CATEGORY_DIVISION)) {
			account = account.getAmtbAccount();
		}

		for (BmtbPaymentReceiptDetail paymentDetail : paymentDetails) {
			BmtbInvoiceHeader cancelledInvoice = paymentDetail.getBmtbInvoiceHeader();
			// Cancel an invoice without due date has no impact to early payment
			if (cancelledInvoice.getDueDate() != null) {
				// take back all early payment rewarded to invoices that are under the corporate
				List<Long> invoiceNos = this.daoHelper.getInvoiceDao().getNotBilledEarlyPaymentInvoices(
						account.getAccountNo(), cancelledInvoice.getDueDate());
				for (Long invoiceNo : invoiceNos) {
					BmtbInvoiceHeader invoiceHeader = (BmtbInvoiceHeader) this.daoHelper.getInvoiceDao().get(
							BmtbInvoiceHeader.class, invoiceNo);
					invoiceHeader.setEarlyPaymentFlag(NonConfigurableConstants.BOOLEAN_YN_NO);
					this.daoHelper.getGenericDao().update(invoiceHeader, updatedBy);
				}
			}
		}
	}

	private void decreaseCreditBalance(BmtbInvoiceDetail invoiceDetail, BigDecimal cancelledAmount,
			String updatedBy, boolean isPrepaid) throws Exception {

		// misc detail ignored
		if (invoiceDetail.getPmtbProductType() == null) {
			return;
		}

		// determine what amount to decrease
		BigDecimal creditBalanceToDecrease = new BigDecimal(0);
		BigDecimal previousPayment = invoiceDetail.getTotalNew().subtract(
				invoiceDetail.getOutstandingAmount());

		if (previousPayment.compareTo(invoiceDetail.getNewTxn()) > 0) {
			BigDecimal difference = previousPayment.subtract(invoiceDetail.getNewTxn());
			creditBalanceToDecrease = cancelledAmount.subtract(difference);
		} else {
			creditBalanceToDecrease = cancelledAmount;
		}

		// Card Based
		if (invoiceDetail.getPmtbProduct() != null) {
			PmtbProduct product = invoiceDetail.getPmtbProduct();
			// Credit balance can be null if credit limit flag is not flagged as 'Y'
			if (product.getCreditBalance() != null) {
				
				if(!isPrepaid)
				{
					logger.info("Checking if got other replaced card for.. > "+product.getCardNo()+ " and Balance > "+creditBalanceToDecrease);
					//Check got future card, Update the credit balance n loop
					checkReplaceCard(product.getCardNo(), creditBalanceToDecrease, "subtract", updatedBy);
					logger.info("Checking and update done");
				}
				
				product = (PmtbProduct) this.daoHelper.getGenericDao().load(PmtbProduct.class,	product.getProductNo());
				product.setCreditBalance(product.getCreditBalance().subtract(creditBalanceToDecrease));
				this.daoHelper.getGenericDao().update(product, updatedBy);
			
			}
			// Still we need to reverse payment to update the amount used in AS
			API.updateProductPayment(product.getCardNo().toString(), product.getCreditBalance().toString(),
					API.formulateAccountId(product.getAmtbAccount()), updatedBy);
		}

		if(!isPrepaid)
		{
			// Recursive method to loop starting from invoice detail's account all the way to top level.
			// ** using invoice detail's account because product's account might have changed
			AmtbAccount account = invoiceDetail.getAmtbAccount();
			do {
				// reload the account to prevent row updated issue
				account = (AmtbAccount) this.daoHelper.getGenericDao().load(AmtbAccount.class,
						account.getAccountNo());
	
				account.setCreditBalance(account.getCreditBalance().subtract(creditBalanceToDecrease));
	
				/*
				 * 11 May 2010 Seng Tat
				 * Merging does not work here because payment is made across invoice details.
				 * At one state the top level account's version might be updated(merging) to 3
				 * but the other detail's account might still remains as version 1.
				 * To resolve we need to reload using "load" to pull out the updated persisted entity
				 * in the memory.
				 */
				// account = (AmtbAccount)this.daoHelper.getGenericDao().merge(account);
				this.daoHelper.getGenericDao().update(account);
	
				API.updateAccountPayment(API.formulateAccountId(account), account.getCreditBalance().toString(),
						API.formulateParentAccountId(account), updatedBy);
				account = account.getAmtbAccount();
			} while (account != null);
		}
	}

	public List<BmtbPaymentReceipt> searchPaymentReceiptsByInvoice(Long invoiceNo) {
		return daoHelper.getPaymentReceiptDao().getPaymentReceiptsByInvoice(invoiceNo);
	}

	public Set<FmtbBankCode> searchBankInBanks(AmtbAccount account) {
		Set<FmtbBankCode> bankCodes = new TreeSet<FmtbBankCode>(bankCodeComparator);
		List<Object[]> results = this.daoHelper.getBankCodeDao().getBankInBanks(account);
		for (Object[] result : results) {
			FmtbEntityMaster entity = (FmtbEntityMaster)result[2];
			FmtbBankCode bankCode = this.daoHelper.getBankCodeDao().getLatestBankCode(result[0].toString(),
					result[1].toString(), entity);
			if (bankCode != null)
				bankCodes.add(bankCode);
		}

		return bankCodes;
	}

	public String getCustomerNo(Integer accountNo) {
		return this.daoHelper.getAccountDao().getCustomerNo(accountNo);
	}

	public Long generateMemoRefundForExcessAmount(BmtbPaymentReceipt paymentReceipt, String userId) {

		BigDecimal refundAmount = paymentReceipt.getExcessAmount();

		BmtbPaymentReceipt memoReceipt = new BmtbPaymentReceipt();
		memoReceipt.setAmtbAccount(paymentReceipt.getAmtbAccount());
		memoReceipt.setMstbMasterTableByPaymentMode(ConfigurableConstants.getMasterTable(
				ConfigurableConstants.PAYMENT_MODE, NonConfigurableConstants.PAYMENT_MODE_MEMO));
		memoReceipt.setPaymentAmount(refundAmount);
		memoReceipt.setExcessAmount(new BigDecimal(0));
		memoReceipt.setPaymentDate(DateUtil.getCurrentDate());
		memoReceipt.setReceiptDt(DateUtil.getCurrentTimestamp());
		;
		// Receipt is not going to be reflected in the invoice
		memoReceipt.setBilledFlag(NonConfigurableConstants.BOOLEAN_NO);
		memoReceipt.setCancelBilledFlag(NonConfigurableConstants.BOOLEAN_NO);
		Long receiptNo = (Long) this.daoHelper.getGenericDao().save(memoReceipt, userId);

		paymentReceipt.setExcessAmount(new BigDecimal(0));
		paymentReceipt.setBmtbPaymentReceipt(memoReceipt);
		this.daoHelper.getGenericDao().update(paymentReceipt, userId);

		return receiptNo;
	}

	public BmtbInvoiceHeader getInvoiceByInvoiceNo(Long invoiceNo) {
		return this.daoHelper.getInvoiceDao().getInvoiceByInvoiceNo(invoiceNo);
	}
	public int checkReplaceCard(String productCardNo, BigDecimal returnCreditBalanceAmount, String addSubtract, String updatedBy) throws Exception {
	//Take currentCardNo & find NewCardNo's Prod & Update, then loop it to check newcardno got replace or not	

		logger.info("Checking CardNo > "+productCardNo+" if replaced before");
		List<PmtbProductReplacement> replacedProductsList = this.daoHelper.getProductDao().getReplacedCardProducts(productCardNo);
		
		//if got future card, update the limit also
		if(replacedProductsList.size() > 0) {
			logger.info("Records Found, checking accountNo, cardNo and ProductNo if match..");
			for(PmtbProductReplacement replace : replacedProductsList)
			{
				PmtbProduct currProd = replace.getPmtbProduct();
				PmtbProduct newCardProd = this.daoHelper.getProductDao().getNewCardProduct(replace.getNewCardNo(), currProd.getAmtbAccount().getAccountNo(), replace.getNewProductNo());
				if(null != newCardProd) //If got record, update credit balance.
				{
					//replace.newCardNo & currProduct.AccountNo & newProdNo must be same for the new Product.
					if(replace.getNewProductNo().equals(newCardProd.getProductNo())) //newProdNo @ pmtbReplace must be same as currProd ProductNo.
					{
						if(addSubtract.trim().equalsIgnoreCase("add"))
							newCardProd.setCreditBalance(newCardProd.getCreditBalance().add(returnCreditBalanceAmount));
						else if(addSubtract.trim().equalsIgnoreCase("subtract"))
							newCardProd.setCreditBalance(newCardProd.getCreditBalance().subtract(returnCreditBalanceAmount));
				
						// 20 may 2016 recovery balance check.
						newCardProd.setUpdatedBy(updatedBy + " (ReplacePaym)");
						newCardProd = doCheckProductBalanceRecovery(newCardProd);
						
						this.update(newCardProd, newCardProd.getUpdatedBy());
						logger.info("Updating Credit Limit for Card No > "+newCardProd.getCardNo());
						
						// Still we need to update payment to update the amount used in AS
						// to call API.formulateAccountId
						API.updateProductPayment(newCardProd.getCardNo().toString(), newCardProd.getCreditBalance()
								.toString(), API.formulateAccountId(newCardProd.getAmtbAccount()), newCardProd.getUpdatedBy());
						
						logger.info("Updating Credit Limit for Product AS");
						
						return checkReplaceCard(newCardProd.getCardNo(), returnCreditBalanceAmount, addSubtract, updatedBy);
					}
				}
			}
		}
		return 1;
	}
	public PmtbProduct doCheckProductBalanceRecovery(PmtbProduct product)
	{
		// 20 May 2016 - if balance more then limit, do Code Self Recovery.
		// if got temp limit , take temp, else take normal limit.
		// Do not let balance more then limit.
		BigDecimal limitToCheck = new BigDecimal("0");
		if(null != product.getTempCreditLimit())
			limitToCheck = product.getTempCreditLimit();
		else
			limitToCheck = product.getCreditLimit();
		
		if(product.getCreditBalance().compareTo(limitToCheck) > 0 ) {
			logger.info("Balance Recovery for Card : "+product.getCardNo());
			product.setUpdatedBy(product.getUpdatedBy() + " (R)");
			product.setCreditBalance(limitToCheck);
		}
		
		return product;
	}
	public AmtbAccount doCheckAccountBalanceRecovery(AmtbAccount account)
	{
		//20 May 2016 if Account balance above limit, process Balance Recovery.
		// Do not allow balance more then limit.
		
		BigDecimal limitToCheckAcct = new BigDecimal("0");
		if(null != account.getTempCreditLimit())
			limitToCheckAcct = account.getTempCreditLimit();
		else
			limitToCheckAcct = account.getCreditLimit();
		
		if(account.getCreditBalance().compareTo(limitToCheckAcct) > 0 ) {
			account.setCreditBalance(limitToCheckAcct);
			account.setUpdatedBy(account.getUpdatedBy() + " (R)");
			logger.info("Balance Recovery for Account : "+account.getAccountNo());
		}
		
		return account;
	}

	public void generateRecurringReceipt(BmtbInvoiceHeader ih, BmtbInvoiceDetail invoiceDetail, String amount, AmtbAccount account)
	{
		try {
			boolean isPrepaid = NonConfigurableConstants.getBoolean(ih.getPrepaidFlag());
			BigDecimal outstandingAmount = new BigDecimal("0");
			BmtbPaymentReceipt paymentReceipt = new BmtbPaymentReceipt();
			// flow of this will be slightly different from UI as it does not allow changing
			// of top up value
			BigDecimal appliedAmount = new BigDecimal(amount);
			// payment belongs to which account
//		if (paymentInfo.getDepartment() != null) {
//			paymentReceipt.setAmtbAccount(paymentInfo.getDepartment());
//		} else if (paymentInfo.getDivision() != null) {
//			paymentReceipt.setAmtbAccount(paymentInfo.getDivision());
//		} else {
//			paymentReceipt.setAmtbAccount(paymentInfo.getAccount());
//		}
			paymentReceipt.setAmtbAccount(account);
			// payment ref no
			paymentReceipt.setPaymentNo(null);
			paymentReceipt.setMstbMasterTableByPaymentMode(ConfigurableConstants.getMasterTable(
					ConfigurableConstants.PAYMENT_MODE, NonConfigurableConstants.PAYMENT_MODE_CREDIT_CARD));

//		if (paymentInfo.getPaymentMode().equals(NonConfigurableConstants.PAYMENT_MODE_CASH)
//				|| paymentInfo.getPaymentMode().equals(NonConfigurableConstants.PAYMENT_MODE_CHEQUE)) {
//			paymentReceipt.setPaymentNo(null);
//		} else {
//			paymentReceipt.setPaymentNo(paymentInfo.getTxnRefNo());
//		}
			// payment mode
			// paymentReceipt.setMstbMasterTableByPaymentMode(NonConfigurableConstants.PAYMENT_MODE_CHEQUE,
			// "CASH"));
			// payment amount
			paymentReceipt.setPaymentAmount(appliedAmount);
			// payment date
			long millis = System.currentTimeMillis();
			java.sql.Date date = new java.sql.Date(millis);
			paymentReceipt.setPaymentDate(date);
			// receipt date
			paymentReceipt.setReceiptDt(new Timestamp(System.currentTimeMillis()));
			// bank
//		paymentReceipt.setMstbBankMaster((MstbBankMaster) MasterSetup.getBankManager().getMaster(
//				paymentInfo.getBankNo()));
//		// branch
//		paymentReceipt.setMstbBranchMaster((MstbBranchMaster) MasterSetup.getBankManager().getDetail(
//				paymentInfo.getBranchNo()));

			// System.out.println(constantDao.getMasterTable(ConfigurableConstants.PAYMENT_MODE,
			// "GR"));
//		paymentReceipt.setMstbMasterTableByPaymentMode(ConfigurableConstants.getMasterTable(
//				ConfigurableConstants.PAYMENT_MODE, "GR"));

			// paymentReceipt.setMstbMasterTableByPaymentMode(
			// constantDao.getMasterTable(ConfigurableConstants.PAYMENT_MODE, "GR"));

//		if (paymentInfo.getPaymentMode().equals(NonConfigurableConstants.PAYMENT_MODE_CHEQUE)) {
//			// Cheque Date
//			paymentReceipt.setChequeDate(paymentInfo.getChequeDate());
//			// Cheque No
//			paymentReceipt.setChequeNo(paymentInfo.getChequeNo());
//			// Quick Cheque Deposit
//			paymentReceipt
//					.setQuickChequeDeposit(paymentInfo.isQuickChequeDeposit() == true ? NonConfigurableConstants.BOOLEAN_YN_YES
//							: NonConfigurableConstants.BOOLEAN_YN_NO);
//		}

			// Remarks
			paymentReceipt.setRemarks("PAYMENT THROUGH RED DOT");
			// bank in
//		if(paymentInfo.getBankInNo()!=null)
//			paymentReceipt.setFmtbBankCode((FmtbBankCode) genericDao.get(FmtbBankCode.class,
//					paymentInfo.getBankInNo()));
			// excess amount - deduct one by one by the payment details
			paymentReceipt.setExcessAmount(paymentReceipt.getPaymentAmount());
			// Billed Flag - default to N
			paymentReceipt.setBilledFlag(NonConfigurableConstants.BOOLEAN_YN_NO);
			// Cancel Billed Flag
			paymentReceipt.setCancelBilledFlag(NonConfigurableConstants.BOOLEAN_YN_NO);

			Serializable receiptNo = this.daoHelper.getGenericDao().save(paymentReceipt, "RED DOT");

			// creates receipt detail for partial payment
			BmtbPaymentReceiptDetail paymentReceiptDetail = new BmtbPaymentReceiptDetail();
			paymentReceiptDetail.setAppliedAmount(appliedAmount);
			paymentReceiptDetail.setBilledFlag(NonConfigurableConstants.BOOLEAN_NO);
			paymentReceiptDetail.setCancelBilledFlag(NonConfigurableConstants.BOOLEAN_NO);
			paymentReceiptDetail.setBmtbPaymentReceipt(paymentReceipt);
			paymentReceiptDetail.setBmtbInvoiceHeader(ih);
			paymentReceiptDetail.setBmtbInvoiceDetail(invoiceDetail);
			this.daoHelper.getGenericDao().save(paymentReceiptDetail, "RED DOT");

			// update invoiceDetail
			invoiceDetail.setOutstandingAmount(invoiceDetail.getOutstandingAmount().subtract(appliedAmount));
			this.daoHelper.getGenericDao().update(invoiceDetail, "RED DOT");

			// reduce outstanding amount - inclusive discount amount (so must add back
			// discount amount
			// to prevent negative amount)
			ih.setOutstandingAmount(ih.getOutstandingAmount().subtract(appliedAmount));
			// genericDao.update(ih);
			outstandingAmount = ih.getOutstandingAmount();

			// subtract excess amount
			paymentReceipt.setExcessAmount(
					paymentReceipt.getExcessAmount().subtract(paymentReceiptDetail.getAppliedAmount()));
			this.daoHelper.getGenericDao().save(paymentReceipt);

			// Increase credit balance

			BigDecimal deductedCreditBalance = this.increaseCreditBalance(invoiceDetail, appliedAmount, "RED DOT",
					isPrepaid);
			paymentReceiptDetail.setDeductedCreditBalance(deductedCreditBalance);
			this.daoHelper.getGenericDao().update(paymentReceiptDetail);
			// save the updated excess amount
			this.daoHelper.getGenericDao().update(paymentReceipt);

			// Partial Payment has managed to pay the rest for this invoice.
			if (this.daoHelper.getInvoiceDao().isInvoiceStillOutstanding(ih.getInvoiceHeaderNo()) == false) {
				ih.setInvoiceStatus(NonConfigurableConstants.INVOICE_STATUS_CLOSED);
				ih.setRecurringDoneFlag("Y");
			}

			this.daoHelper.getGenericDao().update(ih, "RED DOTs");

			if (outstandingAmount.compareTo(new BigDecimal("0")) >= 0) {
				if (isPrepaid) {

					long invoiceHeaderNo = ih.getInvoiceHeaderNo();
					PmtbPrepaidReq req = this.daoHelper.getPrepaidDao().getPrepaidRequestWithInvoiceHeader(invoiceHeaderNo);
					if (req != null) {
						String status = req.getStatus();
						logger.info("Prepaid request status: " + status);
						if (NonConfigurableConstants.PREPAID_REQUEST_STATUS_PENDING_PAYMENT.equals(status)) {
							if (req instanceof PmtbIssuanceReq) {
								PmtbIssuanceReq iReq = (PmtbIssuanceReq) req;
								this.businessHelper.getPrepaidBusiness().issueProduct(iReq, true, "RED DOT");
							} else if (req instanceof PmtbTopUpReq) {
								PmtbTopUpReq tReq = (PmtbTopUpReq) req;
								this.businessHelper.getPrepaidBusiness().topUp(tReq, true, "RED DOT");
							}
						}
					}
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error");
		}
	}
}