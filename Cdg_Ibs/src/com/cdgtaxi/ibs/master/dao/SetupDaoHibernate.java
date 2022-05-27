package com.cdgtaxi.ibs.master.dao;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.cdgtaxi.ibs.common.NonConfigurableConstants;
import com.cdgtaxi.ibs.common.dao.GenericDaoHibernate;
import com.cdgtaxi.ibs.common.model.FmtbEntityMaster;
import com.cdgtaxi.ibs.master.model.LrtbRewardMaster;
import com.cdgtaxi.ibs.master.model.MstbAdminFeeMaster;
import com.cdgtaxi.ibs.master.model.MstbBankMaster;
import com.cdgtaxi.ibs.master.model.MstbCreditTermMaster;
import com.cdgtaxi.ibs.master.model.MstbEarlyPaymentMaster;
import com.cdgtaxi.ibs.master.model.MstbGstDetail;
import com.cdgtaxi.ibs.master.model.MstbIssuanceFeeMaster;
import com.cdgtaxi.ibs.master.model.MstbLatePaymentMaster;
import com.cdgtaxi.ibs.master.model.MstbProdDiscMaster;
import com.cdgtaxi.ibs.master.model.MstbPromotion;
import com.cdgtaxi.ibs.master.model.MstbSalesperson;
import com.cdgtaxi.ibs.master.model.MstbSubscFeeMaster;
import com.cdgtaxi.ibs.master.model.MstbVolDiscMaster;

public class SetupDaoHibernate extends GenericDaoHibernate implements SetupDao {
	@SuppressWarnings("unchecked")
	public List<MstbAdminFeeMaster> getAllAdminFeePlans() {
		DetachedCriteria adminFeeCriteria = DetachedCriteria.forClass(MstbAdminFeeMaster.class);
		DetachedCriteria detailCriteria = adminFeeCriteria.createCriteria("mstbAdminFeeDetails", DetachedCriteria.LEFT_JOIN);
		detailCriteria.add(Restrictions.isNotNull("adminFeePlanDetailNo"));
		return this.findAllByCriteria(adminFeeCriteria);
	}
	@SuppressWarnings("unchecked")
	public List<MstbEarlyPaymentMaster> getAllEarlyPaymentPlans() {
		DetachedCriteria earlyPaymentCriteria = DetachedCriteria.forClass(MstbEarlyPaymentMaster.class);
		DetachedCriteria detailCriteria = earlyPaymentCriteria.createCriteria("mstbEarlyPaymentDetails", DetachedCriteria.LEFT_JOIN);
		detailCriteria.add(Restrictions.isNotNull("earlyPaymentPlanDetailNo"));
		return this.findAllByCriteria(earlyPaymentCriteria);
	}
	@SuppressWarnings("unchecked")
	public List<MstbGstDetail> getAllGst() {
		DetachedCriteria gstCriteria = DetachedCriteria.forClass(MstbGstDetail.class);
		return this.findAllByCriteria(gstCriteria);
	}
	@SuppressWarnings("unchecked")
	public List<MstbLatePaymentMaster> getAllLatePaymentPlans() {
		DetachedCriteria latePaymentCriteria = DetachedCriteria.forClass(MstbLatePaymentMaster.class);
		DetachedCriteria detailCriteria = latePaymentCriteria.createCriteria("mstbLatePaymentDetails", DetachedCriteria.LEFT_JOIN);
		detailCriteria.add(Restrictions.isNotNull("latePaymentPlanDetailNo"));
		return this.findAllByCriteria(latePaymentCriteria);
	}
	@SuppressWarnings("unchecked")
	public List<MstbProdDiscMaster> getAllProductDiscountPlans() {
		DetachedCriteria productDiscountCriteria = DetachedCriteria.forClass(MstbProdDiscMaster.class);
		DetachedCriteria detailCriteria = productDiscountCriteria.createCriteria("mstbProdDiscDetails", DetachedCriteria.LEFT_JOIN);
		detailCriteria.add(Restrictions.isNotNull("prodDiscountPlanDetailNo"));
		
		return this.findAllByCriteria(productDiscountCriteria);
	}
	@SuppressWarnings("unchecked")
	public List<MstbVolDiscMaster> getAllVolumeDiscountPlans() {
		DetachedCriteria volumeDiscountCriteria = DetachedCriteria.forClass(MstbVolDiscMaster.class);
		volumeDiscountCriteria = volumeDiscountCriteria.createCriteria("mstbVolDiscDetails", DetachedCriteria.LEFT_JOIN);
		volumeDiscountCriteria.add(Restrictions.isNotNull("volumeDiscountPlanDetailNo"));
		volumeDiscountCriteria = volumeDiscountCriteria.createCriteria("mstbVolDiscTiers", DetachedCriteria.LEFT_JOIN);
		
		return this.findAllByCriteria(volumeDiscountCriteria);
	}
	@SuppressWarnings("unchecked")
	public List<LrtbRewardMaster> getAllRewardsPlans(){
		DetachedCriteria rewardsCriteria = DetachedCriteria.forClass(LrtbRewardMaster.class);
		rewardsCriteria = rewardsCriteria.createCriteria("lrtbRewardDetails", DetachedCriteria.LEFT_JOIN);
		rewardsCriteria.add(Restrictions.isNotNull("rewardPlanDetailNo"));
		rewardsCriteria = rewardsCriteria.createCriteria("lrtbRewardTiers", DetachedCriteria.LEFT_JOIN);
		
		return this.findAllByCriteria(rewardsCriteria);
	}
	@SuppressWarnings("unchecked")
	public List<FmtbEntityMaster> getAllEntities(){
		DetachedCriteria entityCriteria = DetachedCriteria.forClass(FmtbEntityMaster.class);
		entityCriteria.createCriteria("fmtbArContCodeMasters", DetachedCriteria.LEFT_JOIN);
		entityCriteria.addOrder(Order.asc("entityName"));
		return this.findAllByCriteria(entityCriteria);
	}
	@SuppressWarnings("unchecked")
	public List<MstbSubscFeeMaster> getAllSubscriptionFees(){
		DetachedCriteria subscriptionCriteria = DetachedCriteria.forClass(MstbSubscFeeMaster.class);
		DetachedCriteria detailCriteria = subscriptionCriteria.createCriteria("mstbSubscFeeDetails", DetachedCriteria.LEFT_JOIN);
		detailCriteria.add(Restrictions.isNotNull("subscriptionFeeDetailNo"));
		return this.findAllByCriteria(subscriptionCriteria);
	}
	@SuppressWarnings("unchecked")
	public List<MstbIssuanceFeeMaster> getAllIssuanceFees(){
		DetachedCriteria issuanceCriteria = DetachedCriteria.forClass(MstbIssuanceFeeMaster.class);
		DetachedCriteria detailCriteria = issuanceCriteria.createCriteria("mstbIssuanceFeeDetails", DetachedCriteria.LEFT_JOIN);
		detailCriteria.add(Restrictions.isNotNull("issuanceFeeDetailNo"));
		return this.findAllByCriteria(issuanceCriteria);
	}
	@SuppressWarnings("unchecked")
	public List<MstbSalesperson> getAllSalespersons(){
		return this.getAll(MstbSalesperson.class);
	}
	@SuppressWarnings("unchecked")
	public List<MstbCreditTermMaster> getAllCreditTerms(){
		DetachedCriteria creditTermCriteria = DetachedCriteria.forClass(MstbCreditTermMaster.class);
		DetachedCriteria detailCriteria = creditTermCriteria.createCriteria("mstbCreditTermDetails", DetachedCriteria.LEFT_JOIN);
		detailCriteria.add(Restrictions.isNotNull("mstbCreditTermDetailNo"));
		return this.findAllByCriteria(creditTermCriteria);
	}
	@SuppressWarnings("unchecked")
	public List<MstbBankMaster> getAllBanks(){
		DetachedCriteria bankCriteria = DetachedCriteria.forClass(MstbBankMaster.class);
		DetachedCriteria detailCriteria = bankCriteria.createCriteria("mstbBranchMasters", DetachedCriteria.LEFT_JOIN);
		detailCriteria.add(Restrictions.isNotNull("branchMasterNo"));
		return this.findAllByCriteria(bankCriteria);
	}
	@SuppressWarnings("unchecked")
	public List<MstbPromotion> getAllPromotions(){
		DetachedCriteria promotionCriteria = DetachedCriteria.forClass(MstbPromotion.class);
		DetachedCriteria promotionDetailCriteria = promotionCriteria.createCriteria("currentPromoDetail", DetachedCriteria.LEFT_JOIN);
		promotionDetailCriteria.add(Restrictions.isNotNull("promoDetailNo"));
		promotionCriteria.add(Restrictions.eq("currentStatus", NonConfigurableConstants.PROMOTION_STATUS_ACTIVE));
		promotionDetailCriteria.createCriteria("pmtbProductType", DetachedCriteria.LEFT_JOIN);
		promotionDetailCriteria.createCriteria("mstbMasterTableByJobType", DetachedCriteria.LEFT_JOIN);
		promotionDetailCriteria.createCriteria("mstbMasterTableByVehicleModel", DetachedCriteria.LEFT_JOIN);
		return this.findAllByCriteria(promotionDetailCriteria);
	}
}