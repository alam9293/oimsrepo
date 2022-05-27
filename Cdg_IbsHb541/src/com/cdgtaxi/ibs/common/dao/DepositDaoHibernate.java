package com.cdgtaxi.ibs.common.dao;

import java.math.BigDecimal;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import com.cdgtaxi.ibs.common.NonConfigurableConstants;
import com.cdgtaxi.ibs.common.model.AmtbAccount;
import com.cdgtaxi.ibs.common.model.BmtbInvoiceDepositTxn;

public class DepositDaoHibernate extends GenericDaoHibernate implements DepositDao{
	public BigDecimal getTotalRequestTxnAmount(AmtbAccount account){
		DetachedCriteria depositCriteria = DetachedCriteria.forClass(BmtbInvoiceDepositTxn.class);
		DetachedCriteria invoiceHeaderCriteria = depositCriteria.createCriteria("bmtbInvoiceHeader", Criteria.INNER_JOIN);
		
		depositCriteria.add(Restrictions.eq("amtbAccount", account));
		depositCriteria.add(Restrictions.eq("txnType", NonConfigurableConstants.DEPOSIT_TXN_TYPE_REQUEST));
		invoiceHeaderCriteria.add(Restrictions.eq("invoiceStatus", NonConfigurableConstants.INVOICE_STATUS_CLOSED));
		
		depositCriteria.setProjection(Projections.sum("amount"));
		
		List results = this.findAllByCriteria(depositCriteria);
		BigDecimal totalAmount =  (BigDecimal)results.get(0);
		if(totalAmount==null) return new BigDecimal(0);
		else return totalAmount;
	}
	
	public BigDecimal getTotalRefundTxnAmount(AmtbAccount account){
		DetachedCriteria depositCriteria = DetachedCriteria.forClass(BmtbInvoiceDepositTxn.class);
		DetachedCriteria noteCriteria = depositCriteria.createCriteria("bmtbNote", "note", Criteria.LEFT_JOIN);
		DetachedCriteria paymentReceiptCriteria = depositCriteria.createCriteria("bmtbPaymentReceipt", "paymentReceipt", Criteria.LEFT_JOIN);

		if(account.getCustNo() != null)
		{
			DetachedCriteria acctCriteria = depositCriteria.createCriteria("amtbAccount", org.hibernate.sql.JoinType.LEFT_OUTER_JOIN);
			DetachedCriteria parentAcctCriteria = acctCriteria.createCriteria("amtbAccount", org.hibernate.sql.JoinType.LEFT_OUTER_JOIN);
			DetachedCriteria grandparentAcctCriteria = parentAcctCriteria.createCriteria("amtbAccount", org.hibernate.sql.JoinType.LEFT_OUTER_JOIN);
			
			acctCriteria.add(Restrictions.sqlRestriction("( amtbaccoun3_.account_No like '%"+account.getAccountNo()+"%' or amtbaccoun4_.account_no like '%"+account.getAccountNo()+"%' or amtbaccoun5_.account_no like '%"+account.getAccountNo()+"%' )"));
		}
		else
			depositCriteria.add(Restrictions.eq("amtbAccount", account));
		
		depositCriteria.add(Restrictions.eq("txnType", NonConfigurableConstants.DEPOSIT_TXN_TYPE_REFUND));
		depositCriteria.add(Restrictions.or(
				Restrictions.and(
						Restrictions.eq("note.noteType", NonConfigurableConstants.NOTE_TYPE_CREDIT), 
						Restrictions.ne("note.status", NonConfigurableConstants.NOTE_STATUS_CANCELLED))
				, 
				Restrictions.and(
						Restrictions.isNotNull("paymentReceipt.paymentReceiptNo"), 
						Restrictions.isNull("paymentReceipt.cancelDt"))
				)
		);
		
		depositCriteria.setProjection(Projections.sum("amount"));
		
		List results = this.findAllByCriteria(depositCriteria);
		BigDecimal totalAmount =  (BigDecimal)results.get(0);
		if(totalAmount==null) return new BigDecimal(0);
		else return totalAmount;
	}
}