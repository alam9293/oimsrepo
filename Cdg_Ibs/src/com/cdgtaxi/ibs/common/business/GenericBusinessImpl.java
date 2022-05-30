package com.cdgtaxi.ibs.common.business;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.List;

import org.hibernate.HibernateException;
import org.springframework.orm.hibernate3.HibernateOptimisticLockingFailureException;
import org.springframework.transaction.annotation.Transactional;

import com.cdgtaxi.ibs.common.NonConfigurableConstants;
import com.cdgtaxi.ibs.common.dao.DaoHelper;
import com.cdgtaxi.ibs.common.dao.Sequence;
import com.cdgtaxi.ibs.common.model.AmtbAccount;
import com.cdgtaxi.ibs.common.model.BmtbInvoiceDetail;
import com.cdgtaxi.ibs.common.model.BmtbInvoiceHeader;
import com.cdgtaxi.ibs.common.model.BmtbInvoiceSummary;
import com.cdgtaxi.ibs.util.DateUtil;

@Transactional(rollbackFor = {Exception.class, HibernateException.class, HibernateOptimisticLockingFailureException.class})
public class GenericBusinessImpl implements GenericBusiness{
	protected DaoHelper daoHelper;
	protected BusinessHelper businessHelper;

	public void setBusinessHelper(BusinessHelper businessHelper) {
		this.businessHelper = businessHelper;
	}
	
	public void setDaoHelper(DaoHelper daoHelper) {
		this.daoHelper = daoHelper;
	}
	
	public Object get(Class arg0, Serializable arg1){
		return daoHelper.getGenericDao().get(arg0, arg1);
	}
	
	public List getAll(Class arg0){
		return daoHelper.getGenericDao().getAll(arg0);
	}
	
	public Serializable save(Object entity){
		return daoHelper.getGenericDao().save(entity);
	}
	
	public void saveAll(Collection<?> entities){
		for(Object entity: entities){
			daoHelper.getGenericDao().save(entity);
		}
	}
	
	public void updateAll(Collection<?> entities){
		for(Object entity: entities){
			daoHelper.getGenericDao().update(entity);
		}
	}
	
	
	public Serializable save(Object entity, String createdBy){
		return daoHelper.getGenericDao().save(entity, createdBy);
	}
	
	public void update(Object entity){
		daoHelper.getGenericDao().update(entity);
	}
	
	public void update(Object entity, String updatedBy){
		daoHelper.getGenericDao().update(entity, updatedBy);
	}
	
	public void delete(Object entity){
		daoHelper.getGenericDao().delete(entity);
	}
	
	public void evict(Object entity) {
		daoHelper.getGenericDao().evict(entity);
	}
	
	public int getCount(Object entity){
		return daoHelper.getGenericDao().getCount(entity);
	}
	
	public boolean isExists(Object entity){
		return daoHelper.getGenericDao().isExists(entity);
	}
	
	public List getByExample(Object entity){
		return daoHelper.getGenericDao().getByExample(entity);
	}
	
	public Object merge(Object entity){
		return daoHelper.getGenericDao().merge(entity);
	}
	
	public Long generateMiscInvoice(BmtbInvoiceHeader header) {
		Timestamp now = DateUtil.getCurrentTimestamp();
		java.sql.Date today = DateUtil.getCurrentDate();
		String username = header.getUpdatedBy();
		
		AmtbAccount acct = header.getAmtbAccountByAccountNo();
		//		acct = daoHelper.getAccountDao().retrieveAccount(acct.getCustNo());

		//		AmtbAccount billableAcct = header.getAmtbAccountByDebtTo();
		//		billableAcct = daoHelper.getAccountDao().retrieveAccount(billableAcct.getCustNo());
		//		FmtbEntityMaster entity = billableAcct.getFmtbArContCodeMaster().getFmtbEntityMaster();

		header.setInvoiceNo(daoHelper.getInvoiceDao().getNextSequenceNo(Sequence.INVOICE_NO_SEQUENCE).longValue());
		//		header.setAmtbAccountByAccountNo(acct);
		//		header.setAmtbAccountByDebtTo(acct);
		//		header.setOutstandingAmount(depositAmt);
		//		header.setCustomerId(billableAcct.getCustNo());
		//		header.setCustomerName(acct.getAccountName());
		//		header.setInvoiceDate(DateUtil.convertUtilDateToSqlDate(invoiceDate));
		header.setInvoiceFormat(NonConfigurableConstants.INVOICE_FORMAT_MISC);
		header.setInvoiceStatus(NonConfigurableConstants.INVOICE_STATUS_OUTSTANDING);
		//		header.setNewBalance(depositAmt);
		//		header.setNewTxn(depositAmt);
		//		header.setDueDate(null); //??? due date is null because it is a COD so deposit invoice will never due
		header.setStatementPeriodFromDate(today); // ???
		header.setStatementPeriodToDate(today); // ???
		BigDecimal latePaymentInterest = daoHelper.getAccountDao().getLatestLatePaymentRate(acct, header.getInvoiceDate());
		header.setLatePaymentInterest(latePaymentInterest);
		header.setTxnCutoffDate(today); // ???
		//		header.setEntityName(entity.getEntityName());
		//		header.setEntityArea(entity.getEntityArea());
		//		header.setEntityBlock(entity.getEntityBlock());
		//		header.setEntityBuilding(entity.getEntityBuilding());
		//		header.setEntityCity(entity.getEntityCity());
		//		header.setEntityCountry(entity.getMstbMasterTable().getMasterValue());
		//		header.setEntityPostal(entity.getEntityPostal());
		//		header.setEntityState(entity.getEntityState());
		//		header.setEntityStreet(entity.getEntityStreet());
		//		header.setEntityRcbNo(entity.getEntityRcbNo());
		//		header.setEntityGstNo(entity.getEntityGstNo());
		header.setUpdatedDt(now);

		BigDecimal grandTotal = BigDecimal.ZERO;
		for (BmtbInvoiceSummary summary : header.getBmtbInvoiceSummaries()) {
			BigDecimal qty = new BigDecimal(summary.getQuantity());
			BigDecimal price = summary.getUnitPrice().multiply(qty);
			BigDecimal totalAmount =
				price.subtract(summary.getDiscount()).add(summary.getGst());
			grandTotal = grandTotal.add(totalAmount);

			summary.setBmtbInvoiceHeader(header);
			summary.setAmtbAccount(acct);
			summary.setTotalNew(totalAmount);
			summary.setBalance(totalAmount);
			summary.setAdminFee(new BigDecimal("0.00"));
			summary.setNewTxn(price);
			//			summary.setSummaryGroup(acct.getAccountName());
			//			summary.setSummaryGroupName("MISCELLANEOUS");
//			summary.setSummaryType(NonConfigurableConstants.INVOICE_SUMMARY_DETAIL_TYPE_MISC);
			summary.setDisplayPriority1(1);
			summary.setDisplayPriority2(1);

			BmtbInvoiceDetail detail = summary.getBmtbInvoiceDetails().iterator().next();
			summary.setSummaryType(detail.getInvoiceDetailType());
			detail.setInvoiceDetailName(summary.getSummaryName());
			//			detail.setAccountName(acct.getAccountName());
			detail.setAmtbAccount(acct);
			detail.setBmtbInvoiceSummary(summary);
			detail.setOutstandingAmount(totalAmount);
			detail.setNewTxn(price);
			detail.setTotalNew(totalAmount);
			detail.setUpdatedBy(username);
			detail.setUpdatedDt(now);
			detail.setAdminFee(summary.getAdminFee());
			detail.setGst(summary.getGst());
		}

		header.setOutstandingAmount(grandTotal);
		header.setNewBalance(grandTotal);
		header.setNewTxn(grandTotal);

		return (Long) save(header, username);
	}
	
	public BigDecimal getNextSequenceNo(String sequenceName) 
	{
		return this.daoHelper.getGenericDao().getNextSequenceNo(sequenceName);
	}
}