package com.webapp.ims.dis.service.impl;

import static com.webapp.ims.exception.GlobalConstants.EVALUATE_PROJECT_DETAILS;
import static com.webapp.ims.exception.GlobalConstants.EXISTING_PROJECT;
import static com.webapp.ims.exception.GlobalConstants.NATURE_OF_PROJECT;
import static com.webapp.ims.exception.GlobalConstants.NEW_PROJECT;
import static com.webapp.ims.exception.GlobalConstants.PRODUCTS;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.servlet.ModelAndView;

import com.webapp.ims.controller.ApplicationDetailsViewController;
import com.webapp.ims.dis.model.CapitalInvestmentDetails;
import com.webapp.ims.dis.model.DISPrepareAgendaNotes;
import com.webapp.ims.dis.model.DisViewEvaluate;
import com.webapp.ims.dis.model.Discis;
import com.webapp.ims.dis.model.Disiis;
import com.webapp.ims.dis.model.DissbursmentApplicantDetails;
import com.webapp.ims.dis.model.ExistProjDisbursement;
import com.webapp.ims.dis.model.NewProjDisbursement;
import com.webapp.ims.dis.repository.CapitalInvestRepository;
import com.webapp.ims.dis.repository.DISPrepareAgendaNotesRepository;
import com.webapp.ims.dis.repository.NewProjDisburseRepository;
import com.webapp.ims.dis.repository.ProjDisburseRepository;
import com.webapp.ims.dis.service.DisEvaluationDocumentService;
import com.webapp.ims.dis.service.DisPrepareAgendaNoteService;
import com.webapp.ims.dis.service.DisViewEvaluateService;
import com.webapp.ims.dis.service.DisbursmentApplicantDetailsService;
import com.webapp.ims.dis.service.DisbursmentCisService;
import com.webapp.ims.dis.service.DisbursmentIisService;
import com.webapp.ims.food.Model.FoodEvalutionViewCSIS;
import com.webapp.ims.model.DeptBusinessEntityDetails;
import com.webapp.ims.model.DeptIncentiveDetails;
import com.webapp.ims.model.EvaluateExistNewProjDetails;
import com.webapp.ims.model.EvaluateInvestmentDetails;
import com.webapp.ims.model.EvaluateProjectDetails;
import com.webapp.ims.model.IncentiveDetails;
import com.webapp.ims.model.IncentiveSpecificDetails;
import com.webapp.ims.model.NewProjectDetails;
import com.webapp.ims.model.PhaseWiseInvestmentDetails;
import com.webapp.ims.model.PrepareAgendaNotes;
import com.webapp.ims.model.RaiseQuery;
import com.webapp.ims.repository.DeptBusinessEntityDetailsRepository;
import com.webapp.ims.repository.DeptIncentiveDetailsRepository;
import com.webapp.ims.repository.EvaluateExistNewProjDetailsRepository;
import com.webapp.ims.repository.ExistingProjectDetailsRepository;
import com.webapp.ims.repository.InvestmentDetailsRepository;
import com.webapp.ims.repository.NewProjectRepository;
import com.webapp.ims.repository.PhaseWiseInvestmentDetailsRepository;
import com.webapp.ims.service.BusinessEntityDetailsService;
import com.webapp.ims.service.EvaluateInvestMentDetailsService;
import com.webapp.ims.service.EvaluateProjectDetailsService;
import com.webapp.ims.service.IncentiveDetailsService;
import com.webapp.ims.service.NewProjectDetailsService;
import com.webapp.ims.service.PrepareAgendaNotesService;
import com.webapp.ims.service.impl.AdditionalInterest;

@Service
public class DisPrepareAgendaNoteServiceImpl implements DisPrepareAgendaNoteService {

	@Autowired
	DISPrepareAgendaNotesRepository dISPrepareAgendaNotesRepository;

	@Autowired
	DisbursmentApplicantDetailsService disApplicantService;

	@Autowired
	BusinessEntityDetailsService businessService;

	@Autowired
	DeptBusinessEntityDetailsRepository deptBusEntRepository;

	@Autowired
	ApplicationDetailsViewController applicantViewController;
	@Autowired
	PhaseWiseInvestmentDetailsRepository pwRepository;
	@Autowired
	InvestmentDetailsRepository investRepository;

	@Autowired
	EvaluateProjectDetailsService evaluateProjectDetailsService;
	@Autowired
	EvaluateInvestMentDetailsService evaluateInvestMentDetailsService;
	@Autowired
	public EvaluateExistNewProjDetailsRepository evaluateExistNewProjDetailsRepository;
	@Autowired
	private NewProjectDetailsService newProjectService;
	@Autowired
	CapitalInvestRepository capitalInvestRepository;

	@Autowired
	DisbursmentCisService disbursmentCisService;
	@Autowired
	DisbursmentIisService disbursmentIisService;

	@Autowired
	DisViewEvaluateService disViewEvaluateService;

	@Autowired
	DisEvaluationDocumentService disEvaluationDocumentService;
	@Autowired
	NewProjDisburseRepository newProjDisburseRepository;

	@Autowired
	ProjDisburseRepository projDisburseRepository;
	@Autowired
	AdditionalInterest additionalInterest;
	@Autowired
	IncentiveDetailsService incentiveDetailsService;

	@Autowired
	PrepareAgendaNotesService prepareAgendaNotesService;

	@Autowired
	DISPrepareAgendaNotesRepository prepareAgendaNotesRepository;
	@Autowired
	private DeptIncentiveDetailsRepository deptIncRepository;

	@Autowired
	private ExistingProjectDetailsRepository existProjRepository;

	@Autowired
	private NewProjectRepository newexistProjRepository;

	
	private final Logger logger = LoggerFactory.getLogger(DisPrepareAgendaNoteServiceImpl.class);

	@Override
	public void common(Model model, HttpServletRequest request, HttpSession session) {

		String appId = (String) session.getAttribute("appId");
		String appStr = appId.substring(0, appId.length() - 2);
		String cisInvId = appStr + "CI";
		String projId = appStr + "P1";
		PrepareAgendaNotes prepagendanote = prepareAgendaNotesService.findPrepAgendaNotesByAppliId(appId);
		model.addAttribute("locDate", prepagendanote.getModifyDate());

		DissbursmentApplicantDetails disApplicantDetails = new DissbursmentApplicantDetails();
		disApplicantDetails = disApplicantService.getDetailsBydisAppId(appId);
		model.addAttribute("disApplicantDetails", disApplicantDetails);

		businessService.commonDetails(appId, session, model);

		DeptBusinessEntityDetails deptBusEntFromDb = deptBusEntRepository.findByApplicantDetailId(appId);
		// fetch BusinessEntity documents from MongoDB
		applicantViewController.businessDocFromMongoDB(deptBusEntFromDb.getId(), model);

		EvaluateProjectDetails evaluateProjectDetails = evaluateProjectDetailsService
				.getEvalProjDetByapplicantDetailId(appId);

		EvaluateInvestmentDetails evaluateInvestMentDetails = null;
		if (evaluateInvestMentDetailsService.getEvalInvestDetByapplId(appId) != null) {
			evaluateInvestMentDetails = evaluateInvestMentDetailsService.getEvalInvestDetByapplId(appId);
		}

		model.addAttribute("evaluateInvestMentDetails", evaluateInvestMentDetails);

		String expension = evaluateProjectDetails.getExpansion() != null ? evaluateProjectDetails.getExpansion() : "";
		String diversification = evaluateProjectDetails.getDiversification() != null
				? evaluateProjectDetails.getDiversification()
				: "";

		if (evaluateProjectDetails.getNatureOfProject().equalsIgnoreCase(EXISTING_PROJECT)) {
			// fetch documents from MongoDB
			applicantViewController.existProjDocsFromMongoDB(evaluateProjectDetails.getId(), model);
			model.addAttribute(NATURE_OF_PROJECT, expension);
			if (!expension.isEmpty() && !diversification.isEmpty()) {
				model.addAttribute(NATURE_OF_PROJECT, expension + "/ " + diversification);
			} else if (!diversification.isEmpty()) {
				model.addAttribute(NATURE_OF_PROJECT, diversification);
			}
			List<EvaluateExistNewProjDetails> evaluateExistNewProjDetailsList = evaluateExistNewProjDetailsRepository
					.getEvalExistProjListByepdProjDtlId(evaluateProjectDetails.getId());
			model.addAttribute("evaluateExistNewProjDetailsList", evaluateExistNewProjDetailsList);

		} else {
			// fetch documents from MongoDB
			applicantViewController.newProjDocMongoDB(evaluateProjectDetails.getId(), model);

			model.addAttribute(NATURE_OF_PROJECT, evaluateProjectDetails.getNatureOfProject());
			List<NewProjectDetails> newProjList = newProjectService.getNewProjListById(projId);
			model.addAttribute("newProjList", newProjList);

			model.addAttribute("newProjProdDetail", evaluateProjectDetails.getProductDetails());

		}
		// Phase wise list
		List<PhaseWiseInvestmentDetails> pwInvDetailsList = pwRepository.findByPwApcId(appId);
		model.addAttribute("pwInvDetailsList", pwInvDetailsList);

		// Capital Details
		CapitalInvestmentDetails capInvDetails = capitalInvestRepository.getDetailsBycapInvId(cisInvId);
		NewProjDisbursement newproject = newProjDisburseRepository.getDetailsBynewprojApcId(appId);
		ExistProjDisbursement existproject = projDisburseRepository.getDetailsByprojApcId(appId);
		model.addAttribute("capInvDetails", capInvDetails);
		if (evaluateProjectDetails.getNatureOfProject().equalsIgnoreCase(EXISTING_PROJECT)) {
			model.addAttribute("newtotal", newproject.getTotal());
			model.addAttribute("newpnm", existproject.getProjDisPlantMachCost());
			model.addAttribute("newinfra", existproject.getProjDisInfra());
			/* if (!expension.isEmpty() && !diversification.isEmpty()) { */

		} else {
			model.addAttribute("newtotal", newproject.getTotal());
			model.addAttribute("newpnm", newproject.getNewprojPlantMachCost());
			model.addAttribute("newinfra", newproject.getNewprojInfra());
		}

		// CIS
		String cisid = appId + "CIS";
		Discis discis = new Discis();
		discis = disbursmentCisService.getDiscisBydiscisId(cisid);

		model.addAttribute("cisincentiveDeatilsForm", discis);

		double A = newproject.getTotal();
		double B = newproject.getNewprojPlantMachCost();
		long C = discis.getTotal();
		float D = discis.getRoi();
		long E = discis.getTotalint();

		// (5/E)
		double u;
		String equity = additionalInterest.getaddCIS(appId);

		if (equity == "eligible") {
			model.addAttribute("additional", 7.5);
			u = (7.5 / D);
		} else {
			model.addAttribute("additional", 5);

			u = (5.0 / D);
		}
		// (B/A)*C

		double z = Math.abs(B / A);

		// (B/A)*E

		double propintforPnM = z * E;

		double loanforPnM = z * C;

		// (B/A)*E*(5/D)

		double propciS = propintforPnM * u;

		model.addAttribute("loanforPnM", String.format("%.0f", loanforPnM));

		model.addAttribute("propintforPnM", String.format("%.2f", propintforPnM));
		model.addAttribute("prop5", String.format("%.2f", propciS));

		// Double.toString(d)

		// Eligible Amout of CIS --> Proportionate interest for P&M at Applicable Rate
		// of Interest for first year

		long l = Long.parseLong(discis.getFirstYP());

		double fproposnate = propintforPnM * l;
		model.addAttribute("fproposnate", String.format("%.2f", fproposnate));

		double propfirst = fproposnate * u;

		if (propfirst > 5000000) {
			model.addAttribute("ceiling", 500000);
		} else
			model.addAttribute("ceiling", propfirst);

		model.addAttribute("propfirst", String.format("%.2f", propfirst));

		// of Interest for second year
		if (discis.getSecondYP() != null && !discis.getSecondYP().isEmpty()) {
			long l2 = Long.parseLong(discis.getSecondYP());
			double fproposnate2 = propintforPnM * l2;
			model.addAttribute("fproposnate2", String.format("%.2f", fproposnate2));

			double propfirst2 = fproposnate2 * u;

			if (propfirst2 > 5000000) {
				model.addAttribute("ceiling2", 500000);
			} else
				model.addAttribute("ceiling2", propfirst2);

			model.addAttribute("propfirst2", String.format("%.2f", propfirst2));

		}

		// IIS

		String iisid = appId + "IIS";
		Disiis disiis = new Disiis();
		disiis = disbursmentIisService.getDisiisBydisiisId(iisid);

		model.addAttribute("iisincentiveDeatilsForm", disiis);

		// incentive details
		String isfId = incentiveDetailsService.getIsfIdByApplId(appId);

		IncentiveSpecificDetails incentiveSpecificDetails = new IncentiveSpecificDetails();
		IncentiveDetails IncentiveDetail = incentiveDetailsService.getIncentiveisfById(isfId);
		DeptIncentiveDetails deptIncDetails = deptIncRepository.findById(isfId);

		if (IncentiveDetail != null) {
			// PICUP Officer's Remark
			incentiveSpecificDetails.setISF_Claim_Reim(IncentiveDetail.getISF_Claim_Reim());
			incentiveSpecificDetails.setISF_Reim_SCST(IncentiveDetail.getISF_Reim_SCST());
			incentiveSpecificDetails.setISF_Reim_FW(IncentiveDetail.getISF_Reim_FW());
			incentiveSpecificDetails.setISF_Reim_BPLW(IncentiveDetail.getISF_Reim_BPLW());
			incentiveSpecificDetails.setISF_Ttl_SGST_Reim(IncentiveDetail.getISF_Ttl_SGST_Reim());

			incentiveSpecificDetails.setISF_Stamp_Duty_EX(IncentiveDetail.getISF_Stamp_Duty_EX());
			incentiveSpecificDetails.setISF_Additonal_Stamp_Duty_EX(IncentiveDetail.getISF_Additonal_Stamp_Duty_EX());
			incentiveSpecificDetails.setISF_Amt_Stamp_Duty_Reim(IncentiveDetail.getISF_Amt_Stamp_Duty_Reim());
			incentiveSpecificDetails.setISF_Ttl_Stamp_Duty_EX(IncentiveDetail.getISF_Ttl_Stamp_Duty_EX());

			incentiveSpecificDetails.setISF_Epf_Reim_UW(IncentiveDetail.getISF_Epf_Reim_UW());
			incentiveSpecificDetails.setISF_Add_Epf_Reim_SkUkW(IncentiveDetail.getISF_Add_Epf_Reim_SkUkW());
			incentiveSpecificDetails.setISF_Add_Epf_Reim_DIVSCSTF(IncentiveDetail.getISF_Add_Epf_Reim_DIVSCSTF());
			incentiveSpecificDetails.setISF_Ttl_EPF_Reim(IncentiveDetail.getISF_Ttl_EPF_Reim());// 1

			incentiveSpecificDetails.setISF_Cis(IncentiveDetail.getISF_Cis());
			incentiveSpecificDetails.setISF_ACI_Subsidy_Indus(IncentiveDetail.getISF_ACI_Subsidy_Indus());
			incentiveSpecificDetails.setISF_Infra_Int_Subsidy(IncentiveDetail.getISF_Infra_Int_Subsidy());
			incentiveSpecificDetails.setISF_AII_Subsidy_DIVSCSTF(IncentiveDetail.getISF_AII_Subsidy_DIVSCSTF());
			incentiveSpecificDetails.setISF_Loan_Subsidy(IncentiveDetail.getISF_Loan_Subsidy());
			incentiveSpecificDetails.setISF_Total_Int_Subsidy(IncentiveDetail.getISF_Total_Int_Subsidy());

			incentiveSpecificDetails.setISF_Tax_Credit_Reim(IncentiveDetail.getISF_Tax_Credit_Reim());
			incentiveSpecificDetails.setISF_EX_E_Duty(IncentiveDetail.getISF_EX_E_Duty());
			incentiveSpecificDetails.setISF_EX_E_Duty_PC(IncentiveDetail.getISF_EX_E_Duty_PC());
			incentiveSpecificDetails.setISF_EX_Mandee_Fee(IncentiveDetail.getISF_EX_Mandee_Fee());
			incentiveSpecificDetails.setISF_Indus_Payroll_Asst(IncentiveDetail.getISF_Indus_Payroll_Asst());
			incentiveSpecificDetails.setTotal_Other_Incentive(IncentiveDetail.getTotal_Other_Incentive());

			incentiveSpecificDetails.setISF_Cstm_Inc_Status(IncentiveDetail.getISF_Cstm_Inc_Status());
			incentiveSpecificDetails.setIsfCustIncDocName(IncentiveDetail.getIsfCustIncDocName());
			incentiveSpecificDetails.setIsfCustIncDoc(IncentiveDetail.getIsfCustIncDocName().getBytes());

			incentiveSpecificDetails.setId(IncentiveDetail.getId());

			evaluateProjectDetails.setSubDateAppObserv(deptIncDetails.getSubDateAppObserv());
			try {
				long totOther = incentiveSpecificDetails.getTotal_Other_Incentive() == null ? 0
						: incentiveSpecificDetails.getTotal_Other_Incentive();

				long totIntSub = incentiveSpecificDetails.getISF_Total_Int_Subsidy() == null ? 0
						: incentiveSpecificDetails.getISF_Total_Int_Subsidy();
				long totStamp = incentiveSpecificDetails.getISF_Ttl_Stamp_Duty_EX() == null ? 0
						: incentiveSpecificDetails.getISF_Ttl_Stamp_Duty_EX();
				long totSGST = incentiveSpecificDetails.getISF_Ttl_SGST_Reim() == null ? 0
						: incentiveSpecificDetails.getISF_Ttl_SGST_Reim();
				long totEPF = incentiveSpecificDetails.getISF_Ttl_EPF_Reim() == null ? 0
						: incentiveSpecificDetails.getISF_Ttl_EPF_Reim();

				Long total = totOther + totIntSub + totStamp + totSGST + totEPF;

				model.addAttribute("total", total);
			} catch (Exception e) {
				logger.error(String.format("#### evaluateApplication exception #### %s", e.getMessage()));
			}
		}

		StringBuilder products = new StringBuilder();
		if (evaluateProjectDetails.getNatureOfProject().equalsIgnoreCase(EXISTING_PROJECT)) {
			List<Object[]> existProductsList = existProjRepository.findProductsByProjId(projId);

			if (!existProductsList.isEmpty()) {
				for (Object[] prodArr : existProductsList) {
					if (prodArr.length > 0) {
						for (int k = 0; k < prodArr.length; k++) {
							products = products.append(prodArr[k].toString()).append(", ");
						}
					}
				}
				model.addAttribute(PRODUCTS, products.toString().substring(0, products.toString().lastIndexOf(',')));
			}
		} else if (evaluateProjectDetails.getNatureOfProject().equalsIgnoreCase(NEW_PROJECT)) {

			List<String> newproductsList = newexistProjRepository.findProductsByProjId(projId);
			if (!newproductsList.isEmpty()) {
				for (int k = 0; k < newproductsList.size(); k++) {
					products = products.append(newproductsList.get(k)).append(", ");
				}
				model.addAttribute(PRODUCTS, products.toString().substring(0, products.toString().lastIndexOf(',')));
			} else {
				model.addAttribute(PRODUCTS, "dont have product");
			}
		}

		List<Object> evalObjList = investRepository.getEvalDetailsByApplId(appId);
		model.addAttribute("evalObjList", evalObjList);

		model.addAttribute("appId", appId);
		model.addAttribute("incentiveDeatilsData", incentiveSpecificDetails);
		model.addAttribute(EVALUATE_PROJECT_DETAILS, evaluateProjectDetails);

		String evlId = appId + "DEV";
		String disEvalId = appId + "DISEVL";

		DisViewEvaluate disViewEvaluate1 = disViewEvaluateService.getDetailsByEvaluateId(evlId);
		if (disViewEvaluate1 != null) {
			incentiveSpecificDetails.setSgstRemark(disViewEvaluate1.getSgstRemark());
			incentiveSpecificDetails.setScstRemark(disViewEvaluate1.getScstRemark());
			incentiveSpecificDetails.setFwRemark(disViewEvaluate1.getFwRemark());
			incentiveSpecificDetails.setBplRemark(disViewEvaluate1.getBplRemark());
			incentiveSpecificDetails.setStampDutyExemptRemark(disViewEvaluate1.getStampDutyExemptRemark());
			incentiveSpecificDetails.setStampDutyReimRemark(disViewEvaluate1.getStampDutyReimRemark());
			incentiveSpecificDetails.setDivyangSCSTRemark(disViewEvaluate1.getDivyangSCSTRemark());
			incentiveSpecificDetails.setEpfUnsklRemark(disViewEvaluate1.getEpfUnsklRemark());
			incentiveSpecificDetails.setEpfSklUnsklRemark(disViewEvaluate1.getEpfSklUnsklRemark());
			incentiveSpecificDetails.setEpfDvngSCSTRemark(disViewEvaluate1.getEpfDvngSCSTRemark());
			incentiveSpecificDetails.setCapIntSubRemark(disViewEvaluate1.getCapIntSubRemark());
			incentiveSpecificDetails.setAciSubsidyRemark(disViewEvaluate1.getAciSubsidyRemark());
			incentiveSpecificDetails.setInfraIntSubRemark(disViewEvaluate1.getInfraIntSubRemark());
			incentiveSpecificDetails.setAiiSubsidyRemark(disViewEvaluate1.getAiiSubsidyRemark());
			incentiveSpecificDetails.setLoanIntSubRemark(disViewEvaluate1.getLoanIntSubRemark());
			incentiveSpecificDetails.setInputTaxRemark(disViewEvaluate1.getInputTaxRemark());
			incentiveSpecificDetails.setElecDutyCapRemark(disViewEvaluate1.getElecDutyCapRemark());
			incentiveSpecificDetails.setElecDutyDrawnRemark(disViewEvaluate1.getElecDutyDrawnRemark());
			incentiveSpecificDetails.setMandiFeeRemark(disViewEvaluate1.getMandiFeeRemark());
			incentiveSpecificDetails.setDiffAbleWorkRemark(disViewEvaluate1.getDiffAbleWorkRemark());

			model.addAttribute("incentiveDeatilsData", incentiveSpecificDetails);
			// get Mongo file
			disEvaluationDocumentService.evalDocFromMongoDB(disViewEvaluate1, disEvalId, model);
			model.addAttribute("disViewEvaluate", disViewEvaluate1);

			model.addAttribute("raiseQuery", new RaiseQuery());

		} else {
			model.addAttribute("incentiveDeatilsData", incentiveSpecificDetails);
			// get Mongo file

			model.addAttribute("disViewEvaluate", new DisViewEvaluate());

			model.addAttribute("raiseQuery", new RaiseQuery());

		}
	}

	@Override
	@Query(value = "select PrepareAgendaNotes where appliId = :appliId ")
	public DISPrepareAgendaNotes findDisPrepAgendaNotesByAppliId(String appliId) {

		return dISPrepareAgendaNotesRepository.findDisPrepAgendaNotesByAppliId(appliId);
	}

	@Override
	public DISPrepareAgendaNotes savePrepareAgendaNotes(DISPrepareAgendaNotes disPrepareAgendaNotes) {

		return dISPrepareAgendaNotesRepository.save(disPrepareAgendaNotes);
	}

	@Override
	public List<DISPrepareAgendaNotes> findDisPrepAgendaNotesByUserId(String userid) {
		return dISPrepareAgendaNotesRepository.findDisPrepAgendaNotesByUserId(userid);
	}

	@Override
	public List<DISPrepareAgendaNotes> getAllDisPrepareAgendaNote() {
		return dISPrepareAgendaNotesRepository.findAll();
	}
}
