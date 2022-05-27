package com.cdgtaxi.ibs.common.dao;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Conjunction;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;

import com.cdgtaxi.ibs.common.ConfigurableConstants;
import com.cdgtaxi.ibs.common.NonConfigurableConstants;
import com.cdgtaxi.ibs.common.model.AmtbAccount;
import com.cdgtaxi.ibs.common.model.BmtbPaymentReceipt;
import com.cdgtaxi.ibs.common.model.forms.SearchPaymentReceiptForm;
import com.cdgtaxi.ibs.util.DateUtil;


public class PaymentReceiptDaoHibernate extends GenericDaoHibernate implements PaymentReceiptDao{

	public BmtbPaymentReceipt getPaymentReceipt(Long receiptNo){
		DetachedCriteria criteria = DetachedCriteria.forClass(BmtbPaymentReceipt.class);
		DetachedCriteria lowestLevelAccount = criteria.createCriteria("amtbAccount", DetachedCriteria.INNER_JOIN);
		DetachedCriteria midLevelAccount = lowestLevelAccount.createCriteria("amtbAccount", DetachedCriteria.LEFT_JOIN);
		DetachedCriteria topLevelAccount = midLevelAccount.createCriteria("amtbAccount", DetachedCriteria.LEFT_JOIN);
		criteria.createCriteria("mstbMasterTableByPaymentMode", DetachedCriteria.INNER_JOIN);
		criteria.createCriteria("mstbMasterTableByCancellationReason", DetachedCriteria.LEFT_JOIN);
		criteria.createCriteria("mstbBankMaster", DetachedCriteria.LEFT_JOIN);
		criteria.createCriteria("mstbBranchMaster", DetachedCriteria.LEFT_JOIN);
		criteria.createCriteria("fmtbBankCode", DetachedCriteria.LEFT_JOIN);
		criteria.createCriteria("bmtbPaymentReceipt", DetachedCriteria.LEFT_JOIN);
		criteria.createCriteria("bmtbInvoiceDepositTxns", DetachedCriteria.LEFT_JOIN);
		DetachedCriteria paymentDetailCriteria = criteria.createCriteria("bmtbPaymentReceiptDetails", DetachedCriteria.LEFT_JOIN);
		DetachedCriteria invoiceHeaderCriteria = paymentDetailCriteria.createCriteria("bmtbInvoiceHeader", DetachedCriteria.LEFT_JOIN);
		invoiceHeaderCriteria.createCriteria("amtbAccountByDebtTo", DetachedCriteria.LEFT_JOIN);
		DetachedCriteria invoiceDetailCriteria = paymentDetailCriteria.createCriteria("bmtbInvoiceDetail", DetachedCriteria.LEFT_JOIN);
		invoiceDetailCriteria.createCriteria("pmtbProduct", DetachedCriteria.LEFT_JOIN);
		DetachedCriteria invoiceDetailLowestLevelAccount = invoiceDetailCriteria.createCriteria("amtbAccount", DetachedCriteria.LEFT_JOIN);
		DetachedCriteria invoiceDetailMidLevelAccount = invoiceDetailLowestLevelAccount.createCriteria("amtbAccount", DetachedCriteria.LEFT_JOIN);
		DetachedCriteria invoiceDetailTopLevelAccount = invoiceDetailMidLevelAccount.createCriteria("amtbAccount", DetachedCriteria.LEFT_JOIN);

		criteria.add(Restrictions.idEq(receiptNo));
		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		List results = this.findAllByCriteria(criteria);
		if(results.isEmpty()) {
			return null;
		} else {
			return (BmtbPaymentReceipt)results.get(0);
		}
	}

	public List<BmtbPaymentReceipt> getPaymentReceipt(SearchPaymentReceiptForm form){
		DetachedCriteria paymentReceiptCriteria = DetachedCriteria.forClass(BmtbPaymentReceipt.class);
		DetachedCriteria accountCriteria = paymentReceiptCriteria.createCriteria("amtbAccount", "account", DetachedCriteria.INNER_JOIN);
		DetachedCriteria parentAccountCriteria = accountCriteria.createCriteria("amtbAccount", "parentAccount", DetachedCriteria.LEFT_JOIN);
		DetachedCriteria grandParentAccountCriteria = parentAccountCriteria.createCriteria("amtbAccount", "grandParentAccount", DetachedCriteria.LEFT_JOIN);
		DetachedCriteria paymentModeCriteria = paymentReceiptCriteria.createCriteria("mstbMasterTableByPaymentMode", "pymtMode", DetachedCriteria.INNER_JOIN);

		//search by account
		if(form.getDepartment()!=null) {
			accountCriteria.add(Restrictions.idEq(form.getDepartment().getAccountNo()));
		} else if(form.getDivision()!=null) {
			accountCriteria.add(Restrictions.idEq(form.getDivision().getAccountNo()));
		} else if(form.getAccount()!=null) {
			paymentReceiptCriteria.add(
					Restrictions.or(Restrictions.eq("account.accountNo", form.getAccount().getAccountNo()), 
							Restrictions.or(Restrictions.eq("parentAccount.accountNo", form.getAccount().getAccountNo()), 
									Restrictions.eq("grandParentAccount.accountNo", form.getAccount().getAccountNo()))
					)
			);
			//accountCriteria.add(Restrictions.idEq(form.getAccount().getAccountNo()));
		} else{
			//search by acct no or acct name if no account is selected
			if(form.getCustomerNo()!=null && form.getCustomerNo().length()>0){
				accountCriteria.add(Restrictions.eq("custNo", form.getCustomerNo()));
			}
			if(form.getAccountName()!=null && form.getAccountName().length()>=3) {
				
				Disjunction dj = Restrictions.disjunction();
				dj.add(Restrictions.and(
						Restrictions.isNotNull("account.custNo"),
						Restrictions.ilike("account.accountName", form.getAccountName(), MatchMode.ANYWHERE)
						));
				dj.add(Restrictions.and(
						Restrictions.isNotNull("parentAccount.custNo"),
						Restrictions.ilike("parentAccount.accountName", form.getAccountName(), MatchMode.ANYWHERE)
						));
				dj.add(Restrictions.and(
						Restrictions.isNotNull("grandParentAccount.custNo"),
						Restrictions.ilike("grandParentAccount.accountName", form.getAccountName(), MatchMode.ANYWHERE)
						));
				
				accountCriteria.add(dj);
			}
		}

		//search by receipt no
		if(form.getReceiptNo()!=null) {
			paymentReceiptCriteria.add(Restrictions.idEq(form.getReceiptNo()));
		}
		//search by payment mode
		if(form.getPaymentMode()!=null) {
			paymentModeCriteria.add(Restrictions.idEq(form.getPaymentMode().getMasterNo()));
		}
		//search by date from
		if(form.getReceiptDateFrom()!=null){
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(DateUtil.convertSqlDateToUtilDate(form.getReceiptDateFrom()));
			Timestamp receiptDtFrom = DateUtil.convertDateToTimestamp(DateUtil.convertTo0000Hours(calendar));
			paymentReceiptCriteria.add(Restrictions.ge("receiptDt", receiptDtFrom));
		}
		//search by date to
		if(form.getReceiptDateTo()!=null){
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(DateUtil.convertSqlDateToUtilDate(form.getReceiptDateTo()));
			Timestamp receiptDtTo = DateUtil.convertDateToTimestamp(DateUtil.convertTo2359Hours(calendar));
			paymentReceiptCriteria.add(Restrictions.le("receiptDt", receiptDtTo));
		}
		//search by receipt status
		if(form.getReceiptStatus()!=null){
			String receiptStatus = form.getReceiptStatus();
			if(receiptStatus.equals(NonConfigurableConstants.RECEIPT_STATUS_CANCELLED)) {
				paymentReceiptCriteria.add(Restrictions.isNotNull("cancelDt"));
			} else if(receiptStatus.equals(NonConfigurableConstants.RECEIPT_STATUS_CLOSED)) {
				
				Conjunction partA = Restrictions.conjunction();
				partA.add(Restrictions.eq("pymtMode.masterType", ConfigurableConstants.PAYMENT_MODE));
				partA.add(Restrictions.ne("pymtMode.masterCode", NonConfigurableConstants.PAYMENT_MODE_MEMO));
				partA.add(Restrictions.eq("excessAmount", new BigDecimal(0)));
				partA.add(Restrictions.isNull("cancelDt"));
				
				Conjunction partB = Restrictions.conjunction();
				partB.add(Restrictions.eq("pymtMode.masterType", ConfigurableConstants.PAYMENT_MODE));
				partB.add(Restrictions.eq("pymtMode.masterCode", NonConfigurableConstants.PAYMENT_MODE_MEMO));
				partB.add(Restrictions.isNotNull("chequeNo"));
				
				Disjunction disjunction = Restrictions.disjunction();
				disjunction.add(partA);
				disjunction.add(partB);
				paymentReceiptCriteria.add(disjunction);
				
			} else if(receiptStatus.equals(NonConfigurableConstants.RECEIPT_STATUS_EXCESS)) {
				paymentReceiptCriteria.add(Restrictions.gt("excessAmount", new BigDecimal(0)));
				paymentReceiptCriteria.add(Restrictions.isNull("cancelDt"));
			}
			else if(receiptStatus.equals(NonConfigurableConstants.RECEIPT_STATUS_PENDING)){
				paymentModeCriteria.add(Restrictions.eq("masterType", ConfigurableConstants.PAYMENT_MODE));
				paymentModeCriteria.add(Restrictions.eq("masterCode", NonConfigurableConstants.PAYMENT_MODE_MEMO));
				paymentReceiptCriteria.add(Restrictions.isNull("chequeNo"));
			}
		}
		//search by cheque no
		if(form.getChequeNo()!=null && form.getChequeNo().length()>0){
			paymentReceiptCriteria.add(Restrictions.ilike("chequeNo", form.getChequeNo(), MatchMode.ANYWHERE));
		}
		//search by txn ref no
		if(form.getTransactionRefNo()!=null && form.getTransactionRefNo().length()>0){
			paymentReceiptCriteria.add(Restrictions.ilike("paymentNo", form.getTransactionRefNo(), MatchMode.ANYWHERE));
		}

		return this.findDefaultMaxResultByCriteria(paymentReceiptCriteria);
	}
	@SuppressWarnings("unchecked")
	public void updateReceiptToParent(Integer accountNo, String userId){
		DetachedCriteria paymentReceiptCriteria = DetachedCriteria.forClass(BmtbPaymentReceipt.class);
		paymentReceiptCriteria.createCriteria("amtbAccount", DetachedCriteria.LEFT_JOIN)
		.add(Restrictions.eq("accountNo", accountNo));
		paymentReceiptCriteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		List<BmtbPaymentReceipt> receipts = this.findAllByCriteria(paymentReceiptCriteria);
		DetachedCriteria acctCriteria = DetachedCriteria.forClass(AmtbAccount.class);
		acctCriteria.add(Restrictions.eq("accountNo", accountNo));
		acctCriteria.createCriteria("amtbAccount", DetachedCriteria.LEFT_JOIN);
		acctCriteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		List<AmtbAccount> parentAccts = this.findAllByCriteria(acctCriteria);
		if(!parentAccts.isEmpty()){
			AmtbAccount parent = parentAccts.get(0).getAmtbAccount();
			while(parent.getInvoiceFormat()==null){
				acctCriteria = DetachedCriteria.forClass(AmtbAccount.class);
				acctCriteria.add(Restrictions.eq("accountNo", parent.getAccountNo()));
				acctCriteria.createCriteria("amtbAccount", DetachedCriteria.LEFT_JOIN);
				acctCriteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
				parentAccts = this.findAllByCriteria(acctCriteria);
				if(!parentAccts.isEmpty()){
					parent = parentAccts.get(0).getAmtbAccount();
				}else{
					break;
				}
			}
			for(BmtbPaymentReceipt receipt : receipts){
				receipt.setAmtbAccount(parent);
				this.update(receipt, userId);
			}
		}
	}

	@SuppressWarnings("unchecked")
	public List<BmtbPaymentReceipt> getPaymentReceiptsByInvoice(Long invoiceNo) {
		DetachedCriteria receiptCriteria = DetachedCriteria.forClass(BmtbPaymentReceipt.class);
		DetachedCriteria receiptDetailsCriteria = receiptCriteria.createCriteria("bmtbPaymentReceiptDetails", DetachedCriteria.INNER_JOIN);
		DetachedCriteria invoiceCriteria = receiptDetailsCriteria.createCriteria("bmtbInvoiceHeader", DetachedCriteria.INNER_JOIN);

		DetachedCriteria paymentModeCriteria = receiptCriteria.createCriteria("mstbMasterTableByPaymentMode", DetachedCriteria.LEFT_JOIN);
		DetachedCriteria cancellationReasonCriteria = receiptCriteria.createCriteria("mstbMasterTableByCancellationReason", DetachedCriteria.LEFT_JOIN);

		invoiceCriteria.add(Restrictions.eq("invoiceNo", invoiceNo));

		receiptCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);

		// Need to do this so as to retrieve bmtbPaymentReceiptDetails eagerly
		List<BmtbPaymentReceipt> list = this.findAllByCriteria(receiptCriteria);
		for (BmtbPaymentReceipt receipt : list) {
			receipt.getBmtbPaymentReceiptDetails().size();
		}

		return list;
	}
}