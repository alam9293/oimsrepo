package com.cdgtaxi.ibs.admin.business;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.zkoss.util.media.Media;

import com.cdgtaxi.ibs.common.business.GenericBusiness;
import com.cdgtaxi.ibs.common.exception.DataValidationError;
import com.cdgtaxi.ibs.common.exception.DuplicateCodeError;
import com.cdgtaxi.ibs.common.exception.DuplicateNameError;
import com.cdgtaxi.ibs.common.exception.FieldException;
import com.cdgtaxi.ibs.common.model.FmtbArContCodeDetail;
import com.cdgtaxi.ibs.common.model.FmtbArContCodeMaster;
import com.cdgtaxi.ibs.common.model.FmtbBankCode;
import com.cdgtaxi.ibs.common.model.FmtbBankPaymentDetail;
import com.cdgtaxi.ibs.common.model.FmtbBankPaymentMaster;
import com.cdgtaxi.ibs.common.model.FmtbEntityMaster;
import com.cdgtaxi.ibs.common.model.FmtbNonBillableDetail;
import com.cdgtaxi.ibs.common.model.FmtbNonBillableMaster;
import com.cdgtaxi.ibs.common.model.FmtbTaxCode;
import com.cdgtaxi.ibs.common.model.FmtbTransactionCode;
import com.cdgtaxi.ibs.common.model.IttbGiroReq;
import com.cdgtaxi.ibs.common.model.IttbGiroReturnReq;
import com.cdgtaxi.ibs.common.model.IttbGiroSetup;
import com.cdgtaxi.ibs.common.model.IttbGiroUobHeader;
import com.cdgtaxi.ibs.common.model.IttbGovtEinvoiceReq;
import com.cdgtaxi.ibs.common.model.IttbPubbsReq;
import com.cdgtaxi.ibs.common.model.IttbRecurringReq;
import com.cdgtaxi.ibs.common.model.LrtbRewardAccount;
import com.cdgtaxi.ibs.common.model.PmtbProductType;
import com.cdgtaxi.ibs.common.model.forms.SearchAcquirerForm;
import com.cdgtaxi.ibs.common.model.forms.SearchAcquirerMdrForm;
import com.cdgtaxi.ibs.common.model.forms.SearchAcquirerPaymentTypeForm;
import com.cdgtaxi.ibs.common.model.forms.SearchBankPaymentForm;
import com.cdgtaxi.ibs.common.model.forms.SearchGLBankForm;
import com.cdgtaxi.ibs.common.model.forms.SearchGiroRequestForm;
import com.cdgtaxi.ibs.common.model.forms.SearchGiroReturnRequestForm;
import com.cdgtaxi.ibs.common.model.forms.SearchGovtEInvRequestForm;
import com.cdgtaxi.ibs.common.model.forms.SearchNonBillableForm;
import com.cdgtaxi.ibs.common.model.forms.SearchPrepaidPromotionForm;
import com.cdgtaxi.ibs.common.model.forms.SearchPromoPlanHistoryForm;
import com.cdgtaxi.ibs.common.model.forms.SearchPromotionForm;
import com.cdgtaxi.ibs.common.model.forms.SearchPubbsRequestForm;
import com.cdgtaxi.ibs.common.model.forms.SearchRecurringRequestForm;
import com.cdgtaxi.ibs.common.model.forms.SearchTransactionCodeForm;
import com.cdgtaxi.ibs.master.model.MstbAcquirer;
import com.cdgtaxi.ibs.master.model.MstbAcquirerMdr;
import com.cdgtaxi.ibs.master.model.MstbAcquirerPymtType;
import com.cdgtaxi.ibs.master.model.MstbAdminFeeDetail;
import com.cdgtaxi.ibs.master.model.MstbAdminFeeMaster;
import com.cdgtaxi.ibs.master.model.MstbBankMaster;
import com.cdgtaxi.ibs.master.model.MstbBranchMaster;
import com.cdgtaxi.ibs.master.model.MstbCreditTermDetail;
import com.cdgtaxi.ibs.master.model.MstbCreditTermMaster;
import com.cdgtaxi.ibs.master.model.MstbEarlyPaymentDetail;
import com.cdgtaxi.ibs.master.model.MstbEarlyPaymentMaster;
import com.cdgtaxi.ibs.master.model.MstbInvoicePromo;
import com.cdgtaxi.ibs.master.model.MstbIssuanceFeeDetail;
import com.cdgtaxi.ibs.master.model.MstbIssuanceFeeMaster;
import com.cdgtaxi.ibs.master.model.MstbLatePaymentDetail;
import com.cdgtaxi.ibs.master.model.MstbLatePaymentMaster;
import com.cdgtaxi.ibs.master.model.MstbMasterTable;
import com.cdgtaxi.ibs.master.model.MstbProdDiscDetail;
import com.cdgtaxi.ibs.master.model.MstbProdDiscMaster;
import com.cdgtaxi.ibs.master.model.MstbPromoReq;
import com.cdgtaxi.ibs.master.model.MstbPromotion;
import com.cdgtaxi.ibs.master.model.MstbPromotionCashPlus;
import com.cdgtaxi.ibs.master.model.MstbSalesperson;
import com.cdgtaxi.ibs.master.model.MstbSubscFeeDetail;
import com.cdgtaxi.ibs.master.model.MstbSubscFeeMaster;
import com.cdgtaxi.ibs.master.model.MstbVolDiscDetail;
import com.cdgtaxi.ibs.master.model.MstbVolDiscMaster;
import com.cdgtaxi.ibs.master.model.MstbVolDiscTier;

public interface AdminBusiness extends GenericBusiness {

	void createAdminFeePlan(MstbAdminFeeMaster adminFee, String userLoginId) throws DataValidationError;

	void createBank(MstbBankMaster bank, Map<String, String> branchMap, String userLoginId) throws DataValidationError;

	void createCreditTermPlan(MstbCreditTermMaster plan, String userLoginId) throws DataValidationError;

	void createEarlyPaymentPlan(MstbEarlyPaymentMaster plan, String userLoginId) throws DataValidationError;

	void createGLBank(FmtbBankCode bankCode, FmtbBankCode defaultGiroBank, String userLoginId) throws DataValidationError;

	void createGLControlCode(FmtbArContCodeMaster code, String userLoginId) throws DataValidationError;

	void createLatePaymentPlan(MstbLatePaymentMaster plan, String userLoginId) throws DataValidationError;

	void createProductDiscountPlan(MstbProdDiscMaster plan, String loginUserId) throws DataValidationError;

	void createSubscriptionFeePlan(MstbSubscFeeMaster plan, String userLoginId) throws DataValidationError;

	void createIssuanceFeePlan(MstbIssuanceFeeMaster plan, String userLoginId) throws DataValidationError;
	
	void createVolumeDiscountPlan(MstbVolDiscMaster plan, String userLoginID) throws DataValidationError;

	void deleteAdminFeePlan(MstbAdminFeeMaster value);

	void deleteBank(MstbBankMaster value);

	void deleteCreditTermPlan(MstbCreditTermMaster value);

	void deleteEarlyPaymentPlan(MstbEarlyPaymentMaster value);

	void deleteGLBankCode(FmtbBankCode value);

	void deleteGLControlCode(FmtbArContCodeMaster value);

	void deleteLatePaymentPlan(MstbLatePaymentMaster value);

	void deleteProductDiscountPlan(MstbProdDiscMaster value);

	void deleteSubscriptionFeePlan(MstbSubscFeeMaster value);
	
	void deleteIssuanceFeePlan(MstbIssuanceFeeMaster value);

	void deleteVolumeDiscountPlan(MstbVolDiscMaster plan);

	MstbAdminFeeMaster getAdminFeePlan(Integer planNo);

	MstbAdminFeeDetail getAdminFeePlanDetail(Integer planDetailNo);

	List<MstbAdminFeeMaster> getAdminFeePlans();

	MstbBankMaster getBank(Integer bankNo);

	MstbBranchMaster getBankBranch(Integer planDetailNo);

	List<MstbBankMaster> getBanks();

	MstbCreditTermMaster getCreditTermPlan(Integer creditTermPlanNo);

	MstbCreditTermDetail getCreditTermPlanDetail(Integer planDetailNo);

	List<MstbCreditTermMaster> getCreditTermPlans();

	MstbEarlyPaymentMaster getEarlyPaymentPlan(Integer earlyPaymentPlanNo);

	MstbEarlyPaymentDetail getEarlyPaymentPlanDetail(Integer planDetailNo);

	List<MstbEarlyPaymentMaster> getEarlyPaymentPlans();

	FmtbBankCode getGLBank(Integer codeNo);

	List<Object[]> getGLBankCodes(SearchGLBankForm form);

	FmtbArContCodeMaster getGLControlCode(Integer codeNo);

	FmtbArContCodeDetail getGLControlCodeDetail(Integer planDetailNo);

	List<FmtbArContCodeMaster> getGLControlCodes(Integer entityNo);

	MstbLatePaymentMaster getLatePaymentPlan(Integer latePaymentPlanNo);

	MstbLatePaymentDetail getLatePaymentPlanDetail(Integer planDetailNo);

	List<MstbLatePaymentMaster> getLatePaymentPlans();

	MstbProdDiscMaster getProductDiscountPlan(Integer planNo);

	MstbProdDiscDetail getProductDiscountPlanDetail(Integer planDetailNo);

	List<MstbProdDiscMaster> getProductDiscountPlans();

	MstbSubscFeeMaster getSubscriptionFeePlan(Integer subscriptionFeeNo);

	MstbSubscFeeDetail getSubscriptionFeePlanDetail(Integer planDetailNo);

	List<MstbSubscFeeMaster> getSubscriptionFeePlans();
	
	MstbIssuanceFeeMaster getIssuanceFeePlan(Integer issuanceFeeNo);

	MstbIssuanceFeeDetail getIssuanceFeePlanDetail(Integer planDetailNo);

	List<MstbIssuanceFeeMaster> getIssuanceFeePlans();

	MstbVolDiscMaster getVolumeDiscountPlan(Integer planNo);

	MstbVolDiscDetail getVolumeDiscountPlanDetail(Integer planDetailNo);

	List<MstbVolDiscMaster> getVolumeDiscountPlans();

	void saveAdminFeePlanDetail(MstbAdminFeeDetail detail, String userLoginId) throws DataValidationError;

	void saveBankBranch(MstbBranchMaster branch, String userLoginId) throws DataValidationError, DuplicateNameError;

	void saveCreditTermPlanDetail(MstbCreditTermDetail detail, String userLoginId) throws DataValidationError;

	void saveEarlyPaymentPlanDetail(MstbEarlyPaymentDetail detail, String userLoginId) throws DataValidationError;

	public void saveGLControlCodeDetail(FmtbArContCodeDetail detail, String userLoginId) throws DataValidationError;

	void saveLatePaymentPlanDetail(MstbLatePaymentDetail detail, String userLoginId) throws DataValidationError;

	void saveProductDiscountPlanDetail(MstbProdDiscDetail detail, String userLoginId) throws DataValidationError;

	void saveSubscriptionFeePlanDetail(MstbSubscFeeDetail detail, String userLoginId) throws DataValidationError;

	void saveIssuanceFeePlanDetail(MstbIssuanceFeeDetail detail, String userLoginId) throws DataValidationError;
	
	void saveVolumeDiscountPlanDetail(MstbVolDiscDetail detail,String userLoginId) throws DataValidationError;

	void updateAdminFeePlan(MstbAdminFeeMaster plan, String userLoginId) throws DataValidationError;

	void updateAdminFeePlanDetail(MstbAdminFeeDetail detail, String userLoginId) throws DataValidationError;

	void updateBank(MstbBankMaster bank, String userLoginId) throws DataValidationError;

	void updateBankBranch(MstbBranchMaster branch, String userLoginId) throws DataValidationError, DuplicateNameError;

	void updateCreditTermPlan(MstbCreditTermMaster plan, String userLoginId) throws DataValidationError;

	void updateCreditTermPlanDetail(MstbCreditTermDetail detail, String userLoginId) throws DataValidationError;

	void updateEarlyPaymentPlan(MstbEarlyPaymentMaster plan, String userLoginId) throws DataValidationError;

	void updateEarlyPaymentPlanDetail(MstbEarlyPaymentDetail detail, String userLoginId) throws DataValidationError;

	void updateGLBank(FmtbBankCode bank, FmtbBankCode defaultGiroBank, String userLoginId) throws DataValidationError;

	void updateGLControlCode(FmtbArContCodeMaster code, String userLoginId) throws DataValidationError;

	public void updateGLControlCodeDetail(FmtbArContCodeDetail detail, String userLoginId) throws DuplicateCodeError ;

	void updateLatePaymentPlan(MstbLatePaymentMaster plan, String userLoginId) throws DataValidationError;

	void updateLatePaymentPlanDetail(MstbLatePaymentDetail detail, String userLoginId) throws DataValidationError;

	void updateProductDiscountPlan(MstbProdDiscMaster plan, String userLoginId) throws DataValidationError;

	void updateProductDiscountPlanDetail(MstbProdDiscDetail detail, String userLoginId) throws DataValidationError;

	void updateSubscriptionFeePlan(MstbSubscFeeMaster plan, String userLoginId) throws DataValidationError;

	void updateSubscriptionFeePlanDetail(MstbSubscFeeDetail detail, String userLoginId) throws DataValidationError;

	void updateIssuanceFeePlan(MstbIssuanceFeeMaster plan, String userLoginId) throws DataValidationError;
	
	void updateIssuanceFeePlanDetail(MstbIssuanceFeeDetail detail, String userLoginId) throws DataValidationError;
	
	void updateVolumeDiscountPlan(MstbVolDiscMaster plan, String userLoginId) throws DataValidationError;

	void updateVolumeDiscountPlanDetail(MstbVolDiscDetail detail, Set<MstbVolDiscTier> removedTiers, String userLoginId) throws DataValidationError;

	List<FmtbEntityMaster> getEntities();
	public List<FmtbEntityMaster> getActiveEntities();

	void deleteGLTaxCode(FmtbTaxCode code);

	List<FmtbTaxCode> getGLTaxCodes(Integer entityNo, String taxType);

	void createGLTax(FmtbTaxCode code, String userLoginId) throws DataValidationError;

	FmtbTaxCode getGLTax(Integer taxCodeNo);

	void updateGLTax(FmtbTaxCode tax, String userLoginId) throws DataValidationError;

	void deleteSalesPerson(MstbSalesperson salesPerson);

	List<MstbSalesperson> getSalesPersons();

	void createSalesPerson(MstbSalesperson salesPerson, String userLoginId) throws DataValidationError;

	MstbSalesperson getSalesPerson(Integer salesPersonNo);

	void updateSalesPerson(MstbSalesperson salesPerson, String userLoginId) throws DataValidationError;

	List<Object[]> getTransactionCodes(SearchTransactionCodeForm form);

	List<PmtbProductType> getProductTypes();

	FmtbTransactionCode getTransactionCode(Integer transactionCodeNo);

	void updateTransactionCode(FmtbTransactionCode transactionCode, String userLoginId) throws DataValidationError;

	void createTransactionCode(FmtbTransactionCode transactionCode, String userLoginId) throws DataValidationError;

	FmtbEntityMaster getEntity(Integer entityNo);

	void updateEntity(FmtbEntityMaster entity, String userLoginId) throws DataValidationError;

	void createEntity(FmtbEntityMaster entity, String userLoginId) throws DataValidationError;

	boolean hasDuplicateName(Integer promoNo, String name);
	
	List<MstbPromotion> searchCurrentPromotion(SearchPromotionForm form);
	
	List<MstbPromotion> searchLastPromoReq(SearchPromotionForm form);
	
	List<MstbPromoReq> searchPromoPlanHistory(SearchPromoPlanHistoryForm form);
	
	List<MstbPromoReq> getPendingPromoReq();
	
	MstbPromoReq getPromoReq(Integer promoReqNo);
	
	void approvePromoReq(List<MstbPromoReq> reqs, String remarks);
	
	void rejectPromoReq(List<MstbPromoReq> reqs, String remarks);
	
	List<MstbAcquirer> searchAcquirer(SearchAcquirerForm form);
	
	List<MstbAcquirerPymtType> searchAcquirerPaymentType(SearchAcquirerPaymentTypeForm form);
	
	List<Object[]> searchAcquirerMdr(SearchAcquirerMdrForm form);
	
	boolean isAcquirerDeletable(int acquirer_no);
	
	List<MstbMasterTable> getPaymentType(MstbAcquirer acquirer);
	
	List<MstbAcquirer> getAcquirer();
	
	boolean hasDuplicateRecord(MstbAcquirerPymtType acquirerPymtType);
	
	boolean hasDuplicateRecord(MstbAcquirerMdr acquirerMdr);
	
	MstbAcquirerMdr getAcquirerMdr(Integer mdrNo);
	
	MstbPromotion getPromotion(Integer promoNo);
	
	MstbAcquirerPymtType getAcquirerPaymentType(Integer pymtTypeNo);
	
	List<FmtbNonBillableMaster> searchNonBillable(SearchNonBillableForm form);
	
	public boolean hasDuplicateRecord(FmtbNonBillableMaster fnbm);
	
	List<FmtbArContCodeMaster> getARControlCode(Integer entity_no);
	
	List<String> getTransactionCodeType(Integer entityNo, String txn_type);
	
	public boolean isArContCodeBeenUsed(Integer arControlCodeNo);
	
	FmtbNonBillableMaster getNonBillableMaster(Integer masterNo);
	
	FmtbNonBillableDetail getNonBillableDetail(Integer detailNo);
	
	public boolean hasDuplicateRecord(FmtbNonBillableMaster master, FmtbNonBillableDetail detail);
	
	List<FmtbBankPaymentMaster> searchBankPayment(SearchBankPaymentForm form);
	
	List<FmtbBankPaymentMaster> getBankPaymentByForeignExample(FmtbBankPaymentMaster master);
	
	void createNonBillableGL(FmtbNonBillableMaster master, FmtbNonBillableDetail detail, String loginUserId);

	FmtbBankPaymentMaster getBankPaymentMaster(Integer masterNo);
	
	List<FmtbBankPaymentDetail> getBankPaymentDetailByForeignExample(FmtbBankPaymentDetail detail);
	
	FmtbBankPaymentDetail getBankPaymentDetail(Integer detailNo);
	
	void deleteVolumeDiscountPlanDetail(MstbVolDiscDetail detail);
	
	boolean isRecordDeletable(FmtbEntityMaster master);
	
	boolean isRecordDeletable(FmtbArContCodeMaster master);
	
	boolean isRecordDeletable(MstbBankMaster master);
	
	boolean isRecordDeletable(MstbBranchMaster master);
	
	List<MstbAcquirer> getAcquirerByExample();
	
	void createBankPayment(FmtbBankPaymentMaster master, FmtbBankPaymentDetail detail, String userLoginId);
	
	public FmtbTransactionCode getEarliestEffectedTxnCode(String txnType, String txnCode);
	
	public List<LrtbRewardAccount> getActiveRewardsAccount();
	
	public FmtbBankCode checkDefaultGiroBankExist(Date effectiveDate, Integer bankCodeNo);
	
	public List<IttbGiroSetup> getActiveGiroSetup();
	
	public void saveGiroSetupChanges(List<IttbGiroSetup> existingSetups, 
			List<IttbGiroSetup> newSetups, List<IttbGiroSetup> deletedSetups, String userId);
	
	public FmtbBankCode getLatestEntityBank(FmtbEntityMaster entityMaster);
	
	public Long createGiroRequest(IttbGiroSetup setup, Date requestDate, 
			int requestTime, Date valueDate, Date cutoffDate, String userId);
	
	public List<IttbGiroReq> searchGiroRequest(SearchGiroRequestForm form) ;
	
	public IttbGiroReq searchGiroRequest(Long requestNo) ;
	
	public void updateGiroRequest(IttbGiroReq request, Date requestDate, 
			int requestTime, Date valueDate, Date cutoffDate, String userId);
	
	public IttbGiroSetup getInvoiceGiroDay(Integer entityNo);
	
	public  List<IttbGiroReq> getExistingGiroRequest(IttbGiroSetup setup);
	
	public FmtbBankCode checkDefaultCollectionBankExist(Integer entityNo, Date effectiveDate, Integer bankCodeNo);
	
	public boolean isGiroReturnFileUploadedBefore(String fileName);
	
	public IttbGiroUobHeader getCorrespondingGiroOutgoingFile(String returnFileName);
	
	public Long createGiroReturnRequest(IttbGiroReturnReq newRequest, 
			Media media, String incomingDirectory, String userId) throws Exception;
	
	public List<IttbGiroReturnReq> searchGiroReturnRequest(SearchGiroReturnRequestForm form) ;
	
	public IttbGiroReturnReq searchGiroReturnRequest(Long requestNo) ;
	
	public List<IttbGovtEinvoiceReq> searchGovtEInvRequest(SearchGovtEInvRequestForm form) ;
	
	public IttbGovtEinvoiceReq searchGovtEInvRequest(Integer requestNo) ;
	
	public void uploadGovEInvReturnFile(IttbGovtEinvoiceReq request, Map<String, List<String>> listOfUploadedFiles) throws FieldException, Exception ;

	public List<IttbPubbsReq> searchPubbsRequest(SearchPubbsRequestForm form) ;

	public IttbPubbsReq searchPubbsRequest(Integer requestNo) ;
	
	public List<IttbRecurringReq> searchIttbRecurringRequest(SearchRecurringRequestForm form) ;
	
	public IttbRecurringReq searchIttbRecurringRequest(Integer requestNo) ;

	public void uploadPubbsReturnFile(IttbPubbsReq request, Map<String, List<String>> listOfUploadedFiles) throws FieldException, Exception ;

	public List<MstbPromotionCashPlus> searchPrepaidPromotion(SearchPrepaidPromotionForm form);
	
	public boolean hasDuplicateCashPlusPromoCode(String promoCode);
	
	public MstbPromotionCashPlus getPromotionCashPlus(String promoCode);

	public List<MstbPromotionCashPlus> getEffectivePrepaidPromotions();
	
	public void updateAcquirerPaymentType(MstbAcquirerPymtType acquirerPymtType, Map<java.util.Date, Map<String, Object>> commissionMapSubmit, String userId);
	
	public List<MstbInvoicePromo> getListInvoicePromo(String name, Date dateFrom, Date dateTo, String initial);

	public MstbInvoicePromo getInvoicePromo(Integer invoicePromoId);

	public boolean checkPromoNumber(String checkPromo, Timestamp insDate, int promoId);
}
