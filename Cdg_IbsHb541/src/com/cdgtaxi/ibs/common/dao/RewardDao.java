package com.cdgtaxi.ibs.common.dao;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.cdgtaxi.ibs.common.model.AmtbAccount;
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

public interface RewardDao extends GenericDao {

	boolean categoryHasItems(Integer rewardPlanNo);

	LrtbRewardMaster getPlanWithDetails(Integer planNo);

	LrtbRewardDetail getDetailWithTiers(Integer planDetailNo);

	LrtbGiftCategory getCategoryWithItems(Integer giftCategoryNo);

	boolean itemWasRedeemed(Integer giftItemNo);

	List<LrtbGiftItem> searchItem(SearchGiftItemForm form);

	AmtbAccount getAccountWithUsablePoints(Integer accountNo);

	LrtbGiftItem getItem(Integer giftItemNo);

	List<LrtbGiftStock> getRedemptionHistory(Integer accountNo);

	Date getLatestToDate(Integer planNo);
	
	public List<LrtbRewardMaster> getAllRewardsPlan();
	
	public LrtbRewardTxn getInitialRewardPointsTxn(LrtbRewardAccount rewardAcct);
	
	public List<LrtbRewardMaster> getOverlappingRewards(LrtbRewardDetail rewardDetail);
	
	public BigDecimal getTotalRedeemedPoints(AmtbAccount account);
	
	public BigDecimal getTotalEarnedPoints(AmtbAccount account);
	
	public List<LrtbRewardAdjReq> getPendingAdjustmentRequests();
	
	public LrtbRewardAccount getCurentYearRewardAccount(Integer accountNo);
	
	public List<LrtbRewardAdjReqFlow> searchAdjustmentRequest(SearchAdjustmentRequestForm form);
}
