package com.cdgtaxi.ibs.master.dao;

import java.util.List;

import com.cdgtaxi.ibs.common.dao.GenericDao;
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

public interface SetupDao extends GenericDao {
	public List<MstbAdminFeeMaster> getAllAdminFeePlans();
	public List<MstbEarlyPaymentMaster> getAllEarlyPaymentPlans();
	public List<MstbGstDetail> getAllGst();
	public List<MstbLatePaymentMaster> getAllLatePaymentPlans();
	public List<MstbProdDiscMaster> getAllProductDiscountPlans();
	public List<MstbVolDiscMaster> getAllVolumeDiscountPlans();
	public List<LrtbRewardMaster> getAllRewardsPlans();
	public List<FmtbEntityMaster> getAllEntities();
	public List<MstbSubscFeeMaster> getAllSubscriptionFees();
	public List<MstbIssuanceFeeMaster> getAllIssuanceFees();
	public List<MstbSalesperson> getAllSalespersons();
	public List<MstbCreditTermMaster> getAllCreditTerms();
	public List<MstbBankMaster> getAllBanks();
	public List<MstbPromotion> getAllPromotions();
}