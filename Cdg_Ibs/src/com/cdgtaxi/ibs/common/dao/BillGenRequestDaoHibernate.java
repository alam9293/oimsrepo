package com.cdgtaxi.ibs.common.dao;

import java.sql.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Conjunction;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.cdgtaxi.ibs.common.NonConfigurableConstants;
import com.cdgtaxi.ibs.common.model.AmtbAccount;
import com.cdgtaxi.ibs.common.model.AmtbAcctBillCycle;
import com.cdgtaxi.ibs.common.model.BmtbBillGenReq;
import com.cdgtaxi.ibs.common.model.BmtbBillGenSetup;
import com.cdgtaxi.ibs.common.model.BmtbInvoiceHeader;
import com.cdgtaxi.ibs.util.DateUtil;

public class BillGenRequestDaoHibernate extends GenericDaoHibernate implements BillGenRequestDao{
	public boolean checkRequestExist(List<BmtbBillGenSetup> billGenSetups, Date requestDate, Integer entityNo, Integer requestNo){
		DetachedCriteria billGenRequestCriteria = DetachedCriteria.forClass(BmtbBillGenReq.class);
		DetachedCriteria entityCriteria = billGenRequestCriteria.createCriteria("fmtbEntityMaster", DetachedCriteria.LEFT_JOIN);
		billGenRequestCriteria.add(Restrictions.in("bmtbBillGenSetupBySetupNo", billGenSetups));
		
		//where request date = ?
		billGenRequestCriteria.add(Restrictions.eq("requestDate", requestDate));
		//and status in (?)
		billGenRequestCriteria.add(Restrictions.in("status", new String[]{NonConfigurableConstants.BILL_GEN_REQUEST_STATUS_PENDING,
				NonConfigurableConstants.BILL_GEN_REQUEST_STATUS_IN_PROGRESS, 
				NonConfigurableConstants.BILL_GEN_REQUEST_STATUS_COMPLETED,
				NonConfigurableConstants.BILL_GEN_REQUEST_STATUS_REGENERATED,
				NonConfigurableConstants.BILL_GEN_REQUEST_STATUS_ERROR}));
		//and entity = ?
		if(entityNo!=null)
			entityCriteria.add(Restrictions.idEq(entityNo));
		if(requestNo!=null)
			billGenRequestCriteria.add(Restrictions.ne("reqNo", requestNo));
		
		List results = this.findMaxResultByCriteria(billGenRequestCriteria, 1);
		if(results.isEmpty()) return false;
		else return true;
	}
	
	public boolean checkRequestExist(AmtbAccount topLevelAccount, Date requestDate) throws Exception{
		
		String billingCycle = this.getLatestBillGenCycle(topLevelAccount);
		
		DetachedCriteria billGenRequestCriteria = DetachedCriteria.forClass(BmtbBillGenReq.class);
		billGenRequestCriteria.createCriteria("amtbAccounts", "accounts", DetachedCriteria.INNER_JOIN);
		billGenRequestCriteria.createCriteria("bmtbBillGenSetupBySetupNo", "setup", DetachedCriteria.INNER_JOIN);
		
		//where request date = ?
		billGenRequestCriteria.add(Restrictions.eq("requestDate", requestDate));
		//and status in (?)
		billGenRequestCriteria.add(Restrictions.in("status", new String[]{NonConfigurableConstants.BILL_GEN_REQUEST_STATUS_PENDING,
				NonConfigurableConstants.BILL_GEN_REQUEST_STATUS_IN_PROGRESS, 
				NonConfigurableConstants.BILL_GEN_REQUEST_STATUS_COMPLETED,
				NonConfigurableConstants.BILL_GEN_REQUEST_STATUS_REGENERATED}));
		
		//or setup = M or setup = W1 or setup = W2
		Disjunction disjunction = Restrictions.disjunction();
		if(billingCycle.equals(NonConfigurableConstants.BILLING_CYCLES_MONTHLY))
			disjunction.add(Restrictions.eq("setup.setupNo", NonConfigurableConstants.BILL_GEN_SETUP_MONTHLY));
		else if(billingCycle.equals(NonConfigurableConstants.BILLING_CYCLES_BIWEEKLY)){
			disjunction.add(Restrictions.eq("setup.setupNo", NonConfigurableConstants.BILL_GEN_SETUP_BIWEEKLY_1));
			disjunction.add(Restrictions.eq("setup.setupNo", NonConfigurableConstants.BILL_GEN_SETUP_BIWEEKLY_2));
		}
		
		//or (setup = A and setup.accts = ?)
		Conjunction conjunction = Restrictions.conjunction();
		conjunction.add(Restrictions.eq("setup.setupNo", NonConfigurableConstants.BILL_GEN_SETUP_AD_HOC));
		conjunction.add(Restrictions.eq("accounts.accountNo", topLevelAccount.getAccountNo()));
		disjunction.add(conjunction);
		billGenRequestCriteria.add(disjunction);
		
		List results = this.findMaxResultByCriteria(billGenRequestCriteria, 1);
		if(results.isEmpty()) return false;
		else return true;
	}
	
	private String getLatestBillGenCycle(AmtbAccount topLevelAccount) throws Exception{
		DetachedCriteria criteria = DetachedCriteria.forClass(AmtbAcctBillCycle.class);
		criteria.add(Restrictions.eq("amtbAccount", topLevelAccount));
		criteria.add(Restrictions.le("effectiveDate", DateUtil.getCurrentDate()));
		criteria.addOrder(Order.desc("effectiveDate"));
		List results = this.findMaxResultByCriteria(criteria, 1);
		if(results.isEmpty()) throw new Exception("No billing cycle found for account "+topLevelAccount.getAccountNo());
		else return ((AmtbAcctBillCycle)results.get(0)).getBillingCycle();
	}
	
	public BmtbBillGenReq get(Integer billGenRequestNo){
		DetachedCriteria billGenRequestCriteria = DetachedCriteria.forClass(BmtbBillGenReq.class);
		billGenRequestCriteria.createCriteria("bmtbBillGenSetupBySetupNo", DetachedCriteria.INNER_JOIN);
		billGenRequestCriteria.createCriteria("fmtbEntityMaster", DetachedCriteria.LEFT_JOIN);
		billGenRequestCriteria.add(Restrictions.idEq(billGenRequestNo));
		billGenRequestCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		
		List results = this.findMaxResultByCriteria(billGenRequestCriteria, 1);
		if(results.isEmpty()) return null;
		else return (BmtbBillGenReq)results.get(0);
	}
	
	public List<BmtbBillGenReq> get(Integer requestNo, String status, Integer setupNo, Date requestDateFrom, Date requestDateTo, Integer entityNo){
		return get(requestNo, status, setupNo, requestDateFrom, requestDateTo, entityNo, null);
	}
	public List<BmtbBillGenReq> get(Integer requestNo, String status, Integer setupNo, Date requestDateFrom, Date requestDateTo, Integer entityNo, String backward){
		DetachedCriteria billGenRequestCriteria = DetachedCriteria.forClass(BmtbBillGenReq.class);
		DetachedCriteria billGenSetupCriteria = billGenRequestCriteria.createCriteria("bmtbBillGenSetupBySetupNo", DetachedCriteria.INNER_JOIN);
		DetachedCriteria entityCriteria = billGenRequestCriteria.createCriteria("fmtbEntityMaster", "entity1", DetachedCriteria.LEFT_JOIN);
		DetachedCriteria acctCriteria = billGenRequestCriteria.createCriteria("amtbAccount", DetachedCriteria.LEFT_JOIN);
		DetachedCriteria fmtbArContCodeCriteria = acctCriteria.createCriteria("fmtbArContCodeMaster", DetachedCriteria.LEFT_JOIN);
		DetachedCriteria acctEntityCriteria = fmtbArContCodeCriteria.createCriteria("fmtbEntityMaster", "entity2", DetachedCriteria.LEFT_JOIN);
		
		DetachedCriteria temp1 = billGenRequestCriteria.createCriteria("bmtbBillGenReq", DetachedCriteria.LEFT_JOIN);
		DetachedCriteria temp1EntityCriteria = temp1.createCriteria("fmtbEntityMaster", "entity3", DetachedCriteria.LEFT_JOIN);
		DetachedCriteria temp1AcctCriteria = temp1.createCriteria("amtbAccount", DetachedCriteria.LEFT_JOIN);
		DetachedCriteria temp1FmtbArContCodeCriteria = temp1AcctCriteria.createCriteria("fmtbArContCodeMaster", DetachedCriteria.LEFT_JOIN);
		DetachedCriteria temp1AcctEntityCriteria = temp1FmtbArContCodeCriteria.createCriteria("fmtbEntityMaster",  "entity4", DetachedCriteria.LEFT_JOIN);
		
		DetachedCriteria temp2 = temp1.createCriteria("bmtbBillGenReq", DetachedCriteria.LEFT_JOIN);
		DetachedCriteria temp2EntityCriteria = temp2.createCriteria("fmtbEntityMaster",  "entity5", DetachedCriteria.LEFT_JOIN);
		DetachedCriteria temp2AcctCriteria = temp2.createCriteria("amtbAccount", DetachedCriteria.LEFT_JOIN);
		DetachedCriteria temp2FmtbArContCodeCriteria = temp2AcctCriteria.createCriteria("fmtbArContCodeMaster", DetachedCriteria.LEFT_JOIN);
		DetachedCriteria temp2AcctEntityCriteria = temp2FmtbArContCodeCriteria.createCriteria("fmtbEntityMaster", "entity6", DetachedCriteria.LEFT_JOIN);
		
		DetachedCriteria temp3 = temp2.createCriteria("bmtbBillGenReq", DetachedCriteria.LEFT_JOIN);
		DetachedCriteria temp3EntityCriteria = temp3.createCriteria("fmtbEntityMaster",  "entity7", DetachedCriteria.LEFT_JOIN);
		DetachedCriteria temp3AcctCriteria = temp3.createCriteria("amtbAccount", DetachedCriteria.LEFT_JOIN);
		DetachedCriteria temp3FmtbArContCodeCriteria = temp3AcctCriteria.createCriteria("fmtbArContCodeMaster", DetachedCriteria.LEFT_JOIN);
		DetachedCriteria temp3AcctEntityCriteria = temp3FmtbArContCodeCriteria.createCriteria("fmtbEntityMaster", "entity8", DetachedCriteria.LEFT_JOIN);
		
		DetachedCriteria temp4 = temp3.createCriteria("bmtbBillGenReq", DetachedCriteria.LEFT_JOIN);
		DetachedCriteria temp4EntityCriteria = temp4.createCriteria("fmtbEntityMaster",  "entity9", DetachedCriteria.LEFT_JOIN);
		DetachedCriteria temp4AcctCriteria = temp4.createCriteria("amtbAccount", DetachedCriteria.LEFT_JOIN);
		DetachedCriteria temp4FmtbArContCodeCriteria = temp4AcctCriteria.createCriteria("fmtbArContCodeMaster", DetachedCriteria.LEFT_JOIN);
		DetachedCriteria temp4AcctEntityCriteria = temp4FmtbArContCodeCriteria.createCriteria("fmtbEntityMaster", "entity10", DetachedCriteria.LEFT_JOIN);
		
		DetachedCriteria temp5 = temp4.createCriteria("bmtbBillGenReq", DetachedCriteria.LEFT_JOIN);
		DetachedCriteria temp5EntityCriteria = temp5.createCriteria("fmtbEntityMaster",  "entity11", DetachedCriteria.LEFT_JOIN);
		DetachedCriteria temp5AcctCriteria = temp5.createCriteria("amtbAccount", DetachedCriteria.LEFT_JOIN);
		DetachedCriteria temp5FmtbArContCodeCriteria = temp5AcctCriteria.createCriteria("fmtbArContCodeMaster", DetachedCriteria.LEFT_JOIN);
		DetachedCriteria temp5AcctEntityCriteria = temp5FmtbArContCodeCriteria.createCriteria("fmtbEntityMaster",  "entity12", DetachedCriteria.LEFT_JOIN);
		
		billGenRequestCriteria.createCriteria("bmtbBillGenSetupByRegenSetupNo", DetachedCriteria.LEFT_JOIN);
		
		billGenRequestCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		
		
		if(requestNo!=null) billGenRequestCriteria.add(Restrictions.idEq(requestNo));
		if(status!=null && !status.equals("")) {
			
			if(status.trim().equals("RC")) {
				billGenRequestCriteria.add(Restrictions.or(Restrictions.eq("status", "R"), Restrictions.eq("status", "C")));
				acctCriteria.add(Restrictions.or(Restrictions.isNull("accountNo"),
						Restrictions.and(Restrictions.eq("outsourcePrintingFlag", "Y"), 
								Restrictions.or(Restrictions.ne("einvoiceFlag", "Y"),Restrictions.isNull("einvoiceFlag"))
								)));
			}
			else
				billGenRequestCriteria.add(Restrictions.eq("status", status));
		}
		
		if(backward != null) billGenSetupCriteria.add(Restrictions.ne("setupNo", 5));
		
		if(setupNo!=null) billGenSetupCriteria.add(Restrictions.idEq(setupNo));
		if(requestDateFrom!=null) billGenRequestCriteria.add(Restrictions.ge("requestDate", requestDateFrom));
		if(requestDateTo!=null) billGenRequestCriteria.add(Restrictions.le("requestDate", requestDateTo));
		if(entityNo!=null) {
			
			Disjunction disjunction = Restrictions.disjunction();
			disjunction.add(Restrictions.eq("entity1.entityNo", entityNo));
			disjunction.add(Restrictions.eq("entity2.entityNo", entityNo));
			disjunction.add(Restrictions.eq("entity3.entityNo", entityNo));
			disjunction.add(Restrictions.eq("entity4.entityNo", entityNo));
			disjunction.add(Restrictions.eq("entity5.entityNo", entityNo));
			disjunction.add(Restrictions.eq("entity6.entityNo", entityNo));
			disjunction.add(Restrictions.eq("entity7.entityNo", entityNo));
			disjunction.add(Restrictions.eq("entity8.entityNo", entityNo));
			disjunction.add(Restrictions.eq("entity9.entityNo", entityNo));
			disjunction.add(Restrictions.eq("entity10.entityNo", entityNo));
			disjunction.add(Restrictions.eq("entity11.entityNo", entityNo));
			disjunction.add(Restrictions.eq("entity12.entityNo", entityNo));
			billGenRequestCriteria.add(disjunction);
		}
		if(backward != null) billGenRequestCriteria.addOrder(Order.desc("reqNo"));
		return this.findDefaultMaxResultByCriteria(billGenRequestCriteria);
	}
	
	

	public List<BmtbInvoiceHeader> getAllBmtbInvoicesForRecurring(AmtbAccount acct)
	{
		DetachedCriteria invoiceHeaderCriteria = DetachedCriteria.forClass(BmtbInvoiceHeader.class);
		DetachedCriteria invoiceSummaryCrit = invoiceHeaderCriteria.createCriteria("bmtbInvoiceSummaries",
				DetachedCriteria.LEFT_JOIN);
		DetachedCriteria invoiceDetailCrit = invoiceSummaryCrit.createCriteria("bmtbInvoiceDetails",
				DetachedCriteria.LEFT_JOIN);
//		invoiceDetailCrit.createCriteria("bmtbInvoiceTxns", DetachedCriteria.LEFT_JOIN);
//		invoiceHeaderCriteria.createCriteria("bmtbBillGenReq", DetachedCriteria.LEFT_JOIN);
		
		// joining first lvl 1
		DetachedCriteria accountCriteria = invoiceHeaderCriteria.createCriteria("amtbAccountByDebtTo",
				"first", DetachedCriteria.INNER_JOIN);

		// joining second lvl 2
		DetachedCriteria account2ndLvlCriteria = accountCriteria.createCriteria("amtbAccount", "second",
				Criteria.LEFT_JOIN);

		// joining third lvl 3
		DetachedCriteria account3ndLvlCriteria = account2ndLvlCriteria.createCriteria("amtbAccount", "third",
				DetachedCriteria.LEFT_JOIN);

		
		invoiceHeaderCriteria.add(Restrictions.eq("recurringDoneFlag", "N"));
		invoiceHeaderCriteria.add(Restrictions.eq("first.accountNo", acct.getAccountNo()));
	
		invoiceHeaderCriteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		return this.findAllByCriteria(invoiceHeaderCriteria);
	}
	public List<BmtbInvoiceHeader> getAllBmtbInvoicesForRecurringByInvoiceNo(Long invoiceNoFrom, Long invoiceNoTo)
	{
		DetachedCriteria invoiceHeaderCriteria = DetachedCriteria.forClass(BmtbInvoiceHeader.class);
		DetachedCriteria invoiceSummaryCrit = invoiceHeaderCriteria.createCriteria("bmtbInvoiceSummaries",
				DetachedCriteria.LEFT_JOIN);
		DetachedCriteria invoiceDetailCrit = invoiceSummaryCrit.createCriteria("bmtbInvoiceDetails",
				DetachedCriteria.LEFT_JOIN);
		
		// joining first lvl 1
		DetachedCriteria accountCriteria = invoiceHeaderCriteria.createCriteria("amtbAccountByDebtTo",
				"first", DetachedCriteria.INNER_JOIN);

		// joining second lvl 2
		DetachedCriteria account2ndLvlCriteria = accountCriteria.createCriteria("amtbAccount", "second",
				Criteria.LEFT_JOIN);

		// joining third lvl 3
		DetachedCriteria account3ndLvlCriteria = account2ndLvlCriteria.createCriteria("amtbAccount", "third",
				DetachedCriteria.LEFT_JOIN);

		
		invoiceHeaderCriteria.add(Restrictions.between("invoiceNo", invoiceNoFrom,
				invoiceNoTo));
		invoiceHeaderCriteria.add(Restrictions.eq("first.recurringFlag", "Y"));
	
		invoiceHeaderCriteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		return this.findAllByCriteria(invoiceHeaderCriteria);
	}
	public List<BmtbInvoiceHeader> getAllBmtbInvoicesForRecurringByCustNo(List<String> acctList, Date invoiceDate)
	{
		DetachedCriteria invoiceHeaderCriteria = DetachedCriteria.forClass(BmtbInvoiceHeader.class);
		DetachedCriteria invoiceSummaryCrit = invoiceHeaderCriteria.createCriteria("bmtbInvoiceSummaries",
				DetachedCriteria.LEFT_JOIN);
		DetachedCriteria invoiceDetailCrit = invoiceSummaryCrit.createCriteria("bmtbInvoiceDetails",
				DetachedCriteria.LEFT_JOIN);
		
		// joining first lvl 1
		DetachedCriteria accountCriteria = invoiceHeaderCriteria.createCriteria("amtbAccountByDebtTo",
				"first", DetachedCriteria.INNER_JOIN);

		// joining second lvl 2
		DetachedCriteria account2ndLvlCriteria = accountCriteria.createCriteria("amtbAccount", "second",
				Criteria.LEFT_JOIN);

		// joining third lvl 3
		DetachedCriteria account3ndLvlCriteria = account2ndLvlCriteria.createCriteria("amtbAccount", "third",
				DetachedCriteria.LEFT_JOIN);

		
		invoiceHeaderCriteria.add(Restrictions.eq("first.recurringFlag", "Y"));
		
		if(invoiceDate != null)
			invoiceHeaderCriteria.add(Restrictions.eq("invoiceDate", invoiceDate));
		
		Disjunction disjunction = Restrictions.disjunction();
		disjunction.add(Restrictions.in("first.custNo", acctList));
		disjunction.add(Restrictions.in("second.custNo", acctList));
		disjunction.add(Restrictions.in("third.custNo", acctList));
		invoiceHeaderCriteria.add(disjunction);
		
		invoiceHeaderCriteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		return this.findAllByCriteria(invoiceHeaderCriteria);
	}
//	public List<BmtbInvoiceHeader> getAllBmtbInvoicesForRecurringByAccountNo(List<Integer> acctList, Date invoiceDateFrom, Date invoiceDateTo)
	public List<BmtbInvoiceHeader> getAllBmtbInvoicesForRecurringByAccountNo(List<Integer> acctList, Date invoiceDateTo)
	{
		DetachedCriteria invoiceHeaderCriteria = DetachedCriteria.forClass(BmtbInvoiceHeader.class);
		DetachedCriteria invoiceSummaryCrit = invoiceHeaderCriteria.createCriteria("bmtbInvoiceSummaries",
				DetachedCriteria.LEFT_JOIN);
		DetachedCriteria invoiceDetailCrit = invoiceSummaryCrit.createCriteria("bmtbInvoiceDetails",
				DetachedCriteria.LEFT_JOIN);
		
		// joining first lvl 1
		DetachedCriteria accountCriteria = invoiceHeaderCriteria.createCriteria("amtbAccountByDebtTo",
				"first", DetachedCriteria.INNER_JOIN);

		// joining second lvl 2
		DetachedCriteria account2ndLvlCriteria = accountCriteria.createCriteria("amtbAccount", "second",
				Criteria.LEFT_JOIN);

		// joining third lvl 3
		DetachedCriteria account3ndLvlCriteria = account2ndLvlCriteria.createCriteria("amtbAccount", "third",
				DetachedCriteria.LEFT_JOIN);

		
		invoiceHeaderCriteria.add(Restrictions.eq("first.recurringFlag", "Y"));

		//To follow as closely as auto
//		if (invoiceDateFrom != null) {
//			invoiceHeaderCriteria.add(Restrictions.ge("invoiceDate", invoiceDateFrom));
//		}

		if (invoiceDateTo != null) {
			invoiceHeaderCriteria.add(Restrictions.le("invoiceDate", invoiceDateTo));
		}

		Disjunction disjunction = Restrictions.disjunction();
		disjunction.add(Restrictions.in("first.accountNo", acctList));
		disjunction.add(Restrictions.in("second.accountNo", acctList));
		disjunction.add(Restrictions.in("third.accountNo", acctList));
		invoiceHeaderCriteria.add(disjunction);
		
		invoiceHeaderCriteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		return this.findAllByCriteria(invoiceHeaderCriteria);
	}
	public List<BmtbInvoiceHeader> getAllBmtbInvoicesByBillGenReqNo(BmtbBillGenReq bmtbBillGenReq)
	{
		DetachedCriteria invoiceHeaderCriteria = DetachedCriteria.forClass(BmtbInvoiceHeader.class);
		DetachedCriteria invoiceSummaryCrit = invoiceHeaderCriteria.createCriteria("bmtbInvoiceSummaries",
				DetachedCriteria.LEFT_JOIN);
		DetachedCriteria invoiceDetailCrit = invoiceSummaryCrit.createCriteria("bmtbInvoiceDetails",
				DetachedCriteria.LEFT_JOIN);
		
		// joining first lvl 1
		DetachedCriteria accountCriteria = invoiceHeaderCriteria.createCriteria("amtbAccountByDebtTo",
				"first", DetachedCriteria.INNER_JOIN);

		// joining second lvl 2
		DetachedCriteria account2ndLvlCriteria = accountCriteria.createCriteria("amtbAccount", "second",
				Criteria.LEFT_JOIN);

		// joining third lvl 3
		DetachedCriteria account3ndLvlCriteria = account2ndLvlCriteria.createCriteria("amtbAccount", "third",
				DetachedCriteria.LEFT_JOIN);

		
		invoiceHeaderCriteria.add(Restrictions.eq("bmtbBillGenReq", bmtbBillGenReq));
		invoiceHeaderCriteria.add(Restrictions.eq("first.recurringFlag", "Y"));
	
		invoiceHeaderCriteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		return this.findAllByCriteria(invoiceHeaderCriteria);
	}
}