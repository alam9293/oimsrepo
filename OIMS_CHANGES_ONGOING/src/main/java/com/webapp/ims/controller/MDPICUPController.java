package com.webapp.ims.controller;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.webapp.ims.uam.service.TblUsersService;
import com.webapp.ims.model.BusinessEntityDetails;
import com.webapp.ims.model.IncentiveDetails;
import com.webapp.ims.model.IncentiveSpecificDetails;
import com.webapp.ims.model.InvestmentDetails;
import com.webapp.ims.model.PrepareAgendaNotes;
import com.webapp.ims.model.ProjectDetails;
import com.webapp.ims.model.ProposedEmploymentDetails;
import com.webapp.ims.model.RaiseQuery;
import com.webapp.ims.model.SkilledUnSkilledEmployemnt;
import com.webapp.ims.repository.IncentiveDetailsRepository;
import com.webapp.ims.repository.InvestmentDetailsRepository;
import com.webapp.ims.repository.ProjectRepository;
import com.webapp.ims.service.ApplicantDetailsService;
import com.webapp.ims.service.ApplicantDocumentService;
import com.webapp.ims.service.AvailCustmisedDetailsService;
import com.webapp.ims.service.BreakupCostService;
import com.webapp.ims.service.BusinessEntityDetailsService;
import com.webapp.ims.service.IncentiveDetailsService;
import com.webapp.ims.service.InvestmentDetailsService;
import com.webapp.ims.service.MeansOfFinanceService;
import com.webapp.ims.service.PhaseWiseInvestmentDetailsService;
import com.webapp.ims.service.PrepareAgendaNoteService;
import com.webapp.ims.service.PrepareAgendaNotesService;
import com.webapp.ims.service.ProjectService;
import com.webapp.ims.service.ProposedEmploymentDetailsService;
import com.webapp.ims.service.ProprietorDetailsService;
import com.webapp.ims.service.StateDetailsService;
import com.webapp.ims.service.impl.AdditionalInterest;

@Controller
public class MDPICUPController {
	private final Logger logger = LoggerFactory.getLogger(MDPICUPController.class);
	private ApplicantDetailsService appDtlService;
	private ApplicantDocumentService appDocService;
	private InvestmentDetailsService invDtlService;
	private PhaseWiseInvestmentDetailsService pwInvDtlService;
	private BreakupCostService brkupService;
	private MeansOfFinanceService mofService;
	private ProposedEmploymentDetailsService emplDtlService;
	private BusinessEntityDetailsService businessService;
	private ProprietorDetailsService proprietorService;
	private IncentiveDetailsService incentiveDetailsService;
	private AvailCustmisedDetailsService availCustmisedDetailsService;
	private ProjectService projectService;
	private List<SkilledUnSkilledEmployemnt> skillUnskillEmplList = new ArrayList<>();
	private String applicantId = null;
	List<PrepareAgendaNotes> panList = new ArrayList<>();
	AdditionalInterest additionalInterest;
	InvestmentDetailsRepository investRepository;
	PrepareAgendaNoteService prepareAgendaNoteService;
	private StateDetailsService stateDetailsService;

	@Autowired
	private PrepareAgendaNotesService prepareAgendaNotesService;

	@Autowired
	private TblUsersService loginService;
	
	@Autowired
	ProjectRepository projectRepository;

	@Autowired
	InvestmentDetailsRepository investmentDetailsRepository;

	@Autowired
	IncentiveDetailsRepository incentiveDetailsRepository;

	@Autowired

	BusinessEntityDetailsService businessEntityDetailsService;

	@Autowired
	ApplicationDetailsViewController applicationDetailsViewController;

	@Autowired
	public MDPICUPController(ApplicantDetailsService appDtlService, ApplicantDocumentService appDocService,
			InvestmentDetailsService invDtlService, PhaseWiseInvestmentDetailsService pwInvDtlService,
			BreakupCostService brkupService, MeansOfFinanceService mofService,
			ProposedEmploymentDetailsService emplDtlService, BusinessEntityDetailsService businessService,
			ProprietorDetailsService proprietorService, IncentiveDetailsService incentiveDetailsService,
			AvailCustmisedDetailsService availCustmisedDetailsService, ProjectService projectService,
			AdditionalInterest additionalInterest, InvestmentDetailsRepository investRepository,
			PrepareAgendaNoteService prepareAgendaNoteService, StateDetailsService stateDetailsService) {
		super();
		this.appDtlService = appDtlService;
		this.appDocService = appDocService;
		this.invDtlService = invDtlService;
		this.pwInvDtlService = pwInvDtlService;
		this.brkupService = brkupService;
		this.mofService = mofService;
		this.emplDtlService = emplDtlService;
		this.businessService = businessService;
		this.proprietorService = proprietorService;
		this.incentiveDetailsService = incentiveDetailsService;
		this.availCustmisedDetailsService = availCustmisedDetailsService;
		this.projectService = projectService;
		this.additionalInterest = additionalInterest;
		this.investRepository = investRepository;
		this.prepareAgendaNoteService = prepareAgendaNoteService;
		this.stateDetailsService = stateDetailsService;
	}

	@Autowired
	DashboardController dashboardController;

	@GetMapping(value = "/dashboardMDPICUP")
	public ModelAndView dashboardMDPICUP(Model model, HttpSession sessio) {
		dashboardController.dashboard(model, sessio);
		return new ModelAndView("dashboard-MDPICUP");
	}

	/**
	 * (method description)This method fetches data from database tables on the *
	 * basis of applicant id and renders view page to show the complete form data
	 * filled up by the applicant.
	 * 
	 * @param (String applicantId)
	 * @param (Model  model to contain data fetched from respective tables)
	 * @return (returns ModelAndView view page)
	 */

	@GetMapping(value = "/viewMDPICUPApplicationDetails")
	public ModelAndView viewMDPICUPApplicationDetails(
			@RequestParam(value = "applicantId", required = false) String applId, HttpSession session, Model model) {

		applicationDetailsViewController.commonViewApplicationDetails(applId, session, model);

		return new ModelAndView("view-all-application-details-MDPICUP");

	}

	@GetMapping(value = "/viewSMEMDPICUPApplications")
	public ModelAndView viewSMEMDPICUPApplications() {
		return new ModelAndView("select-policy-SME-MDPICUP");
	}

	@GetMapping(value = "/viewMDPICUPSMEApplicationDetails")
	public ModelAndView viewMDPICUPSMEApplicationDetails(
			@RequestParam(value = "applicantId", required = false) String applId, HttpSession session, Model model) {

		applicationDetailsViewController.commonViewApplicationDetails(applId, session, model);
		return new ModelAndView("view-all-application-details-SME-MDPICUP");

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

	@GetMapping(value = "/MDPICUPApplicationForLoc")
	public String MDPICUPApplicationForLoc(ModelMap model) {
		List<Object> investmentDetailsList = investRepository.getAllDetailsByAppId();
		model.addAttribute("investmentDetailsList", investmentDetailsList);
		return "view-incentive-applications-MDPICUP";
	}

	@GetMapping(value = "/MDPICUPSMEApplicationForLoc")
	public String MDPICUPSMEApplicationForLoc(ModelMap model) {
		logger.debug("Applications SME For LOC");
		List<Object> investmentDetailsList = investRepository.getAllDetailsSMEByAppId();
		model.addAttribute("investmentDetailsList", investmentDetailsList);
		return "view-incentive-applications-SME-MDPICUP";
	}

	@GetMapping(value = "/selectPolicyMDPICUP")
	public String selectPolicyMDPICUP(Model model) {
		return "select-policy-MDPICUP";
	}

	@GetMapping(value = "MDPICUPEvaluateApplication")
	public String MDPICUPEvaluateApplication(@RequestParam(value = "applicantId", required = false) String applId,
			Model model, HttpServletRequest request, HttpSession session) {
		logger.debug("Evaluate department Application Starts");
		final String SMALL = "Small";
		final String MEDIUM = "Medium";
		final String LARGE = "Large";
		final String MEGA = "Mega";
		final String MEGAPLUS = "Mega Plus";
		final String SUPERMEGA = "Super Mega";
		String appId = (String) session.getAttribute("appId");
		ProjectDetails ProjectDetail = projectService.getProjectByapplicantDetailId(appId);
		if (ProjectDetail != null) {
			model.addAttribute("contactPersonName", ProjectDetail.getContactPersonName());
			model.addAttribute("mobileNo", ProjectDetail.getMobileNo());
			model.addAttribute("designation", ProjectDetail.getDesignation());
			model.addAttribute("projectDescription", ProjectDetail.getProjectDescription());
			model.addAttribute("webSiteName", ProjectDetail.getWebSiteName());
			model.addAttribute("fullAddress", ProjectDetail.getFullAddress());
			model.addAttribute("districtName", ProjectDetail.getDistrictName());
			model.addAttribute("mandalName", ProjectDetail.getMandalName());
			model.addAttribute("resionName", ProjectDetail.getResionName());
			model.addAttribute("pinCode", ProjectDetail.getPinCode());
			model.addAttribute("regiOrLicense", ProjectDetail.getRegiOrLicense());
			model.addAttribute("regiOrLicenseFileName", ProjectDetail.getRegiOrLicenseFileName());
			model.addAttribute("newProject", ProjectDetail.getNewProject());
			model.addAttribute("expansion", ProjectDetail.getExpansion());
			model.addAttribute("diversification", ProjectDetail.getDiversification());
			model.addAttribute("ExistingProducts", ProjectDetail.getExistingProducts());
			model.addAttribute("existingInstalledCapacity", ProjectDetail.getExistingInstalledCapacity());
			model.addAttribute("proposedProducts", ProjectDetail.getProposedProducts());
			model.addAttribute("proposedInstalledCapacity", ProjectDetail.getProposedInstalledCapacity());
			model.addAttribute("existingGrossBlock", ProjectDetail.getExistingGrossBlock());
			model.addAttribute("proposedGrossBlock", ProjectDetail.getProposedGrossBlock());
			model.addAttribute("enclDetProRepFileName", ProjectDetail.getEnclDetProRepFileName());
			model.addAttribute("caCertificateFileName", ProjectDetail.getCaCertificateFileName());
			model.addAttribute("charatEngFileName", ProjectDetail.getCharatEngFileName());
			model.addAttribute("natureOfProject", ProjectDetail.getNatureOfProject());

			String pergrosblock = additionalInterest.getgrossBlock(appId);
			model.addAttribute("pergrosblock", pergrosblock);

			String regorlic = additionalInterest.getregiOrLicense(appId);
			model.addAttribute("regorlic", regorlic);
			model.addAttribute("raiseQuery", new RaiseQuery());

		}

		// Proposed Employment Details view
		try {
			ProposedEmploymentDetails emplDetail = emplDtlService.getProposedEmploymentDetailsByappId(appId);
			if (emplDetail != null) {
				List<SkilledUnSkilledEmployemnt> skilledUnSkilledEmployemntslist = emplDetail
						.getSkilledUnSkilledEmployemnt();
				model.addAttribute("proposedEmploymentDetails", emplDetail);
				skillUnskillEmplList.clear();
				skillUnskillEmplList.addAll(skilledUnSkilledEmployemntslist);
				model.addAttribute("skilledUnSkilledEmployemntList", skillUnskillEmplList);
				totalSkilledAndUnSkilledEmploment(model);
				model.addAttribute("proposedEmploymentDetails", new ProposedEmploymentDetails());
				model.addAttribute("skilledUnSkilledEmployemnt", new SkilledUnSkilledEmployemnt());

			} else {
				skillUnskillEmplList.clear();
				model.addAttribute("proposedEmploymentDetails", new ProposedEmploymentDetails());
				model.addAttribute("skilledUnSkilledEmployemnt", new SkilledUnSkilledEmployemnt());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {

			InvestmentDetails invDetailFromDb = invDtlService.getInvestmentDetailsByapplId(appId);

			model.addAttribute("InvFCI", invDetailFromDb.getInvFci());
			model.addAttribute("category", invDetailFromDb.getInvIndType());
			model.addAttribute("InvTPC", invDetailFromDb.getInvTotalProjCost());
			model.addAttribute("InvLC", invDetailFromDb.getInvLandCost());
			model.addAttribute("InvBuiildC", invDetailFromDb.getInvBuildingCost());
			model.addAttribute("invPlantAndMachCost", invDetailFromDb.getInvPlantAndMachCost());
			model.addAttribute("invMisFixCost", invDetailFromDb.getInvOtherCost());
			model.addAttribute("invMisFixCost", invDetailFromDb.getInvOtherCost());

			model.addAttribute("invEqShCapital", invDetailFromDb.getInvEqShCapital());
			model.addAttribute("invEqIntCashAccrl", invDetailFromDb.getInvEqIntCashAccrl());
			model.addAttribute("invEqIntFreeUnsecLoan", invDetailFromDb.getInvEqIntFreeUnsecLoan());
			model.addAttribute("invEqSecDept", invDetailFromDb.getInvEqSecDept());
			model.addAttribute("invEqAdvDealer", invDetailFromDb.getInvEqAdvDealer());
			model.addAttribute("invDebtFi", invDetailFromDb.getInvDebtFi());
			model.addAttribute("invDebtBank", invDetailFromDb.getInvDebtBank());

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
				incentiveSpecificDetails.setISF_Ttl_EPF_Reim(IncentiveDetail.getISF_Ttl_EPF_Reim());

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

				incentiveSpecificDetails.setOthAddRequest1(IncentiveDetail.getOthAddRequest1());

				incentiveSpecificDetails.setId(IncentiveDetail.getId());

				try {
					Long isf_Claim_Reim = incentiveSpecificDetails.getISF_Claim_Reim() == null ? 0
							: incentiveSpecificDetails.getISF_Claim_Reim();
					Long isf_Reim_SCST = incentiveSpecificDetails.getISF_Reim_SCST() == null ? 0
							: incentiveSpecificDetails.getISF_Reim_SCST();
					Long isf_EX_E_Duty_PC = incentiveSpecificDetails.getISF_EX_E_Duty_PC() == null ? 0
							: incentiveSpecificDetails.getISF_EX_E_Duty_PC();
					Long isf_EX_Mandee_Fee = incentiveSpecificDetails.getISF_EX_Mandee_Fee() == null ? 0
							: incentiveSpecificDetails.getISF_EX_Mandee_Fee();
					Long isf_Reim_BPLW = incentiveSpecificDetails.getISF_Reim_BPLW() == null ? 0
							: incentiveSpecificDetails.getISF_Reim_BPLW();
					Long isf_Reim_FW = incentiveSpecificDetails.getISF_Reim_FW() == null ? 0
							: incentiveSpecificDetails.getISF_Reim_FW();
					Long total = isf_Claim_Reim + isf_Reim_SCST + isf_EX_E_Duty_PC + isf_EX_Mandee_Fee + isf_Reim_BPLW
							+ isf_Reim_FW;
					model.addAttribute("total", total);
				} catch (Exception e) {
					logger.error("total  exception **** " + e.getMessage());
					e.printStackTrace();
				}
			}
			model.addAttribute("incentiveDeatilsData", incentiveSpecificDetails);

		} catch (Exception e) {
			logger.error("evaluation-view-MDPICUP exception #### " + e.getMessage());
			e.printStackTrace();
		}
		logger.debug("evaluation-view-MDPICUP End ");
		return "evaluation-view-MDPICUP";
	}

	@RequestMapping(value = "/downloadSupportMD", method = RequestMethod.POST)
	public void downloadSupportMD(@ModelAttribute("downloadSupportMD") @Validated PrepareAgendaNotes prepareAgendaNotes,
			Model model, HttpSession session, HttpServletResponse response) {
		String appId = (String) session.getAttribute("appId");
		// String userId = (String) session.getAttribute("userId");
		// String test = "new string test bytes shuhsillllllllllll";

		// BusinessEntityDetails bedByappli =
		// businessEntityDetailsService.getBusinessEntityByapplicantDetailId(appId);
		ProjectDetails projectdetailid = projectRepository.getProjectByapplicantDetailId(appId);
		InvestmentDetails investmentDetailsid = investmentDetailsRepository.getInvestmentDetailsByapplId(appId);
		BusinessEntityDetails bussinessDetails = businessEntityDetailsService
				.getBusinessEntityByapplicantDetailId(appId);
		IncentiveDetails incentiveByisfapcId = incentiveDetailsRepository.getIncentiveByisfapcId(appId);

		String regiOrLicenseFileName = projectdetailid.getRegiOrLicenseFileName();
		byte[] regiOrLicensepdf = projectdetailid.getRegiOrLicenseFileData();

		String enclDetProRepFileName = projectdetailid.getEnclDetProRepFileName();
		byte[] enclDetProRepFileData = projectdetailid.getEnclDetProRepFileData();

		String caCertificateFileName = projectdetailid.getCaCertificateFileName();
		byte[] caCertificateFileData = projectdetailid.getCaCertificateFileData();

		String charatEngFileName = projectdetailid.getCharatEngFileName();
		byte[] charatEngFileData = projectdetailid.getCharatEngFileData();

		String invBrekupofMeans = investmentDetailsid.getInvFilename();
		byte[] invBrekupofMeansData = investmentDetailsid.getInvFiledata();

		String moaDocFname = bussinessDetails.getMoaDocFname();
		byte[] moaDoc = bussinessDetails.getMoaDoc();

		String regisAttacDocFname = bussinessDetails.getRegisAttacDocFname();
		byte[] registrationAttachedDoc = bussinessDetails.getRegistrationAttachedDoc();

		String bodDocFname = bussinessDetails.getBodDocFname();
		byte[] bodDoc = bussinessDetails.getBodDoc();

		String IndusAffidaDocFname = bussinessDetails.getIndusAffidaDocFname();
		byte[] indusAffidaviteDoc = bussinessDetails.getAnnexiaformFdata();

		String annexureiaformat = bussinessDetails.getAnnexureiaformat();
		byte[] annexiaformFdata = bussinessDetails.getAnnexiaformFdata();

		String isfCustIncDoc = incentiveByisfapcId.getIsfCustIncDocName();
		byte[] IsfCustIncDocment = incentiveByisfapcId.getIsfCustIncDoc();

		response.setHeader("Content-Disposition", "attachment; filename=" + appId + "file.zip");
		response.setHeader("Content-Type", "application/zip");
		ZipOutputStream zipOutputStream;
		try {
			zipOutputStream = new ZipOutputStream(response.getOutputStream());

			if (regiOrLicenseFileName != null && regiOrLicensepdf != null) {
				zipOutputStream.putNextEntry(new ZipEntry("regiOrLicense_" + regiOrLicenseFileName));
				InputStream is = new ByteArrayInputStream(regiOrLicensepdf);
				IOUtils.copy(is, zipOutputStream);
			}

			if (enclDetProRepFileName != null && enclDetProRepFileData != null) {
				zipOutputStream.putNextEntry(new ZipEntry("enclDetProRep_" + enclDetProRepFileName));
				InputStream is1 = new ByteArrayInputStream(enclDetProRepFileData);
				IOUtils.copy(is1, zipOutputStream);
			}
			if (caCertificateFileName != null && caCertificateFileData != null) {
				zipOutputStream.putNextEntry(new ZipEntry("caCertificate_" + caCertificateFileName));
				InputStream is2 = new ByteArrayInputStream(caCertificateFileData);
				IOUtils.copy(is2, zipOutputStream);
			}

			if (charatEngFileName != null && charatEngFileData != null) {
				zipOutputStream.putNextEntry(new ZipEntry("charatEng_" + charatEngFileName));
				InputStream is3 = new ByteArrayInputStream(charatEngFileData);
				IOUtils.copy(is3, zipOutputStream);
			}

			if (invBrekupofMeans != null && invBrekupofMeansData != null) {
				zipOutputStream.putNextEntry(new ZipEntry("invBrekupofMeans_" + invBrekupofMeans));
				InputStream is4 = new ByteArrayInputStream(invBrekupofMeansData);
				IOUtils.copy(is4, zipOutputStream);
			}

			if (moaDocFname != null && moaDocFname != null) {
				zipOutputStream.putNextEntry(new ZipEntry("moaDeed	Doc_" + moaDocFname));
				InputStream is5 = new ByteArrayInputStream(moaDoc);
				IOUtils.copy(is5, zipOutputStream);
			}

			if (regisAttacDocFname != null && registrationAttachedDoc != null) {
				zipOutputStream.putNextEntry(new ZipEntry("CertIncorpRagistion_" + regisAttacDocFname));
				InputStream is6 = new ByteArrayInputStream(registrationAttachedDoc);
				IOUtils.copy(is6, zipOutputStream);
			}

			if (bodDocFname != null && bodDoc != null) {
				zipOutputStream.putNextEntry(new ZipEntry("resolutionofBoD_" + bodDocFname));
				InputStream is7 = new ByteArrayInputStream(bodDoc);
				IOUtils.copy(is7, zipOutputStream);
			}

			if (IndusAffidaDocFname != null && indusAffidaviteDoc != null) {
				zipOutputStream.putNextEntry(new ZipEntry("DeponentofCAuthoofIUAuthorizing_" + IndusAffidaDocFname));
				InputStream is8 = new ByteArrayInputStream(indusAffidaviteDoc);
				IOUtils.copy(is8, zipOutputStream);
			}

			if (annexureiaformat != null && annexiaformFdata != null) {
				zipOutputStream.putNextEntry(new ZipEntry("AnnexureUpload_" + annexureiaformat));
				InputStream is9 = new ByteArrayInputStream(annexiaformFdata);
				IOUtils.copy(is9, zipOutputStream);
			}

			if (IsfCustIncDocment != null && IsfCustIncDocment != null) {
				zipOutputStream.putNextEntry(new ZipEntry("customisedIncentivesDoc" + isfCustIncDoc));
				InputStream is10 = new ByteArrayInputStream(IsfCustIncDocment);
				IOUtils.copy(is10, zipOutputStream);
			}

			zipOutputStream.closeEntry();
			zipOutputStream.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
