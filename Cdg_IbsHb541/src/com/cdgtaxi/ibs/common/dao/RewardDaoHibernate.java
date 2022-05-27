package com.cdgtaxi.ibs.common.dao;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
import org.hibernate.type.StandardBasicTypes;

import com.cdgtaxi.ibs.common.NonConfigurableConstants;
import com.cdgtaxi.ibs.common.model.AmtbAccount;
import com.cdgtaxi.ibs.common.model.AmtbAcctStatus;
import com.cdgtaxi.ibs.common.model.LrtbGiftCategory;
import com.cdgtaxi.ibs.common.model.LrtbGiftItem;
import com.cdgtaxi.ibs.common.model.LrtbGiftStock;
import com.cdgtaxi.ibs.common.model.LrtbRewardAccount;
import com.cdgtaxi.ibs.common.model.LrtbRewardAdjReq;
import com.cdgtaxi.ibs.common.model.LrtbRewardAdjReqFlow;
import com.cdgtaxi.ibs.common.model.LrtbRewardTxn;
import com.cdgtaxi.ibs.common.model.forms.SearchAdjustmentRequestForm;
import com.cdgtaxi.ibs.common.model.forms.SearchGiftItemForm;
import com.cdgtaxi.ibs.master.model.LrtbRewardDetail;
import com.cdgtaxi.ibs.master.model.LrtbRewardMaster;
import com.cdgtaxi.ibs.util.DateUtil;

@SuppressWarnings("unchecked")
public class RewardDaoHibernate extends GenericDaoHibernate implements RewardDao {

	public List<LrtbRewardMaster> getAllRewardsPlan(){
		DetachedCriteria criteria = DetachedCriteria.forClass(LrtbRewardMaster.class);
		DetachedCriteria detailCriteria = criteria.createCriteria("lrtbRewardDetails", org.hibernate.sql.JoinType.LEFT_OUTER_JOIN);
		detailCriteria.createCriteria("lrtbRewardTiers", org.hibernate.sql.JoinType.LEFT_OUTER_JOIN);
		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		return this.findAllByCriteria(criteria);
	}
	
	public boolean categoryHasItems(Integer planNo) {
		DetachedCriteria planCriteria = DetachedCriteria.forClass(LrtbGiftCategory.class);
		planCriteria.createCriteria("lrtbGiftItems");
		planCriteria.add(Restrictions.idEq(planNo));
		planCriteria.setProjection(Projections.rowCount());

		Integer count = (Integer) findAllByCriteria(planCriteria).get(0);

		return count > 0;
	}

	public LrtbRewardMaster getPlanWithDetails(Integer planNo) {
		DetachedCriteria planCriteria = DetachedCriteria.forClass(LrtbRewardMaster.class);
		planCriteria.createCriteria("lrtbRewardDetails", org.hibernate.sql.JoinType.LEFT_OUTER_JOIN);
		planCriteria.add(Restrictions.idEq(planNo));

		List list = findAllByCriteria(planCriteria);
		if (list.size() > 0) {
			return (LrtbRewardMaster) list.get(0);
		} else {
			return null;
		}
	}

	public LrtbRewardDetail getDetailWithTiers(Integer planDetailNo) {
		DetachedCriteria detailCriteria = DetachedCriteria.forClass(LrtbRewardDetail.class);
		detailCriteria.createCriteria("lrtbRewardMaster", DetachedCriteria.INNER_JOIN);
		detailCriteria.createCriteria("lrtbRewardTiers", org.hibernate.sql.JoinType.LEFT_OUTER_JOIN);
		detailCriteria.add(Restrictions.idEq(planDetailNo));

		List list = findAllByCriteria(detailCriteria);
		if (list.size() > 0) {
			return (LrtbRewardDetail) list.get(0);
		} else {
			return null;
		}
	}

	public LrtbGiftCategory getCategoryWithItems(Integer giftCategoryNo) {
		DetachedCriteria categoryCriteria = DetachedCriteria.forClass(LrtbGiftCategory.class);
		categoryCriteria.createCriteria("lrtbGiftItems", org.hibernate.sql.JoinType.LEFT_OUTER_JOIN);
		categoryCriteria.add(Restrictions.idEq(giftCategoryNo));

		List list = findAllByCriteria(categoryCriteria);

		if (list.size() > 0) {
			return (LrtbGiftCategory) list.get(0);
		} else {
			return null;
		}
	}

	public boolean itemWasRedeemed(Integer giftItemNo) {
		DetachedCriteria stockCriteria = DetachedCriteria.forClass(LrtbGiftStock.class);
		DetachedCriteria itemCriteria = stockCriteria.createCriteria("lrtbGiftItem", DetachedCriteria.INNER_JOIN);
		itemCriteria.add(Restrictions.idEq(giftItemNo));
		stockCriteria.setProjection(Projections.rowCount());
		//Subqueries.exists(itemCriteria);
		Integer count = (Integer) findAllByCriteria(stockCriteria).get(0);

		return count > 0;
	}

	public List<LrtbGiftItem> searchItem(SearchGiftItemForm form) {
		DetachedCriteria itemCriteria = DetachedCriteria.forClass(LrtbGiftItem.class);
		itemCriteria.createAlias("lrtbGiftCategory", "c");

		if (form.getCategoryNo() != null) {
			itemCriteria.add(Restrictions.eq("c.giftCategoryNo", form.getCategoryNo()));
		}

		if (!"".equals(form.getItemCode().trim())) {
			itemCriteria.add(Restrictions.like("itemCode", form.getItemCode(), MatchMode.ANYWHERE));
		}

		if (!"".equals(form.getItemName().trim())) {
			itemCriteria.add(Restrictions.like("itemName", form.getItemName(), MatchMode.ANYWHERE));
		}

		if (form.getPointsFrom() != null) {
			itemCriteria.add(Restrictions.ge("points", form.getPointsFrom()));
		}

		if (form.getPointsTo() != null) {
			itemCriteria.add(Restrictions.le("points", form.getPointsTo()));
		}

		if (form.getPriceFrom() != null) {
			itemCriteria.add(Restrictions.ge("price", form.getPriceFrom()));
		}

		if (form.getPriceTo() != null) {
			itemCriteria.add(Restrictions.le("price", form.getPriceTo()));
		}

		return findDefaultMaxResultByCriteria(itemCriteria);
	}

	/**
	 * Retrieve account with points that are not expired. Calculation of the total
	 * "previous" and "current" points has to be summed up separately.
	 */
	public AmtbAccount getAccountWithUsablePoints(Integer accountNo) {
		DetachedCriteria accountCriteria = DetachedCriteria.forClass(AmtbAccount.class, "acct");
		accountCriteria.createAlias("amtbAcctStatuses", "sts", org.hibernate.sql.JoinType.LEFT_OUTER_JOIN);
		accountCriteria.createCriteria("amtbContactPersons", org.hibernate.sql.JoinType.LEFT_OUTER_JOIN);

		DetachedCriteria effectiveStatusDt =
			DetachedCriteria.forClass(AmtbAcctStatus.class, "lastEffDt1")
			.add(Restrictions.eqProperty("amtbAccount", "acct.accountNo"))
			.add(Restrictions.le("effectiveDt", DateUtil.getCurrentDate()))
			.setProjection(Projections.max("effectiveDt"));
		Criterion latestStatus = Subqueries.propertyEq("sts.effectiveDt", effectiveStatusDt);

		accountCriteria.add(Restrictions.idEq(accountNo));
		accountCriteria.add(latestStatus);

		DetachedCriteria issueCriteria = accountCriteria.createCriteria("lrtbRewardAccounts", org.hibernate.sql.JoinType.LEFT_OUTER_JOIN);
		issueCriteria.createCriteria("lrtbRewardTxns", org.hibernate.sql.JoinType.LEFT_OUTER_JOIN);
		issueCriteria.add(
				//Restrictions.disjunction()
				//.add(Restrictions.isNull("rewardAccountNo"))
				//.add(
						Restrictions.gt("ibsExpireDt", DateUtil.getCurrentDate())
				//)
		);

		List list = findAllByCriteria(accountCriteria);
		if (list.size() > 0) {
			return (AmtbAccount) list.get(0);
		} else {
			return null;
		}
	}

	public LrtbGiftItem getItem(Integer giftItemNo) {
		DetachedCriteria itemCriteria = DetachedCriteria.forClass(LrtbGiftItem.class);
		itemCriteria.createCriteria("lrtbGiftCategory", DetachedCriteria.INNER_JOIN);
		itemCriteria.add(Restrictions.idEq(giftItemNo));

		List list = findAllByCriteria(itemCriteria);

		if (list.size() > 0) {
			return (LrtbGiftItem) list.get(0);
		} else {
			return null;
		}
	}

	public List<LrtbGiftStock> getRedemptionHistory(Integer accountNo) {
		DetachedCriteria stockCriteria = DetachedCriteria.forClass(LrtbGiftStock.class);
		DetachedCriteria txnCriteria = stockCriteria.createCriteria("lrtbRewardTxns", org.hibernate.sql.JoinType.LEFT_OUTER_JOIN);
		DetachedCriteria issueCriteria = txnCriteria.createCriteria("lrtbRewardAccount", DetachedCriteria.INNER_JOIN);
		DetachedCriteria accountCriteria = issueCriteria.createCriteria("amtbAccount", DetachedCriteria.INNER_JOIN);
		txnCriteria.createCriteria("amtbContactPerson", org.hibernate.sql.JoinType.LEFT_OUTER_JOIN);
		accountCriteria.add(Restrictions.idEq(accountNo));
		
		stockCriteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		
		return findAllByCriteria(stockCriteria);
	}

	public Date getLatestToDate(Integer planNo) {
		DetachedCriteria planCriteria = DetachedCriteria.forClass(LrtbRewardMaster.class);
		planCriteria.createCriteria("lrtbRewardDetails", "d", org.hibernate.sql.JoinType.LEFT_OUTER_JOIN);
		planCriteria.add(Restrictions.idEq(planNo));
		planCriteria.setProjection(Projections.max("d.effectiveDtTo"));

		List list = findAllByCriteria(planCriteria);
		if (list.size() > 0) {
			return (Timestamp) list.get(0);
		} else {
			return null;
		}
	}
	
	public LrtbRewardTxn getInitialRewardPointsTxn(LrtbRewardAccount rewardAcct){
		DetachedCriteria criteria = DetachedCriteria.forClass(LrtbRewardTxn.class);
		DetachedCriteria rewardAcctCriteria = criteria.createCriteria("lrtbRewardAccount", org.hibernate.sql.JoinType.LEFT_OUTER_JOIN);
		rewardAcctCriteria.add(Restrictions.idEq(rewardAcct.getRewardAccountNo()));
		criteria.add(Restrictions.isNull("bmtbInvoiceHeader"));
		criteria.add(Restrictions.isNull("lrtbRewardMaster"));
		criteria.add(Restrictions.isNull("lrtbGiftStock"));
		List results = findAllByCriteria(criteria);
		if(results.isEmpty()) return null;
		//should only return one record has each account should only have one initial points txn
		else return (LrtbRewardTxn) results.get(0);
	}
	
	
	public List<LrtbRewardMaster> getOverlappingRewards(LrtbRewardDetail rewardDetail){
		DetachedCriteria criteria = DetachedCriteria.forClass(LrtbRewardDetail.class);
		criteria.add(Restrictions.eq("lrtbRewardMaster", rewardDetail.getLrtbRewardMaster()));
		
		//for create check as creation that time the primary key is still null.
		//if null, the query will return nothing at all as the condition does not work. 
		if(rewardDetail.getRewardPlanDetailNo()!=null)
			criteria.add(Restrictions.ne("rewardPlanDetailNo", rewardDetail.getRewardPlanDetailNo()));
		
		Disjunction disjunction = Restrictions.disjunction();
		disjunction.add(Restrictions.sqlRestriction("? between effective_Dt_From and effective_Dt_To", rewardDetail.getEffectiveDtFrom(), StandardBasicTypes.TIMESTAMP));
		disjunction.add(Restrictions.sqlRestriction("? between effective_Dt_From and effective_Dt_To", rewardDetail.getEffectiveDtTo(), StandardBasicTypes.TIMESTAMP));
		
		criteria.add(disjunction);
		
		return this.findAllByCriteria(criteria);
	}
	
	public BigDecimal getTotalRedeemedPoints(AmtbAccount account){
		DetachedCriteria criteria = DetachedCriteria.forClass(LrtbRewardAccount.class);
		DetachedCriteria txn = criteria.createCriteria("lrtbRewardTxns", "txn", org.hibernate.sql.JoinType.LEFT_OUTER_JOIN);
		DetachedCriteria stock = txn.createCriteria("lrtbGiftStock", org.hibernate.sql.JoinType.LEFT_OUTER_JOIN);
		
		criteria.add(Restrictions.eq("amtbAccount.accountNo", account.getAccountNo()));
		stock.add(Restrictions.eq("txnType", NonConfigurableConstants.STOCK_TXN_TYPE_REDEEMED));
		txn.setProjection(Projections.sum("txn.rewardsPts"));
		
		List results = this.findAllByCriteria(criteria);
		if(results.isEmpty()) return BigDecimal.ZERO;
		else{
			if(results.get(0) == null) return BigDecimal.ZERO;
			else return (new BigDecimal(results.get(0).toString())).negate(); //change to positive number
		}
	}
	
	public BigDecimal getTotalEarnedPoints(AmtbAccount account){
		DetachedCriteria criteria = DetachedCriteria.forClass(LrtbRewardAccount.class);
		DetachedCriteria txn = criteria.createCriteria("lrtbRewardTxns", "txn", org.hibernate.sql.JoinType.LEFT_OUTER_JOIN);
		
		criteria.add(Restrictions.eq("amtbAccount.accountNo", account.getAccountNo()));
		txn.add(Restrictions.gt("rewardsPts", 0));
		txn.setProjection(Projections.sum("txn.rewardsPts"));
		
		List results = this.findAllByCriteria(criteria);
		if(results.isEmpty()) return BigDecimal.ZERO;
		else{
			if(results.get(0) == null) return BigDecimal.ZERO;
			else return new BigDecimal(results.get(0).toString());
		}
	}
	
	public List<LrtbRewardAdjReq> getPendingAdjustmentRequests(){
		DetachedCriteria requestCriteria = DetachedCriteria.forClass(LrtbRewardAdjReq.class);
		requestCriteria.createCriteria("lrtbRewardAdjReqFlows", org.hibernate.sql.JoinType.LEFT_OUTER_JOIN);
		requestCriteria.createCriteria("amtbAccount", org.hibernate.sql.JoinType.LEFT_OUTER_JOIN);
		
		requestCriteria.addOrder(Order.asc("createdDt"));
		requestCriteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		
		List<LrtbRewardAdjReq> requests = findAllByCriteria(requestCriteria);
		List<LrtbRewardAdjReq> returnList = new ArrayList<LrtbRewardAdjReq>();
		for(LrtbRewardAdjReq request : requests){
			boolean pending = true;
			for(LrtbRewardAdjReqFlow flow : request.getLrtbRewardAdjReqFlows()){
				if(!flow.getToStatus().equals(NonConfigurableConstants.REWARDS_ADJ_REQUEST_STATUS_PENDING)){
					pending = false;
					break;
				}
			}
			if(pending){
				returnList.add(request);
			}
		}
		return returnList;
	}
	
	public LrtbRewardAccount getCurentYearRewardAccount(Integer accountNo){
		DetachedCriteria reward = DetachedCriteria.forClass(LrtbRewardAccount.class);
		DetachedCriteria account = reward.createCriteria("amtbAccount", Criteria.LEFT_JOIN);
		
		account.add(Restrictions.idEq(accountNo));
		reward.add(Restrictions.ge("cutOffDt", DateUtil.getCurrentTimestamp()));
		reward.addOrder(Order.asc("cutOffDt"));
		
		reward.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		
		List results = this.findMaxResultByCriteria(reward, 1);
		if(results.isEmpty()) return null;
		else return (LrtbRewardAccount) results.get(0);
	}
	
	public List<LrtbRewardAdjReqFlow> searchAdjustmentRequest(SearchAdjustmentRequestForm form){
		DetachedCriteria flow = DetachedCriteria.forClass(LrtbRewardAdjReqFlow.class);
		DetachedCriteria req = flow.createCriteria("lrtbRewardAdjReq", org.hibernate.sql.JoinType.LEFT_OUTER_JOIN);
		DetachedCriteria childFlow = flow.createCriteria("lrtbRewardAdjReqFlows", "childFlow",org.hibernate.sql.JoinType.LEFT_OUTER_JOIN);
		DetachedCriteria user = flow.createCriteria("satbUser", org.hibernate.sql.JoinType.LEFT_OUTER_JOIN);
		DetachedCriteria acct = req.createCriteria("amtbAccount", org.hibernate.sql.JoinType.LEFT_OUTER_JOIN);
		req.createCriteria("mstbMasterTable", org.hibernate.sql.JoinType.LEFT_OUTER_JOIN);
		
		//search by account
		if(form.account!=null) {
			acct.add(Restrictions.idEq(form.account.getAccountNo()));
		} else{
			//search by acct no or acct name if no account is selected
			if(form.customerNo!=null && form.customerNo.length()>0){
				acct.add(Restrictions.eq("custNo", form.customerNo));
			}
			if(form.accountName!=null && form.accountName.length()>=3) {
				acct.add(Restrictions.ilike("accountName", form.accountName, MatchMode.ANYWHERE));
			}
		}
		if(form.requestDateFrom!=null){
			req.add(Restrictions.between("createdDt", form.requestDateFrom, form.requestDateTo));
		}
//		if(form.requester!=null && form.requester.length()>0){
//			user.add(Restrictions.ilike("name", form.requester, MatchMode.ANYWHERE));
//		}
		if(form.requestStatus!=null && form.requestStatus.length()>0){
			if(form.requestStatus.equals(NonConfigurableConstants.REWARDS_ADJ_REQUEST_STATUS_PENDING)){
				flow.add(Restrictions.eq("toStatus", form.requestStatus));
				childFlow.add(Restrictions.isNull("adjReqFlowNo"));
			}
			else{
				flow.add(Restrictions.eq("toStatus", form.requestStatus));
				flow.add(Restrictions.isNotNull("lrtbRewardAdjReqFlow"));
			}
		}
		else{
			Conjunction conj1 = Restrictions.conjunction();
			conj1.add(Restrictions.eq("toStatus", NonConfigurableConstants.REWARDS_ADJ_REQUEST_STATUS_PENDING));
			conj1.add(Restrictions.isNull("childFlow.adjReqFlowNo"));
			Conjunction conj2 = Restrictions.conjunction();
			conj2.add(Restrictions.eq("toStatus", NonConfigurableConstants.REWARDS_ADJ_REQUEST_STATUS_REJECTED));
			conj2.add(Restrictions.isNotNull("lrtbRewardAdjReqFlow"));
			Conjunction conj3 = Restrictions.conjunction();
			conj3.add(Restrictions.eq("toStatus", NonConfigurableConstants.REWARDS_ADJ_REQUEST_STATUS_APPROVED));
			conj3.add(Restrictions.isNotNull("lrtbRewardAdjReqFlow"));
			Disjunction disj = Restrictions.disjunction();
			disj.add(conj1);
			disj.add(conj2);
			disj.add(conj3);
			flow.add(disj);
		}
		
		return this.findDefaultMaxResultByCriteria(flow);
	}
}
