package com.cdgtaxi.ibs.reward.business;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

import com.cdgtaxi.ibs.common.business.GenericBusiness;
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
import com.cdgtaxi.ibs.common.model.LrtbRewardAdjReq;
import com.cdgtaxi.ibs.common.model.LrtbRewardAdjReqFlow;
import com.cdgtaxi.ibs.common.model.forms.SearchAdjustmentRequestForm;
import com.cdgtaxi.ibs.common.model.forms.SearchGiftItemForm;
import com.cdgtaxi.ibs.master.model.LrtbRewardDetail;
import com.cdgtaxi.ibs.master.model.LrtbRewardMaster;
import com.cdgtaxi.ibs.master.model.LrtbRewardTier;

public interface RewardBusiness extends GenericBusiness {
	public void createPlan(String categoryName);

	public List<LrtbRewardMaster> getPlans();

	public void deletePlans(List<LrtbRewardMaster> categories) throws RewardPlanInEffectException;

	public LrtbRewardMaster getPlan(Integer planNo);

	public LrtbRewardDetail getPlanDetail(Integer planDetailNo);

	public void updatePlanDetail(LrtbRewardDetail detail, Set<LrtbRewardTier> removedTiers, String userLoginId) throws RewardPlanOverlappingEffectiveDateException;

	public void savePlanDetail(LrtbRewardDetail detail, String userLoginId) throws RewardPlanOverlappingEffectiveDateException;

	public List<LrtbGiftCategory> getCategories();

	public void deleteCategory(LrtbGiftCategory category) throws RewardCategoryHasItemsException;

	public void createCategory(String categoryName);

	public LrtbGiftCategory getCategory(Integer giftCategoryNo);

	public void deleteItem(LrtbGiftItem value) throws RewardItemIsUsedException;

	public LrtbGiftItem getItem(Integer giftItemNo);

	public void stockupItem(Integer giftItemNo, Integer stockupQty, String userLoginId);

	public void moveItems(List<LrtbGiftItem> giftItems,
			LrtbGiftCategory newCategory);

	public List<LrtbGiftItem> searchItem(SearchGiftItemForm form);

	public AmtbAccount getAccount(Integer accountNo);

	public Integer calcCurrPoints(AmtbAccount account);

	public Integer calcPrevPoints(AmtbAccount account);

	public Integer redeemItem(AmtbAccount account, LrtbGiftItem giftItem,
		Integer redeemQty, String usePointsFrom, AmtbContactPerson contactPerson,
		BigDecimal serialNoStart, BigDecimal serialNoEnd, String userId) 
		throws InsufficientRewardPointsException, InsufficientGiftStockException;

	public List<LrtbGiftStock> getRedemptionHistory(Integer accountNo);
	
	public boolean itemWasRedeemed (LrtbGiftItem item);
	
	public AmtbAcctStatus getAccountLatestStatus(String acctNo);
	
	public BigDecimal getTotalRedeemedPoints(AmtbAccount account);
	
	public BigDecimal getTotalEarnedPoints(AmtbAccount account);
	
	public Serializable createAdjustmentRequest(AmtbAccount account, Integer pts, String requestType,
			String adjustPtsFrom, String reason, String remarks, String requestor) 
			throws InsufficientRewardPointsException;
	
	public List<LrtbRewardAdjReq> getPendingAdjustmentRequests();
	public void rejectAdjustmentRequest(LrtbRewardAdjReq request, String approverRemarks, String approverUserId);
	public void approveAdjustmentRequest(LrtbRewardAdjReq request, String approverRemarks, String approverUserId) 
		throws InsufficientRewardPointsException;
	
	public List<LrtbRewardAdjReqFlow> searchAdjustmentRequest(SearchAdjustmentRequestForm form);
}
