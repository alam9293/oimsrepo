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
		/*Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tx = session.beginTransaction();*/
		DetachedCriteria adminFeeCriteria = DetachedCriteria.forClass(MstbAdminFeeMaster.class);
		DetachedCriteria detailCriteria = adminFeeCriteria.createCriteria("mstbAdminFeeDetails", org.hibernate.sql.JoinType.LEFT_OUTER_JOIN);
		detailCriteria.add(Restrictions.isNotNull("adminFeePlanDetailNo"));
		return this.findAllByCriteria(adminFeeCriteria);
	}
	@SuppressWarnings("unchecked")
	public List<MstbEarlyPaymentMaster> getAllEarlyPaymentPlans() {
		DetachedCriteria earlyPaymentCriteria = DetachedCriteria.forClass(MstbEarlyPaymentMaster.class);
		DetachedCriteria detailCriteria = earlyPaymentCriteria.createCriteria("mstbEarlyPaymentDetails", org.hibernate.sql.JoinType.LEFT_OUTER_JOIN);
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
		DetachedCriteria detailCriteria = latePaymentCriteria.createCriteria("mstbLatePaymentDetails", org.hibernate.sql.JoinType.LEFT_OUTER_JOIN);
		detailCriteria.add(Restrictions.isNotNull("latePaymentPlanDetailNo"));
		return this.findAllByCriteria(latePaymentCriteria);
	}
	@SuppressWarnings("unchecked")
	public List<MstbProdDiscMaster> getAllProductDiscountPlans() {
		DetachedCriteria productDiscountCriteria = DetachedCriteria.forClass(MstbProdDiscMaster.class);
		DetachedCriteria detailCriteria = productDiscountCriteria.createCriteria("mstbProdDiscDetails", org.hibernate.sql.JoinType.LEFT_OUTER_JOIN);
		detailCriteria.add(Restrictions.isNotNull("prodDiscountPlanDetailNo"));
		
		return this.findAllByCriteria(productDiscountCriteria);
	}
	@SuppressWarnings("unchecked")
	public List<MstbVolDiscMaster> getAllVolumeDiscountPlans() {
		DetachedCriteria volumeDiscountCriteria = DetachedCriteria.forClass(MstbVolDiscMaster.class);
		volumeDiscountCriteria = volumeDiscountCriteria.createCriteria("mstbVolDiscDetails", org.hibernate.sql.JoinType.LEFT_OUTER_JOIN);
		volumeDiscountCriteria.add(Restrictions.isNotNull("volumeDiscountPlanDetailNo"));
		volumeDiscountCriteria = volumeDiscountCriteria.createCriteria("mstbVolDiscTiers", org.hibernate.sql.JoinType.LEFT_OUTER_JOIN);
		
		return this.findAllByCriteria(volumeDiscountCriteria);
	}
	@SuppressWarnings("unchecked")
	public List<LrtbRewardMaster> getAllRewardsPlans(){
		DetachedCriteria rewardsCriteria = DetachedCriteria.forClass(LrtbRewardMaster.class);
		rewardsCriteria = rewardsCriteria.createCriteria("lrtbRewardDetails", org.hibernate.sql.JoinType.LEFT_OUTER_JOIN);
		rewardsCriteria.add(Restrictions.isNotNull("rewardPlanDetailNo"));
		rewardsCriteria = rewardsCriteria.createCriteria("lrtbRewardTiers", org.hibernate.sql.JoinType.LEFT_OUTER_JOIN);
		
		return this.findAllByCriteria(rewardsCriteria);
	}
	@SuppressWarnings("unchecked")
	public List<FmtbEntityMaster> getAllEntities(){
		DetachedCriteria entityCriteria = DetachedCriteria.forClass(FmtbEntityMaster.class);
		entityCriteria.createCriteria("fmtbArContCodeMasters", org.hibernate.sql.JoinType.LEFT_OUTER_JOIN);
		entityCriteria.addOrder(Order.asc("entityName"));
		return this.findAllByCriteria(entityCriteria);
	}
	@SuppressWarnings("unchecked")
	public List<MstbSubscFeeMaster> getAllSubscriptionFees(){
		DetachedCriteria subscriptionCriteria = DetachedCriteria.forClass(MstbSubscFeeMaster.class);
		DetachedCriteria detailCriteria = subscriptionCriteria.createCriteria("mstbSubscFeeDetails", org.hibernate.sql.JoinType.LEFT_OUTER_JOIN);
		detailCriteria.add(Restrictions.isNotNull("subscriptionFeeDetailNo"));
		return this.findAllByCriteria(subscriptionCriteria);
	}
	@SuppressWarnings("unchecked")
	public List<MstbIssuanceFeeMaster> getAllIssuanceFees(){
		DetachedCriteria issuanceCriteria = DetachedCriteria.forClass(MstbIssuanceFeeMaster.class);
		DetachedCriteria detailCriteria = issuanceCriteria.createCriteria("mstbIssuanceFeeDetails", org.hibernate.sql.JoinType.LEFT_OUTER_JOIN);
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
		DetachedCriteria detailCriteria = creditTermCriteria.createCriteria("mstbCreditTermDetails", org.hibernate.sql.JoinType.LEFT_OUTER_JOIN);
		detailCriteria.add(Restrictions.isNotNull("mstbCreditTermDetailNo"));
		return this.findAllByCriteria(creditTermCriteria);
	}
	@SuppressWarnings("unchecked")
	public List<MstbBankMaster> getAllBanks(){
		DetachedCriteria bankCriteria = DetachedCriteria.forClass(MstbBankMaster.class);
		DetachedCriteria detailCriteria = bankCriteria.createCriteria("mstbBranchMasters", org.hibernate.sql.JoinType.LEFT_OUTER_JOIN);
		detailCriteria.add(Restrictions.isNotNull("branchMasterNo"));
		return this.findAllByCriteria(bankCriteria);
	}
	@SuppressWarnings("unchecked")
	public List<MstbPromotion> getAllPromotions(){
		DetachedCriteria promotionCriteria = DetachedCriteria.forClass(MstbPromotion.class);
		DetachedCriteria promotionDetailCriteria = promotionCriteria.createCriteria("currentPromoDetail", org.hibernate.sql.JoinType.LEFT_OUTER_JOIN);
		promotionDetailCriteria.add(Restrictions.isNotNull("promoDetailNo"));
		promotionCriteria.add(Restrictions.eq("currentStatus", NonConfigurableConstants.PROMOTION_STATUS_ACTIVE));
		promotionDetailCriteria.createCriteria("pmtbProductType", org.hibernate.sql.JoinType.LEFT_OUTER_JOIN);
		promotionDetailCriteria.createCriteria("mstbMasterTableByJobType", org.hibernate.sql.JoinType.LEFT_OUTER_JOIN);
		promotionDetailCriteria.createCriteria("mstbMasterTableByVehicleModel", org.hibernate.sql.JoinType.LEFT_OUTER_JOIN);
		return this.findAllByCriteria(promotionDetailCriteria);
	}
}