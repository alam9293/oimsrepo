package com.cdgtaxi.ibs.reward.business;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Set;

import com.cdgtaxi.ibs.Uri;
import com.cdgtaxi.ibs.acl.model.SatbUser;
import com.cdgtaxi.ibs.common.ConfigurableConstants;
import com.cdgtaxi.ibs.common.NonConfigurableConstants;
import com.cdgtaxi.ibs.common.business.GenericBusinessImpl;
import com.cdgtaxi.ibs.common.exception.InsufficientGiftStockException;
import com.cdgtaxi.ibs.common.exception.InsufficientRewardPointsException;
import com.cdgtaxi.ibs.common.exception.RewardCategoryHasItemsException;
import com.cdgtaxi.ibs.common.exception.RewardItemIsUsedException;
import com.cdgtaxi.ibs.common.exception.RewardPlanInEffectException;
import com.cdgtaxi.ibs.common.exception.RewardPlanOverlappingEffectiveDateException;
import com.cdgtaxi.ibs.common.model.AmtbAccount;
import com.cdgtaxi.ibs.common.model.AmtbAcctStatus;
import com.cdgtaxi.ibs.common.model.AmtbContactPerson;
import com.cdgtaxi.ibs.common.model.LrtbGiftCategory;
import com.cdgtaxi.ibs.common.model.LrtbGiftItem;
import com.cdgtaxi.ibs.common.model.LrtbGiftStock;
import com.cdgtaxi.ibs.common.model.LrtbRewardAccount;
import com.cdgtaxi.ibs.common.model.LrtbRewardAdjReq;
import com.cdgtaxi.ibs.common.model.LrtbRewardAdjReqFlow;
import com.cdgtaxi.ibs.common.model.LrtbRewardTxn;
import com.cdgtaxi.ibs.common.model.forms.SearchAdjustmentRequestForm;
import com.cdgtaxi.ibs.common.model.forms.SearchGiftItemForm;
import com.cdgtaxi.ibs.common.ui.CommonWindow;
import com.cdgtaxi.ibs.master.model.LrtbRewardDetail;
import com.cdgtaxi.ibs.master.model.LrtbRewardMaster;
import com.cdgtaxi.ibs.master.model.LrtbRewardTier;
import com.cdgtaxi.ibs.util.DateUtil;
import com.cdgtaxi.ibs.util.EmailUtil;

public class RewardBusinessImpl extends GenericBusinessImpl implements RewardBusiness {
	public void createPlan(String planName) {
		LrtbRewardMaster rewardMaster = new LrtbRewardMaster();
		rewardMaster.setRewardPlanName(planName);

		daoHelper.getGenericDao().save(rewardMaster);
	}

	public List<LrtbRewardMaster> getPlans() {
		return daoHelper.getRewardDao().getAllRewardsPlan();
	}

	public void deletePlans(List<LrtbRewardMaster> plans) throws RewardPlanInEffectException {
		Date today = new Date();
		for (LrtbRewardMaster plan : plans) {
			Set<LrtbRewardDetail> details = plan.getLrtbRewardDetails();
			for (LrtbRewardDetail detail : details) {
				if (detail.getEffectiveDtFrom().before(today)) {
					throw new RewardPlanInEffectException(plan);
				}
				
				daoHelper.getGenericDao().deleteAll(detail.getLrtbRewardTiers());
			}
			
			daoHelper.getGenericDao().deleteAll(details);
		}

		daoHelper.getGenericDao().deleteAll(plans);
	}

	public LrtbRewardMaster getPlan(Integer planNo) {
		return daoHelper.getRewardDao().getPlanWithDetails(planNo);
	}

	public LrtbRewardDetail getPlanDetail(Integer planDetailNo) {
		return daoHelper.getRewardDao().getDetailWithTiers(planDetailNo);
	}

	public void updatePlanDetail(LrtbRewardDetail detail, Set<LrtbRewardTier> removedTiers, String userLoginId) throws RewardPlanOverlappingEffectiveDateException {
		if(!this.daoHelper.getRewardDao().getOverlappingRewards(detail).isEmpty())
			throw new RewardPlanOverlappingEffectiveDateException();
		
		if(!removedTiers.isEmpty()) this.daoHelper.getGenericDao().deleteAll(removedTiers);
		daoHelper.getRewardDao().update(detail, userLoginId);
	}

	public void savePlanDetail(LrtbRewardDetail detail, String userLoginId) throws RewardPlanOverlappingEffectiveDateException {
		if(!this.daoHelper.getRewardDao().getOverlappingRewards(detail).isEmpty())
			throw new RewardPlanOverlappingEffectiveDateException();

		daoHelper.getRewardDao().save(detail, userLoginId);
	}

	@SuppressWarnings("unchecked")
	public List<LrtbGiftCategory> getCategories() {
		return daoHelper.getGenericDao().getAll(LrtbGiftCategory.class);
	}

	public void deleteCategory(LrtbGiftCategory category) throws RewardCategoryHasItemsException {
		if (daoHelper.getRewardDao().categoryHasItems(category.getGiftCategoryNo())) {
			throw new RewardCategoryHasItemsException(category);
		}

		daoHelper.getGenericDao().delete(category);
	}

	public void createCategory(String categoryName) {
		LrtbGiftCategory category = new LrtbGiftCategory();
		category.setCategoryName(categoryName);

		daoHelper.getGenericDao().save(category);
	}

	public LrtbGiftCategory getCategory(Integer giftCategoryNo) {
		return daoHelper.getRewardDao().getCategoryWithItems(giftCategoryNo);
	}

	public void deleteItem(LrtbGiftItem item) throws RewardItemIsUsedException {
		if (daoHelper.getRewardDao().itemWasRedeemed(item.getGiftItemNo())) {
			throw new RewardItemIsUsedException(item);
		}

		daoHelper.getGenericDao().delete(item);
	}

	public boolean itemWasRedeemed (LrtbGiftItem item){
		return daoHelper.getRewardDao().itemWasRedeemed(item.getGiftItemNo());
	}
	
	public LrtbGiftItem getItem(Integer giftItemNo) {
		return daoHelper.getRewardDao().getItem(giftItemNo);
	}

	public void stockupItem(Integer giftItemNo, Integer stockupQty, String userLoginId) {
		LrtbGiftItem giftItem = getItem(giftItemNo);
		Integer newStock = giftItem.getStock() + stockupQty;
		giftItem.setStock(newStock);

		LrtbGiftStock giftStock = new LrtbGiftStock();
		giftStock.setLrtbGiftItem(giftItem);
		giftStock.setTxnType(NonConfigurableConstants.STOCK_TXN_TYPE_IN);
		giftStock.setTxnDt(DateUtil.getCurrentTimestamp());
		giftStock.setTxnQty(stockupQty);

		daoHelper.getGenericDao().save(giftStock);
		this.daoHelper.getGenericDao().update(giftItem, userLoginId);
	}

	public void moveItems(List<LrtbGiftItem> giftItems,
			LrtbGiftCategory newCategory) {
		for (LrtbGiftItem giftItem : giftItems) {
			giftItem.setLrtbGiftCategory(newCategory);
		}
		daoHelper.getGenericDao().saveOrUpdateAll(giftItems);
	}

	public List<LrtbGiftItem> searchItem(SearchGiftItemForm form) {
		return daoHelper.getRewardDao().searchItem(form);
	}

	public AmtbAccount getAccount(Integer accountNo) {
		return daoHelper.getRewardDao().getAccountWithUsablePoints(accountNo);
	}

	public Integer calcCurrPoints(AmtbAccount account) {
		Integer currentPoints = 0;
		if (account != null) {
			Set<LrtbRewardAccount> issuances = account.getLrtbRewardAccounts();
			if (issuances != null) {
				Date today = DateUtil.getCurrentDate();
				for (LrtbRewardAccount issuance : issuances) {
					// before cutoff and expiry dates
					if (today.before(issuance.getCutOffDt())) {
						for (LrtbRewardTxn txn : issuance.getLrtbRewardTxns()) {
							currentPoints += txn.getRewardsPts();
						}
					}
				}
			}
		}
		return currentPoints;
	}

	public Integer calcPrevPoints(AmtbAccount account) {
		Integer previousPoints = 0;
		if (account != null) {
			Set<LrtbRewardAccount> issuances = account.getLrtbRewardAccounts();
			if (issuances != null) {
				Date today = DateUtil.getCurrentDate();
				for (LrtbRewardAccount issuance : issuances) {
					// past the cutoff, but before expiry date
					if (today.after(issuance.getCutOffDt())) {
						for (LrtbRewardTxn txn : issuance.getLrtbRewardTxns()) {
							previousPoints += txn.getRewardsPts();
						}
					}
				}
			}
		}
		return previousPoints;
	}

	public Integer redeemItem(AmtbAccount account, LrtbGiftItem giftItem,
			Integer redeemQty, String usePointsFrom, AmtbContactPerson contactPerson,
			BigDecimal serialNoStart, BigDecimal serialNoEnd, String userId)
			throws InsufficientRewardPointsException, InsufficientGiftStockException {
		
		Integer origPointsNeeded = redeemQty * giftItem.getPoints();
		Integer pointsNeeded = origPointsNeeded;
		Integer currPoints = calcCurrPoints(account);
		Integer prevPoints = calcPrevPoints(account);
		Set<LrtbRewardAccount> issuances = account.getLrtbRewardAccounts();

		if (redeemQty > giftItem.getStock()) {
			throw new InsufficientGiftStockException(redeemQty, giftItem.getStock());
		}

		if (pointsNeeded > currPoints + prevPoints || issuances == null) {
			throw new InsufficientRewardPointsException(pointsNeeded, currPoints + prevPoints);
		}
		
		Integer ptsBeforeDeduction = currPoints + prevPoints;
		Integer ptsAfterDeduction = ptsBeforeDeduction - pointsNeeded;
		
		// Update item stock
		LrtbGiftStock stock = new LrtbGiftStock();
		stock.setLrtbGiftItem(giftItem);
		stock.setTxnType(NonConfigurableConstants.STOCK_TXN_TYPE_REDEEMED);
		stock.setTxnQty(-redeemQty);
		stock.setAmtbAccount(account);
		stock.setTxnDt(DateUtil.getCurrentTimestamp());
		stock.setSerialNoStart(serialNoStart);
		stock.setSerialNoEnd(serialNoEnd);
		stock.setPreviousPts(ptsBeforeDeduction);
		stock.setBalancePts(ptsAfterDeduction);
		daoHelper.getGenericDao().save(stock, userId);

		// Deducts points from previous, then current if insufficient
		if (usePointsFrom.equals(NonConfigurableConstants.REWARDS_REDEEM_FROM_PREV)) {
			pointsNeeded = deductPrevPoints(pointsNeeded, issuances, stock, contactPerson, null, userId);
			if (pointsNeeded > 0) {
				deductCurrPoints(pointsNeeded, issuances, stock, contactPerson, null, userId);
			}
		} else { // Deducts points from current, then previous if insufficient
			pointsNeeded = deductCurrPoints(pointsNeeded, issuances, stock, contactPerson, null, userId);
			if (pointsNeeded > 0) {
				deductPrevPoints(pointsNeeded, issuances, stock, contactPerson, null, userId);
			}
		}

		Integer stockRemaining = giftItem.getStock() - redeemQty;
		giftItem.setStock(stockRemaining);
		daoHelper.getGenericDao().update(account);
		daoHelper.getGenericDao().update(giftItem);

		return origPointsNeeded;
	}

	private Integer deductPrevPoints(Integer pointsNeeded, Set<LrtbRewardAccount> issuances, 
			LrtbGiftStock stock, AmtbContactPerson contactPerson, LrtbRewardAdjReq request, String userId) {
		Date today = DateUtil.getCurrentDate();
		for (LrtbRewardAccount issuance : issuances) {
			if (today.after(issuance.getCutOffDt())) { // past the cutoff, but before expiry date
				pointsNeeded = deductPoints(pointsNeeded, issuance, stock, contactPerson, request, userId);
				if (pointsNeeded == 0) {
					break;
				}
			}
		}
		return pointsNeeded;
	}

	private Integer deductCurrPoints(Integer pointsNeeded, Set<LrtbRewardAccount> issuances, 
			LrtbGiftStock stock, AmtbContactPerson contactPerson, LrtbRewardAdjReq request, String userId) {
		Date today = DateUtil.getCurrentDate();
		for (LrtbRewardAccount issuance : issuances) {
			if (today.before(issuance.getCutOffDt())) { // before cutoff and expiry dates
				pointsNeeded = deductPoints(pointsNeeded, issuance, stock, contactPerson, request, userId);
				if (pointsNeeded == 0) {
					break;
				}
			}
		}
		return pointsNeeded;
	}

	private Integer deductPoints(Integer pointsNeeded, LrtbRewardAccount issuance,
			LrtbGiftStock stock, AmtbContactPerson contactPerson, LrtbRewardAdjReq request, String userId) {
		Integer issuancePoints = 0;
		for (LrtbRewardTxn txn : issuance.getLrtbRewardTxns()) {
			issuancePoints += txn.getRewardsPts();
		}
		
		LrtbRewardTxn txn = new LrtbRewardTxn();
		txn.setLrtbRewardAccount(issuance);
		txn.setLrtbGiftStock(stock);
		txn.setAmtbContactPerson(contactPerson);
		txn.setLrtbRewardAdjReq(request);
		
		if (issuancePoints > pointsNeeded) {
			txn.setRewardsPts(-pointsNeeded);
			pointsNeeded = 0;
		} else {
			txn.setRewardsPts(-issuancePoints);
			pointsNeeded -= issuancePoints;
		}
		
		txn.setBilledFlag(NonConfigurableConstants.REWARDS_TXN_BILLED_FLAG_YES);
		
		daoHelper.getRewardDao().save(txn, userId);
		return pointsNeeded;
	}

	public List<LrtbGiftStock> getRedemptionHistory(Integer accountNo) {
		return daoHelper.getRewardDao().getRedemptionHistory(accountNo);
	}
	
	public AmtbAcctStatus getAccountLatestStatus(String acctNo){
		return this.daoHelper.getAccountDao().getAccountLatestStatus(acctNo);
	}
	
	public BigDecimal getTotalRedeemedPoints(AmtbAccount account){
		return this.daoHelper.getRewardDao().getTotalRedeemedPoints(account);
	}
	
	public BigDecimal getTotalEarnedPoints(AmtbAccount account){
		return this.daoHelper.getRewardDao().getTotalEarnedPoints(account);
	}
	
	public Serializable createAdjustmentRequest(AmtbAccount account, Integer pts, String requestType,
			String adjustPtsFrom, String reason, String remarks, String requestorUserId) 
			throws InsufficientRewardPointsException{
		
		//DEDUCT
		if(requestType.equals(NonConfigurableConstants.REWARDS_ADJ_REQUEST_TYPE_DEDUCT)){
			account = this.daoHelper.getRewardDao().getAccountWithUsablePoints(account.getAccountNo());
			Integer currPoints = calcCurrPoints(account);
			Integer prevPoints = calcPrevPoints(account);
			
			if (pts > currPoints + prevPoints || account == null || account.getLrtbRewardAccounts() == null) {
				throw new InsufficientRewardPointsException(pts, currPoints + prevPoints);
			}
		}
		
		LrtbRewardAdjReq newRequest = new LrtbRewardAdjReq();
		newRequest.setPoints(pts);
		newRequest.setRemarks(remarks);
		newRequest.setRequestType(requestType);
		newRequest.setAdjustPointsFrom(adjustPtsFrom);
		newRequest.setMstbMasterTable(ConfigurableConstants.getMasterTable(ConfigurableConstants.REWARDS_ADJUSTMENT_REASON, reason));
		newRequest.setAmtbAccount(account);
		
		Serializable id = this.save(newRequest, requestorUserId);
		SatbUser requestor = this.daoHelper.getUserDao().getUserWithRoles(CommonWindow.getUserId());
		
		LrtbRewardAdjReqFlow newFlow = new LrtbRewardAdjReqFlow();
		newFlow.setFromStatus(NonConfigurableConstants.REWARDS_ADJ_REQUEST_STATUS_NEW);
		newFlow.setToStatus(NonConfigurableConstants.REWARDS_ADJ_REQUEST_STATUS_PENDING);
		newFlow.setRemarks(remarks);
		newFlow.setFlowDt(DateUtil.getCurrentTimestamp());
		newFlow.setLrtbRewardAdjReq(newRequest);
		newFlow.setSatbUser(requestor);
		this.save(newFlow);
		
		//EMAIL
		List<SatbUser> toList = this.daoHelper.getUserDao().searchUser(Uri.APPROVE_REWARDS_ADJUSTMENT_REQUEST);
		List<SatbUser> ccList = new ArrayList<SatbUser>();
//		ccList.add(requestor);
		if(!toList.isEmpty())
			this.sendEmailNotification(newRequest, ConfigurableConstants.EMAIL_TYPE_REWARDS_ADJ_REQUEST_SUBMIT, requestor.getUsername(), toList, ccList);
		
		return id;
	}
	
	private void sendEmailNotification(LrtbRewardAdjReq request, String emailType, String submitter, List<SatbUser> toList, List<SatbUser> ccList) {
		List<String> toEmails = new ArrayList<String>();
		StringBuffer recipientNames = new StringBuffer();
		for(SatbUser recipient : toList){
			toEmails.add(recipient.getEmail());
			recipientNames.append(recipient.getName() + ",");
		}
		recipientNames.delete(recipientNames.length()-1, recipientNames.length());

		List<String> ccEmails = new ArrayList<String>();
		for(SatbUser recipient : ccList){
			ccEmails.add(recipient.getEmail());
		}
		
		EmailUtil.sendEmail(
				toEmails.toArray(new String[]{}),
				ccEmails.toArray(new String[]{}),
				ConfigurableConstants.getEmailText(emailType, ConfigurableConstants.EMAIL_SUBJECT),
				ConfigurableConstants.getEmailText(emailType, ConfigurableConstants.EMAIL_CONTENT)
				.replaceAll("#custNo#", request.getAmtbAccount().getCustNo())
				.replaceAll("#acctName#", request.getAmtbAccount().getAccountName())
				.replaceAll("#userName#", recipientNames.toString())
				.replaceAll("#requestNo#", request.getAdjReqNo().toString())
				.replaceAll("#submiter#", submitter)
			);
	}
	
	public void rejectAdjustmentRequest(LrtbRewardAdjReq request, String approverRemarks, String approverUserId){
		this.update(request, approverUserId);
		
		String[] requestorIdAndDomain = request.getCreatedBy().split("\\\\");
		String requestorId = requestorIdAndDomain[1];
		String requestorDomain = requestorIdAndDomain[0];
		SatbUser requestor = this.daoHelper.getUserDao().getUser(requestorId, requestorDomain);
		SatbUser approver = this.daoHelper.getUserDao().getUserWithRoles(CommonWindow.getUserId());
		
		LrtbRewardAdjReqFlow newFlow = new LrtbRewardAdjReqFlow();
		newFlow.setFromStatus(NonConfigurableConstants.REWARDS_ADJ_REQUEST_STATUS_PENDING);
		newFlow.setToStatus(NonConfigurableConstants.REWARDS_ADJ_REQUEST_STATUS_REJECTED);
		newFlow.setRemarks(approverRemarks);
		newFlow.setFlowDt(DateUtil.getCurrentTimestamp());
		newFlow.setLrtbRewardAdjReq(request);
		newFlow.setLrtbRewardAdjReqFlow(request.getLrtbRewardAdjReqFlows().iterator().next());
		newFlow.setSatbUser(approver);
		this.save(newFlow);
		
		//EMAIL
		List<SatbUser> toList = new ArrayList<SatbUser>();
		List<SatbUser> ccList = new ArrayList<SatbUser>();//this.daoHelper.getUserDao().searchUser(Uri.APPROVE_REWARDS_ADJUSTMENT_REQUEST);
		toList.add(requestor);
		this.sendEmailNotification(request, ConfigurableConstants.EMAIL_TYPE_REWARDS_ADJ_REQUEST_REJECTED, requestor.getUsername(), toList, ccList);
	}
	
	public void approveAdjustmentRequest(LrtbRewardAdjReq request, String approverRemarks, String approverUserId) 
		throws InsufficientRewardPointsException{
		//DEDUCT
		AmtbAccount account = request.getAmtbAccount() ;
		if(request.getRequestType().equals(NonConfigurableConstants.REWARDS_ADJ_REQUEST_TYPE_DEDUCT)){
			account = this.daoHelper.getRewardDao().getAccountWithUsablePoints(request.getAmtbAccount().getAccountNo());
			Integer currPoints = calcCurrPoints(account);
			Integer prevPoints = calcPrevPoints(account);
			
			if (request.getPoints() > currPoints + prevPoints || account == null || account.getLrtbRewardAccounts() == null) {
				throw new InsufficientRewardPointsException(request.getPoints(), currPoints + prevPoints);
			}
		}
		
		this.update(request, approverUserId);
		
		String[] requestorIdAndDomain = request.getCreatedBy().split("\\\\");
		String requestorId = requestorIdAndDomain[1];
		String requestorDomain = requestorIdAndDomain[0];
		SatbUser requestor = this.daoHelper.getUserDao().getUser(requestorId, requestorDomain);
		SatbUser approver = this.daoHelper.getUserDao().getUserWithRoles(CommonWindow.getUserId());
		
		//Create Flow
		LrtbRewardAdjReqFlow newFlow = new LrtbRewardAdjReqFlow();
		newFlow.setFromStatus(NonConfigurableConstants.REWARDS_ADJ_REQUEST_STATUS_PENDING);
		newFlow.setToStatus(NonConfigurableConstants.REWARDS_ADJ_REQUEST_STATUS_APPROVED);
		newFlow.setRemarks(approverRemarks);
		newFlow.setFlowDt(DateUtil.getCurrentTimestamp());
		newFlow.setLrtbRewardAdjReq(request);
		newFlow.setLrtbRewardAdjReqFlow(request.getLrtbRewardAdjReqFlows().iterator().next());
		newFlow.setSatbUser(approver);
		this.save(newFlow);
		
		if(request.getRequestType().equals(NonConfigurableConstants.REWARDS_ADJ_REQUEST_TYPE_DEDUCT)){
			String adjustPointsFrom = request.getAdjustPointsFrom();
			Integer pointsNeeded = request.getPoints();
			// Deducts points from previous, then current if insufficient
			if (adjustPointsFrom.equals(NonConfigurableConstants.REWARDS_REDEEM_FROM_PREV)) {
				pointsNeeded = deductPrevPoints(pointsNeeded, account.getLrtbRewardAccounts(), null, null, request, approverUserId);
				if (pointsNeeded > 0) {
					deductCurrPoints(pointsNeeded, account.getLrtbRewardAccounts(), null, null, request, approverUserId);
				}
			} else { // Deducts points from current, then previous if insufficient
				pointsNeeded = deductCurrPoints(pointsNeeded, account.getLrtbRewardAccounts(), null, null, request, approverUserId);
				if (pointsNeeded > 0) {
					deductPrevPoints(pointsNeeded, account.getLrtbRewardAccounts(), null, null, request, approverUserId);
				}
			}
		}
		else{
			//FIND CURRENT YEAR REWARDS ACCOUNT, DON HAVE THEN CREATE ONE FOR CURRENT YEAR 
			LrtbRewardAccount rewardAccount = this.daoHelper.getRewardDao().getCurentYearRewardAccount(account.getAccountNo());
			if(rewardAccount==null){
				rewardAccount = new LrtbRewardAccount();
				
				//Find last day of current year for cutoff dt
				Calendar cutOffCalendar = Calendar.getInstance();
				cutOffCalendar.set(Calendar.MONTH, Calendar.DECEMBER);
				cutOffCalendar.set(Calendar.DAY_OF_MONTH, 31);
				cutOffCalendar.set(Calendar.AM_PM, Calendar.PM);
				cutOffCalendar.set(Calendar.HOUR, 11);
				cutOffCalendar.set(Calendar.MINUTE, 59);
				cutOffCalendar.set(Calendar.SECOND, 59);
				cutOffCalendar.set(Calendar.MILLISECOND, 999);
				rewardAccount.setCutOffDt(new Timestamp(cutOffCalendar.getTimeInMillis()));
				
				//Keep the grace period for audit purpose
				Integer gracePeriod = new Integer(ConfigurableConstants.getMasterTable(ConfigurableConstants.REWARDS_GRACE_PERIOD, NonConfigurableConstants.REWARDS_GRACE_PERIOD_MASTER_CODE).getMasterValue());
				rewardAccount.setExtMthToExpiry(gracePeriod);
			
				//Find expired dt
				cutOffCalendar.add(Calendar.MONTH, gracePeriod);
				Timestamp expireDate = new Timestamp(cutOffCalendar.getTimeInMillis());
				rewardAccount.setExpireDt(expireDate);
				
				//Find ibs expired dt
				Integer ibsGracePeriod = new Integer(ConfigurableConstants.getMasterTable(ConfigurableConstants.REWARDS_GRACE_PERIOD, NonConfigurableConstants.REWARDS_IBS_GRACE_PERIOD_MASTER_CODE).getMasterValue());
				cutOffCalendar.add(Calendar.MONTH, ibsGracePeriod);
				Timestamp ibsExpireDate = new Timestamp(cutOffCalendar.getTimeInMillis());
				rewardAccount.setIbsExpireDt(ibsExpireDate);
				
				rewardAccount.setAmtbAccount(account);
				rewardAccount.setExpiredBilledFlag(NonConfigurableConstants.BOOLEAN_NO);
				this.save(rewardAccount);
			}
			
			//Create Rewards Txn
			LrtbRewardTxn rewardTxn = new LrtbRewardTxn();
			rewardTxn.setLrtbRewardAdjReq(request);
			rewardTxn.setLrtbRewardAccount(rewardAccount);
			rewardTxn.setRewardsPts(request.getPoints());
			rewardTxn.setBilledFlag(NonConfigurableConstants.REWARDS_TXN_BILLED_FLAG_YES);
			this.save(rewardTxn, approverUserId);
		}
		
		//EMAIL
		List<SatbUser> toList = new ArrayList<SatbUser>();
		List<SatbUser> ccList = new ArrayList<SatbUser>();//this.daoHelper.getUserDao().searchUser(Uri.APPROVE_REWARDS_ADJUSTMENT_REQUEST);
		toList.add(requestor);
		this.sendEmailNotification(request, ConfigurableConstants.EMAIL_TYPE_REWARDS_ADJ_REQUEST_APPROVED, requestor.getUsername(), toList, ccList);
	}
	
	public List<LrtbRewardAdjReq> getPendingAdjustmentRequests(){
		return this.daoHelper.getRewardDao().getPendingAdjustmentRequests();
		
	}
	
	public List<LrtbRewardAdjReqFlow> searchAdjustmentRequest(SearchAdjustmentRequestForm form){
		return this.daoHelper.getRewardDao().searchAdjustmentRequest(form);
	}
}
