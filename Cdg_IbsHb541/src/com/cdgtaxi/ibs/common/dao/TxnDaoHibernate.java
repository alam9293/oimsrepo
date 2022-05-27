package com.cdgtaxi.ibs.common.dao;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.Subqueries;
import org.hibernate.resource.transaction.spi.TransactionStatus;

import com.cdgtaxi.ibs.common.ConfigurableConstants;
import com.cdgtaxi.ibs.common.NonConfigurableConstants;
import com.cdgtaxi.ibs.common.model.BmtbInvoiceHeader;
import com.cdgtaxi.ibs.common.model.BmtbInvoiceTxn;
import com.cdgtaxi.ibs.common.model.IttbCpGuestProduct;
import com.cdgtaxi.ibs.common.model.IttbFmsDrvrRfndColReq;
import com.cdgtaxi.ibs.common.model.IttbTripsTxn;
import com.cdgtaxi.ibs.common.model.IttbTripsTxnReq;
import com.cdgtaxi.ibs.common.model.TempAcctMapping;
import com.cdgtaxi.ibs.common.model.TmtbAcquireTxn;
import com.cdgtaxi.ibs.common.model.TmtbTxnReviewReq;
import com.cdgtaxi.ibs.common.model.TmtbTxnReviewReqFlow;
import com.cdgtaxi.ibs.common.model.VwIntfSetlForIb;
import com.cdgtaxi.ibs.common.model.VwIntfTripsForIb;
import com.cdgtaxi.ibs.master.model.MstbGstDetail;
import com.cdgtaxi.ibs.txn.ui.TxnSearchCriteria;
import com.cdgtaxi.ibs.util.DateUtil;
import com.cdgtaxi.ibs.util.LoggerUtil;
import com.mchange.v2.c3p0.C3P0ProxyConnection;

import oracle.sql.ARRAY;
import oracle.sql.ArrayDescriptor;


public class TxnDaoHibernate extends GenericDaoHibernate implements TxnDao{
	private static final int MAX_ROW = 1;
	private static final int MAX_TRIPS = 200;
	private static Logger logger = Logger.getLogger(TxnDaoHibernate.class);

	public boolean createTxn(TmtbAcquireTxn newTxn, String user){
		logger.info("createTxn(TmtbAcquireTxn newTxn)");
		try
		{
			this.save(newTxn, user);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public boolean updateTxn(TmtbAcquireTxn newTxn, String user){
		logger.info("updateTxn(TmtbAcquireTxn newTxn)");
		//BigDecimal nextValue = this.getNextSequenceNo(Sequence.AMTB_APPLICATION_SQ1);
		//newApplication.setApplicationNo(StringUtil.appendLeft(nextValue.toString(), 8, "0"));
		try
		{
			this.update(newTxn, user);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	
	public boolean updateTxn(TmtbAcquireTxn newTxn){
		logger.info("updateTxn(TmtbAcquireTxn newTxn without user)");
		
		try
		{
			this.update(newTxn);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	
	public BigDecimal getGstByTripDate(java.util.Date tripDt){
		DetachedCriteria criteria = DetachedCriteria.forClass(MstbGstDetail.class);
		criteria.add(Restrictions.lt("effectiveDt", tripDt));
		criteria.addOrder(Order.desc("effectiveDt"));
		
		List results = this.findMaxResultByCriteria(criteria, MAX_ROW);
		if(results.isEmpty()) return new BigDecimal(1);
		else{
			MstbGstDetail gstDetail = (MstbGstDetail) results.get(0);
			return gstDetail.getGst();
		}
	}
	
	public BmtbInvoiceHeader getInvoiceNo(int txnID){
		DetachedCriteria criteria = DetachedCriteria.forClass(BmtbInvoiceHeader.class);
		DetachedCriteria invoiceSummaryCriteria = criteria.createCriteria("bmtbInvoiceSummaries", Criteria.LEFT_JOIN);
		DetachedCriteria invoiceDetailCriteria = invoiceSummaryCriteria.createCriteria("bmtbInvoiceDetails", Criteria.LEFT_JOIN);
		DetachedCriteria invoiceTxnCriteria = invoiceDetailCriteria.createCriteria("bmtbInvoiceTxns", Criteria.LEFT_JOIN);
		
		invoiceTxnCriteria.add(Restrictions.eq("acquireTxnNo", txnID));
		invoiceTxnCriteria.addOrder(Order.desc("invoiceTxnNo"));
		
		List results = this.findMaxResultByCriteria(criteria, MAX_ROW);
		if(results.isEmpty()) return null;
		else{
			return (BmtbInvoiceHeader) results.get(0);
		}
	}
	
	public BmtbInvoiceTxn getInvoiceTxn(int txnID)
	{
		DetachedCriteria criteria = DetachedCriteria.forClass(BmtbInvoiceTxn.class);
		
		criteria.add(Restrictions.eq("acquireTxnNo", txnID));
		
		List results = this.findMaxResultByCriteria(criteria, MAX_ROW);
		if(results.isEmpty()) return null;
		else{
			return (BmtbInvoiceTxn) results.get(0);
		}
	}
	
	public List<TmtbTxnReviewReq> getRemark(String jobNo, Integer txnNo, String txnType)
	{
		DetachedCriteria txnCriteria = DetachedCriteria.forClass(TmtbTxnReviewReq.class);
		
		txnCriteria.createCriteria("tmtbAcquireTxn" , "acquireTxn", Criteria.LEFT_JOIN);
		txnCriteria.createCriteria("acquireTxn.mstbMasterTableByServiceProvider", Criteria.LEFT_JOIN);
		txnCriteria.createCriteria("acquireTxn.pmtbProduct", Criteria.LEFT_JOIN);
		txnCriteria.createCriteria("acquireTxn.pmtbProductType", Criteria.LEFT_JOIN);
		
		txnCriteria.createCriteria("tmtbTxnReviewReqFlows", "reviewReqFlow", Criteria.LEFT_JOIN);
		txnCriteria.createCriteria("reviewReqFlow.satbUser", Criteria.LEFT_JOIN);
		
		if(jobNo != null && !"".equals(jobNo))
			txnCriteria.add(Restrictions.eq("acquireTxn.jobNo", jobNo));
		if(txnNo > 0 && txnNo != null)
		{
				txnCriteria.add(Restrictions.sqlRestriction("this_.ACQUIRE_TXN_NO <= '"+txnNo+"' "));
		}
		
		txnCriteria.add(Restrictions.sqlRestriction(" (reviewreqf5_.TO_STATUS != 'A' AND reviewreqf5_.TO_STATUS != 'R')"));
		
		txnCriteria.addOrder(Order.desc("txnReviewReqNo"));	
		
		List results = this.findDefaultMaxResultByCriteria(txnCriteria);
		
		return results;
	}
	
	public List<TmtbAcquireTxn> getTxns(TxnSearchCriteria txnSearchCriteria){
		
		boolean searchDate = false;
		boolean externalCard = false;
		logger.info("Retriving data by Txn Search Criteria");
		DetachedCriteria txnCriteria = DetachedCriteria.forClass(TmtbAcquireTxn.class);
		DetachedCriteria txnExtCriteria = DetachedCriteria.forClass(TmtbAcquireTxn.class);
		// Base
		DetachedCriteria acctCriteria = txnCriteria.createCriteria("amtbAccount", Criteria.LEFT_JOIN);
		// Parent - could be a division or corp
		DetachedCriteria parentAcctCriteria = acctCriteria.createCriteria("amtbAccount", Criteria.LEFT_JOIN);
		// Parent - could be a corp if the previous one is a division
		parentAcctCriteria.createCriteria("amtbAccount", Criteria.LEFT_JOIN);
		DetachedCriteria productCriteria = txnCriteria.createCriteria("pmtbProduct", "product", Criteria.LEFT_JOIN);
		DetachedCriteria productTypeCriteria = txnCriteria.createCriteria("pmtbProductType", Criteria.LEFT_JOIN);
		
		// For external card
		// Base
		DetachedCriteria acctExtCriteria = txnExtCriteria.createCriteria("amtbAccount", Criteria.LEFT_JOIN);
		// Parent - could be a division or corp
		DetachedCriteria parentExtAcctCriteria = acctExtCriteria.createCriteria("amtbAccount", Criteria.LEFT_JOIN);
		// Parent - could be a corp if the previous one is a division
		parentExtAcctCriteria.createCriteria("amtbAccount", Criteria.LEFT_JOIN);
		// seems like not required
		//DetachedCriteria productExtCriteria = txnCriteria.createCriteria("pmtbProduct", "product", Criteria.LEFT_JOIN);
		DetachedCriteria productTypeExtCriteria = txnExtCriteria.createCriteria("pmtbProductType", Criteria.LEFT_JOIN);
		
		// Search via trip job no
		if(txnSearchCriteria.getJobNo() != null && !"".equals(txnSearchCriteria.getJobNo()))
		{
			txnCriteria.add(Restrictions.eq("jobNo", txnSearchCriteria.getJobNo()));
			txnExtCriteria.add(Restrictions.eq("jobNo", txnSearchCriteria.getJobNo()));
		}
		
		// Search via card no (both start and end must be together or just the start card no)
		if(txnSearchCriteria.getCardNoStart() != null && !"".equals(txnSearchCriteria.getCardNoStart()))
		{
			externalCard = true;
			if (txnSearchCriteria.getCardNoEnd() != null && !"".equals(txnSearchCriteria.getCardNoEnd()))
			{
				// do a range selection if there is a card end no
				txnCriteria.add(Restrictions.and(Restrictions.sqlRestriction("to_number(product4_.CARD_NO) >= " + txnSearchCriteria.getCardNoStart()), 
						Restrictions.sqlRestriction("to_number(product4_.CARD_NO) <= " + txnSearchCriteria.getCardNoEnd())));
				
				txnExtCriteria.add(Restrictions.and(Restrictions.sqlRestriction("to_number(EXTERNAL_CARD_NO) >= " + txnSearchCriteria.getCardNoStart()), 
						Restrictions.sqlRestriction("to_number(EXTERNAL_CARD_NO) <= " + txnSearchCriteria.getCardNoEnd())));
				//productCriteria.add();
				//productCriteria.add(Restrictions.sqlRestriction("to"));
			}
			else
			{
				// do a "like" if there is no card end no
				txnCriteria.add(Restrictions.like("product.cardNo", txnSearchCriteria.getCardNoStart(), MatchMode.START));
				
				txnExtCriteria.add(Restrictions.like("externalCardNo", txnSearchCriteria.getCardNoStart(), MatchMode.START));
			}
		}
		
		boolean setCriteriaTripDate = false;
		// Search via Product Type
		if(txnSearchCriteria.getProductType() != null && !"".equals(txnSearchCriteria.getProductType())
				&& txnSearchCriteria.getTripStartDate() != null
				&& txnSearchCriteria.getTripEndDate() != null)
		{
			logger.info("Product Type = " + txnSearchCriteria.getProductType());
			logger.info("StartDate = " + txnSearchCriteria.getTripStartDate());
			logger.info("EndDate = " + txnSearchCriteria.getTripEndDate());

			productTypeCriteria.add(Restrictions.eq("productTypeId", txnSearchCriteria.getProductType()));
			productTypeExtCriteria.add(Restrictions.eq("productTypeId", txnSearchCriteria.getProductType()));
			
			if (txnSearchCriteria.getTripStartDate() != null && !"".equals(txnSearchCriteria.getTripStartDate()))
			{
				// Latest rule: only retrieved from the tripStartDt field
				Timestamp tripStart = DateUtil.convertStrToTimestamp(txnSearchCriteria.getTripStartDate(), DateUtil.TRIPS_DATE_FORMAT);
				Timestamp tripEnd = DateUtil.getCurrentTimestamp();
				//txnCriteria.add(Restrictions.ge("tripStartDt", DateUtil.convertStrToTimestamp(txnSearchCriteria.getTripStartDate(), DateUtil.TRIPS_DATE_FORMAT)));
				if (txnSearchCriteria.getTripEndDate() != null && !"".equals(txnSearchCriteria.getTripEndDate()))
					//txnCriteria.add(Restrictions.le("tripEndDt", DateUtil.convertStrToTimestamp(txnSearchCriteria.getTripEndDate(), DateUtil.TRIPS_DATE_FORMAT)));
					tripEnd = DateUtil.convertStrToTimestamp(txnSearchCriteria.getTripEndDate(), DateUtil.TRIPS_DATE_FORMAT);
				//else
					//txnCriteria.add(Restrictions.le("tripEndDt", DateUtil.getCurrentTimestamp()));
				txnCriteria.add(Restrictions.between("tripStartDt", tripStart, tripEnd));
				txnExtCriteria.add(Restrictions.between("tripStartDt", tripStart, tripEnd));
				setCriteriaTripDate = true;
			}
			// add in taxi no
			if(txnSearchCriteria.getTaxiNo() != null && !"".equals(txnSearchCriteria.getTaxiNo()))
			{
					txnCriteria.add(Restrictions.eq("taxiNo", txnSearchCriteria.getTaxiNo()));
					txnExtCriteria.add(Restrictions.eq("taxiNo", txnSearchCriteria.getTaxiNo()));
			}
		}
		// Search via Taxi No
		else if(txnSearchCriteria.getTaxiNo() != null && !"".equals(txnSearchCriteria.getTaxiNo())
				&& txnSearchCriteria.getTripStartDate() != null
				&& txnSearchCriteria.getTripEndDate() != null)
		{
			txnCriteria.add(Restrictions.eq("taxiNo", txnSearchCriteria.getTaxiNo()));
			txnExtCriteria.add(Restrictions.eq("taxiNo", txnSearchCriteria.getTaxiNo()));
			// Must include product type, trip start date and trip end date in case it is not
			// searching via product type
			// If trip start date is not null, we must always search via trip start date
			// If trip end date is not null, we must always search via trip end date
			if (txnSearchCriteria.getTripStartDate() != null && !setCriteriaTripDate && !"".equals(txnSearchCriteria.getTripStartDate()))
			{
				Timestamp tripStart = DateUtil.convertStrToTimestamp(txnSearchCriteria.getTripStartDate(), DateUtil.TRIPS_DATE_FORMAT);
				Timestamp tripEnd = DateUtil.getCurrentTimestamp();
				// Latest rule: only retrieved from the tripStartDt field
				//txnCriteria.add(Restrictions.ge("tripStartDt", DateUtil.convertStrToTimestamp(txnSearchCriteria.getTripStartDate(), DateUtil.TRIPS_DATE_FORMAT)));
				if (txnSearchCriteria.getTripEndDate() != null && !"".equals(txnSearchCriteria.getTripEndDate()))
					//txnCriteria.add(Restrictions.le("tripEndDt", DateUtil.convertStrToTimestamp(txnSearchCriteria.getTripEndDate(), DateUtil.TRIPS_DATE_FORMAT)));
					tripEnd = DateUtil.convertStrToTimestamp(txnSearchCriteria.getTripEndDate(), DateUtil.TRIPS_DATE_FORMAT);
				//else
					//txnCriteria.add(Restrictions.le("tripEndDt", DateUtil.getCurrentTimestamp()));
				txnCriteria.add(Restrictions.between("tripStartDt", tripStart, tripEnd));
				txnExtCriteria.add(Restrictions.between("tripStartDt", tripStart, tripEnd));

			}
			//else
			//{
				//txnCriteria.add(Restrictions.sqlRestriction("to_number(to_char(TRIP_START_DT,'HH24MISS')) >= " + txnSearchCriteria.getTripStartTime()));
				//txnCriteria.add(Restrictions.sqlRestriction("to_number(to_char(TRIP_END_DT,'HH24MISS')) <= " + txnSearchCriteria.getTripEndTime()));
			//}
			// add in taxi no
			if(txnSearchCriteria.getProductType() != null && !"".equals(txnSearchCriteria.getProductType()))
			{
				productTypeExtCriteria.add(Restrictions.eq("productTypeId", txnSearchCriteria.getProductType()));
				productTypeCriteria.add(Restrictions.eq("productTypeId", txnSearchCriteria.getProductType()));
			}
		}
		else
		{
			if (txnSearchCriteria.getTaxiNo() != null && !"".equals(txnSearchCriteria.getTaxiNo()))
				txnCriteria.add(Restrictions.eq("taxiNo", txnSearchCriteria.getTaxiNo()));
			if (txnSearchCriteria.getTripStartDate() != null && !"".equals(txnSearchCriteria.getTripStartDate()))
			{
				// Latest rule: only retrieved from the tripStartDt field
				Timestamp tripStart = DateUtil.convertStrToTimestamp(txnSearchCriteria.getTripStartDate(), DateUtil.TRIPS_DATE_FORMAT);
				Timestamp tripEnd = DateUtil.getCurrentTimestamp();
				//txnCriteria.add(Restrictions.ge("tripStartDt", DateUtil.convertStrToTimestamp(txnSearchCriteria.getTripStartDate(), DateUtil.TRIPS_DATE_FORMAT)));
				if (txnSearchCriteria.getTripEndDate() != null && !"".equals(txnSearchCriteria.getTripEndDate()))
					//txnCriteria.add(Restrictions.le("tripEndDt", DateUtil.convertStrToTimestamp(txnSearchCriteria.getTripEndDate(), DateUtil.TRIPS_DATE_FORMAT)));
					tripEnd = DateUtil.convertStrToTimestamp(txnSearchCriteria.getTripEndDate(), DateUtil.TRIPS_DATE_FORMAT);
				//else
					//txnCriteria.add(Restrictions.le("tripEndDt", DateUtil.getCurrentTimestamp()));
				txnCriteria.add(Restrictions.between("tripStartDt", tripStart, tripEnd));
				txnExtCriteria.add(Restrictions.between("tripStartDt", tripStart, tripEnd));
			}
			if (txnSearchCriteria.getProductType() != null && !"".equals(txnSearchCriteria.getProductType()))
			{
				productTypeCriteria.add(Restrictions.eq("productTypeId", txnSearchCriteria.getProductType()));
				productTypeExtCriteria.add(Restrictions.eq("productTypeId", txnSearchCriteria.getProductType()));
			}
		}
		
		if(txnSearchCriteria.getAmtbAccount() != null )
		{
			txnCriteria.add(Restrictions.eq("amtbAccount", txnSearchCriteria.getAmtbAccount()));
			txnExtCriteria.add(Restrictions.eq("amtbAccount", txnSearchCriteria.getAmtbAccount()));
		}
		if(txnSearchCriteria.getFareAmt() != null && !"".equals(txnSearchCriteria.getFareAmt()))
		{
			txnCriteria.add(Restrictions.eq("fareAmt", new BigDecimal(txnSearchCriteria.getFareAmt())));
			txnExtCriteria.add(Restrictions.eq("fareAmt", new BigDecimal(txnSearchCriteria.getFareAmt())));
		}
		if(txnSearchCriteria.getNric() != null && !"".equals(txnSearchCriteria.getNric()) )
		{
			txnCriteria.add(Restrictions.eq("nric", txnSearchCriteria.getNric()));
			txnExtCriteria.add(Restrictions.eq("nric", txnSearchCriteria.getNric()));
		}
		if(txnSearchCriteria.getTxnStatus() != null && !"".equals(txnSearchCriteria.getTxnStatus()) )
		{
			txnCriteria.add(Restrictions.eq("txnStatus", txnSearchCriteria.getTxnStatus()));
			txnExtCriteria.add(Restrictions.eq("txnStatus", txnSearchCriteria.getTxnStatus()));
		}
		if(txnSearchCriteria.getSalesDraftNo() != null && !"".equals(txnSearchCriteria.getSalesDraftNo()) )
		{
			txnCriteria.add(Restrictions.eq("salesDraftNo", txnSearchCriteria.getSalesDraftNo()));
			txnExtCriteria.add(Restrictions.eq("salesDraftNo", txnSearchCriteria.getSalesDraftNo()));
		}
		if(txnSearchCriteria.getPickup() != null && !"".equals(txnSearchCriteria.getPickup()) )
		{
			txnCriteria.add(Restrictions.ilike("pickupAddress", txnSearchCriteria.getPickup(), MatchMode.ANYWHERE));
			txnExtCriteria.add(Restrictions.ilike("pickupAddress", txnSearchCriteria.getPickup(), MatchMode.ANYWHERE));
		}
		if(txnSearchCriteria.getDestination() != null && !"".equals(txnSearchCriteria.getDestination()) )
		{
			txnCriteria.add(Restrictions.ilike("destination", txnSearchCriteria.getDestination(), MatchMode.ANYWHERE));
			txnExtCriteria.add(Restrictions.ilike("destination", txnSearchCriteria.getDestination(), MatchMode.ANYWHERE));
		}

		txnCriteria.addOrder(Order.asc("tripStartDt"));
		txnExtCriteria.addOrder(Order.asc("tripStartDt"));
		
		List<TmtbAcquireTxn> results = this.findDefaultMaxResultByCriteria(txnCriteria);
		if (externalCard)
		{
			List<TmtbAcquireTxn> extResults = this.findDefaultMaxResultByCriteria(txnExtCriteria);
			results.addAll(extResults);
		}
		
		if(results.isEmpty()) return null;
		else 
		{
			if (results.size() <= ConfigurableConstants.getMaxQueryResult())
				return  results;	
			else
				return results.subList(0, ConfigurableConstants.getMaxQueryResult());
		}
	}
	
	public TmtbAcquireTxn getTxn(String txnID){
		
		logger.info("Retriving data by Txn Search Criteria");
		DetachedCriteria txnCriteria = DetachedCriteria.forClass(TmtbAcquireTxn.class);
		// Base
		DetachedCriteria acctCriteria = txnCriteria.createCriteria("amtbAccount", Criteria.LEFT_JOIN);
		// Parent - could be a division or corp
		DetachedCriteria parentAcctCriteria = acctCriteria.createCriteria("amtbAccount", Criteria.LEFT_JOIN);
		// Parent - could be a corp if the previous one is a division
		DetachedCriteria parentAcctLvl2Criteria = parentAcctCriteria.createCriteria("amtbAccount", Criteria.LEFT_JOIN);
		parentAcctLvl2Criteria.createCriteria("amtbAcctMainContacts", "parentLvl2MainBillingContacts", Criteria.LEFT_JOIN);
		parentAcctCriteria.createCriteria("amtbAcctMainContacts", "parentMainBillingContacts", Criteria.LEFT_JOIN);
		DetachedCriteria mainBillingContacts = acctCriteria.createCriteria("amtbAcctMainContacts", "mainBillingContacts", Criteria.LEFT_JOIN);
		
		
		txnCriteria.createCriteria("mstbMasterTableByTripType", Criteria.LEFT_JOIN);
		txnCriteria.createCriteria("mstbMasterTableByVehicleType", Criteria.LEFT_JOIN);
		txnCriteria.createCriteria("mstbMasterTableByServiceProvider", Criteria.LEFT_JOIN);	
		txnCriteria.createCriteria("mstbMasterTableByJobType", Criteria.LEFT_JOIN);	
		txnCriteria.createCriteria("mstbMasterTableByVehicleModel", Criteria.LEFT_JOIN);
		txnCriteria.createCriteria("tmtbTxnReviewReqs", Criteria.LEFT_JOIN);

		txnCriteria.createCriteria("pmtbProduct", Criteria.LEFT_JOIN);
		txnCriteria.createCriteria("pmtbProductType", Criteria.LEFT_JOIN);
		
		int txnNo = Integer.parseInt(txnID);
		txnCriteria.add(Restrictions.eq("acquireTxnNo", txnNo));
		
		mainBillingContacts.add(Restrictions.or
				(Restrictions.eq("mainBillingContacts.comp_id.mainContactType", NonConfigurableConstants.MAIN_CONTACT_TYPE_BILLING), 
						Restrictions.or(Restrictions.eq("parentMainBillingContacts.comp_id.mainContactType", NonConfigurableConstants.MAIN_CONTACT_TYPE_BILLING), 
								Restrictions.eq("parentLvl2MainBillingContacts.comp_id.mainContactType", NonConfigurableConstants.MAIN_CONTACT_TYPE_BILLING))));

		List<TmtbAcquireTxn> results = this.findDefaultMaxResultByCriteria(txnCriteria);
		if(results.isEmpty()) return null;
		else return results.get(0);
	}
	
	public boolean hasActiveOrBilledTripByJobNo(String jobNo){
		
		logger.info("Retriving data by Txn Search Criteria");
		DetachedCriteria txnCriteria = DetachedCriteria.forClass(TmtbAcquireTxn.class);
		
		txnCriteria.add(Restrictions.eq("jobNo", jobNo));
		txnCriteria.add(Restrictions.in("txnStatus", new String[]{NonConfigurableConstants.TRANSACTION_STATUS_ACTIVE, NonConfigurableConstants.TRANSACTION_STATUS_BILLED}));
		
		List<TmtbAcquireTxn> results = this.findDefaultMaxResultByCriteria(txnCriteria);
		if(results.isEmpty()) return false;
		else return true;
	}
	
	
	public List<TmtbTxnReviewReq> getTxnReqs(){
		
		logger.info("Retriving data by Txn Reqs");
		DetachedCriteria txnReqCriteria = DetachedCriteria.forClass(TmtbTxnReviewReq.class);
		
		//-> Trips
		txnReqCriteria.createCriteria("tmtbAcquireTxn", Criteria.LEFT_JOIN);
		//-> Product -> Product Type
		txnReqCriteria.createCriteria("pmtbProduct", Criteria.LEFT_JOIN);
		txnReqCriteria.createCriteria("pmtbProductType", Criteria.LEFT_JOIN);
		//-> Flow (Retrieve request pending for approval)
		DetachedCriteria txnReqFlowCriteria = txnReqCriteria.createCriteria("tmtbTxnReviewReqFlows", Criteria.LEFT_JOIN);
		DetachedCriteria txnReq2FlowCriteria = txnReqFlowCriteria.createCriteria("tmtbTxnReviewReqFlows", Criteria.LEFT_JOIN);
		
		txnReqFlowCriteria.add(Restrictions.ne("toStatus", NonConfigurableConstants.TRANSACTION_REQUEST_STATUS_APPROVED));
		txnReqFlowCriteria.add(Restrictions.or(
				Restrictions.eq("toStatus", NonConfigurableConstants.TRANSACTION_REQUEST_STATUS_PENDING_REFUND), Restrictions.eq("toStatus", NonConfigurableConstants.TRANSACTION_REQUEST_STATUS_PENDING_VOID)));
		txnReq2FlowCriteria.add(Restrictions.isNull("toStatus"));
		
		txnReqCriteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);	
		
		//Due to left join, the result returned more than max result.
		//End search result may only retrieve total entities less than max result.
		DetachedCriteria subqueryCriteria = this.createMaxResultSubquery(txnReqCriteria, "txn_Review_Req_No"); 
		txnReqCriteria.add(Subqueries.propertyIn("txnReviewReqNo", subqueryCriteria));

		List<TmtbTxnReviewReq> results = this.findAllByCriteria(txnReqCriteria);
		if(results.isEmpty()) return null;
		else return results;
	}
	
	public List<TmtbTxnReviewReq> getTxnReqs(String txnID){
		
		logger.info("Retriving data by Txn Reqs");
		DetachedCriteria txnReqCriteria = DetachedCriteria.forClass(TmtbTxnReviewReq.class);
		DetachedCriteria acctCriteria = txnReqCriteria.createCriteria("amtbAccount", Criteria.LEFT_JOIN);
		DetachedCriteria parentAcctCriteria = acctCriteria.createCriteria("amtbAccount", Criteria.LEFT_JOIN);
		DetachedCriteria parent2AcctCriteria = parentAcctCriteria.createCriteria("amtbAccount", Criteria.LEFT_JOIN);
		parent2AcctCriteria.createCriteria("amtbAcctMainContacts", "parentLvl2MainBillingContacts", Criteria.LEFT_JOIN);
		parentAcctCriteria.createCriteria("amtbAcctMainContacts", "parentMainBillingContacts", Criteria.LEFT_JOIN);
		
		DetachedCriteria mainBillingContacts = acctCriteria.createCriteria("amtbAcctMainContacts", "mainBillingContacts", Criteria.LEFT_JOIN);
		mainBillingContacts.add(Restrictions.or(Restrictions.isNull("mainBillingContacts.comp_id.amtbAccount"),Restrictions.or
				(Restrictions.eq("mainBillingContacts.comp_id.mainContactType", NonConfigurableConstants.MAIN_CONTACT_TYPE_BILLING), 
						Restrictions.or(Restrictions.eq("parentMainBillingContacts.comp_id.mainContactType", NonConfigurableConstants.MAIN_CONTACT_TYPE_BILLING), 
								Restrictions.eq("parentLvl2MainBillingContacts.comp_id.mainContactType", NonConfigurableConstants.MAIN_CONTACT_TYPE_BILLING)))));

		DetachedCriteria tmtbAcquireTxnCriteria = txnReqCriteria.createCriteria("tmtbAcquireTxn", Criteria.LEFT_JOIN);
		DetachedCriteria acctAcquireTxnCriteria = tmtbAcquireTxnCriteria.createCriteria("amtbAccount", Criteria.LEFT_JOIN);
		DetachedCriteria parentAcctAcquireTxnCriteria = acctAcquireTxnCriteria.createCriteria("amtbAccount", Criteria.LEFT_JOIN);
		parentAcctAcquireTxnCriteria.createCriteria("amtbAccount", Criteria.LEFT_JOIN);
		
		tmtbAcquireTxnCriteria.createCriteria("mstbMasterTableByTripType", Criteria.LEFT_JOIN);
		tmtbAcquireTxnCriteria.createCriteria("mstbMasterTableByVehicleType", Criteria.LEFT_JOIN);
		tmtbAcquireTxnCriteria.createCriteria("mstbMasterTableByServiceProvider", Criteria.LEFT_JOIN);
		tmtbAcquireTxnCriteria.createCriteria("mstbMasterTableByJobType", Criteria.LEFT_JOIN);	
		tmtbAcquireTxnCriteria.createCriteria("mstbMasterTableByVehicleModel", Criteria.LEFT_JOIN);	

		
		tmtbAcquireTxnCriteria.createCriteria("pmtbProduct", Criteria.LEFT_JOIN);
		tmtbAcquireTxnCriteria.createCriteria("pmtbProductType", Criteria.LEFT_JOIN);
		
		txnReqCriteria.createCriteria("mstbMasterTableByTripType", Criteria.LEFT_JOIN);
		txnReqCriteria.createCriteria("mstbMasterTableByVehicleType", Criteria.LEFT_JOIN);
			
		txnReqCriteria.createCriteria("pmtbProduct", Criteria.LEFT_JOIN);
		txnReqCriteria.createCriteria("pmtbProductType", Criteria.LEFT_JOIN);
		// Base
		DetachedCriteria txnReqFlowCriteria = txnReqCriteria.createCriteria("tmtbTxnReviewReqFlows", Criteria.LEFT_JOIN);
		DetachedCriteria txnReq2FlowCriteria = txnReqFlowCriteria.createCriteria("tmtbTxnReviewReqFlows", Criteria.LEFT_JOIN);
		// Parent - could be a corp if the previous one is a division
		int txnNo = Integer.parseInt(txnID);
		tmtbAcquireTxnCriteria.add(Restrictions.eq("acquireTxnNo", txnNo));
		
		txnReqFlowCriteria.add(Restrictions.ne("toStatus", NonConfigurableConstants.TRANSACTION_REQUEST_STATUS_APPROVED));
		txnReqFlowCriteria.add(Restrictions.or(
				Restrictions.eq("toStatus", NonConfigurableConstants.TRANSACTION_REQUEST_STATUS_PENDING_REFUND), Restrictions.eq("toStatus", NonConfigurableConstants.TRANSACTION_REQUEST_STATUS_PENDING_VOID)));
		txnReq2FlowCriteria.add(Restrictions.isNull("toStatus"));
		
		txnReqCriteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);	
		
		//Due to left join, the result returned more than max result.
		//End search result may only retrieve total entities less than max result.
		DetachedCriteria subqueryCriteria = this.createMaxResultSubquery(txnReqCriteria, "txn_Review_Req_No"); 
		txnReqCriteria.add(Subqueries.propertyIn("txnReviewReqNo", subqueryCriteria));
		
		List<TmtbTxnReviewReq> results = this.findAllByCriteria(txnReqCriteria);
		
		if (results != null)
		{
			if(results.isEmpty()) return null;
			else return results;
		}
		else
			return null;
	}
	
	public List<TmtbTxnReviewReq> getTxnApprovedReqs(String txnID){
		
		logger.info("Retriving data by Txn Reqs");
		DetachedCriteria txnReqCriteria = DetachedCriteria.forClass(TmtbTxnReviewReq.class);
		DetachedCriteria acctCriteria = txnReqCriteria.createCriteria("amtbAccount", Criteria.LEFT_JOIN);
		DetachedCriteria parentAcctCriteria = acctCriteria.createCriteria("amtbAccount", Criteria.LEFT_JOIN);
		DetachedCriteria parent2AcctCriteria = parentAcctCriteria.createCriteria("amtbAccount", Criteria.LEFT_JOIN);
		parent2AcctCriteria.createCriteria("amtbAcctMainContacts", "parentLvl2MainBillingContacts", Criteria.LEFT_JOIN);
		parentAcctCriteria.createCriteria("amtbAcctMainContacts", "parentMainBillingContacts", Criteria.LEFT_JOIN);
		
		DetachedCriteria mainBillingContacts = acctCriteria.createCriteria("amtbAcctMainContacts", "mainBillingContacts", Criteria.LEFT_JOIN);
		mainBillingContacts.add(Restrictions.or(Restrictions.isNull("mainBillingContacts.comp_id.amtbAccount"),Restrictions.or
				(Restrictions.eq("mainBillingContacts.comp_id.mainContactType", NonConfigurableConstants.MAIN_CONTACT_TYPE_BILLING), 
						Restrictions.or(Restrictions.eq("parentMainBillingContacts.comp_id.mainContactType", NonConfigurableConstants.MAIN_CONTACT_TYPE_BILLING), 
								Restrictions.eq("parentLvl2MainBillingContacts.comp_id.mainContactType", NonConfigurableConstants.MAIN_CONTACT_TYPE_BILLING)))));

		DetachedCriteria tmtbAcquireTxnCriteria = txnReqCriteria.createCriteria("tmtbAcquireTxn", Criteria.LEFT_JOIN);
		DetachedCriteria acctAcquireTxnCriteria = tmtbAcquireTxnCriteria.createCriteria("amtbAccount", Criteria.LEFT_JOIN);
		DetachedCriteria parentAcctAcquireTxnCriteria = acctAcquireTxnCriteria.createCriteria("amtbAccount", Criteria.LEFT_JOIN);
		parentAcctAcquireTxnCriteria.createCriteria("amtbAccount", Criteria.LEFT_JOIN);
		
		tmtbAcquireTxnCriteria.createCriteria("mstbMasterTableByTripType", Criteria.LEFT_JOIN);
		tmtbAcquireTxnCriteria.createCriteria("mstbMasterTableByVehicleType", Criteria.LEFT_JOIN);
		tmtbAcquireTxnCriteria.createCriteria("mstbMasterTableByServiceProvider", Criteria.LEFT_JOIN);
		tmtbAcquireTxnCriteria.createCriteria("mstbMasterTableByJobType", Criteria.LEFT_JOIN);	
		tmtbAcquireTxnCriteria.createCriteria("mstbMasterTableByVehicleModel", Criteria.LEFT_JOIN);	

		
		tmtbAcquireTxnCriteria.createCriteria("pmtbProduct", Criteria.LEFT_JOIN);
		tmtbAcquireTxnCriteria.createCriteria("pmtbProductType", Criteria.LEFT_JOIN);
		
		txnReqCriteria.createCriteria("mstbMasterTableByTripType", Criteria.LEFT_JOIN);
		txnReqCriteria.createCriteria("mstbMasterTableByVehicleType", Criteria.LEFT_JOIN);
			
		txnReqCriteria.createCriteria("pmtbProduct", Criteria.LEFT_JOIN);
		txnReqCriteria.createCriteria("pmtbProductType", Criteria.LEFT_JOIN);
		// Base
		DetachedCriteria txnReqFlowCriteria = txnReqCriteria.createCriteria("tmtbTxnReviewReqFlows", Criteria.LEFT_JOIN);
		DetachedCriteria txnReq2FlowCriteria = txnReqFlowCriteria.createCriteria("tmtbTxnReviewReqFlows", Criteria.LEFT_JOIN);
		// Parent - could be a corp if the previous one is a division
		int txnNo = Integer.parseInt(txnID);
		tmtbAcquireTxnCriteria.add(Restrictions.eq("acquireTxnNo", txnNo));
		
		txnReqFlowCriteria.add(Restrictions.eq("toStatus", NonConfigurableConstants.TRANSACTION_REQUEST_STATUS_APPROVED));
		
		txnReqCriteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);	
		
		//Due to left join, the result returned more than max result.
		//End search result may only retrieve total entities less than max result.
		DetachedCriteria subqueryCriteria = this.createMaxResultSubquery(txnReqCriteria, "txn_Review_Req_No"); 
		txnReqCriteria.add(Subqueries.propertyIn("txnReviewReqNo", subqueryCriteria));
		
		List<TmtbTxnReviewReq> results = this.findAllByCriteria(txnReqCriteria);
		
		if (results != null)
		{
			if(results.isEmpty()) return null;
			else return results;
		}
		else
			return null;
	}
	
	public TmtbTxnReviewReq getTxnReq(String txnReqID){
		
		logger.info("Retriving data by Txn Req ID");
		DetachedCriteria txnReqCriteria = DetachedCriteria.forClass(TmtbTxnReviewReq.class);

		// Parent - could be a division or corp
		DetachedCriteria acctCriteria = txnReqCriteria.createCriteria("amtbAccount", Criteria.LEFT_JOIN);
		DetachedCriteria parentAcctCriteria = acctCriteria.createCriteria("amtbAccount", Criteria.LEFT_JOIN);
		DetachedCriteria parent2AcctCriteria = parentAcctCriteria.createCriteria("amtbAccount", Criteria.LEFT_JOIN);
		parent2AcctCriteria.createCriteria("amtbAcctMainContacts", "oldParentLvl2MainBillingContacts", Criteria.LEFT_JOIN);
		parentAcctCriteria.createCriteria("amtbAcctMainContacts", "oldParentMainBillingContacts", Criteria.LEFT_JOIN);
		
		DetachedCriteria oldMainBillingContacts = acctCriteria.createCriteria("amtbAcctMainContacts", "oldMainBillingContacts", Criteria.LEFT_JOIN);
		oldMainBillingContacts.add(Restrictions.or(Restrictions.isNull("oldMainBillingContacts.comp_id.amtbAccount"),Restrictions.or
				(Restrictions.eq("oldMainBillingContacts.comp_id.mainContactType", NonConfigurableConstants.MAIN_CONTACT_TYPE_BILLING), 
						Restrictions.or(Restrictions.eq("oldParentMainBillingContacts.comp_id.mainContactType", NonConfigurableConstants.MAIN_CONTACT_TYPE_BILLING), 
								Restrictions.eq("oldParentLvl2MainBillingContacts.comp_id.mainContactType", NonConfigurableConstants.MAIN_CONTACT_TYPE_BILLING)))));

		
		DetachedCriteria tmtbAcquireTxnCriteria = txnReqCriteria.createCriteria("tmtbAcquireTxn", Criteria.LEFT_JOIN);
		DetachedCriteria acctAcquireTxnCriteria = tmtbAcquireTxnCriteria.createCriteria("amtbAccount", Criteria.LEFT_JOIN);
		DetachedCriteria parentAcctAcquireTxnCriteria = acctAcquireTxnCriteria.createCriteria("amtbAccount", Criteria.LEFT_JOIN);
		DetachedCriteria parent2AcctAcquireTxnCriteria = parentAcctAcquireTxnCriteria.createCriteria("amtbAccount", Criteria.LEFT_JOIN);

		parent2AcctAcquireTxnCriteria.createCriteria("amtbAcctMainContacts", "parentLvl2MainBillingContacts", Criteria.LEFT_JOIN);
		parentAcctAcquireTxnCriteria.createCriteria("amtbAcctMainContacts", "parentMainBillingContacts", Criteria.LEFT_JOIN);
		
		DetachedCriteria mainBillingContacts = acctAcquireTxnCriteria.createCriteria("amtbAcctMainContacts", "mainBillingContacts", Criteria.LEFT_JOIN);
		mainBillingContacts.add(Restrictions.or(Restrictions.isNull("mainBillingContacts.comp_id.amtbAccount"),Restrictions.or
				(Restrictions.eq("mainBillingContacts.comp_id.mainContactType", NonConfigurableConstants.MAIN_CONTACT_TYPE_BILLING), 
						Restrictions.or(Restrictions.eq("parentMainBillingContacts.comp_id.mainContactType", NonConfigurableConstants.MAIN_CONTACT_TYPE_BILLING), 
								Restrictions.eq("parentLvl2MainBillingContacts.comp_id.mainContactType", NonConfigurableConstants.MAIN_CONTACT_TYPE_BILLING)))));

		tmtbAcquireTxnCriteria.createCriteria("mstbMasterTableByTripType", Criteria.LEFT_JOIN);
		tmtbAcquireTxnCriteria.createCriteria("mstbMasterTableByVehicleType", Criteria.LEFT_JOIN);
		tmtbAcquireTxnCriteria.createCriteria("mstbMasterTableByServiceProvider", Criteria.LEFT_JOIN);
		tmtbAcquireTxnCriteria.createCriteria("mstbMasterTableByJobType", Criteria.LEFT_JOIN);	
		tmtbAcquireTxnCriteria.createCriteria("mstbMasterTableByVehicleModel", Criteria.LEFT_JOIN);	

		tmtbAcquireTxnCriteria.createCriteria("pmtbProduct", Criteria.LEFT_JOIN);
		tmtbAcquireTxnCriteria.createCriteria("pmtbProductType", Criteria.LEFT_JOIN);
		
		txnReqCriteria.createCriteria("mstbMasterTableByTripType", Criteria.LEFT_JOIN);
		txnReqCriteria.createCriteria("mstbMasterTableByVehicleType", Criteria.LEFT_JOIN);
		txnReqCriteria.createCriteria("mstbMasterTableByServiceProvider", Criteria.LEFT_JOIN);
		txnReqCriteria.createCriteria("mstbMasterTableByJobType", Criteria.LEFT_JOIN);
		txnReqCriteria.createCriteria("mstbMasterTableByVehicleModel", Criteria.LEFT_JOIN);

		
		txnReqCriteria.createCriteria("pmtbProduct", Criteria.LEFT_JOIN);
		txnReqCriteria.createCriteria("pmtbProductType", Criteria.LEFT_JOIN);
		
		DetachedCriteria txnReqFlowCriteria = txnReqCriteria.createCriteria("tmtbTxnReviewReqFlows", Criteria.LEFT_JOIN);
		txnReqFlowCriteria.createCriteria("tmtbTxnReviewReqFlows", Criteria.LEFT_JOIN);
		txnReqFlowCriteria.createCriteria("satbUser", Criteria.LEFT_JOIN);
		txnReqCriteria.add(Restrictions.eq("txnReviewReqNo", Integer.parseInt(txnReqID)));
		txnReqFlowCriteria.addOrder(Order.asc("txnReviewReqFlowNo"));

		List<TmtbTxnReviewReq> results = this.findAllByCriteria(txnReqCriteria);
		if(results.isEmpty()) return null;
		else return results.get(0);
	}
	
	public List<IttbFmsDrvrRfndColReq> getPendingFMSReq(String jobNo, String nric, String taxiNo, String paymentMode){
			
			logger.info("Retriving data by Job No");
			DetachedCriteria fmsDrvrRfndColReqCriteria = DetachedCriteria.forClass(IttbFmsDrvrRfndColReq.class);
			fmsDrvrRfndColReqCriteria.add(Restrictions.eq("reqStatus", NonConfigurableConstants.STATUS_PENDING));
			fmsDrvrRfndColReqCriteria.add(Restrictions.eq("jobNo", jobNo));
			fmsDrvrRfndColReqCriteria.add(Restrictions.eq("nric", nric));
			fmsDrvrRfndColReqCriteria.add(Restrictions.eq("taxiNo", taxiNo));
			fmsDrvrRfndColReqCriteria.add(Restrictions.eq("paymentMode", paymentMode));

			List<IttbFmsDrvrRfndColReq> results = this.findAllByCriteria(fmsDrvrRfndColReqCriteria);
			if(results.isEmpty()) return null;
			else return results;
	}
	
	public TmtbAcquireTxn getTxnByDraftNo(String salesDraftNo){
		
		logger.info("Retriving data by Draft No");
		DetachedCriteria txnCriteria = DetachedCriteria.forClass(TmtbAcquireTxn.class);
		// Base
		
		txnCriteria.add(Restrictions.eq("salesDraftNo", salesDraftNo));
		txnCriteria.add(Restrictions.or(Restrictions.or(Restrictions.or(Restrictions.eq("txnStatus", NonConfigurableConstants.TRANSACTION_STATUS_PENDING_EDIT_ACTIVE),
				Restrictions.eq("txnStatus", NonConfigurableConstants.TRANSACTION_STATUS_ACTIVE)),
				Restrictions.eq("txnStatus", NonConfigurableConstants.TRANSACTION_STATUS_PENDING_EDIT_ACTIVE)), 
				Restrictions.eq("txnStatus", NonConfigurableConstants.TRANSACTION_STATUS_BILLED)));
		List<TmtbAcquireTxn> results = this.findMaxResultByCriteria(txnCriteria, MAX_ROW);
		if(results.isEmpty()) return null;
			else return results.get(0);
	}
	
	public boolean hasTxn(String jobNo){
		DetachedCriteria criteria = DetachedCriteria.forClass(TmtbAcquireTxn.class);
		criteria.add(Restrictions.eq("jobNo", jobNo));
		
		
		List results = this.findMaxResultByCriteria(criteria, MAX_ROW);
		if(results.isEmpty()) return false;
		else
			return true;
	}
	
	public TmtbAcquireTxn getLatestTxnByJobNo(String jobNo){
		DetachedCriteria criteria = DetachedCriteria.forClass(TmtbAcquireTxn.class);
		criteria.add(Restrictions.eq("jobNo", jobNo));
		criteria.addOrder(Order.desc("createdDt"));
		
		List results = this.findMaxResultByCriteria(criteria, MAX_ROW);
		if(results.isEmpty()) return null;
		else
			return (TmtbAcquireTxn) results.get(0);
	}
	
	public String getMappedCustNo(String oldCustNo){
		DetachedCriteria criteria = DetachedCriteria.forClass(TempAcctMapping.class);
		criteria.add(Restrictions.eq("oldCustNo", oldCustNo));
		
		
		List<TempAcctMapping> results = this.findMaxResultByCriteria(criteria, MAX_ROW);
		if(results.isEmpty()) return null;
		else
			return results.get(0).getNewCustNo();
	}
	
	public List<VwIntfTripsForIb> getTRIPSview(int records){
		
		DetachedCriteria tripInterfaceCriteria = DetachedCriteria.forClass(VwIntfTripsForIb.class);
		//tripInterfaceCriteria.addOrder(Order.asc("tripIntfPk"));

		List<VwIntfTripsForIb> results = this.findMaxResultByCriteria(tripInterfaceCriteria, records);

		if(results.isEmpty()) return null;
		else return results;
	}
	
	public List<IttbTripsTxn> getTxnView(int records){
		
		DetachedCriteria tripInterfaceCriteria = DetachedCriteria.forClass(IttbTripsTxn.class);
		tripInterfaceCriteria.addOrder(Order.asc("tripIntfPk"));

		List<IttbTripsTxn> results = this.findMaxResultByCriteria(tripInterfaceCriteria, records);

		if(results.isEmpty()) return null;
		else return results;
	}
	
	public List<IttbTripsTxnReq> getTripsReqs(){
		
		DetachedCriteria tripsCriteria = DetachedCriteria.forClass(IttbTripsTxnReq.class);
		tripsCriteria.add(Restrictions.eq("status", NonConfigurableConstants.TRIPS_REQUEST_STATUS_PENDING));

		List<IttbTripsTxnReq> results = this.findAllByCriteria(tripsCriteria);

		if(results.isEmpty()) return null;
		else return results;
	}
	
	public void updateSucceeded(int[] succeededList) throws Exception
	{
		Session session = null;
		Connection conn = null;
		C3P0ProxyConnection castCon = null;
		
		
		try
		{
			//String url = "jdbc:oracle:thin:@//10.0.0.124:1521/wizvision";
			//Class.forName("oracle.jdbc.driver.OracleDriver");
			//Connection conn = DriverManager.getConnection(url, "IBS_USR", "ibsusr");
			this.getActiveDBTransaction();
			session = this.currentSession();
			//conn = session.connection();
			
			/////// For Tomcat testing
			//castCon = (C3P0ProxyConnection) ((ConnectionWrapper)conn).getWrappedConnection();
			//Method method = C3P0NativeJdbcExtractor.class.getMethod("getRawConnection", new Class[]{Connection.class});
			//conn = (Connection) castCon.rawConnectionOperation(method, null, new Object[] {C3P0ProxyConnection.RAW_CONNECTION});
			
			//ps = (OraclePreparedStatement) ((OracleConnection)conn).prepareStatement("begin RCPK_RETRIEVE_BY_IBS.SUCCEEDED(?);end;");
			session.doWork(connection ->{
				
			PreparedStatement ps = null; 
			try{
			ps = connection.prepareStatement("begin RCPK_RETRIEVE_BY_IBS.SUCCEEDED(?);end;");
				
			 
			//ps = session.connection().prepareStatement("begin RCPK_RETRIEVE_BY_IBS.SUCCEEDED(?);end;");
			//ps = (OraclePreparedStatement) ((OracleConnection)conn).prepareStatement("begin SUCCEEDED(?);end;");
			
			//OraclePreparedStatement ps = (OraclePreparedStatement) session.connection().prepareStatement("begin RCPK_RETRIEVE_BY_IBS.SUCCEEDED(?);end;");
			ArrayDescriptor ad = ArrayDescriptor.createDescriptor("RCTY_PK_ARRAY", ps.getConnection());
			ARRAY ar = new ARRAY(ad, ps.getConnection(), succeededList);
			ps.setArray(1, ar);
			ps.execute();
			//ps.getConnection().commit();
			}
			catch (Exception e)
			{
				logger.info(e);
				if (ps != null)
					ps.close();
				if (castCon != null)
					castCon.close();
			}
			finally
			{
				if (ps != null)
					ps.close();
				if (castCon != null)
					castCon.close();
				
			}
			});
			
		}
		catch (Exception e)
		{
			logger.info(e);
		}
		finally
		{
			if (castCon != null)
				castCon.close();
			if(session.isConnected()){
				session.close();
			}
		}
		
	}
	
	public void updateSucceededForSETL(BigDecimal[] succeededList) throws Exception
	{
		Session session = null;
		Connection conn = null;
		C3P0ProxyConnection castCon = null;
		PreparedStatement ps = null;
		
		try
		{
			//String url = "jdbc:oracle:thin:@//10.0.0.124:1521/wizvision";
			//Class.forName("oracle.jdbc.driver.OracleDriver");
			//Connection conn = DriverManager.getConnection(url, "IBS_USR", "ibsusr");
			this.getActiveDBTransaction();
			session = this.currentSession();
			//conn = session.connection();
			
			/////// For Tomcat testing
			//castCon = (C3P0ProxyConnection) ((ConnectionWrapper)conn).getWrappedConnection();
			//Method method = C3P0NativeJdbcExtractor.class.getMethod("getRawConnection", new Class[]{Connection.class});
			//conn = (Connection) castCon.rawConnectionOperation(method, null, new Object[] {C3P0ProxyConnection.RAW_CONNECTION});
			
			//ps = (OraclePreparedStatement) ((OracleConnection)conn).prepareStatement("begin RCPK_RETRIEVE_BY_IBS.SUCCEEDED(?);end;");
			session.doWork(connection ->connection.prepareStatement("begin RCPK_RETRIEVE_BY_IBS.SETL_SUCCEEDED(?);end;"));
			//ps = session.connection().prepareStatement("begin RCPK_RETRIEVE_BY_IBS.SETL_SUCCEEDED(?);end;");
			//ps = (OraclePreparedStatement) ((OracleConnection)conn).prepareStatement("begin SUCCEEDED(?);end;");
			
			//OraclePreparedStatement ps = (OraclePreparedStatement) session.connection().prepareStatement("begin RCPK_RETRIEVE_BY_IBS.SUCCEEDED(?);end;");
			ArrayDescriptor ad = ArrayDescriptor.createDescriptor("RCTY_PK_ARRAY", ps.getConnection());
			ARRAY ar = new ARRAY(ad, ps.getConnection(), succeededList);
			ps.setArray(1, ar);
			ps.execute();
			//ps.getConnection().commit();
			
		}
		catch (Exception e)
		{
			LoggerUtil.printStackTrace(logger, e);
			if (ps != null)
				ps.close();
			if (castCon != null)
				castCon.close();
			if(session.isConnected()){
				session.close();
			}
			throw new Exception("Oracle exception");
		}
		finally
		{
			if (ps != null)
				ps.close();
			if (castCon != null)
				castCon.close();
			if(session.isConnected()){
				session.close();
			}
		}
	}
	
	public void updateFailed(int[] failedList, String[] errorCodes) throws Exception
	{
		Session session = null;
		Connection conn = null;
		C3P0ProxyConnection castCon = null;
		PreparedStatement ps = null;

		try
		{
			//String url = "jdbc:oracle:thin:@//10.0.0.124:1521/wizvision";
			//Class.forName("oracle.jdbc.driver.OracleDriver");
			//Connection conn = DriverManager.getConnection(url, "IBS_USR", "ibsusr");
			this.getActiveDBTransaction();
			session = this.currentSession();
			
			/////// For Tomcat testing
			//conn = session.connection();
			//castCon = (C3P0ProxyConnection) ((ConnectionWrapper)conn).getWrappedConnection();
			
			//Method method = C3P0NativeJdbcExtractor.class.getMethod("getRawConnection", new Class[]{Connection.class});
			//conn = (Connection) castCon.rawConnectionOperation(method, null, new Object[] {C3P0ProxyConnection.RAW_CONNECTION});
			//ps = (OraclePreparedStatement) ((OracleConnection)conn).prepareStatement("begin RCPK_RETRIEVE_BY_IBS.FAILED(?,?);end;");
			/////// For Tomcat testing
			
			session.doWork(connection ->connection.prepareStatement("begin RCPK_RETRIEVE_BY_IBS.FAILED(?,?);end;"));
			//ps = session.connection().prepareStatement("begin RCPK_RETRIEVE_BY_IBS.FAILED(?,?);end;");
			
			//OraclePreparedStatement ps = (OraclePreparedStatement) session.connection().prepareStatement("begin RCPK_RETRIEVE_BY_IBS.FAILED(?,?);end;");
			ArrayDescriptor ad = ArrayDescriptor.createDescriptor("RCTY_PK_ARRAY", ps.getConnection());
			ArrayDescriptor adChar = ArrayDescriptor.createDescriptor("RCTY_ERROR_CODE_ARRAY", ps.getConnection());
			
			ARRAY ar = new ARRAY(ad, ps.getConnection(), failedList);
			ARRAY arChar = new ARRAY(adChar, ps.getConnection(), errorCodes);
			ps.setArray(1, ar);
			ps.setArray(2, arChar);
			ps.execute();
			//ps.getConnection().commit();
			
		}
		catch (Exception e)
		{
			e.printStackTrace();
			if (ps != null)
				ps.close();
			if (castCon != null)
				castCon.close();
			if(session.isConnected()){
				session.close();
			}
			throw new Exception("Oracle exception");
		}
		finally
		{
			if (ps != null)
				ps.close();
			if (castCon != null)
				castCon.close();
			if(session.isConnected()){
				session.close();
			}
		}
	}
	
	public void started(int retrievalPK) throws Exception
	{
		Session session = null;
		Connection conn = null;
		C3P0ProxyConnection castCon = null;
		try
		{
			
			//String url = "jdbc:oracle:thin:@//10.0.0.124:1521/wizvision";
			//Class.forName("oracle.jdbc.driver.OracleDriver");
			//Connection conn = DriverManager.getConnection(url, "IBS_USR", "ibsusr");
			this.getActiveDBTransaction();
			session = this.currentSession();
			session.doWork(connection -> 
				{
					PreparedStatement ps = null;
					try
					{
					    ps= connection.prepareStatement("begin RCPK_RETRIEVE_BY_IBS.STARTED(?);end;");
					 
					
					
					//conn = session.connection();
		
					/////// For Tomcat testing
					//castCon = (C3P0ProxyConnection) ((ConnectionWrapper)conn).getWrappedConnection();
					
					//Method method = C3P0NativeJdbcExtractor.class.getMethod("getRawConnection", new Class[]{Connection.class});
					//conn = (Connection) castCon.rawConnectionOperation(method, null, new Object[] {C3P0ProxyConnection.RAW_CONNECTION});
					
					//ps = (OraclePreparedStatement) ((OracleConnection)conn).prepareStatement("begin STARTED(?);end;");
					//ps = (OraclePreparedStatement) ((OracleConnection)conn).prepareStatement("begin RCPK_RETRIEVE_BY_IBS.STARTED(?);end;");
					/////// For Tomcat testing
					
					ps.setInt(1, retrievalPK);
					ps.execute();
					ps.getConnection().commit();
					
				}
				catch (Exception e)
				{
					logger.info(e);
				}
				finally
				{
					if (ps != null)
						ps.close();
					if (castCon != null)
						castCon.close();
				}
			});
	    }
		catch (Exception e)
		{
			logger.info(e);
		}
		finally
		{
			if(session.isConnected()){
				session.close();
			}
		}
	}
	public void ended(int retrievalPK) throws Exception
	{
		Session session = null;
		Connection conn = null;
		C3P0ProxyConnection castCon = null;
		
		try
		{
			//String url = "jdbc:oracle:thin:@//10.0.0.124:1521/wizvision";
			//Class.forName("oracle.jdbc.driver.OracleDriver");
			//Connection conn = DriverManager.getConnection(url, "IBS_USR", "ibsusr");
			this.getActiveDBTransaction();
			session = this.currentSession();
			
			/////// For Tomcat testing

			//conn = session.connection();
			//castCon = (C3P0ProxyConnection) ((ConnectionWrapper)conn).getWrappedConnection();
			
			
			//Method method = C3P0NativeJdbcExtractor.class.getMethod("getRawConnection", new Class[]{Connection.class});
			//conn = (Connection) castCon.rawConnectionOperation(method, null, new Object[] {C3P0ProxyConnection.RAW_CONNECTION});
			
			//ps = (OraclePreparedStatement) ((OracleConnection)conn).prepareStatement("begin RCPK_RETRIEVE_BY_IBS.ENDED(?);end;");
			
			session.doWork(connection ->{
				PreparedStatement ps = null;
				try
				{
					ps = connection.prepareStatement("begin RCPK_RETRIEVE_BY_IBS.ENDED(?);end;");
					//ps = (OraclePreparedStatement) ((OracleConnection)conn).prepareStatement("begin ENDED(?);end;");
					
					ps.setInt(1, retrievalPK);
					ps.execute();
					ps.getConnection().commit();
					
				}
				catch (Exception e)
				{
					e.printStackTrace();
					if (ps != null)
						ps.close();
				}
				finally
				{
					if (ps != null)
						ps.close();
				}
			}); 
		}
		catch (Exception e)
		{
			  logger.info(e);
		}
		finally
		{
			if(session.isConnected()){
				session.close();
			}
		}
	}
	
	@SuppressWarnings("unchecked")
	public IttbCpGuestProduct getIttbCpGuestProduct(Long productNo){
		
		DetachedCriteria ittbCpGuestProductCriteria = DetachedCriteria.forClass(IttbCpGuestProduct.class);
		ittbCpGuestProductCriteria.add(Restrictions.eq("guestProductNo", productNo));

		//ittbCpGuestProductCriteria.addOrder(Order.asc("pmtbProductNo"));

		List<IttbCpGuestProduct> results = this.findDefaultMaxResultByCriteria(ittbCpGuestProductCriteria);

		if(results.isEmpty()) return null;
		else return results.get(0);
	}
	
	@SuppressWarnings("unchecked")
	public List<VwIntfSetlForIb> getSETLSview(int records){
		DetachedCriteria criteria = DetachedCriteria.forClass(VwIntfSetlForIb.class);
		List<VwIntfSetlForIb> results = this.findMaxResultByCriteria(criteria, records);

		if(results.isEmpty()) return null;
		else return results;
	}
	
	public List<TmtbTxnReviewReqFlow> getTxnReqFlows(String txnReqID){
		
		logger.info("Retriving req flow data by Txn Req ID");
		DetachedCriteria txnReqFlowCriteria = DetachedCriteria.forClass(TmtbTxnReviewReqFlow.class);
		DetachedCriteria txnReqCriteria = txnReqFlowCriteria.createCriteria("tmtbTxnReviewReq", Criteria.LEFT_JOIN);
		txnReqFlowCriteria.createCriteria("satbUser", Criteria.LEFT_JOIN);
		txnReqCriteria.add(Restrictions.eq("txnReviewReqNo", Integer.parseInt(txnReqID)));
		txnReqFlowCriteria.addOrder(Order.asc("txnReviewReqFlowNo"));
		
		List results = this.findAllByCriteria(txnReqFlowCriteria);
		if(results.isEmpty()) return null;
		else
			return results;
	}
	public List<Object[]> getPreviousApproval(String jobNo){
		
		Session session =null;
		List<Object[]> results =null;
		try
		{
			this.getActiveDBTransaction();
			session = this.currentSession();
		Query query = session.createSQLQuery("select c.remarks, c.to_status, c.FLOW_DT, d.LOGIN_ID "
				+ " from tmtb_acquire_txn a , tmtb_txn_review_req b, tmtb_txn_review_req_flow c, satb_user d"
				+ " where a.job_no = '"+jobNo+"'"
			    + " and  a.acquire_txn_no = b.acquire_txn_no "
			    + " and c.txn_review_req_no = b.txn_review_req_no "
			    + " and c.User_id = d.user_id "
			    + " and (c.FROM_STATUS = 'V' or C.FROM_STATUS = 'F') "
			    + " ORDER BY c.flow_dt desc");
		
		results = new ArrayList();
		results = query.list();
		}
		catch(Exception e){logger.info(e);}
		finally{if(session.isConnected()){session.close();}}
		return results;
	}
	public boolean checkJobNo(String jobNo)
	{
		DetachedCriteria txnCriteria = DetachedCriteria.forClass(TmtbAcquireTxn.class);
		txnCriteria.add(Restrictions.eq("jobNo", jobNo));
		
		List results = this.findDefaultMaxResultByCriteria(txnCriteria);
		if(results.isEmpty()) return false;
		else return true;
	}
}