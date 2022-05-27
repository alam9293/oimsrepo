package com.cdgtaxi.ibs.common.dao;

import java.math.BigDecimal;
import java.sql.Date;
import java.text.ParseException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import com.cdgtaxi.ibs.common.NonConfigurableConstants;
import com.cdgtaxi.ibs.common.model.ImtbItem;
import com.cdgtaxi.ibs.common.model.IttbTripsTxnError;
import com.cdgtaxi.ibs.common.model.TmtbNonBillableTxnCrca;
import com.cdgtaxi.ibs.master.model.MstbMasterTable;
import com.cdgtaxi.ibs.master.model.MstbReportFormatMap;
import com.cdgtaxi.ibs.util.StringUtil;
import com.google.common.base.Strings;

@SuppressWarnings({"unchecked", "rawtypes"})
public class ReportDaoHibernate extends GenericDaoHibernate implements ReportDao {
	
	public List<Object[]> getCustomerAgingDetail(String accountNo, String accountName, String entity,
			String arControl, String salesPerson, String outstandingAmount, String daysLate, String type,
			String sortBy){
		
		//Create Named Params & Values
		String[] paramNames = new String[]{"accountNo","accountName","entity","arControl",
				"salesPerson","outstandingAmount","daysLate","type","sortBy"};
		String[] values = new String[]{accountNo, accountName == null ? "%" : accountName+"%",
				entity, arControl, salesPerson, outstandingAmount, daysLate, type, sortBy};

		//Query using named query
		return findResultByNamedQueryAndNamedParam("customerAgingDetail", paramNames, values);
		//return results;
	}
	
	public List<Object[]> getCustomerAgingSummary(String accountNo, String accountName, String entityNo,
			String arControlCodeNo, String outstandingAmount, String type, String sortBy){
		
		//Create Named Params & Values
		String[] paramNames = new String[]{"accountNo","accountName","entityNo","arControlCodeNo",
				"outstandingAmount","type","sortBy"};
		String[] values = new String[]{accountNo, accountName == null ? "%" : accountName+"%",
				entityNo, arControlCodeNo, outstandingAmount, type, sortBy};

		//Query using named query
		return findResultByNamedQueryAndNamedParam("customerAgingSummary", paramNames, values);
	}
	
	public List<Object[]> getSoftCopyInvoiceAndTripsDetailByInvoiceNoCustomize(String accountNo, 
			String invoiceMonthYear, String invoiceNo, String sortBy){
		
		//Create Named Params & Values
		String[] paramNames = new String[]{"accountNo","invoiceNo","invoiceMonth","sortBy"};
		String[] values = new String[]{accountNo, invoiceNo, invoiceMonthYear, sortBy};

		//Query using named query
		return findResultByNamedQueryAndNamedParam("softCopyInvoiceAndTripsDetailByInvoiceNoCustomize", paramNames, values);
	}
	
	public List<Object[]> getSoftCopyInvoiceAndTripsDetailByInvoiceNoCSV(String selectedAccountNo, 
			String invoiceMonthYear, String invoiceNo, String chargeTo, String sortBy){
		
		String debtToAccountNo = "-9999", accountNo = "-9999";
		if(chargeTo==null || chargeTo.equals(NonConfigurableConstants.BOOLEAN_NO))
			accountNo = selectedAccountNo;
		else
			debtToAccountNo = selectedAccountNo;
		
		//Create Named Params & Values
		String[] paramNames = new String[]{"debtToAccountNo","accountNo","invoiceNo","invoiceMonth","sortBy"};
		String[] values = new String[]{debtToAccountNo,accountNo, invoiceNo, invoiceMonthYear, sortBy};

		//Query using named query
		return findResultByNamedQueryAndNamedParam("softCopyInvoiceAndTripsDetailByInvoiceNoCSV", paramNames, values);
	}
	
	public List<Object[]> getSoftCopyInvoiceAndTripsDetailByTransactionStatus(String accountNo, 
			String accountName, String cardNo, String txnStatus, String productTypeId,
			String tripStartDate, String tripEndDate, String sortBy){
		
		//Create Named Params & Values
		String[] paramNames = new String[]{"accountNo","accountName","cardNo","txnStatus","prodTypeId","tripStartDate","tripEndDate","sortBy"};
		String[] values = new String[]{accountNo, accountName, cardNo, txnStatus, productTypeId, 
				tripStartDate, tripEndDate, sortBy};

		//Query using named query
		return findResultByNamedQueryAndNamedParam("softCopyInvoiceAndTripsDetailByStatusNo", paramNames, values);
	}

	public List<MstbMasterTable> getASCTDRConfig() {
		
		DetachedCriteria criteria = DetachedCriteria.forClass(MstbMasterTable.class);
		criteria.add(Restrictions.sqlRestriction(" (trim(this_.master_TYPE) = 'ASCTDR' )   and  this_.MASTER_STATUS = 'A' "));
		criteria.addOrder(Order.asc("masterValue"));
		
		return this.findAllByCriteria(criteria);
	}
	public List<MstbReportFormatMap> getReportFormatMap(Long resourceId){
		DetachedCriteria criteria = DetachedCriteria.forClass(MstbReportFormatMap.class);
		criteria.createCriteria("satbResource", "resource", DetachedCriteria.INNER_JOIN);
		criteria.add(Restrictions.eq("resource.rsrcId", resourceId));
		
		return this.findAllByCriteria(criteria);
	}
	public List<Object[]> getCustomerReport(String acctTypeNo, String acctNo, String acctName, String acctStatus, String productTypeId, String industryNo, String joinStart, String joinEnd, String terminateStart, String terminateEnd, String salespersonNo, String sortBy, String entityNo){
		String[] paramNames = new String[]{"acctTypeNo", "acctNo", "acctName", "acctStatus", "productTypeId", "industryNo", "joinStart", "joinEnd", "terminateStart", "terminateEnd", "salespersonNo", "sortBy", "entityNo"};
		String[] values = new String[]{acctTypeNo, acctNo, acctName, acctStatus, productTypeId, industryNo, joinStart, joinEnd, terminateStart, terminateEnd, salespersonNo, sortBy, entityNo};
		return findResultByNamedQueryAndNamedParam("customerReport", paramNames, values);
	}
	public List<Object[]> getReceiptByPeriodDetailed(String receiptStart, String receiptEnd, String cancelStart, String cancelEnd, String acctNo, String acctName, String invoiceNo, String receiptNo, String paymentMode, String order, String entityNo, String salespersonNo){
		String[] paramNames = new String[]{"receiptStart", "receiptEnd", "cancelStart", "cancelEnd", "acctNo", "acctName", "invoiceNo", "receiptNo", "paymentMode", "order", "entityNo", "salespersonNo"};
		String[] values = new String[]{receiptStart, receiptEnd, cancelStart, cancelEnd, acctNo, acctName, invoiceNo, receiptNo, paymentMode, order, entityNo, salespersonNo};
		return findResultByNamedQueryAndNamedParam("customerReport", paramNames, values);
	}
	public List<Object[]> getReceiptByPeriodSummaryDate(String receiptStart, String receiptEnd, String paymentMode, String entityNo, String salespersonNo){
		String[] paramNames = new String[]{"receiptStart", "receiptEnd", "paymentMode", "entityNo", "salespersonNo"};
		String[] values = new String[]{receiptStart, receiptEnd, paymentMode, entityNo, salespersonNo};
		return findResultByNamedQueryAndNamedParam("receiptByPeriodSummaryDate", paramNames, values);
	}
	public List<Object[]> getReceiptByPeriodSummaryPayment(String receiptStart, String receiptEnd, String paymentMode, String entityNo, String salespersonNo){
		String[] paramNames = new String[]{"receiptStart", "receiptEnd", "paymentMode", "entityNo", "salespersonNo"};
		String[] values = new String[]{receiptStart, receiptEnd, paymentMode, entityNo, salespersonNo};
		return findResultByNamedQueryAndNamedParam("receiptByPeriodSummaryPayment", paramNames, values);
	}
	public List<Object[]> getCreditDebitNote(String noteStart, String noteEnd, String cancelStart, String cancelEnd, String acctNo, String acctName, String noteType, String order, String entityNo){
		String[] paramNames = new String[]{"noteStart", "noteEnd", "cancelStart", "cancelEnd", "acctNo", "acctName", "noteType", "order", "entityNo"};
		String[] values = new String[]{noteStart, noteEnd, cancelStart, cancelEnd, acctNo, acctName, noteType, order, entityNo};
		return findResultByNamedQueryAndNamedParam("creditDebitNote", paramNames, values);
	}
	public List<Object[]> getCreditBalance(String creditBalance, String acctNo, String acctName, String divNo, String deptNo, String productType, String salespersonNo, String sort){
		String[] paramNames = new String[]{"creditBalance", "acctNo", "acctName", "divNo", "deptNo", "productType", "salespersonNo", "sort"};
		String[] values = new String[]{creditBalance, acctNo, acctName, divNo, deptNo, productType, salespersonNo, sort};
		return findResultByNamedQueryAndNamedParam("creditBalance", paramNames, values);
	}
	public List<Object[]> getCardInProduction(String productType, String issueStart, String issueEnd, String renewStart, String renewEnd, String replaceStart, String replaceEnd, String cardStart, String cardEnd, String cardStatus, String sortBy){
		String[] paramNames = new String[]{"productType", "issueStart", "issueEnd", "renewStart", "renewEnd", "replaceStart", "replaceEnd", "cardStart", "cardEnd", "cardStatus", "sortBy"};
		String[] values = new String[]{productType, issueStart, issueEnd, renewStart, renewEnd, replaceStart, replaceEnd, cardStart, cardEnd, cardStatus, sortBy};
		return findResultByNamedQueryAndNamedParam("cardInProduction", paramNames, values);
	}
	public List<Object[]> getCustomerDepositSummary(String acctNo, String acctName, String entity){
		String[] paramNames = new String[]{"acctNo", "acctName", "entity"};
		String[] values = new String[]{acctNo, acctName, entity};
		return findResultByNamedQueryAndNamedParam("customerDepositSummary", paramNames, values);
	}
	public List<Object[]> getCustomerDepositDetailedReceipts(String depositStart, String depositEnd, String receiptStart, String receiptEnd, String acctNo, String acctName, String entity, String depositStatus, String sort){
		String[] paramNames = new String[]{"depositStart", "depositEnd", "receiptStart", "receiptEnd", "acctNo", "acctName", "entity", "depositStatus", "sort"};
		String[] values = new String[]{depositStart, depositEnd, receiptStart, receiptEnd, acctNo, acctName, entity, depositStatus, sort};
		return findResultByNamedQueryAndNamedParam("customerDepositDetailedReceipts", paramNames, values);
	}
	public List<Object[]> getCustomerDepositDetailedRefunds(String depositStart, String depositEnd, String receiptStart, String receiptEnd, String acctNo, String acctName, String entity){
		String[] paramNames = new String[]{"depositStart", "depositEnd", "receiptStart", "receiptEnd", "acctNo", "acctName", "entity"};
		String[] values = new String[]{depositStart, depositEnd, receiptStart, receiptEnd, acctNo, acctName, entity};
		return findResultByNamedQueryAndNamedParam("customerDepositDetailedRefunds", paramNames, values);
	}
	public List<Object[]> getDailyChequeDepositListing(String receivedStartDate, String receivedEndDate, String entity, String quickCheckDeposit, String order){
		String[] paramNames = new String[]{"receivedStartDate", "receivedEndDate", "entity", "quickCheckDeposit", "order"};
		String[] values = new String[]{receivedStartDate, receivedEndDate, entity, quickCheckDeposit, order};
		return findResultByNamedQueryAndNamedParam("dailyChequeDepositListing", paramNames, values);
	}
	public List<Object[]> getCustomerUsage(String accountNo, String accountName, String invoiceStartMonth, String invoiceEndMonth, String productType, String entityNo){
		String[] paramNames = new String[]{"accountNo", "accountName", "invoiceStartMonth", "invoiceEndMonth", "productType", "entityNo"};
		String[] values = new String[]{accountNo, accountName, invoiceStartMonth, invoiceEndMonth, productType, entityNo};
		return findResultByNamedQueryAndNamedParam("customerUsage", paramNames, values);
	}
	public List<Object[]> getCustomerUsageComparsion(String invoiceMonth, String productType, String sort, String entityNo){
		String[] paramNames = new String[]{"invoiceMonth", "productType", "sort", "entityNo"};
		String[] values = new String[]{invoiceMonth, productType, sort, entityNo};
		return findResultByNamedQueryAndNamedParam("customerUsageComparsion", paramNames, values);
	}
	public List<Object[]> getRevenueSummary(String entityNo, String invoiceStart, String invoiceEnd){
		String[] paramNames = new String[]{"entityNo", "invoiceStart", "invoiceEnd"};
		String[] values = new String[]{entityNo, invoiceStart, invoiceEnd};
		return findResultByNamedQueryAndNamedParam("revenueSummary", paramNames, values);
	}
	public List<Object[]> getRevenueMisc(String entityNo, String invoiceStart, String invoiceEnd){
		String[] paramNames = new String[]{"entityNo", "invoiceStart", "invoiceEnd"};
		String[] values = new String[]{entityNo, invoiceStart, invoiceEnd};
		return findResultByNamedQueryAndNamedParam("revenueMisc", paramNames, values);
	}
	public List<Object[]> getRevenueHeader(){
		String[] paramNames = new String[]{};
		String[] values = new String[]{};
		return findResultByNamedQueryAndNamedParam("revenueHeader", paramNames, values);
	}
	public List<String> getRevenueHeaderForProductTypeId(String entityNo, String invoiceStart, String invoiceEnd){
		String[] paramNames = new String[]{"entityNo", "invoiceStart", "invoiceEnd"};
		String[] values = new String[]{entityNo, invoiceStart, invoiceEnd};
		return findResultByNamedQueryAndNamedParam("revenueHeaderForProductTypeId", paramNames, values);
	}
	public Map<String, String> getRevenueHeaderForMisc(){
		List<Object[]> results = (List<Object[]>) this.findResultByNamedQueryAndNamedParam("revenueHeaderForMisc", new String[]{}, new String[]{});
		Map resultMap = new LinkedHashMap<String, String>();
		for(Object[] result : results){
			resultMap.put(result[0], result[1]);
		}
		return resultMap;
	}
	public Map<String, Object[]> getRevenueProductTypeTripInfos(String invoiceHeaderNo, String invoiceStart, String invoiceEnd){
		String[] paramNames = new String[]{"invoiceHeaderNo", "invoiceStart", "invoiceEnd"};
		String[] values = new String[]{invoiceHeaderNo, invoiceStart, invoiceEnd};
		List<Object[]> results = (List<Object[]>) this.findResultByNamedQueryAndNamedParam("revenueProductTypeTripInfos", paramNames, values);
		
		Map<String, Object[]> mappedValues = new HashMap<String, Object[]>();
		for(Object[] dataArray : results){
			mappedValues.put((String)dataArray[0], dataArray);
		}
		return mappedValues;
	}
	public Map<String, Object[]> getRevenueProductTypePrepaidInfos(String invoiceHeaderNo, String invoiceStart, String invoiceEnd){
		String[] paramNames = new String[]{"invoiceHeaderNo", "invoiceStart", "invoiceEnd"};
		String[] values = new String[]{invoiceHeaderNo, invoiceStart, invoiceEnd};
		List<Object[]> results = (List<Object[]>) this.findResultByNamedQueryAndNamedParam("revenueProductTypePrepaidInfos", paramNames, values);
		
		Map<String, Object[]> mappedValues = new HashMap<String, Object[]>();
		for(Object[] dataArray : results){
			mappedValues.put((String)dataArray[0], dataArray);
		}
		return mappedValues;
	}
	
	public List<Object[]> getSalesReportBySalesperson(String invoiceStart, String invoiceEnd, String productType, String sort, String entityNo){
		String[] paramNames = new String[]{"invoiceStart", "invoiceEnd", "productType", "sort", "entityNo"};
		String[] values = new String[]{invoiceStart, invoiceEnd, productType, sort, entityNo};
		List results = this.findResultByNamedQueryAndNamedParam("salesReportBySalesperson", paramNames, values);
		return results;
	}
	public List<Object[]> getBankChargebackReport(String chargebackStart, String chargebackEnd, String batchStart, String batchEnd, String entityNo, String providerNo, String acquirerNo, String sort){
		String[] paramNames = new String[]{"chargebackStart", "chargebackEnd", "batchStart", "batchEnd", "entityNo", "providerNo", "acquirerNo", "sort"};
		String[] values = new String[]{chargebackStart, chargebackEnd, batchStart, batchEnd, entityNo, providerNo, acquirerNo, sort};
		List results = this.findResultByNamedQueryAndNamedParam("bankChargebackReport", paramNames, values);
		return results;
	}
	public List<Object[]> getCashlessAgingReportDetailed(String agingDate, String batchStart, String batchEnd, String entityNo, String providerNo, String acquirerNo, String sort){
		String[] paramNames = new String[]{"agingDate", "batchStart", "batchEnd", "entityNo", "providerNo", "acquirerNo", "sort"};
		String[] values = new String[]{agingDate, batchStart, batchEnd, entityNo, providerNo, acquirerNo, sort};
		List results = this.findResultByNamedQueryAndNamedParam("cashlessAgingReportDetailed", paramNames, values);
		return results;
	}
	public List<Object[]> getCashlessAgingReportSummary(String agingDate, String batchStart, String batchEnd, String entityNo, String providerNo, String acquirerNo){
		String[] paramNames = new String[]{"agingDate", "batchStart", "batchEnd", "entityNo", "providerNo", "acquirerNo"};
		String[] values = new String[]{agingDate, batchStart, batchEnd, entityNo, providerNo, acquirerNo};
		List results = this.findResultByNamedQueryAndNamedParam("cashlessAgingReportSummary", paramNames, values);
		return results;
	}
	public List<Object[]> getCashlessBankCollectionSummary(String batchNo, String creditStart, String creditEnd, String batchStart, String batchEnd, String entityNo, String acquirerNo, String paymentTypeNo, String providerNo){
		String[] paramNames = new String[]{"creditStart", "creditEnd", "batchStart", "batchEnd", "entityNo", "acquirerNo", "paymentTypeNo", "providerNo"};
		String[] values = new String[]{creditStart, creditEnd, batchStart, batchEnd, entityNo, acquirerNo, paymentTypeNo, providerNo};
		String sql = "select acquirer.NAME," +
				" to_char(batch.SETTLEMENT_DATE, 'dd/mm/yyyy') as batch_date," +
				" batch.BATCH_NO, count(txn.TXN_ID) as total_records, sum(txn.TOTAL) as total_amt," +
				" to_char(pymt.CREDIT_DATE, 'dd/mm/yyyy') as CREDIT_DATE," +
				" trim(to_char(count(txn.TXN_ID) - count(case when txn.STATUS = 'R' then txn.TXN_ID else null end), '999,999,990')) as credit_record," +
				" trim(to_char(sum(case when txn.STATUS = 'R' then 0 else txn.total end), '999,999,990.00')) as credit_amt," +
				" trim(to_char(round(avg(nvl(pymt.MDR_PERCENTAGE, 0)) * 0.01 * sum(case when txn.STATUS = 'C' then txn.total else 0 end), 2), '999,999,990.00')) as mdr," +
				" trim(to_char(avg(nvl(pymt.MDR_PERCENTAGE, 0)), '999,999,990.00')) as mdr_percent," +
				" trim(to_char(sum(case when txn.STATUS = 'R' then 0 else txn.total end) - round(avg(nvl(pymt.MDR_PERCENTAGE, 0)) * 0.01 * sum(case when txn.STATUS = 'C' then txn.total else 0 end), 2), '999,999,990.00')) as net_amt," +
				" trim(to_char(count(case when txn.STATUS = 'R' then txn.TXN_ID else null end), '999,999,990')) as reject_records," +
				" trim(to_char(sum(case when txn.STATUS = 'R' then txn.total else 0 end), '999,999,990.00')) as reject_amt," +
				" trim(to_char(sum(txn.TOTAL) - (sum(case when txn.STATUS = 'R' then 0 else txn.total end)) - sum(case when txn.STATUS = 'R' then txn.total else 0 end), '999,999,990.00')) as os_amt," +
				" trim(to_char(sum(nvl(txn.COMMISSION_AMT, 0)), '999,999,990.00')) as commission_amt " +
				" from TMTB_NON_BILLABLE_TXN txn" +
				" inner join TMTB_NON_BILLABLE_BATCH batch on txn.BATCH_ID = batch.BATCH_ID" +
				" inner join MSTB_ACQUIRER acquirer on batch.ACQUIRER_NO = acquirer.ACQUIRER_NO" +
				" inner join MSTB_ACQUIRER_PYMT_TYPE pymt_type on txn.PYMT_TYPE_NO = pymt_type.PYMT_TYPE_NO" +
				" inner join FMTB_NON_BILLABLE_MASTER non_master on non_master.SERVICE_PROVIDER = txn.SERVICE_PROVIDER and non_master.PYMT_TYPE_MASTER_NO = pymt_type.MASTER_NO" +
				" inner join (select MASTER_NO, max(EFFECTIVE_DATE) as EFFECTIVE_DATE from FMTB_NON_BILLABLE_DETAIL where EFFECTIVE_DATE < current_date group by MASTER_NO) last_non_detail on non_master.MASTER_NO = last_non_detail.MASTER_NO" +
				" inner join FMTB_NON_BILLABLE_DETAIL non_detail on last_non_detail.MASTER_NO = non_detail.MASTER_NO and last_non_detail.EFFECTIVE_DATE = non_detail.EFFECTIVE_DATE" +
				" inner join FMTB_AR_CONT_CODE_MASTER ar on non_detail.AR_CONTROL_CODE_NO = ar.AR_CONTROL_CODE_NO" +
				" inner join FMTB_ENTITY_MASTER entity on entity.ENTITY_NO = ar.ENTITY_NO" +
				" inner join MSTB_MASTER_TABLE pymt_type_master on pymt_type.MASTER_NO = pymt_type_master.MASTER_NO" +
				" inner join MSTB_MASTER_TABLE provider on txn.SERVICE_PROVIDER = provider.MASTER_NO" +
				" left join (select distinct BATCH_ID, PAYMENT_NO from BMTB_BANK_PAYMENT_DETAIL) pymt_detail on pymt_detail.BATCH_ID = batch.BATCH_ID" +
				" left join BMTB_BANK_PAYMENT pymt on pymt_detail.PAYMENT_NO = pymt.PAYMENT_NO" +
				" where ('"+(batchNo!=null&&batchNo.length()!=0?batchNo:"")+"' is null or batch.BATCH_NO = '"+(batchNo!=null&&batchNo.length()!=0?batchNo:"")+"')" +
				" and ((:creditStart is null and :creditEnd is null)or(pymt.CREDIT_DATE between case when :creditStart is null then to_date(:creditEnd, 'yyyy-mm-dd hh24:mi:ss') else to_date(:creditStart, 'yyyy-mm-dd hh24:mi:ss') end and case when :creditEnd is null then to_date(:creditStart, 'yyyy-mm-dd hh24:mi:ss') else to_date(:creditEnd, 'yyyy-mm-dd hh24:mi:ss') end))" +
				" and ((:batchStart is null and :batchEnd is null)or(batch.SETTLEMENT_DATE between case when :batchStart is null then to_date(:batchEnd, 'yyyy-mm-dd hh24:mi:ss') else to_date(:batchStart, 'yyyy-mm-dd hh24:mi:ss') end and case when :batchEnd is null then to_date(:batchStart, 'yyyy-mm-dd hh24:mi:ss') else to_date(:batchEnd, 'yyyy-mm-dd hh24:mi:ss') end))" +
				" and (:entityNo is null or entity.ENTITY_NO = :entityNo)" +
				" and (:acquirerNo is null or acquirer.ACQUIRER_NO = :acquirerNo)" +
				" and (:paymentTypeNo is null or pymt_type_master.MASTER_CODE = :paymentTypeNo)" +
				" and (:providerNo is null or provider.MASTER_CODE = :providerNo)" +
				" and (txn.MATCHING_STATUS is NULL or txn.MATCHING_STATUS != 'T') " +
				" group by acquirer.NAME, batch.SETTLEMENT_DATE, batch.BATCH_NO, pymt.CREDIT_DATE" +
				" order by acquirer.NAME, batch.SETTLEMENT_DATE, batch.BATCH_NO";
		Session session = null;
		List results =null;
		try
		{
			this.getActiveDBTransaction();
			session = this.currentSession();
			Query query = session.createSQLQuery(sql);
			for(int i=0;i<paramNames.length;i++){
				query.setString(paramNames[i], values[i]);
			}
			results = query.list();
		}
		catch(Exception e)
		{
			logger.info(e);
		}
		finally{if(session!=null){session.close();}}
//		List results = this.getHibernateTemplate().findByNamedQueryAndNamedParam("cashlessBankCollectionSummary", paramNames, values);
		return results;
	}
	public List<Object[]> getCashlessBankCollectionDetailed(String batchNo, String creditStart, String creditEnd, String batchStart, String batchEnd, String entityNo, String acquirerNo, String paymentTypeNo, String providerNo, String taxiNo, String driverIc, String txnStatus, String sort){
		String[] paramNames = new String[]{"batchNo", "creditStart", "creditEnd", "batchStart", "batchEnd", "entityNo", "acquirerNo", "paymentTypeNo", "providerNo", "taxiNo", "driverIc", "txnStatus", "sort"};
		String[] values = new String[]{batchNo, creditStart, creditEnd, batchStart, batchEnd, entityNo, acquirerNo, paymentTypeNo, providerNo, taxiNo, driverIc, txnStatus, sort};
		List results = this.findResultByNamedQueryAndNamedParam("cashlessBankCollectionDetailed", paramNames, values);
		return results;
	}
	public List<Object[]> getCashlessTxnByAmtRange(String tripStart, String tripEnd, String paymentTypeNo, String entityNo){
		String[] paramNames = new String[]{"tripStart", "tripEnd", "paymentTypeNo", "entityNo"};
		String[] values = new String[]{tripStart, tripEnd, paymentTypeNo, entityNo};
		List results = this.findResultByNamedQueryAndNamedParam("cashlessTxnByAmtRange", paramNames, values);
		return results;
	}
	public List<Object[]> getCashlessCollectionStatisticsSummary(String creditStart, String creditEnd, String entityNo, String acquirerNo){
		String[] paramNames = new String[]{"creditStart", "creditEnd", "entityNo", "acquirerNo"};
		String[] values = new String[]{creditStart, creditEnd, entityNo, acquirerNo};
		List results = this.findResultByNamedQueryAndNamedParam("cashlessCollectionStatisticsSummary", paramNames, values);
		return results;
	}
	public List<Object[]> getCashlessCollectionStatisticsDetailed(String creditStart, String creditEnd, String entityNo, String acquirerNo){
		String[] paramNames = new String[]{"creditStart", "creditEnd", "entityNo", "acquirerNo"};
		String[] values = new String[]{creditStart, creditEnd, entityNo, acquirerNo};
		List results = this.findResultByNamedQueryAndNamedParam("cashlessCollectionStatisticsDetailed", paramNames, values);
		return results;
	}
	public List<Object[]> getInvoiceReport(String entity, String invoiceStart, String invoiceEnd, String invoiceType, String eInvoice, String salesPerson, String sortBy){
		String[] paramNames = new String[]{"entity", "invoiceStart", "invoiceEnd", "invoiceType", "eInvoice", "salesPerson", "sortBy"};
		String[] values = new String[]{entity, invoiceStart, invoiceEnd, invoiceType, eInvoice, salesPerson, sortBy};
		List results = this.findResultByNamedQueryAndNamedParam("invoiceReport", paramNames, values);
		return results;
	}
	public List<Object[]> getRevenueByProductCardType(String revenueStart, String revenueEnd, String entity, String provider, String productType, String paymentType){
		String[] paramNames = new String[]{"revenueStart", "revenueEnd", "entity", "provider", "productType", "paymentType"};
		String[] values = new String[]{revenueStart, revenueEnd, entity, provider, productType, paymentType};
		List results = this.findResultByNamedQueryAndNamedParam("revenueByProductCardType", paramNames, values);
		return results;
	}
	public List<Object[]> getRevenueByProductCardType2(String revenueStart, String revenueEnd, String entity, String provider, String productType, String paymentType){
		String[] paramNames = new String[]{"revenueStart", "revenueEnd", "entity", "provider", "productType", "paymentType"};
		String[] values = new String[]{revenueStart, revenueEnd, entity, provider, productType, paymentType};
		List results = this.findResultByNamedQueryAndNamedParam("revenueByProductCardType2", paramNames, values);
		return results;
	}
	public List<Object[]> getCreditCardPromoDetails(String batchStart, String batchEnd, String tripStart, String tripEnd, String paymentTypeNo, String binStart, String binEnd, String promo, String txnStatus, String batchStatus, String sort){
		String[] paramNames = new String[]{"batchStart", "batchEnd", "tripStart", "tripEnd", "paymentTypeNo", "binStart", "binEnd", "promo", "txnStatus", "batchStatus", "sort"};
		String[] values = new String[]{batchStart, batchEnd, tripStart, tripEnd, paymentTypeNo, binStart, binEnd, promo, txnStatus, batchStatus, sort};
		List results = this.findResultByNamedQueryAndNamedParam("creditCardPromoDetails", paramNames, values);
		return results;
	}
	public List<Object[]> getTimelyPaymentStatisticsDetailed(String mthYear, String entityNo, String creditTerm, String type){
		String[] paramNames = new String[]{"mthYear", "entityNo", "creditTerm", "type"};
		String[] values = new String[]{mthYear, entityNo, creditTerm, type};
		List results = this.findResultByNamedQueryAndNamedParam("timelyPaymentStatisticsDetailed", paramNames, values);
		return results;
	}
	
	public List<Object[]> getInventoryMovementReportDetailed(String stockStart, String stockEnd, String itemTypeNo, String entityNo){
		String[] paramNames = new String[]{"stockStart", "stockEnd", "itemTypeNo", "entityNo"};
		String[] values = new String[]{stockStart, stockEnd, itemTypeNo, entityNo};
		List results = this.findResultByNamedQueryAndNamedParam("inventoryMovementReportDetailed", paramNames, values);
		return results;
	}
	public List<Object[]> getTripReconciliationReport(String entity,String accountNo,String accountName,String division,String department,String productType,String startDate,String endDate,String uploadStartDate,String uploadEndDate,String companyCode,String txnStatus,String sortBy){
		String[] paramNames = new String[]{"entity", "accountNo", "accountName", "division", "department", "productType", "startDate", "endDate", "uploadStartDate", "uploadEndDate", "companyCode", "txnStatus", "sortBy"};
		String[] values = new String[]{entity,accountNo,accountName,division,department,productType,startDate,endDate,uploadStartDate,uploadEndDate,companyCode,txnStatus,sortBy};
		List results = this.findResultByNamedQueryAndNamedParam("tripReconciliation", paramNames, values);
		return results;
	}
	public List<Object[]> getContactPersonReport(String accountNo,String accountName,String division,String department,String accountStatus,String contactPersonName,String typeOfContact,String businessNature,String productType,String salesPerson,String sortBy, String entityNo){
		String[] paramNames = new String[]{"accountNo", "accountName", "division", "department", "accountStatus", "contactPersonName", "typeOfContact", "businessNature", "productType", "salesPerson", "sortBy", "entityNo"};
		String[] values = new String[]{accountNo,accountName,division,department,accountStatus,contactPersonName,typeOfContact,businessNature,productType,salesPerson,sortBy, entityNo};
		
		List results = this.findResultByNamedQueryAndNamedParam("contactPerson", paramNames, values);
		return results;
	}
	public List<Object[]> getCorporateCustomerBreakdownUsageReport(String invoiceMonth,String productType,String accountStatus,String businessNature,String salesPerson,String numberOfRecords,String sortBy, String entityNo){
		List results;
		if(numberOfRecords.length()>0){
			String[] paramNames = new String[]{"invoiceMonth","productType","accountStatus","businessNature","salesPerson","numberOfRecords","sortBy", "entityNo"};
			String[] values = new String[]{invoiceMonth,productType,accountStatus,businessNature,salesPerson,numberOfRecords,sortBy, entityNo};
			results = this.findResultByNamedQueryAndNamedParam("corporateCustomerUsageBreakdownLimit", paramNames, values);
		}else{
			String[] paramNames = new String[]{"invoiceMonth","productType","accountStatus","businessNature","salesPerson","sortBy", "entityNo"};
			String[] values = new String[]{invoiceMonth,productType,accountStatus,businessNature,salesPerson,sortBy, entityNo};
			results = this.findResultByNamedQueryAndNamedParam("corporateCustomerUsageBreakdown", paramNames, values);
		}
		return results;
	}
	
	public List<Object[]> getPrepaidProduct(String custNo, String accountName, String productTypeId, 
			String expiryDateFrom, String expiryDateTo, String cardStatus, String cardNoFrom, String cardNoTo,
			String accountTypeNo, String accountStatus, String entityNo){
		
		//Create Named Params & Values
		String[] paramNames = new String[]{"custNo","accountName","productTypeId","expiryDateFrom","expiryDateTo",
				"cardStatus","cardNoFrom","cardNoTo","accountTypeNo","accountStatus", "entityNo"};
		String[] values = new String[]{custNo, accountName, productTypeId, expiryDateFrom, expiryDateTo, 
				cardStatus, cardNoFrom, cardNoTo, accountTypeNo, accountStatus, entityNo};

		//Query using named query
		List results = this.findResultByNamedQueryAndNamedParam("prepaidProduct", paramNames, values);
		return results;
	}
	
	public List<Object[]> getCardStatisticReport(String productStatusDate, String issueStart, String issueEnd, String terminateStart, String terminateEnd, String suspendStart, String suspendEnd, String replaceStart, String replaceEnd, String productType, String productStatus, String acctStatus){
		String[] paramNames = new String[]{"productStatusDate", "issueStart", "issueEnd", "terminateStart", "terminateEnd", "suspendStart", "suspendEnd", "replaceStart", "replaceEnd", "productType", "productStatus", "acctStatus"};
		String[] values = new String[]{productStatusDate, issueStart, issueEnd, terminateStart, terminateEnd, suspendStart, suspendEnd, replaceStart, replaceEnd, productType, productStatus, acctStatus};
		List results = this.findResultByNamedQueryAndNamedParam("cardStatisticsReport", paramNames, values);
		return results;
	}
	public List<Object[]> getCardStatisticReportSummary(String productStatusDate, String issueStart, String issueEnd, String terminateStart, String terminateEnd, String suspendStart, String suspendEnd, String replaceStart, String replaceEnd, String productType, String productStatus, String acctStatus){
		String[] paramNames = new String[]{"productStatusDate", "issueStart", "issueEnd", "terminateStart", "terminateEnd", "suspendStart", "suspendEnd", "replaceStart", "replaceEnd", "productType", "productStatus", "acctStatus"};
		String[] values = new String[]{productStatusDate, issueStart, issueEnd, terminateStart, terminateEnd, suspendStart, suspendEnd, replaceStart, replaceEnd, productType, productStatus, acctStatus};
		List results = this.findResultByNamedQueryAndNamedParam("cardStatisticsReportSummary", paramNames, values);
		return results;
	}
	public List<Object[]> getNewAccountsRevenue(String acctTypeNo, String joinedStart, String joinedEnd, String usageYear, String entityNo, String acctStatus, String industryCode, String salespersonNo){
		String[] paramNames = new String[]{"acctTypeNo", "joinedStart", "joinedEnd", "usageYear", "entityNo", "acctStatus", "industryCode", "salespersonNo"};
		String[] values = new String[]{acctTypeNo, joinedStart, joinedEnd, usageYear, entityNo, acctStatus, industryCode, salespersonNo};
		List results = this.findResultByNamedQueryAndNamedParam("newAccountsRevenue", paramNames, values);
		return results;
	}
	public List<Object[]> getRefundDepositReport(String acctNo, String acctName, String acctStatus, String terminateStart, String terminateEnd, String refund, String entityNo){
		String[] paramNames = new String[]{"acctNo", "acctName", "acctStatus", "terminateStart", "terminateEnd", "refund", "entityNo"};
		String[] values = new String[]{acctNo, acctName, acctStatus, terminateStart, terminateEnd, refund, entityNo};
		List results = this.findResultByNamedQueryAndNamedParam("refundDepositReport", paramNames, values);
		return results;
	}
	public List<Object[]> getFinancialMemoReport(String acctNo, String acctName, String memoStart, String memoEnd, String memoNo, String invoiceNo, String receiptNo, String memoType, String sort, String entityNo){
		String[] paramNames = new String[]{"acctNo", "acctName", "memoStart", "memoEnd", "memoNo", "invoiceNo", "receiptNo", "memoType", "sort", "entityNo"};
		String[] values = new String[]{acctNo, acctName, memoStart, memoEnd, memoNo, invoiceNo, receiptNo, memoType, sort, entityNo};
		List results = this.findResultByNamedQueryAndNamedParam("financialMemoReport", paramNames, values);
		return results;
	}
	public List<Object[]> getItemTypeRevenueProfitReport(String startMonth, String endMonth, String itemTypeNo, String entityNo){
		String[] paramNames = new String[]{"startMonth", "endMonth", "itemTypeNo", "entityNo"};
		String[] values = new String[]{startMonth, endMonth, itemTypeNo, entityNo};
		List results = this.findResultByNamedQueryAndNamedParam("itemTypeRevenueProfitReport", paramNames, values);
		return results;
	}
	public List<Object[]> getMonthlyDebtManagementDebt(String acctTypeNo, String acctStatus, String receiptMonth, String industryCode, String salespersonNo, String entityNo){
		String[] paramNames = new String[]{"acctTypeNo", "acctStatus", "receiptMonth", "industryCode", "salespersonNo", "entityNo"};
		String[] values = new String[]{acctTypeNo, acctStatus, receiptMonth, industryCode, salespersonNo, entityNo};
		List results = this.findResultByNamedQueryAndNamedParam("monthlyDebtManagementDebt", paramNames, values);
		return results;
	}
	public List<Object[]> getMonthlyDebtManagementReceived(String acctTypeNo, String acctStatus, String receiptMonth, String industryCode, String salespersonNo, String entityNo){
		String[] paramNames = new String[]{"acctTypeNo", "acctStatus", "receiptMonth", "industryCode", "salespersonNo", "entityNo"};
		String[] values = new String[]{acctTypeNo, acctStatus, receiptMonth, industryCode, salespersonNo, entityNo};
		List results = this.findResultByNamedQueryAndNamedParam("monthlyDebtManagementReceived", paramNames, values);
		return results;
	}
	public List<Object[]> getMonthlyDebtManagementClosing(String acctTypeNo, String acctStatus, String receiptMonth, String industryCode, String salespersonNo, String entityNo){
		String[] paramNames = new String[]{"acctTypeNo", "acctStatus", "receiptMonth", "industryCode", "salespersonNo", "entityNo"};
		String[] values = new String[]{acctTypeNo, acctStatus, receiptMonth, industryCode, salespersonNo, entityNo};
		List results = this.findResultByNamedQueryAndNamedParam("monthlyDebtManagementClosing", paramNames, values);
		return results;
	}
	public List<Object[]> getMovementReport(String entityNo, String productItemType, String acctNo, String acctName, String acctStatus, String productStatus, String mthYear, String type){
		String[] paramNames = new String[]{"entityNo", "productItemType", "acctNo", "acctName", "acctStatus", "productStatus", "mthYear", "type"};
		String[] values = new String[]{entityNo, productItemType, acctNo, acctName, acctStatus, productStatus, mthYear, type};
		List results = this.findResultByNamedQueryAndNamedParam("movementReport", paramNames, values);
		return results;
	}
	public List<Object[]> getLoyaltyProgramReport(String acctTypeNo, String acctNo, String acctName, String acctStatus, String productTypeId, String pointStart, String pointEnd, String salespersonNo, String sort, String entityNo){
		String[] paramNames = new String[]{"acctTypeNo", "acctNo", "acctName", "acctStatus", "productTypeId", "pointStart", "pointEnd", "salespersonNo", "sort", "entityNo"};
		String[] values = new String[]{acctTypeNo, acctNo, acctName, acctStatus, productTypeId, pointStart, pointEnd, salespersonNo, sort, entityNo};
		List results = this.findResultByNamedQueryAndNamedParam("loyaltyProgramReport", paramNames, values);
		return results;
	}
	public List<Object[]> getTripAdjustmentReport(String acctNo, String acctName, String paymentType, String createStart, String createEnd, String approveStart, String approveEnd, String entityNo, String providerNo, String approvalStatus, String txnStatus, String action, String fmsUpdate, String sort){
		String[] paramNames = new String[]{"acctNo", "acctName", "paymentType", "createStart", "createEnd", "approveStart", "approveEnd", "entityNo", "providerNo", "approvalStatus", "txnStatus", "action", "fmsUpdate", "sort"};
		String[] values = new String[]{acctNo, acctName, paymentType, createStart, createEnd, approveStart, approveEnd, entityNo, providerNo, approvalStatus, txnStatus, action, fmsUpdate, sort};
		List results = this.findResultByNamedQueryAndNamedParam("tripAdjustmentReport", paramNames, values);
		return results;
	}
	public List<Object[]> getGiroSummary(String valueDateFrom, String valueDateTo, String generationDateFrom,
			String generationDateTo, String uploadDateFrom, String uploadDateTo, String entityNo){
		
		//Create Named Params & Values
		String[] paramNames = new String[]{"valueDateFrom","valueDateTo","generationDateFrom",
				"generationDateTo","uploadDateFrom","uploadDateTo","entityNo"};
		String[] values = new String[]{valueDateFrom, valueDateTo, generationDateFrom,
				generationDateTo, uploadDateFrom, uploadDateTo, entityNo};

		//Query using named query
		List results = this.findResultByNamedQueryAndNamedParam("giroSummary", paramNames, values);
		return results;
	}
	
	public List<Object[]> getGiroFile(String custNo, String divAcct, String deptAcct, String entityNo, String cutOffDate){
		
		//Create Named Params & Values
		String[] paramNames = new String[]{"custNo", "divAcct", "deptAcct", "entityNo","cutOffDate"};
		String[] values = new String[]{custNo, divAcct, deptAcct, entityNo, cutOffDate};

		//Query using named query
		List results = this.findResultByNamedQueryAndNamedParam("giroFile", paramNames, values);
		return results;
	}
	
	
	
	
	
	public List<Object[]> getGiroReject(String valueDateFrom, String valueDateTo, String acctTypeNo,
			String salesPersonNo, String rejectedCode, String rejectedBy, String entityNo){
		
		//Create Named Params & Values
		String[] paramNames = new String[]{"valueDateFrom","valueDateTo","acctTypeNo",
				"salesPersonNo","rejectedCode","rejectedBy","entityNo"};
		String[] values = new String[]{valueDateFrom, valueDateTo, acctTypeNo,
				salesPersonNo, rejectedCode, rejectedBy, entityNo};

		//Query using named query
		List results = this.findResultByNamedQueryAndNamedParam("giroReject", paramNames, values);
		return results;
	}
	public List<Object[]> getErrorTxnReport(String txnStart, String txnEnd, String uploadStart, String uploadEnd, String errorMsg, String offline, String jobNo, String cardNo, String nric, String taxiNo, String sort){
		String[] paramNames = new String[]{"txnStart", "txnEnd", "uploadStart", "uploadEnd", "errorMsg", "offline", "jobNo", "cardNo", "nric", "taxiNo", "sort"};
		String[] values = new String[]{txnStart, txnEnd, uploadStart, uploadEnd, errorMsg, offline, jobNo, cardNo, nric, taxiNo, sort};
		List results = this.findResultByNamedQueryAndNamedParam("errorTxnReport", paramNames, values);
		return results;
	}
	public List<String> getAllTxnErrorMsg(){
		DetachedCriteria tripsTxnErrors = DetachedCriteria.forClass(IttbTripsTxnError.class);
		tripsTxnErrors.setProjection(Projections.distinct(Projections.property("errorMsg")));
		return this.findAllByCriteria(tripsTxnErrors);
	}
	public List<Object[]> getASOfflineApproval(String txnStart, String txnEnd,String offlineTxnStart,String offlineTxnEnd, String messageType,String error,String cardNo,String sortBy){
		String msgTypId = NonConfigurableConstants.AS_OFFLINE_MSG_TYP_ID;
		String processCode = messageType;
		
		logger.info("******** getASOfflineApproval msgTypId: " +msgTypId+ " processCode: " +processCode + " messageType: " +messageType);
		
		String[] paramNames = new String[]{"txnStart", "txnEnd", "offlineTxnStart", "offlineTxnEnd", "msgTypId", "processCode", "error", "cardNo", "sortBy"};
		String[] values = new String[]{txnStart, txnEnd, offlineTxnStart, offlineTxnEnd, msgTypId, processCode, error, cardNo, sortBy};
		List results = this.findResultByNamedQueryAndNamedParam("asOfflineApprovalReport", paramNames, values);
		return results;
	}
	
	@SuppressWarnings("unused")
	public List<ImtbItem> getUnredeemedVoucher(Date issuedDateFrom, Date issuedDateTo, Date expiryDateFrom,
			Date expiryDateTo, Integer itemTypeNo, String status, Date batchDateAsAt, Date redeemDateAsAt, Integer entityNo) {
		DetachedCriteria itemCrit = DetachedCriteria.forClass(ImtbItem.class);
		DetachedCriteria itemTypeCrit = itemCrit.createCriteria("imtbItemType",
				org.hibernate.sql.JoinType.LEFT_OUTER_JOIN);
		DetachedCriteria issueCrit = itemCrit.createCriteria("imtbIssue",
				org.hibernate.sql.JoinType.LEFT_OUTER_JOIN);
		DetachedCriteria issueReqCrit = issueCrit.createCriteria("imtbIssueReq",
				org.hibernate.sql.JoinType.LEFT_OUTER_JOIN);
		DetachedCriteria account3Crit = issueReqCrit.createCriteria("amtbAccount", "acct3", org.hibernate.sql.JoinType.LEFT_OUTER_JOIN);
		DetachedCriteria account2Crit = account3Crit.createCriteria("amtbAccount", "acct2", org.hibernate.sql.JoinType.LEFT_OUTER_JOIN);
		DetachedCriteria account1Crit = account2Crit.createCriteria("amtbAccount", "acct1", org.hibernate.sql.JoinType.LEFT_OUTER_JOIN);

		DetachedCriteria fmtbArCont1Crit = account1Crit.createCriteria("fmtbArContCodeMaster", "fmtbArCont1", org.hibernate.sql.JoinType.LEFT_OUTER_JOIN);
		DetachedCriteria fmtbEntity1Crit = fmtbArCont1Crit.createCriteria("fmtbEntityMaster", "fmtbEntity1", org.hibernate.sql.JoinType.LEFT_OUTER_JOIN);
		DetachedCriteria fmtbArCont2Crit = account2Crit.createCriteria("fmtbArContCodeMaster", "fmtbArCont2", org.hibernate.sql.JoinType.LEFT_OUTER_JOIN);
		DetachedCriteria fmtbEntity2Crit = fmtbArCont2Crit.createCriteria("fmtbEntityMaster", "fmtbEntity2", org.hibernate.sql.JoinType.LEFT_OUTER_JOIN);
		DetachedCriteria fmtbArCont3Crit = account3Crit.createCriteria("fmtbArContCodeMaster", "fmtbArCont3", org.hibernate.sql.JoinType.LEFT_OUTER_JOIN);
		DetachedCriteria fmtbEntity3Crit = fmtbArCont3Crit.createCriteria("fmtbEntityMaster", "fmtbEntity3", org.hibernate.sql.JoinType.LEFT_OUTER_JOIN);

		if(entityNo != null)
			itemCrit.add(Restrictions.sqlRestriction("( fmtbentity12_.entity_no = "+entityNo+" or fmtbentity10_.entity_no = "+entityNo+" or fmtbentity8_.entity_no = "+entityNo+" )"));
		if (itemTypeNo != null)
			itemTypeCrit.add(Restrictions.idEq(itemTypeNo));
		if (status != null)
			itemCrit.add(Restrictions.eq("status", status));
		if (issuedDateFrom != null)
			itemCrit.add(Restrictions.between("createdDt", issuedDateFrom, issuedDateTo));
		else
		{
			if(batchDateAsAt!=null && redeemDateAsAt!=null ){
				
				if(batchDateAsAt.before(redeemDateAsAt)) {
					itemCrit.add(Restrictions.le("createdDt", batchDateAsAt));
				}
				else {
					itemCrit.add(Restrictions.le("createdDt", redeemDateAsAt));
				}
			}
			else
			{
				if(batchDateAsAt!=null ) {
					itemCrit.add(Restrictions.le("createdDt", batchDateAsAt));
				}
				if(redeemDateAsAt!=null ) {
					itemCrit.add(Restrictions.le("createdDt", redeemDateAsAt));
				}
			}
			
		}
		if (expiryDateFrom != null)
			issueCrit.add(Restrictions.between("expiryDate", expiryDateFrom, expiryDateTo));

		if(batchDateAsAt!=null ) {
			itemCrit.add(Restrictions.or(Restrictions.isNull("batchDate"), Restrictions.gt("batchDate", batchDateAsAt)));
		}
		if(redeemDateAsAt!=null ) {
			itemCrit.add(Restrictions.or(Restrictions.isNull("redeemTime"), Restrictions.gt("redeemTime", redeemDateAsAt)));
		}
		
		itemCrit.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		return this.findAllByCriteria(itemCrit);
	}
	
	
	public List<ImtbItem> getRedeemedVoucher(BigDecimal serialNoStart, BigDecimal serialNoEnd,
			Integer itemTypeNo, Date batchDateStart, Date batchDateEnd, Date redeemDateStart,  java.sql.Date redeemDateEnd, int accountNo, Integer entityNo) {
		
		DetachedCriteria itemCrit = DetachedCriteria.forClass(ImtbItem.class);
		DetachedCriteria itemTypeCrit = itemCrit.createCriteria("imtbItemType",
				org.hibernate.sql.JoinType.LEFT_OUTER_JOIN);
		itemCrit.add(Restrictions.in("status", new String[]{NonConfigurableConstants.INVENTORY_ITEM_STATUS_REDEEMED, NonConfigurableConstants.INVENTORY_ITEM_STATUS_VOID,
				NonConfigurableConstants.INVENTORY_ITEM_STATUS_PENDING_CHANGE_OF_REDEMPTION, NonConfigurableConstants.INVENTORY_ITEM_STATUS_PENDING_VOID, NonConfigurableConstants.INVENTORY_ITEM_STATUS_PENDING_REMOVE_REDEMPTION}));
		
		DetachedCriteria itemIssue = itemCrit.createCriteria("imtbIssue", org.hibernate.sql.JoinType.LEFT_OUTER_JOIN);
		DetachedCriteria itemIssueReq = itemIssue.createCriteria("imtbIssueReq", org.hibernate.sql.JoinType.LEFT_OUTER_JOIN);
		DetachedCriteria amtbAccount = itemIssueReq.createCriteria("amtbAccount", "account", org.hibernate.sql.JoinType.LEFT_OUTER_JOIN);
		DetachedCriteria parentAccountCriteria = amtbAccount.createCriteria("amtbAccount", "parentAccount", org.hibernate.sql.JoinType.LEFT_OUTER_JOIN);
		DetachedCriteria grandParentAccountCriteria = parentAccountCriteria.createCriteria("amtbAccount", "grandParentAccount", Criteria.LEFT_JOIN);
		
		DetachedCriteria fmtbArCont1Crit = amtbAccount.createCriteria("fmtbArContCodeMaster", "fmtbArCont1", org.hibernate.sql.JoinType.LEFT_OUTER_JOIN);
		DetachedCriteria fmtbEntity1Crit = fmtbArCont1Crit.createCriteria("fmtbEntityMaster", "fmtbEntity1", org.hibernate.sql.JoinType.LEFT_OUTER_JOIN);
		DetachedCriteria fmtbArCont2Crit = parentAccountCriteria.createCriteria("fmtbArContCodeMaster", "fmtbArCont2", org.hibernate.sql.JoinType.LEFT_OUTER_JOIN);
		DetachedCriteria fmtbEntity2Crit = fmtbArCont2Crit.createCriteria("fmtbEntityMaster", "fmtbEntity2", org.hibernate.sql.JoinType.LEFT_OUTER_JOIN);
		DetachedCriteria fmtbArCont3Crit = grandParentAccountCriteria.createCriteria("fmtbArContCodeMaster", "fmtbArCont3", org.hibernate.sql.JoinType.LEFT_OUTER_JOIN);
		DetachedCriteria fmtbEntity3Crit = fmtbArCont3Crit.createCriteria("fmtbEntityMaster", "fmtbEntity3", org.hibernate.sql.JoinType.LEFT_OUTER_JOIN);

		if(entityNo != null)
			itemCrit.add(Restrictions.sqlRestriction("( fmtbentity12_.entity_no = "+entityNo+" or fmtbentity10_.entity_no = "+entityNo+" or fmtbentity8_.entity_no = "+entityNo+" )"));
		
		if(serialNoStart!=null){
			itemCrit.add(Restrictions.between("serialNo", serialNoStart, serialNoEnd));
		}
			
		if (itemTypeNo != null)
			itemTypeCrit.add(Restrictions.idEq(itemTypeNo));
		
		if (batchDateStart != null)
			itemCrit.add(Restrictions.between("batchDate", batchDateStart, batchDateEnd));
		
		if (redeemDateStart != null)
			itemCrit.add(Restrictions.between("redeemTime", redeemDateStart, redeemDateEnd));

		if (accountNo != 0)
		{
//			amtbAccount.add(Restrictions.eq("custNo", Integer.toString(accountNo)));

			Disjunction dj = Restrictions.disjunction();
			dj.add(Restrictions.and(
					Restrictions.isNotNull("account.custNo"),
					Restrictions.eq("account.custNo", Integer.toString(accountNo))
					));
			dj.add(Restrictions.and(
					Restrictions.isNotNull("parentAccount.custNo"),
					Restrictions.eq("parentAccount.custNo", Integer.toString(accountNo))
					));
			dj.add(Restrictions.and(
					Restrictions.isNotNull("grandParentAccount.custNo"),
					Restrictions.eq("grandParentAccount.custNo", Integer.toString(accountNo))
					));
			
			itemCrit.add(dj);
		}
//		DetachedCriteria subqueryCriteria = this.createMaxResultSubquery(itemCrit, "ITEM_NO", ConfigurableConstants.getMaxReportRecord()); 
//		itemCrit.add(Subqueries.propertyIn("itemNo", subqueryCriteria));
		
		itemCrit.addOrder(Order.asc("redeemTime")).addOrder( Order.asc("serialNo") );
		itemCrit.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		return this.findAllByCriteria(itemCrit);
	}
	
	public List<Object[]> suspensionReactivationVoucher(String actionStartDate,
			String actionEndDate, String approvalStartDate, String approvalEndDate,
			String serialNoStart, String serialNoEnd, String itemTypeNo, String actionType,
			String approvalStatus, String entityNo) {
		String[] paramNames = new String[] { "actionStartDate", "actionEndDate",
				"approvalStartDate", "approvalEndDate", "serialNoStart", "serialNoEnd",
				"itemTypeNo", "actionType", "approvalStatus", "entityNo" };
		String[] values = new String[] { actionStartDate, actionEndDate, approvalStartDate,
				approvalEndDate, serialNoStart, serialNoEnd, itemTypeNo, actionType, approvalStatus, entityNo };
		List results = this.findResultByNamedQueryAndNamedParam(
				"suspensionReactivationVoucher", paramNames, values);
		return results;
	}
	
	public List<Object[]> adjustVoucherRedemption(String batchNo, String actionStartDate,
			String actionEndDate, String approvalStartDate, String approvalEndDate,
			String serialNoStart, String serialNoEnd, String itemTypeNo, String actionType,
			String approvalStatus, String entityNo) {
		
		if(batchNo!=null && batchNo.length()>0) batchNo = "%" + batchNo + "%";
		
		String[] paramNames = new String[] { "batchNo", "actionStartDate", "actionEndDate",
				"approvalStartDate", "approvalEndDate", "serialNoStart", "serialNoEnd",
				"itemTypeNo", "actionType", "approvalStatus" , "entityNo"};
		String[] values = new String[] { batchNo, actionStartDate, actionEndDate,
				approvalStartDate, approvalEndDate, serialNoStart, serialNoEnd, itemTypeNo,
				actionType, approvalStatus, entityNo };
		List results = this.findResultByNamedQueryAndNamedParam(
				"adjustVoucherRedemption", paramNames, values);
		return results;
	}
	
	public List<Object[]> getGovtEInv(String accountNo, String accountName, String subAccountNo,
			String entityNo, String businessUnit, String govtEInvFlag, String returnStatus, String requestNo,
			String requestDateFrom, String requestDateTo, String invoiceDateFrom, String invoiceDateTo) {

		// Create Named Params & Values
		String[] paramNames = new String[] { "accountNo", "accountName", "subAccountNo", "entityNo",
				"businessUnit", "govtEInvFlag", "returnStatus", "requestNo", "requestDateFrom",
				"requestDateTo", "invoiceDateFrom", "invoiceDateTo" };
		String[] values = new String[] { accountNo, accountName == null ? "%" : accountName + "%",
				subAccountNo, entityNo, businessUnit, govtEInvFlag, returnStatus, requestNo, requestDateFrom,
				requestDateTo, invoiceDateFrom, invoiceDateTo };

		// Query using named query
		List results = this.findResultByNamedQueryAndNamedParam("govtEInv", paramNames,
				values);
		return results;
	}  
	public List<Object[]> getPubbs(String accountNo, String accountName, String subAccountNo,
			String entityNo, String pubbsFlag, String returnStatus, String requestNo,
			String requestDateFrom, String requestDateTo, String invoiceDateFrom, String invoiceDateTo) {

		// Create Named Params & Values
		String[] paramNames = new String[] { "accountNo", "accountName", "subAccountNo", "entityNo",
				"pubbsFlag", "returnStatus", "requestNo", "requestDateFrom",
				"requestDateTo", "invoiceDateFrom", "invoiceDateTo" };
		String[] values = new String[] { accountNo, accountName == null ? "%" : accountName + "%",
				subAccountNo, entityNo, pubbsFlag, returnStatus, requestNo, requestDateFrom,
				requestDateTo, invoiceDateFrom, invoiceDateTo };

		// Query using named query
		List results = this.findResultByNamedQueryAndNamedParam("pubbs", paramNames,
				values);
		return results;
	}
	
	public List<Object[]> getApprovalRequestRecords(String acctType, String acctNo, String acctName, String cardNo,
			String requestStartDate, String requestEndDate, String approvalStartDate, String approvalEndDate, String actionType, String approvalStatus, String entity, String sortBy){
		
		String[] paramNames = new String[] {"acctType", "acctNo", "acctName", "cardNo",
			"requestStartDate", "requestEndDate", "approvalStartDate", "approvalEndDate", "actionType", "approvalStatus", "entityNo", "sortBy" };
		String[] values = new String[] {acctType, acctNo, acctName, cardNo,
						requestStartDate, requestEndDate, approvalStartDate, approvalEndDate, actionType, approvalStatus, entity, sortBy};
		List results = this.findResultByNamedQueryAndNamedParam(
				"prepaidApproval", paramNames, values);
		return results;
		
		
	}
	
	
	public List<Object[]> getTopUpRecords(String acctType, String acctNo, String acctName, String cardNo,
			String cardStatus, String mobileNo, String productTypeId, String topUpType, String promoCode, 
			String topUpStartDate, String topUpEndDate, String minTopUp, String entityNo, String sortBy){
		
		
		if(!Strings.isNullOrEmpty(minTopUp)){
			BigDecimal minTopUpBD = null;
			try {
				minTopUpBD = StringUtil.stringToBigDecimal(minTopUp);
			} catch (ParseException e) {
				e.printStackTrace();
			}
			minTopUp = String.valueOf(minTopUpBD);
		}
	
		String[] paramNames = new String[] {"acctType", "acctNo", "acctName", "cardNo",
				"cardStatus", "mobileNo", "productTypeId", "topUpType", "promoCode", 
				"topUpStartDate", "topUpEndDate", "minTopUp", "entityNo", "sortBy"};
		String[] values = new String[] {acctType, acctNo, acctName, cardNo,
				cardStatus, mobileNo, productTypeId, topUpType, promoCode, 
				topUpStartDate, topUpEndDate, minTopUp, entityNo, sortBy};
		List results = this.findResultByNamedQueryAndNamedParam(
				"topUp", paramNames, values);
		return results;
		
		
	}
	
	
	public List<Object[]> getPrepaidUsageRecords(String acctType, String acctNo, String acctName, String acctStatus, String subscribeProductTypeId, String joinStartDate, String joinEndDate, String tripStartDate, String tripEndDate,
			  String entityNo, String sortBy){

		String[] paramNames = new String[] {"acctType", "acctNo", "acctName",
				"acctStatus", "productTypeId",  "joinStartDate", "joinEndDate",
				"tripStartDate", "tripEndDate", "entityNo", "sortBy"};
		String[] values = new String[] {acctType, acctNo, acctName, acctStatus, subscribeProductTypeId, joinStartDate, joinEndDate, tripStartDate, tripEndDate, entityNo, sortBy};
		List results = this.findResultByNamedQueryAndNamedParam(
				"prepaidUsage", paramNames, values);
		return results;

	}
	
	
	public List<Object[]> getPrepaidUsageDetailRecords(String acctType, String acctNo, String acctName, String cardNo, String cardStatus, String productTypeId, String issueStartDate, String issueEndDate, String tripStartDate, String tripEndDate,
			  String entityNo, String sortBy){
		
		String[] paramNames = new String[] {"acctType", "acctNo", "acctName", "cardNo",
				"cardStatus", "productTypeId",  "issueStartDate", "issueEndDate",
				"tripStartDate", "tripEndDate", "entityNo", "sortBy"};
		String[] values = new String[] {acctType, acctNo, acctName, cardNo, cardStatus, productTypeId, issueStartDate, issueEndDate, tripStartDate, tripEndDate, entityNo, sortBy};
		List results = this.findResultByNamedQueryAndNamedParam(
				"prepaidUsageDetail", paramNames, values);
		return results;
	}
	
	
	public List<Object[]> getPrepaidCardTransactionRecords(String cardNo){
		String[] paramNames = new String[] {"cardNo"};
		String[] values = new String[] {cardNo};
		List results = this.findResultByNamedQueryAndNamedParam(
				"prepaidCardTransaction", paramNames, values);
		return results;
	}

	public List<Object[]> getCustomerUsageCardLevel(String accountNo, String accountName, String invoiceStartMonth, String invoiceEndMonth, String productType, String entityNo, String expiryDT, String cardNo){
		String[] paramNames = new String[]{"accountNo", "accountName", "invoiceStartMonth", "invoiceEndMonth", "productType", "entityNo", "expiryDT", "cardNo"};
		String[] values = new String[]{accountNo, accountName, invoiceStartMonth, invoiceEndMonth, productType, entityNo, expiryDT, cardNo};
		List results = this.findResultByNamedQueryAndNamedParam("customerUsageCardLevel", paramNames, values);
		return results;	
	}
	
	public List<Object[]> getEmailAudit(String dateFrom, String dateTo) {

		// Create Named Params & Values
		String[] paramNames = new String[] { "dateFrom", "dateTo" };
		String[] values = new String[] { dateFrom, dateTo };

		// Query using named query
		List results = this.findResultByNamedQueryAndNamedParam("emailAudit", paramNames, values);
		return results;
	}
	public List<Object[]> getBirthdayAnnouncement(String birthdayDateFrom, String birthdayDateTo, String joinDateFrom, String joinDateTo, String accountStatus) {

		// Create Named Params & Values
		String[] paramNames = new String[] { "birthdayDateFrom", "birthdayDateTo", "joinDateFrom", "joinDateTo", "accountStatus" };
		String[] values = new String[] { birthdayDateFrom, birthdayDateTo, joinDateFrom, joinDateTo, accountStatus };
		// Query using named query
		List results = this.findResultByNamedQueryAndNamedParam("birthdayAnnouncement", paramNames, values);
		return results;
	}

	public List<Object[]> getAydenPaymentMatchingSummaryReport(String settlementStartDate, String settlementEndDate, String batchNo, String recordType1, String recordType2){
		// Create Named Params & Values
		String[] paramNames = new String[] {"settlementStartDate", "settlementEndDate", "batchNo","recordType1","recordType2"};
		String[] values = new String[]{settlementStartDate, settlementEndDate, batchNo, recordType1, recordType2};
		// Query using named query
		List results = this.findResultByNamedQueryAndNamedParam("AydenPaymentMatchingSummaryReport", paramNames, values);
		return results;
	}

	public List<Object[]> getAydenPaymentMatchingSummaryReport2(String settlementStartDate, String settlementEndDate, String batchNo, String recordType1, String recordType2){
		// Create Named Params & Values
		String[] paramNames = new String[] { "settlementStartDate", "settlementEndDate", "batchNo","recordType1","recordType2"};
		String[] values = new String[] { settlementStartDate, settlementEndDate, batchNo, recordType1, recordType2};
		// Query using named query
		List results = this.findResultByNamedQueryAndNamedParam("AydenPaymentMatchingSummaryReport2", paramNames, values);
		return results;
	}

	@Override
	public List<Object[]> getAydenPaymentMatchingExcessAmount(String settlementStartDate, String settlementEndDate, String batchNo) {

		// Create Named Params & Values
		String[] paramNames = new String[] { "settlementStartDate", "settlementEndDate", "batchNo"};
		String[] values = new String[] { settlementStartDate, settlementEndDate, batchNo};
		// Query using named query
		List results = this.findResultByNamedQueryAndNamedParam("AydenPaymentMatchingExcessAmount", paramNames, values);
		return results;
	}

	@Override
	public List<TmtbNonBillableTxnCrca> getAydenPaymentMatchingBreakdownReport(String settlementStartDate, String settlementEndDate, String batchNo, String recordType1, String recordType2) {

		// Create Named Params & Values
		String[] paramNames = new String[] { "settlementStartDate", "settlementEndDate", "batchNo","recordType1","recordType2"};
		String[] values = new String[] { settlementStartDate, settlementEndDate, batchNo ,recordType1, recordType2};
		// Query using named query
		List results = this.findResultByNamedQueryAndNamedParam("AydenPaymentMatchingBreakdownAmount", paramNames, values);
		return results;
	}

}