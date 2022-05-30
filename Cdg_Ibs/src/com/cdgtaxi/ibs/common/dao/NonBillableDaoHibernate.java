package com.cdgtaxi.ibs.common.dao;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.Transaction;
import org.hibernate.classic.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.Subqueries;

import com.cdgtaxi.ibs.common.ConfigurableConstants;
import com.cdgtaxi.ibs.common.NonConfigurableConstants;
import com.cdgtaxi.ibs.common.model.BmtbBankPayment;
import com.cdgtaxi.ibs.common.model.FmtbBankCode;
import com.cdgtaxi.ibs.common.model.FmtbGlLogDetail;
import com.cdgtaxi.ibs.common.model.IttbSetlReportingAmex;
import com.cdgtaxi.ibs.common.model.IttbSetlReportingAyden;
import com.cdgtaxi.ibs.common.model.TmtbNonBillableBatch;
import com.cdgtaxi.ibs.common.model.TmtbNonBillableTxn;
import com.cdgtaxi.ibs.common.model.TmtbNonBillableTxnCrca;
import com.cdgtaxi.ibs.common.model.TmtbNonBillableTxnCrcaReq;
import com.cdgtaxi.ibs.common.model.VwBankAdvice;
import com.cdgtaxi.ibs.common.model.VwBankAdviceIBS;
import com.cdgtaxi.ibs.common.model.VwBankAdviseAmex;
import com.cdgtaxi.ibs.common.model.VwBankAdviseAyden;
import com.cdgtaxi.ibs.common.model.forms.SearchBankPaymentAdviseForm;
import com.cdgtaxi.ibs.common.model.forms.SearchChargebackForm;
import com.cdgtaxi.ibs.common.model.forms.SearchNonBillableBatchForm;
import com.cdgtaxi.ibs.common.model.forms.SearchNonBillableTxnForm;
import com.cdgtaxi.ibs.common.model.forms.SearchNonBillableTxnsForm;
import com.cdgtaxi.ibs.master.model.MstbAcquirer;
import com.cdgtaxi.ibs.master.model.MstbAcquirerMdr;
import com.cdgtaxi.ibs.master.model.MstbMasterTable;
import com.cdgtaxi.ibs.report.dto.NonBillableBatchDto;
import com.cdgtaxi.ibs.util.DateUtil;

@SuppressWarnings("unchecked")
public class NonBillableDaoHibernate extends GenericDaoHibernate implements NonBillableDao {

    public List<MstbAcquirer> getAllAcquirers() {
        DetachedCriteria criteria = DetachedCriteria.forClass(MstbAcquirer.class);
        criteria.addOrder(Order.asc("name"));
        return findAllByCriteria(criteria);
    }

    public List<Object[]> searchNonBillableBatch(SearchNonBillableBatchForm form) {
        Session session = this.getHibernateTemplate().getSessionFactory().openSession();
        List results = new ArrayList();
        try {
            Query query = session.getNamedQuery("searchNonBillableBatch");

            if (form.batchNo != null && form.batchNo.length() > 0)
                query.setParameter("batchNo", "%" + form.batchNo + "%", Hibernate.STRING);
            else query.setParameter("batchNo", null, Hibernate.STRING);
            if (form.mid != null && form.mid.length() > 0) query.setParameter("mid", form.mid, Hibernate.STRING);
            else query.setParameter("mid", null, Hibernate.STRING);
            if (form.tid != null && form.tid.length() > 0) query.setParameter("tid", form.tid, Hibernate.STRING);
            else query.setParameter("tid", null, Hibernate.STRING);
            if (form.uploadDateFrom != null)
                query.setParameter("settlementDateFrom", DateUtil.convertDateTo0000Hours(form.uploadDateFrom), Hibernate.DATE);
            else query.setParameter("settlementDateFrom", null, Hibernate.DATE);
            if (form.uploadDateTo != null)
                query.setParameter("settlementDateTo", DateUtil.convertDateTo2359Hours(form.uploadDateTo), Hibernate.DATE);
            else query.setParameter("settlementDateTo", null, Hibernate.DATE);
            if (form.creditDateFrom != null)
                query.setParameter("creditDateFrom", DateUtil.convertUtilDateToSqlDate(form.creditDateFrom), Hibernate.DATE);
            else query.setParameter("creditDateFrom", null, Hibernate.DATE);
            if (form.creditDateTo != null)
                query.setParameter("creditDateTo", DateUtil.convertUtilDateToSqlDate(form.creditDateTo), Hibernate.DATE);
            else query.setParameter("creditDateTo", null, Hibernate.DATE);
            if (form.acquirer != null)
                query.setParameter("acquirerNo", form.acquirer.getAcquirerNo(), Hibernate.INTEGER);
            else query.setParameter("acquirerNo", null, Hibernate.INTEGER);
            if (form.paymentType != null) query.setParameter("masterCode", form.paymentType, Hibernate.STRING);
            else query.setParameter("masterCode", null, Hibernate.STRING);
            if (form.batchStatus != null && form.batchStatus.length() > 0)
                query.setParameter("batchStatus", form.batchStatus, Hibernate.STRING);
            else query.setParameter("batchStatus", null, Hibernate.STRING);
            if (form.completeStatus != null)
                query.setParameter("completeStatus", form.completeStatus, Hibernate.STRING);
            else query.setParameter("completeStatus", null, Hibernate.STRING);
            if (form.jobNo != null)
                query.setParameter("jobNo", form.jobNo, Hibernate.STRING);
            else query.setParameter("jobNo", null, Hibernate.STRING);
            if (form.interfaceMappingValue != null) query.setParameter("interfaceMappingValue", form.interfaceMappingValue, Hibernate.STRING);
            else query.setParameter("interfaceMappingValue", null, Hibernate.STRING);

            query.setMaxResults(ConfigurableConstants.getMaxQueryResult());


            results = query.list();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }

        return results;
    }

    public List<Object[]> searchNonBillableBatch2(SearchNonBillableBatchForm form) {
        Session session = this.getHibernateTemplate().getSessionFactory().openSession();
        List results = new ArrayList();
        try {
            Query query = session.getNamedQuery("searchNonBillableBatch2");
            logger.info("query called: " + query.getQueryString());

            if (form.matchingStatus != null && form.matchingStatus.size() > 0)
                query.setParameterList("matchingStatus", form.matchingStatus, Hibernate.STRING);
            else {
                List list = new ArrayList();
                list.add(null);
                list.add(null);
                query.setParameterList("matchingStatus", list, Hibernate.STRING);
            }
            if (form.interfaceMappingValue != null) query.setParameter("interfaceMappingValue", form.interfaceMappingValue, Hibernate.STRING);
            else query.setParameter("interfaceMappingValue", null, Hibernate.STRING);

            
            results = query.list();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }

        logger.info("end of query called: ");

        return results;
    }


    //Todo: Need to change the query for report to not use. For now it is still using this. this guy just check the first table
    public List<TmtbNonBillableTxn> searchNonBillableTxn(SearchNonBillableTxnForm form) {
        DetachedCriteria criteria = DetachedCriteria.forClass(TmtbNonBillableTxn.class);
        DetachedCriteria paymentTypeCriteria = criteria.createCriteria("mstbAcquirerPymtType", "pymtType", DetachedCriteria.LEFT_JOIN);
        DetachedCriteria masterCriteria = paymentTypeCriteria.createCriteria("mstbMasterTable", DetachedCriteria.LEFT_JOIN);
        criteria.createCriteria("mstbMasterTableByServiceProvider", DetachedCriteria.LEFT_JOIN);
        criteria.createCriteria("mstbMasterTableByChargebackRefundReason", DetachedCriteria.LEFT_JOIN);
        criteria.createCriteria("mstbMasterTableByChargebackType", DetachedCriteria.LEFT_JOIN);

        criteria.add(Restrictions.eq("tmtbNonBillableBatch", form.txnBatch));

        if (form.paymentType != null)
            masterCriteria.add(Restrictions.eq("masterCode", form.paymentType));
        if (form.txnStatus != null && form.txnStatus.length() > 0) {
            if (form.rejectedTrips != null
                    && form.rejectedTrips.size() > 0
                    && form.txnStatus.equals(NonConfigurableConstants.NON_BILLABLE_TXN_OPEN)) {
                criteria.add(Restrictions.eq("status", form.txnStatus));
                criteria.add(Restrictions.not(Restrictions.in("txnId", form.rejectedTrips)));
            } else if (form.rejectedTrips != null
                    && form.rejectedTrips.size() > 0
                    && form.txnStatus.equals(NonConfigurableConstants.NON_BILLABLE_TXN_REJECTED))
                criteria.add(Restrictions.in("txnId", form.rejectedTrips));
            else {
                criteria.add(Restrictions.eq("status", form.txnStatus));
            }
        }
        if (form.taxiNo != null && form.taxiNo.length() > 0)
            criteria.add(Restrictions.eq("taxiNo", form.taxiNo));
        if (form.driverID != null && form.driverID.length() > 0)
            criteria.add(Restrictions.eq("nric", form.driverID));
        if (form.tripDateFrom != null)
            criteria.add(Restrictions.ge("tripStartDt", DateUtil.convertDateTo0000Hours(form.tripDateFrom)));
        if (form.tripDateTo != null)
            criteria.add(Restrictions.le("tripStartDt", DateUtil.convertDateTo2359Hours(form.tripDateTo)));
        if (form.totalAmount != null)
            criteria.add(Restrictions.eq("total", form.totalAmount));
        if (form.offline != null && form.offline.length() > 0) {
            criteria.add(Restrictions.eq("offlineFlag", form.offline));
        }
        if (form.pspRefNo != null && form.pspRefNo.length() > 0) {
            criteria.add(Restrictions.or(Restrictions.eq("pspRefNo1", form.pspRefNo), Restrictions.eq("pspRefNo2", form.pspRefNo)));
        }
        if (form.matchingStatus != null && form.matchingStatus.length() > 0) {
            criteria.add(Restrictions.eq("matchingStatus", form.matchingStatus));
        }
        
        Disjunction disjunction = Restrictions.disjunction();
        disjunction.add(Restrictions.isNull("matchingStatus"));
        disjunction.add(Restrictions.ne("matchingStatus",NonConfigurableConstants.AYDEN_MATCHING_STATUS_TRANSFERED));

        if (form.policyNo != null && form.policyNo.length() > 0)
            criteria.add(Restrictions.eq("policyNumber", form.policyNo));
        if (form.policyStatus != null && form.policyStatus.length() > 0) {
            criteria.add(Restrictions.eq("policyStatus", form.policyStatus));
        }
        
        criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);

        criteria.addOrder(Order.asc("tripStartDt"));

        return findDefaultMaxResultByCriteria(criteria);
    }

    //toDo: untested appendQuery
    public List<TmtbNonBillableTxn> searchNonBillableTxns(SearchNonBillableTxnsForm form) {

        logger.info("IN searchNonBillableTxns::::::::");

        Session session = this.getHibernateTemplate().getSessionFactory().openSession();
        List<Object[]> results;
        List<TmtbNonBillableTxn> finalResults = new ArrayList();
        Query query = null;

        List<Date> settlementDateList = new ArrayList<Date>();
        List<Long> batchIdList = new ArrayList<Long>();

        for (TmtbNonBillableBatch batch : form.txnBatches) {
            settlementDateList.add(batch.getSettlementDate());
            batchIdList.add(batch.getBatchId());
            batchIdList.add(null);
        }

        try {
            if(form.sourceList.contains(NonConfigurableConstants.FROM_AYDEN) || form.sourceList.contains(NonConfigurableConstants.FROM_AMEX)) {
                query = session.getNamedQuery("searchNonBillableTxns");
            }else{ // by default should not use the record type because it is null (Eg. Lazada)
                query = session.getNamedQuery("searchNonBillableTxns2");
            }

            logger.info("query used: " + query.getQueryString());

            query.setParameterList("source",form.sourceList,Hibernate.STRING);
            query.setParameterList("matchingStatus", form.matchingStatuses, Hibernate.STRING);
            query.setParameterList("batchId", batchIdList, Hibernate.LONG);
            if(form.sourceList.contains(NonConfigurableConstants.FROM_AYDEN) || form.sourceList.contains(NonConfigurableConstants.FROM_AMEX)) {
                query.setParameterList("recordType", form.recordType, Hibernate.STRING);
            }
            if (StringUtils.isNotBlank(form.pspRefNo)) {
                query.setParameter("pspRefNo", form.pspRefNo, Hibernate.STRING);
            } else {
                query.setParameter("pspRefNo", "%", Hibernate.STRING);
            }
            if (StringUtils.isNotBlank(form.aydenMerchantId)) {
                query.setParameter("merchantId", form.aydenMerchantId, Hibernate.STRING);
            }else{
                query.setParameter("merchantId",null,Hibernate.STRING);
            }
            if (StringUtils.isNotBlank(form.jobNo)) {
                query.setParameter("jobNo", form.jobNo, Hibernate.STRING);
            } else {
                query.setParameter("jobNo", "%", Hibernate.STRING);
            }

            results = query.list();
            logger.info("results1.size(): " + results.size());

            Object[] prev = null;
            Set<TmtbNonBillableTxnCrca> crca1Set = new HashSet<TmtbNonBillableTxnCrca>();
            Set<TmtbNonBillableTxnCrca> crca2Set = new HashSet<TmtbNonBillableTxnCrca>();
            TmtbNonBillableTxn txn = new TmtbNonBillableTxn();

            if (results != null && !results.isEmpty()) {
                for (Object[] temp : results) {
                    if (prev == null) {
                        txn = (TmtbNonBillableTxn) temp[0];
                        if (temp.length > 2 && temp[1] != null) {
                            crca1Set = new HashSet<TmtbNonBillableTxnCrca>();
                            crca1Set.add((TmtbNonBillableTxnCrca) temp[1]);
                        } else {
                            crca1Set = null;
                        }
                        if (temp.length > 3 && temp[2] != null) {
                            crca2Set = new HashSet<TmtbNonBillableTxnCrca>();
                            crca2Set.add((TmtbNonBillableTxnCrca) temp[2]);
                        } else {
                            crca2Set = null;
                        }

                        txn.setTmtbNonBillableTxnCrca1(crca1Set);
                        txn.setTmtbNonBillableTxnCrca2(crca2Set);

                        prev = temp;
                        continue;
                    }
                    if (((TmtbNonBillableTxn) temp[0]).getTxnId().equals(((TmtbNonBillableTxn) prev[0]).getTxnId())) {

                        if (temp.length > 2 && temp[1] != null) {
                            crca1Set.add((TmtbNonBillableTxnCrca) temp[1]);
                        }
                        if (temp.length > 3 && temp[2] != null) {
                            crca2Set.add((TmtbNonBillableTxnCrca) temp[2]);
                        }

                    } else {
                        finalResults.add(txn);
                        txn = (TmtbNonBillableTxn) temp[0];

                        if (temp.length > 2 && temp[1] != null) {
                            crca1Set = new HashSet<TmtbNonBillableTxnCrca>();
                            crca1Set.add((TmtbNonBillableTxnCrca) temp[1]);
                        } else {
                            crca1Set = null;
                        }
                        if (temp.length > 3 && temp[2] != null) {
                            crca2Set = new HashSet<TmtbNonBillableTxnCrca>();
                            crca2Set.add((TmtbNonBillableTxnCrca) temp[2]);
                        } else {
                            crca2Set = null;
                        }

                    }

                    txn.setTmtbNonBillableTxnCrca1(crca1Set);
                    txn.setTmtbNonBillableTxnCrca2(crca2Set);
                    prev = temp;
                }
            }

            if (txn.getTxnId() != null) {
                finalResults.add(txn);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }

        logger.info("OUT searchNonBillableTxns::::::::");


        return finalResults;

    }

    public List<Object[]> processAydenCompletenessCheck(List<NonBillableBatchDto> batches) {
        Session session = this.getHibernateTemplate().getSessionFactory().openSession();
        Query query = session.getNamedQuery("processAydenCompletenessCheck");
        Object[] temp = new Object[4];
        List<Object[]> result = new ArrayList<Object[]>();

        for (NonBillableBatchDto batch : batches) {
            query.setParameter("batchId", batch.getBatchId(), Hibernate.LONG);
            List<Object[]> temp2 = query.list();
            temp[0] = batch.getBatchId();
            temp[1] = (Long) temp2.get(0)[0];
            temp[2] = (Long) temp2.get(0)[1];
            temp[3] = DateUtil.convertDateToStr(batch.getSettlementDate(), DateUtil.GLOBAL_DATE_FORMAT);

            result.add(temp);
            temp = new Object[4];
        }

        return result;

    }


    public List<TmtbNonBillableTxn> searchNonBillableTxnsWithoutCRCA(SearchNonBillableTxnsForm form) {

        Session session = this.getHibernateTemplate().getSessionFactory().openSession();
        List<TmtbNonBillableTxn> result = new ArrayList();
        try {
            DetachedCriteria criteria = DetachedCriteria.forClass(TmtbNonBillableTxn.class);

            criteria.add(Restrictions.eq("settlementDate", form.txnBatches.get(0).getSettlementDate()));
            criteria.add(Restrictions.eq("matchingStatus", form.matchingStatus));

            result = this.findAllByCriteria(criteria);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
        if (result.isEmpty()) return null;
        else return result;

    }

    public TmtbNonBillableBatch getNonbillableBatch(Long batchId) {
        DetachedCriteria criteria = DetachedCriteria.forClass(TmtbNonBillableBatch.class);
        criteria.createCriteria("mstbAcquirer", "acq", DetachedCriteria.LEFT_JOIN);
        DetachedCriteria bankPaymentDetailsCriteria = criteria.createCriteria("bmtbBankPaymentDetails", DetachedCriteria.LEFT_JOIN);
        bankPaymentDetailsCriteria.createCriteria("bmtbBankPayment", DetachedCriteria.LEFT_JOIN);

        criteria.add(Restrictions.idEq(batchId));
        List<TmtbNonBillableBatch> result = this.findAllByCriteria(criteria);
        if (result.isEmpty()) return null;
        else return (TmtbNonBillableBatch) result.get(0);
    }

    public TmtbNonBillableBatch getNonbillableBatch(String batchNo, Date settlementDate) {
        DetachedCriteria criteria = DetachedCriteria.forClass(TmtbNonBillableBatch.class);
        criteria.createCriteria("mstbAcquirer", "acq", DetachedCriteria.LEFT_JOIN);
        DetachedCriteria bankPaymentDetailsCriteria = criteria.createCriteria("bmtbBankPaymentDetails", DetachedCriteria.LEFT_JOIN);
        bankPaymentDetailsCriteria.createCriteria("bmtbBankPayment", DetachedCriteria.LEFT_JOIN);

        criteria.add(Restrictions.eq("batchNo", batchNo));
        criteria.add(Restrictions.eq("settlementDate", settlementDate));
        List<TmtbNonBillableBatch> result = this.findAllByCriteria(criteria);
        if (result.isEmpty()) return null;
        else return (TmtbNonBillableBatch) result.get(0);
    }

    public List<TmtbNonBillableBatch> getNonbillableBatch(List<Long> batchIds) {
        DetachedCriteria criteria = DetachedCriteria.forClass(TmtbNonBillableBatch.class);
        criteria.createCriteria("mstbAcquirer", "acq", DetachedCriteria.LEFT_JOIN);
        DetachedCriteria bankPaymentDetailsCriteria = criteria.createCriteria("bmtbBankPaymentDetails", DetachedCriteria.LEFT_JOIN);
        bankPaymentDetailsCriteria.createCriteria("bmtbBankPayment", DetachedCriteria.LEFT_JOIN);

        criteria.add(Restrictions.in("batchId", batchIds));
        return this.findAllByCriteria(criteria);
    }

    public MstbAcquirerMdr getMDR(MstbAcquirer acquirer) {
        DetachedCriteria criteria = DetachedCriteria.forClass(MstbAcquirerMdr.class);
        criteria.add(Restrictions.eq("mstbAcquirer", acquirer));
        criteria.add(Restrictions.le("effectiveDate", DateUtil.getCurrentDate()));
        criteria.addOrder(Order.desc("effectiveDate"));
        List results = findMaxResultByCriteria(criteria, 1);
        if (results.isEmpty()) return null;
        else return (MstbAcquirerMdr) results.get(0);
    }

    public List<FmtbBankCode> getBankInBanksForNonBillable(List<Long> batchIds) {
        Session session = this.getHibernateTemplate().getSessionFactory().openSession();
        List results = new ArrayList();
        try {
            Query query = session.getNamedQuery("getBankInBanksForNonBillable");
            query.setParameterList("batchIds", batchIds, Hibernate.LONG);
            results = query.list();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }

        return results;
    }

    public List<TmtbNonBillableTxn> getNonBillableTxn(Collection<Long> txnIds) {
        DetachedCriteria criteria = DetachedCriteria.forClass(TmtbNonBillableTxn.class);
        criteria.add(Restrictions.in("txnId", txnIds));
        return this.findAllByCriteria(criteria);
    }

    public void closeAllNonBillableTxn(Long batchId, String userLoginId) {
        DetachedCriteria criteria = DetachedCriteria.forClass(TmtbNonBillableTxn.class);
        criteria.add(Restrictions.eq("tmtbNonBillableBatch.batchId", batchId));
        criteria.add(Restrictions.eq("status", NonConfigurableConstants.NON_BILLABLE_TXN_OPEN));

        List<TmtbNonBillableTxn> results = this.findDefaultMaxResultByCriteria(criteria);
        while (results.isEmpty() == false) {
            for (TmtbNonBillableTxn txn : results) {
                txn.setStatus(NonConfigurableConstants.NON_BILLABLE_TXN_CLOSED);
                this.update(txn, userLoginId);
            }

            /*
             * Seng Tat 17/06/2010
             * Commented to test whether it can avoid ConcurrentModificationException.
             * Might cause performance issue
             */
            //this.getHibernateTemplate().flush();

            results = this.findDefaultMaxResultByCriteria(criteria);
        }

        //Below method does not work with weblogic ear file deployment
//		int recordsUpdated = this.getHibernateTemplate().bulkUpdate(
//				//HQL
//				"Update TmtbNonBillableTxn " +
//				"set status=?, updatedBy=?, updatedDt=? " +
//				"where tmtbNonBillableBatch.batchId=?",
//				//Parameters
//				new Object[]{NonConfigurableConstants.NON_BILLABLE_TXN_CLOSED, userLoginId,
//				DateUtil.getCurrentTimestamp(), batchId});
//
//		this.logger.info("updated "+recordsUpdated+" records...");
    }

    public List<BmtbBankPayment> searchBankPaymentAdvise(SearchBankPaymentAdviseForm form) {
        DetachedCriteria criteria = DetachedCriteria.forClass(BmtbBankPayment.class);
        DetachedCriteria paymentDetailCriteria = criteria.createCriteria("bmtbBankPaymentDetails", DetachedCriteria.LEFT_JOIN);
        DetachedCriteria batchCriteria = paymentDetailCriteria.createCriteria("tmtbNonBillableBatch", DetachedCriteria.LEFT_JOIN);
        DetachedCriteria acquirerCriteria = batchCriteria.createCriteria("mstbAcquirer", DetachedCriteria.LEFT_JOIN);
        criteria.createCriteria("fmtbBankCode", DetachedCriteria.LEFT_JOIN);

        if (form.txnRefNo != null && form.txnRefNo.length() > 0)
            criteria.add(Restrictions.eq("txnRefNo", form.txnRefNo));
        if (form.collectionAmount != null)
            criteria.add(Restrictions.eq("collectionAmount", form.collectionAmount));
        if (form.creditDateFrom != null)
            criteria.add(Restrictions.ge("creditDate", DateUtil.convertUtilDateToSqlDate(form.creditDateFrom)));
        if (form.creditDateTo != null)
            criteria.add(Restrictions.le("creditDate", DateUtil.convertUtilDateToSqlDate(form.creditDateTo)));
        if (form.acquirer != null)
            acquirerCriteria.add(Restrictions.idEq(form.acquirer.getAcquirerNo()));
        if (form.paymentNo != null)
            criteria.add(Restrictions.eq("paymentNo", form.paymentNo));

        criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);

        //Due to left join, the result returned more than max result.
        //End search result may only retrieve total entities less than max result.
        DetachedCriteria criteria2 = this.createMaxResultSubquery(criteria, "payment_No");
        criteria.add(Subqueries.propertyIn("paymentNo", criteria2));

        criteria.addOrder(Order.asc("creditDate"));
        criteria.addOrder(Order.asc("paymentNo"));

        return findDefaultMaxResultByCriteria(criteria);
    }

    public Object[] getRejectedTripsCountAndAmount(Long batchId) {
        DetachedCriteria criteria = DetachedCriteria.forClass(TmtbNonBillableTxn.class);
        criteria.add(Restrictions.eq("tmtbNonBillableBatch.batchId", batchId));
        criteria.add(Restrictions.eq("status", NonConfigurableConstants.NON_BILLABLE_TXN_REJECTED));

        ProjectionList projectionList = Projections.projectionList();
        projectionList.add(Projections.count("txnId"));
        projectionList.add(Projections.sum("total"));
        criteria.setProjection(projectionList);

        List results = this.findAllByCriteria(criteria);
        if (results.isEmpty()) return new Object[]{0, BigDecimal.ZERO};
        else {
            return (Object[]) results.get(0);
        }
    }

    public List<TmtbNonBillableTxn> searchChargeback(SearchChargebackForm form) {
        DetachedCriteria criteria = DetachedCriteria.forClass(TmtbNonBillableTxn.class);
        DetachedCriteria paymentTypeCriteria = criteria.createCriteria("mstbAcquirerPymtType", "pymtType", DetachedCriteria.LEFT_JOIN);
        DetachedCriteria masterCriteria = paymentTypeCriteria.createCriteria("mstbMasterTable", DetachedCriteria.LEFT_JOIN);

        criteria.createCriteria("mstbMasterTableByServiceProvider", DetachedCriteria.LEFT_JOIN);
        criteria.createCriteria("mstbMasterTableByChargebackRefundReason", DetachedCriteria.LEFT_JOIN);
        criteria.createCriteria("mstbMasterTableByChargebackType", DetachedCriteria.LEFT_JOIN);
        DetachedCriteria batchCriteria = criteria.createCriteria("tmtbNonBillableBatch", DetachedCriteria.LEFT_JOIN);
        DetachedCriteria acquirerCriteria = batchCriteria.createCriteria("mstbAcquirer", DetachedCriteria.LEFT_JOIN);
        DetachedCriteria bankPaymentDetailCriteria = batchCriteria.createCriteria("bmtbBankPaymentDetails", DetachedCriteria.LEFT_JOIN);
        bankPaymentDetailCriteria.createCriteria("bmtbBankPayment", DetachedCriteria.LEFT_JOIN);


        Disjunction disjunction = Restrictions.disjunction();
        disjunction.add(Restrictions.isNull("matchingStatus"));
        disjunction.add(Restrictions.ne("matchingStatus",NonConfigurableConstants.AYDEN_MATCHING_STATUS_TRANSFERED));

        criteria.add(Restrictions.in("status", new String[]{
                NonConfigurableConstants.NON_BILLABLE_TXN_CLOSED,
                NonConfigurableConstants.NON_BILLABLE_TXN_CHARGEBACK,
                NonConfigurableConstants.NON_BILLABLE_TXN_REFUNDED}));
        criteria.add(disjunction);

        if (form.paymentType != null)
            masterCriteria.add(Restrictions.eq("masterCode", form.paymentType));
        if (form.acquirer != null)
            acquirerCriteria.add(Restrictions.idEq(form.acquirer.getAcquirerNo()));
        if (form.tripDateFrom != null)
            criteria.add(Restrictions.ge("tripStartDt", DateUtil.convertDateTo0000Hours(form.tripDateFrom)));
        if (form.tripDateTo != null)
            criteria.add(Restrictions.le("tripStartDt", DateUtil.convertDateTo2359Hours(form.tripDateTo)));
        if (form.totalAmount != null)
            criteria.add(Restrictions.eq("total", form.totalAmount));
        if (form.taxiNo != null && form.taxiNo.length() > 0)
            criteria.add(Restrictions.eq("taxiNo", form.taxiNo));
        if (form.driverID != null && form.driverID.length() > 0)
            criteria.add(Restrictions.eq("nric", form.driverID));
        if (form.jobNo != null && form.jobNo.length() > 0)
            criteria.add(Restrictions.eq("jobNo", form.jobNo));
        if (form.offline != null && form.offline.length() > 0)
            criteria.add(Restrictions.eq("offlineFlag", form.offline));
        if (form.txnStatus != null && form.txnStatus.length() > 0)
            criteria.add(Restrictions.eq("status", form.txnStatus));
        criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);

        criteria.addOrder(Order.asc("tripStartDt"));

        List<TmtbNonBillableTxn> result = findDefaultMaxResultByCriteria(criteria);

        return findDefaultMaxResultByCriteria(criteria);
    }


    public List<MstbMasterTable> getPymtType(MstbAcquirer acquirer) {
        Session session = this.getHibernateTemplate().getSessionFactory().openSession();
        List results = new ArrayList();
        try {
            Query query = session.getNamedQuery("getPymtTypeByAcquirer");
            query.setParameter(0, acquirer.getAcquirerNo());
            results = query.list();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }

        return results;
    }

    public boolean isChargebackTxnGLed(TmtbNonBillableTxn txn) {
        DetachedCriteria criteria = DetachedCriteria.forClass(FmtbGlLogDetail.class);
        criteria.add(Restrictions.eq("tmtbNonBillableTxn", txn));

        List results = this.findAllByCriteria(criteria);
        if (results.isEmpty()) return false;
        else return true;
    }

    public List<VwBankAdviseAyden> retrieveAydenSettlementDetailReport() {
        DetachedCriteria criteria = DetachedCriteria.forClass(VwBankAdviseAyden.class);
        criteria.add(Restrictions.or(Restrictions.eq("retrievedFlag", "N"), Restrictions.isNull("retrievedFlag")));
        criteria.add(Restrictions.ne("type",NonConfigurableConstants.AYDEN_RECORD_STATUS_MERCHANT_PAYOUT));


        List results = this.findAllByCriteria(criteria);
        if (results.isEmpty()) return null;
        else return results;
    }

    public List<VwBankAdviseAmex> retrieveAmexSettlementDetailReport() {
        DetachedCriteria criteria = DetachedCriteria.forClass(VwBankAdviseAmex.class);
        criteria.add(Restrictions.or(Restrictions.eq("retrievedFlag", "N"), Restrictions.isNull("retrievedFlag")));


        List results = this.findAllByCriteria(criteria);
        if (results.isEmpty()) return null;
        else return results;
    }

    // WanTing - 24/12/2020 - CR/1220/028 Start    
    public VwBankAdviceIBS cloneVwBankAdviceIBS(VwBankAdviceIBS item) {

    	VwBankAdviceIBS advice = new VwBankAdviceIBS();
    	
    			advice.setPspRefNo(item.getPspRefNo());
    			advice.setRecordType(item.getRecordType());
    			advice.setSubmissionMerchantId(item.getSubmissionMerchantId());
    			advice.setBatchCode(item.getBatchCode());
    			advice.setPaymentMethod(item.getPaymentMethod());
    			advice.setGrossAmount(item.getGrossAmount());
    			advice.setGrossDebit(item.getGrossDebit());
    			advice.setGrossCredit(item.getGrossCredit());
    			advice.setNetDebit(item.getNetDebit());
    			advice.setNetCredit(item.getNetCredit());
    			advice.setCommission(item.getCommission());
    			advice.setMarkup(item.getMarkup());
    			advice.setSchemeFee(item.getSchemeFee());
    			advice.setInterchange(item.getInterchange());
    			//amex
    			advice.setPaymentDate(item.getPaymentDate());
    			advice.setPaymentCurrency(item.getPaymentCurrency());
    			advice.setTransactionAmount(item.getTransactionAmount());
    			advice.setTransactionDate(item.getTransactionDate());
    			advice.setFeeCode(item.getFeeCode());
    			advice.setFeeAmount(item.getFeeAmount());
    			advice.setDiscountRate(item.getDiscountRate());
    			advice.setDiscountAmount(item.getDiscountAmount());
    			advice.setChargebackNo(item.getChargebackNo());
    			advice.setChargebackReasonCode(item.getChargebackReasonCode());
    			advice.setChargebackReasonDescription(item.getChargebackReasonDescription());
    			advice.setServiceFeeAmount(item.getServiceFeeAmount());
    			advice.setTaxAmount(item.getTaxAmount());
    			advice.setNetAmount(item.getNetAmount());
    			advice.setServiceFeeRate(item.getServiceFeeRate());
    			advice.setAdjustmentNo(item.getAdjustmentNo());
    			advice.setAdjustmentReasonCode(item.getAdjustmentReasonCode());
    			advice.setAdjustmentReasonDescription(item.getAdjustmentReasonDescription());
    			advice.setStatus(item.getStatus());

//    			  private TmtbNonBillableTxn tmtbNonBillableTxn1(
//    			  private TmtbNonBillableTxn tmtbNonBillableTxn2(


    			advice.setSource(item.getSource());

    			advice.setCreatedDt(item.getCreatedDt());
    			advice.setCreatedBy(item.getCreatedBy());
    			advice.setModifiedDt(item.getModifiedDt());
    			advice.setUpdatedDt(item.getUpdatedDt());
    			advice.setUpdatedBy(item.getUpdatedBy());
    			advice.setRetrievedFlag(item.getRetrievedFlag());
    			advice.setRetrievedDate(item.getRetrievedDate());
    	
    	return advice;
    }
    
//    public List<List<VwBankAdviceIBS>> retrieveCommonSettlementDetailReport() {
//        DetachedCriteria criteria = DetachedCriteria.forClass(VwBankAdviceIBS.class);
//        criteria.add(Restrictions.or(Restrictions.eq("retrievedFlag", "N"), Restrictions.isNull("retrievedFlag")));
//
//
//        List<VwBankAdviceIBS> retrievedList = this.findAllByCriteria(criteria);
//        List<VwBankAdviceIBS> consolidateList = null;
//        List<VwBankAdviceIBS> tempCopyList = new ArrayList<VwBankAdviceIBS>();
//        Iterator<VwBankAdviceIBS> iterator = retrievedList.iterator();
//
//        //to deep copy retrieved data into a temporary list to avoid overwriting VwBankAdviceIBS's value when update retrievedFlag and retrievedDate
//        while(iterator.hasNext()){
//        	VwBankAdviceIBS retrieved = iterator.next();
//        	tempCopyList.add(cloneVwBankAdviceIBS(retrieved));
//        }
//
//        Map<String, VwBankAdviceIBS> fullMap = new HashMap();
//        List<VwBankAdviceIBS> commissionList = null;
//        List<VwBankAdviceIBS> markupList = null;
//        List<VwBankAdviceIBS> unmappedCommissionList = null;
//        List<VwBankAdviceIBS> unmappedMarkupList = null;
//
//        if (retrievedList.isEmpty())
//        	return null;
//
//        else {
//        	for (VwBankAdviceIBS item : tempCopyList) {
//
//        		if (item.getGrossAmount() != null) {
//        			fullMap.put(item.getPspRefNo(), item);
//        		}
//        		if (item.getCommission() != null) {
//        			if (commissionList == null) {
//        				commissionList = new ArrayList<VwBankAdviceIBS>();
//        			}
//        			commissionList.add(item);
//        		}
//        		if (item.getMarkup() != null) {
//        			if (markupList == null) {
//        				markupList = new ArrayList<VwBankAdviceIBS>();
//        			}
//        			markupList.add(item);
//        		}
//        	}
//
//            logger.info("Total Lazada settlement records retrieved : " + ((retrievedList != null)? retrievedList.size() : 0));
//			logger.info("Total Lazada settlement (commission) records retrieved : " + ((commissionList != null)? commissionList.size() : 0));
//			logger.info("Total Lazada settlement (markup) records retrieved : " + ((markupList != null)? markupList.size() : 0));
//
//        	//to process all the commission retrieved, map into corresponding gross amount record using PSP_REF_NO
//			if (commissionList != null ) {
//				for (VwBankAdviceIBS commission : commissionList) {
//	        		if (fullMap.get(commission.getPspRefNo()) != null) {
//	        			VwBankAdviceIBS tempRecord = fullMap.get(commission.getPspRefNo());
//	        			tempRecord.setCommission(commission.getCommission());
//	        			fullMap.put(commission.getPspRefNo(), tempRecord);
//
//	        		} else {
//	        			if (unmappedCommissionList == null) {
//	        				unmappedCommissionList = new ArrayList<VwBankAdviceIBS>();
//	        			}
//	        			unmappedCommissionList.add(commission);
//	        		}
//	        	}
//			}
//
//        	//to process all the markup retrieved, map into corresponding gross amount record using PSP_REF_NO
//			if (markupList != null) {
//				for (VwBankAdviceIBS markup : markupList) {
//	        		if (fullMap.get(markup.getPspRefNo()) != null) {
//	        			VwBankAdviceIBS tempRecord = fullMap.get(markup.getPspRefNo());
//	        			tempRecord.setMarkup(markup.getMarkup());
//	        			fullMap.put(markup.getPspRefNo(), tempRecord);
//
//	        		}  else {
//	        			if (unmappedMarkupList == null) {
//	        				unmappedMarkupList = new ArrayList<VwBankAdviceIBS>();
//	        			}
//	        			unmappedMarkupList.add(markup);
//	        		}
//	        	}
//			}
//
//			consolidateList = new ArrayList<VwBankAdviceIBS>(fullMap.values());
//        	logger.info("Total Lazada CRCA records to be insert : " + consolidateList.size());
//			logger.info("Total Unmapped Lazada settlement (commission) records : " + ((unmappedCommissionList != null)? unmappedCommissionList.size() : 0));
//			logger.info("Total Unmapped Lazada settlement (markup) records  : " + ((unmappedMarkupList != null)? unmappedMarkupList.size() : 0));
//
//        }
//
//        List<List<VwBankAdviceIBS>> listOfLists = new ArrayList<List<VwBankAdviceIBS>>();
//        listOfLists.add(consolidateList);
//        listOfLists.add(retrievedList);
////        listOfLists.add(commissionList);
////        listOfLists.add(markupList);
//
//        return listOfLists;
//    }

    public List<VwBankAdviceIBS> retrieveCommonSettlementDetailReport() {
        DetachedCriteria criteria = DetachedCriteria.forClass(VwBankAdviceIBS.class);
        criteria.add(Restrictions.or(Restrictions.eq("retrievedFlag", "N"), Restrictions.isNull("retrievedFlag")));


        List results = this.findAllByCriteria(criteria);
        if (results.isEmpty()) return null;
        else return results;
    }
    
    public <T> void processCommonSettlement(
            List<TmtbNonBillableTxnCrca> crcaList,
            List<T> ittbSetlReportingCommonList,
            List<VwBankAdvice> commonSettlementDetailList) {

//        org.hibernate.Session session = this.getHibernateTemplate().getSessionFactory().getCurrentSession();
    	Session session = this.getHibernateTemplate().getSessionFactory().getCurrentSession();
        Transaction txn = session.beginTransaction();
		
        //txn start
        saveAll(crcaList, "Lazada Settlement");
        if (ittbSetlReportingCommonList != null) {        	
        saveAll(ittbSetlReportingCommonList, "Lazada Settlement");
        }
        saveAll(commonSettlementDetailList, "Lazada Settlement");

        //txn commit
        txn.commit();
    }    
    // WanTing - 24/12/2020 - CR/1220/028 End 

    public void processAydenSettlement(
            List<TmtbNonBillableTxnCrca> crcaList,
            List<IttbSetlReportingAyden> ittbSetlReportingAydenList,
            List<VwBankAdviseAyden> aydenSettlementDetailList) {

    	Session session = this.getHibernateTemplate().getSessionFactory().getCurrentSession();
//        org.hibernate.Session session = this.getHibernateTemplate().getSessionFactory().getCurrentSession();
    	Transaction txn = session.beginTransaction();

        //txn start
        saveAll(crcaList, "Ayden Settlement");
        saveAll(ittbSetlReportingAydenList, "Ayden Settlement");
        saveAll(aydenSettlementDetailList, "Ayden Settlement");

        //txn commit
        txn.commit();
    }

    public void processAmexSettlement(
            List<TmtbNonBillableTxnCrca> tmtbNonBillableTxnCrcaList,
            List<IttbSetlReportingAmex> ittbSetlReportingAmexList,
            List<VwBankAdviseAmex> amexSettlementDetailList) {

//        org.hibernate.Session session = this.getHibernateTemplate().getSessionFactory().getCurrentSession();
        Session session = this.getHibernateTemplate().getSessionFactory().getCurrentSession();
        Transaction txn = session.beginTransaction();

        // txn start
        saveAll(tmtbNonBillableTxnCrcaList, "Amex Settlement");
        saveAll(ittbSetlReportingAmexList, "Amex Settlement");
        saveAll(amexSettlementDetailList, "Amex Settlement");

        // txn commit
        txn.commit();
    }

    public List<TmtbNonBillableTxnCrca> retrieveCrca(TmtbNonBillableTxnCrca crca) {
        DetachedCriteria criteria = DetachedCriteria.forClass(TmtbNonBillableTxnCrca.class);

        criteria.add(Restrictions.eq("pspRefNo", crca.getPspRefNo()));

        List<TmtbNonBillableTxnCrca> result = findDefaultMaxResultByCriteria(criteria);

        return result;
    }

    public List<TmtbNonBillableTxnCrca> retrieveCrca(String pspRefNo) {
        DetachedCriteria criteria = DetachedCriteria.forClass(TmtbNonBillableTxnCrca.class);

        criteria.add(Restrictions.eq("pspRefNo", pspRefNo));

        List<TmtbNonBillableTxnCrca> result = findDefaultMaxResultByCriteria(criteria);

        return result;
    }


    public TmtbNonBillableTxnCrcaReq searchTmtbCrcaRequest() {

        DetachedCriteria criteria = DetachedCriteria.forClass(TmtbNonBillableTxnCrcaReq.class);
        criteria.add(Restrictions.eq("status", "P"));

        List<TmtbNonBillableTxnCrcaReq> result = findDefaultMaxResultByCriteria(criteria);

        if (result != null && result.size() > 0)
            return result.get(0);
        else
            return null;
    }

    @Override
    public List<TmtbNonBillableTxn> searchNonBillableTxnsWithMatchingStatues(SearchNonBillableTxnsForm form) {
        logger.info("IN searchNonBillableTxnsWithMatchingStatues::::::::");

        Session session = this.getHibernateTemplate().getSessionFactory().openSession();
        List<Object[]> results = new ArrayList<Object[]>();
        List<TmtbNonBillableTxn> finalResults = new ArrayList<TmtbNonBillableTxn>();


        List<Long> batchIdList = new ArrayList<Long>();

        for (TmtbNonBillableBatch batch : form.txnBatches) {
            batchIdList.add(batch.getBatchId());
        }

        try {
            Query query = session.getNamedQuery("searchNonBillableTxnsWithMatchingStatues");


            query.setParameterList("matchingStatus", form.matchingStatuses, Hibernate.STRING);
            if (StringUtils.isNotBlank(form.jobNo)) {
                query.setParameter("jobNo", form.jobNo, Hibernate.STRING);
            } else {
                query.setParameter("jobNo", "%", Hibernate.STRING);
            }
            query.setParameterList("batchId", batchIdList, Hibernate.LONG);
            query.setParameterList("acquirer",form.acquirerList, Hibernate.STRING);

            results = query.list();

            if (results != null && !results.isEmpty()) {
                for (Object[] temp : results) {
                    finalResults.add((TmtbNonBillableTxn) temp[0]);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }

        logger.info("::::::Out of searchNonBillableTxnsWithMatchingStatues:::::");

        return finalResults;
    }

    public List<Object[]> searchExcessCrca(SearchNonBillableTxnsForm form) {
        Session session = this.getHibernateTemplate().getSessionFactory().openSession();
        List<Object[]> results = new ArrayList<Object[]>();
        List<String> fileNameList = new ArrayList<String>();

        for (TmtbNonBillableBatch tmtbNonBillableBatch : form.txnBatches) {
            if(StringUtils.isNotBlank(tmtbNonBillableBatch.getFileName())) {
                String temp[] = tmtbNonBillableBatch.getFileName().split(",");
                for (int i = 0; i < temp.length; i++) {
                    fileNameList.add(temp[i]);
                }
            }
        }

        form.recordType.add(null);
        form.recordType.add(null);
        fileNameList.add(null);
        fileNameList.add(null);

        try {
            Query query = session.getNamedQuery("searchExcessCrca");
            query.setParameterList("recordType", form.recordType, Hibernate.STRING);
            query.setParameterList("matchingStatuses", form.matchingStatuses, Hibernate.STRING);
            query.setParameterList("source",form.sourceList,Hibernate.STRING);
            query.setParameterList("fileName", fileNameList, Hibernate.STRING);


            results = query.list();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }

        if (results == null) {
            return new ArrayList<Object[]>();
        }

        return results;
    }

    @Override
    public List<TmtbNonBillableTxn> searchNonBillablePendingTxnsNotFoundInCrca(SearchNonBillableTxnsForm form) {
        logger.info("IN searchNonBillablePendingTxnsNotFoundInCrca::::::::");

        Session session = this.getHibernateTemplate().getSessionFactory().openSession();
        List<Object[]> results = new ArrayList<Object[]>();
        List<TmtbNonBillableTxn> finalResults = new ArrayList<TmtbNonBillableTxn>();
        List<Long> batchIdList = new ArrayList<Long>();

        for (TmtbNonBillableBatch batch : form.txnBatches) {
            batchIdList.add(batch.getBatchId());
        }

//        for(String temp: form.matchingStatuses){
//            logger.info("form.matchingStatuses: " + temp);
//        }
//        for(Long temp: batchIdList){
//            logger.info("form.batchId: " + temp);
//        }


        try {
            Query query = session.getNamedQuery("searchNonBillablePendingTxnsNotFoundInCrca");

            query.setParameterList("matchingStatus", form.matchingStatuses, Hibernate.STRING);
            query.setParameterList("batchId", batchIdList, Hibernate.LONG);
            query.setParameterList("acquirer",form.acquirerList, Hibernate.STRING);

            results = query.list();

            if (results != null && !results.isEmpty()) {
                for (Object[] temp : results) {
                    finalResults.add((TmtbNonBillableTxn) temp[0]);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }

        return finalResults;
    }


    @Override
    public List<TmtbNonBillableTxn> searchNonBillableChargebackRefundedTxns(SearchNonBillableTxnsForm txnsForm) {

        logger.info("IN searchNonBillableChargebackRefundedTxns::::::::");

        Session session = this.getHibernateTemplate().getSessionFactory().openSession();
        List<Object[]> results;
        List<TmtbNonBillableTxn> finalResults = new ArrayList();

        List<Date> settlementDateList = new ArrayList<Date>();
        List<Long> batchIdList = new ArrayList<Long>();

        for (TmtbNonBillableBatch batch : txnsForm.txnBatches) {
            settlementDateList.add(batch.getSettlementDate());
            batchIdList.add(batch.getBatchId());
            batchIdList.add(null);
        }

        try {
            Query query = session.getNamedQuery("searchNonBillableChargebackRefundedTxns");

            query.setParameterList("matchingStatus", txnsForm.matchingStatuses, Hibernate.STRING);
            query.setParameterList("recordType", txnsForm.recordType, Hibernate.STRING);
            query.setParameterList("batchId", batchIdList, Hibernate.LONG);

            results = query.list();
            logger.info("results1.size(): " + results.size());

            Object[] prev = null;
            Set<TmtbNonBillableTxnCrca> crca1Set = new HashSet<TmtbNonBillableTxnCrca>();
            Set<TmtbNonBillableTxnCrca> crca2Set = new HashSet<TmtbNonBillableTxnCrca>();
            TmtbNonBillableTxn txn = new TmtbNonBillableTxn();

            if (results != null && !results.isEmpty()) {
                for (Object[] temp : results) {
                    if (prev == null) {
                        txn = (TmtbNonBillableTxn) temp[0];
                        if (temp.length > 2 && temp[1] != null) {
                            crca1Set = new HashSet<TmtbNonBillableTxnCrca>();
                            crca1Set.add((TmtbNonBillableTxnCrca) temp[1]);
                        } else {
                            crca1Set = null;
                        }
                        if (temp.length > 3 && temp[2] != null) {
                            crca2Set = new HashSet<TmtbNonBillableTxnCrca>();
                            crca2Set.add((TmtbNonBillableTxnCrca) temp[2]);
                        } else {
                            crca2Set = null;
                        }

                        txn.setTmtbNonBillableTxnCrca1(crca1Set);
                        txn.setTmtbNonBillableTxnCrca2(crca2Set);

                        prev = temp;
                        continue;
                    }
                    if (((TmtbNonBillableTxn) temp[0]).getTxnId().equals(((TmtbNonBillableTxn) prev[0]).getTxnId())) {

                        if (temp.length > 2 && temp[1] != null) {
                            crca1Set.add((TmtbNonBillableTxnCrca) temp[1]);
                        }
                        if (temp.length > 3 && temp[2] != null) {
                            crca2Set.add((TmtbNonBillableTxnCrca) temp[2]);
                        }

                    } else {
                        finalResults.add(txn);
                        txn = (TmtbNonBillableTxn) temp[0];

                        if (temp.length > 2 && temp[1] != null) {
                            crca1Set = new HashSet<TmtbNonBillableTxnCrca>();
                            crca1Set.add((TmtbNonBillableTxnCrca) temp[1]);
                        } else {
                            crca1Set = null;
                        }
                        if (temp.length > 3 && temp[2] != null) {
                            crca2Set = new HashSet<TmtbNonBillableTxnCrca>();
                            crca2Set.add((TmtbNonBillableTxnCrca) temp[2]);
                        } else {
                            crca2Set = null;
                        }

                    }

                    txn.setTmtbNonBillableTxnCrca1(crca1Set);
                    txn.setTmtbNonBillableTxnCrca2(crca2Set);
                    prev = temp;
                }
            }

            if (txn.getTxnId() != null) {
                finalResults.add(txn);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }

        logger.info("OUT searchNonBillableChargebackRefundedTxns::::::::");


        return finalResults;

    }


    public int updateNonBillableTxnMatchingStatusByHql(String matchingStatus, List<Long> txnIdList) {
        Session session = this.getHibernateTemplate().getSessionFactory().openSession();

        Transaction tx = session.beginTransaction();
        int result = -1;

        try {
            String hqlUpdate = "update TmtbNonBillableTxn txn set txn.matchingStatus = :matchingStatus where txn.txnId in (:txnId)";
            Query query = session.createQuery(hqlUpdate);
            query.setParameter("matchingStatus", matchingStatus);
            query.setParameterList("txnId", txnIdList);

            result = query.executeUpdate();

            tx.commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }

        return result;
    }
}
