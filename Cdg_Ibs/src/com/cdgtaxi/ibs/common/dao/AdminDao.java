package com.cdgtaxi.ibs.common.dao;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;

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
import com.cdgtaxi.ibs.common.model.IttbRecurringDtl;
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

public interface AdminDao extends GenericDao {
	MstbVolDiscMaster getVolumeDiscountPlanWithDetails(Integer planNo);

	MstbVolDiscDetail getVolumeDiscountPlanDetailWithTiers(Integer planDetailNo);

	MstbProdDiscMaster getProductDiscountPlanWithDetails(Integer planNo);

	MstbProdDiscDetail getProductDiscountPlanDetail(Integer planDetailNo);

	MstbCreditTermMaster getCreditTermPlanWithDetails(Integer creditTermPlanNo);

	List<Integer> getAllCreditTerms();
	
	List<MstbPromotionCashPlus> getAllPromotionCashPlus();

	MstbSubscFeeMaster getSubscriptionFeePlanWithDetails(
			Integer subscriptionFeeNo);
	
	MstbIssuanceFeeMaster getIssuanceFeePlanWithDetails(
			Integer issuanceFeeNo);

	MstbLatePaymentMaster getLatePaymentPlanWithDetails(
			Integer latePaymentPlanNo);

	MstbEarlyPaymentMaster getEarlyPaymentPlanWithDetails(
			Integer earlyPaymentPlanNo);

	MstbAdminFeeMaster getAdminFeePlanWithDetails(Integer planNo);

	MstbBankMaster getBankWithBranches(Integer bankNo);

	FmtbBankCode getGLBankWithBranches(Integer bankNo);

	FmtbArContCodeMaster getGLControlCodeWithDetails(Integer codeNo);

	MstbAdminFeeDetail getAdminFeePlanDetail(Integer planDetailNo);

	MstbCreditTermDetail getCreditTermPlanDetail(Integer planDetailNo);

	MstbEarlyPaymentDetail getEarlyPaymentPlanDetail(Integer planDetailNo);

	FmtbArContCodeDetail getGLControlCodeDetail(Integer planDetailNo);

	MstbLatePaymentDetail getLatePaymentPlanDetail(Integer planDetailNo);

	MstbSubscFeeDetail getSubscriptionFeePlanDetail(Integer planDetailNo);
	
	MstbIssuanceFeeDetail getIssuanceFeePlanDetail(Integer planDetailNo);

	List<Object[]> getGLBankCodes(SearchGLBankForm form);

	List<FmtbArContCodeMaster> getGLControlCodes(Integer entityNo);

	MstbBranchMaster getBankBranch(Integer branchNo);

	List<FmtbTaxCode> getGLTaxCodes(Integer entityNo, String taxType);

	FmtbTaxCode getGLTaxCode(Integer taxCodeNo);

	List<Object[]> getTransactionCodes(SearchTransactionCodeForm form);

	FmtbTransactionCode getTransactionCode(Integer transactionCodeNo);

	FmtbEntityMaster getEntity(Integer entityNo);

	List<MstbAdminFeeMaster> getAdminFeePlans();

	List<MstbBankMaster> getBanks();

	List<MstbCreditTermMaster> getCreditTermPlans();

	List<MstbEarlyPaymentMaster> getEarlyPaymentPlans();

	List<MstbLatePaymentMaster> getLatePaymentPlans();

	List<MstbProdDiscMaster> getProductDiscountPlans();

	List<MstbSubscFeeMaster> getSubscriptionFeePlans();
	
	List<MstbIssuanceFeeMaster> getIssuanceFeePlans();

	List<MstbVolDiscMaster> getVolumeDiscountPlans();

	List<FmtbEntityMaster> getEntities();
	List<FmtbEntityMaster> getActiveEntities();

	List<MstbSalesperson> getSalesPersons();

	List<PmtbProductType> getProductTypes();

	boolean hasDuplicateEffectiveDate(FmtbTaxCode tax);

	boolean hasDuplicateEffectiveDate(MstbVolDiscDetail detail);

	boolean hasDuplicateEffectiveDate(MstbProdDiscDetail detail);

	boolean hasDuplicateEffectiveDate(MstbAdminFeeDetail detail);

	boolean hasDuplicateEffectiveDate(MstbCreditTermDetail detail);

	boolean hasDuplicateEffectiveDate(MstbSubscFeeDetail detail);

	boolean hasDuplicateEffectiveDate(MstbIssuanceFeeDetail detail);
	
	boolean hasDuplicateEffectiveDate(MstbLatePaymentDetail detail);

	boolean hasDuplicateEffectiveDate(MstbEarlyPaymentDetail detail);

	boolean hasDuplicateEffectiveDate(FmtbTransactionCode transactionCode);

	boolean hasDuplicateName(MstbVolDiscMaster plan);

	boolean hasDuplicateName(MstbProdDiscMaster plan);

	boolean hasDuplicateName(MstbAdminFeeMaster plan);

	boolean hasDuplicateName(MstbCreditTermMaster plan);

	boolean hasDuplicateName(MstbEarlyPaymentMaster plan);

	boolean hasDuplicateName(MstbLatePaymentMaster plan);

	boolean hasDuplicateName(MstbSubscFeeMaster plan);
	
	boolean hasDuplicateName(MstbIssuanceFeeMaster plan);

	boolean hasDuplicateName(MstbSalesperson salesPerson);

	boolean hasDuplicateName(FmtbEntityMaster entity);

	boolean hasDuplicateCode(FmtbEntityMaster entity);

	boolean hasDuplicateCode(MstbBankMaster bank);

	boolean hasDuplicateCode(FmtbBankCode bank);

	boolean hasDuplicateCode(FmtbArContCodeMaster code);

	boolean hasDuplicateCode(MstbBranchMaster branch);
	
	public boolean hasDuplicateName(MstbBranchMaster branch);
	
	public boolean hasDuplicateName(Integer promoNo, String name);
	
	List<MstbPromotion> searchCurrentPromotion(SearchPromotionForm form);
	
	List<MstbPromoReq> searchPromoPlanHistory(SearchPromoPlanHistoryForm form);
	
	List<MstbPromotion> searchLastPromoReq(SearchPromotionForm form);
	
	List<MstbPromoReq> getPendingPromoReq();
	
	public MstbPromoReq getPromoReq(Integer promoReqNo);
	
	List<MstbAcquirer> searchAcquirer(SearchAcquirerForm form);
	
	List<MstbAcquirerPymtType> searchAcquirerPaymentType(SearchAcquirerPaymentTypeForm form);
	
	boolean isAcquirerDeletable(int acquirer_no);
	
	List<MstbAcquirer> getAcquirer();
	
	MstbAcquirerPymtType getAcquirerPaymentType(Integer pymtTypeNo);
	
	boolean hasDuplicateRecord(MstbAcquirerPymtType acquirerPymtType);
	
	List<MstbMasterTable> getPaymentType(MstbAcquirer acquirer);
	
	List<Object[]> searchAcquirerMdr(SearchAcquirerMdrForm form);
	
	boolean hasDuplicateRecord(MstbAcquirerMdr acquirerMdr);
	
	MstbAcquirerMdr getAcquirerMdr(Integer mdrNo);
	
	MstbPromotion getPromotion(Integer promoNo);
	
	public void setBankBranchDefaultNo(Integer entityNo, String userLoginId);
	
	public void setBankBranchDefaultYes(Integer entityNo, String bankCode, String branchCode, String userLoginId);
	
	public boolean hasDuplicateEffectiveDate(FmtbArContCodeDetail detail) ;
	
	public List<FmtbNonBillableMaster> searchNonBillable(SearchNonBillableForm form);
	
	public boolean hasDuplicateRecord(FmtbNonBillableMaster fnbm);
	
	public List<FmtbArContCodeMaster> getARControlCode(Integer entity_no);
	
	public List<String> getTransactionCodeType(Integer entityNo, String txn_type);
	
	FmtbNonBillableMaster getNonBillableMaster(Integer masterNo);
	
	FmtbNonBillableDetail getNonBillableDetail(Integer detailNo);
	
	List<FmtbBankPaymentMaster> searchBankPayment(SearchBankPaymentForm form);
	
	List<FmtbBankPaymentMaster> getBankPaymentByForeignExample(FmtbBankPaymentMaster master);
	
	public boolean hasDuplicateRecord(FmtbNonBillableMaster master, FmtbNonBillableDetail detail);
	
	FmtbBankPaymentMaster getBankPaymentMaster(Integer masterNo);
	
	List<FmtbBankPaymentDetail> getBankPaymentDetailByForeignExample(FmtbBankPaymentDetail detail);
	
	FmtbBankPaymentDetail getBankPaymentDetail(Integer detailNo);
	
	public boolean isRecordDeletable(FmtbEntityMaster master);
	
	public boolean isRecordDeletable(FmtbArContCodeMaster master);
	
	public boolean isRecordDeletable(MstbBankMaster master);
	
	public boolean isRecordDeletable(MstbBranchMaster master);
	
	public boolean isArContCodeBeenUsed(Integer arControlCodeNo);
	
	List<MstbAcquirer> getAcquirerByExample();
	
	public FmtbTransactionCode getEarliestEffectedTxnCode(String txnType, String txnCode);
	
	public List<LrtbRewardAccount> getActiveRewardsAccount();
	
	public FmtbBankCode checkDefaultGiroBankExist(Date effectiveDate, Integer bankCodeNo);
	
	public List<IttbGiroSetup> getActiveGiroSetup();
	
	public List<IttbGiroReq> getPendingGiroRequest(IttbGiroSetup setup);
	
	public FmtbBankCode getLatestEntityBank(FmtbEntityMaster entityMaster);
	
	public  List<IttbGiroReq> getGiroRequest(IttbGiroSetup setup, Date requestDate);
	
	public List<IttbGiroReq> searchGiroRequest(SearchGiroRequestForm form) ;
	
	public IttbGiroReq searchGiroRequest(Long requestNo) ;
	
	public IttbGiroSetup getInvoiceGiroDay(Integer entityNo);
	
	public  List<IttbGiroReq> getExistingGiroRequest(IttbGiroSetup setup);
	
	public FmtbBankCode checkDefaultCollectionBankExist(Integer entityNo, Date effectiveDate, Integer bankCodeNo);
	
	public boolean isGiroReturnFileUploadedBefore(String fileName);
	
	public IttbGiroUobHeader getCorrespondingGiroOutgoingFile(String returnFileName);
	
	public List<IttbGiroReturnReq> searchGiroReturnRequest(SearchGiroReturnRequestForm form) ;
	
	public IttbGiroReturnReq searchGiroReturnRequest(Long requestNo) ;
	
	public List<IttbGovtEinvoiceReq> searchGovtEInvRequest(SearchGovtEInvRequestForm form) ;
	
	public IttbGovtEinvoiceReq searchGovtEInvRequest(Integer requestNo) ;
	
	public List<MstbPromotionCashPlus> searchPrepaidPromotion(SearchPrepaidPromotionForm form);
	
	public boolean hasDuplicateCashPlusPromoCode(String promoCode);
	
	public MstbPromotionCashPlus getPromotionCashPlus(String promoCode);
	
	public boolean isPromotionCashPlusDeletable(String promoCode);
	
	public List<MstbPromotionCashPlus> getEffectivePrepaidPromotions();
	
	public List<MstbInvoicePromo> getListInvoicePromo(String name, Date dateFrom, Date dateTo, String initial);

	public MstbInvoicePromo getInvoicePromo(Integer invoicePromoId);

	public boolean checkPromoNumber(String promoNumber, Timestamp insDate, int promoId);
	
	public List<IttbPubbsReq> searchPubbsRequest(SearchPubbsRequestForm form) ;

	public IttbPubbsReq searchPubbsRequest(Integer requestNo); 
	
	public List<IttbRecurringReq> searchIttbRecurringRequest(SearchRecurringRequestForm form) ;
	
	public IttbRecurringReq searchIttbRecurringRequest(Integer requestNo); 
	
	public IttbRecurringDtl getRecurringDtlwInvoice(String referenceId, String invoiceNo, String tokenId);

}