package com.webapp.ims.controller;

import static com.webapp.ims.exception.GlobalConstants.EVALUATE_APPLICATION;
import static com.webapp.ims.exception.GlobalConstants.EVALUATE_PROJECT_DETAILS;
import static com.webapp.ims.exception.GlobalConstants.EXISTING_PROJECT;
import static com.webapp.ims.exception.GlobalConstants.LARGE;
import static com.webapp.ims.exception.GlobalConstants.MEDIUM;
import static com.webapp.ims.exception.GlobalConstants.MEGA;
import static com.webapp.ims.exception.GlobalConstants.MEGAPLUS;
import static com.webapp.ims.exception.GlobalConstants.NATURE_OF_PROJECT;
import static com.webapp.ims.exception.GlobalConstants.NEW_PROJECT;
import static com.webapp.ims.exception.GlobalConstants.PRODUCTS;
import static com.webapp.ims.exception.GlobalConstants.PROPOSED_EMPLOYMENT_DETAILS;
import static com.webapp.ims.exception.GlobalConstants.PW_INVESTMENT_LIST;
import static com.webapp.ims.exception.GlobalConstants.SMALL;
import static com.webapp.ims.exception.GlobalConstants.SUPERMEGA;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.webapp.ims.uam.model.TblUsers;
import com.webapp.ims.uam.service.TblUsersService;
import com.webapp.ims.model.ApplicantDetails;
import com.webapp.ims.model.ApplicationFwdEntity;
import com.webapp.ims.model.BusinessEntityDetails;
import com.webapp.ims.model.DeptBusinessEntityDetails;
import com.webapp.ims.model.DeptIncentiveDetails;
import com.webapp.ims.model.DeptProposedEmploymentDetails;
import com.webapp.ims.model.Dept_PhaseWiseInvestmentDetails;
import com.webapp.ims.model.EvaluateCapitalInvestment;
import com.webapp.ims.model.EvaluateExistNewProjDetails;
import com.webapp.ims.model.EvaluateInvestmentDetails;
import com.webapp.ims.model.EvaluateMeanOfFinance;
import com.webapp.ims.model.EvaluateProjectDetails;
import com.webapp.ims.model.EvaluationAuditTrail;
import com.webapp.ims.model.IncentiveDetails;
import com.webapp.ims.model.IncentiveSpecificDetails;
import com.webapp.ims.model.InvestmentDetails;
import com.webapp.ims.model.PhaseWiseInvestmentDetails;
import com.webapp.ims.model.PrepareAgendaNotes;
import com.webapp.ims.model.ProposedEmploymentDetails;
import com.webapp.ims.model.ProprietorDetails;
import com.webapp.ims.model.RaiseQuery;
import com.webapp.ims.model.SkilledUnSkilledEmployemnt;
import com.webapp.ims.repository.ApplicantDetailsRepository;
import com.webapp.ims.repository.ApplicationFwdRepository;
import com.webapp.ims.repository.DeptBusinessEntityDetailsRepository;
import com.webapp.ims.repository.DeptIncentiveDetailsRepository;
import com.webapp.ims.repository.DeptPhaseWiseInvestmentDetailsRepository;
import com.webapp.ims.repository.DeptProposedEmploymentDetailsRepository;
import com.webapp.ims.repository.EvaluateAuditTrialRepository;
import com.webapp.ims.repository.EvaluateExistNewProjDetailsRepository;
import com.webapp.ims.repository.ExistingProjectDetailsRepository;
import com.webapp.ims.repository.InvestmentDetailsRepository;
import com.webapp.ims.repository.NewProjectRepository;
import com.webapp.ims.repository.ProjectRepository;
import com.webapp.ims.service.BusinessEntityDetailsService;
import com.webapp.ims.service.EvaluateCapInvestService;
import com.webapp.ims.service.EvaluateInvestMentDetailsService;
import com.webapp.ims.service.EvaluateMeanofFinanceService;
import com.webapp.ims.service.EvaluateProjectDetailsService;
import com.webapp.ims.service.EvaluationAuditTrailService;
import com.webapp.ims.service.INVInstalledCapacitiesService;
import com.webapp.ims.service.INVOthersService;
import com.webapp.ims.service.IncentiveDetailsService;
import com.webapp.ims.service.InvestmentDetailsService;
import com.webapp.ims.service.MeansOfFinanceService;
import com.webapp.ims.service.PhaseWiseInvestmentDetailsService;
import com.webapp.ims.service.PrepareAgendaNotesService;
import com.webapp.ims.service.ProposedEmploymentDetailsService;
import com.webapp.ims.service.ProprietorDetailsService;
import com.webapp.ims.service.WReturnCUSIDSTATUSBeanService;
import com.webapp.ims.service.impl.AdditionalInterest;
import com.webapp.ims.uam.model.TblUsers;
import com.webapp.ims.uam.service.TblUsersService;

@Controller
@Scope("session")
public class ViewAndEvaluateController {

	private final Logger logger = LoggerFactory.getLogger(ViewAndEvaluateController.class);
	private List<SkilledUnSkilledEmployemnt> skillUnskillEmplList = new ArrayList<>();
	private List<EvaluateCapitalInvestment> evalCapitalInvList = new LinkedList<>();

	private List<EvaluateMeanOfFinance> evalMeanofFinanceList = new LinkedList<>();

	private int epIndex = 0;
	private boolean isepRowRecord = false;

	@Autowired
	BusinessEntityDetailsService businessEntityDetailsService;
	@Autowired
	public ApplicationDetailsViewController applViewController;

	@Autowired
	ProprietorDetailsService propriterService;
	@Autowired
	private ExistingProjectDetailsRepository existProjRepository;

	@Autowired
	private NewProjectRepository newexistProjRepository;
	@Autowired
	public ApplicationDetailsViewController applicantViewController;

	@Autowired
	DeptPhaseWiseInvestmentDetailsRepository deptPwInvRepository;

	@Autowired
	private DeptBusinessEntityDetailsRepository deptBusEntRepository;

	@Autowired
	private DeptProposedEmploymentDetailsRepository deptPrpsEmplRepository;

	@Autowired
	private ProposedEmploymentDetailsService emplDtlService;

	@Autowired
	DeptPhaseWiseInvestmentDetailsRepository deptPWInvRepository;

	@Autowired
	InvestmentDetailsService investmentDetailsService;

	@Autowired
	AdditionalInterest additionalInterest;

	@Autowired
	IncentiveDetailsService incentiveDetailsService;

	@Autowired
	private TblUsersService loginService;

	@Autowired
	private EvaluateCapInvestService evalCapInvService;

	@Autowired
	private EvaluateMeanofFinanceService evalMOf;

	@Autowired
	InvestmentDetailsRepository investRepository;

	@Autowired
	ProjectRepository projectRepository;

	@Autowired
	private PrepareAgendaNotesService prepareAgendaNotesService;

	@Autowired
	private EvaluateProjectDetailsService evaluateProjectDetailsService;

	@Autowired
	public EvaluationAuditTrailService evaluationAuditTrailService;

	@Autowired
	public EvaluateAuditTrialRepository evaluateAuditTrialRepository;
	@Autowired
	private DeptIncentiveDetailsRepository deptIncRepository;

	@Autowired
	public EvaluateExistNewProjDetailsRepository evaluateExistNewProjDetailsRepository;

	@Autowired
	public EvaluateInvestMentDetailsService evaluateInvestMentDetailsService;
	@Autowired
	ApplicantDetailsRepository applicantDetailsRepository;

	@Autowired
	ApplicationFwdRepository applicationFwdRepository;

	@Autowired
	private MeansOfFinanceService meansOfFinanceService;

	@Autowired
	private INVInstalledCapacitiesService iNVInstalledCapacitiesService;

	@Autowired
	WReturnCUSIDSTATUSBeanService wReturnCUSIDSTATUSBeanService;
	private AtomicInteger atomicInteger = new AtomicInteger();

	@Autowired
	private INVOthersService invOthersService;

	@Autowired
	private PhaseWiseInvestmentDetailsService pwInvestDs;

	@InitBinder(EVALUATE_PROJECT_DETAILS)
	public void pwDateCustomize(WebDataBinder binder) {
		SimpleDateFormat dateFormatter = new SimpleDateFormat("dd/MM/yyyy");
		SimpleDateFormat dateFormatter1 = new SimpleDateFormat("yyyy-MM-dd");
		binder.registerCustomEditor(Date.class, "cAStatutoryDate", new CustomDateEditor(dateFormatter, true));
		binder.registerCustomEditor(Date.class, "invCommenceDate", new CustomDateEditor(dateFormatter1, true));
		binder.registerCustomEditor(Date.class, "propCommProdDate", new CustomDateEditor(dateFormatter1, true));
		binder.registerCustomEditor(Date.class, "CreatedDate", new CustomDateEditor(dateFormatter1, true));
	}

	@GetMapping(value = "/evaluatedNodalDeptHeadApplication")
	public ModelAndView evaluatedNodalDeptHeadApplications(
			@RequestParam(value = "applicantId", required = false) String applId, Model model,
			HttpServletRequest request, HttpSession session) {
		commonevaluateApplication(applId, model, request, session);
		return new ModelAndView("evaluation-view-head-department");
	}

	public void commonevaluateApplication(String applId, Model model, HttpServletRequest request, HttpSession session) {

		logger.debug("Evaluate Application Start");

		String appId = (String) session.getAttribute("appId");
		PrepareAgendaNotes prepareAgendaNotesList = prepareAgendaNotesService.findPrepAgendaNotesByAppliId(appId);
		if (prepareAgendaNotesList != null) {
			model.addAttribute("enableIncludeAgenda", "enableIncludeAgenda");
		} else {
			model.addAttribute("enableIncludeAgenda", "");
		}
		EvaluateProjectDetails evaluateProjectDetails = evaluateProjectDetailsService
				.getEvalProjDetByapplicantDetailId(appId);
		if (evaluateProjectDetails != null) {
			model.addAttribute(NEW_PROJECT, evaluateProjectDetails.getNewProject());
			model.addAttribute(NATURE_OF_PROJECT, evaluateProjectDetails.getNatureOfProject());
			model.addAttribute(EVALUATE_PROJECT_DETAILS, evaluateProjectDetails);
			model.addAttribute("region", evaluateProjectDetails.getResionName());
			model.addAttribute("district", evaluateProjectDetails.getDistrictName());
			// evaluateProjectDetails.getFullAddress();
			model.addAttribute("fullAddrs", evaluateProjectDetails.getFullAddress());
			String pergrosblock = additionalInterest.getgrossBlock(appId);
			model.addAttribute("pergrosblock", pergrosblock);

			String regorlic = additionalInterest.getregiOrLicense(appId);
			model.addAttribute("regorlic", regorlic);
			model.addAttribute("raiseQuery", new RaiseQuery());

		}
		model.addAttribute(EVALUATE_PROJECT_DETAILS, new EvaluateProjectDetails());
		String userId = (String) session.getAttribute("userId");
		List<EvaluationAuditTrail> EvaluationAuditTrailList = evaluateAuditTrialRepository
				.getEvaluAudTraByAppliIdUserId(appId, userId);

		model.addAttribute("EvaluationAuditTrailList", EvaluationAuditTrailList);
		// Proposed Employment Details view
		try {
			ProposedEmploymentDetails emplDetail = emplDtlService.getProposedEmploymentDetailsByappId(appId);
			if (emplDetail != null) {
				List<SkilledUnSkilledEmployemnt> skilledUnSkilledEmployemntslist = emplDetail
						.getSkilledUnSkilledEmployemnt();
				model.addAttribute(PROPOSED_EMPLOYMENT_DETAILS, emplDetail);
				skillUnskillEmplList.clear();
				skillUnskillEmplList.addAll(skilledUnSkilledEmployemntslist);
				model.addAttribute("skilledUnSkilledEmployemntList", skillUnskillEmplList);
				totalSkilledAndUnSkilledEmploment(model);
				model.addAttribute(PROPOSED_EMPLOYMENT_DETAILS, new ProposedEmploymentDetails());
				model.addAttribute("skilledUnSkilledEmployemnt", new SkilledUnSkilledEmployemnt());

			} else {
				skillUnskillEmplList.clear();
				model.addAttribute(PROPOSED_EMPLOYMENT_DETAILS, new ProposedEmploymentDetails());
				model.addAttribute("skilledUnSkilledEmployemnt", new SkilledUnSkilledEmployemnt());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {

			
			
PhaseWiseInvestmentDetails pwInvDetailFromDb = pwInvestDs.getPwInvDetailByPwApcId(appId);

			InvestmentDetails invDetailFromDb = investmentDetailsService.getInvestmentDetailsByapplId(appId);

			model.addAttribute("InvFCI", pwInvDetailFromDb.getInvFci());
			model.addAttribute("category", invDetailFromDb.getInvIndType());
			model.addAttribute("InvTPC", invDetailFromDb.getInvTotalProjCost());
			model.addAttribute("InvLC", pwInvDetailFromDb.getInvLandCost());
			model.addAttribute("InvBuiildC", pwInvDetailFromDb.getInvBuildingCost());
			model.addAttribute("invPlantAndMachCost", pwInvDetailFromDb.getInvPlantAndMachCost());
			model.addAttribute("invMisFixCost", pwInvDetailFromDb.getInvOtherCost());
			model.addAttribute("invMisFixCost", pwInvDetailFromDb.getInvOtherCost());

			String categorytype = invDetailFromDb.getInvIndType();

			if ((categorytype.equalsIgnoreCase(SMALL)) || (categorytype.equalsIgnoreCase(MEDIUM))) {
				request.setAttribute("ctypeSMALL", "Yes");
			} else {
				request.setAttribute("ctypeSMALL", "No");
			}

			if (categorytype.equalsIgnoreCase(LARGE)) {
				request.setAttribute("ctypeLARGE", "Yes");
			} else {
				request.setAttribute("ctypeLARGE", "No");
			}

			if ((categorytype.equalsIgnoreCase(MEGA)) || (categorytype.equalsIgnoreCase(MEGAPLUS))) {
				request.setAttribute("ctypeMEGA", "Yes");
			} else {
				request.setAttribute("ctypeMEGA", "No");
			}

			if (categorytype.equalsIgnoreCase(SUPERMEGA)) {
				request.setAttribute("ctypeSUPERMEGA", "Yes");
			} else {
				request.setAttribute("ctypeSUPERMEGA", "No");
			}

			String phsWsAply = invDetailFromDb.getPwApply();

			if (phsWsAply.equalsIgnoreCase("Yes")) {
				request.setAttribute("phsWsAply", "Yes");
			} else {
				request.setAttribute("phsWsAply", "No");
			}

			Object LCasperIEPP = additionalInterest.getProposedFCI(appId);
			model.addAttribute("LCasperIEPP", LCasperIEPP);

			String isfId = incentiveDetailsService.getIsfIdByApplId(appId);

			IncentiveSpecificDetails incentiveSpecificDetails = new IncentiveSpecificDetails();
			IncentiveDetails IncentiveDetail = incentiveDetailsService.getIncentiveisfById(isfId);
			if (IncentiveDetail != null) {
				incentiveSpecificDetails.setISF_Claim_Reim(IncentiveDetail.getISF_Claim_Reim());
				incentiveSpecificDetails.setISF_Reim_SCST(IncentiveDetail.getISF_Reim_SCST());
				incentiveSpecificDetails.setISF_Reim_FW(IncentiveDetail.getISF_Reim_FW());
				incentiveSpecificDetails.setISF_Reim_BPLW(IncentiveDetail.getISF_Reim_BPLW());
				incentiveSpecificDetails.setISF_Ttl_SGST_Reim(IncentiveDetail.getISF_Ttl_SGST_Reim());

				incentiveSpecificDetails.setISF_Stamp_Duty_EX(IncentiveDetail.getISF_Stamp_Duty_EX());
				incentiveSpecificDetails
						.setISF_Additonal_Stamp_Duty_EX(IncentiveDetail.getISF_Additonal_Stamp_Duty_EX());
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

				incentiveSpecificDetails.setIsfSgstComment(IncentiveDetail.getIsfSgstComment());
				incentiveSpecificDetails.setIsfScstComment(IncentiveDetail.getIsfScstComment());
				incentiveSpecificDetails.setIsffwComment(IncentiveDetail.getIsffwComment());
				incentiveSpecificDetails.setIsfBplComment(IncentiveDetail.getIsfBplComment());
				incentiveSpecificDetails.setIsfElecdutyComment(IncentiveDetail.getIsfElecdutyComment());
				incentiveSpecificDetails.setIsfMandiComment(IncentiveDetail.getIsfMandiComment());

				incentiveSpecificDetails.setIsfStampComment(IncentiveDetail.getIsfStampComment());
				incentiveSpecificDetails.setIsfStampremComment(IncentiveDetail.getIsfStampremComment());
				incentiveSpecificDetails.setIsfStampscstComment(IncentiveDetail.getIsfStampscstComment());
				incentiveSpecificDetails.setIsfepfComment(IncentiveDetail.getIsfepfComment());
				incentiveSpecificDetails.setIsfepfaddComment(IncentiveDetail.getIsfepfaddComment());
				incentiveSpecificDetails.setIsfepfscComment(IncentiveDetail.getIsfepfscComment());
				incentiveSpecificDetails.setIsfcapisComment(IncentiveDetail.getIsfcapisComment());
				incentiveSpecificDetails.setIsfcapisaComment(IncentiveDetail.getIsfcapisaComment());
				incentiveSpecificDetails.setIsfinfComment(IncentiveDetail.getIsfinfComment());
				incentiveSpecificDetails.setIsfinfaComment(IncentiveDetail.getIsfinfaComment());
				incentiveSpecificDetails.setIsfloanComment(IncentiveDetail.getIsfloanComment());
				incentiveSpecificDetails.setIsfdisComment(IncentiveDetail.getIsfdisComment());
				incentiveSpecificDetails.setIsfelepodownComment(IncentiveDetail.getIsfelepodownComment());
				incentiveSpecificDetails.setIsfdifferabilComment(IncentiveDetail.getIsfdifferabilComment());

				incentiveSpecificDetails.setOthAddRequest1(IncentiveDetail.getOthAddRequest1());

				incentiveSpecificDetails.setId(IncentiveDetail.getId());

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
					logger.error("total  exception **** " + e.getMessage());
					e.printStackTrace();
				}
			}
			model.addAttribute("appId", appId);
			model.addAttribute("incentiveDeatilsData", incentiveSpecificDetails);

		} catch (Exception e) {
			logger.error("InvestmentDetails exception #### " + e.getMessage());
			e.printStackTrace();
		}
		logger.debug("Evaluate Application End ");

	}

	/**
	 * The responsibility of this method is to fetch records from tables and set
	 * into model.
	 */
	public void common(Model model, HttpServletRequest request, HttpSession session) {

		String appId = (String) session.getAttribute("appId");
		String businId = "";
		String appStr = appId.substring(0, appId.length() - 2);
		businId = appStr + "B1";
		String projId = appStr + "P1";

		PrepareAgendaNotes prepareAgendaNotesList = prepareAgendaNotesService.findPrepAgendaNotesByAppliId(appId);
		if (prepareAgendaNotesList != null) {
			model.addAttribute("enableIncludeAgenda", "enableIncludeAgenda");
		} else {
			model.addAttribute("enableIncludeAgenda", "");
		}
		EvaluateProjectDetails evaluateProjectDetails = evaluateProjectDetailsService
				.getEvalProjDetByapplicantDetailId(appId);
		if (evaluateProjectDetails != null) {

			model.addAttribute(NEW_PROJECT, evaluateProjectDetails.getNewProject());
			model.addAttribute("region", evaluateProjectDetails.getResionName().trim());
			model.addAttribute("district", evaluateProjectDetails.getDistrictName().trim());
			model.addAttribute("prodDetailObserv", evaluateProjectDetails.getProdDetailObserv());
			model.addAttribute("propsProdtDetailObserv", evaluateProjectDetails.getPropsProdtDetailObserv());

			model.addAttribute("fullAddrs", evaluateProjectDetails.getFullAddress());
			model.addAttribute("listAssets", evaluateProjectDetails.getListAssets());
			model.addAttribute("listAssetsObserv", evaluateProjectDetails.getListAssetsObserv());
			model.addAttribute("anexurUndertkObserv", evaluateProjectDetails.getAnexurUndertkObserv());
			model.addAttribute("expDivfObserv", evaluateProjectDetails.getExpDivfObserv());
			model.addAttribute("locationObserv", evaluateProjectDetails.getLocationObserv());
			model.addAttribute("projectObserv", evaluateProjectDetails.getProjectObserv());

			String expension = evaluateProjectDetails.getExpansion() != null ? evaluateProjectDetails.getExpansion()
					: "";
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
				model.addAttribute("newProjProdDetail", evaluateProjectDetails.getProductDetails());

			}

			String pergrosblock = additionalInterest.getgrossBlock(appId);
			model.addAttribute("pergrosblock", pergrosblock);

			String regorlic = additionalInterest.getregiOrLicense(appId);
			model.addAttribute("regorlic", regorlic);
			model.addAttribute("raiseQuery", new RaiseQuery());

		}
		model.addAttribute("raiseQuery", new RaiseQuery());
		String userId = (String) session.getAttribute("userId");
		List<EvaluationAuditTrail> EvaluationAuditTrailList = evaluateAuditTrialRepository
				.getEvaluAudTraByAppliIdUserId(appId, userId);
		model.addAttribute("EvaluationAuditTrailList", EvaluationAuditTrailList);

		// Propriter Details
		List<ProprietorDetails> proprietorDetailsList = propriterService.findAllByBusinessId(businId);
		model.addAttribute("ProprietorDetailsList", proprietorDetailsList);

		// Proposed Employment Details view
		try {
			ProposedEmploymentDetails emplDetail = emplDtlService.getProposedEmploymentDetailsByappId(appId);
			if (emplDetail != null) {
				List<SkilledUnSkilledEmployemnt> skilledUnSkilledEmployemntslist = emplDetail
						.getSkilledUnSkilledEmployemnt();
				model.addAttribute(PROPOSED_EMPLOYMENT_DETAILS, emplDetail);
				skillUnskillEmplList.clear();
				skillUnskillEmplList.addAll(skilledUnSkilledEmployemntslist);
				model.addAttribute("skilledUnSkilledEmployemntList", skillUnskillEmplList);
				totalSkilledAndUnSkilledEmploment(model);
				model.addAttribute(PROPOSED_EMPLOYMENT_DETAILS, new ProposedEmploymentDetails());
				model.addAttribute("skilledUnSkilledEmployemnt", new SkilledUnSkilledEmployemnt());

			} else {
				skillUnskillEmplList.clear();
				model.addAttribute(PROPOSED_EMPLOYMENT_DETAILS, new ProposedEmploymentDetails());
				model.addAttribute("skilledUnSkilledEmployemnt", new SkilledUnSkilledEmployemnt());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {

			if (!deptPwInvRepository.findByPwApcId(appId).isEmpty()) {
				List<Dept_PhaseWiseInvestmentDetails> deptPwInvList = deptPwInvRepository.findByPwApcId(appId);
				model.addAttribute("deptPwInvList", deptPwInvList);
			}

			EvaluateInvestmentDetails evaluateInvestMentDetails = null;
			if (evaluateInvestMentDetailsService.getEvalInvestDetByapplId(appId) != null) {
				evaluateInvestMentDetails = evaluateInvestMentDetailsService.getEvalInvestDetByapplId(appId);
			}

			// fetch documents from MongoDB
			applicantViewController.investMongoDBDoc(evaluateInvestMentDetails.getInvId(), model);

			model.addAttribute("InvFCI", evaluateInvestMentDetails.getInvFci());
			model.addAttribute("optedcufoffdate", evaluateInvestMentDetails.getInvCommenceDate());
			model.addAttribute("invPropCommProdDate", evaluateInvestMentDetails.getPropCommProdDate());
			model.addAttribute("category", evaluateInvestMentDetails.getInvIndType());
			model.addAttribute("InvTPC", evaluateInvestMentDetails.getInvTotalProjCost());
			model.addAttribute("InvLC", evaluateInvestMentDetails.getInvLandCost());
			model.addAttribute("InvBuiildC", evaluateInvestMentDetails.getInvBuildingCost());
			model.addAttribute("invPlantAndMachCost", evaluateInvestMentDetails.getInvPlantAndMachCost());
			model.addAttribute("invMisFixCost", evaluateInvestMentDetails.getInvOtherCost());
			model.addAttribute("invMisFixCost", evaluateInvestMentDetails.getInvOtherCost());
			evaluateProjectDetails.setInvFci(evaluateInvestMentDetails.getInvFci());
			evaluateProjectDetails.setInvTotalProjCost(evaluateInvestMentDetails.getInvTotalProjCost());
			evaluateProjectDetails.setInvIndType(evaluateInvestMentDetails.getInvIndType());

			model.addAttribute("invIemNumber", evaluateInvestMentDetails.getInvIemNumber());
			model.addAttribute("invGovtEquity", evaluateInvestMentDetails.getInvGovtEquity());
			model.addAttribute("invEligcapInvest", evaluateInvestMentDetails.getInvEligcapInvest());
			model.addAttribute("invCAStatutoryAmount", evaluateInvestMentDetails.getInvCAStatutoryAmt());

			model.addAttribute("invLandcostFci", evaluateInvestMentDetails.getLandcostFci());
			model.addAttribute("invBuildingFci", evaluateInvestMentDetails.getBuildingFci());
			model.addAttribute("invPlantAndMachFci", evaluateInvestMentDetails.getPlantAndMachFci());
			model.addAttribute("invFixedAssetFci", evaluateInvestMentDetails.getFixedAssetFci());
			model.addAttribute("invLandcostIIEPP", evaluateInvestMentDetails.getLandcostIIEPP());
			if (evaluateInvestMentDetails.getBuildingIIEPP() != null) {
				model.addAttribute("invBuildingIIEPP", evaluateInvestMentDetails.getBuildingIIEPP());
			} else {
				model.addAttribute("invBuildingIIEPP", evaluateInvestMentDetails.getInvLandCost());
			}
			if (evaluateInvestMentDetails.getPlantAndMachIIEPP() != null) {
				model.addAttribute("invPlantAndMachIIEPP", evaluateInvestMentDetails.getPlantAndMachIIEPP());
			} else {
				model.addAttribute("invPlantAndMachIIEPP", evaluateInvestMentDetails.getInvPlantAndMachCost());
			}
			if (evaluateInvestMentDetails.getFixedAssetIIEPP() != null) {
				model.addAttribute("invFixedAssetIIEPP", evaluateInvestMentDetails.getFixedAssetIIEPP());
			} else {
				model.addAttribute("invFixedAssetIIEPP", evaluateInvestMentDetails.getInvOtherCost());
			}

			evaluateProjectDetails.setCatIndusUndtObserv(evaluateInvestMentDetails.getCatIndusUndtObserv());
			evaluateProjectDetails.setPropsPlntMcnryCostObserv(evaluateInvestMentDetails.getPropsPlntMcnryCostObserv());
			evaluateProjectDetails.setOptcutofdateobserv(evaluateInvestMentDetails.getOptcutofdateobserv());
			evaluateProjectDetails.setDateofComProdObserv(evaluateInvestMentDetails.getDateofComProdObserv());
			evaluateProjectDetails.setDetailProjReportObserv(evaluateInvestMentDetails.getDetailProjReportObserv());
			evaluateProjectDetails.setPropCapInvObserv(evaluateInvestMentDetails.getPropCapInvObserv());
			evaluateProjectDetails.setTotlCostProjObserv(evaluateInvestMentDetails.getTotlCostProjObserv());
			evaluateProjectDetails.setMofObserv(evaluateInvestMentDetails.getMofObserv());
			evaluateProjectDetails.setIemRegObserv(evaluateInvestMentDetails.getIemRegObserv());
			evaluateProjectDetails.setIndusUntkObserv(evaluateInvestMentDetails.getIndusUntkObserv());

			evaluateProjectDetails.setEligblInvPerdLargeObserv(evaluateInvestMentDetails.getEligblInvPerdLargeObserv());
			evaluateProjectDetails.setEligblInvPerdMegaObserv(evaluateInvestMentDetails.getEligblInvPerdMegaObserv());
			evaluateProjectDetails
					.setEligblInvPerdSupermegaObserv(evaluateInvestMentDetails.getEligblInvPerdSupermegaObserv());
			evaluateProjectDetails.setEligblCapInvObserv(evaluateInvestMentDetails.getEligblCapInvObserv());
			evaluateProjectDetails.setProjPhasesObserv(evaluateInvestMentDetails.getProjPhasesObserv());
			evaluateProjectDetails.setCaStatutoryObserv(evaluateInvestMentDetails.getCaStatutoryObserv());
			evaluateProjectDetails.setAuthSignatoryObserv(evaluateInvestMentDetails.getAuthSignatoryObserv());
			evaluateProjectDetails.setIemRegObserv(evaluateInvestMentDetails.getIemRegObserv());
			evaluateProjectDetails.setAppformatObserv(evaluateInvestMentDetails.getAppformatObserv());
			evaluateProjectDetails.setcAStatutoryDate(evaluateInvestMentDetails.getInvCAStatutoryDate());

			/*
			 * if (evaluateInvestMentDetails.getInvCAStatutoryDate() != null) { try {
			 * DateFormat srcDf = new SimpleDateFormat("yyyy-MM-dd"); // parse the date
			 * string into Date object Date date =
			 * srcDf.parse(evaluateInvestMentDetails.getInvCAStatutoryDate().toString());
			 * DateFormat destDf = new SimpleDateFormat("dd/MM/yyyy"); // format the date
			 * into another format String dateStr = destDf.format(date);
			 * 
			 * model.addAttribute("invCAStatutoryDate", dateStr); } catch (ParseException e)
			 * { logger.error(String.format("8888 dateFormat exception 8888 %s",
			 * e.getMessage())); } } else { model.addAttribute("invCAStatutoryDate",
			 * evaluateInvestMentDetails.getInvCAStatutoryDate()); }
			 */

			// EvaluateApplication Breakup of proposed capital Investment Remarks
			evaluateProjectDetails.setFixedAssetRemarks(evaluateInvestMentDetails.getFixedAssetRemarks());
			evaluateProjectDetails.setPlantAndMachRemarks(evaluateInvestMentDetails.getPlantAndMachRemarks());
			evaluateProjectDetails.setBuildingRemarks(evaluateInvestMentDetails.getBuildingRemarks());
			evaluateProjectDetails.setLandcostRemarks(evaluateInvestMentDetails.getLandcostRemarks());

			String categorytype = evaluateInvestMentDetails.getInvIndType();

			if ((categorytype.equalsIgnoreCase(SMALL)) || (categorytype.equalsIgnoreCase(MEDIUM))) {
				request.setAttribute("ctypeSMALL", "Yes");
			} else {
				request.setAttribute("ctypeSMALL", "No");
			}

			if (categorytype.equalsIgnoreCase(LARGE)) {
				request.setAttribute("ctypeLARGE", "Yes");
			} else {
				request.setAttribute("ctypeLARGE", "No");
			}

			if ((categorytype.equalsIgnoreCase(MEGA)) || (categorytype.equalsIgnoreCase(MEGAPLUS))) {
				request.setAttribute("ctypeMEGA", "Yes");
			} else {
				request.setAttribute("ctypeMEGA", "No");
			}

			if (categorytype.equalsIgnoreCase(SUPERMEGA)) {
				request.setAttribute("ctypeSUPERMEGA", "Yes");
			} else {
				request.setAttribute("ctypeSUPERMEGA", "No");
			}

			String phsWsAply = evaluateInvestMentDetails.getPwApply();

			if (phsWsAply.equalsIgnoreCase("Yes")) {
				request.setAttribute("phsWsAply", "Yes");
			} else {
				request.setAttribute("phsWsAply", "No");
			}

			Object LCasperIEPP = additionalInterest.getProposedFCI(appId);
			model.addAttribute("LCasperIEPP", LCasperIEPP);

			// BusinessEntityDetails records fetch from table

			DeptBusinessEntityDetails deptBusEntFromDb = deptBusEntRepository.findByApplicantDetailId(appId);
			model.addAttribute("authSignName", deptBusEntFromDb.getAuthorisedSignatoryName());
			model.addAttribute("GSTno", deptBusEntFromDb.getGstin());
			model.addAttribute("companyPANno", deptBusEntFromDb.getCompanyPanNo());
			model.addAttribute("businessAddress", deptBusEntFromDb.getBusinessAddress());
			model.addAttribute("isPresFormat", deptBusEntFromDb.getPresFormat());
			model.addAttribute("isDocAuthorized", deptBusEntFromDb.getDocAuthorized());

			evaluateProjectDetails.setDirDetailsObserv(deptBusEntFromDb.getDirDetailsObserv());
			evaluateProjectDetails.setSupprtdocObserv(deptBusEntFromDb.getSupprtdocObserv());
			evaluateProjectDetails.setGstinObserv(deptBusEntFromDb.getGstinObserv());
			evaluateProjectDetails.setPanObserv(deptBusEntFromDb.getPanObserv());

			// PropsedEmpDetails
			DeptProposedEmploymentDetails deptProEmploymentDetails = deptPrpsEmplRepository.findByappId(appId);
			evaluateProjectDetails.setTotalDetailObserv(deptProEmploymentDetails.getTotalDetailObserv());

			// fetch BusinessEntity documents from MongoDB
			applicantViewController.businessDocFromMongoDB(deptBusEntFromDb.getId(), model);

			// incentive details
			String isfId = incentiveDetailsService.getIsfIdByApplId(appId);

			IncentiveSpecificDetails incentiveSpecificDetails = new IncentiveSpecificDetails();
			IncentiveDetails IncentiveDetail = incentiveDetailsService.getIncentiveisfById(isfId);
			DeptIncentiveDetails deptIncDetails = deptIncRepository.findById(isfId);

			if (IncentiveDetail != null) {
				// PICUP Officer's Remark
				incentiveSpecificDetails.setSgstRemark(deptIncDetails.getSgstRemark());
				incentiveSpecificDetails.setScstRemark(deptIncDetails.getScstRemark());
				incentiveSpecificDetails.setStampDutyExemptRemark(deptIncDetails.getStampDutyExemptRemark());
				incentiveSpecificDetails.setStampDutyReimRemark(deptIncDetails.getStampDutyReimRemark());
				incentiveSpecificDetails.setCapIntSubRemark(deptIncDetails.getCapIntSubRemark());
				incentiveSpecificDetails.setInfraIntSubRemark(deptIncDetails.getInfraIntSubRemark());
				incentiveSpecificDetails.setLoanIntSubRemark(deptIncDetails.getLoanIntSubRemark());
				incentiveSpecificDetails.setInputTaxRemark(deptIncDetails.getInputTaxRemark());
				incentiveSpecificDetails.setElecDutyCapRemark(deptIncDetails.getElecDutyCapRemark());
				incentiveSpecificDetails.setElecDutyDrawnRemark(deptIncDetails.getElecDutyDrawnRemark());
				incentiveSpecificDetails.setDiffAbleWorkRemark(deptIncDetails.getDiffAbleWorkRemark());
				incentiveSpecificDetails.setMandiFeeRemark(deptIncDetails.getMandiFeeRemark());
				incentiveSpecificDetails.setFwRemark(deptIncDetails.getFwRemark());
				incentiveSpecificDetails.setBplRemark(deptIncDetails.getBplRemark());
				incentiveSpecificDetails.setDivyangSCSTRemark(deptIncDetails.getDivyangSCSTRemark());
				incentiveSpecificDetails.setEpfDvngSCSTRemark(deptIncDetails.getEpfDvngSCSTRemark());
				incentiveSpecificDetails.setEpfSklUnsklRemark(deptIncDetails.getEpfSklUnsklRemark());
				incentiveSpecificDetails.setEpfUnsklRemark(deptIncDetails.getEpfUnsklRemark());
				incentiveSpecificDetails.setAciSubsidyRemark(deptIncDetails.getAciSubsidyRemark());
				incentiveSpecificDetails.setAiiSubsidyRemark(deptIncDetails.getAiiSubsidyRemark());

				incentiveSpecificDetails.setISF_Claim_Reim(IncentiveDetail.getISF_Claim_Reim());
				incentiveSpecificDetails.setISF_Reim_SCST(IncentiveDetail.getISF_Reim_SCST());
				incentiveSpecificDetails.setISF_Reim_FW(IncentiveDetail.getISF_Reim_FW());
				incentiveSpecificDetails.setISF_Reim_BPLW(IncentiveDetail.getISF_Reim_BPLW());
				incentiveSpecificDetails.setISF_Ttl_SGST_Reim(IncentiveDetail.getISF_Ttl_SGST_Reim());

				incentiveSpecificDetails.setISF_Stamp_Duty_EX(IncentiveDetail.getISF_Stamp_Duty_EX());
				incentiveSpecificDetails
						.setISF_Additonal_Stamp_Duty_EX(IncentiveDetail.getISF_Additonal_Stamp_Duty_EX());
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

				incentiveSpecificDetails.setIsfSgstComment(IncentiveDetail.getIsfSgstComment());
				incentiveSpecificDetails.setIsfScstComment(IncentiveDetail.getIsfScstComment());
				incentiveSpecificDetails.setIsffwComment(IncentiveDetail.getIsffwComment());
				incentiveSpecificDetails.setIsfBplComment(IncentiveDetail.getIsfBplComment());
				incentiveSpecificDetails.setIsfElecdutyComment(IncentiveDetail.getIsfElecdutyComment());
				incentiveSpecificDetails.setIsfMandiComment(IncentiveDetail.getIsfMandiComment());
				incentiveSpecificDetails.setIsfStampComment(IncentiveDetail.getIsfStampComment());

				incentiveSpecificDetails.setIsfStampremComment(IncentiveDetail.getIsfStampremComment());
				incentiveSpecificDetails.setIsfStampscstComment(IncentiveDetail.getIsfStampscstComment());
				incentiveSpecificDetails.setIsfepfComment(IncentiveDetail.getIsfepfComment());
				incentiveSpecificDetails.setIsfepfaddComment(IncentiveDetail.getIsfepfaddComment());
				incentiveSpecificDetails.setIsfepfscComment(IncentiveDetail.getIsfepfscComment());
				incentiveSpecificDetails.setIsfcapisComment(IncentiveDetail.getIsfcapisComment());
				incentiveSpecificDetails.setIsfcapisaComment(IncentiveDetail.getIsfcapisaComment());
				incentiveSpecificDetails.setIsfinfComment(IncentiveDetail.getIsfinfComment());
				incentiveSpecificDetails.setIsfinfaComment(IncentiveDetail.getIsfinfaComment());
				incentiveSpecificDetails.setIsfloanComment(IncentiveDetail.getIsfloanComment());
				incentiveSpecificDetails.setIsfdisComment(IncentiveDetail.getIsfdisComment());
				incentiveSpecificDetails.setIsfelepodownComment(IncentiveDetail.getIsfelepodownComment());
				incentiveSpecificDetails.setIsfdifferabilComment(IncentiveDetail.getIsfdifferabilComment());

				incentiveSpecificDetails.setOthAddRequest1(IncentiveDetail.getOthAddRequest1());

				incentiveSpecificDetails.setId(IncentiveDetail.getId());

				if (deptIncDetails.getModify_Date() != null) {
					try {
						DateFormat srcDf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
						// parse the date string into Date object
						Date date = srcDf.parse(deptIncDetails.getModify_Date().toString());
						DateFormat destDf = new SimpleDateFormat("yyyy-MM-dd");
						// format the date into another format
						String dateStr = destDf.format(date);
						model.addAttribute("onlineSubmitDate", dateStr);
					} catch (ParseException e) {
						logger.error(String.format("8888 dateFormat exception 8888 %s", e.getMessage()));
					}
				} else {
					model.addAttribute("onlineSubmitDate", deptIncDetails.getModify_Date());
				}

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
				List<Object[]> productsList = existProjRepository.findProductsByProjId(projId);
				if (!productsList.isEmpty()) {
					for (Object[] prodArr : productsList) {
						if (prodArr.length > 0) {
							for (int k = 0; k < prodArr.length; k++) {
								products = products.append(prodArr[k].toString()).append(", ");
							}
						}
					}
					model.addAttribute(PRODUCTS,
							products.toString().substring(0, products.toString().lastIndexOf(',')));
				}
			}

			else if (evaluateProjectDetails.getNatureOfProject().equalsIgnoreCase(NEW_PROJECT)) {
				List<String> newproductsList = newexistProjRepository.findProductsByProjId(projId);

				if (!newproductsList.isEmpty()) {
					for (int k = 0; k < newproductsList.size(); k++) {
						products = products.append(newproductsList.get(k)).append(", ");
					}
					model.addAttribute(PRODUCTS,
							products.toString().substring(0, products.toString().lastIndexOf(',')));
				}
			}

			List<Object> evalObjList = investRepository.getEvalDetailsByApplId(appId);
			model.addAttribute("evalObjList", evalObjList);
			applViewController.existProjDocsFromMongoDB(projId, model);

			model.addAttribute("appId", appId);

			model.addAttribute("incentiveDeatilsData", incentiveSpecificDetails);
			model.addAttribute(EVALUATE_PROJECT_DETAILS, evaluateProjectDetails);
		} catch (Exception e) {
			logger.error(String.format("^^^^common evaluateApplication exception ^^^^^ %s", e.getMessage()));
		}

	}

	@GetMapping(value = "/evaluateApplication")
	public String evaluateApplication(@RequestParam(value = "applicantId", required = false) String applId, Model model,
			HttpServletRequest request, HttpSession session) {
		logger.debug("Evaluate Application Start");

		String appId = (String) session.getAttribute("appId");
		String businId = "";
		String appStr = appId.substring(0, appId.length() - 2);
		businId = appStr + "B1";
		String projId = appStr + "P1";

		try {
			ApplicantDetails applicantDetails = applicantDetailsRepository.getApplicantDetailsByAppId(appId);
			if (applicantDetails != null) {
				applicantDetails.setStatusCode("05");
				applicantDetails.setRemarks("Application Form Forwarded To Department ");
				applicantDetails.setPendancyLevel("Department Name ");

				wReturnCUSIDSTATUSBeanService.WReturn_CUSID_STATUS(applicantDetails);
			}

		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
		}

		PrepareAgendaNotes prepareAgendaNotesList = prepareAgendaNotesService.findPrepAgendaNotesByAppliId(appId);
		if (prepareAgendaNotesList != null) {
			model.addAttribute("enableIncludeAgenda", "enableIncludeAgenda");
		} else {
			model.addAttribute("enableIncludeAgenda", "");
		}
		EvaluateProjectDetails evaluateProjectDetails = evaluateProjectDetailsService
				.getEvalProjDetByapplicantDetailId(appId);
		if (evaluateProjectDetails != null) {

			model.addAttribute(NEW_PROJECT, evaluateProjectDetails.getNewProject());
			model.addAttribute("region", evaluateProjectDetails.getResionName().trim());
			model.addAttribute("district", evaluateProjectDetails.getDistrictName().trim());
			model.addAttribute("prodDetailObserv", evaluateProjectDetails.getProdDetailObserv());
			model.addAttribute("propsProdtDetailObserv", evaluateProjectDetails.getPropsProdtDetailObserv());
			model.addAttribute("listAssets", evaluateProjectDetails.getListAssets());
			model.addAttribute("listAssetsObserv", evaluateProjectDetails.getListAssetsObserv());
			model.addAttribute("anexurUndertkObserv", evaluateProjectDetails.getAnexurUndertkObserv());
			model.addAttribute("expDivfObserv", evaluateProjectDetails.getExpDivfObserv());
			model.addAttribute("locationObserv", evaluateProjectDetails.getLocationObserv());
			model.addAttribute("projectObserv", evaluateProjectDetails.getProjectObserv());
			model.addAttribute("fullAddrs", evaluateProjectDetails.getFullAddress());

			String expension = evaluateProjectDetails.getExpansion() != null ? evaluateProjectDetails.getExpansion()
					: "";
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
				model.addAttribute("newProjProdDetail", evaluateProjectDetails.getProductDetails());

			}

			String pergrosblock = additionalInterest.getgrossBlock(appId);
			model.addAttribute("pergrosblock", pergrosblock);

			String regorlic = additionalInterest.getregiOrLicense(appId);
			model.addAttribute("regorlic", regorlic);
			model.addAttribute("raiseQuery", new RaiseQuery());

		}
		model.addAttribute("raiseQuery", new RaiseQuery());
		String userId = (String) session.getAttribute("userId");
		List<EvaluationAuditTrail> EvaluationAuditTrailList = evaluateAuditTrialRepository
				.getEvaluAudTraByAppliIdUserId(appId, userId);
		model.addAttribute("EvaluationAuditTrailList", EvaluationAuditTrailList);

		// Propriter Details
		List<ProprietorDetails> proprietorDetailsList = propriterService.findAllByBusinessId(businId);
		model.addAttribute("ProprietorDetailsList", proprietorDetailsList);

		// Proposed Employment Details view
		try {
			ProposedEmploymentDetails emplDetail = emplDtlService.getProposedEmploymentDetailsByappId(appId);
			if (emplDetail != null) {
				List<SkilledUnSkilledEmployemnt> skilledUnSkilledEmployemntslist = emplDetail
						.getSkilledUnSkilledEmployemnt();
				model.addAttribute(PROPOSED_EMPLOYMENT_DETAILS, emplDetail);
				skillUnskillEmplList.clear();
				skillUnskillEmplList.addAll(skilledUnSkilledEmployemntslist);
				model.addAttribute("skilledUnSkilledEmployemntList", skillUnskillEmplList);
				totalSkilledAndUnSkilledEmploment(model);
				model.addAttribute(PROPOSED_EMPLOYMENT_DETAILS, new ProposedEmploymentDetails());
				model.addAttribute("skilledUnSkilledEmployemnt", new SkilledUnSkilledEmployemnt());

			} else {
				skillUnskillEmplList.clear();
				model.addAttribute(PROPOSED_EMPLOYMENT_DETAILS, new ProposedEmploymentDetails());
				model.addAttribute("skilledUnSkilledEmployemnt", new SkilledUnSkilledEmployemnt());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {

			if (!deptPwInvRepository.findByPwApcId(appId).isEmpty()) {
				List<Dept_PhaseWiseInvestmentDetails> deptPwInvList = deptPwInvRepository.findByPwApcId(appId);
				model.addAttribute("deptPwInvList", deptPwInvList);
			}

			EvaluateInvestmentDetails evaluateInvestMentDetails = null;
			if (evaluateInvestMentDetailsService.getEvalInvestDetByapplId(appId) != null) {
				evaluateInvestMentDetails = evaluateInvestMentDetailsService.getEvalInvestDetByapplId(appId);
			}

			// fetch documents from MongoDB
			applicantViewController.investMongoDBDoc(evaluateInvestMentDetails.getInvId(), model);

			model.addAttribute("InvFCI", evaluateInvestMentDetails.getInvFci());
			model.addAttribute("momlist", meansOfFinanceService.getMeansOfFinanceList());
			model.addAttribute("optedcufoffdate", evaluateInvestMentDetails.getInvCommenceDate());
			model.addAttribute("invPropCommProdDate", evaluateInvestMentDetails.getPropCommProdDate());
			model.addAttribute("category", evaluateInvestMentDetails.getInvIndType());
			model.addAttribute("InvTPC", evaluateInvestMentDetails.getInvTotalProjCost());
			model.addAttribute("InvLC", evaluateInvestMentDetails.getInvLandCost());
			model.addAttribute("InvBuiildC", evaluateInvestMentDetails.getInvBuildingCost());
			model.addAttribute("invPlantAndMachCost", evaluateInvestMentDetails.getInvPlantAndMachCost());
			model.addAttribute("invMisFixCost", evaluateInvestMentDetails.getInvOtherCost());
			model.addAttribute("invMisFixCost", evaluateInvestMentDetails.getInvOtherCost());
			evaluateProjectDetails.setInvFci(evaluateInvestMentDetails.getInvFci());
			evaluateProjectDetails.setInvTotalProjCost(evaluateInvestMentDetails.getInvTotalProjCost());
			evaluateProjectDetails.setInvIndType(evaluateInvestMentDetails.getInvIndType());

			model.addAttribute("invIemNumber", evaluateInvestMentDetails.getInvIemNumber());
			model.addAttribute("invGovtEquity", evaluateInvestMentDetails.getInvGovtEquity());
			model.addAttribute("invEligcapInvest", evaluateInvestMentDetails.getInvEligcapInvest());
			model.addAttribute("invCAStatutoryAmount", evaluateInvestMentDetails.getInvCAStatutoryAmt());

			model.addAttribute("invLandcostFci", evaluateInvestMentDetails.getLandcostFci());
			model.addAttribute("invic",
					iNVInstalledCapacitiesService.getAllByINV_ID(evaluateInvestMentDetails.getInvId()));
			model.addAttribute("invotherlist", invOthersService.getAllByINV_ID(evaluateInvestMentDetails.getInvId()));

			model.addAttribute(PW_INVESTMENT_LIST,
					pwInvestDs.getPwInvDetailListById(evaluateInvestMentDetails.getInvId()));

			model.addAttribute("invBuildingFci", evaluateInvestMentDetails.getBuildingFci());
			model.addAttribute("invPlantAndMachFci", evaluateInvestMentDetails.getPlantAndMachFci());
			model.addAttribute("invFixedAssetFci", evaluateInvestMentDetails.getFixedAssetFci());
			model.addAttribute("invLandcostIIEPP", evaluateInvestMentDetails.getLandcostIIEPP());
			if (evaluateInvestMentDetails.getBuildingIIEPP() != null) {
				model.addAttribute("invBuildingIIEPP", evaluateInvestMentDetails.getBuildingIIEPP());
			} else {
				model.addAttribute("invBuildingIIEPP", evaluateInvestMentDetails.getInvLandCost());
			}
			if (evaluateInvestMentDetails.getPlantAndMachIIEPP() != null) {
				model.addAttribute("invPlantAndMachIIEPP", evaluateInvestMentDetails.getPlantAndMachIIEPP());
			} else {
				model.addAttribute("invPlantAndMachIIEPP", evaluateInvestMentDetails.getInvPlantAndMachCost());
			}
			if (evaluateInvestMentDetails.getFixedAssetIIEPP() != null) {
				model.addAttribute("invFixedAssetIIEPP", evaluateInvestMentDetails.getFixedAssetIIEPP());
			} else {
				model.addAttribute("invFixedAssetIIEPP", evaluateInvestMentDetails.getInvOtherCost());
			}

			evaluateProjectDetails.setCatIndusUndtObserv(evaluateInvestMentDetails.getCatIndusUndtObserv());
			evaluateProjectDetails.setPropsPlntMcnryCostObserv(evaluateInvestMentDetails.getPropsPlntMcnryCostObserv());
			evaluateProjectDetails.setOptcutofdateobserv(evaluateInvestMentDetails.getOptcutofdateobserv());
			evaluateProjectDetails.setDateofComProdObserv(evaluateInvestMentDetails.getDateofComProdObserv());
			evaluateProjectDetails.setDetailProjReportObserv(evaluateInvestMentDetails.getDetailProjReportObserv());
			evaluateProjectDetails.setPropCapInvObserv(evaluateInvestMentDetails.getPropCapInvObserv());
			evaluateProjectDetails.setTotlCostProjObserv(evaluateInvestMentDetails.getTotlCostProjObserv());
			evaluateProjectDetails.setMofObserv(evaluateInvestMentDetails.getMofObserv());
			evaluateProjectDetails.setIemRegObserv(evaluateInvestMentDetails.getIemRegObserv());
			evaluateProjectDetails.setIndusUntkObserv(evaluateInvestMentDetails.getIndusUntkObserv());

			evaluateProjectDetails.setEligblInvPerdLargeObserv(evaluateInvestMentDetails.getEligblInvPerdLargeObserv());
			evaluateProjectDetails.setEligblInvPerdMegaObserv(evaluateInvestMentDetails.getEligblInvPerdMegaObserv());
			evaluateProjectDetails
					.setEligblInvPerdSupermegaObserv(evaluateInvestMentDetails.getEligblInvPerdSupermegaObserv());
			evaluateProjectDetails.setEligblCapInvObserv(evaluateInvestMentDetails.getEligblCapInvObserv());
			evaluateProjectDetails.setProjPhasesObserv(evaluateInvestMentDetails.getProjPhasesObserv());
			evaluateProjectDetails.setCaStatutoryObserv(evaluateInvestMentDetails.getCaStatutoryObserv());
			evaluateProjectDetails.setAuthSignatoryObserv(evaluateInvestMentDetails.getAuthSignatoryObserv());
			evaluateProjectDetails.setIemRegObserv(evaluateInvestMentDetails.getIemRegObserv());
			evaluateProjectDetails.setAppformatObserv(evaluateInvestMentDetails.getAppformatObserv());
			evaluateProjectDetails.setcAStatutoryDate(evaluateInvestMentDetails.getInvCAStatutoryDate());
			/*
			 * if (evaluateInvestMentDetails.getInvCAStatutoryDate() != null) { try {
			 * DateFormat srcDf = new SimpleDateFormat("yyyy-MM-dd"); // parse the date
			 * string into Date object Date date =
			 * srcDf.parse(evaluateInvestMentDetails.getInvCAStatutoryDate().toString());
			 * DateFormat destDf = new SimpleDateFormat("dd-MM-yyyy"); // format the date
			 * into another format String dateStr = destDf.format(date);
			 * model.addAttribute("invCAStatutoryDate", dateStr); } catch (ParseException e)
			 * { logger.error(String.format("8888 dateFormat exception 8888 %s",
			 * e.getMessage())); } } else { model.addAttribute("invCAStatutoryDate",
			 * evaluateInvestMentDetails.getInvCAStatutoryDate()); }
			 */

			// EvaluateApplication Breakup of proposed capital Investment Remarks
			evaluateProjectDetails.setFixedAssetRemarks(evaluateInvestMentDetails.getFixedAssetRemarks());
			evaluateProjectDetails.setPlantAndMachRemarks(evaluateInvestMentDetails.getPlantAndMachRemarks());
			evaluateProjectDetails.setBuildingRemarks(evaluateInvestMentDetails.getBuildingRemarks());
			evaluateProjectDetails.setLandcostRemarks(evaluateInvestMentDetails.getLandcostRemarks());

			String categorytype = evaluateInvestMentDetails.getInvIndType();

			if ((categorytype.equalsIgnoreCase(SMALL)) || (categorytype.equalsIgnoreCase(MEDIUM))) {
				request.setAttribute("ctypeSMALL", "Yes");
			} else {
				request.setAttribute("ctypeSMALL", "No");
			}

			if (categorytype.equalsIgnoreCase(LARGE)) {
				request.setAttribute("ctypeLARGE", "Yes");
			} else {
				request.setAttribute("ctypeLARGE", "No");
			}

			if ((categorytype.equalsIgnoreCase(MEGA)) || (categorytype.equalsIgnoreCase(MEGAPLUS))) {
				request.setAttribute("ctypeMEGA", "Yes");
			} else {
				request.setAttribute("ctypeMEGA", "No");
			}

			if (categorytype.equalsIgnoreCase(SUPERMEGA)) {
				request.setAttribute("ctypeSUPERMEGA", "Yes");
			} else {
				request.setAttribute("ctypeSUPERMEGA", "No");
			}

			String phsWsAply = evaluateInvestMentDetails.getPwApply();

			if (phsWsAply.equalsIgnoreCase("Yes")) {
				request.setAttribute("phsWsAply", "Yes");
			} else {
				request.setAttribute("phsWsAply", "No");
			}

			Object LCasperIEPP = additionalInterest.getProposedFCI(appId);
			model.addAttribute("LCasperIEPP", LCasperIEPP);

			// BusinessEntityDetails records fetch from table

			DeptBusinessEntityDetails deptBusEntFromDb = deptBusEntRepository.findByApplicantDetailId(appId);

			model.addAttribute("authSignName", deptBusEntFromDb.getAuthorisedSignatoryName());
			model.addAttribute("GSTno", deptBusEntFromDb.getGstin());
			model.addAttribute("companyPANno", deptBusEntFromDb.getCompanyPanNo());
			model.addAttribute("businessAddress", deptBusEntFromDb.getBusinessAddress());
			model.addAttribute("isPresFormat", deptBusEntFromDb.getPresFormat());
			model.addAttribute("isDocAuthorized", deptBusEntFromDb.getDocAuthorized());

			evaluateProjectDetails.setDirDetailsObserv(deptBusEntFromDb.getDirDetailsObserv());
			evaluateProjectDetails.setSupprtdocObserv(deptBusEntFromDb.getSupprtdocObserv());
			evaluateProjectDetails.setGstinObserv(deptBusEntFromDb.getGstinObserv());
			evaluateProjectDetails.setPanObserv(deptBusEntFromDb.getPanObserv());

			// PropsedEmpDetails
			DeptProposedEmploymentDetails deptProEmploymentDetails = deptPrpsEmplRepository.findByappId(appId);
			evaluateProjectDetails.setTotalDetailObserv(deptProEmploymentDetails.getTotalDetailObserv());

			// fetch BusinessEntity documents from MongoDB
			applicantViewController.businessDocFromMongoDB(deptBusEntFromDb.getId(), model);

			// incentive details
			String isfId = incentiveDetailsService.getIsfIdByApplId(appId);

			IncentiveSpecificDetails incentiveSpecificDetails = new IncentiveSpecificDetails();
			IncentiveDetails IncentiveDetail = incentiveDetailsService.getIncentiveisfById(isfId);
			DeptIncentiveDetails deptIncDetails = deptIncRepository.findById(isfId);

			if (IncentiveDetail != null) {
				// PICUP Officer's Remark
				incentiveSpecificDetails.setSgstRemark(deptIncDetails.getSgstRemark());
				incentiveSpecificDetails.setScstRemark(deptIncDetails.getScstRemark());
				incentiveSpecificDetails.setStampDutyExemptRemark(deptIncDetails.getStampDutyExemptRemark());
				incentiveSpecificDetails.setStampDutyReimRemark(deptIncDetails.getStampDutyReimRemark());
				incentiveSpecificDetails.setCapIntSubRemark(deptIncDetails.getCapIntSubRemark());
				incentiveSpecificDetails.setInfraIntSubRemark(deptIncDetails.getInfraIntSubRemark());
				incentiveSpecificDetails.setLoanIntSubRemark(deptIncDetails.getLoanIntSubRemark());
				incentiveSpecificDetails.setInputTaxRemark(deptIncDetails.getInputTaxRemark());
				incentiveSpecificDetails.setElecDutyCapRemark(deptIncDetails.getElecDutyCapRemark());
				incentiveSpecificDetails.setElecDutyDrawnRemark(deptIncDetails.getElecDutyDrawnRemark());
				incentiveSpecificDetails.setDiffAbleWorkRemark(deptIncDetails.getDiffAbleWorkRemark());
				incentiveSpecificDetails.setMandiFeeRemark(deptIncDetails.getMandiFeeRemark());
				incentiveSpecificDetails.setFwRemark(deptIncDetails.getFwRemark());
				incentiveSpecificDetails.setBplRemark(deptIncDetails.getBplRemark());
				incentiveSpecificDetails.setDivyangSCSTRemark(deptIncDetails.getDivyangSCSTRemark());
				incentiveSpecificDetails.setEpfDvngSCSTRemark(deptIncDetails.getEpfDvngSCSTRemark());
				incentiveSpecificDetails.setEpfSklUnsklRemark(deptIncDetails.getEpfSklUnsklRemark());
				incentiveSpecificDetails.setEpfUnsklRemark(deptIncDetails.getEpfUnsklRemark());
				incentiveSpecificDetails.setAciSubsidyRemark(deptIncDetails.getAciSubsidyRemark());
				incentiveSpecificDetails.setAiiSubsidyRemark(deptIncDetails.getAiiSubsidyRemark());

				incentiveSpecificDetails.setISF_Claim_Reim(IncentiveDetail.getISF_Claim_Reim());
				incentiveSpecificDetails.setISF_Reim_SCST(IncentiveDetail.getISF_Reim_SCST());
				incentiveSpecificDetails.setISF_Reim_FW(IncentiveDetail.getISF_Reim_FW());
				incentiveSpecificDetails.setISF_Reim_BPLW(IncentiveDetail.getISF_Reim_BPLW());
				incentiveSpecificDetails.setISF_Ttl_SGST_Reim(IncentiveDetail.getISF_Ttl_SGST_Reim());

				incentiveSpecificDetails.setISF_Stamp_Duty_EX(IncentiveDetail.getISF_Stamp_Duty_EX());
				incentiveSpecificDetails
						.setISF_Additonal_Stamp_Duty_EX(IncentiveDetail.getISF_Additonal_Stamp_Duty_EX());
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

				incentiveSpecificDetails.setIsfSgstComment(IncentiveDetail.getIsfSgstComment());
				incentiveSpecificDetails.setIsfScstComment(IncentiveDetail.getIsfScstComment());
				incentiveSpecificDetails.setIsffwComment(IncentiveDetail.getIsffwComment());
				incentiveSpecificDetails.setIsfBplComment(IncentiveDetail.getIsfBplComment());
				incentiveSpecificDetails.setIsfElecdutyComment(IncentiveDetail.getIsfElecdutyComment());
				incentiveSpecificDetails.setIsfMandiComment(IncentiveDetail.getIsfMandiComment());
				incentiveSpecificDetails.setIsfStampComment(IncentiveDetail.getIsfStampComment());

				incentiveSpecificDetails.setIsfStampremComment(IncentiveDetail.getIsfStampremComment());
				incentiveSpecificDetails.setIsfStampscstComment(IncentiveDetail.getIsfStampscstComment());
				incentiveSpecificDetails.setIsfepfComment(IncentiveDetail.getIsfepfComment());
				incentiveSpecificDetails.setIsfepfaddComment(IncentiveDetail.getIsfepfaddComment());
				incentiveSpecificDetails.setIsfepfscComment(IncentiveDetail.getIsfepfscComment());
				incentiveSpecificDetails.setIsfcapisComment(IncentiveDetail.getIsfcapisComment());
				incentiveSpecificDetails.setIsfcapisaComment(IncentiveDetail.getIsfcapisaComment());
				incentiveSpecificDetails.setIsfinfComment(IncentiveDetail.getIsfinfComment());
				incentiveSpecificDetails.setIsfinfaComment(IncentiveDetail.getIsfinfaComment());
				incentiveSpecificDetails.setIsfloanComment(IncentiveDetail.getIsfloanComment());
				incentiveSpecificDetails.setIsfdisComment(IncentiveDetail.getIsfdisComment());
				incentiveSpecificDetails.setIsfelepodownComment(IncentiveDetail.getIsfelepodownComment());
				incentiveSpecificDetails.setIsfdifferabilComment(IncentiveDetail.getIsfdifferabilComment());

				incentiveSpecificDetails.setOthAddRequest1(IncentiveDetail.getOthAddRequest1());

				incentiveSpecificDetails.setId(IncentiveDetail.getId());

				if (deptIncDetails.getModify_Date() != null) {
					try {
						DateFormat srcDf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
						// parse the date string into Date object
						Date date = srcDf.parse(deptIncDetails.getModify_Date().toString());
						DateFormat destDf = new SimpleDateFormat("yyyy-MM-dd");
						// format the date into another format
						String dateStr = destDf.format(date);
						model.addAttribute("onlineSubmitDate", dateStr);
					} catch (ParseException e) {
						logger.error(String.format("8888 dateFormat exception 8888 %s", e.getMessage()));
					}
				} else {
					model.addAttribute("onlineSubmitDate", deptIncDetails.getModify_Date());
				}

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
					model.addAttribute(PRODUCTS,
							products.toString().substring(0, products.toString().lastIndexOf(',')));
				}
			} else if (evaluateProjectDetails.getNatureOfProject().equalsIgnoreCase(NEW_PROJECT)) {

				List<String> newproductsList = newexistProjRepository.findProductsByProjId(projId);
				if (!newproductsList.isEmpty()) {
					for (int k = 0; k < newproductsList.size(); k++) {
						products = products.append(newproductsList.get(k)).append(", ");
					}
					model.addAttribute(PRODUCTS,
							products.toString().substring(0, products.toString().lastIndexOf(',')));
				}
			}
			
			List<PhaseWiseInvestmentDetails> pwInvList1 = new ArrayList<PhaseWiseInvestmentDetails>();
			
			
			pwInvList1 = pwInvestDs.findByPwApcId(appId);
			model.addAttribute("pwInvList1", pwInvList1);

			List<Object> evalObjList = investRepository.getEvalDetailsByApplId(appId);
			model.addAttribute("evalObjList", evalObjList);
			applViewController.existProjDocsFromMongoDB(projId, model);

			evalCapitalInvList = evalCapInvService.getEvalCapitalInvListByApplId(appId);
			model.addAttribute("evalCapitalInvList", evalCapitalInvList);

			evalMeanofFinanceList = evalMOf.getEvalMeanofFinanceListByApplId(appId);
			model.addAttribute("evalMeanofFinanceList", evalMeanofFinanceList);

			model.addAttribute("appId", appId);
			model.addAttribute("incentiveDeatilsData", incentiveSpecificDetails);
			model.addAttribute(EVALUATE_PROJECT_DETAILS, evaluateProjectDetails);
		} catch (Exception e) {
			logger.error(String.format("^^^^ evaluateApplication exception ^^^^^ %s", e.getMessage()));
		}

		return EVALUATE_APPLICATION;
	}

	private void totalSkilledAndUnSkilledEmploment(Model model) {
		long skilledEmploymentMale = 0;
		long skilledEmploymentFemale = 0;
		long totalSkilledEmployment = 0;
		long unSkilledEmploymentMale = 0;
		long unSkilledEmploymentFemale = 0;
		long totalUnSkilledEmployment = 0;

		if (!skillUnskillEmplList.isEmpty()) {

			for (SkilledUnSkilledEmployemnt count : skillUnskillEmplList) {
				if (count.getSkilledUnskilled().equalsIgnoreCase("Skilled")) {
					if (count.getNumberofMaleEmployees() != null) {
						skilledEmploymentMale = skilledEmploymentMale + (count.getNumberofMaleEmployees());
					}
					if (count.getNumberOfFemaleEmployees() != null) {
						skilledEmploymentFemale = skilledEmploymentFemale + (count.getNumberOfFemaleEmployees());
					}
					totalSkilledEmployment = (skilledEmploymentMale + skilledEmploymentFemale);
					model.addAttribute("skilledEmploymentMale", skilledEmploymentMale);
					model.addAttribute("skilledEmploymentFemale", skilledEmploymentFemale);
					model.addAttribute("totalSkilledEmployment", totalSkilledEmployment);
					model.addAttribute("skilledDisplay", "skilledDisplay");

				} else {
					if (count.getNumberofMaleEmployees() != null) {
						unSkilledEmploymentMale = unSkilledEmploymentMale + (count.getNumberofMaleEmployees());
					}
					if (count.getNumberOfFemaleEmployees() != null) {
						unSkilledEmploymentFemale = unSkilledEmploymentFemale + (count.getNumberOfFemaleEmployees());
					}
					totalUnSkilledEmployment = (unSkilledEmploymentMale + unSkilledEmploymentFemale);
					model.addAttribute("unSkilledEmploymentMale", unSkilledEmploymentMale);
					model.addAttribute("unSkilledEmploymentFemale", unSkilledEmploymentFemale);
					model.addAttribute("totalUnSkilledEmployment", totalUnSkilledEmployment);
					model.addAttribute("unSkilledDisplay", "unSkilledDisplay");
				}
			}

		}
		model.addAttribute("skilledUnSkilledEmployemntList", skillUnskillEmplList);
		model.addAttribute("grossTotalMaleEmployment", (skilledEmploymentMale + unSkilledEmploymentMale));
		model.addAttribute("grossTotalFemaleEmployment", (skilledEmploymentFemale + unSkilledEmploymentFemale));
		model.addAttribute("grossTotalMaleandFemaleEmployment", ((skilledEmploymentFemale + unSkilledEmploymentFemale)
				+ (skilledEmploymentMale + unSkilledEmploymentMale)));
	}

	@PostMapping(value = "/applicationAgendaNote")
	public ModelAndView saveapplicationAgendaNote(
			@ModelAttribute("applicationAgendaNote") @Validated PrepareAgendaNotes prepareAgendaNotes, Model model,
			HttpSession session) {
		PrepareAgendaNotes prepareAgendaNote = new PrepareAgendaNotes();

		String appId = (String) session.getAttribute("appId");
		prepareAgendaNote.setAppliId(appId);
		BusinessEntityDetails businessEntityDetails = businessEntityDetailsService
				.getBusinessEntityByapplicantDetailId(appId);
		prepareAgendaNote.setCompanyName(businessEntityDetails.getBusinessEntityName());
		prepareAgendaNote.setId(appId + "AGN");
		InvestmentDetails invdtlFromDb = investmentDetailsService.getInvestmentDetailsByapplId(appId);
		prepareAgendaNote.setInvestment(String.valueOf(invdtlFromDb.getInvFci()));
		prepareAgendaNote.setCategoryIndsType(invdtlFromDb.getInvIndType());
		EvaluateProjectDetails evaluateProjectDetails = evaluateProjectDetailsService
				.getEvalProjDetByapplicantDetailId(appId);
		prepareAgendaNote.setDistrict(evaluateProjectDetails.getDistrictName());
		prepareAgendaNote.setRegion(evaluateProjectDetails.getResionName());
		prepareAgendaNote.setNotes("");
		prepareAgendaNote.setCreatedBY("AdminUser");
		prepareAgendaNote.setStatus("Application Included In Agenda Note");

		String userId = (String) session.getAttribute("userId");
		try {

			if (userId != null && userId != "") {
				Optional<TblUsers> loginUser = loginService.getLoginIdById(userId);
				prepareAgendaNote.setUserId(String.valueOf(loginUser.get().getid()));// sachin
			}

			ApplicationFwdEntity applicationFwdEntity = new ApplicationFwdEntity();
			List<ApplicationFwdEntity> applicationFwdEntityList = new ArrayList<>();
			applicationFwdEntityList = applicationFwdRepository.findByAppId(prepareAgendaNote.getAppliId());
			for (ApplicationFwdEntity applicationFwd : applicationFwdEntityList) {
				applicationFwd.setStatus(false);
				applicationFwdRepository.save(applicationFwd);
			}

			applicationFwdEntity.setAppId(prepareAgendaNote.getAppliId());
			Timestamp currentDate = new Timestamp(System.currentTimeMillis());
			applicationFwdEntity.setFwddate(currentDate);

			applicationFwdEntity.setDepartment("PICUP Processing Officer");
			applicationFwdEntity.setName("PICUP Processing Officer");
			applicationFwdEntity.setRolname("PICUP Processing Officer");
			applicationFwdEntity.setStatus(true);
			applicationFwdRepository.save(applicationFwdEntity);

			prepareAgendaNotesService.savePrepareAgendaNotes(prepareAgendaNote);
		} catch (Exception e) {
			e.printStackTrace();
		}

		model.addAttribute("msg", "Succefully Add Agenda Notes");
		return new ModelAndView("redirect:/evaluateApplication");
	}

	@PostMapping(value = "/applicationAgendaNoteDIC")
	public ModelAndView saveapplicationAgendaNoteDIC(
			@ModelAttribute("applicationAgendaNote") @Validated PrepareAgendaNotes prepareAgendaNotes, Model model,
			HttpSession session) {
		PrepareAgendaNotes prepareAgendaNote = new PrepareAgendaNotes();

		String appId = (String) session.getAttribute("appId");

		PrepareAgendaNotes prepAgenNoteExits = prepareAgendaNotesService.findPrepAgendaNotesByAppliId(appId);
		prepareAgendaNote.setId(prepAgenNoteExits.getId());
		prepareAgendaNote.setAppliId(prepAgenNoteExits.getAppliId());
		prepareAgendaNote.setCompanyName(prepAgenNoteExits.getCompanyName());
		prepareAgendaNote.setInvestment(prepAgenNoteExits.getInvestment());
		prepareAgendaNote.setNotes(prepAgenNoteExits.getNotes());
		prepareAgendaNote.setUserId(prepAgenNoteExits.getUserId());
		prepareAgendaNote.setStatus("DICReady");
		prepareAgendaNote.setCreatedBY("AdminUser");
		prepareAgendaNote.setCategoryIndsType(prepAgenNoteExits.getCategoryIndsType());
		prepareAgendaNote.setDistrict(prepAgenNoteExits.getDistrict());
		prepareAgendaNote.setRegion(prepAgenNoteExits.getRegion());
		prepareAgendaNotesService.savePrepareAgendaNotes(prepareAgendaNote);
		/*
		 * prepareAgendaNote.setAppliId(appId); BusinessEntityDetails
		 * businessEntityDetails = businessEntityDetailsService
		 * .getBusinessEntityByapplicantDetailId(appId);
		 * prepareAgendaNote.setCompanyName(businessEntityDetails.getBusinessEntityName(
		 * )); prepareAgendaNote.setId(appId + "AGN"); InvestmentDetails invdtlFromDb =
		 * investmentDetailsService.getInvestmentDetailsByapplId(appId);
		 * prepareAgendaNote.setInvestment(String.valueOf(invdtlFromDb.getInvFci()));
		 * prepareAgendaNote.setCategoryIndsType(invdtlFromDb.getInvIndType());
		 * ProjectDetails projectDetails =
		 * projectService.getProjectByapplicantDetailId(appId);
		 * prepareAgendaNote.setDistrict(projectDetails.getDistrictName());
		 * prepareAgendaNote.setRegion(projectDetails.getResionName());
		 * prepareAgendaNote.setNotes(""); prepareAgendaNote.setCreatedBY("AdminUser");
		 * prepareAgendaNote.setStatus("DICReady");
		 * 
		 * String userId = (String) session.getAttribute("userId"); try {
		 * 
		 * if (userId != null && userId != "") { Login loginUser =
		 * loginService.getLoginIdById(userId);
		 * prepareAgendaNote.setUserId(loginUser.getId()); }
		 * prepareAgendaNotesService.savePrepareAgendaNotes(prepareAgendaNote); } catch
		 * (Exception e) { e.getMessage(); }
		 */

		model.addAttribute("msg", "Succefully Add Agenda Notes");
		return new ModelAndView("redirect:/evaluateApplicationDIC");
	}

	@PostMapping(value = "/EvaluateProjectDetails")
	public ModelAndView update(
			@ModelAttribute(EVALUATE_PROJECT_DETAILS) @Validated EvaluateProjectDetails evaluateProjectDetails,
			@ModelAttribute("incentiveDeatilsData") IncentiveDetails incentiveDetails, BindingResult result,
			Model model, HttpSession session) {
		List<EvaluationAuditTrail> evaluationAuditTrailList = new ArrayList<>();

		try {
			String userId = (String) session.getAttribute("userId");

			if (evaluateProjectDetails.getId() != null && !evaluateProjectDetails.getId().isEmpty()) {
				EvaluateProjectDetails evaluateProjectDetail = evaluateProjectDetailsService
						.getProjectDetailsById(evaluateProjectDetails.getId());
				if (evaluateProjectDetail.getNatureOfProject().equalsIgnoreCase(EXISTING_PROJECT)) {
					List<EvaluateExistNewProjDetails> evaluateExistNewProjDetailsList = evaluateExistNewProjDetailsRepository
							.getEvalExistProjListByepdProjDtlId(evaluateProjectDetails.getId());
					String[] epdId = evaluateProjectDetails.getEpdId().split(",");
					String[] epdExisProducts = evaluateProjectDetails.getEpdExisProducts().split(",");
					String[] epdExisInstallCapacity = evaluateProjectDetails.getEpdExisInstallCapacity().split(",");
					String[] epdPropProducts = evaluateProjectDetails.getEpdPropProducts().split(",");
					String[] epdPropInstallCapacity = evaluateProjectDetails.getEpdPropInstallCapacity().split(",");
					String[] epdExisGrossBlock = evaluateProjectDetails.getEpdExisGrossBlock().split(",");
					String[] epdPropoGrossBlock = evaluateProjectDetails.getEpdPropoGrossBlock().split(",");

					for (int i = 0; i < epdId.length; i++) {
						for (EvaluateExistNewProjDetails list : evaluateExistNewProjDetailsList) {
							if (epdId[i].equals(list.getEpdId())) {

								list.setEpdId(epdId[i]);
								if (!list.getEpdExisProducts().equals(epdExisProducts[i])) {

									EvaluationAuditTrail evaluationAuditTrail = new EvaluationAuditTrail();
									evaluationAuditTrail.setId(
											evaluateProjectDetails.getId() + "EAT" + atomicInteger.getAndIncrement());
									evaluationAuditTrail.setFieldsName("ExistingProducts");
									evaluationAuditTrail.setOldDetails(list.getEpdExisProducts());
									evaluationAuditTrail.setNewDetails(epdExisProducts[i]);
									evaluationAuditTrail.setAppliId(evaluateProjectDetail.getApplicantDetailId());
									evaluationAuditTrail.setProjId(evaluateProjectDetails.getId());
									if (userId != null && !userId.isEmpty()) {
										Optional<TblUsers> loginUser = loginService.getLoginIdById(userId);
										evaluationAuditTrail.setUserId(String.valueOf(loginUser.get().getid()));// sachin
										evaluationAuditTrail
												.setCreatedBY(loginUser.get().getFirstName() + " " + loginUser.get().getLastName());
										evaluationAuditTrail.setModifyedBy(
												loginUser.get().getFirstName() + " " + loginUser.get().getLastName());
									}
									evaluationAuditTrail.setModifyedDate(new Date());
									evaluationAuditTrail.setCreatedDate(new Date());
									evaluationAuditTrail.setStatus("Active");
									evaluationAuditTrailList.add(evaluationAuditTrail);
									list.setEpdExisProducts(epdExisProducts[i]);
								}

								if (!list.getEpdExisInstallCapacity().equals(epdExisInstallCapacity[i])) {

									EvaluationAuditTrail evaluationAuditTrail = new EvaluationAuditTrail();
									evaluationAuditTrail.setId(
											evaluateProjectDetails.getId() + "EAT" + atomicInteger.getAndIncrement());
									evaluationAuditTrail.setFieldsName("ExistingInstalledCapacity");
									evaluationAuditTrail.setOldDetails(list.getEpdExisInstallCapacity());
									evaluationAuditTrail.setNewDetails(epdExisInstallCapacity[i]);
									evaluationAuditTrail.setAppliId(evaluateProjectDetail.getApplicantDetailId());
									evaluationAuditTrail.setProjId(evaluateProjectDetails.getId());
									if (userId != null && !userId.isEmpty()) {
										Optional<TblUsers> loginUser = loginService.getLoginIdById(userId);
										evaluationAuditTrail.setUserId(String.valueOf(loginUser.get().getid()));// sachin
										evaluationAuditTrail
												.setCreatedBY(loginUser.get().getFirstName() + " " + loginUser.get().getLastName());
										evaluationAuditTrail.setModifyedBy(
												loginUser.get().getFirstName() + " " + loginUser.get().getLastName());
									}
									evaluationAuditTrail.setModifyedDate(new Date());
									evaluationAuditTrail.setCreatedDate(new Date());
									evaluationAuditTrail.setStatus("Active");
									evaluationAuditTrailList.add(evaluationAuditTrail);
									list.setEpdExisInstallCapacity(epdExisInstallCapacity[i]);
								}

								if (!list.getEpdPropProducts().equals(epdPropProducts[i])) {

									EvaluationAuditTrail evaluationAuditTrail = new EvaluationAuditTrail();
									evaluationAuditTrail.setId(
											evaluateProjectDetails.getId() + "EAT" + atomicInteger.getAndIncrement());
									evaluationAuditTrail.setFieldsName("ProposedProducts");
									evaluationAuditTrail.setOldDetails(list.getEpdPropProducts());
									evaluationAuditTrail.setNewDetails(epdPropProducts[i]);
									evaluationAuditTrail.setAppliId(evaluateProjectDetail.getApplicantDetailId());
									evaluationAuditTrail.setProjId(evaluateProjectDetails.getId());
									if (userId != null && !userId.isEmpty()) {
										Optional<TblUsers> loginUser = loginService.getLoginIdById(userId);
										evaluationAuditTrail.setUserId(String.valueOf(loginUser.get().getid()));// sachin
										evaluationAuditTrail
												.setCreatedBY(loginUser.get().getFirstName() + " " + loginUser.get().getLastName());
										evaluationAuditTrail.setModifyedBy(
												loginUser.get().getFirstName() + " " + loginUser.get().getLastName());
									}
									evaluationAuditTrail.setModifyedDate(new Date());
									evaluationAuditTrail.setCreatedDate(new Date());
									evaluationAuditTrail.setStatus("Active");
									evaluationAuditTrailList.add(evaluationAuditTrail);
									list.setEpdPropProducts(epdPropProducts[i]);
								}

								if (!list.getEpdPropInstallCapacity().equals(epdPropInstallCapacity[i])) {

									EvaluationAuditTrail evaluationAuditTrail = new EvaluationAuditTrail();
									evaluationAuditTrail.setId(
											evaluateProjectDetails.getId() + "EAT" + atomicInteger.getAndIncrement());
									evaluationAuditTrail.setFieldsName("ProposedInstalledCapacity");
									evaluationAuditTrail.setOldDetails(list.getEpdPropInstallCapacity());
									evaluationAuditTrail.setNewDetails(epdPropInstallCapacity[i]);
									evaluationAuditTrail.setAppliId(evaluateProjectDetail.getApplicantDetailId());
									evaluationAuditTrail.setProjId(evaluateProjectDetails.getId());
									if (userId != null && !userId.isEmpty()) {
										Optional<TblUsers> loginUser = loginService.getLoginIdById(userId);
										evaluationAuditTrail.setUserId(String.valueOf(loginUser.get().getid()));// sachin
										evaluationAuditTrail
												.setCreatedBY(loginUser.get().getFirstName() + " " + loginUser.get().getLastName());
										evaluationAuditTrail.setModifyedBy(
												loginUser.get().getFirstName() + " " + loginUser.get().getLastName());
									}
									evaluationAuditTrail.setModifyedDate(new Date());
									evaluationAuditTrail.setCreatedDate(new Date());
									evaluationAuditTrail.setStatus("Active");
									evaluationAuditTrailList.add(evaluationAuditTrail);
									list.setEpdPropInstallCapacity(epdPropInstallCapacity[i]);
								}

								if (!String.valueOf(list.getEpdExisGrossBlock()).equals(epdExisGrossBlock[i])) {

									EvaluationAuditTrail evaluationAuditTrail = new EvaluationAuditTrail();
									evaluationAuditTrail.setId(
											evaluateProjectDetails.getId() + "EAT" + atomicInteger.getAndIncrement());
									evaluationAuditTrail.setFieldsName("ExistingGrossBlock");
									evaluationAuditTrail.setOldDetails(String.valueOf(list.getEpdExisGrossBlock()));
									evaluationAuditTrail.setNewDetails(String.valueOf(epdExisGrossBlock[i]));
									evaluationAuditTrail.setAppliId(evaluateProjectDetail.getApplicantDetailId());
									evaluationAuditTrail.setProjId(evaluateProjectDetails.getId());
									if (userId != null && !userId.isEmpty()) {
										Optional<TblUsers> loginUser = loginService.getLoginIdById(userId);
										evaluationAuditTrail.setUserId(String.valueOf(loginUser.get().getid()));// sachin
										evaluationAuditTrail
												.setCreatedBY(loginUser.get().getFirstName() + " " + loginUser.get().getLastName());
										evaluationAuditTrail.setModifyedBy(
												loginUser.get().getFirstName() + " " + loginUser.get().getLastName());
									}
									evaluationAuditTrail.setModifyedDate(new Date());
									evaluationAuditTrail.setCreatedDate(new Date());
									evaluationAuditTrail.setStatus("Active");
									evaluationAuditTrailList.add(evaluationAuditTrail);
									list.setEpdExisGrossBlock(Long.parseLong(epdExisGrossBlock[i]));
								}
								if (!String.valueOf(list.getEpdPropoGrossBlock()).equals(epdPropoGrossBlock[i])) {

									EvaluationAuditTrail evaluationAuditTrail = new EvaluationAuditTrail();
									evaluationAuditTrail.setId(
											evaluateProjectDetails.getId() + "EAT" + atomicInteger.getAndIncrement());
									evaluationAuditTrail.setFieldsName("ProposedGrossBlock");
									evaluationAuditTrail.setOldDetails(String.valueOf(list.getEpdExisGrossBlock()));
									evaluationAuditTrail.setNewDetails(String.valueOf(epdPropoGrossBlock[i]));
									evaluationAuditTrail.setAppliId(evaluateProjectDetail.getApplicantDetailId());
									evaluationAuditTrail.setProjId(evaluateProjectDetails.getId());
									if (userId != null && !userId.isEmpty()) {
										Optional<TblUsers> loginUser = loginService.getLoginIdById(userId);
										evaluationAuditTrail.setUserId(String.valueOf(loginUser.get().getid()));// sachin
										evaluationAuditTrail
												.setCreatedBY(loginUser.get().getFirstName() + " " + loginUser.get().getLastName());
										evaluationAuditTrail.setModifyedBy(
												loginUser.get().getFirstName() + " " + loginUser.get().getLastName());
									}
									evaluationAuditTrail.setModifyedDate(new Date());
									evaluationAuditTrail.setCreatedDate(new Date());
									evaluationAuditTrail.setStatus("Active");
									evaluationAuditTrailList.add(evaluationAuditTrail);
									list.setEpdPropoGrossBlock(Long.parseLong(epdPropoGrossBlock[i]));
								}
							}
						}
					}
				}
				for (EvaluationAuditTrail bean : evaluationAuditTrailList) {
					evaluationAuditTrailService.saveEvaluationAuditTrail(bean);
				}

				saveEvaluateProjectDetails(evaluateProjectDetail, evaluateProjectDetails, session);

				EvaluateInvestmentDetails evaluateInvestMentDetails = evaluateInvestMentDetailsService
						.getEvalInvestDetByapplId(evaluateProjectDetail.getApplicantDetailId());

				saveEvaluateInvestmentDetails(evaluateInvestMentDetails, evaluateProjectDetails);

				DeptIncentiveDetails deptIncDetails = deptIncRepository
						.findByIsfapcId(evaluateProjectDetail.getApplicantDetailId());

				saveDeptIncentiveDetails(deptIncDetails, incentiveDetails, evaluateProjectDetails);

				DeptBusinessEntityDetails deptBusinessEntityDetails = deptBusEntRepository
						.findByApplicantDetailId(evaluateProjectDetail.getApplicantDetailId());
				saveDeptBusinessDetails(deptBusinessEntityDetails, evaluateProjectDetails);

				DeptProposedEmploymentDetails deptProposedEmpDetails = deptPrpsEmplRepository
						.findByappId(evaluateProjectDetail.getApplicantDetailId());
				saveDeptProposedEmploymentDetails(deptProposedEmpDetails, evaluateProjectDetails);

				try {
					if (!evalCapInvService.getEvalCapitalInvListByApplId(evaluateProjectDetail.getApplicantDetailId())
							.isEmpty()) {
						List<EvaluateCapitalInvestment> evalcapList = evalCapInvService
								.getEvalCapitalInvListByApplId(evaluateProjectDetail.getApplicantDetailId());
						for (EvaluateCapitalInvestment eci : evalcapList) {
							evalCapInvService.deleteEvalCapInvById(eci.getEciId());
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
				}

				// save EvalCapitalInvList in table
				if (!evalCapitalInvList.isEmpty()) {
					evalCapInvService.saveEvalCapitalInvList(evalCapitalInvList);
				}

				try {
					if (!evalMOf.getEvalMeanofFinanceListByApplId(evaluateProjectDetail.getApplicantDetailId())
							.isEmpty()) {
						List<EvaluateMeanOfFinance> evalMOFList = evalMOf
								.getEvalMeanofFinanceListByApplId(evaluateProjectDetail.getApplicantDetailId());
						for (EvaluateMeanOfFinance emof : evalMOFList) {
							evalMOf.deleteEvalMofById(emof.getEmfId());
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
				}

				// save Eval Mean of Finance List in table
				if (!evalMeanofFinanceList.isEmpty()) {
					evalMOf.saveEvalMeanofFinanceInvList(evalMeanofFinanceList);
				}

			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(String.format("^^^^ EvaluateProjectDetails exception ^^^ %s", e.getMessage()));
		}

		return new ModelAndView("redirect:/evaluateApplication");

	}

	public void saveDeptBusinessDetails(DeptBusinessEntityDetails deptBusinessEntobj,
			EvaluateProjectDetails evaluateProjectDetails) {
		deptBusinessEntobj.setAuthorisedSignatoryName(evaluateProjectDetails.getAuthorisedSignatoryName());
		deptBusinessEntobj.setGstin(evaluateProjectDetails.getGstin());
		deptBusinessEntobj.setCompanyPanNo(evaluateProjectDetails.getCompanyPanNo());
		deptBusinessEntobj.setDocAuthorized(evaluateProjectDetails.getDocAuthorized());
		deptBusinessEntobj.setPresFormat(evaluateProjectDetails.getPresFormat());
		deptBusinessEntobj.setDirDetailsObserv(evaluateProjectDetails.getDirDetailsObserv());

		deptBusinessEntobj.setSupprtdocObserv(evaluateProjectDetails.getSupprtdocObserv());
		deptBusinessEntobj.setGstinObserv(evaluateProjectDetails.getGstinObserv());
		deptBusinessEntobj.setPanObserv(evaluateProjectDetails.getPanObserv());

		deptBusEntRepository.save(deptBusinessEntobj);
	}

	public void saveDeptProposedEmploymentDetails(DeptProposedEmploymentDetails deptPropEmpDetails,
			EvaluateProjectDetails evaluateProjectDetails) {
		deptPropEmpDetails.setTotalDetailObserv(evaluateProjectDetails.getTotalDetailObserv());
		deptPrpsEmplRepository.save(deptPropEmpDetails);
	}

	/**
	 * This method is responsible to save InvestmentDetails records in
	 * Dept_Investment_Details table.
	 */
	public void saveEvaluateInvestmentDetails(EvaluateInvestmentDetails evaluateInvestMentDetails,
			EvaluateProjectDetails evaluateProjectDetails) {

		evaluateInvestMentDetails.setInvIndType(evaluateProjectDetails.getInvIndType());
		evaluateInvestMentDetails.setInvFci(evaluateProjectDetails.getInvFci());
		evaluateInvestMentDetails.setInvCommenceDate(evaluateProjectDetails.getInvCommenceDate());
		evaluateInvestMentDetails.setPropCommProdDate(evaluateProjectDetails.getPropCommProdDate());

		evaluateInvestMentDetails.setInvTotalProjCost(evaluateProjectDetails.getInvTotalProjCost());
		evaluateInvestMentDetails.setBuildingRemarks(evaluateProjectDetails.getBuildingRemarks());
		evaluateInvestMentDetails.setPlantAndMachRemarks(evaluateProjectDetails.getPlantAndMachRemarks());
		evaluateInvestMentDetails.setLandcostRemarks(evaluateProjectDetails.getLandcostRemarks());
		evaluateInvestMentDetails.setFixedAssetRemarks(evaluateProjectDetails.getFixedAssetRemarks());
		evaluateInvestMentDetails.setInvIemNumber(evaluateProjectDetails.getIemNumber());
		evaluateInvestMentDetails.setInvGovtEquity(evaluateProjectDetails.getGovtEquity());
		evaluateInvestMentDetails.setInvEligcapInvest(evaluateProjectDetails.getEligcapInvest());
		evaluateInvestMentDetails.setInvCAStatutoryAmt(evaluateProjectDetails.getcAStatutoryAmt());
		evaluateInvestMentDetails.setInvCAStatutoryDate(evaluateProjectDetails.getcAStatutoryDate());

		evaluateInvestMentDetails.setLandcostFci(evaluateProjectDetails.getLandcostFci());
		evaluateInvestMentDetails.setBuildingFci(evaluateProjectDetails.getBuildingFci());
		evaluateInvestMentDetails.setPlantAndMachFci(evaluateProjectDetails.getPlantAndMachFci());
		evaluateInvestMentDetails.setFixedAssetFci(evaluateProjectDetails.getFixedAssetFci());
		evaluateInvestMentDetails.setLandcostIIEPP(evaluateProjectDetails.getLandcostIIEPP());
		evaluateInvestMentDetails.setBuildingIIEPP(evaluateProjectDetails.getBuildingIIEPP());
		evaluateInvestMentDetails.setPlantAndMachIIEPP(evaluateProjectDetails.getPlantAndMachIIEPP());
		evaluateInvestMentDetails.setFixedAssetIIEPP(evaluateProjectDetails.getFixedAssetIIEPP());

		evaluateInvestMentDetails.setCatIndusUndtObserv(evaluateProjectDetails.getCatIndusUndtObserv());

		evaluateInvestMentDetails.setPropsPlntMcnryCostObserv(evaluateProjectDetails.getPropsPlntMcnryCostObserv());

		evaluateInvestMentDetails.setOptcutofdateobserv(evaluateProjectDetails.getOptcutofdateobserv());
		evaluateInvestMentDetails.setDateofComProdObserv(evaluateProjectDetails.getDateofComProdObserv());
		evaluateInvestMentDetails.setDetailProjReportObserv(evaluateProjectDetails.getDetailProjReportObserv());
		evaluateInvestMentDetails.setPropCapInvObserv(evaluateProjectDetails.getPropCapInvObserv());
		evaluateInvestMentDetails.setTotlCostProjObserv(evaluateProjectDetails.getTotlCostProjObserv());

		evaluateInvestMentDetails.setMofObserv(evaluateProjectDetails.getMofObserv());
		evaluateInvestMentDetails.setIemRegObserv(evaluateProjectDetails.getIemRegObserv());
		evaluateInvestMentDetails.setIndusUntkObserv(evaluateProjectDetails.getIndusUntkObserv());

		evaluateInvestMentDetails.setEligblCapInvObserv(evaluateProjectDetails.getEligblCapInvObserv());

		evaluateInvestMentDetails.setEligblInvPerdLargeObserv(evaluateProjectDetails.getEligblInvPerdLargeObserv());
		evaluateInvestMentDetails.setEligblInvPerdMegaObserv(evaluateProjectDetails.getEligblInvPerdMegaObserv());
		evaluateInvestMentDetails
				.setEligblInvPerdSupermegaObserv(evaluateProjectDetails.getEligblInvPerdSupermegaObserv());

		evaluateInvestMentDetails.setProjPhasesObserv(evaluateProjectDetails.getProjPhasesObserv());

		evaluateInvestMentDetails.setCaStatutoryObserv(evaluateProjectDetails.getCaStatutoryObserv());
		evaluateInvestMentDetails.setAuthSignatoryObserv(evaluateProjectDetails.getAuthSignatoryObserv());
		evaluateInvestMentDetails.setAppformatObserv(evaluateProjectDetails.getAppformatObserv());

		evaluateInvestMentDetailsService.saveEvaluateInvestMentDetails(evaluateInvestMentDetails);

	}

	/**
	 * This method is responsible to save ProjectDetails records in
	 * Dept_Project_Details table.
	 */
	public void saveEvaluateProjectDetails(EvaluateProjectDetails evaluateProjectDetail,
			EvaluateProjectDetails evaluateProjectDetails, HttpSession session) {
		String userId = (String) session.getAttribute("userId");
		EvaluationAuditTrail evaluationAuditTrail = new EvaluationAuditTrail();
		evaluationAuditTrail.setId(evaluateProjectDetails.getId() + "EAT" + atomicInteger.getAndIncrement());
		evaluationAuditTrail.setFieldsName("ProductDetails");
		evaluationAuditTrail.setOldDetails(evaluateProjectDetail.getProductDetails());
		evaluationAuditTrail.setNewDetails(evaluateProjectDetails.getProductDetails());
		evaluationAuditTrail.setAppliId(evaluateProjectDetail.getApplicantDetailId());
		evaluationAuditTrail.setProjId(evaluateProjectDetails.getId());
		if (userId != null && !userId.isEmpty()) {
			Optional<TblUsers> loginUser = loginService.getLoginIdById(userId);
			evaluationAuditTrail.setUserId(String.valueOf(loginUser.get().getid()));// sachin
			evaluationAuditTrail.setCreatedBY(loginUser.get().getFirstName() + " " + loginUser.get().getLastName());
			evaluationAuditTrail.setModifyedBy(loginUser.get().getFirstName() + " " + loginUser.get().getLastName());
		}
		evaluationAuditTrail.setModifyedDate(new Date());
		evaluationAuditTrail.setCreatedDate(new Date());
		evaluationAuditTrail.setStatus("Active");
		evaluationAuditTrailService.saveEvaluationAuditTrail(evaluationAuditTrail);

		EvaluateProjectDetails newEvalProjDetail = new EvaluateProjectDetails();
		newEvalProjDetail.setId(evaluateProjectDetail.getId());
		newEvalProjDetail.setApplicantDetailId(evaluateProjectDetail.getApplicantDetailId());
		newEvalProjDetail.setContactPersonName(evaluateProjectDetail.getContactPersonName());
		newEvalProjDetail.setDesignation(evaluateProjectDetail.getDesignation());
		newEvalProjDetail.setProjectDescription(evaluateProjectDetail.getProjectDescription());
		newEvalProjDetail.setMobileNo((evaluateProjectDetail.getMobileNo()));
		newEvalProjDetail.setWebSiteName(evaluateProjectDetail.getWebSiteName());
		newEvalProjDetail.setFullAddress(evaluateProjectDetails.getFullAddress());
		newEvalProjDetail.setDistrictName(evaluateProjectDetails.getDistrictName());
		newEvalProjDetail.setResionName(evaluateProjectDetails.getResionName());
		newEvalProjDetail.setMandalName(evaluateProjectDetail.getMandalName());
		newEvalProjDetail.setPinCode(evaluateProjectDetail.getPinCode());
		newEvalProjDetail.setNatureOfProject(evaluateProjectDetail.getNatureOfProject());

		newEvalProjDetail.setPropsProdtDetailObserv(evaluateProjectDetails.getPropsProdtDetailObserv());
		newEvalProjDetail.setListAssets(evaluateProjectDetails.getListAssets());
		newEvalProjDetail.setListAssetsObserv(evaluateProjectDetails.getListAssetsObserv());
		newEvalProjDetail.setAnexurUndertk(evaluateProjectDetails.getAnexurUndertk());
		newEvalProjDetail.setAnexurUndertkObserv(evaluateProjectDetails.getAnexurUndertkObserv());
		newEvalProjDetail.setExpDivfObserv(evaluateProjectDetails.getExpDivfObserv());
		newEvalProjDetail.setSolarCaptivePower(evaluateProjectDetails.getSolarCaptivePower());
		newEvalProjDetail.setSolarCaptivePowerObserv(evaluateProjectDetails.getSolarCaptivePowerObserv());

		if (evaluateProjectDetail.getNatureOfProject().equalsIgnoreCase(NEW_PROJECT)) {
			newEvalProjDetail.setNewProject(NEW_PROJECT);
			newEvalProjDetail.setProductDetails(evaluateProjectDetails.getProductDetails());
			newEvalProjDetail.setProjectObserv(evaluateProjectDetails.getProjectObserv());
			newEvalProjDetail.setProdDetailObserv(evaluateProjectDetails.getProdDetailObserv());
			newEvalProjDetail.setLocationObserv(evaluateProjectDetails.getLocationObserv());
		} else {
			newEvalProjDetail.setExpansion(evaluateProjectDetail.getExpansion());
			newEvalProjDetail.setDiversification(evaluateProjectDetail.getDiversification());
			newEvalProjDetail.setProjectObserv(evaluateProjectDetails.getProjectObserv());
			newEvalProjDetail.setLocationObserv(evaluateProjectDetails.getLocationObserv());
		}
		newEvalProjDetail.setCreatedBy("AdminUser");
		newEvalProjDetail.setStatus("active");
		evaluateProjectDetailsService.saveEvaluationProjectDetails(newEvalProjDetail);

	}

	/**
	 * This method is responsible to save IncentiveDetails records in
	 * Dept_ISF_Form_Details table.
	 */
	public void saveDeptIncentiveDetails(DeptIncentiveDetails deptIncDetails, IncentiveDetails incentiveDetails,
			EvaluateProjectDetails evalProjDetails) {
		deptIncDetails.setSgstRemark(incentiveDetails.getSgstRemark());
		deptIncDetails.setScstRemark(incentiveDetails.getScstRemark());
		deptIncDetails.setStampDutyExemptRemark(incentiveDetails.getStampDutyExemptRemark());
		deptIncDetails.setCapIntSubRemark(incentiveDetails.getCapIntSubRemark());
		deptIncDetails.setStampDutyReimRemark(incentiveDetails.getStampDutyReimRemark());
		deptIncDetails.setInfraIntSubRemark(incentiveDetails.getInfraIntSubRemark());
		deptIncDetails.setInputTaxRemark(incentiveDetails.getInputTaxRemark());
		deptIncDetails.setLoanIntSubRemark(incentiveDetails.getLoanIntSubRemark());
		deptIncDetails.setElecDutyCapRemark(incentiveDetails.getElecDutyCapRemark());
		deptIncDetails.setElecDutyDrawnRemark(incentiveDetails.getElecDutyDrawnRemark());
		deptIncDetails.setMandiFeeRemark(incentiveDetails.getMandiFeeRemark());
		deptIncDetails.setDiffAbleWorkRemark(incentiveDetails.getDiffAbleWorkRemark());
		deptIncDetails.setFwRemark(incentiveDetails.getFwRemark());
		deptIncDetails.setBplRemark(incentiveDetails.getBplRemark());
		deptIncDetails.setDivyangSCSTRemark(incentiveDetails.getDivyangSCSTRemark());
		deptIncDetails.setEpfDvngSCSTRemark(incentiveDetails.getEpfDvngSCSTRemark());
		deptIncDetails.setEpfSklUnsklRemark(incentiveDetails.getEpfSklUnsklRemark());
		deptIncDetails.setEpfUnsklRemark(incentiveDetails.getEpfUnsklRemark());
		deptIncDetails.setAciSubsidyRemark(incentiveDetails.getAciSubsidyRemark());
		deptIncDetails.setAiiSubsidyRemark(incentiveDetails.getAiiSubsidyRemark());
		deptIncDetails.setCreatedDate(evalProjDetails.getCreatedDate());

		deptIncDetails.setSubDateAppObserv(evalProjDetails.getSubDateAppObserv());

		deptIncRepository.save(deptIncDetails);
	}

	/**
	 * This method is responsible to add more items in the list.
	 */
	@PostMapping("/addEvaluateCapInvest")
	public ModelAndView addEvaluateCapInvest(
			@ModelAttribute(EVALUATE_PROJECT_DETAILS) @Validated EvaluateProjectDetails evalProjDetails,
			@ModelAttribute("incentiveDeatilsData") @Validated IncentiveDetails incentiveDetails, BindingResult result,
			Model model, HttpSession session, HttpServletRequest request) {

		EvaluateCapitalInvestment newEvalCapInv = new EvaluateCapitalInvestment();
		try {
			String appId = (String) session.getAttribute("appId");
			String evalCapInvId = appId.substring(0, appId.length() - 2) + "ECI" + atomicInteger.getAndIncrement();

			newEvalCapInv.setEciItem(evalProjDetails.getEciItem());
			newEvalCapInv.setEciIsFci(evalProjDetails.getEciIsFci());
			newEvalCapInv.setEciDPRInvest(evalProjDetails.getEciDPRInvest());
			newEvalCapInv.setEciIIEPPInvest(evalProjDetails.getEciIIEPPInvest());
			newEvalCapInv.setEciPICUP_Remarks(evalProjDetails.getEciPICUP_Remarks());
			newEvalCapInv.setEciCreatedBy("ECI User");
			newEvalCapInv.setEciModifiyDate(new Date());
			// newEvalCapInv.setEciCreatedDate(LocalDate.now());
			newEvalCapInv.setEciApplcId(appId);
			newEvalCapInv.setEciId(evalCapInvId);

			evalCapitalInvList.add(newEvalCapInv);

		} catch (Exception e) {
			logger.error(String.format("#### addEvaluateCapInvest exception $$$ %s", e.getMessage()));
		}
		common(model, request, session);
		model.addAttribute(EVALUATE_PROJECT_DETAILS, evalProjDetails);
		model.addAttribute("evalCapitalInvList", evalCapitalInvList);
		model.addAttribute("incentiveDeatilsData", incentiveDetails);
		model.addAttribute("evalMeanofFinanceList", evalMeanofFinanceList);

		return new ModelAndView(EVALUATE_APPLICATION);
	}

	/**
	 * This method is responsible to delete row from the list as well as table.
	 */
	@PostMapping("/deleteEvaluateCapInvest")
	public ModelAndView deleteEvaluateCapInvest(
			@ModelAttribute(EVALUATE_PROJECT_DETAILS) @Validated EvaluateProjectDetails evalProjDetails,
			@ModelAttribute("incentiveDeatilsData") @Validated IncentiveDetails incentiveDetails, BindingResult result,
			Model model, @RequestParam(value = "selectedRow", required = false) int index, HttpSession session,
			HttpServletRequest request) {

		try {
			if (!evalCapitalInvList.isEmpty() && evalCapitalInvList.get(index) != null) {
				EvaluateCapitalInvestment evalCapInv = evalCapitalInvList.get(index);
				try {
					evalCapInvService.deleteEvalCapInvById(evalCapInv.getEciId());
				} catch (Exception e) {

				}
				evalCapitalInvList.remove(index);
			}
		} catch (Exception e) {
			logger.error(String.format("#### deleteEvaluateCapInvest exception $$$ %s", e.getMessage()));
		}
		common(model, request, session);
		model.addAttribute("incentiveDeatilsData", incentiveDetails);
		model.addAttribute(EVALUATE_PROJECT_DETAILS, evalProjDetails);
		model.addAttribute("evalCapitalInvList", evalCapitalInvList);
		model.addAttribute("evalMeanofFinanceList", evalMeanofFinanceList);
		return new ModelAndView(EVALUATE_APPLICATION);

	}

	/**
	 * This method is responsible to add Mean Of Finance in the list.
	 */
	@PostMapping("/addProjAndMeanFinance")
	public ModelAndView addProjAndMeanFinance(
			@ModelAttribute(EVALUATE_PROJECT_DETAILS) @Validated EvaluateProjectDetails evalProjDetails,
			@ModelAttribute("incentiveDeatilsData") @Validated IncentiveDetails incentiveDetails, BindingResult result,
			Model model, HttpSession session, HttpServletRequest request) {

		EvaluateMeanOfFinance newEvalMF = new EvaluateMeanOfFinance();
		try {
			String appId = (String) session.getAttribute("appId");
			String evalCapInvId = appId.substring(0, appId.length() - 2) + "EMF" + atomicInteger.getAndIncrement();

			if (!evalProjDetails.getEmfphaseNo().isEmpty())
				newEvalMF.setEmfphaseNo(evalProjDetails.getEmfphaseNo());
			if (!evalProjDetails.getEmfphsItemName().isEmpty())
				newEvalMF.setEmfphsItemName(evalProjDetails.getEmfphsItemName());
			if (evalProjDetails.getEmfphsInvestAmt() > 0) {
				newEvalMF.setEmfphsInvestAmt(evalProjDetails.getEmfphsInvestAmt());

				newEvalMF.setEmfCreatedBy("Picup Team");
				newEvalMF.setEmfModifiyDate(new Date());
				newEvalMF.setEmfApplcId(appId);
				newEvalMF.setEmfId(evalCapInvId);
			}

			if (isepRowRecord) {
				if (!evalMeanofFinanceList.isEmpty() && evalMeanofFinanceList.get(epIndex) != null) {
					evalMeanofFinanceList.remove(epIndex);
				}
				updateMeanofFinanceRow(epIndex, newEvalMF);
				isepRowRecord = false;
				model.addAttribute("evalMeanofFinanceList", evalMeanofFinanceList);
				model.addAttribute(EVALUATE_PROJECT_DETAILS, evalProjDetails);
			} else {
				if (newEvalMF != null)
					evalMeanofFinanceList.add(newEvalMF);
				model.addAttribute("evalMeanofFinanceList", evalMeanofFinanceList);
				model.addAttribute(EVALUATE_PROJECT_DETAILS, evalProjDetails);
			}

		} catch (Exception e) {
			logger.error(String.format("#### add Mean of Finance exception $$$ %s", e.getMessage()));
		}
		common(model, request, session);
		model.addAttribute("incentiveDeatilsData", incentiveDetails);
		model.addAttribute(EVALUATE_PROJECT_DETAILS, evalProjDetails);
		model.addAttribute("evalCapitalInvList", evalCapitalInvList);
		model.addAttribute("evalMeanofFinanceList", evalMeanofFinanceList);
		model.addAttribute("addMofClick", "addMof");

		return new ModelAndView(EVALUATE_APPLICATION);
	}

	/**
	 * This method is responsible to delete row from the Mean of Finance list as
	 * well as table.
	 */
	@PostMapping("/deleteMeanofFinance")
	public ModelAndView deleteMeanofFinance(
			@ModelAttribute(EVALUATE_PROJECT_DETAILS) @Validated EvaluateProjectDetails evalProjDetails,
			@ModelAttribute("incentiveDeatilsData") @Validated IncentiveDetails incentiveDetails, BindingResult result,
			Model model, @RequestParam(value = "selectedRow", required = false) int index, HttpSession session,
			HttpServletRequest request) {

		try {
			if (!evalMeanofFinanceList.isEmpty() && evalMeanofFinanceList.get(index) != null) {
				EvaluateMeanOfFinance evalMeanofFin = evalMeanofFinanceList.get(index);
				try {
					evalMOf.deleteEvalMofById(evalMeanofFin.getEmfId());
				} catch (Exception e) {

				}
				evalMeanofFinanceList.remove(index);
			}
		} catch (Exception e) {
			logger.error(String.format("#### delete Mean of Finance exception $$$ %s", e.getMessage()));
		}
		common(model, request, session);
		model.addAttribute("incentiveDeatilsData", incentiveDetails);
		model.addAttribute(EVALUATE_PROJECT_DETAILS, evalProjDetails);
		model.addAttribute("evalCapitalInvList", evalCapitalInvList);
		model.addAttribute("evalMeanofFinanceList", evalMeanofFinanceList);
		return new ModelAndView(EVALUATE_APPLICATION);

	}

	@PostMapping(value = "/editMeanofFinance")
	public ModelAndView editMeanofFinance(
			@ModelAttribute(EVALUATE_PROJECT_DETAILS) @Validated EvaluateProjectDetails evalProjDetails,
			@ModelAttribute("incentiveDeatilsData") @Validated IncentiveDetails incentiveDetails, BindingResult result,
			Model model, @RequestParam(value = "editmofRecord", required = false) int index, HttpSession session,
			HttpServletRequest request) {

		EvaluateMeanOfFinance evalMeanofFin = null;
		try {
			if (!evalMeanofFinanceList.isEmpty() && evalMeanofFinanceList.get(index) != null) {
				evalMeanofFin = evalMeanofFinanceList.get(index);

				evalProjDetails.setEmfphaseNo(evalMeanofFin.getEmfphaseNo());
				evalProjDetails.setEmfphsItemName(evalMeanofFin.getEmfphsItemName());
				evalProjDetails.setEmfphsInvestAmt(evalMeanofFin.getEmfphsInvestAmt());
				epIndex = index;
				isepRowRecord = true;
			}
		} catch (Exception e) {
			logger.error(String.format("^^^^ editExistProjectRow Exception ^^^ %s", e.getMessage()));
		}

		common(model, request, session);
		model.addAttribute("incentiveDeatilsData", incentiveDetails);
		model.addAttribute(EVALUATE_PROJECT_DETAILS, evalProjDetails);
		model.addAttribute("evalCapitalInvList", evalCapitalInvList);
		model.addAttribute("evalMeanofFinanceList", evalMeanofFinanceList);

		return new ModelAndView(EVALUATE_APPLICATION);
	}

	public void updateMeanofFinanceRow(int index, EvaluateMeanOfFinance emf) {
		evalMeanofFinanceList.add(index, emf);
	}

}
