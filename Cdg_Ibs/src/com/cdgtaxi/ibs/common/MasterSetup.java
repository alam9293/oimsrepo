package com.cdgtaxi.ibs.common;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

public final class MasterSetup {
	private static Logger logger = Logger.getLogger(MasterSetup.class);
	private static Map<String, MasterManager> masterManagers = new HashMap<String, MasterManager>();
	public static final String ADMIN_FEE_MANAGER = "AF";
	public static final String BANK_MANAGER = "BA";
	public static final String EARLY_PAYMENT_MANAGER = "EP";
	public static final String ENTITY_MANAGER = "ET";
	public static final String GST_MANAGER = "GST";
	public static final String LATE_PAYMENT_MANAGER = "LP";
	public static final String PRODUCT_DISCOUNT = "PD";
	public static final String REWARDS_MANAGER = "RW";
	public static final String SUBSCRIPTION_MANAGER = "SF";
	public static final String ISSUANCE_MANAGER = "IF";
	public static final String SALESPERSON_MANAGER = "SP";
	public static final String VOLUME_DISCOUNT_MANAGER = "VD";
	public static final String CREDIT_TERM_MANAGER = "CT";
	public static final String PROMOTION_MANAGER = "PM";
	public static AdminFeeMasterManager getAdminFeeManager(){
		logger.info("getting admin fee manager");
		return (AdminFeeMasterManager)getManager(MasterSetup.ADMIN_FEE_MANAGER);
	}
	public static BankMasterManager getBankManager(){
		logger.info("getting bank manager");
		return (BankMasterManager)getManager(MasterSetup.BANK_MANAGER);
	}
	public static EarlyPaymentMasterManager getEarlyPaymentManager(){
		logger.info("getting early payment manager");
		return (EarlyPaymentMasterManager)getManager(MasterSetup.EARLY_PAYMENT_MANAGER);
	}
	public static EntityMasterManager getEntityManager(){
		logger.info("getting entity manager");
		return (EntityMasterManager)getManager(MasterSetup.ENTITY_MANAGER);
	}
	public static GSTMasterManager getGSTManager(){
		logger.info("getting GST manager");
		return (GSTMasterManager)getManager(MasterSetup.GST_MANAGER);
	}
	public static LatePaymentMasterManager getLatePaymentManager(){
		logger.info("getting late payment manager");
		return (LatePaymentMasterManager)getManager(MasterSetup.LATE_PAYMENT_MANAGER);
	}
	public static ProductDiscountMasterManager getProductDiscountManager(){
		logger.info("getting product discount manager");
		return (ProductDiscountMasterManager)getManager(MasterSetup.PRODUCT_DISCOUNT);
	}
	public static RewardsMasterManager getRewardsManager(){
		logger.info("getting rewards manager");
		return (RewardsMasterManager)getManager(MasterSetup.REWARDS_MANAGER);
	}
	public static SubscriptionMasterManager getSubscriptionManager(){
		logger.info("getting subscription manager");
		return (SubscriptionMasterManager)getManager(MasterSetup.SUBSCRIPTION_MANAGER);
	}
	public static IssuanceMasterManager getIssuanceManager(){
		logger.info("getting issuance manager");
		return (IssuanceMasterManager)getManager(MasterSetup.ISSUANCE_MANAGER);
	}
	public static SalespersonMasterManager getSalespersonManager(){
		logger.info("getting salesperson manager");
		return (SalespersonMasterManager)getManager(MasterSetup.SALESPERSON_MANAGER);
	}
	public static VolumeDiscountMasterManager getVolumeDiscountManager(){
		logger.info("getting volume discount manager");
		return (VolumeDiscountMasterManager)getManager(MasterSetup.VOLUME_DISCOUNT_MANAGER);
	}
	public static CreditTermMasterManager getCreditTermManager(){
		logger.info("getting credit term manager");
		return (CreditTermMasterManager)getManager(MasterSetup.CREDIT_TERM_MANAGER);
	}
	public static PromotionMasterManager getPromotionManager(){
		logger.info("getting promotion manager");
		return (PromotionMasterManager)getManager(MasterSetup.PROMOTION_MANAGER);
	}
	public Map<String, MasterManager> getMasterManagers(){
		return masterManagers;
	}
	private static MasterManager getManager(String type){
		return masterManagers.get(type);
	}
	public void setMasterManagers(Map<String, MasterManager> masterManagers){
		MasterSetup.masterManagers = masterManagers;
	}
}