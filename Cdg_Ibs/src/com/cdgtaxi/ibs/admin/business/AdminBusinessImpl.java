package com.cdgtaxi.ibs.admin.business;

import java.io.File;
import java.io.FileOutputStream;
import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.log4j.Logger;
import org.zkoss.util.media.Media;

import com.cdgtaxi.ibs.common.MasterSetup;
import com.cdgtaxi.ibs.common.NonConfigurableConstants;
import com.cdgtaxi.ibs.common.business.GenericBusinessImpl;
import com.cdgtaxi.ibs.common.exception.DataValidationError;
import com.cdgtaxi.ibs.common.exception.DuplicateCodeError;
import com.cdgtaxi.ibs.common.exception.DuplicateEffectiveDateError;
import com.cdgtaxi.ibs.common.exception.DuplicateNameError;
import com.cdgtaxi.ibs.common.exception.GiroException;
import com.cdgtaxi.ibs.common.exception.GiroRequestExistanceException;
import com.cdgtaxi.ibs.common.exception.GovtEInvException;
import com.cdgtaxi.ibs.common.exception.IncorrectLengthException;
import com.cdgtaxi.ibs.common.exception.PubbsException;
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
import com.cdgtaxi.ibs.common.model.IttbGovtEinvoiceHdrDtl;
import com.cdgtaxi.ibs.common.model.IttbGovtEinvoiceReq;
import com.cdgtaxi.ibs.common.model.IttbPubbsDtl;
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
import com.cdgtaxi.ibs.common.model.govteinv.ControlHeader;
import com.cdgtaxi.ibs.common.model.govteinv.GovtEInvReturn;
import com.cdgtaxi.ibs.common.model.govteinv.InvoiceHeader;
import com.cdgtaxi.ibs.common.model.govteinv.Trailer;
import com.cdgtaxi.ibs.common.model.pubbs.PubbsControlHeader;
import com.cdgtaxi.ibs.common.model.pubbs.PubbsInvoiceHeader;
import com.cdgtaxi.ibs.common.model.pubbs.PubbsReturn;
import com.cdgtaxi.ibs.common.model.pubbs.PubbsReturnTrailer;
import com.cdgtaxi.ibs.common.ui.CommonWindow;
import com.cdgtaxi.ibs.master.model.MstbAcquirer;
import com.cdgtaxi.ibs.master.model.MstbAcquirerMdr;
import com.cdgtaxi.ibs.master.model.MstbAcquirerPymtComm;
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
import com.cdgtaxi.ibs.master.model.MstbPromoDetail;
import com.cdgtaxi.ibs.master.model.MstbPromoReq;
import com.cdgtaxi.ibs.master.model.MstbPromoReqFlow;
import com.cdgtaxi.ibs.master.model.MstbPromotion;
import com.cdgtaxi.ibs.master.model.MstbPromotionCashPlus;
import com.cdgtaxi.ibs.master.model.MstbSalesperson;
import com.cdgtaxi.ibs.master.model.MstbSubscFeeDetail;
import com.cdgtaxi.ibs.master.model.MstbSubscFeeMaster;
import com.cdgtaxi.ibs.master.model.MstbVolDiscDetail;
import com.cdgtaxi.ibs.master.model.MstbVolDiscMaster;
import com.cdgtaxi.ibs.master.model.MstbVolDiscTier;
import com.cdgtaxi.ibs.util.DateUtil;

public class AdminBusinessImpl extends GenericBusinessImpl implements AdminBusiness {
	private static final Logger logger = Logger.getLogger(AdminBusinessImpl.class);

	public void createAdminFeePlan(MstbAdminFeeMaster plan, String userLoginId) throws DuplicateNameError {
		// check for duplicate plan name
		boolean hasDuplicate = daoHelper.getAdminDao().hasDuplicateName(plan);
		if (hasDuplicate) {
			throw new DuplicateNameError();
		}

		daoHelper.getGenericDao().save(plan, userLoginId);
	}

	public void createBank(MstbBankMaster bank, Map<String, String> branchMap, String userLoginId)
			throws DuplicateCodeError {
		// check for duplicate code
		boolean hasDuplicateCode = daoHelper.getAdminDao().hasDuplicateCode(bank);
		if (hasDuplicateCode) {
			throw new DuplicateCodeError();
		}

		daoHelper.getGenericDao().save(bank, userLoginId);

		Set<Entry<String, String>> entries = branchMap.entrySet();
		for (Entry<String, String> entry : entries) {
			MstbBranchMaster branch = new MstbBranchMaster();
			branch.setMstbBankMaster(bank);
			branch.setBranchCode(entry.getKey());
			branch.setBranchName(entry.getValue());
			daoHelper.getGenericDao().save(branch, userLoginId);
		}
	}

	public void createBankPayment(FmtbBankPaymentMaster master, FmtbBankPaymentDetail detail,
			String userLoginId) {
		daoHelper.getGenericDao().save(master, userLoginId);
		daoHelper.getGenericDao().save(detail, userLoginId);
	}

	public void deleteVolumeDiscountPlanDetail(MstbVolDiscDetail detail) {
		if (detail.getMstbVolDiscTiers().size() > 0)
			daoHelper.getGenericDao().deleteAll(detail.getMstbVolDiscTiers());
		daoHelper.getGenericDao().delete(detail);
	}

	public void createNonBillableGL(FmtbNonBillableMaster master, FmtbNonBillableDetail detail,
			String userLoginId) {
		daoHelper.getGenericDao().save(master, userLoginId);
		daoHelper.getGenericDao().save(detail, userLoginId);
	}

	public void createCreditTermPlan(MstbCreditTermMaster plan, String userLoginId) throws DuplicateNameError {
		// check for duplicate plan name
		boolean hasDuplicate = daoHelper.getAdminDao().hasDuplicateName(plan);
		if (hasDuplicate) {
			throw new DuplicateNameError();
		}

		daoHelper.getGenericDao().save(plan, userLoginId);
	}

	public void createEarlyPaymentPlan(MstbEarlyPaymentMaster plan, String userLoginId)
			throws DuplicateNameError {
		// check for duplicate plan name
		boolean hasDuplicate = daoHelper.getAdminDao().hasDuplicateName(plan);
		if (hasDuplicate) {
			throw new DuplicateNameError();
		}

		daoHelper.getGenericDao().save(plan, userLoginId);
	}

	public void createGLBank(FmtbBankCode bank, FmtbBankCode defaultGiroBank, String userLoginId)
			throws DuplicateCodeError {
		// check for duplicate code
		boolean hasDuplicateCode = daoHelper.getAdminDao().hasDuplicateCode(bank);
		if (hasDuplicateCode) {
			throw new DuplicateCodeError(
					"An existing record with same bank code, branch code and effective date exists.");
		}

		if (bank.getIsDefault().equals(NonConfigurableConstants.BOOLEAN_YN_YES)) {
			this.daoHelper.getAdminDao().setBankBranchDefaultNo(bank.getFmtbEntityMaster().getEntityNo(),
					userLoginId);
			this.daoHelper.getAdminDao().setBankBranchDefaultYes(bank.getFmtbEntityMaster().getEntityNo(),
					bank.getBankCode(), bank.getBranchCode(), userLoginId);
		}

		if (defaultGiroBank != null) {
			// Previous codes may have touched the giro bank entity
			defaultGiroBank = (FmtbBankCode) this.daoHelper.getGenericDao().load(defaultGiroBank.getClass(),
					defaultGiroBank.getBankCodeNo());
			defaultGiroBank.setIsDefaultGiroBank(NonConfigurableConstants.BOOLEAN_NO);
			this.update(defaultGiroBank);
		}

		this.save(bank, userLoginId);
	}

	public void createGLControlCode(FmtbArContCodeMaster code, String userLoginId) throws DuplicateCodeError {
		// check for duplicate code
		boolean hasDuplicateCode = daoHelper.getAdminDao().hasDuplicateCode(code);
		if (hasDuplicateCode) {
			throw new DuplicateCodeError();
		}

		daoHelper.getGenericDao().save(code, userLoginId);
	}

	public void createLatePaymentPlan(MstbLatePaymentMaster plan, String userLoginId)
			throws DuplicateNameError {
		// check for duplicate plan name
		boolean hasDuplicate = daoHelper.getAdminDao().hasDuplicateName(plan);
		if (hasDuplicate) {
			throw new DuplicateNameError();
		}

		daoHelper.getGenericDao().save(plan, userLoginId);
	}

	public void createProductDiscountPlan(MstbProdDiscMaster plan, String userLoginId)
			throws DuplicateNameError {
		// check for duplicate plan name
		boolean hasDuplicate = daoHelper.getAdminDao().hasDuplicateName(plan);
		if (hasDuplicate) {
			throw new DuplicateNameError();
		}

		daoHelper.getGenericDao().save(plan, userLoginId);
	}

	public void createSubscriptionFeePlan(MstbSubscFeeMaster plan, String userLoginId)
			throws DuplicateNameError {
		// check for duplicate plan name
		boolean hasDuplicate = daoHelper.getAdminDao().hasDuplicateName(plan);
		if (hasDuplicate) {
			throw new DuplicateNameError();
		}
		daoHelper.getGenericDao().save(plan, userLoginId);
	}
	
	public void createIssuanceFeePlan(MstbIssuanceFeeMaster plan, String userLoginId)
			throws DuplicateNameError {
		// check for duplicate plan name
		boolean hasDuplicate = daoHelper.getAdminDao().hasDuplicateName(plan);
		if (hasDuplicate) {
			throw new DuplicateNameError();
		}
		daoHelper.getGenericDao().save(plan, userLoginId);
	}

	public void createVolumeDiscountPlan(MstbVolDiscMaster plan, String userLoginId)
			throws DuplicateNameError {
		// check for duplicate plan name
		boolean hasDuplicate = daoHelper.getAdminDao().hasDuplicateName(plan);
		if (hasDuplicate) {
			throw new DuplicateNameError();
		}

		daoHelper.getGenericDao().save(plan, userLoginId);
	}

	public void deleteAdminFeePlan(MstbAdminFeeMaster plan) {
		daoHelper.getGenericDao().delete(plan);
	}

	public void deleteBank(MstbBankMaster bank) {
		daoHelper.getGenericDao().delete(bank);
	}

	public void deleteCreditTermPlan(MstbCreditTermMaster plan) {
		daoHelper.getGenericDao().delete(plan);
	}

	public void deleteEarlyPaymentPlan(MstbEarlyPaymentMaster plan) {
		daoHelper.getGenericDao().delete(plan);
	}

	public void deleteGLBankCode(FmtbBankCode bank) {
		daoHelper.getGenericDao().delete(bank);
	}

	public void deleteGLControlCode(FmtbArContCodeMaster code) {
		daoHelper.getGenericDao().delete(code);
	}

	public void deleteLatePaymentPlan(MstbLatePaymentMaster plan) {
		daoHelper.getGenericDao().delete(plan);
	}

	public void deleteProductDiscountPlan(MstbProdDiscMaster plan) {
		daoHelper.getGenericDao().delete(plan);
	}

	public void deleteSubscriptionFeePlan(MstbSubscFeeMaster plan) {
		daoHelper.getGenericDao().delete(plan);
	}
	
	public void deleteIssuanceFeePlan(MstbIssuanceFeeMaster plan) {
		daoHelper.getGenericDao().delete(plan);
	}

	public void deleteVolumeDiscountPlan(MstbVolDiscMaster plan) {
		daoHelper.getGenericDao().delete(plan);
	}

	public MstbAdminFeeMaster getAdminFeePlan(Integer planNo) {
		return daoHelper.getAdminDao().getAdminFeePlanWithDetails(planNo);
	}

	public MstbAdminFeeDetail getAdminFeePlanDetail(Integer planDetailNo) {
		return daoHelper.getAdminDao().getAdminFeePlanDetail(planDetailNo);
	}

	public List<MstbAdminFeeMaster> getAdminFeePlans() {
		return daoHelper.getAdminDao().getAdminFeePlans();
	}

	public MstbBankMaster getBank(Integer bankNo) {
		return daoHelper.getAdminDao().getBankWithBranches(bankNo);
	}

	public MstbBranchMaster getBankBranch(Integer branchNo) {
		return daoHelper.getAdminDao().getBankBranch(branchNo);
	}

	public List<MstbBankMaster> getBanks() {
		return daoHelper.getAdminDao().getBanks();
	}

	public MstbCreditTermMaster getCreditTermPlan(Integer creditTermPlanNo) {
		return daoHelper.getAdminDao().getCreditTermPlanWithDetails(creditTermPlanNo);
	}

	public MstbCreditTermDetail getCreditTermPlanDetail(Integer planDetailNo) {
		return daoHelper.getAdminDao().getCreditTermPlanDetail(planDetailNo);
	}

	public List<MstbCreditTermMaster> getCreditTermPlans() {
		return daoHelper.getAdminDao().getCreditTermPlans();
	}

	public MstbEarlyPaymentMaster getEarlyPaymentPlan(Integer earlyPaymentPlanNo) {
		return daoHelper.getAdminDao().getEarlyPaymentPlanWithDetails(earlyPaymentPlanNo);
	}

	public MstbEarlyPaymentDetail getEarlyPaymentPlanDetail(Integer planDetailNo) {
		return daoHelper.getAdminDao().getEarlyPaymentPlanDetail(planDetailNo);
	}

	public List<MstbEarlyPaymentMaster> getEarlyPaymentPlans() {
		return daoHelper.getAdminDao().getEarlyPaymentPlans();
	}

	public FmtbBankCode getGLBank(Integer codeNo) {
		return daoHelper.getAdminDao().getGLBankWithBranches(codeNo);
	}

	public List<Object[]> getGLBankCodes(SearchGLBankForm form) {
		return daoHelper.getAdminDao().getGLBankCodes(form);
	}

	public FmtbArContCodeMaster getGLControlCode(Integer codeNo) {
		return daoHelper.getAdminDao().getGLControlCodeWithDetails(codeNo);
	}

	public FmtbArContCodeDetail getGLControlCodeDetail(Integer planDetailNo) {
		return daoHelper.getAdminDao().getGLControlCodeDetail(planDetailNo);
	}

	public List<FmtbArContCodeMaster> getGLControlCodes(Integer entityNo) {
		return daoHelper.getAdminDao().getGLControlCodes(entityNo);
	}

	public MstbLatePaymentMaster getLatePaymentPlan(Integer latePaymentPlanNo) {
		return daoHelper.getAdminDao().getLatePaymentPlanWithDetails(latePaymentPlanNo);
	}

	public MstbLatePaymentDetail getLatePaymentPlanDetail(Integer planDetailNo) {
		return daoHelper.getAdminDao().getLatePaymentPlanDetail(planDetailNo);
	}

	public List<MstbLatePaymentMaster> getLatePaymentPlans() {
		return daoHelper.getAdminDao().getLatePaymentPlans();
	}

	public MstbProdDiscMaster getProductDiscountPlan(Integer planNo) {
		return daoHelper.getAdminDao().getProductDiscountPlanWithDetails(planNo);
	}

	public MstbProdDiscDetail getProductDiscountPlanDetail(Integer planDetailNo) {
		return daoHelper.getAdminDao().getProductDiscountPlanDetail(planDetailNo);
	}

	public List<MstbProdDiscMaster> getProductDiscountPlans() {
		return daoHelper.getAdminDao().getProductDiscountPlans();
	}

	public MstbSubscFeeMaster getSubscriptionFeePlan(Integer subscriptionFeeNo) {
		return daoHelper.getAdminDao().getSubscriptionFeePlanWithDetails(subscriptionFeeNo);
	}

	public MstbSubscFeeDetail getSubscriptionFeePlanDetail(Integer planDetailNo) {
		return daoHelper.getAdminDao().getSubscriptionFeePlanDetail(planDetailNo);
	}

	public List<MstbSubscFeeMaster> getSubscriptionFeePlans() {
		return daoHelper.getAdminDao().getSubscriptionFeePlans();
	}
	
	public MstbIssuanceFeeMaster getIssuanceFeePlan(Integer insuanceFeeNo) {
		return daoHelper.getAdminDao().getIssuanceFeePlanWithDetails(insuanceFeeNo);
	}
	
	public MstbIssuanceFeeDetail getIssuanceFeePlanDetail(Integer planDetailNo) {
		return daoHelper.getAdminDao().getIssuanceFeePlanDetail(planDetailNo);
	}
	public List<MstbIssuanceFeeMaster> getIssuanceFeePlans() {
		return daoHelper.getAdminDao().getIssuanceFeePlans();
	}
	
	public MstbVolDiscMaster getVolumeDiscountPlan(Integer planNo) {
		return daoHelper.getAdminDao().getVolumeDiscountPlanWithDetails(planNo);
	}

	public MstbVolDiscDetail getVolumeDiscountPlanDetail(Integer planDetailNo) {
		return daoHelper.getAdminDao().getVolumeDiscountPlanDetailWithTiers(planDetailNo);
	}

	public List<MstbVolDiscMaster> getVolumeDiscountPlans() {
		return daoHelper.getAdminDao().getVolumeDiscountPlans();
	}

	public void saveAdminFeePlanDetail(MstbAdminFeeDetail detail, String userLoginId)
			throws DuplicateEffectiveDateError {
		// check for plan-effective date
		boolean hasDuplicate = daoHelper.getAdminDao().hasDuplicateEffectiveDate(detail);
		if (hasDuplicate) {
			throw new DuplicateEffectiveDateError();
		}

		daoHelper.getGenericDao().save(detail, userLoginId);
	}

	public void saveBankBranch(MstbBranchMaster branch, String userLoginId) throws DuplicateCodeError,
			DuplicateNameError {
		// check for duplicate code
		boolean hasDuplicateCode = daoHelper.getAdminDao().hasDuplicateCode(branch);
		if (hasDuplicateCode) {
			throw new DuplicateCodeError();
		}

		// check for duplicate name
		if (daoHelper.getAdminDao().hasDuplicateName(branch))
			throw new DuplicateNameError();

		daoHelper.getGenericDao().save(branch, userLoginId);
	}

	public void saveCreditTermPlanDetail(MstbCreditTermDetail detail, String userLoginId)
			throws DuplicateEffectiveDateError {
		// check for plan-effective date
		boolean hasDuplicate = daoHelper.getAdminDao().hasDuplicateEffectiveDate(detail);
		if (hasDuplicate) {
			throw new DuplicateEffectiveDateError();
		}

		daoHelper.getGenericDao().save(detail, userLoginId);
	}

	public void saveEarlyPaymentPlanDetail(MstbEarlyPaymentDetail detail, String userLoginId)
			throws DuplicateEffectiveDateError {
		// check for plan-effective date
		boolean hasDuplicate = daoHelper.getAdminDao().hasDuplicateEffectiveDate(detail);
		if (hasDuplicate) {
			throw new DuplicateEffectiveDateError();
		}

		daoHelper.getGenericDao().save(detail, userLoginId);
	}

	public void saveGLControlCodeDetail(FmtbArContCodeDetail detail, String userLoginId)
			throws DuplicateCodeError {
		if (this.daoHelper.getAdminDao().hasDuplicateEffectiveDate(detail))
			throw new DuplicateCodeError("An existing record with same effective date exists.");

		this.save(detail, userLoginId);
		FmtbArContCodeMaster master = detail.getFmtbArContCodeMaster();
		master.getFmtbArContCodeDetails().add(detail);
		this.update(master, userLoginId);
	}

	public void saveLatePaymentPlanDetail(MstbLatePaymentDetail detail, String userLoginId)
			throws DuplicateEffectiveDateError {
		// check for plan-effective date
		boolean hasDuplicate = daoHelper.getAdminDao().hasDuplicateEffectiveDate(detail);
		if (hasDuplicate) {
			throw new DuplicateEffectiveDateError();
		}

		daoHelper.getGenericDao().save(detail, userLoginId);
	}

	public void saveProductDiscountPlanDetail(MstbProdDiscDetail detail, String userLoginId)
			throws DuplicateEffectiveDateError {
		// check for plan-effective date
		boolean hasDuplicate = daoHelper.getAdminDao().hasDuplicateEffectiveDate(detail);
		if (hasDuplicate) {
			throw new DuplicateEffectiveDateError();
		}

		daoHelper.getGenericDao().save(detail, userLoginId);
	}

	public void saveSubscriptionFeePlanDetail(MstbSubscFeeDetail detail, String userLoginId)
			throws DuplicateEffectiveDateError {
		// check for plan-effective date
		boolean hasDuplicate = daoHelper.getAdminDao().hasDuplicateEffectiveDate(detail);
		if (hasDuplicate) {
			throw new DuplicateEffectiveDateError();
		}

		daoHelper.getGenericDao().save(detail, userLoginId);
	}
	
	public void saveIssuanceFeePlanDetail(MstbIssuanceFeeDetail detail, String userLoginId)
			throws DuplicateEffectiveDateError {
	// check for plan-effective date
		boolean hasDuplicate = daoHelper.getAdminDao().hasDuplicateEffectiveDate(detail);
		if (hasDuplicate) {
			throw new DuplicateEffectiveDateError();
		}
		daoHelper.getGenericDao().save(detail, userLoginId);
	}

	public void saveVolumeDiscountPlanDetail(MstbVolDiscDetail detail, String userLoginId)
			throws DuplicateEffectiveDateError {
		// check for plan-effective date
		boolean hasDuplicate = daoHelper.getAdminDao().hasDuplicateEffectiveDate(detail);
		if (hasDuplicate) {
			throw new DuplicateEffectiveDateError();
		}

		daoHelper.getGenericDao().save(detail, userLoginId);
	}

	public void updateAdminFeePlan(MstbAdminFeeMaster plan, String userLoginId) throws DuplicateNameError {
		// check for duplicate plan name
		boolean hasDuplicate = daoHelper.getAdminDao().hasDuplicateName(plan);
		if (hasDuplicate) {
			throw new DuplicateNameError();
		}

		daoHelper.getGenericDao().update(plan, userLoginId);
	}

	public void updateAdminFeePlanDetail(MstbAdminFeeDetail detail, String userLoginId)
			throws DuplicateEffectiveDateError {
		// check for plan-effective date
		boolean hasDuplicate = daoHelper.getAdminDao().hasDuplicateEffectiveDate(detail);
		if (hasDuplicate) {
			throw new DuplicateEffectiveDateError();
		}

		daoHelper.getGenericDao().update(detail, userLoginId);
	}

	public void updateBank(MstbBankMaster bank, String userLoginId) throws DuplicateCodeError {
		// check for duplicate code
		boolean hasDuplicateCode = daoHelper.getAdminDao().hasDuplicateCode(bank);
		if (hasDuplicateCode) {
			throw new DuplicateCodeError();
		}

		daoHelper.getGenericDao().update(bank, userLoginId);
	}

	public void updateBankBranch(MstbBranchMaster branch, String userLoginId) throws DuplicateCodeError,
			DuplicateNameError {
		// check for duplicate code
		boolean hasDuplicateCode = daoHelper.getAdminDao().hasDuplicateCode(branch);
		if (hasDuplicateCode) {
			throw new DuplicateCodeError();
		}

		// check for duplicate name
		if (daoHelper.getAdminDao().hasDuplicateName(branch))
			throw new DuplicateNameError();

		daoHelper.getGenericDao().update(branch, userLoginId);
	}

	public void updateCreditTermPlan(MstbCreditTermMaster plan, String userLoginId) throws DuplicateNameError {
		// check for duplicate plan name
		boolean hasDuplicate = daoHelper.getAdminDao().hasDuplicateName(plan);
		if (hasDuplicate) {
			throw new DuplicateNameError();
		}

		daoHelper.getGenericDao().update(plan, userLoginId);
	}

	public void updateCreditTermPlanDetail(MstbCreditTermDetail detail, String userLoginId)
			throws DuplicateEffectiveDateError {
		// check for plan-effective date
		boolean hasDuplicate = daoHelper.getAdminDao().hasDuplicateEffectiveDate(detail);
		if (hasDuplicate) {
			throw new DuplicateEffectiveDateError();
		}

		daoHelper.getGenericDao().update(detail, userLoginId);
	}

	public void updateEarlyPaymentPlan(MstbEarlyPaymentMaster plan, String userLoginId)
			throws DuplicateNameError {
		// check for duplicate plan name
		boolean hasDuplicate = daoHelper.getAdminDao().hasDuplicateName(plan);
		if (hasDuplicate) {
			throw new DuplicateNameError();
		}

		daoHelper.getGenericDao().update(plan, userLoginId);
	}

	public void updateEarlyPaymentPlanDetail(MstbEarlyPaymentDetail detail, String userLoginId)
			throws DuplicateEffectiveDateError {
		// check for plan-effective date
		boolean hasDuplicate = daoHelper.getAdminDao().hasDuplicateEffectiveDate(detail);
		if (hasDuplicate) {
			throw new DuplicateEffectiveDateError();
		}

		daoHelper.getGenericDao().update(detail, userLoginId);
	}

	public void updateGLBank(FmtbBankCode bank, FmtbBankCode defaultGiroBank, String userLoginId)
			throws DuplicateCodeError {
		// check for duplicate bank code
		boolean hasDuplicate = daoHelper.getAdminDao().hasDuplicateCode(bank);
		if (hasDuplicate) {
			throw new DuplicateCodeError(
					"An existing record with same bank code, branch code and effective date exists.");
		}

		this.update(bank, userLoginId);

		// This section of codes need to be after the update because the bank might get updated again
		// causing: a different object with the same identifier value was already associated with the session
		if (bank.getIsDefault().equals(NonConfigurableConstants.BOOLEAN_YN_YES)) {
			this.daoHelper.getAdminDao().setBankBranchDefaultNo(bank.getFmtbEntityMaster().getEntityNo(),
					userLoginId);
			this.daoHelper.getAdminDao().setBankBranchDefaultYes(bank.getFmtbEntityMaster().getEntityNo(),
					bank.getBankCode(), bank.getBranchCode(), userLoginId);
		}

		if (defaultGiroBank != null) {
			// Previous codes may have touched the giro bank entity
			defaultGiroBank = (FmtbBankCode) this.daoHelper.getGenericDao().load(defaultGiroBank.getClass(),
					defaultGiroBank.getBankCodeNo());
			defaultGiroBank.setIsDefaultGiroBank(NonConfigurableConstants.BOOLEAN_NO);
			this.update(defaultGiroBank);
		}
	}

	public void updateGLControlCode(FmtbArContCodeMaster code, String userLoginId) throws DuplicateCodeError {
		// check for duplicate code
		boolean hasDuplicateCode = daoHelper.getAdminDao().hasDuplicateCode(code);
		if (hasDuplicateCode) {
			throw new DuplicateCodeError();
		}

		daoHelper.getGenericDao().update(code, userLoginId);
	}

	public void updateGLControlCodeDetail(FmtbArContCodeDetail detail, String userLoginId)
			throws DuplicateCodeError {
		if (this.daoHelper.getAdminDao().hasDuplicateEffectiveDate(detail))
			throw new DuplicateCodeError("An existing record with same effective date exists.");

		daoHelper.getGenericDao().update(detail, userLoginId);
	}

	public void updateLatePaymentPlan(MstbLatePaymentMaster plan, String userLoginId)
			throws DuplicateNameError {
		// check for duplicate plan name
		boolean hasDuplicate = daoHelper.getAdminDao().hasDuplicateName(plan);
		if (hasDuplicate) {
			throw new DuplicateNameError();
		}

		daoHelper.getGenericDao().update(plan, userLoginId);
	}

	public void updateLatePaymentPlanDetail(MstbLatePaymentDetail detail, String userLoginId)
			throws DuplicateEffectiveDateError {
		// check for plan-effective date
		boolean hasDuplicate = daoHelper.getAdminDao().hasDuplicateEffectiveDate(detail);
		if (hasDuplicate) {
			throw new DuplicateEffectiveDateError();
		}

		daoHelper.getGenericDao().update(detail, userLoginId);
	}

	public void updateProductDiscountPlan(MstbProdDiscMaster plan, String userLoginId)
			throws DuplicateNameError {
		// check for duplicate plan name
		boolean hasDuplicate = daoHelper.getAdminDao().hasDuplicateName(plan);
		if (hasDuplicate) {
			throw new DuplicateNameError();
		}

		daoHelper.getGenericDao().update(plan, userLoginId);
	}

	public void updateProductDiscountPlanDetail(MstbProdDiscDetail detail, String userLoginId)
			throws DuplicateEffectiveDateError {
		// check for plan-effective date
		boolean hasDuplicate = daoHelper.getAdminDao().hasDuplicateEffectiveDate(detail);
		if (hasDuplicate) {
			throw new DuplicateEffectiveDateError();
		}

		daoHelper.getGenericDao().update(detail, userLoginId);
	}

	public void updateSubscriptionFeePlan(MstbSubscFeeMaster plan, String userLoginId)
			throws DuplicateNameError {
		// check for duplicate plan name
		boolean hasDuplicate = daoHelper.getAdminDao().hasDuplicateName(plan);
		if (hasDuplicate) {
			throw new DuplicateNameError();
		}

		daoHelper.getGenericDao().update(plan, userLoginId);
	}
	
	public void updateIssuanceFeePlan(MstbIssuanceFeeMaster plan, String userLoginId)
		throws DuplicateNameError {
		// check for duplicate plan name
		boolean hasDuplicate = daoHelper.getAdminDao().hasDuplicateName(plan);
		if (hasDuplicate) {
			throw new DuplicateNameError();
		}
		daoHelper.getGenericDao().update(plan, userLoginId);
	}

	public void updateSubscriptionFeePlanDetail(MstbSubscFeeDetail detail, String userLoginId)
			throws DuplicateEffectiveDateError {
		// check for plan-effective date
		boolean hasDuplicate = daoHelper.getAdminDao().hasDuplicateEffectiveDate(detail);
		if (hasDuplicate) {
			throw new DuplicateEffectiveDateError();
		}

		daoHelper.getGenericDao().update(detail, userLoginId);
	}
	
	public void updateIssuanceFeePlanDetail(MstbIssuanceFeeDetail detail, String userLoginId)
			throws DuplicateEffectiveDateError {
		// check for plan-effective date
		boolean hasDuplicate = daoHelper.getAdminDao().hasDuplicateEffectiveDate(detail);
		if (hasDuplicate) {
			throw new DuplicateEffectiveDateError();
		}
	
		daoHelper.getGenericDao().update(detail, userLoginId);
	}

	public void updateVolumeDiscountPlan(MstbVolDiscMaster plan, String userLoginId)
			throws DuplicateNameError {
		// check for duplicate plan name
		boolean hasDuplicate = daoHelper.getAdminDao().hasDuplicateName(plan);
		if (hasDuplicate) {
			throw new DuplicateNameError();
		}

		daoHelper.getGenericDao().update(plan, userLoginId);
	}

	public void updateVolumeDiscountPlanDetail(MstbVolDiscDetail detail, Set<MstbVolDiscTier> removedTiers,
			String userLoginId) throws DuplicateEffectiveDateError {
		// check for plan-effective date
		boolean hasDuplicate = daoHelper.getAdminDao().hasDuplicateEffectiveDate(detail);
		if (hasDuplicate) {
			throw new DuplicateEffectiveDateError();
		}

		if (!removedTiers.isEmpty())
			this.daoHelper.getGenericDao().deleteAll(removedTiers);
		daoHelper.getGenericDao().update(detail, userLoginId);
	}

	public List<FmtbEntityMaster> getEntities() {
		return daoHelper.getAdminDao().getEntities();
	}

	public List<FmtbEntityMaster> getActiveEntities() {
		return daoHelper.getAdminDao().getActiveEntities();
	}

	public void deleteGLTaxCode(FmtbTaxCode code) {
		daoHelper.getGenericDao().delete(code);
	}

	public List<FmtbTaxCode> getGLTaxCodes(Integer entityNo, String taxType) {
		return daoHelper.getAdminDao().getGLTaxCodes(entityNo, taxType);
	}

	public void createGLTax(FmtbTaxCode tax, String userLoginId) throws DuplicateEffectiveDateError {
		// check for duplicate entity-tax type-effective date
		boolean hasDuplicate = daoHelper.getAdminDao().hasDuplicateEffectiveDate(tax);
		if (hasDuplicate) {
			throw new DuplicateEffectiveDateError();
		}

		daoHelper.getGenericDao().save(tax, userLoginId);
	}

	public FmtbTaxCode getGLTax(Integer taxCodeNo) {
		return daoHelper.getAdminDao().getGLTaxCode(taxCodeNo);
	}

	public void updateGLTax(FmtbTaxCode tax, String userLoginId) throws DuplicateEffectiveDateError {
		// check for duplicate entity-tax type-effective date
		boolean hasDuplicate = daoHelper.getAdminDao().hasDuplicateEffectiveDate(tax);
		if (hasDuplicate) {
			throw new DuplicateEffectiveDateError();
		}

		daoHelper.getGenericDao().update(tax, userLoginId);
	}

	public void deleteSalesPerson(MstbSalesperson salesPerson) {
		daoHelper.getGenericDao().delete(salesPerson);
	}

	public List<MstbSalesperson> getSalesPersons() {
		return daoHelper.getAdminDao().getSalesPersons();
	}

	public void createSalesPerson(MstbSalesperson salesPerson, String userLoginId) throws DuplicateNameError {
		// check for duplicate plan name
		boolean hasDuplicate = daoHelper.getAdminDao().hasDuplicateName(salesPerson);
		if (hasDuplicate) {
			throw new DuplicateNameError();
		}

		daoHelper.getGenericDao().save(salesPerson, userLoginId);
	}

	public MstbSalesperson getSalesPerson(Integer salesPersonNo) {
		return (MstbSalesperson) daoHelper.getGenericDao().get(MstbSalesperson.class, salesPersonNo);
	}

	public void updateSalesPerson(MstbSalesperson salesPerson, String userLoginId) throws DuplicateNameError {
		// check for duplicate plan name
		boolean hasDuplicate = daoHelper.getAdminDao().hasDuplicateName(salesPerson);
		if (hasDuplicate) {
			throw new DuplicateNameError();
		}

		daoHelper.getGenericDao().update(salesPerson, userLoginId);
	}

	public List<Object[]> getTransactionCodes(SearchTransactionCodeForm form) {
		return daoHelper.getAdminDao().getTransactionCodes(form);
	}

	public List<PmtbProductType> getProductTypes() {
		return daoHelper.getAdminDao().getProductTypes();
	}

	public FmtbTransactionCode getTransactionCode(Integer transactionCodeNo) {
		return daoHelper.getAdminDao().getTransactionCode(transactionCodeNo);
	}

	public void updateTransactionCode(FmtbTransactionCode transactionCode, String userLoginId)
			throws DuplicateEffectiveDateError {
		// check for duplicate entity-tax type-effective date
		boolean hasDuplicate = daoHelper.getAdminDao().hasDuplicateEffectiveDate(transactionCode);
		if (hasDuplicate) {
			throw new DuplicateEffectiveDateError();
		}

		daoHelper.getGenericDao().update(transactionCode, userLoginId);
	}

	public void createTransactionCode(FmtbTransactionCode transactionCode, String userLoginId)
			throws DuplicateEffectiveDateError {
		// check for duplicate entity-tax type-effective date
		boolean hasDuplicate = daoHelper.getAdminDao().hasDuplicateEffectiveDate(transactionCode);
		if (hasDuplicate) {
			throw new DuplicateEffectiveDateError();
		}

		daoHelper.getGenericDao().save(transactionCode, userLoginId);
	}

	public FmtbEntityMaster getEntity(Integer entityNo) {
		return daoHelper.getAdminDao().getEntity(entityNo);
	}

	public void updateEntity(FmtbEntityMaster entity, String userLoginId) throws DataValidationError {
		// check for duplicate plan name
		boolean hasDuplicate = daoHelper.getAdminDao().hasDuplicateName(entity);
		if (hasDuplicate) {
			throw new DuplicateNameError();
		}

		// check for duplicate code
		boolean hasDuplicateCode = daoHelper.getAdminDao().hasDuplicateCode(entity);
		if (hasDuplicateCode) {
			throw new DuplicateCodeError();
		}

		daoHelper.getGenericDao().update(entity, userLoginId);
	}

	public void createEntity(FmtbEntityMaster entity, String userLoginId) throws DataValidationError {
		// check for duplicate plan name
		boolean hasDuplicate = daoHelper.getAdminDao().hasDuplicateName(entity);
		if (hasDuplicate) {
			throw new DuplicateNameError();
		}

		// check for duplicate code
		boolean hasDuplicateCode = daoHelper.getAdminDao().hasDuplicateCode(entity);
		if (hasDuplicateCode) {
			throw new DuplicateCodeError();
		}

		daoHelper.getGenericDao().save(entity, userLoginId);
	}

	public boolean hasDuplicateName(Integer promoNo, String name) {
		return this.daoHelper.getAdminDao().hasDuplicateName(promoNo, name);
	}
	
	public boolean hasDuplicateCashPlusPromoCode(String promoNo) {
		return this.daoHelper.getAdminDao().hasDuplicateCashPlusPromoCode(promoNo);
	}
	
	public List<MstbPromotion> searchCurrentPromotion(SearchPromotionForm form) {
		return this.daoHelper.getAdminDao().searchCurrentPromotion(form);
	}
	
	public List<MstbPromotion> searchLastPromoReq(SearchPromotionForm form) {
		return this.daoHelper.getAdminDao().searchLastPromoReq(form);
	}
	
	public List<MstbPromotionCashPlus> searchPrepaidPromotion(SearchPrepaidPromotionForm form) {
		return this.daoHelper.getAdminDao().searchPrepaidPromotion(form);
	}
	
	
	public List<MstbPromoReq> searchPromoPlanHistory(SearchPromoPlanHistoryForm form) {
		return this.daoHelper.getAdminDao().searchPromoPlanHistory(form);
	}
	
	public List<MstbPromoReq> getPendingPromoReq(){
		return this.daoHelper.getAdminDao().getPendingPromoReq();
	}
	
	public void approvePromoReq(List<MstbPromoReq> reqs, String remarks){
		
		for(MstbPromoReq req: reqs){
			
			String event = req.getEvent();
			MstbPromoReqFlow pendingReqFlow = req.getLastPromoReqFlow();
			MstbPromoDetail toBePromoDetail = pendingReqFlow.getToPromoDetail();
			
			String reqFromStatus=null, reqToStatus=null, fromStatus=null, toStatus=null;
			
			if(NonConfigurableConstants.PROMOTION_EVENT_CREATE.equals(event) || 
				NonConfigurableConstants.PROMOTION_EVENT_EDIT.equals(event) ||
				NonConfigurableConstants.PROMOTION_EVENT_DELETE.equals(event)) {
				
				reqFromStatus 	= NonConfigurableConstants.PROMOTION_REQUEST_STATUS_PENDING;
				reqToStatus 	= NonConfigurableConstants.PROMOTION_REQUEST_STATUS_APPROVED;
				
				if(NonConfigurableConstants.PROMOTION_EVENT_CREATE.equals(event)){
					
					fromStatus 	= NonConfigurableConstants.PROMOTION_STATUS_PENDING_APPROVAL_CREATE;
					toStatus 	= NonConfigurableConstants.PROMOTION_STATUS_ACTIVE;
					
				} 
				else if(NonConfigurableConstants.PROMOTION_EVENT_EDIT.equals(event)) {
					
					fromStatus 	= NonConfigurableConstants.PROMOTION_STATUS_PENDING_APPROVAL_EDIT;
					toStatus 	= NonConfigurableConstants.PROMOTION_STATUS_ACTIVE;
					
				}
				else if(NonConfigurableConstants.PROMOTION_EVENT_DELETE.equals(event)) {
					
					fromStatus 	= NonConfigurableConstants.PROMOTION_STATUS_PENDING_APPROVAL_DELETE;
					toStatus 	= NonConfigurableConstants.PROMOTION_STATUS_DELETED;
					
				}
					
				MstbPromoReqFlow reqFlow = new MstbPromoReqFlow();
				reqFlow.setMstbPromotionReq(req);
				reqFlow.setReqFromStatus(reqFromStatus);
				reqFlow.setReqToStatus(reqToStatus);
				reqFlow.setFromStatus(fromStatus);
				reqFlow.setToStatus(toStatus);
				reqFlow.setToPromoDetail(toBePromoDetail);
				reqFlow.setRemarks(remarks);
				this.save(reqFlow, CommonWindow.getUserLoginIdAndDomain());	
					
				req.setLastPromoReqFlow(reqFlow);
				this.update(req);	
				
				MstbPromotion promotion = req.getMstbPromotion();
				promotion.setCurrentPromoDetail(toBePromoDetail);
				promotion.setCurrentStatus(toStatus);
				promotion.setLastUpdatedBy(req.getRequestBy());
				promotion.setLastUpdatedDt(req.getRequestDt());
								
				this.update(promotion);
			}
		}
		
		MasterSetup.getPromotionManager().refresh();
		
	}
	
	public void rejectPromoReq(List<MstbPromoReq> reqs, String remarks){
		
		for(MstbPromoReq req: reqs){
			
			String event = req.getEvent();
			MstbPromoReqFlow pendingReqFlow = req.getLastPromoReqFlow();
			MstbPromoDetail toPromoDetail = null;
			
			
			String reqFromStatus=null, reqToStatus=null, fromStatus=null, toStatus=null;
			
			
			if(NonConfigurableConstants.PROMOTION_EVENT_CREATE.equals(event) || 
				NonConfigurableConstants.PROMOTION_EVENT_EDIT.equals(event) ||
				NonConfigurableConstants.PROMOTION_EVENT_DELETE.equals(event)) {
				
				reqFromStatus 	= NonConfigurableConstants.PROMOTION_REQUEST_STATUS_PENDING;
				reqToStatus 	= NonConfigurableConstants.PROMOTION_REQUEST_STATUS_REJECTED;
				
				if(NonConfigurableConstants.PROMOTION_EVENT_CREATE.equals(event)){
					
					fromStatus 	= NonConfigurableConstants.PROMOTION_STATUS_PENDING_APPROVAL_CREATE;
					toStatus 	= NonConfigurableConstants.PROMOTION_STATUS_DELETED;
					toPromoDetail = pendingReqFlow.getToPromoDetail();
			
					MstbPromotion promotion = req.getMstbPromotion();
					promotion.setCurrentPromoDetail(toPromoDetail);
					promotion.setCurrentStatus(toStatus);
					this.update(promotion);
				} 
				else if(NonConfigurableConstants.PROMOTION_EVENT_EDIT.equals(event)) {
					
					fromStatus 	= NonConfigurableConstants.PROMOTION_STATUS_PENDING_APPROVAL_EDIT;
					toStatus 	= NonConfigurableConstants.PROMOTION_STATUS_ACTIVE;
					toPromoDetail = req.getFromPromoDetail();
					
					//don't need to update promotion as edit request being rejected
					
				}
				else if(NonConfigurableConstants.PROMOTION_EVENT_DELETE.equals(event)) {
					
					fromStatus 	= NonConfigurableConstants.PROMOTION_STATUS_PENDING_APPROVAL_DELETE;
					toStatus 	= NonConfigurableConstants.PROMOTION_STATUS_ACTIVE;
					toPromoDetail = req.getFromPromoDetail();
					
					//don't need to update promotion as delete request being rejected
				}
				
				MstbPromoReqFlow reqFlow = new MstbPromoReqFlow();
				reqFlow.setMstbPromotionReq(req);
				reqFlow.setReqFromStatus(reqFromStatus);
				reqFlow.setReqToStatus(reqToStatus);
				reqFlow.setFromStatus(fromStatus);
				reqFlow.setToPromoDetail(toPromoDetail);
				reqFlow.setRemarks(remarks);
				reqFlow.setToStatus(toStatus);
				this.save(reqFlow, CommonWindow.getUserLoginIdAndDomain());	
					
				req.setLastPromoReqFlow(reqFlow);
				this.update(req);	
				
				
			}
		}
		
		MasterSetup.getPromotionManager().refresh();

		
	}

	public List<MstbAcquirer> searchAcquirer(SearchAcquirerForm form) {
		return this.daoHelper.getAdminDao().searchAcquirer(form);
	}

	public List<MstbAcquirerPymtType> searchAcquirerPaymentType(SearchAcquirerPaymentTypeForm form) {
		return this.daoHelper.getAdminDao().searchAcquirerPaymentType(form);
	}

	public boolean isAcquirerDeletable(int acquirer_no) {
		return this.daoHelper.getAdminDao().isAcquirerDeletable(acquirer_no);
	}

	public List<MstbAcquirer> getAcquirer() {
		return this.daoHelper.getAdminDao().getAcquirer();
	}

	public boolean hasDuplicateRecord(MstbAcquirerPymtType acquirerPymtType) {
		return this.daoHelper.getAdminDao().hasDuplicateRecord(acquirerPymtType);
	}

	public boolean hasDuplicateRecord(FmtbNonBillableMaster master, FmtbNonBillableDetail detail) {
		return this.daoHelper.getAdminDao().hasDuplicateRecord(master, detail);
	}

	public List<MstbMasterTable> getPaymentType(MstbAcquirer acquirer) {
		return this.daoHelper.getAdminDao().getPaymentType(acquirer);
	}

	public boolean hasDuplicateRecord(MstbAcquirerMdr acquirerMdr) {
		return this.daoHelper.getAdminDao().hasDuplicateRecord(acquirerMdr);
	}

	public List<FmtbNonBillableMaster> searchNonBillable(SearchNonBillableForm form) {
		return this.daoHelper.getAdminDao().searchNonBillable(form);
	}

	public boolean hasDuplicateRecord(FmtbNonBillableMaster fnbm) {
		return this.daoHelper.getAdminDao().hasDuplicateRecord(fnbm);
	}

	public List<String> getTransactionCodeType(Integer entityNo, String txn_type) {
		return this.daoHelper.getAdminDao().getTransactionCodeType(entityNo, txn_type);
	}

	public List<FmtbArContCodeMaster> getARControlCode(Integer entity_no) {
		return this.daoHelper.getAdminDao().getARControlCode(entity_no);
	}

	public MstbPromotion getPromotion(Integer promoNo) {
		return this.daoHelper.getAdminDao().getPromotion(promoNo);
	}
	
	public MstbPromotionCashPlus getPromotionCashPlus(String promoCode) {
		return this.daoHelper.getAdminDao().getPromotionCashPlus(promoCode);
	}
	

	public MstbPromoReq getPromoReq(Integer promoReqNo) {
		return this.daoHelper.getAdminDao().getPromoReq(promoReqNo);
	}

	public MstbAcquirerPymtType getAcquirerPaymentType(Integer pymtTypeNo) {
		return this.daoHelper.getAdminDao().getAcquirerPaymentType(pymtTypeNo);
	}

	public MstbAcquirerMdr getAcquirerMdr(Integer mdrNo) {
		return this.daoHelper.getAdminDao().getAcquirerMdr(mdrNo);
	}

	public List<Object[]> searchAcquirerMdr(SearchAcquirerMdrForm form) {
		return this.daoHelper.getAdminDao().searchAcquirerMdr(form);
	}

	public boolean isArContCodeBeenUsed(Integer arControlCodeNo) {
		return this.daoHelper.getAdminDao().isArContCodeBeenUsed(arControlCodeNo);
	}

	public FmtbNonBillableMaster getNonBillableMaster(Integer masterNo) {
		return this.daoHelper.getAdminDao().getNonBillableMaster(masterNo);
	}

	public List<FmtbBankPaymentMaster> searchBankPayment(SearchBankPaymentForm form) {
		return this.daoHelper.getAdminDao().searchBankPayment(form);
	}

	public List<FmtbBankPaymentMaster> getBankPaymentByForeignExample(FmtbBankPaymentMaster master) {
		return this.daoHelper.getAdminDao().getBankPaymentByForeignExample(master);
	}

	public FmtbBankPaymentMaster getBankPaymentMaster(Integer masterNo) {
		return this.daoHelper.getAdminDao().getBankPaymentMaster(masterNo);
	}

	public FmtbBankPaymentDetail getBankPaymentDetail(Integer detailNo) {
		return this.daoHelper.getAdminDao().getBankPaymentDetail(detailNo);
	}

	public List<FmtbBankPaymentDetail> getBankPaymentDetailByForeignExample(FmtbBankPaymentDetail detail) {
		return this.daoHelper.getAdminDao().getBankPaymentDetailByForeignExample(detail);
	}

	public boolean isRecordDeletable(FmtbEntityMaster master) {
		return this.daoHelper.getAdminDao().isRecordDeletable(master);
	}

	public boolean isRecordDeletable(FmtbArContCodeMaster master) {
		return this.daoHelper.getAdminDao().isRecordDeletable(master);
	}

	public boolean isRecordDeletable(MstbBankMaster master) {
		return this.daoHelper.getAdminDao().isRecordDeletable(master);
	}

	public boolean isRecordDeletable(MstbBranchMaster master) {
		return this.daoHelper.getAdminDao().isRecordDeletable(master);
	}

	public FmtbNonBillableDetail getNonBillableDetail(Integer detailNo) {
		return this.daoHelper.getAdminDao().getNonBillableDetail(detailNo);
	}

	public List<MstbAcquirer> getAcquirerByExample() {
		return this.daoHelper.getAdminDao().getAcquirerByExample();
	}

	public FmtbTransactionCode getEarliestEffectedTxnCode(String txnType, String txnCode) {
		return this.daoHelper.getAdminDao().getEarliestEffectedTxnCode(txnType, txnCode);
	}

	public List<LrtbRewardAccount> getActiveRewardsAccount() {
		return this.daoHelper.getAdminDao().getActiveRewardsAccount();
	}

	public FmtbBankCode checkDefaultGiroBankExist(Date effectiveDate, Integer bankCodeNo) {
		return this.daoHelper.getAdminDao().checkDefaultGiroBankExist(effectiveDate, bankCodeNo);
	}

	public List<IttbGiroSetup> getActiveGiroSetup() {
		return this.daoHelper.getAdminDao().getActiveGiroSetup();
	}

	public void saveGiroSetupChanges(List<IttbGiroSetup> existingSetups, List<IttbGiroSetup> newSetups,
			List<IttbGiroSetup> deletedSetups, String userId) {
		for (IttbGiroSetup setup : existingSetups)
			this.update(setup, userId);
		for (IttbGiroSetup setup : newSetups)
			this.save(setup, userId);
		for (IttbGiroSetup setup : deletedSetups) {
			this.delete(setup);
		}
	}

	public FmtbBankCode getLatestEntityBank(FmtbEntityMaster entityMaster) {
		return this.daoHelper.getAdminDao().getLatestEntityBank(entityMaster);
	}

	public Long createGiroRequest(IttbGiroSetup setup, Date requestDate, int requestTime, Date valueDate,
			Date cutoffDate, String userId) {

		if (this.daoHelper.getAdminDao().getGiroRequest(setup, requestDate).size() > 0)
			throw new GiroRequestExistanceException();

		IttbGiroReq newRequest = new IttbGiroReq();
		newRequest.setIttbGiroSetup(setup);
		newRequest.setRequestDate(requestDate);
		newRequest.setRequestTime(requestTime);
		newRequest.setValueDate(valueDate);
		newRequest.setCutoffDate(cutoffDate);
		newRequest.setStatus(NonConfigurableConstants.GIRO_REQUEST_STATUS_PENDING);

		return (Long) this.save(newRequest, userId);
	}

	public void updateGiroRequest(IttbGiroReq request, Date requestDate, int requestTime, Date valueDate,
			Date cutoffDate, String userId) {

		if (requestDate.compareTo(request.getRequestDate()) != 0)
			if (this.daoHelper.getAdminDao().getGiroRequest(request.getIttbGiroSetup(), requestDate).size() > 0)
				throw new GiroRequestExistanceException();

		request.setRequestDate(requestDate);
		request.setRequestTime(requestTime);
		request.setValueDate(valueDate);
		request.setCutoffDate(cutoffDate);

		this.update(request, userId);
	}

	public List<IttbGiroReq> searchGiroRequest(SearchGiroRequestForm form) {
		return this.daoHelper.getAdminDao().searchGiroRequest(form);
	}

	public IttbGiroReq searchGiroRequest(Long requestNo) {
		return this.daoHelper.getAdminDao().searchGiroRequest(requestNo);
	}

	public IttbGiroSetup getInvoiceGiroDay(Integer entityNo) {
		return this.daoHelper.getAdminDao().getInvoiceGiroDay(entityNo);
	}

	public List<IttbGiroReq> getExistingGiroRequest(IttbGiroSetup setup) {
		return this.daoHelper.getAdminDao().getExistingGiroRequest(setup);
	}

	public FmtbBankCode checkDefaultCollectionBankExist(Integer entityNo, Date effectiveDate,
			Integer bankCodeNo) {
		return this.daoHelper.getAdminDao().checkDefaultCollectionBankExist(entityNo, effectiveDate,
				bankCodeNo);
	}

	public boolean isGiroReturnFileUploadedBefore(String fileName) {
		return this.daoHelper.getAdminDao().isGiroReturnFileUploadedBefore(fileName);
	}

	public IttbGiroUobHeader getCorrespondingGiroOutgoingFile(String returnFileName) {
		return this.daoHelper.getAdminDao().getCorrespondingGiroOutgoingFile(returnFileName);
	}

	public Long createGiroReturnRequest(IttbGiroReturnReq newRequest, Media media, String incomingDirectory,
			String userId) throws Exception {
		// Create new request
		Long id = (Long) this.save(newRequest, userId);

		// Update return file name into header
		IttbGiroUobHeader giroUobHeader = newRequest.getIttbGiroUobHeader();
		this.update(giroUobHeader, userId);

		// Find entity code
		String entityCode = giroUobHeader.getIttbGiroReq().getIttbGiroSetup().getFmtbEntityMaster()
				.getEntityCode();
		logger.info("incomingDirectory: " + incomingDirectory + " | File Name: " + media.getName()
				+ " | Entity Code: " + entityCode);
		incomingDirectory = incomingDirectory + entityCode + "/FROMBANK/";
		logger.info("Directory: " + incomingDirectory);

		// Upload file to server
		File directory = new File(incomingDirectory);
		boolean isDirectoryCreated = false;
		if (!directory.exists()) {
			isDirectoryCreated = directory.mkdirs();
			if (!isDirectoryCreated)
				throw new GiroException("Failed to create new directory");
		}

		File tempFile = new File(incomingDirectory + media.getName());
		if (tempFile.exists())
			throw new GiroException("File already existed");

		boolean isFileCreated = tempFile.createNewFile();
		if (!isFileCreated)
			throw new GiroException("Failed to create new file");

		FileOutputStream fos = new FileOutputStream(tempFile);
		fos.write(media.getByteData());
		fos.flush();
		fos.close();

		return id;
	}

	public List<IttbGiroReturnReq> searchGiroReturnRequest(SearchGiroReturnRequestForm form) {
		return this.daoHelper.getAdminDao().searchGiroReturnRequest(form);
	}

	public IttbGiroReturnReq searchGiroReturnRequest(Long requestNo) {
		return this.daoHelper.getAdminDao().searchGiroReturnRequest(requestNo);
	}

	public List<IttbGovtEinvoiceReq> searchGovtEInvRequest(SearchGovtEInvRequestForm form) {
		return this.daoHelper.getAdminDao().searchGovtEInvRequest(form);
	}

	public IttbGovtEinvoiceReq searchGovtEInvRequest(Integer requestNo) {
		return this.daoHelper.getAdminDao().searchGovtEInvRequest(requestNo);
	}

	public void uploadGovEInvReturnFile(IttbGovtEinvoiceReq request,
			Map<String, List<String>> listOfUploadedFiles) throws Exception {

		List<IttbGovtEinvoiceHdrDtl> foundInvoices = new LinkedList<IttbGovtEinvoiceHdrDtl>();

		Map<String, IttbGovtEinvoiceHdrDtl> invoicesInRequest = new HashMap<String, IttbGovtEinvoiceHdrDtl>();
		for (IttbGovtEinvoiceHdrDtl invoiceHeaderDtl : request.getIttbGovtEinvoiceHdrDtls())
			invoicesInRequest.put(invoiceHeaderDtl.getInvoiceNo(), invoiceHeaderDtl);

		// Validation - invoices count should match
		Integer totalInvoiceCount = 0;
		for (List<String> linesOfData : listOfUploadedFiles.values())
			totalInvoiceCount += linesOfData.size() - 2;

		if (invoicesInRequest.size() != totalInvoiceCount)
			throw new GovtEInvException(
					"The no. of invoices ("+invoicesInRequest.size()+") in the request does not match the no. of invoices ("+totalInvoiceCount+") in the return file(s)!");

		GovtEInvReturn govtEInvReturn = new GovtEInvReturn();
		for (Entry<String, List<String>> entry : listOfUploadedFiles.entrySet()) {

			String fileName = entry.getKey();
			List<String> linesOfData = entry.getValue();

			for (int i = 0; i < linesOfData.size(); i++) {
				// first record
				if (i == 0) {
					govtEInvReturn.controlHeader = new ControlHeader(linesOfData.get(i));
					govtEInvReturn.controlHeader.lineNo = i + 1;
				}
				// last record
				else if (i == (linesOfData.size() - 1)) {
					govtEInvReturn.trailer = new Trailer(linesOfData.get(i));
					govtEInvReturn.trailer.lineNo = i + 1;

					// Validation - Check to ensure total no. of lines in the file matches the trailer's total
					// feed line
					if (linesOfData.size() != govtEInvReturn.trailer.totalFeedLine.getDataInInteger()
							.intValue())
						throw new GovtEInvException("Trailer record's total feed line value ("
								+ govtEInvReturn.trailer.totalFeedLine.getDataInInteger()
								+ ") does not match total no. of lines (" + linesOfData.size()
								+ ") read from file(" + fileName + ")!");
				}
				// the rest are details
				else {
					InvoiceHeader invoiceHeader = new InvoiceHeader(linesOfData.get(i));
					invoiceHeader.lineNo = i + 1;
					govtEInvReturn.invoiceHeaders.add(invoiceHeader);

					// Validation - check that invoice in return file is found in the outgoing request's
					// invoices
					if (invoicesInRequest.get(invoiceHeader.invoiceNo.getData()) != null) {
						// Once located, remove from hashmap
						IttbGovtEinvoiceHdrDtl invoiceHeaderDtl = invoicesInRequest
								.remove(invoiceHeader.invoiceNo.getData());

						// Validate BU
						if (!invoiceHeader.businessUnit.getData().equals(invoiceHeaderDtl.getBusinessUnit()))
							throw new GovtEInvException("Return invoice's ("
									+ invoiceHeader.invoiceNo.getData() + ") business unit ("
									+ invoiceHeader.businessUnit.getData()
									+ ") does not match outgoing invoice's business unit ("
									+ invoiceHeaderDtl.getBusinessUnit() + ")");

						// Validate Vendor Id
						if (!invoiceHeader.vendorId.getData().equals(invoiceHeaderDtl.getVendorId()))
							throw new GovtEInvException("Return invoice's ("
									+ invoiceHeader.invoiceNo.getData() + ") vendor id ("
									+ invoiceHeader.vendorId.getData()
									+ ") does not match outgoing invoice's vendor id ("
									+ invoiceHeaderDtl.getVendorId() + ")");

						// Update return status
						if (invoiceHeader.invoiceStatus.getData().equals(InvoiceHeader.SUCCESS))
							invoiceHeaderDtl
									.setReturnStatus(NonConfigurableConstants.GOVT_EINV_RETURN_STATUS_SUCCESS);
						else if (invoiceHeader.invoiceStatus.getData().equals(InvoiceHeader.REJECT))
							invoiceHeaderDtl
									.setReturnStatus(NonConfigurableConstants.GOVT_EINV_RETURN_STATUS_REJECT);

						// Update remarks
						invoiceHeaderDtl.setReturnRemarks(invoiceHeader.remarks.getData());

						foundInvoices.add(invoiceHeaderDtl);
					}
				}
			}
		}

		// Validation - if any invoices remaining in the map, means return file either missed out these
		// invoices or the return file not meant for this request
		if (invoicesInRequest.size() > 0)
			throw new GovtEInvException(
					"The invoices in the request does not match the invoices in the return file(s)!");

		// Update the return status
		request.setStatus(NonConfigurableConstants.GOVT_EINV_REQUEST_STATUS_UPLOADED);
		this.update(request, CommonWindow.getUserLoginIdAndDomain());
		for (IttbGovtEinvoiceHdrDtl invoiceHeaderDtl : foundInvoices)
			this.update(invoiceHeaderDtl, CommonWindow.getUserLoginIdAndDomain());
	}
	public List<IttbPubbsReq> searchPubbsRequest(SearchPubbsRequestForm form) {
		return this.daoHelper.getAdminDao().searchPubbsRequest(form);
	}
	
	public IttbPubbsReq searchPubbsRequest(Integer requestNo) {
		return this.daoHelper.getAdminDao().searchPubbsRequest(requestNo);
	}
	
	public List<IttbRecurringReq> searchIttbRecurringRequest(SearchRecurringRequestForm form) {
		return this.daoHelper.getAdminDao().searchIttbRecurringRequest(form);
	}

	public IttbRecurringReq searchIttbRecurringRequest(Integer requestNo) {
		return this.daoHelper.getAdminDao().searchIttbRecurringRequest(requestNo);
	}
	
	public void uploadPubbsReturnFile(IttbPubbsReq request,
			Map<String, List<String>> listOfUploadedFiles) throws Exception, IncorrectLengthException {

		List<IttbPubbsDtl> foundInvoices = new LinkedList<IttbPubbsDtl>();

		Map<String, IttbPubbsDtl> pubbsInvoicesInRequest = new HashMap<String, IttbPubbsDtl>();
		for (IttbPubbsDtl pubbsInvoiceHeaderDtl : request.getIttbPubbsDtls())
			pubbsInvoicesInRequest.put(pubbsInvoiceHeaderDtl.getInvoiceNumber(), pubbsInvoiceHeaderDtl);

		// Validation - invoices count should match
		Integer totalInvoiceCount = 0;
		for (List<String> linesOfData : listOfUploadedFiles.values())
			totalInvoiceCount += linesOfData.size() - 2;

		if (pubbsInvoicesInRequest.size() != totalInvoiceCount)
			throw new PubbsException(
					"The no. of invoices ("+pubbsInvoicesInRequest.size()+") in the request does not match the no. of invoices ("+totalInvoiceCount+") in the return file(s)!");

		try
		{
			PubbsReturn pubbsReturn = new PubbsReturn();
			for (Entry<String, List<String>> entry : listOfUploadedFiles.entrySet()) {
	
				String fileName = entry.getKey();
				List<String> linesOfData = entry.getValue();
	
				for (int i = 0; i < linesOfData.size(); i++) {
					// first record
					System.out.println("i " +i);
					if (i == 0) {
						pubbsReturn.pubbsControlHeader = new PubbsControlHeader(linesOfData.get(i));
						pubbsReturn.pubbsControlHeader.lineNo = i + 1;
					}
					// last record
					else if (i == (linesOfData.size() - 1)) {
						pubbsReturn.pubbsReturnTrailer = new PubbsReturnTrailer(linesOfData.get(i));
						pubbsReturn.pubbsReturnTrailer.lineNo = i + 1;
	
						// Validation - Check to ensure total no. of lines in the file matches the trailer's total
						// feed line
						if ((linesOfData.size() - 2) != Integer.parseInt(pubbsReturn.pubbsReturnTrailer.totalFeedLine.getData().trim()))
							throw new PubbsException("Trailer record's total feed line value ("
									+ pubbsReturn.pubbsReturnTrailer.totalFeedLine.getData().trim()
									+ ") does not match total no. of lines (" + (linesOfData.size() - 2 )
									+ ") read from file(" + fileName + ")!");
					}
					// the rest are details
					else {
						PubbsInvoiceHeader pubbsInvoiceHeader = new PubbsInvoiceHeader(linesOfData.get(i));
						pubbsInvoiceHeader.lineNo = i + 1;
						pubbsReturn.pubbsInvoiceHeaders.add(pubbsInvoiceHeader);
						// Validation - check that invoice in return file is found in the outgoing request's
						// invoices
						if (pubbsInvoicesInRequest.get(pubbsInvoiceHeader.invoiceNumber.getData()) != null) {
	//						 Once located, remove from hashmap
							IttbPubbsDtl pubbsInvoiceHeaderDtl = pubbsInvoicesInRequest
									.remove(pubbsInvoiceHeader.invoiceNumber.getData());
	
	//						// Update remarks
							pubbsInvoiceHeaderDtl.setReturnStatus(pubbsInvoiceHeader.errorFlag.getData());
							pubbsInvoiceHeaderDtl.setReturnRemarks(pubbsInvoiceHeader.errorMessage.getData());
	
							foundInvoices.add(pubbsInvoiceHeaderDtl);
						}
					}
				}
			}
	
			// Validation - if any invoices remaining in the map, means return file either missed out these
			// invoices or the return file not meant for this request
			if (pubbsInvoicesInRequest.size() > 0)
				throw new PubbsException(
						"The invoices in the request does not match the invoices in the return file(s)!");
	
			// Update the return status
			request.setStatus(NonConfigurableConstants.PUBBS_REQUEST_STATUS_UPLOADED);
			this.update(request, CommonWindow.getUserLoginIdAndDomain());
	
			for (IttbPubbsDtl pubbsInvoiceHeaderDtl : foundInvoices)
				this.update(pubbsInvoiceHeaderDtl, CommonWindow.getUserLoginIdAndDomain());
		}
		catch (IncorrectLengthException e) {
			throw new IncorrectLengthException(""+e); 
		}
	}
	
	public List<MstbPromotionCashPlus> getEffectivePrepaidPromotions(){
		return this.daoHelper.getAdminDao().getEffectivePrepaidPromotions();
	}
	
	public void updateAcquirerPaymentType(MstbAcquirerPymtType acquirerPymtType, Map<java.util.Date, Map<String, Object>> commissionMap, String userId){

		// commission
		if(commissionMap!=null){
			for(Map.Entry<java.util.Date, Map<String, Object>> entry :  commissionMap.entrySet()) {
				
				Map<String, Object> commissionDetail = entry.getValue();
				java.util.Date effectiveDate = (java.util.Date) entry.getKey();
				// skip if effective date is earlier than now.
				if(!DateUtil.isToday(effectiveDate) && effectiveDate.before(Calendar.getInstance().getTime())){
					continue;
				}
				boolean found = false;
				
				if(null != acquirerPymtType.getMstbAcquirerPymtComm())
				{
					//for each see got any existing got same commissionNo (ID, Pkey) or not.. 
					for(MstbAcquirerPymtComm acquirerPymtComm : acquirerPymtType.getMstbAcquirerPymtComm())
					{
						if(null != commissionDetail.get("commissionNo"))
						{
							if(commissionDetail.get("commissionNo").equals(acquirerPymtComm.getCommissionNo()))
							{
								found = true;
								
								acquirerPymtComm.setCommissionValue((BigDecimal)commissionDetail.get("commissionValue"));
								acquirerPymtComm.setEffectiveDtFrom(DateUtil.convertDateToTimestamp(effectiveDate));
								acquirerPymtComm.setCommissionType(commissionDetail.get("commissionType").toString());
								if(null != commissionDetail.get("effectiveDateTo"))
									acquirerPymtComm.setEffectiveDtTo(DateUtil.convertDateToTimestamp( (Date) commissionDetail.get("effectiveDateTo")));
								
								acquirerPymtComm.setUpdatedBy(userId);
								acquirerPymtComm.setUpdatedDt(DateUtil.getCurrentTimestamp());
								this.update(acquirerPymtComm);
								break;
							}
						}
					}
				}
				if(!found){ //NOT FOUND then Create new
					MstbAcquirerPymtComm mstbAcquirerPymtComm = new MstbAcquirerPymtComm();
					mstbAcquirerPymtComm.setCommissionType(commissionDetail.get("commissionType").toString());
					mstbAcquirerPymtComm.setCommissionValue((BigDecimal)commissionDetail.get("commissionValue"));
					mstbAcquirerPymtComm.setEffectiveDtFrom(DateUtil.convertDateToTimestamp(effectiveDate));
					if(null != commissionDetail.get("effectiveDateTo"))
						mstbAcquirerPymtComm.setEffectiveDtTo(DateUtil.convertDateToTimestamp((Date) commissionDetail.get("effectiveDateTo")));
					
					mstbAcquirerPymtComm.setCreatedBy(userId);
					mstbAcquirerPymtComm.setCreatedDt(DateUtil.getCurrentTimestamp());
					mstbAcquirerPymtComm.setMstbAcquirerPymtType(acquirerPymtType);
					this.save(mstbAcquirerPymtComm);
				}
			}
			
			//Delete future record that got X @ the main screen
			for(MstbAcquirerPymtComm pymtComm : acquirerPymtType.getMstbAcquirerPymtComm()){
				boolean found = false;
				for(Map.Entry<java.util.Date, Map<String, Object>> entry :  commissionMap.entrySet()) {
					Map<String, Object> commissionDetail = entry.getValue();
				
					if(null != commissionDetail.get("commissionNo")) {  //See can find existing commissionNo = the on screen commissionNo
						if(pymtComm.getCommissionNo() == commissionDetail.get("commissionNo"))	{
							//if can got same commissionNo record can skip.
							found = true;
							break;
						}
					}
				}
				if(!found){
					//cannot find GG, pls delete the record
					this.delete(pymtComm);
				}
			}
		} //commissionMap End
	}
	
	public List<MstbInvoicePromo> getListInvoicePromo(String name, Date dateFrom, Date dateTo, String initial) {
		return this.daoHelper.getAdminDao().getListInvoicePromo(name, dateFrom, dateTo, initial);
	}
	public MstbInvoicePromo getInvoicePromo(Integer invoicePromoId) {
		return this.daoHelper.getAdminDao().getInvoicePromo(invoicePromoId);
	}
	public boolean checkPromoNumber(String promoNumber, Timestamp insDate, int promoId) {
		return this.daoHelper.getAdminDao().checkPromoNumber(promoNumber, insDate, promoId);		
	}
}
