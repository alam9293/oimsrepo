package com.cdgtaxi.ibs.common.dao;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.hibernate.FetchMode;
import org.hibernate.Query;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;

import com.cdgtaxi.ibs.common.ConfigurableConstants;
import com.cdgtaxi.ibs.common.NonConfigurableConstants;
import com.cdgtaxi.ibs.common.model.ImtbIssue;
import com.cdgtaxi.ibs.common.model.ImtbIssueReq;
import com.cdgtaxi.ibs.common.model.ImtbItem;
import com.cdgtaxi.ibs.common.model.ImtbItemReq;
import com.cdgtaxi.ibs.common.model.ImtbItemType;
import com.cdgtaxi.ibs.common.model.ImtbStock;
import com.cdgtaxi.ibs.common.model.forms.SearchIssuanceRequestForm;
import com.cdgtaxi.ibs.common.model.forms.SearchItemForm;
import com.cdgtaxi.ibs.common.model.forms.SearchItemRequestForm;
import com.cdgtaxi.ibs.inventory.ItemTypeDto;
import com.cdgtaxi.ibs.util.DateUtil;

@SuppressWarnings("unchecked")
public class InventoryDaoHibernate extends GenericDaoHibernate implements InventoryDao {

	public List<ImtbItemType> getItemTypes(){
		DetachedCriteria criteria = DetachedCriteria.forClass(ImtbItemType.class);
		criteria.addOrder(Order.asc("typeName"));
		return this.findAllByCriteria(criteria);
	}
	
	public boolean itemTypeHasStock(Integer itemTypeNo) {
		DetachedCriteria stockCriteria = DetachedCriteria.forClass(ImtbStock.class);
		stockCriteria.createAlias("imtbItemType", "it");
		stockCriteria.add(Restrictions.eq("it.itemTypeNo", itemTypeNo));
		stockCriteria.setProjection(Projections.rowCount());

		Integer count = (Integer) findAllByCriteria(stockCriteria).get(0);

		return count > 0;
	}

	public List<ItemTypeDto> getItemTypeDtos() {
		return getItemTypeDtoQuery(-1).list();
	}

	public ImtbItemType getItemType(Integer itemTypeNo) {
		DetachedCriteria itemTypeCriteria = DetachedCriteria.forClass(ImtbItemType.class);
		//itemTypeCriteria.setFetchMode("imtbStocks", FetchMode.JOIN);
		itemTypeCriteria.add(Restrictions.idEq(itemTypeNo));

		//DetachedCriteria issueReqCriteria = itemTypeCriteria.createCriteria("imtbIssueReqs", org.hibernate.sql.JoinType.LEFT_OUTER_JOIN);
		//issueReqCriteria.setFetchMode("amtbAccount", FetchMode.JOIN);
		//issueReqCriteria.setFetchMode("imtbIssueReqFlow", FetchMode.JOIN);

		List list = findAllByCriteria(itemTypeCriteria);
		if (list.size() > 0) {
			return (ImtbItemType) list.get(0);
		} else {
			return null;
		}
	}
	
	public List<ImtbStock> getStocksByItemType(Integer itemTypeNo){
		DetachedCriteria stockCrit = DetachedCriteria.forClass(ImtbStock.class);
		DetachedCriteria itemTypeCrit = stockCrit.createCriteria("imtbItemType", org.hibernate.sql.JoinType.LEFT_OUTER_JOIN);
		itemTypeCrit.add(Restrictions.idEq(itemTypeNo));
		
		stockCrit.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		stockCrit.addOrder(Order.desc("txnDt"));
		stockCrit.addOrder(Order.asc("serialNoStart"));
		
		return findDefaultMaxResultByCriteria(stockCrit); 
	}

	public ItemTypeDto getItemTypeDto(Integer itemTypeNo) {
		List list = getItemTypeDtoQuery(itemTypeNo).list();
		if (list.size() > 0) {
			return (ItemTypeDto) list.get(0);
		} else {
			return null;
		}
	}

	private Query getItemTypeDtoQuery(Integer itemTypeNo) {
		Query query = currentSession().getNamedQuery("getInventoryItemTypeDto");
		query.setString("stockInCode", NonConfigurableConstants.STOCK_TXN_TYPE_IN);
		query.setString("issuedCode", NonConfigurableConstants.STOCK_TXN_TYPE_ISSUED);
		query.setString("redeemedCode", NonConfigurableConstants.INVENTORY_ITEM_STATUS_REDEEMED);
		query.setString("pendingCode", NonConfigurableConstants.INVENTORY_REQUEST_STATUS_PENDING);
		query.setString("approvedCode", NonConfigurableConstants.INVENTORY_REQUEST_STATUS_APPROVED);
		query.setInteger("itemTypeNo", itemTypeNo);

		query.setResultTransformer(Transformers.aliasToBean(ItemTypeDto.class));
		
		return query;
	}

	public BigDecimal getNextStockSerialNo(Integer itemTypeNo) {
		DetachedCriteria stockCriteria = DetachedCriteria.forClass(ImtbStock.class);
		stockCriteria.createAlias("imtbItemType", "it");
		stockCriteria.add(Restrictions.eq("txnType", NonConfigurableConstants.STOCK_TXN_TYPE_IN));
		stockCriteria.add(Restrictions.eq("it.itemTypeNo", itemTypeNo));
		stockCriteria.setProjection(Projections.max("serialNoEnd"));

		BigDecimal lastSerialNo = (BigDecimal) findAllByCriteria(stockCriteria).get(0);
		if (lastSerialNo == null) {
			lastSerialNo = BigDecimal.ZERO;
		}

		return lastSerialNo.add(BigDecimal.valueOf(1));
	}

	public boolean serialNumberExists(Integer itemTypeNo, BigDecimal serialNoStart,
			BigDecimal serialNoEnd) {
		DetachedCriteria stockCriteria = DetachedCriteria.forClass(ImtbStock.class);
		stockCriteria.createAlias("imtbItemType", "it");
		stockCriteria.add(Restrictions.eq("txnType", NonConfigurableConstants.STOCK_TXN_TYPE_IN));
		//stockCriteria.add(Restrictions.eq("it.itemTypeNo", itemTypeNo));

		Disjunction overlap = Restrictions.disjunction();
		overlap.add(Restrictions.between("serialNoStart", serialNoStart, serialNoEnd));
		overlap.add(Restrictions.between("serialNoEnd", serialNoStart, serialNoEnd));
		stockCriteria.add(overlap);

		stockCriteria.setProjection(Projections.rowCount());

		Integer count = (Integer) findAllByCriteria(stockCriteria).get(0);

		return count > 0;
	}

	public boolean hasSufficientStock(Integer itemTypeNo, Integer requestedQty) {
		List list = getItemTypeDtoQuery(itemTypeNo).list();
		if (list.size() > 0) {
			ItemTypeDto itemTypeDto = (ItemTypeDto) list.get(0);
			Integer availableQty = itemTypeDto.getStockQty() - itemTypeDto.getReservedQty();
			return requestedQty <= availableQty;
		}
		return false;
	}

	public ImtbIssueReq getIssuanceRequest(Integer requestId) {
		DetachedCriteria requestCriteria = DetachedCriteria.forClass(ImtbIssueReq.class);
		requestCriteria.setFetchMode("imtbItemType", FetchMode.JOIN);
		requestCriteria.setFetchMode("imtbIssueReqFlow", FetchMode.JOIN);
		requestCriteria.setFetchMode("amtbContactPerson", FetchMode.JOIN);
		requestCriteria.add(Restrictions.idEq(requestId));

		// Fetch all 3 levels of account
		DetachedCriteria accountCriteria =
			requestCriteria.createCriteria("amtbAccount", org.hibernate.sql.JoinType.LEFT_OUTER_JOIN);
		accountCriteria.createCriteria("amtbAccount", org.hibernate.sql.JoinType.LEFT_OUTER_JOIN)
		.createCriteria("amtbAccount", org.hibernate.sql.JoinType.LEFT_OUTER_JOIN);

		// Fetch main billing contact
		DetachedCriteria contactCriteria =
			accountCriteria.createCriteria("amtbAcctMainContacts", org.hibernate.sql.JoinType.LEFT_OUTER_JOIN);
		contactCriteria.setFetchMode("amtbContactPerson", FetchMode.JOIN);
		contactCriteria.add(Restrictions.eq("id.mainContactType",
				NonConfigurableConstants.MAIN_CONTACT_TYPE_BILLING));

		// Fetch issuance
		DetachedCriteria issueCriteria =
			requestCriteria.createCriteria("imtbIssue", org.hibernate.sql.JoinType.LEFT_OUTER_JOIN);
		issueCriteria.setFetchMode("imtbStock", FetchMode.JOIN);

		List list = findAllByCriteria(requestCriteria);
		if (list.size() > 0) {
			return (ImtbIssueReq) list.get(0);
		} else {
			return null;
		}
	}

	public BigDecimal getNextAvailableStockSerialNo(Integer itemTypeNo) {
		DetachedCriteria stockCriteria = DetachedCriteria.forClass(ImtbStock.class);
		stockCriteria.createAlias("imtbItemType", "it");
		stockCriteria.add(Restrictions.eq("txnType", NonConfigurableConstants.STOCK_TXN_TYPE_ISSUED));
		stockCriteria.add(Restrictions.eq("it.itemTypeNo", itemTypeNo));
		stockCriteria.setProjection(Projections.max("serialNoEnd"));

		BigDecimal lastSerialNo = (BigDecimal) findAllByCriteria(stockCriteria).get(0);
		if(lastSerialNo==null) lastSerialNo = BigDecimal.ZERO;
		
		return lastSerialNo.add(BigDecimal.valueOf(1));
	}

	public boolean serialNumberAvailable(Integer itemTypeNo,
			BigDecimal serialNoStart, BigDecimal serialNoEnd) {
		Query query = currentSession().getNamedQuery("serialNoAvailable");
		query.setString("stockInCode", NonConfigurableConstants.STOCK_TXN_TYPE_IN);
		query.setString("issuedCode", NonConfigurableConstants.STOCK_TXN_TYPE_ISSUED);
		query.setInteger("itemTypeNo", itemTypeNo);
		query.setBigDecimal("serialNoStart", serialNoStart);
		query.setBigDecimal("serialNoEnd", serialNoEnd);

		BigDecimal count = (BigDecimal) query.list().get(0);

		return count.signum() > 0;
	}

	public ImtbIssue getIssuanceByStockNo(Integer stockNo) {
		DetachedCriteria issueCriteria = DetachedCriteria.forClass(ImtbIssue.class);
		issueCriteria.setFetchMode("imtbItems", FetchMode.JOIN);

		// Fetch stock
		DetachedCriteria stockCriteria =
			issueCriteria.createCriteria("imtbStock", org.hibernate.sql.JoinType.LEFT_OUTER_JOIN);
		stockCriteria.add(Restrictions.idEq(stockNo));

		// Fetch request
		DetachedCriteria requestCriteria =
			issueCriteria.createCriteria("imtbIssueReq", org.hibernate.sql.JoinType.LEFT_OUTER_JOIN);
		requestCriteria.setFetchMode("imtbItemType", FetchMode.JOIN);
		requestCriteria.setFetchMode("imtbIssueReqFlow", FetchMode.JOIN);

		// Fetch all 3 levels of account
		DetachedCriteria accountCriteria =
			requestCriteria.createCriteria("amtbAccount", org.hibernate.sql.JoinType.LEFT_OUTER_JOIN);
		accountCriteria.createCriteria("amtbAccount", org.hibernate.sql.JoinType.LEFT_OUTER_JOIN)
		.createCriteria("amtbAccount", org.hibernate.sql.JoinType.LEFT_OUTER_JOIN);

		List list = findAllByCriteria(issueCriteria);
		if (list.size() > 0) {
			return (ImtbIssue) list.get(0);
		} else {
			return null;
		}
	}
	
	public List<ImtbItemType> getAllItemTypes(){
		return this.getAll(ImtbItemType.class);
	}
	
	public Set<String> getAllRedeemPoint(){
		
		Set<String> redeemPointSet = new TreeSet<String>(new Comparator<String>() {
		    public int compare(String o1, String o2) {
		        return o1.compareTo(o2);
		    }});
		
		DetachedCriteria imtbItemCriteria = DetachedCriteria.forClass(ImtbItem.class);
		imtbItemCriteria.setProjection(Projections.distinct(Projections.property("redeemPoint")));
		imtbItemCriteria.add(Restrictions.isNotNull("redeemPoint"));

		DetachedCriteria imtbItemReqCriteria = DetachedCriteria.forClass(ImtbItemReq.class);
		imtbItemReqCriteria.setProjection(Projections.distinct(Projections.property("redeemPoint")));
		imtbItemReqCriteria.add(Restrictions.isNotNull("redeemPoint"));
		
		
		List<Object> imtbItemResults = this.findAllByCriteria(imtbItemCriteria);
		if(!imtbItemResults.isEmpty()){
			for(Object result: imtbItemResults){
				redeemPointSet.add((String)result);
			}
		}
		
		List<Object> imtbItemReqResults = this.findAllByCriteria(imtbItemReqCriteria);
		if(!imtbItemReqResults.isEmpty()){
			for(Object result: imtbItemReqResults)
				redeemPointSet.add((String)result);
		}
		
		return redeemPointSet;
		
		
	}

	
	
	
	public List<Object[]> searchIssuanceRequest(SearchIssuanceRequestForm form) {
		
		Query query = currentSession().getNamedQuery("searchIssuanceRequest");
		if(form.maxResult == null) query.setMaxResults(ConfigurableConstants.getMaxQueryResult());
		else query.setMaxResults(form.maxResult);

		if(form.department!=null){
			query.setInteger("accountNo", form.department.getAccountNo());
			query.setString("custNo", null);
			query.setString("accountName", null);
		}
		else if(form.division!=null){
			query.setInteger("accountNo", form.division.getAccountNo());
			query.setString("custNo", null);
			query.setString("accountName", null);
		}
		else if(form.account!=null){
			//query.setInteger("accountNo", form.account.getAccountNo());
			query.setBigInteger("accountNo", null);
			query.setString("custNo", form.account.getCustNo());
			query.setString("accountName", null);
		}
		else{
			query.setBigInteger("accountNo", null);
			query.setString("custNo", form.customerNo);
			query.setString("accountName", form.accountName!=null ? "%"+form.accountName+"%" : null);
		}
		
		query.setString("requestNo", form.requestNo!=null ? form.requestNo.toString() : null);
		query.setString("requestStatus", form.requestStatus);
		query.setString("requestor", form.requestor!=null ? "%"+form.requestor+"%" : null);
		if(form.requestDateFrom!=null){
			query.setTimestamp("requestDateFrom", DateUtil.convertTimestampTo0000Hours(new Timestamp(form.requestDateFrom.getTime())));
			query.setTimestamp("requestDateTo", DateUtil.convertTimestampTo2359Hours(new Timestamp(form.requestDateTo.getTime())));
		}
		else{
			query.setTimestamp("requestDateFrom", null);
			query.setTimestamp("requestDateTo", null);
		}
		
		query.setString("itemTypeNo", form.itemTypeNo!=null ? form.itemTypeNo.toString() : null);
		query.setBigDecimal("serialNoStart", form.serialNoStart!=null ? form.serialNoStart : null);
		query.setBigDecimal("serialNoEnd", form.serialNoEnd!=null ? form.serialNoEnd : null);
		
		return query.list();
	}

	@SuppressWarnings("unused")
	public List<ImtbItem> searchItem(SearchItemForm form){
		DetachedCriteria itemCrit = DetachedCriteria.forClass(ImtbItem.class);
		DetachedCriteria itemTypeCrit = itemCrit.createCriteria("imtbItemType", org.hibernate.sql.JoinType.LEFT_OUTER_JOIN);
		DetachedCriteria issueCrit = itemCrit.createCriteria("imtbIssue", org.hibernate.sql.JoinType.LEFT_OUTER_JOIN);
		DetachedCriteria issueReqCrit = issueCrit.createCriteria("imtbIssueReq", org.hibernate.sql.JoinType.LEFT_OUTER_JOIN);
		DetachedCriteria account3Crit = issueReqCrit.createCriteria("amtbAccount", org.hibernate.sql.JoinType.LEFT_OUTER_JOIN);
		DetachedCriteria account2Crit = account3Crit.createCriteria("amtbAccount", org.hibernate.sql.JoinType.LEFT_OUTER_JOIN);
		DetachedCriteria account1Crit = account2Crit.createCriteria("amtbAccount", org.hibernate.sql.JoinType.LEFT_OUTER_JOIN);
		
		if(form.itemTypeNo != null)
			itemTypeCrit.add(Restrictions.idEq(form.itemTypeNo));
		if(form.itemStatus != null)
			itemCrit.add(Restrictions.eq("status", form.itemStatus));
		if(form.serialNoFrom != null)
			itemCrit.add(Restrictions.between("serialNo", form.serialNoFrom, form.serialNoTo));
		
		itemCrit.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		return this.findDefaultMaxResultByCriteria(itemCrit);
	}
	
	@SuppressWarnings({ "rawtypes", "unused" })
	public ImtbItem getItemByItemNo(Long itemNo){
		DetachedCriteria itemCrit = DetachedCriteria.forClass(ImtbItem.class);
		DetachedCriteria itemTypeCrit = itemCrit.createCriteria("imtbItemType", org.hibernate.sql.JoinType.LEFT_OUTER_JOIN);
		DetachedCriteria issueCrit = itemCrit.createCriteria("imtbIssue", org.hibernate.sql.JoinType.LEFT_OUTER_JOIN);
		DetachedCriteria issueReqCrit = issueCrit.createCriteria("imtbIssueReq", org.hibernate.sql.JoinType.LEFT_OUTER_JOIN);
		DetachedCriteria account3Crit = issueReqCrit.createCriteria("amtbAccount", org.hibernate.sql.JoinType.LEFT_OUTER_JOIN);
		DetachedCriteria account2Crit = account3Crit.createCriteria("amtbAccount", org.hibernate.sql.JoinType.LEFT_OUTER_JOIN);
		DetachedCriteria account1Crit = account2Crit.createCriteria("amtbAccount", org.hibernate.sql.JoinType.LEFT_OUTER_JOIN);
		
		itemCrit.add(Restrictions.idEq(itemNo));
		
		itemCrit.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		List results = this.findAllByCriteria(itemCrit);
		if(results.isEmpty())
			return null;
		else
			return (ImtbItem) results.get(0);
	}
	
	@SuppressWarnings({ "rawtypes", "unused" })
	public ImtbItem getItemBySerialNo(BigDecimal serialNo){
		DetachedCriteria itemCrit = DetachedCriteria.forClass(ImtbItem.class);
		DetachedCriteria itemTypeCrit = itemCrit.createCriteria("imtbItemType", org.hibernate.sql.JoinType.LEFT_OUTER_JOIN);
		DetachedCriteria issueCrit = itemCrit.createCriteria("imtbIssue", org.hibernate.sql.JoinType.LEFT_OUTER_JOIN);
		DetachedCriteria issueReqCrit = issueCrit.createCriteria("imtbIssueReq", org.hibernate.sql.JoinType.LEFT_OUTER_JOIN);
		DetachedCriteria account3Crit = issueReqCrit.createCriteria("amtbAccount", "acct3", org.hibernate.sql.JoinType.LEFT_OUTER_JOIN);
		DetachedCriteria account2Crit = account3Crit.createCriteria("amtbAccount", "acct2", org.hibernate.sql.JoinType.LEFT_OUTER_JOIN);
		DetachedCriteria account1Crit = account2Crit.createCriteria("amtbAccount", "acct1", org.hibernate.sql.JoinType.LEFT_OUTER_JOIN);
		
		itemCrit.add(Restrictions.eq("serialNo", serialNo));
		
		itemCrit.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		List results = this.findAllByCriteria(itemCrit);
		if(results.isEmpty())
			return null;
		else
			return (ImtbItem) results.get(0);
	}
	
	@SuppressWarnings({ "unused" })
	public List<ImtbItem> getItemByItemNos(List<Long> itemNos){
		DetachedCriteria itemCrit = DetachedCriteria.forClass(ImtbItem.class);
		DetachedCriteria itemTypeCrit = itemCrit.createCriteria("imtbItemType", org.hibernate.sql.JoinType.LEFT_OUTER_JOIN);
		DetachedCriteria issueCrit = itemCrit.createCriteria("imtbIssue", org.hibernate.sql.JoinType.LEFT_OUTER_JOIN);
		DetachedCriteria issueReqCrit = issueCrit.createCriteria("imtbIssueReq", org.hibernate.sql.JoinType.LEFT_OUTER_JOIN);
		DetachedCriteria account3Crit = issueReqCrit.createCriteria("amtbAccount", "acct3", org.hibernate.sql.JoinType.LEFT_OUTER_JOIN);
		DetachedCriteria account2Crit = account3Crit.createCriteria("amtbAccount", "acct2", org.hibernate.sql.JoinType.LEFT_OUTER_JOIN);
		DetachedCriteria account1Crit = account2Crit.createCriteria("amtbAccount", "acct1", org.hibernate.sql.JoinType.LEFT_OUTER_JOIN);
		
		itemCrit.add(Restrictions.in("itemNo", itemNos));
		itemCrit.addOrder(Order.asc("serialNo"));
		
		itemCrit.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		return this.findAllByCriteria(itemCrit);
	}
	
	@SuppressWarnings("unused")
	public List<ImtbItemReq> searchItemRequest(SearchItemRequestForm form){
		DetachedCriteria itemReqCrit = DetachedCriteria.forClass(ImtbItemReq.class);
		DetachedCriteria itemReqFlowCrit = itemReqCrit.createCriteria("imtbItemReqFlows", org.hibernate.sql.JoinType.LEFT_OUTER_JOIN);
		DetachedCriteria itemCrit = itemReqCrit.createCriteria("imtbItem", org.hibernate.sql.JoinType.LEFT_OUTER_JOIN);
		DetachedCriteria itemTypeCrit = itemCrit.createCriteria("imtbItemType", org.hibernate.sql.JoinType.LEFT_OUTER_JOIN);
		DetachedCriteria issueCrit = itemCrit.createCriteria("imtbIssue", org.hibernate.sql.JoinType.LEFT_OUTER_JOIN);
		DetachedCriteria issueReqCrit = issueCrit.createCriteria("imtbIssueReq", org.hibernate.sql.JoinType.LEFT_OUTER_JOIN);
		DetachedCriteria account3Crit = issueReqCrit.createCriteria("amtbAccount", "acct3", org.hibernate.sql.JoinType.LEFT_OUTER_JOIN);
		DetachedCriteria account2Crit = account3Crit.createCriteria("amtbAccount", "acct2", org.hibernate.sql.JoinType.LEFT_OUTER_JOIN);
		DetachedCriteria account1Crit = account2Crit.createCriteria("amtbAccount", "acct1", org.hibernate.sql.JoinType.LEFT_OUTER_JOIN);
		DetachedCriteria reasonCrit = itemReqCrit.createCriteria("mstbMasterTableByReason", org.hibernate.sql.JoinType.LEFT_OUTER_JOIN);
		
		itemReqCrit.add(Restrictions.eq("currentStatus", NonConfigurableConstants.INVENTORY_REQUEST_STATUS_PENDING));
		
		if(form.itemTypeNo != null)
			itemTypeCrit.add(Restrictions.idEq(form.itemTypeNo));
		if(form.itemStatus != null)
			itemCrit.add(Restrictions.eq("status", form.itemStatus));
		if(form.serialNoFrom != null)
			itemCrit.add(Restrictions.between("serialNo", form.serialNoFrom, form.serialNoTo));
		if(form.custNo != null && form.custNo.length() > 0){
			Disjunction disjunction = Restrictions.disjunction();
			disjunction.add((Restrictions.eq("acct3.custNo", form.custNo)));
			disjunction.add((Restrictions.eq("acct2.custNo", form.custNo)));
			disjunction.add((Restrictions.eq("acct1.custNo", form.custNo)));
			itemReqCrit.add(disjunction);
		}
		if(form.accountName != null && form.accountName.length() > 0){
			Disjunction disjunction = Restrictions.disjunction();
			disjunction.add(Restrictions.and(Restrictions.isNotNull("acct3.custNo"), Restrictions.ilike("acct3.accountName", form.accountName, MatchMode.ANYWHERE)));
			disjunction.add(Restrictions.and(Restrictions.isNotNull("acct2.custNo"), Restrictions.ilike("acct2.accountName", form.accountName, MatchMode.ANYWHERE)));
			disjunction.add(Restrictions.and(Restrictions.isNotNull("acct1.custNo"), Restrictions.ilike("acct1.accountName", form.accountName, MatchMode.ANYWHERE)));
			itemReqCrit.add(disjunction);
		}
		
		itemReqCrit.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		return this.findDefaultMaxResultByCriteria(itemReqCrit);
	}
	
	@SuppressWarnings({ "unused", "rawtypes" })
	public ImtbItemReq getItemRequestByReqNo(Integer reqNo){
		DetachedCriteria itemReqCrit = DetachedCriteria.forClass(ImtbItemReq.class);
		DetachedCriteria itemReqFlowCrit = itemReqCrit.createCriteria("imtbItemReqFlows", org.hibernate.sql.JoinType.LEFT_OUTER_JOIN);
		DetachedCriteria itemCrit = itemReqCrit.createCriteria("imtbItem", org.hibernate.sql.JoinType.LEFT_OUTER_JOIN);
		DetachedCriteria itemTypeCrit = itemCrit.createCriteria("imtbItemType", org.hibernate.sql.JoinType.LEFT_OUTER_JOIN);
		DetachedCriteria issueCrit = itemCrit.createCriteria("imtbIssue", org.hibernate.sql.JoinType.LEFT_OUTER_JOIN);
		DetachedCriteria issueReqCrit = issueCrit.createCriteria("imtbIssueReq", org.hibernate.sql.JoinType.LEFT_OUTER_JOIN);
		DetachedCriteria account3Crit = issueReqCrit.createCriteria("amtbAccount", "acct3", org.hibernate.sql.JoinType.LEFT_OUTER_JOIN);
		DetachedCriteria account2Crit = account3Crit.createCriteria("amtbAccount", "acct2", org.hibernate.sql.JoinType.LEFT_OUTER_JOIN);
		DetachedCriteria account1Crit = account2Crit.createCriteria("amtbAccount", "acct1", org.hibernate.sql.JoinType.LEFT_OUTER_JOIN);
		DetachedCriteria reasonCrit = itemReqCrit.createCriteria("mstbMasterTableByReason", org.hibernate.sql.JoinType.LEFT_OUTER_JOIN);
		
		itemReqCrit.add(Restrictions.idEq(reqNo));
		itemReqCrit.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		
		List results = findAllByCriteria(itemReqCrit);
		if(results.isEmpty())
			return null;
		else
			return (ImtbItemReq) results.get(0);
	}
	
	@SuppressWarnings({ "unused" })
	public List<ImtbItemReq> getItemRequestByReqNos(List<Integer> reqNos){
		DetachedCriteria itemReqCrit = DetachedCriteria.forClass(ImtbItemReq.class);
		DetachedCriteria itemReqFlowCrit = itemReqCrit.createCriteria("imtbItemReqFlows", org.hibernate.sql.JoinType.LEFT_OUTER_JOIN);
		DetachedCriteria itemCrit = itemReqCrit.createCriteria("imtbItem", org.hibernate.sql.JoinType.LEFT_OUTER_JOIN);
		DetachedCriteria itemTypeCrit = itemCrit.createCriteria("imtbItemType", org.hibernate.sql.JoinType.LEFT_OUTER_JOIN);
		DetachedCriteria issueCrit = itemCrit.createCriteria("imtbIssue", org.hibernate.sql.JoinType.LEFT_OUTER_JOIN);
		DetachedCriteria issueReqCrit = issueCrit.createCriteria("imtbIssueReq", org.hibernate.sql.JoinType.LEFT_OUTER_JOIN);
		DetachedCriteria account3Crit = issueReqCrit.createCriteria("amtbAccount", "acct3", org.hibernate.sql.JoinType.LEFT_OUTER_JOIN);
		DetachedCriteria account2Crit = account3Crit.createCriteria("amtbAccount", "acct2", org.hibernate.sql.JoinType.LEFT_OUTER_JOIN);
		DetachedCriteria account1Crit = account2Crit.createCriteria("amtbAccount", "acct1", org.hibernate.sql.JoinType.LEFT_OUTER_JOIN);
		DetachedCriteria reasonCrit = itemReqCrit.createCriteria("mstbMasterTableByReason", org.hibernate.sql.JoinType.LEFT_OUTER_JOIN);
		
		itemReqCrit.add(Restrictions.in("reqNo", reqNos));
		itemCrit.addOrder(Order.asc("serialNo"));
		
		itemReqCrit.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		return this.findAllByCriteria(itemReqCrit);
	}
}
