package com.cdgtaxi.ibs.common.dao;

import java.math.BigDecimal;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.criterion.Conjunction;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.Subqueries;

import com.cdgtaxi.ibs.common.NonConfigurableConstants;
import com.cdgtaxi.ibs.common.model.AmtbAccount;
import com.cdgtaxi.ibs.common.model.AmtbAcctMainContact;
import com.cdgtaxi.ibs.common.model.AmtbAcctStatus;
import com.cdgtaxi.ibs.common.model.AmtbContactPerson;
import com.cdgtaxi.ibs.common.model.BmtbBillGenSetup;
import com.cdgtaxi.ibs.common.model.BmtbDraftInvHeader;
import com.cdgtaxi.ibs.common.model.BmtbInvoiceDepositTxn;
import com.cdgtaxi.ibs.common.model.BmtbInvoiceDetail;
import com.cdgtaxi.ibs.common.model.BmtbInvoiceHeader;
import com.cdgtaxi.ibs.common.model.BmtbInvoiceSummary;
import com.cdgtaxi.ibs.common.model.FmtbEntityMaster;
import com.cdgtaxi.ibs.common.model.FmtbTransactionCode;
import com.cdgtaxi.ibs.common.model.forms.SearchInvoiceForm;
import com.cdgtaxi.ibs.util.DateUtil;
import com.cdgtaxi.ibs.util.StringUtil;

public class InvoiceDaoHibernate extends GenericDaoHibernate implements InvoiceDao {
	private static Logger logger = Logger.getLogger(InvoiceDaoHibernate.class);

	public boolean checkGLDepositTxnCodeAvailable(Integer entityNo) {
		DetachedCriteria criteria = DetachedCriteria.forClass(FmtbTransactionCode.class);
		criteria.add(Restrictions.eq("txnType", NonConfigurableConstants.TRANSACTION_TYPE_DEPOSIT));
		criteria.add(Restrictions.eq("fmtbEntityMaster.entityNo", entityNo));
		criteria.add(Restrictions.le("effectiveDate", DateUtil.getCurrentDate()));

		return this.findMaxResultByCriteria(criteria, 1).size() > 0;
	}

	public BmtbInvoiceHeader generateDepositInvoice(BigDecimal depositAmt, Date invoiceDate,
			AmtbAccount acct, String username) {
		logger.info("generateDepositInvoice()");
		BmtbInvoiceHeader header = new BmtbInvoiceHeader();
		header.setInvoiceNo(this.getNextSequenceNo(Sequence.INVOICE_NO_SEQUENCE).longValue());
		header.setAmtbAccountByAccountNo(acct);
		header.setAmtbAccountByDebtTo(acct);
		header.setOutstandingAmount(depositAmt);
		// header.setCustomerId(acct.getCustNo());
		// header.setCustomerName(acct.getAccountName());
		header.setInvoiceDate(DateUtil.convertUtilDateToSqlDate(invoiceDate));
		header.setInvoiceFormat(NonConfigurableConstants.INVOICE_FORMAT_DEPOSIT);
		header.setInvoiceStatus(NonConfigurableConstants.INVOICE_STATUS_OUTSTANDING);
		header.setNewBalance(depositAmt);
		header.setNewTxn(depositAmt);
		header.setBilledFlag(NonConfigurableConstants.BOOLEAN_NO);
		Calendar now = Calendar.getInstance();
		now.set(Calendar.MONTH, now.get(Calendar.MONTH) + 1);
		header.setDueDate(null); // due date is null because it is a COD so deposit invoice will never due
		header.setStatementPeriodFromDate(DateUtil.getCurrentDate());
		header.setStatementPeriodToDate(DateUtil.getCurrentDate());
		header.setTxnCutoffDate(DateUtil.getCurrentDate());
		FmtbEntityMaster entity = acct.getFmtbArContCodeMaster().getFmtbEntityMaster();
		// header.setEntityName(entity.getEntityName());
		// header.setEntityArea(entity.getEntityArea());
		// header.setEntityBlock(entity.getEntityBlock());
		// header.setEntityBuilding(entity.getEntityBuilding());
		// header.setEntityCity(entity.getEntityCity());
		// header.setEntityCountry(entity.getMstbMasterTable().getMasterValue());
		// header.setEntityPostal(entity.getEntityPostal());
		// header.setEntityState(entity.getEntityState());
		// header.setEntityStreet(entity.getEntityStreet());
		// header.setEntityRcbNo(entity.getEntityRcbNo());
		// header.setEntityGstNo(entity.getEntityGstNo());
		for (AmtbAcctMainContact mainContact : acct.getAmtbAcctMainContacts()) {
			if (mainContact.getComp_id().getMainContactType()
					.equals(NonConfigurableConstants.MAIN_CONTACT_TYPE_BILLING)) {
				AmtbContactPerson contact = mainContact.getAmtbContactPerson();
				// header.setAddressArea(contact.getAddressArea());
				// header.setAddressBlock(contact.getAddressBlock());
				// header.setAddressBuilding(contact.getAddressBuilding());
				// header.setAddressCountry(contact.getMstbMasterTableByAddressCountry().getMasterValue());
				// header.setAddressPostal(contact.getAddressPostal());
				// header.setAddressStreet(contact.getAddressStreet());
				// header.setAddressUnit(contact.getAddressUnit());
				// header.setBillingContactPersonName(getContactCombinedNames(contact));
				break;
			}
		}
		this.save(header, username);
		BmtbInvoiceSummary summary = new BmtbInvoiceSummary();
		summary.setBmtbInvoiceHeader(header);
		summary.setAmtbAccount(acct);
		summary.setTotalNew(depositAmt);
		summary.setBalance(depositAmt);
		summary.setNewTxn(depositAmt);
		// summary.setSummaryGroup(acct.getAccountName());
		// summary.setSummaryGroupName("MISCELLANEOUS");
		summary.setSummaryName(NonConfigurableConstants.INVOICE_SUMMARY_TYPE_GROUP_NAME
				.get(NonConfigurableConstants.INVOICE_SUMMARY_DETAIL_TYPE_DEPOSIT));
		summary.setSummaryType(NonConfigurableConstants.INVOICE_SUMMARY_DETAIL_TYPE_DEPOSIT);
		this.save(summary, username);
		BmtbInvoiceDetail detail = new BmtbInvoiceDetail();
		detail.setInvoiceDetailName(NonConfigurableConstants.INVOICE_SUMMARY_TYPE_GROUP_NAME
				.get(NonConfigurableConstants.INVOICE_SUMMARY_DETAIL_TYPE_DEPOSIT));
		detail.setInvoiceDetailType(NonConfigurableConstants.INVOICE_SUMMARY_DETAIL_TYPE_DEPOSIT);
		// detail.setAccountName(acct.getAccountName());
		detail.setAmtbAccount(acct);
		detail.setBmtbInvoiceSummary(summary);
		detail.setOutstandingAmount(depositAmt);
		detail.setNewTxn(depositAmt);
		detail.setTotalNew(depositAmt);
		this.save(detail, username);
		BmtbInvoiceDepositTxn deposit = new BmtbInvoiceDepositTxn();
		deposit.setTxnType(NonConfigurableConstants.DEPOSIT_TXN_TYPE_REQUEST);
		deposit.setAmtbAccount(acct);
		deposit.setAmount(depositAmt);
		deposit.setTxnDate(DateUtil.getCurrentDate());
		deposit.setBmtbInvoiceHeader(header);
		this.save(deposit, username);
		return header;
	}

	private String getContactCombinedNames(AmtbContactPerson contact) {
		StringBuffer contactName = new StringBuffer();
		if (contact != null) {
			if (contact.getMstbMasterTableByMainContactSal() != null) {
				contactName.append(contact.getMstbMasterTableByMainContactSal().getMasterValue());
				contactName.append(" ");
			}
			contactName.append(contact.getMainContactName());
			if (contact.getSubContactName() != null) {
				contactName.append(" / ");
				if (contact.getMstbMasterTableBySubContactSal() != null) {
					contactName.append(contact.getMstbMasterTableBySubContactSal().getMasterValue());
					contactName.append(" ");
				}
				contactName.append(contact.getSubContactName());
			}
		}
		return contactName.toString();
	}

	@SuppressWarnings("unchecked")
	public BmtbBillGenSetup getBillGenSetup(Integer setupNo) {
		DetachedCriteria setupCriteria = DetachedCriteria.forClass(BmtbBillGenSetup.class);
		setupCriteria.add(Restrictions.eq("setupNo", setupNo));
		List<BmtbBillGenSetup> setups = this.findAllByCriteria(setupCriteria);
		return setups.isEmpty() ? null : setups.get(0);
	}

	@SuppressWarnings("unchecked")
	public List<BmtbInvoiceHeader> getOutstandingInvoiceByAccount(AmtbAccount account) {
		DetachedCriteria invoiceHeaderCriteria = DetachedCriteria.forClass(BmtbInvoiceHeader.class);
		DetachedCriteria debtToAccountCriteria = invoiceHeaderCriteria.createCriteria("amtbAccountByDebtTo",
				DetachedCriteria.INNER_JOIN);

		// left join summary - to snap shot current retrieve data with version number
		DetachedCriteria invoiceSummaryCriteria = invoiceHeaderCriteria.createCriteria(
				"bmtbInvoiceSummaries", DetachedCriteria.LEFT_JOIN);
		// left join detail - to snap shot current retrieve data with version number
		DetachedCriteria invoiceDetailCriteria = invoiceSummaryCriteria.createCriteria("bmtbInvoiceDetails",
				DetachedCriteria.LEFT_JOIN);
		invoiceDetailCriteria.createCriteria("pmtbProductType", DetachedCriteria.LEFT_JOIN);

		debtToAccountCriteria.add(Restrictions.idEq(account.getAccountNo()));

		// invoice status still outstanding
		invoiceHeaderCriteria.add(Restrictions.eq("invoiceStatus",
				NonConfigurableConstants.INVOICE_STATUS_OUTSTANDING));
		// invoice no not null
		invoiceHeaderCriteria.add(Restrictions.isNotNull("invoiceNo"));

		invoiceHeaderCriteria.addOrder(Order.asc("invoiceNo"));
		invoiceHeaderCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);

		return this.findAllByCriteria(invoiceHeaderCriteria);
	}

	@SuppressWarnings("unchecked")
	public List<BmtbInvoiceHeader> getOutstandingInvoiceByParentAccount(AmtbAccount parentAccount) {
		DetachedCriteria invoiceHeaderCriteria = DetachedCriteria.forClass(BmtbInvoiceHeader.class);
		DetachedCriteria debtToAccountCriteria = invoiceHeaderCriteria.createCriteria("amtbAccountByDebtTo",
				DetachedCriteria.INNER_JOIN);
		DetachedCriteria parentAccountCriteria = debtToAccountCriteria.createCriteria("amtbAccount",
				DetachedCriteria.INNER_JOIN);

		// left join summary - to snap shot current retrieve data with version number
		DetachedCriteria invoiceSummaryCriteria = invoiceHeaderCriteria.createCriteria(
				"bmtbInvoiceSummaries", DetachedCriteria.LEFT_JOIN);
		// left join detail - to snap shot current retrieve data with version number
		DetachedCriteria invoiceDetailCriteria = invoiceSummaryCriteria.createCriteria("bmtbInvoiceDetails",
				DetachedCriteria.LEFT_JOIN);
		invoiceDetailCriteria.createCriteria("pmtbProductType", DetachedCriteria.LEFT_JOIN);

		parentAccountCriteria.add(Restrictions.idEq(parentAccount.getAccountNo()));

		// invoice status still outstanding
		invoiceHeaderCriteria.add(Restrictions.eq("invoiceStatus",
				NonConfigurableConstants.INVOICE_STATUS_OUTSTANDING));
		// invoice no not null
		invoiceHeaderCriteria.add(Restrictions.isNotNull("invoiceNo"));

		invoiceHeaderCriteria.addOrder(Order.asc("invoiceNo"));
		invoiceHeaderCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);

		return this.findAllByCriteria(invoiceHeaderCriteria);
	}

	@SuppressWarnings("unchecked")
	public List<BmtbInvoiceHeader> getOutstandingInvoiceByGrandParentAccount(AmtbAccount grandParentAccount) {
		DetachedCriteria invoiceHeaderCriteria = DetachedCriteria.forClass(BmtbInvoiceHeader.class);
		DetachedCriteria debtToAccountCriteria = invoiceHeaderCriteria.createCriteria("amtbAccountByDebtTo",
				DetachedCriteria.INNER_JOIN);
		DetachedCriteria parentAccountCriteria = debtToAccountCriteria.createCriteria("amtbAccount",
				DetachedCriteria.INNER_JOIN);
		DetachedCriteria grandParentAccountCriteria = parentAccountCriteria.createCriteria("amtbAccount",
				DetachedCriteria.INNER_JOIN);

		// left join summary - to snap shot current retrieve data with version number
		DetachedCriteria invoiceSummaryCriteria = invoiceHeaderCriteria.createCriteria(
				"bmtbInvoiceSummaries", DetachedCriteria.LEFT_JOIN);
		// left join detail - to snap shot current retrieve data with version number
		DetachedCriteria invoiceDetailCriteria = invoiceSummaryCriteria.createCriteria("bmtbInvoiceDetails",
				DetachedCriteria.LEFT_JOIN);
		invoiceDetailCriteria.createCriteria("pmtbProductType", DetachedCriteria.LEFT_JOIN);

		grandParentAccountCriteria.add(Restrictions.idEq(grandParentAccount.getAccountNo()));

		// invoice status still outstanding
		invoiceHeaderCriteria.add(Restrictions.eq("invoiceStatus",
				NonConfigurableConstants.INVOICE_STATUS_OUTSTANDING));
		// invoice no not null
		invoiceHeaderCriteria.add(Restrictions.isNotNull("invoiceNo"));

		invoiceHeaderCriteria.addOrder(Order.asc("invoiceNo"));
		invoiceHeaderCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);

		return this.findAllByCriteria(invoiceHeaderCriteria);
	}

	@SuppressWarnings("rawtypes")
	public BigDecimal getTotalNegativeDetailAmount(BmtbInvoiceHeader invoiceHeader) {
		DetachedCriteria invoiceDetailCriteria = DetachedCriteria.forClass(BmtbInvoiceDetail.class);

		// All details that belongs to this invoice heaader
		DetachedCriteria invoiceSummaryCriteria = invoiceDetailCriteria.createCriteria("bmtbInvoiceSummary",
				DetachedCriteria.INNER_JOIN);
		invoiceSummaryCriteria.add(Restrictions.eq("bmtbInvoiceHeader", invoiceHeader));

		// Only negative amount
		invoiceDetailCriteria.add(Restrictions.lt("outstandingAmount", new BigDecimal(0)));

		// Project sum of outstandingAmount
		invoiceDetailCriteria.setProjection(Projections.sum("outstandingAmount"));

		List results = this.findAllByCriteria(invoiceDetailCriteria);
		if (results.isEmpty()) {
			return new BigDecimal(0);
		} else {
			BigDecimal totalNegativeAmount = (BigDecimal) results.get(0);
			if (totalNegativeAmount == null) {
				return new BigDecimal(0);
			} else {
				return totalNegativeAmount;
			}
		}
	}

	@SuppressWarnings("unchecked")
	public List<BmtbInvoiceHeader> getNegativeInvoiceByAccount(AmtbAccount account) {
		DetachedCriteria invoiceHeaderCriteria = DetachedCriteria.forClass(BmtbInvoiceHeader.class);
		DetachedCriteria debtToAccountCriteria = invoiceHeaderCriteria.createCriteria("amtbAccountByDebtTo",
				DetachedCriteria.INNER_JOIN);

		// left join summary - to snap shot current retrieve data with version number
		DetachedCriteria invoiceSummaryCriteria = invoiceHeaderCriteria.createCriteria(
				"bmtbInvoiceSummaries", DetachedCriteria.LEFT_JOIN);
		// left join detail - to snap shot current retrieve data with version number
		DetachedCriteria invoiceDetailCriteria = invoiceSummaryCriteria.createCriteria("bmtbInvoiceDetails",
				DetachedCriteria.LEFT_JOIN);
		invoiceDetailCriteria.createCriteria("pmtbProductType", DetachedCriteria.LEFT_JOIN);

		debtToAccountCriteria.add(Restrictions.idEq(account.getAccountNo()));

		// invoice status still outstanding
		invoiceHeaderCriteria.add(Restrictions.eq("invoiceStatus",
				NonConfigurableConstants.INVOICE_STATUS_OUTSTANDING));
		// invoice no not null
		invoiceHeaderCriteria.add(Restrictions.isNotNull("invoiceNo"));
		// invoice amount must be negative
		invoiceHeaderCriteria.add(Restrictions.lt("outstandingAmount", new BigDecimal(0)));

		invoiceHeaderCriteria.addOrder(Order.asc("invoiceNo"));
		invoiceHeaderCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);

		return this.findAllByCriteria(invoiceHeaderCriteria);
	}

	@SuppressWarnings("unchecked")
	public List<BmtbInvoiceHeader> getNegativeInvoiceByParentAccount(AmtbAccount parentAccount) {
		DetachedCriteria invoiceHeaderCriteria = DetachedCriteria.forClass(BmtbInvoiceHeader.class);
		DetachedCriteria debtToAccountCriteria = invoiceHeaderCriteria.createCriteria("amtbAccountByDebtTo",
				DetachedCriteria.INNER_JOIN);
		DetachedCriteria parentAccountCriteria = debtToAccountCriteria.createCriteria("amtbAccount",
				DetachedCriteria.INNER_JOIN);

		// left join summary - to snap shot current retrieve data with version number
		DetachedCriteria invoiceSummaryCriteria = invoiceHeaderCriteria.createCriteria(
				"bmtbInvoiceSummaries", DetachedCriteria.LEFT_JOIN);
		// left join detail - to snap shot current retrieve data with version number
		DetachedCriteria invoiceDetailCriteria = invoiceSummaryCriteria.createCriteria("bmtbInvoiceDetails",
				DetachedCriteria.LEFT_JOIN);
		invoiceDetailCriteria.createCriteria("pmtbProductType", DetachedCriteria.LEFT_JOIN);

		parentAccountCriteria.add(Restrictions.idEq(parentAccount.getAccountNo()));

		// invoice status still outstanding
		invoiceHeaderCriteria.add(Restrictions.eq("invoiceStatus",
				NonConfigurableConstants.INVOICE_STATUS_OUTSTANDING));
		// invoice no not null
		invoiceHeaderCriteria.add(Restrictions.isNotNull("invoiceNo"));
		// invoice amount must be negative
		invoiceHeaderCriteria.add(Restrictions.lt("outstandingAmount", new BigDecimal(0)));

		invoiceHeaderCriteria.addOrder(Order.asc("invoiceNo"));
		invoiceHeaderCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);

		return this.findAllByCriteria(invoiceHeaderCriteria);
	}

	@SuppressWarnings("unchecked")
	public List<BmtbInvoiceHeader> getNegativeInvoiceByGrandParentAccount(AmtbAccount grandParentAccount) {
		DetachedCriteria invoiceHeaderCriteria = DetachedCriteria.forClass(BmtbInvoiceHeader.class);
		DetachedCriteria debtToAccountCriteria = invoiceHeaderCriteria.createCriteria("amtbAccountByDebtTo",
				DetachedCriteria.INNER_JOIN);
		DetachedCriteria parentAccountCriteria = debtToAccountCriteria.createCriteria("amtbAccount",
				DetachedCriteria.INNER_JOIN);
		DetachedCriteria grandParentAccountCriteria = parentAccountCriteria.createCriteria("amtbAccount",
				DetachedCriteria.INNER_JOIN);

		// left join summary - to snap shot current retrieve data with version number
		DetachedCriteria invoiceSummaryCriteria = invoiceHeaderCriteria.createCriteria(
				"bmtbInvoiceSummaries", DetachedCriteria.LEFT_JOIN);
		// left join detail - to snap shot current retrieve data with version number
		DetachedCriteria invoiceDetailCriteria = invoiceSummaryCriteria.createCriteria("bmtbInvoiceDetails",
				DetachedCriteria.LEFT_JOIN);
		invoiceDetailCriteria.createCriteria("pmtbProductType", DetachedCriteria.LEFT_JOIN);

		grandParentAccountCriteria.add(Restrictions.idEq(grandParentAccount.getAccountNo()));

		// invoice status still outstanding
		invoiceHeaderCriteria.add(Restrictions.eq("invoiceStatus",
				NonConfigurableConstants.INVOICE_STATUS_OUTSTANDING));
		// invoice no not null
		invoiceHeaderCriteria.add(Restrictions.isNotNull("invoiceNo"));
		// invoice amount must be negative
		invoiceHeaderCriteria.add(Restrictions.lt("outstandingAmount", new BigDecimal(0)));

		invoiceHeaderCriteria.addOrder(Order.asc("invoiceNo"));
		invoiceHeaderCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);

		return this.findAllByCriteria(invoiceHeaderCriteria);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cdgtaxi.ibs.common.dao.InvoiceDao#getTotalOutstandingAmount(java.lang.Integer, java.sql.Date)
	 */
	@SuppressWarnings({ "rawtypes" })
	public BigDecimal getTotalOutstandingAmount(Integer accountNo, java.sql.Date paidInvoiceDueDate) {
		// Create Named Params & Values
		String[] paramNames = new String[] { "accountNo", "paidInvoiceDueDate" };
		Object[] values = new Object[] { accountNo, paidInvoiceDueDate };

		// Query using named query - BmtbInvoiceHeader.hbm.xml
		List results = this.getHibernateTemplate().findByNamedQueryAndNamedParam(
				"getTotalDueOutstandingAmount", paramNames, values);

		if (results.isEmpty()) {
			return null;
		} else {
			return (BigDecimal) results.get(0);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cdgtaxi.ibs.common.dao.InvoiceDao#getTotalOutstandingAmount(java.lang.Integer)
	 */
	@SuppressWarnings({ "rawtypes" })
	public BigDecimal getTotalOutstandingAmount(Integer accountNo) {
		// Create Named Params & Values
		String[] paramNames = new String[] { "accountNo" };
		Object[] values = new Object[] { accountNo };

		// Query using named query - BmtbInvoiceHeader.hbm.xml
		List results = this.getHibernateTemplate().findByNamedQueryAndNamedParam("getTotalOutstandingAmount",
				paramNames, values);

		if (results.isEmpty()) {
			return null;
		} else {
			return (BigDecimal) results.get(0);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cdgtaxi.ibs.common.dao.InvoiceDao#getInvoicesToRewardEarlyPayment(java.lang.Integer)
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<Long> getInvoicesToRewardEarlyPayment(Integer accountNo) {
		// Create Named Params & Values
		String[] paramNames = new String[] { "accountNo" };
		Object[] values = new Object[] { accountNo };

		// Query using named query
		List results = this.getHibernateTemplate().findByNamedQueryAndNamedParam(
				"getInvoicesToRewardEarlyPayment", paramNames, values);
		return results;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cdgtaxi.ibs.common.dao.InvoiceDao#getNotBilledEarlyPaymentInvoices(java.lang.Integer,
	 * java.sql.Date)
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<Long> getNotBilledEarlyPaymentInvoices(Integer accountNo, java.sql.Date cancelInvoiceDueDate) {
		// Create Named Params & Values
		String[] paramNames = new String[] { "accountNo", "cancelInvoiceDueDate" };
		Object[] values = new Object[] { accountNo, cancelInvoiceDueDate };

		// Query using named query
		List results = this.getHibernateTemplate().findByNamedQueryAndNamedParam(
				"getNotBilledEarlyPaymentInvoices", paramNames, values);
		return results;
	}

	@SuppressWarnings("unchecked")
	public void debtToBilliableParent(Integer accountNo, String userId) {
		logger.info("test");
		DetachedCriteria acctCriteria = DetachedCriteria.forClass(AmtbAccount.class);
		acctCriteria.add(Restrictions.idEq(accountNo));
		acctCriteria.createCriteria("amtbAccount", DetachedCriteria.INNER_JOIN);
		acctCriteria.createCriteria("bmtbInvoiceHeadersByAccountNo", DetachedCriteria.INNER_JOIN);
		acctCriteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		List<AmtbAccount> parentAccts = this.findAllByCriteria(acctCriteria);
		if (!parentAccts.isEmpty()) {
			AmtbAccount acct = parentAccts.get(0);
			AmtbAccount parent = acct.getAmtbAccount();
			while (parent.getInvoiceFormat() == null) {
				acctCriteria = DetachedCriteria.forClass(AmtbAccount.class);
				acctCriteria.add(Restrictions.eq("accountNo", parent.getAccountNo()));
				acctCriteria.createCriteria("amtbAccount", DetachedCriteria.LEFT_JOIN);
				acctCriteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
				parentAccts = this.findAllByCriteria(acctCriteria);
				if (!parentAccts.isEmpty()) {
					parent = parentAccts.get(0).getAmtbAccount();
				} else {
					break;
				}
			}
			for (BmtbInvoiceHeader invoiceHeader : acct.getBmtbInvoiceHeadersByAccountNo()) {
				invoiceHeader.setAmtbAccountByDebtTo(parent);
				this.update(invoiceHeader, userId);
			}
		}
	}

	@SuppressWarnings("rawtypes")
	public boolean checkInvoiceExist(Integer accountNo, Integer billGenRequestNo) {
		DetachedCriteria invoiceCriteria = DetachedCriteria.forClass(BmtbInvoiceHeader.class);
		DetachedCriteria accountCriteria = invoiceCriteria.createCriteria("amtbAccountByAccountNo",
				DetachedCriteria.INNER_JOIN);
		DetachedCriteria billGenRequestCriteria = invoiceCriteria.createCriteria("bmtbBillGenReq",
				DetachedCriteria.INNER_JOIN);

		accountCriteria.add(Restrictions.eq("accountNo", accountNo));
		billGenRequestCriteria.add(Restrictions.eq("reqNo", billGenRequestNo));

		List results = this.findMaxResultByCriteria(invoiceCriteria, 1);
		if (results.isEmpty()) {
			return false;
		} else {
			return true;
		}
	}

	@SuppressWarnings("unchecked")
	public List<BmtbInvoiceHeader> searchInvoice(SearchInvoiceForm form) {
		DetachedCriteria searchInvoiceCriteria = buildSearchInvoiceCriteria(form);

		// Due to left join, the result returned more than max result.
		// End search result may only retrieve total entities less than max result.
		DetachedCriteria subqueryCriteria = this.createMaxResultSubquery(searchInvoiceCriteria,
				"invoice_Header_No");
		searchInvoiceCriteria.add(Subqueries.propertyIn("invoiceHeaderNo", subqueryCriteria));

		// default sorting
		searchInvoiceCriteria.addOrder(Order.desc("invoiceDate"));
		searchInvoiceCriteria.addOrder(Order.asc("invoiceNo"));

		return this.findAllByCriteria(searchInvoiceCriteria);
	}

	@SuppressWarnings("unchecked")
	public List<BmtbDraftInvHeader> searchDraftInvoice(SearchInvoiceForm form) {
		DetachedCriteria searchInvoiceCriteria = buildSearchDraftInvoiceCriteria(form);

		// Due to left join, the result returned more than max result.
		// End search result may only retrieve total entities less than max result.
		DetachedCriteria subqueryCriteria = this.createMaxResultSubquery(searchInvoiceCriteria,
				"invoice_Header_No");
		searchInvoiceCriteria.add(Subqueries.propertyIn("invoiceHeaderNo", subqueryCriteria));

		searchInvoiceCriteria.addOrder(Order.asc("acct.accountNo"));
		searchInvoiceCriteria.addOrder(Order.asc("invoiceNo"));

		return findAllByCriteria(searchInvoiceCriteria);
	}

	@SuppressWarnings("unchecked")
	public BmtbInvoiceHeader getInvoice(String invoiceNo) {
		DetachedCriteria invoiceCriteria = DetachedCriteria.forClass(BmtbInvoiceHeader.class);
		invoiceCriteria.createCriteria("amtbAccountByAccountNo", DetachedCriteria.INNER_JOIN);
		DetachedCriteria mainAccountCriteria = invoiceCriteria.createCriteria("amtbAccountByDebtTo",
				DetachedCriteria.LEFT_JOIN);
		DetachedCriteria parentAccountCriteria = mainAccountCriteria.createCriteria("amtbAccount",
				DetachedCriteria.LEFT_JOIN);
		parentAccountCriteria.createCriteria("amtbAccount", DetachedCriteria.LEFT_JOIN);

		invoiceCriteria.add(Restrictions.eq("invoiceNo", Long.parseLong(invoiceNo)));
		List<BmtbInvoiceHeader> list = findDefaultMaxResultByCriteria(invoiceCriteria);
		if (list.size() == 0) {
			return null;
		} else {
			return list.get(0);
		}
	}

	@SuppressWarnings("unchecked")
	public BmtbDraftInvHeader getDraftInvoice(String invoiceNo) {
		DetachedCriteria invoiceCriteria = DetachedCriteria.forClass(BmtbDraftInvHeader.class);
		invoiceCriteria.createCriteria("amtbAccountByAccountNo", DetachedCriteria.INNER_JOIN);
		DetachedCriteria mainAccountCriteria = invoiceCriteria.createCriteria("amtbAccountByDebtTo",
				DetachedCriteria.LEFT_JOIN);
		DetachedCriteria parentAccountCriteria = mainAccountCriteria.createCriteria("amtbAccount",
				DetachedCriteria.LEFT_JOIN);
		parentAccountCriteria.createCriteria("amtbAccount", DetachedCriteria.LEFT_JOIN);

		invoiceCriteria.add(Restrictions.eq("invoiceNo", Long.parseLong(invoiceNo)));
		List<BmtbDraftInvHeader> list = findDefaultMaxResultByCriteria(invoiceCriteria);
		if (list.size() == 0) {
			return null;
		} else {
			return list.get(0);
		}
	}

	public byte[] getInvoiceFile(String invoiceNo) {
		BmtbInvoiceHeader invoiceHeader = getInvoice(invoiceNo);
		if (invoiceHeader != null && invoiceHeader.getBmtbInvoiceFile() != null) {
			try {
				Blob blob = invoiceHeader.getBmtbInvoiceFile().getInvoiceFile();
				return blob.getBytes(1, (int) blob.length());
			} catch (SQLException e) {
				logger.error(e.getMessage(), e);
				return null;
			}
		}
		return null;
	}

	public byte[] getDraftInvoiceFile(String invoiceNo) {
		BmtbDraftInvHeader invoiceHeader = getDraftInvoice(invoiceNo);
		if (invoiceHeader != null && invoiceHeader.getBmtbDraftInvFile() != null) {
			try {
				Blob blob = invoiceHeader.getBmtbDraftInvFile().getInvoiceFile();
				return blob.getBytes(1, (int) blob.length());
			} catch (SQLException e) {
				logger.error(e.getMessage(), e);
				return null;
			}
		}
		return null;
	}

	public BigDecimal getTransactedAmountByType(String sInvoiceNo, String txnType) {
		Long invoiceNo = Long.parseLong(sInvoiceNo);

		// 1) Get the sum of all transaction amounts of type "txnType" for the invoice
		DetachedCriteria invoiceCriteria = DetachedCriteria.forClass(BmtbInvoiceHeader.class);
		DetachedCriteria detailCriteria = invoiceCriteria.createCriteria("bmtbInvoiceSummaries",
				DetachedCriteria.INNER_JOIN).createCriteria("bmtbInvoiceDetails", "detail",
				DetachedCriteria.INNER_JOIN);

		invoiceCriteria.add(Restrictions.eq("invoiceNo", invoiceNo));
		detailCriteria.add(Restrictions.eq("invoiceDetailType", txnType));

		invoiceCriteria.setProjection(Projections.sum("detail.newTxn"));

		BigDecimal txnAmt = (BigDecimal) findAllByCriteria(invoiceCriteria).get(0);
		txnAmt = (txnAmt == null) ? BigDecimal.ZERO : txnAmt;
		logger.debug("txnAmt = " + txnAmt);

		// 2) Get the sum of all CREDIT note amounts of type "txnType" for the invoice
		DetachedCriteria issuedInvoiceCriteria = DetachedCriteria.forClass(BmtbInvoiceHeader.class);
		DetachedCriteria noteCriteria = issuedInvoiceCriteria.createCriteria("bmtbNotes", "note",
				DetachedCriteria.INNER_JOIN);
		DetachedCriteria txnCodeCriteria = noteCriteria.createCriteria("fmtbTransactionCode",
				DetachedCriteria.INNER_JOIN);

		issuedInvoiceCriteria.add(Restrictions.eq("invoiceNo", invoiceNo));
		noteCriteria.add(Restrictions.eq("noteType", NonConfigurableConstants.NOTE_TYPE_CREDIT));
		noteCriteria.add(Restrictions.ne("status", NonConfigurableConstants.NOTE_STATUS_CANCELLED));
		txnCodeCriteria.add(Restrictions.eq("txnType", txnType));

		issuedInvoiceCriteria.setProjection(Projections.sum("note.noteAmount"));

		BigDecimal noteAmt = (BigDecimal) findAllByCriteria(issuedInvoiceCriteria).get(0);
		noteAmt = (noteAmt == null) ? BigDecimal.ZERO : noteAmt;
		logger.debug("prevNoteAmt = " + noteAmt);

		// 3) Get the limit of type "txnType" for the invoice
		BigDecimal limitAmt = txnAmt.subtract(noteAmt);

		return limitAmt;
	}

	/**
	 * Deposit invoices will only be returned for accounts that are closed.
	 */
	@SuppressWarnings("unchecked")
	public List<BmtbInvoiceHeader> searchNoteIssuableInvoice(SearchInvoiceForm form) {
		DetachedCriteria invoiceHeaderCriteria = buildSearchInvoiceCriteria(form);

		// Prepare sub-queries for getting the date-time of the effective status
		Date currentDate = new Date();
		DetachedCriteria accountEffectiveStatusDt = DetachedCriteria
				.forClass(AmtbAcctStatus.class, "lastEffDt1")
				.add(Restrictions.eqProperty("amtbAccount", "acct.accountNo"))
				.add(Restrictions.le("effectiveDt", currentDate))
				.setProjection(Projections.max("effectiveDt"));

		DetachedCriteria parentAccountEffectiveStatusDt = DetachedCriteria
				.forClass(AmtbAcctStatus.class, "lastEffDt2")
				.add(Restrictions.eqProperty("amtbAccount", "p_acct.accountNo"))
				.add(Restrictions.le("effectiveDt", currentDate))
				.setProjection(Projections.max("effectiveDt"));

		DetachedCriteria grandParentAccountEffectiveStatusDt = DetachedCriteria
				.forClass(AmtbAcctStatus.class, "lastEffDt3")
				.add(Restrictions.eqProperty("amtbAccount", "gp_acct.accountNo"))
				.add(Restrictions.le("effectiveDt", currentDate))
				.setProjection(Projections.max("effectiveDt"));

		// Prepare the criteria for obtaining root accounts that are closed
		Criterion accountIsRootAndClosed = Restrictions.conjunction()
				.add(Restrictions.isNull("acct.amtbAccount"))
				.add(Subqueries.propertyEq("acctSts.effectiveDt", accountEffectiveStatusDt))
				.add(Restrictions.eq("acctSts.acctStatus", NonConfigurableConstants.ACCOUNT_STATUS_CLOSED));

		Criterion parentAccountIsRootAndClosed = Restrictions.conjunction()
				.add(Restrictions.isNotNull("acct.amtbAccount"))
				.add(Restrictions.isNull("p_acct.amtbAccount"))
				.add(Subqueries.propertyEq("p_acctSts.effectiveDt", parentAccountEffectiveStatusDt))
				.add(Restrictions.eq("p_acctSts.acctStatus", NonConfigurableConstants.ACCOUNT_STATUS_CLOSED));

		Criterion grandParentAccountIsRootAndClosed = Restrictions
				.conjunction()
				.add(Restrictions.isNotNull("acct.amtbAccount"))
				.add(Restrictions.isNotNull("p_acct.amtbAccount"))
				.add(Restrictions.isNull("gp_acct.amtbAccount"))
				.add(Subqueries.propertyEq("gp_acctSts.effectiveDt", grandParentAccountEffectiveStatusDt))
				.add(Restrictions.eq("gp_acctSts.acctStatus", NonConfigurableConstants.ACCOUNT_STATUS_CLOSED));

		// Criteria for non-Deposit invoices
		Criterion nonDepositInvoices = Restrictions.ne("invoiceFormat",
				NonConfigurableConstants.INVOICE_FORMAT_DEPOSIT);

		// Final criteria for invoices that can be issued notes
		Criterion issuableCriterion = Restrictions.disjunction().add(nonDepositInvoices)
				.add(accountIsRootAndClosed).add(parentAccountIsRootAndClosed)
				.add(grandParentAccountIsRootAndClosed);

		// Combine with search criteria
		invoiceHeaderCriteria.add(issuableCriterion);

		// Due to left join, the result returned more than max result.
		// End search result may only retrieve total entities less than max result.
		DetachedCriteria subqueryCriteria = this.createMaxResultSubquery(invoiceHeaderCriteria,
				"invoice_Header_No");
		invoiceHeaderCriteria.add(Subqueries.propertyIn("invoiceHeaderNo", subqueryCriteria));

		return findAllByCriteria(invoiceHeaderCriteria);
	}

	private DetachedCriteria buildSearchInvoiceCriteria(SearchInvoiceForm form) {
		DetachedCriteria invoiceHeaderCriteria = DetachedCriteria.forClass(BmtbInvoiceHeader.class);
		DetachedCriteria accountCriteria = invoiceHeaderCriteria.createCriteria("amtbAccountByDebtTo",
				"acct", DetachedCriteria.INNER_JOIN);
		@SuppressWarnings("unused")
		DetachedCriteria notesCriteria = invoiceHeaderCriteria.createCriteria("bmtbNotes",
				DetachedCriteria.LEFT_JOIN);
		DetachedCriteria receiptDetailsCriteria = invoiceHeaderCriteria.createCriteria(
				"bmtbPaymentReceiptDetails", DetachedCriteria.LEFT_JOIN);
		@SuppressWarnings("unused")
		DetachedCriteria receiptCriteria = receiptDetailsCriteria.createCriteria("bmtbPaymentReceipt",
				DetachedCriteria.LEFT_JOIN);

		// these aliases are used by searchNoteIssuableInvoice
		accountCriteria.createAlias("amtbAccount", "p_acct", DetachedCriteria.LEFT_JOIN);
		accountCriteria.createAlias("amtbAccount.amtbAccount", "gp_acct", DetachedCriteria.LEFT_JOIN);
		accountCriteria.createAlias("amtbAcctStatuses", "acctSts", DetachedCriteria.LEFT_JOIN);
		accountCriteria.createAlias("p_acct.amtbAcctStatuses", "p_acctSts", DetachedCriteria.LEFT_JOIN);
		accountCriteria.createAlias("gp_acct.amtbAcctStatuses", "gp_acctSts", DetachedCriteria.LEFT_JOIN);

		// Criteria for account
		if (form.getDepartment() != null) {
			accountCriteria.add(Restrictions.idEq(form.getDepartment().getAccountNo()));
		} else if (form.getDivision() != null) {
			accountCriteria.add(Restrictions.idEq(form.getDivision().getAccountNo()));
		} else if (form.getAccount() != null) {
			// accountCriteria.add(Restrictions.idEq(form.getAccount().getAccountNo()));

			Disjunction disjunction = Restrictions.disjunction();
			disjunction.add(Restrictions.eq("acct.accountNo", form.getAccount().getAccountNo()));
			disjunction.add(Restrictions.eq("p_acct.accountNo", form.getAccount().getAccountNo()));
			disjunction.add(Restrictions.eq("gp_acct.accountNo", form.getAccount().getAccountNo()));

			invoiceHeaderCriteria.add(disjunction);
		} else {
			if (!StringUtil.isBlank(form.getCustomerNo())) {
				accountCriteria.add(Restrictions.eq("custNo", form.getCustomerNo()));
			}

			if (!StringUtil.isBlank(form.getAccountName())) {
				accountCriteria.add(Restrictions.ilike("accountName", form.getAccountName(),
						MatchMode.ANYWHERE));
			}
		}

		DetachedCriteria effectiveStatusDt = DetachedCriteria.forClass(AmtbAcctStatus.class, "lastEffDt1")
				.add(Restrictions.eqProperty("amtbAccount", "acct.accountNo"))
				.add(Restrictions.le("effectiveDt", DateUtil.getCurrentDate()))
				.setProjection(Projections.max("effectiveDt"));
		Criterion latestStatus = Subqueries.propertyEq("acctSts.effectiveDt", effectiveStatusDt);
		Criterion statusIsNotPending = Restrictions.ne("acctSts.acctStatus",
				NonConfigurableConstants.ACCOUNT_STATUS_PENDING_ACTIVATION);
		// Criterion accountIsNotPending =
		// Restrictions.conjunction().add(latestStatus).add(statusIsNotPending);

		invoiceHeaderCriteria.add(latestStatus);
		invoiceHeaderCriteria.add(statusIsNotPending);
		invoiceHeaderCriteria.add(Restrictions.isNotNull("invoiceNo"));

		// Criteria for invoice
		if (form.getInvoiceNoFrom() != null) {
			invoiceHeaderCriteria.add(Restrictions.ge("invoiceNo", form.getInvoiceNoFrom()));
		}

		if (form.getInvoiceNoTo() != null) {
			invoiceHeaderCriteria.add(Restrictions.le("invoiceNo", form.getInvoiceNoTo()));
		}

		if (form.getInvoiceDateFrom() != null) {
			invoiceHeaderCriteria.add(Restrictions.ge("invoiceDate", form.getInvoiceDateFrom()));
		}

		if (form.getInvoiceDateTo() != null) {
			invoiceHeaderCriteria.add(Restrictions.le("invoiceDate", form.getInvoiceDateTo()));
		}

		if (!StringUtil.isBlank(form.getInvoiceStatus())) {
			invoiceHeaderCriteria.add(Restrictions.eq("invoiceStatus", form.getInvoiceStatus()));
		}

		invoiceHeaderCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);

		return invoiceHeaderCriteria;
	}

	private DetachedCriteria buildSearchDraftInvoiceCriteria(SearchInvoiceForm form) {
		DetachedCriteria invoiceHeaderCriteria = DetachedCriteria.forClass(BmtbDraftInvHeader.class);
		DetachedCriteria accountCriteria = invoiceHeaderCriteria.createCriteria("amtbAccountByDebtTo",
				"acct", DetachedCriteria.INNER_JOIN);

		// these aliases are used by searchNoteIssuableInvoice
		accountCriteria.createAlias("amtbAccount", "p_acct", DetachedCriteria.LEFT_JOIN);
		accountCriteria.createAlias("amtbAccount.amtbAccount", "gp_acct", DetachedCriteria.LEFT_JOIN);
		accountCriteria.createAlias("amtbAcctStatuses", "acctSts", DetachedCriteria.LEFT_JOIN);
		accountCriteria.createAlias("p_acct.amtbAcctStatuses", "p_acctSts", DetachedCriteria.LEFT_JOIN);
		accountCriteria.createAlias("gp_acct.amtbAcctStatuses", "gp_acctSts", DetachedCriteria.LEFT_JOIN);

		// Criteria for account
		if (form.getDepartment() != null) {
			accountCriteria.add(Restrictions.idEq(form.getDepartment().getAccountNo()));
		} else if (form.getDivision() != null) {
			accountCriteria.add(Restrictions.idEq(form.getDivision().getAccountNo()));
		} else if (form.getAccount() != null) {
			// accountCriteria.add(Restrictions.idEq(form.getAccount().getAccountNo()));

			Disjunction disjunction = Restrictions.disjunction();
			disjunction.add(Restrictions.eq("acct.accountNo", form.getAccount().getAccountNo()));
			disjunction.add(Restrictions.eq("p_acct.accountNo", form.getAccount().getAccountNo()));
			disjunction.add(Restrictions.eq("gp_acct.accountNo", form.getAccount().getAccountNo()));

			invoiceHeaderCriteria.add(disjunction);
		} else {
			if (!StringUtil.isBlank(form.getCustomerNo())) {
				accountCriteria.add(Restrictions.eq("custNo", form.getCustomerNo()));
			}

			if (!StringUtil.isBlank(form.getAccountName())) {
				accountCriteria.add(Restrictions.ilike("accountName", form.getAccountName(),
						MatchMode.ANYWHERE));
			}
		}

		DetachedCriteria effectiveStatusDt = DetachedCriteria.forClass(AmtbAcctStatus.class, "lastEffDt1")
				.add(Restrictions.eqProperty("amtbAccount", "acct.accountNo"))
				.add(Restrictions.le("effectiveDt", DateUtil.getCurrentDate()))
				.setProjection(Projections.max("effectiveDt"));
		Criterion latestStatus = Subqueries.propertyEq("acctSts.effectiveDt", effectiveStatusDt);
		Criterion statusIsNotPending = Restrictions.ne("acctSts.acctStatus",
				NonConfigurableConstants.ACCOUNT_STATUS_PENDING_ACTIVATION);
		// Criterion accountIsNotPending =
		// Restrictions.conjunction().add(latestStatus).add(statusIsNotPending);

		invoiceHeaderCriteria.add(latestStatus);
		invoiceHeaderCriteria.add(statusIsNotPending);
		invoiceHeaderCriteria.add(Restrictions.isNotNull("invoiceNo"));

		// Criteria for invoice
		if (form.getInvoiceNoFrom() != null) {
			invoiceHeaderCriteria.add(Restrictions.ge("invoiceNo", form.getInvoiceNoFrom()));
		}

		if (form.getInvoiceNoTo() != null) {
			invoiceHeaderCriteria.add(Restrictions.le("invoiceNo", form.getInvoiceNoTo()));
		}

		if (form.getInvoiceDateFrom() != null) {
			invoiceHeaderCriteria.add(Restrictions.ge("invoiceDate", form.getInvoiceDateFrom()));
		}

		if (form.getInvoiceDateTo() != null) {
			invoiceHeaderCriteria.add(Restrictions.le("invoiceDate", form.getInvoiceDateTo()));
		}

		if (!StringUtil.isBlank(form.getInvoiceStatus())) {
			invoiceHeaderCriteria.add(Restrictions.eq("invoiceStatus", form.getInvoiceStatus()));
		}

		invoiceHeaderCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		return invoiceHeaderCriteria;
	}

	@SuppressWarnings("rawtypes")
	public BigDecimal getLess30DaysTotalOutstandingAmount(AmtbAccount debtToAccount, Date invoiceDate) {
		DetachedCriteria invoiceHeaderCriteria = DetachedCriteria.forClass(BmtbInvoiceHeader.class);

		// Retrieve all invoiceHeaders under the account
		invoiceHeaderCriteria.add(Restrictions.eq("amtbAccountByDebtTo", debtToAccount));

		// invoice date - due date <= 30 or due date is null
		invoiceHeaderCriteria.add(Restrictions.or(
				Restrictions.sqlRestriction("(? - due_date) <= 30", invoiceDate, Hibernate.DATE),
				Restrictions.isNull("dueDate")));

		// do a sum
		invoiceHeaderCriteria.setProjection(Projections.sum("outstandingAmount"));

		List results = this.findAllByCriteria(invoiceHeaderCriteria);
		BigDecimal totalOutstanding = (BigDecimal) results.get(0);
		if (totalOutstanding == null) {
			totalOutstanding = new BigDecimal("0.00");
			return totalOutstanding;
		} else {
			return totalOutstanding;
		}
	}

	@SuppressWarnings("rawtypes")
	public BigDecimal getLess60DaysTotalOutstandingAmount(AmtbAccount debtToAccount, Date invoiceDate) {
		DetachedCriteria invoiceHeaderCriteria = DetachedCriteria.forClass(BmtbInvoiceHeader.class);

		// Retrieve all invoiceHeaders under the account
		invoiceHeaderCriteria.add(Restrictions.eq("amtbAccountByDebtTo", debtToAccount));

		// invoice date - due date > 30
		invoiceHeaderCriteria.add(Restrictions.sqlRestriction("(? - due_date) > 30", invoiceDate,
				Hibernate.DATE));

		// invoice date - due date <= 60
		invoiceHeaderCriteria.add(Restrictions.sqlRestriction("(? - due_date) <= 60", invoiceDate,
				Hibernate.DATE));

		// do a sum
		invoiceHeaderCriteria.setProjection(Projections.sum("outstandingAmount"));

		List results = this.findAllByCriteria(invoiceHeaderCriteria);
		BigDecimal totalOutstanding = (BigDecimal) results.get(0);
		if (totalOutstanding == null) {
			totalOutstanding = new BigDecimal("0.00");
			return totalOutstanding;
		} else {
			return totalOutstanding;
		}
	}

	@SuppressWarnings("rawtypes")
	public BigDecimal getLess90DaysTotalOutstandingAmount(AmtbAccount debtToAccount, Date invoiceDate) {
		DetachedCriteria invoiceHeaderCriteria = DetachedCriteria.forClass(BmtbInvoiceHeader.class);

		// Retrieve all invoiceHeaders under the account
		invoiceHeaderCriteria.add(Restrictions.eq("amtbAccountByDebtTo", debtToAccount));

		// invoice date - due date > 60
		invoiceHeaderCriteria.add(Restrictions.sqlRestriction("(? - due_date) > 60", invoiceDate,
				Hibernate.DATE));

		// invoice date - due date <= 90
		invoiceHeaderCriteria.add(Restrictions.sqlRestriction("(? - due_date) <= 90", invoiceDate,
				Hibernate.DATE));

		// do a sum
		invoiceHeaderCriteria.setProjection(Projections.sum("outstandingAmount"));

		List results = this.findAllByCriteria(invoiceHeaderCriteria);
		BigDecimal totalOutstanding = (BigDecimal) results.get(0);
		if (totalOutstanding == null) {
			totalOutstanding = new BigDecimal("0.00");
			return totalOutstanding;
		} else {
			return totalOutstanding;
		}
	}

	@SuppressWarnings("rawtypes")
	public BigDecimal getMore90DaysTotalOutstandingAmount(AmtbAccount debtToAccount, Date invoiceDate) {
		DetachedCriteria invoiceHeaderCriteria = DetachedCriteria.forClass(BmtbInvoiceHeader.class);

		// Retrieve all invoiceHeaders under the account
		invoiceHeaderCriteria.add(Restrictions.eq("amtbAccountByDebtTo", debtToAccount));

		// invoice date - due date > 90
		invoiceHeaderCriteria.add(Restrictions.sqlRestriction("(? - due_date) > 90", invoiceDate,
				Hibernate.DATE));

		// do a sum
		invoiceHeaderCriteria.setProjection(Projections.sum("outstandingAmount"));

		List results = this.findAllByCriteria(invoiceHeaderCriteria);
		BigDecimal totalOutstanding = (BigDecimal) results.get(0);
		if (totalOutstanding == null) {
			totalOutstanding = new BigDecimal("0.00");
			return totalOutstanding;
		} else {
			return totalOutstanding;
		}
	}

	public boolean isInvoiceStillOutstanding(Long invoiceNo) {
		DetachedCriteria invoiceDetailCriteria = DetachedCriteria.forClass(BmtbInvoiceDetail.class);
		DetachedCriteria invoiceSummaryCriteria = invoiceDetailCriteria.createCriteria("bmtbInvoiceSummary",
				DetachedCriteria.INNER_JOIN);
		DetachedCriteria invoiceHeaderCriteria = invoiceSummaryCriteria.createCriteria("bmtbInvoiceHeader",
				DetachedCriteria.INNER_JOIN);

		invoiceHeaderCriteria.add(Restrictions.idEq(invoiceNo));
		invoiceDetailCriteria.add(Restrictions.ne("outstandingAmount", BigDecimal.ZERO));
		invoiceDetailCriteria.setProjection(Projections.count("invoiceDetailNo"));

		Integer countValue = (Integer) this.findAllByCriteria(invoiceHeaderCriteria).get(0);
		if (countValue.intValue() == 0)
			return false;
		else
			return true;
	}

	@SuppressWarnings({ "unchecked", "unused" })
	public List<BmtbInvoiceHeader> getInvoicesForPrinting(Integer billGenRequestNo, Integer custNoFrom,
			Integer custNoTo, Long invoiceNoFrom, Long invoiceNoTo, boolean printNoActivity) {
		DetachedCriteria criteria = DetachedCriteria.forClass(BmtbInvoiceHeader.class);
		DetachedCriteria fileCriteria = criteria
				.createCriteria("bmtbInvoiceFile", DetachedCriteria.LEFT_JOIN);
		DetachedCriteria billGenReqCriteria = criteria.createCriteria("bmtbBillGenReq",
				DetachedCriteria.LEFT_JOIN);
		DetachedCriteria acctCriteria = criteria.createCriteria("amtbAccountByAccountNo", "acct",
				DetachedCriteria.LEFT_JOIN);
		DetachedCriteria subscribeTo = acctCriteria.createCriteria("amtbSubscTos", DetachedCriteria.LEFT_JOIN);
		DetachedCriteria prodType = subscribeTo.createCriteria("comp_id.pmtbProductType", DetachedCriteria.LEFT_JOIN);
		DetachedCriteria pAcctCriteria = acctCriteria.createCriteria("amtbAccount", "pacct",
				DetachedCriteria.LEFT_JOIN);
		DetachedCriteria psubscribeTo = pAcctCriteria.createCriteria("amtbSubscTos", DetachedCriteria.LEFT_JOIN);
		DetachedCriteria pprodType = psubscribeTo.createCriteria("comp_id.pmtbProductType", DetachedCriteria.LEFT_JOIN);
		DetachedCriteria gpAcctCriteria = pAcctCriteria.createCriteria("amtbAccount", "gpacct",
				DetachedCriteria.LEFT_JOIN);
		DetachedCriteria gpsubscribeTo = gpAcctCriteria.createCriteria("amtbSubscTos", DetachedCriteria.LEFT_JOIN);
		DetachedCriteria gprodType = gpsubscribeTo.createCriteria("comp_id.pmtbProductType", DetachedCriteria.LEFT_JOIN);

		if (billGenRequestNo != null) {
			billGenReqCriteria.add(Restrictions.idEq(billGenRequestNo));
			billGenReqCriteria.add(Restrictions.isNotNull("reqNo"));
		}

		criteria.add(Restrictions.isNotNull("invoiceNo"));

		if (invoiceNoFrom != null)
			criteria.add(Restrictions.ge("invoiceNo", invoiceNoFrom));
		if (invoiceNoTo != null)
			criteria.add(Restrictions.le("invoiceNo", invoiceNoTo));

		if (custNoFrom != null && custNoTo != null) {
			Disjunction disjunction = Restrictions.disjunction();

			Conjunction conjunction1 = Restrictions.conjunction();
			conjunction1.add(Restrictions.sqlRestriction("cast(acct3_.cust_No as number) >= " + custNoFrom));
			conjunction1.add(Restrictions.sqlRestriction("cast(acct3_.cust_No as number) <= " + custNoTo));
			conjunction1.add(Restrictions.or(
					Restrictions.eq("acct.einvoiceFlag", NonConfigurableConstants.BOOLEAN_NO),
					Restrictions.isNull("acct.einvoiceFlag")));

			Conjunction conjunction2 = Restrictions.conjunction();
			conjunction2.add(Restrictions.sqlRestriction("cast(pacct6_.cust_No as number) >= " + custNoFrom));
			conjunction2.add(Restrictions.sqlRestriction("cast(pacct6_.cust_No as number) <= " + custNoTo));
			conjunction2.add(Restrictions.or(
					Restrictions.eq("pacct.einvoiceFlag", NonConfigurableConstants.BOOLEAN_NO),
					Restrictions.isNull("pacct.einvoiceFlag")));

			Conjunction conjunction3 = Restrictions.conjunction();
			conjunction3
					.add(Restrictions.sqlRestriction("cast(gpacct9_.cust_No as number) >= " + custNoFrom));
			conjunction3.add(Restrictions.sqlRestriction("cast(gpacct9_.cust_No as number) <= " + custNoTo));
			conjunction3.add(Restrictions.or(
					Restrictions.eq("gpacct.einvoiceFlag", NonConfigurableConstants.BOOLEAN_NO),
					Restrictions.isNull("gpacct.einvoiceFlag")));

			disjunction.add(conjunction1);
			disjunction.add(conjunction2);
			disjunction.add(conjunction3);

			criteria.add(disjunction);
		}

		// Enhancement for E-Invoice
		Conjunction conjunction = Restrictions.conjunction();
		conjunction.add(Restrictions.or(
				Restrictions.eq("acct.einvoiceFlag", NonConfigurableConstants.BOOLEAN_NO),
				Restrictions.isNull("acct.einvoiceFlag")));

		conjunction.add(Restrictions.or(
				Restrictions.eq("pacct.einvoiceFlag", NonConfigurableConstants.BOOLEAN_NO),
				Restrictions.isNull("pacct.einvoiceFlag")));

		conjunction.add(Restrictions.or(
				Restrictions.eq("gpacct.einvoiceFlag", NonConfigurableConstants.BOOLEAN_NO),
				Restrictions.isNull("gpacct.einvoiceFlag")));

		criteria.add(conjunction);


		// Enhancement for Outsource Print 3/12/2016 -START
		//if outsource = yes, dont print !!
		Conjunction outsourceConjunction = Restrictions.conjunction();
		outsourceConjunction.add(Restrictions.or(
				Restrictions.eq("acct.outsourcePrintingFlag", NonConfigurableConstants.BOOLEAN_NO),
				Restrictions.isNull("acct.outsourcePrintingFlag")));

		outsourceConjunction.add(Restrictions.or(
				Restrictions.eq("pacct.outsourcePrintingFlag", NonConfigurableConstants.BOOLEAN_NO),
				Restrictions.isNull("pacct.outsourcePrintingFlag")));

		outsourceConjunction.add(Restrictions.or(
				Restrictions.eq("gpacct.outsourcePrintingFlag", NonConfigurableConstants.BOOLEAN_NO),
				Restrictions.isNull("gpacct.outsourcePrintingFlag")));

		criteria.add(outsourceConjunction);
		// Enhancement for Outsource Print 3/12/2016 -END
		
		if (!printNoActivity) {
			Conjunction conjunction4 = Restrictions.conjunction();
			conjunction4.add(Restrictions.eq("prevPayment", BigDecimal.ZERO));
			conjunction4.add(Restrictions.eq("newTxn", BigDecimal.ZERO));
			conjunction4.add(Restrictions.eq("newBalance", BigDecimal.ZERO));
			conjunction4.add(Restrictions.eq("less30Days", BigDecimal.ZERO));
			conjunction4.add(Restrictions.eq("less60Days", BigDecimal.ZERO));
			conjunction4.add(Restrictions.eq("less90Days", BigDecimal.ZERO));
			conjunction4.add(Restrictions.eq("more90Days", BigDecimal.ZERO));
			// Geok Hua: need not check if there is rewards activity.
			// conjunction4.add(Restrictions.eq("prevRewardsPts", new Integer(0)));
			// conjunction4.add(Restrictions.eq("redeemedRewardsPts", new Integer(0)));
			// conjunction4.add(Restrictions.eq("newRewardsPts", new Integer(0)));
			// conjunction4.add(Restrictions.eq("totalRewardsPts", new Integer(0)));
			// Lay Hong: confirm not to print invoice if the invoice only has excess amount/deposit) 26th Jan
			// 2010
			// conjunction4.add(Restrictions.eq("accountDeposit", BigDecimal.ZERO));
			// conjunction4.add(Restrictions.eq("accountExcess", BigDecimal.ZERO));
			criteria.add(Restrictions.not(conjunction4));
		}

		return this.findAllByCriteria(criteria);
	}

	// added by on 12/12/2011
	@SuppressWarnings("unchecked")
	public BmtbInvoiceHeader getInvoiceByInvoiceNo(Long invoiceNo) {
		DetachedCriteria invoiceHeaderCriteria = DetachedCriteria.forClass(BmtbInvoiceHeader.class);
		DetachedCriteria accountCriteria = invoiceHeaderCriteria.createCriteria("amtbAccountByDebtTo",
				DetachedCriteria.LEFT_JOIN);
		DetachedCriteria parentAccountCriteria = accountCriteria.createCriteria("amtbAccount",
				DetachedCriteria.LEFT_JOIN);
		@SuppressWarnings("unused")
		DetachedCriteria grandParentAccountCriteria = parentAccountCriteria.createCriteria("amtbAccount",
				DetachedCriteria.LEFT_JOIN);

		invoiceHeaderCriteria.add(Restrictions.eq("invoiceNo", invoiceNo));

		// invoiceHeaderCriteria.addOrder(Order.asc("invoiceNo"));
		invoiceHeaderCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);

		List<BmtbInvoiceHeader> results = this.findAllByCriteria(invoiceHeaderCriteria);
		if (results.isEmpty())
			return null;
		else
			return results.get(0);
	}
} 